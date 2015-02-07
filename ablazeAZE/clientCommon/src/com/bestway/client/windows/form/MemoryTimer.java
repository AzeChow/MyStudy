/*
 * Created on 2004-6-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.windows.form;

import java.awt.Color;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * @author bsway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MemoryTimer extends Thread {
	private JLabel lbShowMemony = null;
	private int interval = 2000;

	private int loopTimes = 0;

	private StringBuffer sb = new StringBuffer("");

	private JProgressBar jProgressBar = null;

	public MemoryTimer(JLabel lbShowMemony) {
		this.lbShowMemony = lbShowMemony;
	}

	public void run() {
		if (lbShowMemony != null) {
			while (this != null) {
				Runtime runtime = Runtime.getRuntime();
				sb
						.append("系统允许最大使用内存:"
								+ formatDecimal(runtime.maxMemory() / 1048576.0)
								+ "MB");
				sb.append("  总分配内存:"
						+ formatDecimal(runtime.totalMemory() / 1048576.0)
						+ "MB");
				sb.append("  已使用内存:"
						+ formatDecimal(((runtime.totalMemory() - runtime
								.freeMemory()) / 1048576.0)) + "MB");
				sb.append("  未使用内存:"
						+ formatDecimal(runtime.freeMemory() / 1048576.0)
						+ "MB");
				this.lbShowMemony.setText(sb.toString());
				sb.delete(0, sb.length());
				this.lbShowMemony.repaint();
				/**
				 * 每隔五分钟进行一次手动内存回收。
				 */
				if (loopTimes == 150) {
					loopTimes = 0;
					System.gc();
				} else {
					loopTimes++;
				}
				try {
					this.sleep(interval);
				} catch (InterruptedException e) {
				}

			}
		}
	}

	private String formatDecimal(double f) {
		BigDecimal b = new BigDecimal(f);
		double df = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(df);
	}

	/**
	 * This method initializes jProgressBar
	 * 
	 * @return javax.swing.JProgressBar
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
			jProgressBar.setUI(new BasicProgressBarUI() {
				protected int getCellLength() {
					return 10;
				}

				protected int getCellSpacing() {
					return 2;
				}
			});
			jProgressBar.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			jProgressBar.setBounds(7, 47, 510, 18);
			jProgressBar.setForeground(new Color(10, 36, 106));
			jProgressBar.setBackground(Color.white);

		}
		return jProgressBar;
	}
}