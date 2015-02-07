package com.bestway.common.client.transferfactory;

import java.awt.LayoutManager;

import javax.swing.JPanel;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;

public class PnTransferFlow extends JPanel {

	private FlowButton flowButton = null;

	private FlowButton flowButton1 = null;

	private FlowButton flowButton2 = null;

	private FlowButton flowButton3 = null;

	private FlowButton flowButton4 = null;

	private FlowButton flowButton5 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private FlowLine flowLine = null;

	private FlowLine flowLine1 = null;

	private FlowLine flowLine2 = null;

	private FlowLine flowLine3 = null;

	private FlowLine flowLine4 = null;

	private FlowLine flowLine5 = null;

	private FlowLine flowLine6 = null;

	private Image image = null;

	private FlowButton flowButton6 = null;

	private FlowLine flowLine7 = null;

	private FlowLine flowLine8 = null;

	private FlowLine flowLine9 = null;

	public PnTransferFlow(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// 
		initialize();
	}

	public PnTransferFlow(LayoutManager layout) {
		super(layout);
		// 
		initialize();
	}

	public PnTransferFlow(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// 
		initialize();
	}

	public PnTransferFlow() {
		super();
		// 
		initialize();
	}

	@Override
	public void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		} catch (Exception e) {

		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		flowLine7 = new FlowLine();
		flowLine7.setBounds(new java.awt.Rectangle(353,265,48,13));
		flowLine7.setText("");
		flowLine7.setArrowDirection(0);
		flowLine8 = new FlowLine();
		flowLine8.setBounds(new java.awt.Rectangle(456,289,12,57));
		flowLine8.setText("");
		flowLine8.setArrowDirection(3);
		flowLine8.setOnlyDrawLine(true);
		flowLine8.setLineDirection(5);
		flowLine9 = new FlowLine();
		flowLine9.setBounds(new java.awt.Rectangle(352,338,113,15));
		flowLine9.setText("");
		flowLine9.setArrowDirection(1);
		flowLine6 = new FlowLine();
		flowLine6.setBounds(new java.awt.Rectangle(182,393,196,9));
		flowLine6.setArrowDirection(3);
		flowLine6.setOnlyDrawLine(true);
		flowLine6.setText("");
		flowLine5 = new FlowLine();
		flowLine5.setBounds(new java.awt.Rectangle(372,397,9,41));
		flowLine5.setArrowDirection(3);
		flowLine5.setText("");
		flowLine4 = new FlowLine();
		flowLine4.setBounds(new java.awt.Rectangle(177,396,8,41));
		flowLine4.setArrowDirection(3);
		flowLine4.setText("");
		flowLine3 = new FlowLine();
		flowLine3.setBounds(new java.awt.Rectangle(274,362,11,37));
		flowLine3.setArrowDirection(3);
		flowLine3.setOnlyDrawLine(true);
		flowLine3.setLineDirection(5);
		flowLine3.setText("");
		flowLine2 = new FlowLine();
		flowLine2.setBounds(new java.awt.Rectangle(272,288,15,38));
		flowLine2.setArrowDirection(3);
		flowLine2.setText("");
		flowLine1 = new FlowLine();
		flowLine1.setBounds(new java.awt.Rectangle(270,213,14,39));
		flowLine1.setArrowDirection(3);
		flowLine1.setText("");
		flowLine = new FlowLine();
		flowLine.setBounds(new java.awt.Rectangle(264,133,22,41));
		flowLine.setArrowDirection(3);
		flowLine.setText("");
		jLabel1 = new JLabel();
		jLabel1.setBounds(new java.awt.Rectangle(313, 18, 252, 26));
		jLabel1.setText(CommonVars.getMainDay());
		jLabel1.setForeground(new Color(0, 102, 102));
		jLabel = new JLabel();
		jLabel.setBounds(new java.awt.Rectangle(132, 11, 176, 38));
		jLabel.setForeground(new Color(255, 153, 0));
		jLabel.setText("\u8f6c\u5382\u7ba1\u7406\u5411\u5bfc");
		jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		this.setLayout(null);
		this.setSize(614, 574);
		this.add(getFlowButton(), null);
		this.add(getFlowButton1(), null);
		this.add(getFlowButton2(), null);
		this.add(getFlowButton3(), null);
		this.add(getFlowButton4(), null);
		this.add(getFlowButton5(), null);
		this.add(getFlowButton6(), null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(flowLine, null);
		this.add(flowLine1, null);
		this.add(flowLine2, null);
		this.add(flowLine3, null);
		this.add(flowLine4, null);
		this.add(flowLine5, null);
		this.add(flowLine6, null);
		this.add(flowLine7, null);
		this.add(flowLine8, null);
		this.add(flowLine9, null);
		image = CommonVars.getImageSource().getImage("background.gif");		
			flowLine7.setVisible(false);
			flowLine9.setVisible(false);
			flowLine8.setVisible(false);
	}

	/**
	 * This method initializes flowButton
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton() {
		if (flowButton == null) {
			flowButton = new FlowButton();

			flowButton.setBounds(new java.awt.Rectangle(201,174,154,38));
			flowButton
					.setActionCommand("com.bestway.common.client.transferfactory.FmTransferFactoryInitBill");
			flowButton.setContentAreaFilled(false);
			flowButton.setText("进出货转厂期初单");
			flowButton.setBorder(null);
		}
		return flowButton;
	}

	/**
	 * This method initializes flowButton1
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton1() {
		if (flowButton1 == null) {
			flowButton1 = new FlowButton();
			flowButton1.setBounds(new java.awt.Rectangle(201,250,154,38));
			flowButton1
					.setActionCommand("com.bestway.common.client.transferfactory.FmTransferFactoryBill");
			flowButton1.setContentAreaFilled(false);
			flowButton1.setText("进出货转厂单据");
			flowButton1.setBorder(null);
		}
		return flowButton1;
	}

	/**
	 * This method initializes flowButton2
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton2() {
		if (flowButton2 == null) {
			flowButton2 = new FlowButton();
			flowButton2.setBounds(new java.awt.Rectangle(201,326,154,38));
			flowButton2.setActionCommand("");
			flowButton2.setContentAreaFilled(false);
			flowButton2.setText("报关单");
			flowButton2.setBorder(null);
		}
		return flowButton2;
	}

	/**
	 * This method initializes flowButton3
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton3() {
		if (flowButton3 == null) {
			flowButton3 = new FlowButton();
			flowButton3.setBounds(new java.awt.Rectangle(201,95,154,38));
			flowButton3
					.setActionCommand("com.bestway.common.client.transferfactory.FmCustomsEnvelopBill");
			flowButton3.setContentAreaFilled(false);
			flowButton3.setText("关封管理");
			flowButton3.setBorder(null);
		}
		return flowButton3;
	}

	/**
	 * This method initializes flowButton4
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton4() {
		if (flowButton4 == null) {
			flowButton4 = new FlowButton();
			flowButton4.setBounds(new java.awt.Rectangle(120,436,121,38));
			flowButton4
					.setActionCommand("com.bestway.common.client.transferfactory.FmCustomsEnvelopSpareAnalyse");
			flowButton4.setContentAreaFilled(false);
			flowButton4.setText("关封余量分析");
			flowButton4.setBorder(null);
		}
		return flowButton4;
	}

	/**
	 * This method initializes flowButton5
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton5() {
		if (flowButton5 == null) {
			flowButton5 = new FlowButton();
			flowButton5.setBounds(new java.awt.Rectangle(318,436,131,38));
			flowButton5
					.setActionCommand("com.bestway.common.client.transferfactory.FmTransferStatisticAnalyse");
			flowButton5.setContentAreaFilled(false);
			flowButton5.setText("转厂统计分析");
			flowButton5.setBorder(null);
		}
		return flowButton5;
	}

	/**
	 * This method initializes flowButton6
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton6() {
		if (flowButton6 == null) {
			flowButton6 = new FlowButton();
			flowButton6.setBounds(new java.awt.Rectangle(400,250,135,38));
			flowButton6.setActionCommand("");
			flowButton6.setContentAreaFilled(false);
			flowButton6.setText("报关清单");
			flowButton6.setBorder(null);
			flowButton6.setVisible(false);
		}
		return flowButton6;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
