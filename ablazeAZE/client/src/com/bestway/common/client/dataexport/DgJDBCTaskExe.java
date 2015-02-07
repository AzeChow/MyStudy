/*
 * Created on 2004-10-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ExcuteKind;
import com.bestway.common.constant.GbkToBig5Flag;
import com.bestway.common.constant.ImportDataMode;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.JDBCTask;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class DgJDBCTaskExe extends JDialogBase {

	private javax.swing.JPanel	jContentPane		= null;
	private DataExportAction	dataExportAction	= null;
	private JPanel				jPanel				= null;
	private JSplitPane			jSplitPane			= null;
	private JPanel				jPanel1				= null;
	private JPanel				jPanel2				= null;
	private JToolBar			jToolBar			= null;
	private JButton				btnExecuteTask		= null;
	private JTable				jTable				= null;
	private JScrollPane			jScrollPane			= null;
	private JTable				jTable1				= null;
	private JScrollPane			jScrollPane1		= null;
	private JDBCTask			jdbcTask			= null;
	private JTableListModel		tableModel			= null;
	private JTableListModel		tableModel1			= null;
	private JButton				btnClose			= null;

	/**
	 * This is the default constructor
	 */
	public DgJDBCTaskExe() {
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
		this.setTitle("DB导入导出任务执行");
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
						list.add(addColumn("运行方式", "executekind", 80));
						list.add(addColumn("运行日期", "excuteday", 80));
						list.add(addColumn("父任务", "isParentTask", 100));
						list.add(addColumn("运行时间", "excutetime", 100));
						list.add(addColumn("导入方式", "importDataMode", 100));
						list.add(addColumn("运行状态", "isExecute", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new TableCheckBoxRender());

		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);

						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						int executeKind = Integer.parseInt(String
								.valueOf(value));
						if (executeKind == ExcuteKind.MANUAL) {
							returnValue = "手动";
						} else if (executeKind == ExcuteKind.DAY) {
							returnValue = "每日";
						} else if (executeKind == ExcuteKind.WEEK) {
							returnValue = "每周";
						} else if (executeKind == ExcuteKind.MONTH) {
							returnValue = "每月";
						} else if (executeKind == ExcuteKind.INTERVAL) {
							returnValue = "间隔";
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						JDBCTask planTask = (JDBCTask) tableModel
								.getValueAt(row);
						super.setText((value == null) ? "" : castValue1(value,
								planTask));
						return this;
					}

					private String castValue1(Object value, JDBCTask planTask) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (planTask.getExecutekind().intValue() == ExcuteKind.WEEK) {
							if (value.equals("1")) {
								returnValue = "星期天";
							} else if (value.equals("2")) {
								returnValue = "星期一";
							} else if (value.equals("3")) {
								returnValue = "星期二";
							} else if (value.equals("4")) {
								returnValue = "星期三";
							} else if (value.equals("5")) {
								returnValue = "星期四";
							} else if (value.equals("6")) {
								returnValue = "星期五";
							} else if (value.equals("7")) {
								returnValue = "星期六";
							}
						} else if (planTask.getExecutekind().intValue() == ExcuteKind.MONTH) {
							if (value.equals("L")) {
								returnValue = "本月最后一天";
							} else {
								returnValue = value + "日";
							}
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						int importDataMode = Integer.parseInt(String
								.valueOf(value));
						if (importDataMode == ImportDataMode.ADD_MODE) {
							returnValue = "新增导入";
						} else if (importDataMode == ImportDataMode.MODIFY_MODE) {
							returnValue = "更新导入";
						}
						return returnValue;
					}
				});

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
					list.add(addColumn("DB域名称", "jdbcRegion.regionName", 200));
					list.add(addColumn("DB域对应的视图名称",
							"jdbcRegion.srcJDBCView.name", 150));
					list
							.add(addColumn("字符集转换", "jdbcRegion.gbkToBig5Flag",
									100));
				}
				return list;
			}
		};

		tableModel1 = new JTableListModel(jTable1, dataSource,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		if (isParentTask) {

		} else {
			jTable1.getColumnModel().getColumn(5).setCellRenderer(
					new DefaultTableCellRenderer() {
						@Override
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							super.setText((value == null) ? ""
									: castValue1(value));
							return this;
						}

						private String castValue1(Object value) {
							String returnValue = "";

							if (value.equals(GbkToBig5Flag.NO)) {
								returnValue = "不转换";
							} else if (value.equals(GbkToBig5Flag.BIG5_TO_GBK)) {
								returnValue = "繁转简";
							} else if (value.equals(GbkToBig5Flag.GBK_TO_BIG5)) {
								returnValue = "简转繁";
							}
							return returnValue;
						}
					});
		}
	}

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
			jLabel2.setText("DB导入导出任务执行");
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
			jToolBar.add(getBtnExecuteTask());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes btnExecuteTask
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnExecuteTask() {
		if (btnExecuteTask == null) {
			btnExecuteTask = new JButton();
			btnExecuteTask.setText("立即执行任务");
			btnExecuteTask
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgJDBCTaskExe.this, "请选择你要立即执行的任务",
										"确认", 2);
								return;
							}
							JDBCTask jdbcTask = (JDBCTask) tableModel
									.getCurrentRow();
//							if (dataExportAction.isExecute(new Request(
//									CommonVars.getCurrUser()), jdbcTask)) {
//								JOptionPane.showMessageDialog(
//										DgJDBCTaskExe.this,
//										"任务已经启动，请等待该任务执行完成！", "确认", 2);
//								return;
//							}
							ExecJDBCTask thread = new ExecJDBCTask(jdbcTask);
							thread.start();
						}
					});

		}
		return btnExecuteTask;
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
								jdbcTask = (JDBCTask) tableModel
										.getCurrentRow();
								showTaskDetail(jdbcTask);
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
			// jTable1.setShowVerticalLines(false);
			// jTable1.setShowHorizontalLines(false);
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
					DgJDBCTaskExe.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/** init ui components data */
	private void initUIComponents() {
		List list = dataExportAction.findJDBCTask(new Request(CommonVars
				.getCurrUser()));
		initTableDetail(new Vector(), false);
		initTable(list);
		if (list.size() > 0) {
			showTaskDetail((JDBCTask) list.get(0));
		}
	}

	private void showTaskDetail(JDBCTask jdbcTask) {
		List selList = dataExportAction.findJDBCTaskDetail(new Request(
				CommonVars.getCurrUser()), jdbcTask);
		initTableDetail(selList, (jdbcTask.getIsParentTask() == null ? false
				: jdbcTask.getIsParentTask()));
	}

	class ExecJDBCTask extends Thread {
		JDBCTask	jdbcTask	= null;

		public ExecJDBCTask(JDBCTask jdbcTask) {
			this.jdbcTask = jdbcTask;
		}

		@Override
		public void run() {

			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();

			CommonProgress.showProgressDialog(flag, DgJDBCTaskExe.this,
					false, null, 0);
			CommonProgress.setMessage(flag, "正在执行任务, 请稍后...");

			btnExecuteTask.setEnabled(false);
			try {
				this.exec();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} finally {
				btnExecuteTask.setEnabled(true);
				CommonProgress.closeProgressDialog(flag);
			}
		}

		private void exec() {
			long beginTime = System.currentTimeMillis();
			dataExportAction.executeImportExportData(new Request(CommonVars
					.getCurrUser()), jdbcTask);
			JOptionPane.showMessageDialog(DgJDBCTaskExe.this, jdbcTask
					.getTaskname()
					+ " 任务执行完成！ 共用 "
					+ (System.currentTimeMillis() - beginTime)
					+ " 毫秒 ", "确认", 2);
		}

	}

}
