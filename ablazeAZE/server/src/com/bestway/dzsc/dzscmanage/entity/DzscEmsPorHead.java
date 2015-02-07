/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.dzscmanage.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.custombase.entity.parametercode.BargainType;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscCustomsModifyState;
import com.bestway.customs.common.entity.BaseContractHead;

/**
 * 存放电子手册通关备案里的表头资料
 * 
 * @author yp
 */
public class DzscEmsPorHead extends BaseContractHead {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 流水号
	 */
	private Integer seqNum = null;

	/**
	 * 申报关区号
	 */
	private Customs customNo = null;

	/**
	 * 主管外经部门
	 */
	private RedDep redDep = null;

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
	private Customs iePort1 = null;

	/**
	 * 延期期限
	 */
	private Date deferDate = null;

	/**
	 * 核销日期
	 */
	private Date destroyDate = null;

	/**
	 * 贸易方式
	 */
	private Trade trade = null;

	/**
	 * 贸易国别
	 */
	private Country tradeCountry = null;

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
	private Curr curr = null;

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
	private Transac transac = null;

	/**
	 * 进口口岸2
	 */
	private Customs iePort2 = null;

	/**
	 * 进口口岸3
	 */
	private Customs iePort3 = null;

	/**
	 * 进口口岸4
	 */
	private Customs iePort4 = null;

	/**
	 * 进口口岸5
	 */
	private Customs iePort5 = null;
	
	/**
	 * 进口口岸6
	 */
	private Customs iePort6 = null;
	/**
	 * 进口口岸7
	 */
	private Customs iePort7 = null;
	/**
	 * 进口口岸8
	 */
	private Customs iePort8 = null;
	/**
	 * 进口口岸9
	 */
	private Customs iePort9 = null;
	
	/**
	 * 进口口岸10
	 */
	private Customs iePort10 = null;
	/**
	 * 审批人
	 */
	private String approver = null;

	/**
	 * 审批日期
	 */
	private Date approveDate = null;

	/**
	 * 备注
	 */
	private String note = null;

	/**
	 * 是否第一次下载
	 */
	private Boolean isFirstDown = false;

	/**
	 * 修改标志 UNCHANGE = "0"; 未修改 MODIFIED = "1"; 已修改 DELETED = "2"; 已删除 ADDED =
	 * "3"; 新增
	 */
	private String modifyMark;



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
	 * 是否从ＱＰ导入的资料　
	 */
	private Boolean isImportFromQP=false;
	
	/**
	 * 获取serialVersionUID
	 * 
	 * @return serialVersionUID.
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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

	/**
	 * 获取审批日期
	 * 
	 * @return approveDate 审批日期
	 */
	public Date getApproveDate() {
		return approveDate;
	}

	/**
	 * 设置审批日期
	 * 
	 * @param approveDate
	 *            审批日期
	 */
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	/**
	 * 获取审批人
	 * 
	 * @return approver 审批人
	 */
	public String getApprover() {
		return approver;
	}

	/**
	 * 设置审批人
	 * 
	 * @param approver
	 *            审批人
	 */
	public void setApprover(String approver) {
		this.approver = approver;
	}

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
	 * 获取币制
	 * 
	 * @return curr 币制
	 */
	public Curr getCurr() {
		return curr;
	}

	/**
	 * 设置币制
	 * 
	 * @param curr
	 *            币制
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
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
	 * 获取延期期限
	 * 
	 * @return deferDate 延期期限
	 */
	public Date getDeferDate() {
		return deferDate;
	}

	/**
	 * 设置延期期限
	 * 
	 * @param deferDate
	 *            延期期限
	 */
	public void setDeferDate(Date deferDate) {
		this.deferDate = deferDate;
	}

	/**
	 * 获取核销日期
	 * 
	 * @return destroyDate 核销日期
	 */
	public Date getDestroyDate() {
		return destroyDate;
	}

	/**
	 * 设置核销日期
	 * 
	 * @param destroyDate
	 *            核销日期
	 */
	public void setDestroyDate(Date destroyDate) {
		this.destroyDate = destroyDate;
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
	 * 获取进出口岸1
	 * 
	 * @return iePort1 进出口岸1
	 */
	public Customs getIePort1() {
		return iePort1;
	}

	/**
	 * 设置进出口岸1
	 * 
	 * @param iePort1
	 *            进出口岸1
	 */
	public void setIePort1(Customs iePort1) {
		this.iePort1 = iePort1;
	}

	/**
	 * 获取进出口岸2
	 * 
	 * @return iePort2 进出口岸2
	 */
	public Customs getIePort2() {
		return iePort2;
	}

	/**
	 * 设置进出口岸
	 * 
	 * @param iePort2
	 *            进出口岸2
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

	public Customs getIePort6() {
		return iePort6;
	}

	public void setIePort6(Customs iePort6) {
		this.iePort6 = iePort6;
	}

	public Customs getIePort7() {
		return iePort7;
	}

	public void setIePort7(Customs iePort7) {
		this.iePort7 = iePort7;
	}

	public Customs getIePort8() {
		return iePort8;
	}

	public void setIePort8(Customs iePort8) {
		this.iePort8 = iePort8;
	}

	public Customs getIePort9() {
		return iePort9;
	}

	public void setIePort9(Customs iePort9) {
		this.iePort9 = iePort9;
	}

	public Customs getIePort10() {
		return iePort10;
	}

	public void setIePort10(Customs iePort10) {
		this.iePort10 = iePort10;
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

	/**
	 * 获取流水号
	 * 
	 * @return seqNum 流水号
	 */
	public Integer getSeqNum() {
		return seqNum;
	}

	/**
	 * 设置流水号
	 * 
	 * @param seqNum
	 *            流水号
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	// /**
	// * 获取合同预录号前缀
	// *
	// * @return preContractCodeSuffix 合同预录号前缀
	// */
	// public String getPreContractCodePrefix() {
	// return preContractCodePrefix;
	// }
	//
	// /**
	// * 设置合同预录号前缀
	// *
	// * @param preContractCodePrefix
	// * 合同预录号前缀
	// */
	// public void setPreContractCodePrefix(String preContractCodePrefix) {
	// this.preContractCodePrefix = preContractCodePrefix;
	// }
	//
	// /**
	// * 获取合同预录号后缀
	// *
	// * @return preContractCodeSuffix 合同预录号后缀
	// */
	// public String getPreContractCodeSuffix() {
	// return preContractCodeSuffix;
	// }
	//
	// /**
	// * 设置合同预录号后缀
	// *
	// * @param preContractCodeSuffix
	// * 合同预录号后缀
	// */
	// public void setPreContractCodeSuffix(String preContractCodeSuffix) {
	// this.preContractCodeSuffix = preContractCodeSuffix;
	// }

	/**
	 * 获取是否第一次下载，true是第一次下载
	 * 
	 * @return isFirstDown 是否第一次下载，true是第一次下载
	 */
	public Boolean getIsFirstDown() {
		return isFirstDown;
	}

	/**
	 * 设置是否第一次下载，true是第一次下载
	 * 
	 * @param isFirstDown
	 *            是否第一次下载，true是第一次下载
	 */
	public void setIsFirstDown(Boolean isFirstDown) {
		this.isFirstDown = isFirstDown;
	}

	/**
	 * 获取是否被选中，true表示被选中
	 * 
	 * @return isSelected 是否被选中，true表示被选中
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * 设置是否被选中，true表示被选中
	 * 
	 * @param isSelected
	 *            是否被选中，true表示被选中
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public String getCustomsModifyMark() {
		return DzscCustomsModifyState.getCustomsModifyState(modifyMark);
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
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

	public String getDeclareCode() {
		if ("0".equals(manageObject)) {
			return this.getTradeCode();
		} else {
			return this.getMachCode();
		}
	}

	public String getDeclareName() {
		if ("0".equals(manageObject)) {
			return this.getTradeName();
		} else {
			return this.getMachName();
		}
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

	public RedDep getRedDep() {
		return redDep;
	}

	public void setRedDep(RedDep redDep) {
		this.redDep = redDep;
	}

	public String getInputER() {
		return inputER;
	}

	public void setInputER(String inputER) {
		this.inputER = inputER;
	}

	public String getAllIEPort() {
		String allIEPort = "";
		if (this.iePort1 != null) {
			allIEPort = this.iePort1.getCode();
		}
		if (this.iePort2 != null) {
			if (!"".equals(allIEPort.trim())) {
				allIEPort = allIEPort + "/" + this.iePort2.getCode();
			} else {
				allIEPort = this.iePort2.getCode();
			}
		}
		if (this.iePort3 != null) {
			if (!"".equals(allIEPort.trim())) {
				allIEPort = allIEPort + "/" + this.iePort3.getCode();
			} else {
				allIEPort = this.iePort3.getCode();
			}
		}
		if (this.iePort4 != null) {
			if (!"".equals(allIEPort.trim())) {
				allIEPort = allIEPort + "/" + this.iePort4.getCode();
			} else {
				allIEPort = this.iePort4.getCode();
			}
		}
		if (this.iePort5 != null) {
			if (!"".equals(allIEPort.trim())) {
				allIEPort = allIEPort + "/" + this.iePort5.getCode();
			} else {
				allIEPort = this.iePort5.getCode();
			}
		}
		if (this.iePort6 != null) {
			if (!"".equals(allIEPort.trim())) {
				allIEPort = allIEPort + "/" + this.iePort6.getCode();
			} else {
				allIEPort = this.iePort6.getCode();
			}
		}
		if (this.iePort7 != null) {
			if (!"".equals(allIEPort.trim())) {
				allIEPort = allIEPort + "/" + this.iePort7.getCode();
			} else {
				allIEPort = this.iePort7.getCode();
			}
		}
		if (this.iePort8 != null) {
			if (!"".equals(allIEPort.trim())) {
				allIEPort = allIEPort + "/" + this.iePort8.getCode();
			} else {
				allIEPort = this.iePort8.getCode();
			}
		}
		if (this.iePort9 != null) {
			if (!"".equals(allIEPort.trim())) {
				allIEPort = allIEPort + "/" + this.iePort9.getCode();
			} else {
				allIEPort = this.iePort9.getCode();
			}
		}
		if (this.iePort10 != null) {
			if (!"".equals(allIEPort.trim())) {
				allIEPort = allIEPort + "/" + this.iePort10.getCode();
			} else {
				allIEPort = this.iePort10.getCode();
			}
		}
		return allIEPort;
	}

	public Boolean getIsImportFromQP() {
		if(isImportFromQP==null){
			return false;
		}
		return isImportFromQP;
	}

	public void setIsImportFromQP(Boolean isImportFromQP) {
		this.isImportFromQP = isImportFromQP;
	}
}