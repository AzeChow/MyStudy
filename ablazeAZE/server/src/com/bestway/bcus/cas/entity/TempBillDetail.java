/*
 * Created on 2004-9-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.common.CommonUtils;

/**
 * @author 陈海鹏
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempBillDetail implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 工厂资料与报关商品折算明细
	 */
	private BillDetail billDetail = null;

	/**
	 * 是否被选中
	 */
	private Boolean isSelected = null;

	/**
	 * 入库数量
	 */
	private Double inCount = null;

	/**
	 * 出库数量
	 */
	private Double outCount = null;

	/**
	 * 结余数量(入库数量-出库数量)
	 */
	private Double residual = null;

	/**
	 * 取得工厂资料与报关商品折算明细
	 * 
	 * @return billDetail 工厂资料与报关商品折算明细.
	 */
	public BillDetail getBillDetail() {
		return billDetail;
	}

	/**
	 * 设置工厂资料与报关商品折算明细
	 * 
	 * @param billDetail
	 *            工厂资料与报关商品折算明细.
	 */
	public void setBillDetail(BillDetail billDetail) {
		this.billDetail = billDetail;
	}

	/**
	 * 判断是否被选中
	 * 
	 * @return isSelected 是否被选中.
	 */
	public Boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * 设置是否被选中标志
	 * 
	 * @param isSelected
	 *            是否被选中.
	 */
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Double getInCount() {
		return inCount;
	}

	public void setInCount(Double inCount) {
		this.inCount = inCount;
	}

	public Double getOutCount() {
		return outCount;
	}

	public void setOutCount(Double outCount) {
		this.outCount = outCount;
	}

	public Double getResidual() {
		residual = (inCount == null ? 0.0 : inCount)
				- (outCount == null ? 0.0 : outCount);
		return residual;
	}

	public void setResidual(Double residual) {
		this.residual = residual;
	}
}