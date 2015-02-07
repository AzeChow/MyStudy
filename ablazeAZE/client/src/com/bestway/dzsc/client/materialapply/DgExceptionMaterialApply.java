package com.bestway.dzsc.client.materialapply;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgExceptionMaterialApply extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jJToolBarBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private MaterialApplyAction materialApplyAction = null;

	private JTableListModel tableModel = null;

	private List dataSource = new ArrayList();  //  @jve:decl-index=0:

	public List getDataSource() {
		return dataSource;
	}

	public void setDataSource(List dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgExceptionMaterialApply() {
		super();
		initialize();
		materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(666, 412));
		this.setContentPane(getJContentPane());
		this.setTitle("异常物料备案资料");

	}

	public void setVisible(boolean b) {
		if (b) {
			this.initTable(this.dataSource);
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
			jContentPane.add(getJJToolBarBar(), BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getJButton());
			jJToolBarBar.add(getJButton1());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("删除这些异常的物料备案资料");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(
							DgExceptionMaterialApply.this, "确定要删除这些异常资料？",
							"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					materialApplyAction.deleteExceptionMaterialApply(
							new Request(CommonVars.getCurrUser(), true),
							dataSource);
					dataSource.clear();
					tableModel.setList(dataSource);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
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
		if (list == null) {
			list = new Vector();
		}
		JTableListModel.dataBind(jTable, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("序号", "no", 50, Integer.class));
				list.add(addColumn("类别", "scmCoi.name", 80));
				list.add(addColumn("料号", "ptNo", 100));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("工厂商品名称", "factoryName", 100));
				list.add(addColumn("工厂型号规格", "factorySpec", 100));
				list.add(addColumn("工厂单位", "calUnit.name", 50));
				list.add(addColumn("报关单位", "ptUnit.name", 50));
				list.add(addColumn("单价", "ptPrice", 50));
				list.add(addColumn("净重", "ptNetWeight", 50));
				// list.add(addColumn("状态标志", "stateMark", 100));
				// list.add(addColumn("修改标志", "modifyMark", 100));
				return list;
			}
		});// , ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
		tableModel = (JTableListModel) jTable.getModel();
		this.jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value == null) {
							this.setBackground(Color.RED);
						}
						return this;
					}
				});
		this.jTable.getColumnModel().getColumn(8).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value == null) {
							this.setBackground(Color.RED);
						}
						return this;
					}
				});
		// TableColumn column = this.jTable.getColumnModel().getColumn(11);
		// column.setCellRenderer(new DefaultTableCellRenderer() {
		// public Component getTableCellRendererComponent(JTable table,
		// Object value, boolean isSelected, boolean hasFocus,
		// int row, int column) {
		// super.getTableCellRendererComponent(table, value, isSelected,
		// hasFocus, row, column);
		// String state = "";
		// if (value != null) {
		// state = value.toString();
		// }
		// if (state.equals(DzscState.ORIGINAL)) {
		// this.setText("初始状态");
		// } else if (state.equals(DzscState.APPLY)) {
		// this.setText("申报状态");
		// }
		// if (state.equals(DzscState.EXECUTE)) {
		// this.setText("生效状态");
		// }
		// if (state.equals(DzscState.CHANGE)) {
		// this.setText("变更状态");
		// }
		// return this;
		// }
		// });
		// this.jTable.getColumnModel().getColumn(12).setCellRenderer(
		// new DefaultTableCellRenderer() {
		// public Component getTableCellRendererComponent(
		// JTable table, Object value, boolean isSelected,
		// boolean hasFocus, int row, int column) {
		// super.getTableCellRendererComponent(table, value,
		// isSelected, hasFocus, row, column);
		// String state = "";
		// if (value != null) {
		// state = value.toString();
		// }
		// if (state.equals(ModifyMarkState.UNCHANGE)) {
		// this.setText("未修改");
		// } else if (state.equals(ModifyMarkState.ADDED)) {
		// this.setText("新增");
		// }
		// if (state.equals(ModifyMarkState.MODIFIED)) {
		// this.setText("已修改");
		// }
		// if (state.equals(ModifyMarkState.DELETED)) {
		// this.setText("已删除");
		// }
		// return this;
		// }
		// });
		return tableModel;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
