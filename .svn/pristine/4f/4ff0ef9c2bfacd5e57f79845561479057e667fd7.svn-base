package com.fh.controller.app;

import com.fh.controller.base.BaseController;
import com.fh.entity.app.Topic;
import com.fh.service.app.ExamService;
import com.fh.util.PageData;
import com.fh.util.ResultModel;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
@Api(tags = "题库接口")
public class ExamController extends BaseController {

    @Autowired
    ExamService examService;


    @RequestMapping("/queryQuestionBankList")
    @ApiOperation("题库列表")
    public ResultModel<PageInfo> queryQuestionBankList() throws Exception {
        return ResultModel.success(examService.queryQuestionBankList(this.getPageData()));
    }

    @RequestMapping("/queryQuestionBankListNotLimit")
    @ApiOperation("题库列表不分页")
    public ResultModel<List<PageData>> queryQuestionBankListNotLimit() throws Exception {
        return ResultModel.success(examService.queryQuestionBankListNotLimit(this.getPageData()));
    }

    @RequestMapping("/addQuestionBank")
    @ApiOperation("添加题库")
    public ResultModel<String> addQuestionBank() throws Exception {
        return ResultModel.success(examService.addQuestionBank(this.getPageData()));
    }

    @RequestMapping("/deleteQuestionBank")
    @ApiOperation("删除题库")
    public ResultModel<String> deleteQuestionBank() throws Exception {
        return ResultModel.success(examService.deleteQuestionBank(this.getPageData()));
    }

    @RequestMapping("/statusQuestionBank")
    @ApiOperation("停用/启用/默认题库")
    public ResultModel<String> statusQuestionBank() throws Exception {
        return ResultModel.success(examService.statusQuestionBank(this.getPageData()));
    }

    @RequestMapping("/remarkQuestionBank")
    @ApiOperation("重命名题库")
    public ResultModel<String> remarkQuestionBank() throws Exception {
        return ResultModel.success(examService.remarkQuestionBank(this.getPageData()));
    }

    @RequestMapping("/addOneTopic")
    @ApiOperation("添加试题")
    public ResultModel<String> addOneTopic() throws Exception {
        return ResultModel.success(examService.addOneTopic(this.getPageData()));
    }

    @RequestMapping("/editOneTopic")
    @ApiOperation("编辑试题")
    public ResultModel<String> editOneTopic() throws Exception {
        return ResultModel.success(examService.editOneTopic(this.getPageData()));
    }


    @RequestMapping("/queryOneTopic")
    @ApiOperation("查询试题")
    public ResultModel<Topic> queryOneTopic() throws Exception {
        return ResultModel.success(examService.queryOneTopic(this.getPageData()));
    }

    @RequestMapping("/addBatckTopic")
    @ApiOperation("批量添加试题")
    public ResultModel<String> addBatckTopic() throws Exception {
        return ResultModel.success(examService.addBatckTopic(this.getPageData()));
    }

    @RequestMapping("/queryBankDetail")
    @ApiOperation("查询题库详情")
    public ResultModel<PageData> queryBankDetail() throws Exception {
        return ResultModel.success(examService.queryBankDetail(this.getPageData()));
    }

    @RequestMapping("/updateExcelStatus")
    @ApiOperation("题库Excel停用/启用/删除")
    public ResultModel<String> updateExcelStatus() throws Exception {
        return ResultModel.success(examService.updateExcelStatus(this.getPageData()));
    }

    @RequestMapping("/updateOneTopicStatus")
    @ApiOperation("单题停用/启用/删除")
    public ResultModel<String> updateOneTopicStatus() throws Exception {
        return ResultModel.success(examService.updateOneTopicStatus(this.getPageData()));
    }

    @RequestMapping("/queryUserByNameAndStuId")
    @ApiOperation("获取用户名以及考号")
    public ResultModel<PageData> queryUserByNameAndStuId() throws Exception {
        return ResultModel.success(examService.queryUserByNameAndStuId(this.getPageData()));
    }

    @RequestMapping("/releaseExam")
    @ApiOperation("发布考试")
    public ResultModel<String> releaseExam() throws Exception {
        return ResultModel.success(examService.releaseExam(this.getPageData()));
    }

    @RequestMapping("/examineRange")
    @ApiOperation("考生范围")
    public ResultModel<List<PageData>> examineRange() throws Exception {
        return ResultModel.success(examService.examineRange(this.getPageData()));
    }

    @RequestMapping("/queryExamList")
    @ApiOperation("查询考试列表")
    public ResultModel<PageInfo> queryExamList() throws Exception {
        return ResultModel.success(examService.queryExamList(this.getPageData()));
    }

    @RequestMapping("/queryExamListRoleLaunch")
    @ApiOperation("查询我发起的考试列表")
    public ResultModel<PageInfo> queryExamListRoleLaunch() throws Exception {
        return ResultModel.success(examService.queryExamListRoleLaunch(this.getPageData()));
    }

    @RequestMapping("/deleteExam")
    @ApiOperation("删除考试")
    public ResultModel<String> deleteExam() throws Exception {
        return ResultModel.success(examService.deleteExam(this.getPageData()));
    }

    @RequestMapping("/examStopToEnable")
    @ApiOperation("停用/启用考试")
    public ResultModel<String> examStopToEnable() throws Exception {
        return ResultModel.success(examService.examStopToEnable(this.getPageData()));
    }

    @RequestMapping("/startExam")
    @ApiOperation("拉取试题信息")
    public ResultModel<List<PageData>> startExam() throws Exception {
        return ResultModel.success(examService.startExam(this.getPageData()));
    }

    @RequestMapping("/startUserExam")
    @ApiOperation("开始考试")
    public ResultModel<String> startUserExam() throws Exception {
        return ResultModel.success(examService.startUserExam(this.getPageData()));
    }

    @RequestMapping("/continueExamination")
    @ApiOperation("继续考试")
    public ResultModel<String> continueExamination() throws Exception {
        return ResultModel.success(examService.continueExamination(this.getPageData()));
    }

    @RequestMapping("/startUserAnswer")
    @ApiOperation("开始答题")
    public ResultModel<String> startUserAnswer() throws Exception {
        return ResultModel.success(examService.startUserAnswer(this.getPageData()));
    }

    @RequestMapping("/submitTestPaper")
    @ApiOperation("提交试卷")
    public ResultModel<PageData> submitTestPaper() throws Exception {
        return ResultModel.success(examService.submitTestPaper(this.getPageData()));
    }

    @RequestMapping("/answers")
    @ApiOperation("获取答案考试结果")
    public ResultModel<PageData> answers() throws Exception {
        return ResultModel.success(examService.answers(this.getPageData()));
    }

    @RequestMapping("/questionAgain")
    @ApiOperation("再次答题")
    public ResultModel<String> questionAgain() throws Exception {
        return ResultModel.success(examService.questionAgain(this.getPageData()));
    }

    @RequestMapping("/wrongQuestions")
    @ApiOperation("查看错题")
    public ResultModel<List<PageData>> wrongQuestions() throws Exception {
        return ResultModel.success(examService.wrongQuestions(this.getPageData()));
    }

    @RequestMapping("/historicalAchievements")
    @ApiOperation("历史成绩")
    public ResultModel<List<PageData>> historicalAchievements() throws Exception {
        return ResultModel.success(examService.historicalAchievements(this.getPageData()));
    }

    @RequestMapping("/examPractice")
    @ApiOperation("考试练习")
    public ResultModel<String> examPractice() throws Exception {
        return ResultModel.success(examService.examPractice(this.getPageData()));
    }

    @RequestMapping("/examDetail")
    @ApiOperation("考试详情头")
    public ResultModel<PageData> examDetail() throws Exception {
        return ResultModel.success(examService.examDetail(this.getPageData()));
    }

    @RequestMapping("/examDetailBody")
    @ApiOperation("考试详情体带分页")
    public ResultModel<PageInfo> examDetailBody() throws Exception {
        return ResultModel.success(examService.examDetailBody(this.getPageData()));
    }

    @RequestMapping("/viewPaper")
    @ApiOperation("查看试卷")
    public ResultModel<List<PageData>> viewPaper() throws Exception {
        return ResultModel.success(examService.viewPaper(this.getPageData()));
    }

    @RequestMapping("/exportResults")
    @ApiOperation("导出成绩")
    public ResultModel<PageData> exportResults(HttpServletRequest request) throws Exception {
        return ResultModel.success(examService.exportResults(this.getPageData(),request));
    }
}
