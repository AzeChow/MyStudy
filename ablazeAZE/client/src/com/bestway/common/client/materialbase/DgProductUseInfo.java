package com.bestway.common.client.materialbase;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.EmptyProductMaterial;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.ui.winuicontrol.JDialogBase;

@SuppressWarnings("unchecked")
public class DgProductUseInfo extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane = null;

	private JTable tbMaterial = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbUnitWaste = null;

	private JTableListModel tableModelMaterial = null;

	private JTableListModel tableModelUnitWaste = null;

	private PnCommonQueryPage pnCommonQueryPage = null;

	private MaterialManageAction materialManageAction = null; // @jve:decl-index=0:

	private NumberFormatter numberFormatter = null;

	private DefaultFormatterFactory defaultFormatterFactory = null;

	private JToolBar jToolBar1 = null;

	private JButton btnSave = null;

	private JButton btnUndo = null;

	private JButton btnClose = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgProductUseInfo() {
		super();
		initialize();
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			pnCommonQueryPage.setInitState();
			showUnitWaste();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(707, 502));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("成品使用情况");
		this.setContentPane(getJContentPane());
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
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
			jToolBar.add(getPnCommonQueryPage());
		}
		return jToolBar;
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
					JTableListModel tableListModel = DgProductUseInfo.this
							.initTable(dataSource);
					showUnitWaste();
					return tableListModel;
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return DgProductUseInfo.this.getDataSource(index,
							length, property, value, isLike);
				}

			};
		}
		return pnCommonQueryPage;
	}

	private JTableListModel initTable(final List list) {
		tableModelMaterial = new JTableListModel(tbMaterial, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("类别", "scmCoi.name", 80));
						list.add(addColumn("料号", "ptNo", 100));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("工厂商品名称", "factoryName", 100));
						list.add(addColumn("工厂型号规格", "factorySpec", 100));
						// list.add(addColumn("商品名称", "ptName", 100));
						// list.add(addColumn("型号规格", "ptSpec", 100));
						list.add(addColumn("工厂单位", "calUnit.name", 50));
						list.add(addColumn("报关单位", "ptUnit.name", 50));
						list.add(addColumn("单价", "ptPrice", 50));
						list.add(addColumn("净重", "ptNetWeight", 50));
						list.add(addColumn("材料来源", "materialSource", 100));
						list.add(addColumn("报关助记码", "customsNo", 80));
						return list;
					}
				});
		return tableModelMaterial;

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
		if (materialManageAction == null) {
			return new ArrayList();
		}
		return materialManageAction.findEnterpriseMaterialMaterielAndProduct(new Request(
				CommonVars.getCurrUser()), index, length,
				property, value, isLike);
	}

	private JTableListModel initUnitWasteTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("BOM版本", "versionNo", 50));//1
				list.add(addColumn("子料号", "compentNo", 70));//2
				list.add(addColumn("子料名称", "compentName", 100));//3
				list.add(addColumn("子料规格", "compentSpec", 100));//4
				list.add(addColumn("单耗", "unitWare", 70));//5
				list.add(addColumn("损耗", "ware", 80));//6
				list.add(addColumn("单项用量", "unitDosage", 80));//7
				
				list.add(addColumn("父料号", "ptNo", 80));//8
				list.add(addColumn("料别", "scmCoiName", 70));//9
				list.add(addColumn("父料名称","ptName", 100));//10
				list.add(addColumn("父料规格","ptSpec", 100));//11
				list.add(addColumn("父料单位","calUnitName", 100));//12
				return list;
			}
		};
		tableModelUnitWaste = new JTableListModel(tbUnitWaste, list,
							jTableListModelAdapter,ListSelectionModel.SINGLE_SELECTION);
		
		//以下为wss所加，2010.4.13
		jTableListModelAdapter.setEditableColumn(5);
		jTableListModelAdapter.setEditableColumn(6);
		tableModelUnitWaste.setAllowSetValue(true);
		final JFormattedTextField tf = new JFormattedTextField();
		tf.setFormatterFactory(getDefaultFormatterFactory());
		tf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(final FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						((JTextField) e.getSource()).selectAll();
					}
				});
			}

			@Override
			public void focusLost(FocusEvent e) {

			}
		});
		tf.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			}

			public void insertUpdate(DocumentEvent e) {
				commitValueChange(tf);
			}

			public void removeUpdate(DocumentEvent e) {
				commitValueChange(tf);
			}
		});
		tbUnitWaste.getColumnModel().getColumn(5).setCellEditor(
				new JTextFieldEditor(tf));
		tbUnitWaste.getColumnModel().getColumn(5).setCellRenderer(
				new ForcedEditTableCellRenderer());
		tbUnitWaste.getColumnModel().getColumn(6).setCellEditor(
				new JTextFieldEditor(tf));
		tbUnitWaste.getColumnModel().getColumn(6).setCellRenderer(
				new ForcedEditTableCellRenderer());
		tbUnitWaste.setRowHeight(20);
		//wss2010.04.13所加至此
		return tableModelUnitWaste;
	}

	private void commitValueChange(final JFormattedTextField tf) {
		try {
			tf.commitEdit();
		} catch (Exception ex) {
		}
		if (tableModelUnitWaste == null
				|| tableModelUnitWaste.getCurrentRow() == null) {
			return;
		}
		//wss2010.04.13有修改
		EmptyProductMaterial detail = (EmptyProductMaterial) tableModelUnitWaste
				.getCurrentRow();
		//单耗
		double unitWaste = (detail.getUnitWare() == null ? 0.0 : detail
				.getUnitWare());
		//损耗
		double waste = (detail.getWare() == null ? 0.0 : detail.getWare());
		if (waste >= 1 || waste < 0) {
			detail.setWare(0.0);
			waste = 0.0;
		}
		double unitUsed = CommonVars.getDoubleByDigit(unitWaste
				/ (1 - waste ), 5);
		detail.setUnitDosage(unitUsed);
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

	private void showUnitWaste() {
		List list = new ArrayList();
		if (tableModelMaterial != null) {
			EnterpriseMaterial materiel = (EnterpriseMaterial) tableModelMaterial.getCurrentRow();
			if (materiel != null) {
				System.out.println("执行......");
			    System.out.println("pts="+materiel.getPtNo());
				list = materialManageAction.findEnterpriseBomManageByDetail(
						new Request(CommonVars.getCurrUser()), materiel);
				System.out.println("size="+list.size());
			}
		}
		initUnitWasteTable(list);
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
			jSplitPane.setDividerLocation(200);
			jSplitPane.setTopComponent(getJScrollPane());
			jSplitPane.setBottomComponent(getJPanel());
			jSplitPane.setDividerSize(6);
		}
		return jSplitPane;
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
		if (tbMaterial == null) {
			tbMaterial = new JTable();
			tbMaterial.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							showUnitWaste();
						}
					});
		}
		return tbMaterial;
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
			jPanel.add(getJToolBar1(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (tbUnitWaste == null) {
			tbUnitWaste = new JTable();
		}
		return tbUnitWaste;
	}

	/**
	 * 编辑列
	 */
	class JTextFieldEditor extends DefaultCellEditor implements KeyListener,
			FocusListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private JFormattedTextField tf;

		private JTable table = null;

		public JTextFieldEditor(JFormattedTextField tf) {
			super(tf);
			this.tf = tf;
			this.setClickCountToStart(1);
			this.tf.addKeyListener(this);
			this.tf.addFocusListener(this);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value != null) {
				tf.setValue(Double.parseDouble(value.toString()));
			}

			this.table = table;
			return tf;
		}

		@Override
		public Object getCellEditorValue() {
			if (tf.getValue() == null) {
				return 0.0;
			} else {
				return Double.valueOf(tf.getValue().toString());
			}
		}

		public void focusGained(FocusEvent e) {

		}

		public void keyTyped(KeyEvent e) {

		}

		public void keyPressed(KeyEvent e) {

		}

		public void keyReleased(KeyEvent e) {
		}

		public void focusLost(FocusEvent e) {
//			if (table == null || table.getModel() == null) {
//				return;
//			}
//			// if (tf.getValue() != null) {
//			// double waste = Double.parseDouble(tf.getValue().toString());
//			// if (waste >= 1 || waste < 0) {
//			// JOptionPane.showMessageDialog(
//			// DgEditMaterialBomUnitWaste.this, "损耗不能大于等于1或者小于0",
//			// "提示", JOptionPane.INFORMATION_MESSAGE);
//			// // tf.setValue(0.0);
//			// tf.requestFocus();
//			// return;
//			// }
//			// }
//			JTableListModel tableModel = (JTableListModel) this.table
//					.getModel();
//			MaterialBomDetail detail = (MaterialBomDetail) tableModel
//					.getCurrentRow();
//			double unitWaste = (detail.getUnitWaste() == null ? 0.0 : detail
//					.getUnitWaste());
//			double waste = (detail.getWaste() == null ? 0.0 : detail.getWaste());
//			if (waste >= 1 || waste < 0) {
//				// JOptionPane.showMessageDialog(DgEditBomApplyUnitWaste.this,
//				// "损耗不能大于等于100或者小于0", "提示",
//				// JOptionPane.INFORMATION_MESSAGE);
//				tf.setValue(0.0);
//				waste = 0.0;
//			}
//			double unitUsed = CommonVars.getDoubleByDigit(unitWaste
//					/ (1 - waste), 5);
//			detail.setUnitUsed(unitUsed);
			commitValueChange(tf);
		}
	}

	public class ForcedEditTableCellRenderer extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/** Creates a new instance of ForcedEditTableCellRenderer */
		public ForcedEditTableCellRenderer() {
			super();
		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// Obtain the default component.
			Component comp = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);

			if (hasFocus && isSelected) {
				TableModel tblModel = table.getModel();
				if (tblModel.isCellEditable(row, column)) {
					// Cell is editable
					table.editCellAt(row, column);
					table.getEditorComponent().requestFocus();
					JFormattedTextField tf = (JFormattedTextField) table
							.getEditorComponent();
					if (value == null) {
						tf.setValue(0.0);
					} else {
						tf.setValue(Double.parseDouble(value.toString()));
					}
					tf.selectAll();
				}
			}
			return comp;
		}

	}

	/**
	 * This method initializes jToolBar1	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnSave());
			jToolBar1.add(getBtnUndo());
			jToolBar1.add(getBtnClose());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes btnSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("\u4fdd\u5b58");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelUnitWaste != null) {
						if (tbUnitWaste.getCellEditor() != null) {
							tbUnitWaste.getCellEditor().stopCellEditing();
						}
						List list = tableModelUnitWaste.getList();
						for (int i = 0; i < list.size(); i++) {
							EmptyProductMaterial detail = (EmptyProductMaterial) list
									.get(i);
							detail.setUnitDosage(detail.getUnitWare()/(1-detail.getWare()));
							List bomManagerList = materialManageAction.
											findEnterpriseBomManagerById(detail.getBomManagerId());
							if(bomManagerList != null &&  bomManagerList.size()>0){
								EnterpriseBomManage bom = (EnterpriseBomManage)bomManagerList.get(0);
								bom.setUnitWare(detail.getUnitWare());
								bom.setWare(detail.getWare());
								bom.setUnitDosage(detail.getUnitDosage());
								materialManageAction.saveEnterpriseBomManage(new Request(CommonVars.getCurrUser()), bom);
							}
							
						}
					}
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnUndo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("\u53d6\u6d88");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showUnitWaste();
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes btnClose	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("\u5173\u95ed");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
