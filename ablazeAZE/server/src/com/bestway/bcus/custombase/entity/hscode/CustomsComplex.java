/*
 * Created on 2004-6-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity.hscode;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;

/**
 * 存放海关商品编码资料
 */

public class CustomsComplex extends CustomBaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 备注
	 */
	private String note; 

	/**
	 * 第一单位编码
	 */
	private Unit firstUnit; 

	/**
	 * 第二单位编码
	 */
	private Unit secondUnit; 

	/**
	 * 优惠税率
	 */
	private String lowrate;

	/**
	 * 普通税率
	 */
	private String highrate;

	/**
	 * 许可证代码
	 */
	private  String ccontrol;
	
	/**
	 * 消费税率
	 */
	private  String regRate; 
	
	/**
	 * 出口税率
	 */
	private String outRate; 
	
	/**
	 * 增值税率
	 */
	private String taxRate; 

    /**
	 * 许可证代码注解
	 */
	private String licExp ;
    
	/**
	 * 获取许可证代码注解
	 * 
	 * @return licExp 许可证代码注解
	 */
	public String getLicExp() {
		return licExp;
	}

	/**
	 * 设置许可证代码注解
	 * 
	 * @param licExp 许可证代码注解
	 */
	public void setLicExp(String licExp) {
		this.licExp = licExp;
	}

	/**
	 * 获取第一单位编码
	 * 
	 * @return firstUnit 第一单位编码
	 */
	public Unit getFirstUnit() {
		return firstUnit;
	}

	/**
	 * 设置第一单位编码
	 * 
	 * @param firstUnit 第一单位编码
	 */
	public void setFirstUnit(Unit firstUnit) {
		this.firstUnit = firstUnit;
	}

	/**
	 * 获取普通税率
	 * 
	 * @return highrate 普通税率
	 */
	public String getHighrate() {
		return highrate;
	}

	/**
	 * 设置普通税率
	 * 
	 * @param highrate 普通税率
	 */
	public void setHighrate(String highrate) {
		this.highrate = highrate;
	}

	/**
	 * 获取优惠税率
	 * 
	 * @return lowrate 优惠税率
	 */
	public String getLowrate() {
		return lowrate;
	}

	/**
	 * 设置优惠税率
	 * 
	 * @param lowrate 优惠税率
	 */
	public void setLowrate(String lowrate) {
		this.lowrate = lowrate;
	}

	/**
	 * 获取备注
	 * 
	 * @return note 备注
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置备注
	 * 
	 * @param note 备注
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取出口税率
	 * 
	 * @return outRate 出口税率
	 */
	public String getOutRate() {
		return outRate;
	}

	/**
	 * 设置出口税率
	 * 
	 * @param outRate 出口税率
	 */
	public void setOutRate(String outRate) {
		this.outRate = outRate;
	}

	/**
	 * 获取消费税率
	 * 
	 * @return regRate 消费税率
	 */
	public String getRegRate() {
		return regRate;
	}

	/**
	 * 设置消费税率
	 * 
	 * @param regRate 消费税率
	 */
	public void setRegRate(String regRate) {
		this.regRate = regRate;
	}

	/**
	 * 获取第二单位编码
	 * 
	 * @return secondUnit 第二单位编码
	 */
	public Unit getSecondUnit() {
		return secondUnit;
	}

	/**
	 * 设置第二单位编码
	 * 
	 * @param secondUnit 第二单位编码
	 */
	public void setSecondUnit(Unit secondUnit) {
		this.secondUnit = secondUnit;
	}

	/**
	 * 获取增值税率
	 * 
	 * @return taxRate 增值税率
	 */
	public String getTaxRate() {
		return taxRate;
	}

	/**
	 * 设置增值税率
	 * 
	 * @param taxRate 增值税率
	 */
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	/**
	 * 获取许可证代码
	 * 
	 * @return ccontrol 许可证代码
	 */
	public String getCcontrol() {
		return ccontrol;
	}

	/**
	 * 设置许可证代码
	 * 
	 * @param ccontrol 许可证代码
	 */
	public void setCcontrol(String ccontrol) {
		this.ccontrol = ccontrol;
	}
}

