/*
 * Created on 2005-3-22
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.qp.entity;

import com.bestway.common.CommonUtils;

/**
 * 存放合同备案料件资料
 */
public class BcsQPContractImg implements java.io.Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 料件序号
	 */
	private Integer seqNum;
	/**
	 * 商品编码
	 */
	private String complexCode;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 规格型号
	 */
	private String spec;
	/**
	 * 计量单位
	 */
	private String unitCode;

	/**
	 * 企业申报单价
	 */
	private Double declarePrice;

	/**
	 * 备注
	 */
	private String note;
	/**
	 * 是否禁止
	 */
	private Boolean isForbid;

	/**
	 * 详细型号规格
	 */
	private String detailNote;

	/**
	 * 备案资料库备案序号
	 */
	private Integer credenceNo;

	/**
	 * 数量
	 */
	private Double amount;

	/**
	 * 损耗数量
	 */
	private Double wasteAmount;

	/**
	 * 总金额
	 */
	private Double totalPrice;

	/**
	 * 法定单位总量
	 */
	private Double legalTotalAmount;

	/**
	 * 法定单位数量
	 */
	private Double legalAmount;

	/**
	 * 第一法定比例因子
	 */
	private Double legalUnitGene;

	/**
	 * 第二法定单位数量
	 */
	private Double secondAmount;

	/**
	 * 第二法定比例因子
	 * 
	 */
	private Double legalUnit2Gene;

	/**
	 * 单位重量
	 */
	private Double unitWeight;

	/**
	 * 总重量
	 */
	private Double totalWeight;

	/**
	 * 是否主料
	 */
	private Boolean isMainImg = false;

	/**
	 * 原产地
	 */
	private String countryCode;

	/**
	 * 征减免税方式
	 */
	private String levyModeCode = null;

	/**
	 * 非保税料件比例
	 * 
	 */
	private Double dutyRate = null;

	/**
	 * 获取数量
	 * 
	 * @return amount 数量
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 设置数量
	 * 
	 * @param amount
	 *            数量
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * 获取凭证序号(归并序号)
	 * 
	 * @return credenceNo 凭证序号(归并序号)
	 */
	public Integer getCredenceNo() {
		return credenceNo;
	}

	/**
	 * 设置凭证序号(归并序号)
	 * 
	 * @param credenceNo
	 *            凭证序号(归并序号)
	 */
	public void setCredenceNo(Integer credenceNo) {
		this.credenceNo = credenceNo;
	}

	/**
	 * 获取法定单位数量
	 * 
	 * @return legalAmount 法定单位数量
	 */
	public Double getLegalAmount() {
		return legalAmount;
	}

	/**
	 * 设置法定单位数量
	 * 
	 * @param legalAmount
	 *            法定单位数量
	 */
	public void setLegalAmount(Double legalAmount) {
		this.legalAmount = legalAmount;
	}

	/**
	 * 获取法定单位总量
	 * 
	 * @return legalTotalAmount 法定单位总量
	 */
	public Double getLegalTotalAmount() {
		return legalTotalAmount;
	}

	/**
	 * 设置法定单位总量
	 * 
	 * @param legalTotalAmount
	 *            法定单位总量
	 */
	public void setLegalTotalAmount(Double legalTotalAmount) {
		this.legalTotalAmount = legalTotalAmount;
	}

	// /**
	// * 获取法定单位
	// *
	// * @return legalUnit 法定单位
	// */
	// public Unit getLegalUnit() {
	// return legalUnit;
	// }
	//
	// /**
	// * 设置法定单位
	// *
	// * @param legalUnit 法定单位
	// */
	// public void setLegalUnit(Unit legalUnit) {
	// this.legalUnit = legalUnit;
	// }

	/**
	 * 获取 总金额 = 单价 * 申报数量
	 * 
	 * @return totalPrice 总金额
	 */
	public Double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * 设置总金额
	 * 
	 * @param totalPrice
	 *            总金额
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * 获取总重量
	 * 
	 * @return totalWeight 总重量
	 */
	public Double getTotalWeight() {
		return totalWeight;
	}

	/**
	 * 设置总重量
	 * 
	 * @param totalWeight
	 *            总重量
	 */
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * 获取单位重量
	 * 
	 * @return unitWeight 单位重量
	 */
	public Double getUnitWeight() {
		return unitWeight;
	}

	/**
	 * 设置单位重量
	 * 
	 * @param unitWeight
	 *            单位重量
	 */
	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	/**
	 * 获取损耗数量
	 * 
	 * @return wasteAmount 损耗数量
	 */
	public Double getWasteAmount() {
		return wasteAmount;
	}

	/**
	 * 设置损耗数量
	 * 
	 * @param wasteAmount
	 *            损耗数量
	 */
	public void setWasteAmount(Double wasteAmount) {
		this.wasteAmount = wasteAmount;
	}

	/**
	 * 获取serialVersionUID
	 * 
	 * @return Returns the serialVersionUID.
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	// /**
	// * 获取合同状态，1：申请变更
	// *
	// * @return declareState 合同状态，1：申请变更
	// */
	// public String getDeclareState() {
	// return declareState;
	// }
	// /**
	// * 设置合同状态，1：申请变更
	// *
	// * @param declareState 合同状态，1：申请变更
	// */
	// public void setDeclareState(String declareState) {
	// this.declareState = declareState;
	// }
	/**
	 * 获取第二法定单位数量
	 * 
	 * @return secondAmount 第二法定单位数量
	 */
	public Double getSecondAmount() {
		return secondAmount;
	}

	/**
	 * 设置第二法定单位数量
	 * 
	 * @param secondAmount
	 *            第二法定单位数量
	 */
	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}

	public Boolean getIsMainImg() {
		return isMainImg;
	}

	public void setIsMainImg(Boolean isMainImg) {
		this.isMainImg = isMainImg;
	}

	public Double getLegalUnitGene() {
		return legalUnitGene;
	}

	public void setLegalUnitGene(Double legalUnitGene) {
		this.legalUnitGene = legalUnitGene;
	}

	public Double getLegalUnit2Gene() {
		return legalUnit2Gene;
	}

	public void setLegalUnit2Gene(Double legalUnit2Gene) {
		this.legalUnit2Gene = legalUnit2Gene;
	}

	public void setDutyRate(Double dutyRate) {
		this.dutyRate = dutyRate;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getComplexCode() {
		return complexCode;
	}

	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
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

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public Double getDeclarePrice() {
		return declarePrice;
	}

	public void setDeclarePrice(Double declarePrice) {
		this.declarePrice = declarePrice;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getIsForbid() {
		return isForbid;
	}

	public void setIsForbid(Boolean isForbid) {
		this.isForbid = isForbid;
	}

	public String getDetailNote() {
		return detailNote;
	}

	public void setDetailNote(String detailNote) {
		this.detailNote = detailNote;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getLevyModeCode() {
		return levyModeCode;
	}

	public void setLevyModeCode(String levyModeCode) {
		this.levyModeCode = levyModeCode;
	}

	public Double getDutyRate() {
		return dutyRate;
	}

}
