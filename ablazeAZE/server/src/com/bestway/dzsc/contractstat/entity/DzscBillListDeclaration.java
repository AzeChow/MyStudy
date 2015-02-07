package com.bestway.dzsc.contractstat.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 存放统计报表中的进口料件、出口成品清单明细表的资料
 * 
 * @author yp
 */
public class DzscBillListDeclaration implements Serializable {
	
	/**
	 * 清单申报日期
	 */
	private Date listDeclareDate; 
	
	/**
	 * 进出口类型
	 */
	private Integer listImpExpType; 
	
	/**
	 * 清单号码
	 */
	private String listNo; 
	
	/**
	 * 清单货号
	 */
	private String listPtNo; 
	
	/**
	 * 清单货品名称
	 */
	private String listPtName; 
	
	/**
	 * 清单货品规格
	 */
	private String listPtSpec; 
	
	/**
	 * 清单企业申报单价
	 */
	private Double declaredPrice; 
	
	/**
	 * 报关单位
	 */
	private Unit listPtUnit; 
	
	/**
	 * 清单申报数量
	 */
	private Double declaredAmount; 
	
	/**
	 * 报关单号
	 */
	private String customsDeclarationCode; 
	
	/**
	 * 商品信息
	 */
	private String commCode; 
	
	/**
	 * 商品名称
	 */
	private String commName; 
	
	/**
	 * 商品规格
	 */
	private String commSpec; 
	
	/**
	 * 商品数量
	 */
	private Double commAmount; 
	
	/**
	 * 单位
	 */
	private Unit unit;

	
	/**
	 * 获取商品数量
	 * 
	 * @return commAmount 商品数量
	 */
	public Double getCommAmount() {
		return commAmount;
	}

	/**
	 * 设置商品数量
	 * 
	 * @param commAmount 商品数量
	 */
	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}
	
	/**
	 * 获取商品信息
	 * 
	 * @return commCode 商品信息
	 */
	public String getCommCode() {
		return commCode;
	}

	/**
	 * 设置商品信息
	 * 
	 * @param commCode 商品信息
	 */
	public void setCommCode(String commCode) {
		this.commCode = commCode;
	}
	
	/**
	 * 获取商品名称
	 * 
	 * @return commName 商品名称
	 */
	public String getCommName() {
		return commName;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param commName 商品名称
	 */
	public void setCommName(String commName) {
		this.commName = commName;
	}
	
	/**
	 * 获取商品规格
	 * 
	 * @return commSpec 商品规格
	 */
	public String getCommSpec() {
		return commSpec;
	}

	/**
	 * 设置商品规格
	 * 
	 * @param commSpec 商品规格
	 */
	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}
	
	/**
	 * 获取报关单号
	 * 
	 * @return customsDeclarationCode 报关单号
	 */
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	/**
	 * 设置报关单号
	 * 
	 * @param customsDeclarationCode 报关单号
	 */
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}
	
	/**
	 * 获取清单申报数量
	 * 
	 * @return declaredAmount 清单申报数量
	 */
	public Double getDeclaredAmount() {
		return declaredAmount;
	}

	/**
	 * 设置清单申报数量
	 * 
	 * @param declaredAmount 清单申报数量
	 */
	public void setDeclaredAmount(Double declaredAmount) {
		this.declaredAmount = declaredAmount;
	}
	
	/**
	 * 获取清单企业申报单价
	 * 
	 * @return declaredPrice 清单企业申报单价
	 */
	public Double getDeclaredPrice() {
		return declaredPrice;
	}

	/**
	 * 设置清单企业申报单价
	 * 
	 * @param declaredPrice 清单企业申报单价
	 */
	public void setDeclaredPrice(Double declaredPrice) {
		this.declaredPrice = declaredPrice;
	}
	
	/**
	 * 获取清单申报日期
	 * 
	 * @return listDeclareDate 清单申报日期
	 */
	public Date getListDeclareDate() {
		return listDeclareDate;
	}

	/**
	 * 设置清单申报日期
	 * 
	 * @param listDeclareDate 清单申报日期
	 */
	public void setListDeclareDate(Date listDeclareDate) {
		this.listDeclareDate = listDeclareDate;
	}
	
	/**
	 * 获取进出口类型
	 * 
	 * @return listImpExpType 进出口类型
	 */
	public Integer getListImpExpType() {
		return listImpExpType;
	}

	/**
	 * 设置进出口类型
	 * 
	 * @param listImpExpType 进出口类型
	 */
	public void setListImpExpType(Integer listImpExpType) {
		this.listImpExpType = listImpExpType;
	}
	
	/**
	 * 获取清单号码
	 * 
	 * @return listNo 清单号码
	 */
	public String getListNo() {
		return listNo;
	}

	/**
	 * 设置清单号码
	 * 
	 * @param listNo 清单号码
	 */
	public void setListNo(String listNo) {
		this.listNo = listNo;
	}
	
	/**
	 * 获取清单货品名称
	 * 
	 * @return listPtName 清单货品名称
	 */
	public String getListPtName() {
		return listPtName;
	}

	/**
	 * 设置清单货品名称
	 * 
	 * @param listPtName 清单货品名称
	 */
	public void setListPtName(String listPtName) {
		this.listPtName = listPtName;
	}
	
	/**
	 * 获取清单货号
	 * 
	 * @return listPtNo 清单货号
	 */
	public String getListPtNo() {
		return listPtNo;
	}

	/**
	 * 设置清单货号
	 * 
	 * @param listPtNo 清单货号
	 */
	public void setListPtNo(String listPtNo) {
		this.listPtNo = listPtNo;
	}
	
	/**
	 * 获取清单货品规格
	 * 
	 * @return listPtSpec 清单货品规格
	 */
	public String getListPtSpec() {
		return listPtSpec;
	}

	/**
	 * 设置清单货品规格
	 * 
	 * @param listPtSpec 清单货品规格
	 */
	public void setListPtSpec(String listPtSpec) {
		this.listPtSpec = listPtSpec;
	}
	
	/**
	 * 获取报关单位
	 * 
	 * @return listPtUnit 报关单位
	 */
	public Unit getListPtUnit() {
		return listPtUnit;
	}

	/**
	 * 设置报关单位
	 * 
	 * @param listPtUnit 报关单位
	 */
	public void setListPtUnit(Unit listPtUnit) {
		this.listPtUnit = listPtUnit;
	}
	
	/**
	 * 获取单位
	 * 
	 * @return unit 单位
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit 单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
}
