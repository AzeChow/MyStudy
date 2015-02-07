/*
 * Created on 2004-9-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 料件，成品，设备库存情况统计表
 */
public class StoreInfo implements Serializable {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
	 * BOM编号
	 */
	String ptPart;
	 /**
	 * 商品名称
	 */
	String hsName; 
	/**
	 * 商品规格
	 */
	String hsSpec;  
	/**
	 * 商品编码
	 */
    String complex; 
    /**
	 * 今日数量
	 */
	double todayAmount; 
	/**
	 * 仓库
	 */
	WareSet wareSet; 	
	/**
	 * 昨天数量
	 */
	double yestdayAmount; 
	/**
	 * 单位
	 */
	CalUnit unit;		
	
	
	
	
	/**
	 * 取得商品名称
	 * @return hsName 商品名称.
	 */
	public String getHsName() {
		return hsName;
	}
	/**
	 * 设置商品名称
	 * @param hsName 商品名称.
	 */
	public void setHsName(String hsName) {
		this.hsName = hsName;
	}
	/**
	 * 取得商品规格
	 * @return hsSpec 商品规格.
	 */
	public String getHsSpec() {
		return hsSpec;
	}
	/**
	 * 设置商品规格
	 * @param hsSpec  商品规格.
	 */
	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}
	/**
	 * 取得bom编号
	 * @return ptPart bom编号.
	 */
	public String getPtPart() {
		return ptPart;
	}
	/**
	 * 设置bom编号
	 * @param ptPart bom编号.
	 */
	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}
	/**
	 * 取得今日数量
	 * @return todayAmount 今日数量.
	 */
	public double getTodayAmount() {
		return todayAmount;
	}
	/**
	 * 设置今日数量
	 * @param todayAmount  今日数量.
	 */
	public void setTodayAmount(double todayAmount) {
		this.todayAmount = todayAmount;
	}
	/**
	 * 获取单位
	 * @return unit 单位.
	 */
	public CalUnit getUnit() {
		return unit;
	}
	/**
	 * 设置单位
	 * @param unit  单位.
	 */
	public void setUnit(CalUnit unit) {
		this.unit = unit;
	}
	/**
	 * 取得仓库名称
	 * @return  wareSet 仓库.
	 */
	public WareSet getWareSet() {
		return wareSet;
	}
	/**
	 * 设置仓库名称
	 * @param wareSet  仓库.
	 */
	public void setWareSet(WareSet wareSet) {
		this.wareSet = wareSet;
	}
	/**
	 * 取得昨日数量
	 * @return yestdayAmount 昨日数量.
	 */
	public double getYestdayAmount() {
		return yestdayAmount;
	}
	/**
	 * 设置昨日数量
	 * @param yestdayAmount 昨日数量.
	 */
	public void setYestdayAmount(double yestdayAmount) {
		this.yestdayAmount = yestdayAmount;
	}
	/**
	 * 取得商品编码
	 * @return complex 商品编码.
	 */
    public String getComplex() {
        return complex;
    }
    /**
	 * 设置商品编码
	 * @param complex  商品编码.
	 */
    public void setComplex(String complex) {
        this.complex = complex;
    }
}
