package com.bestway.bcus.checkstock.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 结转差额统计汇总表
 * @author chl
 *
 */
public class ECSTransferAnalyse extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 序号 
     */
    private Integer serialNumber; 
    /**
	 * 帐册编号
	 */
	private String emsNo;
	/**
	 * 备案序号
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
	 * @A=折算已送货未转厂数 
	 */
	private Double unTransHadSendNum;
	
	/**
	 * @B=折算已转厂未送货数
	 */
	private Double unSendHadTransNum;
	
	/**
	 * @C=折算已收货未转厂数
	 */
	private Double unTransHadReceiveNum;
	
	/**
	 * @D=折算已转厂未收货数
	 */
	private Double unReceiveHadTransNum;
	
	/**
	 * 差额（计算公式：val = @A+@D-@B-@C）
	 */
	private Double diffAmount;
	/**
	 * 批次号
	 */
	private ECSSection section;
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
	public ECSSection getSection() {
		return section;
	}
	public void setSection(ECSSection section) {
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
	public Double getDiffAmount() {
		return diffAmount;
	}
	public void setDiffAmount(Double diffAmount) {
		this.diffAmount = diffAmount;
	}
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
	
	public void init() {
		unTransHadSendNum = 0.0;
		unSendHadTransNum = 0.0;
		unTransHadReceiveNum = 0.0;
		unReceiveHadTransNum = 0.0;
		diffAmount = 0.0;
	}
	
}
