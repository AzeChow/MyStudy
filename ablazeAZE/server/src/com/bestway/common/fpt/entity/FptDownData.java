package com.bestway.common.fpt.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 备案资料下载
 * 
 * @author hw
 * 
 */
public class FptDownData extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 电子口岸统一编号
	 */
	private String seqNo = null;  //  @jve:decl-index=0:

	/**
	 * 企业编号
	 */
	private String tradeCode = null;

	/**
	 * 转出企业内部编号
	 */
	private String outCopNo = null;

	/**
	 * 申请表编号
	 */
	private String appNo = null;

	/**
	 * 下载类型（A申请表，B退货，C收发货）
	 */
	private String downLoadState = null;  //  @jve:decl-index=0:
	/**
	 * 转进转出标志   0：转出，1：转入
	 */
	private String inOutFlag = null;  //  @jve:decl-index=0:
	/**
	 * 申报状态
	 */

	private String declareState = null;

	private String note = null;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getDownLoadState() {
		return downLoadState;
	}

	public void setDownLoadState(String downLoadState) {
		this.downLoadState = downLoadState;
	}

	public String getDeclareState() {
		return declareState;
	}

	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getOutCopNo() {
		return outCopNo;
	}

	public void setOutCopNo(String outCopNo) {
		this.outCopNo = outCopNo;
	}

	public String getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

}
