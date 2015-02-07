/*
 * Created on 2004-11-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.materialapply;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.client.common.tableeditor.JNumberForcedTableCellRenderer;
import com.bestway.bcus.client.common.tableeditor.JNumberTableCellEditor;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorEnableListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorParameter;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.client.message.DzscCommon;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.dzsc.materialapply.entity.DzscMaterielHead;
import com.bestway.dzsc.materialapply.entity.MaterialBomDetailApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomMasterApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomVersionApply;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmApplyBomManage extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private DefaultTreeModel model;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JSplitPane jSplitPane = null; // @jve:decl-index=0:

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JTable tbMaster = null;

	private JScrollPane jScrollPane = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JTable tbDetail = null;

	private JScrollPane jScrollPane1 = null;

	private ButtonGroup group = new ButtonGroup();

	// private MaterialManageAction materialManageAction = null;

	private MaterialApplyAction materialApplyAction = null;

	private JTableListModel tableModelMaster = null;

	private JTableListModel tableModelDetail = null;

	private String type = MaterielType.FINISHED_PRODUCT;

	private JButton btnExit = null;

	// private BillTemp bom = null;
	private JScrollPane jScrollPane2 = null;

	private JTree jTree = null;

	// private String imgNo = null;
	private JTextField tfSearchValue = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JButton btnSearch = null;

	private JToolBar jToolBar1 = null;

	private JButton btnAddVersion = null;

	private JButton btnEditVersion = null;

	private JButton btnDeleteVersion = null;

	private JButton btnAddDetail = null;

	private JButton btnEditDetail = null;

	private JButton btnDeleteDetail = null;

	private JPanel jPanel4 = null;

	private JButton btnAddMaster = null;

	private JButton btnDeleteMaster = null;

	private JButton btnDeclarate = null;

	private JButton jButton3 = null;

	private JButton btnEffective = null;

	private JRadioButton rbNotChanged = null;

	private JRadioButton rbChanged = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="808,109"

	private PnCommonQueryPage pnCommonQueryPage = null;

	private JPanel jPanel5 = null;

	private JToolBar jToolBar2 = null;

	private JButton btnChangeExg = null;

	private JButton btnUndoChangeBom = null;

	private JButton jButton4 = null;

	private NumberFormatter numberFormatter = null;

	private DefaultFormatterFactory defaultFormatterFactory = null;

	private JButton btnSaveDetail = null;

	/**
	 * This is the default constructor
	 */
	public FmApplyBomManage() {
		super();
		// materialManageAction = (MaterialManageAction) CommonVars
		// .getApplicationContext().getBean("materialManageAction");
		materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
		// commonBaseCodeAction.checkBomBrowseAuthority(new Request(CommonVars
		// .getCurrUser()));
		initialize();
		this.getButtonGroup();

		tbMaster.getSelectionModel().addListSelectionListener(
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
								setState();
								// (new InitBomVersion()).start();
								MaterialBomMasterApply bomApply = (MaterialBomMasterApply) tableModel
										.getCurrentRow();
								initVersion(bomApply);
							} else {
								initVersion(null);
							}
						} catch (Exception ee) {
						}
					}
				});
		tbDetail.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						setState();
					}
				});
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("成品单耗备案");
		this.setSize(787, 459);
		this.setContentPane(getJContentPane());
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						// (new InitBomMaster()).start();
						// initMasterData();
						pnCommonQueryPage.setInitState();
						// MaterialBomApply master = (MaterialBomApply)
						// tableModelMaster
						// .getCurrentRow();
						// initVersion(master);
						JTableListModel tableModel = (JTableListModel) tbMaster
								.getModel();
						if (tableModel == null) {
							return;
						}
						try {
							if (tableModel.getCurrentRow() != null) {
								setState();
								// (new InitBomVersion()).start();
								MaterialBomMasterApply bomApply = (MaterialBomMasterApply) tableModel
										.getCurrentRow();
								initVersion(bomApply);
							} else {
								initVersion(null);
							}
						} catch (Exception ee) {
						}
					}
				});
		this.setHelpId("");
	}

	// private void initMasterData() {
	// List list = materialApplyAction.findMaterialBomApply(new Request(
	// CommonVars.getCurrUser()),this.tfSearchValue.getText().trim());
	// initMasterTable(list);
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
		Boolean isChanged = rbChanged.isSelected();
		return materialApplyAction.findMaterialBomApply(new Request(CommonVars
				.getCurrUser()), index, length, property, value, isLike,
				isChanged);
	}

	// class InitBomMaster extends Thread {
	// public void run() {
	// try {
	// CommonProgress.showProgressDialog();
	// CommonProgress.setMessage("系统正在加载数据，请稍后...");
	// initMasterData();
	// MaterialBomApply master = (MaterialBomApply) tableModelMaster
	// .getCurrentRow();
	// initVersion(master);
	// CommonProgress.closeProgressDialog();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(FmApplyBomManage.this, "加载数据出错："
	// + e.getMessage(), "提示", 2);
	// // System.out.println(e.getMessage());
	// throw new RuntimeException(e);
	// }
	// }
	// }

	/**
	 * 初始化BOM版本的TREE
	 * 
	 * @param master
	 */
	private synchronized void initVersion(MaterialBomMasterApply bomApply) {
		// this.selectedNode = null;
		List lsVersion = new ArrayList();
		if (bomApply != null) {
			lsVersion = materialApplyAction.findMaterialBomVersionApply(
					new Request(CommonVars.getCurrUser()), bomApply);
		}
		ObjectNode top = new ObjectNode(null);
		model = new DefaultTreeModel(top);
		jTree.setModel(model);
		if (lsVersion.size() == 0) {
			initDetailTable(new ArrayList());
		} else {
			initVersionTree(lsVersion);
			ObjectNode root = (ObjectNode) jTree.getModel().getRoot();
			ObjectNode firstNode = null;
			if (root != null && root.getChildCount() > 0) {
				firstNode = (ObjectNode) root.getChildAt(0);
			}
			// List list = (firstNode == null
			// || firstNode.getBomVersionApply() == null ) ? new ArrayList()
			// : materialApplyAction.findMaterialBomDetailApply(
			// new Request(CommonVars.getCurrUser()), firstNode
			// .getBomVersionApply());
			// initDetailTable(list);
			// this.selectedNode = firstNode;
			expandTreeNode(jTree, (ObjectNode) jTree.getModel().getRoot());
			if (firstNode != null) {
				int index = root.getIndex(firstNode);
				jTree.setSelectionRow(index);
			}
		}
	}

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

	// class InitBomVersion extends Thread {
	// public void run() {
	// try {
	// CommonProgress.showProgressDialog();
	// CommonProgress.setMessage("系统正在展开数据，请稍后...");
	// MaterialBomApply bomApply = (MaterialBomApply) tableModelMaster
	// .getCurrentRow();
	// initVersion(bomApply);
	// CommonProgress.closeProgressDialog();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(FmApplyBomManage.this, "展开数据出错："
	// + e.getMessage(), "提示", 2);
	// throw new RuntimeException(e);
	// }
	// }
	// }

	private void initVersionTree(List list) {
		ObjectNode root = (ObjectNode) jTree.getModel().getRoot();
		for (int i = 0; i < list.size(); i++) {
			MaterialBomVersionApply bomVersionApply = (MaterialBomVersionApply) list
					.get(i);
			ObjectNode note = new ObjectNode(bomVersionApply);
			root.add(note);
		}
	}

	private MaterialBomVersionApply getSelectedBomVersion() {
		TreePath path = jTree.getSelectionPath();
		if (path != null) {
			ObjectNode node = (ObjectNode) path.getLastPathComponent();
			return node.getBomVersionApply();
		}
		return null;
	}

	// private String getStateMark(MaterialBomVersionApply versionApply) {
	// String state = "";
	// if (versionApply != null && versionApply.getStateMark() != null) {
	// state = versionApply.getStateMark();
	// }
	// if (state.equals(DzscState.ORIGINAL)) {
	// return "原始状态";
	// } else if (state.equals(DzscState.APPLY)) {
	// return "申报状态";
	// }
	// if (state.equals(DzscState.EXECUTE)) {
	// return "生效状态";
	// }
	// if (state.equals(DzscState.CHANGE)) {
	// return "变更状态";
	// }
	// return "";
	// }

	private synchronized JTableListModel initMasterTable(List list) {
		// tableModelMaster = new JTableListModel(tbMaster, list,
		// new JTableListModelAdapter() {
		// public List InitColumns() {
		// List<JTableListColumn> list = new Vector<JTableListColumn>();
		// list
		// .add(addColumn("料号", "bomMaster.materiel.ptNo",
		// 150));
		// list.add(addColumn("商品名称",
		// "bomMaster.materiel.factoryName", 260));
		// list.add(addColumn("型号规格",
		// "bomMaster.materiel.factorySpec", 260));
		// list.add(addColumn("计量单位",
		// "bomMaster.materiel.ptUnit.name", 100));
		// // list.add(addColumn("商品大类", "bill5", 100));
		// return list;
		// }
		// });
		JTableListModel.dataBind(tbMaster, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("状态标志", "stateMark", 100));
				list.add(addColumn("料别", "materiel.scmCoi.name", 100));
				list.add(addColumn("成品料号", "materiel.ptNo", 150));
				list.add(addColumn("商品名称", "materiel.factoryName", 220));
				list.add(addColumn("型号规格", "materiel.factorySpec", 220));
				list.add(addColumn("申报单位", "materiel.ptUnit.name", 100));
				// list.add(addColumn("商品大类", "bill5", 100));
				return list;
			}
		});// , ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
		tableModelMaster = (JTableListModel) tbMaster.getModel();
		TableColumn column = this.tbMaster.getColumnModel().getColumn(1);
		column.setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				String state = "";
				if (value != null) {
					state = value.toString();
				}
				if (state.equals(DzscState.ORIGINAL)) {
					this.setText("初始状态");
				} else if (state.equals(DzscState.APPLY)) {
					this.setText("申报状态");
				}
				if (state.equals(DzscState.EXECUTE)) {
					this.setText("生效状态");
				}
				if (state.equals(DzscState.CHANGE)) {
					this.setText("变更状态");
				}
				return this;
			}
		});
		return tableModelMaster;
	}

	private synchronized void initDetailTable(final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("修改标志", "modifyMark", 100));
				list.add(addColumn("料件料号", "materiel.ptNo", 150));
				list.add(addColumn("料件名称", "materiel.factoryName", 150));
				list.add(addColumn("申报单位", "materiel.ptUnit.name", 80));
				list.add(addColumn("单耗", "unitWaste", 80));
				list.add(addColumn("损耗", "waste", 80));
				list.add(addColumn("单位用量", "unitUsed", 80));
				return list;
			}
		};
		tableModelDetail = new JTableListModel(tbDetail, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		tbDetail.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						if (state.equals(ModifyMarkState.UNCHANGE)) {
							this.setText("未修改");
						} else if (state.equals(ModifyMarkState.ADDED)) {
							this.setText("新增");
						}
						if (state.equals(ModifyMarkState.MODIFIED)) {
							this.setText("已修改");
						}
						if (state.equals(ModifyMarkState.DELETED)) {
							this.setText("已删除");
						}
						return this;
					}
				});
		jTableListModelAdapter.setEditableColumn(5);
		jTableListModelAdapter.setEditableColumn(6);
		tableModelDetail.setAllowSetValue(true);
		JNumberTableCellEditor tableCellEditor = new JNumberTableCellEditor();
		tableCellEditor.addAutoCalcListener(new TableCellEditorListener() {
			public void run(TableCellEditorParameter param) {
				commitValueChange((MaterialBomDetailApply) param
						.getEditingData());
			}
		});
		tableCellEditor.setEnableListener(new TableCellEditorEnableListener() {
			public boolean isCanEdit(TableCellEditorParameter param) {
				MaterialBomDetailApply detail = (MaterialBomDetailApply) param
						.getEditingData();
				if (detail == null) {
					return false;
				}
				String stateMark = detail.getVersionApply().getBomMasterApply()
						.getStateMark();
				if (DzscState.ORIGINAL.equals(stateMark)
						|| DzscState.CHANGE.equals(stateMark)) {
					return true;
				} else {
					return false;
				}
			}
		});
		tbDetail.getColumnModel().getColumn(5).setCellEditor(tableCellEditor);
		tbDetail.getColumnModel().getColumn(5).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		tbDetail.getColumnModel().getColumn(6).setCellEditor(tableCellEditor);
		tbDetail.getColumnModel().getColumn(6).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		tbDetail.setRowHeight(20);

	}

	private void commitValueChange(MaterialBomDetailApply detail) {
		if (detail == null) {
			return;
		}
		double unitWaste = (detail.getUnitWaste() == null ? 0.0 : detail
				.getUnitWaste());
		double waste = (detail.getWaste() == null ? 0.0 : detail.getWaste());
		if (waste >= 100 || waste < 0) {
			detail.setWaste(0.0);
			waste = 0.0;
		}
		double unitUsed = CommonVars.getDoubleByDigit(unitWaste
				/ (1 - waste / 100.0), 5);
		detail.setUnitUsed(unitUsed);
	}

	protected DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	public NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat1 = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat1.setMaximumFractionDigits(6);
			decimalFormat1.setGroupingSize(0);
			numberFormatter.setFormat(decimalFormat1);
		}
		return numberFormatter;
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
			jContentPane.add(getJPanel5(), java.awt.BorderLayout.NORTH);
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
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jToolBar = new JToolBar();
			jLabel1.setText("                         ");
			jLabel2.setText("成品料号：");
			jLabel2.setVisible(false);
			jToolBar.add(getBtnAddMaster());
			jToolBar.add(getBtnUndoChangeBom());
			jToolBar.add(getBtnChangeExg());
			jToolBar.add(getBtnDeleteMaster());
			jToolBar.add(getBtnDeclarate());
			jToolBar.add(getBtnEffective());
			jToolBar.add(getTfSearchValue());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getRbNotChanged());
			jToolBar.add(getRbChanged());
			jToolBar.add(jLabel2);
			// jToolBar.add(getBtnChange());
			jToolBar.add(getJButton4());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
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
			jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJPanel4(), java.awt.BorderLayout.CENTER);
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
			tbMaster.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						if (tableModelMaster.getCurrentRow() == null) {
							return;
						}
						// new InitBomVersion().start();
						MaterialBomMasterApply bomApply = (MaterialBomMasterApply) tableModelMaster
								.getCurrentRow();
						initVersion(bomApply);
					}
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
			jSplitPane1.setDividerSize(10);
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
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
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
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
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
			jScrollPane2.setViewportView(getJTree());
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
	private JTree getJTree() {
		if (jTree == null) {
			jTree = new JTree();
			jTree.setRootVisible(false);
			jTree.setModel(new DefaultTreeModel(new ObjectNode(null)));
			jTree
					.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {

						public void valueChanged(
								javax.swing.event.TreeSelectionEvent e) {
							TreePath path = e.getPath();
							if (path != null) {
								if (e.getNewLeadSelectionPath() == null
										|| e.getNewLeadSelectionPath().equals(
												e.getOldLeadSelectionPath())) {
									return;
								}
								try {
									ObjectNode node = (ObjectNode) path
											.getLastPathComponent();
									// new OpenDetailData(node
									// .getBomVersionApply()).start();
									MaterialBomVersionApply version = node
											.getBomVersionApply();
									List list = (version == null ? new ArrayList()
											: materialApplyAction
													.findMaterialBomDetailApply(
															new Request(
																	CommonVars
																			.getCurrUser()),
															version));
									initDetailTable(list);
									// selectedNode = node;
									setState();
								} catch (Exception e1) {
									System.out.println(e1);
								}
							}
						}
					});

		}
		return jTree;
	}

	// class OpenDetailData extends Thread {
	// private MaterialBomVersionApply version;
	//
	// public OpenDetailData(MaterialBomVersionApply version) {
	// super();
	// this.version = version;
	// }
	//
	// public void run() {
	// try {
	// CommonProgress.showProgressDialog();
	// CommonProgress.setMessage("系统正在加载数据，请稍后...");
	// List list = version == null ? new ArrayList()
	// : materialApplyAction.findMaterialBomDetailApply(
	// new Request(CommonVars.getCurrUser()), version);
	// initDetailTable(list);
	// CommonProgress.closeProgressDialog();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(FmApplyBomManage.this, "加载数据出错："
	// + e.getMessage(), "提示", 2);
	// }
	// }
	// }

	// 方法重载
	class ObjectNode extends DefaultMutableTreeNode {

		public ObjectNode(MaterialBomVersionApply bomVersionApply) {
			super(bomVersionApply == null ? "" : bomVersionApply.getVersion()
					.toString()
					+ "  ["
					+ (bomVersionApply.getBeginDate() == null ? "空"
							: (new SimpleDateFormat("yyyy-MM-dd")
									.format(bomVersionApply.getBeginDate())))
					+ "至"
					+ (bomVersionApply.getEndDate() == null ? "空"
							: new SimpleDateFormat("yyyy-MM-dd")
									.format(bomVersionApply.getEndDate()))
					+ "]");
			this.bomVersionApply = bomVersionApply;
		}

		private MaterialBomVersionApply bomVersionApply;

		public MaterialBomVersionApply getBomVersionApply() {
			return bomVersionApply;
		}

		// public void setBomVersionApply(MaterialBomVersionApply
		// bomVersionApply) {
		// this.bomVersionApply = bomVersionApply;
		// }

	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSearchValue() {
		if (tfSearchValue == null) {
			tfSearchValue = new JTextField();
			tfSearchValue.setEditable(true);
			tfSearchValue.setVisible(false);
		}
		return tfSearchValue;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setEnabled(true);
			btnSearch.setText("查询");
			btnSearch.setVisible(false);
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// initMasterData();
					pnCommonQueryPage.setInitState();
				}
			});
		}
		return btnSearch;
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
			jToolBar1.add(getBtnDeleteVersion());
			jToolBar1.add(getBtnAddDetail());
			jToolBar1.add(getBtnEditDetail());
			jToolBar1.add(getBtnDeleteDetail());
			jToolBar1.add(getJButton5());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jButton3
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
							if (tableModelMaster.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmApplyBomManage.this, "请选择成品", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							MaterialBomMasterApply master = (MaterialBomMasterApply) tableModelMaster
									.getCurrentRow();
							List list = MaterialApplyQuery.getInstance()
									.findMaterialBomVersionNotInApply(master);
							if (list == null || list.size() <= 0) {
								return;
							}
							materialApplyAction.addMaterialBomVersionApply(
									new Request(CommonVars.getCurrUser()),
									master, list);
							// (new InitBomVersion()).start();
							MaterialBomMasterApply bomApply = (MaterialBomMasterApply) tableModelMaster
									.getCurrentRow();
							initVersion(bomApply);
						}
					});
		}
		return btnAddVersion;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditVersion() {
		if (btnEditVersion == null) {
			btnEditVersion = new JButton();
			btnEditVersion.setText("修改版本");
			btnEditVersion.setVisible(false);
			btnEditVersion
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (getSelectedBomVersion() == null) {
								JOptionPane.showMessageDialog(
										FmApplyBomManage.this, "请选择成品的版本",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							ObjectNode root = (ObjectNode) jTree.getModel()
									.getRoot();
							DgApplyBomVersion dg = new DgApplyBomVersion();
							dg.setVersion(getSelectedBomVersion());
							dg.setDataState(DataState.EDIT);
							dg.setChange(rbChanged.isSelected());
							dg.setVisible(true);

							if (dg.isOk()) {
								int index = root.getIndex((ObjectNode) jTree
										.getSelectionPath()
										.getLastPathComponent());
								root.remove(index);
								MaterialBomVersionApply versionApply = dg
										.getVersion();
								ObjectNode subNode = new ObjectNode(
										versionApply);
								root.insert(subNode, index);
								((DefaultTreeModel) jTree.getModel()).reload();
								// selectedNode = subNode;
								jTree.setSelectionRow(root.getIndex(subNode));
							}
						}
					});
		}
		return btnEditVersion;
	}

	/**
	 * This method initializes jButton7
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
							if (getSelectedBomVersion() == null) {
								JOptionPane.showMessageDialog(
										FmApplyBomManage.this, "请选择成品的版本",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmApplyBomManage.this, "你确定要删除此版本吗", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							boolean isChange = rbChanged.isSelected();
							if (isChange) {
								materialApplyAction
										.deleteMaterialBomVersionApply(
												new Request(CommonVars
														.getCurrUser()),
												getSelectedBomVersion(),
												isChange);
								MaterialBomVersionApply version = getSelectedBomVersion();
								List list = version == null ? new ArrayList()
										: materialApplyAction
												.findMaterialBomDetailApply(
														new Request(CommonVars
																.getCurrUser()),
														version);
								initDetailTable(list);
							} else {
								materialApplyAction
										.deleteMaterialBomVersionApply(
												new Request(CommonVars
														.getCurrUser()),
												getSelectedBomVersion(),
												isChange);
								ObjectNode root = (ObjectNode) jTree.getModel()
										.getRoot();
								root.remove(root.getIndex((ObjectNode) jTree
										.getSelectionPath()
										.getLastPathComponent()));
								((DefaultTreeModel) jTree.getModel()).reload();
								root = (ObjectNode) jTree.getModel().getRoot();
								if (root.getChildCount() > 0) {
									// selectedNode = (ObjectNode)
									// root.getChildAt(0);
									jTree.setSelectionRow(root
											.getIndex((ObjectNode) root
													.getChildAt(0)));
								}
								// else {
								// selectedNode = null;
								// }
								// new OpenDetailData(selectedNode
								// .getBomVersionApply()).start();
								MaterialBomVersionApply version = getSelectedBomVersion();
								List list = version == null ? new ArrayList()
										: materialApplyAction
												.findMaterialBomDetailApply(
														new Request(CommonVars
																.getCurrUser()),
														version);
								initDetailTable(list);
							}
						}
					});
		}
		return btnDeleteVersion;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddDetail() {
		if (btnAddDetail == null) {
			btnAddDetail = new JButton();
			btnAddDetail.setText("新增料件");
			btnAddDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getSelectedBomVersion() == null) {
						JOptionPane.showMessageDialog(FmApplyBomManage.this,
								"请选择成品的版本", "提示", JOptionPane.OK_OPTION);
						return;
					}
					List list = MaterialApplyQuery.getInstance()
							.findMaterialBomDetailNotInApply(
									getSelectedBomVersion());
					if (list == null || list.size() <= 0) {
						return;
					}
					list = materialApplyAction.addMaterialBomDetailApply(
							new Request(CommonVars.getCurrUser()),
							getSelectedBomVersion(), list);
					tableModelDetail.addRows(list);
				}
			});
		}
		return btnAddDetail;
	}

	/**
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditDetail() {
		if (btnEditDetail == null) {
			btnEditDetail = new JButton();
			btnEditDetail.setText("修改料件");
			btnEditDetail
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelDetail == null
									|| tableModelDetail.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmApplyBomManage.this, "请选择你要修改的料件",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							MaterialBomDetailApply detail = (MaterialBomDetailApply) tableModelDetail
									.getCurrentRow();
							DgApplyBomDetail dg = new DgApplyBomDetail();
							dg.setVersion(getSelectedBomVersion());
							dg.setTableModel(tableModelDetail);
							dg.setChange(rbChanged.isSelected());
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
							if (dg.isOk()) {
								detail = dg.getDetailApply();
								tableModelDetail.updateRow(detail);
							}
						}
					});
		}
		return btnEditDetail;
	}

	/**
	 * This method initializes jButton10
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteDetail() {
		if (btnDeleteDetail == null) {
			btnDeleteDetail = new JButton();
			btnDeleteDetail.setText("删除料件");
			btnDeleteDetail
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelDetail == null
									|| tableModelDetail.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmApplyBomManage.this, "请选择你要删除的料件",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmApplyBomManage.this, "你确定要删除此料件吗", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							MaterialBomDetailApply detailApply = (MaterialBomDetailApply) tableModelDetail
									.getCurrentRow();
							boolean isChange = rbChanged.isSelected();
							materialApplyAction.deleteMaterialBomDetailApply(
									new Request(CommonVars.getCurrUser()),
									detailApply, isChange);
							if (isChange) {
								MaterialBomVersionApply version = getSelectedBomVersion();
								List list = version == null ? new ArrayList()
										: materialApplyAction
												.findMaterialBomDetailApply(
														new Request(CommonVars
																.getCurrUser()),
														version);
								initDetailTable(list);
							} else {
								tableModelDetail.deleteRow(detailApply);
							}
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
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "BOM用量",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel4.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddMaster() {
		if (btnAddMaster == null) {
			btnAddMaster = new JButton();
			btnAddMaster.setText("新增成品");
			btnAddMaster.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = MaterialApplyQuery.getInstance()
							.findMaterialBomNotInApply();
					if (list == null || list.size() == 0) {
						return;
					}
					list = materialApplyAction.addMaterialBomApply(new Request(
							CommonVars.getCurrUser()), list, rbChanged
							.isSelected());
					tableModelMaster.addRows(list);
				}
			});
		}
		return btnAddMaster;
	}

	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteMaster() {
		if (btnDeleteMaster == null) {
			btnDeleteMaster = new JButton();
			btnDeleteMaster.setText("删除成品");
			btnDeleteMaster
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelMaster.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmApplyBomManage.this, "请选择你要删除的成品",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmApplyBomManage.this, "你确定要删除此成品吗", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							List list = tableModelMaster.getCurrentRows();
							materialApplyAction
									.deleteMaterialBomApply(new Request(
											CommonVars.getCurrUser()), list);
							tableModelMaster.deleteRows(list);

						}
					});
		}
		return btnDeleteMaster;
	}

	/**
	 * This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeclarate() {
		if (btnDeclarate == null) {
			btnDeclarate = new JButton();
			btnDeclarate.setText("海关申报");
			btnDeclarate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmApplyBomManage.this,
							"你确定要申报所有成品单耗吗？", "提示",
							JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					new ApplyThread().start();
				}
			});
		}
		return btnDeclarate;
	}

	private void setState() {
		String stateMark = "";
		if (tableModelMaster.getCurrentRow() != null) {
			MaterialBomMasterApply bomMasterApply = (MaterialBomMasterApply) tableModelMaster
					.getCurrentRow();
			stateMark = bomMasterApply.getStateMark();
		} else {
			return;
		}
		DzscMaterielHead dzscMaterielHead = materialApplyAction
				.findDzscMaterielHead(new Request(CommonVars.getCurrUser()));
		this.btnAddMaster.setEnabled(!DzscState.APPLY.equals(dzscMaterielHead
				.getBomState()));
		this.btnDeclarate.setEnabled(!DzscState.APPLY.equals(dzscMaterielHead
				.getBomState()));
		boolean isChange = rbChanged.isSelected();
		this.btnDeleteMaster.setText(isChange ? "取消变更此成品" : "删除成品");
		this.btnDeleteVersion.setText(isChange ? "删除审批后的版本" : "删除版本");
		this.btnDeleteDetail.setText(isChange ? "删除审批后的料件" : "删除料件");
		this.btnUndoChangeBom.setVisible(isChange);
		if (!"".equals(stateMark.trim())) {
			this.btnDeleteMaster.setEnabled(DzscState.ORIGINAL
					.equals(stateMark)
					|| DzscState.CHANGE.equals(stateMark));
			this.btnChangeExg.setEnabled(DzscState.EXECUTE.equals(stateMark)
					&& !DzscState.APPLY.equals(dzscMaterielHead.getBomState()));
			this.btnUndoChangeBom.setEnabled(DzscState.ORIGINAL
					.equals(stateMark)
					|| DzscState.CHANGE.equals(stateMark));
			this.btnEditVersion
					.setEnabled(DzscState.ORIGINAL.equals(stateMark));
			this.btnDeleteVersion.setEnabled(DzscState.ORIGINAL
					.equals(stateMark)
					|| DzscState.CHANGE.equals(stateMark));
			this.btnEditDetail.setEnabled(DzscState.ORIGINAL.equals(stateMark)
					|| DzscState.CHANGE.equals(stateMark));
			this.btnDeleteDetail.setEnabled(DzscState.ORIGINAL
					.equals(stateMark)
					|| DzscState.CHANGE.equals(stateMark));
			this.btnAddDetail.setEnabled(DzscState.ORIGINAL.equals(stateMark)
					|| DzscState.CHANGE.equals(stateMark));
			this.btnSaveDetail.setEnabled(DzscState.ORIGINAL.equals(stateMark)
					|| DzscState.CHANGE.equals(stateMark));
			if (tableModelDetail != null) {
				tableModelDetail.setAllowSetValue(DzscState.ORIGINAL
						.equals(stateMark)
						|| DzscState.CHANGE.equals(stateMark));
			}
		} else {
			this.btnChangeExg.setEnabled(true);
			this.btnUndoChangeBom.setEnabled(true);
			this.btnEditVersion.setEnabled(true);
			this.btnDeleteVersion.setEnabled(true);

			this.btnEditDetail.setEnabled(true);
			this.btnDeleteDetail.setEnabled(true);
			this.btnAddDetail.setEnabled(true);
		}
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEffective() {
		if (btnEffective == null) {
			btnEffective = new JButton();
			btnEffective.setText("处理回执");
			btnEffective.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DzscMaterielHead dzscMaterielHead = materialApplyAction
							.findDzscMaterielHead(new Request(CommonVars
									.getCurrUser()));
					List lsReturnFile=DzscCommon.getInstance().showDzscReceiptFile(
							DzscBusinessType.MATERIAL_BOM,
							dzscMaterielHead.getCopEntNo());
					if (lsReturnFile.size()<=0) {
						return;
					}
					// if (JOptionPane.showConfirmDialog(FmApplyBomManage.this,
					// "你确定要处理回执吗？", "提示", JOptionPane.OK_CANCEL_OPTION) !=
					// JOptionPane.OK_OPTION) {
					// return;
					// }
					try {
						String result = materialApplyAction
								.processMaterialBomResult(new Request(
										CommonVars.getCurrUser()),lsReturnFile);
						JOptionPane.showMessageDialog(FmApplyBomManage.this,
								"处理回执成功\n " + result, "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(FmApplyBomManage.this,
								"处理回执失败 " + ex.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					pnCommonQueryPage.setInitState();
					setState();
				}
			});
		}
		return btnEffective;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNotChanged() {
		if (rbNotChanged == null) {
			rbNotChanged = new JRadioButton();
			rbNotChanged.setText("未变更");
			rbNotChanged.setSelected(true);
			rbNotChanged.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					// if (tableModelMaster.getCurrentRow() == null) {
					// return;
					// }
					pnCommonQueryPage.setInitState();
					setState();
					// new InitBomVersion().start();
					// MaterialBomMasterApply bomApply =
					// (MaterialBomMasterApply) tableModelMaster
					// .getCurrentRow();
					// initVersion(bomApply);
				}
			});
		}
		return rbNotChanged;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbChanged() {
		if (rbChanged == null) {
			rbChanged = new JRadioButton();
			rbChanged.setText("变更中");
			rbChanged.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					pnCommonQueryPage.setInitState();
					setState();
					// if (tableModelMaster.getCurrentRow() == null) {
					// return;
					// }
					// new InitBomVersion().start();
					// MaterialBomMasterApply bomApply =
					// (MaterialBomMasterApply) tableModelMaster
					// .getCurrentRow();
					// initVersion(bomApply);
				}
			});
		}
		return rbChanged;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbChanged());
			buttonGroup.add(this.getRbNotChanged());
		}
		return buttonGroup;
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
			jPanel5.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel5.add(getJToolBar2(), java.awt.BorderLayout.SOUTH);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.add(getPnCommonQueryPage());
		}
		return jToolBar2;
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
					return FmApplyBomManage.this.initMasterTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return FmApplyBomManage.this.getDataSource(index, length,
							property, value, isLike);
				}

			};
		}
		return pnCommonQueryPage;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeExg() {
		if (btnChangeExg == null) {
			btnChangeExg = new JButton();
			btnChangeExg.setText("变更成品");
			btnChangeExg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelMaster.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmApplyBomManage.this,
								"请选择你要变更的成品", "提示", JOptionPane.OK_OPTION);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmApplyBomManage.this,
							"你确定要变更此成品吗", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					List list = tableModelMaster.getCurrentRows();
					tableModelMaster.deleteRows(list);
					materialApplyAction.changeMaterialBomApply(new Request(
							CommonVars.getCurrUser()), list);
					rbChanged.setSelected(true);
				}
			});
		}
		return btnChangeExg;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndoChangeBom() {
		if (btnUndoChangeBom == null) {
			btnUndoChangeBom = new JButton();
			btnUndoChangeBom.setText("删除审批后成品");
			btnUndoChangeBom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelMaster.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmApplyBomManage.this, "请选择你要删除的成品",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmApplyBomManage.this, "你确定要删除此成品吗", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							List list = tableModelMaster.getCurrentRows();
							materialApplyAction
									.markDeleteMaterialBomApply(new Request(
											CommonVars.getCurrUser()), list);
							MaterialBomMasterApply bomApply = (MaterialBomMasterApply) tableModelMaster
									.getCurrentRow();
							initVersion(bomApply);
						}
					});
		}
		return btnUndoChangeBom;
	}

	class ApplyThread extends Thread {
		public void run() {

			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				try {
					DeclareFileInfo fileInfo = materialApplyAction
							.applyMaterialBom(request);
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmApplyBomManage.this,
							fileInfo.getFileInfoSpec(), "提示",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmApplyBomManage.this, "申报失败"
							+ ex.getMessage(), "提示",
							JOptionPane.INFORMATION_MESSAGE);
					System.out.println(ex.getStackTrace() + "\n-->"
							+ ex.getMessage());
					return;
				}
				pnCommonQueryPage.setInitState();
				setState();
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("料件成品单耗");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEditBomApplyUnitWaste dg = new DgEditBomApplyUnitWaste();
					dg.setChange(rbChanged.isSelected());
					dg.setVisible(true);
				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (btnSaveDetail == null) {
			btnSaveDetail = new JButton();
			btnSaveDetail.setText("保存");
			btnSaveDetail
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelDetail != null) {
								if (tbDetail.getCellEditor() != null) {
									tbDetail.getCellEditor().stopCellEditing();
								}
								List list = tableModelDetail.getList();
								for (int i = 0; i < list.size(); i++) {
									MaterialBomDetailApply detail = (MaterialBomDetailApply) list
											.get(i);
									detail = materialApplyAction
											.saveMaterialBomDetailApply(
													new Request(CommonVars
															.getCurrUser()),
													detail);
									tableModelDetail.updateRow(detail);
								}
							}
						}
					});
		}
		return btnSaveDetail;
	}
} // @jve:decl-index=0:visual-constraint="5,7"
