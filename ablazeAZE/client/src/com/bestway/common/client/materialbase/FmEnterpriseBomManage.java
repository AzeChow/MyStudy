/*
 * Created on 2004-11-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;
import com.bestway.common.materialbase.entity.TempEntBomMaster;
import com.bestway.common.materialbase.entity.TempEntBomVersion;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 *         工厂BOM管理
 */
@SuppressWarnings("unchecked")
public class FmEnterpriseBomManage extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	/**
	 * 新增
	 */
	private JButton btnAdd = null;

	/**
	 * 修改
	 */
	private JButton btnEdit = null;

	/**
	 * 删除
	 */
	private JButton btnDelete = null;

	private JSplitPane jSplitPane = null; // @jve:decl-index=0:

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	/**
	 * 成品/半成品表格
	 */
	private JTable tbMaster = null;

	private JScrollPane jScrollPane = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	/**
	 * BOM用量表格
	 */
	private JTable tbDetail = null;

	private JScrollPane jScrollPane1 = null;

	/**
	 * 是否是成品
	 */
	private JRadioButton rbnFinishedProduct = null;

	/**
	 * 是否半成品
	 */
	private JRadioButton rbnHaltFinishedProduct = null;

	/**
	 * 单行按钮组合
	 */
	private ButtonGroup group = new ButtonGroup(); // @jve:decl-index=0:

	/**
	 * 料件操作接口
	 */
	private MaterialManageAction materialManageAction = null;

	/**
	 * 料件/成品表格模型
	 */
	private JTableListModel tableModelMaster = null;

	/**
	 * BOM用量表格模型
	 */
	private JTableListModel tableModelDetail = null; // @jve:decl-index=0:visual-constraint="802,62"

	// private String type = MaterielType.FINISHED_PRODUCT;

	/**
	 * 展阶
	 */
	private JButton btnDeploy = null;

	/**
	 * 退出
	 */
	private JButton btnExit = null;

	// private BillTemp bom = null;

	private JScrollPane jScrollPane2 = null;

	/**
	 * BOM版本树
	 */
	private JTree trVersion = null;
	/**
	 * 只显示有BOM资料的成品/半成品
	 */
	private JButton btnDisplay = null;

	private JLabel jLabel = null;

	// private String imgNo = null;

	// private JLabel jLabel1 = null;

	/**
	 * 新增父件
	 */
	private JButton btnAddMaster = null;

	/**
	 * 删除父件
	 */
	private JButton btnDeleteMaster = null;

	private JToolBar jToolBar1 = null;

	/**
	 * 新增版本
	 */
	private JButton btnAddVersion = null;

	/**
	 * 新增版本
	 */
	private JButton btnEditVersion = null;

	/**
	 * 删除版本
	 */
	private JButton btnDeleteVersion = null;

	/**
	 * 新增子件
	 */
	private JButton btnAddDetail = null;

	/**
	 * 修改子件
	 */
	private JButton btnEditDetail = null;

	/**
	 * 删除子件
	 */
	private JButton btnDeleteDetail = null;

	private JPanel jPanel4 = null;

	/**
	 *存放Bom管理子件
	 */
	private HashMap<String, List<Object>> hmDetail = new HashMap<String, List<Object>>(); // @jve:decl-index=0:

	/**
	 * 查询操作界面
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;

	/**
	 * 单项用量计算
	 */
	private JButton btnAccountUnitDosage = null;

	/**
	 * BOM文本导入
	 */
	private JButton btnBomLoader = null;

	/**
	 * BOM原始列表
	 */
	private JButton btnOldBomList = null;

	/**
	 * 是否第一次初始化
	 */
	private boolean isFirstInitData = true;

	private JCheckBox cbMaterials = null;

	private JButton btnCopyToOtherBOM = null;
	/**
	 * 版本表格
	 */
	private JTable tbVersion = null;
	/**
	 * 版本表格模型
	 */
	private JTableListModel tableVersion = null;

	/**
	 * 成品使用情况
	 */
	private JButton btnProductProcess = null;

	private JButton btnCheck = null;

	/**
	 * This is the default constructor 构造函数
	 */
	public FmEnterpriseBomManage() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		materialManageAction.checkBomBrowseAuthority(new Request(CommonVars
		 .getCurrUser()));
		initialize();

	}

	/**
	 * This method initializes this 初始化
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("工厂BOM管理");
		this.setHelpId("enterpriseBomManage");
		this.setSize(787, 402);
		this.setContentPane(getJContentPane());
		group.add(rbnFinishedProduct);
		group.add(rbnHaltFinishedProduct);
		this.rbnFinishedProduct.setSelected(true);
		// this.trVersion.removeAll();
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						// (new InitBomMaster()).start();
						// initMasterData();
						pnCommonQueryPage.setInitState();
						// listToTree(getSelectedBomMaster());
					}
				});
		this.tbMaster.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						JTableListModel tableModel = (JTableListModel) tbMaster
								.getModel();
						if (tableModel == null) {
							return;
						}
						try {
							if (tableModel.getCurrentRow() != null) {
								// setState();
								// (new InitBomVersion()).start();
								initVersion(getSelectedBomMaster());
							} else {
								initVersion(null);
							}
						} catch (Exception ee) {
						}
					}
				});
	}

	// private void initMasterData() {
	// List list = materialManageAction.findEnterpriseBomMaster(new Request(
	// CommonVars.getCurrUser()));
	// if (list != null && !list.isEmpty()) {
	// initMasterTable(list);
	// } else {
	// initMasterTable(new Vector());
	// }
	// }
	/**
	 * 获得数据源
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List getDataSource(int index, int length, String property,
			Object value, boolean isLike) {
		String s = property;
		if ("ptName".equals(property)) {
			s = "factoryName";
		} else if ("ptSpec".equals(property)) {
			s = "factorySpec";
		} else if ("calUnitName".equals(property)) {
			s = "b.calUnit.name";
		}
		property = s;
		long beginTime=System.currentTimeMillis();
		List list =  materialManageAction.findEnterpriseBomMaster(new Request(
				CommonVars.getCurrUser()), index, length, property, value,
				isLike);
		long endTime=System.currentTimeMillis();
		System.out.println("------Search Time :"+(endTime-beginTime)/1000.0);
		return list;

	}

	// class InitBomMaster extends Thread {
	// public void run() {
	// try {
	// CommonProgress.showProgressDialog();
	// CommonProgress.setMessage("系统正在加载数据，请稍后...");
	// initMasterData();JScrollPane
	// listToTree(getSelectedBomMaster());
	// CommonProgress.closeProgressDialog();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(FmEnterpriseBomManage.this,
	// "加载数据出错：" + e.getMessage(), "提示", 2);
	// throw new RuntimeException(e);
	// }
	// }
	// }

	/**
	 * 初始化BOM版本号树
	 */
	// private synchronized void initVersion(TempEntBomMaster bomMaster) {
	// trVersion.removeAll();
	// ObjectNode top = new ObjectNode(null);
	// trVersion.setModel(new DefaultTreeModel(top));
	// if (bomMaster == null) {
	// initDetailTable(new ArrayList());
	// return;
	// }
	// List listVersion = materialManageAction.findEnterpriseBomVersion(
	// new Request(CommonVars.getCurrUser()), bomMaster.getPtNo(),
	// CommonVars.getBomStructureType());
	// for (int i = 0; i < listVersion.size(); i++) {
	// TempEntBomVersion temp = (TempEntBomVersion) listVersion.get(i);
	// ObjectNode note = new ObjectNode(temp);
	// top.add(note);
	// }
	// expandTreeNode(trVersion, (ObjectNode) trVersion.getModel().getRoot());
	// ObjectNode root = (ObjectNode) trVersion.getModel().getRoot();
	// ObjectNode firstNode = null;
	// if (root != null && root.getChildCount() > 0) {
	// firstNode = (ObjectNode) root.getChildAt(0);
	// int index = root.getIndex(firstNode);
	// trVersion.setSelectionRow(index);
	// }
	// hmDetail = materialManageAction.findEnterpriseBomDetail(new Request(
	// CommonVars.getCurrUser()), bomMaster.getPtNo(), CommonVars
	// .getBomStructureType());
	// List list = (firstNode == null || firstNode.getTempVersion() == null) ?
	// new ArrayList()
	// : hmDetail.get(firstNode.getTempVersion().toString());
	// if (list == null) {
	// list = new ArrayList();
	// }
	// initDetailTable(list);
	// }
	/**
	 * 把BOM版本号作为结点加到树上
	 * 
	 * @param aTree
	 * @param aNode
	 */
	private void expandTreeNode(JTree aTree, DefaultMutableTreeNode aNode) {
		if (aNode.isLeaf()) {
			return;
		}
		aTree.expandPath(new TreePath(((DefaultMutableTreeNode) aNode)
				.getPath()));
		int n = aNode.getChildCount();
		for (int i = 0; i < n; i++) {
			expandTreeNode(aTree, (DefaultMutableTreeNode) aNode.getChildAt(i));
		}
	}

	/**
	 * 初始化料件表格
	 * 
	 * @param list
	 * @return
	 */
	private synchronized JTableListModel initMasterTable(List list) {
		// tableModelMaster = new JTableListModel(tbMaster, list,
		// new JTableListModelAdapter() {
		// public List InitColumns() {
		// List<JTableListColumn> list = new Vector<JTableListColumn>();
		// list.add(addColumn("成品料号", "ptNo", 100));
		// list.add(addColumn("商品名称", "ptName", 200));
		// list.add(addColumn("型号规格", "ptSpec", 200));
		// list.add(addColumn("计量单位", "calUnitName", 80));
		// return list;
		// }
		// });
		new JTableListModel(tbMaster, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("料别", "scmCoiName", "scmCoi.name", 100));
				list.add(addColumn("成品料号", "ptNo", 100));
				list.add(addColumn("商品名称", "ptName", 200));
				list.add(addColumn("型号规格", "ptSpec", 200));
				list.add(addColumn("工厂单位", "calUnitName", 80));
				return list;
			}
		});
		tableModelMaster = (JTableListModel) tbMaster.getModel();
		return tableModelMaster;
	}

	/**
	 * 初始化BOM表格
	 * 
	 * @param list
	 */
	private synchronized JTableListModel initDetailTable(List list) {
		new JTableListModel(tbDetail, list, new JTableListModelAdapter() {
			// tableModelDetail = new JTableListModel(tbDetail, list,
			// new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("料件料号", "compentNo", 100));
				list.add(addColumn("料件名称", "compentName", 150));
				list.add(addColumn("料件规格", "compentSpec", 150));
				list.add(addColumn("单位用量", "unitDosage", 80, 20));
				list.add(addColumn("单耗", "unitWare", 80, 20));
				list.add(addColumn("损耗", "ware", 80, 20));
				list.add(addColumn("子件版本号", "childVersionNo", 80));
				list.add(addColumn("子件生效日期", "childEffectDate", 80));
				list.add(addColumn("子件失效日期", "childEndDate", 80));
				// list.add(addColumn("是否主料", "isMainMateriel", 80));
				return list;
			}
		});
		tableModelDetail = (JTableListModel) tbDetail.getModel();
		return tableModelDetail;
	}

	/**
	 * 初始化BOM版本的TREE
	 * 
	 * @param master
	 */
	private synchronized void initVersion(TempEntBomMaster bomMaster) {
		// 获取版本
		List lsVersion = bomMaster == null ? new ArrayList()
				: materialManageAction.findEnterpriseBomVersion(new Request(
						CommonVars.getCurrUser()), bomMaster.getPtNo(),
						CommonVars.getBomStructureType());
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("版本号", "version", 40));
				list.add(addColumn("开始有效期", "beginDate", 70));
				list.add(addColumn("结束有效期", "endDate", 70));
				list.add(addColumn("备注", "notes", 60));
				return list;
			}
		};
		tableVersion = new JTableListModel(tbVersion, lsVersion,
				jTableListModelAdapter);
		TempEntBomMaster temp = getSelectedBomMaster();
		TempEntBomVersion tempVersion = getSelectedBomVersion();
		if (temp != null) {
			hmDetail = materialManageAction.findEnterpriseBomDetail(
					new Request(CommonVars.getCurrUser()), temp.getPtNo(),
					CommonVars.getBomStructureType());
		}
		List list = (tempVersion == null) ? new ArrayList() : hmDetail
				.get(tempVersion.toString());
		if (list == null) {
			list = new ArrayList();
		}
		initDetailTable(list);
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel4(), java.awt.BorderLayout.NORTH);
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
			// jLabel1 = new JLabel();
			jLabel = new JLabel();
			jToolBar = new JToolBar();
			jLabel.setText("BOM类别：");
			jLabel.setVisible(false);
			// jLabel1
			// .setText("                                                 ");
			jToolBar.add(jLabel);
			jToolBar.add(getJButton7());
			jToolBar.add(getBtnAddMaster());
			jToolBar.add(getBtnDeleteMaster());
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJRadioButton());
			jToolBar.add(getJRadioButton1());
			// jToolBar.add(jLabel2);
			// jToolBar.add(getJTextField());
			// jToolBar.add(getJButton6());
			jToolBar.add(getJButton5());
			jToolBar.add(getJButton3());
			jToolBar.add(getJButton8());
			jToolBar.add(getBtnProductProcess());
			jToolBar.add(getBtnCheck());
			jToolBar.add(getCbMaterials());
			jToolBar.add(getJButton4());
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
			btnAdd.setVisible(false);
			btnAdd.setText("新增");
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
			btnEdit.setVisible(false);
			btnEdit.setText("修改");
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
	private JButton getJButton2() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setVisible(false);
			btnDelete.setText("删除");
		}
		return btnDelete;
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
			jSplitPane.setDividerSize(5);
			jSplitPane.setDividerLocation(150);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane
					.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
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
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"请选择成品/半成品",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
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
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "BOM用量",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel1.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
		}
		return jPanel1;
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
	private JTable getTbMaster() {
		if (tbMaster == null) {
			tbMaster = new JTable();
			tbMaster.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelMaster == null) {
								return;
							}
							if (tableModelMaster.getCurrentRow() == null) {
								return;
							}
							initVersion(getSelectedBomMaster());

						}
					});
		}
		return tbMaster;
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
			jScrollPane.setViewportView(getTbMaster());
			jScrollPane
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jSplitPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setDividerSize(3);
			jSplitPane1.setOneTouchExpandable(true);
			jSplitPane1.setDividerLocation(150);
			jSplitPane1.setLeftComponent(getJPanel2());
			jSplitPane1.setRightComponent(getJPanel3());
		}
		return jSplitPane1;
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
			jPanel2.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
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
			jPanel3.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
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
	private JTable getTbDetail() {
		if (tbDetail == null) {
			tbDetail = new JTable();
		}
		return tbDetail;
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
			jScrollPane1.setViewportView(getTbDetail());
			jScrollPane1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane1;
	}

	/**
	 * 
	 * This method initializes jRadioButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton() {
		if (rbnFinishedProduct == null) {
			rbnFinishedProduct = new JRadioButton();
			rbnFinishedProduct.setText("成品");
			rbnFinishedProduct.setEnabled(false);
			rbnFinishedProduct.setVisible(false);
			rbnFinishedProduct
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {

							// if (type.equals(MaterielType.FINISHED_PRODUCT)) {
							// return;
							// }
							// initData(MaterielType.FINISHED_PRODUCT);
							// setType(MaterielType.FINISHED_PRODUCT);
						}
					});

		}
		return rbnFinishedProduct;
	}

	/**
	 * 
	 * This method initializes jRadioButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton1() {
		if (rbnHaltFinishedProduct == null) {
			rbnHaltFinishedProduct = new JRadioButton();
			rbnHaltFinishedProduct.setText("半成品");
			rbnHaltFinishedProduct.setEnabled(false);
			rbnHaltFinishedProduct.setVisible(false);
			rbnHaltFinishedProduct
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {

							// if
							// (type.equals(MaterielType.SEMI_FINISHED_PRODUCT))
							// {
							// return;
							// }
							// // initData(MaterielType.SEMI_FINISHED_PRODUCT);
							// setType(MaterielType.SEMI_FINISHED_PRODUCT);
						}
					});

		}
		return rbnHaltFinishedProduct;
	}

	// /**
	// * @return Returns the type.
	// */
	// public String getType() {
	// return type;
	// }
	//
	// /**
	// * @param type
	// * The type to set.
	// */
	// public void setType(String type) {
	// this.type = type;
	// }

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
		if (btnDeploy == null) {
			btnDeploy = new JButton();
			btnDeploy.setText("展阶");
			btnDeploy.setVisible(false);
			btnDeploy.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					// if (tableModelMaster.getCurrentRow() == null) {
					// JOptionPane.showMessageDialog(
					// FmEnterpriseBomManage.this, "请选中成品或半成品项目!",
					// "提示", 2);
					// return;
					// }
					// bom = (BillTemp) tableModelMaster.getCurrentRow();
					//
					// initDetailTable(new Vector());
					// noUnitWaster();
					//
					// new initBomOpen().start();
				}
			});

		}
		return btnDeploy;
	}

	/**
	 * 
	 * This method initializes jButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton4() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("退出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dispose();
				}
			});

		}
		return btnExit;
	}

	/**
	 * 
	 * This method initializes jScrollPane2
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbVersion());
			jScrollPane2
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane2;
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
	// private JTree getTrVersion() {
	// if (trVersion == null) {
	// trVersion = new JTree();
	// trVersion.setRootVisible(false);
	// trVersion.setModel(new DefaultTreeModel(new ObjectNode(null)));
	// trVersion
	// .addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
	//
	// public void valueChanged(
	// javax.swing.event.TreeSelectionEvent e) {
	// TreePath path = e.getPath();
	// if (path != null) {
	// if (e.getNewLeadSelectionPath() == null
	// || e.getNewLeadSelectionPath().equals(
	// e.getOldLeadSelectionPath())) {
	// return;
	// }
	// try {
	// ObjectNode node = (ObjectNode) path
	// .getLastPathComponent();
	// TempEntBomVersion tempVersion = node
	// .getTempVersion();
	//
	// TempEntBomMaster temp = getSelectedBomMaster();
	// if (temp != null) {
	// hmDetail = materialManageAction
	// .findEnterpriseBomDetail(
	// new Request(CommonVars
	// .getCurrUser()),
	// temp.getPtNo(),
	// CommonVars
	// .getBomStructureType());
	// }
	//
	// List list = (tempVersion == null) ? new ArrayList()
	// : hmDetail.get(tempVersion
	// .toString());
	// if (list == null) {
	// list = new ArrayList();
	// }
	// initDetailTable(list);
	//
	// // TempEntBomMaster tempMaster =
	// // getSelectedBomMaster();
	// // List list = new ArrayList();
	// // if (tempMaster != null
	// // && tempVersion != null) {
	// // list = (tempVersion == null) ? new
	// // ArrayList()
	// // : hmDetail.get(tempVersion
	// // .toString());
	// // }
	// // if (list != null && !list.isEmpty()) {
	// // initDetailTable(list);
	// // } else {
	// // initDetailTable(new Vector());
	// // }
	// } catch (Exception e1) {
	// System.out.println(e1);
	// }
	// }
	// }
	// });
	//
	// }
	// return trVersion;
	// }
	public JTable getTbVersion() {
		if (tbVersion == null) {
			tbVersion = new JTable();
			tbVersion.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableVersion == null) {
								return;
							}
							if (tableVersion.getCurrentRow() == null) {
								return;
							}
							TempEntBomMaster temp = getSelectedBomMaster();
							TempEntBomVersion tempVersion = getSelectedBomVersion();
							if (temp != null) {
								hmDetail = materialManageAction
										.findEnterpriseBomDetail(new Request(
												CommonVars.getCurrUser()), temp
												.getPtNo(), CommonVars
												.getBomStructureType());
							}

							List list = (tempVersion == null) ? new ArrayList()
									: hmDetail.get(tempVersion.toString());
							if (list == null) {
								list = new ArrayList();
							}
							initDetailTable(list);
						}
					});

		}
		return tbVersion;
	}

	// class InitDetailData extends Thread {
	// private TempEntBomVersion tempVersion = null;
	//
	// private TempEntBomMaster tempMaster = null;
	//
	// public InitDetailData(TempEntBomMaster tempMaster,
	// TempEntBomVersion tempVersion) {
	// super();
	// this.tempVersion = tempVersion;
	// this.tempMaster = tempMaster;
	// }
	//
	// public void run() {
	// try {
	// CommonProgress.showProgressDialog();
	// CommonProgress.setMessage("系统正在加载数据，请稍后...");
	// List list = new ArrayList();
	// if (tempMaster != null && tempVersion != null
	// && tempVersion.getVersion() != null) {
	// list = materialManageAction.findEnterpriseBomDetail(
	// new Request(CommonVars.getCurrUser()), tempMaster
	// .getPtNo(), tempVersion.getVersion());
	// }
	// if (list != null && !list.isEmpty()) {
	// initDetailTable(list);
	// } else {
	// initDetailTable(new Vector());
	// }
	// CommonProgress.closeProgressDialog();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(FmEnterpriseBomManage.this,
	// "加载数据出错：" + e.getMessage(), "提示", 2);
	// }
	// }
	// }

	/**
	 * BOM版本号结点类
	 */
	// class ObjectNode extends DefaultMutableTreeNode {
	//
	// public ObjectNode(TempEntBomVersion version) {
	// super(version == null ? "" : ((version.getVersion() == null ? ""
	// : version.getVersion().toString())
	// + "  ["
	// + (version.getBeginDate() == null ? "空"
	// : (new SimpleDateFormat("yyyy-MM-dd")
	// .format(version.getBeginDate())))
	// + "至"
	// + (version.getEndDate() == null ? "空"
	// : new SimpleDateFormat("yyyy-MM-dd").format(version
	// .getEndDate())) + "]"));
	// this.tempVersion = version;
	// }
	//
	// private TempEntBomVersion tempVersion;
	//
	// public TempEntBomVersion getTempVersion() {
	// return tempVersion;
	// }
	//
	// // public void setTempVersion(TempEntBomVersion version) {
	// // this.tempVersion = version;
	// // }
	// }
	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (btnDisplay == null) {
			btnDisplay = new JButton();
			btnDisplay.setText("只显示有BOM资料的成品/半成品");
			btnDisplay.setVisible(false);
			btnDisplay.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					pnCommonQueryPage.setInitState();
					initVersion(getSelectedBomMaster());
				}
			});
		}
		return btnDisplay;
	}

	private TempEntBomMaster getSelectedBomMaster() {
		JTableListModel tableModel = (JTableListModel) tbMaster.getModel();
		if (tableModel == null) {
			return null;
		} else {
			return (TempEntBomMaster) tableModel.getCurrentRow();
		}
	}

	// private TempEntBomVersion getSelectedBomVersion() {
	// TreePath path = trVersion.getSelectionPath();
	// if (path != null) {
	// ObjectNode node = (ObjectNode) path.getLastPathComponent();
	// return node.getTempVersion();
	// }
	// return null;
	// }

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddMaster() {
		if (btnAddMaster == null) {
			btnAddMaster = new JButton();
			btnAddMaster.setText("新增父件");
			btnAddMaster.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean isFilt = true;
					if (JOptionPane.showConfirmDialog(
							FmEnterpriseBomManage.this,
							"是否过滤已加入的选项\r\n如果选\"是\"是过滤，选\"否\"不过滤", "提示",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						isFilt = false;
					}
					List list = null;

					if (cbMaterials.isSelected()) {
						if (isFilt) {
							list = MaterialQuery
									.getInstance()
									.findEnterpriseMaterialNotInMasterMaterial();

						} else {
							list = MaterialQuery
									.getInstance()
									.findEnterpriseMasterMaterial();

						}
						if (list == null || list.size() <= 0) {
							return;
						}
						list = materialManageAction.addEnterpriseBomMaster(
								new Request(CommonVars.getCurrUser()), list);
						tableModelMaster.addRows(list);
						if (tableModelMaster.getCurrentRow() == null) {
							return;
						}
						// new InitBomVersion().start();
						initVersion(getSelectedBomMaster());
					} else {
						if (isFilt) {
							list = MaterialQuery.getInstance()
									.findEnterpriseMaterialNotInMaster();
						} else {
							list = MaterialQuery.getInstance()
							.findEnterpriseMaterial();
						}
						if (list == null || list.size() <= 0) {
							return;
						}
						list = materialManageAction.addEnterpriseBomMaster(
								new Request(CommonVars.getCurrUser()), list);
						tableModelMaster.addRows(list);
						if (tableModelMaster.getCurrentRow() == null) {
							return;
						}
					}

					initVersion(getSelectedBomMaster());

					if (list == null || list.isEmpty()) {
						return;
					}

				}
			});
		}
		return btnAddMaster;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteMaster() {
		if (btnDeleteMaster == null) {
			btnDeleteMaster = new JButton();
			btnDeleteMaster.setText("删除父件");
			btnDeleteMaster
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							TempEntBomMaster master = (TempEntBomMaster) tableModelMaster
									.getCurrentRow();
							if (master == null) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this,
										"请选择你要删除的BOM父料", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmEnterpriseBomManage.this, "你确定要删除的BOM父料",
									"提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							materialManageAction.deleteEnterpriseBomMaster(
									new Request(CommonVars.getCurrUser()),
									master);
							tableModelMaster.deleteRow(master);
							// new InitBomVersion().start();
							initVersion(getSelectedBomMaster());
						}
					});
		}
		return btnDeleteMaster;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnAddVersion());
			jToolBar1.add(getBtnEditVersion());
			jToolBar1.add(getBtnCopyToOtherBOM());
			jToolBar1.add(getBtnDeleteVersion());
			jToolBar1.add(getBtnAddDetail());
			jToolBar1.add(getBtnEditDetail());
			jToolBar1.add(getJButton6());
			jToolBar1.add(getBtnDeleteDetail());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddVersion() {
		if (btnAddVersion == null) {
			btnAddVersion = new JButton();
			btnAddVersion.setText("新增版本");
			btnAddVersion
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							TempEntBomMaster master = getSelectedBomMaster();
							if (master == null) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this, "请选择BOM父料",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgEnterpriseBomVersion dg = new DgEnterpriseBomVersion();
							dg.setMaster(master);
							dg.setDataState(DataState.ADD);
							dg.setVisible(true);
							if (dg.isOk()) {
								TempEntBomVersion version = dg.getVersion();
								tableVersion.addRow(version);
								// ObjectNode root = (ObjectNode) trVersion
								// .getModel().getRoot();
								// ObjectNode subNode = new ObjectNode(version);
								// root.add(subNode);
								// ((DefaultTreeModel) trVersion.getModel())
								// .reload();
								// int index = root.getIndex(subNode);
								// trVersion.setSelectionRow(index);
							}
						}
					});
		}
		return btnAddVersion;
	}

	/**
	 * This method initializes jButton10
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditVersion() {
		if (btnEditVersion == null) {
			btnEditVersion = new JButton();
			btnEditVersion.setText("修改版本");
			btnEditVersion
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							TempEntBomMaster master = getSelectedBomMaster();
							if (master == null) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this, "请选择BOM父料",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							TempEntBomVersion version = getSelectedBomVersion();
							if (version == null) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this, "请选择BOM版本",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgEnterpriseBomVersion dg = new DgEnterpriseBomVersion();
							dg.setMaster(master);
							dg.setVersion(version);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
							if (dg.isOk()) {
								tableVersion.updateRow(dg.getVersion());
								// ObjectNode root = (ObjectNode) trVersion
								// .getModel().getRoot();
								// int index = root
								// .getIndex((ObjectNode) trVersion
								// .getSelectionPath()
								// .getLastPathComponent());
								// root.remove(index);
								// version = dg.getVersion();
								// ObjectNode subNode = new ObjectNode(version);
								// root.insert(subNode, index);
								// ((DefaultTreeModel) trVersion.getModel())
								// .reload();
								// trVersion.setSelectionRow(index);
							}
						}
					});
		}
		return btnEditVersion;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteVersion() {
		if (btnDeleteVersion == null) {
			btnDeleteVersion = new JButton();
			btnDeleteVersion.setText("删除版本");
			btnDeleteVersion
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							TempEntBomMaster master = getSelectedBomMaster();
							if (master == null) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this, "请选择BOM父料",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							TempEntBomVersion version = getSelectedBomVersion();
							if (version == null) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this, "请选择BOM版本",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmEnterpriseBomManage.this, "你确定要删除的BOM版本",
									"提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							materialManageAction.deleteEnterpriseBomVersion(
									new Request(CommonVars.getCurrUser()),
									master, version);
							tableVersion.deleteRow(version);
							// ObjectNode root = (ObjectNode)
							// trVersion.getModel()
							// .getRoot();
							// TreePath path = trVersion.getSelectionPath();
							// root.remove(root.getIndex((ObjectNode) path
							// .getLastPathComponent()));
							// ((DefaultTreeModel)
							// trVersion.getModel()).reload();
							// root = (ObjectNode)
							// trVersion.getModel().getRoot();
							// if (root.getChildCount() > 0) {
							// ObjectNode node = (ObjectNode) root
							// .getChildAt(0);
							// // new InitDetailData(getSelectedBomMaster(),
							// // node
							// // .getTempVersion()).start();
							// TempEntBomVersion tempVersion = node
							// .getTempVersion();
							// TempEntBomMaster tempMaster =
							// getSelectedBomMaster();
							// List list = new ArrayList();
							// if (tempMaster != null && tempVersion != null) {
							// // list = materialManageAction
							// // .findEnterpriseBomDetail(
							// // new Request(CommonVars
							// // .getCurrUser()),
							// // tempMaster.getPtNo(),
							// // tempVersion.getVersion());
							// list = (tempVersion == null) ? new ArrayList()
							// : hmDetail.get(tempVersion
							// .toString());
							// }
							// if (list != null && !list.isEmpty()) {
							// initDetailTable(list);
							// } else {
							// initDetailTable(new Vector());
							// }
							// int index = root.getIndex(node);
							// trVersion.setSelectionRow(index);
							// } else
							{
								// new InitDetailData(getSelectedBomMaster(),
								// null)
								// .start();
								TempEntBomVersion tempVersion = null;
								TempEntBomMaster tempMaster = getSelectedBomMaster();
								List list = new ArrayList();
								if (tempMaster != null && tempVersion != null) {
									// list = materialManageAction
									// .findEnterpriseBomDetail(
									// new Request(CommonVars
									// .getCurrUser()),
									// tempMaster.getPtNo(),
									// tempVersion.getVersion());
									list = (tempVersion == null) ? new ArrayList()
											: hmDetail.get(tempVersion
													.toString());
								}
								if (list != null && !list.isEmpty()) {
									initDetailTable(list);
								} else {
									initDetailTable(new Vector());
								}
							}
						}
					});
		}
		return btnDeleteVersion;
	}

	/**
	 * 获取选择的树结点
	 * 
	 * @return
	 */
	private TempEntBomVersion getSelectedBomVersion() {
		return (TempEntBomVersion) tableVersion.getCurrentRow();
	}

	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddDetail() {
		if (btnAddDetail == null) {
			btnAddDetail = new JButton();
			btnAddDetail.setText("新增子件");
			btnAddDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TempEntBomMaster master = getSelectedBomMaster();
					if (master == null) {
						JOptionPane.showMessageDialog(
								FmEnterpriseBomManage.this, "请选择BOM父料", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
					TempEntBomVersion version = getSelectedBomVersion();
					if (version == null) {
						JOptionPane.showMessageDialog(
								FmEnterpriseBomManage.this, "请选择BOM版本", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
					List list = MaterialQuery.getInstance()
							.findEnterpriseMaterialNotInDetail(
									master.getPtNo(), version.getVersion());
					if (list == null || list.size() <= 0) {
						return;
					}
					list = materialManageAction.addEnterpriseBomDetail(
							new Request(CommonVars.getCurrUser()), master,
							version, list);
					tableModelDetail.addRows(list);
				}
			});
		}
		return btnAddDetail;
	}

	/**
	 * This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditDetail() {
		if (btnEditDetail == null) {
			btnEditDetail = new JButton();
			btnEditDetail.setText("修改子件");
			btnEditDetail
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelDetail.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this,
										"请选择你要修改的料件", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							TempEntBomVersion version = getSelectedBomVersion();
							if (version == null) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this, "请选择BOM版本",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							EnterpriseBomManage detail = (EnterpriseBomManage) tableModelDetail
									.getCurrentRow();
							DgEnterpriseBomDetail dg = new DgEnterpriseBomDetail();
							// dg.setVersion(version);
							dg.setTableModel(tableModelDetail);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
							if (dg.isOk()) {
								detail = dg.getDetail();
								tableModelDetail.updateRow(detail);
							}
						}
					});
		}
		return btnEditDetail;
	}

	/**
	 * This method initializes jButton14
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteDetail() {
		if (btnDeleteDetail == null) {
			btnDeleteDetail = new JButton();
			btnDeleteDetail.setText("删除子件");
			btnDeleteDetail
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							TempEntBomMaster master = getSelectedBomMaster();
							if (master == null) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this, "请选择BOM父料",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							TempEntBomVersion version = getSelectedBomVersion();
							if (version == null) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this, "请选择BOM版本",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							List list = tableModelDetail.getCurrentRows();
							if (list == null || list.size() <= 0) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this,
										"请选择你要删除的BOM子件", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmEnterpriseBomManage.this, "你确定要删除的BOM子件",
									"提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							materialManageAction.deleteEnterpriseBomDetail(
									new Request(CommonVars.getCurrUser()),
									master, version, list);
							tableModelDetail.deleteRows(list);
						}
					});
		}
		return btnDeleteDetail;
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
			jPanel4.add(getPnCommonQueryPage(), BorderLayout.SOUTH);
			jPanel4.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jPanel4;
	}

	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	private PnCommonQueryPage getPnCommonQueryPage() {
		if (pnCommonQueryPage == null) {
			pnCommonQueryPage = new PnCommonQueryPage() {

				@Override
				public JTableListModel initTable(List dataSource) {
					return FmEnterpriseBomManage.this
							.initMasterTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					if (isFirstInitData) {
						isFirstInitData = false;
						return new Vector();
					} else {
						return FmEnterpriseBomManage.this.getDataSource(index,
								length, property, value, isLike);
					}
				}

			};
		}
		return pnCommonQueryPage;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton6() {
		if (btnAccountUnitDosage == null) {
			btnAccountUnitDosage = new JButton();
			btnAccountUnitDosage.setText("单项用量计算");
			btnAccountUnitDosage
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							new InitBomVersion().start();
						}
					});
		}
		return btnAccountUnitDosage;
	}

	class InitBomVersion extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在计算数据，请稍后...");
				materialManageAction.accountUnitDosage(new Request(CommonVars
						.getCurrUser()));
				initVersion(getSelectedBomMaster());
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEnterpriseBomManage.this,
						"计算数据出错：\n单项用量=单耗/（1-损耗率）请检查损耗率是否为1，导致被除数为0", "提示", 2);
				e.printStackTrace();
//				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton7() {
		if (btnBomLoader == null) {
			btnBomLoader = new JButton();
			btnBomLoader.setText("BOM文本导入");
			btnBomLoader.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBomTxtImport dg = new DgBomTxtImport();
					dg.setVisible(true);
					/*
					 * if (tableModelMaster.getCurrRowCount() > 0 &&
					 * tableModelMaster.getCurrRowCount() < tableModelMaster
					 * .getSize() - 1) tableModelMaster
					 * .setTableSelectRowByRow(tableModelMaster
					 * .getCurrRowCount() + 2); else if
					 * (tableModelMaster.getCurrRowCount() == 0 &&
					 * tableModelMaster.getSize() > 1) { tableModelMaster
					 * .setTableSelectRowByRow(tableModelMaster
					 * .getCurrRowCount() + 2); } else if
					 * (tableModelMaster.getCurrRowCount() == tableModelMaster
					 * .getSize() - 1 && tableModelMaster.getSize() > 1) {
					 * tableModelMaster .setTableSelectRowByRow(tableModelMaster
					 * .getCurrRowCount()); } else {
					 * initMasterTable(materialManageAction
					 * .findEnterpriseBomMaster(new Request(CommonVars
					 * .getCurrUser()), -1, -1, null, null, null)); }
					 * initVersion(getSelectedBomMaster());
					 */
				}
			});
		}
		return btnBomLoader;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton8() {
		if (btnOldBomList == null) {
			btnOldBomList = new JButton();
			btnOldBomList.setText("BOM原始列表");
			btnOldBomList
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							TempEntBomMaster master = (TempEntBomMaster) tableModelMaster
									.getCurrentRow();
							if (master == null) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this, "请选择BOM父料",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgEnterpriseBomList dg = new DgEnterpriseBomList();
							dg.setParentNo(master.getPtNo());
							dg.setVisible(true);
							initVersion(getSelectedBomMaster());
						}
					});
		}
		return btnOldBomList;
	}

	/**
	 * This method initializes cbMaterials
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMaterials() {
		if (cbMaterials == null) {
			cbMaterials = new JCheckBox();
			cbMaterials.setText("新增料件父件");
		}
		return cbMaterials;
	}

	/**
	 * This method initializes btnCopyToOtherBOM
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopyToOtherBOM() {
		if (btnCopyToOtherBOM == null) {
			btnCopyToOtherBOM = new JButton();
			btnCopyToOtherBOM.setText("复制到");
			btnCopyToOtherBOM
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							TempEntBomMaster master = getSelectedBomMaster();
							if (tableModelDetail.getRowCount() <= 0) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this, "没有料件可复制!",
										"提示!", JOptionPane.WARNING_MESSAGE);
								return;
							}
							String bom = JOptionPane.showInputDialog(
									FmEnterpriseBomManage.this,
									"请输入将要复制到的版本号:", "版本号输入",
									JOptionPane.PLAIN_MESSAGE);
							// 新添加版本号
							Integer intbom = null;
							try {
								intbom = Integer.parseInt(bom);
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this,
										"输入有错,版本号只能是整数,请重新输入!", "提示!",
										JOptionPane.WARNING_MESSAGE);
								return;
							}

							List<EnterpriseBomManage> details = tableModelDetail
									.getList();
							Map map = materialManageAction
									.addMaterialBomVersionOrInsertMaterialBomDetail(
											new Request(CommonVars
													.getCurrUser()), details,
											intbom);
							if (Boolean
									.parseBoolean(map.get("isExist") == null ? "false"
											: (map.get("isExist").toString()))) {
								JOptionPane.showMessageDialog(
										FmEnterpriseBomManage.this,
										"所输入的版本号已存在!", "提示!",
										JOptionPane.WARNING_MESSAGE);
								return;
							} else {
								// initVersionTree(new ArrayList());
								initVersion(master);

							}
						}
					});
		}
		return btnCopyToOtherBOM;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProductProcess() {
		if (btnProductProcess == null) {
			btnProductProcess = new JButton();
			btnProductProcess.setText("成品使用情况");
			btnProductProcess
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgProductUseInfo dg = new DgProductUseInfo();
							dg.setVisible(true);
							
							initVersion(getSelectedBomMaster());
						}
					});
		}
		return btnProductProcess;
	}

	/**
	 * This method initializes btnCheck	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCheck() {
		if (btnCheck == null) {
			btnCheck = new JButton();
			btnCheck.setText("检查");
			btnCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list=materialManageAction.checkEnterpriseBomManageIsRight();
					System.out.println("List.size()="+list.size());
					if(list.size()==0){
						JOptionPane.showMessageDialog(FmEnterpriseBomManage.this,
								"无错误！" , "提示", 2);
						return;
					}else{
						System.out.println("List.size()="+list.size());
						DgCheckEnterpriseBomManage dg=	new DgCheckEnterpriseBomManage();
						dg.setDataSource(list);
						dg.setVisible(true);
					}
					System.out.println("hcl444");
				}
			});
		}
		return btnCheck;
	}

} // @jve:decl-index=0:visual-constraint="5,7"
