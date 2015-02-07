package com.bestway.ui.editor;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
/**
 * 编辑列
 */
public abstract class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
	protected JCheckBox cb;
	protected JTable table = null;

	public CheckBoxEditor(JCheckBox checkBox) {
		super(checkBox);
	}

	public Component getTableCellEditorComponent(JTable table,
			Object value, boolean isSelected, int row, int column) {
		if (value == null) {
			return null;
		}
		if (Boolean.valueOf(value.toString()) instanceof Boolean) {
			cb = new JCheckBox();
			cb
					.setSelected(Boolean.valueOf(value.toString())
							.booleanValue());
		}
		cb.setHorizontalAlignment(JLabel.CENTER);
		cb.addActionListener(this);
		this.table = table;
		return cb;
	}

	public Object getCellEditorValue() {
		cb.removeActionListener(this);
		return cb;
	}
}
