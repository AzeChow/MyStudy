/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放中期核查－－参数设定资料
 */
public class CheckParameter extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
	/**
	 * 核查流水号
	 */
	private Integer emsNo = null;
	    
	/**
	 * 核查起始日
	 */
	private Date beginDate = null;
	    
	/**
	 * 核查截止日
	 */
	private Date endDate = null; 
	    
	/**
	 * 备注
	 */
	private String note = null; 
	
	/**
	 * 获取serialVersionUID
	 * 
	 * @return serialVersionUID 
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
		
	/**
	 * 获取核查起始日
	 * 
	 * @return beginDate 核查起始日
	 */
	public Date getBeginDate() {
		return beginDate;
	}
		
	/**
	 * 设置核查起始日
	 * 
	 * @param beginDate 核查起始日
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
		
	/**
	 * 获取核查流水号
	 * 
	 * @return emsNo 核查流水号
	 */
	public Integer getEmsNo() {
		return emsNo;
	}
		
	/**
	 * 设置核查流水号
	 * 
	 * @param emsNo 核查流水号
	 */
	public void setEmsNo(Integer emsNo) {
		this.emsNo = emsNo;
	}
		
	/**
	 * 获取核查截止日
	 * 
	 * @return endDate 核查截止日
	 */
	public Date getEndDate() {
		return endDate;
	}
		
	/**
	 * 设置核查截止日
	 * 
	 * @param endDate 核查截止日
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
		
	/**
	 * 获取备注
	 * 
	 * @return note 备注
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * 设置备注
	 * 
	 * @param note 备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
}
