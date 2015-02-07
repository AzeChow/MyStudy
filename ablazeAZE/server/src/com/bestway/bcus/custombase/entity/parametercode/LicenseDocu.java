/*
 * Created on 2004-6-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity.parametercode;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放报关参数－－证件代码资料
 * 
 * @author Administrator
 */
public class LicenseDocu extends CustomBaseEntity {
	/**
	 * 唯一编号
	 */
	private String id = null;
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
