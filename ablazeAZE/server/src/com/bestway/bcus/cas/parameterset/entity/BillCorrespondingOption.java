/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.parameterset.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 单据对应选项
 */
public class BillCorrespondingOption extends BaseScmEntity {
	private static final long	serialVersionUID		= CommonUtils
																.getSerialVersionUID();
	/**
	 *是否根据对应报关单计算单据的合同单价
	 */
	private Boolean				isCalculatePrice		= false;
	 /**
	 *是否跨年度对应
	 */
	private Boolean				isOverYear				= false;
	/**
	 *单据对应是否需确认
	 */
	private Boolean				isBillCorrespondingAffirm	= false;
	/**
	 * 是否名称匹配
	 */
	private Boolean isNoNameMatching = false;
	/**
	 * 是否部分名称匹配
	 */
	private Boolean isNamePartMatching = false;
	/**
	 * 是否规格匹配
	 */
	private Boolean isNoSpecMatching = false;
	/**
	 * 是否单位匹配
	 */
	private Boolean isNoUnitMatching = null;

	
	public Boolean getIsNamePartMatching() {
		if(isNamePartMatching==null){
			return false;
		}
		return isNamePartMatching;
	}

	public void setIsNamePartMatching(Boolean isNamePartMatching) {
		this.isNamePartMatching = isNamePartMatching;
	}

	public Boolean getIsNoNameMatching() {
		if(isNoNameMatching==null){
			return false;
		}
		return isNoNameMatching;
	}

	public void setIsNoNameMatching(Boolean isNoNameMatching) {
		this.isNoNameMatching = isNoNameMatching;
	}

	public Boolean getIsNoSpecMatching() {
		if(isNoSpecMatching==null){
			return false;
		}
		return isNoSpecMatching;
	}

	public void setIsNoSpecMatching(Boolean isNoSpecMatching) {
		this.isNoSpecMatching = isNoSpecMatching;
	}

	public Boolean getIsNoUnitMatching() {
		if(isNoUnitMatching==null){
			return false;
		}
		return isNoUnitMatching;
	}

	public void setIsNoUnitMatching(Boolean isNoUnitMatching) {
		this.isNoUnitMatching = isNoUnitMatching;
	}

	/**
	 * 判断单据对应确认确认标志  要确认为true 否则为false
	 * @return  isBillCorrespondingAffirm  单据对应需确认
	 */
	public Boolean getIsBillCorrespondingAffirm() {
		if(isBillCorrespondingAffirm==null){
			return false;
		}
		return isBillCorrespondingAffirm;
	}

	/**
	 * 设置单据对应确认标志
	 * @param  isBillCorrespondingAffirm 单据对应需确认
	 */
	public void setIsBillCorrespondingAffirm(Boolean isBillCorrespondingAffirm) {
		this.isBillCorrespondingAffirm = isBillCorrespondingAffirm;
	}

	/**
	 * 判断是否根据对应报关单计算单据的合同单价
	 * @return isCalculatePrice  是否根据对应报关单计算单据的合同单价
	 */
	public Boolean getIsCalculatePrice() {
		if(isCalculatePrice==null){
			return false;
		}
		return isCalculatePrice;
	}

	/**
	 * 设置是否根据对应报关单计算单据的合同单价标志
	 * @param   isCalculatePrice   是否根据对应报关单计算单据的合同单价
	 */
	public void setIsCalculatePrice(Boolean isCalculatePrice) {
		this.isCalculatePrice = isCalculatePrice;
	}

	/**
	 * 判断是否跨年度
	 * @return isOverYear 跨年度
	 */
	public Boolean getIsOverYear() {
		if(isOverYear==null){
			return false;
		}
		return isOverYear;
	}

	/**
	 * 设置是否跨年度标志
	 * @param isOverYear 是否跨年度
	 */
	public void setIsOverYear(Boolean isOverYear) {
		this.isOverYear = isOverYear;
	}
	
	

}
