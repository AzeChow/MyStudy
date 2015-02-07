package com.bestway.bcus.financial.entity;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

public class CostSummaryReport extends BaseScmEntity {
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
	 * 本月产量数量
	 */
	private Double thisInCount;
	/**
	 * 本月销售价格
	 */
	private Double salePrice;
	/**
	 * 本月产值
	 */
	private Double totalAmount;
	/**
	 * 本月料件耗用费用
	 */
	private Double ljTotalAmount;
	/**
	 * 本月成品制造费
	 */
	private Double makeAmount;
	/**
	 * 本月成品人工费
	 */
	private Double laborAmount;
	/**
	 * 本月成品成本合计
	 */
	private Double costTotalAmount;
	/**
	 * 本月成品成本单价
	 */
	private Double costPrice;
	/**
	 * 本月成品制造费总费 页面上输入
	 */
	private Double totalMakeAmount;
	/**
	 * 本月成品人工费总费 页面上输入
	 */
	private Double totalLaborAmount;
	
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
	public Double getThisInCount() {
		return thisInCount;
	}
	public void setThisInCount(Double thisInCount) {
		this.thisInCount = thisInCount;
	}
	public Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Double getLjTotalAmount() {
		return ljTotalAmount;
	}
	public void setLjTotalAmount(Double ljTotalAmount) {
		this.ljTotalAmount = ljTotalAmount;
	}
	public Double getMakeAmount() {
		return makeAmount;
	}
	public void setMakeAmount(Double makeAmount) {
		this.makeAmount = makeAmount;
	}
	public Double getLaborAmount() {
		return laborAmount;
	}
	public void setLaborAmount(Double laborAmount) {
		this.laborAmount = laborAmount;
	}
	public Double getCostTotalAmount() {
		return costTotalAmount;
	}
	public void setCostTotalAmount(Double costTotalAmount) {
		this.costTotalAmount = costTotalAmount;
	}
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Double getTotalMakeAmount() {
		return totalMakeAmount;
	}
	public void setTotalMakeAmount(Double totalMakeAmount) {
		this.totalMakeAmount = totalMakeAmount;
	}
	public Double getTotalLaborAmount() {
		return totalLaborAmount;
	}
	public void setTotalLaborAmount(Double totalLaborAmount) {
		this.totalLaborAmount = totalLaborAmount;
	}
}
