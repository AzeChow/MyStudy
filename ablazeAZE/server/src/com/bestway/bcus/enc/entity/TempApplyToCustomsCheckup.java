package com.bestway.bcus.enc.entity;

import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;

/**
 * @author fhz
 * 
 * 大小清单报表（申请单转清单转报关单）
 */
public class TempApplyToCustomsCheckup extends BaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 清单号码
	 */
	String listNo;
	
	/**
	 * 头中的错误
	 */
	String headErr;
	
	/**
	 * 体的错误
	 */
	String bodyErr;

	/**
	 * @return the bodyErr
	 */
	public String getBodyErr() {
		return bodyErr;
	}

	/**
	 * @param bodyErr the bodyErr to set
	 */
	public void setBodyErr(String bodyErr) {
		this.bodyErr = bodyErr;
	}

	/**
	 * @return the headErr
	 */
	public String getHeadErr() {
		return headErr;
	}

	/**
	 * @param headErr the headErr to set
	 */
	public void setHeadErr(String headErr) {
		this.headErr = headErr;
	}

	/**
	 * @return the listNo
	 */
	public String getListNo() {
		return listNo;
	}

	/**
	 * @param listNo the listNo to set
	 */
	public void setListNo(String listNo) {
		this.listNo = listNo;
	}

}
