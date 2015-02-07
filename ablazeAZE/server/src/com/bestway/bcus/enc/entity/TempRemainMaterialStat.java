/*
 * Created on 2005-4-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.io.Serializable;

/**
 * @author Administrator
 */
public class TempRemainMaterialStat implements Serializable {

	/**
	 * 料件号码
	 */
	private String materialCode;

	/**
	 * 料件名称
	 */
	private String materialName;

	/**
	 * 料件规格
	 */
	private String materialSpec;

	/**
	 * 料件单位
	 */
	private String unit;

	/**
	 * 料件耗用总量
	 */
	private Double totalWasteAmount;

	/**
	 * 料件边角料耗用总量
	 */
	private Double remainMaterialTotalAmount;

	/**
	 * 获得料件号码
	 * @return 料件号码
	 */
	public String getMaterialCode() {
		return materialCode;
	}

	/**
	 * 设置料件号码
	 * @param materialCode 料件号码
	 */
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	/**
	 * 获得料件名称
	 * @return 料件名称
	 */
	public String getMaterialName() {
		return materialName;
	}

	/**
	 * 设计料件名称
	 * @param materialName 料件名称
	 */
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	/**
	 * 获得料件规格
	 * @return 料件规格
	 */
	public String getMaterialSpec() {
		return materialSpec;
	}

	/**
	 * 设置料件规格
	 * @param materialSpec 料件规格
	 */
	public void setMaterialSpec(String materialSpec) {
		this.materialSpec = materialSpec;
	}

	/**
	 * 获得料件边角料耗用总量
	 * @return 料件边角料耗用总量
	 */
	public Double getRemainMaterialTotalAmount() {
		return remainMaterialTotalAmount;
	}

	/**
	 * 设置料件边角料耗用总量
	 * @param remainMaterialTotalAmount 料件边角料耗用总量
	 */
	public void setRemainMaterialTotalAmount(Double remainMaterialTotalAmount) {
		this.remainMaterialTotalAmount = remainMaterialTotalAmount;
	}

	/**
	 * 获得料件耗用总量
	 * @return 料件耗用总量
	 */
	public Double getTotalWasteAmount() {
		return totalWasteAmount;
	}

	/**
	 * 设置料件耗用总量
	 * @param totalWasteAmount 料件耗用总量
	 */
	public void setTotalWasteAmount(Double totalWasteAmount) {
		this.totalWasteAmount = totalWasteAmount;
	}

	/**
	 * 获得料件单位
	 * @return 料件单位
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * 设置料件单位
	 * @param unit 料件单位
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 判断两个对象是否相等
	 */
	public boolean equals(Object arg0) {
		if (arg0 == null) {
			return false;
		}
		if (!arg0.getClass().equals(this.getClass())) {
			return false;
		}
		return this.getMaterialCode().equals(
				((TempRemainMaterialStat) arg0).getMaterialCode());
	}
}
