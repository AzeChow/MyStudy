/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.bestway.bcus.client.enc;

import java.util.List;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
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
public class DgSpecialCustomsDeclaration extends DgBaseSpecialCustomsDeclaration {
	
	protected ManualDeclareAction manualDeclareAction = null;
	
	public DgSpecialCustomsDeclaration() {
		super();
		baseEncAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
		.getApplicationContext().getBean("manualdeclearAction");
        projectType = ProjectType.BCUS   ;
	}
	
	/**
     * 当新增或删除时，显示最初数据
     */
    protected void showData() {
    	super.showData();
//   		this.tfCompanyEmsNo.setText(((EmsHeadH2k)emsHead).getCopEmsNo());
    }

	/* (non-Javadoc)
	 * @see com.bestway.client.custom.common.DgBaseSpecialCustomsDeclaration#newCustomsDeclaration()
	 */
	protected BaseCustomsDeclaration newCustomsDeclaration() {
		return new CustomsDeclaration();
	}

	/* (non-Javadoc)
	 * @see com.bestway.client.custom.common.DgBaseSpecialCustomsDeclaration#showCommInfoDialog(int)
	 */
	protected void showCommInfoDialog(int dataState) {
      DgSpecialCustomsDeclarationCommInfo dg = new DgSpecialCustomsDeclarationCommInfo();
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
      return manualDeclareAction.findEmsHeadH2kInExecuting(new Request(
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
} // @jve:decl-index=0:visual-constraint="10,10"

