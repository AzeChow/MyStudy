package com.bestway.bcus.cas.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;

/**
 * 导入临时检查平衡表（编码级导入）
 * @author ower
 *
 */
public class TempOfCheckBalanceOfCustom extends BaseScmEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4726262405160267020L;

	/**
	 * 编码级盘点
	 */
	private CheckBalanceOfCustom balance;
	
	/**
	 * 料号级盘点
	 */
	private CheckBalance checkBalance;
	
	/**
	 * 错误信息
	 */
	private String errinfo;

	public CheckBalanceOfCustom getBalance() {
		return balance;
	}

	public void setBalance(CheckBalanceOfCustom balance) {
		this.balance = balance;
	}


	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	public CheckBalance getCheckBalance() {
		return checkBalance;
	}

	public void setCheckBalance(CheckBalance checkBalance) {
		this.checkBalance = checkBalance;
	}
	
	
	
	
}