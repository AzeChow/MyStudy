/*
 * Created on 2004-12-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.license.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * licese信息
 * 
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LicenseInfo implements Serializable {
	private static final long serialVersionUID = 3689913980965500976L;// CommonUtils.getSerialVersionUID();

	public final static int PASSED = 0;// 通过

	public final static int NO_LICENSE = -1;// 无license文件

	public final static int DATE_OUT = -2;// 使用期限已过

	public final static int CLINT_NUM_OUT = -3;// 客户端数量已超过允许数量

	/**
	 * 注册公司
	 */
	private List Companies;

	/**
	 * 详细信息
	 */
	private Map hmDetailInfo;// license详细信息

	/**
	 * 是否是试用版本
	 */
	private Boolean isProbationalEdition = false;

	private String createDate;

	private String macAddress;

	public List getCompanies() {
		return Companies;
	}

	public void setCompanies(List lsCompany) {
		this.Companies = lsCompany;
	}

	/**
	 * @return Returns the isProbationalEdition.
	 */
	public Boolean getIsProbationalEdition() {
		return isProbationalEdition;
	}

	/**
	 * @param isProbationalEdition
	 *            The isProbationalEdition to set.
	 */
	public void setIsProbationalEdition(Boolean isProbationalEdition) {
		this.isProbationalEdition = isProbationalEdition;
	}

	public Map getHmDetailInfo() {
		return hmDetailInfo;
	}

	public void setHmDetailInfo(Map hmExceptingModules) {
		this.hmDetailInfo = hmExceptingModules;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
}
