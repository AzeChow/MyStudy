/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractexe;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.custom.common.DgBaseSpecialCustomsDeclaration;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgBcsSpecialCustomsDeclaration extends DgBaseSpecialCustomsDeclaration {
	
	private ContractAction contractAction = null;
	
	public DgBcsSpecialCustomsDeclaration() {
		super();
        lbEmsNo.setText("备案号");
		baseEncAction = (ContractExeAction) CommonVars.getApplicationContext()
		.getBean("contractExeAction");
		contractAction = (ContractAction) CommonVars
		.getApplicationContext().getBean("contractAction");
         projectType = ProjectType.BCS  ;
         lbCompanyEmsNo.setVisible(false);
 	  	 tfCompanyEmsNo.setVisible(false);
 		 tfEmsNo.setBounds(213, 21, 206, 19);
 		 btnEmsNo.setBounds(419, 21, 22, 20);
	}
	
	/**
     * 当新增或删除时，显示最初数据
     */
    protected void showPrimalData() {
    	super.showPrimalData();
    	if (dataState == DataState.ADD) {
//    		customsDeclaration.setContract(((Contract)emsHead).getImpContractNo());
//    		this.tfContract.setText(customsDeclaration.getContract());
    	}
    }

	/* (non-Javadoc)
	 * @see com.bestway.client.custom.common.DgBaseSpecialCustomsDeclaration#newCustomsDeclaration()
	 */
	protected BaseCustomsDeclaration newCustomsDeclaration() {
		return new BcsCustomsDeclaration();
	}

	/* (non-Javadoc)
	 * @see com.bestway.client.custom.common.DgBaseSpecialCustomsDeclaration#showCommInfoDialog(int)
	 */
	protected void showCommInfoDialog(int dataState) {
      DgBcsSpecialCustomsDeclarationCommInfo dg = new DgBcsSpecialCustomsDeclarationCommInfo();
      dg.setDataState(dataState);
      dg
              .setEffective(customsDeclaration.getEffective() == null ? false
                      : customsDeclaration.getEffective()
                              .booleanValue());
      Integer type = Integer
		.valueOf(((ItemProperty) cbbImpExpType
				.getSelectedItem()).getCode());
      other1 = systemAction.findCustomsSet(
				new Request(CommonVars.getCurrUser(), true),
				customsDeclaration == null ? null : type);
      dg.setOther1(other1);
      dg.setCustomsDeclaration(customsDeclaration);
      dg.setTableModel(commInfoModel);
      dg.setBaseEncAction(this.baseEncAction);
      dg.setVisible(true);
      getPiceAndWeight();// 是否自动带出净毛重
	}

	@Override
	protected List getEmsHeadList() {
      return contractAction.findContractByProcessExe(new Request(
		 CommonVars.getCurrUser(),true));  
	}
	/**
	 * 修改件，净重，毛重
	 * 
	 */
	protected void getPiceAndWeight() {
		CompanyOther other = CommonVars.getOther();

		if (other == null || (other != null && other.getIsAutoWeight() == null)
				|| (other != null && !other.getIsAutoWeight())) {
			return;
		}
		if(customsDeclaration.getEffective()){
			return;
		}
		customsDeclaration = (BaseCustomsDeclaration) baseEncAction
				.getPiceAndWeight(new Request(CommonVars.getCurrUser()),
						customsDeclaration);
		tfGrossWeight.setValue(customsDeclaration.getGrossWeight());
		tfNetWeight.setValue(customsDeclaration.getNetWeight());
		tfCommodityNum.setValue(customsDeclaration.getCommodityNum());
		customsDeclarationModel.updateRow(customsDeclaration);
	}
} // @jve:decl-index=0:visual-constraint="24,10"

