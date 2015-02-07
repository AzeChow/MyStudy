/*
 * Created on 2005-5-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractstat.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 存放统计报表中的进口料件报关情况表－－统计数据资料
 * 
 * @author Administrator
 */
public class DzscImpMaterialStatResult implements Serializable{

	/**
	 * 合同进口金额
	 */
	private Double contractMoney;

	/**
	 * 总进口量
	 */
	private Double impTotalMoney;

	/**
	 * 余料金额
	 */
	private Double remainMoney;

	/**
	 * 总进口比例
	 */
	private Double impTotalScale;

	/**
	 * 余料比例
	 */
	private Double remainScale;
	
	/**
	 * 获取合同进口金额
	 * 
	 * @return contractMoney 合同进口金额
	 */
	public Double getContractMoney() {
		return contractMoney;
	}
	
	/**
	 * 获取总进口量
	 * 
	 * @return impTotalMoney 总进口量
	 */
	public Double getImpTotalMoney() {
		return impTotalMoney;
	}
	
	/**
	 * 获取总进口比例
	 * 
	 * @return mpTotalScale 总进口比例
	 */
	public Double getImpTotalScale() {
		return impTotalScale;
	}
	/**
	 * 获取余料金额
	 * 
	 * @return  remainMoney 余料金额
	 */
	public Double getRemainMoney() {
		return remainMoney;
	}
	
	/**
	 * 获取余料比例
	 * 
	 * @return remainScale 余料比例
	 */
	public Double getRemainScale() {
		return remainScale;
	}
	
	/**
	 * 设置合同进口金额
	 * 
	 * @param contractMoney 合同进口金额
	 */
	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}
	
	/**
	 * 设置总进口量
	 * 
	 * @param impTotalMoney 总进口量
	 */
	public void setImpTotalMoney(Double impTotalMoney) {
		this.impTotalMoney = impTotalMoney;
	}
	
	/**
	 * 设置总进口比例
	 * 
	 * @param impTotalScale 总进口比例
	 */
	public void setImpTotalScale(Double impTotalScale) {
		this.impTotalScale = impTotalScale;
	}
	
	/**
	 * 设置余料金额
	 * 
	 * @param remainMoney 余料金额
	 */
	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
	}
	
	/**
	 * 设置余料比例
	 * 
	 * @param remainScale 余料比例
	 */
	public void setRemainScale(Double remainScale) {
		this.remainScale = remainScale;
	}
}
