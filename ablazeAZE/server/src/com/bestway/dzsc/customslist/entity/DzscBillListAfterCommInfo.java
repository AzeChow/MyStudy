package com.bestway.dzsc.customslist.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 报关清单归并后商品信息
 * 
 * @author Administrator Created on 2004-7-26
 */
public class DzscBillListAfterCommInfo extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
     * 报关清单表头
     */
	private DzscCustomsBillList billList;

    
    /**
     * 手册序号
     */
	private Integer emsSerialNo;

    
    /**
     * 商品信息
     */
	private Complex complex;

    
    /**
     * 商品名称
     */
	private String complexName;

    
    /**
     * 商品规格
     */
	private String complexSpec;

    
    /**
     * 申报数量
     */
	private Double declaredAmount;

    
    /**
     * 法定数量
     */	
	private Double legalAmount;

    
    /**
     * 第二法定数量
     */
	private Double secondLegalAmount;

    
    /**
     * 毛重
     */
	private Double grossWeight;

    
    /**
     * 净重
     */
	private Double netWeight;

    
    /**
     * 申报单位
     */
	private Unit unit;

    
//    /**
//     * 法定单位
//     */
//	private Unit legalUnit;
//
//    
//    /**
//     * 第二法定单位
//     */
//	private Unit secondLegalUnit;

    
    /**
     * 备注
     */
	private String memos;

	/**
	 * 获取报关清单表头
	 *
	 * @return billList 报关清单表头
	 */
	public DzscCustomsBillList getBillList() {
		return billList;
	}

	/**
	 * 设置报关清单表头
	 * 
	 * @param billList 报关清单表头
	 */
	public void setBillList(DzscCustomsBillList billList) {
		this.billList = billList;
	}

	/**
	 * 获取商品信息
	 * 
	 * @return complex 商品信息
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * 设置商品信息
	 * 
	 * @param complex 商品信息
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * 获取申报数量
	 * 
	 * @return declaredAmount 申报数量
	 */
	public Double getDeclaredAmount() {
		return declaredAmount;
	}

	/**
	 * 设置申报数量
	 * 
	 * @param declaredAmount 申报数量
	 */
	public void setDeclaredAmount(Double declaredAmount) {
		this.declaredAmount = declaredAmount;
	}

	/**
	 * 获取手册序号
	 * 
	 * @return emsSerialNo 手册序号
	 */
	public Integer getEmsSerialNo() {
		return emsSerialNo;
	}

	/**
	 * 设置手册序号
	 * 
	 * @param emsSerialNo 手册序号
	 */
	public void setEmsSerialNo(Integer emsSerialNo) {
		this.emsSerialNo = emsSerialNo;
	}

	/**
	 * 获取毛重
	 * 
	 * @return grossWeight 毛重
	 */
	public Double getGrossWeight() {
		return grossWeight;
	}

	/**
	 * 设置毛重
	 * 
	 * @param grossWeight 毛重
	 */
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	/**
	 * 获取法定数量
	 * 
	 * @return legalAmount 法定数量
	 */
	public Double getLegalAmount() {
		return legalAmount;
	}

	/**
	 * 设置法定数量
	 * 
	 * @param legalAmount 法定数量
	 */
	public void setLegalAmount(Double legalAmount) {
		this.legalAmount = legalAmount;
	}

	/**
	 * 获取备注
	 * 
	 * @return memo 备注
	 */
	public String getMemos() {
		return memos;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo 备注
	 */
	public void setMemos(String memo) {
		this.memos = memo;
	}

	/**
	 * 获取净重
	 * 
	 * @return netWeight 净重
	 */
	public Double getNetWeight() {
		return netWeight;
	}

	/**
	 * 设置净重
	 * 
	 * @param netWeight 净重
	 */
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	/**
	 * 获取第二法定数量
	 * 
	 * @return secondLegalAmount 第二法定数量
	 */
	public Double getSecondLegalAmount() {
		return secondLegalAmount;
	}

	/**
	 * 设置第二法定数量
	 * 
	 * @param secondLegalAmount 第二法定数量
	 */
	public void setSecondLegalAmount(Double secondLegalAmount) {
		this.secondLegalAmount = secondLegalAmount;
	}

	
		/**
		 * 获取商品名称
		 * 
		 * @return  complexName 商品名称
		 */
	public String getComplexName() {
		return complexName;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param complexName 商品名称
	 */
	public void setComplexName(String complexName) {
		this.complexName = complexName;
	}

	/**
	 * 获取商品规格
	 * 
	 * @return complexSpec 商品规格
	 */
	public String getComplexSpec() {
		return complexSpec;
	}

	/**
	 * 设置商品规格
	 * 
	 * @param complexSpec 商品规格
	 */
	public void setComplexSpec(String complexSpec) {
		this.complexSpec = complexSpec;
	}

//	/**
//	 * 获取法定单位
//	 * 
//	 * @return legalUnit 法定单位
//	 */
//	public Unit getLegalUnit() {
//		return legalUnit;
//	}
//
//	/**
//	 * 设置法定单位
//	 * 
//	 * @param legalUnit 法定单位
//	 */
//	public void setLegalUnit(Unit legalUnit) {
//		this.legalUnit = legalUnit;
//	}
//
//	/**
//	 * 获取第二法定单位
//	 * 
//	 * @return secondLegalUnit 第二法定单位
//	 */
//	public Unit getSecondLegalUnit() {
//		return secondLegalUnit;
//	}
//
//	/**
//	 * 设置第二法定单位
//	 * 
//	 * @param secondLegalUnit 第二法定单位
//	 */
//	public void setSecondLegalUnit(Unit secondLegalUnit) {
//		this.secondLegalUnit = secondLegalUnit;
//	}

	/**
	 * 获取申报单位
	 * 
	 * @return  unit 申报单位
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * 设置申报单位
	 * 
	 * @param unit 申报单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	/**
	 * 附加编码
	 * 
	 * @return
	 */
	public String getComplexSCode() {
		if (this.getComplex() == null
				|| this.getComplex().getCode().length() <= 8) {
			return "";
		} else {
			return this.getComplex().getCode().substring(8,
					this.getComplex().getCode().length());
		}
	}
}