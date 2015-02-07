package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 大类别
 * @author xc
 * 
 */
public class VFCategory extends BaseScmEntity{

	private Integer seqNum;	//大类别序号
	
	private String complexCode;		//大类别商品编码
	
	private String complexName;		//大类别商品名称
	
	private String spec;			//大类别商品规格
	
	private String unit;			//大类别单位

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getComplexCode() {
		return complexCode;
	}

	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}

	public String getComplexName() {
		return complexName;
	}

	public void setComplexName(String complexName) {
		this.complexName = complexName;
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
}
