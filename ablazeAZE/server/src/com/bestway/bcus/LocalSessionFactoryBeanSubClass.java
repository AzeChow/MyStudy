package com.bestway.bcus;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Settings;
import org.hibernate.impl.SessionFactoryImpl;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.bestway.common.CommonUtils;
import com.bestway.common.tools.logic.PluginClientLogic;
import com.bestway.common.tools.logic.PluginClientReportLogic;

public class LocalSessionFactoryBeanSubClass extends LocalSessionFactoryBean {
	private static Log log = LogFactory
			.getLog(LocalSessionFactoryBeanSubClass.class);

	private String tableOwner;

	public String getTableOwner() {
		return tableOwner;
	}

	public void setTableOwner(String userName) {
		this.tableOwner = userName;
	}

	/**
	 * Subclasses can override this method to perform custom initialization of
	 * the SessionFactory instance, creating it via the given Configuration
	 * object that got prepared by this LocalSessionFactoryBean.
	 * <p>
	 * The default implementation invokes Configuration's buildSessionFactory. A
	 * custom implementation could prepare the instance in a specific way, or
	 * use a custom SessionFactoryImpl subclass.
	 * 
	 * @param config
	 *            Configuration prepared by this LocalSessionFactoryBean
	 * @return the SessionFactory instance
	 * @throws HibernateException
	 *             in case of Hibernate initialization errors
	 * @see org.hibernate.cfg.Configuration#buildSessionFactory
	 */
	protected SessionFactory newSessionFactory(Configuration config)
			throws HibernateException {
		System.out.println("-----------------------newSessionFactory:");
		SessionFactory sf = config.buildSessionFactory();
		CommonUtils.setSessionFactoryImpl((SessionFactoryImpl) sf);
		return sf;
	}

	protected void postProcessConfiguration(Configuration config)
			throws HibernateException {
		super.postProcessConfiguration(config);
		String dialect = config.getProperty("hibernate.dialect");
		System.out.println("------------table owner:" + tableOwner);
		if (dialect.indexOf("Oracle") >= 0 && tableOwner != null
				&& !"".equals(tableOwner.trim())) {
			config.setProperty("hibernate.default_schema", tableOwner.trim());
		}
	}

	/**
	 * Subclasses can override this method to perform custom initialization of
	 * the Configuration instance used for SessionFactory creation. The
	 * properties of this LocalSessionFactoryBean will be applied to the
	 * Configuration object that gets returned here.
	 * <p>
	 * The default implementation creates a new Configuration instance. A custom
	 * implementation could prepare the instance in a specific way, or use a
	 * custom Configuration subclass.
	 * 
	 * @return the Configuration instance
	 * @throws HibernateException
	 *             in case of Hibernate initialization errors
	 * @see org.hibernate.cfg.Configuration#Configuration()
	 */
	protected Configuration newConfiguration() throws HibernateException {
		Configuration configuration = super.newConfiguration();
		CommonUtils.setConfiguration(configuration);
		loadHbmJar(configuration);
		return configuration;
	}

	public static boolean isJarExistHbmXml(File jar) throws MappingException {
		boolean isExist = false;
		JarFile jarFile = null;
		try {
			try {
				jarFile = new JarFile(jar);
			} catch (IOException ioe) {
				throw new MappingException(
						"Could not read mapping documents from jar: "
								+ jar.getName(), ioe);
			}
			Enumeration jarEntries = jarFile.entries();
			while (jarEntries.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) jarEntries.nextElement();
				if (ze.getName().endsWith(".hbm.xml")) {
					isExist = true;
					break;
				}
			}
		} finally {
			try {
				if (jarFile != null)
					jarFile.close();
			} catch (IOException ioe) {
				log.error("could not close jar", ioe);
			}
		}
		return isExist;
	}

	private void loadHbmJar(Configuration cofiguration, File jar) {
		if (!isJarExistHbmXml(jar)) {
			return;
		}
		try {

			// ClassLoaderUtil.listBootstrapClassPath();
			// ClassLoaderUtil.listSystemClassPath();
			// ClassLoaderUtil.listExtClassPath();
			// ClassLoaderUtil.addURL2SystemClassLoader(jar.toURL());

			//
			// ClassLoader thisLoader = Thread.currentThread()
			// .getContextClassLoader();
			//			
			// while(thisLoader != null){
			// System.out.println(thisLoader.getClass());
			// ClassLoaderUtil.addURL2ClassLoader(jar.toURL(),thisLoader);
			// if(thisLoader instanceof URLClassLoader){
			// URLClassLoader urlClassLoader = (URLClassLoader)thisLoader;
			// for (int i = 0; i < urlClassLoader.getURLs().length; i++) {
			// System.out.println(urlClassLoader.getURLs()[i]);
			// }
			// }
			// thisLoader = thisLoader.getParent() ;
			// }
			//			
			// URLClassLoader cl = new URLClassLoader(new URL[] { jar.toURL() },
			// Thread.currentThread()
			// .getContextClassLoader());
			// Thread.currentThread().setContextClassLoader(cl);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		log.info("[JBCUS] 动态载入文件 : " + jar.getPath());
		cofiguration.addJar(jar);
	}

	private void loadHbmJar(Configuration cofiguration) {
		try {
			//
			// 删除已经有删除标识的server插件
			//		
			log.info("[JBCUS] 删除已经有删除标识的server插件");
			PluginClientReportLogic.deleteAllServerPlugin();
			PluginClientLogic.deleteAllServerPlugin();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//
		// 载入插件 PluginClientReportLogic PLUGIN_FOLDER_SERVER
		//			
		File file = new File(PluginClientReportLogic.PLUGIN_FOLDER_SERVER);
		File[] files = file.listFiles();

		for (File f : files) {
			String fileName = f.getName();
			//
			// 打包的文件才可以
			//			
			if (f.isDirectory() == true || !fileName.endsWith(".jar")) {
				continue;
			}
			loadHbmJar(cofiguration, f);
		}
		//
		// PluginClientLogic PLUGIN_FOLDER_SERVER
		//
		file = new File(PluginClientLogic.PLUGIN_FOLDER_SERVER);
		files = file.listFiles();

		for (File f : files) {
			String fileName = f.getName();
			//
			// 打包的文件才可以
			//			
			if (f.isDirectory() == true || !fileName.endsWith(".jar")) {
				continue;
			}
			loadHbmJar(cofiguration, f);
		}
	}

}
