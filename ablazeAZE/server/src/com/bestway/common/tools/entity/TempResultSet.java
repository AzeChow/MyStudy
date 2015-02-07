package com.bestway.common.tools.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TempResultSet implements Serializable {
	String[]	columnNames	= {};
	List<List>		rows		= new ArrayList<List>();
	
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
	
	
	

}
