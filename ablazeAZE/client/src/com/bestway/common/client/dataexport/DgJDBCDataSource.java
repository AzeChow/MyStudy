/*
 * Created on 2004-11-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.ui.winuicontrol.JDialogBase;


/**
 * @author luosheng 2006/9/1
 * 
 */
public class DgJDBCDataSource extends JDialogBase {

	private javax.swing.JPanel	jContentPane		= null;
	private JToolBar			jToolBar			= null;
	private JButton				btnAdd				= null;
	private JButton				btnEdit				= null;
	private JButton				btnDelete			= null;
	private JButton				btnClose			= null;
	private JTable				jTable				= null;
	private JScrollPane			jScrollPane			= null;
	private JTableListModel		tableModel			= null;
	private DataExportAction	dataExportAction	= null;
	private JButton				btnTestConnection	= null;

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
			jToolBar.add(getBtnTestConnection());
			jToolBar.add(getBtnClose());			
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
			btnAdd.setText("增加");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgJDBCDataSourceEdit root = new DgJDBCDataSourceEdit();
					root.setAdd(true);
					root.setTableModel(tableModel);
					root.setVisible(true);
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
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgJDBCDataSource.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					DgJDBCDataSourceEdit root = new DgJDBCDataSourceEdit();
					root.setAdd(false);
					root.setTableModel(tableModel);
					root.setVisible(true);
				}
			});

		}
		return btnEdit;
	}

	/**
	 * 
	 * This method initializes btnDelete
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
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgJDBCDataSource.this,
							"确定删除吗?", "提示", 0) == 0) {
						if (tableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgJDBCDataSource.this,
									"请选中要删除的资料！", "提示", 2);
							return;
						}
						JDBCDataSource obj = (JDBCDataSource) tableModel
								.getCurrentRow();
						try {
							dataExportAction.deleteJDBCDataSource(new Request(
									CommonVars.getCurrUser()), obj);
							tableModel.deleteRow(obj);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(DgJDBCDataSource.this,
									"已被引用，不能删除！", "提示", 2);
						}
					}
				}
			});

		}
		return btnDelete;
	}

	/**
	 * 
	 * This method initializes btnClose
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgJDBCDataSource.this.dispose();
				}
			});

		}
		return btnClose;
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

	/**
	 * This is the default constructor
	 */
	public DgJDBCDataSource() {
		super(false);
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initialize();
	}

	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("名称", "name", 175));
						list.add(addColumn("连接字符串", "driverClassName", 425));
						return list;
					}
				});
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("数据源操作");
		this.setSize(639, 448);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				initData();
			}
		});

	}

	private void initData() {
		List list = dataExportAction.findJDBCDataSource(new Request(CommonVars
				.getCurrUser()));
		initTable(list);
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
	 * This method initializes btnTestConnection
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnTestConnection() {
		if (btnTestConnection == null) {
			btnTestConnection = new JButton();
			btnTestConnection.setText("测试连接");
			btnTestConnection
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							testConnect();
						}
					});
		}
		return btnTestConnection;
	}

	/**
	 * 测试连接
	 * 
	 */
	private void testConnect() {

		JDBCDataSource jdbcDataSource = (JDBCDataSource) tableModel.getCurrentRow();
		if (jdbcDataSource == null) {
			JOptionPane.showMessageDialog(DgJDBCDataSource.this, "请选择测试的记录", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String jdbcDriver = jdbcDataSource.getDriverClassName();
		String jdbcUrl = jdbcDataSource.getUrl();
		String userName = jdbcDataSource.getUserName();
		String password = jdbcDataSource.getPassword();

		boolean isOk = dataExportAction.isConnect(jdbcDriver, jdbcUrl, userName, password);		
		JOptionPane.showMessageDialog(DgJDBCDataSource.this, isOk?"连接成功":"连接失败", "提示",
				JOptionPane.INFORMATION_MESSAGE);
	}

} 
