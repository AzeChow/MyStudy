/*
 * Created on 2004-8-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;

/**
 * 报关清单和报关单内容
 * @author Administrator
 *
 */
public class DiffrenceAnalyseCommInfo  implements Serializable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 商品序号
	 */
	private Integer commSerialNo;

	/**
	 * 商品信息
	 */
	private Complex complex;

	/**
	 * 商品名称
	 */
	private String commName;

	/**
	 * 商品规格
	 */
	private String commSpec;
	
	/**
	 * 单位
	 */
	private Unit unit;
	
	/**
	 * 报关清单归并后数量
	 */
	private Double afterMergeAmount;
	
	/**
	 * 报关单数量
	 */
	private Double customsDelarationAmount;
	
	/**
	 * 差异数量
	 */
	private Double diffrenceAmount;
	/**
	 * 取得报关清单归并后数量
	 * @return 报关清单归并后数量.
	 */
	public Double getAfterMergeAmount() {
		return afterMergeAmount;
	}
	/**
	 * 设置报关清单归并后数量
	 * @param afterMergeAmount 报关清单归并后数量
	 */
	public void setAfterMergeAmount(Double afterMergeAmount) {
		this.afterMergeAmount = afterMergeAmount;
	}
	/**
	 * 取得商品名称
	 * @return 商品名称
	 */
	public String getCommName() {
		return commName;
	}
	/**
	 * 设置商品名称
	 * @param commName 商品名称
	 */
	public void setCommName(String commName) {
		this.commName = commName;
	}
	/**
	 * 取得商品序号
	 * @return 商品序号
	 */
	public Integer getCommSerialNo() {
		return commSerialNo;
	}
	/**
	 * 设置商品序号
	 * @param commSerialNo 商品序号
	 */
	public void setCommSerialNo(Integer commSerialNo) {
		this.commSerialNo = commSerialNo;
	}
	/**
	 * 取得商品规格
	 * @return 商品规格
	 */
	public String getCommSpec() {
		return commSpec;
	}
	/**
	 * 设置商品规格
	 * @param commSpec 商品规格
	 */
	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}
	/**
	 * 取得商品信息
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
	 * 获得报关单数量
	 * @return 报关单数量
	 */
	public Double getCustomsDelarationAmount() {
		return customsDelarationAmount;
	}
	/**
	 * 设置报关单数量
	 * @param customsDelarationAmount 报关单数量
	 */
	public void setCustomsDelarationAmount(Double customsDelarationAmount) {
		this.customsDelarationAmount = customsDelarationAmount;
	}
	/**
	 * 获得差异数量
	 * @return 差异数量
	 */
	public Double getDiffrenceAmount() {
		return diffrenceAmount;
	}
	/**
	 * 设置差异数量
	 * @param diffrenceAmount 差异数量
	 */
	public void setDiffrenceAmount(Double diffrenceAmount) {
		this.diffrenceAmount = diffrenceAmount;
	}
	/**
	 * 获得单位
	 * @return 单位
	 */
	public Unit getUnit() {
		return unit;
	}
	/**
	 * 设置单位
	 * @param unit 单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
}
