/*
 * Created on 2004-7-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.bcsinnermerge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
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
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.client.contract.BcsClientHelper;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.CommonTableContextPopupEvent;
import com.bestway.client.util.DataType;
import com.bestway.client.util.ExcelAdapter;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author bsway
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates checked by 陈井彬
 *         2008.11.29 物料与报关对应表
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FmBcsInnerMerge extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1963816528621296284L;

	private AttributiveCellTableModel tableModel = null;

	/**
	 * 表格数据源
	 */
	private List<BcsInnerMerge> dataSource = new ArrayList<BcsInnerMerge>();

	private JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	/**
	 * 工具栏
	 */
	private JToolBar jToolBar = null;

	private JPanel jPanel = null;

	/**
	 * 料件类型
	 */
	private JComboBox cbbMaterielType = null;

	/**
	 * 刷新按钮
	 */
	private JButton btnRenovate = null;

	// /**
	// * 导入按钮
	// */
	// private JButton btnImportData = null;
	//
	/**
	 * 显示未归并
	 */
	private JButton btnShowNotMerge = null;

	// /**
	// * 右键菜单
	// */
	// private JPopupMenu pmImportData = null;
	//
	// private JMenuItem mtFromMateriel = null;

	private JPopupMenu pmAfterTenInnerMerge = null;

	private JMenuItem miNewTenInnerMerge = null;

	private JMenuItem miUndoTenInnerMerge = null;

	private JPopupMenu pmBeforeTenInnerMerge = null;

	private JMenuItem miUndoTenInnerMerge1 = null;

	// private JMenuItem miResetMemoNo = null;

	private JMenuItem miAddTenInnerMerge = null;

	private JMenuItem miDeleteInnerMerge = null;

	/**
	 * 打印按钮
	 */
	private JButton btnPrint = null;

	private JPopupMenu pmTableContextPopupMenu = null;
	private JMenuItem miCopyValue = null;
	private ExcelAdapter excelAdapter = null;
	private int xpoint = 0;

	private int ypoint = 0;

	/**
	 * 是否能够复制
	 */
	private boolean isCopyMenuEnabled = false;

	private JMenuItem miAutoInnerMerge = null;

	private SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // @jve:decl-index=0:

	private JLabel jLabel = null;

	/**
	 * 关闭按钮
	 */
	private JButton btnExit = null;

	/**
	 * 新增物料
	 */
	private JButton btnAddMateriel = null;

	private BcsInnerMergeAction bcsInnerMergeAction = null;

	private JMenuItem miEditBcsTenData = null;

	/**
	 * 是否合并显示
	 */
	private JCheckBox cbCombin = null;
	/**
	 * 是否是未归并
	 */
	private boolean isNoMerge = false;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar1 = null;

	/**
	 * 查询操作页面
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;

	// private JMenuItem miAddFromBcsTenInnerMerge = null;

	private MaterialManageAction materialManageAction;

	private JButton btnFromFile = null;
	private JButton btnSetIsUsing;
	private JButton btnCheck;
	private JCheckBox cbOneToMany;

	/**
	 * 构造函数
	 */
	public FmBcsInnerMerge() {
		super();
		bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("bcsInnerMergeAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		jTable = new MultiSpanCellTable();
		jTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				if (e.getModifiers() != InputEvent.BUTTON3_MASK) {
					return;
				}
				xpoint = e.getX();
				ypoint = e.getY();
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
							if (columns[0] >= 0 && columns[0] <= 5) {
								if (isNewTenInnerMerge()) {
									getMiNewTenInnerMerge().setEnabled(true);
									getMiAddTenInnerMerge().setEnabled(true);
									// getMiAddFromBcsTenInnerMerge().setEnabled(
									// true);
									getMiUndoTenInnerMerge1().setEnabled(false);
								} else {
									getMiNewTenInnerMerge().setEnabled(false);
									getMiAddTenInnerMerge().setEnabled(false);
									// getMiAddFromBcsTenInnerMerge().setEnabled(
									// false);
									getMiUndoTenInnerMerge1().setEnabled(true);
								}
								getPmBeforeTenInnerMerge().show(jTable,
										e.getPoint().x, e.getPoint().y);
							} else if (columns[0] >= 6 && columns[0] <= 12) {
								if (!isEditTenInnerMerge()) {
									getMiUndoTenInnerMerge().setEnabled(false);
									getMiEditBcsTenData().setEnabled(false);
									// getMiResetMemoNo().setEnabled(false);
								} else {
									getMiUndoTenInnerMerge().setEnabled(true);
									getMiEditBcsTenData().setEnabled(true);
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
		pnCommonQueryPage.setInitState();

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
		this.setSize(900, 500);
	}

	// /**
	// * 初始化查询字段
	// */
	// public void initCbbQueryFields() {
	// if (pnCommonQueryPage.getCbbQueryField().getItemCount() > 0) {
	// pnCommonQueryPage.cbbQueryField.setSelectedItem(null);
	// return;
	// }
	// pnCommonQueryPage.getCbbQueryField().removeAllItems();
	// if (tableModel != null) {
	// for (int i = 0; i < tableModel.getColumnCount(); i++) {
	// JTableListColumn c = tableModel.getColumns().get(i);
	// if (c instanceof SerialColumn) {
	// continue;
	// }
	// if (c.isShowSearch() == false) {
	// continue;
	// }
	// pnCommonQueryPage.getCbbQueryField().addItem(
	// new Item(c.getCaption(),
	// c.getCustomProperty() == null ? c.getProperty()
	// : c.getCustomProperty(),
	// getDataTypeByColumn(c.getProperty())));
	// }
	// pnCommonQueryPage.cbbQueryField.setSelectedItem(null);
	// }
	// }

	/**
	 * 根据表格的列得到表格单元格列对应对象的属性
	 * 
	 * @param sProp
	 * @return
	 */
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
			jToolBar.setLayout(f);

			jToolBar.add(getJPanel());
			jToolBar.add(getBtnAddMateriel());
			jToolBar.add(getBtnFromFile());
			// jToolBar.add(getBtnInnerMerge());
			jToolBar.add(getBtnShowNotMerge());
			jToolBar.add(getBtnSetIsCount());
			jToolBar.add(getBtnRenovate());
			jToolBar.add(getBtnCheck());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnExit());

		}
		return jToolBar;
	}

	/**
	 * 显示未归并
	 * 
	 * @return
	 */
	private JButton getBtnShowNotMerge() {
		if (btnShowNotMerge == null) {
			btnShowNotMerge = new JButton();
			btnShowNotMerge.setText("显示未归并");
			btnShowNotMerge.setPreferredSize(new Dimension(75, 30));
			btnShowNotMerge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							isNoMerge = true;
							pnCommonQueryPage.setInitState();
						}
					});
		}
		return btnShowNotMerge;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(4, 2, 56, 25));
			jLabel.setText("物料类别");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new java.awt.Dimension(370, 30));
			jPanel.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbCombin(), null);
			jPanel.add(getCbOneToMany());
		}
		return jPanel;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (cbbMaterielType == null) {
			cbbMaterielType = new JComboBox();
			cbbMaterielType.setBounds(66, 2, 117, 25);
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
						pnCommonQueryPage.setInitState();
					}
				}
			});
		}
		return cbbMaterielType;
	}

	/**
	 * 初始化表格
	 * 
	 * @param dataSource
	 * @return
	 */
	private JTableListModel initTable(List<BcsInnerMerge> dataSource) {
		List<BcsInnerMerge> datas = null;
		if (cbOneToMany.isSelected() && dataSource != null) {
			datas = new ArrayList<BcsInnerMerge>();
			Map<String, Integer> ptNoCounts = new HashMap<String, Integer>();
			BcsInnerMerge merge;
			Integer count = 0;
			for (int i = 0; i < dataSource.size(); i++) {
				merge = dataSource.get(i);
				if (merge.getMateriel() == null) {
					continue;
				}

				count = ptNoCounts.get(merge.getMateriel().getPtNo());
				if (count == null) {
					count = 1;
				} else {
					count++;
				}

				ptNoCounts.put(merge.getMateriel().getPtNo(), count);
			}

			for (int i = 0; i < dataSource.size(); i++) {
				merge = dataSource.get(i);
				if (merge.getMateriel() == null) {
					continue;
				}
				count = ptNoCounts.get(merge.getMateriel().getPtNo());

				if (count > 1) {
					datas.add(merge);
				}
			}
		} else {
			datas = dataSource;
		}

		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) jTable,
				datas, new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料号", "materiel.ptNo", 100));
						list.add(addColumn("商品编码", "materiel.complex.code", 100));
						list.add(addColumn("工厂名称", "materiel.factoryName", 100));
						list.add(addColumn("规格", "materiel.factorySpec", 100));
						list.add(addColumn("详细型号规格", "materiel.ptDeSpec", 100));
						list.add(addColumn("企业单位", "materiel.calUnit.name", 50));
						list.add(new JTableListColumn("英文名称",
								"materiel.ptEnglishName", 100));
						list.add(new JTableListColumn("英文规格",
								"materiel.ptEnglishSpec", 100));

						list.add(addColumn("当前使用标志", "isUsing", 100));
						list.add(addColumn("归并序号", "bcsTenInnerMerge.seqNum",
								50, Integer.class));
						list.add(addColumn("报关名称", "bcsTenInnerMerge.name", 100));
						list.add(addColumn("商品规格", "bcsTenInnerMerge.spec", 100));
						list.add(addColumn("十位商品编码",
								"bcsTenInnerMerge.complex.code", 100));
						list.add(addColumn("报关单位",
								"bcsTenInnerMerge.comUnit.name", 50));
						list.add(addColumn("第一法定单位",
								"bcsTenInnerMerge.complex.firstUnit.name", 100));
						list.add(addColumn("第二法定单位",
								"bcsTenInnerMerge.complex.secondUnit.name", 100));
						list.add(addColumn("单位重量", "materiel.ptNetWeight", 70));
						list.add(addColumn("单价", "materiel.ptPrice", 70));
						list.add(addColumn("单位折算系数", "unitConvert", 70));
						list.add(addColumn("导入修改时间", "createDate", 100));
						return list;
					}
				});

		jTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		TableColumnModel cm = jTable.getColumnModel();

		ColumnGroup gBefore = new ColumnGroup("工厂资料");
		gBefore.add(cm.getColumn(1));
		gBefore.add(cm.getColumn(2));
		gBefore.add(cm.getColumn(3));
		gBefore.add(cm.getColumn(4));
		gBefore.add(cm.getColumn(5));
		gBefore.add(cm.getColumn(6));
		gBefore.add(cm.getColumn(7));
		gBefore.add(cm.getColumn(8));

		ColumnGroup gAfter = new ColumnGroup("报关资料");
		gAfter.add(cm.getColumn(9));
		gAfter.add(cm.getColumn(10));
		gAfter.add(cm.getColumn(11));
		gAfter.add(cm.getColumn(12));
		gAfter.add(cm.getColumn(13));
		gAfter.add(cm.getColumn(14));
		gAfter.add(cm.getColumn(15));
		gAfter.add(cm.getColumn(16));

		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();

		header.addColumnGroup(gBefore);

		header.addColumnGroup(gAfter);

		this.setTableCellToopTip();

		refresh();

		this.deleteCommonTableContextPopupEventMouseListener(jTable);

		setColor(jTable);

		cm.getColumn(9).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				String str = "否";
				// if(Boolean.TRUE.equals(value)) {
				// str = "是";
				// }
				if ("true".equals(value)) {
					str = "是";
				}
				return super.getTableCellRendererComponent(table, str,
						isSelected, hasFocus, row, column);
			}

		});

		return tableModel;
	}

	/**
	 * 刷新
	 */
	private void refresh() {
		if (cbCombin.isSelected()) {
			((MultiSpanCellTable) jTable).combineRows(6, new int[] { 6, 7, 8,
					9, 10, 11, 12 });// , 14, 15, 16, 17, 18
		} else {
			((MultiSpanCellTable) jTable).splitRows(7);
		}
	}

	/**
	 * render table color row
	 */
	class ColorTableCellRenderer extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ColorTableCellRenderer() {
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			boolean isChange = false;
			if (checkValue(table, row, column)) {
				c.setForeground(new Color(0, 0, 204));
				c.setBackground(table.getBackground());
				// c.setBackground(new Color(204, 204, 255));
				// c.setForeground(table.getForeground());
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

	private void setColor(JTable jTable) {
		JTableListModel tableModel = (JTableListModel) jTable.getModel();
		for (int i = 1; i < tableModel.getColumnCount(); i++) {
			jTable.getColumnModel().getColumn(i)
					.setCellRenderer(new ColorTableCellRenderer());
		}
	}

	private boolean checkValue(JTable table, int row, int column) {
		JTableListModel tableModel = (JTableListModel) table.getModel();
		BcsInnerMerge data = (BcsInnerMerge) tableModel.getDataByRow(row);
		if (data == null) {
			return false;
		}
		Date currentDate = new Date();
		if (data.getCreateDate() != null) {
			if (bartDateFormat.format(data.getCreateDate()).equals(
					bartDateFormat.format(currentDate))) {
				return true;
			}
		}
		// if (data.getModifyDate() != null) {
		// if (formate.format(data.getModifyDate()).equals(
		// formate.format(currentDate))) {
		// return true;
		// }
		// }

		return false;
	}

	/**
	 * 删除表格的鼠标监听器
	 * 
	 * @param table
	 */
	private void deleteCommonTableContextPopupEventMouseListener(JTable table) {
		MouseListener[] mouseListeners = table.getMouseListeners();
		for (int i = 0; i < mouseListeners.length; i++) {
			if (mouseListeners[i] instanceof CommonTableContextPopupEvent) {
				table.removeMouseListener(mouseListeners[i]);
			}
		}
	}

	// /**
	// * This method initializes btnImportData
	// *
	// * @return javax.swing.JButton
	// */
	// private JButton getBtnImportData() {
	// if (btnImportData == null) {
	// btnImportData = new JButton();
	// btnImportData.setText("导入");
	// btnImportData.setPreferredSize(new Dimension(65, 30));
	// btnImportData
	// .addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// getPmImportData().show(btnImportData, 0,
	// btnImportData.getHeight());
	// }
	// });
	// }
	// return btnImportData;
	// }

	/**
	 * This method initializes pmImportData
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	// private JPopupMenu getPmImportData() {
	// if (pmImportData == null) {
	// pmImportData = new JPopupMenu();
	// pmImportData.add(getMtFromMateriel());
	// pmImportData.add(getMiFromFile());
	// }
	// return pmImportData;
	// }
	/**
	 * This method initializes mtFromMateriel
	 * 
	 * @return javax.swing.JMenuItem
	 */
	// private JMenuItem getMtFromMateriel() {
	// if (mtFromMateriel == null) {
	// mtFromMateriel = new JMenuItem();
	// mtFromMateriel.setText("从报关常用物料");
	// mtFromMateriel
	// .addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// try {
	// ItemProperty item = (ItemProperty) cbbMaterielType
	// .getSelectedItem();
	// if (item != null) {
	// ImportMaterielDataRunnable thread = new ImportMaterielDataRunnable(
	// item.getCode().trim());
	// thread.start();
	// } else {
	// JOptionPane.showMessageDialog(
	// FmBcsInnerMerge.this,
	// "请选择要导入的物料类别！", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// }
	// } catch (Exception ex) {
	//
	// }
	// }
	// });
	// }
	// return mtFromMateriel;
	// }
	/**
	 * 从物料主档中导入资料线程
	 * 
	 * @author Administrator
	 * 
	 */
	// class ImportMaterielDataRunnable extends Thread {
	// private String materielTypelist = null;
	//
	// public ImportMaterielDataRunnable(String materielTypelist) {
	// this.materielTypelist = materielTypelist;
	// }
	//
	// public void run() {
	// try {
	// CommonProgress.showProgressDialog();
	// CommonProgress.setMessage("系统正在从物料主档中导入资料，请稍后");
	// try {
	// bcsInnerMergeAction.importInnerMergeDataFromMateriel(
	// new Request(CommonVars.getCurrUser()),
	// materielTypelist);
	// } catch (Exception ee) {
	// JOptionPane.showMessageDialog(null, ee.toString());
	// CommonProgress.closeProgressDialog();
	// return;
	// }
	// pnCommonQueryPage.setInitState();
	// } finally {
	// CommonProgress.closeProgressDialog();
	// }
	// JOptionPane.showMessageDialog(null, "资料导入成功！");
	// }
	// };
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
			pmAfterTenInnerMerge.add(getMiEditBcsTenData());
			pmAfterTenInnerMerge.addSeparator();
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
	 * This method initializes miNewTenInnerMerge 新增10码归并
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
	 * This method initializes miUndoTenInnerMerge 撤消物料与报关对应
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
			// pmBeforeTenInnerMerge.add(getMiAddFromBcsTenInnerMerge());
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

	/**
	 * This method initializes miAddFromBcsTenInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	// private JMenuItem getMiAddFromBcsTenInnerMerge() {
	// if (miAddFromBcsTenInnerMerge == null) {
	// miAddFromBcsTenInnerMerge = new JMenuItem();
	// miAddFromBcsTenInnerMerge.setText("新增物料与报关对应来自报关商品资料");
	// miAddFromBcsTenInnerMerge
	// .addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// tenInnerMerge(true);
	// }
	//
	// });
	// }
	// return miAddFromBcsTenInnerMerge;
	// }
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

	/**
	 * 判断当前选中行是否有十位商品编码,是否可以新增
	 */
	private boolean isNewTenInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		for (int i = 0; i < list.size(); i++) {
			BcsInnerMerge data = (BcsInnerMerge) list.get(i);
			if (data.getBcsTenInnerMerge() != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断当前选中行是否有十位商品编码,是否可以编辑
	 * 
	 * @return
	 */
	private boolean isEditTenInnerMerge() {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		for (int i = 0; i < list.size(); i++) {
			BcsInnerMerge data = (BcsInnerMerge) list.get(i);
			if (data.getBcsTenInnerMerge() == null) {
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
							tenInnerMerge(true);
						}
					});
		}
		return miAddTenInnerMerge;
	}

	/**
	 * 自动归并数据
	 * 
	 * @author Administrator
	 * 
	 */
	class AutoInnerMergeDataThread extends Thread {
		public void run() {
			try {
				FmBcsInnerMerge.this.miAutoInnerMerge.setEnabled(false);
				ItemProperty item = (ItemProperty) cbbMaterielType
						.getSelectedItem();
				if (item == null) {
					JOptionPane.showMessageDialog(FmBcsInnerMerge.this,
							"请选择要导入的物料类别！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String materielType = item.getCode().trim();
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("正在自动归并数据,请稍候...");
				try {
					bcsInnerMergeAction
							.bcsAutoMerge(
									new Request(CommonVars.getCurrUser()),
									materielType);
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
				FmBcsInnerMerge.this.miAutoInnerMerge.setEnabled(true);
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
	 * This method initializes btnPrint 打印
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setPreferredSize(new Dimension(50, 30));
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
					List list = bcsInnerMergeAction.findBcsInnerMerge(request,
							materielTypeCode);
					CustomReportDataSource ds = new CustomReportDataSource(list);
					try {
						InputStream masterReportStream = FmBcsInnerMerge.class
								.getResourceAsStream("report/InnerMergeReport.jasper");
						Map parameters = new HashMap();
						Calendar calendar = Calendar.getInstance();
						String printDate = String.valueOf(calendar
								.get(Calendar.YEAR))
								+ "年"
								+ String.valueOf(calendar.get(Calendar.MONTH) + 1)
								+ "月"
								+ String.valueOf(calendar
										.get(Calendar.DAY_OF_MONTH)) + "日";
						parameters.put("printDate", printDate);
						parameters.put("type", materielTypeName);
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
	 * 表格渲染器 toolTip table render 列
	 */
	class ToolTipRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setPreferredSize(new Dimension(50, 30));
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
			btnRenovate.setPreferredSize(new Dimension(50, 30));
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
			btnAddMateriel.setPreferredSize(new Dimension(65, 30));
			btnAddMateriel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbMaterielType.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmBcsInnerMerge.this, "请选择物料类别", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							ItemProperty item = (ItemProperty) cbbMaterielType
									.getSelectedItem();
							String materielType = item.getCode().trim();
							List list = (List) BcsClientHelper
									.getInstance()
									.getMaterielNotInBcsInnerMerge(materielType);
							if (list == null || list.size() <= 0) {
								return;
							}
							List bcsInnerMergeList = bcsInnerMergeAction
									.importInnerMergeDataFromMateriel(
											new Request(CommonVars
													.getCurrUser()), list,
											materielType);
							dataSource.addAll(bcsInnerMergeList);
							initTable(dataSource);
						}
					});
			btnAddMateriel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.out.println("actionPerformed()"); // TODO
																		// Auto-generated
																		// Event
																		// stub
																		// actionPerformed()
						}
					});
		}
		return btnAddMateriel;
	}

	/**
	 * 新增10码归并时，检查料号是否已做归并
	 * 
	 * @param list
	 *            新的10码归并
	 * @param bcsTenInnerMerge
	 *            十位商品编码
	 * @return
	 */
	private boolean checkTenInnerMergeIsExist(List list,
			BcsTenInnerMerge bcsTenInnerMerge) {
		String materielTypeCode = ((ItemProperty) cbbMaterielType
				.getSelectedItem()).getCode();
		// 当新增10归并事，根据Key=料号+归并序号判断在归并关系中是否存在，如果存在不允许新增
		List<BcsInnerMerge> listBcsInnerMerge = bcsInnerMergeAction
				.findBcsInnerMerge(new Request(CommonVars.getCurrUser()),
						materielTypeCode);
		String errorMessageInfo = "";
		Map bcsInnerMergeMap = new HashMap<String, BcsInnerMerge>();
		for (int i = 0; i < listBcsInnerMerge.size(); i++) {// 已经存在的10码归并
			BcsInnerMerge value = listBcsInnerMerge.get(i);
			String ptno = value.getMateriel().getPtNo();
			String seqNum = value.getBcsTenInnerMerge() == null ? ""
					: listBcsInnerMerge.get(i).getBcsTenInnerMerge()
							.getSeqNum().toString();
			String key = ptno + "@" + seqNum;
			if (!bcsInnerMergeMap.containsKey(key)) {
				bcsInnerMergeMap.put(key, value);
			}
		}
		for (int i = 0; i < list.size(); i++) {// 要新增的10码归并
			BcsInnerMerge value = (BcsInnerMerge) list.get(i);
			String ptno = value.getMateriel().getPtNo();
			String seqNum = bcsTenInnerMerge.getSeqNum().toString();
			String key = ptno + "@" + seqNum;
			if (!bcsInnerMergeMap.containsKey(key)) {
				bcsInnerMergeMap.put(key, value);
			} else {
				errorMessageInfo += seqNum + "/";
			}
		}
		if (errorMessageInfo.length() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 新增10码归并时，设置是否当前使用状态] 新增10码归并中对应一个料号只有一笔归并状态设为当前使用,其他的为不可用状态
	 */
	private void setBcsInnerMergeIsUsing(List ls) {
		String materielTypeCode = ((ItemProperty) cbbMaterielType
				.getSelectedItem()).getCode();
		List<BcsInnerMerge> listBcsInnerMerge = bcsInnerMergeAction
				.findBcsInnerMerge(new Request(CommonVars.getCurrUser()),
						materielTypeCode);
		Map bcsInnerMergeMap = new HashMap<String, BcsInnerMerge>();
		List listNew = new ArrayList<BcsInnerMerge>();
		// 新增的归并
		for (int i = 0; i < ls.size(); i++) {
			BcsInnerMerge val = (BcsInnerMerge) ls.get(i);
			String ptno = val.getMateriel().getPtNo();
			if (!bcsInnerMergeMap.containsKey(ptno)) {
				val.setIsUsing(true);// 当前使用
				bcsInnerMergeMap.put(ptno, val);
			} else {
				val.setIsUsing(false);// 不可用状态
			}
			listNew.add(val);
		}
		// 除去新增的归并，原来已经做对应的数据中，料号与新增料号的相同数据当前使用状态设置为不可用
		listBcsInnerMerge.removeAll(ls);
		for (int i = 0; i < listBcsInnerMerge.size(); i++) {
			BcsInnerMerge val = (BcsInnerMerge) listBcsInnerMerge.get(i);
			String ptno = val.getMateriel().getPtNo();
			if (bcsInnerMergeMap.containsKey(ptno)) {
				val.setIsUsing(false);// 当前不可用
				listNew.add(val);
			}
		}
		this.bcsInnerMergeAction.saveBcsInnerMerge(
				new Request(CommonVars.getCurrUser()), listNew);
	}

	/**
	 * 十码归并
	 * 
	 * @param isAddFromBcsTenInnerMerge
	 *            == null 说明是补充归并 true : 新增归并十码数据来自Bcs十码数据 false:
	 *            新增归并十码数据来自手工新增十码数据
	 */
	private void tenInnerMerge(Boolean isAddFromBcsTenInnerMerge) {
		List list = ((AttributiveCellTableModel) jTable.getModel())
				.getCurrentRows();
		if (list.size() < 1) {
			return;
		}
		ItemProperty item = (ItemProperty) cbbMaterielType.getSelectedItem();
		if (item == null) {
			JOptionPane.showMessageDialog(FmBcsInnerMerge.this, "请选择物料类别！",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String materielType = item.getCode().trim();
		int result = this.checkDataForTenMerge(list);
		switch (result) {
		case 0:
			if (!isAddFromBcsTenInnerMerge) {
				// 新增归并来自于自用商品编码
				Object obj = BcsCommonQuery.getInstance().getBcsTenInnerMerge(
						materielType);
				if (obj == null) {
					return;
				}
				BcsTenInnerMerge bcsTenInnerMerge = (BcsTenInnerMerge) obj;

				if (checkTenInnerMergeIsExist(list, bcsTenInnerMerge)) {
					JOptionPane.showMessageDialog(FmBcsInnerMerge.this,
							"料号已在归并序号" + bcsTenInnerMerge.getSeqNum()
									+ "存在,不允许重复新增", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				List ls = this.bcsInnerMergeAction.saveBcsInnerMerge(
						new Request(CommonVars.getCurrUser()), list,
						bcsTenInnerMerge);
				tableModel.updateRows(ls);
				setBcsInnerMergeIsUsing(ls);
				refresh();
			} else if (isAddFromBcsTenInnerMerge) {
				// 补充归并来自于报关商品资料
				Object obj = BcsCommonQuery.getInstance()
						.getBcsTenInnerMergeInMerge(materielType);
				if (obj == null) {
					return;
				}
				BcsTenInnerMerge bcsTenInnerMerge = (BcsTenInnerMerge) obj;
				List ls = this.bcsInnerMergeAction.saveBcsInnerMerge(
						new Request(CommonVars.getCurrUser()), list,
						bcsTenInnerMerge);
				tableModel.updateRows(ls);
				refresh();
			}
			break;
		case 1:
			BcsTenInnerMerge bcsTenInnerMerge = null;
			List currentRows = tableModel.getCurrentRows();
			for (int i = 0; i < currentRows.size(); i++) {
				BcsInnerMerge bcsInnerMerge = (BcsInnerMerge) currentRows
						.get(i);
				bcsTenInnerMerge = bcsInnerMerge.getBcsTenInnerMerge();
				if (bcsTenInnerMerge != null) {
					break;
				}
			}
			if (bcsTenInnerMerge != null) {
				List ls = this.bcsInnerMergeAction.saveBcsInnerMerge(
						new Request(CommonVars.getCurrUser()), currentRows,
						bcsTenInnerMerge);
				tableModel.updateRows(ls);
				refresh();
			}
			break;
		case -1:
			JOptionPane.showMessageDialog(FmBcsInnerMerge.this, "编码不同的数据！",
					"警告", 0);
			break;
		case -2:
			JOptionPane.showMessageDialog(FmBcsInnerMerge.this, "申报计量单位不同！",
					"警告", 0);
			break;
		case -3:
			JOptionPane.showMessageDialog(FmBcsInnerMerge.this, "商品名称不同！",
					"警告", 0);
			break;
		case -4:
			JOptionPane.showMessageDialog(FmBcsInnerMerge.this, "选择的数据已全部归并！",
					"警告", 0);
			break;
		case -5:
			JOptionPane.showMessageDialog(FmBcsInnerMerge.this, "选择的数据的备案序号！",
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
			BcsInnerMerge data = (BcsInnerMerge) list.get(i);
			if (data.getBcsTenInnerMerge() != null) {
				n++;
			}
			if (i == 0) {
				if (data.getBcsTenInnerMerge() != null) {
					memoNo = data.getBcsTenInnerMerge().getSeqNum();
				}
				continue;
			}
			if (data.getBcsTenInnerMerge() != null) {
				if (memoNo != null) {
					if (!memoNo.equals(data.getBcsTenInnerMerge().getSeqNum())) {
						return -5;
					}
				}
				memoNo = data.getBcsTenInnerMerge().getSeqNum();
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
		if (JOptionPane.showConfirmDialog(FmBcsInnerMerge.this,
				"是否确定撤消选定数据!!!", "提示", 0) != 0) {
			return;
		}
		list = this.bcsInnerMergeAction.unDoTenInnerMerge(new Request(
				CommonVars.getCurrUser()), list);
		this.tableModel.updateRows(list);
		this.refresh();
	}

	/**
	 * 删除
	 * 
	 */
	private void deleteData() {
		try {
			if (JOptionPane.showConfirmDialog(FmBcsInnerMerge.this,
					"是否确定删除选定数据!!!", "提示", 0) != 0) {
				return;
			}
			List list = ((AttributiveCellTableModel) jTable.getModel())
					.getCurrentRows();
			if (list.size() < 1) {
				return;
			}
			// List tmepList = bcsInnerMergeAction.findBcsInnerMergeInContract(
			// new Request(CommonVars.getCurrUser()), list);
			// if (tmepList != null && tmepList.size() > 0) {
			// JOptionPane.showMessageDialog(null, "选中的行中有数据在合同被引用,不能删除!!",
			// "警告!!", JOptionPane.INFORMATION_MESSAGE);
			// return;
			// }
			this.bcsInnerMergeAction.deleteBcsInnerMerge(
					new Request(CommonVars.getCurrUser()), list);
			pnCommonQueryPage.refreshData();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "删除数据有错!!", "警告!!",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * This method initializes miEditBcsTenData
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiEditBcsTenData() {
		if (miEditBcsTenData == null) {
			miEditBcsTenData = new JMenuItem();
			miEditBcsTenData.setText("修改十码记录");
			miEditBcsTenData
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = ((AttributiveCellTableModel) jTable
									.getModel()).getCurrentRows();
							if (list.size() < 1) {
								JOptionPane
										.showMessageDialog(
												FmBcsInnerMerge.this,
												"请选择要修改的数据项!!", "信息!!",
												JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							ItemProperty item = (ItemProperty) cbbMaterielType
									.getSelectedItem();
							if (item == null) {
								JOptionPane.showMessageDialog(
										FmBcsInnerMerge.this, "请选择物料类别！", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							String materielType = item.getCode().trim();
							DgBcsTenInnerMerge dgTenInnerMerge = new DgBcsTenInnerMerge();
							dgTenInnerMerge
									.setTable((MultiSpanCellTable) jTable);
							dgTenInnerMerge.setDataState(DataState.EDIT);
							dgTenInnerMerge.setAddFromBcsTenInnerMerge(false);
							dgTenInnerMerge.setMaterielType(materielType);
							dgTenInnerMerge.setCombinShow(cbCombin.isSelected());
							dgTenInnerMerge.setInnerMerge(true);
							dgTenInnerMerge.setVisible(true);
						}
					});
		}
		return miEditBcsTenData;
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
			cbCombin.setBounds(new Rectangle(189, 5, 75, 20));
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
		String materielType = ((ItemProperty) cbbMaterielType.getSelectedItem())
				.getCode();
		if (materielType == null) {
			return new ArrayList();
		}
		Request request = new Request(CommonVars.getCurrUser());
		dataSource = bcsInnerMergeAction
				.findBcsInnerMerge(request, materielType, index, length,
						property, value, isLike, isNoMerge);

		isNoMerge = false;
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
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public JTableListModel initTable(List dataSource) {
					return FmBcsInnerMerge.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return FmBcsInnerMerge.this.getDataSource(index, length,
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
	private JButton getBtnFromFile() {
		if (btnFromFile == null) {
			btnFromFile = new JButton();
			btnFromFile.setText("从文件导入");
			btnFromFile.setPreferredSize(new Dimension(75, 30));
			btnFromFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBcsImportDataFromFile dg = new DgBcsImportDataFromFile();
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
		return btnFromFile;
	}

	private JButton getBtnSetIsCount() {
		if (btnSetIsUsing == null) {
			btnSetIsUsing = new JButton("设置为当前使用");
			btnSetIsUsing.setPreferredSize(new Dimension(100, 30));
			btnSetIsUsing.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<BcsInnerMerge> list = tableModel.getCurrentRows();
					if (list == null || list.isEmpty()) {
						JOptionPane.showMessageDialog(FmBcsInnerMerge.this,
								"请至少选择一行");
						return;
					}
					new SetIsUsingThread().start();
				}
			});
			btnSetIsUsing.setToolTipText("在报表计算的时候默认使用该对应关系折算。");
		}
		return btnSetIsUsing;
	}

	/**
	 * 自动归并数据
	 * 
	 * @author Administrator
	 * 
	 */
	class SetIsUsingThread extends Thread {
		public void run() {
			try {
				List<BcsInnerMerge> list = tableModel.getCurrentRows();
				FmBcsInnerMerge.this.btnSetIsUsing.setEnabled(false);

				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("正在准备中,请稍候...");
				// 获得并查询选中行归并序号
				list = bcsInnerMergeAction.saveBcsInnerMergeIsCounts(
						new Request(CommonVars.getCurrUser()), list);
				CommonProgress.setMessage("正在刷新数据表,请稍候...");
				pnCommonQueryPage.setInitState();
			} finally {
				CommonProgress.closeProgressDialog();
				FmBcsInnerMerge.this.btnSetIsUsing.setEnabled(true);
			}
			JOptionPane.showMessageDialog(FmBcsInnerMerge.this,
					"设置为【核查分析】报表统计使用成功！");
		}
	}

	private JButton getBtnCheck() {
		if (btnCheck == null) {
			btnCheck = new JButton("检查");
			btnCheck.setVisible(false);
			btnCheck.setEnabled(false);
			btnCheck.setPreferredSize(new Dimension(50, 30));
			btnCheck.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane
								.showMessageDialog(FmBcsInnerMerge.this,
										"请选择单据!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					List unitList = CustomBaseList.getInstance().getUnits();
					Hashtable<String, Unit> hsUnit = new Hashtable<String, Unit>();
					for (int i = 0; i < unitList.size(); i++) {
						Unit u = (Unit) unitList.get(i);
						hsUnit.put(u.getName(), u);
					}

					// 获得计量单位
					List calUnitList = materialManageAction
							.findCalUnit(new Request(CommonVars.getCurrUser()));
					// 保存公司自用的计量单位
					Hashtable<String, CalUnit> hsCalUnit = new Hashtable<String, CalUnit>();
					for (int i = 0; i < calUnitList.size(); i++) {
						CalUnit cu = (CalUnit) calUnitList.get(i);
						hsCalUnit.put(cu.getName(), cu);
					}
					List list = ((AttributiveCellTableModel) jTable.getModel())
							.getCurrentRows();
					String err = "";
					for (int i = 0; i < list.size(); i++) {

						// 存放报关常用工厂物料资料 Materiel :工厂料号,工厂品名,工厂规格,工厂单位
						// 存放报关商品资料BcsTenInnerMerge:归并序号,报关名称,报关规格,报关单位,(Complex)商品编码
						// BCS内部归并对应表:折算系数
						// System.out.println(ptNo);
						// if(ptNo==null||"".equals(ptNo)){
						// err+="".equals(err)?"第"+(i+1)+"条的料号为空":",第"+(i+1)+"条的料号为空";
						// }
						// String factoryName =
						// bim.getMateriel().getFactoryName();
						// if(factoryName==null||"".equals(factoryName)){
						// err+="".equals(err)?"料号"+ptNo+"的工厂品名为空":",料号"+ptNo+"的工厂品名为空";
						// }
						// String factorySpec =
						// bim.getMateriel().getFactorySpec();
						// if(factorySpec==null||"".equals(factorySpec)){
						// err+="".equals(err)?"料号"+ptNo+"的工厂规格为空":",料号"+ptNo+"的工厂规格为空";
						// }
						// String calUnit =
						// bim.getMateriel().getCalUnit().getName();
						// if(calUnit==null||"".equals(calUnit)){
						// err+="".equals(err)?"料号"+ptNo+"的工厂单位为空":",料号"+ptNo+"的工厂单位为空";
						// }
						//
						// BcsTenInnerMerge btim = bim.getBcsTenInnerMerge();
						// if(btim==null){
						// err+="".equals(err)?"料号"+ptNo+"的报关资料为空":",料号"+ptNo+"的报关资料为空";
						// }else{
						// if(null==btim.getSeqNum()){
						// err+="".equals(err)?"料号"+ptNo+"的归并序号为空":",料号"+ptNo+"的归并序号为空";
						// }
						//
						// if(btim.getName()==null||"".equals(btim.getName())){
						// err+="".equals(err)?"料号"+ptNo+"的报关名称为空":",料号"+ptNo+"的报关名称为空";
						// }
						//
						// if(btim.getSpec()==null||"".equals(btim.getSpec())){
						// err+="".equals(err)?"料号"+ptNo+"的报关规格为空":",料号"+ptNo+"的报关规格为空";
						// }
						//
						// if(btim.getComUnit()==null){
						// err+="".equals(err)?"料号"+ptNo+"的报关单位为空":",料号"+ptNo+"的报关单位为空";
						// }
						//
						// if(btim.getComplex()==null||"".equals(btim.getComplex().getCode())){
						// err+="".equals(err)?"料号"+ptNo+"的商品编码为空":",料号"+ptNo+"的商品编码为空";
						// }else{
						// Complex com = bcsInnerMergeAction.findComplexByCode(
						// new Request(CommonVars.getCurrUser()),
						// btim.getComplex().getCode());
						// if(com==null){
						// err+="".equals(err)?"料号"+ptNo+"的商品编码,在商品编码库不存在":",料号"+ptNo+"的商品编码,在商品编码库不存在";
						// }
						// }
						// }
						BcsInnerMerge bim = (BcsInnerMerge) list.get(i);
						String ptNo = bim.getMateriel().getPtNo();
						BcsTenInnerMerge btim = bim.getBcsTenInnerMerge();
						if (btim == null) {
							err += "".equals(err) ? "料号" + ptNo + "的报关资料为空"
									: ",料号" + ptNo + "的报关资料为空";
						} else {
							if (null == btim.getSeqNum()) {
								if (btim.getSeqNum() <= 0) {
									err += "".equals(err) ? "料号" + ptNo
											+ "的归并序号必须大于0" : ",料号" + ptNo
											+ "的归并序号必须大于0";
								}
								err += "".equals(err) ? "料号" + ptNo + "的归并序号为空"
										: ",料号" + ptNo + "的归并序号为空";
							}
						}
						double unitConvert = bim.getUnitConvert() == null ? 0.0
								: bim.getUnitConvert();
						if (unitConvert <= 0) {
							err += "".equals(err) ? "料号" + ptNo + "的的折算系数必须大于0"
									: ",料号" + ptNo + "的的折算系数必须大于0";
						}
					}
					if (err.equals("")) {
						JOptionPane.showMessageDialog(FmBcsInnerMerge.this,
								"数据正确", "提示", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(FmBcsInnerMerge.this,
								err, "提示", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			btnCheck.setToolTipText("检查所有对应关系数据的完整性和合法性");
		}
		return btnCheck;
	}

	private JCheckBox getCbOneToMany() {
		if (cbOneToMany == null) {
			cbOneToMany = new JCheckBox("显示一对多");
			cbOneToMany.setBounds(270, 3, 95, 25);
			cbOneToMany.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					initTable(dataSource);
				}
			});
		}
		return cbOneToMany;
	}
}
