/*
 * Created on 2004-9-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fecav;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Font;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmFecavStatReport extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton2 = null;

	private JRadioButton jRadioButton3 = null;

	private JRadioButton jRadioButton4 = null;

	private ButtonGroup group = new ButtonGroup();

	private JPanel PnBottom = null;

	private JButton btnExit = null;

	private JRadioButton jRadioButton5 = null;

	private FecavAction fecavAction = null;

	/**
	 * This is the default constructor
	 */
	public FmFecavStatReport() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("外汇核销单统计报表");
		this.setSize(710, 517);
		this.setContentPane(getJContentPane());
		group.add(jRadioButton);
		group.add(jRadioButton1);
		group.add(jRadioButton2);
		group.add(jRadioButton3);
		group.add(jRadioButton4);
		group.add(jRadioButton5);

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
			jLabel.setText("外汇核销单统计报表");
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
			jPanel1.add(getPnBottom(), null);
			jPanel1.add(getBtnExit(), null);
			jPanel1.add(getJRadioButton(), null);
			jPanel1.add(getJRadioButton1(), null);
			jPanel1.add(getJRadioButton2(), null);
			jPanel1.add(getJRadioButton3(), null);
			jPanel1.add(getJRadioButton4(), null);
			jPanel1.add(getJRadioButton5(), null);
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
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("汇票收支余情况表");
			jRadioButton.setBounds(75, 84, 132, 21);
			jRadioButton.addActionListener(new RadioActionListner());
		}
		return jRadioButton;
	}

	/**
	 * 
	 * This method initializes jRadioButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setText("核销单明细表");
			jRadioButton1.setBounds(75, 58, 136, 24);
			jRadioButton1.addActionListener(new RadioActionListner());
		}
		return jRadioButton1;
	}

	/**
	 * 
	 * This method initializes jRadioButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setText("外汇核销合同汇总表");
			jRadioButton2.setBounds(75, 109, 168, 21);
			jRadioButton2.addActionListener(new RadioActionListner());
		}
		return jRadioButton2;
	}

	/**
	 * 
	 * This method initializes jRadioButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setText("出口收汇核销单使用状况表");
			jRadioButton3.setBounds(75, 177, 192, 21);
			jRadioButton3.addActionListener(new RadioActionListner());
		}
		return jRadioButton3;
	}

	/**
	 * 
	 * This method initializes jRadioButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setText("核销签收汇总表");
			jRadioButton4.setBounds(75, 151, 122, 24);
			jRadioButton4.addActionListener(new RadioActionListner());
		}
		return jRadioButton4;
	}

	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (jRadioButton.isSelected()) { // 汇票收支余情况表
				DgBillOfExchangeStat dg = new DgBillOfExchangeStat();
				dg.setVisible(true);
			} else if (jRadioButton1.isSelected()) {// 外汇核销单明细表
				FecavQuery.getInstance().getFecavBillDetail();
			} else if (jRadioButton2.isSelected()) {// 外汇核销合同汇总表
				DgCancelContractStat dg = new DgCancelContractStat();
				dg.setVisible(true);
			} else if (jRadioButton3.isSelected()) {// 出口收汇核销单使用状况表
				DgCancelBillUseStat dg = new DgCancelBillUseStat();
				dg.setVisible(true);
			} else if (jRadioButton4.isSelected()) {// 核销签收汇总表
				DgCavSignInStat dg = new DgCavSignInStat();
				dg.setVisible(true);
			}
		}
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
		// jPanel2.setBounds(28, 22, this.jPanel1.getWidth() - 28 - 28, 167);
		// jPanel3.setBounds(28, 202, this.jPanel1.getWidth() - 28-28, 154);
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
	 * This method initializes jRadioButton5
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton5() {
		if (jRadioButton5 == null) {
			jRadioButton5 = new JRadioButton();
			jRadioButton5.setText("核销单登记表");
			jRadioButton5.setBounds(new java.awt.Rectangle(75, 220, 107, 21));
			jRadioButton5
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgCancelList dg = new DgCancelList();
							dg.setVisible(true);

						}
					});
		}
		return jRadioButton5;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
