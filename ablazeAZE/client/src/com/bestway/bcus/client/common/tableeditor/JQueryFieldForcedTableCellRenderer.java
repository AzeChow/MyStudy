package com.bestway.bcus.client.common.tableeditor;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import org.apache.commons.beanutils.BeanUtils;

public class JQueryFieldForcedTableCellRenderer extends DefaultTableCellRenderer {

    private String displayFieldName = null;

    /** Creates a new instance of ForcedEditTableCellRenderer */
    public JQueryFieldForcedTableCellRenderer(String displayFieldName) {
        super();
//        this.setHorizontalAlignment(SwingConstants.RIGHT);
        this.displayFieldName = displayFieldName;
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
                JQueryFiled queryField = (JQueryFiled) table.getEditorComponent();
                queryField.setValue(value);
                queryField.requestFocus();
            }
        }
        if (value != null && !"".equals(displayFieldName.trim())) {
            try {
                super.setText(BeanUtils.getProperty(value, displayFieldName));
            } catch (IllegalAccessException ex) {
                Logger.getLogger(JQueryFieldForcedTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(JQueryFieldForcedTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(JQueryFieldForcedTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (row==table.getSelectedRow()) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        return comp;
    }
}
