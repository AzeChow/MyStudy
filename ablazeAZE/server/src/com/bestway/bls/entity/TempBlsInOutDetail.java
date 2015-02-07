package com.bestway.bls.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.materialbase.entity.Materiel;

public class TempBlsInOutDetail implements Serializable {

	/**
	 * 错误信息
	 */
	private String errinfo = null;
	/**
	 * 进出仓单据表头
	 */
	private BlsInOutStockBill bsb = null;
	/**
	 * 原产国
	 */
	private Country originCountry = null;
	/**
	 * 企业内部货号
	 */
	private Materiel partNo = null;
	/**
	 * 数量
	 */
	private Double detailQty = null;
	/**
	 * 申报单价
	 */
	private Double unitPrice = null;

	/**
	 * 币值
	 */
	private Curr curr = null;
	/**
	 * 毛重
	 */
	private Double grossWeight = null;
	/**
	 * 净重
	 */
	private Double netWeight = null;
	/**
	 * 件数
	 */
	private Integer packCount = null;

	/**
	 * 已全转仓单
	 */
	private Boolean isStockBill = false;

	/**
	 * 获取错误信息
	 * 
	 * @return
	 */
	public String getErrinfo() {
		return errinfo;
	}

	/**
	 * 设置错误信息
	 * 
	 * @param errinfo
	 */
	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	/**
	 * GNo 归并序号(Integer)
	 * 
	 * @uml.property name="gNo"
	 */
	private Integer seqNum;

	/**
	 * 获取 归并序号(Integer)
	 * 
	 * @return
	 */
	public Integer getSeqNum() {
		return seqNum;
	}

	/**
	 * 设置 归并序号(Integer)
	 * 
	 * @param seqNum
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * 获取已转仓单的(Boolean)型标志位
	 * 
	 * @return
	 */
	public Boolean getIsStockBill() {
		return isStockBill;
	}

	/**
	 * 设置已转仓单的(Boolean)型标志位
	 * 
	 * @param isStockBill
	 */
	public void setIsStockBill(Boolean isStockBill) {
		this.isStockBill = isStockBill;
	}

	/**
	 * 得到进出仓单据表头
	 * 
	 * @return 进出仓单据表头
	 */
	public BlsInOutStockBill getBsb() {
		return bsb;
	}

	/**
	 * 设置进出仓单据表头
	 * 
	 * @param packCount
	 */
	public void setBsb(BlsInOutStockBill bsb) {
		this.bsb = bsb;
	}

	/**
	 * 得到原产国
	 * 
	 * @return 原产国
	 */
	public Country getOriginCountry() {
		return originCountry;
	}

	/**
	 * 设置原产国
	 * 
	 * @param originCountry
	 */
	public void setOriginCountry(Country originCountry) {
		this.originCountry = originCountry;
	}

	/**
	 * 得到企业内部货号
	 * 
	 * @return 企业内部货号
	 */
	public Materiel getPartNo() {
		return partNo;
	}

	/**
	 * 设置企业内部货号
	 * 
	 * @param partNo
	 */
	public void setPartNo(Materiel partNo) {
		this.partNo = partNo;
	}

	/**
	 * 得到数量
	 * 
	 * @return 数量
	 */
	public Double getDetailQty() {
		return detailQty;
	}

	/**
	 * 设置数量
	 * 
	 * @param detailQty
	 */
	public void setDetailQty(Double detailQty) {
		this.detailQty = detailQty;
	}

	/**
	 * 得到申报单价
	 * 
	 * @return 申报单价
	 */
	public Double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * 设置申报单价
	 * 
	 * @param unitPrice
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * 得到币制
	 * 
	 * @return 币制
	 */
	public Curr getCurr() {
		return curr;
	}

	/**
	 * 设置币制
	 * 
	 * @param curr
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	/**
	 * 得到毛重
	 * 
	 * @return 毛重
	 */
	public Double getGrossWeight() {
		return grossWeight;
	}

	/**
	 * 设置毛重
	 * 
	 * @param grossWeight
	 */
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	/**
	 * 得到净重
	 * 
	 * @return 净重
	 */
	public Double getNetWeight() {
		return netWeight;
	}

	/**
	 * 设置净重
	 * 
	 * @param netWeight
	 */
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	/**
	 * 得到件数
	 * 
	 * @return 件数
	 */
	public Integer getPackCount() {
		return packCount;
	}

	/**
	 * 设置件数
	 * 
	 * @param packCount
	 */
	public void setPackCount(Integer packCount) {
		this.packCount = packCount;
	}

}
