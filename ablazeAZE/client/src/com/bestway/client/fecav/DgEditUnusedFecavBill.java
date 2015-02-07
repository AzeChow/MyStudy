package com.bestway.client.fecav;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.Request;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.contractexe.action.DzscContractExeAction;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fecav.entity.TempCustomsDeclarationInfoForFecav;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Rectangle;
import java.util.List;

public class DgEditUnusedFecavBill extends JDialogBase {

	private JPanel						jContentPane				= null;

	private JLabel						lbFecavCode					= null;

	private JLabel						jLabel						= null;

	private JLabel						jLabel1						= null;

	private JLabel						jLabel2						= null;

	private JLabel						jLabel3						= null;

	private JLabel						jLabel4						= null;

	private JLabel						jLabel5						= null;

	private JLabel						jLabel6						= null;

	private JTextField					tfFecavCode					= null;

	private JTextField					tfCustomsDeclarationCode	= null;

	private JTextField					tfContractNo				= null;

	private JTextField					tfEmsNo						= null;

	private JTextField					tfCurr						= null;

	private JCheckBox					cbPrintWhite				= null;

	private JCheckBox					cbPrintYellow				= null;

	private JButton						btnOk						= null;

	private JButton						btnCancel					= null;

	private Curr						curr						= null;  //  @jve:decl-index=0:

	private FecavBill					fecavBill					= null;  //  @jve:decl-index=0:

	private String						customsDeclarationId		= null;

	private JCalendarComboBox			cbbExportDate				= null;

	private JCalendarComboBox			cbbDeclareDate				= null;

	private JCustomFormattedTextField	tfTotalMoney				= null;

	private FecavAction					fecavAction					= null;

	private boolean						isOk						= false;

	private JLabel jLabel7 = null;

	private JButton btnCustomsDeclaration = null;
	private TempCustomsDeclarationInfoForFecav obj =null;
	private BaseEncAction baseEncAction =null;

	/**
	 * This method initializes
	 * 
	 */
	public DgEditUnusedFecavBill() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
		baseEncAction = (ContractExeAction) CommonVars.getApplicationContext()
		.getBean("contractExeAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			showData();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(395,361));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("编辑核销单");
		this.setContentPane(getJContentPane());

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(58,290,273,24));
			jLabel7.setForeground(java.awt.Color.blue);
			jLabel7.setText("黄单是退税用，白单是核销用");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(57, 48, 78, 18));
			jLabel6.setText("报关单号");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(57, 180, 78, 18));
			jLabel5.setText("币别");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(57, 129, 78, 18));
			jLabel4.setText("合同号码");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(57, 154, 78, 18));
			jLabel3.setText("手册号码");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(57, 102, 78, 18));
			jLabel2.setText("申报日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(57, 208, 78, 18));
			jLabel1.setText("报关单总金额");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(57, 76, 78, 18));
			jLabel.setText("出口日期");
			lbFecavCode = new JLabel();
			lbFecavCode.setBounds(new java.awt.Rectangle(57, 23, 78, 18));
			lbFecavCode.setText("核销单号");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lbFecavCode, null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getTfFecavCode(), null);
			jContentPane.add(getTfCustomsDeclarationCode(), null);
			jContentPane.add(getTfContractNo(), null);
			jContentPane.add(getTfEmsNo(), null);
			jContentPane.add(getTfCurr(), null);
			jContentPane.add(getCbPrintWhite(), null);
			jContentPane.add(getCbPrintYellow(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getCbbExportDate(), null);
			jContentPane.add(getCbbDeclareDate(), null);
			jContentPane.add(getTfTotalMoney(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getBtnCustomsDeclaration(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes tfFecavCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFecavCode() {
		if (tfFecavCode == null) {
			tfFecavCode = new JTextField();
			tfFecavCode.setBounds(new java.awt.Rectangle(137, 22, 195, 23));
			tfFecavCode.setBackground(java.awt.Color.white);
			tfFecavCode.setEditable(false);
		}
		return tfFecavCode;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsDeclarationCode() {
		if (tfCustomsDeclarationCode == null) {
			tfCustomsDeclarationCode = new JTextField();
			tfCustomsDeclarationCode.setBounds(new Rectangle(137, 48, 175, 23));
			tfCustomsDeclarationCode.setBackground(java.awt.Color.white);
			tfCustomsDeclarationCode.setEditable(false);
		}
		return tfCustomsDeclarationCode;
	}

	/**
	 * This method initializes tfContractNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContractNo() {
		if (tfContractNo == null) {
			tfContractNo = new JTextField();
			tfContractNo.setBounds(new java.awt.Rectangle(137, 127, 195, 23));
			tfContractNo.setBackground(java.awt.Color.white);
			tfContractNo.setEditable(false);
		}
		return tfContractNo;
	}

	/**
	 * This method initializes tfEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new java.awt.Rectangle(137, 153, 195, 23));
			tfEmsNo.setBackground(java.awt.Color.white);
			tfEmsNo.setEditable(false);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes tfCurr
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCurr() {
		if (tfCurr == null) {
			tfCurr = new JTextField();
			tfCurr.setBounds(new java.awt.Rectangle(137, 179, 195, 23));
			tfCurr.setBackground(java.awt.Color.white);
			tfCurr.setEditable(false);
		}
		return tfCurr;
	}

	/**
	 * This method initializes cbPrintWhite
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPrintWhite() {
		if (cbPrintWhite == null) {
			cbPrintWhite = new JCheckBox();
			cbPrintWhite.setBounds(new java.awt.Rectangle(56, 229, 140, 24));
			cbPrintWhite.setText("是否打印白单");
		}
		return cbPrintWhite;
	}

	/**
	 * This method initializes cbPrintYellow
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPrintYellow() {
		if (cbPrintYellow == null) {
			cbPrintYellow = new JCheckBox();
			cbPrintYellow.setBounds(new java.awt.Rectangle(196, 229, 140, 24));
			cbPrintYellow.setText("是否打印黄单");
		}
		return cbPrintYellow;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(58, 263, 85, 23));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveFecavBill();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new java.awt.Rectangle(233, 263, 85, 23));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	public FecavBill getFecavBill() {
		return fecavBill;
	}

	public void setFecavBill(FecavBill fecavBill) {
		this.fecavBill = fecavBill;
	}

	private void fillData() {
		if (this.fecavBill == null) {
			return;
		}
		// this.fecavBill.setCustomsDeclarationId(this.customsDeclarationId);
		this.fecavBill.setCustomsDeclarationCode(this.tfCustomsDeclarationCode
				.getText());
		this.fecavBill.setExportDate(this.cbbExportDate.getDate());
		this.fecavBill.setDeclareDate(this.cbbDeclareDate.getDate());
		this.fecavBill.setContractNo(this.tfContractNo.getText());
		this.fecavBill.setEmsNo(this.tfEmsNo.getText());
		this.fecavBill.setCurr(this.curr);
		if (this.tfTotalMoney.getValue() != null) {
			this.fecavBill.setTotalPrice(Double.valueOf(this.tfTotalMoney
					.getValue().toString()));
		}
		this.fecavBill.setIsPrintWhite(this.cbPrintWhite.isSelected());
		this.fecavBill.setIsPrintYellow(this.cbPrintYellow.isSelected());
	}

	private void showData() {
		if (this.fecavBill == null) {
			return;
		}
		this.tfCustomsDeclarationCode.setText(this.fecavBill
				.getCustomsDeclarationCode());
		this.tfFecavCode.setText(this.fecavBill.getCode());
		this.cbbDeclareDate.setValue(this.fecavBill.getDeclareDate());
		this.cbbExportDate.setValue(this.fecavBill.getExportDate());
		this.tfContractNo.setText(this.fecavBill.getContractNo());
		this.tfEmsNo.setText(this.fecavBill.getEmsNo());
		this.curr = this.fecavBill.getCurr();
		this.tfCurr.setText(this.fecavBill.getCurr() == null ? ""
				: this.fecavBill.getCurr().getName());
		this.tfTotalMoney.setValue(this.fecavBill.getTotalPrice());
		this.cbPrintWhite
				.setSelected(this.fecavBill.getIsPrintWhite() == null ? false
						: this.fecavBill.getIsPrintWhite());
		this.cbPrintYellow
				.setSelected(this.fecavBill.getIsPrintYellow() == null ? false
						: this.fecavBill.getIsPrintYellow());
	}

	/**
	 * This method initializes cbbExportDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbExportDate() {
		if (cbbExportDate == null) {
			cbbExportDate = new JCalendarComboBox();
			cbbExportDate.setDate(null);
			cbbExportDate.setBounds(new java.awt.Rectangle(137, 75, 195, 23));
			// cbbExportDate.setEnabled(false);
		}
		return cbbExportDate;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbDeclareDate() {
		if (cbbDeclareDate == null) {
			cbbDeclareDate = new JCalendarComboBox();
			cbbDeclareDate.setDate(null);
			cbbDeclareDate.setBounds(new java.awt.Rectangle(137, 101, 195, 23));
			cbbDeclareDate.setEnabled(false);
		}
		return cbbDeclareDate;
	}

	/**
	 * This method initializes tfTotalMoney
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfTotalMoney() {
		if (tfTotalMoney == null) {
			tfTotalMoney = new JCustomFormattedTextField();
			tfTotalMoney.setBounds(new java.awt.Rectangle(137, 206, 195, 23));
			tfTotalMoney.setBackground(java.awt.Color.white);
			tfTotalMoney.setEditable(false);
		}
		return tfTotalMoney;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * 保存核销单
	 * 
	 */
	private void saveFecavBill() {
		if (fecavBill == null) {
			return;
		}
		if (cbbExportDate.getDate() != null) {
			if (validateData(fecavBill.getCustomsDeclarationId()) == false) {
				return;
			}
			fillData();
			fecavAction.saveFecavBillAndUpdateCustomsDeclaration(new Request(
					CommonVars.getCurrUser()), fecavBill);
			saveExpCustomDeclaration(fecavBill);
			isOk = true;
		}else{
			JOptionPane.showMessageDialog(this, "请填写出口日期!",
					"提示!", 1);
			return ;
		}
		dispose();
	}
	/**
	 * 保存出口报关单，把核销单号反写到出口报关单
	 * 
	 */
	private void saveExpCustomDeclaration(FecavBill fecavBill) {
		if (fecavBill != null) {
			BaseCustomsDeclaration customsDeclaration = baseEncAction
					.findCustomsDeclarationById(new Request(CommonVars
							.getCurrUser()), fecavBill
							.getCustomsDeclarationId());
			customsDeclaration.setAuthorizeFile(fecavBill.getCode());
			baseEncAction.saveCustomsDeclaration(new Request(CommonVars
					.getCurrUser()), customsDeclaration);

		}
	}

	/**
	 * 验证报关单Id对应的这条报关单是否已经生效
	 * 
	 * @param customsDeclarationId ==
	 *            报关单id
	 * @return
	 */
	private boolean validateData(String customsDeclarationId) {
		if (customsDeclarationId == null || "".equals(customsDeclarationId)) {
			return false;
		}
		BaseEncAction baseEncAction = (DzscContractExeAction) CommonVars
				.getApplicationContext().getBean("dzscContractExeAction");
		BaseCustomsDeclaration baseCustomsDeclaration = baseEncAction
				.findAllCustomsDeclarationById(new Request(CommonVars.getCurrUser(),
						true), customsDeclarationId);
		if(baseCustomsDeclaration==null){
			JOptionPane.showMessageDialog(this,
					"找不到该核销单相关联的报关单 !!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (baseCustomsDeclaration.getEffective() == null
				|| baseCustomsDeclaration.getEffective() == false) {
			JOptionPane.showMessageDialog(this,
					"使用该核销单的报关单没有生效!!\n请生效该报关单后再填入 [出口日期]  !!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * This method initializes btnCustomsDeclaration	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCustomsDeclaration() {
		if (btnCustomsDeclaration == null) {
			btnCustomsDeclaration = new JButton();
			btnCustomsDeclaration.setBounds(new Rectangle(313, 48, 19, 23));
			btnCustomsDeclaration.setText("修改");
			btnCustomsDeclaration.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					upData();
				}
			});
		}
		return btnCustomsDeclaration;
	}
	private void upData(){
		 obj = (TempCustomsDeclarationInfoForFecav)FecavQuery
		.getInstance().findExportCustomsDeclaration();
		if(obj!=null){
			tfCustomsDeclarationCode.setText(obj.getCustomsDeclarationCode());
			cbbDeclareDate.setValue(obj.getDeclareDate());
			tfContractNo.setText(obj.getContractNo());
			tfEmsNo.setText(obj.getEmsNo());
			tfCurr.setText(obj.getCurr()==null?"":obj.getCurr().getName());
			tfTotalMoney.setValue(obj.getTotalPrice());
			fecavBill.setCustomsDeclarationId(obj.getCustomsDeclarationId());
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
