package com.bestway.waijing.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 临时表--凭证
 * @author xxmlovelinyuexiu
 *
 */
public class TempPingZheng extends BaseScmEntity  {
	
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID(); 
	
	private Integer seqNum;//序号
	private String name;//名称
	private String spec;//规格
	private String unit;//单位
	private String code;//编码
	private Integer type;//类型
	private String curr;//币制
	private Double unitPrice;//单价
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	
   
}
