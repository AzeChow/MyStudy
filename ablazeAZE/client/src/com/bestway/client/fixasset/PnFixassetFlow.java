package com.bestway.client.fixasset;

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

public class PnFixassetFlow extends JPanel {

	private static final long serialVersionUID = 1L;
	private FlowButton flowButton = null;
	private FlowLine flowLine = null;
	private FlowButton flowButton1 = null;
	private FlowButton flowButton2 = null;
	private FlowButton flowButton3 = null;
	private FlowLine flowLine1 = null;
	private FlowButton flowButton31 = null;
	private FlowButton flowButton32 = null;
	private FlowButton flowButton33 = null;
	private FlowButton flowButton331 = null;
	private FlowButton flowButton332 = null;
	private FlowButton flowButton333 = null;
	private FlowLine flowLine11 = null;
	private FlowLine flowLine12 = null;
	private FlowLine flowLine13 = null;
	private FlowLine flowLine2 = null;
	private FlowLine flowLine3 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private Image image = null;
	/**
	 * This is the default constructor
	 */
	public PnFixassetFlow() {
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
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(344, 11, 227, 36));
		jLabel1.setText(CommonVars.getMainDay());
		jLabel1.setForeground(new Color(0, 102, 102));
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(119, 9, 224, 42));
		jLabel.setForeground(new Color(255, 153, 0));
		jLabel.setText("设备管理向导");
		jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		flowLine3 = new FlowLine();
		flowLine3.setBounds(new Rectangle(124, 311, 235, 15));
		flowLine3.setOnlyDrawLine(true);
		flowLine2 = new FlowLine();
		flowLine2.setBounds(new Rectangle(414, 248, 32, 14));
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
		flowLine = new FlowLine();
		flowLine.setBounds(new Rectangle(170, 105, 91, 17));
		this.setSize(647, 419);
		this.setLayout(null);
		this.add(getFlowButton(), null);
		this.add(flowLine, null);
		this.add(getFlowButton1(), null);
		this.add(getFlowButton2(), null);
		this.add(getFlowButton3(), null);
		this.add(flowLine1, null);
		this.add(getFlowButton31(), null);
		this.add(getFlowButton32(), null);
		this.add(getFlowButton33(), null);
		this.add(getFlowButton331(), null);
		this.add(getFlowButton332(), null);
		this.add(getFlowButton333(), null);
		this.add(flowLine11, null);
		this.add(flowLine12, null);
		this.add(flowLine13, null);
		this.add(flowLine2, null);
		this.add(flowLine3, null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
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
			flowButton.setBounds(new Rectangle(80, 77, 91, 83));
			flowButton.setActionCommand("com.bestway.client.fixasset.FmAgreement");
			flowButton.setText("设备批文");
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
			flowButton1.setBounds(new Rectangle(260, 72, 192, 30));
			flowButton1.setActionCommand("com.bestway.client.fixasset.FmAgreementInvoice");
			flowButton1.setText("商务发票");
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
			flowButton2.setBounds(new Rectangle(260, 104, 192, 30));
			flowButton2.setActionCommand("com.bestway.client.fixasset.FmAgreementWar");
			flowButton2.setText("进口全新设备保证书");
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
			flowButton3.setBounds(new Rectangle(260, 136, 192, 30));
			flowButton3.setActionCommand("com.bestway.client.fixasset.FmDutyCert");
			flowButton3.setText("进出口货物征免税证明");
		}
		return flowButton3;
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
			flowButton32.setActionCommand("com.bestway.client.fixasset.FmFixassetLocation");
			flowButton32.setText("设备位置存放表");
		}
		return flowButton32;
	}

	/**
	 * This method initializes flowButton33	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton33() {
		if (flowButton33 == null) {
			flowButton33 = new FlowButton();
			flowButton33.setBounds(new Rectangle(447, 214, 160, 29));
			flowButton33.setActionCommand("com.bestway.client.fixasset.FmFixassetRemainMoneyQuery");
			flowButton33.setText("设备金额(余额)查询");
		}
		return flowButton33;
	}

	/**
	 * This method initializes flowButton331	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton331() {
		if (flowButton331 == null) {
			flowButton331 = new FlowButton();
			flowButton331.setBounds(new Rectangle(447, 246, 160, 29));
			flowButton331.setActionCommand("com.bestway.client.fixasset.FmFixassetStatusQuery");
			flowButton331.setText("设备状态(进度)查询");
		}
		return flowButton331;
	}

	/**
	 * This method initializes flowButton332	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton332() {
		if (flowButton332 == null) {
			flowButton332 = new FlowButton();
			flowButton332.setBounds(new Rectangle(447, 278, 160, 29));
			flowButton332.setActionCommand("com.bestway.client.fixasset.FmFixassetCustomsDeclarationStat");
			flowButton332.setText("报关单查询");
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
			flowButton333.setBounds(new Rectangle(189, 354, 102, 40));
			flowButton333.setActionCommand("com.bestway.client.fixasset.FmFixassetChangeLocation");
			flowButton333.setText("设备异动");
		}
		return flowButton333;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
