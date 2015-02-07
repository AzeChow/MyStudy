/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity.countrycode;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放地区代码资料
 */
public class District extends CustomBaseEntity {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 处理 包含 (已删除) 字样的 Name
	 */
	@Override
	public String getName() {

		if (StringUtils.isNotBlank(super.getName())
				&& super.getName().contains("(已删除)")) {

			return super.getName().replaceAll("(已删除)", "");

		}

		return super.getName();

	}
}
