package com.bestway.bcus.checkstock.entity;

import com.bestway.common.BaseScmEntity;

/**
 *库存外发---库存汇总
 * @author lyh
 */
public class ECSStockOutSendAnalyse extends ECSBaseResolve {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * @A=原材料折算报关数量
	 */
	private Double imgHsAmount;
	
	/**
	 * @B=成品折算报关数量
	 */
	private Double exgHsAmount;
	
	/**
	 * @C=半成品折算报关数量
	 */
	private Double semiExgHsAmount;
	
	
	/**
	 *  @D=库存汇总数量 ==hsAmount
	 * @return
	 */
	
	
	public Double getImgHsAmount() {
		return imgHsAmount;
	}


	public void setImgHsAmount(Double imgHsAmount) {
		this.imgHsAmount = imgHsAmount;
	}


	public Double getExgHsAmount() {
		return exgHsAmount;
	}


	public void setExgHsAmount(Double exgHsAmount) {
		this.exgHsAmount = exgHsAmount;
	}


	public Double getSemiExgHsAmount() {
		return semiExgHsAmount;
	}


	public void setSemiExgHsAmount(Double semiExgHsAmount) {
		this.semiExgHsAmount = semiExgHsAmount;
	}


	public void init() {
		imgHsAmount = 0.0;
		exgHsAmount = 0.0;
		semiExgHsAmount=0.0;
	}
	
}
