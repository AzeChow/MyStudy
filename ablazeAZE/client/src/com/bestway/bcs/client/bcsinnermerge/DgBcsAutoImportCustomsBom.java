package com.bestway.bcs.client.bcsinnermerge;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomExg;
import com.bestway.bcs.bcsinnermerge.entity.BcsProductScaleInfo;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.tableeditor.JNumberForcedTableCellRenderer;
import com.bestway.bcus.client.common.tableeditor.JNumberTableCellEditor;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ParameterType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.TempEntBomVersion;
import com.bestway.ui.winuicontrol.JDialogBase;
@SuppressWarnings({"unchecked", "serial"})
public class DgBcsAutoImportCustomsBom extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	private JButton btnConfirm = null;

	private JButton btnCancel = null;

	private BcsInnerMergeAction bcsInnerMergeAction = null;

	private JTableListModel tableModelBomBill = null;

	private JTableListModel tableModelExg = null;

	private BcsCustomsBomExg exgBill = null;

	private NumberFormatter numberFormatter = null;

	private DefaultFormatterFactory defaultFormatterFactory = null;

	private JToolBar jJToolBarBar1 = null;

	private JLabel jLabel = null;
	
	private MaterialManageAction materialManageAction = null;
	
	private SystemAction systemAction = null;

	public BcsCustomsBomExg getExgBill() {
		return exgBill;
	}

	public void setExgBill(BcsCustomsBomExg exgBill) {
		this.exgBill = exgBill;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgBcsAutoImportCustomsBom() {
		super();
		initialize();
		bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("bcsInnerMergeAction");
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		systemAction = (SystemAction) CommonVars
		.getApplicationContext().getBean("systemAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(720, 465));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("自动计算单耗");
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
			jSplitPane.setDividerLocation(200);
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
			jPanel.add(getJJToolBarBar1(), BorderLayout.NORTH);
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
			tbUnitWaste.setRowHeight(25);
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
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setLayout(f);
			jJToolBarBar.add(getBtnRefresh());
			jJToolBarBar.add(getBtnCalUnitWaste());
			jJToolBarBar.add(getBtnConfirm());
			jJToolBarBar.add(getBtnCancel());
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
			btnCalUnitWaste.setText("计算单耗");
			btnCalUnitWaste.setPreferredSize(new Dimension(60, 30));
			btnCalUnitWaste
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tbBomExg.getCellEditor() != null) {
								tbBomExg.getCellEditor().stopCellEditing();
							}
							if (tableModelExg == null
									|| tableModelExg.getList().size() <= 0) {
								JOptionPane.showMessageDialog(
										DgBcsAutoImportCustomsBom.this,
										"没有成品资料,所以不能计算单耗", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							/*
							 * 查询出bom版本管理方式 
							 */
							List paras = systemAction.findnameValues(new Request(
									CommonVars.getCurrUser()),
									ParameterType.bomStructureType);
							ParameterSet para = null;
							if (paras.isEmpty()) {
								para = new ParameterSet();
								para.setType(ParameterType.bomStructureType);
							} else {
								para = (ParameterSet) paras.get(0);
							}
							// bom管理类型
							String type = para.getNameValues();
							
							List lsExg = tableModelExg.getCurrentRows();
							if(lsExg == null || lsExg.size() ==0) {
								throw new RuntimeException("必须选择一个成品！");
							}
							Double totalAmount = 0.0;
							for (int i = 0; i < lsExg.size(); i++) {
								BcsProductScaleInfo info = (BcsProductScaleInfo) lsExg
										.get(i);
								totalAmount += (info.getAmount() == null ? 0.0
										: info.getAmount());
							}
							System.out.println("计算音耗");
							// 检查
							List list = bcsInnerMergeAction
									.autoCalDzscCustomsBom(new Request(
											CommonVars.getCurrUser(), true),
											lsExg, exgBill, type == null ? 0
													: Integer.parseInt(type));
							System.out.println("计算音耗");
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
			btnRefresh.setPreferredSize(new Dimension(84, 30));
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
			tbBomExg.setRowHeight(25);
		}
		return tbBomExg;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnConfirm() {
		if (btnConfirm == null) {
			btnConfirm = new JButton();
			btnConfirm.setText("确定");
			btnConfirm.setPreferredSize(new Dimension(60, 30));
			btnConfirm.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBomBill == null
							|| tableModelBomBill.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								DgBcsAutoImportCustomsBom.this, "当前成品没有单耗资料",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (tbUnitWaste.getCellEditor() != null) {
						tbUnitWaste.getCellEditor().stopCellEditing();
					}
					List list = tableModelBomBill.getList();
					bcsInnerMergeAction.saveAutoCalBcsCustomsBomDetail(
							new Request(CommonVars.getCurrUser(), true), list,
							exgBill);
					dispose();
				}
			});
		}
		return btnConfirm;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.setPreferredSize(new Dimension(60, 30));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	private void initTableExgBill(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("成品料号", "parentNo", 150));
				list.add(addColumn("分配数量", "amount", 100));
				list.add(addColumn("比例", "scale", 100));
				list.add(addColumn("成品版本号", "version", 100));
				list.add(addColumn("成品版本失效期", "endDate", 100));
				return list;
			}
		};
		tableModelExg = new JTableListModel(tbBomExg, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		jTableListModelAdapter.setEditableColumn(2);
		/*
		 * 查询出bom版本管理方式
		 */
		List paras = systemAction.findnameValues(new Request(
				CommonVars.getCurrUser()),
				ParameterType.bomStructureType);
		ParameterSet para = null;
		if (paras.isEmpty()) {
			para = new ParameterSet();
			para.setType(ParameterType.bomStructureType);
		} else {
			para = (ParameterSet) paras.get(0);
		}
		// bom管理类型
		String type = para.getNameValues();
		if(CommonUtils.isEmpty(type) || "0".equals(type) || "2".equals(type)) {
			jTableListModelAdapter.setEditableColumn(4);
			tbBomExg.getColumnModel().getColumn(4).setCellEditor(
					new DefaultCellEditor(new JComboBox()) 
			{
				private static final long serialVersionUID = 1L;
				public Component getTableCellEditorComponent(JTable table,
						Object value, boolean isSelected, int row,
						int column) {
					JComboBox cbb = (JComboBox) super.getComponent();
					cbb.removeAllItems();
					Object tmp = table.getModel().getValueAt(row, 1);
					// 料号
					String ptNo = tmp == null ? "" : tmp.toString();
					// bom管理类型
					int bomStructureType = 0;
					// 查询出该料号的所有版本
					List<TempEntBomVersion> list = materialManageAction
							.findMaterialBomVersion(new Request(
									CommonVars.getCurrUser(), true), ptNo,
									bomStructureType);
					if (list != null) {
						List<String> tmpList = new ArrayList<String>(list.size());
						for (int i = 0; i < list.size(); i++) {
							tmpList.add(list.get(i).getVersion() + "");
						}
						cbb.setModel(new DefaultComboBoxModel(tmpList
								.toArray()));
					}
					return super.getTableCellEditorComponent(table, value,
							isSelected, row, column);
				}
			});
			
			tbBomExg.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 1L;
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					Object version = tbBomExg.getColumnModel().getColumn(4).getCellEditor().getCellEditorValue();
					if(version != null && !version.equals("")) {
						BcsProductScaleInfo o = (BcsProductScaleInfo) tableModelExg.getCurrentRow();
						BcsProductScaleInfo tmp = (BcsProductScaleInfo) o.clone();
						tmp.setVersion(version.toString());
						tableModelExg.updateRow(tmp);
					}
					
					return super.getTableCellRendererComponent(table,
							value, isSelected, hasFocus, row, column);
				}
			});
		} else {
			jTableListModelAdapter.setEditableColumn(5);
			tbBomExg.getColumnModel().getColumn(5).setCellEditor(
					new DefaultCellEditor(new JComboBox()) 
			{
				private static final long serialVersionUID = 1L;
				public Component getTableCellEditorComponent(JTable table,
						Object value, boolean isSelected, int row,
						int column) {
					JComboBox cbb = (JComboBox) super.getComponent();
					cbb.removeAllItems();
					Object tmp = table.getModel().getValueAt(row, 1);
					// 料号
					String ptNo = tmp == null ? "" : tmp.toString();
					// 查询出该料号的所有版本
					List<TempEntBomVersion> list = materialManageAction
							.findMaterialBomVersionDate(new Request(
									CommonVars.getCurrUser(), true), ptNo);
					if (list != null) {
						List<Date> tmpList = new ArrayList<Date>(list.size());
						for (int i = 0; i < list.size(); i++) {
							tmpList.add(list.get(i).getEndDate());
						}
						cbb.setModel(new DefaultComboBoxModel(tmpList
								.toArray()));
					}
					System.out.println(value);
					return super.getTableCellEditorComponent(table, value,
							isSelected, row, column);
				}
			});
			
			tbBomExg.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 1L;
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					Object version = tbBomExg.getColumnModel().getColumn(5).getCellEditor().getCellEditorValue();
					if(version != null) {
						BcsProductScaleInfo o = (BcsProductScaleInfo) tableModelExg.getCurrentRow();
						BcsProductScaleInfo tmp = (BcsProductScaleInfo) o.clone();
						tmp.setEndDate(o.getEndDate());
						tableModelExg.updateRow(tmp);
					}
					System.out.println(value);
					return super.getTableCellRendererComponent(table,
							value, isSelected, hasFocus, row, column);
				}
			});
		}
		
		
		
		tableModelExg.setAllowSetValue(true);
		final JFormattedTextField tf = new JFormattedTextField();
		tf.setFormatterFactory(getDefaultFormatterFactory());
		tbBomExg.getColumnModel().getColumn(2).setCellEditor(
				new JNumberTableCellEditor(){
					public void focusLost(FocusEvent e) {
						List<BcsProductScaleInfo> list = tableModelExg.getList();
						tableModelExg.updateRows(updateTableModelExg(list));
					}
					
				});
		tbBomExg.getColumnModel().getColumn(2).setCellRenderer(
				new JNumberForcedTableCellRenderer());
		tbBomExg.setRowHeight(20);
	}
	
	/**
	 * 更新比例
	 * @param list
	 * @return
	 */
	private List updateTableModelExg(List<BcsProductScaleInfo> list){
		double sum =0;
		for(BcsProductScaleInfo info : list){
			sum += CommonUtils.getDoubleExceptNull(info.getAmount());
		}
		if(sum>0){
			for(BcsProductScaleInfo info : list){
				info.setScale(CommonUtils.getDoubleExceptNull(info.getAmount())/sum);
			}
		}
		return list;
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
						list.add(addColumn("归并序号", "tenSeqNum", 60,
								Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("料件名称", "name", 150));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("单耗", "unitWare", 70));
						list.add(addColumn("损耗", "ware", 70));
						list.add(addColumn("单项用量", "unitDosage", 80));
						list.add(addColumn("单位", "unit.name", 40));
						return list;
					}
				});
	}

	private void showExgData() {
		List list = this.bcsInnerMergeAction.findMaterialExgByCustomsBomExg(
				new Request(CommonVars.getCurrUser(), true), exgBill);
		initTableExgBill(updateTableModelExg(list));
	}

	/**
	 * This method initializes jJToolBarBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar1() {
		if (jJToolBarBar1 == null) {
			jLabel = new JLabel();
			jLabel.setText("假定成品总数量是100，请在下面按照不同成品不同版本的百分比进行数量分配");
			jLabel.setForeground(Color.blue);
			jJToolBarBar1 = new JToolBar();
			jJToolBarBar1.add(jLabel);
		}
		return jJToolBarBar1;
	}

	// /**
	// * 编辑列
	// */
	// class JTextFieldEditor extends DefaultCellEditor implements KeyListener,
	// FocusListener {
	//
	// private JFormattedTextField tf;
	//
	// private JTable table = null;
	//
	// public JTextFieldEditor(JFormattedTextField tf) {
	// super(tf);
	// this.tf = tf;
	// this.setClickCountToStart(1);
	// this.tf.addKeyListener(this);
	// this.tf.addFocusListener(this);
	// }
	//
	// public Component getTableCellEditorComponent(JTable table,
	// Object value, boolean isSelected, int row, int column) {
	// this.table = table;
	// this.tf = (JFormattedTextField) super.getTableCellEditorComponent(
	// table, value, isSelected, row, column);
	// if (value == null) {
	// this.tf.setValue(0.0);
	// }
	// return this.tf;
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
	// if (table == null || table.getModel() == null) {
	// return;
	// }
	// commitValueChange(tf);
	// }
	//
	// public void focusLost(FocusEvent e) {
	// // if (table == null || table.getModel() == null) {
	// // return;
	// // }
	// // commitValueChange(tf);
	// }
	//
	// private void commitValueChange(final JFormattedTextField tf) {
	// try {
	// if (tf.getText().trim().equals("")) {
	// tf.setText("0");
	// }
	// tf.commitEdit();
	// } catch (Exception ex) {
	// }
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
	// return comp;
	// }
	//
	// }
} // @jve:decl-index=0:visual-constraint="10,10"
