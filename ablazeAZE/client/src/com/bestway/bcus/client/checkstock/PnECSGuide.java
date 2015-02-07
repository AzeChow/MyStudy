package com.bestway.bcus.client.checkstock;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PnECSGuide extends JPanel{
	private JLabel jLabel1 = null;
	private JLabel jLabel = null;
	private Image image = null;
	private JPanel jPanel = null;
	private FlowButton flowButton=null;
	private FlowButton flowButton_1=null;
	private FlowButton flowButton_2=null;
	private FlowButton flowButton_3=null;
	private FlowButton flowButton_4=null;
	private FlowButton flowButton_5=null;
	private FlowButton flowButton_6=null;
	private FlowButton flowButton_7=null;
	private FlowButton flowButton_8=null;
	private FlowButton flowButton_9=null;
	private FlowButton flowButton_10=null;
	private FlowButton flowButton_11=null;
	private FlowButton flowButton_12=null;
	private FlowButton flowButton_13=null;
	private FlowLine flowLine = null;
	private FlowLine flowLine_1 = null;
	private FlowLine flowLine_2 = null;
	private FlowLine flowLine_3 = null;
	private FlowLine flowLine_4 = null;
	private FlowLine flowLine_5 = null;
	private FlowLine flowLine_6 = null;
	private FlowLine flowLine_7 = null;
	private FlowLine flowLine_8 = null;
	private FlowLine flowLine_9 = null;
	private FlowLine flowLine_10 = null;
	private FlowLine flowLine_11 = null;
	private FlowLine flowLine_12 = null;
	private FlowLine flowLine_13 = null;
	private FlowLine flowLine_14 = null;
	private FlowLine flowLine_15 = null;
	private FlowLine flowLine_16 = null;
	private FlowLine flowLine_17 = null;
	private FlowLine flowLine_18 = null;
	private FlowLine flowLine_19 = null;
	private FlowLine flowLine_20 = null;
	private FlowLine flowLine_21 = null;
	private FlowLine flowLine_22 = null;
	private FlowLine flowLine_23 = null;
	private FlowLine flowLine_24 = null;
	private FlowLine flowLine_25 = null;
	private FlowLine flowLine_26 = null;
	private FlowLine flowLine_27 = null;
	private FlowLine flowLine_28 = null;
	private FlowLine flowLine_29 = null;
	private FlowLine flowLine_30 = null;
	private FlowLine flowLine_31 = null;
	private FlowLine flowLine_32 = null;
	private FlowLine flowLine_33 = null;
	
	public PnECSGuide() {
		super();
		initialize();
		
	}
	private void initialize() {
		jLabel = new JLabel();
		jLabel1 = new JLabel();
		this.setLayout(null);
		this.setSize(850, 530);
		
		jLabel1.setBounds(137, 12, 247, 32);
		jLabel1.setText("盘点核查(账册)管理向导");
		jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		jLabel1.setForeground(new java.awt.Color(255, 153, 0));
		jLabel.setBounds(394, 21, 314, 21);
		jLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		jLabel.setText(CommonVars.getMainDay());
		jLabel.setForeground(new java.awt.Color(0, 102, 102));
		image = CommonVars.getImageSource().getImage("background.gif");
		this.add(getJPanel(), null);
		this.add(jLabel1, null);
		this.add(jLabel, null);
		
	}
	
	public void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			Container container = this.getParent();
			this.setBounds(0, 0, container.getWidth(), container.getHeight());
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
			int x = (this.getWidth() - jPanel.getWidth()) / 2;
			int y = (this.getHeight() - jPanel.getHeight()) / 2;
			if (x < 0) {
				x = 0;
			}
			if (y < 0) {
				y = 0;
			}
			jPanel.setBounds(x, y, 850, 480);

		} catch (Exception e) {

		}
	}
	
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setOpaque(false);
			jPanel.setBounds(10, 54, 829, 466);
			jPanel.setLayout(null);
			jPanel.add(getFlowButton());
			jPanel.add(getFlowButton_1());
			jPanel.add(getFlowButton_2());	
			jPanel.add(getFlowButton_3());		
			jPanel.add(getFlowButton_4());
			jPanel.add(getFlowButton_5());
			jPanel.add(getFlowButton_6());
			jPanel.add(getFlowButton_7());
			jPanel.add(getFlowButton_8());
			jPanel.add(getFlowButton_9());
			jPanel.add(getFlowButton_10());
			jPanel.add(getFlowButton_11());
			jPanel.add(getFlowButton_12());
			jPanel.add(getFlowButton_13());
			//箭头
			flowLine = new FlowLine();
			flowLine.setBounds(new Rectangle(349, 40, 121, 38));
			flowLine.setArrowDirection(3);
			flowLine.setText("");
			jPanel.add(flowLine,null);
			
			flowLine_1 = new FlowLine();
			flowLine_1.setText("");
			flowLine_1.setArrowDirection(1);
			flowLine_1.setOnlyDrawLine(true);
			flowLine_1.setBounds(137, 15, 206, 22);
			jPanel.add(flowLine_1);
			
			flowLine_2 = new FlowLine();
			flowLine_2.setText("");
			flowLine_2.setArrowDirection(3);
			flowLine_2.setBounds(76, 26, 121, 50);
			jPanel.add(flowLine_2);
			
			flowLine_3 = new FlowLine();
			flowLine_3.setText("");
			flowLine_3.setArrowDirection(3);
			flowLine_3.setLineDirection(5);
			flowLine_3.setOnlyDrawLine(true);
			flowLine_3.setBounds(76, 107, 121, 22);
			jPanel.add(flowLine_3);
			
			flowLine_4 = new FlowLine();
			flowLine_4.setText("");
			flowLine_4.setOnlyDrawLine(true);
			flowLine_4.setArrowDirection(1);
			flowLine_4.setBounds(66, 119, 139, 22);
			jPanel.add(flowLine_4);
			
			flowLine_5 = new FlowLine();
			flowLine_5.setText("");
			flowLine_5.setArrowDirection(3);
			flowLine_5.setBounds(6, 130, 121, 33);
			jPanel.add(flowLine_5);
			
			flowLine_6 = new FlowLine();
			flowLine_6.setText("");
			flowLine_6.setArrowDirection(3);
			flowLine_6.setBounds(145, 130, 121, 33);
			jPanel.add(flowLine_6);
			
			flowLine_7 = new FlowLine();
			flowLine_7.setText("");
			flowLine_7.setOnlyDrawLine(true);
			flowLine_7.setArrowDirection(1);
			flowLine_7.setBounds(66, 220, 139, 22);
			jPanel.add(flowLine_7);
			
			flowLine_8 = new FlowLine();
			flowLine_8.setText("");
			flowLine_8.setOnlyDrawLine(true);
			flowLine_8.setArrowDirection(1);
			flowLine_8.setBounds(341, 119, 139, 22);
			jPanel.add(flowLine_8);
			
			flowLine_9 = new FlowLine();
			flowLine_9.setText("");
			flowLine_9.setOnlyDrawLine(true);
			flowLine_9.setArrowDirection(1);
			flowLine_9.setBounds(341, 220, 139, 22);
			jPanel.add(flowLine_9);
			
			flowLine_10 = new FlowLine();
			flowLine_10.setText("");
			flowLine_10.setOnlyDrawLine(true);
			flowLine_10.setArrowDirection(1);
			flowLine_10.setBounds(612, 119, 139, 22);
			jPanel.add(flowLine_10);
			
			flowLine_11 = new FlowLine();
			flowLine_11.setText("");
			flowLine_11.setOnlyDrawLine(true);
			flowLine_11.setArrowDirection(1);
			flowLine_11.setBounds(612, 220, 139, 22);
			jPanel.add(flowLine_11);
			
			flowLine_12 = new FlowLine();
			flowLine_12.setText("");
			flowLine_12.setArrowDirection(3);
			flowLine_12.setLineDirection(5);
			flowLine_12.setOnlyDrawLine(true);
			flowLine_12.setBounds(6, 196, 121, 35);
			jPanel.add(flowLine_12);
			
			flowLine_13 = new FlowLine();
			flowLine_13.setText("");
			flowLine_13.setOnlyDrawLine(true);
			flowLine_13.setLineDirection(5);
			flowLine_13.setArrowDirection(3);
			flowLine_13.setBounds(145, 196, 121, 35);
			jPanel.add(flowLine_13);
			
			flowLine_14 = new FlowLine();
			flowLine_14.setText("");
			flowLine_14.setArrowDirection(3);
			flowLine_14.setBounds(76, 232, 121, 33);
			jPanel.add(flowLine_14);
			
			flowLine_15 = new FlowLine();
			flowLine_15.setText("");
			flowLine_15.setOnlyDrawLine(true);
			flowLine_15.setLineDirection(5);
			flowLine_15.setArrowDirection(3);
			flowLine_15.setBounds(349, 107, 121, 24);
			jPanel.add(flowLine_15);
			
			flowLine_16 = new FlowLine();
			flowLine_16.setText("");
			flowLine_16.setArrowDirection(3);
			flowLine_16.setBounds(280, 130, 121, 22);
			jPanel.add(flowLine_16);
			
			flowLine_17 = new FlowLine();
			flowLine_17.setText("");
			flowLine_17.setArrowDirection(3);
			flowLine_17.setBounds(420, 130, 121, 22);
			jPanel.add(flowLine_17);
			
			flowLine_18 = new FlowLine();
			flowLine_18.setText("");
			flowLine_18.setArrowDirection(3);
			flowLine_18.setOnlyDrawLine(true);
			flowLine_18.setLineDirection(5);
			flowLine_18.setBounds(280, 209, 121, 22);
			jPanel.add(flowLine_18);
			
			flowLine_19 = new FlowLine();
			flowLine_19.setText("");
			flowLine_19.setArrowDirection(3);
			flowLine_19.setOnlyDrawLine(true);
			flowLine_19.setLineDirection(5);
			flowLine_19.setBounds(420, 209, 121, 22);
			jPanel.add(flowLine_19);
			
			flowLine_20 = new FlowLine();
			flowLine_20.setText("");
			flowLine_20.setArrowDirection(3);
			flowLine_20.setBounds(349, 232, 121, 33);
			jPanel.add(flowLine_20);
			
			flowLine_21 = new FlowLine();
			flowLine_21.setText("");
			flowLine_21.setOnlyDrawLine(true);
			flowLine_21.setArrowDirection(1);
			flowLine_21.setBounds(468, 15, 212, 22);
			jPanel.add(flowLine_21);
			
			flowLine_22 = new FlowLine();
			flowLine_22.setText("");
			flowLine_22.setArrowDirection(3);
			flowLine_22.setBounds(620, 26, 121, 50);
			jPanel.add(flowLine_22);
			
			flowLine_23 = new FlowLine();
			flowLine_23.setText("");
			flowLine_23.setArrowDirection(3);
			flowLine_23.setBounds(551, 130, 121, 33);
			jPanel.add(flowLine_23);
			
			flowLine_24 = new FlowLine();
			flowLine_24.setText("");
			flowLine_24.setArrowDirection(3);
			flowLine_24.setBounds(690, 130, 121, 33);
			jPanel.add(flowLine_24);
			
			flowLine_25 = new FlowLine();
			flowLine_25.setText("");
			flowLine_25.setOnlyDrawLine(true);
			flowLine_25.setLineDirection(5);
			flowLine_25.setArrowDirection(3);
			flowLine_25.setBounds(620, 105, 121, 24);
			jPanel.add(flowLine_25);
			
			flowLine_26 = new FlowLine();
			flowLine_26.setText("");
			flowLine_26.setOnlyDrawLine(true);
			flowLine_26.setLineDirection(5);
			flowLine_26.setArrowDirection(3);
			flowLine_26.setBounds(551, 196, 121, 35);
			jPanel.add(flowLine_26);
			
			flowLine_27 = new FlowLine();
			flowLine_27.setText("");
			flowLine_27.setOnlyDrawLine(true);
			flowLine_27.setLineDirection(5);
			flowLine_27.setArrowDirection(3);
			flowLine_27.setBounds(690, 196, 121, 35);
			jPanel.add(flowLine_27);
			
			flowLine_28 = new FlowLine();
			flowLine_28.setText("");
			flowLine_28.setArrowDirection(3);
			flowLine_28.setBounds(620, 232, 121, 33);
			jPanel.add(flowLine_28);
			
			flowLine_29 = new FlowLine();
			flowLine_29.setText("");
			flowLine_29.setArrowDirection(3);
			flowLine_29.setBounds(349, 296, 121, 76);
			jPanel.add(flowLine_29);
			
			flowLine_30 = new FlowLine();
			flowLine_30.setText("");
			flowLine_30.setOnlyDrawLine(true);
			flowLine_30.setLineDirection(5);
			flowLine_30.setArrowDirection(3);
			flowLine_30.setBounds(76, 296, 121, 95);
			jPanel.add(flowLine_30);
			
			flowLine_31 = new FlowLine();
			flowLine_31.setText("");
			flowLine_31.setOnlyDrawLine(true);
			flowLine_31.setLineDirection(5);
			flowLine_31.setArrowDirection(3);
			flowLine_31.setBounds(620, 298, 121, 95);
			jPanel.add(flowLine_31);
			
			flowLine_32 = new FlowLine();
			flowLine_32.setText("");
			flowLine_32.setArrowDirection(0);
			flowLine_32.setBounds(136, 380, 207, 22);
			jPanel.add(flowLine_32);
			
			flowLine_33 = new FlowLine();
			flowLine_33.setText("");
			flowLine_33.setArrowDirection(1);
			flowLine_33.setBounds(470, 380, 210, 22);
			jPanel.add(flowLine_33);
		}
		return jPanel;
	}
	
	private FlowButton getFlowButton() {
		if (flowButton == null) {
			flowButton = new FlowButton();
			flowButton.setForeground(new Color(6, 73, 201));
			flowButton.setBottomFilledColor(new Color(168, 244, 255));
			flowButton.setText("盘点核销批次设定");
			flowButton.setBounds(new Rectangle(344, 10, 126, 33));
			flowButton
					.setActionCommand("com.bestway.bcus.client.checkstock.FmECSSection");
		}
		return flowButton;
	}
	
	private FlowButton getFlowButton_1() {
		if (flowButton_1 == null) {
			flowButton_1 = new FlowButton();
			flowButton_1.setForeground(new Color(6, 73, 201));
			flowButton_1.setBottomFilledColor(new Color(168, 244, 255));
			flowButton_1.setBounds(new Rectangle(80, 76, 110, 33));
			flowButton_1.setText("帐册数据分析");
		}
		return flowButton_1;
	}
	
	private FlowButton getFlowButton_2() {
		if (flowButton_2 == null) {
			flowButton_2=new FlowButton();
			flowButton_2.setText("原料情况统计表");
			flowButton_2.setBounds(new Rectangle(10, 164, 110, 33));
			flowButton_2.setActionCommand("com.bestway.bcus.client.checkstock.contractanalyse.FmECSCustomsCountImg");
		}
		return flowButton_2;
	}
	
	private FlowButton getFlowButton_3() {
		if (flowButton_3 == null) {
			flowButton_3=new FlowButton();
			flowButton_3.setText("成品情况统计表");
			flowButton_3.setBounds(148, 164, 110, 33);
			flowButton_3.setActionCommand("com.bestway.bcus.client.checkstock.contractanalyse.FmECSDeclarationCommInfoExg");
		}
		return flowButton_3;
	}
	
	private FlowButton getFlowButton_4() {
		if (flowButton_4 == null) {
			flowButton_4=new FlowButton();
			flowButton_4.setText("账册分析");
			flowButton_4.setBounds(80, 264, 110, 33);
			flowButton_4.setActionCommand("com.bestway.bcus.client.checkstock.contractanalyse.FmECSContractAnalyse");
		}
		return flowButton_4;
	}
	
	private FlowButton getFlowButton_5() {
		if (flowButton_5 == null) {
			flowButton_5=new FlowButton();
			flowButton_5.setForeground(new Color(6, 73, 201));
			flowButton_5.setBottomFilledColor(new Color(168, 244, 255));
			flowButton_5.setText("盘点数据分析");
			flowButton_5.setBounds(344, 76, 126, 33);
		}
		return flowButton_5;
	}
	
	private FlowButton getFlowButton_6() {
		if (flowButton_6 == null) {
			flowButton_6=new FlowButton();
			flowButton_6.setBounds(292, 150, 97, 60);
			flowButton_6.setBorder(new EmptyBorder(0,5,0,0));
			flowButton_6.setText("<html>2料件、在产品、在途、内购、厂外存放库存<html>");
		}
		return flowButton_6;
	}
	
	private FlowButton getFlowButton_7() {
		if (flowButton_7 == null) {
			flowButton_7=new FlowButton();
			flowButton_7.setText("成品、外发库存");
			flowButton_7.setBounds(418, 150, 97, 60);
			flowButton_7.setActionCommand("");
		}
		return flowButton_7;
	}
	private FlowButton getFlowButton_8() {
		if (flowButton_8 == null) {
			flowButton_8=new FlowButton();
			flowButton_8.setText("盘点总库存");
			flowButton_8.setBounds(350, 264, 110, 33);
			flowButton_8.setActionCommand("com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockAnalyse");
		}
		return flowButton_8;
	}
	private FlowButton getFlowButton_9() {
		if (flowButton_9 == null) {
			flowButton_9=new FlowButton();
			flowButton_9.setForeground(new Color(6, 73, 201));
			flowButton_9.setBottomFilledColor(new Color(168, 244, 255));
			flowButton_9.setText("结转数据分析");
			flowButton_9.setBounds(624, 76, 110, 33);
		}
		return flowButton_9;
	}
	private FlowButton getFlowButton_10() {
		if (flowButton_10 == null) {
			flowButton_10=new FlowButton();
			flowButton_10.setText("料件结转差额表");
			flowButton_10.setBounds(551, 164, 110, 33);
			flowButton_10.setActionCommand("com.bestway.bcus.client.checkstock.transferanalyse.FmECSTransferImgBalance");
		}
		return flowButton_10;
	}
	private FlowButton getFlowButton_11() {
		if (flowButton_11 == null) {
			flowButton_11=new FlowButton();
			flowButton_11.setText("成品结转差额表");
			flowButton_11.setBounds(694, 164, 110, 33);
			flowButton_11.setActionCommand("com.bestway.bcus.client.checkstock.transferanalyse.FmECSTransferExgBalance");
		}
		return flowButton_11;
	}
	private FlowButton getFlowButton_12() {
		if (flowButton_12 == null) {
			flowButton_12=new FlowButton();
			flowButton_12.setText("结转差额总表");
			flowButton_12.setBounds(624, 264, 110, 33);
			flowButton_12.setActionCommand("com.bestway.bcus.client.checkstock.transferanalyse.FmECSTransferCollectBalance");
		}
		return flowButton_12;
	}
	private FlowButton getFlowButton_13() {
		if (flowButton_13 == null) {
			flowButton_13=new FlowButton();
			flowButton_13.setText("短溢分析");
			flowButton_13.setForeground(new Color(6, 73, 201));
			flowButton_13.setBottomFilledColor(new Color(168, 244, 255));
			flowButton_13.setBounds(344, 371, 126, 33);
			flowButton_13.setActionCommand("com.bestway.bcus.client.checkstock.FmECSAnalyse");
		}
		return flowButton_13;
	}
}