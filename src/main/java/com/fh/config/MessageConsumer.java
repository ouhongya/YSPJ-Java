package com.fh.config;

import com.alibaba.excel.util.StringUtils;
import com.fh.dao.DaoSupport;
import com.fh.entity.app.ExamStatus;
import com.fh.util.PageData;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.fh.entity.Exam.*;
import static com.fh.util.Const.ISSUEIMAGEPATH;

/**
 * 考试延迟队列
 */
public class MessageConsumer implements MessageListener {


    @Resource(name = "daoSupport")
    private DaoSupport dao;


    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");

    @Override
    public void onMessage(Message message) {
        try {
            String examId = new String(message.getBody(), "UTF-8");
            String[] split = examId.split("/");
            String s = split[0];
            if (split[1].equals("1")) {
                examSingleTime(s);
            } else if (split[1].equals("1024")){
                String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
                String webappRoot = classpath.replaceAll("WEB-INF/classes/", "");
                String fileUrl = webappRoot + ISSUEIMAGEPATH + s;
                FileUtils.forceDelete(new File(fileUrl));
            }else{
                int time = Integer.parseInt(split[2]);
                userSingleTime(s, time);
            }
            logger.info("收到消息了:------------>" + examId.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info("consumer receive message------->:{}", message);
    }

    public void examSingleTime(String examId) throws Exception {
        List<String> userIds = (List<String>) redisTemplate.opsForValue().get(examId);
        for (String userId : userIds) {
            PageData pd = new PageData();
            String uid = userId;
            pd.put("uid", userId);
            pd.put("exam_id", userId);
            String exam_user_id = "";
            String examUserId = "";
            //考试时间
            Integer examTime = 0;
            //得分
            Integer score = 0;
            //扣分
            Integer etc_score = 0;
            //答对多少题
            Integer right_topic = 0;
            //答错多少题
            Integer bad_topic = 0;
            //及格分
            Integer rightScore = 0;
            //考试次数
            Integer exam_count = 0;
            //答题次数
            Integer topic_count = 0;
            //22正常结束;33时间中止;
            String type = "33";
            //Redis数据同步到MySQL
            String redisHashKey = uid + USER_SINGLE_ANSWER_TOPIC;
            Map<Integer, List<PageData>> data = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, examId);
            List<PageData> dataList = data.get(getMaxKey(data));
            examUserId = dataList.get(0).getString("exam_user_id");
            String isStatus = dataList.get(0).getString("isStatus");
            if (EXAM_COMPLETE.equals(isStatus)) {
                break;
            }
            List<PageData> userAnswerDataList = new ArrayList<PageData>();
            for (PageData pageData : dataList) {
                pageData.put("isStatus", EXAM_END);
                //0正确;1错误;2未开始
                if (pageData.get("right") instanceof String) {
                    switch (Integer.parseInt(pageData.getString("right"))) {
                        case 0:
                            score += (Integer) pageData.get("score");
                            right_topic += 1;
                            break;
                        case 1:
                            etc_score += (Integer) pageData.get("score");
                            bad_topic += 1;
                            break;
                        default:
                            break;
                    }
                } else {
                    switch ((Integer) pageData.get("right")) {
                        case 0:
                            score += (Integer) pageData.get("score");
                            right_topic += 1;
                            break;
                        case 1:
                            etc_score += (Integer) pageData.get("score");
                            bad_topic += 1;
                            break;
                        default:
                            break;
                    }
                }
                exam_user_id = pageData.getString("exam_user_id");
                if (pageData.get("examTime") instanceof Integer) {
                    examTime = (Integer) pageData.get("examTime");
                } else {
                    Long time = new Long((Long) pageData.get("examTime"));
                    examTime = time.intValue();
                }

                rightScore = (Integer) pageData.get("is_hash");
                exam_count = (Integer) pageData.get("exam_count");
                topic_count = (Integer) pageData.get("topic_count");
                PageData userAnswerData = new PageData();
                userAnswerData.put("id", pageData.get("exam_user_topic_id"));
                userAnswerData.put("answer", pageData.get("userAnswer"));
                userAnswerData.put("update_time", pageData.get("update_time") == null ? null : pageData.get("update_time"));
                userAnswerData.put("status", pageData.get("right"));
                userAnswerDataList.add(userAnswerData);
            }
            //是否可以重考
            Integer isFlag = 0;
            if (exam_count > topic_count) {
                isFlag = 1;
            }
            //考试用时
            Long expire = redisTemplate.opsForValue().getOperations().getExpire(exam_user_id, TimeUnit.SECONDS);
            long value = examTime.longValue();
            Long duration = value - expire;
            //0合格,1不合格
            Integer is_hash = score >= rightScore ? 0 : 1;
            //0未开始,1已开始,2进行中,3暂停中,4再次答题,5查看错题,6合格,7不合格
            Integer status = is_hash == 0 ? 6 : 7;
            //修改考试主表
            //判断之前是否考试及格过
            List<PageData> FlagData1 = (List<PageData>) dao.findForList("ExamMapper.queryExamToUserHistory1", pd);
            //判断之前是否考试不及格
            List<PageData> FlagData2 = (List<PageData>) dao.findForList("ExamMapper.queryExamToUserHistory2", pd);
            //查询之前的人数
            PageData examUserSizeData = (PageData) dao.findForObject("ExamMapper.queryExamUserSize", examId);
            Integer finish_user = (Integer) examUserSizeData.get("finish_user");
            Integer unfinish_user = (Integer) examUserSizeData.get("unfinish_user");
            PageData examData = new PageData();
            //附一个初始值
            examData.put("finish_user", finish_user);
            examData.put("unfinish_user", unfinish_user);
            //第一次考试
            if (FlagData1.size() == 0 && FlagData2.size() == 0) {
                if (is_hash == 0) {
                    //合格
                    examData.put("finish_user", finish_user += 1);
                    examData.put("unfinish_user", unfinish_user -= 1);
                } else {
                    //不合格
                    examData.put("finish_user", finish_user);
                    examData.put("unfinish_user", unfinish_user);
                }
            }
            //之前考试全都不合格
            if (FlagData1.size() == 0 && FlagData2.size() != 0) {
                if (is_hash == 0) {
                    //合格
                    examData.put("finish_user", finish_user += 1);
                    examData.put("unfinish_user", unfinish_user -= 1);
                } else {
                    //不合格
                    examData.put("finish_user", finish_user);
                    examData.put("unfinish_user", unfinish_user);
                }

            }
            examData.put("id", examId);
            dao.update("ExamMapper.updateExamBayUser", examData);
            //保存用户表数据
            PageData pageData = new PageData();
            pageData.put("id", examUserId);
            pageData.put("score", score);
            pageData.put("etc_score", etc_score);
            pageData.put("right_topic", right_topic);
            pageData.put("bad_topic", bad_topic);
            pageData.put("type", type);
            pageData.put("duration", duration);
            pageData.put("status", status);
            pageData.put("is_hash", is_hash);
            dao.update("ExamMapper.updateExamToUserById", pageData);
            //保存答题表数据
            dao.batchUpdate("ExamMapper.updateExamToUserByAnswer", userAnswerDataList);
            //保存历史成绩
            PageData history = new PageData();
            history.put("exam_user_id", examUserId);
            history.put("sort", topic_count);
            history.put("score", score);
            history.put("duration", duration);
            history.put("is_flag", score >= rightScore ? 0 : 1);
            history.put("created_time", new Date());
            history.put("exam_id", examId);
            history.put("user_id", uid);
            dao.save("ExamMapper.saveUserHistory", history);
            //修改redis中的数据
            redisTemplate.opsForHash().put(redisHashKey, examId, data);
        }
    }


    public void userSingleTime(String examUserId, Integer times) throws Exception {
        PageData pd = new PageData();
        PageData daoPage = (PageData) dao.findForObject("ExamMapper.queryUserSingleTime", examUserId);
        String examId = daoPage.getString("exam_id");
        String uid = daoPage.getString("user_id");
        String exam_user_id = examUserId;
        pd.put("uid", uid);
        pd.put("exam_id", examId);
        //判断考试是否重考
        Map<Integer, List<PageData>> datas = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(uid + USER_SINGLE_ANSWER_TOPIC, examId);
        List<PageData> dataList1 = datas.get(getMaxKey(datas));
        Integer pauseTime = (Integer) dataList1.get(0).get("pauseTime");
        String isStatus = (String) dataList1.get(0).get("isStatus");
        if (!pauseTime.equals(times)) {
            return;
        }
        if (EXAM_COMPLETE.equals(isStatus)) {
            return;
        }
        //考试时间
        Integer examTime = 0;
        //得分
        Integer score = 0;
        //扣分
        Integer etc_score = 0;
        //答对多少题
        Integer right_topic = 0;
        //答错多少题
        Integer bad_topic = 0;
        //及格分
        Integer rightScore = 0;
        //考试次数
        Integer exam_count = 0;
        //答题次数
        Integer topic_count = 0;
        //22正常结束;33时间中止;
        String type = "22";
        //Redis数据同步到MySQL
        String redisHashKey = uid + USER_SINGLE_ANSWER_TOPIC;
        Map<Integer, List<PageData>> data = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, examId);
        List<PageData> dataList = data.get(getMaxKey(data));
        List<PageData> userAnswerDataList = new ArrayList<PageData>();
        for (PageData pageData : dataList) {
            pageData.put("isStatus", EXAM_END);
            //0正确;1错误;2未开始
            if (pageData.get("right") instanceof String) {
                switch (Integer.parseInt(pageData.getString("right"))) {
                    case 0:
                        score += (Integer) pageData.get("score");
                        right_topic += 1;
                        break;
                    case 1:
                        etc_score += (Integer) pageData.get("score");
                        bad_topic += 1;
                        break;
                    default:
                        break;
                }
            } else {
                switch ((Integer) pageData.get("right")) {
                    case 0:
                        score += (Integer) pageData.get("score");
                        right_topic += 1;
                        break;
                    case 1:
                        etc_score += (Integer) pageData.get("score");
                        bad_topic += 1;
                        break;
                    default:
                        break;
                }
            }
            exam_user_id = pageData.getString("exam_user_id");
            if (pageData.get("examTime") instanceof Integer) {
                examTime = (Integer) pageData.get("examTime");
            } else {
                Long time = new Long((Long) pageData.get("examTime"));
                examTime = time.intValue();
            }

            rightScore = (Integer) pageData.get("is_hash");
            exam_count = (Integer) pageData.get("exam_count");
            topic_count = (Integer) pageData.get("topic_count");
            PageData userAnswerData = new PageData();
            userAnswerData.put("id", pageData.get("exam_user_topic_id"));
            userAnswerData.put("answer", pageData.get("userAnswer"));
            userAnswerData.put("update_time", pageData.get("update_time") == null ? null : pageData.get("update_time"));
            userAnswerData.put("status", pageData.get("right"));
            userAnswerDataList.add(userAnswerData);
        }
        //是否可以重考
        Integer isFlag = 0;
        if (exam_count > topic_count) {
            isFlag = 1;
        }
        //考试用时
        Long expire = redisTemplate.opsForValue().getOperations().getExpire(exam_user_id, TimeUnit.SECONDS);
        long value = examTime.longValue();
        Long duration = value - expire;
        //0合格,1不合格
        Integer is_hash = score >= rightScore ? 0 : 1;
        //0未开始,1已开始,2进行中,3暂停中,4再次答题,5查看错题,6合格,7不合格
        Integer status = is_hash == 0 ? 6 : 7;
        //修改考试主表
        //判断之前是否考试及格过
        List<PageData> FlagData1 = (List<PageData>) dao.findForList("ExamMapper.queryExamToUserHistory1", pd);
        //判断之前是否考试不及格
        List<PageData> FlagData2 = (List<PageData>) dao.findForList("ExamMapper.queryExamToUserHistory2", pd);
        //查询之前的人数
        PageData examUserSizeData = (PageData) dao.findForObject("ExamMapper.queryExamUserSize", examId);
        Integer finish_user = (Integer) examUserSizeData.get("finish_user");
        Integer unfinish_user = (Integer) examUserSizeData.get("unfinish_user");
        PageData examData = new PageData();
        //附一个初始值
        examData.put("finish_user", finish_user);
        examData.put("unfinish_user", unfinish_user);
        //第一次考试
        if (FlagData1.size() == 0 && FlagData2.size() == 0) {
            if (is_hash == 0) {
                //合格
                examData.put("finish_user", finish_user += 1);
                examData.put("unfinish_user", unfinish_user -= 1);
            } else {
                //不合格
                examData.put("finish_user", finish_user);
                examData.put("unfinish_user", unfinish_user);
            }
        }
        //之前考试全都不合格
        if (FlagData1.size() == 0 && FlagData2.size() != 0) {
            if (is_hash == 0) {
                //合格
                examData.put("finish_user", finish_user += 1);
                examData.put("unfinish_user", unfinish_user -= 1);
            } else {
                //不合格
                examData.put("finish_user", finish_user);
                examData.put("unfinish_user", unfinish_user);
            }

        }
        examData.put("id", examId);
        dao.update("ExamMapper.updateExamBayUser", examData);
        //保存用户表数据
        PageData pageData = new PageData();
        pageData.put("id", examUserId);
        pageData.put("score", score);
        pageData.put("etc_score", etc_score);
        pageData.put("right_topic", right_topic);
        pageData.put("bad_topic", bad_topic);
        pageData.put("type", type);
        pageData.put("duration", duration);
        pageData.put("status", status);
        pageData.put("is_hash", is_hash);
        dao.update("ExamMapper.updateExamToUserById", pageData);
        //保存答题表数据
        dao.batchUpdate("ExamMapper.updateExamToUserByAnswer", userAnswerDataList);
        //保存历史成绩
        PageData history = new PageData();
        history.put("exam_user_id", examUserId);
        history.put("sort", topic_count);
        history.put("score", score);
        history.put("duration", duration);
        history.put("is_flag", score >= rightScore ? 0 : 1);
        history.put("created_time", new Date());
        history.put("exam_id", examId);
        history.put("user_id", uid);
        dao.save("ExamMapper.saveUserHistory", history);
        //修改redis中的数据
        redisTemplate.opsForHash().put(redisHashKey, examId, data);
    }


    private PageData showResult(List<String> dateArray) {
        Map<String, Integer> dateMap = new TreeMap<String, Integer>();
        int i, arrayLen;
        arrayLen = dateArray.size();
        for (i = 0; i < arrayLen; i++) {
            String dateKey = dateArray.get(i);
            if (dateMap.containsKey(dateKey)) {
                int value = dateMap.get(dateKey) + 1;
                dateMap.put(dateKey, value);
            } else {
                dateMap.put(dateKey, 1);
            }
        }
        Set<String> keySet = dateMap.keySet();
        String[] sorttedArray = new String[keySet.size()];
        Iterator<String> iter = keySet.iterator();
        int index = 0;
        while (iter.hasNext()) {
            String key = iter.next();
            sorttedArray[index++] = key;
        }
        int sorttedArrayLen = sorttedArray.length;
        String bigDate = sorttedArray[0];
        String simDate = sorttedArray[sorttedArrayLen - 1];
        PageData pageData = new PageData();
        pageData.put("bigDate", bigDate);
        pageData.put("simDate", simDate);
        return pageData;
    }


    public static Integer getMaxKey(Map<Integer, List<PageData>> map) {
        if (map == null) {
            return null;
        }
        Set<Integer> set = map.keySet();
        Object[] obj = set.toArray();
        Arrays.sort(obj);
        return Integer.parseInt(obj[obj.length - 1].toString());
    }

}