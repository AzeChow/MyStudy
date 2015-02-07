/*
 * Created on 2004-6-8
 * EditBY xxm
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.authority;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.InitialFocusSetter;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclGroup;
import com.bestway.ui.winuicontrol.JDialogBase;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgGroup extends JDialogBase {

	/**
	 * @param owner
	 * @throws java.awt.HeadlessException
	 */

	private javax.swing.JPanel jContentPane = null;

	private JTextField tfGroupName = null;
	private JTextField tfGroupDesc = null;
	private JButton btnDone = null;
	private JButton btnCancel = null;
	private AuthorityAction authorityAction = null;
	private AclGroup currentGroup = null;
	private JTableListModel tableModel = null;
	private boolean isAdd = true;

	/**
	 * @param arg0
	 * @throws java.awt.HeadlessException
	 */

	public boolean isIsAdd() {
		return isAdd;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	public void setIsAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * This is the default constructor
	 */
	public DgGroup() {
		super();
		authorityAction = (AuthorityAction) CommonVars.getApplicationContext().getBean("authorityAction");
		initialize();
		//pack();
	}
	
	public void setVisible(boolean b) {

		// 设置最初的焦点
		InitialFocusSetter.setInitialFocus(this, tfGroupName);
		super.setVisible(b);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("群组编辑");
		this.setHelpId("group");
		this.setModal(true);
		this.setSize(300, 183);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						currentGroup = (AclGroup) tableModel.getCurrentRow();
					}
					fillWindow();
				}
			}
		});

	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("群组名称");
			jLabel.setBounds(61, 25, 52, 25);
			jLabel1.setText("群组描述");
			jLabel1.setBounds(61, 61, 53, 25);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfGroupName(), null);
			jContentPane.add(getTfGroupDesc(), null);
			jContentPane.add(getBtnDone(), null);
			jContentPane.add(getBtnCancel(), null);
		}
		return jContentPane;
	}
	private boolean checkNull() {
		if (tfGroupName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "群组名称不能为空,请输入!", "提示!", 1);
			return true;
		}
		return false;
	}

	private boolean checkMultiple() {
		AclGroup group = authorityAction.findGroup(new Request(CommonVars.getCurrUser()),
				tfGroupName.getText().trim());
		if (group != null) {
			if (!isAdd) {
				if (!group.getId().equals(currentGroup.getId())) {
					JOptionPane.showMessageDialog(this, "群组名称不能重复,请重新输入!",
							"提示!", 1);
					return true;
				}
			} else {
				JOptionPane
						.showMessageDialog(this, "群组名称不能重复,请重新输入!", "提示!", 1);
				return true;
			}
		}
		return false;
	}

	private void fillGroup(AclGroup group) {
		group.setGroupName(tfGroupName.getText().trim());
		group.setGroupDescription(tfGroupDesc.getText().trim());
		group.setCompany(CommonVars.getCurrUser().getCompany());
	}

	private void addGroup() {
		AclGroup group = new AclGroup();
		fillGroup(group);
		group = authorityAction.saveGroup(new Request(CommonVars.getCurrUser()), group);
		tableModel.addRow(group);
	}

	private void modifyGroup() {
		fillGroup(currentGroup);
		currentGroup = authorityAction.saveGroup(new Request(CommonVars.getCurrUser()), currentGroup);
		tableModel.updateRow(currentGroup);
	}
	/**
	 * 
	 * This method initializes tfGroupName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getTfGroupName() {
		if (tfGroupName == null) {
			tfGroupName = new JTextField();
			tfGroupName.setBounds(129, 28, 112, 22);
		}
		return tfGroupName;
	}

	/**
	 * 
	 * This method initializes tfGroupDesc
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getTfGroupDesc() {
		if (tfGroupDesc == null) {
			tfGroupDesc = new JTextField();
			tfGroupDesc.setBounds(129, 61, 112, 22);
		}
		return tfGroupDesc;
	}

	/**
	 * 
	 * This method initializes btnDone
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnDone() {
		if (btnDone == null) {
			btnDone = new JButton();
			btnDone.setText("确定");
			btnDone.setBounds(74, 107, 64, 23);
			btnDone.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (checkNull()) {
						return;
					}
					if (checkMultiple()) {
						return;
					}
					if (isAdd == true) {
						addGroup();
					} else {
						modifyGroup();
					}
					DgGroup.this.dispose();
				}
			});

		}
		return btnDone;
	}
	private void fillWindow() {
		if (currentGroup != null) {
			this.tfGroupName.setText(currentGroup.getGroupName());
			this.tfGroupDesc.setText(currentGroup.getGroupDescription());
		}
	}
	/**
	 * 
	 * This method initializes btnCancel
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.setBounds(154, 107, 64, 23);
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgGroup.this.dispose();
				}
			});

		}
		return btnCancel;
	}

} //  @jve:decl-index=0:visual-constraint="70,40"
