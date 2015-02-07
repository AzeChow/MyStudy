/*
 * Created on 2005-5-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractstat.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 存放统计报表中的出口成品报关情况表－－统计数据资料
 */
public class ExpProductStatResult implements Serializable{
	/**
	 * 合同出口金额
	 */
	private Double contractMoney;
	
	/**
	 * 出口成品总额
	 */
	private Double expTotalMoney;
	
	/**
	 * 出口成品总值比率
	 */
	private Double scale;
	
	/**
	 * 获取合同出口金额
	 * 
	 * @return contractAmount 合同出口金额
	 */
	public Double getContractMoney() {
		return contractMoney;
	}
	
	/**
	 * 获取出口成品总额
	 * 
	 * @return expTotalAmont 出口成品总额
	 */
	public Double getExpTotalMoney() {
		return expTotalMoney;
	}
	
	/**
	 * 获取出口成品总值比率
	 * 
	 * @return scale 出口成品总值比率
	 */
	public Double getScale() {
		return scale;
	}
	
	/**
	 * 设置合同出口金额
	 * 
	 * @param contractAmount 合同出口金额
	 */
	public void setContractMoney(Double contractAmount) {
		this.contractMoney = contractAmount;
	}
	
	/**
	 * 设置出口成品总额
	 * 
	 * @param expTotalAmont 出口成品总额
	 */
	public void setExpTotalMoney(Double expTotalAmont) {
		this.expTotalMoney = expTotalAmont;
	}
	
	/**
	 * 设置出口成品总值比率
	 * 
	 * @param scale 出口成品总值比率
	 */
	public void setScale(Double scale) {
		this.scale = scale;
	}
}
