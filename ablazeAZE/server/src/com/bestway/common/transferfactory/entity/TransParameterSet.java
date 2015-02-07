package com.bestway.common.transferfactory.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 关封参数设置表头
 * @author ower
 *
 */
public class TransParameterSet extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 一个关封是否可以用多次
	 */
	private Boolean customsEnvelopUsedMultiple = false;
	
	/**
	 * 是否自动结案
	 */
	private Boolean isAutoJieAn = false;
	
	/**
	 * 进出货转厂单据单据号可修改
	 */
	private Boolean isNoEdit = false;
	
	/**
	 *  是否允许关封单据超量
	 */
	private Boolean isLimit = false;

	
	public Boolean getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(Boolean isLimit) {
		this.isLimit = isLimit;
	}

	public Boolean getCustomsEnvelopUsedMultiple() {
		return customsEnvelopUsedMultiple;
	}

	public void setCustomsEnvelopUsedMultiple(Boolean customsEnvelopUsedMultiple) {
		this.customsEnvelopUsedMultiple = customsEnvelopUsedMultiple;
	}

	public Boolean getIsAutoJieAn() {
		if(isAutoJieAn==null){
			return false;
		}
		return isAutoJieAn;
	}

	public void setIsAutoJieAn(Boolean isAutoJieAn) {
		this.isAutoJieAn = isAutoJieAn;
	}

	public Boolean getIsNoEdit() {
		if(isNoEdit==null){
			return false;
		}
		return isNoEdit;
	}

	public void setIsNoEdit(Boolean isNoEdit) {
		this.isNoEdit = isNoEdit;
	}
	
	
}
