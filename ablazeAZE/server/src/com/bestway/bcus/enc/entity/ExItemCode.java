package com.bestway.bcus.enc.entity;

import java.io.Serializable;

import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;

public class ExItemCode implements Serializable{

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 海关商品编码
	 */
	String code;

	/**
	 * 出口商品代码
	 */
	String outItemCode;

	/**
	 * 出口商品名称
	 */
	String outItemName;

	/**
	 * 申请商品代码
	 */
	String reqItemCode;

	/**
	 * 申请商品名称
	 */
	String reqItemName;

	/**
	 * 计量单位
	 * 
	 * @return
	 */
	String unit;

	/**
	 * 征税税率
	 * @return
	 */
	String outRate;

	/**
	 * 退税税率
	 */
	String inRate;

	/**
	 * 进出口类型
	 */
	String types;

	// String editDate;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOutItemCode() {
		return outItemCode;
	}

	public void setOutItemCode(String outItemCode) {
		this.outItemCode = outItemCode;
	}

	public String getOutItemName() {
		return outItemName;
	}

	public void setOutItemName(String outItemName) {
		this.outItemName = outItemName;
	}

	public String getReqItemCode() {
		return reqItemCode;
	}

	public void setReqItemCode(String reqItemCode) {
		this.reqItemCode = reqItemCode;
	}

	public String getReqItemName() {
		return reqItemName;
	}

	public void setReqItemName(String reqItemName) {
		this.reqItemName = reqItemName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getOutRate() {
		return outRate;
	}

	public void setOutRate(String outRate) {
		this.outRate = outRate;
	}

	public String getInRate() {
		return inRate;
	}

	public void setInRate(String inRate) {
		this.inRate = inRate;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
