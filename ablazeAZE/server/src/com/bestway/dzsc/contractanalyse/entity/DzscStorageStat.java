/*
 * Created on 2005-6-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractanalyse.entity;

import java.io.Serializable;
import java.util.List;

import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 存放报关分析－－库存分析资料
 * 
 * @author ls
 */
public class DzscStorageStat implements Serializable{
	
	/**
	 * 商品编码
	 */
    private String complexCode          = null; 
	
	/**
	 * 品名规格
	 */
    private String nameSpec             = null;
	
	/**
	 * 单位
	 */
    private Unit   unit                 = null;
	
	/**
	 * 总合计
	 */
    private Double   totalize           = null; 
	
	/**
	 * 合同统计对象列表
	 */
    private List   tempContractStatList = null;
    /**
     * 补充说明--栢能
     */
    private String note;

    /**
     * 获取商品编码
     * 
     * @return complexCode 商品编码
     */
    public String getComplexCode() {
        return complexCode;
    }

    /**
     * 获取品名规格
     * 
     * @return nameSpec 品名规格
     */
    public String getNameSpec() {
        return nameSpec;
    }

    /**
     * 获取合同统计对象列表
     * 
     * @return tempContractStatList 合同统计对象列表
     */
    public List getTempContractStatList() {
        return tempContractStatList;
    }

    /**
     * 获取单位
     * 
     * @return unit 单位
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * 设置商品编码
     * 
     * @param complexCode 商品编码
     */
    public void setComplexCode(String complexCode) {
        this.complexCode = complexCode;
    }

    /**
     * 设置品名规格
     * 
     * @param nameSpec 品名规格
     */
    public void setNameSpec(String nameSpec) {
        this.nameSpec = nameSpec;
    }

    /**
     * 设置合同统计对象列表
     * 
     * @param tempContractStatList 合同统计对象列表
     */
    public void setTempContractStatList(List tempContractStatList) {
        this.tempContractStatList = tempContractStatList;
    }

    /**
     * 设置单位
     * 
     * @param unit 单位
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

	/**
	 * 设置总合计
	 * 
	 * @return totalize 总合计
	 */
	public Double getTotalize() {
		return totalize;
	}
	
	/**
     * 设置总合计
     * 
     * @param totalize 总合计
     */
	public void setTotalize(Double totalize) {
		this.totalize = totalize;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
