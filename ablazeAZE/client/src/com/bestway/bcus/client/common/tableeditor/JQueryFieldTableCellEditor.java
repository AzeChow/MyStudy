package com.bestway.bcus.client.common.tableeditor;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.bestway.bcus.client.common.tableeditor.JQueryFiled.NextFocusListenser;
import com.bestway.bcus.client.common.tableeditor.JQueryFiled.QueryDataListenser;
import com.bestway.bcus.client.common.tableeditor.JQueryFiled.QueryFormListenser;
import com.bestway.client.util.JTableListModel;

public class JQueryFieldTableCellEditor extends CustomTableCellEditor implements
        TableCellEditor {

    private static final long serialVersionUID = 1L;
    private JQueryFiled queryField;
    private int dataRow = -1;
    private Object editingData = null;
    private List<TableCellEditorListener> dataChangedListeners = new ArrayList<TableCellEditorListener>();
    private TableCellEditorEnableListener enableListener = null;
    private Object oldValue = null;
    private JTable table = null;

    // private JTable table = null;
    public JQueryFieldTableCellEditor(String displayFiledName) {
        this.queryField = new JQueryFiled(displayFiledName);
        this.queryField.setBorder(null);
        this.queryField.setNextFocusListenser(new NextFocusListenser() {

            public void enterNextFocus() {
                JQueryFieldTableCellEditor.this.enterNextFocus(table);
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.dataRow = row;
        this.table = table;
        this.queryField.setTable(table);
        if (table.getModel() instanceof JTableListModel) {
            editingData = ((JTableListModel) table.getModel()).getDataByRow(row);
        }
//        System.out.println("---------" + value);
        setOldVaue(value);
        this.queryField.setValue(value);
        if (enableListener != null) {
            this.queryField.setEnabled(enableListener.isCanEdit(new TableCellEditorParameter(dataRow,
                    editingData)));
        }
        return this.queryField;
    }

    public Object getCellEditorValue() {
        return queryField.getValue();
    }

    public void addDataChangedListener(final TableCellEditorListener callBack) {
        if (callBack != null) {
            dataChangedListeners.add(callBack);
        }
    }

    private void setOldVaue(Object value) {
        this.oldValue = value;
    }

    @Override
    public boolean stopCellEditing() {
        super.stopCellEditing();
        Object newValue = this.queryField.getValue();
        if (!compareObject(oldValue, newValue)) {
            if (dataChangedListeners.size() > 0) {
                for (TableCellEditorListener callBack : dataChangedListeners) {
                    callBack.run(new TableCellEditorParameter(dataRow, editingData));
//                    System.out.println("---oldValue:" + oldValue + "  newValue:" + newValue);
                }
            }
        }
        return true;
    }

    public TableCellEditorEnableListener getEnableListener() {
        return enableListener;
    }

    public void setEnableListener(TableCellEditorEnableListener enableListener) {
        this.enableListener = enableListener;
    }

    public void setQueryDataListenser(QueryDataListenser queryDataListenser) {
        this.queryField.setQueryDataListenser(queryDataListenser);
    }

    public void setQueryFormListenser(QueryFormListenser queryFormListenser) {
        this.queryField.setQueryFormListenser(queryFormListenser);
    }

    private boolean compareObject(Object oldValue, Object newValue) {
        if (oldValue == null && newValue == null) {
            return true;
        } else if (oldValue != null && newValue == null) {
            return false;
        } else if (oldValue == null && newValue != null) {
            return false;
        } else {
            return oldValue.equals(newValue);
        }
    }
}
