package com.bestway.bcus.system.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bestway.common.CommonUtils;

public class JarFileObject implements Serializable {
	
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	
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
	 * 保存日期
	 */
	private String		storeDate	= null;
	/**
	 * 保存人
	 */
	private String		storer		= null;
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

	public String getStoreDate() {
		return storeDate;
	}

	public void setStoreDate(String storeDate) {
		this.storeDate = storeDate;
	}

	public String getStorer() {
		return storer;
	}

	public void setStorer(String storer) {
		this.storer = storer;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}
