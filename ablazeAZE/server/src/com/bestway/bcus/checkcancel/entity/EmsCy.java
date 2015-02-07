/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 *  存放滚动核销－－帐帐分析－－差异分析
 */
public class EmsCy extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
   
    /**
     * 帐帐分析表头
     */
    private EmsAnalyHead head = null;
    
    /**
     * 帐册编号
     */
    private String emsNo = null; 
    
    /**
     * 备案序号
     */
    private Integer seqNum = null;
    
    /**
     * 料件名称
     */
    private String name = null;
    
    /**
     * 料件规格
     */
    private String spec = null;
    
    /**
     * 计量单位
     */
    private Unit unit = null; 
    /**
     * 单价（取最新报关单单价）
     */
    private Double price = null;
    /**
     * 帐面结余
     */
    private Double emsBalance = null; 
    
    /**
     * 实物库存
     */
    private Double factNum = null;
    
    /**
     * 差异数
     */
    private Double cyNum = null; 
    /**
     * USD 关税
     */
    private Double usd = null;
    
    /**
     * USD 增值税
     */
    private Double usdAdd = null;
	
	
    /**
	 * 获取差异数
	 * 
	 * @return cyNum 差异数
	 */
	public Double getCyNum() {
		return cyNum;
	}
		
	/**
	 * 设置差异数
	 * 
	 * @param cyNum 差异数
	 */
	public void setCyNum(Double cyNum) {
		this.cyNum = cyNum;
	}
		
    /**
	 * 获取帐面结余
	 * 
	 * @return emsBalance 帐面结余
	 */
	public Double getEmsBalance() {
		return emsBalance;
	}
		
	/**
	 * 设置帐面结余
	 * 
	 * @param emsBalance 帐面结余
	 */
	public void setEmsBalance(Double emsBalance) {
		this.emsBalance = emsBalance;
	}
		
    /**
	 * 获取帐册编号
	 * 
	 * @return emsNo 帐册编号
	 */
	public String getEmsNo() {
		return emsNo;
	}
		
	/**
	 * 设置帐册编号
	 * 
	 * @param emsNo 帐册编号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
		
    /**
	 * 获取实物库存
	 * 
	 * @return factNum 实物库存
	 */
	public Double getFactNum() {
		return factNum;
	}
		
	/**
	 * 设置实物库存
	 * 
	 * @param factNum 实物库存
	 */
	public void setFactNum(Double factNum) {
		this.factNum = factNum;
	}
		
    /**
	 * 获取帐帐分析表头
	 * 
	 * @return head 帐帐分析表头
	 */
	public EmsAnalyHead getHead() {
		return head;
	}
		
	/**
	 * 设置帐帐分析表头
	 * 
	 * @param head 帐帐分析表头
	 */
	public void setHead(EmsAnalyHead head) {
		this.head = head;
	}
		
    /**
	 * 获取料件名称
	 * 
	 * @return name 料件名称
	 */
	public String getName() {
		return name;
	}
		
	/**
	 * 设置料件名称
	 * 
	 * @param name 料件名称
	 */
	public void setName(String name) {
		this.name = name;
	}
		
    /**
	 * 获取备案序号
	 * 
	 * @return seqNum 备案序号
	 */
	public Integer getSeqNum() {
		return seqNum;
	}
		
	/**
	 * 设置备案序号
	 * 
	 * @param seqNum 备案序号
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
		
    /**
	 * 获取料件规格
	 * 
	 * @return spec 料件规格
	 */
	public String getSpec() {
		return spec;
	}
		
	/**
	 * 设置料件规格
	 * 
	 * @param spec 料件规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
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
	 * 设置计量单位
	 * 
	 * @param unit 计量单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the usd
	 */
	public Double getUsd() {
		return usd;
	}

	/**
	 * @param usd the usd to set
	 */
	public void setUsd(Double usd) {
		this.usd = usd;
	}

	public Double getUsdAdd() {
		return usdAdd;
	}

	public void setUsdAdd(Double usdAdd) {
		this.usdAdd = usdAdd;
	}
	
	
}
