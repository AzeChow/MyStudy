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

/**
 * 存放滚动核销－－帐帐分析－－盘点数据分析－－成品折料－－成品所折料件
 */
public class EmsPdExgImgBg extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
     * 帐帐分析表头
     */
    private EmsAnalyHead head = null;
    /**
     * 盘点成品
     */
    private EmsPdExgBg pdExgBg=null;
	  
    /**
     * 帐册编号
     */
    private String emsNo = null; 
	  
    /**
     * 备案序号
     */
    private Integer seqNum = null;
	  
    /**
     * 料件名称
     */
    private String name = null;
	  
    /**
     * 料件规格
     */
    private String spec = null;
	  
    /**
     * 计量单位
     */
    private Unit unit = null; 
	  
    /**
     * 单耗
     */
    private Double unitWare = null; 
	  
    /**
     * 损耗
     */
    private Double ware = null;
	  
    /**
     * 总耗用
     */
    private Double totalWate = null;
    
    
   
	
	

	public EmsPdExgBg getPdExgBg() {
		return pdExgBg;
	}

	public void setPdExgBg(EmsPdExgBg pdExgBg) {
		this.pdExgBg = pdExgBg;
	}

	/**
	 * 获取帐册编号
	 * 
	 * @return emsNo 帐册编号
	 */
	public String getEmsNo() {
		return emsNo;
	}
	
	/**
	 * 设置帐册编号
	 * 
	 * @param emsNo 帐册编号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
		
	/**
	 * 获取料件名称
	 * 
	 * @return name 料件名称
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置料件名称
	 * 
	 * @param name 料件名称
	 */
	public void setName(String name) {
		this.name = name;
	}
		
	/**
	 * 获取备案序号
	 * 
	 * @return seqNum 备案序号
	 */
	public Integer getSeqNum() {
		return seqNum;
	}
	
	/**
	 * 设置备案序号
	 * 
	 * @param seqNum 备案序号
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
		
	/**
	 * 获取料件规格
	 * 
	 * @return spec 料件规格
	 */
	public String getSpec() {
		return spec;
	}
	
	/**
	 * 设置料件规格
	 * 
	 * @param spec 料件规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
		
	/**
	 * 获取总耗用
	 * 
	 * @return totalWate 总耗用
	 */
	public Double getTotalWate() {
		return totalWate;
	}
	
	/**
	 * 设置总耗用
	 * 
	 * @param totalWate 总耗用
	 */
	public void setTotalWate(Double totalWate) {
		this.totalWate = totalWate;
	}
		
	/**
	 * 获取计量单位
	 * 
	 * @return unit 计量单位
	 */
	public Unit getUnit() {
		return unit;
	}
	
	/**
	 * 设置计量单位
	 * 
	 * @param unit 计量单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
		
	/**
	 * 获取单耗
	 * 
	 * @return unitWare 单耗
	 */
	public Double getUnitWare() {
		return unitWare;
	}
	
	/**
	 * 设置单耗
	 * 
	 * @param unitWare 单耗
	 */
	public void setUnitWare(Double unitWare) {
		this.unitWare = unitWare;
	}
		
	/**
	 * 获取损耗
	 * 
	 * @return ware 损耗
	 */
	public Double getWare() {
		return ware;
	}
	
	/**
	 * 设置损耗
	 * 
	 * @param ware 损耗
	 */
	public void setWare(Double ware) {
		this.ware = ware;
	}
		
	/**
	 * 获取帐帐分析表头
	 * 
	 * @return head 帐帐分析表头
	 */
	public EmsAnalyHead getHead() {
		return head;
	}
	
	/**
	 * 设置帐帐分析表头
	 * 
	 * @param head 帐帐分析表头
	 */
	public void setHead(EmsAnalyHead head) {
		this.head = head;
	}

}
