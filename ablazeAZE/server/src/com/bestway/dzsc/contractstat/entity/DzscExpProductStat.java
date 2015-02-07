/*
 * Created on 2005-5-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractstat.entity;

import java.io.Serializable;

import com.bestway.bcs.contractstat.entity.ExpProductStat;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 存放统计报表中的出口成品报关情况表资料
 * 
 * @author Administrator
 */
public class DzscExpProductStat implements Serializable,Comparable{
	
	/**
	 * 备案序号
	 */
	private Integer seqNum;

	/**
	 * 商品名称
	 */
	private String complex;
	
	/**
	 * 品名
	 */
	private String commName;
	
	/**
	 * 规格
	 */
	private String commSpec;
	
	/**
	 * 单位
	 */
	private Unit unit;
	
	/**
	 * 单价
	 */
	private Double unitPrice;
	
	/**
	 * 合同定量
	 */
	private Double contractAmount;
	
	/**
	 * 总出口量
	 */
	private Double expTotalAmont;
	
	/**
	 * 成品出口量
	 */
	private Double directExport;
	
	/**
	 * 比例
	 */
	private Double scale;
	
	/**
	 * 转厂出口
	 */
	private Double transferFactoryExport;
	
	/**
	 * 可出口量
	 */
	private Double canExportAmount;
	
	/**
	 * 超量
	 */
	private Double overAmount;
	
	/**
	 * 退厂返工数
	 */
	private Double backFactoryRework; 
	
	/**
	 * 返工复出数
	 */
	private Double reworkExport;
	
	/**
	 * 加工费单价
	 */
	private Double processUnitPrice; 
	
	/**
	 * 加工费总价
	 */
	private Double processTotalPrice; 
	
	/**
	 * 第一法定单位数量
	 */
	private Double legalAmount; 
	
	/**
	 * 第一法定单位
	 */
	private Unit legalUnit; 
	/**
	 * 第二法定单位
	 */
	private Unit secondLegalUnit;
	/**
	 * 第二法定数量
	 */
	private Double secondAmount;
	
	/**
	 * 单重
	 */
	private Double unitWeight; 
	
	/**
	 * 单位毛重
	 */
	private Double unitGrossWeight; 
	
	/**
	 * 单位净重
	 */
	private Double unitNetWeight; 
	
	/**
	 * 征减免税方式
	 */
    private LevyMode levyMode;
	/**
	 * 关封余量
	 */
	private Double customsEnvelopRemain;
	
	/**
	 * 手册号
	 */
	private String emsNo = null;
	
	/**
	 * 合同号
	 */
	private String ieContractNo;
	
	/**
	 * 补充说明
	 */
	private String note = null;
	
	/**
	 * 重compareTo方法，用serialNo（序号）进行比较
	 * 
	 * @param o
	 *            要比较的
	 * @return int 比o小时返回-1，相等时返回0，大于时返回1
	 */
	public int compareTo(Object o) {
		if (o == null) {
			return 1;
		}
		DzscExpProductStat stat = (DzscExpProductStat) o;
		if (this.getSeqNum()== null && stat.getSeqNum() == null) {
			return 0;
		}
		if (this.getSeqNum() == null && stat.getSeqNum() != null) {
			return -1;
		}
		if (this.getSeqNum() != null && stat.getSeqNum() == null) {
			return 1;
		}
		int serialNo1 = Integer.valueOf(this.getSeqNum());
		int serialNo2 = Integer.valueOf(stat.getSeqNum());
		if ((serialNo1 - serialNo2) > 0) {
			return 1;
		} else if ((serialNo1 - serialNo2) == 0) {
			return 0;
		} else if ((serialNo1 - serialNo2) < 0) {
			return -1;
		}
		return 0;
	}
	
	
	/**
	 * 获取退厂返工数
	 * 
	 * @return backFactoryRework 退厂返工数
	 */
	public Double getBackFactoryRework() {
		return backFactoryRework;
	}
	
	/**
	 * 设置退厂返工数
	 * 
	 * @param backFactoryRework 退厂返工数
	 */
	public void setBackFactoryRework(Double backFactoryRework) {
		this.backFactoryRework = backFactoryRework;
	}
	
	/**
	 * 获取可出口量
	 * 
	 * @return canExportAmount 可出口量
	 */
	public Double getCanExportAmount() {
		return canExportAmount;
	}
	
	/**
	 * 设置可出口量
	 * 
	 * @param canExportAmount 可出口量
	 */
	public void setCanExportAmount(Double canExportAmount) {
		this.canExportAmount = canExportAmount;
	}
	
	
	
	/**
	 * 获取品名
	 * 
	 * @return commName 品名
	 */
	public String getCommName() {
		return commName;
	}
	
	/**
	 * 设置品名
	 * 
	 * @param commName 品名
	 */
	public void setCommName(String commName) {
		this.commName = commName;
	}
	
	/**
	 * 获取规格
	 * 
	 * @return commSpec 规格
	 */
	public String getCommSpec() {
		return commSpec;
	}
	
	/**
	 * 设置规格
	 * 
	 * @param commSpec 规格 
	 */
	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}
	
	/**
	 * 获取合同定量
	 * 
	 * @return contractAmount 合同定量
	 */
	public Double getContractAmount() {
		return contractAmount;
	}
	
	/**
	 * 设置合同定量
	 * 
	 * @param contractAmount 合同定量
	 */
	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}
	
	/**
	 * 获取成品出口量
	 * 
	 * @return directExport 成品出口量
	 */
	public Double getDirectExport() {
		return directExport;
	}
	
	/**
	 * 设置成品出口量
	 * 
	 * @param directExport 成品出口量
	 */
	public void setDirectExport(Double directExport) {
		this.directExport = directExport;
	}
	
	
	/**
	 * 获取总出口量
	 * 
	 * @return expTotalAmont 总出口量 
	 */
	public Double getExpTotalAmont() {
		return expTotalAmont;
	}
	
	/**
	 * 设置总出口量
	 * 
	 * @param expTotalAmont 总出口量
	 */
	public void setExpTotalAmont(Double expTotalAmont) {
		this.expTotalAmont = expTotalAmont;
	}
	
	/**
	 * 获取法定单位数量
	 * 
	 * @return legalAmount 法定单位数量
	 */
	public Double getLegalAmount() {
		return legalAmount;
	}
	
	/**
	 * 设置法定单位数量
	 * 
	 * @param legalAmount 法定单位数量
	 */
	public void setLegalAmount(Double legalAmount) {
		this.legalAmount = legalAmount;
	}
	
	/**
	 * 获取法定单位
	 * 
	 * @return legalUnit 法定单位
	 */
	public Unit getLegalUnit() {
		return legalUnit;
	}
	
	/**
	 * 设置法定单位
	 * 
	 * @param legalUnit 法定单位
	 */
	public void setLegalUnit(Unit legalUnit) {
		this.legalUnit = legalUnit;
	}
	
	/**
	 * 获取征减免税方式
	 * 
	 * @return levyMode 征减免税方式
	 */
	public LevyMode getLevyMode() {
		return levyMode;
	}
	
	/**
	 * 设置征减免税方式
	 * 
	 * @param levyMode 征减免税方式
	 */
	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}
	
	/**
	 * 获取超量
	 * 
	 * @return overAmount 超量
	 */
	public Double getOverAmount() {
		return overAmount;
	}
	
	/**
	 * 设置超量
	 * 
	 * @param overAmount 超量
	 */
	public void setOverAmount(Double overAmount) {
		this.overAmount = overAmount;
	}
	
	/**
	 * 获取加工费总价
	 * 
	 * @return processTotalPrice 加工费总价
	 */
	public Double getProcessTotalPrice() {
		return processTotalPrice;
	}
	
	/**
	 * 设置加工费总价
	 * 
	 * @param processTotalPrice 加工费总价
	 */
	public void setProcessTotalPrice(Double processTotalPrice) {
		this.processTotalPrice = processTotalPrice;
	}
	
	/**
	 * 获取加工费单价
	 * 
	 * @return processUnitPrice 加工费单价
	 */
	public Double getProcessUnitPrice() {
		return processUnitPrice;
	}
	
	/**
	 * 设置加工费单价
	 * 
	 * @param processUnitPrice 加工费单价
	 */
	public void setProcessUnitPrice(Double processUnitPrice) {
		this.processUnitPrice = processUnitPrice;
	}
	
	/**
	 * 获取返工复出数
	 * 
	 * @return reworkExport 返工复出数
	 */
	public Double getReworkExport() {
		return reworkExport;
	}
	
	/**
	 * 设置返工复出数
	 * 
	 * @param reworkExport 返工复出数
	 */
	public void setReworkExport(Double reworkExport) {
		this.reworkExport = reworkExport;
	}
	
	/**
	 * 获取比例
	 * 
	 * @return scale 比例
	 */
	public Double getScale() {
		return scale;
	}
	
	/**
	 * 设置比例
	 * 
	 * @param scale 比例
	 */
	public void setScale(Double scale) {
		this.scale = scale;
	}
	
	
	/**
	 * 获取转厂出口
	 * 
	 * @return transferFactoryExport 转厂出口
	 */ 
	public Double getTransferFactoryExport() {
		return transferFactoryExport;
	}
	
	/**
	 * 设置转厂出口
	 * 
	 * @param transferFactoryExport 转厂出口
	 */
	public void setTransferFactoryExport(Double transferFactoryExport) {
		this.transferFactoryExport = transferFactoryExport;
	}
	
	/**
	 * 获取单位
	 * 
	 * @return unit 单位
	 */
	public Unit getUnit() {
		return unit;
	}
	
	/**
	 * 设置单位
	 * 
	 * @param unit 单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	/**
	 * 获取单位毛重 
	 * 
	 * @return unitGrossWeight 单位毛重
	 */
	public Double getUnitGrossWeight() {
		return unitGrossWeight;
	}
	
	/**
	 * 设置 单位毛重
	 * 
	 * @param unitGrossWeight 单位毛重
	 */
	public void setUnitGrossWeight(Double unitGrossWeight) {
		this.unitGrossWeight = unitGrossWeight;
	}
	
	/**
	 * 获取单位净重
	 * 
	 * @return unitNetWeight 单位净重
	 */
	public Double getUnitNetWeight() {
		return unitNetWeight;
	}
	
	/**
	 * 设置单位净重
	 * 
	 * @param unitNetWeight 单位净重
	 */
	public void setUnitNetWeight(Double unitNetWeight) {
		this.unitNetWeight = unitNetWeight;
	}
	
	/**
	 * 获取单价
	 * 
	 * @return unitPrice 单价
	 */
	public Double getUnitPrice() {
		return unitPrice;
	}
	
	/**
	 * 设置单价
	 * 
	 * @param unitPrice 单价
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	/**
	 * 获取单重
	 * 
	 * @return unitWeight 单重
	 */
	public Double getUnitWeight() {
		return unitWeight;
	}
	
	/**
	 * 设置单重
	 * 
	 * @param unitWeight 单重
	 */
	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	/**
	 * 获取凭证序号
	 * 
	 * @return seqNum 凭证序号
	 */
	public Integer getSeqNum() {
		return seqNum;
	}

	/**
	 * 设置凭证序号
	 * 
	 * @param seqNum 凭证序号
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * 获取商品名称
	 * 
	 * @return complex 商品名称
	 */
	public String getComplex() {
		return complex;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param complex 商品名称
	 */
	public void setComplex(String complex) {
		this.complex = complex;
	}

	public Double getCustomsEnvelopRemain() {
		return customsEnvelopRemain;
	}

	public void setCustomsEnvelopRemain(Double customsEnvelopRemain) {
		this.customsEnvelopRemain = customsEnvelopRemain;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public String getIeContractNo() {
		return ieContractNo;
	}

	public void setIeContractNo(String ieContractNo) {
		this.ieContractNo = ieContractNo;
	}


	public Unit getSecondLegalUnit() {
		return secondLegalUnit;
	}


	public void setSecondLegalUnit(Unit secondLegalUnit) {
		this.secondLegalUnit = secondLegalUnit;
	}


	public Double getSecondAmount() {
		return secondAmount;
	}


	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}

}
