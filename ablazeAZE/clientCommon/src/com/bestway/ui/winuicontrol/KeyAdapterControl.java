package com.bestway.ui.winuicontrol;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextPane;

public class KeyAdapterControl {

	public static class KeyAdapterExtend extends KeyAdapter {
		private Component component;

		public KeyAdapterExtend(Component component) {
			this.component = component;
		}

		public void keyPressed(KeyEvent e) {
			if (!KeyAdapterControl.isAddListener) {
				if (e.getKeyCode() == 10) {// Enter
					if (e.getSource().getClass().getName().toLowerCase()
							.indexOf("button") >= 0) {
						component.dispatchEvent(new KeyEvent((Container) e
								.getSource(), KeyEvent.KEY_PRESSED, 0, 0,
								KeyEvent.VK_SPACE, e.getKeyChar()));
						component.dispatchEvent(new KeyEvent((Container) e
								.getSource(), KeyEvent.KEY_RELEASED, 0, 0,
								KeyEvent.VK_SPACE, e.getKeyChar()));
						component.dispatchEvent(new KeyEvent((Container) e
								.getSource(), KeyEvent.KEY_PRESSED, 0, 0,
								KeyEvent.VK_TAB, e.getKeyChar()));
					} else {
						component.dispatchEvent(new KeyEvent((Container) e
								.getSource(), KeyEvent.KEY_PRESSED, 0, 0,
								KeyEvent.VK_TAB, e.getKeyChar()));
					}
				}
				// else{
				// if(e.getModifiers()==KeyEvent.CTRL_MASK){
				// component.dispatchEvent(new KeyEvent((Container) e
				// .getSource(), KeyEvent.KEY_PRESSED, 0, KeyEvent.ALT_MASK,
				// e.getKeyCode(), e.getKeyChar()));
				// component.dispatchEvent(new KeyEvent((Container) e
				// .getSource(), KeyEvent.KEY_RELEASED, 0, KeyEvent.ALT_MASK,
				// e.getKeyCode(), e.getKeyChar()));
				// }
				// }
			}
			KeyAdapterControl.setAddListener(false);
		}
	}

	public static boolean isAddListener = false;

	public static void AddListener(Container container) {
		for (int i = 0; i < container.getComponentCount(); i++) {
			if (!(container.getComponent(i) instanceof Container)) {
				continue;
			}
			if (container.getComponent(i) instanceof JTable
					|| container.getComponent(i) instanceof JTextPane) {
				continue;
			}
			if (container.getComponent(i) instanceof JButton) {
				addMnemonic((JButton) container.getComponent(i));
			}
			container.getComponent(i).addKeyListener(
					new KeyAdapterExtend(container.getComponent(i)));
			AddListener((Container) container.getComponent(i));
		}
	}

	private static void addMnemonic(JButton button) {
		String caption = button.getText().trim();
		// System.out.println(java.awt.event.KeyEvent.VK_P+"--"+java.awt.event.KeyEvent.getKeyText(java.awt.event.KeyEvent.VK_P));
		if (caption.equals("新增") || caption.equals("增加")) {
			button.setMnemonic(java.awt.event.KeyEvent.VK_N);
			button.setToolTipText("快捷方式：ALT+N");// ：ALT+N
			return;
		}
		if (caption.equals("删除"))   {
			button.setMnemonic(java.awt.event.KeyEvent.VK_D);
			button.setToolTipText("快捷方式：ALT+D");// ALT+D
			return;
		}
		if (caption.equals("修改")) {
			button.setMnemonic(java.awt.event.KeyEvent.VK_E);
			button.setToolTipText("快捷方式：ALT+E");// ALT+E
			return;
		}
		if (caption.equals("保存") || caption.equals("确定")) {
			button.setMnemonic(java.awt.event.KeyEvent.VK_S);
			button.setToolTipText("快捷方式：ALT+S");// ALT+S
			return;
		}
		if (caption.equals("查询")) {
			button.setMnemonic(java.awt.event.KeyEvent.VK_F);
			button.setToolTipText("快捷方式：ALT+F");// ALT+F
			return;
		}
		if (caption.equals("刷新")) {
			button.setMnemonic(java.awt.event.KeyEvent.VK_R);
			button.setToolTipText("快捷方式：ALT+R");// ALT+R
			return;
		}
		if (caption.equals("打印")) {
			button.setMnemonic(java.awt.event.KeyEvent.VK_P);
			button.setToolTipText("快捷方式：ALT+P");// ALT+P
			return;
		}
		if (caption.equals("退出") || caption.equals("关闭")
				|| caption.equals("取消")) {
			button.setMnemonic(java.awt.event.KeyEvent.VK_X);
			button.setToolTipText("快捷方式：ALT+X");// ALT+X
			return;
		}
		if (caption.equals("报关申报") || caption.equals("海关申报")) {
			button.setMnemonic(java.awt.event.KeyEvent.VK_A);
			button.setToolTipText("快捷方式：ALT+A");// ALT+A
			return;
		}
		if (caption.equals("回执处理") || caption.equals("处理回执")) {
			button.setMnemonic(java.awt.event.KeyEvent.VK_P);
			button.setToolTipText("快捷方式：ALT+P");// ALT+P
			return;
		}
		if (caption.equals("转抄") || caption.equals("拷贝")) {
			button.setMnemonic(java.awt.event.KeyEvent.VK_C);
			button.setToolTipText("快捷方式：ALT+C");// ALT+C
			return;
		}
	}

	public static void AddListener(Container container, final List list) {
		AddListener(container);
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				final Component currComponent = getRequestFocusComponent(list,
						i);
				final int j = 0;
				if (currComponent != null) {
					currComponent.addKeyListener(new KeyAdapter() {
						public void keyPressed(KeyEvent e) {
							if (!KeyAdapterControl.isAddListener) {
								if (e.getKeyCode() == 10) {// Enter
									Component nextComponent = getNextRequestFocusComponent(
											list, j);
									if (nextComponent != null) {
										nextComponent.requestFocus();
									}
								}
							}
							KeyAdapterControl.setAddListener(false);
						}
					});
				}
			} else {
				final Component currComponent = getRequestFocusComponent(list,
						i);
				final int j = i + 1;
				if (currComponent != null) {
					currComponent.addKeyListener(new KeyAdapter() {
						public void keyPressed(KeyEvent e) {
							if (!KeyAdapterControl.isAddListener) {
								if (e.getKeyCode() == 10) {// Enter
									Component nextComponent = getNextRequestFocusComponent(
											list, j);
									if (nextComponent != null) {
										nextComponent.requestFocus();
									} 
								}
							}
							KeyAdapterControl.setAddListener(false);
						}
					});
				}
			}
		}
	}

	public static Component getRequestFocusComponent(List list, int index) {
		if (list.get(index) == null) {
			return null;
		}
		if (list.get(index) instanceof Component[]) {
			Component[] components = (Component[]) list.get(index);
			for (int j = 0; j < components.length; j++) {
				return components[j];
			}
		} else {
			Component component = (Component) list.get(index);
			return component;
		}
		return null;
	}

	private static Component getNextRequestFocusComponent(List list, int index) {
		if (index < 0 || index > list.size() - 1 || list.get(index) == null) {
			return null;
		}
		if (list.get(index) instanceof Component[]) {
			Component[] components = (Component[]) list.get(index);
			for (int j = 0; j < components.length; j++) {
				if (components[j] != null && components[j].isEnabled()) {
					return components[j];
				}
			}
		} else {
			Component component = (Component) list.get(index);
			if (component != null && component.isEnabled()) {
				return component;
			}
			component.transferFocus();
		}
		return null;
	}

	/**
	 * @param isAddListener
	 *            The isAddListener to set.
	 */
	public static void setAddListener(boolean isAddListener) {
		KeyAdapterControl.isAddListener = isAddListener;
	}
}
