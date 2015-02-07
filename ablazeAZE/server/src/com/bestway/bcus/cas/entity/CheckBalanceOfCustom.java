package com.bestway.bcus.cas.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 检查平衡表（编码级导入）
 * @author hcl
 * check by hcl 2011-04-02
 */
public class CheckBalanceOfCustom extends BaseScmEntity {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -5336295463587005341L;

	/**
	 * 库存类别
	 * 
	 * 料件(只有料件)0;
	 * 成品(只有成品)1;
	 * 半成品（只有半成品)2;
	 * 在产品(可有料件、成品、半成品)3;
	 * 外发加工(可有料件、成品、半成品)4;
	 * 残次品(可有料件、成品、半成品)5;
	 * 边角料6
	 */
	private String kcType; 

	/**
	 * 盘点时间
	 */
	private Date checkDate;
	
	/**
	 * 盘点库存数
	 */
	private Double checkAmount;
	
	/**
	 * 报关名称
	 */
	private String name = "";
	
	/**
	 * 报关规格
	 */
	private String spec = "";
	
	/**
	 * 报关单位名称
	 */
	private String unitName = "";
	
	/**
	 * 仓库
	 */
	private WareSet wareSet;
	
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 获取库存类别
	 */
	public String getKcType() {
		return kcType;
	}
	/**
	 * 设置库存类别
	 */
	public void setKcType(String kcType) {
		this.kcType = kcType;
	}
	/**
	 * 获取盘点时间
	 */
	public Date getCheckDate() {
		return checkDate;
	}
	/**
	 * 设置盘点时间
	 */
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	/**
	 * 获取盘点库存数
	 */
	public Double getCheckAmount() {
		return checkAmount;
	}
	/**
	 * 设置盘点库存数
	 */
	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}
	/**
	 * 获取报关名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置报关名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取报关规格
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * 设置报关规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	/**
	 * 获取报关单位
	 */
	public String getUnitName() {
		return unitName;
	}
	/**
	 * 设置报关单位
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	/**
	 * 获取备注
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取仓库
	 */
	public WareSet getWareSet() {
		return wareSet;
	}
	/**
	 * 设置仓库
	 */
	public void setWareSet(WareSet wareSet) {
		this.wareSet = wareSet;
	}
	
	
}