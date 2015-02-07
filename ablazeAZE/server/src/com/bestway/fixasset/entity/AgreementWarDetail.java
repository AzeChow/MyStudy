package com.bestway.fixasset.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 批文协议设备保证书明细
 * @author Administrator
 *
 */
public class AgreementWarDetail extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	/**
	 * 批文协议设备保证书表头
	 */
	private AgreementWarHead warHead;
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
	 * 总金额
	 */
	private Double totalPrice;
	/**
	 * 单位
	 */
	private Unit unit;
	/**
	 * 币制
	 */
	private Curr curr;

	/**
	 * 原产国
	 */
	private Country country;

	public Integer getMainNo() {
		return mainNo;
	}

	public void setMainNo(Integer mainNo) {
		this.mainNo = mainNo;
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

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
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

	public AgreementWarHead getWarHead() {
		return warHead;
	}

	public void setWarHead(AgreementWarHead warHead) {
		this.warHead = warHead;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
}
