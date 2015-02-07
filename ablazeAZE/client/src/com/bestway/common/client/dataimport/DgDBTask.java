/*
 * Created on 2004-10-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.cas.entity.BillFixingBase;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableTextFieldEditor;
import com.bestway.bcus.client.common.TableTextFieldEditorEvent;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBDataExecuSql;
import com.bestway.bcus.dataimport.entity.DBTaskEx;
import com.bestway.bcus.dataimport.entity.DBTaskSel;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDBTask extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private DataImportAction dataImportAction = null;

	private JPanel jPanel = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JToolBar jToolBar1 = null;

	private JButton jButton3 = null;

	private JTable jTable1 = null;

	private JScrollPane jScrollPane1 = null;

	private DBTaskEx dbTaskEx = null; // 任务主表

	private DBTaskSel dbTaskSel = null; // 任务明细

	private JTableListModel tableModel = null;

	private String importType = null;

	private JTableListModel tableModel1 = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;

	private JButton jButton6 = null;

	/**
	 * This is the default constructor
	 */
	public DgDBTask() {
		super();
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		DgDBTask.this.setImportType("");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("DB导入任务设置");
		this.setSize(748, 494);
		this.setContentPane(getJContentPane());
		initData();
	}

	private void initData() {
		List list = dataImportAction.findDBTaskEx(new Request(CommonVars
				.getCurrUser()));
		if (list != null && !list.isEmpty()) {
			initTable(list);
			dbTaskEx = (DBTaskEx) list.get(0);
			List dbTaskSelList = dataImportAction.findDBTaskSel(new Request(
					CommonVars.getCurrUser()), dbTaskEx);
			if (dbTaskSelList != null && !dbTaskSelList.isEmpty()) {
				initTableDetail(dbTaskSelList,
						(dbTaskEx.getIsParentTask() == null ? false : dbTaskEx
								.getIsParentTask()));
			} else {
				initTableDetail(new Vector(),
						(dbTaskEx.getIsParentTask() == null ? false : dbTaskEx
								.getIsParentTask()));
			}
		} else {
			initTable(new Vector());
			initTableDetail(new Vector(), false);
		}
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
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("任务名称", "taskname", 250));
						list.add(addColumn("运行方式", "excutekind", 80));
						list.add(addColumn("运行日期", "excuteday", 80));
						list.add(addColumn("父任务", "isParentTask", 100));
						list.add(addColumn("运行时间", "excutetime", 100));
						list.add(addColumn("导入选项", "inputSelect", 100));
						list.add(addColumn("创建时间", "createdate", 100));
						list.add(addColumn("创建者", "createuser", 80));
						list.add(addColumn("是否生效", "iseffice", 80));
						list.add(addColumn("生效日期", "efficeDate", 80));
						list.add(addColumn("运行状态", "executeState", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
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
						if (value.equals("0")) {
							returnValue = "手动";
						} else if (value.equals("1")) {
							returnValue = "每日";
						} else if (value.equals("2")) {
							returnValue = "每周";
							DgDBTask.this.setImportType("2");
						} else if (value.equals("3")) {
							returnValue = "每月";
							DgDBTask.this.setImportType("3");
						} else if (value.equals("4")) {
							returnValue = "间隔";
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
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
						if (String.valueOf(value).trim().equals("")
								|| DgDBTask.this.getImportType().equals("")) {
							return "";
						}
						if (DgDBTask.this.getImportType().equals("2")) {
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
						} else if (DgDBTask.this.getImportType().equals("3")) {
							returnValue = value + "日";
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					JCheckBox checkBox = new JCheckBox();

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						if (value == null) {
							checkBox.setSelected(false);
						} else if (value.equals("")) {
							checkBox.setSelected(false);
						} else if (Boolean.valueOf(value.toString()) instanceof Boolean) {
							checkBox.setSelected(Boolean.parseBoolean(value
									.toString()));
						}
						checkBox.setHorizontalAlignment(JLabel.CENTER);
						checkBox.setBackground(table.getBackground());
						if (isSelected) {
							checkBox.setForeground(table
									.getSelectionForeground());
							checkBox.setBackground(table
									.getSelectionBackground());
						} else {
							checkBox.setForeground(table.getForeground());
							checkBox.setBackground(table.getBackground());
						}
						return checkBox;
					}
				});
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
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
						if (value.equals("0")) {
							returnValue = "不处理重复数据";
						} else if (value.equals("1")) {
							returnValue = "覆盖重复数据";
						} else if (value.equals("2")) {
							returnValue = "更新重复数据";
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(11).setCellRenderer(
				new DefaultTableCellRenderer() {
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
						if (value.equals("0")) {
							returnValue = "未执行";
						} else if (value.equals("1")) {
							returnValue = "正在执行";
						} else if (value.equals("2")) {
							returnValue = "执行结束";
						}
						return returnValue;
					}
				});
	}

	private void initTableDetail(List dataSource, final boolean isParentTask) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
            public List<JTableListColumn> InitColumns() {
            	List<JTableListColumn> list = new Vector<JTableListColumn>();
				if (isParentTask) {
					list.add(addColumn("执行顺序", "seqNum", 80));							
					list.add(addColumn("子任务名称", "subDBTaskEx.taskname",
							250));							
				} else {
					list.add(addColumn("执行顺序", "seqNum", 80));
					list.add(addColumn("执行事件名称", "execuSql.name", 250));
					list.add(addColumn("DB导入域名称",
							"dbFormat.regionName", 250));
					list.add(addColumn("视图名称", "dbFormat.dbView.name",
							100));
					list
							.add(addColumn("字符集转换", "dbFormat.gbflag",
									120));
				}
				// list.add(addColumn("域创建者", "dbFormat.creator", 120));
				return list;
            }
		};
		
		tableModel1 = new JTableListModel(jTable1, dataSource,
				jTableListModelAdapter,ListSelectionModel.SINGLE_SELECTION);

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
		
		
		if (isParentTask) {			
			jButton6.setVisible(false);
			jButton3.setText("新增子任务");
		} else {
			jTable1.getColumnModel().getColumn(5).setCellRenderer(
					new DefaultTableCellRenderer() {
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
							if (String.valueOf(value).trim().equals("")) {
								return "";
							}
							if (value.equals("0")) {
								returnValue = "不转换";
							} else if (value.equals("1")) {
								returnValue = "繁转简";
							} else if (value.equals("2")) {
								returnValue = "简转繁";
							}
							return returnValue;
						}
					});
			jButton6.setVisible(true);
			jButton3.setText("新增明细");
		}
	}

	
	/**
     * cellEditor 回车事件
     */
    private TableTextFieldEditorEvent event = new TableTextFieldEditorEvent() {
                                                public Object saveObject(
                                                        Object obj) {
                                                	return dataImportAction.saveDBTaskSel(new Request(
                            								CommonVars.getCurrUser()), (DBTaskSel)obj);
                                                }
                                            };
                                            
                                            

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
			jLabel2.setText("DB导入任务设置");
			jLabel2
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 20));
			jLabel2.setBackground(java.awt.Color.white);
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
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton5());
		}
		return jToolBar;
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
			jButton.setText("新增任务");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgDBTaskAdd dg = new DgDBTaskAdd();
					dg.setTableModel(tableModel);
					dg.setAdd(true);
					dg.setVisible(true);

				}
			});

		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("修改任务");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDBTask.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					DBTaskEx dbTaskEx = (DBTaskEx) tableModel.getCurrentRow();
					if (dbTaskEx.getExecuteState().equals(Integer.valueOf(1))) {
						JOptionPane.showMessageDialog(DgDBTask.this,
								"任务已经启动，请先停止该任务执行！", "确认", 2);
						return;
					}
					DgDBTaskAdd dg = new DgDBTaskAdd();
					dg.setTableModel(tableModel);
					dg.setAdd(false);
					dg.setVisible(true);

				}
			});

		}
		return jButton1;
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
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("删除任务");
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDBTask.this,
								"请选择你要删除任务资料", "确认", 2);
						return;
					}
					DBTaskEx dbTaskEx = (DBTaskEx) tableModel.getCurrentRow();
					if (dbTaskEx.getExecuteState().equals(Integer.valueOf(1))) {
						JOptionPane.showMessageDialog(DgDBTask.this,
								"任务已经启动，请先停止该任务执行！", "确认", 2);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgDBTask.this,
							"是否要删除该任务？\n请注意：其下的任务将一并删除！", "确认", 0) == 0) {

						dataImportAction.deleteAllDBTaskSel(new Request(
								CommonVars.getCurrUser()), dbTaskEx);
						tableModel1.getList().clear();
						initTableDetail(new Vector(), (dbTaskEx
								.getIsParentTask() == null ? false : dbTaskEx
								.getIsParentTask()));
						dataImportAction.deleteDBTaskEx(new Request(CommonVars
								.getCurrUser()), dbTaskEx);
						tableModel.deleteRow(dbTaskEx);
					}
				}
			});

		}
		return jButton2;
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

					public void valueChanged(ListSelectionEvent arg0) {
						if (tableModel == null) {
							return;
						}
						if (tableModel.getCurrentRow() == null) {
							return;
						}
						dbTaskEx = (DBTaskEx) tableModel.getCurrentRow();
						List selList = dataImportAction
								.findDBTaskSel(new Request(CommonVars
										.getCurrUser()), dbTaskEx);
						if (selList != null && !selList.isEmpty()) {
							initTableDetail(selList, (dbTaskEx
									.getIsParentTask() == null ? false
									: dbTaskEx.getIsParentTask()));
						} else {
							initTableDetail(new Vector(), (dbTaskEx
									.getIsParentTask() == null ? false
									: dbTaskEx.getIsParentTask()));
						}
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
			jToolBar1.add(getJButton6());
			jToolBar1.add(getJButton3());
			jToolBar1.add(getJButton4());
		}
		return jToolBar1;
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("新增明细");
			jButton3.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDBTask.this, "请选择任务资料",
								"确认", 2);
						return;
					}
					DBTaskEx dbTaskEx = (DBTaskEx) tableModel.getCurrentRow();
					DBTaskSel dbTaskSel = null;
					List list = (List) CommonQuery.getInstance().getDBTask(
							false, dbTaskEx);
					if (list == null || list.isEmpty())
						return;
					for (int i = 0; i < list.size(); i++) {
						dbTaskSel = (DBTaskSel) list.get(i);
						Integer seqNum = dataImportAction.getNumToTaskSel(
								new Request(CommonVars.getCurrUser()),
								"DBTaskSel", "seqNum", dbTaskEx);
						dbTaskSel.setSeqNum(seqNum);
						dbTaskSel.setDbtaskEx(dbTaskEx);
						dbTaskSel.setCompany(CommonVars.getCurrUser()
								.getCompany());
						dbTaskSel = dataImportAction.saveDBTaskSel(new Request(
								CommonVars.getCurrUser()), dbTaskSel);
						tableModel1.addRow(dbTaskSel);
					}
				}
			});

		}
		return jButton3;
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
			jTable1.setShowVerticalLines(false);
			jTable1.setShowHorizontalLines(false);
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
	 * @return Returns the type.
	 */
	public String getImportType() {
		return importType;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setImportType(String type) {
		this.importType = type;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("移出明细");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel1.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDBTask.this,
								"请选择要移出明细的资料", "确认", 2);
						return;
					}
					DBTaskSel txtTaskSel = (DBTaskSel) tableModel1
							.getCurrentRow();
					dataImportAction.deleteDBTaskSel(new Request(CommonVars
							.getCurrUser()), txtTaskSel);
					tableModel1.deleteRow(txtTaskSel);
				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("关闭");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDBTask.this.dispose();
				}
			});
		}
		return jButton5;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("新增事件");
			jButton6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDBTask.this, "请选择任务资料",
								"确认", 2);
						return;
					}
					DBTaskEx dbTaskEx = (DBTaskEx) tableModel.getCurrentRow();
					DBDataExecuSql execusql = null;
					List list = (List) CommonQuery.getInstance().getExecuSql();
					if (list == null || list.isEmpty())
						return;
					for (int i = 0; i < list.size(); i++) {
						execusql = (DBDataExecuSql) list.get(i);
						DBTaskSel dbTaskSel = new DBTaskSel();
						Integer seqNum = dataImportAction.getNumToTaskSel(
								new Request(CommonVars.getCurrUser()),
								"DBTaskSel", "seqNum", dbTaskEx);
						dbTaskSel.setSeqNum(seqNum);
						dbTaskSel.setDbtaskEx(dbTaskEx);
						dbTaskSel.setExecuSql(execusql);
						dbTaskSel.setCompany(CommonVars.getCurrUser()
								.getCompany());
						dbTaskSel = dataImportAction.saveDBTaskSel(new Request(
								CommonVars.getCurrUser()), dbTaskSel);
						tableModel1.addRow(dbTaskSel);
					}

				}
			});
		}
		return jButton6;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
