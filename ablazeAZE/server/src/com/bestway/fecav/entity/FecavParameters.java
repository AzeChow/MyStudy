package com.bestway.fecav.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 外汇核销单参数
 * @author Administrator
 *
 */
public class FecavParameters extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 进口白单是否只冲销一次
	 */
	private Boolean impWhiteBillUseOnce = false;

	/**
	 * 进口白单的余额低于多少就不用来冲销
	 */
	private Double impLowestMoney = 0.0;
	
	/**
	 * 核销单管制中数量的小数位
	 */
	private Integer fecavControlDigitsNum=2;

	public Double getImpLowestMoney() {
		return impLowestMoney;
	}

	public void setImpLowestMoney(Double impLowestMoney) {
		this.impLowestMoney = impLowestMoney;
	}

	public Boolean getImpWhiteBillUseOnce() {
		return impWhiteBillUseOnce;
	}

	public void setImpWhiteBillUseOnce(Boolean impWhiteBillUseOnce) {
		this.impWhiteBillUseOnce = impWhiteBillUseOnce;
	}

	public Integer getFecavControlDigitsNum() {
		return fecavControlDigitsNum;
	}

	public void setFecavControlDigitsNum(Integer fecavControlDigitsNum) {
		this.fecavControlDigitsNum = fecavControlDigitsNum;
	}


}
