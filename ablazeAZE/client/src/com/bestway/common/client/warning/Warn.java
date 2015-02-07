package com.bestway.common.client.warning;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;
import com.bestway.common.warning.action.WarningAction;

public class Warn extends Thread {

	private static Thread warnThead = null;
	private long period = 0;

	public static void RunWarning(long period) {
		if (warnThead == null) {
			warnThead = new Warn(period);
			warnThead.start();
		}
	}

	public static void RunWarning() {
		if (warnThead == null) {
			warnThead = new Warn();
			warnThead.start();
		}
	}

	public Warn(long period) {
		this.period = period;
	}

	public Warn() {

	}

	public void run() {
		try {
			sleep(period);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (!Thread.currentThread().isInterrupted()) {
			try {
				setPnWarning();
			} catch (Exception ex) {
				//
				// 不中断线程
				//
				ex.printStackTrace();
			}
			try {
				sleep(60 * 1000); // 5 分钟
			} catch (InterruptedException e) {
				break;
			}

		}
	}

	protected void setPnWarning() {
		JPanel pnWarning = FmMain.getInstance().getPnWarning();
		Map<String, String> existWarnLabel = new HashMap<String, String>(21);
		for (Component c : pnWarning.getComponents()) {
			if (c instanceof WarnLable) {
				String key = ((WarnLable) c).getKey();
				existWarnLabel.put(key, key);
			}
		}

		WarningAction warningAction = (WarningAction) CommonVars
				.getApplicationContext().getBean("warningAction");
		Map<String, StringBuffer> warningMap = warningAction
				.getWarningCache(new Request(CommonVars.getCurrUser()));
		String[] keys = warningMap.keySet().toArray(new String[] {});
		//
		// warn window queue start
		//
		for (String key : keys) {
			// System.out.println("-----------------" + key);
			//
			// 如果已经有产生 warn icon 就不用再生成 warn window
			//
			if (existWarnLabel.containsKey(key)) {
				continue;
			}
			DgWarn window = new DgWarn(FmMain.getInstance(), warningMap
					.get(key).toString());
			window.setVisible(true);
			boolean isClose = MoveWindow(window);
			if (!isClose) {
				//
				// 
				//
				addWarnLable(key, pnWarning);
			}
		}
		//
		// 重新刷新
		//
		pnWarning.repaint();
	}

	//
	// 产生向上向下的效果 warn window
	//
	private boolean MoveWindow(DgWarn window) {

		boolean isClose = true;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//
		// 向上
		//
		for (int i = 0;; i += 2) {
			final int value = i;
			// System.out.println(i);
			try {
				Point oldLocation = window.getLocation();
				oldLocation.setLocation(oldLocation.x, oldLocation.y - value);
				window.setLocation(oldLocation);
				Dimension frameSize = window.getSize();
				if (window.getLocation().y <= screenSize.height
						- frameSize.height) {
					break;
				}
				Thread.sleep(100);

			} catch (InterruptedException e) {
				e.printStackTrace();
				//
				// 结束线程
				//		
				return isClose;
			}
		}

		try {
			while (window.isMouseEntered()) {
				Thread.sleep(1000);
			}
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return isClose;
		}

		//
		// 向下
		//				
		for (int i = 0;; i += 2) {
			final int value = i;
			// System.out.println(i);
			try {
				Point oldLocation = window.getLocation();
				oldLocation.setLocation(oldLocation.x, oldLocation.y + value);
				window.setLocation(oldLocation);
				// window.setSize(window.getSize().width,window.getSize().height-value);
				if (window.getLocation().y >= screenSize.height) {
					break;
				}
				Thread.sleep(100);

			} catch (InterruptedException e) {
				e.printStackTrace();
				//
				// 结束线程
				//						
				return isClose;
			}
		}
		isClose = window.isClose();
		window.dispose();
		window = null;
		return isClose;
	}

	private void addWarnLable(String key, JPanel pnWarning) {
		WarnLable lable = new WarnLable();
		lable.setKey(key);
		lable.setIcon(CommonVars.getIconSource().getIcon("smallwarning.gif"));
		lable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() >= 2) {
					WarningAction warningAction = (WarningAction) CommonVars
							.getApplicationContext().getBean("warningAction");
					WarnLable source = (WarnLable) e.getSource();
					String key = source.getKey();
					StringBuffer sb = warningAction.getWarningByKey(
							new Request(CommonVars.getCurrUser()), key);

					DgWarn window = new DgWarn(FmMain.getInstance(),
							sb == null ? "程序运行正常!!" : sb.toString());

					window.setKey(key);
					//
					// 用于关闭时用
					//
					window.setFirst(false);
					window.setInitVisible(true);
				}
			}
		});
		pnWarning.add(lable, null);
	}

	public static class WarnLable extends JLabel {
		private String key = null;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

	}

}
