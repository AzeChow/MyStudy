package com.bestway.common.fpt.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

public class TempFptEmail implements Serializable{
	private static final long serialVersionUID = 1L;

//	private Integer projectType = null;// 系统类型（0:联网监管；1：纸质手册；3：电子手册）
//
	private String emsNo = null;// 账册号（联网监管）；手册号（纸质手册和电子手册）
	
	private String seqNo=null;
	private String appState=null;
	private String appNo=null; //申请单编号
	private String outCopAppNo=null; //转出企业内部编号
	private String billNo=null; //发货单编号
	private String outName=null;//转出企业名称
	private String outCode=null;//转出企业编号
	private String inOutFlag =null;
	private String sysType=null;//申请,收发退类型


	public String getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getAppState() {
		return appState;
	}

	public void setAppState(String appState) {
		this.appState = appState;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public String getOutCopAppNo() {
		return outCopAppNo;
	}

	public void setOutCopAppNo(String outCopAppNo) {
		this.outCopAppNo = outCopAppNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getOutCode() {
		return outCode;
	}

	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}


}
