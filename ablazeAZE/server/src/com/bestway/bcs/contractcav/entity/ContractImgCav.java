/*
 * Created on 2005-4-15
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractcav.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.dzsc.checkcancel.entity.DzscContractCav;

/**
 * 合同核销料件资料
 */
public class ContractImgCav extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 退换出口
	 */
	private Double exchangeExport;

	/**
	 * 合同核销单头
	 */
	private ContractCav contractCav;

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
	 * 进口净重
	 */
	private Double impNetWeight;
	/**
	 * 单位重量
	 */
	private Double unitWeight;
	/**
	 * 料件进口数量
	 */
	private Double directImport;

	/**
	 * 料件转厂数量
	 */
	private Double transferFactoryImport;

	/**
	 * 余料进口数量
	 */
	private Double remainImport;
	/**
	 * 余料结转出口数量
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
	 * 企业库存数量
	 */
	private Double stockAmount;
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
	 * 修改标志 UNCHANGE = "0"; 未修改 MODIFIED = "1"; 已修改 DELETED = "2"; 已删除 ADDED =
	 * "3"; 新增
	 */
	private String modifyMark = null;
	
	/**
	 * 国内购买数量(番禺)
	 */
	private Double domesticPurchase = null;
	
	/**
	 * 净耗重量（KG）(番禺)
	 */
	private Double netLossWeight = null;
	
	/**
	 * 说明原因
	 */
	private String explain = null;

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
	public ContractCav getContractCav() {
		return contractCav;
	}

	/**
	 * 设置合同核销单头
	 * 
	 * @param contractCav
	 *            合同核销单头
	 */
	public void setContractCav(ContractCav contractCav) {
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

	/**
	 * 获取退换出口
	 * 
	 * @return exchangeExport 退换出口
	 */
	public Double getExchangeExport() {
		return exchangeExport;
	}

	/**
	 * 设置退换出口
	 * 
	 * @param exchangeExport
	 *            退换出口
	 */
	public void setExchangeExport(Double exchangeExport) {
		this.exchangeExport = exchangeExport;
	}

	/**
	 * 获取余料转出口数量
	 */
	public Double getRemainExport() {
		return remainExport;
	}

	/**
	 * 设置余料转出口数量
	 */
	public void setRemainExport(Double remainExport) {
		this.remainExport = remainExport;
	}

	/**
	 * 获取进口净重
	 */

	public Double getImpNetWeight() {
		return impNetWeight;
	}

	/**
	 * 设置进口净重
	 */
	public void setImpNetWeight(Double impNetWeight) {
		this.impNetWeight = impNetWeight;
	}

	/**
	 * 获取单位重量
	 * 
	 * @return
	 */

	public Double getUnitWeight() {
		return unitWeight;
	}

	/**
	 * 设置单位重量
	 * 
	 * @param unitWeight
	 */

	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	public Double getStockAmount() {
		if (this.stockAmount == null) {
			return 0.0;
		}
		return stockAmount;
	}

	public void setStockAmount(Double stockAmount) {
		this.stockAmount = stockAmount;
	}

	/**
	 * 国内购买数量
	 */
	public Double getDomesticPurchase() {
		return domesticPurchase;
	}

	/**
	 * 国内购买数量
	 */
	public void setDomesticPurchase(Double domesticPurchase) {
		this.domesticPurchase = domesticPurchase;
	}

	/**
	 * 净耗重量（KG）
	 */
	public Double getNetLossWeight() {
		return netLossWeight;
	}

	/**
	 * 净耗重量（KG）
	 */
	public void setNetLossWeight(Double netLossWeight) {
		this.netLossWeight = netLossWeight;
	}

	/**
	 * 说明原因
	 */
	public String getExplain() {
		return explain;
	}

	/**
	 * 说明原因
	 */
	public void setExplain(String explain) {
		this.explain = explain;
	}
}
