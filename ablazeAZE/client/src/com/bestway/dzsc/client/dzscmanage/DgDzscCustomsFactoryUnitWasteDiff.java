package com.bestway.dzsc.client.dzscmanage;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JOptionPane;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.TempDzscProductVersionInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgDzscCustomsFactoryUnitWasteDiff extends JDialogBase {

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JTable tbUnitWaste = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnCalUnitWaste = null;

	private JButton btnRefresh = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbBomExg = null;

	private DzscAction dzscAction = null;

	private JTableListModel tableModelBomBill = null;

	private JTableListModel tableModelExg = null;

	private DzscEmsExgBill exgBill = null;

	private NumberFormatter numberFormatter = null;

	private DefaultFormatterFactory defaultFormatterFactory = null;

	public DzscEmsExgBill getExgBill() {
		return exgBill;
	}

	public void setExgBill(DzscEmsExgBill exgBill) {
		this.exgBill = exgBill;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscCustomsFactoryUnitWasteDiff() {
		super();
		initialize();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(651, 446));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("BOM单耗差异");
		this.setContentPane(getJContentPane());

	}

	public void setVisible(boolean b) {
		if (b) {
			showExgData();
			initTableBomBill(new ArrayList());
		}
		super.setVisible(b);
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
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
			jSplitPane.setDividerSize(2);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerLocation(180);
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
			jPanel.add(getJJToolBarBar(), BorderLayout.SOUTH);
			jPanel.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbUnitWaste());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbUnitWaste() {
		if (tbUnitWaste == null) {
			tbUnitWaste = new JTable();
		}
		return tbUnitWaste;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnRefresh());
			jJToolBarBar.add(getBtnCalUnitWaste());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCalUnitWaste() {
		if (btnCalUnitWaste == null) {
			btnCalUnitWaste = new JButton();
			btnCalUnitWaste.setText("计算单耗差异");
			btnCalUnitWaste
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tbBomExg.getCellEditor() != null) {
								tbBomExg.getCellEditor().stopCellEditing();
							}
							if (tableModelExg == null
									|| tableModelExg.getList().size() <= 0) {
								JOptionPane.showMessageDialog(
										DgDzscCustomsFactoryUnitWasteDiff.this,
										"没有成品资料,所以不能计算单耗差异", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							List lsExg = tableModelExg.getList();
							Double totalAmount = 0.0;
							for (int i = 0; i < lsExg.size(); i++) {
								TempDzscProductVersionInfo info = (TempDzscProductVersionInfo) lsExg
										.get(i);
								totalAmount += (info.getAmount() == null ? 0.0
										: info.getAmount());
							}
							if (!totalAmount.equals(exgBill.getAmount())) {
								JOptionPane.showMessageDialog(
										DgDzscCustomsFactoryUnitWasteDiff.this,
										"每个成品分配的数量总和" + totalAmount
												+ "\n不等于通关备案成品的总数量"
												+ exgBill.getAmount(), "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							List list = dzscAction
									.findDzscCustomsFactoryUnitWasteDiff(new Request(
											CommonVars.getCurrUser(), true),
											lsExg, exgBill);
							initTableBomBill(list);
						}
					});
		}
		return btnCalUnitWaste;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新成品资料");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showExgData();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbBomExg());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBomExg() {
		if (tbBomExg == null) {
			tbBomExg = new JTable();
		}
		return tbBomExg;
	}

	private void initTableExgBill(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("成品料号", "parentNo", 150));
				list.add(addColumn("版本号", "versionNo", 80));
				list.add(addColumn("开始日期", "beginDate", 100));
				list.add(addColumn("结束日期", "endDate", 100));
				list.add(addColumn("分配数量", "amount", 100));
				return list;
			}
		};
		tableModelExg = new JTableListModel(tbBomExg, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		jTableListModelAdapter.setEditableColumn(5);
		tableModelExg.setAllowSetValue(true);
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
		tbBomExg.getColumnModel().getColumn(5).setCellEditor(
				new JTextFieldEditor(tf));
		tbBomExg.getColumnModel().getColumn(5).setCellRenderer(
				new ForcedEditTableCellRenderer());
		tbBomExg.setRowHeight(20);
	}
	
	private void commitValueChange(final JFormattedTextField tf) {
		try {
			tf.commitEdit();
		} catch (Exception ex) {
		}		
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

	private void initTableBomBill(List list) {
		tableModelBomBill = new JTableListModel(tbUnitWaste, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料件序号", "imgSeqNum", 50,Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("料件名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("单耗(1)", "customsUnitWare", 70));
						list.add(addColumn("工厂单耗(2)", "factoryUnitWare", 70));
						list.add(addColumn("单耗差异=(1)-(2)", "unitWareDiff", 100));
						list.add(addColumn("损耗(3)", "customsWare", 70));						
						list.add(addColumn("工厂损耗(4)", "factoryWare", 70));						
						list.add(addColumn("损耗差异=(3)-(4)", "wareDiff", 100));
						return list;
					}
				});
	}

	private void showExgData() {
		List list = this.dzscAction.findMaterialExgByEmsExg(new Request(
				CommonVars.getCurrUser(), true), exgBill);
		initTableExgBill(list);
	}

	/**
	 * 编辑列
	 */
	class JTextFieldEditor extends DefaultCellEditor implements KeyListener,
			FocusListener {
		private JFormattedTextField tf;

		private JTable table = null;

		// private Object editBeforeValue = "";

		public JTextFieldEditor(JFormattedTextField tf) {
			super(tf);
			this.tf = tf;
			// this.tf.setBorder(null);
			this.setClickCountToStart(1);
			this.tf.addKeyListener(this);
			this.tf.addFocusListener(this);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value != null) {
				// tf.setText((String) value);
				tf.setValue(Double.parseDouble(value.toString()));
			}

			this.table = table;
			// tf.addKeyListener(this);
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
			JTableListModel tableModel = (JTableListModel) table.getModel();
			TempDzscProductVersionInfo info = (TempDzscProductVersionInfo) tableModel
					.getCurrentRow();
			// tf.setText(icd.getWhiteBillNo());
			tf.setValue(info.getAmount());
			// editBeforeValue = tf.getValue();
			tf.selectAll();
		}

		public void keyTyped(KeyEvent e) {

		}

		public void keyPressed(KeyEvent e) {
//			if (e.getKeyCode() == 10 || e.getKeyCode() == 40
//					|| e.getKeyCode() == 38) {
//				JTableListModel tableModel = (JTableListModel) this.table
//						.getModel();
//				if (e.getKeyCode() == 10) {
//					if (tableModel.hasNextRow()) {
//						tableModel.nextRow();
//					}
//				}
//			}
		}

		public void keyReleased(KeyEvent e) {
		}

		public void focusLost(FocusEvent e) {
			if (table == null || table.getModel() == null) {
				return;
			}
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
