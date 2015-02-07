/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.bcus.client.common.tableeditor;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;

/**
 *
 * @author Administrator
 */
public class MouseAdapterExtend extends MouseAdapter {

    private JCopyPastePopupMenu popupMenu = new JCopyPastePopupMenu();
    private List<JMenuItem> extendMenuItems = new ArrayList<JMenuItem>();
    private UndoManager undoManager = new UndoManager();
    private JTextComponent textComponent;

    public UndoManager getUndoManager() {
        return undoManager;
    }

    public MouseAdapterExtend(JTextComponent component) {
        this.textComponent = component;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.isMetaDown()) {//检测鼠标右键单击
//            System.out.println("--------" + popupMenu.getComponents().length);
            popupMenu.setTextComponent(textComponent);
            popupMenu.setUndoManager(undoManager);
            popupMenu.show((Component) e.getSource(), e.getX(), e.getY());
            popupMenu.setState();
        }
    }

    public void addExtendMenuItem(List<JMenuItem> list) {
        if (list.size() <= 0) {
            return;
        }
        extendMenuItems = list;
        popupMenu.addSeparator();
        for (JMenuItem menuItem : extendMenuItems) {
            JMenuItem menuItemCopy = new JMenuItem();
            menuItemCopy.setText(menuItem.getText());
            menuItemCopy.setIcon(menuItem.getIcon());
            ActionListener[] actionListeners = menuItem.getActionListeners();
            for (int i = 0; i < actionListeners.length; i++) {
                menuItemCopy.addActionListener(actionListeners[i]);
            }
            popupMenu.add(menuItemCopy);
        }
    }

    public boolean hasExtendMenuItem() {
        boolean b = extendMenuItems.size() > 0;
//        System.out.println("---------b:"+b);
        return b;
    }
}
