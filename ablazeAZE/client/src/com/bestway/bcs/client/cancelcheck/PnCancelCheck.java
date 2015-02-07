/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.cancelcheck;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
/**
 * @author xxm
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PnCancelCheck extends JPanel {

	private JButton jButton = null;	
	private JLabel jLabel1 = null;	
    private Image image = null;
	private JLabel jLabel = null;
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
		this.setSize(639, 462);			
		jLabel1.setBounds(137, 12, 96, 32);
		jLabel1.setText("合同核销");
        image = CommonVars.getImageSource().getImage("background.gif");
		jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 20));
		jLabel1.setForeground(new java.awt.Color(255,153,0));				
		jLabel.setBounds(242, 19, 385, 24);
		jLabel.setText(CommonVars.getMainDay());
		jLabel.setForeground(new java.awt.Color(0,102,102));
		this.add(jLabel1, null);
		//this.add(jLabel, null);		
		this.add(getJButton(), null);			
		this.add(jLabel, null);
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(166, 276, 123, 25);
			jButton.setActionCommand("com.bestway.bcus.client.manualdeclare.FmEmsEdiTr");
			jButton.setText("数据报核");
            jButton.setContentAreaFilled(false);
            jButton.setForeground(new java.awt.Color(227, 145, 0));
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
	
}  //  @jve:decl-index=0:visual-constraint="28,-88"
