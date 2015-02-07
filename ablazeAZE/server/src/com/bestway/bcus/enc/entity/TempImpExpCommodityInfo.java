/*
 * Created on 2004-7-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * @author Administrator
 * 
 */
public class TempImpExpCommodityInfo implements Serializable, Comparable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 是否选中
	 */
	private Boolean isSelected = null;

	/**
	 * 进出口商品信息
	 */
	private ImpExpCommodityInfo impExpCommodityInfo = null;

	/**
	 * 所属申请单--拆分申请单时用到
	 */
	private String inImpExpRequestBill = null;

	/**
	 * 获得进出口商品信息
	 * 
	 * @return 进出口商品信息
	 */
	public ImpExpCommodityInfo getImpExpCommodityInfo() {
		return impExpCommodityInfo;
	}

	/**
	 * 判断是否选中
	 * 
	 * @return 是否选中
	 */
	public Boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * 设置进出口商品信息
	 * 
	 * @param impExpCommodityInfo
	 *            进出口商品信息
	 */
	public void setImpExpCommodityInfo(ImpExpCommodityInfo impExpCommodityInfo) {
		this.impExpCommodityInfo = impExpCommodityInfo;
	}

	/**
	 * 设置是否选中标志
	 * 
	 * @param isSelected
	 *            是否选中
	 */
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getInImpExpRequestBill() {
		return inImpExpRequestBill;
	}

	public void setInImpExpRequestBill(String inImpExpRequestBill) {
		this.inImpExpRequestBill = inImpExpRequestBill;
	}

	public int compareTo(Object o) {
		ImpExpCommodityInfo info = this.getImpExpCommodityInfo();
		ImpExpCommodityInfo oinfo = ((TempImpExpCommodityInfo) o)
				.getImpExpCommodityInfo();

		if (oinfo.getMateriel() == null) {
			return -1;
		}
		if (info.getMateriel() == null) {
			return 1;
		}
		return oinfo.getMateriel().getPtNo().compareTo(
				info.getMateriel().getPtNo());
	}
}