package com.fh.service.app.sysuser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("sysuserService")
public class SysuserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save( List<PageData> pd)throws Exception{
		dao.batchSave("SysuserMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("SysuserMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit( List<PageData> pd)throws Exception{
		dao.batchUpdate("SysuserMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SysuserMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SysuserMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysuserMapper.findById", pd);
	}



	/*
	 * 通过电话获取获取数据
	 */
	public PageData findByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SysuserMapper.findByPhone", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SysuserMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

