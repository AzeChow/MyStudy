package com.bestway.ui.winuicontrol;

import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;

public class JNaviMenuItem extends JMenuItem {
	private JInternalFrame jf = null;

	public JNaviMenuItem(JInternalFrame jf){
		super();
		this.jf=jf;
		this.setText(jf.getTitle());
	}

	public JInternalFrame getJf() {
		return jf;
	}
}
