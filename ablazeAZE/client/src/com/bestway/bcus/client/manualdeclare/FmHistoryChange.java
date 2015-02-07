/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmHistoryChange extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton7 = null;

	private ManualDeclareAction manualDecleareAction = null;

	private JTableListModel tableModel = null;  //经营范围
	private JTableListModel tableModel1 = null; //归并关系
	private JTableListModel tableModel2 = null; //电子帐册
	private JTableListModel tableModel3 = null;	//分册帐册
	private JTableListModel tableModel4 = null;	//分册

	private boolean isChange = false;
	private boolean isChange1 = false;
	private boolean isChange2 = false;
	private boolean isChange3 = false;
	private boolean isChange4 = false;
	
	private JButton jButton = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;
	private JScrollPane jScrollPane2 = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;	
	private JScrollPane jScrollPane3 = null;
	
	private JTable jTable = null;
	private JTable jTable1 = null;
	private JTable jTable2 = null;
	private JTable jTable3 = null;
	private JTable jTable4 = null;
	private JScrollPane jScrollPane4 = null;
	private EmsHeadH2k emsHeadH2k = null;
	private JLabel jLabel = null;
	/**
	 * This is the default constructor
	 */
	public FmHistoryChange() {
		super();
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(650, 383);
		this.setTitle("电子帐册申报历史变更记录查询");
		this.setContentPane(getJContentPane());

		List dataSource = null;
		//经营范围
		dataSource = manualDecleareAction.findEmsEdiTrHeadAll(new Request(
				CommonVars.getCurrUser()));
		if (dataSource.size()>0){
			initTable(dataSource);
		} else {
			initTable(new Vector());
		}
		//归并关系
		dataSource = manualDecleareAction.findEmsEdiMergerHeadAll(new Request(CommonVars.getCurrUser()));
		if (dataSource.size()>0){
		    initTable1(dataSource);
		} else {
			initTable1(new Vector());
		}
		//电子帐册
		dataSource = manualDecleareAction.findEmsHeadH2kAll(new Request(CommonVars.getCurrUser()));
		if (dataSource.size()>0){
		    initTable2(dataSource);
		} else {
			initTable2(new Vector());
		}
		//电子分册
		if (dataSource.size()>0){
		    initTable3(dataSource);
		    emsHeadH2k = (EmsHeadH2k) dataSource.get(0);
			String emsNo = emsHeadH2k.getEmsNo();
			if (emsHeadH2k.getEmsNo() == null){
			   	  emsNo = "";
			}
			List list = manualDecleareAction.findEmsHeadH2kFasAll(new Request(CommonVars.getCurrUser()),emsNo);
			if (list.size()>0){
			   	  initTable4(list); //分册
			} else {
			   	  initTable4(new Vector());
			}
		} else {
			initTable3(new Vector());
			initTable4(new Vector());
		}
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("帐册编号", "emsNo", 100));
						list.add(addColumn("申报类型", "declareType", 80));
						list.add(addColumn("审批状态", "declareState", 80));
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("变更次数", "modifyTimes", 60));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 120));
						list.add(addColumn("加工单位代码", "ownerCode", 100));
						list.add(addColumn("加工单位名称", "ownerName", 120));
						list.add(addColumn("申报日期", "declareDate", 80));
						list.add(addColumn("批准日期", "newApprDate", 80));
						list.add(addColumn("年加工能力", "machinAbility", 80));
						list.add(addColumn("外经贸部门", "declareDep.name", 100));
						list.add(addColumn("主管海关", "masterCustoms.name", 100));
						list.add(addColumn("备注", "note", 100));
						list.add(addColumn("帐册类型", "emsType", 80));
						return list;
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
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							returnValue = "申请备案";
							FmHistoryChange.this.setChange(false);
						} else if (value.equals("2")) {
							returnValue = "申请变更";
							FmHistoryChange.this.setChange(true);
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							if (FmHistoryChange.this.isChange)
								returnValue = "申请变更";
							else
								returnValue = "申请备案";
						} else if (value.equals("2"))
							returnValue = "等待审批";
						else if (value.equals("3"))
							returnValue = "正在执行";
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}
					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(ModifyMarkState.ADDED))
							returnValue = "新增";
					    else if (value.equals(ModifyMarkState.DELETED))
							returnValue = "已删除";
						else if (value.equals(ModifyMarkState.MODIFIED))
							returnValue = "已修改";
						else if (value.equals(ModifyMarkState.UNCHANGE))
							returnValue = "未修改";
						return returnValue;
					}
				});
	}
	private void initTable1(final List list) {
		tableModel1 = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("帐册编号", "emsNo", 100));
						list.add(addColumn("批文帐册号", "sancEmsNo", 100));
						list.add(addColumn("申报类型", "declareType", 80));
						list.add(addColumn("审批状态", "declareState", 80));
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("变更次数", "modifyTimes", 60));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 120));
						list.add(addColumn("加工单位代码", "machCode", 100));
						list.add(addColumn("加工单位名称", "machName", 120));
						list.add(addColumn("电话", "tel", 80));
						list.add(addColumn("地址", "address", 80));
						list.add(addColumn("申报日期", "declareDate", 80));
						list.add(addColumn("批准日期", "newApprDate", 80));
						list.add(addColumn("年加工能力", "machinAbility", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		jTable1.getColumnModel().getColumn(4).setCellRenderer(
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
						if (value.equals("1")) {
							returnValue = "申请备案";
							FmHistoryChange.this.setChange1(false);
						} else if (value.equals("2")) {
							returnValue = "申请变更";
							FmHistoryChange.this.setChange1(true);
						}
						return returnValue;
					}
				});
		jTable1.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							if (FmHistoryChange.this.isChange1)
								returnValue = "申请变更";
							else
								returnValue = "申请备案";
						} else if (value.equals("2"))
							returnValue = "等待审批";
						else if (value.equals("3"))
							returnValue = "正在执行";
						return returnValue;
					}
				});
		jTable1.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}
					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(ModifyMarkState.ADDED))
							returnValue = "新增";
					    else if (value.equals(ModifyMarkState.DELETED))
							returnValue = "已删除";
						else if (value.equals(ModifyMarkState.MODIFIED))
							returnValue = "已修改";
						else if (value.equals(ModifyMarkState.UNCHANGE))
							returnValue = "未修改";
						return returnValue;
					}
				});
	}
	private void initTable2(final List list) {
		tableModel2 = new JTableListModel(jTable2, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("帐册编号", "emsNo", 100));
						list.add(addColumn("批文帐册号", "sancEmsNo", 100));
						list.add(addColumn("申报类型", "declareType", 80));
						list.add(addColumn("审批状态", "declareState", 80));
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("变更次数", "modifyTimes", 60));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 120));
						list.add(addColumn("加工单位代码", "machCode", 100));
						list.add(addColumn("加工单位名称", "machName", 120));
						list.add(addColumn("电话", "tel", 80));
						list.add(addColumn("地址", "address", 100));
						list.add(addColumn("申报日期", "declareDate", 80));
						list.add(addColumn("批准日期", "newApprDate", 80));
						list.add(addColumn("年加工能力", "machinAbility", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		jTable2.getColumnModel().getColumn(4).setCellRenderer(
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
						if (value.equals("1")) {
							returnValue = "申请备案";
							FmHistoryChange.this.setChange2(false);
						} else if (value.equals("2")) {
							returnValue = "申请变更";
							FmHistoryChange.this.setChange2(true);
						} else if (value.equals("3")){
							returnValue = "预变更";
							FmHistoryChange.this.setChange2(true);
						}
						return returnValue;
					}
				});
		jTable2.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							if (FmHistoryChange.this.isChange2)
								returnValue = "申请变更";
							else
								returnValue = "申请备案";
						} else if (value.equals("2"))
							returnValue = "等待审批";
						else if (value.equals("3"))
							returnValue = "正在执行";
						return returnValue;
					}
				});
		jTable2.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}
					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(ModifyMarkState.ADDED))
							returnValue = "新增";
					    else if (value.equals(ModifyMarkState.DELETED))
							returnValue = "已删除";
						else if (value.equals(ModifyMarkState.MODIFIED))
							returnValue = "已修改";
						else if (value.equals(ModifyMarkState.UNCHANGE))
							returnValue = "未修改";
						return returnValue;
					}
				});

	}
	private void initTable4(final List list) {
		tableModel4 = new JTableListModel(jTable4, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("电子帐册编号", "emsHeadH2kNo", 100));
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("分册号", "emsNo", 100));
						list.add(addColumn("变更次数", "modifyTimes", 60));
						list.add(addColumn("申报类型", "declareType", 80));
						list.add(addColumn("审批状态", "declareState", 80));
						list.add(addColumn("修改标志", "modifyMark", 60));						
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 120));
						list.add(addColumn("申报单位名称", "declareName", 120));
						list.add(addColumn("申报日期", "declareDate", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		jTable4.getColumnModel().getColumn(5).setCellRenderer(
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
						if (value.equals("1")) {
							returnValue = "申请备案";
							FmHistoryChange.this.setChange4(false);
						} else if (value.equals("2")) {
							returnValue = "申请变更";
							FmHistoryChange.this.setChange4(true);
						}
						return returnValue;
					}
				});
		jTable4.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							if (FmHistoryChange.this.isChange4)
								returnValue = "申请变更";
							else
								returnValue = "申请备案";
						} else if (value.equals("2"))
							returnValue = "等待审批";
						else if (value.equals("3"))
							returnValue = "正在执行";
						return returnValue;
					}
				});
		jTable4.getColumnModel().getColumn(7).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}
					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(ModifyMarkState.ADDED))
							returnValue = "新增";
					    else if (value.equals(ModifyMarkState.DELETED))
							returnValue = "已删除";
						else if (value.equals(ModifyMarkState.MODIFIED))
							returnValue = "已修改";
						else if (value.equals(ModifyMarkState.UNCHANGE))
							returnValue = "未修改";
						return returnValue;
					}
				});

	}
	private void initTable3(final List list) {
		tableModel3 = new JTableListModel(jTable3, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("帐册编号", "emsNo", 100));
						list.add(addColumn("批文帐册号", "sancEmsNo", 100));
						list.add(addColumn("申报类型", "declareType", 80));
						list.add(addColumn("审批状态", "declareState", 80));
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("变更次数", "modifyTimes", 60));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 120));
						list.add(addColumn("加工单位代码", "machCode", 100));
						list.add(addColumn("加工单位名称", "machName", 120));
						list.add(addColumn("电话", "tel", 80));
						list.add(addColumn("地址", "address", 100));
						list.add(addColumn("申报日期", "declareDate", 80));
						list.add(addColumn("批准日期", "newApprDate", 80));
						list.add(addColumn("年加工能力", "machinAbility", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		jTable3.getColumnModel().getColumn(4).setCellRenderer(
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
						if (value.equals("1")) {
							returnValue = "申请备案";
							FmHistoryChange.this.setChange3(false);
						} else if (value.equals("2")) {
							returnValue = "申请变更";
							FmHistoryChange.this.setChange3(true);
						} else if (value.equals("3")){
							returnValue = "预变更";
							FmHistoryChange.this.setChange3(true);
						}
						return returnValue;
					}
				});
		jTable3.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							if (FmHistoryChange.this.isChange3)
								returnValue = "申请变更";
							else
								returnValue = "申请备案";
						} else if (value.equals("2"))
							returnValue = "等待审批";
						else if (value.equals("3"))
							returnValue = "正在执行";
						return returnValue;
					}
				});
		jTable3.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}
					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(ModifyMarkState.ADDED))
							returnValue = "新增";
					    else if (value.equals(ModifyMarkState.DELETED))
							returnValue = "已删除";
						else if (value.equals(ModifyMarkState.MODIFIED))
							returnValue = "已修改";
						else if (value.equals(ModifyMarkState.UNCHANGE))
							returnValue = "未修改";
						return returnValue;
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
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
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
			jLabel = new JLabel();
			jToolBar = new JToolBar();
			jLabel.setText("   --  经营范围历史变更记录查询");
			jLabel.setForeground(new java.awt.Color(0,102,51));
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jToolBar.add(getJButton());
			jToolBar.add(getBtnExit());
			jToolBar.add(jLabel);
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes jButton7
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnExit() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("   关闭   ");
			jButton7.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmHistoryChange.this.dispose();

				}
			});

		}
		return jButton7;
	}

	/**
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("   查看   ");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					
					if (FmHistoryChange.this.jTabbedPane.getSelectedIndex()==0){
						edit();
					}  else if (jTabbedPane.getSelectedIndex()==1){
						edit1();
					}  else if (jTabbedPane.getSelectedIndex()==2){
						edit2();
					}  else if (jTabbedPane.getSelectedIndex()==3){
						edit4();
					}
				}
			});
		}
		return jButton;
	}
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */    
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("经营范围", null, getJPanel(), null);
			jTabbedPane.addTab("归并关系", null, getJPanel1(), null);
			jTabbedPane.addTab("电子帐册", null, getJPanel2(), null);
			jTabbedPane.addTab("电子分册", null, getJPanel3(), null);
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {

				public void stateChanged(javax.swing.event.ChangeEvent e) {
					setTitle();
				}
			});
		}
		return jTabbedPane;
	}
	private void setTitle(){
		if (this.jTabbedPane.getSelectedIndex() == 0){
			jLabel.setText("   --  经营范围历史变更记录查询");
		} else if (this.jTabbedPane.getSelectedIndex() == 1){
			jLabel.setText("   --  归并关系历史变更记录查询");
		} else if (this.jTabbedPane.getSelectedIndex() == 2){
			jLabel.setText("   --  电子帐册历史变更记录查询");
		} else if (this.jTabbedPane.getSelectedIndex() == 3){
			jLabel.setText("   --  电子分册历史变更记录查询");
		}
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
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
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
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}
	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						edit();
					}
				}
			});
		}
		return jTable;
	}
	private void edit() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmHistoryChange.this, "请选择你将要查看的记录",
					"提示！", 0);
			return;
		}
		DgEmsEdiTr dgEmsEdiTr = new DgEmsEdiTr();
		dgEmsEdiTr.setChange(((EmsEdiTrHead) tableModel.getCurrentRow())
				.getDeclareType().equals(DelcareType.MODIFY));
		dgEmsEdiTr.setHistoryChange(true);
		dgEmsEdiTr.setTableModel(tableModel);
		dgEmsEdiTr.setVisible(true);
	}
	
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jTable1	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						edit1();
					}
				}
			});
		}
		return jTable1;
	}
	private void edit1() {
		if (tableModel1.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmHistoryChange.this, "请选择你将要查看的记录",
					"提示！", 0);
			return;
		}
		DgEmsEdiMerger dgEmsEdiMerger = new DgEmsEdiMerger();
		dgEmsEdiMerger
				.setChange(((EmsEdiMergerHead) tableModel1.getCurrentRow())
						.getDeclareType().equals(DelcareType.MODIFY));
		dgEmsEdiMerger.setHistoryChange(true);
		dgEmsEdiMerger.setTableModel(tableModel1);
		dgEmsEdiMerger.setVisible(true);
	}
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}
	/**
	 * This method initializes jTable2	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
			jTable2.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						edit2();
					}
				}
			});
		}
		return jTable2;
	}
	private void edit2() {
		if (tableModel2.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmHistoryChange.this,
					"请选择你将要查看的记录", "提示！", 0);
			return;
		}
		DgEmsHeadH2k dgEmsHeadH2k = new DgEmsHeadH2k();
		dgEmsHeadH2k.setChange(((EmsHeadH2k) tableModel2.getCurrentRow())
				.getDeclareType().equals(DelcareType.MODIFY));
		dgEmsHeadH2k.setHistoryChange(true);
		dgEmsHeadH2k.setTableModel(tableModel2);
		dgEmsHeadH2k.setVisible(true);
	}
	/**
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}
	/**
	 * @return Returns the isChange1.
	 */
	public boolean isChange1() {
		return isChange1;
	}
	/**
	 * @param isChange1 The isChange1 to set.
	 */
	public void setChange1(boolean isChange1) {
		this.isChange1 = isChange1;
	}
	/**
	 * @return Returns the isChange2.
	 */
	public boolean isChange2() {
		return isChange2;
	}
	/**
	 * @param isChange2 The isChange2 to set.
	 */
	public void setChange2(boolean isChange2) {
		this.isChange2 = isChange2;
	}
	/**
	 * @return Returns the isChange3.
	 */
	public boolean isChange3() {
		return isChange3;
	}
	/**
	 * @param isChange3 The isChange3 to set.
	 */
	public void setChange3(boolean isChange3) {
		this.isChange3 = isChange3;
	}
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(8);
			jSplitPane.setDividerLocation(150);
			jSplitPane.setTopComponent(getJPanel4());
			jSplitPane.setBottomComponent(getJPanel5());
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}
	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJScrollPane4(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
	}
	/**
	 * This method initializes jTable3	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	private JTable getJTable3() {
		if (jTable3 == null) {
			jTable3 = new JTable();
			jTable3.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
			                if (tableModel3 == null)
			                	return;
			                if (tableModel3.getCurrentRow() == null)
			                	return;
			                getFas();
			                
						}
					});
		}
		return jTable3;
	}
	private void getFas(){
		emsHeadH2k = (EmsHeadH2k) tableModel3.getCurrentRow();
		String emsNo = emsHeadH2k.getEmsNo();
		if (emsHeadH2k.getEmsNo() == null){
		   	  emsNo = "";
		}
		List list = manualDecleareAction.findEmsHeadH2kFasAll(new Request(CommonVars.getCurrUser()),emsNo);
		if (list.size()>0){
		   	  initTable4(list); //分册
		} else {
		   	  initTable4(new Vector());
		}
	}
	/**
	 * This method initializes jScrollPane3	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJTable3());
		}
		return jScrollPane3;
	}
	/**
	 * This method initializes jTable4	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	private JTable getJTable4() {
		if (jTable4 == null) {
			jTable4 = new JTable();
			jTable4.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						edit4();
					}
				}
			});
		}
		return jTable4;
	}
	private void edit4() {
		if (tableModel4.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmHistoryChange.this,
					"请选择你将要查看的记录", "提示！", 0);
			return;
		}
		DgEmsHeadH2kFas dg = new DgEmsHeadH2kFas();
		dg.setChange(((EmsHeadH2kFas) tableModel4.getCurrentRow())
				.getDeclareType().equals(DelcareType.MODIFY));
		dg.setHistoryChange(true);
		dg.setTableModel(tableModel4);
		dg.setVisible(true);
	}
	/**
	 * This method initializes jScrollPane4	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getJTable4());
		}
		return jScrollPane4;
	}
	/**
	 * @return Returns the isChange4.
	 */
	public boolean isChange4() {
		return isChange4;
	}
	/**
	 * @param isChange4 The isChange4 to set.
	 */
	public void setChange4(boolean isChange4) {
		this.isChange4 = isChange4;
	}
               } //  @jve:decl-index=0:visual-constraint="10,10"
