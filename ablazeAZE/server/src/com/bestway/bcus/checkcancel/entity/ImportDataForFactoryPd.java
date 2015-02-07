/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 中期核查－－工厂盘点－－料件
 */
public class ImportDataForFactoryPd extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
     * 料号
     */
    private String ptNo = null;
    /**
     * 版本
     */
    private String version = null; 
    /**
     * 原料库存数量
     */
    private Double materStockNum = null; 
    
   
    
    
	/**
	 * 获取原料库存数量
	 * 
	 * @return materStockNum 原料库存数量
	 */
	public Double getMaterStockNum() {
		return materStockNum;
	}

	/**
	 * 设置原料库存数量
	 * 
	 * @param materStockNum  原料库存数量
	 */
	public void setMaterStockNum(Double materStockNum) {
		this.materStockNum = materStockNum;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
