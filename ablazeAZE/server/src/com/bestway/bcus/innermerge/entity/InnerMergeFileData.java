/*
 * Created on 2004-7-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * 存放从文本导入的内部归并资料
 * 
 * @author Administrator
 */
public class InnerMergeFileData implements Serializable {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 序号
	 */
	public static final int SERIAL_NO = 1;

	/**
	 * 料号
	 */
	public static final int BEFORE_MATERIEL_CODE = 2;

	/**
	 * 10为商品编码
	 */
	public static final int BEFORE_TEN_COMPLEX_CODE = 3;

	/**
	 * 商品名称、型号、规格
	 */
	public static final int BEFORE_MATERIEL_NAME_SPEC = 4;

	/**
	 * 计量单位
	 */
	public static final int BEFORE_LEGAL_UNIT = 5;

	/**
	 * 计量单位
	 */
	public static final int BEFORE_ENTERPRISE_UNIT = 6;

	/**
	 * 备案序号
	 */
	public static final int AFTER_TEN_MEMO_NO = 7;

	/**
	 * 10位商品编码
	 */
	public static final int AFTER_TEN_COMPLEX_CODE = 8;

	/**
	 * 10位商品名称、规格、型号
	 */
	public static final int AFTER_COMPLEX_NAME_SPEC = 9;

	/**
	 * 10位计量单位
	 */
	public static final int AFTER_LEGAL_UNIT = 10;

	/**
	 * 10位计量单位
	 */
	public static final int AFTER_MEMO_UNIT = 11;

	/**
	 * 4位编码序号
	 */
	public static final int FOUR_NO = 12;

	/**
	 * 4位编码商品名称
	 */
	public static final int FOUR_COMPLEX_NAME = 13;

	/**
	 * 4位商品编码
	 */
	public static final int FOUR_COMPLEX_CODE = 14;

	public static final int Before_Name = 15;
	public static final int Before_Spec = 16;
	public static final int After_Name = 17;
	public static final int After_Spec = 18;

	public static final int UNIT_WEIGHT = 19; // 单重
	public static final int UNIT_CONVERT = 20;// 单位折算系数
	public static final int UNIT_PTPRICE = 21;// 单价

	/**
	 * 物料类别
	 */
	private String materielType;

	/**
	 * 序号
	 */
	private String serialNo;

	/**
	 * 料号
	 */
	private String beforeMaterielCode;

	/**
	 * 10位商品编码
	 */
	private String beforeTenComplexCode ;

	/**
	 * 料件名称
	 */
	private String beforeMaterielName;

	/**
	 * 料件规格
	 */
	private String beforeMaterielSpec;

	/**
	 * 法定单位
	 */
	private String beforeLegalUnit;

	/**
	 * 企业
	 */
	private String beforeEnterpriseUnit;

	/**
	 * 备案序号
	 */
	private String afterTenMemoNo;

	/**
	 * 商品编码
	 */
	private String afterTenComplexCode;

	/**
	 * 商品名称
	 */
	private String afterComplexlName;

	/**
	 * 商品规格
	 */
	private String afterComplexSpec;

	/**
	 * 第一法定单位
	 */
	private String afterLegalUnit;
	/**
	 * 第二法定单位
	 */
	private String afterLegal2Unit;

	/**
	 * 申报计量单位
	 */
	private String afterMemoUnit;

	/**
	 * 4位编码序号
	 */
	private String fourNo;

	/**
	 * 4位商品名称
	 */
	private String fourComplexName;

	/**
	 * 4位商品编码
	 */
	private String fourComplexCode;

	/**
	 * 归并前商品名称，规格
	 */
	private String hsBeforeMaterielNameSpec; // 归并前商品名称，规格。

	/**
	 * 归并前商品名称，规格
	 */
	private String hsAfterComplexNameSpec; // 归并前商品名称，规格。

	/**
	 * 
	 */
	private int[] invalidationColNo;

	/**
	 * 错误原因
	 */
	private String errinfo = "";

	/**
	 * 单重
	 */
	private Double unitWeight;
	/**
	 * 单位折算系数
	 */
	private Double unitConvert;
	
	/**
	 * 单价
	 */
	private Double ptPrice;
	
	/**
	 * 第一法定单位比例因子
	 */
	private Double fristUnitRatio;
	/**
	 * 第二法定单位比例因子
	 */
	private Double secondUnitRatio;
	/**
	 * 重量单位比例因子
	 */
	private Double weigthUnitGene;
	/**
	 * 是否主料
	 */
	private Boolean isMainImg = false;

	/**
	 * @return Returns the hsBeforeMaterielNameSpec.
	 */
	public String getHsAfterComplexNameSpec() {
		hsAfterComplexNameSpec = "";
		if (this.getAfterComplexlName() != null
				&& !this.getAfterComplexlName().equals("")) {
			hsAfterComplexNameSpec += this.getAfterComplexlName() + "/";
		}
		if (this.getAfterComplexSpec() != null
				&& !this.getAfterComplexSpec().equals("")) {
			if (!hsAfterComplexNameSpec.equals("")) {
				hsAfterComplexNameSpec += this.getAfterComplexSpec();
			} else {
				hsAfterComplexNameSpec += "/" + this.getAfterComplexSpec();
			}
		}
		return hsAfterComplexNameSpec;
	}

	/**
	 * @return Returns the hsBeforeMaterielNameSpec.
	 */
	public String getHsBeforeMaterielNameSpec() {
		hsBeforeMaterielNameSpec = "";
		if (this.getBeforeMaterielName() != null
				&& !this.getBeforeMaterielName().equals("")) {
			hsBeforeMaterielNameSpec += this.getBeforeMaterielName() + "/";
		}
		if (this.getBeforeMaterielSpec() != null
				&& !this.getBeforeMaterielSpec().equals("")) {
			if (!hsBeforeMaterielNameSpec.equals("")) {
				hsBeforeMaterielNameSpec += this.getBeforeMaterielSpec();
			} else {
				hsBeforeMaterielNameSpec += "/" + this.getBeforeMaterielSpec();
			}
		}
		return hsBeforeMaterielNameSpec;
	}

	/**
	 * @return Returns the invalidationColNo.
	 */
	public int[] getInvalidationColNo() {
		return invalidationColNo;
	}

	/**
	 * @param invalidationColNo
	 *            The invalidationColNo to set.
	 */
	public void setInvalidationColNo(int[] invalidationColNo) {
		this.invalidationColNo = invalidationColNo;
	}

	/**
	 * @return Returns the afterTenMaterielCode.
	 */
	public String getAfterTenComplexCode() {
		return afterTenComplexCode;
	}

	/**
	 * @param afterTenMaterielCode
	 *            The afterTenMaterielCode to set.
	 */
	public void setAfterTenComplexCode(String afterTenMaterielCode) {
		this.afterTenComplexCode = afterTenMaterielCode;
	}

	/**
	 * @return Returns the beforeTenNo.
	 */
	public String getBeforeTenComplexCode() {
		return beforeTenComplexCode;
	}

	/**
	 * @param beforeTenNo
	 *            The beforeTenNo to set.
	 */
	public void setBeforeTenComplexCode(String beforeTenNo) {
		this.beforeTenComplexCode = beforeTenNo;
	}

	/**
	 * @return Returns the afterLegalUnit.
	 */
	public String getAfterLegalUnit() {
		return afterLegalUnit;
	}

	/**
	 * @param afterLegalUnit
	 *            The afterLegalUnit to set.
	 */
	public void setAfterLegalUnit(String afterLegalUnit) {
		this.afterLegalUnit = afterLegalUnit;
	}

	/**
	 * @return Returns the afterMaterielName.
	 */
	public String getAfterComplexlName() {
		return afterComplexlName;
	}

	/**
	 * @param afterMaterielName
	 *            The afterMaterielName to set.
	 */
	public void setAfterComplexlName(String afterMaterielName) {
		this.afterComplexlName = afterMaterielName;
	}

	/**
	 * @return Returns the afterMaterielSpec.
	 */
	public String getAfterComplexSpec() {
		return afterComplexSpec;
	}

	/**
	 * @param afterMaterielSpec
	 *            The afterMaterielSpec to set.
	 */
	public void setAfterComplexSpec(String afterMaterielSpec) {
		this.afterComplexSpec = afterMaterielSpec;
	}

	/**
	 * @return Returns the afterMemoUnit.
	 */
	public String getAfterMemoUnit() {
		return afterMemoUnit;
	}

	/**
	 * @param afterMemoUnit
	 *            The afterMemoUnit to set.
	 */
	public void setAfterMemoUnit(String afterMemoUnit) {
		this.afterMemoUnit = afterMemoUnit;
	}

	/**
	 * @return Returns the afterTenMemoNo.
	 */
	public String getAfterTenMemoNo() {
		return afterTenMemoNo;
	}

	/**
	 * @param afterTenMemoNo
	 *            The afterTenMemoNo to set.
	 */
	public void setAfterTenMemoNo(String afterTenMemoNo) {
		this.afterTenMemoNo = afterTenMemoNo;
	}

	/**
	 * @return Returns the beforeEnterpriseUnit.
	 */
	public String getBeforeEnterpriseUnit() {
		return beforeEnterpriseUnit;
	}

	/**
	 * @param beforeEnterpriseUnit
	 *            The beforeEnterpriseUnit to set.
	 */
	public void setBeforeEnterpriseUnit(String beforeEnterpriseUnit) {
		this.beforeEnterpriseUnit = beforeEnterpriseUnit;
	}

	/**
	 * @return Returns the beforeLegalUnit.
	 */
	public String getBeforeLegalUnit() {
		return beforeLegalUnit;
	}

	/**
	 * @param beforeLegalUnit
	 *            The beforeLegalUnit to set.
	 */
	public void setBeforeLegalUnit(String beforeLegalUnit) {
		this.beforeLegalUnit = beforeLegalUnit;
	}

	/**
	 * @return Returns the beforeMaterielCode.
	 */
	public String getBeforeMaterielCode() {
		return beforeMaterielCode;
	}

	/**
	 * @param beforeMaterielCode
	 *            The beforeMaterielCode to set.
	 */
	public void setBeforeMaterielCode(String beforeMaterielCode) {
		this.beforeMaterielCode = beforeMaterielCode;
	}

	/**
	 * @return Returns the beforeMaterielName.
	 */
	public String getBeforeMaterielName() {
		return beforeMaterielName;
	}

	/**
	 * @param beforeMaterielName
	 *            The beforeMaterielName to set.
	 */
	public void setBeforeMaterielName(String beforeMaterielName) {
		this.beforeMaterielName = beforeMaterielName;
	}

	/**
	 * @return Returns the beforeMaterielSpec.
	 */
	public String getBeforeMaterielSpec() {
		return beforeMaterielSpec;
	}

	/**
	 * @param beforeMaterielSpec
	 *            The beforeMaterielSpec to set.
	 */
	public void setBeforeMaterielSpec(String beforeMaterielSpec) {
		this.beforeMaterielSpec = beforeMaterielSpec;
	}

	/**
	 * @return Returns the fourMaterielCode.
	 */
	public String getFourComplexCode() {
		return fourComplexCode;
	}

	/**
	 * @param fourMaterielCode
	 *            The fourMaterielCode to set.
	 */
	public void setFourComplexCode(String fourMaterielCode) {
		this.fourComplexCode = fourMaterielCode;
	}

	/**
	 * @return Returns the fourMaterielName.
	 */
	public String getFourComplexName() {
		return fourComplexName;
	}

	/**
	 * @param fourMaterielName
	 *            The fourMaterielName to set.
	 */
	public void setFourComplexName(String fourMaterielName) {
		this.fourComplexName = fourMaterielName;
	}

	/**
	 * @return Returns the fourNo.
	 */
	public String getFourNo() {
		return fourNo;
	}

	/**
	 * @param fourNo
	 *            The fourNo to set.
	 */
	public void setFourNo(String fourNo) {
		this.fourNo = fourNo;
	}

	/**
	 * @return Returns the materielType.
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param materielType
	 *            The materielType to set.
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	/**
	 * @return Returns the serialNo.
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            The serialNo to set.
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}


	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	public Double getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	public String getAfterLegal2Unit() {
		return afterLegal2Unit;
	}

	public void setAfterLegal2Unit(String afterLegal2Unit) {
		this.afterLegal2Unit = afterLegal2Unit;
	}

	public Double getPtPrice() {
		return ptPrice;
	}

	public void setPtPrice(Double ptPrice) {
		this.ptPrice = ptPrice;
	}

	public Double getFristUnitRatio() {
		return fristUnitRatio;
	}

	public void setFristUnitRatio(Double fristUnitRatio) {
		this.fristUnitRatio = fristUnitRatio;
	}

	public Double getSecondUnitRatio() {
		return secondUnitRatio;
	}

	public void setSecondUnitRatio(Double secondUnitRatio) {
		this.secondUnitRatio = secondUnitRatio;
	}
	public Boolean getIsMainImg() {
		return isMainImg;
	}

	public void setIsMainImg(Boolean isMainImg) {
		this.isMainImg = isMainImg;
	}

	public Double getWeigthUnitGene() {
		return weigthUnitGene;
	}

	public void setWeigthUnitGene(Double weigthUnitGene) {
		this.weigthUnitGene = weigthUnitGene;
	}
	
}
