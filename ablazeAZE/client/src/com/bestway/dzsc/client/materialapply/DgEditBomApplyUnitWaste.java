package com.bestway.dzsc.client.materialapply;

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
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.dzsc.materialapply.entity.MaterialBomDetailApply;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgEditBomApplyUnitWaste extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane = null;

	private JTable tbMaterial = null;

	private JPanel jPanel = null;

	private JToolBar jToolBar1 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbUnitWaste = null;

	private JButton btnSave = null;

	private JButton btnUndo = null;

	private JButton btnClose = null;

	private JTableListModel tableModelMaterial = null;

	private JTableListModel tableModelUnitWaste = null;

	private PnCommonQueryPage pnCommonQueryPage = null;

	private MaterialApplyAction materialApplyAction = null; // @jve:decl-index=0:

	private NumberFormatter numberFormatter = null;

	private DefaultFormatterFactory defaultFormatterFactory = null;

	private boolean isChange = false;

	public boolean isChange() {
		return isChange;
	}

	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgEditBomApplyUnitWaste() {
		super();
		initialize();
	}

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
		this.setTitle("修改单耗");
		this.setContentPane(getJContentPane());
		materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
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

				@Override
				public JTableListModel initTable(List dataSource) {
					return DgEditBomApplyUnitWaste.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return DgEditBomApplyUnitWaste.this.getDataSource(index,
							length, property, value, isLike);
				}

			};
		}
		return pnCommonQueryPage;
	}

	private JTableListModel initTable(final List list) {
		tableModelMaterial = new JTableListModel(tbMaterial, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						// list.add(addColumn("类别", "scmCoi.name", 80));
						list.add(addColumn("料号", "ptNo", 100));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("工厂商品名称", "factoryName", 100));
						list.add(addColumn("工厂型号规格", "factorySpec", 100));
						list.add(addColumn("工厂单位", "calUnit.name", 70));
						list.add(addColumn("报关单位", "ptUnit.name", 70));
						list.add(addColumn("单价", "ptPrice", 80));
						list.add(addColumn("净重", "ptNetWeight", 80));
						return list;
					}
				});
		this.showUnitWaste();
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
		if (materialApplyAction == null) {
			return new ArrayList();
		}
		return materialApplyAction.findMaterialBomApplyMateriel(new Request(
				CommonVars.getCurrUser()), isChange, index, length, property,
				value, isLike);
	}

	private JTableListModel initUnitWasteTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("状态标志",
						"versionApply.bomMasterApply.stateMark", 60));
				list.add(addColumn("父料号",
						"versionApply.bomMasterApply.materiel.ptNo", 80));
				list
						.add(addColumn(
								"父料名称",
								"versionApply.bomMasterApply.materiel.factoryName",
								100));
				list.add(addColumn("BOM版本", "versionApply.version", 100));
				list.add(addColumn("子料号", "materiel.ptNo", 100));
				// list.add(addColumn("子料名称", "materiel.factoryName", 70));
				list.add(addColumn("单耗", "unitWaste", 70));
				list.add(addColumn("损耗%", "waste", 80));
				list.add(addColumn("单项用量", "unitUsed", 80));
				return list;
			}
		};
		tableModelUnitWaste = new JTableListModel(tbUnitWaste, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		TableColumn column = this.tbUnitWaste.getColumnModel().getColumn(1);
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
		jTableListModelAdapter.setEditableColumn(6);
		jTableListModelAdapter.setEditableColumn(7);
		tableModelUnitWaste.setAllowSetValue(true);
		final JFormattedTextField tf = new JFormattedTextField();
		tf.setFormatterFactory(getDefaultFormatterFactory());
		tf.addFocusListener(new FocusAdapter() {
			public void focusGained(final FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						((JTextField) e.getSource()).selectAll();
					}
				});
			}

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
		tbUnitWaste.getColumnModel().getColumn(6).setCellEditor(
				new JTextFieldEditor(tf));
		tbUnitWaste.getColumnModel().getColumn(6).setCellRenderer(
				new ForcedEditTableCellRenderer());
		tbUnitWaste.getColumnModel().getColumn(7).setCellEditor(
				new JTextFieldEditor(tf));
		tbUnitWaste.getColumnModel().getColumn(7).setCellRenderer(
				new ForcedEditTableCellRenderer());
		tbUnitWaste.setRowHeight(20);
		return tableModelUnitWaste;
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
			Materiel materiel = (Materiel) tableModelMaterial.getCurrentRow();
			if (materiel != null) {
				list = materialApplyAction.findMaterialBomApplyByDetail(
						new Request(CommonVars.getCurrUser()), isChange,
						materiel);
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
							JTableListModel tableModel = (JTableListModel) tbMaterial
									.getModel();
							if (tableModel == null) {
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
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getJButton());
			jToolBar1.add(getJButton1());
			jToolBar1.add(getJButton2());
		}
		return jToolBar1;
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelUnitWaste != null) {
						if (tbUnitWaste.getCellEditor() != null) {
							tbUnitWaste.getCellEditor().stopCellEditing();
						}
						List list = tableModelUnitWaste.getList();
						for (int i = 0; i < list.size(); i++) {
							MaterialBomDetailApply detail = (MaterialBomDetailApply) list
									.get(i);
							materialApplyAction.saveMaterialBomDetailApply(
									new Request(CommonVars.getCurrUser()),
									detail);
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
	private JButton getJButton1() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showUnitWaste();
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
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
		MaterialBomDetailApply detail = (MaterialBomDetailApply) tableModelUnitWaste
				.getCurrentRow();
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

	/**
	 * 编辑列
	 */
	class JTextFieldEditor extends DefaultCellEditor implements KeyListener,
			FocusListener {
		private JFormattedTextField tf;

		private JTable table = null;

		public JTextFieldEditor(JFormattedTextField tf) {
			super(tf);
			this.tf = tf;
			this.setClickCountToStart(1);
			this.tf.addKeyListener(this);
			this.tf.addFocusListener(this);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			this.table = table;
			if (value != null) {
				tf.setValue(Double.parseDouble(value.toString()));
			}
			MaterialBomDetailApply detail = (MaterialBomDetailApply) tableModelUnitWaste
					.getDataByRow(row);
			String stateMark = detail.getVersionApply().getBomMasterApply()
					.getStateMark();
			if (DzscState.ORIGINAL.equals(stateMark)
					|| DzscState.CHANGE.equals(stateMark)) {
				tf.setEditable(true);
			} else {
				tf.setEditable(false);
			}
			return tf;
		}

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
			// if (table == null || table.getModel() == null) {
			// return;
			// }
			//
			// JTableListModel tableModel = (JTableListModel) this.table
			// .getModel();
			// MaterialBomDetailApply detail = (MaterialBomDetailApply)
			// tableModel
			// .getCurrentRow();
			// double unitWaste = (detail.getUnitWaste() == null ? 0.0 : detail
			// .getUnitWaste());
			// double waste = (detail.getWaste() == null ? 0.0 :
			// detail.getWaste());
			// if (waste >= 100 || waste < 0) {
			// // JOptionPane.showMessageDialog(DgEditBomApplyUnitWaste.this,
			// // "损耗不能大于等于100或者小于0", "提示",
			// // JOptionPane.INFORMATION_MESSAGE);
			// tf.setValue(0.0);
			// waste=0.0;
			// }
			// double unitUsed = CommonVars.getDoubleByDigit(unitWaste
			// / (1 - waste / 100.0), 5);
			// detail.setUnitUsed(unitUsed);
			commitValueChange(tf);
		}
	}

	public class ForcedEditTableCellRenderer extends DefaultTableCellRenderer {

		/** Creates a new instance of ForcedEditTableCellRenderer */
		public ForcedEditTableCellRenderer() {
			super();
		}

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
} // @jve:decl-index=0:visual-constraint="10,10"
