/*
 * Created on 2004-6-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 * checked by 陈井彬   date:2008.10.25
 */
package com.bestway.common.client.erpbillcenter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.SBillType;
import com.bestway.common.constant.WareType;
import com.bestway.common.erpbillcenter.authority.ErpBillCenterAuthority;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * checked by 陈井彬   date:2008.10.25
 */
public class FmBillType extends JInternalFrameBase {

	private JPanel jPanel = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JToolBar jToolBar = null;

	/**
	 * 增加按钮
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
	 * 单据类型表格
	 */
	private JTable tableOrderType = null;

	private JScrollPane jScrollPane = null;

	private CasAction casAction = null;

	/**
	 * 单据类型表格模型
	 */
	private JTableListModel tableOrderTypeModel = null;

	/**
	 * 关闭按钮
	 */
	private JButton btnExit = null;

	/**
	 * 单据类型树
	 */
	private JTree treeOrderType = null;

	private int selectedIndex = 0;

	/**
	 * This is the default constructor
	 * 构造函数
	 */
	public FmBillType() {
		super();
		//
		// check authority
		//
		ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
				.getApplicationContext().getBean("erpBillCenterAuthority");
		erpBillCenterAuthority.checkBillTypeByBrower(new Request(CommonVars
				.getCurrUser()));

		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		setSelectedIndex(0);
		getPage();
	}

	/**
	 * This method initializes this
	 * 初始化
	 * @return void
	 */
	private void initialize() {
		this.setSize(550, 366);
		this.setTitle("工厂单据类型");
		this.setContentPane(getJPanel());
	}

	/**
	 * 获取单据数据源
	 */
	public void getPage() {
		List dataSource = null;
		if (this.getSelectedIndex() == 0) {
			dataSource = casAction.findBillType(new Request(CommonVars
					.getCurrUser()), SBillType.MATERIEL_IN); // 0:料件入库

		} else if (this.getSelectedIndex() == 1) {
			dataSource = casAction.findBillType(new Request(CommonVars
					.getCurrUser()), SBillType.MATERIEL_OUT); // 0:料件出库
		} else if (this.getSelectedIndex() == 2) {
			dataSource = casAction.findBillType(new Request(CommonVars
					.getCurrUser()), SBillType.PRODUCE_IN); // 0:成品入库
		} else if (this.getSelectedIndex() == 3) {
			dataSource = casAction.findBillType(new Request(CommonVars
					.getCurrUser()), SBillType.PRODUCE_OUT); // 0:成品出库

		} else if (this.getSelectedIndex() == 4) {
			dataSource = casAction.findBillType(new Request(CommonVars
					.getCurrUser()), SBillType.FIXTURE_IN); // 0:设备入库

		} else if (this.getSelectedIndex() == 5) {
			dataSource = casAction.findBillType(new Request(CommonVars
					.getCurrUser()), SBillType.FIXTURE_OUT); // 0:设备出库

		} else if (this.getSelectedIndex() == 6) {
			dataSource = casAction.findBillType(new Request(CommonVars
					.getCurrUser()), SBillType.HALF_PRODUCE_IN); // 0:半成品入库
		} else if (this.getSelectedIndex() == 7) {
			dataSource = casAction.findBillType(new Request(CommonVars
					.getCurrUser()), SBillType.HALF_PRODUCE_OUT); // 0:半成品出库
		} else if (this.getSelectedIndex() == 8) {
			dataSource = casAction.findBillType(new Request(CommonVars
					.getCurrUser()), SBillType.REMAIN_PRODUCE_IN); // 0:残次品入库
		} else if (this.getSelectedIndex() == 9) {
			dataSource = casAction.findBillType(new Request(CommonVars
					.getCurrUser()), SBillType.REMAIN_PRODUCE_OUT); // 0:残次品出库
		} else if (this.getSelectedIndex() == 10) {
			dataSource = casAction.findBillType(new Request(CommonVars
					.getCurrUser()), SBillType.LEFTOVER_MATERIEL_IN); // 0:边角料入库
		} else if (this.getSelectedIndex() == 11) {
			dataSource = casAction.findBillType(new Request(CommonVars
					.getCurrUser()), SBillType.LEFTOVER_MATERIEL_OUT); // 0:边角料出库
		}
		for(int i=0;i<dataSource.size();i++){
			BillType b=(BillType)dataSource.get(i);
			if(b.getIsShow()==null)
				b.setIsShow(true);
		}
		initTable(dataSource);
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
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getJPanel1());
			jSplitPane.setRightComponent(getJPanel2());
			jSplitPane.setDividerSize(10);
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setDividerLocation(120);
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
			jPanel1.add(getTreeOrderType(), java.awt.BorderLayout.CENTER);
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
			jPanel2.add(getJToolBar(), BorderLayout.NORTH);
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
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setFloatable(true);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnExit());
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
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(38, 34));
			btnAdd.setVisible(false);
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgBillType dgBillType = new DgBillType();
					dgBillType.setTableModel(tableOrderTypeModel);
					dgBillType.setAdd(true);
					dgBillType.setBeginType(FmBillType.this.getSelectedIndex());
					dgBillType.setVisible(true);
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
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(70, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableOrderTypeModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBillType.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					DgBillType dgBillType = new DgBillType();
					dgBillType.setAdd(false);
					dgBillType.setTableModel(tableOrderTypeModel);
					dgBillType.setVisible(true);

				}
			});

		}
		return btnEdit;
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
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setVisible(false);
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					BillType billType = (BillType) tableOrderTypeModel
							.getCurrentRow();
					if (billType == null) {
						JOptionPane.showMessageDialog(FmBillType.this,
								"您没有选中任何记录！", "提示信息", 0);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmBillType.this,
							"您确定要删除该条记录吗？", "提示信息", 0) == JOptionPane.YES_OPTION) {
						try {
							casAction.deleteBillType(new Request(CommonVars
									.getCurrUser()), billType);
							tableOrderTypeModel.deleteRow(billType);
						} catch (Exception r) {
							JOptionPane.showMessageDialog(FmBillType.this,
									"该单据类型已被引用，您不可以删除！", "提示信息", 2);
						}
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
	private JTable getTableOrderType() {
		if (tableOrderType == null) {
			tableOrderType = new JTable();
//			tableOrderType.getSelectionModel().addListSelectionListener(
//					new ListSelectionListener() {
//						public void valueChanged(ListSelectionEvent e) {
//							if (e.getValueIsAdjusting()) {
//								return;
//							}
//							if (tableOrderTypeModel == null)
//								return;
//							if (tableOrderTypeModel.getCurrentRow() == null)
//								return;
//							setState();
//						}
//					});
		}
		return tableOrderType;
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
			jScrollPane.setViewportView(getTableOrderType());
		}
		return jScrollPane;
	}

	/**
	 * 获得单据类型
	 * @param billType
	 */
	public void getPage(int billType) {
		List dataSource = null;
		dataSource = casAction.findBillType(new Request(CommonVars
				.getCurrUser()), billType);
		for(int i=0;i<dataSource.size();i++){
			BillType b=(BillType)dataSource.get(i);
			if(b.getIsShow()==null)
				b.setIsShow(true);
		}
		initTable(dataSource);
	}

	/**
	 * 初始化表格
	 * @param list
	 */
	private void initTable(final List list) {
				JTableListModelAdapter jTableListModelAdapter	=new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("单据类型代码", "code", 80));
						list.add(addColumn("单据名称", "name", 180));
						list.add(addColumn("进出仓类型", "wareType", 80));
						list.add(addColumn("是否系统内定义", "sysDefine", 100));
						list.add(addColumn("工厂单据名称", "factoryBillName", 80));
						list.add(addColumn("是否在工厂单据管理显示", "isShow", 140));
						return list;
					}
				};
		tableOrderTypeModel = new JTableListModel(tableOrderType, list, jTableListModelAdapter);
		tableOrderType.getColumnModel().getColumn(2).setCellRenderer(
				new CustomTableCellRender());
		tableOrderType.getColumnModel().getColumn(4).setCellRenderer(
				new TableCheckBoxRender());
		tableOrderType.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		jTableListModelAdapter.setEditableColumn(6);
		TableColumn column = tableOrderType.getColumnModel().getColumn(6);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		tableOrderType.getColumnModel().getColumn(3).setCellRenderer(
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
						if (value.equals(String.valueOf(WareType.WARE_IN))) {
							returnValue = "进仓";
						} else if (value.equals(String
								.valueOf(WareType.WARE_OUT))) {
							returnValue = "出仓";
						}
						return returnValue;
					}
				});
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(SwingConstants.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		@Override
		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof BillType) {
				BillType temp = (BillType) obj;
				temp.setIsShow(new Boolean(cb.isSelected()));
				temp = casAction.saveBillType(new Request(
						CommonVars.getCurrUser()), temp);
				tableModel.updateRow(obj);
				
				
			}
			fireEditingStopped();
		}
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
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setPreferredSize(new Dimension(70, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmBillType.this.dispose();

				}
			});

		}
		return btnExit;
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
	private JTree getTreeOrderType() {
		if (treeOrderType == null) {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("单据类型");
			DefaultMutableTreeNode root_1 = new DefaultMutableTreeNode("料件入库");
			DefaultMutableTreeNode root_2 = new DefaultMutableTreeNode("料件出库");
			DefaultMutableTreeNode root_3 = new DefaultMutableTreeNode("成品入库");
			DefaultMutableTreeNode root_4 = new DefaultMutableTreeNode("成品出库");
			DefaultMutableTreeNode root_5 = new DefaultMutableTreeNode("设备入库");
			DefaultMutableTreeNode root_6 = new DefaultMutableTreeNode("设备出库");
			DefaultMutableTreeNode root_7 = new DefaultMutableTreeNode("半成品入库");
			DefaultMutableTreeNode root_8 = new DefaultMutableTreeNode("半成品出库");
			DefaultMutableTreeNode root_9 = new DefaultMutableTreeNode("残次品入库");
			DefaultMutableTreeNode root_10 = new DefaultMutableTreeNode("残次品出库");
			DefaultMutableTreeNode root_11 = new DefaultMutableTreeNode("边角料入库");
			DefaultMutableTreeNode root_12 = new DefaultMutableTreeNode("边角料出库");
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
			treeOrderType = new JTree(root);
			treeOrderType
					.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {

						public void valueChanged(
								javax.swing.event.TreeSelectionEvent e) {

							DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeOrderType
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
		return treeOrderType;
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
	 * 设置状态
	 */
	private void setState() {
		// if (tableModel.getCurrentRow() != null
		// && ((BillType) tableModel.getCurrentRow()).getSysDefine() != null) {
		// jButton1.setEnabled(((BillType) tableModel.getCurrentRow())
		// .getSysDefine().equals(new Boolean(false)));
		// jButton2.setEnabled(((BillType) tableModel.getCurrentRow())
		// .getSysDefine().equals(new Boolean(false)));
		// }
	}

	/**
	 * 定制表格渲染器
	 * @author Administrator
	 *
	 */
	class CustomTableCellRender extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			JTableListModel tempTableModel = (JTableListModel) table.getModel();
			BillType billType = (BillType) tempTableModel.getValueAt(row);
			if (billType.getIsTransferBill() != null
					&& billType.getIsTransferBill()) {
				if (isSelected) {
					setBackground(table.getSelectionBackground());
				} else {
					setBackground(table.getBackground());
				}
				setForeground(Color.BLUE);

			} else {
				if (isSelected) {
					setForeground(table.getSelectionForeground());
					setBackground(table.getSelectionBackground());
				} else {
					setForeground(table.getForeground());
					setBackground(table.getBackground());
				}
			}

			if (billType.getIsCustomsDeclarationCorresponding() != null
					&& billType.getIsCustomsDeclarationCorresponding()) {
				setText(getText() + "  ★");
			}
			return this;
		}

	}

} // @jve:decl-index=0:visual-constraint="10,10"
