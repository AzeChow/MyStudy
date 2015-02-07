/*
 * Created on 2005-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.fecav.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Curr;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempBillOfExchangeForFecav implements Serializable {

	/**
	 * 汇票ID
	 */
	private String billOfExchangeId;

	/**
	 * 汇票号码
	 */
	private String billOfExchangeCode;

	/**
	 * 结汇日期
	 */
	private Date endDate;
	
	/**
	 * 币别
	 */
	private Curr curr;
	
	/**
	 * 汇率
	 */
	private Double exchangeRate;
	
	/**
	 * 结汇金额
	 */
	private Double exchangeMoney;

	/**
	 * 冲销金额
	 */
	private Double strikeMoney;

	/**
	 * 剩余金额
	 */
	private Double remainMoney;
	
	/**
	 * 折美元
	 */
	private Double converUSDMoney;

	/**
	 * 获得汇票号码
	 * @return 汇票号码
	 */
	public String getBillOfExchangeCode() {
		return billOfExchangeCode;
	}

	/**
	 * 填写汇票号码
	 * @param billOfExchangeCode 汇票号码
	 */
	public void setBillOfExchangeCode(String billOfExchangeCode) {
		this.billOfExchangeCode = billOfExchangeCode;
	}

	/**
	 * 获得汇票ID
	 * @return 汇票ID
	 */
	public String getBillOfExchangeId() {
		return billOfExchangeId;
	}

	/**
	 * 生成汇票ID
	 * @param billOfExchangeId 汇票ID
	 */
	public void setBillOfExchangeId(String billOfExchangeId) {
		this.billOfExchangeId = billOfExchangeId;
	}

	/**
	 * 获得结汇日期
	 * @return 结汇日期
	 */
	public Date getEndDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (endDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(endDate));
		}
		return endDate;
	}

	/**
	 * 设置结汇日期
	 * @param endDate 结汇日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获得结汇金额
	 * @return 结汇金额
	 */
	public Double getExchangeMoney() {
		return exchangeMoney;
	}

	/**
	 * 设置结汇金额
	 * @param exchangeMoney 结汇金额
	 */
	public void setExchangeMoney(Double exchangeMoney) {
		this.exchangeMoney = exchangeMoney;
	}

	/**
	 * 获得剩余金额
	 * @return 剩余金额
	 */
	public Double getRemainMoney() {
		return remainMoney;
	}

	/**
	 * 设置剩余金额
	 * @param remainMoney 剩余金额
	 */
	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
	}

	/**
	 * 获得冲销金额
	 * @return 冲销金额
	 */
	public Double getStrikeMoney() {
		return strikeMoney;
	}

	/**
	 * 设置冲销金额
	 * @param strikeMoney 冲销金额
	 */
	public void setStrikeMoney(Double strikeMoney) {
		this.strikeMoney = strikeMoney;
	}

	/**
	 * 获得币别
	 * @return 币别
	 */
	public Curr getCurr() {
		return curr;
	}

	/**
	 * 设置币别
	 * @param curr 币别
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	/**
	 * 取得汇率
	 * @return 汇率
	 */
	public Double getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * 设置汇率
	 * @param exchangeRate 汇率
	 */
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Double getConverUSDMoney() {
		return converUSDMoney;
	}

	public void setConverUSDMoney(Double converUSDMoney) {
		this.converUSDMoney = converUSDMoney;
	}

}
