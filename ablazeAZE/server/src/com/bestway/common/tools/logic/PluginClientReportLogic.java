package com.bestway.common.tools.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;

import com.bestway.bcus.LocalSessionFactoryBeanSubClass;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.WebUtils;
import com.bestway.common.plugin.JbcusPlugin;
import com.bestway.common.tools.dao.ToolsDao;
import com.bestway.common.tools.entity.ApplicationContextAnnotation;
import com.bestway.common.tools.entity.BaseDaoAnnotation;
import com.bestway.common.tools.entity.PluginInfoClientReport;

@SuppressWarnings("unchecked")
public class PluginClientReportLogic {
	private ToolsDao toolsDao = null;
	private ToolsLogic toolsLogic = null;
	private static final String PLUGIN_FOLDER = PluginInfoClientReport.REPORT_PLUGIN_FOLDER;
	private static final String PLUGIN_FOLDER_PATH = WebUtils.getWebAppRoot()
			+ PLUGIN_FOLDER;
	private static final String PLUGIN_FOLDER_BAK_PATH = WebUtils
			.getWebAppRoot()
			+ PLUGIN_FOLDER + "bak";
	// private static final String JNLP_FILE = WebUtils
	// .getWebAppRoot()
	// + "Webjbcustemp.jnlp";
	// private static final String JNLP_FILE_BAK = WebUtils
	// .getWebAppRoot()
	// + "Webjbcustemp.jnlp.bak";
	private static final String PLUGIN_CONFIG_FILE = "plugin.xml";
	private static final Log loger = LogFactory.getLog(PluginClientReportLogic.class);
	//
	// 存在的文件 key = 文件名 + 最后修改时间
	//
	private Map<String, PluginInfoClientReport> existFileMap = new HashMap<String, PluginInfoClientReport>();

	public ToolsLogic getToolsLogic() {
		return toolsLogic;
	}

	public void setToolsLogic(ToolsLogic toolsLogic) {
		this.toolsLogic = toolsLogic;
	}

	/**
	 * 初始化 plugin info
	 * 
	 */
	public void init() {
		//
		// 删除已经有删除标识的server插件
		//
		// deleteAllServerPlugin();
		//
		// 载入插件
		//
		loadClientPlugInfo();		
	}

	/**
	 * 重新载入所有 plugin info
	 * 
	 */
	private void loadClientPlugInfo() {
		// loger.info("pluginFolder == " + PLUGIN_FOLDER_PATH);
		File file = new File(PLUGIN_FOLDER_PATH);
		if (!file.exists()) {
			file.mkdir();
			return;
		}
		File[] files = file.listFiles();

		Map<String, String> nowExistMap = new HashMap<String, String>();

		for (File f : files) {
			String fileName = f.getName();
			//
			// 打包的文件才可以
			//			
			if (f.isDirectory() == true || !fileName.endsWith(".jar")) {
				continue;
			}
			//
			// 修改时间
			//
			String modifiyTime = getModifiyTime(f.lastModified());
			// loger.info("lastModified ======= " + modifiyTime);

			String key = fileName + modifiyTime;
			//
			//
			//
			nowExistMap.put(key, key);
			//
			// 是最新的不用再一次载入
			//
			if (this.existFileMap.containsKey(key)) {
				continue;
			}
			//
			// 载入文件
			//	
			JarInputStream jarInputStream = null;

			try {

				Document doc = null;
				jarInputStream = new JarInputStream(new FileInputStream(f));
				JarEntry jarEntry = null;
				while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
					if (jarEntry.getName().equals(PLUGIN_CONFIG_FILE)) {
						SAXBuilder sb = new SAXBuilder();
						doc = sb
								.build(new BufferedReader(
										new InputStreamReader(jarInputStream,
												"UTF-8")));
						break;
					}
					jarInputStream.closeEntry();
				}
				jarInputStream.close();

				if (doc == null) {
					loger.info(f.getName() + " 文件中 " + PLUGIN_CONFIG_FILE
							+ " 不存在 !!");
					continue;
				}
				//
				// make base's PluginInfoClientReport by doc
				//
				PluginInfoClientReport pluginInfoClient = this
						.makePluginInfoClientReportByDoc(doc);

				pluginInfoClient.setFileName(f.getName());
				pluginInfoClient.setFilePath(f.getAbsolutePath());
				pluginInfoClient.setModifiyTime(modifiyTime);
				pluginInfoClient.setFileLength(f.length());

				//
				// 存入
				//
				existFileMap.put(key, pluginInfoClient);

			} catch (Exception ex) {
				ex.printStackTrace();
				if (jarInputStream != null) {
					try {
						jarInputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				loger.error(ex.getMessage());
			}
		}
		//
		// 最后删除已过时的缓存记录
		//		
		String[] keyArray = existFileMap.keySet().toArray(new String[0]);
		for (String key : keyArray) {
			if (!nowExistMap.containsKey(key)) {
				existFileMap.remove(key);
			}
		}
	}

	/** 生成 PluginInfoClientReport by inputStream plugin.xml doc */
	private PluginInfoClientReport makePluginInfoClientReportByDoc(Document doc) {
		Element root = doc.getRootElement();
		List elementId = root.getChildren("id");
		List elementCnName = root.getChildren("cnName");
		List elementDescription = root.getChildren("description");
		List<Element> elementTragetClassName = root
				.getChildren("tragetClassName");

		String id = "";
		String cnName = "";
		String description = "";

		if (elementId != null && elementId.size() > 0) {
			id = ((Element) elementId.get(0)).getTextTrim();
		}
		if (elementCnName != null && elementCnName.size() > 0) {
			cnName = ((Element) elementCnName.get(0)).getTextTrim();
		}
		if (elementDescription != null && elementDescription.size() > 0) {
			description = ((Element) elementDescription.get(0)).getTextTrim();
		}

		PluginInfoClientReport pluginInfoClient = new PluginInfoClientReport();
		pluginInfoClient.setId(id);
		pluginInfoClient.setCnName(cnName);
		pluginInfoClient.setDescription(description);

		Map<String, List<String>> map = new HashMap<String, List<String>>();

		for (Element eTraget : elementTragetClassName) {
			String windowName = eTraget.getAttribute("window").getValue();
			List<Element> elementClassName = eTraget.getChildren("className");
			List<String> classNameList = map.get(windowName);
			if (classNameList == null) {
				classNameList = new ArrayList<String>();				
				map.put(windowName, classNameList);
			}
			for (Element e : elementClassName) {
				String className = e.getTextTrim();
				if (!className.equals("")) {
					loger.info(className);
					classNameList.add(className);
				}
			}
		}
		pluginInfoClient.setMap(map);

		return pluginInfoClient;
	}

	/**
	 * 载入所有插件信息
	 * 
	 * @return
	 */
	public List<PluginInfoClientReport> loadReportPlugin() {
		this.loadClientPlugInfo();
		List<PluginInfoClientReport> list = new ArrayList<PluginInfoClientReport>();
		list.addAll(this.existFileMap.values());
		return list;
	}

	/**
	 * 
	 * 获得 PluginInfoClientReport by traget class name
	 * 
	 */
	public List<PluginInfoClientReport> findPluginInfoClientReport(
			String tragetClassName) {
		
		if (loger.isInfoEnabled()) {
			loger.info(" debug -- loadPluginByClass(tragetClassName) test ");
			loadPluginByClass();
		}

		Iterator<PluginInfoClientReport> iterator = this.existFileMap.values()
				.iterator();
		List<PluginInfoClientReport> list = new ArrayList<PluginInfoClientReport>();
		while (iterator.hasNext()) {
			PluginInfoClientReport p = iterator.next();
			if (p.getMap().containsKey(tragetClassName)) {
				list.add(p);
				System.out.println("plugin id : "+p.getId()+" name:" + p.getCnName()+" class:"+p.getClass()+"  "+p);
				for(String s : p.getMap().get(tragetClassName)){
					System.out.println(" target name class name : " + s);
				}
			}
		}		
		return list;
	}

	/** debug report plugin */
	private void loadPluginByClass() {
		try {

			ClassLoader thisLoader = Thread.currentThread()
					.getContextClassLoader();
			try {
				//
				// getResources is packagePath for example "com/bestway"
				//
				Enumeration<URL> urls = thisLoader.getResources("plugin_report");
				while (urls.hasMoreElements()) {
					URL url = urls.nextElement();
					loger.debug(url.toString());
					if (!"file".equals(url.getProtocol())) {
						continue;
					}
					File directory = new File(url.toURI()
							.getSchemeSpecificPart());
					//
					// load plugin folder all xml files
					//
					this.loadPluginByClass(directory);
				}
			} catch (Exception e) {
				loger.debug(e);
			}

		} catch (Exception e) {
			loger.debug(e);
		}
	}

	/** debug report plugin */
	private void loadPluginByClass(File directory) {
		File[] files = directory.listFiles();
		for (File f : files) {
			if (!f.isFile()) {
				continue;
			}
			String entryName = f.getName();
			if (entryName.endsWith(".xml")) {
				InputStream is = null;
				try {
					is = new FileInputStream(f);
				} catch (FileNotFoundException e) {
					loger.debug(e);
				}
				if (is == null) {
					continue;
				}
				SAXBuilder sb = new SAXBuilder();
				Document doc = null;
				try {
					doc = sb.build(new BufferedReader(new InputStreamReader(is,
							"UTF-8")));
				} catch (Exception e) {
					loger.debug(e);
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (IOException e1) {

						}
					}
				}				
				//
				// make base's PluginInfoClientReport by doc
				//
				PluginInfoClientReport pluginInfoClient = this
						.makePluginInfoClientReportByDoc(doc);
//				pluginInfoClient.setFileName(f.getName());
//				pluginInfoClient.setFilePath(f.getAbsolutePath());
//				pluginInfoClient.setFileLength(f.length());
				//
				// 存入
				//
				existFileMap.put(entryName, pluginInfoClient);
			}

		}
	}

	/**
	 * 下载插件
	 * 
	 * @return
	 */
	public byte[] downloadReportPlugin(PluginInfoClientReport pluginInfoClient) {
		String filePath = pluginInfoClient.getFilePath();
		return CommonUtils.getBytesByFile(filePath);
	}

	/**
	 * 上传插件
	 * 
	 */
	public void uploadReportPlugin(String fileName, byte[] bytes) {
		String filePath = PLUGIN_FOLDER_PATH + "/" + fileName;
		CommonUtils.saveFileByBytes(filePath, bytes);
		try {
			//
			// copy --> client plugin to "serverplugins" 文件夹中
			//
			CommonUtils.copyFile(filePath, PLUGIN_FOLDER_SERVER + "/"
					+ fileName);
		} catch (Exception ex) {
			new File(filePath).delete();
			throw new RuntimeException(ex);
		}
		// resetWebjbcustemp(loadReportPlugin());
		//
		// 执行数据库操作 如果有 hbm.xml 后缀的文件的时候才进行
		//
		// this.toolsLogic.executeSchemaUpdate(new File(filePath), true, true);
	}

	/**
	 * 删除一个插件
	 */
	public boolean deleteReportPlugin(PluginInfoClientReport pluginInfo) {
		String fileName = pluginInfo.getFilePath().trim();
		String serverPluginFileName = PLUGIN_FOLDER_SERVER + "/"
				+ pluginInfo.getFileName();
		File deleteFile = new File(fileName);
		File deleteServerPluginFile = new File(serverPluginFileName);

		File bakFoder = new File(PLUGIN_FOLDER_BAK_PATH);
		if (!bakFoder.exists()) {
			bakFoder.mkdir();
		}

		String srcFileName = fileName;
		String bakFileName = PLUGIN_FOLDER_BAK_PATH + "/"
				+ pluginInfo.getFileName();
		try {
			//
			// 先备份
			//
			CommonUtils.copyFile(srcFileName, bakFileName);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		boolean isDelete = deleteFile.delete();
		if (isDelete) {
			try {
				// resetWebjbcustemp(loadReportPlugin());
				//
				// 2.当客户端插件被删除时,删除 "serverplugins" 文件夹中相同的文件.如果这个文件
				// 不能删除那么说明该jar文件已经载入并动行了,那么写入 "deleteServerFlagJarFile "
				// 版本文件中，做为删除标志
				//
				boolean isSucceed = deleteServerPluginFile.delete();
				if (!isSucceed) {
					fileName = pluginInfo.getFileName().trim();
					String modifiyTime = getModifiyTime(deleteServerPluginFile
							.lastModified());
					propsDeleteVersion.put(fileName + modifiyTime, modifiyTime);
					saveDeleteFlagJarFile();
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					CommonUtils.copyFile(bakFileName, srcFileName);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		}
		return isDelete;
	}

	/**
	 * 保存有删除标志的文件
	 * 
	 */
	private void saveDeleteFlagJarFile() {
		//
		// 保存修改时间版本
		//
		FileOutputStream outputStream = null;
		try {
			File file = new File(PLUGIN_FOLDER_SERVER);
			if (!file.exists()) {
				file.mkdir();
				return;
			}
			outputStream = new FileOutputStream(VERSION_FILE_NAME);
			propsDeleteVersion
					.store(outputStream,
							"[ JBCUS PLUGIN FLAG VERSION BY LASTMODIFE IN BESTWAYSOFT ]");
			outputStream.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除插件
	 * 
	 */
	public void deleteAllReportPlugin() {
		File foder = new File(PLUGIN_FOLDER_PATH);
		File bakFoder = new File(PLUGIN_FOLDER_BAK_PATH);
		if (!foder.exists()) {
			foder.mkdir();
			return;
		}
		if (!bakFoder.exists()) {
			bakFoder.mkdir();
		} else {
			File[] files = bakFoder.listFiles();
			for (File f : files) {
				f.delete();
			}
		}

		File[] files = foder.listFiles();
		for (File f : files) {
			String fileName = f.getName();
			String bakFileName = PLUGIN_FOLDER_BAK_PATH + "/" + fileName;
			try {
				//
				// 先备份
				//
				CommonUtils.copyFile(f.getAbsolutePath(), bakFileName);
			} catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
		}

		boolean isFlag = false;
		for (File f : files) {
			String fileName = f.getName();
			//
			// 打包的文件才可以
			//			
			if (f.isDirectory() == true || !fileName.endsWith(".jar")) {
				continue;
			}
			boolean isDelete = f.delete();
			if (isDelete == true) { // 如果有一个插件被删除了就要刷新
				isFlag = true;
			}
		}

		if (isFlag) {
			try {
				// resetWebjbcustemp(loadReportPlugin());
				//
				// 2.当客户端插件被删除时,删除 "serverplugins" 文件夹中相同的文件.如果这个文件
				// 不能删除那么说明该jar文件已经载入并动行了,那么写入 "deleteServerFlagJarFile "
				// 版本文件中，做为删除标志
				//
				File serverFoder = new File(PLUGIN_FOLDER_SERVER);
				files = serverFoder.listFiles();
				for (File f : files) {
					String fileName = f.getName();
					if (f.isDirectory() == true || !fileName.endsWith(".jar")) {
						continue;
					}
					boolean isSucceed = f.delete();
					if (!isSucceed) {
						String modifiyTime = getModifiyTime(f.lastModified());
						propsDeleteVersion.put(fileName + modifiyTime,
								modifiyTime + "");
					}
				}
				saveDeleteFlagJarFile();

			} catch (Exception ex) {
				File[] bakFiles = bakFoder.listFiles();
				for (File f : bakFiles) {
					String fileName = f.getName();
					String srcFileName = PLUGIN_FOLDER_PATH + "/" + fileName;
					String bakFileName = f.getAbsolutePath();
					try {
						//
						// 还原
						//
						CommonUtils.copyFile(bakFileName, srcFileName);
					} catch (Exception eex) {
						eex.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * 重新写入 Webjbcustemp.jnlp
	 * 
	 */
	// private void resetWebjbcustemp(
	// List<PluginInfoClientReport> pluginInfoClients) {
	// String srcFileName = JNLP_FILE;
	// String dstFileName = JNLP_FILE_BAK;
	// try {
	// //
	// // 先备份
	// //
	// CommonUtils.copyFile(srcFileName, dstFileName);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// return;
	// }
	// BufferedReader bufferedReader = null;
	// FileInputStream fileInputStream = null;
	// InputStreamReader inputStreamReader = null;
	// FileWriter fileWriter = null;
	// try {
	// SAXBuilder sb = new SAXBuilder();
	// fileInputStream = new FileInputStream(srcFileName);
	// inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
	// bufferedReader = new BufferedReader(inputStreamReader);
	// Document doc = sb.build(bufferedReader);
	// Element root = doc.getRootElement();
	// //
	// //
	// // 获得 resources element
	// //
	// Element resources = root.getChild("resources");
	// List<Element> resourcesList = resources.getChildren();
	// for (int i = resourcesList.size() - 1; i >= 0; i--) {
	// Element e = resourcesList.get(i);
	// if (e.getAttribute("href") != null) {
	// String href = e.getAttribute("href").getValue().toString()
	// .trim();
	// //
	// // 是插件的包 删除
	// //
	// if (href.startsWith(PLUGIN_FOLDER)) {
	// resources.removeContent(e);
	// }
	// }
	// }
	// //
	// // 加入新的 插件的包 jar 元素到 resources 中去
	// // <jar href="clientplugins/clienttestplugin.jar"/>
	// //
	// for (int i = 0; i < pluginInfoClients.size(); i++) {
	// PluginInfoClientReport pluginInfoClient = pluginInfoClients
	// .get(i);
	//
	// Element jarElement = new Element("jar");
	// jarElement.setAttribute("href", PLUGIN_FOLDER + "/"
	// + pluginInfoClient.getFileName());
	// resources.addContent(jarElement);
	// resources.addContent("\n");
	// }
	// //
	// // 关闭文件流
	// //
	// bufferedReader.close();
	// fileInputStream.close();
	// inputStreamReader.close();
	// //
	// // 重新写入文件
	// //
	// XMLOutputter outputter = new XMLOutputter("		", false, "GBK");
	// fileWriter = new FileWriter(new File(srcFileName));
	// outputter.output(doc, fileWriter);
	// fileWriter.close();
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// try {
	// if (bufferedReader != null) {
	// bufferedReader.close();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// try {
	// if (fileWriter != null) {
	// fileWriter.close();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// try {
	// if (fileInputStream != null) {
	// fileInputStream.close();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// try {
	// if (inputStreamReader != null) {
	// inputStreamReader.close();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// //
	// // 恢原
	// //
	// try {
	// CommonUtils.copyFile(dstFileName, srcFileName);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// throw new RuntimeException(ex);
	// }
	// }
	// ///////////////////////////////////////////////////////////////////
	//
	// 以下代码是对客户端调用服务器方法时用到 (相当于这个插件即是服务器，也是客户端插件
	// 1.copy --> client plugin to "serverplugins" 文件夹中
	// 2.当客户端插件被删除时,删除 "serverplugins" 文件夹中相同的文件.如果这个文件
	// 不能删除那么说明该jar文件已经载入并动行了,那么写入 "deleteServerFlagJarFile "
	// 版本文件中，做为删除标志
	// 3.在server restart 时删除有删除标志的插件,并删除 "deleteServerFlagJarFile"
	// 4.调用 server plugin 进行动行
	//
	// ///////////////////////////////////////////////////////////////////
	public static final String PLUGIN_FOLDER_SERVER = WebUtils.getWebAppRoot()
			+ "reportserverplugins";

	private static final String VERSION_FILE_NAME = PLUGIN_FOLDER_SERVER
			+ File.separator + "deleteServerFlagJarFile";

	private Properties propsDeleteVersion = new Properties();

	/**
	 * 动态动行一个插件 只要求方法名相同，参数个数相同，来调用某个方法
	 * 
	 * @param obj
	 * @return
	 */
	public Object runReportServerPlugin(String className, String methodName,
			Object... args) {
		return runReportServerPlugin(className, methodName, null, args);
	}

	/**
	 * 动态动行一个插件 非常准确的调用某个方法
	 * 
	 * @param obj
	 * @return
	 */
	public Object runReportServerPlugin(String className, String methodName,
			Class[] paramTypes, Object... args) {
		
		//
		// 获得文件
		//		
		File file = new File(PLUGIN_FOLDER_SERVER);
		File[] files = file.listFiles();
		List<URL> urls = new ArrayList<URL>();
		List<URL> hasXmlUrls = new ArrayList<URL>();

		for (File f : files) {
			String fileName = f.getName();
			//
			// 打包的文件才可以
			//			
			if (f.isDirectory() == true || !fileName.endsWith(".jar")) {
				continue;
			}
			String modifiyTime = getModifiyTime(f.lastModified());
			// loger.info("lastModified ======= " + modifiyTime);
			String key = fileName + modifiyTime;
			//
			// 是否是删除的
			//
			if (propsDeleteVersion.containsKey(key)) {
				continue;
			}
			try {
				if (LocalSessionFactoryBeanSubClass.isJarExistHbmXml(f)) {
					hasXmlUrls.add(f.toURL());
				} else {
					urls.add(f.toURL());
				}
			} catch (Exception ex) {
			}
		}

		//
		// 获得当前线程上下文
		//
		try {
			ClassLoader thisLoader = Thread.currentThread()
					.getContextClassLoader();

			URLClassLoader cl = new URLClassLoader(urls.toArray(new URL[] {}),
					thisLoader);

			String runClass = className;
			Class clazz = null;
			clazz = cl.loadClass(runClass);
			//
			// 详见 Spring BeanUtils 源代码 只是多了输出一些信息,和私有构造
			//
			Object obj = BeanUtils.instantiateClass(clazz);
			//
			// 为对象 BaseDao 和 ApplicationContext set value 不管对象是否私有
			//
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(BaseDaoAnnotation.class)) {
					Class superClass = field.getType();
					while (superClass != null) {
						if (superClass.equals(BaseDao.class)) {
							field.setAccessible(true);
							field.set(obj, this.toolsDao);
							break;
						}
						superClass = superClass.getSuperclass();
					}
				} else if (field
						.isAnnotationPresent(ApplicationContextAnnotation.class)) {
					Class superClass = field.getType();
					while (superClass != null) {
						if (superClass.equals(ApplicationContext.class)) {
							field.setAccessible(true);
							field.set(obj, CommonUtils.getContext());
							break;
						}
						superClass = superClass.getSuperclass();
					}
				}
			}
			//
			// 详见 Spring BeanUtils 源代码
			//	
			
			Method method = null;
			if (paramTypes == null) {
				method = findDeclaredMethodWithEqualsParameters(clazz,
						methodName, args.length);
			} else {
				method = BeanUtils.findMethod(clazz, methodName, paramTypes);
			}
			if (method == null) {
				throw new RuntimeException("调用的方法 [ " + methodName + " ]不存在!!");
			}
			//
			// 在 obj 中进行调用
			//
			return method.invoke(obj, args);

		} catch (Exception ex) {			
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	public static Method findDeclaredMethodWithEqualsParameters(Class clazz,
			String methodName, int length) {
		Method[] methods = clazz.getDeclaredMethods();
		Method targetMethod = null;
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(methodName)) {
				if (methods[i].getParameterTypes().length == length) {
					targetMethod = methods[i];
					return targetMethod;
				}
			}
		}
		if (clazz.getSuperclass() != null) {
			return findDeclaredMethodWithEqualsParameters(
					clazz.getSuperclass(), methodName, length);
		}
		return null;
	}

	/**
	 * 动态动行一个插件 当客户端调用时,动态动行一个插件
	 * 
	 * @param obj
	 * @return
	 */
	public List runReportServerPlugin(String className, Map parameters) {
		List<Object> returnList = new ArrayList<Object>();
		//
		// 获得文件
		//		
		File file = new File(PLUGIN_FOLDER_SERVER);
		File[] files = file.listFiles();
		List<URL> urls = new ArrayList<URL>();

		for (File f : files) {
			String fileName = f.getName();
			//
			// 打包的文件才可以
			//			
			if (f.isDirectory() == true || !fileName.endsWith(".jar")) {
				continue;
			}
			String modifiyTime = getModifiyTime(f.lastModified());
			// loger.info("lastModified ======= " + modifiyTime);
			String key = fileName + modifiyTime;
			//
			// 是否是删除的
			//
			if (propsDeleteVersion.containsKey(key)) {
				continue;
			}
			try {

				if (LocalSessionFactoryBeanSubClass.isJarExistHbmXml(f)) {
				} else {
					urls.add(f.toURL());
				}
			} catch (Exception ex) {
			}
		}

		//
		// 获得当前线程上下文
		//
		try {
			ClassLoader thisLoader = Thread.currentThread()
					.getContextClassLoader();
			URLClassLoader cl = new URLClassLoader(urls.toArray(new URL[] {}),
					thisLoader);
			String runClass = className;
			Class clazz = null;

			clazz = cl.loadClass(runClass);

			Object obj = clazz.newInstance();
			if (!(obj instanceof JbcusPlugin)) {
				returnList.add(className + " 没有实现 JbcusPlugin !!");
				return returnList;
			}
			//
			// try run jbcus server plugin
			//
			JbcusPlugin jbcusPlugin = (JbcusPlugin) obj;
			jbcusPlugin.init(CommonUtils.getContext(), this.toolsDao);
			List returnObject = jbcusPlugin.run(parameters);
			jbcusPlugin.dispose();
			return returnObject;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * 删除插件
	 * 
	 */
	public static void deleteAllServerPlugin() {
		File foder = new File(PLUGIN_FOLDER_SERVER);
		if (!foder.exists()) {
			foder.mkdir();
			return;
		}
		//
		// 读取文件最后修改时间版本
		//
		Properties propsVersion = new Properties();
		File file = new File(VERSION_FILE_NAME);
		FileInputStream in = null;
		try {
			if (file.exists()) {
				in = new FileInputStream(file);
				propsVersion.load(in);
				// loger.info(" propsDeleteVersion loaded ------ ");
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		//
		// server start all delete
		//
		File[] files = foder.listFiles();
		for (File f : files) {
			String fileName = f.getName();
			//
			// 打包的文件才可以
			//			
			if (f.isDirectory() == true || !fileName.endsWith(".jar")) {
				continue;
			}

			String key = fileName + getModifiyTime(f.lastModified());

			if (propsVersion.containsKey(key)) {
				f.delete();
			}
		}
		file.delete();
	}

	/**
	 * 修改时间
	 * 
	 * @param lastModified
	 * @return
	 */
	private static String getModifiyTime(long lastModified) {
		Date date = new Date();
		date.setTime(lastModified);
		String modifiyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(date);
		return modifiyTime;
	}

	public ToolsDao getToolsDao() {
		return toolsDao;
	}

	public void setToolsDao(ToolsDao toolsDao) {
		this.toolsDao = toolsDao;
	}
}
