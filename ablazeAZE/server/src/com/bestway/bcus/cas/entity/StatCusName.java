/*
 * Created on 2005-6-1
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 工厂大类
 */
public class StatCusName extends BaseScmEntity{
    private static final long serialVersionUID=CommonUtils.getSerialVersionUID();
    /**
     * 名称
     */
    private String cusName;        
    /**
     * 编码
     */
    private String cusCode;        
    /**
     * 规格
     */
    private String cusSpec;        
    /**
     * 单位
     */
    private Unit   cusUnit;        
    /**
     * 商品编码
     */
    private Complex complex; 


    /**
     * 取得商品编码
     * @return  complex 商品编码.
     */
    public Complex getComplex() {
        return complex;
    }
    /**
     * 设置商品编码
     * @param complex  商品编码
     */
    public void setComplex(Complex complex) {
        this.complex = complex;
    }
    /**
     * 取得编码或商品序号
     * @return cusCode 编码或序号
     */
    public String getCusCode() {
        return cusCode;
    }
    /**
     * 取得商品名称
     * @return cusName 商品名称
     */
    public String getCusName() {
        return cusName;
    }
    /**
     * 取得商品规格
     * @return cusSpec 商品规格
     */
    public String getCusSpec() {
        return cusSpec;
    }
    /**
     * 取得单位
     * @return cusUnit 单位
     */
    public Unit getCusUnit() {
        return cusUnit;
    }
    /**
     * 设置编码或序号
     * @param cusCode 编码或序号
     */
    public void setCusCode(String cusCode) {
        this.cusCode = cusCode;
    }
    /**
     * 设置商品名称
     * @param cusName 商品名称
     */
    public void setCusName(String cusName) {
        this.cusName = cusName;
    }
    /**
     * 设置商品规格
     * @param cusSpec 商品规格
     */
    public void setCusSpec(String cusSpec) {
        this.cusSpec = cusSpec;
    }
    /**
     * 设置单位
     * @param cusUnit 单位
     */
    public void setCusUnit(Unit cusUnit) {
        this.cusUnit = cusUnit;
    }
}
