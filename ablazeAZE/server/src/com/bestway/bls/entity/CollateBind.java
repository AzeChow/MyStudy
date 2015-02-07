package com.bestway.bls.entity;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;
/**
 * 单证核销（CollateBind）
 * 核销捆绑关系基本信息（表头）
 * @author ower
 *
 */
public class CollateBind extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	
	/** 单证编号	如仓单号 */
	private String formID = null;
	/** 	海关注册编码 TradeCode	,Brief */
	private Brief brief = null;
	/** 单证类型	formID 所指示单证的类型
		入仓单-ImMft, 出仓单-ExMft */
	private String formType = null;
	/** 核销单证类型	FormID进行核销的单证类型 入仓单-ImMft, 出仓单-ExMft，入仓报关单-ImEnt，出仓报关单-ExEnt	*/
	private String collateFormType = null;
	/** 申报状态	初始状态、等待审批、正在执行、退单 */
	private String declareState = null;
	/** 备注	 */
	private String note = null;
	/**
	 * @return the formID
	 */
	public String getFormID() {
		return formID;
	}
	/**
	 * @return the scmCoc
	 */
	public Brief getBrief() {
		return brief;
	}
	/**
	 * @return the formType
	 */
	public String getFormType() {
		return formType;
	}
	/**
	 * @return the collateFormType
	 */
	public String getCollateFormType() {
		return collateFormType;
	}
	/**
	 * @return the declareState
	 */
	public String getDeclareState() {
		return declareState;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param formID the formID to set
	 */
	public void setFormID(String formID) {
		this.formID = formID;
	}
	/**
	 * @param scmCoc the scmCoc to set
	 */
	public void setBrief(Brief scmCoc) {
		this.brief = scmCoc;
	}
	/**
	 * @param formType the formType to set
	 */
	public void setFormType(String formType) {
		this.formType = formType;
	}
	/**
	 * @param collateFormType the collateFormType to set
	 */
	public void setCollateFormType(String collateFormType) {
		this.collateFormType = collateFormType;
	}
	/**
	 * @param declareState the declareState to set
	 */
	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
