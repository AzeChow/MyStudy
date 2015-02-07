/*
 * Created on 2005-6-22
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.fixtureonorder.entity;

import java.io.Serializable;

import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * 存放统计报表中的进口料件报关登记表或出口成品报关登记表资料
 * 
 * @author fhz
 *
 */
public class ImpExpCustomsDeclarationCommInfo extends
		BaseCustomsDeclarationCommInfo implements Serializable, Comparable {

	/**
	 * 数量进口数量累计
	 */
	private Double commAddInAmount;
	
	/**
	 * 数量出口数量累计
	 */
	private Double commAddOutAmount;

	/**
	 * 总金额(美元)
	 */
	private Double sumPrice;

	/**
	 * 加工费单价
	 */
	private Double processUnitPrice;
	
	/**
	 * 报关单份数
	 */
	private Integer bgdNum;

	
	/**
	 * 总价值
	 */
	
	private Double sumPriceFg;
	
	/**
	 * 商品名称＋规格
	 */
	private String commNameAndSpec;
	
	/**
     *  设备类型 0.为不作价设备，1.为借用设备
     */
    private Integer      fixKind;
	
	
	
	/**
	 * 获取数量进口数量累计
	 * 
	 * @return commAddUpAmount 数量进口数量累计
	 */
	public Double getCommAddInAmount() {
		return commAddInAmount;
	}

	/**
	 * 设置数量进口数量累计
	 * 
	 * @param commAddUpAmount
	 *            数量进口数量累计
	 */
	public void setCommAddInAmount(Double commAddUpAmount) {
		this.commAddInAmount = commAddUpAmount;
	}

	/**
	 * 获取加工费单价
	 * 
	 * @return processUnitPrice 加工费单价
	 */
	public Double getProcessUnitPrice() {
		return processUnitPrice;
	}

	/**
	 * 设置加工费单价
	 * 
	 * @param processUnitPrice
	 *            加工费单价
	 */
	public void setProcessUnitPrice(Double processUnitPrice) {
		this.processUnitPrice = processUnitPrice;
	}

	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}

	/**
	 * 重compareTo方法，用serialNo（序号）进行比较
	 * 
	 * @param o
	 *            要比较的
	 * @return int 比o小时返回-1，相等时返回0，大于时返回1
	 */
	public int compareTo(Object o) {
		if (o == null) {
			return 1;
		}
		ImpExpCustomsDeclarationCommInfo stat = (ImpExpCustomsDeclarationCommInfo) o;
		if (this.getCommSerialNo() == null && stat.getCommSerialNo() == null) {
			return 0;
		}
		if (this.getCommSerialNo() == null && stat.getCommSerialNo() != null) {
			return -1;
		}
		if (this.getCommSerialNo() != null && stat.getCommSerialNo() == null) {
			return 1;
		}
		int serialNo1 = Integer.valueOf(this.getCommSerialNo());
		int serialNo2 = Integer.valueOf(stat.getCommSerialNo());
		if ((serialNo1 - serialNo2) > 0) {
			return 1;
		} else if ((serialNo1 - serialNo2) == 0) {
			return 0;
		} else if ((serialNo1 - serialNo2) < 0) {
			return -1;
		}
		return 0;
	}

	public Integer getBgdNum() {
		return bgdNum;
	}

	public void setBgdNum(Integer bgdNum) {
		this.bgdNum = bgdNum;
	}

	public Double getSumPriceFg() {
		return sumPriceFg;
	}

	public void setSumPriceFg(Double sumPriceFg) {
		this.sumPriceFg = sumPriceFg;
	}

	/**
	 * @return the commNameAndSpec
	 */
	public String getCommNameAndSpec() {
		return commNameAndSpec;
	}

	/**
	 * @param commNameAndSpec the commNameAndSpec to set
	 */
	public void setCommNameAndSpec(String commNameAndSpec) {
		this.commNameAndSpec = commNameAndSpec;
	}

	/**
	 * @return the commAddOutAmount
	 */
	public Double getCommAddOutAmount() {
		return commAddOutAmount;
	}

	/**
	 * @param commAddOutAmount the commAddOutAmount to set
	 */
	public void setCommAddOutAmount(Double commAddOutAmount) {
		this.commAddOutAmount = commAddOutAmount;
	}

	/**
	 * @return the fixKind
	 */
	public Integer getFixKind() {
		return fixKind;
	}

	/**
	 * @param fixKind the fixKind to set
	 */
	public void setFixKind(Integer fixKind) {
		this.fixKind = fixKind;
	}
}
