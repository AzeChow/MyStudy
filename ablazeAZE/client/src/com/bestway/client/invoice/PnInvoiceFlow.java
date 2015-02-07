package com.bestway.client.invoice;

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

public class PnInvoiceFlow extends JPanel {

	private FlowButton flowButton = null;

	private FlowButton flowButton1 = null;



	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private FlowLine flowLine = null;


	private Image image = null;

	public PnInvoiceFlow(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initialize();
	}

	public PnInvoiceFlow(LayoutManager layout) {
		super(layout);
		initialize();
	}

	public PnInvoiceFlow(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initialize();
	}

	public PnInvoiceFlow() {
		super();
		initialize();
	}

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
		flowLine = new FlowLine();
		flowLine.setBounds(new java.awt.Rectangle(264,182,22,56));
		flowLine.setArrowDirection(3);
		flowLine.setText("");
		jLabel1 = new JLabel();
		jLabel1.setBounds(new java.awt.Rectangle(313, 18, 252, 26));
		jLabel1.setText(CommonVars.getMainDay());
		jLabel1.setForeground(new Color(0, 102, 102));
		jLabel = new JLabel();
		jLabel.setBounds(new java.awt.Rectangle(132, 11, 176, 38));
		jLabel.setForeground(new Color(255, 153, 0));
		jLabel.setText("出口专用发票管理");
		jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		this.setLayout(null);
		this.setSize(614, 574);
		this.add(getFlowButton(), null);
		this.add(getFlowButton1(), null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(flowLine, null);

		image = CommonVars.getImageSource().getImage("background.gif");		
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

			flowButton.setBounds(new java.awt.Rectangle(178,239,199,38));
			flowButton
					.setActionCommand("com.bestway.client.invoice.FmInvoiceObtainStat");
			flowButton.setContentAreaFilled(false);
			flowButton.setText("出口商品发票领用存统计表");
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
			flowButton1.setBounds(new java.awt.Rectangle(201,144,154,38));
			flowButton1
					.setActionCommand("com.bestway.client.invoice.FmInvoice");
			flowButton1.setContentAreaFilled(false);
			flowButton1.setText("出口专用发票管理");
			flowButton1.setBorder(null);
		}
		return flowButton1;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
