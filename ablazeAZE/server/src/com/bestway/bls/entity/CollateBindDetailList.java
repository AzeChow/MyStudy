package com.bestway.bls.entity;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 核销捆绑关系核销项信息
 * 
 * @author ower
 * 
 */
public class CollateBindDetailList extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/** 核销捆绑关系货物项信息（表体） */
	private CollateBindDetail collateBindDetail = null;
	/**
	 * 之所以要在 单证基本信息 和 核销项信息 中都使用 TradeCode 字段， 是因为要将 TradeCode 和 FormID
	 * 组合之后才能在海关的计算机系统里面确定唯一一份单证。 一般来说，对于同一份核销关系报文，单证基本信息 和 核销项信息 中的 TradeCode
	 * 字段的值是一样的
	 * */
	private String tradeCode = null;
	/** FormID 单证编号 对于初始库存的出仓可以填初始库存号。[!5] */
	private String formId = null;
	/** 货物序号 */
	private Integer gno = null;
	/** 货物数量 */
	private Double gcount = null;
	/** 备注 */
	private String note = null;
	/** 核扣单位 */
	private Unit unit = null;

	/**
	 * @return the collateBindDetail
	 */
	public CollateBindDetail getCollateBindDetail() {
		return collateBindDetail;
	}

	/**
	 * @return the tradeCode
	 */
	public String getTradeCode() {
		return tradeCode;
	}

	/**
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * @return the gNo
	 */
	public Integer getGno() {
		return gno;
	}

	/**
	 * @return the gCount
	 */
	public Double getGcount() {
		return gcount;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param collateBindDetail
	 *            the collateBindDetail to set
	 */
	public void setCollateBindDetail(CollateBindDetail collateBindDetail) {
		this.collateBindDetail = collateBindDetail;
	}

	/**
	 * @param tradeCode
	 *            the tradeCode to set
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	/**
	 * @param formId
	 *            the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}

	/**
	 * @param no
	 *            the gNo to set
	 */
	public void setGno(Integer no) {
		gno = no;
	}

	/**
	 * @param count
	 *            the gCount to set
	 */
	public void setGcount(Double count) {
		gcount = count;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
