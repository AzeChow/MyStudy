package com.bestway.customs.common.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;

public class ExportInvoice extends BaseScmEntity {
	private static final long serialVersionUID = -2715135850653084043L;

	/**
	 * 商品序号
	 */
	private Integer commSerialNo;

	/**
	 * 商品信息
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
	 * 商品数量
	 */
	private Double commAmount;
	/**
	 * 币别
	 */
	private Curr currency;
	/**
	 * 单位
	 */
	private Unit unit;
	/**
	 * 商品单价
	 */
	private Double commUnitPrice;
	/**
	 * 商品总价
	 */
	private Double commTotalPrice;
	/**
	 * 加工费单价
	 */
	private Double processUnitPrice;

	/**
	 * 加工费总价
	 */
	private Double processTotalPrice;

	public Integer getCommSerialNo() {
		return commSerialNo;
	}

	public void setCommSerialNo(Integer commSerialNo) {
		this.commSerialNo = commSerialNo;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
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

	public Curr getCurrency() {
		return currency;
	}

	public void setCurrency(Curr currency) {
		this.currency = currency;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Double getCommTotalPrice() {
		return commTotalPrice;
	}

	public void setCommTotalPrice(Double commTotalPrice) {
		this.commTotalPrice = commTotalPrice;
	}

	public Double getProcessUnitPrice() {
		return processUnitPrice;
	}

	public void setProcessUnitPrice(Double processUnitPrice) {
		this.processUnitPrice = processUnitPrice;
	}

	public Double getProcessTotalPrice() {
		return processTotalPrice;
	}

	public void setProcessTotalPrice(Double processTotalPrice) {
		this.processTotalPrice = processTotalPrice;
	}

	public Double getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}

	public Double getCommUnitPrice() {
		return commUnitPrice;
	}

	public void setCommUnitPrice(Double commUnitPrice) {
		this.commUnitPrice = commUnitPrice;
	}
	
	
}
