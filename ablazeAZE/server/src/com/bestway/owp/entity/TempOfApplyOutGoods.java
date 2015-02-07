package com.bestway.owp.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
/**
 * 外发加工申请表发货明细表（中间类）
 * @author hcl
 * check by hcl 2010-09-06
 *
 */
public class TempOfApplyOutGoods implements Serializable {

	/**
	 * 委托方手册/帐册编号
	 */
	private String appNo;
	/**
	 * 外发序号
	 */
	private Integer listNo = null;
	/**
	 * 委托方手册/帐册编号
	 */
	private String emsNo;
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
	 * 申报数量
	 */
	private Double qty;
	/**
	 * 累计发货数量
	 */
	private Double countQty=0.0;
	/**
	 * 可发货数量
	 */
	private Double canQty=0.0;
	/**
	 *商品项号 非空 
	 *(手册序号)
	 */
	private Integer trNo = null;
	
	
	/**
	 * 获取申请表编号
	 */
	public String getAppNo() {
		return appNo;
	}
	/**
	 * 设置申请表编号
	 */
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
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
	}
	/**
	 * 设置外发序号
	 */
	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}
	/**
	 * 获取账册编号
	 */
	
	public String getEmsNo() {
		return emsNo;
	}
	/**
	 * 设置账册编号
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
	 * 获取报关名称
	 */
	public String getHsName() {
		return hsName;
	}
	/**
	 * 设置报关名称
	 */
	public void setHsName(String hsName) {
		this.hsName = hsName;
	}
	/**
	 * 获取报关规格
	 */
	public String getHsSpec() {
		return hsSpec;
	}
	/**
	 * 设置报关规格
	 */
	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}
	/**
	 * 获取报关单位
	 */
	public Unit getHsUnit() {
		return hsUnit;
	}
	/**
	 * 设置报关单位
	 */
	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}
	/**
	 * 获取申报数量
	 */
	public Double getQty() {
		return qty;
	}
	/**
	 * 设置申报数量
	 */
	public void setQty(Double qty) {
		this.qty = qty;
	}
	/**
	 * 获取累计发货数量
	 */
	public Double getCountQty() {
		return countQty;
	}
	/**
	 * 设置累计发货数量
	 */
	public void setCountQty(Double countQty) {
		this.countQty = countQty;
	}
	/**
	 * 获取可发货数量
	 */
	public Double getCanQty() {
		return canQty;
	}
	/**
	 * 设置发货数量
	 */
	public void setCanQty(Double canQty) {
		this.canQty = canQty;
	}
	
}
