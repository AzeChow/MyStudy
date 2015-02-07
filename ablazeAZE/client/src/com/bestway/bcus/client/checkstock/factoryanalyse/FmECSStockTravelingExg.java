package com.bestway.bcus.client.checkstock.factoryanalyse;

import java.awt.BorderLayout;

import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
public class FmECSStockTravelingExg extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel jContentPane = null;
	private ECSSection section;
	private Request request;
	private PnECSStockTravelingExgHs pnHs;
	private PnECSStockTravelingExgPt pnPt;

	public ECSSection getSection() {
		return section;
	}

	/**
	 * This is the default constructor
	 */
	public FmECSStockTravelingExg() {
		super();
		request = new Request(CommonVars.getCurrUser());
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkStockExg(request);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(889, 566);
		this.setContentPane(getJContentPane());
		this.setTitle("在途成品库存");
		this.getContentPane().add(getPnPt());
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
		}
		return jContentPane;
	}
	
	
	private PnECSStockTravelingExgPt getPnPt() {
		if(pnPt == null) {
			pnPt = new PnECSStockTravelingExgPt(this);
		}
		
		return pnPt;
	}
	
	private PnECSStockTravelingExgHs getPnHs() {
		if(pnHs == null) {
			pnHs = new PnECSStockTravelingExgHs(this);
		}
		
		return pnHs;
	}
	
	
	public void changePn(ECSSection section) {
		if(!section.equals(this.section)) {
			this.section = section;
			
			if(Boolean.TRUE.equals(section.getStockImportIsHs())) {
				getJContentPane().add(getPnPt(), BorderLayout.NORTH);
				getJContentPane().add(getPnHs(), BorderLayout.CENTER);
				getPnPt().setVisible(false);
				getPnHs().setVisible(true);
			} else {
				getJContentPane().add(getPnPt(), BorderLayout.CENTER);
				getJContentPane().add(getPnHs(), BorderLayout.NORTH);
				getPnPt().setVisible(true);
				getPnHs().setVisible(false);
			}
			
		}
	}
	/**
	 * 根据数据显示信息
	 * @param section
	 * @param seqNum
	 */
	public void showData(ECSSection section, Integer seqNum) {
		changePn(section);
		if(Boolean.TRUE.equals(section.getStockImportIsHs())) {
			getPnHs().showData(section,seqNum);
		}else{
			getPnPt().showData(section,seqNum);
		}
	}	
}
