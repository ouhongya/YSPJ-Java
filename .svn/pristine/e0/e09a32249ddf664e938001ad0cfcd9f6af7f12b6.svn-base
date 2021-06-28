package com.fh.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.fh.entity.system.User;

import java.io.IOException;
import java.io.PrintWriter;
import com.alibaba.fastjson.JSON;

/**
 * 
* 类名称：LoginHandlerInterceptor.java
* 类描述： 
* @author FH
* 作者单位： 
* 联系方式：
* 创建时间：2015年1月1日
* @version 1.6
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){
			if (path.contains("/api/")) {
				//排除登录接口以外的其他接口均需要先登录才能访问
					if (!path.equals("/api/v1/login") && !path.equals("/api/v1/getSmsCaptcha") && !path.equals("/api/v1/excel")&& !path.equals("/api/v1/downExcel")&& !path.equals("/api/v1/readExcel") && !path.equals("/api/v1/downunitExcel") && !path.equals("/api/v1/readunitExcel") && !path.equals("/api/v1/downloadTemplate") && !path.equals("/api/v1/uploadExcel")  && !path.equals("/api/v1/downloaddatabase") && !path.equals("/api/v1/uploadImage") &&!path.equals("/api/v1/uploadImageReport") && !path.equals("/api/v1/downloadcheckpicture")  && !path.equals("/api/v1/downloaddatabasecheck")&& !path.equals("/api/v1/downloaddatabasecheckone")&& !path.equals("/api/v1/downloadcheckpictureone")&& !path.equals("/api/v1/downloaddatabasecheckonequestion")&& !path.equals("/api/v1/downloadcheckpicturesecond")&& !path.equals("/api/v1/getsysmenus")&& !path.equals("/api/v1/getsysmenutables")&& !path.equals("/api/v1/getsysroles")&& !path.equals("/api/v1/getsysunits")&& !path.equals("/api/v1/getsysusers")&& !path.equals("/api/v1/gettbexcels")&& !path.equals("/api/v1/gettbexcelcategorys")&& !path.equals("/api/v1/gettbexcelusers")&& !path.equals("/api/v1/gettbgroupreports")&& !path.equals("/api/v1/gettbmessages")&& !path.equals("/api/v1/gettbmessageusers")&& !path.equals("/api/v1/gettbnorms")&& !path.equals("/api/v1/gettbnormdetails")&& !path.equals("/api/v1/gettbnormdetailrows")&& !path.equals("/api/v1/gettbtasks")&& !path.equals("/api/v1/gettbtaskdeletestatuss")&& !path.equals("/api/v1/gettbtaskdetails")&& !path.equals("/api/v1/gettbtaskdetailcheckrows")&& !path.equals("/api/v1/gettbtaskdetailnorms")&& !path.equals("/api/v1/gettbtaskdetailnormdetails")&& !path.equals("/api/v1/gettbtaskinfos")&& !path.equals("/api/v1/insertsysmenus")&& !path.equals("/api/v1/insertsysmenutables")&& !path.equals("/api/v1/insertsysroles")&& !path.equals("/api/v1/insertsysunits")&& !path.equals("/api/v1/insertsysusers")&& !path.equals("/api/v1/inserttbexcels")&& !path.equals("/api/v1/inserttbexcelcategorys")&& !path.equals("/api/v1/inserttbexcelusers")&& !path.equals("/api/v1/inserttbgroupreports")&& !path.equals("/api/v1/inserttbmessages")&& !path.equals("/api/v1/inserttbmessageusers")&& !path.equals("/api/v1/inserttbnorms")&& !path.equals("/api/v1/inserttbnormdetails")&& !path.equals("/api/v1/inserttbnormdetailrows")&& !path.equals("/api/v1/inserttbtasks")&& !path.equals("/api/v1/inserttbtaskdeletestatuss")&& !path.equals("/api/v1/inserttbtaskdetails")&& !path.equals("/api/v1/inserttbtaskdetailcheckrows")&& !path.equals("/api/v1/inserttbtaskdetailnorms")&& !path.equals("/api/v1/inserttbtaskdetailnormdetails")&& !path.equals("/api/v1/inserttbtaskinfos")&& !path.equals("/api/v1/updateImage")&& !path.equals("/api/v1/updateImageNull")&& !path.equals("/api/v1/getpictures")&& !path.equals("/api/v1/gettables")&& !path.equals("/api/v1/getinformation")&& !path.equals("/api/v1/inserttables")&& !path.equals("/api/v1/queryQuestionBankList")&& !path.equals("/api/v1/addQuestionBank")&& !path.equals("/api/v1/deleteQuestionBank")&& !path.equals("/api/v1/statusQuestionBank")&& !path.equals("/api/v1/queryQuestionBankListNotLimit")&& !path.equals("/api/v1/addOneTopic")&& !path.equals("/api/v1/editOneTopic")&& !path.equals("/api/v1/addBatckTopic")&& !path.equals("/api/v1/queryBankDetail")&& !path.equals("/api/v1/updateExcelStatus")&& !path.equals("/api/v1/updateExcelStatus")&& !path.equals("/api/v1/queryOneTopic")&& !path.equals("/api/v1/remarkQuestionBank")&& !path.equals("/api/v1/updateOneTopicStatus")&& !path.equals("/api/v1/queryUserByNameAndStuId")&& !path.equals("/api/v1/examineRange")&& !path.equals("/api/v1/releaseExam")&& !path.equals("/api/v1/queryExamList")&& !path.equals("/api/v1/queryExamListRoleLaunch")&& !path.equals("/api/v1/deleteExam")&& !path.equals("/api/v1/examStopToEnable")&& !path.equals("/api/v1/startExam")&& !path.equals("/api/v1/startUserExam")&& !path.equals("/api/v1/startUserAnswer")&& !path.equals("/api/v1/submitTestPaper")&& !path.equals("/api/v1/wrongQuestions")&& !path.equals("/api/v1/questionAgain")&& !path.equals("/api/v1/historicalAchievements")&& !path.equals("/api/v1/examPractice")&& !path.equals("/api/v1/examDetail")&& !path.equals("/api/v1/examDetailBody")&& !path.equals("/api/v1/viewPaper")&& !path.equals("/api/v1/continueExamination")&& !path.equals("/api/v1/answers")&& !path.equals("/api/v1/exportResults")) {
					ResultModel resultModel = null;
					String uid = request.getParameter("uid");
					if (uid == null || "".equals(uid)) {
						resultModel = ResultModel.failure("请先登录才能调用接口");
					}

					boolean b = checkKey(request.getParameter("FKEYNAME"), request.getParameter("FKEY"));
					if(!b){
						resultModel = ResultModel.failure("加密参数有问题");
					}

					if (null != resultModel) {
						wirteJsonToResponse(response, resultModel);
						return false;
					}
				}
			}
			return true;
		}else{
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			if(user!=null){
				path = path.substring(1, path.length());
//				boolean b = Jurisdiction.hasJurisdiction(path);

				return true;
			}else{
				//登陆过滤
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;		
				//return true;
			}
		}

	}


	private void wirteJsonToResponse(HttpServletResponse resp, Object obj) throws IOException {
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json; charset=utf-8");
		PrintWriter writer = resp.getWriter();
		writer.write(JSON.toJSONString(obj));
	}



	public  boolean checkKey(String paraname, String FKEY){
		paraname = (null == paraname)? "":paraname;

		String a = DateUtil.getDays();

		return MD5.md5(paraname+DateUtil.getDays()+",fh,").equals(FKEY);
	}


}


