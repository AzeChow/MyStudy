package com.bestway.ui.winuicontrol;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

/**
 * Similar to a javax.swing.JMenuBar, this menu bar is able to handle close,
 * minimize e AltMaximize buttons for JMDIFrame contained in a JMDIDesktopPane.
 * 
 * @author Administrator
 */
public class JMDIMenuBar extends javax.swing.JMenuBar implements
		java.awt.event.ComponentListener, java.awt.event.ActionListener {

//	JButton closeButton = null;
//
//	JButton resizeButton = null;
//
//	JButton iconifyButton = null;

	JInternalFrame activeFrame = null;

	boolean isMaximized = false;

	int closeCount = 0;

	/** Creates a new instance of JMDIMenuBar */
	public JMDIMenuBar() {
//		closeButton = new javax.swing.JButton(
//				javax.swing.plaf.metal.MetalIconFactory
//						.getInternalFrameCloseIcon(16));
//		closeButton.setBorder(new javax.swing.border.EmptyBorder(
//				new java.awt.Insets(0, 0, 0, 0)));
//		resizeButton = new javax.swing.JButton(
//				javax.swing.plaf.metal.MetalIconFactory
//						.getInternalFrameAltMaximizeIcon(16));
//		resizeButton.setBorder(new javax.swing.border.EmptyBorder(
//				new java.awt.Insets(0, 0, 0, 0)));
//		iconifyButton = new javax.swing.JButton(
//				javax.swing.plaf.metal.MetalIconFactory
//						.getInternalFrameMinimizeIcon(16));
//		iconifyButton.setBorder(new javax.swing.border.EmptyBorder(
//				new java.awt.Insets(0, 0, 0, 0)));

		this.addComponentListener(this);
	}

	public void componentHidden(java.awt.event.ComponentEvent e) {
	}

	public void componentMoved(java.awt.event.ComponentEvent e) {
	}

	public void componentResized(java.awt.event.ComponentEvent e) {
//		if (this.getParent() != null && closeButton != null)
//			closeButton.setBounds(this.getParent().getWidth() - 16 - 2, (this
//					.getHeight() - 16) / 2, 16, 16);
//		if (this.getParent() != null && closeButton != null)
//			resizeButton.setBounds(this.getParent().getWidth() - 32 - 6, (this
//					.getHeight() - 16) / 2, 16, 16);
//		if (this.getParent() != null && closeButton != null)
//			iconifyButton.setBounds(this.getParent().getWidth() - 48 - 6, (this
//					.getHeight() - 16) / 2, 16, 16);
//		addActionListenerToButton(resizeButton);
//		addActionListenerToButton(iconifyButton);
//		addActionListenerToButton(closeButton);
	}
	
	private void addActionListenerToButton(JButton button){
		ActionListener[] actionListeners=button.getActionListeners();
		int count=actionListeners.length;
		for(int i=count-1;i>=0;i--){
			if(actionListeners[i] instanceof JMDIMenuBar){
				button.removeActionListener(actionListeners[i]);
			}
		}
		button.addActionListener(this);
	}
	
	public void refreshMenuBar(){
//		this.add(iconifyButton);
//		this.add(resizeButton);
//		this.add(closeButton);
		this.restoreButtonsPosition();
		this.update(this.getGraphics());
		this.removeFrameButtons();
	}
	
	public void removeFrameButtons() {
		isMaximized = false;
		activeFrame = null;
//		closeButton.removeActionListener(this);
//		resizeButton.removeActionListener(this);
//		iconifyButton.removeActionListener(this);
//		if (this.getParent() != null && closeButton != null)
//			closeButton.setBounds(-16 - 2, -16 - 2, 16, 16);
//		if (this.getParent() != null && closeButton != null)
//			resizeButton.setBounds(-32 - 6, -16 - 2, 16, 16);
//		if (this.getParent() != null && closeButton != null)
//			iconifyButton.setBounds(-48 - 6, -16 - 2, 16, 16);
		// this.remove( closeButton);
		// this.remove( resizeButton);
		// this.remove( iconifyButton);
		// this.update(this.getGraphics());
	}

//	public void setMaximizedFrame(JInternalFrame jf) {
//		if (jf == null) {
//			removeFrameButtons();
//		} else {
//
//			if (!isMaximized) {
////				resizeButton.addActionListener(this);
////				iconifyButton.addActionListener(this);
////				closeButton.addActionListener(this);
////				this.add(iconifyButton);
////				this.add(resizeButton);
////				this.add(closeButton);
//				this.restoreButtonsPosition();
//				this.update(this.getGraphics());
//			}
//			activeFrame = jf;
//			isMaximized = true;
//		}
//	}

	public JInternalFrame getMaximizedFrame() {
		return this.activeFrame;
	}

	public void componentShown(java.awt.event.ComponentEvent e) {
	}

	/**
	 * Paints this component.
	 * <p>
	 * This method is called when the contents of the component should be
	 * painted in response to the component first being shown or damage needing
	 * repair. The clip rectangle in the <code>Graphics</code> parameter will
	 * be set to the area which needs to be painted. Subclasses of Component
	 * that override this method need not call super.paint(g).
	 * <p>
	 * For performance reasons, Components with zero width or height aren't
	 * considered to need painting when they are first shown, and also aren't
	 * considered to need repair.
	 * 
	 * @param g
	 *            the graphics context to use for painting
	 * @see #update
	 * @since JDK1.0
	 * 
	 */
	public void paint(java.awt.Graphics g) {
		int count = 0, internalFrameCount = 0;
		if (activeFrame != null && activeFrame.getDesktopPane() != null) {
			count = activeFrame.getDesktopPane().getComponentCount();
			Object next;
			for (int i = 0; i < count; i++) {
				next = activeFrame.getDesktopPane().getComponent(i);
				if (next instanceof JInternalFrame) {
					internalFrameCount++;
				}
			}
		}
		if (internalFrameCount > 0) {
			componentResized(null);
		} else {
			this.removeFrameButtons();
		}
		super.paint(g);
	}

	/**
	 * Invoked when an action occurs. This method handle the
	 * minimize,alt-maximize and close buttons for frames maximized in
	 * JMDIDestopPane
	 */
	public void actionPerformed(java.awt.event.ActionEvent e) {
//
//		// The simpler case...
//		closeCount++;
//		if (e.getSource() == resizeButton) {
//			if (this.activeFrame != null) {
//				try {
//					activeFrame.setMaximum(false);
//					activeFrame.getDesktopPane().getDesktopManager()
//							.activateFrame(activeFrame);
//				} catch (Exception ex) {
//				}
//			}
//		}
//		// A bit more complex...
//		else if (e.getSource() == iconifyButton) {
//			if (this.activeFrame != null) {
//				try {
//					// activeFrame.setMaximum( false);
//					activeFrame.setIcon(true);
//				} catch (Exception ex) {
//				}
//			}
//		}
//		// the camplex case....the user want close this form!
//		// What we have to do?
//		//
//		else if (e.getSource() == closeButton) {
//			if (this.activeFrame != null) {
//				try {
//
//					System.out.println(closeCount + " "
//							+ activeFrame.getTitle());
//					System.out.flush();
//
//					javax.swing.JInternalFrame f = activeFrame;
//					int frames = activeFrame.getDesktopPane().getAllFrames().length;
//					f.doDefaultCloseAction();
//					if (f.isClosed() && frames <= 1) {
//						removeFrameButtons();
//					}
//
//					// activeFrame.setMaximum( false);
//					// activeFrame.setIcon(true);
//				} catch (Exception ex) {
//				}
//			}
//		}
	}

	public void restoreButtonsPosition() {
		componentResized(null);
	}

}
