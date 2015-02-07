/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractexe;

import java.util.List;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.custom.common.DgBaseSpecialCustomsDeclaration;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.contractexe.action.DzscContractExeAction;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.dzscmanage.action.DzscAction;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscSpecialCustomsDeclaration extends
        DgBaseSpecialCustomsDeclaration {
	
	private DzscAction dzscAction = null;
	
    public DgDzscSpecialCustomsDeclaration() {
        super();
        lbEmsNo.setText("备案号");
        baseEncAction = (DzscContractExeAction) CommonVars
                .getApplicationContext().getBean("dzscContractExeAction");
        dzscAction = (DzscAction) CommonVars
        .getApplicationContext().getBean("dzscAction");
        projectType = ProjectType.DZSC;
    }

    /**
     * 当新增或删除时，显示最初数据
     */
    protected void showPrimalData() {
        super.showPrimalData();
        if (dataState == DataState.ADD) {
//            customsDeclaration.setContract(((DzscEmsPorHead) emsHead)
//                    .getIeContractNo());
//            this.tfContract.setText(customsDeclaration.getContract());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bestway.client.custom.common.DgBaseSpecialCustomsDeclaration#newCustomsDeclaration()
     */
    protected BaseCustomsDeclaration newCustomsDeclaration() {
        return new DzscCustomsDeclaration();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bestway.client.custom.common.DgBaseSpecialCustomsDeclaration#showCommInfoDialog(int)
     */
    protected void showCommInfoDialog(int dataState) {
        DgDzscSpecialCustomsDeclarationCommInfo dg = new DgDzscSpecialCustomsDeclarationCommInfo();
        dg.setDataState(dataState);
        dg.setEffective(customsDeclaration.getEffective() == null ? false
                : customsDeclaration.getEffective().booleanValue());
        dg.setCustomsDeclaration(customsDeclaration);
        dg.setTableModel(commInfoModel);
        Integer type = Integer
		.valueOf(((ItemProperty) cbbImpExpType
				.getSelectedItem()).getCode());
        other1 = systemAction.findCustomsSet(
				new Request(CommonVars.getCurrUser(), true),
				customsDeclaration == null ? null : type);
        dg.setOther1(other1);
        dg.setBaseEncAction(this.baseEncAction);
        dg.setVisible(true);
        getPiceAndWeight();// 是否自动带出净毛重
    }

	@Override
	protected List getEmsHeadList() {
		return dzscAction.findExeEmsPorHead(new Request(
      		 CommonVars.getCurrUser()));      
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

