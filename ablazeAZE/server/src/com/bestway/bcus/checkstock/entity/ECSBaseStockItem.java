package com.bestway.bcus.checkstock.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 盘点核查工厂数据基础数据表
 * 
 * @author chl
 * @version 1.0
 * @createDate 2013-9-16
 */
public class ECSBaseStockItem extends BaseScmEntity {

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
	 * 报关名称
	 */
	private String hsName;
	/**
	 * 报关规格
	 */
	private String hsSpec;
	/**
	 * 报关单位
	 */
	private String hsUnit;
	/**
	 * 库存数量
	 */
	private Double hsAmount;
	/**
	 * 批次号
	 */
	private ECSSection section;
	/**
	 * 仓库
	 */
	private String warehouse;
	/**
	 * 备注
	 */
	private String memo;
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
	/**
	 * @return the hsName
	 */
	public String getHsName() {
		return hsName;
	}
	/**
	 * @param hsName the hsName to set
	 */
	public void setHsName(String hsName) {
		this.hsName = hsName;
	}
	/**
	 * @return the hsSpec
	 */
	public String getHsSpec() {
		return hsSpec;
	}
	/**
	 * @param hsSpec the hsSpec to set
	 */
	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}
	/**
	 * @return the hsUnit
	 */
	public String getHsUnit() {
		return hsUnit;
	}
	/**
	 * @param hsUnit the hsUnit to set
	 */
	public void setHsUnit(String hsUnit) {
		this.hsUnit = hsUnit;
	}
	/**
	 * @return the hsAmount
	 */
	public Double getHsAmount() {
		return hsAmount;
	}
	/**
	 * @param hsAmount the hsAmount to set
	 */
	public void setHsAmount(Double hsAmount) {
		this.hsAmount = hsAmount;
	}
	/**
	 * @return the section
	 */
	public ECSSection getSection() {
		return section;
	}
	/**
	 * @param section the section to set
	 */
	public void setSection(ECSSection section) {
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
	
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public ECSBaseStockItem(Integer serialNumber, String emsNo, Integer seqNum,
			String hsName, String hsSpec, String hsUnit, Double hsAmount,
			ECSSection section,String warehouse,String memo) {
		super();
		this.serialNumber = serialNumber;
		this.emsNo = emsNo;
		this.seqNum = seqNum;
		this.hsName = hsName;
		this.hsSpec = hsSpec;
		this.hsUnit = hsUnit;
		this.hsAmount = hsAmount;
		this.section = section;
		this.warehouse = warehouse;
		this.memo = memo;
	}
	
	public ECSBaseStockItem(String emsNo, Integer seqNum,
			String hsName, String hsSpec, String hsUnit, Double hsAmount) {
		super();
		this.emsNo = emsNo;
		this.seqNum = seqNum;
		this.hsName = hsName;
		this.hsSpec = hsSpec;
		this.hsUnit = hsUnit;
		this.hsAmount = hsAmount;
	}
	
	public ECSBaseStockItem() {
		super();
	}
	
}
