package com.bestway.client.fixtureonorder;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.fixasset.entity.ChangeLocaOptionParam;
import com.bestway.fixtureonorder.action.FixtureContractAction;
import com.bestway.fixtureonorder.entity.FixtureLocation;
import com.bestway.fixtureonorder.entity.TempFixtureLocationChangeInfo;

/**
 * @author fhz
 * 
 */
public class PnChangeFixture extends JPanel {

	private static final long serialVersionUID = 1L;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnSave = null;

	private JButton btnDelete = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JTableListModel tableModel;

	private FixtureContractAction fixtureContractAction;

	private FixtureLocation location;

	private NumberFormatter numberFormatter = null;

	private DefaultFormatterFactory defaultFormatterFactory = null;

	private List contralDataList = new ArrayList(); // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public PnChangeFixture() {
		super();
		initialize();
	}

	/**
	 * This is the default constructor
	 */
	public PnChangeFixture(List list, FixtureLocation location,
			Integer changeType) {
		super();
		initialize();
		fixtureContractAction = (FixtureContractAction) CommonVars
				.getApplicationContext().getBean("fixtureContractAction");
		List lsChangeInfo = new ArrayList();
		btnAdd.setEnabled(true);
		if (changeType == ChangeLocaOptionParam.CUSTOMS_IN_FACT) {
			btnAdd.setEnabled(false);
			lsChangeInfo = fixtureContractAction
					.getFixtureLocationChangeInfoFromCustomsBill(new Request(
							CommonVars.getCurrUser()), list, location,
							changeType);
		} else if (changeType == ChangeLocaOptionParam.FACT_CHANGE_LOCATION) {
			btnAdd.setEnabled(false);
			lsChangeInfo = fixtureContractAction
					.getFixtureLocationChangeInfoFromResultInfo(new Request(
							CommonVars.getCurrUser()), location, changeType);
		} else if (changeType == ChangeLocaOptionParam.FACT_SUBTRACT) {
			btnAdd.setEnabled(false);
			lsChangeInfo = fixtureContractAction
					.getFixtureLocationChangeInfoFromResultInfo(new Request(
							CommonVars.getCurrUser()), location, changeType);
		}
		this.location = location;
		contralDataList.addAll(lsChangeInfo);
		initTable(lsChangeInfo);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(new BorderLayout());
		this.add(getJToolBar(), BorderLayout.SOUTH);
		this.add(getJScrollPane(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnDelete());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("增加");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = fixtureContractAction
							.getFixtureLocationChangeInfoFromFactory(
									new Request(CommonVars.getCurrUser()),
									new ArrayList(), location, null);
					tableModel.addRows(list);
					contralDataList.addAll(list);
				}
			});
		}
		return btnAdd;
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
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(PnChangeFixture.this,
								"请选择要删除的资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
					tableModel.deleteRows(tableModel.getCurrentRows());
					contralDataList.removeAll(tableModel.getCurrentRows());
				}
			});
		}
		return btnDelete;
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
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	private JTableListModel initTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("报关单号", "customsDeclarationCode", 100));
				list.add(addColumn("进口日期 ", "impExpDate", 100));
				list.add(addColumn("设备名称", "commName", 100));
				list.add(addColumn("规格", "commSpec", 100));
				list.add(addColumn("报关单数量", "amount", 100));
				list.add(addColumn("设备性质", "fixKind", 100));
//				list.add(addColumn("加入位置数量", "locationAmount", 100));
				list.add(addColumn("报关单项号", "customsDeclaItemNo", 100));
				list.add(addColumn("商品编码", "complex.code", 100));
				list.add(addColumn("手册号", "emsNo", 100));
				list.add(addColumn("协议号", "agreementNo", 100));
				list.add(addColumn("报关单流水号", "customsBillSeqNo", 100));
				list.add(addColumn("异动类型", "changeType", 100));
				list.add(addColumn("原位置", "oldLocation.name", 100));
				list.add(addColumn("目标位置", "newLocation.name", 100));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText(change(value));
						return this;
					}
				
				});
//		tableModel.setExcelFileName("设备数量维护");
//		jTableListModelAdapter.setEditableColumn(6);
//		tableModel.setAllowSetValue(true);
//		final JFormattedTextField tf = new JFormattedTextField();
//		tf.setFormatterFactory(getDefaultFormatterFactory());
//		tf.addFocusListener(new FocusAdapter() {
//			public void focusGained(final FocusEvent e) {
//				SwingUtilities.invokeLater(new Runnable() {
//					public void run() {
//						((JTextField) e.getSource()).selectAll();
//					}
//				});
//			}
//
//			public void focusLost(FocusEvent e) {
//
//			}
//		});
//		tf.getDocument().addDocumentListener(new DocumentListener() {
//			public void changedUpdate(DocumentEvent e) {
//			}
//
//			public void insertUpdate(DocumentEvent e) {
//				taxationDrawbackValueChange(tf);
//			}
//
//			public void removeUpdate(DocumentEvent e) {
//				taxationDrawbackValueChange(tf);
//			}
//		});
//		jTable.getColumnModel().getColumn(6).setCellEditor(
//				new JTextFieldEditor(tf));
//		jTable.getColumnModel().getColumn(6).setCellRenderer(
//				new DouForcedEditTableCellRenderer());

		return tableModel;
	}

	private String change(Object value){
		if(value==null)
			return "";
		else
			return Integer.valueOf(value.toString())==0?"不作价设备":"借用设备";
			
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
			if (value != null) {
				tf.setValue(Double.parseDouble(value.toString()));
			}

			this.table = table;
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
			taxationDrawbackValueChange(this.tf);

		}
	}

	class DouForcedEditTableCellRenderer extends DefaultTableCellRenderer {

		/** Creates a new instance of ForcedEditTableCellRenderer */
		public DouForcedEditTableCellRenderer() {
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

	private void taxationDrawbackValueChange(final JFormattedTextField tf) {
		try {
			tf.commitEdit();
		} catch (Exception ex) {
		}
		if (tableModel == null || tableModel.getCurrentRow() == null) {
			return;
		}
		TempFixtureLocationChangeInfo detail = (TempFixtureLocationChangeInfo) tableModel
				.getCurrentRow();
		double zsl = (detail.getLocationAmount() == null ? 0.0 : detail
				.getLocationAmount());

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

	public List getChangeFixture() {
		if (tableModel == null) {
			return new ArrayList();
		} else {
//			System.out.println("----------------");
//			System.out.println(tableModel.getList().size());
			return tableModel.getCurrentRows();
		}
	}

	/**
	 * @return the contralDataList
	 */
	public List getContralDataList() {
		return contralDataList;
	}

}
