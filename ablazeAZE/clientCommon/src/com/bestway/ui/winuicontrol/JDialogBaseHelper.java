package com.bestway.ui.winuicontrol;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class JDialogBaseHelper {
	private static Map<Object, JDialogBase> storeDialgMap = new HashMap<Object, JDialogBase>();

	public static void putJDialogBaseToFlag(final String flag,
			JDialogBase dialog) {
		if (storeDialgMap.get(flag) != null) {

			return;
		}
		storeDialgMap.put(flag, dialog);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				storeDialgMap.remove(flag);
				// System.out.println("storeDialogMap is close");
			}
		});
	}

	public static JDialogBase getJDialogBaseByFlag(String flag) {
		return storeDialgMap.get(flag);
	}

}
