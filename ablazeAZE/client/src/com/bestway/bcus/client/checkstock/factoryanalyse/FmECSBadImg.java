package com.bestway.bcus.client.checkstock.factoryanalyse;

import java.awt.BorderLayout;

import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author lyh
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmECSBadImg extends JInternalFrameBase {

	/**
	 * 残次品库存--原材料
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel jContentPane = null;
	private Request request;
	private PnECSBadImgHs pnHs;
	private PnECSBadImgPt pnPt;
	private ECSSection section;


	/**
	 * This is the default constructor
	 */
	public FmECSBadImg() {
		super();
		request = new Request(CommonVars.getCurrUser());
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkBadImg(request);
		initialize();
	}



	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(889, 566);
		this.setContentPane(this.getJContentPane());
		this.getJContentPane().setLayout(new BorderLayout(0, 0));
		this.getJContentPane().add(getPnPt(), BorderLayout.CENTER);
	}

	@Override
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		super.setVisible(b);
		this.setTitle("残次原材料");
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
	
	private PnECSBadImgPt getPnPt() {
		if(pnPt == null) {
			pnPt = new PnECSBadImgPt(this);
		}
		
		return pnPt;
	}
	
	private PnECSBadImgHs getPnHs() {
		if(pnHs == null) {
			pnHs = new PnECSBadImgHs(this);
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
