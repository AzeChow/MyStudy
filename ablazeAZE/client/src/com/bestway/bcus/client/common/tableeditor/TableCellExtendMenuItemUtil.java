/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestway.bcus.client.common.tableeditor;

import com.bestway.client.util.JTableListModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author Administrator
 */
public class TableCellExtendMenuItemUtil {

    public static List<JMenuItem> getExtendMenuItems(JTableListModel tableModel){
        List<JMenuItem> list=new ArrayList<JMenuItem>();        
        list.add(tableModel.getMiSaveAllToExcel());
        list.add(tableModel.getMiSearch());
        return list;
    }
}
