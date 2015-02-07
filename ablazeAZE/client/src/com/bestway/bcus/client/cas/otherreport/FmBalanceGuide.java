package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.cas.authority.CasBalanceReportAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmBalanceGuide extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JRadioButton rbBalanceInfo = null;

	private JPanel jPanel2 = null;

	private ButtonGroup group = new ButtonGroup();

	private JPanel PnBottom = null;

	private JButton btnExit = null;

	private CasBalanceReportAction casBalanceReportAction = null;

	private JRadioButton rbBalanceInfo2 = null;

	private JRadioButton rbBalanceInfoDiff = null;

	private JRadioButton rbCheckBalance = null;

	private JRadioButton rbBalanceInfoTest = null;

	private JRadioButton rbCheckBalanceOfCustom = null;

	/**
	 * This is the default constructor
	 */
	public FmBalanceGuide() {
		super();
		casBalanceReportAction = (CasBalanceReportAction) CommonVars
				.getApplicationContext().getBean("casBalanceReportAction");
		initialize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setAllRadioButtonProperty(this.jContentPane);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("其它统计报表");
		this.setSize(710, 517);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 18));
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jLabel.setText("平衡表");
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
			jLabel.setForeground(new java.awt.Color(255, 153, 0));
			jPanel.setBackground(java.awt.Color.white);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							18));
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel2.setText("");
			jLabel2
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel1, java.awt.BorderLayout.EAST);
			jPanel.add(jLabel2, java.awt.BorderLayout.WEST);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getJPanel2(), null);
			jPanel1.add(getPnBottom(), null);
			jPanel1.add(getBtnExit(), null);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jRadioButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton() {
		if (rbBalanceInfo == null) {
			rbBalanceInfo = new JRadioButton();
			rbBalanceInfo.setText("短溢表");
			rbBalanceInfo.setVisible(false);
			rbBalanceInfo.setBounds(61, 51, 64, 26);
		}
		return rbBalanceInfo;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(22, 22, 638, 255);
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"平衡表",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									java.awt.Color.black));
			jPanel2.add(getJRadioButton(), null);
			jPanel2.add(getRbBalanceInfo2(), null);
			jPanel2.add(getRbBalanceInfoDiff(), null);
			jPanel2.add(getRbCheckBalance(), null);
			jPanel2.add(getRbBalanceInfoTest(), null);
			jPanel2.add(getRbCheckBalanceOfCustom(), null);
		}
		return jPanel2;
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		PnBottom.setBounds(3, this.jPanel1.getHeight() - 50, this.jPanel1
				.getWidth() - 6, 3);
		// // jPanel5.setBounds(28, this.jPanel1.getHeight() - 50,
		// this.jPanel1.getWidth() - 28 -28, 3);
		btnExit.setBounds(this.jPanel1.getWidth() - 92, this.jPanel1
				.getHeight() - 40, 68, 26);
		//
		jPanel2.setBounds(28, 22, this.jPanel1.getWidth() - 28 - 28,
				this.jPanel1.getHeight() - 98);
	}

	protected void paintComponent(Graphics g) {
		initUIComponents();
		super.paintComponent(g);
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBottom() {
		if (PnBottom == null) {
			PnBottom = new JPanel();
			PnBottom.setBounds(24, 384, 667, 3);
			PnBottom
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return PnBottom;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(560, 394, 68, 26);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 设置所有的RadioButton的属性和事件
	 * 
	 * @param component
	 */
	private void setAllRadioButtonProperty(Component component) {
		if (!(component instanceof Container)) {
			return;
		}
		Container container = (Container) component;
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component temp = container.getComponent(i);
			if (temp instanceof JRadioButton) {
				UUID uuid = UUID.randomUUID();
				final String flag = uuid.toString();
				JRadioButton radioButton = (JRadioButton) temp;
				radioButton.setOpaque(false);
				radioButton.setActionCommand(flag);
				radioButton.addActionListener(new RadioActionListner());
				group.add(radioButton);
			} else {
				setAllRadioButtonProperty(temp);
			}
		}
	}

	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			JComponent component = (JComponent) e.getSource();
			component.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			try {

				if (rbBalanceInfo.isSelected()) { // 短溢表 (不区分国内购买)
					//
					// check authority
					//
					// casOtherReportAction.checkBalanceInfoByBrowse(new
					// Request(
					// CommonVars.getCurrUser()));
					//
					// String flag = rbBalanceInfo.getActionCommand();
					// JDialogBase d = JDialogBaseHelper
					// .getJDialogBaseByFlag(flag);
					//					
					// if (d == null) {
					// d = new FmBalanceInfo();
					// JDialogBaseHelper.putJDialogBaseToFlag(flag, d);
					// d.setVisible(true);
					// } else {
					// d.setVisibleNoChange(true);
					// }
					// d.toFront();

				} else if (rbBalanceInfoTest.isSelected()) { // 短溢表 (新版不区分国内购买)
					//
					// check authority
					//
					casBalanceReportAction
							.checkBalanceInfoByBrowse(new Request(CommonVars
									.getCurrUser()));

					rbBalanceInfoTest.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					DgBalanceInfoTest dg = new DgBalanceInfoTest();
					ShowFormControl.showForm(dg);
					rbBalanceInfoTest.setCursor(new Cursor(Cursor.HAND_CURSOR));
				} else if (rbBalanceInfo2.isSelected()) { // 短溢表(区分国内购买)
					// check authority
					//
					// casOtherReportAction.checkBalanceInfoByBrowse(new
					// Request(
					// CommonVars.getCurrUser()));
					//
					// String flag = rbBalanceInfo2.getActionCommand();
					// JDialogBase d = JDialogBaseHelper
					// .getJDialogBaseByFlag(flag);
					//			
					// if (d == null) {
					// d = new FmBalanceInfo2();
					// JDialogBaseHelper.putJDialogBaseToFlag(flag, d);
					// d.setVisible(true);
					// } else {
					// d.setVisibleNoChange(true);
					// }
					// d.toFront();

				} else if (rbBalanceInfoDiff.isSelected()) {// 短溢差额表
					//
					// check authority
					//
					// casOtherReportAction.checkBalanceInfoByBrowse(new
					// Request(
					// CommonVars.getCurrUser()));
					//
					// String flag = rbBalanceInfoDiff.getActionCommand();
					// JDialogBase d = JDialogBaseHelper
					// .getJDialogBaseByFlag(flag);
					//			
					// if (d == null) {
					// d = new FmBalanceInfoDiff();
					// JDialogBaseHelper.putJDialogBaseToFlag(flag, d);
					// d.setVisible(true);
					// } else {
					// d.setVisibleNoChange(true);
					// }
					// d.toFront();

				} else if (rbCheckBalance.isSelected()) {// 实际盘点过程（料件级）
					//
					// check authority
					//
					casBalanceReportAction.checkBalanceByBrowse(new Request(
							CommonVars.getCurrUser()));

					rbCheckBalance.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					FmCheckBalance dg = new FmCheckBalance();
					ShowFormControl.showForm(dg);
					rbCheckBalance.setCursor(new Cursor(Cursor.HAND_CURSOR));

				} else if (rbCheckBalanceOfCustom.isSelected()) {// 实际盘点过程编码级)
					//
					// check authority
					//
					casBalanceReportAction
							.checkBalanceOfCustomByBrowse(new Request(
									CommonVars.getCurrUser()));

					rbCheckBalanceOfCustom.setCursor(new Cursor(
							Cursor.WAIT_CURSOR));
					FmCheckBalanceOfCustom dg = new FmCheckBalanceOfCustom();
					ShowFormControl.showForm(dg);
					rbCheckBalanceOfCustom.setCursor(new Cursor(
							Cursor.HAND_CURSOR));

				}

			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex.getMessage());
			} finally {
				component.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}

	/**
	 * This method initializes rbBalanceInfo2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBalanceInfo2() {
		if (rbBalanceInfo2 == null) {
			rbBalanceInfo2 = new JRadioButton();
			rbBalanceInfo2.setBounds(new Rectangle(419, 51, 202, 26));
			rbBalanceInfo2.setText("短溢表 (区分国内购买)");
			rbBalanceInfo2.setVisible(false);// wss屏蔽了
		}
		return rbBalanceInfo2;
	}

	/**
	 * This method initializes rbBalanceInfoDiff
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBalanceInfoDiff() {
		if (rbBalanceInfoDiff == null) {
			rbBalanceInfoDiff = new JRadioButton();
			rbBalanceInfoDiff.setBounds(new Rectangle(61, 77, 158, 25));
			rbBalanceInfoDiff.setText("短溢差额表");
			rbBalanceInfoDiff.setVisible(false);// wss屏蔽了
		}
		return rbBalanceInfoDiff;
	}

	/**
	 * This method initializes rbCheckBalance
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCheckBalance() {
		if (rbCheckBalance == null) {
			rbCheckBalance = new JRadioButton();
			rbCheckBalance.setBounds(new Rectangle(108, 106, 189, 26));
			rbCheckBalance.setText("实际盘点过程(料件级)");
		}
		return rbCheckBalance;
	}

	/**
	 * This method initializes rbBalanceInfoTest
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBalanceInfoTest() {
		if (rbBalanceInfoTest == null) {
			rbBalanceInfoTest = new JRadioButton();

			// 长宽为0为了隐藏而又具有快捷功能
			rbBalanceInfoTest.setBounds(new Rectangle(108, 36, 189, 26));
			rbBalanceInfoTest.setText("短溢表(新版)");
			// 快捷键 Alt+E
			rbBalanceInfoTest.setMnemonic(java.awt.event.KeyEvent.VK_E);
		}
		return rbBalanceInfoTest;
	}

	/**
	 * This method initializes rbCheckBalanceOfCustom
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCheckBalanceOfCustom() {
		if (rbCheckBalanceOfCustom == null) {
			rbCheckBalanceOfCustom = new JRadioButton();
			rbCheckBalanceOfCustom.setBounds(new Rectangle(108, 176, 189, 26));
			rbCheckBalanceOfCustom.setText("实际盘点过程(编码级)");
		}
		return rbCheckBalanceOfCustom;
	}

}
