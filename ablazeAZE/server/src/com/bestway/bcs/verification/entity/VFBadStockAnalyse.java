package com.bestway.bcs.verification.entity;

/**
 *残次品库存---库存汇总
 * @author lyh
 */
public class VFBadStockAnalyse extends VFBaseStockImg {

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
	 *  @C=库存汇总数量 ==hsAmount
	 * @return
	 */
	private Double hsAmount;
	

    public void init() {
		imgHsAmount = 0.0;
		exgHsAmount = 0.0;
		semiExgHsAmount = 0.0;
	}


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


	public Double getHsAmount() {
		return hsAmount;
	}


	public void setHsAmount(Double hsAmount) {
		this.hsAmount = hsAmount;
	}
	
}
