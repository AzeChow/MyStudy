/*
 * Created on 2004-10-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.client.common.TableTextFieldEditor;
import com.bestway.bcus.client.common.TableTextFieldEditorEvent;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.JDBCSqlEvent;
import com.bestway.common.dataexport.entity.TxtDBTask;
import com.bestway.common.dataexport.entity.TxtDBTaskDetail;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class DgTxtTask extends JDialogBase {

	private javax.swing.JPanel	jContentPane		= null;
	private DataExportAction	dataExportAction	= null;
	private JPanel				jPanel				= null;
	private JSplitPane			jSplitPane			= null;
	private JPanel				jPanel1				= null;
	private JPanel				jPanel2				= null;
	private JToolBar			jToolBar			= null;
	private JButton				btnAdd				= null;
	private JButton				btnEdit				= null;
	private JButton				btnDelete			= null;
	private JTable				jTable				= null;
	private JScrollPane			jScrollPane			= null;
	private JToolBar			jToolBar1			= null;
	private JButton				btnAddDBToTxtRegion	= null;
	private JTable				jTable1				= null;
	private JScrollPane			jScrollPane1		= null;
	private TxtDBTask			txtDBTask			= null;
	private JTableListModel		tableModel			= null;
	private JTableListModel		tableModel1			= null;
	private JButton				btnRemoveRegion		= null;
	private JButton				btnClose			= null;
	private JButton				btnAddEvent			= null;

	/**
	 * This is the default constructor
	 */
	public DgTxtTask() {
		super(false);
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		initialize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("文本导入导出任务设置");
		this.setSize(733, 541);
		this.setContentPane(getJContentPane());

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
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("任务名称", "taskname", 200));
						list.add(addColumn("是否是父任务", "isParentTask", 100));
						list.add(addColumn("运行状态", "isExecute", 80));
						list.add(addColumn("备注", "note", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new TableCheckBoxRender());

	}

	private void initTableDetail(List dataSource, final boolean isParentTask) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				if (isParentTask) {
					list.add(addColumn("执行顺序", "seqNum", 80));
					list.add(addColumn("子任务名称", "subJDBCTask.taskname", 200));
				} else {
					list.add(addColumn("执行顺序", "seqNum", 80));
					list.add(addColumn("执行事件名称", "jdbcSqlEvent.name", 200));
					list.add(addColumn("DB导出到文本域名称",
							"dbToTxtRegion.regionName", 200));
					list.add(addColumn("文本导出到DB域名称",
							"txtToDBRegion.regionName", 200));
				}
				return list;
			}
		};

		tableModel1 = new JTableListModel(jTable1, dataSource,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);

		//
		// 设置列的修改和修改 enter key event
		//
		List<Integer> editColumns = new ArrayList<Integer>();
		for (int i = 1; i < 2; i++) {
			editColumns.add(i);
			jTable1.getColumnModel().getColumn(i).setCellEditor(
					new TableTextFieldEditor(new JTextField(), event));
		}
		jTableListModelAdapter.setEditableColumn(editColumns);

		btnAddEvent.setVisible(!isParentTask);
		btnAddDBToTxtRegion.setVisible(!isParentTask);
		btnAddTxtToDBRegion.setVisible(!isParentTask);
		btnAddSubTask.setVisible(isParentTask);
	}

	/**
	 * cellEditor 回车事件
	 */
	private TableTextFieldEditorEvent	event				= new TableTextFieldEditorEvent() {
																public Object saveObject(
																		Object obj) {
																	return dataExportAction
																			.saveTxtDBTaskDetail(
																					new Request(
																							CommonVars
																									.getCurrUser()),
																					(TxtDBTaskDetail) obj);
																}
															};
	private JButton						btnAddTxtToDBRegion	= null;
	private JButton						btnAddSubTask		= null;

	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jLabel.setText("");
			jLabel
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel2.setText("文本导入导出任务设置");
			jLabel2
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 20));
			jLabel2.setBackground(java.awt.Color.white);
			jLabel2.setForeground(new java.awt.Color(251, 131, 15));
			jPanel.setBackground(java.awt.Color.white);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(jLabel, java.awt.BorderLayout.WEST);
			jPanel.add(jLabel1, java.awt.BorderLayout.EAST);
			jPanel.add(jLabel2, java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(8);
			jSplitPane.setDividerLocation(250);
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setBottomComponent(getJPanel2());
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
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
			btnAdd.setText("新增任务");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgTxtTaskEdit dg = new DgTxtTaskEdit();
					dg.setTableModel(tableModel);
					dg.setAdd(true);
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
			btnEdit.setText("修改任务");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgTxtTask.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					TxtDBTask txtDBTask = (TxtDBTask) tableModel
							.getCurrentRow();
					if (dataExportAction.isExecute(new Request(CommonVars
							.getCurrUser()), txtDBTask)) {
						JOptionPane.showMessageDialog(DgTxtTask.this,
								"任务已经启动，请等待该任务执行完成！", "确认", 2);
						return;
					}
					DgTxtTaskEdit dg = new DgTxtTaskEdit();
					dg.setTableModel(tableModel);
					dg.setAdd(false);
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
			btnDelete.setText("删除任务");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgTxtTask.this,
								"请选择你要删除任务资料", "确认", 2);
						return;
					}
					TxtDBTask txtDBTask = (TxtDBTask) tableModel
							.getCurrentRow();
					if (dataExportAction.isExecute(new Request(CommonVars
							.getCurrUser()), txtDBTask)) {
						JOptionPane.showMessageDialog(DgTxtTask.this,
								"任务已经启动，请等待该任务执行完成！", "确认", 2);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgTxtTask.this,
							"是否要删除该任务？\n请注意：其下的任务将一并删除！", "确认",
							JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						dataExportAction.deleteTxtDBTask(new Request(CommonVars
								.getCurrUser()), txtDBTask);
						tableModel.deleteRow(txtDBTask);
						initTableDetail(new ArrayList(), false);
					}
				}
			});

		}
		return btnDelete;
	}

	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModel == null
									|| tableModel.getCurrentRow() == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							if (!lsm.isSelectionEmpty()) {
								txtDBTask = (TxtDBTask) tableModel
										.getCurrentRow();
								showTaskDetail(txtDBTask);
							}

						}
					});
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
			jScrollPane
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jToolBar1
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnAddSubTask());
			jToolBar1.add(getBtnAddEvent());
			jToolBar1.add(getBtnAddDBToTxtRegion());
			jToolBar1.add(getBtnAddTxtToDBRegion());
			jToolBar1.add(getBtnRemoveRegion());
		}
		return jToolBar1;
	}

	/**
	 * 
	 * This method initializes btnAddDBToTxtRegion
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAddDBToTxtRegion() {
		if (btnAddDBToTxtRegion == null) {
			btnAddDBToTxtRegion = new JButton();
			btnAddDBToTxtRegion.setText("新增DB导出到文本的域对应");
			btnAddDBToTxtRegion
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgTxtTask.this,
										"请选择任务资料", "确认", 2);
								return;
							}
							TxtDBTask txtDBTask = (TxtDBTask) tableModel
									.getCurrentRow();
							List list = JDBCCommonQuery.getInstance()
									.getDBToTxtRegion(txtDBTask);
							if (list == null) {
								return;
							}
							for (int i = 0; i < list.size(); i++) {
								TxtDBTaskDetail txtDBTaskDetail = (TxtDBTaskDetail) list
										.get(i);
								Integer maxNum = dataExportAction
										.findTxtDBTaskDetailMaxSeqNum(
												new Request(CommonVars
														.getCurrUser()),
												txtDBTask);
								txtDBTaskDetail.setSeqNum(++maxNum);
								txtDBTaskDetail.setTxtDBTask(txtDBTask);
								txtDBTaskDetail.setCompany(CommonVars
										.getCurrUser().getCompany());
								txtDBTaskDetail = dataExportAction
										.saveTxtDBTaskDetail(new Request(
												CommonVars.getCurrUser()),
												txtDBTaskDetail);
								tableModel1.addRow(txtDBTaskDetail);
							}
						}
					});

		}
		return btnAddDBToTxtRegion;
	}

	/**
	 * 
	 * This method initializes jTable1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			jTable1.setIntercellSpacing(new java.awt.Dimension(0, 0));
		}
		return jTable1;
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
			jScrollPane1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane1;
	}

	/**
	 * @return Returns the tableModel1.
	 */
	public JTableListModel getTableModel1() {
		return tableModel1;
	}

	/**
	 * @param tableModel1
	 *            The tableModel1 to set.
	 */
	public void setTableModel1(JTableListModel tableModel1) {
		this.tableModel1 = tableModel1;
	}

	/**
	 * This method initializes btnRemoveRegion
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRemoveRegion() {
		if (btnRemoveRegion == null) {
			btnRemoveRegion = new JButton();
			btnRemoveRegion.setText("移出明细");
			btnRemoveRegion
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel1.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgTxtTask.this,
										"请选择要移出明细的资料", "确认", 2);
								return;
							}
							TxtDBTaskDetail txtDBTaskDetail = (TxtDBTaskDetail) tableModel1
									.getCurrentRow();
							dataExportAction.deleteTxtDBTaskDetail(new Request(
									CommonVars.getCurrUser()), txtDBTaskDetail);
							tableModel1.deleteRow(txtDBTaskDetail);
						}
					});
		}
		return btnRemoveRegion;
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
					DgTxtTask.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes btnAddEvent
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddEvent() {
		if (btnAddEvent == null) {
			btnAddEvent = new JButton();
			btnAddEvent.setText("新增事件");
			btnAddEvent.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgTxtTask.this,
								"请选择事件资料", "确认", 2);
						return;
					}
					TxtDBTask txtDBTask = (TxtDBTask) tableModel
							.getCurrentRow();
					List list = JDBCCommonQuery.getInstance()
							.getJDBCSqlEvent();
					if (list == null || list.isEmpty()) {
						return;
					}
					for (int i = 0; i < list.size(); i++) {
						JDBCSqlEvent jdbcSqlEvent = (JDBCSqlEvent) list.get(i);
						TxtDBTaskDetail txtDBTaskDetail = new TxtDBTaskDetail();
						Integer maxNum = dataExportAction
								.findTxtDBTaskDetailMaxSeqNum(new Request(
										CommonVars.getCurrUser()), txtDBTask);
						txtDBTaskDetail.setSeqNum(++maxNum);
						txtDBTaskDetail
								.setTaskType(TxtDBTaskDetail.JDBC_SQL_EVENT_TYPE);
						txtDBTaskDetail.setTxtDBTask(txtDBTask);
						txtDBTaskDetail.setJdbcSqlEvent(jdbcSqlEvent);
						txtDBTaskDetail.setCompany(CommonVars.getCurrUser()
								.getCompany());
						txtDBTaskDetail = dataExportAction.saveTxtDBTaskDetail(
								new Request(CommonVars.getCurrUser()),
								txtDBTaskDetail);
						tableModel1.addRow(txtDBTaskDetail);
					}
				}
			});
		}
		return btnAddEvent;
	}

	/** init ui components data */
	private void initUIComponents() {
		List list = dataExportAction.findTxtDBTask(new Request(CommonVars
				.getCurrUser()));
		initTableDetail(new Vector(), false);
		initTable(list);
		if (list.size() > 0) {
			showTaskDetail((TxtDBTask) list.get(0));
		}
	}

	private void showTaskDetail(TxtDBTask txtDBTask) {
		List selList = dataExportAction.findTxtDBTaskDetail(new Request(
				CommonVars.getCurrUser()), txtDBTask);
		initTableDetail(selList, (txtDBTask.getIsParentTask() == null ? false
				: txtDBTask.getIsParentTask()));
	}

	/**
	 * This method initializes btnAddTxtToDBRegion
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddTxtToDBRegion() {
		if (btnAddTxtToDBRegion == null) {
			btnAddTxtToDBRegion = new JButton();
			btnAddTxtToDBRegion.setText("新增文本导出到DB的域对应");
			btnAddTxtToDBRegion
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgTxtTask.this,
										"请选择任务资料", "确认", 2);
								return;
							}
							TxtDBTask txtDBTask = (TxtDBTask) tableModel
									.getCurrentRow();
							List list = JDBCCommonQuery.getInstance()
									.getTxtToDBRegion(txtDBTask);
							if (list == null) {
								return;
							}
							for (int i = 0; i < list.size(); i++) {
								TxtDBTaskDetail txtDBTaskDetail = (TxtDBTaskDetail) list
										.get(i);
								Integer maxNum = dataExportAction
										.findTxtDBTaskDetailMaxSeqNum(
												new Request(CommonVars
														.getCurrUser()),
												txtDBTask);
								txtDBTaskDetail.setSeqNum(++maxNum);
								txtDBTaskDetail.setTxtDBTask(txtDBTask);
								txtDBTaskDetail.setCompany(CommonVars
										.getCurrUser().getCompany());
								txtDBTaskDetail = dataExportAction
										.saveTxtDBTaskDetail(new Request(
												CommonVars.getCurrUser()),
												txtDBTaskDetail);
								tableModel1.addRow(txtDBTaskDetail);
							}
						}
					});
		}
		return btnAddTxtToDBRegion;
	}

	/**
	 * This method initializes btnSubTask
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddSubTask() {
		if (btnAddSubTask == null) {
			btnAddSubTask = new JButton();
			btnAddSubTask.setText("新增子任务");
			btnAddSubTask
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgTxtTask.this,
										"请选择任务资料", "确认", 2);
								return;
							}
							TxtDBTask txtDBTask = (TxtDBTask) tableModel
									.getCurrentRow();
							List list = JDBCCommonQuery.getInstance()
									.getSubTask(txtDBTask);
							if (list == null) {
								return;
							}
							for (int i = 0; i < list.size(); i++) {
								TxtDBTaskDetail txtDBTaskDetail = (TxtDBTaskDetail) list
										.get(i);
								Integer maxNum = dataExportAction
										.findTxtDBTaskDetailMaxSeqNum(
												new Request(CommonVars
														.getCurrUser()),
												txtDBTask);
								txtDBTaskDetail.setSeqNum(++maxNum);
								txtDBTaskDetail.setTxtDBTask(txtDBTask);
								txtDBTaskDetail.setCompany(CommonVars
										.getCurrUser().getCompany());
								txtDBTaskDetail = dataExportAction
										.saveTxtDBTaskDetail(new Request(
												CommonVars.getCurrUser()),
												txtDBTaskDetail);
								tableModel1.addRow(txtDBTaskDetail);
							}
						}
					});
		}
		return btnAddSubTask;
	}
}
