/*
 * Created on 2004-10-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 *  存放报关常用工厂BOM版本资料
 * 
 * @author adminstrator
 */
public class MaterialBomVersion extends BaseScmEntity implements Comparable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 成品资料
	 */
	private MaterialBomMaster master; 

	/**
	 * 版本号
	 */
	private Integer version = 0;

	/**
	 * 开始有效期
	 */
	private Date beginDate; 

	/**
	 * 结束有效期
	 */
	private Date endDate; 

	/**
	 * 料号
	 */
	private String ptNo; //
	
	/**
	 * 实际单重
	 */
	private Double factUnitWeight;
	
	/**
	 * 计算单重
	 */
	private Double countUnitWeight;
	
	/**
	 * 备注
	 */
	private String notes;
	
	/**
	 * 获取备注
	 * @return
	 */
	public String getNotes() {
		return notes;
	}


	/**
	 * 设置成品资料
	 * 
	 * @param master 成品资料
	 */
	public void setMaster(MaterialBomMaster master) {
		this.master = master;
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

	/**
	 * 重写compareTo方法，当两个MaterialBomVersion的Version相等时为同一个MaterialBomVersion
	 * 
	 * @param arg0 MaterialBomVersion类型
	 * @return int
	 */
	public int compareTo(Object arg0) {
		return this.getVersion().compareTo(
				((MaterialBomVersion) arg0).getVersion());
	}

		
	/**
	 * 获取开始有效期
	 * 
	 * @return beginDate 开始有效期
	 */ 
	public Date getBeginDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(beginDate));
		}
		return beginDate;
	}

	/**
	 * 设置开始有效期
	 * 
	 * @param beginDate 开始有效期
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (endDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(endDate));
		}
		return endDate;
	}

	/**
	 * 设置结束有效期
	 * 
	 * @param endDate 结束有效期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

		
	/**
	 * 获取料号，导数据备用
	 * 
	 * @return ptNo 料号，导数据备用
	 */
	public String getPtNo() {
		return ptNo;
	}

	/**
	 * 设置料号，导数据备用
	 * 
	 * @param ptNo 料号，导数据备用
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}
	
	/**
	 * 设置备注
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * 获取成品资料
	 * 
	 * @return master 成品资料
	 */
	public MaterialBomMaster getMaster() {
		return master;
	}


	public Double getFactUnitWeight() {
		return factUnitWeight;
	}


	public void setFactUnitWeight(Double factUnitWeight) {
		this.factUnitWeight = factUnitWeight;
	}


	public Double getCountUnitWeight() {
		return countUnitWeight;
	}


	public void setCountUnitWeight(Double countUnitWeight) {
		this.countUnitWeight = countUnitWeight;
	}
	
}
