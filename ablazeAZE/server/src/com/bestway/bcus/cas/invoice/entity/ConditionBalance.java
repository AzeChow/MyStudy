package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 差额总表查询条件
 * @author chensir
 *
 */
public class ConditionBalance implements Serializable {
	
	/**
	 * 料件／成品  true:料件    false:成品
	 */
	private boolean isM = true;

	/**
	 * 客户编码
	 */
	private String customerCode;
	/**
	 * 客户名称
	 */
	private String customerName;
	
	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 商品规格
	 */
	private String spec;
	
	/**
	 * 查询日期
	 */
	private Date date;
	
	/**
	 * 版本
	 */
	private String version;
	
	/**
	 * 台达使用厂区
	 */
	private String changQu;
	/**
	 * 台达使用的主管海关
	 */
	private String cutoms;
	/**
	 * 分组条件
	 */			
	private List<String> groupCondition;
	
	/**
	 * 重量或数量
	 */
	Boolean isQuantity;
	

	public List<String> getGroupCondition() {
		return groupCondition;
	}
	
	public void setGroupCondition(List<String> groupCondition) {
		this.groupCondition = groupCondition;
	}

	public String getChangQu() {
		return changQu;
	}

	public void setChangQu(String changQu) {
		this.changQu = changQu;
	}

	public String getCutoms() {
		return cutoms;
	}

	public void setCutoms(String cutoms) {
		this.cutoms = cutoms;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isM() {
		return isM;
	}

	public void setM(boolean isM) {
		this.isM = isM;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Boolean getIsQuantity() {
		return isQuantity;
	}

	public void setIsQuantity(Boolean isQuantity) {
		this.isQuantity = isQuantity;
	}
	
}
