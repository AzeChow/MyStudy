/*
 * Created on 2005-6-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractanalyse.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 临时实体类，存放合同料件的临时资料
 * 
 * @author ls
 */
public class TempDzscContractImg implements Serializable {
	
	/**
	 * 商品编码
	 */
    private String complexCode; 
	
	/**
	 * 商品名称
	 */
    private String name;
	
	/**
	 * 规格型号
	 */
    private String spec;
	
	/**
	 * 计量单位
	 */
    private Unit   unit;
	
	/**
	 * 单位重量
	 */
    private Double unitWeight;
    
    /**
     * 获取商品编码
     * 
     * @return complexCode 商品编码
     */
    public String getComplexCode() {
        return complexCode;
   }
    
   /**
     * 获取商品名称
     * 
     * @return name 商品名称
     */
    public String getName() {
        return name;
   }
    
   /**
     * 获取规格型号
     * 
     * @return spec 规格型号
     */
    public String getSpec() {
        return spec;
   }
    
   /**
     * 获取计量单位
     * 
     * @return unit 计量单位
     */
    public Unit getUnit() {
        return unit;
   }
    
   /**
     * 获取单位重量
     * 
     * @return unitWeight 单位重量
     */
    public Double getUnitWeight() {
        return unitWeight;
   }
    
   /**
     * 设置商品编码
     * 
     * @param complexCode 商品编码
     */
    public void setComplexCode(String complexCode) {
        this.complexCode = complexCode;
    }
    
   /**
     * 设置商品名称
     * 
     * @param name 商品名称
     */
    public void setName(String name) {
        this.name = name;
    }
   
    /**
     * 设置规格型号
     * 
     * @param spec 规格型号
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }
   
    /**
     * 设置计量单位
     * 
     * @param unit 计量单位
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
  
    /**
     * 设置单位重量
     * 
     * @param unitWeight 单位重量
     */
    public void setUnitWeight(Double unitWeight) {
        this.unitWeight = unitWeight;
    }
}
