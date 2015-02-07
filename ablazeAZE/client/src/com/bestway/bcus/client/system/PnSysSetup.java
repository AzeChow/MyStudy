/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.system;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;

/**
 * @author xxm // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PnSysSetup extends JPanel {

	private static int flag = 0;

	private FlowButton jButton = null;
	private FlowButton jButton1 = null;
	private FlowButton jButton2 = null;
	private JLabel jLabel1 = null;
	private Image image = null;
	private JLabel jLabel = null;
	private FlowButton jButton11 = null;
	private FlowButton jButton3 = null;

	/**
	 * @param arg0
	 * @param arg1
	 */
	public PnSysSetup(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);

		} catch (Exception e) {

		}
	}

	public PnSysSetup(LayoutManager arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnSysSetup(boolean arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * 
	 */
	public PnSysSetup() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		jLabel = new JLabel();

		jLabel1 = new JLabel();

		setLayout(null);

		setSize(731, 462);

		Dimension panelSize = getSize();

		jLabel1.setBounds(137, 12, 133, 32);

		jLabel1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		jLabel1.setText("系统管理向导");

		jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));

		jLabel1.setForeground(new java.awt.Color(255, 153, 0));

		setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		jLabel.setBounds(279, 23, 372, 20);

		jLabel.setFont(new Font("Dialog", Font.BOLD, 12));

		jLabel.setText(CommonVars.getMainDay());

		jLabel.setForeground(new java.awt.Color(0, 102, 102));

		image = CommonVars.getImageSource().getImage("background.gif");

		add(jLabel1, null);

		add(getJButton1(), null);

		add(getJButton2(), null);
		// this.add(getJButton(), null);

		add(jLabel, null);

		add(getJButton11(), null);

		add(getJButton3(), null);

	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private FlowButton getJButton() {
		if (jButton == null) {
			jButton = new FlowButton();
			jButton.setBounds(99, 167, 112, 25);
			jButton.setActionCommand("com.bestway.bcus.client.system.FmSysDataManage");
			jButton.setText("系统数据管理");
			jButton.setContentAreaFilled(false);
			jButton.setFont(new Font("Dialog", Font.BOLD, 12));
			jButton.setForeground(new java.awt.Color(227, 145, 0));
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private FlowButton getJButton1() {

		if (jButton1 == null) {

			jButton1 = new FlowButton();

			jButton1.setBounds(100, 206, 112, 25);

			// 设置行动命令
			jButton1.setActionCommand("com.bestway.bcus.client.system.FmParameterSet");

			jButton1.setText("系统参数设置");

			jButton1.setContentAreaFilled(false);

			jButton1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 12));

			jButton1.setForeground(new java.awt.Color(227, 145, 0));

		}

		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private FlowButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new FlowButton();
			jButton2.setBounds(100, 292, 112, 25);
			jButton2.setText("授权公司设置");
			jButton2.setActionCommand("com.bestway.bcus.client.system.FmCompany");

			// 绘制内容区不填充
			jButton2.setContentAreaFilled(false);

			jButton2.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 12));
			jButton2.setForeground(new java.awt.Color(227, 145, 0));
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private FlowButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new FlowButton();
			jButton11.setBounds(new Rectangle(100, 249, 112, 25));
			jButton11
					.setActionCommand("com.bestway.bcus.client.system.FmOperationLogs");
			jButton11.setContentAreaFilled(false);
			jButton11.setText("系统日志管理");
			jButton11.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 12));
			jButton11.setForeground(new Color(227, 145, 0));
		}
		return jButton11;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private FlowButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new FlowButton();
			jButton3.setBounds(new Rectangle(100, 335, 112, 25));
			jButton3.setForeground(new Color(227, 145, 0));
			jButton3.setActionCommand("com.bestway.common.client.dataimport.FmQuerySql");
			jButton3.setContentAreaFilled(false);
			jButton3.setText("公共查询");
			jButton3.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 12));
		}
		return jButton3;
	}
} // @jve:decl-index=0:visual-constraint="28,-88"
