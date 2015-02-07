/*
 * Created on 2005-3-17
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.bcsinnermerge.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * BCS内部归并对应表
 */
public class BcsInnerMerge extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 关联的物料基本资料
	 */
	private Materiel materiel = null;

	/**
	 * 类别标志 FINISHED_PRODUCT="0"; 成品 SEMI_FINISHED_PRODUCT="1"; 半成品
	 * MATERIEL="2"; 材料--主料 MACHINE="3"; 设备 REMAIN_MATERIEL="4"; 边角料
	 * BAD_PRODUCT="5"; 残次品 ASSISTANT_MATERIAL = "6"; 辅料 WASTE_MATERIAL = "7";
	 * 消耗料
	 */
	private String materielType = null;

	/**
	 * 十位商品编码
	 */
	private BcsTenInnerMerge bcsTenInnerMerge = null;

	/**
	 * 物料与报关商品的折算系数
	 */
	private Double unitConvert;
	/**
	 * 是否当前在使用
	 */
	private Boolean isUsing;
	/**
	 * 获取关联的物料基本资料
	 * 
	 * @return materiel 关联的物料基本资料
	 */
	public Materiel getMateriel() {
		return materiel;
	}

	/**
	 * 设置关联的物料基本资料
	 * 
	 * @param materiel
	 *            关联的物料基本资料
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	/**
	 * 获取类别标志
	 * 
	 * @return materielType 类别标志
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * 设置类别标志
	 * 
	 * @param materielType
	 *            类别标志
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	/**
	 * 获取十位商品编码
	 * 
	 * @return bcsTenInnerMerge 十位商品编码
	 */
	public BcsTenInnerMerge getBcsTenInnerMerge() {
		return bcsTenInnerMerge;
	}

	/**
	 * 设置十位商品编码
	 * 
	 * @param bcsTenInnerMerge
	 *            十位商品编码
	 */
	public void setBcsTenInnerMerge(BcsTenInnerMerge bcsTenInnerMerge) {
		this.bcsTenInnerMerge = bcsTenInnerMerge;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	/**
	 * @return the isUsing
	 */
	public Boolean getIsUsing() {
		return isUsing;
	}

	/**
	 * @param isUsing the isUsing to set
	 */
	public void setIsUsing(Boolean isUsing) {
		this.isUsing = isUsing;
	}
}