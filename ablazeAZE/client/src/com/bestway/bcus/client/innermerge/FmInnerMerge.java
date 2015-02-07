/*
 * Created on 2004-7-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.innermerge.entity.TempAutoInnerMergeParam;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.CommonTableContextPopupEvent;
import com.bestway.client.util.DataType;
import com.bestway.client.util.ExcelAdapter;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModel.SortMouseListener;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author bsway 请改代码的同事注意：一定先看类字段说明再改 // change the template for this generated
 *         type comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
@SuppressWarnings({ "unchecked", "serial" })
public class FmInnerMerge extends JInternalFrameBase {
	private CommonBaseCodeAction commonBaseCodeAction = null;

	private AttributiveCellTableModel tableModel = null;

	private List dataSource = null; // @jve:decl-index=0:

	private JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel = null;

	private JComboBox cbbMaterielType = null;

	private JButton btnImportData = null;

	private JPopupMenu pmImportData = null;

	private JMenuItem mtMateriel = null;

	private JMenuItem mtFile = null;

	private JPopupMenu pmAfterTenInnerMerge = null;

	private JMenuItem miNewTenInnerMerge = null;

	private JMenuItem miAddFourInnerMerge = null;

	private JMenuItem miUndoFourInnerMerge = null;

	private JPopupMenu pmFourInnerMerge = null;

	private JPopupMenu pmBeforeTenInnerMerge = null;

	private JMenuItem miUndoTenInnerMerge1 = null;

	private JMenuItem miUndoFourInnerMerge1 = null;

	private JMenuItem miFourResetMemoNo = null;

	private JMenuItem miAddTenInnerMerge = null;

	private JMenuItem miNewFourInnerMerge = null;

	private JMenuItem miDeleteInnerMerge = null;

	private JButton btnPrint = null;

	private HashMap hmTableInfo = null;

	private JPopupMenu pmTableContextPopupMenu = null;
	private JMenuItem miCopyValue = null;
	private ExcelAdapter excelAdapter = null;
	private int xpoint = 0;

	private int ypoint = 0;

	private boolean isCopyMenuEnabled = false;

	private JPopupMenu pmInnerMerge = null;

	private JButton btnInnerMerge = null;

	private JMenuItem miAutoInnerMerge = null;

	private JMenuItem miCustomInnerMerge = null;

	private JMenuItem miReverseMerge = null;

	private JMenuItem miEditTenInnerMerge = null;

	private PnCommonQueryPage pnCommonQueryPage = null;

	private JButton btnAddMateriel = null;

	private JButton btnExit = null;

	private JLabel jLabel = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar1 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private boolean isNoten = false;

	private JButton jButton3 = null;

	private JCheckBox jCheckBox = null;

	private boolean isfirstOpen = true;

	/**
	 * This method initializes
	 * 
	 */
	public FmInnerMerge() {
		super();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		initialize();

		jTable = new MultiSpanCellTable();
		jTable.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (tableModel == null)
					return;
				if (tableModel.getCurrentRow() == null)
					return;
				if (e.isControlDown() == true
						&& e.getKeyCode() == KeyEvent.VK_L) {
					// 隐藏键改变状态
					List ls = ((AttributiveCellTableModel) jTable.getModel())
							.getCurrentRows();
					ls = commonBaseCodeAction.changeNotMerger(new Request(
							CommonVars.getCurrUser()), ls);
					refreshTable(ls);
					refresh();
				}
			}
		});
		jTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					edit();
				}
			}
		});
		jTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				if (e.getModifiers() != InputEvent.BUTTON3_MASK) {
					return;
				}
				int[] columns = jTable.getSelectedColumns();
				int[] rows = jTable.getSelectedRows();
				int selectStartPointX = 0;
				int selectEndPointX = 0;
				xpoint = e.getX();
				ypoint = e.getY();
				//
				// 是否有上下文菜单
				//
				boolean hasTableContextPopupMenu = true;

				if (columns.length > 0 && rows.length > 0) {

					for (int i = 0; i < columns[0]; i++) {
						selectStartPointX += jTable.getColumnModel()
								.getColumn(i).getWidth()
								+ jTable.getColumnModel().getColumnMargin();
					}
					for (int i = 0; i <= columns[columns.length - 1]; i++) {
						selectEndPointX += jTable.getColumnModel().getColumn(i)
								.getWidth()
								+ jTable.getColumnModel().getColumnMargin();
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
							selectStartPointY += jTable.getRowHeight(i)
									+ jTable.getRowMargin();
						}
						for (int i = 0; i <= j; i++) {
							selectEndPointY += jTable.getRowHeight(i)
									+ jTable.getRowMargin();
						}
						if (e.getPoint().x >= selectStartPointX
								&& e.getPoint().x <= selectEndPointX
								&& e.getPoint().y >= selectStartPointY
								&& e.getPoint().y <= selectEndPointY) {
							isCopyMenuEnabled = true;
							excelAdapter = new ExcelAdapter(jTable, 0);
							if (columns[0] > 0 && columns[0] < 6) {
								if (isNewTenInnerMerge()) {
									getMiNewTenInnerMerge().setEnabled(true);
									getMiAddTenInnerMerge().setEnabled(true);
									getMiUndoTenInnerMerge1().setEnabled(false);
								} else {
									getMiNewTenInnerMerge().setEnabled(false);
									getMiAddTenInnerMerge().setEnabled(false);
									getMiUndoTenInnerMerge1().setEnabled(true);
								}
								getPmBeforeTenInnerMerge().show(jTable,
										e.getPoint().x, e.getPoint().y);
							} else if (columns[0] > 5 && columns[0] < 11) {
								if (isNewFourInnerMerge()) {
									getMiNewFourInnerMerge().setEnabled(true);
									getMiAddFourInnerMerge().setEnabled(true);
								} else {
									getMiNewFourInnerMerge().setEnabled(false);
									getMiAddFourInnerMerge().setEnabled(false);
								}
								if (isEditTenInnerMerge()) {
									getMiEditTenInnerMerge().setEnabled(true);
								} else {
									getMiEditTenInnerMerge().setEnabled(false);
								}
								getPmAfterTenInnerMerge().show(jTable,
										e.getPoint().x, e.getPoint().y);
							} else if (columns[0] > 10) {
								getPmFourInnerMerge().show(jTable,
										e.getPoint().x, e.getPoint().y);
							}
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
					getTableContextPopupMenu().show(jTable, e.getPoint().x,
							e.getPoint().y);
					return;
				}
				// if (columns.length == jTable.getColumnCount()) {
				// //
				// // 加入公共弹出菜单
				// //
				// isCopyMenuEnabled = true;
				// getTableContextPopupMenu().show(jTable, e.getPoint().x,
				// e.getPoint().y);
				// return;
				// }

			}
		});
		jTable.getColumnModel().addColumnModelListener(
				new TableColumnModelListener() {
					/** Tells listeners that a column was added to the model. */
					public void columnAdded(TableColumnModelEvent e) {
					}

					/**
					 * Tells listeners that a column was removed from the model.
					 */
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
						int[] columns = ((MultiSpanCellTable) jTable)
								.getSelectedColumns();
						int[] rows = ((MultiSpanCellTable) jTable)
								.getSelectedRows();
						// int[] columns = jTable.getSelectedColumns();
						// int[] rows = jTable.getSelectedRows();
						if (columns.length < 1 || rows.length < 1) {
							return;
						}
						// for (int i = 0; i < rows.length; i++) {
						// System.out.println("row:" + rows[i]);
						// }
						if (columns[0] > 0 && columns[0] < 6) {
							jTable.setColumnSelectionInterval(1, 5);
							return;
						} else if (columns[0] > 5 && columns[0] < 13) {
							jTable.setColumnSelectionInterval(6, 12);
							return;
						} else if (columns[0] > 12 && columns[0] <= 15) {
							jTable.setColumnSelectionInterval(13, 15);
							return;
						}
					}
				});
		jScrollPane.setViewportView(jTable);
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
		this.setTitle("内部归并");
		this.setContentPane(getJContentPane());
		this.getPmAfterTenInnerMerge();
		this.setSize(845, 376);
		this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			public void internalFrameOpened(
					javax.swing.event.InternalFrameEvent e) {
				pnCommonQueryPage.setInitState();
			}
		});
		isNoten = false;
		isfirstOpen = true;
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable gJTable() {
		if (jTable == null) {
			jTable = new JTable();

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
			jScrollPane.setViewportView(gJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.setLayout(f);
			jToolBar.add(getJPanel());
			jToolBar.add(getBtnAddMateriel());
			jToolBar.add(getJButton());
			jToolBar.add(getBtnImportData());
			jToolBar.add(getBtnInnerMerge());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton3());
			jToolBar.add(getBtnExit());
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
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(3, 10, 52, 18));
			jLabel.setText("物料类别");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(250, 34));
			jPanel.add(getCbbMaterielType(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getJCheckBox(), null);
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

			cbbMaterielType.setBounds(55, 4, 114, 27);

			cbbMaterielType.addItem(new ItemProperty(
					MaterielType.FINISHED_PRODUCT, "成品"));

			cbbMaterielType.addItem(new ItemProperty(
					MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));

			cbbMaterielType.addItem(new ItemProperty(MaterielType.MATERIEL,
					"料件"));

			cbbMaterielType
					.addItem(new ItemProperty(MaterielType.MACHINE, "设备"));

			cbbMaterielType.addItem(new ItemProperty(
					MaterielType.REMAIN_MATERIEL, "边角料"));

			cbbMaterielType.addItem(new ItemProperty(MaterielType.BAD_PRODUCT,
					"残次品"));

			cbbMaterielType.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(java.awt.event.ItemEvent e) {

					if (e.getStateChange() == ItemEvent.SELECTED) {

						pnCommonQueryPage.setInitState();

					}
				}
			});

		}
		return cbbMaterielType;
	}

	private JTableListModel initTable(List dataSource) {
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) jTable,
				dataSource, new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料号", "materiel.ptNo", 100));
						list.add(addColumn("10位商品编码", "hsAfterComplex.code",
								100));
						list.add(addColumn("商品名称.规格,型号",
								"hsBeforeMaterielNameSpec",
								"hsAfterMaterielTenName", 150));
						list.add(addColumn("法定单位",
								"hsAfterComplex.firstUnit.name", 50));
						list.add(addColumn("企业单位", "materiel.calUnit.name", 50));
						list.add(addColumn("备案序号", "hsAfterTenMemoNo", 50,
								Integer.class));
						list.add(addColumn("10位商品编码", "hsAfterComplex.code",
								100));
						list.add(addColumn("商品名称", "hsAfterMaterielTenName",
								120));

						list.add(addColumn("商品规格,型号", "hsAfterMaterielTenSpec",
								120));
						list.add(addColumn("法定单位一",
								"hsAfterComplex.firstUnit.name", 50));

						list.add(addColumn("法定单位二",
								"hsAfterComplex.secondUnit.name", 50));
						list.add(addColumn("备案单位", "hsAfterMemoUnit.name", 50));
						list.add(addColumn("4位编码序号", "hsFourNo", 70,
								Integer.class));
						list.add(addColumn("4位商品名称", "hsFourMaterielName", 120));
						list.add(addColumn("4位商品编码", "hsFourCode", 70));
						list.add(addColumn("是否已备案", "isMerger",
								"isExistMerger", DataType.BOOLEAN, 70));
						list.add(addColumn("是否禁用", "isForbid", 70));
						list.add(addColumn("企业单价", "materiel.ptPrice", 70));
						list.add(addColumn("企业单重", "materiel.ptNetWeight", 70));
						list.add(addColumn("单位折算系数", "materiel.unitConvert", 80));

						list.add(addColumn("第一法定单位比例因子", "fristUnitRatio", 120));
						list.add(addColumn("第二法定单位比例因子", "secondUnitRatio", 120));
						list.add(addColumn("重量单位比例因子", "weigthUnitGene", 120));
						list.add(addColumn("是否主料", "isMainImg", 120));

						list.add(addColumn("导入时间", "importTimer", 70));
						list.add(addColumn("修改时间", "updateDate", 70));
						return list;
					}
				});

		TableColumnModel cm = jTable.getColumnModel();

		ColumnGroup gBefore = new ColumnGroup("归并前");
		gBefore.add(cm.getColumn(1));
		gBefore.add(cm.getColumn(2));
		gBefore.add(cm.getColumn(3));
		gBefore.add(cm.getColumn(4));
		gBefore.add(cm.getColumn(5));

		ColumnGroup gAfter = new ColumnGroup("归并后");
		gAfter.add(cm.getColumn(6));
		gAfter.add(cm.getColumn(7));
		gAfter.add(cm.getColumn(8));
		gAfter.add(cm.getColumn(9));
		gAfter.add(cm.getColumn(10));
		gAfter.add(cm.getColumn(11));
		gAfter.add(cm.getColumn(12));

		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gBefore);
		header.addColumnGroup(gAfter);
		this.setTableCellToopTip();
		refreshTable();
		refresh();
		this.deleteCommonTableContextPopupEventMouseListener(jTable);
		setRowColor(jTable);
		jTable.getColumnModel().getColumn(17)
				.setCellRenderer(new TableCellRenderer());
		jTable.getColumnModel().getColumn(24)
				.setCellRenderer(new TableCellRenderer());
		return tableModel;
	}

	private class TableCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			if (value == null || value.equals("false")) {
				this.setText("否");
			} else if (value.equals("true")) {
				this.setText("是");
			}
			return this;
		}
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
	 * render table color row
	 */
	class ColorTableCellRenderer extends DefaultTableCellRenderer {

		private Date maxImportTimer = null;

		public ColorTableCellRenderer(Date maxImportTimer) {
			this.maxImportTimer = maxImportTimer;
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			boolean isChange = false;
			if (checkValue(table, row, column, maxImportTimer)) {
				// c.setBackground(IMPORT_COLOR);
				// c.setForeground(table.getForeground());
				c.setForeground(new Color(0, 0, 204));
				c.setBackground(table.getBackground());
				isChange = true;
			}
			if (isSelected) {
				c.setForeground(table.getSelectionForeground());
				c.setBackground(table.getSelectionBackground());
			}
			if (isChange == false && !isSelected) {
				c.setForeground(table.getForeground());
				c.setBackground(table.getBackground());
			}
			return c;
		}
	}

	/**
	 * 设置数据着色
	 */
	private void setRowColor(JTable jTable) {
		String materielType = ((ItemProperty) cbbMaterielType.getSelectedItem())
				.getCode();
		Date maxImportTimer = this.commonBaseCodeAction
				.findMaxImportTimerByInnerMergeData(
						new Request(CommonVars.getCurrUser()), materielType);
		JTableListModel tableModel = (JTableListModel) jTable.getModel();
		for (int i = 1; i < tableModel.getColumnCount(); i++) {
			jTable.getColumnModel()
					.getColumn(i)
					.setCellRenderer(new ColorTableCellRenderer(maxImportTimer));
		}
	}

	private boolean checkValue(JTable table, int row, int column,
			Date maxImportTimer) {
		JTableListModel tableModel = (JTableListModel) table.getModel();
		InnerMergeData data = (InnerMergeData) tableModel.getDataByRow(row);
		if (data == null) {
			return false;
		}
		if (data.getImportTimer() == null || data.getUpdateDate() != null) {
			return false;
		}
		if (data.getImportTimer().toString().equals(maxImportTimer.toString())) {
			return true;
		}
		return false;
	}

	/**
	 * This method initializes btnImportData
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImportData() {
		if (btnImportData == null) {
			btnImportData = new JButton();
			btnImportData.setText("导入");
			btnImportData.setPreferredSize(new Dimension(60, 30));
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
	 * This method initializes pmImportData
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmImportData() {
		if (pmImportData == null) {
			pmImportData = new JPopupMenu();
			// pmImportData.setVisible(false);
			pmImportData.add(getMtMateriel());
			pmImportData.add(getMtFile());
		}
		return pmImportData;
	}

	/**
	 * This method initializes mtMateriel
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMtMateriel() {
		if (mtMateriel == null) {
			mtMateriel = new JMenuItem();
			mtMateriel.setText("来源于物料主档");
			mtMateriel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						DgMaterielImportItem dg = new DgMaterielImportItem();
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
		return mtMateriel;
	}

	class ImportMaterielDataRunnable extends Thread {
		private List materielTypelist = null;

		public ImportMaterielDataRunnable(List materielTypelist) {
			this.materielTypelist = materielTypelist;
		}

		public void run() {
			boolean isinit = false;
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在从物料主档中导入资料，请稍后");
				try {
					commonBaseCodeAction.importDataFromMaterial(new Request(
							CommonVars.getCurrUser()), materielTypelist);
				} catch (Exception ee) {
					JOptionPane.showMessageDialog(null, ee.toString());
					CommonProgress.closeProgressDialog();
					return;
				}
				isinit = true;
			} finally {
				if (isinit) {
					pnCommonQueryPage.setInitState();
				}
				CommonProgress.closeProgressDialog();
			}
			JOptionPane.showMessageDialog(null, "资料导入成功！");
		}
	};

	/**
	 * This method initializes mtFile
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMtFile() {
		if (mtFile == null) {
			mtFile = new JMenuItem();
			mtFile.setText("来源于文件");
			mtFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgImportDataFromFile dgImportDataFromFile = new DgImportDataFromFile();
					dgImportDataFromFile.setVisible(true);
					if (dgImportDataFromFile.isOk() == false) {
						return;
					}
					if (dgImportDataFromFile.getMaterielType() == null) {
						return;
					}
					if (((ItemProperty) cbbMaterielType.getSelectedItem())
							.getCode().equals(
									dgImportDataFromFile.getMaterielType())) {
						pnCommonQueryPage.setInitState();
					} else {
						cbbMaterielType.setSelectedIndex(ItemProperty
								.getIndexByCode(
										dgImportDataFromFile.getMaterielType(),
										cbbMaterielType));
					}
				}
			});
		}
		return mtFile;
	}

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
			pmAfterTenInnerMerge.add(getMiAddFourInnerMerge());
			pmAfterTenInnerMerge.add(getMiEditTenInnerMerge());
			pmAfterTenInnerMerge.addSeparator();
			// pmAfterTenInnerMerge.add(getMiUndoTenInnerMerge());
			pmAfterTenInnerMerge.add(getMiUndoFourInnerMerge1());
			// pmAfterTenInnerMerge.addSeparator();
			// pmAfterTenInnerMerge.add(getMiResetMemoNo());
			// pmAfterTenInnerMerge.addSeparator();
			pmAfterTenInnerMerge.add(getMiCopyValue());
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
			miNewTenInnerMerge.setText("新增10码归并");
			miNewTenInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							tenInnerMerge();
						}
					});
		}
		return miNewTenInnerMerge;
	}

	/**
	 * 新增十码归并
	 */
	private void tenInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		int result = commonBaseCodeAction.checkDataForTenMerge(new Request(
				CommonVars.getCurrUser()), list);
		DgTenInnerMerge dgTenInnerMerge = null;
		switch (result) {
		case 1:
			dgTenInnerMerge = new DgTenInnerMerge();
			dgTenInnerMerge.setJTable((MultiSpanCellTable) jTable);
			dgTenInnerMerge.setCurrentRows(list);
			dgTenInnerMerge.setNew(true);
			dgTenInnerMerge.setVisible(true);
			refresh();
			break;
		/*
		 * case 0: dgTenInnerMerge = new DgTenInnerMerge();
		 * dgTenInnerMerge.setJTable((MultiSpanCellTable) jTable);
		 * dgTenInnerMerge.setCurrentRows(list); dgTenInnerMerge.setNew(true);
		 * dgTenInnerMerge.setVisible(true); refresh(); break;
		 */
		case -1:
			JOptionPane.showMessageDialog(FmInnerMerge.this, "编码不同的数据！", "警告",
					0);
			break;
		case -2:
			JOptionPane.showMessageDialog(FmInnerMerge.this, "申报计量单位不同！", "警告",
					0);
			break;
		}
	}

	/**
	 * 
	 */
	private void refreshTable() {
		// ((MultiSpanCellTable) jTable).combineRows(new int[] { 6, 1 },
		// new int[] { 6, 7, 8, 9, 10 });
		// ((MultiSpanCellTable) jTable).combineRows(11, new int[] { 11, 12, 13
		// });
		((MultiSpanCellTable) jTable).combineRows(new int[] { 15, 6 },
				new int[] { 13, 14, 15 });
		((MultiSpanCellTable) jTable).combineRows(6, new int[] { 6, 7, 8, 9,
				10, 11, 12 });
	}

	/**
	 * This method initializes miUndoTenInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	/*
	 * private JMenuItem getMiUndoTenInnerMerge() { if (miUndoTenInnerMerge ==
	 * null) { miUndoTenInnerMerge = new JMenuItem();
	 * miUndoTenInnerMerge.setText("撤消10码归并"); miUndoTenInnerMerge
	 * .addActionListener(new java.awt.event.ActionListener() { public void
	 * actionPerformed(java.awt.event.ActionEvent e) { unDoTenInnerMerge(); }
	 * }); } return miUndoTenInnerMerge; }
	 */

	/**
	 * This method initializes miAddFourInnerMerge
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
							addfourInnerMerge();
						}
					});
		}
		return miAddFourInnerMerge;
	}

	// 补充4码归并
	private void addfourInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		int result = commonBaseCodeAction.checkDataForFourInnerMerge(
				new Request(CommonVars.getCurrUser()), list);
		switch (result) {
		case -2:
			JOptionPane.showMessageDialog(FmInnerMerge.this,
					"选择的数据的10位商品编码的前4位不同！", "警告", 0);
			break;
		case -4:
			JOptionPane.showMessageDialog(FmInnerMerge.this, "全部已经全部归并！", "警告",
					0);
			break;
		}
		String materielTypeCode = ((ItemProperty) cbbMaterielType
				.getSelectedItem()).getCode();// 类型
		BillTemp bill = (BillTemp) BcusCommonQuery.getInstance()
				.findDistinctFourceInner(materielTypeCode);
		if (bill != null) {
			new addFourceInner(list, bill, materielTypeCode).start();
		}
		refresh();
	}

	class addFourceInner extends Thread {
		private List list = null;

		private BillTemp bill = null;

		private String materielType = null;

		public addFourceInner(List list, BillTemp bill, String materielType) {
			this.list = list;
			this.bill = bill;
			this.materielType = materielType;
		}

		List ls = list;

		public void run() {
			try {
				CommonProgress.showProgressDialog(FmInnerMerge.this);
				CommonProgress.setMessage("系统正在作四码补充归并,请稍候...");
				ls = commonBaseCodeAction.addfourceInner(
						new Request(CommonVars.getCurrUser()), materielType,
						bill, list);
				CommonProgress.setMessage("正在刷新数据表,请稍候...");
				refreshTable(ls);
				refresh();
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(null, "补充归并数据可能有错!!", "信息!!", 0);
			}
		}
	}

	/**
	 * 
	 */
	private void fourInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		int result = commonBaseCodeAction.checkDataForFourInnerMerge(
				new Request(CommonVars.getCurrUser()), list);
		DgFourInnerMerge dgFourInnerMerge = null;
		switch (result) {
		case 1:
			dgFourInnerMerge = new DgFourInnerMerge();
			dgFourInnerMerge.setJTable((MultiSpanCellTable) jTable);
			dgFourInnerMerge.setSelectedRows(list);
			dgFourInnerMerge.setNew(false);
			dgFourInnerMerge.setVisible(true);
			refresh();
			break;
		case 0:
			dgFourInnerMerge = new DgFourInnerMerge();
			dgFourInnerMerge.setJTable((MultiSpanCellTable) jTable);
			dgFourInnerMerge.setSelectedRows(list);
			dgFourInnerMerge.setNew(true);
			dgFourInnerMerge.setVisible(true);
			refresh();
			break;
		case -1:
			JOptionPane.showMessageDialog(FmInnerMerge.this, "有数据没有做过10位归并！",
					"警告", 0);
			break;
		case -2:
			JOptionPane.showMessageDialog(FmInnerMerge.this,
					"选择的数据的10位商品编码的前4位不同！", "警告", 0);
			break;
		case -3:
			JOptionPane.showMessageDialog(FmInnerMerge.this,
					"已经归并过的数据有不同编码序号！", "警告", 0);
			break;
		case -4:
			JOptionPane.showMessageDialog(FmInnerMerge.this, "全部已经全部归并！", "警告",
					0);
			break;
		}
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
							unDoFourInnerMerge();
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
			pmBeforeTenInnerMerge.add(getMiAddTenInnerMerge());
			pmBeforeTenInnerMerge.addSeparator();
			pmBeforeTenInnerMerge.add(getMiUndoTenInnerMerge1());
			pmBeforeTenInnerMerge.addSeparator();
			pmBeforeTenInnerMerge.add(getMiDeleteInnerMerge());
			pmBeforeTenInnerMerge.addSeparator();
			pmBeforeTenInnerMerge.add(getMiCopyValue());
			pmBeforeTenInnerMerge.add(tableModel.getMiCopy());
			pmBeforeTenInnerMerge.add(tableModel.getMiSearch());
			pmBeforeTenInnerMerge.add(tableModel.getMiSaveTableListToExcel());
			tableModel.getMiCopy().setEnabled(isCopyMenuEnabled);
		}
		return pmBeforeTenInnerMerge;
	}

	private JMenuItem getMiCopyValue() {
		if (miCopyValue == null) {
			miCopyValue = new JMenuItem();
			miCopyValue.setIcon(CommonVariables.getCopyIcon());
			// ("导出Excel Ctrl+S")
			miCopyValue.setText("  复制当前值(L)");
			miCopyValue.setMnemonic('L');
			miCopyValue.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
					ActionEvent.CTRL_MASK));
			miCopyValue.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					excelAdapter.copyCurrValues(xpoint, ypoint);
				}
			});
		}
		return miCopyValue;
	}

	/**
	 * This method initializes miUndoTenInnerMerge1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiUndoTenInnerMerge1() {
		if (miUndoTenInnerMerge1 == null) {
			miUndoTenInnerMerge1 = new JMenuItem();
			miUndoTenInnerMerge1.setText("撤消10码归并");
			miUndoTenInnerMerge1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							unDoTenInnerMerge();
						}

					});
		}
		return miUndoTenInnerMerge1;
	}

	/**
	 * This method initializes miUndoFourInnerMerge1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiUndoFourInnerMerge1() {
		if (miUndoFourInnerMerge1 == null) {
			miUndoFourInnerMerge1 = new JMenuItem();
			miUndoFourInnerMerge1.setText("撤消4码归并");
			miUndoFourInnerMerge1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// unDoFourInnerMerge1();
							unDoFourInnerMerge();
						}
					});
		}
		return miUndoFourInnerMerge1;
	}

	/**
	 * 
	 */
	private void unDoTenInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}

		List tempList = commonBaseCodeAction.findWhetherInnerMergeInEmsHeadH2k(
				new Request(CommonVars.getCurrUser()), list);
		if (tempList.size() > 0) {
			JOptionPane.showMessageDialog(this, "选中的行中有数据在电子帐册中备案,不能撤消!!",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		tempList = commonBaseCodeAction.findInnerMergeInEmsEdiMergeAfter(
				new Request(CommonVars.getCurrUser()), list);
		if (tempList.size() > 0) {
			JOptionPane.showMessageDialog(this, "选中的行中有数据在归并归系中备案,不能撤消!!",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int result = commonBaseCodeAction.checkDataEndTenInnerMerge(
				new Request(CommonVars.getCurrUser()), list);
		switch (result) {
		case 0:
			List ls = commonBaseCodeAction.unDoTenInnerMerge(new Request(
					CommonVars.getCurrUser()), list);
			refreshTable(ls);
			refresh();
			break;
		case -1:
			JOptionPane.showMessageDialog(FmInnerMerge.this,
					"请先撤消4码归并，再撤消10位归并！", "警告", 0);
			break;
		}
	}

	/**
	 * 撤消4码归并(在四码中)
	 */
	private void unDoFourInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		List tempList = commonBaseCodeAction.findInnerMergeDataInEmsEdiTr(
				new Request(CommonVars.getCurrUser()), list);
		if (tempList.size() > 0) {
			JOptionPane.showMessageDialog(this, "选中的行中有数据在经营范围中备案,不能撤消!!",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		List ls = commonBaseCodeAction.undoFourInnerMerge(new Request(
				CommonVars.getCurrUser()), list);
		refreshTable(ls);
		refresh();
	}

	private void refreshTable(List ls) {
		AttributiveCellTableModel tableModel = (AttributiveCellTableModel) jTable
				.getModel();
		tableModel.updateRows(ls);
		((MultiSpanCellTable) jTable).combineRows(new int[] { 15, 6 },
				new int[] { 13, 14, 15 });
		((MultiSpanCellTable) jTable).combineRows(6, new int[] { 6, 7, 8, 9,
				10, 11, 12 });
	}

	/**
	 * This method initializes miResetMemoNo 功能屏蔽 报关与工厂对照表 中由此功能
	 * 
	 * @return javax.swing.JMenuItem
	 */
	/*
	 * private JMenuItem getMiResetMemoNo() { if (miResetMemoNo == null) {
	 * miResetMemoNo = new JMenuItem(); miResetMemoNo.setText("重排10码行号");
	 * miResetMemoNo .addActionListener(new java.awt.event.ActionListener() {
	 * public void actionPerformed(java.awt.event.ActionEvent e) { List list =
	 * ((AttributiveCellTableModel) jTable .getModel()).getCurrentRows();
	 * DgInnerMergeResetNo dgTenInnerMergeResetNo = new DgInnerMergeResetNo();
	 * dgTenInnerMergeResetNo .setJTable((MultiSpanCellTable) jTable);
	 * dgTenInnerMergeResetNo.setCurrentRows(list);
	 * dgTenInnerMergeResetNo.setTenInnerMergeSort(true);
	 * dgTenInnerMergeResetNo.setVisible(true); if
	 * (dgTenInnerMergeResetNo.isOk() == true) {
	 * pnCommonQueryPage.refreshData(); } } }); } return miResetMemoNo; }
	 */

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
							List list = ((AttributiveCellTableModel) jTable
									.getModel()).getCurrentRows();
							DgInnerMergeResetNo dgTenInnerMergeResetNo = new DgInnerMergeResetNo();
							dgTenInnerMergeResetNo
									.setJTable((MultiSpanCellTable) jTable);
							dgTenInnerMergeResetNo.setCurrentRows(list);
							dgTenInnerMergeResetNo.setTenInnerMergeSort(false);
							dgTenInnerMergeResetNo.setVisible(true);
							if (dgTenInnerMergeResetNo.isOk() == true) {
								pnCommonQueryPage.refreshData();
							}
						}
					});
		}
		return miFourResetMemoNo;
	}

	private boolean isNewTenInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			if (data.getHsAfterComplex() != null) {
				return false;
			}
		}
		return true;
	}

	private boolean isNewFourInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			if (data.getHsFourCode() != null
					&& !data.getHsFourCode().equals("")) {
				return false;
			}
		}
		return true;
	}

	private boolean isEditTenInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list == null || list.size() <= 0) {
			return false;
		}
		InnerMergeData data = (InnerMergeData) list.get(0);
		if (data.getHsAfterTenMemoNo() != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method initializes miAddTenInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiAddTenInnerMerge() {
		if (miAddTenInnerMerge == null) {
			miAddTenInnerMerge = new JMenuItem();
			miAddTenInnerMerge.setText("补充10码归并");
			miAddTenInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							addtenInnerMerge();
						}
					});
		}
		return miAddTenInnerMerge;
	}

	// 补充十码归并
	private void addtenInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		int result = commonBaseCodeAction.checkDataForTenMerge(new Request(
				CommonVars.getCurrUser()), list);
		switch (result) {
		case -1:
			JOptionPane.showMessageDialog(FmInnerMerge.this, "编码不同的数据！", "警告",
					0);
			return;
		case -2:
			JOptionPane.showMessageDialog(FmInnerMerge.this, "申报计量单位不同！", "警告",
					0);
			return;
		}
		String materielTypeCode = ((ItemProperty) cbbMaterielType
				.getSelectedItem()).getCode();
		BillTemp bill = (BillTemp) BcusCommonQuery.getInstance()
				.findDistinctTenInner(materielTypeCode);
		if (bill != null) {
			String seqNum = bill.getBill1();
			new addTenInner(list, seqNum, materielTypeCode).start();
		}
		refresh();
	}

	class addTenInner extends Thread {
		private List list = null;

		private String seqNum = null;

		private String materielType = null;

		public addTenInner(List list, String seqNum, String materielType) {
			this.list = list;
			this.seqNum = seqNum;
			this.materielType = materielType;
		}

		List ls = list;

		public void run() {
			try {
				CommonProgress.showProgressDialog(FmInnerMerge.this);
				CommonProgress.setMessage("系统正在作补充归并,请稍候...");
				ls = commonBaseCodeAction.addtenInner(
						new Request(CommonVars.getCurrUser()), materielType,
						seqNum, list);
				CommonProgress.setMessage("正在刷新数据表,请稍候...");
				refreshTable(ls);
				refresh();
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(null, "补充归并数据可能有错!!", "信息!!", 0);
			}
		}
	}

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
							fourInnerMerge();
						}
					});
		}
		return miNewFourInnerMerge;
	}

	class AutoInnerMergeDataThread extends Thread {
		public void run() {
			try {
				TempAutoInnerMergeParam param = null;
				DgAutoInnerMergeParam dgParam = new DgAutoInnerMergeParam();
				dgParam.setVisible(true);
				if (dgParam.isOK()) {
					param = dgParam.getAutoInnerMergeParam();
					if (param == null) {
						return;
					}
				} else {
					return;
				}
				FmInnerMerge.this.miAutoInnerMerge.setEnabled(false);
				CommonProgress.showProgressDialog(FmInnerMerge.this);
				CommonProgress.setMessage("正在自动归并数据,请稍候...");
				try {
					commonBaseCodeAction.autoInnerMergeData(new Request(
							CommonVars.getCurrUser()), param);
				} catch (Exception e) {
					e.printStackTrace();
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(null, "自动归并数据可能有错!!", "信息!!",
							0);
				}
				CommonProgress.setMessage("正在刷新数据表,请稍候...");
				pnCommonQueryPage.refreshData();
			} finally {
				CommonProgress.closeProgressDialog();
				FmInnerMerge.this.miAutoInnerMerge.setEnabled(true);
			}
		}
	}

	class CustomInnerMergeDataThread extends Thread {
		public void run() {
			try {
				FmInnerMerge.this.miCustomInnerMerge.setEnabled(false);
				CommonProgress.showProgressDialog(FmInnerMerge.this);
				CommonProgress.setMessage("正在刷新数据表,请稍候...");
				pnCommonQueryPage.refreshData();
			} finally {
				CommonProgress.closeProgressDialog();
				FmInnerMerge.this.miCustomInnerMerge.setEnabled(true);
			}
		}
	}

	/**
	 * This method initializes miDeleteInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiDeleteInnerMerge() {
		if (miDeleteInnerMerge == null) {
			miDeleteInnerMerge = new JMenuItem();
			miDeleteInnerMerge.setText("删除(D)");
			miDeleteInnerMerge.setMnemonic('D');
			miDeleteInnerMerge.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_D, ActionEvent.CTRL_MASK));
			miDeleteInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							deleteInnerMerge();
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
			btnPrint.setPreferredSize(new Dimension(60, 30));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbMaterielType.getSelectedIndex() < 0) {
						return;
					}
					Request request = new Request(CommonVars.getCurrUser());
					String materielTypeCode = ((ItemProperty) cbbMaterielType
							.getSelectedItem()).getCode();
					String materielTypeName = ((ItemProperty) cbbMaterielType
							.getSelectedItem()).getName();
					List list = commonBaseCodeAction.findInnerMergeReportData(
							request, materielTypeCode);
					CustomReportDataSource ds = new CustomReportDataSource(list);
					try {
						InputStream masterReportStream = FmInnerMerge.class
								.getResourceAsStream("report/InnerMergeReport.jasper");
						Map parameters = new HashMap();
						String companyCode = ((Company) CommonVars
								.getCurrUser().getCompany()).getCode();
						String companyName = ((Company) CommonVars
								.getCurrUser().getCompany()).getName();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(new Date());
						String printDate = String.valueOf(calendar
								.get(Calendar.YEAR))
								+ "年"
								+ String.valueOf(calendar.get(Calendar.MONTH) + 1)
								+ "月"
								+ String.valueOf(calendar
										.get(Calendar.DAY_OF_MONTH)) + "日";
						parameters.put("companyName", companyName);
						parameters.put("companyCode", companyCode);
						parameters.put("printDate", printDate);
						parameters.put("materielType", materielTypeName);
						parameters.put("rowCount", Integer.valueOf(list.size()));
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
		int count = jTable.getColumnModel().getColumnCount();
		for (int i = 1; i < count; i++) {
			jTable.getColumnModel().getColumn(i)
					.setCellRenderer(new ToolTipRenderer());
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

	class ReverseInnerMergeDataThread extends Thread {
		public void run() {
			try {
				FmInnerMerge.this.miReverseMerge.setEnabled(false);
				CommonProgress.showProgressDialog(FmInnerMerge.this);
				CommonProgress.setMessage("正在刷新数据表,请稍候...");
				pnCommonQueryPage.setInitState();
				// }
			} finally {
				CommonProgress.closeProgressDialog();
				FmInnerMerge.this.miReverseMerge.setEnabled(true);
			}
		}
	}

	public void initTableInfo() {
		hmTableInfo = new HashMap();
		int defaultRowHeight = jTable.getRowHeight();
		int defaultFontSize = jTable.getFont().getSize();

		TableColumnModel columnModel = jTable.getTableHeader().getColumnModel();
		int columnCount = columnModel.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			hmTableInfo.put(new Integer(i), new Integer(columnModel
					.getColumn(i).getPreferredWidth() * 2));
		}
		hmTableInfo.put("MaxRowHeight", new Integer(defaultRowHeight * 2));
		hmTableInfo.put("MaxFontSize", new Integer(defaultFontSize * 2));
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmInnerMerge() {
		if (pmInnerMerge == null) {
			pmInnerMerge = new JPopupMenu();
			pmInnerMerge.add(getMiAutoInnerMerge());
			pmInnerMerge.add(getMiCustomInnerMerge());
			pmInnerMerge.add(getMiReverseMerge());
		}
		return pmInnerMerge;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInnerMerge() {
		if (btnInnerMerge == null) {
			btnInnerMerge = new JButton();
			btnInnerMerge.setText("归并");
			btnInnerMerge.setPreferredSize(new Dimension(60, 30));
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
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiAutoInnerMerge() {
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
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCustomInnerMerge() {
		if (miCustomInnerMerge == null) {
			miCustomInnerMerge = new JMenuItem();
			miCustomInnerMerge.setText("自定义归并");
			miCustomInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgCustomInnerMerge dgCustomInnerMerge = new DgCustomInnerMerge();
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
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiReverseMerge() {
		if (miReverseMerge == null) {
			miReverseMerge = new JMenuItem();
			miReverseMerge.setText("反向归并");
			miReverseMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgReverseMerge dgReverseMerge = new DgReverseMerge();
							dgReverseMerge
									.setMaterielType(((ItemProperty) cbbMaterielType
											.getSelectedItem()).getCode());
							dgReverseMerge.setVisible(true);
							new ReverseInnerMergeDataThread().start();
						}
					});
		}
		return miReverseMerge;
	}

	/**
	 * This method initializes miEditTenInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiEditTenInnerMerge() {
		if (miEditTenInnerMerge == null) {
			miEditTenInnerMerge = new JMenuItem();
			miEditTenInnerMerge.setText("修改十码归并");
			miEditTenInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							editTenInnerMerge();
						}
					});
		}
		return miEditTenInnerMerge;
	}

	/**
	 * 编辑十码内部归并
	 */
	private void editTenInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		List<InnerMergeData> listEditData = new ArrayList<InnerMergeData>();
		int memoNo = -1;
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			if (i == 0) { // 默认编辑第一行的数据
				memoNo = data.getHsAfterTenMemoNo();
				listEditData.add(data);
				continue;
			}
			if (data.getHsAfterTenMemoNo() != null
					&& data.getHsAfterTenMemoNo() == memoNo) {
				listEditData.add(data);
			}
		}
		List<InnerMergeData> listCheckData = new ArrayList<InnerMergeData>();
		listCheckData.add(listEditData.get(0));
		// 修改十码
		// 检测所修改资料的数据是否做了归并前备案（因为做了从内部归并更新到备案资料故放开限制）
		// 2010-09-25 hcl add
		if (commonBaseCodeAction.findInnerMergeInEmsEdiMergeOfUsed(
				new Request(CommonVars.getCurrUser()), listCheckData).size() > 0) {
			JOptionPane.showMessageDialog(this, "归并后数据已经有做报关单，不能修改!!", "警告!!",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgEditTenInnerMerge dg = new DgEditTenInnerMerge();
		dg.setTableModel(this.tableModel);
		dg.setEditListData(listEditData);
		dg.setVisible(true);
		if (dg.isOk()) {
			this.refreshTable();
		}
	}

	/**
	 * 撤消4码归并(在十码中)
	 */
	private void unDoFourInnerMerge1() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		List tempList = commonBaseCodeAction.findInnerMergeDataInEmsEdiTr(
				new Request(CommonVars.getCurrUser()), list);
		if (tempList.size() > 0) {
			//
			// 判断四码是否有其它十码对应
			//
			boolean isHas = commonBaseCodeAction.findInnerMergeDataByFilter(
					new Request(CommonVars.getCurrUser()), list);
			if (isHas) {
				//
				// 检测所撤消四码归并的数据是否做了归并前备案
				//
				if (commonBaseCodeAction.findInnerMergeInEmsEdiMergeBefore(
						new Request(CommonVars.getCurrUser()), list).size() > 0) {
					JOptionPane.showMessageDialog(null,
							"选中的四码已经备案,而且选中的十码归并数据在归并关系被引用!!\n不能进行撤消四码归并!!",
							"提示!!", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			} else {
				JOptionPane.showMessageDialog(this, "选中的行中有数据在经营范围中备案,不能撤消!!",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		List ls = commonBaseCodeAction.undoFourInnerMerge(new Request(
				CommonVars.getCurrUser()), list);
		refreshTable(ls);
		refresh();
	}

	/**
	 * 删除内部归并数据
	 * 
	 */
	private void deleteInnerMerge() {
		try {
			if (JOptionPane.showConfirmDialog(FmInnerMerge.this,
					"是否确定删除选定数据!!!", "提示", JOptionPane.OK_CANCEL_OPTION) != 0) {
				return;
			}
			List list = ((AttributiveCellTableModel) jTable.getModel())
					.getCurrentRows();
			if (list.size() < 1) {
				return;
			}
			List tempList = commonBaseCodeAction.findInnerMergeDataInEmsEdiTr(
					new Request(CommonVars.getCurrUser()), list);
			if (tempList.size() > 0) {
				//
				// 判断四码是否有其它十码对应
				//
				boolean isHas = commonBaseCodeAction
						.findInnerMergeDataByFilter(
								new Request(CommonVars.getCurrUser()), list);
				if (isHas) {
					//
					// 检测所撤消四码归并的数据是否做了归并前备案
					//
					if (commonBaseCodeAction.findInnerMergeInEmsEdiMergeBefore(
							new Request(CommonVars.getCurrUser()), list).size() > 0) {
						JOptionPane.showMessageDialog(null,
								"内部归并前有数据已做了归并关系的备案,不能删除!!!!", "警告!!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} else {
					JOptionPane.showMessageDialog(this,
							"选中的行中有数据在经营范围中备案,不能删除!!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
			commonBaseCodeAction.deleteInnerMergeData(
					new Request(CommonVars.getCurrUser()), list);
			this.tableModel.deleteRows(list);
			this.refreshTable();
			// selectItem(((ItemProperty) jComboBox
			// .getSelectedItem()).getCode(), null);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "删除数据有错!!", "警告!!", 0);
		}
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddMateriel() {
		if (btnAddMateriel == null) {
			btnAddMateriel = new JButton();
			btnAddMateriel.setText("新增物料");
			btnAddMateriel.setPreferredSize(new Dimension(60, 30));
			btnAddMateriel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbMaterielType.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmInnerMerge.this, "请选择物料类别", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							ItemProperty item = (ItemProperty) cbbMaterielType
									.getSelectedItem();
							String materielType = item.getCode().trim();// 类型
							List list = (List) CommonQueryPage.getInstance()
									.getMaterielNotInInnerMerge(materielType);
							if (list == null || list.size() <= 0) {
								return;
							}
							List innerMergeDataList = commonBaseCodeAction
									.importInnerMergeDataFromMateriel(
											new Request(CommonVars
													.getCurrUser()), list,
											materielType);
							dataSource.addAll(innerMergeDataList);
							initTable(dataSource);
						}
					});
		}
		return btnAddMateriel;
	}

	public void addMouseListener(JTable table) {
		tableModel.addMouseListener(table);
	}

	public void removeMouseListener(JTable table) {
		MouseListener[] mouseListeners = table.getTableHeader()
				.getMouseListeners();
		for (int i = 0; i < mouseListeners.length; i++) {
			if (mouseListeners[i] instanceof SortMouseListener) {
				table.getTableHeader().removeMouseListener(mouseListeners[i]);
			}
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
			btnExit.setText("关闭");
			btnExit.setPreferredSize(new Dimension(60, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
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
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
		}
		return jPanel1;
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
			jToolBar1.add(getPnCommonQueryPage());
		}
		return jToolBar1;
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
			Object value, boolean isLike, boolean isShowNoInner) {
		String materielType = ((ItemProperty) cbbMaterielType.getSelectedItem())
				.getCode();
		if (materielType == null) {
			return new ArrayList();
		}
		if (property == null && isfirstOpen && CommonVars.isCompany("康舒电子")) {
			property = "importTimer";
			isLike = false;
			value = commonBaseCodeAction.findLastInnestDateByInnerMergeData(
					new Request(CommonVars.getCurrUser()), materielType);
		}
		dataSource = commonBaseCodeAction.findInnerMergeDataByType(new Request(
				CommonVars.getCurrUser()), materielType, index, length,
				property, value, isLike, isNoten);
		return dataSource;
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
	public List getDataSourceCount(int index, int length, String property,
			Object value, boolean isLike, boolean isShowNoInner) {
		String materielType = ((ItemProperty) cbbMaterielType.getSelectedItem())
				.getCode();
		if (materielType == null) {
			return new ArrayList();
		}
		if (property == null && isfirstOpen && CommonVars.isCompany("康舒电子")) {
			property = "importTimer";
			isLike = false;
			value = commonBaseCodeAction.findLastInnestDateByInnerMergeData(
					new Request(CommonVars.getCurrUser()), materielType);
		}
		dataSource = commonBaseCodeAction.findInnerMergeDataByTypeCount(
				new Request(CommonVars.getCurrUser()), materielType, index, 0,
				property, value, isLike, isNoten);
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
					return FmInnerMerge.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return FmInnerMerge.this.getDataSource(index, length,
							property, value, isLike, false);
				}

				@Override
				public Long getDataSourceCount(int index, int length,
						String property, Object value, boolean isLike) {
					return (long) FmInnerMerge.this.getDataSourceCount(index,
							length, property, value, isLike, false).size();
				}

			};
			// pnCommonQueryPage.setLength(1000);
		}
		return pnCommonQueryPage;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("修改物料");
			jButton.setPreferredSize(new Dimension(60, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					edit();
				}
			});
		}
		return jButton;
	}

	private void edit() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(null, "请选择要修改的物料!", "提示!", 2);
			return;
		}
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (commonBaseCodeAction.findInnerMergeInEmsEdiMergeBeforeOfUsed(
				new Request(CommonVars.getCurrUser()), list).size() > 0) {
			JOptionPane.showMessageDialog(this, "归并前数据已经有做报关清单，不能修改!!", "警告!!",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgMaterielInner dg = new DgMaterielInner();
		dg.setAdd(false);
		dg.setTableModel(tableModel);
		dg.setVisible(true);
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("显示未归并");
			jButton1.setPreferredSize(new Dimension(75, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isNoten = true;
					isfirstOpen = false;
					pnCommonQueryPage.setInitState();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("删除未归并");
			jButton2.setPreferredSize(new Dimension(75, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmInnerMerge.this,
							"确定要删除未归并资料！", "确认", 0) == 0) {
						new deleteNoten().start();
					}
				}
			});
		}
		return jButton2;
	}

	class deleteNoten extends Thread {

		public void run() {
			try {
				CommonProgress.showProgressDialog(FmInnerMerge.this);
				CommonProgress.setMessage("系统正删除未归并数据，请稍后...");
				String materielType = ((ItemProperty) cbbMaterielType
						.getSelectedItem()).getCode();
				commonBaseCodeAction.deleteInnerNoten(
						new Request(CommonVars.getCurrUser()), materielType);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmInnerMerge.this,
						"删除数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				pnCommonQueryPage.setInitState();
			}
		}
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("显示所有");
			jButton3.setPreferredSize(new Dimension(60, 30));
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isNoten = false;
					isfirstOpen = false;
					pnCommonQueryPage.setInitState();
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(175, 6, 77, 26);
			jCheckBox.setText("合并显示");
			jCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					refresh();
				}
			});
		}
		return jCheckBox;
	}

	private void refresh() {
		if (jCheckBox.isSelected()) {
			((MultiSpanCellTable) jTable).combineRows(new int[] { 13, 6 },
					new int[] { 13, 14, 15 });
			((MultiSpanCellTable) jTable).combineRows(6, new int[] { 6, 7, 8,
					9, 10, 11, 12, 13, 14, 15 });
			removeMouseListener(jTable);
		} else {
			((MultiSpanCellTable) jTable).splitRows(6);
			addMouseListener(jTable);
		}

	}
} // @jve:decl-index=0:visual-constraint="10,10"
