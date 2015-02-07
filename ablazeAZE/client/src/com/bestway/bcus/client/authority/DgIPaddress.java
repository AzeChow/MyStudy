package com.bestway.bcus.client.authority;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclIPaddress;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgIPaddress extends JDialogBase {

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JTextField tfBegianIP = null;

	private JTextField ftEndIP = null;

	private JLabel jLabel2 = null;

	private JTextField ftMemo = null;

	private JButton btnOK = null;

	private JButton btnCancel = null;

	private JTableListModel tableModel = null;

	private int state = DataState.ADD;

	private AuthorityAction authorityAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgIPaddress() {
		super();
		authorityAction = (AuthorityAction) CommonVars.getApplicationContext()
				.getBean("authorityAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(346, 219));
		this.setContentPane(getJPanel());
		this.setTitle("新增/修改IP地址");

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(53, 91, 43, 26));
			jLabel2.setText("备注");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(53, 59, 43, 26));
			jLabel1.setText("结束IP");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(53, 27, 43, 26));
			jLabel.setText("起始IP");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getFtEndIP(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getFtMemo(), null);
			jPanel.add(getBtnOK(), null);
			jPanel.add(getBtnCancel(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (tfBegianIP == null) {
			tfBegianIP = new JTextField();
			tfBegianIP.setBounds(new Rectangle(98, 27, 185, 26));
		}
		return tfBegianIP;
	}

	/**
	 * This method initializes ftEndIP
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getFtEndIP() {
		if (ftEndIP == null) {
			ftEndIP = new JTextField();
			ftEndIP.setBounds(new Rectangle(98, 59, 185, 26));
		}
		return ftEndIP;
	}

	public void setVisible(boolean b) {
		showData();
		super.setVisible(b);
	}

	/**
	 * This method initializes ftMemo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getFtMemo() {
		if (ftMemo == null) {
			ftMemo = new JTextField();
			ftMemo.setBounds(new Rectangle(98, 91, 185, 26));
		}
		return ftMemo;
	}

	/**
	 * This method initializes btnOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(79, 139, 73, 26));
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkIPaddress()) {
						return;
					}
					AclIPaddress data = null;
					if (state == DataState.ADD) {
						data = new AclIPaddress();
						data = fillData(data);
						data.setCreateDate(new Date());
						data.setLastModifDate(new Date());
						data = authorityAction.saveIPaddress(new Request(
								CommonVars.getCurrUser()), data);
						tableModel.addRow(data);
					} else if (state == DataState.EDIT) {
						data = (AclIPaddress) tableModel.getCurrentRow();
						data = fillData(data);
						data.setLastModifDate(new Date());
						data = authorityAction.saveIPaddress(new Request(
								CommonVars.getCurrUser()), data);
						tableModel.updateRow(data);
					}
					dispose();
				}
			});
		}
		return btnOK;
	}

	private boolean checkIPaddress() {
		String beginIP = this.getJTextField().getText();
		String endIP = this.getFtEndIP().getText();
		if (beginIP == null || beginIP.trim().equals("") || endIP == null
				|| endIP.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "起始IP或结束IP为空,请重新输入!", "提示!",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		String tempbegin = makeIPTo12Num(beginIP);
		if (tempbegin == null || tempbegin.equals("")) {
			return false;
		} else {
			this.getJTextField().setText(tempbegin);
		}
		String tempend = makeIPTo12Num(endIP);
		if (tempend == null || tempend.equals("")) {
			return false;
		} else {
			this.getFtEndIP().setText(tempend);
		}
		String[] bstrs = CommonVariables.split(tempbegin, ".");
		String[] estrs = CommonVariables.split(tempend, ".");
		boolean is = true;
		for (int i = 0; i < bstrs.length; i++) {
			if (Integer.parseInt(bstrs[i].replace(".", "")) < Integer
					.parseInt(estrs[i].replace(".", ""))) {
				is = true;
				break;
			} else if (Integer.parseInt(bstrs[i].replace(".", "")) == Integer
					.parseInt(estrs[i].replace(".", ""))) {
				continue;
			} else {
				is = false;
				break;
			}
		}
		if (!is) {
			JOptionPane.showMessageDialog(this, "结束地址应该大于或等于开始地址!", "提示!",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private String makeIPTo12Num(String ip) {
		String resultstr = "";
		String[] strs = CommonVariables.split(ip.trim(), ".");
		if (strs.length != 4) {
			JOptionPane.showMessageDialog(this, "IP地址应为4组数字,以英文点(.)隔开!", "提示!",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		for (int p = 0; p < strs.length; p++) {
			if (strs[p].contains(".")) {
				strs[p] = strs[p].substring(0, strs.length - 1);
			}
		}
		int[] ints = new int[strs.length];
		try {
			for (int t = 0; t < strs.length; t++) {
				ints[t] = Integer.parseInt(strs[t]);

			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "IP地址应为4组数字,以英文点(.)隔开!", "提示!",
					JOptionPane.WARNING_MESSAGE);
			ex.printStackTrace();
			return null;
		}

		for (int k = 0; k < ints.length; k++) {
			if (ints[k] < 0 || ints[k] > 255) {
				JOptionPane.showMessageDialog(this, "数字应该在0-255之间,以以英文点(.)隔开!",
						"提示!", JOptionPane.WARNING_MESSAGE);
				return null;
			} else {
				if (new Integer(strs[k]).toString().length() == 1) {
					resultstr += ("00" + new Integer(strs[k]).toString() + ".");
				} else if (new Integer(strs[k]).toString().length() == 2) {
					resultstr += ("0" + new Integer(strs[k]).toString() + ".");
				} else {
					resultstr += (new Integer(strs[k]).toString() + ".");
				}

			}
		}

		return resultstr.substring(0, resultstr.length() - 1);
	}

	private String makeIPToMinNum(String ip) {
		String resultstr = "";
		String[] strs = CommonVariables.split(ip.trim(), ".");
		for (int p = 0; p < strs.length; p++) {
			if (strs[p].contains(".")) {
				strs[p] = strs[p].substring(0, strs.length - 1);
			}
		}
		int[] ints = new int[strs.length];
		try {
			for (int t = 0; t < strs.length; t++) {
				ints[t] = Integer.parseInt(strs[t]);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		for (int k = 0; k < ints.length; k++) {
			if (new Integer(strs[k]).toString().length() == 1) {
				resultstr += (new Integer(strs[k]).toString() + ".");
			} else if (new Integer(strs[k]).toString().length() == 2) {
				resultstr += (new Integer(strs[k]).toString() + ".");
			} else {
				resultstr += (new Integer(strs[k]).toString() + ".");
			}
		}

		return resultstr.substring(0, resultstr.length() - 1);
	}

	private AclIPaddress fillData(AclIPaddress data) {
		data.setBegianIP(getJTextField().getText() == null ? ""
				: getJTextField().getText());
		data.setEndIP(getFtEndIP().getText() == null ? "" : getFtEndIP()
				.getText().trim());
		data.setExplain(getFtMemo().getText() == null ? "" : getFtMemo()
				.getText().trim());
		return data;
	}

	private void showData() {
		if (tableModel.getCurrentRow() == null || state == DataState.ADD) {
			return;
		}
		AclIPaddress ip = (AclIPaddress) tableModel.getCurrentRow();
		this.getJTextField().setText(makeIPToMinNum(ip.getBegianIP()));
		this.getFtEndIP().setText(makeIPToMinNum(ip.getEndIP()));
		this.getFtMemo().setText(ip.getExplain());
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(193, 139, 73, 26));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

} // @jve:decl-index=0:visual-constraint="164,35"
