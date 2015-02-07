/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.custombase;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.PortInternal;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.client.util.JTableListModel;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Point;
import javax.swing.SwingConstants;

/**
 * @author zyy 2012.3.13
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgPreDock extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private CustomBaseAction customBaseAction = null;

	private boolean isAdd = true;

	private JTextField tfCode = null;
	private JTextField tfName = null;
	private JTextField tfShortName = null;
	private JTextField tfCusCode = null;
	private JTableListModel tableModel = null;
	private PreDock preDock = null;

	private String scode = "";

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	/**
	 * This is the default constructor
	 */
	public DgPreDock() {
		super();
		initialize();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(391, 240);
		this.setTitle("国内进出口码头编辑");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						preDock = (PreDock) tableModel.getCurrentRow();
						scode = preDock.getName();
					}
					fillWindow();
					tfCode.setEditable(false);
					tfCusCode.setEditable(false);
				}
			}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("港口代码");
			jLabel.setBounds(36, 24, 58, 23);
			jLabel2.setText("港口名称");
			jLabel2.setBounds(36, 57, 58, 23);
			jLabel3.setText("简称");
			jLabel3.setBounds(36, 90, 58, 23);
			jLabel4.setText("关区");
			jLabel4.setBounds(36, 123, 58, 23);
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getTfCode(), null);
			jPanel2.add(getTfName(), null);
			jPanel2.add(getTfShortName(), null);
			jPanel2.add(getTfCusCode(), null);
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
			jButton.setText("确定");
			jButton.setBounds(65, 165, 71, 26);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}

					if (isAdd == true) {
						PreDock preDock = new PreDock();
						fillCurrCode(preDock);
						if (isReCode(DgPreDock.this.tfCode.getText())) {
							return;
						}
						if (isReName(DgPreDock.this.tfName.getText())) {
							return;
						}
						preDock = customBaseAction.SavePreDock(preDock);
						tableModel.addRow(preDock);
						
						System.out.println("保存成功！");
					    clearData();
					} else {
						 fillCurrCode(preDock);
						 if (isReName(preDock.getName())){
						 return;
						 }
						 preDock = customBaseAction.SavePreDock(preDock);
						 Dialog();
						 tableModel.updateRow(preDock);
						 DgPreDock.this.dispose();
					}

				}

			});

		}
		return jButton;
	}

	private void Dialog(){
		JOptionPane.showMessageDialog(this, "修改已完成，同时会更新到其它被使用的模块！", "提示", 2);
	}
	
	private boolean isReCode(String data) {
		if (customBaseAction.isReDgPreDockCode(data)) {
			JOptionPane.showMessageDialog(this, "该代码已存在！", "提示", 2);
			return true;
		}
		return false;
	}

	private boolean isReName(String name) {
		if (customBaseAction.isReDgPreDockName(name)) {
			JOptionPane.showMessageDialog(this, "该名称已存在！", "提示", 2);
			return true;
		}
		return false;
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
			jButton1.setText("取消");
			jButton1.setBounds(199, 164, 70, 25);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgPreDock.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		if (preDock != null) {
			this.tfCode.setText(preDock.getCode());
			this.tfName.setText(preDock.getName());
			this.tfShortName.setText(preDock.getShortName());
			this.tfCusCode.setText(preDock.getCusCode());
		}
	}

	private void fillCurrCode(PreDock preDock) {
		preDock.setCode(this.tfCode.getText().trim());
		preDock.setName(this.tfName.getText().trim());
		preDock.setShortName(this.tfShortName.getText().trim());
		preDock.setCusCode(this.tfCusCode.getText().trim());
	}

	private void clearData() {
		this.tfCode.setText("");
		this.tfName.setText("");
		this.tfShortName.setText("");
		this.tfCusCode.setText("");

	}

	private boolean checkNull() {
		if (this.tfCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "代码不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (this.tfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "名称不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (this.tfCusCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "关区不能为空,请输入!", "提示!", 1);
			return true;
		}
		return false;
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
	 * 
	 * This method initializes tfCode
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(103, 22, 200, 22);
		}
		return tfCode;
	}

	/**
	 * 
	 * This method initializes tfName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(103, 58, 200, 22);
		}
		return tfName;
	}
	/**
	 * This method initializes tfShortName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfShortName() {
		if (tfShortName == null) {
			tfShortName = new JTextField();
			tfShortName.setBounds(103, 94, 200, 22);
		}
		return tfShortName;
	}

	/**
	 * This method initializes tfCusCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCusCode() {
		if (tfCusCode == null) {
			tfCusCode = new JTextField();
			tfCusCode.setBounds(103, 130, 200, 22);
		}
		return tfCusCode;
	}

	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
