package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.TempFptAppheadAndOrder;

public class PnMakeCustomsOrderToFptAppHead2 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JToolBar jJToolBarBar = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTableListModel tableModel = null;
	private FptManageAction fptManageAction = null;
	private List tempFptAppheadAndOrderList = new ArrayList(); // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public PnMakeCustomsOrderToFptAppHead2() {
		super();
		this.fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(630, 355);
		this.setLayout(new BorderLayout());
		this.add(getJJToolBarBar(), BorderLayout.NORTH);
		this.add(getJScrollPane(), BorderLayout.CENTER);
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
	 * 初始化数据参数 tb
	 */
	private void initTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 50));
				list.add(addColumn("转出数量", "tempAmount", 50));
				list.add(addColumn("订单号", "detial.customOrder.billCode", 150));
				list.add(addColumn("生效", "detial.customOrder.ifok", 50));
				list.add(addColumn("商品名称", "detial.bgname", 150));
				list.add(addColumn("规格型号", "detial.bgspec", 150));
				list.add(addColumn("单位", "detial.bgunit.name", 60));
				list.add(addColumn("币制", "detial.curr.name", 60));
				list.add(addColumn("数量", "detial.amount", 60));
				list.add(addColumn("已转厂数量", "detial.transNum", 80));
				list.add(addColumn("未转厂数量", "detial.notTransNum", 80));
				list.add(addColumn("单价", "detial.unitPrice", 60));
				list.add(addColumn("总价", "detial.totalPrice", 60));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new TableCheckBoxRender());
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
			jButton.setText("全选");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					doAllSelected(true);
				}
			});
		}
		return jButton;
	}

	private void doAllSelected(boolean f) {
		List list = this.tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			TempFptAppheadAndOrder tfao = (TempFptAppheadAndOrder) list.get(i);
			tfao.setIsSelected(f);
		}
		this.repaint();
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("全否");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					doAllSelected(false);
				}
			});
		}
		return jButton1;
	}

	public void initPn2Table(List orderList) {
		List dataSource = fptManageAction
				.findCustomOrderForToFptAppHead(new Request(CommonVars
						.getCurrUser()), orderList);
		initTable(dataSource);
	}

	public List getTempFptAppheadAndOrderList() {
		tempFptAppheadAndOrderList.clear();
		List list = this.tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			TempFptAppheadAndOrder tfao = (TempFptAppheadAndOrder) list.get(i);
			if (tfao.getIsSelected() != null && tfao.getIsSelected()) {
				tempFptAppheadAndOrderList.add(tfao);
			}
		}
		return tempFptAppheadAndOrderList;
	}

	/**
	 * 编辑列
	 */
	/**
	 * 编辑列
	 */

	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		@Override
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
			cb.setHorizontalAlignment(SwingConstants.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		@Override
		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof TempFptAppheadAndOrder) {
				TempFptAppheadAndOrder temp = (TempFptAppheadAndOrder) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
//				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

	}
} // @jve:decl-index=0:visual-constraint="10,10"
