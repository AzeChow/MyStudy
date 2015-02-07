/*
 * Created on 2008-5-16 luosheng 
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.upgrade;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.upgrade.httpconfig.HttpConfigEntity;
import com.bestway.client.upgrade.httpconfig.UpdateHttpConfigService;
import com.bestway.client.upgrade.httpconfig.UpdateHttpConfigServiceService;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgUpgrade extends JDialogBase {

	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JButton btnUpgrade = null;
	private JButton btnExit = null;
	private JPanel pnContext = null;
	private JProgressBar jProgressBar = null;
	private JLabel lbMessageTop = null;
	private JLabel lbMessageBottom = null;
	private JLabel jLabel2 = null;
	private JScrollPane jScrollPane = null;
	private JTextArea jTextArea = null;

	private JButton btnHttpConfig = null;
	private RunThread runThread = null;

	private UpdateHttpConfigServiceService httpConfigService = null;
	private UpgradeServiceService upgradeService = null;
	private UpgradeInfoThread r = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgUpgrade() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initialize();
		setProgressShowState(false);
		String serverHostAddress = CommonVars.getServerName();
		httpConfigService = UpdateHttpConfigServiceService
				.getUpdateHttpConfigServiceService(serverHostAddress);
		upgradeService = UpgradeServiceService
				.getUpgradeServiceService(serverHostAddress);
	}

	@Override
	public void setVisible(boolean b) {
		if (b == true) {

		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("JBCUS Upgrade --- 关务整合平台升级 ");
		this.setContentPane(getJContentPane());
		this.setSize(542, 386);
		this.setResizable(false);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				closeThread(r);
				// closeThread(runThread);
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getPnContext(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(18, 17, 313, 31));
			jLabel2.setFont(new Font("Dialog", Font.BOLD, 24));
			jLabel2.setText("JBCUS 程式升级(客户端)");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(1, 1, 535, 66);
			jPanel.setBackground(java.awt.Color.white);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(jLabel2, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(1, 300, 535, 59);
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.add(getBtnUpgrade(), null);
			jPanel1.add(getBtnExit(), null);
			jPanel1.add(getBtnHttpConfig(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnUpgrade
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpgrade() {
		if (btnUpgrade == null) {
			btnUpgrade = new JButton();
			btnUpgrade.setBounds(362, 11, 60, 24);
			btnUpgrade.setText("升级");
			btnUpgrade.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
					12));
			btnUpgrade.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					startUpgrade();
				}
			});
		}
		return btnUpgrade;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(426, 11, 60, 24);
			btnExit.setText("关闭");
			btnExit
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							12));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					closeThread(r);
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes pnContext
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContext() {
		if (pnContext == null) {
			lbMessageBottom = new JLabel();
			lbMessageTop = new JLabel();
			pnContext = new JPanel();
			pnContext.setLayout(null);
			pnContext.setBounds(1, 68, 536, 231);
			pnContext.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			lbMessageTop.setBounds(29, 8, 478, 16);
			lbMessageTop.setText(".....");
			lbMessageTop.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 12));

			lbMessageBottom.setBounds(29, 50, 478, 18);
			lbMessageBottom.setText("共 12 MB 已下载 0.25 MB");
			lbMessageBottom.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 12));

			pnContext.add(getJProgressBar(), null);
			pnContext.add(lbMessageTop, null);
			pnContext.add(lbMessageBottom, null);
			pnContext.add(getJScrollPane(), null);
		}
		return pnContext;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(29, 72, 478, 153));
			jScrollPane.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
		}
		return jTextArea;
	}

	/**
	 * This method initializes jProgressBar
	 * 
	 * @return javax.swing.JProgressBar
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			// jProgressBar = new JProgressBar();
			jProgressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
			jProgressBar.setUI(new BasicProgressBarUI() {
				protected int getCellLength() {
					return 10;
				}

				protected int getCellSpacing() {
					return 2;
				}
			});
			jProgressBar.setBorder(null);
			jProgressBar.setBounds(29, 27, 478, 16);
			jProgressBar.setForeground(new Color(10, 36, 106));
			jProgressBar.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 12));
			// jProgressBar.setBackground(java.awt.Color.white);
		}
		return jProgressBar;
	}

	/**
	 * This method initializes btnHttpConfig
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHttpConfig() {
		if (btnHttpConfig == null) {
			btnHttpConfig = new JButton();
			btnHttpConfig.setBounds(new Rectangle(263, 11, 96, 24));
			btnHttpConfig.setText("参数设置");
			btnHttpConfig.setFont(new Font("Dialog", Font.PLAIN, 12));
			btnHttpConfig
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							UpgradeService servicePort = upgradeService
									.getUpgradeServicePort();
							if (servicePort.checkUpgrade()) {
								JOptionPane.showMessageDialog(DgUpgrade.this,
										"服务端另一个用户正在进行升级!!", "", 2);
								return;
							}
							DgUpgradeConfig dg = new DgUpgradeConfig();
							dg.setVisible(true);
						}
					});
		}
		return btnHttpConfig;
	}

	class UpgradeInfoThread extends Thread {
		UpgradeService servicePort = null;

		public UpgradeInfoThread() {
			servicePort = upgradeService.getUpgradeServicePort();
		}

		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					UpgradeInfo info = servicePort.getUpgradeInfo();
					if (info != null) {
						initJProgressBar(info.getMaxValue() == null ? 100
								: info.getMaxValue());
						setJProgressBarValue(info.getValue() == null ? 0 : info
								.getValue());
						setMessageTop(info.getMessageTop());
						setMessageBottom(info.getMessageBottom());
						setJTextArea(info.getNote());
						if (info.isUpgradeSucceed() || info.isUpgradeError()) {
							setButtonState(true);
							setProgressShowState(false);
							break;
						}
					}
					sleep(500);
				} catch (InterruptedException e) {
					return;

				}
			}
		}
	}

	class RunThread extends Thread {

		public void run() {
			while (true) {
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
								setJProgressBarValue(value);
							}
						});
						sleep(100);
					} catch (Exception ex) {
						return;
					}
				}
			}
		}
	}

	private boolean validateData() {
		UpdateHttpConfigService servicePort = httpConfigService
				.getUpdateHttpConfigServicePort();
		HttpConfigEntity entity = servicePort.getHttpConfig();
		if (entity != null) {
			String tomcatDir = entity.getTomcatDir();
			if ("".equals(tomcatDir)) {
				JOptionPane.showMessageDialog(this,
						"Tomcat 目录名不能为空,请先进行参数设置!!", "", 2);
				return false;
			}
		}
		return true;
	}

	/**
	 * 开始升级
	 */
	private void startUpgrade() {

		setProgressShowState(true);

		if (!validateData()) {
			return;
		}
		UpgradeService servicePort = this.upgradeService
				.getUpgradeServicePort();
		if (servicePort.checkUpgrade()) {
			JOptionPane.showMessageDialog(this, "服务端另一个用户正在进行升级!!", "", 2);
			if (r == null || (r != null && !r.isAlive())) {
				r = new UpgradeInfoThread();
				r.start();
			}
			return;
		}
		setButtonState(false);
		setMessageTop("");
		setMessageBottom("");

		//
		// 起动进度条循环线程
		//
		runThread = new RunThread();
		runThread.start();
		servicePort.startUpgrade();
		if (r == null || (r != null && !r.isAlive())) {
			r = new UpgradeInfoThread();
			r.start();
		}
		closeThread(runThread);
	}

	/**
	 * stop升级程式
	 * 
	 * @return
	 */
	private void stopUpgrade() {
		UpgradeService servicePort = this.upgradeService
				.getUpgradeServicePort();
		servicePort.stopUpgrade();
		//
		// 结束进度条循环线程
		//
		closeThread(r);
		// closeThread(runThread);
	}

	/**
	 * // // 结束进度条循环线程 //
	 */
	private void closeThread(Thread t) {
		while (t != null && t.isAlive()) {
			t.interrupt();
			try {
				Thread.currentThread().sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * 设置进度是否显示
	 * 
	 */
	public void setProgressShowState(boolean isShow) {
		this.jProgressBar.setVisible(isShow);
		this.lbMessageBottom.setVisible(isShow);
	}

	public void setButtonState(boolean b) {
		this.btnUpgrade.setEnabled(b);
		this.btnHttpConfig.setEnabled(b);
	}

	/**
	 * 初始化组件 最重要的是设置进度条的最大值
	 */
	public void initJProgressBar(final int maxValue) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (jProgressBar.getMaximum() != maxValue) {
					jProgressBar.setValue(0);
					jProgressBar.setMinimum(0);
					jProgressBar.setMaximum(maxValue);
					jProgressBar.setStringPainted(true);
					jProgressBar.setIgnoreRepaint(true);
				}
			}
		});
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
	 * 设置进度条上面的提示信息
	 * 
	 * @param messageTop
	 */
	public void setMessageTop(String messageTop) {
		this.lbMessageTop.setText(messageTop);
	}

	/**
	 * 设置进度条下面的提示信息
	 * 
	 * @param messageTop
	 */
	public void setMessageBottom(String messageBottom) {
		this.lbMessageBottom.setText(messageBottom);
	}

	public void setJTextArea(String message) {
		int len = jTextArea.getText().length();
		if (len > message.length()) {
			jTextArea.setText(message);
		} else {
			this.jTextArea.append(message.substring(len));
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
