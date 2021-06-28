package com.fh.service.app;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.config.DelayMessageSender;
import com.fh.config.RabbitmqPublish;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.app.PageVo;
import com.fh.entity.app.*;
import com.fh.entity.system.User;
import com.fh.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.IterableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.fh.entity.Exam.*;
import static com.fh.util.Const.EXAM;
import static com.fh.util.Const.ISSUEIMAGEPATH;

@Service("ExamService")
public class ExamService {

    private volatile static String path = "E:/";

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "delayMessageSender")
    private DelayMessageSender sender;

    @Autowired
    private RabbitmqPublish rabbitmqPublish;

    @Autowired
    private RedisTemplate redisTemplate;


    SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 查询题库列表
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public PageInfo queryQuestionBankList(PageData pageData) throws Exception {
        List<PageData> QuestionBankList = null;
        PageVo pageVo = new PageVo();
        pageVo.setPage(pageData.get("page").toString());
        pageVo.setSize(pageData.get("size").toString());
        pageVo.setUid(pageData.get("uid").toString());
        pageVo.setIds(new ArrayList<String>(new TreeSet<String>(Arrays.asList(pageData.get("ids").toString().split(",")))));
        pageVo.setCompanyId(pageData.get("companyId").toString());
        if (!StringUtils.isEmpty(pageData.get("ids").toString())) {
            PageHelper.startPage(Integer.parseInt(pageVo.getPage()), 99999999);
            QuestionBankList = (List<PageData>) dao.findForList("ExamMapper.queryQuestionBankListByCategory", pageVo);
            //拉取下面的子项
            for (PageData data : QuestionBankList) {
                pageData.put("id", data.getString("id"));
                PageData dataVo = queryBankDetail(pageData);
                List<PageData> lisst = new ArrayList<>();
                List<PageData> list1 = (List<PageData>) dataVo.get("bankList");
                List<PageData> list2 = (List<PageData>) dataVo.get("oneList");
                Integer count = 0;
                for (PageData pageData1 : list1) {
                    Long radio = (Long) pageData1.get("radio");
                    Long checkbox = (Long) pageData1.get("checkbox");
                    Long judge = (Long) pageData1.get("judge");
                    count += radio.intValue();
                    count += checkbox.intValue();
                    count += judge.intValue();
                }
                PageData pd = new PageData();
                pd.put("name", "批量录入 " + "(" + count + ")");
                pd.put("type", 1);
                pd.put("count", count);
                pd.put("list", list1);
                List<PageData> dataList = new ArrayList<>();
                for (PageData pageData1 : list2) {
                    String typename = "";
                    Integer type = (Integer) pageData1.get("type");
                    if (type == 1) {
                        typename = "单选题";
                    } else if (type == 2) {
                        typename = "多选题";
                    } else {
                        typename = "判断题";
                    }
                    PageData data1 = new PageData();
                    data1.put("id", pageData1.get("id"));
                    data1.put("title", pageData1.get("stem"));
                    data1.put("created_time", pageData1.get("created_time"));
                    data1.put("typename", typename);
                    data1.put("name", pageData1.get("name"));
                    data1.put("isFlag", pageData1.get("isFlag"));
                    dataList.add(data1);
                }
                PageData pageData1 = new PageData();
                pageData1.put("name", "单个录入 " + "(" + dataList.size() + ")");
                pageData1.put("type", 2);
                pageData1.put("count", dataList.size());
                pageData1.put("list", dataList);
                lisst.add(pd);
                lisst.add(pageData1);
                data.put("listpart", lisst);
            }
        } else {
            PageHelper.startPage(Integer.parseInt(pageVo.getPage()), Integer.parseInt(pageVo.getSize()));
            QuestionBankList = (List<PageData>) dao.findForList("ExamMapper.queryQuestionBankList", pageVo);
        }
        return new PageInfo(QuestionBankList);
    }

    /**
     * 查询题库列表不分页
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public List<PageData> queryQuestionBankListNotLimit(PageData pageData) throws Exception {
        return (List<PageData>) dao.findForList("ExamMapper.queryQuestionBankListNotLimit", pageData);
    }

    /**
     * 添加题库
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public String addQuestionBank(PageData pageData) throws Exception {
        List<PageData> pd = (List<PageData>) dao.findForList("ExamMapper.queryQuestionBankOneRemark", pageData);
        if (pd.size() != 0) {
            List<String> listName = new ArrayList<>();
            for (PageData data : pd) {
                listName.add(data.getString("name"));
            }
            if (listName.contains(pageData.getString("name"))) {
                return "WARNING";
            }
            pageData.put("is_default", 0);
        } else {
            pageData.put("is_default", 1);
        }
        pageData.put("id", UuidUtil.get32UUID());
        pageData.put("name", pageData.getString("name"));
        pageData.put("status", 0);
        pageData.put("company_id", pageData.getString("companyId"));
        pageData.put("user_id", pageData.getString("uid"));
        pageData.put("created_time", new Date());
        pageData.put("last_update_time", new Date());
        dao.save("ExamMapper.addQuestionBank", pageData);
        return "SUCCESS";
    }

    /**
     * 删除题库
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public String deleteQuestionBank(PageData pageData) throws Exception {
        //删除题库
        dao.update("ExamMapper.deleteQuestionBank", pageData);
        //删除题
        dao.update("ExamMapper.deleteQuestionBankByExamTopic", pageData);
        return "SUCCESS";
    }

    /**
     * 停用/启用/默认题库
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public String statusQuestionBank(PageData pageData) throws Exception {
        String status = pageData.getString("status");
        if (status.equals("666")) {
            dao.update("ExamMapper.statusQuestionBankByDefault", pageData);
            dao.update("ExamMapper.statusQuestionBankByDefaultOnt", pageData);
        } else {
            dao.update("ExamMapper.statusQuestionBank", pageData);
            dao.update("ExamMapper.statusQuestionBankByTopic", pageData);
        }
        return "SUCCESS";
    }

    /**
     * 重命名题库
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public String remarkQuestionBank(PageData pageData) throws Exception {
        List<PageData> pd = (List<PageData>) dao.findForList("ExamMapper.queryQuestionBankOneRemark", pageData);
        if (pd.size() != 0) {
            List<String> listName = new ArrayList<>();
            for (PageData data : pd) {
                listName.add(data.getString("name"));
            }
            if (listName.contains(pageData.getString("name"))) {
                return "WARNING";
            }
        }
        dao.update("ExamMapper.remarkQuestionBank", pageData);
        return "SUCCESS";
    }

    /**
     * 添加试题
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public String addOneTopic(PageData pageData) throws Exception {
        List<TopicVo> topic = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(pageData.getString("topic"));
        for (Object jsonObject : jsonArray) {
            TopicVo topicVo = JSONObject.parseObject(jsonObject.toString(), TopicVo.class);
            topic.add(topicVo);
        }
        List<AnswerVo> answer = new ArrayList<>();
        JSONArray jsonArrayByAnswer = JSONArray.parseArray(pageData.getString("topicByAnswer"));
        for (Object jsonObject : jsonArrayByAnswer) {
            AnswerVo answerVo = JSONObject.parseObject(jsonObject.toString(), AnswerVo.class);
            answer.add(answerVo);
        }
        //判断是否有题重复
        List<String> stemNameList = (List<String>) dao.findForList("ExamMapper.queryStemNameList", pageData.get("companyId"));
        String repeatStem = "";
        Integer index = 0;
        for (TopicVo topicVo : topic) {
            index += 1;
            if (stemNameList.contains(topicVo.getStem())) {
                repeatStem += index + ",";
            }
        }
        if (!StringUtils.isEmpty(repeatStem)) {
            return repeatStem;
        }
        dao.batchSave("ExamMapper.addOneTopic", topic);
        dao.batchSave("ExamMapper.addOneTopicByAnswer", answer);
        return "SUCCESS";
    }

    /**
     * 编辑试题
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public String editOneTopic(PageData pageData) throws Exception {
        TopicVo topicVo = null;
        JSONArray jsonArray = JSONArray.parseArray(pageData.getString("stem"));
        for (Object jsonObject : jsonArray) {
            topicVo = JSONObject.parseObject(jsonObject.toString(), TopicVo.class);
        }
        List<Answer> answer = new ArrayList<>();
        JSONArray jsonArrayByAnswer = JSONArray.parseArray(pageData.getString("answer"));
        for (Object jsonObject : jsonArrayByAnswer) {
            Answer answerVo = JSONObject.parseObject(jsonObject.toString(), Answer.class);
            answer.add(answerVo);
        }
        //判断是否有题重复
        String stemName = (String) dao.findForObject("ExamMapper.queryExamName", topicVo.getId());
        if (!stemName.equals(topicVo.getStem())) {
            List<String> stemNameList = (List<String>) dao.findForList("ExamMapper.queryStemNameList", pageData.get("companyId"));
            if (stemNameList.contains(topicVo.getStem())) {
                return "WARNING";
            }
        }
        dao.update("ExamMapper.updateOneStem", topicVo);
        dao.batchUpdate("ExamMapper.updateOneTopicByAnswer", answer);
        return "SUCCESS";
    }

    /**
     * 查询试题
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public Topic queryOneTopic(PageData pageData) throws Exception {
        Topic stem = (Topic) dao.findForObject("ExamMapper.queryOneTopic", pageData.get("id"));
        return stem;
    }

    /**
     * 批量添加试题
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public String addBatckTopic(PageData pageData) throws Exception {
        try {
            List<TopicVo> topic = new ArrayList<>();
            JSONArray jsonArray = JSONArray.parseArray(pageData.getString("topic"));
            for (Object jsonObject : jsonArray) {
                TopicVo topicVo = JSONObject.parseObject(jsonObject.toString(), TopicVo.class);
                topic.add(topicVo);
            }
            List<AnswerVo> answer = new ArrayList<>();
            JSONArray jsonArrayByAnswer = JSONArray.parseArray(pageData.getString("topicByAnswer"));
            for (Object jsonObject : jsonArrayByAnswer) {
                AnswerVo answerVo = JSONObject.parseObject(jsonObject.toString(), AnswerVo.class);
                answer.add(answerVo);
            }
            List<BatckVo> batck = new ArrayList<>();
            JSONArray jsonArrayByBatck = JSONArray.parseArray(pageData.getString("excel"));
            for (Object jsonObject : jsonArrayByBatck) {
                BatckVo batckVo = JSONObject.parseObject(jsonObject.toString(), BatckVo.class);
                batck.add(batckVo);
            }
            dao.batchSave("ExamMapper.addBatckTopic", batck);
            dao.batchSave("ExamMapper.addOneTopic", topic);
            dao.batchSave("ExamMapper.addOneTopicByAnswer", answer);
            return "SUCCESS";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    /**
     * 查询题库详情
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public PageData queryBankDetail(PageData pageData) throws Exception {
        List<PageData> bankList = (List<PageData>) dao.findForList("ExamMapper.queryBankDetail", pageData);

        for (PageData data : bankList) {
            PageData pd = new PageData();
            pd.put("bankId", data.getString("id"));
            pd.put("categoryId", pageData.getString("id"));
            List<PageData> list = (List<PageData>) dao.findForList("ExamMapper.queryExcelCount", pd);
            if (list != null) {
                for (PageData pageData1 : list) {
                    switch (pageData1.getString("type")) {
                        case "radio":
                            data.put("radio", pageData1.get("value"));
                            break;
                        case "checkbox":
                            data.put("checkbox", pageData1.get("value"));
                            break;
                        case "judge":
                            data.put("judge", pageData1.get("value"));
                            break;
                    }
                }
            }
        }
        List<PageData> oneList = (List<PageData>) dao.findForList("ExamMapper.queryBankOneList", pageData);
        PageData pd = new PageData();
        pd.put("bankList", bankList);
        pd.put("oneList", oneList);
        return pd;
    }


    /**
     * 题库Excel停用/启用/删除
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public String updateExcelStatus(PageData pageData) throws Exception {
        String status = pageData.getString("status");
        dao.update("ExamMapper.updateExcelStatus", pageData);
        if (status.equals("0")) {
            pageData.put("id", pageData.getString("categoryId"));
            dao.update("ExamMapper.statusQuestionBank", pageData);
        }
        return "SUCCESS";
    }

    /**
     * 单题停用/启用/删除
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public String updateOneTopicStatus(PageData pageData) throws Exception {
        dao.update("ExamMapper.updateOneTopicStatus", pageData);
        return "SUCCESS";
    }

    /**
     * 获取用户名以及考号
     *
     * @param pageData
     * @return
     * @throws Exception
     */
    public PageData queryUserByNameAndStuId(PageData pageData) throws Exception {
        PageData pd = (PageData) dao.findForObject("ExamMapper.queryUserByNameAndStuId", pageData);
        return pd;
    }

    /**
     * 发布考试
     *
     * @param pageData
     * @return
     */
    public String releaseExam(PageData pageData) throws Exception {
        ExamVo examVo = JSONObject.parseObject(pageData.getString("params"), ExamVo.class);
        Integer flag = examVo.getIsFlag();
        //名称是否重复
        if (flag == 0) {
            PageData forObject = (PageData) dao.findForObject("ExamMapper.queryExamToName", examVo);
            if (forObject != null) {
                return "考试名称重复";
            }
        }
        String examId = UuidUtil.get32UUID();
        examVo.setId(examId);
        int size = examVo.getUserIds().size();
        examVo.setUnfinish_user(size + "");
        List<examType> examTypeList = examVo.getExamType();
        //单选题数
        Integer radio = 0;
        //多选题数
        Integer checkbox = 0;
        //判断题数
        Integer judge = 0;
        for (examType examType : examTypeList) {
            switch (examType.getType()) {
                case 0:
                    radio = examType.getCount();
                    break;
                case 1:
                    checkbox = examType.getCount();
                    break;
                default:
                    judge = examType.getCount();
                    break;
            }
            examType.setId(UuidUtil.get32UUID());
            examType.setExamId(examId);
        }
        //抽题
        PageData sample = sample(examVo.getExamIds(), radio, checkbox, judge);
        Integer isFlag = (Integer) sample.get("isFlag");
        if (isFlag == 1) {
            return sample.getString("message");
        }
        //随机单选题数
        List<String> radioTopicCount = (List<String>) sample.get("radioTopicCount");
        //随机多选题数
        List<String> checkboxTopicCount = (List<String>) sample.get("checkboxTopicCount");
        //随机判断题数
        List<String> judgeTopicCount = (List<String>) sample.get("judgeTopicCount");
        //保存试卷
        dao.save("ExamMapper.saveExam", examVo);
        //保存试题类型
        dao.batchSave("ExamMapper.saveExamType", examTypeList);
        //保存试题和题库的中间表
        List<PageData> p = new ArrayList<>();
        for (String id : examVo.getExamIds()) {
            PageData pp = new PageData();
            pp.put("examId", examId);
            pp.put("id", id);
            p.add(pp);
        }
        dao.batchSave("ExamMapper.saveExamByCategory", p);
        //考生
        List<String> userIds = examVo.getUserIds();
        List<PageData> userPage = new ArrayList<>();
        for (String userId : userIds) {
            PageData userExam = new PageData();
            userExam.put("id", UuidUtil.get32UUID());
            userExam.put("exam_id", examId);
            userExam.put("user_id", userId);
            //得分
            userExam.put("score", 0);
            //扣分
            userExam.put("etc_score", 0);
            //答对题总数
            userExam.put("right_topic", 0);
            //答错题总数
            userExam.put("bad_topic", 0);
            //考试次数
            userExam.put("exam_count", examVo.getExamCount());
            //答题次数
            userExam.put("topic_count", 1);
            //最近答题时间
            userExam.put("created_time", null);
            //0未开始,1已开始,2进行中,3暂停中,4再次答题,5查看错题,6进行中,7合格,8不合格
            long starTime = examVo.getStartTime().getTime();
            if (starTime > System.currentTimeMillis()) {
                userExam.put("status", 0);
            } else {
                userExam.put("status", 1);
            }
            //0有效,1无效
            userExam.put("is_flag", 0);
            userPage.add(userExam);
        }
        //保存考生试题
        dao.batchSave("ExamMapper.saveExamUser", userPage);
        List<PageData> examUserToTopicList = new ArrayList<>();
        //存题
        for (PageData user : userPage) {
            for (String radioTopic : radioTopicCount) {
                PageData examUserToTopic = new PageData();
                examUserToTopic.put("id", UuidUtil.get32UUID());
                examUserToTopic.put("exam_user_id", user.getString("id"));
                examUserToTopic.put("exam_topic_id", radioTopic);
                examUserToTopic.put("exam_id", examId);
                examUserToTopic.put("answer", null);
                examUserToTopic.put("update_time", null);
                examTypeList.stream().filter(entry -> entry.getType() == 0).forEach(val -> examUserToTopic.put("score", val.getScore()));
                examUserToTopic.put("status", 2);
                examUserToTopicList.add(examUserToTopic);
            }
            for (String checkboxTopic : checkboxTopicCount) {
                PageData examUserToTopic = new PageData();
                examUserToTopic.put("id", UuidUtil.get32UUID());
                examUserToTopic.put("exam_user_id", user.getString("id"));
                examUserToTopic.put("exam_topic_id", checkboxTopic);
                examUserToTopic.put("exam_id", examId);
                examUserToTopic.put("answer", null);
                examUserToTopic.put("update_time", null);
                examTypeList.stream().filter(entry -> entry.getType() == 1).forEach(val -> examUserToTopic.put("score", val.getScore()));
                examUserToTopic.put("status", 2);
                examUserToTopicList.add(examUserToTopic);
            }
            for (String judgeTopic : judgeTopicCount) {
                PageData examUserToTopic = new PageData();
                examUserToTopic.put("id", UuidUtil.get32UUID());
                examUserToTopic.put("exam_user_id", user.getString("id"));
                examUserToTopic.put("exam_topic_id", judgeTopic);
                examUserToTopic.put("exam_id", examId);
                examUserToTopic.put("answer", null);
                examUserToTopic.put("update_time", null);
                examTypeList.stream().filter(entry -> entry.getType() == 2).forEach(val -> examUserToTopic.put("score", val.getScore()));
                examUserToTopic.put("status", 2);
                examUserToTopicList.add(examUserToTopic);
            }
        }
        dao.batchSave("ExamMapper.saveExamUserToTopic", examUserToTopicList);
        //把题存入redis
        List<String> topic = new LinkedList<>();
        topic.addAll(radioTopicCount);
        topic.addAll(checkboxTopicCount);
        topic.addAll(judgeTopicCount);
        //拿到答题卡
        List<AnswerSheet> answerSheet = (List<AnswerSheet>) dao.findForList("ExamMapper.answerSheet", topic);
        //排序
        answerSheet.stream().forEach(item -> item.getAnswerTopicList().stream().sorted(Comparator.comparing(AnswerTopic::getForder)).collect(Collectors.toList()));
        /**
         * {
         * 					id: 652,
         * 					title: '黄金持续上涨，因此2020年鼠年金币的发行价格相对去年上涨30%是合理的',
         * 					answer: 'B',
         * 					according: '这是题目依据这是题目依据',
         * 					type: 1,
         * 				    user_topic_id:答题卡id,
         * 					score: '5',
         * 				update_time:答题时间
         * 					optionList: [
         *                                                {
         * 							optionId: 2469,
         * 							id: 'A',
         * 							content: '正确',
         * 						    isFlag:'0'答案
         *                        },
         *                        {
         * 							optionId: 2470,
         * 							id: 'B',
         * 							content: '错误',
         * 						isFlag:'1'不是答案
         *                        }
         * 					],
         * 					userAnswer: ''
         * 				isFlag:	考试是否异常中止/考试结束
         *                }
         */
        for (PageData user : userPage) {
            //存入用户的答题卡
            List<PageData> answerSheetList = new ArrayList<>();
            Map<Integer, List<PageData>> userAnswerTopicMap = new HashMap<Integer, List<PageData>>();
            for (AnswerSheet sheet : answerSheet) {
                PageData answerTopic = new PageData();
                answerTopic.put("exam_user_id", user.getString("id"));
                answerTopic.put("exam_id", examId);
                examUserToTopicList.stream().filter(item -> item.get("exam_user_id").equals(user.getString("id")) && item.get("exam_topic_id").equals(sheet.getId())).forEach(entry -> {
                    answerTopic.put("exam_user_topic_id", entry.get("id"));
                });
                //存入答题id
                answerTopic.put("title", sheet.getStem());
                answerTopic.put("according", StringUtils.isEmpty(sheet.getQuote()) == true ? "" : sheet.getQuote());
                answerTopic.put("type", sheet.getType());
                switch (Integer.parseInt(sheet.getType().toString())) {
                    case 1:
                        for (examType examType : examTypeList) {
                            if (examType.getType() == 0) {
                                answerTopic.put("score", examType.getScore());
                            }
                        }
                        break;
                    case 2:
                        for (examType examType : examTypeList) {
                            if (examType.getType() == 1) {
                                answerTopic.put("score", examType.getScore());
                            }
                        }
                        break;
                    default:
                        for (examType examType : examTypeList) {
                            if (examType.getType() == 2) {
                                answerTopic.put("score", examType.getScore());
                            }
                        }
                        break;
                }
                List<AnswerTopic> answerTopicList = sheet.getAnswerTopicList();
                List<PageData> answerTopicListVo = new ArrayList<>();
                String answer = "";
                for (AnswerTopic answerTopicVo : answerTopicList) {
                    if (answerTopicVo.getIsFlag() == 0) {
                        answer += answerTopicVo.getForder();
                    }
                    PageData topicVo = new PageData();
                    topicVo.put("optionId", answerTopicVo.getAnswerId());
                    topicVo.put("id", answerTopicVo.getForder());
                    topicVo.put("isFlag", answerTopicVo.getIsFlag());
                    topicVo.put("content", answerTopicVo.getContent());
                    answerTopicListVo.add(topicVo);
                }
                answerTopic.put("answer", answer);
                answerTopic.put("userAnswer", "");
                answerTopic.put("update_time", "");
                answerTopic.put("is_hash", examVo.getQualifiedScore());
                answerTopic.put("optionList", answerTopicListVo);
                //考试是否异常中止/考试结束
                answerTopic.put("isFlag", EXAM_NORMAL);
                answerTopic.put("isStatus", EXAM_NORMAL);
                //考试说明
                answerTopic.put("remarks", examVo.getRemarks());
                //考试时间
                answerTopic.put("examTime", examVo.getLongTime() * 60);
                //考试时间
                answerTopic.put("examTimeNew", examVo.getLongTime() * 60);
                //考试暂停时间
                answerTopic.put("pauseTime", 0);
                //答案是否正确 0正确;1错误;2未开始
                answerTopic.put("right", 0);
                //试题来源
                answerTopic.put("source", sheet.getName());
                //考试次数
                answerTopic.put("exam_count", examVo.getExamCount());
                //答题次数
                answerTopic.put("topic_count", 1);
                //题干id
                answerTopic.put("exam_topic_id", sheet.getId());
                //考试名称
                answerTopic.put("examName", examVo.getName());
                //考试总分
                answerTopic.put("totalScore", examVo.getTotalScore());
                //考试题数
                answerTopic.put("totalCount", topic.size());
                answerSheetList.add(answerTopic);
            }
            String redisHashKey = user.getString("user_id") + USER_SINGLE_ANSWER_TOPIC;
            userAnswerTopicMap.put(0, answerSheetList);
            if (redisTemplate.opsForHash().hasKey(redisHashKey, examId)) {
                userAnswerTopicMap.putAll((Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, examId));
            }
            redisTemplate.opsForHash().put(redisHashKey, examId, userAnswerTopicMap);
        }
        //redis中添加添加用户
        redisTemplate.opsForValue().set(examId, examVo.getUserIds());
        //发消息给中间件,考核超时处理
        int delay = timeDifference(examVo.getStartTime(), examVo.getEndTime());
        rabbitmqPublish.sendTimeoutMsg(examId + "/1", "examTimeout", delay);
        if (flag == 1) {
            return examId;
        }
        return "SUCCESS";
    }

    /**
     * 随机抽题
     *
     * @param examList
     * @return
     */
    public PageData sample(List<String> examList, Integer radio, Integer checkbox, Integer judge) throws Exception {
        PageData pageData = new PageData();
        //抽题
        List<PageData> topicList = (List<PageData>) dao.findForList("ExamMapper.selectedTopic", examList);
        //单选题库
        List<String> radioTopic = new LinkedList<>();
        //多选题库
        List<String> checkboxTopic = new LinkedList<>();
        //判断题库
        List<String> judgeTopic = new LinkedList<>();
        for (PageData data : topicList) {
            Integer type = (Integer) data.get("type");
            if (type == 1) {
                radioTopic.add(data.getString("id"));
            } else if (type == 2) {
                checkboxTopic.add(data.getString("id"));
            } else if (type == 3) {
                judgeTopic.add(data.getString("id"));
            }
        }
        if (radio > radioTopic.size()) {
            pageData.put("isFlag", 1);
            pageData.put("type", 0);
            pageData.put("message", "单选题,题库数量不足");
            return pageData;
        }
        if (checkbox > checkboxTopic.size()) {
            pageData.put("isFlag", 1);
            pageData.put("type", 1);
            pageData.put("message", "多选题,题库数量不足");
            return pageData;
        }
        if (judge > judgeTopic.size()) {
            pageData.put("isFlag", 1);
            pageData.put("type", 2);
            pageData.put("message", "判断题,题库数量不足");
            return pageData;
        }
        //随机单选题数
        List<String> radioTopicCount = randomTopic(radioTopic, radio);
        //随机多选题数
        List<String> checkboxTopicCount = randomTopic(checkboxTopic, checkbox);
        //随机判断题数
        List<String> judgeTopicCount = randomTopic(judgeTopic, judge);
        pageData.put("isFlag", 10);
        pageData.put("type", 10);
        pageData.put("radioTopicCount", radioTopicCount);
        pageData.put("checkboxTopicCount", checkboxTopicCount);
        pageData.put("judgeTopicCount", judgeTopicCount);
        return pageData;
    }

    /**
     * 计算两个时间的差值
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public int timeDifference(Date startTime, Date endTime) {
        int value = 0;
        try {
            long diff = endTime.getTime() - startTime.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            value = new Long(days * 86400000 + hours * 3600000 + minutes * 60000).intValue();
        } catch (Exception e) {
        }
        return value;
    }


    /**
     * 发布考试
     *
     * @param pd
     * @return
     */
    public List<PageData> examineRange(PageData pd) throws Exception {
        List<PageData> pageData = (List<PageData>) dao.findForList("ExamMapper.examineRange", pd);
        return pageData;
    }


    /**
     * 查询考试列表
     *
     * @param pageData
     * @return
     */
    public PageInfo queryExamList(PageData pageData) throws Exception {
        PageVo pageVo = new PageVo();
        pageVo.setPage(pageData.get("page").toString());
        pageVo.setSize(pageData.get("size").toString());
        pageVo.setUid(pageData.get("uid").toString());
        pageVo.setUid(pageData.get("uid").toString());
        PageHelper.startPage(Integer.parseInt(pageVo.getPage()), Integer.parseInt(pageVo.getSize()));
        List<PageData> data = (List<PageData>) dao.findForList("ExamMapper.queryExamList", pageVo);
        return new PageInfo(data);
    }


    /**
     * 查询我发起的考试列表
     *
     * @param pageData
     * @return
     */
    public PageInfo queryExamListRoleLaunch(PageData pageData) throws Exception {
        PageVo pageVo = new PageVo();
        pageVo.setPage(pageData.get("page").toString());
        pageVo.setSize(pageData.get("size").toString());
        pageVo.setSearch(pageData.get("search").toString());
        pageVo.setUid(pageData.get("uid").toString());
        PageHelper.startPage(Integer.parseInt(pageVo.getPage()), Integer.parseInt(pageVo.getSize()));
        List<PageData> data = (List<PageData>) dao.findForList("ExamMapper.queryExamListRoleLaunch", pageVo);
        return new PageInfo(data);
    }


    /**
     * 删除考试
     *
     * @param pageData
     * @return
     */
    public String deleteExam(PageData pageData) throws Exception {
        //修改主表
        String id = pageData.getString("id");
        dao.update("ExamMapper.deleteExams", id);
        //删除用户考试表
        //dao.update("ExamMapper.deleteExamUser", id);
        //修改redis中的数据
        List<String> userId = (List<String>) redisTemplate.opsForValue().get(id);
        for (String uid : userId) {
            String redisHashKey = uid + USER_SINGLE_ANSWER_TOPIC;
            Map<Integer, List<PageData>> data = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, id);
            for (Map.Entry<Integer, List<PageData>> mapEntry : data.entrySet()) {
                for (PageData listEntry : mapEntry.getValue()) {
                    listEntry.put("isStatus", EXAM_DELETE);
                }
            }
            redisTemplate.opsForHash().put(redisHashKey, id, data);
        }

        return "SUCCESS";
    }


    /**
     * 停用/启用考试
     *
     * @param pageData
     * @return
     */
    public String examStopToEnable(PageData pageData) throws Exception {
        Integer status = Integer.parseInt(pageData.getString("status"));
        //修改redis中的数据
        String id = pageData.getString("id");
        String uida = pageData.getString("uid");
        List<String> userId = (List<String>) redisTemplate.opsForValue().get(id);
        switch (status) {
            case 0:
                //恢复操作
                //恢复主表
                dao.update("ExamMapper.deleteExamsss", id);
                //恢复子表
                //1):查询之前的状态
                List<PageData> data = (List<PageData>) dao.findForList("ExamMapper.queryExamUserStatus", id);
                List<PageData> userStatusList = new ArrayList<>();
                for (PageData entry : data) {
                    PageData userStatus = new PageData();
                    userStatus.put("id", entry.getString("id"));
                    userStatus.put("status", entry.get("retain"));
                    userStatusList.add(userStatus);
                }
                dao.batchUpdate("ExamMapper.deleteExamUserss", userStatusList);
                for (String uid : userId) {
                    String redisHashKey = uid + USER_SINGLE_ANSWER_TOPIC;
                    Map<Integer, List<PageData>> dataList = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, id);
                    for (Map.Entry<Integer, List<PageData>> listEntry : dataList.entrySet()) {
                        for (PageData entry : listEntry.getValue()) {
                            entry.put("isStatus", entry.get("isFlag"));
                        }
                    }
                    redisTemplate.opsForHash().put(redisHashKey, id, dataList);
                }
                break;
            default:
                //停用操作
                //修改主表
                dao.update("ExamMapper.deleteExamss", id);
                //修改子表
                //1):查询之前的状态
                List<PageData> data1 = (List<PageData>) dao.findForList("ExamMapper.queryExamUserStatus", id);
                List<PageData> userStatusList1 = new ArrayList<>();
                for (PageData entry : data1) {
                    PageData userStatus = new PageData();
                    userStatus.put("id", entry.get("id"));
                    userStatus.put("retain", entry.get("status"));
                    userStatus.put("status", 3);
                    userStatusList1.add(userStatus);
                }
                dao.batchUpdate("ExamMapper.deleteExamUsers", userStatusList1);
                for (String uid : userId) {
                    String redisHashKey = uid + USER_SINGLE_ANSWER_TOPIC;
                    //修改redis中的数据
                    Map<Integer, List<PageData>> dataList1 = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, id);
                    for (Map.Entry<Integer, List<PageData>> listEntry : dataList1.entrySet()) {
                        for (PageData entry : listEntry.getValue()) {
                            entry.put("isStatus", EXAM_SUSPEND);
                        }
                    }
                    redisTemplate.opsForHash().put(redisHashKey, id, dataList1);
                }
                //把正在考试的用户时间给暂停了
                List<PageData> ids = (List<PageData>) dao.findForList("ExamMapper.queryExamUserToId", id);
                for (PageData examUserId : ids) {
                    Long expire = redisTemplate.opsForValue().getOperations().getExpire(examUserId.get("id"), TimeUnit.SECONDS);
                    if (expire != -1 && expire != -2) {
                        String redisHashKey = examUserId.getString("user_id") + USER_SINGLE_ANSWER_TOPIC;
                        Map<Integer, List<PageData>> userToData = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, id);
                        List<PageData> dataList = userToData.get(getMaxKey(userToData));
                        for (PageData item : dataList) {
                            item.put("examTimeNew", expire);
                            //考试时间
                            Long examTime = (Long) item.get("examTimeNew");
                            long time = examTime - expire;
                            Integer pauseTime = (Integer) item.get("pauseTime");
                            long longValue = pauseTime.longValue();
                            Long value = longValue += time;
                            int i = value.intValue();
                            item.put("pauseTime", i);
                        }
                        //标识状态
                        redisTemplate.opsForValue().set(examUserId.getString("user_id") + ExamTimeUp, 1);
                        //存入数据
                        redisTemplate.opsForHash().put(redisHashKey, id, userToData);
                    }
                }
                break;
        }
        return "SUCCESS";
    }

    /**
     * 拉取试题信息
     *
     * @param pd
     * @return
     */
    public List<PageData> startExam(PageData pd) {
        String examId = pd.getString("id");
        String uid = pd.getString("uid");
        String redisHashKey = uid + USER_SINGLE_ANSWER_TOPIC;
        Map<Integer, List<PageData>> data = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, examId);
        Integer maxKey = getMaxKey(data);
        List<PageData> dataList = data.get(maxKey);
        for (PageData pageData : dataList) {
            String redisValueKey = uid + ExamTimeUp;
            Integer flag = (Integer) redisTemplate.opsForValue().get(redisValueKey);
            if (flag == null) {
                Long expire = redisTemplate.opsForValue().getOperations().getExpire(pageData.getString("exam_user_id"), TimeUnit.SECONDS);
                if (expire != -1 && expire != -2 && expire != 0) {
                    pageData.put("examTimeNew", expire);
                }
            }
        }
        return dataList;
    }


    /**
     * 开始考试
     *
     * @param pd
     * @return
     */
    public String startUserExam(PageData pd) throws Exception {
        String examUserId = pd.getString("exam_user_id");
        Integer examTimeNew = Integer.parseInt(pd.getString("examTimeNew"));
        Integer pauseTime = Integer.parseInt(pd.getString("pauseTime"));
        //修改用户试卷信息
        PageData pageData = new PageData();
        pageData.put("id", examUserId);
        pageData.put("status", 2);
        dao.update("ExamMapper.deleteExamUserss", pageData);
        //redis存一个用户单场考试的倒计时
        redisTemplate.opsForValue().set(examUserId, 0, examTimeNew, TimeUnit.SECONDS);
        //发消息给rabbitmq判断考试是否结束
        rabbitmqPublish.sendTimeoutMsg(examUserId + "/2/" + pauseTime, "examUserTimeOut", examTimeNew * 1000);
        //加入考试时间
        dao.update("ExamMapper.updateExamTime",examUserId);
        return "SUCCESS";
    }

    /**
     * 继续考试
     *
     * @param pd
     * @return
     */
    public String continueExamination(PageData pd) throws Exception {
        String uid = pd.getString("uid");
        String examUserId = pd.getString("exam_user_id");
        Integer examTimeNew = Integer.parseInt(pd.getString("examTimeNew"));
        Integer pauseTime = Integer.parseInt(pd.getString("pauseTime"));
        String redisValueKey = uid + ExamTimeUp;
        Integer flag = (Integer) redisTemplate.opsForValue().get(redisValueKey);
        if (flag != null) {
            //删除暂停的值
            redisTemplate.delete(redisValueKey);
            //redis存一个用户单场考试的倒计时
            redisTemplate.opsForValue().set(examUserId, 0, examTimeNew, TimeUnit.SECONDS);
            //发消息给rabbitmq
            rabbitmqPublish.sendTimeoutMsg(examUserId + "/2/" + pauseTime, "examUserTimeOut", examTimeNew * 1000);
        }
        return "SUCCESS";
    }

    /**
     * 开始答题
     *
     * @param pd
     * @return
     */
    public String startUserAnswer(PageData pd) throws Exception {
        String examId = pd.getString("exam_id");
        String uid = pd.getString("uid");
        String exam_user_topic_id = pd.getString("exam_user_topic_id");
        String userAnswer = pd.getString("userAnswer");
        String right = pd.getString("right");
        String redisHashKey = uid + USER_SINGLE_ANSWER_TOPIC;
        Map<Integer, List<PageData>> userData = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, examId);
        List<PageData> dataList = userData.get(getMaxKey(userData));
        for (PageData pageData : dataList) {
            //考试是否正常
            if (!pageData.getString("isStatus").equals(EXAM_NORMAL)) {
                return pageData.getString("isStatus");
            }
            //修改状态
            if (pageData.getString("exam_user_topic_id").equals(exam_user_topic_id)) {
                pageData.put("update_time", new Date());
                pageData.put("userAnswer", userAnswer);
                pageData.put("right", right);
            }
        }
        redisTemplate.opsForHash().put(redisHashKey, examId, userData);
        return "SUCCESS";
    }

    /**
     * 提交试卷
     *
     * @param pd
     * @return
     */
    public PageData submitTestPaper(PageData pd) throws Exception {
        String examId = pd.getString("exam_id");
        String uid = pd.getString("uid");
        String examUserId = pd.getString("exam_user_id");
        String exam_user_id = null;
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
        String type = pd.getString("type");
        //Redis数据同步到MySQL
        String redisHashKey = uid + USER_SINGLE_ANSWER_TOPIC;
        Map<Integer, List<PageData>> data = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, examId);
        List<PageData> dataList = data.get(getMaxKey(data));
        String isStatus = (String) dataList.get(0).get("isStatus");
        if (EXAM_COMPLETE.equals(isStatus)) {
            //返回考试结果
            for (PageData pageData : dataList) {
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
            }
            PageData resultData = new PageData();
            resultData.put("score", score);
            resultData.put("totalScore", score + etc_score);
            resultData.put("rightScore", rightScore);
            resultData.put("status", score >= rightScore ? 0 : 1);
            exam_count = (Integer) dataList.get(0).get("exam_count");
            topic_count = (Integer) dataList.get(0).get("topic_count");
            resultData.put("isFlag", exam_count > topic_count ? 1 : 0);
            resultData.put("duration", dataList.get(0).get("examTime"));
            resultData.put("right_topic", right_topic);
            resultData.put("bad_topic", bad_topic);
            return resultData;
        }
        List<PageData> userAnswerDataList = new ArrayList<PageData>();
        for (PageData pageData : dataList) {
            pageData.put("isStatus", EXAM_COMPLETE);
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
        //返回考试结果
        PageData resultData = new PageData();
        resultData.put("score", score);
        resultData.put("totalScore", score + etc_score);
        resultData.put("rightScore", rightScore);
        resultData.put("status", score >= rightScore ? 0 : 1);
        resultData.put("isFlag", isFlag);
        resultData.put("duration", duration);
        resultData.put("right_topic", right_topic);
        resultData.put("bad_topic", bad_topic);
        return resultData;
    }


    /**
     * 获取答案考试结果
     *
     * @param pd
     * @return
     */
    public PageData answers(PageData pd) {
        String examId = pd.getString("exam_id");
        String uid = pd.getString("uid");
        String examUserId = pd.getString("exam_user_id");
        String redisHashKey = uid + USER_SINGLE_ANSWER_TOPIC;
        Map<Integer, List<PageData>> data = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, examId);
        List<PageData> dataList = data.get(getMaxKey(data));
        //返回考试结果
        PageData resultData = new PageData();
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
        //返回考试结果
        for (PageData pageData : dataList) {
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
        }
        resultData.put("score", score);
        resultData.put("totalScore", score + etc_score);
        resultData.put("rightScore", rightScore);
        resultData.put("status", score >= rightScore ? 0 : 1);
        exam_count = (Integer) dataList.get(0).get("exam_count");
        topic_count = (Integer) dataList.get(0).get("topic_count");
        resultData.put("isFlag", exam_count > topic_count ? 1 : 0);
        resultData.put("duration", dataList.get(0).get("examTime"));
        resultData.put("right_topic", right_topic);
        resultData.put("bad_topic", bad_topic);
        return resultData;
    }

    /**
     * 再次答题
     *
     * @param pageData
     * @return
     */
    public String questionAgain(PageData pageData) throws Exception {
        String examId = pageData.getString("examId");
        String examUserId = pageData.getString("examUserId");
        String uid = pageData.getString("uid");
        String redisHashKey = uid + USER_SINGLE_ANSWER_TOPIC;
        //修改MySQL的值
        UserToAnswer data = (UserToAnswer) dao.findForObject("ExamMapper.queryUserToAnswerList", examUserId);
        UserToAnswer userToAnswer = new UserToAnswer();
        String userToAnswerId = UuidUtil.get32UUID();
        userToAnswer.setId(userToAnswerId);
        userToAnswer.setExam_id(examId);
        userToAnswer.setUser_id(uid);
        userToAnswer.setScore(0);
        userToAnswer.setEtc_score(0);
        userToAnswer.setRight_topic(0);
        userToAnswer.setBad_topic(0);
        userToAnswer.setExam_count(data.getExam_count());
        userToAnswer.setTopic_count(data.getTopic_count() + 1);
        userToAnswer.setCreated_time(new Date());
        userToAnswer.setType(null);
        userToAnswer.setRetain(null);
        userToAnswer.setDuration(null);
        userToAnswer.setStatus(1);
        userToAnswer.setIs_flag(0);
        userToAnswer.setIs_hash(null);
        List<UserTopic> answerList = data.getAnswerList();
        //新的答题卡
        List<UserTopic> newAnswerList = new ArrayList<>();
        for (UserTopic answer : answerList) {
            UserTopic answerVo = new UserTopic();
            String answerId = UuidUtil.get32UUID();
            answerVo.setId(answerId);
            answerVo.setExam_user_id(userToAnswerId);
            answerVo.setExam_topic_id(answer.getExam_topic_id());
            answerVo.setAnswer(null);
            answerVo.setUpdate_time(null);
            answerVo.setStatus(2);
            answerVo.setScore(answer.getScore());
            newAnswerList.add(answerVo);
        }
        //修改之前的试卷
        dao.update("ExamMapper.updateUserExamStatus", examUserId);
        //加入新的试卷
        dao.save("ExamMapper.saveUserToExam", userToAnswer);
        //加入新的答题卡
        dao.batchSave("ExamMapper.saveUserToExamAndTopic", newAnswerList);
        //修改redis的值
        Map<Integer, List<PageData>> dataLod = new HashMap<Integer, List<PageData>>();
        Map<Integer, List<PageData>> dataList = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, examId);
        dataLod.putAll(dataList);
        //修改状态
        for (PageData pageData1 : dataLod.get(getMaxKey(dataLod))) {
            pageData1.put("isStatus", EXAM_COMPLETE);
        }
        //添加新的
        Integer maxKey = getMaxKey(dataList);
        List<PageData> list = dataList.get(maxKey);
        for (PageData answerTopic : list) {
            answerTopic.put("exam_user_id", userToAnswerId);
            answerTopic.put("exam_id", examId);
            newAnswerList.stream().filter(item -> item.getExam_topic_id().equals(answerTopic.get("exam_topic_id"))).forEach(entry -> {
                answerTopic.put("exam_user_topic_id", entry.getId());
            });
            answerTopic.put("userAnswer", "");
            answerTopic.put("update_time", "");
            answerTopic.put("is_hash", answerTopic.get("is_hash"));
            //考试是否异常中止/考试结束
            answerTopic.put("isFlag", EXAM_NORMAL);
            answerTopic.put("isStatus", EXAM_NORMAL);
            //考试说明
            answerTopic.put("remarks", answerTopic.get("remarks"));
            //考试时间
            answerTopic.put("examTime", answerTopic.get("examTime"));
            //考试时间
            answerTopic.put("examTimeNew", answerTopic.get("examTime"));
            //考试暂停时间
            answerTopic.put("pauseTime", 0);
            //答案是否正确 0正确;1错误;2未开始
            answerTopic.put("right", 2);
            //考试次数
            answerTopic.put("exam_count", answerTopic.get("exam_count"));
            //答题次数
            answerTopic.put("topic_count", userToAnswer.getTopic_count());
        }
        dataLod.put(maxKey + 1, list);
        redisTemplate.opsForHash().put(redisHashKey, examId, dataLod);
        return examId;
    }

    /**
     * 查看错题
     *
     * @param pageData
     * @return
     */
    public List<PageData> wrongQuestions(PageData pageData) {
        String examId = pageData.getString("id");
        String uid = pageData.getString("ids");
        String redisHashKey = uid + USER_SINGLE_ANSWER_TOPIC;
        Map<Integer, List<PageData>> userData = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, examId);
        List<PageData> dataList = userData.get(getMaxKey(userData));
        List<PageData> data = dataList.stream().filter(entry -> entry.getString("right").equals("1")).collect(Collectors.toList());
        return data;
    }

    /**
     * 历史成绩
     *
     * @param pageData
     * @return
     */
    public List<PageData> historicalAchievements(PageData pageData) throws Exception {
        List<PageData> data = (List<PageData>) dao.findForList("ExamMapper.historicalAchievements", pageData);
        return data;
    }

    /**
     * 考试练习
     *
     * @param pageData
     * @return
     */
    public String examPractice(PageData pageData) throws Exception {
        //题库
        String[] categoryIds = pageData.getString("category_id").split(",");
        //题目类型
        String[] types = pageData.getString("type").split(",");
        //题目数量
        Integer count = Integer.parseInt(pageData.getString("count"));
        //单选题数
        Integer radio = 0;
        //多选题数
        Integer checkbox = 0;
        //判断题数
        Integer judge = 0;
        //计算单题多少个
        int examSize = count % types.length;
        if (examSize == 0) {
            int size = count / types.length;
            for (String type : types) {
                if (type.equals("0")) {
                    radio += size;
                } else if (type.equals("1")) {
                    checkbox += size;
                } else {
                    judge += size;
                }
            }
        } else {
            int size = (count - examSize) / types.length;
            for (String type : types) {
                if (type.equals("0")) {
                    radio += size;
                } else if (type.equals("1")) {
                    checkbox += size;
                } else {
                    judge += size;
                }
            }
            int node = 0;
            jj:
            for (int i = 0; i < examSize; i++) {
                for (String type : types) {
                    if (type.equals("0")) {
                        if (node == examSize) {
                            break jj;
                        }
                        radio += 1;
                        node += 1;
                        if (examSize == i + 1) {
                            break jj;
                        }
                    } else if (type.equals("1")) {
                        if (node == examSize) {
                            break jj;
                        }
                        checkbox += 1;
                        node += 1;
                        if (examSize == i + 1) {
                            break jj;
                        }
                    } else {
                        if (node == examSize) {
                            break jj;
                        }
                        judge += 1;
                        node += 1;
                        if (examSize == i + 1) {
                            break jj;
                        }
                    }
                }
            }
        }
        String uid = pageData.getString("uid");
        String companyId = pageData.getString("companyId");
        List<examType> examTypeList = new ArrayList<>();
        for (String type : types) {
            if (type.equals("0")) {
                examType examType = new examType();
                examType.setType(0);
                examType.setScore(0);
                examType.setCount(radio);
                examTypeList.add(examType);
            } else if (type.equals("1")) {
                examType examType = new examType();
                examType.setType(1);
                examType.setScore(0);
                examType.setCount(checkbox);
                examTypeList.add(examType);
            } else {
                examType examType = new examType();
                examType.setType(2);
                examType.setScore(0);
                examType.setCount(judge);
                examTypeList.add(examType);
            }
        }
        PageData data = new PageData();
        data.put("name", null);
        data.put("examIds", categoryIds);
        data.put("userIds", new String[]{uid});
        data.put("startTime", new Date());
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        data.put("endTime", c.getTime());
        data.put("longTime", 86400);
        data.put("exam_count", 100);
        data.put("examCount", 100);
        data.put("remarks", null);
        data.put("examType", examTypeList);
        data.put("totalScore", 0);
        data.put("qualifiedScore", 0);
        data.put("totalUser", 1);
        data.put("status", 0);
        data.put("isFlag", 1);
        data.put("userId", uid);
        data.put("companyId", companyId);
        data.put("unfinish_user", 1);
        String string = JSON.toJSONString(data);
        pageData.put("params", string);
        String examId = releaseExam(pageData);
        return examId;
    }

    /**
     * 考试详情头
     *
     * @param pageData
     * @return
     */
    public PageData examDetail(PageData pageData) throws Exception {
        String examId = pageData.getString("examId");
        PageData data = (PageData) dao.findForObject("ExamMapper.examHeadTitle", examId);
        List<String> userIds = (List<String>) redisTemplate.opsForValue().get(examId);
        String redisHashKey = userIds.get(0) + USER_SINGLE_ANSWER_TOPIC;
        Map<Integer, List<PageData>> dataList = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, examId);
        int size = dataList.get(0).size();
        data.put("size", size);
        //算平均分
        PageData score = (PageData) dao.findForObject("ExamMapper.queryExamAvg", examId);
        data.put("average", score.get("score"));
        return data;
    }

    /**
     * 考试详情体带分页
     *
     * @param pageData
     * @return
     */
    public PageInfo examDetailBody(PageData pageData) throws Exception {
        String examId = pageData.getString("examId");
        PageVo pageVo = new PageVo();
        pageVo.setPage(pageData.get("page").toString());
        pageVo.setSize(pageData.get("size").toString());
        pageVo.setUid(pageData.get("uid").toString());
        pageVo.setSearch(pageData.get("search").toString());
        pageVo.setExamId(examId);
        List<String> userList = (List<String>) redisTemplate.opsForValue().get(examId);
        List<String> examUserId = new ArrayList<>();
        for (String uid : userList) {
            pageVo.setUid(uid);
            PageData data = (PageData) dao.findForObject("ExamMapper.examDetailBody", pageVo);
            if (data != null) {
                examUserId.add(data.getString("id"));
            }
        }
        pageVo.setIds(examUserId);
        PageHelper.startPage(Integer.parseInt(pageVo.getPage()), Integer.parseInt(pageVo.getSize()));
        List<PageData> data = (List<PageData>) dao.findForList("ExamMapper.examDetailBodyLimit", pageVo);
        for (PageData datum : data) {
            pageVo.setExamId(datum.getString("exam_id"));
            pageVo.setUid(datum.getString("user_id"));
            PageData forObject = (PageData) dao.findForObject("ExamMapper.queryUserToExamCount", pageVo);
            if (forObject == null) {
                datum.put("size", 0);
            } else {
                datum.put("size", forObject.get("sort"));
            }
        }
        return new PageInfo(data);
    }

    /**
     * 查看试卷
     *
     * @param pageData
     * @return
     */
    public List<PageData> viewPaper(PageData pageData) {
        String examId = pageData.getString("exam_id");
        List<String> userIds = (List<String>) redisTemplate.opsForValue().get(examId);
        String redisHashKey = userIds.get(0) + USER_SINGLE_ANSWER_TOPIC;
        Map<Integer, List<PageData>> dataList = (Map<Integer, List<PageData>>) redisTemplate.opsForHash().get(redisHashKey, examId);
        List<PageData> data = dataList.get(0);
        return data;
    }

    /**
     * 导出成绩
     *
     * @param pageData
     * @param request
     * @return PageData
     * @throws Exception
     */
    public PageData exportResults(PageData pageData, HttpServletRequest request) throws Exception {
        String examId = pageData.getString("exam_id");
        PageVo pageVo = new PageVo();
        pageVo.setUid(pageData.get("uid").toString());
        pageVo.setExamId(examId);
        List<String> userList = (List<String>) redisTemplate.opsForValue().get(examId);
        List<String> examUserId = new ArrayList<>();
        for (String uid : userList) {
            pageVo.setUid(uid);
            PageData data = (PageData) dao.findForObject("ExamMapper.examDetailBody", pageVo);
            if (data != null) {
                examUserId.add(data.getString("id"));
            }
        }
        pageVo.setIds(examUserId);
        List<PageData> data = (List<PageData>) dao.findForList("ExamMapper.exportResults", pageVo);
        for (PageData datum : data) {
            pageVo.setExamId(datum.getString("exam_id"));
            pageVo.setUid(datum.getString("user_id"));
            PageData forObject = (PageData) dao.findForObject("ExamMapper.queryUserToExamCount", pageVo);
            if (forObject == null) {
                datum.put("size", 0);
            } else {
                datum.put("size", forObject.get("sort"));
            }
            //查询历史成绩
            String OffSet = (String) dao.findForObject("ExamMapper.queryUserToExamOffSet", pageVo);
            datum.put("offSet", OffSet);
        }
        String StringXmlHead = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<?mso-application progid=\"Excel.Sheet\"?>\n" +
                "<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
                "          xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"\n" +
                "          xmlns:html=\"http://www.w3.org/TR/REC-html40\" xmlns:dt=\"uuid:C2F41010-65B3-11d1-A29F-00AA00C14882\">\n" +
                "    <DocumentProperties xmlns=\"urn:schemas-microsoft-com:office:office\">\n" +
                "        <Title>任务数据</Title>\n" +
                "        <Subject>凭条数据导出</Subject>\n" +
                "        <Author>程序汪</Author>\n" +
                "        <Description>除验收评价注明之服务条款外，其它因使用验收评价而引致之任何意外、疏忽、合约毁坏、诽谤、版权或知识\n" +
                "            产权侵犯及其所造成的各种损失（包括因下载而感染电脑病毒），验收评价概不负责，亦不承担任何法律责任。\n" +
                "        </Description>\n" +
                "        <LastAuthor>Administrator</LastAuthor>\n" +
                "        <Created>2021-01-13T02:34:00Z</Created>\n" +
                "        <LastSaved>2021-01-26T01:46:57Z</LastSaved>\n" +
                "        <Category>文档</Category>\n" +
                "        <Manager>伍总</Manager>\n" +
                "        <Company>成都积盛电子科技有限公司</Company>\n" +
                "    </DocumentProperties>\n" +
                "   <CustomDocumentProperties\n" +
                "      xmlns='urn:schemas-microsoft-com:office:office'>\n" +
                "      <KSOProductBuildVer dt:dt='string'>2052-11.8.2.9067</KSOProductBuildVer>\n" +
                "   </CustomDocumentProperties>\n" +
                "   <ExcelWorkbook\n" +
                "      xmlns='urn:schemas-microsoft-com:office:excel'>\n" +
                "      <WindowWidth>28245</WindowWidth>\n" +
                "      <WindowHeight>12150</WindowHeight>\n" +
                "      <ProtectStructure>False</ProtectStructure>\n" +
                "      <ProtectWindows>False</ProtectWindows>\n" +
                "   </ExcelWorkbook>\n" +
                "   <Styles>\n" +
                "      <Style ss:ID='Default' ss:Name='Normal'>\n" +
                "         <Alignment ss:Vertical='Bottom'/>\n" +
                "         <Borders/>\n" +
                "         <Font ss:FontName='等线' x:CharSet='134' ss:Size='11' ss:Color='#000000'/>\n" +
                "         <Interior/>\n" +
                "         <NumberFormat/>\n" +
                "         <Protection/>\n" +
                "      </Style>\n" +
                "      <Style ss:ID='s49'>\n" +
                "         <Alignment ss:Vertical='Bottom'/>\n" +
                "         <Borders/>\n" +
                "         <Font ss:FontName='等线' x:CharSet='134' ss:Size='11' ss:Color='#000000'/>\n" +
                "         <Interior/>\n" +
                "         <NumberFormat/>\n" +
                "         <Protection/>\n" +
                "      </Style>\n" +
                "      <Style ss:ID='s50'>\n" +
                "         <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>\n" +
                "         <Borders>\n" +
                "            <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "            <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "            <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "            <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "         </Borders>\n" +
                "         <Font ss:FontName='微软雅黑 Light' x:CharSet='134' ss:Size='20' ss:Color='#000000' ss:Bold='1'/>\n" +
                "      </Style>\n" +
                "      <Style ss:ID='s51'>\n" +
                "         <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>\n" +
                "         <Borders>\n" +
                "            <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "            <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "            <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "            <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "         </Borders>\n" +
                "         <Font ss:FontName='微软雅黑 Light' x:CharSet='134' ss:Size='18' ss:Color='#000000'/>\n" +
                "      </Style>\n" +
                "      <Style ss:ID='s52'>\n" +
                "         <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>\n" +
                "         <Borders>\n" +
                "            <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "            <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "            <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "            <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "         </Borders>\n" +
                "         <Font ss:FontName='微软雅黑 Light' x:CharSet='134' ss:Size='18' ss:Color='#000000'/>\n" +
                "      </Style>\n" +
                "      <Style ss:ID='s53'>\n" +
                "         <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>\n" +
                "         <Borders>\n" +
                "            <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "            <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "            <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "            <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>\n" +
                "         </Borders>\n" +
                "         <Font ss:FontName='微软雅黑 Light' x:CharSet='134' ss:Size='18' ss:Color='#000000'/>\n" +
                "      </Style>\n" +
                "   </Styles>\n" +
                "   <Worksheet ss:Name='" + data.get(0).get("name") + "'>\n" +
                "      <Table ss:ExpandedColumnCount='14' ss:ExpandedRowCount='5' x:FullColumns='1' x:FullRows='1'ss:DefaultColumnWidth='126' ss:DefaultRowHeight='65'>\n" +
                "         <Column ss:Index='1' ss:StyleID='s49' ss:AutoFitWidth='0' ss:Width='291.35'/>\n" +
                "         <Column ss:Index='2' ss:StyleID='s49' ss:AutoFitWidth='0' ss:Width='266.5'/>\n" +
                "         <Column ss:StyleID='s49' ss:AutoFitWidth='0' ss:Width='243.4' ss:Span='1'/>\n" +
                "         <Column ss:Index='5' ss:StyleID='s49' ss:AutoFitWidth='0' ss:Width='129.6' ss:Span='1'/>\n" +
                "         <Column ss:Index='7' ss:StyleID='s49' ss:AutoFitWidth='0' ss:Width='122' ss:Span='4'/>\n" +
                "         <Column ss:Index='12' ss:StyleID='s49' ss:AutoFitWidth='0' ss:Width='243.4'/>\n" +
                "         <Column ss:StyleID='s49' ss:AutoFitWidth='0' ss:Width='222.25'/>\n" +
                "         <Column ss:StyleID='s49' ss:AutoFitWidth='0' ss:Width='136.6'/>\n" +
                "         <Row>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>考试名称</Data>\n" +
                "            </Cell>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>题库</Data>\n" +
                "            </Cell>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>考试开始时间</Data>\n" +
                "            </Cell>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>考试截止时间</Data>\n" +
                "            </Cell>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>考号</Data>\n" +
                "            </Cell>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>考生姓名</Data>\n" +
                "            </Cell>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>得分</Data>\n" +
                "            </Cell>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>及格线</Data>\n" +
                "            </Cell>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>总分</Data>\n" +
                "            </Cell>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>是否合格</Data>\n" +
                "            </Cell>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>考试次数</Data>\n" +
                "            </Cell>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>考试时间</Data>\n" +
                "            </Cell>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>历史成绩</Data>\n" +
                "            </Cell>\n" +
                "            <Cell ss:StyleID='s50'>\n" +
                "               <Data ss:Type='String'>状态</Data>\n" +
                "            </Cell>\n" +
                "         </Row>";
        String StringWe = "";
        String StringRow = "";
        for (PageData datum : data) {
            Integer bad_topic = (Integer) datum.get("bad_topic");
            String name = (String) datum.get("name");
            String categoryName = datum.getString("categoryName");
            Integer size = (Integer) datum.get("size");
            Timestamp ts1 = (Timestamp) datum.get("start_time");
            Timestamp ts2 = (Timestamp) datum.get("end_time");
            Timestamp ts3 = (Timestamp) datum.get("created_time");
            String start_time = sb.format(ts1);
            String end_time = sb.format(ts2);
            Integer stu_id = (Integer) datum.get("stu_id");
            String username = (String) datum.get("username");
            Double score = (Double) datum.get("score");
            Double qualified_score = (Double) datum.get("qualified_score");
            Double total_score = (Double) datum.get("total_score");
            String is_hash = (String) datum.get("is_hash");
            String offSet = (String) datum.get("offSet");
            String createdTime = null;
            if(!StringUtils.isEmpty(ts3)){
                 createdTime =  sb.format(ts3);
            }
            if (bad_topic == 0 && size == 0) {
                StringWe += "\"<Row>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">" + name + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">" + categoryName + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">" + start_time + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">" + end_time + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"Number\">" + stu_id + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">" + username + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s52\" ss:MergeAcross=\"7\">\n" +
                        "      <Data ss:Type=\"String\">未开始</Data>\n" +
                        "   </Cell>\n" +
                        "</Row>\"";
            } else {
                StringRow += "\"\n" +
                        "\n" +
                        "<Row>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">" + datum.get("name") + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">" + categoryName + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">" + start_time + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">" + end_time + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"Number\">" + stu_id + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">" + username + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"Number\">" + score + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"Number\">" + qualified_score + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"Number\">" + total_score + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">" + is_hash + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"Number\">" + size + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">" + createdTime + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">" + offSet + "</Data>\n" +
                        "   </Cell>\n" +
                        "   <Cell ss:StyleID=\"s51\">\n" +
                        "      <Data ss:Type=\"String\">已完成</Data>\n" +
                        "   </Cell>\n" +
                        "</Row>\"";
            }
        }
        String StringLoad = "<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">\n" +
                "            <PageSetup>\n" +
                "                <Header x:Margin=\"0.3\"/>\n" +
                "                <Footer x:Margin=\"0.3\"/>\n" +
                "                <PageMargins x:Left=\"0.7\" x:Right=\"0.7\" x:Top=\"0.75\" x:Bottom=\"0.75\"/>\n" +
                "            </PageSetup>\n" +
                "            <Zoom>54</Zoom>\n" +
                "            <Selected/>\n" +
                "            <TopRowVisible>0</TopRowVisible>\n" +
                "            <LeftColumnVisible>0</LeftColumnVisible>\n" +
                "            <PageBreakZoom>100</PageBreakZoom>\n" +
                "            <Panes>\n" +
                "                <Pane>\n" +
                "                    <Number>3</Number>\n" +
                "                    <ActiveRow>0</ActiveRow>\n" +
                "                    <ActiveCol>13</ActiveCol>\n" +
                "                    <RangeSelection>C14</RangeSelection>\n" +
                "                </Pane>\n" +
                "            </Panes>\n" +
                "            <ProtectObjects>False</ProtectObjects>\n" +
                "            <ProtectScenarios>False</ProtectScenarios>\n" +
                "        </WorksheetOptions>\n" +
                "    </Worksheet>\n" +
                "</Workbook>";
        String XML = StringXmlHead + StringRow + StringWe + StringLoad;
        path = request.getSession().getServletContext().getRealPath(ISSUEIMAGEPATH);
        String fileName = UuidUtil.get32UUID();
        InputStream stream = new ByteArrayInputStream(XML.getBytes(StandardCharsets.UTF_8));
        FileUpload.copyFile(stream, path, fileName + ".xlsx");
        PageData resultData = new PageData();
        resultData.put("url", "/" + ISSUEIMAGEPATH + fileName + ".xlsx");
        resultData.put("fileName", data.get(0).getString("name") + ".xlsx");
        rabbitmqPublish.sendTimeoutMsg(fileName + ".xlsx"+"/1024","fileTimeRemove",60*1000);
        return resultData;

    }


    public List randomTopic(List paramList, int count) {
        //把随机取得的数据存储在 listRandom 中
        List<String> listRandom = new ArrayList<String>();
        //随机取出n条不重复的数据,这里我设置随机取3条数据
        for (int i = count; i >= 1; i--) {
            Random random = new Random();
            Math.random();
            //在数组大小之间产生一个随机数 j
            int j = random.nextInt(paramList.size() - 1);
            //取得list 中下标为j 的数据存储到 listRandom 中
            listRandom.add((String) paramList.get(j));
            //把已取到的数据移除,避免下次再次取到出现重复
            paramList.remove(j);
        }
        return listRandom;
    }

    public Integer getMaxKey(Map<Integer, List<PageData>> map) {
        if (map == null) {
            return null;
        }
        Set<Integer> set = map.keySet();
        Object[] obj = set.toArray();
        Arrays.sort(obj);
        return Integer.parseInt(obj[obj.length - 1].toString());
    }
}
