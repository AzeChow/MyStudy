/*
 * Created on 2004-11-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.common.dataexport.entity.JDBCSqlEvent;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class DgJDBCSqlEvent extends JDialogBase {

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
	private JButton				btnExecute			= null;
	private JSplitPane			jSplitPane			= null;
	private JPanel				pnLeft				= null;
	private JPanel				pnRight				= null;
	private JToolBar			jJToolBarBar		= null;
	private JScrollPane			jScrollPane1		= null;
	private JList				jList				= null;
	private JLabel				jLabel				= null;

	/**
	 * This is the default constructor
	 */
	public DgJDBCSqlEvent() {
		super(false);
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		initialize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initUIComponents();
	}

	private void initUIComponents() {
		List<JDBCDataSource> list = this.dataExportAction
				.findJDBCDataSource(new Request(CommonVars.getCurrUser()));
		this.jList.setListData(list.toArray());
		this.jList.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				if (value != null && value instanceof JDBCDataSource) {
					JDBCDataSource d = (JDBCDataSource) value;
					this.setText(d.getName());
				}
				return this;
			}
		});
		if (this.jList.getModel().getSize() > 0) {
			this.jList.setSelectedIndex(0);
		}
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
			jToolBar.add(getBtnExecute());
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
					JDBCDataSource jdbcDataSource = (JDBCDataSource) (jList.getSelectedValue());
					if(jdbcDataSource == null){
						JOptionPane.showMessageDialog(DgJDBCSqlEvent.this,
								"请选择数据源", "确认", 2);
						return;
					}
					DgJDBCSqlEventEdit dg = new DgJDBCSqlEventEdit();
					dg.setDataState(DataState.ADD);
					dg.setTableModel(tableModel);					
					dg.setJdbcDataSource(jdbcDataSource);
					dg.setVisible(true);

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
					JDBCSqlEvent sqlEvent = (JDBCSqlEvent)tableModel.getCurrentRow();
					if (sqlEvent == null) {
						JOptionPane.showMessageDialog(DgJDBCSqlEvent.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					DgJDBCSqlEventEdit dg = new DgJDBCSqlEventEdit();
					dg.setDataState(DataState.EDIT);
					dg.setTableModel(tableModel);
					dg.setJdbcDataSource(sqlEvent.getJdbcDataSource());
					dg.setVisible(true);
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
					if (JOptionPane.showConfirmDialog(DgJDBCSqlEvent.this,
							"确定删除吗?", "提示", 0) == 0) {
						if (tableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgJDBCSqlEvent.this,
									"请选中要删除的资料！", "提示", 2);
							return;
						}

						JDBCSqlEvent obj = (JDBCSqlEvent) tableModel
								.getCurrentRow();
						try {
							dataExportAction.deleteJDBCSqlEvent(new Request(
									CommonVars.getCurrUser()), obj);
							tableModel.deleteRow(obj);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(DgJDBCSqlEvent.this,
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

					DgJDBCSqlEvent.this.dispose();
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
						list.add(addColumn("事件名称", "name", 250));
						list.add(addColumn("执行语句", "sqlStr", 380));
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
		this.setTitle("事件设置");
		this.setSize(733, 541);
		this.setContentPane(getJContentPane());
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			initData();
		}
		super.setVisible(b);
	}

	private void initData() {
		
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnExecute
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExecute() {
		if (btnExecute == null) {
			btnExecute = new JButton();
			btnExecute.setText("执行");
			btnExecute.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgJDBCSqlEvent.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					JDBCSqlEvent sqlEvent = (JDBCSqlEvent) tableModel
							.getCurrentRow();
					new execusql(sqlEvent).start();
				}
			});
		}
		return btnExecute;
	}

	class execusql extends Thread {
		JDBCSqlEvent	sqlEvent	= null;

		public execusql(JDBCSqlEvent sqlEvent) {
			this.sqlEvent = sqlEvent;
		}

		@Override
		public void run() {
			try {
				//
				// 用于标识这个对话框的ID
				//
				UUID uuid = UUID.randomUUID();
				final String flag = uuid.toString();

				CommonProgress.showProgressDialog(flag, DgJDBCSqlEvent.this);
				CommonProgress.setMessage(flag, "正在执行 SQL 语句, 请稍后...");
				dataExportAction.execuJDBCSqlEvent(new Request(CommonVars
						.getCurrUser()), sqlEvent);
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgJDBCSqlEvent.this, "执行成功",
						"提示", 2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgJDBCSqlEvent.this, "执行失败"
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getPnLeft());
			jSplitPane.setRightComponent(getPnRight());
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setDividerLocation(155);
			jSplitPane.setDividerSize(10);

		}
		return jSplitPane;
	}

	/**
	 * This method initializes pnLeft
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnLeft() {
		if (pnLeft == null) {
			pnLeft = new JPanel();
			pnLeft.setLayout(new BorderLayout());
			pnLeft.add(getJJToolBarBar(), BorderLayout.NORTH);
			pnLeft.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return pnLeft;
	}

	/**
	 * This method initializes pnRight
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnRight() {
		if (pnRight == null) {
			pnRight = new JPanel();
			pnRight.setLayout(new BorderLayout());
			pnRight.add(getJScrollPane(), BorderLayout.CENTER);
			pnRight.add(getJToolBar(), BorderLayout.NORTH);

		}
		return pnRight;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jLabel = new JLabel();
			jLabel.setText(" 数据源");
			jLabel.setPreferredSize(new Dimension(60, 18));
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setFloatable(false);
			jJToolBarBar.add(jLabel);
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

						public void valueChanged(
								javax.swing.event.ListSelectionEvent e) {
							if (e.getValueIsAdjusting() == true) {
								return;
							}
							JDBCDataSource jdbcDataSource = (JDBCDataSource) (((JList) e
									.getSource()).getSelectedValue());							
							List list = dataExportAction.findJDBCSqlEvent(new Request(CommonVars
									.getCurrUser()),jdbcDataSource);
							initTable(list);
						}
					});
		}
		return jList;
	}

}
