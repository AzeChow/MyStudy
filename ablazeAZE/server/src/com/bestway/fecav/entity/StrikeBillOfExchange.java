package com.bestway.fecav.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 汇票冲销
 * @author 
 * table="strikebillofexchange"
 */
public class StrikeBillOfExchange extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 外汇核销单
	 */
	private FecavBillStrike fecavBillStrike;

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
	 * 币别
	 */
	private Curr curr;
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
	 * 设置汇票号码
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
	 * 设置汇票ID
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
	 * 获得外汇核销单内容
	 * @return 外汇核销单
	 */
	public FecavBillStrike getFecavBillStrike() {
		return fecavBillStrike;
	}

	/**
	 * 设置外汇核销单内容
	 * @param fecavBill 外汇核销单
	 */
	public void setFecavBillStrike(FecavBillStrike fecavBill) {
		this.fecavBillStrike = fecavBill;
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
	 * 获得汇率
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
