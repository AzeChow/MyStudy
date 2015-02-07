/*
 * Created on 2004-9-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.bestway.client.custom.common;

import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.message.DgProcessCustomsMessageTCS;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;

/**
 * 
 */
@SuppressWarnings( { "unchecked", "deprecation", "serial" })
public class DgProcessCustomsMessage extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JButton btnProcess = null;

	private JButton btnExit = null;

	private MessageAction messageAction = null; // @jve:decl-index=0:

	private JTextArea taExecInfo = null;

	public int projectType;

	protected ManualDeclareAction manualDeclareAction = null; // @jve:decl-index=0:

	private JScrollPane jScrollPane = null;

	private JLabel jLabel = null;

	/**
	 * This is the default constructor
	 */
	public DgProcessCustomsMessage() {
		super();
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");

		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setResizable(false);
		this.setSize(802, 520);
		this.setContentPane(getJContentPane());
		this.setTitle("回执处理");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setText(" 回执处理信息:");
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(jLabel, BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.add(getBtnProcess(), null);
			jPanel.add(getBtnExit(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnProcess
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("处理回执");
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 处理回执
					new ReceiptProcess().start();
				}
			});
		}
		return btnProcess;
	}

	class ReceiptProcess extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgProcessCustomsMessage.this);

				CommonProgress.setMessage("系统开始处理回执，请稍后...");
				
				taExecInfo.append("\n第一步：下载回执...");
				
				try {
					String msg = messageAction.customsBrokerDownload();

					taExecInfo.append(msg);
				} catch (RuntimeException e) {
					e.printStackTrace();
					taExecInfo.append("\n" + e.getMessage());
				}

				taExecInfo.append("\n第二步：开始处理回执...");

				List<String> files = new ArrayList<String>();
				File[] tempFiles = messageAction
						.getCustomBillRecvFiles(new Request(CommonVars
								.getCurrUser()));
				Map<String, File> progessFiles = new HashMap<String, File>();

				for (int i = 0; i < tempFiles.length; i++) {
					progessFiles.put(tempFiles[i].getName(), tempFiles[i]);
					files.add(tempFiles[i].getName());
				}
				Collections.sort(files);

				taExecInfo.append("\n开始处理回执 Files Total Num:" + files.size()
						+ "\n");
				for (int i = 0; i < files.size(); i++) {
					taExecInfo.append("正在处理回执：" + files.get(i) + "\n");
					messageAction.processTcsReturnMessage(new Request(
							CommonVars.getCurrUser()), progessFiles.get(files
							.get(i)), projectType);
					CommonProgress.setMessage("已成功处理完回执：" + files.get(i)
							+ "。\n");
					taExecInfo.append("已成功处理完回执：" + files.get(i) + "。\n");

				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgProcessCustomsMessage.this,
						"处理回执失败：！" + e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgProcessCustomsMessage.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes taExecInfo
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTaExecInfo() {
		if (taExecInfo == null) {
			taExecInfo = new JTextArea();
		}
		return taExecInfo;
	}

	public List getFileString(List lines) {
		List sb = new ArrayList();
		for (int i = 0; i < lines.size(); i++) {
			try {
				sb.add((String) lines.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb;
	}

	/**
	 * @return Returns the projectType.
	 */
	public int getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            The projectType to set.
	 */
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTaExecInfo());
		}
		return jScrollPane;
	}

}
