/*
 * Created on 2005-3-22
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contract.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DutyRateType;
import com.bestway.common.constant.EquipMode;
import com.bestway.customs.common.entity.BaseEmsExg;

/**
 * 存放合同备案成品资料
 */
public class ContractExg extends BaseEmsExg implements Comparable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 合同表头
	 */
	private Contract contract;

	/**
	 * 出口数量
	 */
	private Double exportAmount;

	/**
	 * 单价
	 */
	private Double unitPrice;

	/**
	 * 总金额
	 */
	private Double totalPrice;

	/**
	 * 法定单位数量
	 */
	private Double legalAmount;

	/**
	 * 第一法定比例因子
	 */
	private Double legalUnitGene;

	/**
	 * 第二法定单位数量
	 */
	private Double secondAmount;

	/**
	 * 第二法定比例因子
	 * 
	 */
	private Double legalUnit2Gene;

	/**
	 * 归并序号,记录号（备案资料库序号）
	 */
	private Integer credenceNo;

	/**
	 * 原料费用
	 */
	private Double materialFee;

	/**
	 * 消费国
	 */
	private Country country;

	/**
	 * 加工费单价
	 */
	private Double processUnitPrice;

	/**
	 * 加工费总价
	 */
	private Double processTotalPrice;

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
	private LevyMode levyMode = null;

	/**
	 * 未修改标志 UNCHANGE = "0"; 已修改 MODIFIED = "1"; 已删除 DELETED = "2"; 新增 ADDED ="3"	 * 
	 */
	private String modifyMark = null;

	/**
	 * 方式（我也不知道是什么方式，是客户填的）
	 */

	private String mannerNote = null;

	/**
	 * 申报状态 1-企业不申报； 2-企业申报； 9-已核定
	 * 
	 */
	private Double dutyRate = null;
	
	private String dutyRateString = null;

	/**
	 * 获取出口数量
	 * 
	 * @return exportAmount 出口数量
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
	 * 获取原料费用
	 * 
	 * @return materialFee 原料费用
	 */
	public Double getMaterialFee() {
		return materialFee;
	}

	/**
	 * 设置原料费用
	 * 
	 * @param materialFee
	 *            原料费用
	 */
	public void setMaterialFee(Double materialFee) {
		this.materialFee = materialFee;
	}

	/**
	 * 获取 加工费总价 = processUnitPrice * exportAmount
	 * 
	 * @return processTotalPrice 加工费总价
	 */
	public Double getProcessTotalPrice() {
		// if(this.processTotalPrice!=null){
		// return this.processTotalPrice;
		// }
		// double exportAmount = 0;
		// double processUnitPrice = 0;
		// if(this.exportAmount!= null){
		// exportAmount = this.exportAmount.doubleValue() ;
		// }
		// if(this.processUnitPrice != null){
		// processUnitPrice = this.processUnitPrice.doubleValue() ;
		// }
		// return exportAmount*processUnitPrice;
		return this.processTotalPrice;
	}

	/**
	 * 设置加工费总价
	 * 
	 * @param processTotalPrice
	 *            加工费总价
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
	 * @param processUnitPrice
	 *            加工费单价
	 */
	public void setProcessUnitPrice(Double processUnitPrice) {
		this.processUnitPrice = processUnitPrice;
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
	 * 设置单位毛重
	 * 
	 * @param unitGrossWeight
	 *            单位毛重
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
	 * @param unitNetWeight
	 *            单位净重
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
	 * @param unitPrice
	 *            单价
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * 获取合同表头
	 * 
	 * @return contract 合同表头
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * 设置合同表头
	 * 
	 * @param contract
	 *            合同表头
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * 获取消费国
	 * 
	 * @return country 消费国
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 设置消费国
	 * 
	 * @param country
	 *            消费国
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * 获取归并序号
	 * 
	 * @return credenceNo 归并序号
	 */
	public Integer getCredenceNo() {
		return credenceNo;
	}

	/**
	 * 设置归并序号
	 * 
	 * @param credenceNo
	 *            归并序号
	 */
	public void setCredenceNo(Integer credenceNo) {
		this.credenceNo = credenceNo;
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
	 * @param legalAmount
	 *            法定单位数量
	 */
	public void setLegalAmount(Double legalAmount) {
		this.legalAmount = legalAmount;
	}

	// /**
	// * 获取法定单位
	// *
	// * @return legalUnit 法定单位
	// */
	// public Unit getLegalUnit() {
	// return legalUnit;
	// }
	//
	// /**
	// * 设置法定单位
	// *
	// * @param legalUnit 法定单位
	// */
	// public void setLegalUnit(Unit legalUnit) {
	// this.legalUnit = legalUnit;
	// }

	/**
	 * 获取 总金额 = unitPrice * exportAmount
	 * 
	 * @return totalPrice 总金额
	 */
	public Double getTotalPrice() {
		// if(this.totalPrice!=null){
		// return this.totalPrice;
		// }
		// double exportAmount = 0;
		// double unitPrice = 0;
		// if(this.exportAmount!= null){
		// exportAmount = this.exportAmount.doubleValue() ;
		// }
		// if(this.unitPrice != null){
		// unitPrice = this.unitPrice.doubleValue() ;
		// }
		// return exportAmount*unitPrice;
		return this.totalPrice;
	}

	/**
	 * 设置总金额
	 * 
	 * @param totalPrice
	 *            总金额
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * 获取serialVersionUID
	 * 
	 * @return Returns the serialVersionUID.
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
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
	 * @param levyMode
	 *            征减免税方式
	 */
	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

	/**
	 * 获取第二法定单位数量
	 * 
	 * @return secondAmount 第二法定单位数量
	 */
	public Double getSecondAmount() {
		return secondAmount;
	}

	/**
	 * 设置第二法定单位数量
	 * 
	 * @param secondAmount
	 *            第二法定单位数量
	 */
	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}

	// /**
	// * 获取第二法定单位
	// *
	// * @return secondUnit 第二法定单位
	// */
	// public Unit getSecondUnit() {
	// return secondUnit;
	// }
	// /**
	// * 设置第二法定单位
	// *
	// * @param secondUnit 第二法定单位
	// */
	// public void setSecondUnit(Unit secondUnit) {
	// this.secondUnit = secondUnit;
	// }
	/**
	 * 商品编号
	 * 
	 * @return
	 */
	public String getComplexTCode() {
		if (this.getComplex() == null
				|| this.getComplex().getCode().length() < 8) {
			return "";
		} else {
			return this.getComplex().getCode().substring(0,8);
		}
	}
	/**
	 * 附加编码
	 * 
	 * @return
	 */
	public String getComplexSCode() {
		if (this.getComplex() == null
				|| this.getComplex().getCode().length() <= 8) {
			return "";
		} else {
			return this.getComplex().getCode().substring(8,
					this.getComplex().getCode().length());
		}
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	public int compareTo(Object obj) {
		ContractExg o = (ContractExg) obj;
		if (this.getSeqNum() == null && o.getSeqNum() == null) {
			return 0;
		} else if (this.getSeqNum() == null && o.getSeqNum() != null) {
			return -1;
		} else if (this.getSeqNum() != null && o.getSeqNum() == null) {
			return 1;
		} else if (this.getSeqNum() > o.getSeqNum()) {
			return 1;
		} else if (this.getSeqNum() == o.getSeqNum()) {
			return 0;
		} else if (this.getSeqNum() < o.getSeqNum()) {
			return -1;
		}
		return -1;
	}

	public String getMannerNote() {
		return mannerNote;
	}

	public void setMannerNote(String mannerNote) {
		this.mannerNote = mannerNote;
	}

	public Double getLegalUnitGene() {
		return legalUnitGene;
	}

	public void setLegalUnitGene(Double legalUnitGene) {
		this.legalUnitGene = legalUnitGene;
	}

	public Double getLegalUnit2Gene() {
		return legalUnit2Gene;
	}

	public void setLegalUnit2Gene(Double legalUnit2Gene) {
		this.legalUnit2Gene = legalUnit2Gene;
	}

	public Double getDutyRate() {
		if (dutyRate == null) {
			// 如果单耗申报环节是1.备案 的话，那么成品的申报状态默认为 企业申报 2
			if (EquipMode.PUT_ON_RECORD.equals(this.contract.getEquipMode())) {
				return Double.valueOf(DutyRateType.DECLARE);
			}
			// 否则默认为 企业不申报 1
			return Double.valueOf(DutyRateType.NO_DECLARE);
		}
		return dutyRate;
	}

	public void setDutyRate(Double dutyRate) {
		this.dutyRate = dutyRate;
	}

	public String getDutyRateString() {
		if(dutyRate == 1){
			return "企业不申报";
		}else if(dutyRate == 2){
			return "企业申报";
		}else if(dutyRate == 9){
			return "已核定";
		}
		return "";
	}
}
