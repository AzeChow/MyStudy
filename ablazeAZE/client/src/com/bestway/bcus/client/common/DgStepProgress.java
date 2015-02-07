/*
 * Created on 2004-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicProgressBarUI;

import com.bestway.common.BaseAction;
import com.bestway.common.ProgressInfo;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgStepProgress extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel lbMessage = null;

	private JProgressBar jProgressBar = null;

	private JLabel jLabel = null;

	private ToolsAction toolsAction = null;

	private ProgressInfo info = null;

	private Point mousePressedPosition = new Point(0, 0);  //  @jve:decl-index=0:

	private String taskId;
	
	private ProgressTask progressTask = null;  //  @jve:decl-index=0:

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgStepProgress() {
		super();
		initialize();
		this.setAlwaysOnTop(true);
	}

	public DgStepProgress(Dialog owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgStepProgress(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public void setVisible(boolean b) {
		if (b) {
			progressTask=new ProgressTask();
			getTimer().schedule(progressTask, 0, 2000);
		}
		super.setVisible(b);
	}

	public void setProgressMaximum(int maximum) {
		this.jProgressBar.setMaximum(maximum);
	}

	public void setProgressValue(int value) {
		this.jProgressBar.setValue(value);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setBounds(0, 0, 529, 96);
		this.setTitle("正在运行");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setResizable(false);
	}

	private JLabel getLbMessage() {
		if (lbMessage == null) {
			lbMessage = new JLabel();
			lbMessage.setBounds(65, 8, 443, 19);
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

			lbRemainTime = new JLabel();
			lbRemainTime.setBounds(new Rectangle(65, 28, 443, 19));
			lbRemainTime.setText("");
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
			jContentPane.add(lbRemainTime, null);
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

	public void setMessage(final String message) {
		SwingUtilities.invokeLater(new Runnable() {			
			public void run() {
				getLbMessage().setText(message);
			}
		});
	}

	public boolean isFlag = false;

	private Timer timer = null; // @jve:decl-index=0:visual-constraint="582,47"

	private JLabel lbRemainTime = null;

	private JButton btnExit = null;

	private JButton btnHide = null;

	// public void dispose() {
	// jProgressBar.setValue(jProgressBar.getMaximum());
	// // if (runThread != null && runThread.isAlive()) {
	// // Thread moribund = runThread;
	// // runThread = null;
	// // moribund.interrupt();
	// // // runThread.stop();
	// // }
	// super.dispose();
	// }

	public void close() {
		if (toolsAction != null) {
			toolsAction.removeProgressInfo(taskId);
		}
		if(progressTask!=null){
			progressTask.cancel();
		}
		if(timer!=null){
			timer.purge();
		}
		this.dispose();
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

	class ProgressTask extends TimerTask {
		public void run() {
			if (taskId == null || "".equals(taskId)) {
				return;
			}
			if (toolsAction == null) {
				toolsAction = (ToolsAction) CommonVars.getApplicationContext()
						.getBean("toolsAction");
			}
			info = toolsAction.getProgressInfo(taskId);
			if (info == null) {
				return;
			}
//			if (info.getTotalNum() >= 0
//					&& jProgressBar.getMaximum() != info.getTotalNum()) {
				jProgressBar.setMaximum(info.getTotalNum());
////			}
//			if (info.getCurrentNum() >= 0) {
				jProgressBar.setValue(info.getCurrentNum());
				lbMessage.setText(info.getMethodName());
				lbRemainTime.setText(info.getTimeInfo());
//			}
		}
	}

	// public ProgressInfo createRunningTask() {
	// if (runThread.isAlive()) {
	// runThread.interrupt();
	// }
	// // task = toolsAction.;
	// return task;
	// }

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
					close();
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
}
