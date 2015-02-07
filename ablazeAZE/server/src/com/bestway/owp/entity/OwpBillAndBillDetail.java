package com.bestway.owp.entity;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.common.BaseScmEntity;

public class OwpBillAndBillDetail extends BaseScmEntity {

	private static final long serialVersionUID = -7911950017519355878L;

	/**
	 * 单据ID
	 */
	private String bill ;
	
	/**
	 * 单据类型代码
	 */
	private String billTypeCode ; 
	
	 /**单据类别
	 * 
	 * 商品类型
	 * FINISHED_PRODUCT="0";	成品
	 * SEMI_FINISHED_PRODUCT="1";	半成品
	 * MATERIEL="2";	材料--主料
	 * MACHINE="3";	设备
	 * REMAIN_MATERIEL="4";	边角料
	 * BAD_PRODUCT="5";	残次品
	 * ASSISTANT_MATERIAL = "6";	辅料
	 * WASTE_MATERIAL = "7";	消耗料
	 */
	private String  produceType = null;

	/**
	 * 外发加工 - 收发货登记 - 发货单企业表体
	 */
	private OwpBillSendItem owpBillSendItem ;
	
	/**
	 * 外发加工 - 收发货登记 - 收货单企业表体
	 */
	private OwpBillRecvItem owpBillRecvItem ;
	
	/**
	 * 是否进出口
	 */
	private String isOut ;
	
	
	
	public String getProduceType() {
		return produceType;
	}

	public void setProduceType(String produceType) {
		this.produceType = produceType;
	}

	public String getBillTypeCode() {
		return billTypeCode;
	}

	public void setBillTypeCode(String billTypeCode) {
		this.billTypeCode = billTypeCode;
	}

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public OwpBillSendItem getOwpBillSendItem() {
		return owpBillSendItem;
	}

	public void setOwpBillSendItem(OwpBillSendItem owpBillSendItem) {
		this.owpBillSendItem = owpBillSendItem;
	}

	public OwpBillRecvItem getOwpBillRecvItem() {
		return owpBillRecvItem;
	}

	public void setOwpBillRecvItem(OwpBillRecvItem owpBillRecvItem) {
		this.owpBillRecvItem = owpBillRecvItem;
	}

	public String getIsOut() {
		return isOut;
	}

	public void setIsOut(String isOut) {
		this.isOut = isOut;
	}
	
	
}
