package com.bestway.common.erpbill.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseEmsImg;
/**
 * 存放定单料件资料
 */
public class CustomOrderImg extends BaseEmsImg {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	/**
	 * 定单表头
	 */
	private CustomOrder customOrder = null;
//	/**
//	 * 定单明细
//	 */
//	private CustomOrderDetail customOrderDetail;

	/**
	 * 备案资料库备案序号
	 */
	private Integer credenceNo = null;

	/**
	 * 数量
	 */
	private Double amount = null;

	/**
	 * 损耗数量
	 */
	private Double wasteAmount = null;
	/**
	 * 单价
	 */
	private Double unitPrice = null; 

	/**
	 * 总金额
	 */
	private Double totalPrice = null;

	/**
	 * 法定单位总量
	 */
	private Double legalTotalAmount = null;

	/**
	 * 法定单位数量
	 */
	private Double legalAmount = null;

	/**
	 * 第二法定单位数量
	 */
	private Double secondAmount = null;

	/**
	 * 单位重量
	 */
	private Double unitWeight = null;

	/**
	 * 总重量
	 */
	private Double totalWeight = null;

	/**
	 * 是否主料
	 */
	private Boolean isMainImg = false;

	/**
	 * 原产地
	 */
	private Country country = null;

	/**
	 * 废料是否报关
	 */
	private Boolean isApplyToCustoms = false;

	/**
	 * 征减免税方式
	 */
	private LevyMode levyMode = null;

    /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark = null;
	/**
	 * 币别
	 */
	private Curr curr = null;
	/**
	 * 已转厂数量
	 */
	private Double transNum = null;
	/**
	 * 已转合同数量
	 */
	private Double contractNum = null;
	/**
	 * 未转厂数量
	 */
	private Double notTransNum = null;
	/**
	 * 未转合同数量
	 */
	private Double notContractNum = null;
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Integer getCredenceNo() {
		return credenceNo;
	}

	public void setCredenceNo(Integer credenceNo) {
		this.credenceNo = credenceNo;
	}

//	public CustomOrder getCustomOrder() {
//		return customOrder;
//	}
//
//	public void setCustomOrder(CustomOrder customOrder) {
//		this.customOrder = customOrder;
//	}

	public Boolean getIsApplyToCustoms() {
		return isApplyToCustoms;
	}

	public void setIsApplyToCustoms(Boolean isApplyToCustoms) {
		this.isApplyToCustoms = isApplyToCustoms;
	}

	public Boolean getIsMainImg() {
		return isMainImg;
	}

	public void setIsMainImg(Boolean isMainImg) {
		this.isMainImg = isMainImg;
	}

	public Double getLegalAmount() {
		return legalAmount;
	}

	public void setLegalAmount(Double legalAmount) {
		this.legalAmount = legalAmount;
	}

	public Double getLegalTotalAmount() {
		return legalTotalAmount;
	}

	public void setLegalTotalAmount(Double legalTotalAmount) {
		this.legalTotalAmount = legalTotalAmount;
	}

	public LevyMode getLevyMode() {
		return levyMode;
	}

	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	public Double getSecondAmount() {
		return secondAmount;
	}

	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Double getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	public Double getWasteAmount() {
		return wasteAmount;
	}

	public void setWasteAmount(Double wasteAmount) {
		this.wasteAmount = wasteAmount;
	}


	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}


	public CustomOrder getCustomOrder() {
		return customOrder;
	}

	public void setCustomOrder(CustomOrder customOrder) {
		this.customOrder = customOrder;
	}

	public Double getContractNum() {
		return contractNum;
	}

	public void setContractNum(Double contractNum) {
		this.contractNum = contractNum;
	}

	public Double getNotContractNum() {
		return notContractNum;
	}

	public void setNotContractNum(Double notContractNum) {
		this.notContractNum = notContractNum;
	}

	public Double getNotTransNum() {
		return notTransNum;
	}

	public void setNotTransNum(Double notTransNum) {
		this.notTransNum = notTransNum;
	}

	public Double getTransNum() {
		return transNum;
	}

	public void setTransNum(Double transNum) {
		this.transNum = transNum;
	}

	// /**
	// * 合同状态
	// */
	// private String declareState;
}
