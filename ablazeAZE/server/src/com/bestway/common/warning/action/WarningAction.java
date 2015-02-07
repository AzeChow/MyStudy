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
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.warning.entity.PlanTask;
import com.bestway.common.warning.entity.TempWarningItem;
import com.bestway.common.warning.entity.WarningItem;
import com.bestway.common.warning.entity.WarningThread;
import com.bestway.common.warning.logic.WarningLogic;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface WarningAction {

	/** 获得预警信息 */
	StringBuffer getWarningByKey(Request request, String key);

	/** 获得所有预警信息 */
	Map<String, StringBuffer> getWarningCache(Request request);

	/** 删除预警信息 */
	void removeWarningByKey(Request request, String key);

	/**
	 * type = 0 or 1 获得预警线程
	 * 
	 * @return
	 */
	WarningThread findWarningThread(Request request, int type);

	/** 保存预警线程 */
	WarningThread saveWarningThread(Request request, WarningThread w);

	/** 查找所有计划任务 */
	List<PlanTask> findPlanTask(Request request);

	/** 保存计划任务 */
	PlanTask savePlanTask(Request request, PlanTask p);

	/** 删除计划任务 */
	void deletePlanTask(Request request, PlanTask p);

	/** 查找预警项目 */
	List<WarningItem> findWarningItem(Request request);
	
	/**查找预警项目*/
	WarningItem findWarningItem(Request request,Integer type);

	/** 保存预警项目 */
	WarningItem saveWarningItem(Request request, WarningItem w);

	/** 删除预警项目 */
	void deleteWarningItem(Request request, WarningItem w);

	/**
	 * 启动当前公司的预警线程
	 */
	void startWarningThread(Request request);

	/**
	 * 结束预警线程
	 * 
	 */
	void shutDownWarningThread(Request request);

	/**
	 * 启动当前计划任务线程
	 */
	void startPlanTaskThread(Request request);

	/**
	 * 结束当前计划任务线程
	 * 
	 */
	void shutDownPlanTaskThread(Request request);
	
	List<TempWarningItem> getTempWarningItem(Request request);
	

}