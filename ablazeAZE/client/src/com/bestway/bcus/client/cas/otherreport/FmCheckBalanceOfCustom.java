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
import com.bestway.bcus.cas.entity.CheckBalanceOfCustom;
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
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.jptds.client.common.PnMutiConditionQueryPage;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;

/**
 * 盘点导入（编码级别）
 * 
 * @author Administrator check by hcl 2011-04-07
 */
public class FmCheckBalanceOfCustom extends JInternalFrameBase {
	/**
	 * jSplitPane面板
	 */
	private JSplitPane jSplitPane = null;
	/**
	 * 静态变量：全选
	 */
	private static String SELECT_ALL = "全选"; // @jve:decl-index=0:
	/**
	 * 静态变量：不选
	 */
	private static String SELECT_NOT_ALL = "不选"; // @jve:decl-index=0:
	/**
	 * 滚动面板
	 */
	private JScrollPane jScrollPane = null;
	/**
	 * 表格
	 */
	private JTable jTable = null;
	/**
	 * 菜单Bar
	 */
	private JToolBar jToolBar = null;
	/**
	 * 导入按钮
	 */
	private JButton btnImport = null;
	/**
	 * 修改按钮
	 */
	private JButton btnEdit = null;
	/**
	 * 删除所有按钮
	 */
	private JButton btnDelAll = null;

	/**
	 * 导入原态tableModel
	 */
	private JTableListModel tableModel = null;

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
	/**
	 * 刷新按钮
	 */
	private JButton btnRefresh = null;

	// private List dataSource = null; // @jve:decl-index=0:
	/**
	 * jSplitPane1面板
	 */
	private JSplitPane jSplitPane1 = null;
	/**
	 * jPanel面板
	 */
	private JPanel jPanel = null;
	/**
	 * 打印按钮
	 */
	private JButton btnPrint = null;

	/**
	 * 开始日期选择框
	 */
	private JCalendarComboBox cbbBeginDate = null;
	/**
	 * 结束日期选择框
	 */
	private JCalendarComboBox cbbEndDate = null;

	/**
	 * 分页查询组件
	 */
	private PnMutiConditionQueryPage pnCommonQueryPage = null;
	/**
	 * 标签
	 */
	private JLabel jLabel = null;
	/**
	 * 标签1
	 */
	private JLabel jLabel1 = null;
	/**
	 * 2
	 */
	private JLabel jLabel2 = null;
	/**
	 * 库存类别选择框
	 */
	private JComboBox cbbKcType = null;
	/**
	 * 面板1
	 */
	private JPanel jPanel1 = null;
	/**
	 * 查询按钮
	 */
	private JButton btnQuery = null;
	/**
	 * 关闭按钮
	 */
	private JButton btnClose = null;
	/**
	 * 删除按钮
	 */
	private JButton btnDelSelected = null;

	// /**
	// * 查询条件
	// */
	// private List<Condition> conditions;
	/**
	 * 查询条件，因为同时几个地方用到
	 */
	/**
	 * 开始日期
	 */
	private Date beginDate;
	/**
	 * 结束日期
	 */
	private Date endDate;
	/**
	 * 库存类别
	 */
	private String kcType;
	/**
	 * 报关名称
	 */
	private String name;
	/**
	 * 报关规格
	 */
	private String spec;
	/**
	 * 单位
	 */
	private String unitName;
	/**
	 * 查询全部按钮
	 */
	private JButton btnFindAll = null;
	/**
	 * 面板2
	 */
	private JPanel jPanel2 = null;
	/**
	 * 标签
	 */
	private JLabel lbName = null;
	/**
	 * 标签
	 */
	private JLabel lbSpecs = null;
	/**
	 * 名称输入框
	 */
	private JTextField tfName = null;
	/**
	 * 规格输入框
	 */
	private JTextField tfSpec = null;
	/**
	 * 单位输入框
	 */
	private JTextField tfUnit = null;
	/**
	 * 标签
	 */
	private JLabel jLabel3 = null;

	/**
	 * 构造方法
	 * 
	 */
	public FmCheckBalanceOfCustom() {
		super();
		/**
		 * 权限接口
		 */
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
		FmCheckBalanceOfCustom.this.initTable(new ArrayList());
		fillSeachConditions();
		// getData();
		// List list = casAction.findCheckBalance(new
		// Request(CommonVars.getCurrUser()));
		// initTable(list);
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

		// //库存类别
		// this.cbbKcType.removeAllItems();
		// this.cbbKcType.addItem(new ItemProperty("0", "料件库存"));
		// this.cbbKcType.addItem(new ItemProperty("1", "成品库存"));
		// this.cbbKcType.addItem(new ItemProperty("2", "在产品库存"));
		// this.cbbKcType.addItem(new ItemProperty("4", "外发未收回"));
		// this.cbbKcType.addItem(new ItemProperty("5", "残次品库存"));
		// this.cbbKcType.addItem(new ItemProperty("6", "非保税料"));

		// 库存类别
		this.cbbKcType.removeAllItems();
		this.cbbKcType.addItem(new ItemProperty("0", "料件库存"));
		this.cbbKcType.addItem(new ItemProperty("1", "成品库存"));
		this.cbbKcType.addItem(new ItemProperty("2", "在产品库存"));
		this.cbbKcType.addItem(new ItemProperty("4", "外发未收回"));
		this.cbbKcType.addItem(new ItemProperty("5", "残次品库存"));
		this.cbbKcType.addItem(new ItemProperty("6", "边角料库存"));

		initComboBoxRenderer(cbbKcType);

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
	 * 初始化数据
	 * 
	 */
	private void initialize() {
		this.setTitle("盘点平衡表查询(编码级)");
		this.setContentPane(getJSplitPane());
		this.setSize(739, 541);
	}

	/**
	 * 获取jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJSplitPane1());
			jSplitPane.setDividerLocation(165);
			jSplitPane.setBottomComponent(getJPanel2());
			jSplitPane.setEnabled(false);

		}
		return jSplitPane;
	}

	/**
	 * 获取 jScrollPane
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
	 * 获取jTable
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
	 * 获取 jToolBar
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
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelSelected());
			jToolBar.add(getBtnDelAll());
			// 打印功能暂时不做
			// jToolBar.add(getBtnPrint());
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
			btnImport.setText("导入盘点数据");
			btnImport.setPreferredSize(new Dimension(90, 30));
			btnImport.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					casBalanceReportAction.checkBalanceOfCustomByImport(new Request(CommonVars.getCurrUser()));
					DgCheckBalanceOfCustomImport dg = new DgCheckBalanceOfCustomImport();
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
						JOptionPane.showMessageDialog(
								FmCheckBalanceOfCustom.this, "请选择你要修改的数据!",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CheckBalanceOfCustom balance = (CheckBalanceOfCustom) tableModel
							.getCurrentRow();
					DgCheckBalanceOfCustomEdit dg = new DgCheckBalanceOfCustomEdit(
							balance);
					dg.setVisible(true);
					tableModel.updateRow(balance);
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
			btnDelAll.setPreferredSize(new Dimension(60, 30));
			btnDelAll.setToolTipText("删除当前【查询条件】下的所有数据");
			btnDelAll.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					casBalanceReportAction.checkBalanceOfCustomByDelete(new Request(CommonVars.getCurrUser()));
					// 日期是一定要选择的,否则很可能会误删除数据
					if (cbbBeginDate.getDate() == null
							|| cbbEndDate.getDate() == null) {
						JOptionPane.showMessageDialog(
								FmCheckBalanceOfCustom.this, "您必须选择时间！",
								"注意啦！！！", 0);
						return;
					}

					if (JOptionPane.showConfirmDialog(
							FmCheckBalanceOfCustom.this,
							"确定要删除   【当前查询条件下】  的所有记录吗？？", "警告!!!",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

						new DeleteThread().start();//

					}
				}

			});
		}
		return btnDelAll;
	}

	/**
	 * 删除 （一起删）
	 * 
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
				casAction.deleteAllCheckBalanceOfCustom(new Request(CommonVars
						.getCurrUser()), beginDate, endDate, kcType, name,
						spec, unitName);
				initTable(new ArrayList());

				CommonProgress.closeProgressDialog();

				long endTime = System.currentTimeMillis();

				info += " 任务完成,共用时:" + (endTime - beginTime) / (1000 * 60)
						+ "分 " + (endTime - beginTime) / 1000 % 60 + "秒";
				JOptionPane.showMessageDialog(FmCheckBalanceOfCustom.this,
						info, "提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmCheckBalanceOfCustom.this,
						"删除失败！！！" + e.getMessage(), "提示", 2);
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
	 */
	class DeleteThreadOneByOne extends Thread {
		public void run() {

			try {

				CommonStepProgress.showStepProgressDialog(null);

				List ls = tableModel.getList();
				CommonStepProgress.setStepProgressMaximum(ls.size());
				int size = ls.size();
				for (int i = 0; i < size; i++) {
					CheckBalance data = (CheckBalance) ls.get(i);
					casAction.deleteCheckBalanceOneByOne(new Request(CommonVars
							.getCurrUser()), data);

					CommonStepProgress.setStepMessage("正在删除:第 " + i + "条,总共"
							+ size + "条");
					CommonStepProgress.setStepProgressValue(i + 1);
				}
				initTable(new ArrayList());

				CommonStepProgress.closeStepProgressDialog();

			} catch (Exception e) {
				e.printStackTrace();
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmCheckBalanceOfCustom.this,
						"删除失败！！！" + e.getMessage(), "提示", 2);
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
						list.add(addColumn("报关商品名称", "name", 100));
						list.add(addColumn("报关商品规格", "spec", 100));
						list.add(addColumn("报关商品单位", "unitName", 100));
						list.add(addColumn("数量", "checkAmount", 100));
						list.add(addColumn("仓库名称", "wareSet.name", 100));
						list.add(addColumn("库存类别", "kcType", 100));
						return list;
					}
				});

		jTable.getColumnModel().getColumn(7).setCellRenderer(
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
							returnValue = "料件库存";
						} else if (value.equals("1")) {
							returnValue = "成品库存";
						} else if (value.equals("2")) {
							returnValue = "在产品库存";
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

	/**
	 * 获取jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setBottomComponent(getJToolBar());
			jSplitPane1.setDividerSize(0);
			jSplitPane1.setTopComponent(getJPanel1());
			jSplitPane1.setDividerLocation(130);
			jSplitPane1.setEnabled(false);

		}
		return jSplitPane1;
	}

	/**
	 * 获取 jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(400, 68, 50, 18));
			jLabel3.setText("单位");
			lbSpecs = new JLabel();
			lbSpecs.setBounds(new Rectangle(208, 70, 48, 18));
			lbSpecs.setText("商品规格");
			lbName = new JLabel();
			lbName.setBounds(new Rectangle(17, 70, 48, 18));
			lbName.setText("商品名称");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(400, 32, 50, 18));
			jLabel2.setText("库存类别");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(17, 32, 48, 18));
			jLabel.setText("开始日期");
			jPanel = new JPanel();
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "查询条件",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel.setLayout(null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getJLabel1(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbKcType(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(lbName, null);
			jPanel.add(lbSpecs, null);
			jPanel.add(getTfName(), null);
			jPanel.add(getTfSpec(), null);
			jPanel.add(getTfUnit(), null);
			jPanel.add(jLabel3, null);
		}
		return jPanel;
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
	 * 获取 btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
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
	 * 获取 jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(76, 32, 117, 25));
		}
		return cbbBeginDate;
	}

	/**
	 * 获取jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(262, 32, 117, 25));
		}
		return cbbEndDate;
	}

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
					return FmCheckBalanceOfCustom.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length) {

					list = casAction.findCheckBalanceOfCustom(index, length,
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate, kcType, name, spec, unitName);
					System.out.println("wss list.size = " + list.size());
					return list;

				}

				@Override
				protected Long getDataSourceCount() {
					int count = 0;

					count = casAction.findCheckBalanceCountOfCustom(
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate, kcType, name, spec, unitName);

					System.out.println("wss count = " + count);
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
		name = tfName.getText() == null ? "" : tfName.getText();
		spec = tfSpec.getText() == null ? "" : tfSpec.getText();
		unitName = tfUnit.getText() == null ? "" : tfUnit.getText();

	}

	/**
	 * 获取 jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("结束日期");
			jLabel1.setBounds(new Rectangle(208, 32, 48, 18));
		}
		return jLabel1;
	}

	/**
	 * 获取 cbbKcType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbKcType() {
		if (cbbKcType == null) {
			cbbKcType = new JComboBox();
			cbbKcType.setBounds(new Rectangle(456, 32, 117, 25));
		}
		return cbbKcType;
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
	 * 设置状态
	 */
	private void setSate() {
		// btnDel.setEnabled(!isAfter);
	}

	/**
	 * 获取 jPanel1
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
	 * 获取 btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(577, 70, 88, 25));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 日期是一定要选择的
					if (cbbBeginDate.getDate() == null
							|| cbbEndDate.getDate() == null) {
						JOptionPane.showMessageDialog(
								FmCheckBalanceOfCustom.this, "您必须选择时间！",
								"注意啦！！！", 0);
						return;
					}
					new SearchThread().start();
				}

			});
		}
		return btnQuery;
	}

	/**
	 * 查询线程
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
				CommonProgress.showProgressDialog(flag,
						FmCheckBalanceOfCustom.this);
				// CommonProgress.showProgressDialog(flag, owner)
				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");

				fillSeachConditions();
				getPnCommonQueryPage().setInitState();

				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmCheckBalanceOfCustom.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			}
			btnQuery.setEnabled(true);
		}
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
		/**
		 * 仓库代码
		 */
		private String code;
		/**
		 * 仓库名称
		 */
		private String name;
		/**
		 * 是否被选择
		 */
		private boolean isSelected;

		/**
		 * 检查被选择方法
		 */
		public CheckableItem(String str, String name) {
			this.code = str;
			this.name = name;
			isSelected = false;
		}

		/**
		 * 设置被选择
		 */
		public void setSelected(boolean b) {
			isSelected = b;
		}

		/**
		 * 获取是否被选择
		 */
		public boolean isSelected() {
			return isSelected;
		}

		/**
		 * 获取仓库代码
		 */
		public String getCode() {
			return code;
		}

		/**
		 * 设置仓库代码
		 */
		public void setCode(String code) {
			this.code = code;
		}

		/**
		 * 获取仓库名称
		 */
		public String getName() {
			return name;
		}

		/**
		 * 设置仓库名称
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 获取名称+代码
		 */
		public String toString() {
			return name + " (" + code + ")";
		}
	}

	/**
	 * 用于列表框中项中的选择
	 */
	class CheckableItemExtra {
		/**
		 * 是否被选择
		 */
		private boolean isSelected;
		/**
		 * 名称
		 */
		private String name;

		/**
		 * 检查被选择
		 */
		public CheckableItemExtra(String name) {
			this.name = name;
			isSelected = false;
		}

		/**
		 * 设置被选择
		 */
		public void setSelected(boolean b) {
			isSelected = b;
		}

		/**
		 * 是否被选择
		 */
		public boolean isSelected() {
			return isSelected;
		}

		/**
		 * 获取名称
		 */
		public String getName() {
			return name;
		}

		/**
		 * 设置名称
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 获取名称
		 */
		public String toString() {
			return name;
		}
	}

	/**
	 * 获取 btnClose
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
					FmCheckBalanceOfCustom.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * 获取 btnDelSelected
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
							casBalanceReportAction.checkBalanceOfCustomByDelete(new Request(CommonVars.getCurrUser()));
							if (tableModel.getCurrentRows() == null) {

								JOptionPane.showMessageDialog(
										FmCheckBalanceOfCustom.this,
										"请选择你要删除的记录", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							List ls = tableModel.getCurrentRows();
							if (ls.size() == 0) {
								return;
							}
							casAction.deleteCheckBalanceOfCustom(new Request(
									CommonVars.getCurrUser()), ls);
							tableModel.deleteRows(ls);
						}
					});
		}
		return btnDelSelected;
	}

	/**
	 * 获取 btnFindAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFindAll() {
		if (btnFindAll == null) {
			btnFindAll = new JButton();
			btnFindAll.setText("显示全部");
			btnFindAll.setPreferredSize(new Dimension(60, 30));
			btnFindAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 日期是一定要选择的
					if (cbbBeginDate.getDate() == null
							|| cbbEndDate.getDate() == null) {
						JOptionPane.showMessageDialog(
								FmCheckBalanceOfCustom.this, "您必须选择时间！",
								"注意啦！！！", 0);
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
				CommonProgress.showProgressDialog(flag,
						FmCheckBalanceOfCustom.this);
				CommonProgress.setMessage(flag, "正在获取，请稍后...");

				fillSeachConditions();

				List list = new ArrayList();

				list = casAction.findCheckBalanceOfCustom(-1, -1, new Request(
						CommonVars.getCurrUser()), beginDate, endDate, kcType,
						name, spec, unitName);
				initTable(list);
				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmCheckBalanceOfCustom.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				btnFindAll.setEnabled(true);
			}

		}
	}

	/**
	 * 获取 jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(getJScrollPane(), gridBagConstraints);
		}
		return jPanel2;
	}

	/**
	 * 获取 tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(76, 70, 117, 25));
		}
		return tfName;
	}

	/**
	 * 获取 tfSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setBounds(new Rectangle(262, 70, 117, 25));
		}
		return tfSpec;
	}

	/**
	 * 获取tfUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfUnit() {
		if (tfUnit == null) {
			tfUnit = new JTextField();
			tfUnit.setBounds(new Rectangle(457, 70, 117, 25));
		}
		return tfUnit;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
