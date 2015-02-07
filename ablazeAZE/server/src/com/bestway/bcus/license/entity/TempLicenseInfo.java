package com.bestway.bcus.license.entity;

import java.io.Serializable;

public class TempLicenseInfo implements Serializable{
	private String companyName;//公司名称
	private String edition;//版本
	private String maturityDate;//试用到期日
	private String maxNum;//客户端最大数
	private String loginedNum;//当前已经登陆客户数量
	private String loginedIP;//当前已经登陆客户IP
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getLoginedIP() {
		return loginedIP;
	}
	public void setLoginedIP(String loginedIP) {
		this.loginedIP = loginedIP;
	}
	public String getLoginedNum() {
		return loginedNum;
	}
	public void setLoginedNum(String loginedNum) {
		this.loginedNum = loginedNum;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}
}
