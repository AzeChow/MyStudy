package com.bestway.common.tools.logic;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.bestway.common.CommonUtils;
import com.bestway.common.WebUtils;
import com.bestway.common.plugin.JbcusServerPlugin;
import com.bestway.common.tools.dao.ToolsDao;
import com.bestway.common.tools.entity.PluginInfo;
import com.bestway.common.tools.entity.PluginParameter;

public class PluginLogic {
	private ToolsDao						toolsDao			= null;
	private static String					PLUGIN_FOLDER		= WebUtils
																		.getWebAppRoot()
																		+ "plugins";
	private static String					PLUGIN_CONFIG_FILE	= "plugin.xml";
	private static String					VERSION_FILE_NAME	= PLUGIN_FOLDER
																		+ File.separator
																		+ "deleteFlagJarFile";
	private Properties						propsDeleteVersion	= new Properties();
	private Log								loger				= LogFactory
																		.getLog(PluginLogic.class);

	//
	// 存在的文件 key = 文件名 + 最后修改时间
	//
	private Map<String, PluginInfo>			existFileMap		= new HashMap<String, PluginInfo>();
	private Map<String, JbcusServerPlugin>	pluginPool			= new Hashtable<String, JbcusServerPlugin>();

	public ToolsDao getToolsDao() {
		return toolsDao;
	}

	public void setToolsDao(ToolsDao toolsDao) {
		this.toolsDao = toolsDao;
	}

	// ---------------------------
	//
	// 动态动行一个插件
	//
	// ---------------------------

	/**
	 * 动态动行一个插件
	 * 
	 * @param obj
	 * @return
	 */
	public List runPlugin(PluginInfo pluginInfo) {
		List<Object> returnList = new ArrayList<Object>();
		String key = pluginInfo.getClassName();
		JbcusServerPlugin plugin = pluginPool.get(key);
		if (plugin != null && plugin.getState() == JbcusServerPlugin.ISRUN) {
			returnList.add(pluginInfo.getFileName() + " 正在运行请先停止运行!!");
			return returnList;
		}
		try {
			//
			// 获得文件路径
			//
			String filePath = pluginInfo.getFilePath();
			File file = new File(filePath);
			if (!file.exists()) {
				returnList.add(pluginInfo.getFileName() + " 文件不存在!!");
				return returnList;
			}
			//
			// 获得当前线程上下文
			//
			ClassLoader thisLoader = Thread.currentThread()
					.getContextClassLoader();
			URLClassLoader cl = new URLClassLoader(new URL[] { file.toURL() },
					thisLoader);
			String runClass = pluginInfo.getClassName();

			Object obj = cl.loadClass(runClass).newInstance();
			if (!(obj instanceof JbcusServerPlugin)) {
				returnList.add(pluginInfo.getFileName() + " 不是有效的插件!!");
				return returnList;
			}
			//
			// try run jbcus server plugin
			//
			final JbcusServerPlugin jbcusPlugin = (JbcusServerPlugin) obj;
			// jbcusPlugin.
			final PluginInfo tempPluginInfo = pluginInfo;
			Thread t = new Thread() {
				public void run() {
					try {
						jbcusPlugin.init(CommonUtils.getContext(), toolsDao);
						jbcusPlugin.run(tempPluginInfo);
					} finally {
						jbcusPlugin.dispose();
					}
				}
			};
			t.start();
			pluginPool.put(key, jbcusPlugin);
			returnList.add(pluginInfo.getFileName() + " 动行成功!!");
			return returnList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * 初始化 plugin info
	 * 
	 */
	public void init() {
		//
		// 删除插件
		//
		deleteAllPlugin();
		//
		// 载入插件
		//
		loadPlugInfo();
		//
		// 执行自动运行的插件...
		//
		String[] keys = existFileMap.keySet().toArray(new String[0]);
		for (String key : keys) {
			final PluginInfo pluginInfo = existFileMap.get(key);
			if (pluginInfo.getIsAutoRun() != null
					&& pluginInfo.getIsAutoRun().booleanValue() == true) {
				runPlugin(pluginInfo);
			}
		}
	}

	/**
	 * 重新载入所有 plugin info
	 * 
	 */
	private void loadPlugInfo() {
		// loger.info("pluginFolder == " + PLUGIN_FOLDER);
		File file = new File(PLUGIN_FOLDER);
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
			Date date = new Date();

			date.setTime(f.lastModified());
			String modifiyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(date);
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
			try {

				Document doc = null;
				JarInputStream jarInputStream = new JarInputStream(
						new FileInputStream(f));
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

				if (doc == null) {
					loger.info(f.getName() + " 文件中 " + PLUGIN_CONFIG_FILE
							+ " 不存在 !!");
					continue;
				}

				Element root = doc.getRootElement();
				List elementId = root.getChildren("id");
				List elementClassName = root.getChildren("className");
				List elementCnName = root.getChildren("cnName");
				List elementDescription = root.getChildren("description");
				List elementIsAutoRun = root.getChildren("isAutoRun");
				List elementParameters = root.getChildren("parameters");

				String id = "";
				String className = "";
				String cnName = "";
				String description = "";
				boolean isAutoRun = false;

				if (elementId != null && elementId.size() > 0) {
					id = ((Element) elementId.get(0)).getTextTrim();
				}
				if (elementClassName != null && elementClassName.size() > 0) {
					className = ((Element) elementClassName.get(0))
							.getTextTrim();
				}
				if (elementCnName != null && elementCnName.size() > 0) {
					cnName = ((Element) elementCnName.get(0)).getTextTrim();
				}
				if (elementDescription != null && elementDescription.size() > 0) {
					description = ((Element) elementDescription.get(0))
							.getTextTrim();
				}
				if (elementIsAutoRun != null && elementIsAutoRun.size() > 0) {
					String isAutoRunStr = ((Element) elementIsAutoRun.get(0))
							.getTextTrim();
					try {
						isAutoRun = new Boolean(isAutoRunStr);
					} catch (Exception ex) {
						isAutoRun = false;
					}
				}
				//
				// 插件参数值
				//
				Map<String, PluginParameter> parameters = new LinkedHashMap<String, PluginParameter>();

				if (elementParameters != null && elementParameters.size() > 0) {
					List list = ((Element) elementParameters.get(0))
							.getChildren("parameter");
					if (list == null || list.size() == 0) {
						return;
					}
					int size = list.size();
					for (int i = 0; i < size; i++) {
						Element element = (Element) list.get(i);
						Attribute paraKeyAttribute = element
								.getAttribute("key");
						Attribute paraValueAttribute = element
								.getAttribute("value");
						Attribute paraCnNameAttribute = element
								.getAttribute("cnName");

						if (paraKeyAttribute == null) {
							loger.info(f.getName()
									+ " 插件文件中，plugin.xml 中参数的关键字不能为空");
							continue;
						}
						String paraKey = paraKeyAttribute.getValue().toString()
								.trim();
						if (paraKey.equals("")) {
							loger.info(f.getName()
									+ " 插件文件中，plugin.xml 中参数的关键字不能为空");
							continue;
						}
						String paraValue = "";
						if (paraValueAttribute != null) {
							paraValue = paraValueAttribute.getValue()
									.toString();
						}
						String paraCnName = "";
						if (paraCnNameAttribute != null) {
							paraCnName = paraCnNameAttribute.getValue()
									.toString();
						}
						PluginParameter pluginParameter = new PluginParameter();
						pluginParameter.setCnName(paraCnName);
						pluginParameter.setKey(paraKey);
						pluginParameter.setValue(paraValue);
						parameters.put(paraKey, pluginParameter);
					}
				}
				if (cnName.equals("")) {
					loger.info(f.getName()
							+ " 插件文件中，plugin.xml 中服务器端插件类名不可为空......");
					continue;
				}

				PluginInfo pluginInfo = new PluginInfo();
				pluginInfo.setClassName(className);
				pluginInfo.setId(id);
				pluginInfo.setCnName(cnName);
				pluginInfo.setDescription(description);
				pluginInfo.setFileName(f.getName());
				pluginInfo.setFilePath(f.getAbsolutePath());
				pluginInfo.setModifiyTime(modifiyTime);
				pluginInfo.setIsAutoRun(isAutoRun);
				pluginInfo.setParameter(parameters);
				//
				// 存入
				//
				existFileMap.put(key, pluginInfo);
			} catch (Exception ex) {
				ex.printStackTrace();
				loger.error(ex.getMessage());
			}
		}
		//
		// 最后删除已过时的缓存记录
		//		
		Iterator<String> iterator = existFileMap.keySet().iterator();
		List<String> keyList = new ArrayList<String>();
		while (iterator.hasNext()) {
			String key = iterator.next();
			keyList.add(key);
		}
		for (String key : keyList) {
			if (!nowExistMap.containsKey(key)) {
				existFileMap.remove(key);
			} else if (propsDeleteVersion.containsKey(key)) {
				existFileMap.remove(key);
			}
		}
	}

	/**
	 * 载入所有插件信息
	 * 
	 * @return
	 */
	public List<PluginInfo> loadPlugin() {
		this.loadPlugInfo();
		List<PluginInfo> list = new ArrayList<PluginInfo>();
		list.addAll(this.existFileMap.values());
		return list;
	}

	/**
	 * 下载插件
	 * 
	 * @return
	 */
	public byte[] downloadPlugin(PluginInfo pluginInfo) {
		String filePath = pluginInfo.getFilePath();
		return CommonUtils.getBytesByFile(filePath);
	}

	/**
	 * 上传插件
	 * 
	 */
	public void uploadPlugin(String fileName, byte[] bytes) {
		String filePath = PLUGIN_FOLDER + "/" + fileName;
		CommonUtils.saveFileByBytes(filePath, bytes);
		loadPlugInfo();
		String[] keys = existFileMap.keySet().toArray(new String[0]);
		for (String key : keys) {
			//
			// 执行自动运行的插件...
			//
			PluginInfo pluginInfo = existFileMap.get(key);
			if (pluginInfo.getFileName().trim().equals(fileName.trim())) {
				if (pluginInfo.getIsAutoRun() != null
						&& pluginInfo.getIsAutoRun().booleanValue() == true) {
					runPlugin(pluginInfo);
					break;
				}
			}
		}
	}

	/**
	 * 删除一个插件 不是真的删除,只是做上记号 用于下次服务端程序中重启来正式删除
	 */
	public boolean deletePlugin(PluginInfo pluginInfo) {
		String fileName = pluginInfo.getFileName().trim();
		String modifiyTime = pluginInfo.getModifiyTime();
		// System.out.println( new File(pluginInfo.getFilePath()).delete() );
		propsDeleteVersion.put(fileName + modifiyTime, modifiyTime);
		//
		// 把 propsDeleteVersion 保存到文件
		//
		saveDeleteFlagJarFile();
		return true;
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
			File file = new File(PLUGIN_FOLDER);
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
	private void deleteAllPlugin() {
		File foder = new File(PLUGIN_FOLDER);
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
			//
			// 修改时间
			//
			Date date = new Date();
			date.setTime(f.lastModified());
			String modifiyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(date);

			String key = fileName + modifiyTime;

			if (propsVersion.containsKey(key)) {
				f.delete();
			}
		}
		file.delete();
	}

	/** 停止服务器插件 */
	public void stopJbcusServerPlugin(PluginInfo pluginInfo) {
		String key = pluginInfo.getClassName();
		JbcusServerPlugin plugin = pluginPool.get(key);
		if(plugin == null){
			return;
		}
		plugin.stop();		
		pluginPool.remove(key);
	}

	/** 重启服务器插件 */
	public void restartJbcusServerPlugin(PluginInfo pluginInfo) {
		stopJbcusServerPlugin(pluginInfo);
		runPlugin(pluginInfo);
	}

	/** 服务端插件是否运行 */
	public boolean isRunJbcusServerPlugin(PluginInfo pluginInfo) {
		String key = pluginInfo.getClassName();
		JbcusServerPlugin thread = pluginPool.get(key);
		if(thread == null){
			return false;
		}
		return thread.getState() == JbcusServerPlugin.ISRUN;
	}

	/** 保存插件信息 */
	public PluginInfo savePluginInfo(PluginInfo pluginInfo) {

		JarInputStream jarInputStream = null;
		JarOutputStream jarOutputStream = null;
		try {
			String destFileName = PLUGIN_FOLDER + "/"
					+ pluginInfo.getFileName();
			String srcFileName = PLUGIN_FOLDER + "/temp";

			jarInputStream = new JarInputStream(new FileInputStream(
					destFileName));

			jarOutputStream = new JarOutputStream(new FileOutputStream(
					srcFileName));

			JarEntry jarEntry = null;
			while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {

				if (jarEntry.getName().equals(PLUGIN_CONFIG_FILE)) {
					jarInputStream.closeEntry();
					jarOutputStream.putNextEntry(new JarEntry(
							PLUGIN_CONFIG_FILE));
					//
					// 重新生成 PLUGIN_CONFIG_FILE
					//
					XMLOutputter outputter = new XMLOutputter("		", false,
							"UTF-8");
					Document doc = new Document();
					Element rootElement = new Element("plugin");
					Element idElement = new Element("id");
					idElement.addContent(pluginInfo.getId());

					Element classNameElement = new Element("className");
					classNameElement.addContent(pluginInfo.getClassName());

					Element cnNameElement = new Element("cnName");
					cnNameElement.addContent(pluginInfo.getCnName());

					Element descriptionElement = new Element("description");
					descriptionElement.addContent(pluginInfo.getDescription());

					Element isAutoRunElement = new Element("isAutoRun");
					isAutoRunElement.addContent(pluginInfo.getIsAutoRun()
							.toString());

					Element parametersElement = new Element("parameters");
					Map<String, PluginParameter> map = pluginInfo
							.getParameter();
					Iterator<PluginParameter> iterator = map.values()
							.iterator();
					while (iterator.hasNext()) {
						PluginParameter tempP = iterator.next();
						Element parameterElement = new Element("parameter");
						Attribute aKey = new Attribute("key", tempP.getKey());
						Attribute aValue = new Attribute("value", tempP
								.getValue());
						Attribute aCnName = new Attribute("cnName", tempP
								.getCnName());
						parameterElement.setAttribute(aKey);
						parameterElement.setAttribute(aValue);
						parameterElement.setAttribute(aCnName);
						parametersElement.addContent(parameterElement);
					}

					rootElement.addContent(idElement);
					rootElement.addContent(classNameElement);
					rootElement.addContent(cnNameElement);
					rootElement.addContent(descriptionElement);
					rootElement.addContent(isAutoRunElement);
					if (parametersElement.getChildren().size() > 0) {
						rootElement.addContent(parametersElement);
					}
					doc.setRootElement(rootElement);
					ByteArrayOutputStream btyeArray = new ByteArrayOutputStream();
					outputter.output(doc, btyeArray);
					jarOutputStream.write(btyeArray.toByteArray());
					jarOutputStream.closeEntry();
					continue;
				}

				jarOutputStream.putNextEntry(jarEntry);
				byte[] buf = new byte[1024 * 32];
				int len;
				while ((len = jarInputStream.read(buf)) > 0) {
					jarOutputStream.write(buf, 0, len);
				}
				jarInputStream.closeEntry();
				jarOutputStream.closeEntry();
			}

			jarInputStream.close();
			jarOutputStream.flush();
			jarOutputStream.close();

			//
			// 载入插件
			//
			CommonUtils.copyFile(srcFileName, destFileName);
			//
			// 删除临时文件
			//
			new File(srcFileName).delete();
			//
			// 载入插件
			//
			loadPlugInfo();
			//
			// 执行自动运行的插件...
			//
			String[] keys = existFileMap.keySet().toArray(new String[0]);
			for (String key : keys) {
				PluginInfo tempPluginInfo = existFileMap.get(key);
				if (tempPluginInfo.getFileName().trim().equals(
						pluginInfo.getFileName().trim())) {
					// loger.info("======== 返回插件时运行 " + destFileName);
					return tempPluginInfo;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (jarInputStream != null) {
					jarInputStream.close();
				}
			} catch (Exception e) {

			}
			try {
				if (jarOutputStream != null) {
					jarOutputStream.close();
				}
			} catch (Exception e) {

			}
			throw new RuntimeException(ex.getMessage());
		}
		return pluginInfo;
	}

}
