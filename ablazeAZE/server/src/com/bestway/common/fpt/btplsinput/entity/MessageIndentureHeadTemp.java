/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.common.fpt.btplsinput.entity;

import java.util.Date;

/**
 * 同步时至JBCUS.0收发货报文表头实体
 *
 * @author lzj
 */
public class MessageIndentureHeadTemp {
//       XML标签项	        中文说明	                  数据类型	 是否必填	    备注
//1	<BILL_NO>	收发货单编号	                    an17       否	 发货方不填，收货方必填
//2	<SEQ_NO>	电子口岸统一编号	           an18	     否         发货方不填，收货方必填
//3	<APP_NO>	申请表编号	                     an12      是
//4	<COP_BILL_NO>	发（收）货企业内部编号	        an..20	   是	     企业海关编码+发（收）货类型+进出口标志+7位流水号
//5	<TRADE_CODE>	发（收）货企业编码	          an10	    是
//6	<TRADE_NAME>	发（收）货企业名称	          an..70    是
//7	<ISSUE_DATE>	发（收）货日期	                   n14	     是
//8	<IS_DECLA_EM>	发（收）货申报人	          an..10     是
//9	<IS_DECLA_DATE>	发（收）货申报时间	          n14	    是
//10	<AGENT_CODE >	发（收）货申报企业9位组织机构代码   an10       是
//11	<AGENT_NAME>	发（收）货申报企业组织机构名称	     an..30	是
//12	<CONVEY_TYPE>	运输工具类别	                    an2	       否	发货方填写
//13	<CONVEY_NO>	运输工具编号	                    an..32     否	发货方填写
//14	<NOTE>	        备注	                       an..128	  否
//15	<CONTR_NO>	合同号	                      an..20	 否

    /**
     * ID
     */
    private String id = null;
    /**
     * 收发单编号 发货方不填，收货方必填
     */
    private String billNo = null;
    /**
     * 电子口岸统一编号 发货方不填，收货方必填
     */
    private String seqNo = null;
    /**
     * 申请表编号
     */
    private String appNo = null;
    /**
     * 合同号
     */
    private String contractNo = null;
    /**
     * 申报状态
     */
    private String declareState = null;
    /**
     * 发货企业内部编号 企业海关编码+发货类型+进出口标志+7位流水号
     */
    private String issueCopBillNo = null;
    /**
     * 发货企业编码
     */
    private String issueTradeCod = null;
    /**
     * 发货企业名称
     */
    private String issueTradeName = null;
    /**
     * 发货日期
     */
    private Date issueDate;
    /**
     * 发货申报人
     */
    private String issueIsDeclaEm = null;
    /**
     * 发货申报时间
     */
    private Date issueIsDeclaDate = null;
    /**
     * 发货申报企业9位组织机构代码
     */
    private String issueAgentCode = null;
    /**
     * 发货申报企业组织机构名称
     */
    private String issueAgentName = null;
    /**
     * 运输工具类别 发货方填写
     */
    private String conveType = null;
    /**
     * 运输工具编号 发货方填写
     */
    private String conveNo = null;
    /**
     * 发货备注
     */
    private String issueNote = null;
    /**
     * 收货企业内部编号 企业海关编码+收货类型+进出口标志+7位流水号
     */
    private String receiveCopBillNo = null;
    /**
     * 收货企业编码
     */
    private String receiveTradeCod = null;
    /**
     * 收货企业名称
     */
    private String receiveTradeName = null;
    /**
     * 收货日期
     */
    private Date receiveDate;
    /**
     * 收货申报人
     */
    private String receiveIsDeclaEm = null;
    /**
     * 收货申报时间
     */
    private Date receiveIsDeclaDate = null;
    /**
     * 收货申报企业9位组织机构代码
     */
    private String receiveAgentCode = null;
    /**
     * 收货申报企业组织机构名称
     */
    private String receiveAgentName = null;
    /**
     * 收货备注
     */
    private String receiveNote = null;
    /**
     * 是否是关帐
     */
    private Boolean isCloseRecon = null;
    /**
     * 单据类型
     */
    private Integer messageIndentureType;
    /**
     * 转出方是否同步至JBCUS
     */
    private Boolean isGYSJBCUSDown;
    /**
     * 转入方是否同步至JBCUS
     */
    private Boolean isKHJBCUSDown;
    
    /**
     *  是否选择与界面主键绑定
     */
    private Boolean isSelected = false;
    
    

    public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getConveNo() {
        return conveNo;
    }

    public void setConveNo(String conveNo) {
        this.conveNo = conveNo;
    }

    public String getConveType() {
        return conveType;
    }

    public void setConveType(String conveType) {
        this.conveType = conveType;
    }

    public String getIssueAgentCode() {
        return issueAgentCode;
    }

    public void setIssueAgentCode(String issueAgentCode) {
        this.issueAgentCode = issueAgentCode;
    }

    public String getIssueAgentName() {
        return issueAgentName;
    }

    public void setIssueAgentName(String issueAgentName) {
        this.issueAgentName = issueAgentName;
    }

    public String getIssueCopBillNo() {
        return issueCopBillNo;
    }

    public void setIssueCopBillNo(String issueCopBillNo) {
        this.issueCopBillNo = issueCopBillNo;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getIssueIsDeclaDate() {
        return issueIsDeclaDate;
    }

    public void setIssueIsDeclaDate(Date issueIsDeclaDate) {
        this.issueIsDeclaDate = issueIsDeclaDate;
    }

    public String getIssueIsDeclaEm() {
        return issueIsDeclaEm;
    }

    public void setIssueIsDeclaEm(String issueIsDeclaEm) {
        this.issueIsDeclaEm = issueIsDeclaEm;
    }

    public String getIssueNote() {
        return issueNote;
    }

    public void setIssueNote(String issueNote) {
        this.issueNote = issueNote;
    }

    public String getIssueTradeCod() {
        return issueTradeCod;
    }

    public void setIssueTradeCod(String issueTradeCod) {
        this.issueTradeCod = issueTradeCod;
    }

    public String getIssueTradeName() {
        return issueTradeName;
    }

    public void setIssueTradeName(String issueTradeName) {
        this.issueTradeName = issueTradeName;
    }

    public String getReceiveAgentCode() {
        return receiveAgentCode;
    }

    public void setReceiveAgentCode(String receiveAgentCode) {
        this.receiveAgentCode = receiveAgentCode;
    }

    public String getReceiveAgentName() {
        return receiveAgentName;
    }

    public void setReceiveAgentName(String receiveAgentName) {
        this.receiveAgentName = receiveAgentName;
    }

    public String getReceiveCopBillNo() {
        return receiveCopBillNo;
    }

    public void setReceiveCopBillNo(String receiveCopBillNo) {
        this.receiveCopBillNo = receiveCopBillNo;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Date getReceiveIsDeclaDate() {
        return receiveIsDeclaDate;
    }

    public void setReceiveIsDeclaDate(Date receiveIsDeclaDate) {
        this.receiveIsDeclaDate = receiveIsDeclaDate;
    }

    public String getReceiveIsDeclaEm() {
        return receiveIsDeclaEm;
    }

    public void setReceiveIsDeclaEm(String receiveIsDeclaEm) {
        this.receiveIsDeclaEm = receiveIsDeclaEm;
    }

    public String getReceiveTradeCod() {
        return receiveTradeCod;
    }

    public void setReceiveTradeCod(String receiveTradeCod) {
        this.receiveTradeCod = receiveTradeCod;
    }

    public String getReceiveTradeName() {
        return receiveTradeName;
    }

    public void setReceiveTradeName(String receiveTradeName) {
        this.receiveTradeName = receiveTradeName;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getReceiveNote() {
        return receiveNote;
    }

    public void setReceiveNote(String receiveNote) {
        this.receiveNote = receiveNote;
    }

    public String getDeclareState() {
        return declareState;
    }

    public void setDeclareState(String declareState) {
        this.declareState = declareState;
    }

    public Boolean getIsCloseRecon() {
        return isCloseRecon;
    }

    public void setIsCloseRecon(Boolean isCloseRecon) {
        this.isCloseRecon = isCloseRecon;
    }

    public Integer getMessageIndentureType() {
        return messageIndentureType;
    }

    public void setMessageIndentureType(Integer messageIndentureType) {
        this.messageIndentureType = messageIndentureType;
    }

    public Boolean getIsGYSJBCUSDown() {
        return isGYSJBCUSDown;
    }

    public void setIsGYSJBCUSDown(Boolean isGYSJBCUSDown) {
        this.isGYSJBCUSDown = isGYSJBCUSDown;
    }

    public Boolean getIsKHJBCUSDown() {
        return isKHJBCUSDown;
    }

    public void setIsKHJBCUSDown(Boolean isKHJBCUSDown) {
        this.isKHJBCUSDown = isKHJBCUSDown;
    }
}
