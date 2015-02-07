/*
 * Created on 2005-4-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.checkcancel.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscCustomsModifyState;
import com.bestway.common.constant.ModifyMarkState;

/**
 * 存放电子手册核销BOM资料
 * 
 * @author Administrator
 */
public class DzscContractBomCav extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 核销表头
	 */
	private DzscContractCav contractCav;

	/**
	 * 成品序号
	 */
	private Integer seqProductNum;

	/**
	 * 料件序号
	 */
	private Integer seqMaterialNum;

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
	 * 成品数量
	 */
	private Double exgExpTotalAmount;

	/**
	 * 损耗
	 */
	private Double waste;

	/**
	 * 单耗
	 */
	private Double unitWaste;

	/**
	 * 损耗量
	 */
	private Double wasteAmount;

	/**
	 * 总用量
	 */
	private Double totalAmount;
	/**
	 * 非保税料件比例%
	 */
	private Double nonMnlRatio;
	

	/**
	 * 是海关
	 */
	private Boolean isCustoms = false;

	/**
	 * 为界面选择而用
	 */
	private Boolean isSelected = true;


    /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark = null;

	/**
	 * 获取核销表头
	 * 
	 * @return contractCav 核销表头
	 */
	public DzscContractCav getContractCav() {
		return contractCav;
	}

	/**
	 * 设置核销表头
	 * 
	 * @param contractCav
	 *            核销表头
	 */
	public void setContractCav(DzscContractCav contractCav) {
		this.contractCav = contractCav;
	}
	/**
	 * 获取非保税料件比例%
	 */
	public Double getNonMnlRatio() {
		return nonMnlRatio;
	}
	/**
	 * 设置非保税料件比例%
	 */
	public void setNonMnlRatio(Double nonMnlRatio) {
		this.nonMnlRatio = nonMnlRatio;
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
	 * 获取料件序号
	 * 
	 * @return seqMaterialNum 料件序号
	 */
	public Integer getSeqMaterialNum() {
		return seqMaterialNum;
	}

	/**
	 * 设置料件序号
	 * 
	 * @param seqMaterialNum
	 *            料件序号
	 */
	public void setSeqMaterialNum(Integer seqMaterialNum) {
		this.seqMaterialNum = seqMaterialNum;
	}

	/**
	 * 获取成品序号
	 * 
	 * @return seqProductNum 成品序号
	 */
	public Integer getSeqProductNum() {
		return seqProductNum;
	}

	/**
	 * 设置成品序号
	 * 
	 * @param seqProductNum
	 *            成品序号
	 */
	public void setSeqProductNum(Integer seqProductNum) {
		this.seqProductNum = seqProductNum;
	}

	/**
	 * 获取总用量
	 * 
	 * @return totalAmount 总用量
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * 设置总用量
	 * 
	 * @param totalAmount
	 *            总用量
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * 获取单耗
	 * 
	 * @return unitWaste 单耗
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
	 * 获取损耗量
	 * 
	 * @return wasteAmount 损耗量
	 */
	public Double getWasteAmount() {
		return wasteAmount;
	}

	/**
	 * 设置损耗量
	 * 
	 * @param wasteAmount
	 *            损耗量
	 */
	public void setWasteAmount(Double wasteAmount) {
		this.wasteAmount = wasteAmount;
	}

	/**
	 * 获取海关或自用判断，true代表是海关用
	 * 
	 * @return isCustoms 是海关，还是自用 true代表是海关用
	 */
	public Boolean getIsCustoms() {
		return isCustoms;
	}

	/**
	 * 设置海关或自用判断，true代表是海关用
	 * 
	 * @param isCustoms
	 *            是海关，还是自用 true代表是海关用
	 */
	public void setIsCustoms(Boolean isCustoms) {
		this.isCustoms = isCustoms;
	}

	/**
	 * 获取成品数量
	 * 
	 * @return exgExpTotalAmount 成品数量
	 */
	public Double getExgExpTotalAmount() {
		return exgExpTotalAmount;
	}

	/**
	 * 设置成品数量
	 * 
	 * @param exgExpTotalAmount
	 *            成品数量
	 */
	public void setExgExpTotalAmount(Double exgExpTotalAmount) {
		this.exgExpTotalAmount = exgExpTotalAmount;
	}

	/**
	 * 获取损耗
	 * 
	 * @return waste 损耗
	 */
	public Double getWaste() {
		return waste;
	}

	/**
	 * 设置损耗
	 * 
	 * @param waste
	 *            损耗
	 */
	public void setWaste(Double waste) {
		this.waste = waste;
	}

	/**
	 * 获取界面选择判断
	 * 
	 * @return isSelected 界面选择判断，true为见面选用
	 */
	public Boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * 设置界面选择判断
	 * 
	 * @param isSelected
	 *            界面选择判断，true为见面选用
	 */
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getCustomsModifyMark() {
		return DzscCustomsModifyState.getCustomsModifyState(modifyMark);
	}

	public Integer getBomVersion() {
		return 0;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}
}
