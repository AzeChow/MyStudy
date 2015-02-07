package com.bestway.dzsc.contractanalyse.entity;

import java.io.Serializable;

/**
 * 临时实体类，存放临时的成品资料
 * 
 * @author yp
 */
public class TempDzscFinishProduct implements Serializable {
    
	/**
     * 成品名称
     */
    private String productName = null; 
   
    /**
     * 单项用量
     */
    private Double unitDosage  = null;
    
    
    /**
     * 获取成品名称
     * 
     * @return 成品名称
     */
    public String getProductName() {
        return productName;
    }
    
    /**
     * 设置成品名称
     * 
     * @param productName 成品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    /**
     * 获取单项用量
     * 
     * @return unitDosage 单项用量
     */
    public Double getUnitDosage() {
        return unitDosage;
    }
    
    /**
     * 设置
     * 
     * @param unitDosage 单项用量
     */
    public void setUnitDosage(Double unitDosage) {
        this.unitDosage = unitDosage;
    }
    
    
}
