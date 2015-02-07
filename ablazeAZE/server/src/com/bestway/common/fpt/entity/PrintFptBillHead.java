package com.bestway.common.fpt.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 用于收退货每五笔打印
 * @author lyh
 *
 */
public class PrintFptBillHead extends FptBillHead  {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 流水号（用于分笔）
	 */
	private Integer serialNumber;

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
}