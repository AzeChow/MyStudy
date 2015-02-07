/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.qp.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscCustomsModifyState;

/**
 * 存放电子手册合同备案里的料件资料
 * 
 * @author yp
 */
public class DzscQPEmsImgWj implements java.io.Serializable {

	/**
	 * 备案序号
	 */
	private Integer seqNum;

	/**
	 * 4码归并序号
	 */
	private Integer fourSeqNum;

	/**
	 * 4码商品编码
	 */
	private String fourComplexCode;

	/**
	 * 4码商品名称
	 */
	private String fourName;

	/**
	 * 4码商品型号规格
	 */
	private String fourSpec;

	/**
	 * 4码商品单位
	 */
	private String fourUnitCode = null;

	/**
	 * 法定计量单位
	 */
	private String firstUnitCode = null;

	/**
	 * 第二法定计量单位
	 */
	private String secondUnitCode = null;

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * 获取4码商品名称
	 * 
	 * @return fourName 4码商品名称
	 */
	public String getFourName() {
		return fourName;
	}

	/**
	 * 设置4码商品名称
	 * 
	 * @param fourName
	 *            4码商品名称
	 */
	public void setFourName(String fourName) {
		this.fourName = fourName;
	}

	/**
	 * 获取4码归并序号
	 * 
	 * @return fourSeqNum 4码归并序号
	 */
	public Integer getFourSeqNum() {
		return fourSeqNum;
	}

	/**
	 * 设置4码归并序号
	 * 
	 * @param fourSeqNum
	 *            4码归并序号
	 */
	public void setFourSeqNum(Integer fourSeqNum) {
		this.fourSeqNum = fourSeqNum;
	}

	/**
	 * 获取4码商品型号规格
	 * 
	 * @return fourSpec 4码商品型号规格
	 */
	public String getFourSpec() {
		return fourSpec;
	}

	/**
	 * 设置4码商品型号规格
	 * 
	 * @param fourSpec
	 *            4码商品型号规格
	 */
	public void setFourSpec(String fourSpec) {
		this.fourSpec = fourSpec;
	}

	public String getFourComplexCode() {
		return fourComplexCode;
	}

	public void setFourComplexCode(String fourComplexCode) {
		this.fourComplexCode = fourComplexCode;
	}

	public String getFourUnitCode() {
		return fourUnitCode;
	}

	public void setFourUnitCode(String fourUnitCode) {
		this.fourUnitCode = fourUnitCode;
	}

	public String getFirstUnitCode() {
		return firstUnitCode;
	}

	public void setFirstUnitCode(String firstUnitCode) {
		this.firstUnitCode = firstUnitCode;
	}

	public String getSecondUnitCode() {
		return secondUnitCode;
	}

	public void setSecondUnitCode(String secondUnitCode) {
		this.secondUnitCode = secondUnitCode;
	}
}