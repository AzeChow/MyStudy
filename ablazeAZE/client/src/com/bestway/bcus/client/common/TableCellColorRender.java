/*
 * Created on 2005-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

/**
 * render table color row
 */
public class TableCellColorRender extends DefaultTableCellRenderer {

    private List<Integer> rowList    = new ArrayList<Integer>();
    private List<Integer> columnList = new ArrayList<Integer>();
    private Color         foreground = Color.BLUE;
    private Color         background = Color.WHITE;

    public TableCellColorRender() {
        super();
    }

    public void setColumn(int column) {
        this.columnList.add(column);
    }

    public void setRow(int row) {
        this.columnList.add(row);
    }

    public void setColumnList(List<Integer> columnList) {
        this.columnList = columnList;
    }

    public void setRowList(List<Integer> rowList) {
        this.rowList = rowList;
    }

    
    public void setBackColor(Color background) {
        this.background = background;
    }

    
    public void setForeColor(Color foreground) {
        this.foreground = foreground;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
        if (rowList.contains(row) || columnList.contains(column)) {
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
            } else {
                c.setBackground(background);
            }
            // c.setFont(new Font(null,Font.BOLD, 14));
            c.setForeground(foreground);
        } else {
            if (isSelected) {
                c.setForeground(table.getSelectionForeground());
                c.setBackground(table.getSelectionBackground());
            } else {
                c.setForeground(table.getForeground());
                c.setBackground(table.getBackground());
            }
        }
        return c;
    }

}
