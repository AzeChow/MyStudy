/*
 * Created on 2004-8-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.message;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import com.bestway.bcus.client.DgErrorMessage;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.bcus.message.entity.ReceiptResult;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MyFile;
import com.bestway.common.Request;
import com.bestway.ui.message.DgMessage;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author xxm // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgRecvProcessMessage extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JButton btnProcess = null;
	private JButton btnClose = null;
	private JTable tbUnProcess = null;
	private JScrollPane jScrollPane = null;
	private JTable tbProcessed = null;
	private JScrollPane jScrollPane1 = null;
	private MessageAction messageAction = null;
	private JTableListModel unProcessModel = null;
	private JTableListModel processedModel = null;
	private JPanel jPanel3 = null;
	private JButton jButton = null;

	private JButton jButton1 = null;
	private String type; // @jve:decl-index=0:
	private boolean isOk = false;

	private JButton btnBtnchProcess = null;

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * This is the default constructor
	 */
	public DgRecvProcessMessage() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("回执处理");
		this.setSize(577, 376);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				// 未处理
				messageAction = (MessageAction) CommonVars
						.getApplicationContext().getBean("messageAction");
				List fileList = getFileList();
				// 已处理
				List processedReceipts = messageAction
						.findReceiptResults(new Request(CommonVars
								.getCurrUser()));
				initProcessedTable(processedReceipts);
				initUnProcessTable(fileList);
			}
		});
	}

	private void initProcessedTable(List list) {
		processedModel = new JTableListModel(this.tbProcessed, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("经营单位代码", "tradeCode", 80));
						list.add(addColumn("企业内部编号", "copEmsNo", 80));
						list.add(addColumn("数据中心统一编号", "seqNo", 80));
						list.add(addColumn("数据类型", "applType", 50));
						list.add(addColumn("申报类型", "declareType", 50));
						list.add(addColumn("处理结果", "chkMark", 80));
						list.add(addColumn("通知日期", "noticeDate", 80));
						// list.add(addColumn("通知时间", "noticeTime", 80));
						list.add(addColumn("备注", "note", 80));
						return list;
					}
				});
	}

	private List<MyFile> getFileList() {
		return messageAction.getUnProcessFiles(new Request(CommonVars
				.getCurrUser()), type);
	}

	private void initUnProcessTable(List list) {
		unProcessModel = new JTableListModel(this.tbUnProcess, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("文件名", "name", 370));
						list.add(addColumn("文件大小", "length", 80));
						list.add(addColumn("最后修改日期", "lastModified", 80));
						return list;
					}
				});
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
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJPanel3(), java.awt.BorderLayout.NORTH);
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
			jTabbedPane.addTab("未处理回执", null, getJPanel(), null);
			jTabbedPane.addTab("已经处理的回执", null, getJPanel2(), null);
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
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getBtnProcess(), null);
			jPanel1.add(getBtnBtnchProcess(), null);
			jPanel1.add(getBtnClose(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes btnProcess
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("回执处理");
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					process();
				}
			});
		}
		return btnProcess;
	}
	
	
	private void process(){
		if (unProcessModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(
					DgRecvProcessMessage.this, "请先选择要处理的文件。");
			return;
		}
		
		MyFile myFile = (MyFile) unProcessModel.getCurrentRow();
		if(type.equals("EmsHeadH2k")) {
			int i = JOptionPane.showConfirmDialog(this, 
					"文件名：        "+myFile.getName()+"\r\n"
					+"文件长度："+myFile.getLength()+"\r\n"
					+"最后修改："+myFile.getLastModified()+"\r\n", "提示！", JOptionPane.YES_NO_OPTION);
			if (i != 0){
				return;
			}


		}
		
		String sCheckInfo = messageAction.processMessage(
				new Request(CommonVars.getCurrUser()), myFile
						.getFile());
		
		DgErrorMessage dg = DgErrorMessage.getInstance(sCheckInfo);
		dg.setTitle("回执处理信息：");
		dg.setVisible(true);
		
		if (!messageAction.moveFileToProcessedDir(new Request(
				CommonVars.getCurrUser()), myFile.getFile())) {
			JOptionPane.showMessageDialog(
					DgRecvProcessMessage.this, "移动回执到处理完的回执目录时失败");
		}
		if (type.equals("CancelHead")) {
			isOk = true;
		}
		List<MyFile> fileList = getFileList();
		initUnProcessTable(fileList);
		List<ReceiptResult> processedReceipts = messageAction
				.findReceiptResults(new Request(CommonVars
						.getCurrUser()));
		initProcessedTable(processedReceipts);
	}

	/**
	 * 批量处理回执
	 */
	private void btnchProcess(){
		List rowList = unProcessModel.getList();
		if(rowList!=null&&rowList.size()>0){
			for (int i = 0; i < rowList.size(); i++) {
				MyFile myFile = (MyFile) rowList.get(i);
				String messstr ="";
				messstr += "文件名：        "+myFile.getName()+"\r\n"
						+"文件长度："+myFile.getLength()+"\r\n"
						+"最后修改："+myFile.getLastModified()+"\r\n";
				if(type.equals("EmsHeadH2k")) {
					int x = JOptionPane.showConfirmDialog(this, messstr, "提示！", JOptionPane.YES_NO_OPTION);
					if (x != 0){
						return;
					}
				}
				String sCheckInfo = messageAction.processMessage(
						new Request(CommonVars.getCurrUser()), myFile
								.getFile());
				new DgMessage("回执处理信息",sCheckInfo);
				if (!messageAction.moveFileToProcessedDir(new Request(
						CommonVars.getCurrUser()), myFile.getFile())) {
					JOptionPane.showMessageDialog(
							DgRecvProcessMessage.this, "移动回执到处理完的回执目录时失败");
				}
				if (type.equals("CancelHead")) {
					isOk = true;
				}
				List fileList = getFileList();
				initUnProcessTable(fileList);
			}
			List processedReceipts = messageAction
					.findReceiptResults(new Request(CommonVars.getCurrUser()));
			initProcessedTable(processedReceipts);
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
			btnClose.setText("关  闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgRecvProcessMessage.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes tbUnProcess
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbUnProcess() {
		if (tbUnProcess == null) {
			tbUnProcess = new JTable();
		}
		return tbUnProcess;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbUnProcess());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbProcessed
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbProcessed() {
		if (tbProcessed == null) {
			tbProcessed = new JTable();
		}
		return tbProcessed;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbProcessed());
		}
		return jScrollPane1;
	}

	/**
	 * 
	 * This method initializes jPanel3
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jPanel3.setBackground(java.awt.Color.white);
			jLabel.setText("回执处理");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel2.setText("");
			jLabel2
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jPanel3.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel3.add(jLabel1, java.awt.BorderLayout.EAST);
			jPanel3.add(jLabel2, java.awt.BorderLayout.WEST);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("回执信息查看");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						if (unProcessModel == null
								|| unProcessModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									DgRecvProcessMessage.this, "请选择未处理回执信息！",
									"提示", 2);
							return;
						}
						MyFile file = (MyFile) unProcessModel.getCurrentRow();
						String fileName = file.getName();
						new openUnRecv(fileName).start();
					} else {
						if (processedModel == null
								|| processedModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									DgRecvProcessMessage.this, "请选择回执信息！",
									"提示", 2);
							return;
						}
						ReceiptResult file = (ReceiptResult) processedModel
								.getCurrentRow();
						String fileName = file.getFileName();
						new openRecv(fileName).start();
					}

				}
			});
		}
		return jButton;
	}

	class openUnRecv extends Thread {
		List message = null;
		String file = "";

		public openUnRecv(String file) {
			this.file = file;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgRecvProcessMessage.this);
				CommonProgress.setMessage("系统正准备打开未处理回执，请稍后...");
				message = messageAction.getUnprocess(new Request(CommonVars
						.getCurrUser()), file, type);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgRecvProcessMessage.this,
						"打开未处理回执失败：！" + e.getMessage(), "提示", 2);
			} finally {
				DgLookCustomsMessage dg = new DgLookCustomsMessage();
				dg.setList(message);
				dg.setVisible(true);
			}
		}
	}

	class openRecv extends Thread {
		List message = null;
		String file = "";

		public openRecv(String file) {
			this.file = file;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgRecvProcessMessage.this);
				CommonProgress.setMessage("系统正准备打开未处理回执，请稍后...");
				message = messageAction.getprocess(new Request(CommonVars
						.getCurrUser()), file);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgRecvProcessMessage.this,
						"打开未处理回执失败：！" + e.getMessage(), "提示", 2);
			} finally {
				DgLookCustomsMessage dg = new DgLookCustomsMessage();
				dg.setList(message);
				dg.setVisible(true);
			}
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("异常回执删除");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (unProcessModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgRecvProcessMessage.this, "请先选择要异常回执删除的文件。");
						return;
					}
					if (JOptionPane.showConfirmDialog(
							DgRecvProcessMessage.this, "确定要移出该回执吗？", "确认", 0) == 0) {

						MyFile myFile = (MyFile) unProcessModel.getCurrentRow();

						if (!messageAction.moveFileToProcessedDir(new Request(
								CommonVars.getCurrUser()), myFile.getFile())) {
							JOptionPane.showMessageDialog(
									DgRecvProcessMessage.this,
									"移动回执到处理完的回执目录时失败");
						}
						List fileList = getFileList();
						initUnProcessTable(fileList);

						List processedReceipts = messageAction
								.findReceiptResults(new Request(CommonVars
										.getCurrUser()));
						initProcessedTable(processedReceipts);
					}
				}
			});
		}
		return jButton1;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * This method initializes btnBtnchProcess	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnBtnchProcess() {
		if (btnBtnchProcess == null) {
			btnBtnchProcess = new JButton();
			btnBtnchProcess.setText("批量处理回执");
			btnBtnchProcess.addActionListener(new java.awt.event.ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					 btnchProcess();
				}
			});
		}
		return btnBtnchProcess;
	}
} // @jve:decl-index=0:visual-constraint="10,10"

