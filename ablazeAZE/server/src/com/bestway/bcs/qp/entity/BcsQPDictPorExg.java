package com.bestway.bcs.qp.entity;

import com.bestway.common.CommonUtils;

/**
 * 备案资料库成品表
 * 
 */
public class BcsQPDictPorExg implements java.io.Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 备案序号
	 */
	private Integer seqNum = null;

	/**
	 * 归并序号
	 */
	private Integer innerMergeSeqNum = null;

	/**
	 * 十位商品编码
	 */
	private String complexCode = null;

	/**
	 * 名称
	 */
	private String commName = null;

	/**
	 * 规格
	 */
	private String commSpec = null;

	/**
	 * 常用单位
	 */
	private String comUnitCode = null;

	/**
	 * 单价
	 */
	private Double unitPrice = null;

	/**
	 * 币制
	 */
	private String currCode = null;

	/**
	 * 备注
	 */
	private String memo;

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

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double price) {
		this.unitPrice = price;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getInnerMergeSeqNum() {
		return innerMergeSeqNum;
	}

	public void setInnerMergeSeqNum(Integer tenSeqNum) {
		this.innerMergeSeqNum = tenSeqNum;
	}

	public String getComplexCode() {
		return complexCode;
	}

	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}

	public String getComUnitCode() {
		return comUnitCode;
	}

	public void setComUnitCode(String comUnitCode) {
		this.comUnitCode = comUnitCode;
	}

	public String getCurrCode() {
		return currCode;
	}

	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}

}
