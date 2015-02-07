package com.bestway.client.fixasset;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.ChangeLocaOptionParam;
import com.bestway.fixasset.entity.FixassetLocation;
import com.bestway.fixasset.entity.TempOtherBillInfo;

public class PnFixassetChangeResult extends JPanel {

	private static final long serialVersionUID = 1L;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JTableListModel tableModel;

	private FixAssetAction fixAssetAction;

	/**
	 * This is the default constructor
	 */
	public PnFixassetChangeResult() {
		super();
		initialize();
	}

	/**
	 * This is the default constructor
	 */
	public PnFixassetChangeResult(List list, TempOtherBillInfo otherInfo) {
		super();
		initialize();
		fixAssetAction = (FixAssetAction) CommonVars.getApplicationContext()
				.getBean("fixAssetAction");
		List lsChangeInfo = fixAssetAction.getFixassetLocationChangeBillInfo(
				new Request(CommonVars.getCurrUser()), list, otherInfo);
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
		this.add(getJScrollPane(), BorderLayout.CENTER);
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
				list.add(addColumn("单据号码", "billCode", 100));
				list.add(addColumn("经手人", "handMan", 100));
				return list;
			}
		});
		tableModel = (JTableListModel) jTable.getModel();
	}

	public List getChangeBillInfo() {
		if (tableModel == null) {
			return new ArrayList();
		} else {
			return tableModel.getList();
		}
	}
}
