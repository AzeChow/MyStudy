package com.bestway.bcus.cas.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;

/**
 * 存放工厂BOM管理子件的临时类
 */
public class TempBomsManager extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 存放工厂BOM管理子件的实体类
	 */
	private EnterpriseBomManage ebm = null;
	/**
	 * 错误信息
	 */
	private String errinfo;

	public EnterpriseBomManage getEbm() {
		return ebm;
	}

	public void setEbm(EnterpriseBomManage ebm) {
		this.ebm = ebm;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
}
