package com.bestway.customs.common.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;

/**
 * 基础类
 * 
 * @author refdom
 *
 */
public class BaseEmsExg extends BaseScmEntity{
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
	 * 企业申报单价
	 */
	private Double declarePrice;    
	/**
	 * 备注
	 */
	private String note;            
    /**
     * 是否禁止
     */
    private Boolean isForbid;       
	
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
	 * 取得企业申报单价
	 * @return 企业申报单价
	 */
	public Double getDeclarePrice() {
		return declarePrice;
	}
	/**
	 * 设置企业申报单价
	 * @param declarePrice 企业申报单价
	 */
	public void setDeclarePrice(Double declarePrice) {
		this.declarePrice = declarePrice;
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
	 * 取得备注内容
	 * @return 备注
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 填写备注内容
	 * @param note 备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 取得成品序号
	 * @return 成品序号
	 */
	public Integer getSeqNum() {
		return seqNum;
	}
	/**
	 * 设置成品序号
	 * @param seqNum 成品序号
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	/**
	 * 取得规格型号
	 * @return 规格型号
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
	 * @param unit 计量单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
    /**
     * 判断是否禁止
     * @return 是否禁止
     */
    public Boolean getIsForbid() {
        return isForbid;
    }
    /**
     * 设置是否禁止标志 
     * @param isForbid 是否禁止
     */
    public void setIsForbid(Boolean isForbid) {
        this.isForbid = isForbid;
    }
	
}
