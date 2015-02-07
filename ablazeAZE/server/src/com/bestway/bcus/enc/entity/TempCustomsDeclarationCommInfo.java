/*
 * Created on 2004-7-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.common.CommonUtils;

/**
 * @author Administrator
 * 
 */
public class TempCustomsDeclarationCommInfo implements Serializable {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 帐册序号
	 */
	private String emsSerialNo;

	/**
	 * 商品信息
	 */
	private Complex complex;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 商品规格
	 */
	private String spec;

	/**
	 * 计量单位
	 */
	private Unit unit;

	/**
	 * 法定单位
	 */
	private Unit legalUnit;

	/**
	 * 法定单位二
	 */
	private Unit legalUnit2;

	/**
	 * 版本，主要是成品，料件没有版本
	 */
	private Integer version;
	/**
	 * 单价
	 */
	private Double price; 
	/**
	 * 凭证序号 BCS专用
	 */
	private Integer credenceNo;
	/**
	 * 详细型号规格
	 */
	private String detailNote;
	
	/**
	 * 法定单位数量
	 */
	private Double legalAmount;

	/**
	 * 第二法定单位数量
	 */
	private Double secondAmount;

	/**
	 * 工缴费(加工费总价)
	 */
	private Double workUsd;
	
	/**
	 * 用途
	 */
	private Uses uses;

	/**
	 * 减免方式
	 */
	private LevyMode levyMode;
	
	
	
	/**
	 * 获得版本，主要是成品，料件没有版本
	 * @return 版本，主要是成品，料件没有版本
	 */
	public Integer getVersion() {
		return version;
	}
	/**
	 * 设置版本，主要是成品，料件没有版本
	 * @param version 版本，主要是成品，料件没有版本
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * 获得凭证序号 BCS专用
	 * @return 凭证序号 BCS专用
	 */
	public Integer getCredenceNo() {
		return credenceNo;
	}
	/**
	 * 设置凭证序号 BCS专用
	 * @param credenceNo 凭证序号 BCS专用
	 */
	public void setCredenceNo(Integer credenceNo) {
		this.credenceNo = credenceNo;
	}
	/**
	 * 获得商品信息
	 * @return 商品信息
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * 设置商品信息
	 * @param complex 商品信息
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * 获得帐册序号
	 * @return 帐册序号
	 */
	public String getEmsSerialNo() {
		return emsSerialNo;
	}

	/**
	 * 设置帐册序号
	 * @param emsSerialNo 帐册序号
	 */
	public void setEmsSerialNo(String emsSerialNo) {
		this.emsSerialNo = emsSerialNo;
	}

	/**
	 * 取得法定单位
	 * @return 法定单位
	 */
	public Unit getLegalUnit() {
		return legalUnit;
	}

	/**
	 * 设置法定单位
	 * @param legalUnit 法定单位
	 */
	public void setLegalUnit(Unit legalUnit) {
		this.legalUnit = legalUnit;
	}

	/**
	 * 获得法定单位二
	 * @return 法定单位二
	 */
	public Unit getLegalUnit2() {
		return legalUnit2;
	}

	/**
	 * 设置法定单位二
	 * @param legalUnit2 法定单位二
	 */
	public void setLegalUnit2(Unit legalUnit2) {
		this.legalUnit2 = legalUnit2;
	}

	/**
	 * 获得计量单位
	 * @return 计量单位
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * 设置计量单位
	 * @param unit 计量单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	/**
	 * 获得加工费总价
	 * @return 加工费总价
	 */
	public Double getWorkUsd() {
		return workUsd;
	}
	/**
	 * 设置加工费总价
	 * @param unit 加工费总价
	 */
	public void setWorkUsd(Double workUsd) {
		this.workUsd = workUsd;
	}

	/**
	 * 获得商品规格
	 * @return 商品规格
	 */
	public String getSpec() {
		return spec;
	}

	/**
	 * 获得商品规格
	 * @param spec 商品规格
	 */            
	public void setSpec(String spec) {
		this.spec = spec;
	}

	/**
	 * 获得商品名称
	 * @return 商品名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置商品名称
	 * @param name 商品名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获得单价
	 * @return 单价
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * 设置单价
	 * @param price 单价
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	public LevyMode getLevyMode() {
		return levyMode;
	}
	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}
	public Uses getUses() {
		return uses;
	}
	public void setUses(Uses uses) {
		this.uses = uses;
	}
	public String getDetailNote() {
		return detailNote;
	}
	public void setDetailNote(String detailNote) {
		this.detailNote = detailNote;
	}
	public Double getLegalAmount() {
		return legalAmount;
	}
	public void setLegalAmount(Double legalAmount) {
		this.legalAmount = legalAmount;
	}
	public Double getSecondAmount() {
		return secondAmount;
	}
	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}
}