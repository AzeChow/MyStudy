/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.outsource;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import com.bestway.bcus.client.common.CommonVars;
import java.awt.Font;
/**
 * @author xxm
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PnOutSource extends JPanel {

    private JLabel  jLabel1  = null;
    private Image   image    = null;
    private JLabel  jLabel   = null;
    private FlowButton flowButton = null;
    private FlowButton flowButton1 = null;
    private FlowButton flowButton2 = null;

    /**
     * @param arg0
     * @param arg1
     */
    public PnOutSource(LayoutManager arg0, boolean arg1) {
        super(arg0, arg1);
        initialize();
    }

    /**
     * @param arg0
     */
    public PnOutSource(LayoutManager arg0) {
        super(arg0);
        initialize();
    }

    /**
     * @param arg0
     */
    public PnOutSource(boolean arg0) {
        super(arg0);
        initialize();
    }

    /**
     * 
     */
    public PnOutSource() {
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
        this.setSize(755, 462);
        Dimension panelSize = this.getSize();
        jLabel1.setBounds(137, 12, 216, 32);
        jLabel1.setText("委外管理");
        jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel.setBounds(363, 19, 376, 24);
        jLabel.setText(CommonVars.getMainDay());
        jLabel.setForeground(new java.awt.Color(0, 102, 102));
        image = CommonVars.getImageSource().getImage("background.gif");
        this.add(jLabel1, null);
        this.add(jLabel, null);
        this.add(getFlowButton(), null);
        this.add(getFlowButton1(), null);
        this.add(getFlowButton2(), null);
    }

    public void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        } catch (Exception e) {

        }
    }
    
	private FlowButton getFlowButton() {
		if (flowButton == null) {
			flowButton = new FlowButton();
			flowButton.setBounds(new java.awt.Rectangle(130,200,110,30));
			flowButton.setActionCommand("com.bestway.common.client.outsource.FmQueryGuide");
			flowButton.setText("委外明细报表");
		}
		return flowButton;
	}
	
	private FlowButton getFlowButton1() {
		if (flowButton1 == null) {
			flowButton1 = new FlowButton();
			flowButton1.setBounds(new java.awt.Rectangle(160,250,110,30));
			flowButton1.setActionCommand("com.bestway.common.client.outsource.specificcontrol.FmSpecificControl");
			flowButton1.setText("特殊控制");
		}
		return flowButton1;
	}

	private FlowButton getFlowButton2() {
		if (flowButton2 == null) {
			flowButton2 = new FlowButton();
			flowButton2.setBounds(new java.awt.Rectangle(190,300,110,30));
			flowButton2.setActionCommand("com.bestway.common.client.outsource.FmStatQueryGuide");
			flowButton2.setText("委外统计报表");
		}
		return flowButton2;
	}






      }  //  @jve:decl-index=0:visual-constraint="28,-88"
