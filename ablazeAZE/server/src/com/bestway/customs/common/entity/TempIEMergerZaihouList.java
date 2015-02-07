package com.bestway.customs.common.entity;

import com.bestway.bcus.custombase.entity.parametercode.Wrap;

/**
 * 进出口报关单打印载货清单
 * 临时表
 * @author Administrator
 * 
 */
public class TempIEMergerZaihouList implements java.io.Serializable {
	/**
	 * 关封号
	 */
	private String complexCode;
	/**
	 * 商品名称
	 */
	private String commName;

	/**
	 * 包装种类
	 */
	private String wrapTypeName;
	/**
	 * 总价
	 */
	private Double commTotalPrice;

	/**
	 * 净重
	 */
	private Double netWeight;
	/**
	 * 毛重
	 */
	private Double grossWeight; 
	/**
	 * 件数
	 */
	private String pieces;
	/**
	 * 客户名称
	 */
	private String cusName;

	public String getComplexCode() {
		return complexCode;
	}

	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getWrapTypeName() {
		return wrapTypeName;
	}

	public void setWrapTypeName(String wrapTypeName) {
		this.wrapTypeName = wrapTypeName;
	}

	public Double getCommTotalPrice() {
		return commTotalPrice;
	}

	public void setCommTotalPrice(Double commTotalPrice) {
		this.commTotalPrice = commTotalPrice;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public String getPieces() {
		return pieces;
	}

	public void setPieces(String pieces) {
		this.pieces = pieces;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public Double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}
}
