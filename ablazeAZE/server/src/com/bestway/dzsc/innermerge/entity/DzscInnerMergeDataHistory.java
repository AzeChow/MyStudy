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
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 存放电子手册商品归并的变更历史
 * 
 * @author bsway
 */
public class DzscInnerMergeDataHistory extends BaseScmEntity {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 内部归并数据的ID
	 */
	private String innerMergeDataId;

	/**
	 * 流水号
	 */
	private Integer no;

	/**
	 * 物料类型
	 */
	private String imrType;

	/**
	 * 物料<第一层>
	 */
	private Materiel materiel;

	/**
	 * 十位编码序号
	 */
	private Integer tenSeqNum;

	/**
	 * 十位商品编码<第二层>
	 */
	private Complex tenComplex;

	/**
	 * 四位商品名称tenPtName
	 */
	private String tenPtName;

	/**
	 * 四位商品规格
	 */
	private String tenPtSpec;

	/**
	 * 四位商品单位
	 */
	private Unit tenUnit;

	/**
	 * 十位规格,对应列名
	 */
	// private String spec; // 十位规格
	/**
	 * 四位编码序号<第三层>
	 */
	private Integer fourSeqNum;

	/**
	 * 
	 */
	// private LevyMode levyMode; // 征免性质(数据为征免方式)默认来自系统设置报关单LevyMode
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
	 * 状态标志
	 * ORIGINAL = "0"	原始状态
	 * APPLY = "1";	申请
	 * EXECUTE = "2";	执行
	 * CHANGE = "3";	变更
	 * CHECK_CANCEL = "4";	核销
	 * BACK_BILL = "5";	退单
	 */
	private String stateMark;

    /**
	 * 修改标志(归并前)
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String beforeModifyMark;


    /**
	 * 修改标志(归并后10码)
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String tenModifyMark;

	/**
	 * 是否变更
	 */
	private Boolean isChange = false;

	// private Boolean isDeleted; 删除标记

	/**
	 * 物料与报关商品的折算系数
	 */
	private Double unitConvert;

	public String getBeforeModifyMark() {
		return beforeModifyMark;
	}

	public void setBeforeModifyMark(String beforeModifyMark) {
		this.beforeModifyMark = beforeModifyMark;
	}

	public String getFourComplex() {
		return fourComplex;
	}

	public void setFourComplex(String fourComplex) {
		this.fourComplex = fourComplex;
	}

	public String getFourPtName() {
		return fourPtName;
	}

	public void setFourPtName(String fourPtName) {
		this.fourPtName = fourPtName;
	}

	public String getFourPtSpec() {
		return fourPtSpec;
	}

	public void setFourPtSpec(String fourPtSpec) {
		this.fourPtSpec = fourPtSpec;
	}

	public Integer getFourSeqNum() {
		return fourSeqNum;
	}

	public void setFourSeqNum(Integer fourSeqNum) {
		this.fourSeqNum = fourSeqNum;
	}

	public Unit getFourUnit() {
		return fourUnit;
	}

	public void setFourUnit(Unit fourUnit) {
		this.fourUnit = fourUnit;
	}

	public String getImrType() {
		return imrType;
	}

	public void setImrType(String imrType) {
		this.imrType = imrType;
	}

	public Boolean getIsChange() {
		return isChange;
	}

	public void setIsChange(Boolean isChange) {
		this.isChange = isChange;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getStateMark() {
		return stateMark;
	}

	public void setStateMark(String stateMark) {
		this.stateMark = stateMark;
	}

	public Complex getTenComplex() {
		return tenComplex;
	}

	public void setTenComplex(Complex tenComplex) {
		this.tenComplex = tenComplex;
	}

	public String getTenModifyMark() {
		return tenModifyMark;
	}

	public void setTenModifyMark(String tenModifyMark) {
		this.tenModifyMark = tenModifyMark;
	}

	public String getTenPtName() {
		return tenPtName;
	}

	public void setTenPtName(String tenPtName) {
		this.tenPtName = tenPtName;
	}

	public String getTenPtSpec() {
		return tenPtSpec;
	}

	public void setTenPtSpec(String tenPtSpec) {
		this.tenPtSpec = tenPtSpec;
	}

	public Integer getTenSeqNum() {
		return tenSeqNum;
	}

	public void setTenSeqNum(Integer tenSeqNum) {
		this.tenSeqNum = tenSeqNum;
	}

	public Unit getTenUnit() {
		return tenUnit;
	}

	public void setTenUnit(Unit tenUnit) {
		this.tenUnit = tenUnit;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	/**
	 * 获取内部归并数据的ID
	 * 
	 * @return innerMergeDataId 内部归并数据的ID
	 */
	public String getInnerMergeDataId() {
		return innerMergeDataId;
	}

	/**
	 * 设置内部归并数据的ID
	 * 
	 * @param innerMergeDataId
	 *            内部归并数据的ID
	 */
	public void setInnerMergeDataId(String innerMergeDataId) {
		this.innerMergeDataId = innerMergeDataId;
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