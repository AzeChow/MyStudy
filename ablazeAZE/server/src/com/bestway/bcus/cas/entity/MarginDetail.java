/*
 * Created on 2004-9-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;


import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 利润表明细
 */
public class MarginDetail extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
	 * 利润表表头
	 */
	private MarginMaster marginMaster;
	/**
	 * 项目
	 */
	private String item;       
	/**
	 * 行次
	 */
	private String times;       
	/**
	 * 本期数
	 */
	private Double thisCount;   
	/**
	 * 本年累计数
	 */
	private Double thisSumCount; 
	/**
	 * 上年同期累计数
	 */
	private Double priveSumCount;
	
	/**
	 * 取得项目明细
	 * @return item 项目
	 */
	public String getItem() {
		return item;
	}
	/**
	 * 设置项目明细
	 * @param item 项目
	 */
	public void setItem(String item) {
		this.item = item;
	}
	/**
	 * 取得利润表表头
	 * @return  marginMaster 利润表表头
	 */
	public MarginMaster getMarginMaster() {
		return marginMaster;
	}
	/**
	 * 设置利润表表头
	 * @param marginMaster 利润表表头
	 */
	public void setMarginMaster(MarginMaster marginMaster) {
		this.marginMaster = marginMaster;
	}
	/**
	 * 取得上年同期累计数
	 * @return  priveSumCount 上年同期累计数
	 */
	public Double getPriveSumCount() {
		return priveSumCount;
	}
	/**
	 * 设置上年同期累计数
	 *@param priveSumCount 上年同期累计数
	 */
	public void setPriveSumCount(Double priveSumCount) {
		this.priveSumCount = priveSumCount;
	}
	/**
	 * 取得本期数
	 * @return thisCount 本期数
	 */
	public Double getThisCount() {
		return thisCount;
	}
	/**
	 * 设置本期数
	 * @param thisCount 本期数
	 */
	public void setThisCount(Double thisCount) {
		this.thisCount = thisCount;
	}
	/**
	 * 取得本期累计数
	 * @return thisSumCount 本期累计数
	 */
	public Double getThisSumCount() {
		return thisSumCount;
	}
	/**
	 * 设置本期累计数
	 * @param thisSumCount 本期累计数
	 */
	public void setThisSumCount(Double thisSumCount) {
		this.thisSumCount = thisSumCount;
	}
	/**
	 * 取得行次
	 * @return times 行次
	 */
	public String getTimes() {
		return times;
	}
	/**
	 * 设置行次
	 * @param times 行次
	 */
	public void setTimes(String times) {
		this.times = times;
	}
}