package com.bestway.client.windows.control;

import java.awt.Color;
import java.awt.Component;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class UIStyleControl {

	// 宝石兰 样式
	public final static int BORLAND_STYLE = 0;

	// Java 样式
	public final static int JAVA_STYLE = 1;

	// Windows 样式
	public final static int WINDOWS_STYLE = 2;

	private UIStyleControl() {
	}

	private static int style = 1;

	public static void setStyle(int no) {
		if (no < 0) {
			style = BORLAND_STYLE;
		} else if (no > 2) {
			style = WINDOWS_STYLE;
		} else {
			style = no;
		}
	}

	public static int getStyle() {
		return style;
	}

	public static void setCurrentUIStyle(Component component) {
		try {
			switch (style) {
			case BORLAND_STYLE:
				UIManager
						.setLookAndFeel("com.borland.plaf.borland.BorlandLookAndFeel");
				break;
			case JAVA_STYLE:

				UIManager
						.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				break;
			case WINDOWS_STYLE:
				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				break;
			}
			// UIManager.put("ToolTip.foreground",
			// new ColorUIResource(Color.red));
			UIManager.put("ToolTip.foreground",
					new ColorUIResource(Color.BLACK));
			UIManager.put("ToolTip.background", new ColorUIResource(
					Color.YELLOW));
			SwingUtilities.updateComponentTreeUI(component);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}