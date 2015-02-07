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
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.client.util.JTableListModel;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;
import java.awt.Dimension;

public class DgOpenPort extends JDialogBase{


	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private CustomBaseAction customBaseAction = null;

	private boolean isAdd = true;

	private JTextField tfCode = null;
	private JTextField tfName = null;

	private JTableListModel tableModel = null;
	private PortLin portLin = null;

	private String scode = "";  //  @jve:decl-index=0:

	private JLabel jLabel21 = null;

	private JTextField tfName1 = null;

	private JLabel jLabel211 = null;

	private JTextField tfName11 = null;

	/**
	 * This is the default constructor
	 */
	public DgOpenPort() {
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
		this.setSize(407, 236);
		this.setTitle("国际进出口港口");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						portLin = (PortLin) tableModel.getCurrentRow();
						scode = portLin.getName();
					}
					fillWindow();
					tfCode.setEditable(false);
				}
			}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel211 = new JLabel();
			jLabel211.setBounds(new Rectangle(14, 131, 72, 18));
			jLabel211.setText("航线");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(14, 95, 72, 18));
			jLabel21.setText("港口中文名称");
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("港口代码");
			jLabel.setBounds(14, 24, 82, 23);
			jLabel2.setText("港口英文名称");
			jLabel2.setBounds(14, 57, 82, 23);
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getTfCode(), null);
			jPanel2.add(getTfName(), null);
			jPanel2.add(jLabel21, null);
			jPanel2.add(getTfName1(), null);
			jPanel2.add(jLabel211, null);
			jPanel2.add(getTfName11(), null);
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
			jButton.setBounds(101, 163, 71, 26);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}

					if (isAdd == true) {
						PortLin portLin = new PortLin();
						fillCurrCode(portLin);
						if (isReCode(DgOpenPort.this.tfCode.getText())) {
							return;
						}
						portLin = customBaseAction.SaveportLin(portLin);
						tableModel.addRow(portLin);
						
						System.out.println("保存成功！");
					    clearData();
					} else {
						 fillCurrCode(portLin);
						 portLin = customBaseAction.SaveportLin(portLin);
						 Dialog();
						 tableModel.updateRow(portLin);
						 DgOpenPort.this.dispose();
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
		if (customBaseAction.isReDgOpenPortcode(data)) {
			JOptionPane.showMessageDialog(this, "该代码已存在！", "提示", 2);
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
			jButton1.setBounds(220, 164, 70, 25);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgOpenPort.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		System.out.println(portLin.getName());
		if (portLin != null) {
			this.tfCode.setText(portLin.getCode());
			this.tfName.setText(portLin.getName());
			this.tfName1.setText(portLin.getPortEnname());
			this.tfName11.setText(portLin.getPortLine());
		}
	}

	private void fillCurrCode(PortLin portLin) {
		portLin.setCode(this.tfCode.getText().trim());
		portLin.setName(this.tfName.getText().trim());
		portLin.setPortEnname(this.tfName1.getText().trim());
		portLin.setPortLine(this.tfName11.getText().trim());
	}

	private void clearData() {
		this.tfCode.setText("");
		this.tfName.setText("");

	}

	private boolean checkNull() {
		if (this.tfCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "代码不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (this.tfName1.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "中文名称不能为空,请输入!", "提示!", 1);
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
			tfCode.setBounds(105, 22, 231, 22);
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
			tfName.setBounds(105, 58, 231, 22);
		}
		return tfName;
	}

	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * This method initializes tfName1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfName1() {
		if (tfName1 == null) {
			tfName1 = new JTextField();
			tfName1.setBounds(new Rectangle(105, 92, 231, 22));
		}
		return tfName1;
	}

	/**
	 * This method initializes tfName11	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfName11() {
		if (tfName11 == null) {
			tfName11 = new JTextField();
			tfName11.setBounds(new Rectangle(105, 127, 231, 22));
		}
		return tfName11;
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"
