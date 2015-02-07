/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放中期核查－－中期核查表头资料
 */
public class CheckHead extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
     * 参数设定
     */
    private CheckParameter  head = null;  
	  
    /**
     * 帐册编号
     */
    private String emsNo;    
	  
    /**
     * 经营单位代码
     */
    private String tradeCode;  
	  
    /**
     * 经营单位名称
     */
    private String tradeName;  
	  
    /**
     * 加工单位代码
     */
    private String machCode;    
	  
    /**
     * 加工单位名称
     */
    private String machName;   
	  
    /**
     * 主管海关
     */
    private Customs masterCustoms;  
	  
    /**
     * 申报类型
     */
    private String flagHg;       
	
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
	 * 获取加工单位代码
	 * 
	 * @return machCode 加工单位代码
	 */
	public String getMachCode() {
		return machCode;
	}
		
	/**
	 * 设置加工单位代码
	 * 
	 * @param machCode 加工单位代码
	 */
	public void setMachCode(String machCode) {
		this.machCode = machCode;
	}
		
	/**
	 * 获取加工单位名称
	 * 
	 * @return machName 加工单位名称
	 */
	public String getMachName() {
		return machName;
	}
		
	/**
	 * 设置加工单位名称
	 * 
	 * @param machName 加工单位名称
	 */
	public void setMachName(String machName) {
		this.machName = machName;
	}
		
	/**
	 * 获取主管海关
	 * 
	 * @return masterCustoms 主管海关
	 */
	public Customs getMasterCustoms() {
		return masterCustoms;
	}
		
	/**
	 * 设置主管海关
	 * 
	 * @param masterCustoms 主管海关
	 */
	public void setMasterCustoms(Customs masterCustoms) {
		this.masterCustoms = masterCustoms;
	}
		
	/**
	 * 获取经营单位代码
	 * 
	 * @return tradeCode 经营单位代码
	 */
	public String getTradeCode() {
		return tradeCode;
	}
		
	/**
	 * 设置经营单位代码
	 * 
	 * @param tradeCode 经营单位代码
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
		
	/**
	 * 获取经营单位名称
	 * 
	 * @return tradeName 经营单位名称
	 */
	public String getTradeName() {
		return tradeName;
	}
		
	/**
	 * 设置经营单位名称
	 * 
	 * @param tradeName 经营单位名称
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
		
	/**
	 * 获取申报类型，"1"-中期核查，"2"-库存调整
	 * 
	 * @return flagHg 申报类型，"1"-中期核查，"2"-库存调整
	 */
	public String getFlagHg() {
		return flagHg;
	}
		
	/**
	 * 设置申报类型，"1"-中期核查，"2"-库存调整
	 * 
	 * @param flagHg 申报类型，"1"-中期核查，"2"-库存调整
	 */
	public void setFlagHg(String flagHg) {
		this.flagHg = flagHg;
	}
		
	/**
	 * 获取参数设定
	 * 
	 * @return head 参数设定
	 */
	public CheckParameter getHead() {
		return head;
	}
	
	/**
	 * 设置参数设定
	 * 
	 * @param head 参数设定
	 */
	public void setHead(CheckParameter head) {
		this.head = head;
	}
}
