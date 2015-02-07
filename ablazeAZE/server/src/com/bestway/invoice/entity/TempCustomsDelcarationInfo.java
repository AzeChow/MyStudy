/*
 * Created on 2005-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.invoice.entity;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 */
public class TempCustomsDelcarationInfo implements Serializable {
	/**
	 * 报关单号
	 */
	private String customsDeclarationCode; 
	/**
	 * 报关金额
	 */
	private Double money;
	/**
	 * 报关单来源 bcus bcs dzba
	 */
	private Integer projectType; 
	/**
	 * 报关单ID号
	 */
	private String customsDeclarationId; 
	/**
	 * 取得报关单号
	 * @return customsDeclarationCode 报关单号
	 */
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}
	/**
	 * 设置报关单号
	 * @param customsDeclarationCode 报关单号
	 */
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}
	/**
	 * 取得金额
	 * @return money 金额
	 */
	public Double getMoney() {
		return money;
	}
	/**设置金额
	 * @param money 金额
	 */
	public void setMoney(Double money) {
		this.money = money;
	}
	/**
	 * 取得报关单来源
	 * @return projectType 报关单来源
	 */
	public Integer getProjectType() {
		return projectType;
	}
	/**
	 * 设置报关单来源
	 * @param projectType 报关单来源
	 */
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	/**
	 * 取得报关单id
	 * @return RcustomsDeclarationId 报关单id
	 */
	public String getCustomsDeclarationId() {
		return customsDeclarationId;
	}
	/**
	 * 设置报关单id 
	 * @param customsDeclarationId 报关单id
	 */
	public void setCustomsDeclarationId(String customsDeclarationId) {
		this.customsDeclarationId = customsDeclarationId;
	}
}
