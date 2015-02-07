/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;

/**
 * 存放滚动核销－－帐帐分析－－转厂数据分析－－料件转厂－－工厂资料
 */
public class EmsTransFactory extends BaseScmEntity implements Cloneable{
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
     * 已收货未转厂数量
     */
    private Double unTransferNum = null; 
     
    /**
     * 已送货未转厂折料数量
     */
    private Double unReceiveNum = null;
    
    /**
     * 导入错误信息
     */
    private String err;
     
    
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
	 * @return the unTransferNum
	 */
	public Double getUnTransferNum() {
		return unTransferNum;
	}

	/**
	 * @param unTransferNum the unTransferNum to set
	 */
	public void setUnTransferNum(Double unTransferNum) {
		this.unTransferNum = unTransferNum;
	}

	/**
	 * @return the unReceiveNum
	 */
	public Double getUnReceiveNum() {
		return unReceiveNum;
	}

	/**
	 * @param unReceiveNum the unReceiveNum to set
	 */
	public void setUnReceiveNum(Double unReceiveNum) {
		this.unReceiveNum = unReceiveNum;
	}

	/**
	 * @return the err
	 */
	public String getErr() {
		return err;
	}

	/**
	 * @param err the err to set
	 */
	public void setErr(String err) {
		this.err = err;
	}
	
}
