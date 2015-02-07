package com.bestway.bcus.system.logic;

import java.io.File;
import java.io.IOException;

import com.bestway.common.CommonUtils;
import com.bestway.common.WebUtils;

/**
 * @author
 * 
 */
public class JbucsUpgradeLogic {
	private static final String AUTO_UPAGER_FLAG_FILE = "/autoUpgradeFlag";
	
//	/**
//	 * wss检验重启是否成功
//	 */
//	private static final String ISRESTART_OK = "/restartOK.html";

	private static final String CLOSE_TOMCAT_SERVER = System
			.getProperty("java.home")
			+ System.getProperty("file.separator") + "closeTomcatServerFlag";
	private static final String NO_CLOSE_TOMCAT_SERVER = System
			.getProperty("java.home")
			+ System.getProperty("file.separator") + "noCloseTomcatServerFlag";

	private static boolean closeThread = false;
	
	private static String tomcatBinDir = "";

	/**
	 * 客户端调用服务端自动升级
	 * 
	 */
	public static void autoUpgradeJBCUS() {
		new UpgradeThread().start();
	}

	static class UpgradeThread extends Thread {

		public UpgradeThread() {
		}

		public void run() {
			try {

				File c = new File(CLOSE_TOMCAT_SERVER);
				if (c.exists()) {
					c.delete();
				}

				File nc = new File(NO_CLOSE_TOMCAT_SERVER);
				if (nc.exists()) {
					nc.delete();
				}

				closeThread = false;

				String tomcatBinDir = CommonUtils.getFilePath(WebUtils
						.getWebAppRoot());
				String flagFile = tomcatBinDir + AUTO_UPAGER_FLAG_FILE;
				File f = new File(flagFile);
				if (f.exists()) {
					f.delete();
				}

				tomcatBinDir = tomcatBinDir.substring(0,
						tomcatBinDir.length() - 13)
						+ "bin/jbcusupgrade";

				ProcessBuilder pb = new ProcessBuilder("java", "-mx512m",
						"-cp", ".;", "-jar", "lib/jbcusupgrade.jar", "run");

				File file = new File(tomcatBinDir);
				//
				pb.directory(file);
				// Process process =
				pb.start();

				new CloseTomcatServerThread().start();
				new NoCloseTomcatServerThread().start();

			} catch (Exception e) {
				String tomcatBinDir = CommonUtils.getFilePath(WebUtils
						.getWebAppRoot());
				String flagFile = tomcatBinDir + AUTO_UPAGER_FLAG_FILE;
				File f = new File(flagFile);
				if (!f.exists()) {
					f.mkdir();
				}
				e.printStackTrace();
			}
		}
	};

	static class CloseTomcatServerThread extends Thread {

		public void run() {
			try {
				/**
				 * 是否要关闭 tomcat server
				 */
				File closeTomcatServerFlagFile = new File(CLOSE_TOMCAT_SERVER);
				while (!closeTomcatServerFlagFile.exists() && !closeThread) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
				}
				System.out.println("Close Tomcat Server Thread Exit");
				if (closeTomcatServerFlagFile.exists()) {
					if (closeTomcatServerFlagFile.delete()) {
						System.exit(0);
					}
				}

			} catch (Exception e) {
			}
		}
	};

	static class NoCloseTomcatServerThread extends Thread {

		public void run() {
			try {
				/**
				 * 是否要关闭 tomcat server
				 */
				File closeTomcatServerFlagFile = new File(
						NO_CLOSE_TOMCAT_SERVER);
				while (!closeTomcatServerFlagFile.exists()) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
				}
				System.out.println("NO Close Tomcat Server Thread Exit");
				if (closeTomcatServerFlagFile.exists()) {
					closeThread = true;
				}

			} catch (Exception e) {
			}
		}
	};

	/**
	 * 升级成功
	 */
	public static boolean getAutoUpgradeState() {
		/**
		 * 说明升级开始
		 */
		File file = new File(AUTO_UPAGER_FLAG_FILE);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 升级成功，重新tomcat
	 * wss:2010.05屏避升级功能
	 */
	public static void restart() {
		try {
			
		   tomcatBinDir = CommonUtils.getFilePath(WebUtils
					.getWebAppRoot());
			if (tomcatBinDir.endsWith("/") || tomcatBinDir.endsWith(File.separator)) {
				tomcatBinDir = tomcatBinDir.substring(0,
						tomcatBinDir.length() - 1);
			}
			
			System.out.println("wss tomcatBinDir:" + tomcatBinDir);
			
			int year = Integer.parseInt(CommonUtils.getYear());
			
			String flagFile = tomcatBinDir + File.separator + (year + 1) + ".html";
			File f = new File(flagFile);
			if (f.exists()) {
				f.delete();
			}
			
			String flagFile2 = tomcatBinDir + File.separator + (year - 1) + ".html";
			File f2 = new File(flagFile2);
			if (f2.exists()) {
				f2.delete();
			}
			
			
			File c = new File(CLOSE_TOMCAT_SERVER);//jdk.jre目录下的
			if (c.exists()) {
				c.delete();
			}

			tomcatBinDir = tomcatBinDir
					.substring(0, tomcatBinDir.length() - 13)
					+ File.separator + "bin" + File.separator + "jbcusupgrade";

			ProcessBuilder pb = new ProcessBuilder("javaw", "-mx512m", "-cp",
					".;", "-jar", "lib/jbcusupgrade.jar", year + "");

			File file = new File(tomcatBinDir);
			pb.directory(file);
			pb.start();

		} catch (Exception e) {
//			String tomcatBinDir = CommonUtils.getFilePath(WebUtils
//					.getWebAppRoot());
//			String flagFile = tomcatBinDir + AUTO_UPAGER_FLAG_FILE;
//			File f = new File(flagFile);
//			if (!f.exists()) {
//				f.mkdir();
//			}
			e.printStackTrace();
		} finally {
			new RestartCloseTomcatServerThread().start();
		}
	}

	static class RestartCloseTomcatServerThread extends Thread {

		public void run() {
			try {
				/**
				 * 是否要关闭 tomcat server
				 */
				File closeTomcatServerFlagFile = new File(CLOSE_TOMCAT_SERVER);
				while (!closeTomcatServerFlagFile.exists()) {//此文件不存在表示还没有关闭
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
				}
				
				if (closeTomcatServerFlagFile.exists()) {//此文件有创建则表示已经关闭了老Tomcat
					if (closeTomcatServerFlagFile.delete()) {
//						String tomcatBinDir = CommonUtils.getFilePath(WebUtils
//								.getWebAppRoot());
//						String flagFile = tomcatBinDir + ISRESTART_OK;
//						File f = new File(flagFile);
//						if (!f.exists()) {
//							f.createNewFile();
//						}
						System.exit(0);//关闭了老Tomcat，可以关闭系统（服务器）
//						System.out.println("wss test tomcat 关闭了，服务器端要关闭了");
//						Thread.sleep(1000);

					}
				}

			} catch (Exception e) {
			}
		}
	};


}
