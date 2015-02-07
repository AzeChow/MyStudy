package com.bestway.common.materialbase.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 临时实体类，存放临时的BOM参数
 * 
 * @author administrator
 *
 */
public class TempBomParam implements Serializable {


	/**
	 * 版本号
	 */
	private String versionNo;  

	/**
	 * 单耗
	 */
	private Double unitWare = 1.0; 

	/**
	 * 损耗
	 */
	private Double ware = 1.0;  

	/**
	 * 单项用量
	 */
	private Double unitDosage = 1.0;  

	

	/**
	 * 生效日期
	 */
	private Date effectDate; 

	/**
	 * 失效日期
	 */
	private Date endDate; 

	
	/**
	 * 获取生效日期
	 * 
	 * @return effectDate 生效日期
	 */
	public Date getEffectDate() {
		return effectDate;
	}

	/**
	 * 获取生效日期
	 * 
	 * @param effectDate 生效日期
	 */
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	/**
	 * 获取失效日期
	 * 
	 * @return endDate 失效日期
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 获取失效日期
	 * 
	 * @param  endDate 失效日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取版本号
	 * 
	 * @return versionNo 版本号
	 */
	public String getVersionNo() {
		return versionNo;
	}

	/**
	 * 获取版本号
	 * 
	 * @param  versionNo 版本号
	 */
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * 获取单耗
	 * 
	 * @return unitWare 单耗
	 */
	public Double getUnitWare() {
		return unitWare;
	}

	/**
	 * 获取单耗
	 * 
	 * @param  unitWare 单耗
	 */
	public void setUnitWare(Double unitWare) {
		this.unitWare = unitWare;
	}

	/**
	 * 获取损耗
	 * 
	 * @return ware 损耗
	 */
	public Double getWare() {
		return ware;
	}

	/**
	 * 获取损耗
	 * 
	 * @param  ware 损耗
	 */
	public void setWare(Double ware) {
		this.ware = ware;
	}

	/**
	 * 获取单项用量
	 * 
	 * @return unitDosage 单项用量
	 */
	public Double getUnitDosage() {
		return unitDosage;
	}

	/**
	 * 获取单项用量
	 * 
	 * @param  unitDosage 单项用量
	 */
	public void setUnitDosage(Double unitDosage) {
		this.unitDosage = unitDosage;
	}

	/**
	 * 获取EffectEndDate
	 * 
	 * @return String
	 */
	public String getEffectEndDate() {
		return (effectDate == null ? "[]" : DateFormatUtils.format(effectDate,
				"yyyy-MM-dd"))
				+ (endDate == null ? "[]" : DateFormatUtils.format(endDate,
						"yyyy-MM-dd"));
	}
}
