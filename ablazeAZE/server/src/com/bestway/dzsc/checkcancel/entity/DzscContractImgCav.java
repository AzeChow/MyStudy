/*
 * Created on 2005-4-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.checkcancel.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放电子手册合同核销料件资料
 * 
 * @author Administrator
 */
public class DzscContractImgCav extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 合同核销单头
	 */
	private DzscContractCav contractCav;

	/**
	 * 料件序号
	 */
	private Integer seqNum;

	/**
	 * 商品编码
	 */
	private Complex complex;

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
	 * 进口总数
	 */
	private Double impTotal;

	/**
	 * 料件进口数量
	 */
	private Double directImport;

	/**
	 * 料件转厂数量
	 */
	private Double transferFactoryImport;

	/**
	 * 余料转进口数量
	 */
	private Double remainImport;

	/**
	 * 余料转出口数量
	 */
	private Double remainExport;

	/**
	 * 成品耗用量
	 */
	private Double productWaste;

	/**
	 * 内销数量
	 */
	private Double internalAmount;

	/**
	 * 退运出口数量
	 */
	private Double backExport;

	/**
	 * 边角料数量
	 */
	private Double leftoverMaterial;

	/**
	 * 余料数量
	 */
	private Double remainMaterial;

	/**
	 * 是海关
	 */
	private Boolean isCustoms = false;
	/**
	 * 企业库存数量
	 */
	private Double stockAmount;
	/**
	 * 是否选中
	 */
	private Boolean isSelected = false;

	/**
	 * 获取退运出口数量
	 * 
	 * @return backExport 退运出口数量
	 */
	public Double getBackExport() {
		return backExport;
	}

	/**
	 * 设置退运出口数量
	 * 
	 * @param backExport
	 *            退运出口数量
	 */
	public void setBackExport(Double backExport) {
		this.backExport = backExport;
	}

	/**
	 * 获取商品编码
	 * 
	 * @return complex 商品编码
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * 设置商品编码
	 * 
	 * @param complex
	 *            商品编码
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * 获取合同核销单头
	 * 
	 * @return contractCav 合同核销单头
	 */
	public DzscContractCav getContractCav() {
		return contractCav;
	}

	/**
	 * 设置合同核销单头
	 * 
	 * @param contractCav
	 *            合同核销单头
	 */
	public void setContractCav(DzscContractCav contractCav) {
		this.contractCav = contractCav;
	}

	/**
	 * 获取进口总数
	 * 
	 * @return impTotal 进口总数
	 */
	public Double getImpTotal() {
		return impTotal;
	}

	/**
	 * 设置进口总数
	 * 
	 * @param impTotal
	 *            进口总数
	 */
	public void setImpTotal(Double impTotal) {
		this.impTotal = impTotal;
	}

	/**
	 * 获取内销数量
	 * 
	 * @return internalAmount 内销数量
	 */
	public Double getInternalAmount() {
		return internalAmount;
	}

	/**
	 * 设置内销数量
	 * 
	 * @param internalAmount
	 *            内销数量
	 */
	public void setInternalAmount(Double internalAmount) {
		this.internalAmount = internalAmount;
	}

	/**
	 * 获取是海关，还是自用 true代表是海关用
	 * 
	 * @return isCustoms 是海关，还是自用 true代表是海关用
	 */
	public Boolean getIsCustoms() {
		return isCustoms;
	}

	/**
	 * 设置是海关，还是自用 true代表是海关用
	 * 
	 * @param isCustoms
	 *            是海关，还是自用 true代表是海关用
	 */
	public void setIsCustoms(Boolean isCustoms) {
		this.isCustoms = isCustoms;
	}

	/**
	 * 获取边角料数量
	 * 
	 * @return leftoverMaterial 边角料数量
	 */
	public Double getLeftoverMaterial() {
		return leftoverMaterial;
	}

	/**
	 * 设置边角料数量
	 * 
	 * @param leftoverMaterial
	 *            边角料数量
	 */
	public void setLeftoverMaterial(Double leftoverMaterial) {
		this.leftoverMaterial = leftoverMaterial;
	}

	/**
	 * 获取商品名称
	 * 
	 * @return name 商品名称
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
	 * 获取成品耗用量
	 * 
	 * @return productWaste 成品耗用量
	 */
	public Double getProductWaste() {
		return productWaste;
	}

	/**
	 * 设置成品耗用量
	 * 
	 * @param productWaste
	 *            成品耗用量
	 */
	public void setProductWaste(Double productWaste) {
		this.productWaste = productWaste;
	}

	/**
	 * 获取余料进口数量
	 * 
	 * @return remainImport 余料进口数量
	 */
	public Double getRemainImport() {
		return remainImport;
	}

	/**
	 * 设置余料进口数量
	 * 
	 * @param remainImport
	 *            余料进口数量
	 */
	public void setRemainImport(Double remainImport) {
		this.remainImport = remainImport;
	}

	/**
	 * 获取余料数量
	 * 
	 * @return remainMaterial 余料数量
	 */
	public Double getRemainMaterial() {
		return remainMaterial;
	}

	/**
	 * 设置余料数量
	 * 
	 * @param remainMaterial
	 *            余料数量
	 */
	public void setRemainMaterial(Double remainMaterial) {
		this.remainMaterial = remainMaterial;
	}

	/**
	 * 获取料件序号
	 * 
	 * @return seqNum 料件序号
	 */
	public Integer getSeqNum() {
		return seqNum;
	}

	/**
	 * 设置料件序号
	 * 
	 * @param seqNum
	 *            料件序号
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * 获取规格型号
	 * 
	 * @return spec 规格型号
	 */
	public String getSpec() {
		return spec;
	}

	/**
	 * 设置
	 * 
	 * @param spec
	 *            规格型号
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}

	/**
	 * 获取料件转厂数量
	 * 
	 * @return transferFactoryImport 料件转厂数量
	 */
	public Double getTransferFactoryImport() {
		return transferFactoryImport;
	}

	/**
	 * 设置料件转厂数量
	 * 
	 * @param transferFactoryImport
	 *            料件转厂数量
	 */
	public void setTransferFactoryImport(Double transferFactoryImport) {
		this.transferFactoryImport = transferFactoryImport;
	}

	/**
	 * 获取计量单位
	 * 
	 * @return unit 计量单位
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
	 * 获取料件进口数量
	 * 
	 * @return directImport 量料件进口数量
	 */
	public Double getDirectImport() {
		return directImport;
	}

	/**
	 * 设置料件进口数量
	 * 
	 * @param directImport
	 *            料件进口数量
	 */
	public void setDirectImport(Double directImport) {
		this.directImport = directImport;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Double getRemainExport() {
		return remainExport;
	}

	public void setRemainExport(Double remainExport) {
		this.remainExport = remainExport;
	}

	public Double getStockAmount() {
		if(this.stockAmount==null){
			return 0.0;
		}
		return stockAmount;
	}

	public void setStockAmount(Double stockAmount) {
		this.stockAmount = stockAmount;
	}
}
