package com.bestway.fecav.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 银行汇票
 * @author wuzepei
 *  table="billofexchange"
 */
public class BillOfExchange extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
    
	/**
	 * 汇票号码
	 */
	private String code;

	/**
	 * 结汇日期
	 */
	private Date endDate;

	/**
	 * 结汇银行
	 */
	private String bank;

	/**
	 * 银行帐号
	 */
	private String bankAccounts;

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
	 * 可冲销金额
	 */
	private Double canStrikeMoney;

	/**
	 * 操作员
	 */
	private String operator;

	/**
	 * 操作日期
	 */
	private Date operateDate;
	
	/**
	 * 折美元
	 */
	private Double converUSDMoney;

	/**
	 * 取得结汇银行
	 * @return 结汇银行
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * 设置结汇银行
	 * @param bank 结汇银行
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * 取得银行帐号
	 * @return  银行帐号
	 */
	public String getBankAccounts() {
		return bankAccounts;
	}

	/**
	 * 设置银行帐号
	 * @param bankAccounts 银行帐号
	 */
	public void setBankAccounts(String bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	/**
	 * 取得可冲销金额
	 * @return 可冲销金额
	 */
	public Double getCanStrikeMoney() {
		return canStrikeMoney;
	}

	/**
	 * 设置可冲销金额
	 * @param canStrikeMoney 可冲销金额
	 */
	public void setCanStrikeMoney(Double canStrikeMoney) {
		this.canStrikeMoney = canStrikeMoney;
	}

	/**
	 * 取得汇票号码
	 * @return 汇票号码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置汇票号码
	 * @param code 汇票号码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 取得币别
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
	 * 取得结汇日期
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
	 * 取得结汇金额
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

	/**
	 * 取得操作日期
	 * @return 操作日期
	 */
	public Date getOperateDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (operateDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(operateDate));
		}
		return operateDate;
	}

	/**
	 * 设置操作日期
	 * @param operateDate 操作日期
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * 取得操作员
	 * @return 操作员
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 设置操作员
	 * @param operator 操作员
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Double getConverUSDMoney() {
		return converUSDMoney;
	}

	public void setConverUSDMoney(Double converUSDMoney) {
		this.converUSDMoney = converUSDMoney;
	}
}
