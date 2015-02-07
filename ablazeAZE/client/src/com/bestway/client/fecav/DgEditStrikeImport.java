package com.bestway.client.fecav;

import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.StrikeImpCustomsDeclaration;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JFormattedTextField;

public class DgEditStrikeImport extends JDialogBase {

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

	private StrikeImpCustomsDeclaration strikeImpCustomsDeclaration = null;  //  @jve:decl-index=0:

	private FecavAction fecavAction = null;

	private JTextField tfWhiteNo = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JFormattedTextField tfExchangeRate = null;

	private JFormattedTextField tfConvertUSDMoney = null;

	public StrikeImpCustomsDeclaration getStrikeImpCustomsDeclaration() {
		return strikeImpCustomsDeclaration;
	}

	public void setStrikeImpCustomsDeclaration(
			StrikeImpCustomsDeclaration strikeImpCustomsDeclaration) {
		this.strikeImpCustomsDeclaration = strikeImpCustomsDeclaration;
	}

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgEditStrikeImport() {
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
		this.setSize(new java.awt.Dimension(404, 329));
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
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(50, 221, 79, 22));
			jLabel7.setText("折美元");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(50, 194, 79, 20));
			jLabel6.setText("汇率");
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(50, 135, 79, 18));
			jLabel4.setText("冲销金额");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(50, 106, 79, 18));
			jLabel3.setText("报关单总金额");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(50, 79, 79, 18));
			jLabel2.setText("手册编号");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(50, 49, 79, 18));
			jLabel1.setText("合同号");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(50, 23, 79, 18));
			jLabel.setText("进口报关单号 ");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jLabel5.setBounds(50, 165, 79, 22);
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
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getTfExchangeRate(), null);
			jContentPane.add(getTfConvertUSDMoney(), null);
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
			tfCustomsDeclarationCode.setBounds(new java.awt.Rectangle(131, 21,
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
			tfContractNo.setBounds(new java.awt.Rectangle(131, 49, 212, 23));
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
			tfEmsNo.setBounds(new java.awt.Rectangle(131, 78, 212, 23));
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
			tfTotalMoney.setBounds(new java.awt.Rectangle(131, 107, 212, 23));
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
			tfStrikeMoney.setBounds(new java.awt.Rectangle(131, 135, 212, 23));
			tfStrikeMoney.setEditable(false);
			tfStrikeMoney.setFormatterFactory(getDefaultFormatterFactory());
			// tfStrikeMoney.getDocument().addDocumentListener(
			// new DocumentListener() {
			//
			// public void insertUpdate(DocumentEvent e) {
			// try {
			// tfStrikeMoney.commitEdit();
			// } catch (ParseException e1) {
			// }
			// // settfCanStrikeExchangeMoneyValue();
			// calConvertUSDMoney();
			// }
			//
			// public void removeUpdate(DocumentEvent e) {
			// try {
			// tfStrikeMoney.commitEdit();
			// } catch (ParseException e1) {
			// }
			// // settfCanStrikeExchangeMoneyValue();
			// calConvertUSDMoney();
			// }
			//
			// public void changedUpdate(DocumentEvent e) {
			//
			// }
			// });
		}
		return tfStrikeMoney;
	}

	private void calConvertUSDMoney() {
		Double exchangeRate = (this.tfExchangeRate.getValue() == null ? 0.0
				: Double.valueOf(this.tfExchangeRate.getValue().toString()));
		if (exchangeRate >= 100.0) {
			return;
		}
		Double strikeMoney = (this.tfStrikeMoney.getValue() == null ? 0.0
				: Double.valueOf(this.tfStrikeMoney.getValue().toString()));
		Double convertUSDMoney = (strikeMoney * exchangeRate);
		this.tfConvertUSDMoney.setValue(convertUSDMoney);
	}

	private void calConvertOwnerMoney() {
		Double exchangeRate = (this.tfExchangeRate.getValue() == null ? 0.0
				: Double.valueOf(this.tfExchangeRate.getValue().toString()));
		if (exchangeRate >= 100.0) {
			return;
		}
		Double convertUSDMoney = (this.tfConvertUSDMoney.getValue() == null ? 0.0
				: Double.valueOf(this.tfConvertUSDMoney.getValue().toString()));
		Double strikeMoney = CommonVars.getDoubleByDigit(convertUSDMoney
				/ exchangeRate, 4);
		this.tfStrikeMoney.setValue(strikeMoney);
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
			btnOk.setBounds(new java.awt.Rectangle(72, 252, 85, 25));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData();
					strikeImpCustomsDeclaration = fecavAction
							.saveBrikeImpCustomsDeclaration(new Request(
									CommonVars.getCurrUser()),
									strikeImpCustomsDeclaration);
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
			btnCancel.setBounds(new java.awt.Rectangle(233, 252, 85, 25));
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
		if (strikeImpCustomsDeclaration == null) {
			return;
		}
		tfCustomsDeclarationCode.setText(strikeImpCustomsDeclaration
				.getCustomsDeclarationCode());
		tfContractNo.setText(strikeImpCustomsDeclaration.getContractNo());
		tfEmsNo.setText(strikeImpCustomsDeclaration.getEmsNo());
		tfTotalMoney
				.setText(strikeImpCustomsDeclaration.getTotalMoney() == null ? "0.0"
						: strikeImpCustomsDeclaration.getTotalMoney()
								.toString());
		tfStrikeMoney.setValue(strikeImpCustomsDeclaration.getStrikeMoney());
		tfWhiteNo.setText(strikeImpCustomsDeclaration.getWhiteBillNo());

		tfExchangeRate.setValue(fecavAction.findExchangeRate(new Request(
				CommonVars.getCurrUser(), true), strikeImpCustomsDeclaration
				.getCurr(), strikeImpCustomsDeclaration.getFecavBillStrike()
				.getCurr(), strikeImpCustomsDeclaration.getDeclareDate()));
		calConvertUSDMoney();
	}

	private void fillData() {
		if (strikeImpCustomsDeclaration == null) {
			return;
		}
		if (tfWhiteNo.getText().trim().equals("")) {
			strikeImpCustomsDeclaration.setWhiteBillNo(null);
		} else {
			strikeImpCustomsDeclaration.setWhiteBillNo(tfWhiteNo.getText()
					.trim());
		}
		strikeImpCustomsDeclaration
				.setStrikeMoney(tfStrikeMoney.getValue() == null ? 0.0 : Double
						.parseDouble(tfStrikeMoney.getValue().toString()));
		Double sMoney = tfStrikeMoney.getValue() == null ? 0.0 : Double
				.parseDouble(tfStrikeMoney.getValue().toString());
		Double rate = tfExchangeRate.getValue() == null ? 0.0 : Double
				.parseDouble(tfExchangeRate.getValue().toString());
		strikeImpCustomsDeclaration
		.setConverUSDMoney(sMoney*rate);
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
			tfWhiteNo.setBounds(131, 164, 212, 23);
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

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfExchangeRate() {
		if (tfExchangeRate == null) {
			tfExchangeRate = new JFormattedTextField();
			tfExchangeRate.setBounds(new java.awt.Rectangle(131, 191, 212, 24));
			tfExchangeRate.setEditable(false);
			tfExchangeRate.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfExchangeRate;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfConvertUSDMoney() {
		if (tfConvertUSDMoney == null) {
			tfConvertUSDMoney = new JFormattedTextField();
			tfConvertUSDMoney.setBounds(new java.awt.Rectangle(131, 219, 212,
					26));
			tfConvertUSDMoney.setEditable(true);
			tfConvertUSDMoney.setFormatterFactory(getDefaultFormatterFactory());
			tfConvertUSDMoney.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							try {
								tfConvertUSDMoney.commitEdit();
							} catch (ParseException e1) {
							}
							// settfCanStrikeExchangeMoneyValue();
							calConvertOwnerMoney();
						}

						public void removeUpdate(DocumentEvent e) {
							try {
								tfConvertUSDMoney.commitEdit();
							} catch (ParseException e1) {
							}
							// settfCanStrikeExchangeMoneyValue();
							calConvertOwnerMoney();
						}

						public void changedUpdate(DocumentEvent e) {

						}
					});
		}
		return tfConvertUSDMoney;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
