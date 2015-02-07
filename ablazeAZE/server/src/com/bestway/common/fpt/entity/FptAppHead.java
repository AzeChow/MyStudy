package com.bestway.common.fpt.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 关封申请单
 */
public class FptAppHead extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/** 转入转出标记 0：转出，1：转入 */
	private String inOutFlag = null;

	/** 申请表编号 */
	private String appNo = null;

	/** 电子口岸统一编号 X(18) 转出方不填，转入方必填 */
	private String seqNo = null;

	// ////////////////////////////////////////////////////////////
	// 转出填写
	// 报文
	// 1 <APP_NO> 申请表编号 X(12) 不填
	// 2 <SEQ_NO> 电子口岸统一编号 X(18) 不填
	// 3 <APP_CLASS> 申请表类型 X 非空 申请表类型代码
	// 4 <COP_APP_NO> 转出企业内部编号 X(20) 非空 转出方，不可重复
	// 5 <MAST_CUST> 转出地海关 X(4) 非空
	// 6 <EMS_NO> 转出手册/账册编号 X(12) 非空 转出方
	// 7 <TRADE_CODE> 转出企业代码 X(10) 非空 转出方。为手册经营加工单位之一
	// 8 <TRADE_NAME> 转出企业名称 X(30) 非空
	// 9 < AGENT_CODE> 申报企业9位组织机构代码 X(10) 非空 转出方
	// 10 < AGENT_NAME> 申报企业组织机构名称 X(30) 非空
	// 11 <CORP> 转出企业法人/联系电话 X(32) 转出方
	// 12 <DECL> 转出申报人/联系电话 X(32) 转出方
	// 13 <DIST_CODE> 转出地 X(5) 非空 标准参数代码
	// 14 <CONTR_NO> 企业合同号 X(32) 转出方
	// 15 <LICE_NO> 转出企业批准证编号 X(20) 非空 申请表商务部门审批编号，无审批时填写“人工审批”
	// 16 <D_DATE> 转出申报日期 Z(8) 非空 必须取当天日期；自动生成(yyyymmdd)
	// 17 <NOTE> 转出企业备注 X(50) 转出方
	// 18 <IN_TRADE_CODE> 转入企业代码 X(10) 非空 转出方填写。
	// 19 <IN_TRADE_NAME> 转入企业名称 X(30) 非空 转出方填写
	// 20 <IN_DIST_CODE> 目的地 X(5) 非空 地区代码表
	// 21 <CONVEY_DAY> 预计运输耗时（天） Z(12)9.9(5)
	// 22 <CONVEY_SPA> 送货距离（公里） Z(12)9.9(5)
	// ////////////////////////////////////////////////////////////

	/** 申请表类型 */
	private String appClass = null;

	/** 企业合同号 */
	private String contrNo = null;

	/** 转出企业代码 非空 转出方。为手册经营加工单位之一 */
	private String outTradeCode = null;

	/** 转出企业名称 非空 */
	private String outTradeName = null;

	/** 转出地 */
	private District outDistrict = null;

	/** 转出地海关 非空 */
	private Customs outCustoms = null;
	
	/** 转出手册/账册编号 非空 转出方 */
	private String emsNo = null;

	/** 转出企业内部编号 非空 转出方，不可重复 */
	private String outCopAppNo = null;

	/** 转出企业批准证编号 非空 申请表商务部门审批编号，无审批时填写“人工审批” */
	private String outLiceNo = null;

	/** 转出申报企业代码 可空 转出方 */
	private String outTradeCode2 = null;

	/** 转出申报企业名称 可空 */
	private String outTradeName2 = null;

	/** 不允许录入及修改，当企业申报数据时，由系统自动填写。必须取当天日期；自动生成(yyyymmdd) */
	private Date outDate = null;

	/** 申报企业9位组织机构代码 非空 转出方 */
	private String outAgentCode = null;

	/** 申报企业组织机构名称 非空 */
	private String outAgentName = null;

	/** 预计运输耗时（天） */
	private Integer conveyDay = null;

	/** 送货距离（公里） */
	private Integer conveySpa = null;

	/** 转出企业法人/联系电话 转出方 */
	private String outCorp = null;

	/** 转出申报人/联系电话 转出方 */
	private String outDecl = null;

	/** 转出备注 */
	private String outNote = null;

	// ////////////////////////////////////////////////////////////
	// 转入填写
	// 报文
	// 1 <SEQ_NO> 电子口岸统一编号 X(18) 非空
	// 2 <TRADE_CODE> 企业代码 X(10) 非空
	// 3 <COP_APP_NO> 转入企业内部编号 X(20) 非空
	// 4 <MAST_CUST> 转入地海关 X(4) 非空
	// 5 <AGENT_CODE> 转入申报企业9位组织机构代码 X(10) 非空
	// 6 < AGENT_NAME> 转入申报企业组织机构名称 X(30) 非空
	// 7 <CORP> 转入企业法人/联系电话 X(32)
	// 8 <DECL> 转入申报人/联系电话 X(320)
	// 9 <LICE_NO> 转入企业批准证编号 X(20) 非空 申请表商务部门审批编号，无审批时填写“人工审批”
	// 10 <D_DATE> 转入申报日期 Z(8) 非空 申报当天日期（YYYYMMDD）
	// 11 <NOTE> 转入企业备注 X(50) 转入方
	// ////////////////////////////////////////////////////////////
	/** 转入企业内部编号 非空 */
	private String inCopAppNo = null;

	/** 转入地海关 非空 */
	private Customs inCustoms = null;
	
	/** 转入企业代码 */
	private String inTradeCode = null;
	
	/** 转入企业 */
	private String inTradeName = null;
	
	/** 转入地 */
	private District inDistrict = null;

	/** 申报企业9位组织机构代码 非空 转入方 */
	private String inAgentCode = null;

	/** 申报企业组织机构名称 非空 */
	private String inAgentName = null;

	/** 转入企业法人/联系电话 */
	private String inCorp = null;

	/** 转入申报人/联系电话 */
	private String inDecl = null;

	/** 转入备注 */
	private String inNote = null;

	/** 转入企业批准证编号 可空 申请表商务部门审批编号，无审批时填写“人工审批” */
	private String inLiceNo = null;

	/** 不允许录入及修改，当企业申报数据时，由系统自动填写。必须取当天日期；自动生成(yyyymmdd) */
	private Date inDate = null;

	/** 转入申报企业代码 可空 */
	private String inTradeCode2 = null;

	/** 转入申报企业名称 可空 */
	private String inTradeName2 = null;
	
	/** 转入手册/账册编号 非空 转入方 */
	private String inEmsNo = null;
	/** 手册有效期 */
	private Date endDate = null;
	// /////////////////////////////////////////////
	//
	// 1 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单
	// 2 修改标记 非空 0：未修改，1：已修改，2：已删除，3：新增
	// 3 录入日期 可空 当前日期
	// 4 录入员 可空 当前登陆人员
	// 5 项目类型 0：电子帐册，1：电子化手册，3：电子手册

	// /////////////////////////////////////////////
	/** 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 
	 * 2013(HH 1：原始状态，2：等待审批，3：正在执行，4：变更，5：退单 )
	 */
	private String declareState = null;

	/** 修改标记 非空 0：未修改，1：已修改，2：已删除，3：新增 */
	private String modifyMarkState = null;

	/** 录入员 可空 当前登陆人员 */
	private AclUser aclUser = null;

	/** 项目类型 0：电子帐册，1：电子化手册，3：电子手册 */
	private Integer projectType = null;

	/** 申报类型 1－备案申请 2－变更申请 */
	private String delcareType = null;
	/**客户*/
	private ScmCoc scmCoc = null;
	/**对方手册生效日期*/
	private Date oppositeEmsDate = null;
	/**
	 * 获取是否已核销关封，true为已核销
	 */
	private Boolean isCollated =false;
	
	/**
	 * 作废时需要填写的信息
	 */
	private String marke;

	public String getMarke() {
		return marke;
	}

	public void setMarke(String marke) {
		this.marke = marke;
	}

	/**
	 * 新增购销合同号
	 */
	private String saleContractNo=null;
	
	
	
	public String getSaleContractNo() {
		return saleContractNo;
	}

	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}

	/**
	 * @return the inOutFlag
	 */
	public String getInOutFlag() {
		return inOutFlag;
	}

	/**
	 * @param inOutFlag
	 *            the inOutFlag to set
	 */
	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	/**
	 * @return the appNo
	 */
	public String getAppNo() {
		return appNo;
	}

	/**
	 * @param appNo
	 *            the appNo to set
	 */
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	/**
	 * @return the seqNo
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo
	 *            the seqNo to set
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @return the appClass
	 */
	public String getAppClass() {
		return appClass;
	}

	/**
	 * @param appClass
	 *            the appClass to set
	 */
	public void setAppClass(String appClass) {
		this.appClass = appClass;
	}

	/**
	 * @return the contrNo
	 */
	public String getContrNo() {
		return contrNo;
	}

	/**
	 * @param contrNo
	 *            the contrNo to set
	 */
	public void setContrNo(String contrNo) {
		this.contrNo = contrNo;
	}

	/**
	 * @return the outTradeCode
	 */
	public String getOutTradeCode() {
		return outTradeCode;
	}

	/**
	 * @param outTradeCode
	 *            the outTradeCode to set
	 */
	public void setOutTradeCode(String outTradeCode) {
		this.outTradeCode = outTradeCode;
	}

	/**
	 * @return the outTradeName
	 */
	public String getOutTradeName() {
		return outTradeName;
	}

	/**
	 * @param outTradeName
	 *            the outTradeName to set
	 */
	public void setOutTradeName(String outTradeName) {
		this.outTradeName = outTradeName;
	}

	/**
	 * @return the outDistrict
	 */
	public District getOutDistrict() {
		return outDistrict;
	}

	/**
	 * @param outDistrict
	 *            the outDistrict to set
	 */
	public void setOutDistrict(District outDistrict) {
		this.outDistrict = outDistrict;
	}

	/**
	 * @return the inTradeCode
	 */
	public String getInTradeCode() {
		return inTradeCode;
	}

	/**
	 * @param inTradeCode
	 *            the inTradeCode to set
	 */
	public void setInTradeCode(String inTradeCode) {
		this.inTradeCode = inTradeCode;
	}

	/**
	 * @return the inTradeName
	 */
	public String getInTradeName() {
		return inTradeName;
	}

	/**
	 * @param inTradeName
	 *            the inTradeName to set
	 */
	public void setInTradeName(String inTradeName) {
		this.inTradeName = inTradeName;
	}

	/**
	 * @return the inDistrict
	 */
	public District getInDistrict() {
		return inDistrict;
	}

	/**
	 * @param inDistrict
	 *            the inDistrict to set
	 */
	public void setInDistrict(District inDistrict) {
		this.inDistrict = inDistrict;
	}

	/**
	 * @return the outCustoms
	 */
	public Customs getOutCustoms() {
		return outCustoms;
	}

	/**
	 * @param outCustoms
	 *            the outCustoms to set
	 */
	public void setOutCustoms(Customs outCustoms) {
		this.outCustoms = outCustoms;
	}

	/**
	 * @return the emsNo
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * @param emsNo
	 *            the emsNo to set
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * @return the outCopAppNo
	 */
	public String getOutCopAppNo() {
		return outCopAppNo;
	}

	/**
	 * @param outCopAppNo
	 *            the outCopAppNo to set
	 */
	public void setOutCopAppNo(String outCopAppNo) {
		this.outCopAppNo = outCopAppNo;
	}

	/**
	 * @return the outLiceNo
	 */
	public String getOutLiceNo() {
		return outLiceNo;
	}

	/**
	 * @param outLiceNo
	 *            the outLiceNo to set
	 */
	public void setOutLiceNo(String outLiceNo) {
		this.outLiceNo = outLiceNo;
	}

	/**
	 * @return the outTradeCode2
	 */
	public String getOutTradeCode2() {
		return outTradeCode2;
	}

	/**
	 * @param outTradeCode2
	 *            the outTradeCode2 to set
	 */
	public void setOutTradeCode2(String outTradeCode2) {
		this.outTradeCode2 = outTradeCode2;
	}

	/**
	 * @return the outTradeName2
	 */
	public String getOutTradeName2() {
		return outTradeName2;
	}

	/**
	 * @param outTradeName2
	 *            the outTradeName2 to set
	 */
	public void setOutTradeName2(String outTradeName2) {
		this.outTradeName2 = outTradeName2;
	}

	/**
	 * @return the outDate
	 */
	public Date getOutDate() {
		return outDate;
	}

	/**
	 * @param outDate
	 *            the outDate to set
	 */
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	/**
	 * @return the outAgentCode
	 */
	public String getOutAgentCode() {
		return outAgentCode;
	}

	/**
	 * @param outAgentCode
	 *            the outAgentCode to set
	 */
	public void setOutAgentCode(String outAgentCode) {
		this.outAgentCode = outAgentCode;
	}

	/**
	 * @return the outAgentName
	 */
	public String getOutAgentName() {
		return outAgentName;
	}

	/**
	 * @param outAgentName
	 *            the outAgentName to set
	 */
	public void setOutAgentName(String outAgentName) {
		this.outAgentName = outAgentName;
	}

	/**
	 * @return the conveyDay
	 */
	public Integer getConveyDay() {
		return conveyDay;
	}

	/**
	 * @param conveyDay
	 *            the conveyDay to set
	 */
	public void setConveyDay(Integer conveyDay) {
		this.conveyDay = conveyDay;
	}

	/**
	 * @return the conveySpa
	 */
	public Integer getConveySpa() {
		return conveySpa;
	}

	/**
	 * @param conveySpa
	 *            the conveySpa to set
	 */
	public void setConveySpa(Integer conveySpa) {
		this.conveySpa = conveySpa;
	}

	/**
	 * @return the outCorp
	 */
	public String getOutCorp() {
		return outCorp;
	}

	/**
	 * @param outCorp
	 *            the outCorp to set
	 */
	public void setOutCorp(String outCorp) {
		this.outCorp = outCorp;
	}

	/**
	 * @return the outDecl
	 */
	public String getOutDecl() {
		return outDecl;
	}

	/**
	 * @param outDecl
	 *            the outDecl to set
	 */
	public void setOutDecl(String outDecl) {
		this.outDecl = outDecl;
	}

	/**
	 * @return the outNote
	 */
	public String getOutNote() {
		return outNote;
	}

	/**
	 * @param outNote
	 *            the outNote to set
	 */
	public void setOutNote(String outNote) {
		this.outNote = outNote;
	}

	/**
	 * @return the inCopAppNo
	 */
	public String getInCopAppNo() {
		return inCopAppNo;
	}

	/**
	 * @param inCopAppNo
	 *            the inCopAppNo to set
	 */
	public void setInCopAppNo(String inCopAppNo) {
		this.inCopAppNo = inCopAppNo;
	}

	/**
	 * @return the inCustoms
	 */
	public Customs getInCustoms() {
		return inCustoms;
	}

	/**
	 * @param inCustoms
	 *            the inCustoms to set
	 */
	public void setInCustoms(Customs inCustoms) {
		this.inCustoms = inCustoms;
	}

	/**
	 * @return the inAgentCode
	 */
	public String getInAgentCode() {
		return inAgentCode;
	}

	/**
	 * @param inAgentCode
	 *            the inAgentCode to set
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
	 * @param inAgentName
	 *            the inAgentName to set
	 */
	public void setInAgentName(String inAgentName) {
		this.inAgentName = inAgentName;
	}

	/**
	 * @return the inCorp
	 */
	public String getInCorp() {
		return inCorp;
	}

	/**
	 * @param inCorp
	 *            the inCorp to set
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
	 * @param inDecl
	 *            the inDecl to set
	 */
	public void setInDecl(String inDecl) {
		this.inDecl = inDecl;
	}

	/**
	 * @return the inNote
	 */
	public String getInNote() {
		return inNote;
	}

	/**
	 * @param inNote
	 *            the inNote to set
	 */
	public void setInNote(String inNote) {
		this.inNote = inNote;
	}

	/**
	 * @return the inLiceNo
	 */
	public String getInLiceNo() {
		return inLiceNo;
	}

	/**
	 * @param inLiceNo
	 *            the inLiceNo to set
	 */
	public void setInLiceNo(String inLiceNo) {
		this.inLiceNo = inLiceNo;
	}

	/**
	 * @return the inDate
	 */
	public Date getInDate() {
		return inDate;
	}

	/**
	 * @param inDate
	 *            the inDate to set
	 */
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	/**
	 * @return the inTradeCode2
	 */
	public String getInTradeCode2() {
		return inTradeCode2;
	}

	/**
	 * @param inTradeCode2
	 *            the inTradeCode2 to set
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
	 * @param inTradeName2
	 *            the inTradeName2 to set
	 */
	public void setInTradeName2(String inTradeName2) {
		this.inTradeName2 = inTradeName2;
	}

	/**
	 * @return the declareState
	 */
	public String getDeclareState() {
		return declareState;
	}

	/**
	 * @param declareState
	 *            the declareState to set
	 */
	public void setDeclareState(String transferRequestBillState) {
		this.declareState = transferRequestBillState;
	}

	/**
	 * @return the modifyMarkState
	 */
	public String getModifyMarkState() {
		return modifyMarkState;
	}

	/**
	 * @param modifyMarkState
	 *            the modifyMarkState to set
	 */
	public void setModifyMarkState(String modifyMarkState) {
		this.modifyMarkState = modifyMarkState;
	}

	/**
	 * @return the projectType
	 */
	public Integer getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            the projectType to set
	 */
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	/**
	 * @return the aclUser
	 */
	public AclUser getAclUser() {
		return aclUser;
	}

	/**
	 * @param aclUser
	 *            the aclUser to set
	 */
	public void setAclUser(AclUser aclUser) {
		this.aclUser = aclUser;
	}

	public String getDelcareType() {
		return delcareType;
	}

	public void setDelcareType(String delcareType) {
		this.delcareType = delcareType;
	}

	/**
	 * 联网监管企业标记1－非联网监管企业 2－联网监管企业
	 * 
	 * @return
	 */
	public String getLwMark() {
		if (ProjectType.BCUS == this.projectType) {
			return "2";
		} else {
			return "1";
		}
	}

	/**
	 * @return the scmCoc
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	/**
	 * @param scmCoc the scmCoc to set
	 */
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	/**
	 * @return the oppositeEmsDate
	 */
	public Date getOppositeEmsDate() {
		return oppositeEmsDate;
	}

	/**
	 * @param oppositeEmsDate the oppositeEmsDate to set
	 */
	public void setOppositeEmsDate(Date oppositeEmsDate) {
		this.oppositeEmsDate = oppositeEmsDate;
	}

	public String getInEmsNo() {
		return inEmsNo;
	}

	public void setInEmsNo(String inEmsNo) {
		this.inEmsNo = inEmsNo;
	}

	public Boolean getIsCollated() {
		return isCollated;
	}

	public void setIsCollated(Boolean isCollated) {
		this.isCollated = isCollated;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


}