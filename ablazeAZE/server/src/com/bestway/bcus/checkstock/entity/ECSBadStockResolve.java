package com.bestway.bcus.checkstock.entity;

import com.bestway.common.BaseScmEntity;

/**
 *残次品库存---库存汇总
 * @author lyh
 */
public class ECSBadStockResolve extends ECSBaseResolve {

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
	private Double finishedHsAmount;
	
	/**
	 * @C=半成品折算报关数量
	 */
	private Double semiFinishedHsAmount;
	
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


	public Double getFinishedHsAmount() {
		return finishedHsAmount;
	}


	public void setFinishedHsAmount(Double finishedHsAmount) {
		this.finishedHsAmount = finishedHsAmount;
	}


	public Double getSemiFinishedHsAmount() {
		return semiFinishedHsAmount;
	}


	public void setSemiFinishedHsAmount(Double semiFinishedHsAmount) {
		this.semiFinishedHsAmount = semiFinishedHsAmount;
	}


	public void init() {
		imgHsAmount = 0.0;
		finishedHsAmount = 0.0;
		semiFinishedHsAmount = 0.0;
	}
	
}
