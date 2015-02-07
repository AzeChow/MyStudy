package com.bestway.client.windows.control;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.Dimension;

/**
 * @Author KCB 屏幕抓图
 * 
 */

public class ScreenCapture {
	private int x1, y1, x2, y2;

	private int recX, recY, recH, recW; // 截取的图像

	private boolean isFirstPoint = true;

	private ImgLab imgLab = null;

	private Robot robot = null;

	private BufferedImage fullScreenImage = null;

	private BufferedImage pickedImage = null;

	private JDialog dialog = null;

	public boolean isOK = false;

	public void doDraw() {
	}

	public ScreenCapture() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			System.err.println("Internal Error: " + e);
			e.printStackTrace();
		}
		dialog = new JDialog();
		// =========================================
		imgLab = new ImgLab();
		imgLab.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent evn) {
				if (recX <= 0 || recY <= 0 || recW <= 0 || recH <= 0) {
					return;
				}
				isFirstPoint = true;
				pickedImage = fullScreenImage.getSubimage(recX, recY, recW,
						recH);
				isOK = true;
				dialog.setVisible(false);

			}
		});
		imgLab.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent evn) {
				if (isFirstPoint) {
					x1 = evn.getX();
					y1 = evn.getY();
					isFirstPoint = false;
				} else {
					x2 = evn.getX();
					y2 = evn.getY();
					int maxX = Math.max(x1, x2);
					int maxY = Math.max(y1, y2);
					int minX = Math.min(x1, x2);
					int minY = Math.min(y1, y2);
					recX = minX;
					recY = minY;
					recW = maxX - minX;
					recH = maxY - minY;
					imgLab.drawRectangle(recX, recY, recW, recH);
				}
			}

			public void mouseMoved(MouseEvent e) {
				// imgLab.drawCross(e.getX(), e.getY());
			}

		});
		// =========================================

		JPanel cp = (JPanel) dialog.getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(BorderLayout.CENTER, imgLab);
		dialog.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		dialog.setAlwaysOnTop(true);
		dialog.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		dialog.setUndecorated(true);
		dialog.setSize(dialog.getMaximumSize());
		dialog.setModal(true);
		captureImage();

	}

	/** 捕捉全屏慕 */
	public Icon captureFullScreen() {
		fullScreenImage = robot.createScreenCapture(new Rectangle(Toolkit
				.getDefaultToolkit().getScreenSize()));
		ImageIcon icon = new ImageIcon(fullScreenImage);
		return icon;
	}

	/**
	 * 捕捉屏幕的一个矫形区域
	 */
	public void captureImage() {
		fullScreenImage = robot.createScreenCapture(new Rectangle(Toolkit
				.getDefaultToolkit().getScreenSize()));
		ImageIcon icon = new ImageIcon(fullScreenImage);
		imgLab.setIcon(icon);
		dialog.setVisible(true);
	}

	/** 得到捕捉后的BufferedImage */
	public BufferedImage getPickedImage() {
		return pickedImage;
	}

	/** 得到捕捉后的Icon */
	public ImageIcon getPickedIcon() {
		return new ImageIcon(getPickedImage());
	}

}

/** 显示图片的Label */
class ImgLab extends JLabel {

	int x, y, h, w;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		g.drawRect(x, y, w, h);
		String area = "图像大小:" + Integer.toString(w) + " * "
				+ Integer.toString(h);
		g.setColor(Color.BLUE);
		g.drawString(area, x + (int) w / 2 - 45, y + (int) h / 2);
	}

	public void drawRectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		h = height;
		w = width;
		repaint();
	}
}
