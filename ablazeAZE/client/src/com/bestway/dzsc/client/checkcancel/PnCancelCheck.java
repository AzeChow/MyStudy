/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.checkcancel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonVars;
import javax.swing.ImageIcon;
/**
 * @author xxm
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PnCancelCheck extends JPanel {

	private JButton jButton = null;
	private JButton jButton1 = null;
	private JLabel jLabel1 = null;
	private JButton jButton2 = null;
	 private Image image = null;
	private JLabel jLabel = null;
	private JLabel jLabel2 = null;
	private JPanel jPanel = null;
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
		jLabel2 = new JLabel();
        jLabel2.setOpaque(false);
		jLabel = new JLabel();
		jLabel1 = new JLabel();
		this.setLayout(null);
		this.setSize(751, 462);
		Dimension panelSize = this.getSize();
		jLabel1.setBounds(137, 12, 214, 32);
		jLabel1.setText("帐册核查核销管理向导");
		jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 20));
		jLabel1.setForeground(new java.awt.Color(255,153,0));
		jLabel.setBounds(355, 23, 376, 21);
		jLabel.setText(CommonVars.getMainDay());
		jLabel.setForeground(new java.awt.Color(0,102,102));
		jLabel2.setBounds(25, 83, 540, 268);
		jLabel2.setText("");
		jLabel2.setIcon(new ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/checkCancel.gif")));
		image = CommonVars.getImageSource().getImage("background.gif");
		this.add(jLabel1, null);
        this.add(getJPanel(), null);
		//this.add(jLabel, null);
		this.add(jLabel, null);
		this.add(jLabel2, null);		
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
			jButton = new JButton();
			jButton.setActionCommand("com.bestway.bcus.client.checkcancel.FmCheckHead");
//			jButton.setText("中期核查");
		     jButton.setForeground(Color.white);
		     jButton.setText("中期核查");
			jButton.setBounds(366, 55, 108, 31);
            jButton.setContentAreaFilled(false);
            jButton.setBorder(null);
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
			jButton1 = new JButton();
			jButton1.setActionCommand("com.bestway.bcus.client.checkcancel.FmCancelHead");
//			jButton1.setText("数据报核");
		     jButton1.setForeground(Color.white);
		     jButton1.setText("数据报核");
			jButton1.setBounds(366, 176, 111, 34);
            jButton1.setContentAreaFilled(false);
            jButton1.setBorder(null);
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
			jButton2 = new JButton();
			jButton2.setActionCommand("com.bestway.bcus.client.checkcancel.FmEmsAnalyHead");
//			jButton2.setText("帐帐分析");
		     jButton2.setForeground(Color.white);
		     jButton2.setText("帐帐分析");
			jButton2.setBounds(363, 113, 112, 34);
            jButton2.setContentAreaFilled(false);
            jButton2.setBorder(null);
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
			jPanel = new JPanel();
			jPanel.setOpaque(false);
			jPanel.setLayout(null);
			jPanel.setBounds(24, 83, 540, 268);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton(), null);
		}
		return jPanel;
	}
  }  //  @jve:decl-index=0:visual-constraint="28,-88"
