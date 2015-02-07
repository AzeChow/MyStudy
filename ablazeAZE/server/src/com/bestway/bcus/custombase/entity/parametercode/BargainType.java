/*
 * Created on 2004-6-12
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity.parametercode;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放合同类型资料
 */
public class BargainType extends CustomBaseEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 是否平衡
	 */
	private Boolean isBalance; 

	/**
	 * 获取是否平衡
	 * 
	 * @return isBalance 是否平衡
	 */
	public Boolean getIsBalance() {
		return isBalance;
	}
	/**
	 * 设置是否平衡
	 * 
	 * @param isBalance 是否平衡
	 */
	public void setIsBalance(Boolean isBalance) {
		this.isBalance = isBalance;
	}
}
