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
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.TempImpExpCommodityInfo;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JPanelBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class PnMakeApplyToCustoms3 extends JPanelBase {
	private JTableListModel tableModel = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private List parentList = null;

	private JButton btnSelectAll = null;

	private JButton btnNotSelectAll = null;

	private EncAction encAction = null;


	private JPanel jPanel = null;

	private JLabel jLabel1 = null;
	
	CompanyOther other =  CommonVars.getOther();
	/**
	 * This is the default constructor
	 */
	public PnMakeApplyToCustoms3() {
		super();
		this.encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		initialize();
	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			List allcommondityInfoList = this.encAction
					.findTempImpExpCommodityInfoByParent(new Request(CommonVars
							.getCurrUser()), this.parentList);
			initTable(allcommondityInfoList);
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
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {   
				public void mouseReleased(java.awt.event.MouseEvent e) {    
					List<TempImpExpCommodityInfo> list = tableModel.getCurrentRows();
					for (TempImpExpCommodityInfo item : list) {
						item.setIsSelected(!item.getIsSelected());
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
			if (list.get(i) instanceof TempImpExpCommodityInfo) {
				TempImpExpCommodityInfo temp = (TempImpExpCommodityInfo) list
						.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModel.updateRows(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List allcommondityInfoList) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 50));
				list.add(addColumn("单据号", "impExpCommodityInfo.impExpRequestBill.billNo",
						110));
				list.add(addColumn("备案序号", "impExpCommodityInfo.seqNum",
						50));
				list.add(addColumn("料号", "impExpCommodityInfo.materiel.ptNo",
						90));
				list.add(addColumn("名称",
						"impExpCommodityInfo.materiel.factoryName", 120));
				list.add(addColumn("规格型号",
						"impExpCommodityInfo.materiel.factorySpec", 120));
				list.add(addColumn("产销国", "impExpCommodityInfo.country.name",
						100));
				list.add(addColumn("单位",
						"impExpCommodityInfo.materiel.calUnit.name", 60));
				list.add(addColumn("数量", "impExpCommodityInfo.quantity", 60));
				list.add(addColumn("单价", "impExpCommodityInfo.unitPrice", 60));
				list
						.add(addColumn("总金额",
								"impExpCommodityInfo.amountPrice", 60));
				list
						.add(addColumn("毛重", "impExpCommodityInfo.grossWeight",
								60));
				list.add(addColumn("净重", "impExpCommodityInfo.netWeight", 60));
				list.add(addColumn("仓库毛重",
						"impExpCommodityInfo.materiel.ckoutWeight", 60));
				list.add(addColumn("仓库净重",
						"impExpCommodityInfo.materiel.cknetWeight", 60));
				list.add(addColumn("件数", "impExpCommodityInfo.piece", 60));
				list.add(addColumn("体积", "impExpCommodityInfo.bulks", 60));
				list
						.add(addColumn("制单号", "impExpCommodityInfo.makeBillNo",
								60));
				list
						.add(addColumn("海关版本号", "impExpCommodityInfo.version",
								80));
				list
				.add(addColumn("企业版本号", "impExpCommodityInfo.cmpVersion",
						80));
				list.add(addColumn("物料版本号",
						"impExpCommodityInfo.materiel.ptVersion", 80));
				list.add(addColumn("未归并的料号", "impExpCommodityInfo.ptNo", 80));

				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, allcommondityInfoList,
				jTableListModelAdapter);
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
			if (obj instanceof TempImpExpCommodityInfo) {
				TempImpExpCommodityInfo temp = (TempImpExpCommodityInfo) obj;
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
		if (this.tableModel == null) {
			return null;
		}
		List list = tableModel.getList();
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempImpExpCommodityInfo) {
				TempImpExpCommodityInfo temp = (TempImpExpCommodityInfo) list
						.get(i);
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
			jLabel1.setText("可供选择的商品信息:         ");
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout);
			jPanel.add(jLabel1, null);
			jPanel.add(getBtnSelectAll(), null);
			jPanel.add(getBtnNotSelectAll(), null);
		}
		return jPanel;
	}

}
