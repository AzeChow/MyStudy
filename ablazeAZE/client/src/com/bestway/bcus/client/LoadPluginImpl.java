package com.bestway.bcus.client;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.windows.form.JModuleMenu;
import com.bestway.client.windows.form.JModuleMenuItem;
import com.bestway.client.windows.form.LoadPlugin;
import com.bestway.client.windows.form.ModuleTreeNode;
import com.bestway.client.windows.form.FmMain.MenuActionListener;
import com.bestway.common.Request;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.PluginInfoClient;
import com.bestway.common.tools.entity.PluginTreeNode;
import com.bestway.common.tools.entity.ReplaceIntreeList;
import com.bestway.ui.winuicontrol.JMDIMenuBar;

public class LoadPluginImpl implements LoadPlugin {

	//
	// deployment.user.cachedir + "/"+ javaws
	//
	final static String PLUGIN_FOLDER = PluginTreeNode.PLUGIN_FOLDER;

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
	 * FmMain setVisible(true) 调用
	 */
	public void load(JTree jtree) {

		ToolsAction toolsAction = (ToolsAction) CommonVars
				.getApplicationContext().getBean("toolsAction");

		List<PluginInfoClient> pluginInfoClients = toolsAction
				.loadClientPlugin(new Request(CommonVars.getCurrUser()));

		DefaultTreeModel model = (DefaultTreeModel) jtree.getModel();
		ModuleTreeNode root = (ModuleTreeNode) model.getRoot();
		Map map = putFullCaptionInMap(null, root);
		for (PluginInfoClient pluginIn : pluginInfoClients) {
			// -------------------------------------------------------
			// 这段代码是为了替换
			boolean isreplace = (pluginIn.getReplace() != null && pluginIn
					.getReplace().equals("true")) ? true : false;
			String paranet = pluginIn.getReplacePath();
			if (map.containsKey(paranet) && isreplace) {
				ModuleTreeNode oldNode = (ModuleTreeNode) map.get(paranet);
				if (pluginIn.getReplaceform() != null
						&& !pluginIn.getReplaceform().equals("")) {
					oldNode.setTargetForm(pluginIn.getReplaceform());
				}
			}
			// --------
			ReplaceIntreeList replaceIntreeList = pluginIn
					.getReplaceIntreeList();
			if (replaceIntreeList != null) {
				List<ReplaceIntreeList> rlist = replaceIntreeList.getList();
				for (int i = 0; i < rlist.size(); i++) {
					ReplaceIntreeList rt = rlist.get(i);
					ModuleTreeNode oldNode = (ModuleTreeNode) map.get(rt
							.getRepalcePath());
					oldNode.setTargetForm(rt.getRePalceClass());
					System.out.println(rt.getRepalcePath());
					System.out.println(rt.getRePalceClass());
				}
			}
			// 这段代码是为了替换
			// --------------------------------------------------------
			String[] paths = pluginIn.getPaths();
			int childIndex = pluginIn.getChildIndex();
			List<PluginTreeNode> pluginTreeNodeList = pluginIn.getList();
			//
			// 是否有下载 没有检查版本
			//
			if (!validateDownLoad(pluginIn.getFileName().trim()
					+ pluginIn.getFileLength())) {
				continue;
			}
			//
			// 获得路径的最后一个结点，没有就创建
			//
			ModuleTreeNode parentNode = root;
			for (int i = 0; i < paths.length; i++) {
				boolean isExist = false;
				for (int k = 0; k < parentNode.getChildCount(); k++) {
					ModuleTreeNode tempNode = (ModuleTreeNode) parentNode
							.getChildAt(k);
					String caption = (String) tempNode.getUserObject();
					if (caption.equals(paths[i])) {
						parentNode = tempNode;
						isExist = true;
						break;
					}
				}
				if (isExist == false) {
					//
					// 创建新的
					//
					ModuleTreeNode newNode = new ModuleTreeNode(paths[i], "1",
							"");
					//
					// 加到最后
					//
					parentNode.add(newNode);
					//
					// 把地址转换
					//
					parentNode = newNode;
				}
			}

			//
			// 加入结点到树中
			//
			try {
				List<ModuleTreeNode> returnList = new ArrayList<ModuleTreeNode>();
				createNewChilderNode(pluginTreeNodeList, returnList);

				int childCount = parentNode.getChildCount();
				for (int i = 0; i < returnList.size(); i++) {
					if (childIndex == -1 || childIndex > childCount - 1) {
						parentNode.add(returnList.get(i));
					} else {
						parentNode.insert(returnList.get(i), childIndex++);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}

		}
		jtree.setModel(new DefaultTreeModel(root));
	}

	private String getNotesCaption(ModuleTreeNode node, String str) {
		if (node.getParent() == null || ((ModuleTreeNode) node).isRoot()) {
			return str;
		}
		if (str == null) {
			str = "";
		}
		if (str.equals("")) {
			str += (node.getCaption() == null ? "" : node.getCaption());
		} else {
			str += ("@" + (node.getCaption() == null ? "" : node.getCaption()));
		}
		return getNotesCaption((ModuleTreeNode) node.getParent(), str);
	}

	private List getAllLeaf(List list, ModuleTreeNode root) {
		if (list == null) {
			list = new ArrayList();
		}
		for (int m = 0; m < root.getChildCount(); m++) {
			ModuleTreeNode node = (ModuleTreeNode) root.getChildAt(m);
			if (node.isLeaf()) {
				list.add(node);
				// System.out.println((node.getCaption() == null ? "" : node
				// .getCaption()));
			} else {
				getAllLeaf(list, node);
			}
		}
		return list;
	}

	private Map putFullCaptionInMap(Map map, ModuleTreeNode root) {
		if (map == null) {
			map = new HashMap();
		}
		List listLeaf = new ArrayList();
		listLeaf = getAllLeaf(listLeaf, root);
		for (int i = 0; i < listLeaf.size(); i++) {
			ModuleTreeNode node = (ModuleTreeNode) listLeaf.get(i);
			String ss = getNotesCaption(node, null);
			String keys = getStringArray(ss);
			map.put(keys, node);
		}
		return map;
	}

	private String getStringArray(String str) {
		String rstr = "";
		String[] strs = str.split("@");
		if (strs == null || strs.length == 0) {
			return str;
		}
		for (int i = strs.length - 1; i >= 0; i--) {
			if (i == strs.length - 1) {
				rstr += strs[strs.length - 1];
			} else {
				rstr += ("@" + strs[i]);
			}
		}
		return rstr;
	}

	/** 返回结点集合 */
	private void createNewChilderNode(List<PluginTreeNode> pluginTreeNodeList,
			List<ModuleTreeNode> returnList) {
		for (int i = 0; i < pluginTreeNodeList.size(); i++) {
			PluginTreeNode item = pluginTreeNodeList.get(i);
			ModuleTreeNode node = new ModuleTreeNode(item.getCaption(), item
					.getIsshow(), item.getTargetForm());
			returnList.add(node);
			createNewChilderNode(item.getList(), node);
		}
	}

	/** 递归 */
	private void createNewChilderNode(List<PluginTreeNode> pluginTreeNodeList,
			ModuleTreeNode parentNode) {
		for (int i = 0; i < pluginTreeNodeList.size(); i++) {
			PluginTreeNode item = pluginTreeNodeList.get(i);
			ModuleTreeNode node = new ModuleTreeNode(item.getCaption(), item
					.getIsshow(), item.getTargetForm());
			parentNode.add(node);
			createNewChilderNode(item.getList(), node);
		}
	}

	/**
	 * FmMain setVisible(true) 调用
	 */
	public void load(JMDIMenuBar jJMenuBar, JMenu mOther, boolean isOverFlow,
			int totalWidth, int menuBarWidth, MenuActionListener actionListener) {
		ToolsAction toolsAction = (ToolsAction) CommonVars
				.getApplicationContext().getBean("toolsAction");

		List<PluginInfoClient> pluginInfoClients = toolsAction
				.loadClientPlugin(new Request(CommonVars.getCurrUser()));

		for (PluginInfoClient pluginIn : pluginInfoClients) {
			String[] paths = pluginIn.getPaths();
			int childIndex = pluginIn.getChildIndex();
			List<PluginTreeNode> pluginTreeNodeList = pluginIn.getList();
			//
			// 是否有下载 没有检查版本
			//
			if (!validateDownLoad(pluginIn.getFileName().trim()
					+ pluginIn.getFileLength())) {
				continue;
			}

			//
			// 加入结点到MENUBAR中
			//
			try {
				List<JModuleMenu> returnList = new ArrayList<JModuleMenu>();
				createNewChilderMenu(pluginTreeNodeList, returnList);
				for (JModuleMenu menu : returnList) {
					if (totalWidth >= menuBarWidth) {
						menu.addActionListener(actionListener);
						if (!isOverFlow) {
							jJMenuBar.add(mOther);
							isOverFlow = true;
						}
						mOther.add(menu);
						totalWidth += menu.getPreferredSize().width;
					} else {
						menu.addActionListener(actionListener);
						jJMenuBar.add(menu);
						totalWidth += menu.getPreferredSize().width;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	}

	/** 返回结点集合 */
	private void createNewChilderMenu(List<PluginTreeNode> pluginTreeNodeList,
			List<JModuleMenu> returnList) {
		for (int i = 0; i < pluginTreeNodeList.size(); i++) {
			PluginTreeNode item = pluginTreeNodeList.get(i);
			JModuleMenu node = new JModuleMenu("", item.getCaption(), item
					.getIsshow(), item.getTargetForm(), null);
			returnList.add(node);
			createNewChilderMenu(item.getList(), node);
		}
	}

	/** 递归 */
	private void createNewChilderMenu(List<PluginTreeNode> pluginTreeNodeList,
			JModuleMenu parentNode) {
		for (int i = 0; i < pluginTreeNodeList.size(); i++) {
			PluginTreeNode item = pluginTreeNodeList.get(i);
			if (item.getList().size() > 0) {
				JModuleMenu node = new JModuleMenu("", item.getCaption(), item
						.getIsshow(), item.getTargetForm(), null);
				parentNode.add(node);
				createNewChilderMenu(item.getList(), node);
			} else {
				JModuleMenuItem node = new JModuleMenuItem("", item
						.getCaption(), item.getIsshow(), item.getTargetForm(),
						null);
				parentNode.add(node);
			}
		}
	}

	private boolean validateDownLoad(String key) {
		//
		// jdk version 不进行暂不进行检查
		//
		String jdkVersion = System.getProperty("java.version");
		if (jdkVersion.startsWith("1.6")) {
			return true;
		}
		//
		// 获取主机 host,port
		//
		String baseDir = System.getProperty("deployment.user.cachedir");
		//
		// 这里方便测试而已
		//
		if (baseDir == null || baseDir.equals("")) {
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

		System.out.println("=========================== " + path);

		File foder = new File(path);
		if (!foder.exists()) {
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
		return plugMap.containsKey(key);
	}

}
