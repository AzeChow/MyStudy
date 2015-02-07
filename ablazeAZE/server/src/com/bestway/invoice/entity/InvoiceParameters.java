package com.bestway.invoice.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 出口专用发票管理参数
 * @author 
 *
 */
public class InvoiceParameters extends BaseScmEntity {
	
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	
	private Boolean isInvoiceWithyear=false;//true 发票前加年号   fale 发票前不加年号

	public Boolean getIsInvoiceWithyear() {
		return isInvoiceWithyear;
	}

	public void setIsInvoiceWithyear(Boolean isInvoiceWithyear) {
		this.isInvoiceWithyear = isInvoiceWithyear;
	}

	

}
