/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.message.entity;


import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 消息查询
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CustomReceiptMessage extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 集成通任务ID
	 */
	private String tcsTaskId;
	/**
	 * 报关单类型
	 */
	private Integer projectType;
	/**
	 * 经营单位代码
	 */
	private String tradeCode;

	/**
	 * 内部报关单编号
	 */
	private String seqNo;    
	
	/**
	 * 手册编号
	 */
	private String emsNo;    	
	/**
	 * 回执类型
	 * APPLY_POR = "1";	申请备案
	 * WAIT_EAA = "2";	等待审批
	 * PROCESS_EXE = "3";	正在执行
	 * CHANGING_EXE = "4";	正在变更
	 * CHANGING_CANCEL = "5";	已经核销
	 */
	private String returnType; 
	/**
	 * 回执时间
	 */
	private Date returnDate; 
//	/**
//	 * 回执内容
//	 */
//	private String context;
	/**
	 * 回执处理者
	 */
	private String returnUser; 
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 报关单统一编号
	 */
	private String uniteCode;     
	/**
	 * 报关单号
	 */
	private String customs;
	/**
	 * 报关单预录入号
	 */
	private String ylrh;
	/**
	 * 回执状态
	 */
	private String success;
	/**
	 * 通知时间
	 */
	private String noticeDate;
	/**
	 * 申报口岸
	 */
	private String eportLocationCode;	
	/**
	 * 申报单位
	 */
	private String corporationName;	
	/**
	 * 报关员代码
	 */
	private String entrydeclarantNo;	
	/**
	 * 经营单位代码
	 */
	private String corporationCustomsCode;	
	/**
	 * 货场代码
	 */
	private String cyNo;	
	/**
	 * 保税仓库代码
	 */
	private String warehouseNo;	
	/**
	 * 进出口日期
	 */
	private String importExportDate;
	/**
	 * 件数
	 */
	private String packages;	
	/**
	 * 提单号
	 */
	private String billOfLadingNo;
	/**
	 * 运输方式
	 */
	private String meansOfTransportMode;
	/**
	 * 航班号
	 */
	private String meansOfTransportId;	
	/**
	 * 净重
	 */
	private String netWeight;	
	/**
	 * 毛重
	 */
	private String grossWeight;	
	/**
	 * 申报日期
	 */
	private String declareDate;
	
	
	public Integer getProjectType() {
		return projectType;
	}
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getEmsNo() {
		return emsNo;
	}
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
//	public String getContext() {
//		return context;
//	}
//	public void setContext(String context) {
//		this.context = context;
//	}
	public String getReturnUser() {
		return returnUser;
	}
	public void setReturnUser(String returnUser) {
		this.returnUser = returnUser;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUniteCode() {
		return uniteCode;
	}
	public void setUniteCode(String uniteCode) {
		this.uniteCode = uniteCode;
	}
	public String getCustoms() {
		return customs;
	}
	public void setCustoms(String customs) {
		this.customs = customs;
	}
	public String getTcsTaskId() {
		return tcsTaskId;
	}
	public void setTcsTaskId(String tcsTaskId) {
		this.tcsTaskId = tcsTaskId;
	}
	public String getYlrh() {
		return ylrh;
	}
	public void setYlrh(String ylrh) {
		this.ylrh = ylrh;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(String noticeDate) {
		this.noticeDate = noticeDate;
	}
	public String getEportLocationCode() {
		return eportLocationCode;
	}
	public void setEportLocationCode(String eportLocationCode) {
		this.eportLocationCode = eportLocationCode;
	}
	public String getCorporationName() {
		return corporationName;
	}
	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}
	public String getEntrydeclarantNo() {
		return entrydeclarantNo;
	}
	public void setEntrydeclarantNo(String entrydeclarantNo) {
		this.entrydeclarantNo = entrydeclarantNo;
	}
	public String getCorporationCustomsCode() {
		return corporationCustomsCode;
	}
	public void setCorporationCustomsCode(String corporationCustomsCode) {
		this.corporationCustomsCode = corporationCustomsCode;
	}
	public String getCyNo() {
		return cyNo;
	}
	public void setCyNo(String cyNo) {
		this.cyNo = cyNo;
	}
	public String getWarehouseNo() {
		return warehouseNo;
	}
	public void setWarehouseNo(String warehouseNo) {
		this.warehouseNo = warehouseNo;
	}
	public String getImportExportDate() {
		return importExportDate;
	}
	public void setImportExportDate(String importExportDate) {
		this.importExportDate = importExportDate;
	}
	public String getPackages() {
		return packages;
	}
	public void setPackages(String packages) {
		this.packages = packages;
	}
	public String getBillOfLadingNo() {
		return billOfLadingNo;
	}
	public void setBillOfLadingNo(String billOfLadingNo) {
		this.billOfLadingNo = billOfLadingNo;
	}
	public String getMeansOfTransportMode() {
		return meansOfTransportMode;
	}
	public void setMeansOfTransportMode(String meansOfTransportMode) {
		this.meansOfTransportMode = meansOfTransportMode;
	}
	public String getMeansOfTransportId() {
		return meansOfTransportId;
	}
	public void setMeansOfTransportId(String meansOfTransportId) {
		this.meansOfTransportId = meansOfTransportId;
	}
	public String getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}
	public String getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	public String getDeclareDate() {
		return declareDate;
	}
	public void setDeclareDate(String declareDate) {
		this.declareDate = declareDate;
	}
}
