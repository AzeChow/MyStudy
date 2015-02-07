/*
 * Created on 2005-3-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractexe.entity;

import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * 存放报关单表头
 * 
 * @author Administrator
*/
public class DzscCustomsDeclaration extends BaseCustomsDeclaration{
	private static final long serialVersionUID  = CommonUtils
                                  .getSerialVersionUID();
	/**
	 * 合同协议号
	 */
	private String contractNo;
	
	/**
	 * 获取合同协议号
	 * 
	 * @return conractNo 合同协议号
	 */
	public String getContractNo() {
		return contractNo;
	}
	
	/**
	 * 设置合同协议号
	 * 
	 * @param conractNo 合同协议号
	 */
	public void setContractNo(String conractNo) {
		this.contractNo = conractNo;
	}
}
