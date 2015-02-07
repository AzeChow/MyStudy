package com.bestway.common.dataexport.logic;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
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

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.ExcuteKind;
import com.bestway.common.constant.ImportDataMode;
import com.bestway.common.dataexport.dao.DBExportDao;
import com.bestway.common.dataexport.entity.JDBCTask;
import com.bestway.common.dataexport.entity.ThreadData;
/**
 * @author luosheng 2006/9/1
 * 
 */
public class DBAutoExportLogic {
	private DBExportDao			dbExportDao			= null;
	private DBImportExportLogic	dbImportExportLogic	= null;
	private static Log			logger				= LogFactory
															.getLog(DBAutoExportLogic.class);

	public DBExportDao getDbExportDao() {
		return dbExportDao;
	}

	public void setDbExportDao(DBExportDao dbExportDao) {
		this.dbExportDao = dbExportDao;
	}

	public DBImportExportLogic getDbImportExportLogic() {
		return dbImportExportLogic;
	}

	public void setDbImportExportLogic(DBImportExportLogic dbImportExportLogic) {
		this.dbImportExportLogic = dbImportExportLogic;
	}

	/**
	 * String key = 线程公司(Id)
	 */
	private Map<String, Thread>	threadPool	= new HashMap<String, Thread>();

	/**
	 * 
	 * 清空所有的自动导入任务
	 */
	public void init() {
		//
		// 如果存在线程导入的话，在重启服务器的时候把所有的线程导入加入
		//
		List<ThreadData> list = dbExportDao.findAllDBThreadData();

		for (int i = 0; i < list.size(); i++) {// 分公司启动线程

			ThreadData threadList = list.get(i);
			Company company = (Company) threadList.getCompany();
			String key = company.getId();

			if (threadPool.containsKey(key)
					|| !threadList.getIsStart().booleanValue()) { // 已起动线程
				// 不用再启动
				return;
			} else {
				//
				// key =执行状态(每日,每周,每月,间隔)+任务Id+excuteday+excutetime; //运行日期
				// 当前线程的任务调度池(用于新增任务,或删除不用的任务,或改变时间的自动导入任务)
				//
				Map<String, Scheduler> schedulerPool = new HashMap<String, Scheduler>();
				StartUpdb startdb = new StartUpdb(schedulerPool,
						(Company) threadList.getCompany());
				startdb.start();
				threadPool.put(key, startdb);
			}
			logger.info("[JBCUS DATA EXPORT INFO]  " + company.getName()
					+ " 数据导出任务已启动 .....");
		}
	}

	/**
	 * 结束导入线程
	 * 
	 */
	public void shutDownThread() {
		String key = CommonUtils.getCompany().getId();
		logger.info("[JBCUS DATA EXPORT INFO]  shutDownThread " + key);
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
					logger
							.info("[JBCUS DATA EXPORT INFO]  shutDown Scheduler --");
					schedulerPool.remove(key);
					scheduler = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			thread.interrupt();
			thread = null;
			threadPool.remove(key);
		}
	}

	/**
	 * 起动自动导入
	 */
	public void startThread() { // 服务启动
		ThreadData threadData = dbExportDao.findThreadData(ThreadData.DB_TYPE);

		if (threadData != null) { // 分公司启动线程
			String key = threadData.getCompany().getId();
			if (threadPool.containsKey(key)) { // 已起动线程
				// 不用再启动
				return;
			} else {
				//
				// key =执行状态(每日,每周,每月,间隔)+任务Id+excuteday+excutetime; //运行日期
				// 当前线程的任务调度池(用于新增任务,或删除不用的任务,或改变时间的自动导入任务)
				//
				Map<String, Scheduler> schedulerPool = new HashMap<String, Scheduler>();
				StartUpdb startdb = new StartUpdb(schedulerPool,
						(Company) threadData.getCompany());
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

		public Map<String, Scheduler> getSchedulerPool() {
			return schedulerPool;
		}

		public StartUpdb(Map<String, Scheduler> schedulerPool, Company company) {
			this.schedulerPool = schedulerPool;
			this.company = company;
		}

		public void run() {
			int interval = 15000;// 15 秒开始查询一次任务
			while (!isInterrupted()) {
				try {
					try {
						Thread.sleep(interval);
					} catch (InterruptedException e) {
						break;
					}
					// logger.info("[JBCUS DATA EXPORT INFO] 正在检查最新的任务 === :"
					// + new Date());
					jdbcTaskJob(company, schedulerPool);
				} catch (Exception e) {// 保证线程不中断
					e.printStackTrace();
				}
			}
			// logger.info("[JBCUS DATA EXPORT INFO] 线程关闭 === :"
			// + new Date());
		}
	}

	/**
	 * 数据库自动导入
	 * 
	 * @param company
	 */
	private void jdbcTaskJob(Company company,
			Map<String, Scheduler> schedulerPool) {
		List<JDBCTask> list = this.dbExportDao.findJDBCTask(company);
		//
		// 获得最新的任务情况(自动导入的)
		//
		Map<String, JDBCTask> newJDBCTaskMap = new HashMap<String, JDBCTask>();

		for (int i = 0; i < list.size(); i++) {
			JDBCTask jdbcTask = list.get(i);
			Integer excuteKind = jdbcTask.getExecutekind();
			String taskId = jdbcTask.getId();
			String excuteDay = jdbcTask.getExcuteday() == null ? "" : jdbcTask
					.getExcuteday();
			String excuteTime = jdbcTask.getExcutetime() == null ? ""
					: jdbcTask.getExcutetime();
			//
			// 获得导入数据的方式
			//
			int importDataMode = jdbcTask.getImportDataMode() == null ? ImportDataMode.ADD_MODE
					: jdbcTask.getImportDataMode();
			//
			// key =执行状态(每日,每周,每月,间隔)+任务Id+excuteday+excutetime; 启动新的任务
			//
			String key = excuteKind + taskId + excuteDay + excuteTime + importDataMode;
			//
			// 用于删除不存这里面的任务调度
			//
			newJDBCTaskMap.put(key, jdbcTask);

			if (!schedulerPool.containsKey(key)) { // 如果不存在任务调度,起动任务调度
				try {

					UUID uuid = UUID.randomUUID();
					String id = uuid.toString();
					Properties properties = new Properties();
					properties.put(
							StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, id);
					properties.put(StdSchedulerFactory.PROP_SCHED_RMI_EXPORT,
							"false");
					properties.put(StdSchedulerFactory.PROP_SCHED_RMI_PROXY,
							"false");
					properties.put(
							StdSchedulerFactory.PROP_SCHED_WRAP_JOB_IN_USER_TX,
							"false");
					properties.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS,
							"org.quartz.simpl.SimpleThreadPool");
					properties.put(StdSchedulerFactory.PROP_JOB_STORE_CLASS,
							"org.quartz.simpl.RAMJobStore");
					properties.put("org.quartz.threadPool.threadCount", "10");
					properties.put("org.quartz.threadPool.threadPriority", "5");
					properties
							.put(
									"org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread",
									"true");
					properties.put("org.quartz.jobStore.misfireThreshold",
							"60000");

					SchedulerFactory factory = new StdSchedulerFactory(
							properties);
					Scheduler scheduler = factory.getScheduler();

					JobDetail jobDetail = new JobDetail(id,
							Scheduler.DEFAULT_GROUP, DBAutoExportJob.class);
					//
					// 见 DBAutoExportJob excute method context by 罗盛
					//
					JobDataMap jobDateMap = new JobDataMap();
					jobDetail.setJobDataMap(jobDateMap);
					jobDateMap.put("company", company);
					jobDateMap.put("dbImportExportLogic", dbImportExportLogic);
					jobDateMap.put("jdbcTask", jdbcTask);
					CronTrigger trigger = new CronTrigger(id + "Trigger",
							Scheduler.DEFAULT_GROUP);
					trigger.setCronExpression(this.getCronExpression(jdbcTask));
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
		String[] keys = schedulerPool.keySet().toArray(new String[] {});
		for (String key : keys) {
			//
			// 最新的任务调度中不存这样的任务,就干掉它
			//
			if (!newJDBCTaskMap.containsKey(key)) {
				Scheduler scheduler = schedulerPool.get(key);
				// Scheduler.
				try {
					scheduler.shutdown();
					schedulerPool.remove(key);
					scheduler = null;
					logger
							.info("[JBCUS DATA EXPORT INFO]  shutDown Scheduler --");
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
	public String getCronExpression(JDBCTask jdbcTask) {
		// Seconds YES 0-59 , - * /
		// Minutes YES 0-59 , - * /
		// Hours YES 0-23 , - * /
		// Day of month YES 1-31 , - * ? / L W C
		// Month YES 1-12 or JAN-DEC, - * /
		// Day of week YES 1-7 or SUN-SAT , - * ? / L C #
		// Year NO empty,1970-2099, - * /

		String cronExpression = "0 0 3 * * ?"; // 每天3时开始导入

		int excuteKind = jdbcTask.getExecutekind();
		String excuteDay = jdbcTask.getExcuteday() == null ? ""
				: jdbcTask.getExcuteday();
		String excuteTime = jdbcTask.getExcutetime() == null ? ""
				: jdbcTask.getExcutetime();
		Time time = Time.valueOf(excuteTime);
		String seconds = String.valueOf(time.getSeconds());
		String minutes = String.valueOf(time.getMinutes());
		String hours = String.valueOf(time.getHours());

		if (excuteKind == ExcuteKind.DAY) { // 每日

			logger.info("[JBCUS DATA EXPORT INFO]  每日调度任务开始时间："
					+ new Date().toLocaleString());
			cronExpression = "" + seconds + " " + minutes + " " + hours
					+ " * * ?";
			logger.info("[JBCUS DATA EXPORT INFO]  每日调度触发表达式 == "
					+ cronExpression);

		} else if (excuteKind == ExcuteKind.WEEK) { // 每周

			logger.info("[JBCUS DATA EXPORT INFO]  每周调度任务开始时间："
					+ new Date().toLocaleString());
			cronExpression = "" + seconds + " " + minutes + " " + hours
					+ " ? * " + excuteDay;
			logger.info("[JBCUS DATA EXPORT INFO]  每周调度触发表达式 == "
					+ cronExpression);

		} else if (excuteKind == ExcuteKind.MONTH) { // 每月

			logger.info("[JBCUS DATA EXPORT INFO]  每月调度任务开始时间："
					+ new Date().toLocaleString());
			cronExpression = "" + seconds + " " + minutes + " " + hours + " "
					+ excuteDay + " * ?";
			logger.info("[JBCUS DATA EXPORT INFO]  每月调度触发表达式 == "
					+ cronExpression);

		} else if (excuteKind == ExcuteKind.INTERVAL) { // 间隔时间

			logger.info("[JBCUS DATA EXPORT INFO]  间隔时间调度任务开始时间："
					+ new Date().toLocaleString());
			
			System.out.println("hours:" + hours + ",minutes:" + minutes + ",seconds:" + seconds);
			if (!hours.equals("0")) {
				hours = "0/" + hours;
			} else {
				hours = "*";
			}
			if (!minutes.equals("0")) {
				minutes = "0/" + minutes;
			} else {
				if("*".equals(hours)) {
					minutes = "*";
				} else {
					minutes = "0";
				}
			}
			if(!seconds.equals("0")) {
				seconds = "0/" + seconds;
			} else {
				if("*".equals(minutes)) {
					seconds = "*";
				} else {
					seconds = "0";
				}
			}
			cronExpression = seconds + " " + minutes + " " + hours
					+ " * * ?";
			logger.info("[JBCUS DATA EXPORT INFO]  间隔时间调度触发表达式 == "
					+ cronExpression);
		}
		return cronExpression;
	}

}
