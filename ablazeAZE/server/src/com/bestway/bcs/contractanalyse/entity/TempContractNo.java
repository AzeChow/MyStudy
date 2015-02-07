/*
 * Created on 2005-6-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractanalyse.entity;

import java.io.Serializable;

import com.bestway.bcs.contract.entity.Contract;

/**
 * 临时实体类，存放临时的合同号，当需要根据合同查找时都用到
 * 
 * @author ls
 */
public class TempContractNo implements Serializable {

	/**
	 * 合同号
	 */
    private String contractNo            = null;

    
    /**
     * 获取合同号
     * 
     * @return  contractNo 合同号
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * 设置合同号
     * 
     * @param contractNo 合同号
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }
    
}
