package com.bestway.common.client.tools;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JRViewer;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.FileResource;
import com.bestway.common.tools.entity.FtpConfig;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class FmReportTool extends JInternalFrameBase {

	private javax.swing.JPanel	jContentPane	= null;
	private JToolBar			jToolBar		= null;
	private ToolsAction			toolsAction		= null;
	private JButton				btnRefresh		= null;
	private JButton				btnClose		= null;
	private JButton				btnDelete		= null;
	private JButton				btnUpload		= null;
	private JButton				btnDownload		= null;
	private JScrollPane			jScrollPane1	= null;
	private JTable				tbPlugin		= null;
	private JTableListModel		tableModel		= null;
	private JButton				btnFtpConfig	= null;
	private JButton				btnDeleteDir	= null;
	private JButton				btnPreview		= null;
	private JPanel				jPanel			= null;

	/**
	 * This is the default constructor
	 */
	public FmReportTool() {
		super();
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
				"toolsAction");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("远程打单客户端工具");
		this.setSize(543, 379);
		this.setContentPane(getJContentPane());
		this.initUIComponents();
	}

	/**
	 * init Data to UI
	 * 
	 */
	private void initUIComponents() {
//		refreshTb();
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnFtpConfig());
			jToolBar.add(getBtnUpload());
			jToolBar.add(getBtnDownload());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnDeleteDir());
			jToolBar.add(getBtnPreview());
			jToolBar.add(getBtnRefresh());
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
			jContentPane.add(getJScrollPane1(), BorderLayout.CENTER);
			jContentPane.add(getJPanel(), BorderLayout.EAST);
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
					refreshTb();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes btnFtpConfig
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFtpConfig() {
		if (btnFtpConfig == null) {
			btnFtpConfig = new JButton();
			btnFtpConfig.setText("FTP 参数设置");
			btnFtpConfig.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Point point = btnFtpConfig.getLocationOnScreen();
					point.setLocation(point.x - 1, point.y + 29);
					DgFtpConfig dg = new DgFtpConfig();
					dg.setScreenPoint(point);
					dg.setVisible(true);
					if (dg.isOk()) {
						refreshTb();
					}
				}
			});
		}
		return btnFtpConfig;
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
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setToolTipText("删除报表");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteFileResource();
				}

			});
		}
		return btnDelete;
	}

	/**
	 * 删除插件
	 * 
	 */
	private void deleteFileResource() {
		List<FileResource> fileResource = (List<FileResource>) tableModel
				.getCurrentRows();
		if (fileResource == null) {
			JOptionPane.showMessageDialog(FmReportTool.this, "请选择要删除的报表：！",
					"提示", 2);
			return;
		}
		if (JOptionPane.showConfirmDialog(this, "是否确定删除报表!", "提示", 0) != 0) {
			return;
		}
		FtpConfig ftpConfig = this.toolsAction.findFtpConfig(new Request(
				CommonVars.getCurrUser()));
		if (ftpConfig == null) {
			JOptionPane.showMessageDialog(FmReportTool.this,
					"请设置您的 Ftp 连接参数!!", "提示", 2);
			return;
		}
		String server = ftpConfig.getServer();
		String userName = ftpConfig.getUserName();
		String password = ftpConfig.getPassword();
		String directory = ftpConfig.getRemoteDir();

		StringBuffer sb = toolsAction.deleteFileResource(server, userName,
				password, fileResource);
		JOptionPane
				.showMessageDialog(FmReportTool.this, sb.toString(), "提示", 2);
		this.refreshTb();
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
			btnUpload.setToolTipText("上传报表");
			btnUpload.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					uploadPlugin();
				}
			});
		}
		return btnUpload;
	};

	//
	// 加入可以浏览报表的 FileChooser
	//
	class MyFileChooser extends JFileChooser {
		private static final long	serialVersionUID	= 1L;

		public MyFileChooser(String currentDirectoryPath) {
			super(currentDirectoryPath);
			try {
				this.setAccessory(new ReportPreviewer(null, this));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		protected JDialog createDialog(Component parent)
				throws HeadlessException {
			JDialog dialog = super.createDialog(parent);
			dialog.setSize(755, 555);
			dialog.setResizable(true);
			dialog.setLocationRelativeTo(FmMain.getInstance());
			return dialog;
		}
	}

	class ReportPreviewer extends JRViewer implements PropertyChangeListener {
		private static final long	serialVersionUID	= 1L;

		public ReportPreviewer(String sourceFileName, JFileChooser fc)
				throws JRException {
			super(sourceFileName, true);
			setPreferredSize(new Dimension(348, 299));
			fc.addPropertyChangeListener(this);
		}

		protected void loadReport(String fileName, boolean isXmlReport)
				throws JRException {
			if (fileName == null) {
				return;
			}
			super.loadReport(fileName, isXmlReport);
		}

		protected void setZooms() {
			super.defaultZoomIndex = 0;
		}

		public void loadReportss(File f) {
			if (f != null && f.isFile()) {
				try {
					this.loadReport(f.getAbsolutePath(), false);
					this.refreshPage();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "不是有效的报表文件", "提示", 2);
				}
			}
		}

		public void propertyChange(PropertyChangeEvent e) {
			String prop = e.getPropertyName();
			if (prop == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
				if (isShowing()) {
					loadReportss((File) e.getNewValue());
				}
			}
		}
	}

	// 调出文件选择框
	private File[] getJarFile() {
		File[] files = null;
		MyFileChooser fileChooser = new MyFileChooser("./");
		// fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("jrprint"));
		// fileChooser.addChoosableFileFilter(new ExampleFileFilter("zip"));
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(true);
		int state = fileChooser.showDialog(FmReportTool.this, "选择报表文件");
		if (state == JFileChooser.APPROVE_OPTION) {
			files = fileChooser.getSelectedFiles();
		}
		return files;
	}

	/**
	 * 上传插件
	 * 
	 */
	private void uploadPlugin() {
		File[] files = getJarFile();
		if (files == null) {
			return;
		}
		FtpConfig ftpConfig = this.toolsAction.findFtpConfig(new Request(
				CommonVars.getCurrUser()));
		if (ftpConfig == null) {
			JOptionPane.showMessageDialog(FmReportTool.this,
					"请设置您的 Ftp 连接参数!!", "提示", 2);
			return;
		}
		new UploadPluginThread(files, ftpConfig).start();

	}

	class UploadPluginThread extends Thread {

		File[]		files		= null;
		FtpConfig	ftpConfig	= null;

		public UploadPluginThread(File[] file, FtpConfig ftpConfig) {
			this.files = file;
			this.ftpConfig = ftpConfig;
		}

		public void run() {
			String server = ftpConfig.getServer();
			String userName = ftpConfig.getUserName();
			String password = ftpConfig.getPassword();
			String directory = ftpConfig.getRemoteDir();
			String prefixReportName = ftpConfig.getPrefixReportName();
			if(prefixReportName == null || "".equals(prefixReportName)){
				JOptionPane.showMessageDialog(FmReportTool.this,
						"FTP 参数设置中报表前缀名不可为空!!", "提示", 2);
				return;
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
				CommonProgress.setMessage(flag, "系统正在上传报表文件，请稍后...");
				List<byte[]> list = new ArrayList<byte[]>();
				List<String> remotes = new ArrayList<String>();

				
				for (File f : files) {
					byte[] bytes = CommonUtils.getBytesByFile(f
							.getAbsolutePath());
					list.add(bytes);
					String fileName = f.getName();
					if(!fileName.startsWith(prefixReportName+"_")){
						fileName = prefixReportName+"_"+fileName;
					}
					remotes.add(directory + "/" + fileName);
				}
				StringBuffer sb = toolsAction.uploadFileResource(server,
						userName, password, remotes, list);
				//
				// 刷新插件
				//
				initTb();
				CommonProgress.closeProgressDialog(flag);
				String info = "上传报表文件任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 \n\n"
						+ sb.toString();
				JOptionPane.showMessageDialog(FmReportTool.this, info, "提示", 2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
//				JOptionPane.showMessageDialog(FmReportTool.this,
//						e.getMessage(), "提示", 2);
				throw new RuntimeException(e);
			} finally {
				btnUpload.setEnabled(true);
			}
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
			btnDownload.setToolTipText("下载报表");
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
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("jrprint"));
		// fileChooser.addChoosableFileFilter(new ExampleFileFilter("sql"));
		String fileName = "";
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		int state = fileChooser.showDialog(FmReportTool.this, "保存报表文件");
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
	 * download 报表
	 * 
	 */
	private void downloadPlugin() {
		FileResource fileResource = (FileResource) tableModel.getCurrentRow();
		if (fileResource == null) {
			JOptionPane.showMessageDialog(FmReportTool.this, "请选择要下载的报表：！",
					"提示", 2);
			return;
		}
		FtpConfig ftpConfig = this.toolsAction.findFtpConfig(new Request(
				CommonVars.getCurrUser()));
		if (ftpConfig == null) {
			JOptionPane.showMessageDialog(FmReportTool.this,
					"请设置您的 Ftp 连接参数!!", "提示", 2);
			return;
		}
		new DownloadPluginThread(fileResource, ftpConfig).start();
	}

	class DownloadPluginThread extends Thread {

		FileResource	fileResource	= null;
		FtpConfig		ftpConfig		= null;

		public DownloadPluginThread(FileResource fileResource,
				FtpConfig ftpConfig) {
			this.fileResource = fileResource;
			this.ftpConfig = ftpConfig;
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
				CommonProgress.setMessage(flag, "系统正在下载报表，请稍后...");

				String server = ftpConfig.getServer();
				String userName = ftpConfig.getUserName();
				String password = ftpConfig.getPassword();
				String directory = ftpConfig.getRemoteDir();

				byte[] bytes = toolsAction.downloadFileResource(server,
						userName, password, fileResource);
				CommonProgress.closeProgressDialog(flag);

				String filePath = getSaveHsqlFileName();
				if (filePath == null || "".equals(filePath)) {
					btnDownload.setEnabled(true);
					return;
				}
				CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
						false, null, 0);
				CommonProgress.setMessage(flag, "系统正在保存报表，请稍后...");
				CommonUtils.saveFileByBytes(filePath, bytes);
				CommonProgress.closeProgressDialog(flag);

				String info = "下载报表任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(FmReportTool.this, info, "提示", 2);

			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
//				JOptionPane.showMessageDialog(FmReportTool.this,
//						e.getMessage(), "提示", 2);
				throw new RuntimeException(e);
			} finally {
				btnDownload.setEnabled(true);
			}
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
		}
		return tbPlugin;
	}

	class RefreshTable extends Thread {

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
						false, null, 0);
				CommonProgress.setMessage(flag, "系统正在刷新报表程序，请稍后...");
				initTb();
				CommonProgress.closeProgressDialog(flag);
			} catch (Exception ex) {
//				JOptionPane.showMessageDialog(FmReportTool.this,
//						ex.getMessage(), "提示", 2);
				throw new RuntimeException(ex);
			} finally {
				CommonProgress.closeProgressDialog(flag);
			}
		}
	}

	/**
	 * 初始化数据Table
	 */
	private void refreshTb() {
		new RefreshTable().start();
	}

	/**
	 * 初始化数据Table
	 */
	private void initTb() {
		List<FileResource> list = new ArrayList<FileResource>();
		FtpConfig ftpConfig = this.toolsAction.findFtpConfig(new Request(
				CommonVars.getCurrUser()));
		if (ftpConfig != null) {
			String server = ftpConfig.getServer();
			String userName = ftpConfig.getUserName();
			String password = ftpConfig.getPassword();
			String directory = ftpConfig.getRemoteDir();
			list = this.toolsAction.getResourceList(server, userName, password,
					directory);
		}
		tableModel = new JTableListModel(this.tbPlugin, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("文件名", "fileName", 180));
						list.add(addColumn("Ftp文件路径", "filePath", 210));
						list.add(addColumn("最后修改时间", "lastModifeTime", 150));
						list.add(addColumn("文件大小(KB)", "kb", 150));
						return list;
					}
				});
	}

	/**
	 * This method initializes btnDeleteDir
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteDir() {
		if (btnDeleteDir == null) {
			btnDeleteDir = new JButton();
			btnDeleteDir.setText("删除所有文件");
			btnDeleteDir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteAllFileResource();
				}
			});
		}
		return btnDeleteDir;
	}

	/**
	 * 删除插件
	 * 
	 */
	private void deleteAllFileResource() {
		List<FileResource> fileResource = (List<FileResource>) tableModel
				.getList();
		if (fileResource.size() <= 0) {
			return;
		}
		if (JOptionPane.showConfirmDialog(this, "是否确定删除所有报表!", "提示", 0) != 0) {
			return;
		}
		FtpConfig ftpConfig = this.toolsAction.findFtpConfig(new Request(
				CommonVars.getCurrUser()));
		if (ftpConfig == null) {
			JOptionPane.showMessageDialog(FmReportTool.this,
					"请设置您的 Ftp 连接参数!!", "提示", 2);
			return;
		}
		String server = ftpConfig.getServer();
		String userName = ftpConfig.getUserName();
		String password = ftpConfig.getPassword();
		String directory = ftpConfig.getRemoteDir();

		StringBuffer sb = toolsAction.deleteAllFileResource(server, userName,
				password, directory);
		JOptionPane
				.showMessageDialog(FmReportTool.this, sb.toString(), "提示", 2);
		this.refreshTb();
	}

	/**
	 * This method initializes btnPreview
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPreview() {
		if (btnPreview == null) {
			btnPreview = new JButton();
			btnPreview.setText("报表预览");
			btnPreview.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					reportPreview();
				}
			});
		}
		return btnPreview;
	}

	/**
	 * download 报表
	 * 
	 */
	private void reportPreview() {
		FileResource fileResource = (FileResource) tableModel.getCurrentRow();
		if (fileResource == null) {
			JOptionPane.showMessageDialog(FmReportTool.this, "请选择要预览的报表：！",
					"提示", 2);
			return;
		}
		FtpConfig ftpConfig = this.toolsAction.findFtpConfig(new Request(
				CommonVars.getCurrUser()));
		if (ftpConfig == null) {
			JOptionPane.showMessageDialog(FmReportTool.this,
					"请设置您的 Ftp 连接参数!!", "提示", 2);
			return;
		}
		new ReportPreviewThread(fileResource, ftpConfig).start();
	}

	class ReportPreviewThread extends Thread {

		FileResource	fileResource	= null;
		FtpConfig		ftpConfig		= null;

		public ReportPreviewThread(FileResource fileResource,
				FtpConfig ftpConfig) {
			this.fileResource = fileResource;
			this.ftpConfig = ftpConfig;
		}

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			ByteArrayInputStream input = null;
			btnPreview.setEnabled(false);
			try {
				CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
						false, null, 0);
				CommonProgress.setMessage(flag, "系统正在预览报表，请稍后...");

				String server = ftpConfig.getServer();
				String userName = ftpConfig.getUserName();
				String password = ftpConfig.getPassword();
				String directory = ftpConfig.getRemoteDir();

				byte[] bytes = toolsAction.downloadFileResource(server,
						userName, password, fileResource);
				input = new ByteArrayInputStream(bytes);
				DgReportViewer.viewReport(input, false, false);
				CommonProgress.closeProgressDialog(flag);

			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				throw new RuntimeException(e);
			} finally {
				btnPreview.setEnabled(true);
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
		}
		return jPanel;
	}

}
