/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;

import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.awt.Font;
/**
 * @author xxm
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnEmsReport extends JPanel {

    private FlowButton jButton = null;
    private JLabel  jLabel1 = null;
    private Image   image   = null;

	private JLabel jLabel = null;
	private JPanel jPanel = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private FlowLine flowLine1 = null;
	private FlowLine flowLine11 = null;
	private FlowLine flowLine12 = null;
	private FlowLine flowLine13 = null;
    /**
     * @param arg0
     * @param arg1
     */
    public PnEmsReport(LayoutManager arg0, boolean arg1) {
        super(arg0, arg1);
        initialize();
    }

    /**
     * @param arg0
     */
    public PnEmsReport(LayoutManager arg0) {
        super(arg0);
        initialize();
    }

    /**
     * @param arg0
     */
    public PnEmsReport(boolean arg0) {
        super(arg0);
        initialize();
    }

    /**
     * 
     */
    public PnEmsReport() {
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
        this.setSize(639, 462);
        jLabel1.setBounds(137, 12, 141, 32);
        jLabel1.setText("电子帐册报表");
        jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel.setBounds(285, 23, 352, 22);
        jLabel.setText(CommonVars.getMainDay());
        jLabel.setForeground(new java.awt.Color(0,102,102));
        image = CommonVars.getImageSource().getImage("background.gif");
        this.add(getJPanel(), null);
        this.add(jLabel1, null);
        this.add(jLabel, null);
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton() {
        if (jButton == null) {
            jButton = new FlowButton();
            jButton
                    .setActionCommand("com.bestway.bcus.client.manualdeclare.FmReportQueryPrint");
            jButton.setBounds(new Rectangle(364, 110, 118, 47));
		     jButton.setText("报表查询打印");
        }
        return jButton;
    }

    public void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
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
			flowLine13 = new FlowLine();
			flowLine13.setBounds(new Rectangle(236, 74, 30, 106));
			flowLine13.setOnlyDrawLine(true);
			flowLine13.setArrowDirection(0);
			flowLine13.setLineDirection(5);
			flowLine12 = new FlowLine();
			flowLine12.setBounds(new Rectangle(252, 120, 112, 20));
			flowLine12.setArrowDirection(0);
			flowLine11 = new FlowLine();
			flowLine11.setBounds(new Rectangle(128, 65, 124, 18));
			flowLine11.setArrowDirection(0);
			flowLine11.setOnlyDrawLine(true);
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(22, 60, 125, 33));
			jLabel3.setText("电子帐册备案资料");
			jLabel3.setForeground(new java.awt.Color(255,153,0));
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(40, 167, 107, 27));
			jLabel2.setText("进出口报关单");
			jLabel2.setForeground(new java.awt.Color(255,153,0));
			jPanel = new JPanel();
            jPanel.setOpaque(false);
			jPanel.setLayout(null);
			flowLine1 = new FlowLine();
			flowLine1.setBounds(new Rectangle(144, 164, 108, 30));
			flowLine1.setArrowDirection(0);
			flowLine1.setOnlyDrawLine(true);
			jPanel.setBounds(34, 116, 514, 253);
			jPanel.add(getJButton(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3);
			jPanel.add(flowLine1, null);
			jPanel.add(flowLine11);
			jPanel.add(flowLine12, null);
			jPanel.add(flowLine13, null);
		}
		return jPanel;
	}
 } // @jve:decl-index=0:visual-constraint="28,-88"
