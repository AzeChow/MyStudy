/*
 * Created on 2005-3-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractexe.entity;

import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * 存放报关单物料资料
 */
public class BcsCustomsDeclarationCommInfo extends BaseCustomsDeclarationCommInfo {
	private static final long serialVersionUID  = CommonUtils
    .getSerialVersionUID();
	
	/**
	 * 凭证序号
	 */
	//,对应列名credenceNo
	private Integer credenceNo;
	/**
	 * 获取凭证序号
	 * 
	 * @return credenceNo 凭证序号
	 */
	public Integer getCredenceNo() {
		return credenceNo;
	}
	/**
	 * 设置凭证序号
	 * 
	 * @param credenceNo 凭证序号
	 */
	public void setCredenceNo(Integer credenceNo) {
		this.credenceNo = credenceNo;
	}
}
