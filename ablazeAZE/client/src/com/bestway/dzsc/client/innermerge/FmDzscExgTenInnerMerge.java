package com.bestway.dzsc.client.innermerge;

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
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.dzsc.innermerge.action.DzscInnerMergeAction;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmDzscExgTenInnerMerge extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JTableListModel tableModel = null;

	private PnCommonQueryPage pnCommonQueryPage = null;

	private NumberFormatter numberFormatter = null;

	private DefaultFormatterFactory defaultFormatterFactory = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JButton btnSave = null;
	private JButton btnUndo = null;
	
	private DzscInnerMergeAction dzscInnerMergeAction = null;
	private JButton btnClose = null;
	/**
	 * This method initializes 
	 * 
	 */
	public FmDzscExgTenInnerMerge() {
		super();
		initialize();
		dzscInnerMergeAction = (DzscInnerMergeAction) CommonVars
		.getApplicationContext().getBean("dzscInnerMergeAction");
		dzscInnerMergeAction.checkFmDzscExgTenInnerMerge(new Request(CommonVars
				.getCurrUser()));
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(615, 405));
        this.setContentPane(getJContentPane());
        this.setTitle("成品加工费预设");
        this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
        	public void internalFrameOpened(javax.swing.event.InternalFrameEvent e) {
        		pnCommonQueryPage.setInitState();
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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
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
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnUndo());
			jToolBar.add(getPnCommonQueryPage());			
			jToolBar.add(getBtnClose());
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
					return FmDzscExgTenInnerMerge.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return FmDzscExgTenInnerMerge.this.getDataSource(index,
							length, property, value, isLike);
				}

			};
		}
		return pnCommonQueryPage;
	}

	private JTableListModel initTable(final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("备案序号", "tenSeqNum", 100,Integer.class));
				list.add(addColumn("商品编码", "tenComplex.code", 100));
				list.add(addColumn("商品名称", "tenPtName", 150));
				list.add(addColumn("型号规格", "tenPtSpec", 150));
				list.add(addColumn("报关单位", "tenUnit.name", 100));
				list.add(addColumn("加工费单价", "machinPrice", 100));
				list.add(addColumn("加工费比例", "machinScale", 100));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		jTableListModelAdapter.setEditableColumn(6);
		jTableListModelAdapter.setEditableColumn(7);
		tableModel.setAllowSetValue(true);
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
		
		final JFormattedTextField tf1 = new JFormattedTextField();
		tf1.setFormatterFactory(getDefaultFormatterFactory());
		tf1.addFocusListener(new FocusAdapter() {
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
		tf1.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			}

			public void insertUpdate(DocumentEvent e) {

				commitValueChange(tf1);
			}

			public void removeUpdate(DocumentEvent e) {
				commitValueChange(tf1);
			}
		});
		jTable.getColumnModel().getColumn(6).setCellEditor(
				new JTextFieldEditor(tf));
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new ForcedEditTableCellRenderer());
		jTable.getColumnModel().getColumn(7).setCellEditor(
				new JTextFieldEditor1(tf1));
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new ForcedEditTableCellRenderer());
		jTable.setRowHeight(20);
		return tableModel;
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
		if (dzscInnerMergeAction == null) {
			return new ArrayList();
		}
		// String materielType = scmCoiSelected.getCode();
		return dzscInnerMergeAction.findDzscExgTenInnerMerge(new Request(CommonVars
				.getCurrUser()), index, length, property, value, isLike);
		// return new ArrayList();
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
					List list=tableModel.getList();
					dzscInnerMergeAction.saveDzscTenInnerMerge(new Request(CommonVars
							.getCurrUser()), list);
					if(list.size()>0){
						JOptionPane.showMessageDialog(FmDzscExgTenInnerMerge.this, "保存成功！");
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
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					pnCommonQueryPage.setInitState();
				}
			});
		}
		return btnUndo;
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
			DzscTenInnerMerge info = (DzscTenInnerMerge) tableModel
					.getCurrentRow();
			// tf.setText(icd.getWhiteBillNo());
			tf.setValue(info.getMachinPrice());
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

	/**
	 * 编辑列
	 */
	class JTextFieldEditor1 extends DefaultCellEditor implements KeyListener,
			FocusListener {
		private JFormattedTextField tf;

		private JTable table = null;

		// private Object editBeforeValue = "";

		public JTextFieldEditor1(JFormattedTextField tf) {
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
			DzscTenInnerMerge info = (DzscTenInnerMerge) tableModel
					.getCurrentRow();
			// tf.setText(icd.getWhiteBillNo());
			tf.setValue(info.getMachinScale());
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
					// if (value == null) {
					// tf.setText("");
					// } else {
					// tf.setText(value.toString());
					// tf.selectAll();
					// }
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
					FmDzscExgTenInnerMerge.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
