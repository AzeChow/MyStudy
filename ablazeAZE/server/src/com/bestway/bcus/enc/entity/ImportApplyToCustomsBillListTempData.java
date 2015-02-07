/*
 * Created on 2004-7-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * @author fhz
 * 
 */
public class ImportApplyToCustomsBillListTempData implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	// ----------数据对应的列,默认值为0,表示没有对应列
	// ----------表头
	private String headImpExpType = null;// 表头－进出口类型

	private String headBillListNo = null;// 表头－清单编号

	private String headUnifiedCode = null;// 表头－清单统一编号

	private String headListDeclareDate = null;// 表头－清单申报日期

	private String headDeclareCustom = null;// 表头－申报地海关

	private String headTransportMode = null;// 表头－运输方式

	private String headDeclareCompany = null;// 表头－申报单位

	private String headEmsNo = null;// 表头－电子帐册编号

	private String headMaterialNum = null;// 表头－商品项数

	private String headEntrancePort = null;// 表头－进出口岸

	private String headTradeMode = null;// 表头－监管方式

	private String headMemo = null;// 表头－备注

	private String headListState = null;// 表头－清单状态
	
	private String headCustoms = null;// 表头－客户(供商应)

	// ----------归并后
	private String afterEmsSerialNo = null;// 归并后－帐册序号

	// ----------归并前
	private String beforeMaterielPtNo = null;// 归并前－商品货号

	private String beforeCustomsNo = null;// 归并前－对应报关单商品号

	private String beforeCurrency = null;// 归并前－币别

	private String beforeDeclaredAmount = null;// 归并前－申报数量

	private String beforeLegalAmount = null;// 归并前－法定数量

	private String beforeSecondLegalAmount = null;// 归并前－第二法定数量

	private String beforeDeclaredPrice = null;// 归并前－企业申报单价

	private String beforeTotalPrice = null;// 归并前－企业总价

	private String beforeWorkUsd = null;// 归并前－加工费总价

	private String beforeSalesCountry = null;// 归并前－产销国(地区)

	private String beforeLevyModel = null;// 归并前－征免方式

	private String beforeUsess = null;// 归并前－用途

	private String beforeVersion = null;// 归并前－海关版本号
	private String beforeCmpVersion = null;// 归并前－企业版本号

	private String beforeGrossWeight = null;// 归并前－毛重

	private String beforeNetWeight = null;// 归并前－净重

	private String beforePeice = null;// 归并前－件数

	private String beforeBoxNo = null;// 归并前－箱号

	private String beforeMemos = null;// 归并前－备注

	private String beforeProjectDept = null;// 归并前－事业部

	private String beforeExtendMemo = null;// 归并前－扩展备注

	/**
	 * 物料类别
	 */
	private String materielType = null;

	/**
	 * 错误原因
	 */
	private String errinfo = "";

	/**
	 * @return the headImpExpType
	 */

	private String beforeBodyMemo;

	public String getBeforeBodyMemo() {
		return beforeBodyMemo;
	}

	public void setBeforeBodyMemo(String beforeBodyMemo) {
		this.beforeBodyMemo = beforeBodyMemo;
	}

	public String getHeadImpExpType() {
		return headImpExpType;
	}

	/**
	 * @param headImpExpType
	 *            the headImpExpType to set
	 */
	public void setHeadImpExpType(String headImpExpType) {
		this.headImpExpType = headImpExpType;
	}

	/**
	 * @return the headBillListNo
	 */
	public String getHeadBillListNo() {
		return headBillListNo;
	}

	/**
	 * @param headBillListNo
	 *            the headBillListNo to set
	 */
	public void setHeadBillListNo(String headBillListNo) {
		this.headBillListNo = headBillListNo;
	}

	/**
	 * @return the headUnifiedCode
	 */
	public String getHeadUnifiedCode() {
		return headUnifiedCode;
	}

	
	
	public String getBeforeCmpVersion() {
		return beforeCmpVersion;
	}

	public void setBeforeCmpVersion(String beforeCmpVersion) {
		this.beforeCmpVersion = beforeCmpVersion;
	}

	/**
	 * @param headUnifiedCode
	 *            the headUnifiedCode to set
	 */
	public void setHeadUnifiedCode(String headUnifiedCode) {
		this.headUnifiedCode = headUnifiedCode;
	}

	public String getBeforeTotalPrice() {
		return beforeTotalPrice;
	}

	public void setBeforeTotalPrice(String beforeTotalPrice) {
		this.beforeTotalPrice = beforeTotalPrice;
	}

	public String getBeforeWorkUsd() {
		return beforeWorkUsd;
	}

	public void setBeforeWorkUsd(String beforeWorkUsd) {
		this.beforeWorkUsd = beforeWorkUsd;
	}

	public String getBeforeBoxNo() {
		return beforeBoxNo;
	}

	public void setBeforeBoxNo(String beforeBoxNo) {
		this.beforeBoxNo = beforeBoxNo;
	}

	/**
	 * @return the headMaterielProductFlag
	 */
	public String getHeadListDeclareDate() {
		return headListDeclareDate;
	}

	/**
	 * @param headMaterielProductFlag
	 *            the headMaterielProductFlag to set
	 */
	public void setHeadListDeclareDate(String headMaterielProductFlag) {
		this.headListDeclareDate = headMaterielProductFlag;
	}

	/**
	 * @return the headDeclareCustom
	 */
	public String getHeadDeclareCustom() {
		return headDeclareCustom;
	}

	/**
	 * @param headDeclareCustom
	 *            the headDeclareCustom to set
	 */
	public void setHeadDeclareCustom(String headDeclareCustom) {
		this.headDeclareCustom = headDeclareCustom;
	}

	/**
	 * @return the headTransportMode
	 */
	public String getHeadTransportMode() {
		return headTransportMode;
	}

	/**
	 * @param headTransportMode
	 *            the headTransportMode to set
	 */
	public void setHeadTransportMode(String headTransportMode) {
		this.headTransportMode = headTransportMode;
	}

	/**
	 * @return the headDeclareFirm
	 */
	public String getHeadDeclareCompany() {
		return headDeclareCompany;
	}

	/**
	 * @param headDeclareFirm
	 *            the headDeclareFirm to set
	 */
	public void setHeadDeclareCompany(String headDeclareFirm) {
		this.headDeclareCompany = headDeclareFirm;
	}

	/**
	 * @return the headEmsNo
	 */
	public String getHeadEmsNo() {
		return headEmsNo;
	}

	/**
	 * @param headEmsNo
	 *            the headEmsNo to set
	 */
	public void setHeadEmsNo(String headEmsNo) {
		this.headEmsNo = headEmsNo;
	}

	/**
	 * @return the headMaterialNum
	 */
	public String getHeadMaterialNum() {
		return headMaterialNum;
	}

	/**
	 * @param headMaterialNum
	 *            the headMaterialNum to set
	 */
	public void setHeadMaterialNum(String headMaterialNum) {
		this.headMaterialNum = headMaterialNum;
	}

	/**
	 * @return the headEntrancePort
	 */
	public String getHeadEntrancePort() {
		return headEntrancePort;
	}

	/**
	 * @param headEntrancePort
	 *            the headEntrancePort to set
	 */
	public void setHeadEntrancePort(String headEntrancePort) {
		this.headEntrancePort = headEntrancePort;
	}

	/**
	 * @return the headTradeMode
	 */
	public String getHeadTradeMode() {
		return headTradeMode;
	}

	/**
	 * @param headTradeMode
	 *            the headTradeMode to set
	 */
	public void setHeadTradeMode(String headTradeMode) {
		this.headTradeMode = headTradeMode;
	}

	/**
	 * @return the headMemo
	 */
	public String getHeadMemo() {
		return headMemo;
	}

	/**
	 * @param headMemo
	 *            the headMemo to set
	 */
	public void setHeadMemo(String headMemo) {
		this.headMemo = headMemo;
	}

	/**
	 * @return the headListState
	 */
	public String getHeadListState() {
		return headListState;
	}

	/**
	 * @param headListState
	 *            the headListState to set
	 */
	public void setHeadListState(String headListState) {
		this.headListState = headListState;
	}

	/**
	 * @return the afterEmsSerialNo
	 */
	public String getAfterEmsSerialNo() {
		return afterEmsSerialNo;
	}

	/**
	 * @param afterEmsSerialNo
	 *            the afterEmsSerialNo to set
	 */
	public void setAfterEmsSerialNo(String afterEmsSerialNo) {
		this.afterEmsSerialNo = afterEmsSerialNo;
	}

	/**
	 * @return the beforeMaterielPtNo
	 */
	public String getBeforeMaterielPtNo() {
		return beforeMaterielPtNo;
	}

	/**
	 * @param beforeMaterielPtNo
	 *            the beforeMaterielPtNo to set
	 */
	public void setBeforeMaterielPtNo(String beforeMaterielPtNo) {
		this.beforeMaterielPtNo = beforeMaterielPtNo;
	}

	/**
	 * @return the beforeCustomsNo
	 */
	public String getBeforeCustomsNo() {
		return beforeCustomsNo;
	}

	/**
	 * @param beforeCustomsNo
	 *            the beforeCustomsNo to set
	 */
	public void setBeforeCustomsNo(String beforeCustomsNo) {
		this.beforeCustomsNo = beforeCustomsNo;
	}

	/**
	 * @return the beforeCurrency
	 */
	public String getBeforeCurrency() {
		return beforeCurrency;
	}

	/**
	 * @param beforeCurrency
	 *            the beforeCurrency to set
	 */
	public void setBeforeCurrency(String beforeCurrency) {
		this.beforeCurrency = beforeCurrency;
	}

	/**
	 * @return the beforeDeclaredAmount
	 */
	public String getBeforeDeclaredAmount() {
		return beforeDeclaredAmount;
	}

	/**
	 * @param beforeDeclaredAmount
	 *            the beforeDeclaredAmount to set
	 */
	public void setBeforeDeclaredAmount(String beforeDeclaredAmount) {
		this.beforeDeclaredAmount = beforeDeclaredAmount;
	}

	/**
	 * @return the beforeLegalAmount
	 */
	public String getBeforeLegalAmount() {
		return beforeLegalAmount;
	}

	/**
	 * @param beforeLegalAmount
	 *            the beforeLegalAmount to set
	 */
	public void setBeforeLegalAmount(String beforeLegalAmount) {
		this.beforeLegalAmount = beforeLegalAmount;
	}

	/**
	 * @return the beforeSecondLegalAmount
	 */
	public String getBeforeSecondLegalAmount() {
		return beforeSecondLegalAmount;
	}

	/**
	 * @param beforeSecondLegalAmount
	 *            the beforeSecondLegalAmount to set
	 */
	public void setBeforeSecondLegalAmount(String beforeSecondLegalAmount) {
		this.beforeSecondLegalAmount = beforeSecondLegalAmount;
	}

	/**
	 * @return the beforeDeclaredPrice
	 */
	public String getBeforeDeclaredPrice() {
		return beforeDeclaredPrice;
	}

	/**
	 * @param beforeDeclaredPrice
	 *            the beforeDeclaredPrice to set
	 */
	public void setBeforeDeclaredPrice(String beforeDeclaredPrice) {
		this.beforeDeclaredPrice = beforeDeclaredPrice;
	}

	/**
	 * @return the beforeSalesCountry
	 */
	public String getBeforeSalesCountry() {
		return beforeSalesCountry;
	}

	/**
	 * @param beforeSalesCountry
	 *            the beforeSalesCountry to set
	 */
	public void setBeforeSalesCountry(String beforeSalesCountry) {
		this.beforeSalesCountry = beforeSalesCountry;
	}

	/**
	 * @return the beforeLevyModel
	 */
	public String getBeforeLevyModel() {
		return beforeLevyModel;
	}

	/**
	 * @param beforeLevyModel
	 *            the beforeLevyModel to set
	 */
	public void setBeforeLevyModel(String beforeLevyModel) {
		this.beforeLevyModel = beforeLevyModel;
	}

	/**
	 * @return the beforeUsess
	 */
	public String getBeforeUsess() {
		return beforeUsess;
	}

	/**
	 * @param beforeUsess
	 *            the beforeUsess to set
	 */
	public void setBeforeUsess(String beforeUsess) {
		this.beforeUsess = beforeUsess;
	}

	/**
	 * @return the beforeVersion
	 */
	public String getBeforeVersion() {
		return beforeVersion;
	}

	public String getHeadCustoms() {
		return headCustoms;
	}

	public void setHeadCustoms(String headCustoms) {
		this.headCustoms = headCustoms;
	}

	/**
	 * @param beforeVersion
	 *            the beforeVersion to set
	 */
	public void setBeforeVersion(String beforeVersion) {
		this.beforeVersion = beforeVersion;
	}

	/**
	 * @return the beforeGrossWeight
	 */
	public String getBeforeGrossWeight() {
		return beforeGrossWeight;
	}

	/**
	 * @param beforeGrossWeight
	 *            the beforeGrossWeight to set
	 */
	public void setBeforeGrossWeight(String beforeGrossWeight) {
		this.beforeGrossWeight = beforeGrossWeight;
	}

	/**
	 * @return the beforeNetWeight
	 */
	public String getBeforeNetWeight() {
		return beforeNetWeight;
	}

	/**
	 * @param beforeNetWeight
	 *            the beforeNetWeight to set
	 */
	public void setBeforeNetWeight(String beforeNetWeight) {
		this.beforeNetWeight = beforeNetWeight;
	}

	/**
	 * @return the beforeMemos
	 */
	public String getBeforeMemos() {
		return beforeMemos;
	}

	/**
	 * @param beforeMemos
	 *            the beforeMemos to set
	 */
	public void setBeforeMemos(String beforeMemos) {
		this.beforeMemos = beforeMemos;
	}

	/**
	 * @return the beforeProjectDept
	 */
	public String getBeforeProjectDept() {
		return beforeProjectDept;
	}

	/**
	 * @param beforeProjectDept
	 *            the beforeProjectDept to set
	 */
	public void setBeforeProjectDept(String beforeProjectDept) {
		this.beforeProjectDept = beforeProjectDept;
	}

	/**
	 * @return the beforeExtendMemo
	 */
	public String getBeforeExtendMemo() {
		return beforeExtendMemo;
	}

	/**
	 * @param beforeExtendMemo
	 *            the beforeExtendMemo to set
	 */
	public void setBeforeExtendMemo(String beforeExtendMemo) {
		this.beforeExtendMemo = beforeExtendMemo;
	}

	/**
	 * @return the materielType
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param materielType
	 *            the materielType to set
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	public String getBeforePeice() {
		return beforePeice;
	}

	public void setBeforePeice(String beforePeice) {
		this.beforePeice = beforePeice;
	}

}