package com.bestway.owp.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
/**
 * 
 * @author hcl
 * 
 * check by hcl 2010-09-06
 *
 */
public class TempReturnToBom implements Serializable  {

	private static final long serialVersionUID = 3793453566871146022L;
	/**
	 * 外发序号
	 */
	private Integer listNo = null;
	/**
	 * 委托方手册/帐册编号
	 */
	private String emsNo;
	/**
	 * 单据号
	 */
	private String billDetailNo;
	/**
	 * 工厂料号
	 */
	private String ptNo;
	/**
	 * 工厂名称
	 */
	private String ptName;
	/**
	 * 工厂规格
	 */
	private String ptSpec;
	/**
	 * 工厂单位
	 */
	private String ptUnit;
	/**
	 * 商品编码
	 */
	private Complex complex;
	/**
	 * 商品名称
	 */
	private String hsName;
	
	/**
	 * 商品规格
	 */
	private String hsSpec;
	
	/**
	 * 计量单位
	 */
	private Unit hsUnit;
	/**
	 * 工厂数量
	 */
	private Double ptQty;
	/**
	 * 报关数量
	 */
	private Double hsQty;
	/**
	 * 单项用量
	 */
	private Double unitUsed;
	/**
	 * 物料与实际报关的折算系数
	 */
	private Double unitConvert;
	/**
	 * 单耗
	 */
	private Double unitWaste;  
	/**
	 * 单耗合计
	 */
	private Double unitWasteAmount;  
	/**
	 * 损耗
	 */
	private Double waste;
	/**
	 *商品项号 非空 
	 *(手册序号)
	 */
	private Integer trNo = null;

	/**
	 * 获取手册序号
	 */
	public Integer getTrNo() {
		return trNo;
	}
	/**
	 * 设置手册序号
	 */
	public void setTrNo(Integer trNo) {
		this.trNo = trNo;
	}
	
	/**
	 * 获取外发序号
	 */
	public Integer getListNo() {
		return listNo;
	}/**
	 * 设置外发序号
	 */
	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}
	/**
	 * 获取委托方手册/帐册编号
	 */
	public String getEmsNo() {
		return emsNo;
	}
	/**
	 * 设置委托方手册/帐册编号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	/**
	 * 获取商品编码
	 */
	public Complex getComplex() {
		return complex;
	}
	/**
	 * 设置商品编码
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}
	/**
	 * 获取归并系数
	 */
	public Double getUnitConvert() {
		return unitConvert;
	}
	/**
	 * 设置归并系数
	 */
	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}
	/**
	 * 获取商品名称
	 */
	public String getHsName() {
		return hsName;
	}
	/**
	 * 设置商品名称
	 */
	public void setHsName(String hsName) {
		this.hsName = hsName;
	}
	/**
	 * 获取商品规格
	 */
	public String getHsSpec() {
		return hsSpec;
	}
	/**
	 * 设置商品规格
	 */
	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}
	/**
	 * 获取计量单位
	 */
	public Unit getHsUnit() {
		return hsUnit;
	}
	/**
	 * 设置计量单位
	 */
	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}
	/**
	 * 获取单据号
	 */
	public String getBillDetailNo() {
		return billDetailNo;
	}
	/**
	 * 设置单据号
	 */
	public void setBillDetailNo(String billDetailNo) {
		this.billDetailNo = billDetailNo;
	}
	/**
	 * 获取工厂料号
	 */
	public String getPtNo() {
		return ptNo;
	}
	/**
	 * 设置工厂料号
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}
	/**
	 * 获取工厂名称
	 */
	public String getPtName() {
		return ptName;
	}
	/**
	 * 设置工厂名称
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	/**
	 * 获取工厂规格
	 */
	public String getPtSpec() {
		return ptSpec;
	}
	/**
	 * 设置工厂规格
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}
	/**
	 * 获取工厂单位
	 */
	public String getPtUnit() {
		return ptUnit;
	}
	/**
	 * 设置工厂单位
	 */
	public void setPtUnit(String ptUnit) {
		this.ptUnit = ptUnit;
	}
	/**
	 * 获取工厂数量
	 */
	public Double getPtQty() {
		return ptQty;
	}
	/**
	 * 设置工厂数量
	 */
	public void setPtQty(Double ptQty) {
		this.ptQty = ptQty;
	}
	/**
	 * 获取报关数量
	 */
	public Double getHsQty() {
		return hsQty;
	}
	/**
	 * 设置报关数量
	 */
	public void setHsQty(Double hsQty) {
		this.hsQty = hsQty;
	}
	/**
	 * 获取单项用量
	 */
	public Double getUnitUsed() {
		return unitUsed;
	}
	/**
	 * 设置单项用量
	 */
	public void setUnitUsed(Double unitUsed) {
		this.unitUsed = unitUsed;
	}
	/**
	 * 获取单耗
	 */
	public Double getUnitWaste() {
		return unitWaste;
	}
	/**
	 * 设置单耗
	 */
	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}
	/**
	 * 获取单耗合计
	 */
	public Double getUnitWasteAmount() {
		return unitWasteAmount;
	}
	/**
	 * 设置单耗合计
	 */
	public void setUnitWasteAmount(Double unitWasteAmount) {
		this.unitWasteAmount = unitWasteAmount;
	}
	/**
	 * 获取损耗
	 */
	public Double getWaste() {
		return waste;
	}
	/**
	 * 设置损耗
	 */
	public void setWaste(Double waste) {
		this.waste = waste;
	}
	
	
	
}
