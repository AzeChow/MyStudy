package com.bestway.bcus.dataimport.logic;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.bestway.bcus.custombase.dao.BaseCodeDao;
import com.bestway.bcus.dataimport.dao.DataImportDao;
import com.bestway.bcus.dataimport.entity.DBTaskEx;
import com.bestway.bcus.dataimport.entity.ThreadList;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.common.tools.logic.ToolsLogic;

public class DataDBAutoImportLogic {

	private DataImportDao	dataImportDao	= null;
	private DataImportLogic	dataImportLogic	= null;
	private BaseCodeDao		baseCodeDao		= null;
	private ToolsLogic toolsLogic = null;
	private static Log		logger			= LogFactory
													.getLog(DataDBAutoImportLogic.class);
	private Map<TempNodeItem, List<TempNodeItem>>	dataDictMap		= null;
	private Hashtable hs = null;

	public BaseCodeDao getBaseCodeDao() {
		return baseCodeDao;
	}

	public void setBaseCodeDao(BaseCodeDao baseCodeDao) {
		this.baseCodeDao = baseCodeDao;
	}

	public DataImportLogic getDataImportLogic() {
		return dataImportLogic;
	}

	public void setDataImportLogic(DataImportLogic dataImportLogic) {
		this.dataImportLogic = dataImportLogic;
	}

	public DataImportDao getDataImportDao() {
		return dataImportDao;
	}

	public void setDataImportDao(DataImportDao dataImportDao) {
		this.dataImportDao = dataImportDao;
	}

	/**
	 * String key = 线程类型+线程公司(Id)
	 */
	private Map<String, Thread>	threadPool	= new HashMap<String, Thread>();

	/**
	 * 
	 * 清空所有的自动导入任务
	 */
	public void initThreadList() {		
		if (hs == null){
			hs = new Hashtable();
			dataDictMap = toolsLogic.getTableColumnMap();
			List<TempNodeItem> listitem = new ArrayList<TempNodeItem>();
			listitem.addAll(dataDictMap.keySet());
			Collections.sort(listitem);
			for (int i=0;i<listitem.size();i++){
				TempNodeItem obj = (TempNodeItem) listitem.get(i);
				String className = forClassName(obj.getClassName());
				hs.put(className,obj.getClassName());
			}
		}
		//
		// 如果存在线程导入的话，在重启服务器的时候把所有的线程导入加入
		//
		List list = dataImportDao.findThread("db");

		for (int i = 0, n = list.size(); i < n; i++) {// 分公司启动线程

			ThreadList threadList = (ThreadList) list.get(i);
			Company company = (Company) threadList.getCompany();
			String key = threadList.getType() + company.getId();

			if (threadPool.containsKey(key)) { // 已起动线程 不用再启动
				return;
			} else {
				//
				// key =执行状态(每日,每周,每月,间隔)+任务Id+excuteday+excutetime; //运行日期
				// 当前线程的任务调度池(用于新增任务,或删除不用的任务,或改变时间的自动导入任务)
				//
				Map<String, Scheduler> schedulerPool = new HashMap<String, Scheduler>();
				StartUpdb startdb = new StartUpdb(schedulerPool,
						(Company) threadList.getCompany(), hs);
				startdb.start();
				threadPool.put(key, startdb);
			}
			logger.info("[JBCUS DATA IMPORT]  " + company.getName()
					+ " 导入接口自动导入任务启动 .....");
		}
		//
		// 初始化所有的自动导入任务为空
		//
		// this.dataImportDao.deleteAllThread("db");

	}
	/**
	 * 结束导入线程
	 * 
	 */
	public void shutDownThread() {
		String key = "db" + CommonUtils.getCompany().getId();
		logger.info("[JBCUS DATA IMPORT]  shutDownThread " + key);
		if (threadPool.containsKey(key)) {
			Thread thread = threadPool.get(key);
			StartUpdb startUpdb = (StartUpdb) thread;
			Map<String, Scheduler> schedulerPool = startUpdb.getSchedulerPool();
			//
			// 删除过时的任务调度
			//		
			Iterator<Scheduler> iterator = schedulerPool.values().iterator();
			while (iterator.hasNext()) {
				Scheduler scheduler = iterator.next();
				try {
					scheduler.shutdown();
					logger.info("[JBCUS DATA IMPORT]  shutDown Scheduler --");
					schedulerPool.remove(key);
					scheduler = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			thread.stop();
			thread = null;
			threadPool.remove(key);
		}
	}
	private String forClassName(String value){
		if (value != null) {
			String entityName = value.toString();
			int lastIndex = entityName.lastIndexOf(".");
			if (lastIndex > -1) {
				return entityName
						.substring(lastIndex + 1);
			}
		}
		return value;
	}
	/**
	 * 起动自动导入
	 * 
	 */
	public void startThread() { // 服务启动
		if (hs == null){
			hs = new Hashtable();
			dataDictMap = toolsLogic.getTableColumnMap();
			List<TempNodeItem> listitem = new ArrayList<TempNodeItem>();
			listitem.addAll(dataDictMap.keySet());
			Collections.sort(listitem);
			for (int i=0;i<listitem.size();i++){
				TempNodeItem obj = (TempNodeItem) listitem.get(i);
				String className = forClassName(obj.getClassName());
				hs.put(className,obj.getClassName());
			}
		}
		
		List list = dataImportDao.findThreadList("db");
		if (list != null && !list.isEmpty()) { // 分公司启动线程
			ThreadList threadList = (ThreadList) list.get(0);
			String key = threadList.getType() + threadList.getCompany().getId();
			if (threadPool.containsKey(key)) { // 已起动线程 不用再启动
				return;
			} else {
				//
				// key =执行状态(每日,每周,每月,间隔)+任务Id+excuteday+excutetime; //运行日期
				// 当前线程的任务调度池(用于新增任务,或删除不用的任务,或改变时间的自动导入任务)
				//
				Map<String, Scheduler> schedulerPool = new HashMap<String, Scheduler>();
				StartUpdb startdb = new StartUpdb(schedulerPool,
						(Company) threadList.getCompany(), hs);				
				startdb.start();
				threadPool.put(key, startdb);
			}
		}
	}

	/**
	 * DB线程启动
	 */
	class StartUpdb extends Thread {
		private Map<String, Scheduler>	schedulerPool	= null;
		private Company					company			= null;
		private Hashtable               hs              = null;

		public Map<String, Scheduler> getSchedulerPool() {
			return schedulerPool;
		}

		public StartUpdb(Map<String, Scheduler> schedulerPool, Company company, Hashtable hs) {
			this.schedulerPool = schedulerPool;
			this.company = company;
			this.hs = hs;
		}

		public void run() {
			int interval = 15000;// 15 秒开始查询一次任务
			while (2 == 2) {
				try {
					Thread.sleep(interval);
//					logger.info("[JBCUS DATA IMPORT]  正在检查最新的任务 === :" + new Date());
					dbAutoImport(company, schedulerPool, hs);					
				} catch (Exception e) {// 保证线程不中断
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * 数据库自动导入
	 * 
	 * @param company
	 */
	private void dbAutoImport(BaseCompany company,
			Map<String, Scheduler> schedulerPool,Hashtable hs) {
		List list = dataImportDao.findDBTaskEx(company);
		//
		// 获得最新的任务情况(自动导入的)
		//
		Map<String, DBTaskEx> newTaskExMap = new HashMap<String, DBTaskEx>();

		for (int i = 0; i < list.size(); i++) {
			DBTaskEx dbex = (DBTaskEx) list.get(i);
			String excuteKind = dbex.getExcutekind();
			String taskId = dbex.getId();
			String excuteDay = dbex.getExcuteday() == null ? "" : dbex
					.getExcuteday();
			String excuteTime = dbex.getExcutetime() == null ? "" : dbex
					.getExcutetime();
			String inputSelect = dbex.getInputSelect() ==null?"0":dbex.getInputSelect();
			//
			// key =执行状态(每日,每周,每月,间隔)+任务Id+excuteday+excutetime; 启动新的任务
			//
			String key = excuteKind + taskId + excuteDay + excuteTime + inputSelect;
			//
			// 用于删除不存这里面的任务调度
			//
			newTaskExMap.put(key, dbex);

			if (!schedulerPool.containsKey(key)) { // 如果不存在任务调度,起动任务调度
				try {
					
					UUID uuid = UUID.randomUUID();
					String id = uuid.toString();
					Properties properties = new Properties();
					properties.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, id);
					properties.put(StdSchedulerFactory.PROP_SCHED_RMI_EXPORT, "false");					
					properties.put(StdSchedulerFactory.PROP_SCHED_RMI_PROXY, "false");
					properties.put(StdSchedulerFactory.PROP_SCHED_WRAP_JOB_IN_USER_TX, "false");
					properties.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, "org.quartz.simpl.SimpleThreadPool");
					properties.put(StdSchedulerFactory.PROP_JOB_STORE_CLASS, "org.quartz.simpl.RAMJobStore");
					properties.put("org.quartz.threadPool.threadCount", "10");
					properties.put("org.quartz.threadPool.threadPriority", "5");
					properties.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");
					properties.put("org.quartz.jobStore.misfireThreshold", "60000");					
					
					SchedulerFactory factory = new StdSchedulerFactory(properties);
					Scheduler scheduler = factory.getScheduler();					
					
					JobDetail jobDetail = new JobDetail(id,
							Scheduler.DEFAULT_GROUP, DBAutoImportJob.class);
					//
					// 见 DBAutoImportJob excute method context by 罗盛
					//
					JobDataMap jobDateMap = new JobDataMap();
					jobDetail.setJobDataMap(jobDateMap);
					jobDateMap.put("company", company);
					jobDateMap.put("dataImportDao", dataImportDao);
					jobDateMap.put("baseCodeDao", baseCodeDao);
					jobDateMap.put("dataImportLogic", dataImportLogic);
					jobDateMap.put("dbTaskEx", dbex);
					jobDateMap.put("hs",hs);					
					CronTrigger trigger = new CronTrigger(id + "Trigger",
							Scheduler.DEFAULT_GROUP);
					trigger.setCronExpression(this.getCronExpression(dbex));
					trigger.setStartTime(new Date());
					scheduler.scheduleJob(jobDetail, trigger);
					scheduler.start();
					//
					// 存入调度池
					//
					schedulerPool.put(key, scheduler);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		//
		// 删除过时的任务调度
		//		
		String[] keys = schedulerPool.keySet().toArray(new String[]{});
		for (String key : keys) {
			//
			// 最新的任务调度中不存这样的任务,就干掉它
			//
			if (!newTaskExMap.containsKey(key)) {
				Scheduler scheduler = schedulerPool.get(key);
				//Scheduler.
				try {
					scheduler.shutdown();
					schedulerPool.remove(key);				
					scheduler = null;
					logger.info("[JBCUS DATA IMPORT]  shutDown Scheduler --");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获得触发表达式
	 * 
	 * @return
	 */
	public String getCronExpression(DBTaskEx dbex) {
		// Seconds YES 0-59 , - * /
		// Minutes YES 0-59 , - * /
		// Hours YES 0-23 , - * /
		// Day of month YES 1-31 , - * ? / L W C
		// Month YES 1-12 or JAN-DEC, - * /
		// Day of week YES 1-7 or SUN-SAT , - * ? / L C #
		// Year NO empty,1970-2099, - * /

		String cronExpression = "0 0 3 * * ?"; // 每天3时开始导入

		String excuteKind = dbex.getExcutekind();
		String excuteDay = dbex.getExcuteday() == null ? "" : dbex
				.getExcuteday();
		String excuteTime = dbex.getExcutetime() == null ? "" : dbex
				.getExcutetime();
		Time time = Time.valueOf(excuteTime);
		String seconds = String.valueOf(time.getSeconds());
		String minutes = String.valueOf(time.getMinutes());
		String hours = String.valueOf(time.getHours());

		if (excuteKind.equals("1")) { // 每日

			logger.info("[JBCUS DATA IMPORT]  每日调度任务开始时间：" + new Date().toLocaleString());
			cronExpression = "" + seconds + " " + minutes + " " + hours
					+ " * * ?";
			logger.info("[JBCUS DATA IMPORT]  每日调度触发表达式 == " + cronExpression);

		} else if (excuteKind.equals("2")) { // 每周

			logger.info("[JBCUS DATA IMPORT]  每周调度任务开始时间：" + new Date().toLocaleString());
			cronExpression = "" + seconds + " " + minutes + " " + hours
					+ " ? * " + excuteDay;
			logger.info("[JBCUS DATA IMPORT]  每周调度触发表达式 == " + cronExpression);

		} else if (excuteKind.equals("3")) { // 每月

			logger.info("[JBCUS DATA IMPORT]  每月调度任务开始时间：" + new Date().toLocaleString());
			cronExpression = "" + seconds + " " + minutes + " " + hours + " "
					+ excuteDay + " * ?";
			logger.info("[JBCUS DATA IMPORT]  每月调度触发表达式 == " + cronExpression);

		} else if (excuteKind.equals("4")) { // 间隔时间

			logger.info("[JBCUS DATA IMPORT]  间隔时间调度任务开始时间：" + new Date().toLocaleString());

			if (!hours.equals("0")) {
				hours = "0/" + hours;
			} else {
				hours = "*";
			}
			if (!minutes.equals("0")) {
				minutes = "0/" + minutes;
			} else {
				minutes = "*";
			}
			cronExpression = "0/" + seconds + " " + minutes + " " + hours
					+ " * * ?";
			logger.info("[JBCUS DATA IMPORT]  间隔时间调度触发表达式 == " + cronExpression);
		}
		return cronExpression;
	}

	public ToolsLogic getToolsLogic() {
		return toolsLogic;
	}

	public void setToolsLogic(ToolsLogic toolsLogic) {
		this.toolsLogic = toolsLogic;
	}

}
