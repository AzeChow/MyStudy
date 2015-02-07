package com.bestway.bcus.client.common;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CheckBoxListCellRenderer extends JCheckBox implements
		ListCellRenderer {
	private JComboBox	comboBox	= null;

	public CheckBoxListCellRenderer(JComboBox comboBox) {
		this.comboBox = comboBox;
		this.comboBox.setEditable(true);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setEnabled(true);
		if (value != null) {
			if (value instanceof CheckBoxListItem) {
				final CheckBoxListItem item = (CheckBoxListItem) value;
				setSelected(item.getIsSelected());
				this.setText(item.toString());
			}else if(value instanceof CheckBoxListItemForFinancial){
				final CheckBoxListItemForFinancial item = (CheckBoxListItemForFinancial) value;
				setSelected(item.getIsSelected());
				this.setText(item.toString());
			} else{
				this.setText(value.toString());
			}
		}
		if (isSelected) {
			this.setForeground(list.getSelectionForeground());
			this.setBackground(list.getSelectionBackground());
			this.setFont(new Font(null, Font.BOLD, 12));
		} else {
			this.setForeground(list.getForeground());
			this.setBackground(list.getBackground());
			this.setFont(list.getFont());
		}
		return this;
	}
	
}
