/*
 * Created on 2005-6-22
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractstat.entity;

import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * 存放统计报表中的进口料件报关登记表或出口成品报关登记表资料
 * 
 * @author Administrator
 */
public class DzscImpExpCustomsDeclarationCommInfo extends
		BaseCustomsDeclarationCommInfo {
	/**
	 * 数量累计
	 */
	private Double commAddUpAmount;
	/**
	 * 补充说明--栢能
	 */
	private String note = null;
	
	/**
	 * 获取数量累计
	 *  
	 * @return commAddUpAmount 数量累计
	 */
	public Double getCommAddUpAmount() {
		return commAddUpAmount;
	}
	
	/**
	 * 设置数量累计
	 * 
	 * @param commAddUpAmount 数量累计
	 */
	public void setCommAddUpAmount(Double commAddUpAmount) {
		this.commAddUpAmount = commAddUpAmount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
