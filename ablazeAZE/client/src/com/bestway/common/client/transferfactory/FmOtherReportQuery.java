/*
 * Created on 2005-5-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.transferfactory;


import java.awt.Rectangle;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.client.common.PnImageBackground;
import com.bestway.client.selfcheck.DgSupplierBalanceAllInfoNew;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FmOtherReportQuery extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel jContentPane = null;
	private PnImageBackground jPanel4 = null;
	private JPanel jPanel5 = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private ButtonGroup buttonGroup = null;  //  @jve:decl-index=0:visual-constraint="191,33"
	private JRadioButton jRadioButton2 = null;
	private JRadioButton jRadioButton3 = null;
	private JRadioButton jRadioButton4 = null;
	private JRadioButton rbCusTranfer = null;
	private JRadioButton rbVendorTranfer = null;
	/**
	 * This is the default constructor
	 */
	public FmOtherReportQuery() {
		super();
		getButtonGroup();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("统计报表打印");
		this.setSize(651, 485);
		this.setContentPane(getJContentPane());
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel4(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new PnImageBackground();
			jPanel4.setLayout(null);
			jPanel4.add(getJPanel5(), null);
		}
		return jPanel4;
	}
	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setBounds(20, 40, 599, 175);
			jPanel5.setOpaque(false);
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "其他查询统计报表", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.SystemColor.activeCaption));
			jPanel5.add(getJRadioButton(), null);
			jPanel5.add(getJRadioButton1(), null);
			jPanel5.add(getJRadioButton2(), null);
			jPanel5.add(getJRadioButton3(), null);
			jPanel5.add(getJRadioButton4(), null);
			jPanel5.add(getRbCusTranfer(), null);
			jPanel5.add(getRbVendorTranfer(), null);
		}
		return jPanel5;
	}
	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(123, 33, 184, 23);
			jRadioButton.setText("备案安排周报表");
			jRadioButton.setOpaque(false);
			jRadioButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					 DgPutRecordPlan dg = new DgPutRecordPlan();
					 dg.setVisible(true);
					
				}
			});			
		}
		return jRadioButton;
	}
 
	private JRadioButton getJRadioButton1(){
		 if(jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(123, 60, 183, 23);
			jRadioButton1.setText("转厂安排周报表");
			jRadioButton1.setOpaque(false);
			jRadioButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					DgTransferFactoryPlan dg = new DgTransferFactoryPlan();
					dg.setVisible(true);
	
				}
			});
		}
		return jRadioButton1;
	}
	/**
	 * This method initializes buttonGroup	
	 * 	
	 * @return javax.swing.ButtonGroup	
	 */    
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getJRadioButton());
			buttonGroup.add(getJRadioButton1());
			buttonGroup.add(getJRadioButton2());
			buttonGroup.add(getJRadioButton3());
			buttonGroup.add(getJRadioButton4());
			buttonGroup.add(getRbCusTranfer());
			buttonGroup.add(getRbVendorTranfer());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jRadioButton2	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setBounds(124, 91, 115, 21);
			jRadioButton2.setOpaque(false);
			jRadioButton2.setText("关封明细报表");
			jRadioButton2.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					DgEnvelopListReport dg = new DgEnvelopListReport();
					dg.setVisible(true);
				}
			});
		}
		return jRadioButton2;
	}
	/**
	 * This method initializes jRadioButton3	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setBounds(new java.awt.Rectangle(124,118,135,21));
			jRadioButton3.setText("转厂客户对帐表");
			jRadioButton3.setOpaque(false);
			jRadioButton3.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    					
					DgTransferClientCollate dg = new DgTransferClientCollate();
					dg.setVisible(true);
				}
			});
		}
		return jRadioButton3;
	}
	/**
	 * This method initializes jRadioButton4	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setBounds(new java.awt.Rectangle(124,142,179,21));
			jRadioButton4.setText("送货转厂金额对照表");
			jRadioButton4.setOpaque(false);
			jRadioButton4.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    					
					DgTransCustomsCollate dg = new DgTransCustomsCollate();
					dg.setVisible(true);
				}
			});
		}
		return jRadioButton4;
	}
	/**
	 * This method initializes rbCusTranfer	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbCusTranfer() {
		if (rbCusTranfer == null) {
			rbCusTranfer = new JRadioButton();
			rbCusTranfer.setBounds(new Rectangle(358, 60, 144, 21));
			rbCusTranfer.setOpaque(false);
			rbCusTranfer.setText("客户转厂差额总表");
			rbCusTranfer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSupplierBalanceAllInfoNew dg = new DgSupplierBalanceAllInfoNew();
					dg.setTitle("客户转厂差额总表");
					dg.setIsM(false);
					dg.setVisible(true);
				}
			});
		}
		return rbCusTranfer;
	}
	/**
	 * This method initializes rbVendorTranfer	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbVendorTranfer() {
		if (rbVendorTranfer == null) {
			rbVendorTranfer = new JRadioButton();
			rbVendorTranfer.setBounds(new Rectangle(358, 33, 150, 21));
			rbVendorTranfer.setOpaque(false);
			rbVendorTranfer.setText("供应商转厂差额总表");
			rbVendorTranfer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSupplierBalanceAllInfoNew dg = new DgSupplierBalanceAllInfoNew();
					dg.setTitle("供应商转厂差额总表");
					dg.setIsM(true);
					dg.setVisible(true);
				}
			});
		}
		return rbVendorTranfer;
	}
 }
