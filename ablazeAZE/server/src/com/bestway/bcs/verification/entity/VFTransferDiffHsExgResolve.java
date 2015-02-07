package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 结转成品分解料件表(编码级)
 * @author chl
 *
 */
public class VFTransferDiffHsExgResolve extends BaseScmEntity {

	/**
	 * 成品
	 */
	private VFTransferDiffHsExg hsExg;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 序号 
     */
    private Integer serialNumber; 
    /**
     * 料件备案序号
     */
    private Integer seqNum;
	 
	/**
	 * 物料名称
	 */
	private String hsName;
	/**
	 * 物料规格
	 */
	private String hsSpec;
	/**
	 * 物料单位
	 */
	private String hsUnit;
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
	 * 批次号
	 */
	private VFSection section;


	public VFTransferDiffHsExg getHsExg() {
		return hsExg;
	}

	public void setHsExg(VFTransferDiffHsExg hsExg) {
		this.hsExg = hsExg;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
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

	public Integer getMergerNo() {
		return mergerNo;
	}

	public void setMergerNo(Integer mergerNo) {
		this.mergerNo = mergerNo;
	}

	public VFSection getSection() {
		return section;
	}

	public void setSection(VFSection section) {
		this.section = section;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	
}
