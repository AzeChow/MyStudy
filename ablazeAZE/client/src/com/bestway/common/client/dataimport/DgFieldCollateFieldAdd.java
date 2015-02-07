/*
 * Created on 2004-10-26
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;


import java.util.List;

import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.ClassList;
import com.bestway.bcus.dataimport.entity.FieldList;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JComboBox;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgFieldCollateFieldAdd extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;
	private JTextField jTextField1 = null;
	private JTextField jTextField2 = null;
	private JTextField jTextField4 = null;
	private JCheckBox jCheckBox = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private ClassList classList = null; //表头
    private FieldList fieldList = null; //表体
	private boolean isAdd = true;

	private JTableListModel tableModel = null;

	private DataImportAction dataImportAction = null;
	
	private JTextField jTextField5 = null;
	private JComboBox jComboBox = null;
	private JComboBox jComboBox1 = null;
	private JCheckBox jCheckBox1 = null;
	/**
	 * This is the default constructor
	 */
	public DgFieldCollateFieldAdd() {
		super();
		dataImportAction = (DataImportAction) CommonVars
           .getApplicationContext().getBean("dataImportAction");
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("字段设置");
		this.setSize(385, 234);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				if (isAdd == false) { 
					if (tableModel.getCurrentRow() != null) {
						fieldList = (FieldList) tableModel.getCurrentRow();
					}
					initUI();
					initUIData();//初始化数据
				} else {
					initUI();
					DgFieldCollateFieldAdd.this.jTextField.setText(dataImportAction.getNum(new Request(
								CommonVars.getCurrUser()),"FieldList",DgFieldCollateFieldAdd.this.getClassList()));
				}
				getState();
			}
		});
	}
	private void initUIData() { //初始化数据
		this.jTextField5.setText(fieldList.getName());
		this.jTextField.setText(String.valueOf(fieldList.getSortno()));//序号
		this.jTextField1.setText(fieldList.getFieldname());
		jTextField2.setText(String.valueOf(fieldList.getFieldlen()));
		jComboBox.setSelectedItem(fieldList.getFieldtype());
		this.jTextField4.setText(fieldList.getFielddesc());
		if (fieldList.getIskey().equals(new Boolean(true))){
			this.jCheckBox.setSelected(true);
		} else {
			this.jCheckBox.setSelected(false);
		}
		if (fieldList.getIsNull().equals(new Boolean(true))){
			this.jCheckBox1.setSelected(true);
		} else {
			this.jCheckBox1.setSelected(false);
		}
		if (fieldList.getObjName()!=null){
			this.jComboBox1.setSelectedItem(fieldList.getObjName());
		} else {
			this.jComboBox1.setSelectedItem(null);
		}
		
	}
	private void initUI(){
		this.jComboBox.removeAllItems();
        this.jComboBox.addItem("String");
        this.jComboBox.addItem("Double");
        this.jComboBox.addItem("double");
        this.jComboBox.addItem("Integer");
        this.jComboBox.addItem("Boolean");
        this.jComboBox.addItem("Date");
        this.jComboBox.addItem("对象");
        this.jComboBox.setUI(new CustomBaseComboBoxUI(112));
        //对象名称 
        List list = dataImportAction.findClassList(new Request(CommonVars
				.getCurrUser()));
		this.jComboBox1.setModel(new DefaultComboBoxModel(list.toArray()));
		this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender(
				"sortno", "name",80,145));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox1, "sortno", "name");
		jCheckBox1.setSelected(true);
		this.jTextField2.setText("225");
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(16, 16, 39, 21);
			jLabel.setText("序号");
			jLabel1.setBounds(174, 16, 61, 22);
			jLabel1.setText("字段名称");
			jLabel2.setBounds(175, 48, 57, 22);
			jLabel2.setText("字段长度");
			jLabel3.setBounds(175, 80, 57, 22);
			jLabel3.setText("字段类型");
			jLabel4.setBounds(17, 136, 56, 20);
			jLabel4.setText("字段说明");
			jLabel6.setBounds(15, 48, 55, 22);
			jLabel6.setText("中文名称");
			jLabel7.setBounds(17, 108, 79, 21);
			jLabel7.setText("对象类型名称");
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJTextField5(), null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJComboBox1(), null);
			jContentPane.add(getJCheckBox1(), null);
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
			jTextField.setBounds(77, 16, 82, 22);
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
			jTextField1.setBounds(244, 15, 111, 22);
		}
		return jTextField1;
	}

	/**

	 * This method initializes jTextField2	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(244, 48, 113, 22);
		}
		return jTextField2;
	}

	/**

	 * This method initializes jTextField4	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(78, 136, 281, 22);
		}
		return jTextField4;
	}

	/**

	 * This method initializes jCheckBox	

	 * 	

	 * @return javax.swing.JCheckBox	

	 */    
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(14, 79, 74, 21);
			jCheckBox.setText("是否主键");
		}
		return jCheckBox;
	}

	/**

	 * This method initializes jButton	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(84, 166, 66, 24);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					if (isAdd == true) {
						FieldList fieldList = new FieldList();
						fillData(fieldList);
						fieldList = dataImportAction.saveFieldList(new Request(
								CommonVars.getCurrUser()), fieldList);
						tableModel.addRow(fieldList);
						clearData();
						DgFieldCollateFieldAdd.this.jTextField.setText(dataImportAction.getNum(new Request(
								CommonVars.getCurrUser()),"FieldList",DgFieldCollateFieldAdd.this.getClassList()));
					} else {
						fillData(fieldList);
						fieldList = dataImportAction.saveFieldList(new Request(
								CommonVars.getCurrUser()), fieldList);
						tableModel.updateRow(fieldList);
						DgFieldCollateFieldAdd.this.dispose();
						
					}
					

				}
			});

		}
		return jButton;
	}
	public void fillData(FieldList fieldList) { //保存		
		    fieldList.setName(this.jTextField5.getText());
		    fieldList.setClassList(this.getClassList());
			fieldList.setSortno(Integer.valueOf(this.jTextField.getText()));
			fieldList.setFieldname(this.jTextField1.getText());
			if (this.jTextField2.getText().equals("")){
				this.jTextField2.setText("0");
			}
			fieldList.setFieldlen(Integer.valueOf(this.jTextField2.getText()));
			if (jComboBox.getSelectedItem()!=null){
			    fieldList.setFieldtype(jComboBox.getSelectedItem().toString());
			} else {
				 fieldList.setFieldtype(null);
			}
			fieldList.setFielddesc(this.jTextField4.getText());
			if (this.jCheckBox.isSelected()){
				fieldList.setIskey(new Boolean(true));
			} else {
				fieldList.setIskey(new Boolean(false));
			}
			if (this.jCheckBox1.isSelected()){
				fieldList.setIsNull(new Boolean(true));
			} else {
				fieldList.setIsNull(new Boolean(false));
			}
			if (jComboBox1.getSelectedItem()!=null){
				fieldList.setObjName((ClassList) jComboBox1.getSelectedItem());
			} else {
				fieldList.setObjName(null);
			}
		}
  private void clearData(){
  	this.jTextField.setText("");
  	this.jTextField1.setText("");
  	this.jComboBox.setSelectedItem("");
  	this.jTextField4.setText("");
  	this.jTextField5.setText("");
  	this.jTextField2.setText("225");
  }
	/**

	 * This method initializes jButton1	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(223, 166, 69, 24);
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgFieldCollateFieldAdd.this.dispose();

				}
			});

		}
		return jButton1;
	}

	/**
	 * @return Returns the classList.
	 */
	public ClassList getClassList() {
		return classList;
	}
	/**
	 * @param classList The classList to set.
	 */
	public void setClassList(ClassList classList) {
		this.classList = classList;
	}
	/**
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}
	/**
	 * @param isAdd The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
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

	 * This method initializes jTextField5	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setBounds(78, 47, 95, 22);
		}
		return jTextField5;
	}

	/**

	 * This method initializes jComboBox	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(245, 80, 112, 22);
			jComboBox.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					if (jComboBox.getSelectedItem()!=null && !jComboBox.getSelectedItem().equals("String")){
						jTextField2.setText("");	
					}
				}
			});

			jComboBox.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					
					getState();
					
				}
			});
		}
		return jComboBox;
	}
	private void getState(){
		if (DgFieldCollateFieldAdd.this.jComboBox.getSelectedItem().equals("对象")){
			DgFieldCollateFieldAdd.this.jComboBox1.setEnabled(true);
		} else {
			DgFieldCollateFieldAdd.this.jComboBox1.setEnabled(false);
			this.jComboBox1.setSelectedIndex(-1);
		}
	}

	/**

	 * This method initializes jComboBox1	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setEnabled(false);
			jComboBox1.setBounds(102, 109, 256, 22);
			jComboBox1.addItemListener(new java.awt.event.ItemListener() { 
				public void itemStateChanged(java.awt.event.ItemEvent e) {    
					if (jComboBox1.getSelectedItem()!=null){
						ClassList classList = (ClassList) jComboBox1.getSelectedItem();
						jTextField4.setText(classList.getClassPath());
					}
				}
			});
		}
		return jComboBox1;
	}

	/**

	 * This method initializes jCheckBox1	

	 * 	

	 * @return javax.swing.JCheckBox	

	 */    
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setBounds(88, 79, 88, 21);
			jCheckBox1.setText("是否可为空");
		}
		return jCheckBox1;
	}

            }  //  @jve:decl-index=0:visual-constraint="10,10"
