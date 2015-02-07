package com.bestway.bcus.client.common;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.JTableListModel;

/**
 * 编辑列
 */
public class TableTextFieldEditor extends DefaultCellEditor implements KeyListener,
        FocusListener {
    private JTextField                tf              = null;
    private JTable                    table           = null;
    private String                    editBeforeValue = "";
    private Integer                   column          = -1;
    private TableTextFieldEditorEvent event           = null;

    public TableTextFieldEditor(JTextField tf, TableTextFieldEditorEvent event) {
        super(tf);
        this.tf = tf;
        this.event = event;
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value != null) {
            tf.setText((String) value);
        }
        tf.addFocusListener(this);
        tf.addKeyListener(this);
        this.column = column;
        this.table = table;
        return tf;
    }

    public Object getCellEditorValue() {
        tf.removeFocusListener(this);
        tf.removeKeyListener(this);
        return tf;
    }

    public void focusGained(FocusEvent e) {
        editBeforeValue = tf.getText();
    }

    private void saveData() {
        JTableListModel tableModel = (JTableListModel) this.table.getModel();
        Object obj = tableModel.getCurrentRow();
        String fieldName = tableModel.getPropertyName(column);
        Class cls = CommonVariables.getTypeByField(obj.getClass(), fieldName);
        try {
            Object value = null;

            //System.out.println(obj.getClass());
            
            if (cls.equals(Integer.class) || cls.equals(Long.class)
                    || cls.equals(Short.class)) {
                try {
                    if ("".equals(tf.getText().trim())) {
                        value = 0;
                    } else {
                        value = Integer.valueOf(tf.getText());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(getWindowComponet(table), "不是有效的整数,请重新输入!!",
                            "提示", 2);
                    return;
                }
            } else if (cls.equals(Double.class) || cls.equals(Float.class)) {
                try {
                    if ("".equals(tf.getText().trim())) {
                        value = 0.0;
                    } else {
                        value = Double.valueOf(tf.getText());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(getWindowComponet(table), "不是有效的数字,请重新输入!!",
                            "提示", 2);
                    return;
                }
            } else if (cls.equals(String.class)) {
                value = tf.getText();
            }
            
            PropertyUtils.setProperty(obj, fieldName, value);
            obj = this.event.saveObject(obj);
            tableModel.updateRow(obj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        fireEditingStopped();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            String editAfterValue = tf.getText();
            if (!editAfterValue.equalsIgnoreCase(editBeforeValue)) {
                saveData();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void focusLost(FocusEvent e) {
    }
    
    
    
    private Component getWindowComponet(Component component){
        Component tempComponent = component.getParent() ;
        if(tempComponent instanceof JDialog || tempComponent instanceof JFrame
                || tempComponent instanceof JInternalFrame){
            return tempComponent;
        }
        return getWindowComponet(tempComponent);
    }
}
