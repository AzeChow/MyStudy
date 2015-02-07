/*
 * Created on 2005-1-28
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.backup.entity;

import java.io.Serializable;
import java.util.List;

import com.bestway.common.CommonUtils;

/**
 * 存放备份模式（全部备份后选择备份）
 */
public class BackupModuleInfo implements Serializable {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 模块名称
	 */
	private String name;
	/**
	 * 模块对应值
	 */
	private List value;
	
	/**
	 * 取得模块名称
	 * @return 模块名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置模块名称
	 * @param name 模块名称
	 */
	public void setName(String name) {
		this.name = name;
	}	
	

	
	
//	public String toString(){
//		return name;
//	}
	
	
    /**
     * 取得模块对应值
     * @return 模块对应值
     */
    public List getValue() {
        return value;
    }
    
    /**
     * 设置模块对应值
     * @param value 模块对应值
     */
    public void setValue(List value) {
        this.value = value;
    }
}
