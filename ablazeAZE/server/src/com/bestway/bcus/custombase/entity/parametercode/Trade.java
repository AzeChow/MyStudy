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
 * 存放报关参数－－贸易方式资料
 * 
 * @author Administrator
 */
public class Trade extends CustomBaseEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
    /**
	 * 贸易方式全称
	 */
	private String tradeFname; 

	/**
	 * 获取贸易方式全称
	 * 
	 * @return tradeFname 贸易方式全称
	 */
	public String getTradeFname() {
		return tradeFname;
	}
	
	/**
	 * 设置贸易方式全称
	 * 
	 * @param tradeFname 贸易方式全称
	 */
	public void setTradeFname(String tradeFname) {
		this.tradeFname = tradeFname;
	}

}