package com.bestway.owp.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放工厂BOM管理版本资料
 * 
 * @author 2010-08-03 hcl
 *
 */
public class OwpBomVersion extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 成品/半成品
	 */
	private OwpBomMaster  parent;

	/**
	 * 版本号
	 */
	private Integer version;
	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 获取成品/半成品货号
	 * 
	 * @return parentNo 
	 */
	public OwpBomMaster getParent() {
		return parent;
	}

	/**
	 * 设置成品/半成品货号
	 * 
	 * @param parentNo 
	 */
	public void setParent(OwpBomMaster parent) {
		this.parent = parent;
	}

	/**
	 * 获取版本号
	 * 
	 * @return version 版本号
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * 设置版本号
	 * 
	 * @param version 版本号
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
