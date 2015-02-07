/*
 * Created on 2005-7-19
 *
 * 
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
public class CasCommonVars {
    private static BillCorrespondingControl billCorrespondingControl = null;
    private static OtherOption              otherOption              = null;
    private static WriteAccountYear         writeAccountYear         = null;
    

    /**
     * @param billCorrespondingControl The billCorrespondingControl to set.
     */
    public static void setBillCorrespondingControl(
            BillCorrespondingControl billCorrespondingControl) {
        CasCommonVars.billCorrespondingControl = billCorrespondingControl;
    }
    
    /**
     * @param otherOption The otherOption to set.
     */
    public static void setOtherOption(OtherOption otherOption) {
        CasCommonVars.otherOption = otherOption;
    }
    /**
     * @param writeAccountYear The writeAccountYear to set.
     */
    public static void setWriteAccountYear(WriteAccountYear writeAccountYear) {
        CasCommonVars.writeAccountYear = writeAccountYear;
    }
    

    /**
     * 获得单据对应
     * 
     * @return
     */
    public static BillCorrespondingControl getBillCorrespondingControl() {
        return billCorrespondingControl;
    }

    

    /**
     * 获得其它选项对象
     * 
     * @return
     */
    public static OtherOption getOtherOption() {
        return otherOption;
    }

    /**
     * 获得记帐年度对象
     * 
     * @return
     */
    public static WriteAccountYear getWriteAccountYear() {
        return writeAccountYear;
    }
    
    
    
    /**
     * 获得记帐年度
     * @return
     */
    public static String getYear(){
        WriteAccountYear writeAccountYear = getWriteAccountYear();
        if(writeAccountYear != null){
            return writeAccountYear.getYear().toString();
        }else{
            return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        }        
    }
    
    /**
     * 获得记帐年度
     * @return
     */
    public static int getYearInt(){
        WriteAccountYear writeAccountYear = getWriteAccountYear();
        if(writeAccountYear != null){
            return writeAccountYear.getYear();
        }else{
            return Calendar.getInstance().get(Calendar.YEAR);
        }        
    }
    
    /**
     * 单据对应选项
     * @return
     */
	public static BillCorrespondingOption getBillCorrespondingOption() {
		CasAction casAction = (CasAction) CommonVars.getApplicationContext().getBean(
        "casAction");
		BillCorrespondingOption billCorrespondingOption = casAction.findBillCorrespondingOption(new Request(
                CommonVars.getCurrUser()));		
		return billCorrespondingOption;
	}
	

}
