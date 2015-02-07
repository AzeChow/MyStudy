package com.bestway.bls.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 保税物料进口申请表头
 * @author chen
 *
 */
public class MaterielImportApply extends BaseScmEntity{

	/**
	 * 仓库编号(WareSet)
	 */
	private WareSet warehouseCode;
	
	/**
	 * 申报状态
	 */
	private String declareState;
	/**
	 * 申请原因(String)
	 */
	private String applyReason;
	
	/**
	 * 申报时间 
	 */
	private Date applyDate;
	
	/**
	 * 申报人
	 */
	private String applyPerson;	
	
	/**
	 * 录入时间
	 */
	private Date writeDate;
	
	/**
	 * 录入人
	 */
	private String writePerson;
	
	/**
	 * 备注(String)
	 */
	private String note;

	

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyPerson() {
		return applyPerson;
	}

	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public String getWritePerson() {
		return writePerson;
	}

	public void setWritePerson(String writePerson) {
		this.writePerson = writePerson;
	}

	public WareSet getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(WareSet warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getDeclareState() {
		return declareState;
	}

	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}
}
