package com.bestway.bcus.checkstock.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 料件情况统计表
 * 
 * @author chl
 */
public class ECSCustomsCountImg extends BaseScmEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 序号 
     */
    private Integer serialNumber; 

	/**
	 * 盘点核查批次
	 */
	private ECSSection section;
	
	/**
	 * 帐册编号
	 */
	private String emsNo;
	
	/**
	 * 备案序号
	 */
	private Integer seqNum;
	/**
	 * 料件名称
	 */
	private String commName;
	/**
	 * 型号规格
	 */
	private String commSpec;
	/**
	 * 计量单位
	 */
	private String unit;
	/**
	 * 期初数
	 */
	private Double qcAmount;
	/**
	 * 进口数量
	 */
	private Double directImportAmount;
	/**
	 * 转厂数量
	 */
	private Double transferExportAmount;
	/**
	 * 退料数量
	 */
	private Double backMaterExportAmount;
	/**
	 * 余料结转进口
	 */
	private Double remainForwardImportAmount;
	/**
	 * 余料结转出口
	 */
	private Double remainForwardExportAmount;
	/**
	 * 总进口数量
	 */
	private Double totalImportAmount;
	public ECSSection getSection() {
		return section;
	}
	public void setSection(ECSSection section) {
		this.section = section;
	}
	public String getEmsNo() {
		return emsNo;
	}
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public String getCommName() {
		return commName;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
	public String getCommSpec() {
		return commSpec;
	}
	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getQcAmount() {
		return qcAmount;
	}
	public void setQcAmount(Double qcAmount) {
		this.qcAmount = qcAmount;
	}
	public Double getDirectImportAmount() {
		return directImportAmount;
	}
	public void setDirectImportAmount(Double directImportAmount) {
		this.directImportAmount = directImportAmount;
	}
	public Double getTransferExportAmount() {
		return transferExportAmount;
	}
	public void setTransferExportAmount(Double transferExportAmount) {
		this.transferExportAmount = transferExportAmount;
	}
	public Double getBackMaterExportAmount() {
		return backMaterExportAmount;
	}
	public void setBackMaterExportAmount(Double backMaterExportAmount) {
		this.backMaterExportAmount = backMaterExportAmount;
	}
	public Double getRemainForwardImportAmount() {
		return remainForwardImportAmount;
	}
	public void setRemainForwardImportAmount(Double remainForwardImportAmount) {
		this.remainForwardImportAmount = remainForwardImportAmount;
	}
	public Double getRemainForwardExportAmount() {
		return remainForwardExportAmount;
	}
	public void setRemainForwardExportAmount(Double remainForwardExportAmount) {
		this.remainForwardExportAmount = remainForwardExportAmount;
	}
	public Double getTotalImportAmount() {
		return totalImportAmount;
	}
	public void setTotalImportAmount(Double totalImportAmount) {
		this.totalImportAmount = totalImportAmount;
	}
	
	public Integer getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * 初始化字段值
	 */
	public void init(){
		qcAmount = 0d;
		directImportAmount = 0d;
		transferExportAmount= 0d;
		backMaterExportAmount= 0d;
		remainForwardImportAmount= 0d;
		remainForwardExportAmount= 0d;
		totalImportAmount= 0d;		
	}
	
}
