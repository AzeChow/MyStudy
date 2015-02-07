package com.bestway.client.util;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public abstract class JTableListModelBase extends AbstractTableModel {
    public abstract Class getTypeByColumn(int col);
    public abstract List getList();
    public abstract JTable getTable();
    public abstract int getColumnWidth(int i);
    public abstract Object getValueAt(Object obj, int col);
}
