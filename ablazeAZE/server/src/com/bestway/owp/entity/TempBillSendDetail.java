package com.bestway.owp.entity;

import java.io.Serializable;
/**
 * 
 * @author hcl
 * 外发加工发货明细表
 */
public class TempBillSendDetail implements Serializable {
	/**
	 * 发货类型
	 */
	private String type=null;
	/**
	 * 发货单
	 */
	private OwpBillSendItem item=null;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public OwpBillSendItem getItem() {
		return item;
	}
	public void setItem(OwpBillSendItem item) {
		this.item = item;
	}
	
}
