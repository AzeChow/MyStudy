/*
 * Created on 2004-9-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.warning.action;

import java.util.List;
import java.util.Map;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.warning.dao.WarningDao;
import com.bestway.common.warning.entity.PlanTask;
import com.bestway.common.warning.entity.TempWarningItem;
import com.bestway.common.warning.entity.WarningItem;
import com.bestway.common.warning.entity.WarningThread;
import com.bestway.common.warning.logic.PlanTaskJobLogic;
import com.bestway.common.warning.logic.WarningJobLogic;
import com.bestway.common.warning.logic.WarningLogic;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@AuthorityClassAnnotation(caption = "预警管理", index = 21)
public class WarningActionImpl extends BaseActionImpl implements WarningAction {
	private WarningLogic		warningLogic		= null;
	private WarningDao			warningDao			= null;
	private WarningJobLogic		warningJobLogic		= null;
	private PlanTaskJobLogic	planTaskJobLogic	= null;

	
	
	public PlanTaskJobLogic getPlanTaskJobLogic() {
		return planTaskJobLogic;
	}

	public void setPlanTaskJobLogic(PlanTaskJobLogic planTaskJobLogic) {
		this.planTaskJobLogic = planTaskJobLogic;
	}

	public WarningJobLogic getWarningJobLogic() {
		return warningJobLogic;
	}

	public void setWarningJobLogic(WarningJobLogic warningJobLogic) {
		this.warningJobLogic = warningJobLogic;
	}

	public WarningDao getWarningDao() {
		return warningDao;
	}

	public void setWarningDao(WarningDao warningDao) {
		this.warningDao = warningDao;
	}

	public WarningLogic getWarningLogic() {
		return warningLogic;
	}

	public void setWarningLogic(WarningLogic warningLogic) {
		this.warningLogic = warningLogic;
	}

	/** 获得预警信息 */
	public StringBuffer getWarningByKey(Request request, String key) {
		return warningLogic.getWarningByKey(key);
	}

	/** 获得所有预警信息 */
	public Map<String, StringBuffer> getWarningCache(Request request) {
		return warningLogic.getWarningCache();
	}

	/** 删除所有预警信息 */
	public void removeWarningByKey(Request request, String key) {
		warningLogic.removeWarningByKey(key);
	}
	
	
	

	/**
	 * type = 0 or 1
	 * 获得预警线程 
	 * @return
	 */
	public WarningThread findWarningThread(Request request, int type) {
		return this.warningDao.findWarningThread(type);
	}

	/**保存预警线程*/
	public WarningThread saveWarningThread(Request request, WarningThread w) {
		this.warningDao.saveWarningThread(w);
		return w;
	}

	/**查找所有计划任务*/
	@AuthorityFunctionAnnotation(caption = "计划任务", index = 2)
	public List<PlanTask> findPlanTask(Request request) {
		Company company = (Company) CommonUtils.getCompany();
		return this.warningDao.findPlanTask(company);
	}

	/**保存计划任务*/
	public PlanTask savePlanTask(Request request, PlanTask p) {
		this.warningDao.savePlanTask(p);
		return p;
	}

	/**删除计划任务*/
	public void deletePlanTask(Request request,PlanTask p) {
		this.warningDao.deletePlanTask(p);
	}
	
	
	/**查找预警项目*/
	@AuthorityFunctionAnnotation(caption = "预警项目", index = 1)
	public List<WarningItem> findWarningItem(Request request) {
		Company company = (Company) CommonUtils.getCompany();
		return this.warningDao.findWarningItem(company);
	}
	
	/**查找预警项目*/
	@AuthorityFunctionAnnotation(caption = "预警项目", index = 1)
	public WarningItem findWarningItem(Request request,Integer type) {
		Company company = (Company) CommonUtils.getCompany();
		return this.warningDao.findWarningItem(company,type);
	}
	
	/**保存预警项目*/
	public WarningItem saveWarningItem(Request request,WarningItem w) {
		this.warningDao.saveWarningItem(w);
		return w;
	}

	/**删除预警项目*/
	public void deleteWarningItem(Request request,WarningItem w) {
		this.warningDao.deleteWarningItem(w);
	}

	
	
	/**
	 * 启动当前公司的预警线程
	 */
	public void startWarningThread(Request request) { // 服务启动		
		this.warningJobLogic.startThread();
	}
	
	/**
	 * 结束预警线程
	 * 
	 */
	public void shutDownWarningThread(Request request) {
		this.warningJobLogic.shutDownThread();
	}

	
	/**
	 * 启动当前计划任务线程
	 */
	public void startPlanTaskThread(Request request) { // 服务启动		
		this.planTaskJobLogic.startThread();
	}
	
	/**
	 * 结束当前计划任务线程
	 * 
	 */
	public void shutDownPlanTaskThread(Request request) {
		this.planTaskJobLogic.shutDownThread();
	}
	
	
	public List<TempWarningItem> getTempWarningItem(Request request){
		return WarningLogic.getTempWarningItem();
	}
	
}
