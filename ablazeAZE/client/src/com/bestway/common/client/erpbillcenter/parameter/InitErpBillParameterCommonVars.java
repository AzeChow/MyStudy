/*
 * Created on 2005-7-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter.parameter;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.parameterset.entity.CasBillOption;
import com.bestway.bcus.cas.parameterset.entity.LotControl;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class InitErpBillParameterCommonVars implements Runnable {
    private static CasAction casAction = null;
    private static InitErpBillParameterCommonVars initCasCommonVars = null;
    
    public static InitErpBillParameterCommonVars getInstance(){
        if(initCasCommonVars == null){
            initCasCommonVars = new InitErpBillParameterCommonVars();            
        }
        return initCasCommonVars;
    }
    
    public void run() {
        init();
    }

    
    static {
    	casAction = (CasAction) CommonVars.getApplicationContext().getBean(
        "casAction");
    }
    
    /**
     * 初始化一条数据
     * 
     */
    private void init() {
        //
        // 海关帐基本单据项目设定
        //
        CasBillOption casBillOption = casAction.findCasBillOption(new Request(
                CommonVars.getCurrUser()));
        if (casBillOption == null) {
            casBillOption = new CasBillOption();
            casBillOption.setCompany(CommonVars.getCurrUser().getCompany());
            casBillOption.setIsBillRepeat(false);
            casBillOption.setIsDoubleClickBillState(false);
            casBillOption.setIsInNeedCustomer(false);
            casBillOption.setIsOutNeedCustomer(false);
            casBillOption.setIsShowStocks(true);
            casBillOption.setShowBillCorrRecord(0.1d);
            casBillOption.setIsInitBillNo(true);
            casBillOption = casAction.saveCasBillOption(new Request(CommonVars.getCurrUser()),
                    casBillOption);
        }
        ErpBillParameterCommonVars.setCasBillOption(casBillOption);

        //
        // 制单号控制对象
        //
        LotControl lotControl = casAction.findLotControl(new Request(CommonVars
                .getCurrUser()));
        if (lotControl == null) {
            lotControl = new LotControl();
            lotControl.setCompany(CommonVars.getCurrUser().getCompany());
            lotControl.setIsDirectExportNeedLot(false);
            lotControl.setIsTransferFactoryExportNeedLot(false);
            lotControl.setIsWorkshopBackNeedLot(false);
            lotControl.setIsWorkshopImportNeedLot(false);
            lotControl = casAction.saveLotControl(new Request(CommonVars.getCurrUser()),
                    lotControl);
        }
        ErpBillParameterCommonVars.setLotControl(lotControl);

    }
}
