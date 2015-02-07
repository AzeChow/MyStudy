package com.bestway.bcus.cas.bill.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * @author fhz
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class BillTempDetail extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 单据商品项明细
	 */
	private BillDetail billDetail;
	/**
	 * 是否被选中
	 */
	private Boolean isSelected = false;

	/**
	 * 取得单据商品项明细资料
	 * 
	 * @return billDetail 单据商品项明细
	 */
	public BillDetail getBillDetail() {
		return billDetail;
	}

	/**
	 * 设置单据商品项明细
	 * 
	 * @param billDetail
	 *            单据商品项明细
	 */
	public void setBillDetail(BillDetail billDetail) {
		this.billDetail = billDetail;
	}

	/**
	 * 判断是否被选中 默认为false
	 * 
	 * @return isSelected 是否被选中.
	 */
	public Boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * 设置是否被选中标志 默认为false
	 * 
	 * @param isSelected
	 *            是否被选中
	 */
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	// public void setContractUnit(Double contractUnit){
	//
	// }

}