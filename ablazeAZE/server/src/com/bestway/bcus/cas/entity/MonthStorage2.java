package com.bestway.bcus.cas.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 月度库存
 */
public class MonthStorage2 extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();

	/**
	 * 物料类别
	 */
	private String				materielType		= null;
	/**
	 * 月份值
	 */
	private Integer				month				= null;	
	/**
	 * 商品名称
	 */
	private String				ptName				= null;
	/**
	 * 规格型号
	 */
	private String				ptSpec				= null;	
	/**
	 * 报关单位
	 */
	private String				unitName				= null;
	/**
	 * 结存数量
	 */
	private Double				ptAmount			= 0.0;
	/**
	 * 结存净重
	 */
	private Double				netWeight			= 0.0;

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public Double getPtAmount() {
		return ptAmount;
	}

	public void setPtAmount(Double ptAmount) {
		this.ptAmount = ptAmount;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String ptPart) {
		this.unitName = ptPart;
	}

	public String getMaterielType() {
		return materielType;
	}

	public void setMaterielType(String materielType) {
		this.materielType = materielType;
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
