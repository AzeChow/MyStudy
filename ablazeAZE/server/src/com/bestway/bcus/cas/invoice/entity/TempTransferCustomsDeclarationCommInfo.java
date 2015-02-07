package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;

import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * 临时存转厂商品信息
 */
public class TempTransferCustomsDeclarationCommInfo extends
		BaseCustomsDeclarationCommInfo implements Serializable, Comparable {

	/**
	 * 数量累计 
	 */
	private Double commAddUpAmount;

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
	 * 合同单价
	 */
	
	private Double contractUnitPrice;
	

	/**
	 * get数量累计 
	 */
	public Double getCommAddUpAmount() {
		return commAddUpAmount;
	}


	/**
	 * set数量累计 
	 */
	public void setCommAddUpAmount(Double commAddUpAmount) {
		this.commAddUpAmount = commAddUpAmount;
	}

	/**
	 * get总金额(美元)
	 */
	public Double getSumPrice() {
		return sumPrice;
	}
	/**
	 * set总金额(美元)
	 */
	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}

	/**
	 * get加工费单价
	 */
	public Double getProcessUnitPrice() {
		return processUnitPrice;
	}

	/**
	 * set加工费单价
	 */
	public void setProcessUnitPrice(Double processUnitPrice) {
		this.processUnitPrice = processUnitPrice;
	}

	/**
	 * get报关单份数
	 */
	public Integer getBgdNum() {
		return bgdNum;
	}

	/**
	 * set报关单份数
	 */
	public void setBgdNum(Integer bgdNum) {
		this.bgdNum = bgdNum;
	}

	/**
	 * get总价值
	 */
	public Double getSumPriceFg() {
		return sumPriceFg;
	}

	/**
	 * set总价值
	 */
	public void setSumPriceFg(Double sumPriceFg) {
		this.sumPriceFg = sumPriceFg;
	}

	/**
	 * get合同单价
	 */
	public Double getContractUnitPrice() {
		return contractUnitPrice;
	}

	/**
	 * set合同单价
	 */
	public void setContractUnitPrice(Double contractUnitPrice) {
		this.contractUnitPrice = contractUnitPrice;
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
		TempTransferCustomsDeclarationCommInfo stat = (TempTransferCustomsDeclarationCommInfo) o;
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

}
