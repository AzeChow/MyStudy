package com.bestway.owp.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放工厂BOM管理成品资料
 * 
 * @author 2010-08-03hcl
 */
public class OwpBomMaster extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 成品/半成品货号
	 */
	private String ptNo;
	/**
	 * 成品/半成品名称
	 */
	private String ptName;
	/**
	 * 成品/半成品规格
	 */
	private String ptSpec;
	/**
	 * 获取成品/半成品货号
	 * 
	 * @return ptNo 
	 */
	public String getPtNo() {
		return ptNo;
	}

	/**
	 * 设置成品/半成品货号
	 * 
	 * @param ptNo
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

}
