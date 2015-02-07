package com.bestway.client.util;

import java.util.ArrayList;
import java.util.List;

public class JarDataItem {

	/**
	 * 保存顺序
	 */
	private Integer		storeIndex	= 0;
	/**
	 * 保存表的列名
	 */
	private String[]	columnNames	= {};
	/**
	 * 保存数据
	 */
	private List<List>	rows		= new ArrayList<List>();
	/**
	 * 备注
	 */
	private String		note		= null;

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<List> getRows() {
		return rows;
	}

	public void setRows(List<List> rows) {
		this.rows = rows;
	}

	public Integer getStoreIndex() {
		return storeIndex;
	}

	public void setStoreIndex(Integer storeIndex) {
		this.storeIndex = storeIndex;
	}

}
