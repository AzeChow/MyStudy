package com.bestway.common.client.tools;

import java.awt.BorderLayout;
import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import com.bestway.bcus.client.cas.otherreport.FmBalanceInfoTip;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.tools.action.CheckToolsAuthorityAction;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.PluginInfoClient;
import com.bestway.ui.editor.HSQLTextPane;
import com.bestway.ui.winuicontrol.JFrameBase;
import com.bestway.ui.winuicontrol.JFrameBaseHelper;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class FmPluginClientTool extends JInternalFrameBase {

	private javax.swing.JPanel			jContentPane				= null;

	private JToolBar					jToolBar					= null;

	private JSplitPane					jSplitPane1					= null;

	private JPanel						jPanel1						= null;

	private ToolsAction					toolsAction					= null;

	private JButton						btnRefresh					= null;

	private JButton						btnClose					= null;

	private JTabbedPane					jTabbedPane					= null;

	private JScrollPane					jScrollPane					= null;

	private HSQLTextPane				logTextPane					= null;

	private JButton						btnDelete					= null;

	private JButton						btnUpload					= null;

	private JButton						btnDownload					= null;

	private JScrollPane					jScrollPane1				= null;

	private JTable						tbPlugin					= null;

	private JTableListModel				tableModel					= null;

	private CheckToolsAuthorityAction	checkToolsAuthorityAction	= null;

	private JButton						btnDeleteAll				= null;
	private JButton btnHelp = null;

	/**
	 * This is the default constructor
	 */
	public FmPluginClientTool() {
		super();
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
				"toolsAction");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initialize();
		checkToolsAuthorityAction = (CheckToolsAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkToolsAuthorityAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("客户端插件工具");
		this.setSize(543, 379);
		this.setContentPane(getJContentPane());
		this.initUIComponents();
	}

	/**
	 * init Data to UI
	 * 
	 */
	private void initUIComponents() {
		initTbPlugin();
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
			jToolBar.add(getBtnDownload());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnDeleteAll());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnHelp());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setOneTouchExpandable(true);
			jSplitPane1.setDividerLocation(350);
			jSplitPane1.setTopComponent(getJScrollPane1());
			jSplitPane1.setBottomComponent(getJPanel1());
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
			jContentPane.add(getJSplitPane1(), BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTbPlugin();
				}
			});
		}
		return btnRefresh;
	}

	class ExampleFileFilter extends FileFilter {
		String	suffix	= "";

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
			btnClose.setVisible(false);
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
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("插件描述", null, getJScrollPane(), "插件描述");
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getLogTextPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes logTextPane
	 * 
	 * @return com.bestway.ui.editor.HSQLTextPane
	 */
	private HSQLTextPane getLogTextPane() {
		if (logTextPane == null) {
			logTextPane = new HSQLTextPane();
		}
		return logTextPane;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setToolTipText("删除插件");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deletePlugin();
				}

			});
		}
		return btnDelete;
	}

	/**
	 * 删除插件
	 * 
	 */
	private void deletePlugin() {
		PluginInfoClient pluginInfoClient = (PluginInfoClient) tableModel
				.getCurrentRow();
		if (pluginInfoClient == null) {
			JOptionPane.showMessageDialog(FmPluginClientTool.this,
					"请选择要删除的插件：！", "提示", 2);
			return;
		}
		if (JOptionPane.showConfirmDialog(this, "是否确定删除插件!", "提示", 0) != 0) {
			return;
		}
		boolean isSuccess = toolsAction.deleteClientPlugin(new Request(
				CommonVars.getCurrUser()), pluginInfoClient);
		if (isSuccess) {
			this.tableModel.deleteRow(pluginInfoClient);
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
			btnUpload.setText("上传");
			btnUpload.setVisible(false);
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
		int state = fileChooser.showDialog(FmPluginClientTool.this, "选择插件文件");
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

		File	file	= null;

		public UploadPluginThread(File file) {
			this.file = file;
		}

		public void run() {
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
				toolsAction.uploadClientPlugin(new Request(CommonVars.getCurrUser()),
						file.getName(), bytes);
				//
				// 刷新插件
				//
				initTbPlugin();
				CommonProgress.closeProgressDialog(flag);
				String info = "上传插件任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(FmPluginClientTool.this, info,
						"提示", 2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmPluginClientTool.this,
						"上传插件失败：！" + e.getMessage(), "提示", 2);
			}
			btnUpload.setEnabled(true);
		}
	}

	/**
	 * This method initializes btnDownload
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDownload() {
		if (btnDownload == null) {
			btnDownload = new JButton();
			btnDownload.setText("下载");
			btnDownload.setToolTipText("下载插件");
			btnDownload.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					downloadPlugin();
				}
			});
		}
		return btnDownload;
	}

	/**
	 * 获得保存的文件名
	 * 
	 * @return
	 */
	private String getSaveHsqlFileName() {
		JFileChooser fileChooser = new JFileChooser("./");
		// fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("jar"));
		// fileChooser.addChoosableFileFilter(new ExampleFileFilter("sql"));
		String fileName = "";
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		int state = fileChooser.showDialog(FmPluginClientTool.this, "保存插件文件");
		if (state == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			String description = fileChooser.getFileFilter().getDescription();
			if (f.getPath().indexOf(".") > 0 || description.indexOf(".") == -1) {
				fileName = f.getPath();
			} else {
				String suffix = description.substring(description.indexOf("."));
				fileName = f.getPath() + suffix;
			}
		} else {
			return null;
		}
		return fileName;
	}

	/**
	 * download 插件
	 * 
	 */
	private void downloadPlugin() {
		PluginInfoClient pluginInfoClient = (PluginInfoClient) tableModel
				.getCurrentRow();
		if (pluginInfoClient == null) {
			JOptionPane.showMessageDialog(FmPluginClientTool.this,
					"请选择要下载的插件：！", "提示", 2);
		}
		new DownloadPluginThread(pluginInfoClient).start();
	}

	class DownloadPluginThread extends Thread {

		PluginInfoClient	pluginInfoClient	= null;

		public DownloadPluginThread(PluginInfoClient pluginInfoClient) {
			this.pluginInfoClient = pluginInfoClient;
		}

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			btnDownload.setEnabled(false);
			try {
				long beginTime = System.currentTimeMillis();
				CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
						false, null, 0);
				CommonProgress.setMessage(flag, "系统正在下载插件，请稍后...");

				byte[] bytes = toolsAction.downloadClientPlugin(new Request(
						CommonVars.getCurrUser()), pluginInfoClient);
				CommonProgress.closeProgressDialog(flag);

				String filePath = getSaveHsqlFileName();
				if (filePath == null || "".equals(filePath)) {
					btnDownload.setEnabled(true);
					return;
				}
				CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
						false, null, 0);
				CommonProgress.setMessage(flag, "系统正在保存插件，请稍后...");
				CommonUtils.saveFileByBytes(filePath, bytes);
				CommonProgress.closeProgressDialog(flag);

				String info = "下载插件任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(FmPluginClientTool.this, info,
						"提示", 2);

			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmPluginClientTool.this,
						"下载插件失败：！" + e.getMessage(), "提示", 2);
			}
			btnDownload.setEnabled(true);
		}
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbPlugin());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbPlugin
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbPlugin() {
		if (tbPlugin == null) {
			tbPlugin = new JTable();
			tbPlugin.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbPlugin
									.getModel();
							if (tableModel == null) {
								return;
							}

							PluginInfoClient pluginInfoClient = (PluginInfoClient) tableModel
									.getCurrentRow();
							if (pluginInfoClient != null) {
								String description = pluginInfoClient
										.getDescription();
								logTextPane.setText("插件详细描述:\n" + description);
							}
						}
					});
		}
		return tbPlugin;
	}

	/**
	 * 初始化数据Table
	 */
	public void initTbPlugin() {
		List<PluginInfoClient> list = this.toolsAction
				.loadClientPlugin(new Request(CommonVars.getCurrUser()));
		tableModel = new JTableListModel(this.tbPlugin, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("插件中文名", "cnName", 255));
						list.add(addColumn("文件名", "fileName", 210));
						list.add(addColumn("最后修改时间", "modifiyTime", 150));
						list.add(addColumn("插件位置", "parentPath", 255));
						return list;
					}
				});
	}

	/**
	 * This method initializes btnDeleteAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteAll() {
		if (btnDeleteAll == null) {
			btnDeleteAll = new JButton();
			btnDeleteAll.setText("删除所有客户端插件");
			btnDeleteAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteAllPlugin();
				}
			});
		}
		return btnDeleteAll;
	}

	/**
	 * 删除插件
	 * 
	 */
	private void deleteAllPlugin() {
		if (JOptionPane.showConfirmDialog(this, "是否确定删除所有插件!", "提示", 0) != 0) {
			return;
		}
		new DeleteAllPluginThread().start();
	}

	class DeleteAllPluginThread extends Thread {
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			uuid = UUID.randomUUID();
			final String flag2 = uuid.toString();
			btnDeleteAll.setEnabled(false);
			try {
				CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
						false, null, 0);
				CommonProgress.setMessage(flag, "系统正在删除所有插件，请稍后...");

				toolsAction.deleteAllClientPlugin(new Request(CommonVars
						.getCurrUser()));
				CommonProgress.closeProgressDialog(flag);

				
				CommonProgress.showProgressDialog(flag2, FmMain.getInstance(),
						false, null, 0);
				CommonProgress.setMessage(flag2, "系统正在重新载入插件，请稍后...");
				initTbPlugin();
				CommonProgress.closeProgressDialog(flag2);
				JOptionPane.showMessageDialog(FmPluginClientTool.this,
						"删除插件任务完成", "提示", 2);

			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				CommonProgress.closeProgressDialog(flag2);
				JOptionPane.showMessageDialog(FmPluginClientTool.this,
						"删除插件任务失败：！" + e.getMessage(), "提示", 2);
			}
			btnDeleteAll.setEnabled(true);
		}
	}
	
	
	/**
	 * This method initializes btnHelp
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHelp() {
		if (btnHelp == null) {
			btnHelp = new JButton();
			btnHelp.setVisible(false);
			btnHelp.setText("帮助");
			UUID uuid = UUID.randomUUID();
			btnHelp.setActionCommand(uuid.toString());
			btnHelp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						String flag = btnHelp.getActionCommand();
						JFrameBase fm = JFrameBaseHelper
								.getJFrameBaseByFlag(flag);
						if (fm == null) {
							fm = new FmBalanceInfoTip(CommonVariables
									.readFileByClientPluginTipInfo().toString(),
									"客户端插件帮助文档");
							JFrameBaseHelper.putJDialogBaseToFlag(flag, fm);
							fm.setVisible(true);
						} else {
							fm.setVisibleNoChange(true);
							JFrameBaseHelper.deiconify(fm);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return btnHelp;
	}

}
