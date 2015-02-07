/*
 * Created on 2005-3-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractexe.entity;

import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * 存放报关单物料
 * 
 * @author Administrator
 */
public class DzscCustomsDeclarationCommInfo extends BaseCustomsDeclarationCommInfo {
	
	private static final long serialVersionUID  = CommonUtils
                                  .getSerialVersionUID();
	
	/**
	 * 补充说明==栢能
	 */
	private String note;
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
