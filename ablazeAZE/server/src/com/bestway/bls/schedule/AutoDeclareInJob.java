package com.bestway.bls.schedule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.bestway.bcus.system.entity.Company;

public class AutoDeclareInJob implements StatefulJob  {//Job
	private static Log logger = LogFactory.getLog(AutoDeclareInJob.class);

	private ScheduleLogic scheduleLogic = null;

	/**
	 * Job 的实现
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		//
		// stop scheduler in exception
		//
		JobDataMap jobDateMap = context.getJobDetail().getJobDataMap();
		String originCronExpression = ((CronTrigger) context.getTrigger())
				.getCronExpression();
		Company company = (Company) jobDateMap.get("company");
		scheduleLogic = (ScheduleLogic) jobDateMap
				.get("scheduleLogic");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS",
				Locale.CHINA);
		System.out.println("IN-----------------------"
				+ dateFormat.format(new Date()) + company.getName() + "  "
				+ originCronExpression);// + dbCronExpression
		if (!scheduleLogic.getIsAutoDeclare(company)) {
			return;
		}
		scheduleLogic.allAutoDeclareInDelivery(company);
		if (scheduleLogic.getIsAutoProcess(company)) {
			scheduleLogic.allAutoProcessInDelivery(company);
		}
		scheduleLogic.allAutoDeclareInEntranceMessage(company);
		if (scheduleLogic.getIsAutoProcess(company)) {
			scheduleLogic.allAutoProcessInEntranceMessage(company);
		}
	}

}
