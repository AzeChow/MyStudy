package com.bestway.ui.winuicontrol;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

import javax.swing.JLabel;
import javax.swing.UIManager;

public class JLineLabel extends JLabel {

	final static BasicStroke stroke = new BasicStroke(1.0f);

	private Color drawColor =  new Color(163, 161, 161);//
													// SystemColor.activeCaption;//Color.LIGHT_GRAY;
//	(Color) UIManager.getLookAndFeel().getDefaults()
//	.get("TextPane.selectionBackground");//

	public JLineLabel() {
		this.setText("");
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(drawColor);
		g2.setStroke(stroke);
		this.paintOnlyLineH(g2);
	}

	private void paintOnlyLineH(Graphics2D g) {
		Dimension d = getSize();
		double lby = d.getHeight() ;//- 2*stroke.getLineWidth();
		double lbx = 0;
		double lex = d.getWidth();
		double ley = d.getHeight() ;//- 2*stroke.getLineWidth();
		g.draw(new Line2D.Double(lbx, lby, lex, ley));
	}
}
