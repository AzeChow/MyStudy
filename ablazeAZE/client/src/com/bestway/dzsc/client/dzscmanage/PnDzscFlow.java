package com.bestway.dzsc.client.dzscmanage;

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

public class PnDzscFlow extends JPanel {

	private FlowButton flowButton = null;
	private FlowLine flowLine = null;
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
	private FlowLine flowLine1 = null;
	private FlowLine flowLine2 = null;
	private FlowLine flowLine3 = null;
	private FlowLine flowLine5 = null;
	private FlowLine flowLine4 = null;
	private FlowLine flowLine6 = null;
	private FlowLine flowLine7 = null;
	private FlowLine flowLine8 = null;
	private FlowLine flowLine9 = null;
	private FlowLine flowLine10 = null;
	private FlowLine flowLine11 = null;
	private Image image = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	
	public PnDzscFlow(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initialize();
	}

	public PnDzscFlow(LayoutManager layout) {
		super(layout);
		initialize();
	}

	public PnDzscFlow(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initialize();
	}

	public PnDzscFlow() {
		super();
		initialize();
		image = CommonVars.getImageSource().getImage("background.gif");
		this.add(getFlowButton12(), null);
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
		jLabel.setText("电子手册管理向导");
		jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		flowLine11 = new FlowLine();
		flowLine11.setBounds(new java.awt.Rectangle(531,277,137,13));
		flowLine11.setArrowDirection(3);
		flowLine11.setOnlyDrawLine(true);
		flowLine11.setText("");
		flowLine10 = new FlowLine();
		flowLine10.setBounds(new java.awt.Rectangle(531,402,138,20));
		flowLine10.setArrowDirection(3);
		flowLine10.setOnlyDrawLine(true);
		flowLine10.setText("");
		flowLine9 = new FlowLine();
		flowLine9.setBounds(new java.awt.Rectangle(662,283,10,132));
		flowLine9.setArrowDirection(3);
		flowLine9.setOnlyDrawLine(true);
		flowLine9.setLineDirection(5);
		flowLine9.setText("");
		flowLine8 = new FlowLine();
		flowLine8.setBounds(new java.awt.Rectangle(525,284,13,129));
		flowLine8.setArrowDirection(3);
		flowLine8.setOnlyDrawLine(true);
		flowLine8.setLineDirection(5);
		flowLine8.setText("");
		flowLine7 = new FlowLine();
		flowLine7.setBounds(new java.awt.Rectangle(453,325,72,22));
		flowLine7.setArrowDirection(1);
		flowLine7.setText("");
		flowLine6 = new FlowLine();
		flowLine6.setBounds(new java.awt.Rectangle(457,176,18,65));
		flowLine6.setArrowDirection(3);
		flowLine6.setText("");
		flowLine4 = new FlowLine();
		flowLine4.setBounds(new java.awt.Rectangle(588,232,22,50));
		flowLine4.setArrowDirection(3);
		flowLine4.setText("");
		flowLine5 = new FlowLine();
		flowLine5.setBounds(new java.awt.Rectangle(586,125,17,74));
		flowLine5.setArrowDirection(3);
		flowLine5.setText("");
		flowLine3 = new FlowLine();
		flowLine3.setBounds(new java.awt.Rectangle(504,150,85,20));
		flowLine3.setText("");
		flowLine2 = new FlowLine();
		flowLine2.setBounds(new java.awt.Rectangle(370,149,44,19));
		flowLine2.setText("");
		flowLine1 = new FlowLine();
		flowLine1.setBounds(new java.awt.Rectangle(110,142,51,21));
		flowLine1.setText("");
		flowLine = new FlowLine();
		flowLine.setBounds(new java.awt.Rectangle(241,144,58,24));
		flowLine.setText("");
		this.setLayout(null);
		this.setSize(696, 480);
		this.add(getFlowButton(), null);
		this.add(flowLine, null);
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
		this.add(flowLine1, null);
		this.add(flowLine2, null);
		this.add(flowLine3, null);
		this.add(flowLine5, null);
		this.add(flowLine4, null);
		this.add(flowLine6, null);
		this.add(flowLine7, null);
		this.add(flowLine8, null);
		this.add(flowLine9, null);
		this.add(flowLine10, null);
		this.add(flowLine11, null);
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
			flowButton.setBounds(new java.awt.Rectangle(156,139,88,36));
			flowButton.setActionCommand("com.bestway.dzsc.client.innermerge.FmDzscInnerMerge");
			flowButton.setText("\u5f52\u5e76\u5173\u7cfb");
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
			flowButton1.setBounds(new java.awt.Rectangle(28,120,89,40));
			flowButton1.setActionCommand("com.bestway.dzsc.client.materialapply.FmMaterialApply");
			flowButton1.setFont(new java.awt.Font("新宋体", java.awt.Font.PLAIN, 15));
			flowButton1.setText("\u7269\u6599\u5907\u6848");
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
			flowButton2.setBounds(new java.awt.Rectangle(44,152,83,38));
			flowButton2.setActionCommand("com.bestway.dzsc.client.materialapply.FmApplyBomManage");
			flowButton2.setText("BOM\u5907\u6848");
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
			flowButton3.setBounds(new java.awt.Rectangle(297,139,76,38));
			flowButton3.setActionCommand("com.bestway.dzsc.client.dzscmanage.FmDzscEmsPorWjHead");
			flowButton3.setText("\u5408\u540c\u5907\u6848");
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
			flowButton4.setBounds(new java.awt.Rectangle(545,84,107,42));
			flowButton4.setActionCommand("com.bestway.dzsc.client.impexprequest.FmDzscImpExpRequestBill");
			flowButton4.setText("\u62a5\u5173\u7533\u8bf7\u5355");
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
			flowButton5.setBounds(new java.awt.Rectangle(543,200,113,35));
			flowButton5.setActionCommand("com.bestway.dzsc.client.customslist.FmDzscCustomsBillList");
			flowButton5.setText("\u62a5\u5173\u6e05\u5355");
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
			flowButton6.setBounds(new java.awt.Rectangle(415,141,89,35));
			flowButton6.setActionCommand("com.bestway.dzsc.client.dzscmanage.FmDzscEmsPorBillHead");
			flowButton6.setText("\u901a\u5173\u5907\u6848");
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
			flowButton7.setBounds(new java.awt.Rectangle(411,241,106,32));
			flowButton7.setActionCommand("com.bestway.dzsc.client.dzscmanage.FmDzscFascicule");
			flowButton7.setText("\u624b\u518c\u5206\u518c");
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
			flowButton8.setBounds(new java.awt.Rectangle(548,291,111,34));
			flowButton8.setActionCommand("com.bestway.dzsc.client.contractexe.FmDzscImportCustomsDeclaration");
			flowButton8.setText("\u8fdb\u53e3\u62a5\u5173\u5355");
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
			flowButton9.setBounds(new java.awt.Rectangle(548,328,111,34));
			flowButton9.setActionCommand("com.bestway.dzsc.client.contractexe.FmDzscExportCustomsDeclaration");
			flowButton9.setText("\u51fa\u53e3\u62a5\u5173\u5355");
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
			flowButton10.setBounds(new java.awt.Rectangle(548,365,111,34));
			flowButton10.setActionCommand("com.bestway.dzsc.client.contractexe.FmDzscSpecialCustomsDeclaration");
			flowButton10.setText("\u7279\u6b8a\u62a5\u5173\u5355");
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
			flowButton11.setBounds(new java.awt.Rectangle(330,306,108,37));
			flowButton11.setActionCommand("com.bestway.dzsc.client.checkcancel.FmDzscContractCav");
			flowButton11.setText("\u6570\u636e\u6838\u9500");
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
			flowButton12.setBounds(new java.awt.Rectangle(356,332,95,37));
			flowButton12.setActionCommand("com.bestway.dzsc.client.checkcancel.FmDzscCheckHead");
			flowButton12.setText("\u4e2d\u671f\u6838\u67e5");
		}
		return flowButton12;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
