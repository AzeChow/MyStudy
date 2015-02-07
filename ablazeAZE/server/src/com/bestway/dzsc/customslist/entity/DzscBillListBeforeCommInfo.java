package com.bestway.dzsc.customslist.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 报关清单归并前商品信息
 * 
 * @author Administrator Created on 2004-7-26
 */
public class DzscBillListBeforeCommInfo extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 流水序号
	 */
	private Integer no;

	/**
	 * 报关清单归并后商品信息
	 */
	private DzscBillListAfterCommInfo afterComInfo;

	// /**
	// * 手册序号
	// */
	// private Integer emsSerialNo;

	/**
	 * 物料信息
	 */
	private Materiel materiel;

	/**
	 * 成品版本号
	 */
	private Integer bomVersion;

	/**
	 * 企业申报单价
	 */
	private Double declaredPrice;

	/**
	 * 申报数量
	 */
	private Double declaredAmount;

	/**
	 * 法定数量
	 */
	private Double legalAmount;

	/**
	 * 总价
	 */
	private Double totalPrice;

	/**
	 * 第二法定数量
	 */
	private Double secondLegalAmount;

	/**
	 * 毛重
	 */
	private Double grossWeight;

	/**
	 * 净重
	 */
	private Double netWeight;

	/**
	 * 产销国
	 */
	private Country salesCountry;

	/**
	 * 用途代码
	 */
	private Uses usesCode;

	/**
	 * 币制
	 */
	private Curr currency;

	/**
	 * 征免方式
	 */
	private LevyMode levyMode;

	/**
	 * 自动创建
	 */
	private Boolean isAutoCreate = false;

	/**
	 * 备注
	 */
	private String memos;

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	/**
	 * 获取自动创建，true时为自动创建
	 * 
	 * @return isAutoCreate 自动创建，true时为自动创建
	 */
	public Boolean getIsAutoCreate() {
		return isAutoCreate;
	}

	/**
	 * 获取自动创建，true时为自动创建
	 * 
	 * @param isAutoCreate
	 *            自动创建，true时为自动创建
	 */
	public void setIsAutoCreate(Boolean isAutoCreate) {
		this.isAutoCreate = isAutoCreate;
	}

	/**
	 * 获取报关清单归并后商品信息
	 * 
	 * @return afterComInfo 报关清单归并后商品信息
	 */
	public DzscBillListAfterCommInfo getAfterComInfo() {
		return afterComInfo;
	}

	/**
	 * 设置报关清单归并后商品信息
	 * 
	 * @param afterComInfo
	 *            报关清单归并后商品信息
	 */
	public void setAfterComInfo(DzscBillListAfterCommInfo afterComInfo) {
		this.afterComInfo = afterComInfo;
	}

	/**
	 * 获取申报数量
	 * 
	 * @return declaredAmount 申报数量
	 */
	public Double getDeclaredAmount() {
		return declaredAmount;
	}

	/**
	 * 设置申报数量
	 * 
	 * @param declaredAmount
	 *            申报数量
	 */
	public void setDeclaredAmount(Double declaredAmount) {
		this.declaredAmount = declaredAmount;
	}

	/**
	 * 获取企业申报单价
	 * 
	 * @return declaredPrice 企业申报单价
	 */
	public Double getDeclaredPrice() {
		return declaredPrice;
	}

	/**
	 * 设置企业申报单价
	 * 
	 * @param declaredPrice
	 *            企业申报单价
	 */
	public void setDeclaredPrice(Double declaredPrice) {
		this.declaredPrice = declaredPrice;
	}

	/**
	 * 获取企业申报总价
	 * 
	 * @return declaredPrice 企业申报总价
	 */
	public Double getTotalPrice() {
		if (totalPrice != null && totalPrice != 0.0) {
			return totalPrice;
		} else {
			return (declaredPrice == null ? 0 : declaredPrice)
					* (declaredAmount == null ? 0 : declaredAmount);
		}
	}

	/**
	 * 企业申报总价
	 * 
	 * @param totalPrice
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * 获取毛重
	 * 
	 * @return grossWeight 毛重
	 */
	public Double getGrossWeight() {
		return grossWeight;
	}

	/**
	 * 设置毛重
	 * 
	 * @param grossWeight
	 *            毛重
	 */
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	/**
	 * 获取法定数量
	 * 
	 * @return legalAmount 法定数量
	 */
	public Double getLegalAmount() {
		return legalAmount;
	}

	/**
	 * 设置法定数量
	 * 
	 * @param legalAmount
	 *            法定数量
	 */
	public void setLegalAmount(Double legalAmount) {
		this.legalAmount = legalAmount;
	}

	/**
	 * 获取物料信息
	 * 
	 * @return materiel 物料信息
	 */
	public Materiel getMateriel() {
		return materiel;
	}

	/**
	 * 设置物料信息
	 * 
	 * @param materiel
	 *            物料信息
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public Integer getBomVersion() {
		return bomVersion;
	}

	public void setBomVersion(Integer bomVersion) {
		this.bomVersion = bomVersion;
	}

	/**
	 * 获取备注
	 * 
	 * @return memo 备注
	 */
	public String getMemos() {
		return memos;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemos(String memo) {
		this.memos = memo;
	}

	/**
	 * 获取净重
	 * 
	 * @return netWeight 净重
	 */
	public Double getNetWeight() {
		return netWeight;
	}

	/**
	 * 设置净重
	 * 
	 * @param netWeight
	 *            净重
	 */
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	/**
	 * 获取第二法定数量
	 * 
	 * @return secondLegalAmount 第二法定数量
	 */
	public Double getSecondLegalAmount() {
		return secondLegalAmount;
	}

	/**
	 * 设置第二法定数量
	 * 
	 * @param secondLegalAmount
	 *            第二法定数量
	 */
	public void setSecondLegalAmount(Double secondLegalAmount) {
		this.secondLegalAmount = secondLegalAmount;
	}

	/**
	 * 获取币制
	 * 
	 * @return currency 币制
	 */
	public Curr getCurrency() {
		return currency;
	}

	/**
	 * 设置币制
	 * 
	 * @param currency
	 *            币制
	 */
	public void setCurrency(Curr currency) {
		this.currency = currency;
	}

	/**
	 * 获取征免方式
	 * 
	 * @return levyMode 征免方式
	 */
	public LevyMode getLevyMode() {
		return levyMode;
	}

	/**
	 * 设置征免方式
	 * 
	 * @param levyMode
	 *            征免方式
	 */
	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

	/**
	 * 获取产销国
	 * 
	 * @return salesCountry 产销国
	 */
	public Country getSalesCountry() {
		return salesCountry;
	}

	/**
	 * 设置产销国
	 * 
	 * @param salesCountry
	 *            产销国
	 */
	public void setSalesCountry(Country salesCountry) {
		this.salesCountry = salesCountry;
	}

	/**
	 * 获取用途代码
	 * 
	 * @return usesCode 用途代码
	 */
	public Uses getUsesCode() {
		return usesCode;
	}

	/**
	 * 设置用途代码
	 * 
	 * @param usesCode
	 *            用途代码
	 */
	public void setUsesCode(Uses usesCode) {
		this.usesCode = usesCode;
	}
}