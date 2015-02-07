package com.bestway.owp.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
/**
 * 
 * 外发加工库存汇总表（中间类）
 * @author hcl
 *
 *	check by hcl 2010-09-06
 */
public class TempOwpStockAll implements Serializable  {

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
	 * 累计发货工厂数量数量
	 */
	private Double ptAmount;
	/**
	 * 商品编码
	 */
	private Complex complex;
	/**
	 * 累计发货数量
	 */
	private Double unitConvert;
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
	 * 累计发货数量
	 */
	private Double outAllAmount;
	/**
	 * 累计退回数量
	 */
	private Double returnAllAmount;
	/**
	 * 总发货数量（累计发货数量-累计退回数量）
	 */
	private Double allAmount;
	/**
	 * 返回折料数量
	 */
	private Double returnBomAmount;
	/**
	 * 返回残次品折料数量
	 */
	private Double returnBadBomAmount;
	/**
	 * 应返回边角料数量
	 */
	private Double yinRemianAmount;
	/**
	 *  实际返回边角料数量
	 */
	private Double sjRemianAmount;
	/**
	 * 外发加工库存
	 */
	private Double owpStockAmount;
	/**
	 * 外发加工边角料库存
	 */
	private Double owpRemianAmout;
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
	 * 获取累计发货数量
	 */
	public Double getOutAllAmount() {
		return outAllAmount;
	}
	/**
	 * 设置累计发货数量
	 */
	public void setOutAllAmount(Double outAllAmount) {
		this.outAllAmount = outAllAmount;
	}
	/**
	 * 获取累计退回数量
	 */
	public Double getReturnAllAmount() {
		return returnAllAmount;
	}
	/**
	 *设置 累计退回数量
	 */
	public void setReturnAllAmount(Double returnAllAmount) {
		this.returnAllAmount = returnAllAmount;
	}
	/**
	 * 获取总发货数量（累计发货数量-累计退回数量）
	 */
	public Double getAllAmount() {
		return allAmount;
	}
	/**
	 * 设置总发货数量（累计发货数量-累计退回数量）
	 */
	public void setAllAmount(Double allAmount) {
		this.allAmount = allAmount;
	}
	/**
	 * 获取返回折料数量
	 */
	public Double getReturnBomAmount() {
		return returnBomAmount;
	}
	/**
	 * 设置返回折料数量
	 */
	public void setReturnBomAmount(Double returnBomAmount) {
		this.returnBomAmount = returnBomAmount;
	}/**
	 * 获取返回残次品折料数量
	 */
	public Double getReturnBadBomAmount() {
		return returnBadBomAmount;
	}
	/**
	 * 设置返回残次品折料数量
	 */
	public void setReturnBadBomAmount(Double returnBadBomAmount) {
		this.returnBadBomAmount = returnBadBomAmount;
	}
	/**
	 * 获取应返回边角料数量
	 */
	public Double getYinRemianAmount() {
		return yinRemianAmount;
	}
	/**
	 * 设置应返回边角料数量
	 */
	public void setYinRemianAmount(Double yinRemianAmount) {
		this.yinRemianAmount = yinRemianAmount;
	}
	
	/**
	 * 获取 实际返回边角料数量
	 */
	public Double getSjRemianAmount() {
		return sjRemianAmount;
	}

	/**
	 *  设置实际返回边角料数量
	 */
	public void setSjRemianAmount(Double sjRemianAmount) {
		this.sjRemianAmount = sjRemianAmount;
	}
	/**
	 * 获取外发加工库存
	 */
	public Double getOwpStockAmount() {
		return owpStockAmount;
	}
	
	
	/**
	 * 设置外发加工库存
	 */
	public void setOwpStockAmount(Double owpStockAmount) {
		this.owpStockAmount = owpStockAmount;
	}/**
	 * 获取外发加工边角料库存
	 */
	public Double getOwpRemianAmout() {
		return owpRemianAmout;
	}/**
	 * 设置外发加工边角料库存
	 */
	public void setOwpRemianAmout(Double owpRemianAmout) {
		this.owpRemianAmout = owpRemianAmout;
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
	public Double getPtAmount() {
		return ptAmount;
	}
	/**
	 * 设置工厂数量
	 */
	public void setPtAmount(Double ptAmount) {
		this.ptAmount = ptAmount;
	}
	
	
}
