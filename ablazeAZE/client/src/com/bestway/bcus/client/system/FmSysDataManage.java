/*
 * Created on 2004-8-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.system;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.backup.action.BackupAction;
import com.bestway.bcus.backup.entity.BackupFileInfo;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author lin
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmSysDataManage extends JInternalFrameBase {
	// private static final Log logger = LogFactory.getLog(FmImportDemo.class);
	private Log log = LogFactory.getLog(FmSysDataManage.class);

	private javax.swing.JPanel jContentPane = null; // @jve:decl-index=0:visual-constraint="191,91"

	private BackupAction backupAction = null;

	private JTableListModel backupFileInfoTableModel = null;

	private JTableListModel selectedBackupFileInfoTableModel = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnBackupAllData = null;

	private JPanel pnBackupPartData = null;

	private JToolBar jToolBar = null;

	private JTable tbBackupFileInfo = null;

	private JScrollPane jScrollPane = null;

	private JButton btnAdd = null;

	private JButton btnDelete = null;

	private JButton btnRestoreData = null;

	private JButton btnUploadBackupFile = null;

	private JButton btnDownloadBackupFile = null;

	private JToolBar jToolBar1 = null;

	private JButton btnSelectedAdd = null;

	private JTable tbSelectedBackupFileInfo = null;

	private JScrollPane jScrollPane1 = null;

	private JButton btnSelectedDelete = null;

	private JButton btnSelectedRestoreData = null;

	private JButton btnSelectedUploadBackupFile = null;

	private JButton btnSelectedDownloadBackupFile = null;

	/**
	 * This is the default constructor
	 */
	public FmSysDataManage() {
		super();
		initialize();
		backupAction = (BackupAction) CommonVars.getApplicationContext()
				.getBean("backupAction");
		showBackupFileInfo();

	}

	private void showBackupFileInfo() {
		List list = backupAction.getBackupFilesInfo(new Request(CommonVars
				.getCurrUser()));
		backupFileInfoTableModel = new JTableListModel(tbBackupFileInfo, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("备份文件名称", "fileName", 250));
						list.add(addColumn("文件大小", "fileSize", 100));
						list.add(addColumn("备份日期", "backupDate", 200));
						list.add(addColumn("备份人员", "creater", 100));
						list.add(addColumn("备份资料所属公司", "company.buName", 150));
						return list;
					}
				});
	}

	private void showSelectedBackupFileInfo() {
		List list = backupAction.getBackupFilesInfo(new Request(CommonVars
				.getCurrUser()), false);
		selectedBackupFileInfoTableModel = new JTableListModel(
				tbSelectedBackupFileInfo, list, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("备份项目名称", "backupItemInfo", 250));
						list.add(addColumn("备份文件名称", "fileName", 200));
						list.add(addColumn("文件大小", "fileSize", 100));
						list.add(addColumn("备份日期", "backupDate", 200));
						list.add(addColumn("备份人员", "creater", 100));
						list.add(addColumn("备份资料所属公司", "company.buName", 150));

						return list;
					}
				});
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("系统数据管理");
		this.setSize(605, 482);
		this.setHelpId("sysDataManage");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			this.setSize(600, 443);
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			throw new IOException("File " + file.getName() + " size " + length
					+ " is too big!");
		}

		byte[] bytes = new byte[(int) length];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		is.close();
		return bytes;
	}

	class BackupFileFilter extends FileFilter {
		private List list = new ArrayList();

		BackupFileFilter(String suffix) {
			this.addExtension(suffix);
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (isAcceptSuffix(suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String getDescription() {
			String description = "*.";
			for (int i = 0; i < list.size(); i++) {
				description += list.get(i).toString() + " & *.";
			}
			return description.substring(0, description.length() - 5);
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}

		private boolean isAcceptSuffix(String suffix) {
			boolean isAccept = false;
			for (int i = 0; i < list.size(); i++) {
				if (suffix.equals(list.get(i).toString())) {
					isAccept = true;
					break;
				}
			}
			return isAccept;
		}

		public void addExtension(String extensionName) {
			if (extensionName.equals("")) {
				return;
			}
			list.add(extensionName.toLowerCase().trim());
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
			jTabbedPane.addTab("备份和恢复全部数据", null, getPnBackupAllData(), null);
			jTabbedPane.addTab("选择性备份和恢复数据", null, getPnBackupPartData(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {

						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {

							} else if (jTabbedPane.getSelectedIndex() == 1) {
								showSelectedBackupFileInfo();
							}
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBackupAllData() {
		if (pnBackupAllData == null) {
			pnBackupAllData = new JPanel();
			pnBackupAllData.setLayout(new BorderLayout());
			pnBackupAllData.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			pnBackupAllData.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return pnBackupAllData;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBackupPartData() {
		if (pnBackupPartData == null) {
			pnBackupPartData = new JPanel();
			pnBackupPartData.setLayout(new BorderLayout());
			pnBackupPartData.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			pnBackupPartData.add(getJScrollPane1(),
					java.awt.BorderLayout.CENTER);
		}
		return pnBackupPartData;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnRestoreData());
			jToolBar.add(getBtnDownloadBackupFile());
			jToolBar.add(getBtnUploadBackupFile());
		}
		return jToolBar;
	}

	/**
	 * This method initializes tbSelectedBackupFileInfo
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBackupFileInfo() {
		if (tbBackupFileInfo == null) {
			tbBackupFileInfo = new JTable();
		}
		return tbBackupFileInfo;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbBackupFileInfo());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes btnSelectedAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增备份");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					AddBackupDataRunnable thread = new AddBackupDataRunnable();
					thread.setIsAll(true);
					thread.start();
				}
			});
		}
		return btnAdd;
	}

	class AddBackupDataRunnable extends Thread {
		private List lsSelectedClasses = null;

		private List backupItem = null;

		private boolean isAll = true;

		public void setLsSelectedClasses(List lsSelectedClasses) {
			this.lsSelectedClasses = lsSelectedClasses;
		}

		public void setBackupItem(List backupItem) {
			this.backupItem = backupItem;
		}

		public void setIsAll(boolean isAll) {
			this.isAll = isAll;
		}

		public void run() {
			// JFileChooser jfc = new JFileChooser();
			// jfc.removeChoosableFileFilter(jfc.getAcceptAllFileFilter());
			// jfc.addChoosableFileFilter(new BackupFileFilter("jbcus"));
			btnAdd.setEnabled(false);
			String zipFileName = (new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss"))
					.format(new Date());
			if (this.isAll) {
				zipFileName += "全部数据备份";
			} else {
				zipFileName += "部分数据备份";
			}
			// zipFileName += ".jbcus";
			// jfc.setSelectedFile(new File(zipFileName));
			DgBackupFile dg = new DgBackupFile();
			dg.setFileName(zipFileName);
			dg.setVisible(true);
			// if (jfc.showSaveDialog(FmSysDataManage.this) ==
			// JFileChooser.APPROVE_OPTION) {
			if (dg.isOk() && !"".equals(dg.getFileName().trim())) {
				String taskId = CommonProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正在新增备份，请稍后");
				BackupFileInfo backupFileInfo = new BackupFileInfo();
				String fileName = "";
				try {
					fileName = dg.getFileName().trim() + ".jbcus";
					int lastIndex = fileName.lastIndexOf(".");
					if (lastIndex > 0) {
						if (!fileName.substring(lastIndex, fileName.length())
								.equals("jbcus")) {
							fileName = fileName.substring(0, lastIndex + 1)
									+ "jbcus";
						}
					} else {
						fileName = fileName + ".jbcus";
					}
					Request request = new Request(CommonVars.getCurrUser());
					request.setTaskId(taskId);
					backupFileInfo.setCompany((Company) CommonVars
							.getCurrUser().getCompany());
					backupFileInfo.setFileName(fileName);
					if (this.isAll == true) {
						backupFileInfo.setIsBackupAll(new Boolean(true));
						backupFileInfo = backupAction.backupToFile(request,
								backupFileInfo, fileName, taskId);
						backupFileInfoTableModel.addRow(backupFileInfo);
					} else {
						backupFileInfo.setIsBackupAll(new Boolean(false));
						backupFileInfo.setBackupItem(backupItem);
						backupFileInfo = backupAction.backupToFile(request,
								backupFileInfo, lsSelectedClasses, fileName,
								taskId);
						selectedBackupFileInfoTableModel.addRow(backupFileInfo);
					}
				} catch (Exception e2) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmSysDataManage.this,
							"新增备份失败！");
					if (backupFileInfo == null) {
						backupFileInfo = new BackupFileInfo();
						backupFileInfo.setFileName(fileName);
					}
					backupAction.removeBakcupFile(new Request(CommonVars
							.getCurrUser()), backupFileInfo);
					backupFileInfoTableModel.deleteRow(backupFileInfo);
					return;
				} finally {
					CommonStepProgress.closeStepProgressDialog();
					btnAdd.setEnabled(true);
				}
				JOptionPane.showMessageDialog(FmSysDataManage.this, "新增备份成功！");
			}
			btnAdd.setEnabled(true);
		}
	};

	/**
	 * This method initializes btnSelectedDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除备份");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (backupFileInfoTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmSysDataManage.this,
								"请选择需要删除的文件！");
						return;
					}
					if (JOptionPane.showConfirmDialog(FmSysDataManage.this,
							"你确定要删除此备份文件吗?", "确认", 0) != JOptionPane.OK_OPTION) {
						return;
					}
					BackupFileInfo backupFileInfo = (BackupFileInfo) backupFileInfoTableModel
							.getCurrentRow();
					backupAction.removeBakcupFile(new Request(CommonVars
							.getCurrUser()), backupFileInfo);
					backupFileInfoTableModel.deleteRow(backupFileInfo);
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnSelectedAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRestoreData() {
		if (btnRestoreData == null) {
			btnRestoreData = new JButton();
			btnRestoreData.setText("恢复数据");
			btnRestoreData
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (backupFileInfoTableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmSysDataManage.this, "请选择需要恢复数据的文件！");
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmSysDataManage.this, "你确定要恢复此备份文件的数据吗?",
									"确认", 0) != JOptionPane.OK_OPTION) {
								return;
							}
							RestoreDataRunnable thread = new RestoreDataRunnable();
							thread.setTableMode(backupFileInfoTableModel);
							thread.start();
						}
					});
		}
		return btnRestoreData;
	}

	class RestoreDataRunnable extends Thread {
		private JTableListModel tableMode = null;

		public void setTableMode(JTableListModel jTableListModel) {
			tableMode = jTableListModel;
		}

		public void run() {
			btnRestoreData.setEnabled(false);
			String taskId = CommonProgress.getExeTaskId();
			// System.out.println("aaaaaaaaaaaaaaa:"+taskId);
			CommonStepProgress.showStepProgressDialog(taskId);
			CommonStepProgress.setStepMessage("系统正在恢复资料，请稍后");
			try {
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				backupAction.restoreFromFile(request,
						(BackupFileInfo) tableMode.getCurrentRow());
			} catch (Exception e2) {
				e2.printStackTrace();
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmSysDataManage.this, "资料恢复失败！");
				return;
			} finally {
				CommonStepProgress.closeStepProgressDialog();
				btnRestoreData.setEnabled(true);
			}
			JOptionPane.showMessageDialog(FmSysDataManage.this,
					"资料恢复成功！如果你恢复的资料不是当前公司资料的话,请重新登陆系统");
		};
	}

	/**
	 * This method initializes btnSelectedDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUploadBackupFile() {
		if (btnUploadBackupFile == null) {
			btnUploadBackupFile = new JButton();
			btnUploadBackupFile.setText("上传备份文件");
			btnUploadBackupFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							UploadBackupDataRunnable thread = new UploadBackupDataRunnable();
							thread.setTableMode(backupFileInfoTableModel);
							thread.start();
						}
					});
		}
		return btnUploadBackupFile;
	}

	class UploadBackupDataRunnable extends Thread {

		private JTableListModel tableMode = null;

		public void setTableMode(JTableListModel jTableListModel) {
			tableMode = jTableListModel;
		}

		public void run() {
			JFileChooser jfc = new JFileChooser();
			jfc.removeChoosableFileFilter(jfc.getAcceptAllFileFilter());
			jfc.setFileFilter(new BackupFileFilter("jbcus"));
			if (jfc.showOpenDialog(FmSysDataManage.this) == JFileChooser.APPROVE_OPTION) {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在上传备份资料，请稍后");
				File file = jfc.getSelectedFile();
				BackupFileInfo backupFileInfo = null;
				try {
					byte[] fileContext = new byte[(int) file.length()];
					FileInputStream in = new FileInputStream(file);
					in.read(fileContext);
					in.close();
					backupFileInfo = backupAction.uploadBackupFile(new Request(
							CommonVars.getCurrUser()), fileContext);
					if (backupFileInfo != null) {
						tableMode.addRow(backupFileInfo);
					}
				} catch (Exception e2) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(FmSysDataManage.this,
							"上传备份资料失败！" + e2.getMessage());
					e2.printStackTrace();
					return;
				} finally {
					CommonProgress.closeProgressDialog();
				}
				JOptionPane
						.showMessageDialog(FmSysDataManage.this, "上传备份资料成功！");
			}
		};
	}

	/**
	 * This method initializes btnSelectedAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDownloadBackupFile() {
		if (btnDownloadBackupFile == null) {
			btnDownloadBackupFile = new JButton();
			btnDownloadBackupFile.setText("下载备份文件");
			btnDownloadBackupFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (backupFileInfoTableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmSysDataManage.this, "请选择需要下载的文件！");
								return;
							}
							DownloadBackupDataRunnable thread = new DownloadBackupDataRunnable();
							thread.setTableMode(backupFileInfoTableModel);
							thread.start();
						}
					});
		}
		return btnDownloadBackupFile;
	}

	class DownloadBackupDataRunnable extends Thread {
		private JTableListModel tableMode = null;

		public void setTableMode(JTableListModel jTableListModel) {
			tableMode = jTableListModel;
		}

		public void run() {
			JFileChooser jfc = new JFileChooser();
			jfc.removeChoosableFileFilter(jfc.getAcceptAllFileFilter());
			jfc.setFileFilter(new BackupFileFilter("jbcus"));
			BackupFileInfo backupFileInfo = (BackupFileInfo) tableMode
					.getCurrentRow();
			jfc.setSelectedFile(new File(backupFileInfo.getFileName()));
			if (jfc.showSaveDialog(FmSysDataManage.this) == JFileChooser.APPROVE_OPTION) {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在下载备份资料，请稍后");

				File file = jfc.getSelectedFile();
				String fileName = file.getPath();
				int lastIndex = fileName.lastIndexOf(File.separator);
				if (lastIndex > 0) {
					fileName = fileName.substring(0, lastIndex + 1)
							+ backupFileInfo.getFileName();
				} else {
					fileName = fileName + File.separator
							+ backupFileInfo.getFileName();
				}
				file = new File(fileName);

				byte[] fileContext = backupAction.downloadBackupFile(
						new Request(CommonVars.getCurrUser()), backupFileInfo);
				try {
					FileOutputStream out = new FileOutputStream(file);
					out.write(fileContext);
					out.flush();
					out.close();
				} catch (Exception e2) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(FmSysDataManage.this,
							"下载备份资料失败！");
					e2.printStackTrace();
					return;
				} finally {
					CommonProgress.closeProgressDialog();
				}
				JOptionPane
						.showMessageDialog(FmSysDataManage.this, "下载备份资料成功！");
			}
		};
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getJButton());
			jToolBar1.add(getJButton1());
			jToolBar1.add(getJButton2());
			jToolBar1.add(getJButton3());
			jToolBar1.add(getJButton4());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes btnSelectedAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnSelectedAdd == null) {
			btnSelectedAdd = new JButton();
			btnSelectedAdd.setText("新增备份");
			btnSelectedAdd
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgBackupModuleItem dg = new DgBackupModuleItem();
							dg.setVisible(true);
							if (dg.isOk() == true) {
								AddBackupDataRunnable thread = new AddBackupDataRunnable();
								thread.setLsSelectedClasses(dg.returnValue());
								thread.setBackupItem(dg.returnModuleName());
								thread.setIsAll(false);
								thread.start();
							}
						}
					});
		}
		return btnSelectedAdd;
	}

	/**
	 * This method initializes tbSelectedBackupFileInfo
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (tbSelectedBackupFileInfo == null) {
			tbSelectedBackupFileInfo = new JTable();
		}
		return tbSelectedBackupFileInfo;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes btnSelectedDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (btnSelectedDelete == null) {
			btnSelectedDelete = new JButton();
			btnSelectedDelete.setText("删除备份");
			btnSelectedDelete
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (selectedBackupFileInfoTableModel
									.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmSysDataManage.this, "请选择需要删除的文件！");
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmSysDataManage.this, "你确定要删除此备份文件吗?",
									"确认", 0) != JOptionPane.OK_OPTION) {
								return;
							}
							BackupFileInfo backupFileInfo = (BackupFileInfo) selectedBackupFileInfoTableModel
									.getCurrentRow();
							backupAction.removeBakcupFile(new Request(
									CommonVars.getCurrUser()), backupFileInfo);
							selectedBackupFileInfoTableModel
									.deleteRow(backupFileInfo);
						}
					});
		}
		return btnSelectedDelete;
	}

	/**
	 * This method initializes btnSelectedRestoreData
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnSelectedRestoreData == null) {
			btnSelectedRestoreData = new JButton();
			btnSelectedRestoreData.setText("恢复数据");
			btnSelectedRestoreData
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (selectedBackupFileInfoTableModel
									.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmSysDataManage.this, "请选择需要恢复数据的文件！");
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmSysDataManage.this, "你确定要恢复此备份文件的数据吗?",
									"确认", 0) != JOptionPane.OK_OPTION) {
								return;
							}
							RestoreDataRunnable thread = new RestoreDataRunnable();
							thread
									.setTableMode(selectedBackupFileInfoTableModel);
							thread.start();
						}
					});
		}
		return btnSelectedRestoreData;
	}

	/**
	 * This method initializes btnSelectedUploadBackupFile
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnSelectedUploadBackupFile == null) {
			btnSelectedUploadBackupFile = new JButton();
			btnSelectedUploadBackupFile.setText("下载备份文件");
			btnSelectedUploadBackupFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (selectedBackupFileInfoTableModel
									.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmSysDataManage.this, "请选择需要下载的文件！");
								return;
							}
							DownloadBackupDataRunnable thread = new DownloadBackupDataRunnable();
							thread
									.setTableMode(selectedBackupFileInfoTableModel);
							thread.start();
						}
					});
		}
		return btnSelectedUploadBackupFile;
	}

	/**
	 * This method initializes btnSelectedDownloadBackupFile
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (btnSelectedDownloadBackupFile == null) {
			btnSelectedDownloadBackupFile = new JButton();
			btnSelectedDownloadBackupFile.setText("上传备份文件");
			btnSelectedDownloadBackupFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							UploadBackupDataRunnable thread = new UploadBackupDataRunnable();
							thread
									.setTableMode(selectedBackupFileInfoTableModel);
							thread.start();

						}
					});
		}
		return btnSelectedDownloadBackupFile;
	}
} // @jve:decl-index=0:visual-constraint="36,-1"
