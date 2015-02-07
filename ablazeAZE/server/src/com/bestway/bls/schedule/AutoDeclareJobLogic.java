package com.bestway.bls.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.bestway.bcus.system.dao.CompanyDao;
import com.bestway.bcus.system.entity.Company;

public class AutoDeclareJobLogic {

	private CompanyDao companyDao = null;

	private ScheduleLogic scheduleLogic = null;

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	public ScheduleLogic getScheduleLogic() {
		return scheduleLogic;
	}

	public void setScheduleLogic(ScheduleLogic scheduleInfoLogic) {
		this.scheduleLogic = scheduleInfoLogic;
	}

	Map<String, Map<String, Scheduler>> schedulerPool = new HashMap<String, Map<String, Scheduler>>();

	/**
	 * 
	 * 初始化所有导入任务
	 */
	public void init() {
		List list = companyDao.findCompanies();
		for (int i = 0; i < list.size(); i++) {
			Company company = (Company) list.get(i);
			Map<String, Scheduler> mapScheduler = schedulerPool.get(company
					.getId());
			if (mapScheduler == null) {
				mapScheduler = new HashMap<String, Scheduler>();
				schedulerPool.put(company.getId(), mapScheduler);
			}
			if (scheduleLogic.getIsAutoDeclare(company)) {
				createAutoDeclareInJob(company, mapScheduler);
				createAutoDeclareOutJob(company, mapScheduler);
				createAutoDeclareCobJob(company, mapScheduler);
			}
		}
	}

	/**
	 * 创建入仓自动申报和处理回执任务
	 * 
	 * @param company
	 */
	private void createAutoDeclareInJob(Company company,
			Map<String, Scheduler> mapScheduler) {
		String key = "IN";
		try {
			// UUID uuid = UUID.randomUUID();
			String id = company.getId() + "_IN";// uuid.toString();
			Properties properties = new Properties();
			properties.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, id);
			properties.put(StdSchedulerFactory.PROP_SCHED_RMI_EXPORT, "false");
			properties.put(StdSchedulerFactory.PROP_SCHED_RMI_PROXY, "false");
			properties.put(StdSchedulerFactory.PROP_SCHED_WRAP_JOB_IN_USER_TX,
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
			properties.put("org.quartz.jobStore.misfireThreshold", "60000");

			SchedulerFactory factory = new StdSchedulerFactory(properties);
			Scheduler scheduler = factory.getScheduler();

			JobDetail jobDetail = new JobDetail(id, Scheduler.DEFAULT_GROUP,
					AutoDeclareInJob.class);
			JobDataMap jobDateMap = new JobDataMap();
			jobDetail.setJobDataMap(jobDateMap);
			jobDateMap.put("company", company);
			jobDateMap.put("scheduleLogic", scheduleLogic);
			CronTrigger trigger = new CronTrigger(id + "Trigger",
					Scheduler.DEFAULT_GROUP);
			trigger.setCronExpression(this.scheduleLogic
					.getInCronExpressionFromDB(company));
			trigger.setStartTime(new Date());
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			mapScheduler.put(key, scheduler);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 创建出仓自动申报和处理回执任务
	 * 
	 * @param company
	 */
	private void createAutoDeclareOutJob(Company company,
			Map<String, Scheduler> mapScheduler) {
		String key = "OUT";
		try {
			// UUID uuid = UUID.randomUUID();
			String id = company.getId() + "_OUT";// uuid.toString();
			Properties properties = new Properties();
			properties.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, id);
			properties.put(StdSchedulerFactory.PROP_SCHED_RMI_EXPORT, "false");
			properties.put(StdSchedulerFactory.PROP_SCHED_RMI_PROXY, "false");
			properties.put(StdSchedulerFactory.PROP_SCHED_WRAP_JOB_IN_USER_TX,
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
			properties.put("org.quartz.jobStore.misfireThreshold", "60000");

			SchedulerFactory factory = new StdSchedulerFactory(properties);
			Scheduler scheduler = factory.getScheduler();

			JobDetail jobDetail = new JobDetail(id, Scheduler.DEFAULT_GROUP,
					AutoDeclareOutJob.class);
			JobDataMap jobDateMap = new JobDataMap();
			jobDetail.setJobDataMap(jobDateMap);
			jobDateMap.put("company", company);
			jobDateMap.put("scheduleLogic", scheduleLogic);
			CronTrigger trigger = new CronTrigger(id + "Trigger",
					Scheduler.DEFAULT_GROUP);
			trigger.setCronExpression(this.scheduleLogic
					.getOutCronExpressionFromDB(company));
			trigger.setStartTime(new Date());
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			mapScheduler.put(key, scheduler);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 创建出仓自动申报和处理回执任务
	 * 
	 * @param company
	 */
	private void createAutoDeclareCobJob(Company company,
			Map<String, Scheduler> mapScheduler) {
		String key = "COB";
		try {
			// UUID uuid = UUID.randomUUID();
			String id = company.getId() + "_COB";// uuid.toString();
			Properties properties = new Properties();
			properties.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, id);
			properties.put(StdSchedulerFactory.PROP_SCHED_RMI_EXPORT, "false");
			properties.put(StdSchedulerFactory.PROP_SCHED_RMI_PROXY, "false");
			properties.put(StdSchedulerFactory.PROP_SCHED_WRAP_JOB_IN_USER_TX,
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
			properties.put("org.quartz.jobStore.misfireThreshold", "60000");

			SchedulerFactory factory = new StdSchedulerFactory(properties);
			Scheduler scheduler = factory.getScheduler();

			JobDetail jobDetail = new JobDetail(id, Scheduler.DEFAULT_GROUP,
					AutoDeclareCobJob.class);
			JobDataMap jobDateMap = new JobDataMap();
			jobDetail.setJobDataMap(jobDateMap);
			jobDateMap.put("company", company);
			jobDateMap.put("scheduleLogic", scheduleLogic);
			CronTrigger trigger = new CronTrigger(id + "Trigger",
					Scheduler.DEFAULT_GROUP);
			trigger.setCronExpression(this.scheduleLogic
					.getCobCronExpressionFromDB(company));
			trigger.setStartTime(new Date());
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			mapScheduler.put(key, scheduler);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void refreshCronExpression(Company company) {
		if (scheduleLogic.getIsAutoDeclare(company)) {
			Map<String, Scheduler> mapScheduler = schedulerPool.get(company
					.getId());
			if (mapScheduler != null) {
				Scheduler schedulerIn = mapScheduler.get("IN");
				if (schedulerIn != null) {
					try {
						CronTrigger trigger = (CronTrigger) schedulerIn
								.getTrigger(
										company.getId() + "_IN" + "Trigger",
										Scheduler.DEFAULT_GROUP);
						if (trigger != null) {
							trigger.setCronExpression(this.scheduleLogic
									.getInCronExpressionFromDB(company));
							trigger.setStartTime(new Date());
							schedulerIn.rescheduleJob(company.getId() + "_IN"
									+ "Trigger", Scheduler.DEFAULT_GROUP,
									trigger);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					this.createAutoDeclareInJob(company, mapScheduler);
				}
				Scheduler schedulerOut = mapScheduler.get("OUT");
				if (schedulerOut != null) {
					try {
						CronTrigger trigger = (CronTrigger) schedulerOut
								.getTrigger(company.getId() + "_OUT"
										+ "Trigger", Scheduler.DEFAULT_GROUP);
						if (trigger != null) {
							trigger.setCronExpression(this.scheduleLogic
									.getOutCronExpressionFromDB(company));
							trigger.setStartTime(new Date());
							schedulerOut.rescheduleJob(company.getId() + "_OUT"
									+ "Trigger", Scheduler.DEFAULT_GROUP,
									trigger);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					this.createAutoDeclareOutJob(company, mapScheduler);
				}
				
				Scheduler schedulerCob = mapScheduler.get("COB");
				if (schedulerCob != null) {
					try {
						CronTrigger trigger = (CronTrigger) schedulerCob
								.getTrigger(company.getId() + "_COB"
										+ "Trigger", Scheduler.DEFAULT_GROUP);
						if (trigger != null) {
							trigger.setCronExpression(this.scheduleLogic
									.getCobCronExpressionFromDB(company));
							trigger.setStartTime(new Date());
							schedulerCob.rescheduleJob(company.getId() + "_COB"
									+ "Trigger", Scheduler.DEFAULT_GROUP,
									trigger);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					this.createAutoDeclareCobJob(company, mapScheduler);
				}
			}
		} else {
			this.closeScheduler(company);
		}
	}

	private void closeScheduler(Company company) {
		Map<String, Scheduler> mapScheduler = schedulerPool
				.get(company.getId());
		if (mapScheduler != null) {
			Scheduler schedulerIn = mapScheduler.get("IN");
			if (schedulerIn != null) {
				try {
					schedulerIn.shutdown();
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Scheduler schedulerOut = mapScheduler.get("OUT");
			if (schedulerOut != null) {
				try {
					schedulerOut.shutdown();
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Scheduler schedulerCob = mapScheduler.get("COB");
			if (schedulerCob != null) {
				try {
					schedulerCob.shutdown();
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			mapScheduler.put("IN", null);
			mapScheduler.put("OUT", null);
			mapScheduler.put("COB", null);
		}
	}

}
