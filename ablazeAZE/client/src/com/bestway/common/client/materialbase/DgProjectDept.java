package com.bestway.common.client.materialbase;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgProjectDept extends JDialogBase {

	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JTextField jTextField = null;
	private JTextField jTextField1 = null;
	private JTextField jTextField2 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTableListModel tableModel = null;

	private MaterialManageAction materialManageAction = null;
	private boolean isAdd = true;
	private ProjectDept obj = null;   //企业
	
	/**
	 * This is the default constructor
	 */
	public DgProjectDept() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
		             .getApplicationContext().getBean("materialManageAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(401, 241);
		this.setContentPane(getJContentPane());
		this.setTitle("事业部设置");
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						obj = (ProjectDept) tableModel.getCurrentRow();
					}
					fillWindow();
				} else {
				}
			}
		});
	}

	private void fillWindow() {
		if (obj != null) {
			this.jTextField.setText(obj.getCode());
			this.jTextField1.setText(obj.getName());
			this.jTextField2.setText(obj.getNote());
		}
	}
	
	private void fillData(ProjectDept obj) {
		obj.setCode(this.jTextField.getText().trim());
		obj.setName(this.jTextField1.getText().trim());
		obj.setNote(this.jTextField2.getText().trim());
		obj.setCompany(CommonVars.getCurrUser().getCompany());	
	}
	
	
	private void addData() {
		ProjectDept obj = new ProjectDept();
		fillData(obj);

		obj = materialManageAction.saveProjectDept(new Request(CommonVars
				.getCurrUser()), obj);
		tableModel.addRow(obj);

		System.out.println("保存成功！");
	}

	private void modifyData() {
		fillData(obj);
		obj = materialManageAction.saveProjectDept(new Request(CommonVars
				.getCurrUser()), obj);
		tableModel.updateRow(obj);

	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(58,98,73,18));
			jLabel2.setText("备注");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(58,69,68,18));
			jLabel1.setText("事业部名称");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(58,38,71,18));
			jLabel.setText("事业部代码");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
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
			jTextField.setBounds(new java.awt.Rectangle(141,38,176,22));
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
			jTextField1.setBounds(new java.awt.Rectangle(141,69,175,22));
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
			jTextField2.setBounds(new java.awt.Rectangle(141,98,175,22));
		}
		return jTextField2;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(104,143,75,28));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if (isAdd == true) {
						addData();
						clearData();
					} else {
						modifyData();
						dispose();
					}

				}
			});
		}
		return jButton;
	}
	
	private void clearData(){
		this.jTextField.setText("");
		this.jTextField1.setText("");
		this.jTextField2.setText("");
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(207,143,75,28));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgProjectDept.this.dispose();

				}

			});

		}
		return jButton1;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	public boolean isAdd() {
		return isAdd;
	}

	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
