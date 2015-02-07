/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.innermerge.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放手册报关单耗的BOM明细资料
 * 
 * @author yp
 */
public class DzscCustomsBomDetail extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 成品
	 */
	private DzscCustomsBomExg dzscCustomsBomExg = null;

	/**
	 * 归并序号
	 */
	private Integer tenSeqNum = null;

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
	 * 单位
	 */
	private Unit unit = null;
	
	/**
	 * 报关助记码
	 */
	private String customsNo = null;

	/**
	 * 获取serialVersionUID
	 * 
	 * @return Returns serialVersionUID.
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
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
	public DzscCustomsBomExg getDzscCustomsBomExg() {
		return dzscCustomsBomExg;
	}

	/**
	 * 设置成品
	 * 
	 * @param dzbaEmsExgBill
	 *            成品
	 */
	public void setDzscCustomsBomExg(DzscCustomsBomExg dzscCustomsBomExg) {
		this.dzscCustomsBomExg = dzscCustomsBomExg;
	}

	/**
	 * 获取料件总表序号
	 * 
	 * @return imgSeqNum 料件总表序号
	 */
	public Integer getTenSeqNum() {
		return tenSeqNum;
	}

	/**
	 * 设置料件总表序号
	 * 
	 * @param imgSeqNum
	 *            料件总表序号
	 */
	public void setTenSeqNum(Integer imgSeqNum) {
		this.tenSeqNum = imgSeqNum;
	}

	public String getCustomsNo() {
		return customsNo;
	}

	public void setCustomsNo(String customsNo) {
		this.customsNo = customsNo;
	}

	// public Integer getBomVersion() {
	// return 0;
	// }
}