/*
 * Created on 2004-6-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity.parametercode;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放报关参数－－集装箱托架种类资料
 * 
 * @author Administrator
 */
public class SrtTj extends CustomBaseEntity{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
    /**
	 * 类型
	 */
	private String tjType;  
		
    /**
	 * 重量
	 */
	private Double tjWeight; 
		
    /**
	 * 托架英文名称
	 */
	private String bracketEnglishName ;
	
	
	/**
	 * 获取类型
	 * 
	 * @return tjType 类型
	 */
	public String getTjType() {
		return tjType;
	}
	
	/**
	 * 设置类型
	 * 
	 * @param tjType 类型
	 */
	public void setTjType(String tjType) {
		this.tjType = tjType;
	}
	/**
	 * 获取重量
	 * 
	 * @return tjWeight 重量
	 */
	public Double getTjWeight() {
		return tjWeight;
	}
	
	/**
	 * 获取托架英文名称
	 * 
	 * @return bracketEnglishName 托架英文名称
	 */
	public String getBracketEnglishName()
	{
		return bracketEnglishName;
	}
	
	/**
	 * 设置托架英文名称
	 * 
	 * @param bracketEnglishName 托架英文名称
	 */
	public void setBracketEnglishName(String bracketEnglishName)
	{
		this.bracketEnglishName = bracketEnglishName;
	}
		
	/**
	 * 设置重量
	 * 
	 * @param tjWeight 重量
	 */
	public void setTjWeight(Double tjWeight) {
		this.tjWeight = tjWeight;
	}
}
