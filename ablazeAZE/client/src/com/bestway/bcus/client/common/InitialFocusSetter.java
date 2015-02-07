package com.bestway.bcus.client.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.bestway.client.common.CommonVariables;

/**
 * Setting the Initial Focused Component in a Window
 * 
 * code example by :
 * http://www.javaalmanac.com/egs/java.awt/focus_InitialFocus.html?l=rel
 * 
 * @author root
 * 
 */
public class InitialFocusSetter {

	public static void setInitialFocus(Window w, Component c) {
		w.addWindowListener(new FocusSetter(c));
	}

	public static void setInitialFocus(Component c) {
		Component container = CommonVariables.getFormWhereComponentIsIn(c);
		if (container != null) {
			if (container instanceof JInternalFrame) {
				JInternalFrame internalFrame = (JInternalFrame) container;
				internalFrame
						.addInternalFrameListener(new JInternalFrameFocusSetter(
								c));
			} else if (container instanceof JDialog
					|| container instanceof JFrame) {
				Window w = (Window) container;
				w.addWindowListener(new FocusSetter(c));
			}
		}

	}

	public static class JInternalFrameFocusSetter extends InternalFrameAdapter {
		Component initComp;

		JInternalFrameFocusSetter(Component c) {
			initComp = c;
		}

		public void internalFrameOpened(InternalFrameEvent e) {
			initComp.requestFocus();
			// Since this listener is no longer needed, remove it
			e.getInternalFrame().removeInternalFrameListener(this);
		}
	}

	public static class FocusSetter extends WindowAdapter {
		Component initComp;

		FocusSetter(Component c) {
			initComp = c;
		}

		public void windowOpened(WindowEvent e) {
			initComp.requestFocus();

			// Since this listener is no longer needed, remove it
			e.getWindow().removeWindowListener(this);
		}
	}

	/** 设置焦点 policy */
	public static void setFocusTraversalPolicy(Component[] components) {
		if (components == null || components.length < 0) {
			return;
		}
		MyOwnFocusTraversalPolicy policy = new MyOwnFocusTraversalPolicy(
				components);
		Component c = components[0];
		Component container = CommonVariables.getFormWhereComponentIsIn(c);
		if (container != null) {
			if (container instanceof JInternalFrame) {
				JInternalFrame internalFrame = (JInternalFrame) container;
				internalFrame.setFocusTraversalPolicy(policy);
			} else if (container instanceof JDialog
					|| container instanceof JFrame) {
				Window w = (Window) container;
				w.setFocusTraversalPolicy(policy);
			}
		}
	}

	public static class MyOwnFocusTraversalPolicy extends FocusTraversalPolicy {
		Component[] components = null;

		public MyOwnFocusTraversalPolicy(Component[] components) {
			this.components = components;
		}

		public Component getComponentAfter(Container focusCycleRoot,
				Component aComponent) {

			for (int i = 0; i < components.length; i++) {
				if (i == components.length - 1) {
					return components[0];
				}
				if (aComponent.equals(components[i])) {
					return components[i + 1];
				}
			}
			return components[0];
		}

		public Component getComponentBefore(Container focusCycleRoot,
				Component aComponent) {
			for (int i = components.length - 1; i >= 0; i--) {
				if (i == 0) {
					return components[components.length - 1];
				}
				if (aComponent.equals(components[i])) {
					return components[i - 1];
				}
			}
			return components[components.length - 1];
		}

		public Component getDefaultComponent(Container focusCycleRoot) {
			return components[0];
		}

		public Component getLastComponent(Container focusCycleRoot) {
			return components[components.length - 1];
		}

		public Component getFirstComponent(Container focusCycleRoot) {
			return components[0];
		}
	}

}
