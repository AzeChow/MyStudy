/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 存放中期核查－－自用中期核查计算表－－核查对比表资料
 */
public class CheckOwnerAccountComport extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * 企业实际库存
     */
    private Double materielNum = null;
    
    /**
     * 参数
     */
	private CheckParameter head = null;
	/**
	 * 料号
	 */
	private String ptNo = null;
	/**
	 * 名称
	 */
	private String name = null;
	/**
	 * 规格
	 */
	private String spec = null;
	/**
	 * 单位
	 */
	private Unit   unit = null;
	/**
	 * 原料库存数量
	 */
	private Double materielStockNum = null;
	/**
	 * 原料在途数量
	 */
	private Double materielOnwayNum = null;
	/**
	 * 成品折料数量
	 */
	private Double exgConvertImgNum = null;
	/**
	 * 转进未报数量	
	 */
	private Double tranNoCustomsNum = null; 
	/**
	 * 其他库存
	 */
	private Double otherStorkNum = null; 
	/**
	 * 差异值
	 */
	private Double cyNum = null;
	

	public Double getCyNum() {
		return cyNum;
	}

	public void setCyNum(Double cyNum) {
		this.cyNum = cyNum;
	}

	public Double getExgConvertImgNum() {
		return exgConvertImgNum;
	}

	public void setExgConvertImgNum(Double exgConvertImgNum) {
		this.exgConvertImgNum = exgConvertImgNum;
	}

	public CheckParameter getHead() {
		return head;
	}

	public void setHead(CheckParameter head) {
		this.head = head;
	}

	public Double getMaterielNum() {
		return materielNum;
	}

	public void setMaterielNum(Double materielNum) {
		this.materielNum = materielNum;
	}

	public Double getMaterielOnwayNum() {
		return materielOnwayNum;
	}

	public void setMaterielOnwayNum(Double materielOnwayNum) {
		this.materielOnwayNum = materielOnwayNum;
	}

	public Double getMaterielStockNum() {
		return materielStockNum;
	}

	public void setMaterielStockNum(Double materielStockNum) {
		this.materielStockNum = materielStockNum;
	}

	public Double getOtherStorkNum() {
		return otherStorkNum;
	}

	public void setOtherStorkNum(Double otherStorkNum) {
		this.otherStorkNum = otherStorkNum;
	}

	public Double getTranNoCustomsNum() {
		return tranNoCustomsNum;
	}

	public void setTranNoCustomsNum(Double tranNoCustomsNum) {
		this.tranNoCustomsNum = tranNoCustomsNum;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	
	
}
