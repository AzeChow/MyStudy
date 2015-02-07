package com.bestway.bcs.client.contract;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.manualdeclare.FmEmsHeadH2k;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgProcessContractData extends JDialogBase {

	private JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar = null;

	private JButton btnDownload = null;

	private JButton btnViewDownloadFile = null;

	private JScrollPane jScrollPane = null;

	private JList lsDownloadFiles = null;

	private JToolBar jToolBar1 = null;

	private JSplitPane jSplitPane = null;

	private JButton btnProcess = null;

	private JButton btnViewProcessFile = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JScrollPane jScrollPane1 = null;

	private JScrollPane jScrollPane2 = null;

	private JList lsNotProcessed = null;

	private JList lsProcessed = null;

	private List processedData = new ArrayList();

	private List notProcessedData = new ArrayList();

	private ContractAction contractAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgProcessContractData() {
		super();
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			this.showNotProcessedData();
			this.showProcessedData();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(630, 441));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("合同下载处理");
		this.setContentPane(getJContentPane());

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("下载合同", null, getJPanel(), null);
			jTabbedPane.addTab("处理合同", null, getJPanel1(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
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
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.SOUTH);
			jPanel1.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnDownload());
			jToolBar.add(getBtnViewDownloadFile());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDownload() {
		if (btnDownload == null) {
			btnDownload = new JButton();
			btnDownload.setText("下载");
			btnDownload.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new downFiles().start();
				}
			});
		}
		return btnDownload;
	}

	
	class downFiles extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在进行下载合同，请稍后...");
				
				List list = contractAction.ftpContractDownload(new Request(
						CommonVars.getCurrUser()));
				lsDownloadFiles.setListData(list.toArray());
				lsNotProcessed.setListData(list.toArray());
				
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgProcessContractData.this, "下载合同失败！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnViewDownloadFile() {
		if (btnViewDownloadFile == null) {
			btnViewDownloadFile = new JButton();
			btnViewDownloadFile.setText("查看内容");
			btnViewDownloadFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if(lsDownloadFiles
									.getSelectedValue()==null){
								return;
							}
							String fileName = lsDownloadFiles
									.getSelectedValue().toString();
							showContractFileContent(fileName);
						}
					});
		}
		return btnViewDownloadFile;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getLsDownloadFiles());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getLsDownloadFiles() {
		if (lsDownloadFiles == null) {
			lsDownloadFiles = new JList();
			lsDownloadFiles
					.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		}
		return lsDownloadFiles;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnProcess());
			jToolBar1.add(getBtnViewProcessFile());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(6);
			jSplitPane.setLeftComponent(getJPanel2());
			jSplitPane.setRightComponent(getJPanel3());
			jSplitPane.setDividerLocation(300);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("处理合同");
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(lsNotProcessed.getSelectedIndex()<0){
						return;
					}
				    new exectFiles().start();	
				}
			});
		}
		return btnProcess;
	}
	
	
	class exectFiles extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在进行处理合同，请稍后...");
				
				String selectFile = lsNotProcessed.getSelectedValue().toString();
		        contractAction.processContractData(new Request(CommonVars.getCurrUser()), selectFile);
		        lsNotProcessed.remove(lsNotProcessed.getSelectedIndex());
		        processedData.add(selectFile);
		        lsProcessed.setListData(processedData.toArray());
				
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgProcessContractData.this, "处理合同失败！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}
	

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnViewProcessFile() {
		if (btnViewProcessFile == null) {
			btnViewProcessFile = new JButton();
			btnViewProcessFile.setText("查看内容");
			btnViewProcessFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String fileName = "";
							if (lsProcessed.getSelectedIndex() > -1) {
								fileName = lsProcessed.getSelectedValue()
										.toString();
							} else if (lsNotProcessed.getSelectedIndex() > -1) {
								fileName = lsNotProcessed.getSelectedValue()
										.toString();
							}
							if(fileName.trim().equals("")){
								return;
							}
							showContractFileContent(fileName);
						}
					});
		}
		return btnViewProcessFile;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jLabel.setText("未处理的合同");
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(jLabel, java.awt.BorderLayout.NORTH);
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("已处理的合同");
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(jLabel1, java.awt.BorderLayout.NORTH);
			jPanel3.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getLsNotProcessed());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getLsProcessed());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jList1
	 * 
	 * @return javax.swing.JList
	 */
	private JList getLsNotProcessed() {
		if (lsNotProcessed == null) {
			lsNotProcessed = new JList();
			lsNotProcessed
					.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			lsNotProcessed.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					lsProcessed.setSelectedIndex(-1);
				}
			});
		}
		return lsNotProcessed;
	}

	/**
	 * This method initializes jList2
	 * 
	 * @return javax.swing.JList
	 */
	private JList getLsProcessed() {
		if (lsProcessed == null) {
			lsProcessed = new JList();
			lsProcessed
					.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			lsProcessed.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					lsNotProcessed.setSelectedIndex(-1);
				}
			});
		}
		return lsProcessed;
	}

	private void showNotProcessedData() {
		this.notProcessedData = contractAction
				.findNotProcessedFile(new Request(CommonVars.getCurrUser(),
						true));
		lsNotProcessed.setListData(notProcessedData.toArray());
	}

	private void showProcessedData() {
		this.processedData = contractAction.findProcessedFile(new Request(
				CommonVars.getCurrUser(), true));
		lsProcessed.setListData(processedData.toArray());
	}

	private void showContractFileContent(String fileName) {
		List list = contractAction.getContractFileList(new Request(CommonVars
				.getCurrUser(), true), fileName);
		DgViewProcessData dg = new DgViewProcessData();
		dg.setDataSource(list);
		dg.setVisible(true);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
