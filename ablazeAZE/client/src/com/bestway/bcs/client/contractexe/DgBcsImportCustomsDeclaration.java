/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractexe;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsFromMateriel;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.client.custom.common.DgBaseImportCustomsDeclaration;
import com.bestway.client.custom.common.DgBaseImportCustomsDeclarationCommInfo;
import com.bestway.client.custom.common.DgCustomsFromMaterielDialog;
import com.bestway.client.custom.common.DgFromMaterielEdit;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgBcsImportCustomsDeclaration extends
		DgBaseImportCustomsDeclaration {

	public DgBcsImportCustomsDeclaration() {
		super();
		lbEmsNo.setText("备案号");
		baseEncAction = (ContractExeAction) CommonVars.getApplicationContext()
				.getBean("contractExeAction");
		projectType = ProjectType.BCS;
		lbCompanyEmsNo.setVisible(false);
		tfCompanyEmsNo.setVisible(false);
		tfEmsNo.setBounds(239, 10, 150, 21);
	}

	/**
	 * 当新增或删除时，显示最初数据
	 */
	@Override
	protected void showPrimalData() {
		super.showPrimalData();
		if (dataState == DataState.ADD) {
			customsDeclaration.setContract(((Contract) emsHead)
					.getImpContractNo());
			this.tfContract.setText(customsDeclaration.getContract());
		}
	}

	@Override
	protected BaseCustomsDeclaration newCustomsDeclaration() {
		return new BcsCustomsDeclaration();
	}

	@Override
	protected void addFromMateriel() {
		DgCustomsFromMaterielDialog dg = new DgCustomsFromMaterielDialog();
		dg.setProjectType(ProjectType.BCS);
		dg.setBaseEncAction(baseEncAction);
		dg.setMaterialType(MaterielType.MATERIEL);
		dg.setEmsNo(customsDeclaration.getEmsHeadH2k());
		dg.setCustomsDeclaration(customsDeclaration);
		dg.setVisible(true);
	}

	@Override
	protected void lookFromMateriel() {
		if (commInfoModel.getCurrentRow() == null) {
			return;
		}
		BcsCustomsFromMateriel obj = null;
		BaseCustomsDeclarationCommInfo info = (BaseCustomsDeclarationCommInfo) commInfoModel
				.getCurrentRow();
		List list = baseEncAction.getMaterielByCustoms(
				new Request(CommonVars.getCurrUser()), info);
		if (list != null && list.size() > 0) {
			obj = (BcsCustomsFromMateriel) list.get(0);
		}
		if (obj == null) {
			return;
		}
		DgFromMaterielEdit dg = new DgFromMaterielEdit();
		dg.setProjectType(ProjectType.BCS);
		dg.setMateriel(obj.getMateriel());
		dg.setBcsexgbill(obj.getBcsexgbill());
		dg.setBcsimgbill(obj.getBcsimgbill());
		dg.setFromMateriel(obj);
		dg.setBrowse(true);
		dg.setVisible(true);
	}

	@Override
	protected DgBaseImportCustomsDeclarationCommInfo getImportCustomsDeclarationCommInfo() {
		int impExpType = Integer.parseInt(((ItemProperty) this.cbbImpExpType
				.getSelectedItem()).getCode());
		String tradeCode = "";
		if (this.cbbTradeMode.getSelectedItem() != null) {
			tradeCode = ((Trade) cbbTradeMode.getSelectedItem()).getCode();
		}
		DgBcsImportCustomsDeclarationCommInfo dg = new DgBcsImportCustomsDeclarationCommInfo();
		dg.setContract((Contract) emsHead);
		dg.setImpExpType(impExpType);
		dg.setTradeCode(tradeCode);
		return dg;
	}

	/**
	 * 检查商品明细与当前余量
	 * 
	 * @return
	 */
	@Override
	protected String checkCurrentRemainAmount() {
		Integer impExpType = customsDeclaration.getImpExpType();
		boolean isMaterial = EncCommon.isMaterial(impExpType);
		String tradeCode = customsDeclaration.getTradeCode();
		Contract contract = (Contract) emsHead;
		// 进口报关单，且报关单类型不等于海关批准内销的报关单，进行控制
		List<BaseCustomsDeclarationCommInfo> commInfo = commInfoModel.getList();

		return ((ContractExeAction) this.baseEncAction)
				.checkCurrentRemainAmount(
						new Request(CommonVars.getCurrUser()), commInfo,
						contract, isMaterial, impExpType, tradeCode);
	}

	@Override
	protected boolean saveCustom() {
		Date declareDate = this.cbbDeclarationDate.getDate();
		Date beginDate = ((Contract) emsHead).getBeginDate();
		Date endDate = ((Contract) emsHead).getEndDate();
		// Date deferDate=((Contract) emsHead).getDeferDate();
		if (declareDate != null) {
			if (declareDate.compareTo(beginDate) < 0) {
				JOptionPane.showMessageDialog(
						DgBcsImportCustomsDeclaration.this, "申报日期不能小于合同开始生效日期",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		// if (deferDate != null) {
		// if (declareDate!=null&&declareDate.compareTo(deferDate) > 0) {
		// JOptionPane.showMessageDialog(
		// DgBcsImportCustomsDeclaration.this, "申报日期不能大于合同延期期限",
		// "提示", JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		// }else
		if (endDate != null) {
			if (declareDate != null && declareDate.compareTo(endDate) > 0) {
				JOptionPane.showMessageDialog(
						DgBcsImportCustomsDeclaration.this, "申报日期不能大于合同有效日期",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return super.saveCustom();
	}
} // @jve:decl-index=0:visual-constraint="10,10"

