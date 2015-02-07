package com.bestway.owp.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;

/**
 * 外发加工 - 申请表 - 申请表表头
 * @author wss2010.08.04
 */
public class OwpAppHead extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 流水号
	 */
	private Integer seqNum = null;
	
	/**
	 * 申请表编号
	 */
	private String appNo ;
	
	/**
	 * 电子口岸统一编号
	 */
	private String seqNo;
	
	/**
	 * 申报状态
	 * APPLY_POR = "1";	申请备案
	 * WAIT_EAA = "2";	等待审批
	 * PROCESS_EXE = "3";	正在执行
	 * CHANGING_EXE = "4";	正在变更
	 * CHANGING_CANCEL = "5";	已经核销
	 */
	private String declareState;  
	
	/**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark;
	
	/**
	 * 申请表类型
	 * 默认为 外发加工"G"
	 */
	private String appClass;
	
	/**
	 * 转出企业内部编号
	 */
	private String copAppNo;
	
	/**
	 * 委托方海关
	 */
	private Customs mastCust;
	
	/**
	 * 帐册类型 0电子化手册  1电子手册   2电子帐册
	 */
	private Integer emsType;
	
	/**
	 * 委托方手册/帐册编号
	 */
	private String emsNo;
	
	/**
	 * 委托方企业代码
	 */
	private String tradeCode;
	
	/**
	 * 委托方企业名称
	 */
	private String tradeName;
	
	/**
	 * 委托方企业9位组织机构代码
	 */
	private String agentCode;
	
	/**
	 * 委托方申报企业组织机构名称
	 */
	private String agentName;
	
	/**
	 * 委托企业联系人/联系电话
	 */
	private String corp;
	
	/**
	 * 委托申报人/联系电话
	 */
	private String decl;
	
	/**
	 * 委托加工合同号
	 */
	private String contrNo;
	
	/**
	 * 加工期限
	 */
	private Double conveyDay;
	
	/**
	 * 委托申报日期
	 */
	private Date appDate;
	
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 承揽方海关
	 */
	private Customs inMastCust;
	
	/**
	 * 承揽企业代码
	 */
	private String inTradeCode;
	
	/**
	 * 承揽企业名称
	 */
	private String inTradeName;
	
	/**
	 * 承揽方联系电话
	 */
	private String inCorp;
	
	/**
	 * 承揽方地区代码（目的地）
	 */
	private District inDistrict;
	
	/**
	 * 申报类型
	 * 1－备案申请 2－变更申请
	 */
	private String declareType;
	
	/**
	 * 录入人
	 * wss2010.10.27添加
	 */
	private String inputMan;
	
	/**
	 * 录入日期
	 * wss2010.10.27添加
	 */
	private Date inputDate;
	

	/**
	 * 委托方海关
	 * @return
	 */
	public Customs getMastCust() {
		return mastCust;
	}

	/**
	 * 委托方海关
	 * @param mastCust
	 */
	public void setMastCust(Customs mastCust) {
		this.mastCust = mastCust;
	}

	/**
	 * 加工期限
	 * @return
	 */
	public Double getConveyDay() {
		return conveyDay;
	}

	/**
	 * 加工期限
	 * @param conveyDay
	 */
	public void setConveyDay(Double conveyDay) {
		this.conveyDay = conveyDay;
	}

	/**
	 * 承揽方海关
	 * @return
	 */
	public Customs getInMastCust() {
		return inMastCust;
	}

	/**
	 * 承揽方海关
	 * @param inMastCust
	 */
	public void setInMastCust(Customs inMastCust) {
		this.inMastCust = inMastCust;
	}

	/**
	 * 流水号
	 * @return
	 */
	public Integer getSeqNum() {
		return seqNum;
	}

	/**
	 * 流水号
	 * @param seqNum
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * 申请表编号
	 * @return
	 */
	public String getAppNo() {
		return appNo;
	}

	/**
	 * 申请表编号
	 */
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	/**
	 * 电子口岸统一编号
	 * @return
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * 电子口岸统一编号
	 * @param seqNo
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * 申请表类型 默认为 外发加工
	 * @return
	 */
	public String getAppClass() {
		return appClass;
	}

	/**
	 * 申请表类型 默认为 G.外发加工
	 * @param appClass
	 */
	public void setAppClass(String appClass) {
		this.appClass = appClass;
	}

	/**
	 * 转出企业内部编号
	 * @return
	 */
	public String getCopAppNo() {
		return copAppNo;
	}

	/**
	 * 转出企业内部编号
	 * @param copAppNo
	 */
	public void setCopAppNo(String copAppNo) {
		this.copAppNo = copAppNo;
	}

	/**
	 * 帐册类型 "0"电子化手册 "1"电子手册 "2"电子帐册
	 * @return
	 */
	public Integer getEmsType() {
		return emsType;
	}

	/**
	 * 帐册类型 "0"电子化手册 "1"电子手册 "2"电子帐册
	 * @param emsType
	 */
	public void setEmsType(Integer emsType) {
		this.emsType = emsType;
	}

	/**
	 * 委托方手册/帐册编号
	 * @return
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * 委托方手册/帐册编号
	 * @param emsNo
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 委托方企业代码
	 * @return
	 */
	public String getTradeCode() {
		return tradeCode;
	}

	/**
	 * 委托方企业代码
	 * @param tradeCode
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	/**
	 * 委托方企业名称
	 * @return
	 */
	public String getTradeName() {
		return tradeName;
	}

	/**
	 * 委托方企业名称
	 * @param tradeName
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	/**
	 * 委托方企业9位组织机构代码
	 * @return
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * 委托方企业9位组织机构代码
	 * @param agentCode
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * 委托方申报企业组织机构名称
	 * @return
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * 委托方申报企业组织机构名称
	 * @param agentName
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * 委托企业联系人/联系电话
	 * @return
	 */
	public String getCorp() {
		return corp;
	}

	/**
	 * 委托企业联系人/联系电话
	 * @param corp
	 */
	public void setCorp(String corp) {
		this.corp = corp;
	}

	/**
	 * 委托申报人/联系电话
	 * @return
	 */
	public String getDecl() {
		return decl;
	}

	/**
	 * 委托申报人/联系电话
	 * @param decl
	 */
	public void setDecl(String decl) {
		this.decl = decl;
	}

	/**
	 * 委托加工合同号
	 * @return
	 */
	public String getContrNo() {
		return contrNo;
	}

	/**
	 * 委托加工合同号
	 * @param contrNo
	 */
	public void setContrNo(String contrNo) {
		this.contrNo = contrNo;
	}

	/**
	 * 备注
	 * @return
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * 委托申报日期
	 * @return
	 */
	public Date getAppDate() {
		return appDate;
	}

	/**
	 * 备注
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 委托申报日期
	 * @param dDate
	 */
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	/**
	 * 承揽企业代码
	 * @return
	 */
	public String getInTradeCode() {
		return inTradeCode;
	}

	/**
	 * 承揽企业代码
	 * @param inTradeCode
	 */
	public void setInTradeCode(String inTradeCode) {
		this.inTradeCode = inTradeCode;
	}

	/**
	 * 承揽企业名称
	 * @return
	 */
	public String getInTradeName() {
		return inTradeName;
	}

	/**
	 * 承揽企业名称
	 * @param inTradeName
	 */
	public void setInTradeName(String inTradeName) {
		this.inTradeName = inTradeName;
	}

	/**
	 * 承揽方联系电话
	 * @return
	 */
	public String getInCorp() {
		return inCorp;
	}

	/**
	 * 承揽方联系电话
	 * @param inCorp
	 */
	public void setInCorp(String inCorp) {
		this.inCorp = inCorp;
	}

	/**
	 * 承揽方地区代码（目的地）
	 * @return
	 */
	public District getInDistrict() {
		return inDistrict;
	}

	/**
	 * 承揽方地区代码（目的地）
	 * @param inDistCode
	 */
	public void setInDistrict(District inDistrict) {
		this.inDistrict = inDistrict;
	}

	/**
	 * 申报类型
	 * @return
	 */
	public String getDeclareType() {
		return declareType;
	}

	/**
	 * 申报类型
	 * @param declareType
	 */
	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}

	/**
	 * 审批状态 
	 * APPLY_POR = "1"; 申请备案 
	 * WAIT_EAA = "2"; 等待审批 
	 * PROCESS_EXE = "3"; 正在执行 
	 * CHANGING_EXE = "4"; 正在变更 
	 * CHANGING_CANCEL = "5"; 已经核销
	 * @return
	 */
	public String getDeclareState() {
		return declareState;
	}

	/**
	 * 审批状态 
	 * APPLY_POR = "1"; 申请备案 
	 * WAIT_EAA = "2"; 等待审批 
	 * PROCESS_EXE = "3"; 正在执行 
	 * CHANGING_EXE = "4"; 正在变更 
	 * CHANGING_CANCEL = "5"; 已经核销
	 * @param declareState
	 */
	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}

	/**
	 * 修改标志 
	 * UNCHANGE = "0"; 未修改 
	 * MODIFIED = "1"; 已修改 
	 * DELETED = "2"; 已删除 
	 * ADDED = "3"; 新增
	 * @return
	 */
	public String getModifyMark() {
		return modifyMark;
	}

	/**
	 * 修改标志 
	 * UNCHANGE = "0"; 未修改 
	 * MODIFIED = "1"; 已修改 
	 * DELETED = "2"; 已删除 
	 * ADDED = "3"; 新增
	 * @param modifyMark
	 */
	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	/**
	 * 录入日期
	 * @return
	 */
	public Date getInputDate() {
		return inputDate;
	}

	/**
	 * 录入日期
	 * @param inputDate
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 录入人
	 * @return
	 */
	public String getInputMan() {
		return inputMan;
	}

	/**
	 * 录入人
	 * @param inputMan
	 */
	public void setInputMan(String inputMan) {
		this.inputMan = inputMan;
	}
	
	
	
	
}
