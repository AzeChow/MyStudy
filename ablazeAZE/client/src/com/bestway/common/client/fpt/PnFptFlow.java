package com.bestway.common.client.fpt;

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
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PnFptFlow extends JPanel {

	private FlowButton flowButton1 = null;

	private FlowButton flowButton2 = null;

	private FlowButton flowButton3 = null;

	private FlowButton flowButton4 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private FlowLine flowLine = null;

	private FlowLine flowLine2 = null;

	private FlowLine flowLine3 = null;

	private FlowLine flowLine4 = null;

	private FlowLine flowLine6 = null;

	private Image image = null;

	private FlowButton flowButton31 = null;

	private FlowButton flowButton11 = null;

	private FlowButton flowButton12 = null;

	private FlowButton flowButton51 = null;

	private FlowLine flowLine1 = null;

	private FlowLine flowLine31 = null;

	private FlowLine flowLine611 = null;

	private FlowLine flowLine6111 = null;

	private FlowLine flowLine6112 = null;

	private FlowLine flowLine32 = null;

	private FlowButton flowButton32 = null;

	private FlowButton flowButton321 = null;

	private FlowButton flowButton33 = null;

	private FlowLine flowLine7 = null;

	private FlowButton flowButton21 = null;

	private FlowLine flowLine311 = null;

	private FlowLine flowLine61121 = null;

	public PnFptFlow(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// 
		initialize();
	}

	public PnFptFlow(LayoutManager layout) {
		super(layout);
		// 
		initialize();
	}

	public PnFptFlow(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// 
		initialize();
	}

	public PnFptFlow() {
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
		flowLine61121 = new FlowLine();
		flowLine61121.setBounds(new Rectangle(380, 273, 115, 10));
		flowLine61121.setArrowDirection(0);
		flowLine61121.setOnlyDrawLine(false);
		flowLine61121.setLineDirection(2);
		flowLine61121.setText("");
		flowLine311 = new FlowLine();
		flowLine311.setBounds(new Rectangle(569, 313, 10, 29));
		flowLine311.setArrowDirection(3);
		flowLine311.setOnlyDrawLine(true);
		flowLine311.setLineDirection(5);
		flowLine311.setText("");
		flowLine7 = new FlowLine();
		flowLine7.setBounds(new Rectangle(568, 230, 10, 39));
		flowLine7.setArrowDirection(3);
		flowLine7.setText("");
		flowLine32 = new FlowLine();
		flowLine32.setBounds(new Rectangle(445, 397, 11, 35));
		flowLine32.setArrowDirection(3);
		flowLine32.setLineDirection(5);
		flowLine32.setOnlyDrawLine(false);
		flowLine32.setText("");
		flowLine6112 = new FlowLine();
		flowLine6112.setBounds(new Rectangle(205, 307, 121, 9));
		flowLine6112.setArrowDirection(0);
		flowLine6112.setLineDirection(2);
		flowLine6112.setOnlyDrawLine(false);
		flowLine6112.setText("");
		flowLine6111 = new FlowLine();
		flowLine6111.setBounds(new Rectangle(439, 309, 135, 10));
		flowLine6111.setArrowDirection(1);
		flowLine6111.setLineDirection(2);
		flowLine6111.setOnlyDrawLine(false);
		flowLine6111.setText("");
		flowLine611 = new FlowLine();
		flowLine611.setBounds(new Rectangle(283, 255, 98, 12));
		flowLine611.setArrowDirection(1);
		flowLine611.setOnlyDrawLine(false);
		flowLine611.setLineDirection(2);
		flowLine611.setText("");
		flowLine31 = new FlowLine();
		flowLine31.setBounds(new Rectangle(197, 277, 17, 34));
		flowLine31.setArrowDirection(3);
		flowLine31.setLineDirection(5);
		flowLine31.setOnlyDrawLine(true);
		flowLine31.setText("");
		flowLine1 = new FlowLine();
		flowLine1.setBounds(new Rectangle(200, 128, 11, 38));
		flowLine1.setArrowDirection(3);
		flowLine1.setText("");
		flowLine6 = new FlowLine();
		flowLine6.setBounds(new Rectangle(298, 392, 153, 9));
		flowLine6.setArrowDirection(3);
		flowLine6.setOnlyDrawLine(true);
		flowLine6.setText("");
		flowLine4 = new FlowLine();
		flowLine4.setBounds(new Rectangle(294, 397, 8, 35));
		flowLine4.setArrowDirection(3);
		flowLine4.setText("");
		flowLine3 = new FlowLine();
		flowLine3.setBounds(new Rectangle(375, 261, 11, 27));
		flowLine3.setArrowDirection(3);
		flowLine3.setOnlyDrawLine(true);
		flowLine3.setLineDirection(5);
		flowLine3.setText("");
		flowLine2 = new FlowLine();
		flowLine2.setBounds(new Rectangle(200, 204, 11, 38));
		flowLine2.setArrowDirection(3);
		flowLine2.setText("");
		flowLine = new FlowLine();
		flowLine.setBounds(new Rectangle(568, 155, 11, 41));
		flowLine.setArrowDirection(3);
		flowLine.setText("");
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(375, 18, 252, 26));
		jLabel1.setText(CommonVars.getMainDay());
		jLabel1.setForeground(new Color(0, 102, 102));
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(194, 11, 176, 38));
		jLabel.setForeground(new Color(255, 153, 0));
		jLabel.setText("深加工结转向导");
		jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		this.setLayout(null);
		this.setSize(819, 574);
		this.add(getFlowButton1(), null);
		this.add(getFlowButton2(), null);
		this.add(getFlowButton3(), null);
		this.add(getFlowButton4(), null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(flowLine, null);
		this.add(flowLine2, null);
		this.add(flowLine3, null);
		this.add(flowLine4, null);
		this.add(flowLine6, null);
		this.add(getFlowButton31(), null);
		this.add(getFlowButton11(), null);
		this.add(getFlowButton12(), null);
		this.add(getFlowButton51(), null);
		this.add(flowLine1, null);
		this.add(flowLine31, null);
		this.add(flowLine611, null);
		this.add(flowLine6111, null);
		this.add(flowLine6112, null);
		this.add(flowLine32, null);
		this.add(getFlowButton32(), null);
		this.add(getFlowButton321(), null);
		this.add(getFlowButton33(), null);
		this.add(flowLine7, null);
		this.add(getFlowButton21(), null);
		this.add(flowLine311, null);
		this.add(flowLine61121, null);
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
			flowButton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			flowButton1.setBounds(new Rectangle(131, 241, 154, 38));
			flowButton1
					.setActionCommand("com.bestway.common.client.fpt.FmFptExgBillHead");
			flowButton1.setContentAreaFilled(false);
			flowButton1.setText("成品发货单");
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
			flowButton2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			flowButton2.setBounds(new Rectangle(494, 268, 154, 38));
			flowButton2.setActionCommand("com.bestway.common.client.fpt.FmFptImgBillHead");
			flowButton2.setContentAreaFilled(false);
			flowButton2.setText("料件收货单");
			flowButton2.setForeground(Color.gray);
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
			flowButton3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			flowButton3.setBounds(new Rectangle(129, 165, 154, 38));
			flowButton3
					.setActionCommand("com.bestway.common.client.fpt.FmFptExgAppHead");
			flowButton3.setContentAreaFilled(false);
			flowButton3.setText("结转申请表(关封)");
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
			flowButton4.setBounds(new Rectangle(381, 430, 131, 38));
			flowButton4
					.setActionCommand("com.bestway.common.client.fpt.FmFptAppSpareAnalyseGuide");
			flowButton4.setContentAreaFilled(false);
			flowButton4.setText("统计报表");
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
			flowButton31.setBounds(new Rectangle(14, 36, 154, 38));
			flowButton31
					.setActionCommand("com.bestway.common.client.fpt.FmFptParameterSet");
			flowButton31.setContentAreaFilled(false);
			flowButton31.setText("参数设置");
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
			flowButton11.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			flowButton11.setBounds(new Rectangle(324, 289, 117, 40));
//			flowButton11
//					.setActionCommand("com.bestway.common.client.fpt.FmFptCancelBill");
			flowButton11.setContentAreaFilled(false);
			flowButton11.setText("单据撤消");
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
			flowButton12.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			flowButton12.setBounds(new Rectangle(134, 318, 154, 40));
			flowButton12
					.setActionCommand("com.bestway.common.client.fpt.FmFptExgBackBillHead");
			flowButton12.setContentAreaFilled(false);
			flowButton12.setText("成品退货单");
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
			flowButton51.setBounds(new Rectangle(234, 430, 131, 38));
			flowButton51
					.setActionCommand("com.bestway.common.client.fpt.FmRequestToFptToCustomsReport");
			flowButton51.setContentAreaFilled(false);
			flowButton51.setText("结转单据对应表");
			flowButton51.setBorder(null);
		}
		return flowButton51;
	}

	/**
	 * This method initializes flowButton32	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton32() {
		if (flowButton32 == null) {
			flowButton32 = new FlowButton();
			flowButton32.setBounds(new Rectangle(130, 91, 154, 38));
			flowButton32.setContentAreaFilled(false);
			flowButton32.setText("成品结转");
			flowButton32.setForeground(Color.blue);
			flowButton32.setBorder(null);
		}
		return flowButton32;
	}

	/**
	 * This method initializes flowButton321	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton321() {
		if (flowButton321 == null) {
			flowButton321 = new FlowButton();
			flowButton321.setBounds(new Rectangle(490, 121, 157, 38));
			flowButton321.setContentAreaFilled(false);
			flowButton321.setText("料件结转");
			flowButton321.setForeground(Color.blue);
			flowButton321.setBorder(null);
		}
		return flowButton321;
	}

	/**
	 * This method initializes flowButton33	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton33() {
		if (flowButton33 == null) {
			flowButton33 = new FlowButton();
			flowButton33.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			flowButton33.setBounds(new Rectangle(492, 194, 157, 38));
			flowButton33.setActionCommand("com.bestway.common.client.fpt.FmFptImgAppHead");
			flowButton33.setContentAreaFilled(false);
			flowButton33.setText("\u7ed3\u8f6c\u7533\u8bf7\u8868(\u5173\u5c01)");
			flowButton33.setBorder(null);
		}
		return flowButton33;
	}

	/**
	 * This method initializes flowButton21	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getFlowButton21() {
		if (flowButton21 == null) {
			flowButton21 = new FlowButton();
			flowButton21.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			flowButton21.setBounds(new Rectangle(496, 342, 154, 38));
			flowButton21.setBorder(null);
			flowButton21.setActionCommand("com.bestway.common.client.fpt.FmFptImgBackBillHead");
			flowButton21.setContentAreaFilled(false);
			flowButton21.setText("退货单");
			flowButton21.setForeground(Color.gray);
		}
		return flowButton21;
	}

}  //  @jve:decl-index=0:visual-constraint="27,67"
