package com.bestway.client.excel;

import java.awt.Component;
import java.util.UUID;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.bestway.bcus.client.common.CommonProgress;

public class DefaultWriteExcelListener implements WriteExcelListener {

	//
	// 用于标识这个对话框的ID
	//
	private String flag = "";

	private boolean isFirst = true;

	public DefaultWriteExcelListener() {
		UUID uuid = UUID.randomUUID();
		flag = uuid.toString();
	}

	public void writeState(Component parentComponent, String message,
			boolean isBeginFullData, int sheetSize, long totalRow,
			long writeExcelRow, Boolean isWriteSuccess) {

		if (parentComponent instanceof JDialog) {
			CommonProgress.showProgressDialog(flag, (JDialog) parentComponent,
					false, null, 0);
		} else {
			CommonProgress.showProgressDialog(flag, (JFrame) null, false, null,
					0);
		}
		if (isBeginFullData) {
			if (isFirst == true) {
				CommonProgress.initJProgressBar(flag, new Long(totalRow)
						.intValue());
				isFirst = false;
			}
			CommonProgress.setJProgressBarValue(flag, new Long(writeExcelRow)
					.intValue());
			message = "共 " + totalRow + " 行记录  已写入 " + writeExcelRow + " 行";
		}
		if (isWriteSuccess != null) {
			CommonProgress.closeProgressDialog(flag);
			if (isWriteSuccess.booleanValue()) {
				JOptionPane.showMessageDialog(parentComponent,
						"导出 Excel 完成 !!", "提示", 2);
			} else {
				JOptionPane.showMessageDialog(parentComponent,
						"导出 Excel 失败 !!", "提示", 2);
			}
		}else{
			CommonProgress.setMessage(flag, message);
		}
	}
}
