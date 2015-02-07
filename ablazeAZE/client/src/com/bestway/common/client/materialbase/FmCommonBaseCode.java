/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Parame;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.CurrCode;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.common.materialbase.entity.CustomsUser;
import com.bestway.common.materialbase.entity.MotorCode;
import com.bestway.common.materialbase.entity.ParaSet;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.common.materialbase.entity.ShareCode;
import com.bestway.common.materialbase.entity.SysArea;
import com.bestway.common.materialbase.entity.UnitCollate;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 *  物流通用代码管理 陈井彬
 *  2008-09-09 checked
 */
@SuppressWarnings("unchecked")
public class FmCommonBaseCode extends JInternalFrameBase {
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel jContentPane = null; // @jve:decl-index=0:visual-constraint="26,105"
	private JPanel jPanel = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel1 = null;
	/**
	 * 工厂通用代码树
	 */
	private JTree jTree = null;
	private JPanel jPanel2 = null;
	private JToolBar jToolBar = null;
	/**
	 * 新增按钮
	 */
	private JButton btnAdd = null;
	/**
	 * 修改按钮
	 */
	private JButton btnEdit = null;
	/**
	 * 删除按钮
	 */
	private JButton btnDelete = null;
	/**
	 * 通用代码操作表格
	 */
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	/**
	 * 料件参数业务逻辑操作接口,远程逻辑方法调用
	 */
	private MaterialManageAction materialManageAction = null;
	/**
	 * o:仓库设置 1: 计量单位2:币别代码3: 国家代码 4:客户/供应商/合作伙伴资料 5:物料类别 6:税制代码
	 * 7:司机资料设置,8:报关员,9:参数对应,10:事业部设置,11单位折算对照表
	 */
	private int selectedIndex = 0;
	/**
	 * 树模型
	 */
	private DefaultTreeModel model;
	/**
	 * 表格模型
	 */
	private JTableListModel tableModel = null;
	/**
	 * 关闭按钮
	 */
	private JButton btnClose = null;
	/**
	 * 更新报关单汇率
	 */
	private JButton btnRefresh = null;
	/**
	 * 导入按钮
	 */
	private JButton btnLoad = null;

	// private JButton jRefreshTable = null;

	/**
	 * This is the default constructor
	 */
	public FmCommonBaseCode() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		materialManageAction.checkCommonBaseCodeAuthority(new Request(
				CommonVars.getCurrUser()));
		initialize();
	}

	/**
	 * 工厂仓库设置
	 */
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			if (jTree.getRowCount() >= 8) {
				TreePath treePath = jTree.getPathForRow(1);
				if (treePath != null) {
					jTree.setSelectionPath(treePath);
					jTree.scrollPathToVisible(treePath);
				}
			}
		}
		super.setVisible(isFlag);
	}

	/**
	 * 工厂汇率设置
	 * 
	 * @param isFlag
	 */
	public void setVisibleToCurrRate(boolean isFlag) {
		if (isFlag) {
			if (jTree.getRowCount() >= 8) {
				TreePath treePath = jTree.getPathForRow(8);
				if (treePath != null) {
					jTree.setSelectionPath(treePath);
					jTree.scrollPathToVisible(treePath);
				}
			}
		}
		super.setVisible(isFlag);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel());
		this.setSize(600, 400);
		this.setTitle("物流通用代码");
		this.setHelpId("commonBaseCode");
		this.validate();
		this.pack();
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
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			this.getJSplitPane().setResizeWeight(0.2);
			jSplitPane.setLeftComponent(getJPanel1());
			jSplitPane.setRightComponent(getJPanel2());
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJTree(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jTree 初始化树
	 * 
	 * @return javax.swing.JTree
	 * 
	 */
	private JTree getJTree() {
		if (jTree == null) {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("工厂通用代码");
			DefaultMutableTreeNode root_1 = new DefaultMutableTreeNode("工厂仓库设置");
			DefaultMutableTreeNode root_2 = new DefaultMutableTreeNode("工厂计量单位");
			DefaultMutableTreeNode root_3 = new DefaultMutableTreeNode("工厂币别代码");
			DefaultMutableTreeNode root_4 = new DefaultMutableTreeNode("国家代码");
			DefaultMutableTreeNode root_5 = new DefaultMutableTreeNode("客户供应商");
			DefaultMutableTreeNode root_6 = new DefaultMutableTreeNode("工厂物料类别");
			DefaultMutableTreeNode root_7 = new DefaultMutableTreeNode("工厂税制代码");
			DefaultMutableTreeNode root_8 = new DefaultMutableTreeNode("工厂汇率设置");
			DefaultMutableTreeNode root_9 = new DefaultMutableTreeNode("工厂司机资料");
			DefaultMutableTreeNode root_10 = new DefaultMutableTreeNode(
					"工厂报关员设置");
			DefaultMutableTreeNode root_11 = new DefaultMutableTreeNode(
					"工厂参数对应设置");
			DefaultMutableTreeNode root_12 = new DefaultMutableTreeNode("事业部设置");
			DefaultMutableTreeNode root_13 = new DefaultMutableTreeNode(
					"单位折算对照表");
			root.add(root_1);
			root.add(root_2);
			root.add(root_3);
			root.add(root_4);
			root.add(root_5);
			root.add(root_6);
			root.add(root_7);
			root.add(root_8);
			root.add(root_9);
			root.add(root_10);
			root.add(root_11);
			root.add(root_12);
			root.add(root_13);
			jTree = new JTree(root);
			jTree.setCellRenderer(new NodeRenderer());
			jTree
					.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
						public void valueChanged(
								javax.swing.event.TreeSelectionEvent e) {
							DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree
									.getLastSelectedPathComponent();
							if (selectedNode == null) {
								return;
							}
							DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode
									.getParent();
							if (parent != null) {
								int selectedIndex = parent
										.getIndex(selectedNode);
								setSelectedIndex(selectedIndex);
								getPage();
							}
						}
					});
		}
		return jTree;
	}

	/**
	 * 
	 * @author Administrator 返回一个树编辑器
	 * 
	 */
	class NodeRenderer extends DefaultTreeCellRenderer {
		public Component getTreeCellRendererComponent(JTree jTtree,
				Object value, boolean selected, boolean expanded, boolean leaf,
				int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(jTree, value, selected,
					expanded, leaf, row, hasFocus);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			if (node.getUserObject().toString().trim().equals("工厂汇率设置")) // 条件满足
			{
				setForeground(Color.BLUE);
			}
			return this;
		}
	}

	/**
	 * 
	 * This method initializes jPanel2 根据查询需求查询数据,并显示到表格中.
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	public void getPage() {
		List dataSource = null;
		if (this.getSelectedIndex() == 0) {
			dataSource = materialManageAction.findWareSet(new Request(
					CommonVars.getCurrUser())); // 0:仓库设置
		} else if (this.getSelectedIndex() == 1) {
			dataSource = materialManageAction.findCalUnit(new Request(
					CommonVars.getCurrUser())); // 1: 计量单位
		} else if (this.getSelectedIndex() == 2) {
			dataSource = materialManageAction.findCurrCode(new Request(
					CommonVars.getCurrUser())); // 2:币别代码
		} else if (this.getSelectedIndex() == 3) {
			dataSource = materialManageAction.findSysAreas(new Request(
					CommonVars.getCurrUser())); // 3: 国家代码
		} else if (this.getSelectedIndex() == 4) {
			dataSource = materialManageAction.findScmCocs(new Request(
					CommonVars.getCurrUser())); // 4:客户/供应商/合作伙伴资料
		} else if (this.getSelectedIndex() == 5) {
			dataSource = materialManageAction.findScmCois(new Request(
					CommonVars.getCurrUser())); // 5:物料类别
		} else if (this.getSelectedIndex() == 6) {
			dataSource = materialManageAction.findTaxaTion(new Request(
					CommonVars.getCurrUser())); // 6:税制代码
		} else if (this.getSelectedIndex() == 7) {
			dataSource = materialManageAction.findCurrRate(new Request(
					CommonVars.getCurrUser())); // 7:汇率设置
		} else if (this.getSelectedIndex() == 8) {
			dataSource = materialManageAction.findMotorCode(new Request(
					CommonVars.getCurrUser())); // 8:司机资料设置
		} else if (this.getSelectedIndex() == 9) {
			dataSource = materialManageAction.findCustomsUser(new Request(
					CommonVars.getCurrUser())); // 9:报关员设置
		} else if (this.getSelectedIndex() == 10) {
			dataSource = materialManageAction.findParaSet(new Request(
					CommonVars.getCurrUser())); // 10:参数对应设置
		} else if (this.getSelectedIndex() == 11) {
			dataSource = materialManageAction.findProjectDept(new Request(
					CommonVars.getCurrUser())); // 11:事业部设置
		} else if (this.getSelectedIndex() == 12) {
			dataSource = materialManageAction.findUnitCollate(new Request(
					CommonVars.getCurrUser())); // 12:单位折算对照表
		}
		initTable(dataSource, this.getSelectedIndex());
		btnRefresh.setVisible(this.getSelectedIndex() == 7);
		// jButton4.setVisible(false);
		btnLoad.setVisible(this.getSelectedIndex() == 4
				|| this.getSelectedIndex() == 8);
		// jRefreshTable.setVisible(this.getSelectedIndex()==8);
	}

	/**
	 * 初始化表格
	 */
	private void initTable(final List list, final int selectindexs) {
		if (selectindexs == 0) { // 仓库设置
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("仓库编号", "code", 100));
							list.add(addColumn("仓库名称", "name", 150));
							list.add(addColumn("仓库地址", "wareaddr", 200));
							list.add(addColumn("属性(保税 / 非保税)", "wareProperty", 200));
							// list.add(addColumn("联系电话", "waretel", 100));
							// list.add(addColumn("传真", "warefax", 100));
							// list.add(addColumn("E-Mail", "wareemail", 100));
							return list;
						}
					});
			jTable.getColumnModel().getColumn(4).setCellRenderer(
					new DefaultTableCellRenderer() {
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							super.setText((value == null) ? "" : castValue1(value));
							return this;
						}

						//"0"为保税，"1"为非保税
						private String castValue1(Object value) {
							String returnValue = "";
							if (String.valueOf(value).trim().equals("")) {
								return "";
							}
							if (value.equals("0")) {
								returnValue = "保税";
							} else if (value.equals("1")) {
								returnValue = "非保税";
							}
							return returnValue;
						}
					});
			
		} else if (selectindexs == 1) { // 计量单位
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("单位编码", "code", 100));
							list.add(addColumn("单位名称", "name", 100));
							// list.add(addColumn("关务计量单位", "unit.name", 100));
							// list.add(addColumn("单位对照比例因子", "scale", 100));
							return list;
						}
					});
		} else if (selectindexs == 2) { // 币别代码
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("币别编码", "code", 100));
							list.add(addColumn("币别名称", "name", 100));
							// list.add(addColumn("币别符号", "currSign", 100));
							// list.add(addColumn("关务币别", "curr.name", 100));
							return list;
						}
					});
		} else if (selectindexs == 3) { // 国家地区
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("国家区域编码", "code", 100));
							list.add(addColumn("国家区域名称", "name", 100));
							// list.add(addColumn("关务国家名称", "country.name",
							// 100));
							return list;
						}
					});
		} else if (selectindexs == 4) { // 客户，合作厂商，
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("编码(不可为空)", "code", 90));
							list.add(addColumn("全称", "name", 100));
							list.add(addColumn("关务海关注册公司", "brief.name", 150));
							list.add(addColumn("简称", "fname", 100));
							list.add(addColumn("是否客户", "isCustomer", 80));
							list.add(addColumn("是否供应商", "isManufacturer", 80));
							list.add(addColumn("是否合作者", "isCollaborater", 80));
							list.add(addColumn("是否经营单位", "isWork", 80));
							list.add(addColumn("是否结转出口",
									"isTransferFactoryOut", 80));
							list.add(addColumn("是否直接出口", "isStraightOut", 80));
							list.add(addColumn("是否结转进口", "isTransferFactoryIn",
									80));
							
							list.add(addColumn("是否直接进口", "isStraightIn", 80));
							list.add(addColumn("是否国内购买", "isHomeBuy", 80));
							list.add(addColumn("联系人","linkMan",60));
							list.add(addColumn("联系电话","linkTel",80));
							list.add(addColumn("传真号","fax",60));
							list.add(addColumn("地址","addre",100));
							list.add(addColumn("银行及账户","bak",100));
							
							list.add(addColumn("运抵国", "country.name", 80));
							list.add(addColumn("指运港", "portLin.name", 80));
							list.add(addColumn("产销国", "country2.name", 80));
							list.add(addColumn("出口口岸", "customs.name", 80));
							list.add(addColumn("所属海关", "belongToCustoms.name",
									80));
							list.add(addColumn("运输方式", "transf.name", 80));
							list
									.add(addColumn("运输时间",
											"transportationTime", 80));
							list.add(addColumn("码头", "predock.shortName", 80));
							list.add(addColumn("包装种类", "warp.name", 80));
							list.add(addColumn("贸易方式", "trade.tradeFname", 80));
							list.add(addColumn("送货距离", "deliveryDistance", 80));
							return list;
						}
					});
			jTable.getColumnModel().getColumn(5).setCellRenderer(
					new BooleanTableCellRenderer());
			jTable.getColumnModel().getColumn(6).setCellRenderer(
					new BooleanTableCellRenderer());
			jTable.getColumnModel().getColumn(7).setCellRenderer(
					new BooleanTableCellRenderer());
			jTable.getColumnModel().getColumn(8).setCellRenderer(
					new BooleanTableCellRenderer());
			jTable.getColumnModel().getColumn(9).setCellRenderer(
					new BooleanTableCellRenderer());
			jTable.getColumnModel().getColumn(10).setCellRenderer(
					new BooleanTableCellRenderer());
			jTable.getColumnModel().getColumn(11).setCellRenderer(
					new BooleanTableCellRenderer());
			jTable.getColumnModel().getColumn(12).setCellRenderer(
					new BooleanTableCellRenderer());
			jTable.getColumnModel().getColumn(13).setCellRenderer(
					new BooleanTableCellRenderer());
		} else if (selectindexs == 5) { // 物料类别
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("类别编码", "code", 100));
							list.add(addColumn("类别名称", "name", 100));
							list.add(addColumn("类别属性", "coiProperty", 100));
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
							super.setText((value == null) ? ""
									: castValue(value));
							return this;
						}

						private String castValue(Object value) {
							if (String.valueOf(value).trim().equals("")) {
								return "";
							}
							String s = String.valueOf(value).trim();
							if (s.equals(MaterielType.FINISHED_PRODUCT)) {
								return "成品";
							} else if (s
									.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
								return "半成品";
							} else if (s.equals(MaterielType.MATERIEL)) {
								return "料件";
							} else if (s.equals(MaterielType.MACHINE)) {
								return "设备";
							} else if (s.equals(MaterielType.REMAIN_MATERIEL)) {
								return "边角料";
							} else if (s.equals(MaterielType.BAD_PRODUCT)) {
								return "残次品";
							}
							return "";
						}
					});
		} else if (selectindexs == 6) { // 税制代码
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("税制编码", "code", 100));
							list.add(addColumn("税制名称", "name", 100));
							return list;
						}
					});
		} else if (selectindexs == 7) { // 汇率设置
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("币制(1)代码", "curr.code", 80));
							list.add(addColumn("币制(1)符号", "curr.currSymb", 80));
							list.add(addColumn("币制(2)代码", "curr1.code", 80));
							list
									.add(addColumn("币制(2)符号", "curr1.currSymb",
											80));
							list.add(addColumn("汇率", "rate", 80));
							list.add(addColumn("创建日期", "createDate", 80));
							return list;
						}
					});
		} else if (selectindexs == 8) { // 司机资料设置
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("代码(不可为空)", "code", 90));
							list.add(addColumn("司机名称(不可为空)", "name", 110));
							list.add(addColumn("国内车牌", "homeCard", 80));
							list.add(addColumn("香港车牌", "hongkongCard", 80));
							list.add(addColumn("司机本海关编码", "complex", 100));
							list.add(addColumn("IC卡", "icCard", 80));
							list.add(addColumn("结关口岸", "customsPort", 80));
							list.add(addColumn("运输单位", "trafficUnit", 80));
							list.add(addColumn("运输单位地址", "trafficUnitAdd", 80));
							list.add(addColumn("运输单位电话", "trafficUnitTel", 80));
							return list;
						}
					});
		} else if (selectindexs == 9) { // 报关员设置
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("报关员证编码", "code", 200));
							list.add(addColumn("报关员名称", "name", 200));
							list.add(addColumn("报关员电话", "tel", 200));
							return list;
						}
					});
		} else if (selectindexs == 10) { // 参数对应设置
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("类型", "type", 200));
							list.add(addColumn("原始值", "beginValue", 200));
							list.add(addColumn("对应值", "afterValue", 200));
							return list;
						}
					});
			jTable.getColumnModel().getColumn(1).setCellRenderer(
					new DefaultTableCellRenderer() {
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							super.setText((value == null) ? ""
									: castValue(value));
							return this;
						}

						private String castValue(Object value) {
							String s = String.valueOf(value);
							if (s.equals(String
									.valueOf(Parame.jizhuangxiangcode))) {
								return Parame.jizhuangxiangname;
							}
							return "";
						}
					});
		} else if (selectindexs == 11) { // 事业部设置
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("事业部代码", "code", 200));
							list.add(addColumn("事业部名称", "name", 200));
							list.add(addColumn("备注", "note", 300));
							return list;
						}
					});
		} else if (selectindexs == 12) { // 单位折算
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("1.单位(1)", "unitName", 200));
							list.add(addColumn("2.单位(2)", "unitName1", 200));
							list.add(addColumn("折算比率=1/2", "unitRate", 300));
							return list;
						}
					});
		}
	}

	/**
	 * 表格编辑器
	 * 
	 * @author Administrator
	 * 
	 */
	class BooleanTableCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			super.setText((value == null) ? "" : castValue(value));
			return this;
		}

		private String castValue(Object value) {
			if ((Boolean.valueOf(String.valueOf(value))).booleanValue()) {
				return "是";
			} else {
				return "否";
			}
		}
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel2.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getJButton4());
			jToolBar.add(getJButton5());
			// jToolBar.add(getJRefreshTable());
			jToolBar.add(getJButton3());
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
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setMnemonic(java.awt.event.KeyEvent.VK_A);
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					switch (FmCommonBaseCode.this.getSelectedIndex()) {
					case 0:
						DgWareSet dgWareSet = new DgWareSet();
						dgWareSet.setTableModel(tableModel);
						dgWareSet.setIsAdd(true);
						dgWareSet.setVisible(true);
						break;
					case 1:
						DgCalUnit dgCalUnit = new DgCalUnit();
						dgCalUnit.setTableModel(tableModel);
						dgCalUnit.setIsAdd(true);
						dgCalUnit.setVisible(true);
						break;
					case 2:
						DgCurrCode dgCurrCode = new DgCurrCode();
						dgCurrCode.setTableModel(tableModel);
						dgCurrCode.setIsAdd(true);
						dgCurrCode.setVisible(true);
						break;
					case 3:
						DgSysArea dgSysArea = new DgSysArea();
						dgSysArea.setTableModel(tableModel);
						dgSysArea.setAdd(true);
						dgSysArea.setVisible(true);
						break;
					case 4:
						DgScmCoc dgScmCoc = new DgScmCoc();
						dgScmCoc.setTableModel(tableModel);
						dgScmCoc.setAdd(true);
						dgScmCoc.setVisible(true);
						break;
					case 5:
						DgScmCoi dgScmCoi = new DgScmCoi();
						dgScmCoi.setTableModel(tableModel);
						dgScmCoi.setAdd(true);
						dgScmCoi.setVisible(true);
						break;
					case 6:
						DgShareCode dgShareCode = new DgShareCode();
						dgShareCode.setTableModel(tableModel);
						dgShareCode.setAdd(true);
						dgShareCode.setCodeType("taxation");
						dgShareCode.setVisible(true);
						break;
					case 7:
						DgCurrRate dg = new DgCurrRate();
						dg.setTableModel(tableModel);
						dg.setAdd(true);
						dg.setVisible(true);
						break;
					case 8:
						DgMotorCode motor = new DgMotorCode();
						motor.setTableModel(tableModel);
						motor.setAdd(true);
						motor.setVisible(true);
						break;
					case 9:
						DgCustomsUser obj = new DgCustomsUser();
						obj.setTableModel(tableModel);
						obj.setIsAdd(true);
						obj.setVisible(true);
						break;
					case 10:
						DgParaSet dgp = new DgParaSet();
						dgp.setTableModel(tableModel);
						dgp.setIsAdd(true);
						dgp.setVisible(true);
						break;
					case 11:
						DgProjectDept dgpro = new DgProjectDept();
						dgpro.setTableModel(tableModel);
						dgpro.setAdd(true);
						dgpro.setVisible(true);
						break;
					case 12:
						DgUnitCollate dgUnitCollate = new DgUnitCollate();
						dgUnitCollate.setTableModel(tableModel);
						dgUnitCollate.setIsAdd(true);
						dgUnitCollate.setVisible(true);
						break;
					default:
						break;
					}
				}
			});
		}
		return btnAdd;
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
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setMnemonic(java.awt.event.KeyEvent.VK_E);
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					updateTableRow();
				}
			});
		}
		return btnEdit;
	}

	/*
	 * 修改数据记录
	 */
	private void updateTableRow() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmCommonBaseCode.this, "请选择你要修改的资料",
					"确认", 1);
			return;
		}
		switch (this.getSelectedIndex()) {
		case 0:
			DgWareSet dgWareSet = new DgWareSet();
			dgWareSet.setIsAdd(false);
			dgWareSet.setTableModel(tableModel);
			dgWareSet.setVisible(true);
			break;
		case 1:
			DgCalUnit dgCalUnit = new DgCalUnit();
			dgCalUnit.setIsAdd(false);
			dgCalUnit.setTableModel(tableModel);
			dgCalUnit.setVisible(true);
			break;
		case 2:
			DgCurrCode dgCurrCode = new DgCurrCode();
			dgCurrCode.setIsAdd(false);
			dgCurrCode.setTableModel(tableModel);
			dgCurrCode.setVisible(true);
			break;
		case 3:
			DgSysArea dgSysArea = new DgSysArea();
			dgSysArea.setTableModel(tableModel);
			dgSysArea.setAdd(false);
			dgSysArea.setVisible(true);
			break;
		case 4:
			DgScmCoc dgScmCoc = new DgScmCoc();
			dgScmCoc.setTableModel(tableModel);
			dgScmCoc.setAdd(false);
			dgScmCoc.setVisible(true);
			break;
		case 5:
			DgScmCoi dgScmCoi = new DgScmCoi();
			dgScmCoi.setTableModel(tableModel);
			dgScmCoi.setAdd(false);
			dgScmCoi.setVisible(true);
			break;
		case 6:
			DgShareCode dgShareCode = new DgShareCode();
			dgShareCode.setTableModel(tableModel);
			dgShareCode.setAdd(false);
			dgShareCode.setCodeType("taxation");
			dgShareCode.setVisible(true);
			break;
		case 7:
			DgCurrRate dg = new DgCurrRate();
			dg.setTableModel(tableModel);
			dg.setAdd(false);
			dg.setVisible(true);
			break;
		case 8:
			DgMotorCode motor = new DgMotorCode();
			motor.setTableModel(tableModel);
			motor.setAdd(false);
			motor.setVisible(true);
			break;
		case 9:
			DgCustomsUser obj = new DgCustomsUser();
			obj.setTableModel(tableModel);
			obj.setIsAdd(false);
			obj.setVisible(true);
			break;
		case 10:
			DgParaSet dgp = new DgParaSet();
			dgp.setTableModel(tableModel);
			dgp.setIsAdd(false);
			dgp.setVisible(true);
			break;
		case 11:
			DgProjectDept dgpr = new DgProjectDept();
			dgpr.setTableModel(tableModel);
			dgpr.setAdd(false);
			dgpr.setVisible(true);
			break;
		case 12:
			DgUnitCollate dgUnitCollate = new DgUnitCollate();
			dgUnitCollate.setTableModel(tableModel);
			dgUnitCollate.setIsAdd(false);
			dgUnitCollate.setVisible(true);
			break;
		default:
			break;
		}
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
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setMnemonic(java.awt.event.KeyEvent.VK_D);
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCommonBaseCode.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}
					switch (FmCommonBaseCode.this.getSelectedIndex()) {
					case 0:
						WareSet ware = (WareSet) tableModel.getCurrentRow();
						if (JOptionPane.showConfirmDialog(
								FmCommonBaseCode.this, "你确定要删除此记录吗?", "确认", 0) == 0) {
							try {
								materialManageAction.deleteWare(new Request(
										CommonVars.getCurrUser()), ware);
								tableModel.deleteRow(ware);
							} catch (Exception r) {
								JOptionPane.showMessageDialog(
										FmCommonBaseCode.this,
										"该资料已被引用，您不可以删除！", "提示信息", 2);
							}
						}
						break;
					case 1:
						CalUnit unit = (CalUnit) tableModel.getCurrentRow();
						if (JOptionPane.showConfirmDialog(
								FmCommonBaseCode.this, "你确定要删除此记录吗?", "确认", 0) == 0) {
							try {
								materialManageAction.deleteUnit(new Request(
										CommonVars.getCurrUser()), unit);
								tableModel.deleteRow(unit);
							} catch (Exception r) {
								JOptionPane.showMessageDialog(
										FmCommonBaseCode.this,
										"该资料已被引用，您不可以删除！", "提示信息", 2);
							}
						}
						break;
					case 2:
						CurrCode curr = (CurrCode) tableModel.getCurrentRow();
						if (JOptionPane.showConfirmDialog(
								FmCommonBaseCode.this, "你确定要删除此记录吗?", "确认", 0) == 0) {
							try {
								materialManageAction.deleteCurr(new Request(
										CommonVars.getCurrUser()), curr);
								tableModel.deleteRow(curr);
							} catch (Exception r) {
								JOptionPane.showMessageDialog(
										FmCommonBaseCode.this,
										"该资料已被引用，您不可以删除！", "提示信息", 2);
							}
						}
						break;
					case 3:
						SysArea sysArea = (SysArea) tableModel.getCurrentRow();
						if (JOptionPane.showConfirmDialog(
								FmCommonBaseCode.this, "你确定要删除此记录吗?", "确认", 0) == 0) {
							try {
								materialManageAction.deleteSysArea(new Request(
										CommonVars.getCurrUser()), sysArea);
								tableModel.deleteRow(sysArea);
							} catch (Exception r) {
								JOptionPane.showMessageDialog(
										FmCommonBaseCode.this,
										"该资料已被引用，您不可以删除！", "提示信息", 2);
							}
						}
						break;
					case 4:
						ScmCoc scmCoc = (ScmCoc) tableModel.getCurrentRow();
						if (JOptionPane.showConfirmDialog(
								FmCommonBaseCode.this, "你确定要删除此记录吗?", "确认", 0) == 0) {
//							try {
							materialManageAction.deleteScmCoc(new Request(
									CommonVars.getCurrUser()), scmCoc);
							tableModel.deleteRow(scmCoc);
//							} catch (Exception r) {
//								JOptionPane.showMessageDialog(
//										FmCommonBaseCode.this,
//										"该资料已被引用，您不可以删除！", "提示信息", 2);
//							}
						}
						break;
					case 5:
						ScmCoi scmCoi = (ScmCoi) tableModel.getCurrentRow();
						if (JOptionPane.showConfirmDialog(
								FmCommonBaseCode.this, "你确定要删除此记录吗?", "确认", 0) == 0) {
							try {
								materialManageAction.deleteScmCoi(new Request(
										CommonVars.getCurrUser()), scmCoi);
								tableModel.deleteRow(scmCoi);
							} catch (Exception r) {
								JOptionPane.showMessageDialog(
										FmCommonBaseCode.this,
										"该资料已被引用，您不可以删除！", "提示信息", 2);
							}
						}
						break;
					case 6:
						ShareCode share = (ShareCode) tableModel
								.getCurrentRow();
						if (JOptionPane.showConfirmDialog(
								FmCommonBaseCode.this, "你确定要删除此记录吗?", "确认", 0) == 0) {
							try {
								materialManageAction.deleteShare(new Request(
										CommonVars.getCurrUser()), share);
								tableModel.deleteRow(share);
							} catch (Exception r) {
								JOptionPane.showMessageDialog(
										FmCommonBaseCode.this,
										"该资料已被引用，您不可以删除！", "提示信息", 2);
							}
						}
						break;
					case 7:
						CurrRate rate = (CurrRate) tableModel.getCurrentRow();
						if (JOptionPane.showConfirmDialog(
								FmCommonBaseCode.this, "你确定要删除此记录吗?", "确认", 0) == 0) {
							try {
								materialManageAction.deleteCurrRate(
										new Request(CommonVars.getCurrUser()),
										rate);
								tableModel.deleteRow(rate);
							} catch (Exception r) {
								JOptionPane.showMessageDialog(
										FmCommonBaseCode.this,
										"该资料已被引用，您不可以删除！", "提示信息", 2);
							}
						}
						break;
					case 8:
						MotorCode motor = (MotorCode) tableModel
								.getCurrentRow();
						if (JOptionPane.showConfirmDialog(
								FmCommonBaseCode.this, "你确定要删除此记录吗?", "确认", 0) == 0) {
							try {
								materialManageAction.deleteMotorCode(
										new Request(CommonVars.getCurrUser()),
										motor);
								tableModel.deleteRow(motor);
							} catch (Exception r) {
								JOptionPane.showMessageDialog(
										FmCommonBaseCode.this,
										"该资料已被引用，您不可以删除！", "提示信息", 2);
							}
						}
						break;
					case 9:
						CustomsUser obj = (CustomsUser) tableModel
								.getCurrentRow();
						if (JOptionPane.showConfirmDialog(
								FmCommonBaseCode.this, "你确定要删除此记录吗?", "确认", 0) == 0) {
							try {
								materialManageAction.deleteCustomsUser(
										new Request(CommonVars.getCurrUser()),
										obj);
								tableModel.deleteRow(obj);
							} catch (Exception r) {
								JOptionPane.showMessageDialog(
										FmCommonBaseCode.this,
										"该资料已被引用，您不可以删除！", "提示信息", 2);
							}
						}
						break;
					case 10:
						ParaSet paraSet = (ParaSet) tableModel.getCurrentRow();
						if (JOptionPane.showConfirmDialog(
								FmCommonBaseCode.this, "你确定要删除此记录吗?", "确认", 0) == 0) {
							try {
								materialManageAction.deleteParaSet(new Request(
										CommonVars.getCurrUser()), paraSet);
								tableModel.deleteRow(paraSet);
							} catch (Exception r) {
								JOptionPane.showMessageDialog(
										FmCommonBaseCode.this,
										"该资料已被引用，您不可以删除！", "提示信息", 2);
							}
						}
						break;
					case 11:
						ProjectDept proj = (ProjectDept) tableModel
								.getCurrentRow();
						if (JOptionPane.showConfirmDialog(
								FmCommonBaseCode.this, "你确定要删除此记录吗?", "确认", 0) == 0) {
							try {
								materialManageAction.deleteProjectDept(
										new Request(CommonVars.getCurrUser()),
										proj);
								tableModel.deleteRow(proj);
							} catch (Exception r) {
								JOptionPane.showMessageDialog(
										FmCommonBaseCode.this,
										"该资料已被引用，您不可以删除！", "提示信息", 2);
							}
						}
						break;
					case 12:
						UnitCollate unitCollate = (UnitCollate) tableModel
								.getCurrentRow();
						if (JOptionPane.showConfirmDialog(
								FmCommonBaseCode.this, "你确定要删除此记录吗?", "确认", 0) == 0) {
							try {
								materialManageAction.deleteUnitCollate(
										new Request(CommonVars.getCurrUser()),
										unitCollate);
								tableModel.deleteRow(unitCollate);
							} catch (Exception r) {
								JOptionPane.showMessageDialog(
										FmCommonBaseCode.this,
										"该资料已被引用，您不可以删除！", "提示信息", 2);
							}
						}
						break;
					default:
						break;
					}
				}
			});
		}
		return btnDelete;
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
			jTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						updateTableRow();
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
		}
		return jScrollPane;
	}

	/**
	 * @return Returns the selectedIndex.
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * @param selectedIndex
	 *            The selectedIndex to set.
	 */
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	/**
	 * @return Returns the model.
	 */
	public DefaultTreeModel getModel() {
		return model;
	}

	/**
	 * @param model
	 *            The model to set.
	 */
	public void setModel(DefaultTreeModel model) {
		this.model = model;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
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
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setMnemonic(java.awt.event.KeyEvent.VK_X);
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					close();
				}
			});
		}
		return btnClose;
	}

	/**
	 * 关闭窗体
	 * 
	 */
	protected void close() {
		JDialog frame = (JDialog) getComponent(this);
		if (frame != null) {
			frame.dispose();
		} else {
			dispose();
		}
	}

	private Component getComponent(Component component) {
		if (component instanceof JDialog) {
			return component;
		}
		if (component == null) {
			return null;
		}
		return getComponent(component.getParent());
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("更新报关单汇率");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null){
						JOptionPane.showMessageDialog(FmCommonBaseCode.this, "请选择你要更新的记录",
								"确认", 1);
						return;
					}
					Map maps = new HashMap();
					DgUpdateRateParaSet dg = new DgUpdateRateParaSet();
					dg.setCurrRateModel(tableModel);
					dg.setResult(maps);
					dg.setVisible(true);
					if (dg.isOk) {
						Date beginDate = (Date) maps.get("beginDate");
						Date endDate = (Date) maps.get("endDate");
						Boolean isRateNull = (Boolean) maps.get("isRateNull");
						new updateCustomsDeclarationRate(beginDate, endDate,
								isRateNull).start();
					}
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * 更新报关单汇率线程
	 * 
	 * @author Administrator
	 * 
	 */
	class updateCustomsDeclarationRate extends Thread {
		public updateCustomsDeclarationRate(Date beginDate, Date endDate,
				Boolean isRateNull) {
			super();
			this.beginDate = beginDate;
			this.endDate = endDate;
			this.isRateNull = isRateNull;
		}

		private Date beginDate = null;
		private Date endDate = null;
		private Boolean isRateNull = null;

		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在更新报关单汇率，请稍候.....");
				materialManageAction.updateCustomsCurrRate(new Request(
						CommonVars.getCurrUser()), beginDate, endDate,
						isRateNull);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmCommonBaseCode.this, "更新失败：！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (btnLoad == null) {
			btnLoad = new JButton();
			btnLoad.setText("导入");
			btnLoad.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					switch (FmCommonBaseCode.this.getSelectedIndex()) {
					case 4:
						DgScmCocTxtImport dg = new DgScmCocTxtImport();
						dg.setVisible(true);
						getPage();
						break;
					case 8:
						DgDriverInfo di = new DgDriverInfo();
						di.setJmodel(tableModel);
						di.setVisible(true);
						getPage();
						break;
					default:
						break;
					}
				}
			});
		}
		return btnLoad;
	}
	/**
	 * This method initializes jRefreshTable
	 * 
	 * @return javax.swing.JButton
	 */
	/*
	 * private JButton getJRefreshTable() { if (jRefreshTable == null) {
	 * jRefreshTable = new JButton(); jRefreshTable.setText("刷新表体");
	 * jRefreshTable.addActionListener(new java.awt.event.ActionListener() {
	 * public void actionPerformed(java.awt.event.ActionEvent e) { getPage(); }
	 * }); //public void actionPerformed(java.awt.event.ActionEvent e) {} }
	 * return jRefreshTable; }
	 */
}