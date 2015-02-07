package com.bestway.client.excel;

import java.awt.Component;

public interface WriteExcelListener {
	/**
	 * 写入 Excel Listener
	 * 
	 * @param sheetSize
	 * @param writeExcelRow
	 * @param message
	 * @param isWriteSuccess
	 */
	public void writeState(Component parentComponent, 
			String message,boolean isBeginFullData,
			int sheetSize,
			long totalRow, long writeExcelRow,
			Boolean isWriteSuccess);

}
