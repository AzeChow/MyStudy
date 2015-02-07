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
public class DgFinanceCheckEditEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField4 = null;

	private JFormattedTextField jFormattedTextField3 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private CasAction casAction = null;	

	private JTableListModel tableModel = null;
	private CheckDetail checkDetail = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; //  @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; //  @jve:decl-index=0:parse

	private JFormattedTextField jFormattedTextField = null;
	private JFormattedTextField jFormattedTextField1 = null;
	private JTextField jTextField3 = null;
	/**
	 * This is the default constructor
	 */
	public DgFinanceCheckEditEdit() {
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
		this.setSize(419, 226);
		this.setTitle("资料修改");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (tableModel.getCurrentRow() != null) {
					checkDetail = (CheckDetail) tableModel
								.getCurrentRow();
						fillWindow();
				}
			}
		});
	}

	private void fillWindow() {
		jTextField.setText(String.valueOf(checkDetail.getCheckMaster().getNum()));
		jTextField1.setText(checkDetail.getPtName());
		jTextField3.setText(checkDetail.getPtUnit());
		jTextField2.setText(checkDetail.getPtPart());
		jTextField4.setText(checkDetail.getPtSpec());
		jFormattedTextField3.setText(doubleToStr(checkDetail.getPtAmount()));
		jFormattedTextField.setText(doubleToStr(checkDetail.getFactPtAmount()));
		jFormattedTextField1.setText(doubleToStr(checkDetail.getRemoveAmount()));
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel12 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("商品名称");
			jLabel.setBounds(216, 22, 61, 18);
			jLabel1.setBounds(14, 22, 57, 17);
			jLabel1.setText("盘点序号");
			jLabel2.setBounds(13, 53, 54, 20);
			jLabel2.setText("商品编号");
			jLabel4.setBounds(215, 53, 53, 20);
			jLabel4.setText("型号规格");
			jLabel10.setBounds(14, 84, 61, 21);
			jLabel10.setText("库存数量");
			jLabel12.setBounds(215, 115, 57, 21);
			jLabel12.setText("差值");
			jLabel7.setBounds(13, 115, 72, 19);
			jLabel7.setText("实际库存数量");
			jLabel3.setBounds(216, 84, 45, 22);
			jLabel3.setText("单位");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(getJFormattedTextField3(), null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(getJFormattedTextField1(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextField3(), null);
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
			jTextField.setBounds(89, 22, 105, 22);
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
			jTextField1.setBounds(284, 22, 110, 22);
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
			jTextField2.setBounds(89, 53, 105, 22);
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
			jTextField4.setBounds(283, 53, 110, 22);
			jTextField4.setEditable(false);
		}
		return jTextField4;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 *  
	 */
	private JFormattedTextField getJFormattedTextField3() {
		if (jFormattedTextField3 == null) {
			jFormattedTextField3 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField3.setEditable(false);
			jFormattedTextField3.setBounds(89, 84, 105, 22);
			jFormattedTextField3
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField3;
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
			jButton.setBounds(116, 156, 70, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData(checkDetail);
					checkDetail = casAction
							.saveCheckDetails(new Request(CommonVars
									.getCurrUser()), checkDetail);
					tableModel.updateRow(checkDetail);
					DgFinanceCheckEditEdit.this.dispose();
				}
			});

		}
		return jButton;
	}

	public void fillData(CheckDetail checkDetail) { //保存
		checkDetail.setFactPtAmount(strToDouble(jFormattedTextField.getText()));
		checkDetail.setRemoveAmount(Double.valueOf(CommonVars.formatDouble(strToDouble(jFormattedTextField3.getText())).doubleValue()-CommonVars.formatDouble(strToDouble(jFormattedTextField.getText())).doubleValue()));
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
			jButton1.setBounds(223, 157, 69, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgFinanceCheckEditEdit.this.dispose();
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
			jFormattedTextField.setBounds(89, 115, 105, 22);
			jFormattedTextField.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField;
	}

	/**

	 * This method initializes jFormattedTextField1	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField1() {
		if (jFormattedTextField1 == null) {
			jFormattedTextField1 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField1.setEditable(false);
			jFormattedTextField1.setBounds(283, 115, 110, 22);
			jFormattedTextField1.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField1;
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
			jTextField3.setBounds(284, 84, 110, 22);
		}
		return jTextField3;
	}

 } //  @jve:decl-index=0:visual-constraint="10,10"
