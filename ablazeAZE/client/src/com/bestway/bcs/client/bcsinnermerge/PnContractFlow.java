/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.bcsinnermerge;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;

/**
 * @author xxm
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class PnContractFlow extends JPanel {

	private JLabel jLabel1 = null;

	private Image image = null;

	private JLabel jLabel = null;

	private JPanel jPanel = null;

	private FlowLine flowLine = null;

	private FlowButton flowButton = null;

	private FlowButton flowButton1 = null;

	private FlowButton flowButton11 = null;

	private FlowButton flowButton12 = null;

	private FlowButton flowButton8 = null;

	private FlowButton flowButton9 = null;

	private FlowButton flowButton10 = null;

	private FlowLine flowLine11 = null;

	private FlowLine flowLine9 = null;

	private FlowLine flowLine10 = null;

	private FlowLine flowLine91 = null;

	private FlowLine flowLine4 = null;

	private FlowLine flowLine7 = null;

	private FlowButton flowButton111 = null;

	private FlowLine flowLine41 = null;

	private FlowButton flowButton2 = null;

	private FlowLine flowLine42 = null;

	private FlowLine flowLine911 = null;

	private FlowLine flowLine111 = null;

	private FlowLine flowLine1 = null;

	private FlowLine flowLine2 = null;

	private FlowLine flowLine9111 = null;

	private FlowLine flowLine1111 = null;

	private FlowLine flowLine43 = null;

	private FlowButton flowButton21 = null;

	private FlowButton flowButton13 = null;

	private FlowLine flowLine411 = null;

	private FlowButton flowButton121 = null;

	private FlowLine flowLine71 = null;

	private FlowButton flowButton1111 = null;

	private FlowButton flowButton1112 = null;

	private FlowLine flowLine72 = null;

	private FlowLine flowLine73 = null;

	/**
	 * @param arg0
	 * @param arg1
	 */
	public PnContractFlow(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnContractFlow(LayoutManager arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnContractFlow(boolean arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * 
	 */
	public PnContractFlow() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel = new JLabel();
		jLabel1 = new JLabel();
		this.setLayout(null);
		this.setSize(708, 462);
		jLabel1.setBounds(155, 12, 252, 32);
		jLabel1.setText("纸质/电子化手册管理向导");
		jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		jLabel1.setForeground(new java.awt.Color(255, 153, 0));
		jLabel.setBounds(397, 20, 299, 23);
		jLabel.setText(CommonVars.getMainDay());
		jLabel.setForeground(new java.awt.Color(0, 102, 102));
		image = CommonVars.getImageSource().getImage("background.gif");
		this.add(getJPanel(), null);
		this.add(jLabel1, null);
		// this.add(jLabel, null);
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
			jPanel.setBounds(x, y, 702, 382);

		} catch (Exception e) {

		}
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			flowLine73 = new FlowLine();
			flowLine73.setBounds(new Rectangle(400, 337, 86, 13));
			flowLine73.setArrowDirection(1);
			flowLine73.setText("");
			flowLine72 = new FlowLine();
			flowLine72.setBounds(new Rectangle(400, 297, 87, 16));
			flowLine72.setArrowDirection(1);
			flowLine72.setText("");
			flowLine71 = new FlowLine();
			flowLine71.setBounds(new Rectangle(488, 164, 84, 26));
			flowLine71.setArrowDirection(0);
			flowLine71.setText("");
			flowLine411 = new FlowLine();
			flowLine411.setBounds(new Rectangle(98, 172, 15, 17));
			flowLine411.setArrowDirection(3);
			flowLine411.setText("");
			flowLine43 = new FlowLine();
			flowLine43.setBounds(new Rectangle(390, 130, 11, 22));
			flowLine43.setArrowDirection(3);
			flowLine43.setDrawColor(new Color(241, 148, 148));
			flowLine43.setText("");
			flowLine1111 = new FlowLine();
			flowLine1111.setBounds(new Rectangle(190, 142, 45, 13));
			flowLine1111.setArrowDirection(3);
			flowLine1111.setOnlyDrawLine(true);
			flowLine1111.setDrawColor(new Color(241, 148, 148));
			flowLine1111.setText("");
			flowLine9111 = new FlowLine();
			flowLine9111.setBounds(new Rectangle(231, 117, 10, 32));
			flowLine9111.setArrowDirection(3);
			flowLine9111.setOnlyDrawLine(true);
			flowLine9111.setLineDirection(5);
			flowLine9111.setDrawColor(new Color(241, 148, 148));
			flowLine9111.setText("");
			flowLine2 = new FlowLine();
			flowLine2.setBounds(new Rectangle(235, 112, 76, 12));
			flowLine2.setDrawColor(new Color(241, 148, 148));
			flowLine1 = new FlowLine();
			flowLine1.setBounds(new Rectangle(190, 97, 121, 20));
			flowLine1.setText("");
			flowLine1.setHorizontalAlignment(SwingConstants.CENTER);
			flowLine1.setForeground(new Color(51, 153, 255));
			flowLine1.setDrawColor(new Color(241, 148, 148));
			flowLine111 = new FlowLine();
			flowLine111.setBounds(new Rectangle(190, 51, 45, 11));
			flowLine111.setArrowDirection(3);
			flowLine111.setOnlyDrawLine(true);
			flowLine111.setDrawColor(new Color(241, 148, 148));
			flowLine111.setText("");
			flowLine911 = new FlowLine();
			flowLine911.setBounds(new Rectangle(230, 56, 10, 41));
			flowLine911.setArrowDirection(3);
			flowLine911.setOnlyDrawLine(true);
			flowLine911.setLineDirection(5);
			flowLine911.setDrawColor(new Color(241, 148, 148));
			flowLine911.setText("");
			flowLine42 = new FlowLine();
			flowLine42.setBounds(new Rectangle(97, 74, 10, 18));
			flowLine42.setArrowDirection(3);
			flowLine42.setText("");
			flowLine41 = new FlowLine();
			flowLine41.setBounds(new Rectangle(96, 123, 14, 20));
			flowLine41.setArrowDirection(3);
			flowLine41.setText("");
			flowLine7 = new FlowLine();
			flowLine7.setBounds(new Rectangle(400, 261, 87, 11));
			flowLine7.setArrowDirection(1);
			flowLine7.setText("");
			flowLine4 = new FlowLine();
			flowLine4.setBounds(new Rectangle(567, 133, 9, 106));
			flowLine4.setArrowDirection(3);
			flowLine4.setText("");
			flowLine91 = new FlowLine();
			flowLine91.setBounds(new Rectangle(479, 236, 13, 131));
			flowLine91.setArrowDirection(3);
			flowLine91.setOnlyDrawLine(true);
			flowLine91.setLineDirection(5);
			flowLine91.setText("");
			flowLine10 = new FlowLine();
			flowLine10.setBounds(new Rectangle(484, 357, 179, 17));
			flowLine10.setArrowDirection(3);
			flowLine10.setOnlyDrawLine(true);
			flowLine10.setText("");
			flowLine9 = new FlowLine();
			flowLine9.setBounds(new Rectangle(656, 237, 10, 129));
			flowLine9.setArrowDirection(3);
			flowLine9.setOnlyDrawLine(true);
			flowLine9.setLineDirection(5);
			flowLine9.setText("");
			flowLine11 = new FlowLine();
			flowLine11.setBounds(new Rectangle(484, 228, 179, 17));
			flowLine11.setArrowDirection(3);
			flowLine11.setOnlyDrawLine(true);
			flowLine11.setText("");
			flowLine = new FlowLine();
			flowLine.setBounds(new Rectangle(235, 90, 76, 12));
			flowLine.setDrawColor(new Color(241, 148, 148));
			jPanel = new JPanel();
			jPanel.setOpaque(false);
			jPanel.setLayout(null);
			jPanel.setBounds(9, 69, 675, 382);
			jPanel.add(flowLine, null);
			jPanel.add(getFlowButton(), null);
			jPanel.add(getFlowButton1(), null);
			jPanel.add(getFlowButton11(), null);
			jPanel.add(getFlowButton12(), null);
			jPanel.add(getFlowButton8(), null);
			jPanel.add(getFlowButton9(), null);
			jPanel.add(getFlowButton10(), null);
			jPanel.add(flowLine11, null);
			jPanel.add(flowLine9, null);
			jPanel.add(flowLine10, null);
			jPanel.add(flowLine91, null);
			jPanel.add(flowLine4, null);
			jPanel.add(flowLine7, null);
			jPanel.add(getFlowButton111(), null);
			jPanel.add(flowLine41, null);
			jPanel.add(getFlowButton2(), null);
			jPanel.add(flowLine42, null);
			jPanel.add(flowLine911, null);
			jPanel.add(flowLine111, null);
			jPanel.add(flowLine1, null);
			jPanel.add(flowLine2, null);
			jPanel.add(flowLine9111, null);
			jPanel.add(flowLine1111, null);
			jPanel.add(flowLine43, null);
			jPanel.add(getFlowButton21(), null);
			jPanel.add(getFlowButton13(), null);
			jPanel.add(flowLine411, null);
			jPanel.add(getFlowButton121(), null);
			jPanel.add(flowLine71, null);
			jPanel.add(getFlowButton1111(), null);
			jPanel.add(getFlowButton1112(), null);
			jPanel.add(flowLine72, null);
			jPanel.add(flowLine73, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes flowButton
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton() {
		if (flowButton == null) {
			flowButton = new FlowButton();
			flowButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			flowButton.setBounds(new Rectangle(28, 91, 166, 33));
			flowButton
					.setActionCommand("com.bestway.bcs.client.bcsinnermerge.FmBcsTenInnerMerge");
			flowButton.setText("报关商品资料");
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
			flowButton1.setBounds(new Rectangle(28, 140, 166, 33));
			flowButton1
					.setActionCommand("com.bestway.bcs.client.bcsinnermerge.FmBcsInnerMerge");
			flowButton1.setText("物料与报关对应表");
		}
		return flowButton1;
	}

	/**
	 * This method initializes flowButton11
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton11() {
		if (flowButton11 == null) {
			flowButton11 = new FlowButton();
			flowButton11.setBounds(new Rectangle(311, 78, 180, 55));
			flowButton11
					.setActionCommand("com.bestway.bcs.client.dictpor.FmBcsDictPor");
			flowButton11.setText("备案资料库备案");
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
			flowButton12.setBounds(new Rectangle(311, 149, 180, 55));
			flowButton12
					.setActionCommand("com.bestway.bcs.client.contract.FmContract");
			flowButton12.setText("通关手册备案");
		}
		return flowButton12;
	}

	/**
	 * This method initializes flowButton8
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton8() {
		if (flowButton8 == null) {
			flowButton8 = new FlowButton();
			flowButton8.setBounds(new Rectangle(503, 248, 140, 29));
			flowButton8.setText("\u8fdb\u53e3\u62a5\u5173\u5355");
			flowButton8
					.setActionCommand("com.bestway.bcs.client.contractexe.FmBcsImportCustomsDeclaration");
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
			flowButton9.setBounds(new Rectangle(503, 288, 140, 29));
			flowButton9.setText("\u51fa\u53e3\u62a5\u5173\u5355");
			flowButton9
					.setActionCommand("com.bestway.bcs.client.contractexe.FmBcsExportCustomsDeclaration");
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
			flowButton10.setBounds(new Rectangle(503, 326, 140, 29));
			flowButton10.setText("\u7279\u6b8a\u62a5\u5173\u5355");
			flowButton10
					.setActionCommand("com.bestway.bcs.client.contractexe.FmBcsSpecialCustomsDeclaration");
		}
		return flowButton10;
	}

	/**
	 * This method initializes flowButton111
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton111() {
		if (flowButton111 == null) {
			flowButton111 = new FlowButton();
			flowButton111.setBounds(new Rectangle(270, 251, 133, 30));
			flowButton111.setText("数据报核");
			flowButton111
					.setActionCommand("com.bestway.bcs.client.contractcav.FmContractCav");
		}
		return flowButton111;
	}

	/**
	 * This method initializes flowButton2
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton2() {
		if (flowButton2 == null) {
			flowButton2 = new FlowButton();
			flowButton2.setBounds(new Rectangle(28, 42, 166, 33));
			flowButton2
					.setActionCommand("com.bestway.bcus.client.custombase.FmBaseCodeHs");
			flowButton2.setText("商品编码");
		}
		return flowButton2;
	}

	/**
	 * This method initializes flowButton21
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton21() {
		if (flowButton21 == null) {
			flowButton21 = new FlowButton();
			flowButton21.setBounds(new Rectangle(311, 16, 180, 45));
			flowButton21.setText("纸质手册参数设定");
			flowButton21
					.setActionCommand("com.bestway.bcs.client.contract.FmBcsParameterSet");
		}
		return flowButton21;
	}

	/**
	 * This method initializes flowButton13
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton13() {
		if (flowButton13 == null) {
			flowButton13 = new FlowButton();
			flowButton13.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			flowButton13.setBounds(new Rectangle(28, 186, 166, 33));
			flowButton13.setText("工厂与报关对比");
			flowButton13
					.setActionCommand("com.bestway.bcs.client.bcsinnermerge.FmBcsFactoryCustoms");
		}
		return flowButton13;
	}

	/**
	 * This method initializes flowButton121
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton121() {
		if (flowButton121 == null) {
			flowButton121 = new FlowButton();
			flowButton121.setBounds(new Rectangle(496, 78, 153, 55));
			flowButton121.setText("进出口申请单");
			flowButton121
					.setActionCommand("com.bestway.bcs.client.contractexe.FmBcsImpExpRequestBill");
		}
		return flowButton121;
	}

	/**
	 * This method initializes flowButton1111
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton1111() {
		if (flowButton1111 == null) {
			flowButton1111 = new FlowButton();
			flowButton1111.setBounds(new Rectangle(270, 291, 133, 30));
			flowButton1111.setText("统计报表");
			flowButton1111
					.setActionCommand("com.bestway.bcs.client.contractstat.FmContractReport");
		}
		return flowButton1111;
	}

	/**
	 * This method initializes flowButton1112
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton1112() {
		if (flowButton1112 == null) {
			flowButton1112 = new FlowButton();
			flowButton1112.setBounds(new Rectangle(270, 329, 133, 30));
			flowButton1112.setText("报关分析");
			flowButton1112
					.setActionCommand("com.bestway.bcs.client.contractanalyse.FmCustomsAnalyse");
		}
		return flowButton1112;
	}
} // @jve:decl-index=0:visual-constraint="28,-88"
