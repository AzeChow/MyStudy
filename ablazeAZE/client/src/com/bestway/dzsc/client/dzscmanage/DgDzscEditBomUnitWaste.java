package com.bestway.dzsc.client.dzscmanage;

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
import com.bestway.bcus.client.common.tableeditor.JNumberForcedTableCellRenderer;
import com.bestway.bcus.client.common.tableeditor.JNumberTableCellEditor;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorEnableListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorParameter;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgDzscEditBomUnitWaste extends JDialogBase {

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

	private NumberFormatter numberFormatter = null;

	private DefaultFormatterFactory defaultFormatterFactory = null;

	private DzscEmsPorHead head = null; // 头

	private DzscAction dzscAction = null;

	public DzscEmsPorHead getHead() {
		return head;
	}

	public void setHead(DzscEmsPorHead head) {
		this.head = head;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscEditBomUnitWaste() {
		super();
		initialize();
	}

	public void setVisible(boolean b) {
		if (b) {
			pnCommonQueryPage.setInitState();
			showUnitWaste();
			setState();
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
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
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
					return DgDzscEditBomUnitWaste.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return DgDzscEditBomUnitWaste.this.getDataSource(index,
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
						list.add(addColumn("备案序号", "seqNum", 100));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("型号规格", "spec", 150));
						list.add(addColumn("报关单位", "unit.name", 100));
						list.add(addColumn("单价", "price", 100));
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
		if (dzscAction == null) {
			return new ArrayList();
		}
		// String materielType = scmCoiSelected.getCode();
		return dzscAction.findDzscEmsBomBillImg(new Request(CommonVars
				.getCurrUser()), head, index, length, property, value, isLike);
		// return new ArrayList();
	}

	private JTableListModel initUnitWasteTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("父料备案序号", "dzscEmsExgBill.seqNum", 80));
				list.add(addColumn("父料编码", "dzscEmsExgBill.complex.code", 80));
				list.add(addColumn("父料名称", "dzscEmsExgBill.name", 80));
				list.add(addColumn("父料规格", "dzscEmsExgBill.spec", 80));
				// list.add(addColumn("BOM版本", "version.version", 100));
				list.add(addColumn("子料备案序号", "imgSeqNum", 80));
				list.add(addColumn("子料编码", "complex.code", 80));
				list.add(addColumn("子料名称", "name", 70));
				list.add(addColumn("单耗", "unitWare", 70));
				list.add(addColumn("损耗%", "ware", 70));
				list.add(addColumn("单项用量", "unitDosage", 70));
				return list;
			}
		};
		tableModelUnitWaste = new JTableListModel(tbUnitWaste, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		jTableListModelAdapter.setEditableColumn(7);
		jTableListModelAdapter.setEditableColumn(8);
		tableModelUnitWaste.setAllowSetValue(true);
		JNumberTableCellEditor tableCellEditor = new JNumberTableCellEditor();
		tableCellEditor.addAutoCalcListener(new TableCellEditorListener() {
			public void run(TableCellEditorParameter param) {
				commitValueChange((DzscEmsBomBill) param.getEditingData());
			}
		});
		tableCellEditor.setEnableListener(new TableCellEditorEnableListener() {
			public boolean isCanEdit(TableCellEditorParameter param) {
				DzscEmsBomBill detail = (DzscEmsBomBill) param.getEditingData();
				if (detail == null) {
					return false;
				}
				String declareState = detail.getDzscEmsExgBill()
						.getDzscEmsPorHead().getDeclareState();
				if (DzscState.ORIGINAL.equals(declareState)
						|| DzscState.CHANGE.equals(declareState)) {
					return true;
				} else {
					return false;
				}
			}
		});

		tbUnitWaste.getColumnModel().getColumn(7)
				.setCellEditor(tableCellEditor);
		tbUnitWaste.getColumnModel().getColumn(7).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		tbUnitWaste.getColumnModel().getColumn(8)
				.setCellEditor(tableCellEditor);
		tbUnitWaste.getColumnModel().getColumn(8).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		tbUnitWaste.setRowHeight(20);
		return tableModelUnitWaste;
	}

	private void showUnitWaste() {
		List list = new ArrayList();
		if (tableModelMaterial != null) {
			DzscEmsImgBill img = (DzscEmsImgBill) tableModelMaterial
					.getCurrentRow();
			if (img != null) {
				list = dzscAction.findDzscEmsBomBillByImg(new Request(
						CommonVars.getCurrUser()), head, img);
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
							DzscEmsBomBill bom = (DzscEmsBomBill) list.get(i);
							dzscAction.saveDzscEmsBomBill(new Request(
									CommonVars.getCurrUser()), bom);
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

	private void commitValueChange(DzscEmsBomBill detail) {
		if (detail == null) {
			return;
		}
		double unitWaste = (detail.getUnitWare() == null ? 0.0 : detail
				.getUnitWare());
		double waste = (detail.getWare() == null ? 0.0 : detail.getWare());
		if (waste >= 100 || waste < 0) {
			// JOptionPane.showMessageDialog(
			// DgDzscEditBomUnitWaste.this, "损耗不能大于等于100或者小于0",
			// "提示", JOptionPane.INFORMATION_MESSAGE);

			// tf.setValue(0.0);
			detail.setWare(0.0);
			waste = 0.0;
			// tf.requestFocus();
			// return;
		}
		double unitUsed = CommonVars.getDoubleByDigit(unitWaste
				/ (1 - waste / 100.0), 5);
		detail.setUnitDosage(unitUsed);
	}

	//
	// /**
	// * 编辑列
	// */
	// class JTextFieldEditor extends DefaultCellEditor implements KeyListener,
	// FocusListener {
	// private JFormattedTextField tf;
	//
	// private JTable table = null;
	//
	// // private int keyCode = -1;
	//
	// // private Object editBeforeValue = "";
	//
	// public JTextFieldEditor(JFormattedTextField tf) {
	// super(tf);
	// this.tf = tf;
	// // this.tf.setBorder(null);
	// this.setClickCountToStart(1);
	// this.tf.addKeyListener(this);
	// this.tf.addFocusListener(this);
	// }
	//
	// public Component getTableCellEditorComponent(JTable table,
	// Object value, boolean isSelected, int row, int column) {
	// this.table = table;
	// if (value != null) {
	// // tf.setText((String) value);
	// tf.setValue(Double.parseDouble(value.toString()));
	// }
	// if (this.table != null && this.table.getModel() != null) {
	// JTableListModel tableModel = (JTableListModel) this.table
	// .getModel();
	// DzscEmsBomBill detail = (DzscEmsBomBill) tableModel
	// .getDataByRow(row);
	// String declareState = detail.getDzscEmsExgBill()
	// .getDzscEmsPorHead().getDeclareState();
	// if (DzscState.ORIGINAL.equals(declareState)
	// || DzscState.CHANGE.equals(declareState)) {
	// tf.setEditable(true);
	// } else {
	// tf.setEditable(false);
	// }
	// }
	// // tf.addKeyListener(this);
	// return tf;
	// }
	//
	// public Object getCellEditorValue() {
	// if (tf.getValue() == null) {
	// return 0.0;
	// } else {
	// return Double.valueOf(tf.getValue().toString());
	// }
	// }
	//
	// public void focusGained(FocusEvent e) {
	// // JTableListModel tableModel = (JTableListModel) table.getModel();
	// // MaterialBomDetail info = (MaterialBomDetail) tableModel
	// // .getCurrentRow();
	// // tf.setValue(info.getUnitWaste());
	// tf.selectAll();
	// }
	//
	// public void keyTyped(KeyEvent e) {
	//
	// }
	//
	// public void keyPressed(KeyEvent e) {
	// // keyCode = e.getKeyCode();
	// // if (e.getKeyCode() == 10 && table != null) {
	// // table.requestFocus();
	// // }
	//
	// // if (e.getKeyCode() == 10 || e.getKeyCode() == 40
	// // || e.getKeyCode() == 38) {
	// // JTableListModel tableModel = (JTableListModel) this.table
	// // .getModel();
	// // if (e.getKeyCode() == 10) {
	// // if (tableModel.hasNextRow()) {
	// // tableModel.nextRow();
	// // }
	// // }
	// // }
	// // if (e.getKeyCode() == 10 || e.getKeyCode() == 40
	// // || e.getKeyCode() == 38) {
	// // if(table==null||table.getModel()==null){
	// // return;
	// // }
	// // JTableListModel tableModel = (JTableListModel) this.table
	// // .getModel();
	// // MaterialBomDetail
	// // detail=(MaterialBomDetail)tableModel.getCurrentRow();
	// // double
	// // unitWaste=(detail.getUnitWaste()==null?0.0:detail.getUnitWaste());
	// // double waste=(detail.getWaste()==null?0.0:detail.getWaste());
	// // double unitUsed=(unitWaste/(1-waste));
	// // detail.setUnitUsed(unitUsed);
	// // tableModel.updateRow(detail);
	// // }
	// }
	//
	// public void keyReleased(KeyEvent e) {
	// }
	//
	// public void focusLost(FocusEvent e) {
	// if (table == null || table.getModel() == null) {
	// return;
	// }
	// commitValueChange(tf);
	// }
	// }
	//
	// public class ForcedEditTableCellRenderer extends DefaultTableCellRenderer
	// {
	//
	// /** Creates a new instance of ForcedEditTableCellRenderer */
	// public ForcedEditTableCellRenderer() {
	// super();
	// }
	//
	// public Component getTableCellRendererComponent(JTable table,
	// Object value, boolean isSelected, boolean hasFocus, int row,
	// int column) {
	// // Obtain the default component.
	// Component comp = super.getTableCellRendererComponent(table, value,
	// isSelected, hasFocus, row, column);
	//
	// if (hasFocus && isSelected) {
	// TableModel tblModel = table.getModel();
	// if (tblModel.isCellEditable(row, column)) {
	// // Cell is editable
	// table.editCellAt(row, column);
	// table.getEditorComponent().requestFocus();
	// JFormattedTextField tf = (JFormattedTextField) table
	// .getEditorComponent();
	// // if (value == null) {
	// // tf.setText("");
	// // } else {
	// // tf.setText(value.toString());
	// // tf.selectAll();
	// // }
	// if (value == null) {
	// tf.setValue(0.0);
	// } else {
	// tf.setValue(Double.parseDouble(value.toString()));
	// }
	// tf.selectAll();
	// }
	// }
	// // else {
	// // if (value != null && value.toString().length() != 9) {
	// // comp.setForeground(Color.RED);
	// // } else {
	// // if (isSelected) {
	// // comp.setForeground(table.getSelectionForeground());
	// // comp.setBackground(table.getSelectionBackground());
	// // } else {
	// // comp.setForeground(table.getForeground());
	// // comp.setBackground(table.getBackground());
	// // }
	// // }
	// // }
	// // setValue(value);
	// // System.out.println("value:" + value + " row:" + row + " column:"
	// // + column);
	// return comp;
	// }
	//
	// }

	private void setState() {
		String declareState = head.getDeclareState();
		this.btnSave.setEnabled(DzscState.ORIGINAL.equals(declareState)
				|| DzscState.CHANGE.equals(declareState));
		this.btnUndo.setEnabled(DzscState.ORIGINAL.equals(declareState)
				|| DzscState.CHANGE.equals(declareState));
	}
} // @jve:decl-index=0:visual-constraint="10,10"
