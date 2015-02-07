package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 合同数据分析
 * @author chl
 */
public class VFContractAnalyse extends BaseScmEntity{

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
	 * 海关料件名称
	 */
	private String hsName;
	/**
	 * 海关料件规格
	 */
	private String hsSpec;
	/**
	 * 海关料件单位
	 */
	private String hsUnit;
	/**
	 * 料件进口平均单价
	 */
	private Double unitPrice;
	/**
	 * 进口数量
	 */
	private Double impAmount;
	/**
	 * 出口数量
	 */
	private Double expAmount;
	/**
	 * 合同应剩余量
	 */
	private Double contractLeaveNum;
	/**
	 * 库存数量
	 */
	private Double stockAmount;
	/**
	 * 溢多数量
	 */
	private Double overflowAmount;
	/**
	 * 短少数量
	 */
	private Double deficitAmount;
	/**
	 * 溢多金额
	 */
	private Double overflowPrice;
	/**
	 * 短少金额
	 */
	private Double deficitPrice;
	/**
	 * 手册号
	 */
	private String emsNo;
	
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

	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	/**
	 * @return the impAmount
	 */
	public Double getImpAmount() {
		return impAmount;
	}
	/**
	 * @param impAmount the impAmount to set
	 */
	public void setImpAmount(Double impAmount) {
		this.impAmount = impAmount;
	}
	/**
	 * @return the expAmount
	 */
	public Double getExpAmount() {
		return expAmount;
	}
	/**
	 * @param expAmount the expAmount to set
	 */
	public void setExpAmount(Double expAmount) {
		this.expAmount = expAmount;
	}
	public Double getContractLeaveNum() {
		return contractLeaveNum;
	}
	public void setContractLeaveNum(Double contractLeaveNum) {
		this.contractLeaveNum = contractLeaveNum;
	}
	public Double getStockAmount() {
		return stockAmount;
	}
	public void setStockAmount(Double stockAmount) {
		this.stockAmount = stockAmount;
	}
	public Double getOverflowAmount() {
		return overflowAmount;
	}
	public void setOverflowAmount(Double overflowAmount) {
		this.overflowAmount = overflowAmount;
	}
	public Double getDeficitAmount() {
		return deficitAmount;
	}
	public void setDeficitAmount(Double deficitAmount) {
		this.deficitAmount = deficitAmount;
	}
	public Double getOverflowPrice() {
		return overflowPrice;
	}
	public void setOverflowPrice(Double overflowPrice) {
		this.overflowPrice = overflowPrice;
	}
	public Double getDeficitPrice() {
		return deficitPrice;
	}
	public void setDeficitPrice(Double deficitPrice) {
		this.deficitPrice = deficitPrice;
	}	
	public VFSection getSection() {
		return section;
	}
	public void setSection(VFSection section) {
		this.section = section;
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
	public void init(){
		this.deficitPrice = 0.0;
		this.overflowPrice = 0.0;
		this.deficitAmount = 0.0;
		this.overflowAmount = 0.0;
		this.stockAmount = 0.0;
		this.contractLeaveNum = 0.0;
		this.expAmount = 0.0;
		this.impAmount = 0.0;
		this.unitPrice = 0.0;
	}
}
