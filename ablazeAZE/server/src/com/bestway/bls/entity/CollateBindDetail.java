package com.bestway.bls.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 核销捆绑关系货物项信息（表体）
 * @author ower
 *
 */
public class CollateBindDetail extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	
	/** 核销捆绑关系基本信息（表头）*/
	private CollateBind collateBind = null;	
	/** 序号 */
	private Integer gno = null;
	/** 备注	 */
	private String note = null;
	/**
	 * @return the collateBind
	 */
	public CollateBind getCollateBind() {
		return collateBind;
	}
	/**
	 * @return the gNo
	 */
	public Integer getGno() {
		return gno;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param collateBind the collateBind to set
	 */
	public void setCollateBind(CollateBind collateBind) {
		this.collateBind = collateBind;
	}
	/**
	 * @param no the gNo to set
	 */
	public void setGno(Integer no) {
		gno = no;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
