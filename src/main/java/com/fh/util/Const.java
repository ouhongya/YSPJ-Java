package com.fh.util;

import org.springframework.context.ApplicationContext;

/**
 * 项目名称：
 *
 * @author:fh
 */
public class Const {
    public static final String SESSION_SECURITY_CODE = "sessionSecCode";
    public static final String SESSION_USER = "sessionUser";
    public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
    public static final String SESSION_menuList = "menuList";            //当前菜单
    public static final String SESSION_allmenuList = "allmenuList";        //全部菜单
    public static final String SESSION_QX = "QX";
    public static final String SESSION_userpds = "userpds";
    public static final String SESSION_USERROL = "USERROL";                //用户对象
    public static final String SESSION_USERNAME = "USERNAME";            //用户名
    public static final String TRUE = "T";
    public static final String FALSE = "F";
    public static final String LOGIN = "/login_toLogin.do";                //登录地址
    public static final String SYSNAME = "admin/config/SYSNAME.txt";    //系统名称路径
    public static final String PAGE = "admin/config/PAGE.txt";            //分页条数配置路径
    public static final String EMAIL = "admin/config/EMAIL.txt";        //邮箱服务器配置路径
    public static final String SMS1 = "admin/config/SMS1.txt";            //短信账户配置路径1
    public static final String SMS2 = "admin/config/SMS2.txt";            //短信账户配置路径2
    public static final String FWATERM = "admin/config/FWATERM.txt";    //文字水印配置路径
    public static final String IWATERM = "admin/config/IWATERM.txt";    //图片水印配置路径
    public static final String WEIXIN = "admin/config/WEIXIN.txt";    //微信配置路径
    public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";    //图片上传路径
    public static final String EXAM = "uploadFiles/exam/";    //临时的成绩导出路径
    public static final String FILEPATHFILE = "uploadFiles/file/";        //文件上传路径
    public static final String ISSUEIMAGEPATH = "static/issueImage/";        //问题图片上传路径
    public static final String SERVERURL = "192.168.1.204:8888/";        //问题图片上传路径
    public static final String EXCELLIST = "uploadFiles/excel/";        //EXCEL
    public static final String EXCELTEMPLATEPATH = "uploadFiles/template/";        //Excel模板上传路径
    public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; //二维码存放路径
    public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(gettbtaskdeletestatuss)|(inserttables)|(getinformation)|(gettables)|(getpictures)|(uploadFiles)|(updateImage)|(updateImageNull)|(inserttbtaskinfos)|(inserttbtaskdetailnormdetails)|(inserttbtaskdetailnorms)|(inserttbtaskdetailcheckrows)|(inserttbtaskdetails)|(inserttbtasks)|(inserttbtaskdeletestatuss)|(inserttbnormdetailrows)|(inserttbnormdetails)|(inserttbnorms)|(inserttbmessages)|(inserttbmessageusers)|(inserttbgroupreports)|(inserttbexcelusers)|(inserttbexcelcategorys)|(insertsysusers)|(inserttbexcels)|(insertsysunits)|(insertsysmenutables)|(insertsysroles)|(insertsysmenus)|(gettbtaskinfos)|(gettbtaskdetailnormdetails)|(gettbtaskdetailnorms)|(gettbtaskdetailcheckrows)|(logout)|(gettbtaskdetails)|(gettbtasks)|(gettbnormdetailrows)|(gettbnormdetails)|(gettbnorms)|(gettbmessageusers)|(gettbmessages)|(gettbgroupreports)|(gettbexcelusers)|(gettbexcelcategorys)|(getsysroles)|(gettbexcels)|(getsysusers)|(getsysunits)|(getsysmenutables)|(getsysmenus)|(queryUserDetail)|(downloadcheckpicturesecond)|(downloadcheckpictureone)|(downloaddatabasecheckonequestion)|(unitsecondlistName)|(queryCheckReportUser)|(unitlistsearch)|(recordrecyclebin)|(queryWorkload)|(exportTaskIssueList)|(downloaddatabase)|(deleterecyclebin)|(recyclebin)|(unitlist)|(deleteIMEI)|(downunitExcel)|(editunitsecond)|(editU)|(deleterole)|(deleteunit)|(unitsecondlist)|(saverole)|(saveunit)|(seeroleuser)|(saveunitsecond)|(editunit)|(goaddroledata)|(editroledata)|(editrole)|(readunitExcel)|(roleandrolemenu)|(goEditU)|(getSmsCaptcha)|(saveU)|(goAddU)|(listUsers)|(excel)|(deleteU)|(downExcel)|(readExcel)|(code)|(app)|(weixin)|(static)|(main)|(uploadExcel)|(queryExcelList)|(allot)|(queryAllot)|(updateAllot)|(disableExcel)|(rename)|(queryExcelOneAndTwo)|(queryUserPending)|(queryUserPending)|(reject)|(queryUserByBulletin)|(queryUserByBulletinNum)|(queryBulletin)|(createdTask)|(queryTaskToOne)|(editTask)|(deleteTask)|(checkingTaskList)|(queryUserToRectifyNum)|(checkingTask)|(cancelCheckingTask)|(IssueCheckingTask)|(cancelIssueCheckingTask)|(escalationTask)|(uploadImage)|(deleteImage)|(readByBulletin)|(queryTaskList)|(queryUserProcessed)|(pass)|(enableExcel)|(deleteExcel)|(addBulletin)|(queryReportMessage)|(deleteBulletin)|(questionsList)|(downloadTemplate)|(queryUserBySpecialty)|(queryUnitAll)|(queryExcelOneAndTwo)|(queryTaskIssueList)|(querySiteAll)|(checkingTaskList)|(queryUserByTask)|(queryUserByTaskToUser)|(questionsTaskList)|(downloadcheckpicture)|(downloaddatabasecheck)|(getnormal)|(queryUserNormList)|(queryUnitList)|(queryTaskOverDetail)|(queryStatisticsList)|(sendMessage)|(downloaddatabasecheckone)|(questionsTaskOne)|(queryTaskIssue)|(queryTaskIssueList)|(allocation)|(groupAllocation)|(queryTaskDetailRowCheck)|(groupAllocationList)|(websocket)|(queryQuestionBankList)|(addQuestionBank)|(deleteQuestionBank)|(statusQuestionBank)|(queryQuestionBankListNotLimit)|(addOneTopic)|(editOneTopic)|(addBatckTopic)|(queryBankDetail)|(updateExcelStatus)|(updateOneTopicStatus)|(queryOneTopic)|(remarkQuestionBank)|(updateOneTopicStatus)|(queryUserByNameAndStuId)|(releaseExam)|(examineRange)|(queryExamListRoleLaunch)|(queryExamList)|(deleteExam)|(examStopToEnable)|(startExam)|(startUserExam)|(startUserAnswer)|(submitTestPaper)|(wrongQuestions)|(questionAgain)|(historicalAchievements)|(examPractice)|(examDetail)|(examDetailBody)|(viewPaper)|(continueExamination)|(answers)|(exportResults)).*";    //不对匹配该值的访问路径拦截（正则）

    public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化

    /**
     * APP Constants
     */
    //app注册接口_请求协议参数)
    public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[]{"countries", "uname", "passwd", "title", "full_name", "company_name", "countries_code", "area_code", "telephone", "mobile"};
    public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[]{"国籍", "邮箱帐号", "密码", "称谓", "名称", "公司名称", "国家编号", "区号", "电话", "手机号"};

    //app根据用户名获取会员信息接口_请求协议中的参数
    public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
    public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};


}