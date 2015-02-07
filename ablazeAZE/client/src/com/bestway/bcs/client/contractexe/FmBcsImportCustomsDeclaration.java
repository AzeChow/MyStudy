/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractexe;

import java.util.List;

import javax.swing.JOptionPane;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.custom.common.FmBaseImportCustomsDeclaration;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * addCustomsDeclarationData
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmBcsImportCustomsDeclaration extends
		FmBaseImportCustomsDeclaration {

	// private MessageAction messageAction = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ContractAction contractAction = null;

	/**
	 * This method initializes
	 * 
	 * @return
	 * 
	 */

	public FmBcsImportCustomsDeclaration() {
		super();
		baseEncAction = (ContractExeAction) CommonVars.getApplicationContext()
				.getBean("contractExeAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		projectType = ProjectType.BCS;
		// messageAction = (MessageAction) CommonVars.getApplicationContext()
		// .getBean("messageAction");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.client.custom.common.FmBaseImportCustomsDeclaration#
	 * showCustomsDeclarationData(int)
	 */
	protected void showCustomsDeclarationData(int dataState) {
		Contract contract = (Contract) this.cbbEmshead.getSelectedItem();
		if (contract == null) {
			JOptionPane.showMessageDialog(null, "没有正在执行的合同!!", "提示", 1);
			return;
		}
		if (contract.getEmsNo() == null
				|| contract.getEmsNo().trim().equals("")) {
			JOptionPane.showMessageDialog(FmBcsImportCustomsDeclaration.this,
					"纸质手册的编号不能为空", "提示", 0);
			return;
		}
		DgBcsImportCustomsDeclaration dg = new DgBcsImportCustomsDeclaration();
		BaseCustomsDeclaration customs = (BaseCustomsDeclaration) tableModel
				.getCurrentRow();

		customs = baseEncAction.findCustomsDeclaration(
				new Request(CommonVars.getCurrUser(), true), customs.getId());
		dg.setCustomsDeclarationModel(tableModel);
		dg.setDataState(dataState);
		dg.setEmsHead(contract);
		dg.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.client.custom.common.FmBaseImportCustomsDeclaration#
	 * addCustomsDeclarationData()
	 */
	protected void addCustomsDeclarationData() {
		Contract contract = (Contract) this.cbbEmshead.getSelectedItem();

		if (contract == null) {
			JOptionPane.showMessageDialog(null, "没有正在执行的合同!!", "提示", 1);
			return;
		}
		if (contract.getEmsNo() == null
				|| contract.getEmsNo().trim().equals("")) {
			JOptionPane.showMessageDialog(FmBcsImportCustomsDeclaration.this,
					"纸质手册的编号不能为空", "提示", 0);
			return;
		}
		DgBcsImportCustomsDeclaration dg = new DgBcsImportCustomsDeclaration();
		dg.setCustomsDeclarationModel(tableModel);
		dg.setDataState(DataState.ADD);
		dg.setEmsHead(contract);
		dg.setVisible(true);
	}

	// protected List getDataSource() {
	// Contract contract = (Contract) this.cbbEmshead.getSelectedItem();
	// if(contract==null){
	// return new ArrayList();
	// }
	// return this.baseEncAction.findCustomsDeclaration(new Request(
	// CommonVars.getCurrUser()),ImpExpFlag.IMPORT,contract.getEmsNo(),contract.getImpContractNo());
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.client.custom.common.FmBaseImportCustomsDeclaration#
	 * initUIComponents()
	 */
	protected void initUIComponents() {
		// 电子化手册
		cbbEmshead.removeAllItems();
		List contracts = contractAction.findContractByProcessExe(new Request(
				CommonVars.getCurrUser(), true));
		if (contracts != null && contracts.size() > 0) {
			for (int i = 0; i < contracts.size(); i++) {
				this.cbbEmshead.addItem((Contract) contracts.get(i));
			}
			this.cbbEmshead.setRenderer(CustomBaseRender.getInstance()
					.getRender("emsNo", "impContractNo", 100, 150)
					.setForegroundColor(java.awt.Color.red));
		}
		if (this.cbbEmshead.getItemCount() > 0) {
			this.cbbEmshead.setSelectedIndex(0);
		}
		// btnForcePass.setVisible(false);
		lbEmsHead.setText("手册号+合同号:");
	}

}