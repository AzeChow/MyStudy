/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 中期核查－－工厂盘点－－料件
 */
public class ImportDataForCheckImg extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
     * 料号
     */
    private String ptNo = null;
    /**
     * 原料库存数量
     */
	private Double materStock; 
	/**
	 * 原料在途数量
	 */
	private Double materByWay; 
	/**
	 * 成品折料数量
	 */
	private Double passExgUse;      
	/**
	 * 在线数量
	 */
	private Double onlines;        
	/**
	 * 废品，残次品折料数量
	 */
	private Double otherConvert;   
	/**
	 * 废料数量
	 */
	private Double depose;         
	/**
	 * 转进未报数量
	 */
	
	private Double turnInNoReport;  
    
    
    
    
    
   
    
   

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public Double getDepose() {
		return depose;
	}

	public void setDepose(Double depose) {
		this.depose = depose;
	}


	public Double getMaterByWay() {
		return materByWay;
	}

	public void setMaterByWay(Double materByWay) {
		this.materByWay = materByWay;
	}

	public Double getMaterStock() {
		return materStock;
	}

	public void setMaterStock(Double materStock) {
		this.materStock = materStock;
	}

	public Double getOnlines() {
		return onlines;
	}

	public void setOnlines(Double onlines) {
		this.onlines = onlines;
	}

	public Double getPassExgUse() {
		return passExgUse;
	}

	public void setPassExgUse(Double passExgUse) {
		this.passExgUse = passExgUse;
	}

	public Double getTurnInNoReport() {
		return turnInNoReport;
	}

	public void setTurnInNoReport(Double turnInNoReport) {
		this.turnInNoReport = turnInNoReport;
	}

	public Double getOtherConvert() {
		return otherConvert;
	}

	public void setOtherConvert(Double otherConvert) {
		this.otherConvert = otherConvert;
	}

	
}
