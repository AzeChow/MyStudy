/*
 * Created on 2004-6-8
 * editby xxm
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.authority;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmUser extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private AuthorityAction authorityAction = null;

	private JTableListModel tableModel = null;

	private JButton jButton = null;

	private JButton btnFromGroup = null;

	private JButton btnEditPassword = null;

	private AclUser currentUser = null;

	/**
	 * This is the default constructor
	 */
	public FmUser() {
		super();
		authorityAction = (AuthorityAction) CommonVars.getApplicationContext()
				.getBean("authorityAction");
		initialize();

	}

	/**
	 * 初始化 FmUser
	 */
	private void initialize() {

		setTitle("用户管理");

		setHelpId("user");

		setSize(521, 333);

		setContentPane(getJContentPane());

		addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			public void internalFrameOpened(
					javax.swing.event.InternalFrameEvent e) {
				loadData();
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
			jContentPane = new javax.swing.JPanel();
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
			jToolBar.add(getBtnEditPassword());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnFromGroup());
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

					authorityAction.checkAuthorityUserByAdd(new Request(
							CommonVars.getCurrUser()));

					DgUser dgUser = new DgUser();

					dgUser.setIsAdd(true);

					dgUser.setTableModel(tableModel);

					dgUser.setVisible(true);

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

					authorityAction.checkAuthorityUserByUpdate(new Request(
							CommonVars.getCurrUser()));
					DgUser dgUser = new DgUser();
					dgUser.setIsAdd(false);
					dgUser.setTableModel(tableModel);
					dgUser.setVisible(true);

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
					AclUser user = (AclUser) tableModel.getCurrentRow();
					if (user.getLoginName().toLowerCase().equals("admin")) {
						JOptionPane.showMessageDialog(FmUser.this,
								"超级用户admin不能被删除!", "确认", 1);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmUser.this,
							"你确定要删除此记录吗?", "确认", 0) == 0) {
						try {
							authorityAction.deleteUser(
									new Request(CommonVars.getCurrUser()), user);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(FmUser.this,
									"你所选择的用户正在被引用，不能删除！", "确认", 1);
							return;
						}
						tableModel.deleteRow(user);
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
						list.add(addColumn("用户名字", "userName", 100));
						list.add(addColumn("Email", "email", 100));
						list.add(addColumn("所属工厂", "company.name", 180));
						JTableListColumn column = addColumn("超级用户", "userFlag",
								50);
						list.add(column);
						list.add(addColumn("是否禁用", "isForbid", 50));
						return list;
					}
				});
		TableColumn column = this.getJTable().getColumnModel().getColumn(5);
		column.setCellRenderer(new DefaultTableCellRenderer() {
			JCheckBox checkBox = new JCheckBox();

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				if (isSelected) {
					checkBox.setForeground(table.getSelectionForeground());
					checkBox.setBackground(table.getSelectionBackground());
				} else {
					checkBox.setForeground(table.getForeground());
					checkBox.setBackground(table.getBackground());
				}
				checkBox.setSelected(false);
				if (value != null)
					checkBox.setSelected(value.equals("S"));
				return checkBox;
			}
		});
		this.getJTable().getColumnModel().getColumn(6)
				.setCellRenderer(new TableCheckBoxRender());
	}

	private void loadData() {
		Request request = new Request();
		request.setUser(CommonVars.getCurrUser());
		request.setModuleName("");
		request.setPermission("");
		List dataSource = authorityAction.findUsers(request);
		if (dataSource != null && !dataSource.isEmpty()) {
			initTable(dataSource);
		} else {
			initTable(new Vector());
		}
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

	/**
	 * This method initializes btnFromGroup
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFromGroup() {
		if (btnFromGroup == null) {
			btnFromGroup = new JButton();
			btnFromGroup.setText("所属群组");
			btnFromGroup.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmUser.this,
								"请选择你要隶属于的用户", "确认", 1);
						return;
					}
					AclUser user = (AclUser) tableModel.getCurrentRow();
					if (user.getUserFlag() != null
							&& user.getUserFlag().toLowerCase().trim()
									.equals("s")) {
						JOptionPane.showMessageDialog(FmUser.this,
								"超级用户admin不需要隶属组!", "确认", 1);
						return;
					}
					DgUserFromGroup dgUser = new DgUserFromGroup();
					dgUser.setCureentAclUser(user);
					dgUser.setVisible(true);
				}
			});
		}
		return btnFromGroup;
	}

	/**
	 * This method initializes btnEditPassword
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditPassword() {
		if (btnEditPassword == null) {
			btnEditPassword = new JButton();
			btnEditPassword.setText("修改密码");
			btnEditPassword
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							authorityAction
									.checkAuthorityUserByUpdatePW(new Request(
											CommonVars.getCurrUser()));
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(FmUser.this,
										"请选择你要修改的资料", "确认", 1);
								return;
							}
							authorityAction
									.checkAuthorityUserByUpdatePW(new Request(
											CommonVars.getCurrUser()));
							DgEditPassword dgUser = new DgEditPassword();
							if (tableModel.getCurrentRow() != null) {
								currentUser = (AclUser) tableModel
										.getCurrentRow();
							}
							dgUser.setCurrentUser(currentUser);
							dgUser.setVisible(true);

						}
					});
		}
		return btnEditPassword;
	}

}
