/*
 * Created on2010-09-06
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.owp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.client.common.tableeditor.JNumberForcedTableCellRenderer;
import com.bestway.bcus.client.common.tableeditor.JNumberTableCellEditor;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorParameter;
import com.bestway.bcus.client.manualdeclare.DgEmsHeadH2k;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.owp.action.OwpAnalyAction;
import com.bestway.owp.entity.*;
import com.bestway.common.materialbase.entity.BomStructureType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author hcl
 * check by hcl 2010-09-06
 */
public class FmOwpBomManage extends JInternalFrameBase {
	/**
	 * 主面板
	 */
	private javax.swing.JPanel pnMain = null;
	/**
	 * 父件头工具栏
	 */
	private JToolBar tbHead = null;
	/**
	 * 上下分隔面板
	 */
	private JSplitPane spPane = null; // @jve:decl-index=0:
	/**
	 * Top面板
	 */
	private JPanel pnTop = null;
	/**
	 * Bttom面板
	 */
	private JPanel pnBttom = null;

	/**
	 * 成品/半成品表格
	 */
	private JTable tbMaster = null;
	
	/**
	 * 版本表格
	 */
	private JTable tbVersion = null;
	/**
	 * 成品/半成品表格的面板
	 */
	private JScrollPane spnMaster = null;
	/**
	 * 版本、明细表格的面板
	 */
	private JSplitPane spnVersionAndDetail = null;
	/**
	 * 版本面板
	 */
	private JPanel pnVersion = null;
	/**
	 * 明细面板
	 */
	private JPanel pnDetail = null;
	/**
	 * 料件明细表格
	 */
	private JTable tbDetail = null;
	/**
	 * 料件表格面板
	 */
	private JScrollPane spnDetail = null;
	/**
	 * 单选按钮组
	 */
	private ButtonGroup group = new ButtonGroup();

	/**
	 * 业务操作接口
	 */
	private OwpAnalyAction owpAnalyAction = null;

	/**
	 * 物料表格模型
	 */
	private JTableListModel tableModelMaster = null;

	/**
	 * BOM详细资料表格模型
	 */
	private JTableListModel tableModelDetail = null;
	
	/**
	 * 版本表格模型
	 */
	private JTableListModel tableVersion = null;

	/**
	 * 物料类型
	 */
	private String type = MaterielType.FINISHED_PRODUCT; // @jve:decl-index=0:

	/**
	 * 关闭
	 */
	private JButton btnExit = null;
	/**
	 * 树形面板
	 */
	private JScrollPane spnTree = null;
	/**
	 * Bom树
	 */
	private JTree treeBom = null;
	/**
	 * 料件明细工具栏
	 */
	private JToolBar tbDetailBar = null;

	/**
	 * 新增版本
	 */
	private JButton btnAddVersion = null;

	/**
	 * 修改版本
	 */
	private JButton btnEditVersion = null;

	/**
	 * 删除版本
	 */
	private JButton btnDeleteVersion = null;

	/**
	 * 新增料件
	 */
	private JButton btnAddDetail = null;

	/**
	 * 修改料件
	 */
	private JButton btnEditDetail = null;

	/**
	 * 删除料件
	 */
	private JButton btnDeleteDetail = null;
	/**
	 * 版本明细面板
	 */
	private JPanel pnVersionAndDetail = null;

	/**
	 * 新增成品
	 */
	private JButton btnAddMaster = null;

	/**
	 * 删除成品
	 */
	private JButton btnDeleteMaster = null;
	/**
	 * NumberFormatter类
	 */
	private NumberFormatter numberFormatter = null;
	/**
	 * DefaultFormatterFactory类
	 */
	private DefaultFormatterFactory defaultFormatterFactory = null;
	/**
	 * 成品工具栏面板
	 */
	private JPanel pnHeadBar = null;

	/**
	 * 查询操作页面
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;

	/**
	 * 修改料件成品耗用
	 */
	private JButton btnEditUnitWaste = null;

	/**
	 * 保存
	 */
	private JButton btnSave = null;

	/**
	 * 取消
	 */
	private JButton btnCancel = null;

	/**
	 * 复制到
	 */
	private JButton btnCopyToOtherBOM = null;

	/**
	 * 是否是首次初始化数据
	 */
	private boolean isFirstInitData = true;

	/**
	 * 版本面板
	 */
	private JScrollPane spnVersion;


	/**
	 *默认构造方法
	 */
	public FmOwpBomManage() {
		super();
		owpAnalyAction = (OwpAnalyAction) CommonVars
				.getApplicationContext().getBean("owpAnalyAction");
		// commonBaseCodeAction.checkBomBrowseAuthority(new Request(CommonVars
		// .getCurrUser()));
		initialize();
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
								// setState();
								// (new InitBomVersion()).start();
								OwpBomMaster master = (OwpBomMaster) tableModel
										.getCurrentRow();
								initVersion(master);
							} else {
								initVersion(null);
							}
						} catch (Exception ee) {
						}
					}
				});
	}

	/**
	 * 初始化方法
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("外发加工BOM");
		this.setHelpId("OwpBomManage");
		this.setSize(787, 459);
		this.setContentPane(getJContentPane());
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						// (new InitBomMaster()).start();
						// initMasterData();
						pnCommonQueryPage.setInitState();
						// OwpBomMaster master = (OwpBomMaster)
						// tableModelMaster
						// .getCurrentRow();
						// initVersion(master);
					}
				});
	}

	

	

	/**
	 * 
	 * @author Administrator 变更损耗
	 */
	class UpdateMaterialBomWaste extends Thread {

		// private List list;
		private Integer versionNo;

		private String pFourCode;

		private String mFourCode;

		private Double waste;

		private boolean isByUnitUsed;

		public UpdateMaterialBomWaste(Integer versionNo, String pFourCode,
				String mFourCode, Double waste, boolean isByUnitUsed) {
			super();
			this.versionNo = versionNo;
			this.pFourCode = pFourCode;
			this.mFourCode = mFourCode;
			this.waste = waste;
			this.isByUnitUsed = isByUnitUsed;
		}

		public void run() {
			try {
				String taskId = CommonProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正在更新数据，请稍后...");

				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				owpAnalyAction.updateMaterialBomWaste(request, versionNo,
						pFourCode, mFourCode, waste, isByUnitUsed);
				CommonStepProgress.setStepMessage("系统正在加载数据，请稍后...");
				pnCommonQueryPage.setInitState();
				CommonStepProgress.closeStepProgressDialog();
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmOwpBomManage.this, "更新数据出错："
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	// private void initMasterData() {
	// List list = materialManageAction.findMaterielBomMaster(new Request(
	// CommonVars.getCurrUser()));
	// initMasterTable(list);
	// }
	/**
	 * 查询数据
	 */
	public List getDataSource(int index, int length, String property,
			Object value, boolean isLike) {
		
		long beginTime=System.currentTimeMillis();
		List list =  owpAnalyAction.findMaterielBomMaster(new Request(
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
	// initMasterData();
	// OwpBomMaster master = (OwpBomMaster) tableModelMaster
	// .getCurrentRow();
	// initVersion(master);
	// CommonProgress.closeProgressDialog();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(FmOwpBomManage.this, "加载数据出错："
	// + e.getMessage(), "提示", 2);
	// throw new RuntimeException(e);
	// }
	// }
	// }

	/**
	 * 初始化BOM版本的TREE
	 * 
	 * @param master
	 */
//	private synchronized void initVersion(OwpBomMaster master) {
//		// this.selectedNode=null;
//		List lsVersion = master == null ? new ArrayList()
//				: materialManageAction.findMaterielBomVersion(new Request(
//						CommonVars.getCurrUser()), master);
//		ObjectNode top = new ObjectNode(null);
//		treeBom.setModel(new DefaultTreeModel(top));
//		if (lsVersion.size() == 0) {
//			initDetailTable(new ArrayList());
//		} else {
//			initVersionTree(lsVersion);
//			ObjectNode root = (ObjectNode) treeBom.getModel().getRoot();
//			ObjectNode firstNode = null;
//			if (root != null && root.getChildCount() > 0) {
//				firstNode = (ObjectNode) root.getChildAt(0);
//			}
//			// List list = (firstNode == null || firstNode.getVersion() == null)
//			// ? new ArrayList()
//			// : materialManageAction.findMaterielBomDetail(new Request(
//			// CommonVars.getCurrUser()), firstNode.getVersion());
//			// initDetailTable(list);
//			// this.selectedNode = firstNode;
//			expandTreeNode(treeBom, (ObjectNode) treeBom.getModel().getRoot());
//			int index = root.getIndex(firstNode);
//			treeBom.setSelectionRow(index);
//		}
//	}
	/**
	 * 初始化BOM版本的TREE
	 * 
	 * @param master
	 */
	private synchronized void initVersion(OwpBomMaster master) {
		//获取版本
		List lsVersion = master == null ? new ArrayList()
		: owpAnalyAction.findMaterielBomVersion(new Request(
				CommonVars.getCurrUser()), master);
		JTableListModelAdapter jTableListModelAdapter =new JTableListModelAdapter(){
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("版本号", "version", 40));
				list.add(addColumn("备注", "notes", 90));
				return list;
			}
		};
		tableVersion = new JTableListModel(tbVersion, lsVersion,
				jTableListModelAdapter);
//		tableVersion.setSelectRow(0);
		OwpBomVersion version = (OwpBomVersion) tableVersion
				.getCurrentRow();
		if (version == null) {
			return;
		}
		List list = owpAnalyAction.findMaterielBomDetail(new Request(
				CommonVars.getCurrUser()), version);
		initDetailTable(list);
	}

	/**
	 * 展开树
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

	// class InitBomVersion extends Thread {
	// public void run() {
	// try {
	// CommonProgress.showProgressDialog();
	// CommonProgress.setMessage("系统正在展开数据，请稍后...");
	// OwpBomMaster master = (OwpBomMaster) tableModelMaster
	// .getCurrentRow();
	// initVersion(master);
	// CommonProgress.closeProgressDialog();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(FmOwpBomManage.this, "展开数据出错："
	// + e.getMessage(), "提示", 2);
	// throw new RuntimeException(e);
	// }
	// }
	// }

	/**
	 * 初始化树
	 */
	private void initVersionTree(List list) {
		ObjectNode root = (ObjectNode) treeBom.getModel().getRoot();
		for (int i = 0; i < list.size(); i++) {
			OwpBomVersion version = (OwpBomVersion) list.get(i);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			ObjectNode note = new ObjectNode(version);
			root.add(note);
			// jTree.getModel().get
		}
	}

	/**
	 * 获取选择的树结点
	 * 
	 * @return
	 */
	private OwpBomVersion getSelectedBomVersion() {
		return (OwpBomVersion)tableVersion.getCurrentRow();
	}

	/**
	 * 初始化成品/半成品表格
	 * 
	 * @param list
	 * @return
	 */
	private synchronized JTableListModel initMasterTable(final List list) {
		JTableListModel.dataBind(tbMaster, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("料号", "ptNo", 150));
				list.add(addColumn("名称", "ptName", 220));
				list.add(addColumn("规格", "ptSpec", 220));
				return list;
			}
		});
		tableModelMaster = (JTableListModel) tbMaster.getModel();
		OwpBomMaster master = (OwpBomMaster) tableModelMaster
		.getCurrentRow();

		initVersion(master);
		return tableModelMaster;
	}

	/**
	 * 初始化BOM详细资料
	 * 
	 * @param list
	 */
	private synchronized void initDetailTable(final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("子级料号", "compentNo", 150));
				list.add(addColumn("子级名称", "compentName", 150));
				list.add(addColumn("子级规格", "compentSpec", 150));
				list.add(addColumn("单耗", "unitWare", 80));
				list.add(addColumn("损耗", "ware", 80));
				list.add(addColumn("单项用量", "unitUsed", 80));
				return list;
			}
		};
		tableModelDetail = new JTableListModel(tbDetail, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		jTableListModelAdapter.setEditableColumn(4);
		jTableListModelAdapter.setEditableColumn(5);
		tableModelDetail.setAllowSetValue(true);
		JNumberTableCellEditor tableCellEditor = new JNumberTableCellEditor();
		tableCellEditor.addAutoCalcListener(new TableCellEditorListener() {
			public void run(TableCellEditorParameter param) {
				commitValueChange((OwpBomDetail) param.getEditingData());
			}
		});
		tbDetail.getColumnModel().getColumn(4).setCellEditor(tableCellEditor);
		tbDetail.getColumnModel().getColumn(4).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		tbDetail.getColumnModel().getColumn(5).setCellEditor(tableCellEditor);
		tbDetail.getColumnModel().getColumn(5).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		// tbDetail.setRowHeight(20);
	}

	/**
	 * 计算单项用量
	 * 
	 * @param detail
	 */
	private void commitValueChange(OwpBomDetail detail) {
		if (detail == null) {
			return;
		}
		double unitWaste = (detail.getUnitWare() == null ? 0.0 : detail
				.getUnitWare());
		double waste = (detail.getWare() == null ? 0.0 : detail.getWare());
		if (waste >= 1 || waste < 0) {
			detail.setWare(0.0);
			waste = 0.0;
		}
		double unitUsed = CommonVars.getDoubleByDigit(unitWaste / (1 - waste),
				5);
		System.out.println("unitUsed"+unitUsed);
		detail.setUnitUsed(unitUsed);
	}

	/**
	 * 获取主面板
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (pnMain == null) {
			pnMain = new javax.swing.JPanel();
			pnMain.setLayout(new BorderLayout());
			pnMain.add(getSpPane(), java.awt.BorderLayout.CENTER);
			pnMain.add(getPnHeadBar(), java.awt.BorderLayout.NORTH);
		}
		return pnMain;
	}

	/**
	 * 
	 * 获取tbHead
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getTbHead() {
		if (tbHead == null) {
			tbHead = new JToolBar();
			tbHead.add(getBtnAddMaster());
			tbHead.add(getBtnDeleteMaster());
			// jToolBar.add(jLabel2);
			tbHead.add(getBtnEditUnitWaste());
			// jToolBar.add(getTfSearchValue());
			// jToolBar.add(getBtnSearch());
			// jToolBar.add(getBtnDeclarate());
			// jToolBar.add(getBtnChange());
			tbHead.add(getBtnExit());
		}
		return tbHead;
	}

	/**
	 * 
	 * 获取spPane
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getSpPane() {
		if (spPane == null) {
			spPane = new JSplitPane();
			spPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			spPane.setDividerSize(5);
			spPane.setDividerLocation(150);
			spPane.setTopComponent(getPnTop());
			spPane.setBottomComponent(getPnBttom());
			spPane
					.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
		}
		return spPane;
	}

	/**
	 * 
	 * 获取pnTop
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			pnTop = new JPanel();
			pnTop.setLayout(new BorderLayout());
			pnTop.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"请选择成品/半成品",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			pnTop.add(getSpnMaster(), java.awt.BorderLayout.CENTER);
		}
		return pnTop;
	}

	/**
	 * 
	 *获取pnBttom
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnBttom() {
		if (pnBttom == null) {
			pnBttom = new JPanel();
			pnBttom.setLayout(new BorderLayout());
			pnBttom.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			pnBttom.add(getTbDetailBar(), java.awt.BorderLayout.NORTH);
			pnBttom.add(getPnVersionAndDetail(), java.awt.BorderLayout.CENTER);
		}
		return pnBttom;
	}

	/**
	 * 
	 * 获取tbMaster
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getTbMaster() {
		if (tbMaster == null) {
			tbMaster = new JTable();
//			tbMaster.addMouseListener(new java.awt.event.MouseAdapter() {
//				public void mouseClicked(java.awt.event.MouseEvent e) {
//
//					if (e.getClickCount() == 2) {
//						if (tableModelMaster.getCurrentRow() == null) {
//							return;
//						}
//						// new InitBomVersion().start();
//						OwpBomMaster master = (OwpBomMaster) tableModelMaster
//								.getCurrentRow();
//						initVersion(master);
//						
//					}
//				}
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

							// new InitBomVersion().start();
							OwpBomMaster master = (OwpBomMaster) tableModelMaster
									.getCurrentRow();
							
							initVersion(master);

						}
					});
		}
		return tbMaster;
	}

	/**
	 * 
	 * 获取spnMaster
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getSpnMaster() {
		if (spnMaster == null) {
			spnMaster = new JScrollPane();
			spnMaster.setViewportView(getTbMaster());
			spnMaster
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return spnMaster;
	}

	/**
	 * 
	 * 获取spnVersionAndDetail
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getSpnVersionAndDetail() {
		if (spnVersionAndDetail == null) {
			spnVersionAndDetail = new JSplitPane();
			spnVersionAndDetail.setDividerSize(10);
			spnVersionAndDetail.setOneTouchExpandable(true);
			spnVersionAndDetail.setDividerLocation(150);
			spnVersionAndDetail.setLeftComponent(getPnVersion());
			spnVersionAndDetail.setRightComponent(getPnDetail());
		}
		return spnVersionAndDetail;
	}

	/**
	 * 
	 * 获取pnVersion
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnVersion() {
		if (pnVersion == null) {
			pnVersion = new JPanel();
			pnVersion.setLayout(new BorderLayout());
			pnVersion.add(getSpnVersion(), java.awt.BorderLayout.CENTER);
		}
		return pnVersion;
	}

	/**
	 * 
	 * 获取pnDetail
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnDetail() {
		if (pnDetail == null) {
			pnDetail = new JPanel();
			pnDetail.setLayout(new BorderLayout());
			pnDetail.add(getSpnDetail(), java.awt.BorderLayout.CENTER);
		}
		return pnDetail;
	}

	/**
	 * 
	 *获取tbDetail
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
	 * 获取spnDetail
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getSpnDetail() {
		if (spnDetail == null) {
			spnDetail = new JScrollPane();
			spnDetail.setViewportView(getTbDetail());
			spnDetail
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return spnDetail;
	}
	/**
	 * 
	 * 获取spnVersion
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getSpnVersion() {
		if (spnVersion == null) {
			spnVersion = new JScrollPane();
			spnVersion.setViewportView(getTbVersion());
			spnVersion
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return spnVersion;
	}

	/**
	 * 获取物料类型type
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置物料类型tyep
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * 获取关闭按钮btnExit
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
	 * 获取spnTree
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getSpnTree() {
		if (spnTree == null) {
			spnTree = new JScrollPane();
			spnTree.setViewportView(getTreeBom());
		}
		return spnTree;
	}

	/**
	 * 
	 *获取treeBom
	 * 
	 * 
	 * @return javax.swing.JTree
	 * 
	 */
	private JTree getTreeBom() {
		if (treeBom == null) {
			treeBom = new JTree();
			treeBom.setRootVisible(false);
			treeBom.setModel(new DefaultTreeModel(new ObjectNode(null)));
			treeBom
					.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {

						public void valueChanged(
								javax.swing.event.TreeSelectionEvent e) {
							TreePath path = e.getPath();
							if (path != null) {
								try {
									if (e.getNewLeadSelectionPath() == null
											|| e
													.getNewLeadSelectionPath()
													.equals(
															e
																	.getOldLeadSelectionPath())) {
										return;
									}
									// System.out.println("aaaaaaaaaaaaaaaa");
									ObjectNode node = (ObjectNode) path
											.getLastPathComponent();
									OwpBomVersion version = node
											.getVersion();
									List list = version == null ? new ArrayList()
											: owpAnalyAction
													.findMaterielBomDetail(
															new Request(
																	CommonVars
																			.getCurrUser()),
															version);
									initDetailTable(list);
								} catch (Exception e1) {
									System.out.println(e1);
								}
							}
						}
					});
		}
		return treeBom;
	}

	// class OpenDetailData extends Thread {
	// private OwpBomVersion version;
	//
	// public OpenDetailData(OwpBomVersion version) {
	// super();
	// this.version = version;
	// }
	//
	// public void run() {
	// try {
	// CommonProgress.showProgressDialog();
	// CommonProgress.setMessage("系统正在加载数据，请稍后...");
	// List list = version == null ? new ArrayList()
	// : materialManageAction.findMaterielBomDetail(
	// new Request(CommonVars.getCurrUser()), version);
	// initDetailTable(list);
	// CommonProgress.closeProgressDialog();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(FmOwpBomManage.this, "加载数据出错："
	// + e.getMessage(), "提示", 2);
	// }
	// }
	// }

	/**
	 * 树结点
	 */
	class ObjectNode extends DefaultMutableTreeNode {

		public ObjectNode(OwpBomVersion version) {

			super(version == null ? "" : (version.getVersion().toString()+ 
					(version.getNotes() == null ? "空" : version.getNotes())));
			this.version = version;
		}

		private OwpBomVersion version;

		public OwpBomVersion getVersion() {
			return version;
		}

		// public void setVersion(OwpBomVersion version) {
		// this.version = version;
		// }
	}

	/**
	 * 获取tbDetailBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getTbDetailBar() {
		if (tbDetailBar == null) {
			tbDetailBar = new JToolBar();
			tbDetailBar.add(getBtnAddVersion());
			tbDetailBar.add(getBtnEditVersion());
			tbDetailBar.add(getBtnCopyToOtherBOM());
			tbDetailBar.add(getBtnDeleteVersion());
			tbDetailBar.add(getBtnAddDetail());
			tbDetailBar.add(getBtnEditDetail());
			tbDetailBar.add(getBtnDeleteDetail());
			tbDetailBar.add(getBtnSave());
			tbDetailBar.add(getBtnCancel());
		}
		return tbDetailBar;
	}

	/**
	 * 获取btnAddVersion
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
										FmOwpBomManage.this, "请选择成品", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							OwpBomMaster master = (OwpBomMaster) tableModelMaster
									.getCurrentRow();
							DgOwpBomVersion dg = new DgOwpBomVersion();
							dg.setMaster(master);
							dg.setDataState(DataState.ADD);
							dg.setVisible(true);
							if (dg.isOk()) {
								OwpBomVersion version = dg.getVersion();
								tableVersion.addRow(version);
							}
						}
					});
		}
		return btnAddVersion;
	}

	/**
	 * 获取btnEditVersion
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
							if (getSelectedBomVersion() == null) {
								JOptionPane.showMessageDialog(
										FmOwpBomManage.this, "请选择成品的版本",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgOwpBomVersion dg = new DgOwpBomVersion();
							dg.setVersion(getSelectedBomVersion());
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);

							if (dg.isOk()) {
								tableVersion.updateRow(dg.getVersion());
							}
						}
					});
		}
		return btnEditVersion;
	}

	/**
	 * 获取btnDeleteVersion
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
										FmOwpBomManage.this, "请选择成品的版本",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmOwpBomManage.this, "你确定要删除此版本吗", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							owpAnalyAction.deleteOwpBomVersion(
									new Request(CommonVars.getCurrUser()),
									getSelectedBomVersion());
							tableVersion.deleteRow(getSelectedBomVersion());
							// else {
							// selectedNode = null;
							// }
							// new OpenDetailData(selectedNode.getVersion())
							// .start();
							OwpBomVersion version = getSelectedBomVersion();
							List list = version == null ? new ArrayList()
									: owpAnalyAction
											.findMaterielBomDetail(new Request(
													CommonVars.getCurrUser()),
													version);
							initDetailTable(list);
						}
					});
		}
		return btnDeleteVersion;
	}

	/**
	 * 获取btnAddDetail
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
						JOptionPane.showMessageDialog(FmOwpBomManage.this,
								"请选择成品的版本", "提示", JOptionPane.OK_OPTION);
						return;
					}
					List list = OwpHclQuery
							.getInstance()
							.findNotInBomDetailMaterial(getSelectedBomVersion());
					if (list == null || list.size() <= 0) {
						return;
					}
					
					 long beginTime=System.currentTimeMillis();
					list = owpAnalyAction.saveOwpBomManage(
							new Request(CommonVars.getCurrUser()),
							getSelectedBomVersion(), list);
					
					long endTime=System.currentTimeMillis();
					System.out.println("------Save Time :"+(endTime-beginTime)/1000.0);
					tableModelDetail.addRows(list);
				}
			});
		}
		return btnAddDetail;
	}

	/**
	 * 获取btnEditDetail
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
										FmOwpBomManage.this, "请选择你要修改的料件",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							OwpBomDetail detail = (OwpBomDetail) tableModelDetail
									.getCurrentRow();
							DgOwpBomDetail dg = new DgOwpBomDetail();
							dg.setVersion(getSelectedBomVersion());
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
	 * 获取btnDeleteDetail
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
										FmOwpBomManage.this, "请选择你要删除的料件",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmOwpBomManage.this, "你确定要删除此料件吗", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							OwpBomDetail detail = (OwpBomDetail) tableModelDetail
									.getCurrentRow();
							
							 long beginTime=System.currentTimeMillis();
							owpAnalyAction.deleteOwpBomManage(
									new Request(CommonVars.getCurrUser()),
									detail);
							long endTime=System.currentTimeMillis();
							System.out.println("------Save Time :"+(endTime-beginTime)/1000.0);
							
							tableModelDetail.deleteRow(detail);
						}
					});
		}
		return btnDeleteDetail;
	}

	/**
	 * 获取pnVersionAndDetail
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnVersionAndDetail() {
		if (pnVersionAndDetail == null) {
			pnVersionAndDetail = new JPanel();
			pnVersionAndDetail.setLayout(new BorderLayout());
			pnVersionAndDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "BOM用量",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			pnVersionAndDetail.add(getSpnVersionAndDetail(), java.awt.BorderLayout.CENTER);
		}
		return pnVersionAndDetail;
	}

	/**
	 * 获取btnAddMaster
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddMaster() {
		if (btnAddMaster == null) {
			btnAddMaster = new JButton();
			btnAddMaster.setText("新增成品");
			btnAddMaster.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = OwpHclQuery.getInstance()
							.findBomMaster();
					if (list == null || list.size() <= 0) {
						return;
					}
					
					long beginTime=System.currentTimeMillis();
					list = owpAnalyAction
							.saveOwpBomMaster(new Request(
									CommonVars.getCurrUser()), list);
					
					long endTime=System.currentTimeMillis();
					System.out.println("------Save Time :"+(endTime-beginTime)/1000.0);
					tableModelMaster.addRows(list);
					System.out.println("-----1111111 :");
				}
			});
		}
		return btnAddMaster;
	}

	/**
	 * 获取btnDeleteMaster
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
										FmOwpBomManage.this, "请选择你要删除的成品",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmOwpBomManage.this, "你确定要删除此成品吗", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							OwpBomMaster master = (OwpBomMaster) tableModelMaster
									.getCurrentRow();
							
							long beginTime=System.currentTimeMillis();
							owpAnalyAction.deleteMaterielBomMaster(
									new Request(CommonVars.getCurrUser()),
									master);
							long endTime=System.currentTimeMillis();
							System.out.println("------Save Time :"+(endTime-beginTime)/1000.0);
							
							tableModelMaster.deleteRow(master);
						}
					});
		}
		return btnDeleteMaster;
	}

	/**
	 *获取pnHeadBar
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnHeadBar() {
		if (pnHeadBar == null) {
			pnHeadBar = new JPanel();
			pnHeadBar.setLayout(new BorderLayout());
			pnHeadBar.add(getTbHead(), java.awt.BorderLayout.NORTH);
			pnHeadBar.add(getPnCommonQueryPage(), BorderLayout.SOUTH);
		}
		return pnHeadBar;
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
					return FmOwpBomManage.this.initMasterTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					if (isFirstInitData) {
						isFirstInitData = false;
						return new Vector();
					} else {
						return FmOwpBomManage.this.getDataSource(index,
								length, property, value, isLike);


					}
				}

			};
		}
		return pnCommonQueryPage;
	}


	/**
	 * 获取btnEditUnitWaste
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditUnitWaste() {
		if (btnEditUnitWaste == null) {
			btnEditUnitWaste = new JButton();
			btnEditUnitWaste.setText("修改料件成品耗用");
			btnEditUnitWaste
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgEditOwpBomUnitWaste dg = new DgEditOwpBomUnitWaste();
							dg.setVisible(true);
							OwpBomMaster master = (OwpBomMaster) tableModelMaster
									.getCurrentRow();
							initVersion(master);
						}
					});
		}
		return btnEditUnitWaste;
	}

	/**
	 *获取btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelDetail != null) {
						if (tbDetail.getCellEditor() != null) {
							tbDetail.getCellEditor().stopCellEditing();
						}
						System.out.println("------Save goooooooooooooo:");
						List list = tableModelDetail.getList();
						for (int i = 0; i < list.size(); i++) {
							OwpBomDetail detail = (OwpBomDetail) list
									.get(i);
							System.out.println("UnitUsed="+detail.getUnitUsed());
							// detail.setUnitUsed(detail.getUnitWaste()
							// / (1 - detail.getWaste()));
							
							 long beginTime=System.currentTimeMillis();
							detail = owpAnalyAction
									.saveOwpBomManage(new Request(
											CommonVars.getCurrUser()), detail);
							long endTime=System.currentTimeMillis();
							System.out.println("------Save Time :"+(endTime-beginTime)/1000.0);


							
							
							tableModelDetail.updateRow(detail);
						}
					}
				}
			});
		}
		return btnSave;
	}

	/**
	 * 获取btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
		}
		return btnCancel;
	}

	/**
	 * 获取btnCopyToOtherBOM
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
							if ((null==tableModelDetail)||(null==tableModelDetail.getCurrentRow())||tableModelDetail.getRowCount() <= 0) {
								JOptionPane.showMessageDialog(
										FmOwpBomManage.this, "没有料件可复制!",
										"提示!", JOptionPane.WARNING_MESSAGE);
								return;
							}
							String bom = JOptionPane.showInputDialog(
									FmOwpBomManage.this, "请输入将要复制到的版本号:",
									"版本号输入", JOptionPane.PLAIN_MESSAGE);
							if(bom==null)
								return;
							// 新添加版本号
							Integer intbom = null;
							try {
								intbom = Integer.parseInt(bom);
							} catch (NumberFormatException ex) {
								JOptionPane.showMessageDialog(
										FmOwpBomManage.this,
										"输入有错,版本号只能是整数,请重新输入!", "提示!",
										JOptionPane.WARNING_MESSAGE);
								return;
							}
							List versionList=tableVersion.getList();
							for(int i=0;i<versionList.size();i++){
								if(intbom.equals(((OwpBomVersion)versionList.get(i)).getVersion()))
									{
									JOptionPane.showMessageDialog(
											FmOwpBomManage.this, "所输入的版本号已存在!",
											"提示!", JOptionPane.WARNING_MESSAGE);
									return;
									}
							}
							List listFrom=tableModelDetail.getList();
							List listTo=new ArrayList();
							OwpBomVersion newVersion=new OwpBomVersion();
							newVersion.setCompany(getSelectedBomVersion().getCompany());
							newVersion.setParent(getSelectedBomVersion().getParent());
							newVersion.setVersion(intbom);
							newVersion=owpAnalyAction.saveOwpBomVersion(new Request(CommonVars.getCurrUser()), newVersion);
							for(int i=0;i<listFrom.size();i++){
								OwpBomDetail from=new OwpBomDetail();
								from.setVersion(newVersion);
								from.setCompentNo(((OwpBomDetail)listFrom.get(i)).getCompentNo());
								from.setCompany(((OwpBomDetail)listFrom.get(i)).getCompany());
								from.setCompentName(((OwpBomDetail)listFrom.get(i)).getCompentName());
								from.setCompentSpec(((OwpBomDetail)listFrom.get(i)).getCompentSpec());
								from.setUnitWare(((OwpBomDetail)listFrom.get(i)).getUnitWare());
								from.setWare(((OwpBomDetail)listFrom.get(i)).getWare());
								from.setUnitUsed(((OwpBomDetail)listFrom.get(i)).getUnitUsed());
								listTo.add(from);
								
							}
							owpAnalyAction.saveList(listTo);
							
							OwpBomMaster master = (OwpBomMaster) tableModelMaster
									.getCurrentRow();
							initVersion(master);
						}
					});
		}
		return btnCopyToOtherBOM;
	}
	/**
	 * 获取tbVersion
	 * @return
	 */
	public JTable getTbVersion() {
		if (tbVersion == null) {
			tbVersion = new JTable();
			tbVersion.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if(tableVersion==null){
								List lists = new ArrayList();
								initDetailTable(lists);
								return ;
							}
							if(tableVersion.getCurrentRow()==null){
								List lists = new ArrayList();
								initDetailTable(lists);
								return;
							}
							OwpBomVersion version = (OwpBomVersion) tableVersion.getCurrentRow();
							List list = owpAnalyAction
									.findMaterielBomDetail(new Request(
											CommonVars.getCurrUser()), version);
							initDetailTable(list);
						}
					});

			
		}
		return tbVersion;
	}
	
} // @jve:decl-index=0:visual-constraint="5,7"
