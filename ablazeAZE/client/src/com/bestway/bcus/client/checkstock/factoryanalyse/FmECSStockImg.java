package com.bestway.bcus.client.checkstock.factoryanalyse;

import java.awt.BorderLayout;

import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author xxm
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmECSStockImg extends JInternalFrameBase {

	/**
	 * 
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel jContentPane = null;
	private Request request;
	private PnECSStockImgHs pnHs;
	private PnECSStockImgPt pnPt;
	private ECSSection section;


	/**
	 * This is the default constructor
	 */
	public FmECSStockImg() {
		super();
		request = new Request(CommonVars.getCurrUser());
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkStockBuyImg(request);
		initialize();
	}



	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(889, 566);
		this.setTitle("料件库存");
		this.setContentPane(this.getJContentPane());
		this.getJContentPane().setLayout(new BorderLayout(0, 0));
		this.getJContentPane().add(getPnPt(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
		}
		return jContentPane;
	}
	
	private PnECSStockImgPt getPnPt() {
		if(pnPt == null) {
			pnPt = new PnECSStockImgPt(this);
		}
		
		return pnPt;
	}
	
	private PnECSStockImgHs getPnHs() {
		if(pnHs == null) {
			pnHs = new PnECSStockImgHs(this);
		}
		
		return pnHs;
	}
	
	public void changePn(ECSSection section) {
		if(!section.equals(this.section)) {
			this.section = section;
			
			if(Boolean.TRUE.equals(section.getStockImportIsHs())) {
//				getJContentPane().add(getPnPt(), BorderLayout.NORTH);
				getJContentPane().add(getPnHs(), BorderLayout.CENTER);
				getPnPt().setVisible(false);
				getPnHs().setVisible(true);
			} else {
				getJContentPane().add(getPnPt(), BorderLayout.CENTER);
//				getJContentPane().add(getPnHs(), BorderLayout.NORTH);
				getPnPt().setVisible(true);
				getPnHs().setVisible(false);
			}
			
		}
	}
	
	public ECSSection getSection() {
		return this.section;
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
