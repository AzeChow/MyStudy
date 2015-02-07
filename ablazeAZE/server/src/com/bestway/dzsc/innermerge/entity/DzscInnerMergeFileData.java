/*
 * Created on 2004-7-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.innermerge.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * 存放从文本导入的内部归并资料
 * 
 * @author Administrator
 */
public class DzscInnerMergeFileData implements Serializable {

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
	 * 商品名称
	 */
	public static final int BEFORE_MATERIEL_NAME = 20;

	/**
	 * 型号、规格
	 */
	public static final int BEFORE_MATERIEL_SPEC = 21;

	/**
	 * 申报单位
	 */
	public static final int BEFORE_UNIT = 5;

	// /**
	// * 计量单位（法定）
	// */
	// public static final int BEFORE_LEGAL_UNIT = 5;
	//	
	/**
	 * 计量单位（企业使用）
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
	 * 10位商品名称
	 */
	public static final int AFTER_COMPLEX_NAME = 22;

	/**
	 * 10位商品规格、型号
	 */
	public static final int AFTER_COMPLEX_SPEC = 23;

	/**
	 * 10位计量单位
	 */
	public static final int AFTER_UNIT = 10;

	// /**
	// * 10位计量单位（法定）
	// */
	// public static final int AFTER_LEGAL_UNIT = 10;
	//	
	// /**
	// * 10位计量单位（备案）
	// */
	// public static final int AFTER_MEMO_UNIT = 11;

	/**
	 * 4位编码序号
	 */
	public static final int FOUR_NO = 11;

	/**
	 * 4位编码商品名称
	 */
	public static final int FOUR_COMPLEX_NAME = 12;

	/**
	 * 4位商品编码
	 */
	public static final int FOUR_COMPLEX_CODE = 13;

	/**
	 * 4计量单位
	 */
	public static final int FOUR_UNIT = 14;

	/**
	 * 归并前和归并后单位换算率
	 */
	public static final int UNIT_CONVERT = 15;

	/**
	 * 单重
	 */
	public static final int UNIT_WEIGHT = 16;

	/**
	 * 法定单位比例因子
	 */
	public static final int LEGAL_UNIT_GENE = 17;

	/**
	 * 第二法定单位比例因子
	 */
	public static final int LEGAL_UNIT2_GENE = 18;

	/**
	 * 重量单位比例因子
	 */
	public static final int WEIGHT_UNIT_GENE = 19;

	/**
	 * 物料类别
	 */
	private String materielType;

	/**
	 * 序号
	 */
	private String serialNo;

	/**
	 * 10位商品编码
	 */
	private String beforeTenComplexCode;

	/**
	 * 料号
	 */
	private String beforeMaterielCode;

	/**
	 * 归并前料件名称
	 */
	private String beforeMaterielName;

	/**
	 * 归并前料件规格
	 */
	private String beforeMaterielSpec;

	//	
	// /**
	// *
	// */
	// private String beforeLegalUnit;
	//	
	/**
	 * 企业
	 */
	private String beforeEnterpriseUnit;

	/**
	 * 单位
	 */
	private String beforeUnit;

	/**
	 * 备案序号
	 */
	private String afterTenMemoNo;

	/**
	 * 10位商品编码
	 */
	private String afterTenComplexCode;

	/**
	 * 商品名称
	 */
	private String afterComplexlName;

	/**
	 * 商品编码
	 */
	private String afterComplexSpec;

	/**
	 * 单位
	 */
	private String afterUnit;

	/**
	 * 法定单位比例因子
	 */
	private String legalUnitGene;

	/**
	 * 第二法定单位比例因子
	 */
	private String legalUnit2Gene;

	/**
	 * 重量单位比例因子
	 */
	private String weigthUnitGene;

	// /**
	// *
	// */
	// private String afterLegalUnit;
	//
	// /**
	// *
	// */
	// private String afterMemoUnit;

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
	 * 4位单位
	 */
	private String fourUnit;

	/**
	 * 归并前和归并后单位换算率
	 */
	private String unitConvert;

	/**
	 * 单重
	 */
	private String unitWeight;

	/**
	 * 归并前商品名称，规格
	 */
	private String hsBeforeMaterielNameSpec; // 归并前商品名称，规格。

	/**
	 * 归并后商品名称，规格
	 */
	private String hsAfterComplexNameSpec; // 归并前商品名称，规格。

	/**
	 * 
	 */
	private int[] invalidationColNo;

	/**
	 * 错误原因
	 */
	private String errorReason = "";

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

	// /**
	// * @return Returns the afterLegalUnit.
	// */
	// public String getAfterLegalUnit() {
	// return afterLegalUnit;
	// }
	//
	// /**
	// * @param afterLegalUnit
	// * The afterLegalUnit to set.
	// */
	// public void setAfterLegalUnit(String afterLegalUnit) {
	// this.afterLegalUnit = afterLegalUnit;
	// }

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

	// /**
	// * @return Returns the afterMemoUnit.
	// */
	// public String getAfterMemoUnit() {
	// return afterMemoUnit;
	// }
	//
	// /**
	// * @param afterMemoUnit
	// * The afterMemoUnit to set.
	// */
	// public void setAfterMemoUnit(String afterMemoUnit) {
	// this.afterMemoUnit = afterMemoUnit;
	// }

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

	// /**
	// * @return Returns the beforeLegalUnit.
	// */
	// public String getBeforeLegalUnit() {
	// return beforeLegalUnit;
	// }
	// /**
	// * @param beforeLegalUnit The beforeLegalUnit to set.
	// */
	// public void setBeforeLegalUnit(String beforeLegalUnit) {
	// this.beforeLegalUnit = beforeLegalUnit;
	// }
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

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	public String getBeforeUnit() {
		return beforeUnit;
	}

	public void setBeforeUnit(String beforeUnit) {
		this.beforeUnit = beforeUnit;
	}

	public String getAfterUnit() {
		return afterUnit;
	}

	public void setAfterUnit(String afterUnit) {
		this.afterUnit = afterUnit;
	}

	public String getFourUnit() {
		return fourUnit;
	}

	public void setFourUnit(String fourUnit) {
		this.fourUnit = fourUnit;
	}

	public String getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(String convertRate) {
		this.unitConvert = convertRate;
	}

	public String getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(String unitWeight) {
		this.unitWeight = unitWeight;
	}

	public String getLegalUnit2Gene() {
		return legalUnit2Gene;
	}

	public void setLegalUnit2Gene(String legalUnit2Gene) {
		this.legalUnit2Gene = legalUnit2Gene;
	}

	public String getLegalUnitGene() {
		return legalUnitGene;
	}

	public void setLegalUnitGene(String legalUnitGene) {
		this.legalUnitGene = legalUnitGene;
	}

	public String getWeigthUnitGene() {
		return weigthUnitGene;
	}

	public void setWeigthUnitGene(String weigthUnitGene) {
		this.weigthUnitGene = weigthUnitGene;
	}
}
