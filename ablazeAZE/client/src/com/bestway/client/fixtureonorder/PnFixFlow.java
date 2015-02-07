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

/**
 * @author fhz
 *
 */
public class PnFixFlow extends JPanel {

	private static final long serialVersionUID = 1L;
	private FlowButton flowButton = null;
	private FlowLine flowLine = null;
	private FlowLine flowLine12 = null;
	private FlowLine flowLine2 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private Image image = null;
	private FlowLine flowLine21 = null;
	private FlowButton flowButton1 = null;
	private FlowButton flowButton2 = null;
	/**
	 * This is the default constructor
	 */
	public PnFixFlow() {
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
		flowLine21 = new FlowLine();
		flowLine21.setBounds(new Rectangle(239, 248, 67, 12));
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(344, 11, 227, 36));
		jLabel1.setText(CommonVars.getMainDay());
		jLabel1.setForeground(new Color(0, 102, 102));
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(119, 9, 224, 42));
		jLabel.setForeground(new Color(255, 153, 0));
		jLabel.setText("设备管理向导");
		jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		flowLine2 = new FlowLine();
		flowLine2.setBounds(new Rectangle(240, 110, 61, 14));
		flowLine12 = new FlowLine();
		flowLine12.setBounds(new Rectangle(234, 115, 11, 139));
		flowLine12.setOnlyDrawLine(true);
		flowLine12.setLineDirection(0);
		flowLine12.setArrowDirection(3);
		flowLine = new FlowLine();
		flowLine.setBounds(new Rectangle(187, 183, 54, 17));
		flowLine.setOnlyDrawLine(true);
		this.setSize(647, 419);
		this.setLayout(null);
		this.add(getFlowButton(), null);
		this.add(flowLine, null);
		this.add(flowLine12, null);
		this.add(flowLine2, null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(flowLine21, null);
		this.add(getFlowButton1(), null);
		this.add(getFlowButton2(), null);
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
			flowButton.setBounds(new Rectangle(97, 147, 91, 83));
			flowButton.setActionCommand("com.bestway.client.fixtureonorder.FmFixtureParameterSet");
			flowButton.setText("设备参数设置");
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
			flowButton1.setBounds(new Rectangle(302, 82, 105, 77));
			flowButton1.setActionCommand("com.bestway.client.fixasset.PnFixassetFlow");
			flowButton1.setText("三资设备管理");
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
			flowButton2.setBounds(new Rectangle(306, 210, 95, 82));
			flowButton2.setActionCommand("com.bestway.client.fixtureonorder.PnFixtureFlow");
			flowButton2.setText("来料设备管理");
		}
		return flowButton2;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
