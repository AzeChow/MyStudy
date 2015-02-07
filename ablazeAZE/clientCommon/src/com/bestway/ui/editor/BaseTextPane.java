package com.bestway.ui.editor;

import java.awt.Font;

import javax.swing.JTextPane;

public class BaseTextPane extends JTextPane {

	public BaseTextPane() {
		super();
		setFont(new Font("Courier New", Font.PLAIN, 12));
	}
	
}
