/*
 * Created on 2004-6-9
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity.basecode;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.common.CommonUtils;


/**
 * 存放海关注册公司的资料
 */
public class Brief extends CustomBaseEntity 
{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 检测帐号
	 */
	private String  chkManual;



	/**
	 * @return Returns the chkManual.
	 */
	public String getChkManual() {
		return chkManual;
	}
	/**
	 * @param chkManual The chkManual to set.
	 */
	public void setChkManual(String chkManual) {
		this.chkManual = chkManual;
	}

}

