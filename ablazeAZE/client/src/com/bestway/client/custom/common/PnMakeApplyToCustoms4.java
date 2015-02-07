/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.entity.TempCustomsList;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.ui.winuicontrol.JPanelBase;

/**
 * @author Administrator
 * 
 * Preferences - Java - Code Style - Code Templates
 */
public class PnMakeApplyToCustoms4 extends JPanelBase {
	private JTableListModel tableModel = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private List parentList = null; // @jve:decl-index=0:

	private JButton btnSelectAll = null;

	private JButton btnNotSelectAll = null;

	private JPanel jPanel = null;

	private JLabel jLabel1 = null;
	private CompanyOther other = CommonVars.getOther();
	/**
	 * This is the default constructor
	 */
	public PnMakeApplyToCustoms4() {
		super();
		initialize();
	}

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
		this.setLayout(new BorderLayout());
		this.setSize(630, 248);
		this.add(getJScrollPane(), BorderLayout.CENTER);
		this.add(getJPanel(), BorderLayout.NORTH);
	}

	/**
	 * @return Returns the parentList.
	 */
	public List getParentList() {
		return parentList;
	}

	/**
	 * @param parentList
	 *            The parentList to set.
	 */
	public void setParentList(List parentList) {
		this.parentList = parentList;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModel == null)
								return;
							if (tableModel.getCurrentRow() == null)
								return;
						}
					});
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						// edit();
					}
				}
			});
		}
		return jTable;
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
	 * This method initializes btnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全选");
			btnSelectAll.setPreferredSize(new Dimension(60, 25));
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
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
			btnNotSelectAll.setText("不选");
			btnNotSelectAll.setPreferredSize(new Dimension(60, 25));
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectAll(false);
						}
					});
		}
		return btnNotSelectAll;
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
			if (list.get(i) instanceof TempCustomsList) {
				TempCustomsList temp = (TempCustomsList) list.get(i);
				temp.setIsSelected(isSelected);
			}
		}
		tableModel.updateRows(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable() {
		
		List list = parentList;
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("帐册序号", "afterinfo.emsSerialNo", 60));
				list.add(addColumn("商品编码", "afterinfo.complex.code", 80));
				list.add(addColumn("名称", "afterinfo.complexName", 120));
				list.add(addColumn("产销国", "afterinfo.country.name", 120));
				list.add(addColumn("版本号", "afterinfo.version", 60));
				list.add(addColumn("规格型号", "afterinfo.complexSpec", 120));
				list.add(addColumn("单位", "afterinfo.unit.name", 60));
				list.add(addColumn("折算报关数量", "afterinfo.declaredAmount", 80));
				list.add(addColumn("单价", "afterinfo.price", 60));
				list.add(addColumn("总金额", "afterinfo.totalPrice", 60));
				list.add(addColumn("毛重", "afterinfo.grossWeight", 60));
				list.add(addColumn("净重", "afterinfo.netWeight", 60));
				list.add(addColumn("件数", "afterinfo.piece", 60));

				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		jTable.getColumnModel().getColumn(9).setCellRenderer(
				new DefaultTableCellRenderer(){
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							if(other != null){
								this.setValue(CommonUtils.getDoubleByDigit(Double.valueOf(value.toString()), other.getCustomAmountNum()));
							}
						} else {
								this.setValue(CommonUtils.getDoubleByDigit(Double.valueOf(value.toString()), 5));
						}
						return this;
					}
				});
		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new DefaultTableCellRenderer(){
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							if(other != null){
								this.setValue(CommonUtils.getDoubleByDigit(Double.valueOf(value.toString()), other.getCustomPriceNum()));
							}
						} else {
								this.setValue(CommonUtils.getDoubleByDigit(Double.valueOf(value.toString()), 4));
						}
						return this;
					}
				});
		jTable.getColumnModel().getColumn(11).setCellRenderer(
				new DefaultTableCellRenderer(){
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							if(other != null){
								this.setValue(CommonUtils.getDoubleByDigit(Double.valueOf(value.toString()), other.getCustomTotalNum()));
							}
						} else {
								this.setValue(CommonUtils.getDoubleByDigit(Double.valueOf(value.toString()), 4));
						}
						return this;
					}
				});
	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
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
				System.out.println(Boolean.valueOf(value.toString())
						.booleanValue());
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
			if (obj instanceof TempCustomsList) {
				TempCustomsList temp = (TempCustomsList) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

	}

	/**
	 * 获得选中关封申请单中商品信息的信息
	 */
	public List getCommodityList() {
		List newList = new ArrayList();
		if (this.tableModel == null) {
			return newList;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempCustomsList) {
				TempCustomsList temp = (TempCustomsList) list.get(i);
				if (temp.getIsSelected().booleanValue() == true) {
					newList.add(temp);
				}
			}
		}
		return newList;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout.setHgap(1);
			jLabel1 = new JLabel();
			jLabel1.setText("可供选择的电子帐册商品信息:          ");
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout);
			jPanel.add(jLabel1, null);
			jPanel.add(getBtnSelectAll(), null);
			jPanel.add(getBtnNotSelectAll(), null);
		}
		return jPanel;
	}
}
