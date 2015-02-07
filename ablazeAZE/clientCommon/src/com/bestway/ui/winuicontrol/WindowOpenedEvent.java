package com.bestway.ui.winuicontrol;

import java.awt.Component;

/**
 * 用于 client report plugin
 * */
public interface WindowOpenedEvent {
	//
	// component is JDialogBase or JInternalFrameBase 
	//
	void windowOpened(Component component);
}