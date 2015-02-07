package com.bestway.client.util.footer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.TableColumn;

import com.bestway.client.util.JTableListModel;

public class JLineViewport extends JViewport {

//	@Override
//	protected void printComponent(Graphics g) {
//		// 
//		super.printComponent(g);
//		if (this.getView() instanceof JTable) {
//			JTable table = (JTable) this.getView();
//			if (table.getModel() instanceof JTableListModel) {
//				Graphics2D g2 = (Graphics2D) g;
//				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//						RenderingHints.VALUE_ANTIALIAS_ON);
//
//				// Color fg3D = Color.LIGHT_GRAY;
//				// JTable table = (JTable) this.getView();
//				table.getGridColor();
//				Color drawColor = table.getGridColor();// Color.LIGHT_GRAY;//
//				// new
//				// Color(163, 161, 161);
//				BasicStroke stroke = new BasicStroke(1f);
//				g2.setPaint(drawColor);
//				g2.setStroke(stroke);
//				// this.getView();
//				paintLine(g2);
//			}
//		}
//	}

	@Override
	public void paint(Graphics g) {
		// 
		super.paint(g);
		if (this.getView() instanceof JTable) {
			JTable table = (JTable) this.getView();
			if (table.getModel() instanceof JTableListModel) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

				// Color fg3D = Color.LIGHT_GRAY;
				// JTable table = (JTable) this.getView();
				table.getGridColor();
				Color drawColor = table.getGridColor();// Color.LIGHT_GRAY;//
				// new
				// Color(163, 161, 161);
				BasicStroke stroke = new BasicStroke(1f,BasicStroke.CAP_ROUND,
			              BasicStroke.JOIN_ROUND, 0, new float[]{0,3,0,3}, 0);
				g2.setPaint(drawColor);
				g2.setStroke(stroke);
				// this.getView();
				paintLine(g2);
			}
		}
	}

	private void paintLine(Graphics2D g) {
		if (this.getView() instanceof JTable) {
			JTable table = (JTable) this.getView();
			Dimension d = getSize();
			Point vp = this.getViewPosition();
			double lby = 0;
			double lbx = -vp.x - 1;
			double lex = -vp.x - 1;
			double ley = d.getHeight();
			int columnCount = table.getColumnModel().getColumnCount();
			for (int i = 0; i < columnCount; i++) {
				TableColumn tableColumn = table.getColumnModel().getColumn(i);
				lbx += tableColumn.getPreferredWidth();
				lex += tableColumn.getPreferredWidth();
				g.draw(new Line2D.Double(lbx, lby, lex, ley));
			}
		}
	}

}
