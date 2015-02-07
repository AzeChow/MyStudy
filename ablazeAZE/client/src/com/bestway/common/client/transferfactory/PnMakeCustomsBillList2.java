/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.transferfactory;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.entity.TempTransferFactoryBill;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnMakeCustomsBillList2 extends PnCommon {

	private PnMakeCustomsBillList2 pnMakeCustomsEnvelopBill2 = null;

	private ScmCoc scmCoc = null;

	private JTableListModel tableModel = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnSelectAll = null;

	private JButton btnNotSelectAll = null;

	private String emsNo = null;

	/**
	 * This is the default constructor
	 */
	public PnMakeCustomsBillList2() {
		super();
		initialize();
	}

	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			initTable();
		}
		super.setVisible(isFlag);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		javax.swing.JLabel jLabel = new JLabel();
		this.setLayout(null);
		this.setSize(500, 248);
		jLabel.setBounds(13, 11, 115, 21);
		jLabel.setText("可供选择的单据");
		this.add(jLabel, null);
		this.add(getJScrollPane(), null);
		this.add(getBtnSelectAll(), null);
		this.add(getBtnNotSelectAll(), null);
	}

	/**
	 * @return Returns the scmCoc.
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	/**
	 * @return Returns the pnMakeCustomsEnvelopBill2.
	 */
	public PnMakeCustomsBillList2 getPnMakeCustomsEnvelopBill2() {
		return pnMakeCustomsEnvelopBill2;
	}

	/**
	 * @param pnMakeCustomsEnvelopBill2
	 *            The pnMakeCustomsEnvelopBill2 to set.
	 */
	public void setPnMakeCustomsEnvelopBill2(
			PnMakeCustomsBillList2 pnMakeCustomsEnvelopBill2) {
		this.pnMakeCustomsEnvelopBill2 = pnMakeCustomsEnvelopBill2;
	}

	/**
	 * @param scmCoc
	 *            The scmCoc to set.
	 */
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
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
	 * This method initializes btnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setBounds(361, 11, 62, 21);
			btnSelectAll.setText("全选");
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PnMakeCustomsBillList2.this.setCursor(new Cursor(
							Cursor.WAIT_CURSOR));
					selectAll(true);
					PnMakeCustomsBillList2.this.setCursor(new Cursor(
							Cursor.DEFAULT_CURSOR));

				}
			});
		}
		return btnSelectAll;
	}

	/**
	 * This method initializes btnNotSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelectAll() {
		if (btnNotSelectAll == null) {
			btnNotSelectAll = new JButton();
			btnNotSelectAll.setBounds(427, 11, 62, 21);
			btnNotSelectAll.setText("不选");
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							PnMakeCustomsBillList2.this.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							selectAll(false);
							PnMakeCustomsBillList2.this.setCursor(new Cursor(
									Cursor.DEFAULT_CURSOR));
						}
					});
		}
		return btnNotSelectAll;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(2, 39, 497, 209);
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * 选中所有 or 清除所有选择
	 */
	private void selectAll(boolean isSelected) {
		if (this.tableModel == null) {
			return;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempTransferFactoryBill) {
				TempTransferFactoryBill t = (TempTransferFactoryBill) list
						.get(i);
				t.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModel.updateRows(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable() {
		List list = super.transferFactoryManageAction
				.findTempTransferFactoryBillByScmCocNotATC(new Request(
						CommonVars.getCurrUser()), this.scmCoc.getId(),
						this.emsNo);
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("单据号", "t.transferFactoryBillNo", 100));
				list.add(addColumn("生效", "t.isAvailability", 50));
				list.add(addColumn("是否转报关清单", "t.isApplyToCustomsBill", 100));
				list.add(addColumn("是否关封申请单", "t.isCustomsEnvelopRequestBill",
						90));
				list.add(addColumn("生效日期", "t.beginAvailability", 80));
				list.add(addColumn("客户/供应商名称", "t.scmCoc.name", 150));
				list.add(addColumn("仓库名称", "t.wareSet.name", 150));
				list.add(addColumn("商品项数", "t.itemCount", 80));
				list.add(addColumn("录入人员", "t.aclUser.name", 80));
				list.add(addColumn("备注", "t.memo", 100));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new MultiRenderer());
	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				return this;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
				checkBox.setHorizontalAlignment(SwingConstants.CENTER);
				checkBox.setBackground(table.getBackground());
				if (isSelected) {
					checkBox.setForeground(table.getSelectionForeground());
					checkBox.setBackground(table.getSelectionBackground());
				} else {
					checkBox.setForeground(table.getForeground());
					checkBox.setBackground(table.getBackground());
				}
				return checkBox;
			}
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
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
			if (obj instanceof TempTransferFactoryBill) {
				TempTransferFactoryBill temp = (TempTransferFactoryBill) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * 获得选中关封申请单中的信息
	 */
	public List getParentList() {
		if (this.tableModel == null) {
			return null;
		}
		List list = tableModel.getList();
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempTransferFactoryBill) {
				TempTransferFactoryBill t = (TempTransferFactoryBill) list
						.get(i);
				if (t.getIsSelected().booleanValue() == true) {
					newList.add(t);
				}
			}
		}
		return newList;
	}
	/**
	 * 检查选中的转厂单是否属于同一关封
	 * @return
	 */
	public boolean checkSelectedList() {
		List list = this.getParentList();
		String code = "";
		if (list.size() > 0) {
			TempTransferFactoryBill t = (TempTransferFactoryBill) list.get(0);
			code = t.getT().getEnvelopNo();
		}
		for (int i = 0; i < list.size(); i++) {
			TempTransferFactoryBill t = (TempTransferFactoryBill) list.get(i);
			if (!t.getT().getEnvelopNo().equals(code)) {
				return false;
			}
		}
		return true;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

}
