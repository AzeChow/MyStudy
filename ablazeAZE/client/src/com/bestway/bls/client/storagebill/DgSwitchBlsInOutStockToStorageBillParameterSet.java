package com.bestway.bls.client.storagebill;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bls.action.BlsInOutStockBillAction;

import com.bestway.bls.entity.BlsIOStockBillIOF;
import com.bestway.bls.entity.BlsInOutStockSwichParameterSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;

import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 转仓单参数设置见面
 * 
 * @author hw
 */
public class DgSwitchBlsInOutStockToStorageBillParameterSet extends JDialogBase {

	private JPanel jPanel = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnNew = null;

	private JButton btnDelete = null;

	private JButton btnModified = null;

	private JButton btnExit = null;

	private JScrollPane jScrollPane = null;

	/**
	 * 窗体主表
	 */
	private JTable tbMain = null;

	private JTableListModel tableModel = null;

	/**
	 * 进出仓单据服务器端接口
	 */
	public BlsInOutStockBillAction blsInOutStockBillAction = (BlsInOutStockBillAction) CommonVars
			.getApplicationContext().getBean("blsInOutStockBillAction"); // @jve:decl-index=0:

	/**
	 * 窗体构造函数
	 * 
	 */
	public DgSwitchBlsInOutStockToStorageBillParameterSet() {
		super();
		initialize();
		List list = this.blsInOutStockBillAction
				.findBlsSwitchParameterSet(new Request(CommonVars.getCurrUser()));
		if (list != null && list.size() > 0) {
			initTable(list);
		} else {
			initTable(new ArrayList());
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(688, 385));
		this.setTitle("转仓单参数设置");
		this.setContentPane(getJPanel());

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnNew());
			jJToolBarBar.add(getBtnDelete());
			jJToolBarBar.add(getBtnModified());
			jJToolBarBar.add(getBtnExit());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes btnNew
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNew() {
		if (btnNew == null) {
			btnNew = new JButton();
			btnNew.setText("新增");
			btnNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBlsSwitchParameterSet dg = new DgBlsSwitchParameterSet();
					dg.setDataState(DataState.ADD);
					dg.setTableModel(tableModel);
					dg.setVisible(true);
				}
			});
		}
		return btnNew;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane
								.showMessageDialog(
										DgSwitchBlsInOutStockToStorageBillParameterSet.this,
										"请选择要删除的数据行!", "提示", 0);
						return;
					}
					deleteTableRow();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 删除一行信息
	 */
	public void deleteTableRow() {
		if (tableModel.getCurrentRows() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", 0) != 0) {
				return;
			}
		}
		BlsInOutStockSwichParameterSet bosp = (BlsInOutStockSwichParameterSet) this.tableModel
				.getCurrentRow();
		this.blsInOutStockBillAction.deleteBlsSwitchParameterSet(new Request(
				CommonVars.getCurrUser()), bosp);
		this.tableModel.deleteRow(bosp);
	}

	/**
	 * This method initializes btnModified
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnModified() {
		if (btnModified == null) {
			btnModified = new JButton();
			btnModified.setText("修改");
			btnModified.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane
								.showMessageDialog(
										DgSwitchBlsInOutStockToStorageBillParameterSet.this,
										"没有数据，或请选择数据!", "提示", 0);
						return;
					}
					DgBlsSwitchParameterSet dg = new DgBlsSwitchParameterSet();
					dg.setTableModel(tableModel);
					dg.setDataState(DataState.EDIT);
					dg.setVisibles(true);
				}
			});
		}
		return btnModified;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("退出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbMain());
		}
		return jScrollPane;
	}

	/**
	 * 初始化表体
	 * 
	 * @param list
	 */
	private void initTable(List list) {
		tableModel = new JTableListModel(tbMain, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("进出仓类型", "ioFlag", 100));
						list.add(addColumn("申报海关", "declarationCustoms.name",
								100));
						list.add(addColumn("进出口岸", "customs.name", 100));
						list.add(addColumn("包装种类", "wrapType.name", 100));
						list.add(addColumn("仓单类型", "billType", 100));
						list.add(addColumn("帐册编号", "emsNo", 100));
						return list;
					}
				});
		tbMain.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					// 转化表体数据
					private String castValue(Object value) {
						return BlsIOStockBillIOF.getImpExpFlagSpec(value
								.toString());
					}
				});
		tbMain.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value == null || value.toString().trim().equals("")) {
							super.setText("");
						} else if (value.toString().trim().equals("00")) {
							super.setText("申报初始库存");
						} else if (value.toString().trim().equals("01")) {
							super.setText("后报关方式");
						} else if (value.toString().trim().equals("02")) {
							super.setText("先报关分批送货方式");
						} else if (value.toString().trim().equals("03")) {
							super.setText("特殊审核");
						} else {
							super.setText("");
						}
						return this;
					}

				});
	}

	/**
	 * This method initializes tbMain
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbMain() {
		if (tbMain == null) {
			tbMain = new JTable();
		}
		return tbMain;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
