/*
 * Created on 2005-4-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.checkcancel.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放电子手册合同核销成品资料
 * 
 * @author Administrator
 */
public class DzscContractExgCav  extends BaseScmEntity{
    private static final long serialVersionUID  = CommonUtils
    .getSerialVersionUID();
	/**
	 *  是否选中
	 */
	private Boolean isSelected=false;
    /**
     * 核销表头
     */
    private DzscContractCav contractCav;

    /**
     * 成品序号
     */
	private Integer seqNum;

    /**
     * 商品编码
     */
	private Complex complex;

    /**
     * 商品名称
     */
	private String name; 
    
    /**
     * 规格型号
     */
	private String spec;
    
    /**
     * 计量单位
     */
	private Unit unit; 
    
    /**
     * 出口总数
     */
	private Double expTotal;
    
    /**
     * 成品出口数
     */
	private Double directExport;
    
    /**
     * 结转出口数量
     */
	private Double transferExpAmount;
    
    /**
     * 退厂返工数量
     */
	private Double backFactoryRework;
    
    /**
     * 返工复出数量
     */
	private Double reworkExport;
	/**
	 * 企业库存数量
	 */
	private Double stockAmount;
    /**
     * 是海关
     */
	private Boolean isCustoms = false;
	
	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * 获取是海关,还是自用,true代表是海关用
	 * 
	 * @return isCustoms 是海关，还是自用 true代表是海关用
	 */
	public Boolean getIsCustoms() {
		return isCustoms;
	}
	
	/**
	 * 设置是海关，还是自用 true代表是海关用
	 * 
	 * @param isCustoms 是海关，还是自用 true代表是海关用
	 */
	public void setIsCustoms(Boolean isCustoms) {
		this.isCustoms = isCustoms;
	}
	
	/**
	 * 获取退厂返工数量
	 * 
	 * @return backFactoryRework 退厂返工数量
	 */
	public Double getBackFactoryRework() {
		return backFactoryRework;
	}
	
	/**
	 * 设置退厂返工数量
	 * 
	 * @param backFactoryRework 退厂返工数量
	 */
	public void setBackFactoryRework(Double backFactoryRework) {
		this.backFactoryRework = backFactoryRework;
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
	 * 获取出口总数
	 * 
	 * @return expTotal 出口总数
	 */
	public Double getExpTotal() {
		return expTotal;
	}
	
	/**
	 * 设置出口总数
	 * 
	 * @param expTotal 出口总数
	 */
	public void setExpTotal(Double expTotal) {
		this.expTotal = expTotal;
	}
	
	/**
	 * 获取商品名称
	 * 
	 * @return name 商品名称
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置商品名称
	 * 
	 * @param name 商品名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取返工复出数量
	 * 
	 * @return reworkExport 返工复出数量
	 */
	public Double getReworkExport() {
		return reworkExport;
	}
	
	/**
	 * 设置返工复出数量
	 * 
	 * @param reworkExport 返工复出数量
	 */
	public void setReworkExport(Double reworkExport) {
		this.reworkExport = reworkExport;
	}
	
	/**
	 * 获取成品序号
	 * 
	 * @return seqNum 成品序号
	 */
	public Integer getSeqNum() {
		return seqNum;
	}
	
	/**
	 * 设置成品序号
	 * 
	 * @param seqNum 成品序号
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	
	/**
	 * 获取规格型号
	 * 
	 * @return spec 规格型号
	 */
	public String getSpec() {
		return spec;
	}
	
	/**
	 * 设置规格型号
	 * 
	 * @param spec The spec to set.
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	/**
	 * 获取结转出口数量
	 * 
	 * @return transferExpAmount 结转出口数量
	 */
	public Double getTransferExpAmount() {
		return transferExpAmount;
	}
	
	/**
	 * 设置结转出口数量
	 * 
	 * @param transferExpAmount 结转出口数量
	 */
	public void setTransferExpAmount(Double transferExpAmount) {
		this.transferExpAmount = transferExpAmount;
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
	 * 获取核销表头
	 * 
	 * @return contractCav 核销表头
	 */
	public DzscContractCav getContractCav() {
		return contractCav;
	}
	
	/**
	 * 设置核销表头
	 * 
	 * @param contractCav 核销表头
	 */
	public void setContractCav(DzscContractCav contractCav) {
		this.contractCav = contractCav;
	}
	
	/**
	 * 获取成品出口数
	 * 
	 * @return directExport 成品出口数
	 */
	public Double getDirectExport() {
		return directExport;
	}
	
	/**
	 * 设置directExport
	 * 
	 * @param directExport 成品出口数
	 */
	public void setDirectExport(Double directExport) {
		this.directExport = directExport;
	}

	public Double getStockAmount() {
		if(this.stockAmount==null){
			return 0.0;
		}
		return stockAmount;
	}

	public void setStockAmount(Double stockAmount) {
		this.stockAmount = stockAmount;
	}
}
