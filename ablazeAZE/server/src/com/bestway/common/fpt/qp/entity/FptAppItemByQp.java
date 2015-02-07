package com.bestway.common.fpt.qp.entity;

import com.bestway.common.CommonUtils;


/**
 * 关封申请单明细
 */
public class FptAppItemByQp  {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	  /** 转入转出标记 0：转出，1：转入 */
    private String inOutFlag = null;    
    

    // //////////////////////////////////////////////
    // 1 <LIST_NO> 序号 Z(8)9 非空 按自然数顺序编号（1，2，3…………）
    // 2 <IN_OUT_NO> 转出序号 Z(8)9 转出方不填，转入方必填
    // 3 <IN_EMS_NO> 转入手册号 X(12) 转出方不填，转入方必填
    // 4 <TR_NO> 商品项号 Z(8)9 非空
    // 5 <CODE_T_S> 商品编码 X(10) 非空
    // 6 <G_NAME> 商品名称 X(50) 非空 同上
    // 7 <G_MODEL> 规格型号 X(50) 同上
    // 8 <UNIT> 计量单位 X(3) 非空 同上
    // 9 <UNIT_1> 法定单位 X(3) 非空 同上
    // 10 <QTY> 申报数量 Z(12)9.9(5) 非空
    // 11 <QTY_1> 法定数量 Z(12)9.9(5)
    // 12 <NOTE> 备注 X(50)
    // //////////////////////////////////////////////
    

    /** 序号 非空 按自然数顺序编号（1，2，3…………） */
    private Integer listNo = null;
    /** 转出序号 转出方不填，转入方必填 */
    private Integer inOutNo = null;
    /** 转入手册号 转出方不填，转入方必填 */
    private String inEmsNo = null;
    /** 商品项号 非空 */
    private Integer trNo = null;
    /** 商品编码 */
    private String codeTs = null;
    /** 商品名称 */
    private String name = null;
    /** 规格型号 */
    private String spec = null;
    /** 计量单位 */
    private String unit = null;
    /** 法定单位 */
    private String unit1 = null;
    /** 申请数量 */
    private Double qty = null;
    /** 法定数量 */
    private Double qty1 = null;
    /** 备用商品编码 */
    private String standComplex = null;
    /** 备注 */
    private String note = null;
	/**
	 * @return the inOutFlag
	 */
	public String getInOutFlag() {
		return inOutFlag;
	}
	
	/**
	 * @return the listNo
	 */
	public Integer getListNo() {
		return listNo;
	}
	/**
	 * @return the inOutNo
	 */
	public Integer getInOutNo() {
		return inOutNo;
	}
	/**
	 * @return the inEmsNo
	 */
	public String getInEmsNo() {
		return inEmsNo;
	}
	/**
	 * @return the trNo
	 */
	public Integer getTrNo() {
		return trNo;
	}
	/**
	 * @return the codeTs
	 */
	public String getCodeTs() {
		return codeTs;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the spec
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @return the unit1
	 */
	public String getUnit1() {
		return unit1;
	}
	/**
	 * @return the qty
	 */
	public Double getQty() {
		return qty;
	}
	/**
	 * @return the qty1
	 */
	public Double getQty1() {
		return qty1;
	}
	/**
	 * @return the standComplex
	 */
	public String getStandComplex() {
		return standComplex;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param inOutFlag the inOutFlag to set
	 */
	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}
	
	/**
	 * @param listNo the listNo to set
	 */
	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}
	/**
	 * @param inOutNo the inOutNo to set
	 */
	public void setInOutNo(Integer inOutNo) {
		this.inOutNo = inOutNo;
	}
	/**
	 * @param inEmsNo the inEmsNo to set
	 */
	public void setInEmsNo(String inEmsNo) {
		this.inEmsNo = inEmsNo;
	}
	/**
	 * @param trNo the trNo to set
	 */
	public void setTrNo(Integer trNo) {
		this.trNo = trNo;
	}
	/**
	 * @param codeTs the codeTs to set
	 */
	public void setCodeTs(String codeTs) {
		this.codeTs = codeTs;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param spec the spec to set
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @param unit1 the unit1 to set
	 */
	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}
	/**
	 * @param qty the qty to set
	 */
	public void setQty(Double qty) {
		this.qty = qty;
	}
	/**
	 * @param qty1 the qty1 to set
	 */
	public void setQty1(Double qty1) {
		this.qty1 = qty1;
	}
	/**
	 * @param standComplex the standComplex to set
	 */
	public void setStandComplex(String standComplex) {
		this.standComplex = standComplex;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
    
    
		
}