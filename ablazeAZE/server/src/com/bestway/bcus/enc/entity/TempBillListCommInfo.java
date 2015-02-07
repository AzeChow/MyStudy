/*
 * Created on 2004-7-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * @author Administrator
 *
 */
public class TempBillListCommInfo implements Serializable {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 是否备案
	 */
	private Boolean isPutOnRecord;
	/**
	 * 帐册序号
	 */
	private String emsSerialNo;
	/**
	 * 物料信息
	 */
	private Materiel materiel;
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
	 * 物料类别，如：材料，成品等
	 */
	private String materielType;
	
	
	/**
	 * 取得商品信息
	 * @return 商品信息
	 */
	public Complex getComplex() {
		return complex;
	}
	/**
	 * 设计商品信息
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
	 * 判断是否备案
	 * @return 是否备案
	 */
	public Boolean getIsPutOnRecord() {
		return isPutOnRecord;
	}
	/**
	 * 设置是否备案标志
	 * @param isPutOnRecord 是否备案
	 */
	public void setIsPutOnRecord(Boolean isPutOnRecord) {
		this.isPutOnRecord = isPutOnRecord;
	}
	/**
	 * 获得法定单位
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
	 * 获得物料信息
	 * @return 物料信息
	 */
	public Materiel getMateriel() {
		return materiel;
	}
	/**
	 * 设计物料信息
	 * @param materiel 物料信息
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}
	/**
	 * 获得物料类别，如：材料，成品等
	 * @return  物料类别，如：材料，成品等
	 */
	public String getMaterielType() {
		return materielType;
	}
	/**
	 * 设置物料类别，如：材料，成品等
	 * @param materielType  物料类别，如：材料，成品等
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
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
	 * 获得商品规格
	 * @return 商品规格
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * 设置商品规格
	 * @param spec 商品规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
}
