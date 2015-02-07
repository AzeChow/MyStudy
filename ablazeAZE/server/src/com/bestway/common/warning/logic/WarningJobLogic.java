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

import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contractcav.dao.ContractCavDao;
import com.bestway.bcus.checkcancel.dao.CheckCancelDao;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.system.dao.CompanyDao;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.dao.AclUserDao;
import com.bestway.common.constant.ExcuteKind;
import com.bestway.common.fpt.dao.FptManageDao;
import com.bestway.common.warning.dao.WarningDao;
import com.bestway.common.warning.entity.WarningItem;
import com.bestway.common.warning.entity.WarningThread;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.fixtureonorder.dao.FixtureContractDao;

public class WarningJobLogic {

	private WarningDao warningDao = null;

	private CompanyDao companyDao = null;

	private AclUserDao aclUserDao = null;

	private DzscDao dzscDao = null;

	private EmsEdiTrDao emsEdiTrDao = null;

	private CheckCancelDao checkCancelDao = null;

	private FptManageDao fptManageDao = null;

	private ContractDao contractDao = null;

	private ContractCavDao contractCavDao=null;
	
	public ContractCavDao getContractCavDao() {
		return contractCavDao;
	}

	public void setContractCavDao(ContractCavDao contractCavDao) {
		this.contractCavDao = contractCavDao;
	}

	private FixtureContractDao fixtureContractDao = null;

	private static Log logger = LogFactory.getLog(WarningJobLogic.class);

	public ContractDao getContractDao() {
		return contractDao;
	}

	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}

	public FptManageDao getFptManageDao() {
		return fptManageDao;
	}

	public void setFptManageDao(
			FptManageDao transferFactoryManageDao) {
		this.fptManageDao = transferFactoryManageDao;
	}

	public CheckCancelDao getCheckCancelDao() {
		return checkCancelDao;
	}

	public void setCheckCancelDao(CheckCancelDao checkCancelDao) {
		this.checkCancelDao = checkCancelDao;
	}

	public DzscDao getDzscDao() {
		return dzscDao;
	}

	public void setDzscDao(DzscDao dzscDao) {
		this.dzscDao = dzscDao;
	}

	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

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
	 * @return the fixtureContractDao
	 */
	public FixtureContractDao getFixtureContractDao() {
		return fixtureContractDao;
	}

	/**
	 * @param fixtureContractDao
	 *            the fixtureContractDao to set
	 */
	public void setFixtureContractDao(FixtureContractDao fixtureContractDao) {
		this.fixtureContractDao = fixtureContractDao;
	}

	/**
	 * String key = 线程公司(Id)
	 */
	private Map<String, Thread> threadPool = new HashMap<String, Thread>();

	/**
	 * 
	 * 清空所有的自动导入任务
	 */
	public void init() {
		//
		// 如果存在线程导入的话，在重启服务器的时候把所有的线程导入加入
		//
		List<WarningThread> list = warningDao
				.findWarningThreadAll(WarningThread.WARNING_ITEM);
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
			logger.info("[JBCUS WARNING INFO]  " + company.getName()
					+ " 预警任务启动 .....");
		}
	}

	/**
	 * 结束导入线程
	 * 
	 */
	public void shutDownThread() {
		String key = CommonUtils.getCompany().getId();
		logger.info("[JBCUS WARNING INFO]  shutDownThread " + key);
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
					logger.info("[JBCUS WARNING INFO]  shutDown Scheduler --");
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
				.findWarningThread(WarningThread.WARNING_ITEM);
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
		private Map<String, Scheduler> schedulerPool = null;

		private Company company = null;

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
					// logger.info("[JBCUS WARNING INFO] 正在检查最新的任务 === :"
					// + new Date());
					warningJob(company, schedulerPool);
				} catch (Exception e) {// 保证线程不中断
					e.printStackTrace();
				}
			}
			// logger.info("[JBCUS WARNING INFO] 线程关闭 === :"
			// + new Date());
		}
	}

	/**
	 * 数据库自动导入
	 * 
	 * @param company
	 */
	private void warningJob(Company company,
			Map<String, Scheduler> schedulerPool) {
		List<WarningItem> list = this.warningDao.findWarningItem(company);
		//
		// 获得最新的任务情况(自动导入的)
		//
		Map<String, WarningItem> newWarningItemMap = new HashMap<String, WarningItem>();

		for (int i = 0; i < list.size(); i++) {
			WarningItem warningItem = list.get(i);
			Integer excuteKind = warningItem.getExcuteKind();
			String taskId = warningItem.getId();
			String excuteDay = warningItem.getExcuteday() == null ? ""
					: warningItem.getExcuteday();
			String excuteTime = warningItem.getExcutetime() == null ? ""
					: warningItem.getExcutetime();
			//
			// key =执行状态(每日,每周,每月,间隔)+任务Id+excuteday+excutetime; 启动新的任务
			//
			String key = excuteKind + taskId + excuteDay + excuteTime;

			//
			// 用于删除不存这里面的任务调度
			//
			newWarningItemMap.put(key, warningItem);
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
							Scheduler.DEFAULT_GROUP, WarningJob.class);
					//
					// 见 DBAutoImportJob excute method context by 罗盛
					//
					JobDataMap jobDateMap = new JobDataMap();
					jobDetail.setJobDataMap(jobDateMap);
					jobDateMap.put("company", company);
					jobDateMap.put("warningDao", warningDao);
					jobDateMap.put("fixtureContractDao", fixtureContractDao);
					jobDateMap.put("companyDao", companyDao);
					jobDateMap.put("aclUserDao", aclUserDao);
					jobDateMap.put("dzscDao", dzscDao);
					jobDateMap.put("warningItem", warningItem);
					jobDateMap.put("emsEdiTrDao", emsEdiTrDao);
					jobDateMap.put("transferFactoryManageDao",
							fptManageDao);
					jobDateMap.put("checkCancelDao", checkCancelDao);
					jobDateMap.put("contractDao", contractDao);
					jobDateMap.put("contractCavDao", contractCavDao);
					CronTrigger trigger = new CronTrigger(id + "Trigger",
							Scheduler.DEFAULT_GROUP);
					trigger.setCronExpression(this
							.getCronExpression(warningItem));
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
			if (!newWarningItemMap.containsKey(key)) {
				Scheduler scheduler = schedulerPool.get(key);
				// Scheduler.
				try {
					scheduler.shutdown();
					schedulerPool.remove(key);
					scheduler = null;
					logger.info("[JBCUS WARNING INFO]  shutDown Scheduler --");
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
	public String getCronExpression(WarningItem warningItem) {
		// Seconds YES 0-59 , - * /
		// Minutes YES 0-59 , - * /
		// Hours YES 0-23 , - * /
		// Day of month YES 1-31 , - * ? / L W C
		// Month YES 1-12 or JAN-DEC, - * /
		// Day of week YES 1-7 or SUN-SAT , - * ? / L C #
		// Year NO empty,1970-2099, - * /

		String cronExpression = "0 0 3 * * ?"; // 每天3时开始导入

		int excuteKind = warningItem.getExcuteKind();
		String excuteDay = warningItem.getExcuteday() == null ? ""
				: warningItem.getExcuteday();
		String excuteTime = warningItem.getExcutetime() == null ? ""
				: warningItem.getExcutetime();
		Time time = Time.valueOf(excuteTime);
		String seconds = String.valueOf(time.getSeconds());
		String minutes = String.valueOf(time.getMinutes());
		String hours = String.valueOf(time.getHours());

		if (excuteKind == ExcuteKind.DAY) { // 每日

			logger.info("[JBCUS WARNING INFO]  每日调度任务开始时间："
					+ new Date().toLocaleString());
			cronExpression = "" + seconds + " " + minutes + " " + hours
					+ " * * ?";
			logger.info("[JBCUS WARNING INFO]  每日调度触发表达式 == " + cronExpression);

		} else if (excuteKind == ExcuteKind.WEEK) { // 每周

			logger.info("[JBCUS WARNING INFO]  每周调度任务开始时间："
					+ new Date().toLocaleString());
			cronExpression = "" + seconds + " " + minutes + " " + hours
					+ " ? * " + excuteDay;
			logger.info("[JBCUS WARNING INFO]  每周调度触发表达式 == " + cronExpression);

		} else if (excuteKind == ExcuteKind.MONTH) { // 每月

			logger.info("[JBCUS WARNING INFO]  每月调度任务开始时间："
					+ new Date().toLocaleString());
			cronExpression = "" + seconds + " " + minutes + " " + hours + " "
					+ excuteDay + " * ?";
			logger.info("[JBCUS WARNING INFO]  每月调度触发表达式 == " + cronExpression);

		} else if (excuteKind == ExcuteKind.INTERVAL) { // 间隔时间

			logger.info("[JBCUS WARNING INFO]  间隔时间调度任务开始时间："
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
			logger.info("[JBCUS WARNING INFO]  间隔时间调度触发表达式 == "
					+ cronExpression);
		}
		return cronExpression;
	}

}
