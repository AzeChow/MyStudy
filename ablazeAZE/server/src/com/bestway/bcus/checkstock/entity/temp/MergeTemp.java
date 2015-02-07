package com.bestway.bcus.checkstock.entity.temp;

public class MergeTemp implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 料件货号
	 */
	private String ptNo;     
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
	private String ptUnit;
	/**
	 * 料件序号
	 */
	private Integer seqNum;    
	/**
	 * 报关商品名称
	 */
	private String hsName;
	/**
	 * 报关商品规格
	 */
	private String hsSpec;
	/**
	 * 报关单位
	 */
	private String hsUnit;     
	/**
	 * 单位折算系数
	 */
	private Double unitConvert;
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
	 * @return the ptName
	 */
	public String getPtName() {
		return ptName;
	}
	/**
	 * @param ptName the ptName to set
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	/**
	 * @return the ptSpec
	 */
	public String getPtSpec() {
		return ptSpec;
	}
	/**
	 * @param ptSpec the ptSpec to set
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}
	/**
	 * @return the ptUnit
	 */
	public String getPtUnit() {
		return ptUnit;
	}
	/**
	 * @param ptUnit the ptUnit to set
	 */
	public void setPtUnit(String ptUnit) {
		this.ptUnit = ptUnit;
	}
	/**
	 * @return the seqNum
	 */
	public Integer getSeqNum() {
		return seqNum;
	}
	/**
	 * @param seqNum the seqNum to set
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	/**
	 * @return the hsName
	 */
	public String getHsName() {
		return hsName;
	}
	/**
	 * @param hsName the hsName to set
	 */
	public void setHsName(String hsName) {
		this.hsName = hsName;
	}
	/**
	 * @return the hsSpec
	 */
	public String getHsSpec() {
		return hsSpec;
	}
	/**
	 * @param hsSpec the hsSpec to set
	 */
	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}
	/**
	 * @return the hsUnit
	 */
	public String getHsUnit() {
		return hsUnit;
	}
	/**
	 * @param hsUnit the hsUnit to set
	 */
	public void setHsUnit(String hsUnit) {
		this.hsUnit = hsUnit;
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
	public MergeTemp(String ptNo, String ptName, String ptSpec, String ptUnit,
			Integer seqNum, String hsName, String hsSpec, String hsUnit,
			Double unitConvert) {
		super();
		this.ptNo = ptNo;
		this.ptName = ptName;
		this.ptSpec = ptSpec;
		this.ptUnit = ptUnit;
		this.seqNum = seqNum;
		this.hsName = hsName;
		this.hsSpec = hsSpec;
		this.hsUnit = hsUnit;
		this.unitConvert = unitConvert;
	}
	public MergeTemp() {
		super();
	}
	public MergeTemp(String ptNo, String ptName, String ptSpec, String ptUnit) {
		super();
		this.ptNo = ptNo;
		this.ptName = ptName;
		this.ptSpec = ptSpec;
		this.ptUnit = ptUnit;
	}
	public MergeTemp(String ptNo, Integer seqNum, String hsName, String hsSpec,
			String hsUnit, Double unitConvert) {
		super();
		this.ptNo = ptNo;
		this.seqNum = seqNum;
		this.hsName = hsName;
		this.hsSpec = hsSpec;
		this.hsUnit = hsUnit;
		this.unitConvert = unitConvert;
	}
}
