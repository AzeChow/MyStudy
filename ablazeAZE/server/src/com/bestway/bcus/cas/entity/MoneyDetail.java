/*
 * Created on 2004-9-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;


import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 现金流量表明细
 */
public class MoneyDetail extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
	 * 现金流量表表头
	 */
	private MoneyMaster moneyMaster;
	/**
	 * 项目1
	 */
	private String item1;        
	/**
	 * 行次1
	 */
	private String times1;       
	/**
	 * 金额1
	 */
	private Double money1;       
	
	/**
	 * 项目2
	 */
	private String item2;        
	/**
	 * 行次2
	 */
	private String times2;       
	/**
	 * 金额2
	 */
	private Double money2;       
	
	/**
	 * 项目3
	 */
	private String item3;        
	/**
	 * 行次3
	 */
	private String times3;       
	/**
	 * 金额3
	 */
	private Double money3;       
	
	/**
	 * 取得项目1
	 * @return item1 项目1.
	 */
	public String getItem1() {
		return item1;
	}
	/**
	 * 设置项目1
	 * @param item1 项目1.
	 */
	public void setItem1(String item1) {
		this.item1 = item1;
	}
	/**
	 * 取得项目2
	 * @return item2 项目2.
	 */
	public String getItem2() {
		return item2;
	}
	/**
	 * 设置项目2
	 * @param item2 项目2.
	 */
	public void setItem2(String item2) {
		this.item2 = item2;
	}
	/**
	 * 取得项目3
	 * @return item3 项目3.
	 */
	public String getItem3() {
		return item3;
	}
	/**
	 * 设置项目3
	 * @param item3 项目3.
	 */
	public void setItem3(String item3) {
		this.item3 = item3;
	}
	/**
	 * 取得金额1
	 * @return money1 金额1.
	 */
	public Double getMoney1() {
		return money1;
	}
	/**
	 * 设置金额1
	 * @param money1 金额1.
	 */
	public void setMoney1(Double money1) {
		this.money1 = money1;
	}
	/**
	 * 取得金额2
	 * @return money2 金额2.
	 */
	public Double getMoney2() {
		return money2;
	}
	/**
	 * 设置金额2
	 * @param money2 The money2 to set.
	 */
	public void setMoney2(Double money2) {
		this.money2 = money2;
	}
	/**
	 * 取得金额3
	 * @return money3 金额3.
	 */
	public Double getMoney3() {
		return money3;
	}
	/**
	 * 设置金额3
	 * @param money3 金额3.
	 */
	public void setMoney3(Double money3) {
		this.money3 = money3;
	}
	/**
	 * 取得行次1
	 * @return times1 行次1.
	 */
	public String getTimes1() {
		return times1;
	}
	/**
	 * 设置行次1
	 * @param times1 行次1.
	 */
	public void setTimes1(String times1) {
		this.times1 = times1;
	}
	/**
	 * 取得行次2
	 * @return times2 行次2.
	 */
	public String getTimes2() {
		return times2;
	}
	/**
	 * 设置行次2
	 * @param times2 行次2.
	 */
	public void setTimes2(String times2) {
		this.times2 = times2;
	}
	/**
	 * 	取得行次3
	 * @return times3 行次3.
	 */
	public String getTimes3() {
		return times3;
	}
	/**
	 * 设置行次3
	 * @param times3 行次3.
	 */
	public void setTimes3(String times3) {
		this.times3 = times3;
	}
	/**
	 * 取得现金流量表表头
	 * @return moneyMaster 现金流量表表头.
	 */
	public MoneyMaster getMoneyMaster() {
		return moneyMaster;
	}
	/**
	 * 设置现金流量表表头
	 * @param moneyMaster 现金流量表表头.
	 */
	public void setMoneyMaster(MoneyMaster moneyMaster) {
		this.moneyMaster = moneyMaster;
	}
}