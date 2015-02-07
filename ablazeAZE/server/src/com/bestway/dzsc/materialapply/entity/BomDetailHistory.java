package com.bestway.dzsc.materialapply.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * BOM明细历史
 * @author yp
*/
public class BomDetailHistory extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 料件Id
	 */
	private String bomDetailId; 
	
	/**
	 * 成品版本
	 */
	private MaterialBomVersion version; 
	
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
	 * @param materiel 料件
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}
	
	/**
	 * 获取备注
	 * 
	 * @return note 备注
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 *设置备注
	 *
	 * @param note 备注
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
	 * @param unitWaste 单耗
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
	 * @param waste 损耗
	 */
	public void setWaste(Double waste) {
		this.waste = waste;
	}

	/**
	 * 获取成品版本
	 * 
	 * @return version 成品版本
	 */
	public MaterialBomVersion getVersion() {
		return version;
	}
	
	/**
	 * 设置成品版本
	 * 
	 * @param version 成品版本
	 */
	public void setVersion(MaterialBomVersion version) {
		this.version = version;
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
	 * @param unitUsed 单项用量
	 */
	public void setUnitUsed(Double unitUsed) {
		this.unitUsed = unitUsed;
	}
	
	/**
	 * 获取料件Id
	 * 
	 * @return bomDetailId 料件Id
	 */
	public String getBomDetailId() {
		return bomDetailId;
	}
	
	/**
	 * 设置料件Id
	 * 
	 * @param bomDetailId 料件Id
	 */
	public void setBomDetailId(String bomDetailId) {
		this.bomDetailId = bomDetailId;
	}
}
