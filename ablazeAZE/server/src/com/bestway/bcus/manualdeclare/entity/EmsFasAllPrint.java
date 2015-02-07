package com.bestway.bcus.manualdeclare.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.custombase.entity.basecode.ApplicationMode;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;

public class EmsFasAllPrint extends BaseScmEntity implements Comparator,Comparable{
	private Integer imSeqNum;
	/**
	 * 电子帐册编号
	 */
	private String emsHeadH2kNo;  
	/**
	 * 分册类型
	 */
	private String emsType;       
	/**
	 * 企业内部编码
	 */
	private String copEmsNo;      
	/**
	 * 分册号
	 */
	private String emsNo;         
	/**
	 * 经营单位代码
	 */
	private String tradeCode;     
	/**
	 * 经营单位名称
	 */
	private String tradeName;      
	/**
	 * 申报单位代码
	 */
	private String declareCode;    
	/**
	 * 申报单位名称
	 */
	private String declareName;   
	/**
	 * 分册期限
	 */
	private Date limitDate;        
	/**
	 * 申报标志
	 */
	private String declareMark;    
	/**
	 * 录入日期
	 */
	private Date inputDate;       
	/**
	 * 申报日期
	 */
	private Date declareDate;      
	/**
	 * 申报时间
	 */
	private Date declareTime;    
	/**
	 * 备案批准日期
	 */
	private Date newApprDate;     
	/**
	 * 变更批准日期
	 */
	private Date changeApprDate;   
	/**
	 * 进出口岸
	 */
	private String iePort ;       
	/**
	 * 电子帐册表头 
	 */
	private EmsHeadH2kFas emsHeadH2kFas;  

	
	
	public Integer getImSeqNum() {
		return imSeqNum;
	}
	/**
	 * @param imSeqNo The imSeqNo to set.
	 */
	public void setImSeqNum(Integer imSeqNum) {
		this.imSeqNum = imSeqNum;
	}
	public Date getChangeApprDate() {
		return changeApprDate;
	}
	public void setChangeApprDate(Date changeApprDate) {
		this.changeApprDate = changeApprDate;
	}
	public String getCopEmsNo() {
		return copEmsNo;
	}
	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}
	public String getDeclareCode() {
		return declareCode;
	}
	public void setDeclareCode(String declareCode) {
		this.declareCode = declareCode;
	}
	public Date getDeclareDate() {
		return declareDate;
	}
	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}
	public String getDeclareMark() {
		return declareMark;
	}
	public void setDeclareMark(String declareMark) {
		this.declareMark = declareMark;
	}
	public String getDeclareName() {
		return declareName;
	}
	public void setDeclareName(String declareName) {
		this.declareName = declareName;
	}
	public Date getDeclareTime() {
		return declareTime;
	}
	public void setDeclareTime(Date declareTime) {
		this.declareTime = declareTime;
	}
	public String getEmsHeadH2kNo() {
		return emsHeadH2kNo;
	}
	public void setEmsHeadH2kNo(String emsHeadH2kNo) {
		this.emsHeadH2kNo = emsHeadH2kNo;
	}
	public String getEmsNo() {
		return emsNo;
	}
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	public String getEmsType() {
		return emsType;
	}
	public void setEmsType(String emsType) {
		this.emsType = emsType;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public Date getLimitDate() {
		return limitDate;
	}
	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}
	public Date getNewApprDate() {
		return newApprDate;
	}
	public void setNewApprDate(Date newApprDate) {
		this.newApprDate = newApprDate;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getIePort() {
		return iePort;
	}
	public void setIePort(String iePort) {
		this.iePort = iePort;
	}
	public int compare(Object arg0, Object arg1) {
		try{
			Integer seqNum1 = ((Integer)PropertyUtils.getProperty(arg0,"imSeqNum"));
			Integer seqNum2 = ((Integer)PropertyUtils.getProperty(arg1,"imSeqNum"));
			return seqNum1.compareTo(seqNum2);				
		}
		catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	public int compareTo(Object arg0) {
		try{
			Integer seqNum1 = this.imSeqNum;
			Integer seqNum2 = ((Integer)PropertyUtils.getProperty(arg0,"imSeqNum"));
			return seqNum1.compareTo(seqNum2);	
		}
		catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	public EmsHeadH2kFas getEmsHeadH2kFas() {
		return emsHeadH2kFas;
	}
	public void setEmsHeadH2kFas(EmsHeadH2kFas emsHeadH2kFas) {
		this.emsHeadH2kFas = emsHeadH2kFas;
	}

}
