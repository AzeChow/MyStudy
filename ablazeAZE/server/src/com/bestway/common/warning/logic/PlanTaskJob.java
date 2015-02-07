package com.bestway.common.warning.logic;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bestway.bcus.system.dao.CompanyDao;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.authority.dao.AclUserDao;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.warning.dao.WarningDao;
import com.bestway.common.warning.entity.PlanTask;

public class PlanTaskJob implements Job {
	private static Log					logger			= LogFactory
																.getLog(PlanTaskJob.class);
	private Map<String, StringBuffer>	warningCache	= WarningCache
																.getCache();
	private WarningDao					warningDao		= null;
	private CompanyDao					companyDao		= null;
	private AclUserDao					aclUserDao		= null;
	private PlanTask					planTask		= null;

	/**
	 * Job 的实现
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		//
		// stop scheduler in exception
		//
		JobDataMap jobDateMap = context.getJobDetail().getJobDataMap();
		Company company = (Company) jobDateMap.get("company");

		warningDao = (WarningDao) jobDateMap.get("warningDao");
		aclUserDao = (AclUserDao) jobDateMap.get("aclUserDao");
		companyDao = (CompanyDao) jobDateMap.get("companyDao");
		planTask = (PlanTask) jobDateMap.get("planTask");

		try {
			logger.info("[JBCUS PLAN TASK START] " + company.getName()
					+ " 计划任务开始生成提示信息 .....");
			System.out
			.println("PlanTaskJob.execute---------------------------------");
			planTask(company);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[JBCUS PLAN TASK START] " + company.getName()
				+ " 计划任务结束生成提示信息 .....");
	}

	/**
	 * 
	 * warning method 所有生成的提示信息在这里调用 ,尽量每个方法要求 try catch finally
	 * 在这里调用的方法不能用CommonUtils.getCompany() 方法来获得参数 StringBuffer stringBuffer
	 * =makeContractWarningInfo(Company company){...};
	 * 
	 */
	public void planTask(Company company) {
		//
		// 让第个用户有一个预警提示信息
		//
		String companyId = company.getId();
		AclUser user = planTask.getAclUser();
		StringBuffer sb = new StringBuffer();
		sb.append(planTask.getContent());
		//
		// 这条信息将是对所有用户进行广播的
		//
		if (user == null) {
			List list = aclUserDao.findUsers(company);
			for (int j = 0; j < list.size(); j++) {
				AclUser aclUser = (AclUser) list.get(j);
				//
				// key = company + user name + planTask id.
				//
				String key = companyId + aclUser.getName() + planTask.getId();
				warningCache.put(key, sb);
			}
		} else {
			String key = companyId + user.getName() + planTask.getId();
			warningCache.put(key, sb);
		}
		//
		// 存入文件
		//
		WarningCache.store();
	}

}
