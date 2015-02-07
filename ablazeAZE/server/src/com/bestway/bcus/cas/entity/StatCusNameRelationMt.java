/*
 * Created on 2005-6-1
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 工厂物料
 */
public class StatCusNameRelationMt extends BaseScmEntity {
    private static final long serialVesionUID = CommonUtils
            .getSerialVersionUID();
    /**
	 *  商品大类
	 */
    private StatCusNameRelation statCusNameRelation;  
    /**
	 * 工厂物料
	 */
    private Materiel materiel;
    /**
     * 物料与大类的折算系数
     */
    private Double    unitConvert ; 
    
    /**
     * 临时使用编号
     */
    private String    ptNo;    
    /**
     * 工厂名称
     */
    private String    factoryName;
    /**
     * 工厂规格
     */
    private String    factorySpec;
    

	/**
	 * 取得工厂物料内容
	 * @return materiel 工厂物料.
	 */
	public Materiel getMateriel() {
		return materiel;
	}
	/**
	 * 设置工厂物料内容
	 * @param materiel  工厂物料.
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}
	 /**
     * 取得商品大类内容
     * @return statCusNameRelation  商品大类.
     */
    public StatCusNameRelation getStatCusNameRelation() {
        return statCusNameRelation;
    }
    /**
     * 设置商品大类内容
     * @param statCusNameRelation  商品大类.
     */
    public void setStatCusNameRelation(StatCusNameRelation statCusNameRelation) {
        this.statCusNameRelation = statCusNameRelation;
    }
    /**
     * 取得物料与商品大类折算系数
     * @return unitConvert 物料与商品大类折算系数.
     */
    public Double getUnitConvert() {
        return unitConvert;
    }
    /**
     * 设置物料与商品大类折算系数
     * @param unitConvert  物料与商品大类折算系数.
     */
    public void setUnitConvert(Double unitConvert) {
        this.unitConvert = unitConvert;
    }
	public String getPtNo() {
		return ptNo;
	}
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getFactorySpec() {
		return factorySpec;
	}
	public void setFactorySpec(String factorySpec) {
		this.factorySpec = factorySpec;
	}
}
