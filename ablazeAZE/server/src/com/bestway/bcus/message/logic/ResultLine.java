/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.message.logic;

/**
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResultLine {

	String tradeCode;
	String copEmsNo;
	String seqNo;
	String emsNo;
	String applType;
	String declareType;
	String chkMark;
	String noticeDate;
	String noticeTime;
	String note;

	public ResultLine(String sResultLine){
		tradeCode = new String(sResultLine.getBytes(),0+22,10).trim();//经营单位名称
	    copEmsNo = new String(sResultLine.getBytes(),0+22+10,20).trim();//企业内部编号
	    seqNo = new String(sResultLine.getBytes(),0+22+10+20,18).trim(); //清单编号
	    emsNo = new String(sResultLine.getBytes(),0+22+10+20+18,12).trim(); //帐册/分册编号
	    applType = new String(sResultLine.getBytes(),0+22+10+20+18+12,1).trim(); //数据类型
	    declareType = new String(sResultLine.getBytes(),0+22+10+20+18+12+1,1).trim();//申报类型
	    chkMark = new String(sResultLine.getBytes(),0+22+10+20+18+12+1+1,1).trim(); //处理结果 84
	    noticeDate = new String(sResultLine.getBytes(),0+22+10+20+18+12+1+1+1,8).trim(); //通知日期
	    noticeTime = new String(sResultLine.getBytes(),0+22+10+20+18+12+1+1+1+8,8-2).trim();//通知时间
	    note = new String(sResultLine.getBytes(),0+22+10+20+18+12+1+1+1+8+8-2,255).trim();//备注
	   
	}
	
	
	/**
	 * @return Returns the applType.
	 */
	public String getApplType() {
		return applType;
	}
	/**
	 * @return Returns the chkMark.
	 */
	public String getChkMark() {
		return chkMark;
	}
	/**
	 * @return Returns the copEmsNo.
	 */
	public String getCopEmsNo() {
		return copEmsNo;
	}
	/**
	 * @return Returns the declareType.
	 */
	public String getDeclareType() {
		return declareType;
	}
	/**
	 * @return Returns the emsNo.
	 */
	public String getEmsNo() {
		return emsNo;
	}
	/**
	 * @return Returns the note.
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @return Returns the noticeDate.
	 */
	public String getNoticeDate() {
		return noticeDate;
	}
	/**
	 * @return Returns the noticeTime.
	 */
	public String getNoticeTime() {
		return noticeTime;
	}
	/**
	 * @return Returns the seqNo.
	 */
	public String getSeqNo() {
		return seqNo;
	}
	/**
	 * @return Returns the tradeCode.
	 */
	public String getTradeCode() {
		return tradeCode;
	}

}
