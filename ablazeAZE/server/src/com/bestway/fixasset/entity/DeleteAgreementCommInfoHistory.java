package com.bestway.fixasset.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 删除批文协议设备明细历史
 * @author Administrator
 *
 */
public class DeleteAgreementCommInfoHistory   extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	/**
	 * 协议单头
	 */
	private Agreement agreement;
	/**
	 * 变更次数
	 */
	private Integer changedTimes;
	/**
	 * 主序号
	 */
	private Integer mainNo; 
	/**
	 * 海关编码
	 */
	private Complex complex; 
	/**
	 * 商品名称
	 */
	private String commName;
	/**
	 * 商品规格
	 */
	private String commSpec;
	/**
	 * 单价
	 */
	private Double unitPrice;
	/**
	 * 数量
	 */
	private Double amount;
	/**
	 * 单位
	 */
	private Unit unit;
	/**
	 * 总金额
	 */
	private Double totalPrice; 
	
	/**
	 * 原产国 or 最终目的产国
	 */
	private Country country;

	/**
	 * 状态
	 */
	private Integer stateMark;

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	public Integer getChangedTimes() {
		return changedTimes;
	}

	public void setChangedTimes(Integer changedTimes) {
		this.changedTimes = changedTimes;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getCommSpec() {
		return commSpec;
	}

	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Integer getMainNo() {
		return mainNo;
	}

	public void setMainNo(Integer mainNo) {
		this.mainNo = mainNo;
	}

	public Integer getStateMark() {
		return stateMark;
	}

	public void setStateMark(Integer stateMark) {
		this.stateMark = stateMark;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
}
