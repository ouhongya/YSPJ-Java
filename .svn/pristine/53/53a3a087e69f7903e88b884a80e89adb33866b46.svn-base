package com.fh.controller.system.role;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;

import javax.annotation.Resource;

import com.fh.service.app.sysmenutable.SysmenutableService;
import com.fh.service.app.sysrole.SysroleService;
import com.fh.service.app.sysuser.SysuserService;
import com.fh.service.information.pictures.PicturesService;
import com.fh.util.*;
import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.service.system.menu.MenuService;
import com.fh.service.system.role.RoleService;

/**
 * 类名称：RoleController
 * 创建人：FH 
 * 创建时间：2014年6月30日
 * @version
 */
@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {


	String menuUrl = "role.do"; //菜单地址(权限用)
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="sysuserService")
	private SysuserService sysuserService;
	@Resource(name="sysroleService")
	private SysroleService sysroleService;
	@Resource(name="sysmenutableService")
	private SysmenutableService sysmenutableService;
	@Resource(name="picturesService")
	private PicturesService picturesService;
	
	/**
	 * 权限(增删改查)
	 */
	@RequestMapping(value="/qx")
	public ModelAndView qx()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String msg = pd.getString("msg");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){roleService.updateQx(msg,pd);}
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * K权限
	 */
	@RequestMapping(value="/kfqx")
	public ModelAndView kfqx()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String msg = pd.getString("msg");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){roleService.updateKFQx(msg,pd);}
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * c权限
	 */
	@RequestMapping(value="/gysqxc")
	public ModelAndView gysqxc()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String msg = pd.getString("msg");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){roleService.gysqxc(msg,pd);}
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping
	public ModelAndView list(Page page)throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			
			String roleId = pd.getString("ROLE_ID");
			if(roleId == null || "".equals(roleId)){
				pd.put("ROLE_ID", "1");
			}
			List<Role> roleList = roleService.listAllRoles();				//列出所有部门
			List<Role> roleList_z = roleService.listAllRolesByPId(pd);		//列出此部门的所有下级
			
			List<PageData> kefuqxlist = roleService.listAllkefu(pd);		//管理权限列表
			List<PageData> gysqxlist = roleService.listAllGysQX(pd);		//用户权限列表
			pd = roleService.findObjectById(pd);							//取得点击部门
			mv.addObject("pd", pd);
			mv.addObject("kefuqxlist", kefuqxlist);
			mv.addObject("gysqxlist", gysqxlist);
			mv.addObject("roleList", roleList);
			mv.addObject("roleList_z", roleList_z);
			mv.setViewName("system/role/role_list");
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		
		return mv;
	}
	
	/**
	 * 新增页面
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			mv.setViewName("system/role/role_add");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 保存新增信息
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			String parent_id = pd.getString("PARENT_ID");		//父类角色id
			pd.put("ROLE_ID", parent_id);			
			if("0".equals(parent_id)){
				pd.put("RIGHTS", "");
			}else{
				String rights = roleService.findObjectById(pd).getString("RIGHTS");
				pd.put("RIGHTS", (null == rights)?"":rights);
			}

			pd.put("QX_ID", "");
			
			String UUID = this.get32UUID();
			
				pd.put("GL_ID", UUID);
				pd.put("FX_QX", 0);				//发信权限
				pd.put("FW_QX", 0);				//服务权限
				pd.put("QX1", 0);				//操作权限
				pd.put("QX2", 0);				//产品权限
				pd.put("QX3", 0);				//预留权限
				pd.put("QX4", 0);				//预留权限
				if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){roleService.saveKeFu(pd);}//保存到K权限表
				
				pd.put("U_ID", UUID);
				pd.put("C1", 0);				//每日发信数量
				pd.put("C2", 0);
				pd.put("C3", 0);
				pd.put("C4", 0);
				pd.put("Q1", 0);				//权限1
				pd.put("Q2", 0);				//权限2
				pd.put("Q3", 0);
				pd.put("Q4", 0);
				if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){roleService.saveGYSQX(pd);}//保存到G权限表
				pd.put("QX_ID", UUID);
			
			pd.put("ROLE_ID", UUID);
			pd.put("ADD_QX", "0");
			pd.put("DEL_QX", "0");
			pd.put("EDIT_QX", "0");
			pd.put("CHA_QX", "0");
			if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){roleService.add(pd);}
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 请求编辑
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit( String ROLE_ID )throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("ROLE_ID", ROLE_ID);
			pd = roleService.findObjectById(pd);
			mv.setViewName("system/role/role_edit");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value="/edit")
	@ResponseBody
	public Object edit()throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(pd.getString("company") == null || pd.getString("company").equals("") || pd.getString("phone") == null || pd.getString("phone").equals("") || pd.getString("pictureid") == null || pd.getString("pictureid").equals("") || pd.getString("username") == null || pd.getString("username").equals("") || pd.getString("usernumber") == null || pd.getString("usernumber").equals("")){
				map.put("msg","1");
				return AppUtil.returnObject(new PageData(), map);
			}
			PageData byPhone = sysuserService.findByPhone(pd);
			PageData byName = sysroleService.findByName(pd);
			if(byPhone == null && byName == null){
				String role_id = pd.getString("pictureid");
				String role_name = pd.getString("company");
				String parent_id = "0";
				String status = "0";
				String function_id = pd.getString("usernumber");
				String direty = "0";
				String delete = "0";
				String updatetime = String.valueOf(new Date().getTime());
				List<PageData> menusaves = new ArrayList<>();
				PageData menusave= new PageData();
				menusave.put("role_id",role_id);
				menusave.put("role_name",role_name);
				menusave.put("parent_id",parent_id);
				menusave.put("status",status);
				menusave.put("function_id",function_id);
				menusave.put("direty",direty);
				menusave.put("delete",delete);
				menusave.put("updatetime",updatetime);
				menusaves.add(menusave);
				sysroleService.save(menusaves);

				String zuser = String.valueOf(new Date().getTime());
				List<PageData> menusaveroles = new ArrayList<>();
				PageData menusaverole= new PageData();
				menusaverole.put("role_id",zuser);
				menusaverole.put("role_name","专责");
				menusaverole.put("parent_id",role_id);
				menusaverole.put("status","0");
				menusaverole.put("function_id","1");
				menusaverole.put("direty","0");
				menusaverole.put("delete","0");
				menusaverole.put("updatetime",String.valueOf(new Date().getTime()));
				menusaveroles.add(menusaverole);
				sysroleService.save(menusaveroles);


				String grouper = String.valueOf(new Date().getTime());
				List<PageData> menusavegroups = new ArrayList<>();
				PageData menusavegroup= new PageData();
				menusavegroup.put("role_id",grouper);
				menusavegroup.put("role_name","组长");
				menusavegroup.put("parent_id",role_id);
				menusavegroup.put("status","0");
				menusavegroup.put("function_id","2");
				menusavegroup.put("direty","0");
				menusavegroup.put("delete","0");
				menusavegroup.put("updatetime",String.valueOf(new Date().getTime()));
				menusavegroups.add(menusavegroup);
				sysroleService.save(menusavegroups);


				String juser = String.valueOf(new Date().getTime());
				List<PageData> menusavejs = new ArrayList<>();
				PageData menusavej= new PageData();
				menusavej.put("role_id",juser);
				menusavej.put("role_name","检查员");
				menusavej.put("parent_id",role_id);
				menusavej.put("status","0");
				menusavej.put("function_id","3");
				menusavej.put("direty","0");
				menusavej.put("delete","0");
				menusavej.put("updatetime",String.valueOf(new Date().getTime()));
				menusavejs.add(menusavej);
				sysroleService.save(menusavejs);

				List<PageData> menusave1s = new ArrayList<>();
				PageData menusave1= new PageData();
				menusave1.put("role_id",zuser);
				menusave1.put("menu_id","0");
				menusave1.put("status","0");
				menusave1.put("direty","0");
				menusave1.put("delete","0");
				menusave1.put("updatetime",String.valueOf(new Date().getTime()));
				menusave1s.add(menusave1);
				sysmenutableService.save(menusave1s);

				List<PageData> menusave2s = new ArrayList<>();
				PageData menusave2= new PageData();
				menusave2.put("role_id",zuser);
				menusave2.put("menu_id","2");
				menusave2.put("status","0");
				menusave2.put("direty","0");
				menusave2.put("delete","0");
				menusave2.put("updatetime",String.valueOf(new Date().getTime()));
				menusave2s.add(menusave2);
				sysmenutableService.save(menusave2s);

				List<PageData> menusave3s = new ArrayList<>();
				PageData menusave3= new PageData();
				menusave3.put("role_id",zuser);
				menusave3.put("menu_id","3");
				menusave3.put("status","0");
				menusave3.put("direty","0");
				menusave3.put("delete","0");
				menusave3.put("updatetime",String.valueOf(new Date().getTime()));
				menusave3s.add(menusave3);
				sysmenutableService.save(menusave3s);


				List<PageData> menusave4s = new ArrayList<>();
				PageData menusave4 = new PageData();
				menusave4.put("role_id",zuser);
				menusave4.put("menu_id","4");
				menusave4.put("status","0");
				menusave4.put("direty","0");
				menusave4.put("delete","0");
				menusave4.put("updatetime",String.valueOf(new Date().getTime()));
				menusave4s.add(menusave4);
				sysmenutableService.save(menusave4s);


				List<PageData> menusave5s = new ArrayList<>();
				PageData menusave5 = new PageData();
				menusave5.put("role_id",zuser);
				menusave5.put("menu_id","5");
				menusave5.put("status","0");
				menusave5.put("direty","0");
				menusave5.put("delete","0");
				menusave5.put("updatetime",String.valueOf(new Date().getTime()));
				menusave5s.add(menusave5);
				sysmenutableService.save(menusave5s);


				List<PageData> menusave6s = new ArrayList<>();
				PageData menusave6 = new PageData();
				menusave6.put("role_id",zuser);
				menusave6.put("menu_id","6");
				menusave6.put("status","0");
				menusave6.put("direty","0");
				menusave6.put("delete","0");
				menusave6.put("updatetime",String.valueOf(new Date().getTime()));
				menusave6s.add(menusave6);
				sysmenutableService.save(menusave6s);


				List<PageData> menusave7s = new ArrayList<>();
				PageData menusave7 = new PageData();
				menusave7.put("role_id",grouper);
				menusave7.put("menu_id","0");
				menusave7.put("status","0");
				menusave7.put("direty","0");
				menusave7.put("delete","0");
				menusave7.put("updatetime",String.valueOf(new Date().getTime()));
				menusave7s.add(menusave7);
				sysmenutableService.save(menusave7s);


				List<PageData> menusave8s = new ArrayList<>();
				PageData menusave8 = new PageData();
				menusave8.put("role_id",grouper);
				menusave8.put("menu_id","1");
				menusave8.put("status","0");
				menusave8.put("direty","0");
				menusave8.put("delete","0");
				menusave8.put("updatetime",String.valueOf(new Date().getTime()));
				menusave8s.add(menusave8);
				sysmenutableService.save(menusave8s);


				List<PageData> menusave9s = new ArrayList<>();
				PageData menusave9 = new PageData();
				menusave9.put("role_id",grouper);
				menusave9.put("menu_id","3");
				menusave9.put("status","0");
				menusave9.put("direty","0");
				menusave9.put("delete","0");
				menusave9.put("updatetime",String.valueOf(new Date().getTime()));
				menusave9s.add(menusave9);
				sysmenutableService.save(menusave9s);


				List<PageData> menusave10s = new ArrayList<>();
				PageData menusave10 = new PageData();
				menusave10.put("role_id",grouper);
				menusave10.put("menu_id","6");
				menusave10.put("status","0");
				menusave10.put("direty","0");
				menusave10.put("delete","0");
				menusave10.put("updatetime",String.valueOf(new Date().getTime()));
				menusave10s.add(menusave10);
				sysmenutableService.save(menusave10s);


				List<PageData> menusave11s = new ArrayList<>();
				PageData menusave11 = new PageData();
				menusave11.put("role_id",juser);
				menusave11.put("menu_id","0");
				menusave11.put("status","0");
				menusave11.put("direty","0");
				menusave11.put("delete","0");
				menusave11.put("updatetime",String.valueOf(new Date().getTime()));
				menusave11s.add(menusave11);
				sysmenutableService.save(menusave11s);


				List<PageData> menusave12s = new ArrayList<>();
				PageData menusave12 = new PageData();
				menusave12.put("role_id",juser);
				menusave12.put("menu_id","1");
				menusave12.put("status","0");
				menusave12.put("direty","0");
				menusave12.put("delete","0");
				menusave12.put("updatetime",String.valueOf(new Date().getTime()));
				menusave12s.add(menusave12);
				sysmenutableService.save(menusave12s);



				List<PageData> menusave13s = new ArrayList<>();
				PageData menusave13 = new PageData();
				menusave13.put("role_id",juser);
				menusave13.put("menu_id","3");
				menusave13.put("status","0");
				menusave13.put("direty","0");
				menusave13.put("delete","0");
				menusave13.put("updatetime",String.valueOf(new Date().getTime()));
				menusave13s.add(menusave13);
				sysmenutableService.save(menusave13s);

				List<PageData> menusave14s = new ArrayList<>();
				PageData menusave14 = new PageData();
				menusave14.put("role_id",juser);
				menusave14.put("menu_id","6");
				menusave14.put("status","0");
				menusave14.put("direty","0");
				menusave14.put("delete","0");
				menusave14.put("updatetime",String.valueOf(new Date().getTime()));
				menusave14s.add(menusave14);
				sysmenutableService.save(menusave14s);


				List<PageData> menusaveusers = new ArrayList<>();
				PageData menusaveuser= new PageData();
				String userid = String.valueOf(new Date().getTime());
				menusaveuser.put("user_id",userid);
				menusaveuser.put("password", pd.getString("passwd"));
				menusaveuser.put("name",pd.getString("username"));
				menusaveuser.put("role_id",zuser);
				menusaveuser.put("status","0");
				menusaveuser.put("phone",pd.getString("phone"));
				menusaveuser.put("company_id",role_id);
				menusaveuser.put("direty","0");
				menusaveuser.put("delete","0");
				menusaveuser.put("updatetime",String.valueOf(new Date().getTime()));
				menusaveuser.put("isupdate","0");
				menusaveusers.add(menusaveuser);
				sysuserService.save(menusaveusers);
				map.put("name",pd.getString("username"));
				map.put("phone",pd.getString("phone"));
				map.put("msg","0");

			}else if(byPhone != null) {
				map.put("msg","1");
			}else if(byPhone == null &&  byName != null){
				String role_id = byName.getString("role_id");
				PageData editnumber= new PageData();
				editnumber.put("role_id",byName.getString("role_id"));
				editnumber.put("updatetime",String.valueOf(new Date().getTime()));
				editnumber.put("function_id",pd.getString("usernumber"));
				sysroleService.editnumber(editnumber);

				String PICTURES_ID = pd.getString("pictureid");
				pd.put("PICTURES_ID",PICTURES_ID);
				PageData byId = picturesService.findById(pd);
				pd.put("PICTURES_ID",role_id);
				PageData byId1 = picturesService.findById(pd);
				byId1.put("PATH",byId.getString("PATH"));
				picturesService.edit(byId1);

				List<PageData> findrolelist = sysroleService.findrolelist(byName);
				List<PageData> menusaveusers = new ArrayList<>();
				PageData menusaveuser= new PageData();
				String userid = String.valueOf(new Date().getTime());
				menusaveuser.put("user_id",userid);
				menusaveuser.put("password", pd.getString("passwd"));
				menusaveuser.put("name",pd.getString("username"));
				menusaveuser.put("role_id",findrolelist.get(0).getString("role_id"));
				menusaveuser.put("status","0");
				menusaveuser.put("phone",pd.getString("phone"));
				menusaveuser.put("company_id",byName.getString("role_id"));
				menusaveuser.put("direty","0");
				menusaveuser.put("delete","0");
				menusaveuser.put("updatetime",String.valueOf(new Date().getTime()));
				menusaveuser.put("isupdate","0");
				menusaveusers.add(menusaveuser);
				sysuserService.save(menusaveusers);
				map.put("name",pd.getString("username"));
				map.put("phone",pd.getString("phone"));
				map.put("msg","0");
			}
		} catch(Exception e){
			map.put("msg","1");
		}
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 请求角色菜单授权页面
	 */
	@RequestMapping(value="/auth")
	public String auth(@RequestParam String ROLE_ID,Model model)throws Exception{
		
		try{
			List<Menu> menuList = menuService.listAllMenu();
			Role role = roleService.getRoleById(ROLE_ID);
			String roleRights = role.getRIGHTS();
			if(Tools.notEmpty(roleRights)){
				for(Menu menu : menuList){
					menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
					if(menu.isHasMenu()){
						List<Menu> subMenuList = menu.getSubMenu();
						for(Menu sub : subMenuList){
							sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
						}
					}
				}
			}
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			model.addAttribute("roleId", ROLE_ID);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return "authorization";
	}
	
	/**
	 * 请求角色按钮授权页面
	 */
	@RequestMapping(value="/button")
	public ModelAndView button(@RequestParam String ROLE_ID,@RequestParam String msg,Model model)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			List<Menu> menuList = menuService.listAllMenu();
			Role role = roleService.getRoleById(ROLE_ID);
			
			String roleRights = "";
			if("add_qx".equals(msg)){
				roleRights = role.getADD_QX();
			}else if("del_qx".equals(msg)){
				roleRights = role.getDEL_QX();
			}else if("edit_qx".equals(msg)){
				roleRights = role.getEDIT_QX();
			}else if("cha_qx".equals(msg)){
				roleRights = role.getCHA_QX();
			}
			
			if(Tools.notEmpty(roleRights)){
				for(Menu menu : menuList){
					menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
					if(menu.isHasMenu()){
						List<Menu> subMenuList = menu.getSubMenu();
						for(Menu sub : subMenuList){
							sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
						}
					}
				}
			}
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			//System.out.println(json);
			json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			mv.addObject("zTreeNodes", json);
			mv.addObject("roleId", ROLE_ID);
			mv.addObject("msg", msg);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("system/role/role_button");
		return mv;
	}
	
	/**
	 * 保存角色菜单权限
	 */
	@RequestMapping(value="/auth/save")
	public void saveAuth(@RequestParam String ROLE_ID,@RequestParam String menuIds,PrintWriter out)throws Exception{
		PageData pd = new PageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
				if(null != menuIds && !"".equals(menuIds.trim())){
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
					Role role = roleService.getRoleById(ROLE_ID);
					role.setRIGHTS(rights.toString());
					roleService.updateRoleRights(role);
					pd.put("rights",rights.toString());
				}else{
					Role role = new Role();
					role.setRIGHTS("");
					role.setROLE_ID(ROLE_ID);
					roleService.updateRoleRights(role);
					pd.put("rights","");
				}
					
					pd.put("roleId", ROLE_ID);
					roleService.setAllRights(pd);
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 保存角色按钮权限
	 */
	@RequestMapping(value="/roleButton/save")
	public void orleButton(@RequestParam String ROLE_ID,@RequestParam String menuIds,@RequestParam String msg,PrintWriter out)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
				if(null != menuIds && !"".equals(menuIds.trim())){
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
					pd.put("value",rights.toString());
				}else{
					pd.put("value","");
				}
				pd.put("ROLE_ID", ROLE_ID);
				roleService.updateQx(msg,pd);
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object deleteRole(@RequestParam String ROLE_ID)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		String errInfo = "";
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				pd.put("ROLE_ID", ROLE_ID);
				List<Role> roleList_z = roleService.listAllRolesByPId(pd);		//列出此部门的所有下级
				if(roleList_z.size() > 0){
					errInfo = "false";
				}else{
					
					List<PageData> userlist = roleService.listAllUByRid(pd);
					List<PageData> appuserlist = roleService.listAllAppUByRid(pd);
					if(userlist.size() > 0 || appuserlist.size() > 0){
						errInfo = "false2";
					}else{
					roleService.deleteRoleById(ROLE_ID);
					roleService.deleteKeFuById(ROLE_ID);
					roleService.deleteGById(ROLE_ID);
					errInfo = "success";
					}
				}
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	

}
