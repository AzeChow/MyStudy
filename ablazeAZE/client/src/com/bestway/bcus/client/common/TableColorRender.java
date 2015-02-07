/*
 * Created on 2005-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

/**
 * render table color row
 */
public class TableColorRender {

    /**
     * 设置单行的颜色显示
     * 
     * @param table
     * @param row
     */
    public static void setTableRowColor(JTable table, int row) {
        setTableRowColor(table, row, null, null);
    }

    /**
     * 设置单行的颜色显示
     * 
     * @param table
     * @param row
     */
    public static void setTableRowColor(JTable table, int row,
            Color foreground, Color background) {
        List<Integer> rowList = new ArrayList<Integer>();
        rowList.add(row);
        setTableRowColor(table, rowList, foreground, background);
    }

    /**
     * 设置多行的颜色显示
     * 
     * @param table
     * @param rowList
     */
    public static void setTableRowColor(JTable table, List<Integer> rowList) {
        TableColorRender.setTableRowColor(table, rowList, null, null);
    }

    /**
     * 设置多行的颜色显示
     * 
     * @param table
     * @param rowList
     */
    public static void setTableRowColor(JTable table, List<Integer> rowList,
            Color foreground, Color background) {
        int columnCount = table.getModel().getColumnCount();
        for (int i = 1; i < columnCount; i++) {
            TableCellRenderer temp = table.getColumnModel().getColumn(i)
                    .getCellRenderer();
            TableCellColorRender render = null;
            if (temp instanceof TableCellColorRender) {
                render = (TableCellColorRender) temp;
            } else {
                render = new TableCellColorRender();
            }
            if (foreground != null) {
                render.setForeColor(foreground);
            }
            if (background != null) {
                render.setBackColor(background);
            }
            render.setRowList(rowList);
            table.getColumnModel().getColumn(i).setCellRenderer(render);
        }
        table.repaint();
        table.validate();
    }

    /**
     * 设置单列的颜色显示
     * 
     * @param table
     * @param row
     */
    public static void setTableColumnColor(JTable table, int column) {
        setTableColumnColor(table, column, null, null);
    }

    /**
     * 设置单列的颜色显示
     * 
     * @param table
     * @param row
     */
    public static void setTableColumnColor(JTable table, int column,
            Color foreground, Color background) {
        List<Integer> columnList = new ArrayList<Integer>();
        columnList.add(column);
        setTableColumnColor(table, columnList, foreground, background);
    }

    /**
     * 设置多列的颜色显示
     * 
     * @param table
     * @param columnList
     */
    public static void setTableColumnColor(JTable table,
            List<Integer> columnList) {
        setTableColumnColor(table, columnList, null, null);
    }

    /**
     * 设置多列的颜色显示
     * 
     * @param table
     * @param columnList
     */
    public static void setTableColumnColor(JTable table,
            List<Integer> columnList, Color foreground, Color background) {
        int columnCount = table.getModel().getColumnCount();
        for (int i = 1; i < columnCount; i++) {
            TableCellRenderer temp = table.getColumnModel().getColumn(i)
                    .getCellRenderer();
            TableCellColorRender render = null;
            if (temp instanceof TableCellColorRender) {
                render = (TableCellColorRender) temp;
            } else {
                render = new TableCellColorRender();
            }
            if (foreground != null) {
                render.setForeColor(foreground);
            }
            if (background != null) {
                render.setBackColor(background);
            }
            render.setColumnList(columnList);
            table.getColumnModel().getColumn(i).setCellRenderer(render);
        }
        table.repaint();
        table.validate();
    }

}
