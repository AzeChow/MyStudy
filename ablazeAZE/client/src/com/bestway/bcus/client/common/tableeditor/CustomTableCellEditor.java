/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.bcus.client.common.tableeditor;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;

import com.bestway.client.util.CreateTableDataListener;
import com.bestway.client.util.JTableListModel;

/**
 * 
 * @author yp
 */
public abstract class CustomTableCellEditor extends AbstractCellEditor {

	protected List<Integer> editableCols = null;
	private boolean isSkip = false;

	public boolean isIsSkip() {
		return isSkip;
	}

	public void setIsSkip(boolean isSkip) {
		this.isSkip = isSkip;
	}

	protected void enterNextFocus(JTable table) {		
		if (editableCols == null || editableCols.isEmpty()) {
            initEditableRows(table);
        }
        int row = table.getSelectedRow();
        int col = table.getEditingColumn();
        int index = editableCols.indexOf(col);
        if (index < editableCols.size() - 1) {//不是最后可编辑的一列
            col = editableCols.get(index + 1);
            table.changeSelection(row, col, true, true);
        } else if (index == editableCols.size() - 1) {//最后可编辑的一列
            if (row < table.getRowCount() - 1) {//不是最后一行
                row++;
                col = editableCols.get(0);
                table.changeSelection(row, col, true, true);
            } else {//最后一行
                JTableListModel tableModel = (JTableListModel) table.getModel();
                CreateTableDataListener createTableDataListener = tableModel.getCreateTableDataListener();
                Object obj = createTableDataListener == null ? null : createTableDataListener.createData();
                if (createTableDataListener != null && obj != null) {
                    col = editableCols.get(0);
                    tableModel.addRow(obj);
                    row = table.getSelectedRow();
                } else {
                    row = 0;
                    col = editableCols.get(0);
                    table.changeSelection(row, col, true, true);                                    
                }
            }

        }
        table.editCellAt(row, col);
        table.setRowSelectionInterval(row, row);
        table.setColumnSelectionInterval(col, col);
        Object value = table.getModel().getValueAt(table.convertRowIndexToModel(row), col);
        if (table.getCellEditor() != null) {
            Component comp = table.getCellEditor().getTableCellEditorComponent(table, value, true, row, col);
            comp.requestFocus();
        }        
	}

	private void initEditableRows(JTable table) {
		int columnCount = table.getColumnCount();
		int row = table.getSelectedRow();
		editableCols = new ArrayList<Integer>();
		for (int i = 0; i < columnCount; i++) {
			if (table.getModel().isCellEditable(row, i)) {
				if (table.getColumnModel().getColumn(i).getCellEditor() instanceof CustomTableCellEditor) {
					CustomTableCellEditor editor = (CustomTableCellEditor) table
							.getColumnModel().getColumn(i).getCellEditor();
					if (!editor.isSkip) {
						editableCols.add(i);
					}
				}
				// System.out.println("----------"+i);
			}
		}
	}
}