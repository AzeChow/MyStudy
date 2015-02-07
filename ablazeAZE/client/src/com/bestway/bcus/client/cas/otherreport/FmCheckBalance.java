package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.authority.CasBalanceReportAction;
import com.bestway.bcus.cas.entity.CheckBalance;
import com.bestway.bcus.cas.entity.CheckBalanceConvertMateriel;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.jptds.client.common.PnMutiConditionQueryPage;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class FmCheckBalance extends JInternalFrameBase {

	private JSplitPane jSplitPane = null;

	private static String SELECT_ALL = "全选"; // @jve:decl-index=0:

	private static String SELECT_NOT_ALL = "不选"; // @jve:decl-index=0:

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JToolBar jToolBar = null;

	private JButton btnImport = null;

	private JButton btnEdit = null;

	private JButton btnDelAll = null;

	/**
	 * 导入原态tableModel
	 */
	private JTableListModel tableModel = null;

	/**
	 * 折算料件tableModel
	 */
	private JTableListModel tableModelAfter = null;

	private JButton btnCalculate = null;

	/**
	 * 海关帐远程接口
	 */
	private CasAction casAction = null;

	/**
	 * 物料操作远程接口
	 */
	private MaterialManageAction materialManageAction = null;
	/**
	 * 权限接口
	 */
	private CasBalanceReportAction casBalanceReportAction = null;

	private JButton btnRefresh = null;

	// private List dataSource = null; // @jve:decl-index=0:

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel = null;

	private JButton btnConvertToBom = null;

	private JButton btnPrint = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	/**
	 * 分页查询组件
	 */
	private PnMutiConditionQueryPage pnCommonQueryPage = null;

	private JTextField tfPtNo = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JComboBox cbbKcType = null;

	private JComboBox cbbWlType = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnBefore = null;

	private JPanel pnAfter = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbConvertMateriel = null;

	private JPanel jPanel1 = null;

	private JButton btnQuery = null;

	private JList lsFactory = null;

	/**
	 * 是否折料后
	 */
	private Boolean isAfter = false; // @jve:decl-index=0:

	private JScrollPane jScrollPane2 = null;

	private JButton btnClose = null;

	private JButton btnDelSelected = null;

	// /**
	// * 查询条件
	// */
	// private List<Condition> conditions;
	/**
	 * 查询条件，因为同时几个地方用到
	 */
	private Date beginDate;
	private Date endDate;

	// 库存类别
	private String kcType;

	// 料号
	private String ptNo;

	// 物料类别
	private String wlType;

	private List<String> lsWareSetCode;// @jve:decl-index=0:

	private JButton btnFindAll = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmCheckBalance() {

		super();

		casBalanceReportAction = (CasBalanceReportAction) CommonVars
				.getApplicationContext().getBean("casBalanceReportAction");
		/**
		 * 初始化海关帐远程接口
		 */
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");

		/**
		 * 初始化物料管理接口
		 */
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");

		initialize();

		initUI();

		initTable(new ArrayList());

		initTableConvertM(new ArrayList());

		fillSeachConditions();

	}

	/**
	 * 初始化一些组件
	 */
	private void initUI() {

		// 仓库
		Vector<Object> vector = new Vector<Object>();
		List listWareSet = materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser(), true));
		for (int i = 0; i < listWareSet.size(); i++) {
			if (i == 0) {
				vector.add(new CheckableItemExtra(SELECT_ALL));
			}
			WareSet obj = (WareSet) listWareSet.get(i);
			CheckableItem item = new CheckableItem(obj.getCode(), obj.getName());
			vector.add(item);
		}
		this.lsFactory.setListData(vector);

		// 库存类别
		this.cbbKcType.removeAllItems();
		this.cbbKcType.addItem(new ItemProperty("0", "料件库存"));
		this.cbbKcType.addItem(new ItemProperty("1", "成品库存"));
		this.cbbKcType.addItem(new ItemProperty("2", "在产品库存"));
		this.cbbKcType.addItem(new ItemProperty("4", "外发未收回"));
		this.cbbKcType.addItem(new ItemProperty("5", "残次品库存"));
		this.cbbKcType.addItem(new ItemProperty("6", "边角料库存"));

		initComboBoxRenderer(cbbKcType);

		// 物料类别
		this.cbbWlType.addItem(new ItemProperty("0", "料件"));
		this.cbbWlType.addItem(new ItemProperty("1", "成品"));
		this.cbbWlType.addItem(new ItemProperty("2", "半成品"));
		initComboBoxRenderer(cbbWlType);

	}

	/**
	 * 初始化下拉框呈现
	 * 
	 * @param cbb
	 */
	private void initComboBoxRenderer(JComboBox cbb) {
		cbb.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbb);
		cbb.setSelectedItem(null);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("盘点平衡表查询(料件级)");
		this.setContentPane(getJSplitPane());
		this.setSize(870, 554);
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
			jSplitPane.setDividerSize(5);
			jSplitPane.setTopComponent(getJSplitPane1());
			jSplitPane.setBottomComponent(getJTabbedPane());
			jSplitPane.setDividerLocation(160);
			jSplitPane.setEnabled(false);

		}
		return jSplitPane;
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

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnImport());
			jToolBar.add(getJbCalculate());
			jToolBar.add(getBtnConvertToBom());

			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelSelected());
			jToolBar.add(getBtnDelAll());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnFindAll());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * 1.导入盘点数据 按钮
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("1.导入盘点数据");
			btnImport.setPreferredSize(new Dimension(100, 30));
			btnImport.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					casBalanceReportAction.checkBalanceByImport(new Request(
							CommonVars.getCurrUser()));
					DgCheckBalanceImport dg = new DgCheckBalanceImport();
					dg.setVisible(true);

				}

			});
		}
		return btnImport;
	}

	/**
	 * 修改按钮
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(60, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCheckBalance.this,
								"请选择你要修改的数据!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgCheckBalance dg = new DgCheckBalance();
					dg.setTableModel(tableModel);
					CheckBalance data = (CheckBalance) tableModel
							.getCurrentRow();
					dg.fillAllData(data);
					dg.setVisible(true);
				}

			});
		}
		return btnEdit;
	}

	/**
	 * 删除全部 按钮
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelAll() {
		if (btnDelAll == null) {
			btnDelAll = new JButton();
			btnDelAll.setText("删除全部");
			btnDelAll.setPreferredSize(new Dimension(64, 30));
			btnDelAll.setToolTipText("删除当前【查询条件】下的所有数据");
			btnDelAll.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {

					casBalanceReportAction.checkBalanceByDelete(new Request(
							CommonVars.getCurrUser()));
					// 日期是一定要选择的,否则很可能会误删除数据
					if (cbbBeginDate.getDate() == null
							|| cbbEndDate.getDate() == null) {
						JOptionPane.showMessageDialog(FmCheckBalance.this,
								"您必须选择时间！", "注意啦！！！", 0);
						return;
					}

					if (JOptionPane.showConfirmDialog(FmCheckBalance.this,
							"确定要删除   【当前查询条件下】  的所有记录吗？？", "警告!!!",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

						new DeleteThread().start();// wss 一起删除,会更快。

						// new DeleteThreadOneByOne().start();//wss 一条一条地删

					}
				}

			});
		}
		return btnDelAll;
	}

	/**
	 * 删除 （一起删）
	 * 
	 * @author wss
	 */
	class DeleteThread extends Thread {
		public void run() {

			// 用于标识这个对话框的ID
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				btnDelAll.setEnabled(false);
				String info = "";
				long beginTime = System.currentTimeMillis();

				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在删除，请稍后...");

				fillSeachConditions();
				if (!isAfter) {
					System.out.println("正在删除导入原态的");
					casAction.deleteAllCheckBalance(
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate, kcType, ptNo, wlType, lsWareSetCode);
					initTable(new ArrayList());
					initTableConvertM(new ArrayList());
				} else {
					System.out.println("正在删除折料后的");
					List ls = tableModelAfter.getList();
					casAction.deleteAllCheckBalanceConvertMateriel(new Request(
							CommonVars.getCurrUser()), beginDate, endDate,
							kcType, ptNo, wlType, lsWareSetCode);
					initTableConvertM(new ArrayList());
				}

				CommonProgress.closeProgressDialog();

				// long endTime = System.currentTimeMillis();
				//
				// info += " 任务完成,共用时:" + (endTime - beginTime) / (1000 * 60)
				// + "分 " + (endTime - beginTime) / 1000 % 60 + "秒";
				// JOptionPane.showMessageDialog(FmCheckBalance.this, info,
				// "提示",
				// 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmCheckBalance.this, "删除失败！！！"
						+ e.getMessage(), "提示", 2);
			} finally {
				ToolsAction toolsAction = (ToolsAction) CommonVars
						.getApplicationContext().getBean("toolsAction");
				toolsAction.removeProgressInfo(flag);
				CommonProgress.closeProgressDialog();

			}
			btnDelAll.setEnabled(true);
		}
	}

	/**
	 * 删除 one by one
	 * 
	 * @author wss
	 */
	class DeleteThreadOneByOne extends Thread {
		public void run() {

			try {

				CommonStepProgress.showStepProgressDialog(null);

				if (!isAfter) {
					List ls = tableModel.getList();
					CommonStepProgress.setStepProgressMaximum(ls.size());
					int size = ls.size();
					for (int i = 0; i < size; i++) {
						CheckBalance data = (CheckBalance) ls.get(i);
						casAction.deleteCheckBalanceOneByOne(new Request(
								CommonVars.getCurrUser()), data);

						CommonStepProgress.setStepMessage("正在删除:第 " + i
								+ "条,总共" + size + "条");
						CommonStepProgress.setStepProgressValue(i + 1);
					}
					initTable(new ArrayList());

				} else {
					List ls = tableModelAfter.getList();

					System.out.println("wss ls.size" + ls.size());
					int size = ls.size();
					CommonStepProgress.setStepProgressMaximum(ls.size());

					for (int i = 0; i < size; i++) {
						CheckBalanceConvertMateriel data = (CheckBalanceConvertMateriel) ls
								.get(i);
						casAction.deleteCheckBalanceConvertMateriel(
								new Request(CommonVars.getCurrUser()), data);

						CommonStepProgress.setStepMessage("正在删除:第 " + i
								+ "条,总共" + size + "条");
						CommonStepProgress.setStepProgressValue(i + 1);
					}
					initTableConvertM(new ArrayList());
				}

				CommonStepProgress.closeStepProgressDialog();

			} catch (Exception e) {
				e.printStackTrace();
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmCheckBalance.this, "删除失败！！！"
						+ e.getMessage(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	// /**
	// * 刷新数据
	// */
	// private void refresh(){
	// List list = casAction.findCheckBalance(new
	// Request(CommonVars.getCurrUser()));
	// initTable(list);
	// }

	/**
	 * 初始化导入原态 表数据
	 */
	private JTableListModel initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("盘点日期", "checkDate", 100));
						list.add(addColumn("物料标记", "ljCpMark", 80));
						list.add(addColumn("工厂料号", "ptNo", 100));
						list.add(addColumn("工厂单位", "ptUnit.name", 100));
						list.add(addColumn("仓库名称", "wareSet.name", 100));
						list.add(addColumn("库存数量", "checkAmount", 100));
						list.add(addColumn("报关商品名称", "name", 100));
						list.add(addColumn("报关商品规格", "spec", 100));
						list.add(addColumn("报关商品单位", "hsUnit.name", 100));
						list.add(addColumn("折算报关数量", "hsAmount", 100));
						list.add(addColumn("折算报关比率", "unitConvert", 100));
						list.add(addColumn("库存类别", "kcType", 100));
						list.add(addColumn("Bom版本号", "bomVersion", 100));
						list.add(addColumn("备注", "note", 200));
						return list;
					}
				});

		jTable.getColumnModel().getColumn(2)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					// 料件0， 成品 1 ,半成2，残次品3，外发加工4
					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("0")) {
							returnValue = "料件";
						} else if (value.equals("1")) {
							returnValue = "成品";
						} else if (value.equals("2")) {
							returnValue = "半成品";
						}
						return returnValue;
					}
				});

		jTable.getColumnModel().getColumn(12)
				.setCellRenderer(new DefaultTableCellRenderer() {
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
							returnValue = "料件库存";
						} else if (value.equals("1")) {
							returnValue = "成品库存";
						} else if (value.equals("2")) {
							returnValue = "在产品库存";
							// } else if (value.equals("3")) {
							// returnValue = "在产品库存";
						} else if (value.equals("5")) {
							returnValue = "残次品库存";
						} else if (value.equals("4")) {
							returnValue = "外发未收回";
						} else if (value.equals("6")) {
							returnValue = "边角料库存";
						}
						return returnValue;
					}
				});

		// 不能自定义列
		tableModel.setMiRenderColumnEnabled(false);
		return tableModel;
	}

	/**
	 * 初始化折算后的表
	 * 
	 * @return com.bestway.client.util.mutispan.AttributiveCellTableModel
	 */
	private JTableListModel initTableConvertM(List list) {

		tableModelAfter = new AttributiveCellTableModel(
				(MultiSpanCellTable) tbConvertMateriel, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						// 成品信息
						list.add(addColumn("盘点日期",
								"fatherCheckBalance.checkDate", 100));// 1
						list.add(addColumn("物料标记",
								"fatherCheckBalance.ljCpMark", 100));// 2
						list.add(addColumn("工厂料号", "fatherCheckBalance.ptNo",
								100));// 3
						list.add(addColumn("库存数量",
								"fatherCheckBalance.checkAmount", 100));// 4
						list.add(addColumn("库存类别", "fatherCheckBalance.kcType",
								100));// 5
						list.add(addColumn("仓库名称",
								"fatherCheckBalance.wareSet.name", 100));// 6
						list.add(addColumn("版本号",
								"fatherCheckBalance.bomVersion", 100));// 7

						// 折料信息
						list.add(addColumn("物料标记", "ljCpMark", 80));// 8
						list.add(addColumn("工厂料号", "ptNo", 100));// 9
						list.add(addColumn("工厂单位", "ptUnit.name", 100));// 10
						list.add(addColumn("库存数量", "checkAmount", 100));// 11
						list.add(addColumn("报关商品名称", "name", 100));// 12
						list.add(addColumn("报关商品规格", "spec", 100));// 13
						list.add(addColumn("报关商品单位", "hsUnit.name", 100));// 14
						list.add(addColumn("折算报关数量", "hsAmount", 100));// 15
						list.add(addColumn("折算报关比率", "unitConvert", 100));// 16
						list.add(addColumn("备注", "note", 200));// 17
						return list;
					}
				});

		// 物料标记
		tbConvertMateriel.getColumnModel().getColumn(2)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					// 物料标记 料件0， 成品 1 ,半成2
					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("0")) {
							returnValue = "料件";
						} else if (value.equals("1")) {
							returnValue = "成品";
						} else if (value.equals("2")) {
							returnValue = "半成品";
						}
						return returnValue;
					}
				});

		// 库存类别
		tbConvertMateriel.getColumnModel().getColumn(5)
				.setCellRenderer(new DefaultTableCellRenderer() {
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
							returnValue = "料件库存";
						} else if (value.equals("1")) {
							returnValue = "成品库存";
						} else if (value.equals("2")) {
							returnValue = "在产品库存";
							// } else if (value.equals("3")) {
							// returnValue = "在产品库存";
						} else if (value.equals("5")) {
							returnValue = "残次品库存";
						} else if (value.equals("4")) {
							returnValue = "外发未收回";
						} else if (value.equals("6")) {
							returnValue = "边角料";
						}
						return returnValue;
					}
				});

		// Bom物料标记
		jTable.getColumnModel().getColumn(8)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					// 物料标记 料件0， 成品 1 ,半成2
					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("0")) {
							returnValue = "料件";
						} else if (value.equals("1")) {
							returnValue = "成品";
						} else if (value.equals("2")) {
							returnValue = "半成品";
						}
						return returnValue;
					}
				});

		TableColumnModel tcm = tbConvertMateriel.getColumnModel();
		ColumnGroup cgProduct = new ColumnGroup("成品");
		cgProduct.add(tcm.getColumn(1));
		cgProduct.add(tcm.getColumn(2));
		cgProduct.add(tcm.getColumn(3));
		cgProduct.add(tcm.getColumn(4));
		cgProduct.add(tcm.getColumn(5));
		cgProduct.add(tcm.getColumn(6));
		cgProduct.add(tcm.getColumn(7));

		ColumnGroup cbBom = new ColumnGroup("料件");
		cbBom.add(tcm.getColumn(8));
		cbBom.add(tcm.getColumn(9));
		cbBom.add(tcm.getColumn(10));
		cbBom.add(tcm.getColumn(11));
		cbBom.add(tcm.getColumn(12));
		cbBom.add(tcm.getColumn(13));
		cbBom.add(tcm.getColumn(14));
		cbBom.add(tcm.getColumn(15));
		cbBom.add(tcm.getColumn(16));
		cbBom.add(tcm.getColumn(17));

		GroupableTableHeader header = (GroupableTableHeader) tbConvertMateriel
				.getTableHeader();
		header.setColumnModel(tbConvertMateriel.getColumnModel());
		header.addColumnGroup(cgProduct);
		header.addColumnGroup(cbBom);
		tbConvertMateriel.repaint();

		combineTable();// 合并

		return tableModelAfter;
	}

	/**
	 * 合并料件单元格
	 */
	public void combineTable() {
		((MultiSpanCellTable) tbConvertMateriel).splitRows2(new int[] { 1, 2,
				3, 4, 5, 6, 7 });
		((MultiSpanCellTable) tbConvertMateriel).combineRowsByIndeies(
				new int[] { 1, 2, 3, 4, 5, 6, 7 }, new int[] { 1, 2, 3, 4, 5,
						6, 7 });
	}

	/**
	 * This method initializes jbCalculate
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbCalculate() {
		if (btnCalculate == null) {
			btnCalculate = new JButton();
			btnCalculate.setText("2.折算报关数量");
			btnCalculate.setPreferredSize(new Dimension(100, 30));
			btnCalculate.setToolTipText("获取相应的报关资料。如果原先折算过，可以不折");
			btnCalculate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList() == null
							|| tableModel.getList().size() == 0) {
						return;
					}

					// 将查询条件时间改为，列表内最大时间最小时间
					for (int i = 0; i < tableModel.getList().size(); i++) {
						CheckBalance c = (CheckBalance) tableModel.getList()
								.get(i);
						if (i == 0) {
							beginDate = c.getCheckDate();
							endDate = c.getCheckDate();
						} else {
							if (c.getCheckDate().before(beginDate)) {
								beginDate = c.getCheckDate();
							}
							if (c.getCheckDate().after(endDate)) {
								endDate = c.getCheckDate();
							}

						}
					}
					cbbBeginDate.setDate(beginDate);
					cbbEndDate.setDate(endDate);
					// 判断是否先前折过了
					// conditions = getConditions();
					// List list = casAction.findCheckBalanceConvertM(new
					// Request(
					// CommonVars.getCurrUser()),conditions);

					//
					// Date beginDate = getCbbBeginDate().getDate();
					// Date endDate = getCbbEndDate().getDate();
					// String kcType = cbbKcType.getSelectedItem() == null ? "":
					// ((ItemProperty)cbbKcType.getSelectedItem()).getCode();
					// String ptNo = getTfPtNo().getText().trim();
					// String wlType = cbbWlType.getSelectedItem() == null ? "":
					// ((ItemProperty)cbbWlType.getSelectedItem()).getCode();

					// 获取折算料件数量，用于判断先前是否已经折算过
					List list = casAction.findCheckBalanceConvertMateriel(0,
							100, new Request(CommonVars.getCurrUser()),
							beginDate, endDate, kcType, ptNo, wlType,
							lsWareSetCode);

					if (list.size() > 0) {
						JOptionPane.showMessageDialog(FmCheckBalance.this,
								"您之前有折算过!!! 如果确定要重新折算，请先删除原先折算过的折算料件数据", "确认",
								1);

						isAfter = true;
						getJTabbedPane().setSelectedIndex(1);
						initTableConvertM(list);
						return;
					}

					if (JOptionPane.showConfirmDialog(FmCheckBalance.this,
							btnCalculate.getText() + "\n确定要继续吗？？", "警告!!!",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

						new GetHsInfoThread().start();// wss此线程批量计算
						// new GetHsInfoOneByOneThread().start();//wss此线程是一条一条地
						// 计算
					}

				}
			});
		}
		return btnCalculate;
	}

	/**
	 * 折算报关数量（获取报关资料）
	 * 
	 * @author Administrator
	 */
	class GetHsInfoThread extends Thread {
		public void run() {
			try {
				btnCalculate.setEnabled(false);
				String info = "";
				long beginTime = System.currentTimeMillis();
				List listUp = new ArrayList();
				CommonStepProgress.showStepProgressDialog(null);
				CommonStepProgress.setStepMessage("系统正在重新获取资料，请稍后...");

				List returnList = casAction.findCheckBalance(-1, -1,
						new Request(CommonVars.getCurrUser()), beginDate,
						endDate, kcType, ptNo, wlType, lsWareSetCode);

				CommonStepProgress.setStepProgressMaximum(returnList.size());
				int i = 0;
				for (; i < returnList.size(); i++) {
					CheckBalance company = (CheckBalance) returnList.get(i);
					listUp.add(company);
				}
				if (!listUp.isEmpty()) {
					casAction.fillCheckBalanceHsInfoPerPage(new Request(
							CommonVars.getCurrUser()), listUp);
					CommonStepProgress.setStepMessage("总共有【"
							+ returnList.size() + "】条数据,正在导入 ［ " + i + " 条］"
							+ " 数据到实体中 !");
					CommonStepProgress.setStepProgressValue(i);
					listUp.clear();
				}

				CommonStepProgress.closeStepProgressDialog();

				long endTime = System.currentTimeMillis();

				info += " 任务完成,共用时:" + (endTime - beginTime) / (1000 * 60)
						+ "分 " + (endTime - beginTime) / 1000 % 60 + "秒";
				JOptionPane.showMessageDialog(FmCheckBalance.this, info, "提示",
						2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmCheckBalance.this, "重新获取失败！！！"
						+ e.getMessage(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}

			btnCalculate.setEnabled(true);
			new SearchThread().start();// 更新显示
		}
	}

	/**
	 * 折算报关数量（获取报关资料),如果是料件，复制到折算料件 one by one
	 * 
	 * @author wss
	 */
	class GetHsInfoOneByOneThread extends Thread {
		public void run() {

			try {
				btnCalculate.setEnabled(false);
				String info = "";
				long beginTime = System.currentTimeMillis();

				CommonStepProgress.showStepProgressDialog(null);
				CommonStepProgress.setStepMessage("系统正在重新获取资料，请稍后...");

				List ls = tableModel.getList();
				CommonStepProgress.setStepProgressMaximum(ls.size());
				CheckBalance cb;
				for (int i = 0; i < ls.size(); i++) {
					cb = (CheckBalance) ls.get(i);

					long b = System.currentTimeMillis();

					casAction.fillCheckBalanceHsInfoOneByOne(new Request(
							CommonVars.getCurrUser()), cb);

					long e = System.currentTimeMillis();

					System.out.println("-这一条用的时间为：" + (e - b) + " ms");

					CommonStepProgress.setStepMessage("正在 折算：第" + i + " 条，料号为："
							+ cb.getPtNo());
					CommonStepProgress.setStepProgressValue(i);
				}

				CommonStepProgress.closeStepProgressDialog();
				long endTime = System.currentTimeMillis();

				info += " 任务完成,共用时:" + (endTime - beginTime) / (1000 * 60)
						+ "分 " + (endTime - beginTime) / 1000 % 60 + "秒";
				JOptionPane.showMessageDialog(FmCheckBalance.this, info, "提示",
						2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmCheckBalance.this, "获取资料失败！！！"
						+ e.getMessage(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
			btnCalculate.setEnabled(true);
		}
	}

	/**
	 * 刷新 按钮
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.setPreferredSize(new Dimension(60, 30));
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					fillSeachConditions();
					getPnCommonQueryPage().setInitState();
				}

			});
		}
		return btnRefresh;
	}

	// /**
	// * 获得数据源
	// *
	// * @param index
	// * @param length
	// * @param property
	// * @param value
	// * @param isLike
	// * @return
	// */
	// public List getDataSource(int index, int length, String property,
	// Object value, boolean isLike) {
	// Request request = new Request(CommonVars.getCurrUser());
	// dataSource = casAction.findCheckBalance(request, index, length,
	// property,
	// value,
	// isLike);
	// return dataSource;
	// }

	// /**
	// * 得到所有数据
	// */
	// public void getData() {
	// getConditions();
	// }

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setBottomComponent(getJToolBar());
			jSplitPane1.setDividerSize(5);
			jSplitPane1.setTopComponent(getJPanel1());
			jSplitPane1.setDividerLocation(115);
			jSplitPane1.setEnabled(false);

		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(12, 12, 33, 14));
			jLabel5.setText("仓库");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(621, 22, 53, 22));
			jLabel4.setText("物料类别");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(208, 54, 50, 22));
			jLabel3.setText("料号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(430, 54, 55, 22));
			jLabel2.setText("库存类别");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(208, 22, 50, 22));
			jLabel.setText("开始日期");
			jPanel = new JPanel();
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "查询条件",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel.setLayout(null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getTfPtNo(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getJLabel1(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(getCbbKcType(), null);
			jPanel.add(getCbbWlType(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getJScrollPane2(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnConvertToBom
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnConvertToBom() {
		if (btnConvertToBom == null) {
			btnConvertToBom = new JButton();
			btnConvertToBom.setText("3.成品折算料件(报关常用工厂BOM)");
			btnConvertToBom.setPreferredSize(new Dimension(213, 30));
			btnConvertToBom.setToolTipText("将物料类型为[成品/半成品]的部分按Bom折算成料件");
			btnConvertToBom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (tableModel.getList() == null
									|| tableModel.getList().size() == 0) {
								return;
							}

							// conditions = getConditions();
							// conditions.add(new Condition("and",
							// "(","fatherCheckBalance.ljCpMark", "=","1",
							// null));
							// conditions.add(new Condition("or",
							// null,"fatherCheckBalance.ljCpMark", "=","2",
							// ")"));
							//
							// List list =
							// casAction.findCheckBalanceConvertM(new Request(
							// CommonVars.getCurrUser()),conditions);
							//
							// // List list =
							// casAction.findCheckBalanceConvertMateriel(0,100,new
							// Request(
							// //
							// CommonVars.getCurrUser()),beginDate,endDate,"(2 or 1)",ptNo,wlType);

							// 1.获取数量判断是否先前折算过,数量>0
							int count = casAction
									.getBalanceCheckConvertMaterielCount(
											new Request(CommonVars
													.getCurrUser()), beginDate,
											endDate, kcType, ptNo, wlType,
											lsWareSetCode);

							if (count > 0) {
								if (JOptionPane.showConfirmDialog(
										FmCheckBalance.this,
										"您之前有折算过BOM,是否删除先前的折算重新折算\n"
												+ "确认重新折算选择：是(Y)  不折算选择：否(N) ",
										"警告!!!", JOptionPane.YES_NO_OPTION,
										JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

									new ConvertToBomThreadOneByOne(true)
											.start();// wss此线程是一条一条地折

								}
							}

							else if (JOptionPane.showConfirmDialog(
									FmCheckBalance.this,
									btnConvertToBom.getText() + "\n确定要继续吗？？",
									"警告!!!", JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

								new ConvertToBomThreadOneByOne(false).start();// wss此线程是一条一条地折
								// new ConvertToBomThread().start();//wss此线程是一起折

							}
						}
					});
		}
		return btnConvertToBom;
	}

	/**
	 * 成品折算料件 批量
	 * 
	 * @author wss
	 */
	class ConvertToBomThread extends Thread {
		public void run() {
			try {
				btnConvertToBom.setEnabled(false);
				List infos = new ArrayList();
				String info = "";
				long beginTime = System.currentTimeMillis();

				CommonStepProgress.showStepProgressDialog(null);
				CommonStepProgress.setStepMessage("系统正在重新获取资料，请稍后...");

				// 要折BOM的数据
				List list = tableModel.getList();

				CommonStepProgress.setStepProgressMaximum(list.size());

				List<CheckBalanceConvertMateriel> lsAll = new ArrayList<CheckBalanceConvertMateriel>();

				for (int i = 0; i < list.size(); i++) {

					CheckBalance b = (CheckBalance) list.get(i);
					CommonStepProgress.setStepProgressValue(i + 1);

					// 如果已经是料件则直接跳过
					if ("0".equals(b.getLjCpMark())) {
						continue;
					}
					CommonStepProgress.setStepMessage("正在 计算：第" + i + " 条，料号为："
							+ b.getPtNo());

					casAction.convertCheckBalanceToBomOneByOne(new Request(
							CommonVars.getCurrUser()), b);

				}

				CommonStepProgress.closeStepProgressDialog();

				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane.showMessageDialog(FmCheckBalance.this, info, "提示",
						2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmCheckBalance.this, "重新获取失败！！！"
						+ e.getMessage(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();

			}
			btnConvertToBom.setEnabled(true);
		}
	}

	/**
	 * 成品折算料件 one by one
	 * 
	 * @author wss
	 */
	class ConvertToBomThreadOneByOne extends Thread {
		private Boolean isDeleteOldData = false;

		ConvertToBomThreadOneByOne(Boolean isDeleteOldData) {
			this.isDeleteOldData = isDeleteOldData;
		}

		public void run() {
			try {
				btnConvertToBom.setEnabled(false);
				List infos = new ArrayList();
				String info = "";
				long beginTime = System.currentTimeMillis();

				CommonStepProgress.showStepProgressDialog(null);
				CommonStepProgress.setStepMessage("系统正在重新获取资料，请稍后...");

				// 要折BOM的数据
				List list = tableModel.getList();

				CommonStepProgress.setStepProgressMaximum(list.size());

				if (isDeleteOldData) {
					CommonStepProgress.setStepMessage("系统正在删除旧数据，请稍后...");

					casAction.deleteCheckBalanceConvertM(
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate, kcType, ptNo, wlType, lsWareSetCode);
					CommonStepProgress.setStepMessage("系统正进行计算，请稍后...");
				}

				for (int i = 0; i < list.size(); i++) {

					CheckBalance b = (CheckBalance) list.get(i);
					CommonStepProgress.setStepProgressValue(i + 1);

					// 如果已经是料件则直接跳过
					if ("0".equals(b.getLjCpMark())) {
						continue;
					}

					CommonStepProgress.setStepMessage("正在 计算：第" + i + " 条，料号为："
							+ b.getPtNo());

					casAction.convertCheckBalanceToBomOneByOne(new Request(
							CommonVars.getCurrUser()), b);
				}

				CommonStepProgress.closeStepProgressDialog();

				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane.showMessageDialog(FmCheckBalance.this, info, "提示",
						2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmCheckBalance.this, "重新获取失败！！！"
						+ e.getMessage(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();

			}
			btnConvertToBom.setEnabled(true);
		}
	}

	/**
	 * 转换值 物料类别
	 * 
	 * @param value
	 * @return
	 */
	public static String castWlTypeValue(Object value) {
		String returnValue = "";
		if (String.valueOf(value).trim().equals("")) {
			return "";
		}
		if (value.equals("0")) {
			returnValue = "料件";
		} else if (value.equals("1")) {
			returnValue = "成品";
		} else if (value.equals("3")) {
			returnValue = "半成品";
		}
		return returnValue;
	}

	/**
	 * 转换值 库存类别
	 * 
	 * @param value
	 * @return
	 */
	public static String castKcTypeValue(Object value) {
		String returnValue = "";
		if (String.valueOf(value).trim().equals("")) {

			return "";
		}
		if (value.equals("0")) {
			returnValue = "料件库存";
		} else if (value.equals("1")) {
			returnValue = "成品库存";
		} else if (value.equals("2")) {
			returnValue = "半成品库存";
		} else if (value.equals("3")) {
			returnValue = "外发未收回";
		}
		return returnValue;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setPreferredSize(new Dimension(60, 30));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList() != null
							&& !tableModel.getList().isEmpty()) {

						CommonDataSource imgExgDS = new CommonDataSource(
								tableModel.getList());
						List<BaseCompany> company = new ArrayList<BaseCompany>();
						company.add(CommonVars.getCurrUser().getCompany());
						CommonDataSource companyDS = new CommonDataSource(
								company);

						InputStream masterReportStream = FmBalanceInfo.class
								.getResourceAsStream("report/CheckBalanceReport.jasper");
						InputStream detailReportStream = FmBalanceInfo.class
								.getResourceAsStream("report/CheckBalanceSubbReport.jasper");
						try {
							JasperReport detailReport = (JasperReport) JRLoader
									.loadObject(detailReportStream);

							Map<String, Object> parameters = new HashMap<String, Object>();
							parameters.put("imgExgDS", imgExgDS);
							parameters.put("detailReport", detailReport);
							JasperPrint jasperPrint;
							jasperPrint = JasperFillManager.fillReport(
									masterReportStream, parameters, companyDS);
							DgReportViewer viewer = new DgReportViewer(
									jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(262, 19, 116, 25));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(486, 19, 115, 25));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes tfPtNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(new Rectangle(262, 51, 116, 25));
		}
		return tfPtNo;
	}

	// /**
	// * 公共查询组件
	// *
	// * @return
	// */
	// private PnMutiConditionQueryPage getPnCommonQueryPage2() {
	// if (pnCommonQueryPage == null) {
	// pnCommonQueryPage = new PnMutiConditionQueryPage() {
	//
	// List list = new ArrayList();
	// @Override
	// public JTableListModel initTable(List dataSource) {
	// if(!isAfter){
	// return FmCheckBalance.this.initTable(dataSource);
	// }else{
	// return FmCheckBalance.this.initTableConvertM(dataSource);
	// }
	//
	// }
	//
	// @Override
	// public List getDataSource(int index, int length) {
	//
	// // Date beginDate = getCbbBeginDate().getDate();
	// // Date endDate = getCbbEndDate().getDate();
	// // String kcType = cbbKcType.getSelectedItem() == null ? "":
	// // ((ItemProperty)cbbKcType.getSelectedItem()).getCode();
	// // String ptNo = getTfPtNo().getText().trim();
	// // String wlType = cbbWlType.getSelectedItem() == null ? "":
	// // ((ItemProperty)cbbWlType.getSelectedItem()).getCode();
	//
	//
	// conditions = getConditions();
	// if(!isAfter){
	// list = casAction.findCheckBalance(new Request(
	// CommonVars.getCurrUser()),conditions);
	// //
	// // list = casAction.findCheckBalance(index,length,new Request(
	// // CommonVars.getCurrUser()),beginDate,endDate,kcType,ptNo,wlType);
	// }else{
	// list = casAction.findCheckBalanceConvertM(new Request(
	// CommonVars.getCurrUser()),conditions);
	// //
	// // list = casAction.findCheckBalanceConvertMateriel(index,length,new
	// Request(
	// // CommonVars.getCurrUser()),beginDate,endDate,kcType,ptNo,wlType);
	// }
	// return list;
	//
	// }
	//
	// @Override
	// protected Long getDataSourceCount() {
	// int count = 0;
	//
	// // Date beginDate = getCbbBeginDate().getDate();
	// // Date endDate = getCbbEndDate().getDate();
	// // String kcType = cbbKcType.getSelectedItem() == null ? "":
	// // ((ItemProperty)cbbKcType.getSelectedItem()).getCode();
	// // String ptNo = getTfPtNo().getText().trim();
	// // String wlType = cbbWlType.getSelectedItem() == null ? "":
	// // ((ItemProperty)cbbWlType.getSelectedItem()).getCode();
	// // if(!isAfter){
	// // count = casAction.findCheckBalanceCount(new Request(
	// // CommonVars.getCurrUser()),beginDate,endDate,kcType,ptNo,wlType);
	// //
	// // }else{
	// // count = casAction.findCheckBalanceConvertMaterielCount(new Request(
	// // CommonVars.getCurrUser()),beginDate,endDate,kcType,ptNo,wlType);
	// //
	// // }
	// // return Long.valueOf(count);
	// return Long.valueOf(String.valueOf(list.size()));
	// }
	// };
	// }
	// return pnCommonQueryPage;
	// }

	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	private PnMutiConditionQueryPage getPnCommonQueryPage() {
		if (pnCommonQueryPage == null) {
			pnCommonQueryPage = new PnMutiConditionQueryPage() {

				List list = new ArrayList();

				@Override
				public JTableListModel initTable(List dataSource) {
					if (!isAfter) {
						return FmCheckBalance.this.initTable(dataSource);
					} else {
						return FmCheckBalance.this
								.initTableConvertM(dataSource);
					}

				}

				@Override
				public List getDataSource(int index, int length) {

					if (!isAfter) {
						list = casAction.findCheckBalance(index, length,
								new Request(CommonVars.getCurrUser()),
								beginDate, endDate, kcType, ptNo, wlType,
								lsWareSetCode);
					} else {
						list = casAction.findCheckBalanceConvertMateriel(index,
								length, new Request(CommonVars.getCurrUser()),
								beginDate, endDate, kcType, ptNo, wlType,
								lsWareSetCode);
					}
					return list;

				}

				@Override
				protected Long getDataSourceCount() {
					int count = 0;

					if (!isAfter) {
						count = casAction.findCheckBalanceCount(new Request(
								CommonVars.getCurrUser()), beginDate, endDate,
								kcType, ptNo, wlType, lsWareSetCode);

					} else {
						count = casAction.findCheckBalanceConvertMaterielCount(
								new Request(CommonVars.getCurrUser()),
								beginDate, endDate, kcType, ptNo, wlType,
								lsWareSetCode);

					}
					return Long.valueOf(count);
				}
			};
		}
		return pnCommonQueryPage;
	}

	/**
	 * 填充查询条件
	 */
	private void fillSeachConditions() {

		beginDate = getCbbBeginDate().getDate();

		endDate = getCbbEndDate().getDate();

		kcType = cbbKcType.getSelectedItem() == null ? ""
				: ((ItemProperty) cbbKcType.getSelectedItem()).getCode();

		ptNo = getTfPtNo().getText().trim();

		wlType = cbbWlType.getSelectedItem() == null ? ""
				: ((ItemProperty) cbbWlType.getSelectedItem()).getCode();

		// 仓库
		lsWareSetCode = new ArrayList<String>();

		for (int j = 0; j < lsFactory.getModel().getSize(); j++) {

			Object value = lsFactory.getModel().getElementAt(j);

			if (value instanceof CheckableItem) {

				CheckableItem item = (CheckableItem) value;

				if (item.isSelected) {

					lsWareSetCode.add(item.getCode());

				}
			}
		}
	}

	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("结束日期");
			jLabel1.setBounds(new Rectangle(429, 22, 53, 22));
		}
		return jLabel1;
	}

	/**
	 * This method initializes cbbKcType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbKcType() {
		if (cbbKcType == null) {
			cbbKcType = new JComboBox();
			cbbKcType.setBounds(new Rectangle(486, 51, 115, 25));
		}
		return cbbKcType;
	}

	/**
	 * This method initializes cbbWlType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWlType() {
		if (cbbWlType == null) {
			cbbWlType = new JComboBox();
			cbbWlType.setBounds(new Rectangle(675, 19, 117, 25));
		}
		return cbbWlType;
	}

	/**
	 * 设置怎么样编辑JComboBox 上的 JList 和 JTextField
	 * 
	 * @param cbb
	 */
	private void setCmbBillTypeEvent(final JComboBox cbb) {
		final JList listBox;
		try {
			Field field = JComponent.class.getDeclaredField("ui");
			field.setAccessible(true);
			BasicComboBoxUI ui = (BasicComboBoxUI) field.get(cbb);
			field = BasicComboBoxUI.class.getDeclaredField("listBox");
			field.setAccessible(true);
			listBox = (JList) field.get(ui);
		} catch (NoSuchFieldException nsfe) {
			throw new RuntimeException(nsfe);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae);
		}
		if (listBox == null) {
			return;
		}

		listBox.setBackground(Color.white);
		listBox.setFixedCellHeight(20);

		MouseListener[] mouseListener = listBox.getMouseListeners();
		for (int i = 0; i < mouseListener.length; i++) {
			listBox.removeMouseListener(mouseListener[i]);
		}

		listBox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = listBox.locationToIndex(e.getPoint());
				if (index == -1) {
					return;
				}
				CheckBoxListItem item = (CheckBoxListItem) listBox.getModel()
						.getElementAt(index);
				item.setIsSelected(!item.getIsSelected());
				Rectangle rect = listBox.getCellBounds(index, index);
				listBox.repaint(rect);
				ComboBoxEditor com = cbb.getEditor();
				JTextField tf = (JTextField) com.getEditorComponent();
				if (tf != null) {
					if (item.getIsSelected()) {
						tf.setText("".equals(tf.getText()) ? item.getName()
								: tf.getText() + "," + item.getName());
					} else {
						String[] strs = tf.getText().split(",");
						String str = "";
						for (int i = 0; i < strs.length; i++) {
							if (item.getName().equalsIgnoreCase(strs[i])) {
								continue;
							}
							if ("".equals(str)) {
								str += strs[i];
							} else {
								str += "," + strs[i];
							}
						}
						tf.setText(str);
					}
				}
			}
		});

		//
		// 当焦点丢失的时候
		//
		ComboBoxEditor com = cbb.getEditor();
		final JTextField tf = (JTextField) com.getEditorComponent();
		tf.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {

				String[] strs = tf.getText().split(",");
				Map<String, CheckBoxListItem> checkBoxListItemMap = new HashMap<String, CheckBoxListItem>();

				int size = listBox.getModel().getSize();
				for (int i = 0; i < size; i++) {
					CheckBoxListItem item = (CheckBoxListItem) listBox
							.getModel().getElementAt(i);
					checkBoxListItemMap.put(item.getName(), item);
				}
				//
				// 根据输入的字符串选中JList中的列表
				//
				String tempText = "";
				// System.out.println("strs.length =="+strs.length);
				for (String str : strs) {
					// System.out.println(str);
					CheckBoxListItem item = checkBoxListItemMap.get(str);
					if (item != null) {
						item.setIsSelected(true);
						if ("".equals(tempText)) {
							tempText += item.getName();
						} else {
							tempText += "," + item.getName();
						}
						checkBoxListItemMap.remove(str);
					}
				}
				//
				// 清除未选中的记录
				//
				Iterator<CheckBoxListItem> iterator = checkBoxListItemMap
						.values().iterator();
				for (; iterator.hasNext();) {
					CheckBoxListItem checkBoxListItem = iterator.next();
					checkBoxListItem.setIsSelected(false);
				}
				//
				// 重新设置其显示文本值
				//
				tf.setText(tempText);
			}
		});

	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("导入原态", null, getPnBefore(), null);
			jTabbedPane.addTab("折算料件", null, getPnAfter(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							JTabbedPane jTabPane = (JTabbedPane) e.getSource();
							if (jTabPane.getSelectedIndex() == 0) {
								isAfter = false;
								setSate();

							} else {
								isAfter = true;
								setSate();
							}
							// fillSeachConditions();
							// getPnCommonQueryPage().setInitState();

						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * 设置状态
	 */
	private void setSate() {
		btnImport.setEnabled(!isAfter);
		btnConvertToBom.setEnabled(!isAfter);
		btnEdit.setEnabled(!isAfter);
		jLabel4.setVisible(!isAfter);
		cbbWlType.setVisible(!isAfter);
		// btnDel.setEnabled(!isAfter);
		btnCalculate.setEnabled(!isAfter);
	}

	/**
	 * This method initializes pnBefore
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBefore() {
		if (pnBefore == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			pnBefore = new JPanel();
			// pnBefore.setLayout(new GridBagLayout());
			// pnBefore.add(getJScrollPane(), gridBagConstraints);
			pnBefore.setLayout(new BorderLayout());
			pnBefore.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return pnBefore;
	}

	/**
	 * This method initializes pnAfter
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnAfter() {
		if (pnAfter == null) {
			// GridBagConstraints gridBagConstraints1 = new
			// GridBagConstraints();
			// gridBagConstraints1.fill = GridBagConstraints.BOTH;
			// gridBagConstraints1.weighty = 1.0;
			// gridBagConstraints1.weightx = 1.0;
			pnAfter = new JPanel();
			// pnAfter.setLayout(new GridBagLayout());
			// pnAfter.add(getJScrollPane1(), gridBagConstraints1);
			pnAfter.setLayout(new BorderLayout());
			pnAfter.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return pnAfter;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbConvertMateriel());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbConvertMateriel
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbConvertMateriel() {
		if (tbConvertMateriel == null) {
			tbConvertMateriel = new MultiSpanCellTable() {
				protected JTableHeader createDefaultTableHeader() {
					return new GroupableTableHeader(columnModel);
				}
			};
		}
		return tbConvertMateriel;
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
			jPanel1.add(getJPanel(), BorderLayout.CENTER);
			jPanel1.add(getPnCommonQueryPage(), BorderLayout.SOUTH);
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {

		if (btnQuery == null) {

			btnQuery = new JButton();

			btnQuery.setBounds(new Rectangle(674, 54, 88, 25));

			btnQuery.setText("查询");

			btnQuery.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					// 日期是一定要选择的
					if (cbbBeginDate.getDate() == null
							|| cbbEndDate.getDate() == null) {

						JOptionPane.showMessageDialog(FmCheckBalance.this,
								"请选择查询日期！", "提示", 0);

						return;
					}
					new SearchThread().start();
				}

			});
		}
		return btnQuery;
	}

	/**
	 * 查询
	 * 
	 * @author wss
	 * 
	 */

	class SearchThread extends Thread {
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();

			final String flag = uuid.toString();

			btnQuery.setEnabled(false);

			try {

				CommonProgress.showProgressDialog(flag, FmCheckBalance.this);

				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");

				fillSeachConditions();

				getPnCommonQueryPage().setInitState();

				CommonProgress.closeProgressDialog(flag);

			} catch (Exception e) {

				CommonProgress.closeProgressDialog(flag);

				JOptionPane.showMessageDialog(FmCheckBalance.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);

			}

			btnQuery.setEnabled(true);
		}
	}

	// /**
	// * 查询条件
	// */
	// public List<Condition> getConditions(){
	// List<Condition> conditions = new ArrayList<Condition>();
	//
	//
	// //日期
	// Date beginDate = cbbBeginDate.getDate();
	// Date endDate = cbbEndDate.getDate();
	//
	// conditions.add(new Condition("and", null,"checkDate", ">=",beginDate,
	// null));
	// conditions.add(new Condition("and", null,"checkDate", "<=",endDate,
	// null));
	//
	// //库存类型
	//
	// if(cbbKcType.getSelectedItem() != null){
	// String kcType = ((ItemProperty)cbbKcType.getSelectedItem()).getCode();
	// conditions.add(new Condition("and", null,"kcType", "=",kcType, null));
	// }
	//
	//
	// //料号
	// if(!tfPtNo.getText().trim().equals("")){
	// String ptNo= tfPtNo.getText();
	// conditions.add(new Condition("and", null,"ptNo", "=",ptNo, null));
	// }
	//
	//
	// //物料类型
	//
	// if(cbbWlType.getSelectedItem() != null){
	// String wlType = ((ItemProperty)cbbWlType.getSelectedItem()).getCode();
	//
	// conditions.add(new Condition("and", null,"ljCpMark", "=",wlType, null));
	// }
	//
	//
	// // 仓库
	// List<CheckableItem> checkableItemList = new ArrayList<CheckableItem>();
	// // List<String> checkableCodeList = new ArrayList<String>();
	// for (int j = 0; j < lsFactory.getModel().getSize(); j++) {
	// Object value = lsFactory.getModel().getElementAt(j);
	// if (value instanceof CheckableItem) {
	// CheckableItem item = (CheckableItem) value;
	// if (item.isSelected) {
	// checkableItemList.add(item);
	// // checkableCodeList.add(item.getCode());
	// }
	// }
	// }
	//
	// for (int j = 0; j < checkableItemList.size(); j++) {
	// CheckableItem item = checkableItemList.get(j);
	// Condition condition = null;
	// if (j == 0) {
	// condition = new Condition("and", "(",
	// "wareSet.code", "=", item.getCode().trim(),
	// null);
	// } else {
	// condition = new Condition("or", null,
	// "wareSet.code", "=", item.getCode().trim(),
	// null);
	// }
	// if (j == checkableItemList.size() - 1) {
	// condition.setEndBracket(")");
	// }
	// conditions.add(condition);
	// }
	// return conditions;
	// }
	//

	/**
	 * 仓库
	 * 
	 * @return
	 */
	private JList getLsFactory() {
		if (lsFactory == null) {
			lsFactory = new JList();
			lsFactory.setCellRenderer(new CheckListRenderer());
			lsFactory.setFixedCellHeight(18);
			lsFactory.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int index = lsFactory.locationToIndex(e.getPoint());
					Object obj = lsFactory.getModel().getElementAt(index);
					if (obj instanceof CheckableItemExtra) {
						CheckableItemExtra item = (CheckableItemExtra) obj;
						item.setSelected(!item.isSelected());
						if (item.isSelected()) {
							item.setName(SELECT_NOT_ALL);
						} else {
							item.setName(SELECT_ALL);
						}
						ListModel model = lsFactory.getModel();
						for (int i = 0; i < model.getSize(); i++) {
							Object o = model.getElementAt(i);
							if (o instanceof CheckableItem) {
								CheckableItem c = (CheckableItem) o;
								c.setSelected(item.isSelected());
							}
						}
						lsFactory.repaint();
					} else if (obj instanceof CheckableItem) {
						CheckableItem item = (CheckableItem) obj;
						item.setSelected(!item.isSelected());
						Rectangle rect = lsFactory.getCellBounds(index, index);
						lsFactory.repaint(rect);
					}
				}
			});
		}
		return lsFactory;
	}

	/**
	 * 列表框呈现类（此处用于仓库选择）
	 * 
	 * @author Administrator
	 */
	class CheckListRenderer extends JCheckBox implements ListCellRenderer {

		public CheckListRenderer() {
			setBackground(UIManager.getColor("List.textBackground"));
			setForeground(UIManager.getColor("List.textForeground"));
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus) {
			setEnabled(list.isEnabled());
			if (value instanceof CheckableItemExtra) {
				CheckableItemExtra item = (CheckableItemExtra) value;
				setSelected(item.isSelected());
				setSize(350, 17);
				setFont(new Font(getFont().getName(), Font.BOLD, getFont()
						.getSize()));
				setText("  " + item.getName());
			} else if (value instanceof CheckableItem) {
				CheckableItem item = (CheckableItem) value;
				setSelected(item.isSelected());
				setSize(350, 17);
				setFont(new Font(getFont().getName(), Font.PLAIN, getFont()
						.getSize()));
				setText("  " + item.getCode() + " (" + item.getName() + ")");
			}
			return this;
		}
	}

	/**
	 * 列表框中的选项类（此处用于仓库选择）
	 * 
	 * @author Administrator
	 */
	class CheckableItem {
		private String code;

		private String name;

		private boolean isSelected;

		public CheckableItem(String str, String name) {
			this.code = str;
			this.name = name;
			isSelected = false;
		}

		public void setSelected(boolean b) {
			isSelected = b;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return name + " (" + code + ")";
		}
	}

	/**
	 * 用于列表框中项中的选择
	 */
	class CheckableItemExtra {

		private boolean isSelected;

		private String name;

		public CheckableItemExtra(String name) {
			this.name = name;
			isSelected = false;
		}

		public void setSelected(boolean b) {
			isSelected = b;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBounds(new Rectangle(12, 25, 182, 57));
			jScrollPane2.setViewportView(getLsFactory());
		}
		return jScrollPane2;
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
			btnClose.setPreferredSize(new Dimension(60, 30));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmCheckBalance.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes btnDelSelected
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelSelected() {
		if (btnDelSelected == null) {
			btnDelSelected = new JButton();
			btnDelSelected.setText("删除");
			btnDelSelected.setPreferredSize(new Dimension(60, 30));
			btnDelSelected
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							casBalanceReportAction
									.checkBalanceByDelete(new Request(
											CommonVars.getCurrUser()));
							if ((isAfter && tableModelAfter.getCurrentRows() == null)
									|| (!isAfter && tableModel.getCurrentRows() == null)) {

								JOptionPane.showMessageDialog(
										FmCheckBalance.this, "请选择你要删除的记录",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (!isAfter) {
								List ls = tableModel.getCurrentRows();
								if (ls.size() == 0) {
									return;
								}
								casAction.deleteCheckBalance(new Request(
										CommonVars.getCurrUser()), ls);
								tableModel.deleteRows(ls);

							} else {
								List ls = tableModelAfter.getCurrentRows();

								casAction.deleteCheckBalanceConvertMateriel(
										new Request(CommonVars.getCurrUser()),
										ls);

								tableModelAfter.deleteRows(ls);
							}
						}
					});
		}
		return btnDelSelected;
	}

	/**
	 * This method initializes btnFindAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFindAll() {
		if (btnFindAll == null) {
			btnFindAll = new JButton();
			btnFindAll.setText("显示全部");
			btnFindAll.setPreferredSize(new Dimension(64, 30));
			btnFindAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 日期是一定要选择的
					if (cbbBeginDate.getDate() == null
							|| cbbEndDate.getDate() == null) {
						JOptionPane.showMessageDialog(FmCheckBalance.this,
								"您必须选择时间！", "注意啦！！！", 0);
						return;
					}
					new FindAllThread().start();

				}
			});
		}
		return btnFindAll;
	}

	/**
	 * 显示全部
	 * 
	 * @author wss
	 * 
	 */
	class FindAllThread extends Thread {

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			btnFindAll.setEnabled(false);
			try {
				CommonProgress.showProgressDialog(flag, FmCheckBalance.this);
				CommonProgress.setMessage(flag, "正在获取，请稍后...");

				fillSeachConditions();

				List list = new ArrayList();

				if (!isAfter) {
					list = casAction.findCheckBalance(-1, -1, new Request(
							CommonVars.getCurrUser()), beginDate, endDate,
							kcType, ptNo, wlType, lsWareSetCode);
					initTable(list);

				} else {
					list = casAction.findCheckBalanceConvertMateriel(-1, -1,
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate, kcType, ptNo, wlType, lsWareSetCode);
					initTableConvertM(list);
				}

				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmCheckBalance.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				btnFindAll.setEnabled(true);
			}

		}
	}

} // @jve:decl-index=0:visual-constraint="42,0"
