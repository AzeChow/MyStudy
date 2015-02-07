/*
 * Created on 2004-7-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.TempInnerMergeData;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author bsway 请改代码的同事注意：一定先看类字段说明再改
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgInnerMergeNoMerger extends JDialogBase {
	private CommonBaseCodeAction commonBaseCodeAction = null;

	private JTableListModelAdapter topAdapter = null; // @jve:decl-index=0:

	private AttributiveCellTableModel downTableModel = null;

//	private AttributiveCellTableModel topTableModel = null;
	
	private JTableListModel topTableModel = null;

	private List downDataSource = null; // @jve:decl-index=0:

	private List topDataSource = null; // @jve:decl-index=0:

	private JPanel jContentPane = null;

	private JTable downJTable = null;

	private JTable topJTable = null;

	private JScrollPane downJScrollPane = null;

	private JScrollPane topJScrollPane = null;

	private JToolBar jToolBar = null;

	private JPanel toolJPanel = null;

	private JPanel jPanel = null;

	private JComboBox cbbMaterielType = null;

	private JMenuItem miUndoTenInnerMerge = null;

	private HashMap hmTableInfo = null;

	private boolean isCopyMenuEnabled = false;

	private int index = -1000;

	private int count = 0;

	private PnCommonQueryPage pnCommonQueryPage = null;

	private JButton btnExit = null;

	private JLabel jLabel = null;

	private JPanel downJPanel = null;

	private JPanel topJPanel = null;

	private JToolBar jToolBar1 = null;

	private JSplitPane jSplitPane = null;

	private JCheckBox cbSelectAll = null; // 全选

	private boolean isNoten = false;

	private JButton jButton = null;

	private JButton btnWriteBack = null;

	private JCheckBox cbSelectNotAll = null;
	private ButtonGroup buttonGroup = new ButtonGroup(); // @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public DgInnerMergeNoMerger() {
		super();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		initialize();
		downJTable = new MultiSpanCellTable();
//		topJTable = new MultiSpanCellTable();
		downJScrollPane.setViewportView(downJTable);
		topJScrollPane.setViewportView(topJTable);
		initDownTable(new Vector());
		initTopTable(new Vector());
		buttonGroup.add(cbSelectAll);
		buttonGroup.add(cbSelectNotAll);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("未备案的内部归并资料");
		this.setContentPane(getJContentPane());
		this.setSize(896, 564);
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getDownJTable() {
		if (downJTable == null) {
			downJTable = new JTable();

		}
		return downJTable;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTopJTable() {
		if (topJTable == null) {
			topJTable = new GroupableHeaderTable();
			System.out.println("init top table..");
//			topJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
//				@Override
//				public void valueChanged(ListSelectionEvent e) {
//					System.out.println("section change..");
//					if(topTableModel.getSize() > 0){						
//						List<TempInnerMergeData> list = topTableModel.getCurrentRows();
//						Point p =   topJTable.getMousePosition();
//						int column = topJTable.columnAtPoint(p);
//						for (TempInnerMergeData item : list) {
////							if(column!=1){//如果鼠标选择的第一列复选框将不执行表格拖拉事件
//								item.setIsSelected(!item.getIsSelected());
////							}
//						}
//					}
//				}
//			});
			/**
			 * 2014-2-8因企业每天补充归并较多，所以添加鼠标滑行选择事件，且需在JTableListModel才能执行
			 */
			topJTable.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if (topTableModel.getSize() > 0) {
						List<TempInnerMergeData> list = topTableModel
								.getCurrentRows();

						Point p = topJTable.getMousePosition();
						if(p == null)
							return;
						int column = topJTable.columnAtPoint(p);
						for (TempInnerMergeData item : list) {
							 if(column!=1){//如果鼠标选择的第一列复选框将不执行表格拖拉事件
							item.setIsSelected(!item.getIsSelected());
											}
						}
					}
				}
			});
		}
		return topJTable;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getDownJScrollPane() {
		if (downJScrollPane == null) {
			downJScrollPane = new JScrollPane();
			downJScrollPane.setViewportView(getDownJTable());
		}
		return downJScrollPane;
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
	private JScrollPane getTopJScrollPane() {
		if (topJScrollPane == null) {
			topJScrollPane = new JScrollPane();
			topJScrollPane.setViewportView(getTopJTable());
		}
		return topJScrollPane;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(5);
			jSplitPane.setDividerLocation(250);
			jSplitPane.setTopComponent(getTopJPanel());
			jSplitPane.setBottomComponent(getDownJPanel());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getToolJPanel());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getToolJPanel() {
		if (toolJPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(5, 3, 55, 25));
			jLabel.setText("物料类别");
			toolJPanel = new JPanel();
			toolJPanel.setLayout(null);
			toolJPanel.setPreferredSize(new Dimension(1, 30));
			toolJPanel.add(getCbbMaterielType(), null);
			toolJPanel.add(jLabel, null);
			toolJPanel.add(getJButton(), null);
			toolJPanel.add(getBtnWriteBack(), null);
			toolJPanel.add(getCbSelectAll(), null);
			toolJPanel.add(getBtnExit(), null);
			toolJPanel.add(getCbSelectNotAll(), null);
		}
		return toolJPanel;
	}

	private void initTopTable(List list) {
		if (list == null) {
			list = new Vector();
		}
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 30));
				list.add(addColumn("料号", "innerMergeData.materiel.ptNo", 100));
				list.add(addColumn("10位商品编码",
						"innerMergeData.hsAfterComplex.code", 100));
				list.add(addColumn("商品名称.规格,型号",
						"innerMergeData.hsBeforeMaterielNameSpec", 150));
				list.add(addColumn("法定单位",
						"innerMergeData.hsAfterLegalUnit.name", 50));
				list.add(addColumn("企业单位",
						"innerMergeData.materiel.calUnit.name", 50));
				list.add(addColumn("备案序号", "innerMergeData.hsAfterTenMemoNo",
						50, Integer.class));
				list.add(addColumn("10位商品编码",
						"innerMergeData.hsAfterComplex.code", 100));
				list.add(addColumn("商品名称.规格,型号",
						"innerMergeData.hsAfterMaterielNameSpec", 120));
				list.add(addColumn("法定单位",
						"innerMergeData.hsAfterLegalUnit.name", 50));
				list.add(addColumn("备案单位",
						"innerMergeData.hsAfterMemoUnit.name", 50));
				list.add(addColumn("4位编码序号", "innerMergeData.hsFourNo", 70,
						Integer.class));
				list.add(addColumn("4位商品名称",
						"innerMergeData.hsFourMaterielName", 120));
				list.add(addColumn("4位商品编码", "innerMergeData.hsFourCode", 70));
				return list;
			}
		};
		
		
//		topTableModel = (AttributiveCellTableModel) new AttributiveCellTableModel(
//				(MultiSpanCellTable) topJTable, dataSource, topAdapter);
		
		jTableListModelAdapter.setEditableColumn(1);
		
		topTableModel = new JTableListModel(topJTable, list, jTableListModelAdapter);
		
		topJTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		topJTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		
		TableColumnModel cm = topJTable.getColumnModel();
		ColumnGroup gBefore = new ColumnGroup("归并前");
		gBefore.add(cm.getColumn(2));
		gBefore.add(cm.getColumn(3));
		gBefore.add(cm.getColumn(4));
		gBefore.add(cm.getColumn(5));
		gBefore.add(cm.getColumn(6));

		ColumnGroup gAfter = new ColumnGroup("归并后");
		gAfter.add(cm.getColumn(7));
		gAfter.add(cm.getColumn(8));
		gAfter.add(cm.getColumn(9));
		gAfter.add(cm.getColumn(10));
		gAfter.add(cm.getColumn(11));

		GroupableTableHeader header = (GroupableTableHeader) topJTable
				.getTableHeader();
		header.addColumnGroup(gBefore);
		header.addColumnGroup(gAfter);


		
//		return topTableModel;

	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				value = false;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				checkBox.setBackground(table.getBackground());
				if (isSelected) {
					checkBox.setForeground(table.getSelectionForeground());
					checkBox.setBackground(table.getSelectionBackground());
				} else {
					checkBox.setForeground(table.getForeground());
					checkBox.setBackground(table.getBackground());
				}
				return checkBox;
			}
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value != null) {
				// return null;
				if (Boolean.valueOf(value.toString()) instanceof Boolean) {
					cb = new JCheckBox();
					cb.setSelected(Boolean.valueOf(value.toString())
							.booleanValue());
				}
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof TempInnerMergeData) {
				TempInnerMergeData temp = (TempInnerMergeData) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterielType() {
		if (cbbMaterielType == null) {
			cbbMaterielType = new JComboBox();
			cbbMaterielType.setBounds(61, 3, 146, 25);
			cbbMaterielType.addItem(new ItemProperty(
					MaterielType.FINISHED_PRODUCT, "成品"));
			// cbbMaterielType.addItem(new ItemProperty(
			// MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
			cbbMaterielType.addItem(new ItemProperty(MaterielType.MATERIEL,
					"料件"));
			// cbbMaterielType
			// .addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
			// cbbMaterielType.addItem(new ItemProperty(
			// MaterielType.REMAIN_MATERIEL, "边角料"));
			// cbbMaterielType.addItem(new
			// ItemProperty(MaterielType.BAD_PRODUCT,
			// "残次品"));
		}
		return cbbMaterielType;
	}

	private JTableListModel initDownTable(List dataSource) {
		downTableModel = new AttributiveCellTableModel(
				(MultiSpanCellTable) downJTable, dataSource,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料号", "materiel.ptNo", 100));
						list.add(addColumn("10位商品编码", "hsAfterComplex.code",
								100));
						list.add(addColumn("商品名称.规格,型号",
								"hsBeforeMaterielNameSpec", 150));
						list
								.add(addColumn("法定单位", "hsAfterLegalUnit.name",
										50));
						list
								.add(addColumn("企业单位", "materiel.calUnit.name",
										50));
						list.add(addColumn("备案序号", "hsAfterTenMemoNo", 50,
								Integer.class));
						list.add(addColumn("10位商品编码", "hsAfterComplex.code",
								100));
						list.add(addColumn("商品名称.规格,型号",
								"hsAfterMaterielNameSpec", 120));
						list
								.add(addColumn("法定单位", "hsAfterLegalUnit.name",
										50));
						list.add(addColumn("备案单位", "hsAfterMemoUnit.name", 50));
						list.add(addColumn("4位编码序号", "hsFourNo", 70,
								Integer.class));
						list
								.add(addColumn("4位商品名称", "hsFourMaterielName",
										120));
						list.add(addColumn("4位商品编码", "hsFourCode", 70));
						return list;
					}
				});

		TableColumnModel cm = downJTable.getColumnModel();

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

		GroupableTableHeader header = (GroupableTableHeader) downJTable
				.getTableHeader();
		header.addColumnGroup(gBefore);
		header.addColumnGroup(gAfter);
		return downTableModel;
	}

	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbSelectAll() {
		if (cbSelectAll == null) {
			cbSelectAll = new JCheckBox();
			cbSelectAll.setText("全选");
			cbSelectAll.setBounds(new Rectangle(316, 2, 60, 24));
			cbSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);

				}
			});
		}
		return cbSelectAll;
	}

	/**
	 * 全选或取消全选
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private void selectAll(boolean isSelected) {
		if (this.topTableModel == null) {
			return;
		}
		List list = topTableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempInnerMergeData) {
				TempInnerMergeData t = (TempInnerMergeData) list.get(i);
				t.setIsSelected(new Boolean(isSelected));
			}
		}
		topTableModel.updateRows(list);
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
			btnExit.setBounds(new Rectangle(610, 1, 58, 24));
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
	private JPanel getDownJPanel() {
		if (downJPanel == null) {
			downJPanel = new JPanel();
			downJPanel.setLayout(new BorderLayout());
			downJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "十码未备案",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			downJPanel.add(getDownJScrollPane(), java.awt.BorderLayout.CENTER);

		}
		return downJPanel;
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
	private JPanel getTopJPanel() {
		if (topJPanel == null) {
			topJPanel = new JPanel();
			topJPanel.setLayout(new BorderLayout());
			topJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "十码对应归并前未备案",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					new java.awt.Color(51, 51, 51)));
			topJPanel.add(getTopJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return topJPanel;
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
	public List getDownDataSource() {
		String materielType = ((ItemProperty) cbbMaterielType.getSelectedItem())
				.getCode();
		if (materielType == null) {
			return new ArrayList();
		}
		downDataSource = commonBaseCodeAction.findInnerMergeDataByTypeNoMerger(
				new Request(CommonVars.getCurrUser()), materielType);
		return downDataSource;
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
	public List getTopDataSource() {
		String materielType = ((ItemProperty) cbbMaterielType.getSelectedItem())
				.getCode();
		System.out.println("materielType:" + materielType);
		if (materielType == null) {
			return new ArrayList();
		}
		topDataSource = commonBaseCodeAction
				.findInnerMergeDataByTypeSomeInMerger(new Request(CommonVars
						.getCurrUser()), materielType);
		return topDataSource;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(210, 3, 84, 24));
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					new find().start();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnWriteBack() {
		if (btnWriteBack == null) {
			btnWriteBack = new JButton();
			btnWriteBack.setBounds(new Rectangle(444, 1, 146, 24));
			btnWriteBack.setText("添加到归并关系管理");
			btnWriteBack.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materielType = ((ItemProperty) cbbMaterielType
							.getSelectedItem()).getCode();
					if (materielType == null) {
						return;
					}

					List resultList = new ArrayList();
					List list = topTableModel.getList();
					for (int i = 0; i < list.size(); i++) {
						TempInnerMergeData temp = (TempInnerMergeData) list
								.get(i);
						if (temp.getIsSelected()) {
							resultList.add(temp);
						}
					}
					if (resultList.size() <= 0) {
						CommonProgress.closeProgressDialog();
						JOptionPane
								.showMessageDialog(DgInnerMergeNoMerger.this,
										"没有可添加的数据！", "提示", 2);
						return;
					}
					CommonProgress
							.showProgressDialog(DgInnerMergeNoMerger.this);
					CommonProgress.setMessage("系统正在更新数据，请稍后...");
					commonBaseCodeAction.writeBackEmsEdiMergerExgImgBefore(
							new Request(CommonVars.getCurrUser()),
							materielType, resultList);
					topTableModel.deleteRows(resultList);
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(DgInnerMergeNoMerger.this,
							"更新数据成功：！", "提示", 2);
//					initTopTable(list);
					initDownTable(getDownDataSource());
				}
			});
		}
		return btnWriteBack;
	}

	class find extends Thread {
		public void run() {
			List downList = new Vector();
			List topList = new Vector();
			try {
				CommonProgress.showProgressDialog(DgInnerMergeNoMerger.this);
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				downList = getDownDataSource();
				// System.out.println("downList.size:"
				// + (downList != null ? String.valueOf(downList.size())
				// : ""));
				topList = getTopDataSource();
				// System.out.println("topList.size:"
				// + (topList != null ? String.valueOf(topList.size())
				// : ""));
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgInnerMergeNoMerger.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				initTopTable(topList);
				initDownTable(downList);
			}
		}
	}

	/**
	 * This method initializes cbSelectNotAll
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbSelectNotAll() {
		if (cbSelectNotAll == null) {
			cbSelectNotAll = new JCheckBox();
			cbSelectNotAll.setBounds(new Rectangle(376, 1, 49, 26));
			cbSelectNotAll.setText("全否");
			cbSelectNotAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectAll(false);
						}
					});
		}
		return cbSelectNotAll;
	}
}
