package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 结转差额统计汇总表
 * @author chl
 *
 */
public class VFTransferDiffCount extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 序号 
     */
    private Integer serialNumber; 
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
	 * @A=折算已送货未转厂数 
	 */
	private Double convertUnTransHadSendNum;
	
	/**
	 * @B=折算已转厂未送货数
	 */
	private Double convertUnSendHadTransNum;
	
	/**
	 * @C=折算已收货未转厂数
	 */
	private Double convertUnTransHadReceiveNum;
	
	/**
	 * @D=折算已转厂未收货数
	 */
	private Double convertUnReceiveHadTransNum;
	
	/**
	 * 差额（计算公式：val = @A+@D-@B-@C）
	 */
	private Double diffAmount;
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
	public Double getDiffAmount() {
		return diffAmount;
	}
	public void setDiffAmount(Double diffAmount) {
		this.diffAmount = diffAmount;
	}	
}
