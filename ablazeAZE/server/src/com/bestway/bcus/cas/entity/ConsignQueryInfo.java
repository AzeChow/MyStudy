/*
 * Created on 2004-9-22
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 委外的进出口商品信息
 */
public class ConsignQueryInfo implements Serializable {
	//子件部分
	
	/**
	 * 工厂料号
	 */
	private String ptPart;
	
	/**
	 * 商品名称
	 */
	private String ptName;

	/**
	 * 规格型号
	 */
	private String ptSpec;
	
	/**
	 * 进出仓日期
	 */
	private Date inOutDate;
	
	/**
	 * 单据类型
	 */
	private String billType;
	
	//进仓
	/**
	 * 凭证号
	 */
	private String pingZhenNoOfIn;
	
	/**
	 * 数量
	 */
	private Double numOfIn; 
	
	//出仓
	/**
	 * 凭证号
	 */
	private String pingZhenNoOfOut;
	
	/**
	 * 出仓料号
	 */
	private String ptPartOfOut;
	
	/**
	 * 数量
	 */
	private Double numOfOut; 
	
	/**
	 * 折算数量
	 */
	private Double numOfZheSuan;
	
	/**
	 * 结存
	 */
	private Double jieCun;
	
	/**
	 * 制单号
	 */
	private String zhiDanHao;
	
	/**
	 * 出送货商家名称 
	 */
	private String customerName;
	
	/**
	 * 商品序号
	 */
	private String ptNo;
	
	/**
	 * 工厂单位
	 */
	private String unitName;
	
	/**
	 * 仓库
	 */
	private String storeName;
	
	/**
	 * 备注
	 */
	private String memo;

	public String getPtPart() {
		return ptPart;
	}

	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}


	public Date getInOutDate() {
		return inOutDate;
	}

	public void setInOutDate(Date inOutDate) {
		this.inOutDate = inOutDate;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getPingZhenNoOfIn() {
		return pingZhenNoOfIn;
	}

	public void setPingZhenNoOfIn(String pingZhenNoOfIn) {
		this.pingZhenNoOfIn = pingZhenNoOfIn;
	}


	public String getPingZhenNoOfOut() {
		return pingZhenNoOfOut;
	}

	public void setPingZhenNoOfOut(String pingZhenNoOfOut) {
		this.pingZhenNoOfOut = pingZhenNoOfOut;
	}

	public String getPtPartOfOut() {
		return ptPartOfOut;
	}

	public void setPtPartOfOut(String ptPartOfOut) {
		this.ptPartOfOut = ptPartOfOut;
	}


	public String getZhiDanHao() {
		return zhiDanHao;
	}

	public void setZhiDanHao(String zhiDanHao) {
		this.zhiDanHao = zhiDanHao;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Double getNumOfIn() {
		return numOfIn;
	}

	public void setNumOfIn(Double numOfIn) {
		this.numOfIn = numOfIn;
	}

	public Double getNumOfOut() {
		return numOfOut;
	}

	public void setNumOfOut(Double numOfOut) {
		this.numOfOut = numOfOut;
	}

	public Double getNumOfZheSuan() {
		return numOfZheSuan;
	}

	public void setNumOfZheSuan(Double numOfZheSuan) {
		this.numOfZheSuan = numOfZheSuan;
	}

	public Double getJieCun() {
		return jieCun;
	}

	public void setJieCun(Double jieCun) {
		this.jieCun = jieCun;
	}
	
	
}
