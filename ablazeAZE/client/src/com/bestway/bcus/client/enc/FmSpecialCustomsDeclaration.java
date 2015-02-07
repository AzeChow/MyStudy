/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.custom.common.FmBaseSpecialCustomsDeclaration;
import com.bestway.client.fixasset.FixAssetQuery;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmSpecialCustomsDeclaration extends
		FmBaseSpecialCustomsDeclaration {

	protected ManualDeclareAction manualDeclareAction = null;

	public FmSpecialCustomsDeclaration() {
		super();
		initialize();
		baseEncAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		projectType = ProjectType.BCUS;
	}

	/**
	 * 
	 */
	protected void showCustomsDeclarationData(int dataState) {
		EmsHeadH2k ems = null;
		List contracts = manualDeclareAction
				.findEmsHeadH2kInExecuting(new Request(
						CommonVars.getCurrUser(), true));
		if (contracts != null && contracts.size() > 0) {
			ems = (EmsHeadH2k) contracts.get(0);

		}
		if (ems == null) {
			JOptionPane.showMessageDialog(null, "没有正在执行的电子账册!!", "提示", 1);
			return;
		}
		if (ems.getEmsNo() == null || ems.getEmsNo().trim().equals("")) {
			JOptionPane.showMessageDialog(FmSpecialCustomsDeclaration.this,
					"电子帐册的编号不能为空", "提示", 0);
			return;
		}
		DgSpecialCustomsDeclaration dg = new DgSpecialCustomsDeclaration();
		BaseCustomsDeclaration customs = (BaseCustomsDeclaration) tableModel
				.getCurrentRow();
		customs = baseEncAction.findCustomsDeclaration(new Request(CommonVars
				.getCurrUser(), true), customs.getId());
		dg.setCustomsDeclarationModel(tableModel);
		dg.setDataState(dataState);
		dg.setEmsHead(ems);
		dg.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.client.custom.common.FmBaseImportCustomsDeclaration#addCustomsDeclarationData()
	 */
	protected void addCustomsDeclarationData() {
		EmsHeadH2k ems = null;
		List contracts = manualDeclareAction
				.findEmsHeadH2kInExecuting(new Request(
						CommonVars.getCurrUser(), true));
		if (contracts != null && contracts.size() > 0) {
			ems = (EmsHeadH2k) contracts.get(0);

		}
		if (ems == null) {
			JOptionPane.showMessageDialog(null, "没有正在执行的电子账册!!", "提示", 1);
			return;
		}
		if (ems.getEmsNo() == null || ems.getEmsNo().trim().equals("")) {
			JOptionPane.showMessageDialog(FmSpecialCustomsDeclaration.this,
					"电子帐册的编号不能为空", "提示", 0);
			return;
		}
		DgSpecialCustomsDeclaration dg = new DgSpecialCustomsDeclaration();
		dg.setCustomsDeclarationModel(tableModel);
		dg.setDataState(DataState.ADD);
		dg.setEmsHead(ems);
		dg.setVisible(true);

	}

	// protected List getDataSource() {
	// EmsHeadH2k emsHead=(EmsHeadH2k)this.cbbEmshead.getSelectedItem();
	// if(emsHead==null){
	// return new ArrayList();
	// }
	// return this.baseEncAction.findCustomsDeclaration(new Request(
	// CommonVars.getCurrUser()),ImpExpFlag.SPECIAL,emsHead.getEmsNo(),null);
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.client.custom.common.FmBaseImportCustomsDeclaration#initUIComponents()
	 */
	protected void initUIComponents() {
		// 电子帐册
		// // cbbEmshead.removeAllItems();
		// List contracts = manualDeclareAction
		// .findEmsHeadH2kInExecuting(new Request(
		// CommonVars.getCurrUser(), true));
		// if (contracts != null && contracts.size() > 0) {
		// for (int i = 0; i < contracts.size(); i++) {
		// this.cbbEmshead.addItem((EmsHeadH2k) contracts.get(i));
		// }
		// this.cbbEmshead.setRenderer(CustomBaseRender.getInstance()
		// .getRender("preEmsNo", "emsNo", 0, 150));
		// }
		// if (this.cbbEmshead.getItemCount() > 0) {
		// this.cbbEmshead.setSelectedIndex(0);
		// }
		//
		// lbEmsHead.setText("正在执行的帐册号:");
	}
} // @jve:decl-index=0:visual-constraint="10,10"
