package com.bestway.common.client.erpbillcenter;

/**
 * 对应关系
 * 
 * 刘民添加部分注释
 * 
 * 修改时间： 2008-9-13
 * 
 * @author change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * 
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.client.util.JTableContextPopupMenu;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.erpbillcenter.authority.ErpBillCenterAuthority;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmFactoryAndFactualCustomsRalation extends JInternalFrameBase {
	private JMenuItem miEditHshRelation = null;

	private JToolBar jJToolBarBar = null;

	private JLabel jLabel = null;

	private AttributiveCellTableModel tableModel = null;

	/**
	 * 物品类型
	 */
	private JComboBox cbbMaterialType = null;

	/**
	 *关闭
	 */
	private JButton btnExit = null;

	private JScrollPane jScrollPane = null;

	/**
	 * 企业物料与实际报关商品对应表格
	 */
	private JTable tbRelation = null;

	private CasAction casAction = null;

	/**
	 * 合并显示 [一个物料对多个报关]
	 */
	private JRadioButton rbUniteDisplay = null;

	/**
	 * 合并显示 [一个报关对多个物料]
	 */
	private JRadioButton rbUniteDisplay1 = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="926,-4"

	/**
	 * 删除
	 */
	private JButton btnDelete = null;

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:visual-constraint="897,62"

	/**
	 * 从归并关系导入
	 */
	private JButton btnMergerRelationInput = null;

	/**
	 * 打印
	 */
	private JButton btnPrint = null;

	/**
	 * 新增
	 */
	private JButton btnAdd = null;

	private JMenuItem itmBcs = null;

	/**
	 * 从文件导入
	 */
	private JButton btnFileImport = null;

	/**
	 * 查询
	 */
	private JButton btnQuerry = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	/**
	 * 查询操作页面
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;

	/**
	 * 设置数据源
	 */
	private List dataSource = null; // @jve:decl-index=0:

//	/**
//	 * 进出仓栏小数位数控制，默认为6
//	 */
//	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
//			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
//			.getOtherOption().getInOutMaximumFractionDigits(); // @jve:decl-index=0:
	private Integer maximumFractionDigits = 9; // @jve:decl-index=0:
	
	private JToolBar jToolBar = null;

	private JPanel pnTop = null;

	private JToolBar jToolBar1 = null;

	private JPanel pnUnder = null;

	private JPanel pnTopOne = null;

	private ErpBillCenterAuthority erpBillCenterAuthority = null;

	/**
	 * 修改
	 */
	private JButton btnEdit = null;

	/**
	 * 当前选择的物料类型 wss为了方便打印时标题方便，所以设置为全局变量
	 */
	private String currType = null;

	/**
	 * 选择的是报关信息（右边）
	 * 
	 */
	private boolean isHsInfo = false;

	private JMenuItem miEditHsInfo = null; // @jve:decl-index=0:visual-constraint="475,499"

	/**
	 * This method initializes 构造函数
	 */
	public FmFactoryAndFactualCustomsRalation() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
				.getApplicationContext().getBean("erpBillCenterAuthority");
		erpBillCenterAuthority.checkRelationByBrower(new Request(
				CommonVars.getCurrUser()));
		initialize();
		tbRelation = new MultiSpanCellTable();
		tbRelation.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON3) {
					return;
				}
				JTableContextPopupMenu jp = (JTableContextPopupMenu) ((JTableListModel) tbRelation
						.getModel()).getJTableContextPopupMenu();
				int[] columns = ((MultiSpanCellTable) tbRelation)
						.getSelectedColumns();
				int[] rows = ((MultiSpanCellTable) tbRelation)
						.getSelectedRows();
				if (columns.length < 1 || rows.length < 1) {
					return;
				}
				if (getRbUniteDisplay1().isSelected()) {
					if (columns[0] > 0 && columns[0] < 8) {
						jp.add(getMiEditHsnRelation());
						jp.remove(getMiEditHsInfo());// wss2010.11.02加
					} else {
						jp.remove((getMiEditHsnRelation()));
						jp.add(getMiEditHsInfo());// wss2010.11.02加

					}
				} else if (getRbUniteDisplay().isSelected()) {
					if (columns[0] > 7 && columns[0] < 15) {
						jp.add(getMiEditHsnRelation());
						jp.remove(getMiEditHsInfo());// wss2010.11.02加
					} else {
						jp.remove((getMiEditHsnRelation()));
						jp.add(getMiEditHsInfo());// wss2010.11.02加
					}
				}
			}

			/**
			 * 捕捉鼠标事件
			 */
			public void mouseClicked(MouseEvent e) {
				int[] columns = ((MultiSpanCellTable) tbRelation)
						.getSelectedColumns();
				int[] rows = ((MultiSpanCellTable) tbRelation)
						.getSelectedRows();
				if (columns.length < 1 || rows.length < 1) {
					return;
				}
				if (getRbUniteDisplay1().isSelected()) {
					if (columns[0] > 0 && columns[0] < 8) {
						tbRelation.setColumnSelectionInterval(1, 7);
						tbRelation.setRowSelectionInterval(rows[0],
								rows[rows.length - 1]);

						isHsInfo = false;// 选中的是工厂信息

						return;

					} else if (columns[0] > 8 && columns[0] < 15) {
						tbRelation.setColumnSelectionInterval(9, 14);
						tbRelation.setRowSelectionInterval(rows[0],
								rows[rows.length - 1]);

						isHsInfo = true;// 选中的是报关信息

						return;
					} else if (columns[0] == 8) {
						tbRelation.setColumnSelectionInterval(8, 14);
						tbRelation.setRowSelectionInterval(rows[0],
								rows[rows.length - 1]);

						isHsInfo = true;// 选中的是报关信息

						return;
					}
				} else if (getRbUniteDisplay().isSelected()) {
					if (columns[0] > 0 && columns[0] < 8) {
						tbRelation.setColumnSelectionInterval(1, 7);
						tbRelation.setRowSelectionInterval(rows[0],
								rows[rows.length - 1]);

						isHsInfo = false;// 选中的是工厂信息

						return;

					} else if (columns[0] > 7 && columns[0] < 15) {
						tbRelation.setColumnSelectionInterval(8, 14);
						tbRelation.setRowSelectionInterval(rows[0],
								rows[rows.length - 1]);

						isHsInfo = true;// 选中的是报关信息

						return;
					}
				}
			}
		});
		getButtonGroup1();

		initTable(new ArrayList());
	}

	/**
	 * This method initializes miEditHshRelation
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiEditHsnRelation() {
		if (miEditHshRelation == null) {
			miEditHshRelation = new JMenuItem();
			miEditHshRelation.setText("        修改企业物料");
			miEditHshRelation
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() == null) {
								return;
							}
							DgEditUniteConvert dg = new DgEditUniteConvert();
							dg
									.setFdata((FactoryAndFactualCustomsRalation) tableModel
											.getCurrentRow());
							dg
									.setOldEmsNo(((FactoryAndFactualCustomsRalation) tableModel
											.getCurrentRow())
											.getStatCusNameRelationHsn()
											.getEmsNo());
							dg.setTableModel(tableModel);
							dg.setMaterielType(((ItemProperty) cbbMaterialType
									.getSelectedItem()).getCode());
							dg.setVisible(true);
						}

					});
		}

		return miEditHshRelation;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(872, 444));
		this.setContentPane(getJSplitPane());
		this.setTitle("工厂物料与实际报关对应关系");

	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.LEFT);
			fl.setVgap(1);
			fl.setHgap(1);
			jLabel = new JLabel();
			jLabel.setPreferredSize(new Dimension(60, 25));
			jLabel.setBounds(new Rectangle(7, 3, 56, 22));
			jLabel.setText("\u7269\u6599\u7c7b\u578b");
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setFloatable(false);
			jJToolBarBar.setPreferredSize(new Dimension(352, 34));
			jJToolBarBar.setLayout(fl);
			jJToolBarBar.add(getPnTopOne());
			jJToolBarBar.add(getBtnQuerry());
			jJToolBarBar.add(getBtnAdd());
			jJToolBarBar.add(getBtnEdit());
			jJToolBarBar.add(getBtnDelete());
			jJToolBarBar.add(getBtnPrint());
			jJToolBarBar.add(getBtnExit());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes cbbMaterialType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterialType() {
		if (cbbMaterialType == null) {
			cbbMaterialType = new JComboBox();
			cbbMaterialType.setPreferredSize(new Dimension(85, 25));
			cbbMaterialType.setBounds(new Rectangle(64, 2, 128, 25));
			cbbMaterialType.addItem(new ItemProperty(
					MaterielType.FINISHED_PRODUCT, "成品"));
			cbbMaterialType.addItem(new ItemProperty(MaterielType.MATERIEL,
					"料件"));
			cbbMaterialType
					.addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
			cbbMaterialType.addItem(new ItemProperty(
					MaterielType.REMAIN_MATERIEL, "边角料"));
			cbbMaterialType.addItem(new ItemProperty(MaterielType.BAD_PRODUCT,
					"残次品"));
			cbbMaterialType.setSelectedIndex(0);
		}
		return cbbMaterialType;
	}

	/**
	 * 刷新数据显示
	 */
	private void refresh() {
		// List dataSource = new ArrayList();
		if (cbbMaterialType.getSelectedItem() != null) {
			String mtype = ((ItemProperty) cbbMaterialType.getSelectedItem())
					.getCode();
			getPnCommonQueryPage().setInitState();
		} else {
			if (dataSource == null)
				dataSource = new ArrayList();
		}
		cToMany(dataSource, false);
	}

	/**
	 * 初始化tableModel
	 * 
	 * @param dalist
	 * @return
	 */
	private JTableListModel initTable(List dalist) {
		tableModel = new AttributiveCellTableModel(
				(MultiSpanCellTable) tbRelation, dalist,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料号", "materiel.ptNo",
								"a.materiel.ptNo", 100));
						list.add(addColumn("商品名称", "materiel.factoryName",
								"a.materiel.factoryName", 100));
						list.add(addColumn("型号规格", "materiel.factorySpec",
								"a.materiel.factorySpec", 100));
						list.add(addColumn("净重", "materiel.ptNetWeight",
								"a.materiel.ptNetWeight", 70));
						list.add(addColumn("参考单价", "materiel.ptPrice",
								"a.materiel.ptPrice", 80));
						list.add(addColumn("单位", "materiel.calUnit.name",
								"a.materiel.calUnit.name", 50));
						list.add(addColumn("单位折算", "unitConvert", 50));
						list.add(addColumn("编码",
								"statCusNameRelationHsn.complex.code",
								"a.statCusNameRelationHsn.complex.code", 80));

						list.add(addColumn("名称",
								"statCusNameRelationHsn.cusName",
								"a.statCusNameRelationHsn.cusName", 140));
						list.add(addColumn("规格",
								"statCusNameRelationHsn.cusSpec",
								"a.statCusNameRelationHsn.cusSpec", 140));
						list.add(addColumn("单位",
								"statCusNameRelationHsn.cusUnit.name",
								"a.statCusNameRelationHsn.cusUnit.name", 40));
						if (cbbMaterialType != null
								&& cbbMaterialType.getSelectedItem() != null
								&& ((ItemProperty) cbbMaterialType
										.getSelectedItem()).getCode().equals(
										MaterielType.MACHINE))
							list.add(addColumn("合同协议号",
									"statCusNameRelationHsn.emsNo",
									"a.statCusNameRelationHsn.emsNo", 100));
						else
							list.add(addColumn("手册号",
									"statCusNameRelationHsn.emsNo",
									"a.statCusNameRelationHsn.emsNo", 100));

						list.add(addColumn("序号",
								"statCusNameRelationHsn.seqNum",
								"a.statCusNameRelationHsn.seqNum", 50));
						if (cbbMaterialType != null
								&& cbbMaterialType.getSelectedItem() != null
								&& ((ItemProperty) cbbMaterialType
										.getSelectedItem()).getCode().equals(
										MaterielType.FINISHED_PRODUCT))
							list.add(addColumn("成品版本号",
									"statCusNameRelationHsn.version",
									"a.statCusNameRelationHsn.version", 80));
						list.add(addColumn("管理类型",
								"statCusNameRelationHsn.projectName",
								"a.statCusNameRelationHsn.projectName", 60));
						list.add(addColumn("导入修改时间", "createDate",80));
						list.add(addColumn("备注", "remark",80));
						return list;
					}
				});
		TableColumnModel cm = tbRelation.getColumnModel();
		ColumnGroup gMaterial = new ColumnGroup("企业物料");
		gMaterial.add(cm.getColumn(1));
		gMaterial.add(cm.getColumn(2));
		gMaterial.add(cm.getColumn(3));
		gMaterial.add(cm.getColumn(4));
		gMaterial.add(cm.getColumn(5));
		gMaterial.add(cm.getColumn(6));
		gMaterial.add(cm.getColumn(7));
		ColumnGroup gRelation = new ColumnGroup("实际报关商品");
		gRelation.add(cm.getColumn(8));
		gRelation.add(cm.getColumn(9));
		gRelation.add(cm.getColumn(10));
		gRelation.add(cm.getColumn(11));
		gRelation.add(cm.getColumn(12));
		gRelation.add(cm.getColumn(13));
		gRelation.add(cm.getColumn(14));
		

		GroupableTableHeader header = (GroupableTableHeader) tbRelation
				.getTableHeader();
		header.addColumnGroup(gMaterial);
		header.addColumnGroup(gRelation);
		jScrollPane.setViewportView(tbRelation);
		tableModel.setMiRenderColumnEnabled(false);
		return tableModel;
	}

	/**
	 * 刷新tbRelation
	 * 
	 * @param isM
	 */
	private void refreshTable(boolean isM) {

		// 以料号相同的进行合并
		if (isM) {
			((MultiSpanCellTable) tbRelation).splitRows(1);
			((MultiSpanCellTable) tbRelation).combineRows(1, new int[] { 1, 2,
					3, 4, 5, 6 });
		}
		// 以名称+规格+单位+编码相同的进行合并
		else {
			// ((MultiSpanCellTable) tbRelation).combineRowsByIndeies(
			// new int[] { 8 }, new int[] { 8 });
			// ((MultiSpanCellTable) tbRelation).combineRowsByIndeies(new int[]
			// {
			// 12, 13 }, new int[] { 8, 9, 10, 11, 12, 13, 14 });
			((MultiSpanCellTable) tbRelation).splitRows2(new int[] { 8, 9, 10,
					11 });
			((MultiSpanCellTable) tbRelation).combineRowsByIndeies(new int[] {
					8, 9, 10, 11, 12, 13, 14 }, new int[] { 8, 9, 10, 11, 12,
					13, 14 });
		}
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setPreferredSize(new Dimension(85, 30));
			btnExit.setText("\u5173\u95ed");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
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
		if (tbRelation == null) {
			tbRelation = new JTable();
			tbRelation.setRowHeight(25);
		}
		return tbRelation;
	}

	/**
	 * This method initializes rbUniteDisplay
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbUniteDisplay() {
		if (rbUniteDisplay == null) {
			rbUniteDisplay = new JRadioButton();
			rbUniteDisplay.setText("合并显示 [一个物料对多个报关]");
			rbUniteDisplay.setMnemonic(KeyEvent.VK_UNDEFINED);
			rbUniteDisplay.setPreferredSize(new Dimension(95, 30));
			rbUniteDisplay
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							cToMany(true);
						}
					});
		}
		return rbUniteDisplay;
	}

	/**
	 * This method initializes rbUniteDisplay1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbUniteDisplay1() {
		if (rbUniteDisplay1 == null) {
			rbUniteDisplay1 = new JRadioButton();
			rbUniteDisplay1.setText("合并显示 [一个报关对多个物料]");
			rbUniteDisplay1.setPreferredSize(new Dimension(95, 30));
			rbUniteDisplay1.setMnemonic(KeyEvent.VK_UNDEFINED);
			rbUniteDisplay1.setSelected(true);
			rbUniteDisplay1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							cToMany(false);
						}
					});
		}
		return rbUniteDisplay1;
	}

	/**
	 * 对应关系
	 * 
	 * @param isM
	 */

	private void cToMany(boolean isM) {
		List<FactoryAndFactualCustomsRalation> flist = tableModel.getList();
		// if (flist == null || flist.size() == 0) {
		// return;
		// }
		// List templist = new ArrayList();
		// Map<String, List<FactoryAndFactualCustomsRalation>> tempMap = new
		// HashMap<String, List<FactoryAndFactualCustomsRalation>>();
		// for (FactoryAndFactualCustomsRalation data : flist) {
		// String key = "";
		// if (isM) {
		// key = data.getMateriel().getPtNo();
		// } else {
		// StatCusNameRelationHsn fc = data.getStatCusNameRelationHsn();
		// if (fc == null) {
		// continue;
		// }
		// key = fc.getComplex().getCode()
		// + "/"
		// + (fc.getEmsNo() == null ? "" : fc.getEmsNo())
		// + "/"
		// + (fc.getSeqNum() == null ? "" : fc.getSeqNum()
		// .toString())
		// + "/"
		// + (fc.getCusName() == null ? "" : fc.getCusName()
		// .toString())
		// + "/"
		// + (fc.getCusSpec() == null ? "" : fc.getCusSpec()
		// .toString())
		// + "/"
		// + (fc.getCusUnit() == null ? "" : fc.getCusUnit()
		// .getName());
		// }
		// if (tempMap.get(key) == null) {
		// List list = new ArrayList();
		// list.add(data);
		// tempMap.put(key, list);
		// } else {
		// tempMap.get(key).add(data);
		// }
		// }
		// Iterator itr = tempMap.keySet().iterator();
		// while (itr.hasNext()) {
		// String keys = itr.next().toString();
		// List<FactoryAndFactualCustomsRalation> dlist = tempMap.get(keys);
		// Map<String, List<FactoryAndFactualCustomsRalation>> tmap = new
		// HashMap<String, List<FactoryAndFactualCustomsRalation>>();
		// if (!isM) {// 实现第二层排序
		// for (int m = 0; m < dlist.size(); m++) {
		// FactoryAndFactualCustomsRalation data = dlist.get(m);
		// StatCusNameRelationHsn fc = data
		// .getStatCusNameRelationHsn();
		// String key = fc.getComplex().getCode()
		// + "/"
		// + (fc.getEmsNo() == null ? "" : fc.getEmsNo())
		// + "/"
		// + (fc.getSeqNum() == null ? "" : fc.getSeqNum()
		// .toString())
		// + "/"
		// + (fc.getCusName() == null ? "" : fc.getCusName()
		// .toString())
		// + "/"
		// + (fc.getCusSpec() == null ? "" : fc.getCusSpec()
		// .toString())
		// + "/"
		// + (fc.getCusUnit() == null ? "" : fc.getCusUnit()
		// .getName());
		// if (tmap.get(key) == null) {
		// List<FactoryAndFactualCustomsRalation> list = new
		// ArrayList<FactoryAndFactualCustomsRalation>();
		// list.add(data);
		// tmap.put(key, list);
		// } else {
		// tmap.get(key).add(data);
		// }
		// }
		// Iterator itrs = tmap.keySet().iterator();
		// while (itrs.hasNext()) {
		// List<FactoryAndFactualCustomsRalation> klist = tmap
		// .get(itrs.next().toString());
		// for (int i = 0; i < klist.size(); i++) {
		// templist.add(klist.get(i));
		// }
		// }
		//
		// } else {
		// for (int i = 0; i < dlist.size(); i++) {
		// templist.add(dlist.get(i));
		// }
		// }
		// }
		initTable(flist);
		refreshTable(isM);
	}

	/**
	 * 对应关系
	 * 
	 * @param isM
	 */

	private void cToMany(List<FactoryAndFactualCustomsRalation> flist,
			boolean isM) {
		// List<FactoryAndFactualCustomsRalation> flist = tableModel.getList();
		// if (flist == null || flist.size() == 0) {
		// initTable(new ArrayList());
		// return;
		// }
		// List templist = new ArrayList();
		// Map<String, List<FactoryAndFactualCustomsRalation>> tempMap = new
		// HashMap<String, List<FactoryAndFactualCustomsRalation>>();
		// for (FactoryAndFactualCustomsRalation data : flist) {
		// String key = "";
		// if (isM) {
		// key = data.getMateriel().getPtNo();
		// } else {
		// StatCusNameRelationHsn fc = data.getStatCusNameRelationHsn();
		//
		// key = fc.getComplex() == null ? "" : fc.getComplex().getCode();
		// }
		// if (tempMap.get(key) == null) {
		// List list = new ArrayList();
		// list.add(data);
		// tempMap.put(key, list);
		// } else {
		// tempMap.get(key).add(data);
		// }
		// }
		// Iterator itr = tempMap.keySet().iterator();
		// while (itr.hasNext()) {
		// String keys = itr.next().toString();
		// List<FactoryAndFactualCustomsRalation> dlist = tempMap.get(keys);
		// Map<String, List<FactoryAndFactualCustomsRalation>> tmap = new
		// HashMap<String, List<FactoryAndFactualCustomsRalation>>();
		// if (!isM) {// 实现第二层排序
		// for (int m = 0; m < dlist.size(); m++) {
		// FactoryAndFactualCustomsRalation data = dlist.get(m);
		// StatCusNameRelationHsn fc = data
		// .getStatCusNameRelationHsn();
		// String key = (fc.getComplex() == null ? "" : fc
		// .getComplex().getCode())
		// + (fc.getEmsNo() == null ? "" : fc.getEmsNo())
		// + (fc.getSeqNum() == null ? "" : fc.getSeqNum()
		// .toString());
		// if (tmap.get(key) == null) {
		// List<FactoryAndFactualCustomsRalation> list = new
		// ArrayList<FactoryAndFactualCustomsRalation>();
		// list.add(data);
		// tmap.put(key, list);
		// } else {
		// tmap.get(key).add(data);
		// }
		// }
		// Iterator itrs = tmap.keySet().iterator();
		// while (itrs.hasNext()) {
		// List<FactoryAndFactualCustomsRalation> klist = tmap
		// .get(itrs.next().toString());
		// for (int i = 0; i < klist.size(); i++) {
		// templist.add(klist.get(i));
		// }
		// }
		//
		// } else {
		// for (int i = 0; i < dlist.size(); i++) {
		// templist.add(dlist.get(i));
		// }
		// }
		// }
		initTable(flist);
		refreshTable(isM);
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	//hwy2012-11-14
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(85, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(
							FmFactoryAndFactualCustomsRalation.this,
							"此对应关系可能被进出仓单据调用，确定需要删除？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}

					if (tableModel.getCurrentRows().size() == 0) {
						JOptionPane.showMessageDialog(
								FmFactoryAndFactualCustomsRalation.this,
								"请选择您需要删除的列!", "提示!",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					List delelist = tableModel.getCurrentRows();
					casAction.deleteFactoryAndFactualCustomsRalation(
							new Request(CommonVars.getCurrUser()), delelist);
					getRbUniteDisplay().setSelected(false);
					getRbUniteDisplay1().setSelected(true);
					refresh();
				}

			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getRbUniteDisplay1());
			buttonGroup1.add(getRbUniteDisplay());
		}
		return buttonGroup1;
	}

	/**
	 * 弹出式菜单
	 * 
	 * @author ower
	 * 
	 */
	abstract class MyModifJPopupMenu extends JPopupMenu {
		JMenuItem miInputBcs = null;

		JMenuItem miInputBcus = null;

		JMenuItem miInputDzsc = null;

		JComboBox cbb = null;

		/**
		 * This method initializes
		 * 
		 * @param cbb
		 */
		public MyModifJPopupMenu(JComboBox cbb) {
			super();
			this.add(getMiInputBcs());
			this.add(getMiInputBcus());
			this.add(getMiInputDzsc());
			this.cbb = cbb;
		}

		abstract void doActions(int pprojectType);

		/**
		 * This method initializes miInputBcs
		 * 
		 * @return javax.swing.JMenuItem
		 */

		public JMenuItem getMiInputBcs() {
			if (miInputBcs == null) {
				miInputBcs = new JMenuItem("电子化手册导入");
				miInputBcs
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								doActions(ProjectType.BCS);
							}
						});
			}
			return miInputBcs;
		}

		/**
		 * This method initializes miInputBcus
		 * 
		 * @return javax.swing.JMenuItem
		 */
		public JMenuItem getMiInputBcus() {
			if (miInputBcus == null) {
				miInputBcus = new JMenuItem("电子帐册导入");
				miInputBcus
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								doActions(ProjectType.BCUS);
							}
						});
			}
			return miInputBcus;
		}

		/**
		 * This method initializes miInputDzsc
		 * 
		 * @return javax.swing.JMenuItem
		 */
		public JMenuItem getMiInputDzsc() {
			if (miInputDzsc == null) {
				miInputDzsc = new JMenuItem("电子手册导入");
				miInputDzsc
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(ActionEvent e) {
								doActions(ProjectType.DZSC);
							}
						});
			}
			return miInputDzsc;
		}

	}

	/**
	 * this method initializes itmBcs
	 * 
	 * @return javax.swing.JMenuItem
	 */

	public JMenuItem getItmBcs() {
		if (itmBcs == null) {
			itmBcs = new JMenuItem("电子化手册新增");
			itmBcs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String materielType = ((ItemProperty) getCbbMaterialType()
							.getSelectedItem()).getCode();
					List list = (List) getBcsTenInnerMerge(materielType);
					List slist = casAction
							.addFactoryAndFactualCustomsRalation(list);
					refresh();

				}
			});
		}
		return itmBcs;
	}

	/**
	 * 初始化表格
	 * 
	 * @param materielType
	 * @return
	 */
	public Object getBcsTenInnerMerge(final String materielType) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("名称", "materiel.factoryName", 100));
		list.add(new JTableListColumn("规格", "materiel.factorySpec", 100));
		list.add(new JTableListColumn("序号", "bcsTenInnerMerge.seqNum", 30));
		list.add(new JTableListColumn("商品编码", "bcsTenInnerMerge.complex.code",
				100));
		list.add(new JTableListColumn("报关名称", "bcsTenInnerMerge.name", 100));
		list.add(new JTableListColumn("报关规格", "bcsTenInnerMerge.spec", 100));
		list.add(new JTableListColumn("报关单位", "bcsTenInnerMerge.comUnit.name",
				80));

		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//
				BcsInnerMergeAction bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
						.getApplicationContext().getBean("bcsInnerMergeAction");
				return bcsInnerMergeAction.findBcsInnerMerge(new Request(
						CommonVars.getCurrUser(), true), materielType, index,
						length, property, value, isLike,false);
			}
		};

		dgCommonQuery.setTitle("选择【BCS】报关资料");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			List<BcsInnerMerge> rlist = dgCommonQuery.getReturnList();
			if (rlist == null) {
				return new ArrayList();
			}
			List relist = new ArrayList();
			for (BcsInnerMerge data : rlist) {
				if (data.getBcsTenInnerMerge() != null) {
					relist.add(data);
				}
			}
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * This method initializes btnMergerRelationInput
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMergerRelationInput() {
		if (btnMergerRelationInput == null) {
			btnMergerRelationInput = new JButton();
			btnMergerRelationInput.setText("从归并关系导入");
			btnMergerRelationInput.setMnemonic(KeyEvent.VK_UNDEFINED);
			btnMergerRelationInput.setPreferredSize(new Dimension(95, 30));
			btnMergerRelationInput
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (getCbbMaterialType().getSelectedItem() == null) {
								JOptionPane
										.showMessageDialog(
												FmFactoryAndFactualCustomsRalation.this,
												"请先选择物料类型!", "提示",
												JOptionPane.WARNING_MESSAGE);
								return;
							}
							MyModifJPopupMenu mf = new MyModifJPopupMenu(
									getCbbMaterialType()) {
								String mtype = "";

								public void doActions(final int projectType) {
									mtype = ((ItemProperty) this.cbb
											.getSelectedItem()).getCode();
									new SwingWorker() {
										@Override
										protected Object doInBackground()
												throws Exception {
											try {
												CommonProgress
														.showProgressDialog();
												CommonProgress
														.setMessage("系统正在加载默认系数，请稍后...");
												if (mtype
														.equals(MaterielType.MACHINE)) {
													casAction
															.makeAllFixFactoryAndFactualCustomsRalation(
																	new Request(
																			CommonVars
																					.getCurrUser()),
																	projectType);
												} else {
													casAction
															.makeAllFactoryAndFactualCustomsRalation(
																	new Request(
																			CommonVars
																					.getCurrUser()),
																	mtype,
																	projectType);

												}
											} catch (Exception e) {
												JOptionPane
														.showMessageDialog(
																FmFactoryAndFactualCustomsRalation.this,
																"加载失败：！"
																		+ e
																				.getMessage(),
																"提示", 2);
											} finally {
												CommonProgress
														.closeProgressDialog();
											}
											return null;
										}

										/**
										 * 执行 刷新
										 */

										@Override
										protected void done() {
											refresh();
										}
									}.execute();
									// new Thread() {
									// public void run() {
									// CommonProgress.showProgressDialog();
									// CommonProgress.setMessage("正在导入数据，请稍候...");
									// try {
									//
									// if (mtype.equals(MaterielType.MACHINE)) {
									// casAction
									// .makeAllFixFactoryAndFactualCustomsRalation(
									// new Request(
									// CommonVars
									// .getCurrUser()),
									// projectType);
									// } else {
									// casAction
									// .makeAllFactoryAndFactualCustomsRalation(
									// new Request(
									// CommonVars
									// .getCurrUser()),
									// mtype, projectType);
									// }
									// } catch (Exception e) {
									// e.printStackTrace();
									// } finally {
									// CommonProgress.closeProgressDialog();
									// refresh();
									// }
									// }
									// }.start();
								}
							};
							mf.show(getBtnMergerRelationInput(), 0, 25);
						}
					});
		}
		return btnMergerRelationInput;
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
			btnPrint.setPreferredSize(new Dimension(85, 30));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<FactoryAndFactualCustomsRalation> flist = tableModel
							.getList();
					if (flist.size() == 0) {
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							flist);
					InputStream reportStream = FmFactoryAndFactualCustomsRalation.class
							.getResourceAsStream("report/FCRalationReport.jasper");
					JasperPrint jasperPrint = null;
					Map<String, String> parameters = new HashMap<String, String>();

					String title = "成品归并表";
					if (MaterielType.FINISHED_PRODUCT.equals(currType)) {
						title = "成品归并表";
					} else if (MaterielType.MATERIEL.equals(currType)) {
						title = "料件归并表";
					} else if (MaterielType.MACHINE.equals(currType)) {
						title = "设备归并表";
					} else if (MaterielType.REMAIN_MATERIEL.equals(currType)) {
						title = "边角料归并表";
					} else if (MaterielType.BAD_PRODUCT.equals(currType)) {
						title = "残次品归并表";
					}
					parameters.put("title", title);

					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnPrint;
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
			btnAdd.setPreferredSize(new Dimension(85, 30));
			// btnAdd.setVisible(false);
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getCbbMaterialType().getSelectedItem() == null) {
						JOptionPane.showMessageDialog(
								FmFactoryAndFactualCustomsRalation.this,
								"请先选择物料类型!", "提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					final String materielType = ((ItemProperty) cbbMaterialType
							.getSelectedItem()).getCode();
					boolean isFilt = true;
					if (JOptionPane.showConfirmDialog(
							FmFactoryAndFactualCustomsRalation.this,
							"是否过滤已加入的料件\r\n如果选\"是\"是过滤，选\"否\"不过滤", "提示",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						isFilt = false;
					}
					List dataSource = CommonQueryPage.getInstance()
							.getMaterielAllOfCustomsRalation(materielType,
									isFilt);
					if (dataSource == null || dataSource.isEmpty()) {
						return;
					}
					// 报关常用物料资料
					Materiel m = (Materiel) dataSource.get(0);
					DgStatCusNameRelationHsn dg = new DgStatCusNameRelationHsn();
					dg.setTableModel(tableModel);
					dg.setDataState(DataState.ADD);
					dg.setMateriel(m);
					dg.setMaterielType(materielType);
					dg.setVisible(true);
					if (dg.isClose()) {
						getData();
					}

					// JPopupMenu mf = new JPopupMenu();
					// mf.add(getItmBcs());
					// mf.show(getBtnAdd(), 0, 25);
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnFileImport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFileImport() {
		if (btnFileImport == null) {
			btnFileImport = new JButton();
			btnFileImport.setPreferredSize(new Dimension(95, 30));
			btnFileImport.setMnemonic(KeyEvent.VK_UNDEFINED);
			btnFileImport.setSize(new Dimension(77, 30));
			btnFileImport.setText("从文件导入");
			btnFileImport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (getCbbMaterialType().getSelectedItem() == null) {
								JOptionPane
										.showMessageDialog(
												FmFactoryAndFactualCustomsRalation.this,
												"请先选择物料类型!", "提示",
												JOptionPane.WARNING_MESSAGE);
								return;
							}
							// 对应关系数据导入
							DgRelationImportFromFile fm = new DgRelationImportFromFile();
							fm.setTableModel(tableModel);
							String materielType = ((ItemProperty) cbbMaterialType
									.getSelectedItem()).getCode();
							fm.setMaterielType(materielType);
							fm.setVisible(true);
							if (fm.isOk()) {
								// cToMany(false);
								pnCommonQueryPage.setInitState();

							}
						}
					});
		}
		return btnFileImport;
	}

	/**
	 * SwingWorker线程类
	 * 
	 * @author ower
	 * 
	 */

	class MySwingWorker extends SwingWorker {
		private String mtype = "";
		private List dataSource = new ArrayList();

		/**
		 * this method is constructor
		 */

		public MySwingWorker() {
			super();
		}

		/**
		 * 数据加载
		 */
		@Override
		protected Object doInBackground() throws Exception {
			try {
				CommonProgress.showProgressDialog(); // @jve:decl-index=0:
				CommonProgress.setMessage("系统正在加载默认系数，请稍后...");
				if (cbbMaterialType.getSelectedItem() != null) {
					getPnCommonQueryPage().setInitState();
				} else {
					CommonProgress.closeProgressDialog();
					initTable(new ArrayList());
					// cToMany(new ArrayList(), false);
				}
				dataSource = FmFactoryAndFactualCustomsRalation.this.dataSource;
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(
						FmFactoryAndFactualCustomsRalation.this, "加载失败：！"
								+ e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return null;
		}

		@Override
		protected void done() {
			cToMany(dataSource == null ? (new ArrayList()) : dataSource, false);
		}
	};

	/**
	 * This method initializes btnQuerry
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuerry() {
		if (btnQuerry == null) {
			btnQuerry = new JButton();
			btnQuerry.setText("查询"); // Generated
			btnQuerry.setVisible(false);
			btnQuerry.setPreferredSize(new Dimension(85, 30));
			btnQuerry.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					erpBillCenterAuthority.checkRelationByBrower(new Request(
							CommonVars.getCurrUser()));
					if (getCbbMaterialType().getSelectedItem() == null) {
						JOptionPane.showMessageDialog(
								FmFactoryAndFactualCustomsRalation.this,
								"请先选择物料类型!", "提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					/**
					 * 
					 */
					new MySwingWorker().execute();
					// getPnCommonQueryPage().setInitState();
					// cToMany(dataSource==null?(new ArrayList()):dataSource,
					// false);
				}
			});
		}
		return btnQuerry;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setDividerSize(2);
			jSplitPane.setDividerLocation(68);
			jSplitPane.setContinuousLayout(true);
			jSplitPane.setTopComponent(getPnTop());
			jSplitPane.setBottomComponent(getPnUnder());
			jSplitPane.setEnabled(true);
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
			jPanel.setPreferredSize(new Dimension(344, 40));
		}
		return jPanel;
	}

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
		Request request = new Request(CommonVars.getCurrUser());
		if (cbbMaterialType.getSelectedItem() != null) {
			currType = ((ItemProperty) cbbMaterialType.getSelectedItem())
					.getCode();
			dataSource = casAction.findFactoryAndFactualCustomsRalation(request, index, length, property, value, isLike, currType);
		} else {
			dataSource = new ArrayList();
		}
		return dataSource;
	}

	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	private PnCommonQueryPage getPnCommonQueryPage() {
		if (pnCommonQueryPage == null) {
			pnCommonQueryPage = new PnCommonQueryPage() {
				/**
				 * 初始化表格
				 */

				@Override
				public JTableListModel initTable(List dataSource) {
					JTableListModel tableModel = FmFactoryAndFactualCustomsRalation.this
							.initTable(dataSource);
					FmFactoryAndFactualCustomsRalation.this.cToMany(dataSource,
							false);
					return tableModel;
				}

				/**
				 * 获得数据源
				 */

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					List list = FmFactoryAndFactualCustomsRalation.this
							.getDataSource(index, length, property, value,
									isLike);
					return list;
				}

			};
			pnCommonQueryPage.setPreferredSize(new Dimension(60, 23));
		}
		return pnCommonQueryPage;
	}

	/**
	 * 获得所有数据
	 */

	public void getData() {
		
		pnCommonQueryPage = getPnCommonQueryPage();
		if (pnCommonQueryPage.getCbbQueryField().getItemCount() > 0) {
			pnCommonQueryPage.getCbbQueryField().removeAllItems();
			pnCommonQueryPage.setFirstHasDataInit(true);
		}
		getPnCommonQueryPage().setInitState();
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			// jToolBar.setPreferredSize(new Dimension(384, 10));
			jToolBar.setPreferredSize(new Dimension(4, 34));
			jToolBar.add(getBtnMergerRelationInput());
			jToolBar.add(getBtnFileImport());
			jToolBar.add(getRbUniteDisplay());
			jToolBar.add(getRbUniteDisplay1());
		}
		return jToolBar;
	}

	/**
	 * This method initializes pnTop
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			pnTop = new JPanel();
			pnTop.setLayout(new BorderLayout());
			// pnTop.setPreferredSize(new Dimension(352, 58));
			pnTop.setPreferredSize(new Dimension(384, 20));
			pnTop.add(getJJToolBarBar(), BorderLayout.NORTH);
			pnTop.add(getJToolBar(), BorderLayout.CENTER);
		}
		return pnTop;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.setFloatable(false);
			jToolBar1.setPreferredSize(new Dimension(2, 40));
			jToolBar1.add(getPnCommonQueryPage());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes pnUnder
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnUnder() {
		if (pnUnder == null) {
			pnUnder = new JPanel();
			pnUnder.setLayout(new BorderLayout());
			pnUnder.add(getJScrollPane(), BorderLayout.CENTER);
			pnUnder.add(getJToolBar1(), BorderLayout.NORTH);
		}
		return pnUnder;
	}

	/**
	 * This method initializes pnTopOne
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTopOne() {
		if (pnTopOne == null) {
			pnTopOne = new JPanel();
			pnTopOne.setLayout(null);
			pnTopOne.setPreferredSize(new Dimension(200, 30));
			pnTopOne.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			pnTopOne.add(getCbbMaterialType(), null);
			pnTopOne.add(jLabel, null);
		}
		return pnTopOne;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setPreferredSize(new Dimension(85, 30));
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmFactoryAndFactualCustomsRalation.this,
								"请选择你要修改的资料", "确认", 1);
						return;
					}

					if (getCbbMaterialType().getSelectedItem() == null) {
						JOptionPane.showMessageDialog(
								FmFactoryAndFactualCustomsRalation.this,
								"请先选择物料类型!", "提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					final String materielType = ((ItemProperty) cbbMaterialType
							.getSelectedItem()).getCode();
					if (isHsInfo) {
						DgStatCusNameRelationHsn dg = new DgStatCusNameRelationHsn();
						dg.setTableModel(tableModel);
						dg.setDataState(DataState.EDIT);
						dg.setF((FactoryAndFactualCustomsRalation) tableModel
								.getCurrentRow());
						dg.setMaterielType(materielType);
						dg.setVisible(true);
					} else {
						DgEditUniteConvert dg = new DgEditUniteConvert();
						dg
								.setFdata((FactoryAndFactualCustomsRalation) tableModel
										.getCurrentRow());
						dg
								.setOldEmsNo(((FactoryAndFactualCustomsRalation) tableModel
										.getCurrentRow())
										.getStatCusNameRelationHsn().getEmsNo());
						dg.setTableModel(tableModel);
						dg.setMaterielType(((ItemProperty) cbbMaterialType
								.getSelectedItem()).getCode());
						dg.setVisible(true);
					}

					// wss2010-06-23加
					// if (dg.isClose()) {
					// getData();//就是刷新一下了
					// }
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes miEditHsInfo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiEditHsInfo() {
		if (miEditHsInfo == null) {
			miEditHsInfo = new JMenuItem();
			miEditHsInfo.setSize(new Dimension(133, 24));
			miEditHsInfo.setText("        修改报关资料");
			miEditHsInfo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgStatCusNameRelationHsn dg = new DgStatCusNameRelationHsn();
					dg.setTableModel(tableModel);
					dg.setDataState(DataState.EDIT);
					dg.setF((FactoryAndFactualCustomsRalation) tableModel
							.getCurrentRow());
					dg.setMaterielType(((ItemProperty) cbbMaterialType
							.getSelectedItem()).getCode());
					dg.setVisible(true);
				}
			});
		}
		return miEditHsInfo;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
