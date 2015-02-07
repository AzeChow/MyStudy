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
 *  加工贸易原材料来源与使用情况表中的结转收货对比信息
 */
public class CarryForwardCmpImgInfo implements Serializable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
	* 料件名称
	*/
	private String ptName;
	/**
	* 料件规格
	*/
	private String ptSpec;
	/**
	* 料件单位名称
	*/
	private String ptUnitName;
	/**
	* 客户名称
	*/
	private String customerName;
	/**
	 * 结转料件进口数
	 */
    private Double hsAmount1004;
    /**
	 * 结转料件退换数
	 */
    private Double hsAmount1106;
    /**
	 *收货数
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
	* 构造函数  初始化 结转料件进口数,结转料件退换数,送货数,结转数,已经结转未送货,未结转已送货数
	*/
	public CarryForwardCmpImgInfo(){
        hsAmount1004 = 0.0;
        hsAmount1106 = 0.0;
		hsAmount = 0.0;
		customNum = 0.0;
		carryForwardNum = 0.0;
		unCarryForwardNum = 0.0;
	}
	/**
	 * 取得键值
	 * @return 料件名称+规格+单位.
	 */
    public String getKey(){
        String ptSpec  = (this.ptSpec == null || "".equals(this.ptSpec))?"":"/"+this.ptSpec; 
        return this.ptName+ptSpec+"/"+this.getPtUnitName();
    }
    
    /**
	 * 取得已经结转未送货
	 * @return 结转数-收货数（大于0）或0.0（小于0）.
	 */
    public Double getCarryForwardNum() {
        Double hsAmount = this.getHsAmount() ;
        if((customNum - hsAmount)>0){
            return customNum - hsAmount ;
        }
        else {
            return 0.0;
        }        
    }
    
    /**
	 * 取得未结转已送货数
	 * @return 收货数-结转数（大于0）或0.0（小于0）.
	 */
    public Double getUnCarryForwardNum() {
        Double hsAmount = this.getHsAmount() ;
        if((hsAmount - customNum)>0){
            return hsAmount - customNum ;
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
	 * 取得收货数
	 * @return  结转料件进口数-结转料件退换数.
	 */
    public Double getHsAmount() {
        return this.hsAmount1004 - this.hsAmount1106;
    }
    /**
	 * 设置收货数
	 * @param hsAmount 收货数.
	 */
    public void setHsAmount(Double hsAmount) {
        this.hsAmount = hsAmount;
    }
    /**
	 * 取得料件名称
	 * @return  ptName 料件名称.
	 */
    public String getPtName() {
        return ptName;
    }
    /**
	 * 设置料件名称
	 * @param ptName 料件名称
	 */
    public void setPtName(String ptName) {
        this.ptName = ptName;
    }
    /**
	 * 取得料件规格
	 * @return  ptSpec 料件规格.
	 */
    public String getPtSpec() {
        return ptSpec;
    }
    /**
	 * 设置料件规格
	 * @param ptSpec 料件规格.
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
	 * 取得结转料件进口数
	 * @return hsAmount1004 结转料件进口数.
	 */

    public Double getHsAmount1004() {
        return hsAmount1004;
    }
    /**
	 * 设置结转料件进口数
	 * @param hsAmount1004 结转料件进口数.
	 */
    public void setHsAmount1004(Double hsAmount1004) {
        this.hsAmount1004 = hsAmount1004;
    }
    /**
	 * 取得结转料件退换数
	 * @return hsAmount1106 结转料件退换数.
	 */
    public Double getHsAmount1106() {
        return hsAmount1106;
    }
    /**
	 * 设置结转料件退换数
	 * @param hsAmount1106 结转料件退换数.
	 */
    public void setHsAmount1106(Double hsAmount1106) {
        this.hsAmount1106 = hsAmount1106;
    }
	
	
	
}
