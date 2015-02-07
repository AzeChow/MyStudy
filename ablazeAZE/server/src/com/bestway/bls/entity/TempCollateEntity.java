package com.bestway.bls.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * CollateEntity临时表
 * @author hw
 *
 */
public class TempCollateEntity implements Serializable {

	/**
	 * 核销类型
	 */
	private String collateType = null;

	/**
	 * 该商品序号得商品是否核销完成
	 */
	private int isCollateFinished;
	/**
	 * 需要核销数量
	 */
	private Double collateTotalCount = null;
	/**
	 * 已核销数量
	 */
	private Double collatedCount = null;
	/**
	 * 核销项明细
	 */
	private List collateItems = new ArrayList();

	/**
	 * 获取核销类型
	 * 
	 * @return
	 */
	public String getCollateType() {
		return collateType;
	}

	/**
	 * 设置核销类型
	 * 
	 * @param collateType
	 */
	public void setCollateType(String collateType) {
		this.collateType = collateType;
	}

	/**
	 * 获取该商品序号得商品是否核销完成标志位
	 * 
	 * @return
	 */
	public int getIsCollateFinished() {
		return isCollateFinished;
	}

	/**
	 * 设置该商品序号得商品是否核销完成标志位
	 * 
	 * @param isCollateFinished
	 */
	public void setIsCollateFinished(int isCollateFinished) {
		this.isCollateFinished = isCollateFinished;
	}

	/**
	 * 获取需要核销数量
	 * 
	 * @return
	 */
	public Double getCollateTotalCount() {
		return collateTotalCount;
	}

	/**
	 * 设置需要核销数量
	 * 
	 * @param collateTotalCount
	 */
	public void setCollateTotalCount(Double collateTotalCount) {
		this.collateTotalCount = collateTotalCount;
	}

	/**
	 * 获取已核销数量
	 * 
	 * @return
	 */
	public Double getCollatedCount() {
		return collatedCount;
	}

	/**
	 * 设置已核销数量
	 * 
	 * @param collatedCount
	 */
	public void setCollatedCount(Double collatedCount) {
		this.collatedCount = collatedCount;
	}

	/**
	 * 获取核销项明细
	 * 
	 * @return
	 */
	public List getCollateItems() {
		return collateItems;
	}

	/**
	 * 设置核销项明细
	 * 
	 * @param collateItems
	 */
	public void setCollateItems(List collateItems) {
		this.collateItems = collateItems;
	}
}
