package com.bestway.dzsc.client.innermerge;
/***
 * 报关单耗
 * 
 * 刘民checked  by 2009-1-21
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.tableeditor.JNumberForcedTableCellRenderer;
import com.bestway.bcus.client.common.tableeditor.JNumberTableCellEditor;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorParameter;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.dzsc.innermerge.action.DzscInnerMergeAction;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomDetail;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomExg;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmDzscCustomsBom extends JInternalFrameBase {

	private JPanel pnContent = null;

	private JPanel pnMain = null;

	private JSplitPane spnMain = null;

	private JPanel pnUp = null;

	private JPanel pnDown = null;

	private JToolBar tbbUp = null;

	private JToolBar tbbDown = null;

	private JScrollPane spnUp = null;

	private JTable tbUp = null;

	private JScrollPane spnDown = null;

	private JTable tbDown = null;

	private JButton btnAdd = null;

	private JButton btnDelete = null;

	private JButton btnAdd2 = null;

	private JButton btnDelete2 = null;

	private JButton btnAmend = null;

	private JTableListModel tableModelExg = null;

	private JTableListModel tableModelBom = null;

	private DzscInnerMergeAction dzscInnerMergeAction = null;

	private JButton btnImportUnitConsumption = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmDzscCustomsBom() {
		super();
		initialize();
		dzscInnerMergeAction = (DzscInnerMergeAction) CommonVars
				.getApplicationContext().getBean("dzscInnerMergeAction");
		List list = dzscInnerMergeAction.findDzscCustomsBomExg(new Request(
				CommonVars.getCurrUser()));
		initExgTable(list);
		if (tableModelExg.getCurrentRow() == null)
			return;
		DzscCustomsBomExg exg = (DzscCustomsBomExg) tableModelExg
				.getCurrentRow();
		// lbExgInfo.setText("当前成品序号为:" + exg.getSeqNum()
		// + " 名称为:" + exg.getName());
		list = dzscInnerMergeAction.findDzscCustomsBomDetail(new Request(
				CommonVars.getCurrUser()), exg);
		initBomTable(list);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(664, 428));
		this.setTitle("报关单耗");
		this.setContentPane(getPnContent());

	}

	/**
	 * This method initializes pnContent
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContent() {
		if (pnContent == null) {
			pnContent = new JPanel();
			pnContent.setLayout(new BorderLayout());
			pnContent.add(getPnMain(), BorderLayout.CENTER);
		}
		return pnContent;
	}

	/**
	 * This method initializes pnMain
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			pnMain = new JPanel();
			pnMain.setLayout(new BorderLayout());
			pnMain.add(getSpnMain(), BorderLayout.CENTER);
		}
		return pnMain;
	}

	/**
	 * This method initializes spnMain
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getSpnMain() {
		if (spnMain == null) {
			spnMain = new JSplitPane();
			spnMain.setOrientation(JSplitPane.VERTICAL_SPLIT);
			spnMain.setDividerSize(2);
			spnMain.setTopComponent(getPnUp());
			spnMain.setBottomComponent(getPnDown());
			spnMain.setDividerLocation(200);
		}
		return spnMain;
	}

	/**
	 * This method initializes pnUp
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnUp() {
		if (pnUp == null) {
			pnUp = new JPanel();
			pnUp.setLayout(new BorderLayout());
			pnUp.add(getTbbUp(), BorderLayout.NORTH);
			pnUp.add(getSpnUp(), BorderLayout.CENTER);
		}
		return pnUp;
	}

	/**
	 * This method initializes pnDown
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnDown() {
		if (pnDown == null) {
			pnDown = new JPanel();
			pnDown.setLayout(new BorderLayout());
			pnDown.add(getTbbDown(), BorderLayout.NORTH);
			pnDown.add(getSpnDown(), BorderLayout.CENTER);
		}
		return pnDown;
	}

	/**
	 * This method initializes tbbUp
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getTbbUp() {
		if (tbbUp == null) {
			tbbUp = new JToolBar();
			tbbUp.add(getBtnAdd());
			tbbUp.add(getBtnDelete());
		}
		return tbbUp;
	}

	/**
	 * This method initializes tbbDown
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getTbbDown() {
		if (tbbDown == null) {
			tbbDown = new JToolBar();
			tbbDown.add(getBtnAdd2());
			tbbDown.add(getBtnAmend());
			tbbDown.add(getBtnDelete2());
			tbbDown.add(getBtnImportUnitConsumption());
			tbbDown.add(getBtnSave());
			tbbDown.add(getBtnCancel());
		}
		return tbbDown;
	}

	/**
	 * This method initializes spnUp
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpnUp() {
		if (spnUp == null) {
			spnUp = new JScrollPane();
			spnUp.setViewportView(getTbUp());
		}
		return spnUp;
	}

	/**
	 * This method initializes tbUp
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbUp() {
		if (tbUp == null) {
			tbUp = new JTable();
			tbUp.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelExg == null)
								return;
							if (tableModelExg.getCurrentRow() == null)
								return;
							DzscCustomsBomExg exg = (DzscCustomsBomExg) tableModelExg
									.getCurrentRow();
							// lbExgInfo.setText("当前成品序号为:" + exg.getSeqNum()
							// + " 名称为:" + exg.getName());
							List list = dzscInnerMergeAction
									.findDzscCustomsBomDetail(new Request(
											CommonVars.getCurrUser()), exg);
							initBomTable(list);
						}
					});
		}
		return tbUp;
	}

	/**
	 * This method initializes spnDown
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpnDown() {
		if (spnDown == null) {
			spnDown = new JScrollPane();
			spnDown.setViewportView(getTbDown());
		}
		return spnDown;
	}

	/**
	 * This method initializes tbDown
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbDown() {
		if (tbDown == null) {
			tbDown = new JTable();
			InputMap inputMap = tbDown
					.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
			KeyStroke entry = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

			KeyStroke sTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 1);
			KeyStroke sEntry = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 1);

			Object tabOperObject = inputMap.get(tab);
			inputMap.remove(entry);
			inputMap.put(entry, tabOperObject);

			Object sTabOperObject = inputMap.get(sTab);
			inputMap.remove(sEntry);
			inputMap.put(sEntry, sTabOperObject);
		}
		return tbDown;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = DzscInnerMergeQuery.getInstance()
							.findInnerMergeNotInCustomsBomExg();
					if (list.size() > 0) {
						list = dzscInnerMergeAction
								.addDzscCustomsBomExgFromInnerMerge(
										new Request(CommonVars.getCurrUser()),
										list);
						tableModelExg.addRows(list);
					}
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscCustomsBom.this,
								"请选择要删除的单耗成品资料!");
						return;
					}
					if (JOptionPane.showConfirmDialog(FmDzscCustomsBom.this,
							"确定要删除此成品资料吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					List list = tableModelExg.getCurrentRows();
					dzscInnerMergeAction.deleteDzscCustomsBomExg(new Request(
							CommonVars.getCurrUser()), list);
					tableModelExg.deleteRows(list);
				}

			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnAdd2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd2() {
		if (btnAdd2 == null) {
			btnAdd2 = new JButton();
			btnAdd2.setText("新增");
			btnAdd2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscCustomsBom.this,
								"请选择单耗成品资料!");
						return;
					}
					DzscCustomsBomExg exg = (DzscCustomsBomExg) tableModelExg
							.getCurrentRow();
					List list = DzscInnerMergeQuery.getInstance()
							.findInnerMergeNotInCustomsBomDetail(exg);
					if (list.size() > 0) {
						list = dzscInnerMergeAction
								.addDzscCustomsBomDetailFromInnerMerge(
										new Request(CommonVars.getCurrUser()),
										exg, list);
						tableModelBom.addRows(list);
					}
				}
			});
		}
		return btnAdd2;
	}

	/**
	 * This method initializes btnDelete2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete2() {
		if (btnDelete2 == null) {
			btnDelete2 = new JButton();
			btnDelete2.setText("删除");
			btnDelete2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBom.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscCustomsBom.this,
								"请选择要删除的单耗明细资料!");
						return;
					}
					if (JOptionPane.showConfirmDialog(FmDzscCustomsBom.this,
							"确定要删除此单耗资料吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					List list = tableModelBom.getCurrentRows();
					dzscInnerMergeAction.deleteDzscCustomsBomDetail(
							new Request(CommonVars.getCurrUser()), list);
					tableModelBom.deleteRows(list);
				}
			});
		}
		return btnDelete2;
	}

	/**
	 * This method initializes btnAmend
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAmend() {
		if (btnAmend == null) {
			btnAmend = new JButton();
			btnAmend.setText("修改");
			btnAmend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBom.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscCustomsBom.this,
								"请选择要修改的单耗明细资料!");
						return;
					}
					DgDzscCustomsBom dg = new DgDzscCustomsBom();
					dg.setTableModel(tableModelBom);
					dg.setVisible(true);
				}
			});
		}
		return btnAmend;
	}
	/**
	 * 初始化表
	 * 
	 * @param list
	 */

	private void initExgTable(List list) {
		tableModelExg = new JTableListModel(tbUp, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("归并序号", "tenSeqNum", 100,
								Integer.class));
						list.add(addColumn("海关编码", "complex.code", 100));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("单位", "unit.name", 80));
						return list;
					}
				});
	}
	/**
	 * 初始化表
	 * 
	 * @param list
	 */

	private void initBomTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("归并序号", "tenSeqNum", 100, Integer.class));
				list.add(addColumn("海关编码", "complex.code", 100));
				list.add(addColumn("商品名称", "name", 100));
				list.add(addColumn("型号规格", "spec", 100));
				list.add(addColumn("单位", "unit.name", 80));
				list.add(addColumn("单耗", "unitWare", 80));
				list.add(addColumn("损耗率%", "ware", 80));
				list.add(addColumn("单项用量", "unitDosage", 80));
				return list;
			}
		};
		tableModelBom = new JTableListModel(tbDown, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		jTableListModelAdapter.setEditableColumn(6);
		jTableListModelAdapter.setEditableColumn(7);
		JNumberTableCellEditor tableCellEditor = new JNumberTableCellEditor();
		tableCellEditor.addAutoCalcListener(new TableCellEditorListener() {
			public void run(TableCellEditorParameter param) {
				commitValueChange((DzscCustomsBomDetail) param.getEditingData());
			}
		});
		// final JFormattedTextField tf = new JFormattedTextField();
		// CustomFormattedTextFieldUtils.setFormatterFactory(tf, 999);
		// CustomFormattedTextFieldUtils.addAutoCalcListener(tf,
		// new AutoCalcListener() {
		// public void run() {
		// commitValueChange(tableModelBom);
		// }
		// });
		tableModelBom.setAllowSetValue(true);
		tbDown.getColumnModel().getColumn(6).setCellEditor(tableCellEditor);
		tbDown.getColumnModel().getColumn(6).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		tbDown.getColumnModel().getColumn(7).setCellEditor(tableCellEditor);
		tbDown.getColumnModel().getColumn(7).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		tbDown.setRowHeight(20);
	}

	/**
	 * This method initializes btnImportUnitConsumption
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImportUnitConsumption() {
		if (btnImportUnitConsumption == null) {
			btnImportUnitConsumption = new JButton();
			btnImportUnitConsumption.setText("导入单耗");
			btnImportUnitConsumption.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DzscCustomsBomExg exg = (DzscCustomsBomExg) tableModelExg
							.getCurrentRow();
					if (exg == null) {
						JOptionPane.showMessageDialog(FmDzscCustomsBom.this,
								"请先选中成品!", "提示", 2);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmDzscCustomsBom.this,
							"确定要导入单耗吗?", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					DgDzscAutoImportCustomsBom dg = new DgDzscAutoImportCustomsBom();
					dg.setExgBill(exg);
					dg.setVisible(true);
					List list = dzscInnerMergeAction.findDzscCustomsBomDetail(
							new Request(CommonVars.getCurrUser()), exg);
					initBomTable(list);
				}
			});
		}
		return btnImportUnitConsumption;
	}

	// /**
	// * 编辑列
	// */
	// class JTextFieldEditor extends AbstractCellEditor implements
	// TableCellEditor, KeyListener,// DefaultCellEditor
	// FocusListener {
	//
	// private JFormattedTextField tf;
	//
	// // private JTable table = null;
	//
	// public JTextFieldEditor(JFormattedTextField tf) {
	// // super(tf);
	// this.tf = tf;
	// // this.setClickCountToStart(1);
	// this.tf.addKeyListener(this);
	// this.tf.addFocusListener(this);
	// }
	//
	// public Component getTableCellEditorComponent(JTable table,
	// Object value, boolean isSelected, int row, int column) {
	// // this.table = table;
	// // this.tf = (JFormattedTextField)
	// // super.getTableCellEditorComponent(
	// // table, value, isSelected, row, column);
	// if (value == null) {
	// this.tf.setValue(0.0);
	// } else {
	// this.tf.setValue(Double.valueOf(value.toString()));
	// }
	// System.out.println("------------table value:" + value);
	// System.out.println("------------component value:"
	// + this.tf.getValue());
	// return this.tf;
	// }
	//
	// public Object getCellEditorValue() {
	// // System.out.println("--------super:"+super.getCellEditorValue());
	// if (tf.getValue() == null) {
	// System.out.println("----------- ii:" + 0.0);
	// return 0.0;
	// } else {
	// System.out.println("----------- ss:"
	// + Double.valueOf(tf.getValue().toString()));
	// return Double.valueOf(tf.getValue().toString());
	// }
	// }
	//
	// public void focusGained(final FocusEvent e) {
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// // if (table == null) {
	// // return;
	// // }
	// // if (table.getSelectedRow() < 0
	// // || table.getSelectedRow() > table.getRowCount() - 1) {
	// // return;
	// // }
	// // if (table.getSelectedColumn() < 0
	// // || table.getSelectedColumn() > table
	// // .getColumnCount() - 1) {
	// // return;
	// // }
	// // Object value = table.getValueAt(table.getSelectedRow(),
	// // table.getSelectedColumn());
	// // if (value != null) {
	// // tf.setValue(Double.valueOf(value.toString()));
	// // } else {
	// // tf.setValue(0.0);
	// // }
	// tf.selectAll();
	// }
	// });
	// }
	//
	// public void keyTyped(KeyEvent e) {
	//
	// }
	//
	// public void keyPressed(KeyEvent e) {
	//
	// }
	//
	// public void keyReleased(KeyEvent e) {
	// // if (table == null || table.getModel() == null) {
	// // return;
	// // }
	// // commitValueChange(tf);
	// try {
	// if (tf.getText().trim().equals("")) {
	// tf.setText("0");
	// }
	// tf.commitEdit();
	// } catch (Exception ex) {
	// }
	// }
	//
	// public void focusLost(FocusEvent e) {
	// // if (table == null || table.getModel() == null) {
	// // return;
	// // }
	// // commitValueChange(tf);
	// }
	//
	// // private void commitValueChange(final JFormattedTextField tf) {
	// // try {
	// // if (tf.getText().trim().equals("")) {
	// // tf.setText("0");
	// // }
	// // tf.commitEdit();
	// // } catch (Exception ex) {
	// // }
	// // }
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
	// System.out
	// .println("---------------render:" + tf.getValue());
	// tf.selectAll();
	// }
	// }
	// return comp;
	// }
	//
	// }
	/**
	 * 计算单项用量
	 */
	private void commitValueChange(DzscCustomsBomDetail detail) {
		// if (tableModel == null || currRow < 0) {
		// System.out.println("--------------value change is null:1");
		// return;
		// }
		// DzscCustomsBomDetail detail = (DzscCustomsBomDetail) tableModel
		// .getDataByRow(currRow);
		if (detail == null) {
			// System.out.println("--------------value change is null:2");
			return;
		}
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
		// System.out.println("--------------value change is not null:"
		// + unitWaste + "-------" + waste + "---" + unitUsed);
		// tableModel.fireTableDataChanged();
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBom == null
							|| tableModelBom.getList().size() <= 0) {
						return;
					}
					System.out.println("---------------before stopCellEditing");
					if (tbDown.getCellEditor() != null) {
						tbDown.getCellEditor().stopCellEditing();
					}
					System.out.println("---------------after stopCellEditing");
					tableModelBom.fireTableDataChanged();
					List list = tableModelBom.getList();
					for (int i = 0; i < list.size(); i++) {
						DzscCustomsBomDetail bom = (DzscCustomsBomDetail) list
								.get(i);
						System.out.println("---unitwaste:" + bom.getUnitWare()
								+ "--:" + bom.getWare() + "---:"
								+ bom.getUnitDosage());
						dzscInnerMergeAction.saveDzscCustomsBomDetail(
								new Request(CommonVars.getCurrUser()), bom);
					}
					JOptionPane.showMessageDialog(FmDzscCustomsBom.this,
							"保存数据成功！");
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExg.getCurrentRow() == null)
						return;
					DzscCustomsBomExg exg = (DzscCustomsBomExg) tableModelExg
							.getCurrentRow();
					// lbExgInfo.setText("当前成品序号为:" + exg.getSeqNum()
					// + " 名称为:" + exg.getName());
					List list = dzscInnerMergeAction.findDzscCustomsBomDetail(
							new Request(CommonVars.getCurrUser()), exg);
					initBomTable(list);
				}
			});
		}
		return btnCancel;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
