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
import com.bestway.bcus.cas.entity.DebtDetail;
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
public class DgFinanceDebtEditEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField4 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private CasAction casAction = null;	

	private JTableListModel tableModel = null;
	private DebtDetail debtDetail = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; //  @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; //  @jve:decl-index=0:parse

	private JFormattedTextField jFormattedTextField = null;
	private JTextField jTextField3 = null;
	private JFormattedTextField jFormattedTextField1 = null;
	private JFormattedTextField jFormattedTextField2 = null;
	private JFormattedTextField jFormattedTextField3 = null;
	/**
	 * This is the default constructor
	 */
	public DgFinanceDebtEditEdit() {
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
		this.setSize(363, 221);
		this.setTitle("资料修改");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (tableModel.getCurrentRow() != null) {
					debtDetail = (DebtDetail) tableModel
								.getCurrentRow();
						fillWindow();
				}
				setState();
			}
		});
	}

	private void fillWindow() {
		jTextField.setText(debtDetail.getItem1());
		jTextField2.setText(debtDetail.getItem2());
		
		jTextField4.setText(debtDetail.getTimes1());
		jTextField3.setText(debtDetail.getTimes2());
		
		jFormattedTextField.setText(doubleToStr(debtDetail.getBeginValue1()));
		jFormattedTextField1.setText(doubleToStr(debtDetail.getEndValue1()));
		jFormattedTextField2.setText(doubleToStr(debtDetail.getBeginValue2()));
		jFormattedTextField3.setText(doubleToStr(debtDetail.getEndValue2()));
		
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("行次");
			jLabel.setBounds(16, 46, 38, 18);
			jLabel1.setBounds(16, 18, 31, 17);
			jLabel1.setText("资产");
			jLabel7.setBounds(122, 46, 41, 19);
			jLabel7.setText("年初值");
			jContentPane.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jLabel2.setBounds(233, 46, 43, 20);
			jLabel2.setText("期末值");
			jLabel3.setBounds(16, 75, 101, 22);
			jLabel3.setText("负债及所有者权益");
			jLabel4.setBounds(16, 105, 31, 20);
			jLabel4.setText("行次");
			jLabel5.setBounds(122, 105, 43, 20);
			jLabel5.setText("年初值");
			jLabel6.setBounds(233, 105, 47, 20);
			jLabel6.setText("期末值");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(getJFormattedTextField1(), null);
			jContentPane.add(getJFormattedTextField2(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJFormattedTextField3(), null);
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
			jTextField.setBounds(56, 18, 286, 22);
			jTextField.setEditable(false);
		}
		return jTextField;
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
			jTextField2.setBounds(122, 75, 219, 22);
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
			jTextField4.setBounds(56, 46, 54, 22);
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
			jButton.setBounds(84, 147, 70, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if (jTextField4.getText() != null && !jTextField4.getText().equals("")){
					  debtDetail.setBeginValue1(strToDouble(jFormattedTextField.getText()));
					  debtDetail.setEndValue1(strToDouble(jFormattedTextField1.getText()));
					  casAction.saveDebtDetail(new Request(CommonVars.getCurrUser()),debtDetail);
					  casAction.debtAccount(new Request(CommonVars.getCurrUser()),debtDetail.getDebtMaster(),jTextField4.getText().trim(),"1");
					  casAction.debtAccount(new Request(CommonVars.getCurrUser()),debtDetail.getDebtMaster(),jTextField4.getText().trim(),"2");
					} 
                    if (jTextField3.getText() != null && !jTextField3.getText().equals("")){
                    	  debtDetail.setBeginValue2(strToDouble(jFormattedTextField2.getText()));
                    	  debtDetail.setEndValue2(strToDouble(jFormattedTextField3.getText()));
                    	  casAction.saveDebtDetail(new Request(CommonVars.getCurrUser()),debtDetail);
						  casAction.debtAccount(new Request(CommonVars.getCurrUser()),debtDetail.getDebtMaster(),jTextField3.getText().trim(),"1");			
						  casAction.debtAccount(new Request(CommonVars.getCurrUser()),debtDetail.getDebtMaster(),jTextField3.getText().trim(),"2");
					} 				
					DgFinanceDebtEditEdit.this.dispose();
				}
			});

		}
		return jButton;
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
			jButton1.setBounds(191, 148, 69, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgFinanceDebtEditEdit.this.dispose();
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
			jFormattedTextField.setBounds(169, 46, 59, 22);
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
			jTextField3.setBounds(56, 105, 54, 22);
		}
		return jTextField3;
	}

	/**

	 * This method initializes jFormattedTextField1	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField1() {
		if (jFormattedTextField1 == null) {
			jFormattedTextField1 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField1.setBounds(283, 46, 59, 22);
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
			jFormattedTextField2.setBounds(169, 105, 59, 22);
		}
		return jFormattedTextField2;
	}
	private void setState(){
		this.jFormattedTextField.setEditable(this.jTextField4.getText()!=null && !this.jTextField4.getText().equals("") 
				&& !this.jTextField4.getText().equals("17") && !this.jTextField4.getText().equals("23") && !this.jTextField4.getText().equals("32") 
				&& !this.jTextField4.getText().equals("40") && !this.jTextField4.getText().equals("41"));
		this.jFormattedTextField1.setEditable(this.jTextField4.getText()!=null && !this.jTextField4.getText().equals("") 
				&& !this.jTextField4.getText().equals("17") && !this.jTextField4.getText().equals("23") && !this.jTextField4.getText().equals("32") 
				&& !this.jTextField4.getText().equals("40") && !this.jTextField4.getText().equals("41"));
		this.jFormattedTextField2.setEditable(this.jTextField3.getText()!=null && !this.jTextField3.getText().equals("")
				&& !this.jTextField3.getText().equals("55") && !this.jTextField3.getText().equals("60") && !this.jTextField3.getText().equals("66")
				&& !this.jTextField3.getText().equals("67") && !this.jTextField3.getText().equals("68") && !this.jTextField3.getText().equals("80")
				&& !this.jTextField3.getText().equals("81"));
		this.jFormattedTextField3.setEditable(this.jTextField3.getText()!=null && !this.jTextField3.getText().equals("")
				&& !this.jTextField3.getText().equals("55") && !this.jTextField3.getText().equals("60") && !this.jTextField3.getText().equals("66")
				&& !this.jTextField3.getText().equals("67") && !this.jTextField3.getText().equals("68") && !this.jTextField3.getText().equals("80")
				&& !this.jTextField3.getText().equals("81"));
	}

	/**

	 * This method initializes jFormattedTextField3	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField3() {
		if (jFormattedTextField3 == null) {
			jFormattedTextField3 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField3.setBounds(283, 105, 59, 22);
			jFormattedTextField3.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField3;
	}

     } //  @jve:decl-index=0:visual-constraint="10,10"
