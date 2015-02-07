/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.qp.entity;

/**
 * 存放电子手册通关备案里的表头资料
 * 
 * @author yp
 */
public class DzscQPEmsPorBillHead implements java.io.Serializable {

	/**
	 * 手册编号
	 */
	private String emsNo;

	/**
	 * 批文帐册号
	 */
	private String sancEmsNo;

	/**
	 * 经营单位代码
	 */
	private String tradeCode;

	/**
	 * 经营单位名称
	 */
	private String tradeName;

	/**
	 * 收货单位代码
	 */
	private String machCode;

	/**
	 * 收货单位名称
	 */
	private String machName;

	/**
	 * 开始有效期(yyyy-MM-dd HH:mm:ss)
	 */
	private String beginDate;

	/**
	 * 结束有效期(yyyy-MM-dd HH:mm:ss)
	 */
	private String endDate;

	/**
	 * 外商公司
	 */
	private String outTradeCo;

	/**
	 * 征免方式
	 */
	private String levyModeCode;

	/**
	 * 征面性质
	 */
	private String levyKindCode;

	/**
	 * 保税方式
	 */
	private String payModeCode;

	/**
	 * 加工种类
	 */
	private String machiningTypeCode;

	/**
	 * 批准文号
	 */
	private String emsApprNo;

	/**
	 * 收货地区
	 */
	private String receiveAreaCode = null;

	/**
	 * 投资方式
	 */
	private String investModeCode = null;

	/**
	 * 引进方式
	 */
	private String fetchInModeCode = null;

	/**
	 * 内销比
	 */
	private Double inSaleRate = null;

	/**
	 * 申报时间(yyyy-MM-dd HH:mm:ss)
	 */
	private String declareDate;

	/**
	 * 原料项目个数
	 */
	private Integer materielItemCount = null;

	/**
	 * 设备个数
	 */
	private Integer machineCount = null;

	/**
	 * 成品项目个数
	 */
	private Integer productItemCount = null;

	/**
	 * 管理对象
	 */
	protected String manageObject;

	/**
	 * 限制类标志 ADJUST_BEFORE_EMS = "1"; 调整前旧手册 ADJUST_AFTER_EMS = "2"; 调整后新手册
	 * ACOUNT_BOOK_USE = "3"; 台账专用手册
	 */
	private String limitFlag;

	/**
	 * 单耗申报环节 1.备案 2.出口前(默认) 3.报核前
	 */
	private String equipMode;

	/**
	 * 申报关区号
	 */
	private String customNoCode = null;

	/**
	 * 主管外经部门
	 */
	private String redDepCode = null;

	/**
	 * 手册类型
	 */
	private String emsType;

	// /**
	// * 合同性质,对应列名bargainKind
	// */
	// private String bargainKind = null;
	/**
	 * 企业内部编号
	 */
	private String copTrNo;

	// /**
	// * 预录入号,对应列名preRecordNo
	// */
	// private String preRecordNo = null;

	/**
	 * 进出口岸
	 */
	private String iePort1Code = null;

	/**
	 * 延期期限(yyyy-MM-dd HH:mm:ss)
	 */
	private String deferDate = null;

	/**
	 * 核销日期(yyyy-MM-dd HH:mm:ss)
	 */
	private String destroyDate = null;

	/**
	 * 贸易方式
	 */
	private String tradeModeCode = null;

	/**
	 * 贸易国别
	 */
	private String tradeCountryCode = null;

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
	 * 协议书号
	 */
	private String agreementNo = null;

	/**
	 * 进口合同号
	 */
	private String ieContractNo = null;

	/**
	 * 出口合同号
	 */
	private String imContractNo = null;

	/**
	 * 进口总金额
	 */
	private Double imgAmount = null;

	/**
	 * 出口总金额
	 */
	private Double exgAmount = null;

	/**
	 * 币制
	 */
	private String currCode = null;

	/**
	 * 监管费率
	 */
	private Double wardshipRate = null;

	/**
	 * 监管费用
	 */
	private Double wardshipFee = null;

	/**
	 * 成交方式
	 */
	private String transacCode = null;

	/**
	 * 进口口岸2
	 */
	private String iePort2Code = null;

	/**
	 * 进口口岸3
	 */
	private String iePort3Code = null;

	/**
	 * 进口口岸4
	 */
	private String iePort4Code = null;

	/**
	 * 进口口岸5
	 */
	private String iePort5Code = null;

	/**
	 * 进口口岸6
	 */
	private String iePort6Code = null;
	/**
	 * 进口口岸7
	 */
	private String iePort7Code = null;
	/**
	 * 进口口岸8
	 */
	private String iePort8Code = null;
	/**
	 * 进口口岸9
	 */
	private String iePort9Code = null;
	/**
	 * 进口口岸10
	 */
	private String iePort10Code = null;

	/**
	 * 备注
	 */
	private String note = null;

	/**
	 * 企业内部物料编号
	 */
	private String copEntNo;

	/**
	 * 对应上本帐册号
	 */
	private String lastEmsNo;

	/**
	 * 其他手册号
	 */
	private String corrEmsNo;

	/**
	 * 是否被选中
	 */
	private Boolean isSelected = false;

	/**
	 * 录入员代号
	 */
	private String inputER = null;

	/**
	 * 获取协议书号
	 * 
	 * @return agreementNo 协议书号
	 */
	public String getAgreementNo() {
		return agreementNo;
	}

	/**
	 * 设置协议书号
	 * 
	 * @param agreementNo
	 *            协议书号
	 */
	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

//	/**
//	 * 设置审批人
//	 * 
//	 * @param approver
//	 *            审批人
//	 */
//	public void setApprover(String approver) {
//		this.approver = approver;
//	}

	//
	// /**
	// * 获取合同性质
	// *
	// * @return bargainKind 合同性质
	// */
	// public String getBargainKind() {
	// return bargainKind;
	// }
	//
	// /**
	// * 设置合同性质
	// *
	// * @param bargainKind
	// * 合同性质
	// */
	// public void setBargainKind(String bargainKind) {
	// this.bargainKind = bargainKind;
	// }
	//
	// /**
	// * 获取合同类型
	// *
	// * @return bargainType 合同类型
	// */
	// public BargainType getBargainType() {
	// return bargainType;
	// }
	//
	// /**
	// * 设置合同类型
	// *
	// * @param bargainType
	// * 合同类型
	// */
	// public void setBargainType(BargainType bargainType) {
	// this.bargainType = bargainType;
	// }

	/**
	 * 获取联系电话
	 * 
	 * @return contactTel 联系电话
	 */
	public String getContactTel() {
		return contactTel;
	}

	/**
	 * 设置联系电话
	 * 
	 * @param contactTel
	 *            联系电话
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	/**
	 * 获取企业地址
	 * 
	 * @return enterpriseAddress 企业地址
	 */
	public String getEnterpriseAddress() {
		return enterpriseAddress;
	}

	/**
	 * 设置企业地址
	 * 
	 * @param enterpriseAddress
	 *            企业地址
	 */
	public void setEnterpriseAddress(String enterpriseAddress) {
		this.enterpriseAddress = enterpriseAddress;
	}

	/**
	 * 获取出口总金额
	 * 
	 * @return exgAmount 出口总金额
	 */
	public Double getExgAmount() {
		return exgAmount;
	}

	/**
	 * 设置出口总金额
	 * 
	 * @param exgAmount
	 *            出口总金额
	 */
	public void setExgAmount(Double exgAmount) {
		this.exgAmount = exgAmount;
	}

	/**
	 * 获取进口合同号
	 * 
	 * @return ieContractNo 进口合同号
	 */
	public String getIeContractNo() {
		return ieContractNo;
	}

	/**
	 * 设置进口合同号
	 * 
	 * @param ieContractNo
	 *            进口合同号
	 */
	public void setIeContractNo(String ieContractNo) {
		this.ieContractNo = ieContractNo;
	}

	/**
	 * 获取出口合同号
	 * 
	 * @return imContractNo 出口合同号
	 */
	public String getImContractNo() {
		return imContractNo;
	}

	/**
	 * 设置出口合同号
	 * 
	 * @param imContractNo
	 *            出口合同号
	 */
	public void setImContractNo(String imContractNo) {
		this.imContractNo = imContractNo;
	}

	/**
	 * 获取进口总金额
	 * 
	 * @return imgAmount 进口总金额
	 */
	public Double getImgAmount() {
		return imgAmount;
	}

	/**
	 * 设置进口总金额
	 * 
	 * @param imgAmount
	 *            进口总金额
	 */
	public void setImgAmount(Double imgAmount) {
		this.imgAmount = imgAmount;
	}

	/**
	 * 获取联系人
	 * 
	 * @return linkMan 联系人
	 */
	public String getLinkMan() {
		return linkMan;
	}

	/**
	 * 设置联系人
	 * 
	 * @param linkMan
	 *            联系人
	 */
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	/**
	 * 获取备注
	 * 
	 * @return note 备注
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置备注
	 * 
	 * @param note
	 *            备注
	 */
	public void setNote(String note) {
		this.note = note;
	}

	// /**
	// * 获取合同预录号
	// *
	// * preContractCodePrefix 合同预录号前缀 preContractCodeSuffix 合同预录号后缀
	// *
	// * @return preContractCodePrefix＋preContractCodeSuffix 合同预录号
	// */
	// public String getPreRecordNo() {
	// if (preContractCodePrefix == null) {
	// preContractCodePrefix = "";
	// }
	// if (preContractCodeSuffix == null) {
	// preContractCodeSuffix = "";
	// }
	// return this.preContractCodePrefix + this.preContractCodeSuffix;
	// }

	// /**
	// * 设置合同预录号,preContractCodePrefix为合同预录号前缀, preContractCodeSuffix为合同预录号后缀
	// *
	// * @param preRecordNo
	// * 等于preContractCodePrefix＋preContractCodeSuffix 合同预录号
	// */
	// public void setPreRecordNo(String preRecordNo) {
	// if (preRecordNo == null || "".equals(preRecordNo.trim())) {
	// return;
	// }
	// this.setPreContractCodePrefix(preRecordNo.substring(0, 6));
	// this.setPreContractCodeSuffix(preRecordNo.substring(6, 12));
	// }

	/**
	 * 获取监管费用
	 * 
	 * @return wardshipFee 监管费用
	 */
	public Double getWardshipFee() {
		return wardshipFee;
	}

	/**
	 * 设置监管费用
	 * 
	 * @param wardshipFee
	 *            监管费用
	 */
	public void setWardshipFee(Double wardshipFee) {
		this.wardshipFee = wardshipFee;
	}

	/**
	 * 获取监管费率
	 * 
	 * @return wardshipRate 监管费率
	 */
	public Double getWardshipRate() {
		return wardshipRate;
	}

	/**
	 * 设置监管费率
	 * 
	 * @param wardshipRate
	 *            监管费率
	 */
	public void setWardshipRate(Double wardshipRate) {
		this.wardshipRate = wardshipRate;
	}


	public String getCopTrNo() {
		return copTrNo;
	}

	public void setCopTrNo(String copTrNo) {
		this.copTrNo = copTrNo;
	}

	public String getEmsType() {
		return emsType;
	}

	public void setEmsType(String emsType) {
		this.emsType = emsType;
	}

	// public String getContractEmsNo() {
	// return contractEmsNo;
	// }
	//
	// public void setContractEmsNo(String contractEmsNo) {
	// this.contractEmsNo = contractEmsNo;
	// }

	public String getCopEntNo() {
		return copEntNo;
	}

	public void setCopEntNo(String copEntNo) {
		this.copEntNo = copEntNo;
	}

	public String getCorrEmsNo() {
		return corrEmsNo;
	}

	public void setCorrEmsNo(String crrEmsNo) {
		this.corrEmsNo = crrEmsNo;
	}

	public String getLastEmsNo() {
		return lastEmsNo;
	}

	public void setLastEmsNo(String lastEmsNo) {
		this.lastEmsNo = lastEmsNo;
	}

	public String getInputER() {
		return inputER;
	}

	public void setInputER(String inputER) {
		this.inputER = inputER;
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

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOutTradeCo() {
		return outTradeCo;
	}

	public void setOutTradeCo(String outTradeCo) {
		this.outTradeCo = outTradeCo;
	}

	public String getLevyModeCode() {
		return levyModeCode;
	}

	public void setLevyModeCode(String levyModeCode) {
		this.levyModeCode = levyModeCode;
	}

	public String getLevyKindCode() {
		return levyKindCode;
	}

	public void setLevyKindCode(String levyKindCode) {
		this.levyKindCode = levyKindCode;
	}

	public String getPayModeCode() {
		return payModeCode;
	}

	public void setPayModeCode(String payModeCode) {
		this.payModeCode = payModeCode;
	}

	public String getMachiningTypeCode() {
		return machiningTypeCode;
	}

	public void setMachiningTypeCode(String machiningTypeCode) {
		this.machiningTypeCode = machiningTypeCode;
	}

	public String getEmsApprNo() {
		return emsApprNo;
	}

	public void setEmsApprNo(String emsApprNo) {
		this.emsApprNo = emsApprNo;
	}

	public String getReceiveAreaCode() {
		return receiveAreaCode;
	}

	public void setReceiveAreaCode(String receiveAreaCode) {
		this.receiveAreaCode = receiveAreaCode;
	}

	public String getInvestModeCode() {
		return investModeCode;
	}

	public void setInvestModeCode(String investModeCode) {
		this.investModeCode = investModeCode;
	}

	public String getFetchInModeCode() {
		return fetchInModeCode;
	}

	public void setFetchInModeCode(String fetchInModeCode) {
		this.fetchInModeCode = fetchInModeCode;
	}

	public Double getInSaleRate() {
		return inSaleRate;
	}

	public void setInSaleRate(Double inSaleRate) {
		this.inSaleRate = inSaleRate;
	}

	public String getDeclareDate() {
		return declareDate;
	}

	public void setDeclareDate(String declareDate) {
		this.declareDate = declareDate;
	}

	public Integer getMaterielItemCount() {
		return materielItemCount;
	}

	public void setMaterielItemCount(Integer materielItemCount) {
		this.materielItemCount = materielItemCount;
	}

	public Integer getMachineCount() {
		return machineCount;
	}

	public void setMachineCount(Integer machineCount) {
		this.machineCount = machineCount;
	}

	public Integer getProductItemCount() {
		return productItemCount;
	}

	public void setProductItemCount(Integer productItemCount) {
		this.productItemCount = productItemCount;
	}

	public String getManageObject() {
		return manageObject;
	}

	public void setManageObject(String manageObject) {
		this.manageObject = manageObject;
	}

	public String getLimitFlag() {
		return limitFlag;
	}

	public void setLimitFlag(String limitFlag) {
		this.limitFlag = limitFlag;
	}

	public String getEquipMode() {
		return equipMode;
	}

	public void setEquipMode(String equipMode) {
		this.equipMode = equipMode;
	}

	public String getCustomNoCode() {
		return customNoCode;
	}

	public void setCustomNoCode(String customNoCode) {
		this.customNoCode = customNoCode;
	}

	public String getRedDepCode() {
		return redDepCode;
	}

	public void setRedDepCode(String redDepCode) {
		this.redDepCode = redDepCode;
	}

	public String getIePort1Code() {
		return iePort1Code;
	}

	public void setIePort1Code(String iePort1Code) {
		this.iePort1Code = iePort1Code;
	}

	public String getDeferDate() {
		return deferDate;
	}

	public void setDeferDate(String deferDate) {
		this.deferDate = deferDate;
	}

	public String getDestroyDate() {
		return destroyDate;
	}

	public void setDestroyDate(String destroyDate) {
		this.destroyDate = destroyDate;
	}

	public String getTradeModeCode() {
		return tradeModeCode;
	}

	public void setTradeModeCode(String tradeModeCode) {
		this.tradeModeCode = tradeModeCode;
	}

	public String getTradeCountryCode() {
		return tradeCountryCode;
	}

	public void setTradeCountryCode(String tradeCountryCode) {
		this.tradeCountryCode = tradeCountryCode;
	}

	public String getCurrCode() {
		return currCode;
	}

	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}

	public String getTransacCode() {
		return transacCode;
	}

	public void setTransacCode(String transacCode) {
		this.transacCode = transacCode;
	}

	public String getIePort2Code() {
		return iePort2Code;
	}

	public void setIePort2Code(String iePort2Code) {
		this.iePort2Code = iePort2Code;
	}

	public String getIePort3Code() {
		return iePort3Code;
	}

	public void setIePort3Code(String iePort3Code) {
		this.iePort3Code = iePort3Code;
	}

	public String getIePort4Code() {
		return iePort4Code;
	}

	public void setIePort4Code(String iePort4Code) {
		this.iePort4Code = iePort4Code;
	}

	public String getIePort5Code() {
		return iePort5Code;
	}

	public void setIePort5Code(String iePort5Code) {
		this.iePort5Code = iePort5Code;
	}

	public String getIePort6Code() {
		return iePort6Code;
	}

	public void setIePort6Code(String iePort6Code) {
		this.iePort6Code = iePort6Code;
	}

	public String getIePort7Code() {
		return iePort7Code;
	}

	public void setIePort7Code(String iePort7Code) {
		this.iePort7Code = iePort7Code;
	}

	public String getIePort8Code() {
		return iePort8Code;
	}

	public void setIePort8Code(String iePort8Code) {
		this.iePort8Code = iePort8Code;
	}

	public String getIePort9Code() {
		return iePort9Code;
	}

	public void setIePort9Code(String iePort9Code) {
		this.iePort9Code = iePort9Code;
	}

	public String getIePort10Code() {
		return iePort10Code;
	}

	public void setIePort10Code(String iePort10Code) {
		this.iePort10Code = iePort10Code;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

//	public String getApproveDate() {
//		return approveDate;
//	}
//
//	public void setApproveDate(String approveDate) {
//		this.approveDate = approveDate;
//	}
//
//	public String getApprover() {
//		return approver;
//	}

}