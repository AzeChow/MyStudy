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

import com.bestway.bcus.cas.bill.entity.BillDetail;
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
import com.bestway.common.aptitudereport.entity.ReportField;
import com.bestway.common.aptitudereport.entity.SelectCondition;
import com.bestway.common.aptitudereport.entity.TempReport;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;

public class FmReportQuery extends JInternalFrameBase {

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

	private List<BillDetail> reportBillDetailList = null; // @jve:decl-index=0:

	private List<BaseCustomsDeclarationCommInfo> reportCustomsList = null; // @jve:decl-index=0:

	private List<AtcMergeBeforeComInfo> reportAtcMergeList = null;

	private List<DzscBillListBeforeCommInfo> reportDzscBillList = null;

	private List<ReportField> reportFieldList = null;

	private FmReportQueryResultShow fmReportQueryResultShow;

	/**
	 * This is the default constructor
	 */
	public FmReportQuery() {
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
				new Request(CommonVars.getCurrUser()), new Integer(1));
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
					DgReportQueryCondition dgReportQueryCondition = new DgReportQueryCondition();
					dgReportQueryCondition.setTableModel(tableModel);
					dgReportQueryCondition.setAdd(true);
					dgReportQueryCondition.setDataState(DataState.ADD);
					dgReportQueryCondition.setVisible(true);

				}
			});

		}
		return btnAdd;
	}

	private JTableListModel initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("报表名称", "reportName", 200));
						list.add(addColumn("数据来源", "aimObject", 130));
						return list;
					}

				});
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

		return tableModel;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	// private JButton getBtnSearch() {
	// if (btnSearch == null) {
	// btnSearch = new JButton();
	// btnSearch.setText("查询");
	// }
	// return btnSearch;
	// }
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
						JOptionPane.showMessageDialog(FmReportQuery.this,
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
						JOptionPane.showMessageDialog(FmReportQuery.this,
								"请选择你要删除的资料", "确认", 2);
						return;
					}
					SelectCondition selectCondition = (SelectCondition) tableModel
							.getCurrentRow();
					if (JOptionPane.showConfirmDialog(FmReportQuery.this,
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
						JOptionPane.showMessageDialog(FmReportQuery.this,
								"请选择你要显示的资料", "提示", 0);
						return;
					}
					SelectCondition selectCondition = (SelectCondition) tableModel
							.getCurrentRow();

					String className = selectCondition.getAimObject();
					reportFieldList = new ArrayList();
					reportFieldList = aptitudeReportAction.findReportField(
							new Request(CommonVars.getCurrUser()),
							selectCondition.getId());
					List<FiltCondition> filtList = aptitudeReportAction
							.findFiltCondition(new Request(CommonVars
									.getCurrUser()), selectCondition.getId(),
									"1");

					List<Condition> conditions = new ArrayList<Condition>();
					conditions = aptitudeReportAction.getConditions(
							new Request(CommonVars.getCurrUser()), filtList);

					new find("", className, conditions, selectCondition,
							reportBillDetailList, reportCustomsList,
							reportAtcMergeList, reportDzscBillList,
							reportFieldList).start();

				}
			});

		}
		return btnShow;
	}

	class find extends Thread {
		String selects = null;

		String className = null;

		List conditions = null;

		SelectCondition selectCondition = null;

		List<BillDetail> reportBillDetailList = null;

		List<BaseCustomsDeclarationCommInfo> reportCustomsList = null;

		List<AtcMergeBeforeComInfo> reportAtcMergeList = null;

		List<DzscBillListBeforeCommInfo> reportDzscBillList = null;

		List<ReportField> reportFieldList = null;

		public find(String selects, String className, List conditions,
				SelectCondition selectCondition,
				List<BillDetail> reportBillDetailList,
				List<BaseCustomsDeclarationCommInfo> reportCustomsList,
				List<AtcMergeBeforeComInfo> reportAtcMergeList,
				List<DzscBillListBeforeCommInfo> reportDzscBillList,
				List<ReportField> reportFieldList) {
			this.selects = selects;
			this.className = className;
			this.conditions = conditions;
			this.selectCondition = selectCondition;
			this.reportAtcMergeList = reportAtcMergeList;
			this.reportBillDetailList = reportBillDetailList;
			this.reportCustomsList = reportCustomsList;
			this.reportDzscBillList = reportDzscBillList;
			this.reportFieldList = reportFieldList;

		}

		@Override
		public void run() {

			try {
				CommonProgress.showProgressDialog(FmReportQuery.this);
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				Object obj = Class.forName(className).newInstance();
				if (obj instanceof BillDetail) {
					reportBillDetailList = aptitudeReportAction
							.findReportDetailToBillDetail(new Request(
									CommonVars.getCurrUser()), null, className,
									conditions);
				}
				if (obj instanceof BaseCustomsDeclarationCommInfo) {
					reportCustomsList = aptitudeReportAction
							.findReportDetailToCustoms(new Request(CommonVars
									.getCurrUser()), null, className,
									conditions);
				}
				if (obj instanceof com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo) {
					reportAtcMergeList = aptitudeReportAction
							.findReportDetailToAtcMerge(new Request(CommonVars
									.getCurrUser()), null, className,
									conditions);
				}
				if (obj instanceof com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo) {
					reportDzscBillList = aptitudeReportAction
							.findReportDetailToDzscBill(new Request(CommonVars
									.getCurrUser()), null, className,
									conditions);
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmReportQuery.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
				if (reportBillDetailList != null
						&& !reportBillDetailList.isEmpty()
						|| reportCustomsList != null
						&& !reportCustomsList.isEmpty()
						|| reportDzscBillList != null
						&& !reportDzscBillList.isEmpty()
						|| reportFieldList != null
						&& !reportFieldList.isEmpty()) {
					fmReportQueryResultShow = new FmReportQueryResultShow(
							selectCondition, reportBillDetailList,
							reportCustomsList, reportAtcMergeList,
							reportDzscBillList, reportFieldList);

					fmReportQueryResultShow.setSelectCondition(selectCondition);
					fmReportQueryResultShow
							.setReportBillDetailList(reportBillDetailList);
					fmReportQueryResultShow
							.setReportCustomsList(reportCustomsList);
					fmReportQueryResultShow
							.setReportAtcMergeList(reportAtcMergeList);
					fmReportQueryResultShow.setReportFieldList(reportFieldList);
					fmReportQueryResultShow.setAdd(true);
					fmReportQueryResultShow.setVisible(true);
					fmReportQueryResultShow.setMainFrame(FmReportQuery.this
							.getMainFrame());
					ShowFormControl.showForm(fmReportQueryResultShow,
							((FmMain) FmReportQuery.this.getMainFrame())
									.getDeskPanel(), FmReportQuery.this
									.getMenu(),null);
				} else {
					JOptionPane.showMessageDialog(FmReportQuery.this,
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
					FmReportQuery.this.dispose();
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

		}
		return jTable;
	}

	private void editData() {
		DgReportQueryCondition dgReportQueryCondition = new DgReportQueryCondition();
		dgReportQueryCondition.setDataState(DataState.EDIT);
		dgReportQueryCondition.setTableModel(tableModel);
		dgReportQueryCondition.setVisible(true);

	}

	private boolean validateData() {

		if (reportList.size() <= 0) {
			JOptionPane.showMessageDialog(this, "没有查询到相关记录！请检查和更改统计查询条件！",
					"提示！", 0);
			return true;
		}

		return false;
	}

}
