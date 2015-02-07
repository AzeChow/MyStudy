/*
 * Created on 2004-9-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;


import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.TxtTaskEx;
import com.bestway.bcus.dataimport.entity.TxtTaskSel;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.sql.Time;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.text.DateFormatter;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
/**
 * @author lin
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgTxtTaskAdd extends JDialogBase {

	private JPanel jPanel = null;
	private JPanel jPanel2 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JTextField tfTxtTaskName = null;
	private JLabel jLabel4 = null;
	private JCheckBox jCheckBox = null;
	private TxtTaskEx txtTaskEx = null; //任务主表
	private TxtTaskSel txtTaskSel = null; //任务明细表
	
	private DataImportAction dataImportAction = null;
	
	private boolean isAdd = true;
	private JTableListModel tableModel = null;
	
	private JLabel jLabel5 = null;
	private DateFormatter dateFormatter = null;   //  @jve:decl-index=0:
	private JComboBox jComboBox = null;
	private JTextField jTextField = null;
	private JComboBox jComboBox1 = null;
	private JComboBox jComboBox2 = null;
	private JTextArea jTextArea = null;
	private JScrollPane jScrollPane = null;
	private JCheckBox cbIsParentTask = null;
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
	 * 
	 */
	public DgTxtTaskAdd() {
		super();	
		dataImportAction = (DataImportAction) CommonVars
			   .getApplicationContext().getBean("dataImportAction");
		initialize();
	}


	private void initialize() {
        this.setForeground(java.awt.SystemColor.windowText);
        this.setContentPane(getJPanel());
        this.setTitle("文本导入任务设置");
        this.setSize(422, 297);
        this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				if (isAdd == false) { 
					if (tableModel.getCurrentRow() != null) {
						txtTaskEx = (TxtTaskEx) tableModel.getCurrentRow();
					}
					initUI();
					fillWindow();
				} else {
					initUI();
				}
			}
		});
		
			
	}
	private void initUI(){
		jTextField.setText("00:00:00");
		//执行频率
		this.jComboBox.removeAllItems();
		this.jComboBox.addItem(new ItemProperty("0", "手动"));
		this.jComboBox.addItem(new ItemProperty("1", "每日"));
		this.jComboBox.addItem(new ItemProperty("2", "每周"));
		this.jComboBox.addItem(new ItemProperty("3", "每月"));
		this.jComboBox.addItem(new ItemProperty("4", "间隔"));
		this.jComboBox
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox);
        //导入选项
		this.jComboBox2.removeAllItems();
		this.jComboBox2.addItem(new ItemProperty("0", "不处理重复数据"));
		this.jComboBox2.addItem(new ItemProperty("1", "覆盖重复数据"));
		this.jComboBox2.addItem(new ItemProperty("2", "更新重复数据"));
		this.jComboBox2.addItem(new ItemProperty("3", "直接保存数据"));
		this.jComboBox2
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox2);
        
	}
	private void fillWindow()
	{
		//执行频率
		if (this.txtTaskEx.getExcutekind()!= null) {
			jComboBox.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(this.txtTaskEx.getExcutekind()), jComboBox));
			//执行日期
			if ((((ItemProperty) jComboBox.getSelectedItem())
					.getCode()).equals("2")){
				getriqi(1);
				jComboBox1.setEnabled(true);
				this.jTextField.setEditable(true);
			} else if ((((ItemProperty) jComboBox.getSelectedItem())
					.getCode()).equals("3")){
				getriqi(2);
				jComboBox1.setEnabled(true);
				this.jTextField.setEditable(true);
			} else if ((((ItemProperty) jComboBox.getSelectedItem())
					.getCode()).equals("0")){
				jComboBox1.setEnabled(false);
				this.jTextField.setEditable(false);			
				this.jComboBox1.setSelectedIndex(-1);
			} else {
				jComboBox1.setEnabled(false);
				this.jTextField.setEditable(true);
				this.jComboBox1.setSelectedIndex(-1);
			}
		} else {
			jComboBox.setSelectedIndex(-1);
		}	
		//导入选项
		if (this.txtTaskEx.getInputSelect()!= null) {
			jComboBox2.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(this.txtTaskEx.getInputSelect()), jComboBox2));
		} else {
			jComboBox2.setSelectedIndex(-1);
		}
		tfTxtTaskName.setText(txtTaskEx.getTaskname());
		if (txtTaskEx.getIseffice().equals(new Boolean(true))){
			jCheckBox.setSelected(true);
		} else {
			jCheckBox.setSelected(false);
		}
		this.jTextArea.setText(txtTaskEx.getNote());
		this.jTextField.setText(txtTaskEx.getExcutetime());
	}
	private void getriqi(int yy){
        //执行日期
		this.jComboBox1.removeAllItems();
		if (yy == 1){	  
		  this.jComboBox1.addItem(new ItemProperty("1", "星期一"));
		  this.jComboBox1.addItem(new ItemProperty("2", "星期二"));
		  this.jComboBox1.addItem(new ItemProperty("3", "星期三"));
		  this.jComboBox1.addItem(new ItemProperty("4", "星期四"));
		  this.jComboBox1.addItem(new ItemProperty("5", "星期五"));
		  this.jComboBox1.addItem(new ItemProperty("6", "星期六"));
		  this.jComboBox1.addItem(new ItemProperty("7", "星期天"));
		} else if (yy == 2){
			for (int i=1;i<=31;i++){
		      this.jComboBox1.addItem(new ItemProperty(String.valueOf(i), String.valueOf(i)+"日"));
			}
		}
		this.jComboBox1
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox1);
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setBounds(19, 18, 58, 23);
			jLabel.setText("任务名称");
			jLabel1.setBounds(19, 51, 60, 21);
			jLabel1.setText("执行频率");
			jLabel2.setBounds(228, 51, 59, 22);
			jLabel2.setText("运行日期");
			jLabel3.setBounds(19, 118, 60, 20);
			jLabel3.setText("导入选项");
			jLabel4.setBounds(18, 148, 59, 21);
			jLabel4.setText("任务说明");
			jLabel5.setBounds(18, 84, 64, 22);
			jLabel5.setText("执行时间");
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getTfTxtTaskName(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJCheckBox(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJComboBox(), null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(getJComboBox1(), null);
			jPanel2.add(getJComboBox2(), null);
			jPanel2.add(getJScrollPane(), null);
			jPanel2.add(getCbIsParentTask(), null);
		}
		return jPanel2;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确 定");
			jButton.setBounds(211, 233, 61, 24);	
			jButton.addActionListener(new java.awt.event.ActionListener(){
				
				public void actionPerformed(ActionEvent arg0){	
					if (checkNull())
					{
						return;	
					}
					if (isAdd()) {
						addTxtTaskEx();
					}
					else
					{
						modifyTxtTaskEx();
					}
				     DgTxtTaskAdd.this.dispose();
				}			
			});
		}
		return jButton;
	}
	
	private  boolean checkNull(){
		if (this.tfTxtTaskName.getText().trim().equals(""))
		{
		  JOptionPane.showMessageDialog(this,"任务名称不能为空，请输入!","提示",1);
		  return true;
		}
		if (((ItemProperty)DgTxtTaskAdd.this.jComboBox.getSelectedItem()).getCode().equals("0")&& this.cbIsParentTask.isSelected())
		{
			JOptionPane.showMessageDialog(this, "父任务必须为自动导入，请更正!", "提示", 1);
			return true;
		}
		try{
			  Time.valueOf(this.jTextField.getText());
			} catch (Exception e){
				JOptionPane.showMessageDialog(this,"时间格式不正确，请重新输入!","提示",1);
				return true;
			}
		return false;
	}
	private void addTxtTaskEx()
	{
		TxtTaskEx txttaskex = new TxtTaskEx();
		fillData(txttaskex);		
		txttaskex  = dataImportAction.saveTxtTaskEx(new Request(CommonVars
				 .getCurrUser()),txttaskex);
		tableModel.addRow(txttaskex);
		
	}
	
	private void modifyTxtTaskEx()
	{
		fillData(txtTaskEx);
		txtTaskEx  = dataImportAction.saveTxtTaskEx(new Request(CommonVars
				 .getCurrUser()),txtTaskEx);
		tableModel.updateRow(txtTaskEx);
	}

	
	
	private void fillData(TxtTaskEx txttaskex){
		txttaskex.setTaskname(tfTxtTaskName.getText());
		if (jComboBox.getSelectedItem()!=null){
		   txttaskex.setExcutekind(((ItemProperty) jComboBox.getSelectedItem())
				.getCode());
		} else {
			txttaskex.setExcutekind(null);
		}
		if (jComboBox1.getSelectedItem()!=null){
		   txttaskex.setExcuteday(((ItemProperty) jComboBox1.getSelectedItem())
				.getCode());
		} else {
			txttaskex.setExcuteday(null);
		}
		if (jComboBox2.getSelectedItem()!=null){
		   txttaskex.setInputSelect(((ItemProperty) jComboBox2.getSelectedItem())
				.getCode());
		} else {
			txttaskex.setInputSelect(null);
		}
		//txttaskex.setExcutetime()
		txttaskex.setNote(this.jTextArea.getText());
		if (this.jCheckBox.isSelected()){
			txttaskex.setIseffice(new Boolean(true));
			txttaskex.setEfficeDate(new Date());
		} else {
			txttaskex.setIseffice(new Boolean(false));
		}
		txttaskex.setCompany(CommonVars.getCurrUser().getCompany());
		txttaskex.setCreatedate(CommonVars.dateToStandDate(new Date()));
		txttaskex.setCreateuser(CommonVars.getCurrUser().getUserName());
		txttaskex.setExecuteState(Integer.valueOf(0));
		if(((ItemProperty)jComboBox.getSelectedItem()).getCode().equals("0")){
		    txttaskex.setIsParentTask(new Boolean(false));
		}
		else{
		    txttaskex.setIsParentTask(cbIsParentTask.isSelected());
		}
		txttaskex.setExcutetime(this.jTextField.getText());
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("取 消");
			jButton1.setBounds(315, 233, 61, 24);
			jButton1.addActionListener(new java.awt.event.ActionListener(){
				
				public void actionPerformed(ActionEvent arg0){
				 	
				     DgTxtTaskAdd.this.dispose();
				     
				}
				
				
			});
		}
		return jButton1;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTfTxtTaskName() {
		if (tfTxtTaskName == null) {
			tfTxtTaskName = new JTextField();
			tfTxtTaskName.setBounds(96, 17, 300, 24);
		}
		return tfTxtTaskName;
	}
	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(225, 81, 63, 22);
			jCheckBox.setEnabled(false);
			jCheckBox.setText("有效");
		}
		return jCheckBox;
	}
	/**
	 * This method initializes dateFormatter	
	 * 	
	 * @return javax.swing.text.DateFormatter	
	 */    
	private DateFormatter getDateFormatter() {
		if (dateFormatter == null) {
			dateFormatter = new DateFormatter();
		}
		return dateFormatter;
	}
  

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(96, 50, 115, 22);
			jComboBox.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
                    if (DgTxtTaskAdd.this.jComboBox.getSelectedItem()!=null){
					if ((((ItemProperty) DgTxtTaskAdd.this.jComboBox.getSelectedItem())
							.getCode()).equals("2")){
						getriqi(1);
						DgTxtTaskAdd.this.jComboBox1.setEnabled(true);
						DgTxtTaskAdd.this.jTextField.setEditable(true);
					} else if ((((ItemProperty) DgTxtTaskAdd.this.jComboBox.getSelectedItem())
							.getCode()).equals("3")){
						getriqi(2);
						DgTxtTaskAdd.this.jComboBox1.setEnabled(true);
						DgTxtTaskAdd.this.jTextField.setEditable(true);
					} else if ((((ItemProperty) DgTxtTaskAdd.this.jComboBox.getSelectedItem())
							.getCode()).equals("0")){
						DgTxtTaskAdd.this.jComboBox1.setEnabled(false);
						DgTxtTaskAdd.this.jTextField.setEditable(false);	
						DgTxtTaskAdd.this.jComboBox1.setSelectedIndex(-1);
					} else if ((((ItemProperty) DgTxtTaskAdd.this.jComboBox.getSelectedItem())
							.getCode()).equals("1")){
						DgTxtTaskAdd.this.jComboBox1.setEnabled(false);
						DgTxtTaskAdd.this.jTextField.setEditable(true);
						DgTxtTaskAdd.this.jComboBox1.setSelectedIndex(-1);
					} else {
						DgTxtTaskAdd.this.jComboBox1.setEnabled(false);
						DgTxtTaskAdd.this.jTextField.setEditable(true);
						DgTxtTaskAdd.this.jComboBox1.setSelectedIndex(-1);
					}
                    }
				}
			});

		}
		return jComboBox;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(95, 83, 116, 22);
		}
		return jTextField;
	}
	/**
	 * This method initializes jComboBox1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(292, 50, 102, 22);
		}
		return jComboBox1;
	}
	/**
	 * This method initializes jComboBox2	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setBounds(96, 117, 115, 22);
		}
		return jComboBox2;
	}
	/**

	 * This method initializes jTextArea	

	 * 	

	 * @return javax.swing.JTextArea	

	 */    
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
		}
		return jTextArea;
	}

	/**

	 * This method initializes jScrollPane	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(96, 149, 295, 72);
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jCheckBox1	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbIsParentTask() {
		if (cbIsParentTask == null) {
			cbIsParentTask = new JCheckBox();
			cbIsParentTask.setBounds(new java.awt.Rectangle(95,236,101,19));
			cbIsParentTask.setText("是否父任务");
		}
		return cbIsParentTask;
	}

  }  //  @jve:decl-index=0:visual-constraint="10,10"
