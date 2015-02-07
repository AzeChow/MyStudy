package com.bestway.fixasset.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 批文协议设备分组表头
 * @author Administrator
 *
 */
public class AgreementGroupHead extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	/**
	 * 批文协议表头
	 */
	private Agreement agreement;

	/**
	 * 变更次数
	 */
	private Integer changedTimes;

	/**
	 * 组号
	 */
	private String groupNo;

	/**
	 * 发票号
	 */
	private String invoiceNo;

	/**
	 * 征免税号
	 */
	private String certNo;

	/**
	 * 保证书号(报检号)
	 */
	private String warNo;

	/**
	 * 状态
	 * ORIGINAL = "0";// 原始状态
	 * APPLY = "1"; // 申请
	 * EXECUTE = "2"; // 执行
	 * CHANGE = "3"; // 变更
	 * CHECK_CANCEL = "4"; // 核销
	 * BACK_BILL = "5";// 退单
	 */
	private Integer stateMark;

	/**
	 * 收件日期
	 */
	private Date createDate;

	/**
	 * 征免税证递单日期
	 */
	private Date dutyCertApplyDate;

	/**
	 * 征免税证出单日期
	 */
	private Date dutyCertApproveDate;

	/**
	 * 待出货通知日期
	 */
	private Date prepExportNoteDate;

	/**
	 * 设备到港日期
	 */
	private Date arriveHKDate;

	/**
	 * 设备到莞日期
	 */
	private Date arriveDGDate;

	/**
	 * 商检日期
	 */
	private Date inspectDate;

	/**
	 * 报关日期
	 */
	private Date declareDate;

	/**
	 * 进厂日期
	 */
	private Date inFactDate;
	/**
	 * 申请人
	 */
	private String applier;
	/**
	 * 报关单号
	 */
	private String customsDeclarationCode;
	

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	public Date getArriveDGDate() {
		return arriveDGDate;
	}

	public void setArriveDGDate(Date arriveDGDate) {
		this.arriveDGDate = arriveDGDate;
	}

	public Date getArriveHKDate() {
		return arriveHKDate;
	}

	public void setArriveHKDate(Date arriveHKDate) {
		this.arriveHKDate = arriveHKDate;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public Integer getChangedTimes() {
		return changedTimes;
	}

	public void setChangedTimes(Integer changedTimes) {
		this.changedTimes = changedTimes;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDeclareDate() {
		return declareDate;
	}

	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	public Date getDutyCertApplyDate() {
		return dutyCertApplyDate;
	}

	public void setDutyCertApplyDate(Date dutyCertApplyDate) {
		this.dutyCertApplyDate = dutyCertApplyDate;
	}

	public Date getDutyCertApproveDate() {
		return dutyCertApproveDate;
	}

	public void setDutyCertApproveDate(Date dutyCertApproveDate) {
		this.dutyCertApproveDate = dutyCertApproveDate;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public Date getInFactDate() {
		return inFactDate;
	}

	public void setInFactDate(Date inFactDate) {
		this.inFactDate = inFactDate;
	}

	public Date getInspectDate() {
		return inspectDate;
	}

	public void setInspectDate(Date inspectDate) {
		this.inspectDate = inspectDate;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getPrepExportNoteDate() {
		return prepExportNoteDate;
	}

	public void setPrepExportNoteDate(Date prepExportNoteDate) {
		this.prepExportNoteDate = prepExportNoteDate;
	}

	public Integer getStateMark() {
		return stateMark;
	}

	public void setStateMark(Integer stateMark) {
		this.stateMark = stateMark;
	}

	public String getWarNo() {
		return warNo;
	}

	public void setWarNo(String warNo) {
		this.warNo = warNo;
	}

	public String getApplier() {
		return applier;
	}

	public void setApplier(String applier) {
		this.applier = applier;
	}

	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

}
