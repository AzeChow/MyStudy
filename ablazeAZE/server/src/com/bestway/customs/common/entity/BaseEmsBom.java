package com.bestway.customs.common.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;

/**
 * EMSBOM信息
 * 
 * @author refdom
 *
 */
public class BaseEmsBom extends BaseScmEntity{
	/**
	 * 料件序号
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
	 * 单耗
	 */
	private Double unitWear;      
	/**
	 * 损耗
	 */
	private Double wear;          
	/**
	 * 单价
	 */
	private Double price;         
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 取得商品编码
	 * @return 商品编码
	 */
	public Complex getComplex() {
		return complex;
	}
	/**
	 * 设置商品编码
	 * @param complex 商品编码
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}
	/**
	 * 取得商品名称
	 * @return 商品名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置商品名称
	 * @param name 商品名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 取得备注
	 * @return 备注
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置备注
	 * @param note 备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 取得单价
	 * @return 单价
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * 设置单价
	 * @param price 单价
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	
	/**
	 * 取得规格型号
	 * @return  规格型号
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * 设置规格型号
	 * @param spec 规格型号
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	/**
	 * 取得计量单位
	 * @return 计量单位
	 */
	public Unit getUnit() {
		return unit;
	}
	/**
	 * 设置计量单位
	 * @param unit
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	/**
	 * 取得单耗
	 * @return 单耗
	 */
	public Double getUnitWear() {
		return unitWear;
	}
	/**
	 * 设置单耗
	 * @param unitWear 单耗
	 */
	public void setUnitWear(Double unitWear) {
		this.unitWear = unitWear;
	}
	/**
	 * 取得损耗
	 * @return 损耗
	 */
	public Double getWear() {
		return wear;
	}
	/**
	 * 设置损耗
	 * @param wear 损耗
	 */
	public void setWear(Double wear) {
		this.wear = wear;
	}
	
	/**
	 * 取得料件序号
	 * @return 料件序号
	 */
	public Integer getSeqNum() {
		return seqNum;
	}
	/**
	 * 设置料件序号
	 * @param seqNum 料件序号
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
}
