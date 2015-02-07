package com.bestway.bcus.client.common.tableeditor;

public class TableCellEditorParameter {
	private int editingRow = -1;

	private Object editingData = null;

	public TableCellEditorParameter(int currRow, Object currData) {
		this.editingRow = currRow;
		this.editingData = currData;
	}

	// public TableCellEditorParameter(Object currData) {
	// this.editingData = currData;
	// }

	public int getEditingRow() {
		return editingRow;
	}

	public void setEditingRow(int dataRow) {
		this.editingRow = dataRow;
	}

	public Object getEditingData() {
		return editingData;
	}

	public void setEditingData(Object currData) {
		this.editingData = currData;
	}
}
