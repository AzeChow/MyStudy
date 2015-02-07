package com.bestway.bcus.system.entity;

import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;

public class TcsLinkMan extends BaseEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 联系人 
	 */
	private String name;
	/**
	 * 部门 
	 */
	private String department;
	/**
	 * 职务 
	 */
	private String duty;
	/**
	 * 电话 
	 */
	private String telephone;
	/**
	 * 手机 
	 */
	private String mobile;
	/**
	 * 传真 
	 */
	private String fax;
	/**
	 * 地址 
	 */
	private String address;
	/**
	 *  邮编 
	 */
	private String zipCode;
	/**
	 * EMAIL 
	 */
	private String email;
	/**
	 * IM编号 
	 */
	private String imCode;
	/**
	 * IM类型 
	 */
	private String imType;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImCode() {
		return imCode;
	}
	public void setImCode(String imCode) {
		this.imCode = imCode;
	}
	public String getImType() {
		return imType;
	}
	public void setImType(String imType) {
		this.imType = imType;
	}

}
