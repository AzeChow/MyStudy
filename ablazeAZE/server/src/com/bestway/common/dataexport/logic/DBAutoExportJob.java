package com.bestway.common.dataexport.logic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bestway.bcus.dataimport.logic.DBAutoImportJob;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.dataexport.entity.JDBCTask;
/**
 * @author luosheng 2006/9/1
 *         <ul>
 *         <li> 1.org.quartz 调度工作
 *         </ul>
 * 
 */
public class DBAutoExportJob implements Job {
	private static Log			logger				= LogFactory
															.getLog(DBAutoExportJob.class);
	private DBImportExportLogic	dbImportExportLogic	= null;

	/**
	 * Job 的实现
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap jobDateMap = context.getJobDetail().getJobDataMap();
		Company company = (Company) jobDateMap.get("company");
		dbImportExportLogic = (DBImportExportLogic) jobDateMap
				.get("dbImportExportLogic");
		JDBCTask jdbcTask = (JDBCTask) jobDateMap.get("jdbcTask");
		//
		// stop scheduler in exception
		//
		try {
			logger.info("[JBCUS DATA EXPORT]  自动任务开始执行   任务名:["
					+ jdbcTask.getTaskname() + " ] ");
			dbImportExportLogic.executeImportExportData(jdbcTask, company);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.info("[JBCUS DATA EXPORT]  自动任务结束执行   任务名:["
				+ jdbcTask.getTaskname() + " ] ");
	}
}
