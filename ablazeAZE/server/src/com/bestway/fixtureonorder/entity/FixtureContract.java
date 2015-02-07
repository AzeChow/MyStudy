/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.fixtureonorder.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseContractHead;

/**
 * 存放设备合同备案表头资料
 * @author sanvi
 *
 */
public class FixtureContract extends BaseContractHead {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 报送海关
	 */
	private Customs declareCustoms = null;

	/**
	 * 贸易方式
	 */
	private Trade trade = null;

	/**
	 * 有效期限
	 */
	private Date availabilityDate = null;

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
	private String impContractNo = null;

	/**
	 * 出口合同号
	 */
	private String expContractNo = null;

	/**
	 * 设备总金额
	 */
	private Double fixtureAmount = null;

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
	private Date approveDate = null;

	/**
	 * 备注
	 */
	private String memo = null;

	/**
	 * 是否被选中
	 */
	private Boolean isSelected = false;
	
	/**
	 * 类型
	 * 
	 */
	private String type="来料设备";

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
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
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
	public String getRetrialer() {
		return retrialer;
	}

	/**
	 * 设置审批人
	 * 
	 * @param approver
	 *            审批人
	 */
	public void setRetrialer(String approver) {
		this.retrialer = approver;
	}

	/**
	 * 获取有效期限
	 * 
	 * @return availabilityDate 有效期限
	 */
	public Date getAvailabilityDate() {
		return availabilityDate;
	}

	/**
	 * 设置有效期限
	 * 
	 * @param availabilityDate
	 *            有效期限
	 */
	public void setAvailabilityDate(Date availabilityDate) {
		this.availabilityDate = availabilityDate;
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
	public Double getFixtureAmount() {
		return fixtureAmount;
	}

	/**
	 * 设置进口总金额
	 * 
	 * @param imgAmount
	 *            进口总金额
	 */
	public void setFixtureAmount(Double imgAmount) {
		this.fixtureAmount = imgAmount;
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

	public String getType() {
		return type;
	}

	public String getFirstTrialer() {
		return firstTrialer;
	}

	public void setFirstTrialer(String firstTrialer) {
		this.firstTrialer = firstTrialer;
	}

}