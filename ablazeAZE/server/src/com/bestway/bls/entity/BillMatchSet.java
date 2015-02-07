package com.bestway.bls.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 单据对应参数设置
 * @author Administrator
 *
 */
public class BillMatchSet extends BaseScmEntity{
	
	/**
	 * 单据对应参数，单据对应须确认,默认true,须确认
	 */
	private Boolean isCheack = true;

	public Boolean getIsCheack() {
		if(isCheack==null)
			return true;
		return isCheack;
	}

	public void setIsCheack(Boolean isCheack) {
		this.isCheack = isCheack;
	}

	
}
