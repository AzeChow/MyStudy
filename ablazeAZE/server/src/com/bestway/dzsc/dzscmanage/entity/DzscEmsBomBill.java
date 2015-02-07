/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.dzscmanage.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscCustomsModifyState;

/**
 * 存放手册通关备案的BOM资料
 * 
 * @author yp
 */
public class DzscEmsBomBill extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 成品
	 */
	private DzscEmsExgBill dzscEmsExgBill = null;

	/**
	 * 商品编码
	 */
	private Complex complex = null;

	/**
	 * 料件名称
	 */
	private String name = null;

	/**
	 * 型号规格
	 */
	private String spec = null;

	/**
	 * 单耗
	 */
	private Double unitWare = null;

	/**
	 * 损耗
	 */
	private Double ware = null;

	/**
	 * 单项用量
	 */
	private Double unitDosage = null;

	/**
	 * 单价
	 */
	private Double price = null;

	/**
	 * 数量
	 */
	private Double amount = null;

	/**
	 * 金额
	 */
	private Double money = null;

	/**
	 * 单位
	 */
	private Unit unit = null;

	/**
	 * 单位重量
	 */
	private Double unitWeight = null;

	/**
	 * 是否主辅料
	 */
	private String isMainImg = null;

	/**
	 * 原产地
	 */
	private Country country = null;

	/**
	 * 料件总表序号
	 */
	private Integer imgSeqNum = null;
	/**
	 * 非保税料件比例%
	 */
	private Double nonMnlRatio;
	/**
	 * 获取非保税料件比例%
	 */
	public Double getNonMnlRatio() {
		return nonMnlRatio;
	}
	/**
	 * 设置非保税料件比例%
	 */
	public void setNonMnlRatio(Double nonMnlRatio) {
		this.nonMnlRatio = nonMnlRatio;
	}
	/**
	 * 修改标志
	 */
	private String modifyMark;

	/**
	 * 获取serialVersionUID
	 * 
	 * @return Returns serialVersionUID.
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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
	 * 获取商品编码
	 * 
	 * @return complex 商品编码
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * 设置商品编码
	 * 
	 * @param complex
	 *            商品编码
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * 获取原产地
	 * 
	 * @return country 原产地
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 设置原产地
	 * 
	 * @param country
	 *            原产地
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * 获取是否主辅料
	 * 
	 * @return isMainImg 是否主辅料
	 */
	public String getIsMainImg() {
		return isMainImg;
	}

	/**
	 * 设置是否主辅料
	 * 
	 * @param isMainImg
	 *            是否主辅料
	 */
	public void setIsMainImg(String isMainImg) {
		this.isMainImg = isMainImg;
	}

	/**
	 * 获取金额
	 * 
	 * @return money 金额
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * 设置金额
	 * 
	 * @param money
	 *            金额
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	/**
	 * 获取料件名称
	 * 
	 * @return name 料件名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置料件名称
	 * 
	 * @param name
	 *            料件名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取单价
	 * 
	 * @return price 单价
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * 设置单价
	 * 
	 * @param price
	 *            单价
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * 获取型号规格
	 * 
	 * @return spec 型号规格
	 */
	public String getSpec() {
		return spec;
	}

	/**
	 * 设置型号规格
	 * 
	 * @param spec
	 *            型号规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}

	/**
	 * 获取单位
	 * 
	 * @return unit 单位
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit
	 *            单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * 获取单项用量
	 * 
	 * @return unitDosage 单项用量
	 */
	public Double getUnitDosage() {
		return unitDosage;
	}

	/**
	 * 设置单项用量
	 * 
	 * @param unitDosage
	 *            单项用量
	 */
	public void setUnitDosage(Double unitDosage) {
		this.unitDosage = unitDosage;
	}

	/**
	 * 获取单耗
	 * 
	 * @return unitWare 单耗
	 */
	public Double getUnitWare() {
		return unitWare;
	}

	/**
	 * 设置单耗
	 * 
	 * @param unitWare
	 *            单耗
	 */
	public void setUnitWare(Double unitWare) {
		this.unitWare = unitWare;
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
	 * 获取损耗
	 * 
	 * @return ware 损耗
	 */
	public Double getWare() {
		return ware;
	}

	/**
	 * 设置损耗
	 * 
	 * @param ware
	 *            损耗
	 */
	public void setWare(Double ware) {
		this.ware = ware;
	}

	/**
	 * 获取成品
	 * 
	 * @return dzbaEmsExgBill 成品
	 */
	public DzscEmsExgBill getDzscEmsExgBill() {
		return dzscEmsExgBill;
	}

	/**
	 * 设置成品
	 * 
	 * @param dzbaEmsExgBill
	 *            成品
	 */
	public void setDzscEmsExgBill(DzscEmsExgBill dzbaEmsExgBill) {
		this.dzscEmsExgBill = dzbaEmsExgBill;
	}

	/**
	 * 获取料件总表序号
	 * 
	 * @return imgSeqNum 料件总表序号
	 */
	public Integer getImgSeqNum() {
		return imgSeqNum;
	}

	/**
	 * 设置料件总表序号
	 * 
	 * @param imgSeqNum
	 *            料件总表序号
	 */
	public void setImgSeqNum(Integer imgSeqNum) {
		this.imgSeqNum = imgSeqNum;
	}

	/**
	 * 获取修改标志
	 * 
	 * @return modifyMark 修改标志
	 */
	public String getModifyMark() {
		return modifyMark;
	}

	/**
	 * 获取修改标志
	 * 
	 * @return modifyMark 修改标志
	 */
	public String getCustomsModifyMark() {
		return DzscCustomsModifyState.getCustomsModifyState(modifyMark);
	}

	/**
	 * 设置修改标志
	 * 
	 * @param modifyMark
	 *            修改标志
	 */
	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	public Integer getBomVersion() {
		return 0;
	}
}