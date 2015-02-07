/*
 * Created on 2005-6-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractanalyse.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 存放报关分析－－料件执行情况分析－－进口料件统计
 * 
 * @author ls
 */
public class DzscImpMaterielExeStat implements Serializable{
	
	/**
	 * 商品编码
	 */
    private String complexCode            = null;  
	
	/**
	 * 品名规格
	 */
    private String nameSpec               = null; 
	
	/**
	 * 单位
	 */
    private Unit   unit                   = null; 
	
	/**
	 * 合同总订量
	 */
    private Double contractTotalAmount    = null; 
	
	/**
	 * 总进口量
	 */
    private Double totalImpAmount         = null; 
	
	/**
	 * 大单进口量
	 */
    private Double orderImpAmount         = null; 
	
	/**
	 * 料件进口总量
	 */
    private Double materielImpAmount      = null;  
	
	/**
	 * 转厂进口总量
	 */
    private Double transferImpAmount      = null; 
	
	/**
	 * 退料出口总量
	 */
    private Double backMaterielExpAmount  = null;
	
	/**
	 * 退料复出总量
	 */
    private Double reworkExpAmount        = null;
	
	/**
	 * 退料退换总量
	 */
    private Double backMaterielExchange   = null;
	
	/**
	 * 出口成品使用总量
	 */
    private Double expFinishProductAmount = null; 
	
	/**
	 * 余料情况
	 */
    private Double remainStat             = null;  
	
	/**
	 * 缺料情况
	 */
    private Double lackStat               = null; 
	
	/**
	 * 可进口总量
	 */
    private Double canImpAmount           = null; 
	
	/**
	 * 比例 %
	 */
    private String proportion             = null;
	
	/**
	 * 状态
	 */
    private String state                  = null;
	
	/**
	 * 余料结转转出
	 */
    private Double remainForwordExpAmount   = null;
	
	/**
	 * 余料结转转入
	 */
    private Double remainForwordImpAmount   = null;
   
    
    /**
     * 获取比例 %
     * 
     * @return  proportion 比例 %
     */
    public String getProportion() {
        return proportion;
    }
    
    /**
     * 设置比例 %
     * 
     * @param proportion 比例 %
     */
    public void setProportion(String proportion) {
        this.proportion = proportion;
    }
    
    /**
     * 获取退料退换总量
     * 
     * @return backMaterielExchange 退料退换总量
     */
    public Double getBackMaterielExchange() {
        return backMaterielExchange;
    }
    
    /**
     * 获取退料出口总量
     * 
     * @return backMaterielExpAmount 退料出口总量
     */
    public Double getBackMaterielExpAmount() {
        return backMaterielExpAmount;
    }
    
    /**
     * 获取可进口总量
     * 
     * @return canImpAmount 可进口总量
     */
    public Double getCanImpAmount() {
        return canImpAmount;
    }
    
    /**
     * 获取商品编码
     * 
     * @return  complexCode 商品编码
     */
    public String getComplexCode() {
        return complexCode;
    }
    
    /**
     * 获取合同总订量
     * 
     * @return contractTotalAmount 合同总订量
     */
    public Double getContractTotalAmount() {
        return contractTotalAmount;
    }
    /**
     * 获取出口成品使用总量
     * 
     * @return expFinishProductAmount 出口成品使用总量
     */
    public Double getExpFinishProductAmount() {
        return expFinishProductAmount;
    }
    /**
     * 获取缺料情况
     * 
     * @return lackStat 缺料情况
     */
    public Double getLackStat() {
        return lackStat;
    }
    /**
     * 获取料件进口总量
     * 
     * @return materielImpAmount 料件进口总量
     */
    public Double getMaterielImpAmount() {
        return materielImpAmount;
    }
    /**
     * 获取品名规格
     * 
     * @return  nameSpec 品名规格
     */
    public String getNameSpec() {
        return nameSpec;
    }
    /**
     * 获取大单进口量
     * 
     * @return orderImpAmount 大单进口量
     */
    public Double getOrderImpAmount() {
        return orderImpAmount;
    }
    
    /**
     * 获取余料情况
     * 
     * @return remainStat 余料情况
     */
    public Double getRemainStat() {
        return remainStat;
    }
   
    /**
     * 获取退料复出总量
     * 
     * @return reworkExpAmount 退料复出总量
     */
    public Double getReworkExpAmount() {
        return reworkExpAmount;
    }
    /**
     * 获取状态
     * 
     * @return state 状态
     */
    public String getState() {
        return state;
    }
    /**
     * 获取总进口量
     * 
     * @return totalImpAmount 总进口量
     */
    public Double getTotalImpAmount() {
        return totalImpAmount;
    }
    /**
     * 获取转厂进口总量
     * 
     * @return transferImpAmount 转厂进口总量
     */
    public Double getTransferImpAmount() {
        return transferImpAmount;
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
     * 设置退料退换总量
     * 
     * @param backMaterielExchange 退料退换总量
     */
    public void setBackMaterielExchange(Double backMaterielExchange) {
        this.backMaterielExchange = backMaterielExchange;
    }
    /**
     * 设置退料出口总量
     * 
     * @param backMaterielExpAmount 退料出口总量
     */
    public void setBackMaterielExpAmount(Double backMaterielExpAmount) {
        this.backMaterielExpAmount = backMaterielExpAmount;
    }
    /**
     * 设置可进口总量
     * 
     * @param canImpAmount 可进口总量
     */
    public void setCanImpAmount(Double canImpAmount) {
        this.canImpAmount = canImpAmount;
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
     * 设置合同总订量
     * 
     * @param contractTotalAmount 合同总订量
     */
    public void setContractTotalAmount(Double contractTotalAmount) {
        this.contractTotalAmount = contractTotalAmount;
    }
    /**
     * 设置出口成品使用总量
     * 
     * @param expFinishProductAmount 出口成品使用总量
     */
    public void setExpFinishProductAmount(Double expFinishProductAmount) {
        this.expFinishProductAmount = expFinishProductAmount;
    }
    /**
     * 设置缺料情况
     * 
     * @param lackStat 缺料情况
     */
    public void setLackStat(Double lackStat) {
        this.lackStat = lackStat;
    }
    /**
     * 设置料件进口总量
     * 
     * @param materielImpAmount 料件进口总量
     */
    public void setMaterielImpAmount(Double materielImpAmount) {
        this.materielImpAmount = materielImpAmount;
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
     * 设置大单进口量
     * 
     * @param orderImpAmount 大单进口量
     */
    public void setOrderImpAmount(Double orderImpAmount) {
        this.orderImpAmount = orderImpAmount;
    }
   
    /**
     * 设置余料情况
     * 
     * @param remainStat 余料情况
     */
    public void setRemainStat(Double remainStat) {
        this.remainStat = remainStat;
    }
    
    /**
     * 获取余料结转转出
     * 
     * @return remainForwordExpAmount 余料结转转出
     */
    public Double getRemainForwordExpAmount() {
        return remainForwordExpAmount;
    }
    /**
     * 获取余料结转转入
     * 
     * @return remainForwordImpAmount 余料结转转入
     */
    public Double getRemainForwordImpAmount() {
        return remainForwordImpAmount;
    }
    
    /**
     * 设置余料结转转出
     * 
     * @param remainForwordExpAmount 余料结转转出
     */
    public void setRemainForwordExpAmount(Double remainForwordExpAmount) {
        this.remainForwordExpAmount = remainForwordExpAmount;
    }
    /**
     * 设置余料结转转入
     * 
     * @param remainForwordImpAmount 余料结转转入
     */
    public void setRemainForwordImpAmount(Double remainForwordImpAmount) {
        this.remainForwordImpAmount = remainForwordImpAmount;
    }
    /**
     * 设置退料复出总量
     * 
     * @param reworkExpAmount 退料复出总量
     */
    public void setReworkExpAmount(Double reworkExpAmount) {
        this.reworkExpAmount = reworkExpAmount;
    }
    /**
     * 设置状态
     * 
     * @param state 状态
     */
    public void setState(String state) {
        this.state = state;
    }
    /**
     * 设置总进口量
     * 
     * @param totalImpAmount 总进口量
     */
    public void setTotalImpAmount(Double totalImpAmount) {
        this.totalImpAmount = totalImpAmount;
    }
    /**
     * 设置转厂进口总量
     * 
     * @param transferImpAmount 转厂进口总量
     */
    public void setTransferImpAmount(Double transferImpAmount) {
        this.transferImpAmount = transferImpAmount;
    }
    /**
     * 设置单位
     * 
     * @param unit 单位
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
