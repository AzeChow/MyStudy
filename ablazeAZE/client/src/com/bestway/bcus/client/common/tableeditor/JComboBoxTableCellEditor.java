package com.bestway.bcus.client.common.tableeditor;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.table.TableCellEditor;

import org.apache.commons.beanutils.BeanUtils;

import com.bestway.client.util.JTableListModel;

public class JComboBoxTableCellEditor extends CustomTableCellEditor implements
        TableCellEditor {//, DefaultCellEditor   AbstractCellEditor     FocusListener , KeyListener

    private static final long serialVersionUID = 1L;
    protected JComboBox comboBox;
    private JTextField textField;
    private int dataRow = -1;
    private Object editingData = null;
    private List<TableCellEditorListener> dataChangedCalcListeners = new ArrayList<TableCellEditorListener>();
    private List<TableCellEditorListener> persistListeners = new ArrayList<TableCellEditorListener>();
    private TableCellEditorEnableListener enableListener = null;
    private Object oldValue = null;
    private JTable table = null;
    private MouseAdapterExtend mouseAdapterExtend=null;

    // private JTable table = null;
    public JComboBoxTableCellEditor() {
        this.comboBox = new JComboBox();
        this.comboBox.setBorder(null);
        this.comboBox.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ("editor".equals(evt.getPropertyName())) {
                    if (evt.getNewValue() != null) {
                        ((BasicComboBoxEditor) evt.getNewValue()).getEditorComponent().addKeyListener(new java.awt.event.KeyAdapter() {

                            public void keyPressed(java.awt.event.KeyEvent evt) {
                                if (evt.getKeyCode() == 10) {// Enter
//                                    table.dispatchEvent(new KeyEvent((Container) evt.getSource(), KeyEvent.KEY_PRESSED, 0, 0,
//                                            KeyEvent.VK_TAB, evt.getKeyChar()));
//                                    Component comp = ((BasicComboBoxEditor) comboBox.getEditor()).getEditorComponent();
//                                    comp.dispatchEvent(new KeyEvent((Container) evt.getSource(), KeyEvent.KEY_PRESSED, 0, 0,
//                                            KeyEvent.VK_TAB, evt.getKeyChar()));
                                    System.out.println("-----");
                                    enterNextFocus(table);
                                    evt.consume();
                                }
                                if (evt.getKeyCode() == 40) {
                                    if (((JTableListModel) table.getModel()).hasNextRow()) {
                                        int col = table.getSelectedColumn();
                                        int row = table.getSelectedRow();
//                                        System.out.println("1-------row:" + row + " col:" + col);
                                        ((JTableListModel) table.getModel()).nextRow();
                                        table.editCellAt(row + 1, col);
//                                        System.out.println("2-------row:" + (row + 1) + " col:" + col);
//                                        JTextField comp = (JTextField) ((BasicComboBoxEditor) comboBox.getEditor()).getEditorComponent();
//                                        comp.requestFocus();
//                                        comp.selectAll();
                                        SwingUtilities.invokeLater(new Runnable() {

                                            public void run() {
                                                JTextField comp = (JTextField) ((BasicComboBoxEditor) comboBox.getEditor()).getEditorComponent();
                                                comp.requestFocus();
                                                comp.selectAll();
                                            }
                                        });
                                    }
                                }
                                if (evt.getKeyCode() == 38) {
                                    if (((JTableListModel) table.getModel()).hasPreviousRow()) {
                                        int col = table.getSelectedColumn();
                                        int row = table.getSelectedRow();
                                        ((JTableListModel) table.getModel()).previousRow();
                                        table.editCellAt(row - 1, col);
//                                        System.out.println("-------row:" + (row + 1) + " col:" + col);
                                        SwingUtilities.invokeLater(new Runnable() {

                                            public void run() {
                                                JTextField comp = (JTextField) ((BasicComboBoxEditor) comboBox.getEditor()).getEditorComponent();
                                                comp.requestFocus();
                                                comp.selectAll();
                                            }
                                        });
                                    }
                                }

                            }
                        });
                    }
                }
            }
        });
        this.textField = new JTextField();
        this.textField.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == 10) {// Enter
//                                    table.dispatchEvent(new KeyEvent((Container) evt.getSource(), KeyEvent.KEY_PRESSED, 0, 0,
//                                            KeyEvent.VK_TAB, evt.getKeyChar()));
//                                    Component comp = ((BasicComboBoxEditor) comboBox.getEditor()).getEditorComponent();
//                                    comp.dispatchEvent(new KeyEvent((Container) evt.getSource(), KeyEvent.KEY_PRESSED, 0, 0,
//                                            KeyEvent.VK_TAB, evt.getKeyChar()));
                    enterNextFocus(table);
                    evt.consume();
                }
            }
        });
        this.textField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(new Runnable(){
                    public void run() {
                        textField.selectAll();
                    }
                });
            }
        });
        mouseAdapterExtend = new MouseAdapterExtend(this.textField);
        this.textField.addMouseListener(mouseAdapterExtend);
        this.textField.getDocument().addUndoableEditListener(mouseAdapterExtend.getUndoManager());
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.dataRow = row;
        this.table = table;
        if(!mouseAdapterExtend.hasExtendMenuItem()){
            mouseAdapterExtend.addExtendMenuItem(TableCellExtendMenuItemUtil.getExtendMenuItems((JTableListModel)table.getModel()));
        }
        if (table.getModel() instanceof JTableListModel) {
            editingData = ((JTableListModel) table.getModel()).getDataByRow(row);
        }
//        System.out.println("---------"+value);
        setOldVaue(value);
        setCellEditorValue(
                value);
        if (enableListener != null) {
            boolean isEnabled = enableListener.isCanEdit(new TableCellEditorParameter(dataRow,
                    editingData));
//            this.comboBox.setEnabled();
            if (isEnabled) {
                return this.comboBox;
            } else {
                this.textField.setEditable(false);
                if (value instanceof String || value instanceof Integer) {
                    this.textField.setText(value.toString());
                } else {
                    try {
                        if (value != null && !"".equals(value)) {
                            this.textField.setText(BeanUtils.getProperty(value, "name"));
                        }
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(JComboBoxTableCellEditor.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(JComboBoxTableCellEditor.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NoSuchMethodException ex) {
                        Logger.getLogger(JComboBoxTableCellEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return this.textField;
            }
        }
        return this.comboBox;


    }

    public void setCellEditorValue(Object value) {
        this.comboBox.setSelectedItem(value);


    }

    public JComboBox getComboBox() {
        return comboBox;


    }

    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();


    }

    private void setOldVaue(Object value) {
        this.oldValue = value;


    }
//    public void focusLost(FocusEvent e) {
//    }

    public void addDataChangedListener(final TableCellEditorListener callBack) {
        if (callBack != null) {
            dataChangedCalcListeners.add(callBack);


        }
    }

    public void addPersistListener(final TableCellEditorListener callBack) {
        if (callBack != null) {
            persistListeners.add(callBack);


        }
    }

    @Override
    public boolean stopCellEditing() {
        super.stopCellEditing();
        Object newValue = this.getCellEditorValue();


        if (!compareObject(oldValue, newValue)) {
            for (TableCellEditorListener callBack : dataChangedCalcListeners) {
                callBack.run(new TableCellEditorParameter(dataRow, editingData));


            } //        System.out.println("---oldValue:" + oldValue + "  newValue:" + newValue);
            for (TableCellEditorListener callBack : persistListeners) {
                callBack.run(new TableCellEditorParameter(dataRow, editingData));


            }
        }
        return true;


    }

    public TableCellEditorEnableListener getEnableListener() {
        return enableListener;


    }

    public void setEnableListener(TableCellEditorEnableListener enableListener) {
        this.enableListener = enableListener;


    }

    private boolean compareObject(Object oldValue, Object newValue) {
        if (oldValue == null && newValue == null) {
            return true;


        } else if (oldValue != null && newValue == null) {
            return false;


        } else if (oldValue == null && newValue != null) {
            return false;


        } else {
            return oldValue.equals(newValue);

        }
    }
}
