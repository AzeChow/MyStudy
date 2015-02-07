package com.bestway.client.custom.common;

import java.awt.Dialog;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgCustomsDeclarationQueryCondition extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JTextField tfCustomsDeclarationCode = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel3 = null;

	private JTextField tfFecavBillCode = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JLabel jLabel4 = null;

	private JComboBox cbbImpExpType = null;

	private int impExpFlag = ImpExpFlag.IMPORT;

	private JLabel jLabel5 = null;

	private boolean isOk = false;

	private String emsNo = "";  //  @jve:decl-index=0:

	private JComboBox cbbTradeMode = null;

	private List lsResult = new ArrayList();

	protected BaseEncAction baseEncAction = null;

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public List getLsResult() {
		return lsResult;
	}

	public void setLsResult(List lsResult) {
		this.lsResult = lsResult;
	}

	public BaseEncAction getBaseEncAction() {
		return baseEncAction;
	}

	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}

	public int getImpExpFlag() {
		return impExpFlag;
	}

	public void setImpExpFlag(int impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	public DgCustomsDeclarationQueryCondition() {
		super();
		initialize();
	}

	public DgCustomsDeclarationQueryCondition(boolean isModal) {
		super(isModal);
		initialize();
	}

	public DgCustomsDeclarationQueryCondition(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgCustomsDeclarationQueryCondition(Dialog owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public void setVisible(boolean b) {
		if (b) {
			this.initUIComponents();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(385, 302));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("报关单查询");
		this.setContentPane(getJContentPane());

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(57,154,68,20));
			jLabel5.setText("贸易方式");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(57,123,68,20));
			jLabel4.setText("进出口类型");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(57,184,68,20));
			jLabel3.setText("核销单号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(110,92,16,24));
			jLabel2.setText("\u5230");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(57,62,68,20));
			jLabel1.setText("\u7533\u62a5\u65e5\u671f \u4ece");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(57,32,68,20));
			jLabel.setText("报关单号 ");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfCustomsDeclarationCode(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getTfFecavBillCode(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getCbbImpExpType(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getCbbTradeMode(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsDeclarationCode() {
		if (tfCustomsDeclarationCode == null) {
			tfCustomsDeclarationCode = new JTextField();
			tfCustomsDeclarationCode.setBounds(new java.awt.Rectangle(126, 31,
					201, 23));
		}
		return tfCustomsDeclarationCode;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(126, 60, 201, 23));
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
			cbbEndDate.setBounds(new java.awt.Rectangle(126, 91, 201, 23));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFecavBillCode() {
		if (tfFecavBillCode == null) {
			tfFecavBillCode = new JTextField();
			tfFecavBillCode
					.setBounds(new java.awt.Rectangle(126, 184, 201, 23));
		}
		return tfFecavBillCode;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(65, 223, 77, 24));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String condition = "";
					List<Object> parameters = new ArrayList<Object>();
					if (emsNo != null && !"".equals(emsNo.trim())) {
						condition += " and a.emsHeadH2k=? ";
						parameters.add(emsNo);
					}
					if (!tfCustomsDeclarationCode.getText().trim().equals("")) {
						condition += " and a.customsDeclarationCode like ? ";
						parameters.add("%"
								+ tfCustomsDeclarationCode.getText().trim()
								+ "%");
					}
					if (cbbBeginDate.getDate() != null) {
						condition += " and (a.declarationDate>=?  or a.declarationDate is null) ";
						parameters.add(CommonVars.getBeginDate(cbbBeginDate
								.getDate()));
					}
					if (cbbEndDate.getDate() != null) {
						condition += " and (a.declarationDate<=?  or a.declarationDate is null) ";
						parameters.add(CommonVars.getEndDate(cbbEndDate
								.getDate()));
					}
					if (cbbImpExpType.getSelectedIndex() >= 0) {
						condition += " and a.impExpType=? ";
						parameters.add(Integer
								.valueOf(((ItemProperty) cbbImpExpType
										.getSelectedItem()).getCode()));
					}
					if (cbbTradeMode.getSelectedIndex() >= 0) {
						condition += " and a.tradeMode=? ";
						parameters
								.add(((Trade) cbbTradeMode.getSelectedItem()));
					}
					if (!tfFecavBillCode.getText().trim().equals("")) {
						condition += " and a.authorizeFile like ? ";
						parameters.add("%" + tfFecavBillCode.getText().trim()
								+ "%");
					}
					if (impExpFlag == ImpExpFlag.IMPORT) {
						lsResult = baseEncAction.findImportCustomsDeclaration(
								new Request(CommonVars.getCurrUser()),
								condition, parameters);
					} else if (impExpFlag == ImpExpFlag.EXPORT) {
						lsResult = baseEncAction.findExportCustomsDeclaration(
								new Request(CommonVars.getCurrUser()),
								condition, parameters);
					}
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
			btnCancel.setBounds(new java.awt.Rectangle(230, 223, 77, 24));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(new java.awt.Rectangle(126, 122, 201, 23));
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(new java.awt.Rectangle(126, 153, 201, 23));
		}
		return cbbTradeMode;
	}

	private void initUIComponents() {
		// 初始化进口类型
		this.cbbImpExpType.removeAllItems();
		if (impExpFlag == ImpExpFlag.IMPORT) {
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
		} else if (impExpFlag == ImpExpFlag.EXPORT) {
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_EXPORT).toString(), "成品出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_EXPORT).toString(), "转厂出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_MATERIEL_EXPORT).toString(), "退料出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.REWORK_EXPORT).toString(), "返工复出"));
		}
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbImpExpType.setSelectedIndex(-1);

		// 初始化贸易方式
		this.cbbTradeMode.setModel(CustomBaseModel.getInstance()
				.getTradeModel());
		this.cbbTradeMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTradeMode);
		cbbTradeMode.setSelectedIndex(-1);
		
		this.cbbBeginDate.setDate(CommonVars.getBeginDate());
        this.cbbEndDate.setDate(new Date());
	}
} // @jve:decl-index=0:visual-constraint="10,10"
