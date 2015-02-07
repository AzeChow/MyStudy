package com.bestway.common.fpt.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

public class TempFptBillItem implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	private Boolean isSelected = false;
	private FptBillItem t = null;
	private String seqNum = null;
	/**
	 * 错误信息
	 */
	private String errinfo =null;

	/**
	 * @return Returns the seqNum.
	 */
	public String getSeqNum() {
		return seqNum;
	}

	/**
	 * @param seqNum
	 *            The seqNum to set.
	 */
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * @return Returns the isSelected.
	 */
	public Boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected
	 *            The isSelected to set.
	 */
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * @return Returns the transferFactoryCommodityInfo.
	 */
	public FptBillItem getT() {
		return t;
	}

	/**
	 * @param transferFactoryCommodityInfo
	 *            The transferFactoryCommodityInfo to set.
	 */
	public void setT(FptBillItem fptBillItem) {
		this.t = fptBillItem;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
}