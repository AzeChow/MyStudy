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
public class DzscInnerMergeData extends BaseScmEntity {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 电子手册商品归并资料
	 */
	private DzscTenInnerMerge dzscTenInnerMerge = null;

	/**
	 * 物料类型
	 */
	private String imrType;

	/**
	 * 物料<第一层>
	 */
	private Materiel materiel;

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
	 * 是否变更
	 */
	private Boolean isChange = false;

	/**
	 * 物料与报关商品的折算系数
	 */
	private Double unitConvert;
	/**
	 *  是否选中
	 */
	private Boolean isSelected=false;

	public DzscTenInnerMerge getDzscTenInnerMerge() {
		return dzscTenInnerMerge;
	}

	public void setDzscTenInnerMerge(DzscTenInnerMerge dzscTenInnerMerge) {
		this.dzscTenInnerMerge = dzscTenInnerMerge;
	}

	/**
	 * 获取物料类型
	 * 
	 * @return imrType 物料类型
	 */
	public String getImrType() {
		return imrType;
	}

	/**
	 * 设置物料类型
	 * 
	 * @param imrType
	 *            物料类型
	 */
	public void setImrType(String imrType) {
		this.imrType = imrType;
	}

	/**
	 * 获取物料 <第一层>
	 * 
	 * @return materiel 物料 <第一层>
	 */
	public Materiel getMateriel() {
		return materiel;
	}

	/**
	 * 设置物料 <第一层>
	 * 
	 * @param materiel
	 *            物料 <第一层>
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	/**
	 * 获取修改标记
	 * 
	 * @return modifyMark 修改标记
	 */
	public String getBeforeModifyMark() {
		return beforeModifyMark;
	}

	/**
	 * 设置修改标记
	 * 
	 * @param modifyMark
	 *            修改标记
	 */
	public void setBeforeModifyMark(String modifyMark) {
		this.beforeModifyMark = modifyMark;
	}

	/**
	 * 获取是否变更
	 * 
	 * @return isChange 是否变更
	 */
	public Boolean getIsChange() {
		return isChange;
	}

	/**
	 * 设置是否变更
	 * 
	 * @param isChange
	 *            是否变更
	 */
	public void setIsChange(Boolean isChange) {
		this.isChange = isChange;
	}

	/**
	 * 获取serialVersionUID
	 * 
	 * @return serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	public void setStateMark(String stateMark) {
		this.stateMark = stateMark;
	}

	public String getStateMark() {
		return stateMark;
	}

	/**
	 * 获取状态
	 * 
	 * @return String 状态
	 */
	public String getStateMarkDesc() {
		String state = this.getStateMark();
		if (state.equals(DzscState.ORIGINAL)) {
			return "初始状态";
		} else if (state.equals(DzscState.APPLY)) {
			return "正在申报";
		} else if (state.equals(DzscState.EXECUTE)) {
			return "正在执行";
		} else if (state.equals(DzscState.CHANGE)) {
			return "正在变更";
		} else if (state.equals(DzscState.CHECK_CANCEL)) {
			return "正在核销";
		}
		return "";
	}

	/**
	 * 获取状态
	 * 
	 * @return String 状态
	 */
	public String getBeforeModifyMarkDesc() {
		String state = this.beforeModifyMark;
		if (state.equals(ModifyMarkState.UNCHANGE)) {
			return "未修改";
		} else if (state.equals(ModifyMarkState.ADDED)) {
			return "新增";
		} else if (state.equals(ModifyMarkState.DELETED)) {
			return "删除";
		} else if (state.equals(ModifyMarkState.MODIFIED)) {
			return "已修改";
		}
		return "";
	}

	public String getCustomsModifyMark() {
		return DzscCustomsModifyState.getCustomsModifyState(beforeModifyMark);
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
}