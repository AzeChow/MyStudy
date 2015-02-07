package com.bestway.bcus.cas.invoice.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 在产品当日结存（临时类，与CheckBalance一样）
 * @author wss
 *
 */
public class TempCheckBalance extends BaseScmEntity {

	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = 2278467884332918064L;
	
	/**
	 * 料件/成品标记
	 */
	private String ljCpMark;//料件0， 成品 1  ,半成品2  （wss:物料标记只有这三种情况）
	/**
	 * 库存类别
	 * 
	 * 料件(只有料件)0;
	 * 成品(只有成品)1;
	 * 半成品（只有半成品)2;
	 * 在产品(可有料件、成品、半成品)3;
	 * 外发加工(可有料件、成品、半成品)4;
	 * 残次品(可有料件、成品、半成品)5;
	 */
	private String kcType; 

	/**
	 * 盘点时间
	 */
	private Date checkDate;
	/**
	 * 料号
	 */
	private String ptNo;
	/**
	 * 工厂名称
	 * wss:2010.05.06新增
	 */
	private String ptName;
	/**
	 * 工厂规格
	 * wss:2010.05.06新增
	 */
	private String ptSpec;
	
	/**
	 * 工厂单位
	 */
	private String ptUnitName;
	/**
	 * 仓库
	 */
	private WareSet wareSet;
	/**
	 * 盘点库存数
	 */
	private Double checkAmount;
	/**
	 * 报关编码
	 * wss:2010.05.06新增
	 */
	private Complex complex;
	/**
	 * 报关名称
	 */
	private String name = "";
	/**
	 * 报关规格
	 */
	private String spec = "";
	/**
	 * 报关单位
	 */
	private String unitName = "";
	/**
	 * 折算报关比例
	 */
	private Double unitConvert;
	/**
	 * 折算报关数量
	 */
	private Double hsAmount;
	
	/**
	 * 单耗版本
	 */
	private String bomVersion;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 错误原因
	 */
	private String errorReason;
	/**
	 * 
	 */
	private int[] invalidationColNo = null;	
	/**
	 * @return the errorReason
	 */
	public String getErrorReason() {
		return errorReason;
	}
	/**
	 * @param errorReason the errorReason to set
	 */
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	/**
	 * @return the checkDate
	 */
	public Date getCheckDate() {
		return checkDate;
	}
	/**
	 * @param checkDate the checkDate to set
	 */
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	/**
	 * @return the ptNo
	 */
	public String getPtNo() {
		return ptNo;
	}
	/**
	 * @param ptNo the ptNo to set
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}
	/**
	 * @return the wareSet
	 */
	public WareSet getWareSet() {
		return wareSet;
	}
	/**
	 * @param wareSet the wareSet to set
	 */
	public void setWareSet(WareSet wareSet) {
		this.wareSet = wareSet;
	}
	/**
	 * @return the checkAmount
	 */
	public Double getCheckAmount() {
		return checkAmount;
	}
	/**
	 * @param checkAmount the checkAmount to set
	 */
	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the spec
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * @param spec the spec to set
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	/**
	 * @return the unitName
	 */
	public String getUnitName() {
		return unitName;
	}
	/**
	 * @param unitName the unitName to set
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	/**
	 * @return the unitConvert
	 */
	public Double getUnitConvert() {
		return unitConvert;
	}
	/**
	 * @param unitConvert the unitConvert to set
	 */
	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the hsAmount
	 */
	public Double getHsAmount() {
		return hsAmount;
	}
	/**
	 * @param hsAmount the hsAmount to set
	 */
	public void setHsAmount(Double hsAmount) {
		this.hsAmount = hsAmount;
	}
	/**
	 * @return the invalidationColNo
	 */
	public int[] getInvalidationColNo() {
		return invalidationColNo;
	}
	/**
	 * @param invalidationColNo the invalidationColNo to set
	 */
	public void setInvalidationColNo(int[] invalidationColNo) {
		this.invalidationColNo = invalidationColNo;
	}
	/**
	 * @return the ptUnitName
	 */
	public String getPtUnitName() {
		return ptUnitName;
	}
	/**
	 * @param ptUnitName the ptUnitName to set
	 */
	public void setPtUnitName(String ptUnitName) {
		this.ptUnitName = ptUnitName;
	}
	public String getLjCpMark() {
		return ljCpMark;
	}
	public void setLjCpMark(String ljCpMark) {
		this.ljCpMark = ljCpMark;
	}
	public String getKcType() {
		return kcType;
	}
	public void setKcType(String kcType) {
		this.kcType = kcType;
	}
	public String getBomVersion() {
		return bomVersion;
	}
	public void setBomVersion(String bomVersion) {
		this.bomVersion = bomVersion;
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
	public Complex getComplex() {
		return complex;
	}
	public void setComplex(Complex complex) {
		this.complex = complex;
	}
	

}
