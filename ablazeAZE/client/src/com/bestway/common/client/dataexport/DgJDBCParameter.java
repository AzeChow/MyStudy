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
import com.bestway.common.dataexport.entity.JDBCParameter;
import com.bestway.ui.winuicontrol.JDialogBase;


/**
 * @author luosheng 2006/9/1
 * 
 */
public class DgJDBCParameter extends JDialogBase {

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

	/**
	 * This is the default constructor
	 */
	public DgJDBCParameter() {
		super(false);
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		initialize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

					DgJDBCParameterEdit root = new DgJDBCParameterEdit();
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
						JOptionPane.showMessageDialog(DgJDBCParameter.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					DgJDBCParameterEdit root = new DgJDBCParameterEdit();
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
					if (JOptionPane.showConfirmDialog(DgJDBCParameter.this,
							"确定删除吗?", "提示", 0) == 0) {
						if (tableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgJDBCParameter.this,
									"请选中要删除的资料！", "提示", 2);
							return;
						}
						JDBCParameter obj = (JDBCParameter) tableModel
								.getCurrentRow();
						try {
							dataExportAction.deleteJDBCParameter(new Request(
									CommonVars.getCurrUser()), obj);
							tableModel.deleteRow(obj);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(DgJDBCParameter.this,
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

					DgJDBCParameter.this.dispose();
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

	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("名称", "pname", 100));
						list.add(addColumn("值", "pvalue", 100));
						list.add(addColumn("是否当前日期", "isNowDate", 120));
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
		this.setTitle("参数设置操作");
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
		List list = dataExportAction.findJDBCParameter(new Request(CommonVars
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
} 
