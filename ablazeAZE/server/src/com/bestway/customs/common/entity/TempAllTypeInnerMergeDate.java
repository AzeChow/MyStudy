package com.bestway.customs.common.entity;

import java.io.Serializable;

import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;

/**
 * @author fhz
 *
 *	用来存放临时的内部归并资料，可以不同的模块
 *
 */
public class TempAllTypeInnerMergeDate implements Serializable {
	
	
	//BCS--------------------------------------------------------
	/**
	 * 十码归并资料
	 */
	private BcsTenInnerMerge bcsTenInnerMerge;
	
	//BCUS--------------------------------------------------------
	/**
	 * 归并后10位商品编码
	 */
	private Complex	hsAfterComplex;
	
	/**
	 * 归并序号
	 */
	private Integer hsAfterTenMemoNo;
	
	
	/**
	 * 十位商品名称tenPtName
	 */
	private String hsAfterMaterielTenName;

	/**
	 * 十位商品规格
	 */
	private String hsAfterMaterielTenSpec;

	/**
	 * 十位商品单位
	 */
	private Unit hsAfterLegalUnit;


	//DZSC--------------------------------------------------------
	/**
	 * 十码归并资料
	 */
	private DzscTenInnerMerge dzscTenInnerMerge;
	
		

	public BcsTenInnerMerge getBcsTenInnerMerge() {
		return bcsTenInnerMerge;
	}


	public void setBcsTenInnerMerge(BcsTenInnerMerge bcsTenInnerMerge) {
		this.bcsTenInnerMerge = bcsTenInnerMerge;
	}


	public DzscTenInnerMerge getDzscTenInnerMerge() {
		return dzscTenInnerMerge;
	}


	public void setDzscTenInnerMerge(DzscTenInnerMerge dzscTenInnerMerge) {
		this.dzscTenInnerMerge = dzscTenInnerMerge;
	}


	public Unit getHsAfterLegalUnit() {
		return hsAfterLegalUnit;
	}


	public void setHsAfterLegalUnit(Unit hsAfterLegalUnit) {
		this.hsAfterLegalUnit = hsAfterLegalUnit;
	}


	public String getHsAfterMaterielTenName() {
		return hsAfterMaterielTenName;
	}


	public void setHsAfterMaterielTenName(String hsAfterMaterielTenName) {
		this.hsAfterMaterielTenName = hsAfterMaterielTenName;
	}


	public String getHsAfterMaterielTenSpec() {
		return hsAfterMaterielTenSpec;
	}


	public void setHsAfterMaterielTenSpec(String hsAfterMaterielTenSpec) {
		this.hsAfterMaterielTenSpec = hsAfterMaterielTenSpec;
	}


	public Integer getHsAfterTenMemoNo() {
		return hsAfterTenMemoNo;
	}


	public void setHsAfterTenMemoNo(Integer hsAfterTenMemoNo) {
		this.hsAfterTenMemoNo = hsAfterTenMemoNo;
	}


	public Complex getHsAfterComplex() {
		return hsAfterComplex;
	}


	public void setHsAfterComplex(Complex hsAfterComplex) {
		this.hsAfterComplex = hsAfterComplex;
	}


}
