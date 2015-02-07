package com.bestway.bcus.client.common.tableeditor;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import org.apache.commons.beanutils.BeanUtils;

public class JComboBoxForcedTableCellRenderer extends DefaultTableCellRenderer {

    private String displayFieldName = null;

    /** Creates a new instance of ForcedEditTableCellRenderer */
    public JComboBoxForcedTableCellRenderer(String displayFieldName) {
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
                if (table.getEditorComponent() instanceof JComboBox) {
                    JComboBox comboBox = (JComboBox) table.getEditorComponent();
                    setCellEditorValue(comboBox, value);
                } else if (table.getEditorComponent() instanceof JTextField) {
                    if (value != null && displayFieldName != null && !"".equals(displayFieldName.trim())) {
                        try {
                            ((JTextField) table.getEditorComponent()).setText(BeanUtils.getProperty(value, displayFieldName));
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(JComboBoxForcedTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(JComboBoxForcedTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchMethodException ex) {
                            Logger.getLogger(JComboBoxForcedTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        ((JTextField) table.getEditorComponent()).setText(value.toString());
                    }
                }
            }
        }
        setDisplayText(this, value);
        if (row == table.getSelectedRow()) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        return comp;
    }

    public void setCellEditorValue(JComboBox comboBox, Object value) {
        comboBox.setSelectedItem(value);
    }

    public void setDisplayText(JLabel label, Object value) {
        if (value != null && displayFieldName != null && !"".equals(displayFieldName.trim())) {
        	System.out.println("===value=="+value+"=displayFieldName="+displayFieldName);
            try {
                label.setText(BeanUtils.getProperty(value, displayFieldName));
            } catch (IllegalAccessException ex) {
                Logger.getLogger(JComboBoxForcedTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(JComboBoxForcedTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(JComboBoxForcedTableCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
