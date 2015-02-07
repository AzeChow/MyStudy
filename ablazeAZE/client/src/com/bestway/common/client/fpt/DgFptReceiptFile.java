package com.bestway.common.client.fpt;

import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.client.message.DgCspReceiptFile;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.FptProcessResult;

public class DgFptReceiptFile extends DgCspReceiptFile {

	@Override
	protected void initNotProcessTable(List list) {
		JTableListModelAdapter jTableListModelAdapter= new JTableListModelAdapter() {
			@Override
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 80));
				list.add(addColumn("转入转出标记", "inOutFlag", 80));		
				list.add(addColumn("序号", "retNo", 50));
				// list.add(addColumn("经营单位代码", "tradeCode", 100));
				list.add(addColumn("企业内部编号", "copEmsNo", 150));
				// list.add(addColumn("核查/报核次数", "colDcrTime", 100));						
				list.add(addColumn("数据中心统一编号", "seqNo", 140));
				list.add(addColumn("处理结果", "chkMark", 100));
				if (FptBusinessType.FPT_APP.equals(sysType)) {
					list.add(addColumn("申请单号", "customsNo", 130));
				} else if (FptBusinessType.FPT_BILL.equals(sysType)
						|| FptBusinessType.FPT_BILL_BACK
								.equals(sysType)) {
					list.add(addColumn("收发货单号", "customsNo", 150));
				} else {
					list.add(addColumn("海关编号", "customsNo", 130));
				}
				list.add(addColumn("通知时间", "formatedNoticeDate", 120));
				list.add(addColumn("文件名称", "fileName", 300));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModelNotProcess = new JTableListModel(tbNotProcess, list,
				jTableListModelAdapter);
		tbNotProcess.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		tbNotProcess.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		tbNotProcess.getColumnModel().getColumn(2).setCellRenderer(//转入转出标记
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FptInOutFlag
								.getInOutFlagSpec(value.toString().trim()));
						return this;
					}

				});
		tbNotProcess.getColumnModel().getColumn(6).setCellRenderer(//处理结果
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FptProcessResult
								.getResultDesc(value.toString().trim()));
						return this;
					}
				});
	}
} // @jve:decl-index=0:visual-constraint="10,10"
