package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 结转料件差额(编码级)
 * @author chl
 *
 */
public class VFTransferDiffHsImg extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 序号 
     */
    private Integer serialNumber;
    /**
	 * 备案资料库备案序号
	 */
	private Integer seqNum;
	/**
     * 手册号
     */
    private String emsNo; 
	/**
	 * 归并序号
	 */
	private Integer mergerNo;
	/**
	 * 料件名称
	 */
	private String hsName;
	/**
	 * 料件规格
	 */
	private String hsSpec;
	/**
	 * 料件单位
	 */
	private String hsUnit;
	
	/**
	 * 已收货未转厂数
	 */
	private Double unTransHadReceiveNum;
	
	/**
	 * 已转厂未收货数
	 */
	private Double unReceiveHadTransNum;
	
	/**
	 * 报关单数量
	 */
	private Double customsBillNum;
	
	/**
	 * 收货数量
	 */
	private Double receiveBillNum;
	
	/**
	 * 批次号
	 */
	private VFSection section;

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
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public String getEmsNo() {
		return emsNo;
	}
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	public Double getUnTransHadReceiveNum() {
		return unTransHadReceiveNum;
	}
	public void setUnTransHadReceiveNum(Double unTransHadReceiveNum) {
		this.unTransHadReceiveNum = unTransHadReceiveNum;
	}
	public Double getUnReceiveHadTransNum() {
		return unReceiveHadTransNum;
	}
	public void setUnReceiveHadTransNum(Double unReceiveHadTransNum) {
		this.unReceiveHadTransNum = unReceiveHadTransNum;
	}
	public Double getCustomsBillNum() {
		return customsBillNum;
	}
	public void setCustomsBillNum(Double customsBillNum) {
		this.customsBillNum = customsBillNum;
	}
	public Double getReceiveBillNum() {
		return receiveBillNum;
	}
	public void setReceiveBillNum(Double receiveBillNum) {
		this.receiveBillNum = receiveBillNum;
	}
	
	public void init(){
		unTransHadReceiveNum = 0d;
		unReceiveHadTransNum = 0d;
		customsBillNum = 0d;
		receiveBillNum = 0d;
	}
}
