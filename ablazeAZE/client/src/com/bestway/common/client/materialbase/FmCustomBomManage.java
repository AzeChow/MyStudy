/*
 * Created on 2004-11-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.client.common.tableeditor.JNumberTableCellEditor;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.BomStructureType;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator * // change the template for this generated type
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates 报关常用工厂BOM checked by 陈井彬 2008.10.11
 */
@SuppressWarnings("unchecked")
public class FmCustomBomManage extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	// private DefaultTreeModel model;

	private JToolBar jToolBar = null;

	private JSplitPane jSplitPane = null; // @jve:decl-index=0:

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	/**
	 * 成品/半成品表格
	 */
	private JTable tbMaster = null;

	/**
	 * 版本表格
	 */
	private JTable tbVersion = null;

	private JScrollPane jScrollPane = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JTable tbDetail = null;

	private JScrollPane jScrollPane1 = null;

	/**
	 * 料件操作接口
	 */
	private MaterialManageAction materialManageAction = null;

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

	private JLabel jLabel1 = null;

	private JToolBar jToolBar1 = null;

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

	private JPanel jPanel4 = null;

	/**
	 * 新增成品
	 */
	private JButton btnAddMaster = null;

	/**
	 * 删除成品
	 */
	private JButton btnDeleteMaster = null;

	/**
	 * 从工厂BOM转换
	 */
	private JButton btnTransfer = null;

	private JPanel jPanel5 = null;

	/**
	 * 查询操作页面
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;

	/**
	 * 变更损耗
	 */
	private JButton btnChangeWaste = null;

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
	 * 版本js
	 */
	private JScrollPane jScrollPaneV;

	private JPopupMenu jPopupMenu; // @jve:decl-index=0:

	private JMenuItem miSeniFinishProduct;

	private JMenuItem miFinishProduct;

	/**
	 * This is the default constructor
	 */
	public FmCustomBomManage() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		materialManageAction.checkCustomBomManageAuthority(new Request(CommonVars
		 .getCurrUser()));
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
								MaterialBomMaster master = (MaterialBomMaster) tableModel
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
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("报关常用工厂BOM");
		this.setHelpId("customBomManage");
		this.setSize(787, 459);
		this.setContentPane(getJContentPane());
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						// (new InitBomMaster()).start();
						// initMasterData();
						pnCommonQueryPage.setInitState();
						// MaterialBomMaster master = (MaterialBomMaster)
						// tableModelMaster
						// .getCurrentRow();
						// initVersion(master);
						setState();
					}
				});
	}

	/**
	 * 
	 * @author Administrator 从工厂BOM转换
	 */
	class ImportBomFromEnterpriseNoVersionNoDate extends Thread {
		/**
		 * 物料主档资料
		 */
		private List list = new ArrayList();

		/**
		 * 父件版本号
		 */
		private Integer version;
		private boolean isMerge = false;

		public ImportBomFromEnterpriseNoVersionNoDate(List list, Integer version, boolean isMerge) {
			super();
			this.list = list;
			this.version = version;
			this.isMerge = isMerge;
		}

		public void run() {
			try {
				CommonStepProgress.showStepProgressDialog(null);
				CommonStepProgress.setStepMessage("系统正在导入数据，请稍后...");
				CommonStepProgress.setStepProgressMaximum(list.size());
				int num = 0;
				System.out.println("======list size = " + list.size());
				List lsTemp = new ArrayList();
				int j = 1;
				for (int i = 0; i < list.size(); i++) {
					EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) list
							.get(i);
					lsTemp.add(enterpriseMaterial);
					num++;
					if ((num == 100) || (i == list.size() - 1)) {
						System.out.println("======第" + j + "个一百个正在运行");
						materialManageAction
								.transferBomFromEnterpriseNoVersionNoDate(
										new Request(CommonVars.getCurrUser()),
										lsTemp, version, isMerge);
						num = 0;
						lsTemp.clear();
						j++;
					}
					CommonStepProgress.setStepProgressValue(i + 1);
				}
				CommonStepProgress.setStepMessage("系统正在加载数据，请稍后...");
				pnCommonQueryPage.setInitState();
				CommonStepProgress.closeStepProgressDialog();
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmCustomBomManage.this, "导入数据出错："
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * 
	 * @author Administrator 从工厂BOM转换-依据时间
	 */
	class ImportBomFromEnterpriseByDate extends Thread {
		private List list = new ArrayList();

		public ImportBomFromEnterpriseByDate(List list) {
			super();
			this.list = list;
		}

		public void run() {
			try {
				CommonStepProgress.showStepProgressDialog(null);
				CommonStepProgress.setStepMessage("系统正在导入数据，请稍后...");
				CommonStepProgress.setStepProgressMaximum(list.size());
				int num = 0;
				List lsTemp = new ArrayList();
				System.out.println("======list size = " + list.size());
				int j = 1;
				for (int i = 0; i < list.size(); i++) {

					EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) list
							.get(i);
					lsTemp.add(enterpriseMaterial);
					num++;
					if ((num == 100) || (i == list.size() - 1)) {
						System.out.println("======第" + j + "个一百个正在运行");
						materialManageAction.transferBomFromEnterpriseByDate(
								new Request(CommonVars.getCurrUser()), lsTemp);
						num = 0;
						lsTemp.clear();
						j++;
					}
					CommonStepProgress.setStepProgressValue(i + 1);
				}
				pnCommonQueryPage.setInitState();
				// CommonStepProgress.setStepMessage("系统正在加载数据，请稍后...");
				// // initMasterData();
				// pnCommonQueryPage.setInitState();
				// MaterialBomMaster master = (MaterialBomMaster)
				// tableModelMaster
				// .getCurrentRow();
				// initVersion(master);
				CommonStepProgress.closeStepProgressDialog();
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmCustomBomManage.this, "导入数据出错："
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * 
	 * @author Administrator 从工厂BOM转换-依据版本号
	 */
	class ImportBomFromEnterpriseByVersion extends Thread {
		private List list = new ArrayList();

		public ImportBomFromEnterpriseByVersion(List list) {
			super();
			this.list = list;
		}

		public void run() {
			try {
				CommonStepProgress.showStepProgressDialog(null);
				CommonStepProgress.setStepMessage("系统正在导入数据，请稍后...");
				CommonStepProgress.setStepProgressMaximum(list.size());
				int num = 0;
				List lsTemp = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					
					EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) list
							.get(i);
					lsTemp.add(enterpriseMaterial);
					num++;
					if ((num == 100) || (i == list.size() - 1)) {
						materialManageAction
								.transferBomFromEnterpriseByVersion(
										new Request(CommonVars.getCurrUser()),
										lsTemp);
						num = 0;
						lsTemp.clear();
					}
					CommonStepProgress.setStepProgressValue(i + 1);
				}
				
				CommonStepProgress.setStepMessage("系统正在加载数据，请稍后...");
				pnCommonQueryPage.setInitState();
				CommonStepProgress.closeStepProgressDialog();
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmCustomBomManage.this, "导入数据出错："
						+ e.getMessage(), "提示", 2);
			}
		}
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
				materialManageAction.updateMaterialBomWaste(request, versionNo,
						pFourCode, mFourCode, waste, isByUnitUsed);
				CommonStepProgress.setStepMessage("系统正在加载数据，请稍后...");
				pnCommonQueryPage.setInitState();
				CommonStepProgress.closeStepProgressDialog();
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmCustomBomManage.this, "更新数据出错："
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

		long beginTime = System.currentTimeMillis();
		List list = materialManageAction.findMaterielBomMaster(new Request(
				CommonVars.getCurrUser()), index, length, property, value,
				isLike);
		long endTime = System.currentTimeMillis();
		System.out.println("------Search Time :" + (endTime - beginTime)
				/ 1000.0);
		return list;
	}

	// class InitBomMaster extends Thread {
	// public void run() {
	// try {
	// CommonProgress.showProgressDialog();
	// CommonProgress.setMessage("系统正在加载数据，请稍后...");
	// initMasterData();
	// MaterialBomMaster master = (MaterialBomMaster) tableModelMaster
	// .getCurrentRow();
	// initVersion(master);
	// CommonProgress.closeProgressDialog();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(FmCustomBomManage.this, "加载数据出错："
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
	// private synchronized void initVersion(MaterialBomMaster master) {
	// // this.selectedNode=null;
	// List lsVersion = master == null ? new ArrayList()
	// : materialManageAction.findMaterielBomVersion(new Request(
	// CommonVars.getCurrUser()), master);
	// ObjectNode top = new ObjectNode(null);
	// treeBom.setModel(new DefaultTreeModel(top));
	// if (lsVersion.size() == 0) {
	// initDetailTable(new ArrayList());
	// } else {
	// initVersionTree(lsVersion);
	// ObjectNode root = (ObjectNode) treeBom.getModel().getRoot();
	// ObjectNode firstNode = null;
	// if (root != null && root.getChildCount() > 0) {
	// firstNode = (ObjectNode) root.getChildAt(0);
	// }
	// // List list = (firstNode == null || firstNode.getVersion() == null)
	// // ? new ArrayList()
	// // : materialManageAction.findMaterielBomDetail(new Request(
	// // CommonVars.getCurrUser()), firstNode.getVersion());
	// // initDetailTable(list);
	// // this.selectedNode = firstNode;
	// expandTreeNode(treeBom, (ObjectNode) treeBom.getModel().getRoot());
	// int index = root.getIndex(firstNode);
	// treeBom.setSelectionRow(index);
	// }
	// }
	/**
	 * 初始化BOM版本的TREE
	 * 
	 * @param master
	 */
	private synchronized void initVersion(MaterialBomMaster master) {
		// 获取版本
		List lsVersion = master == null ? new ArrayList()
				: materialManageAction.findMaterielBomVersion(new Request(
						CommonVars.getCurrUser()), master);
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("版本号", "version", 40));
				list.add(addColumn("开始有效期", "beginDate", 70));
				list.add(addColumn("结束有效期", "endDate", 70));
				list.add(addColumn("实际单重", "factUnitWeight", 60));
				list.add(addColumn("计算单重", "countUnitWeight", 60));
				list.add(addColumn("备注", "notes", 60));
				// list.add(addColumn("商品大类", "bill5", 100));
				return list;
			}
		};
		tableVersion = new JTableListModel(tbVersion, lsVersion,
				jTableListModelAdapter);
		// tableVersion.setSelectRow(0);
		MaterialBomVersion version = (MaterialBomVersion) tableVersion
				.getCurrentRow();
		if (version == null) {
			return;
		}
		List list = materialManageAction.findMaterielBomDetail(new Request(
				CommonVars.getCurrUser()), version);
		initDetailTable(list);
	}


	/**
	 * 获取选择的树结点
	 * 
	 * @return
	 */
	private MaterialBomVersion getSelectedBomVersion() {
		return (MaterialBomVersion) tableVersion.getCurrentRow();
	}

	/**
	 * 初始化成品/半成品表格
	 * 
	 * @param list
	 * @return
	 */
	private synchronized JTableListModel initMasterTable(final List list) {
		// tableModelMaster = new JTableListModel(tbMaster, list,
		// new JTableListModelAdapter() {
		// public List InitColumns() {
		// List<JTableListColumn> list = new Vector<JTableListColumn>();
		// list.add(addColumn("料号", "materiel.ptNo", 150));
		// list
		// .add(addColumn("商品名称", "materiel.factoryName",
		// 260));
		// list
		// .add(addColumn("型号规格", "materiel.factorySpec",
		// 260));
		// list
		// .add(addColumn("计量单位", "materiel.ptUnit.name",
		// 100));
		// // list.add(addColumn("商品大类", "bill5", 100));
		// return list;
		// }
		// });
		JTableListModel.dataBind(tbMaster, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("料别", "materiel.scmCoi.name", 100));
				list.add(addColumn("料号", "materiel.ptNo", 150));
				list.add(addColumn("商品名称", "materiel.factoryName", 220));
				list.add(addColumn("型号规格", "materiel.factorySpec", 220));
				list.add(addColumn("工厂单位", "materiel.calUnit.name", 100));
				// list.add(addColumn("商品大类", "bill5", 100));
				return list;
			}
		});
		tableModelMaster = (JTableListModel) tbMaster.getModel();
		MaterialBomMaster master = (MaterialBomMaster) tableModelMaster
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
				list.add(addColumn("子级料号", "materiel.ptNo", 150));
				list.add(addColumn("子级料件名称", "materiel.factoryName", 200));
				list.add(addColumn("工厂单位", "materiel.calUnit.name", 80));
				list.add(addColumn("单耗", "unitWaste", 80, 20));
				list.add(addColumn("损耗", "waste", 80, 20));
				list.add(addColumn("单项用量", "unitUsed", 80, 20));
				return list;
			}
		};
		tableModelDetail = new JTableListModel(tbDetail, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		jTableListModelAdapter.setEditableColumn(4);
		jTableListModelAdapter.setEditableColumn(5);
		tableModelDetail.setAllowSetValue(true);
		CompanyOther other = CommonVars.getOther();
		// 保留小数位
		Integer d = 6;
		if (other != null) {
			d = (other.getBomChangeBit() == null) ? 0 : other
					.getBomChangeBit();
		}
		tableModelDetail.setColumnsFractionCount(4, d);
		tableModelDetail.setColumnsFractionCount(5, d);
		tableModelDetail.setColumnsFractionCount(6, d);
		JNumberTableCellEditor tableCellEditor = new JNumberTableCellEditor(d);
		tableCellEditor.addAutoCalcListener(new TableCellEditorListener() {
			public void run(TableCellEditorParameter param) {
				commitValueChange((MaterialBomDetail) param.getEditingData());
			}
		});
//		tbDetail.getColumnModel().getColumn(4).setCellEditor(tableCellEditor);
//		tbDetail.getColumnModel().getColumn(4).setCellRenderer(
//				new JNumberForcedTableCellRenderer());
//		tbDetail.getColumnModel().getColumn(5).setCellEditor(tableCellEditor);
//		tbDetail.getColumnModel().getColumn(5).setCellRenderer(
//				new JNumberForcedTableCellRenderer());
	}

	/**
	 * 计算单项用量
	 * 
	 * @param detail
	 */
	private void commitValueChange(MaterialBomDetail detail) {
		if (detail == null) {
			return;
		}
		double unitWaste = (detail.getUnitWaste() == null ? 0.0 : detail
				.getUnitWaste());
		double waste = (detail.getWaste() == null ? 0.0 : detail.getWaste());
		if (waste >= 1 || waste < 0) {
			detail.setWaste(0.0);
			waste = 0.0;
		}
		CompanyOther other = CommonVars.getOther();
		// 保留小数位
		Integer d = 6;
		if (other != null) {
			d = (other.getBomChangeBit() == null) ? Integer.valueOf(0) : other
					.getBomChangeBit();
		}
		double unitUsed = CommonVars.getDoubleByDigit(unitWaste / (1 - waste),
				d);
		detail.setUnitUsed(unitUsed);
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
			jLabel1 = new JLabel();
			jToolBar = new JToolBar();
			jLabel1.setText("                         ");
			jToolBar.add(getBtnTransfer());
			jToolBar.add(getBtnChangeWaste());
			jToolBar.add(getBtnAddMaster());
			jToolBar.add(getBtnDeleteMaster());
			// jToolBar.add(jLabel2);
			jToolBar.add(getJButton());
			// jToolBar.add(getTfSearchValue());
			// jToolBar.add(getBtnSearch());
			// jToolBar.add(getBtnDeclarate());
			// jToolBar.add(getBtnChange());
			jToolBar.add(getBtnExit());
			jToolBar.add(jLabel1);
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
			// tbMaster.addMouseListener(new java.awt.event.MouseAdapter() {
			// public void mouseClicked(java.awt.event.MouseEvent e) {
			//
			// if (e.getClickCount() == 2) {
			// if (tableModelMaster.getCurrentRow() == null) {
			// return;
			// }
			// // new InitBomVersion().start();
			// MaterialBomMaster master = (MaterialBomMaster) tableModelMaster
			// .getCurrentRow();
			// initVersion(master);
			//						
			// }
			// }
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
							MaterialBomMaster master = (MaterialBomMaster) tableModelMaster
									.getCurrentRow();

							System.out.println("master="
									+ master.getMateriel().getPtNo());
							initVersion(master);

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
			jPanel2.add(getJScrollPaneV(), java.awt.BorderLayout.CENTER);
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

	private JScrollPane getJScrollPaneV() {
		if (jScrollPaneV == null) {
			jScrollPaneV = new JScrollPane();
			jScrollPaneV.setViewportView(getTbVersion());
			jScrollPaneV
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPaneV;
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
			jToolBar1.add(getBtnDeleteDetail());
			jToolBar1.add(getBtnSave());
			jToolBar1.add(getBtnCancel());
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
										FmCustomBomManage.this, "请选择成品", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							MaterialBomMaster master = (MaterialBomMaster) tableModelMaster
									.getCurrentRow();
							DgCustomBomVersion dg = new DgCustomBomVersion();
							dg.setMaster(master);
							dg.setDataState(DataState.ADD);
							dg.setVisible(true);
							if (dg.isOk()) {
								MaterialBomVersion version = dg.getVersion();
								tableVersion.addRow(version);
							}
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
			btnEditVersion
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (getSelectedBomVersion() == null) {
								JOptionPane.showMessageDialog(
										FmCustomBomManage.this, "请选择成品的版本",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgCustomBomVersion dg = new DgCustomBomVersion();
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
										FmCustomBomManage.this, "请选择成品的版本",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmCustomBomManage.this, "你确定要删除此版本吗", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							materialManageAction.deleteMaterielBomVersion(
									new Request(CommonVars.getCurrUser()),
									getSelectedBomVersion());
							tableVersion.deleteRow(getSelectedBomVersion());
							// else {
							// selectedNode = null;
							// }
							// new OpenDetailData(selectedNode.getVersion())
							// .start();
							MaterialBomVersion version = getSelectedBomVersion();
							List list = version == null ? new ArrayList()
									: materialManageAction
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
						JOptionPane.showMessageDialog(FmCustomBomManage.this,
								"请选择成品的版本", "提示", JOptionPane.OK_OPTION);
						return;
					}
					List list = MaterialQuery
							.getInstance()
							.findNotInBomDetailMaterial(getSelectedBomVersion());
					if (list == null || list.size() <= 0) {
						return;
					}

					long beginTime = System.currentTimeMillis();
					list = materialManageAction.saveMaterialBomDetail(
							new Request(CommonVars.getCurrUser()),
							getSelectedBomVersion(), list);

					long endTime = System.currentTimeMillis();
					System.out.println("------Save Time :"
							+ (endTime - beginTime) / 1000.0);
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
										FmCustomBomManage.this, "请选择你要修改的料件",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							MaterialBomDetail detail = (MaterialBomDetail) tableModelDetail
									.getCurrentRow();
							DgCustomBomDetail dg = new DgCustomBomDetail();
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
										FmCustomBomManage.this, "请选择你要删除的料件",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmCustomBomManage.this, "你确定要删除此料件吗", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							MaterialBomDetail detail = (MaterialBomDetail) tableModelDetail
									.getCurrentRow();

							long beginTime = System.currentTimeMillis();
							materialManageAction.deleteMaterielBomDetail(
									new Request(CommonVars.getCurrUser()),
									detail);
							long endTime = System.currentTimeMillis();
							System.out.println("------Save Time :"
									+ (endTime - beginTime) / 1000.0);

							tableModelDetail.deleteRow(detail);
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
					Component comp = (Component) e.getSource();
					getJPopupMenu().show(comp, 0, comp.getHeight());
					// List list = MaterialQuery.getInstance()
					// .findNotInBomMasterMaterial();
					// if (list == null || list.size() <= 0) {
					// return;
					// }
					// list = materialManageAction.saveMaterialBomMaster(
					// new Request(CommonVars.getCurrUser()), list);
					// tableModelMaster.addRows(list);
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
										FmCustomBomManage.this, "请选择你要删除的成品",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmCustomBomManage.this, "你确定要删除选择的成品吗", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							MaterialBomMaster master = null;
							List<MaterialBomMaster> list = tableModelMaster
									.getCurrentRows();
							long beginTime = System.currentTimeMillis();
							
							CommonStepProgress.showStepProgressDialog();
							CommonStepProgress.setStepMessage("系统正在删除数据，请稍后...");
							for (int i = 0; i < list.size(); i++) {
								master = list.get(i);
								materialManageAction.deleteMaterielBomMaster(
										new Request(CommonVars.getCurrUser()),
										master);

								tableModelMaster.deleteRow(master);
								CommonStepProgress.setStepMessage("删除报关常用工厂BOM：" + master.getMateriel().getPtNo());
							}
							CommonStepProgress.closeStepProgressDialog();
							long endTime = System.currentTimeMillis();
							System.out.println("------Save Time :"
									+ (endTime - beginTime) / 1000.0);

							
						}
					});
		}
		return btnDeleteMaster;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnTransfer() {
		if (btnTransfer == null) {
			btnTransfer = new JButton();
			btnTransfer.setText("从工厂BOM转换");
			btnTransfer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String bomType = "";
					if (CommonVars.getBomStructureType() == BomStructureType.NO_VERSION_NO_DATE) {
						bomType = "系统只有一个版本的BOM";
					} else if (CommonVars.getBomStructureType() == BomStructureType.HAVE_VERSION_NO_DATE) {
						bomType = "系统以成品版本号来区分版本";
					} else if (CommonVars.getBomStructureType() == BomStructureType.NO_VERSION_HAS_DATE) {
						bomType = "系统以成品日期来区分版本";
					}
					if (JOptionPane.showConfirmDialog(FmCustomBomManage.this,
							"系统工厂BOM版本区分方式为:" + bomType + "\n你确定要从工厂BOM转换吗",
							"提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					if (CommonVars.getBomStructureType() == BomStructureType.NO_VERSION_NO_DATE) {
						// 无成品版本号，BOM中存在最新的半成品和料件的耗用情况
						DgVersionSelected dgVersion = new DgVersionSelected();
						dgVersion.setVisible(true);
						Integer version = dgVersion.getVersion();
						if (version == null) {
							return;
						}
						DgBomTopProduct dg = new DgBomTopProduct();
						dg
								.setBomStructureType(BomStructureType.NO_VERSION_NO_DATE);
						dg.setVersion(version);
						dg.setVisible(true);
						List list = dg.getLsResult();
						if (list.size() > 0) {
							new ImportBomFromEnterpriseNoVersionNoDate(list,
									version, dg.getIsMerge()).start();
						}
						// -----
					} else if (CommonVars.getBomStructureType() == BomStructureType.HAVE_VERSION_NO_DATE) {
						// 以BOM成品版本号来区分不同的版本
						DgBomTopProduct dg = new DgBomTopProduct();
						dg
								.setBomStructureType(BomStructureType.HAVE_VERSION_NO_DATE);
						dg.setVisible(true);
						List list = dg.getLsResult();
						if (list.size() > 0) {
							new ImportBomFromEnterpriseByVersion(list).start();
						}
					} else if (CommonVars.getBomStructureType() == BomStructureType.NO_VERSION_HAS_DATE) {
						// 无成品版本号，以成品开始日期失效日期来区分不同的BOM版本
						DgBomTopProduct dg = new DgBomTopProduct();
						dg
								.setBomStructureType(BomStructureType.NO_VERSION_HAS_DATE);
						dg.setVisible(true);
						List list = dg.getLsResult();
						if (list.size() > 0) {
							new ImportBomFromEnterpriseByDate(list).start();
						}
					}
				}
			});
		}
		return btnTransfer;
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
			jPanel5.add(getPnCommonQueryPage(), BorderLayout.SOUTH);
		}
		return jPanel5;
	}

	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	private PnCommonQueryPage getPnCommonQueryPage() {
		if (pnCommonQueryPage == null) {
			pnCommonQueryPage = new PnCommonQueryPage() {

				private static final long serialVersionUID = 1L;

				@Override
				public JTableListModel initTable(List dataSource) {
					return FmCustomBomManage.this.initMasterTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					if (isFirstInitData) {
						isFirstInitData = false;
						return new Vector();
					} else {
						return FmCustomBomManage.this.getDataSource(index,
								length, property, value, isLike);

					}
				}

			};
		}
		return pnCommonQueryPage;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeWaste() {
		if (btnChangeWaste == null) {
			btnChangeWaste = new JButton();
			btnChangeWaste.setText("变更损耗");
			btnChangeWaste
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgUpdateMaterialBomWaste dg = new DgUpdateMaterialBomWaste();
							dg.setVisible(true);
							if (dg.isOk()) {
								Double waste = dg.getWaste();
								Integer version = dg.getVersion();
								String pFourCode = dg.getPFourCode();
								String mFourCode = dg.getMFourCode();
								boolean isByUnitUsed = dg.isByUnitUsed();
								new UpdateMaterialBomWaste(version, pFourCode,
										mFourCode, waste, isByUnitUsed).start();
							}
						}
					});
		}
		return btnChangeWaste;
	}

	/**
	 * 设置变更损耗的状态
	 */
	private void setState() {
		this.btnChangeWaste
				.setVisible(CommonVars.getBomStructureType() == BomStructureType.NO_VERSION_NO_DATE);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnEditUnitWaste == null) {
			btnEditUnitWaste = new JButton();
			btnEditUnitWaste.setText("修改料件成品耗用");
			btnEditUnitWaste
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgEditMaterialBomUnitWaste dg = new DgEditMaterialBomUnitWaste();
							dg.setVisible(true);
							MaterialBomMaster master = (MaterialBomMaster) tableModelMaster
									.getCurrentRow();
							initVersion(master);
						}
					});
		}
		return btnEditUnitWaste;
	}

	/**
	 * This method initializes jButton
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
						List list = tableModelDetail.getList();
						for (int i = 0; i < list.size(); i++) {
							MaterialBomDetail detail = (MaterialBomDetail) list
									.get(i);
							// detail.setUnitUsed(detail.getUnitWaste()
							// / (1 - detail.getWaste()));

							long beginTime = System.currentTimeMillis();
							detail = materialManageAction
									.saveMaterialBomDetail(new Request(
											CommonVars.getCurrUser()), detail);
							long endTime = System.currentTimeMillis();
							System.out.println("------Save Time :"
									+ (endTime - beginTime) / 1000.0);

							tableModelDetail.updateRow(detail);
						}
					}
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton1
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
							if (tableModelDetail.getRowCount() <= 0) {
								JOptionPane.showMessageDialog(
										FmCustomBomManage.this, "没有料件可复制!",
										"提示!", JOptionPane.WARNING_MESSAGE);
								return;
							}
							String bom = JOptionPane.showInputDialog(
									FmCustomBomManage.this, "请输入将要复制到的版本号:",
									"版本号输入", JOptionPane.PLAIN_MESSAGE);
							// 新添加版本号
							Integer intbom = null;
							try {
								intbom = Integer.parseInt(bom);
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(
										FmCustomBomManage.this,
										"输入有错,版本号只能是整数,请重新输入!", "提示!",
										JOptionPane.WARNING_MESSAGE);
								return;
							}

							MaterialBomDetail detail = (MaterialBomDetail) tableModelDetail
									.getValueAt(0);
							Map map = materialManageAction
									.addMaterialBomVersionOrInsertMaterialBomDetail(
											new Request(CommonVars
													.getCurrUser()), detail
													.getVersion(), intbom);
							if (Boolean
									.parseBoolean(map.get("isExist") == null ? "false"
											: (map.get("isExist").toString()))) {
								JOptionPane.showMessageDialog(
										FmCustomBomManage.this, "所输入的版本号已存在!",
										"提示!", JOptionPane.WARNING_MESSAGE);
								return;
							} else {
								// initVersionTree(new ArrayList());
								initVersion(detail.getVersion().getMaster());

							}
							// tableModelDetail.addRows(list);
						}
					});
		}
		return btnCopyToOtherBOM;
	}

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
								List lists = new ArrayList();
								initDetailTable(lists);
								return;
							}
							if (tableVersion.getCurrentRow() == null) {
								List lists = new ArrayList();
								initDetailTable(lists);
								return;
							}
							MaterialBomVersion version = (MaterialBomVersion) tableVersion
									.getCurrentRow();
							List list = materialManageAction
									.findMaterielBomDetail(new Request(
											CommonVars.getCurrUser()), version);
							initDetailTable(list);
						}
					});

		}
		return tbVersion;
	}

	/**
	 * This method initializes miFinishProduct
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiFinishProduct() {
		if (miFinishProduct == null) {
			miFinishProduct = new JMenuItem();
			miFinishProduct.setText("新增成品");
			miFinishProduct
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = MaterialQuery.getInstance()
									.findNotInBomMasterMaterial();
							if (list == null || list.size() <= 0) {
								return;
							}

							long beginTime = System.currentTimeMillis();
							list = materialManageAction
									.saveMaterialBomMaster(new Request(
											CommonVars.getCurrUser()), list);

							long endTime = System.currentTimeMillis();
							System.out.println("------Save Time :"
									+ (endTime - beginTime) / 1000.0);
							tableModelMaster.addRows(list);
						}
					});
		}
		return miFinishProduct;
	}

	/**
	 * This method initializes miSeniFinishProduct
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiSeniFinishProduct() {
		if (miSeniFinishProduct == null) {
			miSeniFinishProduct = new JMenuItem();
			miSeniFinishProduct.setText("新增半成品");
			miSeniFinishProduct
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = MaterialQuery.getInstance()
									.findNotInBomMasterMaterial2();
							if (list == null || list.size() <= 0) {
								return;
							}
							long beginTime = System.currentTimeMillis();
							list = materialManageAction
									.saveMaterialBomMaster(new Request(
											CommonVars.getCurrUser()), list);
							long endTime = System.currentTimeMillis();
							System.out.println("------Add Time :"
									+ (endTime - beginTime) / 1000.0);

							tableModelMaster.addRows(list);
						}
					});
		}
		return miSeniFinishProduct;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.add(getMiSeniFinishProduct());
			jPopupMenu.add(getMiFinishProduct());
		}
		return jPopupMenu;
	}

} // @jve:decl-index=0:visual-constraint="5,7"
