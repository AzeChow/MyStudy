/*
 * Created on 2004-6-8
 * editby xxm
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmUser extends JDialogBase {

	private javax.swing.JPanel	jContentPane	= null;
	private JToolBar			jToolBar		= null;
	private JButton				btnAdd			= null;
	private JButton				btnEdit			= null;
	private JButton				btnDelete		= null;
	private JTable				jTable			= null;
	private JScrollPane			jScrollPane		= null;
	private JTableListModel		tableModel		= null;
	private JButton				jButton			= null;
	/**
	 * This is the default constructor
	 */
	public FmUser() {
		super();
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("用户管理");
		this.setSize(521, 333);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
						loadData();
					}
				});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
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
	 * 
	 * This method initializes btnAdd
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setName("btnAdd");
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					
				}
			});

		}
		return btnAdd;
	}

	/**
	 * 
	 * This method initializes btnEdit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setName("btnEdit");
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmUser.this,
								"请选择你要修改的资料", "确认", 1);
						return;
					}

				}
			});

		}
		return btnEdit;
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setName("btnDelete");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmUser.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}
					
				}
			});

		}
		return btnDelete;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		jTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
					}
				});
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	private void initTable(List list) {
		tableModel = new JTableListModel(this.getJTable(), list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("登陆编号", "loginName", 100));
						list.add(addColumn("用户名字", "name", 100));
						list.add(addColumn("密码", "loginPw", 100));
						return list;
					}
				});

	}

	private void loadData() {
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
			jButton.setText("关闭");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dispose();

				}
			});

		}
		return jButton;
	}

}
