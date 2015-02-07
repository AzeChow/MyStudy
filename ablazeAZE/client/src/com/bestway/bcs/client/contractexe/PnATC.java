/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractexe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonVars;
import java.awt.Font;
/**
 * @author xxm
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PnATC extends JPanel {

	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JLabel jLabel1 = null;
	private JButton jButton4 = null;
    private Image image = null;
	private JLabel jLabel = null;
	/**
	 * @param arg0
	 * @param arg1
	 */
	public PnATC(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnATC(LayoutManager arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnATC(boolean arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * 
	 */
	public PnATC() {
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
		this.setSize(639, 462);			
		jLabel1.setBounds(137, 12, 94, 32);
		jLabel1.setText("合同通关");
		jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		jLabel1.setForeground(new java.awt.Color(255,145,0));
		jLabel.setBounds(236, 22, 391, 22);
		jLabel.setText(CommonVars.getMainDay());
		jLabel.setForeground(new java.awt.Color(0,102,102));
		 image = CommonVars.getImageSource().getImage("background.gif");		
		this.add(jLabel1, null);
		//this.add(jLabel, null);
		this.add(getJButton4(), null);
		this.add(getJButton2(), null);
		this.add(getJButton3(), null);	
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
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(128, 175, 114, 24);
			jButton2.setText("进口报关单");
            jButton2.setContentAreaFilled(false);
			jButton2.setActionCommand("com.bestway.bcs.client.contractexe.FmBcsImportCustomsDeclaration");
			jButton2.setForeground(new java.awt.Color(227, 145, 0));
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
			jButton3 = new JButton();
			jButton3.setBounds(128, 224, 114, 24);
			jButton3.setText("出口报关单");
            jButton3.setContentAreaFilled(false);
			jButton3.setActionCommand("com.bestway.bcs.client.contractexe.FmBcsExportCustomsDeclaration");
            jButton3.setForeground(new java.awt.Color(227, 145, 0));
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
			jButton4 = new JButton();
			jButton4.setBounds(128, 273, 114, 24);
			jButton4.setText("特殊报关单");
            jButton4.setContentAreaFilled(false);
            jButton4.setForeground(new java.awt.Color(227, 145, 0));
			jButton4.setActionCommand("com.bestway.bcs.client.contractexe.FmBcsSpecialCustomsDeclaration");            
		}
		return jButton4;
	}
}  //  @jve:decl-index=0:visual-constraint="28,-88"
