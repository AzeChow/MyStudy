/*
 * Created on 2004-8-17
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.owp.entity;

import java.util.Map;

import com.bestway.common.CommonUtils;
import com.bestway.common.message.entity.CspReceiptResult;

/**
 * 报文信息接收
 * 
 * @author yp // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class OwpReceiptResult extends CspReceiptResult {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 转入转出标记
	 */
	private String inOutFlag;

	/**
	 * 序号
	 */
	private String retNo;

//	/**
//	 * 单据号码
//	 */
//	private String customsNo;

	/**
	 * 下载回执的申请单或受退货单数据
	 */
	private Map mapDownData;

	public String getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	public String getRetNo() {
		return retNo;
	}

	public void setRetNo(String retNo) {
		this.retNo = retNo;
	}

//	public String getCustomsNo() {
//		return customsNo;
//	}
//
//	public void setCustomsNo(String billNo) {
//		this.customsNo = billNo;
//	}

	public Map getMapDownData() {
		return mapDownData;
	}

	public void setMapDownData(Map map) {
		this.mapDownData = map;
	}

}
