/*
 * Created on 2004-9-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放物料中的既是料件又是半成的品的料件
 * @author lin
 */
public class MaterielAndHalfProduct extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();   
    
    /**
     * 料号
     */
    private String code = null; 
    
    /**
     * 名称
     */
    private String name = null;  
    
    /**
     * 备注
     */
    private String note =  null;
	
    /**
     * 获取料号
     * 
	 * @return code 料号
	 */
	public String getCode() {
		return code;
	}
		
	/**
	 * 设置料号
	 * 
	 * @param code 料号
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
    /**
     * 获取名称
     * 
	 * @return name 名称
	 */
	public String getName() {
		return name;
	}
		
	/**
	 * 设置名称
	 * 
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
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
	 * 设置备注
	 * 
	 * @param note 备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
    
    
}
