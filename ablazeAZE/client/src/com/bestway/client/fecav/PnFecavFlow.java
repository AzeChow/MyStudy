package com.bestway.client.fecav;

import java.awt.LayoutManager;

import javax.swing.JPanel;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.bestway.bcus.client.common.CommonVars;

public class PnFecavFlow extends JPanel {

	private FlowButton flowButton = null;
	private FlowButton flowButton1 = null;
	private FlowButton flowButton2 = null;
	private FlowButton flowButton3 = null;
	private FlowButton flowButton4 = null;
	private FlowButton flowButton5 = null;
	private FlowButton flowButton6 = null;
	private FlowButton flowButton7 = null;
	private FlowButton flowButton8 = null;
	private FlowButton flowButton9 = null;
	private FlowButton flowButton10 = null;
	private FlowButton flowButton11 = null;
	private FlowButton flowButton12 = null;
	private FlowButton flowButton13 = null;
	private FlowLine flowLine = null;
	private FlowLine flowLine1 = null;
	private FlowLine flowLine2 = null;
	private FlowLine flowLine3 = null;
	private FlowLine flowLine4 = null;
	private FlowLine flowLine5 = null;
	private FlowLine flowLine6 = null;
	private FlowLine flowLine7 = null;
	private FlowLine flowLine8 = null;
	private FlowLine flowLine9 = null;
	private FlowLine flowLine10 = null;
	private FlowLine flowLine11 = null;
	private FlowLine flowLine12 = null;
	private FlowLine flowLine13 = null;
	private FlowLine flowLine14 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private Image image = null;

	public PnFecavFlow(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initialize();
	}

	public PnFecavFlow(LayoutManager layout) {
		super(layout);
		initialize();
	}

	public PnFecavFlow(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initialize();
	}

	public PnFecavFlow() {
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
		jLabel1.setBounds(new java.awt.Rectangle(364,14,265,30));
		jLabel1.setText(CommonVars.getMainDay());
		jLabel1.setForeground(new Color(0, 102, 102));
		jLabel = new JLabel();
		jLabel.setBounds(new java.awt.Rectangle(138,12,227,36));
		jLabel.setForeground(new Color(255, 153, 0));
		jLabel.setText("外汇核销管理向导");
		jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		flowLine14 = new FlowLine();
		flowLine14.setBounds(new java.awt.Rectangle(492,281,13,27));
		flowLine14.setArrowDirection(2);
		flowLine14.setText("");
		flowLine13 = new FlowLine();
		flowLine13.setBounds(new java.awt.Rectangle(189,361,21,8));
		flowLine13.setOnlyDrawLine(true);
		flowLine13.setText("");
		flowLine12 = new FlowLine();
		flowLine12.setBounds(new java.awt.Rectangle(188,286,22,11));
		flowLine12.setOnlyDrawLine(true);
		flowLine12.setText("");
		flowLine11 = new FlowLine();
		flowLine11.setBounds(new java.awt.Rectangle(202,290,14,75));
		flowLine11.setArrowDirection(3);
		flowLine11.setOnlyDrawLine(true);
		flowLine11.setLineDirection(5);
		flowLine11.setText("");
		flowLine10 = new FlowLine();
		flowLine10.setBounds(new java.awt.Rectangle(494,384,13,23));
		flowLine10.setArrowDirection(3);
		flowLine10.setText("");
		flowLine9 = new FlowLine();
		flowLine9.setBounds(new java.awt.Rectangle(494,336,13,19));
		flowLine9.setArrowDirection(3);
		flowLine9.setText("");
		flowLine8 = new FlowLine();
		flowLine8.setBounds(new java.awt.Rectangle(320,285,13,26));
		flowLine8.setArrowDirection(3);
		flowLine8.setText("");
		flowLine7 = new FlowLine();
		flowLine7.setBounds(new java.awt.Rectangle(320,240,13,22));
		flowLine7.setArrowDirection(3);
		flowLine7.setText("");
		flowLine6 = new FlowLine();
		flowLine6.setBounds(new java.awt.Rectangle(320,194,13,21));
		flowLine6.setArrowDirection(3);
		flowLine6.setText("");
		flowLine5 = new FlowLine();
		flowLine5.setBounds(new java.awt.Rectangle(320,101,13,21));
		flowLine5.setArrowDirection(3);
		flowLine5.setText("");
		flowLine4 = new FlowLine();
		flowLine4.setBounds(new java.awt.Rectangle(397,318,33,13));
		flowLine4.setText("");
		flowLine3 = new FlowLine();
		flowLine3.setBounds(new java.awt.Rectangle(209,317,42,13));
		flowLine3.setText("");
		flowLine2 = new FlowLine();
		flowLine2.setBounds(new java.awt.Rectangle(396,81,32,14));
		flowLine2.setText("");
		flowLine1 = new FlowLine();
		flowLine1.setBounds(new java.awt.Rectangle(320,146,13,22));
		flowLine1.setArrowDirection(3);
		flowLine1.setText("");
		flowLine = new FlowLine();
		flowLine.setBounds(new java.awt.Rectangle(189,82,64,11));
		flowLine.setText("");
		this.setLayout(null);
		this.setSize(731, 495);
		this.add(getFlowButton(), null);
		this.add(getFlowButton1(), null);
		this.add(getFlowButton2(), null);
		this.add(getFlowButton3(), null);
		this.add(getFlowButton4(), null);
		this.add(getFlowButton5(), null);
		this.add(getFlowButton6(), null);
		this.add(getFlowButton7(), null);
		this.add(getFlowButton8(), null);
		this.add(getFlowButton9(), null);
		this.add(getFlowButton10(), null);
		this.add(getFlowButton11(), null);
		this.add(getFlowButton12(), null);
		this.add(getFlowButton13(), null);
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
		this.add(flowLine10, null);
		this.add(flowLine11, null);
		this.add(flowLine12, null);
		this.add(flowLine13, null);
		this.add(flowLine14, null);
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
			flowButton.setBounds(new java.awt.Rectangle(75,72,116,29));
			flowButton.setActionCommand("com.bestway.client.fecav.FmOuterObtainFecavBill");
			flowButton.setText("外部领单");
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
			flowButton1.setBounds(new java.awt.Rectangle(252,72,146,29));
			flowButton1.setActionCommand("com.bestway.client.fecav.FmInnerObtainFecavBill");
			flowButton1.setText("内部领单");
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
			flowButton2.setBounds(new java.awt.Rectangle(252,118,146,29));
			flowButton2.setActionCommand("com.bestway.client.fecav.FmUseFecavBill");
			flowButton2.setText("核销单使用");
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
			flowButton3.setBounds(new java.awt.Rectangle(74,276,116,29));
			flowButton3.setActionCommand("com.bestway.client.fecav.FmUseImportFecavBill");
			flowButton3.setText("进口白单管制");
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
			flowButton4.setBounds(new java.awt.Rectangle(252,166,146,29));
			flowButton4.setActionCommand("com.bestway.client.fecav.FmDebentureSignInFecavBill");
			flowButton4.setText("核销单退税联签收");
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
			flowButton5.setBounds(new java.awt.Rectangle(252,212,146,29));
			flowButton5.setActionCommand("com.bestway.client.fecav.FmHandInFecavBill");
			flowButton5.setText("核销单交单明细");
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
			flowButton6.setBounds(new java.awt.Rectangle(73,351,116,29));
			flowButton6.setActionCommand("com.bestway.client.fecav.FmBillOfExchange");
			flowButton6.setText("银行汇票录入");
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
			flowButton7.setBounds(new java.awt.Rectangle(252,308,146,29));
			flowButton7.setActionCommand("com.bestway.client.fecav.FmStrikeFecavBill");
			flowButton7.setText("核销单冲销");
		}
		return flowButton7;
	}

	/**
	 * This method initializes flowButton8	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton8() {
		if (flowButton8 == null) {
			flowButton8 = new FlowButton();
			flowButton8.setBounds(new java.awt.Rectangle(428,308,148,29));
			flowButton8.setActionCommand("com.bestway.client.fecav.FmCavFecavBill");
			flowButton8.setText("核销单核销");
		}
		return flowButton8;
	}

	/**
	 * This method initializes flowButton9	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton9() {
		if (flowButton9 == null) {
			flowButton9 = new FlowButton();
			flowButton9.setBounds(new java.awt.Rectangle(427,356,148,29));
			flowButton9.setActionCommand("com.bestway.client.fecav.FmFinanceSignInFecavBill");
			flowButton9.setText("核销单财务签收");
		}
		return flowButton9;
	}

	/**
	 * This method initializes flowButton10	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton10() {
		if (flowButton10 == null) {
			flowButton10 = new FlowButton();
			flowButton10.setBounds(new java.awt.Rectangle(428,407,148,29));
			flowButton10.setActionCommand("com.bestway.client.fecav.FmCloseAccountFecavBill");
			flowButton10.setText("核销单关账");
		}
		return flowButton10;
	}

	/**
	 * This method initializes flowButton11	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton11() {
		if (flowButton11 == null) {
			flowButton11 = new FlowButton();
			flowButton11.setBounds(new java.awt.Rectangle(428,73,148,29));
			flowButton11.setActionCommand("com.bestway.client.fecav.FmBlankOutFecavBill");
			flowButton11.setText("核销单遗失作废管理");
		}
		return flowButton11;
	}

	/**
	 * This method initializes flowButton12	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton12() {
		if (flowButton12 == null) {
			flowButton12 = new FlowButton();
			flowButton12.setBounds(new java.awt.Rectangle(252,258,146,29));
			flowButton12.setActionCommand("com.bestway.client.fecav.FmControlFecavBill");
			flowButton12.setText("出口收汇管制");
		}
		return flowButton12;
	}

	/**
	 * This method initializes flowButton13	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton13() {
		if (flowButton13 == null) {
			flowButton13 = new FlowButton();
			flowButton13.setBounds(new java.awt.Rectangle(428,253,148,29));
			flowButton13.setActionCommand("com.bestway.client.fecav.FmFecavStatReport");
			flowButton13.setText("统计报表");
		}
		return flowButton13;
	}

}  //  @jve:decl-index=0:visual-constraint="5,17"
