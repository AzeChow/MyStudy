/*
 * Created on 2004-6-11
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity.countrycode;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放国家地区资料
 */
public class Country extends CustomBaseEntity {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 国家英文名称
	 */
	private String countryEnname;
	/**
	 * 检验标志
	 */
	private String countryMark;
	/**
	 * 是否是疫区，即是否商检的区域
	 */
	private Boolean isChcekup = false;

	/**
	 * 获取国家英文名称
	 * 
	 * @return countryEnname 国家英文名称
	 */
	public String getCountryEnname() {
		return countryEnname;
	}

	/**
	 * 设置国家英文名称
	 * 
	 * @param countryEnname
	 *            国家英文名称
	 */
	public void setCountryEnname(String countryEnname) {
		this.countryEnname = countryEnname;
	}

	/**
	 * 获取检验标志
	 * 
	 * @return countryMark 检验标志
	 */
	public String getCountryMark() {
		return countryMark;
	}

	/**
	 * 设置检验标志
	 * 
	 * @param countryMark
	 *            检验标志
	 */
	public void setCountryMark(String countryMark) {
		this.countryMark = countryMark;
	}

	public Boolean getIsChcekup() {
		return isChcekup;
	}

	public void setIsChcekup(Boolean isChcekup) {
		this.isChcekup = isChcekup;
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
