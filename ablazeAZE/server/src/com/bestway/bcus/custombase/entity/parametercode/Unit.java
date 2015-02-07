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
 * 存放报关参数－－计量单位资料
 * 
 * @author refdom
 */
public class Unit extends CustomBaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 计量单位比例因子
	 */
	private Double unitRatio;

	/**
	 * 是否必须取整
	 */
	private Boolean isMustInt = false;

	/**
	 * @return 布尔型 isMustInt 是否必须取整
	 */
	public Boolean getIsMustInt() {
		if(isMustInt==null){
			return false;
		}
		return isMustInt;
	}

	/**
	 * @param isMustInt
	 *            是否必须取整
	 */

	public void setIsMustInt(Boolean isMustInt) {
		this.isMustInt = isMustInt;
	}

	/**
	 * 获取计量单位比例因子
	 * 
	 * @return unitRatio 计量单位比例因子
	 */
	public Double getUnitRatio() {
		return unitRatio;
	}

	/**
	 * 设置计量单位比例因子
	 * 
	 * @param unitRatio
	 *            计量单位比例因子
	 */
	public void setUnitRatio(Double unitRatio) {
		this.unitRatio = unitRatio;
	}
}