/*
 * Created on 2004-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Point;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicProgressBarUI;

import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgProgress extends JDialogBase {

	private JPanel			jContentPane			= null;
	private JLabel			lbMessage				= null;
	private JProgressBar	jProgressBar			= null;
	private JLabel			jLabel					= null;
	private RunThread		runThread				= new RunThread();
	private Point			mousePressedPosition	= new Point(0, 0);
	private ProgressTask	progressTask			= null;

	/**
	 * This method initializes
	 * 
	 */
	public DgProgress() {
		super();
		initialize();
		runThread.setPriority(Thread.MIN_PRIORITY);
		runThread.start();
		this.setAlwaysOnTop(true);
	}

	public DgProgress(Dialog owner, boolean isModal) {
		super(owner, isModal);
		initialize();
		runThread.setPriority(Thread.MIN_PRIORITY);
		runThread.start();
	}

	public DgProgress(Dialog owner, boolean isModal, ProgressTask progressTask,
			long period) {
		super(owner, isModal);
		initialize();
		runThread.setPriority(Thread.MIN_PRIORITY);
		runThread.start();
		if (progressTask != null) {
			getTimer().schedule(progressTask, 0, period);
		}
	}

	public DgProgress(JFrame owner, boolean isModal, ProgressTask progressTask,
			long period) {		
		super(owner, isModal);
		if(owner == null){
			this.setAlwaysOnTop(true);
		}
		initialize();
		runThread.setPriority(Thread.MIN_PRIORITY);
		runThread.start();
		if (progressTask != null) {
			getTimer().schedule(progressTask, 0, period);
		}
	}

	public DgProgress(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
		runThread.setPriority(Thread.MIN_PRIORITY);
		runThread.start();
	}
	
	public DgProgress(JInternalFrame owner, boolean isModal, ProgressTask progressTask,
			long period) {
		super(owner, isModal);
		initialize();
		runThread.setPriority(Thread.MIN_PRIORITY);
		runThread.start();
		if (progressTask != null) {
			getTimer().schedule(progressTask, 0, period);
		}
	}

	public DgProgress(JInternalFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
		runThread.setPriority(Thread.MIN_PRIORITY);
		runThread.start();
	}

	public void setVisible(boolean b) {
		if (b) {
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setBounds(0, 0, 529, 96);
		this.setTitle("正在运行");
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setResizable(false);
	}

	private JLabel getLbMessage() {
		if (lbMessage == null) {
			lbMessage = new JLabel();
			lbMessage.setBounds(61, 12, 452, 19);
			lbMessage.setText("");
		}
		return lbMessage;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {

			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(11, 4, 37, 39));
			jLabel.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/info.gif")));
			jLabel.setText("");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBorder(javax.swing.BorderFactory.createLineBorder(
					java.awt.Color.gray, 1));
			jContentPane.add(getJProgressBar(), null);
			jContentPane.add(getLbMessage(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getBtnExit(), null);
			jContentPane.add(getBtnHide(), null);
			jContentPane
					.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
						public void mouseDragged(java.awt.event.MouseEvent e) {
							Point currentMousePosition = e.getPoint();
							Point currentWindowLocation = getLocation();
							int moveX = currentMousePosition.x
									- mousePressedPosition.x;
							int moveY = currentMousePosition.y
									- mousePressedPosition.y;
							currentWindowLocation.translate(moveX, moveY);
							setLocation(currentWindowLocation);
						}
					});
			jContentPane.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					mousePressedPosition = e.getPoint();
				}
			});
		}
		return jContentPane;
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

	public void setMessage(String message) {
		this.getLbMessage().setText(message);
	}

	public boolean	isFlag	= false;

	private Timer	timer	= null;

	private JButton	btnExit	= null;

	private JButton	btnHide	= null;

	class RunThread extends Thread {
		public void run() {

			while (!Thread.currentThread().isInterrupted()) {				
				for (int i = 0; i < 99; i++) {
					if (i == 5) {
						i = 20;
					}
					if (i == 21) {
						i = 40;
					}
					if (i == 41) {
						i = 99;
					}
					final int value = i;					
					try {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								jProgressBar.setValue(value);
							}
						});
						sleep(100);
					} catch (InterruptedException e) {
						//
						// 结束线程
						//
						return;
					}
				}
			}
		}
	}

	public void dispose() {
		jProgressBar.setValue(jProgressBar.getMaximum());
		if (runThread != null && runThread.isAlive()) {
			// Thread moribund = runThread;
			// runThread = null;
			runThread.interrupt();
			// runThread.interrupted();

//			System.out.println("moribund is closed!!");
		}
		if (timer != null) {
			timer.cancel();
			timer = null;
			System.out.println("schedule(progressTask, 0, period) is closed!!");
		}
		super.dispose();
	}

	/**
	 * This method initializes timer
	 * 
	 * @return java.util.Timer
	 */
	private Timer getTimer() {
		if (timer == null) {
			timer = new Timer();
		}
		return timer;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(new java.awt.Rectangle(416, 69, 59, 20));
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CommonProgress.setDgProgress(null);
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes btnHide
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHide() {
		if (btnHide == null) {
			btnHide = new JButton();
			btnHide.setBounds(new java.awt.Rectangle(353, 69, 59, 20));
			btnHide.setText("隐藏");
			btnHide.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return btnHide;
	}
	
	
	
	
	/**
	 * 设置进度条的值
	 */
	public void setJProgressBarValue(final int value) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jProgressBar.setValue(value);
			}
		});

	}
	
	/**
	 * 初始化组件 最重要的是设置进度条的最大值
	 */
	public void initJProgressBar(final int maxValue) {
		if (runThread != null && runThread.isAlive()) {
			runThread.interrupt();
		}
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jProgressBar.setValue(0);
				jProgressBar.setMinimum(0);
				jProgressBar.setMaximum(maxValue);
				jProgressBar.setStringPainted(true);
				jProgressBar.setIgnoreRepaint(true);
			}
		});

	}
	
}
