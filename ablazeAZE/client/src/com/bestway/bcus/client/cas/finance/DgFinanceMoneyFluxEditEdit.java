/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.finance;

import java.text.ParseException;

import javax.swing.JLabel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.CheckDetail;
import com.bestway.bcus.cas.entity.MoneyDetail;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFinanceMoneyFluxEditEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField4 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private CasAction casAction = null;	

	private JTableListModel tableModel = null;
	private MoneyDetail moneyDetail = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; //  @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; //  @jve:decl-index=0:parse

	private JFormattedTextField jFormattedTextField = null;
	private JTextField jTextField3 = null;
	private JTextField jTextField5 = null;
	private JFormattedTextField jFormattedTextField1 = null;
	private JFormattedTextField jFormattedTextField2 = null;
	/**
	 * This is the default constructor
	 */
	public DgFinanceMoneyFluxEditEdit() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext()
	       .getBean( "casAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(465, 217);
		this.setTitle("资料修改");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (tableModel.getCurrentRow() != null) {
					moneyDetail = (MoneyDetail) tableModel
								.getCurrentRow();
						fillWindow();
				}
				setState();
			}
		});
	}

	private void fillWindow() {
		jTextField.setText(moneyDetail.getItem1());
		jTextField2.setText(moneyDetail.getItem2());
		jTextField1.setText(moneyDetail.getItem3());
		
		jTextField4.setText(moneyDetail.getTimes1());
		jTextField3.setText(moneyDetail.getTimes2());
		jTextField5.setText(moneyDetail.getTimes3());
	
		jFormattedTextField.setText(doubleToStr(moneyDetail.getMoney1()));
		jFormattedTextField1.setText(doubleToStr(moneyDetail.getMoney2()));
		jFormattedTextField2.setText(doubleToStr(moneyDetail.getMoney3()));
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("行次");
			jLabel.setBounds(321, 19, 38, 18);
			jLabel1.setBounds(116, 18, 104, 17);
			jLabel1.setText("项                       目");
			jLabel7.setBounds(384, 19, 31, 19);
			jLabel7.setText("金额");
			jContentPane.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(getJTextField5(), null);
			jContentPane.add(getJFormattedTextField1(), null);
			jContentPane.add(getJFormattedTextField2(), null);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(17, 42, 297, 22);
			jTextField.setEditable(false);
		}
		return jTextField;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(17, 107, 297, 22);
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(17, 74, 297, 22);
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}

	/**
	 * 
	 * This method initializes jTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(321, 42, 54, 22);
			jTextField4.setEditable(false);
		}
		return jTextField4;
	}

	private Double strToDouble(String value) { //转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				//	return new Double("0");
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			//return new Double("0");
			return null;
		}
	}

	private String doubleToStr(Double value) { //转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(150, 148, 70, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if (jTextField4.getText() != null && !jTextField4.getText().equals("")){
					  moneyDetail.setMoney1(strToDouble(jFormattedTextField.getText()));
					  casAction.saveMoneyDetails(new Request(CommonVars.getCurrUser()),moneyDetail);
					  casAction.account(new Request(CommonVars.getCurrUser()),moneyDetail.getMoneyMaster(),jTextField4.getText().trim());					  
					} 
                    if (jTextField3.getText() != null && !jTextField3.getText().equals("")){
                    	  moneyDetail.setMoney2(strToDouble(jFormattedTextField1.getText()));
                    	  casAction.saveMoneyDetails(new Request(CommonVars.getCurrUser()),moneyDetail);
						  casAction.account(new Request(CommonVars.getCurrUser()),moneyDetail.getMoneyMaster(),jTextField3.getText().trim());					  
					} 
                    if (jTextField5.getText() != null && !jTextField5.getText().equals("")){
                    	moneyDetail.setMoney3(strToDouble(jFormattedTextField2.getText()));
                  	    casAction.saveMoneyDetails(new Request(CommonVars.getCurrUser()),moneyDetail);  
                    	casAction.account(new Request(CommonVars.getCurrUser()),moneyDetail.getMoneyMaster(),jTextField5.getText().trim());					  
					} 					
					DgFinanceMoneyFluxEditEdit.this.dispose();
				}
			});

		}
		return jButton;
	}

	public void fillData(CheckDetail checkDetail) { //保存
		checkDetail.setFactPtAmount(strToDouble(jFormattedTextField.getText()));
	}
	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(257, 149, 69, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgFinanceMoneyFluxEditEdit.this.dispose();
				}
			});

		}
		return jButton1;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * 
	 * This method initializes defaultFormatterFactory
	 * 
	 * 
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 *  
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * 
	 * This method initializes numberFormatter
	 * 
	 * 
	 * 
	 * @return javax.swing.text.NumberFormatter
	 *  
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**

	 * This method initializes jFormattedTextField	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField.setBounds(384, 42, 59, 22);
			jFormattedTextField.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField;
	}

	/**

	 * This method initializes jTextField3	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setEditable(false);
			jTextField3.setBounds(321, 73, 54, 22);
		}
		return jTextField3;
	}

	/**

	 * This method initializes jTextField5	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setEditable(false);
			jTextField5.setBounds(321, 107, 54, 22);
		}
		return jTextField5;
	}

	/**

	 * This method initializes jFormattedTextField1	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField1() {
		if (jFormattedTextField1 == null) {
			jFormattedTextField1 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField1.setBounds(384, 73, 59, 22);
		}
		return jFormattedTextField1;
	}

	/**

	 * This method initializes jFormattedTextField2	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField2() {
		if (jFormattedTextField2 == null) {
			jFormattedTextField2 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField2.setBounds(384, 107, 59, 22);
		}
		return jFormattedTextField2;
	}
	private void setState(){
		this.jFormattedTextField.setEditable(this.jTextField4.getText()!=null && !this.jTextField4.getText().equals("") 
				&& !this.jTextField4.getText().equals("6") && !this.jTextField4.getText().equals("14") && !this.jTextField4.getText().equals("15") && !this.jTextField4.getText().equals("21"));
		this.jFormattedTextField1.setEditable(this.jTextField3.getText()!=null && !this.jTextField3.getText().equals("")
				&& !this.jTextField3.getText().equals("26") && !this.jTextField3.getText().equals("27") && !this.jTextField3.getText().equals("32") && !this.jTextField3.getText().equals("40") && !this.jTextField3.getText().equals("41"));
		this.jFormattedTextField2.setEditable(this.jTextField5.getText()!=null && !this.jTextField5.getText().equals(""));
	}

    } //  @jve:decl-index=0:visual-constraint="10,10"
