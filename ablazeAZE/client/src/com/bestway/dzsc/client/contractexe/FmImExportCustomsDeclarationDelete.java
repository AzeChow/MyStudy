/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractexe;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.custom.common.FmBaseExportCustomsDeclaration;
import com.bestway.client.custom.common.FmBaseImExportCustomsDeclarationDelete;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ProjectType;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmImExportCustomsDeclarationDelete extends FmBaseImExportCustomsDeclarationDelete {
	private DzscAction dzscAction = null;

	public FmImExportCustomsDeclarationDelete() {
		super();
		initialize();
		baseEncAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		dzscAction = (DzscAction) CommonVars
        .getApplicationContext().getBean("dzscAction");
		projectType = ProjectType.DZSC;
		dzscAction.brownDeleteCustoms(new Request(CommonVars.getCurrUser()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.client.custom.common.FmBaseImportCustomsDeclaration#initUIComponents()
	 */
	protected void initUIComponents() {
	       // 电子帐册
        cbbEmshead.removeAllItems();
        List contracts= dzscAction.findExeEmsPorHead(new Request(
       		 CommonVars.getCurrUser()));       
        if (contracts != null && contracts.size() > 0) {
            for (int i = 0; i < contracts.size(); i++) {
                this.cbbEmshead.addItem((DzscEmsPorHead) contracts.get(i));
            }
            this.cbbEmshead.setRenderer(CustomBaseRender.getInstance()
                    .getRender("ieContractNo", "emsNo", 150, 150));
        }
        if(this.cbbEmshead.getItemCount()>0){
        	this.cbbEmshead.setSelectedIndex(0);
        }      
        
        lbEmsHead.setText("合同号+手册号:");
	}
 }  //  @jve:decl-index=0:visual-constraint="10,10"
