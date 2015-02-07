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
 * 存放滚动核销－－帐帐分析－－盘点数据分析－－成品盘点－－工厂资料
 */
public class EmsPdExg extends BaseScmEntity implements Cloneable{
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
     * 工单号
     */
    private String workBillNo = null;
       
    /**
     * 版本号
     */
    private String versionNo = null;
       
    /**
     * 盘点数量
     */
    private Double pdNum = null;
       
    /**
     * 计量单位
     */
    private CalUnit calUnit = null; 
    
	/**
	 * 获取计量单位
	 * 
	 * @return calUnit 计量单位
	 */
	public CalUnit getCalUnit() {
		return calUnit;
	}
		
	/**
	 * 设置计量单位
	 * 
	 * @param calUnit 计量单位
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
	   
	/**
	 * 获取版本号
	 * 
	 * @return versionNo 版本号
	 */
	public String getVersionNo() {
		return versionNo;
	}
		
	/**
	 * 设置版本号
	 * 
	 * @param versionNo 版本号
	 */
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	   
	/**
	 * 获取工单号
	 * 
	 * @return workBillNo 工单号
	 */
	public String getWorkBillNo() {
		return workBillNo;
	}
	
	/**
	 * 设置工单号
	 * 
	 * @param workBillNo 工单号
	 */
	public void setWorkBillNo(String workBillNo) {
		this.workBillNo = workBillNo;
	}
}
