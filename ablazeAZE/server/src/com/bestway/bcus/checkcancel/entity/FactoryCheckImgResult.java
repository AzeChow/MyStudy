/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 中期核查－－工厂盘点－－料件汇总
 */
public class FactoryCheckImgResult extends BaseScmEntity implements Cloneable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 参数
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
     * 单位
     */
    private Unit   unit = null;
    
	/**
	 * 原料库存数量
	 */
	private Double materielStockNum = null;

	/**
	 * 获取参数
	 * 
	 * @return head 参数
	 */
	public CheckParameter getHead() {
		return head;
	}

	/**
	 * 设置参数
	 * 
	 * @param head
	 *            参数
	 */
	public void setHead(CheckParameter head) {
		this.head = head;
	}

	/**
	 * 获取原料库存数量
	 * 
	 * @return materielStockNum 原料库存数量
	 */
	public Double getMaterielStockNum() {
		return materielStockNum;
	}

	/**
	 * 设置原料库存数量
	 * 
	 * @param materielStockNum
	 *            原料库存数量
	 */
	public void setMaterielStockNum(Double materielStockNum) {
		this.materielStockNum = materielStockNum;
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

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	
}
