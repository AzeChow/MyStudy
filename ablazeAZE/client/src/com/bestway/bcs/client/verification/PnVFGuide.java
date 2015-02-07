package com.bestway.bcs.client.verification;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PnVFGuide extends JPanel{
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
	private FlowButton flowButton_14=null;
	private FlowButton flowButton_15=null;
	private FlowButton flowButton_16=null;
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
	
	public PnVFGuide() {
		super();
		initialize();
		
	}
	private void initialize() {
		jLabel = new JLabel();
		jLabel1 = new JLabel();
		this.setLayout(null);
		this.setSize(810, 530);
		
		jLabel1.setBounds(137, 12, 247, 32);
		jLabel1.setText("核查分析(手册)管理向导");
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
			jPanel.setBounds(x, y, 800, 466);

		} catch (Exception e) {

		}
	}
	
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setOpaque(false);
			jPanel.setBounds(0, 50, 800, 466);
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
			jPanel.add(getFlowButton_14());
			jPanel.add(getFlowButton_15());
			jPanel.add(getFlowButton_16());
			//箭头
			flowLine = new FlowLine();
			flowLine.setBounds(new Rectangle(300, 40, 121, 20));
			flowLine.setArrowDirection(3);
			flowLine.setText("");
			jPanel.add(flowLine,null);
			
			flowLine_1 = new FlowLine();
			flowLine_1.setText("");
			flowLine_1.setArrowDirection(3);
			flowLine_1.setBounds(300, 91, 121, 20);
			jPanel.add(flowLine_1);
			
			flowLine_2 = new FlowLine();
			flowLine_2.setText("");
			flowLine_2.setArrowDirection(1);
			flowLine_2.setOnlyDrawLine(true);
			flowLine_2.setBounds(69, 115, 233, 20);
			jPanel.add(flowLine_2);
			
			flowLine_3 = new FlowLine();
			flowLine_3.setText("");
			flowLine_3.setArrowDirection(3);
			flowLine_3.setBounds(8, 125, 121, 45);
			jPanel.add(flowLine_3);
			
			flowLine_4 = new FlowLine();
			flowLine_4.setText("");
			flowLine_4.setArrowDirection(3);
			flowLine_4.setBounds(8, 200, 121, 20);
			jPanel.add(flowLine_4);
			
			flowLine_5 = new FlowLine();
			flowLine_5.setText("");
			flowLine_5.setArrowDirection(3);
			flowLine_5.setLineDirection(5);
			flowLine_5.setBounds(8, 249, 121, 71);
			flowLine_5.setOnlyDrawLine(true);
			jPanel.add(flowLine_5);
			
			flowLine_6 = new FlowLine();
			flowLine_6.setText("");
			flowLine_6.setOnlyDrawLine(true);
			flowLine_6.setArrowDirection(1);
			flowLine_6.setBounds(69, 310, 126, 20);
			jPanel.add(flowLine_6);
			
			flowLine_7 = new FlowLine();
			flowLine_7.setText("");
			flowLine_7.setArrowDirection(3);
			flowLine_7.setBounds(135, 125, 121, 45);
			jPanel.add(flowLine_7);
			
			flowLine_8 = new FlowLine();
			flowLine_8.setText("");
			flowLine_8.setArrowDirection(3);
			flowLine_8.setBounds(135, 202, 121, 20);
			jPanel.add(flowLine_8);
			
			flowLine_9 = new FlowLine();
			flowLine_9.setText("");
			flowLine_9.setArrowDirection(3);
			flowLine_9.setBounds(135, 249, 121, 20);
			jPanel.add(flowLine_9);
			
			flowLine_10 = new FlowLine();
			flowLine_10.setText("");
			flowLine_10.setArrowDirection(3);
			flowLine_10.setOnlyDrawLine(true);
			flowLine_10.setLineDirection(5);
			flowLine_10.setBounds(135, 300, 121, 20);
			jPanel.add(flowLine_10);
			
			flowLine_11 = new FlowLine();
			flowLine_11.setText("");
			flowLine_11.setArrowDirection(3);
			flowLine_11.setBounds(72, 320, 121, 20);
			jPanel.add(flowLine_11);
			
			flowLine_12 = new FlowLine();
			flowLine_12.setText("");
			flowLine_12.setArrowDirection(3);
			flowLine_12.setBounds(266, 145, 121, 25);
			jPanel.add(flowLine_12);
			
			flowLine_13 = new FlowLine();
			flowLine_13.setText("");
			flowLine_13.setArrowDirection(3);
			flowLine_13.setBounds(355, 145, 121, 25);
			jPanel.add(flowLine_13);
			
			flowLine_14 = new FlowLine();
			flowLine_14.setText("");
			flowLine_14.setArrowDirection(3);
			flowLine_14.setOnlyDrawLine(true);
			flowLine_14.setLineDirection(5);
			flowLine_14.setBounds(377, 200, 76, 39);
			jPanel.add(flowLine_14);
			
			flowLine_15 = new FlowLine();
			flowLine_15.setText("");
			flowLine_15.setArrowDirection(3);
			flowLine_15.setOnlyDrawLine(true);
			flowLine_15.setLineDirection(5);
			flowLine_15.setBounds(280, 220, 91, 100);
			jPanel.add(flowLine_15);
			
			flowLine_16 = new FlowLine();
			flowLine_16.setText("");
			flowLine_16.setOnlyDrawLine(true);
			flowLine_16.setLineDirection(5);
			flowLine_16.setArrowDirection(3);
			flowLine_16.setBounds(379, 288, 76, 32);
			jPanel.add(flowLine_16);
			
			flowLine_17 = new FlowLine();
			flowLine_17.setText("");
			flowLine_17.setOnlyDrawLine(true);
			flowLine_17.setArrowDirection(1);
			flowLine_17.setBounds(326, 310, 92, 20);
			jPanel.add(flowLine_17);
			
			flowLine_18 = new FlowLine();
			flowLine_18.setText("");
			flowLine_18.setOnlyDrawLine(true);
			flowLine_18.setArrowDirection(1);
			flowLine_18.setBounds(365, 228, 51, 20);
			jPanel.add(flowLine_18);
			
			flowLine_19 = new FlowLine();
			flowLine_19.setText("");
			flowLine_19.setArrowDirection(3);
			flowLine_19.setBounds(305, 238, 121, 102);
			jPanel.add(flowLine_19);
			
			flowLine_20 = new FlowLine();
			flowLine_20.setText("");
			flowLine_20.setOnlyDrawLine(true);
			flowLine_20.setArrowDirection(0);
			flowLine_20.setBounds(424, 115, 277, 20);
			jPanel.add(flowLine_20);
			
			flowLine_21 = new FlowLine();
			flowLine_21.setText("");
			flowLine_21.setBounds(new Rectangle(300, 40, 121, 20));
			flowLine_21.setArrowDirection(3);
			flowLine_21.setBounds(509, 125, 121, 45);
			jPanel.add(flowLine_21);
			
			flowLine_22 = new FlowLine();
			flowLine_22.setText("");
			flowLine_22.setBounds(new Rectangle(300, 40, 121, 20));
			flowLine_22.setArrowDirection(3);
			flowLine_22.setBounds(640, 125, 121, 45);
			jPanel.add(flowLine_22);
			
			flowLine_23 = new FlowLine();
			flowLine_23.setText("");
			flowLine_23.setOnlyDrawLine(true);
			flowLine_23.setLineDirection(5);
			flowLine_23.setArrowDirection(3);
			flowLine_23.setBounds(530, 200, 76, 39);
			jPanel.add(flowLine_23);
			
			flowLine_24 = new FlowLine();
			flowLine_24.setText("");
			flowLine_24.setOnlyDrawLine(true);
			flowLine_24.setLineDirection(5);
			flowLine_24.setArrowDirection(3);
			flowLine_24.setBounds(662, 200, 76, 39);
			jPanel.add(flowLine_24);
			
			flowLine_25 = new FlowLine();
			flowLine_25.setText("");
			flowLine_25.setOnlyDrawLine(true);
			flowLine_25.setArrowDirection(1);
			flowLine_25.setBounds(568, 228, 133, 20);
			jPanel.add(flowLine_25);
			
			flowLine_26 = new FlowLine();
			flowLine_26.setText("");
			flowLine_26.setBounds(new Rectangle(300, 40, 121, 20));
			flowLine_26.setArrowDirection(3);
			flowLine_26.setBounds(569, 238, 121, 102);
			jPanel.add(flowLine_26);
			
			flowLine_27 = new FlowLine();
			flowLine_27.setText("");
			flowLine_27.setOnlyDrawLine(true);
			flowLine_27.setLineDirection(5);
			flowLine_27.setArrowDirection(3);
			flowLine_27.setBounds(72, 366, 121, 71);
			jPanel.add(flowLine_27);
			
			flowLine_28 = new FlowLine();
			flowLine_28.setText("");
			flowLine_28.setArrowDirection(0);
			flowLine_28.setBounds(133, 420, 167, 33);
			jPanel.add(flowLine_28);
			
			flowLine_29 = new FlowLine();
			flowLine_29.setText("");
			flowLine_29.setArrowDirection(3);
			flowLine_29.setBounds(305, 366, 121, 52);
			jPanel.add(flowLine_29);
			
			flowLine_30 = new FlowLine();
			flowLine_30.setText("");
			flowLine_30.setOnlyDrawLine(true);
			flowLine_30.setLineDirection(5);
			flowLine_30.setArrowDirection(3);
			flowLine_30.setBounds(569, 366, 121, 71);
			jPanel.add(flowLine_30);
			
			flowLine_31 = new FlowLine();
			flowLine_31.setText("");
			flowLine_31.setArrowDirection(1);
			flowLine_31.setBounds(424, 420, 206, 33);
			jPanel.add(flowLine_31);
			
		}
		return jPanel;
	}
	
	private FlowButton getFlowButton() {
		if (flowButton == null) {
			flowButton = new FlowButton();	
			flowButton.setForeground(new Color(6, 73, 201));
			flowButton.setBottomFilledColor(new Color(168, 244, 255));
			flowButton.setBounds(new Rectangle(300, 10, 126, 33));
			flowButton
					.setActionCommand("com.bestway.bcs.client.bcsinnermerge.FmBcsTenInnerMerge");
			flowButton.setText("报关商品资料");
		}
		return flowButton;
	}
	
	private FlowButton getFlowButton_1(){
		if(flowButton_1 == null){
			flowButton_1=new FlowButton();
			flowButton_1.setText("物料与报关对应表");
			flowButton_1.setForeground(new Color(6, 73, 201));
			flowButton_1.setBottomFilledColor(new Color(168, 244, 255));
			flowButton_1.setBounds(new Rectangle(300, 60, 126, 33));
			flowButton_1.setActionCommand("com.bestway.bcs.client.bcsinnermerge.FmBcsInnerMerge");
		}
		return flowButton_1;
	}
	private FlowButton getFlowButton_2() {
		if (flowButton_2 == null) {
			flowButton_2 = new FlowButton();
			flowButton_2.setForeground(new Color(6, 73, 201));
			flowButton_2.setBottomFilledColor(new Color(168, 244, 255));
			flowButton_2.setBounds(new Rectangle(300, 110, 126, 33));
			flowButton_2.setText("核查批次设定");
			flowButton_2.setActionCommand("com.bestway.bcs.client.verification.FmVFSection");
		}
		return flowButton_2;
	}
	private FlowButton getFlowButton_3() {
		if (flowButton_3 == null) {
			flowButton_3 = new FlowButton();
			flowButton_3.setBounds(10, 170, 110, 33);
			flowButton_3.setText("3料件报关明细表");
			flowButton_3.setActionCommand("com.bestway.bcs.client.verification.contractanalyse.FmVFDeclarationCommInfoImg");
		}
		return flowButton_3;
	}
	private FlowButton getFlowButton_4() {
		if (flowButton_4 == null) {
			flowButton_4 = new FlowButton();
			flowButton_4.setBounds(10, 220, 110, 33);
			flowButton_4.setText("2料件数据统计表");
			flowButton_4.setActionCommand("com.bestway.bcs.client.verification.contractanalyse.FmVFCustomsCountImg");
		}
		return flowButton_4;
	}
	private FlowButton getFlowButton_5() {
		if (flowButton_5 == null) {
			flowButton_5 = new FlowButton();
			flowButton_5.setBounds(140, 170, 110, 33);
			flowButton_5.setText("3成品报关明细表");
			flowButton_5.setActionCommand("com.bestway.bcs.client.verification.contractanalyse.FmVFDeclarationCommInfoExg");
		}
		return flowButton_5;
	}
	private FlowButton getFlowButton_6() {
		if (flowButton_6 == null) {
			flowButton_6 = new FlowButton();
			flowButton_6.setBounds(140, 220, 110, 33);
			flowButton_6.setText("2成品数据统计表");
			flowButton_6.setActionCommand("com.bestway.bcs.client.verification.contractanalyse.FmVFCustomsCountExg");
		}
		return flowButton_6;
	}
	private FlowButton getFlowButton_7() {
		if (flowButton_7 == null) {
			flowButton_7 = new FlowButton();
			flowButton_7.setBounds(140, 270, 110, 33);
			flowButton_7.setText("2成品折算统计表");
			flowButton_7.setActionCommand("com.bestway.bcs.client.verification.contractanalyse.FmVFCustomsCountExgConvert");
		}
		return flowButton_7;
	}
	private FlowButton getFlowButton_8() {
		if (flowButton_8 == null) {
			flowButton_8 = new FlowButton();
			flowButton_8.setBounds(80, 340, 110, 33);
			flowButton_8.setText("1合同分析");
			flowButton_8.setActionCommand("com.bestway.bcs.client.verification.contractanalyse.FmVFContractAnalyse");
		}
		return flowButton_8;
	}
	private FlowButton getFlowButton_9() {
		if (flowButton_9 == null) {
			flowButton_9 = new FlowButton();
			flowButton_9.setBounds(270, 170, 97, 52);
			flowButton_9.setBorder(new EmptyBorder(0,5,0,0));
			flowButton_9.setText("<html>2料件、在产品、内购、厂外、在途库存<html>");
			flowButton_9.setActionCommand("");
		}
		return flowButton_9;
	}
	private FlowButton getFlowButton_10() {
		if (flowButton_10 == null) {
			flowButton_10 = new FlowButton();
			flowButton_10.setBounds(390, 170, 110, 33);
			flowButton_10.setText("2成品折料库存");
			flowButton_10.setActionCommand("com.bestway.bcs.client.verification.factoryanalyse.FmVFStockExg");
		}
		return flowButton_10;
	}
	private FlowButton getFlowButton_11() {
		if (flowButton_11 == null) {
			flowButton_11 = new FlowButton();
			flowButton_11.setBounds(379, 260, 126, 33);
			flowButton_11.setText("2外发成品折料库存");
			flowButton_11.setActionCommand("com.bestway.bcs.client.verification.factoryanalyse.FmVFStockOutSendImg");
		}
		return flowButton_11;
	}
	private FlowButton getFlowButton_12() {
		if (flowButton_12 == null) {
			flowButton_12 = new FlowButton();
			flowButton_12.setBounds(310, 340, 110, 33);
			flowButton_12.setText("1工厂库存汇总");
			flowButton_12.setActionCommand("com.bestway.bcs.client.verification.factoryanalyse.FmVFStockAnalyse");
		}
		return flowButton_12;
	}
	private FlowButton getFlowButton_13() {
		if (flowButton_13 == null) {
			flowButton_13 = new FlowButton();
			flowButton_13.setBounds(520, 170, 110, 33);
			flowButton_13.setText("2料件结转差额");
			flowButton_13.setActionCommand("com.bestway.bcs.client.verification.transferanalyse.FmVFTransferDiffImg");
		}
		return flowButton_13;
	}
	private FlowButton getFlowButton_14() {
		if (flowButton_14 == null) {
			flowButton_14 = new FlowButton();
			flowButton_14.setBounds(640, 170, 126, 33);
			flowButton_14.setText("2成品结转差额折料");
			flowButton_14.setActionCommand("com.bestway.bcs.client.verification.transferanalyse.FmVFTransferDiffExg");
		}
		return flowButton_14;
	}
	private FlowButton getFlowButton_15() {
		if (flowButton_15 == null) {
			flowButton_15 = new FlowButton();
			flowButton_15.setBounds(580, 340, 110, 33);
			flowButton_15.setText("1结转差额汇总");
			flowButton_15.setActionCommand("com.bestway.bcs.client.verification.transferanalyse.FmVFTransferDiffCount");
		}
		return flowButton_15;
	}
	private FlowButton getFlowButton_16() {
		if (flowButton_16 == null) {
			flowButton_16 = new FlowButton();
			flowButton_16.setBottomFilledColor(new Color(168, 244, 255));
			flowButton_16.setForeground(new Color(6, 73, 201));
			flowButton_16.setBounds(300, 415, 126, 33);
			flowButton_16.setText("0短溢分析");
			flowButton_16.setActionCommand("com.bestway.bcs.client.verification.FmVFAnalyse");
		}
		return flowButton_16;
	}
}
