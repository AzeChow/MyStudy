package com.bestway.bcus.client.common.tableeditor;

import java.awt.Component;

import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class JNumberForcedTableCellRenderer extends DefaultTableCellRenderer {

	/** Creates a new instance of ForcedEditTableCellRenderer */
	public JNumberForcedTableCellRenderer() {
		super();
	}

	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		// Obtain the default component.
		Component comp = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		if (hasFocus && isSelected) {
			TableModel tblModel = table.getModel();
			if (tblModel.isCellEditable(row, column)) {
				// Cell is editable
				table.editCellAt(row, column);
				table.getEditorComponent().requestFocus();
				JFormattedTextField tf = (JFormattedTextField) table
						.getEditorComponent();
				if (value == null) {
					tf.setValue(0.0);
				} else {
					tf.setValue(Double.parseDouble(value.toString()));
				}
				tf.selectAll();
			}
		}
		return comp;
	}
	
}
