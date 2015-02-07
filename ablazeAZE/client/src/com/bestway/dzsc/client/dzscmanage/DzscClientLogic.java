/*
 * Created on 2005-3-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.tableeditor.JNumberForcedTableCellRenderer;
import com.bestway.bcus.client.common.tableeditor.JNumberTableCellEditor;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorEnableListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorParameter;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomDetail;
import com.bestway.dzsc.message.entity.DzscParameterSet;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DzscClientLogic {
	private static DefaultFormatterFactory defaultFormatterFactory = null;

	private static NumberFormatter numberFormatter = null;

	private static DzscAction dzscAction = (DzscAction) CommonVars
			.getApplicationContext().getBean("dzscAction");
	private static DzscParameterSet parameterSet = dzscAction
			.findDzscParameterSet(new Request(CommonVars.getCurrUser(), true));

	// 填充动态ComboBox
	public static void intoComboBox(List list, JComboBox jComboBox) {
		jComboBox.setModel(new DefaultComboBoxModel(list.toArray()));
		jComboBox.setRenderer(CustomBaseRender.getInstance().getRender("code",
				"name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox,
				"code", "name");
		jComboBox.setSelectedIndex(-1);
	}

	// 填充动态公司ComboBox
	public static void intoCompanyComboBox(List list, JComboBox jComboBox) {
		jComboBox.setModel(new DefaultComboBoxModel(list.toArray()));
		jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
				"buCode", "buName", 100, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox,
				"buCode", "buName");
		jComboBox.setUI(new CustomBaseComboBoxUI(300));
		jComboBox.setSelectedIndex(-1);
	}

	// 填充合同表体料件和成品Table
	public static JTableListModel initTable(JTableListModel tableModel,
			JTable jTable, final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("序号", "no", 30, Integer.class));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("单位", "unit.name", 40));
						list.add(addColumn("编码", "fourcomplex", 80));
						list.add(addColumn("征免性质", "levyMode.name", 80));
						// list.add(addColumn("最低单价", "lowPrice", 80));
						return list;
					}
				});
	}

	// 填充料件清单Table
	private static JTableListModel initTableImgBill(JTable jTable,
			final List list) {
		JTableListModel tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("状态", "modifyMark", 40));
						list
								.add(addColumn("备案序号", "seqNum", 60,
										Integer.class));
						list.add(addColumn("归并序号", "tenSeqNum", 60,
								Integer.class));
						list.add(addColumn("海关编码", "complex.code", 80));
						list.add(addColumn("料件名称", "name", 150));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("单价", "price", 70));
						list.add(addColumn("数量", "amount", 70));
						list.add(addColumn("金额", "money", 70));
						list.add(addColumn("单位", "unit.name", 40));
						list.add(addColumn("非保税料件比例", "dutyRate", 100));
						list.add(addColumn("单位重量", "unitWeight", 80));
						list.add(addColumn("总重量", "weight", 70));
						list.add(addColumn("原产地", "country.name", 70));
						// list.add(addColumn("合同序号", "seqNum", 80));
						list.add(addColumn("征免方式", "levyMode.name", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						this.setText(ModifyMarkState.getModifyMarkSpec(state));
						return this;
					}
				});
		List<JTableListColumn> cm = tableModel.getColumns();
		cm.get(8).setFractionCount(5);
		cm.get(9).setFractionCount(5);
		cm.get(7).setFractionCount(5);
		// ((JTableListColumn)((JTableListModel)jTable.getColumnModel()).getColumn(7)).setFractionCount(5);
		// ((JTableListColumn)(JTableListModel)jTable.getColumnModel().getColumn(8)).setFractionCount(5);
		// ((JTableListColumn)(JTableListModel)jTable.getColumnModel().getColumn(9)).setFractionCount(5);
		return tableModel;
	}

	public static JTableListModel initTableImgBill(JTable jTable,
			DzscEmsPorHead head) {
		List list = dzscAction.findEmsPorImgBill(new Request(CommonVars
				.getCurrUser()), head);
		return initTableImgBill(jTable, list);
	}

	// 填充成品清单Table
	private static JTableListModel initTableExgBill(JTable jTable,
			final List list) {
		JTableListModel tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("状态", "modifyMark", 40));
						list
								.add(addColumn("备案序号", "seqNum", 60,
										Integer.class));
						list.add(addColumn("归并序号", "tenSeqNum", 60,
								Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("单位", "unit.name", 40));
						list.add(addColumn("单价", "price", 70));
						list.add(addColumn("出口数量", "amount", 80));
						list.add(addColumn("总额", "money", 70));
						list.add(addColumn("原料费", "imgMoney", 50));
						list.add(addColumn("消费国", "country.name", 70));
						list.add(addColumn("加工费单价", "machinPrice", 100));
						list.add(addColumn("补充说明", "note", 80));
						list.add(addColumn("单位净重", "unitNetWeight", 80));
						list.add(addColumn("单位毛重", "unitGrossWeight", 80));
						// list.add(addColumn("合同序号", "seqNum", 80));
						list.add(addColumn("征免方式", "levyMode.name", 80));
						list.add(addColumn("申报状态", "dutyRateString", 70));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						this.setText(ModifyMarkState.getModifyMarkSpec(state));
						return this;
					}
				});
		List<JTableListColumn> cm = tableModel.getColumns();
		cm.get(8).setFractionCount(5);
		cm.get(9).setFractionCount(5);
		cm.get(10).setFractionCount(5);
		return tableModel;
	}

	public static JTableListModel initTableExgBill(JTable tbExg,
			DzscEmsPorHead head) {
		List list = dzscAction.findEmsPorExgBill(new Request(CommonVars
				.getCurrUser()), head);
		return initTableExgBill(tbExg, list);
	}

	// /**
	// * 编辑列
	// */
	// static class JTextFieldEditor extends DefaultCellEditor implements
	// KeyListener, FocusListener {
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
	// if (value != null && (!"".equals(value.toString()))) {
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
	// // if (tf.getValue() != null) {
	// // double waste = Double.parseDouble(tf.getValue().toString());
	// //
	// // }
	// JTableListModel tableModel = (JTableListModel) this.table
	// .getModel();
	// commitValueChange(tableModel, tf);
	// }
	// }
	//
	// static class ForcedEditTableCellRenderer extends DefaultTableCellRenderer
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

	// 填充BOM清单Table
	public static JTableListModel initTableBomBill(JTable jTable,
			final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("状态", "modifyMark", 40));
				list.add(addColumn("料件总表序号", "imgSeqNum", 80, Integer.class));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("料件名称", "name", 150));
				list.add(addColumn("型号规格", "spec", 100));
				list.add(addColumn("单耗", "unitWare", 70));
				list.add(addColumn("损耗率%", "ware", 70));
				list.add(addColumn("单项用量", "unitDosage", 80));
				list.add(addColumn("非保税料件比例%", "nonMnlRatio", 80));
				list.add(addColumn("单价", "price", 70));
				list.add(addColumn("数量", "amount", 70));
				list.add(addColumn("总额", "money", 70));
				list.add(addColumn("单位", "unit.name", 40));
				list.add(addColumn("单位重量", "unitWeight", 80));
				list.add(addColumn("主料/辅料", "isMainImg", 80));
				return list;
			}
		};
		final JTableListModel tableModel = new JTableListModel(jTable, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						this.setText(ModifyMarkState.getModifyMarkSpec(state));
						return this;
					}
				});
		jTableListModelAdapter.setEditableColumn(6);
		jTableListModelAdapter.setEditableColumn(7);
		jTableListModelAdapter.setEditableColumn(9);
		tableModel.setAllowSetValue(true);
	
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

		JNumberTableCellEditor nonMnlRatioCellEditor = new JNumberTableCellEditor();
		
		nonMnlRatioCellEditor.setEnableListener(new TableCellEditorEnableListener() {
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
		
		jTable.getColumnModel().getColumn(6).setCellEditor(tableCellEditor);
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		jTable.getColumnModel().getColumn(7).setCellEditor(tableCellEditor);
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		
		jTable.getColumnModel().getColumn(9).setCellEditor(nonMnlRatioCellEditor);
		jTable.getColumnModel().getColumn(9).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		
		List<JTableListColumn> cm = tableModel.getColumns();
		cm.get(6).setFractionCount(9);
		cm.get(7).setFractionCount(5);
		cm.get(8).setFractionCount(5);
		cm.get(9).setFractionCount(5);
		cm.get(10).setFractionCount(5);
		cm.get(11).setFractionCount(5);
		cm.get(12).setFractionCount(5);
		// ((JTableListColumn)jTable.getColumnModel().getColumn(6)).setFractionCount(9);
		// ((JTableListColumn)jTable.getColumnModel().getColumn(7)).setFractionCount(5);
		// ((JTableListColumn)jTable.getColumnModel().getColumn(8)).setFractionCount(5);
		// ((JTableListColumn)jTable.getColumnModel().getColumn(9)).setFractionCount(5);
		// ((JTableListColumn)jTable.getColumnModel().getColumn(10)).setFractionCount(5);
		// ((JTableListColumn)jTable.getColumnModel().getColumn(11)).setFractionCount(5);
		return tableModel;
	}

	public static JTableListModel initTableBomBill(JTable jTable,
			DzscEmsExgBill exg) {
		if (exg == null) {
			return initTableBomBill(jTable, new Vector());
		}
		List list = dzscAction.findEmsPorBomBill(new Request(CommonVars
				.getCurrUser()), exg);
		return initTableBomBill(jTable, list);
	}

	// public static DefaultFormatterFactory getDefaultFormatterFactory() {
	// if (defaultFormatterFactory == null) {
	// defaultFormatterFactory = new DefaultFormatterFactory();
	// defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
	// defaultFormatterFactory.setEditFormatter(getNumberFormatter());
	// }
	// return defaultFormatterFactory;
	// }
	//
	// public static NumberFormatter getNumberFormatter() {
	// if (numberFormatter == null) {
	// numberFormatter = new NumberFormatter();
	// DecimalFormat decimalFormat1 = new DecimalFormat(); // @jve:decl-index=0:
	// decimalFormat1.setMaximumFractionDigits(6);
	// decimalFormat1.setGroupingSize(0);
	// numberFormatter.setFormat(decimalFormat1);
	// }
	// return numberFormatter;
	// }

	// 填充归并成品
	public static JTableListModel initTableMaterialExg(JTable jTable,
			DzscEmsPorHead dzscEmsPorHead) {
		List list = dzscAction.findDzscEmsPorMaterialExg(new Request(CommonVars
				.getCurrUser()), dzscEmsPorHead);
		AttributiveCellTableModel tableModel = new AttributiveCellTableModel(
				(MultiSpanCellTable) jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("状态", "modifyMark", 40));
						list.add(addColumn("货号", "materiel.ptNo", 70));
						list.add(addColumn("商品名称", "materiel.factoryName", 80));
						list.add(addColumn("型号规格", "materiel.factorySpec", 70));
						list.add(addColumn("单位", "materiel.ptUnit.name", 50));

						list.add(addColumn("备案序号", "dzscEmsExgBill.seqNum", 60,
								Integer.class));
						list.add(addColumn("归并序号", "dzscEmsExgBill.tenSeqNum",
								60, Integer.class));
						list.add(addColumn("商品编码",
								"dzscEmsExgBill.complex.code", 80));
						list.add(addColumn("商品名称", "dzscEmsExgBill.name", 150));
						list.add(addColumn("型号规格", "dzscEmsExgBill.spec", 100));
						list
								.add(addColumn("单位",
										"dzscEmsExgBill.unit.name", 40));

						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						this.setText(ModifyMarkState.getModifyMarkSpec(state));
						return this;
					}
				});
		TableColumnModel cm = jTable.getColumnModel();

		ColumnGroup gBefore = new ColumnGroup("归并前");
		// gBefore.add(cm.getColumn(0));
		gBefore.add(cm.getColumn(1));
		gBefore.add(cm.getColumn(2));
		gBefore.add(cm.getColumn(3));
		gBefore.add(cm.getColumn(4));
		gBefore.add(cm.getColumn(5));

		//		
		//
		ColumnGroup gAfter = new ColumnGroup("归并后");
		// gAfter.add(cm.getColumn(5));
		gAfter.add(cm.getColumn(6));
		gAfter.add(cm.getColumn(7));
		gAfter.add(cm.getColumn(8));
		gAfter.add(cm.getColumn(9));
		gAfter.add(cm.getColumn(10));
		gAfter.add(cm.getColumn(11));

		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gBefore);
		header.addColumnGroup(gAfter);
		return tableModel;
	}

	// 填充归并料件
	public static JTableListModel initTableMaterialImg(JTable jTable,
			DzscEmsPorHead dzscEmsPorHead) {
		List list = dzscAction.findDzscEmsPorMaterialImg(new Request(CommonVars
				.getCurrUser()), dzscEmsPorHead);
		AttributiveCellTableModel tableModel = new AttributiveCellTableModel(
				(MultiSpanCellTable) jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("状态", "modifyMark", 40));
						list.add(addColumn("货号", "materiel.ptNo", 70));
						list.add(addColumn("商品名称", "materiel.factoryName", 80));
						list.add(addColumn("型号规格", "materiel.factorySpec", 70));
						list.add(addColumn("单位", "materiel.ptUnit.name", 50));

						list.add(addColumn("备案序号", "dzscEmsImgBill.seqNum", 60,
								Integer.class));
						list.add(addColumn("归并序号", "dzscEmsImgBill.tenSeqNum",
								60, Integer.class));
						list.add(addColumn("商品编码",
								"dzscEmsImgBill.complex.code", 80));
						list.add(addColumn("商品名称", "dzscEmsImgBill.name", 150));
						list.add(addColumn("型号规格", "dzscEmsImgBill.spec", 100));
						list
								.add(addColumn("单位",
										"dzscEmsImgBill.unit.name", 40));

						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						this.setText(ModifyMarkState.getModifyMarkSpec(state));
						return this;
					}
				});

		//
		TableColumnModel cm = jTable.getColumnModel();

		ColumnGroup gBefore = new ColumnGroup("归并前");
		// gBefore.add(cm.getColumn(0));
		gBefore.add(cm.getColumn(1));
		gBefore.add(cm.getColumn(2));
		gBefore.add(cm.getColumn(3));
		gBefore.add(cm.getColumn(4));
		gBefore.add(cm.getColumn(5));

		//		
		//
		ColumnGroup gAfter = new ColumnGroup("归并后");
		// gAfter.add(cm.getColumn(5));
		gAfter.add(cm.getColumn(6));
		gAfter.add(cm.getColumn(7));
		gAfter.add(cm.getColumn(8));
		gAfter.add(cm.getColumn(9));
		gAfter.add(cm.getColumn(10));
		gAfter.add(cm.getColumn(11));

		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gBefore);
		header.addColumnGroup(gAfter);
		//

		return tableModel;
	}

	private static Double formatDouble(Double value) {
		if (value == null) {
			return Double.valueOf(0);
		}
		return value;
	}

	// /**
	// * 新增成品(成品清单) 来自合同备案（成品）
	// *
	// * @param type
	// * @param head
	// * @param tableModel
	// */
	// public static JTableListModel newEmsPorExgBill(DzscEmsPorHead head,
	// JTableListModel tableModel) {
	// // List list = null;
	// // DgDzscEmsPorImgExgBill dialog = new DgDzscEmsPorImgExgBill();
	// // dialog.setHead(head);
	// // dialog.setType(MaterielType.FINISHED_PRODUCT);
	// // dialog.setVisible(true);
	// // if (dialog.isOk()) {
	// // list = dialog.getReturnList();
	// // }
	// List list = DzscClientHelper.getInstance().findMerger10ForEmsPor(head,
	// MaterielType.FINISHED_PRODUCT);
	// if (list == null || list.isEmpty())
	// return tableModel;
	// LevyMode levyMode = dzscAction.getSysLevyMode(new Request(CommonVars
	// .getCurrUser()));
	// // if (dialog.isMerger()){ //来自电子合同料件成品
	// for (int i = 0; i < list.size(); i++) {
	// DzscEmsExgBill exg = (DzscEmsExgBill) list.get(i);
	// exg.setNo(dzscAction.getNumForImgExgBill(new Request(CommonVars
	// .getCurrUser()), "DzscEmsExgBill", head));// 序号
	// exg.setAmount(Double.valueOf(0));
	// exg.setMoney(Double.valueOf(0));
	// exg.setMachinPrice(Double.valueOf(0));
	// exg.setMachinMoney(Double.valueOf(0));
	// exg.setUnitNetWeight(Double.valueOf(0));
	// exg.setUnitGrossWeight(Double.valueOf(0));
	// exg.setDzscEmsPorHead(head);
	// exg.setCompany(CommonVars.getCurrUser().getCompany());
	// exg.setModifyMark(ModifyMarkState.ADDED);
	// exg = dzscAction.saveDzscEmsExgBill(new Request(CommonVars
	// .getCurrUser()), exg);
	// tableModel.addRow(exg);
	// }
	// return tableModel;
	// }

	/**
	 * （单耗） 新增料件(BOM清单)
	 * 
	 * @param type
	 * @param head
	 * @param tableModel
	 */
	public static JTableListModel newEmsPorBomBill(DzscEmsExgBill exg,
			JTableListModel tableModel) {
		DzscEmsBomBill bom = null;
		List list = (List) DzscClientHelper.getInstance()
				.getDzscBomBillFromImgBill(false, exg);
		if (list == null || list.isEmpty())
			return tableModel;
		for (int i = 0; i < list.size(); i++) {
			bom = (DzscEmsBomBill) list.get(i);
			bom.setDzscEmsExgBill(exg);
			// bom.setNo(dzscAction.getNumForBomBill(new Request(CommonVars
			// .getCurrUser()), "DzscEmsBomBill", exg));// 序号
			bom.setCompany(CommonVars.getCurrUser().getCompany());
			bom.setModifyMark(ModifyMarkState.ADDED);
			bom = dzscAction.saveDzscEmsBomBill(new Request(CommonVars
					.getCurrUser()), bom);
			tableModel.addRow(bom);
		}
		return tableModel;
	}

	// 填充使用该通关备案料件成品列表Table
	private static JTableListModel initTableImgBillForExg(
			JTableListModel tableModel, JTable jTable, final List list) {
		JTableListModelAdapter jTableListModelAdapter=new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list
								.add(addColumn("成品序号", "dzscEmsExgBill.seqNum",
										50));//1
						list.add(addColumn("成品名称", "dzscEmsExgBill.name", 80));//2
						list.add(addColumn("成品规格", "dzscEmsExgBill.spec", 80));//3
						list.add(addColumn("成品单位", "dzscEmsExgBill.unit.name",
								80));//4
						list.add(addColumn("商品编码", "complex.code", 80));//5
						list.add(addColumn("名称", "name", 150));//6
						list.add(addColumn("单耗", "unitWare", 70));//7
						list.add(addColumn("损耗", "ware", 70));//8
						list.add(addColumn("单项用量", "unitDosage", 80));//9
						list.add(addColumn("型号规格", "spec", 80));//10
						list
								.add(addColumn("出口数量", "dzscEmsExgBill.amount",
										80));
						list.add(addColumn("单位", "unit.name", 50));
						list.add(addColumn("单价", "price", 50));
						list.add(addColumn("数量", "amount", 50));
						return list;
					}
				};
		
		 tableModel = new JTableListModel(jTable, list,jTableListModelAdapter,ListSelectionModel.SINGLE_SELECTION);
		jTableListModelAdapter.setEditableColumn(7);
		jTableListModelAdapter.setEditableColumn(8);
		tableModel.setAllowSetValue(true);
		 
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
		jTable.getColumnModel().getColumn(7).setCellEditor(tableCellEditor);
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		jTable.getColumnModel().getColumn(8).setCellEditor(tableCellEditor);
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		return tableModel;
	}

	public static JTableListModel initTableImgBillForExg(
			JTableListModel tableModelExg, JTable jTable1, DzscEmsImgBill img) {
		List list = dzscAction.findExgBillForImg(new Request(CommonVars
				.getCurrUser()), img);
		if (list != null && list.size() > 0) {
			return initTableImgBillForExg(tableModelExg, jTable1, list);
		} else {
			return initTableImgBillForExg(tableModelExg, jTable1, new Vector());
		}
	}

	// /**
	// * 修改合同料件或成品序号
	// * @return
	// */
	// public static JTableListModel editBarNo(JTableListModel
	// tableModel,boolean isImg){
	// DgDzscBargainNoEdit dg = null;
	// dg = new DgDzscBargainNoEdit();
	// dg.setVisible(true);
	// if (dg.isok){
	// if (isImg){
	// DzscEmsPorImg img = (DzscEmsPorImg) tableModel.getCurrentRow();
	// img.setNo(dg.getBaraNo());
	// img = dzscAction.saveDzscEmsPorImg(new
	// Request(CommonVars.getCurrUser()),img);
	// tableModel.updateRow(img);
	// } else {
	// DzscEmsPorExg img = (DzscEmsPorExg) tableModel.getCurrentRow();
	// img.setNo(dg.getBaraNo());
	// img = dzscAction.saveDzscEmsPorExg(new
	// Request(CommonVars.getCurrUser()),img);
	// tableModel.updateRow(img);
	// }
	// }
	// return tableModel;
	// }
	/**
	 * 删除合同料件或成品
	 * 
	 * @return
	 */
	// public static JTableListModel deleteEmsPorImgExg(JTableListModel
	// tableModel,boolean isImg){
	// if (isImg){
	// DzscEmsPorImg img = (DzscEmsPorImg) tableModel.getCurrentRow();
	// dzscAction.deleteDzscEmsPorImg(new
	// Request(CommonVars.getCurrUser()),img);
	// tableModel.deleteRow(img);
	// } else {
	// DzscEmsPorExg exg = (DzscEmsPorExg) tableModel.getCurrentRow();
	// dzscAction.deleteDzscEmsPorExg(new
	// Request(CommonVars.getCurrUser()),exg);
	// tableModel.deleteRow(exg);
	// }
	// return tableModel;
	// }
	// public static boolean isAddState(JTableListModel tableModel,boolean
	// isImg){
	// if (tableModel.getCurrentRow() == null){
	// return false;
	// }
	// if (isImg){
	// DzscEmsPorImg obj = (DzscEmsPorImg) tableModel.getCurrentRow();
	// return obj.getModifyMark().equals(ModifyMarkState.ADDED);
	// } else {
	// DzscEmsPorExg obj = (DzscEmsPorExg) tableModel.getCurrentRow();
	// return obj.getModifyMark().equals(ModifyMarkState.ADDED);
	// }
	// }
	/**
	 * 检查基本资料
	 * 
	 * @return
	 */
	public static boolean isEmpty(DzscEmsPorWjHead head, Component form) {
		// if (head.getEmsNo() == null || head.getEmsNo().equals("")) {
		// JOptionPane.showMessageDialog(form, "手册号不能为空!", "提示", 2);
		// return true;
		// }
		// if (head.getEmsNo().trim().length() != 12) {
		// if (JOptionPane.showConfirmDialog(form, "手册号不为12位，是否确定？", "确认", 0) !=
		// 0) {
		// return true;
		// }
		// }
		if (head.getTradeName() == null || head.getTradeName().equals("")) {
			JOptionPane.showMessageDialog(form, "经营单位不能为空!", "提示", 2);
			return true;
		}
		// if (head.getIePort1() == null) {
		// JOptionPane.showMessageDialog(form, "进出口岸不能为空!", "提示", 2);
		// return true;
		// }
		if (head.getTrade() == null) {
			JOptionPane.showMessageDialog(form, "贸易方式不能为空!", "提示", 2);
			return true;
		}
		if (head.getBeginDate() == null) {
			JOptionPane.showMessageDialog(form, "起始日期不能为空!", "提示", 2);
			return true;
		}
		if (head.getEndDate() == null) {
			JOptionPane.showMessageDialog(form, "结束日期不能为空!", "提示", 2);
			return true;
		}
		// if (head.getDestroyDate() == null) {
		// JOptionPane.showMessageDialog(form, "核销期限不能为空!", "提示", 2);
		// return true;
		// }
		// if (head.getTradeCountry() == null) {
		// JOptionPane.showMessageDialog(form, "贸易国别不能为空!", "提示", 2);
		// return true;
		// }
		if (head.getEnterpriseAddress() == null
				|| head.getEnterpriseAddress().equals("")) {
			JOptionPane.showMessageDialog(form, "企业地址不能为空!", "提示", 2);
			return true;
		}
		if (head.getLinkMan() == null || head.getLinkMan().equals("")) {
			JOptionPane.showMessageDialog(form, "联系人不能为空!", "提示", 2);
			return true;
		}
		if (head.getContactTel() == null || head.getContactTel().equals("")) {
			JOptionPane.showMessageDialog(form, "联系电话不能为空!", "提示", 2);
			return true;
		}
		if (head.getOutTradeCo() == null || head.getOutTradeCo().equals("")) {
			JOptionPane.showMessageDialog(form, "外商公司不能为空!", "提示", 2);
			return true;
		}
		if (head.getLevyKind() == null) {
			JOptionPane.showMessageDialog(form, "征免性质不能为空!", "提示", 2);
			return true;
		}
		if (head.getIeContractNo() == null || head.getIeContractNo().equals("")) {
			if (JOptionPane
					.showConfirmDialog(form, "当前进口合同号为空，确定备案吗？", "确认", 0) != 0) {
				return true;
			}
		}
		if (head.getImgAmount() == null
				|| head.getImgAmount().doubleValue() == 0) {
			JOptionPane.showMessageDialog(form, "进口总值不能为空!", "提示", 2);
			return true;
		}
		if (head.getCurr() == null) {
			JOptionPane.showMessageDialog(form, "币制不能为空!", "提示", 2);
			return true;
		}
		// List listImg = dzscAction.findEmsPorImg(new
		// Request(CommonVars.getCurrUser()),head);
		// List listExg = dzscAction.findEmsPorExg(new
		// Request(CommonVars.getCurrUser()),head);
		// if (listImg.size() == 0 && listExg.size() == 0 ){
		// JOptionPane.showMessageDialog(form,"当前合同没有归并资料,无法备案!","提示",2);
		// return true;
		// }
		// if
		// (JOptionPane.showConfirmDialog(form,"你确认合同:"+head.getIeContractNo().trim()+"海关已经备案完毕吗","确认",0)
		// != 0){
		// return true;
		// }
		return false;
	}

	/**
	 * 检查基本资料
	 * 
	 * @return
	 */
	public static boolean isEmpty(DzscEmsPorHead head, Component form) {
		// if (head.getEmsNo() == null || head.getEmsNo().equals("")) {
		// JOptionPane.showMessageDialog(form, "手册号不能为空!", "提示", 2);
		// return true;
		// }
		// if (head.getEmsNo().trim().length() != 12) {
		// if (JOptionPane.showConfirmDialog(form, "手册号不为12位，是否确定？", "确认", 0) !=
		// 0) {
		// return true;
		// }
		// }
		if (head.getTradeName() == null || head.getTradeName().equals("")) {
			JOptionPane.showMessageDialog(form, "经营单位不能为空!", "提示", 2);
			return true;
		}
		// if (head.getIePort1() == null) {
		// JOptionPane.showMessageDialog(form, "进出口岸不能为空!", "提示", 2);
		// return true;
		// }
		if (head.getTrade() == null) {
			JOptionPane.showMessageDialog(form, "贸易方式不能为空!", "提示", 2);
			return true;
		}
		if (head.getBeginDate() == null) {
			JOptionPane.showMessageDialog(form, "起始日期不能为空!", "提示", 2);
			return true;
		}
		if (head.getEndDate() == null) {
			JOptionPane.showMessageDialog(form, "结束日期不能为空!", "提示", 2);
			return true;
		}
		// if (head.getDestroyDate() == null) {
		// JOptionPane.showMessageDialog(form, "核销期限不能为空!", "提示", 2);
		// return true;
		// }
		// if (head.getTradeCountry() == null) {
		// JOptionPane.showMessageDialog(form, "贸易国别不能为空!", "提示", 2);
		// return true;
		// }
		if (head.getEnterpriseAddress() == null
				|| head.getEnterpriseAddress().equals("")) {
			JOptionPane.showMessageDialog(form, "企业地址不能为空!", "提示", 2);
			return true;
		}
		if (head.getLinkMan() == null || head.getLinkMan().equals("")) {
			JOptionPane.showMessageDialog(form, "联系人不能为空!", "提示", 2);
			return true;
		}
		if (head.getContactTel() == null || head.getContactTel().equals("")) {
			JOptionPane.showMessageDialog(form, "联系电话不能为空!", "提示", 2);
			return true;
		}
		if (head.getOutTradeCo() == null || head.getOutTradeCo().equals("")) {
			JOptionPane.showMessageDialog(form, "外商公司不能为空!", "提示", 2);
			return true;
		}
		if (head.getLevyKind() == null) {
			JOptionPane.showMessageDialog(form, "征免性质不能为空!", "提示", 2);
			return true;
		}
		if (head.getIeContractNo() == null || head.getIeContractNo().equals("")) {
			if (JOptionPane
					.showConfirmDialog(form, "当前进口合同号为空，确定备案吗？", "确认", 0) != 0) {
				return true;
			}
		}
		if (head.getImgAmount() == null
				|| head.getImgAmount().doubleValue() == 0) {
			JOptionPane.showMessageDialog(form, "进口总值不能为空!", "提示", 2);
			return true;
		}
		if (head.getCurr() == null) {
			JOptionPane.showMessageDialog(form, "币制不能为空!", "提示", 2);
			return true;
		}
		// List listImg = dzscAction.findEmsPorImg(new
		// Request(CommonVars.getCurrUser()),head);
		// List listExg = dzscAction.findEmsPorExg(new
		// Request(CommonVars.getCurrUser()),head);
		// if (listImg.size() == 0 && listExg.size() == 0 ){
		// JOptionPane.showMessageDialog(form,"当前合同没有归并资料,无法备案!","提示",2);
		// return true;
		// }
		if (JOptionPane.showConfirmDialog(form, "你确认合同:"
				+ head.getIeContractNo().trim() + "海关已经备案完毕吗", "确认", 0) != 0) {
			return true;
		}
		return false;
	}

	// 填充料件清单Table----外经
	private static JTableListModel initTableImgWj(JTable jTable, final List list) {
		JTableListModel tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						// list.add(addColumn("序号", "no", 50));
						// list.add(addColumn("海关编码", "complex", 80));
						// list.add(addColumn("料件名称", "name", 150));
						// list.add(addColumn("型号规格", "spec", 150));
						// list.add(addColumn("单价", "price", 50));
						// list.add(addColumn("数量", "amount", 50));
						// list.add(addColumn("数量", "wareAmount", 50));
						// list.add(addColumn("金额", "money", 50));
						// list.add(addColumn("单位", "unit.name", 50));
						// list.add(addColumn("法定单位总量", "legalGross", 120));
						// list.add(addColumn("法定单位", "legalUnit.name", 50));
						// list.add(addColumn("法定单位数量", "legalAmount", 50));
						// list.add(addColumn("单位重量", "unitWeight", 80));
						// list.add(addColumn("总重量", "weight", 70));
						// list.add(addColumn("主辅料", "isMainImg", 70));
						// list.add(addColumn("原产地", "country.name", 70));
						// list.add(addColumn("废料是否报关", "isImgCustom", 120));
						// list.add(addColumn("报关凭证序号", "seqNum", 120));
						// list.add(addColumn("征免方式", "levyMode.name", 80));
						list.add(addColumn("修改标志", "modifyMark", 80));
						list.add(addColumn("备案序号", "seqNum", 50));
						list.add(addColumn("归并序号", "fourSeqNum", 50));
						list.add(addColumn("商品编码", "fourComplex", 80));
						list.add(addColumn("商品名称", "fourName", 80));
						list.add(addColumn("规格型号", "fourSpec", 80));
						list.add(addColumn("单位", "fourUnit.name", 80));
						list.add(addColumn("第一法定单位", "firstUnit.name", 80));
						list.add(addColumn("第二法定单位", "secondUnit.name", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						if (state.equals(ModifyMarkState.UNCHANGE)) {
							this.setText("未修改");
						} else if (state.equals(ModifyMarkState.ADDED)) {
							this.setText("新增");
						}
						if (state.equals(ModifyMarkState.MODIFIED)) {
							this.setText("已修改");
						}
						if (state.equals(ModifyMarkState.DELETED)) {
							this.setText("已删除");
						}
						return this;
					}
				});
		return tableModel;
	}

	public static JTableListModel initTableImgWj(JTable jTable,
			DzscEmsPorWjHead head) {
		List list = dzscAction.findEmsPorImgWj(new Request(CommonVars
				.getCurrUser()), head);
		if (list != null && list.size() > 0) {
			return initTableImgWj(jTable, list);
		} else {
			return initTableImgWj(jTable, new Vector());
		}
	}

	// 填充成品Table----外经
	private static JTableListModel initTableExgWj(JTable jTable, final List list) {
		JTableListModel tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						// list.add(addColumn("序号", "no", 50));
						// list.add(addColumn("商品编码", "complex", 80));
						// list.add(addColumn("商品名称", "name", 150));
						// list.add(addColumn("型号规格", "spec", 150));
						// list.add(addColumn("出口数量", "amount", 80));
						// list.add(addColumn("单位", "unit.name", 50));
						// list.add(addColumn("单价", "price", 50));
						// list.add(addColumn("总额", "money", 50));
						// list.add(addColumn("法定数量", "legalAmount", 80));
						// list.add(addColumn("原料费", "imgMoney", 50));
						// list.add(addColumn("消费国", "country.name", 70));
						// list.add(addColumn("加工费单价", "machinPrice", 100));
						// list.add(addColumn("补充说明", "note", 80));
						// list.add(addColumn("单位净重", "unitNetWeight", 80));
						// list.add(addColumn("单位毛重", "unitGrossWeight", 80));
						// list.add(addColumn("成品总表序号", "seqNum", 80));
						// list.add(addColumn("征免方式", "levyMode.name", 80));
						list.add(addColumn("修改标志", "modifyMark", 80));
						list.add(addColumn("备案序号", "seqNum", 50));
						list.add(addColumn("归并序号", "fourSeqNum", 50));
						list.add(addColumn("商品编码", "fourComplex", 80));
						list.add(addColumn("商品名称", "fourName", 80));
						list.add(addColumn("规格型号", "fourSpec", 80));
						list.add(addColumn("单位", "fourUnit.name", 80));
						list.add(addColumn("第一法定单位", "firstUnit.name", 80));
						list.add(addColumn("第二法定单位", "secondUnit.name", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						if (state.equals(ModifyMarkState.UNCHANGE)) {
							this.setText("未修改");
						} else if (state.equals(ModifyMarkState.ADDED)) {
							this.setText("新增");
						}
						if (state.equals(ModifyMarkState.MODIFIED)) {
							this.setText("已修改");
						}
						if (state.equals(ModifyMarkState.DELETED)) {
							this.setText("已删除");
						}
						return this;
					}
				});
		return tableModel;
	}

	public static JTableListModel initTableExgWj(JTable jTable,
			DzscEmsPorWjHead head) {
		List list = dzscAction.findEmsPorExgWj(new Request(CommonVars
				.getCurrUser()), head);
		return initTableExgWj(jTable, list);
	}

	private static void commitValueChange(DzscEmsBomBill detail) {
		int countBit = parameterSet.getCountBit() == null ? 5 : parameterSet
				.getCountBit();
		int priceBit = parameterSet.getPriceBit() == null ? 5 : parameterSet
				.getPriceBit();
		int moneyBit = parameterSet.getMoneyBit() == null ? 5 : parameterSet
				.getMoneyBit();
		if (detail == null) {
			return;
		}
		// DzscEmsBomBill detail = (DzscEmsBomBill) tableModel.getCurrentRow();
		double unitWaste = (detail.getUnitWare() == null ? 0.0 : detail
				.getUnitWare());
		double waste = (detail.getWare() == null ? 0.0 : detail.getWare());
		if (waste >= 100 || waste < 0) {
			detail.setWare(0.0);
			waste = 0.0;
		}
		double unitUsed = CommonVars.getDoubleByDigit(unitWaste
				/ (1 - waste / 100.0), 5);
		detail.setUnitDosage(unitUsed);
		double exgAmount = CommonVars.formatDouble(detail.getDzscEmsExgBill()
				.getAmount());
		double imgAmount = CommonVars.getDoubleByDigit(unitUsed * exgAmount, 5);
		detail.setAmount(CommonVars.getDoubleByDigit(imgAmount, countBit));
		detail.setPrice(CommonVars.getDoubleByDigit(CommonVars
				.formatDouble(detail.getPrice()), priceBit));
		detail.setMoney(CommonVars.getDoubleByDigit(CommonVars
				.formatDouble(detail.getPrice())
				* imgAmount, moneyBit));
	}
}
