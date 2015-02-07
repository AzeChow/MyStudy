package com.bestway.common.erpbill.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 用于订单转合同时生成的报关资料
 * 
 * @author ower
 * 
 */
public class TempCustomOrderChangContract implements Serializable {

	/**
	 * 备案号
	 */
	private Integer seqNum = null;
	/**
	 * 商品编码
	 */
	private Complex code = null;
	/**
	 * 商品名称
	 */
	private String name = null;
	/**
	 * 规格型号
	 */
	private String spec = null;
	/**
	 * 计量单位
	 */
	private Unit unit = null;
	/**
	 * 料号
	 */
	private String ptNo = null;
	/**
	 * 版本号
	 */
	private Integer version = null;
	/**
	 * 单价
	 */
	private Double unitPrice = 0.0;
	/**
	 * 未转合同数量
	 */
	private Double notContractNum = 0.0;
	/**
	 * 总金额
	 */
	private Double totalPrice = 0.0;

	private Boolean isSelected ;

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Complex getCode() {
		return code;
	}

	public void setCode(Complex code) {
		this.code = code;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Double getNotContractNum() {
		return notContractNum;
	}

	public void setNotContractNum(Double notContractNum) {
		this.notContractNum = notContractNum;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
