package com.bestway.bcus.system.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 报表栏位参数设置
 * @author 石小凯
 *
 */
public class ReportControl extends BaseScmEntity {
	private static final long serialVersionUID = 6057370633796132808L;

	/**
	 * 第二法定单位
	 */
	private Boolean secondLegalUnit = false;
	
	/**
	 * 第二法定数量
	 */
	private Boolean secondAmount = false;

	/**
	 * 用途
	 */
	private Boolean uses = false;
	
	/**
	 * 征减免税方式
	 */
	private Boolean levyMode = false;
	
	/**
	 * 件数
	 */
	private Boolean pieces = false;
	
	/**
	 * 包装方式头
	 */
	private Boolean wrapType = false;
	
	/**
	 * 包装方式体
	 */
	private Boolean wrapTypeDetail = false;
	
	/**
	 * 进出口口岸
	 */
	private Boolean customs = false;
	
	/**
	 * 进出口日期
	 */
	private Boolean impExpDate = false;
	
	/**
	 * 运输方式
	 */
	private Boolean transferMode = false;
	/**
	 * 征免性质
	 */
	private Boolean levyKind = false;
	/**
	 *  许可证号
	 */
	private Boolean license = false;
	/**
	 * 起运国/运抵国	
	 */
	private Boolean countryOfLoadingOrUnloading = false;
	/**
	 * 境内目的地
	 */
	private Boolean domesticDestinationOrSource = false;
	/**
	 * 装货港/指运港	
	 */
	private Boolean portOfLoadingOrUnloading = false;
	/**
	 * 核销单号
	 */
	private Boolean authorizeFile = false;
	/**
	 * 成交方式	
	 */
	private Boolean transac = false;
	/**
	 * 总毛重
	 */
	private Boolean grossWeight = false;
	/**
	 * 总净重
	 */
	private Boolean netWeight = false;
	/**
	 * 总件数
	 */
	private Boolean commodityNum = false;
	/**
	 * 集装箱号
	 */
	private Boolean containerNum = false;
	/**
	 * 随附单据	
	 */
	private Boolean attachedBill = false;
	/**
	 * 报送海关	
	 */
	private Boolean declarationCustoms = false;
	/**
	 * 备注证件代码	
	 */
	private Boolean certificateCode = false;
	/**
	 * 备注其他说明	
	 */
	private Boolean memos = false;
	/**
	 * 码头
	 */
	private Boolean predock = false;
	/**
	 * 保税仓库
	 */
	private Boolean bondedWarehouse = false;
	/**
	 * 关联手册号
	 */
	private Boolean relativeManualNo = false;
	/**
	 * 关联报关单号
	 */
	private Boolean relativeId = false;
	/**
	 * 关封号
	 */
	private Boolean customsEnvelopBillNo = false;
	/**
	 * 发票号
	 */
	private Boolean invoiceCode = false; 
	/**
	 * 预录入号
	 */
	private Boolean preCustomsDeclarationCode = false; 
	/**
	 * 报关单份数
	 */
	private Boolean CustomdeclarationMun = false;
	/**
	 * 录入员
	 */
	private Boolean creater = false;
	
	/**
	 * 合同金额
	 */
	private Boolean contractAmount = true;
	
	/**
	 * 表体备注
	 */
	private Boolean detailNote = false;
	
	/**
	 * 设置供应商代码
	 */
	private Boolean customerCode= false;

	/**
	 * 获得表体备注
	 * @return
	 */
	public Boolean getDetailNote() {
		return detailNote;
	}

	/**
	 * 设置表体备注
	 * @param detailNote
	 */
	public void setDetailNote(Boolean detailNote) {
		this.detailNote = detailNote;
	}

	/**
	 * 获取合同金额
	 * @return
	 */
	public Boolean getContractAmount() {
		return contractAmount;
	}
	
	/**
	 * 设置合同金额
	 * @return
	 */
	public void setContractAmount(Boolean contractAmount) {
		this.contractAmount = contractAmount;
	}
	/**
	 * 获取发票号
	 * @return
	 */
	public Boolean getInvoiceCode() {
		return invoiceCode;
	}
	/**
	 * 设置发票号
	 * @param invoiceCode
	 */
	public void setInvoiceCode(Boolean invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	/**
	 * 获取报关登记表
	 * @return
	 */
	public Boolean getSecondLegalUnit() {
		return secondLegalUnit;
	}
	/**
	 * 设置报关登记表
	 * @param secondLegalUnit
	 */
	public void setSecondLegalUnit(Boolean secondLegalUnit) {
		this.secondLegalUnit = secondLegalUnit;
	}
	/**
	 * 第二法定数量
	 * @return
	 */
	public Boolean getSecondAmount() {
		return secondAmount;
	}
	/**
	 * 设置第二法定数量
	 * @param secondAmount
	 */
	public void setSecondAmount(Boolean secondAmount) {
		this.secondAmount = secondAmount;
	}
	/**
	 * 获取用途
	 * @return
	 */
	public Boolean getUses() {
		return uses;
	}
	/**
	 * 设置用途
	 * @param uses
	 */
	public void setUses(Boolean uses) {
		this.uses = uses;
	}
	/**
	 * 获取征减免税方式
	 * @return
	 */
	public Boolean getLevyMode() {
		return levyMode;
	}
	/**
	 * 设置征减免税方式
	 * @param levyMode
	 */
	public void setLevyMode(Boolean levyMode) {
		this.levyMode = levyMode;
	}
	/**
	 * 获取件数
	 * @return
	 */
	public Boolean getPieces() {
		return pieces;
	}
	/**
	 * 设置件数
	 * @param pieces
	 */
	public void setPieces(Boolean pieces) {
		this.pieces = pieces;
	}
	/**
	 * 获取包装方式
	 * @return
	 */
	public Boolean getWrapType() {
		return wrapType;
	}
	/**
	 * 设置包装方式
	 * @param wrapType
	 */
	public void setWrapType(Boolean wrapType) {
		this.wrapType = wrapType;
	}
	/**
	 * 获取进出口口岸
	 * @return
	 */
	public Boolean getCustoms() {
		return customs;
	}
	/**
	 * 设置进出口口岸
	 * @param customs
	 */
	public void setCustoms(Boolean customs) {
		this.customs = customs;
	}
	/**
	 * 获取进出口日期
	 * @return
	 */
	public Boolean getImpExpDate() {
		return impExpDate;
	}
	/**
	 * 设置进出口日期
	 * @param impExpDate
	 */
	public void setImpExpDate(Boolean impExpDate) {
		this.impExpDate = impExpDate;
	}
	/**
	 * 获取运输方式
	 * @return
	 */
	public Boolean getTransferMode() {
		return transferMode;
	}
	/**
	 * 设置运输方式
	 * @param transferMode
	 */
	public void setTransferMode(Boolean transferMode) {
		this.transferMode = transferMode;
	}
	/**
	 * 获取征免性质
	 * @return
	 */
	public Boolean getLevyKind() {
		return levyKind;
	}
	/**
	 * 设置征免性质
	 * @param levyKind
	 */
	public void setLevyKind(Boolean levyKind) {
		this.levyKind = levyKind;
	}
	/**
	 * 获取许可证号
	 * @return
	 */
	public Boolean getLicense() {
		return license;
	}
	/**
	 * 设置许可证号
	 * @param license
	 */
	public void setLicense(Boolean license) {
		this.license = license;
	}
	/**
	 * 获取起运国/运抵国
	 * @return
	 */
	public Boolean getCountryOfLoadingOrUnloading() {
		return countryOfLoadingOrUnloading;
	}
	/**
	 * 设置起运国/运抵国
	 * @param countryOfLoadingOrUnloading
	 */
	public void setCountryOfLoadingOrUnloading(Boolean countryOfLoadingOrUnloading) {
		this.countryOfLoadingOrUnloading = countryOfLoadingOrUnloading;
	}
	/**
	 * 获取境内目的地
	 * @return
	 */
	public Boolean getDomesticDestinationOrSource() {
		return domesticDestinationOrSource;
	}
	/**
	 * 设置境内目的地
	 * @param domesticDestinationOrSource
	 */
	public void setDomesticDestinationOrSource(Boolean domesticDestinationOrSource) {
		this.domesticDestinationOrSource = domesticDestinationOrSource;
	}
	/**
	 * 获取装货港/指运港
	 * @return
	 */
	public Boolean getPortOfLoadingOrUnloading() {
		return portOfLoadingOrUnloading;
	}
	/**
	 * 设置装货港/指运港
	 * @param portOfLoadingOrUnloading
	 */
	public void setPortOfLoadingOrUnloading(Boolean portOfLoadingOrUnloading) {
		this.portOfLoadingOrUnloading = portOfLoadingOrUnloading;
	}
	/**
	 * 获取核销单号
	 * @return
	 */
	public Boolean getAuthorizeFile() {
		return authorizeFile;
	}
	/**
	 * 设置核销单号
	 * @param authorizeFile
	 */
	public void setAuthorizeFile(Boolean authorizeFile) {
		this.authorizeFile = authorizeFile;
	}
	/**
	 * 获取成交方式 
	 * @return
	 */
	public Boolean getTransac() {
		return transac;
	}
	/**
	 * 设置成交方式 
	 * @param transac
	 */
	public void setTransac(Boolean transac) {
		this.transac = transac;
	}
	/**
	 * 获取总毛重
	 * @return
	 */
	public Boolean getGrossWeight() {
		return grossWeight;
	}
	/**
	 * 设置总毛重
	 * @param grossWeight
	 */
	public void setGrossWeight(Boolean grossWeight) {
		this.grossWeight = grossWeight;
	}
	/**
	 * 获取总净重
	 * @return
	 */
	public Boolean getNetWeight() {
		return netWeight;
	}
	/**
	 * 设置总净重
	 * @param netWeight
	 */
	public void setNetWeight(Boolean netWeight) {
		this.netWeight = netWeight;
	}
	/**
	 * 获取总件数
	 * @return
	 */
	public Boolean getCommodityNum() {
		return commodityNum;
	}
	/**
	 * 设置总件数
	 * @param commodityNum
	 */
	public void setCommodityNum(Boolean commodityNum) {
		this.commodityNum = commodityNum;
	}
	/**
	 * 获取集装箱号
	 * @return
	 */
	public Boolean getContainerNum() {
		return containerNum;
	}
	/**
	 * 设置集装箱号
	 * @param containerNum
	 */
	public void setContainerNum(Boolean containerNum) {
		this.containerNum = containerNum;
	}
	/**
	 * 获取随附单据
	 * @return
	 */
	public Boolean getAttachedBill() {
		return attachedBill;
	}
	/**
	 * 设置随附单据
	 * @param attachedBill
	 */
	public void setAttachedBill(Boolean attachedBill) {
		this.attachedBill = attachedBill;
	}
	/**
	 * 获取报送海关
	 * @return
	 */
	public Boolean getDeclarationCustoms() {
		return declarationCustoms;
	}
	/**
	 * 设置报送海关
	 * @param declarationCustoms
	 */
	public void setDeclarationCustoms(Boolean declarationCustoms) {
		this.declarationCustoms = declarationCustoms;
	}
	/**
	 * 获取备注证件代码
	 * @return
	 */
	public Boolean getCertificateCode() {
		return certificateCode;
	}
	/**
	 * 设置备注证件代码
	 * @param certificateCode
	 */
	public void setCertificateCode(Boolean certificateCode) {
		this.certificateCode = certificateCode;
	}
	/**
	 * 获取备注其他说明
	 * @return
	 */
	public Boolean getMemos() {
		return memos;
	}
	/**
	 * 设置备注其他说明
	 * @param memos
	 */
	public void setMemos(Boolean memos) {
		this.memos = memos;
	}
	/**
	 * 获取码头
	 * @return
	 */
	public Boolean getPredock() {
		return predock;
	}
	/**
	 * 设置码头
	 * @param predock
	 */
	public void setPredock(Boolean predock) {
		this.predock = predock;
	}
	/**
	 * 获取保税仓库
	 * @return
	 */
	public Boolean getBondedWarehouse() {
		return bondedWarehouse;
	}
	/**
	 * 设置保税仓库
	 */
	public void setBondedWarehouse(Boolean bondedWarehouse) {
		this.bondedWarehouse = bondedWarehouse;
	}
	/**
	 * 获取关联手册号
	 * @return
	 */
	public Boolean getRelativeManualNo() {
		return relativeManualNo;
	}
	/**
	 * 设置关联手册号
	 * @param relativeManualNo
	 */
	public void setRelativeManualNo(Boolean relativeManualNo) {
		this.relativeManualNo = relativeManualNo;
	}
	/**
	 * 获取关联报关单号
	 * @return
	 */
	public Boolean getRelativeId() {
		return relativeId;
	}
	/**
	 * 设置关联报关单号
	 * @param relativeId
	 */
	public void setRelativeId(Boolean relativeId) {
		this.relativeId = relativeId;
	}
	/**
	 * 获取关封号
	 * @return
	 */
	public Boolean getCustomsEnvelopBillNo() {
		return customsEnvelopBillNo;
	}
	/**
	 * 设置关封号
	 * @param customsEnvelopBillNo
	 */
	public void setCustomsEnvelopBillNo(Boolean customsEnvelopBillNo) {
		this.customsEnvelopBillNo = customsEnvelopBillNo;
	}
	/**
	 * 获取包装方式体
	 * @return
	 */
	public Boolean getWrapTypeDetail() {
		return wrapTypeDetail;
	}
	/**
	 * 设置包装方体体
	 * @param wrapTypeDetail
	 */
	public void setWrapTypeDetail(Boolean wrapTypeDetail) {
		this.wrapTypeDetail = wrapTypeDetail;
	}
	public Boolean getPreCustomsDeclarationCode() {
		return preCustomsDeclarationCode;
	}
	public void setPreCustomsDeclarationCode(Boolean preCustomsDeclarationCode) {
		this.preCustomsDeclarationCode = preCustomsDeclarationCode;
	}
	public Boolean getCustomdeclarationMun() {
		return CustomdeclarationMun;
	}
	public void setCustomdeclarationMun(Boolean customdeclarationMun) {
		CustomdeclarationMun = customdeclarationMun;
	}
	/**
	 * 设置录入员
	 * @return
	 */
	public Boolean getCreater() {
		return creater;
	}
	/**
	 * 设置录入员
	 * @return
	 */
	public void setCreater(Boolean creater) {
		this.creater = creater;
	}

	/**
	 * 设置供应商代码
	 */
	public Boolean getCustomerCode() {
		return customerCode;
	}

	/**
	 * 设置供应商代码
	 */
	public void setCustomerCode(Boolean customerCode) {
		this.customerCode = customerCode;
	}
	
	
}
