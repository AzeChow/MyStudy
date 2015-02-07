/*
 * Created on 2004-7-17
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.bcsinnermerge.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放从文本导入的内部归并资料
 * 
 * @author Administrator
 */
public class BcsInnerMergeDataOrder extends BaseScmEntity {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 序号
	 */
	private Integer serialNo;

	/**
	 * 10位商品编码
	 */
	private Integer beforeTenComplexCode;

	/**
	 * 料号
	 */
	private Integer beforeMaterielCode;

	/**
	 * 归并前料件名称
	 */
	private Integer beforeMaterielName;
	/**
	 * 归并前料件名称规格
	 */
	private Integer beforeMaterielNameSpec;
	/**
	 * 归并前料件规格
	 */
	private Integer beforeMaterielSpec;

	/**
	 * 企业
	 */
	private Integer beforeEnterpriseUnit;

	/**
	 * 单位
	 */
	private Integer beforeUnit;

	/**
	 * 备案序号
	 */
	private Integer afterTenMemoNo;

	/**
	 * 10位商品编码
	 */
	private Integer afterTenComplexCode;
	
	/**
	 * 商品名称规格
	 */
	private Integer afterComplexlNameSpec;

	/**
	 * 商品名称
	 */
	private Integer afterComplexlName;

	/**
	 * 商品编码
	 */
	private Integer afterComplexSpec;

	/**
	 * 单位
	 */
	private Integer afterUnit;

	/**
	 * 归并前和归并后单位换算率
	 */
	private Integer unitConvert;

	/**
	 * 单重
	 */
	private Integer unitWeight;

	
	/**
	 * @return Returns the afterTenMaterielCode.
	 */
	public Integer getAfterTenComplexCode() {
		return afterTenComplexCode;
	}

	/**
	 * @param afterTenMaterielCode
	 *            The afterTenMaterielCode to set.
	 */
	public void setAfterTenComplexCode(Integer afterTenMaterielCode) {
		this.afterTenComplexCode = afterTenMaterielCode;
	}

	/**
	 * @return Returns the beforeTenNo.
	 */
	public Integer getBeforeTenComplexCode() {
		return beforeTenComplexCode;
	}

	/**
	 * @param beforeTenNo
	 *            The beforeTenNo to set.
	 */
	public void setBeforeTenComplexCode(Integer beforeTenNo) {
		this.beforeTenComplexCode = beforeTenNo;
	}

	// /**
	// * @return Returns the afterLegalUnit.
	// */
	// public Integer getAfterLegalUnit() {
	// return afterLegalUnit;
	// }
	//
	// /**
	// * @param afterLegalUnit
	// * The afterLegalUnit to set.
	// */
	// public void setAfterLegalUnit(Integer afterLegalUnit) {
	// this.afterLegalUnit = afterLegalUnit;
	// }

	/**
	 * @return Returns the afterMaterielName.
	 */
	public Integer getAfterComplexlName() {
		return afterComplexlName;
	}

	/**
	 * @param afterMaterielName
	 *            The afterMaterielName to set.
	 */
	public void setAfterComplexlName(Integer afterMaterielName) {
		this.afterComplexlName = afterMaterielName;
	}

	/**
	 * @return Returns the afterMaterielSpec.
	 */
	public Integer getAfterComplexSpec() {
		return afterComplexSpec;
	}

	/**
	 * @param afterMaterielSpec
	 *            The afterMaterielSpec to set.
	 */
	public void setAfterComplexSpec(Integer afterMaterielSpec) {
		this.afterComplexSpec = afterMaterielSpec;
	}

	// /**
	// * @return Returns the afterMemoUnit.
	// */
	// public Integer getAfterMemoUnit() {
	// return afterMemoUnit;
	// }
	//
	// /**
	// * @param afterMemoUnit
	// * The afterMemoUnit to set.
	// */
	// public void setAfterMemoUnit(Integer afterMemoUnit) {
	// this.afterMemoUnit = afterMemoUnit;
	// }

	/**
	 * @return Returns the afterTenMemoNo.
	 */
	public Integer getAfterTenMemoNo() {
		return afterTenMemoNo;
	}

	/**
	 * @param afterTenMemoNo
	 *            The afterTenMemoNo to set.
	 */
	public void setAfterTenMemoNo(Integer afterTenMemoNo) {
		this.afterTenMemoNo = afterTenMemoNo;
	}

	/**
	 * @return Returns the beforeEnterpriseUnit.
	 */
	public Integer getBeforeEnterpriseUnit() {
		return beforeEnterpriseUnit;
	}

	/**
	 * @param beforeEnterpriseUnit
	 *            The beforeEnterpriseUnit to set.
	 */
	public void setBeforeEnterpriseUnit(Integer beforeEnterpriseUnit) {
		this.beforeEnterpriseUnit = beforeEnterpriseUnit;
	}

	// /**
	// * @return Returns the beforeLegalUnit.
	// */
	// public Integer getBeforeLegalUnit() {
	// return beforeLegalUnit;
	// }
	// /**
	// * @param beforeLegalUnit The beforeLegalUnit to set.
	// */
	// public void setBeforeLegalUnit(Integer beforeLegalUnit) {
	// this.beforeLegalUnit = beforeLegalUnit;
	// }
	/**
	 * @return Returns the beforeMaterielCode.
	 */
	public Integer getBeforeMaterielCode() {
		return beforeMaterielCode;
	}

	/**
	 * @param beforeMaterielCode
	 *            The beforeMaterielCode to set.
	 */
	public void setBeforeMaterielCode(Integer beforeMaterielCode) {
		this.beforeMaterielCode = beforeMaterielCode;
	}

	/**
	 * @return Returns the beforeMaterielName.
	 */
	public Integer getBeforeMaterielName() {
		return beforeMaterielName;
	}

	/**
	 * @param beforeMaterielName
	 *            The beforeMaterielName to set.
	 */
	public void setBeforeMaterielName(Integer beforeMaterielName) {
		this.beforeMaterielName = beforeMaterielName;
	}

	/**
	 * @return Returns the beforeMaterielSpec.
	 */
	public Integer getBeforeMaterielSpec() {
		return beforeMaterielSpec;
	}

	/**
	 * @param beforeMaterielSpec
	 *            The beforeMaterielSpec to set.
	 */
	public void setBeforeMaterielSpec(Integer beforeMaterielSpec) {
		this.beforeMaterielSpec = beforeMaterielSpec;
	}

	/**
	 * @return Returns the serialNo.
	 */
	public Integer getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            The serialNo to set.
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getBeforeUnit() {
		return beforeUnit;
	}

	public void setBeforeUnit(Integer beforeUnit) {
		this.beforeUnit = beforeUnit;
	}

	public Integer getAfterUnit() {
		return afterUnit;
	}

	public void setAfterUnit(Integer afterUnit) {
		this.afterUnit = afterUnit;
	}

	public Integer getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Integer convertRate) {
		this.unitConvert = convertRate;
	}

	public Integer getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(Integer unitWeight) {
		this.unitWeight = unitWeight;
	}

	public Integer getAfterComplexlNameSpec() {
		return afterComplexlNameSpec;
	}

	public void setAfterComplexlNameSpec(Integer afterComplexlNameSpec) {
		this.afterComplexlNameSpec = afterComplexlNameSpec;
	}

	public Integer getBeforeMaterielNameSpec() {
		return beforeMaterielNameSpec;
	}

	public void setBeforeMaterielNameSpec(Integer beforeMaterielNameSpec) {
		this.beforeMaterielNameSpec = beforeMaterielNameSpec;
	}
}
