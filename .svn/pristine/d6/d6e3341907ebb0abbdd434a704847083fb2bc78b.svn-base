package com.fh.service.app.tbtaskdetailcheckrow;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("tbtaskdetailcheckrowService")
public class TbtaskdetailcheckrowService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(List<PageData> pd)throws Exception{
		dao.batchSave("TbtaskdetailcheckrowMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("TbtaskdetailcheckrowMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(List<PageData> pd)throws Exception{
		dao.batchUpdate("TbtaskdetailcheckrowMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TbtaskdetailcheckrowMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TbtaskdetailcheckrowMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public List<PageData> findById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TbtaskdetailcheckrowMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TbtaskdetailcheckrowMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

