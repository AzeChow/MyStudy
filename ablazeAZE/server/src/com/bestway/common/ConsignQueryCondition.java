package com.bestway.common;

import java.io.Serializable;
import java.util.Date;

import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 海关帐--委托外发加工帐
 * 查询条件
 * @author Administrator
 *
 */
public class ConsignQueryCondition implements Serializable{

	/**
	 * 客户
	 */
	private ScmCoc scmCoc = null;
	
	/**
	 * 开始时间
	 */
	private Date startDate = null;
	
	/**
	 * 开始时间
	 */
	private Date endDate = null;
	
	/**
	 * 开始料号
	 */
	private String startPartNo = null;
	
	/**
	 * 结束料号
	 */
	private String endPartNo = null;
	
	/**
	 * 商品名称
	 */
	private String name = null;
	
	/**
	 * 仓库
	 */
	private WareSet wareSet = null;
	
	/**
	 * 是否统计上期结存
	 */
	private Boolean isCompute = null;
	
	/**
	 * 是否统计半成品
	 */
	private Boolean isComputeBan = null;

	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public WareSet getWareSet() {
		return wareSet;
	}

	public void setWareSet(WareSet wareSet) {
		this.wareSet = wareSet;
	}

	public String getStartPartNo() {
		return startPartNo;
	}

	public void setStartPartNo(String startPartNo) {
		if(startPartNo==null || "".equals(startPartNo)){
			return;
		}
		this.startPartNo = startPartNo;
	}

	public String getEndPartNo() {
		return endPartNo;
	}

	public void setEndPartNo(String endPartNo) {
		if(endPartNo==null || "".equals(endPartNo)){
			return;
		}
		this.endPartNo = endPartNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name==null || "".equals(name)){
			return;
		}
		this.name = name;
	}

	public Boolean getIsCompute() {
		return isCompute;
	}

	public void setIsCompute(Boolean isCompute) {
		this.isCompute = isCompute;
	}

	public Boolean getIsComputeBan() {
		return isComputeBan;
	}

	public void setIsComputeBan(Boolean isComputeBan) {
		this.isComputeBan = isComputeBan;
	}
	
}
