/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放报核每月平均单价临时Entity
 * 
 * @author Administrator
 */
public class TempCancelImgAvgPrice extends BaseScmEntity implements Cloneable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 料件序号
	 */
	private String seqNum = null;

	/**
	 * 临时字段aa
	 */
	private Double LeftOverImgNum = null;

	/**
	 * 临时字段bb
	 */
	private Double LeftOverImgMoney = null;
	/**
	 * 临时字段zchy
	 */
	private Double impNum = null;
	/**
	 * 临时字段zjckhy
	 */
	private Double impMoney = null;
	
	/**
	 * 临时字段zchy
	 */
	private Double useNum = null;
	/**
	 * 临时字段zjckhy
	 */
	private Double useMoney = null;
	/**
	 * 临时字段fgfchy
	 */
	private Double leaveMoney = null;
	/**
	 * 临时字段tcfghy
	 */
	private Double leaveNum = null;

	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public Double getLeftOverImgNum() {
		return LeftOverImgNum;
	}
	public void setLeftOverImgNum(Double leftOverImgNum) {
		LeftOverImgNum = leftOverImgNum;
	}
	public Double getLeftOverImgMoney() {
		return LeftOverImgMoney;
	}
	public void setLeftOverImgMoney(Double leftOverImgMoney) {
		LeftOverImgMoney = leftOverImgMoney;
	}
	public Double getUseNum() {
		return useNum;
	}
	public void setUseNum(Double useNum) {
		this.useNum = useNum;
	}
	public Double getUseMoney() {
		return useMoney;
	}
	public void setUseMoney(Double useMoney) {
		this.useMoney = useMoney;
	}
	public Double getLeaveMoney() {
		return leaveMoney;
	}
	public void setLeaveMoney(Double leaveMoney) {
		this.leaveMoney = leaveMoney;
	}
	public Double getLeaveNum() {
		return leaveNum;
	}
	public void setLeaveNum(Double leaveNum) {
		this.leaveNum = leaveNum;
	}
	public Double getImpNum() {
		return impNum;
	}
	public void setImpNum(Double impNum) {
		this.impNum = impNum;
	}
	public Double getImpMoney() {
		return impMoney;
	}
	public void setImpMoney(Double impMoney) {
		this.impMoney = impMoney;
	}
	

}
