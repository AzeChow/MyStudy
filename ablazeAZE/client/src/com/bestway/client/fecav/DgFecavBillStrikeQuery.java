package com.bestway.client.fecav;

import java.awt.Dialog;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.constant.FecavState;
import com.bestway.fecav.action.FecavAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.BorderLayout;

public class DgFecavBillStrikeQuery extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel lbMan = null;

	private JLabel lbFecavBillCode = null;

	private JTextField tfSignInNo = null;

	private JTextField tfMan = null;

	private JLabel lbBeginDate = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel lbEndDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private ButtonGroup buttonGroup = null;

	private FecavAction fecavAction = null;

	private int fecavState = FecavState.STRIKE_BALANCE;

	private List lsResult = new ArrayList();

	private boolean isBefore = true;

	private boolean isOk = false;

	public boolean isBefore() {
		return isBefore;
	}

	public void setBefore(boolean isBefore) {
		this.isBefore = isBefore;
	}

	public boolean isOk() {
		return isOk;
	}

	public List getLsResult() {
		return lsResult;
	}

	public void setFecavState(int fecavState) {
		this.fecavState = fecavState;
	}

	public DgFecavBillStrikeQuery() {
		super();
		initialize();
	}

	public DgFecavBillStrikeQuery(boolean isModal) {
		super(isModal);
		initialize();
	}

	public DgFecavBillStrikeQuery(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgFecavBillStrikeQuery(Dialog owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(329, 248));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("外部领单查询");
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			this.initUIComponents();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lbEndDate = new JLabel();
			lbEndDate.setBounds(new java.awt.Rectangle(84, 125, 15, 21));
			lbEndDate.setText("到");
			lbBeginDate = new JLabel();
			lbBeginDate.setBounds(new java.awt.Rectangle(34, 89, 64, 24));
			lbBeginDate.setText("领单日期 从");
			lbFecavBillCode = new JLabel();
			lbFecavBillCode.setBounds(new java.awt.Rectangle(34, 25, 64, 23));
			lbFecavBillCode.setText("核销编码");
			lbMan = new JLabel();
			lbMan.setBounds(new java.awt.Rectangle(34, 56, 64, 23));
			lbMan.setText("领单人　");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lbMan, null);
			jContentPane.add(lbFecavBillCode, null);
			jContentPane.add(getTfSignInNo(), null);
			jContentPane.add(getTfMan(), null);
			jContentPane.add(lbBeginDate, null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(lbEndDate, null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSignInNo() {
		if (tfSignInNo == null) {
			tfSignInNo = new JTextField();
			tfSignInNo.setBounds(new java.awt.Rectangle(100, 24, 161, 25));
		}
		return tfSignInNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMan() {
		if (tfMan == null) {
			tfMan = new JTextField();
			tfMan.setBounds(new java.awt.Rectangle(100, 56, 161, 25));
		}
		return tfMan;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(100, 88, 161, 25));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new java.awt.Rectangle(100, 122, 161, 25));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(69, 170, 66, 23));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					lsResult = getResultData();
					isOk = true;
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new java.awt.Rectangle(169, 170, 66, 23));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
		}
		return buttonGroup;
	}

	private void initUIComponents() {
		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
		switch (fecavState) {
		case FecavState.CONTROL:// 核销单管制查询
			this.setTitle("核销单管制查询");
			lbMan.setText("管制人");
			lbBeginDate.setText("管制日期从");
			break;
		case FecavState.STRIKE_BALANCE:// 核销单冲销查询
			this.setTitle("核销单冲销查询");
			lbMan.setText("冲销人");
			lbBeginDate.setText("冲销日期从");
			break;
		case FecavState.CANCEL:// 核销单核销查询
			this.setTitle("核销单核销查询");
			lbMan.setText("核销人");
			lbBeginDate.setText("核销日期从");
			break;
		case FecavState.FINANCE_SIGN_IN:// 核销单财务签收查询

			this.setTitle("核销单财务签收查询");
			lbMan.setText("财务签收人");
			lbBeginDate.setText("签收日期从");

			break;
		case FecavState.CLOSE_ACCOUNT:// 核销单关帐查询
			this.setTitle("核销单关帐查询");
			lbMan.setText("关帐人");
			lbBeginDate.setText("关帐日期从");
			break;
		}
	}

	private List getResultData() {
		List list = new ArrayList();
		String condition = "";
		List<Object> lsParam = new ArrayList<Object>();
		switch (fecavState) {
		case FecavState.CONTROL:// 核销单管制查询
			if (!this.tfSignInNo.getText().trim().equals("")) {
				condition += " and a.signInNo like ? ";
				lsParam.add("%" + this.tfSignInNo.getText().trim() + "%");
			}
			if (!this.tfMan.getText().trim().equals("")) {
				condition += " and a.cavSignInMan like ? ";
				lsParam.add("%" + this.tfMan.getText().trim() + "%");
			}
			if (this.cbbBeginDate.getDate() != null) {
				condition += " and a.cavSignInDate >=? ";
				lsParam.add(CommonVars
						.getBeginDate(this.cbbBeginDate.getDate()));
			}
			if (this.cbbEndDate.getDate() != null) {
				condition += " and a.cavSignInDate <=? ";
				lsParam.add(CommonVars.getEndDate(this.cbbEndDate.getDate()));
			}
			list = fecavAction.findFecavBillStrikeByState(new Request(
					CommonVars.getCurrUser(), true), FecavState.CONTROL,
					condition, lsParam);
			break;
		case FecavState.STRIKE_BALANCE:// 核销单冲销查询
			if (!this.tfSignInNo.getText().trim().equals("")) {
				condition += " and a.signInNo like ? ";
				lsParam.add("%" + this.tfSignInNo.getText().trim() + "%");
			}
			if (!this.tfMan.getText().trim().equals("")) {
				condition += " and a.strikeMan like ? ";
				lsParam.add("%" + this.tfMan.getText().trim() + "%");
			}
			if (this.cbbBeginDate.getDate() != null) {
				condition += " and a.strikeDate >=? ";
				lsParam.add(CommonVars
						.getBeginDate(this.cbbBeginDate.getDate()));
			}
			if (this.cbbEndDate.getDate() != null) {
				condition += " and a.strikeDate <=? ";
				lsParam.add(CommonVars.getEndDate(this.cbbEndDate.getDate()));
			}
			list = fecavAction.findFecavBillStrikeByState(new Request(
					CommonVars.getCurrUser(), true), FecavState.STRIKE_BALANCE,
					condition, lsParam);
			break;
		case FecavState.CANCEL:// 核销单核销查询
			if (!this.tfSignInNo.getText().trim().equals("")) {
				condition += " and a.signInNo like ? ";
				lsParam.add("%" + this.tfSignInNo.getText().trim() + "%");
			}
			if (!this.tfMan.getText().trim().equals("")) {
				condition += " and a.cancelMan like ? ";
				lsParam.add("%" + this.tfMan.getText().trim() + "%");
			}
			if (this.cbbBeginDate.getDate() != null) {
				condition += " and a.cancelDate >=? ";
				lsParam.add(CommonVars
						.getBeginDate(this.cbbBeginDate.getDate()));
			}
			if (this.cbbEndDate.getDate() != null) {
				condition += " and a.cancelDate <=? ";
				lsParam.add(CommonVars.getEndDate(this.cbbEndDate.getDate()));
			}
			list = fecavAction.findFecavBillStrikeByState(new Request(
					CommonVars.getCurrUser(), true), FecavState.CANCEL,
					condition, lsParam);
			break;
		case FecavState.FINANCE_SIGN_IN:// 核销单财务签收查询
			if (!this.tfSignInNo.getText().trim().equals("")) {
				condition += " and a.signInNo like ? ";
				lsParam.add("%" + this.tfSignInNo.getText().trim() + "%");
			}
			if (!this.tfMan.getText().trim().equals("")) {
				condition += " and a.financeSignInMan like ? ";
				lsParam.add("%" + this.tfMan.getText().trim() + "%");
			}
			if (this.cbbBeginDate.getDate() != null) {
				condition += " and a.financeSignInDate >=? ";
				lsParam.add(CommonVars
						.getBeginDate(this.cbbBeginDate.getDate()));
			}
			if (this.cbbEndDate.getDate() != null) {
				condition += " and a.financeSignInDate <=? ";
				lsParam.add(CommonVars.getEndDate(this.cbbEndDate.getDate()));
			}
			list = fecavAction.findFecavBillStrikeByState(new Request(
					CommonVars.getCurrUser(), true),
					FecavState.FINANCE_SIGN_IN, condition, lsParam);
			break;
		case FecavState.CLOSE_ACCOUNT:// 核销单关帐查询
			if (!this.tfSignInNo.getText().trim().equals("")) {
				condition += " and a.signInNo like ? ";
				lsParam.add("%" + this.tfSignInNo.getText().trim() + "%");
			}
			if (!this.tfMan.getText().trim().equals("")) {
				condition += " and a.closeAccountMan like ? ";
				lsParam.add("%" + this.tfMan.getText().trim() + "%");
			}
			if (this.cbbBeginDate.getDate() != null) {
				condition += " and a.closeAccountDate >=? ";
				lsParam.add(CommonVars
						.getBeginDate(this.cbbBeginDate.getDate()));
			}
			if (this.cbbEndDate.getDate() != null) {
				condition += " and a.closeAccountDate <=? ";
				lsParam.add(CommonVars.getEndDate(this.cbbEndDate.getDate()));
			}
			list = fecavAction.findFecavBillStrikeByState(new Request(
					CommonVars.getCurrUser(), true), FecavState.CLOSE_ACCOUNT,
					condition, lsParam);
			break;
		}
		return list;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
