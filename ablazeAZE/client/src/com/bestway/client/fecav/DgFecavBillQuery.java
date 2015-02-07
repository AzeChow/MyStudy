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

public class DgFecavBillQuery extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel lbMan = null;

	private JLabel lbFecavBillCode = null;

	private JTextField tfFecavBillCode = null;

	private JTextField tfObtainer = null;

	private JLabel lbBeginDate = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel lbEndDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup = null;

	private FecavAction fecavAction = null;

	private int fecavState = FecavState.OUTER_OBTAIN;

	private List lsResult = new ArrayList();

	private boolean isBefore = true;

	private boolean isOk = false;
	
	private boolean isBlankOut=false; // 是否遗失作废

	private JPanel pnSelect = null;

	public void setBlankOut(boolean isBlankOut) {
		this.isBlankOut = isBlankOut;
	}

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

	public DgFecavBillQuery() {
		super();
		initialize();
	}

	public DgFecavBillQuery(boolean isModal) {
		super(isModal);
		initialize();
	}

	public DgFecavBillQuery(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgFecavBillQuery(Dialog owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(329, 259));
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
			lbFecavBillCode.setText("核销单号码");
			lbMan = new JLabel();
			lbMan.setBounds(new java.awt.Rectangle(34, 56, 64, 23));
			lbMan.setText("领单人　");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lbMan, null);
			jContentPane.add(lbFecavBillCode, null);
			jContentPane.add(getTfFecavBillCode(), null);
			jContentPane.add(getTfObtainer(), null);
			jContentPane.add(lbBeginDate, null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(lbEndDate, null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getPnSelect(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFecavBillCode() {
		if (tfFecavBillCode == null) {
			tfFecavBillCode = new JTextField();
			tfFecavBillCode.setBounds(new java.awt.Rectangle(100, 24, 161, 25));
		}
		return tfFecavBillCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfObtainer() {
		if (tfObtainer == null) {
			tfObtainer = new JTextField();
			tfObtainer.setBounds(new java.awt.Rectangle(100, 56, 161, 25));
		}
		return tfObtainer;
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
			btnOk.setBounds(new java.awt.Rectangle(68, 185, 66, 23));
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
			btnCancel.setBounds(new java.awt.Rectangle(168, 185, 66, 23));
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
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbYes() {
		if (rbYes == null) {
			rbYes = new JRadioButton();
			rbYes.setText("内部已领用");
			rbYes.setBounds(new java.awt.Rectangle(1, 4, 91, 22));
		}
		return rbYes;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNo() {
		if (rbNo == null) {
			rbNo = new JRadioButton();
			rbNo.setText("内部未领用");
			rbNo.setBounds(new java.awt.Rectangle(92, 4, 90, 22));
		}
		return rbNo;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setText("全部");
			rbAll.setSelected(true);
			rbAll.setBounds(new java.awt.Rectangle(189, 4, 62, 22));
		}
		return rbAll;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(rbYes);
			buttonGroup.add(rbNo);
			buttonGroup.add(rbAll);
		}
		return buttonGroup;
	}

	private void initUIComponents() {
		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
		if(isBlankOut){
			this.setTitle("核销单遗失作废查询");
			lbMan.setText("作废人");
			lbBeginDate.setText("作废日期从");
			// rbYes.setText("已使用");
			// rbNo.setText("未使用");
			// rbAll.setText("全部");
			pnSelect.setVisible(false);
			return;
		}
		switch (fecavState) {
		case FecavState.OUTER_OBTAIN:// 外部领单查询
			this.setTitle("外部领单查询");
			lbMan.setText("外部领单人");
			lbBeginDate.setText("领单日期从");
			rbYes.setText("内部已领用");
			rbNo.setText("内部未领用");
			rbAll.setText("全部");
			pnSelect.setVisible(true);
			break;
		case FecavState.INNER_OBTAIN:// 内部领单查询
			this.setTitle("内部领单查询");
			lbMan.setText("内部领单人");
			lbBeginDate.setText("领单日期从");
			rbYes.setText("已使用");
			rbNo.setText("未使用");
			rbAll.setText("全部");
			pnSelect.setVisible(true);
			break;
		case FecavState.USED:// 核销单使用
			if (isBefore) {
				this.setTitle("核销单使用查询");
				lbMan.setText("报关单号");
				lbBeginDate.setText("申报日期从");
				rbYes.setText("已编辑");
				rbNo.setText("未编辑");
				rbAll.setText("全部");
				pnSelect.setVisible(true);
			} else {
				this.setTitle("核销单使用查询");
				lbMan.setText("报关单号");
				lbBeginDate.setText("出口日期从");
				// rbYes.setText("已编辑");
				// rbNo.setText("未编辑");
				// rbAll.setText("全部");
				pnSelect.setVisible(false);
			}
			break;
		case FecavState.DEBENTURE_SIGN_IN:// 退税联签收查询
			this.setTitle("退税联签收查询");
			lbMan.setText("签收人");
			lbBeginDate.setText("签收日期从");
			// rbYes.setText("已使用");
			// rbNo.setText("未使用");
			// rbAll.setText("全部");
			pnSelect.setVisible(false);
			break;
		case FecavState.HAND_IN_BILL:// 核销单交单查询
			this.setTitle("核销单交单查询");
			lbMan.setText("交单人");
			lbBeginDate.setText("交单日期从");
			// rbYes.setText("已使用");
			// rbNo.setText("未使用");
			// rbAll.setText("全部");
			pnSelect.setVisible(false);
			break;
//		case FecavState.:// 核销单交单查询
//			this.setTitle("退税联签收查询");
//			lbMan.setText("签收人");
//			lbBeginDate.setText("签收日期从");
//			// rbYes.setText("已使用");
//			// rbNo.setText("未使用");
//			// rbAll.setText("全部");
//			pnSelect.setVisible(false);
//			break;
		}		
	}

	private List getResultData() {
		List list = new ArrayList();
		String condition = "";
		List<Object> lsParam = new ArrayList<Object>();
		if(isBlankOut){
			if (!this.tfFecavBillCode.getText().trim().equals("")) {
				condition += " and a.code like ? ";
				lsParam.add("%" + this.tfFecavBillCode.getText().trim() + "%");
			}
			if (!this.tfObtainer.getText().trim().equals("")) {
				condition += " and a.blankOutMan like ? ";
				lsParam.add("%" + this.tfObtainer.getText().trim() + "%");
			}
			if (this.cbbBeginDate.getDate() != null) {
				condition += " and a.blankOutDate >=? ";
				lsParam.add(CommonVars
						.getBeginDate(this.cbbBeginDate.getDate()));
			}
			if (this.cbbEndDate.getDate() != null) {
				condition += " and a.blankOutDate <=? ";
				lsParam.add(CommonVars.getEndDate(this.cbbEndDate.getDate()));
			}
//			if (this.rbYes.isSelected()) {
//				condition += " and a.billState !=? ";
//				lsParam.add(FecavState.OUTER_OBTAIN);
//			} else if (this.rbNo.isSelected()) {
//				condition += " and a.billState =? ";
//				lsParam.add(FecavState.OUTER_OBTAIN);
//			}
			list = fecavAction.findIsBlankOutFecavBill(new Request(CommonVars
					.getCurrUser(), true), condition, lsParam);
			return list;
		}
		switch (fecavState) {
		case FecavState.OUTER_OBTAIN:// 外部领单查询
			if (!this.tfFecavBillCode.getText().trim().equals("")) {
				condition += " and a.code like ? ";
				lsParam.add("%" + this.tfFecavBillCode.getText().trim() + "%");
			}
			if (!this.tfObtainer.getText().trim().equals("")) {
				condition += " and a.outerObtain like ? ";
				lsParam.add("%" + this.tfObtainer.getText().trim() + "%");
			}
			if (this.cbbBeginDate.getDate() != null) {
				condition += " and a.outerObtainDate >=? ";
				lsParam.add(CommonVars
						.getBeginDate(this.cbbBeginDate.getDate()));
			}
			if (this.cbbEndDate.getDate() != null) {
				condition += " and a.outerObtainDate <=? ";
				lsParam.add(CommonVars.getEndDate(this.cbbEndDate.getDate()));
			}
			if (this.rbYes.isSelected()) {
				condition += " and a.billState !=? ";
				lsParam.add(FecavState.OUTER_OBTAIN);
			} else if (this.rbNo.isSelected()) {
				condition += " and a.billState =? ";
				lsParam.add(FecavState.OUTER_OBTAIN);
			}
			list = fecavAction.findFecavBill(new Request(CommonVars
					.getCurrUser(), true), condition, lsParam);
			break;
		case FecavState.INNER_OBTAIN:// 内部领单查询
			if (!this.tfFecavBillCode.getText().trim().equals("")) {
				condition += " and a.code like ? ";
				lsParam.add("%" + this.tfFecavBillCode.getText().trim() + "%");
			}
			if (!this.tfObtainer.getText().trim().equals("")) {
				condition += " and a.innerObtain like ? ";
				lsParam.add("%" + this.tfObtainer.getText().trim() + "%");
			}
			if (this.cbbBeginDate.getDate() != null) {
				condition += " and a.innerObtainDate >=? ";
				lsParam.add(CommonVars
						.getBeginDate(this.cbbBeginDate.getDate()));
			}
			if (this.cbbEndDate.getDate() != null) {
				condition += " and a.innerObtainDate <=? ";
				lsParam.add(CommonVars.getEndDate(this.cbbEndDate.getDate()));
			}
			if (this.rbYes.isSelected()) {
				condition += " and a.billState !=? ";
				lsParam.add(FecavState.INNER_OBTAIN);
			} else if (this.rbNo.isSelected()) {
				condition += " and a.billState =? ";
				lsParam.add(FecavState.INNER_OBTAIN);
			}
			list = fecavAction.findFecavBillNotOuterObtain(new Request(
					CommonVars.getCurrUser(), true), condition, lsParam);
			break;
		case FecavState.USED:// 核销单使用
			if (isBefore) {
				if (!this.tfFecavBillCode.getText().trim().equals("")) {
					condition += " and a.code like ? ";
					lsParam.add("%" + this.tfFecavBillCode.getText().trim()
							+ "%");
				}
				if (!this.tfObtainer.getText().trim().equals("")) {
					condition += " and a.customsDeclarationCode like ? ";
					lsParam.add("%" + this.tfObtainer.getText().trim() + "%");
				}
				if (this.cbbBeginDate.getDate() != null) {
					condition += " and a.declareDate >=? ";
					lsParam.add(CommonVars.getBeginDate(this.cbbBeginDate
							.getDate()));
				}
				if (this.cbbEndDate.getDate() != null) {
					condition += " and a.declareDate <=? ";
					lsParam.add(CommonVars
							.getEndDate(this.cbbEndDate.getDate()));
				}
				if (this.rbYes.isSelected()) {
					condition += " and a.exportDate is not null ";
				} else if (this.rbNo.isSelected()) {
					condition += " and a.exportDate is null ";
				}
				list = fecavAction.findFecavBillByUsedBefore(new Request(
						CommonVars.getCurrUser(), true), condition, lsParam);
			} else {
				if (!this.tfFecavBillCode.getText().trim().equals("")) {
					condition += " and a.code like ? ";
					lsParam.add("%" + this.tfFecavBillCode.getText().trim()
							+ "%");
				}
				if (!this.tfObtainer.getText().trim().equals("")) {
					condition += " and a.customsDeclarationCode like ? ";
					lsParam.add("%" + this.tfObtainer.getText().trim() + "%");
				}
				if (this.cbbBeginDate.getDate() != null) {
					condition += " and a.exportDate >=? ";
					lsParam.add(CommonVars.getBeginDate(this.cbbBeginDate
							.getDate()));
				}
				if (this.cbbEndDate.getDate() != null) {
					condition += " and a.exportDate <=? ";
					lsParam.add(CommonVars
							.getEndDate(this.cbbEndDate.getDate()));
				}
				// if (this.rbYes.isSelected()) {
				// condition += " and a.exportDate is not null ";
				// } else if (this.rbNo.isSelected()) {
				// condition += " and a.exportDate is null ";
				// }
				list = fecavAction.findFecavBillByUsedAfter(new Request(CommonVars
						.getCurrUser(), true), condition, lsParam);
			}
			break;
		case FecavState.DEBENTURE_SIGN_IN:// 退税联签收查询
			if (!this.tfFecavBillCode.getText().trim().equals("")) {
				condition += " and a.code like ? ";
				lsParam.add("%" + this.tfFecavBillCode.getText().trim() + "%");
			}
			if (!this.tfObtainer.getText().trim().equals("")) {
				condition += " and a.debentureSignInMan like ? ";
				lsParam.add("%" + this.tfObtainer.getText().trim() + "%");
			}
			if (this.cbbBeginDate.getDate() != null) {
				condition += " and a.debentureSignInDate >=? ";
				lsParam.add(CommonVars
						.getBeginDate(this.cbbBeginDate.getDate()));
			}
			if (this.cbbEndDate.getDate() != null) {
				condition += " and a.debentureSignInDate <=? ";
				lsParam.add(CommonVars.getEndDate(this.cbbEndDate.getDate()));
			}
			// if (this.rbYes.isSelected()) {
			// condition += " and a.billState !=? ";
			// lsParam.add(FecavState.INNER_OBTAIN);
			// } else if (this.rbNo.isSelected()) {
			// condition += " and a.billState =? ";
			// lsParam.add(FecavState.INNER_OBTAIN);
			// }
			list = fecavAction.findFecavBillByState(new Request(CommonVars
					.getCurrUser(), true), FecavState.DEBENTURE_SIGN_IN,
					condition, lsParam);
			break;
		case FecavState.HAND_IN_BILL:// 核销单交单查询
			if (!this.tfFecavBillCode.getText().trim().equals("")) {
				condition += " and a.code like ? ";
				lsParam.add("%" + this.tfFecavBillCode.getText().trim() + "%");
			}
			if (!this.tfObtainer.getText().trim().equals("")) {
				condition += " and a.handInBillMan like ? ";
				lsParam.add("%" + this.tfObtainer.getText().trim() + "%");
			}
			if (this.cbbBeginDate.getDate() != null) {
				condition += " and a.handInBillDate >=? ";
				lsParam.add(CommonVars
						.getBeginDate(this.cbbBeginDate.getDate()));
			}
			if (this.cbbEndDate.getDate() != null) {
				condition += " and a.handInBillDate <=? ";
				lsParam.add(CommonVars.getEndDate(this.cbbEndDate.getDate()));
			}
			// if (this.rbYes.isSelected()) {
			// condition += " and a.billState !=? ";
			// lsParam.add(FecavState.INNER_OBTAIN);
			// } else if (this.rbNo.isSelected()) {
			// condition += " and a.billState =? ";
			// lsParam.add(FecavState.INNER_OBTAIN);
			// }
			list = fecavAction.findFecavBillNotStrike(new Request(CommonVars
					.getCurrUser(), true),
					condition, lsParam);
			break;
		}
		return list;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnSelect() {
		if (pnSelect == null) {
			pnSelect = new JPanel();
			pnSelect.setLayout(null);
			pnSelect.setBounds(new java.awt.Rectangle(33, 152, 249, 29));
			pnSelect.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			pnSelect.add(getRbAll(), null);
			pnSelect.add(getRbNo(), null);
			pnSelect.add(getRbYes(), null);
		}
		return pnSelect;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
