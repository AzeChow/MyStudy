package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 结转料件差额
 * @author chl
 *
 */
public class VFTransferDiffImg extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 序号 
     */
    private Integer serialNumber; 
	/**
	 * 料件料号
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
	 * 已收货未转厂
	 */
	private Double unTransferNum;
	/**
	 * 已转厂未收货
	 */
	private Double unReceiveNum ;
	/**
	 * 归并序号
	 */
	private Integer mergerNo;
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
	 * 折算已收货未转厂数
	 */
	private Double convertUnTransHadReceiveNum;
	
	/**
	 * 折算已转厂未收货数
	 */
	private Double convertUnReceiveHadTransNum;
	/**
	 * 批次号
	 */
	private VFSection section;
	/**
	 * 折算系数
	 */
	private Double unitConvert;
	
	
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
	public Double getUnReceiveNum() {
		return unReceiveNum;
	}
	public void setUnReceiveNum(Double unReceiveNum) {
		this.unReceiveNum = unReceiveNum;
	}

	public Integer getMergerNo() {
		return mergerNo;
	}
	public void setMergerNo(Integer mergerNo) {
		this.mergerNo = mergerNo;
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
	public String getHsUnit() {
		return hsUnit;
	}
	public void setHsUnit(String hsUnit) {
		this.hsUnit = hsUnit;
	}

	public Double getConvertUnTransHadReceiveNum() {
		return convertUnTransHadReceiveNum;
	}
	public void setConvertUnTransHadReceiveNum(Double convertUnTransHadReceiveNum) {
		this.convertUnTransHadReceiveNum = convertUnTransHadReceiveNum;
	}
	public Double getConvertUnReceiveHadTransNum() {
		return convertUnReceiveHadTransNum;
	}
	public void setConvertUnReceiveHadTransNum(Double convertUnReceiveHadTransNum) {
		this.convertUnReceiveHadTransNum = convertUnReceiveHadTransNum;
	}
	public VFSection getSection() {
		return section;
	}
	public void setSection(VFSection section) {
		this.section = section;
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
	public Double getUnitConvert() {
		return unitConvert;
	}
	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}
	
	
}
