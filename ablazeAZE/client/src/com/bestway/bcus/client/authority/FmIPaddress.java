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

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclIPaddress;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmIPaddress extends JInternalFrameBase {
	private AuthorityAction authorityAction = null; // @jve:decl-index=0:

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton jButton = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JTableListModel tableModel = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmIPaddress() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("IP地址管理");
		this.setContentPane(getJContentPane());
		this.setSize(521, 333);
		authorityAction = (AuthorityAction) CommonVars.getApplicationContext()
				.getBean("authorityAction");
		initTable();
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
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getJButton());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setName("btnAdd");
			btnAdd.setText("\u65b0\u589e");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgIPaddress dg = new DgIPaddress();
					dg.setTableModel(tableModel);
					dg.setState(DataState.ADD);
					dg.setVisible(true);
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setName("btnEdit");
			btnEdit.setText("\u4fee\u6539");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmIPaddress.this,
								"请选择要修改的数据!", "提示!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgIPaddress dg = new DgIPaddress();
					dg.setTableModel(tableModel);
					dg.setState(DataState.EDIT);
					dg.setVisible(true);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setName("btnDelete");
			btnDelete.setText("\u5220\u9664");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmIPaddress.this,
								"请选择要删除的数据!", "提示!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					AclIPaddress aclIPaddress = (AclIPaddress) tableModel
							.getCurrentRow();
					tableModel.deleteRow(aclIPaddress);
					authorityAction.deleteIPaddress(new Request(CommonVars
							.getCurrUser()), aclIPaddress);

				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("\u5173\u95ed");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton;
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

	private void initTable() {
		List datalist = authorityAction.findIPaddress(new Request(CommonVars
				.getCurrUser()));
		tableModel = new JTableListModel(this.getJTable(), datalist,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("起始IP", "showBegianIP", 100));
						list.add(addColumn("结束IP", "showEndIP", 100));
						list.add(addColumn("创建时间", "showCreateDate", 100));
						list.add(addColumn("修改时间", "showLastModifDate", 100));
						list.add(addColumn("备注", "explain", 200));
						return list;

					}
				});

	}
} // @jve:decl-index=0:visual-constraint="10,10"
