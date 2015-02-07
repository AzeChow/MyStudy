package com.bestway.common.client.transferfactory;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;
import com.bestway.ui.winuicontrol.JPanelBase;

public class PnMakeBillList extends JPanelBase {

	private TransferFactoryManageAction transferFactoryManageAction = null;

	private String emsNo = "";

	private boolean isImport = false;

	private JTableListModel tableModel = null;

	private JScrollPane jScrollPane = null;

	private JTable tfTransFactBill = null;

	private JComboBox cbbScmCoc = null;

	private JLabel lbScmCoc = null;

	private JButton btnNotSelectAll = null;

	private JButton btnSelectAll = null;

	private List lsScmCoc = new ArrayList();

	private JCheckBox cbMergeOne = null;

	private ScmCoc initScmCoc = null;

	// public List getLsScmCoc() {
	// return lsScmCoc;
	// }
	//
	// public void setLsScmCoc(List lsScmCoc) {
	// this.lsScmCoc = lsScmCoc;
	// }
	/**
	 * This is the default constructor
	 */
	public PnMakeBillList(String emsNo, boolean isImport, List scmCocs,
			ScmCoc initScmCoc) {
		super();
		initialize();
		this.emsNo = emsNo;
		this.isImport = isImport;
		this.lsScmCoc = scmCocs;
		this.initScmCoc = initScmCoc;
		transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
				.getApplicationContext().getBean("transferFactoryManageAction");
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		lbScmCoc = new JLabel();
		lbScmCoc.setBounds(new java.awt.Rectangle(13, 5, 76, 21));
		lbScmCoc.setText("JLabel");
		this.setLayout(null);
		this.setSize(500, 248);
		this.add(getJScrollPane(), null);
		this.add(getCbbScmCoc(), null);
		this.add(lbScmCoc, null);
		this.add(getBtnNotSelectAll(), null);
		this.add(getBtnSelectAll(), null);
		this.add(getCbMergeOne(), null);
	}

	// public void setVisible(boolean isFalg) {
	// if (isFalg == true) {
	// initUIComponents();
	// }
	// super.setVisible(isFalg);
	// }

	/**
	 * 获得右边jList的数据源
	 */
	public List getListData() {
		if (this.tableModel == null) {
			return new ArrayList();
		} else {
			List lsResult = new ArrayList();
			List list = this.tableModel.getList();
			for (int i = 0; i < list.size(); i++) {
				TransferFactoryBill bill = (TransferFactoryBill) list.get(i);
				if (bill.getIsSelected() != null && bill.getIsSelected()) {
					lsResult.add(bill);
				}
			}
			return lsResult;
		}
	}

	/**
	 * 是否将多张单据合并成一张报关单
	 * 
	 * @return
	 */
	public boolean isMergeOne() {
		return this.cbMergeOne.isSelected();
	}
	/**
	 * 取得所选择的供应商/客户
	 * @return
	 */
	public ScmCoc getScmCoc(){
		return (ScmCoc)this.cbbScmCoc.getSelectedItem();
	}
	/**
	 * 填充数据到JList
	 */
	private void initTable() {
		ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
		List list = (scmCoc == null ? new ArrayList()
				: transferFactoryManageAction
						.findTransFactBillMakeCustomsDeclaration(new Request(
								CommonVars.getCurrUser()), isImport, scmCoc));
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("单据号", "transferFactoryBillNo", 100));
				list.add(addColumn("生效", "isAvailability", 50));
				list.add(addColumn("生效日期", "beginAvailability", 80));
				list.add(addColumn("客户/供应商名称", "scmCoc.name", 150));
				list.add(addColumn("仓库名称", "wareSet.name", 150));
				list.add(addColumn("商品项数", "itemCount", 80));
				list.add(addColumn("录入人员", "aclUser.name", 80));
				list.add(addColumn("备注", "memo", 100));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(tfTransFactBill, list,
				jTableListModelAdapter);
		tfTransFactBill.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		tfTransFactBill.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		tfTransFactBill.getColumnModel().getColumn(3).setCellRenderer(
				new TableCheckBoxRender());
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
			if (obj instanceof TransferFactoryBill) {
				TransferFactoryBill temp = (TransferFactoryBill) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new java.awt.Rectangle(12, 52, 480, 191));
			jScrollPane.setViewportView(getTfTransFactBill());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTfTransFactBill() {
		if (tfTransFactBill == null) {
			tfTransFactBill = new JTable();
		}
		return tfTransFactBill;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new java.awt.Rectangle(13, 28, 156, 21));
			cbbScmCoc.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initTable();
					}
				}
			});
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelectAll() {
		if (btnNotSelectAll == null) {
			btnNotSelectAll = new JButton();
			btnNotSelectAll.setBounds(new java.awt.Rectangle(420, 28, 60, 22));
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
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setBounds(new java.awt.Rectangle(420, 5, 60, 22));
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
	 * 选中所有 or 清除所有选择
	 */
	private void selectAll(boolean isSelected) {
		if (this.tableModel == null) {
			return;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TransferFactoryBill) {
				TransferFactoryBill t = (TransferFactoryBill) list.get(i);
				t.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModel.updateRows(list);
	}

	private void initUIComponents() {
		if (lsScmCoc == null) {
			lsScmCoc = new ArrayList();
		}
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(lsScmCoc.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
				"code", "name", 250);
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		this.cbbScmCoc.setSelectedItem(this.initScmCoc);

		if (isImport) {
			lbScmCoc.setText("供应商名称");
		} else {
			lbScmCoc.setText("客户名称");
		}

		initTable();
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMergeOne() {
		if (cbMergeOne == null) {
			cbMergeOne = new JCheckBox();
			cbMergeOne.setBounds(new java.awt.Rectangle(177, 16, 238, 21));
			cbMergeOne.setSelected(true);
			cbMergeOne.setText("是否将所选单据合并生成一张报关单");
		}
		return cbMergeOne;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
