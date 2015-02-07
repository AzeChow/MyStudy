/*
 * Created on 2004-6-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.custombase;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.parametercode.CustomsBroker;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({"unchecked", "serial"})
public class FmBaseCodeParameter extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JTree jTree = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JTableListModel tableModel = null;

	private DefaultTreeModel model;  //  @jve:decl-index=0:

	private int treeIndexValue;

	private String searchFiled; // 查询字段  //  @jve:decl-index=0:

	private int searchFlat; // 0:不查询，1：查询

	private CustomBaseAction customBaseAction = null;  //  @jve:decl-index=0:

	private CommonBaseCodeAction commonBaseCodeObj = null;

	private JPanel pnCommonQuery = null;

	private JComboBox jComboBox = null;

	private JTextField tfQueryValue = null;

	private JButton btnSearch = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnDown = null;

	private JButton btnDefault = null;

	/**
	 * This is the default constructor
	 */
	public FmBaseCodeParameter() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		commonBaseCodeObj = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		customBaseAction.checkBaseCodeParameterAuthority(new Request(CommonVars
				.getCurrUser()));
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJSplitPane());
		this.setSize(825, 379);
		this.setTitle("报关参数");
		this.setHelpId("baseCodeParameter");
		this.validate();
		this.pack();
		this.setSindex(0);
		searchFlat = 0; // 不查询
		getData(); // 得到第一项
		setState();

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
			jContentPane.add(getJTree(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
			this.getJSplitPane().setResizeWeight(0.2);
			jSplitPane.setLeftComponent(getJContentPane());
			jSplitPane.setRightComponent(getJPanel2());
			jSplitPane.setDividerLocation(120);
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel22(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJPanel3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jTree
	 * 
	 * 
	 * 
	 * @return javax.swing.JTree
	 * 
	 */
	private JTree getJTree() {
		if (jTree == null) {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("报关参数");
			DefaultMutableTreeNode root_1 = new DefaultMutableTreeNode("贸易方式");
			DefaultMutableTreeNode root_2 = new DefaultMutableTreeNode("计量单位");
			DefaultMutableTreeNode root_3 = new DefaultMutableTreeNode("货币代码");
			DefaultMutableTreeNode root_4 = new DefaultMutableTreeNode("证件代码");
			DefaultMutableTreeNode root_5 = new DefaultMutableTreeNode("运输方式");
			DefaultMutableTreeNode root_6 = new DefaultMutableTreeNode("成交方式");
			DefaultMutableTreeNode root_7 = new DefaultMutableTreeNode("保税方式");
			DefaultMutableTreeNode root_8 = new DefaultMutableTreeNode("包装种类");
			DefaultMutableTreeNode root_9 = new DefaultMutableTreeNode("用途代码");
			DefaultMutableTreeNode root_10 = new DefaultMutableTreeNode("集装箱代码");
			DefaultMutableTreeNode root_11 = new DefaultMutableTreeNode("集装箱规格");
			DefaultMutableTreeNode root_12 = new DefaultMutableTreeNode("集装箱尺寸");
			DefaultMutableTreeNode root_13 = new DefaultMutableTreeNode(
					"集装箱托架种类");
			DefaultMutableTreeNode root_14 = new DefaultMutableTreeNode("付款类型");
			DefaultMutableTreeNode root_15 = new DefaultMutableTreeNode("付款者类型");
			DefaultMutableTreeNode root_16 = new DefaultMutableTreeNode("结汇方式");
			DefaultMutableTreeNode root_17 = new DefaultMutableTreeNode("征免方式");
			DefaultMutableTreeNode root_18 = new DefaultMutableTreeNode("征免性质");
			DefaultMutableTreeNode root_19 = new DefaultMutableTreeNode("核扣方式");
			DefaultMutableTreeNode root_20 = new DefaultMutableTreeNode("报关行");
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
			root.add(root_14);
			root.add(root_15);
			root.add(root_16);
			root.add(root_17);
			root.add(root_18);
			root.add(root_19);
			root.add(root_20);
			jTree = new JTree(root);
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
								setSindex(selectedIndex);
								searchFlat = 0;
								getData();
								FmBaseCodeParameter.this.tfQueryValue
										.setText("");
								setState();
							}
						}
					});

		}
		return jTree;
	}

	private JPanel getJPanel22() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jPanel3
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
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
			jToolBar.setFloatable(false);
			jToolBar.add(getPnCommonQuery());
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnDefault());
			jToolBar.add(getBtnDown());
		}
		return jToolBar;
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
			jScrollPane.setBackground(java.awt.Color.WHITE);
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
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
	 * @return Returns the sindex.
	 */
	public int getSindex() {
		return treeIndexValue;
	}

	/**
	 * @param sindex
	 *            The sindex to set.
	 */
	public void setSindex(int sindex) {
		this.treeIndexValue = sindex;
	}

	public void getFields() {

		boolean isTrue = this.jComboBox.getSelectedItem().equals("代码");
		if (isTrue)
			this.searchFiled = "code";
		else
			this.searchFiled = "name";

	}

	/**
	 * 得到
	 */
	public void getData() {
		List dataSource = null;
		if (treeIndexValue == 0) {
			dataSource = CustomBaseList.getInstance().getTrades(); // 贸易方式
		} else if (treeIndexValue == 1) {
			dataSource = CustomBaseList.getInstance().getUnits(); // 计量单位
		} else if (treeIndexValue == 2) {
			dataSource = CustomBaseList.getInstance().getCurrs(); // 货币代码
		} else if (treeIndexValue == 3) {
			dataSource = CustomBaseList.getInstance().getLicensedocus(); // 证件代码
		} else if (treeIndexValue == 4) {
			dataSource = CustomBaseList.getInstance().getTransfs(); // 运输方式
		} else if (treeIndexValue == 5) {
			dataSource = CustomBaseList.getInstance().getTransacs(); // 成交方式
		} else if (treeIndexValue == 6) {
			dataSource = CustomBaseList.getInstance().getPayModes(); // 保税方式
		} else if (treeIndexValue == 7) {
			dataSource = CustomBaseList.getInstance().getWraps(); // 包装种类
		} else if (treeIndexValue == 8) {
			dataSource = CustomBaseList.getInstance().getUses(); // 用途代码
		} else if (treeIndexValue == 9) {
			dataSource = CustomBaseList.getInstance().getSrtJzxs(); // 集装箱代码
		} else if (treeIndexValue == 10) {
			dataSource = CustomBaseList.getInstance().getContaModels(); // 集装箱规格
		} else if (treeIndexValue == 11) {
			dataSource = CustomBaseList.getInstance().getContaSizes(); // 集装箱尺寸
		} else if (treeIndexValue == 12) {
			dataSource = CustomBaseList.getInstance().getSrtTjs(); // 集装箱托架种类
		} else if (treeIndexValue == 13) {
			dataSource = CustomBaseList.getInstance().getPayTypes(); // 付款类型
		} else if (treeIndexValue == 14) {
			dataSource = CustomBaseList.getInstance().getPayerTypes(); // 付款者类型
		} else if (treeIndexValue == 15) {
			dataSource = CustomBaseList.getInstance().getBalanceMode(); // 结汇方式
		} else if (treeIndexValue == 16) {
			dataSource = CustomBaseList.getInstance().getLevymode(); // 征免方式
		} else if (treeIndexValue == 17) {
			dataSource = CustomBaseList.getInstance().getLevyKind(); // 征免性质
		} else if (treeIndexValue == 18) {
			dataSource = CustomBaseList.getInstance().getDeduc(); // 核扣方式
		} else if(treeIndexValue == 19){
			dataSource = CustomBaseList.getInstance().getCustomsBbroker(); // 报关行
		}

		initTable(dataSource, treeIndexValue);
	}

	/**
	 * 初始化表格
	 */
	private void initTable(final List list, final int treeindex1) {
		if (treeindex1 == 0) { // 贸易方式
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("简称", "name", 150));
							list.add(addColumn("全称", "tradeFname", 150));

							return list;
						}
					});
		} else if (treeindex1 == 1) { // 计量单位
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 200));
							list.add(addColumn("比例因子", "unitRatio", 100));
							list.add(addColumn("是否取整", "isMustInt", 100));
							return list;
						}
					});
			jTable.getColumnModel().getColumn(4).setCellRenderer(
					new TableCheckBoxRender());
		} else if (treeindex1 == 2) { // 货币代码
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 200));
							list.add(addColumn("符号", "currSymb", 100));
							return list;
						}
					});
		} else if (treeindex1 == 3) { // 证件代码
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));

							return list;
						}
					});

		} else if (treeindex1 == 4) { // 运输方式
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		} else if (treeindex1 == 5) { // 成交方式
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		} else if (treeindex1 == 6) { // 保税方式
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		} else if (treeindex1 == 7) { // 包装种类
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		} else if (treeindex1 == 8) { // 用途代码
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		} else if (treeindex1 == 9) { // 集装箱代码
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("规格", "name", 100));
							list.add(addColumn("用途", "srtUsing", 150));
							list.add(addColumn("重量", "srtWeight", 100));

							return list;
						}
					});
		} else if (treeindex1 == 10) { // 集装箱规格
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		} else if (treeindex1 == 11) { // 集装箱尺寸
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		} else if (treeindex1 == 12) { // 集装箱托架种类
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("用途", "name", 100));
							list
									.add(addColumn("英文名称",
											"bracketEnglishName", 80));
							list.add(addColumn("类型", "tjType", 150));
							list.add(addColumn("重量", "tjWeight", 80));
							return list;
						}
					});
		} else if (treeindex1 == 13) { // 付款类型
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});
		} else if (treeindex1 == 14) { // 付款者类型
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});

		} else if (treeindex1 == 15) { // 结汇方式
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});

		} else if (treeindex1 == 16) { // 征免方式
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});

		} else if (treeindex1 == 17) { // 征免性质
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 300));
							return list;
						}
					});

		} else if (treeindex1 == 18) { // 核扣方式
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("名称", "name", 100));
							list.add(addColumn("贸易方式代码", "tradeCode", 100));
							list.add(addColumn("核扣方法", "deducMark", 50));
							list.add(addColumn("进出口类别", "inOutType", 60));
							list.add(addColumn("核扣方式说明", "deducNote", 300));
							return list;
						}
					});

		}else if (treeindex1 == 19) { // 报关行
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("代码", "code", 100));
							list.add(addColumn("报关行名称", "name", 200));
							list.add(addColumn("地址", "address", 240));
							list.add(addColumn("联系人", "linkName", 80));
							list.add(addColumn("联系人电话", "linkTel", 200));
							list.add(addColumn("是否默认", "isDefault", 60));
							return list;
						}
					});
			jTable.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer(){

				@Override
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					if(Boolean.TRUE.toString().equals(value)) {
						value = "是";
					} else {
						value = "否";
					}
					
					return super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
							row, column);
				}
			});
		}
		CommonQuery.getInstance().addCommonFilter(jComboBox, tfQueryValue,
				btnSearch, tableModel);
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
	 * This method initializes jPanel4
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnCommonQuery() {
		if (pnCommonQuery == null) {
			java.awt.GridBagConstraints gridBagConstraints15 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints14 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints13 = new GridBagConstraints();

			pnCommonQuery = new JPanel();
			pnCommonQuery.setLayout(new GridBagLayout());
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.gridy = 0;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.ipadx = 49;
			gridBagConstraints13.ipady = -2;
			gridBagConstraints13.insets = new java.awt.Insets(3, 6, 2, 3);
			gridBagConstraints14.gridx = 1;
			gridBagConstraints14.gridy = 0;
			gridBagConstraints14.weightx = 1.0;
			gridBagConstraints14.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints14.ipadx = 35;
			gridBagConstraints14.ipady = 3;
			gridBagConstraints14.insets = new java.awt.Insets(3, 4, 2, 3);
			gridBagConstraints15.gridx = 2;
			gridBagConstraints15.gridy = 0;
			gridBagConstraints15.ipadx = 11;
			gridBagConstraints15.ipady = -3;
			gridBagConstraints15.insets = new java.awt.Insets(3, 3, 2, 7);
			pnCommonQuery.add(getJComboBox(), gridBagConstraints13);
			pnCommonQuery.add(getJTextField(), gridBagConstraints14);
			pnCommonQuery.add(getBtnSearch(), gridBagConstraints15);
		}
		return pnCommonQuery;
	}

	/**
	 * 
	 * This method initializes jComboBox
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setPreferredSize(new java.awt.Dimension(111, 27));
			jComboBox.addItem("代码");
			jComboBox.addItem("名称");
		}
		return jComboBox;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField() {
		if (tfQueryValue == null) {
			tfQueryValue = new JTextField();
			tfQueryValue.setPreferredSize(new java.awt.Dimension(120, 22));
		}
		return tfQueryValue;
	}

	/**
	 * 
	 * This method initializes btnSearch
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 查询
					// searchFlat = 1;
					// getFields();
					// getSearchData(FmBaseCodeParameter.this.searchFiled,
					// tfQueryValue.getText());

				}
			});

		}
		return btnSearch;
	}

	/**
	 * @param comboBox
	 *            The jComboBox to set.
	 */
	public void setJComboBox(JComboBox comboBox) {
		jComboBox = comboBox;
	}

	/**
	 * @return Returns the baseCode.
	 */
	public CustomBaseAction getBaseCode() {
		return customBaseAction;
	}

	/**
	 * @param baseCode
	 *            The baseCode to set.
	 */
	public void setBaseCode(CustomBaseAction baseCode) {
		this.customBaseAction = baseCode;
	}

	public void getSearchData(String sFieldName, String sValue) {
		List dataSource = null;
		if (treeIndexValue == 0) {
			dataSource = customBaseAction.findTrade(sFieldName, sValue); // 贸易方式

		} else if (treeIndexValue == 1) {
			dataSource = customBaseAction.findUnit(sFieldName, sValue);// 单位
		} else if (treeIndexValue == 2) {
			dataSource = customBaseAction.findUnit(sFieldName, sValue); // 货币代码

		} else if (treeIndexValue == 3) {
			dataSource = customBaseAction.findLicensedocu(sFieldName, sValue); // 证件代码
		} else if (treeIndexValue == 4) {
			dataSource = customBaseAction.findTransf(sFieldName, sValue); // 运输方式
		} else if (treeIndexValue == 5) {
			dataSource = customBaseAction.findTransac(sFieldName, sValue); // 成交方式
		} else if (treeIndexValue == 6) {
			dataSource = customBaseAction.findPayMode(sFieldName, sValue); // 保税方式
		} else if (treeIndexValue == 7) {
			dataSource = customBaseAction.findWrap(sFieldName, sValue); // 包装种类
		} else if (treeIndexValue == 8) {
			dataSource = customBaseAction.findUses(sFieldName, sValue); // 用途代码
		} else if (treeIndexValue == 9) {
			dataSource = customBaseAction.findSrtJzx(sFieldName, sValue); // 集装箱代码
		} else if (treeIndexValue == 10) {
			dataSource = customBaseAction.findContaModel(sFieldName, sValue); // 集装箱规格
		} else if (treeIndexValue == 11) {
			dataSource = customBaseAction.findContaSize(sFieldName, sValue); // 集装箱尺寸
		} else if (treeIndexValue == 12) {
			dataSource = customBaseAction.findSrtTj(sFieldName, sValue); // 集装箱托架种类
		} else if (treeIndexValue == 13) {
			dataSource = customBaseAction.findPayType(sFieldName, sValue); // 付款类型
		} else if (treeIndexValue == 14) {
			dataSource = customBaseAction.findPayerType(sFieldName, sValue); // 付款者类型
		} else if (treeIndexValue == 15) {
			dataSource = customBaseAction.findBalanceMode(sFieldName, sValue); // 付款者类型
		} else if (treeIndexValue == 16) {
			dataSource = customBaseAction.findLevyMode(sFieldName, sValue); // 征免方式

		} else if (treeIndexValue == 17) {
			dataSource = customBaseAction.findLevyKind(sFieldName, sValue); // 征免性质
		} else if (treeIndexValue == 18) {
			dataSource = CustomBaseList.getInstance().getDeduc(); // 核扣方式
		} else if(treeIndexValue == 19){
			dataSource = CustomBaseList.getInstance().getCustomsBbroker(); // 报关行
		}

		initTable(dataSource, treeIndexValue);
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgWraps dg = new DgWraps();
					dg.setIsAdd(true);
					dg.setTableModel(tableModel);
					dg.setVisible(true);
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(treeIndexValue == 7){
					DgWraps dg = new DgWraps();
					dg.setIsAdd(false);
					dg.setTableModel(tableModel);
					dg.setVisible(true);
					}else if(treeIndexValue == 1) {
						if (tableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(FmBaseCodeParameter.this,
									"请选择你要修改的资料", "确认", 2);
							return;
						}
						DgUnits bill = new DgUnits();
						bill.setIsAdd(false);
						bill.setTableModel(tableModel);
						bill.setVisible(true);
					}
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBaseCodeParameter.this,
								"请选中要删除的行,请输入!", "提示!", 1);
						return;
					}
					try {
						Wrap obj = (Wrap) tableModel.getCurrentRow();
						customBaseAction.DeleteWrap(obj);
						tableModel.deleteRow(obj);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(FmBaseCodeParameter.this,
								"已经被引用不能删除！", "提示", 1);
						return;
					}
				}
			});
		}
		return btnDelete;
	}

	private void setState() {
		// btnAdd.setEnabled(treeIndexValue == 7);
		// btnEdit.setEnabled(treeIndexValue == 7);
		// btnDelete.setEnabled(treeIndexValue == 7);
		btnAdd.setVisible(treeIndexValue == 7);
		btnEdit.setVisible(treeIndexValue == 7||treeIndexValue == 1);
		btnDelete.setVisible(treeIndexValue == 7);
		btnDown.setVisible(treeIndexValue == 19);
		btnDefault.setVisible(treeIndexValue == 19);
	}

	/**
	 * This method initializes btnDown	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDown() {
		if (btnDown == null) {
			btnDown = new JButton();
			btnDown.setText("运维平台下载");
			btnDown.setToolTipText("<html><body>平台地址(http://bcgs.bg114.com.cn 端口：80)请保持畅通"+"<br/>" +
			"	</body></html>");
			btnDown.setSize(new Dimension(105,30));
			btnDown.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = tableModel.getList();
					if(list!=null){
						customBaseAction.deleteCustomsBroker(list);
					}
					//同步下载
					List<CustomsBroker> customsBrokerList = customBaseAction
							.downCustomsBrokerFormbgcs();
					customBaseAction.saveCustomsBroker(customsBrokerList);
					customsBrokerList = customBaseAction.findCustomsBroker("", "");; // 报关行
					initTable(customsBrokerList, 19);
				}
			});
		}
		return btnDown;
	}

	/**
	 * This method initializes btnDefault	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDefault() {
		if (btnDefault == null) {
			btnDefault = new JButton();
			btnDefault.setText("默认");
			btnDefault.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsBroker cb = (CustomsBroker) tableModel.getCurrentRow();
					
					if(cb != null) {
						List<CustomsBroker> list = tableModel.getList();
						for (CustomsBroker customsBroker : list) {
							customsBroker.setIsDefault(Boolean.FALSE);
						}
						cb.setIsDefault(Boolean.TRUE);
						customBaseAction.saveCustomsBroker(list);
						tableModel.updateRow(cb);
					}
					
				}
			});
		}
		return btnDefault;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
