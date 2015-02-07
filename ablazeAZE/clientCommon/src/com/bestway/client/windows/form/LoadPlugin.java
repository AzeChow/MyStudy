package com.bestway.client.windows.form;

import javax.swing.JMenu;
import javax.swing.JTree;

import com.bestway.client.windows.form.FmMain.MenuActionListener;
import com.bestway.ui.winuicontrol.JMDIMenuBar;

public interface LoadPlugin {
    void load(JTree jTree);
    
    void load(JMDIMenuBar jJMenuBar, JMenu mOther, boolean isOverFlow,
			int totalWidth, int menuBarWidth, MenuActionListener actionListener);
}
