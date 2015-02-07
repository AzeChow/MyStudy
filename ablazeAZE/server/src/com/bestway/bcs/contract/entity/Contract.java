/*
 * Created on 2005-3-21
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contract.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseContractHead;

/**
 * 存放合同备案表头资料
 */
public class Contract extends BaseContractHead {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 报送海关
	 */
	private Customs declareCustoms = null;

	/**
	 * 主管外经部门
	 */
	private RedDep redDep = null;

	/**
	 * 是否已经核销
	 */
	private Boolean isCancel = false;

	/**
	 * 手册类型
	 * FOREIGN_CAPITAL_SELL_PRODUCT = "A";	外资内销产品
	 * COME_MATERIEL_PROCESS = "B";	来料加工
	 * IMPORT_MATERIEL_PROCESS = "C";	进料加工
	 * FOREIGN_CAPITAL_MACHINE = "D";	外资设备
	 * BONDEN_WAREHOUSE = "P";	保税仓
	 * COME_MATERIEL_EMS = "U";	纸制手册电子化来料手册
	 * IMPORT_MATERIEL_EMS = "R";	纸制手册电子化进料手册
	 */
	private String emsType = null;

	/**
	 * 贸易方式
	 */
	private Trade trade = null;

	// /**
	// * 有效期限
	// */
	// private Date availabilityDate = null;

	/**
	 * 贸易国别
	 */
	private Country tradeCountry = null;

	/**
	 * 延期期限
	 */
	private Date deferDate = null;

	/**
	 * 企业地址
	 */
	private String enterpriseAddress = null;

	/**
	 * 核销日期
	 */
	private Date destroyDate = null;

	/**
	 * 联系人
	 */
	private String linkMan = null;
	
	/**
	 * 外商公司经理人
	 */
	private String outLinkManager = null;

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
	private String impContractNo = null;

	/**
	 * 出口合同号
	 */
	private String expContractNo = null;

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
	 * 监管费用
	 */
	private Double wardshipFee = null;

	/**
	 * 监管费率
	 */
	private Double wardshipRate = null;

	/**
	 * 成交方式
	 */
	private Transac transac = null;

	/**
	 * 进口口岸
	 */
	private Customs iePort1 = null;

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
	 * 许可证号
	 */
	private String permitNo = null;

	/**
	 * 企业内部编号
	 */
	private String copEmsNo = null;

	/**
	 * 备案资料库编号
	 */
	private String corrEmsNo = null;

	/**
	 * 录入员代号
	 */
	private String inputER = null;

	/**
	 * 备注
	 */
	private String memo = null;

    /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark = null;

	/**
	 * 是否被选中
	 */
	private Boolean isSelected = false;

	/**
	 * 是否是纸质手册电子化
	 * true:纸质手册电子化
	 * false:普通纸质手册
	 */
	private Boolean isContractEms = false;
	/**
	 * 是否从ＱＰ导入的资料　
	 */
	private Boolean isImportFromQP=false;
	
	/**
	 * 计算比例
	 */
	private Double countScale = null;
	
	/**
	 * 设置计算比例
	 * @return
	 */
	public Double getCountScale() {
		return countScale;
	}
	/**
	 * 获取计算比例
	 * @param countScale
	 */
	public void setCountScale(Double countScale) {
		this.countScale = countScale;
	}

	/**
	 * 获取是否被选中，true为选中
	 * 
	 * @return isSelected 是否被选中，true为选中
	 */
	public Boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * 设置是否被选中，true为选中
	 * 
	 * @param isSelected
	 *            是否被选中，true为选中
	 */
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * 获取是否被选中，true为选中
	 * 
	 * @return isSelected 是否被选中，true为选中
	 */
	public Boolean isSelected() {
		return isSelected;
	}

	/**
	 * 设置是否被选中，true为选中
	 * 
	 * @param isSelected
	 *            是否被选中，true为选中
	 */
	public void setSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * 获取进口口岸1
	 * 
	 * @return iePort1 进口口岸1
	 */
	public Customs getIePort1() {
		return iePort1;
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
	 * 获取进口口岸3
	 * 
	 * @return iePort3 进口口岸3
	 */
	public Customs getIePort3() {
		return iePort3;
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
	 * 获取进口口岸5
	 * 
	 * @return iePort5 进口口岸5
	 */
	public Customs getIePort5() {
		return iePort5;
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
	 * 设置进口口岸2
	 * 
	 * @param iePort2
	 *            进口口岸2
	 */
	public void setIePort2(Customs iePort2) {
		this.iePort2 = iePort2;
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
	 * 设置进口口岸4
	 * 
	 * @param iePort4
	 *            进口口岸4
	 */
	public void setIePort4(Customs iePort4) {
		this.iePort4 = iePort4;
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
	 * 获取备注
	 * 
	 * @return memo 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 获取许可证号
	 * 
	 * @return permitNo 许可证号
	 */
	public String getPermitNo() {
		return permitNo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 设置许可证号
	 * 
	 * @param permitNo
	 *            许可证号
	 */
	public void setPermitNo(String permitNo) {
		this.permitNo = permitNo;
	}

	/**
	 * 获取serialVersionUID
	 * 
	 * @return Returns the serialVersionUID.
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

	// /**
	// * 获取有效期限
	// *
	// * @return availabilityDate 有效期限
	// */
	// public Date getAvailabilityDate() {
	// return availabilityDate;
	// }
	//
	// /**
	// * 设置有效期限
	// *
	// * @param availabilityDate
	// * 有效期限
	// */
	// public void setAvailabilityDate(Date availabilityDate) {
	// this.availabilityDate = availabilityDate;
	// }

	/**
	 * 获取外商公司经理人
	 * @return
	 */
	public String getOutLinkManager() {
		return outLinkManager;
	}
	
	/**
	 * 设置外商公司经理人
	 * @param outLinkManager
	 */
	public void setOutLinkManager(String outLinkManager) {
		this.outLinkManager = outLinkManager;
	}
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
	 * 获取报送海关
	 * 
	 * @return declareCustoms 报送海关
	 */
	public Customs getDeclareCustoms() {
		return declareCustoms;
	}

	/**
	 * 设置报送海关
	 * 
	 * @param declareCustoms
	 *            报送海关
	 */
	public void setDeclareCustoms(Customs declareCustoms) {
		this.declareCustoms = declareCustoms;
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
	 * 获取出口合同号
	 * 
	 * @return expContractNo 出口合同号
	 */
	public String getExpContractNo() {
		return expContractNo;
	}

	/**
	 * 设置出口合同号
	 * 
	 * @param expContractNo
	 *            出口合同号
	 */
	public void setExpContractNo(String expContractNo) {
		this.expContractNo = expContractNo;
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
	 * 获取进口合同号
	 * 
	 * @return impContractNo 进口合同号
	 */
	public String getImpContractNo() {
		return impContractNo;
	}

	/**
	 * 设置进口合同号
	 * 
	 * @param impContractNo
	 *            进口合同号
	 */
	public void setImpContractNo(String impContractNo) {
		this.impContractNo = impContractNo;
	}

	/**
	 * 获取是否已经核销，true为已核销
	 * 
	 * @return isCancel 是否已经核销，true为已核销
	 */
	public Boolean getIsCancel() {
		return isCancel;
	}

	/**
	 * 设置是否已经核销，true为已核销
	 * 
	 * @param isCancel
	 *            是否已经核销，true为已核销
	 */
	public void setIsCancel(Boolean isCancel) {
		this.isCancel = isCancel;
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

	public String getCopEmsNo() {
		return copEmsNo;
	}

	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}

	public RedDep getRedDep() {
		return redDep;
	}

	public void setRedDep(RedDep redDep) {
		this.redDep = redDep;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	/**
	 * 获得
	 * 备案资料库编号
	 */
	public String getCorrEmsNo() {
		return corrEmsNo;
	}
	/**
	 * 设置
	 * 备案资料库编号
	 */
	public void setCorrEmsNo(String corrEmsNo) {
		this.corrEmsNo = corrEmsNo;
	}

	public String getEmsType() {
		return emsType;
	}

	public void setEmsType(String emsType) {
		this.emsType = emsType;
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

	public Boolean getIsContractEms() {
		return isContractEms;
	}

	public void setIsContractEms(Boolean isContractEms) {
		this.isContractEms = isContractEms;
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
		if (!"".equals(allIEPort.trim()) && this.iePort2 != null) {
			allIEPort = allIEPort + "/" + this.iePort2.getCode();
		}
		if (!"".equals(allIEPort.trim()) && this.iePort3 != null) {
			allIEPort = allIEPort + "/" + this.iePort3.getCode();
		}
		if (!"".equals(allIEPort.trim()) && this.iePort4 != null) {
			allIEPort = allIEPort + "/" + this.iePort4.getCode();
		}
		if (!"".equals(allIEPort.trim()) && this.iePort5 != null) {
			allIEPort = allIEPort + "/" + this.iePort5.getCode();
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