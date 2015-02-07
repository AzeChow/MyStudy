package com.bestway.bcus.cas.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;

/**
 * 存放物料主档的临时类
 */
public class TempEnterpriseMaterial extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 存物料主档的实体类
	 */
	private EnterpriseMaterial ebm = null;
	/**
	 * 错误信息
	 */
	private String errinfo;


	public EnterpriseMaterial getEbm() {
		return ebm;
	}

	public void setEbm(EnterpriseMaterial ebm) {
		this.ebm = ebm;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
}
