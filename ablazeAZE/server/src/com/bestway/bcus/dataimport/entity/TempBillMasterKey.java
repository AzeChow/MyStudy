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
public class TempBillMasterKey implements Serializable {
	/**
     * 单据类型.
     */
    private String          billTypeCode;
    /**
     * 单据号码
     */
    private String            billNo; 
    /**
     * 生效日期
     */
    private Date              validDate;
    
    
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBillTypeCode() {
		return billTypeCode;
	}
	public void setBillTypeCode(String billTypeCode) {
		this.billTypeCode = billTypeCode;
	}
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}    
    
    
}