/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JPanelBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnMakeDzscCustomsDeclaration2 extends JPanelBase {

	private PnMakeDzscCustomsDeclaration2 pnMakeCustomsEnvelopBill2 = null;
	private int impExpType = -1;
	private JTableListModel tableModel = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JButton btnSelectAll = null;
	private JButton btnNotSelectAll = null;
	private EncAction encAction = null;
	private boolean isexe = false;
	private JComboBox jComboBox = null;
	private List list = null;
	private JButton jButton = null;

	/**
	 * This is the default constructor
	 */
	public PnMakeDzscCustomsDeclaration2() {
		super();
		this.encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		initialize();
	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			list = this.encAction.findTempImpExpRequestBillByImpExpTypeToATC(
					new Request(CommonVars.getCurrUser()), this.impExpType); //
			initTable(list);
			isexe = false;
			initCombox(list);
		}
		super.setVisible(isFlag);
		isexe = true;
	}

	// 初始化Combox
	public void initCombox(List list) {
		this.jComboBox.removeAllItems();
		for (int i = 0; i < list.size(); i++) {
			TempImpExpRequestBill temp = (TempImpExpRequestBill) list.get(i);
			if (temp.getImpExpRequestBill() == null) {
				continue;
			}
			this.jComboBox.addItem(temp.getImpExpRequestBill().getBillNo());
		}
		this.jComboBox.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		javax.swing.JLabel jLabel = new JLabel();
		this.setLayout(null);
		this.setSize(630, 248);
		jLabel.setBounds(13, 11, 93, 21);
		jLabel.setText("可供选择的单据");
		this.add(jLabel, null);
		this.add(getJScrollPane(), null);
		this.add(getBtnSelectAll(), null);
		this.add(getBtnNotSelectAll(), null);
		this.add(getJComboBox(), null);
		this.add(getJButton(), null);
	}

	/**
	 * @return Returns the impExpType.
	 */
	public int getImpExpType() {
		return impExpType;
	}

	/**
	 * @param impExpType
	 *            The impExpType to set.
	 */
	public void setImpExpType(int impExpType) {
		this.impExpType = impExpType;
	}

	/**
	 * @return Returns the pnMakeCustomsEnvelopBill2.
	 */
	public PnMakeDzscCustomsDeclaration2 getPnMakeCustomsEnvelopBill2() {
		return pnMakeCustomsEnvelopBill2;
	}

	/**
	 * @param pnMakeCustomsEnvelopBill2
	 *            The pnMakeCustomsEnvelopBill2 to set.
	 */
	public void setPnMakeCustomsEnvelopBill2(
			PnMakeDzscCustomsDeclaration2 pnMakeCustomsEnvelopBill2) {
		this.pnMakeCustomsEnvelopBill2 = pnMakeCustomsEnvelopBill2;
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
			btnSelectAll.setBounds(471, 13, 62, 21);
			btnSelectAll.setText("全选");
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PnMakeDzscCustomsDeclaration2.this.setCursor(new Cursor(
							Cursor.WAIT_CURSOR));
					selectAll(true);
					PnMakeDzscCustomsDeclaration2.this.setCursor(new Cursor(
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
			btnNotSelectAll.setBounds(537, 13, 62, 21);
			btnNotSelectAll.setText("不选");
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							PnMakeDzscCustomsDeclaration2.this
									.setCursor(new Cursor(Cursor.WAIT_CURSOR));
							selectAll(false);
							PnMakeDzscCustomsDeclaration2.this
									.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
			jScrollPane.setBounds(2, 39, 628, 209);
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
			if (list.get(i) instanceof TempImpExpRequestBill) {
				TempImpExpRequestBill t = (TempImpExpRequestBill) list.get(i);
				t.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModel.updateRows(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new Vector();
		}
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("单据号", "impExpRequestBill.billNo", 100));
				list.add(addColumn("生效日期",
						"impExpRequestBill.beginAvailability", 80));
				list
						.add(addColumn("有效",
								"impExpRequestBill.isAvailability", 50));
				list.add(addColumn("已转报关清单", "impExpRequestBill.isCustomsBill",
						80));
				list.add(addColumn("项目个数", "impExpRequestBill.itemCount", 60));
				list.add(addColumn("仓库名称", "impExpRequestBill.wareSet.name",
						100));
				list.add(addColumn("客户/供应商名称", "impExpRequestBill.scmCoc.name",
						150));
				list.add(addColumn("运输工具", "impExpRequestBill.conveyance", 60));
				list.add(addColumn("录入日期", "impExpRequestBill.imputDate", 80));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new MultiRenderer());
	}

	/**
	 * render table JchcckBox 列
	 */
	public class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				return this;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
				checkBox.setHorizontalAlignment(JLabel.CENTER);
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
			if (obj instanceof TempImpExpRequestBill) {
				TempImpExpRequestBill temp = (TempImpExpRequestBill) obj;
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
			if (list.get(i) instanceof TempImpExpRequestBill) {
				TempImpExpRequestBill t = (TempImpExpRequestBill) list.get(i);
				if (t.getIsSelected().booleanValue() == true) {
					newList.add(t);
				}
			}
		}
		return newList;
	}

	/**
	 * 验证
	 * 
	 * @return
	 */
	public boolean vaildateData() {
		if (this.getParentList() == null || this.getParentList().size() <= 0) {
			JOptionPane.showMessageDialog(this, "请选择进出口申请单据!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setSelectedIndex(-1);
			jComboBox.setBounds(107, 11, 141, 21);
			jComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (jComboBox.getSelectedItem() == null
							|| jComboBox.getSelectedItem().equals("")
							|| isexe == false) {
						return;
					}
					String billNo = (String) jComboBox.getSelectedItem();
					list = encAction
							.findTempImpExpRequestBillByImpExpTypeToATC(
									new Request(CommonVars.getCurrUser()),
									impExpType);
					for (int i = list.size() - 1; i >= 0; i--) {
						TempImpExpRequestBill temp = (TempImpExpRequestBill) list
								.get(i);
						if (temp.getImpExpRequestBill() != null
								&& !temp.getImpExpRequestBill().getBillNo()
										.equals(billNo)) {
							temp.setIsSelected(new Boolean(false));
							list.remove(i);
						} else if (temp.getImpExpRequestBill() != null
								&& temp.getImpExpRequestBill().getBillNo()
										.equals(billNo)) {
							temp.setIsSelected(new Boolean(true));
						}
					}
					initTable(list);
				}

			});
		}
		return jComboBox;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(376, 13, 92, 21);
			jButton.setText("显示全部");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jComboBox.setSelectedIndex(-1);
					list = encAction
							.findTempImpExpRequestBillByImpExpTypeToATC(
									new Request(CommonVars.getCurrUser()),
									impExpType);

					initTable(list);
				}
			});
		}
		return jButton;
	}
}
