/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.text.ParseException;

import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

import javax.swing.JButton;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JPanel;
import java.text.DecimalFormat;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgEmsHeadH2kImgExgPrice extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField3 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private ManualDeclareAction manualdeclearAction = null;

	private JTableListModel tableModel = null;

	private EmsHeadH2kImg emsImg = null; //料件

	private EmsHeadH2kVersion emsVersion = null; //成品
	
	private EmsHeadH2k emsHeadH2k = null; //表头

	private Curr curr = null; //海关币制
	private Complex complex = null;//商品编码库

	private LevyMode levyMode = null; //征免方式
	private boolean isChange = false; 

	private Unit unit = null; //计量单位

	private boolean isImg = true; //是否为料件还是成品

	private DefaultFormatterFactory defaultFormatterFactory = null; //  @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; //  @jve:decl-index=0:parse

	private JLabel jLabel15 = null;
	private JFormattedTextField jFormattedTextField5 = null;
	private JLabel jLabel16 = null;
	private JFormattedTextField jFormattedTextField6 = null;
	private boolean isModityComplex = false;
	private String seqNum = null;

	private JLabel jLabel17 = null;

	private JFormattedTextField jFormattedTextField7 = null;

	private JLabel jLabel18 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel19 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	/**
	 * This is the default constructor
	 */
	public DgEmsHeadH2kImgExgPrice() {
		super();
		initialize();
		manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(521, 257);
		this.setTitle("编辑电子帐册料件");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				if (tableModel.getCurrentRow() != null) {
					if (DgEmsHeadH2kImgExgPrice.this.isImg) {
						emsImg = (EmsHeadH2kImg) tableModel
								.getCurrentRow();
						curr = emsImg.getCurr();
						seqNum = String.valueOf(emsImg.getSeqNum());
						complex = emsImg.getComplex();
						levyMode = emsImg.getLevyMode();
						unit = emsImg.getUnit();
						DgEmsHeadH2kImgExgPrice.this.setTitle("编辑电子帐册料件");
						fillWindowImg();
					} else {
						emsVersion = (EmsHeadH2kVersion) tableModel
								.getCurrentRow();
						curr = emsVersion.getEmsHeadH2kExg().getCurr();
						seqNum = String.valueOf(emsVersion.getEmsHeadH2kExg().getSeqNum());
						complex = emsVersion.getEmsHeadH2kExg().getComplex();
						levyMode = emsVersion.getEmsHeadH2kExg().getLevyMode();
						unit = emsVersion.getEmsHeadH2kExg().getUnit();
						DgEmsHeadH2kImgExgPrice.this.setTitle("编辑电子帐册成品");
						fillWindowExg();
					}
				}
			}
		});
	}

	private void fillWindowImg() {
		jTextField.setText(String.valueOf(emsImg.getSeqNum()));
		if (complex!=null){
	    	jTextField1.setText(complex.getCode());    
		}
		else
			jTextField1.setText("");
		jTextField2.setText(emsImg.getName());
		jTextField3.setText(emsImg.getSpec());
		jFormattedTextField5.setText(doubleToStr(emsImg.getUnitNetWeight()));
		jFormattedTextField6.setText(doubleToStr(emsImg.getUnitGrossWeight()));
		
		jFormattedTextField7.setText(doubleToStr(emsImg.getFactoryPrice()));
		jCalendarComboBox.setDate(emsImg.getBeginDate());
		jCalendarComboBox1.setDate(emsImg.getEndDate());
		
	}

	private void fillWindowExg() {
		jTextField.setText(String.valueOf(emsVersion.getEmsHeadH2kExg().getSeqNum()));
	    if (complex!=null)
		  jTextField1.setText(complex.getCode());
		else
			jTextField1.setText("");
		jTextField2.setText(emsVersion.getEmsHeadH2kExg().getName());
		jTextField3.setText(emsVersion.getEmsHeadH2kExg().getSpec());
		
		jFormattedTextField5.setText(doubleToStr(emsVersion.getUnitNetWeight()));
		jFormattedTextField6.setText(doubleToStr(emsVersion.getUnitGrossWeight()));
		
		jFormattedTextField7.setText(doubleToStr(emsVersion.getFactoryPrice()));
		jCalendarComboBox.setDate(emsVersion.getBeginDate());
		jCalendarComboBox1.setDate(emsVersion.getEndDate());
		
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel19 = new JLabel();
			jLabel19.setBounds(new java.awt.Rectangle(339,123,66,22));
			jLabel19.setForeground(new java.awt.Color(0,102,0));
			jLabel19.setText("结束有效期");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new java.awt.Rectangle(188,123,66,18));
			jLabel18.setForeground(new java.awt.Color(0,102,0));
			jLabel18.setText("开始有效期");
			jLabel17 = new JLabel();
			jLabel17.setBounds(new java.awt.Rectangle(29,123,79,20));
			jLabel17.setForeground(new java.awt.Color(0,102,0));
			jLabel17.setText("工厂申报单价");
			jLabel16 = new JLabel();
			jLabel15 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("料件序号");
			jLabel.setBounds(27, 22, 53, 18);
			jLabel1.setBounds(278, 24, 60, 17);
			jLabel1.setText("商品编码");
			jLabel2.setBounds(27, 48, 54, 20);
			jLabel2.setText("商品名称");
			jLabel3.setBounds(278, 50, 59, 17);
			jLabel3.setText("型号规格");
			jLabel15.setBounds(28, 86, 64, 24);
			jLabel15.setText("单位净重");
			jLabel15.setForeground(new java.awt.Color(0,102,0));
			jLabel16.setBounds(280, 86, 70, 21);
			jLabel16.setText("单位毛重");
			jLabel16.setForeground(new java.awt.Color(51,102,0));
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(getJFormattedTextField5(), null);
			jContentPane.add(jLabel16, null);
			jContentPane.add(getJFormattedTextField6(), null);
			jContentPane.add(jLabel17, null);
			jContentPane.add(getJFormattedTextField7(), null);
			jContentPane.add(jLabel18, null);
			jContentPane.add(getJCalendarComboBox(), null);
			jContentPane.add(jLabel19, null);
			jContentPane.add(getJCalendarComboBox1(), null);
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
			jTextField.setBounds(99, 22, 123, 22);
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
			jTextField1.setBounds(365, 23, 121, 22);
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
			jTextField2.setBounds(99, 48, 123, 22);
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
			jTextField3.setBounds(365, 49, 120, 22);
			jTextField3.setEditable(false);
		}
		return jTextField3;
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
			jButton.setBounds(184, 161, 70, 25);
			jButton.setText("确定");

			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (DgEmsHeadH2kImgExgPrice.this.isImg) {
						fillDataImg(emsImg);
						emsImg = manualdeclearAction
								.saveEmsHeadH2kImg(new Request(
										CommonVars.getCurrUser()), emsImg);
						tableModel.updateRow(emsImg);
						DgEmsHeadH2kImgExgPrice.this.dispose();
					} else {
						fillDataExg(emsVersion);
						emsVersion = manualdeclearAction
								.saveEmsHeadH2kVersion(new Request(
										CommonVars.getCurrUser()), emsVersion);
						tableModel.updateRow(emsVersion);
						DgEmsHeadH2kImgExgPrice.this.dispose();
					}
				}
			});

		}
		return jButton;
	}

	public void fillDataImg(EmsHeadH2kImg emsAfter) { //保存
/*		EmsHeadH2kImg old = new EmsHeadH2kImg();
		old = (EmsHeadH2kImg) emsAfter.clone();	
		emsAfter.setUnit(unit);
		if (isModityComplex){
		    emsAfter.setComplex(complex);
		}
		emsAfter.setSpec(jTextField3.getText());
		*/
		emsAfter.setUnitNetWeight(strToDouble(jFormattedTextField5.getText()));
		emsAfter.setUnitGrossWeight(strToDouble(jFormattedTextField6.getText()));
		
		emsAfter.setFactoryPrice(strToDouble(jFormattedTextField7.getText()));
		emsAfter.setBeginDate(jCalendarComboBox.getDate());
		emsAfter.setEndDate(jCalendarComboBox1.getDate());
		
		/*emsAfter.setCurr(curr);
		emsAfter.setLevyMode(levyMode);*/
		
		
	}

	public void fillDataExg(EmsHeadH2kVersion emsAfter) { //保存
		/*emsVersion.setUnit(unit);
		if (isModityComplex){
		    emsAfter.setComplex(complex);
		}
		emsAfter.setSpec(jTextField3.getText());*/
		
		emsAfter.setUnitNetWeight(strToDouble(jFormattedTextField5.getText()));
		emsAfter.setUnitGrossWeight(strToDouble(jFormattedTextField6.getText()));
		
		emsAfter.setFactoryPrice(strToDouble(jFormattedTextField7.getText()));
		emsAfter.setBeginDate(jCalendarComboBox.getDate());
		emsAfter.setEndDate(jCalendarComboBox1.getDate());
		
		/*emsAfter.setCurr(curr);
		emsAfter.setLevyMode(levyMode);*/

		
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
			jButton1.setBounds(271, 161, 69, 25);
			jButton1.setText("取消");

			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEmsHeadH2kImgExgPrice.this.dispose();

				}
			});

		}
		return jButton1;
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
	 * @return Returns the isImg.
	 */
	public boolean isImg() {
		return isImg;
	}

	/**
	 * @param isImg
	 *            The isImg to set.
	 */
	public void setImg(boolean isImg) {
		this.isImg = isImg;
	}

	/**
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}
	/**
	 * @param isChange The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}
	/**
	 * @return Returns the emsHeadH2k.
	 */
	public EmsHeadH2k getEmsHeadH2k() {
		return emsHeadH2k;
	}
	/**
	 * @param emsHeadH2k The emsHeadH2k to set.
	 */
	public void setEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}
	/**
	 * This method initializes jFormattedTextField5	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */    
	private JFormattedTextField getJFormattedTextField5() {
		if (jFormattedTextField5 == null) {
			jFormattedTextField5 = new JFormattedTextField();
			jFormattedTextField5.setBounds(102, 86, 125, 22);
			jFormattedTextField5
			.setFormatterFactory(getDefaultFormatterFactory());
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
			jFormattedTextField6 = new JFormattedTextField();
			jFormattedTextField6.setBounds(370, 86, 121, 22);
			jFormattedTextField6
			.setFormatterFactory(getDefaultFormatterFactory());
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
			jFormattedTextField7 = new JFormattedTextField();
			jFormattedTextField7.setBounds(new java.awt.Rectangle(110,123,75,22));
			jFormattedTextField7.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField7;
	}

	/**
	 * This method initializes jCalendarComboBox	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new java.awt.Rectangle(253,123,83,23));
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jCalendarComboBox1	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(new java.awt.Rectangle(405,123,85,22));
		}
		return jCalendarComboBox1;
	}
   } //  @jve:decl-index=0:visual-constraint="10,10"
