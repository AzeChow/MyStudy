/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractexe;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.custom.common.FmBaseSpecialCustomsDeclaration;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmBcsSpecialCustomsDeclaration extends
		FmBaseSpecialCustomsDeclaration {

	// private MessageAction messageAction = null;

	private ContractAction contractAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmBcsSpecialCustomsDeclaration() {
		super();
		baseEncAction = (ContractExeAction) CommonVars.getApplicationContext()
				.getBean("contractExeAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		this.projectType = ProjectType.BCS;
		// messageAction = (MessageAction) CommonVars.getApplicationContext()
		// .getBean("messageAction");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.client.custom.common.FmBaseImportCustomsDeclaration#showCustomsDeclarationData(int)
	 */
	protected void showCustomsDeclarationData(int dataState) {
		// Contract contract = (Contract) this.cbbEmshead.getSelectedItem();
		// if (contract == null) {
		// JOptionPane.showMessageDialog(null, "没有正在执行的合同!!", "提示", 1);
		// return;
		// }
		// if (contract.getEmsNo() == null ||
		// contract.getEmsNo().trim().equals("")) {
		// JOptionPane.showMessageDialog(FmBcsSpecialCustomsDeclaration.this,
		// "电子帐册的编号不能为空", "提示", 0);
		// return;
		// }
		DgBcsSpecialCustomsDeclaration dg = new DgBcsSpecialCustomsDeclaration();
		BaseCustomsDeclaration customs = (BaseCustomsDeclaration) tableModel
				.getCurrentRow();
		customs = baseEncAction.findCustomsDeclaration(new Request(CommonVars
				.getCurrUser(), true), customs.getId());
		dg.setCustomsDeclarationModel(tableModel);
		dg.setDataState(dataState);
		// dg.setEmsHead(contract);
		dg.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.client.custom.common.FmBaseImportCustomsDeclaration#addCustomsDeclarationData()
	 */
	protected void addCustomsDeclarationData() {
		// Contract contract = (Contract) this.cbbEmshead.getSelectedItem();
		// if (contract == null) {
		// JOptionPane.showMessageDialog(null, "没有正在执行的合同!!",
		// "提示", 1);
		// return;
		// }
		// if (contract.getEmsNo() == null
		// || contract.getEmsNo().trim().equals("")) {
		// JOptionPane.showMessageDialog(
		// FmBcsSpecialCustomsDeclaration.this, "电子帐册的编号不能为空",
		// "提示", 0);
		// return;
		// }
		DgBcsSpecialCustomsDeclaration dg = new DgBcsSpecialCustomsDeclaration();
		dg.setCustomsDeclarationModel(tableModel);
		dg.setDataState(DataState.ADD);
		// dg.setEmsHead(contract);
		dg.setVisible(true);
	}

	//	
	// protected List getDataSource() {
	// Contract contract = (Contract) this.cbbEmshead.getSelectedItem();
	// if(contract==null){
	// return new ArrayList();
	// }
	// return this.baseEncAction.findCustomsDeclaration(new Request(
	// CommonVars.getCurrUser()),ImpExpFlag.SPECIAL,contract.getEmsNo(),contract.getImpContractNo());
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.client.custom.common.FmBaseImportCustomsDeclaration#initUIComponents()
	 */
	protected void initUIComponents() {
		// 电子帐册
		// cbbEmshead.removeAllItems();
		// List contracts= contractAction.findContractByProcessExe(new Request(
		// CommonVars.getCurrUser(),true));
		// if (contracts != null && contracts.size() > 0) {
		// for (int i = 0; i < contracts.size(); i++) {
		// this.cbbEmshead.addItem((Contract) contracts.get(i));
		// }
		// this.cbbEmshead.setRenderer(CustomBaseRender.getInstance()
		// .getRender("impContractNo", "emsNo", 100,
		// 100).setForegroundColor(java.awt.Color.red));
		// }
		// if(this.cbbEmshead.getItemCount()>0){
		// this.cbbEmshead.setSelectedIndex(0);
		// }
		//        
		// lbEmsHead.setText("执行的合同(合同号+帐册号):");
	}

} // @jve:decl-index=0:visual-constraint="10,10"
