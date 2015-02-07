package com.bestway.ui.winuicontrol;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import com.bestway.client.common.CommonVariables;
import com.bestway.client.windows.form.FmMain;

public class JDialogBase extends JDialog {

	private boolean isAddkeyListenered = false;

	private String helpId = "";

	private List componentFocusList = null;

	public JDialogBase() {

		super(FmMain.getInstance().getLastOpenedWindow() == null ? FmMain
				.getInstance() : FmMain.getInstance().getLastOpenedWindow());

		setModal(true);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		setWindowClientReportPluginAdapter();

		addWindowListener();
	}

	private void addWindowListener() {
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				FmMain.getInstance().addOpenedWindow((Window) e.getSource());
			}

			public void windowClosing(java.awt.event.WindowEvent e) {
				FmMain.getInstance().removeOpenedWindow((Window) e.getSource());
			}
		});
	}

	public JDialogBase(boolean isModal) {

		super(FmMain.getInstance().getLastOpenedWindow() == null ? FmMain
				.getInstance() : FmMain.getInstance().getLastOpenedWindow());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		setModal(isModal);

		setWindowClientReportPluginAdapter();

		addWindowListener();
	}

	public JDialogBase(JFrame owner, boolean isModal) {

		super(owner);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		setModal(isModal);

		setWindowClientReportPluginAdapter();

		addWindowListener();
	}

	public JDialogBase(JInternalFrame owner, boolean isModal) {
		super(FmMain.getInstance());
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setModal(isModal);
		this.setWindowClientReportPluginAdapter();
		this.addWindowListener();
	}

	public JDialogBase(Dialog owner, boolean isModal) {
		super(owner, isModal);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setWindowClientReportPluginAdapter();
		this.addWindowListener();
	}

	/** 客户端自定义报表插件,在 windowOpened 中载入 */
	private void setWindowClientReportPluginAdapter() {
		final WindowOpenedEvent windowClosedEvent = CommonVariables
				.getWindowOpenedEvent();
		if (windowClosedEvent != null) {
			this.addWindowListener(new WindowAdapter() {
				/**
				 * Invoked when a window has been closed.
				 */
				public void windowOpened(WindowEvent e) {
					JDialog dialog = (JDialog) e.getSource();
					windowClosedEvent.windowOpened(dialog);
				}
			});
		}
	}

	public void dispose() {
		FmMain.getInstance().removeOpenedWindow((Window) this);
		super.dispose();
	}

	public String getHelpId() {
		return helpId;
	}

	public void setHelpId(String helpId) {
		this.helpId = helpId;
	}

	/**
	 * 设置组件是否显示
	 * 
	 * @param b
	 */
	public void setVisibleNoChange(boolean b) {
		super.setVisible(b);
	}

	public void setVisible(boolean b) {
		if (b == true) {
			bindRequestFocusListenser();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = this.getSize();
			if (frameSize.height > screenSize.height) {
				frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
				frameSize.width = screenSize.width;
			}
			this.setLocation((screenSize.width - frameSize.width) / 2,
					(screenSize.height - frameSize.height) / 2);
			setVisibleAfter();
			this.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowOpened(java.awt.event.WindowEvent e) {
					firstComponectRequestFocus();
				}
			});
		}
		super.setVisible(b);
	}

	public void setVisible(boolean b, int topHeight) {
		if (b == true) {
			bindRequestFocusListenser();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = this.getSize();
			if (frameSize.height > screenSize.height) {
				frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
				frameSize.width = screenSize.width;
			}
			this.setLocation((screenSize.width - frameSize.width) / 2,
					(screenSize.height - frameSize.height) / 2 - topHeight);
			this.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowOpened(java.awt.event.WindowEvent e) {
					firstComponectRequestFocus();
				}
			});
		}
		super.setVisible(b);
	}

	private void bindRequestFocusListenser() {
		if (!isAddkeyListenered) {
			if (componentFocusList == null || componentFocusList.size() <= 1) {
				KeyAdapterControl.AddListener(this.getContentPane());
			} else {
				KeyAdapterControl.AddListener(this.getContentPane(),
						componentFocusList);
			}
			isAddkeyListenered = true;
		}
	}

	public void setVisible(boolean b, Point point) {
		if (b == true) {
			bindRequestFocusListenser();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = this.getSize();
			if (frameSize.height > screenSize.height) {
				frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
				frameSize.width = screenSize.width;
			}
			this.setLocation(point);
			this.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowOpened(java.awt.event.WindowEvent e) {
					firstComponectRequestFocus();
				}
			});
		}
		super.setVisible(b);
	}

	public void setVisibleAfter() {

	}

	public List getComponentFocusList() {
		return componentFocusList;
	}

	public void setComponentFocusList(List componentFocusList) {
		this.componentFocusList = componentFocusList;
	}

	private void firstComponectRequestFocus() {
		if (componentFocusList != null && componentFocusList.size() > 0) {
			Component component = KeyAdapterControl.getRequestFocusComponent(
					componentFocusList, 0);
			if (component != null) {
				component.requestFocus();
			}
		}
	}

}