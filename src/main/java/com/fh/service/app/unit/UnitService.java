package com.fh.service.app.unit;

import java.util.List;

import javax.annotation.Resource;

import com.fh.entity.app.*;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("unitService")
public class UnitService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("UnitMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("UnitMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("UnitMapper.edit", pd);
	}

	/*
	 *列表
	 */
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UnitMapper.datalistPage", page);
	}

	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UnitMapper.listAll", pd);
	}


	/*
	 *列表(全部)
	 */
	public List<PageData> listNAME(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UnitMapper.listAllNAME", pd);
	}

	/*
	 *列表(全部)
	 */
	public List<PageData> listAllone(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UnitMapper.listAllone", pd);
	}

	/*
	 *列表(全部)
	 */
	public List<PageData> listAllUNIT(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UnitMapper.listAllUNIT", pd);
	}

	/*
	 *列表(全部)
	 */
	public List<PageData> listtop(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UnitMapper.listtop", pd);
	}



	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UnitMapper.findById", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findByNAMEpd(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UnitMapper.findByNAMEpd", pd);
	}


	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("UnitMapper.deleteAll", ArrayDATA_IDS);
	}


	/*
	 * 回收站列表
	 */
	public List<PageData> listtask(Page page)throws Exception{
		return  (List<PageData>) dao.findForList("TaskMapper.datalistPage", page);
	}



	public List<PageData> listtaskids(String[]  ids)throws Exception{
		return  (List<PageData>) dao.findForList("UnitMapper.listtaskids", ids);
	}




	/*
	 * 回收站查询
	 */
	public List<PageData> listtaskfind(PageData pd)throws Exception{
		return  (List<PageData>) dao.findForList("TaskMapper.queryNormName", pd);
	}



	public void recordTask(PageData pd)throws Exception{
		dao.update("TaskMapper.recordTask", pd);
	}

	public void solveTask(PageData pd)throws Exception{
		dao.update("TaskMapper.solveTask", pd);
	}


	/*
	 *列表(全部)
	 */
	public List<PageData> listAllByIds(String[] UNIT_IDS)throws Exception{
		return (List<PageData>)dao.findForList("UnitMapper.listAllByIds", UNIT_IDS);
	}



	/*
	 *当前登录用户tb_execel 的id
	 */
	public List<PageData> listUserExecel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ExcelSettingMapper.listUserExecel", pd);
	}



	/*
	 *当前登录用户tb_execel
	 */
	public List<PageData> listUserExecelsecond(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ExcelSettingMapper.listUserExecelsecond", pd);
	}


	/*
	 *当前登录用户tb_execel 的id
	 */
	public List<PageData> listUsertaskid(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ExcelSettingMapper.listUsertaskid", pd);
	}


	/*
	 *当前登录用户tb_execel 的id
	 */
	public String queryNormDetailName(String id)throws Exception{
		return (String)dao.findForObject("ExcelSettingMapper.queryNormDetailName", id);
	}

	public String queryNormDetailSerial(String id)throws Exception{
		return (String)dao.findForObject("ExcelSettingMapper.queryNormDetailSerial", id);
	}

	public String queryIssueTime(String id) throws Exception {
		return (String)dao.findForObject("ExcelSettingMapper.queryIssueTime", id);
	}

	public String querytaskcheckId(String id) throws Exception {
		return (String)dao.findForObject("ExcelSettingMapper.querytaskcheckId", id);
	}


	/**
	 * 检查任务详情
	 *
	 * @param pd
	 * @return
	 */
	public List<TaskCensorRes> checkingTaskListapi(PageData pd) throws Exception {
		List<TaskCensorRes> pageData = (List<TaskCensorRes>) dao.findForList("UnitMapper.checkingTaskList", pd);
		for (TaskCensorRes pageDatum : pageData) {
			for (CensorRow censorRow : pageDatum.getCensorRowList()) {
				if (censorRow.getCensorRowIssueList().size() != 0) {
					for (CensorRowIssue censorRowIssue : censorRow.getCensorRowIssueList()) {
						String id = censorRowIssue.getId();
						List<CensorRowIssueImage> id1 = (List<CensorRowIssueImage>) dao.findForList("TaskMapper.censorRowIssueImageList", id);
						censorRowIssue.setCensorRowIssueImageList(id1);
					}
				}
			}
		}
		return pageData;
	}



}

