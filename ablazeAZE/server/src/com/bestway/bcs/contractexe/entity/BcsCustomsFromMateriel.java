/*
 * Created on 2004-8-5
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractexe.entity;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseCustomsFromMateriel;

/**
 * 存放合同备案物料与对应的第一层物料的资料
 */
public class BcsCustomsFromMateriel extends BaseCustomsFromMateriel {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
     * 合同成品
     */
    private ContractImg bcsimgbill = null;
    
	/**
	 * 合同料件
	 */
	private ContractExg bcsexgbill = null;

	
	
	/**
	 * 获取合同成品
	 * 
	 * @return  bcsexgbill 合同成品
	 */
	public ContractExg getBcsexgbill() {
		return bcsexgbill;
	}
	
	/**
	 * 设置合同成品
	 * 
	 * @param bcsexgbill 合同成品
	 */
	public void setBcsexgbill(ContractExg bcsexgbill) {
		this.bcsexgbill = bcsexgbill;
	}
	
	/**
	 * 获取合同料件
	 * 
	 * @return bcsimgbill 合同料件
	 */
	public ContractImg getBcsimgbill() {
		return bcsimgbill;
	}
	
	/**
	 * 设置合同料件
	 * 
	 * @param bcsimgbill 合同料件
	 */
	public void setBcsimgbill(ContractImg bcsimgbill) {
		this.bcsimgbill = bcsimgbill;
	}
}