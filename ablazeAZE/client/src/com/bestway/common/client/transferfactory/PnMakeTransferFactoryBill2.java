/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.transferfactory;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.TempBillDetail;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import java.awt.BorderLayout;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnMakeTransferFactoryBill2 extends PnCommon {

	private PnMakeTransferFactoryBill2 pnMakeCustomsEnvelopBill2 = null;

	private JTableListModel tableModelMaster = null;

	private JTableListModel tableModelDetail = null;

	private JTable tbDetail = null;

	private JScrollPane jScrollPane = null;

	private List billMasterList = null;

	private CasAction casAction = null;

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbMaster = null;

	/**
	 * This is the default constructor
	 */
	public PnMakeTransferFactoryBill2() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
	}

	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			initMasterTable();
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
		this.setSize(500, 248);
		this.add(getJSplitPane(), BorderLayout.CENTER);
		tbMaster.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						JTableListModel tableModel = (JTableListModel) tbMaster
								.getModel();
						if (tableModel == null) {
							return;
						}
						try {
							initDetailTable();
						} catch (Exception ee) {
						}
					}
				});
	}

	/**
	 * @return Returns the pnMakeCustomsEnvelopBill2.
	 */
	public PnMakeTransferFactoryBill2 getPnMakeCustomsEnvelopBill2() {
		return pnMakeCustomsEnvelopBill2;
	}

	/**
	 * @param pnMakeCustomsEnvelopBill2
	 *            The pnMakeCustomsEnvelopBill2 to set.
	 */
	public void setPnMakeCustomsEnvelopBill2(
			PnMakeTransferFactoryBill2 pnMakeCustomsEnvelopBill2) {
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
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbMaster());
		}
		return jScrollPane;
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
	private void initMasterTable() {
		List list = this.billMasterList;
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				// list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("类别", "billMaster.billType.name", 100));
				list.add(addColumn("单据号", "billMaster.billNo", 100));
				//list.add(addColumn("仓库", "wareSet.name", 80));
				list.add(addColumn("对应报关单号", "billMaster.customNo", 100));
				list.add(addColumn("有效", "billMaster.isValid", 50));
				list.add(addColumn("生效日期", "billMaster.validDate", 80));
				list.add(addColumn("是否记帐", "billMaster.keepAccounts", 50));
				list.add(addColumn("关封号", "billMaster.envelopNo", 50));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModelMaster = new JTableListModel(this.tbMaster, list,
				jTableListModelAdapter);
		// jTable.getColumnModel().getColumn(1).setCellRenderer(
		// new TableCheckBoxRender());
		tbMaster.getColumnModel().getColumn(4).setCellRenderer(
				new TableCheckBoxRender());
		tbMaster.getColumnModel().getColumn(6).setCellRenderer(
				new TableCheckBoxRender());
		// jTable.getColumnModel().getColumn(1).setCellEditor(
		// new CheckBoxEditor(new JCheckBox()));
	}

	/**
	 * 初始化数据Table
	 */
	private void initDetailTable() {
		List list = new ArrayList();
		if (tableModelMaster.getCurrentRow() != null) {
			List<Object> lsMaster = new ArrayList<Object>();
			lsMaster.add(tableModelMaster.getCurrentRow());
			list = this.casAction.findBillDetailByParent(new Request(CommonVars
					.getCurrUser()), lsMaster);
		}
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				// list.add(addColumn("选择", "isSelected", 100));
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
		// jTableListModelAdapter.setEditableColumn(1);
		tableModelDetail = new JTableListModel(tbDetail, list,
				jTableListModelAdapter);
		// tbDetail.getColumnModel().getColumn(1).setCellRenderer(
		// new TableCheckBoxRender());
		// tbDetail.getColumnModel().getColumn(1).setCellEditor(
		// new CheckBoxEditor(new JCheckBox()));
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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

//	/**
//	 * 获得选中关封申请单中商品信息的信息
//	 */
//	public List getCommodityList() {
//		if (this.tableModelDetail == null) {
//			return null;
//		}
//		List list = tableModelDetail.getList();
//		List newList = new ArrayList();
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i) instanceof TempBillDetail) {
//				TempBillDetail temp = (TempBillDetail) list.get(i);
//				if (temp.getIsSelected().booleanValue() == true) {
//					newList.add(temp);
//				}
//			}
//		}
//		return newList;
//	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(2);
			jSplitPane.setDividerLocation(100);
			jSplitPane.setTopComponent(getJScrollPane());
			jSplitPane.setBottomComponent(getJScrollPane1());
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
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
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbMaster() {
		if (tbMaster == null) {
			tbMaster = new JTable();
		}
		return tbMaster;
	}

	public JTableListModel getTableModelMaster() {
		if(tableModelMaster != null) {
			return tableModelMaster;
		} else {
			throw new RuntimeException("主表为空。");
		}
	}

}
