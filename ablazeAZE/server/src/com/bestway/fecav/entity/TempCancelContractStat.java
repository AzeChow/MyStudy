/*
 * Created on 2005-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.fecav.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Administrator 外汇核销合同汇总表 
 *         generated type comment go to Window - Preferences - Java - Code Style -
 *         Code Templates
 */
public class TempCancelContractStat implements Serializable {

	/**
	 * 合同号
	 */
	private String	contractNo		= null; 
	/**
	 * 份数
	 */
	private Integer	count			= null; 
	/**
	 * 出口金额
	 */
	private Double	exportMoney		= null; 
	/**
	 * 币制
	 */
	private String	currName		= null; 
	/**
	 * 预算料值比例
	 */
	private Double	rate			= null; 
	/**
	 * 可抵扣料件金额
	 */
	private Double	materielMoney	= null; 

	/**
	 * 获得合同号
	 * @return 合同号
	 */
	public String getContractNo() {
		return contractNo;
	}

	/**
	 * 设置合同号
	 * @param contractNo 合同号
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * 获得份数
	 * @return 份数
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * 设置份数
	 * @param count 份数
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 获得币制
	 * @return 币制
	 */
	public String getCurrName() {
		return currName;
	}

	/**
	 * 设置币制
	 * @param currName 币制
	 */
	public void setCurrName(String currName) {
		this.currName = currName;
	}

	/**
	 * 获得出口金额
	 * @return 出口金额
	 */
	public Double getExportMoney() {
		return exportMoney;
	}

	/**
	 * 设置出口金额
	 * @param exportMoney 出口金额
	 */
	public void setExportMoney(Double exportMoney) {
		this.exportMoney = exportMoney;
	}

	/**
	 * 获得可抵扣料件金额
	 * @return 可抵扣料件金额
	 */
	public Double getMaterielMoney() {
		return materielMoney;
	}

	/**
	 * 设置可抵扣料件金额
	 * @param materielMoney 可抵扣料件金额
	 */
	public void setMaterielMoney(Double materielMoney) {
		this.materielMoney = materielMoney;
	}

	/**
	 * 获得预算比率
	 * @return 保存两位小数的预算料值比例
	 */
	public Double getRate() {
		double exportMoney = 0.0;
		double materielMoney = 0.0;
		if (this.exportMoney != null) {
			exportMoney = this.exportMoney;
		}
		if (this.materielMoney != null) {
			materielMoney = this.materielMoney;
		}
		BigDecimal b = new BigDecimal(materielMoney / exportMoney);
		double df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return df;
	}

	
	
	/**
	 * 设置预算料值比例
	 * @param rate 预算料值比例
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

}
