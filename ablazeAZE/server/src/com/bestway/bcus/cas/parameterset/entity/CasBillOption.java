/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.parameterset.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 海关帐基本单据项目设定
 * 
 * 这种参数设置是一种最笨的方法,以后要屏弃. ls
 */
public class CasBillOption extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 单据是否允许重复
	 */
	private Boolean isBillRepeat = false;
	/**
	 * 新增时是否初始化单据号
	 */
	private Boolean isInitBillNo = true;
	/**
	 * 双击是否修改还是流览
	 */
	private Boolean isDoubleClickBillState = false;
	/**
	 * 单据是否显示结存数量及结余数量
	 */
	private Boolean isShowStocks = false;
	/**
	 * 车间入库单是否需要录入客户名称
	 */
	private Boolean isInNeedCustomer = false;
	/**
	 * 车间领用是否需要录入客户名称
	 */
	private Boolean isOutNeedCustomer = false;
	/**
	 * 国内购买是否备注发票号
	 */
	private Boolean isNoticeBillNo = false;
	
	 /**
     * 是否显示对应记录 当单据对应时数量小于0.01时
     */
    private Boolean	isShowBillCorrRecord = false;
    
    /**
     * 当单据对应时数量小于showBillCorrRecord时,有给显示
     */
    private Double showBillCorrRecord = null;
    /**
	 * 车间返回是否需要录入客户名称
	 */
	private Boolean isWorkShopBack = false;
	
	/**
	 * 获取车间返回是否需要录入客户名称boolean变量
	 * @return
	 */
	public Boolean getIsWorkShopBack() {
		return isWorkShopBack;
	}
	/**
	 * 设置车间返回是否需要录入客户名称boolean变量
	 * @param isWorkShopBack
	 */
	public void setIsWorkShopBack(Boolean isWorkShopBack) {
		this.isWorkShopBack = isWorkShopBack;
	}

	/**
     * 返回车间需录入客户名称
     */
    private Boolean	isBackWorkShop = false;
    
    //hwy2012-11-13
	/**
	 * 料件转仓入库是否需要录入客户供应商名称
	 */
    private Boolean isInChangeHouseMateriel = false;
    /**
     * 料件转厂出库是否需要录入客户供应商名称
     */
    private Boolean isOutChangeHouseMateriel = false;
    /**
     * 成品转厂入库是否需要录入客户供应商名
     */
    private Boolean isInChangeHouseProduct = false;
    /**
     * 成品转厂出库是否需要录入客户供应商名称
     */
    private Boolean isOutChangeHouseProduct = false;
    
	/**
	 * 获取返回车间需录入客户名称boolean变量
	 * @return
	 */
	public Boolean getIsBackWorkShop() {
		return isBackWorkShop;
	}

	/**
	 * 设置返回车间需录入客户名称boolean变量
	 * @param isBackWorkShop
	 */
	public void setIsBackWorkShop(Boolean isBackWorkShop) {
		this.isBackWorkShop = isBackWorkShop;
	}

	/**
	 * 判断国内购买是否备注发票号 是为true,否为false
	 * 
	 * @return the isNoticeBillNo 国内购买是否备注发票号
	 */
	public Boolean getIsNoticeBillNo() {
		return isNoticeBillNo;
	}

	/**
	 * 设置国内购买是否备注发票号
	 * 
	 * @param isNoticeBillNo
	 *            国内购买是否备注发票号
	 */
	public void setIsNoticeBillNo(Boolean isNoticeBillNo) {
		this.isNoticeBillNo = isNoticeBillNo;
	}

	/**
	 * 判断单据是否允许重复 允许为true 否则为false
	 * 
	 * @return isBillRepeat 单据是否允许重复
	 */
	public Boolean getIsBillRepeat() {
		return isBillRepeat;
	}

	/**
	 * 判断双击是否修改流览
	 * 
	 * @return isDoubleClickBillState 双击是否修改流览
	 */
	public Boolean getIsDoubleClickBillState() {
		return isDoubleClickBillState;
	}

	/**
	 * 判断车间入库单是否需要录入客户名称
	 * 
	 * @return isInNeedCustomer 车间入库单是否需要录入客户名称
	 */
	public Boolean getIsInNeedCustomer() {
		return isInNeedCustomer;
	}

	/**
	 * 取得车间领用是否需要录入客户名称
	 * 
	 * @return isOutNeedCustomer 车间领用是否需要录入客户名称
	 */
	public Boolean getIsOutNeedCustomer() {
		return isOutNeedCustomer;
	}

	/**
	 * 取得单据是否显示结存数量及结余数量
	 * 
	 * @return isShowStocks 单据是否显示结存数量及结余数量
	 */
	public Boolean getIsShowStocks() {
		return isShowStocks;
	}

	/**
	 * 设置单据是否允许重复
	 * 
	 * @param isBillRepeat
	 *            单据是否允许重复
	 */
	public void setIsBillRepeat(Boolean isBillRepeat) {
		this.isBillRepeat = isBillRepeat;
	}

	/**
	 * 设置双击是否修改流览
	 * 
	 * @param isDoubleClickBillState
	 *            双击是否修改流览
	 */
	public void setIsDoubleClickBillState(Boolean isDoubleClickBillState) {
		this.isDoubleClickBillState = isDoubleClickBillState;
	}

	/**
	 * 设置车间入库单是否需要录入客户名称
	 * 
	 * @param isInNeedCustomer
	 *            车间入库单是否需要录入客户名称
	 */
	public void setIsInNeedCustomer(Boolean isInNeedCustomer) {
		this.isInNeedCustomer = isInNeedCustomer;
	}

	/**
	 * 设置车间领用是否需要录入客户名称
	 * 
	 * @param isOutNeedCustomer
	 *            车间领用是否需要录入客户名称
	 */
	public void setIsOutNeedCustomer(Boolean isOutNeedCustomer) {
		this.isOutNeedCustomer = isOutNeedCustomer;
	}

	/**
	 * 设置单据是否显示结存数量及结余数量
	 * 
	 * @param isShowStocks
	 *            单据是否显示结存数量及结余数量
	 */
	public void setIsShowStocks(Boolean isShowStocks) {
		this.isShowStocks = isShowStocks;
	}

	public Boolean getIsInitBillNo() {
		return isInitBillNo;
	}

	public void setIsInitBillNo(Boolean isInitBillNo) {
		this.isInitBillNo = isInitBillNo;
	}

	public Boolean getIsShowBillCorrRecord() {
		if(isShowBillCorrRecord==null)
			return false;
		return isShowBillCorrRecord;
	}

	public void setIsShowBillCorrRecord(Boolean isShowBillCorrRecord) {
		this.isShowBillCorrRecord = isShowBillCorrRecord;
	}

	public Double getShowBillCorrRecord() {
		if(showBillCorrRecord==null){
			return 0.1;
		}
		return showBillCorrRecord;
	}

	public void setShowBillCorrRecord(Double showBillCorrRecord) {
		this.showBillCorrRecord = showBillCorrRecord;
	}
	/**
	 * 判断料件转仓入库是否需要录入客户供应商名称
	 * @return
	 */
	
	public Boolean getIsInChangeHouseMateriel() {
		return isInChangeHouseMateriel;
	}
	/**
	 * 设置料件转仓入库是否需要录入客户供应商名称
	 * @param isInChangeHouseMateriel
	 */
	public void setIsInChangeHouseMateriel(Boolean isInChangeHouseMateriel) {
		this.isInChangeHouseMateriel = isInChangeHouseMateriel;
	}
	/**
	 * 判断料件转仓出库是否需要录入客户供应商名称
	 * @return
	 */
	public Boolean getIsOutChangeHouseMateriel() {
		return isOutChangeHouseMateriel;
	}
	/**
	 * 设置料件转仓出库是否需要录入客户供应商名称
	 * @param isOutChangeHouseMateriel
	 */
	public void setIsOutChangeHouseMateriel(Boolean isOutChangeHouseMateriel) {
		this.isOutChangeHouseMateriel = isOutChangeHouseMateriel;
	}
	/**
	 * 判断成品转仓入库是否需要录入客户供应商名称
	 * @return
	 */
	public Boolean getIsInChangeHouseProduct() {
		return isInChangeHouseProduct;
	}
	/**
	 * 设置成品转仓入库是否需要录入客户供应商名称
	 * @param isInChangeHouseProduct
	 */
	public void setIsInChangeHouseProduct(Boolean isInChangeHouseProduct) {
		this.isInChangeHouseProduct = isInChangeHouseProduct;
	}
	/**
	 * 判断成品转仓出库是否需要录入客户供应商名称
	 * @return
	 */
	public Boolean getIsOutChangeHouseProduct() {
		return isOutChangeHouseProduct;
	}
	/**
	 * 设置成品转仓出库是否需要录入客户供应商名称
	 * @param isOutChangeHouseProduct
	 */
	public void setIsOutChangeHouseProduct(Boolean isOutChangeHouseProduct) {
		this.isOutChangeHouseProduct = isOutChangeHouseProduct;
	}
	
	
}
