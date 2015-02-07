/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;


import java.util.Date;

import com.bestway.bcus.custombase.entity.basecode.CoType;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;


/**
 * 经营范围表头
 * @author 001
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsEdiTrHead extends BaseScmEntity implements Cloneable{
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
	 * 批准证号
	 */
	private String emsApprNo;   
	/**
	 * 预申报帐册编号
	 */
	private String preEmsNo;     
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
	private String ownerCode;      
	/**
	 * 加工单位名称
	 */
	private String ownerName;     
	/**
	 * 申报类型
	 */
	private String declareType;   
	/**
	 * 审批状态
	 * APPLY_POR = "1";	申请备案
	 * WAIT_EAA = "2";	等待审批
	 * PROCESS_EXE = "3";	正在执行
	 * CHANGING_EXE = "4";	正在变更
	 * CHANGING_CANCEL = "5";	已经核销
	 */
	private String declareState;     
	/**
	 * 申报单位类型
	 */
	private String declareErType;   
	/**
	 * 地区代码
	 */
	private District area;       
	/**
	 * 联系电话
	 */
	private String phone;      
	/**
	 * 联系地址
	 */
	private String address;    
	/**
	 * 投资金额
	 */
	private Double investAmount;  
	/**
	 * 年加工生产能力
	 */
	private Double productRatio;    
	/**
	 * 币制
	 */
	private Curr curr;            
	/**
	 * 企业性质
	 */
	private CoType ownerType;     
	/**
	 * 主管海关
	 */
	private Customs masterCustoms;  
	/**
	 * 主管外经委
	 */
	private RedDep declareDep;      
	/**
	 * 开始有效期
	 */
	private Date beginDate;       
	/**
	 * 结束有效期
	 */
	private Date endDate;           
	/**
	 * 申报日期
	 */
	private Date declareDate;      
	/**
	 * 申报时间
	 */
	private Date declareTime;     
	/**
	 * 录入日期
	 */
	private Date inputDate;        
	/**
	 * 录入员代号
	 */
	private String inputEr;        
	/**
	 * 帐册类型
	 */
	private String emsType;        
	/**
	 * 备案批准日期
	 */
	private Date newApprDate;      
	/**
	 * 变更批准日期
	 */
	private Date changeApprDate;    
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
	 * 审批标志
	 */
	private String checkMark;        
	/**
	 * 执行标志
	 */
	private String exeMark;        
	
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
	 * @return Returns the area.
	 */
	public District getArea() {
		return area;
	}
	/**
	 * @param area The area to set.
	 */
	public void setArea(District area) {
		this.area = area;
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
	 * @return Returns the curr.
	 */
	public Curr getCurr() {
		return curr;
	}
	/**
	 * @param curr The curr to set.
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
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
	 * @return Returns the declareDep.
	 */
	public RedDep getDeclareDep() {
		return declareDep;
	}
	/**
	 * @param declareDep The declareDep to set.
	 */
	public void setDeclareDep(RedDep declareDep) {
		this.declareDep = declareDep;
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
	/**
	 * @param emsType The emsType to set.
	 */
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
	public String getOwnerCode() {
		return ownerCode;
	}
	/**
	 * @param machCode The machCode to set.
	 */
	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}
	/**
	 * @return Returns the machinAbility.
	 */
	public Double getProductRatio() {
		return productRatio;
	}
	/**
	 * @param machinAbility The machinAbility to set.
	 */
	public void setProductRatio(Double productRatio) {
		this.productRatio = productRatio;
	}
	/**
	 * @return Returns the machKind.
	 */
	public CoType getOwnerType() {
		return ownerType;
	}
	/**
	 * @param machKind The machKind to set.
	 */
	public void setOwnerType(CoType ownerType) {
		this.ownerType = ownerType;
	}
	/**
	 * @return Returns the machName.
	 */
	public String getOwnerName() {
		return ownerName;
	}
	/**
	 * @param machName The machName to set.
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	/**
	 * @return Returns the masterCustoms.
	 */
	public Customs getMasterCustoms() {
		return masterCustoms;
	}
	/**
	 * @param masterCustoms The masterCustoms to set.
	 */
	public void setMasterCustoms(Customs masterCustoms) {
		this.masterCustoms = masterCustoms;
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
	 * @return Returns the tel.
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param tel The tel to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	/**
	 * @return Returns the preEmsNo.
	 */
	public String getPreEmsNo() {
		return preEmsNo;
	}
	/**
	 * @param preEmsNo The preEmsNo to set.
	 */
	public void setPreEmsNo(String preEmsNo) {
		this.preEmsNo = preEmsNo;
	}
}
