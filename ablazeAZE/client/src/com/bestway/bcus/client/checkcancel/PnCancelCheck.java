/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

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
public class PnCancelCheck extends JPanel {

	private FlowButton jButton = null;
	private FlowButton jButton1 = null;
	private JLabel jLabel1 = null;
	private FlowButton jButton2 = null;
	 private Image image = null;
	private JLabel jLabel = null;
	private JPanel jPanel = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel21 = null;
	private JLabel jLabel22 = null;
	private FlowLine flowLine = null;
	private FlowLine flowLine1 = null;
	private FlowLine flowLine111 = null;
	private FlowLine flowLine1111 = null;
	private FlowLine flowLine2 = null;
	private FlowLine flowLine3 = null;
	private FlowLine flowLine21 = null;
	private FlowLine flowLine211 = null;
	/**
	 * @param arg0
	 * @param arg1
	 */
	public PnCancelCheck(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnCancelCheck(LayoutManager arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnCancelCheck(boolean arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * 
	 */
	public PnCancelCheck() {
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
		this.setSize(751, 462);
		Dimension panelSize = this.getSize();
		jLabel1.setBounds(137, 12, 214, 32);
		jLabel1.setText("帐册核查核销管理向导");
		jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		jLabel1.setForeground(new java.awt.Color(255,153,0));
		jLabel.setBounds(355, 23, 376, 21);
		jLabel.setText(CommonVars.getMainDay());
		jLabel.setForeground(new java.awt.Color(0,102,102));
		image = CommonVars.getImageSource().getImage("background.gif");
		this.add(jLabel1, null);
        this.add(getJPanel(), null);
		//this.add(jLabel, null);
		this.add(jLabel, null);
	}
	 public void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        } catch (Exception e) {

        }
    }
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new FlowButton();
			jButton.setActionCommand("com.bestway.bcus.client.checkcancel.FmCancelOwnerHead");
			jButton.setText("自用核销");
			jButton.setBounds(new Rectangle(364, 50, 108, 31));
		}
		return jButton;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new FlowButton();
			jButton1.setActionCommand("com.bestway.bcus.client.checkcancel.FmCancelCusHead");
			jButton1.setText("数据报核");
			jButton1.setBounds(new Rectangle(364, 193, 111, 34));
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
			jButton2.setActionCommand("com.bestway.bcus.client.checkcancel.FmEmsAnalyHead");
			jButton2.setText("帐帐分析");
			jButton2.setBounds(new Rectangle(362, 123, 112, 34));
		}
		return jButton2;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			flowLine211 = new FlowLine();
			flowLine211.setBounds(new Rectangle(302, 52, 63, 24));
			flowLine211.setArrowDirection(0);
			flowLine21 = new FlowLine();
			flowLine21.setBounds(new Rectangle(303, 199, 62, 29));
			flowLine21.setArrowDirection(0);
			flowLine3 = new FlowLine();
			flowLine3.setBounds(new Rectangle(282, 64, 43, 149));
			flowLine3.setOnlyDrawLine(true);
			flowLine3.setArrowDirection(0);
			flowLine3.setLineDirection(5);
			flowLine2 = new FlowLine();
			flowLine2.setBounds(new Rectangle(242, 127, 120, 22));
			flowLine2.setArrowDirection(0);
			flowLine1111 = new FlowLine();
			flowLine1111.setBounds(new Rectangle(219, 147, 21, 67));
			flowLine1111.setArrowDirection(2);
			flowLine1 = new FlowLine();
			flowLine1.setBounds(new Rectangle(124, 201, 105, 23));
			flowLine1.setOnlyDrawLine(true);
			flowLine1.setArrowDirection(0);
			flowLine111  = new FlowLine();
			flowLine111.setBounds(new Rectangle(217, 58, 22, 66));
			flowLine111.setArrowDirection(3);
			jLabel22 = new JLabel();
			jLabel22.setBounds(new Rectangle(214, 123, 30, 31));
			jLabel22.setText("转换");
			jLabel22.setForeground(new java.awt.Color(255,153,0));
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(19, 43, 71, 26));
			jLabel21.setText("出口报关单");
			jLabel21.setForeground(new java.awt.Color(255,153,0));
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(19, 195, 113, 34));
			jLabel2.setText("电子帐册备案物料");
			jLabel2.setForeground(new java.awt.Color(255,153,0));
			jPanel = new JPanel();
			jPanel.setOpaque(false);
			jPanel.setLayout(null);
			jPanel.setBounds(40, 83, 540, 268);
			flowLine  = new FlowLine();
			flowLine.setBounds(new Rectangle(83, 42, 146, 31));
			flowLine.setArrowDirection(0);
			flowLine.setOnlyDrawLine(true);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel21, null);
			jPanel.add(jLabel22, null);
			jPanel.add(flowLine, null);
			jPanel.add(flowLine1, null);
			jPanel.add(flowLine111, null);
			jPanel.add(flowLine1111, null);
			jPanel.add(flowLine2, null);
			jPanel.add(flowLine3, null);
			jPanel.add(flowLine21, null);
			jPanel.add(flowLine211, null);
		}
		return jPanel;
	}
  }  //  @jve:decl-index=0:visual-constraint="28,-88"
