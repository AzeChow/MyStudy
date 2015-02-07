/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;



import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.UnitWaste;
import com.bestway.ui.winuicontrol.JDialogBase;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgOrgUnitWasteEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;
	private JTextField jTextField5 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JFormattedTextField jFormattedTextField = null;
	private JFormattedTextField jFormattedTextField1 = null;
	private ManualDeclareAction manualdeclearAction = null;
	private JTableListModel tableModel = null;  //成品BOM	
	private UnitWaste emsBom = null;
	private DefaultFormatterFactory defaultFormatterFactory = null;   //  @jve:decl-index=0:parse
	private NumberFormatter numberFormatter = null;   //  @jve:decl-index=0:parse
	private JTextField jTextField4 = null;
	private JTextField jTextField6 = null;
	private JLabel jLabel6 = null;
	private MaterialManageAction materialManageAction = null;
	
	/**
	 * This is the default constructor
	 */
	public DgOrgUnitWasteEdit() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(434, 217);
		this.setTitle("单耗维护");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

					if (tableModel.getCurrentRow() != null) {
						emsBom = (UnitWaste) tableModel.getCurrentRow();
						fillWindow();
					}
				}
		});
	}
	private void fillWindow() {	
		jTextField.setText(emsBom.getPtNo());
		//jTextField3.setText(String.valueOf(emsBom.getVersion()));		
		/*if (emsBom.getMateriel().getComplex()!=null)
	    	jTextField1.setText(emsBom.getMateriel().getComplex().getCode());
		else
			jTextField1.setText("");*/
		/*jTextField4.setText(emsBom.getFactoryName());
		jTextField6.setText(emsBom.getFactorySpec());
		jFormattedTextField.setText(doubleToStr(Double.valueOf(emsBom.getUnitWaste())));
		jFormattedTextField1.setText(doubleToStr(Double.valueOf(emsBom.getWaste())));
		if (emsBom.getCalUnit()!=null)
	    	jTextField5.setText(emsBom.getCalUnit().getName());
		else
			jTextField5.setText("");*/
		//jTextField7.setText(emsBom.getNote());
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jLabel6 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("料件序号");
			jLabel.setBounds(10, 25, 53, 18);
			jLabel2.setBounds(214, 59, 50, 20);
			jLabel2.setText("单耗");
			jLabel3.setBounds(215, 96, 51, 17);
			jLabel3.setText("损耗率");
			jLabel4.setBounds(10, 61, 50, 20);
			jLabel4.setText("型号规格");
			jLabel5.setBounds(11, 98, 49, 18);
			jLabel5.setText("申报单位");
			jLabel10.setBounds(214, 25, 52, 21);
			jLabel10.setText("商品名称");
			jLabel6.setBounds(402, 95, 17, 21);
			jLabel6.setText("%");
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getJTextField5(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(getJFormattedTextField1(), null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(getJTextField6(), null);
			jContentPane.add(jLabel6, null);
		}
		return jContentPane;
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
			jTextField.setLocation(72, 25);
			jTextField.setSize(122, 22);
		}
		return jTextField;
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
			jTextField5.setLocation(73, 96);
			jTextField5.setSize(122, 22);
		}
		return jTextField5;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(123, 134, 70, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					fillData(emsBom);
					emsBom = materialManageAction.saveBomImgDetail(
							new Request(CommonVars
									.getCurrUser()), emsBom);
					tableModel.updateRow(emsBom);
					DgOrgUnitWasteEdit.this.dispose();

				}
			});

		}
		return jButton;
	}
	public void fillData(UnitWaste emsBom) { //保存
		emsBom.setUnitWaste(strToDouble(jFormattedTextField.getText()).doubleValue());
		emsBom.setWaste(strToDouble(jFormattedTextField1.getText()).doubleValue());
		//emsBom.setNote(jTextField7.getText());	
	}
	/**

	 * This method initializes jButton1	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(232, 135, 69, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgOrgUnitWasteEdit.this.dispose();

				}
			});

		}
		return jButton1;
	}

	/**

	 * This method initializes jFormattedTextField	

	 * 	

	 * @return javax.swing.JFormattedTextField	

	 */    
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField.setLocation(296, 58);
			jFormattedTextField.setSize(122, 22);
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
			jFormattedTextField1.setBounds(297, 95, 103, 22);
			jFormattedTextField1.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField1;
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
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}
	/**
	 * @param tableModel The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
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
		}
		return numberFormatter;
	}

	/**

	 * This method initializes jTextField4	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setEditable(false);	
			jTextField4.setLocation(295, 25);
			jTextField4.setSize(122, 22);
		}
		return jTextField4;
	}

	/**

	 * This method initializes jTextField6	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setEditable(false);
			jTextField6.setLocation(72, 60);
			jTextField6.setSize(122, 22);
		}
		return jTextField6;
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"
