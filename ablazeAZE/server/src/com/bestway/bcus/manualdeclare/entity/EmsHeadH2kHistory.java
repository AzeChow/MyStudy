/*
 * Created on 2004-7-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseEmsHead;

/**
 * 电子帐册历史
 * @author 001
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsHeadH2kHistory extends BaseEmsHead{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 帐册类型
	 */
	private String emsType;     
//	private String preEmsNo;     //预申报帐册编号
//	private String declareState; //帐册状态 1，申请变更
//	private String declareType;  //申报类型 1,申请备案 2,申请变更
	/**
	 * 企业内部编码
	 */
	private String copEmsNo;     
//	private String emsNo;        //帐册编号
//	private String sancEmsNo;    //批文帐册号
//	private String tradeCode;    //经营单位代码
//	private String tradeName;    //经营单位名称
//	private String machCode;     //收货单位代码
//	private String machName;     //收货单位名称	
	/**
	 * 申请单位类型
	 * APPLY_POR = "1";	申请备案
	 * WAIT_EAA = "2";	等待审批
	 * PROCESS_EXE = "3";	正在执行
	 * CHANGING_EXE = "4";	正在变更
	 * CHANGING_CANCEL = "5";	已经核销
	 */
	private String declareErType;
	/**
	 * 年加工生产能力
	 */
	private Double machAbility; 
	/**
	 * 最大周转金额
	 */
	private Double maxTurnMoney;
	/**
	 * 联系地址
	 */
	private String address;      
	/**
	 * 联系电话
	 */
	private String tel;          
//	private Date beginDate;      //开始有效期
//	private Date endDate;        //结束有效期
	/**
	 * 进口口岸
	 */
	private String iePort;       
	/**
	 * 外商公司
	 */
	private String outTradeCo;  
	/**
	 * 征免性质
	 */
	private LevyKind levyKind; 
	/**
	 * 保税方式 
	 */
	private PayMode payMode;    
	/**
	 * 加工种类
	 */
	private MachiningType machiningType;
	/**
	 * 录入员代号
	 */
	private String inputEr;     
	/**
	 * 录入日期
	 */
	private Date inputDate;     
//	private String emsApprNo;    //批准证号
	/**
	 * 申报日期
	 */
	private Date declareDate; 
	/**
	 * 申报时间
	 */
	private Date declareTime;   
//	private Date newApprDate;    //备案批准日期
//	private Date changeApprDate; //变更批准日期
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
	 * 投资金额
	 */
	private Double investAmount;
//	private String checkMark;    //审批标志
//	private String exeMark;      //执行标志
	/**
	 * 进口料件数
	 */
	private String imgItems;    
	/**
	 * 出口成品数
	 */
	private String exgItems;   
	/**
	 * 进口总金额
	 */
	private Double imgAmount;   
	/**
	 * 出口总金额
	 */
	private Double exgAmount;   
	/**
	 * 进口总重量
	 */
	private Double imgWeight;  
	/**
	 * 出口总重量
	 */
	private Double exgWeight;   
	
	
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
//	/**
//	 * @return Returns the beginDate.
//	 */
//	public Date getBeginDate() {
//		return beginDate;
//	}
//	/**
//	 * @param beginDate The beginDate to set.
//	 */
//	public void setBeginDate(Date beginDate) {
//		this.beginDate = beginDate;
//	}
//	/**
//	 * @return Returns the changeApprDate.
//	 */
//	public Date getChangeApprDate() {
//		return changeApprDate;
//	}
//	/**
//	 * @param changeApprDate The changeApprDate to set.
//	 */
//	public void setChangeApprDate(Date changeApprDate) {
//		this.changeApprDate = changeApprDate;
//	}
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
//	/**
//	 * @return Returns the declareState.
//	 */
//	public String getDeclareState() {
//		return declareState;
//	}
//	/**
//	 * @param declareState The declareState to set.
//	 */
//	public void setDeclareState(String declareState) {
//		this.declareState = declareState;
//	}
//	/**
//	 * @return Returns the declareType.
//	 */
//	public String getDeclareType() {
//		return declareType;
//	}
//	/**
//	 * @param declareType The declareType to set.
//	 */
//	public void setDeclareType(String declareType) {
//		this.declareType = declareType;
//	}
//	/**
//	 * @return Returns the emsApprNo.
//	 */
//	public String getEmsApprNo() {
//		return emsApprNo;
//	}
//	/**
//	 * @param emsApprNo The emsApprNo to set.
//	 */
//	public void setEmsApprNo(String emsApprNo) {
//		this.emsApprNo = emsApprNo;
//	}
//	/**
//	 * @return Returns the emsNo.
//	 */
//	public String getEmsNo() {
//		return emsNo;
//	}
//	/**
//	 * @param emsNo The emsNo to set.
//	 */
//	public void setEmsNo(String emsNo) {
//		this.emsNo = emsNo;
//	}
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
//	/**
//	 * @return Returns the endDate.
//	 */
//	public Date getEndDate() {
//		return endDate;
//	}
//	/**
//	 * @param endDate The endDate to set.
//	 */
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}
	/**
	 * @return Returns the inPort.
	 */
	public String getIePort() {
		return iePort;
	}
	/**
	 * @param inPort The inPort to set.
	 */
	public void setIePort(String iePort) {
		this.iePort = iePort;
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

//	/**
//	 * @return Returns the machCode.
//	 */
//	public String getMachCode() {
//		return machCode;
//	}
//	/**
//	 * @param machCode The machCode to set.
//	 */
//	public void setMachCode(String machCode) {
//		this.machCode = machCode;
//	}
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
	 * @return Returns the machiningType.
	 */
	public MachiningType getMachiningType() {
		return machiningType;
	}
	/**
	 * @param machiningType The machiningType to set.
	 */
	public void setMachiningType(MachiningType machiningType) {
		this.machiningType = machiningType;
	}
//	/**
//	 * @return Returns the machName.
//	 */
//	public String getMachName() {
//		return machName;
//	}
//	/**
//	 * @param machName The machName to set.
//	 */
//	public void setMachName(String machName) {
//		this.machName = machName;
//	}
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
//	/**
//	 * @return Returns the newApprDate.
//	 */
//	public Date getNewApprDate() {
//		return newApprDate;
//	}
//	/**
//	 * @param newApprDate The newApprDate to set.
//	 */
//	public void setNewApprDate(Date newApprDate) {
//		this.newApprDate = newApprDate;
//	}
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
	 * @return Returns the outTradeCo.
	 */
	public String getOutTradeCo() {
		return outTradeCo;
	}
	/**
	 * @param outTradeCo The outTradeCo to set.
	 */
	public void setOutTradeCo(String outTradeCo) {
		this.outTradeCo = outTradeCo;
	}
	/**
	 * @return Returns the payMode.
	 */
	public PayMode getPayMode() {
		return payMode;
	}
	/**
	 * @param payMode The payMode to set.
	 */
	public void setPayMode(PayMode payMode) {
		this.payMode = payMode;
	}
//	/**
//	 * @return Returns the sancEmsNo.
//	 */
//	public String getSancEmsNo() {
//		return sancEmsNo;
//	}
//	/**
//	 * @param sancEmsNo The sancEmsNo to set.
//	 */
//	public void setSancEmsNo(String sancEmsNo) {
//		this.sancEmsNo = sancEmsNo;
//	}
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
//	/**
//	 * @return Returns the tradeCode.
//	 */
//	public String getTradeCode() {
//		return tradeCode;
//	}
//	/**
//	 * @param tradeCode The tradeCode to set.
//	 */
//	public void setTradeCode(String tradeCode) {
//		this.tradeCode = tradeCode;
//	}
//	/**
//	 * @return Returns the tradeName.
//	 */
//	public String getTradeName() {
//		return tradeName;
//	}
//	/**
//	 * @param tradeName The tradeName to set.
//	 */
//	public void setTradeName(String tradeName) {
//		this.tradeName = tradeName;
//	}

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
//	/**
//	 * @return Returns the checkMark.
//	 */
//	public String getCheckMark() {
//		return checkMark;
//	}
//	/**
//	 * @param checkMark The checkMark to set.
//	 */
//	public void setCheckMark(String checkMark) {
//		this.checkMark = checkMark;
//	}
//	/**
//	 * @return Returns the exeMark.
//	 */
//	public String getExeMark() {
//		return exeMark;
//	}
//	/**
//	 * @param exeMark The exeMark to set.
//	 */
//	public void setExeMark(String exeMark) {
//		this.exeMark = exeMark;
//	}
	/**
	 * @return Returns the levyKind.
	 */
	public LevyKind getLevyKind() {
		return levyKind;
	}
	/**
	 * @param levyKind The levyKind to set.
	 */
	public void setLevyKind(LevyKind levyKind) {
		this.levyKind = levyKind;
	}
	/**
	 * @return Returns the exgItems.
	 */
	public String getExgItems() {
		return exgItems;
	}
	/**
	 * @param exgItems The exgItems to set.
	 */
	public void setExgItems(String exgItems) {
		this.exgItems = exgItems;
	}
	/**
	 * @return Returns the imgItems.
	 */
	public String getImgItems() {
		return imgItems;
	}
	/**
	 * @param imgItems The imgItems to set.
	 */
	public void setImgItems(String imgItems) {
		this.imgItems = imgItems;
	}
//	/**
//	 * @return Returns the preEmsNo.
//	 */
//	public String getPreEmsNo() {
//		return preEmsNo;
//	}
//	/**
//	 * @param preEmsNo The preEmsNo to set.
//	 */
//	public void setPreEmsNo(String preEmsNo) {
//		this.preEmsNo = preEmsNo;
//	}
	/**
	 * @return Returns the exgAmount.
	 */
	public Double getExgAmount() {
		return exgAmount;
	}
	/**
	 * @param exgAmount The exgAmount to set.
	 */
	public void setExgAmount(Double exgAmount) {
		this.exgAmount = exgAmount;
	}
	/**
	 * @return Returns the exgWeight.
	 */
	public Double getExgWeight() {
		return exgWeight;
	}
	/**
	 * @param exgWeight The exgWeight to set.
	 */
	public void setExgWeight(Double exgWeight) {
		this.exgWeight = exgWeight;
	}
	/**
	 * @return Returns the imgAmount.
	 */
	public Double getImgAmount() {
		return imgAmount;
	}
	/**
	 * @param imgAmount The imgAmount to set.
	 */
	public void setImgAmount(Double imgAmount) {
		this.imgAmount = imgAmount;
	}
	/**
	 * @return Returns the imgWeight.
	 */
	public Double getImgWeight() {
		return imgWeight;
	}
	/**
	 * @param imgWeight The imgWeight to set.
	 */
	public void setImgWeight(Double imgWeight) {
		this.imgWeight = imgWeight;
	}
}
