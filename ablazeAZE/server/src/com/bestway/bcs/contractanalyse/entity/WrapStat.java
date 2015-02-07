/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractanalyse.entity;

import java.io.Serializable;

/**
 * 存放报关分析－－进口包装统计
 */
public class WrapStat implements Serializable {

	/**
	 * 包装名称
	 */
	String wrapName=null;
	
	/**
	 * 包材重量
	 */
	Double wrapWeight=null;
	
	/**
	 * 包装单位
	 */
	String wrapUnit=null;
	
	/**
     * 手册号
     */
    private String  emsNo    = null; 
    
    /**
     * 报关单号
     */
    private String  customsDeclarationCode = null; 
    /**
     * 备注
     */
    private String  memos = null; 
    /**
     * 获取备注
     */
	public String getMemos() {
		return memos;
	}

	public void setMemos(String memos) {
		this.memos = memos;
	}

	/**
	 * 获取包装名称
	 * 
	 * @return wrapName 包装名称
	 */
	public String getWrapName() {
		return wrapName;
	}
	
	/**
	 * 获取包装单位
	 * 
	 * @return wrapUnit 包装单位
	 */
	public String getWrapUnit() {
		return wrapUnit;
	}
	
	/**
	 * 获取包材重量
	 * 
	 * @return wrapWeight 包材重量
	 */
	public Double getWrapWeight() {
		return wrapWeight;
	}
	
	/**
	 * 设置包装名称
	 * 
	 * @param wrapName 包装名称
	 */
	public void setWrapName(String wrapName) {
		this.wrapName = wrapName;
	}
	
	/**
	 * 设置包装单位
	 * 
	 * @param wrapUnit 包装单位
	 */
	public void setWrapUnit(String wrapUnit) {
		this.wrapUnit = wrapUnit;
	}
	
	/**
	 * 设置包材重量
	 * 
	 * @param wrapWeight 包材重量
	 */
	public void setWrapWeight(Double wrapWeight) {
		this.wrapWeight = wrapWeight;
	}

	/**
	 * @return the customsDeclarationCode
	 */
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	/**
	 * @param customsDeclarationCode the customsDeclarationCode to set
	 */
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

	/**
	 * @return the emsNo
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * @param emsNo the emsNo to set
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
}
