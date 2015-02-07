/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

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
public class PnEms extends JPanel {

	private FlowButton jButton = null;
	private FlowButton jButton1 = null;
	private FlowButton jButton2 = null;
	private FlowButton jButton3 = null;
	private JLabel jLabel1 = null;
	 private Image image = null;
	private JLabel jLabel = null;
	private JPanel jPanel = null;
	private FlowButton jButton4 = null;
	private FlowLine flowLine73;
	private FlowLine flowLine731 = null;
	private FlowLine flowLine7311 = null;
	private FlowLine flowLine73111 = null;
	private FlowButton jButton31 = null;
	private FlowLine flowLine73112 = null;
	/**
	 * @param arg0
	 * @param arg1
	 */
	public PnEms(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnEms(LayoutManager arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnEms(boolean arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * 
	 */
	public PnEms() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
//		jLabel2 = new JLabel();
//        jLabel2.setOpaque(false);
//        jLabel2.setIcon(new ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/ems.gif")));
		jLabel = new JLabel();
		jLabel1 = new JLabel();
		this.setLayout(null);
		this.setSize(786, 462);
		Dimension panelSize = this.getSize();
		jLabel1.setBounds(137, 12, 178, 32);
		jLabel1.setText("电子帐册管理向导");
		jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		jLabel1.setForeground(new java.awt.Color(255,153,0));
		jLabel.setBounds(324, 22, 314, 21);
		jLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		jLabel.setText(CommonVars.getMainDay());
		jLabel.setForeground(new java.awt.Color(0,102,102));
		image = CommonVars.getImageSource().getImage("background.gif");
        this.add(getJPanel(), null);
		this.add(jLabel1, null);
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
			jButton.setActionCommand("com.bestway.bcus.client.innermerge.PnInnerMerge");
			jButton.setBounds(new Rectangle(3, 118, 125, 35));
     jButton.setText("企业内部归并管理");
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
			jButton1.setActionCommand("com.bestway.bcus.client.manualdeclare.PnEmsDeclare");
			jButton1.setBounds(new Rectangle(150, 119, 97, 34));
			jButton1.setText("电子帐册申报");
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
			jButton2.setActionCommand("com.bestway.bcus.client.enc.PnEnc");
			jButton2.setBounds(new Rectangle(264, 119, 96, 33));
			jButton2.setText("电子帐册通关");
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
		     jButton3.setText("滚动核销");
			jButton3.setActionCommand("com.bestway.bcus.client.checkcancel.PnCancelCheck");
			jButton3.setBounds(new Rectangle(381, 119, 71, 34));
		}
		return jButton3;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			flowLine73112 = new FlowLine();
			flowLine73112.setBounds(new Rectangle(450, 119, 27, 30));
			flowLine73112.setArrowDirection(0);
			flowLine73111 = new FlowLine();
			flowLine73111.setBounds(new Rectangle(568, 120, 44, 30));
			flowLine73111.setArrowDirection(0);
			flowLine7311 = new FlowLine();
			flowLine7311.setBounds(new Rectangle(358, 119, 24, 30));
			flowLine7311.setArrowDirection(0);
			flowLine731 = new FlowLine();
			flowLine731.setBounds(new Rectangle(244, 119, 21, 31));
			flowLine731.setArrowDirection(0);
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(43, 135, 721, 237);
			flowLine73 = new FlowLine();
			flowLine73.setBounds(new Rectangle(125, 119, 26, 31));
			flowLine73.setArrowDirection(0);
			flowLine73.setText("");
			jPanel.add(flowLine73,null);
            jPanel.add(getJButton1(), null);
            jPanel.add(getJButton2(), null);
            jPanel.add(getJButton(), null);
            jPanel.add(getJButton3(), null);
            jPanel.setOpaque(false);
            jPanel.add(getJButton4(), null);
            jPanel.add(flowLine731, null);
            jPanel.add(flowLine7311, null);
            jPanel.add(flowLine73111, null);
            jPanel.add(getJButton31(), null);
            jPanel.add(flowLine73112, null);
		}
		return jPanel;
	}
	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new FlowButton();
//            com.bestway.bcus.client.manualdeclare.PnEmsReport
            jButton4.setActionCommand("com.bestway.bcus.client.manualdeclare.PnEmsReport");
			jButton4.setBounds(new Rectangle(611, 119, 109, 34));
			jButton4.setText("电子帐册统计表");
		}
		return jButton4;
	}

	/**
	 * This method initializes jButton31	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getJButton31() {
		if (jButton31 == null) {
			jButton31 = new FlowButton();
			jButton31.setBounds(new Rectangle(478, 119, 91, 34));
			jButton31.setText("中期核查");
			jButton31.setActionCommand("com.bestway.bcus.client.checkcancel.FmCheckParameter");
		}
		return jButton31;
	}
  }  //  @jve:decl-index=0:visual-constraint="28,-88"
