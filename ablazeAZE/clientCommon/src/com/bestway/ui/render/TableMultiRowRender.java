/*
 * Created on 2005-6-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.ui.render;

import java.awt.Component;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;

import com.bestway.client.util.JTableListModel;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */

/**
 * render table JchcckBox 列
 */
public class TableMultiRowRender extends JTextArea implements TableCellRenderer {// extends

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setLineWrap(true);
		setWrapStyleWord(true);
		setOpaque(true);

		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(table.getBackground());
		}
		setText((value == null) ? "" : value.toString());
		setSize(table.getColumnModel().getColumn(
				table.convertColumnIndexToModel(column)).getWidth(), 8);
		int height = (int) getPreferredSize().getHeight();
		if (this.check((JTableListModel) table.getModel(), row, column, height)
				&& height != table.getRowHeight(row)) {
			table.setRowHeight(row, height);
			this.cacheColumnHeight((JTableListModel) table.getModel(), row,
					column, height);
		}
		return this;
	}

	private boolean check(JTableListModel tableModel, int row, int column,
			int height) {
		int iCount = tableModel.getTable().getColumnModel().getColumnCount();
		Map<Integer, Integer> map = tableModel.getMapColumnHeight().get(row);
		if (map == null) {
			return true;
		}
		for (int i = 0; i < iCount; i++) {
			if (i == column) {
				continue;
			}
			Integer otherHeight = map.get(i);
			if (otherHeight == null || height >= otherHeight) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	private void cacheColumnHeight(JTableListModel tableModel, int row,
			int column, int height) {
		Map<Integer, Integer> map = tableModel.getMapColumnHeight().get(row);
		if (map == null) {
			map = new Hashtable<Integer, Integer>();
			tableModel.getMapColumnHeight().put(row, map);
		}
		map.put(column, height);
	}
}
