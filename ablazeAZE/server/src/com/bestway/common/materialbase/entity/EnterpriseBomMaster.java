package com.bestway.common.materialbase.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放工厂BOM管理成品资料
 * 
 * @author adminstrator
 */
public class EnterpriseBomMaster extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 成品料号
	 */
	private String ptNo;

	/**
	 * 成品名称
	 */
	private String ptName;

	/**
	 * 成品规格
	 */
	private String ptSpec;

	/**
	 * 工厂单位
	 */
	private String calUnitName;

	/**
	 * 获取成品名称
	 * 
	 * @return ptName 成品名称
	 */
	public String getPtName() {
		return ptName;
	}

	/**
	 * 设置成品名称
	 * 
	 * @param ptName
	 *            成品名称
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	/**
	 * 获取成品料号
	 * 
	 * @return ptNo 成品料号
	 */
	public String getPtNo() {
		return ptNo;
	}

	/**
	 * 设置成品料号
	 * 
	 * @param ptNo
	 *            成品料号
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	/**
	 * 获取成品规格
	 * 
	 * @return ptSpec成品规格
	 */
	public String getPtSpec() {
		return ptSpec;
	}

	/**
	 * 设置成品规格
	 * 
	 * @param ptSpec
	 *            成品规格
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	/**
	 * 获取工厂单位
	 * 
	 * @return calUnitName 工厂单位
	 */
	public String getCalUnitName() {
		return calUnitName;
	}

	/**
	 * 设置工厂单位
	 * 
	 * @param calUnitName
	 *            工厂单位
	 */
	public void setCalUnitName(String calUnitName) {
		this.calUnitName = calUnitName;
	}
}
