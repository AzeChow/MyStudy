/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;

/**
 * 存放滚动核销－－帐帐分析－－盘点数据分析－－料件盘点－－工厂资料
 */
public class EmsPdImg extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
     * 帐帐分析表头
     */
    private EmsAnalyHead head = null;
    
    /**
     * 料号
     */
    private String ptNo = null;
     
    /**
     * 品名
     */
    private String ptName = null;
     
    /**
     * 规格
     */
    private String ptSpec = null;
     
    /**
     * 单位
     */
    private CalUnit calUnit = null;
     
    /**
     * 保税数量
     */
    private Double proNum = null; 
     
    /**
     * 非保税数量
     */
    private Double notProNum = null;
     
    /**
     * 盘点数量
     */
    private Double pdNum = null; 
    
	/**
	 * 获取单位
	 * 
	 * @return calUnit 单位
	 */
	public CalUnit getCalUnit() {
		return calUnit;
	}
	
	/**
	 * 设置单位
	 * 
	 * @param calUnit 单位
	 */
	public void setCalUnit(CalUnit calUnit) {
		this.calUnit = calUnit;
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
	 * 获取非保税数量
	 * 
	 * @return notProNum 非保税数量
	 */
	public Double getNotProNum() {
		return notProNum;
	}
	
	/**
	 * 设置非保税数量
	 * 
	 * @param notProNum 非保税数量
	 */
	public void setNotProNum(Double notProNum) {
		this.notProNum = notProNum;
	}
	
	/**
	 * 获取盘点数量
	 * 
	 * @return pdNum 盘点数量
	 */
	public Double getPdNum() {
		return pdNum;
	}
	
	/**
	 * 设置盘点数量
	 * 
	 * @param pdNum 盘点数量
	 */
	public void setPdNum(Double pdNum) {
		this.pdNum = pdNum;
	}
	
	/**
	 * 获取保税数量
	 * 
	 * @return proNum 保税数量
	 */
	public Double getProNum() {
		return proNum;
	}
	
	/**
	 * 设置保税数量
	 * 
	 * @param proNum 保税数量
	 */
	public void setProNum(Double proNum) {
		this.proNum = proNum;
	}
	
	/**
	 * 获取品名
	 * 
	 * @return ptName 品名
	 */
	public String getPtName() {
		return ptName;
	}
	
	/**
	 * 设置品名
	 * 
	 * @param ptName 品名
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	
	/**
	 * 获取料号
	 * 
	 * @return ptNo 料号
	 */
	public String getPtNo() {
		return ptNo;
	}
	
	/**
	 * 设置料号
	 * 
	 * @param ptNo 料号
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}
	
	/**
	 * 获取规格
	 * 
	 * @return ptSpec 规格
	 */
	public String getPtSpec() {
		return ptSpec;
	}
	
	/**
	 * 设置规格
	 * 
	 * @param ptSpec 规格
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}
}
