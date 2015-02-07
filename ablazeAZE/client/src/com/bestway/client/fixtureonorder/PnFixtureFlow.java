package com.bestway.client.fixtureonorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;
import java.awt.Dimension;

/**
 * @author fhz
 *
 */
public class PnFixtureFlow extends JPanel {

	private static final long serialVersionUID = 1L;
	private FlowButton flowButton = null;
	private FlowLine flowLine1 = null;
	private FlowButton flowButton31 = null;
	private FlowButton flowButton32 = null;
	private FlowButton flowButton332 = null;
	private FlowButton flowButton333 = null;
	private FlowLine flowLine11 = null;
	private FlowLine flowLine12 = null;
	private FlowLine flowLine13 = null;
	private FlowLine flowLine3 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private Image image = null;
	private FlowLine flowLine131 = null;
	private FlowLine flowLine31 = null;
	private FlowLine flowLine1311 = null;
	private FlowLine flowLine13111 = null;
	/**
	 * This is the default constructor
	 */
	public PnFixtureFlow() {
		super();
		initialize();
		image = CommonVars.getImageSource().getImage("background.gif");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		flowLine13111 = new FlowLine();
		flowLine13111.setBounds(new Rectangle(176, 416, 142, 12));
		flowLine13111.setArrowDirection(1);
		flowLine1311 = new FlowLine();
		flowLine1311.setBounds(new Rectangle(355, 359, 10, 47));
		flowLine1311.setArrowDirection(3);
		flowLine31 = new FlowLine();
		flowLine31.setBounds(new Rectangle(125, 351, 241, 14));
		flowLine31.setOnlyDrawLine(true);
		flowLine131 = new FlowLine();
		flowLine131.setBounds(new Rectangle(120, 357, 11, 46));
		flowLine131.setArrowDirection(3);
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(344, 11, 227, 36));
		jLabel1.setText(CommonVars.getMainDay());
		jLabel1.setForeground(new Color(0, 102, 102));
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(119, 9, 224, 42));
		jLabel.setForeground(new Color(255, 153, 0));
		jLabel.setText("来料设备管理向导");
		jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		flowLine3 = new FlowLine();
		flowLine3.setBounds(new Rectangle(124, 311, 235, 15));
		flowLine3.setOnlyDrawLine(true);
		flowLine13 = new FlowLine();
		flowLine13.setBounds(new Rectangle(232, 319, 17, 37));
		flowLine13.setArrowDirection(3);
		flowLine12 = new FlowLine();
		flowLine12.setBounds(new Rectangle(120, 273, 11, 46));
		flowLine12.setOnlyDrawLine(true);
		flowLine12.setLineDirection(0);
		flowLine12.setArrowDirection(3);
		flowLine11 = new FlowLine();
		flowLine11.setBounds(new Rectangle(353, 281, 11, 39));
		flowLine11.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		flowLine11.setOnlyDrawLine(true);
		flowLine11.setLineDirection(0);
		flowLine11.setArrowDirection(3);
		flowLine1 = new FlowLine();
		flowLine1.setBounds(new Rectangle(351, 166, 9, 59));
		flowLine1.setArrowDirection(3);
		this.setSize(647, 504);
		this.setLayout(null);
		this.add(getFlowButton(), null);
		this.add(flowLine1, null);
		this.add(getFlowButton31(), null);
		this.add(getFlowButton32(), null);
		this.add(getFlowButton332(), null);
		this.add(getFlowButton333(), null);
		this.add(flowLine11, null);
		this.add(flowLine12, null);
		this.add(flowLine13, null);
		this.add(flowLine3, null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(flowLine131, null);
		this.add(flowLine31, null);
		this.add(flowLine1311, null);
		this.add(flowLine13111, null);
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.FlowButton	
	 */    
	 public void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        } catch (Exception e) {

        }
    }
	/**
	 * This method initializes flowButton	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton() {
		if (flowButton == null) {
			flowButton = new FlowButton();
			flowButton.setBounds(new Rectangle(304, 74, 107, 90));
			flowButton.setActionCommand("com.bestway.client.fixtureonorder.FmFixtureContract");
			flowButton.setText("设备协议");
		}
		return flowButton;
	}

	/**
	 * This method initializes flowButton31	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton31() {
		if (flowButton31 == null) {
			flowButton31 = new FlowButton();
			flowButton31.setBounds(new Rectangle(285, 224, 131, 59));
			flowButton31.setEnabled(true);
			flowButton31.setActionCommand("");
			flowButton31.setText("特殊报关单");
		}
		return flowButton31;
	}

	/**
	 * This method initializes flowButton32	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton32() {
		if (flowButton32 == null) {
			flowButton32 = new FlowButton();
			flowButton32.setBounds(new Rectangle(56, 241, 149, 31));
			flowButton32.setActionCommand("com.bestway.client.fixtureonorder.FmFixtureLocation");
			flowButton32.setText("设备位置存放表");
		}
		return flowButton32;
	}

	/**
	 * This method initializes flowButton332	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton332() {
		if (flowButton332 == null) {
			flowButton332 = new FlowButton();
			flowButton332.setBounds(new Rectangle(90, 403, 91, 31));
			flowButton332.setActionCommand("com.bestway.client.fixtureonorder.FmFixtureContractReport");
			flowButton332.setText("报表");
		}
		return flowButton332;
	}

	/**
	 * This method initializes flowButton333	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton333() {
		if (flowButton333 == null) {
			flowButton333 = new FlowButton();
			flowButton333.setBounds(new Rectangle(313, 405, 102, 40));
			flowButton333.setActionCommand("com.bestway.client.fixtureonorder.FmFixtureChangeLocation");
			flowButton333.setText("设备异动");
		}
		return flowButton333;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
