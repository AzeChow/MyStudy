/*
 * Created on 2004-9-23
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
import com.bestway.bcus.dataimport.entity.ClassList;
import com.bestway.bcus.dataimport.entity.TxtTask;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
/**
 * @author lin
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgTxtFormatAdd extends JDialogBase{

	private JPanel jContentPane = null;
	private JDialog jDialog = null;  //  @jve:decl-index=0:visual-constraint="70,9"
	private JPanel jPanel1 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTextField tfTaskName = null;
	private JTextField tfMemo = null;
	private JComboBox cbTableName = null;
	private JComboBox cbGbFlag = null;
	private boolean isAdd = true;
	private JTableListModel tableModel = null;
	private TxtTask currentTxtTask = null;
	
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
	private DataImportAction dataImportAction = null;
	
	private JComboBox jComboBox = null;
	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 * 
	 */
	public DgTxtFormatAdd() {
		super();
		initialize();
		dataImportAction = (DataImportAction) CommonVars
		   .getApplicationContext().getBean("dataImportAction");
	}
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize(){
		this.setTitle("文本导入格式设置");
		this.setContentPane(getJContentPane());	
		this.setSize(324, 213);
		this.addWindowListener(new java.awt.event.WindowAdapter(){
			public void windowOpened(java.awt.event.WindowEvent e){

				if (isAdd == false) //编辑
				{
					currentTxtTask = (TxtTask) tableModel.getCurrentRow();  //格式	
					initUI();
					fillWindow();
				} else {
					initUI();
				}
			}	
		});
	}
	private void initUI(){
		//目标表
		List list = dataImportAction.findClassList(new Request(CommonVars
				.getCurrUser()));
		this.jComboBox.setModel(new DefaultComboBoxModel(list.toArray()));
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
				"sortno", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox, "sortno", "name");
		this.jComboBox.setSelectedIndex(-1);
		//转换
		this.cbGbFlag.removeAllItems();
		this.cbGbFlag.addItem(new ItemProperty("0", "不转换"));
		this.cbGbFlag.addItem(new ItemProperty("1", "繁转简"));
		this.cbGbFlag.addItem(new ItemProperty("2", "简转繁"));
		this.cbGbFlag
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbGbFlag);
	}
	private void fillWindow()
	{
		//目标表
		if (currentTxtTask.getClassList() != null) {
			jComboBox.setSelectedItem(currentTxtTask.getClassList());
		} else {
			jComboBox.setSelectedItem(null);
		}
		tfTaskName.setText(currentTxtTask.getTaskname());
        //转换方式
		if (this.currentTxtTask.getGbflag() != null) {
			cbGbFlag.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(this.currentTxtTask.getGbflag()), cbGbFlag));
		} else {
			cbGbFlag.setSelectedIndex(-1);
		}
		tfMemo.setText(currentTxtTask.getMemo());
	}
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel.setBounds(16, 46, 75, 23);
			jLabel.setText("格式名称");
			jLabel1.setBounds(16, 74, 79, 21);
			jLabel1.setText("字符集转换");
			jLabel2.setBounds(16, 102, 76, 18);
			jLabel2.setText("其它说明");
			jLabel3.setBounds(16, 19, 52, 20);
			jLabel3.setText("目标表");
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getJTextField(), null);
			jPanel1.add(getJTextField1(), null);
			jPanel1.add(getJComboBox1(), null);
			jPanel1.add(getJComboBox2(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
		}
		return jPanel1;
	}
	private  boolean checkNull(){
		if (tfTaskName.getText().trim().equals(""))
		{
		  JOptionPane.showMessageDialog(this,"格式名称不能为空，请输入!","提示",1);
		  return true;
		}
		if (jComboBox.getSelectedItem() == null)
		{
		  JOptionPane.showMessageDialog(this,"目标表名称不能为空，请输入!","提示",1);
		  return true;
		}
		if (cbGbFlag.getSelectedItem() == null)
		{
		  JOptionPane.showMessageDialog(this,"转换方式不能为空，请输入!","提示",1);
		  return true;
		}
		return false;
	}
	private void fillData(TxtTask txtFormat){
		txtFormat.setClassList((ClassList) this.jComboBox
				.getSelectedItem()); //目标表
		if (this.cbGbFlag.getSelectedItem() != null) {
			txtFormat.setGbflag(((ItemProperty) this.cbGbFlag.getSelectedItem())
							.getCode());
		}  //转换方式
		txtFormat.setTaskname(tfTaskName.getText().trim());
		txtFormat.setCreatedate(CommonVars.dateToStandDate(new Date()));
		txtFormat.setCreator(CommonVars.getCurrUser().getUserName());
		txtFormat.setEditor(CommonVars.getCurrUser().getUserName());
		txtFormat.setMemo(tfMemo.getText().trim());		        
		txtFormat.setCompany(CommonVars.getCurrUser().getCompany());
		
	   }
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */ 
	private void addTxtFormat()
	{
		TxtTask txttask = new TxtTask();
		fillData(txttask);
		txttask  = dataImportAction.saveTxtTask(new Request(CommonVars
				 .getCurrUser()),txttask);
		tableModel.addRow(txttask);
		
	}
	private void modifyTxtFormat()
	{
		fillData(currentTxtTask);
		currentTxtTask  = dataImportAction.saveTxtTask(new Request(CommonVars
				 .getCurrUser()),currentTxtTask);
		tableModel.updateRow(currentTxtTask);
	}

	private boolean checkMultiple() {
		TxtTask taskname = dataImportAction.findTxtTaskBytaskname(new Request(CommonVars
				.getCurrUser()), this.tfTaskName.getText().trim());
		if (taskname != null) {
			if (!isAdd) {
				if (!taskname.getId().equals(currentTxtTask.getId())) {
					JOptionPane.showMessageDialog(this, "格式名称不能重复,请重新输入!", "提示!",
							2);
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(this, "格式名称不能重复,请重新输入!", "提示!", 2);
				return true;
			}
		}
		return false;
	}
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("保存");
			jButton.setBounds(67, 139, 61, 25);
			jButton.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(ActionEvent e){
					if (checkNull())
					{
						return;	
					}
					if (checkMultiple()) {
						return;
					}
					if (isAdd()) {
						addTxtFormat();
					}
					else
					{
						modifyTxtFormat();
					}
					
					DgTxtFormatAdd.this.dispose();
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
			jButton1.setText("关闭");
			jButton1.setBounds(187, 140, 63, 25);
			jButton1.addActionListener(new java.awt.event.ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					DgTxtFormatAdd.this.dispose();
					
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
	private JTextField getJTextField() {
		if (tfTaskName == null) {
			tfTaskName = new JTextField();
			tfTaskName.setBounds(106, 46, 185, 22);
		}
		return tfTaskName;
	}
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField1() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(106, 102, 185, 22);
		}
		return tfMemo;
	}

	/**
	 * This method initializes jComboBox1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getJComboBox1() {
		if (cbGbFlag == null) {
			cbGbFlag = new JComboBox();
			cbGbFlag.setBounds(106, 74, 184, 22);
		}
		return cbGbFlag;
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

	 * This method initializes jComboBox	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox2() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(106, 19, 184, 22);
			jComboBox.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
                    if (DgTxtFormatAdd.this.jComboBox
							.getSelectedItem()!=null){
					tfTaskName.setText("导入"+((ClassList) DgTxtFormatAdd.this.jComboBox
							.getSelectedItem()).getName());	
                    }

				}
			});

		}
		return jComboBox;
	}

}  //  @jve:decl-index=0:visual-constraint="6,10"
