package com.fh.service.app.tbexceluser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("tbexceluserService")
public class TbexceluserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(List<PageData> pd)throws Exception{
		dao.batchSave("TbexceluserMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("TbexceluserMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(List<PageData> pd)throws Exception{
		dao.batchUpdate("TbexceluserMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TbexceluserMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TbexceluserMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public List<PageData> findById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TbexceluserMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TbexceluserMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

