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
public class PnEmsDeclare extends JPanel {

	private FlowButton jButton = null;
	private FlowButton jButton1 = null;
	private FlowButton jButton2 = null;
	private FlowButton jButton3 = null;
	private JLabel jLabel1 = null;
	private FlowButton jButton4 = null;
	private FlowButton jButton5 = null;
	 private Image image = null;
	private JLabel jLabel = null;
	private JPanel jPanel = null;
	private FlowLine flowLine11 = null;
	private FlowLine flowLine111 = null;
	private FlowLine flowLine1111 = null;
	private FlowLine flowLine11111 = null;
	private FlowLine flowLine11112 = null;
	private FlowLine flowLine111121 = null;
	/**
	 * @param arg0
	 * @param arg1
	 */
	public PnEmsDeclare(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnEmsDeclare(LayoutManager arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * @param arg0
	 */
	public PnEmsDeclare(boolean arg0) {
		super(arg0);
		initialize();
	}

	/**
	 * 
	 */
	public PnEmsDeclare() {
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
		this.setSize(799, 462);
		Dimension panelSize = this.getSize();
		jLabel1.setBounds(137, 12, 214, 32);
		jLabel1.setText("电子帐册申报管理向导");
		jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
		jLabel1.setForeground(new java.awt.Color(255,153,0));
		jLabel.setBounds(359, 22, 341, 22);
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
			jButton.setActionCommand("com.bestway.bcus.client.manualdeclare.FmEmsEdiTr");
		     jButton.setText("经营范围管理");
			jButton.setBounds(new Rectangle(5, 105, 108, 44));
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
			jButton1.setActionCommand("com.bestway.bcus.client.manualdeclare.FmEmsEdiMerger");
		     jButton1.setText("归并关系管理");
			jButton1.setBounds(new Rectangle(144, 104, 97, 44));
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
//			jButton2.setText("电子帐册管理");
			jButton2.setActionCommand("com.bestway.bcus.client.manualdeclare.FmEmsHeadH2k");
			jButton2.setText("电子帐册管理");
			jButton2.setBounds(new Rectangle(278, 103, 96, 45));
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
//			jButton3.setText("帐册分册管理");
			jButton3.setActionCommand("com.bestway.bcus.client.manualdeclare.FmEmsHeadH2kFas");
			jButton3.setBounds(new Rectangle(414, 106, 121, 44));
			jButton3.setText("帐册分册管理");
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
			jButton4.setActionCommand("com.bestway.bcus.client.message.FmMessageQuery");
		     jButton4.setText("报文收发信息查询");
			jButton4.setBounds(new Rectangle(519, 19, 134, 43));
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
			jButton5 = new FlowButton();
			jButton5.setActionCommand("com.bestway.bcus.client.manualdeclare.FmHistoryChange");
			jButton5.setBounds(new Rectangle(518, 203, 132, 44));
			jButton5.setText("历史变更记录查询");
		}
		return jButton5;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			flowLine111121 = new FlowLine();
			flowLine111121.setBounds(new Rectangle(569, 128, 21, 75));
			flowLine111121.setArrowDirection(3);
			flowLine11112 = new FlowLine();
			flowLine11112.setBounds(new Rectangle(569, 61, 21, 67));
			flowLine11112.setArrowDirection(2);
			flowLine11111 = new FlowLine();
			flowLine11111.setBounds(new Rectangle(532, 112, 47, 30));
			flowLine11111.setArrowDirection(0);
			flowLine11111.setOnlyDrawLine(true);
			flowLine1111 = new FlowLine();
			flowLine1111.setBounds(new Rectangle(372, 112, 43, 30));
			flowLine1111.setArrowDirection(0);
			flowLine111 = new FlowLine();
			flowLine111.setBounds(new Rectangle(240, 112, 38, 30));
			flowLine111.setArrowDirection(0);
			jPanel = new JPanel();
            jPanel.setOpaque(false);
			jPanel.setLayout(null);            
			jPanel.setBounds(43, 85, 666, 276);
			flowLine11  = new FlowLine();
			flowLine11.setBounds(new Rectangle(109, 111, 35, 31));
			flowLine11.setArrowDirection(0);
			jPanel.add(getJButton4(), null);
			jPanel.add(getJButton5(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton3(), null);
			jPanel.add(flowLine11,null);
			jPanel.add(flowLine111, null);
			jPanel.add(flowLine1111, null);
			jPanel.add(flowLine11111, null);
			jPanel.add(flowLine11112, null);
			jPanel.add(flowLine111121, null);
		}
		return jPanel;
	}
   }  //  @jve:decl-index=0:visual-constraint="28,-88"
