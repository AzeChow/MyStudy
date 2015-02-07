package com.bestway.bcs.contractcav.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;
import com.bestway.dzsc.checkcancel.entity.DzscContractCav;

/**
 * 存放分组后的单耗、损耗量、总用量
 */
public class ContractUnitWasteCav implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 核销表头
	 */
	private ContractCav contractCav;

	/**
	 * 分组
	 */
	private Integer groupId = null;

	/**
	 * 料件序
	 */
	private String seqMaterialNum;

	/**
	 * 成品名称
	 */
	private String productName;

	/**
	 * 成品规格
	 */
	private String productSpec;

	/**
	 * 料件名称
	 */
	private String materialName;

	/**
	 * 料件规格
	 */
	private String materialSpec;

	/**
	 * 单耗
	 */
	private Double unitWaste1 = null;

	/**
	 * 损耗量
	 */
	private Double wasteAmount1 = null;

	/**
	 * 总用量
	 */
	private Double totalAmount1 = null;

	/**
	 * 单耗
	 */
	private Double unitWaste2 = null;

	/**
	 * 损耗量
	 */
	private Double wasteAmount2 = null;

	/**
	 * 总用量
	 */
	private Double totalAmount2 = null;

	/**
	 * 单耗
	 */
	private Double unitWaste3 = null;

	/**
	 * 损耗量
	 */
	private Double wasteAmount3 = null;

	/**
	 * 总用量
	 */
	private Double totalAmount3 = null;

	/**
	 * 单耗
	 */
	private Double unitWaste4 = null;

	/**
	 * 损耗量
	 */
	private Double wasteAmount4 = null;

	/**
	 * 总用量
	 */
	private Double totalAmount4 = null;
	
	/**
	 * 根据损耗量决定单净耗打印位置-临时字段
	 */
	private Boolean isPrintUnitNet1 = false;
	/**
	 * 根据损耗量决定单净耗打印位置-临时字段
	 */
	private Boolean isPrintUnitNet2 = false;
	/**
	 * 根据损耗量决定单净耗打印位置-临时字段
	 */
	private Boolean isPrintUnitNet3 = false;
	/**
	 * 根据损耗量决定单净耗打印位置-临时字段
	 */
	private Boolean isPrintUnitNet4 = false;
	

	public Boolean getIsPrintUnitNet1() {
		return isPrintUnitNet1;
	}

	public void setIsPrintUnitNet1(Boolean isPrintUnitNet1) {
		this.isPrintUnitNet1 = isPrintUnitNet1;
	}

	public Boolean getIsPrintUnitNet2() {
		return isPrintUnitNet2;
	}

	public void setIsPrintUnitNet2(Boolean isPrintUnitNet2) {
		this.isPrintUnitNet2 = isPrintUnitNet2;
	}

	public Boolean getIsPrintUnitNet3() {
		return isPrintUnitNet3;
	}

	public void setIsPrintUnitNet3(Boolean isPrintUnitNet3) {
		this.isPrintUnitNet3 = isPrintUnitNet3;
	}

	public Boolean getIsPrintUnitNet4() {
		return isPrintUnitNet4;
	}

	public void setIsPrintUnitNet4(Boolean isPrintUnitNet4) {
		this.isPrintUnitNet4 = isPrintUnitNet4;
	}

	/**
	 * 获取分组
	 * 
	 * @return groupId 分组
	 */
	public Integer getGroupId() {
		return groupId;
	}

	/**
	 * 设置分组
	 * 
	 * @param groupId
	 *            分组
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	/**
	 * 获取总用量1
	 * 
	 * @return totalAmount1 总用量1
	 */
	public Double getTotalAmount1() {
		return totalAmount1;
	}

	/**
	 * 设置总用量1
	 * 
	 * @param totalAmount1
	 *            总用量1
	 */
	public void setTotalAmount1(Double totalAmount1) {
		this.totalAmount1 = totalAmount1;
	}

	/**
	 * 获取总用量2
	 * 
	 * @return totalAmount2 总用量2
	 */
	public Double getTotalAmount2() {
		return totalAmount2;
	}

	/**
	 * 设置总用量2
	 * 
	 * @param totalAmount2
	 *            总用量2
	 */
	public void setTotalAmount2(Double totalAmount2) {
		this.totalAmount2 = totalAmount2;
	}

	/**
	 * 获取总用量3
	 * 
	 * @return totalAmount3 总用量3
	 */
	public Double getTotalAmount3() {
		return totalAmount3;
	}

	/**
	 * 设置总用量3
	 * 
	 * @param totalAmount3
	 *            总用量3
	 */
	public void setTotalAmount3(Double totalAmount3) {
		this.totalAmount3 = totalAmount3;
	}

	/**
	 * 获取总用量4
	 * 
	 * @return totalAmount4 总用量4
	 */
	public Double getTotalAmount4() {
		return totalAmount4;
	}

	/**
	 * 设置总用量4
	 * 
	 * @param totalAmount4
	 *            总用量4
	 */
	public void setTotalAmount4(Double totalAmount4) {
		this.totalAmount4 = totalAmount4;
	}

	/**
	 * 获取单耗1
	 * 
	 * @return unitWaste1 单耗1
	 */
	public Double getUnitWaste1() {
		return unitWaste1;
	}

	/**
	 * 设置单耗1
	 * 
	 * @param unitWaste1
	 *            单耗1
	 */
	public void setUnitWaste1(Double unitWaste1) {
		this.unitWaste1 = unitWaste1;
	}

	/**
	 * 获取单耗2
	 * 
	 * @return unitWaste2 单耗2
	 */
	public Double getUnitWaste2() {
		return unitWaste2;
	}

	/**
	 * 设置单耗2
	 * 
	 * @param unitWaste2
	 *            单耗2
	 */
	public void setUnitWaste2(Double unitWaste2) {
		this.unitWaste2 = unitWaste2;
	}

	/**
	 * 获取单耗3
	 * 
	 * @return unitWaste3 单耗3
	 */
	public Double getUnitWaste3() {
		return unitWaste3;
	}

	/**
	 * 设置单耗3
	 * 
	 * @param unitWaste3
	 *            单耗3
	 */
	public void setUnitWaste3(Double unitWaste3) {
		this.unitWaste3 = unitWaste3;
	}

	/**
	 * 获取单耗4
	 * 
	 * @return unitWaste4 单耗4
	 */
	public Double getUnitWaste4() {
		return unitWaste4;
	}

	/**
	 * 设置单耗
	 * 
	 * @param unitWaste4
	 *            单耗4
	 */
	public void setUnitWaste4(Double unitWaste4) {
		this.unitWaste4 = unitWaste4;
	}

	/**
	 * 获取损耗量1
	 * 
	 * @return wasteAmount1 损耗量
	 */
	public Double getWasteAmount1() {
		return wasteAmount1;
	}

	/**
	 * 设置损耗量
	 * 
	 * @param wasteAmount1
	 *            损耗量1
	 */
	public void setWasteAmount1(Double wasteAmount1) {
		this.wasteAmount1 = wasteAmount1;
	}

	/**
	 * 获取损耗量2
	 * 
	 * @return wasteAmount2 损耗量2
	 */
	public Double getWasteAmount2() {
		return wasteAmount2;
	}

	/**
	 * 设置损耗量2
	 * 
	 * @param wasteAmount2
	 *            损耗量2
	 */
	public void setWasteAmount2(Double wasteAmount2) {
		this.wasteAmount2 = wasteAmount2;
	}

	/**
	 * 获取损耗量3
	 * 
	 * @return wasteAmount3 损耗量3
	 */
	public Double getWasteAmount3() {
		return wasteAmount3;
	}

	/**
	 * 设置损耗量3
	 * 
	 * @param wasteAmount3
	 *            损耗量3
	 */
	public void setWasteAmount3(Double wasteAmount3) {
		this.wasteAmount3 = wasteAmount3;
	}

	/**
	 * 获取损耗量4
	 * 
	 * @return wasteAmount4 损耗量4
	 */
	public Double getWasteAmount4() {
		return wasteAmount4;
	}

	/**
	 * 设置损耗量4
	 * 
	 * @param wasteAmount4
	 *            损耗量4
	 */
	public void setWasteAmount4(Double wasteAmount4) {
		this.wasteAmount4 = wasteAmount4;
	}

	/**
	 * 获取料件名称
	 * 
	 * @return materialName 料件名称
	 */
	public String getMaterialName() {
		return materialName;
	}

	/**
	 * 设置料件名称
	 * 
	 * @param materialName
	 *            料件名称
	 */
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	/**
	 * 获取料件规格
	 * 
	 * @return materialSpec 料件规格
	 */
	public String getMaterialSpec() {
		return materialSpec;
	}

	/**
	 * 设置料件规格
	 * 
	 * @param materialSpec
	 *            料件规格
	 */
	public void setMaterialSpec(String materialSpec) {
		this.materialSpec = materialSpec;
	}

	/**
	 * 获取成品名称
	 * 
	 * @return productName 成品名称
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 设置成品名称
	 * 
	 * @param productName
	 *            成品名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 获取成品规格
	 * 
	 * @return productSpec 成品规格
	 */
	public String getProductSpec() {
		return productSpec;
	}

	/**
	 * 设置成品规格
	 * 
	 * @param productSpec
	 *            成品规格
	 */
	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	/**
	 * 获取料件序
	 * 
	 * @return seqMaterialNum 料件序
	 */
	public String getSeqMaterialNum() {
		return seqMaterialNum;
	}

	/**
	 * 设置料件序
	 * 
	 * @param seqMaterialNum
	 *            料件序
	 */
	public void setSeqMaterialNum(String seqMaterialNum) {
		this.seqMaterialNum = seqMaterialNum;
	}

	/**
	 * 获取核销表头
	 * 
	 * @return contractCav 核销表头
	 */
	public ContractCav getContractCav() {
		return contractCav;
	}

	/**
	 * 设置核销表头
	 * 
	 * @param contractCav
	 *            核销表头
	 */
	public void setContractCav(ContractCav contractCav) {
		this.contractCav = contractCav;
	}
}
