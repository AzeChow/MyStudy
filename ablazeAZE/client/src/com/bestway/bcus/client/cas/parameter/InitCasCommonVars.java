/*
 * Created on 2005-7-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.parameter;

import java.util.Calendar;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingControl;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingOption;
import com.bestway.bcus.cas.parameterset.entity.CasBillOption;
import com.bestway.bcus.cas.parameterset.entity.LotControl;
import com.bestway.bcus.cas.parameterset.entity.OtherOption;
import com.bestway.bcus.cas.parameterset.entity.WriteAccountYear;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class InitCasCommonVars implements Runnable {
    private static CasAction casAction = null;
    private static InitCasCommonVars initCasCommonVars = null;
    
    public static InitCasCommonVars getInstance(){
        if(initCasCommonVars == null){
            initCasCommonVars = new InitCasCommonVars();            
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
        // 记帐年度
        //
        WriteAccountYear writeAccountYear = casAction
                .findWriteAccountYear(new Request(CommonVars.getCurrUser()));
        if (writeAccountYear == null) {
            writeAccountYear = new WriteAccountYear();
            writeAccountYear.setCompany(CommonVars.getCurrUser().getCompany());
            writeAccountYear.setIsCleftCustomerStat(false);
            writeAccountYear.setYear(Calendar.getInstance().get(Calendar.YEAR));
            writeAccountYear = casAction.saveWriteAccountYear(
                    new Request(CommonVars.getCurrUser()), writeAccountYear);
        }
        CasCommonVars.setWriteAccountYear(writeAccountYear);

        //
        // 单据对应
        //
        BillCorrespondingControl billCorrespondingControl = casAction
                .findBillCorrespondingControl(new Request(CommonVars
                        .getCurrUser()));
        if (billCorrespondingControl == null) {
            billCorrespondingControl = new BillCorrespondingControl();
            billCorrespondingControl.setCompany(CommonVars.getCurrUser()
                    .getCompany());
            billCorrespondingControl.setIsHandContrl(false);
            billCorrespondingControl.setIsSpecialControl(false);
            billCorrespondingControl.setIsSystemControl(new Boolean(true));
            billCorrespondingControl.setIsTransferBack(false);
            billCorrespondingControl = casAction.saveBillCorrespondingControl(new Request(CommonVars
                    .getCurrUser()), billCorrespondingControl);
        }
        CasCommonVars.setBillCorrespondingControl(billCorrespondingControl);
        //
        // other 对象
        //
        OtherOption otherOption = casAction.findOtherOption(new Request(
                CommonVars.getCurrUser()));
        if (otherOption == null) {
            otherOption = new OtherOption();
            otherOption.setCompany(CommonVars.getCurrUser().getCompany());
            otherOption.setIsImportExportAccountBalance(false);
            otherOption.setIsNameSpecRepeat(false);
            otherOption.setInOutMaximumFractionDigits(6);
            otherOption = casAction.saveOtherOption(new Request(CommonVars.getCurrUser()),
                    otherOption);
        }
        CasCommonVars.setOtherOption(otherOption);
        
        
        //
        // 单据对应选项
        //
        BillCorrespondingOption billCorrespondingOption = casAction.findBillCorrespondingOption(new Request(
                CommonVars.getCurrUser()));
        if (billCorrespondingOption == null) {
        	billCorrespondingOption = new BillCorrespondingOption();
        	billCorrespondingOption.setCompany(CommonVars.getCurrUser().getCompany());
        	billCorrespondingOption.setIsCalculatePrice(false);
        	billCorrespondingOption.setIsBillCorrespondingAffirm(true);
        	billCorrespondingOption.setIsOverYear(false);
            billCorrespondingOption.setIsNoNameMatching(false);
            billCorrespondingOption.setIsNamePartMatching(false);
            billCorrespondingOption.setIsNoSpecMatching(false);
            billCorrespondingOption.setIsNoUnitMatching(false);
        	billCorrespondingOption = casAction.saveBillCorrespondingOption(new Request(CommonVars.getCurrUser()),
            		billCorrespondingOption);
        }
        //CasCommonVars.setBillCorrespondingOption(billCorrespondingOption);

    }
}
