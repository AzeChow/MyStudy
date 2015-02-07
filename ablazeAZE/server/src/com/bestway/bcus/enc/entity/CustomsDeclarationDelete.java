/*
 * Created on 2004-8-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.util.Date;

import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * 报关单
 * @author Administrator 报关单 
 * table="customsdeclarationdelete"
 */
public class CustomsDeclarationDelete extends BaseCustomsDeclaration {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
	 * 项目类型
	 * BCUS = 0;	电子帐册
	 * BCS = 1;	电子化手册
	 * DZSC = 3;	电子手册
	 */
    private Integer typeModel;
    /**
     * 操作日期
     */
    private Date workDate;    
    /**
     * 操作人
     */
    private String workEr;    
    
    
    
    
	/**
	 * 取得类型标记（使用在作废删单上面）
	 * @return 类型标记（使用在作废删单上面）
	 */
	public Integer getTypeModel() {
		return typeModel;
	}
	/**
	 * 设置类型标记（使用在作废删单上面）
	 * @param typeModel 类型标记（使用在作废删单上面）
	 */
	public void setTypeModel(Integer typeModel) {
		this.typeModel = typeModel;
	}
	/**
	 * 取得操作日期
	 * @return 操作日期
	 */
	public Date getWorkDate() {
		return workDate;
	}
	/**
	 * 设置操作日期
	 * @param workDate 操作日期
	 */
	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}
	/**
	 * 取得操作人
	 * @return 操作人
	 */
	public String getWorkEr() {
		return workEr;
	}
	/**
	 * 设置操作人
	 * @param workEr 操作人
	 */
	public void setWorkEr(String workEr) {
		this.workEr = workEr;
	}

	
}