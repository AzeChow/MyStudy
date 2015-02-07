package com.bestway.common.client.tools;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.tools.action.CheckToolsAuthorityAction;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.PluginInfo;
import com.bestway.common.tools.entity.PluginInfoClient;
import com.bestway.common.tools.entity.PluginInfoClientReport;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class FmPlugin extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private ToolsAction toolsAction = null;

	private JButton btnClose = null;

	private JButton btnUpload = null;

	private JTableListModel tableModel = null;

	private CheckToolsAuthorityAction checkToolsAuthorityAction = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnWindowPlugin = null;

	private JPanel pnMenuPlugin = null;

	private JPanel pnSeverPlugin = null;
	private static final String PLUGIN_CONFIG_FILE = "plugin.xml"; // @jve:decl-index=0:
	private static final int SERVER_PLUGIN = 0;
	private static final int MENU_PLUGIN = 1;
	private static final int WINDOW_PLUGIN = 2;
	private FmPluginClientReportTool fmWindowPlugin = new FmPluginClientReportTool();
	private FmPluginClientTool fmMenuPlugin = new FmPluginClientTool();
	private FmPluginTool fmSeverPlugin = new FmPluginTool();
	private String pluginId = null; // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public FmPlugin() {
		super();
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
				"toolsAction");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initialize();
		checkToolsAuthorityAction = (CheckToolsAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkToolsAuthorityAction");
		checkToolsAuthorityAction.checkGroovyServiceAuthority(new Request(
				CommonVars.getCurrUser()));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("插件工具");
		this.setSize(650, 553);
		this.setContentPane(getJContentPane());
		this.initUIComponents();
	}

	/**
	 * init Data to UI
	 * 
	 */
	private void initUIComponents() {
		// initTbPlugin();
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnUpload());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	class ExampleFileFilter extends FileFilter {
		String suffix = "";

		ExampleFileFilter(String suffix) {
			this.suffix = suffix;
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (suffix.toLowerCase().equals(this.suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String getDescription() {
			return "*." + this.suffix;
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * 删除插件
	 * 
	 */
	private void deletePlugin() {
		PluginInfo pluginInfo = (PluginInfo) tableModel.getCurrentRow();
		if (pluginInfo == null) {
			JOptionPane
					.showMessageDialog(FmPlugin.this, "请选择要删除的插件：！", "提示", 2);
			return;
		}
		if (JOptionPane.showConfirmDialog(this, "是否确定删除插件!", "提示", 0) != 0) {
			return;
		}
		boolean isSuccess = toolsAction.deletePlugin(new Request(CommonVars
				.getCurrUser()), pluginInfo);
		if (isSuccess) {
			this.tableModel.deleteRow(pluginInfo);
		}
	}

	/**
	 * This method initializes btnUpload
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpload() {
		if (btnUpload == null) {
			btnUpload = new JButton();
			btnUpload.setText("上传插件");
			btnUpload.setToolTipText("上传插件");
			btnUpload.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					uploadPlugin();
				}
			});
		}
		return btnUpload;
	};

	// 调出文件选择框
	private File getJarFile() {
		File txtFile = null;
		JFileChooser fileChooser = new JFileChooser("./");
		// fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("jar"));
		// fileChooser.addChoosableFileFilter(new ExampleFileFilter("zip"));
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		int state = fileChooser.showDialog(FmPlugin.this, "选择插件文件");
		if (state == JFileChooser.APPROVE_OPTION) {
			txtFile = fileChooser.getSelectedFile();
		}
		return txtFile;
	}

	/**
	 * 上传插件
	 * 
	 */
	private void uploadPlugin() {
		File file = getJarFile();
		if (file == null) {
			return;
		}
		new UploadPluginThread(file).start();

	}

	class UploadPluginThread extends Thread {

		File file = null;

		public UploadPluginThread(File file) {
			this.file = file;
		}

		public void run() {

			int type = analysePlugin(file);
			Object obj = getExistPluginById(pluginId, type);

			if (obj != null) {
				if (type == FmPlugin.WINDOW_PLUGIN) {
					PluginInfoClientReport plugin = (PluginInfoClientReport) obj;
					if (JOptionPane
							.showConfirmDialog(FmPlugin.this, "已经存在相同的插件文件 ["
									+ plugin.getFileName() + "],是否覆盖!!", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					//
					// 删除
					//
					toolsAction.deleteReportPlugin(new Request(CommonVars
							.getCurrUser()), plugin);
				} else if (type == FmPlugin.MENU_PLUGIN) {
					PluginInfoClient plugin = (PluginInfoClient) obj;
					if (JOptionPane
							.showConfirmDialog(FmPlugin.this, "已经存在相同的插件文件 ["
									+ plugin.getFileName() + "],是否覆盖!!", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					//
					// 删除
					//
					toolsAction.deleteClientPlugin(new Request(CommonVars
							.getCurrUser()), plugin);
				} else if (type == FmPlugin.SERVER_PLUGIN) {
					PluginInfo plugin = (PluginInfo) obj;
					if (JOptionPane
							.showConfirmDialog(FmPlugin.this, "已经存在相同的插件文件 ["
									+ plugin.getFileName() + "],是否覆盖!!", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					//
					// 删除
					//
					toolsAction.deletePlugin(new Request(CommonVars
							.getCurrUser()), plugin);
				}

			}

			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			btnUpload.setEnabled(false);
			try {
				long beginTime = System.currentTimeMillis();
				CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
						false, null, 0);
				CommonProgress.setMessage(flag, "系统正在上传插件，请稍后...");

				byte[] bytes = CommonUtils.getBytesByFile(file
						.getAbsolutePath());
				if (type == FmPlugin.WINDOW_PLUGIN) {
					toolsAction.uploadReportPlugin(new Request(CommonVars
							.getCurrUser()), file.getName(), bytes);
				} else if (type == FmPlugin.MENU_PLUGIN) {
					toolsAction.uploadClientPlugin(new Request(CommonVars
							.getCurrUser()), file.getName(), bytes);
				} else if (type == FmPlugin.SERVER_PLUGIN) {
					toolsAction.uploadPlugin(new Request(CommonVars
							.getCurrUser()), file.getName(), bytes);
				}
				//
				// 刷新插件
				//
				showPlugin(type);
				CommonProgress.closeProgressDialog(flag);
				String info = "上传插件任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(FmPlugin.this, info, "提示", 2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmPlugin.this, "上传插件失败：！"
						+ e.getMessage(), "提示", 2);
			}
			btnUpload.setEnabled(true);
		}
	}

	private Object getExistPluginById(String id, int type) {
		Object plugin = null;
		if (type == FmPlugin.WINDOW_PLUGIN) {
			List<PluginInfoClientReport> list = this.toolsAction
					.loadReportPlugin(new Request(CommonVars.getCurrUser()));

			for (PluginInfoClientReport tempPlugin : list) {
				String tempId = tempPlugin.getId() == null ? "" : tempPlugin
						.getId();
				if (tempId.equals(id)) {
					plugin = tempPlugin;
					break;
				}
			}
		} else if (type == FmPlugin.MENU_PLUGIN) {
			List<PluginInfoClient> list = this.toolsAction
					.loadClientPlugin(new Request(CommonVars.getCurrUser()));
			for (PluginInfoClient tempPlugin : list) {
				String tempId = tempPlugin.getId() == null ? "" : tempPlugin
						.getId();
				if (tempId.equals(id)) {
					plugin = tempPlugin;
					break;
				}
			}
		} else if (type == FmPlugin.SERVER_PLUGIN) {
			List<PluginInfo> list = this.toolsAction.loadPlugin(new Request(
					CommonVars.getCurrUser()));
			for (PluginInfo tempPlugin : list) {
				String tempId = tempPlugin.getId() == null ? "" : tempPlugin
						.getId();
				if (tempId.equals(id)) {
					plugin = tempPlugin;
					break;
				}
			}
		}
		return plugin;
	}

	private void showPlugin(int type) {
		if (type == FmPlugin.WINDOW_PLUGIN) {
			fmWindowPlugin.initTbPlugin();
			jTabbedPane.setSelectedIndex(0);
		} else if (type == FmPlugin.MENU_PLUGIN) {
			fmMenuPlugin.initTbPlugin();
			jTabbedPane.setSelectedIndex(1);
		} else if (type == FmPlugin.SERVER_PLUGIN) {
			fmSeverPlugin.initTbPlugin();
			jTabbedPane.setSelectedIndex(2);
		}
	}

	/**
	 * 分析插件
	 * 
	 * @param f
	 */
	private int analysePlugin(File f) {
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
					doc = sb.build(new BufferedReader(new InputStreamReader(
							jarInputStream, "UTF-8")));
					break;
				}
				jarInputStream.closeEntry();
			}
			jarInputStream.close();

			if (doc == null) {
				String message = f.getName() + " 文件中 " + PLUGIN_CONFIG_FILE
						+ " 不存在 !!";
				throw new RuntimeException(message);
			}

			Element root = doc.getRootElement();
			String id = "";
			List elementId = root.getChildren("id");
			if (elementId != null && elementId.size() > 0) {
				id = ((Element) elementId.get(0)).getTextTrim();
			}
			if (id == null || "".equals(id)) {
				String message = f.getName() + " 文件中 " + PLUGIN_CONFIG_FILE
						+ " 不存在 ID 元素,请先设置插件 ID 唯一值";
				throw new RuntimeException(message);
			}
			//
			// set
			//
			pluginId = id;

			List<Element> elementTragetClassName = root
					.getChildren("tragetClassName");
			//
			// WINDOW_PLUGIN
			//
			if (elementTragetClassName != null
					&& elementTragetClassName.size() > 0) {
				return FmPlugin.WINDOW_PLUGIN;
			}
			//
			// MENU_PLUGIN
			//
			List list = root.getChildren("link");
			if (list != null && list.size() >= 0) {
				return FmPlugin.MENU_PLUGIN;
			}

			List elementReplace = root.getChildren("replace");
			List elementReplacePath = root.getChildren("replaceFormPath");
			List replaceForm = root.getChildren("replaceForm");
			List<Element> replaceList = root.getChildren("replaceList");
			if (elementReplace != null && elementReplace.size() > 0) {
				return FmPlugin.MENU_PLUGIN;
			}
			if (elementReplacePath != null && elementReplacePath.size() > 0) {
				return FmPlugin.MENU_PLUGIN;
			}
			if (elementReplacePath != null && elementReplacePath.size() > 0) {
				return FmPlugin.MENU_PLUGIN;
			}
			if (replaceForm != null && replaceForm.size() > 0) {
				return FmPlugin.MENU_PLUGIN;
			}
			//
			// other SERVER_PLUGIN
			//
			return FmPlugin.SERVER_PLUGIN;

		} catch (Exception ex) {
			ex.printStackTrace();
			pluginId = null;
			throw new RuntimeException(ex);
		} finally {
			if (jarInputStream != null) {
				try {
					jarInputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			// jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			jTabbedPane.addTab("客户端报表插件", null, getPnWindowPlugin(), "窗口插件");
			jTabbedPane.addTab("客户端插件", null, getPnMenuPlugin(), "模块插件");
			jTabbedPane.addTab("服务端插件", null, getPnSeverPlugin(), "服务端插件");
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes pnWindowPlugin
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnWindowPlugin() {
		if (pnWindowPlugin == null) {
			pnWindowPlugin = (JPanel) fmWindowPlugin.getContentPane();
			// pnWindowPlugin = new JPanel();
			// pnWindowPlugin.setLayout(new BorderLayout());
		}
		return pnWindowPlugin;
	}

	/**
	 * This method initializes pnMenuPlugin
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMenuPlugin() {
		if (pnMenuPlugin == null) {
			pnMenuPlugin = (JPanel) fmMenuPlugin.getContentPane();
			// pnMenuPlugin = new JPanel();
			// pnMenuPlugin.setLayout(new BorderLayout());
		}
		return pnMenuPlugin;
	}

	/**
	 * This method initializes pnSeverPlugin
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnSeverPlugin() {
		if (pnSeverPlugin == null) {
			pnSeverPlugin = (JPanel) fmSeverPlugin.getContentPane();
			// pnSeverPlugin = new JPanel();
			// pnSeverPlugin.setLayout(new BorderLayout());
		}
		return pnSeverPlugin;
	}
}
