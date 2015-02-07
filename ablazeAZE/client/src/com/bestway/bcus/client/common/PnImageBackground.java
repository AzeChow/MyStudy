/*
 * Created on 2005-6-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PnImageBackground extends JPanel {

	/**
	 * This is the default constructor
	 */
	public PnImageBackground() {
		super();
	}
	
	
	 public void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            Image image = CommonVars.getImageSource().getImage("background.gif");
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        } catch (Exception e) {

        }
    }
}  