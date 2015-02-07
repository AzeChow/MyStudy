/*
 * Created on 2004-7-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 *  导入报关清单的一些属性
 * @author Administrator table="ImportApplyToCustomsBillProperty"
 */
public class ImportApplyCustomsProperty  extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	//----------数据对应的列,默认值为0,表示没有对应列
	//----------表头
	/**
	 * 表头－进出口类型
	 */
	private Integer cbbImpExpType= 0;
	
	/**
	 * 表头－清单编号
	 */
	private Integer cbbBillListNo= 0;
	
	/**
	 * 表头－清单统一编号
	 */
	private Integer cbbUnifiedCode= 0;
	
	/**
	 * 表头－料件成品标志
	 */
	private Integer cbbListDeclareDate= 0;
	
	/**
	 * 表头－申报地海关
	 */
	private Integer cbbDeclareCustom= 0;
	
	/**
	 * 表头－运输方式
	 */
	private Integer cbbTransportMode= 0;
	
	/**
	 * 表头－申报单位
	 */	
	private Integer cbbDeclareCompany= 0;
	
	/**
	 * 表头－电子帐册编号
	 */
	private Integer cbbEmsNo= 0;
	
	/**
	 * 表头－商品项数
	 */
	private Integer cbbMaterialNum= 0;
	
	/**
	 * 表头－进出口岸
	 */
	private Integer cbbEntrancePort= 0;
	
	/**
	 * 表头－监管方式
	 */
	private Integer cbbTradeMode= 0;
	
	/**
	 * 表头－备注
	 */
	private Integer cbbMemo= 0;
	
	/**
	 * 表头－清单状态
	 */
	private Integer cbbListState= 0;
	
	
	//----------归并后
	/**
	 * 归并后－帐册序号
	 */
	private Integer cbbAfterEmsSerialNo= 0;
	
	
	//----------归并前
	/**
	 * 归并前－商品货号
	 */
	private Integer cbbBeforeMaterielPtNo= 0;
	
	/**
	 * 归并前－对应报关单商品号
	 */
	private Integer cbbBeforeCustomsNo= 0;
	
	/**
	 * 归并前－币别
	 */
	private Integer cbbBeforeCurrency= 0;
	
	/**
	 * 归并前－申报数量
	 */
	private Integer cbbBeforeDeclaredAmount= 0;
	
	/**
	 * 归并前－法定数量
	 */
	private Integer cbbBeforeLegalAmount= 0;
	
	/**
	 * 归并前－第二法定数量
	 */
	private Integer cbbBeforeSecondLegalAmount= 0;
	
	/**
	 * 归并前－企业申报单价
	 */
	private Integer cbbBeforeDeclaredPrice= 0;
	
	/**
	 * 归并前－产销国(地区)
	 */
	private Integer cbbBeforeSalesCountry= 0;
	/**
	 * 归并前－征免方式
	 */
	private Integer cbbBeforeLevyModel= 0;
	
	/**
	 * 归并前－用途
	 */
	private Integer cbbBeforeUsess= 0;
	
	/**
	 * 归并前－版本号
	 */
	private Integer cbbBeforeVersion= 0;
	
	/**
	 * 归并前－毛重
	 */
	private Integer cbbBeforeGrossWeight= 0;
	
	/**
	 * 归并前－净重
	 */
	private Integer cbbBeforeNetWeight= 0;
	
	/**
	 * 归并前-件数
	 */
	private Integer cbbBeforePeice=0;
	
	/**
	 * 归并前－备注
	 */
	private Integer cbbBeforeMemos= 0;
	
	/**
	 * 归并前－事业部
	 */
	private Integer cbbBeforeProjectDept= 0;
	
	/**
	 * 归并前－扩展备注
	 */
	private Integer cbbBeforeExtendMemo= 0;
	
	
	/**----------------------------------------------------------------------------*/
	
	//----------数据是取代码还是名称.0表示取编码，1表示取名称。默认值都为0
	//----------表头
//	private Integer cbbImpExpTypeCodeName= 0;//进出口类型
	
	/**
	 * 申报地海关
	 */
	private Integer cbbDeclareCustomCodeName= 0;
	
	/**
	 * 运输方式
	 */
	private Integer cbbTransportModeCodeName= 0;
	
	/**
	 * 申报单位
	 */
	private Integer cbbDeclareCompanyCodeName= 0;
	
	/**
	 * 进出口岸
	 */
	private Integer cbbEntrancePortCodeName= 0;
	
	/**
	 * 监管方式
	 */
	private Integer cbbTradeModeCodeName= 0;
	
	
	//----------归并前
	/**
	 * 币别
	 */
	private Integer cbbBeforeCurrencyCodeName= 0;
	
	/**
	 * 产销国(地区)
	 */
	private Integer cbbBeforeSalesCountryCodeName= 0;
	
	/**
	 * 征免方式
	 */
	private Integer cbbBeforeLevyModelCodeName= 0;
	
	/**
	 * 用途
	 */
	private Integer cbbBeforeUsessCodeName= 0;
	
	/**
	 * 事业部
	 */
	private Integer cbbBeforeProjectDeptCodeName= 0;

	public Integer getCbbBeforePeice() {
		return cbbBeforePeice;
	}

	public void setCbbBeforePeice(Integer cbbBeforePeice) {
		this.cbbBeforePeice = cbbBeforePeice;
	}

	/**
	 * @return the cbbImpExpType
	 */
	public Integer getCbbImpExpType() {
		return cbbImpExpType;
	}

	/**
	 * @param cbbImpExpType the cbbImpExpType to set
	 */
	public void setCbbImpExpType(Integer cbbImpExpType) {
		this.cbbImpExpType = cbbImpExpType;
	}

	/**
	 * @return the cbbBillListNo
	 */
	public Integer getCbbBillListNo() {
		return cbbBillListNo;
	}

	/**
	 * @param cbbBillListNo the cbbBillListNo to set
	 */
	public void setCbbBillListNo(Integer cbbBillListNo) {
		this.cbbBillListNo = cbbBillListNo;
	}

	/**
	 * @return the cbbUnifiedCode
	 */
	public Integer getCbbUnifiedCode() {
		return cbbUnifiedCode;
	}

	/**
	 * @param cbbUnifiedCode the cbbUnifiedCode to set
	 */
	public void setCbbUnifiedCode(Integer cbbUnifiedCode) {
		this.cbbUnifiedCode = cbbUnifiedCode;
	}

	/**
	 * @return the cbbMaterielProductFlag
	 */
	public Integer getCbbListDeclareDate() {
		return cbbListDeclareDate;
	}

	/**
	 * @param cbbMaterielProductFlag the cbbMaterielProductFlag to set
	 */
	public void setCbbListDeclareDate(Integer cbbListDeclareDate) {
		this.cbbListDeclareDate = cbbListDeclareDate;
	}

	/**
	 * @return the cbbDeclareCustom
	 */
	public Integer getCbbDeclareCustom() {
		return cbbDeclareCustom;
	}

	/**
	 * @param cbbDeclareCustom the cbbDeclareCustom to set
	 */
	public void setCbbDeclareCustom(Integer cbbDeclareCustom) {
		this.cbbDeclareCustom = cbbDeclareCustom;
	}

	/**
	 * @return the cbbTransportMode
	 */
	public Integer getCbbTransportMode() {
		return cbbTransportMode;
	}

	/**
	 * @param cbbTransportMode the cbbTransportMode to set
	 */
	public void setCbbTransportMode(Integer cbbTransportMode) {
		this.cbbTransportMode = cbbTransportMode;
	}

	/**
	 * @return the cbbDeclareFirm
	 */
	public Integer getCbbDeclareCompany() {
		return cbbDeclareCompany;
	}

	/**
	 * @param cbbDeclareFirm the cbbDeclareFirm to set
	 */
	public void setCbbDeclareCompany(Integer cbbDeclareFirm) {
		this.cbbDeclareCompany = cbbDeclareFirm;
	}

	/**
	 * @return the cbbEmsNo
	 */
	public Integer getCbbEmsNo() {
		return cbbEmsNo;
	}

	/**
	 * @param cbbEmsNo the cbbEmsNo to set
	 */
	public void setCbbEmsNo(Integer cbbEmsNo) {
		this.cbbEmsNo = cbbEmsNo;
	}

	/**
	 * @return the cbbMaterialNum
	 */
	public Integer getCbbMaterialNum() {
		return cbbMaterialNum;
	}

	/**
	 * @param cbbMaterialNum the cbbMaterialNum to set
	 */
	public void setCbbMaterialNum(Integer cbbMaterialNum) {
		this.cbbMaterialNum = cbbMaterialNum;
	}

	/**
	 * @return the cbbEntrancePort
	 */
	public Integer getCbbEntrancePort() {
		return cbbEntrancePort;
	}

	/**
	 * @param cbbEntrancePort the cbbEntrancePort to set
	 */
	public void setCbbEntrancePort(Integer cbbEntrancePort) {
		this.cbbEntrancePort = cbbEntrancePort;
	}

	/**
	 * @return the cbbTradeMode
	 */
	public Integer getCbbTradeMode() {
		return cbbTradeMode;
	}

	/**
	 * @param cbbTradeMode the cbbTradeMode to set
	 */
	public void setCbbTradeMode(Integer cbbTradeMode) {
		this.cbbTradeMode = cbbTradeMode;
	}

	/**
	 * @return the cbbMemo
	 */
	public Integer getCbbMemo() {
		return cbbMemo;
	}

	/**
	 * @param cbbMemo the cbbMemo to set
	 */
	public void setCbbMemo(Integer cbbMemo) {
		this.cbbMemo = cbbMemo;
	}

	/**
	 * @return the cbbListState
	 */
	public Integer getCbbListState() {
		return cbbListState;
	}

	/**
	 * @param cbbListState the cbbListState to set
	 */
	public void setCbbListState(Integer cbbListState) {
		this.cbbListState = cbbListState;
	}

	/**
	 * @return the cbbAfterEmsSerialNo
	 */
	public Integer getCbbAfterEmsSerialNo() {
		return cbbAfterEmsSerialNo;
	}

	/**
	 * @param cbbAfterEmsSerialNo the cbbAfterEmsSerialNo to set
	 */
	public void setCbbAfterEmsSerialNo(Integer cbbAfterEmsSerialNo) {
		this.cbbAfterEmsSerialNo = cbbAfterEmsSerialNo;
	}

	/**
	 * @return the cbbBeforeMaterielPtNo
	 */
	public Integer getCbbBeforeMaterielPtNo() {
		return cbbBeforeMaterielPtNo;
	}

	/**
	 * @param cbbBeforeMaterielPtNo the cbbBeforeMaterielPtNo to set
	 */
	public void setCbbBeforeMaterielPtNo(Integer cbbBeforeMaterielPtNo) {
		this.cbbBeforeMaterielPtNo = cbbBeforeMaterielPtNo;
	}

	/**
	 * @return the cbbBeforeCustomsNo
	 */
	public Integer getCbbBeforeCustomsNo() {
		return cbbBeforeCustomsNo;
	}

	/**
	 * @param cbbBeforeCustomsNo the cbbBeforeCustomsNo to set
	 */
	public void setCbbBeforeCustomsNo(Integer cbbBeforeCustomsNo) {
		this.cbbBeforeCustomsNo = cbbBeforeCustomsNo;
	}

	/**
	 * @return the cbbBeforeCurrency
	 */
	public Integer getCbbBeforeCurrency() {
		return cbbBeforeCurrency;
	}

	/**
	 * @param cbbBeforeCurrency the cbbBeforeCurrency to set
	 */
	public void setCbbBeforeCurrency(Integer cbbBeforeCurrency) {
		this.cbbBeforeCurrency = cbbBeforeCurrency;
	}

	/**
	 * @return the cbbBeforeDeclaredAmount
	 */
	public Integer getCbbBeforeDeclaredAmount() {
		return cbbBeforeDeclaredAmount;
	}

	/**
	 * @param cbbBeforeDeclaredAmount the cbbBeforeDeclaredAmount to set
	 */
	public void setCbbBeforeDeclaredAmount(Integer cbbBeforeDeclaredAmount) {
		this.cbbBeforeDeclaredAmount = cbbBeforeDeclaredAmount;
	}

	/**
	 * @return the cbbBeforeLegalAmount
	 */
	public Integer getCbbBeforeLegalAmount() {
		return cbbBeforeLegalAmount;
	}

	/**
	 * @param cbbBeforeLegalAmount the cbbBeforeLegalAmount to set
	 */
	public void setCbbBeforeLegalAmount(Integer cbbBeforeLegalAmount) {
		this.cbbBeforeLegalAmount = cbbBeforeLegalAmount;
	}

	/**
	 * @return the cbbBeforeSecondLegalAmount
	 */
	public Integer getCbbBeforeSecondLegalAmount() {
		return cbbBeforeSecondLegalAmount;
	}

	/**
	 * @param cbbBeforeSecondLegalAmount the cbbBeforeSecondLegalAmount to set
	 */
	public void setCbbBeforeSecondLegalAmount(Integer cbbBeforeSecondLegalAmount) {
		this.cbbBeforeSecondLegalAmount = cbbBeforeSecondLegalAmount;
	}

	/**
	 * @return the cbbBeforeDeclaredPrice
	 */
	public Integer getCbbBeforeDeclaredPrice() {
		return cbbBeforeDeclaredPrice;
	}

	/**
	 * @param cbbBeforeDeclaredPrice the cbbBeforeDeclaredPrice to set
	 */
	public void setCbbBeforeDeclaredPrice(Integer cbbBeforeDeclaredPrice) {
		this.cbbBeforeDeclaredPrice = cbbBeforeDeclaredPrice;
	}

	/**
	 * @return the cbbBeforeSalesCountry
	 */
	public Integer getCbbBeforeSalesCountry() {
		return cbbBeforeSalesCountry;
	}

	/**
	 * @param cbbBeforeSalesCountry the cbbBeforeSalesCountry to set
	 */
	public void setCbbBeforeSalesCountry(Integer cbbBeforeSalesCountry) {
		this.cbbBeforeSalesCountry = cbbBeforeSalesCountry;
	}

	/**
	 * @return the cbbBeforeLevyModel
	 */
	public Integer getCbbBeforeLevyModel() {
		return cbbBeforeLevyModel;
	}

	/**
	 * @param cbbBeforeLevyModel the cbbBeforeLevyModel to set
	 */
	public void setCbbBeforeLevyModel(Integer cbbBeforeLevyModel) {
		this.cbbBeforeLevyModel = cbbBeforeLevyModel;
	}

	/**
	 * @return the cbbBeforeUsess
	 */
	public Integer getCbbBeforeUsess() {
		return cbbBeforeUsess;
	}

	/**
	 * @param cbbBeforeUsess the cbbBeforeUsess to set
	 */
	public void setCbbBeforeUsess(Integer cbbBeforeUsess) {
		this.cbbBeforeUsess = cbbBeforeUsess;
	}

	/**
	 * @return the cbbBeforeVersion
	 */
	public Integer getCbbBeforeVersion() {
		return cbbBeforeVersion;
	}

	/**
	 * @param cbbBeforeVersion the cbbBeforeVersion to set
	 */
	public void setCbbBeforeVersion(Integer cbbBeforeVersion) {
		this.cbbBeforeVersion = cbbBeforeVersion;
	}

	/**
	 * @return the cbbBeforeGrossWeight
	 */
	public Integer getCbbBeforeGrossWeight() {
		return cbbBeforeGrossWeight;
	}

	/**
	 * @param cbbBeforeGrossWeight the cbbBeforeGrossWeight to set
	 */
	public void setCbbBeforeGrossWeight(Integer cbbBeforeGrossWeight) {
		this.cbbBeforeGrossWeight = cbbBeforeGrossWeight;
	}

	/**
	 * @return the cbbBeforeNetWeight
	 */
	public Integer getCbbBeforeNetWeight() {
		return cbbBeforeNetWeight;
	}

	/**
	 * @param cbbBeforeNetWeight the cbbBeforeNetWeight to set
	 */
	public void setCbbBeforeNetWeight(Integer cbbBeforeNetWeight) {
		this.cbbBeforeNetWeight = cbbBeforeNetWeight;
	}

	/**
	 * @return the cbbBeforeMemos
	 */
	public Integer getCbbBeforeMemos() {
		return cbbBeforeMemos;
	}

	/**
	 * @param cbbBeforeMemos the cbbBeforeMemos to set
	 */
	public void setCbbBeforeMemos(Integer cbbBeforeMemos) {
		this.cbbBeforeMemos = cbbBeforeMemos;
	}

	/**
	 * @return the cbbBeforeProjectDept
	 */
	public Integer getCbbBeforeProjectDept() {
		return cbbBeforeProjectDept;
	}

	/**
	 * @param cbbBeforeProjectDept the cbbBeforeProjectDept to set
	 */
	public void setCbbBeforeProjectDept(Integer cbbBeforeProjectDept) {
		this.cbbBeforeProjectDept = cbbBeforeProjectDept;
	}

	/**
	 * @return the cbbBeforeExtendMemo
	 */
	public Integer getCbbBeforeExtendMemo() {
		return cbbBeforeExtendMemo;
	}

	/**
	 * @param cbbBeforeExtendMemo the cbbBeforeExtendMemo to set
	 */
	public void setCbbBeforeExtendMemo(Integer cbbBeforeExtendMemo) {
		this.cbbBeforeExtendMemo = cbbBeforeExtendMemo;
	}

//	/**
//	 * @return the cbbImpExpTypeCodeName
//	 */
//	public Integer getCbbImpExpTypeCodeName() {
//		return cbbImpExpTypeCodeName;
//	}
//
//	/**
//	 * @param cbbImpExpTypeCodeName the cbbImpExpTypeCodeName to set
//	 */
//	public void setCbbImpExpTypeCodeName(Integer cbbImpExpTypeCodeName) {
//		this.cbbImpExpTypeCodeName = cbbImpExpTypeCodeName;
//	}

	/**
	 * @return the cbbDeclareCustomCodeName
	 */
	public Integer getCbbDeclareCustomCodeName() {
		return cbbDeclareCustomCodeName;
	}

	/**
	 * @param cbbDeclareCustomCodeName the cbbDeclareCustomCodeName to set
	 */
	public void setCbbDeclareCustomCodeName(Integer cbbDeclareCustomCodeName) {
		this.cbbDeclareCustomCodeName = cbbDeclareCustomCodeName;
	}

	/**
	 * @return the cbbTransportModeCodeName
	 */
	public Integer getCbbTransportModeCodeName() {
		return cbbTransportModeCodeName;
	}

	/**
	 * @param cbbTransportModeCodeName the cbbTransportModeCodeName to set
	 */
	public void setCbbTransportModeCodeName(Integer cbbTransportModeCodeName) {
		this.cbbTransportModeCodeName = cbbTransportModeCodeName;
	}

	/**
	 * @return the cbbDeclareFirmCodeName
	 */
	public Integer getCbbDeclareCompanyCodeName() {
		return cbbDeclareCompanyCodeName;
	}

	/**
	 * @param cbbDeclareFirmCodeName the cbbDeclareFirmCodeName to set
	 */
	public void setCbbDeclareCompanyCodeName(Integer cbbDeclareFirmCodeName) {
		this.cbbDeclareCompanyCodeName = cbbDeclareFirmCodeName;
	}

	/**
	 * @return the cbbEntrancePortCodeName
	 */
	public Integer getCbbEntrancePortCodeName() {
		return cbbEntrancePortCodeName;
	}

	/**
	 * @param cbbEntrancePortCodeName the cbbEntrancePortCodeName to set
	 */
	public void setCbbEntrancePortCodeName(Integer cbbEntrancePortCodeName) {
		this.cbbEntrancePortCodeName = cbbEntrancePortCodeName;
	}

	/**
	 * @return the cbbTradeModeCodeName
	 */
	public Integer getCbbTradeModeCodeName() {
		return cbbTradeModeCodeName;
	}

	/**
	 * @param cbbTradeModeCodeName the cbbTradeModeCodeName to set
	 */
	public void setCbbTradeModeCodeName(Integer cbbTradeModeCodeName) {
		this.cbbTradeModeCodeName = cbbTradeModeCodeName;
	}

	/**
	 * @return the cbbBeforeCurrencyCodeName
	 */
	public Integer getCbbBeforeCurrencyCodeName() {
		return cbbBeforeCurrencyCodeName;
	}

	/**
	 * @param cbbBeforeCurrencyCodeName the cbbBeforeCurrencyCodeName to set
	 */
	public void setCbbBeforeCurrencyCodeName(Integer cbbBeforeCurrencyCodeName) {
		this.cbbBeforeCurrencyCodeName = cbbBeforeCurrencyCodeName;
	}

	/**
	 * @return the cbbBeforeSalesCountryCodeName
	 */
	public Integer getCbbBeforeSalesCountryCodeName() {
		return cbbBeforeSalesCountryCodeName;
	}

	/**
	 * @param cbbBeforeSalesCountryCodeName the cbbBeforeSalesCountryCodeName to set
	 */
	public void setCbbBeforeSalesCountryCodeName(
			Integer cbbBeforeSalesCountryCodeName) {
		this.cbbBeforeSalesCountryCodeName = cbbBeforeSalesCountryCodeName;
	}

	/**
	 * @return the cbbBeforeLevyModelCodeName
	 */
	public Integer getCbbBeforeLevyModelCodeName() {
		return cbbBeforeLevyModelCodeName;
	}

	/**
	 * @param cbbBeforeLevyModelCodeName the cbbBeforeLevyModelCodeName to set
	 */
	public void setCbbBeforeLevyModelCodeName(Integer cbbBeforeLevyModelCodeName) {
		this.cbbBeforeLevyModelCodeName = cbbBeforeLevyModelCodeName;
	}

	/**
	 * @return the cbbBeforeUsessCodeName
	 */
	public Integer getCbbBeforeUsessCodeName() {
		return cbbBeforeUsessCodeName;
	}

	/**
	 * @param cbbBeforeUsessCodeName the cbbBeforeUsessCodeName to set
	 */
	public void setCbbBeforeUsessCodeName(Integer cbbBeforeUsessCodeName) {
		this.cbbBeforeUsessCodeName = cbbBeforeUsessCodeName;
	}

	/**
	 * @return the cbbBeforeProjectDeptCodeName
	 */
	public Integer getCbbBeforeProjectDeptCodeName() {
		return cbbBeforeProjectDeptCodeName;
	}

	/**
	 * @param cbbBeforeProjectDeptCodeName the cbbBeforeProjectDeptCodeName to set
	 */
	public void setCbbBeforeProjectDeptCodeName(Integer cbbBeforeProjectDeptCodeName) {
		this.cbbBeforeProjectDeptCodeName = cbbBeforeProjectDeptCodeName;
	}
	
	
	
	

}
