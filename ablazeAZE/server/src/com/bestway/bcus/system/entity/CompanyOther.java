/*
 * Created on 2004-7-6
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.entity;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.parametercode.BargainType;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放系统参数设置－其他参数设置资料
 * 
 * @author 001
 */
public class CompanyOther extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	// /**
	// * FPT连接模式 被动为：true 主动为：false;
	// */
	// private Boolean isFtpPASV = true;

	// /**
	// * 设置FPT连接的超时时间(秒)
	// */
	// private Integer ftpTimeOut = 60;

	private String month;
	/**
	 * 常用币制
	 */
	private Curr commonCurr;

	/**
	 * 核销提示天数
	 */
	private String cancelDay;

	/**
	 * 退换提示天数
	 */
	private String exitDay;

	/**
	 * 企业简称
	 */
	private String simpName;

	/**
	 * 报关单客户供应商是否自动带出相关信息
	 */
	private Boolean isAutoAmount = false;
	/**
	 * 新增申请单是否自动产生单据号码；
	 */
	private Boolean isAutoGetDjNo = false;
	/**
	 * 各报表数据以千分号来显
	 */
	private Boolean isAutoshowThousandthsign = false;

	/**
	 * 申请单新增商品时过滤
	 */
	private Boolean isFilter = false;
	/**
	 * 申请单文本导入默认计算总金额
	 */
	private Boolean isSumMoney = false;

	/**
	 * 报关单件数，净重，毛重是否自动由商品信息带出
	 */
	private Boolean isAutoWeight = false;

	/**
	 * 报关单净重，毛重是否小数位取整
	 */
	private Boolean isAutoWeightRound = false;

	/**
	 * 报关单检查中查看净重，毛重与表头总数量是否一致
	 */
	private Boolean isCheckWeight = false;
	/**
	 * 直接、转厂出口报关单合同余量包含退厂返工-返工复出,默认不勾项
	 */
	private Boolean isCludeReturn = false;

	/**
	 * 新增归并关系是否自动带出归并前
	 */
	private Boolean isAutoNewMergerBefore = false;

	/**
	 * 报关单商品单价小数位
	 */
	private Integer customPriceNum = 4;

	/**
	 * 报关单商品总价小数位
	 */
	private Integer customTotalNum = 4;

	/**
	 * 报关单商品数量小数位
	 */
	private Integer customAmountNum = 4;

	/**
	 * 申请单商品单价小数位
	 */
	private Integer customPriceNumSq = 4;
	/**
	 * 申请单商品总价小数位
	 */
	private Integer customTotalNumSq = 2;

	/**
	 * 申请单数量小数位
	 */
	private Integer customAmountNumSq = 4;
	/* 合同设置 */

	/**
	 * 申报关区号
	 */
	private Customs customNo;

	/**
	 * 批文号
	 */
	private String sancEmsNo;

	/**
	 * 进口口岸1
	 */
	private Customs iePort1;

	/**
	 * 进口口岸2
	 */
	private Customs iePort2;

	/**
	 * 进口口岸3
	 */
	private Customs iePort3;

	/**
	 * 进口口岸4
	 */
	private Customs iePort4;

	/**
	 * 进口口岸5
	 */
	private Customs iePort5;

	/**
	 * 合同类型
	 */
	private BargainType bargainType;

	/**
	 * 贸易方式
	 */
	private Trade trade;

	/**
	 * 征免性质
	 */
	private LevyKind levyKind;

	/**
	 * 贸易国别
	 */
	private Country tradeCountry;

	/**
	 * 合同备注
	 */
	private String note;

	/**
	 * 消费国
	 */
	private Country country;

	/**
	 * 合同性质
	 */
	private String bargainKind;

	/**
	 * 成交方式
	 */
	private Transac transac;

	/**
	 * 重量参数
	 */
	private Double weightPara;

	/* 报关单设置 */

	/**
	 * 申报单位
	 */
	private Company declarationCompany;

	/**
	 * 最大报关单预录入号
	 */
	private String preCustomsDeclarationCode;

	/**
	 * 是否手动输入于录入号
	 */
	private Boolean isManualPreCode;

	/**
	 * 随附单据是否打印全称
	 */
	private Boolean printAll = true;

	/**
	 * 购货单位
	 */
	private Brief manufacturer;

	/**
	 * 企业运输对应发运港
	 */
	private PortLin port1;

	/**
	 * 汽车运输对应转运港
	 */
	private PortLin port2;

	/**
	 * 江海运输对应发运港
	 */
	private PortLin port3;

	/**
	 * 江海运输对应转运港
	 */
	private PortLin port4;
	/**
	 * 运输工具是否可重复输入
	 */
	private Boolean isReConveyance; // 运输工具是否可重复输入
	/**
	 * 报关单是否自动带证件代码
	 */
	private Boolean isCustomAutoAttachedBill;
	/**
	 * 分页显示时，每页显示的项数
	 */
	private Integer pageSize;

	/**
	 * 合同小数位
	 */
	private Integer contractBit = 5;
	/**
	 * 报表小数位
	 */
	private Integer reportBit = 5;

	/**
	 * 工厂BOM转报关BOM后保留小数位
	 */
	private Integer bomChangeBit = 6;

	// 数据导入接口参数
	/**
	 * 是否发送邮件
	 */
	private Boolean isSendMail;

	/**
	 * 发件箱
	 */
	private String sendMail;

	/**
	 * 是否允许报关单明细超过20项
	 */
	private Boolean isAllowBGDDetailExceed20 = false;
	/**
	 * 乐观锁
	 */
	private Integer optLock = 0;
	/**
	 * 运输工具额外位数（不包含@，除13、16默认外）
	 */
	private Integer transportTool;
	/**
	 * 报关单(打印)默认发运港
	 */
	private String sendCustoms;

	/**
	 * 发票（太平）默认地址
	 */
	private String invoiceAddress;
	/**
	 * 进出口报关单转厂业务是否强制打印关封号
	 */
	private Boolean isSaveCustomsEnvelopBillNo;

	/**
	 * 报关单打印取规范申报规格
	 */
	private Boolean isSpecification;
	
	/**
	 * 【进口装箱单/出口加工发票】打印显示购货单位/发货单位
	 */
    private Boolean isExportPackinglistOrInvoice;
	
    /**
	 * 打印报关单是否显示【转关附加信息】
	 */
    private Boolean isTransitadd;
    // --------------------
	/**
	 * http远程服务地址
	 */
    private String httpAddress;

	/**
	 * http用户名
	 */
	private String httpTcsUserName;

	/**
	 * http密码
	 */
	private String httpTcsPwd;
	/**
	 * http用户名
	 */
	private String httpFptUserName;
	/**
	 * http密码
	 */
	private String httpFptPwd;
	
	/**
	 * 运维服务器地址
	 */
	private String pisAddress;
	/**
	 * 运维服务器端口
	 */
	private Integer pisPort;

	/**
	 * proxy远程服务地址
	 */
	private String proxyAddress;
	/**
	 * proxy远程服务端口
	 */
	private Integer proxyPort;
	/**
	 * proxy用户名
	 */
	private String proxyUserName;
	/**
	 * proxy密码
	 */
	private String proxyPwd;
	
	/**
	 * 获取【进口装箱单/出口加工发票】打印显示购货单位/发货单位
	 */
	public Boolean getIsExportPackinglistOrInvoice() {
		return isExportPackinglistOrInvoice;
	}

	/**
	 * 设置【进口装箱单/出口加工发票】打印显示购货单位/发货单位
	 */
	public void setIsExportPackinglistOrInvoice(Boolean isExportPackinglistOrInvoice) {
		this.isExportPackinglistOrInvoice = isExportPackinglistOrInvoice;
	}

	/**
	 * 设置报关单打印取规范申报规格
	 */
	public Boolean getIsSpecification() {
		return isSpecification;
	}

	/**
	 * 获取报关单打印取规范申报规格
	 */
	public void setIsSpecification(Boolean isSpecification) {
		this.isSpecification = isSpecification;
	}

	/**
	 * 设置 进出口报关单转厂业务是否强制打印关封号
	 */
	public Boolean getIsSaveCustomsEnvelopBillNo() {
		return isSaveCustomsEnvelopBillNo;
	}

	/**
	 * 获取 进出口报关单转厂业务是否强制打印关封号
	 */
	public void setIsSaveCustomsEnvelopBillNo(Boolean isSaveCustomsEnvelopBillNo) {
		this.isSaveCustomsEnvelopBillNo = isSaveCustomsEnvelopBillNo;
	}

	/**
	 * 获取报关单检查中查看净重，毛重与表头总数量是否一致
	 * 
	 * @return
	 */
	public Boolean getIsCheckWeight() {
		return isCheckWeight;
	}

	/**
	 * 设置报关单检查中查看净重，毛重与表头总数量是否一致
	 * 
	 * @param isCheckWeight
	 */
	public void setIsCheckWeight(Boolean isCheckWeight) {
		this.isCheckWeight = isCheckWeight;
	}

	public Integer getOptLock() {
		return optLock;
	}

	public void setOptLock(Integer optLock) {
		this.optLock = optLock;
	}

	/**
	 * 获取合同性质
	 * 
	 * @return Returns 合同性质
	 */
	public String getBargainKind() {
		return bargainKind;
	}

	/**
	 * 设置合同性质
	 * 
	 * @param bargainKind
	 *            合同性质
	 */
	public void setBargainKind(String bargainKind) {
		this.bargainKind = bargainKind;
	}

	/**
	 * 获取合同类型
	 * 
	 * @return bargainType 合同类型
	 */
	public BargainType getBargainType() {
		return bargainType;
	}

	/**
	 * 设置合同类型
	 * 
	 * @param bargainType
	 *            合同类型
	 */
	public void setBargainType(BargainType bargainType) {
		this.bargainType = bargainType;
	}

	/**
	 * 获取核销提示天数
	 * 
	 * @return cancelDay 核销提示天数
	 */
	public String getCancelDay() {
		return cancelDay;
	}

	/**
	 * 设置核销提示天数
	 * 
	 * @param cancelDay
	 *            核销提示天数
	 */
	public void setCancelDay(String cancelDay) {
		this.cancelDay = cancelDay;
	}

	/**
	 * 获取常用币制
	 * 
	 * @return commonCurr 常用币制
	 */
	public Curr getCommonCurr() {
		return commonCurr;
	}

	/**
	 * 设置常用币制
	 * 
	 * @param commonCurr
	 *            常用币制
	 */
	public void setCommonCurr(Curr commonCurr) {
		this.commonCurr = commonCurr;
	}

	/**
	 * 获取最大报关单预录入号
	 * 
	 * @return preCustomsDeclarationCode 最大报关单预录入号
	 */
	public String getPreCustomsDeclarationCode() {
		return preCustomsDeclarationCode;
	}

	/**
	 * 设置最大报关单预录入号
	 * 
	 * @param preCustomsDeclarationCode
	 *            最大报关单预录入号
	 */
	public void setPreCustomsDeclarationCode(String preCustomsDeclarationCode) {
		this.preCustomsDeclarationCode = preCustomsDeclarationCode;
	}

	/**
	 * 获取消费国，表体使用
	 * 
	 * @return country 消费国，表体使用
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 设置消费国，表体使用
	 * 
	 * @param country
	 *            消费国，表体使用
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * 获取申报关区号
	 * 
	 * @return customNo 申报关区号
	 */
	public Customs getCustomNo() {
		return customNo;
	}

	/**
	 * 设置申报关区号
	 * 
	 * @param customNo
	 *            申报关区号
	 */
	public void setCustomNo(Customs customNo) {
		this.customNo = customNo;
	}

	/**
	 * 获取退换提示天数
	 * 
	 * @return exitDay 退换提示天数
	 */
	public String getExitDay() {
		return exitDay;
	}

	/**
	 * 设置退换提示天数
	 * 
	 * @param exitDay
	 *            退换提示天数
	 */
	public void setExitDay(String exitDay) {
		this.exitDay = exitDay;
	}

	/**
	 * 获取进口口岸1
	 * 
	 * @return iePort 进口口岸1
	 */
	public Customs getIePort1() {
		return iePort1;
	}

	/**
	 * 设置进口口岸1
	 * 
	 * @param iePort1
	 *            进口口岸1
	 */
	public void setIePort1(Customs iePort1) {
		this.iePort1 = iePort1;
	}

	/**
	 * 获取征免性质
	 * 
	 * @return levyKind 征免性质
	 */
	public LevyKind getLevyKind() {
		return levyKind;
	}

	/**
	 * 设置征免性质
	 * 
	 * @param levyKind
	 *            征免性质
	 */
	public void setLevyKind(LevyKind levyKind) {
		this.levyKind = levyKind;
	}

	/**
	 * 获取购货单位
	 * 
	 * @return manufacturer 购货单位
	 */
	public Brief getManufacturer() {
		return manufacturer;
	}

	/**
	 * 设置购货单位
	 * 
	 * @param manufacturer
	 *            购货单位
	 */
	public void setManufacturer(Brief manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * 获取合同备注
	 * 
	 * @return note 合同备注
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置合同备注
	 * 
	 * @param note
	 *            合同备注
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取企业运输对应发运港
	 * 
	 * @return port1 企业运输对应发运港
	 */
	public PortLin getPort1() {
		return port1;
	}

	/**
	 * 设置企业运输对应发运港
	 * 
	 * @param port1
	 *            企业运输对应发运港
	 */
	public void setPort1(PortLin port1) {
		this.port1 = port1;
	}

	/**
	 * 获取汽车运输对应转运港
	 * 
	 * @return port2 汽车运输对应转运港
	 */
	public PortLin getPort2() {
		return port2;
	}

	/**
	 * 设置汽车运输对应转运港
	 * 
	 * @param port2
	 *            汽车运输对应转运港
	 */
	public void setPort2(PortLin port2) {
		this.port2 = port2;
	}

	/**
	 * 获取江海运输对应发运港
	 * 
	 * @return port3 江海运输对应发运港
	 */
	public PortLin getPort3() {
		return port3;
	}

	/**
	 * 设置江海运输对应发运港
	 * 
	 * @param port3
	 *            江海运输对应发运港
	 */
	public void setPort3(PortLin port3) {
		this.port3 = port3;
	}

	/**
	 * 获取江海运输对应转运港
	 * 
	 * @return port4 江海运输对应转运港
	 */
	public PortLin getPort4() {
		return port4;
	}

	/**
	 * 设置江海运输对应转运港
	 * 
	 * @param port4
	 *            江海运输对应转运港
	 */
	public void setPort4(PortLin port4) {
		this.port4 = port4;
	}

	/**
	 * 获取批文号
	 * 
	 * @return sancEmsNo 批文号
	 */
	public String getSancEmsNo() {
		return sancEmsNo;
	}

	/**
	 * 设置批文号
	 * 
	 * @param sancEmsNo
	 *            批文号
	 */
	public void setSancEmsNo(String sancEmsNo) {
		this.sancEmsNo = sancEmsNo;
	}

	/**
	 * 获取企业简称
	 * 
	 * @return simpName 企业简称
	 */
	public String getSimpName() {
		return simpName;
	}

	/**
	 * 设置企业简称
	 * 
	 * @param simpName
	 *            企业简称
	 */
	public void setSimpName(String simpName) {
		this.simpName = simpName;
	}

	/**
	 * 获取贸易方式
	 * 
	 * @return trade 贸易方式
	 */
	public Trade getTrade() {
		return trade;
	}

	/**
	 * 设置贸易方式
	 * 
	 * @param trade
	 *            贸易方式
	 */
	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	/**
	 * 获取贸易国别
	 * 
	 * @return tradeCountry 贸易国别
	 */
	public Country getTradeCountry() {
		return tradeCountry;
	}

	/**
	 * 设置贸易国别
	 * 
	 * @param tradeCountry
	 *            贸易国别
	 */
	public void setTradeCountry(Country tradeCountry) {
		this.tradeCountry = tradeCountry;
	}

	/**
	 * 获取成交方式
	 * 
	 * @return transac 成交方式
	 */
	public Transac getTransac() {
		return transac;
	}

	/**
	 * 设置成交方式
	 * 
	 * @param transac
	 *            成交方式
	 */
	public void setTransac(Transac transac) {
		this.transac = transac;
	}

	/**
	 * 获取申报单位
	 * 
	 * @return declarationCompany 申报单位
	 */
	public Company getDeclarationCompany() {
		return declarationCompany;
	}

	/**
	 * 设置申报单位
	 * 
	 * @param declarationCompany
	 *            申报单位
	 */
	public void setDeclarationCompany(Company declarationCompany) {
		this.declarationCompany = declarationCompany;
	}

	/**
	 * 获取重量参数
	 * 
	 * @return weightPara 重量参数
	 */
	public Double getWeightPara() {
		return weightPara;
	}

	/**
	 * 设置重量参数
	 * 
	 * @param weightPara
	 *            重量参数
	 */
	public void setWeightPara(Double weightPara) {
		this.weightPara = weightPara;
	}

	/**
	 * 获取报关单件数，净毛重是否自动计算出来
	 * 
	 * @return isAutoAmount 报关单件数，净毛重是否自动计算出来
	 */
	public Boolean getIsAutoAmount() {
		return isAutoAmount;
	}

	/**
	 * 设置报关单件数，净毛重是否自动计算出来
	 * 
	 * @param isAutoAmount
	 *            报关单件数，净毛重是否自动计算出来
	 */
	public void setIsAutoAmount(Boolean isAutoAmount) {
		this.isAutoAmount = isAutoAmount;
	}

	/**
	 * 获取进口口岸2
	 * 
	 * @return iePort2 进口口岸2
	 */
	public Customs getIePort2() {
		return iePort2;
	}

	/**
	 * 设置进口口岸2
	 * 
	 * @param iePort2
	 *            进口口岸2
	 */
	public void setIePort2(Customs iePort2) {
		this.iePort2 = iePort2;
	}

	/**
	 * 获取进口口岸3
	 * 
	 * @return iePort3 进口口岸3
	 */
	public Customs getIePort3() {
		return iePort3;
	}

	/**
	 * 设置进口口岸3
	 * 
	 * @param iePort3
	 *            进口口岸3
	 */
	public void setIePort3(Customs iePort3) {
		this.iePort3 = iePort3;
	}

	/**
	 * 获取进口口岸4
	 * 
	 * @return iePort4 进口口岸4
	 */
	public Customs getIePort4() {
		return iePort4;
	}

	/**
	 * 设置进口口岸4
	 * 
	 * @param iePort4
	 *            进口口岸4
	 */
	public void setIePort4(Customs iePort4) {
		this.iePort4 = iePort4;
	}

	/**
	 * 获取进口口岸5
	 * 
	 * @return iePort5 进口口岸5
	 */
	public Customs getIePort5() {
		return iePort5;
	}

	/**
	 * 设置进口口岸5
	 * 
	 * @param iePort5
	 *            进口口岸5
	 */
	public void setIePort5(Customs iePort5) {
		this.iePort5 = iePort5;
	}

	/**
	 * 获取新增归并关系是否自动带出归并前
	 * 
	 * @return isAutoNewMergerBefore 新增归并关系是否自动带出归并前
	 */
	public Boolean getIsAutoNewMergerBefore() {
		return isAutoNewMergerBefore;
	}

	/**
	 * 设置新增归并关系是否自动带出归并前
	 * 
	 * @param isAutoNewMergerBefore
	 *            新增归并关系是否自动带出归并前
	 */
	public void setIsAutoNewMergerBefore(Boolean isAutoNewMergerBefore) {
		this.isAutoNewMergerBefore = isAutoNewMergerBefore;
	}

	public Boolean getIsReConveyance() {
		return isReConveyance;
	}

	public void setIsReConveyance(Boolean isReConveyance) {
		this.isReConveyance = isReConveyance;
	}

	public Boolean getIsAutoWeight() {
		return isAutoWeight;
	}

	public void setIsAutoWeight(Boolean isAutoWeight) {
		this.isAutoWeight = isAutoWeight;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Boolean getIsManualPreCode() {
		return isManualPreCode;
	}

	public void setIsManualPreCode(Boolean isManualPreCode) {
		this.isManualPreCode = isManualPreCode;
	}

	public Boolean getIsAutoGetDjNo() {
		return isAutoGetDjNo;
	}

	public void setIsAutoGetDjNo(Boolean isAutoGetDjNo) {
		this.isAutoGetDjNo = isAutoGetDjNo;
	}

	public Integer getContractBit() {
		return contractBit;
	}

	public void setContractBit(Integer contractBit) {
		this.contractBit = contractBit;
	}

	public Integer getReportBit() {
		return reportBit;
	}

	public void setReportBit(Integer reportBit) {
		this.reportBit = reportBit;
	}

	public Boolean getIsAutoshowThousandthsign() {
		return isAutoshowThousandthsign;
	}

	public void setIsAutoshowThousandthsign(Boolean isAutoshowThousandthsign) {
		this.isAutoshowThousandthsign = isAutoshowThousandthsign;
	}

	public Boolean getIsCustomAutoAttachedBill() {
		return isCustomAutoAttachedBill;
	}

	public void setIsCustomAutoAttachedBill(Boolean isCustomAutoAttachedBill) {
		this.isCustomAutoAttachedBill = isCustomAutoAttachedBill;
	}

	public Boolean getPrintAll() {
		if (printAll == null) {
			return true;
		}
		return printAll;
	}

	public void setPrintAll(Boolean printAll) {
		this.printAll = printAll;
	}

	public String getMonth() {
		if (month == null || "".equals(month)) {
			return "30";
		}
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getCustomPriceNum() {
		if (customPriceNum == null) {
			return new Integer(4);
		}
		return customPriceNum;
	}

	public void setCustomPriceNum(Integer customPriceNum) {
		this.customPriceNum = customPriceNum;
	}

	public Integer getCustomAmountNum() {
		if (customAmountNum == null) {
			return new Integer(4);
		}
		return customAmountNum;
	}

	public void setCustomAmountNum(Integer customAmountNum) {
		this.customAmountNum = customAmountNum;
	}

	/**
	 * 总金额
	 * 
	 * @return
	 */
	public Integer getCustomTotalNum() {
		if (customTotalNum == 4 || customTotalNum == 3) {
			return new Integer(2);
		}
		return customTotalNum;
	}

	public void setCustomTotalNum(Integer customTotalNum) {
		this.customTotalNum = customTotalNum;
	}

	public Integer getBomChangeBit() {
		return bomChangeBit;
	}

	public void setBomChangeBit(Integer bomChangeBit) {
		this.bomChangeBit = bomChangeBit;
	}

	public Boolean getIsCludeReturn() {
		if (isCludeReturn == null) {
			return false;
		}
		return isCludeReturn;
	}

	public void setIsCludeReturn(Boolean isCludeReturn) {
		this.isCludeReturn = isCludeReturn;
	}

	public Boolean getIsSendMail() {
		if (isSendMail == null)
			return false;
		return isSendMail;
	}

	public void setIsSendMail(Boolean isSendMail) {
		this.isSendMail = isSendMail;
	}

	public String getSendMail() {
		if (sendMail == null) {
			return "";
		}
		return sendMail;
	}

	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}

	public Boolean getIsAllowBGDDetailExceed20() {
		return isAllowBGDDetailExceed20;
	}

	public void setIsAllowBGDDetailExceed20(Boolean isAllowBGDDetailExceed20) {
		this.isAllowBGDDetailExceed20 = isAllowBGDDetailExceed20;
	}

	public Boolean getIsAutoWeightRound() {
		return isAutoWeightRound;
	}

	public void setIsAutoWeightRound(Boolean isAutoWeightRound) {
		this.isAutoWeightRound = isAutoWeightRound;
	}

	/**
	 * 获取运输工具额外位数（不包含@，除13、16默认外）
	 */
	public Integer getTransportTool() {
		return transportTool;
	}

	/**
	 * 设置运输工具额外位数（不包含@，除13、16默认外）
	 */
	public void setTransportTool(Integer transportTool) {
		this.transportTool = transportTool;
	}

	public String getSendCustoms() {
		return sendCustoms;
	}

	public void setSendCustoms(String sendCustoms) {
		this.sendCustoms = sendCustoms;
	}

	public Boolean getIsFilter() {
		return isFilter;
	}

	public void setIsFilter(Boolean isFilter) {
		this.isFilter = isFilter;
	}

	/**
	 * 申请单文本导入默认计算总金额
	 */
	public Boolean getIsSumMoney() {
		return isSumMoney;
	}

	/**
	 * 申请单文本导入默认计算总金额
	 */
	public void setIsSumMoney(Boolean isSumMoney) {
		this.isSumMoney = isSumMoney;
	}

	public Integer getCustomPriceNumSq() {
		if (customPriceNumSq == null) {
			return new Integer(4);
		}
		return customPriceNumSq;
	}

	public void setCustomPriceNumSq(Integer customPriceNumSq) {
		this.customPriceNumSq = customPriceNumSq;
	}

	public Integer getCustomTotalNumSq() {
		if (customTotalNumSq == null) {
			return new Integer(2);
		}
		return customTotalNumSq;
	}

	public void setCustomTotalNumSq(Integer customTotalNumSq) {
		this.customTotalNumSq = customTotalNumSq;
	}

	public Integer getCustomAmountNumSq() {
		if (customAmountNumSq == null) {
			return new Integer(4);
		}
		return customAmountNumSq;
	}

	public void setCustomAmountNumSq(Integer customAmountNumSq) {
		this.customAmountNumSq = customAmountNumSq;
	}

	// /**
	// * 获取FPT连接模式 isFtpPASV 被动为：true 主动为：false;
	// *
	// * @return isFtpPASV
	// */
	// public Boolean getIsFtpPASV() {
	// return isFtpPASV;
	// }
	//
	// /**
	// * 设置FPT连接模式
	// *
	// * @param isFtpPASV
	// * 被动为：true 主动为：false;
	// */
	// public void setIsFtpPASV(Boolean isFtpPASV) {
	// this.isFtpPASV = isFtpPASV;
	// }
	//
	// /**
	// * 获取超时时间
	// *
	// * @return
	// */
	// public Integer getFtpTimeOut() {
	// return ftpTimeOut;
	// }
	//
	// /**
	// * 设置超时时间
	// *
	// * @param timeOut
	// */
	// public void setFtpTimeOut(Integer ftpTimeOut) {
	// this.ftpTimeOut = ftpTimeOut;
	// }

	public String getHttpAddress() {
		return httpAddress;
	}

	public void setHttpAddress(String httpAddress) {
		this.httpAddress = httpAddress;
	}

	public String getHttpTcsUserName() {
		return httpTcsUserName;
	}

	public void setHttpTcsUserName(String httpTcsUserName) {
		this.httpTcsUserName = httpTcsUserName;
	}

	public String getHttpTcsPwd() {
		return httpTcsPwd;
	}

	public void setHttpTcsPwd(String httpTcsPwd) {
		this.httpTcsPwd = httpTcsPwd;
	}

	public String getProxyAddress() {
		return proxyAddress;
	}

	public void setProxyAddress(String proxyAddress) {
		this.proxyAddress = proxyAddress;
	}

	public Integer getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUserName() {
		return proxyUserName;
	}

	public void setProxyUserName(String proxyUserName) {
		this.proxyUserName = proxyUserName;
	}

	public String getProxyPwd() {
		return proxyPwd;
	}

	public void setProxyPwd(String proxyPwd) {
		this.proxyPwd = proxyPwd;
	}

	public String getHttpFptUserName() {
		return httpFptUserName;
	}

	public void setHttpFptUserName(String httpFptUserName) {
		this.httpFptUserName = httpFptUserName;
	}

	public String getHttpFptPwd() {
		return httpFptPwd;
	}

	public void setHttpFptPwd(String httpFptPwd) {
		this.httpFptPwd = httpFptPwd;
	}

	public Integer getPisPort() {
		return pisPort;
	}

	public void setPisPort(Integer httpPort) {
		this.pisPort = httpPort;
	}
	 /**
	 * 打印报关单是否显示【转关附加信息】
	 */
	public Boolean getIsTransitadd() {
		return isTransitadd;
	}
	 /**
	 * 打印报关单是否显示【转关附加信息】
	 */
	public void setIsTransitadd(Boolean isTransitadd) {
		this.isTransitadd = isTransitadd;
	}

	public String getPisAddress() {
		return pisAddress;
	}

	public void setPisAddress(String pisAddress) {
		this.pisAddress = pisAddress;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}
}
