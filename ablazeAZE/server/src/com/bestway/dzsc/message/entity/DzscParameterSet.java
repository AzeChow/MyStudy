package com.bestway.dzsc.message.entity;

import com.bestway.common.CommonUtils;
import com.bestway.common.message.entity.CspParameterSet;
/**
 * 电子手册参数设置表
 * @author ower
 *
 */
public class DzscParameterSet extends CspParameterSet {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * QP导入数据路径
	 */
	private String loadQPDataXmlDir = "";

	/**
	 * 管理类别
	 * 1为既用于关务管理也向海关申报数据;0为仅用于关务管理不向海关申报数据
	 */
	private String manageType = "0";

	/**
	 * 修改BOM反算成品
	 */
	private Boolean updateEmsBomWriteBackExg = false;

	/**
	 * 修改成品反算料件
	 */
	private Boolean updateEmsExgBomWriteBackImg = false;
	/**
	 * 修改料件反算成品、bom
	 * 2010-06-25 hcl
	 */
	private Boolean updateEmsImgWriteBackExg = false;

	/**
	 * 报表小数位数,默认为2位
	 */
	private Integer reportDecimalLength = null;
	
	/**
	 * 是否打印运输工具代码
	 */
	private Boolean isPrintToolCode;
	
	/**
	 * 是否打印航次
	 */
	private Boolean isPrintNo;

	/**
	 * 单价小数位控制
	 */
	private Integer priceBit = 5;

	/**
	 * 数量小数位控制
	 */
	private Integer countBit = 5;

	/**
	 * 金额小数位控制
	 */
	private Integer moneyBit = 5;
	/**
	 * 单耗小数位控制
	 */
	private Integer unitWaste = 5;
	/**
	 * 损耗小数位控制
	 */
	private Integer waste = 5;
	/**
	 * 总用量小数位控制
	 */
	private Integer totalAmount = 5;

	
	
	public Boolean getUpdateEmsImgWriteBackExg() {
		return updateEmsImgWriteBackExg;
	}

	public void setUpdateEmsImgWriteBackExg(Boolean updateEmsImgWriteBackExg) {
		this.updateEmsImgWriteBackExg = updateEmsImgWriteBackExg;
	}

	public Boolean getUpdateEmsBomWriteBackExg() {
		return updateEmsBomWriteBackExg;
	}

	public void setUpdateEmsBomWriteBackExg(Boolean updateDzscEmsBomWriteBackExg) {
		this.updateEmsBomWriteBackExg = updateDzscEmsBomWriteBackExg;
	}

	public Boolean getUpdateEmsExgBomWriteBackImg() {
		return updateEmsExgBomWriteBackImg;
	}

	public void setUpdateEmsExgBomWriteBackImg(
			Boolean updateDzscEmsExgWriteBackImg) {
		this.updateEmsExgBomWriteBackImg = updateDzscEmsExgWriteBackImg;
	}

	public String getLoadQPDataXmlDir() {
		return loadQPDataXmlDir;
	}

	public void setLoadQPDataXmlDir(String loadBGDXmlDir) {
		this.loadQPDataXmlDir = loadBGDXmlDir;
	}

	/**
	 * @return the reportDecimalLength
	 */
	public Integer getReportDecimalLength() {
		return reportDecimalLength;
	}

	/**
	 * @param reportDecimalLength
	 *            the reportDecimalLength to set
	 */
	public void setReportDecimalLength(Integer reportDecimalLength) {
		this.reportDecimalLength = reportDecimalLength;
	}

	public String getManageType() {
		return manageType;
	}

	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	public Boolean getIsPrintToolCode() {
		if(isPrintToolCode==null){
			return true;
		}
		return isPrintToolCode;
	}

	public void setIsPrintToolCode(Boolean isPrintToolCode) {
		this.isPrintToolCode = isPrintToolCode;
	}

	public Boolean getIsPrintNo() {
		if(isPrintNo==null){
			return false;
		}
		return isPrintNo;
	}

	public void setIsPrintNo(Boolean isPrintNo) {
		this.isPrintNo = isPrintNo;
	}

	public Integer getPriceBit() {
		if(priceBit==null){
			return 5;
		}
		return priceBit;
	}

	public void setPriceBit(Integer priceBit) {
		this.priceBit = priceBit;
	}

	public Integer getCountBit() {
		if(countBit==null){
			return 5;
		}
		return countBit;
	}

	public void setCountBit(Integer countBit) {
		this.countBit = countBit;
	}

	public Integer getMoneyBit() {
		if(moneyBit==null){
			return 5;
		}
		return moneyBit;
	}

	public void setMoneyBit(Integer moneyBit) {
		this.moneyBit = moneyBit;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getWaste() {
		return waste;
	}

	public void setWaste(Integer waste) {
		this.waste = waste;
	}

	public Integer getUnitWaste() {
		return unitWaste;
	}

	public void setUnitWaste(Integer unitWaste) {
		this.unitWaste = unitWaste;
	}
	
}
