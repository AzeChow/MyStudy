/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.authority;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;

/**
 * @author xxm
 *
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class PnAuthority extends JPanel {

	private FlowButton jButton = null;
	private FlowButton jButton1 = null;
	private FlowButton jButton2 = null;
	private FlowButton jButton3 = null;
	private FlowButton jButton4 = null;
	private FlowLine flowLine = null;
	private JLabel jLabel1 = null;
	private Image image = null;
	private JLabel jLabel = null;
	private JPanel jPanel = null;
	private FlowLine flowLine1 = null;
	private FlowLine flowLine2 = null;
	private FlowLine flowLine3 = null;
	private FlowLine flowLine21 = null;
	private FlowButton jButton41 = null;
	private FlowLine flowLine31 = null;

	/**
	 * @param arg0
	 * @param arg1
	 */
	public PnAuthority(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnAuthority(LayoutManager arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnAuthority(boolean arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * 
	 */
	public PnAuthority() {
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

		setSize(808, 462);

		Dimension panelSize = getSize();

		jLabel1.setBounds(137, 12, 134, 32);

		jLabel1.setText("权限管理向导");

		jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));

		jLabel1.setForeground(new java.awt.Color(255, 153, 0));

		jLabel.setBounds(282, 20, 491, 24);

		jLabel.setFont(new Font("Dialog", Font.BOLD, 12));

		jLabel.setText(CommonVars.getMainDay());

		jLabel.setForeground(new java.awt.Color(0, 102, 102));

		image = CommonVars.getImageSource().getImage("background.gif");

		add(getJPanel(), null);

		add(jLabel1, null);

		add(jLabel, null);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new FlowButton();
			jButton.setActionCommand("com.bestway.bcus.client.authority.FmUser");
			jButton.setText("用户管理");
			jButton.setFont(new java.awt.Font("新宋体", java.awt.Font.PLAIN, 15));
			jButton.setBounds(new Rectangle(60, 12, 115, 30));
		}
		return jButton;
	}

	public void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		} catch (Exception e) {

		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new FlowButton();
			jButton1.setActionCommand("com.bestway.bcus.client.authority.FmGroup");
			jButton1.setText("群组管理");
			jButton1.setFont(new java.awt.Font("新宋体", java.awt.Font.PLAIN, 15));
			jButton1.setBounds(new Rectangle(60, 89, 115, 30));
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new FlowButton();
			jButton2.setText("用户群组管理");
			jButton2.setActionCommand("com.bestway.bcus.client.authority.FmGroupUser");
			jButton2.setFont(new java.awt.Font("新宋体", java.awt.Font.PLAIN, 15));
			jButton2.setBounds(new Rectangle(252, 52, 137, 30));
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new FlowButton();
			jButton3.setText("用户群组授权管理");
			jButton3.setActionCommand("com.bestway.bcus.client.authority.FmAccredit");
			jButton3.setFont(new java.awt.Font("新宋体", java.awt.Font.PLAIN, 15));
			jButton3.setBounds(new Rectangle(463, 52, 144, 30));
		}
		return jButton3;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			flowLine31 = new FlowLine();
			flowLine31.setBounds(new Rectangle(175, 166, 123, 22));
			flowLine31.setText("");
			flowLine21 = new FlowLine();
			flowLine21.setBounds(new Rectangle(308, 26, 18, 27));
			flowLine21.setArrowDirection(3);
			flowLine21.setText("");
			flowLine3 = new FlowLine();
			flowLine3.setBounds(new Rectangle(389, 58, 76, 21));
			flowLine3.setText("");
			flowLine2 = new FlowLine();
			flowLine2.setBounds(new Rectangle(300, 82, 33, 20));
			flowLine2.setText("");
			flowLine2.setArrowDirection(2);
			flowLine1 = new FlowLine();
			flowLine1.setBounds(new Rectangle(169, 92, 149, 20));
			flowLine1.setText("");
			flowLine = new FlowLine();
			flowLine1.setOnlyDrawLine(true);
			flowLine.setText("");
			flowLine.setBounds(new Rectangle(167, 16, 151, 21));
			flowLine.setOnlyDrawLine(true);
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setOpaque(false);
			jPanel.setBounds(28, 151, 622, 218);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton3(), null);
			jPanel.add(getJButton4(), null);
			jPanel.add(flowLine, null);
			jPanel.add(flowLine1, null);
			jPanel.add(flowLine2, null);
			jPanel.add(flowLine3, null);
			jPanel.add(flowLine21, null);
			jPanel.add(getJButton41(), null);
			jPanel.add(flowLine31, null);
		}
		return jPanel;
	}

	public JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new FlowButton();
			jButton4.setActionCommand("com.bestway.bcus.client.authority.FmIPaddress");
			jButton4.setText("IP地址管理");
			jButton4.setFont(new java.awt.Font("新宋体", java.awt.Font.PLAIN, 15));
			jButton4.setBounds(new Rectangle(60, 160, 115, 30));

		}
		return jButton4;
	}

	/**
	 * This method initializes jButton41
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getJButton41() {
		if (jButton41 == null) {
			jButton41 = new FlowButton();
			jButton41.setBounds(new Rectangle(297, 160, 144, 30));
			jButton41
					.setActionCommand("com.bestway.bcus.client.authority.FmIPAccredit");
			jButton41.setText("IP地址授权管理");
			jButton41.setFont(new Font("宋体", Font.PLAIN, 15));
		}
		return jButton41;
	}

} // @jve:decl-index=0:visual-constraint="28,-88"
