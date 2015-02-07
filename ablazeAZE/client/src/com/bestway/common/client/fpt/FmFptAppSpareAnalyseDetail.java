package com.bestway.common.client.fpt;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmFptAppSpareAnalyseDetail extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel = null;

	/**
	 * This is the default constructor
	 */
	public FmFptAppSpareAnalyseDetail() {
		super();
		initialize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("申请单余量明细分析报表");
		this.setSize(710, 517);
		this.setContentPane(getJContentPane());
	}


	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 18));
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new PnFptAppSpareAnalyse();
//			jPanel.setLayout(new BorderLayout());
		}
		return jPanel;
	}

	

}
