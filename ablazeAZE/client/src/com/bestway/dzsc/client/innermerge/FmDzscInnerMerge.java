/*
 * Created on 2004-7-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.innermerge;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.CommonTableContextPopupEvent;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.client.message.DzscCommon;
import com.bestway.dzsc.innermerge.action.DzscInnerMergeAction;
import com.bestway.dzsc.innermerge.entity.DzscFourInnerMerge;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.innermerge.entity.TempDzscAutoInnerMergeParam;
import com.bestway.dzsc.innermerge.entity.TempInnerMergeApplySelectParam;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.dzsc.materialapply.entity.DzscMaterielHead;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
/**
 * @author bsway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmDzscInnerMerge extends JInternalFrameBase {

	private AttributiveCellTableModel tableModel = null;

	private List dataSource = new ArrayList();

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel = null;

	private JComboBox cbbMaterielType = null;

	private JButton btnAddData = null;

	private JPopupMenu pmAfterTenInnerMerge = null;

	private JMenuItem miNewTenInnerMerge = null;

	private JMenuItem miUndoTenInnerMerge = null;

	private JMenuItem miUndoChange = null;

	// private JMenuItem miAddFourInnerMerge = null;

	private JMenuItem miUndoFourInnerMerge = null;

	private JPopupMenu pmFourInnerMerge = null;

	private JPopupMenu pmBeforeTenInnerMerge = null;

	private JMenuItem miResetMemoNo = null;

	private JMenuItem miFourResetMemoNo = null;

	// private JMenuItem miAddTenInnerMerge = null;

	private JMenuItem miNewFourInnerMerge = null;

	private JMenuItem miDeleteInnerMerge = null;

	private JButton btnPrint = null;

	private JSlider jSlider = null;

	private HashMap hmTableInfo = null; // @jve:decl-index=0:

	private JPopupMenu pmTableContextPopupMenu = null;

	private boolean isCopyMenuEnabled = false;

	private DzscInnerMergeAction dzscInnerMergeAction = null;

	// private DzscInnerMergeHead head = null;
	private JMenuItem miEditMateriel = null;

	private JMenuItem miCopy = null;

	private JMenuItem miEditTen = null;

	private JMenuItem miEditFour = null;

	private JComboBox cbbReport = null;

	private JButton btnImportData = null;

	private JCheckBox cbCombineRows = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JScrollPane jScrollPane = null;

	private JTable tbInnerMerge = null;

	private JToolBar jJToolBarBar = null;

	private PnCommonQueryPage pnCommonQueryPage = null;

	private JButton btnApply = null;

	private JButton btnEffective = null;

	private JButton btnChange = null;

	private JButton btnClose = null;

	private JButton btnInnerMerge = null;

	private JMenuItem miAutoInnerMerge = null;

	private JPopupMenu pmInnerMerge = null; // @jve:decl-index=0:visual-constraint="870,224"

	private JMenuItem miCustomInnerMerge = null;

	private JPopupMenu pmImportData = null; // @jve:decl-index=0:visual-constraint="939,142"

	private JMenuItem miFromMaterial = null;

	private JMenuItem miFromFile = null;

	private JTabbedPane jTabbedPane = null;

	private MaterialApplyAction materialApplyAction = null;

	private JMenuItem miForbidMateriel = null;

	private JButton btnOtherFunction = null;

	private JPopupMenu pmOtherFunction = null; // @jve:decl-index=0:visual-constraint="863,160"

	private JMenuItem miShowAll = null;

	private JMenuItem miShowNotMerge = null;

	private JMenuItem btnDeleteNotMerge = null;

	private JPanel pnToolBar = null;
	
	private JMenu jMenu = null;

	private JMenuItem jMenuItem = null;

	private JMenuItem miAddFourInnerMerge = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmDzscInnerMerge() {
		super();
		materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
		dzscInnerMergeAction = (DzscInnerMergeAction) CommonVars
				.getApplicationContext().getBean("dzscInnerMergeAction");
		initialize();
		tbInnerMerge = new MultiSpanCellTable();
		tbInnerMerge.addMouseListener(new InnerMergeMouseAdapter(tbInnerMerge));
		tbInnerMerge.getSelectionModel().addListSelectionListener(
				new InnerMergeListSelectionListener());
		tbInnerMerge.getColumnModel().addColumnModelListener(
				new TableColumnModelListener() {
					/** Tells listeners that a column was added to the model. */
					public void columnAdded(TableColumnModelEvent e) {
					}

					/** Tells listeners that a column was removed from the model. */
					public void columnRemoved(TableColumnModelEvent e) {
					}

					/** Tells listeners that a column was repositioned. */
					public void columnMoved(TableColumnModelEvent e) {
					}

					/**
					 * Tells listeners that a column was moved due to a margin
					 * change.
					 */
					public void columnMarginChanged(ChangeEvent e) {
					}

					public void columnSelectionChanged(ListSelectionEvent e) {
						int[] columns = ((MultiSpanCellTable) tbInnerMerge)
								.getSelectedColumns();
						int[] rows = ((MultiSpanCellTable) tbInnerMerge)
								.getSelectedRows();
						if (columns.length < 1 || rows.length < 1) {
							return;
						}
						if (columns[0] >= 1 && columns[0] <= 7) {
							tbInnerMerge.setColumnSelectionInterval(1, 7);
							return;
						} else if (columns[0] >= 8 && columns[0] <= 13) {
							tbInnerMerge.setColumnSelectionInterval(8, 13);
							return;
						} else if (columns[0] >= 14 && columns[0] <= 18) {
							tbInnerMerge.setColumnSelectionInterval(14, 18);
							return;
						}
					}
				});
		jScrollPane.setViewportView(tbInnerMerge);
	}

	class InnerMergeMouseAdapter extends java.awt.event.MouseAdapter {
		private JTable table = null;

		public InnerMergeMouseAdapter(JTable table) {
			super();
			this.table = table;
		}

		public void mousePressed(java.awt.event.MouseEvent e) {
			if (e.getModifiers() != InputEvent.BUTTON3_MASK) {
				return;
			}
			int[] columns = table.getSelectedColumns();
			int[] rows = table.getSelectedRows();
			int selectStartPointX = 0;
			int selectEndPointX = 0;
			//
			// 是否有上下文菜单
			//
			boolean hasTableContextPopupMenu = true;

			if (columns.length > 0 && rows.length > 0) {

				for (int i = 0; i < columns[0]; i++) {
					selectStartPointX += table.getColumnModel().getColumn(i)
							.getWidth()
							+ table.getColumnModel().getColumnMargin();
				}
				for (int i = 0; i <= columns[columns.length - 1]; i++) {
					selectEndPointX += table.getColumnModel().getColumn(i)
							.getWidth()
							+ table.getColumnModel().getColumnMargin();
				}
				//
				// 列不变，行可以不是连续的
				//
				for (int j = rows[0]; j <= rows[rows.length - 1]; j++) {
					boolean isSelectedRow = false;
					for (int i = 0; i < rows.length; i++) {
						if (j == rows[i]) {
							isSelectedRow = true;
							break;
						}
					}
					if (isSelectedRow == false) {
						continue;
					}
					int selectStartPointY = 0;
					int selectEndPointY = 0;
					for (int i = 0; i < j; i++) {
						selectStartPointY += table.getRowHeight(i)
								+ table.getRowMargin();
					}
					for (int i = 0; i <= j; i++) {
						selectEndPointY += table.getRowHeight(i)
								+ table.getRowMargin();
					}
					/**
					 * 在选择区域内点击鼠标右键
					 */
					if (e.getPoint().x >= selectStartPointX
							&& e.getPoint().x <= selectEndPointX
							&& e.getPoint().y >= selectStartPointY
							&& e.getPoint().y <= selectEndPointY) {
						isCopyMenuEnabled = true;
						if (columns[0] >= 0 && columns[0] <= 7) {
							getMiNewTenInnerMerge().setEnabled(
									checkCanEdit(tableModel)); // 新增十码
							getPmBeforeTenInnerMerge().show(table,
									e.getPoint().x, e.getPoint().y);
						} else if (columns[0] >= 8 && columns[0] <= 13) {
							getMiUndoTenInnerMerge().setEnabled(
									checkCanEdit(tableModel));// 撤消10码归并
							if (isNewFourInnerMerge(table) == 0) {
								getMiNewFourInnerMerge().setEnabled(
										checkCanEdit(tableModel)); // 新增四码
								getMiAddFourInnerMerge().setEnabled(checkCanEdit(tableModel));
								getMiEditTen().setEnabled(
										checkCanEdit(tableModel)
												&& checkCanEditTen(tableModel));// 修改10码
							} else if (isNewFourInnerMerge(table) == 1) {
								getMiNewFourInnerMerge().setEnabled(false);
								getMiAddFourInnerMerge().setEnabled(false);
								getMiEditTen().setEnabled(false);// 修改10码
							} else if (isNewFourInnerMerge(table) == 2) {
								getMiNewFourInnerMerge().setEnabled(false);
								getMiAddFourInnerMerge().setEnabled(false);
								getMiEditTen().setEnabled(
										checkCanEdit(tableModel)
												&& checkCanEditTen(tableModel));// 修改10码
							}
							getPmAfterTenInnerMerge().show(table,
									e.getPoint().x, e.getPoint().y);
						} else if (columns[0] >= 14 && columns[0] <= 18) {
							if (checkHaveFourInnerMerges(table)) {
								getMiEditFour().setEnabled(
										checkCanEdit(tableModel));
								getMiUndoFourInnerMerge().setEnabled(
										checkCanEdit(tableModel));
							} else {
								getMiEditFour().setEnabled(false);
								getMiUndoFourInnerMerge().setEnabled(false);
							}
							getPmFourInnerMerge().show(table, e.getPoint().x,
									e.getPoint().y);
						}
						getMiEditMateriel().setEnabled(
								isCanEditMaterel(tableModel)
										&& checkCanEdit(tableModel));
						getMiDeleteInnerMerge().setEnabled(
								isCanDeleteMaterel(tableModel)
										&& checkCanEdit(tableModel));
						getMiForbidMateriel().setEnabled(
								isCanDeleteMaterel(tableModel)
										&& checkCanEdit(tableModel));
						hasTableContextPopupMenu = false;
						break;
					}
				}
			}
			if (hasTableContextPopupMenu == true) {
				//
				// 加入公共弹出菜单
				//                  
				isCopyMenuEnabled = false;
				getTableContextPopupMenu().show(table, e.getPoint().x,
						e.getPoint().y);
				return;
			}

		}
	}

	class InnerMergeListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting()) {
				return;
			}
			if (tableModel == null)
				return;
			if (tableModel.getCurrentRow() == null)
				return;
			setBtnState();

		}
	}

	private JPopupMenu getTableContextPopupMenu() {
		if (pmTableContextPopupMenu == null) {
			pmTableContextPopupMenu = new JPopupMenu();
		}
		if (tableModel != null) {
			tableModel.getMiCopy().setEnabled(isCopyMenuEnabled);
			pmTableContextPopupMenu.removeAll();
			pmTableContextPopupMenu.add(tableModel.getMiCopy());
			pmTableContextPopupMenu.addSeparator();
			pmTableContextPopupMenu.add(tableModel.getMiSearch());
			pmTableContextPopupMenu.add(tableModel.getMiSaveTableListToExcel());
		}
		return pmTableContextPopupMenu;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("企业商品归并设置");
		this.setContentPane(getJContentPane());
		this.getPmAfterTenInnerMerge();

		this.setSize(824, 544);
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						pnCommonQueryPage.setInitState();
						setBtnState();
					}
				});
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
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getPnToolBar(), BorderLayout.NORTH);

		}
		return jContentPane;
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
			jToolBar.add(getBtnAddData());
			jToolBar.add(getBtnImportData());
			jToolBar.add(getBtnApply());
			jToolBar.add(getBtnEffective());
			jToolBar.add(getBtnChange());
			jToolBar.add(getBtnInnerMerge());
			jToolBar.add(getBtnOtherFunction());
			jToolBar.add(getBtnClose());
			jToolBar.add(getJPanel());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getCbbMaterielType(), null);
			jPanel.add(getCbbReport(), null);
			jPanel.add(getBtnPrint(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterielType() {
		if (cbbMaterielType == null) {
			cbbMaterielType = new JComboBox();
			cbbMaterielType.setBounds(new java.awt.Rectangle(8, 3, 83, 24));
			cbbMaterielType.addItem(new ItemProperty(
					MaterielType.FINISHED_PRODUCT, "成品"));
			// cbbMaterielType.addItem(new ItemProperty(
			// MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
			cbbMaterielType.addItem(new ItemProperty(MaterielType.MATERIEL,
					"料件"));
			cbbMaterielType
					.addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
			// cbbMaterielType.addItem(new ItemProperty(
			// MaterielType.REMAIN_MATERIEL, "边角料"));
			// cbbMaterielType.addItem(new
			// ItemProperty(MaterielType.BAD_PRODUCT,
			// "残次品"));
			cbbMaterielType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						// if (jTabbedPane.getSelectedIndex() == 0) {
						pnCommonQueryPage.setInitState();
						// } else if (jTabbedPane.getSelectedIndex() == 1) {
						// pnCommonQueryPage1.setInitState();
						// }
						if (cbbMaterielType.getSelectedItem() != null) {
							ItemProperty itemProperty = (ItemProperty) cbbMaterielType
									.getSelectedItem();
							if (itemProperty.getCode().equals(
									MaterielType.MACHINE)) {// 设备
								setMachineState();
							} else {
								cbbReport.setSelectedIndex(cbbMaterielType
										.getSelectedIndex());
								btnPrint.setEnabled(true);//打印
								cbbReport.setEnabled(true);
							}
						}

					}
				}
			});

		}
		return cbbMaterielType;
	}

	private JTableListModel initTable(List dataSource) {
		tableModel = new AttributiveCellTableModel(
				(MultiSpanCellTable) tbInnerMerge, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("状态", "stateMarkDesc", 60));
						list.add(addColumn("处理标记", "beforeModifyMarkDesc", 60));
						list.add(addColumn("货号", "materiel.ptNo", 100));
						list
								.add(addColumn("商品编码", "materiel.complex.code",
										100));
						list
								.add(addColumn("商品名称", "materiel.factoryName",
										100));
						list.add(addColumn("规格", "materiel.factorySpec", 100));
						list.add(addColumn("单位", "materiel.ptUnit.name", 50));
						// list.add(addColumn("单价", "materiel.ptPrice", 100));
						list.add(addColumn("处理标记",
								"dzscTenInnerMerge.tenModifyMarkDesc", 60));
						list.add(addColumn("序号", "dzscTenInnerMerge.tenSeqNum",
								50, Integer.class));
						list.add(addColumn("10位商品编码",
								"dzscTenInnerMerge.tenComplex.code", 100));
						list.add(addColumn("10位商品名称",
								"dzscTenInnerMerge.tenPtName", 100));
						list.add(addColumn("10位商品规格",
								"dzscTenInnerMerge.tenPtSpec", 100));
						list.add(addColumn("10位常用单位",
								"dzscTenInnerMerge.tenUnit.name", 100));

						list
								.add(addColumn(
										"序号",
										"dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum",
										50, Integer.class));
						list
								.add(addColumn(
										"4位商品编码",
										"dzscTenInnerMerge.dzscFourInnerMerge.fourComplex",
										100));
						list
								.add(addColumn(
										"4位商品名称",
										"dzscTenInnerMerge.dzscFourInnerMerge.fourPtName",
										100));
						list
								.add(addColumn(
										"4位商品规格",
										"dzscTenInnerMerge.dzscFourInnerMerge.fourPtSpec",
										100));
						list
								.add(addColumn(
										"4位商品单位",
										"dzscTenInnerMerge.dzscFourInnerMerge.fourUnit.name",
										100));
						// list.add(addColumn("最低单价", "lowPrice", 70));
						// list.add(addColumn("征免性质", "levyMode.name", 70));
						return list;
					}
				});

		TableColumnModel cm = tbInnerMerge.getColumnModel();

		ColumnGroup gBefore = new ColumnGroup("归并前");
		gBefore.add(cm.getColumn(1));
		gBefore.add(cm.getColumn(2));
		gBefore.add(cm.getColumn(3));
		gBefore.add(cm.getColumn(4));
		gBefore.add(cm.getColumn(5));
		gBefore.add(cm.getColumn(6));
		gBefore.add(cm.getColumn(7));
		ColumnGroup gAfter = new ColumnGroup("归并后");
		// gAfter.add(cm.getColumn(7));
		gAfter.add(cm.getColumn(8));
		gAfter.add(cm.getColumn(9));
		gAfter.add(cm.getColumn(10));
		gAfter.add(cm.getColumn(11));
		gAfter.add(cm.getColumn(12));
		gAfter.add(cm.getColumn(13));
		GroupableTableHeader header = (GroupableTableHeader) tbInnerMerge
				.getTableHeader();
		header.addColumnGroup(gBefore);
		header.addColumnGroup(gAfter);
		// TableColumn column = this.jTable.getColumnModel().getColumn(1);
		// column.setCellRenderer(new DefaultTableCellRenderer() {
		// public Component getTableCellRendererComponent(JTable table,
		// Object value, boolean isSelected, boolean hasFocus,
		// int row, int column) {
		// super.getTableCellRendererComponent(table, value,
		// isSelected, hasFocus, row, column);
		// String state = "";
		// if (value != null) {
		// state = value.toString();
		// }
		// if (state.equals(DzscState.Original)) {
		// this.setText("初始状态");
		// } else if (state.equals(DzscState.Application)) {
		// this.setText("申报状态");
		// }
		// if (state.equals(DzscState.Execute)) {
		// this.setText("生效状态");
		// }
		// if (state.equals(DzscState.Change)) {
		// this.setText("变更状态");
		// }
		// this.setText("AAAAAAAA");
		// return this;
		// }
		// });
		this.setTableCellToopTip();
		refreshTable();
		this.deleteCommonTableContextPopupEventMouseListener(tbInnerMerge);
		return tableModel;
	}

	private void deleteCommonTableContextPopupEventMouseListener(JTable table) {
		MouseListener[] mouseListeners = table.getMouseListeners();
		for (int i = 0; i < mouseListeners.length; i++) {
			if (mouseListeners[i] instanceof CommonTableContextPopupEvent) {
				table.removeMouseListener(mouseListeners[i]);
			}
		}
	}

	/**
	 * This method initializes btnImportData
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddData() {
		if (btnAddData == null) {
			btnAddData = new JButton();
			btnAddData.setText("新增");// 导入
			btnAddData.setToolTipText("新增物料");
			btnAddData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (cbbMaterielType.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(FmDzscInnerMerge.this,
								"请选择物料类别", "提示", JOptionPane.OK_OPTION);
						return;
					}
					ItemProperty item = (ItemProperty) cbbMaterielType
							.getSelectedItem();
					String materielType = item.getCode().trim();
					List list = CommonQueryPage.getInstance()
							.getMaterielNotInDzscInnerMerge(materielType);
					if (list == null || list.size() <= 0) {
						return;
					}
					List dzscInnerMergeList = dzscInnerMergeAction
							.addInnerMergeData(new Request(CommonVars
									.getCurrUser()), list, false);
					dataSource.addAll(dzscInnerMergeList);
					pnCommonQueryPage.setInitState();
				}
			});
		}
		return btnAddData;
	}

	class ImportMaterielDataRunnable extends Thread {
		private List materielType = null;

		public ImportMaterielDataRunnable(List materielType) {
			this.materielType = materielType;
		}

		public void run() {
			try {
				btnImportData.setEnabled(false);
				btnAddData.setEnabled(false);
				CommonProgress.showProgressDialog();
				// RunningTask task = CommonProgress.createRunningTask();
				CommonProgress.setMessage("系统正在从企业物料中导入资料，请稍后");
				try {
					// dzscInnerMergeAction
					// .importInnerMergeDataFromMateriel(new Request(
					// CommonVars.getCurrUser()), materielType);

					if (jTabbedPane.getSelectedIndex() == 0) {
						dzscInnerMergeAction.importInnerMergeDataFromMateriel(
								new Request(CommonVars.getCurrUser()),
								materielType, false);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						dzscInnerMergeAction.importInnerMergeDataFromMateriel(
								new Request(CommonVars.getCurrUser()),
								materielType, true);
					}
				} catch (Exception ee) {
					JOptionPane.showMessageDialog(null, ee.toString());
					CommonProgress.closeProgressDialog();
					return;
				}
				pnCommonQueryPage.refreshData();
			} finally {
				CommonProgress.closeProgressDialog();
				// JOptionPane.showMessageDialog(DgInnerMerge.this, "资料导入成功！");
				btnImportData.setEnabled(true);
				btnAddData.setEnabled(true);
			}
		}
	};

	/**
	 * This method initializes pmAfterTenInnerMerge
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmAfterTenInnerMerge() {
		if (pmAfterTenInnerMerge == null) {
			pmAfterTenInnerMerge = new JPopupMenu();
		}
		if (tableModel != null) {
			pmAfterTenInnerMerge.removeAll();
			pmAfterTenInnerMerge.add(getMiNewFourInnerMerge());
			// pmAfterTenInnerMerge.add(getMiAddFourInnerMerge());
			pmAfterTenInnerMerge.add(getMiAddFourInnerMerge());
			pmAfterTenInnerMerge.addSeparator();
			pmAfterTenInnerMerge.add(getMiUndoTenInnerMerge());
			// pmAfterTenInnerMerge.add(getMiUndoFourInnerMerge());

			pmAfterTenInnerMerge.add(getMiEditTen());
			// pmAfterTenInnerMerge.add(getMiResetMemoNo());
			pmAfterTenInnerMerge.addSeparator();
			pmAfterTenInnerMerge.add(tableModel.getMiCopy());
			pmAfterTenInnerMerge.add(tableModel.getMiSearch());
			pmAfterTenInnerMerge.add(tableModel.getMiSaveTableListToExcel());
			tableModel.getMiCopy().setEnabled(isCopyMenuEnabled);
		}
		return pmAfterTenInnerMerge;
	}

	/**
	 * This method initializes miNewTenInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNewTenInnerMerge() {
		if (miNewTenInnerMerge == null) {
			miNewTenInnerMerge = new JMenuItem();
			miNewTenInnerMerge.setText("10码归并");// 新增
			miNewTenInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							tenInnerMerge(1, tbInnerMerge);
						}
					});
		}
		return miNewTenInnerMerge;
	}

	/**
	 * 新增十码归并
	 */
	private void tenInnerMerge(int type, JTable jTable) {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		/*
		 * int result = dzscAction.checkDataForTenMerge(new Request(
		 * CommonVars.getCurrUser()), list); DgNewTenInnerMerge dgTenInnerMerge =
		 * null; switch (result) { case 1: dgTenInnerMerge = new
		 * DgNewTenInnerMerge(); dgTenInnerMerge.setJTable((MultiSpanCellTable)
		 * jTable); dgTenInnerMerge.setCurrentRows(list);
		 * dgTenInnerMerge.setNew(false); dgTenInnerMerge.setVisible(true);
		 * break; case 0: dgTenInnerMerge = new DgNewTenInnerMerge();
		 * dgTenInnerMerge.setJTable((MultiSpanCellTable) jTable);
		 * dgTenInnerMerge.setCurrentRows(list); dgTenInnerMerge.setNew(true);
		 * dgTenInnerMerge.setVisible(true); break; case -1:
		 * JOptionPane.showMessageDialog(DgInnerMerge.this, "编码不同的数据！", "警告",
		 * 0); break; case -2: JOptionPane.showMessageDialog(DgInnerMerge.this,
		 * "申报计量单位不同！", "警告", 0); break; case -3: JOptionPane
		 * 
		 * .showMessageDialog(DgInnerMerge.this, "商品名称不同！", "警告", 0); break;
		 * case -4: JOptionPane.showMessageDialog(DgInnerMerge.this,
		 * "选择的数据已全部归并！", "警告", 0); break; case -5:
		 * JOptionPane.showMessageDialog(DgInnerMerge.this, "选择的数据的备案序号！", "警告",
		 * 0); break; }
		 */
		if (type == 1) { // 新增10
			DgDzscTenInnerMerge dgTenInnerMerge = null;
			dgTenInnerMerge = new DgDzscTenInnerMerge();
			dgTenInnerMerge.setJTable((MultiSpanCellTable) jTable);
			dgTenInnerMerge.setCurrentRows(list);
			dgTenInnerMerge.setMaterialType(((ItemProperty) cbbMaterielType
					.getSelectedItem()).getCode());
			dgTenInnerMerge.setEditTenMerge(false);
			dgTenInnerMerge.setChange(jTabbedPane.getSelectedIndex() == 1);
			dgTenInnerMerge.setCombineRows(this.getCbCombineRows().isSelected());
			dgTenInnerMerge.setVisible(true);
		} else if (type == 2) {// 补充10
			DgDzscTenInnerMerge dgTenInnerMerge = null;
			dgTenInnerMerge = new DgDzscTenInnerMerge();
			dgTenInnerMerge.setJTable((MultiSpanCellTable) jTable);
			dgTenInnerMerge.setCurrentRows(list);
			dgTenInnerMerge.setMaterialType(((ItemProperty) cbbMaterielType
					.getSelectedItem()).getCode());
			dgTenInnerMerge.setEditTenMerge(false);
			dgTenInnerMerge.setChange(jTabbedPane.getSelectedIndex() == 1);
			dgTenInnerMerge.setCombineRows(this.getCbCombineRows().isSelected());
			dgTenInnerMerge.setVisible(true);
		} else if (type == 3) { // 修改10
			DgDzscTenInnerMerge dgTenInnerMerge = null;
			dgTenInnerMerge = new DgDzscTenInnerMerge();
			dgTenInnerMerge.setJTable((MultiSpanCellTable) jTable);
			dgTenInnerMerge.setCurrentRows(list);
			dgTenInnerMerge.setMaterialType(((ItemProperty) cbbMaterielType
					.getSelectedItem()).getCode());
			dgTenInnerMerge.setEditTenMerge(true);
			dgTenInnerMerge.setChange(jTabbedPane.getSelectedIndex() == 1);
			dgTenInnerMerge.setCombineRows(this.getCbCombineRows().isSelected());
			dgTenInnerMerge.setVisible(true);
		}
	}

	/**
	 * 
	 */
	private void refreshTable() {
		if (getCbCombineRows().isSelected()) {
			((MultiSpanCellTable) tbInnerMerge).combineRows(
					new int[] { 14, 9 }, new int[] { 14, 15, 16, 17, 18 });
			((MultiSpanCellTable) tbInnerMerge).combineRows(9, new int[] { 8,
					9, 10, 11, 12, 13 });
		} else {
			((MultiSpanCellTable) tbInnerMerge).splitRows(new int[] { 9, 14 });
		}
	}

	/**
	 * This method initializes miUndoTenInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiUndoTenInnerMerge() {
		if (miUndoTenInnerMerge == null) {
			miUndoTenInnerMerge = new JMenuItem();
			miUndoTenInnerMerge.setText("撤消10码归并");
			miUndoTenInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							unDoTenInnerMerge(tbInnerMerge);
						}
					});
		}
		return miUndoTenInnerMerge;
	}

	//
	// /**
	// * This method initializes miAddFourInnerMerge
	// *
	// * @return javax.swing.JMenuItem
	// */
	// private JMenuItem getMiAddFourInnerMerge() {
	// if (miAddFourInnerMerge == null) {
	// miAddFourInnerMerge = new JMenuItem();
	//
	// miAddFourInnerMerge.setText("补充4码归并");
	// miAddFourInnerMerge
	// .addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// fourInnerMerge(2, tbInnerMerge);
	// }
	// });
	// }
	// return miAddFourInnerMerge;
	// }

	/**
	 * 
	 */
	private void fourInnerMerge(int type, JTable jTable) {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		if (type == 1) { // 新增四码
			List ls = dzscInnerMergeAction.fourInnerMerge(new Request(
					CommonVars.getCurrUser()), list);
			refreshTable(ls);
		} else if (type == 2) { // 补充
			// List ls = dzscInnerMergeAction.addfourInner(new
			// Request(CommonVars
			// .getCurrUser()), list);
			// refreshTable(ls);
		} else if (type == 3) { // 修改
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(0);
			if (data.getDzscTenInnerMerge() == null
					|| data.getDzscTenInnerMerge().getDzscFourInnerMerge() == null) {
				return;
			}
			DgDzscFourInnerMerge dgFourInnerMerge = null;
			dgFourInnerMerge = new DgDzscFourInnerMerge();
			dgFourInnerMerge.setJTable((MultiSpanCellTable) jTable);
			dgFourInnerMerge.setCurrentRows(list);
			// dgFourInnerMerge.setHead(head);
			dgFourInnerMerge.setCombineRows(this.getCbCombineRows().isSelected());
			dgFourInnerMerge.setVisible(true);
		}

		/*
		 * int result = commonBaseCodeAction.checkDataForFourInnerMerge( new
		 * Request(CommonVars.getCurrUser()), list); List ls = null;
		 * DgFourInnerMerge dgFourInnerMerge = null; switch (result) { case 1:
		 * dgFourInnerMerge = new DgFourInnerMerge();
		 * dgFourInnerMerge.setJTable((MultiSpanCellTable) jTable);
		 * dgFourInnerMerge.setSelectedRows(list);
		 * dgFourInnerMerge.setNew(false); dgFourInnerMerge.setVisible(true);
		 * break; case 0: dgFourInnerMerge = new DgFourInnerMerge();
		 * dgFourInnerMerge.setJTable((MultiSpanCellTable) jTable);
		 * dgFourInnerMerge.setSelectedRows(list);
		 * dgFourInnerMerge.setNew(true); dgFourInnerMerge.setVisible(true);
		 * break; case -1: JOptionPane.showMessageDialog(DgInnerMerge.this,
		 * "有数据没有做过10位归并！", "警告", 0); break; case -2:
		 * JOptionPane.showMessageDialog(DgInnerMerge.this,
		 * "选择的数据的10位商品编码的前4位不同！", "警告", 0); break; case -3:
		 * JOptionPane.showMessageDialog(DgInnerMerge.this, "已经归并过的数据有不同编码序号！",
		 * "警告", 0); break; case -4:
		 * JOptionPane.showMessageDialog(DgInnerMerge.this, "全部已经全部归并！", "警告",
		 * 0); break; }
		 */
	}

	/**
	 * This method initializes miUndoFourInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiUndoFourInnerMerge() {
		if (miUndoFourInnerMerge == null) {
			miUndoFourInnerMerge = new JMenuItem();
			miUndoFourInnerMerge.setText("撤消4码归并");
			miUndoFourInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							unDoFourInnerMerge(tbInnerMerge);
						}
					});
		}
		return miUndoFourInnerMerge;
	}

	/**
	 * This method initializes pmFourInnerMerge
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmFourInnerMerge() {
		if (pmFourInnerMerge == null) {
			pmFourInnerMerge = new JPopupMenu();
		}
		if (tableModel != null) {
			pmFourInnerMerge.removeAll();
			pmFourInnerMerge.add(getMiEditFour());
			pmFourInnerMerge.add(getMiUndoFourInnerMerge());
			// pmFourInnerMerge.addSeparator();
			// pmFourInnerMerge.add(getMiFourResetMemoNo());
			pmFourInnerMerge.addSeparator();
			pmFourInnerMerge.add(tableModel.getMiCopy());
			pmFourInnerMerge.add(tableModel.getMiSearch());
			pmFourInnerMerge.add(tableModel.getMiSaveTableListToExcel());
			tableModel.getMiCopy().setEnabled(isCopyMenuEnabled);
		}
		return pmFourInnerMerge;
	}

	/**
	 * This method initializes pmBeforeTenInnerMerge
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmBeforeTenInnerMerge() {
		if (pmBeforeTenInnerMerge == null) {
			pmBeforeTenInnerMerge = new JPopupMenu();
		}
		if (tableModel != null) {
			pmBeforeTenInnerMerge.removeAll();
			pmBeforeTenInnerMerge.add(getMiNewTenInnerMerge());
			// pmBeforeTenInnerMerge.add(getMiAddTenInnerMerge());
			// pmBeforeTenInnerMerge.addSeparator();
			// pmBeforeTenInnerMerge.add(getMiUndoTenInnerMerge());
			// pmBeforeTenInnerMerge.add(getMiUndoChange());

			pmBeforeTenInnerMerge.addSeparator();
			pmBeforeTenInnerMerge.add(getMiEditMateriel());
			pmBeforeTenInnerMerge.add(getMiDeleteInnerMerge());
			// pmBeforeTenInnerMerge.add(getMiCopy());
			pmBeforeTenInnerMerge.add(getMiForbidMateriel());
			pmBeforeTenInnerMerge.addSeparator();
			pmBeforeTenInnerMerge.add(tableModel.getMiCopy());
			pmBeforeTenInnerMerge.add(tableModel.getMiSearch());
			pmBeforeTenInnerMerge.add(tableModel.getMiSaveTableListToExcel());
			tableModel.getMiCopy().setEnabled(isCopyMenuEnabled);
		}
		return pmBeforeTenInnerMerge;
	}

	/**
	 * 
	 */
	private void unDoTenInnerMerge(JTable jTable) {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		// int result = dzscInnerMergeAction.checkDataForUndoTenInnerMerge(
		// new Request(CommonVars.getCurrUser()), list);
		// switch (result) {
		// case 0:
		List lss = dzscInnerMergeAction.unDoTenInnerMerge(new Request(
				CommonVars.getCurrUser()), list);
		refreshTable(lss);
		// break;
		// case -1:
		// JOptionPane.showMessageDialog(FmDzscInnerMerge.this,
		// "所选择的数据已经有做过4位归并，所以不能撤消10位归并！", "警告", 0);
		// break;
		// }

	}

	/**
	 * 
	 */
	private void unDoFourInnerMerge(JTable jTable) {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		List ls = dzscInnerMergeAction.undoFourInnerMerge(new Request(
				CommonVars.getCurrUser()), list);
		refreshTable(ls);
	}

	/**
	 * This method initializes miResetMemoNo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiResetMemoNo() {
		if (miResetMemoNo == null) {
			miResetMemoNo = new JMenuItem();
			miResetMemoNo.setText("重排10码行号");
			miResetMemoNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							/*
							 * List list = ((AttributiveCellTableModel) jTable
							 * .getModel()).getCurrentRows();
							 * DgInnerMergeResetNo dgTenInnerMergeResetNo = new
							 * DgInnerMergeResetNo(); dgTenInnerMergeResetNo
							 * .setJTable((MultiSpanCellTable) jTable);
							 * dgTenInnerMergeResetNo.setCurrentRows(list);
							 * dgTenInnerMergeResetNo.setTenInnerMergeSort(true);
							 * dgTenInnerMergeResetNo.setVisible(true); if
							 * (dgTenInnerMergeResetNo.isOk() == true) {
							 * selectItem(((ItemProperty) jComboBox
							 * .getSelectedItem()).getCode()); }
							 */
						}
					});
		}
		return miResetMemoNo;
	}

	/**
	 * This method initializes miFourResetMemoNo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiFourResetMemoNo() {
		if (miFourResetMemoNo == null) {
			miFourResetMemoNo = new JMenuItem();
			miFourResetMemoNo.setText("重排4码行号");
			miFourResetMemoNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							/*
							 * List list = ((AttributiveCellTableModel) jTable
							 * .getModel()).getCurrentRows();
							 * DgInnerMergeResetNo dgTenInnerMergeResetNo = new
							 * DgInnerMergeResetNo(); dgTenInnerMergeResetNo
							 * .setJTable((MultiSpanCellTable) jTable);
							 * dgTenInnerMergeResetNo.setCurrentRows(list);
							 * dgTenInnerMergeResetNo.setTenInnerMergeSort(false);
							 * dgTenInnerMergeResetNo.setVisible(true); if
							 * (dgTenInnerMergeResetNo.isOk() == true) {
							 * selectItem(((ItemProperty) jComboBox
							 * .getSelectedItem()).getCode()); }
							 */
						}
					});
		}
		return miFourResetMemoNo;
	}

	// private boolean isNewTenInnerMerge(JTable jTable) {
	// List list = ((AttributiveCellTableModel) jTable.getModel())
	// .getCurrentRows();
	// for (int i = 0; i < list.size(); i++) {
	// DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
	// if (data.getDzscTenInnerMerge() != null) {
	// return false;
	// }
	// }
	// return true;
	// }

	// private boolean checkTenInnerMerges(JTable jTable) {
	// List list = ((AttributiveCellTableModel) jTable.getModel())
	// .getCurrentRows();
	// if (list.size() <= 1) {
	// return false;
	// }
	// for (int i = 0; i < list.size(); i++) {
	// DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
	// if ((i == 0) && (data.getDzscTenInnerMerge() == null)) {
	// return false;
	// } else if ((i != 0) && (data.getDzscTenInnerMerge() != null)) {
	// return false;
	// }
	// }
	// return true;
	// }

	private boolean isCanEditMaterel(AttributiveCellTableModel tableModel) {
		List list = tableModel.getCurrentRows();
		DzscInnerMergeData data = (DzscInnerMergeData) list.get(0);
		if ((data.getBeforeModifyMark() != null)
				&& (data.getBeforeModifyMark().equals(ModifyMarkState.DELETED))) {
			return false;

		}
		return true;
	}

	private boolean isCanDeleteMaterel(AttributiveCellTableModel tableModel) {
		List list = tableModel.getCurrentRows();
		DzscInnerMergeData data = (DzscInnerMergeData) list.get(0);
		if ((data.getBeforeModifyMark() != null)
				&& (data.getBeforeModifyMark().equals(ModifyMarkState.ADDED))
				&& (data.getDzscTenInnerMerge() == null)) {
			return true;

		}
		return false;
	}

	private int isNewFourInnerMerge(JTable jTable) {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (data.getDzscTenInnerMerge() != null
					&& data.getDzscTenInnerMerge().getDzscFourInnerMerge() == null) {
				return 0;
			} else if (data.getDzscTenInnerMerge() == null) {
				return 1;
			} else if (data.getDzscTenInnerMerge() != null
					&& data.getDzscTenInnerMerge().getDzscFourInnerMerge() != null) {
				return 2;
			}
		}
		return -1;
	}

	private boolean checkFourInnerMerges(JTable jTable) {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() <= 1) {
			return false;
		}
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if ((i == 0)
					&& ((data.getDzscTenInnerMerge() == null) || (data
							.getDzscTenInnerMerge().getDzscFourInnerMerge() == null))) {
				return false;
			} else if ((i != 0)
					&& ((data.getDzscTenInnerMerge() == null) || (data
							.getDzscTenInnerMerge().getDzscFourInnerMerge() != null))) {
				return false;
			}
		}
		return true;
	}

	private boolean checkHaveFourInnerMerges(JTable jTable) {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() <= 0) {
			return false;
		}
		DzscInnerMergeData data = (DzscInnerMergeData) list.get(0);
		if (data.getDzscTenInnerMerge() == null
				|| data.getDzscTenInnerMerge().getDzscFourInnerMerge() == null) {
			return false;
		}
		return true;
	}

	// /**
	// * This method initializes miAddTenInnerMerge
	// *
	// * @return javax.swing.JMenuItem
	// */
	// private JMenuItem getMiAddTenInnerMerge() {
	// if (miAddTenInnerMerge == null) {
	// miAddTenInnerMerge = new JMenuItem();
	// miAddTenInnerMerge.setText("补充10码归并");
	// miAddTenInnerMerge
	// .addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// tenInnerMerge(2, tbInnerMerge);
	// }
	// });
	// }
	// return miAddTenInnerMerge;
	// }

	/**
	 * This method initializes miNewFourInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNewFourInnerMerge() {
		if (miNewFourInnerMerge == null) {
			miNewFourInnerMerge = new JMenuItem();
			miNewFourInnerMerge.setText("新增4码归并");
			miNewFourInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							fourInnerMerge(1, tbInnerMerge);
						}
					});
		}
		return miNewFourInnerMerge;
	}

	/**
	 * This method initializes miDeleteInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiDeleteInnerMerge() {
		if (miDeleteInnerMerge == null) {
			miDeleteInnerMerge = new JMenuItem();
			miDeleteInnerMerge.setText("删除归并前(D)");
			miDeleteInnerMerge.setMnemonic('D');
			miDeleteInnerMerge.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_D, ActionEvent.CTRL_MASK));
			miDeleteInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								if (JOptionPane.showConfirmDialog(
										FmDzscInnerMerge.this, "是否确定删除选定数据?",
										"提示", 0) != 0) {
									return;
								}
								List list = new ArrayList();
								list = ((AttributiveCellTableModel) tbInnerMerge
										.getModel()).getCurrentRows();
								if (list.size() < 1) {
									return;
								}
								if (dzscInnerMergeAction.isInnerMerger(
										new Request(CommonVars.getCurrUser()),
										list)) {
									JOptionPane.showMessageDialog(null,
											"数据已做了归并!", "提示!",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								dzscInnerMergeAction
										.deleInnerMergerForMateriel(
												new Request(CommonVars
														.getCurrUser()), list);
								tableModel.deleteRows(list);
								refreshTable();
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null, "删除数据有错!",
										"提示", 0);
							}

						}
					});
		}
		return miDeleteInnerMerge;
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
			btnPrint.setBounds(new java.awt.Rectangle(229, 3, 60, 24));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbReport.getSelectedIndex() < 0
							|| cbbReport.getSelectedItem().equals("")) {
						return;
					}
					String titleName = cbbReport.getSelectedItem().toString();
					List list = null;
					Request request = new Request(CommonVars.getCurrUser());
					if (titleName.equals("(料件)进出口商品归并对应表")) {
						list = dzscInnerMergeAction
								.findInnerMergeDataByTypeForPrint(request,
										MaterielType.MATERIEL);
					} else if (titleName.equals("(成品)进出口商品归并对应表")) {
						list = dzscInnerMergeAction
								.findInnerMergeDataByTypeForPrint(request,
										MaterielType.FINISHED_PRODUCT);
					} else if (titleName.equals("(料件)进出口商品归并对应变更表")) {
						list = dzscInnerMergeAction
								.findInnerMergeDataAndChangeByType(request,
										MaterielType.MATERIEL);
					} else if (titleName.equals("(成品)进出口商品归并对应变更表")) {
						list = dzscInnerMergeAction
								.findInnerMergeDataAndChangeByType(request,
										MaterielType.FINISHED_PRODUCT);
					}
					if (list == null || list.size() <= 0) {
						JOptionPane.showMessageDialog(FmDzscInnerMerge.this,
								"没有报表数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(list);
					try {
						InputStream masterReportStream = FmDzscInnerMerge.class
								.getResourceAsStream("report/InnerMergeReport.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();
						String companyCode = ((Company) CommonVars
								.getCurrUser().getCompany()).getCode();
						String companyName = ((Company) CommonVars
								.getCurrUser().getCompany()).getName();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(new Date());
						String printDate = String.valueOf(calendar
								.get(Calendar.YEAR))
								+ "年"
								+ String
										.valueOf(calendar.get(Calendar.MONTH) + 1)
								+ "月"
								+ String.valueOf(calendar
										.get(Calendar.DAY_OF_MONTH)) + "日";
						parameters.put("companyName", companyName);
						parameters.put("companyCode", companyCode);
						parameters.put("printDate", printDate);
						parameters.put("type", titleName.substring(1, 3) + "栏");
						parameters.put("title", titleName.substring(4,
								titleName.length()));
						parameters
								.put("rowCount", Integer.valueOf(list.size()));
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, ds);
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
	 * 设置单元格提示
	 * 
	 */
	private void setTableCellToopTip() {
		int count = tbInnerMerge.getColumnModel().getColumnCount();
		for (int i = 1; i < count; i++) {
			tbInnerMerge.getColumnModel().getColumn(i).setCellRenderer(
					new ToolTipRenderer());
		}
	}

	/**
	 * toolTip table render 列
	 */
	class ToolTipRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			JLabel lb = (JLabel) super.getTableCellRendererComponent(table,
					value, isSelected, hasFocus, row, column);
			lb.setToolTipText((value == null) ? "" : value.toString());
			return lb;
		}
	}

	/**
	 * This method initializes jSlider
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getJSlider() {
		if (jSlider == null) {
			jSlider = new JSlider();
			// jSlider.setVisible(false);
			initTableInfo();
		}
		return jSlider;
	}

	public void initTableInfo() {
		hmTableInfo = new HashMap();
		int defaultRowHeight = tbInnerMerge.getRowHeight();
		int defaultFontSize = tbInnerMerge.getFont().getSize();
		jSlider.setMaximum(100);
		jSlider.setMinimum(1);
		jSlider.setValue(50);
		jSlider.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				if (hmTableInfo == null) {
					return;
				}
				int maxRowHeight = ((Integer) hmTableInfo.get("MaxRowHeight"))
						.intValue();
				int maxFontSize = ((Integer) hmTableInfo.get("MaxFontSize"))
						.intValue();
				int rowHeight = (((JSlider) e.getSource()).getValue() * maxRowHeight) / 100;
				if (rowHeight < 1) {
					rowHeight = 1;
				}
				int fontSize = (((JSlider) e.getSource()).getValue() * maxFontSize) / 100;
				if (fontSize < 1) {
					fontSize = 1;
				}
				tbInnerMerge.setRowHeight(rowHeight);
				TableColumnModel columnModel = tbInnerMerge.getTableHeader()
						.getColumnModel();
				tbInnerMerge.getTableHeader().setFont(
						new Font("Dialog", java.awt.Font.PLAIN, fontSize));
				int columnCount = columnModel.getColumnCount();
				for (int i = 0; i < columnCount; i++) {
					int maxColumnWidth = ((Integer) hmTableInfo
							.get(new Integer(i))).intValue();
					int columnWidth = (((JSlider) e.getSource()).getValue() * maxColumnWidth) / 100;
					// System.out.println("((JSlider)e.getSource()).getValue():"
					// + ((JSlider) e.getSource()).getValue());
					if (columnWidth < 1) {
						columnWidth = 1;
					}
					TableColumn aColumn = columnModel.getColumn(i);
					aColumn.setPreferredWidth(columnWidth);
				}
				tbInnerMerge.setFont(new Font("Dialog", java.awt.Font.PLAIN,
						fontSize));
				tbInnerMerge.repaint();
			}
		});
		TableColumnModel columnModel = tbInnerMerge.getTableHeader()
				.getColumnModel();
		int columnCount = columnModel.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			hmTableInfo.put(new Integer(i), new Integer(columnModel
					.getColumn(i).getPreferredWidth() * 2));
		}
		hmTableInfo.put("MaxRowHeight", new Integer(defaultRowHeight * 2));
		hmTableInfo.put("MaxFontSize", new Integer(defaultFontSize * 2));
	}

	private void refreshTable(List ls) {
		AttributiveCellTableModel tableModel = (AttributiveCellTableModel) tbInnerMerge
				.getModel();
		tableModel.updateRows(ls);
		refreshTable();
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiEditMateriel() {
		if (miEditMateriel == null) {
			miEditMateriel = new JMenuItem();
			miEditMateriel.setText("修改归并前");
			miEditMateriel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgDzscInnerMergeEdit dg = new DgDzscInnerMergeEdit();
							dg.setTableModel(tableModel);
							dg.setMaterialType(((ItemProperty) cbbMaterielType
									.getSelectedItem()).getCode());
							dg.setAdd(false);
							dg.setVisible(true);
						}
					});
		}
		return miEditMateriel;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	// private JMenuItem getMiCopy() {
	// if (miCopy == null) {
	// miCopy = new JMenuItem();
	// miCopy.setText("转抄");
	// miCopy.addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// List list = ((AttributiveCellTableModel) jTable.getModel())
	// .getCurrentRows();
	// if (list.size() < 1) {
	// return;
	// }
	// dzscAction.copyInnerMergerForMateriel(new Request(
	// CommonVars.getCurrUser()), list);
	// pnCommonQueryPage.refreshData();
	// }
	// });
	// }
	// return miCopy;
	// }
	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiEditTen() {
		if (miEditTen == null) {
			miEditTen = new JMenuItem();
			miEditTen.setText("修改10码");
			miEditTen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DzscInnerMergeData data = (DzscInnerMergeData) tableModel
							.getCurrentRow();
					if (data.getDzscTenInnerMerge() == null) {
						return;
					}
					tenInnerMerge(3, tbInnerMerge);
				}
			});
		}
		return miEditTen;
	}

	/**
	 * This method initializes jMenuItem3
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiEditFour() {
		if (miEditFour == null) {
			miEditFour = new JMenuItem();
			miEditFour.setText("修改4码");
			miEditFour.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fourInnerMerge(3, tbInnerMerge);
				}
			});
		}
		return miEditFour;
	}

	public boolean checkCanEdit(AttributiveCellTableModel tableModel) {
		DzscInnerMergeData data = (DzscInnerMergeData) tableModel
				.getCurrentRow();
		if (data.getStateMark().equals(DzscState.ORIGINAL)
				|| data.getStateMark().equals(DzscState.CHANGE)) {
			return true;
		}
		// JOptionPane.showMessageDialog(this, "该状态下不能对数据操作!", "提示!",
		// JOptionPane.WARNING_MESSAGE);
		return false;
	}

	public boolean checkCanEditTen(AttributiveCellTableModel tableModel) {
		DzscInnerMergeData data = (DzscInnerMergeData) tableModel
				.getCurrentRow();
		if (data.getDzscTenInnerMerge() != null
				&& (data.getDzscTenInnerMerge().getTenModifyMark() != null)
				&& data.getDzscTenInnerMerge().getTenModifyMark().equals(
						ModifyMarkState.DELETED)) {
			return false;
		}
		// JOptionPane.showMessageDialog(this, "该状态下不能对数据操作!", "提示!",
		// JOptionPane.WARNING_MESSAGE);
		return true;
	}

	private void setBtnState() {
		DzscMaterielHead dzscMaterielHead = materialApplyAction
				.findDzscMaterielHead(new Request(CommonVars.getCurrUser()));
		btnAddData.setEnabled(!DzscState.APPLY.equals(dzscMaterielHead
				.getInnerMergeState())); //
		btnImportData.setEnabled(!DzscState.APPLY.equals(dzscMaterielHead
				.getInnerMergeState()));
		btnApply.setEnabled(!DzscState.APPLY.equals(dzscMaterielHead
				.getInnerMergeState()));
		btnEffective.setEnabled(DzscState.APPLY.equals(dzscMaterielHead
				.getInnerMergeState()));
		btnChange.setEnabled(!DzscState.APPLY.equals(dzscMaterielHead
				.getInnerMergeState()));
		if (this.jTabbedPane.getSelectedIndex() == 0) {
			this.btnChange.setText("变更");
		} else if (this.jTabbedPane.getSelectedIndex() == 1) {
			this.btnChange.setText("取消变更");
		}
		
		setMachineState();

	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbReport() {
		if (cbbReport == null) {
			cbbReport = new JComboBox();
			cbbReport.addItem("(成品)进出口商品归并对应表");
			cbbReport.addItem("(料件)进出口商品归并对应表");
			cbbReport.setUI(new CustomBaseComboBoxUI(250));
			cbbReport.setBounds(new java.awt.Rectangle(104, 3, 124, 24));
			cbbReport.setName("cbbReport");
		}
		return cbbReport;
	}

	/**
	 * This method initializes btnimport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImportData() {
		if (btnImportData == null) {
			btnImportData = new JButton();
			btnImportData.setToolTipText("从物料主档或文本中导入");
			btnImportData.setText("导入");
			btnImportData
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							getPmImportData().show(btnImportData, 0,
									btnImportData.getHeight());
						}
					});
		}
		return btnImportData;
	}

	/**
	 * This method initializes cbShowDataParam
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCombineRows() {
		if (cbCombineRows == null) {
			cbCombineRows = new JCheckBox();
			cbCombineRows.setText("合并显示");
			cbCombineRows.setBounds(new java.awt.Rectangle(301, 3, 77, 24));
			cbCombineRows.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					refreshTable();
				}
			});
		}
		return cbCombineRows;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);

		}
		return jPanel1;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
		}
		return jPanel2;
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
		if (tbInnerMerge == null) {
			tbInnerMerge = new JTable();
		}
		return tbInnerMerge;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setFloatable(false);
			jJToolBarBar.add(getPnCommonQueryPage());
		}
		return jJToolBarBar;
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
		String materielType = ((ItemProperty) cbbMaterielType.getSelectedItem())
				.getCode();
		if (materielType == null) {
			return new ArrayList();
		}
		Request request = new Request(CommonVars.getCurrUser());
		if (this.jTabbedPane.getSelectedIndex() == 0) {
			dataSource = dzscInnerMergeAction.findInnerMergeDataByType(request,
					materielType, index, length, property, value, isLike);
		} else if (this.jTabbedPane.getSelectedIndex() == 1) {
			dataSource = dzscInnerMergeAction.findChangedInnerMergeData(
					request, materielType, index, length, property, value,
					isLike);
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

				@Override
				public JTableListModel initTable(List dataSource) {
					return FmDzscInnerMerge.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return FmDzscInnerMerge.this.getDataSource(index, length,
							property, value, isLike);
				}

			};
			// pnCommonQueryPage.setLength(100);
		}
		return pnCommonQueryPage;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton();
			btnApply.setText("海关申报");
			btnApply.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmDzscInnerMerge.this,
							"你确定要申报这些内部归并数据吗?", "提示",
							JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					new ApplyThread().start();
				}
			});

		}
		return btnApply;
	}

	class ApplyThread extends Thread {
		public void run() {
			try {
				DgInnerMergeApplySelect dg = new DgInnerMergeApplySelect();
				dg.setVisible(true);
				if (!dg.isOk()) {
					return;
				}
				TempInnerMergeApplySelectParam param = dg.getParam();
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				// String scmCoiCode = ((ScmCoi) jList.getSelectedValue())
				// .getCode();
				try {
					DeclareFileInfo fileInfo = dzscInnerMergeAction.applyInnerMerge(
							request, param);
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmDzscInnerMerge.this,
							fileInfo.getFileInfoSpec(), "提示",
							JOptionPane.INFORMATION_MESSAGE);
					// if (jTabbedPane.getSelectedIndex() == 0) {
					pnCommonQueryPage.setInitState();
					// state = DzscState.APPLY;
					// } else if (jTabbedPane.getSelectedIndex() == 1) {
					// pnCommonQueryPage1.setInitState();
					// state1 = DzscState.APPLY;
					// }
					setBtnState();
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmDzscInnerMerge.this,
							"申报失败 " + ex.getMessage(), "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes jButton2
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
							DzscBusinessType.INNER_MERGE,
							dzscMaterielHead.getCopEntNo());
					if (lsReturnFile.size()<=0) {
						return;
					}
					// if (JOptionPane.showConfirmDialog(FmDzscInnerMerge.this,
					// "你确定要处理内部归并数据的回执吗?", "提示",
					// JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
					// return;
					// }
					try {
						String result = dzscInnerMergeAction
								.proccessDzscInnerMerge(new Request(CommonVars
										.getCurrUser()),lsReturnFile);
						JOptionPane.showMessageDialog(FmDzscInnerMerge.this,
								"处理回执成功\n " + result, "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(FmDzscInnerMerge.this,
								"处理回执失败 " + ex.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					// if (jTabbedPane.getSelectedIndex() == 0) {
					pnCommonQueryPage.setInitState();
					// state = DzscState.EXECUTE;
					// } else if (jTabbedPane.getSelectedIndex() == 1) {
					// pnCommonQueryPage1.setInitState();
					// state1 = DzscState.EXECUTE;
					// }
					setBtnState();
				}
			});
		}
		return btnEffective;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChange() {
		if (btnChange == null) {
			btnChange = new JButton();
			btnChange.setText("变更");
			btnChange.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						List list = tableModel.getCurrentRows();
						if (list.size() < 1) {
							JOptionPane.showMessageDialog(
									FmDzscInnerMerge.this, "请选择要变更的内部归并数据",
									"提示", JOptionPane.OK_CANCEL_OPTION);
							return;
						}
						if (JOptionPane.showConfirmDialog(
								FmDzscInnerMerge.this, "你确定要变更这些内部归并数据吗?",
								"提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}
						list = dzscInnerMergeAction.changeInnerMerge(
								new Request(CommonVars.getCurrUser()), list);
						StringBuffer sb = new StringBuffer();
						if (list.size() > 0) {
							for (int i = 0; i < list.size(); i++) {
								sb.append(((DzscInnerMergeData) list.get(i))
										.getMateriel().getPtNo()
										+ ";");
							}
							JOptionPane.showMessageDialog(
									FmDzscInnerMerge.this, "货号如下\n"
											+ sb.toString() + "\n的归并数据已经有变更数据",
									"提示", JOptionPane.INFORMATION_MESSAGE);
						}
						jTabbedPane.setSelectedIndex(1);
						// tableModel.updateRows(tableModel.getCurrentRows());
						// if (jTabbedPane.getSelectedIndex() == 0) {
						// pnCommonQueryPage.setInitState();
						// } else if (jTabbedPane.getSelectedIndex() == 1) {
						//							
						// }
					} else {
						List list = tableModel.getCurrentRows();
						if (list.size() < 1) {
							JOptionPane.showMessageDialog(
									FmDzscInnerMerge.this, "请选择要取消变更的内部归并数据",
									"提示", JOptionPane.OK_CANCEL_OPTION);
							return;
						}
						if (JOptionPane.showConfirmDialog(
								FmDzscInnerMerge.this, "你确定要取消变更这些内部归并数据吗?",
								"提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}
						dzscInnerMergeAction.deleteDzscInnerMergeData(
								new Request(CommonVars.getCurrUser()), list);
						pnCommonQueryPage.setInitState();
					}
				}
			});
		}
		return btnChange;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmDzscInnerMerge.this.doDefaultCloseAction();
					// dzscInnerMergeAction
					// .convertOldDataToNewData(new Request(CommonVars
					// .getCurrUser()));
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInnerMerge() {
		if (btnInnerMerge == null) {
			btnInnerMerge = new JButton();
			btnInnerMerge.setText("归并");
			btnInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							getPmInnerMerge().show(btnInnerMerge, 0,
									btnInnerMerge.getHeight());
							getPmInnerMerge().show(btnInnerMerge, 0,
									btnInnerMerge.getHeight());
						}
					});
		}
		return btnInnerMerge;
	}

	/**
	 * This method initializes miAutoInnerMerge1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getmiAutoInnerMerge1() {
		if (miAutoInnerMerge == null) {
			miAutoInnerMerge = new JMenuItem();
			miAutoInnerMerge.setBounds(new java.awt.Rectangle(541, 5, 29, 10));
		}
		return miAutoInnerMerge;
	}

	/**
	 * This method initializes miCustomInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCustomInnerMerge() {
		if (miCustomInnerMerge == null) {
			miCustomInnerMerge = new JMenuItem();
			miCustomInnerMerge
					.setBounds(new java.awt.Rectangle(533, 13, 29, 10));
			miCustomInnerMerge.setText("自定义归并");
			miCustomInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgDzscCustomInnerMerge dgCustomInnerMerge = new DgDzscCustomInnerMerge();
							dgCustomInnerMerge.setVisible(true);
							if (dgCustomInnerMerge.isBeginCustomInnerOk() == true) {
								new CustomInnerMergeDataThread().start();
							}
						}
					});
		}
		return miCustomInnerMerge;
	}

	class AutoInnerMergeDataThread extends Thread {
		public void run() {
			try {
				TempDzscAutoInnerMergeParam param = null;
				DgDzscAutoInnerMergeParam dgParam = new DgDzscAutoInnerMergeParam();
				dgParam.setVisible(true);
				if (dgParam.isOK()) {
					param = dgParam.getAutoInnerMergeParam();
					if (param == null) {
						return;
					}
				} else {
					return;
				}
				FmDzscInnerMerge.this.miAutoInnerMerge.setEnabled(false);
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("正在自动归并数据,请稍候...");
				try {
					dzscInnerMergeAction.dzscAutoInnerMergeData(new Request(
							CommonVars.getCurrUser()), param);
				} catch (Exception e) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(null, "自动归并数据可能有错!!", "信息!!",
							0);
				}
				CommonProgress.setMessage("正在刷新数据表,请稍候...");
				pnCommonQueryPage.refreshData();
			} finally {
				CommonProgress.closeProgressDialog();
				FmDzscInnerMerge.this.miAutoInnerMerge.setEnabled(true);
			}
		}
	}

	class CustomInnerMergeDataThread extends Thread {
		public void run() {
			try {
				FmDzscInnerMerge.this.miCustomInnerMerge.setEnabled(false);
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("正在刷新数据表,请稍候...");
				pnCommonQueryPage.refreshData();
			} finally {
				CommonProgress.closeProgressDialog();
				FmDzscInnerMerge.this.miCustomInnerMerge.setEnabled(true);
			}
		}
	}

	/**
	 * This method initializes PmInnerMerge
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmInnerMerge() {
		if (pmInnerMerge == null) {
			pmInnerMerge = new JPopupMenu();
			pmInnerMerge.add(getmiCustomInnerMerge());
			pmInnerMerge.add(getmiAutoInnerMerge());
		}
		return pmInnerMerge;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getmiCustomInnerMerge() {
		if (miCustomInnerMerge == null) {
			miCustomInnerMerge = new JMenuItem();
			miCustomInnerMerge.setText("自定义归并");
			miCustomInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgDzscCustomInnerMerge dgCustomInnerMerge = new DgDzscCustomInnerMerge();
							dgCustomInnerMerge.setVisible(true);
							if (dgCustomInnerMerge.isBeginCustomInnerOk() == true) {
								new CustomInnerMergeDataThread().start();
							}

						}
					});
		}
		return miCustomInnerMerge;
	}

	/**
	 * This method initializes miAutoInnerMerge1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getmiAutoInnerMerge() {
		if (miAutoInnerMerge == null) {
			miAutoInnerMerge = new JMenuItem();
			miAutoInnerMerge.setText("自动归并");
			miAutoInnerMerge.setVisible(false);
			miAutoInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							new AutoInnerMergeDataThread().start();
						}
					});
		}
		return miAutoInnerMerge;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmImportData() {
		if (pmImportData == null) {
			pmImportData = new JPopupMenu();
			pmImportData.setSize(new java.awt.Dimension(44, 43));
			pmImportData.add(getMiFromMaterial());
			pmImportData.add(getMiFromFile());
		}
		return pmImportData;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiFromMaterial() {
		if (miFromMaterial == null) {
			miFromMaterial = new JMenuItem();
			miFromMaterial.setText("从物料备案");
			miFromMaterial
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								DgDzscMaterielImportItem dg = new DgDzscMaterielImportItem();
								dg.setVisible(true);
								if (dg.isOk() == true) {
									List materielType = dg.returnValue();
									if (materielType == null
											|| materielType.size() <= 0) {
										return;
									}
									ImportMaterielDataRunnable thread = new ImportMaterielDataRunnable(
											materielType);
									thread.start();
								}
							} catch (Exception ex) {

							}
						}
					});
		}
		return miFromMaterial;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiFromFile() {
		if (miFromFile == null) {
			miFromFile = new JMenuItem();
			miFromFile.setText("\u4ece\u6587\u4ef6");
			miFromFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscImportDataFromFile dg = new DgDzscImportDataFromFile();
					dg.setVisible(true);
					if (dg.isOk() == false) {
						return;
					}
					if (dg.getMaterielType() == null) {
						return;
					}
					if (((ItemProperty) cbbMaterielType.getSelectedItem())
							.getCode().equals(dg.getMaterielType())) {
						pnCommonQueryPage.setInitState();
					} else {
						cbbMaterielType.setSelectedIndex(ItemProperty
								.getIndexByCode(dg.getMaterielType(),
										cbbMaterielType));
					}
				}
			});
		}
		return miFromFile;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("未变更", null, getJPanel1(), null);
			jTabbedPane.addTab("变更中", null, getJPanel2(), null);
			// jTabbedPane.addTab("未变更", null, getJScrollPane(), null);
			// jTabbedPane.addTab("变更中", null, getJScrollPane(), null);
			jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {
								jPanel1.add(getJScrollPane(),
										java.awt.BorderLayout.CENTER);
							} else {
								jPanel2.add(getJScrollPane(),
										java.awt.BorderLayout.CENTER);
							}
							// showData();
							// if (jTabbedPane.getSelectedIndex() == 0) {
							pnCommonQueryPage.setInitState();
							// setState();
							setBtnState();
							// } else if (jTabbedPane.getSelectedIndex() == 1) {
							// pnCommonQueryPage1.setInitState();
							// // setState();
							// setBtnState();
							// }
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiForbidMateriel() {
		if (miForbidMateriel == null) {
			miForbidMateriel = new JMenuItem();
			miForbidMateriel.setText("禁用此物料");
			miForbidMateriel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								if (JOptionPane.showConfirmDialog(
										FmDzscInnerMerge.this, "是否确定删除选定数据?",
										"提示", 0) != 0) {
									return;
								}
								List list = new ArrayList();
								// if (jTabbedPane.getSelectedIndex() == 0) {

								list = ((AttributiveCellTableModel) tbInnerMerge
										.getModel()).getCurrentRows();
								// } else if (jTabbedPane.getSelectedIndex() ==
								// 1) {
								// list = ((AttributiveCellTableModel) tbChanged
								// .getModel()).getCurrentRows();
								// }
								if (list.size() < 1) {
									return;
								}
								if (dzscInnerMergeAction.isInnerMerger(
										new Request(CommonVars.getCurrUser()),
										list)) {
									JOptionPane.showMessageDialog(null,
											"数据已做了归并!", "提示!",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								dzscInnerMergeAction
										.forbidInnerMergeForMateriel(
												new Request(CommonVars
														.getCurrUser()), list);
								tableModel.deleteRows(list);
								refreshTable();
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null, "删除数据有错!",
										"提示", 0);
							}
						}
					});
		}
		return miForbidMateriel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOtherFunction() {
		if (btnOtherFunction == null) {
			btnOtherFunction = new JButton();
			btnOtherFunction.setText("其他功能");
			btnOtherFunction
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							getPmOtherFunction().show(btnOtherFunction, 0,
									btnOtherFunction.getHeight());
						}
					});
		}
		return btnOtherFunction;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmOtherFunction() {
		if (pmOtherFunction == null) {
			pmOtherFunction = new JPopupMenu();
			pmOtherFunction.add(getMiShowAll());
			pmOtherFunction.add(getMiShowNotMerge());
			pmOtherFunction.add(getBtnDeleteNotMerge());
			pmOtherFunction.add(getJMenu());
			pmOtherFunction.addSeparator();
			pmOtherFunction.add(this.getCbCombineRows());
			pmOtherFunction.add(this.getJSlider());
		}
		return pmOtherFunction;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiShowAll() {
		if (miShowAll == null) {
			miShowAll = new JMenuItem();
			miShowAll.setText("显示所有归并");
			miShowAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					pnCommonQueryPage.setInitState();
					setBtnState();
				}
			});
		}
		return miShowAll;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiShowNotMerge() {
		if (miShowNotMerge == null) {
			miShowNotMerge = new JMenuItem();
			miShowNotMerge.setText("显示未归并");
			miShowNotMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String materielType = ((ItemProperty) cbbMaterielType
									.getSelectedItem()).getCode();
							List list = new ArrayList();
							if (jTabbedPane.getSelectedIndex() == 0) {
								list = dzscInnerMergeAction
										.findDzscNotMergeInnerMergeData(
												new Request(CommonVars
														.getCurrUser()),
												materielType, false);

							} else if (jTabbedPane.getSelectedIndex() == 1) {
								list = dzscInnerMergeAction
										.findDzscNotMergeInnerMergeData(
												new Request(CommonVars
														.getCurrUser()),
												materielType, true);
							}
							initTable(list);
						}
					});
		}
		return miShowNotMerge;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getBtnDeleteNotMerge() {
		if (btnDeleteNotMerge == null) {
			btnDeleteNotMerge = new JMenuItem();
			btnDeleteNotMerge.setText("删除未归并");
			btnDeleteNotMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String materielType = ((ItemProperty) cbbMaterielType
									.getSelectedItem()).getCode();
							if (jTabbedPane.getSelectedIndex() == 0) {
								dzscInnerMergeAction
										.deleteDzscNotMergeInnerMergeData(
												new Request(CommonVars
														.getCurrUser()),
												materielType, false);
							} else if (jTabbedPane.getSelectedIndex() == 1) {
								dzscInnerMergeAction
										.deleteDzscNotMergeInnerMergeData(
												new Request(CommonVars
														.getCurrUser()),
												materielType, true);
							}
							pnCommonQueryPage.setInitState();
						}
					});
		}
		return btnDeleteNotMerge;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnToolBar() {
		if (pnToolBar == null) {
			pnToolBar = new JPanel();
			pnToolBar.setLayout(new BorderLayout());
			pnToolBar.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			pnToolBar.add(getJJToolBarBar(), java.awt.BorderLayout.SOUTH);
		}
		return pnToolBar;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenu() {
		if (jMenu == null) {
			jMenu = new JMenu();
			jMenu.setText("特殊处理(慎用)");
			jMenu.add(getJMenuItem());
		}
		return jMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText("回滚<正在执行>的状态到<原始状态>");
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						List list = tableModel.getCurrentRows();
						if (list.size() <= 0) {
							return;
						}
						list = dzscInnerMergeAction.resetExecuteToOriginal(
								new Request(CommonVars.getCurrUser()), list);
						tableModel.updateRows(list);
					}
				}
			});
		}
		return jMenuItem;
	}
	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiAddFourInnerMerge() {
		if (miAddFourInnerMerge == null) {
			miAddFourInnerMerge = new JMenuItem();
			miAddFourInnerMerge.setText("补充4码归并");
			miAddFourInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							ItemProperty item = (ItemProperty) cbbMaterielType
									.getSelectedItem();
							if (item == null) {
								JOptionPane.showMessageDialog(
										FmDzscInnerMerge.this, "请选择物料类别！",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							String materielType = item.getCode().trim();
							DzscFourInnerMerge fourInnerMerge = DzscInnerMergeQuery
									.getInstance()
									.findDzscFourInnerMerge(materielType,
											jTabbedPane.getSelectedIndex() == 1);
							if (fourInnerMerge != null) {
								List list = tableModel.getCurrentRows();
								list = dzscInnerMergeAction.addFourInnerMerge(
										new Request(CommonVars.getCurrUser(),
												true), list, fourInnerMerge);
								refreshTable(list);
							}
						}
					});
		}
		return miAddFourInnerMerge;
	}

	/**
	 * 当为设备时，控制按钮的状态
	 * 
	 */
	private void setMachineState(){
		if (cbbMaterielType.getSelectedItem() != null) {
			ItemProperty itemProperty = (ItemProperty) cbbMaterielType
					.getSelectedItem();
			if (itemProperty.getCode().equals(
					MaterielType.MACHINE)) {// 设备
				btnApply.setEnabled(false);//海关申报
				btnEffective.setEnabled(false);//处理回执
				btnChange.setEnabled(false);//变更
				btnPrint.setEnabled(false);//打印
				cbbReport.setSelectedIndex(-1);
				cbbReport.setEnabled(false);
//				getMiForbidMateriel().setEnabled(false);//禁用此物料
			}else{
				btnPrint.setEnabled(true);//打印
				cbbReport.setEnabled(true);
			}
				
			
		}
	}
	
	
} 

