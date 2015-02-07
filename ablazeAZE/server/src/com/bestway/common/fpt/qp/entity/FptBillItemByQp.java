package com.bestway.common.fpt.qp.entity;

import com.bestway.common.CommonUtils;

/**
 * 关封申请单明细
 */
public class FptBillItemByQp {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 转入转出标志
	 */
	private String billSort = null;
	/**
	 * 序号
	 * 
	 */
	private Integer listNo = 0;
	/**
	 * 发货序号
	 * 
	 * @return
	 */
	private Integer outNo = 0;
	/**
	 * 手册号
	 */
	private String inEmsNo = null;
	/**
	 * 料号
	 * 
	 * @return
	 */
	private String copGNo = null;
	/**
	 * 归并前商品名称
	 * 
	 * @return
	 */
	private String copGName = null;
	/**
	 * 归并前规格型号
	 */
	private String copGModel = null;
	/**
	 * 申请表序号
	 */
	private Integer appGNo = 0; // 对应申请表中表体的序号
	/**
	 * 项号
	 */
	private Integer trGno = 0; // 对应申请表中表体的备案序号
	/**
	 * 海关商品编码
	 */
	private String complex = null;

	/**
	 * 商品名称
	 */
	private String commName = null;

	/**
	 * 商品规格
	 */
	private String commSpec = null;
	/**
	 * 交易单位
	 */
	private String tradeUnit = null;
	/**
	 * 交易数量
	 */
	private Double tradeQty = 0.0;
	/**
	 * 计量单位
	 */
	private String unit = null;
	/**
	 * 申报数量
	 */
	private Double qty = 0.0;
	/**
	 * 备注
	 */
	private String note = null;

	public String getBillSort() {
		return billSort;
	}

	public void setBillSort(String billSort) {
		this.billSort = billSort;
	}

	public Integer getListNo() {
		return listNo;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public Integer getOutNo() {
		return outNo;
	}

	public void setOutNo(Integer outNo) {
		this.outNo = outNo;
	}

	public String getInEmsNo() {
		return inEmsNo;
	}

	public void setInEmsNo(String inEmsNo) {
		this.inEmsNo = inEmsNo;
	}

	public String getCopGNo() {
		return copGNo;
	}

	public void setCopGNo(String copGNo) {
		this.copGNo = copGNo;
	}

	public String getCopGName() {
		return copGName;
	}

	public void setCopGName(String copGName) {
		this.copGName = copGName;
	}

	public String getCopGModel() {
		return copGModel;
	}

	public void setCopGModel(String copGModel) {
		this.copGModel = copGModel;
	}

	public Integer getAppGNo() {
		return appGNo;
	}

	public void setAppGNo(Integer appGNo) {
		this.appGNo = appGNo;
	}

	public Integer getTrGno() {
		return trGno;
	}

	public void setTrGno(Integer trGno) {
		this.trGno = trGno;
	}

	public String getComplex() {
		return complex;
	}

	public void setComplex(String complex) {
		this.complex = complex;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getCommSpec() {
		return commSpec;
	}

	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	public String getTradeUnit() {
		return tradeUnit;
	}

	public void setTradeUnit(String tradeUnit) {
		this.tradeUnit = tradeUnit;
	}

	public Double getTradeQty() {
		return tradeQty;
	}

	public void setTradeQty(Double tradeQty) {
		this.tradeQty = tradeQty;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}