/*
 * Created on 2005-6-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

/**
 * render table JchcckBox 列
 */
public class TableCheckBoxRender extends DefaultTableCellRenderer {
	JCheckBox checkBox = new JCheckBox();

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		checkBox.setHorizontalAlignment(JLabel.CENTER);
		checkBox.setBackground(table.getBackground());
		if (isSelected) {
			checkBox.setForeground(table.getSelectionForeground());
			checkBox.setBackground(table.getSelectionBackground());
		} else {
			checkBox.setForeground(table.getForeground());
			checkBox.setBackground(table.getBackground());
		}
		if (value == null || "".equals(value.toString())) {
			checkBox.setSelected(false);
			return checkBox;
		} else {
			try {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
				return checkBox;
			} catch (Exception ex) {
				String str = value == null ? "" : value.toString();
				return super.getTableCellRendererComponent(table, str,
						isSelected, hasFocus, row, column);
			}
		}

	}
}
