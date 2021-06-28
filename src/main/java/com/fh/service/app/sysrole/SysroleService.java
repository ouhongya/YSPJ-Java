package com.fh.service.app.sysrole;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("sysroleService")
public class SysroleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save( List<PageData> pd)throws Exception{
		dao.batchSave("SysroleMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("SysroleMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(List<PageData> pd)throws Exception{
		dao.batchUpdate("SysroleMapper.edit", pd);
	}

	/*
	 * 修改
	 */
	public void editnumber(PageData pd)throws Exception{
		dao.update("SysroleMapper.editnumber", pd);
	}


	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysroleMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysroleMapper.listAll", pd);
	}


	public List<PageData> findrolelist(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysroleMapper.findrolelist", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysroleMapper.findById", pd);
	}

	/*
	 * 通过公司名字获取
	 */
	public PageData findByName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysroleMapper.findByName", pd);
	}




	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysroleMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

