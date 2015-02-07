/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractexe;

import java.util.List;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.client.custom.common.FmBaseImExportCustomsDeclarationDelete;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmImExportCustomsDeclarationDelete extends
		FmBaseImExportCustomsDeclarationDelete {

	private ContractAction contractAction = null;

	public FmImExportCustomsDeclarationDelete() {
		super();
		initialize();
		baseEncAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		projectType = ProjectType.BCS;
		contractAction.brownDeleteCustoms(new Request(CommonVars
				.getCurrUser()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.client.custom.common.FmBaseImportCustomsDeclaration#initUIComponents()
	 */
	protected void initUIComponents() {
	       // 电子帐册
        cbbEmshead.removeAllItems();
        List contracts= contractAction.findContractByProcessExe(new Request(
       		 CommonVars.getCurrUser(),true));       
        if (contracts != null && contracts.size() > 0) {
            for (int i = 0; i < contracts.size(); i++) {
                this.cbbEmshead.addItem((Contract) contracts.get(i));
            }
            this.cbbEmshead.setRenderer(CustomBaseRender.getInstance()
                    .getRender("impContractNo", "emsNo", 80, 150).setForegroundColor(java.awt.Color.red));
        }
        if(this.cbbEmshead.getItemCount()>0){
        	this.cbbEmshead.setSelectedIndex(0);
        }      
        lbEmsHead.setText("合同号+手册号:");
	}
} // @jve:decl-index=0:visual-constraint="10,10"
