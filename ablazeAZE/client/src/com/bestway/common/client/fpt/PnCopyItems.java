/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.Component;
import java.awt.Rectangle;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.entity.TempFptAppItem;
import com.bestway.ui.winuicontrol.JPanelBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnCopyItems extends JPanelBase {

	private PnCopyItems pnMakeCustomsEnvelopBill2 = null;

	private JTableListModel tableModel = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnSelectAll = null;

	private JButton btnNotSelectAll = null;

	private FptAppHead fptAppHead = null; // @jve:decl-index=0:

	private FptManageAction fptManageAction = null; // 合同

	private boolean isOk = false;

	private JTextField jTextField = null;

	/**
	 * This is the default constructor
	 */
	public PnCopyItems() {
		super();
		initialize();
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
	}

	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			if (fptAppHead != null) {
				String parentId = fptAppHead.getId();
				List list = fptManageAction.findTempFptAppItemByParentId(
						new Request(CommonVars.getCurrUser()), fptAppHead);
				initTable(list);
			}
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
		this.setSize(558, 386);
		jLabel.setBounds(13, 11, 163, 21);
		jLabel.setText("选择转抄的明细(\",\"号分开)");
		this.add(jLabel, null);
		this.add(getJScrollPane(), null);
		this.add(getBtnSelectAll(), null);
		this.add(getBtnNotSelectAll(), null);
		this.add(getJTextField(), null);
	}

	/**
	 * @return Returns the pnMakeCustomsEnvelopBill2.
	 */
	public PnCopyItems getPnMakeCustomsEnvelopBill2() {
		return pnMakeCustomsEnvelopBill2;
	}

	/**
	 * @param pnMakeCustomsEnvelopBill2
	 *            The pnMakeCustomsEnvelopBill2 to set.
	 */
	public void setPnMakeCustomsEnvelopBill2(
			PnCopyItems pnMakeCustomsEnvelopBill2) {
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
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(2, 39, 557, 345);
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
			btnSelectAll.setBounds(364, 11, 61, 21);
			btnSelectAll.setText("全选");
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
			btnNotSelectAll.setBounds(431, 11, 61, 21);
			btnNotSelectAll.setText("不选");
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
			if (list.get(i) instanceof TempFptAppItem) {
				TempFptAppItem temp = (TempFptAppItem) list.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModel.updateRows(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				
				list.add(addColumn("选择", "isSelected", 50));
				list.add(addColumn("转入转出标记","fptAppItem.inOutFlag", 80));
				list.add(addColumn("修改标记","fptAppItem.modifyMarkState", 80));
				list.add(addColumn("序号","fptAppItem.listNo", 90));
				list.add(addColumn("转出序号","fptAppItem.inOutNo", 90));
				list.add(addColumn("转入手册号","fptAppItem.inEmsNo", 120));

				list.add(addColumn("商品项号","fptAppItem.trNo", 120));
				list.add(addColumn("商品编码","fptAppItem.codeTs.code", 120));
				list.add(addColumn("商品名称","fptAppItem.gName", 80));
				list.add(addColumn("规格型号","fptAppItem.gModel", 80));
				list.add(addColumn("计量单位","fptAppItem.unit.name", 80));

				list.add(addColumn("法定单位","fptAppItem.unit1.name", 80));
				list.add(addColumn("申报数量","fptAppItem.qty", 80));
				list.add(addColumn("法定数量","fptAppItem.qty1", 80));
				list.add(addColumn("单价","fptAppItem.unitPrice", 80));
				list.add(addColumn("总价","fptAppItem.totalPrice", 80));
				
				list.add(addColumn("币制","fptAppItem.curr.name", 80));
				list.add(addColumn("备用商品编码","fptAppItem.standComplex.code", 80));
				list.add(addColumn("备注","fptAppItem.note", 80));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		
		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return FptInOutFlag.getNote(value
								.toString());
					}
				});
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		
		
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
			if (obj instanceof TempFptAppItem) {
				TempFptAppItem temp = (TempFptAppItem) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

	}

	/**
	 * 获得选中的海关商品单据
	 */
	public List<FptAppItem> getSelectedContractExgList() {
		List<FptAppItem> newList = new ArrayList<FptAppItem>();
		if (this.tableModel == null) {
			return newList;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempFptAppItem) {
				TempFptAppItem temp = (TempFptAppItem) list.get(i);
				if (temp.getIsSelected().booleanValue() == true) {
					newList.add(temp.getFptAppItem());
				}
			}
		}
		return newList;
	}

	

	

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(182, 9, 175, 24));
			jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
				@Override
				public void keyTyped(java.awt.event.KeyEvent e) {
//					System.out.println("------------" + jTextField.getText()
//							+ e.getKeyChar());
					String[] keys = (jTextField.getText().trim() + e
							.getKeyChar()).trim().split(",");
					if (keys.length > 0) {
						List list = tableModel.getList();
						for (int i = 0; i < list.size(); i++) {
							TempFptAppItem temp = (TempFptAppItem) list
									.get(i);
							if (temp.getFptAppItem().getListNo() != null) {
								temp.setIsSelected(checkKey(keys, temp
										.getFptAppItem().getListNo()
										.toString()));
							}
						}
						tableModel.setList(list);
					}
				}
			});
		}
		return jTextField;
	}

	private boolean checkKey(String[] keys, String key) {
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] != null && key != null
					&& keys[i].trim().equals(key.trim())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the fptAppHead
	 */
	public FptAppHead getFptAppHead() {
		return fptAppHead;
	}

	/**
	 * @param fptAppHead the fptAppHead to set
	 */
	public void setFptAppHead(FptAppHead fptAppHead) {
		this.fptAppHead = fptAppHead;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
