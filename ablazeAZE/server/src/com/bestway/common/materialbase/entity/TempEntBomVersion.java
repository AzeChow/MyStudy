package com.bestway.common.materialbase.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 临时实体类，存放临时的工厂BOM管理版本资料
 * 
 * @author administrator
 * 
 */
public class TempEntBomVersion implements Serializable {

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
	 * 获取备注信息
	 * 
	 * @return 备注信息
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * 设置备注信息
	 * 
	 * @param notes
	 *            备注信息
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * 复写equals
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TempEntBomVersion)) {
			return false;
		}
		TempEntBomVersion temp = (TempEntBomVersion) obj;
		return this.toString().equals(temp.toString());
	}

	/**
	 * 复写hashCode
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(toString()).toHashCode();
	}

	/**
	 * 把version、beginDate、endDate输出
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String v = (version == null ? "" : version.toString());
		String b = (beginDate == null ? "" : DateFormatUtils.format(beginDate,
				"yyyy-MM-dd"));
		String e = (endDate == null ? "" : DateFormatUtils.format(endDate,
				"yyyy-MM-dd"));
		return v + b + e;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}
	
	
}
