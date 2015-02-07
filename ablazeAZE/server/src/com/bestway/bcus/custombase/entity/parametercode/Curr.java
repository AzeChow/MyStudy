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
 * 存放报关参数－－币制代码资料
 */
public class Curr extends CustomBaseEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 名称
	 */
	private String currSymb;


	

	/**
	 * 获取名称
	 * 
	 * @return currSymb 名称
	 */
	public String getCurrSymb() {
		return currSymb;
	}
	/**
	 * 设置名称
	 * 
	 * @param currSymb 名称
	 */
	public void setCurrSymb(String currSymb) {
		this.currSymb = currSymb;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return currSymb;
	}
	
	
}
