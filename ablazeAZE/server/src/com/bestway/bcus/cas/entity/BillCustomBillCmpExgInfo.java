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
 * 单据与报关单对比
 */
public class BillCustomBillCmpExgInfo implements Serializable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
	 *成品名称
	 */
	private String ptName;
	/**
	 * 成品规格
	 */
	private String ptSpec;
	/**
	 * 单位名称
	 */
	private String ptUnitName;
	/**
	 * 直接报关出口数量
	 */
	private double f2;
	/**
	 * 直接报关出口数量
	 */
	private double f2CustomNum;
	
	
	/**
	 * 构造函数 初始化商品名称 规格 单位名称 直接报关出口数量 直接报关出口数量
	 */
	public BillCustomBillCmpExgInfo(){
		ptName = "" ;
		ptSpec = "" ;
		ptUnitName = "" ;
		
		f2 = 0 ;
		f2CustomNum = 0 ;
		
	}
    
	/**
	 * 取得键值
	 * @return 键值(成品名称+成品规格+单位名称)
	 */
    public String getKey(){
        String ptSpec  = (this.ptSpec == null || "".equals(this.ptSpec))?"":"/"+this.ptSpec;   
        String ptUnitName = (this.ptUnitName == null || "".equals(this.ptUnitName))?"":"/"+this.ptUnitName; 
        return this.ptName+ptSpec+ptUnitName;
    }
	
	/**
	 * 直接报关出口数量
	 * @return 直接报关出口数量
	 */
	public double getF2() {
		return f2;
	}
	/**
	 * 设置直接报关出口数量
	 * @param f2 直接报关出口数量
	 */
	public void setF2(double f2) {
		this.f2 = f2;
	}
	/**
	 * 取得直接报关出口数量
	 * @return f2CustomNum 直接报关出口数量
	 */
	public double getF2CustomNum() {
		return f2CustomNum;
	}
	/**
	 * 设置直接报关出口数量
	 * @param customNum 直接报关出口数量
	 */
	public void setF2CustomNum(double customNum) {
		f2CustomNum = customNum;
	}
	/**取得成品名称
	 * @return ptName 成品名称
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
	 * @return 成品规格
	 */
	public String getPtSpec() {
		return ptSpec;
	}
	/**
	 * 设置成品规格
	 * @param ptSpec 成品规格
	 */
	
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}
	/**
	 * 取得单位名称
	 * @return 单位名称
	 */
	public String getPtUnitName() {
		return ptUnitName;
	}
	/**
	 * 设置单位名称
	 * @param ptUnitName 单位名称
	 */
	public void setPtUnitName(String ptUnitName) {
		this.ptUnitName = ptUnitName;
	}
}
