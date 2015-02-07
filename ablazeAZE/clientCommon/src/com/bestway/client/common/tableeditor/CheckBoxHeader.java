/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.client.common.tableeditor;

import com.bestway.client.util.JTableListModel;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author yp
 */
public class CheckBoxHeader extends JCheckBox
        implements TableCellRenderer, MouseListener {

    protected CheckBoxHeader rendererComponent;
    protected int column;
    protected JTable table;
    protected boolean mousePressed = false;
    protected boolean isShowChangeState = false;
    protected String propertyName = "isSelected";

    public CheckBoxHeader() {
        this(false, "isSelected");
    }

    public CheckBoxHeader(boolean isShowChangeState) {
        this(isShowChangeState, "isSelected");
    }

    public CheckBoxHeader(String propertyName) {
        this(false, propertyName);
    }

    public CheckBoxHeader(boolean isShowChangeState, String propertyName) {
        this.isShowChangeState = isShowChangeState;
        this.propertyName = propertyName;
        this.setBorderPainted(true);
        rendererComponent = this;
        rendererComponent.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                int row=table.getSelectedRow();
                JTableListModel tableModel = (JTableListModel) table.getModel();
                if (CheckBoxHeader.this.isShowChangeState) {
                    int rows = table.getRowCount();
                    for (int i = 0; i < rows; i++) {
                        tableModel.setValueAt(CheckBoxHeader.this.isSelected(), i, column);
                    }
                } else {
//                    String propertyName = ((JTableListColumn) table.getColumnModel().getColumn(column)).getProperty();
                    List list = tableModel.getList();
//                    long begintime = System.currentTimeMillis();
                    for (int i = 0; i < list.size(); i++) {
                        Object obj = list.get(i);
                        try {
                            BeanUtils.setProperty(obj, CheckBoxHeader.this.propertyName, CheckBoxHeader.this.isSelected());
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(CheckBoxHeader.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(CheckBoxHeader.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    tableModel.fireTableDataChanged();
//                    long endtime = System.currentTimeMillis();
//                    System.out.println("---totaltime:" + (endtime - begintime) / 1000.0);
                }
                if(row>=0){
                    table.setRowSelectionInterval(row, row);
                }
            }
        });
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        this.table = table;
        if (table != null) {
            JTableHeader header = table.getTableHeader();
            if (header != null) {
                rendererComponent.setForeground(header.getForeground());
                rendererComponent.setBackground(header.getBackground());
                rendererComponent.setFont(header.getFont());
                header.addMouseListener(rendererComponent);
            }
        }
        setColumn(column);
        rendererComponent.setText("选择");
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        return rendererComponent;
    }

    protected void setColumn(int column) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    protected void handleClickEvent(MouseEvent e) {
        if (mousePressed) {
            mousePressed = false;
            JTableHeader header = (JTableHeader) (e.getSource());
            JTable tableView = header.getTable();
            TableColumnModel columnModel = tableView.getColumnModel();
            int viewColumn = columnModel.getColumnIndexAtX(e.getX());
            int column = tableView.convertColumnIndexToModel(viewColumn);

            if (viewColumn == this.column && e.getClickCount() == 1 && column != -1) {
                doClick();
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        handleClickEvent(e);
        ((JTableHeader) e.getSource()).repaint();
    }

    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
