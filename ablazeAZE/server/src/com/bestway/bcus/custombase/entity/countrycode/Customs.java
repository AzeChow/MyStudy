/*
 * Created on 2004-6-11
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity.countrycode;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放海关关区资料
 */
public class Customs extends CustomBaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 序号
	 */
	private String customsSerial;

	/**
	 * 获取序号
	 * 
	 * @return customsSerial 序号
	 */
	public String getCustomsSerial() {
		return customsSerial;
	}

	/**
	 * 设置序号
	 * 
	 * @param customsSerial
	 *            序号
	 */
	public void setCustomsSerial(String customsSerial) {
		this.customsSerial = customsSerial;
	}

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