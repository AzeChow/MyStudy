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
 * 存放国际进出口港口资料
 */
public class PortLin extends CustomBaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 港口英文名称
	 */
	private String portEnname;

	/**
	 * 航线
	 */
	private String portLine;

	/**
	 * 获取港口中午名称
	 * 
	 * @return portEnname 港口中午名称
	 */
	public String getPortEnname() {
		return portEnname;
	}

	/**
	 * 设置港口中午名称
	 * 
	 * @param portEnname
	 *            港口中午名称
	 */
	public void setPortEnname(String portEnname) {
		this.portEnname = portEnname;
	}

	/**
	 * 获取航线
	 * 
	 * @return portLine 航线
	 */
	public String getPortLine() {
		return portLine;
	}

	/**
	 * 设置航线
	 * 
	 * @param portLine
	 *            航线
	 */
	public void setPortLine(String portLine) {
		this.portLine = portLine;
	}

	/**
	 * 用于覆盖 包含 “(已删除)” 的字样
	 */
	@Override
	public String getName() {

		if (StringUtils.isNotBlank(super.getName())
				&& super.getName().contains("(已删除)")) {

			return super.getName().replace("(已删除)", "");

		}

		return super.getName();

	}
}
