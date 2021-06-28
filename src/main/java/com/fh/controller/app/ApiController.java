package com.fh.controller.app;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.*;
import com.fh.service.app.sysmenu.SysmenuService;
import com.fh.service.app.sysmenutable.SysmenutableService;
import com.fh.service.app.sysrole.SysroleService;
import com.fh.service.app.sysunit.SysunitService;
import com.fh.service.app.sysuser.SysuserService;
import com.fh.service.app.tbexcelcategory.TbexcelcategoryService;
import com.fh.service.app.tbexceluser.TbexceluserService;
import com.fh.service.app.tbexecel.TbexecelService;
import com.fh.service.app.tbgroupreport.TbgroupreportService;
import com.fh.service.app.tbmessage.TbmessageService;
import com.fh.service.app.tbmessageuser.TbmessageuserService;
import com.fh.service.app.tbnorm.TbnormService;
import com.fh.service.app.tbnormdetail.TbnormdetailService;
import com.fh.service.app.tbnormdetailrow.TbnormdetailrowService;
import com.fh.service.app.tbtask.TbtaskService;
import com.fh.service.app.tbtaskdeletestatus.TbtaskdeletestatusService;
import com.fh.service.app.tbtaskdetail.TbtaskdetailService;
import com.fh.service.app.tbtaskdetailcheckrow.TbtaskdetailcheckrowService;
import com.fh.service.app.tbtaskdetailnorm.TbtaskdetailnormService;
import com.fh.service.app.tbtaskdetailnormdetail.TbtaskdetailnormdetailService;
import com.fh.service.app.tbtaskinfo.TbtaskinfoService;
import com.fh.service.information.pictures.PicturesService;
import com.fh.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * 客户端接口文档
 */



@RestController
@RequestMapping("/api/v1")
@Api(tags = "ct接口管理")
public class ApiController extends BaseController {

    @Resource(name="sysmenuService")
    private SysmenuService sysmenuService;

    @Resource(name="sysmenutableService")
    private SysmenutableService sysmenutableService;

    @Resource(name="sysroleService")
    private SysroleService sysroleService;

    @Resource(name="sysunitService")
    private SysunitService sysunitService;

    @Resource(name="sysuserService")
    private SysuserService sysuserService;

    @Resource(name="tbexecelService")
    private TbexecelService tbexecelService;

    @Resource(name="tbexcelcategoryService")
    private TbexcelcategoryService tbexcelcategoryService;

    @Resource(name="tbexceluserService")
    private TbexceluserService tbexceluserService;

    @Resource(name="tbgroupreportService")
    private TbgroupreportService tbgroupreportService;

    @Resource(name="tbmessageService")
    private TbmessageService tbmessageService;

    @Resource(name="tbmessageuserService")
    private TbmessageuserService tbmessageuserService;

    @Resource(name="tbnormService")
    private TbnormService tbnormService;

    @Resource(name="tbnormdetailService")
    private TbnormdetailService tbnormdetailService;

    @Resource(name="tbnormdetailrowService")
    private TbnormdetailrowService tbnormdetailrowService;

    @Resource(name="tbtaskService")
    private TbtaskService tbtaskService;

    @Resource(name="tbtaskdeletestatusService")
    private TbtaskdeletestatusService tbtaskdeletestatusService;

    @Resource(name="tbtaskdetailService")
    private TbtaskdetailService tbtaskdetailService;

    @Resource(name="tbtaskdetailcheckrowService")
    private TbtaskdetailcheckrowService tbtaskdetailcheckrowService;

    @Resource(name="tbtaskdetailnormService")
    private TbtaskdetailnormService tbtaskdetailnormService;

    @Resource(name="tbtaskdetailnormdetailService")
    private TbtaskdetailnormdetailService tbtaskdetailnormdetailService;

    @Resource(name="tbtaskinfoService")
    private TbtaskinfoService tbtaskinfoService;

    @Resource(name="picturesService")
    private PicturesService picturesService;


    @RequestMapping("/getSmsCaptcha")
    @ApiOperation("手机登录获取验证码")
    public ResultModel<Message> getSmsCaptcha() {
        PageData pd = this.getPageData();
        String phone=pd.getString("phone");
        Message message=new Message();
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        String status = sendSms1(phone, code).trim().replaceAll("\n", "");
        switch (status) {
            case "100":
                message.setStatus("100");
                message.setNumber(code);
                break;
            case "101":
                message.setStatus("101");
                message.setNumber("");
                break;
            case "102":
                message.setStatus("102");
                message.setNumber("");
                break;
            case "103":
                message.setStatus("103");
                message.setNumber("");
                break;
            case "104":
                message.setStatus("104");
                message.setNumber("");
                break;
            case "105":
                message.setStatus("105");
                message.setNumber("");
                break;
            case "106":
                message.setStatus("106");
                message.setNumber("");
                break;
            case "107":
                message.setStatus("107");
                message.setNumber("");
                break;
            case "108":
                message.setStatus("108");
                message.setNumber("");
                break;
            case "109":
                message.setStatus("109");
                message.setNumber("");
                break;
            case "111":
                message.setStatus("111");
                message.setNumber("");
                break;
            case "120":
                message.setStatus("120");
                message.setNumber("");
                break;
            default:
                message.setStatus("130");
                message.setNumber("");
                break;
        }
        return ResultModel.success(message);

    }


    public static  String sendSms1(String mobile,String code){
        String PostData = "";
        try {
            String password = "cciet82449088";
            String username = "CDJSDZ";
            String phoneCode = "您的验证码是：【变量】。请不要把验证码泄露给其他人。如非本人操作，可不用理会！".replace("【变量】", code);
            PostData = "account="+username+"&password="+password+"&mobile="+mobile+"&content="+ URLEncoder.encode(phoneCode,"utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("短信提交失败");
        }
        String ret = SMS(PostData, "http://sms.106jiekou.com/utf8/sms.aspx");
        return ret;
    }


    public static  String sendSms2(String mobile,String name,String lastmobile){
        String PostData = "";
        try {
            String password = "cciet82449088";
            String username = "CDJSDZ";
            String phoneCode = "尊敬的" + name + "您好，您尾号为" + lastmobile + "的手机号已成功开通账户，详情请登录【验收检查】APP查看。感谢您的支持！";
            PostData = "account="+username+"&password="+password+"&mobile="+mobile+"&content="+ URLEncoder.encode(phoneCode,"utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("短信提交失败");
        }
        String ret = SMS(PostData, "http://sms.106jiekou.com/utf8/sms.aspx");
        return ret;
    }

    public static  String sendSms3(String mobile){
        String PostData = "";
        try {
            String password = "cciet82449088";
            String username = "CDJSDZ";
            String phoneCode = "您收到一条新任务，详情请登录【验收检查】APP查看。感谢您的支持！";
            PostData = "account="+username+"&password="+password+"&mobile="+mobile+"&content="+ URLEncoder.encode(phoneCode,"utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("短信提交失败");
        }
        String ret = SMS(PostData, "http://sms.106jiekou.com/utf8/sms.aspx");
        return ret;
    }


    public static  String sendSms4(String mobile){
        String PostData = "";
        try {
            String password = "cciet82449088";
            String username = "CDJSDZ";
            String phoneCode = "您有一条任务信息已变更，详情请登录【验收检查】APP查看。感谢您的支持！";
            PostData = "account="+username+"&password="+password+"&mobile="+mobile+"&content="+ URLEncoder.encode(phoneCode,"utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("短信提交失败");
        }
        String ret = SMS(PostData, "http://sms.106jiekou.com/utf8/sms.aspx");
        return ret;
    }

    public static  String sendSms5(String mobile){
        String PostData = "";
        try {
            String password = "cciet82449088";
            String username = "CDJSDZ";
            String phoneCode = "您收到一条新的待办消息，详情请登录【验收检查】APP查看。感谢您的支持！";
            PostData = "account="+username+"&password="+password+"&mobile="+mobile+"&content="+ URLEncoder.encode(phoneCode,"utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("短信提交失败");
        }
        String ret = SMS(PostData, "http://sms.106jiekou.com/utf8/sms.aspx");
        return ret;
    }

    public static  String SMS(String postData, String postUrl) {
        HttpURLConnection conn = null;
        InputStream is = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        String str = "";
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-agent","Mozilla/4.0");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);//默觉得false的，所以须要设置
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            OutputStream outStream = conn.getOutputStream();
            DataOutputStream out = new DataOutputStream(outStream);
            out.writeBytes(postData);
            out.close();

            is = conn.getInputStream();
            reader = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(reader);
            String readLine = "";
            while((readLine=br.readLine())!=null){
                str+=readLine+"\n";
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return "";
    }

    @RequestMapping("/getpictures")
    @ApiOperation("获取公司图片")
    public ResultModel<PageData> getpictures() throws Exception {
        PageData pd = this.getPageData();
        PageData byId = picturesService.findById(pd);
        return ResultModel.success(byId);
    }



    @RequestMapping("/gettables")
    @ApiOperation("获取tables记录更新")
    public ResultModel<Map<String, List<PageData>>> getsysmenus() throws Exception {
        PageData pd = this.getPageData();
        List<PageData> data = sysmenuService.listAll(pd);//列出Sysmenu列表
        List<PageData> data1 = sysmenutableService.listAll(pd);//列出sysmenutable列表
        List<PageData> data2 = sysroleService.listAll(pd);//列出sysrole列表
        List<PageData> data3 = sysunitService.listAll(pd);//列出sysunit列表
        List<PageData> data4 = sysuserService.listAll(pd);//列出sysuser列表
        List<PageData> data5 = tbexecelService.listAll(pd);//列出tbexecel列表
        List<PageData> data6 = tbexcelcategoryService.listAll(pd);//列出tbexcelcategory列表
        List<PageData> data7 = tbexceluserService.listAll(pd);//列出tbexceluser列表
        List<PageData> data8 = tbgroupreportService.listAll(pd);//列出tbgroupreport列表
        List<PageData> data9 = tbmessageService.listAll(pd);//列出tbmessage列表
        List<PageData> data10 = tbmessageuserService.listAll(pd);//列出tbmessageuser列表
        List<PageData> data11 = tbnormService.listAll(pd);//列出tbnorm列表
        List<PageData> data12 = tbnormdetailService.listAll(pd);//列出tbnormdetail列表
        List<PageData> data13 = tbnormdetailrowService.listAll(pd);//列出tbnormdetailrow列表
        List<PageData> data14 = tbtaskService.listAll(pd);//列出tbtask列表
        List<PageData> data15 = tbtaskdeletestatusService.listAll(pd);//列出tbtaskdeletestatus列表
        List<PageData> data16 = tbtaskdetailService.listAll(pd);//列出tbtaskdetail列表
        List<PageData> data17 = tbtaskdetailcheckrowService.listAll(pd);//列出tbtaskdetailcheckrow列表
        List<PageData> data18 = tbtaskdetailnormService.listAll(pd);//列出tbtaskdetailnorm列表
        List<PageData> data19 = tbtaskdetailnormdetailService.listAll(pd);//列出tbtaskdetailnormdetail列表
        List<PageData> data20 = tbtaskinfoService.listAll(pd);//列出tbtaskinfo列表
        Map<String, List<PageData>> map = new HashMap<String,List<PageData>>();
        map.put("sysmenu",data);
        map.put("sysmenutable",data1);
        map.put("sysrole",data2);
        map.put("sysunit",data3);
        map.put("sysuser",data4);
        map.put("tbexecel",data5);
        map.put("tbexcelcategory",data6);
        map.put("tbexceluser",data7);
        map.put("tbgroupreport",data8);
        map.put("tbmessage",data9);
        map.put("tbmessageuser",data10);
        map.put("tbnorm",data11);
        map.put("tbnormdetail",data12);
        map.put("tbnormdetailrow",data13);
        map.put("tbtask",data14);
        map.put("tbtaskdeletestatus",data15);
        map.put("tbtaskdetail",data16);
        map.put("tbtaskdetailcheckrow",data17);
        map.put("tbtaskdetailnorm",data18);
        map.put("tbtaskdetailnormdetail",data19);
        map.put("tbtaskinfo",data20);
        return ResultModel.success(map);
    }




    @RequestMapping("/getinformation")
    @ApiOperation("获取sysmenu记录更新")
    public ResultModel<Map<String, List<PageData>>> getinformation() throws Exception {
        PageData pd = this.getPageData();
        List<PageData> data = sysmenuService.listAll(pd);//列出Sysmenu列表
        List<PageData> data1 = sysmenutableService.listAll(pd);//列出sysmenutable列表
        List<PageData> data2 = sysroleService.listAll(pd);//列出sysrole列表
        List<PageData> data3 = sysunitService.listAll(pd);//列出sysunit列表
        List<PageData> data4 = sysuserService.listAll(pd);//列出sysuser列表
        Map<String, List<PageData>> map = new HashMap<String,List<PageData>>();
        map.put("sysmenu",data);
        map.put("sysmenutable",data1);
        map.put("sysrole",data2);
        map.put("sysunit",data3);
        map.put("sysuser",data4);
        return ResultModel.success(map);
    }



    @RequestMapping("/inserttables")
    @ApiOperation("本地sysmenu记录更新")
    public ResultModel< Map<String, Object>> insertsysmenus() throws Exception {
        PageData pd = this.getPageData();
        String resmenus21=pd.getString("resmenus21");
        String resmenus22=pd.getString("resmenus22");
        String resmenus23=pd.getString("resmenus23");
        String resmenus24=pd.getString("resmenus24");
        String resmenus25=pd.getString("resmenus25");
        String resmenus26=pd.getString("resmenus26");
        String resmenus27=pd.getString("resmenus27");
        String resmenus28=pd.getString("resmenus28");
        String resmenus29=pd.getString("resmenus29");
        String resmenus30=pd.getString("resmenus30");
        String resmenus31=pd.getString("resmenus31");
        String resmenus32=pd.getString("resmenus32");
        String resmenus33=pd.getString("resmenus33");
        String resmenus34=pd.getString("resmenus34");
        String resmenus35=pd.getString("resmenus35");
        String resmenus36=pd.getString("resmenus36");
        String resmenus37=pd.getString("resmenus37");
        String resmenus38=pd.getString("resmenus38");
        String resmenus39=pd.getString("resmenus39");
        String resmenus40=pd.getString("resmenus40");
        String resmenus41=pd.getString("resmenus41");
        List<Resmenus21> accountsresmenus21 = new ArrayList<>();

        if(resmenus21 !=null){
            JSONArray jsonArray=JSONArray.parseArray(resmenus21);
            for (Object jsonObject : jsonArray) {
                Resmenus21 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus21.class);
                accountsresmenus21.add(platformModel);
            }
        }


        List<Resmenus22> accountsresmenus22 = new ArrayList<>();
        if(resmenus22 !=null){
            JSONArray jsonArray1=JSONArray.parseArray(resmenus22);
            for (Object jsonObject : jsonArray1) {
                Resmenus22 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus22.class);
                accountsresmenus22.add(platformModel);
            }

        }


        List<Resmenus23> accountsresmenus23 = new ArrayList<>();
        if(resmenus23 !=null){
            JSONArray jsonArray2=JSONArray.parseArray(resmenus23);
            for (Object jsonObject : jsonArray2) {
                Resmenus23 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus23.class);
                accountsresmenus23.add(platformModel);
            }
        }



        List<Resmenus24> accountsresmenus24 = new ArrayList<>();
        if(resmenus24 !=null){
            JSONArray jsonArray3=JSONArray.parseArray(resmenus24);
            for (Object jsonObject : jsonArray3) {
                Resmenus24 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus24.class);
                accountsresmenus24.add(platformModel);
            }

        }



        List<Resmenus25> accountsresmenus25 = new ArrayList<>();
        if(resmenus25 !=null){
            JSONArray jsonArray4=JSONArray.parseArray(resmenus25);
            for (Object jsonObject : jsonArray4) {
                Resmenus25 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus25.class);
                accountsresmenus25.add(platformModel);
            }

        }


        List<Resmenus26> accountsresmenus26 = new ArrayList<>();
        if(resmenus26 !=null){
            JSONArray jsonArray5=JSONArray.parseArray(resmenus26);
            for (Object jsonObject : jsonArray5) {
                Resmenus26 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus26.class);
                accountsresmenus26.add(platformModel);
            }
        }

        List<Resmenus27> accountsresmenus27 = new ArrayList<>();
        if(resmenus27 !=null){
            JSONArray jsonArray6=JSONArray.parseArray(resmenus27);
            for (Object jsonObject : jsonArray6) {
                Resmenus27 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus27.class);
                accountsresmenus27.add(platformModel);
            }

        }

        List<Resmenus28> accountsresmenus28 = new ArrayList<>();
        if(resmenus28 !=null){
            JSONArray jsonArray7=JSONArray.parseArray(resmenus28);
            for (Object jsonObject : jsonArray7) {
                Resmenus28 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus28.class);
                accountsresmenus28.add(platformModel);
            }

        }

        List<Resmenus29> accountsresmenus29 = new ArrayList<>();
        if(resmenus29 !=null){
            JSONArray jsonArray8=JSONArray.parseArray(resmenus29);
            for (Object jsonObject : jsonArray8) {
                Resmenus29 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus29.class);
                accountsresmenus29.add(platformModel);
            }

        }

        List<Resmenus30> accountsresmenus30 = new ArrayList<>();
        if(resmenus30 !=null){
            JSONArray jsonArray9=JSONArray.parseArray(resmenus30);
            for (Object jsonObject : jsonArray9) {
                Resmenus30 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus30.class);
                accountsresmenus30.add(platformModel);
            }
        }

        List<Resmenus31> accountsresmenus31 = new ArrayList<>();

        if(resmenus31 !=null){
            JSONArray jsonArray10=JSONArray.parseArray(resmenus31);
            for (Object jsonObject : jsonArray10) {
                Resmenus31 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus31.class);
                accountsresmenus31.add(platformModel);
            }
        }

        List<Resmenus32> accountsresmenus32 = new ArrayList<>();

        if(resmenus32 !=null){
            JSONArray jsonArray11=JSONArray.parseArray(resmenus32);
            for (Object jsonObject : jsonArray11) {
                Resmenus32 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus32.class);
                accountsresmenus32.add(platformModel);
            }
        }

        List<Resmenus33> accountsresmenus33 = new ArrayList<>();

        if(resmenus33 !=null){
            JSONArray jsonArray12=JSONArray.parseArray(resmenus33);
            for (Object jsonObject : jsonArray12) {
                Resmenus33 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus33.class);
                accountsresmenus33.add(platformModel);
            }
        }

        List<Resmenus34> accountsresmenus34 = new ArrayList<>();

        if(resmenus34 !=null){
            JSONArray jsonArray13=JSONArray.parseArray(resmenus34);
            for (Object jsonObject : jsonArray13) {
                Resmenus34 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus34.class);
                accountsresmenus34.add(platformModel);
            }
        }

        List<Resmenus35> accountsresmenus35 = new ArrayList<>();

        if(resmenus35 !=null){
            JSONArray jsonArray14=JSONArray.parseArray(resmenus35);
            for (Object jsonObject : jsonArray14) {
                Resmenus35 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus35.class);
                accountsresmenus35.add(platformModel);
            }
        }

        List<Resmenus36> accountsresmenus36 = new ArrayList<>();

        if(resmenus36 !=null){
            JSONArray jsonArray15=JSONArray.parseArray(resmenus36);
            for (Object jsonObject : jsonArray15) {
                Resmenus36 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus36.class);
                accountsresmenus36.add(platformModel);
            }
        }

        List<Resmenus37> accountsresmenus37 = new ArrayList<>();

        if(resmenus37 !=null){
            JSONArray jsonArray16=JSONArray.parseArray(resmenus37);
            for (Object jsonObject : jsonArray16) {
                Resmenus37 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus37.class);
                accountsresmenus37.add(platformModel);
            }
        }

        List<Resmenus38> accountsresmenus38 = new ArrayList<>();

        if(resmenus38 !=null){
            JSONArray jsonArray17=JSONArray.parseArray(resmenus38);
            for (Object jsonObject : jsonArray17) {
                Resmenus38 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus38.class);
                accountsresmenus38.add(platformModel);
            }
        }

        List<Resmenus39> accountsresmenus39 = new ArrayList<>();

        if(resmenus39 !=null){
            JSONArray jsonArray18=JSONArray.parseArray(resmenus39);
            for (Object jsonObject : jsonArray18) {
                Resmenus39 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus39.class);
                accountsresmenus39.add(platformModel);
            }
        }

        List<Resmenus40> accountsresmenus40 = new ArrayList<>();

        if(resmenus40 !=null){
            JSONArray jsonArray19=JSONArray.parseArray(resmenus40);
            for (Object jsonObject : jsonArray19) {
                Resmenus40 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus40.class);
                accountsresmenus40.add(platformModel);
            }
        }

        List<Resmenus41> accountsresmenus41 = new ArrayList<>();

        if(resmenus41 !=null){
            JSONArray jsonArray20=JSONArray.parseArray(resmenus41);
            for (Object jsonObject : jsonArray20) {
                Resmenus41 platformModel = JSONObject.parseObject(jsonObject.toString(), Resmenus41.class);
                accountsresmenus41.add(platformModel);
            }
        }


        List<PageData> menuedits = new ArrayList<>();
        List<PageData> menusaves = new ArrayList<>();
        for(Resmenus21 menus21:accountsresmenus21){
            if(menus21.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("menu_id",menus21.getMenu_id());
                PageData menubyId = sysmenuService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("menu_id",menus21.getMenu_id());
                    menuedit.put("menu_name",menus21.getMenu_name());
                    menuedit.put("menu_url",menus21.getMenu_url());
                    menuedit.put("parent_id",menus21.getParent_id());
                    menuedit.put("menu_icon",menus21.getMenu_icon());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("menu_id",menus21.getMenu_id());
                    menusave.put("menu_name",menus21.getMenu_name());
                    menusave.put("menu_url",menus21.getMenu_url());
                    menusave.put("parent_id",menus21.getParent_id());
                    menusave.put("menu_icon",menus21.getMenu_icon());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves.add(menusave);
                }
            }

            if(menus21.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("menu_id",menus21.getMenu_id());
                menudelete.put("menu_name",menus21.getMenu_name());
                menudelete.put("menu_url",menus21.getMenu_url());
                menudelete.put("parent_id",menus21.getParent_id());
                menudelete.put("menu_icon",menus21.getMenu_icon());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits.add(menudelete);
            }
        }


        if(menusaves.size()>0){
            sysmenuService.save(menusaves);
        }
        if(menuedits.size()>0){
            sysmenuService.edit(menuedits);
        }





        List<PageData> menuedits1 = new ArrayList<>();
        List<PageData> menusaves1 = new ArrayList<>();
        for(Resmenus22 menus22:accountsresmenus22){
            if(menus22.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("role_id",menus22.getRole_id());
                pmenu.put("menu_id",menus22.getMenu_id());
                List<PageData> menutablebyId = sysmenutableService.findById(pmenu);
                if( menutablebyId!=null && menutablebyId.size()!=0){
                    PageData menuedit= new PageData();
                    menuedit.put("role_id",menus22.getRole_id());
                    menuedit.put("menu_id",menus22.getMenu_id());
                    menuedit.put("status",menus22.getStatus());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits1.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("role_id",menus22.getRole_id());
                    menusave.put("menu_id",menus22.getMenu_id());
                    menusave.put("status",menus22.getStatus());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves1.add(menusave);
                }
            }

            if(menus22.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("role_id",menus22.getRole_id());
                menudelete.put("menu_id",menus22.getMenu_id());
                menudelete.put("status",menus22.getStatus());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits1.add(menudelete);
            }
        }

        if(menusaves1.size()>0){
            sysmenutableService.save(menusaves1);
        }
        if(menuedits1.size()>0){
            sysmenutableService.edit(menuedits1);
        }






        List<PageData> menuedits2 = new ArrayList<>();
        List<PageData> menusaves2 = new ArrayList<>();
        for(Resmenus23 menus23:accountsresmenus23){
            if(menus23.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("role_id",menus23.getRole_id());
                PageData menubyId = sysroleService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("role_id",menus23.getRole_id());
                    menuedit.put("role_name",menus23.getRole_name());
                    menuedit.put("parent_id",menus23.getParent_id());
                    menuedit.put("status",menus23.getStatus());
                    menuedit.put("function_id",menus23.getFunction_id());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits2.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("role_id",menus23.getRole_id());
                    menusave.put("role_name",menus23.getRole_name());
                    menusave.put("parent_id",menus23.getParent_id());
                    menusave.put("status",menus23.getStatus());
                    menusave.put("function_id",menus23.getFunction_id());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves2.add(menusave);
                }
            }

            if(menus23.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("role_id",menus23.getRole_id());
                menudelete.put("role_name",menus23.getRole_name());
                menudelete.put("parent_id",menus23.getParent_id());
                menudelete.put("status",menus23.getStatus());
                menudelete.put("function_id",menus23.getFunction_id());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits2.add(menudelete);
            }
        }


        if(menusaves2.size()>0){
            sysroleService.save(menusaves2);
        }
        if(menuedits2.size()>0){
            sysroleService.edit(menuedits2);
        }




        List<PageData> menuedits3 = new ArrayList<>();
        List<PageData> menusaves3 = new ArrayList<>();
        for(Resmenus24 menus24:accountsresmenus24){
            if(menus24.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("unit_id",menus24.getUnit_id());
                PageData menubyId = sysunitService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("unit_id",menus24.getUnit_id());
                    menuedit.put("unit_name",menus24.getUnit_name());
                    menuedit.put("unit_user",menus24.getUnit_user());
                    menuedit.put("unit_userphone",menus24.getUnit_userphone());
                    menuedit.put("address",menus24.getAddress());
                    menuedit.put("parent_id",menus24.getParent_id());
                    menuedit.put("status",menus24.getStatus());
                    menuedit.put("company_id",menus24.getCompany_id());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits3.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("unit_id",menus24.getUnit_id());
                    menusave.put("unit_name",menus24.getUnit_name());
                    menusave.put("unit_user",menus24.getUnit_user());
                    menusave.put("unit_userphone",menus24.getUnit_userphone());
                    menusave.put("address",menus24.getAddress());
                    menusave.put("parent_id",menus24.getParent_id());
                    menusave.put("status",menus24.getStatus());
                    menusave.put("company_id",menus24.getCompany_id());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves3.add(menusave);
                }
            }

            if(menus24.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("unit_id",menus24.getUnit_id());
                menudelete.put("unit_name",menus24.getUnit_name());
                menudelete.put("unit_user",menus24.getUnit_user());
                menudelete.put("unit_userphone",menus24.getUnit_userphone());
                menudelete.put("address",menus24.getAddress());
                menudelete.put("parent_id",menus24.getParent_id());
                menudelete.put("status",menus24.getStatus());
                menudelete.put("company_id",menus24.getCompany_id());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits3.add(menudelete);
            }
        }


        if(menusaves3.size()>0){
            sysunitService.save(menusaves3);
        }
        if(menuedits3.size()>0){
            sysunitService.edit(menuedits3);
        }



        List<PageData> menuedits4 = new ArrayList<>();
        List<PageData> menusaves4 = new ArrayList<>();
        for(Resmenus25 menus25:accountsresmenus25){
            if(menus25.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("user_id",menus25.getUser_id());
                PageData menubyId = sysuserService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("user_id",menus25.getUser_id());
                    menuedit.put("password",menus25.getPassword());
                    menuedit.put("name",menus25.getName());
                    menuedit.put("role_id",menus25.getRole_id());
                    menuedit.put("status",menus25.getStatus());
                    menuedit.put("phone",menus25.getPhone());
                    menuedit.put("company_id",menus25.getCompany_id());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedit.put("isupdate",menus25.getIsupdate());
                    menuedits4.add(menuedit);

                }else{
                    PageData menusave= new PageData();
                    menusave.put("user_id",menus25.getUser_id());
                    menusave.put("password",menus25.getPassword());
                    menusave.put("name",menus25.getName());
                    menusave.put("role_id",menus25.getRole_id());
                    menusave.put("status",menus25.getStatus());
                    menusave.put("phone",menus25.getPhone());
                    menusave.put("company_id",menus25.getCompany_id());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusave.put("isupdate",menus25.getIsupdate());
                    menusaves4.add(menusave);

                }
            }

            if(menus25.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("user_id",menus25.getUser_id());
                menudelete.put("password",menus25.getPassword());
                menudelete.put("name",menus25.getName());
                menudelete.put("role_id",menus25.getRole_id());
                menudelete.put("status",menus25.getStatus());
                menudelete.put("phone",menus25.getPhone());
                menudelete.put("company_id",menus25.getCompany_id());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menudelete.put("isupdate",menus25.getIsupdate());
                menuedits4.add(menudelete);

            }
        }

        if(menusaves4.size()>0){
            sysuserService.save(menusaves4);
           for(PageData pagedata : menusaves4 )  {
               String s = sendSms2(pagedata.getString("phone"), pagedata.getString("name"), pagedata.getString("phone").substring(7)).trim().replaceAll("\n", "");
               System.out.println(s);
           }
        }

        if(menuedits4.size()>0){
            sysuserService.edit(menuedits4);
        }



        List<PageData> menuedits5 = new ArrayList<>();
        List<PageData> menusaves5 = new ArrayList<>();
        for(Resmenus26 menus26:accountsresmenus26){
            if(menus26.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("id",menus26.getId());
                PageData menubyId = tbexecelService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("id",menus26.getId());
                    menuedit.put("url",menus26.getUrl());
                    menuedit.put("name",menus26.getName());
                    menuedit.put("user_id",menus26.getUser_id());
                    menuedit.put("isCategory",menus26.getIsCategory());
                    menuedit.put("categort_id",menus26.getCategort_id());
                    menuedit.put("status",menus26.getStatus());
                    menuedit.put("type",menus26.getType());
                    menuedit.put("view",menus26.getView());
                    menuedit.put("totlecheck",menus26.getTotlecheck());
                    menuedit.put("size1",menus26.getSize());
                    menuedit.put("created_time",menus26.getCreated_time());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits5.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("id",menus26.getId());
                    menusave.put("url",menus26.getUrl());
                    menusave.put("name",menus26.getName());
                    menusave.put("user_id",menus26.getUser_id());
                    menusave.put("isCategory",menus26.getIsCategory());
                    menusave.put("categort_id",menus26.getCategort_id());
                    menusave.put("status",menus26.getStatus());
                    menusave.put("type",menus26.getType());
                    menusave.put("view",menus26.getView());
                    menusave.put("totlecheck",menus26.getTotlecheck());
                    menusave.put("size1",menus26.getSize());
                    menusave.put("created_time",menus26.getCreated_time());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves5.add(menusave);
                }
            }

            if(menus26.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("id",menus26.getId());
                menudelete.put("url",menus26.getUrl());
                menudelete.put("name",menus26.getName());
                menudelete.put("user_id",menus26.getUser_id());
                menudelete.put("isCategory",menus26.getIsCategory());
                menudelete.put("categort_id",menus26.getCategort_id());
                menudelete.put("status",menus26.getStatus());
                menudelete.put("type",menus26.getType());
                menudelete.put("view",menus26.getView());
                menudelete.put("totlecheck",menus26.getTotlecheck());
                menudelete.put("size1",menus26.getSize());
                menudelete.put("created_time",menus26.getCreated_time());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits5.add(menudelete);
            }
        }

        if(menusaves5.size()>0){
            tbexecelService.save(menusaves5);
        }
        if(menuedits5.size()>0){
            tbexecelService.edit(menuedits5);
        }




        List<PageData> menuedits6 = new ArrayList<>();
        List<PageData> menusaves6 = new ArrayList<>();
        for(Resmenus27 menus27:accountsresmenus27){
            if(menus27.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("id",menus27.getId());
                PageData menubyId = tbexcelcategoryService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("id",menus27.getId());
                    menuedit.put("name",menus27.getName());
                    menuedit.put("status",menus27.getStatus());
                    menuedit.put("user_id",menus27.getUser_id());
                    menuedit.put("created_time",menus27.getCreated_time());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits6.add(menuedit);
//                    tbexcelcategoryService.edit(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("id",menus27.getId());
                    menusave.put("name",menus27.getName());
                    menusave.put("status",menus27.getStatus());
                    menusave.put("user_id",menus27.getUser_id());
                    menusave.put("created_time",menus27.getCreated_time());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves6.add(menusave);
//                    tbexcelcategoryService.save(menusave);
                }
            }

            if(menus27.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("id",menus27.getId());
                menudelete.put("name",menus27.getName());
                menudelete.put("status",menus27.getStatus());
                menudelete.put("user_id",menus27.getUser_id());
                menudelete.put("created_time",menus27.getCreated_time());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits6.add(menudelete);
//                tbexcelcategoryService.edit(menudelete);
            }
        }

        if(menusaves6.size()>0){
            tbexcelcategoryService.save(menusaves6);
        }
        if(menuedits6.size()>0){
            tbexcelcategoryService.edit(menuedits6);
        }





        List<PageData> menuedits7 = new ArrayList<>();
        List<PageData> menusaves7 = new ArrayList<>();
        for(Resmenus28 menus28:accountsresmenus28){
            if(menus28.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("excel_id",menus28.getExcel_id());
                pmenu.put("user_id",menus28.getUser_id());
                List<PageData> menutablebyId = tbexceluserService.findById(pmenu);
                if( menutablebyId!=null && menutablebyId.size()!=0){
                    PageData menuedit= new PageData();
                    menuedit.put("excel_id",menus28.getExcel_id());
                    menuedit.put("user_id",menus28.getUser_id());
                    menuedit.put("status",menus28.getStatus());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits7.add(menuedit);

                }else{
                    PageData menusave= new PageData();
                    menusave.put("excel_id",menus28.getExcel_id());
                    menusave.put("user_id",menus28.getUser_id());
                    menusave.put("status",menus28.getStatus());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves7.add(menusave);

                }
            }

            if(menus28.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("excel_id",menus28.getExcel_id());
                menudelete.put("user_id",menus28.getUser_id());
                menudelete.put("status",menus28.getStatus());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits7.add(menudelete);

            }
        }



        if(menusaves7.size()>0){
            tbexceluserService.save(menusaves7);
        }

        if(menuedits7.size()>0){
            tbexceluserService.edit(menuedits7);
        }


        List<PageData> menuedits8 = new ArrayList<>();
        List<PageData> menusaves8 = new ArrayList<>();
        for(Resmenus29 menus29:accountsresmenus29){
            if(menus29.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("id",menus29.getId());
                PageData menubyId = tbgroupreportService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("id",menus29.getId());
                    menuedit.put("task_id",menus29.getTask_id());
                    menuedit.put("taskdetail_id",menus29.getTaskdetail_id());
                    menuedit.put("againtaskid",menus29.getAgaintaskid());
                    menuedit.put("remarkUn",menus29.getRemarkUn());
                    menuedit.put("unit_id",menus29.getUnit_id());
                    menuedit.put("site_id",menus29.getSite_id());
                    menuedit.put("total_issue",menus29.getTotal_issue());
                    menuedit.put("group_id",menus29.getGroup_id());
                    menuedit.put("view_id",menus29.getView_id());
                    menuedit.put("user_id",menus29.getUser_id());
                    menuedit.put("type",menus29.getType());
                    menuedit.put("status",menus29.getStatus());
                    menuedit.put("solve",menus29.getSolve());
                    menuedit.put("create_time",menus29.getCreate_time());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedit.put("solveask",menus29.getSolveask());
                    menuedit.put("reconfire",menus29.getReconfire());
                    menuedits8.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("id",menus29.getId());
                    menusave.put("task_id",menus29.getTask_id());
                    menusave.put("taskdetail_id",menus29.getTaskdetail_id());
                    menusave.put("againtaskid",menus29.getAgaintaskid());
                    menusave.put("remarkUn",menus29.getRemarkUn());
                    menusave.put("unit_id",menus29.getUnit_id());
                    menusave.put("site_id",menus29.getSite_id());
                    menusave.put("total_issue",menus29.getTotal_issue());
                    menusave.put("group_id",menus29.getGroup_id());
                    menusave.put("view_id",menus29.getView_id());
                    menusave.put("user_id",menus29.getUser_id());
                    menusave.put("type",menus29.getType());
                    menusave.put("status",menus29.getStatus());
                    menusave.put("solve",menus29.getSolve());
                    menusave.put("create_time",menus29.getCreate_time());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusave.put("solveask",menus29.getSolveask());
                    menusave.put("reconfire",menus29.getReconfire());
                    menusaves8.add(menusave);
                }
            }

            if(menus29.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("id",menus29.getId());
                menudelete.put("task_id",menus29.getTask_id());
                menudelete.put("taskdetail_id",menus29.getTaskdetail_id());
                menudelete.put("againtaskid",menus29.getAgaintaskid());
                menudelete.put("remarkUn",menus29.getRemarkUn());
                menudelete.put("unit_id",menus29.getUnit_id());
                menudelete.put("site_id",menus29.getSite_id());
                menudelete.put("total_issue",menus29.getTotal_issue());
                menudelete.put("group_id",menus29.getGroup_id());
                menudelete.put("view_id",menus29.getView_id());
                menudelete.put("user_id",menus29.getUser_id());
                menudelete.put("type",menus29.getType());
                menudelete.put("status",menus29.getStatus());
                menudelete.put("solve",menus29.getSolve());
                menudelete.put("create_time",menus29.getCreate_time());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menudelete.put("solveask",menus29.getSolveask());
                menudelete.put("reconfire",menus29.getReconfire());
                menuedits8.add(menudelete);
            }
        }

        if(menusaves8.size()>0){
            tbgroupreportService.save(menusaves8);
            for(PageData pagedata:menusaves8){
                PageData byId1 = sysuserService.findById(pagedata);
                String phone = byId1.getString("phone");
                String s = sendSms5(phone).trim().replaceAll("\n", "");
                System.out.println(s);
            }
        }
        if(menuedits8.size()>0){
            tbgroupreportService.edit(menuedits8);
        }






        List<PageData> menuedits9 = new ArrayList<>();
        List<PageData> menusaves9 = new ArrayList<>();
        for(Resmenus30 menus30:accountsresmenus30){
            if(menus30.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("id",menus30.getId());
                PageData menubyId = tbmessageService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("id",menus30.getId());
                    menuedit.put("title",menus30.getTitle());
                    menuedit.put("content",menus30.getContent());
                    menuedit.put("type",menus30.getType());
                    menuedit.put("status",menus30.getStatus());
                    menuedit.put("user_id",menus30.getUser_id());
                    menuedit.put("created_time",menus30.getCreated_time());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits9.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("id",menus30.getId());
                    menusave.put("title",menus30.getTitle());
                    menusave.put("content",menus30.getContent());
                    menusave.put("type",menus30.getType());
                    menusave.put("status",menus30.getStatus());
                    menusave.put("user_id",menus30.getUser_id());
                    menusave.put("created_time",menus30.getCreated_time());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves9.add(menusave);
                }
            }

            if(menus30.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("id",menus30.getId());
                menudelete.put("title",menus30.getTitle());
                menudelete.put("content",menus30.getContent());
                menudelete.put("type",menus30.getType());
                menudelete.put("status",menus30.getStatus());
                menudelete.put("user_id",menus30.getUser_id());
                menudelete.put("created_time",menus30.getCreated_time());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits9.add(menudelete);
            }
        }

        if(menusaves9.size()>0){
            tbmessageService.save(menusaves9);
        }

        if(menuedits9.size()>0){
            tbmessageService.edit(menuedits9);
        }





        List<PageData> menuedits10 = new ArrayList<>();
        List<PageData> menusaves10 = new ArrayList<>();
        for(Resmenus31 menus31:accountsresmenus31){
            if(menus31.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("message_id",menus31.getMessage_id());
                pmenu.put("user_id",menus31.getUser_id());
                List<PageData> menutablebyId = tbmessageuserService.findById(pmenu);
                if(menutablebyId!=null&&menutablebyId.size()!=0){
                    PageData menuedit= new PageData();
                    menuedit.put("message_id",menus31.getMessage_id());
                    menuedit.put("status",menus31.getStatus());
                    menuedit.put("user_id",menus31.getUser_id());
                    menuedit.put("read_time",menus31.getRead_time());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits10.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("message_id",menus31.getMessage_id());
                    menusave.put("status",menus31.getStatus());
                    menusave.put("user_id",menus31.getUser_id());
                    menusave.put("read_time",menus31.getRead_time());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves10.add(menusave);
                }
            }


            if(menus31.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("message_id",menus31.getMessage_id());
                menudelete.put("status",menus31.getStatus());
                menudelete.put("user_id",menus31.getUser_id());
                menudelete.put("read_time",menus31.getRead_time());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits10.add(menudelete);
            }
        }



        if(menusaves10.size()>0){
            tbmessageuserService.save(menusaves10);
        }
        if(menuedits10.size()>0){
            tbmessageuserService.edit(menuedits10);
        }






        List<PageData> menuedits11 = new ArrayList<>();
        List<PageData> menusaves11 = new ArrayList<>();
        for(Resmenus32 menus32:accountsresmenus32){
            if(menus32.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("norm_id",menus32.getNorm_id());
                PageData menubyId = tbnormService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("norm_id",menus32.getNorm_id());
                    menuedit.put("excel_id",menus32.getExcel_id());
                    menuedit.put("name",menus32.getName());
                    menuedit.put("unit",menus32.getUnit());
                    menuedit.put("content",menus32.getContent());
                    menuedit.put("total_score",menus32.getTotal_score());
                    menuedit.put("score_time",menus32.getScore_time());
                    menuedit.put("totlecheck",menus32.getTotlecheck());
                    menuedit.put("created_time",menus32.getCreated_time());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits11.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("norm_id",menus32.getNorm_id());
                    menusave.put("excel_id",menus32.getExcel_id());
                    menusave.put("name",menus32.getName());
                    menusave.put("unit",menus32.getUnit());
                    menusave.put("content",menus32.getContent());
                    menusave.put("total_score",menus32.getTotal_score());
                    menusave.put("score_time",menus32.getScore_time());
                    menusave.put("totlecheck",menus32.getTotlecheck());
                    menusave.put("created_time",menus32.getCreated_time());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves11.add(menusave);
                }
            }

            if(menus32.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("norm_id",menus32.getNorm_id());
                menudelete.put("excel_id",menus32.getExcel_id());
                menudelete.put("name",menus32.getName());
                menudelete.put("unit",menus32.getUnit());
                menudelete.put("content",menus32.getContent());
                menudelete.put("total_score",menus32.getTotal_score());
                menudelete.put("score_time",menus32.getScore_time());
                menudelete.put("totlecheck",menus32.getTotlecheck());
                menudelete.put("created_time",menus32.getCreated_time());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits11.add(menudelete);
            }
        }

        if(menusaves11.size()>0){
            tbnormService.save(menusaves11);
        }

        if(menuedits11.size()>0){
            tbnormService.edit(menuedits11);
        }




        List<PageData> menuedits12 = new ArrayList<>();
        List<PageData> menusaves12 = new ArrayList<>();
        for(Resmenus33 menus33:accountsresmenus33){
            if(menus33.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("id",menus33.getId());
                PageData menubyId = tbnormdetailService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("id",menus33.getId());
                    menuedit.put("norm_id",menus33.getNorm_id());
                    menuedit.put("serial",menus33.getSerial());
                    menuedit.put("item",menus33.getItem());
                    menuedit.put("untitled",menus33.getUntitled());
                    menuedit.put("content",menus33.getContent());
                    menuedit.put("total_score",menus33.getTotal_score());
                    menuedit.put("score",menus33.getScore());
                    menuedit.put("mode",menus33.getMode());
                    menuedit.put("standard",menus33.getStandard());
                    menuedit.put("totlecheck",menus33.getTotlecheck());
                    menuedit.put("type",menus33.getType());
                    menuedit.put("parent_id",menus33.getParent_id());
                    menuedit.put("created_time",menus33.getCreated_time());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits12.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("id",menus33.getId());
                    menusave.put("norm_id",menus33.getNorm_id());
                    menusave.put("serial",menus33.getSerial());
                    menusave.put("item",menus33.getItem());
                    menusave.put("untitled",menus33.getUntitled());
                    menusave.put("content",menus33.getContent());
                    menusave.put("total_score",menus33.getTotal_score());
                    menusave.put("score",menus33.getScore());
                    menusave.put("mode",menus33.getMode());
                    menusave.put("standard",menus33.getStandard());
                    menusave.put("totlecheck",menus33.getTotlecheck());
                    menusave.put("type",menus33.getType());
                    menusave.put("parent_id",menus33.getParent_id());
                    menusave.put("created_time",menus33.getCreated_time());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves12.add(menusave);
                }
            }

            if(menus33.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("id",menus33.getId());
                menudelete.put("norm_id",menus33.getNorm_id());
                menudelete.put("serial",menus33.getSerial());
                menudelete.put("item",menus33.getItem());
                menudelete.put("untitled",menus33.getUntitled());
                menudelete.put("content",menus33.getContent());
                menudelete.put("total_score",menus33.getTotal_score());
                menudelete.put("score",menus33.getScore());
                menudelete.put("mode",menus33.getMode());
                menudelete.put("standard",menus33.getStandard());
                menudelete.put("totlecheck",menus33.getTotlecheck());
                menudelete.put("type",menus33.getType());
                menudelete.put("parent_id",menus33.getParent_id());
                menudelete.put("created_time",menus33.getCreated_time());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits12.add(menudelete);
            }
        }

        if(menusaves12.size()>0){
            tbnormdetailService.save(menusaves12);
        }
        if(menuedits12.size()>0){
            tbnormdetailService.edit(menuedits12);
        }






        List<PageData> menuedits13 = new ArrayList<>();
        List<PageData> menusaves13 = new ArrayList<>();
        for(Resmenus34 menus34:accountsresmenus34){
            if(menus34.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("id",menus34.getId());
                PageData menubyId = tbnormdetailrowService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("id",menus34.getId());
                    menuedit.put("norm_detail_id",menus34.getNorm_detail_id());
                    menuedit.put("row_id",menus34.getRow_id());
                    menuedit.put("score",menus34.getScore());
                    menuedit.put("content",menus34.getContent());
                    menuedit.put("created_time",menus34.getCreated_time());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits13.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("id",menus34.getId());
                    menusave.put("norm_detail_id",menus34.getNorm_detail_id());
                    menusave.put("row_id",menus34.getRow_id());
                    menusave.put("score",menus34.getScore());
                    menusave.put("content",menus34.getContent());
                    menusave.put("created_time",menus34.getCreated_time());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves13.add(menusave);
                }
            }

            if(menus34.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("id",menus34.getId());
                menudelete.put("norm_detail_id",menus34.getNorm_detail_id());
                menudelete.put("row_id",menus34.getRow_id());
                menudelete.put("score",menus34.getScore());
                menudelete.put("content",menus34.getContent());
                menudelete.put("created_time",menus34.getCreated_time());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits13.add(menudelete);
            }
        }
        if(menusaves13.size()>0){
            tbnormdetailrowService.save(menusaves13);
        }
        if(menuedits13.size()>0){
            tbnormdetailrowService.edit(menuedits13);
        }




        List<PageData> menuedits14 = new ArrayList<>();
        List<PageData> menusaves14 = new ArrayList<>();
        for(Resmenus35 menus35:accountsresmenus35){
            if(menus35.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("task_id",menus35.getTask_id());
                PageData menubyId = tbtaskService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("task_id",menus35.getTask_id());
                    menuedit.put("task_name",menus35.getTask_name());
                    menuedit.put("unit_id",menus35.getUnit_id());
                    menuedit.put("site_id",menus35.getSite_id());
                    menuedit.put("star_time",menus35.getStar_time());
                    menuedit.put("end_time",menus35.getEnd_time());
                    menuedit.put("location",menus35.getLocation());
                    menuedit.put("frequency",menus35.getFrequency());
                    menuedit.put("created_time",menus35.getCreated_time());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedit.put("totlecheck",menus35.getTotlecheck());
                    menuedit.put("type",menus35.getType());
                    menuedit.put("totolequestion",menus35.getTotolequestion());
                    menuedit.put("user_id",menus35.getUser_id());
                    menuedit.put("hascheck",menus35.getHascheck());
                    menuedit.put("status",menus35.getStatus());
                    menuedits14.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("task_id",menus35.getTask_id());
                    menusave.put("task_name",menus35.getTask_name());
                    menusave.put("unit_id",menus35.getUnit_id());
                    menusave.put("site_id",menus35.getSite_id());
                    menusave.put("star_time",menus35.getStar_time());
                    menusave.put("end_time",menus35.getEnd_time());
                    menusave.put("location",menus35.getLocation());
                    menusave.put("frequency",menus35.getFrequency());
                    menusave.put("created_time",menus35.getCreated_time());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusave.put("totlecheck",menus35.getTotlecheck());
                    menusave.put("type",menus35.getType());
                    menusave.put("totolequestion",menus35.getTotolequestion());
                    menusave.put("user_id",menus35.getUser_id());
                    menusave.put("hascheck",menus35.getHascheck());
                    menusave.put("status",menus35.getStatus());
                    menusaves14.add(menusave);
                }
            }

            if(menus35.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("task_id",menus35.getTask_id());
                menudelete.put("task_name",menus35.getTask_name());
                menudelete.put("unit_id",menus35.getUnit_id());
                menudelete.put("site_id",menus35.getSite_id());
                menudelete.put("star_time",menus35.getStar_time());
                menudelete.put("end_time",menus35.getEnd_time());
                menudelete.put("location",menus35.getLocation());
                menudelete.put("frequency",menus35.getFrequency());
                menudelete.put("created_time",menus35.getCreated_time());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menudelete.put("totlecheck",menus35.getTotlecheck());
                menudelete.put("type",menus35.getType());
                menudelete.put("totolequestion",menus35.getTotolequestion());
                menudelete.put("user_id",menus35.getUser_id());
                menudelete.put("hascheck",menus35.getHascheck());
                menudelete.put("status",menus35.getStatus());
                menuedits14.add(menudelete);
            }
        }

        if(menusaves14.size()>0){
            tbtaskService.save(menusaves14);
        }
        if(menuedits14.size()>0){
            for(PageData pagedata : menuedits14){
                PageData byId = tbtaskService.findById(pagedata);
                String star_time = byId.getString("star_time");
                String end_time = byId.getString("end_time");
                String star_time1 = pagedata.getString("star_time");
                String end_time1 = pagedata.getString("end_time");
                if(!star_time.equals(star_time1) || !end_time.equals(end_time1)){
                    List<PageData> menubyId = tbtaskdetailService.findByIdList(pagedata);
                    for(PageData pagedata1:menubyId){
                        String user_id = pagedata1.getString("user_id");
                        String user_id1 = pagedata.getString("user_id");
                        if(!user_id.equals(user_id1)){
                            PageData byId1 = sysuserService.findById(pagedata1);
                            String phone = byId1.getString("phone");
                            String s = sendSms4(phone).trim().replaceAll("\n", "");
                            System.out.println(s);
                        }
                    }
                }
            }
            tbtaskService.edit(menuedits14);
        }







        List<PageData> menuedits15 = new ArrayList<>();
        List<PageData> menusaves15 = new ArrayList<>();
        for(Resmenus36 menus36:accountsresmenus36){
            if(menus36.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("task_id",menus36.getTask_id());
                PageData menubyId = tbtaskdeletestatusService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("task_id",menus36.getTask_id());
                    menuedit.put("status",menus36.getStatus());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits15.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("task_id",menus36.getTask_id());
                    menusave.put("status",menus36.getStatus());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves15.add(menusave);
                }
            }

            if(menus36.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("task_id",menus36.getTask_id());
                menudelete.put("status",menus36.getStatus());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits15.add(menudelete);
            }
        }

        if(menusaves15.size()>0){
            tbtaskdeletestatusService.save(menusaves15);
        }
        if(menuedits15.size()>0){
            tbtaskdeletestatusService.edit(menuedits15);
        }








        List<PageData> menuedits16 = new ArrayList<>();
        List<PageData> menusaves16 = new ArrayList<>();
        for(Resmenus37 menus37:accountsresmenus37){
            if(menus37.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("taskdetail_id",menus37.getTaskdetail_id());
                PageData menubyId = tbtaskdetailService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("taskdetail_id",menus37.getTaskdetail_id());
                    menuedit.put("task_id",menus37.getTask_id());
                    menuedit.put("totlequestion",menus37.getTotlequestion());
                    menuedit.put("totlecheck",menus37.getTotlecheck());
                    menuedit.put("hascheck",menus37.getHascheck());
                    menuedit.put("created_time",menus37.getCreated_time());
                    menuedit.put("group_id",menus37.getGroup_id());
                    menuedit.put("user_id",menus37.getUser_id());
                    menuedit.put("status",menus37.getStatus());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedit.put("type",menus37.getType());
                    menuedit.put("toperson",menus37.getToperson());
                    menuedits16.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("taskdetail_id",menus37.getTaskdetail_id());
                    menusave.put("task_id",menus37.getTask_id());
                    menusave.put("totlequestion",menus37.getTotlequestion());
                    menusave.put("totlecheck",menus37.getTotlecheck());
                    menusave.put("hascheck",menus37.getHascheck());
                    menusave.put("created_time",menus37.getCreated_time());
                    menusave.put("group_id",menus37.getGroup_id());
                    menusave.put("user_id",menus37.getUser_id());
                    menusave.put("status",menus37.getStatus());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusave.put("type",menus37.getType());
                    menusave.put("toperson",menus37.getToperson());
                    menusaves16.add(menusave);
                }
            }

            if(menus37.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("taskdetail_id",menus37.getTaskdetail_id());
                menudelete.put("task_id",menus37.getTask_id());
                menudelete.put("totlequestion",menus37.getTotlequestion());
                menudelete.put("totlecheck",menus37.getTotlecheck());
                menudelete.put("hascheck",menus37.getHascheck());
                menudelete.put("created_time",menus37.getCreated_time());
                menudelete.put("group_id",menus37.getGroup_id());
                menudelete.put("user_id",menus37.getUser_id());
                menudelete.put("status",menus37.getStatus());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menudelete.put("type",menus37.getType());
                menudelete.put("toperson",menus37.getToperson());
                menuedits16.add(menudelete);
            }
        }
        if(menusaves16.size()>0){
            tbtaskdetailService.save(menusaves16);
            for(PageData pagedata:menusaves16){
                PageData menubyId = tbtaskService.findById(pagedata);
                String user_id = menubyId.getString("user_id");
                String type = menubyId.getString("type");
               if(type.equals("2")){
                   PageData byId = sysuserService.findById(pagedata);
                   String phone = byId.getString("phone");
                   String s = sendSms3(phone).trim().replaceAll("\n", "");
                   System.out.println(s);
               }else{
                    if(!pagedata.getString("user_id").equals(user_id)){
                        PageData byId = sysuserService.findById(pagedata);
                        String phone = byId.getString("phone");
                        String s = sendSms3(phone).trim().replaceAll("\n", "");
                        System.out.println(s);
                    }
               }
            }
        }


        if(menuedits16.size()>0){
            tbtaskdetailService.edit(menuedits16);
        }







        List<PageData> menuedits17 = new ArrayList<>();
        List<PageData> menusaves17 = new ArrayList<>();
        for(Resmenus38 menus38:accountsresmenus38){
            if(menus38.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("tasknormdetailid",menus38.getTasknormdetailid());
                pmenu.put("norm_row_id",menus38.getNorm_row_id());
                List<PageData> menutablebyId = tbtaskdetailcheckrowService.findById(pmenu);
                if(menutablebyId!=null && menutablebyId.size()!=0){
                    PageData menuedit= new PageData();
                    menuedit.put("tasknormdetailid",menus38.getTasknormdetailid());
                    menuedit.put("norm_row_id",menus38.getNorm_row_id());
                    menuedit.put("score_type",menus38.getScore_type());
                    menuedit.put("score",menus38.getScore());
                    menuedit.put("status",menus38.getStatus());
                    menuedit.put("problempicture",menus38.getProblempicture());
                    menuedit.put("remark",menus38.getRemark());
                    menuedit.put("correct",menus38.getCorrect());
                    menuedit.put("correctremark",menus38.getCorrectremark());
                    menuedit.put("correctpicture",menus38.getCorrectpicture());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits17.add(menuedit);
//                    tbtaskdetailcheckrowService.edit(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("tasknormdetailid",menus38.getTasknormdetailid());
                    menusave.put("norm_row_id",menus38.getNorm_row_id());
                    menusave.put("score_type",menus38.getScore_type());
                    menusave.put("score",menus38.getScore());
                    menusave.put("status",menus38.getStatus());
                    menusave.put("problempicture",menus38.getProblempicture());
                    menusave.put("remark",menus38.getRemark());
                    menusave.put("correct",menus38.getCorrect());
                    menusave.put("correctremark",menus38.getCorrectremark());
                    menusave.put("correctpicture",menus38.getCorrectpicture());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves17.add(menusave);
//                    tbtaskdetailcheckrowService.save(menusave);
                }
            }

            if(menus38.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("tasknormdetailid",menus38.getTasknormdetailid());
                menudelete.put("norm_row_id",menus38.getNorm_row_id());
                menudelete.put("score_type",menus38.getScore_type());
                menudelete.put("score",menus38.getScore());
                menudelete.put("status",menus38.getStatus());
                menudelete.put("problempicture",menus38.getProblempicture());
                menudelete.put("remark",menus38.getRemark());
                menudelete.put("correct",menus38.getCorrect());
                menudelete.put("correctremark",menus38.getCorrectremark());
                menudelete.put("correctpicture",menus38.getCorrectpicture());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
//                tbtaskdetailcheckrowService.edit(menudelete);
                menuedits17.add(menudelete);
            }
        }

        if(menusaves17.size()>0){
            tbtaskdetailcheckrowService.save(menusaves17);
        }
        if(menuedits17.size()>0){
            tbtaskdetailcheckrowService.edit(menuedits17);
        }






        List<PageData> menuedits18 = new ArrayList<>();
        List<PageData> menusaves18 = new ArrayList<>();
        for(Resmenus39 menus39:accountsresmenus39){
            if(menus39.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("tasknormid",menus39.getTasknormid());
                PageData menubyId = tbtaskdetailnormService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("tasknormid",menus39.getTasknormid());
                    menuedit.put("task_id",menus39.getTask_id());
                    menuedit.put("taskdetail_id",menus39.getTaskdetail_id());
                    menuedit.put("norm_id",menus39.getNorm_id());
                    menuedit.put("norm_detail_id",menus39.getNorm_detail_id());
                    menuedit.put("totlecheck",menus39.getTotlecheck());
                    menuedit.put("status",menus39.getStatus());
                    menuedit.put("question",menus39.getQuestion());
                    menuedit.put("losescroe",menus39.getLosescroe());
                    menuedit.put("score",menus39.getScore());
                    menuedit.put("hascheck",menus39.getHascheck());
                    menuedit.put("user_id",menus39.getUser_id());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits18.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("tasknormid",menus39.getTasknormid());
                    menusave.put("task_id",menus39.getTask_id());
                    menusave.put("taskdetail_id",menus39.getTaskdetail_id());
                    menusave.put("norm_id",menus39.getNorm_id());
                    menusave.put("norm_detail_id",menus39.getNorm_detail_id());
                    menusave.put("totlecheck",menus39.getTotlecheck());
                    menusave.put("status",menus39.getStatus());
                    menusave.put("question",menus39.getQuestion());
                    menusave.put("losescroe",menus39.getLosescroe());
                    menusave.put("score",menus39.getScore());
                    menusave.put("hascheck",menus39.getHascheck());
                    menusave.put("user_id",menus39.getUser_id());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves18.add(menusave);
                }
            }

            if(menus39.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("tasknormid",menus39.getTasknormid());
                menudelete.put("task_id",menus39.getTask_id());
                menudelete.put("taskdetail_id",menus39.getTaskdetail_id());
                menudelete.put("norm_id",menus39.getNorm_id());
                menudelete.put("norm_detail_id",menus39.getNorm_detail_id());
                menudelete.put("totlecheck",menus39.getTotlecheck());
                menudelete.put("status",menus39.getStatus());
                menudelete.put("question",menus39.getQuestion());
                menudelete.put("losescroe",menus39.getLosescroe());
                menudelete.put("score",menus39.getScore());
                menudelete.put("hascheck",menus39.getHascheck());
                menudelete.put("user_id",menus39.getUser_id());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits18.add(menudelete);
            }
        }

        if(menusaves18.size()>0){
            tbtaskdetailnormService.save(menusaves18);
        }
        if(menuedits18.size()>0){
            tbtaskdetailnormService.edit(menuedits18);
        }






        List<PageData> menuedits19 = new ArrayList<>();
        List<PageData> menusaves19 = new ArrayList<>();
        for(Resmenus40 menus40:accountsresmenus40){
            if(menus40.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("tasknormdetailid",menus40.getTasknormdetailid());
                PageData menubyId = tbtaskdetailnormdetailService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("tasknormdetailid",menus40.getTasknormdetailid());
                    menuedit.put("tasknormid",menus40.getTasknormid());
                    menuedit.put("norm_detail_id",menus40.getNorm_detail_id());
                    menuedit.put("score",menus40.getScore());
                    menuedit.put("isproblem",menus40.getIsproblem());
                    menuedit.put("status",menus40.getStatus());
                    menuedit.put("user_id",menus40.getUser_id());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits19.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("tasknormdetailid",menus40.getTasknormdetailid());
                    menusave.put("tasknormid",menus40.getTasknormid());
                    menusave.put("norm_detail_id",menus40.getNorm_detail_id());
                    menusave.put("score",menus40.getScore());
                    menusave.put("isproblem",menus40.getIsproblem());
                    menusave.put("status",menus40.getStatus());
                    menusave.put("user_id",menus40.getUser_id());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves19.add(menusave);
                }
            }

            if(menus40.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("tasknormdetailid",menus40.getTasknormdetailid());
                menudelete.put("tasknormid",menus40.getTasknormid());
                menudelete.put("norm_detail_id",menus40.getNorm_detail_id());
                menudelete.put("score",menus40.getScore());
                menudelete.put("isproblem",menus40.getIsproblem());
                menudelete.put("status",menus40.getStatus());
                menudelete.put("user_id",menus40.getUser_id());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits19.add(menudelete);
            }
        }
        if(menusaves19.size()>0){
            tbtaskdetailnormdetailService.save(menusaves19);
        }

        if(menuedits19.size()>0){
            tbtaskdetailnormdetailService.edit(menuedits19);
        }






        List<PageData> menuedits20 = new ArrayList<>();
        List<PageData> menusaves20 = new ArrayList<>();
        for(Resmenus41 menus41:accountsresmenus41){
            if(menus41.getDirety().equals("1")){
                PageData pmenu = new PageData();
                pmenu.put("task_id",menus41.getTask_id());
                PageData menubyId = tbtaskinfoService.findById(pmenu);
                if(menubyId!=null){
                    PageData menuedit= new PageData();
                    menuedit.put("task_id",menus41.getTask_id());
                    menuedit.put("usernormrel",menus41.getUsernormrel());
                    menuedit.put("normtext",menus41.getNormtext());
                    menuedit.put("collapseList",menus41.getCollapseList());
                    menuedit.put("normdetailids",menus41.getNormdetailids());
                    menuedit.put("type",menus41.getType());
                    menuedit.put("flag",menus41.getFlag());
                    menuedit.put("direty","0");
                    menuedit.put("delete","0");
                    menuedit.put("updatetime",String.valueOf(new Date().getTime()));
                    menuedits20.add(menuedit);
                }else{
                    PageData menusave= new PageData();
                    menusave.put("task_id",menus41.getTask_id());
                    menusave.put("usernormrel",menus41.getUsernormrel());
                    menusave.put("normtext",menus41.getNormtext());
                    menusave.put("collapseList",menus41.getCollapseList());
                    menusave.put("normdetailids",menus41.getNormdetailids());
                    menusave.put("type",menus41.getType());
                    menusave.put("flag",menus41.getFlag());
                    menusave.put("direty","0");
                    menusave.put("delete","0");
                    menusave.put("updatetime",String.valueOf(new Date().getTime()));
                    menusaves20.add(menusave);
                }
            }


            if(menus41.getDelete().equals("1")){
                PageData menudelete= new PageData();
                menudelete.put("task_id",menus41.getTask_id());
                menudelete.put("usernormrel",menus41.getUsernormrel());
                menudelete.put("normtext",menus41.getNormtext());
                menudelete.put("collapseList",menus41.getCollapseList());
                menudelete.put("normdetailids",menus41.getNormdetailids());
                menudelete.put("type",menus41.getType());
                menudelete.put("flag",menus41.getFlag());
                menudelete.put("direty","0");
                menudelete.put("delete","0");
                menudelete.put("updatetime",String.valueOf(new Date().getTime()));
                menuedits20.add(menudelete);
            }
        }

        if(menusaves20.size()>0){
            tbtaskinfoService.save(menusaves20);
        }
        if(menuedits20.size()>0){
            tbtaskinfoService.edit(menuedits20);
        }



        Map<String, Object> map = new HashMap<String,Object>();
        map.put("accountsresmenus21",accountsresmenus21);
        map.put("accountsresmenus22",accountsresmenus22);
        map.put("accountsresmenus23",accountsresmenus23);
        map.put("accountsresmenus24",accountsresmenus24);
        map.put("accountsresmenus25",accountsresmenus25);
        map.put("accountsresmenus26",accountsresmenus26);
        map.put("accountsresmenus27",accountsresmenus27);
        map.put("accountsresmenus28",accountsresmenus28);
        map.put("accountsresmenus29",accountsresmenus29);
        map.put("accountsresmenus30",accountsresmenus30);
        map.put("accountsresmenus31",accountsresmenus31);
        map.put("accountsresmenus32",accountsresmenus32);
        map.put("accountsresmenus33",accountsresmenus33);
        map.put("accountsresmenus34",accountsresmenus34);
        map.put("accountsresmenus35",accountsresmenus35);
        map.put("accountsresmenus36",accountsresmenus36);
        map.put("accountsresmenus37",accountsresmenus37);
        map.put("accountsresmenus38",accountsresmenus38);
        map.put("accountsresmenus39",accountsresmenus39);
        map.put("accountsresmenus40",accountsresmenus40);
        map.put("accountsresmenus41",accountsresmenus41);
        return ResultModel.success(map);

    }

}

