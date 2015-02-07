/*
 * Created on 2004-6-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.ClassList;

import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JCheckBox;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFieldCollateTableAdd extends JDialogBase {

	private JPanel jPanel = null;

	private JTextField tfptNo = null;

	private JTextField tfptName = null;

	private ClassList classList = null; //表头

	private boolean isAdd = true;

	private JTableListModel tableModel = null;

	private DataImportAction dataImportAction = null;

	private JTextField jTextField1 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JCheckBox jCheckBox = null;
	/**
	 * This is the default constructor
	 */
	public DgFieldCollateTableAdd() {
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
		this.setContentPane(getJPanel());
		this.setSize(344, 196);
		this.setTitle("表设置");
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				if (isAdd == false) { 
					if (tableModel.getCurrentRow() != null) {
						classList = (ClassList) tableModel.getCurrentRow();
					}
					initUIData();//初始化数据
				} else {
					DgFieldCollateTableAdd.this.tfptNo.setText(dataImportAction.getNum(new Request(
								CommonVars.getCurrUser()),"ClassList",null));
				}
			}
		});
	}

	private void initUIData() { //初始化数据
		
		tfptNo.setText(String.valueOf(classList.getSortno()));//序号
		tfptName.setText(classList.getName());
		jTextField1.setText(classList.getClassPath());
		if (classList.getIsCoid().equals(new Boolean(true))){
			this.jCheckBox.setSelected(true);
		} else {
			this.jCheckBox.setSelected(false);
		}
		
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
			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			jLabel.setText("序号");
			jLabel.setBounds(15, 24, 30, 19);
			jLabel3.setText("表中文名称");
			jLabel3.setBounds(16, 53, 68, 22);
			jLabel2.setBounds(17, 84, 56, 23);
			jLabel2.setText("类名称");
			jLabel1.setBounds(194, 55, 91, 19);
			jLabel1.setText("是否存在公司ID");
			jPanel.add(jLabel, null);
			jPanel.add(getTfptNo(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfptName(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJCheckBox(), null);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes tfptNo
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getTfptNo() {
		if (tfptNo == null) {
			tfptNo = new JTextField();
			tfptNo.setEditable(false);
			tfptNo.setBounds(91, 24, 142, 22);
		}
		return tfptNo;
	}

	/**
	 * 
	 * This method initializes tfptName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getTfptName() {
		if (tfptName == null) {
			tfptName = new JTextField();
			tfptName.setBounds(92, 53, 95, 22);
		}
		return tfptName;
	}

	/**
	 * 
	 * This method initializes jButton5
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private boolean checkNull() { //检查是否为空
		if (this.tfptNo.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "代码不能为空！", "提示！", 2);
			return true;
		}
		if (this.tfptName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "表中文名称不能为空！", "提示！", 2);
			return true;
		}
		if (this.jTextField1.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "类名称不能为空！", "提示！", 2);
			return true;
		}
		return false;
	}

	public void fillData(ClassList classList) { //保存	
		classList.setSortno(Integer.valueOf(tfptNo.getText()));
		classList.setName(tfptName.getText());
		if (this.jCheckBox.isSelected()){
			classList.setIsCoid(new Boolean(true));
		} else {
			classList.setIsCoid(new Boolean(false));
		}
		classList.setClassPath(this.jTextField1.getText());
	}

	private void clearData() {
		tfptNo.setText("");
		tfptName.setText("");
		this.jTextField1.setText("");
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
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}
	/**

	 * This method initializes jTextField1	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(93, 84, 219, 22);
		}
		return jTextField1;
	}

	/**

	 * This method initializes jButton	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(68, 123, 63, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					if (checkNull()) {
						return;
					}
					if (isAdd == true) {
						ClassList classList = new ClassList();
						fillData(classList);
						classList = dataImportAction.saveClassList(new Request(
								CommonVars.getCurrUser()), classList);
						tableModel.addRow(classList);
						clearData();
						DgFieldCollateTableAdd.this.tfptNo.setText(dataImportAction.getNum(new Request(
								CommonVars.getCurrUser()),"ClassList",null));
					} else {
						fillData(classList);
						classList = dataImportAction.saveClassList(new Request(
								CommonVars.getCurrUser()), classList);
						tableModel.updateRow(classList);
						DgFieldCollateTableAdd.this.dispose();
					}
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
			jButton1.setBounds(196, 123, 63, 25);
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgFieldCollateTableAdd.this.dispose();

				}
			});

		}
		return jButton1;
	}

	/**

	 * This method initializes jCheckBox	

	 * 	

	 * @return javax.swing.JCheckBox	

	 */    
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(288, 53, 21, 21);
		}
		return jCheckBox;
	}

   }  //  @jve:decl-index=0:visual-constraint="17,10"
//  @jve:decl-index=0:visual-constraint="10,10"
