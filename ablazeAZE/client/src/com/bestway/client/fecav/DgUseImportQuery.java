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

public class DgUseImportQuery extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel lbMan = null;

	private JLabel lbFecavBillCode = null;

	private JTextField tfCustomsCode = null;

	private JTextField tfWhiteCode = null;

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

	private List lsResult = new ArrayList();

	private boolean isBefore = true;

	private boolean isOk = false;

	private JPanel pnSelect = null;

	private boolean isWhiteBill = false;

	private boolean isPnViseble = true;

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

	public DgUseImportQuery() {
		super();
		initialize();
	}

	public DgUseImportQuery(boolean isModal) {
		super(isModal);
		initialize();
	}

	public DgUseImportQuery(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgUseImportQuery(Dialog owner, boolean isModal) {
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
		this.setTitle("进口白单查询");
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
			lbEndDate.setBounds(new java.awt.Rectangle(84, 121, 15, 21));
			lbEndDate.setText("到");
			lbBeginDate = new JLabel();
			lbBeginDate.setBounds(new java.awt.Rectangle(34, 87, 64, 24));
			lbBeginDate.setText("申报日期从");
			lbFecavBillCode = new JLabel();
			lbFecavBillCode.setBounds(new java.awt.Rectangle(34, 25, 64, 23));
			lbFecavBillCode.setText("报关单号码");
			lbMan = new JLabel();
			lbMan.setBounds(new java.awt.Rectangle(34, 55, 64, 23));
			lbMan.setText("白单号码");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lbMan, null);
			jContentPane.add(lbFecavBillCode, null);
			jContentPane.add(getTfCustomsCode(), null);
			jContentPane.add(getTfWhiteCode(), null);
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
	private JTextField getTfCustomsCode() {
		if (tfCustomsCode == null) {
			tfCustomsCode = new JTextField();
			tfCustomsCode.setBounds(new java.awt.Rectangle(100, 24, 161, 25));
		}
		return tfCustomsCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfWhiteCode() {
		if (tfWhiteCode == null) {
			tfWhiteCode = new JTextField();
			tfWhiteCode.setBounds(new java.awt.Rectangle(100, 55, 161, 25));
		}
		return tfWhiteCode;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(100, 86, 161, 25));
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
			cbbEndDate.setBounds(new java.awt.Rectangle(100, 118, 161, 25));
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
			rbYes.setText("可核销");
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
			rbNo.setText("不可核销");
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
		if (isBefore && isPnViseble) {
			// this.setTitle("进口白单查询");
			// lbMan.setText("内部领单人");
			// lbBeginDate.setText("领单日期从");
			// rbYes.setText("已编辑");
			// rbNo.setText("未编辑");
			// rbAll.setText("全部");
			pnSelect.setVisible(true);
		} else {
			// this.setTitle("进口白单查询");
			// lbMan.setText("内部领单人");
			// lbBeginDate.setText("出口日期从");
			// rbYes.setText("已编辑");
			// rbNo.setText("未编辑");
			// rbAll.setText("全部");
			pnSelect.setVisible(false);
		}

	}

	private List getResultData() {
		List list = new ArrayList();
		String condition = "";
		List<Object> lsParam = new ArrayList<Object>();
		if (isBefore) {
			if (!this.tfCustomsCode.getText().trim().equals("")) {
				condition += " and a.customsDeclarationCode like ? ";
				lsParam.add("%" + this.tfCustomsCode.getText().trim() + "%");
			}
			if (!this.tfWhiteCode.getText().trim().equals("")) {
				condition += " and a.whiteBillNo like ? ";
				lsParam.add("%" + this.tfWhiteCode.getText().trim() + "%");
			}
			if (this.cbbBeginDate.getDate() != null) {
				condition += " and a.declareDate >=? ";
				lsParam.add(CommonVars
						.getBeginDate(this.cbbBeginDate.getDate()));
			}
			if (this.cbbEndDate.getDate() != null) {
				condition += " and a.declareDate <=? ";
				lsParam.add(CommonVars.getEndDate(this.cbbEndDate.getDate()));
			}
			if (isWhiteBill) {
				condition += " and a.whiteBillNo is not null ";
				if (this.rbYes.isSelected()) {
					condition += " and a.canStrike=? ";
					lsParam.add(true);
				} else if (this.rbNo.isSelected()) {
					condition += " and ( a.canStrike=? or a.canStrike is null ) ";
					lsParam.add(false);
				}
			} else {
				condition += " and (a.whiteBillNo is null or a.whiteBillNo=?) ";
				lsParam.add("");
			}
			list = fecavAction.findImpCustomsDeclarationNotCancel(new Request(
					CommonVars.getCurrUser(), true), condition, lsParam);
		} else {
			if (!this.tfCustomsCode.getText().trim().equals("")) {
				condition += " and a.customsDeclarationCode like ? ";
				lsParam.add("%" + this.tfCustomsCode.getText().trim() + "%");
			}
			if (!this.tfWhiteCode.getText().trim().equals("")) {
				condition += " and a.whiteBillNo like ? ";
				lsParam.add("%" + this.tfWhiteCode.getText().trim() + "%");
			}
			if (this.cbbBeginDate.getDate() != null) {
				condition += " and a.declareDate >=? ";
				lsParam.add(CommonVars
						.getBeginDate(this.cbbBeginDate.getDate()));
			}
			if (this.cbbEndDate.getDate() != null) {
				condition += " and a.declareDate <=? ";
				lsParam.add(CommonVars.getEndDate(this.cbbEndDate.getDate()));
			}
			list = fecavAction.findImpCustomsDeclarationIsCancel(new Request(
					CommonVars.getCurrUser(), true), condition, lsParam);
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
			pnSelect.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			pnSelect.setBounds(new java.awt.Rectangle(25, 152, 251, 26));
			pnSelect.add(getRbAll(), null);
			pnSelect.add(getRbNo(), null);
			pnSelect.add(getRbYes(), null);
		}
		return pnSelect;
	}

	public void setPnViseble(boolean isPnViseble) {
		this.isPnViseble = isPnViseble;
	}

	public boolean isWhiteBill() {
		return isWhiteBill;
	}

	public void setWhiteBill(boolean isWhiteBill) {
		this.isWhiteBill = isWhiteBill;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
