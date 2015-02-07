package com.bestway.bcus.financial.entity;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

public class MonthlyProductionAndSales extends BaseScmEntity {
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
	private Double lastQuantity;
	/**
	 * 本月产量数量
	 */
	private Double productionQuantity;
	/**
	 * 本月产量总价
	 */
	private Double productionAccount;
	/**
	 * 本月产量单价
	 */
	private Double productionPrice;
	/**
	 * 本月销量数量
	 */
	private Double saleQuantity;
	/**
	 * 本月销量总价
	 */
	private Double saleAccount;
	/**
	 * 本月销量单价
	 */
	private Double salePrice;
	
	
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
	public Double getLastQuantity() {
		return lastQuantity;
	}
	public void setLastQuantity(Double lastQuantity) {
		this.lastQuantity = lastQuantity;
	}
	public Double getProductionQuantity() {
		return productionQuantity;
	}
	public void setProductionQuantity(Double productionQuantity) {
		this.productionQuantity = productionQuantity;
	}
	public Double getProductionAccount() {
		return productionAccount;
	}
	public void setProductionAccount(Double productionAccount) {
		this.productionAccount = productionAccount;
	}
	public Double getProductionPrice() {
		return productionPrice;
	}
	public void setProductionPrice(Double productionPrice) {
		this.productionPrice = productionPrice;
	}
	public Double getSaleQuantity() {
		return saleQuantity;
	}
	public void setSaleQuantity(Double saleQuantity) {
		this.saleQuantity = saleQuantity;
	}
	public Double getSaleAccount() {
		return saleAccount;
	}
	public void setSaleAccount(Double saleAccount) {
		this.saleAccount = saleAccount;
	}
	public Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	
}
