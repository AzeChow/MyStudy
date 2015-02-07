/*
 * Created on 2004-10-11
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.materialapply.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscCustomsModifyState;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 存放电子手册－单耗申报的料件资料
 * @author yp
 * 
 * 
 */
public class MaterialBomDetailApply extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 成品版本
	 */
	private MaterialBomVersionApply versionApply;

	/**
	 * 料件
	 */
	private Materiel materiel;

	/**
	 * 单耗
	 */
	private Double unitWaste;

	/**
	 * 损耗
	 */
	private Double waste;

	/**
	 * 单项用量
	 */
	private Double unitUsed;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 修改标志
	 */
	private String modifyMark;

	/**
	 * 获取备注
	 * 
	 * @return note 备注
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置备注
	 * 
	 * @param note
	 *            备注
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取单耗
	 * 
	 * @return unitWaste 单耗
	 */
	public Double getUnitWaste() {
		return unitWaste;
	}

	/**
	 * 设置单耗
	 * 
	 * @param unitWaste
	 *            单耗
	 */
	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}

	/**
	 * 获取损耗
	 * 
	 * @return waste 损耗
	 */
	public Double getWaste() {
		return waste;
	}

	/**
	 * 设置损耗
	 * 
	 * @param waste
	 *            损耗
	 */
	public void setWaste(Double waste) {
		this.waste = waste;
	}

	/**
	 * 获取成品版本
	 * 
	 * @return version 成品版本
	 */
	public MaterialBomVersionApply getVersionApply() {
		return versionApply;
	}

	/**
	 * 设置成品版本
	 * 
	 * @param version
	 *            成品版本
	 */
	public void setVersionApply(MaterialBomVersionApply version) {
		this.versionApply = version;
	}

	/**
	 * 获取单项用量
	 * 
	 * @return unitUsed 单项用量
	 */
	public Double getUnitUsed() {
		return unitUsed;
	}

	/**
	 * 设置单项用量
	 * 
	 * @param unitUsed
	 *            单项用量
	 */
	public void setUnitUsed(Double unitUsed) {
		this.unitUsed = unitUsed;
	}

	/**
	 * 获取修改标志
	 * 
	 * @return editMark 修改标志
	 */
	public String getModifyMark() {
		return modifyMark;
	}

	/**
	 * 设置修改标志
	 * 
	 * @param editMark
	 *            修改标志
	 */
	public void setModifyMark(String editMark) {
		this.modifyMark = editMark;
	}

	/**
	 * 获取料件
	 * 
	 * @return materiel 料件
	 */
	public Materiel getMateriel() {
		return materiel;
	}

	/**
	 * 设置料件
	 * 
	 * @param materiel
	 *            料件
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	/**
	 * 获取海关修改标志
	 * 
	 * @return editMark 修改标志
	 */
	public String getCustomsModifyMark() {
		return DzscCustomsModifyState.getCustomsModifyState(modifyMark);
	}
}
