package com.bestway.common.client.fpt;

import java.awt.BorderLayout;

import com.bestway.common.fpt.entity.TempFptAppParam;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgFptAppSpareAnalyseDetail extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private PnFptAppSpareAnalyse pnFpt = null;
	private TempFptAppParam param = null;  //  @jve:decl-index=0:
	/**
	 * This is the default constructor
	 */
	public DgFptAppSpareAnalyseDetail() {
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
		this.setSize(738, 583);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				pnFpt.showData(getParam());
				pnFpt.search();
			}
		});
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
			jContentPane.add(getPnFpt(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes pnFpt	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private PnFptAppSpareAnalyse getPnFpt() {
		if (pnFpt == null) {
			pnFpt = new PnFptAppSpareAnalyse();
//			pnFpt.setLayout(new BorderLayout());
		}
		return pnFpt;
	}

	/**
	 * @return the param
	 */
	public TempFptAppParam getParam() {
		return param;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(TempFptAppParam param) {
		this.param = param;
	}

	

}
