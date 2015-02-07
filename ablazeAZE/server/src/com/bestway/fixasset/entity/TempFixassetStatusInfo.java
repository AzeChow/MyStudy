package com.bestway.fixasset.entity;

import java.util.Date;

public class TempFixassetStatusInfo implements java.io.Serializable{
	private Integer stateMark;	
	private String groupNo;	
	private String applier;	
	private String customsDeclarationCode;
	private String invoiceNo;
	private String certNo;
	private String warNo;
	private String complexCode;
	private String commName;	
	private String commSpec;
	
	private Date createDate;	
	private Date dutyCertApplyDate;
	private Date dutyCertApproveDate;	
	private Date prepExportNoteDate;
	private Date arriveHKDate;
	private Date arriveDGDate;
	private Date inspectDate;
	private Date declareDate;
	private Date inFactDate;
	
	public String getApplier() {
		return applier;
	}
	public void setApplier(String applier) {
		this.applier = applier;
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
	public String getCommName() {
		return commName;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
	public String getCommSpec() {
		return commSpec;
	}
	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}
	public String getComplexCode() {
		return complexCode;
	}
	public void setComplexCode(String coplexCode) {
		this.complexCode = coplexCode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
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

}
