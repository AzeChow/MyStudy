package com.bestway.bcus.dataimport.logic;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bestway.bcus.custombase.dao.BaseCodeDao;
import com.bestway.bcus.dataimport.dao.DataImportDao;
import com.bestway.bcus.dataimport.entity.DBTaskEx;
import com.bestway.bcus.dataimport.entity.DBTaskSel;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.authority.entity.BaseCompany;

public class DBAutoImportJob implements Job {

	private static Log	logger	= LogFactory.getLog(DBAutoImportJob.class);

	
	/**
	 * Job 的实现
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap jobDateMap = context.getJobDetail().getJobDataMap();
		Company company = (Company) jobDateMap.get("company");
		Hashtable hs = (Hashtable) jobDateMap.get("hs");
		DataImportDao dataImportDao = (DataImportDao) jobDateMap
				.get("dataImportDao");
		BaseCodeDao baseCodeDao = (BaseCodeDao) jobDateMap.get("baseCodeDao");
		DataImportLogic dataImportLogic = (DataImportLogic) jobDateMap
				.get("dataImportLogic");
		
		DBTaskEx dbex = (DBTaskEx) jobDateMap.get("dbTaskEx");
		//
		// stop scheduler in exception
		//
		try {
			logger.info("[JBCUS DATA IMPORT]  自动任务开始执行   任务名:[" + dbex.getTaskname() +" ] " );
			dbAutoImport(dbex, company, baseCodeDao, dataImportDao,
					dataImportLogic, hs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[JBCUS DATA IMPORT]  自动任务结束执行   任务名:[" + dbex.getTaskname() +" ] " );
	}

	/**
	 * db 自动导入
	 * 
	 * @param list
	 * @param company
	 * @param baseCodeDao
	 * @param dataImportDao
	 * @param dataImportLogic
	 */
	private void dbAutoImport(DBTaskEx dbex, Company company,
			BaseCodeDao baseCodeDao, DataImportDao dataImportDao,
			DataImportLogic dataImportLogic, Hashtable hs) throws SQLException {
		//
		// 繁简字符
		//
		List listgb = baseCodeDao.findGbtobig("", "");
		 
		if (dbex.getIsParentTask() == null || (!dbex.getIsParentTask())) {
			dbSignTaskImport(company, listgb, dbex, dataImportDao,
					dataImportLogic, hs);
		} else {
			List dbselList = dataImportDao.findDBTaskSelRun(dbex, company);
			for (int j = 0, n = dbselList.size(); j < n; j++) {
				DBTaskSel subDBTaskEx = (DBTaskSel) dbselList.get(j);
				if (subDBTaskEx.getSubDBTaskEx() == null) {
					continue;
				}
				dbSignTaskImport(company, listgb, subDBTaskEx.getSubDBTaskEx(),
						dataImportDao, dataImportLogic,hs);
			}
		}
	}

	/**
	 * DB 单任务导入
	 * 
	 * @param company
	 * @param listgb
	 * @param dbex
	 */
	private void dbSignTaskImport(BaseCompany company, List listgb,
			DBTaskEx dbex, DataImportDao dataImportDao,
			DataImportLogic dataImportLogic, Hashtable hs) throws SQLException {
		List dbselList = dataImportDao.findDBTaskSelRun(dbex, company);
		for (int j = 0; j < dbselList.size(); j++) {
			DBTaskSel dbSel = (DBTaskSel) dbselList.get(j);
			if (dbSel.getExecuSql() != null && dbSel.getDbFormat() == null) {
				logger.info("[JBCUS DATA IMPORT]  自动任务调度正在执行事件 事件名:["
						+ dbSel.getExecuSql().getName() + "] " );
				String message = dataImportLogic
						.execuSql(dbSel.getExecuSql(), (Company) company);				
				if (message != null && message.contains("执行失败")){
					throw new RuntimeException(message);
				}
				
			} else {
				dataImportLogic.intoData(dbSel.getDbFormat(), company, "终止",
						dbSel.getDbtaskEx().getInputSelect(), listgb, hs);
			}
		}
	}
}
