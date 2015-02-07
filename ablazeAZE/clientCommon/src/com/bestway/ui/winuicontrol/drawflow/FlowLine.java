package com.bestway.ui.winuicontrol.drawflow;

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

public class FlowLine extends JLabel {

	final static BasicStroke stroke = new BasicStroke(1.0f);

	final static BasicStroke wideStroke = new BasicStroke(8.0f);

//	final static float dash1[] = { 10.0f };

//	final static BasicStroke dashed = new BasicStroke(1.0f,
//			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

	final static int arrowHeight = 6;

	final static int arrowWidth = 3;

	private boolean onlyDrawLine = false;
	
	private boolean isDrawPairArrow=false;

	private int arrowDirection = ARROW_RIGHT;

	private int lineDirection = LINE_H;

	private Color drawColor = new Color(0,102,153);//163, 161, 161// SystemColor.activeCaption;//Color.LIGHT_GRAY;

	public static int ARROW_RIGHT = 0;

	public static int ARROW_LEFT = 1;

	public static int ARROW_TOP = 2;

	public static int ARROW_BOTTOM = 3;

//	public static int LEFTBOTTOM_TO_TOPRIGHT = 10;
//
//	public static int LEFTTOP_TO_BOTTOMRIGHT = 11;

	public static int LINE_H = 4;

	public static int LINE_V = 5;

	public FlowLine() {
		this.setText("");
	}

	public boolean isOnlyDrawLine() {
		return onlyDrawLine;
	}

	public void setOnlyDrawLine(boolean onlyDrawLine) {
		this.onlyDrawLine = onlyDrawLine;
		this.repaint();
	}

	public boolean isDrawPairArrow() {
		return isDrawPairArrow;
	}

	public void setDrawPairArrow(boolean isDrawPairArrow) {
		this.isDrawPairArrow = isDrawPairArrow;
		this.repaint();
	}

	public int getLineDirection() {
		return lineDirection;
	}

	public void setLineDirection(int lineDirection) {
		this.lineDirection = lineDirection;
		this.repaint();
	}

	public int getArrowDirection() {
		return arrowDirection;
	}

	public void setArrowDirection(int drawDirection) {
		this.arrowDirection = drawDirection;
		this.repaint();
	}

	public Color getDrawColor() {
		return drawColor;
	}

	public void setDrawColor(Color drawColor) {
		this.drawColor = drawColor;
		this.repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Color fg3D = Color.LIGHT_GRAY;

		g2.setPaint(drawColor);

		g2.setStroke(stroke);
		if (this.onlyDrawLine) {
			if (this.lineDirection == LINE_H) {
				this.paintOnlyLineH(g2);
			} else {
				this.paintOnlyLineV(g2);
			}
		} else {
			if (this.arrowDirection == ARROW_RIGHT) {
				this.paintToRight(g2);
				if(this.isDrawPairArrow){
					this.paintToLeft(g2);
				}
			} else if (this.arrowDirection == ARROW_LEFT) {
				this.paintToLeft(g2);
				if(this.isDrawPairArrow){
					this.paintToRight(g2);
				}
			} else if (this.arrowDirection == ARROW_TOP) {
				this.paintToTop(g2);
				if(this.isDrawPairArrow){
					this.paintToBottom(g2);
				}
			} else if (this.arrowDirection == ARROW_BOTTOM) {
				this.paintToBottom(g2);
				if(this.isDrawPairArrow){
					this.paintToTop(g2);
				}
			}
//			else if (this.arrowDirection == LEFTBOTTOM_TO_TOPRIGHT) {
//				this.paintLeftBottomToTopRight(g2);
//			}
		}
	}

	private void paintToRight(Graphics2D g) {
		Dimension d = getSize();
		double lby = d.getHeight() / 2;
		double lbx = 0;
		double lex = d.getWidth() - arrowHeight;
		double ley = d.getHeight() / 2;
		g.draw(new Line2D.Double(lbx, lby, lex, ley));
		int x1 = Double.valueOf(d.getWidth()).intValue() - arrowHeight;
		int y1 = Double.valueOf(d.getHeight() / 2).intValue() - arrowWidth;

		int x2 = Double.valueOf(d.getWidth()).intValue() - arrowHeight;
		int y2 = Double.valueOf(d.getHeight() / 2).intValue() + arrowWidth;

		int x3 = Double.valueOf(d.getWidth()).intValue();
		int y3 = Double.valueOf(d.getHeight() / 2).intValue();

		int x3Points[] = { x1, x2, x3, x1 };
		int y3Points[] = { y1, y2, y3, y1 };
		GeneralPath filledPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
				x3Points.length);
		filledPolygon.moveTo(x3Points[0], y3Points[0]);

		for (int index = 1; index < x3Points.length; index++) {
			filledPolygon.lineTo(x3Points[index], y3Points[index]);
		}
		filledPolygon.closePath();
		g.fill(filledPolygon);
	}

	private void paintToLeft(Graphics2D g) {
		Dimension d = getSize();
		double lby = d.getHeight() / 2;
		double lbx = arrowHeight;
		double lex = d.getWidth();
		double ley = d.getHeight() / 2;
		g.draw(new Line2D.Double(lbx, lby, lex, ley));
		int x1 = arrowHeight;
		int y1 = Double.valueOf(d.getHeight() / 2).intValue() - arrowWidth;

		int x2 = arrowHeight;
		int y2 = Double.valueOf(d.getHeight() / 2).intValue() + arrowWidth;

		int x3 = 0;
		int y3 = Double.valueOf(d.getHeight() / 2).intValue();

		int x3Points[] = { x1, x2, x3, x1 };
		int y3Points[] = { y1, y2, y3, y1 };
		GeneralPath filledPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
				x3Points.length);
		filledPolygon.moveTo(x3Points[0], y3Points[0]);

		for (int index = 1; index < x3Points.length; index++) {
			filledPolygon.lineTo(x3Points[index], y3Points[index]);
		}
		filledPolygon.closePath();
		g.fill(filledPolygon);
	}

	private void paintToBottom(Graphics2D g) {
		Dimension d = getSize();
		double lby = 0;
		double lbx = d.getWidth() / 2;
		double lex = d.getWidth() / 2;
		double ley = d.getHeight() - arrowHeight;
		g.draw(new Line2D.Double(lbx, lby, lex, ley));
		int x1 = Double.valueOf(d.getWidth() / 2).intValue() - arrowWidth;
		int y1 = Double.valueOf(d.getHeight()).intValue() - arrowHeight;

		int x2 = Double.valueOf(d.getWidth() / 2).intValue() + arrowWidth;
		int y2 = Double.valueOf(d.getHeight()).intValue() - arrowHeight;

		int x3 = Double.valueOf(d.getWidth() / 2).intValue();
		int y3 = Double.valueOf(d.getHeight()).intValue();

		int x3Points[] = { x1, x2, x3, x1 };
		int y3Points[] = { y1, y2, y3, y1 };
		GeneralPath filledPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
				x3Points.length);
		filledPolygon.moveTo(x3Points[0], y3Points[0]);

		for (int index = 1; index < x3Points.length; index++) {
			filledPolygon.lineTo(x3Points[index], y3Points[index]);
		}
		filledPolygon.closePath();
		g.fill(filledPolygon);
	}

	private void paintToTop(Graphics2D g) {
		Dimension d = getSize();
		double lby = arrowHeight;
		double lbx = d.getWidth() / 2;
		double lex = d.getWidth() / 2;
		double ley = d.getHeight();
		g.draw(new Line2D.Double(lbx, lby, lex, ley));
		int x1 = Double.valueOf(d.getWidth() / 2).intValue() - arrowWidth;
		int y1 = arrowHeight;

		int x2 = Double.valueOf(d.getWidth() / 2).intValue() + arrowWidth;
		int y2 = arrowHeight;

		int x3 = Double.valueOf(d.getWidth() / 2).intValue();
		int y3 = 0;

		int x3Points[] = { x1, x2, x3, x1 };
		int y3Points[] = { y1, y2, y3, y1 };
		GeneralPath filledPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
				x3Points.length);
		filledPolygon.moveTo(x3Points[0], y3Points[0]);

		for (int index = 1; index < x3Points.length; index++) {
			filledPolygon.lineTo(x3Points[index], y3Points[index]);
		}
		filledPolygon.closePath();
		g.fill(filledPolygon);
	}

	private void paintOnlyLineV(Graphics2D g) {
		Dimension d = getSize();
		double lby = 0;
		double lbx = d.getWidth() / 2;
		double lex = d.getWidth() / 2;
		double ley = d.getHeight();
		g.draw(new Line2D.Double(lbx, lby, lex, ley));
	}

	private void paintOnlyLineH(Graphics2D g) {
		Dimension d = getSize();
		double lby = d.getHeight() / 2;
		double lbx = 0;
		double lex = d.getWidth();
		double ley = d.getHeight() / 2;
		g.draw(new Line2D.Double(lbx, lby, lex, ley));
	}
	
//	private void paintLeftBottomToTopRight(Graphics2D g){
//		Dimension d = getSize();
//		double lby = d.getHeight();
//		double lbx = 0;
//		double lex = d.getWidth() ;
//		double ley = 0;
//		g.draw(new Line2D.Double(lbx, lby, lex, ley));
//		
//		double len=Math.sqrt(Math.pow(d.getWidth(), 2)+Math.pow(d.getHeight(), 2));
//		
//		double yy=arrowHeight*(d.getHeight()/len);
//		double xx=arrowHeight*(d.getWidth()/len);
//		
//		double yyy=(arrowWidth/2.0)*(d.getWidth()/len);
//		double xxx=(arrowWidth/2.0)*(d.getHeight()/len);
//		
//		int x1 = Double.valueOf(d.getWidth() - xx-xxx).intValue();
//		int y1 = Double.valueOf(yy-yyy).intValue();
//
//		int x2 = Double.valueOf(d.getWidth() - xx+xxx).intValue();
//		int y2 = Double.valueOf(yy+yyy).intValue();
//
//		int x3 = Double.valueOf(d.getWidth()).intValue();
//		int y3 = 0;
//		
//
//		
//		int x3Points[] = { x1, x2, x3, x1 };
//		int y3Points[] = { y1, y2, y3, y1 };
//		GeneralPath filledPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
//				x3Points.length);
//		filledPolygon.moveTo(x3Points[0], y3Points[0]);
//
//		for (int index = 1; index < x3Points.length; index++) {
//			filledPolygon.lineTo(x3Points[index], y3Points[index]);
//		}
//		filledPolygon.closePath();
//		g.fill(filledPolygon);
//	}
}
