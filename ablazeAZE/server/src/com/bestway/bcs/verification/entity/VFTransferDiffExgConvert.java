package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 结转成品差额折料转换报关数据
 * @author chl
 *
 */
public class VFTransferDiffExgConvert extends BaseScmEntity {

	/**
	 * 成品
	 */
	private VFTransferDiffExg exg;

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
	 * 已送货未转厂
	 */
	private Double unTransferNum;
	/**
	 * 已转厂未送货
	 */
	private Double unSendferNum;
	/**
	 * 归并序号
	 */
	private Integer mergerNo;
	
	/**
	 * 单耗
	 */
	private Double unitWaste;  
	
	/**
	 * 损耗
	 */
	private Double waste;  
	
	/**
	 * 单项用量
	 */
	private Double unitUsed;
	
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
	 * 折算已送货未转厂数
	 */
	private Double convertUnTransHadSendNum;
	
	/**
	 * 折算已转厂未送货数
	 */
	private Double convertUnSendHadTransNum;
	/**
	 * 批次号
	 */
	private VFSection section;
	/**
	 * 折算系数
	 */
	private Double unitConvert;
		
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
	
	public String getPtNo() {
		return ptNo;
	}
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
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
	
	public Double getConvertUnTransHadSendNum() {
		return convertUnTransHadSendNum;
	}
	public void setConvertUnTransHadSendNum(Double convertUnTransHadSendNum) {
		this.convertUnTransHadSendNum = convertUnTransHadSendNum;
	}
	public Double getConvertUnSendHadTransNum() {
		return convertUnSendHadTransNum;
	}
	public void setConvertUnSendHadTransNum(Double convertUnSendHadTransNum) {
		this.convertUnSendHadTransNum = convertUnSendHadTransNum;
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
	public VFTransferDiffExg getExg() {
		return exg;
	}
	public void setExg(VFTransferDiffExg exg) {
		this.exg = exg;
	}
	public Double getUnitWaste() {
		return unitWaste;
	}
	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}
	public Double getWaste() {
		return waste;
	}
	public void setWaste(Double waste) {
		this.waste = waste;
	}
	public Double getUnitUsed() {
		return unitUsed;
	}
	public void setUnitUsed(Double unitUsed) {
		this.unitUsed = unitUsed;
	}	
}
