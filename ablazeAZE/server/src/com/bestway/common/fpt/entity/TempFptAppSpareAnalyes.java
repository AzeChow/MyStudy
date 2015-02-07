/*
 * Created on 2004-10-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.fpt.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * 申请表余量分析 // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class TempFptAppSpareAnalyes implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	
	// ///////////////
	// 1.合同定量
	// 2.可进口量
	// 3.申请表数量
	// 4.累计申请表数量
	// 5.可申请数量(2-4)
	// 6.已送货数量(7+8)
	// 7.已发送数量
	// 8.未发送数量
	// 9.可发送数量(5-7)
	// 10.已转厂数量
	// 11.未转厂数量(6-10)
	// 12.可送货数量(3-7)
	// 13.累计收货数量
	// 14.累计转厂数
	// 15.总未转厂数（期初单+6）
	// /////////////////

	
	/** 转入转出标记 0：转出，1：转入 */
	private String inOutFlag = null;
	/** 客户供应商海关代码 */
	private String tradeCode = null;
	/** 客户供应商 */
	private String tradeName = null;
	/** 商品编码 */
	private String complex = null;
	/** 商品名称 */
	private String name = null;
	/** 规格型号 */
	private String spec = null;
	/** 计量单位 */
	private String unitName = null;

	/** 1.合同定量 */
	private Double f1 = 0.0;
	/** 2.可进口量 */
	private Double f2 = 0.0;
	/** 3.申请表数量 */
	private Double f3 = 0.0;
	/** 4.累计申请表数量 */
	private Double f4 = 0.0;
	/** 5.可申请数量 */
	private Double f5 = 0.0;

	/** 6.已送货数量(7+8)*/
	private Double f6 = 0.0;
	/** 7.已发送数量 */
	private Double f7 = 0.0;
	/** 8.未发送数量 */
	private Double f8 = 0.0;
	/**9.可发送数量(5-7)*/
	private Double f9 = 0.0;
	/**10.已转厂数量*/
	private Double f10 = 0.0;
	/**11.未转厂数量(6-10)*/
	private Double f11 = 0.0;
	/**12.可送货数量*/
	private Double f12 = 0.0;
	/**13.累计收货数量*/
	private Double f13 = 0.0;
	/**14.累计转厂数*/
	private Double f14 = 0.0;
	/**15.总未转厂数（期初单+6）*/
	private Double f15 = 0.0;
	/**
	 * @return the inOutFlag
	 */
	public String getInOutFlag() {
		return inOutFlag;
	}
	/**
	 * @param inOutFlag the inOutFlag to set
	 */
	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}
	/**
	 * @return the tradeCode
	 */
	public String getTradeCode() {
		return tradeCode;
	}
	/**
	 * @param tradeCode the tradeCode to set
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	/**
	 * @return the tradeName
	 */
	public String getTradeName() {
		return tradeName;
	}
	/**
	 * @param tradeName the tradeName to set
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	/**
	 * @return the complex
	 */
	public String getComplex() {
		return complex;
	}
	/**
	 * @param complex the complex to set
	 */
	public void setComplex(String complex) {
		this.complex = complex;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the spec
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * @param spec the spec to set
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	/**
	 * @return the unitName
	 */
	public String getUnitName() {
		return unitName;
	}
	/**
	 * @param unitName the unitName to set
	 */
	public void setUnitName(String unit) {
		this.unitName = unit;
	}
	/**
	 * @return the f1
	 */
	public Double getF1() {
		return f1;
	}
	/**
	 * @param f1 the f1 to set
	 */
	public void setF1(Double f1) {
		this.f1 = f1;
	}
	/**
	 * @return the f2
	 */
	public Double getF2() {
		return f2;
	}
	/**
	 * @param f2 the f2 to set
	 */
	public void setF2(Double f2) {
		this.f2 = f2;
	}
	/**
	 * @return the f3
	 */
	public Double getF3() {
		return f3;
	}
	/**
	 * @param f3 the f3 to set
	 */
	public void setF3(Double f3) {
		this.f3 = f3;
	}
	/**
	 * @return the f4
	 */
	public Double getF4() {
		return f4;
	}
	/**
	 * @param f4 the f4 to set
	 */
	public void setF4(Double f4) {
		this.f4 = f4;
	}
	/**
	 * @return the f5
	 */
	public Double getF5() {
		return getF2()-getF4();
	}
//	/**
//	 * @param f5 the f5 to set
//	 */
//	public void setF5(Double f5) {
//		this.f5 = f5;
//	}
	/**
	 * @return the f6
	 */
	public Double getF6() {
		return getF7()+getF8();
	}
//	/**
//	 * @param f6 the f6 to set
//	 */
//	public void setF6(Double f6) {
//		this.f6 = f6;
//	}
	/**
	 * @return the f7
	 */
	public Double getF7() {
		return f7;
	}
	/**
	 * @param f7 the f7 to set
	 */
	public void setF7(Double f7) {
		this.f7 = f7;
	}
	/**
	 * @return the f8
	 */
	public Double getF8() {
		return f8;
	}
	/**
	 * @param f8 the f8 to set
	 */
	public void setF8(Double f8) {
		this.f8 = f8;
	}
	/**
	 * @return the f9
	 */
	public Double getF9() {
		return getF3()-getF7();
	}
//	/**
//	 * @param f9 the f9 to set
//	 */
//	public void setF9(Double f9) {
//		this.f9 = f9;
//	}
	/**
	 * @return the f10
	 */
	public Double getF10() {
		return f10;
	}
	/**
	 * @param f10 the f10 to set
	 */
	public void setF10(Double f10) {
		this.f10 = f10;
	}
	/**
	 * @return the f11
	 */
	public Double getF11() {
		return getF6()-getF10();
	}
//	/**
//	 * @param f11 the f11 to set
//	 */
//	public void setF11(Double f11) {
//		this.f11 = f11;
//	}
	/**
	 * @return the f12
	 */
	public Double getF12() {
		return getF3()-getF6();
	}
//	/**
//	 * @param f12 the f12 to set
//	 */
//	public void setF12(Double f12) {
//		this.f12 = f12;
//	}
	/**
	 * @return the f13
	 */
	public Double getF13() {
		return f13;
	}
	/**
	 * @param f13 the f13 to set
	 */
	public void setF13(Double f13) {
		this.f13 = f13;
	}
	/**
	 * @return the f14
	 */
	public Double getF14() {
		return f14;
	}
	/**
	 * @param f14 the f14 to set
	 */
	public void setF14(Double f14) {
		this.f14 = f14;
	}
	/**
	 * @return the f15
	 */
	public Double getF15() {
		return f15 + getF6();
	}
	/**
	 * @param f15 the f15 to set
	 */
	public void setF15(Double f15) {
		this.f15 = f15;
	}

	}