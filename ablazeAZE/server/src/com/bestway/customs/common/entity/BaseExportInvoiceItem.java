package com.bestway.customs.common.entity;

import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 基础类
 * 
 * @author refdom
 *
 */
public class BaseExportInvoiceItem extends BaseScmEntity {
	/**
	 * 开户银行
	 */
	private String openAnAccountBank = null; 

	/**
	 * 帐号
	 */
	private String bankAccounts = null; 

	/**
	 * 开票人
	 */
	private String makeInvoiceEmployeeName = null; 

	/**
	 * 备注
	 */
	private String memo = null; 

	/**
	 * 运输方式
	 */
	private String transferMode = null; 

	/**
	 * 目的港
	 */
	private String portDest = null; 

	/**
	 * 装货港口
	 */
	private PortLin portOfLoadingOrUnloading = null; 

	/**
	 * 出口报关单
	 */
	private BaseCustomsDeclaration baseCustomsDeclaration = null; 

	/**
	 * 取得帐号
	 * @return 帐号
	 */
	public String getBankAccounts() {
		return bankAccounts;
	}

	/**
	 * 设置帐号
	 * @param bankAccounts 帐号
	 */
	public void setBankAccounts(String bankAccounts) {
		this.bankAccounts = bankAccounts;
	}
	/**
	 * 取得开票人
	 * @return 开票人
	 */
	public String getMakeInvoiceEmployeeName() {
		return makeInvoiceEmployeeName;
	}

	/**
	 * 取得出口报关单
	 * @return 出口报关单
	 */
	public BaseCustomsDeclaration getBaseCustomsDeclaration() {
		return baseCustomsDeclaration;
	}

	/**
	 * 设置出口报关单
	 * @param customsDeclaration 出口报关单
	 */
	public void setBaseCustomsDeclaration(BaseCustomsDeclaration customsDeclaration) {
		this.baseCustomsDeclaration = customsDeclaration;
	}

	/**
	 * 设置开票人
	 * @param makeInvoiceEmployeeName 开票人
	 */
	public void setMakeInvoiceEmployeeName(String makeInvoiceEmployeeName) {
		this.makeInvoiceEmployeeName = makeInvoiceEmployeeName;
	}

	/**
	 * 取得备注
	 * @return 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 填写备注
	 * @param memo 备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * 取得开户银行
	 * @return 开户银行
	 */
	public String getOpenAnAccountBank() {
		return openAnAccountBank;
	}
	/**
	 * 设置开户银行
	 * @param openAnAccountBank 开户银行
	 */
	public void setOpenAnAccountBank(String openAnAccountBank) {
		this.openAnAccountBank = openAnAccountBank;
	}
	
	/**
	 * 取得 装货港口 or 指运港口
	 * @return  装货港口 or 指运港口
	 */
	public PortLin getPortOfLoadingOrUnloading() {
		return portOfLoadingOrUnloading;
	}
	/**
	 * 设置 装货港口 or 指运港口
	 * @param portOfLoadingOrUnloading  装货港口 or 指运港口
	 */
	public void setPortOfLoadingOrUnloading(PortLin portOfLoadingOrUnloading) {
		this.portOfLoadingOrUnloading = portOfLoadingOrUnloading;
	}

	/**
	 * 取得目的港
	 * @return 目的港
	 */
	public String getPortDest() {
		return portDest;
	}

	/**
	 * 取得运输方式
	 * @return 运输方式
	 */
	public String getTransferMode() {
		return transferMode;
	}

	/**
	 * 设置目的港
	 * @param portDest 目的港
	 */
	public void setPortDest(String portDest) {
		this.portDest = portDest;
	}

	/**
	 * 设置运输方式
	 * @param transferMode 运输方式
	 */
	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}
}