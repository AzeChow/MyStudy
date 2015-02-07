/*
 * Created on 2005-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempRelationCommInfo implements Serializable {
	/**
     * 序号
     */
	private Integer seqNum;  
	
	/**
     * 名称
     */
	private String cusName; 
	/**
     * 编码
     */
	private Complex complex;  
	/**
     * 规格
     */
	private String cusSpec; 
	/**
     * 单位
     */
	private Unit cusUnit; 

	
    /**
     * 取得商品编码
     * @return complex 商品编码.
     */
    public Complex getComplex() {
        return complex;
    }
    /**
     * 设置商品编码
     * @param complex  商品编码.
     */
    public void setComplex(Complex complex) {
        this.complex = complex;
    }
	/**
	 * 取得商品名称
	 * @return cusName 商品名称.
	 */
	public String getCusName() {
		return cusName;
	}

	/**
	 * 取得商品规格
	 * @return cusSpec 规格.
	 */
	public String getCusSpec() {
		return cusSpec;
	}

	/**
	 * 取得商品单位
	 * @return cusUnit 商品单位.
	 */
	public Unit getCusUnit() {
		return cusUnit;
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
	 * @param cusSpec  商品规格
	 */
	public void setCusSpec(String cusSpec) {
		this.cusSpec = cusSpec;
	}

	/**
	 * 设置商品单位
	 * @param cusUnit 商品单位
	 */
	public void setCusUnit(Unit cusUnit) {
		this.cusUnit = cusUnit;
	}

	
    /**
     * 取得序号
     * @return seqNum 序号.
     */
    public Integer getSeqNum() {
        return seqNum;
    }
    /**
     * 设置序号
     * @param seqNum  序号.
     */
    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }
	/**
	 * 比较传入的参数objiect是否和TempRelationCommInfo类的实例是否相等 相等为true ，否则为false
	 *@param  obj
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TempRelationCommInfo)) {
			return false;
		}
		TempRelationCommInfo commInfo = (TempRelationCommInfo) obj;
		if (commInfo.seqNum == null && this.seqNum == null) {
			if (commInfo.getComplex().getCode().equals(this.getComplex().getCode())) {
				return true;
			} else {
				return false;
			}
		} else if (commInfo.seqNum == null || this.seqNum == null) {
			return false;
		} else {
			if (commInfo.seqNum.equals(this.seqNum)
					&& commInfo.getComplex().getCode().equals(this.getComplex().getCode())) {
				return true;
			} else {
				return false;
			}
		}
	}
}
