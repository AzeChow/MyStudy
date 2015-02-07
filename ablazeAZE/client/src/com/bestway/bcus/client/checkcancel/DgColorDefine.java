/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.ColorSet;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Parame;
import com.bestway.common.Request;
import com.bestway.common.constant.ApplyToCustomType;
import com.bestway.common.materialbase.entity.ParaSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DefaultFormatter;
import java.text.DecimalFormat;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgColorDefine extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private CommonBaseCodeAction commonBaseCodeObj = null;

	private boolean isAdd = true;

	private ParaSet obj = null; //

	private JTextField jTextField10 = null;

	private JTextField jTextField11 = null;

	private JTextField jTextField12 = null;

	private JTextField jTextField13 = null;

	private JTextField jTextField14 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;
	
	private CheckCancelAction checkCancelAction = null;

	private JFormattedTextField jFormattedTextField = null;

	private JFormattedTextField jFormattedTextField1 = null;

	private JFormattedTextField jFormattedTextField2 = null;

	private JFormattedTextField jFormattedTextField3 = null;

	private JFormattedTextField jFormattedTextField4 = null;

	private JFormattedTextField jFormattedTextField5 = null;

	private JFormattedTextField jFormattedTextField6 = null;

	private JFormattedTextField jFormattedTextField7 = null;

	private JFormattedTextField jFormattedTextField8 = null;

	private JFormattedTextField jFormattedTextField9 = null;
	
	private DefaultFormatterFactory defaultFormatterFactory = null; //  @jve:decl-index=0:parse
	private NumberFormatter numberFormatter = null; //  @jve:decl-index=0:parse
	

	/**
	 * This is the default constructor
	 */
	public DgColorDefine() {
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
		this.setContentPane(getJPanel2());
		this.setSize(395, 303);
		this.setTitle("差异颜色自定义设置");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				List list = checkCancelAction.findAllColor(new Request(CommonVars.getCurrUser()));
				for (int i=0;i<list.size();i++){
					ColorSet obj = (ColorSet) list.get(i);
					int x =  obj.getColor().intValue();
					if (x == ColorSet.GREEN){
						jFormattedTextField.setText(doubleToStr(obj.getBeginNum()));
						jFormattedTextField1.setText(doubleToStr(obj.getEndNum()));
					} else if (x == ColorSet.DEEPGREEN) {
						jFormattedTextField2.setText(doubleToStr(obj.getBeginNum()));
						jFormattedTextField3.setText(doubleToStr(obj.getEndNum()));
					} else if (x == ColorSet.YELLOW) {
						jFormattedTextField4.setText(doubleToStr(obj.getBeginNum()));
						jFormattedTextField5.setText(doubleToStr(obj.getEndNum()));
					} else if (x == ColorSet.DEEPYELLOW) {
						jFormattedTextField6.setText(doubleToStr(obj.getBeginNum()));
						jFormattedTextField7.setText(doubleToStr(obj.getEndNum()));
					} else if (x == ColorSet.RED) {
						jFormattedTextField8.setText(doubleToStr(obj.getBeginNum()));
						jFormattedTextField9.setText(doubleToStr(obj.getEndNum()));
					}
				}
				
			}
		});
	}

	public void setVisible(boolean b){
		if(b){
			initUICompoments();
			if (isAdd == false) {
				if (tableModel.getCurrentRow() != null) {
					obj = (ParaSet) tableModel.getCurrentRow();
				}
				fillWindow();
			}
		}
		super.setVisible(b);
	}
	private void initUICompoments() {
		
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(116,168,24,22));
			jLabel4.setText("至");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(116,132,23,19));
			jLabel3.setText("至");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(116,95,24,23));
			jLabel2.setText("至");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(115,60,24,20));
			jLabel1.setText("至");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(116,23,25,20));
			jLabel.setText("至");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJTextField10(), null);
			jPanel2.add(getJTextField11(), null);
			jPanel2.add(getJTextField12(), null);
			jPanel2.add(getJTextField13(), null);
			jPanel2.add(getJTextField14(), null);
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJFormattedTextField(), null);
			jPanel2.add(getJFormattedTextField1(), null);
			jPanel2.add(getJFormattedTextField2(), null);
			jPanel2.add(getJFormattedTextField3(), null);
			jPanel2.add(getJFormattedTextField4(), null);
			jPanel2.add(getJFormattedTextField5(), null);
			jPanel2.add(getJFormattedTextField6(), null);
			jPanel2.add(getJFormattedTextField7(), null);
			jPanel2.add(getJFormattedTextField8(), null);
			jPanel2.add(getJFormattedTextField9(), null);
		}
		return jPanel2;
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
			jButton.setBounds(105, 222, 75, 26);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					ColorSet obj = null;
					//保存红色
                    obj = checkCancelAction.findColorSet(new Request(CommonVars.getCurrUser()),ColorSet.GREEN);
					if (obj != null){
						obj.setBeginNum(strToDouble(jFormattedTextField.getText()));
						obj.setEndNum(strToDouble(jFormattedTextField1.getText()));
					} else {
						obj = new ColorSet();
						obj.setBeginNum(strToDouble(jFormattedTextField.getText()));
						obj.setEndNum(strToDouble(jFormattedTextField1.getText()));
						obj.setColor(ColorSet.GREEN);
						obj.setCompany(CommonVars.getCurrUser().getCompany());
					}					
					checkCancelAction.saveColorSet(new Request(CommonVars.getCurrUser()),obj);
					
					
                    //保存深黄色
					obj = checkCancelAction.findColorSet(new Request(CommonVars.getCurrUser()),ColorSet.DEEPGREEN);
					if (obj != null){
						obj.setBeginNum(strToDouble(jFormattedTextField2.getText()));
						obj.setEndNum(strToDouble(jFormattedTextField3.getText()));
					} else {
						obj = new ColorSet();
						obj.setBeginNum(strToDouble(jFormattedTextField2.getText()));
						obj.setEndNum(strToDouble(jFormattedTextField3.getText()));
						obj.setColor(ColorSet.DEEPGREEN);
						obj.setCompany(CommonVars.getCurrUser().getCompany());
					}
					checkCancelAction.saveColorSet(new Request(CommonVars.getCurrUser()),obj);
					
					//保存黄色
					obj = checkCancelAction.findColorSet(new Request(CommonVars.getCurrUser()),ColorSet.YELLOW);
					if (obj != null){
						obj.setBeginNum(strToDouble(jFormattedTextField4.getText()));
						obj.setEndNum(strToDouble(jFormattedTextField5.getText()));
					} else {
						obj = new ColorSet();
						obj.setBeginNum(strToDouble(jFormattedTextField4.getText()));
						obj.setEndNum(strToDouble(jFormattedTextField5.getText()));
						obj.setColor(ColorSet.YELLOW);
						obj.setCompany(CommonVars.getCurrUser().getCompany());
					}
					checkCancelAction.saveColorSet(new Request(CommonVars.getCurrUser()),obj);
					
					//保存深绿色
					obj = checkCancelAction.findColorSet(new Request(CommonVars.getCurrUser()),ColorSet.DEEPYELLOW);
					if (obj != null){
						obj.setBeginNum(strToDouble(jFormattedTextField6.getText()));
						obj.setEndNum(strToDouble(jFormattedTextField7.getText()));
					} else {
						obj = new ColorSet();
						obj.setBeginNum(strToDouble(jFormattedTextField6.getText()));
						obj.setEndNum(strToDouble(jFormattedTextField7.getText()));
						obj.setColor(ColorSet.DEEPYELLOW);
						obj.setCompany(CommonVars.getCurrUser().getCompany());
					}
					checkCancelAction.saveColorSet(new Request(CommonVars.getCurrUser()),obj);
					
					//保存绿色
					obj = checkCancelAction.findColorSet(new Request(CommonVars.getCurrUser()),ColorSet.RED);
					if (obj != null){
						obj.setBeginNum(strToDouble(jFormattedTextField8.getText()));
						obj.setEndNum(strToDouble(jFormattedTextField9.getText()));
					} else {
						obj = new ColorSet();
						obj.setBeginNum(strToDouble(jFormattedTextField8.getText()));
						obj.setEndNum(strToDouble(jFormattedTextField9.getText()));
						obj.setColor(ColorSet.RED);
						obj.setCompany(CommonVars.getCurrUser().getCompany());
					}
					checkCancelAction.saveColorSet(new Request(CommonVars.getCurrUser()),obj);
					
                    DgColorDefine.this.dispose();
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
			jButton1.setBounds(207, 222, 74, 25);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgColorDefine.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		
	}



	private void fillCurrCode(ParaSet obj) {
	  
	}

	private void clearData() {
		
		
	}

	class CustomDocument extends PlainDocument {
		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			if (super.getLength() >= 10 || str.length() > 10
					|| super.getLength() + str.length() > 10) {
				return;
			}
			super.insertString(offs, str, a);
		}
	}

	private void addData() {
		
	}

	private void modifyData() {
		
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
	 * @return Returns the commonBaseCodeObj.
	 */
	public CommonBaseCodeAction getCommonBaseCodeObj() {
		return commonBaseCodeObj;
	}

	/**
	 * @param commonBaseCodeObj
	 *            The commonBaseCodeObj to set.
	 */
	public void setCommonBaseCodeObj(CommonBaseCodeAction commonBaseCodeObj) {
		this.commonBaseCodeObj = commonBaseCodeObj;
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
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * This method initializes jTextField10	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField10() {
		if (jTextField10 == null) {
			jTextField10 = new JTextField();
			jTextField10.setEnabled(false);
			jTextField10.setBounds(new java.awt.Rectangle(252,169,116,23));
			jTextField10.setBackground(java.awt.Color.red);
		}
		return jTextField10;
	}

	/**
	 * This method initializes jTextField11	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField11() {
		if (jTextField11 == null) {
			jTextField11 = new JTextField();
			jTextField11.setEnabled(false);
			jTextField11.setBounds(new java.awt.Rectangle(252,133,116,22));
			jTextField11.setBackground(java.awt.Color.orange);
		}
		return jTextField11;
	}

	/**
	 * This method initializes jTextField12	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField12() {
		if (jTextField12 == null) {
			jTextField12 = new JTextField();
			jTextField12.setEnabled(false);
			jTextField12.setBounds(new java.awt.Rectangle(252,97,116,22));
			jTextField12.setBackground(java.awt.Color.yellow);
		}
		return jTextField12;
	}

	/**
	 * This method initializes jTextField13	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField13() {
		if (jTextField13 == null) {
			jTextField13 = new JTextField();
			jTextField13.setEnabled(false);
			jTextField13.setBounds(new java.awt.Rectangle(252,61,116,22));
			jTextField13.setBackground(java.awt.Color.green);
		}
		return jTextField13;
	}

	/**
	 * This method initializes jTextField14	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField14() {
		if (jTextField14 == null) {
			jTextField14 = new JTextField();
			jTextField14.setEnabled(false);
			jTextField14.setBounds(new java.awt.Rectangle(252,23,116,22));
			jTextField14.setBackground(java.awt.Color.cyan);
		}
		return jTextField14;
	}

	/**
	 * This method initializes jFormattedTextField	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new JFormattedTextField();
			jFormattedTextField.setBounds(new java.awt.Rectangle(23,24,85,22));
			jFormattedTextField.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField;
	}
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
	
	
	
	private Double strToDouble(String value) { //转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
					return new Double("0");
				//return null;
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
				return "0";
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * This method initializes jFormattedTextField1	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JFormattedTextField getJFormattedTextField1() {
		if (jFormattedTextField1 == null) {
			jFormattedTextField1 = new JFormattedTextField();
			jFormattedTextField1.setBounds(new java.awt.Rectangle(157,24,85,22));
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
			jFormattedTextField2 = new JFormattedTextField();
			jFormattedTextField2.setBounds(new java.awt.Rectangle(23,60,85,22));
			jFormattedTextField2.setFormatterFactory(getDefaultFormatterFactory());
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
			jFormattedTextField3 = new JFormattedTextField();
			jFormattedTextField3.setBounds(new java.awt.Rectangle(157,60,85,22));
			jFormattedTextField3.setFormatterFactory(getDefaultFormatterFactory());
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
			jFormattedTextField4 = new JFormattedTextField();
			jFormattedTextField4.setBounds(new java.awt.Rectangle(23,93,85,22));
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
			jFormattedTextField5 = new JFormattedTextField();
			jFormattedTextField5.setBounds(new java.awt.Rectangle(157,93,85,22));
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
			jFormattedTextField6 = new JFormattedTextField();
			jFormattedTextField6.setBounds(new java.awt.Rectangle(21,131,85,22));
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
			jFormattedTextField7 = new JFormattedTextField();
			jFormattedTextField7.setBounds(new java.awt.Rectangle(155,131,85,22));
			jFormattedTextField7.setFormatterFactory(getDefaultFormatterFactory());
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
			jFormattedTextField8 = new JFormattedTextField();
			jFormattedTextField8.setBounds(new java.awt.Rectangle(21,169,85,22));
			jFormattedTextField8.setFormatterFactory(getDefaultFormatterFactory());
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
			jFormattedTextField9 = new JFormattedTextField();
			jFormattedTextField9.setBounds(new java.awt.Rectangle(155,169,85,22));
			jFormattedTextField9.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField9;
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

	
} // @jve:decl-index=0:visual-constraint="100,100"
