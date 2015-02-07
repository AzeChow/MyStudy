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
 * 存放滚动核销－－帐帐分析－－报关数据分析－－成品进出口－－全部折成料件
 */
public class EmsBgExgBg extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
	 * 帐帐分析表头
	 */
	private EmsAnalyHead head = null;
	/**
	 * 报关数据分析－成品进出口
	 */
	private EmsBgExg exg = null;
	   
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
	 * 型号规格
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
	private Double totalWare = null; 
	
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
	 * 获取报关数据分析－成品进出口
	 * 
	 * @return exg 报关数据分析－成品进出口
	 */
	public EmsBgExg getExg() {
		return exg;
	}
		
	
	
	public EmsAnalyHead getHead() {
		return head;
	}

	public void setHead(EmsAnalyHead head) {
		this.head = head;
	}

	/**
	 * 设置报关数据分析－成品进出口
	 * 
	 * @param exg 报关数据分析－成品进出口
	 */
	public void setExg(EmsBgExg exg) {
		this.exg = exg;
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
	 * @param seqNum 
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
		
	/**
	 * 获取型号规格
	 * 
	 * @return spec 型号规格
	 */
	public String getSpec() {
		return spec;
	}
		
	/**
	 * 设置型号规格
	 * 
	 * @param spec 型号规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
		
	/**
	 * 获取总耗用
	 * 
	 * @return totalWare 总耗用
	 */
	public Double getTotalWare() {
		return totalWare;
	}
		
	/**
	 * 设置总耗用
	 * 
	 * @param totalWare 总耗用
	 */
	public void setTotalWare(Double totalWare) {
		this.totalWare = totalWare;
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
}
