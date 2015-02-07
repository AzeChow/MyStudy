/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractexe;

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
public class PnDzscATC extends JPanel {

	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JLabel jLabel1 = null;
	private JButton jButton4 = null;
	private JButton jButton5 = null;
    private Image image = null;
	private JLabel jLabel = null;
	/**
	 * @param arg0
	 * @param arg1
	 */
	public PnDzscATC(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnDzscATC(LayoutManager arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnDzscATC(boolean arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * 
	 */
	public PnDzscATC() {
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
		this.setSize(905, 462);			
		jLabel1.setBounds(137, 12, 93, 32);
		jLabel1.setText("合同执行");		
		jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 20));
		jLabel1.setForeground(new java.awt.Color(255,153,0));
		jLabel.setBounds(244, 25, 469, 18);
		jLabel.setText(CommonVars.getMainDay());
		jLabel.setForeground(new java.awt.Color(0,102,102));
	    image = CommonVars.getImageSource().getImage("background.gif");		
		this.add(jLabel1, null);
		this.add(getJButton4(), null);
		this.add(getJButton5(), null);
		this.add(getJButton1(), null);
		this.add(getJButton2(), null);
		this.add(getJButton(), null);
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
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(100, 255, 160, 25);
			jButton.setActionCommand("com.bestway.dzsc.client.contractexe.FmDzscImpExpRequestBill");
			jButton.setText("手册报关清单申请单");
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
			jButton1.setBounds(123, 294, 112, 25);
			jButton1.setActionCommand("com.bestway.bcus.client.enc.FmApplyToCustomsBillList");
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
			jButton2 = new JButton();
			jButton2.setBounds(123, 337, 114, 24);
			jButton2.setText("进口报关单");
			jButton2.setActionCommand("com.bestway.dzsc.client.contractexe.FmDzscImportCustomsDeclaration");
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
			jButton3.setBounds(260, 338, 115, 24);
			jButton3.setText("出口报关单");
			jButton3.setActionCommand("com.bestway.dzsc.client.contractexe.FmDzscExportCustomsDeclaration");
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
			jButton4.setBounds(205, 381, 147, 24);
			jButton4.setText("特殊报关单");
			jButton4.setActionCommand("com.bestway.dzsc.client.contractexe.FmDzscSpecialCustomsDeclaration");
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
			jButton5 = new JButton();
			jButton5.setBounds(378, 381, 145, 24);
			jButton5.setText("大小清单差异分析");
			jButton5.setActionCommand("com.bestway.bcus.client.enc.FmCustomsDeclarationDiffAnalyse");
		}
		return jButton5;
	}
  }  //  @jve:decl-index=0:visual-constraint="28,-88"
