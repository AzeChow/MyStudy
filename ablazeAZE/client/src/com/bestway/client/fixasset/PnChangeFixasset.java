package com.bestway.client.fixasset;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.ChangeLocaOptionParam;
import com.bestway.fixasset.entity.FixassetLocation;

public class PnChangeFixasset extends JPanel {

	private static final long serialVersionUID = 1L;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnDelete = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JTableListModel tableModel;

	private FixAssetAction fixAssetAction;
	
	private FixassetLocation location;

	/**
	 * This is the default constructor
	 */
	public PnChangeFixasset() {
		super();
		initialize();
	}

	/**
	 * This is the default constructor
	 */
	public PnChangeFixasset(List list, FixassetLocation location,
			Integer changeType) {
		super();
		initialize();
		fixAssetAction = (FixAssetAction) CommonVars.getApplicationContext()
				.getBean("fixAssetAction");
		List lsChangeInfo = new ArrayList();
		btnAdd.setEnabled(true);
		if (changeType == ChangeLocaOptionParam.CUSTOMS_IN_FACT) {
			btnAdd.setEnabled(false);
			lsChangeInfo = fixAssetAction
					.getFixassetLocationChangeInfoFromCustomsBill(new Request(
							CommonVars.getCurrUser()), list, location,
							changeType);
		} else if (changeType == ChangeLocaOptionParam.FACT_CHANGE_LOCATION) {
			btnAdd.setEnabled(false);
			lsChangeInfo = fixAssetAction
					.getFixassetLocationChangeInfoFromResultInfo(new Request(
							CommonVars.getCurrUser()), location, changeType);
		} else if (changeType == ChangeLocaOptionParam.FACT_SUBTRACT) {
			btnAdd.setEnabled(false);
			lsChangeInfo = fixAssetAction
					.getFixassetLocationChangeInfoFromResultInfo(new Request(
							CommonVars.getCurrUser()), location, changeType);
		}
		this.location=location;
		initTable(lsChangeInfo);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(new BorderLayout());
		this.add(getJToolBar(), BorderLayout.SOUTH);
		this.add(getJScrollPane(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnDelete());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("增加");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list=fixAssetAction.getFixassetLocationChangeInfoFromFactory(
							new Request(CommonVars.getCurrUser()),new ArrayList(),location,null);
					tableModel.addRows(list);
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(tableModel.getCurrentRow()==null){
						JOptionPane.showMessageDialog(PnChangeFixasset.this,
								"请选择要删除的资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
					tableModel.deleteRows(tableModel.getCurrentRows());
				}
			});
		}
		return btnDelete;
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

	private void initTable(List list) {
		JTableListModel.dataBind(jTable, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("报关单号", "customsDeclarationCode", 100));
				list.add(addColumn("进口日期 ", "impExpDate", 100));
				list.add(addColumn("设备名称", "commName", 100));
				list.add(addColumn("规格", "commSpec", 100));
				list.add(addColumn("报关单数量", "amount", 100));
				list.add(addColumn("报关单项号", "customsDeclaItemNo", 100));
				list.add(addColumn("商品编码", "complex.code", 100));
				list.add(addColumn("协议号", "agreementNo", 100));
				list.add(addColumn("报关单流水号", "customsBillSeqNo", 100));
				list.add(addColumn("异动类型", "changeType", 100));
				list.add(addColumn("原位置", "oldLocation.name", 100));
				list.add(addColumn("目标位置", "newLocation.name", 100));
				return list;
			}
		});
		tableModel = (JTableListModel) jTable.getModel();
	}

	public List getChangeFixasset() {
		if (tableModel == null) {
			return new ArrayList();
		} else {
			return tableModel.getList();
		}
	}

}
