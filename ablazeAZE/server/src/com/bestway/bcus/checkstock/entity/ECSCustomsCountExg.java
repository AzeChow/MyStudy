package com.bestway.bcus.checkstock.entity;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;

/**
 * 成品统计表
 * 
 * @author chl
 * @version 1.0
 * @createDate 2013-9-16
 */
public class ECSCustomsCountExg extends BaseScmEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 盘点核查批次
	 */
	private ECSSection section;
	
	/**
	 * 帐册编号
	 */
	private String emsNo;
	
	/**
	 * 成品备案序号
	 */
	private Integer seqNum;
	
	/**
	 * 版本号
	 */
	private Integer version;
	
	
	/**
	 * 成品名称
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
	 * 出口数量 
	 */
	private Double directExportAmount;
	
	/**
	 * 转厂出口
	 */
	private Double transferExportAmount;
	
	/**
	 * 退厂返工
	 */
	private Double backFactoryReworkAmount;
	/**
	 * 返工复出
	 */
	private Double reworkExportAmount;
	
	/**
	 * 总出口
	 */
	private Double totalExportAmount;

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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	public Double getDirectExportAmount() {
		return directExportAmount;
	}

	public void setDirectExportAmount(Double directExportAmount) {
		this.directExportAmount = directExportAmount;
	}

	public Double getTransferExportAmount() {
		return transferExportAmount;
	}

	public void setTransferExportAmount(Double transferExportAmount) {
		this.transferExportAmount = transferExportAmount;
	}

	public Double getBackFactoryReworkAmount() {
		return backFactoryReworkAmount;
	}

	public void setBackFactoryReworkAmount(Double backFactoryReworkAmount) {
		this.backFactoryReworkAmount = backFactoryReworkAmount;
	}

	public Double getReworkExportAmount() {
		return reworkExportAmount;
	}

	public void setReworkExportAmount(Double reworkExportAmount) {
		this.reworkExportAmount = reworkExportAmount;
	}

	public Double getTotalExportAmount() {
		return totalExportAmount;
	}

	public void setTotalExportAmount(Double totalExportAmount) {
		this.totalExportAmount = totalExportAmount;
	}
	public void init() {
			this.backFactoryReworkAmount= 0d;
			this.directExportAmount = 0d;
			this.reworkExportAmount = 0d;
			this.totalExportAmount = 0d;
			this.transferExportAmount = 0d;
		
	}
}
