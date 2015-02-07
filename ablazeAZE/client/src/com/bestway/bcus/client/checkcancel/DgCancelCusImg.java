/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CancelImgResult;
import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCancelCusImg extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTableListModel tableModel = null; // 料件

	private CheckCancelAction checkCancelAction = null;

	private CancelImgResult cancelImg = null; // @jve:decl-index=0:

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JFormattedTextField jFormattedTextField = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

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

	private JFormattedTextField jFormattedTextField11 = null;

	private JFormattedTextField jFormattedTextField12 = null;

	private JFormattedTextField jFormattedTextField13 = null;

	private JFormattedTextField tfResultNum = null;

	private JTextField jTextField = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JLabel jLabel = null;

	private JLabel jLabel16 = null;

	private JLabel jLabel17 = null;

	private JFormattedTextField tfInChinaBuyNum = null;

	private JLabel jLabel18 = null;

	private JFormattedTextField tfOtherSourceNum = null;

	private JLabel jLabel19 = null;

	private JFormattedTextField tfResultSumPrice = null;

	private JTextPane jTextPane = null;

	private JButton btnNext = null;

	private JButton btnPrevious = null;
	
	private int rowNumber=0;
	
	private int dataState = DataState.BROWSE;

	private JLabel jLabel171 = null;

	private JLabel jLabel181 = null;

	private JFormattedTextField tfInChinaBuyMoney = null;

	private JFormattedTextField tfOtherSourcePrice = null;

	private JLabel jLabel22 = null;

	private JFormattedTextField tfPrice = null;
	
	private String cavIsIncludeNonBonde = null;

	private ManualDeclareAction manualDecleareAction = null;
	
	private double tempUseSumNum = 0.0;
	
	private double tempLeaveNum = 0.0;
	/**
	 * This is the default constructor
	 */
	public DgCancelCusImg() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		cavIsIncludeNonBonde = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()),
				BcusParameter.CAV_REAL_NUM_INCLUDE_NONBONDE);
		initialize();
		initUIComponents();
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		CustomFormattedTextFieldUtils.setFormatterFactory(jFormattedTextField,
				5);
		CustomFormattedTextFieldUtils.setFormatterFactory(jFormattedTextField1,
				5);
		CustomFormattedTextFieldUtils.setFormatterFactory(jFormattedTextField2,
				5);
		CustomFormattedTextFieldUtils.setFormatterFactory(jFormattedTextField3,
				5);
		CustomFormattedTextFieldUtils.setFormatterFactory(jFormattedTextField4,
				5);
		CustomFormattedTextFieldUtils.setFormatterFactory(jFormattedTextField5,
				5);
		CustomFormattedTextFieldUtils.setFormatterFactory(jFormattedTextField6,
				5);
		CustomFormattedTextFieldUtils.setFormatterFactory(jFormattedTextField7,
				5);
		CustomFormattedTextFieldUtils.setFormatterFactory(jFormattedTextField8,
				5);
		CustomFormattedTextFieldUtils.setFormatterFactory(jFormattedTextField9,
				5);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				jFormattedTextField10, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				jFormattedTextField11, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				jFormattedTextField12, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				jFormattedTextField13, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				tfResultNum, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				tfInChinaBuyNum, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				tfOtherSourceNum, 5);
		CustomFormattedTextFieldUtils.setFormatterFactory(
				tfResultSumPrice, 5);

		CustomFormattedTextFieldUtils.addAutoCalcListener(jFormattedTextField,
				new AutoCalcListener() {
					public void run() {
						double amount = (jFormattedTextField.getValue() == null ? 0.0
								: Double.parseDouble(jFormattedTextField
										.getValue().toString()));
						double unitPrice = (cancelImg.getAveragePrice() == null ? 0.0
								: cancelImg.getAveragePrice());
						jFormattedTextField1.setValue(amount * unitPrice);
					}
				});

		CustomFormattedTextFieldUtils.addAutoCalcListener(jFormattedTextField2,
				new AutoCalcListener() {
					public void run() {
						double amount = (jFormattedTextField2.getValue() == null ? 0.0
								: Double.parseDouble(jFormattedTextField2
										.getValue().toString()));
						double unitPrice = (cancelImg.getAveragePrice() == null ? 0.0
								: cancelImg.getAveragePrice());
						jFormattedTextField3.setValue(amount * unitPrice);
						
						if(Double.parseDouble(jFormattedTextField2.getText()) - tempUseSumNum > 0){
							Double	leaveNum = tempLeaveNum - (Double.parseDouble(jFormattedTextField2.getText()) - tempUseSumNum);
							jFormattedTextField6.setValue(leaveNum);
							if(cancelImg.getUnit().equals("千克")){
								jFormattedTextField8.setValue(leaveNum);
							}else{
								jFormattedTextField8.setValue(0);
							}
						}
						if(Double.parseDouble(jFormattedTextField2.getText()) == tempUseSumNum){
							jFormattedTextField6.setValue(tempLeaveNum);
							if(cancelImg.getUnit().equals("千克")){
								jFormattedTextField8.setValue(tempLeaveNum);
							}else{
								jFormattedTextField8.setValue(0);
							}
						}
						if(Double.parseDouble(jFormattedTextField2.getText()) - tempUseSumNum < 0){
							Double	leaveNum = tempLeaveNum + ( tempUseSumNum - Double.parseDouble(jFormattedTextField2.getText()));
							jFormattedTextField6.setValue(leaveNum);
							if(cancelImg.getUnit().equals("千克")){
								jFormattedTextField8.setValue(leaveNum);
							}else{
								jFormattedTextField8.setValue(0);
							}
						}
						double amount1 = (jFormattedTextField6.getValue() == null ? 0.0
								: Double.parseDouble(jFormattedTextField6
										.getValue().toString()));
						double unitPrice1 = (cancelImg.getAveragePrice() == null ? 0.0
								: cancelImg.getAveragePrice());
						jFormattedTextField7.setValue(amount1 * unitPrice1);
					}
				});

//		CustomFormattedTextFieldUtils.addAutoCalcListener(jFormattedTextField6,
//				new AutoCalcListener() {
//					public void run() {
//						double amount = (jFormattedTextField6.getValue() == null ? 0.0
//								: Double.parseDouble(jFormattedTextField6
//										.getValue().toString()));
//						double unitPrice = (cancelImg.getAveragePrice() == null ? 0.0
//								: cancelImg.getAveragePrice());
//						jFormattedTextField7.setValue(amount * unitPrice);
//					}
//				});

		CustomFormattedTextFieldUtils.addAutoCalcListener(jFormattedTextField5,
				new AutoCalcListener() {
					public void run() {
						double amount = (jFormattedTextField5.getValue() == null ? 0.0
								: Double.parseDouble(jFormattedTextField5
										.getValue().toString()));
						double unitPrice = (cancelImg.getAveragePrice() == null ? 0.0
								: cancelImg.getAveragePrice());
						jFormattedTextField9.setValue(amount * unitPrice);
					}
				});

		CustomFormattedTextFieldUtils.addAutoCalcListener(
				jFormattedTextField11,
				new AutoCalcListener() {
					public void run() {
						double amount = (jFormattedTextField11.getValue() == null ? 0.0
								: Double.parseDouble(jFormattedTextField11
										.getValue().toString()));
						double unitPrice = (cancelImg.getAveragePrice() == null ? 0.0
								: cancelImg.getAveragePrice());
						jFormattedTextField12.setValue(amount * unitPrice);
					}
				});

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(441, 481);
		this.setTitle("修改报核料件内容");
		this.setContentPane(getJContentPane());		
	}

	private void fillWindow() {
		cancelImg = (CancelImgResult)tableModel.getObjectByRow(rowNumber);
		this.setState();
		String emsSeqNum = cancelImg.getEmsSeqNum()==null || "".equals(cancelImg.getEmsSeqNum())?"帐册序号：":"帐册序号：【"+cancelImg.getEmsSeqNum()+"】";
		String sname = cancelImg.getName() == null
				|| "".equals(cancelImg.getName()) ? "名称：" : "名称：【"
				+ cancelImg.getName() + "】";
		String sspec = cancelImg.getSpec() == null
				|| "".equals(cancelImg.getSpec()) ? "规格：" : "规格：【"
				+ cancelImg.getSpec() + "】";
		jLabel.setText(emsSeqNum+"   "+sname +"   "+ sspec);
		jFormattedTextField.setValue(cancelImg.getInnerUseSumNum());
		jFormattedTextField1.setValue(cancelImg.getInnerUseSumPrice());
		jFormattedTextField2.setValue(cancelImg.getUseSumNum());
		jFormattedTextField3.setValue(cancelImg.getUseSumPrice());
		jFormattedTextField4.setValue(cancelImg.getUseSumWeight());
		jFormattedTextField6.setValue(cancelImg.getLeaveNum());
		jFormattedTextField7.setValue(cancelImg.getLeaveSumPrice());
		jFormattedTextField8.setValue(cancelImg.getLeaveSumWeight());
		jFormattedTextField5.setValue(cancelImg.getFactLeaveNum());
		jFormattedTextField9.setValue(cancelImg.getFactLeaveSumPrice());
		jFormattedTextField10.setValue(cancelImg.getFactLeaveSumWeight());
		jTextField.setText(cancelImg.getNote());
		jFormattedTextField11.setValue(cancelImg.getLeftOverImgNum());
		jFormattedTextField12.setValue(cancelImg.getLeftOverImgSumPrice());
		jFormattedTextField13.setValue(cancelImg.getLeftOverImgSumWeight());
		System.out.println("cancelImg.getResultNum():"
				+ cancelImg.getResultNum());
		tfResultNum.setValue(cancelImg.getResultNum());
		tfInChinaBuyNum.setValue(cancelImg.getInChinaBuyNum());
		tfOtherSourceNum.setValue(cancelImg.getOtherSourceNum());
		tfResultSumPrice.setValue(cancelImg.getResultSumPrice());

		tfInChinaBuyMoney.setValue(cancelImg.getInChinaBuyMoney());
		tfOtherSourcePrice.setValue(cancelImg.getOtherSourcePrice());
		tfPrice.setText(String.valueOf(formatBig(cancelImg.getSelfPrice())));

	}

	private Double objToDouble(Object value) { // 转换strToDouble 填充数据
		// try {
		if (value == null) {
			// return new Double("0");
			return null;
		}
		return Double.parseDouble(value.toString());
		// getNumberFormatter().setValueClass(Double.class);
		// return (Double) getNumberFormatter().stringToValue(value);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// // return new Double("0");
		// return null;
		// }
	}

	// private String doubleToStr(Double value) { //转换doubleToStr 取数据
	// try {
	// if (value == null || value.doubleValue() == 0) {
	// return null;
	// }
	// getNumberFormatter().setValueClass(Double.class);
	// return getNumberFormatter().valueToString(value);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
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

	public void fillData(CancelImgResult cancelImg) { // 料件保存
		cancelImg
				.setInnerUseSumNum(objToDouble(jFormattedTextField.getValue()));
		cancelImg.setInnerUseSumPrice(objToDouble(jFormattedTextField1
				.getValue()));
		cancelImg.setUseSumNum(objToDouble(jFormattedTextField2.getValue()));
		cancelImg.setUseSumPrice(objToDouble(jFormattedTextField3.getValue()));
		cancelImg.setUseSumWeight(objToDouble(jFormattedTextField4.getValue()));
		cancelImg.setLeaveNum(objToDouble(jFormattedTextField6.getValue()));
		cancelImg
				.setLeaveSumPrice(objToDouble(jFormattedTextField7.getValue()));
		cancelImg
				.setLeaveSumWeight(objToDouble(jFormattedTextField8.getValue()));
		cancelImg.setFactLeaveNum(objToDouble(jFormattedTextField5.getValue()));
		cancelImg.setFactLeaveSumPrice(objToDouble(jFormattedTextField9
				.getValue()));
		cancelImg.setFactLeaveSumWeight(objToDouble(jFormattedTextField10
				.getValue()));
		cancelImg.setNote(jTextField.getText());
		cancelImg.setLeftOverImgNum(objToDouble(jFormattedTextField11
				.getValue()));
		cancelImg.setLeftOverImgSumPrice(objToDouble(jFormattedTextField12
				.getValue()));
		cancelImg.setLeftOverImgSumWeight(objToDouble(jFormattedTextField13
				.getValue()));
		cancelImg.setResultNum(objToDouble(tfResultNum.getValue()));// 结余数量
		cancelImg
				.setInChinaBuyNum(objToDouble(tfInChinaBuyNum.getValue()));// 国内购买
//		System.out.println("国内购买="+CommonUtils.getDoubleExceptNull(objToDouble(tfInChinaBuyNum.getValue()))
//				* CommonUtils.getDoubleExceptNull(cancelImg.getAveragePrice()));
		cancelImg.setInChinaBuyMoney(objToDouble(tfInChinaBuyMoney.getValue()));
		cancelImg.setOtherSourceNum(objToDouble(tfOtherSourceNum
				.getValue()));// 其他来源
		cancelImg.setResultSumPrice(objToDouble(tfResultSumPrice
				.getValue()));// 结余金额
		cancelImg.setOtherSourcePrice(objToDouble(tfOtherSourcePrice
				.getValue()));// 其他来源金额
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
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jLabel.setText("帐册序号[], 名称：[]，规格：[]");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel.setForeground(java.awt.Color.red);
			jPanel.add(jLabel, null);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel22 = new JLabel();
			jLabel22.setBounds(new Rectangle(233, 214, 76, 23));
			jLabel22.setText("手调单价：");
			jLabel22.setForeground(Color.red);
			jLabel181 = new JLabel();
			jLabel181.setBounds(new Rectangle(234, 272, 74, 23));
			jLabel181.setText("其他来源金额");
			jLabel181.setForeground(Color.blue);
			jLabel171 = new JLabel();
			jLabel171.setBounds(new Rectangle(234, 242, 72, 23));
			jLabel171.setText("国内购买金额");
			jLabel171.setForeground(Color.blue);
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(234, 304, 56, 22));
			jLabel19.setForeground(java.awt.Color.blue);
			jLabel19.setText("结余金额");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(15, 272, 74, 23));
			jLabel18.setForeground(java.awt.Color.blue);
			jLabel18.setText("其他来源数量");
			jLabel17 = new JLabel();
			jLabel17.setBounds(new Rectangle(15, 242, 74, 23));
			jLabel17.setForeground(java.awt.Color.blue);
			jLabel17.setText("国内购买数量");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(16, 304, 43, 19));
			jLabel16.setForeground(java.awt.Color.blue);
			jLabel16.setText("结余数");
			javax.swing.JLabel jLabel15 = new JLabel();

			javax.swing.JLabel jLabel14 = new JLabel();

			javax.swing.JLabel jLabel13 = new JLabel();

			javax.swing.JLabel jLabel12 = new JLabel();

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
			jLabel7.setBounds(15, 70, 67, 19);
			jLabel7.setText("应剩余数量");
			jLabel8.setBounds(15, 100, 78, 19);
			jLabel8.setText("应剩余总价值");
			jLabel9.setBounds(15, 128, 75, 21);
			jLabel9.setText("应剩余总重量");
			jLabel10.setBounds(234, 126, 89, 21);
			jLabel10.setText("实际剩余总价值");
			jLabel11.setBounds(234, 154, 90, 19);
			jLabel11.setText("实际剩余总重量");
			jLabel12.setBounds(14, 156, 71, 21);
			jLabel12.setText("边角料数量");
			jLabel13.setBounds(14, 185, 77, 19);
			jLabel13.setText("边角料总价值");
			jLabel14.setBounds(15, 213, 76, 19);
			jLabel14.setText("边角料总重量");
			jLabel15.setBounds(234, 188, 27, 20);
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
			jPanel1.add(jLabel12, null);
			jPanel1.add(getJFormattedTextField11(), null);
			jPanel1.add(jLabel13, null);
			jPanel1.add(getJFormattedTextField12(), null);
			jPanel1.add(jLabel14, null);
			jPanel1.add(getJFormattedTextField13(), null);
			jPanel1.add(jLabel15, null);
			jPanel1.add(getJTextField(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(jLabel16, null);
			jPanel1.add(getTfResultNum(), null);
			jPanel1.add(jLabel17, null);
			jPanel1.add(getTfInChinaBuyNum(), null);
			jPanel1.add(jLabel18, null);
			jPanel1.add(getTfOtherSourceNum(), null);
			jPanel1.add(jLabel19, null);
			jPanel1.add(getTfResultSumPrice(), null);
			jPanel1.add(getJTextPane(), null);
			jPanel1.add(getBtnNext(), null);
			jPanel1.add(getBtnPrevious(), null);
			jPanel1.add(jLabel171, null);
			jPanel1.add(jLabel181, null);
			jPanel1.add(getTfInChinaBuyMoney(), null);
			jPanel1.add(getTfOtherSourcePrice(), null);
			jPanel1.add(jLabel22, null);
			jPanel1.add(getTfPrice(), null);
			jPanel1.add(getTfResultNum(), null);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField.setBounds(104, 12, 88, 22);
//			jFormattedTextField
//					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField;
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
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setGroupingSize(0);
			decimalFormat.setMaximumFractionDigits(6);
			numberFormatter = new NumberFormatter();
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField1() {
		if (jFormattedTextField1 == null) {
			jFormattedTextField1 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField1.setBounds(104, 41, 88, 22);
		}
		return jFormattedTextField1;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField2() {
		if (jFormattedTextField2 == null) {
			jFormattedTextField2 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField2.setBounds(332, 12, 88, 22);
		}
		return jFormattedTextField2;
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
			jFormattedTextField3.setBounds(332, 41, 88, 22);
		}
		return jFormattedTextField3;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField4() {
		if (jFormattedTextField4 == null) {
			jFormattedTextField4 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField4.setBounds(333, 69, 87, 22);
		}
		return jFormattedTextField4;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField5
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField5() {
		if (jFormattedTextField5 == null) {
			jFormattedTextField5 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField5.setBounds(334, 96, 86, 22);
		}
		return jFormattedTextField5;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField6
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField6() {
		if (jFormattedTextField6 == null) {
			jFormattedTextField6 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField6.setBounds(104, 70, 90, 22);
		}
		return jFormattedTextField6;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField7
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField7() {
		if (jFormattedTextField7 == null) {
			jFormattedTextField7 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField7.setBounds(104, 100, 89, 22);
		}
		return jFormattedTextField7;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField8
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField8() {
		if (jFormattedTextField8 == null) {
			jFormattedTextField8 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField8.setBounds(104, 128, 89, 22);
		}
		return jFormattedTextField8;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField9
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField9() {
		if (jFormattedTextField9 == null) {
			jFormattedTextField9 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField9.setBounds(334, 126, 86, 22);
		}
		return jFormattedTextField9;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField10
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField10() {
		if (jFormattedTextField10 == null) {
			jFormattedTextField10 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField10.setBounds(334, 154, 86, 22);
		}
		return jFormattedTextField10;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField11
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField11() {
		if (jFormattedTextField11 == null) {
			jFormattedTextField11 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField11.setBounds(104, 156, 90, 22);
		}
		return jFormattedTextField11;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField12
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField12() {
		if (jFormattedTextField12 == null) {
			jFormattedTextField12 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField12.setBounds(104, 185, 91, 22);
		}
		return jFormattedTextField12;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField13
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJFormattedTextField13() {
		if (jFormattedTextField13 == null) {
			jFormattedTextField13 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField13.setBounds(104, 213, 91, 22);
		}
		return jFormattedTextField13;
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
			jTextField.setBounds(275, 188, 145, 22);
		}
		return jTextField;
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
			jButton.setBounds(276, 374, 70, 28);
			jButton.setText("保存");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData(cancelImg);
					cancelImg = checkCancelAction.saveCancelImgResult(
							new Request(CommonVars.getCurrUser()), cancelImg);
					tableModel.updateRow(cancelImg);
//					DgCancelCusImg.this.dispose();
                    dataState = DataState.BROWSE;
                    setState();
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
			jButton1.setBounds(352, 373, 70, 28);
			jButton1.setText("修改");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dataState = DataState.EDIT;
					tempUseSumNum = (Double)jFormattedTextField2.getValue();
					tempLeaveNum = (Double)jFormattedTextField6.getValue();
					
                    setState();
				}
			});

		}
		return jButton1;
	}

//	public void setCancelImg(CancelImgResult cancelImg) {
//		this.cancelImg = cancelImg;
//	}

	/**
	 * This method initializes tfResultNum
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfResultNum() {
		if (tfResultNum == null) {
			tfResultNum = new JFormattedTextField();
			tfResultNum.setBounds(new Rectangle(103, 304, 92, 21));
			tfResultNum
					.setFormatterFactory(getDefaultFormatterFactory());
			tfResultNum
			.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {

					double guonei = (tfResultNum.getText() == null || tfResultNum
							.getText().equals("")) ? 0 : Double
							.parseDouble(tfResultNum.getText());
					/**
					 * 2013-2-1国内购买的单价无法确定，且会影响单价表中的系统单价计算，所以国内购买金额由客户自行填写。（所以注释）
					 */
//					double guoneiM = guonei*(cancelImg.getSelfPrice() == null ? 0 : cancelImg
//							.getSelfPrice());
//					tfResultSumPrice.setValue(formatBig(guoneiM));
					getjyNUm();
				}
			});
		}
		return tfResultNum;
	}

	/**
	 * This method initializes tfInChinaBuyNum
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfInChinaBuyNum() {
		if (tfInChinaBuyNum == null) {
			tfInChinaBuyNum = new JFormattedTextField();
			tfInChinaBuyNum.setBounds(new Rectangle(104, 242, 91, 23));
			tfInChinaBuyNum
					.setFormatterFactory(getDefaultFormatterFactory());
			tfInChinaBuyNum
					.addFocusListener(new java.awt.event.FocusAdapter() {
						public void focusLost(java.awt.event.FocusEvent e) {

							double guonei = (tfInChinaBuyNum.getText() == null || tfInChinaBuyNum
									.getText().equals("")) ? 0 : Double
									.parseDouble(tfInChinaBuyNum.getText());
							/**
							 * 2013-2-1国内购买的单价无法确定，且会影响单价表中的系统单价计算，所以国内购买金额由客户自行填写。（所以注释）
							 */
//							double guoneiM = guonei*(cancelImg.getSelfPrice() == null ? 0 : cancelImg
//									.getSelfPrice());
//							tfInChinaBuyMoney.setValue(formatBig(guoneiM));
							tfInChinaBuyMoney.getText();
							getjyNUm();
						}
					});
		}
		return tfInChinaBuyNum;
	}
//
	 
//	private void getLeaveNum(){
//		if(Double.parseDouble(jFormattedTextField2.getText()) - tempUseSumNum > 0){
//		    Double	leaveNum = tempLeaveNum - (Double.parseDouble(jFormattedTextField2.getText()) - tempUseSumNum);
//		}
//	}
	
	private void getjyNUm() {
		double shijisy = (jFormattedTextField6.getText() == null || jFormattedTextField5
				.getText().equals("")) ? 0 : Double
				.parseDouble(jFormattedTextField6.getText());
		double guonei = (tfInChinaBuyNum.getText() == null || tfInChinaBuyNum
				.getText().equals("")) ? 0 : Double
				.parseDouble(tfInChinaBuyNum.getText());
		double othernum = (tfOtherSourceNum.getText() == null || tfOtherSourceNum
				.getText().equals("")) ? 0 : Double
				.parseDouble(tfOtherSourceNum.getText());
		tfResultNum.setValue(shijisy  + guonei + othernum);
		
		double guoneiMoney = (tfInChinaBuyMoney.getText() == null || tfInChinaBuyMoney
				.getText().equals("")) ? 0 : Double
						.parseDouble(tfInChinaBuyMoney.getText());
		double otherMoney = (tfOtherSourcePrice.getText() == null || tfOtherSourcePrice
				.getText().equals("")) ? 0 : Double
						.parseDouble(tfOtherSourcePrice.getText());
		double money = (shijisy)
				* (cancelImg.getSelfPrice() == null ? 0 : cancelImg
						.getSelfPrice())+guoneiMoney+otherMoney;
		tfResultSumPrice.setValue(formatBig(money));
		
		if("1".equals(cavIsIncludeNonBonde)) {
			jFormattedTextField5.setValue(tfResultNum.getValue());
			jFormattedTextField9.setValue(tfResultSumPrice.getValue());
		}
	}

	private Double formatBig(Object num) {
		int digits = 5;
		double unroundedValue = 0;
		if (num == null) {
			return Double.valueOf(0);
		}
		unroundedValue = Double.parseDouble(num.toString());
		int x = 1;
		for (int i = 0; i < digits; i++) {
			x = x * 10;
		}
		double intPortion = Math.floor(unroundedValue);
		double unroundedDecimalPortion = (unroundedValue - intPortion);
		double roundedDecimalPortion = Math.round(unroundedDecimalPortion * x);
		roundedDecimalPortion = roundedDecimalPortion / x;
		double total = intPortion + roundedDecimalPortion;
		return new Double(total);
	}
	
	private void calcuteReal() {
		
	}

	/**
	 * This method initializes tfOtherSourceNum
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfOtherSourceNum() {
		if (tfOtherSourceNum == null) {
			tfOtherSourceNum = new JFormattedTextField();
			tfOtherSourceNum.setBounds(new Rectangle(104, 272, 91, 23));
			tfOtherSourceNum
					.setFormatterFactory(getDefaultFormatterFactory());
			tfOtherSourceNum
					.addFocusListener(new java.awt.event.FocusAdapter() {
						public void focusLost(java.awt.event.FocusEvent e) {
							double other = (tfOtherSourceNum.getText() == null || tfOtherSourceNum
									.getText().equals("")) ? 0 : Double
									.parseDouble(tfOtherSourceNum.getText());
							double otherM = other*(cancelImg.getSelfPrice() == null ? 0 : cancelImg
									.getSelfPrice());
							tfOtherSourcePrice.setValue(formatBig(otherM));
							getjyNUm();
						}
					});
		}
		return tfOtherSourceNum;
	}

	/**
	 * This method initializes tfResultSumPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfResultSumPrice() {
		if (tfResultSumPrice == null) {
			tfResultSumPrice = new JFormattedTextField();
			tfResultSumPrice.setBounds(new Rectangle(304, 303, 116, 22));
			tfResultSumPrice
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfResultSumPrice;
	}

	/**
	 * This method initializes jTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setBounds(new Rectangle(13, 342, 257, 60));
			
			if("1".equals(cavIsIncludeNonBonde)) {
				jTextPane.setText("实际剩余数量=结余数=应剩余数量+国内购买+其他来源\n"
						+ "结余数，结余金额作为下一期核销期初数，期初金额 ");
			} else {
				jTextPane.setText("结余数 = 应剩余数量 + 国内购买 + 其他来源\n"
						+ "结余数，结余金额作为下一期核销期初数，期初金额 ");
			}
			
			
			jTextPane.setForeground(java.awt.Color.red);
			jTextPane.setEnabled(true);
			jTextPane.setBackground(java.awt.SystemColor.window);
			jTextPane.setEditable(false);
		}
		return jTextPane;
	}

	/**
	 * This method initializes btnNext	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(new Rectangle(351, 339, 70, 28));
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
			btnPrevious.setBounds(new Rectangle(275, 339, 70, 28));
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
		jFormattedTextField12.setEditable(dataState==DataState.BROWSE?false:true);
		jFormattedTextField13.setEditable(dataState==DataState.BROWSE?false:true);
		tfResultNum.setEditable(dataState==DataState.BROWSE?false:false);
		tfInChinaBuyNum.setEditable(dataState==DataState.BROWSE?false:true);
		tfOtherSourceNum.setEditable(dataState==DataState.BROWSE?false:true);
		tfResultSumPrice.setEditable(dataState==DataState.BROWSE?false:true);
		tfInChinaBuyMoney.setEditable(dataState==DataState.BROWSE?false:true);
		tfOtherSourcePrice.setEditable(dataState==DataState.BROWSE?false:true);
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

	/**
	 * This method initializes tfInChinaBuyMoney	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JFormattedTextField getTfInChinaBuyMoney() {
		if (tfInChinaBuyMoney == null) {
			tfInChinaBuyMoney = new JFormattedTextField();
			tfInChinaBuyMoney.setBounds(new Rectangle(315, 242, 103, 23));
			tfInChinaBuyMoney.setFormatterFactory(getDefaultFormatterFactory());
			tfInChinaBuyMoney.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					getjyNUm();
				}
			});
		}
		return tfInChinaBuyMoney;
	}

	/**
	 * This method initializes tfOtherSourcePrice	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JFormattedTextField getTfOtherSourcePrice() {
		if (tfOtherSourcePrice == null) {
			tfOtherSourcePrice = new JFormattedTextField();
			tfOtherSourcePrice.setBounds(new Rectangle(315, 272, 103, 23));
			tfOtherSourcePrice.setFormatterFactory(getDefaultFormatterFactory());
			tfOtherSourcePrice.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					getjyNUm();
				}
			});
		}
		return tfOtherSourcePrice;
	}

	/**
	 * This method initializes tfPrice	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getTfPrice() {
		if (tfPrice == null) {
			tfPrice = new JFormattedTextField();
			tfPrice.setBounds(new Rectangle(313, 215, 105, 23));
			tfPrice.setFormatterFactory(getDefaultFormatterFactory());
			tfPrice.setEditable(false);
		}
		return tfPrice;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
