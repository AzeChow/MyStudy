package com.bestway.client.util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellRenderers {

	public static class TableCheckBoxRender extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
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
					checkBox
							.setSelected(Boolean.parseBoolean(value.toString()));
					return checkBox;
				} catch (Exception ex) {
					String str = value == null ? "" : value.toString();
					return super.getTableCellRendererComponent(table, str,
							isSelected, hasFocus, row, column);
				}
			}

		}
	}

    public static class CheckBoxEditor extends DefaultCellEditor implements ActionListener {

        public JCheckBox cb;
        public JTable table = null;

        public CheckBoxEditor(final JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int row, int column) {
            if (value == null) {
                return null;
            }
            if (Boolean.valueOf(value.toString()) instanceof Boolean) {
                cb = new JCheckBox();
                cb.setSelected(Boolean.valueOf(value.toString()).booleanValue());
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

        public void actionPerformed(ActionEvent e) {
        }
        //    public void actionPerformed(ActionEvent e) {
//            JTableListModel tableModel = (JTableListModel) this.table.getModel();
//            Object obj = tableModel.getCurrentRow();
//            //  if (obj instanceof this.object) {
//            //   object temp = (object) obj;
//            //  temp.setIsSelected(new Boolean(cb.isSelected()));
//            // tableModel.updateRow(obj);
//            //  }
//            fireEditingStopped();
        //   }
    }
	public static class TableDateRender extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null || value.toString().equals("")) {
				this.setText("");
			}
			if (value instanceof Date) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				this.setText(df.format((Date) value));
			} else {
				this.setText(value.toString());
			}
			return this;
		}
	}
}
