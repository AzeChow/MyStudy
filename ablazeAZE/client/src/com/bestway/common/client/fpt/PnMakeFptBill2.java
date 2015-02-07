/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.TempBillDetail;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Rectangle;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class PnMakeFptBill2 extends PnCommon {

	private PnMakeFptBill2 pnMakeCustomsEnvelopBill2 = null;

	// private JTableListModel tableModelMaster = null;

	private JTableListModel tableModelDetail = null;

	private JTable tbDetail = null;

	private List billMasterList = null; // @jve:decl-index=0:

	private CasAction casAction = null;

	private JScrollPane jScrollPane1 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JButton btnSelectAll = null;

	private JButton btnNotSelect = null;

	/**
	 * This is the default constructor
	 */
	public PnMakeFptBill2() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
	}

	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			initDetailTable();
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
		this.setSize(678, 354);
		this.add(getJSplitPane(), BorderLayout.CENTER);
	}

	/**
	 * @return Returns the pnMakeCustomsEnvelopBill2.
	 */
	public PnMakeFptBill2 getPnMakeFptBill2() {
		return pnMakeCustomsEnvelopBill2;
	}

	/**
	 * @param pnMakeCustomsEnvelopBill2
	 *            The pnMakeCustomsEnvelopBill2 to set.
	 */
	public void setPnMakeFptBill2(PnMakeFptBill2 pnMakeCustomsEnvelopBill2) {
		this.pnMakeCustomsEnvelopBill2 = pnMakeCustomsEnvelopBill2;
	}

	/**
	 * @return Returns the parentList.
	 */
	public List getBillMasterList() {
		return billMasterList;
	}

	/**
	 * @param parentList
	 *            The parentList to set.
	 */
	public void setBillMasterList(List parentList) {
		this.billMasterList = parentList;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbDetail() {
		if (tbDetail == null) {
			tbDetail = new JTable();
		}
		return tbDetail;
	}

	/**
	 * 选中所有 or 清除所有选择
	 */
	private void selectAll(boolean isSelected) {
		if (this.tableModelDetail == null) {
			return;
		}
		List list = tableModelDetail.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempBillDetail) {
				TempBillDetail temp = (TempBillDetail) list.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModelDetail.updateRows(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initDetailTable() {
		List list = new ArrayList();
		// if (tableModelMaster.getCurrentRow() != null) {
		// List<Object> lsMaster = new ArrayList<Object>();
		// lsMaster.add(tableModelMaster.getCurrentRow());
		list = this.casAction.findBillDetailByParent(new Request(CommonVars
				.getCurrUser()), billMasterList);// lsMaster
		// }
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("账册/手册号", "billDetail.emsNo", 100));
				list.add(addColumn("商品料号", "billDetail.ptPart", 100));
				list.add(addColumn("商品名称", "billDetail.ptName", 100));
				list.add(addColumn("商品规格", "billDetail.ptSpec", 120));
				list.add(addColumn("数量", "billDetail.ptAmount", 60));
				list.add(addColumn("单价", "billDetail.ptPrice", 60));
				list.add(addColumn("金额", "billDetail.money", 60));
				list.add(addColumn("制单号", "billDetail.productNo", 80));
				list.add(addColumn("报关单号", "billDetail.customNo", 80));
				list.add(addColumn("商品海关编码", "billDetail.complex.code", 80));
				list.add(addColumn("报关商品名称", "billDetail.hsName", 100));
				list.add(addColumn("报关商品规格 ", "billDetail.hsSpec", 100));
				list.add(addColumn("折算报关数量", "billDetail.hsAmount", 60));
				list.add(addColumn("报关单价", "billDetail.hsPrice", 60));
				list.add(addColumn("仓库", "billDetail.wareSet.name", 80));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModelDetail = new JTableListModel(tbDetail, list,
				jTableListModelAdapter);
		tbDetail.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		tbDetail.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
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
			if (obj instanceof TempBillDetail) {
				TempBillDetail temp = (TempBillDetail) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

	}

	/**
	 * 获得选中关封申请单中商品信息的信息
	 */
	public List getSelectedCommodityList() {
		if (this.tableModelDetail == null) {
			return null;
		}
		List list = tableModelDetail.getList();
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempBillDetail) {
				TempBillDetail temp = (TempBillDetail) list.get(i);
				if (temp.getIsSelected().booleanValue() == true) {
					newList.add(temp);
				}
			}
		}
		return newList;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbDetail());
		}
		return jScrollPane1;
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
			jSplitPane.setBottomComponent(getJScrollPane1());
			jSplitPane.setDividerLocation(80);
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
			jPanel.setLayout(null);
			jPanel.add(getBtnSelectAll(), null);
			jPanel.add(getBtnNotSelect(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setBounds(new Rectangle(585, 12, 66, 25));
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
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelect() {
		if (btnNotSelect == null) {
			btnNotSelect = new JButton();
			btnNotSelect.setBounds(new Rectangle(585, 44, 66, 25));
			btnNotSelect.setText("不选");
			btnNotSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(false);
				}
			});
		}
		return btnNotSelect;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
