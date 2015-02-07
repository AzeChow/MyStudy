package com.bestway.common.fpt.qp.entity;

import java.util.List;

import com.bestway.common.CommonUtils;

public class FptBillHeadByQp {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 单据类型标志 收发货单 2 退货单 3
	 */
	private String sysType = null;

	/**
	 * 进出口标志中的进口标志 0 出口1 进口
	 * 
	 * 备注 :发货与收退货是0 ;收货与发退货是1
	 */
	private String billSort = null;

	/**
	 * 收发货单编号
	 */
	private String billNo = null;

	/**
	 * 电子口岸统一编号
	 */
	private String seqNo = null;

	/**
	 * 申请表编号
	 */
	private String appNo = null;

	/**
	 * 转出企业手册/账册号
	 */
	private String outEmsNo = null;

	/**
	 * 发货企业内部编号
	 */
	private String issueCopBillNo = null;

	/**
	 * 收货企业内部编号
	 */
	private String receiveCopBillNo = null;

	/**
	 * 发货企业编码
	 */
	private String issueTradeCod = null;

	/**
	 * 收货企业编码
	 */
	private String receiveTradeCod = null;

	/**
	 * 发货企业名称
	 */
	private String issueTradeName = null;

	/**
	 * 收货企业名称
	 */
	private String receiveTradeName = null;

	/**
	 * 发货日期
	 */
	private String issueDate = null;

	/**
	 * 收货日期
	 */
	private String receiveDate = null;

	/**
	 * 发货申报人
	 */
	private String issueIsDeclaEm = null;

	/**
	 * 收货申报人
	 */
	private String receiveIsDeclaEm = null;

	/**
	 * 发货申报时间
	 */
	private String issueIsDeclaDate = null;

	/**
	 * 收货申报时间
	 */
	private String receiveIsDeclaDate = null;

	/**
	 * 发货申报企业9位组织机构代码
	 */
	private String issueAgentCode = null;

	/**
	 * 收货申报企业9位组织机构代码
	 */
	private String receiveAgentCode = null;

	/**
	 * 发货申报企业组织机构名称
	 */
	private String issueAgentName = null;

	/**
	 * 发货申报企业组织机构名称
	 */
	private String receiveAgentName = null;

	/**
	 * 发货备注
	 */
	private String issueNote = null;

	/**
	 * 收货备注
	 */
	private String receiveNote = null;
	/**
	 * 明细
	 */

	private List items = null;

	/**
	 * @return the items
	 */
	public List getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List items) {
		this.items = items;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getBillSort() {
		return billSort;
	}

	public void setBillSort(String billSort) {
		this.billSort = billSort;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getOutEmsNo() {
		return outEmsNo;
	}

	public void setOutEmsNo(String outEmsNo) {
		this.outEmsNo = outEmsNo;
	}

	public String getIssueCopBillNo() {
		return issueCopBillNo;
	}

	public void setIssueCopBillNo(String issueCopBillNo) {
		this.issueCopBillNo = issueCopBillNo;
	}

	public String getReceiveCopBillNo() {
		return receiveCopBillNo;
	}

	public void setReceiveCopBillNo(String receiveCopBillNo) {
		this.receiveCopBillNo = receiveCopBillNo;
	}

	public String getIssueTradeCod() {
		return issueTradeCod;
	}

	public void setIssueTradeCod(String issueTradeCod) {
		this.issueTradeCod = issueTradeCod;
	}

	public String getReceiveTradeCod() {
		return receiveTradeCod;
	}

	public void setReceiveTradeCod(String receiveTradeCod) {
		this.receiveTradeCod = receiveTradeCod;
	}

	public String getIssueTradeName() {
		return issueTradeName;
	}

	public void setIssueTradeName(String issueTradeName) {
		this.issueTradeName = issueTradeName;
	}

	public String getReceiveTradeName() {
		return receiveTradeName;
	}

	public void setReceiveTradeName(String receiveTradeName) {
		this.receiveTradeName = receiveTradeName;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getIssueIsDeclaEm() {
		return issueIsDeclaEm;
	}

	public void setIssueIsDeclaEm(String issueIsDeclaEm) {
		this.issueIsDeclaEm = issueIsDeclaEm;
	}

	public String getReceiveIsDeclaEm() {
		return receiveIsDeclaEm;
	}

	public void setReceiveIsDeclaEm(String receiveIsDeclaEm) {
		this.receiveIsDeclaEm = receiveIsDeclaEm;
	}

	public String getIssueIsDeclaDate() {
		return issueIsDeclaDate;
	}

	public void setIssueIsDeclaDate(String issueIsDeclaDate) {
		this.issueIsDeclaDate = issueIsDeclaDate;
	}

	public String getReceiveIsDeclaDate() {
		return receiveIsDeclaDate;
	}

	public void setReceiveIsDeclaDate(String receiveIsDeclaDate) {
		this.receiveIsDeclaDate = receiveIsDeclaDate;
	}

	public String getIssueAgentCode() {
		return issueAgentCode;
	}

	public void setIssueAgentCode(String issueAgentCode) {
		this.issueAgentCode = issueAgentCode;
	}

	public String getReceiveAgentCode() {
		return receiveAgentCode;
	}

	public void setReceiveAgentCode(String receiveAgentCode) {
		this.receiveAgentCode = receiveAgentCode;
	}

	public String getIssueAgentName() {
		return issueAgentName;
	}

	public void setIssueAgentName(String issueAgentName) {
		this.issueAgentName = issueAgentName;
	}

	public String getReceiveAgentName() {
		return receiveAgentName;
	}

	public void setReceiveAgentName(String receiveAgentName) {
		this.receiveAgentName = receiveAgentName;
	}

	public String getIssueNote() {
		return issueNote;
	}

	public void setIssueNote(String issueNote) {
		this.issueNote = issueNote;
	}

	public String getReceiveNote() {
		return receiveNote;
	}

	public void setReceiveNote(String receiveNote) {
		this.receiveNote = receiveNote;
	}

}
