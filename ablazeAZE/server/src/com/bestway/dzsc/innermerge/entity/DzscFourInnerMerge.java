/*
 * Created on 2004-7-2
 * 内部归并
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.innermerge.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscCustomsModifyState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 存放电子手册商品归并资料
 * 
 * @author bsway
 */
public class DzscFourInnerMerge extends BaseScmEntity {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 四位编码序号<第三层>
	 */
	private Integer fourSeqNum;

	/**
	 * 四位商品名称
	 */
	private String fourPtName;

	/**
	 * 四位商品规格
	 */
	private String fourPtSpec;

	/**
	 * 四位商品单位
	 */
	private Unit fourUnit;

	/**
	 * 最低单价
	 */
	// private Double lowPrice; // 最低单价
	/**
	 * 四位商品编码
	 */
	private String fourComplex;

	/**
	 * 获取四位商品编码
	 * 
	 * @return fourComplex 四位商品编码
	 */
	public String getFourComplex() {
		return fourComplex;
	}

	/**
	 * 设置四位商品编码
	 * 
	 * @param fourComplex
	 *            四位商品编码
	 */
	public void setFourComplex(String fourComplex) {
		this.fourComplex = fourComplex;
	}

	/**
	 * 获取四位商品名称
	 * 
	 * @return fourPtName 四位商品名称
	 */
	public String getFourPtName() {
		return fourPtName;
	}

	/**
	 * 设置四位商品名称
	 * 
	 * @param fourPtName
	 *            四位商品名称
	 */
	public void setFourPtName(String fourPtName) {
		this.fourPtName = fourPtName;
	}

	/**
	 * 获取四位商品规格
	 * 
	 * @return fourPtSpec 四位商品规格
	 */
	public String getFourPtSpec() {
		return fourPtSpec;
	}

	/**
	 * 设置四位商品规格
	 * 
	 * @param fourPtSpec
	 *            四位商品规格
	 */
	public void setFourPtSpec(String fourPtSpec) {
		this.fourPtSpec = fourPtSpec;
	}

	/**
	 * 获取四位编码序号 <第三层>
	 * 
	 * @return fourSeqNum 四位编码序号 <第三层>
	 */
	public Integer getFourSeqNum() {
		return fourSeqNum;
	}

	/**
	 * 设置四位编码序号 <第三层>
	 * 
	 * @param fourSeqNum
	 *            四位编码序号 <第三层>
	 */
	public void setFourSeqNum(Integer fourSeqNum) {
		this.fourSeqNum = fourSeqNum;
	}

	/**
	 * 获取四位商品单位
	 * 
	 * @return fourUnit 四位商品单位
	 */
	public Unit getFourUnit() {
		return fourUnit;
	}

	/**
	 * 设置四位商品单位
	 * 
	 * @param fourUnit
	 *            四位商品单位
	 */
	public void setFourUnit(Unit fourUnit) {
		this.fourUnit = fourUnit;
	}

	/**
	 * 获取serialVersionUID
	 * 
	 * @return serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}