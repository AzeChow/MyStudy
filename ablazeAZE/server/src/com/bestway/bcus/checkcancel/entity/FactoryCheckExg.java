/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 中期核查－－工厂盘点－－成品
 */
public class FactoryCheckExg extends BaseScmEntity implements Cloneable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 参数设定
	 */
	private CheckParameter head = null;
	/**
	 * 料号
	 */
	private String ptNo = null;
	/**
	 * 名称
	 */
	private String name = null;
	/**
	 * 规格
	 */
	private String spec = null;
	/**
	 * 版本
	 */
	private String version = null; 

	/**
	 * 原料库存数量
	 */
	private Double materStockNum = null; 
	/**
	 * 工厂单位
	 */
	private CalUnit calUnit = null;
	/**
	 * 海关单位
	 */
	private Unit unit = null;
	/**
	 * 海关折算数量
	 */
	private Double bgNum = null;

	/**
	 * 获取参数设定
	 * 
	 * @return head 参数设定
	 */
	public CheckParameter getHead() {
		return head;
	}

	/**
	 * 设置参数设定
	 * 
	 * @param head 参数设定
	 */
	public void setHead(CheckParameter head) {
		this.head = head;
	}

	/**
	 * 获取原料库存数量
	 * 
	 * @return materStockNum 原料库存数量
	 */
	public Double getMaterStockNum() {
		return materStockNum;
	}

	/**
	 * 设置原料库存数量
	 * 
	 * @param materStockNum 原料库存数量
	 */
	public void setMaterStockNum(Double materStockNum) {
		this.materStockNum = materStockNum;
	}


	/**
	 * 获取版本
	 * 
	 * @return version 版本
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * 设置版本
	 * 
	 * @param version 版本
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Double getBgNum() {
		return bgNum;
	}

	public void setBgNum(Double bgNum) {
		this.bgNum = bgNum;
	}

	public CalUnit getCalUnit() {
		return calUnit;
	}

	public void setCalUnit(CalUnit calUnit) {
		this.calUnit = calUnit;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
