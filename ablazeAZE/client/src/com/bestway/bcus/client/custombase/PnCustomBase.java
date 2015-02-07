
/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.custombase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;

/**
 * @author xxm
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnCustomBase extends JPanel {

    private FlowButton jButton  = null;
    private FlowButton jButton1 = null;
    private FlowButton jButton2 = null;
    private FlowButton jButton3 = null;
    private JLabel  jLabel1  = null;
    private FlowButton jButton4 = null;
    private Image   image    = null;
    private JLabel  jLabel   = null;
	private FlowButton jButton41 = null;

    /**
     * @param arg0
     * @param arg1
     */
    public PnCustomBase(LayoutManager arg0, boolean arg1) {
        super(arg0, arg1);
        initialize();
    }

    /**
     * @param arg0
     */
    public PnCustomBase(LayoutManager arg0) {
        super(arg0);
        initialize();
    }

    /**
     * @param arg0
     */
    public PnCustomBase(boolean arg0) {
        super(arg0);
        initialize();
    }

    /**
     * 
     */
    public PnCustomBase() {
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
        jLabel1.setText("关务基本资料管理向导");
        jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel.setBounds(363, 19, 376, 24);
        jLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        jLabel.setText(CommonVars.getMainDay());
        jLabel.setForeground(new java.awt.Color(0, 102, 102));
        image = CommonVars.getImageSource().getImage("background.gif");
        this.add(jLabel1, null);

        this.add(getFlowButton4(), null);
        this.add(getFlowButton1(), null);
        this.add(getFlowButton2(), null);
        this.add(getFlowButton(), null);
        this.add(getFlowButton3(), null);
        this.add(jLabel, null);
        this.add(getFlowButton41(), null);
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
     * @return javax.swing.FlowButton
     */
    private FlowButton getFlowButton() {
        if (jButton == null) {
            jButton = new FlowButton();
            jButton.setBounds(150, 200, 130, 25);
            jButton
                    .setActionCommand("com.bestway.bcus.client.custombase.FmBaseCode");
            jButton.setText("基础代码");
            jButton.setContentAreaFilled(false);
            jButton.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 12));
            jButton.setForeground(new java.awt.Color(227, 145, 0));
        }
        return jButton;
    }

    /**
     * This method initializes jButton1
     * 
     * @return javax.swing.FlowButton
     */
    private FlowButton getFlowButton1() {
        if (jButton1 == null) {
            jButton1 = new FlowButton();
            jButton1.setBounds(190, 240, 130, 25);
            jButton1
                    .setActionCommand("com.bestway.bcus.client.custombase.FmBaseCodeDep");
            jButton1.setText("部门代码");
            jButton1.setContentAreaFilled(false);
            jButton1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 12));
            jButton1.setForeground(new java.awt.Color(227, 145, 0));
        }
        return jButton1;
    }

    /**
     * This method initializes jButton2
     * 
     * @return javax.swing.FlowButton
     */
    private FlowButton getFlowButton2() {
        if (jButton2 == null) {
            jButton2 = new FlowButton();
            jButton2.setBounds(230, 280, 130, 24);
            jButton2.setText("国家地区");
            jButton2
                    .setActionCommand("com.bestway.bcus.client.custombase.FmBaseCodeCountry");
            jButton2.setContentAreaFilled(false);
            jButton2.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 12));
            jButton2.setForeground(new java.awt.Color(227, 145, 0));
        }
        return jButton2;
    }

    /**
     * This method initializes jButton3
     * 
     * @return javax.swing.FlowButton
     */
    private FlowButton getFlowButton3() {
        if (jButton3 == null) {
            jButton3 = new FlowButton();
            jButton3.setBounds(270, 320, 130, 24);
            jButton3.setText("报关参数");
            jButton3
                    .setActionCommand("com.bestway.bcus.client.custombase.FmBaseCodeParameter");
            jButton3.setContentAreaFilled(false);
            jButton3.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 12));
            jButton3.setForeground(new java.awt.Color(227, 145, 0));
        }
        return jButton3;
    }

    /**
     * This method initializes jButton4
     * 
     * @return javax.swing.FlowButton
     */
    private FlowButton getFlowButton4() {
        if (jButton4 == null) {
            jButton4 = new FlowButton();
            jButton4.setBounds(310, 360, 130, 25);
            jButton4.setText("商品编码");
            jButton4
                    .setActionCommand("com.bestway.bcus.client.custombase.FmBaseCodeHs");
            jButton4.setContentAreaFilled(false);
            jButton4.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 12));
            jButton4.setForeground(new java.awt.Color(227, 145, 0));
        }
        return jButton4;
    }

	/**
	 * This method initializes jButton41	
	 * 	
	 * @return javax.swing.FlowButton	
	 */
	private FlowButton getFlowButton41() {
		if (jButton41 == null) {
			jButton41 = new FlowButton();
			jButton41.setBounds(new Rectangle(350, 400, 130, 25));
			jButton41.setForeground(new Color(227, 145, 0));
			jButton41.setActionCommand("com.bestway.bcus.client.custombase.FmBaseCodeHs");
			jButton41.setContentAreaFilled(false);
			jButton41.setText("商品编码及疫区");
			jButton41.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 12));
		}
		return jButton41;
	}
} // @jve:decl-index=0:visual-constraint="28,-88"
