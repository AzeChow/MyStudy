package com.bestway.common.fpt.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 用来存储转厂单据的临时实体类
 * 
 * @author hw
 * 
 */
public class TempFptBillHeadImportFromExcel extends BaseScmEntity {
	/**
	 * 结转单据商品信息（实体类对象）
	 */
	private FptBillItem fptBillItem = null;
	/**
	 * 转厂进出货单 （实体类对象）
	 */
	private FptBillHead fptBillHead = null;
	/**
	 * 错误信息
	 */
	private String errinfo = null;
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	public FptBillItem getFptBillItem() {
		return fptBillItem;
	}

	public void setFptBillItem(FptBillItem fptBillItem) {
		this.fptBillItem = fptBillItem;
	}

	public FptBillHead getFptBillHead() {
		return fptBillHead;
	}

	public void setFptBillHead(FptBillHead fptBillHead) {
		this.fptBillHead = fptBillHead;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
}
