package com.bestway.fixasset.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 批文协议设备保证书表头
 * @author Administrator
 *
 */
public class AgreementWarHead  extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	/**
	 * 协议批文号
	 */
	private String sancEmsNo;
	/**
	 * 保证书号
	 */
	private String warNo;
	
	/**
	 * 申请人
	 */
	private String applier;
	
	/**
	 * 联系人
	 */
	private String linkMan;
	
	/**
	 * 联系电话
	 */
	private String linkTel;
	
	/**
	 * 厂址
	 */
	private String address;

	public String getSancEmsNo() {
		return sancEmsNo;
	}

	public void setSancEmsNo(String sancEmsNo) {
		this.sancEmsNo = sancEmsNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getApplier() {
		return applier;
	}

	public void setApplier(String applier) {
		this.applier = applier;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public String getWarNo() {
		return warNo;
	}

	public void setWarNo(String warNo) {
		this.warNo = warNo;
	}
}
