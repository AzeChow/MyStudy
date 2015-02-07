package com.bestway.bcs.bcsinnermerge.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;

/**
 *  工厂成品对应报关成品比例
 * @author chensir
 *
 */
public class BcsProductScaleInfo  extends BaseScmEntity {
	
	/**
	 * 报关单耗成品 
	 */
	private BcsCustomsBomExg bcsCustomsBomExg = null;
	
	/**
	 * 成品料号
	 */
	private String parentNo;

	/**
	 * 分配数量
	 */
	private Double amount;
	
	/**
	 * 对应比例
	 */
	private Double scale;
	
	/**
	 * 物料与报关商品的折算系数
	 */
	private Double unitConvert;
	
	/**
	 * 版本号
	 */
	private String version;
	
	/**
	 * 结束有效期
	 */
	private Date endDate; 
	
	

	public BcsCustomsBomExg getBcsCustomsBomExg() {
		return bcsCustomsBomExg;
	}

	public void setBcsCustomsBomExg(BcsCustomsBomExg bcsCustomsBomExg) {
		this.bcsCustomsBomExg = bcsCustomsBomExg;
	}

	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getScale() {
		return scale;
	}

	public void setScale(Double scale) {
		this.scale = scale;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	

}
