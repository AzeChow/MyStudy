/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractexe;

import java.util.List;

import javax.swing.JOptionPane;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.custom.common.FmBaseExportCustomsDeclaration;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.contractexe.action.DzscContractExeAction;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmDzscExportCustomsDeclaration extends
		FmBaseExportCustomsDeclaration {
	// private MessageAction messageAction = null;

	private DzscAction dzscAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmDzscExportCustomsDeclaration() {
		super();
		this.projectType = ProjectType.DZSC;
		baseEncAction = (DzscContractExeAction) CommonVars
				.getApplicationContext().getBean("dzscContractExeAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		this.btnForcePass.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.client.custom.common.FmBaseImportCustomsDeclaration#
	 * showCustomsDeclarationData(int)
	 */
	protected void showCustomsDeclarationData(int dataState) {
		DzscEmsPorHead contract = (DzscEmsPorHead) this.cbbEmshead
				.getSelectedItem();
		if (contract == null) {
			JOptionPane.showMessageDialog(null, "没有正在执行的合同!!", "提示", 1);
			return;
		}
		if (contract.getEmsNo() == null
				|| contract.getEmsNo().trim().equals("")) {
			JOptionPane.showMessageDialog(FmDzscExportCustomsDeclaration.this,
					"电子帐册的编号不能为空", "提示", 0);
			return;
		}
		DgDzscExportCustomsDeclaration dg = new DgDzscExportCustomsDeclaration();
		BaseCustomsDeclaration customs = (BaseCustomsDeclaration) tableModel
				.getCurrentRow();
		customs = baseEncAction.findCustomsDeclaration(new Request(CommonVars
				.getCurrUser(), true), customs.getId());
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
		DzscEmsPorHead contract = (DzscEmsPorHead) this.cbbEmshead
				.getSelectedItem();
		if (contract == null) {
			JOptionPane.showMessageDialog(null, "没有正在执行的合同!!", "提示", 1);
			return;
		}
		if (contract.getEmsNo() == null
				|| contract.getEmsNo().trim().equals("")) {
			JOptionPane.showMessageDialog(FmDzscExportCustomsDeclaration.this,
					"电子帐册的编号不能为空", "提示", 0);
			return;
		}
		DgDzscExportCustomsDeclaration dg = new DgDzscExportCustomsDeclaration();
		dg.setCustomsDeclarationModel(tableModel);
		dg.setDataState(DataState.ADD);
		dg.setEmsHead(contract);
		dg.setVisible(true);
	}

	//	
	// protected List getDataSource() {
	// Contract contract = (Contract) this.cbbEmshead.getSelectedItem();
	// if(contract==null){
	// return new ArrayList();
	// }
	// return this.baseEncAction.findCustomsDeclaration(new Request(
	// CommonVars.getCurrUser()),ImpExpFlag.EXPORT,contract.getEmsNo(),contract.getImpContractNo());
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.client.custom.common.FmBaseImportCustomsDeclaration#
	 * initUIComponents()
	 */
	protected void initUIComponents() {
		// 电子帐册
		cbbEmshead.removeAllItems();
		List contracts = dzscAction.findExeEmsPorHead(new Request(CommonVars
				.getCurrUser(), true));
		if (contracts != null && contracts.size() > 0) {
			for (int i = 0; i < contracts.size(); i++) {
				this.cbbEmshead.addItem((DzscEmsPorHead) contracts.get(i));
			}
			this.cbbEmshead.setRenderer(CustomBaseRender.getInstance()
					.getRender("emsNo", "imContractNo", 100, 150));
		}
		if (this.cbbEmshead.getItemCount() > 0) {
			this.cbbEmshead.setSelectedIndex(0);
		}

		lbEmsHead.setText("手册号+合同号:");
	}

} // @jve:decl-index=0:visual-constraint="10,10"
