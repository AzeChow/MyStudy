package com.bestway.bcus.client.cas;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;
import java.awt.Rectangle;

public class PnCas extends JPanel {

	private FlowButton flowButton = null;
	private FlowButton flowButton3 = null;
	private FlowButton flowButton4 = null;
	private FlowButton flowButton5 = null;
	private FlowButton flowButton6 = null;
	private FlowButton flowButton7 = null;

	private FlowLine flowLine5 = null;
	private FlowLine flowLine4 = null;
	private FlowLine flowLine6 = null;
	private FlowLine flowLine7 = null;
	private FlowLine flowLine8 = null;
	private Image image = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	
	public PnCas(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initialize();
	}

	public PnCas(LayoutManager layout) {
		super(layout);
		initialize();
	}

	public PnCas(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initialize();
	}

	public PnCas() {
		super();
		initialize();
		image = CommonVars.getImageSource().getImage("background.gif");
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
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel1 = new JLabel();
		jLabel1.setBounds(new java.awt.Rectangle(332,16,266,25));
		jLabel1.setText(CommonVars.getMainDay());
		jLabel1.setForeground(new Color(0, 102, 102));
		jLabel = new JLabel();
		jLabel.setBounds(new java.awt.Rectangle(137,11,186,42));
		jLabel.setForeground(new Color(255, 153, 0));
		jLabel.setText("海关帐");
		jLabel.setFont(new Font("Dialog", Font.BOLD, 20));

		flowLine8 = new FlowLine();
		flowLine8.setBounds(new java.awt.Rectangle(362,161,13,147));
		flowLine8.setArrowDirection(3);
		flowLine8.setOnlyDrawLine(true);
		flowLine8.setLineDirection(5);
		flowLine8.setText("");
		flowLine7 = new FlowLine();
		flowLine7.setBounds(new java.awt.Rectangle(368,302,30,13));
		flowLine7.setArrowDirection(3);
		flowLine7.setOnlyDrawLine(true);
		flowLine7.setText("");
		flowLine6 = new FlowLine();
		flowLine6.setBounds(new java.awt.Rectangle(368,155,30,13));
		flowLine6.setArrowDirection(3);
		flowLine6.setOnlyDrawLine(true);
		flowLine6.setText("");
		flowLine4 = new FlowLine();
		flowLine4.setBounds(new java.awt.Rectangle(155,225,49,20));
		flowLine4.setText("");
		flowLine5 = new FlowLine();
		flowLine5.setBounds(new java.awt.Rectangle(310,223,49,20));
		flowLine5.setText("");
		this.setLayout(null);
		this.setSize(696, 480);
		this.add(getFlowButton(), null);
		this.add(getFlowButton3(), null);
		this.add(getFlowButton4(), null);
		this.add(getFlowButton5(), null);
		this.add(getFlowButton6(), null);
		this.add(getFlowButton7(), null);
		this.add(flowLine5, null);
		this.add(flowLine4, null);
		this.add(flowLine6, null);
		this.add(flowLine7, null);
		this.add(flowLine8, null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
	}

	/**
	 * This method initializes flowButton	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton() {
		if (flowButton == null) {
			flowButton = new FlowButton();
			flowButton.setBounds(new Rectangle(51, 214, 102, 40));
			flowButton.setActionCommand("com.bestway.bcus.client.cas.parameter.FmCasParameterSet");
			flowButton.setText("参数设置");
		}
		return flowButton;
	}

	/**
	 * This method initializes flowButton3	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton3() {
		if (flowButton3 == null) {
			flowButton3 = new FlowButton();
			flowButton3.setBounds(new java.awt.Rectangle(210,214,102,40));
			flowButton3.setActionCommand("com.bestway.common.client.erpbillcenter.FmBill");
			flowButton3.setText("工厂单据管理");
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
			flowButton4.setBounds(new java.awt.Rectangle(400,140,120,40));
			flowButton4.setActionCommand("com.bestway.bcus.client.cas.FmFactoryQueryGuide");
			flowButton4.setText("工厂资料统计报表");
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
			flowButton5.setBounds(new java.awt.Rectangle(400,190,120,40));
			flowButton5.setActionCommand("com.bestway.bcus.client.cas.FmCustomQueryGuide");
			flowButton5.setText("海关资料统计报表");
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
			flowButton6.setBounds(new java.awt.Rectangle(400,240,120,40));
			flowButton6.setActionCommand("com.bestway.bcus.client.cas.finance.FmFinanceQueryGuide");
			flowButton6.setText("海关财务报表");
		}
		return flowButton6;
	}

	/**
	 * This method initializes flowButton7	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton7() {
		if (flowButton7 == null) {
			flowButton7 = new FlowButton();
			flowButton7.setBounds(new java.awt.Rectangle(400,290,120,40));
			flowButton7.setActionCommand("com.bestway.bcus.client.cas.otherreport.FmOtherQueryGuide");
			flowButton7.setText("其他报表");
		}
		return flowButton7;
	}


}  //  @jve:decl-index=0:visual-constraint="0,-25"
