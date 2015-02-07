/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.erpbill.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存储订单转合同的中间表资料
 */
public class MakeCustomOrderToContract extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	
	/**
	 * 项目类型
	 */
	private Integer projectType;
	/**
	 * 合同成品商品信息
	 */
	private String contractImgExgId = null;
	/**
	 * 订单商品信息
	 */
	private String customOrderDetailId = null;
	
	
	public Integer getProjectType() {
		return projectType;
	}
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	public String getContractImgExgId() {
		return contractImgExgId;
	}
	public void setContractImgExgId(String contractImgExgId) {
		this.contractImgExgId = contractImgExgId;
	}
	public String getCustomOrderDetailId() {
		return customOrderDetailId;
	}
	public void setCustomOrderDetailId(String customOrderDetailId) {
		this.customOrderDetailId = customOrderDetailId;
	}
	
	

}