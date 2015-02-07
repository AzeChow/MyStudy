/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.bcus.client.common.tableeditor;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;

/**
 *
 * @author Administrator
 */
public class JCopyPastePopupMenu extends JPopupMenu {

//    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); //获得系统剪切板
    private JMenuItem miCopy = null;
    private JMenuItem miCut = null;
    private JMenuItem miPaste = null;
    private JMenuItem miUndo = null;
    private JMenuItem miRedo = null;
    private JMenuItem miSelectAll = null;
    private JMenuItem miDelete = null;
    private JTextComponent textComponent = null;
    private UndoManager undoManager = null;

    public void setTextComponent(JTextComponent textComponent) {
        this.textComponent = textComponent;
    }

    public void setUndoManager(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    public JCopyPastePopupMenu() {
        this.add(this.getMiCopy());
        this.add(this.getMiCut());
        this.add(this.getMiPaste());
        this.add(new JSeparator());
        this.add(this.getMiUndo());
        this.add(this.getMiRedo());
        this.add(new JSeparator());
        this.add(this.getMiSelectAll());
        this.add(this.getMiDelete());
    }

    public JMenuItem getMiCopy() {
        if (miCopy == null) {
            miCopy = new JMenuItem();
            miCopy.setText("  复制");
            miCopy.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (textComponent != null) {
                        textComponent.copy();
                    }
                }
            });
        }
        return miCopy;
    }

    public JMenuItem getMiCut() {
        if (miCut == null) {
            miCut = new JMenuItem();
            miCut.setText("  剪切");
            miCut.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (textComponent != null) {
                        textComponent.cut();
                    }
                }
            });
        }
        return miCut;
    }

    public JMenuItem getMiPaste() {
        if (miPaste == null) {
            miPaste = new JMenuItem();
            miPaste.setText("  粘贴");
            miPaste.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (textComponent != null) {
                        textComponent.paste();
                    }
                }
            });
        }
        return miPaste;
    }

    public JMenuItem getMiDelete() {
        if (miDelete == null) {
            miDelete = new JMenuItem();
            miDelete.setText("  删除");
            miDelete.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (textComponent != null) {
                        textComponent.replaceSelection("");
                    }
                }
            });
        }
        return miDelete;
    }

    public JMenuItem getMiRedo() {
        if (miRedo == null) {
            miRedo = new JMenuItem();
            miRedo.setText("  重做");
            miRedo.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (textComponent != null && undoManager != null) {
                        try {
                            undoManager.redo();
                        } catch (Exception ex) {
                        }
                    }
                }
            });
        }
        return miRedo;
    }

    public JMenuItem getMiSelectAll() {
        if (miSelectAll == null) {
            miSelectAll = new JMenuItem();
            miSelectAll.setText("  全选");
            miSelectAll.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (textComponent != null) {
                        textComponent.selectAll();
                    }
                }
            });
        }
        return miSelectAll;
    }

    public JMenuItem getMiUndo() {
        if (miUndo == null) {
            miUndo = new JMenuItem();
            miUndo.setText("  撤消");
            miUndo.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (textComponent != null && undoManager != null) {
                        try {
                            undoManager.undo();
                        } catch (Exception ex) {
                        }
                    }
                }
            });
        }
        return miUndo;
    }

    public void setState() {
        boolean isEditable = isEditable();
        String selectedText = this.getSelectedText();
        this.miCopy.setEnabled(selectedText != null && !"".equals(selectedText));
        this.miCut.setEnabled(isEditable && selectedText != null && !"".equals(selectedText));
        this.miPaste.setEnabled(isEditable);
        this.miUndo.setEnabled(isEditable);
        this.miRedo.setEnabled(isEditable);
        this.miDelete.setEnabled(isEditable && selectedText != null && !"".equals(selectedText));
    }

    private String getSelectedText() {
        return textComponent.getSelectedText();
    }

    private boolean isEditable() {
        return textComponent.isEditable();
    }
}
