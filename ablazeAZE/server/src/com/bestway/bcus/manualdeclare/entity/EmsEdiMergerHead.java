/*
 * Created on 2004-7-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 电子帐册归并关系表头
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EmsEdiMergerHead extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 企业内部编号
	 */
	private String copEmsNo;    
	/**
	 * 帐册编号	
	 */
	private String emsNo;     
	/**
	 * 批文帐册号
	 */
	private String sancEmsNo;    
	/**
	 * 批准证号
	 */
	private String emsApprNo;   
	/**
	 * 申报类型
	 */
    private String declareType;  
    /**
     * 审批状态
     */
    private String declareState;  
    /**
     * 帐册类型
     */
	private String emsType;       
	/**
	 * 经营单位代码
	 */
	private String tradeCode;     
	/**
	 * 经营单位名称
	 */
	private String tradeName;   
	/**
	 * 加工单位代码
	 */
	private String machCode;     
	/**
	 * 加工单位名称
	 */
	private String machName;     
	/**
	 * 联系电话
	 */
	private String tel;         
	/**
	 * 联系地址
	 */
	private String address;     
	/**
	 * 开始有效期
	 */
	private Date beginDate;      
	/**
	 * 结束有效期
	 */
	private Date endDate;       
	/**
	 * 申报单位类型
	 */
	private String declareErType; 
	/**
	 * 投资金额
	 */
	private Double investAmount;
	/**
	 * 年加工生产能力
	 */
	private Double machAbility;   
	/**
	 * 最大周转金额
	 */
	private Double maxTurnMoney;  
	/**
	 * 录入员代号
	 */
	private String inputEr;       
	/**
	 * 录入日期
	 */
	private Date inputDate;       
	/**
	 * 备案批准日期
	 */
	private Date newApprDate;     
	/**
	 * 变更批准日期
	 */
	private Date changeApprDate; 
	/**
	 * 申报日期
	 */
	private Date declareDate;    
	/**
	 * 申报时间
	 */
	private Date declareTime;    
	/**
	 * 备注
	 */
	private String note;         
	/**
	 * 历史记录
	 */
	private Boolean historyState=false; 
	/**
	 * 变更次数
	 */
	private Integer modifyTimes;  
    /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark;  
	/**
	 * 企业性质
	 */
	private String ownerType;     
	/**
	 * 地区代码
	 */
	private String area;          
	/**
	 * 审批标志
	 */
	private String checkMark;     
	/**
	 * 执行标志
	 */
	private String exeMark;       
	
	/**
	 * @return Returns the declareState.
	 */
	public String getDeclareState() {
		return declareState;
	}
	/**
	 * @param declareState The declareState to set.
	 */
	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}
	/**
	 * @return Returns the declareType.
	 */
	public String getDeclareType() {
		return declareType;
	}
	/**
	 * @param declareType The declareType to set.
	 */
	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}
	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return Returns the beginDate.
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate The beginDate to set.
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return Returns the changeApprDate.
	 */
	public Date getChangeApprDate() {
		return changeApprDate;
	}
	/**
	 * @param changeApprDate The changeApprDate to set.
	 */
	public void setChangeApprDate(Date changeApprDate) {
		this.changeApprDate = changeApprDate;
	}

	/**
	 * @return Returns the modifyMark.
	 */
	public String getModifyMark() {
		return modifyMark;
	}
	/**
	 * @param modifyMark The modifyMark to set.
	 */
	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}
	/**
	 * @return Returns the modifyTimes.
	 */
	public Integer getModifyTimes() {
		return modifyTimes;
	}
	/**
	 * @param modifyTimes The modifyTimes to set.
	 */
	public void setModifyTimes(Integer modifyTimes) {
		this.modifyTimes = modifyTimes;
	}
	/**
	 * 返回更改次数（海关申报用，固定为0）
	 */
	public Integer getModifyTimesMark() {
		return 0;
	}
	/**
	 * @return Returns the copEmsNo.
	 */
	public String getCopEmsNo() {
		return copEmsNo;
	}
	/**
	 * @param copEmsNo The copEmsNo to set.
	 */
	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}
	/**
	 * @return Returns the declareDate.
	 */
	public Date getDeclareDate() {
		return declareDate;
	}
	/**
	 * @param declareDate The declareDate to set.
	 */
	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}
	/**
	 * @return Returns the declareErType.
	 */
	public String getDeclareErType() {
		return declareErType;
	}
	/**
	 * @param declareErType The declareErType to set.
	 */
	public void setDeclareErType(String declareErType) {
		this.declareErType = declareErType;
	}
	/**
	 * @return Returns the declareTime.
	 */
	public Date getDeclareTime() {
		return declareTime;
	}
	/**
	 * @param declareTime The declareTime to set.
	 */
	public void setDeclareTime(Date declareTime) {
		this.declareTime = declareTime;
	}
	/**
	 * @return Returns the emsApprNo.
	 */
	public String getEmsApprNo() {
		return emsApprNo;
	}
	/**
	 * @param emsApprNo The emsApprNo to set.
	 */
	public void setEmsApprNo(String emsApprNo) {
		this.emsApprNo = emsApprNo;
	}
	/**
	 * @return Returns the emsNo.
	 */
	public String getEmsNo() {
		return emsNo;
	}
	/**
	 * @param emsNo The emsNo to set.
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	/**
	 * @return Returns the emsType.
	 */
	public String getEmsType() {
		return emsType;
	}

	public void setEmsType(String emsType) {
		this.emsType = emsType;
	}
	/**
	 * @return Returns the endDate.
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Date getInputDate() {
		return inputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	/**
	 * @return Returns the inputUser.
	 */
	public String getInputEr() {
		return inputEr;
	}
	/**
	 * @param inputUser The inputUser to set.
	 */
	public void setInputEr(String inputEr) {
		this.inputEr = inputEr;
	}
	/**
	 * @return Returns the investAmount.
	 */
	public Double getInvestAmount() {
		return investAmount;
	}
	/**
	 * @param investAmount The investAmount to set.
	 */
	public void setInvestAmount(Double investAmount) {
		this.investAmount = investAmount;
	}
	/**
	 * @return Returns the machCode.
	 */
	public String getMachCode() {
		return machCode;
	}
	/**
	 * @param machCode The machCode to set.
	 */
	public void setMachCode(String machCode) {
		this.machCode = machCode;
	}
	/**
	 * @return Returns the machinAbility.
	 */
	public Double getMachAbility() {
		return machAbility;
	}
	/**
	 * @param machinAbility The machinAbility to set.
	 */
	public void setMachAbility(Double machAbility) {
		this.machAbility = machAbility;
	}
	/**
	 * @return Returns the machName.
	 */
	public String getMachName() {
		return machName;
	}
	/**
	 * @param machName The machName to set.
	 */
	public void setMachName(String machName) {
		this.machName = machName;
	}
	/**
	 * @return Returns the maxTurnMoney.
	 */
	public Double getMaxTurnMoney() {
		return maxTurnMoney;
	}
	/**
	 * @param maxTurnMoney The maxTurnMoney to set.
	 */
	public void setMaxTurnMoney(Double maxTurnMoney) {
		this.maxTurnMoney = maxTurnMoney;
	}
	/**
	 * @return Returns the newApprDate.
	 */
	public Date getNewApprDate() {
		return newApprDate;
	}
	/**
	 * @param newApprDate The newApprDate to set.
	 */
	public void setNewApprDate(Date newApprDate) {
		this.newApprDate = newApprDate;
	}
	/**
	 * @return Returns the note.
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note The note to set.
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return Returns the sancEmsNo.
	 */
	public String getSancEmsNo() {
		return sancEmsNo;
	}
	/**
	 * @param sancEmsNo The sancEmsNo to set.
	 */
	public void setSancEmsNo(String sancEmsNo) {
		this.sancEmsNo = sancEmsNo;
	}
	/**
	 * @return Returns the tel.
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel The tel to set.
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return Returns the tradeCode.
	 */
	public String getTradeCode() {
		return tradeCode;
	}
	/**
	 * @param tradeCode The tradeCode to set.
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	/**
	 * @return Returns the tradeName.
	 */
	public String getTradeName() {
		return tradeName;
	}
	/**
	 * @param tradeName The tradeName to set.
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	/**
	 * @return Returns the historyState.
	 */
	public Boolean getHistoryState() {
		return historyState;
	}
	/**
	 * @param historyState The historyState to set.
	 */
	public void setHistoryState(Boolean historyState) {
		this.historyState = historyState;
	}
	/**
	 * @return Returns the ownerType.
	 */
	public String getOwnerType() {
		return ownerType;
	}
	/**
	 * @param ownerType The ownerType to set.
	 */
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	/**
	 * @return Returns the area.
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @param area The area to set.
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * @return Returns the checkMark.
	 */
	public String getCheckMark() {
		return checkMark;
	}
	/**
	 * @param checkMark The checkMark to set.
	 */
	public void setCheckMark(String checkMark) {
		this.checkMark = checkMark;
	}
	/**
	 * @return Returns the exeMark.
	 */
	public String getExeMark() {
		return exeMark;
	}
	/**
	 * @param exeMark The exeMark to set.
	 */
	public void setExeMark(String exeMark) {
		this.exeMark = exeMark;
	}
}