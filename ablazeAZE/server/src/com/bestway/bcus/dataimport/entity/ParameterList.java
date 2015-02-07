/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dataimport.entity;


import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 参数列表
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ParameterList extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 参数名称
	 */
    private String pname = null;
    /**
     * 参数值
     */
	private String pvalue = null;
	/**
	 * 是否当前日期
	 */
	private Boolean isNowDate = null; 
	
	/**
	 * @return Returns the pname.
	 */
	public String getPname() {
		return pname;
	}
	/**
	 * @param pname The pname to set.
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}
	/**
	 * @return Returns the pvalue.
	 */
	public String getPvalue() {
		return pvalue;
	}
	/**
	 * @param pvalue The pvalue to set.
	 */
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
	/**
	 * @return Returns the isNowDate.
	 */
	public Boolean getIsNowDate() {
		return isNowDate;
	}
	/**
	 * @param isNowDate The isNowDate to set.
	 */
	public void setIsNowDate(Boolean isNowDate) {
		this.isNowDate = isNowDate;
	}
}
