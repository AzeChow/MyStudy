package com.bestway.ui.winuicontrol.drawflow;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

public class FlowButton extends JButton {

	private final static float borderWidth = 1.0f;
	private final static BasicStroke borderStroke = new BasicStroke(borderWidth);
	// final static BasicStroke wideStroke = new BasicStroke(8.0f);

	private int buttonType = 0;

	// final static float dash1[] = { 10.0f };
	// final static BasicStroke dashed = new BasicStroke(1.0f,
	// BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
	private Color tempFontColor = null;
	private Color borderColor = new Color(121, 193, 228); // 163, 161, 161 //
	// SystemColor.activeCaption;//Color.LIGHT_GRAY;
	private Color topFilledColor = new Color(255, 255, 255);
	private Color bottomFilledColor = new Color(229, 247, 253);

	public int getButtonType() {
		return buttonType;
	}

	public void setButtonType(int buttonType) {
		this.buttonType = buttonType;
		this.repaint();
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color drawColor) {
		this.borderColor = drawColor;
		this.repaint();
	}

	public Color getTopFilledColor() {
		return topFilledColor;
	}

	public void setTopFilledColor(Color fillTopColor) {
		this.topFilledColor = fillTopColor;
		this.repaint();
	}

	public Color getBottomFilledColor() {
		return bottomFilledColor;
	}

	public void setBottomFilledColor(Color fillBottomColor) {
		this.bottomFilledColor = fillBottomColor;
		this.repaint();
	}

	public FlowButton() {
		this.setBorder(null);
		this.setContentAreaFilled(false);
		this.setForeground(new java.awt.Color(102, 102, 102));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setFont(new java.awt.Font("新宋体", java.awt.Font.BOLD, 12));
		this.setActionCommand("");
		this.addMouseListener(new java.awt.event.MouseAdapter() {

			// 鼠标滑进事件
			@Override
			public void mouseEntered(MouseEvent e) {
				tempFontColor = FlowButton.this.getForeground();
				FlowButton.this.setForeground(new Color(255, 102, 0));
			}

			// 鼠标滑出事件
			@Override
			public void mouseExited(MouseEvent e) {
				if (tempFontColor != null) {
					FlowButton.this.setForeground(tempFontColor);
				}
			}
		});
	}

	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		// g2.drawString(this.getText(), 10, 20);
		// this.setText(this.getText());
		// this.p
		// g2.fillRect(x, y, width, height)
		// g2.drawString("RoundRectangle2D", x, stringY);
		if (buttonType == 0) {
			drawRoundButton(g2);
		} else if (buttonType == 1) {
			drawRectangleButton(g2);
		} else if (buttonType == 2) {
			drawEllipseButton(g2);
		}
	}

	// 绘画圆形按钮
	private void drawRoundButton(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension d = getSize();
		double y = 0;
		double x = 0;
		double rectWidth = d.getWidth();
		double rectHeight = d.getHeight();
		// Color fg3D = Color.LIGHT_GRAY;
		g2.setPaint(borderColor);
		g2.setStroke(borderStroke);
		g2.draw(new RoundRectangle2D.Double(x, y, rectWidth - borderWidth,
				rectHeight - borderWidth, 18, 18));
		GradientPaint redtowhite = new GradientPaint(0,
				this.getPreferredSize().height, bottomFilledColor, 0, 0,
				topFilledColor);
		g2.setPaint(redtowhite);
		g2.fill(new RoundRectangle2D.Double(x + borderWidth, y + borderWidth,
				rectWidth - 2.0 * borderWidth, rectHeight - 2.0 * borderWidth,
				18, 18));
		// g2.setPaint(Color.BLACK );
		super.paint(g2);
	}

	private void drawEllipseButton(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension d = getSize();
		double y = 0;
		double x = 0;
		double rectWidth = d.getWidth();
		double rectHeight = d.getHeight();
		// Color fg3D = Color.LIGHT_GRAY;
		g2.setPaint(borderColor);
		g2.setStroke(borderStroke);
		g2.draw(new Ellipse2D.Double(x, y, rectWidth - borderWidth, rectHeight
				- borderWidth));
		GradientPaint redtowhite = new GradientPaint(0,
				this.getPreferredSize().height, bottomFilledColor, 0, 0,
				topFilledColor);
		g2.setPaint(redtowhite);
		g2.fill(new Ellipse2D.Double(x + borderWidth, y + borderWidth,
				rectWidth - 2.0 * borderWidth, rectHeight - 2.0 * borderWidth));
		// g2.setPaint(Color.BLACK );
		super.paint(g2);
	}

	private void drawRectangleButton(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension d = getSize();
		double y = 0;
		double x = 0;
		double rectWidth = d.getWidth();
		double rectHeight = d.getHeight();
		// Color fg3D = Color.LIGHT_GRAY;
		g2.setPaint(borderColor);
		g2.setStroke(borderStroke);
		g2.draw(new Rectangle2D.Double(x, y, rectWidth - borderWidth,
				rectHeight - borderWidth));
		GradientPaint redtowhite = new GradientPaint(0,
				this.getPreferredSize().height, bottomFilledColor, 0, 0,
				topFilledColor);
		g2.setPaint(redtowhite);
		g2.fill(new Rectangle2D.Double(x + borderWidth, y + borderWidth,
				rectWidth - 2.0 * borderWidth, rectHeight - 2.0 * borderWidth));
		// g2.setPaint(Color.BLACK );
		super.paint(g2);
	}

}
