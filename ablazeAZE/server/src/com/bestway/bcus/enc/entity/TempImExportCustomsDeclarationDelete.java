package com.bestway.bcus.enc.entity;

import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;


public class TempImExportCustomsDeclarationDelete extends BaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	
	/**
	 * 报关单头
	 */
	private BaseCustomsDeclaration baseCustomsDeclaration = null;
	
	/**
	 * 海关删单报关表体查询
	 */
	private BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo = null;
	
	
	
	public BaseCustomsDeclarationCommInfo getBaseCustomsDeclarationCommInfo() {
		return baseCustomsDeclarationCommInfo;
	}
	
	public void setBaseCustomsDeclarationCommInfo(
			BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo) {
		this.baseCustomsDeclarationCommInfo = baseCustomsDeclarationCommInfo;
	}
	
	public BaseCustomsDeclaration getBaseCustomsDeclaration() {
		return baseCustomsDeclaration;
	}
	
	public void setBaseCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		this.baseCustomsDeclaration = baseCustomsDeclaration;
	}


}
