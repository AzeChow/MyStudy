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
 * 存放国内进出口码头资料
 */
public class PreDock extends CustomBaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 关区 2010-05-31 hcl
	 */
	private String cusCode;
	/**
	 * 简称（报关填写规则） 2010-05-31 hcl
	 */
	private String shortName;

	/**
	 * 获取简称（报关填写规则） 2010-05-31 hcl
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * 设置简称（报关填写规则） 2010-05-31 hcl
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * 获取关区 2010-05-31 hcl
	 * 
	 * @return cusCode 商品编码
	 */
	public String getCusCode() {
		return cusCode;
	}

	/**
	 * 设置商关区 2010-05-31 hcl
	 * 
	 * @param cusCode
	 *            商品编码
	 */
	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
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
