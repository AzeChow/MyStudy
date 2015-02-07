/*
 * Created on 2004-9-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dataimport.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.common.CommonUtils;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempImpExpRequestBillKey implements Serializable {
	/**
     * 料号.
     */
    private String          ptNo;
    /**
     * 单据号码
     */
    private String            billNo;
    
    
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getPtNo() {
		return ptNo;
	}
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	} 
    
    
    
}