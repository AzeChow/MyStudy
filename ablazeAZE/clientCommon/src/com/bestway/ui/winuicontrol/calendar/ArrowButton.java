/*
 * @(#)BasicArrowButton.java    1.26 03/12/19
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.bestway.ui.winuicontrol.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.FocusListener;

import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * JButton object that draws a scaled Arrow in one of the cardinal directions.
 * <p>
 * <strong>Warning:</strong> Serialized objects of this class will not be
 * compatible with future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Swing. As of 1.4, support for long term storage of all
 * JavaBeans<sup><font size="-2">TM</font></sup> has been added to the
 * <code>java.beans</code> package. Please see {@link java.beans.XMLEncoder}.
 * 
 * @version 1.26 12/19/03
 * @author David Kloba
 */
public class ArrowButton extends JButton implements SwingConstants {
	protected int direction;

	private Color shadow;

	private Color darkShadow;

	private Color highlight;

	public ArrowButton(int direction, Color shadow, Color darkShadow,
			Color highlight) {
		super();
		setRequestFocusEnabled(false);
		setDirection(direction);
		this.shadow = shadow;
		this.darkShadow = darkShadow;
		this.highlight = highlight;

	}

	public ArrowButton(int direction) {
		this(direction, UIManager.getColor("controlShadow"), UIManager
				.getColor("controlDkShadow"), UIManager
				.getColor("controlLtHighlight"));
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int dir) {
		direction = dir;
	}

	public Dimension getPreferredSize() {
		return new Dimension(20, 20);
	}

	public Dimension getMinimumSize() {
		return new Dimension(5, 5);
	}

	public Dimension getMaximumSize() {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public boolean isFocusTraversable() {
		return false;
	}

	public void paintTriangle(Graphics g, int x, int y, int size,
			int direction, boolean isEnabled) {
		Color oldColor = g.getColor();
		int mid, i, j;
		j = 0;
		size = Math.max(size, 2);
		mid = (size / 2) - 1;

		g.translate(x, y);
		if (isEnabled) {
			g.setColor(darkShadow);
		} else {
			g.setColor(shadow);
		}
		switch (direction) {
		case NORTH:
			for (i = 0; i < size; i++) {
				g.drawLine(mid - i, i, mid + i, i);
			}
			if (!isEnabled) {
				g.setColor(highlight);
				g.drawLine(mid - i + 2, i, mid + i, i);
			}
			break;
		case SOUTH:
			if (!isEnabled) {
				g.translate(1, 1);
				g.setColor(highlight);
				for (i = size - 1; i >= 0; i--) {
					g.drawLine(mid - i, j, mid + i, j);
					j++;
				}
				g.translate(-1, -1);
				g.setColor(shadow);
			}

			j = 0;
			for (i = size - 1; i >= 0; i--) {
				g.drawLine(mid - i, j, mid + i, j);
				j++;
			}
			break;
		case WEST:
			for (i = 0; i < size; i++) {
				g.drawLine(i, mid - i, i, mid + i);
			}
			if (!isEnabled) {
				g.setColor(highlight);
				g.drawLine(i, mid - i + 2, i, mid + i);
			}
			break;
		case EAST:
			if (!isEnabled) {
				g.translate(1, 1);
				g.setColor(highlight);
				for (i = size - 1; i >= 0; i--) {
					g.drawLine(j, mid - i, j, mid + i);
					j++;
				}
				g.translate(-1, -1);
				g.setColor(shadow);
			}

			j = 0;
			for (i = size - 1; i >= 0; i--) {
				g.drawLine(j, mid - i, j, mid + i);
				j++;
			}
			break;
		}
		g.translate(-x, -y);
		g.setColor(oldColor);
	}

	public void paintComponent(Graphics g) {
		// Paint the button as usual
		super.paintComponent(g);
		int size;
		int w = getSize().width;
		int h = getSize().height;
		if (w <= 0 || h <= 0) {
			return;
		}

		size = Math.min((h - 4) / 3, (w - 4) / 3);
		size = Math.max(size, 2);
		paintTriangle(g, (w - size) / 2 + 1, (h - size) / 2, size, direction,
				this.isEnabled());

	}

}
