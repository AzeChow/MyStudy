/*
 * Created on 2004-10-21
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * 结转成品对比
 */
public class CarryForwardCmpExgInfo implements Serializable, Comparable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
	* 成品名称
	*/
	private String ptName;
	/**
	* 成品规格
	*/
	private String ptSpec;
	/**
	* 成品单位名称
	*/
	private String ptUnitName;
	/**
	* 客户名称
	*/
	private String customerName;
	/**
	 * 结转成品退换数
	 */
	private Double hsAmount2009 ;
	/**
	 * 结转成品出口数
	 */
	private Double hsAmount2102 ;
	/**
	 *送货数
	 */
	private Double hsAmount;
	/**
	 *结转数
	 */
	private Double customNum;
	/**
	 * 已经结转未送货
	 */
	private Double carryForwardNum;
	/**
	* 未结转已送货数
	*/
	private Double unCarryForwardNum;
	/**
	* 构造函数  初始化 结转成品退换数,结转成品出口数,送货数,结转数,已经结转未送货,未结转已送货数
	*/
	public CarryForwardCmpExgInfo(){
        hsAmount2009 = 0.0;
        hsAmount2102 = 0.0;
		hsAmount = 0.0;
		customNum = 0.0;
		carryForwardNum = 0.0;
		unCarryForwardNum = 0.0;
	}
	
	/**
	 * 取得键值
	 * @return 成品名称+规格+单位.
	 */
    public String getKey(){
        String ptSpec  = (this.ptSpec == null || "".equals(this.ptSpec))?"":"/"+this.ptSpec; 
        return this.ptName+ptSpec+"/"+this.getPtUnitName();
    }
    
    /**
	 * 取得送货数
	 * @return 结转出口成品数-结转成品退换数.
	 */
    public Double getHsAmount() {
        return hsAmount2102 - hsAmount2009;
    }
    
    /**
	 * 取得已结转未送货数
	 * @return 结转数-送货数（大于0）或0.0（小于0）.
	 */
	public Double getCarryForwardNum() {
        Double hsAmount = this.getHsAmount() ;
        if((customNum - hsAmount)>0){
            return customNum - hsAmount;
        }
        else {
            return 0.0;
        }
	}
	/**
	 * 取得未结转已送货数
	 * @return 送货数-结转数（大于0）或0.0（小于0）.
	 */
    public Double getUnCarryForwardNum() {
        Double hsAmount = this.getHsAmount() ;
        if((hsAmount - customNum)>0){
            return hsAmount - customNum;
        }
        else {
            return 0.0;
        }
    }
    
	
	/**
	 * 取得客户名称
	 * @return customerName 客户名称.
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * 设置客户名称
	 * @param customerName 客户名称.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * 取得结转数
	 * @return customNum 结转数.
	 */
	public Double getCustomNum() {
		return customNum;
	}
	/**
	 * 设置结转数
	 * @param customNum 结转数.
	 */
	public void setCustomNum(Double customNum) {
		this.customNum = customNum;
	}
	
	/**
	 * 设置送货数
	 * @param hsAmount 送货数.
	 */
	public void setHsAmount(Double hsAmount) {
		this.hsAmount = hsAmount;
	}
	/**
	 * 取得成品名称
	 * @return  ptName 成品名称.
	 */
	public String getPtName() {
		return ptName;
	}
	/**
	 * 设置成品名称
	 * @param ptName 成品名称
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	/**
	 * 取得成品规格
	 * @return  ptSpec 成品规格.
	 */
	public String getPtSpec() {
		return ptSpec;
	}
	/**
	 * 设置成品规格
	 * @param ptSpec 成品规格.
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}
	/**
	 * 取得单位名称
	 * @return ptUnitName 单位名称.
	 */
	public String getPtUnitName() {
		return ptUnitName;
	}
	/**
	 * 设置单位名称
	 * @param ptUnitName 单位名称.
	 */
	public void setPtUnitName(String ptUnitName) {
		this.ptUnitName = ptUnitName;
	}
	

	/** 
	 * 按照成品名称,规格,单位,客户名称重新排序  
	 */
	public int compareTo(Object arg0) {
		CarryForwardCmpExgInfo info = (CarryForwardCmpExgInfo)arg0;
		if(!this.getPtName().equals(info.getPtName())){
			return this.getPtName().compareTo(info.getPtName());
		}
		else if(!this.getPtUnitName().equals(info.getPtSpec())){
			return this.getPtSpec().compareTo(info.getPtSpec());
		}
		else if(!this.getPtUnitName().equals(info.getPtUnitName())){
			return this.getPtUnitName().compareTo(info.getPtUnitName());
		}
		else {
			return this.getCustomerName().compareTo(info.getCustomerName());
		}		
	}
	/**
	 * 取得结转成品退换数
	 * @return hsAmount2009 结转成品退换数.
	 */
    public Double getHsAmount2009() {
        return hsAmount2009;
    }
    /**
	 * 设置结转成品退换数
	 * @param hsAmount2009 结转成品退换数.
	 */
    public void setHsAmount2009(Double hsAmount2009) {
        this.hsAmount2009 = hsAmount2009;
    }
    /**
	 * 取得结转成品出口数
	 * @return hsAmount2102 结转成品出口数.
	 */
    public Double getHsAmount2102() {
        return hsAmount2102;
    }
    /**
	 * 设置结转成品出口数
	 * @param hsAmount2102 结转成品出口数.
	 */
    public void setHsAmount2102(Double hsAmount2102) {
        this.hsAmount2102 = hsAmount2102;
    }

}
