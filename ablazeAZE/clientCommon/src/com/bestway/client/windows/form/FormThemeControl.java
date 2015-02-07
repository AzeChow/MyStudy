package com.bestway.client.windows.form;

import java.awt.Component;
import java.awt.event.ItemEvent;

import javax.swing.JRadioButtonMenuItem;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.client.windows.control.UIStyleControl;
import com.bestway.client.windows.form.theme.AquaMetalTheme;
import com.bestway.client.windows.form.theme.GreenMetalTheme;
import com.bestway.client.windows.form.theme.KhakiMetalTheme;


public class FormThemeControl {
	private static Log	log	= LogFactory.getLog(FormThemeControl.class);

	private FormThemeControl() {
	}

//	public static void setStyle(ItemEvent e) {
//		if (e.getSource().getClass() != JRadioButtonMenuItem.class) {
//			log.error(e.getSource().getClass()
//					+ " is not JRadioButtonMenuItem!");
//			return;
//		}
//		JRadioButtonMenuItem jRadioButtonMenuItem = (JRadioButtonMenuItem) e
//				.getSource();
//		if (jRadioButtonMenuItem.getActionCommand().equals("borland")) {
//			UIStyleControl.setStyle(UIStyleControl.BORLAND_STYLE);
//		} else if (jRadioButtonMenuItem.getActionCommand().equals("java")) {
//			UIStyleControl.setStyle(UIStyleControl.JAVA_STYLE);
//		} else if (jRadioButtonMenuItem.getActionCommand().equals("windows")) {
//			UIStyleControl.setStyle(UIStyleControl.WINDOWS_STYLE);
//		}
//
//	}

	public static void setComponentStyle(Component comp) {
		if (comp != null) {
			UIStyleControl.setCurrentUIStyle(comp);
		}
	}

	public static final int	OCEAN_THEME		= 0;	// 海兰色
	public static final int	STEEL_THEME		= 1;	// 铁色
	public static final int	AQUA_THEME		= 2;	// 海蓝宝石
	public static final int	SANDSTONE_THEME	= 3;	// 沙岩色
	public static final int	EMERALD_THEME	= 4;	// 翠绿色

	// new OCEAN_THEME(),// 海兰色
	// new STEEL_THEME(),// 铁色
	// new GreenMetalTheme(),// 翠绿色
	// new AquaMetalTheme(), // 海蓝宝石
	// new KhakiMetalTheme(),// 沙岩色
	// new CharcoalMetalTheme()// 木炭色

	public static void setTheme(int theme) {
		MetalTheme settheme;
		switch (theme) {
		case OCEAN_THEME:
			settheme = new OceanTheme();
			break;
		case STEEL_THEME:
			settheme = new DefaultMetalTheme();
			break;
		case AQUA_THEME:
			settheme = new AquaMetalTheme();
			break;
		case SANDSTONE_THEME:
			settheme = new KhakiMetalTheme();
			break;
		case EMERALD_THEME:
			settheme = new GreenMetalTheme();
			break;		
		default:
			settheme = new OceanTheme();
		}
		MetalLookAndFeel.setCurrentTheme(settheme);

	}
}