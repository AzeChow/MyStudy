package com.bestway.bcus.system.entity;

import com.bestway.bcus.custombase.entity.basecode.ApplicationMode;
import com.bestway.bcus.custombase.entity.basecode.CoType;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.BaseCompany;

/**
 * 存放公司设置的基本资料
 * 
 * @author 001
 */
public class Company extends BaseCompany {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/* *****经营单位信息*********** */

	/**
	 * 经营单位海关编码
	 */
	private String buCode;

	/**
	 * 经营单位名称
	 */
	private String buName;

	/**
	 * 经营单位管理类别
	 */
	private String buLevel;

	/**
	 * 经营单位企业性质
	 */
	private CoType buOwnerType;

	/**
	 * 经营范围法人代表
	 */
	private String buOwner;

	/**
	 * 经营单位联系电话
	 */
	private String butel;

	/**
	 * 经营单位联系人
	 */
	private String buaddress;

	/* *****经营单位信息加工单位信息*********** */

	/**
	 * 加工单位海关编码
	 */
	private String code;

	/**
	 * 加工单位类别
	 */
	private String coLevel;

	/**
	 * 加工单位企业性质
	 */
	private CoType ownerType;

	/**
	 * 加工单位法人代表
	 */
	private String owner;

	/**
	 * 加工单位地址
	 */
	private String address;

	/**
	 * 投资总额
	 */
	private Double inverst;

	/**
	 * 加工单位联系电话
	 */
	private String tel;

	/**
	 * 加工单位联系人
	 */
	private String linkman;

	/**
	 * 英文名称
	 */
	private String englishName;

	/**
	 * 英文地址
	 */
	private String englishAddr;

	/**
	 * 工厂面积
	 */
	private Double area;

	/**
	 * 主管海关
	 */
	private Customs masterCustoms;

	/**
	 * 主管外经部门
	 */
	private RedDep masterFtc;

	// /**
	// * 申报类型
	// */
	// private ApplicationMode applicationMode;

	/**
	 * 上年加工贸易进口总值
	 */
	private Double amountIn;

	/**
	 * 上年加工贸易出口总值
	 */
	private Double amountOut;

	/**
	 * 年加工生产能力
	 */
	private Double amountProduct;

	/**
	 * 开户银行
	 */
	private String bank;

	/**
	 * 帐号
	 */
	private String account;

	/**
	 * 进口料件范围
	 */
	private String inArea;

	/**
	 * 出口料件范围
	 */
	private String outArea;

	/* ********经营单位信息企业内部计算机管理系统*********** */

	/**
	 * 系统名称
	 */
	private String sysName;

	/**
	 * 开发商
	 */
	private String sysOwner;

	/**
	 * 功能描述
	 */
	private String sysFunc;

	/* *****经营单位信息其它信息*********** */

	/**
	 * 企业承诺
	 */
	private String consent;

	/**
	 * 加工单位国标代码
	 */
	private String ownercounFlag;

	/**
	 * 经营单位国标代码
	 */
	private String counFlag;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 负责人
	 */
	private String principal;

	/**
	 * 外商公司
	 */
	private String outTradeCo;

	/**
	 * 外商公司电话
	 */
	private String outCoTel;

	/**
	 * 外商公司地址
	 */
	private String outAddress;
	
	/**
	 * 外商公司传真
	 */
	private String outFax;

	/**
	 * 报关员
	 */
	private String appCusMan;

	/**
	 * 报关员联系电话
	 */
	private String appCusManTel;

	/**
	 * 是否是当前公司
	 */
	private Boolean isCurrCompany;

	/**
	 * ftp地址
	 */
	private String bcsFtpAddress;

	/**
	 * ftp用户名
	 */
	private String bcsFtpUser;

	/**
	 * ftp密码
	 */
	private String bcsFtpPassword;

	/**
	 * 无纸通关EDI号码
	 */
	private String bcsEDICode;

	/**
	 * ftp地址
	 */
	private String dzscFtpAddress;

	/**
	 * ftp用户名
	 */
	private String dzscFtpUser;

	/**
	 * ftp密码
	 */
	private String dzscFtpPassword;

	/**
	 * 电子手册EDI号码
	 */
	private String dzscEDICode;

	/**
	 * ftp地址
	 */
	private String bcusftpAddress;

	/**
	 * ftp用户名
	 */
	private String bcusftpUser;

	/**
	 * ftp密码
	 */
	private String bcusftpPassword;

	/**
	 * 联网监管EDI号码
	 */
	private String bcusbcusEDICode;

	/**
	 * 外商公司联系人
	 */
	private String outLinkMan;

	/**
	 * 外商公司经理人
	 */
	private String outLinkManager;
	//////////////////////////////////////
	//  转厂用到
	//////////////////////////////////////
	/** 转出地 */
	private District outDistrict = null;
	/** 申报企业9位组织机构代码 非空 转出方 */
	private String inAgentCode = null;
	/** 申报企业组织机构名称 非空 */
	private String inAgentName = null;
	/** 转入企业法人/联系电话 */
	private String inCorp = null;
	/** 转入申报人/联系电话 */
	private String inDecl = null;
	/** 转入申报企业代码 可空 */
	private String inTradeCode2 = null;
	/** 转入申报企业名称 可空 */
	private String inTradeName2 = null;

	/**
	 * 组织机构代码
	 */
	private String copCode;
	/**
	 * 组织机构名称
	 */
	private String copName;
	
	/**
	 * 是否启用，true为启用 false为禁用
	 */
	private Boolean isEnable;
	
	/**
	 * 是否启用，true为启用 false为禁用
	 * @return
	 */
	public Boolean getIsEnable() {
		return isEnable;
	}

	/**
	 * 是否启用，true为启用 false为禁用
	 * @param isEnable
	 */
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	/**
	 * 获取外商公司地址
	 * 
	 * @return outAddress 外商公司地址
	 */
	public String getOutAddress() {
		return outAddress;
	}

	/**
	 * 设置外商公司地址
	 * 
	 * @param outAddress
	 *            外商公司地址
	 */
	public void setOutAddress(String outAddress) {
		this.outAddress = outAddress;
	}

	/**
	 * 获取外商公司电话
	 * 
	 * @return outCoTel 外商公司电话
	 */
	public String getOutCoTel() {
		return outCoTel;
	}

	/**
	 * 设置外商公司电话
	 * 
	 * @param outCoTel
	 *            外商公司电话
	 */
	public void setOutCoTel(String outCoTel) {
		this.outCoTel = outCoTel;
	}

	/**
	 * 获取外商公司联系人
	 * 
	 * @return outLinkMan 外商公司联系人
	 */
	public String getOutLinkMan() {
		return outLinkMan;
	}

	/**
	 * 设置外商公司联系人
	 * 
	 * @param outLinkMan
	 *            外商公司联系人
	 */
	public void setOutLinkMan(String outLinkMan) {
		this.outLinkMan = outLinkMan;
	}

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
	 * 获取帐号
	 * 
	 * @return account 帐号
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 设置帐号
	 * 
	 * @param account
	 *            帐号
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 获取加工单位地址
	 * 
	 * @return address 加工单位地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置
	 * 
	 * @param address
	 *            加工单位地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取上年加工贸易进口总值(美元)
	 * 
	 * @return amountIn 上年加工贸易进口总值(美元)
	 */
	public Double getAmountIn() {
		return amountIn;
	}

	/**
	 * 设置上年加工贸易进口总值(美元)
	 * 
	 * @param amountIn
	 *            上年加工贸易进口总值(美元)
	 */
	public void setAmountIn(Double amountIn) {
		this.amountIn = amountIn;
	}

	/**
	 * 获取上年加工贸易出口总值(美元)
	 * 
	 * @return amountOut 上年加工贸易出口总值(美元)
	 */
	public Double getAmountOut() {
		return amountOut;
	}

	/**
	 * 设置上年加工贸易出口总值(美元)
	 * 
	 * @param amountOut
	 *            上年加工贸易出口总值(美元)
	 */
	public void setAmountOut(Double amountOut) {
		this.amountOut = amountOut;
	}

	/**
	 * 获取年加工生产能力(美元)
	 * 
	 * @return amountProduct 年加工生产能力(美元)
	 */
	public Double getAmountProduct() {
		return amountProduct;
	}

	/**
	 * 设置年加工生产能力(美元)
	 * 
	 * @param amountProduct
	 *            年加工生产能力(美元)
	 */
	public void setAmountProduct(Double amountProduct) {
		this.amountProduct = amountProduct;
	}

	/**
	 * 获取工厂面积
	 * 
	 * @return area 工厂面积
	 */
	public Double getArea() {
		return area;
	}

	/**
	 * 设置工厂面积
	 * 
	 * @param area
	 *            工厂面积
	 */
	public void setArea(Double area) {
		this.area = area;
	}

	/**
	 * 获取开户银行
	 * 
	 * @return bank 开户银行
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * 设置开户银行
	 * 
	 * @param bank
	 *            开户银行
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * 获取加工单位海关编码
	 * 
	 * @return code 加工单位海关编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置加工单位海关编码
	 * 
	 * @param code
	 *            加工单位海关编码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取企业承诺
	 * 
	 * @return consent 企业承诺
	 */
	public String getConsent() {
		return consent;
	}

	/**
	 * 设置企业承诺
	 * 
	 * @param consent
	 *            企业承诺
	 */
	public void setConsent(String consent) {
		this.consent = consent;
	}

	/**
	 * 获取经营单位国标代码
	 * 
	 * @return counFlag 经营单位国标代码
	 */
	public String getCounFlag() {
		return counFlag;
	}

	/**
	 * 设置经营单位国标代码
	 * 
	 * @param counFlag
	 *            经营单位国标代码
	 */
	public void setCounFlag(String counFlag) {
		this.counFlag = counFlag;
	}

	/**
	 * 获取描述
	 * 
	 * @return description 描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 * 
	 * @param description
	 *            描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取进口料件范围
	 * 
	 * @return inArea 进口料件范围
	 */
	public String getInArea() {
		return inArea;
	}

	/**
	 * 设置进口料件范围
	 * 
	 * @param inArea
	 *            进口料件范围
	 */
	public void setInArea(String inArea) {
		this.inArea = inArea;
	}

	/**
	 * 获取投资总额
	 * 
	 * @return inverst 投资总额
	 */
	public Double getInverst() {
		return inverst;
	}

	/**
	 * 设置投资总额
	 * 
	 * @param inverst
	 *            投资总额
	 */
	public void setInverst(Double inverst) {
		this.inverst = inverst;
	}

	/**
	 * 获取加工单位类别
	 * 
	 * @return coLevel 加工单位类别
	 */
	public String getCoLevel() {
		return coLevel;
	}

	/**
	 * 设置加工单位类别
	 * 
	 * @param level
	 *            加工单位类别
	 */

	public void setCoLevel(String level) {
		this.coLevel = level;
	}

	/**
	 * 获取出口料件范围
	 * 
	 * @return outArea 出口料件范围
	 */
	public String getOutArea() {
		return outArea;
	}

	/**
	 * 设置出口料件范围
	 * 
	 * @param outArea
	 *            出口料件范围
	 */
	public void setOutArea(String outArea) {
		this.outArea = outArea;
	}

	/**
	 * 获取加工单位法人代表
	 * 
	 * @return owner 加工单位法人代表
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * 设置加工单位法人代表
	 * 
	 * @param owner
	 *            加工单位法人代表
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * 获取加工单位企业性质
	 * 
	 * @return ownerType 加工单位企业性质
	 */
	public CoType getOwnerType() {
		return ownerType;
	}

	/**
	 * 设置加工单位企业性质
	 * 
	 * @param ownerType
	 *            加工单位企业性质
	 */
	public void setOwnerType(CoType ownerType) {
		this.ownerType = ownerType;
	}

	/**
	 * 获取负责人
	 * 
	 * @return principal 负责人
	 */
	public String getPrincipal() {
		return principal;
	}

	/**
	 * 设置负责人
	 * 
	 * @param principal
	 *            负责人
	 */
	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	/**
	 * 获取功能描述
	 * 
	 * @return sysFunc 功能描述
	 */
	public String getSysFunc() {
		return sysFunc;
	}

	/**
	 * 设置功能描述
	 * 
	 * @param sysFunc
	 *            功能描述
	 */
	public void setSysFunc(String sysFunc) {
		this.sysFunc = sysFunc;
	}

	/**
	 * 获取系统名称
	 * 
	 * @return sysName 系统名称
	 */
	public String getSysName() {
		return sysName;
	}

	/**
	 * 设置系统名称
	 * 
	 * @param sysName
	 *            系统名称
	 */
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	/**
	 * 获取开发商
	 * 
	 * @return sysOwner 开发商
	 */
	public String getSysOwner() {
		return sysOwner;
	}

	/**
	 * 设置开发商
	 * 
	 * @param sysOwner
	 *            开发商
	 */
	public void setSysOwner(String sysOwner) {
		this.sysOwner = sysOwner;
	}

	/**
	 * 获取加工单位联系电话
	 * 
	 * @return tel 加工单位联系电话
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 设置加工单位联系电话
	 * 
	 * @param tel
	 *            加工单位联系电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 获取经营单位海关编码
	 * 
	 * @return buCode 经营单位海关编码
	 */
	public String getBuCode() {
		return buCode;
	}

	/**
	 * 设置经营单位海关编码
	 * 
	 * @param buCode
	 *            经营单位海关编码
	 */
	public void setBuCode(String buCode) {
		this.buCode = buCode;
	}

	/**
	 * 获取经营单位管理类别[A类、B类、C类、D类]
	 * 
	 * @return buLevel 经营单位管理类别[A类、B类、C类、D类]
	 */
	public String getBuLevel() {
		return buLevel;
	}

	/**
	 * 设置经营单位管理类别[A类、B类、C类、D类]
	 * 
	 * @param buLevel
	 *            经营单位管理类别[A类、B类、C类、D类]
	 */
	public void setBuLevel(String buLevel) {
		this.buLevel = buLevel;
	}

	/**
	 * 获取经营单位名称
	 * 
	 * @return buName 经营单位名称
	 */
	public String getBuName() {
		return buName;
	}

	/**
	 * 设置经营单位名称
	 * 
	 * @param buName
	 *            经营单位名称
	 */
	public void setBuName(String buName) {
		this.buName = buName;
	}

	/**
	 * 获取经营范围法人代表
	 * 
	 * @return buOwner 经营范围法人代表
	 */
	public String getBuOwner() {
		return buOwner;
	}

	/**
	 * 设置经营范围法人代表
	 * 
	 * @param buOwner
	 *            经营范围法人代表
	 */
	public void setBuOwner(String buOwner) {
		this.buOwner = buOwner;
	}

	/**
	 * 获取经营单位企业性质
	 * 
	 * @return buOwnerType 经营单位企业性质
	 */
	public CoType getBuOwnerType() {
		return buOwnerType;
	}

	/**
	 * 设置经营单位企业性质
	 * 
	 * @param buOwnerType
	 *            经营单位企业性质
	 */
	public void setBuOwnerType(CoType buOwnerType) {
		this.buOwnerType = buOwnerType;
	}

	/**
	 * 获取 主管海关
	 * 
	 * @return masterCustoms. 主管海关
	 */
	public Customs getMasterCustoms() {
		return masterCustoms;
	}

	/**
	 * 设置主管海关
	 * 
	 * @param masterCustoms
	 *            主管海关
	 */
	public void setMasterCustoms(Customs masterCustoms) {
		this.masterCustoms = masterCustoms;
	}

	//	
	// public ApplicationMode getApplicationMode() {
	// return applicationMode;
	// }
	//
	// public void setApplicationMode(ApplicationMode applicationMode) {
	// this.applicationMode = applicationMode;
	// }

	/**
	 * 获取
	 * 
	 * @return boolean
	 */
	public boolean isValid() {
		if (!super.isValid())
			return false;
		if (this.code == null)
			return false;
		return true;
	}

	/**
	 * 获取外商公司
	 * 
	 * @return outTradeCo. 外商公司
	 */
	public String getOutTradeCo() {
		return outTradeCo;
	}

	/**
	 * 设置外商公司
	 * 
	 * @param outTradeCo
	 *            外商公司
	 */
	public void setOutTradeCo(String outTradeCo) {
		this.outTradeCo = outTradeCo;
	}

	/**
	 * 获取加工单位国标代码
	 * 
	 * @return ownercounFlag 加工单位国标代码
	 */
	public String getOwnercounFlag() {
		return ownercounFlag;
	}

	/**
	 * 设置加工单位国标代码
	 * 
	 * @param ownercounFlag
	 *            加工单位国标代码
	 */
	public void setOwnercounFlag(String ownercounFlag) {
		this.ownercounFlag = ownercounFlag;
	}

	/**
	 * 获取报关员
	 * 
	 * @return appCusMan 报关员
	 */
	public String getAppCusMan() {
		return appCusMan;
	}

	/**
	 * 设置报关员
	 * 
	 * @param appCusMan
	 *            报关员
	 */
	public void setAppCusMan(String appCusMan) {
		this.appCusMan = appCusMan;
	}

	/**
	 * 获取报关员联系电话
	 * 
	 * @return appCusManTel 报关员联系电话
	 */
	public String getAppCusManTel() {
		return appCusManTel;
	}

	/**
	 * 设置报关员联系电话
	 * 
	 * @param appCusManTel
	 *            报关员联系电话
	 */
	public void setAppCusManTel(String appCusManTel) {
		this.appCusManTel = appCusManTel;
	}

	/**
	 * 获取加工单位联系人
	 * 
	 * @return linkman 加工单位联系人
	 */
	public String getLinkman() {
		return linkman;
	}

	/**
	 * 设置加工单位联系人
	 * 
	 * @param linkman
	 *            加工单位联系人
	 */
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	/**
	 * 获取英文地址
	 * 
	 * @return nglishAddr 英文地址
	 */
	public String getEnglishAddr() {
		return englishAddr;
	}

	/**
	 * 设置英文地址
	 * 
	 * @param englishAddr
	 *            英文地址
	 */
	public void setEnglishAddr(String englishAddr) {
		this.englishAddr = englishAddr;
	}

	/**
	 * 获取英文名称
	 * 
	 * @return englishName 英文名称
	 */
	public String getEnglishName() {
		return englishName;
	}

	/**
	 * 设置英文名称
	 * 
	 * @param englishName
	 *            英文名称
	 */
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	/**
	 * 获取经营单位联系电话
	 * 
	 * @return butel 经营单位联系电话
	 */
	public String getButel() {
		return butel;
	}

	/**
	 * 设置经营单位联系电话
	 * 
	 * @param butel
	 *            经营单位联系电话
	 */
	public void setButel(String butel) {
		this.butel = butel;
	}

	/**
	 * 获取经营单位联系人
	 * 
	 * @return buaddress 经营单位联系人
	 */
	public String getBuaddress() {
		return buaddress;
	}

	/**
	 * 设置经营单位联系人
	 * 
	 * @param buaddress
	 *            经营单位联系人
	 */
	public void setBuaddress(String buaddress) {
		this.buaddress = buaddress;
	}

	/**
	 * 设置无纸通关EDI号码
	 * 
	 * @return String 无纸通关EDI号码
	 */
	public String getBcsEDICode() {
		return bcsEDICode;
	}

	/**
	 * 设置无纸通关EDI号码
	 * 
	 * @param bcsEDICode
	 *            无纸通关EDI号码
	 */
	public void setBcsEDICode(String bcsEDICode) {
		this.bcsEDICode = bcsEDICode;
	}

	/**
	 * 获取ftp地址,BCS专用
	 * 
	 * @return bcsFtpAddress ftp地址,BCS专用
	 */
	public String getBcsFtpAddress() {
		return bcsFtpAddress;
	}

	/**
	 * 设置ftp地址,BCS专用
	 * 
	 * @param bcsFtpAddress
	 *            ftp地址,BCS专用
	 */
	public void setBcsFtpAddress(String bcsFtpAddress) {
		this.bcsFtpAddress = bcsFtpAddress;
	}

	/**
	 * 获取ftp密码,BCS专用
	 * 
	 * @return bcsFtpPassword ftp密码,BCS专用
	 */
	public String getBcsFtpPassword() {
		return bcsFtpPassword;
	}

	/**
	 * 设置ftp密码,BCS专用
	 * 
	 * @param bcsFtpPassword
	 *            ftp密码,BCS专用
	 */
	public void setBcsFtpPassword(String bcsFtpPassword) {
		this.bcsFtpPassword = bcsFtpPassword;
	}

	/**
	 * 获取ftp用户名,BCS专用
	 * 
	 * @return bcsFtpUser ftp用户名,BCS专用
	 */
	public String getBcsFtpUser() {
		return bcsFtpUser;
	}

	/**
	 * 设置ftp用户名,BCS专用
	 * 
	 * @param bcsFtpUser
	 *            ftp用户名,BCS专用
	 */
	public void setBcsFtpUser(String bcsFtpUser) {
		this.bcsFtpUser = bcsFtpUser;
	}

	/**
	 * 获取联网监管EDI号码
	 * 
	 * @return bcusbcusEDICode 联网监管EDI号码
	 */
	public String getBcusbcusEDICode() {
		return bcusbcusEDICode;
	}

	/**
	 * 设置联网监管EDI号码
	 * 
	 * @param bcusbcusEDICode
	 *            联网监管EDI号码
	 */
	public void setBcusbcusEDICode(String bcusbcusEDICode) {
		this.bcusbcusEDICode = bcusbcusEDICode;
	}

	/**
	 * 获取ftp地址,BCUS专用
	 * 
	 * @return bcusftpAddress ftp地址,BCUS专用
	 */
	public String getBcusftpAddress() {
		return bcusftpAddress;
	}

	/**
	 * 设置ftp地址,BCUS专用
	 * 
	 * @param bcusftpAddress
	 *            ftp地址,BCUS专用
	 */
	public void setBcusftpAddress(String bcusftpAddress) {
		this.bcusftpAddress = bcusftpAddress;
	}

	/**
	 * 获取ftp密码,BCUS专用
	 * 
	 * @return bcusftpPassword ftp密码,BCUS专用
	 */
	public String getBcusftpPassword() {
		return bcusftpPassword;
	}

	/**
	 * 设置ftp密码,BCUS专用
	 * 
	 * @param bcusftpPassword
	 *            ftp密码,BCUS专用
	 */
	public void setBcusftpPassword(String bcusftpPassword) {
		this.bcusftpPassword = bcusftpPassword;
	}

	/**
	 * 获取ftp用户名,BCUS专用
	 * 
	 * @return bcusftpUser ftp用户名,BCUS专用
	 */
	public String getBcusftpUser() {
		return bcusftpUser;
	}

	/**
	 * 设置ftp用户名,BCUS专用
	 * 
	 * @param bcusftpUser
	 *            ftp用户名,BCUS专用
	 */
	public void setBcusftpUser(String bcusftpUser) {
		this.bcusftpUser = bcusftpUser;
	}

	/**
	 * 获取电子手册EDI号码
	 * 
	 * @return dzscEDICode 电子手册EDI号码
	 */
	public String getDzscEDICode() {
		return dzscEDICode;
	}

	/**
	 * 设置电子手册EDI号码
	 * 
	 * @param dzscEDICode
	 *            电子手册EDI号码
	 */
	public void setDzscEDICode(String dzscEDICode) {
		this.dzscEDICode = dzscEDICode;
	}

	/**
	 * 获取ftp地址,DZSC专用
	 * 
	 * @return dzscFtpAddress ftp地址,DZSC专用
	 */
	public String getDzscFtpAddress() {
		return dzscFtpAddress;
	}

	/**
	 * 设置ftp地址,DZSC专用
	 * 
	 * @param dzscFtpAddress
	 *            ftp地址,DZSC专用
	 */
	public void setDzscFtpAddress(String dzscFtpAddress) {
		this.dzscFtpAddress = dzscFtpAddress;
	}

	/**
	 * 获取ftp密码,DZSC专用
	 * 
	 * @return dzscFtpPassword ftp密码,DZSC专用
	 */
	public String getDzscFtpPassword() {
		return dzscFtpPassword;
	}

	/**
	 * 设置ftp密码,DZSC专用
	 * 
	 * @param dzscFtpPassword
	 *            ftp密码,DZSC专用
	 */
	public void setDzscFtpPassword(String dzscFtpPassword) {
		this.dzscFtpPassword = dzscFtpPassword;
	}

	/**
	 * 获取ftp用户名,DZSC专用
	 * 
	 * @return dzscFtpUser ftp用户名,DZSC专用
	 */
	public String getDzscFtpUser() {
		return dzscFtpUser;
	}

	/**
	 * 设置ftp用户名,DZSC专用
	 * 
	 * @param dzscFtpUser
	 *            ftp用户名,DZSC专用
	 */
	public void setDzscFtpUser(String dzscFtpUser) {
		this.dzscFtpUser = dzscFtpUser;
	}

	/**
	 * 获取是否是当前公司
	 * 
	 * @return isCurrCompany 是否是当前公司
	 */
	public Boolean getIsCurrCompany() {
		return isCurrCompany;
	}

	/**
	 * 设置是否是当前公司
	 * 
	 * @param isCurrCompany
	 *            是否是当前公司
	 */
	public void setIsCurrCompany(Boolean isCurrCompany) {
		this.isCurrCompany = isCurrCompany;
	}

	/**
	 * 获取主管外经部门
	 * 
	 * @return masterFtc 主管外经部门
	 */
	public RedDep getMasterFtc() {
		return masterFtc;
	}

	/**
	 * 设置主管外经部门
	 * 
	 * @param masterFtc
	 *            主管外经部门
	 */
	public void setMasterFtc(RedDep masterFtc) {
		this.masterFtc = masterFtc;
	}

	/**
	 * @return the outDistrict
	 */
	public District getOutDistrict() {
		return outDistrict;
	}

	/**
	 * @param outDistrict the outDistrict to set
	 */
	public void setOutDistrict(District outDistrict) {
		this.outDistrict = outDistrict;
	}

	/**
	 * @return the inAgentCode
	 */
	public String getInAgentCode() {
		return inAgentCode;
	}

	/**
	 * @param inAgentCode the inAgentCode to set
	 */
	public void setInAgentCode(String inAgentCode) {
		this.inAgentCode = inAgentCode;
	}

	/**
	 * @return the inAgentName
	 */
	public String getInAgentName() {
		return inAgentName;
	}

	/**
	 * @param inAgentName the inAgentName to set
	 */
	public void setInAgentName(String inAgentName) {
		this.inAgentName = inAgentName;
	}

	/**
	 * @return the inCorp
	 */
	public String getInCorp() {
		if(inCorp == null || "".equals(inCorp)){
			return (buOwner==null?"":buOwner )+ 
			(butel==null?"":butel ) ;
		}
		return inCorp;
	}

	/**
	 * @param inCorp the inCorp to set
	 */
	public void setInCorp(String inCorp) {
		this.inCorp = inCorp;
	}

	/**
	 * @return the inDecl
	 */
	public String getInDecl() {
		return inDecl;
	}

	/**
	 * @param inDecl the inDecl to set
	 */
	public void setInDecl(String inDecl) {
		this.inDecl = inDecl;
	}

	/**
	 * @return the inTradeCode2
	 */
	public String getInTradeCode2() {
		return inTradeCode2;
	}

	/**
	 * @param inTradeCode2 the inTradeCode2 to set
	 */
	public void setInTradeCode2(String inTradeCode2) {
		this.inTradeCode2 = inTradeCode2;
	}

	/**
	 * @return the inTradeName2
	 */
	public String getInTradeName2() {
		return inTradeName2;
	}

	/**
	 * @param inTradeName2 the inTradeName2 to set
	 */
	public void setInTradeName2(String inTradeName2) {
		this.inTradeName2 = inTradeName2;
	}

	public String getOutFax() {
		return outFax;
	}

	public void setOutFax(String outFax) {
		this.outFax = outFax;
	}
}