package com.bestway.bcus.custombase.entity.parametercode;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放报关行资料
 * @author Administrator
 *
 */
public class CustomsBroker extends CustomBaseEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
	 * 报关行地址
	 */
	private String address;
	/**
	 * 报关行联系人
	 */
	private String linkName;
	/**
	 * 报关行联系电话
	 */
	private String linkTel;
    /**
     * 是否默认报关行
     */
    private Boolean isDefault;
    
    
    
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	/**
	 * 报关行地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 报关行地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 报关行联系人
	 */
	public String getLinkName() {
		return linkName;
	}
	/**
	 * 报关行联系人
	 */
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	/**
	 * 报关行联系电话
	 */
	public String getLinkTel() {
		return linkTel;
	}
	/**
	 * 报关行联系电话
	 */
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}
}
