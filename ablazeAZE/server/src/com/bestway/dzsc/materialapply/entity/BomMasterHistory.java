package com.bestway.dzsc.materialapply.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * BOM表头历史
 * @author yp
 */
public class BomMasterHistory extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 成品Id
	 */
	private String bomMasterId;
	
	/**
	 * 料件---电子手册使用
	 */
	private Materiel materiel;
	
	/**
	 * 状态标志
	 */
	private String stateMark;
	
	/**
	 * 获取料件---电子手册使用
	 * 
	 * @return materiel 料件---电子手册使用
	 */
	public Materiel getMateriel() {
		return materiel;
	}
	
	/**
	 * 设置料件---电子手册使用
	 * 
	 * @param materiel 料件---电子手册使用
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}
	
	/**
	 * 获取状态标志
	 * 
	 * @return stateMark 状态标志
	 */
	public String getStateMark() {
		return stateMark;
	}
	
	/**
	 * 设置状态标志
	 * 
	 * @param stateMark 状态标志
	 */
	public void setStateMark(String stateMark) {
		this.stateMark = stateMark;
	}
	
	/**
	 * 获取成品Id
	 * 
	 * @return bomMasterId 成品Id
	 */
	public String getBomMasterId() {
		return bomMasterId;
	}
	
	/**
	 * 设置成品Id
	 * 
	 * @param bomMasterId 成品Id
	 */
	public void setBomMasterId(String bomMasterId) {
		this.bomMasterId = bomMasterId;
	}
}
