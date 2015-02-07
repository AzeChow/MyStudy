package com.bestway.common.tools.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
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
import com.bestway.common.tools.entity.PluginInfoClient;
import com.bestway.common.tools.entity.PluginTreeNode;
import com.bestway.common.tools.entity.ReplaceIntreeList;

public class PluginClientLogic {
	private ToolsDao toolsDao = null;

	private static final String PLUGIN_FOLDER = PluginTreeNode.PLUGIN_FOLDER;

	private static final String PLUGIN_FOLDER_PATH = WebUtils.getWebAppRoot()
			+ PLUGIN_FOLDER;

	private static final String PLUGIN_FOLDER_BAK_PATH = WebUtils
			.getWebAppRoot()
			+ "clientpluginsbak";

	private static final String PLUGIN_CONFIG_FILE = "plugin.xml";

	private Log loger = LogFactory.getLog(PluginClientLogic.class);

	//
	// 存在的文件 key = 文件名 + 最后修改时间
	//
	private Map<String, PluginInfoClient> existFileMap = new HashMap<String, PluginInfoClient>();

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

				Element root = doc.getRootElement();
				List elementId = root.getChildren("id");
				List elementCnName = root.getChildren("cnName");
				List elementDescription = root.getChildren("description");
				List elementParentPath = root.getChildren("parentPath");
				List elementChildIndex = root.getChildren("childIndex");
				List elementReplace = root.getChildren("replace");
				List elementReplacePath = root.getChildren("replaceFormPath");
				List replaceForm = root.getChildren("replaceForm");
				List<Element> replaceList = root.getChildren("replaceList");
				String id = "";
				String cnName = "";
				String description = "";
				String replacePath = "";
				String replaceform = "";
				int childIndex = -1;

				String parentPath = PluginInfoClient.ROOT_NODE;
				String replace = "";
				if (elementId != null && elementId.size() > 0) {
					id = ((Element) elementId.get(0)).getTextTrim();
				}
				if (elementCnName != null && elementCnName.size() > 0) {
					cnName = ((Element) elementCnName.get(0)).getTextTrim();
				}
				if (elementDescription != null && elementDescription.size() > 0) {
					description = ((Element) elementDescription.get(0))
							.getTextTrim();
				}
				if (elementReplace != null && elementReplace.size() > 0) {
					String rplace = ((Element) elementReplace.get(0))
							.getTextTrim();
					if (!rplace.equals("")) {
						replace = rplace;
					}
				}
				if (elementParentPath != null && elementParentPath.size() > 0) {
					String tempParentPath = ((Element) elementParentPath.get(0))
							.getTextTrim();
					if (!tempParentPath.equals("")) {
						parentPath = tempParentPath;
					}
				}
				if (elementReplacePath != null && elementReplacePath.size() > 0) {
					replacePath = ((Element) elementReplacePath.get(0))
							.getTextTrim();
				}
				if (replaceForm != null && replaceForm.size() > 0) {
					replaceform = ((Element) replaceForm.get(0)).getTextTrim();
				}
				if (elementChildIndex != null && elementChildIndex.size() > 0) {
					String childIndexStr = ((Element) elementChildIndex.get(0))
							.getTextTrim();
					if (childIndexStr != null && !childIndexStr.equals("")) {
						try {
							childIndex = Integer.parseInt(childIndexStr);
						} catch (Exception eee) {
							eee.printStackTrace();
						}
					}
				}
				ReplaceIntreeList replaceIntreeList = new ReplaceIntreeList();
				if (replaceList != null && replaceList.size() > 0) {
					for (int i = 0; i < replaceList.size(); i++) {
						ReplaceIntreeList temp = new ReplaceIntreeList();
						Element ent = ((Element) replaceList.get(i));
						String path = ent.getAttributeValue("path");
						String calss = ent.getAttributeValue("class");
						if (path == null || calss == null
								|| path.trim().equals("") || calss.equals("")) {
							continue;
						}
						temp.setRepalcePath(path);
						temp.setRePalceClass(calss);
						replaceIntreeList.addReplaceIntreeList(temp);
					}
				}

				PluginInfoClient pluginInfoClient = new PluginInfoClient();
				pluginInfoClient.setId(id);
				pluginInfoClient.setCnName(cnName);
				pluginInfoClient.setDescription(description);
				pluginInfoClient.setFileName(f.getName());
				pluginInfoClient.setFilePath(f.getAbsolutePath());
				pluginInfoClient.setModifiyTime(modifiyTime);
				pluginInfoClient.setChildIndex(childIndex);
				pluginInfoClient.setParentPath(parentPath);
				pluginInfoClient.setFileLength(f.length());
				pluginInfoClient.setReplace(replace);
				pluginInfoClient.setReplaceform(replaceform);
				pluginInfoClient.setReplacePath(replacePath);
				List<PluginTreeNode> pluginTreeNodeList = new ArrayList<PluginTreeNode>();
				createXmlNodeList(root, pluginTreeNodeList);
				pluginInfoClient.setList(pluginTreeNodeList);
				pluginInfoClient.setReplaceIntreeList(replaceIntreeList);
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

	/** 递归获得 pluginTreeNodeList */
	private void createXmlNodeList(Element xmlNode,
			List<PluginTreeNode> pluginTreeNodeList) throws IOException,
			JDOMException {
		List list = xmlNode.getChildren("link");
		if (list == null || list.size() == 0) {
			return;
		}
		int size = list.size();
		for (int i = 0; i < size; i++) {
			Element element = (Element) list.get(i);
			String targetForm = element.getAttribute("targetForm").getValue()
					.toString().trim();
			String caption = element.getAttribute("caption").getValue()
					.toString();
			String strShow = "";
			if (element.getAttribute("isshow") != null) {
				strShow = element.getAttribute("isshow").getValue();
			}
			PluginTreeNode item = new PluginTreeNode();
			item.setCaption(caption);
			item.setIsshow(strShow);
			item.setTargetForm(targetForm);

			List<PluginTreeNode> tempList = new ArrayList<PluginTreeNode>();
			item.setList(tempList);
			pluginTreeNodeList.add(item);
			createXmlNodeList(element, tempList);
		}
	}

	/**
	 * 载入所有插件信息
	 * 
	 * @return
	 */
	public List<PluginInfoClient> loadClientPlugin() {
		this.loadClientPlugInfo();
		List<PluginInfoClient> list = new ArrayList<PluginInfoClient>();
		list.addAll(this.existFileMap.values());
		return list;
	}

	/**
	 * 下载插件
	 * 
	 * @return
	 */
	public byte[] downloadClientPlugin(PluginInfoClient pluginInfoClient) {
		String filePath = pluginInfoClient.getFilePath();
		return CommonUtils.getBytesByFile(filePath);
	}

	/**
	 * 上传插件
	 * 
	 */
	public void uploadClientPlugin(String fileName, byte[] bytes) {
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
	}

	/**
	 * 删除一个插件
	 */
	public boolean deleteClientPlugin(PluginInfoClient pluginInfo) {
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
				//
				// 2.当客户端插件被删除时,删除 "serverplugins" 文件夹中相同的文件.如果这个文件
				// 不能删除那么说明该jar文件已经载入并动行了,那么写入 "deleteServerFlagJarFile "
				// 版本文件中，做为删除标志
				//
				boolean isSucceed = deleteServerPluginFile.delete();
				if (!isSucceed) {
					fileName = pluginInfo.getFileName().trim();
					String modifiyTime = getModifiyTime(deleteServerPluginFile.lastModified());
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
	public void deleteAllClientPlugin() {
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
								modifiyTime);
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
			+ "serverplugins";

	private static final String VERSION_FILE_NAME = PLUGIN_FOLDER_SERVER
			+ File.separator + "deleteServerFlagJarFile";

	private Properties propsDeleteVersion = new Properties();

	/**
	 * 动态动行一个插件 只要求方法名相同，参数个数相同，来调用某个方法
	 * 
	 * @param obj
	 * @return
	 */
	public Object runServerPlugin(String className, String methodName,
			Object... args) {
		return runServerPlugin(className, methodName, null, args);
	}

	/**
	 * 动态动行一个插件 非常准确的调用某个方法
	 * 
	 * @param obj
	 * @return
	 */
	public Object runServerPlugin(String className, String methodName,
			Class[] paramTypes, Object... args) {
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
			// loger.info("lastModified ======= " + modifiyTime);
			String key = fileName + getModifiyTime(f.lastModified());
			//
			// 是否是删除的
			//
			if (propsDeleteVersion.containsKey(key)) {
				continue;
			}
			try {
				if (LocalSessionFactoryBeanSubClass.isJarExistHbmXml(f)) {
					// ...... 将会写代码
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
			Class clazz = cl.loadClass(runClass);
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
	public List runServerPlugin(String className, Map parameters) {
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
			String key = fileName + getModifiyTime(f.lastModified());
			//
			// 是否是删除的
			//
			if (propsDeleteVersion.containsKey(key)) {
				continue;
			}
			try {
				if (LocalSessionFactoryBeanSubClass.isJarExistHbmXml(f)) {
					// ...... 将会写代码
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

			Object obj = cl.loadClass(runClass).newInstance();
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
	 * @param lastModified
	 * @return
	 */
	private static String getModifiyTime (long lastModified){
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
