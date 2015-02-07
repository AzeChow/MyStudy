package com.bestway.common.fpt.qp.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bestway.common.CommonUtils;

public class FptAppHeadByQp  {
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
    private String outDistrict = null;

    /** 转入企业代码 */
    private String inTradeCode = null;

    /** 转入企业代码 */
    private String inTradeName = null;

    /** 转入地 */
    private String inDistrict = null;

    /** 转出地海关 非空 */
    private String outCustoms = null;

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
    private String outDate = null;

    /** 申报企业9位组织机构代码 非空 转出方 */
    private String outAgentCode = null;

    /** 申报企业组织机构名称 非空 */
    private String outAgentName = null;

    /** 预计运输耗时（天） */
    private Double conveyDay = 0.0;

    /** 送货距离（公里） */
    private Double conveySpa = 0.0;

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
    private String inCustoms = null;

    /** 申报企业9位组织机构代码 非空 转出方 */
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
    private String inDate = null;

    /** 转入申报企业代码 可空 */
    private String inTradeCode2 = null;

    /** 转入申报企业名称 可空 */
    private String inTradeName2 = null;

    /**暂存", "处理失败", "审批通过", "正在处理", "全部" 0,1,2,3,4*/
    private Integer fptAppType = 4; //全部
    
    /**明细*/
    private List items = null;

	/**
	 * @return the inOutFlag
	 */
	public String getInOutFlag() {
		return inOutFlag;
	}

	/**
	 * @return the appNo
	 */
	public String getAppNo() {
		return appNo;
	}

	/**
	 * @return the seqNo
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @return the appClass
	 */
	public String getAppClass() {
		return appClass;
	}

	/**
	 * @return the contrNo
	 */
	public String getContrNo() {
		return contrNo;
	}

	/**
	 * @return the outTradeCode
	 */
	public String getOutTradeCode() {
		return outTradeCode;
	}

	/**
	 * @return the outTradeName
	 */
	public String getOutTradeName() {
		return outTradeName;
	}

	/**
	 * @return the outDistrict
	 */
	public String getOutDistrict() {
		return outDistrict;
	}

	/**
	 * @return the inTradeCode
	 */
	public String getInTradeCode() {
		return inTradeCode;
	}

	/**
	 * @return the inTradeName
	 */
	public String getInTradeName() {
		return inTradeName;
	}

	/**
	 * @return the inDistrict
	 */
	public String getInDistrict() {
		return inDistrict;
	}

	/**
	 * @return the outCustoms
	 */
	public String getOutCustoms() {
		return outCustoms;
	}

	/**
	 * @return the emsNo
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * @return the outCopAppNo
	 */
	public String getOutCopAppNo() {
		return outCopAppNo;
	}

	/**
	 * @return the outLiceNo
	 */
	public String getOutLiceNo() {
		return outLiceNo;
	}

	/**
	 * @return the outTradeCode2
	 */
	public String getOutTradeCode2() {
		return outTradeCode2;
	}

	/**
	 * @return the outTradeName2
	 */
	public String getOutTradeName2() {
		return outTradeName2;
	}

	/**
	 * @return the outDate
	 */
	public String getOutDate() {		
		return outDate;
	}

	/**
	 * @return the outAgentCode
	 */
	public String getOutAgentCode() {
		return outAgentCode;
	}

	/**
	 * @return the outAgentName
	 */
	public String getOutAgentName() {
		return outAgentName;
	}

	/**
	 * @return the conveyDay
	 */
	public Double getConveyDay() {
		return conveyDay;
	}

	/**
	 * @return the conveySpa
	 */
	public Double getConveySpa() {
		return conveySpa;
	}

	/**
	 * @return the outCorp
	 */
	public String getOutCorp() {
		return outCorp;
	}

	/**
	 * @return the outDecl
	 */
	public String getOutDecl() {
		return outDecl;
	}

	/**
	 * @return the outNote
	 */
	public String getOutNote() {
		return outNote;
	}

	/**
	 * @return the inCopAppNo
	 */
	public String getInCopAppNo() {
		return inCopAppNo;
	}

	/**
	 * @return the inCustoms
	 */
	public String getInCustoms() {
		return inCustoms;
	}

	/**
	 * @return the inAgentCode
	 */
	public String getInAgentCode() {
		return inAgentCode;
	}

	/**
	 * @return the inAgentName
	 */
	public String getInAgentName() {
		return inAgentName;
	}

	/**
	 * @return the inCorp
	 */
	public String getInCorp() {
		return inCorp;
	}

	/**
	 * @return the inDecl
	 */
	public String getInDecl() {
		return inDecl;
	}

	/**
	 * @return the inNote
	 */
	public String getInNote() {
		return inNote;
	}

	/**
	 * @return the inLiceNo
	 */
	public String getInLiceNo() {
		return inLiceNo;
	}

	/**
	 * @return the inDate
	 */
	public String getInDate() {		
		return inDate;
	}

	/**
	 * @return the inTradeCode2
	 */
	public String getInTradeCode2() {
		return inTradeCode2;
	}

	/**
	 * @return the inTradeName2
	 */
	public String getInTradeName2() {
		return inTradeName2;
	}

	/**
	 * @return the fptAppType
	 */
	public Integer getFptAppType() {
		return fptAppType;
	}

	/**
	 * @param inOutFlag the inOutFlag to set
	 */
	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	/**
	 * @param appNo the appNo to set
	 */
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	/**
	 * @param seqNo the seqNo to set
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @param appClass the appClass to set
	 */
	public void setAppClass(String appClass) {
		this.appClass = appClass;
	}

	/**
	 * @param contrNo the contrNo to set
	 */
	public void setContrNo(String contrNo) {
		this.contrNo = contrNo;
	}

	/**
	 * @param outTradeCode the outTradeCode to set
	 */
	public void setOutTradeCode(String outTradeCode) {
		this.outTradeCode = outTradeCode;
	}

	/**
	 * @param outTradeName the outTradeName to set
	 */
	public void setOutTradeName(String outTradeName) {
		this.outTradeName = outTradeName;
	}

	/**
	 * @param outDistrict the outDistrict to set
	 */
	public void setOutDistrict(String outDistrict) {
		this.outDistrict = outDistrict;
	}

	/**
	 * @param inTradeCode the inTradeCode to set
	 */
	public void setInTradeCode(String inTradeCode) {
		this.inTradeCode = inTradeCode;
	}

	/**
	 * @param inTradeName the inTradeName to set
	 */
	public void setInTradeName(String inTradeName) {
		this.inTradeName = inTradeName;
	}

	/**
	 * @param inDistrict the inDistrict to set
	 */
	public void setInDistrict(String inDistrict) {
		this.inDistrict = inDistrict;
	}

	/**
	 * @param outCustoms the outCustoms to set
	 */
	public void setOutCustoms(String outCustoms) {
		this.outCustoms = outCustoms;
	}

	/**
	 * @param emsNo the emsNo to set
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * @param outCopAppNo the outCopAppNo to set
	 */
	public void setOutCopAppNo(String outCopAppNo) {
		this.outCopAppNo = outCopAppNo;
	}

	/**
	 * @param outLiceNo the outLiceNo to set
	 */
	public void setOutLiceNo(String outLiceNo) {
		this.outLiceNo = outLiceNo;
	}

	/**
	 * @param outTradeCode2 the outTradeCode2 to set
	 */
	public void setOutTradeCode2(String outTradeCode2) {
		this.outTradeCode2 = outTradeCode2;
	}

	/**
	 * @param outTradeName2 the outTradeName2 to set
	 */
	public void setOutTradeName2(String outTradeName2) {
		this.outTradeName2 = outTradeName2;
	}

	/**
	 * @param outDate the outDate to set
	 */
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	/**
	 * @param outAgentCode the outAgentCode to set
	 */
	public void setOutAgentCode(String outAgentCode) {
		this.outAgentCode = outAgentCode;
	}

	/**
	 * @param outAgentName the outAgentName to set
	 */
	public void setOutAgentName(String outAgentName) {
		this.outAgentName = outAgentName;
	}

	/**
	 * @param conveyDay the conveyDay to set
	 */
	public void setConveyDay(Double conveyDay) {
		this.conveyDay = conveyDay;
	}

	/**
	 * @param conveySpa the conveySpa to set
	 */
	public void setConveySpa(Double conveySpa) {
		this.conveySpa = conveySpa;
	}

	/**
	 * @param outCorp the outCorp to set
	 */
	public void setOutCorp(String outCorp) {
		this.outCorp = outCorp;
	}

	/**
	 * @param outDecl the outDecl to set
	 */
	public void setOutDecl(String outDecl) {
		this.outDecl = outDecl;
	}

	/**
	 * @param outNote the outNote to set
	 */
	public void setOutNote(String outNote) {
		this.outNote = outNote;
	}

	/**
	 * @param inCopAppNo the inCopAppNo to set
	 */
	public void setInCopAppNo(String inCopAppNo) {
		this.inCopAppNo = inCopAppNo;
	}

	/**
	 * @param inCustoms the inCustoms to set
	 */
	public void setInCustoms(String inCustoms) {
		this.inCustoms = inCustoms;
	}

	/**
	 * @param inAgentCode the inAgentCode to set
	 */
	public void setInAgentCode(String inAgentCode) {
		this.inAgentCode = inAgentCode;
	}

	/**
	 * @param inAgentName the inAgentName to set
	 */
	public void setInAgentName(String inAgentName) {
		this.inAgentName = inAgentName;
	}

	/**
	 * @param inCorp the inCorp to set
	 */
	public void setInCorp(String inCorp) {
		this.inCorp = inCorp;
	}

	/**
	 * @param inDecl the inDecl to set
	 */
	public void setInDecl(String inDecl) {
		this.inDecl = inDecl;
	}

	/**
	 * @param inNote the inNote to set
	 */
	public void setInNote(String inNote) {
		this.inNote = inNote;
	}

	/**
	 * @param inLiceNo the inLiceNo to set
	 */
	public void setInLiceNo(String inLiceNo) {
		this.inLiceNo = inLiceNo;
	}

	/**
	 * @param inDate the inDate to set
	 */
	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	/**
	 * @param inTradeCode2 the inTradeCode2 to set
	 */
	public void setInTradeCode2(String inTradeCode2) {
		this.inTradeCode2 = inTradeCode2;
	}

	/**
	 * @param inTradeName2 the inTradeName2 to set
	 */
	public void setInTradeName2(String inTradeName2) {
		this.inTradeName2 = inTradeName2;
	}

	/**
	 * @param fptAppType the fptAppType to set
	 */
	public void setFptAppType(Integer fptAppType) {
		this.fptAppType = fptAppType;
	}

	/**
	 * @return the items
	 */
	public List getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List items) {
		this.items = items;
	}
    
    

    
	
	
}
