package com.bestway.common.fpt.entity;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * 
 * 
 * 结转对应报表(单据中心的结转单转结转单据再转报关单)
 */
public class TempCasBillTOFptTOCustomsReport extends BaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 进出口申请单表体
	 */
	private BillDetail billDetail;

	/**
	 * 报关清单表体
	 */
	private FptBillItem fptBillItem;

	/**
	 * 报关单表体
	 */
	private BaseCustomsDeclarationCommInfo customsCommInfo;

	/**
	 * 转出/转入
	 * 
	 * @return
	 */
	private String inOutFlag = null;

	public BillDetail getBillDetail() {
		return billDetail;
	}

	public void setBillDetail(BillDetail billDetail) {
		this.billDetail = billDetail;
	}

	public FptBillItem getFptBillItem() {
		return fptBillItem;
	}

	public void setFptBillItem(FptBillItem fptBillItem) {
		this.fptBillItem = fptBillItem;
	}

	public BaseCustomsDeclarationCommInfo getCustomsCommInfo() {
		return customsCommInfo;
	}

	public void setCustomsCommInfo(
			BaseCustomsDeclarationCommInfo customsCommInfo) {
		this.customsCommInfo = customsCommInfo;
	}

	public String getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

}
