package com.bestway.bcus.checkstock.entity;

import com.bestway.common.BaseScmEntity;

/**
 *在制品库存---库存汇总
 * @author lyh
 */
public class ECSFinishingStockAnalyse extends ECSBaseResolve {

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
	 *  @C=库存汇总数量 ==hsAmount
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


	public void init() {
		imgHsAmount = 0.0;
		exgHsAmount = 0.0;
	}
	
}
