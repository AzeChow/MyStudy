/*
 * Created on 2004-10-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放报关常用工厂BOM成品资料
 * 
 * @author administrator
 */
public class MaterialBomMaster extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 物料
	 */
	private Materiel materiel;
	//---电子手册使用
	
	/**
	 * 状态表示
	 * ORIGINAL = "0";	原始状态
	 * APPLY = "1"; 	申请
	 * EXECUTE = "2";	执行
	 * CHANGE = "3";	变更
	 * CHECK_CANCEL = "4";	 核销
	 * BACK_BILL = "5";	退单
	 */
	private String stateMark;
	
//	/**
//	 * 料号
//	 */
//	private String ptNo;
	
	/**
	 * 获取物料
	 * 
	 * @return materiel 物料
	 */
	public Materiel getMateriel() {
		return materiel;
	}
	
	/**
	 * 设置物料
	 * 
	 * @param materiel 物料
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

//	/**
//	 * 获取料号
//	 * 
//	 * @return ptNo 料号
//	 */
//	public String getPtNo() {
//		return ptNo;
//	}
//		
//	/**
//	 * 设置料号
//	 * 
//	 * @param ptNo 料号
//	 */
//	public void setPtNo(String ptNo) {
//		this.ptNo = ptNo;
//	}
}
