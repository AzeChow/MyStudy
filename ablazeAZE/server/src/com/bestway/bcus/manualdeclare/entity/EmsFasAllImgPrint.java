package com.bestway.bcus.manualdeclare.entity;

import java.util.Comparator;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.common.BaseScmEntity;

public class EmsFasAllImgPrint extends BaseScmEntity implements Comparator,Comparable{
	/**
	 * 成品序号
	 */
	private Integer imSeqNum;
	/**
	 * 料件序号
	 */
	private Integer seqNum;             
	/**
	 * 商品编码
	 */
	private String complex;       
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
	private String unit;             
	/**
	 * 币制
	 */
	private String curr;          
	/**
	 * 允许数量
	 */
	private Double allowAmount;         
	/**
	 * 企业申报单价
	 */
	private Double declarePrice;        
	
	
	public Double getAllowAmount() {
		return allowAmount;
	}
	public void setAllowAmount(Double allowAmount) {
		this.allowAmount = allowAmount;
	}
	public String getComplex() {
		return complex;
	}
	public void setComplex(String complex) {
		this.complex = complex;
	}
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	public Double getDeclarePrice() {
		return declarePrice;
	}
	public void setDeclarePrice(Double declarePrice) {
		this.declarePrice = declarePrice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getImSeqNum() {
		return imSeqNum;
	}
	public void setImSeqNum(Integer imSeqNum) {
		this.imSeqNum = imSeqNum;
	}
	public int compare(Object arg0, Object arg1) {
		try{
			Integer seqNum1 = ((Integer)PropertyUtils.getProperty(arg0,"imSeqNum"));
			Integer seqNum2 = ((Integer)PropertyUtils.getProperty(arg1,"imSeqNum"));
			return seqNum1.compareTo(seqNum2);				
		}
		catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	public int compareTo(Object arg0) {
		try{
			Integer seqNum1 = this.imSeqNum;
			Integer seqNum2 = ((Integer)PropertyUtils.getProperty(arg0,"imSeqNum"));
			return seqNum1.compareTo(seqNum2);	
		}
		catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}
