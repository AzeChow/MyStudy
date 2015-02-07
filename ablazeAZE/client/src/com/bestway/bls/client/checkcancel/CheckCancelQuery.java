package com.bestway.bls.client.checkcancel;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bls.action.BlsCheckCancelAction;
import com.bestway.bls.entity.BlsIOStockBillIOF;
import com.bestway.bls.entity.StorageBill;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;

public class CheckCancelQuery {
	private static CheckCancelQuery checkCancelQuery = null;

	public static BlsCheckCancelAction blsCheckCancelAction = null;

	public static CheckCancelQuery getInstance() {
		if (checkCancelQuery == null) {
			checkCancelQuery = new CheckCancelQuery();
			blsCheckCancelAction = (BlsCheckCancelAction) CommonVars
					.getApplicationContext().getBean("blsCheckCancelAction");
		}
		return checkCancelQuery;

	}

	/** 获得正在执行的 StorageBill */
	public List<StorageBill> findStorageBillByProcessExes(Integer flag) {
		List<StorageBill> dataSource = new ArrayList<StorageBill>();
		if (flag == 0) {
			dataSource = blsCheckCancelAction.findStorageBillByProcessExe(
					new Request(CommonVars.getCurrUser()), flag);
		} else if (flag == 1) {
			dataSource = blsCheckCancelAction.findStorageBillByProcessExe(
					new Request(CommonVars.getCurrUser()), flag);
		}

		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("进出仓标志", "ioFlag", 100));
		list.add(new JTableListColumn("单据编号", "billNo", 100));
		list.add(new JTableListColumn("单据生效日期", "validDate", 100));
		list.add(new JTableListColumn("供货方企业", "corrOwner.name", 180));
		list.add(new JTableListColumn("仓库编码", "wareHouseCode.code", 100));

		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("获得等待审批的仓单");
		dgCommonQuery.setLike(false);

		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable tb = dgCommonQuery.getJTable();
				tb.getColumnModel().getColumn(1).setCellRenderer(
						new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: castValue(value));
								return this;
							}

							private String castValue(Object value) {
								return BlsIOStockBillIOF
										.getImpExpFlagSpec(value.toString());
							}
						});
				tb.repaint();
			}
		});
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (List<StorageBill>) dgCommonQuery.getReturnList();
		}
		return null;
	}
}
