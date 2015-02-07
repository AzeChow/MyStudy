package com.bestway.common.materialbase.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放工厂BOM管理版本资料
 * 
 * @author adminstrator
 * 
 */
public class EnterpriseBomVersion extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 成品料号
	 */
	private String parentNo;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 开始有效期
	 */
	private Date beginDate;

	/**
	 * 结束有效期
	 */
	private Date endDate;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 净重
	 */
	private Double netWeight;
	
	/**
	 * 获取成品料号
	 * 
	 * @return parentNo 成品料号
	 */
	public String getParentNo() {
		return parentNo;
	}

	/**
	 * 设置成品料号
	 * 
	 * @param parentNo
	 *            成品料号
	 */
	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	/**
	 * 获取开始有效期
	 * 
	 * @return beginDate 开始有效期
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * 设置开始有效期
	 * 
	 * @param beginDate
	 *            开始有效期
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 获取结束有效期
	 * 
	 * @return endDate 结束有效期
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置结束有效期
	 * 
	 * @param endDate
	 *            结束有效期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	 * @param version
	 *            版本号
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * 获取备注
	 * 
	 * 
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * 设置备注
	 * 
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}
}
