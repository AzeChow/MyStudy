package com.bestway.dzsc.materialapply.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.common.BaseScmEntity;

/**
 * 归并表头
 */
public class DzscMaterielHead extends BaseScmEntity {
	/**
	 * 企业内部编号
	 */
	private String copEntNo;

	/**
	 * 经营单位海关编码,对应列名buCode
	 */
	private String tradeCode;

	/**
	 * 经营单位名称 ,对应列名buName
	 */
	private String tradeName;

	/**
	 * 加工单位海关编码,对应列名code
	 */
	private String machCode;

	/**
	 * 加工单位海关名称,对应列名name
	 */
	private String machName;

	/**
	 * 主管海关,对应列名masterCustoms,对应表Customs
	 */
	private Customs masterCustoms;

	/**
	 * 数据输入
	 */
	private Date inputDate;

	/**
	 * 申报日期
	 */
	private Date declareDate;

	/**
	 * 管理对象
	 */
	private String manageObject;

	/**
	 * 物料备案的状态
	 */
	private String materialState;

	/**
	 * 内部归并备案的状态
	 */
	private String innerMergeState;

	/**
	 * BOM备案的状态
	 */
	private String bomState;

	/**
	 * 备注
	 */
	private String note;

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String buCode) {
		this.tradeCode = buCode;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String buName) {
		this.tradeName = buName;
	}

	public String getMachCode() {
		return machCode;
	}

	public void setMachCode(String code) {
		this.machCode = code;
	}

	public String getMachName() {
		return machName;
	}

	public void setMachName(String name) {
		this.machName = name;
	}

	public String getCopEntNo() {
		return copEntNo;
	}

	public void setCopEntNo(String copEntNo) {
		this.copEntNo = copEntNo;
	}

	public Customs getMasterCustoms() {
		return masterCustoms;
	}

	public void setMasterCustoms(Customs masterCustoms) {
		this.masterCustoms = masterCustoms;
	}

	public Date getDeclareDate() {
		return declareDate;
	}

	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getManageObject() {
		return manageObject;
	}

	public void setManageObject(String manageObject) {
		this.manageObject = manageObject;
	}

	// public String getName() {
	// return name;
	// }
	//
	// public void setName(String name) {
	// this.name = name;
	// }

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getBomState() {
		return bomState;
	}

	public void setBomState(String bomState) {
		this.bomState = bomState;
	}

	public String getInnerMergeState() {
		return innerMergeState;
	}

	public void setInnerMergeState(String innerMergeState) {
		this.innerMergeState = innerMergeState;
	}

	public String getMaterialState() {
		return materialState;
	}

	public void setMaterialState(String materialState) {
		this.materialState = materialState;
	}
}
