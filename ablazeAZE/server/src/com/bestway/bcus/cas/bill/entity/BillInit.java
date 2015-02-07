/*
 * Created on 2004-9-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.bill.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * @author
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class BillInit extends BaseScmEntity implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 记帐年
	 */
	private String year = "";

	/**
	 * 单据类型代码
	 */
	private String typeCode = "";

	/**
	 * 工厂数量
	 */
	private Double ptAmount = 0.0;

	/**
	 * 料号
	 */
	private String ptNo = "";

	// wss2010.08.26增加以下3项

	/**
	 * 报关编码
	 */
	private String hsNo = "";

	/**
	 * 报关名称
	 */
	private String hsName = "";

	/**
	 * 报关规格 wss2010.10.26
	 */
	private String hsSpec = "";

	/**
	 * 报关单位 wss2010.10.26
	 */
	private Unit hsUnit;
	/**
	 * 报关数量
	 */
	private Double hsAmount = 0.0;

	/**
	 * 注释
	 */
	private String note = "";

	/**
	 * 仓库代码
	 */
	private String wareSetCode = "";

	/**
	 * 版本号
	 */
	private Integer version = 0;

	/**
	 * 工厂数量
	 * 
	 * @return the ptAmount
	 */
	public Double getPtAmount() {
		return ptAmount;
	}

	/**
	 * 料号
	 * 
	 * @return the ptNo
	 */
	public String getPtNo() {
		return ptNo;
	}

	/**
	 * 仓库代码
	 * 
	 * @return the wareSetCode
	 */
	public String getWareSetCode() {
		return wareSetCode;
	}

	/**
	 * 工厂数量
	 * 
	 * @param ptAmount
	 *            the ptAmount to set
	 */
	public void setPtAmount(Double ptAmount) {
		this.ptAmount = ptAmount;
	}

	/**
	 * 料号
	 * 
	 * @param ptNo
	 *            the ptNo to set
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	/**
	 * 仓库代码
	 * 
	 * @param wareSetCode
	 *            the wareSetCode to set
	 */
	public void setWareSetCode(String wareSetCode) {
		this.wareSetCode = wareSetCode;
	}

	/**
	 * 记帐年
	 * 
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * 单据类型代码
	 * 
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * 单据类型代码
	 * 
	 * @param year
	 *            the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * 单据类型代码
	 * 
	 * @param typeCode
	 *            the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * 版本号
	 * 
	 * @return
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * 版本号
	 * 
	 * @param version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * 报关编码
	 * 
	 * @return
	 */
	public String getHsNo() {
		return hsNo;
	}

	/**
	 * 报关编码
	 * 
	 * @param hsNo
	 */
	public void setHsNo(String hsNo) {
		this.hsNo = hsNo;
	}

	/**
	 * 报关名称
	 * 
	 * @return
	 */
	public String getHsName() {
		return hsName;
	}

	/**
	 * 报关名称
	 * 
	 * @param hsName
	 */
	public void setHsName(String hsName) {
		this.hsName = hsName;
	}

	/**
	 * 报关规格
	 * 
	 * @return
	 */
	public String getHsSpec() {
		return hsSpec;
	}

	/**
	 * 报关规格
	 * 
	 * @param hsSpec
	 */
	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}

	/**
	 * 注释
	 * 
	 * @return
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 注释
	 * 
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 报关数量 wss2010.10.26
	 * 
	 * @return
	 */
	public Double getHsAmount() {
		return hsAmount;
	}

	/**
	 * 报关数量 wss2010.10.26
	 * 
	 * @param hsAmount
	 */
	public void setHsAmount(Double hsAmount) {
		this.hsAmount = hsAmount;
	}

	/**
	 * 报关单位 wss2010.10.26
	 * 
	 * @return
	 */
	public Unit getHsUnit() {
		return hsUnit;
	}

	/**
	 * 报关单位 wss2010.10.26
	 * 
	 * @param hsUnit
	 */
	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}

}