/*
 * Created on 2004-6-8
 * editby xxm
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author fhz
 * 
 * 修改协议号
 */
public class DgEditEmsNo extends JDialogBase {
	/**
	 * Commons Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(DgEditEmsNo.class);

	private javax.swing.JPanel jContentPane = null;

	private JTextField tfOldEmsNo = null;

	private JTextField tfNewEmsNo = null;

	private JButton btnDone = null;

	private JButton btnCancel = null;

	private JTableListModel tableModel = null;

	private CasAction casAction;

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * This is the default constructor
	 */
	public DgEditEmsNo() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */

	private void initialize() {
		this.setSize(331, 195);
		this.setTitle("修改协议号");
		this.setContentPane(getJContentPane());

	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			StatCusNameRelationHsn statCusNameRelationHsn = (StatCusNameRelationHsn) tableModel
					.getCurrentRow();
			if (statCusNameRelationHsn.getEmsNo() != null)
				tfOldEmsNo.setText(statCusNameRelationHsn.getEmsNo());
			else
				tfOldEmsNo.setText("");
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("旧协议号");
			jLabel.setBounds(30, 25, 75, 20);
			jLabel1.setText("新协议号");
			jLabel1.setBounds(30, 55, 75, 20);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfOldEmsNo(), null);
			jContentPane.add(getTfNewEmsNo(), null);
			jContentPane.add(getBtnDone(), null);
			jContentPane.add(getBtnCancel(), null);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes tfLoginName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfOldEmsNo() {
		if (tfOldEmsNo == null) {
			tfOldEmsNo = new JTextField();
			tfOldEmsNo.setBounds(111, 25, 177, 21);
			tfOldEmsNo.setEditable(false);
		}
		return tfOldEmsNo;
	}

	/**
	 * 
	 * This method initializes tfUserName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfNewEmsNo() {
		if (tfNewEmsNo == null) {
			tfNewEmsNo = new JTextField();
			tfNewEmsNo.setBounds(112, 55, 177, 21);
		}
		return tfNewEmsNo;
	}

	/**
	 * 
	 * This method initializes btnDone
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDone() {
		if (btnDone == null) {
			btnDone = new JButton();
			btnDone.setText("确定");
			btnDone.setBounds(66, 105, 67, 23);
			btnDone.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tfNewEmsNo.getText() == null
							&& tfNewEmsNo.getText().equals("")) {
						JOptionPane.showMessageDialog(DgEditEmsNo.this,
								"请输入新的协议号!", "提示", JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (JOptionPane.showConfirmDialog(DgEditEmsNo.this,
							"你确定要修改协议号?", "确认", 0) == 0) {
						try {
							StatCusNameRelationHsn statCusNameRelationHsn = (StatCusNameRelationHsn) tableModel
									.getCurrentRow();
							if (!tfNewEmsNo.getText().equals(
									statCusNameRelationHsn.getEmsNo())) {
								if (!casAction
										.isExitSpecialCustomsContractNo(
												new Request(CommonVars
														.getCurrUser()),
												tfNewEmsNo.getText().trim(),
												statCusNameRelationHsn
														.getProjectType())) {// 检查“特殊报关单中的合同协议号”里存在指定的协议号
									if (JOptionPane
											.showConfirmDialog(
													DgEditEmsNo.this,
													"输入的协议号没有在“特殊报关单中的合同协议号”里存在,是否继续修改?",
													"提示", 0) == 0) {
										List list = tableModel.getCurrentRows();
										for (int i = 0; i < list.size(); i++) {
											statCusNameRelationHsn = (StatCusNameRelationHsn) list
													.get(i);
											statCusNameRelationHsn
													.setEmsNo(tfNewEmsNo
															.getText().trim());
											statCusNameRelationHsn = casAction
													.saveStatCusNameRelationHsn(
															new Request(
																	CommonVars
																			.getCurrUser()),
															statCusNameRelationHsn);
										}
										tableModel.updateRows(list);
									} else {
										return;
									}
								} else {
									List list = tableModel.getCurrentRows();
									for (int i = 0; i < list.size(); i++) {
										statCusNameRelationHsn = (StatCusNameRelationHsn) list
												.get(i);
										statCusNameRelationHsn
												.setEmsNo(tfNewEmsNo.getText()
														.trim());
										statCusNameRelationHsn = casAction
												.saveStatCusNameRelationHsn(
														new Request(CommonVars
																.getCurrUser()),
														statCusNameRelationHsn);
									}
									tableModel.updateRows(list);
								}
							}
						} catch (Exception e1) {
							System.out.println(e1.getStackTrace());
							return;
						}
					}
				}
			});

		}
		return btnDone;
	}

	/**
	 * 
	 * This method initializes btnCancel
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.setBounds(195, 105, 66, 23);
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEditEmsNo.this.dispose();
				}
			});

		}
		return btnCancel;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
