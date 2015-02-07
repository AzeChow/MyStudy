/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.text.ParseException;

import javax.swing.JLabel;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CheckExg;
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
public class DgCheckExg extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField3 = null;

	private JTextField jTextField4 = null;

	private JFormattedTextField jFormattedTextField3 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTextField jTextField6 = null;

	private CheckCancelAction checkCancelAction = null;

	private JTableListModel tableModel = null;

	private CheckExg checkExg = null; //成品

	private DefaultFormatterFactory defaultFormatterFactory = null; //  @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; //  @jve:decl-index=0:parse

	private JFormattedTextField jFormattedTextField = null;
	private JFormattedTextField jFormattedTextField1 = null;
	private JFormattedTextField jFormattedTextField2 = null;
	private JFormattedTextField jFormattedTextField4 = null;
	private JFormattedTextField jFormattedTextField5 = null;
	private JFormattedTextField jFormattedTextField6 = null;
	private JFormattedTextField jFormattedTextField7 = null;
	private JFormattedTextField jFormattedTextField13 = null;
	private JFormattedTextField jFormattedTextField14 = null;
	private JFormattedTextField jFormattedTextField15 = null;
	private JTextField jTextField5 = null;
	/**
	 * This is the default constructor
	 */
	public DgCheckExg() {
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
		this.setSize(582, 298);
		this.setTitle("中期核查料件维护");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (tableModel.getCurrentRow() != null) {
						checkExg = (CheckExg) tableModel
								.getCurrentRow();
						fillWindowExg();
				}
			}
		});
	}

	private void fillWindowExg() {
		jTextField.setText(checkExg.getSeqNum());
		jTextField5.setText(checkExg.getVersion());
		jTextField1.setText(checkExg.getPtNo());
		jTextField3.setText(checkExg.getName());
		jTextField2.setText(checkExg.getComplex()==null?"":checkExg.getComplex().getCode());
		jTextField4.setText(checkExg.getSpec());
		jTextField6.setText(checkExg.getUnit()==null?"":checkExg.getUnit().getName());
		jFormattedTextField3.setText(doubleToStr(checkExg.getMaterStock()));
		jFormattedTextField.setText(doubleToStr(checkExg.getHalfExg()));
		jFormattedTextField1.setText(doubleToStr(checkExg.getMaterExitChange()));
		jFormattedTextField2.setText(doubleToStr(checkExg.getMaterInWare()));
		jFormattedTextField4.setText(doubleToStr(checkExg.getOtherExg()));
		jFormattedTextField14.setText(doubleToStr(checkExg.getDepose()));
		jFormattedTextField5.setText(doubleToStr(checkExg.getThisThrow()));
		jFormattedTextField13.setText(doubleToStr(checkExg.getThisMaterOutWare()));
		jFormattedTextField6.setText(doubleToStr(checkExg.getMaterByWay()));
		jFormattedTextField7.setText(doubleToStr(checkExg.getThisMaterCancel()));
		jFormattedTextField15.setText(doubleToStr(checkExg.getThisMaterOutWare()));
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			javax.swing.JLabel jLabel22 = new JLabel();

			javax.swing.JLabel jLabel16 = new JLabel();

			javax.swing.JLabel jLabel15 = new JLabel();

			javax.swing.JLabel jLabel14 = new JLabel();

			javax.swing.JLabel jLabel13 = new JLabel();

			javax.swing.JLabel jLabel11 = new JLabel();

			javax.swing.JLabel jLabel9 = new JLabel();

			javax.swing.JLabel jLabel8 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel12 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("成品号/货号");
			jLabel.setBounds(191, 22, 72, 18);
			jLabel1.setBounds(14, 22, 35, 17);
			jLabel1.setText("序号");
			jLabel2.setBounds(14, 49, 54, 20);
			jLabel2.setText("商品编码");
			jLabel3.setBounds(379, 22, 59, 17);
			jLabel3.setText("成品名称");
			jLabel4.setBounds(191, 49, 53, 20);
			jLabel4.setText("型号规格");
			jLabel10.setBounds(14, 75, 76, 21);
			jLabel10.setText("成品库存数量");
			jLabel12.setBounds(379, 75, 78, 21);
			jLabel12.setText("成品退换");
			jLabel6.setBounds(379, 49, 78, 21);
			jLabel6.setText("备案计量单位");
			jLabel7.setBounds(191, 75, 72, 19);
			jLabel7.setText("半成品数量");
			jLabel5.setBounds(14, 101, 74, 20);
			jLabel5.setText("成品入库数量");
			jLabel8.setBounds(191, 101, 70, 20);
			jLabel8.setText("残次品数量");
			jLabel9.setBounds(379, 101, 75, 21);
			jLabel9.setText("废品数量");
			jLabel11.setBounds(14, 129, 100, 21);
			jLabel11.setText("本期成品放弃数量");
			jLabel13.setBounds(292, 129, 98, 19);
			jLabel13.setText("成品转出未报数量");
			jLabel14.setBounds(14, 157, 101, 20);
			jLabel14.setText("成品在途数量");
			jLabel15.setBounds(292, 157, 100, 23);
			jLabel15.setText("本期成品内销数量");
			jLabel16.setBounds(14, 183, 100, 21);
			jLabel16.setText("本期成品出库数量");
			jLabel22.setBounds(104, 22, 40, 19);
			jLabel22.setText("版本");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(getJFormattedTextField3(), null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJTextField6(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(getJFormattedTextField1(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getJFormattedTextField2(), null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getJFormattedTextField4(), null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(getJFormattedTextField5(), null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(getJFormattedTextField6(), null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(getJFormattedTextField7(), null);
			jContentPane.add(jLabel16, null);
			jContentPane.add(getJFormattedTextField13(), null);
			jContentPane.add(getJFormattedTextField14(), null);
			jContentPane.add(getJFormattedTextField15(), null);
			jContentPane.add(jLabel22, null);
			jContentPane.add(getJTextField5(), null);
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
			jTextField.setBounds(47, 21, 47, 22);
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
			jTextField1.setBounds(268, 22, 93, 22);
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
			jTextField2.setBounds(89, 49, 92, 22);
			jTextField2.setEditable(false);
		}
		return jTextField2;
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
			jTextField3.setBounds(463, 22, 98, 22);
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
			jTextField4.setBounds(267, 49, 94, 22);
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
			jFormattedTextField3.setBounds(89, 75, 93, 22);
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
			jButton.setBounds(200, 225, 70, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillDataExg(checkExg);
					checkExg = checkCancelAction
					.saveCheckExg(new Request(CommonVars
							.getCurrUser()), checkExg);
					tableModel.updateRow(checkExg);
					DgCheckExg.this.dispose();
				}
			});

		}
		return jButton;
	}

	public void fillDataExg(CheckExg checkExg) { //保存
		checkExg.setMaterStock(strToDouble(jFormattedTextField3.getText()));
		checkExg.setHalfExg(strToDouble(jFormattedTextField.getText()));
		checkExg.setMaterExitChange(strToDouble(jFormattedTextField1.getText()));
		checkExg.setMaterInWare(strToDouble(jFormattedTextField2.getText()));
		checkExg.setOtherExg(strToDouble(jFormattedTextField4.getText()));
		checkExg.setDepose(strToDouble(jFormattedTextField14.getText()));
		checkExg.setThisThrow(strToDouble(jFormattedTextField5.getText()));
		checkExg.setTurnOutNoReport(strToDouble(jFormattedTextField13.getText()));
		checkExg.setMaterByWay(strToDouble(jFormattedTextField6.getText()));
		checkExg.setThisMaterCancel(strToDouble(jFormattedTextField7.getText()));
		checkExg.setThisMaterOutWare(strToDouble(jFormattedTextField15.getText()));
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
			jButton1.setBounds(316, 225, 69, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgCheckExg.this.dispose();
				}
			});

		}
		return jButton1;
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
			jTextField6.setBounds(462, 49, 98, 22);
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
			jFormattedTextField.setBounds(268, 75, 94, 22);
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
			jFormattedTextField1.setBounds(463, 75, 99, 22);
			jFormattedTextField1.setFormatterFactory(getDefaultFormatterFactory());
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
			jFormattedTextField2.setBounds(89, 101, 93, 22);
			jFormattedTextField2.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField2;
	}

	/**

	 * This method initializes jFormattedTextField4	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField4() {
		if (jFormattedTextField4 == null) {
			jFormattedTextField4 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField4.setBounds(269, 101, 94, 22);
			jFormattedTextField4.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField4;
	}

	/**

	 * This method initializes jFormattedTextField5	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField5() {
		if (jFormattedTextField5 == null) {
			jFormattedTextField5 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField5.setBounds(118, 129, 159, 22);
			jFormattedTextField5.setFormatterFactory(getDefaultFormatterFactory());
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
			jFormattedTextField6.setBounds(119, 157, 159, 22);
			jFormattedTextField6.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField6;
	}

	/**

	 * This method initializes jFormattedTextField7	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField7() {
		if (jFormattedTextField7 == null) {
			jFormattedTextField7 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField7.setBounds(397, 157, 163, 22);
			jFormattedTextField7.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField7;
	}

	/**

	 * This method initializes jFormattedTextField13	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField13() {
		if (jFormattedTextField13 == null) {
			jFormattedTextField13 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField13.setBounds(397, 129, 164, 22);
			jFormattedTextField13.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField13;
	}

	/**

	 * This method initializes jFormattedTextField14	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField14() {
		if (jFormattedTextField14 == null) {
			jFormattedTextField14 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField14.setBounds(464, 100, 97, 22);
			jFormattedTextField14.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField14;
	}

	/**

	 * This method initializes jFormattedTextField15	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField15() {
		if (jFormattedTextField15 == null) {
			jFormattedTextField15 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField15.setBounds(120, 183, 157, 22);
			jFormattedTextField15.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField15;
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
			jTextField5.setBounds(142, 22, 41, 22);
		}
		return jTextField5;
	}

            } //  @jve:decl-index=0:visual-constraint="10,10"
