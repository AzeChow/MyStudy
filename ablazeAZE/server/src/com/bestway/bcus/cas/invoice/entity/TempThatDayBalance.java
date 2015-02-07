
package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;

import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 当日结存表 单据明细临时类
 */
public class TempThatDayBalance implements Serializable {
	private static final long serialVersionUID = 2L;

	/**
	 * 工厂料号
	 */
	private String ptPart;

	/**
	 * 工厂名称
	 */
	private String ptName;

	/**
	 * 工厂规格
	 */
	private String ptSpec;

	/**
	 * 工厂单位
	 */
	private CalUnit ptUnit;

	/**
	 * 工厂数量
	 */
	private Double ptAmount;
	
	

	/**
	 * 海关商品编码
	 */
	private Complex complex;

	/**
	 * 海关商品名称
	 */
	private String hsName;

	/**
	 * 海关商品规格型号
	 */
	private String hsSpec;

	/**
	 * 折算报关数量
	 */
	private Double hsAmount;
	
	
	/**
	 * 折算报关单位比率
	 */

	private Double unitConvert = null;

	/**
	 * 海关单位
	 */
	private Unit hsUnit;
	
	/**
	 * 结存表类型
	 */
	private String materialType;

	/**
	 * 仓库
	 */

	private WareSet wareSet;
	
	/**
	 * 备注
	 * (用来区分  物料类型  )
	 */
	private String note;
	
	
	/**
	 * 物料类型
	 */
	private String materialType1;
	
	/**
	 * 版本号
	 */
	private Integer version = 0;
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getPtPart() {
		return ptPart;
	}

	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
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

	public CalUnit getPtUnit() {
		return ptUnit;
	}

	public void setPtUnit(CalUnit ptUnit) {
		this.ptUnit = ptUnit;
	}

	public Double getPtAmount() {
		return ptAmount;
	}

	public void setPtAmount(Double ptAmount) {
		this.ptAmount = ptAmount;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public String getHsName() {
		return hsName;
	}

	public void setHsName(String hsName) {
		this.hsName = hsName;
	}

	public String getHsSpec() {
		return hsSpec;
	}

	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}

	public Double getHsAmount() {
		return hsAmount;
	}

	public void setHsAmount(Double hsAmount) {
		this.hsAmount = hsAmount;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	public Unit getHsUnit() {
		return hsUnit;
	}

	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public WareSet getWareSet() {
		return wareSet;
	}

	public void setWareSet(WareSet wareSet) {
		this.wareSet = wareSet;
	}

	public String getMaterialType1() {
		return materialType1;
	}

	public void setMaterialType1(String materialType1) {
		this.materialType1 = materialType1;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	

}