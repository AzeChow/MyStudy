package com.bestway.bls.entity;

import java.io.Serializable;
/**
 * CollateItem表
 * @author hw
 *
 */
public class TempCollateItem implements Serializable{

	/**
	 * 核销单证类型
	 */
	private String collateItemType=null;
	
	/**
	 * 核销单证号码
	 */
	private String collateItemFormID=null;
	/**
	 * 核销单证中得序号
	 */
	private int collateItemFormGNo;
	/**
	 * 核销数量
	 */
	private Double collateItemFormCount=null;
	/**
	 * 核销申报时间
	 */
	private String collateItemOperTime=null;
	/**
	 * 获取核销单证类型
	 * @return
	 */
	public String getCollateItemType() {
		return collateItemType;
	}
	/**
	 * 设置核销单证类型
	 * @param collateItemType
	 */
	public void setCollateItemType(String collateItemType) {
		this.collateItemType = collateItemType;
	}
	/**
	 * 获取核销单证号码
	 * @return
	 */
	public String getCollateItemFormID() {
		return collateItemFormID;
	}
	/**
	 * 设置核销单证号码
	 * @param collateItemFormID
	 */
	public void setCollateItemFormID(String collateItemFormID) {
		this.collateItemFormID = collateItemFormID;
	}
	/**
	 * 获取核销单证中得序号
	 * @return
	 */
	public int getCollateItemFormGNo() {
		return collateItemFormGNo;
	}
	/**
	 * 设置核销单证中得序号
	 * @param collateItemFormGNo
	 */
	public void setCollateItemFormGNo(int collateItemFormGNo) {
		this.collateItemFormGNo = collateItemFormGNo;
	}
	/**
	 * 获取核销数量
	 * @return
	 */
	public Double getCollateItemFormCount() {
		return collateItemFormCount;
	}
	/**
	 * 设置核销数量
	 * @param collateItemFormCount
	 */
	public void setCollateItemFormCount(Double collateItemFormCount) {
		this.collateItemFormCount = collateItemFormCount;
	}
	/**
	 * 获取核销申报时间
	 * @return
	 */
	public String getCollateItemOperTime() {
		return collateItemOperTime;
	}
	/**
	 * 设置核销申报时间
	 * @param collateItemOperTime
	 */
	public void setCollateItemOperTime(String collateItemOperTime) {
		this.collateItemOperTime = collateItemOperTime;
	}
}
