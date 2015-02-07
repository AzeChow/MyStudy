/*
 * Created on 2005-6-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractanalyse.entity;

import java.io.Serializable;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 临时实体类，存放报关分析－－料件耗用
 * 
 * @author ls
 */
public class TempImpMaterielExeStat implements Serializable {
	/**
	 * 合同BOM资料
	 */
	private ContractBom contractBom;
	/**
	 * 商品编码
	 */
	private String complexCode;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 规格型号
	 */
	private String spec;

	/**
	 * 计量单位
	 */
	private Unit unit;

	/**
	 * 合同号
	 */
	private String contractNo;

	/**
	 * 单耗
	 */
	private Double unitWaste;

	/**
	 * 损耗率
	 */
	private Double waste;

	/**
	 * 单项用量
	 */
	private Double unitDosage = null;
	/**
	 * 出口数量
	 */
	private Double exportAmount;
	/**
	 * 耗用数量
	 */
	private Double usedAmount;
	/**
	 * 结转出口耗用
	 */
	private Double transferExpUsedAmount;
	/**
	 * 直接出口耗用
	 */
	private Double directExpUsedAmount;
	
	/**
	 * 获取商品编码
	 * 
	 * @return 商品编码
	 */
	public String getComplexCode() {
		return complexCode;
	}

	/**
	 * 设置商品编码
	 * 
	 * @param complexCode
	 *            商品编码
	 */
	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}

	/**
	 * 获取合同号
	 * 
	 * @return 合同号
	 */
	public String getContractNo() {
		return contractNo;
	}

	/**
	 * 设置合同号
	 * 
	 * @param contractNo
	 *            合同号
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * 获取出口数量
	 * 
	 * @return 出口数量
	 */
	public Double getExportAmount() {
		return exportAmount;
	}

	/**
	 * 设置出口数量
	 * 
	 * @param exportAmount
	 *            出口数量
	 */
	public void setExportAmount(Double exportAmount) {
		this.exportAmount = exportAmount;
	}

	/**
	 * 获取商品名称
	 * 
	 * @return 商品名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param name
	 *            商品名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取规格型号
	 * 
	 * @return 规格型号
	 */
	public String getSpec() {
		return spec;
	}

	/**
	 * 设置规格型号
	 * 
	 * @param spec
	 *            规格型号
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}

	/**
	 * 获取计量单位
	 * 
	 * @return 计量单位
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * 设置计量单位
	 * 
	 * @param unit
	 *            计量单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * 获取单项用量
	 * 
	 * @return 单项用量
	 */
	public Double getUnitDosage() {
		return unitDosage;
	}

	/**
	 * 设置单项用量
	 * 
	 * @param unitDosage
	 *            单项用量
	 */

	public void setUnitDosage(Double unitDosage) {
		this.unitDosage = unitDosage;
	}

	/**
	 * 获取单耗
	 * 
	 * @return 单耗
	 */
	public Double getUnitWaste() {
		return unitWaste;
	}

	/**
	 * 设置单耗
	 * 
	 * @param unitWaste
	 *            单耗
	 */
	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}

	/**
	 * 获取 耗用数量
	 * 
	 * @return 耗用数量
	 */
	public Double getUsedAmount() {
		return usedAmount;
	}

	/**
	 * 设置耗用数量
	 * 
	 * @param usedAmount
	 *            耗用数量
	 */
	public void setUsedAmount(Double usedAmount) {
		this.usedAmount = usedAmount;
	}

	/**
	 * 获取损耗率
	 * 
	 * @return 损耗率
	 */
	public Double getWaste() {
		return waste;
	}

	/**
	 * 设置损耗率
	 * 
	 * @param waste
	 *            损耗率
	 */
	public void setWaste(Double waste) {
		this.waste = waste;
	}

	/**
	 * 获取合同BOM资料
	 * 
	 * @return 合同BOM资料
	 */
	public ContractBom getContractBom() {
		return contractBom;
	}

	/**
	 * 设置合同BOM资料
	 * 
	 * @param contractBom
	 *            合同BOM资料
	 */
	public void setContractBom(ContractBom contractBom) {
		this.contractBom = contractBom;
	}

	/**
	 * 获取结转出口耗用
	 * 
	 * @return 结转出口耗用
	 */
	public Double getTransferExpUsedAmount() {
		return transferExpUsedAmount;
	}

	/**
	 * 设置结转出口耗用
	 * 
	 * @param transferExpUsedAmount
	 *            结转出口耗用
	 */
	public void setTransferExpUsedAmount(Double transferExpUsedAmount) {
		this.transferExpUsedAmount = transferExpUsedAmount;
	}

	/**
	 * 获取直接出口耗用
	 * 
	 * @return 直接出口耗用
	 */
	public Double getDirectExpUsedAmount() {
		return directExpUsedAmount;
	}

	/**
	 * 设置直接出口耗用
	 * 
	 * @param directExpUsedAmount
	 *            直接出口耗用
	 */
	public void setDirectExpUsedAmount(Double directExpUsedAmount) {
		this.directExpUsedAmount = directExpUsedAmount;
	}
}
