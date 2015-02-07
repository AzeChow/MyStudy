/*
 * Created on 2004-6-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;


import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放物流通用代码－－仓库设置资料
 * 
 * @author Administrator
 */
public class WareSet extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 仓库编号
	 */
	private String code;

	
	/**
	 * 仓库名称
	 */
	private String name;

	
	/**
	 * 仓库地址
	 */
	private String wareaddr;

	
	/**
	 * 联系电话
	 */
	private String waretel;

	
	/**
	 * 传真
	 */
	private String warefax;

	
	/**
	 * E-Mail
	 */
	private String wareemail;
	
	/**
	 * 属性
	 * "0"表示保税   "1"表示非保税,默认为保税(未填的话也为保税)
	 * wss2010.10.08
	 */
	private String wareProperty = "0";

	/**
	 * 获取仓库编号
	 * 
	 * @return code 仓库编号
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置仓库编号
	 * 
	 * @param code 仓库编号
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取仓库名称
	 * 
	 * @return name 仓库名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置仓库名称
	 * 
	 * @param name 仓库名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取仓库地址
	 * 
	 * @return wareaddr 仓库地址
	 */
	public String getWareaddr() {
		return wareaddr;
	}

	/**
	 * 设置仓库地址
	 * 
	 * @param wareaddr 仓库地址
	 */
	public void setWareaddr(String wareaddr) {
		this.wareaddr = wareaddr;
	}

	/**
	 * 获取E-Mail
	 * 
	 * @return wareemail E-Mail
	 */
	public String getWareemail() {
		return wareemail;
	}

	/**
	 * 设置E-Mail
	 * 
	 * @param wareemail E-Mail
	 */
	public void setWareemail(String wareemail) {
		this.wareemail = wareemail;
	}

	/**
	 * 获取传真
	 * 
	 * @return warefax 传真
	 */
	public String getWarefax() {
		return warefax;
	}

	/**
	 * 设置传真
	 * 
	 * @param warefax 传真
	 */
	public void setWarefax(String warefax) {
		this.warefax = warefax;
	}

	/**
	 * 获取联系电话
	 * 
	 * @return waretel 联系电话
	 */
	public String getWaretel() {
		return waretel;
	}

	/**
	 * 设置联系电话
	 * 
	 * @param waretel 联系电话
	 */
	public void setWaretel(String waretel) {
		this.waretel = waretel;
	}

	/**
	 * 属性 "0"表示保税 "1"表示非保税,默认为保税
	 * @return
	 */
	public String getWareProperty() {
		return wareProperty;
	}

	/**
	 * 属性 "0"表示保税 "1"表示非保税,默认为保税
	 * @param wareProperty
	 */
	public void setWareProperty(String wareProperty) {
		this.wareProperty = wareProperty;
	}
	
	

}