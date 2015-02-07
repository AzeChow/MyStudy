package com.bestway.bls.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 报文反馈信息
 * 
 * @author hw
 * 
 */
public class TempStockReturn implements Serializable {
	/**
	 * 服务Handle
	 */
	private String serviceHandle = null;
	/**
	 * 服务状态
	 */
	private String serviceStatus = null;
	/**
	 * 反馈描述
	 */
	private String description = null;
	/**
	 * 电子帐册号
	 */
	private String emsNo = null;
	/**
	 * 仓库编码
	 */
	private String wareHouseCode = null;
	/**
	 * 关区代码
	 */
	private String customsCode = null;

	/**
	 * 商品库存信息
	 */
	private List queryProducts = new ArrayList();

	/**
	 * 获取服务Handle
	 * 
	 * @return
	 */
	public String getServiceHandle() {
		return serviceHandle;
	}

	/**
	 * 设置服务Handle
	 * 
	 * @param serviceHandle
	 */
	public void setServiceHandle(String serviceHandle) {
		this.serviceHandle = serviceHandle;
	}

	/**
	 * 获取服务状态
	 * 
	 * @return
	 */
	public String getServiceStatus() {
		return serviceStatus;
	}

	/**
	 * 设置服务状态
	 * 
	 * @param serviceStatus
	 */
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	/**
	 * 获取反馈描述
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 反馈描述
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取电子帐册号
	 * 
	 * @return
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * 设置电子帐册号
	 * 
	 * @param emsNo
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 获取仓库编码
	 * 
	 * @return
	 */
	public String getWareHouseCode() {
		return wareHouseCode;
	}

	/**
	 * 设置仓库编码
	 * 
	 * @param wareHouseCode
	 */
	public void setWareHouseCode(String wareHouseCode) {
		this.wareHouseCode = wareHouseCode;
	}

	/**
	 * 获取关区代码
	 * 
	 * @return
	 */
	public String getCustomsCode() {
		return customsCode;
	}

	/**
	 * 设置关区代码
	 * 
	 * @param customsCode
	 */
	public void setCustomsCode(String customsCode) {
		this.customsCode = customsCode;
	}

	/**
	 * 获取商品库存信息
	 * 
	 * @return
	 */
	public List getQueryProducts() {
		return queryProducts;
	}

	/**
	 * 设置商品库存信息
	 * 
	 * @param queryProducts
	 */
	public void setQueryProducts(List queryProducts) {
		this.queryProducts = queryProducts;
	}
}
