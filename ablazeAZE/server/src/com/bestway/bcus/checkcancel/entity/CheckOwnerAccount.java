/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 存放中期核查－－自用中期核查计算表－－核查计算表资料
 */
public class CheckOwnerAccount extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
	/**
	 * 参数
	 */
	private CheckParameter head = null; 
	/**
	 * 料号
	 */ 
    private String ptNo=null;
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
	 * 原料在途数量
	 */
	private Double materielOnwayNum = null;
	   
	/**
	 * 成品折料数量
	 */
	private Double exgConvertImgNum = null;
	   
	/**
	 * 转进未报数量
	 */
	private Double tranNoCustomsNum = null; 
	
	   
	/**
	 * 在线数量
	 */
	private Double onLineNum = null;
	   
	/**
	 * 半成品数量
	 */
	private Double halfExgConvertImgNum = null; 
	   
	/**
	 * 废料数量
	 */
	private Double flotsamNum = null; 
	
	   
	/**
	 * 原料入库数量
	 */
	private Double importStockNum = null;
	   
	/**
	 * 原料出库数量
	 */
	private Double exportStockNum = null;
	
	   
	/**
	 * 结转送货单据
	 */
	private Double transImportDoods = null;

	/**
	 * 获取成品折料数量
	 * 
	 * @return exgConvertImgNum 成品折料数量
	 */
	public Double getExgConvertImgNum() {
		return exgConvertImgNum;
	}


	/**
	 * 设置成品折料数量
	 * 
	 * @param exgConvertImgNum 成品折料数量
	 */
	public void setExgConvertImgNum(Double exgConvertImgNum) {
		this.exgConvertImgNum = exgConvertImgNum;
	}


	/**
	 * 获取原料出库数量
	 * 
	 * @return exportStockNum 原料出库数量
	 */
	public Double getExportStockNum() {
		return exportStockNum;
	}


	/**
	 * 设置原料出库数量
	 * 
	 * @param exportStockNum 原料出库数量
	 */
	public void setExportStockNum(Double exportStockNum) {
		this.exportStockNum = exportStockNum;
	}


	/**
	 * 获取废料数量
	 * 
	 * @return flotsamNum 废料数量
	 */
	public Double getFlotsamNum() {
		return flotsamNum;
	}


	/**
	 * 设置废料数量
	 * 
	 * @param flotsamNum 废料数量
	 */
	public void setFlotsamNum(Double flotsamNum) {
		this.flotsamNum = flotsamNum;
	}


	/**
	 * 获取半成品数量
	 * 
	 * @return halfExgConvertImgNum 半成品数量
	 */
	public Double getHalfExgConvertImgNum() {
		return halfExgConvertImgNum;
	}


	/**
	 * 设置半成品数量
	 * 
	 * @param halfExgConvertImgNum 半成品数量
	 */
	public void setHalfExgConvertImgNum(Double halfExgConvertImgNum) {
		this.halfExgConvertImgNum = halfExgConvertImgNum;
	}


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
	 * @param head 参数
	 */
	public void setHead(CheckParameter head) {
		this.head = head;
	}


	/**
	 * 获取原料入库数量
	 * 
	 * @return importStockNum 原料入库数量
	 */
	public Double getImportStockNum() {
		return importStockNum;
	}


	/**
	 * 设置原料入库数量
	 * 
	 * @param importStockNum 原料入库数量
	 */
	public void setImportStockNum(Double importStockNum) {
		this.importStockNum = importStockNum;
	}


	/**
	 * 获取原料在途数量
	 * 
	 * @return materielOnwayNum 原料在途数量
	 */
	public Double getMaterielOnwayNum() {
		return materielOnwayNum;
	}


	/**
	 * 设置原料在途数量
	 * 
	 * @param materielOnwayNum 原料在途数量
	 */
	public void setMaterielOnwayNum(Double materielOnwayNum) {
		this.materielOnwayNum = materielOnwayNum;
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
	 * @param materielStockNum 原料库存数量
	 */
	public void setMaterielStockNum(Double materielStockNum) {
		this.materielStockNum = materielStockNum;
	}


	/**
	 * 获取在线数量
	 * 
	 * @return onLineNum 在线数量
	 */
	public Double getOnLineNum() {
		return onLineNum;
	}


	/**
	 * 设置在线数量
	 * 
	 * @param onLineNum 在线数量
	 */
	public void setOnLineNum(Double onLineNum) {
		this.onLineNum = onLineNum;
	}

	

	/**
	 * 获取转进未报数量
	 * 
	 * @return tranNoCustomsNum 转进未报数量
	 */
	public Double getTranNoCustomsNum() {
		return tranNoCustomsNum;
	}


	/**
	 * 设置转进未报数量
	 * 
	 * @param tranNoCustomsNum 转进未报数量
	 */
	public void setTranNoCustomsNum(Double tranNoCustomsNum) {
		this.tranNoCustomsNum = tranNoCustomsNum;
	}


	/**
	 * 获取结转送货单据
	 * 
	 * @return transImportDoods 结转送货单据
	 */
	public Double getTransImportDoods() {
		return transImportDoods;
	}


	/**
	 * 设置结转送货单据
	 * 
	 * @param transImportDoods 结转送货单据
	 */
	public void setTransImportDoods(Double transImportDoods) {
		this.transImportDoods = transImportDoods;
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
