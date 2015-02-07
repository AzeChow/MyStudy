package com.bestway.common.warning.logic;

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

import com.bestway.bcus.system.dao.CompanyDao;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.dao.AclUserDao;
import com.bestway.common.constant.ExcuteKind;
import com.bestway.common.warning.dao.WarningDao;
import com.bestway.common.warning.entity.PlanTask;
import com.bestway.common.warning.entity.WarningThread;

public class PlanTaskJobLogic {

	private WarningDao	warningDao	= null;
	private CompanyDao	companyDao	= null;
	private AclUserDao	aclUserDao	= null;
	private static Log	logger		= LogFactory.getLog(PlanTaskJobLogic.class);

	public WarningDao getWarningDao() {
		return warningDao;
	}

	public void setWarningDao(WarningDao warningDao) {
		this.warningDao = warningDao;
	}

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	public AclUserDao getAclUserDao() {
		return aclUserDao;
	}

	public void setAclUserDao(AclUserDao aclUserDao) {
		this.aclUserDao = aclUserDao;
	}

	/**
	 * String key = 线程类型+线程公司(Id)
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
		List<WarningThread> list = warningDao
				.findWarningThreadAll(WarningThread.PLAN_TASK);

		for (int i = 0; i < list.size(); i++) {// 分公司启动线程

			WarningThread threadList = list.get(i);
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
			logger.info("[JBCUS PLAN TASK INFO]  " + company.getName()
					+ " 预警任务启动 .....");
		}
	}

	/**
	 * 结束导入线程
	 * 
	 */
	public void shutDownThread() {
		String key = CommonUtils.getCompany().getId();
		logger.info("[JBCUS PLAN TASK INFO]  shutDownThread " + key);
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
							.info("[JBCUS PLAN TASK INFO]  shutDown Scheduler --");
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
	 * 起动自动导入 启动当前公司的预警线程
	 */
	public void startThread() { // 服务启动
		WarningThread warningThread = warningDao
				.findWarningThread(WarningThread.PLAN_TASK);

		if (warningThread != null) { // 分公司启动线程
			String key = warningThread.getCompany().getId();
			if (threadPool.containsKey(key)
					|| !warningThread.getIsStart().booleanValue()) { // 已起动线程
				// 不用再启动
				return;
			} else {
				//
				// key =执行状态(每日,每周,每月,间隔)+任务Id+excuteday+excutetime; //运行日期
				// 当前线程的任务调度池(用于新增任务,或删除不用的任务,或改变时间的自动导入任务)
				//
				Map<String, Scheduler> schedulerPool = new HashMap<String, Scheduler>();
				StartUpdb startdb = new StartUpdb(schedulerPool,
						(Company) warningThread.getCompany());
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
					//					 logger.info("[JBCUS PLAN TASK INFO] 正在检查最新的任务 === :"
					// + new Date());
					planTaskJob(company, schedulerPool);
				} catch (Exception e) {// 保证线程不中断
					e.printStackTrace();
				}
			}
			// logger.info("[JBCUS PLAN TASK INFO] 关闭线程 === :"
			// + new Date());

		}
	}

	/**
	 * 数据库自动导入
	 * 
	 * @param company
	 */
	private void planTaskJob(Company company,
			Map<String, Scheduler> schedulerPool) {
		List<PlanTask> list = this.warningDao.findPlanTask(company);
		//
		// 过滤超期的时间调度任务
		//
		filterPlanTask(list);
		//
		// 获得最新的任务情况(自动导入的)
		//
		Map<String, PlanTask> newPlanTaskMap = new HashMap<String, PlanTask>();

		for (int i = 0; i < list.size(); i++) {
			PlanTask planTask = list.get(i);
			Integer excuteKind = planTask.getExcuteKind();
			String taskId = planTask.getId();
			String excuteDay = planTask.getExcuteday() == null ? "" : planTask
					.getExcuteday();
			String excuteTime = planTask.getExcutetime() == null ? ""
					: planTask.getExcutetime();
			//
			// key =执行状态(每日,每周,每月,间隔)+任务Id+excuteday+excutetime; 启动新的任务
			//
			String key = excuteKind + taskId + excuteDay + excuteTime;
			//
			// 用于删除不存这里面的任务调度
			//
			newPlanTaskMap.put(key, planTask);

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
							Scheduler.DEFAULT_GROUP, PlanTaskJob.class);
					//
					// 见 PlanTaskJob excute method context by 罗盛
					//
					JobDataMap jobDateMap = new JobDataMap();
					jobDetail.setJobDataMap(jobDateMap);
					jobDateMap.put("company", company);
					jobDateMap.put("warningDao", warningDao);
					jobDateMap.put("companyDao", companyDao);
					jobDateMap.put("aclUserDao", aclUserDao);
					jobDateMap.put("planTask", planTask);
					CronTrigger trigger = new CronTrigger(id + "Trigger",
							Scheduler.DEFAULT_GROUP);
					trigger.setCronExpression(this.getCronExpression(planTask));
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
			if (!newPlanTaskMap.containsKey(key)) {
				Scheduler scheduler = schedulerPool.get(key);
				// Scheduler.
				try {
					scheduler.shutdown();
					schedulerPool.remove(key);
					scheduler = null;
					logger
							.info("[JBCUS PLAN TASK INFO]  shutDown Scheduler --");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**过滤超期的时间调度任务*/
	public void filterPlanTask(List<PlanTask> planTaskList) {

		for (int i = planTaskList.size() - 1; i >= 0; i--) {
			PlanTask planTask = planTaskList.get(i);
			int excuteKind = planTask.getExcuteKind();	
			if (excuteKind == ExcuteKind.TIMESTAMP) { // 如果时间戳的任务
				String excuteTime = planTask.getExcutetime() == null ? ""
						: planTask.getExcutetime();
				Time time = Time.valueOf(excuteTime);
				Date timestamp = planTask.getTimestamp();
				
				Date newDate = new Date(timestamp.getTime());
				newDate.setHours(time.getHours());
				newDate.setMinutes(time.getMinutes());
				newDate.setSeconds(time.getSeconds());
				
				if(newDate.before(new Date())){
//					System.out.println("过滤超期的时间调度任务 ------ " + planTask.getItemName());
					planTaskList.remove(i);
				}				
			}
		}
	}

	/**
	 * 获得触发表达式
	 * 
	 * @return
	 */
	public String getCronExpression(PlanTask planTask) {
		// Seconds YES 0-59 , - * /
		// Minutes YES 0-59 , - * /
		// Hours YES 0-23 , - * /
		// Day of month YES 1-31 , - * ? / L W C
		// Month YES 1-12 or JAN-DEC, - * /
		// Day of week YES 1-7 or SUN-SAT , - * ? / L C #
		// Year NO empty,1970-2099, - * /

		String cronExpression = "0 0 3 * * ?"; // 每天3时开始导入

		int excuteKind = planTask.getExcuteKind();
		String excuteDay = planTask.getExcuteday() == null ? "" : planTask
				.getExcuteday();
		String excuteTime = planTask.getExcutetime() == null ? "" : planTask
				.getExcutetime();
		Time time = Time.valueOf(excuteTime);
		String seconds = String.valueOf(time.getSeconds());
		String minutes = String.valueOf(time.getMinutes());
		String hours = String.valueOf(time.getHours());

		if (excuteKind == ExcuteKind.TIMESTAMP) { // 时间戳
			Date timestamp = planTask.getTimestamp();
			Date date = new Date();
			int year = timestamp.getYear() + 1900;
			int month = timestamp.getMonth() + 1;
			int day = timestamp.getDate();
			logger.info("[JBCUS PLAN TASK INFO]  时间戳调度任务开始时间："
					+ new Date().toLocaleString());
			cronExpression = seconds + " " + minutes + " " + hours + " " + day
					+ " " + month + " " + "?" + " " + year;
			logger.info("[JBCUS PLAN TASK INFO]  时间戳调度触发表达式 == "
					+ cronExpression);

		} else if (excuteKind == ExcuteKind.DAY) { // 每日

			logger.info("[JBCUS PLAN TASK INFO]  每日调度任务开始时间："
					+ new Date().toLocaleString());
			cronExpression = "" + seconds + " " + minutes + " " + hours
					+ " * * ?";
			logger.info("[JBCUS PLAN TASK INFO]  每日调度触发表达式 == "
					+ cronExpression);

		} else if (excuteKind == ExcuteKind.WEEK) { // 每周

			logger.info("[JBCUS PLAN TASK INFO]  每周调度任务开始时间："
					+ new Date().toLocaleString());
			cronExpression = "" + seconds + " " + minutes + " " + hours
					+ " ? * " + excuteDay;
			logger.info("[JBCUS PLAN TASK INFO]  每周调度触发表达式 == "
					+ cronExpression);

		} else if (excuteKind == ExcuteKind.MONTH) { // 每月

			logger.info("[JBCUS PLAN TASK INFO]  每月调度任务开始时间："
					+ new Date().toLocaleString());
			cronExpression = "" + seconds + " " + minutes + " " + hours + " "
					+ excuteDay + " * ?";
			logger.info("[JBCUS PLAN TASK INFO]  每月调度触发表达式 == "
					+ cronExpression);

		} else if (excuteKind == ExcuteKind.INTERVAL) { // 间隔时间

			logger.info("[JBCUS PLAN TASK INFO]  间隔时间调度任务开始时间："
					+ new Date().toLocaleString());

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
			logger.info("[JBCUS PLAN TASK INFO]  间隔时间调度触发表达式 == "
					+ cronExpression);
		}
		return cronExpression;
	}

}
