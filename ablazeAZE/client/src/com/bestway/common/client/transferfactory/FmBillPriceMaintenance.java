package com.bestway.common.client.transferfactory;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityException;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.entity.CustomsEnvelopCommodityInfo;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;

public class FmBillPriceMaintenance extends FmCommon {

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JTabbedPane jTabbedPane = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnExit = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JTree trImportGoods = null;

	private JTree trExportGoods = null;

	private JPopupMenu pmOtherFunction = null;

	private JMenuItem miImportByMaterielBill = null;

	private JMenuItem miMakeCustomsBillList = null;

	// private JMenuItem miSingleWasteAccount = null;
	private JMenuItem miMakeCustomsDeclaration = null;

	private TransferFactoryBill transferFactoryBill = null;

	private ScmCoc scmCoc = null;

	private List list = null; // @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private boolean isImportGoods = true;

	private boolean isFirstTime = true;

	private boolean isControl = false;

	private JScrollPane jScrollPane1 = null;

	private JScrollPane jScrollPane2 = null;

	private JMenuItem miSplitBill = null;

	private JSeparator jSeparator = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmBillPriceMaintenance() {
		super();
		initialize();
		if (trImportGoods.getRowCount() >= 2) {
			TreePath treePath = trImportGoods.getPathForRow(1);
			if (treePath != null) {
				trImportGoods.setSelectionPath(treePath);
				trImportGoods.scrollPathToVisible(treePath);
			}
		}
		showInitData();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setSize(572, 335);
		this.setTitle("商品单价维护");
	}

	private void showInitData() {
		// List clist = super.getManufacturer();
		if (trImportGoods.getSelectionPath()!= null) {
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) trImportGoods
					.getSelectionPath().getLastPathComponent();
			if (selectedNode == null) {
				return;
			}
			if (!(selectedNode.getUserObject() instanceof ScmCoc)) {
				return;
			} else {
				scmCoc = (ScmCoc) selectedNode.getUserObject();
			}
		}
		List list = this.transferFactoryManageAction
				.findBillPriceMaintenanceByScmCoc(new Request(CommonVars
						.getCurrUser()), scmCoc, false);
		initTable(list);
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * @return Returns the isImportGoods.
	 */
	public boolean isImportGoods() {
		return isImportGoods;
	}

	/**
	 * @param isImportGoods
	 *            The isImportGoods to set.
	 */
	public void setImportGoods(boolean isImportGoods) {
		this.isImportGoods = isImportGoods;
	}

	/**
	 * @return Returns the impExpRequestBill.
	 */
	public TransferFactoryBill getTransferFactoryBill() {
		return transferFactoryBill;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(10);
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setLeftComponent(getJTabbedPane());
			jSplitPane.setRightComponent(getJPanel());
			jSplitPane.setDividerLocation(120);
		}
		return jSplitPane;
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
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(SwingConstants.BOTTOM);
			jTabbedPane.addTab("进货", null, getJScrollPane1(), null);
			jTabbedPane.addTab("出货", null, getJScrollPane2(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							showData();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
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
					Boolean isCustomer = false;
					if (jTabbedPane.getSelectedIndex() == 0) {
						isCustomer = false;
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						isCustomer = true;
					}
					List<CustomsEnvelopCommodityInfo> list = TransferFactoryQuery
							.getInstance()
							.getCustomsEnvelopCommodityInfoByScmcoc(scmCoc,
									isCustomer);
					if (list == null) {
						return;
					}

					makeAddBillPriceMaintenance(list, isCustomer);

				}
			});
		}
		return btnAdd;
	}

	private void makeAddBillPriceMaintenance(
			List<CustomsEnvelopCommodityInfo> list, Boolean isCustomer) {
		List tempList = this.transferFactoryManageAction
				.addBillPriceMaintence(new Request(CommonVars.getCurrUser()),
						list, scmCoc, isCustomer);
		this.tableModel.addRows(tempList);
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
					transferFactoryManageAction
							.checkEditBillPriceMaintenancePrice(new Request(
									CommonVars.getCurrUser()));
					editData();
				}
			});
		}
		return btnEdit;
	}

	private void editData() {
		if (getTableModel().getCurrentRow() == null) {
			return;
		}
		DgBillPriceMaintence dg = new DgBillPriceMaintence();
		dg.setTableModel(tableModel);
		dg.setVisible(true);
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
					deleteData();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmBillPriceMaintenance.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
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
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						editData();
					}
				}
			});
		}
		return jTable;
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
	 * @return Returns the list.
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list
	 *            The list to set.
	 */
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	// /**
	// * @param tableModel
	// * The tableModel to set.
	// */
	// public void setTableModel(JTableListModel tableModel) {
	// this.tableModel = tableModel;
	// }

	/**
	 * This method initializes jTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getTrImportGoods() {
		if (trImportGoods == null) {
			trImportGoods = super.getManufacturerJTree();
			trImportGoods.getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION);
			trImportGoods
					.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {

						public void valueChanged(
								javax.swing.event.TreeSelectionEvent e) {
							treeValueChange(trImportGoods);
						}
					});

		}
		return trImportGoods;
	}

	/**
	 * This method initializes jTree1
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getTrExportGoods() {
		if (trExportGoods == null) {
			trExportGoods = super.getCustomerJTree();
			trExportGoods.getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION);
			trExportGoods
					.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {

						public void valueChanged(
								javax.swing.event.TreeSelectionEvent e) {
							treeValueChange(trExportGoods);
						}
					});

		}
		return trExportGoods;
	}

	/**
	 * 树值改变
	 */
	private void treeValueChange(JTree tree) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree
				.getSelectionPath().getLastPathComponent();
		if (selectedNode == null) {
			return;
		}
		if (!(selectedNode.getUserObject() instanceof ScmCoc)) {
			return;
		} else {
			scmCoc = (ScmCoc) selectedNode.getUserObject();
		}
		Boolean isCustomer = false;
		if (jTabbedPane.getSelectedIndex() == 0) {
			isCustomer = false;
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			isCustomer = true;
		}
		List list = this.transferFactoryManageAction
				.findBillPriceMaintenanceByScmCoc(new Request(CommonVars
						.getCurrUser()), scmCoc, isCustomer);
		initTable(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("项目及备案序号", "projectTypeAndSeqNum",
								100));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "ptName", 150));
						list.add(addColumn("商品规格", "ptSpec", 80));
						list.add(addColumn("单价", "unitPrice", 50));
						list.add(addColumn("币制", "curr.currSymb", 80));
						list.add(addColumn("客户/供应商名称", "scmCoc.name", 150));
						return list;
					}
				});
	}

	/**
	 * 删除
	 */
	private void deleteData() {
		if (tableModel.getCurrentRows() != null
				&& tableModel.getCurrentRows().size() > 0) {
			if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", 0) != 0) {
				return;
			}
			try {
				super.transferFactoryManageAction.delBillPriceMaintenance(
						new Request(CommonVars.getCurrUser()), tableModel
								.getCurrentRows());
				tableModel.deleteRows(tableModel.getCurrentRows());
			}catch (AuthorityException a) {
				JOptionPane.showMessageDialog(FmBillPriceMaintenance.this,
						"你没有删除记录的权限，请与管理员联系！", "提示信息", 2);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(FmBillPriceMaintenance.this,
						"部分数据未能删除！", "提示！", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "请选择要删除的数据行!", "提示",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * 设置状态
	 */
	private void setState() {
		if (getTransferFactoryBill() != null) {
			btnEdit.setEnabled(!transferFactoryBill.getIsApplyToCustomsBill()
					.booleanValue()
					|| !transferFactoryBill.getIsCustomsEnvelopRequestBill()
							.booleanValue());
			btnDelete.setEnabled(!transferFactoryBill.getIsAvailability()
					.booleanValue());
		}
	}

	/**
	 * This method initializes miSingleWasteAccount
	 * 
	 * @return javax.swing.JMenuItem
	 */

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTrImportGoods());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTrExportGoods());
		}
		return jScrollPane2;
	}

	private void showData() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			setImportGoods(true);
			TreePath treePath = trImportGoods.getSelectionPath();
			if (treePath != null) {
				trImportGoods.setSelectionPath(treePath);
				trImportGoods.scrollPathToVisible(treePath);
				treeValueChange(trImportGoods);
			}
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			setImportGoods(false);
			TreePath treePath = null;
			if (trImportGoods.getRowCount() >= 2) {
				if (isFirstTime == true) {
					treePath = trExportGoods.getPathForRow(1);
					isFirstTime = false;
				} else {
					treePath = trExportGoods.getSelectionPath();
					treeValueChange(trExportGoods);
				}
			}
			if (treePath != null) {
				trExportGoods.setSelectionPath(treePath);
				trExportGoods.scrollPathToVisible(treePath);
			}
		}
	}

}