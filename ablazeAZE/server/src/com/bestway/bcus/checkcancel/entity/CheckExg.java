/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 存放中期核查－－中期核查成品资料
 */
public class CheckExg extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
    /**
	 * 中期核查表头
	 */
	private CheckHead checkHead;
    	
    /**
	 * 序号
	 */
	private String seqNum;  
    	
    /**
	 * 版本号
	 */
	private String version;
    	
		
    /**
	 * 商品编码
	 */
	private Complex complex; 
		 
		
    /**
	 * 计量单位
	 */
	private Unit unit;    	
	/**
	 * 料号
	 */
	private String ptNo;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 规格
	 */
	private String spec;
    	
    /**
	 * 成品库存数量
	 */
	private Double materStock;  
    	
    /**
	 * 成品在途数量
	 */
	private Double materByWay;  
    	
    /**
	 * 成品转出未报数量
	 */
	private Double turnOutNoReport; 
    	
    /**
	 * 成品入库数量
	 */
	private Double materInWare;   
    
    	
    /**
	 * 本期成品出库数量
	 */
	private Double thisMaterOutWare;
    	
    /**
	 * 本期成品内销数量
	 */
	private Double thisMaterCancel; 	
    
    	
    /**
	 * 本期成品放弃数量
	 */
	private Double thisThrow;    
    
		
    /**
	 * 半成品数量
	 */
	private Double halfExg;       
		
    /**
	 *成品退换
	 */
	private Double materExitChange;
	
		
    /**
	 * 残次品数量
	 */
	private Double otherExg;      
		
    /**
	 * 废品数量
	 */
	private Double depose;      
	

	/**
	 * 获取中期核查表头
	 * 
	 * @return checkHead 中期核查表头
	 */
	public CheckHead getCheckHead() {
		return checkHead;
	}
		
	/**
	 * 设置中期核查表头
	 * 
	 * @param checkHead 中期核查表头
	 */
	public void setCheckHead(CheckHead checkHead) {
		this.checkHead = checkHead;
	}
	
	/**
	 * 获取商品编码
	 * 
	 * @return complex 商品编码
	 */
	public Complex getComplex() {
		return complex;
	}
		
	/**
	 * 设置商品编码
	 * 
	 * @param complex 商品编码
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}
	
	/**
	 * 获取废品数量
	 * 
	 * @return depose 废品数量
	 */
	public Double getDepose() {
		return depose;
	}
		
	/**
	 * 设置废品数量
	 * 
	 * @param depose 废品数量
	 */
	public void setDepose(Double depose) {
		this.depose = depose;
	}
	
	/**
	 * 获取半成品数量
	 * 
	 * @return halfExg 半成品数量
	 */
	public Double getHalfExg() {
		return halfExg;
	}
		
	/**
	 * 设置半成品数量
	 * 
	 * @param halfExg 半成品数量
	 */
	public void setHalfExg(Double halfExg) {
		this.halfExg = halfExg;
	}
	
	/**
	 * 获取成品在途数量
	 * 
	 * @return materByWay 成品在途数量
	 */
	public Double getMaterByWay() {
		return materByWay;
	}
		
	/**
	 * 设置成品在途数量
	 * 
	 * @param materByWay 成品在途数量
	 */
	public void setMaterByWay(Double materByWay) {
		this.materByWay = materByWay;
	}
	
	/**
	 * 获取成品退换
	 * 
	 * @return materExitChange 成品退换
	 */
	public Double getMaterExitChange() {
		return materExitChange;
	}
		
	/**
	 * 设置成品退换
	 * 
	 * @param materExitChange 成品退换
	 */
	public void setMaterExitChange(Double materExitChange) {
		this.materExitChange = materExitChange;
	}
	
	/**
	 * 获取成品入库数量
	 * 
	 * @return materInWare 成品入库数量
	 */
	public Double getMaterInWare() {
		return materInWare;
	}
		
	/**
	 * 设置成品入库数量
	 * 
	 * @param materInWare 成品入库数量
	 */
	public void setMaterInWare(Double materInWare) {
		this.materInWare = materInWare;
	}
	
	/**
	 * 获取成品库存数量
	 * 
	 * @return materStock 成品库存数量
	 */
	public Double getMaterStock() {
		return materStock;
	}
		
	/**
	 * 设置成品库存数量
	 * 
	 * @param materStock 成品库存数量
	 */
	public void setMaterStock(Double materStock) {
		this.materStock = materStock;
	}
	
	
	/**
	 * 获取残次品数量
	 * 
	 * @return otherExg 残次品数量
	 */
	public Double getOtherExg() {
		return otherExg;
	}
		
	/**
	 * 设置残次品数量
	 * 
	 * @param otherExg 残次品数量
	 */
	public void setOtherExg(Double otherExg) {
		this.otherExg = otherExg;
	}
	
	/**
	 * 获取序号（归并前，归并后十码序号）
	 * 
	 * @return seqNum 序号（归并前，归并后十码序号）
	 */
	public String getSeqNum() {
		return seqNum;
	}
		
	/**
	 * 设置序号（归并前，归并后十码序号）
	 * 
	 * @param seqNum 序号（归并前，归并后十码序号）
	 */
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	
	/**
	 * 获取本期成品内销数量
	 * 
	 * @return thisMaterCancel 本期成品内销数量
	 */
	public Double getThisMaterCancel() {
		return thisMaterCancel;
	}
		
	/**
	 * 设置本期成品内销数量
	 * 
	 * @param thisMaterCancel 本期成品内销数量
	 */
	public void setThisMaterCancel(Double thisMaterCancel) {
		this.thisMaterCancel = thisMaterCancel;
	}
	
	/**
	 * 获取本期成品出库数量
	 * 
	 * @return thisMaterOutWare 本期成品出库数量
	 */
	public Double getThisMaterOutWare() {
		return thisMaterOutWare;
	}
		
	/**
	 * 设置本期成品出库数量
	 * 
	 * @param thisMaterOutWare 本期成品出库数量
	 */
	public void setThisMaterOutWare(Double thisMaterOutWare) {
		this.thisMaterOutWare = thisMaterOutWare;
	}
	
	/**
	 * 获取本期成品放弃数量
	 * 
	 * @return thisThrow 本期成品放弃数量
	 */
	public Double getThisThrow() {
		return thisThrow;
	}
		
	/**
	 * 设置本期成品放弃数量
	 * 
	 * @param thisThrow 本期成品放弃数量
	 */
	public void setThisThrow(Double thisThrow) {
		this.thisThrow = thisThrow;
	}
	
	/**
	 * 获取成品转出未报数量
	 * 
	 * @return turnOutNoReport 成品转出未报数量
	 */
	public Double getTurnOutNoReport() {
		return turnOutNoReport;
	}
		
	/**
	 * 设置成品转出未报数量
	 * 
	 * @param turnOutNoReport 成品转出未报数量
	 */
	public void setTurnOutNoReport(Double turnOutNoReport) {
		this.turnOutNoReport = turnOutNoReport;
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
	 * 获取版本号
	 * 
	 * @return version 版本号
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * 设置版本号
	 * 
	 * @param version 版本号
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
}
