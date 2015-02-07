package com.bestway.common.client.aptitudereport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.aptitudereport.action.AptitudeReportAction;
import com.bestway.common.aptitudereport.entity.FiltCondition;
import com.bestway.common.aptitudereport.entity.GroupValue;
import com.bestway.common.aptitudereport.entity.SelectCondition;
import com.bestway.common.aptitudereport.entity.StatTypeValue;
import com.bestway.common.aptitudereport.entity.TempReport;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmReportStatQuery extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnSearch = null;

	private JButton btnEdit = null;

	private JButton btnDel = null;

	private JButton btnShow = null;

	private JButton btnClose = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JTableListModel tableModel = null;

	private AptitudeReportAction aptitudeReportAction = null;

	private List<TempReport> reportList = null;

	private String statTypeColumn = "";

	private FmReportStatResultShow fmReportStatResultShow;

	/**
	 * This is the default constructor
	 */
	public FmReportStatQuery() {
		super();
		aptitudeReportAction = (AptitudeReportAction) CommonVars
				.getApplicationContext().getBean("aptitudeReportAction");
		initialize();
		fillList();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(733, 541);
		this.setTitle("统计报表");
		this.setPreferredSize(new java.awt.Dimension(734, 552));
		this.setContentPane(getJContentPane());
		initTable(new Vector());
	}

	private void fillList() {
		List dataSource = aptitudeReportAction.findSelectConditionByState(
				new Request(CommonVars.getCurrUser()), new Integer(0));
		if (dataSource != null && !dataSource.isEmpty()) {
			initTable(dataSource);
		} else {
			initTable(new Vector());
		}

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
			jContentPane.setPreferredSize(new java.awt.Dimension(724, 517));
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setPreferredSize(new java.awt.Dimension(208, 45));
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new java.awt.Dimension(208, 40));
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDel());
			jToolBar.add(getBtnShow());
			jToolBar.add(getBtnClose());
			// jToolBar.add(getBtnSearch());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgReportStatCondition dgReportStatCondition = new DgReportStatCondition();
					dgReportStatCondition.setTableModel(tableModel);
					dgReportStatCondition.setAdd(true);
					dgReportStatCondition.setDataState(DataState.ADD);
					dgReportStatCondition.setVisible(true);

				}
			});

		}
		return btnAdd;
	}

	private JTableListModel initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("报表名称", "reportName", 200));
						list.add(addColumn("数据来源", "aimObject", 130));
						list.add(addColumn("统计栏位", "statColumn", 130));
						list.add(addColumn("分组栏位", "groupingColumnValue", 140));
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
						if (value
								.equals("com.bestway.bcus.cas.bill.entity.BillDetailFixture")) {
							returnValue = "设备单据";
						} else if (value
								.equals("com.bestway.bcus.cas.bill.entity.BillDetailHalfProduct")) {
							returnValue = "半成品单据";
						} else if (value
								.equals("com.bestway.bcus.cas.bill.entity.BillDetailMateriel")) {
							returnValue = "料件单据";
						} else if (value
								.equals("com.bestway.bcus.cas.bill.entity.BillDetailProduct")) {
							returnValue = "成品单据";
						} else if (value
								.equals("com.bestway.bcus.cas.bill.entity.BillDetailRemainProduct")) {
							returnValue = "残次品单据";
						} else if (value
								.equals("com.bestway.bcus.cas.bill.entity.BillDetailLeftoverMateriel")) {
							returnValue = "边角料单据";
						} else if (value
								.equals("com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo")) {
							returnValue = "电子手册报关单";
						} else if (value
								.equals("com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo")) {
							returnValue = "纸质手册报关单";
						} else if (value
								.equals("com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo")) {
							returnValue = "电子帐册报关单";
						} else if (value
								.equals("com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo")) {
							returnValue = "BCUS报关清单";
						} else if (value
								.equals("com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo")) {
							returnValue = "DZSC报关清单";
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
						if (value.equals("customNum")) {
							returnValue = "对应报关数量";
						} else if (value.equals("hsAmount")) {
							returnValue = "折算海关数量";
						} else if (value.equals("dollarTotalPrice")) {
							returnValue = "美元总价";
						} else if (value.equals("hsPrice")) {
							returnValue = "海关单价";
						} else if (value.equals("money")) {
							returnValue = "金额";
						} else if (value.equals("noCustomNum")) {
							returnValue = "末对应报关数量";
						} else if (value.equals("ptAmount")) {
							returnValue = "数量";
						} else if (value.equals("ptPrice")) {
							returnValue = "单价";
						} else if (value.equals("unitConvert")) {
							returnValue = "折算报关单位比率";
						} else if (value.equals("commAmount")) {
							returnValue = "商品数量";
						} else if (value.equals("commTotalPrice")) {
							returnValue = "商品总价";
						} else if (value.equals("commUnitPrice")) {
							returnValue = "商品单价";
						} else if (value.equals("firstAmount")) {
							returnValue = "第一法定数量";
						} else if (value.equals("secondAmount")) {
							returnValue = "第二法定数量";
						} else if (value.equals("unitWeight")) {
							returnValue = "单位重量";
						} else if (value.equals("declaredAmount")) {
							returnValue = "申报数量";
						} else if (value.equals("grossWeight")) {
							returnValue = "毛重";
						} else if (value.equals("legalAmount")) {
							returnValue = "法定数量";
						} else if (value.equals("netWeight")) {
							returnValue = "净重";
						} else if (value.equals("price")) {
							returnValue = "件数";
						} else if (value.equals("secondLegalAmount")) {
							returnValue = "第二法定数量";
						} else if (value.equals("totalNetWeight")) {
							returnValue = "总重量";
						} else if (value.equals("totalPrice")) {
							returnValue = "总金额";
						}
						return returnValue;
					}
				});

		return tableModel;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
		}
		return btnSearch;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmReportStatQuery.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					editData();

				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDel() {
		if (btnDel == null) {
			btnDel = new JButton();
			btnDel.setText("删除");
			btnDel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmReportStatQuery.this,
								"请选择你要删除的资料", "确认", 2);
						return;
					}
					SelectCondition selectCondition = (SelectCondition) tableModel
							.getCurrentRow();
					if (JOptionPane.showConfirmDialog(FmReportStatQuery.this,
							"确定要删除此记录吗?\n注意：其表体将一并被删除", "确认", 0) == 0) {
						aptitudeReportAction.deleteSelectCondition(new Request(
								CommonVars.getCurrUser()), selectCondition);
						tableModel.deleteRow(selectCondition);
					}
				}
			});

		}
		return btnDel;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnShow() {
		if (btnShow == null) {
			btnShow = new JButton();
			btnShow.setText("显示报表");
			btnShow.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmReportStatQuery.this,
								"请选择你要显示的资料", "提示", 0);
						return;
					}
					SelectCondition selectCondition = (SelectCondition) tableModel
							.getCurrentRow();

					String className = selectCondition.getAimObject();
					String statColumn = selectCondition.getStatColumn();
					statTypeColumn = getStatTypeColumn(selectCondition,
							statColumn);

					List<GroupValue> groupByList = aptitudeReportAction
							.findGroupValue(new Request(CommonVars
									.getCurrUser()), selectCondition.getId());
					String groupByColumn = aptitudeReportAction.getGroupByList(
							new Request(CommonVars.getCurrUser()), groupByList);
					List<FiltCondition> filtList = aptitudeReportAction
							.findFiltCondition(new Request(CommonVars
									.getCurrUser()), selectCondition.getId(),
									"1");
					List<GroupValue> groupValueList = aptitudeReportAction
							.findGroupValue(new Request(CommonVars
									.getCurrUser()), selectCondition.getId(), 0);
					List<GroupValue> statTypeList = aptitudeReportAction
							.findGroupValue(new Request(CommonVars
									.getCurrUser()), selectCondition.getId(), 1);
					String groupBy = getGroupBy(groupValueList);
					List<Condition> conditions = new ArrayList<Condition>();
					conditions = aptitudeReportAction.getConditions(
							new Request(CommonVars.getCurrUser()), filtList);

					List statConditionList = aptitudeReportAction
							.findFiltCondition(new Request(CommonVars
									.getCurrUser()), selectCondition.getId(),
									"2");
					List<Condition> conditionsToStat = new ArrayList<Condition>();
					conditionsToStat = aptitudeReportAction
							.getConditionsTOStat(new Request(CommonVars
									.getCurrUser()), statConditionList);

					if (groupByList.size() > 0) {
						new find("", className, conditions, groupByColumn,
								conditionsToStat, groupBy, selectCondition,
								groupByList, groupValueList, statTypeList)
								.start();

					}

				}
			});

		}
		return btnShow;
	}

	class find extends Thread {
		String selects = null;

		String className = null;

		List conditions = null;

		String groupByColumn = null;

		List conditionsToStat = null;

		String groupBy = null;

		List<GroupValue> groupByList = null;

		List<GroupValue> groupValueList = null;

		List<GroupValue> statTypeList = null;

		SelectCondition selectCondition = null;

		public find(String selects, String className, List conditions,
				String groupByColumn, List conditionsToStat, String groupBy,
				SelectCondition selectCondition, List<GroupValue> groupByList,
				List<GroupValue> groupValueList, List<GroupValue> statTypeList) {
			this.selects = selects;
			this.className = className;
			this.conditions = conditions;
			this.groupByColumn = groupByColumn;
			this.groupBy = groupBy;
			this.conditionsToStat = conditionsToStat;
			this.groupByList = groupByList;
			this.groupValueList = groupValueList;
			this.statTypeList = statTypeList;
			this.selectCondition = selectCondition;

		}

		public void run() {

			try {
				CommonProgress.showProgressDialog(FmReportStatQuery.this);
				CommonProgress.setMessage("系统正获取数据，请稍后...");

				reportList = aptitudeReportAction.commonCount(new Request(
						CommonVars.getCurrUser()), null, className, conditions,
						groupByColumn, conditionsToStat, groupBy);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmReportStatQuery.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
				if (reportList != null && !reportList.isEmpty()) {
					fmReportStatResultShow = new FmReportStatResultShow(
							selectCondition, reportList, groupByList,
							groupValueList, statTypeList);

					fmReportStatResultShow.setSelectCondition(selectCondition);
					fmReportStatResultShow.setReportList(reportList);
					fmReportStatResultShow.setGroupValueList(groupByList);
					fmReportStatResultShow.setGroupValueList(groupValueList);
					fmReportStatResultShow.setStatTypeList(statTypeList);
					fmReportStatResultShow.setAdd(true);
					fmReportStatResultShow.setVisible(true);
					fmReportStatResultShow.setMainFrame(FmReportStatQuery.this
							.getMainFrame());
					ShowFormControl.showForm(fmReportStatResultShow,
							((FmMain) FmReportStatQuery.this.getMainFrame())
									.getDeskPanel(), FmReportStatQuery.this
									.getMenu(),null);
				} else {
					JOptionPane.showMessageDialog(FmReportStatQuery.this,
							"没有查到符合条件的记录！", "提示！", 2);
				}
			}

		}
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmReportStatQuery.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setPreferredSize(new java.awt.Dimension(710, 450));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "请选择报表",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setPreferredSize(new java.awt.Dimension(710, 410));
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
			// jTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
			// jTable.setPreferredSize(new java.awt.Dimension(700,410));
		}
		return jTable;
	}

	private void editData() {
		DgReportStatCondition dgReportStatCondition = new DgReportStatCondition();
		dgReportStatCondition.setDataState(DataState.EDIT);
		dgReportStatCondition.setTableModel(tableModel);
		dgReportStatCondition.setVisible(true);

	}

	private boolean validateData() {

		if (reportList.size() <= 0) {
			JOptionPane.showMessageDialog(this, "没有查询到相关记录！请检查和更改统计查询条件！",
					"提示！", 0);
			return true;
		}

		return false;
	}

	private String getStatTypeColumn(SelectCondition selectCondition,
			String statColumn) {
		String statTypeColumn = "";
		String sign = ",";
		List<StatTypeValue> statTypeValueList = aptitudeReportAction
				.findStatTypeValue(new Request(CommonVars.getCurrUser()),
						selectCondition.getId());
		for (int i = 0; i < statTypeValueList.size(); i++) {
			statTypeColumn += sign + statTypeValueList.get(i).getCode() + "("
					+ statColumn + ") ";
		}
		return statTypeColumn;
	}

	private String getGroupBy(List<GroupValue> groupValueList) {
		String groupBy = "";
		String sign = ",";
		for (int i = 0; i < groupValueList.size(); i++) {
			if (i == groupValueList.size() - 1) {
				sign = "";
			}
			groupBy += groupValueList.get(i).getCode() + sign;
		}

		return groupBy;

	}

}
