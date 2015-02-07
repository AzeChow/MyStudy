package com.bestway.client.fecav;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.ImpCustomsDeclaration;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgEditImpCustomsDeclaration extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JTextField tfCustomsDeclarationCode = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextField tfContractNo = null;

	private JTextField tfEmsNo = null;

	private JLabel jLabel3 = null;

	private JTextField tfTotalMoney = null;

	private JLabel jLabel4 = null;

	private JCustomFormattedTextField tfStrikeMoney = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private boolean isOk = false;

	private boolean isWriteInWhiteNo = false;

	private ImpCustomsDeclaration impCustomsDeclaration = null;

	private FecavAction fecavAction = null;

	private JTextField tfWhiteNo = null;

	private JLabel jLabel5 = null;

	public ImpCustomsDeclaration getImpCustomsDeclaration() {
		return impCustomsDeclaration;
	}

	public void setImpCustomsDeclaration(
			ImpCustomsDeclaration impCustomsDeclaration) {
		this.impCustomsDeclaration = impCustomsDeclaration;
	}

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgEditImpCustomsDeclaration() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			showData();
			tfStrikeMoney.setEditable(!isWriteInWhiteNo);
			tfWhiteNo.setEditable(isWriteInWhiteNo);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(404, 290));
		this.setTitle("修改进口报关单冲销");
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(48, 147, 79, 18));
			jLabel4.setText("冲销金额");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(48, 115, 79, 18));
			jLabel3.setText("报关单总金额");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(48, 88, 79, 18));
			jLabel2.setText("手册编号");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(48, 58, 79, 18));
			jLabel1.setText("合同号");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(48, 32, 79, 18));
			jLabel.setText("进口报关单号 ");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jLabel5.setBounds(48, 174, 76, 22);
			jLabel5.setText("白单号");
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfCustomsDeclarationCode(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfContractNo(), null);
			jContentPane.add(getTfEmsNo(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getTfTotalMoney(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getTfStrikeMoney(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getTfWhiteNo(), null);
			jContentPane.add(jLabel5, null);
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
			tfCustomsDeclarationCode.setBounds(new java.awt.Rectangle(129, 30,
					212, 23));
			tfCustomsDeclarationCode.setBackground(java.awt.Color.white);
			tfCustomsDeclarationCode.setEditable(false);
		}
		return tfCustomsDeclarationCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContractNo() {
		if (tfContractNo == null) {
			tfContractNo = new JTextField();
			tfContractNo.setBounds(new java.awt.Rectangle(129, 58, 212, 23));
			tfContractNo.setBackground(java.awt.Color.white);
			tfContractNo.setEditable(false);
		}
		return tfContractNo;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new java.awt.Rectangle(129, 87, 212, 23));
			tfEmsNo.setBackground(java.awt.Color.white);
			tfEmsNo.setEditable(false);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTotalMoney() {
		if (tfTotalMoney == null) {
			tfTotalMoney = new JTextField();
			tfTotalMoney.setBounds(new java.awt.Rectangle(129, 116, 212, 23));
			tfTotalMoney.setBackground(java.awt.Color.white);
			tfTotalMoney.setEditable(false);
		}
		return tfTotalMoney;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfStrikeMoney() {
		if (tfStrikeMoney == null) {
			tfStrikeMoney = new JCustomFormattedTextField();
			tfStrikeMoney.setBounds(new java.awt.Rectangle(129, 144, 212, 23));
			tfStrikeMoney.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfStrikeMoney;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(48, 213, 85, 25));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(tfWhiteNo.getText()!=null&&tfWhiteNo.getText().length()!=18){
						if(JOptionPane.showConfirmDialog(DgEditImpCustomsDeclaration.this, 
								"白单号长度不够18位,你确定要保存吗?","提示",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
							fillData();
							impCustomsDeclaration = fecavAction
									.saveImpCustomsDeclaration(new Request(
											CommonVars.getCurrUser()),
											impCustomsDeclaration);
							isOk = true;
							dispose();
						}
					}
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
			btnCancel.setBounds(new java.awt.Rectangle(256, 213, 85, 25));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	private void showData() {
		if (impCustomsDeclaration == null) {
			return;
		}
		tfCustomsDeclarationCode.setText(impCustomsDeclaration
				.getCustomsDeclarationCode());
		tfContractNo.setText(impCustomsDeclaration.getContractNo());
		tfEmsNo.setText(impCustomsDeclaration.getEmsNo());
		tfTotalMoney
				.setText(impCustomsDeclaration.getTotalMoney() == null ? "0.0"
						: impCustomsDeclaration.getTotalMoney()
								.toString());
		tfStrikeMoney.setValue(impCustomsDeclaration.getStrikeMoney());
		tfWhiteNo.setText(impCustomsDeclaration.getWhiteBillNo());
	}

	private void fillData() {
		if (impCustomsDeclaration == null) {
			return;
		}
		if(tfWhiteNo.getText().trim().equals("")){
			impCustomsDeclaration.setWhiteBillNo(null);	
		}else{
			impCustomsDeclaration.setWhiteBillNo(tfWhiteNo.getText().trim());
			impCustomsDeclaration.setCanStrike(true);
		}
		impCustomsDeclaration
				.setStrikeMoney(tfStrikeMoney.getValue() == null ? 0.0 : Double
						.parseDouble(tfStrikeMoney.getValue().toString()));
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfWhiteNo() {
		if (tfWhiteNo == null) {
			tfWhiteNo = new JTextField();
			tfWhiteNo.setEditable(false);
			tfWhiteNo.setBackground(java.awt.Color.white);
			tfWhiteNo.setBounds(129, 173, 212, 23);
		}
		return tfWhiteNo;
	}

	/**
	 * @return Returns the isWriteInWhiteNo.
	 */
	public boolean isWriteInWhiteNo() {
		return isWriteInWhiteNo;
	}

	/**
	 * @param isWriteInWhiteNo
	 *            The isWriteInWhiteNo to set.
	 */
	public void setWriteInWhiteNo(boolean isWriteInWhiteNo) {
		this.isWriteInWhiteNo = isWriteInWhiteNo;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
