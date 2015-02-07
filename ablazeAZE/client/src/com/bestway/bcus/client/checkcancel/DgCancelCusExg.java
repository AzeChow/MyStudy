
/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;


import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CancelCusExgResult;
import com.bestway.bcus.checkcancel.entity.CancelExgResult;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import java.awt.BorderLayout;
import java.text.ParseException;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.text.DecimalFormat;
import java.awt.Rectangle;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCancelCusExg extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;  //成品
	private JTableListModel tableModel = null;
	private CheckCancelAction checkCancelAction = null;
	private CancelExgResult cancelExg = null;

	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JFormattedTextField jFormattedTextField = null;
	private DefaultFormatterFactory defaultFormatterFactory = null;   //  @jve:decl-index=0:parse
	private NumberFormatter numberFormatter = null;   //  @jve:decl-index=0:parse
	private JFormattedTextField jFormattedTextField1 = null;
	private JFormattedTextField jFormattedTextField2 = null;
	private JFormattedTextField jFormattedTextField3 = null;
	private JFormattedTextField jFormattedTextField4 = null;
	private JFormattedTextField jFormattedTextField5 = null;
	private JFormattedTextField jFormattedTextField6 = null;
	private JFormattedTextField jFormattedTextField7 = null;
	private JFormattedTextField jFormattedTextField8 = null;
	private JFormattedTextField jFormattedTextField9 = null;
	private JFormattedTextField jFormattedTextField10 = null;
	private JTextField jTextField = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JLabel jLabel = null;
	private JLabel jLabel12 = null;
	private JFormattedTextField jFormattedTextField11 = null;
    private int rowNumber=0;
	
	private int dataState = DataState.BROWSE;
	private JButton btnNext = null;
	private JButton btnPrevious = null;
	/**
	 * This is the default constructor
	 */
	public DgCancelCusExg() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
		.getApplicationContext().getBean("checkCancelAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(442, 318);
		this.setTitle("修改报核成品内容");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				fillWindow();
			}
		});
	}
	private void fillWindow() {
		cancelExg =(CancelExgResult)tableModel.getObjectByRow(rowNumber); 
		this.setState();
		String emsSeqNum = cancelExg.getEmsSeqNum()==null || "".equals(cancelExg.getEmsSeqNum())?"帐册序号：":"帐册序号：【"+cancelExg.getEmsSeqNum()+"】";
		String sname = cancelExg.getName() == null || "".equals(cancelExg.getName())? "名称：":"名称：【"+ cancelExg.getName()+"】";
		String sspec = cancelExg.getSpec() == null || "".equals(cancelExg.getSpec())? "规格：":"规格：【"+ cancelExg.getSpec()+"】";
		jLabel.setText(emsSeqNum+"   "+sname +"   "+ sspec);
		jFormattedTextField.setText(doubleToStr(cancelExg.getInnerUseSumNum()));
		jFormattedTextField1.setText(doubleToStr(cancelExg.getInnerUseSumPrice()));
		jFormattedTextField2.setText(doubleToStr(cancelExg.getUseSumNum()));
		jFormattedTextField3.setText(doubleToStr(cancelExg.getUseSumPrice()));
		jFormattedTextField4.setText(doubleToStr(cancelExg.getUseSumWeight()));
		jFormattedTextField6.setText(doubleToStr(cancelExg.getLeftOverImgNum()));
		jFormattedTextField7.setText(doubleToStr(cancelExg.getLeftOverImgSumPrice()));
		jFormattedTextField8.setText(doubleToStr(cancelExg.getLeftOverImgSumWeight()));
		jFormattedTextField5.setText(doubleToStr(cancelExg.getFactLeaveNum()));
		jFormattedTextField9.setText(doubleToStr(cancelExg.getFactLeaveSumPrice()));
		jFormattedTextField10.setText(doubleToStr(cancelExg.getFactLeaveSumWeight()));
		jFormattedTextField11.setText(doubleToStr(cancelExg.getResultNum()));
		jTextField.setText(cancelExg.getNote());
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
	public void fillData(CancelExgResult cancelExg) { //成品保存
		cancelExg.setInnerUseSumNum(strToDouble(jFormattedTextField.getText()));
		cancelExg.setInnerUseSumPrice(strToDouble(jFormattedTextField1.getText()));
		cancelExg.setUseSumNum(strToDouble(jFormattedTextField2.getText()));
		cancelExg.setUseSumPrice(strToDouble(jFormattedTextField3.getText()));
		cancelExg.setUseSumWeight(strToDouble(jFormattedTextField4.getText()));
		cancelExg.setLeftOverImgNum(strToDouble(jFormattedTextField6.getText()));
		cancelExg.setLeftOverImgSumPrice(strToDouble(jFormattedTextField7.getText()));
		cancelExg.setLeftOverImgSumWeight(strToDouble(jFormattedTextField8.getText()));
		cancelExg.setFactLeaveNum(strToDouble(jFormattedTextField5.getText()));
		cancelExg.setFactLeaveSumPrice(strToDouble(jFormattedTextField9.getText()));
		cancelExg.setFactLeaveSumWeight(strToDouble(jFormattedTextField10.getText()));
		cancelExg.setResultNum(strToDouble(jFormattedTextField11.getText()));
		cancelExg.setNote(jTextField.getText());
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
		rowNumber = tableModel.getCurrRowCount();
		fillWindow();
	}
	/**

	 * This method initializes jPanel	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jLabel.setText("帐册序号[], 名称：[]，规格：[]");
			jLabel.setForeground(java.awt.Color.red);
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jPanel.add(jLabel, null);
		}
		return jPanel;
	}

	/**

	 * This method initializes jPanel1	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel12 = new JLabel();
			jLabel12.setBounds(new java.awt.Rectangle(233,185,44,21));
			jLabel12.setText("结余数");
			javax.swing.JLabel jLabel15 = new JLabel();

			javax.swing.JLabel jLabel11 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel9 = new JLabel();

			javax.swing.JLabel jLabel8 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel1.setText("内销总数量");
			jLabel1.setBounds(15, 12, 67, 18);
			jLabel2.setBounds(15, 41, 67, 19);
			jLabel2.setText("内销总价值");
			jLabel3.setBounds(234, 12, 68, 20);
			jLabel3.setText("消耗总数量");
			jLabel4.setBounds(234, 41, 68, 20);
			jLabel4.setText("消耗总价值");
			jLabel5.setBounds(234, 68, 66, 21);
			jLabel5.setText("消耗总重量");
			jLabel6.setBounds(234, 96, 78, 20);
			jLabel6.setText("实际剩余数量");
			jLabel7.setBounds(15, 96, 67, 19);
			jLabel7.setText("残次品数量");
			jLabel8.setBounds(15, 126, 78, 19);
			jLabel8.setText("残次品总价值");
			jLabel9.setBounds(15, 154, 75, 21);
			jLabel9.setText("残次品总重量");
			jLabel10.setBounds(234, 126, 89, 21);
			jLabel10.setText("实际剩余总价值");
			jLabel11.setBounds(234, 154, 90, 19);
			jLabel11.setText("实际剩余总重量");
			jLabel15.setBounds(14, 188, 27, 20);
			jLabel15.setText("备注");
			jPanel1.add(jLabel1, null);
			jPanel1.add(getJFormattedTextField(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getJFormattedTextField1(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getJFormattedTextField2(), null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(getJFormattedTextField3(), null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(getJFormattedTextField4(), null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(getJFormattedTextField5(), null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(getJFormattedTextField6(), null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(getJFormattedTextField7(), null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(getJFormattedTextField8(), null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(getJFormattedTextField9(), null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(getJFormattedTextField10(), null);
			jPanel1.add(jLabel15, null);
			jPanel1.add(getJTextField(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(jLabel12, null);
			jPanel1.add(getJFormattedTextField11(), null);
			jPanel1.add(getBtnNext(), null);
			jPanel1.add(getBtnPrevious(), null);
		}
		return jPanel1;
	}

	/**

	 * This method initializes jFormattedTextField	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField.setBounds(104, 12, 88, 22);
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
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setGroupingSize(0);
			decimalFormat.setMaximumFractionDigits(6);
			numberFormatter = new NumberFormatter();
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

	/**

	 * This method initializes jFormattedTextField1	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField1() {
		if (jFormattedTextField1 == null) {
			jFormattedTextField1 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField1.setBounds(104, 41, 88, 22);
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
			jFormattedTextField2.setBounds(332, 12, 88, 22);
		}
		return jFormattedTextField2;
	}

	/**

	 * This method initializes jFormattedTextField3	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField3() {
		if (jFormattedTextField3 == null) {
			jFormattedTextField3 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField3.setBounds(332, 41, 88, 22);
		}
		return jFormattedTextField3;
	}

	/**

	 * This method initializes jFormattedTextField4	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField4() {
		if (jFormattedTextField4 == null) {
			jFormattedTextField4 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField4.setBounds(332, 69, 87, 22);
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
			jFormattedTextField5.setBounds(332, 96, 86, 22);
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
			jFormattedTextField6.setBounds(104, 96, 90, 22);
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
			jFormattedTextField7.setBounds(104, 126, 89, 22);
		}
		return jFormattedTextField7;
	}

	/**

	 * This method initializes jFormattedTextField8	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField8() {
		if (jFormattedTextField8 == null) {
			jFormattedTextField8 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField8.setBounds(104, 154, 89, 22);
		}
		return jFormattedTextField8;
	}

	/**

	 * This method initializes jFormattedTextField9	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField9() {
		if (jFormattedTextField9 == null) {
			jFormattedTextField9 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField9.setBounds(332, 126, 86, 22);
		}
		return jFormattedTextField9;
	}

	/**

	 * This method initializes jFormattedTextField10	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField10() {
		if (jFormattedTextField10 == null) {
			jFormattedTextField10 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField10.setBounds(332, 154, 86, 22);
		}
		return jFormattedTextField10;
	}

	/**

	 * This method initializes jTextField	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(46, 188, 146, 22);
		}
		return jTextField;
	}

	/**

	 * This method initializes jButton	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(307, 220, 58, 28);
			jButton.setText("保存");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					fillData(cancelExg);
					cancelExg = checkCancelAction
					.saveCancelExgResult(new Request(CommonVars
							.getCurrUser()), cancelExg);
					tableModel.updateRow(cancelExg);
//					DgCancelCusExg.this.dispose();
					 dataState = DataState.BROWSE;
	                    setState();

				}
			});

		}
		return jButton;
	}

	/**

	 * This method initializes jButton1	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(92, 220, 58, 28);
			jButton1.setText("修改");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					dataState = DataState.EDIT;
                    setState();

				}
			});

		}
		return jButton1;
	}

	public void setCancelExg(CancelExgResult cancelExg) {
		this.cancelExg = cancelExg;
	}

	/**
	 * This method initializes jFormattedTextField11	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JFormattedTextField getJFormattedTextField11() {
		if (jFormattedTextField11 == null) {
			jFormattedTextField11 = new JFormattedTextField();
			jFormattedTextField11.setBounds(new java.awt.Rectangle(285,185,133,22));
			jFormattedTextField11.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField11;
	}

	/**
	 * This method initializes btnNext	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(new Rectangle(231, 220, 70, 28));
			btnNext.setText("下一笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					rowNumber++;
					fillWindow();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes btnPrevious	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(new Rectangle(155, 220, 70, 28));
			btnPrevious.setText("上一笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					rowNumber--;
					fillWindow();
				}
			});
		}
		return btnPrevious;
	}
	
	private void setState(){
		jButton.setEnabled(dataState==DataState.BROWSE?false:true);		
		jButton1.setEnabled(dataState==DataState.BROWSE?true:false);
		
		jFormattedTextField.setEditable(dataState==DataState.BROWSE?false:true);
		jFormattedTextField1.setEditable(dataState==DataState.BROWSE?false:true);
		jFormattedTextField2.setEditable(dataState==DataState.BROWSE?false:true);
		jFormattedTextField3.setEditable(dataState==DataState.BROWSE?false:true);
		jFormattedTextField6.setEditable(dataState==DataState.BROWSE?false:true);
		jFormattedTextField4.setEditable(dataState==DataState.BROWSE?false:true);
		jFormattedTextField7.setEditable(dataState==DataState.BROWSE?false:true);
		jFormattedTextField5.setEditable(dataState==DataState.BROWSE?false:true);
		jFormattedTextField8.setEditable(dataState==DataState.BROWSE?false:true);
		jFormattedTextField9.setEditable(dataState==DataState.BROWSE?false:true);
		jFormattedTextField11.setEditable(dataState==DataState.BROWSE?false:true);
		jFormattedTextField10.setEditable(dataState==DataState.BROWSE?false:true);		
		jTextField.setEditable(dataState==DataState.BROWSE?false:true);
		if(rowNumber<=0){
			btnPrevious.setEnabled(false);
		}else{
			btnPrevious.setEnabled(dataState==DataState.BROWSE?true:false);
		}
		if(rowNumber>=tableModel.getList().size()-1){
			btnNext.setEnabled(false);
		}else{
			btnNext.setEnabled(dataState==DataState.BROWSE?true:false);
		}	
		
	}


} //  @jve:decl-index=0:visual-constraint="10,10"
