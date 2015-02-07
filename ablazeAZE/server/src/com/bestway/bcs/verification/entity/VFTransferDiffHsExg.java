package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 结转成品差额(编码级)
 * @author chl
 *
 */
public class VFTransferDiffHsExg extends BaseScmEntity {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 序号 
     */
    private Integer serialNumber;
    /**
	 * 成品备案序号
	 */
	private Integer seqNum;
	/**
     * 手册号
     */
    private String emsNo; 
	/**
	 * 报关品名
	 */
	private String hsName;
	/**
	 * 报关规格
	 */
	private String hsSpec;
	/**
	 * 计量单位
	 */
	private String hsUnit;
	
	/**
	 * 已发货未转厂
	 */
	private Double unTransHadSendNum;
	
	/**
	 * 已转厂未发货
	 */
	private Double unSendHadTransNum;
	
	/**
	 * 报关单数量
	 */
	private Double customsBillNum;
	
	/**
	 * 发货数量
	 */
	private Double sendBillNum;
	
	/**
	 * 批次号
	 */
	private VFSection section;

	public Integer getSerialNumber() {
		return serialNumber;
	}

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

	public Double getUnTransHadSendNum() {
		return unTransHadSendNum;
	}

	public void setUnTransHadSendNum(Double unTransHadSendNum) {
		this.unTransHadSendNum = unTransHadSendNum;
	}

	public Double getUnSendHadTransNum() {
		return unSendHadTransNum;
	}

	public void setUnSendHadTransNum(Double unSendHadTransNum) {
		this.unSendHadTransNum = unSendHadTransNum;
	}

	public Double getCustomsBillNum() {
		return customsBillNum;
	}

	public void setCustomsBillNum(Double customsBillNum) {
		this.customsBillNum = customsBillNum;
	}

	public Double getSendBillNum() {
		return sendBillNum;
	}

	public void setSendBillNum(Double sendBillNum) {
		this.sendBillNum = sendBillNum;
	}

	public VFSection getSection() {
		return section;
	}

	public void setSection(VFSection section) {
		this.section = section;
	}
	
	
	
}
