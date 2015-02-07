/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;



import java.text.ParseException;

import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;

import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.KeyAdapterControl;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.text.DecimalFormat;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgEditAppFromMateriel extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JFormattedTextField jFormattedTextField = null;
	private JFormattedTextField jFormattedTextField1 = null;
	private DefaultFormatterFactory defaultFormatterFactory = null;   //  @jve:decl-index=0:parse
	private NumberFormatter numberFormatter = null;   //  @jve:decl-index=0:parse
	private JTextField jTextField2 = null;
	private JTextField jTextField3 = null;

	private JLabel jLabel1 = null;

	private JFormattedTextField jFormattedTextField2 = null;

	private JLabel jLabel4 = null;

	private JFormattedTextField jFormattedTextField3 = null;   
	
	private EncAction baseEncAction = null;
	
	private ImpExpCommodityInfo info = null;
	private JTableListModel tableModel = null;
	
	private BaseCustomsDeclarationCommInfo customsInfo = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JButton jButton4 = null; 
	
	private int     totalCount = 0; //总行
	private int     rowCount   = 0; //当前行
	private int dataState = DataState.EDIT;
	/**
	 * This is the default constructor
	 */
	public DgEditAppFromMateriel() {
		super();
		baseEncAction = (EncAction) CommonVars.getApplicationContext().getBean("encAction");
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(452, 235);
		this.setTitle("修改申请单信息");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
                    if (tableModel.getCurrentRow() != null){
                    	totalCount = tableModel.getRowCount();
                    	info = (ImpExpCommodityInfo) tableModel.getCurrentRow();
                    	rowCount = tableModel.getCurrRowCount();
						fillWindow();
						setState();
					}
				}
		});
	}
	
	private void setState(){
		jButton2.setEnabled(rowCount > 0);
		jButton3.setEnabled(rowCount < totalCount-1);
		jButton4.setEnabled(dataState == DataState.BROWSE); //修改
		jButton.setEnabled(dataState != DataState.BROWSE);  //保存
		jButton1.setEnabled(dataState != DataState.BROWSE); //取消
		jFormattedTextField.setEditable(dataState != DataState.BROWSE);
		jFormattedTextField1.setEditable(dataState != DataState.BROWSE);
		jFormattedTextField2.setEditable(dataState != DataState.BROWSE);
		jFormattedTextField3.setEditable(dataState != DataState.BROWSE);
	}
	
	private void fillWindow() {
		jTextField2.setText(info.getMateriel().getPtNo());
		jTextField3.setText(info.getMateriel().getFactoryName());
		jTextField.setText(info.getMateriel().getFactorySpec());
		
		jFormattedTextField.setText(doubleToStr(info.getQuantity()));
		jFormattedTextField1.setText(doubleToStr(info.getNetWeight()));
		jFormattedTextField2.setText(doubleToStr(info.getGossweightcy()));
		jFormattedTextField3.setText(info.getPiece() == null?null:String.valueOf(info.getPiece()));
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(17,111,61,21));
			jLabel4.setText("件数");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(240,81,57,23));
			jLabel1.setText("毛重");
			javax.swing.JLabel jLabel8 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("规格");
			jLabel.setBounds(17, 49, 53, 18);
			jLabel2.setBounds(240, 49, 54, 20);
			jLabel2.setText("数量");
			jLabel3.setBounds(17, 81, 59, 17);
			jLabel3.setText("净重");
			jLabel7.setBounds(17, 18, 54, 21);
			jLabel7.setText("料号");
			jLabel8.setBounds(240, 18, 50, 20);
			jLabel8.setText("名称");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(getJFormattedTextField1(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJFormattedTextField2(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJFormattedTextField3(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJButton3(), null);
			jContentPane.add(getJButton4(), null);
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
			jTextField.setBounds(85, 49, 123, 22);
			jTextField.setEditable(false);
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
			jButton.setBounds(270, 164, 65, 25);
			jButton.setText("保存");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {
					Double amount = info.getQuantity();
					Double netweight = info.getNetWeight();
					Double gossweight = info.getGrossWeight();
					Integer piece = info.getPiece()==null?0:info.getPiece();
					info = fillData(info);
                    ImpExpCommodityInfo obj = new ImpExpCommodityInfo();
                    obj.setMateriel(info.getMateriel());
                    obj.setQuantity(fd(amount) - fd(info.getQuantity()));
                    obj.setNetWeight(fd(netweight) - fd(info.getNetWeight()));
                    obj.setGrossWeight(fd(gossweight) - fd(info.getGrossWeight()));
                    obj.setPiece(piece - (info.getPiece() == null ?0:info.getPiece()));		
                    obj.setTempAmount(fd(amount));
                    
                    baseEncAction.deleteImpExpCommodityInfo(new Request(CommonVars.getCurrUser()),customsInfo,obj,false);
					info = baseEncAction.saveImpExpComInfo(new Request(CommonVars.getCurrUser()),info);
					tableModel.updateRow(info);
					dataState = DataState.BROWSE;
					setState();
				}
			});

		}
		return jButton;
	}
	
	private Double fd(Double d){
		if (d != null){
			return d;
		}
		return Double.valueOf(0);
	}
	public ImpExpCommodityInfo fillData(ImpExpCommodityInfo info) { //保存
		info.setQuantity(strToDouble(jFormattedTextField.getText()));
		info.setNetWeight(strToDouble(jFormattedTextField1.getText()));
		info.setGrossWeight(strToDouble(jFormattedTextField2.getText()));
		info.setPiece((jFormattedTextField3.getText() == null || "".equals(jFormattedTextField3.getText()))?0:Integer.valueOf(jFormattedTextField3.getText()));
		return info;
		
	}
	/**

	 * This method initializes jButton1	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(341, 164, 65, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					fillWindow();
					dataState = DataState.BROWSE;
					setState();

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
			jFormattedTextField.setBounds(309, 49, 121, 22);
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
			jFormattedTextField1.setBounds(85, 81, 123, 22);
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
			DecimalFormat decimalFormat1 = new DecimalFormat();  //  @jve:decl-index=0:
			decimalFormat1.setGroupingSize(0);
			decimalFormat1.setMaximumFractionDigits(9);
			numberFormatter.setFormat(decimalFormat1);
		}
		return numberFormatter;
	}

	/**

	 * This method initializes jTextField2	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(85, 18, 123, 22);
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}

	/**

	 * This method initializes jTextField3	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(309, 18, 121, 22);
			jTextField3.setEditable(false);
		}
		return jTextField3;
	}

	
	/**
	 * This method initializes jFormattedTextField2	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JFormattedTextField getJFormattedTextField2() {
		if (jFormattedTextField2 == null) {
			jFormattedTextField2 = new JFormattedTextField();
			jFormattedTextField2.setBounds(new java.awt.Rectangle(309,81,121,22));
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
			jFormattedTextField3.setBounds(new java.awt.Rectangle(85,111,123, 22));
		}
		return jFormattedTextField3;
	}
	public void setCustomsInfo(BaseCustomsDeclarationCommInfo customsInfo) {
		this.customsInfo = customsInfo;
	}
	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new java.awt.Rectangle(35,164,77,25));
			jButton2.setText("上一条");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					RollSave();
					dataState = DataState.EDIT;
					rowCount -- ;
					info = (ImpExpCommodityInfo) tableModel.getObjectByRow(rowCount);
					fillWindow();
					setState();
				}
			});
		}
		return jButton2;
	}
	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new java.awt.Rectangle(117,164,77,25));
			jButton3.setText("下一条");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					RollSave();
					dataState = DataState.EDIT;
					rowCount ++ ;
					info = (ImpExpCommodityInfo) tableModel.getObjectByRow(rowCount);
					fillWindow();					
					setState();
				}
			});
		}
		return jButton3;
	}
	
	private void RollSave(){
		if (dataState == DataState.EDIT){
			Double amount = info.getQuantity();
			Double netweight = info.getNetWeight();
			Double gossweight = info.getGrossWeight();
			Integer piece = info.getPiece()==null?0:info.getPiece();
			info = fillData(info);
            ImpExpCommodityInfo obj = new ImpExpCommodityInfo();
            obj.setMateriel(info.getMateriel());
            obj.setQuantity(fd(amount) - fd(info.getQuantity()));
            obj.setNetWeight(fd(netweight) - fd(info.getNetWeight()));
            obj.setGrossWeight(fd(gossweight) - fd(info.getGrossWeight()));
            obj.setPiece(piece - (info.getPiece() == null ?0:info.getPiece()));	
            System.out.println("obj.getQuantity():"+obj.getQuantity());
            baseEncAction.deleteImpExpCommodityInfo(new Request(CommonVars.getCurrUser()),customsInfo,obj,false);
			info = baseEncAction.saveImpExpComInfo(new Request(CommonVars.getCurrUser()),info);
			tableModel.updateRow(info);
		}
	}
	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new java.awt.Rectangle(199,164,65,25));
			jButton4.setText("修改");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return jButton4;
	}
                               }  //  @jve:decl-index=0:visual-constraint="10,10"
