package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 结转成品差额
 * @author chl
 *
 */
public class VFTransferDiffExg extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 序号 
     */
    private Integer serialNumber; 
	/**
	 * 成品料号
	 */
	private String ptNo; 
	/**
	 * 物料名称
	 */
	private String ptName;
	/**
	 * 物料规格
	 */
	private String ptSpec;
	/**
	 * 物料单位
	 */
	private String ptUnit;
	/**
	 * 已送货未转厂
	 */
	private Double unTransferNum;
	/**
	 * 已转厂未送货
	 */
	private Double unSendferNum;
	
	/**
	 * 批次号
	 */
	private VFSection section;
	/**
	 * 成品BOM版本
	 */
	private String bomVersion;
	
	public String getPtNo() {
		return ptNo;
	}
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
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
	public String getPtUnit() {
		return ptUnit;
	}
	public void setPtUnit(String ptUnit) {
		this.ptUnit = ptUnit;
	}
	public Double getUnTransferNum() {
		return unTransferNum;
	}
	public void setUnTransferNum(Double unTransferNum) {
		this.unTransferNum = unTransferNum;
	}
	public Double getUnSendferNum() {
		return unSendferNum;
	}
	public void setUnSendferNum(Double unSendferNum) {
		this.unSendferNum = unSendferNum;
	}
	
	public VFSection getSection() {
		return section;
	}
	public void setSection(VFSection section) {
		this.section = section;
	}
	public String getBomVersion() {
		return bomVersion;
	}
	public void setBomVersion(String bomVersion) {
		this.bomVersion = bomVersion;
	}
	/**
	 * @return the serialNumber
	 */
	public Integer getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	
}
