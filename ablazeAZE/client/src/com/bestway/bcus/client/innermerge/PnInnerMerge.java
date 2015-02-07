/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;
import java.awt.Font;
/**
 * @author xxm
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PnInnerMerge extends JPanel {

	private FlowButton jButton = null;
	private FlowButton jButton1 = null;
	private FlowButton jButton2 = null;
	private FlowButton jButton3 = null;
	private JLabel jLabel1 = null;
	private FlowButton jButton4 = null;
	 private Image image = null;
	private JLabel jLabel = null;
	private JPanel jPanel = null;
	private JTextPane jTextPane = null;
	private JLabel jLabeltmp = null;
	private FlowLine flowLine1 = null;
	private FlowLine flowLine11 = null;
	/**
	 * @param arg0
	 * @param arg1
	 */
	public PnInnerMerge(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnInnerMerge(LayoutManager arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnInnerMerge(boolean arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * 
	 */
	public PnInnerMerge() {
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
		this.setSize(745, 462);
		
		Dimension panelSize = this.getSize();
		jLabel1.setBounds(137, 12, 213, 32);
		jLabel1.setText("企业内部归并管理向导");
		jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		jLabel1.setForeground(new java.awt.Color(255,153,0));
		jLabel.setBounds(362, 20, 382, 22);
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
			jButton.setActionCommand("com.bestway.common.client.materialbase.FmCommonBaseCode");
			jButton.setBounds(new Rectangle(38, 86, 85, 128));
			jButton.setText("物流通用代码");
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
			jButton1.setActionCommand("com.bestway.bcus.client.innermerge.FmInnerMerge");
//			jButton1.setText("物料主档");
//            jButton1.setOpaque(false);
			jButton1.setBounds(new Rectangle(184, 101, 70, 54));
			jButton1.setText("内部归并");
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
			jButton2.setActionCommand("com.bestway.bcus.client.innermerge.FmFactoryCustoms");
			jButton2.setBounds(new Rectangle(309, 101, 151, 53));
			jButton2.setText("报关与工厂资料对照");
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
			jButton3.setForeground(Color.white);
			jButton3.setActionCommand("com.bestway.common.client.materialbase.FmEnterpriseBomManage");
			jButton3.setBounds(new Rectangle(374, 90, 45, 129));
			jButton3.setText("产品结构BOM");
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
			jButton4.setActionCommand("com.bestway.common.client.materialbase.FmCustomBomManage");
			jButton4.setBounds(new Rectangle(479, 113, 75, 74));
			jButton4.setText("成品单耗");

		}
		return jButton4;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			flowLine11 = new FlowLine();
			flowLine11.setBounds(new Rectangle(253, 111, 56, 31));
			flowLine11.setArrowDirection(0);
			jLabeltmp = new JLabel();
			jLabeltmp.setBounds(new Rectangle(39, 102, 110, 53));
			jLabeltmp.setText("报关常用工厂物料");
			jLabeltmp.setForeground(new java.awt.Color(255,153,0));
			jPanel = new JPanel();
            jPanel.setOpaque(false);
			jPanel.setLayout(null);
			jPanel.setBounds(61, 94, 656, 290);
//			jPanel.add(getJButton4(), null);
			jPanel.add(jLabeltmp, null);
			flowLine1 = new FlowLine();
			flowLine1.setBounds(new Rectangle(142, 111, 44, 31));
			flowLine1.setArrowDirection(0);
			jPanel.add(flowLine1,null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), null);
//			jPanel.add(getJButton(), null);
			jPanel.add(flowLine11, null);
//			jPanel.add(getJButton3(), null);
//			jPanel.add(getJTextPane(), null);
            
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setText("报关常用物料");
			jTextPane.setForeground(new Color(255,102,0));
			jTextPane.setBounds(new java.awt.Rectangle(178,103,18,112));
		}
		return jTextPane;
	}
      }  //  @jve:decl-index=0:visual-constraint="28,-88"
