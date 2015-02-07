/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.dzscmanage.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.basecode.ApplicationMode;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.BargainType;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscCustomsModifyState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.customs.common.entity.BaseContractHead;

/**
 * 存放电子手册分册表头资料
 * 
 * @author yp
 */
public class DzscEmsPorHeadFas extends BaseContractHead {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 流水号
	 */
	private Integer seqNum = null;

	/**
	 * 帐册分号
	 */
	private String fasEmsNo;

	/**
	 * 企业内部编号
	 */
	private String copEmsNo;

	/**
	 * 分册类型
	 */
	private String fasEmsType;

	/**
	 * 合同类型
	 */
	private BargainType bargainType = null;

	/**
	 * 申报关区号
	 */
	private Customs customNo = null;

	/**
	 * 合同性质
	 */
	private String bargainKind = null;

	/**
	 * 预录入号
	 */
	private String preRecordNo = null;

	/**
	 * 进出口岸
	 */
	private Customs iePortFas = null;

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
	 * 合同预录号前缀
	 */
	private String preContractCodePrefix = null;

	/**
	 * 合同预录号后缀
	 */
	private String preContractCodeSuffix = null;

	/**
	 * 是否第一次下载
	 */
	private Boolean isFirstDown = false;

	/**
	 * 分册期限
	 */
	private Date limitDate;

	/**
	 * 申报标志
	 */
	private ApplicationMode declareMark;

	/**
	 * 修改标志 UNCHANGE = "0"; 未修改 MODIFIED = "1"; 已修改 DELETED = "2"; 已删除 ADDED =
	 * "3"; 新增
	 */
	private String modifyMark;

	/**
	 * 录入员代号
	 */
	private String inputEr;

	/**
	 * 录入日期
	 */
	private Date inputDate;

	/**
	 * 申报日期
	 */
	private Date declareDate;

	/**
	 * 申报时间
	 */
	private Date declareTime;

	/**
	 * 申报类型
	 */
	private String declareType;

	/**
	 * 历史记录
	 */

	private Boolean historyState = false;

	/**
	 * 变更次数
	 */
	private Integer modifyTimes;

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

	/**
	 * 获取合同性质
	 * 
	 * @return bargainKind 合同性质
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
	 * 获取联系电话
	 * 
	 * @return contactTel联系电话
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
	 * 过去核销日期
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
	 * 获取进出口岸
	 * 
	 * @return iePort1 进出口岸
	 */
	public Customs getIePortFas() {
		return iePortFas;
	}

	public int getCustomsIePort1() {
		return Integer.parseInt(this.iePortFas.getCode());
	}

	/**
	 * 设置
	 * 
	 * @param iePort1
	 *            进出口岸
	 */
	public void setIePortFas(Customs iePort1) {
		this.iePortFas = iePort1;
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

	/**
	 * 获取合同预录号
	 * 
	 * preContractCodePrefix 合同预录号前缀 preContractCodeSuffix 合同预录号后缀
	 * 
	 * @return preContractCodePrefix＋preContractCodeSuffix 合同预录号
	 */
	public String getPreRecordNo() {
		if (preContractCodePrefix == null) {
			preContractCodePrefix = "";
		}
		if (preContractCodeSuffix == null) {
			preContractCodeSuffix = "";
		}
		return this.preContractCodePrefix + this.preContractCodeSuffix;
	}

	/**
	 * 设置合同预录号
	 * 
	 * preContractCodePrefix 合同预录号前缀 preContractCodeSuffix 合同预录号后缀
	 * 
	 * @param preRecordNo
	 *            等于preContractCodePrefix＋preContractCodeSuffix 合同预录号
	 */
	public void setPreRecordNo(String preRecordNo) {
		if (preRecordNo == null || "".equals(preRecordNo.trim())) {
			return;
		}
		this.setPreContractCodePrefix(preRecordNo.substring(0, 6));
		this.setPreContractCodeSuffix(preRecordNo.substring(6, 12));
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

	/**
	 * 获取合同预录号前缀
	 * 
	 * @return preContractCodeSuffix 合同预录号前缀
	 */
	public String getPreContractCodePrefix() {
		return preContractCodePrefix;
	}

	/**
	 * 设置合同预录号前缀
	 * 
	 * @param preContractCodePrefix
	 *            合同预录号前缀
	 */
	public void setPreContractCodePrefix(String preContractCodePrefix) {
		this.preContractCodePrefix = preContractCodePrefix;
	}

	/**
	 * 获取合同预录号后缀
	 * 
	 * @return preContractCodeSuffix 合同预录号后缀
	 */
	public String getPreContractCodeSuffix() {
		return preContractCodeSuffix;
	}

	/**
	 * 设置合同预录号后缀
	 * 
	 * @param preContractCodeSuffix
	 *            合同预录号后缀
	 */
	public void setPreContractCodeSuffix(String preContractCodeSuffix) {
		this.preContractCodeSuffix = preContractCodeSuffix;
	}

	/**
	 * 获取是否第一次下载，true不是第一次下载
	 * 
	 * @return isFirstDown 是否第一次下载，true不是第一次下载
	 */
	public Boolean getIsFirstDown() {
		return isFirstDown;
	}

	/**
	 * 设置是否第一次下载，true不是第一次下载
	 * 
	 * @param isFirstDown
	 *            是否第一次下载，true不是第一次下载
	 */
	public void setIsFirstDown(Boolean isFirstDown) {
		this.isFirstDown = isFirstDown;
	}

	/**
	 * 获取帐册分号
	 * 
	 * @return fasEmsNo 帐册分号
	 */
	public String getFasEmsNo() {
		return fasEmsNo;
	}

	/**
	 * 设置帐册分号
	 * 
	 * @param fasEmsNo
	 *            帐册分号
	 */
	public void setFasEmsNo(String fasEmsNo) {
		this.fasEmsNo = fasEmsNo;
	}

	/**
	 * 获取企业内部编号
	 * 
	 * @return copEmsNo 企业内部编号
	 */
	public String getCopEmsNo() {
		return copEmsNo;
	}

	/**
	 * 设置企业内部编号
	 * 
	 * @param copEmsNo
	 *            企业内部编号
	 */
	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}

	/**
	 * 获取分册期限
	 * 
	 * @return limitDate 分册期限
	 */
	public Date getLimitDate() {
		return limitDate;
	}

	/**
	 * 设置分册期限
	 * 
	 * @param limitDate
	 *            分册期限
	 */
	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}

	/**
	 * 获取申报标志
	 * 
	 * @return declareMark 申报标志
	 */
	public ApplicationMode getDeclareMark() {
		return declareMark;
	}

	/**
	 * 设置申报标志
	 * 
	 * @param declareMark
	 *            申报标志
	 */
	public void setDeclareMark(ApplicationMode declareMark) {
		this.declareMark = declareMark;
	}

	/**
	 * 获取修改标志
	 * 
	 * @return modifyMark 修改标志
	 */
	public String getModifyMark() {
		return modifyMark;
	}

	/**
	 * 设置修改标志
	 * 
	 * @param modifyMark
	 *            修改标志
	 */
	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	public String getCustomsModifyMark() {
		return DzscCustomsModifyState.getCustomsModifyState(modifyMark);
	}

	/**
	 * 获取分册类型
	 * 
	 * @return fasEmsType 分册类型
	 */
	public String getFasEmsType() {
		return fasEmsType;
	}

	/**
	 * 设置分册类型
	 * 
	 * @param fasEmsType
	 *            分册类型
	 */
	public void setFasEmsType(String fasEmsType) {
		this.fasEmsType = fasEmsType;
	}

	/**
	 * 获取申报日期
	 * 
	 * @return declareDate 申报日期
	 */
	public Date getDeclareDate() {
		return declareDate;
	}

	/**
	 * 设置申报日期
	 * 
	 * @param declareDate
	 *            申报日期
	 */
	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	/**
	 * 获取申报时间
	 * 
	 * @return declareTime 申报时间
	 */
	public Date getDeclareTime() {
		return declareTime;
	}

	/**
	 * 设置申报时间
	 * 
	 * @param declareTime
	 *            申报时间
	 */
	public void setDeclareTime(Date declareTime) {
		this.declareTime = declareTime;
	}

	/**
	 * 获取录入日期
	 * 
	 * @return inputDate 录入日期
	 */
	public Date getInputDate() {
		return inputDate;
	}

	/**
	 * 设置录入日期
	 * 
	 * @param inputDate
	 *            录入日期
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 获取录入员代号
	 * 
	 * @return inputEr 录入员代号
	 */
	public String getInputEr() {
		return inputEr;
	}

	/**
	 * 设置 录入员代号
	 * 
	 * @param inputEr
	 *            录入员代号
	 */
	public void setInputEr(String inputEr) {
		this.inputEr = inputEr;
	}

	/**
	 * 获取历史记录 0，1
	 * 
	 * @return historyState 历史记录 0，1
	 */
	public Boolean getHistoryState() {
		return historyState;
	}

	/**
	 * 设置历史记录 0，1
	 * 
	 * @param historyState
	 *            历史记录 0，1
	 */
	public void setHistoryState(Boolean historyState) {
		this.historyState = historyState;
	}

	/**
	 * 获取变更次数
	 * 
	 * @return modifyTimes 变更次数
	 */
	public Integer getModifyTimes() {
		return modifyTimes;
	}

	/**
	 * 设置变更次数
	 * 
	 * @param modifyTimes
	 *            变更次数
	 */
	public void setModifyTimes(Integer modifyTimes) {
		this.modifyTimes = modifyTimes;
	}

	public String getDeclareType() {
		return declareType;
	}

	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}
}