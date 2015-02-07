package com.bestway.client.windows.control;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.io.Serializable;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;

import com.bestway.client.windows.form.FmMain;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.JMDIMenuBar;
import com.bestway.ui.winuicontrol.JNaviBar;

public class ShowFormControl implements Serializable {

	private ShowFormControl() {

	}

	public static JFrame showForm(JFrame form) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = form.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		form.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		form.setVisible(true);
		return form;
	}

	public static JFrame showForm(String formstr) {
		Class cls = null;
		JFrame form = null;
		try {
			cls = Class.forName(formstr);
			form = (JFrame) cls.newInstance();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return showForm(form);
	}

	public static JInternalFrame reshowForm(JInternalFrame form,
			JDesktopPane desktop) {
		desktop.getDesktopManager().activateFrame(form);
		form.pack();
		try {
			form.setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		desktop.getDesktopManager().activateFrame(form);
		form.pack();
		try {
			form.setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		return form;
	}

	public static void refreshInteralForm(JInternalFrame targetForm) {

		JDesktopPane deskPanel = FmMain.getInstance().getDeskPanel();

		int iCount = deskPanel.getComponentCount();

		for (int i = 0; i < iCount; i++) {
			if (deskPanel.getComponent(i) instanceof JInternalFrame) {
				if (targetForm.getClass().equals(
						deskPanel.getComponent(i).getClass())) {
					JInternalFrame form = (JInternalFrame) deskPanel
							.getComponent(i);
					form.doDefaultCloseAction();
					FmMain.getInstance().refreshNaviBar();
					break;
				}
			} else if (deskPanel.getComponent(i) instanceof JInternalFrame.JDesktopIcon) {
				if (targetForm
						.getClass()
						.equals(((JInternalFrame.JDesktopIcon) deskPanel
								.getComponent(i)).getInternalFrame().getClass())) {
					JInternalFrame form = (JInternalFrame) ((JInternalFrame.JDesktopIcon) deskPanel
							.getComponent(i)).getInternalFrame();
					form.doDefaultCloseAction();
					FmMain.getInstance().refreshNaviBar();
					break;
				}
			}
		}
		showForm(targetForm);
	}

	public static void showForm(JInternalFrame form) {
		JDesktopPane desktop = FmMain.getInstance().getDeskPanel();
		if (checkFormIsExist(desktop, form)) {
			return;
		}
		JNaviBar naviBar = FmMain.getInstance().getNaviBar();
		JMenuBar menuBar = FmMain.getInstance().getJMenuBar();
		showForm(form, desktop, menuBar, naviBar);
	}

	private static boolean checkFormIsExist(JDesktopPane deskPanel,
			JInternalFrame form1) {
		String targetForm = form1.getClass().getName();
		int iCount = deskPanel.getComponentCount();
		for (int i = 0; i < iCount; i++) {
			if (deskPanel.getComponent(i) instanceof JInternalFrame) {
				if (targetForm.equals(deskPanel.getComponent(i).getClass()
						.getName())
						|| targetForm.equals(((JInternalFrameBase) deskPanel
								.getComponent(i)).getFlowPaneClassName())) {
					form1.doDefaultCloseAction();
					JInternalFrame form = (JInternalFrame) deskPanel
							.getComponent(i);
					reshowForm(form, deskPanel);
					return true;
				}
			} else if (deskPanel.getComponent(i) instanceof JInternalFrame.JDesktopIcon) {
				if (targetForm.equals(((JInternalFrame.JDesktopIcon) deskPanel
						.getComponent(i)).getInternalFrame().getClass()
						.getName())) {
					form1.doDefaultCloseAction();
					JInternalFrame form = (JInternalFrame) ((JInternalFrame.JDesktopIcon) deskPanel
							.getComponent(i)).getInternalFrame();
					deskPanel.getDesktopManager().deiconifyFrame(form);
					reshowForm(form, deskPanel);
					return true;
				}
			}
		}
		return false;
	}

	public static JInternalFrame showForm(JInternalFrame form,
			JDesktopPane desktop, JMenuBar menuBar, JNaviBar naviBar) {
		((JInternalFrameBase) form).setMenu((JMDIMenuBar) menuBar);
		((JInternalFrameBase) form).setNaviBar((JNaviBar) naviBar);
		((JInternalFrameBase) form).setMainFrame(FmMain.getInstance());
		form.setClosable(true);
		form.setIconifiable(true);
		form.setResizable(true);
		try {
			desktop.add(form);
			if (form != null) {
				try {
					form.setMaximum(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
					form.doDefaultCloseAction();
					FmMain.getInstance().refreshNaviBar();
				}
				form.setVisible(true);
				form.moveToFront();
			}
		} catch (RuntimeException e) {
			desktop.remove(form);
			form.doDefaultCloseAction();
			FmMain.getInstance().refreshNaviBar();
			throw (e);
		}
		return form;
	}

	public static JInternalFrame showForm(String formstr, JDesktopPane desktop,
			JMenuBar menuBar, JNaviBar naviBar) {
		Class cls = null;
		JInternalFrame form = null;
		try {
			cls = Class.forName(formstr);
			form = (JInternalFrame) cls.newInstance();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return showForm(form, desktop, menuBar, naviBar);
	}
}