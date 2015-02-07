/*
 * Created on 2005-6-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;

import org.hibernate.cfg.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderServlet;
import org.springframework.web.context.WebApplicationContext;

import com.bestway.common.ClassLoaderUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.tools.entity.FileResource;
import com.bestway.common.tools.logic.PluginClientLogic;
import com.bestway.common.tools.logic.PluginClientReportLogic;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CustomContextLoaderServlet extends ContextLoaderServlet {

	private static final String	UPGRADE_FILE_NAME	= System
															.getProperty("java.home")
															+ System
																	.getProperty("file.separator")
															+ "jbcusupgrade.properties";
	private static final String	VERSION_FILE_NAME	= System
															.getProperty("java.home")
															+ System
																	.getProperty("file.separator")
															+ "VersionFileName.properties";

	private String				jbcusUpgradePath	= null;

	/**
	 * Initialize the root web application context.
	 */
	public void init() throws ServletException {
		loadHbmJar();
		
		jbcusUpgradePath = this.getServletContext().getRealPath("/");
		//
		// 升级路径写入
		//
		writeJBCUSUpgradeFilePath();

		// 生成版本
		makeJBCUSVersion();
		//
		// 初始CAS记帐年度
		//
		System.out.println(jbcusUpgradePath+"WEB-INF/jdbc.properties");
		setCasYear(jbcusUpgradePath+"WEB-INF/jdbc.properties");
		//
		// init spring context
		//
		WebApplicationContext webApplicationContext = createContextLoader()
				.initWebApplicationContext(getServletContext());
		CommonUtils.setContext(webApplicationContext);
		//
		// init log4j
		//
		AppLog4j.initLog4j();
	}

	/**
	 * Create the ContextLoader to use. Can be overridden in subclasses.
	 * 
	 * @return the new ContextLoader
	 */
	protected ContextLoader createContextLoader() {
		return new CustomContextLoader();
	}

	/**
	 * jbcusupgrade.properties 升级路径写入 *
	 * 
	 */
	private void writeJBCUSUpgradeFilePath() {
		OutputStream outputStream = null;
		BufferedWriter out = null;
		try {
			File file = new File(UPGRADE_FILE_NAME);
			//
			// 每次启动会删除
			//
			if (file.exists()) {
				file.delete();
			}
			outputStream = new FileOutputStream(new File(UPGRADE_FILE_NAME));
			out = new BufferedWriter(new OutputStreamWriter(outputStream));
			System.out.println(" [jbcus 升级路径写入到 ] " + UPGRADE_FILE_NAME);
			out.write("jbcus_upgrade_path = " + jbcusUpgradePath);
			out.flush();
			out.close();
			outputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 生成升级版本
	 * 
	 */
	private void makeJBCUSVersion() {
		OutputStream outputStream = null;
		try {
			File file = new File(VERSION_FILE_NAME);
			//
			// 如果存在不进行生成,最先一次生成,以便以后升级进行检查
			//
			if (file.exists() && file.length() > 0) {
				return;
			}
			System.out.println(" [jbcus 生成升级版本文件 ] " + VERSION_FILE_NAME);
			Properties propsVersion = new Properties();

			List<FileResource> listFileResource = new ArrayList<FileResource>();
			fileResource(listFileResource, this.getServletContext()
					.getRealPath("/"));
			System.out.println("listFileResource = " + listFileResource.size());

			for (int i = 0, n = listFileResource.size(); i < n; i++) {
				FileResource f = listFileResource.get(i);
				propsVersion.put(f.getFilePath(), f.getLastModife().toString());
			}
			try {
				outputStream = new FileOutputStream(VERSION_FILE_NAME);
				propsVersion
						.store(outputStream,
								"[ JBCUS UPGRADE VERSION BY LASTMODIFE IN BESTWAYSOFT ]");
				outputStream.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 读取本路径下的所有信息存入列表中
	 * 
	 * @param listFileResource
	 * @param mapDirectory
	 * @param filePath
	 * @throws Exception
	 */
	private void fileResource(List<FileResource> listFileResource,
			String filePath) {
		try {
			if (filePath == null || "".equals(filePath.trim())) {
				return;
			}
			File file = new File(filePath);
			if (!file.exists() && !file.isDirectory()) {
				return;
			}
			File[] files = file.listFiles();
			//
			// 建立 FileResource
			//
			for (int i = 0, n = files.length; i < n; i++) {
				File tempFile = files[i];

				if (tempFile.isDirectory()) {
					fileResource(listFileResource, tempFile.getAbsolutePath());

				} else {
					FileResource fileResource = new FileResource();
					fileResource.setFileName(tempFile.getName());
					fileResource.setFileLength(tempFile.length());
					String absolutePath = tempFile.getAbsolutePath();
					//
					// 相对root的路径
					//
					if (absolutePath.indexOf(jbcusUpgradePath) > -1) {
						fileResource.setFilePath("/"
								+ CommonUtils
										.getFilePath(absolutePath
												.substring(jbcusUpgradePath
														.length() - 6)));
					}
					fileResource.setLastModife(tempFile.length());
					listFileResource.add(fileResource);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 获得当前年份,用于程序将查询那个年度的表
	 * @param jdbcFile
	 */
	public void setCasYear(String jdbcFile) {
		Properties jdbcProperties = new Properties();		
        File file = new File(jdbcFile);
        try {
            if (file.exists()) {
                FileInputStream in = new FileInputStream(file);
                jdbcProperties.load(in);
                in.close();
                System.out.println(" jdbcFile loaded  ------  " + file.getAbsolutePath());
            }
        } catch (Exception ex) {
            ex.printStackTrace();   
            return;
        }
    	
		String url = jdbcProperties.getProperty("jdbc.url");
        String driverClassName = jdbcProperties.getProperty("jdbc.driverClassName");
        String username = jdbcProperties.getProperty("jdbc.username");
        String password = jdbcProperties.getProperty("jdbc.password");
        String tableOwner = jdbcProperties.getProperty("jdbc.tableOwner");
		Connection conn = null;
		try {
			Class.forName(driverClassName);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		try {
			conn = DriverManager.getConnection(url,username,password);
			if(tableOwner != null && !tableOwner.equals("")){
				conn.setCatalog(tableOwner);
			}
		} catch (Exception e) {			
			e.printStackTrace();
			return;
		}
		try {
			Statement dstmt = conn.createStatement();
			ResultSet rs = dstmt.executeQuery("select * from writeAccountYear");
			while(rs.next()){
				String year = rs.getString("year"); //只要第一个条记录,不管那个公司
				CommonUtils.setYear(year);
				break;
			}		
			dstmt.close() ;
		} catch (SQLException e) {
			;
		}finally{
			try{
				conn.close() ;
			}catch(Exception ex){
				
			}
		}
	}
	
	
	
	
	private void loadHbmJar(File jar) {
		if (!LocalSessionFactoryBeanSubClass.isJarExistHbmXml(jar)) {
			return;
		}
		try {

			ClassLoaderUtil.listBootstrapClassPath();
			ClassLoaderUtil.listSystemClassPath();
			ClassLoaderUtil.listExtClassPath();
			
			ClassLoaderUtil.addURL2ClassLoader(jar.toURL(),CustomContextLoaderServlet.class.getClassLoader());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void loadHbmJar() {
		
		System.out.println("开始加载.................. ");
		try {
			//
			// 删除已经有删除标识的server插件
			//		
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
			loadHbmJar( f);
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
			loadHbmJar( f);
		}
	}

}
