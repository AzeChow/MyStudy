/*
 * Created on 2004-7-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.client.blsinnermerge;

import java.awt.BorderLayout;
import java.awt.Component;
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

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.Item;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bls.action.BlsInnerMergeAction;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.CommonTableContextPopupEvent;
import com.bestway.client.util.DataType;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.SerialColumn;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Rectangle;

/**
 * @author bsway
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates checked by 陈井彬
 *         2008.11.7 物料与报关对应表
 */
public class FmBlsInnerMerge extends JInternalFrameBase {

	private AttributiveCellTableModel tableModel = null;

	/**
	 * 表格数据源
	 */
	private List dataSource = new ArrayList();

	private JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel = null;

	private JButton btnRenovate = null;

	private JButton btnImportData = null;

	private JPopupMenu pmImportData = null;

	private JMenuItem mtFromMateriel = null;

	private JPopupMenu pmAfterTenInnerMerge = null;

	private JMenuItem miNewTenInnerMerge = null;

	private JMenuItem miUndoTenInnerMerge = null;

	private JPopupMenu pmBeforeTenInnerMerge = null;

	private JMenuItem miUndoTenInnerMerge1 = null;

	// private JMenuItem miResetMemoNo = null;

	private JMenuItem miAddTenInnerMerge = null;

	private JMenuItem miDeleteInnerMerge = null;

	private JButton btnPrint = null;

	private JPopupMenu pmTableContextPopupMenu = null;

	private boolean isCopyMenuEnabled = false;

	private JPopupMenu pmInnerMerge = null;

	private JButton btnInnerMerge = null;

	private JMenuItem miAutoInnerMerge = null;

	private int index = -100;

	private int count = 0;

	private JButton btnExit = null;

	private JButton btnAddMateriel = null;

	private BlsInnerMergeAction blsInnerMergeAction = null;

	private JMenuItem miEditBlsTenData = null;

	private JCheckBox cbCombin = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar1 = null;

	private PnCommonQueryPage pnCommonQueryPage = null;

	private JMenuItem miAddFromBlsTenInnerMerge = null;

	private JMenuItem miFromFile = null;

	public FmBlsInnerMerge() {
		super();
		blsInnerMergeAction = (BlsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("blsInnerMergeAction");
		initialize();
		jTable = new MultiSpanCellTable();
		jTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				if (e.getModifiers() != InputEvent.BUTTON3_MASK) {
					return;
				}
				int[] columns = jTable.getSelectedColumns();
				int[] rows = jTable.getSelectedRows();
				int selectStartPointX = 0;
				int selectEndPointX = 0;

				//
				// 是否有上下文菜单
				//
				boolean hasTableContextPopupMenu = true;

				if (columns.length > 0 && rows.length > 0) {

					for (int i = 0; i < columns[0]; i++) {
						selectStartPointX += jTable.getColumnModel().getColumn(
								i).getWidth()
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
							if (columns[0] >= 0 && columns[0] <= 5) {
								if (isNewTenInnerMerge()) {
									getMiNewTenInnerMerge().setEnabled(true);
									getMiAddTenInnerMerge().setEnabled(false);
									getMiAddFromBlsTenInnerMerge().setEnabled(
											true);
								} else {
									getMiNewTenInnerMerge().setEnabled(false);
									getMiAddTenInnerMerge().setEnabled(true);
									getMiAddFromBlsTenInnerMerge().setEnabled(
											false);
								}
								getPmBeforeTenInnerMerge().show(jTable,
										e.getPoint().x, e.getPoint().y);
							} else if (columns[0] >= 6 && columns[0] <= 12) {
								if (!isEditTenInnerMerge()) {
									getMiUndoTenInnerMerge().setEnabled(false);
									getMiEditBlsTenData().setEnabled(false);
									// getMiResetMemoNo().setEnabled(false);
								} else {
									getMiUndoTenInnerMerge().setEnabled(true);
									getMiEditBlsTenData().setEnabled(true);
									// getMiResetMemoNo().setEnabled(true);
								}
								getPmAfterTenInnerMerge().show(jTable,
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
						int[] columns = jTable.getSelectedColumns();
						int[] rows = jTable.getSelectedRows();
						if (columns.length < 1 || rows.length < 1) {
							return;
						}
						if (columns[0] >= 0 && columns[0] <= 5) {
							jTable.setColumnSelectionInterval(1, 5);
							return;
						} else if (columns[0] >= 6 && columns[0] <= 12) {
							jTable.setColumnSelectionInterval(6, 12);
							return;
						}
					}
				});
		jScrollPane.setViewportView(jTable);
		this.initTable(pnCommonQueryPage.dataSource);
		initCbbQueryFields();

	}

	public void initCbbQueryFields() {
		if (pnCommonQueryPage.getCbbQueryField().getItemCount() > 0) {
			pnCommonQueryPage.cbbQueryField.setSelectedItem(null);
			return;
		}
		pnCommonQueryPage.getCbbQueryField().removeAllItems();
		if (tableModel != null) {
			for (int i = 0; i < tableModel.getColumnCount(); i++) {
				JTableListColumn c = tableModel.getColumns().get(i);
				if (c instanceof SerialColumn) {
					continue;
				}
				if (c.isShowSearch() == false) {
					continue;
				}
				pnCommonQueryPage.getCbbQueryField().addItem(
						new Item(c.getCaption(),
								c.getCustomProperty() == null ? c.getProperty()
										: c.getCustomProperty(),
								getDataTypeByColumn(c.getProperty())));
			}
			pnCommonQueryPage.cbbQueryField.setSelectedItem(null);
		}
	}

	public int getDataTypeByColumn(String sProp) {
		List list = tableModel.getList();
		if (list == null || list.size() <= 0) {
			return DataType.NULL;
		}
		int dataType = DataType.NULL;
		try {
			if (list.get(0) == null) {
				return dataType;
			}
			Class cls = CommonVariables.getTypeByField(list.get(0).getClass(),
					sProp);
			if (cls.equals(Integer.class) || cls.equals(Long.class)
					|| cls.equals(Short.class)) {
				dataType = DataType.INTEGER;
			} else if (cls.equals(Double.class) || cls.equals(Float.class)) {
				dataType = DataType.DOUBLE;
			} else if (cls.equals(String.class)) {
				dataType = DataType.STRING;
			} else if (cls.equals(Boolean.class)) {
				dataType = DataType.BOOLEAN;
			} else if (cls.equals(Date.class) || cls.equals(Calendar.class)) {
				dataType = DataType.DATE;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dataType;
	}

	/**
	 * 获得上下文菜单
	 * 
	 * @return
	 */
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
		this.setTitle("物料与报关对应");
		this.setContentPane(getJContentPane());
		this.getPmAfterTenInnerMerge();
		this.setSize(687, 376);
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
			jToolBar = new JToolBar();
			jToolBar.add(getBtnImportData());
			jToolBar.add(getBtnInnerMerge());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnAddMateriel());
			jToolBar.add(getBtnRenovate());
			jToolBar.add(getBtnExit());
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
			jPanel.setPreferredSize(new java.awt.Dimension(182, 30));
			jPanel.setSize(new java.awt.Dimension(182, 30));
			jPanel.add(getCbCombin(), null);
		}
		return jPanel;
	}

	private JTableListModel initTable(List dataSource) {
		// if (tableModel == null) {
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) jTable,
				dataSource, new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料号", "materiel.ptNo", 100));
						list
								.add(addColumn("商品编码", "materiel.complex.code",
										100));
						list
								.add(addColumn("工厂名称", "materiel.factoryName",
										100));
						list.add(addColumn("规格", "materiel.factorySpec", 100));
						list
								.add(addColumn("企业单位", "materiel.calUnit.name",
										80));
						// list.add(addColumn("单价", "materiel.ptPrice", 80));

						list.add(addColumn("归并序号", "blsTenInnerMerge.seqNum",
								100, Integer.class));
						list
								.add(addColumn("报关名称", "blsTenInnerMerge.name",
										100));
						list
								.add(addColumn("商品规格", "blsTenInnerMerge.spec",
										100));
						list.add(addColumn("十位商品编码",
								"blsTenInnerMerge.complex.code", 100));
						list.add(addColumn("报关单位",
								"blsTenInnerMerge.comUnit.name", 50));
						list
								.add(addColumn(
										"第一法定单位",
										"blsTenInnerMerge.complex.firstUnit.name",
										100));
						// list.add(addColumn("第一法定数量",
						// "blsTenInnerMerge.legalAmount", 100));
						list
								.add(addColumn(
										"第二法定单位",
										"blsTenInnerMerge.complex.secondUnit.name",
										100));
						// list.add(addColumn("第二法定数量",
						// "blsTenInnerMerge.secondLegalAmount", 100));
						// list.add(addColumn("单价",
						// "blsTenInnerMerge.price",
						// 50));
						// list.add(addColumn("币制",
						// "blsTenInnerMerge.curr.name",
						// 50));
						// list.add(addColumn("单位重量",
						// "blsTenInnerMerge.unitWeight", 50));
						// list.add(addColumn("产销国",
						// "blsTenInnerMerge.country.name", 100));

						return list;
					}
				});

		TableColumnModel cm = jTable.getColumnModel();

		// this.tableModel.getCellAttribute()
		ColumnGroup gBefore = new ColumnGroup("工厂资料");
		gBefore.add(cm.getColumn(1));
		gBefore.add(cm.getColumn(2));
		gBefore.add(cm.getColumn(3));
		gBefore.add(cm.getColumn(4));
		gBefore.add(cm.getColumn(5));
		// gBefore.add(cm.getColumn(6));
		ColumnGroup gAfter = new ColumnGroup("报关资料");
		gAfter.add(cm.getColumn(6));
		gAfter.add(cm.getColumn(7));
		gAfter.add(cm.getColumn(8));
		gAfter.add(cm.getColumn(9));
		gAfter.add(cm.getColumn(10));
		gAfter.add(cm.getColumn(11));
		gAfter.add(cm.getColumn(12));
		// gAfter.add(cm.getColumn(13));
		// gAfter.add(cm.getColumn(14));
		// gAfter.add(cm.getColumn(15));
		// gAfter.add(cm.getColumn(16));
		// gAfter.add(cm.getColumn(17));
		// gAfter.add(cm.getColumn(18));

		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gBefore);
		header.addColumnGroup(gAfter);
		this.setTableCellToopTip();
		refresh();
		this.deleteCommonTableContextPopupEventMouseListener(jTable);
		// } else {
		// tableModel.setList(dataSource);
		// refresh();
		// }
		return tableModel;
	}

	private void refresh() {
		if (cbCombin.isSelected()) {
			((MultiSpanCellTable) jTable).combineRows(6, new int[] { 6, 7, 8,
					9, 10, 11, 12 });// , 14, 15, 16, 17, 18
		} else {
			((MultiSpanCellTable) jTable).splitRows(6);
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
	 * This method initializes btnImportData
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImportData() {
		if (btnImportData == null) {
			btnImportData = new JButton();
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
	 * This method initializes pmImportData
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmImportData() {
		if (pmImportData == null) {
			pmImportData = new JPopupMenu();
			pmImportData.add(getMtFromMateriel());
			pmImportData.add(getMiFromFile());
		}
		return pmImportData;
	}

	/**
	 * This method initializes mtFromMateriel
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMtFromMateriel() {
		if (mtFromMateriel == null) {
			mtFromMateriel = new JMenuItem();
			mtFromMateriel.setText("从报关常用物料");
			mtFromMateriel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								// ItemProperty item = (ItemProperty)
								// cbbMaterielType
								// .getSelectedItem();
								// if (item != null) {
								ImportMaterielDataRunnable thread = new ImportMaterielDataRunnable();
								thread.start();
								// } else {
								// JOptionPane.showMessageDialog(
								// FmBlsInnerMerge.this,
								// "请选择要导入的物料类别！", "提示",
								// JOptionPane.INFORMATION_MESSAGE);
								// }
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
		}
		return mtFromMateriel;
	}

	class ImportMaterielDataRunnable extends Thread {
		// private String materielTypelist = null;

		public ImportMaterielDataRunnable() {
			// this.materielTypelist = materielTypelist;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在从物料主档中导入资料，请稍后");
				try {
					blsInnerMergeAction
							.importInnerMergeDataFromMateriel(new Request(
									CommonVars.getCurrUser()));
				} catch (Exception ee) {
					JOptionPane.showMessageDialog(null, ee.toString());
					CommonProgress.closeProgressDialog();
					return;
				}
				pnCommonQueryPage.setInitState();
				// pnCommonQueryPage.refreshData();
			} finally {
				CommonProgress.closeProgressDialog();
			}
			JOptionPane.showMessageDialog(null, "资料导入成功！");
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
			pmAfterTenInnerMerge.add(getMiUndoTenInnerMerge());
			pmAfterTenInnerMerge.add(getMiEditBlsTenData());
			pmAfterTenInnerMerge.addSeparator();
			// pmAfterTenInnerMerge.add(getMiResetMemoNo());
			// pmAfterTenInnerMerge.addSeparator();
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
							tenInnerMerge(false);
						}
					});
		}
		return miNewTenInnerMerge;
	}

	/**
	 * This method initializes miUndoTenInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiUndoTenInnerMerge() {
		if (miUndoTenInnerMerge == null) {
			miUndoTenInnerMerge = new JMenuItem();
			miUndoTenInnerMerge.setText("撤消物料与报关对应");
			miUndoTenInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							unDoTenInnerMerge();
						}
					});
		}
		return miUndoTenInnerMerge;
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
			pmBeforeTenInnerMerge.add(getMiAddFromBlsTenInnerMerge());
			pmBeforeTenInnerMerge.addSeparator();
			pmBeforeTenInnerMerge.add(getMiUndoTenInnerMerge1());
			pmBeforeTenInnerMerge.addSeparator();
			pmBeforeTenInnerMerge.add(getMiDeleteInnerMerge());
			pmBeforeTenInnerMerge.addSeparator();
			pmBeforeTenInnerMerge.add(tableModel.getMiCopy());
			pmBeforeTenInnerMerge.add(tableModel.getMiSearch());
			pmBeforeTenInnerMerge.add(tableModel.getMiSaveTableListToExcel());
			tableModel.getMiCopy().setEnabled(isCopyMenuEnabled);
		}
		return pmBeforeTenInnerMerge;
	}

	/**
	 * This method initializes miAddFromBlsTenInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiAddFromBlsTenInnerMerge() {
		if (miAddFromBlsTenInnerMerge == null) {
			miAddFromBlsTenInnerMerge = new JMenuItem();
			miAddFromBlsTenInnerMerge.setText("新增物料与报关对应来自报关商品资料");
			miAddFromBlsTenInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							tenInnerMerge(true);
						}

					});
		}
		return miAddFromBlsTenInnerMerge;
	}

	/**
	 * This method initializes miUndoTenInnerMerge1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiUndoTenInnerMerge1() {
		if (miUndoTenInnerMerge1 == null) {
			miUndoTenInnerMerge1 = new JMenuItem();
			miUndoTenInnerMerge1.setText("撤消物料与报关对应");
			miUndoTenInnerMerge1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							unDoTenInnerMerge();
						}

					});
		}
		return miUndoTenInnerMerge1;
	}

	// /**
	// * This method initializes miResetMemoNo
	// *
	// * @return javax.swing.JMenuItem
	// */
	// private JMenuItem getMiResetMemoNo() {
	// if (miResetMemoNo == null) {
	// miResetMemoNo = new JMenuItem();
	// miResetMemoNo.setText("重排10码行号");
	// miResetMemoNo
	// .addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// tenCodeResetNo();
	// }
	// });
	// }
	// return miResetMemoNo;
	// }

	private boolean isNewTenInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		for (int i = 0; i < list.size(); i++) {
			BlsInnerMerge data = (BlsInnerMerge) list.get(i);
			if (data.getBlsTenInnerMerge() != null) {
				return false;
			}
		}
		return true;
	}

	private boolean isEditTenInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		for (int i = 0; i < list.size(); i++) {
			BlsInnerMerge data = (BlsInnerMerge) list.get(i);
			if (data.getBlsTenInnerMerge() == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method initializes miAddTenInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiAddTenInnerMerge() {
		if (miAddTenInnerMerge == null) {
			miAddTenInnerMerge = new JMenuItem();
			miAddTenInnerMerge.setText("补充物料与报关对应");
			miAddTenInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							tenInnerMerge(null);
						}
					});
		}
		return miAddTenInnerMerge;
	}

	class AutoInnerMergeDataThread extends Thread {
		public void run() {
			try {
				FmBlsInnerMerge.this.miAutoInnerMerge.setEnabled(false);
				// ItemProperty item = (ItemProperty) cbbMaterielType
				// .getSelectedItem();
				// if (item == null) {
				// JOptionPane.showMessageDialog(FmBlsInnerMerge.this,
				// "请选择要导入的物料类别！", "提示",
				// JOptionPane.INFORMATION_MESSAGE);
				// return;
				// }
				// String materielType = item.getCode().trim();
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("正在自动归并数据,请稍候...");
				try {
					blsInnerMergeAction.blsAutoMerge(new Request(CommonVars
							.getCurrUser()));
					pnCommonQueryPage.setInitState();
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "自动归并数据可能有错!!", "警告!!",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				CommonProgress.setMessage("正在刷新数据表,请稍候...");
				pnCommonQueryPage.refreshData();
			} finally {
				CommonProgress.closeProgressDialog();
				FmBlsInnerMerge.this.miAutoInnerMerge.setEnabled(true);
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
							deleteData();

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
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (cbbMaterielType.getSelectedIndex() < 0) {
					// return;
					// }
					Request request = new Request(CommonVars.getCurrUser());
					// String materielTypeCode = ((ItemProperty) cbbMaterielType
					// .getSelectedItem()).getCode();
					// String materielTypeName = ((ItemProperty) cbbMaterielType
					// .getSelectedItem()).getName();
					List list = blsInnerMergeAction.findBlsInnerMerge(request);
					CustomReportDataSource ds = new CustomReportDataSource(list);
					try {
						InputStream masterReportStream = FmBlsInnerMerge.class
								.getResourceAsStream("report/InnerMergeReport.jasper");
						Map parameters = new HashMap();
						Calendar calendar = Calendar.getInstance();
						String printDate = String.valueOf(calendar
								.get(Calendar.YEAR))
								+ "年"
								+ String
										.valueOf(calendar.get(Calendar.MONTH) + 1)
								+ "月"
								+ String.valueOf(calendar
										.get(Calendar.DAY_OF_MONTH)) + "日";
						parameters.put("printDate", printDate);
						// parameters.put("type", materielTypeName);
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
		int count = jTable.getColumnModel().getColumnCount();
		for (int i = 1; i < count; i++) {
			jTable.getColumnModel().getColumn(i).setCellRenderer(
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
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmInnerMerge() {
		if (pmInnerMerge == null) {
			pmInnerMerge = new JPopupMenu();
			pmInnerMerge.add(getMiAutoInnerMerge());
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
			btnInnerMerge.setVisible(false);
			btnInnerMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							getPmInnerMerge().show(btnInnerMerge, 0,
									getPmInnerMerge().getHeight());
							getPmInnerMerge().show(btnInnerMerge, 0,
									getPmInnerMerge().getHeight());
						}
					});
		}
		return btnInnerMerge;
	}

	/**
	 * This method initializes miFromFile
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiAutoInnerMerge() {
		if (miAutoInnerMerge == null) {
			miAutoInnerMerge = new JMenuItem();
			miAutoInnerMerge.setText("自动归并");
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
					dispose();
				}
			});
		}
		return btnExit;
	}

	// 刷新数据
	private JButton getBtnRenovate() {
		if (btnRenovate == null) {
			btnRenovate = new JButton();
			btnRenovate.setText("刷新");
			btnRenovate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					pnCommonQueryPage.setInitState();
				}
			});
		}
		return btnRenovate;
	}

	/**
	 * This method initializes btnAddMateriel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddMateriel() {
		if (btnAddMateriel == null) {
			btnAddMateriel = new JButton();
			btnAddMateriel.setText("新增物料");
			btnAddMateriel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// if (cbbMaterielType.getSelectedItem() == null) {
							// JOptionPane.showMessageDialog(
							// FmBlsInnerMerge.this, "请选择物料类别", "提示",
							// JOptionPane.INFORMATION_MESSAGE);
							// return;
							// }
							// ItemProperty item = (ItemProperty)
							// cbbMaterielType
							// .getSelectedItem();
							// String materielType = item.getCode().trim();
							// List list=new ArrayList();
							List list = (List) BlsInnerMergeQuery.getInstance()
									.getMaterielNotInBlsInnerMerge();
							if (list == null || list.size() <= 0) {
								return;
							}
							List blsInnerMergeList = blsInnerMergeAction
									.importInnerMergeDataFromMateriel(
											new Request(CommonVars
													.getCurrUser()), list);
							dataSource.addAll(blsInnerMergeList);
							initTable(dataSource);
						}
					});
		}
		return btnAddMateriel;
	}

	/**
	 * 十码归并
	 * 
	 * @param isAddFromBlsTenInnerMerge
	 *            == null 说明是补充归并 true : 新增归并十码数据来自Bls十码数据 false:
	 *            新增归并十码数据来自手工新增十码数据
	 */
	private void tenInnerMerge(Boolean isAddFromBlsTenInnerMerge) {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		// ItemProperty item = (ItemProperty) cbbMaterielType.getSelectedItem();
		// if (item == null) {
		// JOptionPane.showMessageDialog(FmBlsInnerMerge.this, "请选择物料类别！",
		// "提示", JOptionPane.INFORMATION_MESSAGE);
		// return;
		// }
		// String materielType = item.getCode().trim();
		int result = this.checkDataForTenMerge(list);
		DgBlsTenInnerMerge dgTenInnerMerge = null;
		switch (result) {
		case 0:
			if (!isAddFromBlsTenInnerMerge) {
				// 新增归并来自于自用商品编码
				dgTenInnerMerge = new DgBlsTenInnerMerge();
				dgTenInnerMerge.setTable((MultiSpanCellTable) jTable);
				dgTenInnerMerge.setDataState(DataState.ADD);
				dgTenInnerMerge.setAddFromBlsTenInnerMerge(false);
				// dgTenInnerMerge.setMaterielType(materielType);
				dgTenInnerMerge.setCombinShow(cbCombin.isSelected());
				dgTenInnerMerge.setInnerMerge(true);
				dgTenInnerMerge.setVisible(true);
			} else if (isAddFromBlsTenInnerMerge) {
				// 新增归并来自于报关商品资料
				Object obj = BlsInnerMergeQuery.getInstance()
						.getBlsTenInnerMerge();
				if (obj == null) {
					return;
				}
				BlsTenInnerMerge blsTenInnerMerge = (BlsTenInnerMerge) obj;
				// dgTenInnerMerge = new DgBlsTenInnerMerge();
				// dgTenInnerMerge.setTable((MultiSpanCellTable) jTable);
				// dgTenInnerMerge.setDataState(DataState.ADD);
				// dgTenInnerMerge.setAddFromBlsTenInnerMerge(true);
				// dgTenInnerMerge.setMaterielType(materielType);
				// dgTenInnerMerge.setBlsTenInnerMerge(blsTenInnerMerge);
				// dgTenInnerMerge.setCombinShow(cbCombin.isSelected());
				// dgTenInnerMerge.setInnerMerge(true);
				// dgTenInnerMerge.setVisible(true);
				List ls = this.blsInnerMergeAction.saveBlsInnerMerge(
						new Request(CommonVars.getCurrUser()), list,
						blsTenInnerMerge);
				tableModel.updateRows(ls);
				refresh();
			}
			break;
		case 1:
			BlsTenInnerMerge blsTenInnerMerge = null;
			List currentRows = tableModel.getCurrentRows();
			for (int i = 0; i < currentRows.size(); i++) {
				BlsInnerMerge blsInnerMerge = (BlsInnerMerge) currentRows
						.get(i);
				blsTenInnerMerge = blsInnerMerge.getBlsTenInnerMerge();
				if (blsTenInnerMerge != null) {
					break;
				}
			}
			if (blsTenInnerMerge != null) {
				List ls = this.blsInnerMergeAction.saveBlsInnerMerge(
						new Request(CommonVars.getCurrUser()), currentRows,
						blsTenInnerMerge);
				tableModel.updateRows(ls);
				refresh();
			}
			break;
		case -1:
			JOptionPane.showMessageDialog(FmBlsInnerMerge.this, "编码不同的数据！",
					"警告", 0);
			break;
		case -2:
			JOptionPane.showMessageDialog(FmBlsInnerMerge.this, "申报计量单位不同！",
					"警告", 0);
			break;
		case -3:
			JOptionPane.showMessageDialog(FmBlsInnerMerge.this, "商品名称不同！",
					"警告", 0);
			break;
		case -4:
			JOptionPane.showMessageDialog(FmBlsInnerMerge.this, "选择的数据已全部归并！",
					"警告", 0);
			break;
		case -5:
			JOptionPane.showMessageDialog(FmBlsInnerMerge.this, "选择的数据的备案序号！",
					"警告", 0);
			break;
		}
	}

	/**
	 * 检查所选择的数据能否进行10位归并 如果数据有效则并且归并后的10位商品编码全部为空返回0，归并后的10位商品编码只要有一不为空返回1。；否则，
	 * 如果有编码不同的数据返回-1； 申报计量单位不同返回-2； 商品名称不同返回-3； 如果全部归并的话 返回-4；
	 * 如果选择的数据的备案序号不同返回-5。
	 * 
	 * @param list
	 * @return
	 */
	public int checkDataForTenMerge(List list) {
		Integer memoNo = -1;
		int n = 0;
		for (int i = 0; i < list.size(); i++) {
			BlsInnerMerge data = (BlsInnerMerge) list.get(i);
			if (data.getBlsTenInnerMerge() != null) {
				n++;
			}
			if (i == 0) {
				if (data.getBlsTenInnerMerge() != null) {
					memoNo = data.getBlsTenInnerMerge().getSeqNum();
				}
				continue;
			}

			// if (data.getMateriel().getComplex() != null) {
			// if (complex != null) {
			// if (!data.getMateriel().getComplex().equals(complex)) {
			// return -1;
			// }
			// }
			// complex = data.getMateriel().getComplex();
			// }
			// if (data.getMateriel().getCalUnit() != null) {
			// if (calUnit != null) {
			// if (!data.getMateriel().getCalUnit().equals(calUnit)) {
			// return -2;
			// }
			// }
			// calUnit = data.getMateriel().getCalUnit();
			// }
			// if (data.getMateriel().getFactoryName() != null
			// && !data.getMateriel().getFactoryName().equals("")) {
			// if (materielName != null && !materielName.equals("")) {
			// if (!data.getMateriel().getFactoryName().toLowerCase().equals(
			// materielName.toLowerCase())) {
			// return -3;
			// }
			// }
			// materielName = data.getMateriel().getFactoryName();
			// }
			if (data.getBlsTenInnerMerge() != null) {
				if (memoNo != null) {
					if (!memoNo.equals(data.getBlsTenInnerMerge().getSeqNum())) {
						return -5;
					}
				}
				memoNo = data.getBlsTenInnerMerge().getSeqNum();
			}
		}
		if (n == list.size()) {
			return -4;
		}
		if (n > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 撤消10码归并
	 */
	private void unDoTenInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		// List tmepList = blsInnerMergeAction.findBlsInnerMergeInContract(
		// new Request(CommonVars.getCurrUser()), list);
		// if (tmepList != null && tmepList.size() > 0) {
		// JOptionPane.showMessageDialog(this, "选中的行中有数据在合同被引用,不能撤消!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return;
		// }
		if (JOptionPane.showConfirmDialog(FmBlsInnerMerge.this,
				"是否确定撤消选定数据!!!", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		boolean isDeleteTenInnerMerge = false;
		if (JOptionPane.showConfirmDialog(FmBlsInnerMerge.this,
				"撤消归并时,是否删除没有引用的归并后资料!!!", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			isDeleteTenInnerMerge = false;
		} else {
			isDeleteTenInnerMerge = true;
		}
		list = this.blsInnerMergeAction.unDoTenInnerMerge(new Request(
				CommonVars.getCurrUser()), list, isDeleteTenInnerMerge);
		this.tableModel.updateRows(list);
		this.refresh();
		// pnCommonQueryPage.setInitState();
	}

	/**
	 * 十码重排
	 * 
	 */
	private void tenCodeResetNo() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list == null || list.size() <= 0) {
			JOptionPane.showMessageDialog(this, "请选择要重排的记录!!", "警告!!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		DgBlsTenResetNo dg = new DgBlsTenResetNo();
		dg.setCurrentRows(list);
		dg.setVisible(true);
		if (dg.isOk() == true) {
			pnCommonQueryPage.refreshData();
		}
	}

	/**
	 * 删除
	 * 
	 */
	private void deleteData() {
		try {
			if (JOptionPane.showConfirmDialog(FmBlsInnerMerge.this,
					"是否确定删除选定数据!!!", "提示", 0) != 0) {
				return;
			}
			List list = ((AttributiveCellTableModel) jTable.getModel())
					.getCurrentRows();
			if (list.size() < 1) {
				return;
			}
			// List tmepList = blsInnerMergeAction.findBlsInnerMergeInContract(
			// new Request(CommonVars.getCurrUser()), list);
			// if (tmepList != null && tmepList.size() > 0) {
			// JOptionPane.showMessageDialog(null, "选中的行中有数据在合同被引用,不能删除!!",
			// "警告!!", JOptionPane.INFORMATION_MESSAGE);
			// return;
			// }
			this.blsInnerMergeAction.deleteBlsInnerMerge(new Request(CommonVars
					.getCurrUser()), list);
			pnCommonQueryPage.refreshData();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "删除数据有错!!", "警告!!",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * This method initializes miEditBlsTenData
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiEditBlsTenData() {
		if (miEditBlsTenData == null) {
			miEditBlsTenData = new JMenuItem();
			miEditBlsTenData.setText("修改十码记录");
			miEditBlsTenData
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = ((AttributiveCellTableModel) jTable
									.getModel()).getCurrentRows();
							if (list.size() < 1) {
								JOptionPane
										.showMessageDialog(
												FmBlsInnerMerge.this,
												"请选择要修改的数据项!!", "信息!!",
												JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							// ItemProperty item = (ItemProperty)
							// cbbMaterielType
							// .getSelectedItem();
							// if (item == null) {
							// JOptionPane.showMessageDialog(
							// FmBlsInnerMerge.this, "请选择物料类别！", "提示",
							// JOptionPane.INFORMATION_MESSAGE);
							// return;
							// }
							// String materielType = item.getCode().trim();
							DgBlsTenInnerMerge dgTenInnerMerge = new DgBlsTenInnerMerge();
							dgTenInnerMerge
									.setTable((MultiSpanCellTable) jTable);
							dgTenInnerMerge.setDataState(DataState.EDIT);
							// dgTenInnerMerge.setMaterielType(materielType);
							dgTenInnerMerge
									.setCombinShow(cbCombin.isSelected());
							dgTenInnerMerge.setInnerMerge(true);
							dgTenInnerMerge.setVisible(true);
						}
					});
		}
		return miEditBlsTenData;
	}

	/**
	 * This method initializes cb
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCombin() {
		if (cbCombin == null) {
			cbCombin = new JCheckBox();
			cbCombin.setText("合并显示");
			cbCombin.setBounds(new Rectangle(7, 5, 87, 20));
			cbCombin.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					refresh();
				}
			});
		}
		return cbCombin;
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
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);

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
			Object value, boolean isLike) {
		// String materielType = ((ItemProperty)
		// cbbMaterielType.getSelectedItem())
		// .getCode();
		// if (materielType == null) {
		// return new ArrayList();
		// }
		Request request = new Request(CommonVars.getCurrUser());
		dataSource = blsInnerMergeAction.findBlsInnerMerge(request, index,
				length, property, value, isLike);// materielType,

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
					return FmBlsInnerMerge.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return FmBlsInnerMerge.this.getDataSource(index, length,
							property, value, isLike);
				}

			};
		}

		return pnCommonQueryPage;

	}

	/**
	 * This method initializes miFromFile
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiFromFile() {
		if (miFromFile == null) {
			miFromFile = new JMenuItem();
			miFromFile.setText("从文件导入");
			miFromFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBlsImportDataFromFile dg = new DgBlsImportDataFromFile();
					dg.setVisible(true);
					if (dg.isOk() == false) {
						return;
					}
					// if (dg.getMaterielType() == null) {
					// return;
					// }
					// if (((ItemProperty) cbbMaterielType.getSelectedItem())
					// .getCode().equals(dg.getMaterielType())) {
					pnCommonQueryPage.setInitState();
					// } else {
					// cbbMaterielType.setSelectedIndex(ItemProperty
					// .getIndexByCode(dg.getMaterielType(),
					// cbbMaterielType));
					// }
				}
			});
		}
		return miFromFile;
	}
}
