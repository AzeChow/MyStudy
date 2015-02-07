/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.common.tableeditor.CheckBoxHeader;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.common.Request;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.ui.winuicontrol.JPanelBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class PnMakeFptBillList1 extends JPanelBase {
	private FptManageAction fptManageAction = null;
	private JTableListModel tableModel = null;

	private JTableListModel tableModelDetail = null;
	
	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private List parentList = null; // @jve:decl-index=0:

	private JButton btnSelectAll = null;

	private JButton btnNotSelectAll = null;

	private JPanel jPanel = null;

	private JLabel jLabel1 = null;

	private String inOutFlag = ""; // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public PnMakeFptBillList1() {
		super();
		fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		initialize();
		// initUIComponents();
	}

	private void initUIComponents() {
		List allcommondityInfoList = new ArrayList();
		allcommondityInfoList = this.fptManageAction.findMakeSummaryFptBillCommodityInfoByParent(new Request(CommonVars.getCurrUser()), this.parentList,this.inOutFlag);
		initTable(allcommondityInfoList);
	}

	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			initUIComponents();
		}
		super.setVisible(isFlag);
	}

	/**
	 * 获得选中关封申请单中商品信息的信息
	 */
	public List getCommodityList() {
		if (this.tableModel == null) {
			return null;
		}
		List list = tableModel.getList();
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof FptBillItem) {
				FptBillItem temp = (FptBillItem) list.get(i);
				if (temp.getIsSelected().booleanValue() == true) {
					newList.add(temp);
				}
			}
		}
		return newList;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(654, 352);
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
					PnMakeFptBillList1.this.setCursor(new Cursor(
							Cursor.WAIT_CURSOR));
					selectAll(true);
					PnMakeFptBillList1.this.setCursor(new Cursor(
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
			btnNotSelectAll.setText("不选");
			btnNotSelectAll.setPreferredSize(new Dimension(60, 25));
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							PnMakeFptBillList1.this.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							selectAll(false);
							PnMakeFptBillList1.this.setCursor(new Cursor(
									Cursor.DEFAULT_CURSOR));
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
			if (list.get(i) instanceof FptBillItem) {
				FptBillItem temp = (FptBillItem) list.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
//		tableModel.updateRows(list);
		tableModelDetail.updateRows(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List allcommondityInfoList) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List InitColumns() {
				List list = new Vector();
				
				list.add(addColumn("选择", "isSelected", 40));
				list.add(addColumn("序号", "listNo", 20));
				list.add(addColumn("单据类型", "fptBillHead.sysType", 80));
				if (FptInOutFlag.IN.equals(inOutFlag)) {
					list.add(addColumn("手册/账册号", "inEmsNo", 90));
					list.add(addColumn("转入", "billSort", 60));
				} else {
					list.add(addColumn("转出", "billSort", 60));
				}
				list.add(addColumn("项号", "trGno", 50));
				list.add(addColumn("商品编码", "complex.code", 120));
				list.add(addColumn("商品名称", "commName", 100));
				list.add(addColumn("商品规格", "commSpec", 60));
				list.add(addColumn("交易单位", "tradeUnit.name", 60));
				list.add(addColumn("交易数量", "tradeQty", 60));
				list.add(addColumn("计量单位", "unit.name", 60));
				list.add(addColumn("申报数量", "qty", 60));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, allcommondityInfoList,	jTableListModelAdapter);
		
		int count = 0;
		int conBillSort = 0;
		if (FptInOutFlag.IN.equals(inOutFlag)) {
			count = 4;
			conBillSort = 5;
		} else {
			conBillSort = 4;
			count = 3;
		}
		
		jTable.getTableHeader().setReorderingAllowed(false);
		JTableListModel.dataBind(jTable, allcommondityInfoList, jTableListModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		tableModelDetail = (JTableListModel) jTable.getModel();
		tableModelDetail.setAllowSetValue(true);
		jTableListModelAdapter.setEditableColumn(1);
		jTable.getColumnModel().getColumn(1).setHeaderRenderer(
				new CheckBoxHeader(false));
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		

		jTable.getColumnModel().getColumn(count).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FptInOutFlag
								.getInOutFlagSpec(value.toString()));
						return this;
					}
				});
		/**
		jTable.getColumnModel().getColumn(3)
		.setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(
					JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
				super.setText((value == null) ? "" : castValue1(value));
				return this;
			}

			private String castValue1(Object value) {
				String returnValue = FptBusinessType
						.getFptBusinessTypeDesc(value.toString());
				return returnValue;
			}
		});
		**/
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer(){
			public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
				System.out.println(value);
				String formatValue = "2".equals(value) ? "收发货单":"退货单";//单据类型标志 收发货单 2 退货单 3
				return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,row, column);
			}
		});
		
		/**
		 * 进出口标志中的进口标志 0 出口1 进口
		 * 
		 * 备注 :发货与收退货是0 ;收货与发退货是1
		 */
		jTable.getColumnModel().getColumn(conBillSort).setCellRenderer(
				new DefaultTableCellRenderer(){
			public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
				System.out.println(value);
				String formatValue = "0".equals(value) ? "出口":"进口";//单据类型标志 收发货单 2 退货单 3
				return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,row, column);
			}
		});
//		jTable.getColumnModel().getColumn(1).setCellRenderer(
//				new MultiRenderer());
		

	}

	/**
	 * 表格渲染器
	 * 
	 * @author Administrator
	 * 
	 */

	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				checkBox.setSelected(false);
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
			if (obj instanceof FptBillItem) {
				FptBillItem temp = (FptBillItem) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

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
			jLabel1.setText("可供选择的商品信息:         ");
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout);
			jPanel.add(jLabel1, null);
			jPanel.add(getBtnSelectAll(), null);
			jPanel.add(getBtnNotSelectAll(), null);
		}
		return jPanel;
	}

	public String getIsImport() {
		return inOutFlag;
	}

	public void setIsImport(String isImport) {
		this.inOutFlag = isImport;
	}

	public boolean vaildateData(String cm) {
		return fptManageAction.checkmakeFptToBgdEmsH2kBill(cm,
				tableModel.getList());
	}
}
