/*
 * Created on 2004-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.entity;

import java.util.Date;

import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;


/**
 * 不在使用状态
 * 
 * @author 001
 */
public class SysCode extends BaseEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 实体类名
	 */
	private String entityClassName;
	
	/**
	 * 最大长度
	 */
	private Integer maxLength;
	
	/**
	 * 前缀
	 */
	private String prefix;
	
	/**
	 * 年度
	 */
	private Integer yearLength;
	
	/**
	 * 月度
	 */
	private Integer monthLength;
	
	/**
	 * 天数
	 */
	private Integer dayLength;
	
	/**
	 * 序号
	 */
	private Integer seq;
	
	/**
	 * 当前时间
	 */
	private Date currDate;	

	
	/**
	 * 获取
	 * 
	 * @return entityClassName
	 */
	public String getEntityClassName() {
		return entityClassName;
	}
	/**
	 * 设置
	 * 
	 * @param entityClassName
	 */
	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}
	/**
	 * 获取
	 * 
	 * @return currDate
	 */
	public Date getCurrDate() {
		return currDate;
	}
	/**
	 * 设置
	 * 
	 * @param currDate
	 */
	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
	}
	/**
	 * 获取
	 * 
	 * @return dayLength
	 */
	public Integer getDayLength() {
		return dayLength;
	}
	/**
	 * 设置
	 * 
	 * @param dayLength
	 */
	public void setDayLength(Integer dayLength) {
		this.dayLength = dayLength;
	}

	/**
	 * 获取
	 * 
	 * @return maxLength
	 */
	public Integer getMaxLength() {
		return maxLength;
	}
	/**
	 * 设置
	 * 
	 * @param maxLength
	 */
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	/**
	 * 获取
	 * 
	 * @return monthLength
	 */
	public Integer getMonthLength() {
		return monthLength;
	}
	/**
	 * 设置
	 * 
	 * @param monthLength
	 */
	public void setMonthLength(Integer monthLength) {
		this.monthLength = monthLength;
	}
	/**
	 * 获取
	 * 
	 * @return prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * 设置
	 * 
	 * @param prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * 获取
	 * 
	 * @return seq
	 */
	public Integer getSeq() {
		return seq;
	}
	/**
	 * 设置
	 * 
	 * @param seq
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	/**
	 * 获取
	 * 
	 * @return yearLength
	 */
	public Integer getYearLength() {
		return yearLength;
	}
	/**
	 * 设置
	 * 
	 * @param yearLength
	 */
	public void setYearLength(Integer yearLength) {
		this.yearLength = yearLength;
	}
}
