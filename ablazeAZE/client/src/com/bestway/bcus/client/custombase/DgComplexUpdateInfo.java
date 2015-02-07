package com.bestway.bcus.client.custombase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgComplexUpdateInfo extends JDialogBase {

	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel tableModel = null;// 表1
	private AttributiveCellTableModel tableModelDiff = null;// 表1
	// private List errLs = new ArrayList(); // @jve:decl-index=0:
	private JButton btnCheck = null;
	private JButton bnFind = null;
	private JButton btnDelete = null;
	private JButton bnSelectAll = null;
	private JButton bnNoSelect = null;
	private JButton bnTongBu = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JButton btnAllChaYiSerch = null;
	/**
	 * 商品编码操作接口
	 */
	private CustomBaseAction customBaseAction = null; // @jve:decl-index=0:
	private JLabel jLabel = null;

	/**
	 * This is the default constructor
	 */
	public DgComplexUpdateInfo() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		initialize();
		jTable = new MultiSpanCellTable();
		// jTable.getColumnModel().addColumnModelListener(
		// new TableColumnModelListener() {
		// /** Tells listeners that a column was added to the model. */
		// public void columnAdded(TableColumnModelEvent e) {
		// }
		//
		// /**
		// * Tells listeners that a column was removed from the model.
		// */
		// public void columnRemoved(TableColumnModelEvent e) {
		// }
		//
		// /** Tells listeners that a column was repositioned. */
		// public void columnMoved(TableColumnModelEvent e) {
		// }
		//
		// /**
		// * Tells listeners that a column was moved due to a margin
		// * change.
		// */
		// public void columnMarginChanged(ChangeEvent e) {
		// }
		//
		// public void columnSelectionChanged(ListSelectionEvent e) {
		// int[] columns = jTable.getSelectedColumns();
		// int[] rows = jTable.getSelectedRows();
		// if (columns.length < 1 || rows.length < 1) {
		// return;
		// }
		// if (columns[0] >= 0 && columns[0] <= 5) {
		// jTable.setColumnSelectionInterval(1, 5);
		// return;
		// }
		// // else if (columns[0] >= 6 && columns[0] <= 12) {
		// // jTable.setColumnSelectionInterval(6, 12);
		// // return;
		// // }
		// }
		// });
		jScrollPane.setViewportView(jTable);
		initTableDiff(new ArrayList());
	}

	// public void setVisible(boolean b) {
	// if (b) {
	//		
	// initTableDiff(new ArrayList());
	// }
	// super.setVisible(b);
	// }

	private JTableListModel initTableError(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("是否选择", "isSelected", 50, false));
				list.add(addColumn("编码", "code", 80));
				list.add(addColumn("商品名称", "name", 200));
				list.add(addColumn("第一法定单位", "firstUnit.name", 80));
				list.add(addColumn("第二法定单位", "secondUnit.name", 80));
				list.add(addColumn("许可证代码", "ccontrol", 70));
				list.add(addColumn("备注", "note", 230));
				return list;
			}
		};
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) jTable,
				list, jTableListModelAdapter);
		jTableListModelAdapter.setEditableColumn(1);
		TableColumn column = jTable.getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		return tableModel;

	}

	private JTableListModel initTableDiff(List list) {
		tableModelDiff = new AttributiveCellTableModel(
				(MultiSpanCellTable) jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("代码", "bill1", 80));
						list.add(addColumn("商品名称", "bill2", 100));
						list.add(addColumn("第一法定单位", "bill3", 60));
						list.add(addColumn("第二法定单位", "bill4", 60));
						list.add(addColumn("证单代码", "bill5", 60));

						list.add(addColumn("商品名称", "bill6", 100));
						list.add(addColumn("第一法定单位", "bill7", 60));
						list.add(addColumn("第二法定单位", "bill8", 60));
						list.add(addColumn("证单代码", "bill9", 60));
						list.add(addColumn("差异说明", "bill10", 230));
						return list;
					}
				});
		// jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		TableColumnModel cm = jTable.getColumnModel();

		ColumnGroup gBefore = new ColumnGroup("自用商品编码");
		gBefore.add(cm.getColumn(2));
		gBefore.add(cm.getColumn(3));
		gBefore.add(cm.getColumn(4));
		gBefore.add(cm.getColumn(5));
		ColumnGroup gAfter = new ColumnGroup("海关商品编码");
		gAfter.add(cm.getColumn(6));
		gAfter.add(cm.getColumn(7));
		gAfter.add(cm.getColumn(8));
		gAfter.add(cm.getColumn(9));

		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gBefore);
		header.addColumnGroup(gAfter);
		jTable.getColumnModel().getColumn(10)
		.setCellRenderer(new TableMultiRowRender());
		return tableModelDiff;

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(818, 517);
		this.setContentPane(getJContentPane());
		this.setTitle("海关与自用商编差异比较信息");
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
			jContentPane.add(getJPanel1(), BorderLayout.CENTER);
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
			jLabel = new JLabel();
			jLabel.setText("查询差异的信息：指查询10商品编码，名称、法定单位、证单代码对比");
			jToolBar = new JToolBar();
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getBtnAllChaYiSerch());
			jToolBar.add(getJButton());
			jToolBar.add(jLabel);
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("退出");
			jButton.setPreferredSize(new Dimension(85, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton;
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

	// public List getErrLs() {
	// return errLs;
	// }
	//
	// public void setErrLs(List errLs) {
	// this.errLs = errLs;
	// }

	/**
	 * This method initializes btnCheck
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCheck() {
		if (btnCheck == null) {
			btnCheck = new JButton();
			btnCheck.setText("1.查询错误商编");
			btnCheck.setBounds(new Rectangle(17, 28, 130, 22));
			btnCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new FindError().start();
				}
			});
		}
		return btnCheck;
	}

	/**
	 * 检查是否有错误编码
	 * 
	 * @author Administrator
	 * 
	 */
	class FindError extends Thread {
		public void run() {
			List list = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgComplexUpdateInfo.this);
				CommonProgress.setMessage("系统正在查询错误商编数据，请稍后...");
				list = customBaseAction.findComplexNotInCustomsComplex();
				if (list == null || list.size() == 0) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(null, "               "
							+ "查询数据完成，没有错误编码！", "提示！",
							JOptionPane.CLOSED_OPTION);
				}
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(null,
						"检查数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
				initTableError(list);
			}

		}
	}

	/**
	 * 查询变化商品
	 * 
	 * @author Administrator
	 * 
	 */
	class FindChanged extends Thread {
		public void run() {
			List list = new ArrayList();
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正查询差异的商品数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				list = customBaseAction.findChangedComplex(request);
				if (list == null || list.size() == 0) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(null, "               "
							+ "查询数据完成，没有存在差异的商品！", "提示！",
							JOptionPane.CLOSED_OPTION);
				}
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgComplexUpdateInfo.this,
						"查找数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
				initTableError(list);
			}

		}
	}

	/**
	 * 获取选中数据
	 * 
	 * @return
	 */
	private List getSelectData() {
		List lsResult = new ArrayList();
		if (tableModel == null) {
			return lsResult;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Complex) {
				Complex complex = (Complex) list.get(i);
				if (complex.getIsSelected() != null && complex.getIsSelected()) {
					lsResult.add(complex);
				}
			}
		}
		return lsResult;
	}

	/**
	 * 商品同步
	 * 
	 * @author Administrator
	 * 
	 */
	class UpdateComplex extends Thread {
		List selectList = new ArrayList();

		public UpdateComplex(List selectList) {
			this.selectList = selectList;
		}

		public void run() {
			List returnList = new ArrayList();
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正同步数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				returnList = customBaseAction
						.updateComplex(request, selectList);
				tableModel.setList(returnList);
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgComplexUpdateInfo.this,
						"数据同步完毕!", "提示", 2);
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgComplexUpdateInfo.this,
						"同步数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * 全选/全否
	 * 
	 * @param isSelected
	 */
	private void selectAll(boolean isSelected) {

		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Complex) {
				Complex complex = (Complex) list.get(i);
				complex.setIsSelected(isSelected);
			}
		}
		tableModel.setList(list);

	}

	/**
	 * This method initializes bnFind
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBnFind() {
		if (bnFind == null) {
			bnFind = new JButton();
			bnFind.setBounds(new Rectangle(52, 28, 140, 22));
			bnFind.setText("1.查询差异的商品");
			bnFind.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new FindChanged().start();
				}
			});
		}
		return bnFind;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("3.移除错误商编");
			btnDelete.setBounds(new Rectangle(153, 28, 125, 22));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					List list = tableModel.getCurrentRows();
					List lsDelete = new ArrayList();
//					if (list.size() < 0) {
//						JOptionPane.showMessageDialog(DgComplexUpdateInfo.this,
//								"请选择要删除的数据", "提示", JOptionPane.OK_OPTION);
//						return;
//					}
					List selectList = getSelectData();
					if (selectList == null || selectList.size() <= 0) {
						CommonStepProgress.closeStepProgressDialog();
						JOptionPane.showMessageDialog(DgComplexUpdateInfo.this,
								"请选择要删除的数据!", "提示", 2);
						return;
					}
					for (int i = 0; i < selectList.size(); i++) {
						if (selectList.get(i) instanceof Complex) {
							Complex complex = (Complex) selectList.get(i);
							try {
								customBaseAction.deleteComplex(complex);
								lsDelete.add(complex);
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(
										DgComplexUpdateInfo.this,
										"所选数据被其它地方引用，请先删除其引用！", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						}
					}
					tableModel.deleteRows(lsDelete);
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 检查更新
	 * 
	 * @author Administrator
	 * 
	 */
	class FindUpdateComplex extends Thread {
		public void run() {
			List errorLs = new ArrayList();
			try {
				btnAllChaYiSerch.setEnabled(false);
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在查询差异，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				errorLs = customBaseAction.findUpdateComplex(request);
				if (errorLs == null || errorLs.size() < 0) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(DgComplexUpdateInfo.this,
							"检查完毕,无任何需要变更!", "提示", 2);
				}
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgComplexUpdateInfo.this,
						"检查数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
				initTableDiff(errorLs);
				btnAllChaYiSerch.setEnabled(true);
			}
		}
	}

	/**
	 * This method initializes bnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBnSelectAll() {
		if (bnSelectAll == null) {
			bnSelectAll = new JButton();
			bnSelectAll.setText("2.全选");
			bnSelectAll.setBounds(new Rectangle(346, 6, 74, 22));
			bnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
				}
			});

		}
		return bnSelectAll;
	}

	/**
	 * This method initializes bnNoSelect
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBnNoSelect() {
		if (bnNoSelect == null) {
			bnNoSelect = new JButton();
			bnNoSelect.setText("2.全否");
			bnNoSelect.setBounds(new Rectangle(345, 34, 76, 22));
			bnNoSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(false);
				}
			});
		}
		return bnNoSelect;
	}

	/**
	 * This method initializes bnTongBu
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBnTongBu() {
		if (bnTongBu == null) {
			bnTongBu = new JButton();
			bnTongBu.setBounds(new Rectangle(231, 28, 100, 22));
			bnTongBu.setText("3.开始同步");
			bnTongBu.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List selectList = getSelectData();
					if (selectList == null || selectList.size() <= 0) {
						CommonStepProgress.closeStepProgressDialog();
						JOptionPane.showMessageDialog(DgComplexUpdateInfo.this,
								"请选择要同步的数据行!", "提示", 2);
						return;
					}
					new UpdateComplex(selectList).start();
				}
			});
		}
		return bnTongBu;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(1, 60));
			jPanel.add(getJPanel3(), null);
			jPanel.add(getJPanel4(), null);
			jPanel.add(getBnSelectAll(), null);
			jPanel.add(getBnNoSelect(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
			jPanel1.add(getJPanel(), BorderLayout.NORTH);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(new Rectangle(-3, 2, 347, 61));
			jPanel3
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u67e5\u627e\u81ea\u7528\u5546\u54c1\u7f16\u7801\uff08\u4ee3\u7801\uff09\u4e0d\u5b58\u5728\u6d77\u5173\u5546\u54c1\u7f16\u7801\uff08\u4ee3\u7801\uff09\u7684\u4fe1\u606f",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.PLAIN, 12),
									new Color(51, 51, 51)));
			jPanel3.add(getBtnCheck(), null);
			jPanel3.add(getBtnDelete(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBounds(new Rectangle(424, 3, 382, 60));
			jPanel4
					.setBorder(BorderFactory.createTitledBorder(null, "\u67e5\u627e\u6d77\u5173\u5546\u7f16\u4e0e\u81ea\u7528\u5546\u7f16\u5728\u4ee5\u4ee3\u7801\u76f8\u540c\uff0c\u540d\u79f0\u3001\u6cd5\u5b9a\u5355\u4f4d\u4e0d\u540c\u7684\u4fe1\u606f", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel4.add(getBnTongBu(), null);
			jPanel4.add(getBnFind(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes btnAllChaYiSerch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAllChaYiSerch() {
		if (btnAllChaYiSerch == null) {
			btnAllChaYiSerch = new JButton();
			btnAllChaYiSerch.setText("查询所有差异对比信息");
			btnAllChaYiSerch
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							new FindUpdateComplex().start();
						}
					});
		}
		return btnAllChaYiSerch;
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
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
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
			if (obj instanceof Complex) {
				Complex temp = (Complex) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
