//package com.bestway.bcus.client;

package com.bestway.bcus.client;

import java.io.File;
import java.util.Properties;
import java.util.TimeZone;

import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.bestway.bcus.client.authority.FmLogin;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.client.windows.form.FmMain;

public class ApplicationLauncher {
	private final Log logger = LogFactory.getLog(getClass());

	private ApplicationContext applicationContext;

	/**
	 * Construct the application
	 */
	public ApplicationLauncher() {
	}

	// 启动登录
	private void showLoginForm() {

		// 设置工具提示标签延迟时间为0
		ToolTipManager.sharedInstance().setInitialDelay(0);

		// 启动登录界面
		FmLogin frame = new FmLogin();

		frame.setVisible(true);
	}

	private void setApplicationContext(ApplicationContext context) {
		Assert.notNull(context);
		this.applicationContext = context;
	}

	private void launch(String startupContextPath, String[] contextPaths) {
		logger.info("Launching Application...");
		try {
			if (StringUtils.hasText(startupContextPath)) {
				ApplicationContext startupContext = new ClassPathXmlApplicationContext(
						startupContextPath);
			}
		} catch (RuntimeException e) {
			logger.warn("Exception occured initializing context.", e);
		}

		try {

			setApplicationContext(new ClassPathXmlApplicationContext(
					contextPaths));

			// 设置全局应用上下文对象
			CommonVars.setApplicationContext(applicationContext);

		} catch (RuntimeException e) {
			logger.warn("Exception occured initializing context.", e);
		}
		launch();
	}

	// 正式启动
	private void launch() {

		// Spring 断言 如果 applicationContext 为空会抛出异常
		Assert.notNull(applicationContext);

		try {

			// 调用 最小优先权线程 初始化所有需要用的列表
			Thread thread = new Thread(CustomBaseList.getInstance());

			thread.setPriority(Thread.MIN_PRIORITY);

			thread.start();

			// 打开登录对话框
			showLoginForm();

		} catch (RuntimeException e) {
			logger.error("Exception occured initializing console.", e);
		} finally {
			logger.debug("Launcher thread exiting...");
		}
	}

	/**
	 * 获得系统指定的协议
	 * 
	 * @return
	 */
	private static String readProtocol() {

		Properties props = new Properties();

		System.out.println(new File(".").getAbsolutePath());

		ClassLoader ccl = Thread.currentThread().getContextClassLoader();

		try {

			props.load(ccl.getResourceAsStream("client.properties"));

			return props.getProperty("jnlp.protocol");

		} catch (Exception ex) {

			System.out.print("client.properties 不存在!!");
		}

		return System.getProperty("jnlp.protocol");
	}

	/**
	 * 启动
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// 用户级别外观 存储一个K/V
		UIManager.put("swing.boldMetal", Boolean.TRUE);

		// 全局静态异常操作器
		System.setProperty("sun.awt.exception.handler",
				ExceptionHandler.class.getName());

		// 如果没有捕获到异常，就交由默认捕获异常操作器处理
		Thread.setDefaultUncaughtExceptionHandler(new ThreadExceptionHandler());

		ApplicationLauncher applicationLauncher = new ApplicationLauncher();

		try {

			String protocol = readProtocol();

			// 默认启动rmi协议
			if (protocol == null || "".equals(protocol)) {
				applicationLauncher.launch("startup-context.xml", new String[] {
						"rmiClientContext.xml", "application-context.xml" });
			} else {

				if (protocol.toString().toLowerCase().equals("rmi")) {
					applicationLauncher.launch("startup-context.xml",
							new String[] { "rmiClientContext.xml",
									"application-context.xml" });
				} else if (protocol.toString().toLowerCase().equals("http")) {
					applicationLauncher.launch("startup-context.xml",
							new String[] { "httpClientContext.xml",
									"application-context.xml" });
				}
			}
			// 设置时区
			setTimeZone();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(FmMain.getInstance(),
					"应用程序异常,错误信息请参见日志文件\n" + ex.getMessage(), "信息", 1);
			applicationLauncher.logger.error(ex);
		}
	}

	/**
	 * 设置时区
	 */
	private static void setTimeZone() {

		if (TimeZone.getDefault() == null) {
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		} else {
			TimeZone tz = TimeZone.getDefault();
			System.out.println(tz.getRawOffset() / 60 / 60 / 1000);
			if ((tz.getRawOffset() * 60 * 60 * 1000) == 0) {
				System.out.println("设置时区为：Asia/Shanghai");
				TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
			}
		}
	}

}