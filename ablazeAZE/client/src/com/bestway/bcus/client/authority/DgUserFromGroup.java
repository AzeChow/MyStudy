/*
 * Created on 2004-6-8
 * editby xxm
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.authority;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclGroup;
import com.bestway.common.authority.entity.AclGroupUser;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgUserFromGroup extends JDialogBase {

	private AuthorityAction	authorityAction	= null;
	private JPanel			jContentPane	= null;
	private JToolBar		jToolBar		= null;
	private JScrollPane		jScrollPane		= null;
	private JTable			jTable			= null;
	private JButton			btnAdd			= null;
	private JButton			btnDelete		= null;
	private JButton			btnClose		= null;
	private JTableListModel	tableModel		= null;
	private AclUser			cureentAclUser	= null;

	/**
	 * This is the default constructor
	 */
	public DgUserFromGroup() {
		super();
		initialize();
		authorityAction = (AuthorityAction) CommonVars.getApplicationContext()
				.getBean("authorityAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */

	private void initialize() {
		this.setSize(450, 319);
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("用户所属于下列群组");
		
	}

	public void setVisible(boolean b) {
		if(b){
			showData();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());			
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("增加");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveData();
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteData();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		List<AclGroupUser> list = this.authorityAction.findAclGroupUser(
				new Request(CommonVars.getCurrUser()), this.cureentAclUser );
		initTable(list);
	}

	private void initTable(List list) {
		tableModel = new JTableListModel(this.getJTable(), list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("群组名称", "aclGroup.groupName", 150));
						list.add(addColumn("群组描述", "aclGroup.groupDescription",
								300));
						return list;
					}
				});
	}

	/**
	 * 增加用户隶属组
	 * 
	 */
	private void saveData() {
		AclUser user = cureentAclUser;
		List<AclGroup> currentGroupList = CommonQuery.getAclGroup(user);
		if(currentGroupList == null){
			return ;
		}
		for (AclGroup currentGroup : currentGroupList) {
			AclGroupUser groupUser = new AclGroupUser();
			groupUser.setAclGroup(currentGroup);
			groupUser.setAclUser(user);
			groupUser.setCompany(CommonVars.getCurrUser().getCompany());
			groupUser = authorityAction.saveGroupUser(
					new Request(CommonVars.getCurrUser()), groupUser);
			this.tableModel.addRow(groupUser);
		}
	}

	private void deleteData() {
		AclGroupUser groupUser = (AclGroupUser) tableModel.getCurrentRow();
		if (groupUser == null) {
			JOptionPane.showMessageDialog(this, "请选择要删除的组!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		authorityAction.deleteGroupUser(new Request(CommonVars.getCurrUser()),
				groupUser);
		this.tableModel.deleteRow(groupUser);
	}

	/**
	 * 设置当前用户
	 * 
	 * @param cureentAclUser
	 */
	public void setCureentAclUser(AclUser cureentAclUser) {
		this.cureentAclUser = cureentAclUser;
	}

}
