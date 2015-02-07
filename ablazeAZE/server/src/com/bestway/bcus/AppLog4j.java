package com.bestway.bcus;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.bestway.common.WebUtils;
import com.bestway.common.authority.AuthorityLog;
/***
 * 自定义log4j
 * @author luosheng
 *
 */
public class AppLog4j {
	/**
	 * 见 CustomContextLoaderServlet.java init() method
	 * 初始化 log4j 
	 * author luosheng
	 */
	public static void initLog4j(){
		Properties pro = new Properties();
		// pro.setProperty("log4j.logger.net.sf.hibernate","info");
		// pro.setProperty("log4j.logger.net.sf.hibernate.type","info");
		// pro.setProperty("log4j.logger.net.sf.hibernate.tool.hbm2ddl","debug");
		// -----------		
		pro.setProperty("log4j.rootLogger", "INFO, stdout, logfile");
		pro.setProperty("log4j.appender.stdout",
				"com.bestway.common.LogConsoleAppender");
		pro.setProperty("log4j.appender.stdout.Target", "System.out");
		pro.setProperty("log4j.appender.stdout.layout",
				"org.apache.log4j.PatternLayout");
		pro.setProperty("log4j.appender.stdout.layout.ConversionPattern",
				"%d{ABSOLUTE} %5p %c{1}:%L - %m%n");
		//
		// 自定义的web日志目录(logs)请看 -->CustomLog4jConfigServlet.java
		//
		String webAppLogsRoot = WebUtils.getWebAppLogsRoot();

		File logInvoke = new File(webAppLogsRoot + "/" + "log_invoke");
		if (!logInvoke.exists()) {
			logInvoke.mkdir();
		}
		System.out.println("webAppLogsRoot:" + webAppLogsRoot);
		System.out.println("logInvoke:" + logInvoke.getPath());
		pro.setProperty("log4j.appender.logfile",
				"org.apache.log4j.DailyRollingFileAppender");
		pro.setProperty("log4j.appender.logfile.File", webAppLogsRoot + "/"
				+ "log_invoke/invoke_log.log");
		pro.setProperty("log4j.appender.logfile.layout",
				"org.apache.log4j.PatternLayout");
		// pro.setProperty("log4j.appender.logfile.MaxFileSize","5MB");
		pro.setProperty("log4j.appender.logfile.layout.ConversionPattern",
				"%d %p [%c]:%L - %m%n");

		File authorityLog = new File(webAppLogsRoot + "/" + "authority_log");
		if (!authorityLog.exists()) {
			authorityLog.mkdir();
		}
		System.out.println("------------" + authorityLog.getPath());
		AuthorityLog.setLogLocation(authorityLog.getPath());
		// System.out.println("operateLog:" + authorityLog.getPath());
		// pro.setProperty("log4j.logger.authoritylog",
		// "info,authoritylogfile");
		// pro.setProperty("log4j.appender.authoritylogfile",
		// "org.apache.log4j.DailyRollingFileAppender");
		// pro.setProperty("log4j.appender.authoritylogfile.File",
		// webAppLogsRoot
		// + "/" + "authority_log_invoke/authority_log.log");
		// pro.setProperty("log4j.appender.authoritylogfile.layout",
		// "org.apache.log4j.xml.XMLLayout");
		// // pro.setProperty("log4j.appender.logfile.MaxFileSize","5MB");
		// pro.setProperty("log4j.appender.authoritylogfile.layout.ConversionPattern",
		// " - %m%n");

		PropertyConfigurator.configure(pro);
	}
}
