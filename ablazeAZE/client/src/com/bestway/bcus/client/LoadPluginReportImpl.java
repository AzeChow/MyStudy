package com.bestway.bcus.client;

import java.awt.Component;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.plugin.ClientReportPlugin;
import com.bestway.common.Request;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.PluginInfoClientReport;
import com.bestway.ui.winuicontrol.WindowOpenedEvent;

public class LoadPluginReportImpl implements WindowOpenedEvent {

	//
	// deployment.user.cachedir + "/"+ javaws
	//
	final static String PLUGIN_FOLDER = PluginInfoClientReport.REPORT_PLUGIN_FOLDER;

	/** Dir */
	final static char DIRECTORY_TYPE = 'D';

	/** Main JNLP types for downloaded resources JAR/CLASS/IMAGE */
	final static char RESOURCE_TYPE = 'R';

	/** the P is for PersistenceService */
	final static char MUFFIN_TYPE = 'P';

	/** main resource (Jar,image or muffin) */
	final static char MAIN_FILE_TAG = 'M';

	//
	// key == fileName + length(file size)
	//	
	Map<String, String> plugMap = null;

	/**
	 * JDialogBase or JInternalFrameBase windowOpen 时调用 component is JDialogBase
	 * or JInternalFrameBase
	 */
	public void windowOpened(Component component) {
		//
		// 获得运行时的目标类
		//
		String tragetClassName = component.getClass().getName();

//		System.out.println("tragetClassName = " + tragetClassName);

		ToolsAction toolsAction = (ToolsAction) CommonVars
				.getApplicationContext().getBean("toolsAction");
		List<PluginInfoClientReport> list = toolsAction
				.findPluginInfoClientReport(new Request(CommonVars
						.getCurrUser()), tragetClassName);
		
		//
		// 没有自定义客户端报表
		//
		if (list == null || list.size() <= 0) {
			return;
		}
		for (PluginInfoClientReport pluginInfoClientReport : list) {
			//
			// 是否有下载 没有检查版本
			//
			String fileName = pluginInfoClientReport.getFileName()==null?
					"":pluginInfoClientReport.getFileName().trim();
			String fileLength = pluginInfoClientReport.getFileLength()==null?
					"":pluginInfoClientReport.getFileLength().toString();
			String key = fileName+fileLength;
			if(!(key).equals("")){
				if (!validateDownLoad(key)) {
					System.out.println("!validateDownLoad(key)，跳过");
					continue;
				}
			}			
			// ///////////////////////////////////////////
			// 运行客户端报表插件,对目录对象进行修改或增加功能
			// 1.因为webstart 已经帮我们把内载入了
			// 2.所以直接构造该类即可
			// ///////////////////////////////////////////
			List<String> classNameList = pluginInfoClientReport.getMap().get(
					tragetClassName);
			System.out.println("classNameList.size===="+classNameList.size());
			for (String className : classNameList) {
				try {
					Object obj = Class.forName(className).newInstance();
					if (!(obj instanceof ClientReportPlugin)) {
						System.out.println(className + " 没有实现 -- "
								+ ClientReportPlugin.class.getName());
						System.out.println("没有实现 ClientReportPlugin 跳过");
						continue;
					}
					//
					// try run client report plugin
					//
					ClientReportPlugin clientReportPlugin = (ClientReportPlugin) obj;
					clientReportPlugin.execute(component);
					System.out.println("-- loadClass = " + clientReportPlugin.getClass().getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean validateDownLoad(String key) {
		return true;
		//
		// jdk version 不进行暂不进行检查
		//
		/*String jdkVersion = System.getProperty("java.version");
		if (jdkVersion.startsWith("1.6")) {
			System.out.println("jdkVersion.startsWith(1.6)==true");
			return true;
		}
		//
		 * 1.5一下加载客户端插件
		// 获取主机 host,port
		//
		String baseDir = System.getProperty("deployment.user.cachedir");
		System.out.println(baseDir);
		//
		// 这里方便测试而已
		//
		if (baseDir == null || baseDir.equals("")) {
			System.out.println("baseDir==true");
			return true;
		}
		String javaws = "javaws";
		String http = "http";
		String host = DIRECTORY_TYPE + CommonVars.getServerName();
		String port = MUFFIN_TYPE + CommonVars.getPort();
		String pluginDir = DIRECTORY_TYPE + "" + MAIN_FILE_TAG + PLUGIN_FOLDER;

		String separator = System.getProperty("file.separator");

		String path = (baseDir.endsWith(separator) ? baseDir : baseDir
				+ separator)
				+ javaws
				+ separator
				+ http
				+ separator
				+ host
				+ separator
				+ port + separator + pluginDir;

		File foder = new File(path);
		System.out.println(foder.getPath());
		if (!foder.exists()) {
			System.out.println("!foder.exists()==false");
			return false;
		}
		if (plugMap == null) {
			plugMap = new HashMap<String, String>();
			File[] files = foder.listFiles();
			// if(files)
			for (File f : files) {
				String fileName = f.getName();
				//
				// 打包的文件才可以
				//			
				if (f.isDirectory() == true || !fileName.endsWith(".jar")) {
					continue;
				}
				if (fileName.startsWith("" + RESOURCE_TYPE + MAIN_FILE_TAG)) {
					plugMap.put(fileName.substring(2).trim() + f.length(),
							fileName);
				}
			}
		}
		System.out.println("plugMap"+plugMap==null);
		return plugMap.containsKey(key);*/
	}

}
