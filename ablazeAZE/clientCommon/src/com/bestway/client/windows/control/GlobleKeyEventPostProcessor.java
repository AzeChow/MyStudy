package com.bestway.client.windows.control;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.KeyEventPostProcessor;
import java.awt.Window;
import java.awt.event.KeyEvent;

import javax.swing.JInternalFrame;

import com.bestway.client.windows.form.FmMain;
import com.bestway.help.FmHelp;
import com.bestway.ui.winuicontrol.JDialogBase;

public class GlobleKeyEventPostProcessor implements KeyEventPostProcessor {
	public boolean postProcessKeyEvent(KeyEvent e) {
		if (e.isShiftDown() && e.isControlDown()
				&& (KeyEvent.VK_Z == e.getKeyCode())) {
			FmScreenCapture.getInstance().setVisible(true);
		} else if (e.isControlDown() && (KeyEvent.VK_Q == e.getKeyCode())) {
			try {
				FmMain
						.getInstance()
						.showTargetForm(
								"",
								"com.bestway.common.client.tools.FmQueryTool",
								"数据查询",
								FmMain
										.getInstance()
										.getIconByName(
												"/com/bestway/client/resource/images/search.gif"),
								null);
			} finally {
			}
		} else if (e.isControlDown() && (KeyEvent.VK_D == e.getKeyCode())) {
			try {
				FmMain
						.getInstance()
						.showTargetForm(
								"",
								"com.bestway.common.client.tools.FmDataDict",
								"数据字典",
								FmMain
										.getInstance()
										.getIconByName(
												"/com/bestway/client/resource/images/search.gif"),
								null);
			} finally {
			}
		} else if (KeyEvent.VK_F2 == e.getKeyCode()) {
			if (KeyEvent.KEY_PRESSED == e.getID()) {
				JDialogBase jDialog = null;
				Window d = FmMain.getInstance().getLastOpenedWindow();
				if (d instanceof JDialogBase) {
					jDialog = (JDialogBase) d;
					if (FmMain.getInstance().getWidth() != jDialog.getWidth()
							&& FmMain.getInstance().getHeight() != jDialog
									.getHeight()) {
						FmMain.getInstance().setOldwidth(jDialog.getWidth());
						FmMain.getInstance().setOldheight(jDialog.getHeight());
						jDialog.setPreferredSize(new Dimension(FmMain
								.getInstance().getWidth(), FmMain.getInstance()
								.getHeight()));
					} else {
						jDialog.setPreferredSize(new Dimension(FmMain
								.getInstance().getOldwidth(), FmMain
								.getInstance().getOldheight()));
					}
					jDialog.pack();
					jDialog.validate();
					int x = (FmMain.getInstance().getWidth() - jDialog
							.getWidth()) / 2;
					int y = (FmMain.getInstance().getHeight() - jDialog
							.getHeight()) / 2;
					jDialog.setLocation(x, y);
				}
			}
		} else if (KeyEvent.VK_F1 == e.getKeyCode()) {
			if (KeyEvent.KEY_PRESSED == e.getID()) {
				Container d = getTopParentWindow((Component) e.getSource());
				FmHelp fmHelp = null;
				if (d != null) {
					if (d instanceof Window) {
						fmHelp = FmHelp.getInstance((Window) d, d.getClass()
								.getName());
					} else {
						fmHelp = FmHelp.getInstance(FmMain.getInstance(), d
								.getClass().getName());
					}
				}
				fmHelp.setVisible(true);
			}
		}
		return true;
	}

	private Container getTopParentWindow(Component obj) {
		Container container = obj.getParent();
		if (container == null) {
			return null;
		}
		if (container instanceof Window || container instanceof JInternalFrame) {
			return container;
		} else {
			return getTopParentWindow(container);
		}
	}

}
