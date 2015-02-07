package com.bestway.bcus.checkstock.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;

/**
 * 成品明细表
 * 
 * @author hwy
 * @version 4.0
 * @createDate 2014-2-19
 */
public class ECSDeclarationCommInfoExg extends BaseScmEntity{

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
	
	private Integer serialNumber;
	public Integer getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * 成品备案序号
	 */
	private Integer seqNum;
	/**
	 * 版本号
	 */
	private Integer version;
	/**
	 * 报关单号
	 */
	private String customsDeclarationCode;
	/**
	 * 申报日期
	 */
	private Date declarationDate;
	
	/**
	 * 计量单位
	 */
	private String unit;
	/**
	 * 商品名称
	 */
	private String commName;

	/**
	 * 商品规格
	 */
	private String commSpec;
	/**
	 * 商品数量
	 */
	private Double commAmount;
	/**
	 * 商品单价
	 */
	private Double commUnitPrice;
	/**
	 * 总金额
	 */
	private Double totalMoney;
	/**
	 * 进出口类型
	 */
	private Integer impExpType;
	/**
	 * 贸易方式
	 */
	private String tradeMode;
	/**
	 * 进出口标志
	 */
	private Integer impExpFlag;
	
	public Integer getImpExpFlag() {
		return impExpFlag;
	}


	public void setImpExpFlag(Integer impExpFlag) {
		this.impExpFlag = impExpFlag;
	}
	/**
	 * 币制
	 * @return
	 */
	private String currency;
	
	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	/**
	 * 
	 * @return
	 */
	
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


	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}


	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}


	public Date getDeclarationDate() {
		return declarationDate;
	}


	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
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


	public Double getCommAmount() {
		return commAmount;
	}


	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}


	public Double getCommUnitPrice() {
		return commUnitPrice;
	}


	public void setCommUnitPrice(Double commUnitPrice) {
		this.commUnitPrice = commUnitPrice;
	}


	public Double getTotalMoney() {
		return totalMoney;
	}


	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}


	public Integer getImpExpType() {
		return impExpType;
	}


	public void setImpExpType(Integer impExpType) {
		this.impExpType = impExpType;
	}


	public String getTradeMode() {
		return tradeMode;
	}


	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public void init() {
			this.commAmount= 0d;
			this.commUnitPrice=0d;
			this.totalMoney=0d;
	}
}
