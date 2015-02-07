package com.bestway.ui.winuicontrol;

import java.awt.Component;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import com.bestway.client.common.CommonVariables;

public class JInternalFrameBase extends JInternalFrame {
	private boolean isAddkeyListenered = false;

	private Border borderNormal = null;

	private JMDIMenuBar menu = null;

	private JNaviBar naviBar = null;

	private JFrame mainFrame = null;

	private String helpId = "";

	private String flowPaneClassName = null;

	private List componentFocusList = null;

	/**
	 * @return Returns the mainFrame.
	 */
	public JFrame getMainFrame() {
		return mainFrame;
	}

	public JInternalFrameBase() {
		super();
		setWindowClientReportPluginAdapter();
	}

	/**
	 * @param mainFrame
	 *            The mainFrame to set.
	 */
	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/**
	 * @return Returns the menu.
	 */
	public JMDIMenuBar getMenu() {
		return menu;
	}

	/**
	 * @param menu
	 *            The menu to set.
	 */
	public void setMenu(JMDIMenuBar menu) {
		this.menu = menu;
	}

	private boolean borderNone = false;

	public void setVisible(boolean b) {

		if (b) {

			bindRequestFocusListenser();
			// this.bindCopyPasteMouseListenser();

			if (!isAddkeyListenered) {

				KeyAdapterControl.AddListener(this.getContentPane());

				isAddkeyListenered = true;

			}
			this.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
				public void propertyChange(java.beans.PropertyChangeEvent evt) {
					formPropertyChange(evt);
				}
			});
			this.addInternalFrameListener(new InternalFrameAdapter() {

				public void internalFrameOpened(InternalFrameEvent e) {
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

	private void formPropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_formPropertyChange
		if (evt.getPropertyName().equals("maximum"))
			onMaximizeMinimize();
		else if (evt.getPropertyName().equals("selected"))
			onSelect();
	}

	private void onMaximizeMinimize() {
		if (borderNormal == null) {
			borderNormal = this.getBorder();
		}
		if (this.isMaximum()) {
			if (!borderNone) {
				try {
					this.setSelected(true);
				} catch (Exception ex) {
				}
				this.setBorder(new javax.swing.border.EmptyBorder(
						new java.awt.Insets(0, 0, 0, 0)));
				borderNone = true;
				((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI())
						.getNorthPane().setPreferredSize(
								new java.awt.Dimension(0, 0));
				// if (menu != null)
				// menu.setMaximizedFrame(this);
			}
		} else if (!this.isMaximum() && borderNone) {
			java.awt.Dimension normalSize = this.getSize();
			borderNone = false;
			((javax.swing.plaf.basic.BasicInternalFrameUI) getUI())
					.getNorthPane().setPreferredSize(
							new java.awt.Dimension(normalSize.width, 23));
			this.setPreferredSize(normalSize);
			this.setSize(normalSize);
			this.setBorder(borderNormal);
			this.setMaximizable(true);
			// if (menu != null)
			// menu.setMaximizedFrame(null);
			try {
				this.setSelected(true);
			} catch (Exception ex) {
			}
			;
		}
	}

	/**
	 * This method set the activeForm to the menu. It is useful when the user
	 * maximize windows and then close one. The frame that will activated will
	 * set activeForm in JMDIMenuBar class trought this method.
	 * 
	 */
	private void onSelect() {
		if (this.isMaximum()) {
			// if (menu != null)
			// menu.setMaximizedFrame(this);
		}
		if (naviBar != null) {
			naviBar.activeInternalFrame(this);
		}
		if (mainFrame != null)
			mainFrame.setTitle(CommonVariables.mainTitle + "  ---"
					+ this.getTitle());
	}

	public void dispose() {
		if (this.isClosed() == false) {
			this.doDefaultCloseAction();
		}
	}

	public void doDefaultCloseAction() {
		if (naviBar != null) {
			naviBar.removeInternalFrame(this);
		}
		super.doDefaultCloseAction();
	}

	public String getHelpId() {
		return helpId;
	}

	public void setHelpId(String helpId) {
		this.helpId = helpId;
	}

	private void setWindowClientReportPluginAdapter() {
		final WindowOpenedEvent windowClosedEvent = CommonVariables
				.getWindowOpenedEvent();
		if (windowClosedEvent != null) {
			this.addInternalFrameListener(new InternalFrameListener() {

				public void internalFrameActivated(InternalFrameEvent e) {
					//

				}

				public void internalFrameClosed(InternalFrameEvent e) {
					//

				}

				public void internalFrameClosing(InternalFrameEvent e) {
					//

				}

				public void internalFrameDeactivated(InternalFrameEvent e) {
					//

				}

				public void internalFrameDeiconified(InternalFrameEvent e) {
					//

				}

				public void internalFrameIconified(InternalFrameEvent e) {
					//

				}

				public void internalFrameOpened(InternalFrameEvent e) {
					JInternalFrame dialog = (JInternalFrame) e.getSource();
					windowClosedEvent.windowOpened(dialog);
				}

			});
		}

	}

	public JNaviBar getNaviBar() {
		return naviBar;
	}

	public void setNaviBar(JNaviBar naviBar) {
		this.naviBar = naviBar;
	}

	public String getFlowPaneClassName() {
		return flowPaneClassName;
	}

	public void setFlowPaneClassName(String navaPaneClassName) {
		this.flowPaneClassName = navaPaneClassName;
	}
}