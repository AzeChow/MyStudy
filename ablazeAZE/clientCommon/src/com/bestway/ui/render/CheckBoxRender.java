package com.bestway.ui.render;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CheckBoxRender extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JCheckBox checkBox = new JCheckBox();

	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		if (value == null) {
			return this;
		}
		if (Boolean.valueOf(value.toString()) instanceof Boolean) {
			checkBox.setSelected(Boolean.parseBoolean(value.toString()));
			checkBox.setHorizontalAlignment(JLabel.CENTER);
			checkBox.setBackground(table.getBackground());
			if (isSelected) {
				checkBox.setForeground(table.getSelectionForeground());
				checkBox.setBackground(table.getSelectionBackground());
			} else {
				checkBox.setForeground(table.getForeground());
				checkBox.setBackground(table.getBackground());
			}
			return checkBox;
		}
		String str = (value == null) ? "" : value.toString();
		return super.getTableCellRendererComponent(table, str, isSelected,
				hasFocus, row, column);
	}
}
