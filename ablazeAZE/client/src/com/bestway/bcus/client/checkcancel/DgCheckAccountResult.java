/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JLabel;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CheckImg;
import com.bestway.bcus.checkcancel.entity.CheckOwnerAccountComport;
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
public class DgCheckAccountResult extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField3 = null;

	private JTextField jTextField4 = null;

	private JButton jButton = null;

	private JTextField jTextField6 = null;

	private CheckCancelAction checkCancelAction = null;

	private JTableListModel tableModel = null;

	private CheckOwnerAccountComport obj = null; //料件

	private DefaultFormatterFactory defaultFormatterFactory = null; //  @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; //  @jve:decl-index=0:parse

	private JFormattedTextField jFormattedTextField = null;
	private JFormattedTextField jFormattedTextField1 = null;
	private JFormattedTextField jFormattedTextField5 = null;
	private JFormattedTextField jFormattedTextField6 = null;
	private JFormattedTextField jFormattedTextField10 = null;
	private JFormattedTextField jFormattedTextField12 = null;
	private JFormattedTextField jFormattedTextField13 = null;

	private JButton jButton1 = null;

	/**
	 * This is the default constructor
	 */
	public DgCheckAccountResult() {
		super();
		initialize();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(522, 299);
		this.setTitle("中期核查料件维护");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (tableModel.getCurrentRow() != null) {
					obj = (CheckOwnerAccountComport) tableModel
								.getCurrentRow();
						fillWindowImg();
				}
			}
		});
	}

	private void fillWindowImg() {
		jTextField1.setText(obj.getPtNo());
		jTextField3.setText(obj.getName());
		jTextField4.setText(obj.getSpec());
		jTextField6.setText("");
		
		
		jFormattedTextField.setText(doubleToStr(obj.getMaterielOnwayNum()));
		jFormattedTextField1.setText(doubleToStr(obj.getMaterielStockNum()));
	
		jFormattedTextField5.setText(doubleToStr(obj.getExgConvertImgNum()));
		jFormattedTextField6.setText(doubleToStr(obj.getTranNoCustomsNum()));
		
		jFormattedTextField10.setText(doubleToStr(obj.getMaterielNum()));
		jFormattedTextField12.setText(doubleToStr(obj.getCyNum()));
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			javax.swing.JLabel jLabel21 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			javax.swing.JLabel jLabel14 = new JLabel();

			javax.swing.JLabel jLabel13 = new JLabel();

			javax.swing.JLabel jLabel11 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel12 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("料号");
			jLabel.setBounds(16, 19, 28, 18);
			jLabel3.setBounds(248, 22, 59, 17);
			jLabel3.setText("料件名称");
			jLabel4.setBounds(16, 49, 59, 20);
			jLabel4.setText("型号规格");
			jLabel12.setBounds(16, 81, 78, 21);
			jLabel12.setText("原料库存数量");
			jLabel6.setBounds(315, 49, 61, 21);
			jLabel6.setText("计量单位");
			jLabel7.setBounds(280, 83, 72, 19);
			jLabel7.setText("原料在途数量");
			jLabel11.setBounds(16, 110, 66, 21);
			jLabel11.setText("成品折料");
			jLabel13.setBounds(281, 111, 77, 19);
			jLabel13.setText("其他原料库存");
			jLabel14.setBounds(16, 141, 79, 20);
			jLabel14.setText("转进未报数量");
			jLabel19.setBounds(16, 171, 80, 22);
			jLabel19.setText("企业实际库存");
			jLabel21.setBounds(278, 171, 64, 22);
			jLabel21.setText("库存差异");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJTextField6(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(getJFormattedTextField1(), null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(getJFormattedTextField5(), null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(getJFormattedTextField6(), null);
			jContentPane.add(jLabel19, null);
			jContentPane.add(getJFormattedTextField10(), null);
			jContentPane.add(jLabel21, null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJFormattedTextField12(), null);
			jContentPane.add(getJFormattedTextField13(), null);
		}
		return jContentPane;
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
			jTextField1.setBounds(66, 18, 172, 22);
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(314, 20, 177, 22);
			jTextField3.setEditable(false);
		}
		return jTextField3;
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
			jTextField4.setBounds(77, 48, 216, 22);
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
			jButton.setBounds(180, 221, 69, 24);
			jButton.setText("保存");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillDataImg(obj);
					obj = checkCancelAction
							.saveCheckOwnerAccountComport(new Request(CommonVars
									.getCurrUser()), obj);
					tableModel.updateRow(obj);
					DgCheckAccountResult.this.dispose();
				}
			});

		}
		return jButton;
	}

	public void fillDataImg(CheckOwnerAccountComport obj) { //保存
		obj.setMaterielOnwayNum(strToDouble(jFormattedTextField.getText()));
		obj.setMaterielStockNum(strToDouble(jFormattedTextField1.getText()));
		obj.setExgConvertImgNum(strToDouble(jFormattedTextField5.getText()));
		obj.setOtherStorkNum(strToDouble(jFormattedTextField13.getText()));
		obj.setTranNoCustomsNum(strToDouble(jFormattedTextField6.getText()));		
		obj.setCyNum(strToDouble(jFormattedTextField12.getText())); 
	}
	/**
	 * 
	 * This method initializes jTextField6
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(380, 47, 111, 22);
			jTextField6.setEditable(false);
		}
		return jTextField6;
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
			DecimalFormat decimalFormat1 = new DecimalFormat();  //  @jve:decl-index=0:
			decimalFormat1.setMaximumFractionDigits(9);
			decimalFormat1.setGroupingSize(0);
			numberFormatter.setFormat(decimalFormat1);
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
			jFormattedTextField.setBounds(363, 82, 129, 22);
			jFormattedTextField.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField.addFocusListener(new java.awt.event.FocusAdapter() { 
				public void focusLost(java.awt.event.FocusEvent e) {  
					jisuancy();
				}
			});
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
			jFormattedTextField1.setBounds(106, 80, 154, 22);
			jFormattedTextField1.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField1.addFocusListener(new java.awt.event.FocusAdapter() { 
				public void focusLost(java.awt.event.FocusEvent e) {  
					jisuancy();
				}
			});
		}
		return jFormattedTextField1;
	}

	private double forDouble(String d){
		if (d == null || "".equals(d)){
			return 0;
		}
		return Double.parseDouble(d);
	}
	private void jisuancy(){
		
		
		double materielstocknum = forDouble(jFormattedTextField1.getText().toString());	
		double materielOnwayNum = forDouble(jFormattedTextField.getText().toString());
		double exgconvertimg = forDouble(jFormattedTextField5.getText().toString());
		double otherstocknum = forDouble(jFormattedTextField13.getText().toString());
		double transnobg = forDouble(jFormattedTextField6.getText().toString());
		
		double materielNum = forDouble(jFormattedTextField10.getText().toString());
		
			
		jFormattedTextField12.setText(forNumq(materielNum-(materielstocknum+materielOnwayNum+exgconvertimg+otherstocknum-transnobg),9));
	}
	
	
	private String forNumq(Double d,int dig) {
    	if (d == null || d.equals(Double.valueOf(0))){
    		return "";
    	}
    	double shuliang = d.doubleValue();
		BigDecimal bd = new BigDecimal(shuliang);
		String totalshuliang = bd.setScale(dig, BigDecimal.ROUND_HALF_UP)
				.toString();
		for (int i=totalshuliang.length();i>0;i--){
			if (totalshuliang.substring(i-1,i).equals("0")){
				totalshuliang = totalshuliang.substring(0,i-1);	
			} else if (totalshuliang.substring(i-1,i).equals(".")){
				totalshuliang = totalshuliang.substring(0,i-1);	
				break;
			} else {
				break;
			}
		}
		return totalshuliang;
	}
	/**

	 * This method initializes jFormattedTextField5	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField5() {
		if (jFormattedTextField5 == null) {
			jFormattedTextField5 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField5.setBounds(106, 109, 154, 22);
			jFormattedTextField5.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField5.addFocusListener(new java.awt.event.FocusAdapter() { 
				public void focusLost(java.awt.event.FocusEvent e) {  
					jisuancy();
				}
			});
		}
		return jFormattedTextField5;
	}

	/**

	 * This method initializes jFormattedTextField6	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField6() {
		if (jFormattedTextField6 == null) {
			jFormattedTextField6 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField6.setBounds(106, 140, 154, 22);
			jFormattedTextField6.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField6.addFocusListener(new java.awt.event.FocusAdapter() { 
				public void focusLost(java.awt.event.FocusEvent e) {  
					jisuancy();
				}
			});
		}
		return jFormattedTextField6;
	}

	/**

	 * This method initializes jFormattedTextField10	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField10() {
		if (jFormattedTextField10 == null) {
			jFormattedTextField10 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField10.setEditable(false);
			jFormattedTextField10.setBounds(106, 170, 154, 22);
			jFormattedTextField10.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField10;
	}

	/**

	 * This method initializes jFormattedTextField12	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField12() {
		if (jFormattedTextField12 == null) {
			jFormattedTextField12 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField12.setBounds(361, 171, 129, 22);
			jFormattedTextField12.setEditable(false);
			jFormattedTextField12.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField12;
	}

	/**

	 * This method initializes jFormattedTextField13	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField13() {
		if (jFormattedTextField13 == null) {
			jFormattedTextField13 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField13.setBounds(363, 111, 128, 22);
			jFormattedTextField13.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField13.addFocusListener(new java.awt.event.FocusAdapter() { 
				public void focusLost(java.awt.event.FocusEvent e) {  
					jisuancy();
				}
			});
		}
		return jFormattedTextField13;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(267,221,69,24));
			jButton1.setText("退出");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCheckAccountResult.this.dispose();
				}
			});
		}
		return jButton1;
	}

           } //  @jve:decl-index=0:visual-constraint="10,10"
