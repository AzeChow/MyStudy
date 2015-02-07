/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;

/**
 * 存放中期核查－－自用中期核查计算表－－自定义差异颜色资料
 */
public class ColorSet extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
   /**
    * 红色
    */
    public static final int	RED = 0;  
    /**
     * 深黄色
     */
    public static final int DEEPYELLOW = 1;
    /**
     * 黄色
     */
    public static final int YELLOW = 2;
    /**
     * 深绿色
     */
    public static final int DEEPGREEN = 3;
    /**
     * 绿色
     */
    public static final int GREEN = 4;
    
    
    /**
     * 开始数字
     */
    private Double beginNum = null;
 
    /**
     * 结束数字
     */
    private Double endNum = null;
   
    /**
     * 颜色
     * RED = 0;	红色
     * DEEPYELLOW = 1;	深黄色
     * YELLOW = 2;	黄色
     * DEEPGREEN = 3;	深绿色
     * GREEN = 4;	绿色
     */
    private Integer color;
	
    /**
     * 获取开始数字
     * 
	 * @return beginNum 开始数字
	 */
	public Double getBeginNum() {
		return beginNum;
	}
		
	/**
	 * 设置开始数字
	 * 
	 * @param beginNum 开始数字
	 */
	public void setBeginNum(Double beginNum) {
		this.beginNum = beginNum;
	}
	
    /**
     * 获取颜色
     * 
	 * @return color 颜色
	 */
	public Integer getColor() {
		return color;
	}
		
	/**
	 * 设置颜色
	 * 
	 * @param color 颜色
	 */
	public void setColor(Integer color) {
		this.color = color;
	}
	
    /**
     * 获取结束数字
     * 
	 * @return endNum 结束数字
	 */
	public Double getEndNum() {
		return endNum;
	}
	
	/**
	 * 设置结束数字
	 * 
	 * @param endNum 结束数字
	 */
	public void setEndNum(Double endNum) {
		this.endNum = endNum;
	}
    
    
    
	
}
