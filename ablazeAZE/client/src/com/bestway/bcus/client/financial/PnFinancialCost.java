package com.bestway.bcus.client.financial;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;

public class PnFinancialCost extends JPanel {

	private FlowButton flowButton1 = null;

	private FlowButton flowButton2 = null;

	private FlowButton flowButton3 = null;

	private FlowButton flowButton4 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private FlowLine flowLine = null;

	private FlowLine flowLine2 = null;

	private FlowLine flowLine7 = null;

	private Image image = null;

	private FlowButton flowButton31 = null;

	private FlowButton flowButton11 = null;

	private FlowButton flowButton12 = null;

	private FlowButton flowButton51 = null;

	private FlowLine flowLine1 = null;

	private FlowLine flowLine611 = null;

	private FlowLine flowLine6112 = null;

	private FlowLine flowLine32 = null;

	private FlowLine flowLine61121 = null;
	
	private FlowLine flowLine61122 = null;

	private FlowLine flowLine61123 = null;

	private FlowLine flowLine6111 = null;

	private FlowLine flowLine6113 = null;

	private FlowLine flowLine61124 = null;

	public PnFinancialCost(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// 
		initialize();
	}

	public PnFinancialCost(LayoutManager layout) {
		super(layout);
		// 
		initialize();
	}

	public PnFinancialCost(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// 
		initialize();
	}

	public PnFinancialCost() {
		super();
		// 
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
		flowLine61124 = new FlowLine();
		flowLine61124.setBounds(new Rectangle(244, 365, 50, 10));
		flowLine61124.setArrowDirection(0);
		flowLine61124.setLineDirection(2);
		flowLine61124.setOnlyDrawLine(false);
		flowLine61124.setText("");
		flowLine6113 = new FlowLine();
		flowLine6113.setBounds(new Rectangle(244, 375, 50, 10));
		flowLine6113.setArrowDirection(1);
		flowLine6113.setLineDirection(2);
		flowLine6113.setOnlyDrawLine(false);
		flowLine6113.setText("");
		flowLine6111 = new FlowLine();
		flowLine6111.setBounds(new Rectangle(448, 370, 50, 10));
		flowLine6111.setArrowDirection(1);
		flowLine6111.setLineDirection(2);
		flowLine6111.setOnlyDrawLine(false);
		flowLine6111.setText("");
		flowLine61123 = new FlowLine();
		flowLine61123.setBounds(new Rectangle(448, 267, 50, 10));
		flowLine61123.setArrowDirection(0);
		flowLine61123.setLineDirection(2);
		flowLine61123.setOnlyDrawLine(false);
		flowLine61123.setText("");
		flowLine61121 = new FlowLine();
		flowLine61121.setBounds(new Rectangle(244, 277, 50, 10));
		flowLine61121.setArrowDirection(1);
		flowLine61121.setLineDirection(2);
		flowLine61121.setOnlyDrawLine(false);
		flowLine61122 = new FlowLine();
		flowLine61122.setBounds(new Rectangle(230, 259, 34, 47));
		flowLine61122.setArrowDirection(1);
		flowLine61122.setLineDirection(2);
		flowLine61122.setOnlyDrawLine(false);
		flowLine61122.setText("");
		flowLine32 = new FlowLine();
		flowLine32.setBounds(new Rectangle(565, 296, 10, 60));
		flowLine32.setArrowDirection(2);
		flowLine32.setLineDirection(5);
		flowLine32.setOnlyDrawLine(false);
		flowLine32.setText("");
		flowLine6112 = new FlowLine();
		flowLine6112.setBounds(new Rectangle(244, 267, 50, 10));
		flowLine6112.setArrowDirection(0);
		flowLine6112.setLineDirection(2);
		flowLine6112.setOnlyDrawLine(false);
		flowLine6112.setText("");
		flowLine611 = new FlowLine();
		flowLine611.setBounds(new Rectangle(448, 277, 50, 10));
		flowLine611.setArrowDirection(1);
		flowLine611.setOnlyDrawLine(false);
		flowLine611.setLineDirection(2);
		flowLine611.setText("");
		flowLine1 = new FlowLine();
		flowLine1.setBounds(new Rectangle(162, 196, 10, 62));
		flowLine1.setArrowDirection(3);
		flowLine1.setText("");
		flowLine2 = new FlowLine();
		flowLine2.setBounds(new Rectangle(575, 296, 10, 60));
		flowLine2.setArrowDirection(3);
		flowLine2.setArrowDirection(3);
		flowLine2.setText("");
		flowLine7 = new FlowLine();
		flowLine7.setBounds(new Rectangle(336, 288, 11, 38));
		flowLine7.setText("");
//		flowLine = new FlowLine();
//		flowLine.setBounds(new Rectangle(366, 196, 11, 62));
//		flowLine.setArrowDirection(3);
//		flowLine.setText("");
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(375, 18, 252, 26));
		jLabel1.setText(CommonVars.getMainDay());
		jLabel1.setForeground(new Color(0, 102, 102));
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(201, 14, 176, 38));
		jLabel.setForeground(new Color(255, 153, 0));
		jLabel.setText("账务成本分析向导");
		jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		this.setLayout(null);
		this.setSize(659, 574);
		this.add(getFlowButton1(), null);
		this.add(getFlowButton2(), null);
//		this.add(getFlowButton3(), null);
		this.add(getFlowButton4(), null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
//		this.add(flowLine, null);
		this.add(flowLine2, null);
		this.add(getFlowButton31(), null);
		this.add(getFlowButton11(), null);
		this.add(getFlowButton12(), null);
		this.add(getFlowButton51(), null);
		this.add(flowLine1, null);
		this.add(flowLine611, null);
		this.add(flowLine6112, null);
		this.add(flowLine32, null);
		this.add(flowLine61121, null);
		this.add(flowLine61123, null);
		this.add(flowLine6111, null);
		this.add(flowLine6113, null);
		this.add(flowLine61124, null);
		image = CommonVars.getImageSource().getImage("background.gif");
	}

	/**
	 * This method initializes flowButton1
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton1() {
		if (flowButton1 == null) {
			flowButton1 = new FlowButton();
			flowButton1.setBounds(new Rectangle(294, 258, 154, 38));
			flowButton1
					.setActionCommand("com.bestway.bcus.client.financial.FmCostSummaryReport");
			flowButton1.setContentAreaFilled(false);
			flowButton1.setText("成本汇总表");
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
			flowButton2.setBounds(new Rectangle(498, 356, 154, 38));
			flowButton2.setActionCommand("com.bestway.bcus.client.financial.FmProductConsumption");
			flowButton2.setContentAreaFilled(false);
			flowButton2.setText("成品单耗调整表");
			flowButton2.setForeground(Color.gray);
			flowButton2.setBorder(null);
		}
		return flowButton2;
	}

	/**
	 * This method initializes flowButton3
	 * 
	 * @return com.bestway.xi.winuicontrol.drawflow.FlowButton
	 */
//	private FlowButton getFlowButton3() {
//		if (flowButton3 == null) {
//			flowButton3 = new FlowButton();
//			flowButton3.setBounds(new Rectangle(294, 160, 154, 38));
//			flowButton3.setActionCommand("");
//			flowButton3.setContentAreaFilled(false);
//			flowButton3.setText("成本制造费人工费表");
//			flowButton3.setBorder(null);
//		}
//		return flowButton3;
//	}

	/**
	 * This method initializes flowButton4
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton4() {
		if (flowButton4 == null) {
			flowButton4 = new FlowButton();
			flowButton4.setBounds(new Rectangle(294, 356, 154, 38));
			flowButton4
					.setActionCommand("com.bestway.bcus.client.financial.FmMaterialAccount");
			flowButton4.setContentAreaFilled(false);
			flowButton4.setText("原材料进出仓帐");
			flowButton4.setBorder(null);
		}
		return flowButton4;
	}

	/**
	 * This method initializes flowButton31
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton31() {
		if (flowButton31 == null) {
			flowButton31 = new FlowButton();
			flowButton31.setBounds(new Rectangle(90, 160, 154, 38));
			flowButton31
					.setActionCommand("com.bestway.bcus.client.financial.FmProductWeightedAveragePrice");
			flowButton31.setContentAreaFilled(false);
			flowButton31.setText("成品加权平均价");
			flowButton31.setBorder(null);
		}
		return flowButton31;
	}

	/**
	 * This method initializes flowButton11
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton11() {
		if (flowButton11 == null) {
			flowButton11 = new FlowButton();
			flowButton11.setBounds(new Rectangle(90, 258, 154, 38));
			flowButton11
					.setActionCommand("com.bestway.bcus.client.financial.FmFinishedProductCost");
			flowButton11.setContentAreaFilled(false);
			flowButton11.setText("成品成本进出仓帐");
			flowButton11.setBorder(null);
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
			flowButton12.setBounds(new Rectangle(498, 258, 154, 38));
			flowButton12
					.setActionCommand("com.bestway.bcus.client.financial.FmMonthlyProductionAndSales");
			flowButton12.setContentAreaFilled(false);
			flowButton12.setText("每月产量与销量表");
			flowButton12.setBorder(null);
		}
		return flowButton12;
	}

	/**
	 * This method initializes flowButton51
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton51() {
		if (flowButton51 == null) {
			flowButton51 = new FlowButton();
			flowButton51.setBounds(new Rectangle(90, 356, 154, 38));
			flowButton51
					.setActionCommand("com.bestway.bcus.client.financial.FmMaterialAveragePrice");
			flowButton51.setContentAreaFilled(false);
			flowButton51.setText("料件加权平均价");
			flowButton51.setBorder(null);
		}
		return flowButton51;
	}

} // @jve:decl-index=0:visual-constraint="27,67"
