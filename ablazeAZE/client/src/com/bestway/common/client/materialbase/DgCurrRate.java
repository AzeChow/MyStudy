/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.ui.winuicontrol.JDialogBase;


import java.awt.GridBagConstraints;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
/**
 * @author Administratorf
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCurrRate extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JTextField jtname = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private MaterialManageAction materialManageAction = null;

	private boolean isAdd = true;

	private CurrRate currRate = null;  //  @jve:decl-index=0:
	private Curr curr = null;
    private Curr curr1 = null;
	private JTextField jtcustomUnit = null;

	private JButton jButton2 = null;

	private JTextField jtcode = null;
	private JFormattedTextField jFormattedTextField = null;
	private DefaultFormatterFactory defaultFormatterFactory = null;   //  @jve:decl-index=0:parse
	private NumberFormatter numberFormatter = null;   //  @jve:decl-index=0:parse
	private JButton jButton3 = null;
	private JLabel jLabel4 = null;
	private JTextField jTextField = null;
	private JLabel jLabel5 = null;
	private JTextField jTextField1 = null;
	private JLabel jLabel6 = null;
	private JTextField jTextField2 = null;
	private JLabel jLabel7 = null;
	private JCalendarComboBox jCalendarComboBox = null;
	private JLabel jLabel8 = null;
	/**
	 * This is the default constructor
	 */
	public DgCurrRate() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");

	}
	@Override
	public void setVisible(boolean b) {
		if (b == true) {
			if (isAdd == false) { //编辑
				if (tableModel.getCurrentRow() != null) {
					currRate = (CurrRate) tableModel.getCurrentRow();
					curr = currRate.getCurr();
					curr1 = currRate.getCurr1();
				}
				fillWindow();
			} else {
				DgCurrRate.this.jFormattedTextField.setText("1");
				DgCurrRate.this.jCalendarComboBox.setDate(new Date());
			}
		}
		super.setVisible(b);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(445, 227);
		this.setTitle("汇率编辑");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel8 = new JLabel();
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();

			java.awt.GridBagConstraints gridBagConstraints10 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints9 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints8 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints7 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints6 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints5 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints4 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints3 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints2 = new GridBagConstraints();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("币制(1)代码");
			jLabel.setBounds(16, 24, 65, 23);
			jLabel2.setText("符号");
			jLabel2.setBounds(182, 24, 40, 23);
			jLabel1.setText("币制(2)代码");
			jLabel1.setBounds(15, 56, 67, 22);
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridwidth = 2;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.ipadx = 106;
			gridBagConstraints2.insets = new java.awt.Insets(25, 1, 5, 44);
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.ipadx = 9;
			gridBagConstraints3.ipady = 5;
			gridBagConstraints3.insets = new java.awt.Insets(5, 42, 6, 25);
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.gridwidth = 2;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.ipadx = 105;
			gridBagConstraints4.insets = new java.awt.Insets(7, 1, 5, 45);
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 3;
			gridBagConstraints5.ipadx = 13;
			gridBagConstraints5.ipady = -2;
			gridBagConstraints5.insets = new java.awt.Insets(8, 48, 18, 5);
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 3;
			gridBagConstraints6.ipadx = 12;
			gridBagConstraints6.ipady = -3;
			gridBagConstraints6.insets = new java.awt.Insets(9, 37, 18, 0);
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 2;
			gridBagConstraints7.ipadx = 10;
			gridBagConstraints7.ipady = 4;
			gridBagConstraints7.insets = new java.awt.Insets(6, 41, 8, 1);
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 2;
			gridBagConstraints8.gridwidth = 2;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.ipadx = 104;
			gridBagConstraints8.insets = new java.awt.Insets(5, 2, 9, 45);
			gridBagConstraints9.gridx = 2;
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.ipadx = -18;
			gridBagConstraints9.ipady = -7;
			gridBagConstraints9.insets = new java.awt.Insets(5, 1, 10, 22);
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.gridwidth = 2;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.ipadx = 104;
			gridBagConstraints10.insets = new java.awt.Insets(23,1,7,46);
			jLabel3.setBounds(15, 87, 32, 20);
			jLabel3.setText("汇率");
			jLabel4.setBounds(316, 24, 34, 23);
			jLabel4.setText("名称");
			jLabel5.setBounds(181, 54, 36, 22);
			jLabel5.setText("符号");
			jLabel6.setBounds(317, 54, 32, 23);
			jLabel6.setText("名称");
			jLabel7.setBounds(164, 86, 57, 22);
			jLabel7.setText("创建日期");
			jLabel8.setBounds(15, 117, 418, 21);
			jLabel8.setText("注：币制(1)币值 = 币制(2)币值 * 汇率     (注意：对报关单币制(1)常为美元)");
			jLabel8.setForeground(new java.awt.Color(51,0,255));
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJTextField2(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJTextField3(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.add(getJtcode(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJFormattedTextField(), null);
			jPanel2.add(getJButton3(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJTextField1(), null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(getJTextField22(), null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(getJCalendarComboBox(), null);
			jPanel2.add(jLabel8, null);
		}
		return jPanel2;
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
		if (jtname == null) {
			jtname = new JTextField();
			jtname.setEditable(false);
			jtname.setBounds(222, 24, 66, 22);
		}
		return jtname;
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
			jButton.setText("确定(S)");
			jButton.setMnemonic(java.awt.event.KeyEvent.VK_S);
			jButton.setBounds(122, 157, 77, 23);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					if (isAdd == true) {
						addData();
						clearData();
					} else {
						modifyData();
						DgCurrRate.this.dispose();
					}

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
			jButton1.setText("取消(X)");
			jButton1.setMnemonic(java.awt.event.KeyEvent.VK_X);
			jButton1.setBounds(235, 157, 77, 23);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgCurrRate.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		if (currRate != null) {
			this.jtcode.setText(currRate.getCurr().getCode());
			this.jtname.setText(currRate.getCurr().getCurrSymb());
			jTextField.setText(currRate.getCurr().getName());			
			this.jtcustomUnit.setText(currRate.getCurr1().getCode());
			this.jTextField1.setText(currRate.getCurr1().getCurrSymb());
			this.jTextField2.setText(currRate.getCurr1().getName());
			this.jFormattedTextField.setText(doubleToStr(currRate.getRate()));
			this.jCalendarComboBox.setDate(currRate.getCreateDate());
		}
	}

	private void fillCalUnit(CurrRate rate) {
		rate.setCurr(curr);
		rate.setCurr1(curr1);
		rate.setRate(strToDouble(this.jFormattedTextField.getText()));
		rate.setCompany(CommonVars.getCurrUser().getCompany());
		rate.setCreateDate(jCalendarComboBox.getDate());
	}

	private void clearData() {
		this.jtname.setText("");
		this.jtcode.setText("");
		jTextField.setText("");
		this.jFormattedTextField.setText("1");
		this.jtcustomUnit.setText("");
		this.jTextField1.setText("");
		this.jTextField2.setText("");
	}

	private boolean checkNull() {
		if (this.jtcode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "本币代码不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (this.jtcustomUnit.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "外币代码不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (this.jFormattedTextField.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "汇率不能为空,请输入!", "提示!", 1);
			return true;
		}
		return false;
	}

	private void addData() {
		CurrRate rate = new CurrRate();
		fillCalUnit(rate);

		rate = materialManageAction.saveCurrRate(new Request(CommonVars
				.getCurrUser()), rate);
		tableModel.addRow(rate);

		System.out.println("保存成功！");
	}

	private void modifyData() {
		fillCalUnit(currRate);
		currRate = materialManageAction.saveCurrRate(new Request(CommonVars
				.getCurrUser()), currRate);
		tableModel.updateRow(currRate);

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
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setIsAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}
	/**
	 * 
	 * This method initializes jtcustomUnit
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField3() {
		if (jtcustomUnit == null) {
			jtcustomUnit = new JTextField();
			jtcustomUnit.setEditable(false);
			jtcustomUnit.setBounds(80, 56, 66, 22);
			
		}
		return jtcustomUnit;
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
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("···");
			jButton2.setBounds(146, 56, 25, 23);
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					Curr c = (Curr) CommonQuery.getInstance().getCustomCurr();
					if (c != null) {
						curr1 = c;
						getJTextField3().setText(c.getCode());
						getJTextField1().setText(c.getCurrSymb());
						getJTextField22().setText(c.getName());
					}

				}
			});

		}
		return jButton2;
	}

	/**

	 * This method initializes jtcode	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJtcode() {
		if (jtcode == null) {
			jtcode = new JTextField();
			jtcode.setEditable(false);
			jtcode.setBounds(79, 24, 67, 22);
		}
		return jtcode;
	}

	/**

	 * This method initializes jFormattedTextField	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField.setBounds(64, 86, 91, 22);
			jFormattedTextField.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField;
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
			DecimalFormat decimalFormat = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat.setMaximumFractionDigits(999);
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(146, 24, 25, 22);
			jButton3.setText("...");
			jButton3.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					
					Curr c = (Curr) CommonQuery.getInstance().getCustomCurr();
					if (c != null) {
						curr = c;
						getJtcode().setText(c.getCode());
						getJtname().setText(c.getCurrSymb());
						getJTextField().setText(c.getName());
					}
				}
			});
		}
		return jButton3;
	}
	/**
	 * @return Returns the jtname.
	 */
	public JTextField getJtname() {
		return jtname;
	}
	/**
	 * @param jtname The jtname to set.
	 */
	public void setJtname(JTextField jtname) {
		this.jtname = jtname;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setEditable(false);
			jTextField.setBounds(356, 25, 68, 22);
		}
		return jTextField;
	}
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setEditable(false);
			jTextField1.setBounds(223, 55, 64, 22);
		}
		return jTextField1;
	}
	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField22() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setEditable(false);
			jTextField2.setBounds(357, 54, 67, 22);
		}
		return jTextField2;
	}
	/**
	 * This method initializes jCalendarComboBox	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */    
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(229, 86, 93, 22);
		}
		return jCalendarComboBox;
	}
	/**
	 * @param isAdd The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}
          } //  @jve:decl-index=0:visual-constraint="10,10"
