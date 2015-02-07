/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 给核销料件的核销中间过程继承
 */
public class CancelImgBefore extends BaseScmEntity implements Cloneable{
    
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
       
	/**
     * 核销表头
     */
    private CancelHead cancelHead; 
	   
	/**
     * 帐册序号
     */
    private Integer emsSeqNum;    
	   
	/**
     * 数量
     */
    private Double num;        
	   
	/**
     * 价值(总价)
     */
    private Double price;         
	   
	/**
     * 报关单号
     */
    private String customNo;    
	   
	/**
     * 进出口类型
     */
    private String inOutType;   
	   
	/**
     * 贸易方式
     */
    private String tradeMode;      
	   
	/**
     * 运输方式
     */
    private String transportMode;  
	   
	/**
     * 核扣代码
     */
    private String checkCode;      
	   
	/**
     * 核扣方法
     */
    private String checkWay;     
    
	/**
     * 核扣名称
     */
    private String checkName;     
	   
	/**
     * 核扣方式说明
     */
    private String checkModeShow;  
    /**
     * 获取核销表头
     * 
	 * @return cancelHead 核销表头
	 */
	public CancelHead getCancelHead() {
		return cancelHead;
	}
		
	/**
	 * 设置核销表头
	 * 
	 * @param cancelHead 核销表头
	 */
	public void setCancelHead(CancelHead cancelHead) {
		this.cancelHead = cancelHead;
	}
		
    /**
     * 获取核扣代码
     * 
	 * @return checkCode 核扣代码
	 */
	public String getCheckCode() {
		return checkCode;
	}
		
	/**
	 * 设置核扣代码
	 * 
	 * @param checkCode 核扣代码
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
		
    /**
     * 获取核扣方式说明
     * 
	 * @return checkModeShow 核扣方式说明
	 */
	public String getCheckModeShow() {
		return checkModeShow;
	}
		
	/**
	 * 设置核扣方式说明
	 * 
	 * @param checkModeShow 核扣方式说明
	 */
	public void setCheckModeShow(String checkModeShow) {
		this.checkModeShow = checkModeShow;
	}
		
    /**
     * 获取核扣名称
     * 
	 * @return checkName 核扣名称
	 */
	public String getCheckName() {
		return checkName;
	}
		
	/**
	 * 设置核扣名称
	 * 
	 * @param checkName 核扣名称
	 */
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
		
    /**
     * 获取核扣方法
     * 
	 * @return checkWay 核扣方法
	 */
	public String getCheckWay() {
		return checkWay;
	}
		
	/**
	 * 设置核扣方法
	 * 
	 * @param checkWay 核扣方法
	 */
	public void setCheckWay(String checkWay) {
		this.checkWay = checkWay;
	}

		
    /**
     * 获取帐册序号
     * 
	 * @return emsSeqNum 帐册序号
	 */
	public Integer getEmsSeqNum() {
		return emsSeqNum;
	}
		
	/**
	 * 设置帐册序号
	 * 
	 * @param emsSeqNum 帐册序号
	 */
	public void setEmsSeqNum(Integer emsSeqNum) {
		this.emsSeqNum = emsSeqNum;
	}
		
    /**
     * 获取进出口类型（已不使用）
     * 
	 * @return inOutType 进出口类型（已不使用）
	 */
	public String getInOutType() {
		return inOutType;
	}
		
	/**
	 * 设置进出口类型（已不使用）
	 * 
	 * @param inOutType 进出口类型（已不使用）
	 */
	public void setInOutType(String inOutType) {
		this.inOutType = inOutType;
	}
		
    /**
     * 获取数量
     * 
	 * @return num 数量
	 */
	public Double getNum() {
		return num;
	}
		
	/**
	 * 设置数量
	 * 
	 * @param num 数量
	 */
	public void setNum(Double num) {
		this.num = num;
	}
		
    /**
     * 获取价值(总价)
     * 
	 * @return price 价值(总价)
	 */
	public Double getPrice() {
		return price;
	}
		
	/**
	 * 设置价值(总价)
	 * 
	 * @param price 价值(总价)
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
		
    /**
     * 获取贸易方式
     * 
	 * @return tradeMode 贸易方式
	 */
	public String getTradeMode() {
		return tradeMode;
	}
		
	/**
	 * 设置贸易方式
	 * 
	 * @param tradeMode 贸易方式
	 */
	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}
		
    /**
     * 获取运输方式（已不使用）
     * 
	 * @return transportMode 运输方式（已不使用）
	 */
	public String getTransportMode() {
		return transportMode;
	}
		
	/**
	 * 设置运输方式（已不使用）
	 * 
	 * @param transportMode 运输方式（已不使用）
	 */
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}
		
    /**
     * 获取报关单号（已不使用）
     * 
	 * @return customNo 报关单号（已不使用）
	 */
	public String getCustomNo() {
		return customNo;
	}
	
	/**
	 * 设置报关单号（已不使用）
	 * 
	 * @param customNo 报关单号（已不使用）
	 */
	public void setCustomNo(String customNo) {
		this.customNo = customNo;
	}
}
