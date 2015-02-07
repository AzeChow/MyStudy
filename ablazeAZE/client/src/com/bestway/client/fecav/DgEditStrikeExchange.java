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
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.StrikeBillOfExchange;
import com.bestway.fecav.entity.StrikeImpCustomsDeclaration;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgEditStrikeExchange extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextField tfBillOfExchangeCode = null;

	private JTextField tfEndDate = null;

	private JLabel jLabel3 = null;

	private JTextField tfExchangeMoney = null;

	private JLabel jLabel4 = null;

	private JCustomFormattedTextField tfStrikeMoney = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private boolean isOk = false;

	private StrikeBillOfExchange strikeBillOfExchange = null;

	private FecavAction fecavAction = null;

	private JLabel jLabel = null;

	private JLabel jLabel5 = null;

	private JCustomFormattedTextField tfExchangeRate = null;

	private JCustomFormattedTextField tfConvertUSDMoney = null;

	public StrikeBillOfExchange getStrikeBillOfExchange() {
		return strikeBillOfExchange;
	}

	public void setStrikeBillOfExchange(
			StrikeBillOfExchange strikeImpCustomsDeclaration) {
		this.strikeBillOfExchange = strikeImpCustomsDeclaration;
	}

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgEditStrikeExchange() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
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
		this.setSize(new java.awt.Dimension(368, 289));
		this.setTitle("修改汇票冲销");
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
			jLabel5.setBounds(new java.awt.Rectangle(30, 172, 59, 21));
			jLabel5.setText("折美元");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(30, 142, 79, 22));
			jLabel.setText("汇率");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(30, 115, 79, 18));
			jLabel4.setText("冲销金额");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(30, 86, 79, 18));
			jLabel3.setText("结汇金额");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(30, 59, 79, 18));
			jLabel2.setText("结汇日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(30, 29, 79, 18));
			jLabel1.setText("汇票号码");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfBillOfExchangeCode(), null);
			jContentPane.add(getTfEndDate(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getTfExchangeMoney(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getTfStrikeMoney(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getTfExchangeRate(), null);
			jContentPane.add(getTfConvertUSDMoney(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBillOfExchangeCode() {
		if (tfBillOfExchangeCode == null) {
			tfBillOfExchangeCode = new JTextField();
			tfBillOfExchangeCode.setBounds(new java.awt.Rectangle(111, 29, 212,
					23));
			tfBillOfExchangeCode.setBackground(java.awt.Color.white);
			tfBillOfExchangeCode.setEditable(false);
		}
		return tfBillOfExchangeCode;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEndDate() {
		if (tfEndDate == null) {
			tfEndDate = new JTextField();
			tfEndDate.setBounds(new java.awt.Rectangle(111, 58, 212, 23));
			tfEndDate.setBackground(java.awt.Color.white);
			tfEndDate.setEditable(false);
		}
		return tfEndDate;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExchangeMoney() {
		if (tfExchangeMoney == null) {
			tfExchangeMoney = new JTextField();
			tfExchangeMoney.setBounds(new java.awt.Rectangle(111, 87, 212, 23));
			tfExchangeMoney.setBackground(java.awt.Color.white);
			tfExchangeMoney.setEditable(false);
		}
		return tfExchangeMoney;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfStrikeMoney() {
		if (tfStrikeMoney == null) {
			tfStrikeMoney = new JCustomFormattedTextField();
			tfStrikeMoney.setBounds(new java.awt.Rectangle(111, 115, 212, 23));
			tfStrikeMoney.setEditable(false);
			tfStrikeMoney.setFormatterFactory(getDefaultFormatterFactory());
//			tfStrikeMoney.getDocument().addDocumentListener(
//					new DocumentListener() {
//
//						public void insertUpdate(DocumentEvent e) {
//							try {
//								tfStrikeMoney.commitEdit();
//							} catch (ParseException e1) {
//							}
//							// settfCanStrikeExchangeMoneyValue();
//							calConvertUSDMoney();
//						}
//
//						public void removeUpdate(DocumentEvent e) {
//							try {
//								tfStrikeMoney.commitEdit();
//							} catch (ParseException e1) {
//							}
//							// settfCanStrikeExchangeMoneyValue();
//							calConvertUSDMoney();
//						}
//
//						public void changedUpdate(DocumentEvent e) {
//
//						}
//					});
		}
		return tfStrikeMoney;
	}
	
	private void calConvertUSDMoney() {
		Double exchangeRate = (this.tfExchangeRate.getValue() == null ? 0.0
				: Double.valueOf(this.tfExchangeRate.getValue()
						.toString()));
		if (exchangeRate >= 100.0) {
			return;
		}
		Double strikeMoney = (this.tfStrikeMoney.getValue() == null ? 0.0
				: Double.valueOf(this.tfStrikeMoney.getValue()
						.toString()));
		Double convertUSDMoney = (strikeMoney * exchangeRate);
		this.tfConvertUSDMoney.setValue(convertUSDMoney);
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
			btnOk.setBounds(new java.awt.Rectangle(58, 213, 85, 25));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData();
					strikeBillOfExchange = fecavAction.saveBrikeBillOfExchange(
							new Request(CommonVars.getCurrUser()),
							strikeBillOfExchange);
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
			btnCancel.setBounds(new java.awt.Rectangle(209, 213, 85, 25));
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
		if (strikeBillOfExchange == null) {
			return;
		}
		tfBillOfExchangeCode.setText(strikeBillOfExchange
				.getBillOfExchangeCode());
		tfEndDate.setText(strikeBillOfExchange.getEndDate().toString());
		tfExchangeMoney
				.setText(strikeBillOfExchange.getExchangeMoney() == null ? "0.0"
						: strikeBillOfExchange.getExchangeMoney().toString());
		tfStrikeMoney.setValue(strikeBillOfExchange.getStrikeMoney());

		tfExchangeRate.setValue(fecavAction.findExchangeRate(new Request(
				CommonVars.getCurrUser(), true),
				strikeBillOfExchange.getCurr(), strikeBillOfExchange
						.getFecavBillStrike().getCurr(), strikeBillOfExchange
						.getEndDate()));
		calConvertUSDMoney();
	}

	private void fillData() {
		if (strikeBillOfExchange == null) {
			return;
		}
		strikeBillOfExchange
				.setStrikeMoney(tfStrikeMoney.getValue() == null ? 0.0 : Double
						.parseDouble(tfStrikeMoney.getValue().toString()));
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfExchangeRate() {
		if (tfExchangeRate == null) {
			tfExchangeRate = new JCustomFormattedTextField();
			tfExchangeRate.setBounds(new java.awt.Rectangle(111, 142, 212, 24));
			tfExchangeRate.setEditable(false);
			tfExchangeRate.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfExchangeRate;
	}

	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfConvertUSDMoney() {
		if (tfConvertUSDMoney == null) {
			tfConvertUSDMoney = new JCustomFormattedTextField();
			tfConvertUSDMoney.setBounds(new java.awt.Rectangle(111, 172, 212,
					23));

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
} // @jve:decl-index=0:visual-constraint="10,10"
