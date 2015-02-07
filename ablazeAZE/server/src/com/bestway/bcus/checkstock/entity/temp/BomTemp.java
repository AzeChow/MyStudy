package com.bestway.bcus.checkstock.entity.temp;

public class BomTemp implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer exgSeqNum;
	private String emsNo;
	private Integer seqNum;
	private String name;
	private String spec;
	private String unitName;
	private Double unitWear;
	private Double wear;
	private int version;
	/**
	 * @return the emsNo
	 */
	public String getEmsNo() {
		return emsNo;
	}
	/**
	 * @param emsNo the emsNo to set
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
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
	 * @return the unitWear
	 */
	public Double getUnitWear() {
		return unitWear;
	}
	/**
	 * @param unitWear the unitWear to set
	 */
	public void setUnitWear(Double unitWear) {
		this.unitWear = unitWear;
	}
	/**
	 * @return the wear
	 */
	public Double getWear() {
		return wear;
	}
	/**
	 * @param wear the wear to set
	 */
	public void setWear(Double wear) {
		this.wear = wear;
	}
	
	/**
	 * @return the exgSeqNum
	 */
	public Integer getExgSeqNum() {
		return exgSeqNum;
	}
	/**
	 * @param exgSeqNum the exgSeqNum to set
	 */
	public void setExgSeqNum(Integer exgSeqNum) {
		this.exgSeqNum = exgSeqNum;
	}
	public BomTemp(Integer exgSeqNum, String emsNo, Integer seqNum, String name, String spec,
			String unitName, Double unitWear, Double wear , int version) {
		super();
		this.exgSeqNum = exgSeqNum;
		this.emsNo = emsNo;
		this.seqNum = seqNum;
		this.name = name;
		this.spec = spec;
		this.unitName = unitName;
		this.unitWear = unitWear;
		this.wear = wear;
		this.version = version;
	}
	public BomTemp() {
		super();
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
}
