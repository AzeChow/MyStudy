package com.bestway.bcus.financial.entity;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

public class ProductAccount extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 报表日期
	 */
	private String accountDate;
	/**
	 * 海关商品名称
	 */
	private String hsName;

	/**
	 * 海关商品规格型号
	 */
	private String hsSpec;
	/**
	 * 海关单位
	 */
	private Unit hsUnit;
	/**
	 * 上月结存数量
	 */
	private Double previousCount;
	/**
	 * 上月结存金额
	 */
	private Double previousAmount;
	/**
	 * 本月进入数量
	 */
	private Double thisInCount;
	/**
	 * 本月进入金额
	 */
	private Double thisInAmount;
	/**
	 * 本月直接出口商品数量
	 */
	private Double thisExportCount;
	/**
	 * 本月直接出口商品金额
	 */
	private Double thisExportAmount;
	/**
	 * 本月发出商品数量
	 */
	private Double thisOutCount;
	/**
	 * 本月发出商品金额
	 */
	private Double thisOutAmount;
	/**
	 * 本月结存数量
	 */
	private Double balanceCount;
	/**
	 * 本月结存金额
	 */
	private Double balanceAmount;
	/**
	 * 本月加权单价，与料件加权平均价相同，用于计算成品汇总表时不用再去查数据
	 */
	private Double averagePrice;
	
	public String getAccountDate() {
		return accountDate;
	}
	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}
	public String getHsName() {
		return hsName;
	}
	public void setHsName(String hsName) {
		this.hsName = hsName;
	}
	public String getHsSpec() {
		return hsSpec;
	}
	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}
	public Unit getHsUnit() {
		return hsUnit;
	}
	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}
	public Double getPreviousCount() {
		return previousCount;
	}
	public void setPreviousCount(Double previousCount) {
		this.previousCount = previousCount;
	}
	public Double getPreviousAmount() {
		return previousAmount;
	}
	public void setPreviousAmount(Double previousAmount) {
		this.previousAmount = previousAmount;
	}
	public Double getThisInCount() {
		return thisInCount;
	}
	public void setThisInCount(Double thisInCount) {
		this.thisInCount = thisInCount;
	}
	public Double getThisInAmount() {
		return thisInAmount;
	}
	public void setThisInAmount(Double thisInAmount) {
		this.thisInAmount = thisInAmount;
	}
	public Double getThisOutCount() {
		return thisOutCount;
	}
	public void setThisOutCount(Double thisOutCount) {
		this.thisOutCount = thisOutCount;
	}
	public Double getThisOutAmount() {
		return thisOutAmount;
	}
	public void setThisOutAmount(Double thisOutAmount) {
		this.thisOutAmount = thisOutAmount;
	}
	public Double getBalanceCount() {
		return balanceCount;
	}
	public void setBalanceCount(Double balanceCount) {
		this.balanceCount = balanceCount;
	}
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Double getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(Double averagePrice) {
		this.averagePrice = averagePrice;
	}
	public Double getThisExportCount() {
		return thisExportCount;
	}
	public void setThisExportCount(Double thisExportCount) {
		this.thisExportCount = thisExportCount;
	}
	public Double getThisExportAmount() {
		return thisExportAmount;
	}
	public void setThisExportAmount(Double thisExportAmount) {
		this.thisExportAmount = thisExportAmount;
	}
	
}
