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
import com.bestway.bcus.cas.entity.MarginDetail;
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
public class DgFinanceMarginEditEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;

	private JTextField jTextField2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private CasAction casAction = null;	

	private JTableListModel tableModel = null;
	private MarginDetail marginDetail = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; //  @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; //  @jve:decl-index=0:parse

	private JFormattedTextField jFormattedTextField = null;
	private JFormattedTextField jFormattedTextField2 = null;
	private JFormattedTextField jFormattedTextField1 = null;
	/**
	 * This is the default constructor
	 */
	public DgFinanceMarginEditEdit() {
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
		this.setSize(382, 193);
		this.setTitle("资料修改");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (tableModel.getCurrentRow() != null) {
					marginDetail = (MarginDetail) tableModel
								.getCurrentRow();
						fillWindow();
				}
				setState();
			}
		});
	}

	private void fillWindow() {
		jTextField.setText(marginDetail.getItem());
		jTextField2.setText(marginDetail.getTimes());
		jFormattedTextField.setText(doubleToStr(marginDetail.getThisCount()));
		jFormattedTextField1.setText(doubleToStr(marginDetail.getPriveSumCount()));
		jFormattedTextField2.setText(doubleToStr(marginDetail.getThisSumCount()));
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("行次");
			jLabel.setBounds(16, 51, 38, 18);
			jLabel1.setBounds(16, 19, 36, 17);
			jLabel1.setText("项目");
			jLabel7.setBounds(185, 51, 49, 19);
			jLabel7.setText("本期数");
			jContentPane.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jLabel2.setBounds(16, 79, 67, 23);
			jLabel2.setText("本年累计数");
			jLabel3.setBounds(185, 79, 90, 21);
			jLabel3.setText("上年同期累计数");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(getJFormattedTextField2(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJFormattedTextField1(), null);
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
			jTextField.setBounds(57, 18, 300, 22);
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
			jTextField2.setBounds(57, 49, 110, 22);
			jTextField2.setEditable(false);
		}
		return jTextField2;
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
			jButton.setBounds(102, 123, 70, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if (jTextField2.getText() != null && !jTextField2.getText().equals("")){
					  marginDetail.setThisCount(strToDouble(jFormattedTextField.getText()));
					  marginDetail.setThisSumCount(strToDouble(jFormattedTextField2.getText()));
					  marginDetail.setPriveSumCount(strToDouble(jFormattedTextField1.getText()));
					  casAction.saveMarginDetails(new Request(CommonVars.getCurrUser()),marginDetail);
					  casAction.marginAccount(new Request(CommonVars.getCurrUser()),marginDetail.getMarginMaster());					  
					} 
					DgFinanceMarginEditEdit.this.dispose();
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
			jButton1.setBounds(209, 124, 69, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgFinanceMarginEditEdit.this.dispose();
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
			jFormattedTextField.setBounds(278, 48, 80, 22);
			jFormattedTextField.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField;
	}

	/**

	 * This method initializes jFormattedTextField2	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField2() {
		if (jFormattedTextField2 == null) {
			jFormattedTextField2 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField2.setBounds(90, 79, 79, 22);
		}
		return jFormattedTextField2;
	}
	private void setState(){
		this.jFormattedTextField.setEditable(this.jTextField2.getText()!=null && !this.jTextField2.getText().equals("") 
				&& !this.jTextField2.getText().equals("4") && !this.jTextField2.getText().equals("8") && !this.jTextField2.getText().equals("14") && !this.jTextField2.getText().equals("16")
				&& !this.jTextField2.getText().equals("21") && !this.jTextField2.getText().equals("23"));
	}

	/**

	 * This method initializes jFormattedTextField1	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField1() {
		if (jFormattedTextField1 == null) {
			jFormattedTextField1 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField1.setBounds(278, 78, 79, 22);
			jFormattedTextField1.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField1;
	}

     } //  @jve:decl-index=0:visual-constraint="10,10"
