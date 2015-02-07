package com.bestway.fixasset.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.common.CommonUtils;

public class TempImportEquipmentContract implements Serializable{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	//表头
	/**
	 * 手册编号
	 */
	private String emsNo;
	
	/**
	 * 批文帐册号
	 */
	private String sancEmsNo;
	
	/**
	 * 帐册状态 APPLY_POR = "1"; 申请备案 WAIT_EAA = "2"; 等待审批 PROCESS_EXE = "3"; 正在执行
	 * CHANGING_EXE = "4"; 正在变更 CHANGING_CANCEL = "5"; 已经核销
	 */
	private String declareState;
	
	/**
	 * 开始有效期
	 */
	private String beginDate;
	
	/**
	 * 报关关区
	 */
	private String declareCustoms = null;
	
	/**
	 * 经营单位代码
	 */
	private String tradeCode;
	
	/**
	 * 经营单位名称
	 */
	private String tradeName;

	/**
	 * 贸易方式
	 */
	private String trade = null;

	/**
	 * 有效期限
	 */
	private String availabilityDate = null;

	/**
	 * 贸易国别
	 */
	private String tradeCountry = null;

	/**
	 * 延期期限
	 */
	private String deferDate = null;

	/**
	 * 企业地址
	 */
	private String enterpriseAddress = null;

	/**
	 * 联系人
	 */
	private String linkMan = null;

	/**
	 * 联系电话
	 */
	private String contactTel = null;

	/**
	 * 协义书号
	 */
	private String agreementNo = null;
	
	/**
	 * 进口合同号
	 */
	private String impContractNo = null;

	/**
	 * 出口合同号
	 */
	private String expContractNo = null;

	/**
	 * 设备总金额
	 */
	private String fixtureAmount = null;

	/**
	 * 币制
	 */
	private String curr = null;

	/**
	 * 监管费用
	 */
	private String wardshipFee = null;

	/**
	 * 监管费率
	 */
	private String wardshipRate = null;

	/**
	 * 成交方式
	 */
	private String transac = null;

	/**
	 * 进口口岸
	 */
	private String iePort1 = null;
	//----------
	/**
	 * 外商公司
	 */
	private String outTradeCo;
	
	/**
	 * 征免性质
	 */
	private String levyKindName;
	
	/**
	 * 保税方式
	 */
	private String payModeName;
	
	/**
	 * 贸易方式
	 */
	private String tradeType;
	
	/**
	 * 贸易国别
	 */
	private String tradeCountryName;
	
	//-----------

	/**
	 * 进口口岸2
	 */
	private String iePort2 = null;

	/**
	 * 进口口岸3
	 */
	private String iePort3 = null;

	/**
	 * 进口口岸4
	 */
	private String iePort4 = null;

	/**
	 * 进口口岸5
	 */
	private String iePort5 = null;
	
	/**
	 * 初审人
	 */
	private String firstTrialer = null;

	/**
	 * 复审人
	 */
	private String retrialer = null;

	/**
	 * 审批日期
	 */
	private String approveDate = null;

	/**
	 * 备注
	 */
	private String memo = null;

	/**
	 * 是否被选中
	 */
	private String isSelected = "false";
	
	/**
	 * 类型
	 * 
	 */
	private String type="来料设备";
	/**
	 * 收货单位代码
	 */
	private String machCode;

	/**
	 * 收货单位名称
	 */
	private String machName;
	
	/**
	 * 设备项数
	 */
	private String machineCount;
	
	/**
	 * 重点标志
	 */
	private String emphasisFlag = null;
	
	
	//表体
	  /**
     * 分协议号
     */
    private String secondProtocol;
    
    /**
     * 进口日期
     */
    private String importDate;
	/**
	 * 商品序号
	 */
	private String seqNum = null;
	
	/**
	 * 商品名称
	 */
	private String name = null;
	
	/**
	 * 规格型号
	 */
	private String spec = null;
	
	/**
	 * 计量单位
	 */
	private String unitName = null;

	/**
	 * 单价
	 */
	private String declarePrice = null;
	
	/**
	 * 数量 
	 */
	private String amount = null;
	
	/**
	 * 总金额
	 */
	private String totalPrice = null;
	
	/**
	 * 商品编码
	 */
	private String complexCode = null;
	
	/**
	 * 法定单位
	 */
	private String firstUnitName = null;
	
	/**
	 * 原产地
	 */
	private String countryName = null;
	
	/**
	 * 征免方式
	 */
	private String levyMode = null;
	
	/**
	 * 设备类型
	 */
	private String equipmentType = null;
	
	/**
	 * 详细型号规格
	 */
	private String detailedSpec = null;
	
	/**
	 * 表体备注
	 */
	private String itemMemo = null;
	
	/**
	 * 错误提示
	 */
	private String errinfo = null;

	public String getDeclareCustoms() {
		return declareCustoms;
	}

	public void setDeclareCustoms(String declareCustoms) {
		this.declareCustoms = declareCustoms;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getAvailabilityDate() {
		return availabilityDate;
	}

	public void setAvailabilityDate(String availabilityDate) {
		this.availabilityDate = availabilityDate;
	}

	public String getTradeCountry() {
		return tradeCountry;
	}

	public void setTradeCountry(String tradeCountry) {
		this.tradeCountry = tradeCountry;
	}

	public String getDeferDate() {
		return deferDate;
	}

	public void setDeferDate(String deferDate) {
		this.deferDate = deferDate;
	}

	public String getEnterpriseAddress() {
		return enterpriseAddress;
	}

	public void setEnterpriseAddress(String enterpriseAddress) {
		this.enterpriseAddress = enterpriseAddress;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	public String getImpContractNo() {
		return impContractNo;
	}

	public void setImpContractNo(String impContractNo) {
		this.impContractNo = impContractNo;
	}

	public String getExpContractNo() {
		return expContractNo;
	}

	public void setExpContractNo(String expContractNo) {
		this.expContractNo = expContractNo;
	}

	public String getFixtureAmount() {
		return fixtureAmount;
	}

	public void setFixtureAmount(String fixtureAmount) {
		this.fixtureAmount = fixtureAmount;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public String getWardshipFee() {
		return wardshipFee;
	}

	public void setWardshipFee(String wardshipFee) {
		this.wardshipFee = wardshipFee;
	}

	public String getWardshipRate() {
		return wardshipRate;
	}

	public void setWardshipRate(String wardshipRate) {
		this.wardshipRate = wardshipRate;
	}

	public String getTransac() {
		return transac;
	}

	public void setTransac(String transac) {
		this.transac = transac;
	}

	public String getIePort1() {
		return iePort1;
	}

	public void setIePort1(String iePort1) {
		this.iePort1 = iePort1;
	}

	public String getIePort2() {
		return iePort2;
	}

	public void setIePort2(String iePort2) {
		this.iePort2 = iePort2;
	}

	public String getIePort3() {
		return iePort3;
	}

	public void setIePort3(String iePort3) {
		this.iePort3 = iePort3;
	}

	public String getIePort4() {
		return iePort4;
	}

	public void setIePort4(String iePort4) {
		this.iePort4 = iePort4;
	}

	public String getIePort5() {
		return iePort5;
	}

	public void setIePort5(String iePort5) {
		this.iePort5 = iePort5;
	}

	public String getFirstTrialer() {
		return firstTrialer;
	}

	public void setFirstTrialer(String firstTrialer) {
		this.firstTrialer = firstTrialer;
	}

	public String getRetrialer() {
		return retrialer;
	}

	public void setRetrialer(String retrialer) {
		this.retrialer = retrialer;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDeclarePrice() {
		return declarePrice;
	}

	public void setDeclarePrice(String declarePrice) {
		this.declarePrice = declarePrice;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getComplexCode() {
		return complexCode;
	}

	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}

	public String getFirstUnitName() {
		return firstUnitName;
	}

	public void setFirstUnitName(String firstUnitName) {
		this.firstUnitName = firstUnitName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public String getSancEmsNo() {
		return sancEmsNo;
	}

	public void setSancEmsNo(String sancEmsNo) {
		this.sancEmsNo = sancEmsNo;
	}

	public String getDeclareState() {
		return declareState;
	}

	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getOutTradeCo() {
		return outTradeCo;
	}

	public void setOutTradeCo(String outTradeCo) {
		this.outTradeCo = outTradeCo;
	}

	public String getLevyKindName() {
		return levyKindName;
	}

	public void setLevyKindName(String levyKindName) {
		this.levyKindName = levyKindName;
	}

	public String getPayModeName() {
		return payModeName;
	}

	public void setPayModeName(String payModeName) {
		this.payModeName = payModeName;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeCountryName() {
		return tradeCountryName;
	}

	public void setTradeCountryName(String tradeCountryName) {
		this.tradeCountryName = tradeCountryName;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	public String getLevyMode() {
		return levyMode;
	}

	public void setLevyMode(String levyMode) {
		this.levyMode = levyMode;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getDetailedSpec() {
		return detailedSpec;
	}

	public void setDetailedSpec(String detailedSpec) {
		this.detailedSpec = detailedSpec;
	}

	public String getMachCode() {
		return machCode;
	}

	public void setMachCode(String machCode) {
		this.machCode = machCode;
	}

	public String getMachName() {
		return machName;
	}

	public void setMachName(String machName) {
		this.machName = machName;
	}

	public String getMachineCount() {
		return machineCount;
	}

	public void setMachineCount(String machineCount) {
		this.machineCount = machineCount;
	}

	public String getItemMemo() {
		return itemMemo;
	}

	public void setItemMemo(String itemMemo) {
		this.itemMemo = itemMemo;
	}

	public String getEmphasisFlag() {
		return emphasisFlag;
	}

	public void setEmphasisFlag(String emphasisFlag) {
		this.emphasisFlag = emphasisFlag;
	}

	public String getSecondProtocol() {
		return secondProtocol;
	}

	public void setSecondProtocol(String secondProtocol) {
		this.secondProtocol = secondProtocol;
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	
}
