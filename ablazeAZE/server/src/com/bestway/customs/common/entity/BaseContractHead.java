/*
 * Created on 2005-3-21
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.customs.common.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.basecode.InvestMode;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.parametercode.FetchInMode;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.EquipMode;

/**
 * 所有的合同表头都是继承于它
 * 
 * @author ls
 * 
 */
public class BaseContractHead extends BaseEmsHead {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 收货地区
	 */
	private District receiveArea = null;

	/**
	 * 经办人
	 */
	private String handler = null;

	/**
	 * 浮动比
	 */
	private Double floatRate = null;

	/**
	 * 投资方式
	 */
	private InvestMode investMode = null;

	/**
	 * 引进方式
	 */
	private FetchInMode fetchInMode = null;

	/**
	 * 内销比
	 */
	private Double inSaleRate = null;

	/**
	 * 合同份数
	 */
	private Double contractItemCount = null;

	/**
	 * 重点标志
	 */
	private String emphasisFlag = null;

	/**
	 * 变更日期
	 */
	private Date changeDate = null;

	/**
	 * 录入人员对象
	 */
	private AclUser aclUser = null;

	/**
	 * 输入时间
	 */
	private Date saveDate = null;

	/**
	 * 申报时间
	 */
	private Date declareDate;

	/**
	 * 原料项目个数
	 */
	private Integer materielItemCount = null;

	/**
	 * 设备个数
	 */
	private Integer machineCount = null;

	/**
	 * 成品项目个数
	 */
	private Integer productItemCount = null;

	/**
	 * 管理对象
	 */
	protected String manageObject;

	/**
	 * 限制类标志 ADJUST_BEFORE_EMS = "1"; 调整前旧手册 ADJUST_AFTER_EMS = "2"; 调整后新手册
	 * ACOUNT_BOOK_USE = "3"; 台账专用手册
	 */
	private String limitFlag;

	/**
	 * 单耗申报环节 1.备案 2.出口前(默认) 3.报核前
	 */
	private String equipMode;
	
	/**
	 * 台帐银行 （TempInvestMode.PAPER-纸质台帐  TempInvestMode.BANKOFCHINA-中国银行 ICBC-工商银行）
	 */
	private String bankModel=null;

	/**
	 * 与外经关联主键
	 */
	private String wjComputerNo;

	/**
	 * 取得录入人员对象
	 * 
	 * @return 录入人员对象
	 */
	public AclUser getAclUser() {
		return aclUser;
	}

	/**
	 * 设置录入人员对象
	 * 
	 * @param aclUser
	 *            录入人员对象
	 */
	public void setAclUser(AclUser aclUser) {
		this.aclUser = aclUser;
	}

	/**
	 * 取得变更日期
	 * 
	 * @return 变更日期
	 */
	public Date getChangeDate() {
		return changeDate;
	}

	/**
	 * 设置变更日期
	 * 
	 * @param changeDate
	 *            变更日期
	 */
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	/**
	 * 取得合同份数
	 * 
	 * @return 合同份数
	 */
	public Double getContractItemCount() {
		return contractItemCount;
	}

	/**
	 * 设置合同份数
	 * 
	 * @param contractItemCount
	 *            合同份数
	 */
	public void setContractItemCount(Double contractItemCount) {
		this.contractItemCount = contractItemCount;
	}

	/**
	 * 取得重点标志
	 * 
	 * @return 重点标志
	 */
	public String getEmphasisFlag() {
		return emphasisFlag;
	}

	/**
	 * 设置重点标志
	 * 
	 * @param emphasisFlag
	 *            重点标志
	 */
	public void setEmphasisFlag(String emphasisFlag) {
		this.emphasisFlag = emphasisFlag;
	}

	/**
	 * 取得引进方式
	 * 
	 * @return 引进方式
	 */
	public FetchInMode getFetchInMode() {
		return fetchInMode;
	}

	/**
	 * 设置引进方式
	 * 
	 * @param fetchInMode
	 *            引进方式
	 */
	public void setFetchInMode(FetchInMode fetchInMode) {
		this.fetchInMode = fetchInMode;
	}

	/**
	 * 取得浮动比
	 * 
	 * @return 浮动比
	 */
	public Double getFloatRate() {
		return floatRate;
	}

	/**
	 * 设置浮动比
	 * 
	 * @param floatRate
	 *            浮动比
	 */
	public void setFloatRate(Double floatRate) {
		this.floatRate = floatRate;
	}

	/**
	 * 取得经办人
	 * 
	 * @return 经办人
	 */
	public String getHandler() {
		return handler;
	}

	/**
	 * 填写经办人
	 * 
	 * @param handler
	 *            经办人
	 */
	public void setHandler(String handler) {
		this.handler = handler;
	}

	/**
	 * 取得内销比
	 * 
	 * @return 内销比
	 */
	public Double getInSaleRate() {
		return inSaleRate;
	}

	/**
	 * 设置内销比
	 * 
	 * @param inSaleRate
	 *            内销比
	 */
	public void setInSaleRate(Double inSaleRate) {
		this.inSaleRate = inSaleRate;
	}

	/**
	 * 取得投资方式
	 * 
	 * @return 投资方式
	 */
	public InvestMode getInvestMode() {
		return investMode;
	}

	/**
	 * 设置投资方式
	 * 
	 * @param investMode
	 *            投资方式
	 */
	public void setInvestMode(InvestMode investMode) {
		this.investMode = investMode;
	}

	/**
	 * 取得设备个数
	 * 
	 * @return 设备个数
	 */
	public Integer getMachineCount() {
		return machineCount;
	}

	/**
	 * 填写设备个数
	 * 
	 * @param machineCount
	 *            设备个数
	 */
	public void setMachineCount(Integer machineCount) {
		this.machineCount = machineCount;
	}

	/**
	 * 取得原料项目个数
	 * 
	 * @return 原料项目个数
	 */
	public Integer getMaterielItemCount() {
		return materielItemCount;
	}

	/**
	 * 设置原料项目个数
	 * 
	 * @param materielItemCount
	 *            原料项目个数
	 */
	public void setMaterielItemCount(Integer materielItemCount) {
		this.materielItemCount = materielItemCount;
	}

	/**
	 * 取得成品项目个数
	 * 
	 * @return 成品项目个数
	 */
	public Integer getProductItemCount() {
		return productItemCount;
	}

	/**
	 * 填写成品项目个数
	 * 
	 * @param productItemCount
	 *            成品项目个数
	 */
	public void setProductItemCount(Integer productItemCount) {
		this.productItemCount = productItemCount;
	}

	/**
	 * 取得收货地区
	 * 
	 * @return 收货地区
	 */
	public District getReceiveArea() {
		return receiveArea;
	}

	/**
	 * 填写收货地区
	 * 
	 * @param receiveArea
	 *            收货地区
	 */
	public void setReceiveArea(District receiveArea) {
		this.receiveArea = receiveArea;
	}

	/**
	 * 取得输入时间
	 * 
	 * @return 输入时间
	 */
	public Date getSaveDate() {
		return saveDate;
	}

	/**
	 * 填写输入时间
	 * 
	 * @param saveDate
	 *            输入时间
	 */
	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}

	public Date getDeclareDate() {
		return declareDate;
	}

	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	public String getEquipMode() {
//		// 单耗申报环节默认为3 报核前
//		if (equipMode == null || "".equals(equipMode.trim())) {
//			return EquipMode.BEFORE_CANCEL;
//		}
		return equipMode;
	}

	public void setEquipMode(String equipMode) {
		this.equipMode = equipMode;
	}

	public String getLimitFlag() {
		return limitFlag;
	}

	public void setLimitFlag(String limitFlag) {
		this.limitFlag = limitFlag;
	}

	public String getManageObject() {
		return manageObject;
	}

	public void setManageObject(String manageObject) {
		this.manageObject = manageObject;
	}

	public String getWjComputerNo() {
		return wjComputerNo;
	}

	public void setWjComputerNo(String wjComputerNo) {
		this.wjComputerNo = wjComputerNo;
	}

	public String getBankModel() {
		return bankModel;
	}

	public void setBankModel(String bankModel) {
		this.bankModel = bankModel;
	}
	
	

}