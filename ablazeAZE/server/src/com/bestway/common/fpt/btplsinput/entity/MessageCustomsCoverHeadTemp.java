/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.common.fpt.btplsinput.entity;

import java.util.Date;

/**
 * 同步到JBCUS的临时申请表
 *
 * @author kengbo
 */
public class MessageCustomsCoverHeadTemp {

    /**
     * ID
     */
    private String id = null;
    /**
     * 供应商代码
     */
    private String companyCode;
    /**
     * 供应商名称
     */
    private String companyName;
    /**
     * 客户代码
     */
    private String khCode;
    /**
     * 客户名称
     */
    private String khName;
    /**
     * 原申请表ID
     */
    private String customsHeadId = null;
    /**
     * 结转申请关封号
     */
    private String coverNO;
    /**
     * 申请表编号
     */
    private String appNo = null;
    /**
     * 电子口岸统一编号 X(18) 转出方不填，转入方必填
     */
    private String seqNo = null;
    /**
     * 结转申请表有效时间开始时间
     */
    private Date startDate;
    /**
     * 结转申请表有效时间结束时间
     */
    private Date expiryDate;
    /**
     * 申报状态
     */
    private String declareState = null;
    /**
     * 申报类型(1－备案申请 2－变更申请)
     */
    private String declareType = null;
    /**
     * 申请表类型
     */
    private String appClass = null;
    /**
     * 企业合同号
     */
    private String contrNo = null;
    /**
     * 转出企业代码 非空 转出方。为手册经营加工单位之一
     */
    private String outTradeCode = null;
    /**
     * 转出企业名称 非空
     */
    private String outTradeName = null;
    /**
     * 转出地
     */
    private String outDistrict = null;
    /**
     * 转入企业代码
     */
    private String inTradeCode = null;
    /**
     * 转入企业名称
     */
    private String inTradeName = null;
    /**
     * 转入地
     */
    private String inDistrict = null;
    /**
     * 转出地海关 非空
     */
    private String outCustoms = null;
    /**
     * 转出手册/账册编号 非空 转出方
     */
    private String emsNo = null;
    /**
     * 转出企业内部编号 非空 转出方，不可重复
     */
    private String outCopAppNo = null;
    /**
     * 转出企业批准证编号 非空 申请表商务部门审批编号，无审批时填写“人工审批”
     */
    private String outLiceNo = null;
    /**
     * 转出申报企业代码 可空 转出方
     */
    private String outTradeCode2 = null;
    /**
     * 转出申报企业名称 可空
     */
    private String outTradeName2 = null;
    /**
     * 转出申报日期 不允许录入及修改，当企业申报数据时，由系统自动填写。必须取当天日期；自动生成(yyyymmdd)
     */
    private Date outDate = null;
    /**
     * 申报企业9位组织机构代码 非空 转出方
     */
    private String outAgentCode = null;
    /**
     * 申报企业组织机构名称 非空
     */
    private String outAgentName = null;
    /**
     * 预计运输耗时（天）
     */
    private Integer conveyDay = null;
    /**
     * 送货距离（公里）
     */
    private Integer conveySpa = null;
    /**
     * 转出企业法人/联系电话 转出方
     */
    private String outCorp = null;
    /**
     * 转出申报人/联系电话 转出方
     */
    private String outDecl = null;
    /**
     * 转出企业备注
     */
    private String outNote = null;
    /**
     * 联网监管企业标记(1－备案申请 2－变更申请)
     */
    private String lwMark = null;
    /**
     * 转入企业内部编号 非空
     */
    private String inCopAppNo = null;
    /**
     * 转入地海关 非空
     */
    private String inCustoms = null;
    /**
     * 组织机构代码(转入)
     */
    private String inAgentCode = null;
    /**
     * 组织机构名称(转入)
     */
    private String inAgentName = null;
    /**
     * 转入企业法人/联系电话
     */
    private String inCorp = null;
    /**
     * 转入申报人/联系电话
     */
    private String inDecl = null;
    /**
     * 转入备注
     */
    private String inNote = null;
    /**
     * 转入企业批准证编号 可空 申请表商务部门审批编号，无审批时填写“人工审批”
     */
    private String inLiceNo = null;
    /**
     * 不允许录入及修改，当企业申报数据时，由系统自动填写。必须取当天日期；自动生成(yyyymmdd)
     */
    private Date inDate = null;
    /**
     * 转入申报企业代码 可空
     */
    private String inTradeCode2 = null;
    /**
     * 转入申报企业名称 可空
     */
    private String inTradeName2 = null;
    /**
     * 转入手册号
     */
    private String handsBookIn = null;
    /**
     * 对方手册生效日期
     */
    private Date oppositeEmsDate = null;
    
    /**
     * JBCUS是否下载过
     */
    private Boolean isJBCUSDown;
    
    /**
     *  是否选择与界面主键绑定
     */
    private Boolean isSelected = false;

    /**
     * @return the appNo
     */
    public String getAppNo() {
        return appNo;
    }

    /**
     * @param appNo the appNo to set
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
     * @param seqNo the seqNo to set
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
     * @param appClass the appClass to set
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
     * @param contrNo the contrNo to set
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
     * @param outTradeCode the outTradeCode to set
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
     * @param outTradeName the outTradeName to set
     */
    public void setOutTradeName(String outTradeName) {
        this.outTradeName = outTradeName;
    }

    /**
     * @return the inTradeCode
     */
    public String getInTradeCode() {
        return inTradeCode;
    }

    /**
     * @param inTradeCode the inTradeCode to set
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
     * @param inTradeName the inTradeName to set
     */
    public void setInTradeName(String inTradeName) {
        this.inTradeName = inTradeName;
    }

    /**
     * @return the emsNo
     */
    public String getEmsNo() {
        return emsNo;
    }

    /**
     * @param emsNo the emsNo to set
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
     * @param outCopAppNo the outCopAppNo to set
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
     * @param outLiceNo the outLiceNo to set
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
     * @param outTradeCode2 the outTradeCode2 to set
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
     * @param outTradeName2 the outTradeName2 to set
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
     * @param outDate the outDate to set
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
     * @param outAgentCode the outAgentCode to set
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
     * @param outAgentName the outAgentName to set
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
     * @param conveyDay the conveyDay to set
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
     * @param conveySpa the conveySpa to set
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
     * @param outCorp the outCorp to set
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
     * @param outDecl the outDecl to set
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
     * @param outNote the outNote to set
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
     * @param inCopAppNo the inCopAppNo to set
     */
    public void setInCopAppNo(String inCopAppNo) {
        this.inCopAppNo = inCopAppNo;
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
     * @return the inNote
     */
    public String getInNote() {
        return inNote;
    }

    /**
     * @param inNote the inNote to set
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
     * @param inLiceNo the inLiceNo to set
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
     * @param inDate the inDate to set
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

    public String getLwMark() {
        return lwMark;
    }

    public void setLwMark(String lwMark) {
        this.lwMark = lwMark;
    }

    public String getCustomsHeadId() {
        return customsHeadId;
    }

    public void setCustomsHeadId(String customsHeadId) {
        this.customsHeadId = customsHeadId;
    }

    public String getCoverNO() {
        return coverNO;
    }

    public void setCoverNO(String coverNO) {
        this.coverNO = coverNO;
    }

    public String getDeclareState() {
        return declareState;
    }

    public void setDeclareState(String declareState) {
        this.declareState = declareState;
    }

    public String getDeclareType() {
        return declareType;
    }

    public void setDeclareType(String declareType) {
        this.declareType = declareType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getHandsBookIn() {
        return handsBookIn;
    }

    public void setHandsBookIn(String handsBookIn) {
        this.handsBookIn = handsBookIn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getKhCode() {
        return khCode;
    }

    public void setKhCode(String khCode) {
        this.khCode = khCode;
    }

    public String getKhName() {
        return khName;
    }

    public void setKhName(String khName) {
        this.khName = khName;
    }

    public String getOutDistrict() {
        return outDistrict;
    }

    public void setOutDistrict(String outDistrict) {
        this.outDistrict = outDistrict;
    }

    public String getInDistrict() {
        return inDistrict;
    }

    public void setInDistrict(String inDistrict) {
        this.inDistrict = inDistrict;
    }

    public String getOutCustoms() {
        return outCustoms;
    }

    public void setOutCustoms(String outCustoms) {
        this.outCustoms = outCustoms;
    }

    public String getInCustoms() {
        return inCustoms;
    }

    public void setInCustoms(String inCustoms) {
        this.inCustoms = inCustoms;
    }

	public Boolean getIsJBCUSDown() {
		return isJBCUSDown;
	}

	public void setIsJBCUSDown(Boolean isJBCUSDown) {
		this.isJBCUSDown = isJBCUSDown;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
    
    
}
