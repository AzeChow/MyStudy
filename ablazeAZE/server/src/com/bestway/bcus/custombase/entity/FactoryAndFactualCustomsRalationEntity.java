package com.bestway.bcus.custombase.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

public class FactoryAndFactualCustomsRalationEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 实际报关名称
	 */
	private String hsCusName;
	
	/**
	 * 实际报关规格
	 */
	private String hsCusSpec;
	
	/**
	 * 实际报关单位
	 */
	private Unit hsCusUnit; 
	
	/**
	 * 实际报关商品编码
	 */
	private Complex complex;
	
	/**
	 * 料件料号
	 */
	private String materielNo;

	public String getHsCusName() {
		return hsCusName;
	}

	public void setHsCusName(String hsCusName) {
		this.hsCusName = hsCusName;
	}

	public String getHsCusSpec() {
		return hsCusSpec;
	}

	public void setHsCusSpec(String hsCusSpec) {
		this.hsCusSpec = hsCusSpec;
	}

	public Unit getHsCusUnit() {
		return hsCusUnit;
	}

	public void setHsCusUnit(Unit hsCusUnit) {
		this.hsCusUnit = hsCusUnit;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getMaterielNo() {
		return materielNo;
	}

	public void setMaterielNo(String materielNo) {
		this.materielNo = materielNo;
	}

	
	
}
