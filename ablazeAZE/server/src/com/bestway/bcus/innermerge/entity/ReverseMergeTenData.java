/*
 * Created on 2004-12-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 内部归并－－反向归并第二层数据资料
 * 
 * @author Administrator
 */
public class ReverseMergeTenData extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 反向归并四码资料
	 */
	private ReverseMergeFourData reverseMergeFourData; 

	/**
	 * 十位备案序号
	 */
	private Integer hsAfterTenMemoNo; 

	/**
	 * 归并后10位商品编码
	 */
	private Complex hsAfterComplex; 

	/**
	 * 归并后商品名称
	 */
	private String hsAfterMaterielTenName; 

	/**
	 * 归并后商品规格、型号
	 */
	private String hsAfterMaterielTenSpec; 

	/**
	 * 归并后备案单位
	 */
	private Unit hsAfterMemoUnit; 

	/**
	 * 归并后第一法定单位
	 */
	private Unit hsAfterLegalUnit; 

	/**
	 * 归并后第二法定单位
	 */
	private Unit hsAfterSecondLegalUnit; 

	/**
	 * 获取反向归并四码资料
	 * 
	 * @return reserseMergeFourData 反向归并四码资料
	 */
	public ReverseMergeFourData getReverseMergeFourData() {
		return reverseMergeFourData;
	}

	/**
	 * 设置反向归并四码资料
	 * 
	 * @param reserseMergeFourData 反向归并四码资料
	 */
	public void setReverseMergeFourData(
			ReverseMergeFourData reserseMergeFourData) {
		this.reverseMergeFourData = reserseMergeFourData;
	}

	/**
	 * 获取归并后10位商品编码
	 * 
	 * @return hsAfterComplex 归并后10位商品编码
	 */
	public Complex getHsAfterComplex() {
		return hsAfterComplex;
	}

	/**
	 * 设置归并后10位商品编码
	 * 
	 * @param hsAfterComplex 归并后10位商品编码
	 */
	public void setHsAfterComplex(Complex hsAfterComplex) {
		this.hsAfterComplex = hsAfterComplex;
	}

	/**
	 * 获取归并后第一法定单位
	 * 
	 * @return hsAfterLegalUnit 归并后第一法定单位
	 */
	public Unit getHsAfterLegalUnit() {
		return hsAfterLegalUnit;
	}

	/**
	 * 设置归并后第一法定单位
	 * 
	 * @param hsAfterLegalUnit 归并后第一法定单位
	 */
	public void setHsAfterLegalUnit(Unit hsAfterLegalUnit) {
		this.hsAfterLegalUnit = hsAfterLegalUnit;
	}

	/**
	 * 获取归并后商品名称
	 * 
	 * @return hsAfterMaterielTenName 归并后商品名称
	 */
	public String getHsAfterMaterielTenName() {
		return hsAfterMaterielTenName;
	}

	/**
	 * 设置归并后商品名称
	 * 
	 * @param hsAfterMaterielTenName 归并后商品名称
	 */
	public void setHsAfterMaterielTenName(String hsAfterMaterielTenName) {
		this.hsAfterMaterielTenName = hsAfterMaterielTenName;
	}

	/**
	 * 获取归并后商品规格、型号
	 * 
	 * @return hsAfterMaterielTenSpec 归并后商品规格、型号
	 */
	public String getHsAfterMaterielTenSpec() {
		return hsAfterMaterielTenSpec;
	}

	/**
	 * 设置归并后商品规格、型号
	 * 
	 * @param hsAfterMaterielTenSpec 归并后商品规格、型号
	 */
	public void setHsAfterMaterielTenSpec(String hsAfterMaterielTenSpec) {
		this.hsAfterMaterielTenSpec = hsAfterMaterielTenSpec;
	}

	/**
	 * 获取归并后备案单位
	 * 
	 * @return hsAfterMemoUnit 归并后备案单位
	 */
	public Unit getHsAfterMemoUnit() {
		return hsAfterMemoUnit;
	}

	/**
	 * 设置归并后备案单位
	 * 
	 * @param hsAfterMemoUnit 归并后备案单位
	 */
	public void setHsAfterMemoUnit(Unit hsAfterMemoUnit) {
		this.hsAfterMemoUnit = hsAfterMemoUnit;
	}

	/**
	 * 获取归并后第二法定单位
	 * 
	 * @return hsAfterSecondLegalUnit 归并后第二法定单位
	 */
	public Unit getHsAfterSecondLegalUnit() {
		return hsAfterSecondLegalUnit;
	}

	/**
	 * 设置归并后第二法定单位
	 * 
	 * @param hsAfterSecondLegalUnit 归并后第二法定单位
	 */
	public void setHsAfterSecondLegalUnit(Unit hsAfterSecondLegalUnit) {
		this.hsAfterSecondLegalUnit = hsAfterSecondLegalUnit;
	}

	/**
	 * 获取十位备案序号
	 * 
	 * @return hsAfterTenMemoNo 十位备案序号
	 */
	public Integer getHsAfterTenMemoNo() {
		return hsAfterTenMemoNo;
	}

	/**
	 * 设置十位备案序号
	 * 
	 * @param hsAfterTenMemoNo 十位备案序号
	 */
	public void setHsAfterTenMemoNo(Integer hsAfterTenMemoNo) {
		this.hsAfterTenMemoNo = hsAfterTenMemoNo;
	}
}
