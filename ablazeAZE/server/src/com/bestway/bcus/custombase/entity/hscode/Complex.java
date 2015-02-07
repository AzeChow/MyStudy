/*
 * Created on 2004-6-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity.hscode;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoi;

/**
 * 存放自用商品编码资料
 */

public class Complex extends BaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 商品编码
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 是否作废
	 */
	private String isOut;

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
	 * 是否有变更
	 */
	private String isChange; // 是否有变更

	/**
	 * 是否选中
	 */
	private Boolean isSelected = false;
	/**
	 * 许可证代码
	 */
	private String ccontrol;

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
	 * @param ccontrol
	 *            许可证代码
	 */
	public void setCcontrol(String ccontrol) {
		this.ccontrol = ccontrol;
	}

	/**
	 * 获取代码
	 * 
	 * @return code 代码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置代码
	 * 
	 * @param code
	 *            代码
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @param firstUnit
	 *            第一单位编码
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
	 * @param highrate
	 *            普通税率
	 */
	public void setHighrate(String highrate) {
		this.highrate = highrate;
	}

	/**
	 * 获取是否作废
	 * 
	 * @return isOut 是否作废
	 */
	public String getIsOut() {
		return isOut;
	}

	/**
	 * 设置是否作废
	 * 
	 * @param isOut
	 *            是否作废
	 */
	public void setIsOut(String isOut) {
		this.isOut = isOut;
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
	 * @param lowrate
	 *            优惠税率
	 */
	public void setLowrate(String lowrate) {
		this.lowrate = lowrate;
	}

	/**
	 * 获取名称
	 * 
	 * @return name 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @param note
	 *            备注
	 */
	public void setNote(String note) {
		this.note = note;
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
	 * @param secondUnit
	 *            第二单位编码
	 */
	public void setSecondUnit(Unit secondUnit) {
		this.secondUnit = secondUnit;
	}

	public String getIsChange() {
		return isChange;
	}

	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
}