package com.bestway.common.client.warning;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JWindow;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;
import com.bestway.common.client.warning.Warn.WarnLable;
import com.bestway.common.warning.action.WarningAction;

public class DgWarn extends JWindow {

	private static final long	serialVersionUID	= 1L;
	private JPanel				jContentPane		= null;
	private JLabel				jLabel				= null;
	private JPanel				jPanel				= null;
	private JLabel				jLabel1				= null;
	private JScrollPane			jScrollPane			= null;
	private JEditorPane			jEditorPane			= null;
	private String				text				= "";
	private boolean				isFirst				= true;
	private boolean				isClose				= false;
	private boolean				isMouseEntered		= false;
	private JLabel				jLabel2				= null;
	private String				key					= "";

	/**
	 * @param owner
	 */
	public DgWarn(Frame owner, String text) {
		super(owner);
		this.text = text;
		initialize();
		this.setMouseEvent(getJContentPane());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 225);
		this.setContentPane(getJContentPane());
	}

	/**
	 * 设置所有的RadioButton的属性和事件
	 * 
	 * @param component
	 */
	private void setMouseEvent(Component component) {
		if (!(component instanceof Container)) {
			return;
		}
		Container container = (Container) component;
		container.addMouseListener(new CMouseAdapter());
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component temp = container.getComponent(i);
			if (!(temp instanceof Container)) {
				temp.addMouseListener(new CMouseAdapter());
			} else {
				setMouseEvent(temp);
			}
		}
	}

	class CMouseAdapter extends MouseAdapter {
		public void mouseEntered(MouseEvent e) {
			isMouseEntered = true;
		}

		public void mouseExited(MouseEvent e) {
			isMouseEntered = false;
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBorder(javax.swing.BorderFactory.createLineBorder(
					java.awt.Color.gray, 1));
			jContentPane.setBackground(new Color(238, 238, 238));
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJScrollPane(), null);
		}
		return jContentPane;
	}

	public void setVisible(boolean b) {
		if (b) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = this.getSize();
			if (frameSize.height > screenSize.height) {
				frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
				frameSize.width = screenSize.width;
			}
			this.setLocation((screenSize.width - frameSize.width - 18),
					screenSize.height);
		}
		super.setVisible(b);
	}

	public void setInitVisible(boolean b) {
		if (b) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = this.getSize();
			if (frameSize.height > screenSize.height) {
				frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
				frameSize.width = screenSize.width;
			}
			this.setLocation((screenSize.width - frameSize.width -18),
					screenSize.height - frameSize.height);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new JLabel();
			jLabel.setText("JBCUS 预警信息");
			jLabel.setBounds(new Rectangle(1, 0, 258, 23));
			jLabel.setForeground(Color.white);
			jLabel.setIcon(CommonVars.getIconSource().getIcon(
					"smallwarning.gif"));
		}
		return jLabel;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(278, 1, 19, 21));
			jLabel1.setIcon(CommonVars.getIconSource().getIcon(
					"closewindow.gif"));
			jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (isFirst) {
						isClose = true;
						setVisible(false);
					} else {
						//
						// close
						//
						dispose();
						//
						// remove 预警信息
						//
						
						WarningAction warningAction = (WarningAction) CommonVars
								.getApplicationContext().getBean("warningAction");
						warningAction.removeWarningByKey(new Request(CommonVars
								.getCurrUser()), key);
						//
						// remove
						//
						JPanel pnWarning = FmMain.getInstance().getPnWarning();
						int count = pnWarning.getComponentCount();
						for (int i = count - 1; i >= 0; i--) {
							Component c = pnWarning.getComponent(i);
							if (c instanceof WarnLable) {
								String tempKey = ((WarnLable) c).getKey();
								if (key.equals(tempKey)) {
									pnWarning.remove(c);
									pnWarning.repaint();
									break;
								}
							}
						}

					}
				}
			});
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(2, 2, 296, 22));
			jPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel.setBackground(new Color(102, 102, 102));
			jPanel.add(getJLabel(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJLabel2(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(2, 27, 296, 172));
			jScrollPane
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jScrollPane.setViewportView(getJEditorPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jEditorPane
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			jEditorPane = new JEditorPane();
			jEditorPane.setEditable(false);
			jEditorPane.setBackground(new Color(238, 238, 238));
			jEditorPane.setText(text);
		}
		return jEditorPane;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isClose() {
		return isClose;
	}

	public boolean isMouseEntered() {
		return isMouseEntered;
	}

	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("");
			jLabel2.setIcon(CommonVars.getIconSource().getIcon("minimize.gif"));
			jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (isFirst) {
						setVisible(false);
					} else {
						dispose();
					}
				}
			});
			jLabel2.setBounds(new Rectangle(265, 6, 13, 16));
		}
		return jLabel2;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
