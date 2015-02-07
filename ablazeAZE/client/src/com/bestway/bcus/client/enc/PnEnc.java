/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;

import javax.swing.ImageIcon;
import java.awt.Font;
/**
 * @author xxm
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PnEnc extends JPanel {

	private FlowButton jButton6 = null;
	private FlowButton jButton1 = null;
	private FlowButton jButton2 = null;
	private FlowButton jButton3 = null;
	private JLabel jLabel1 = null;
	private FlowButton jButton4 = null;
	private FlowButton jButton5 = null;
	private Image image = null;
	private JLabel jLabel = null;
	private JPanel jPanel = null;
	private FlowLine flowLine = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel21 = null;
	private FlowLine flowLine1 = null;
	private FlowLine flowLine11 = null;
	private FlowLine flowLine111 = null;
	private FlowLine flowLine1111 = null;
	private FlowLine flowLine11111 = null;
	private FlowLine flowLine2 = null;
	private FlowLine flowLine21 = null;
	private FlowLine flowLine111111 = null;
	private FlowLine flowLine111112 = null;
	private FlowLine flowLine111113 = null;
	private FlowLine flowLine111114 = null;
	private FlowLine flowLine1111141 = null;
	/**
	 * @param arg0
	 * @param arg1
	 */
	public PnEnc(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnEnc(LayoutManager arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnEnc(boolean arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * 
	 */
	public PnEnc() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		jLabel = new JLabel();
		jLabel1 = new JLabel();
		this.setLayout(null);
		this.setSize(770, 462);
		Dimension panelSize = this.getSize();
		jLabel1.setBounds(137, 12, 216, 32);
		jLabel1.setText("电子帐册通关管理向导");
		jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		jLabel1.setForeground(new java.awt.Color(255,153,0));
		jLabel.setBounds(357, 21, 386, 23);
		jLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		jLabel.setText(CommonVars.getMainDay());
		jLabel.setForeground(new java.awt.Color(0,102,102));
		image = CommonVars.getImageSource().getImage("background.gif");
        this.add(getJPanel(), null);
		this.add(jLabel1, null);
		//this.add(jLabel, null);
		this.add(jLabel, null);
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	 public void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        } catch (Exception e) {

        }
    }
	private JButton getJButton() {
		if (jButton6 == null) {
			jButton6 = new FlowButton();
			jButton6.setActionCommand("com.bestway.bcus.client.enc.FmBcusImpExpRequestBill");
			jButton6.setBounds(new Rectangle(87, 108, 105, 32));
			jButton6.setText("进出口申请单");
		}
		return jButton6;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new FlowButton();
			jButton1.setActionCommand("com.bestway.bcus.client.enc.FmApplyToCustomsBillList");
			jButton1.setBounds(new Rectangle(235, 108, 105, 33));
			jButton1.setText("报关清单");
		}
		return jButton1;
	}
	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new FlowButton();
			jButton2.setActionCommand("com.bestway.bcus.client.enc.FmImportCustomsDeclaration");
			jButton2.setBounds(new Rectangle(392, 38, 111, 31));
		     jButton2.setText("进口报关单");
		}
		return jButton2;
	}
	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new FlowButton();
			jButton3.setActionCommand("com.bestway.bcus.client.enc.FmExportCustomsDeclaration");
			jButton3.setBounds(new Rectangle(392, 106, 110, 31));
		     jButton3.setText("出口报关单");
		}
		return jButton3;
	}
	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new FlowButton();
//			jButton4.setText("特殊报关单");
			jButton4.setActionCommand("com.bestway.bcus.client.enc.FmSpecialCustomsDeclaration");
			jButton4.setBounds(new Rectangle(390, 182, 110, 33));
		     jButton4.setText("特殊报关单");
		}
		return jButton4;
	}
	/**
	 * This method initializes jButton5	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new FlowButton();
			jButton5.setActionCommand("com.bestway.bcus.client.enc.FmCustomsDeclarationDiffAnalyse");
			jButton5.setBounds(new Rectangle(561, 105, 144, 32));
		     jButton5.setText("大小清单差异分析");
		}
		return jButton5;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			flowLine1111141 = new FlowLine();
			flowLine1111141.setBounds(new Rectangle(496, 195, 38, 10));
			flowLine1111141.setOnlyDrawLine(true);
			flowLine1111141.setArrowDirection(0);
			flowLine111114 = new FlowLine();
			flowLine111114.setBounds(new Rectangle(501, 42, 33, 15));
			flowLine111114.setArrowDirection(0);
			flowLine111114.setOnlyDrawLine(true);
			flowLine111113 = new FlowLine();
			flowLine111113.setBounds(new Rectangle(355, 190, 37, 14));
			flowLine111113.setArrowDirection(0);
			flowLine111112 = new FlowLine();
			flowLine111112.setBounds(new Rectangle(357, 45, 36, 10));
			flowLine111112.setArrowDirection(0);
			flowLine111111 = new FlowLine();
			flowLine111111.setBounds(new Rectangle(500, 108, 61, 29));
			flowLine111111.setArrowDirection(0);
			flowLine21 = new FlowLine();
			flowLine21.setBounds(new Rectangle(527, 50, 10, 152));
			flowLine21.setLineDirection(5);
			flowLine21.setOnlyDrawLine(true);
			flowLine21.setArrowDirection(0);
			flowLine2 = new FlowLine();
			flowLine2.setBounds(new Rectangle(337, 51, 38, 147));
			flowLine2.setArrowDirection(0);
			flowLine2.setOnlyDrawLine(true);
			flowLine2.setLineDirection(5);
			flowLine11111 = new FlowLine();
			flowLine11111.setBounds(new Rectangle(337, 109, 55, 28));
			flowLine11111.setArrowDirection(0);
			flowLine1111 = new FlowLine();
			flowLine1111.setBounds(new Rectangle(189, 110, 47, 29));
			flowLine1111.setArrowDirection(0);
			flowLine111 = new FlowLine();
			flowLine111.setBounds(new Rectangle(122, 65, 22, 44));
			flowLine111.setArrowDirection(3);
			flowLine11 = new FlowLine();
			flowLine11.setBounds(new Rectangle(119, 140, 21, 31));
			flowLine11.setArrowDirection(2);
			flowLine1 = new FlowLine();
			flowLine1.setBounds(new Rectangle(71, 155, 59, 30));
			flowLine1.setArrowDirection(0);
			flowLine1.setOnlyDrawLine(true);
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(15, 155, 63, 34));
			jLabel21.setText("物料主档");
			jLabel21.setForeground(new java.awt.Color(255,153,0));
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(9, 50, 71, 37));
			jLabel2.setText("从物料单据");
			jLabel2.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel2.setForeground(new java.awt.Color(255,153,0));
			jPanel = new JPanel();
			jPanel.setLayout(null);
            jPanel.setOpaque(false);
			flowLine = new FlowLine();
			flowLine.setBounds(new Rectangle(75, 50, 58, 31));
			flowLine.setArrowDirection(0);
			flowLine.setOnlyDrawLine(true);
			jPanel.add(flowLine,null);
            jPanel.setBounds(9, 96, 712, 252);
            jPanel.add(getJButton4(), null);
            jPanel.add(getJButton5(), null);
            jPanel.add(getJButton1(), null);
            jPanel.add(getJButton2(), null);
            jPanel.add(getJButton(), null);
            jPanel.add(getJButton3(), null);
            jPanel.add(jLabel2, null);
            jPanel.add(jLabel21, null);
            jPanel.add(flowLine1, null);
            jPanel.add(flowLine11, null);
            jPanel.add(flowLine111, null);
            jPanel.add(flowLine1111, null);
            jPanel.add(flowLine11111, null);
            jPanel.add(flowLine2, null);
            jPanel.add(flowLine21, null);
            jPanel.add(flowLine111111, null);
            jPanel.add(flowLine111112, null);
            jPanel.add(flowLine111113, null);
            jPanel.add(flowLine111114, null);
            jPanel.add(flowLine1111141, null);
		}
		return jPanel;
	}
 }  //  @jve:decl-index=0:visual-constraint="28,-88"
