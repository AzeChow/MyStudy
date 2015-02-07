/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;

/**
 * 单据类别
 * check by hcl 2011-01-07
 * 
 */
public class BillType extends BaseEntity implements Cloneable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 单据类别
	 * MATERIEL_IN = 1;	料件入库
	 * MATERIEL_OUT = 2;	料件出库
	 * MATERIEL_INOUT = 121;	料件出入库
	 * PRODUCE_IN = 3;	成品入库
	 * PRODUCE_OUT = 4;	成品出库
	 * PRODUCE_INOUT = 343;	成品出入库
	 * FIXTURE_IN = 5;	设备入库
	 * FIXTURE_OUT = 6;	设备出库
	 * FIXTURE_INOUT = 565; 	设备出入库
	 *  HALF_PRODUCE_IN = 7; 	半成品入库
	 *  HALF_PRODUCE_OUT = 8;	半成品出库
	 *  HALF_PRODUCE_INOUT = 78; 	半成品入库出库
	 *  REMAIN_PRODUCE_IN = 9;	残次品入库
	 *  REMAIN_PRODUCE_OUT = 10; 	残次品出库
	 *  REMAIN_PRODUCE_INOUT = 910;	残次品入库出库
	 *  LEFTOVER_MATERIEL_IN = 11; 	边角料入库
	 *  LEFTOVER_MATERIEL_OUT = 12;	边角料出库
	 *  LEFTOVER_MATERIEL_INOUT = 1112;	边角料入库出库
	 */
	private Integer billType = null;

	/**
	 * 单据类型代码
	 */

	private String code = null;

	/**
	 * 单据类型名称(直接进口 国内购买..........)
	 */
	private String name = null;

	/**
	 * 进出仓库类别(进仓/出仓)
	 * WARE_IN = 1;	入库
	 * WARE_OUT = 2;	出库
	 */
	private Integer wareType = null;

	/**
	 * 商品类型
	 * FINISHED_PRODUCT="0";	成品
	 * SEMI_FINISHED_PRODUCT="1";	半成品
	 * MATERIEL="2";	材料--主料
	 * MACHINE="3";	设备
	 * REMAIN_MATERIEL="4";	边角料
	 * BAD_PRODUCT="5";	残次品
	 * ASSISTANT_MATERIAL = "6";	辅料
	 * WASTE_MATERIAL = "7";	消耗料
	 */
	private Integer produceType = null;

	/**
	 * 系统定义
	 */
	private Boolean sysDefine = false;

	/**
	 * 是否能与报关单对应的单据
	 */
	private Boolean isCustomsDeclarationCorresponding = false;

	/**
	 * 是否能与报关单互转的单据
	 */
	private Boolean isTransferBill = false;

	/**
	 * 增加此栏位主要是为了解决，工厂实际单据类型与我们系统中的名称不一至。
	 *  如：我们系统直接进口，工厂有可能是ZJJK。
	 *  这也是为单据文本导入新增加的
	 */
	private String factoryBillName = null;
	/**
	 * 是否在工厂单据管理显示
	 */
	private Boolean isShow = false;

	/**
	 * 获取 是否能与报关单对应的单据
	 */
	public Boolean getIsCustomsDeclarationCorresponding() {
		return isCustomsDeclarationCorresponding;
	}

	/**
	 * 设置 是否能与报关单对应的单据
	 */
	public void setIsCustomsDeclarationCorresponding(
			Boolean isCustomsDeclarationCorresponding) {
		this.isCustomsDeclarationCorresponding = isCustomsDeclarationCorresponding;
	}

	/**
	 * 获取是否能与报关单互转的单据
	 */
	public Boolean getIsTransferBill() {
		return isTransferBill;
	}

	/**
	 * 设置  是否能与报关单互转的单据
	 */
	public void setIsTransferBill(Boolean isTransferBill) {
		this.isTransferBill = isTransferBill;
	}

	/**
	 * 取得单据类别
	 * 
	 * @return billType 单据类别(料件入库 料件出库.......)
	 */
	public Integer getBillType() {
		return billType;
	}

	/**
	 * 设置单据类别
	 * 
	 * @param billType
	 *            单据类别(料件入库 料件出库......)
	 */
	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	/**
	 * 取得单据类型代码
	 * 
	 * @return code 单据类型代码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置单据类型代码
	 * 
	 * @param code
	 *            单据类型代码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 取得单据类型名称
	 * 
	 * @return name 单据类型名称(直接进口 国内购买......)
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置单据类型名称
	 * 
	 * @param name
	 *            单据类型名称(直接进口 国内购买.......)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 取得商品类型
	 * 
	 * @return produceType 商品类型(料件 成品 半成品......)
	 */
	public Integer getProduceType() {
		return produceType;
	}

	/**
	 * 取得商品类型
	 * 
	 * @param produceType
	 *            商品类型(料件 成品 半成品......)
	 */
	public void setProduceType(Integer produceType) {
		this.produceType = produceType;
	}

	/**
	 * 取得进出仓类别
	 * 
	 * @return wareType 进出仓库类别(进仓/出仓)
	 */
	public Integer getWareType() {
		return wareType;
	}

	/**
	 * 设置进出仓类别 整型
	 * 
	 * @param wareType
	 *            进出仓库类别(进仓/出仓)
	 */
	public void setWareType(Integer wareType) {
		this.wareType = wareType;
	}

	/**
	 * 取得系统定义 布尔型
	 * 
	 * @return sysDefine 系统定义
	 */
	public Boolean getSysDefine() {
		return sysDefine;
	}

	/**
	 * 设置系统定义类型 布尔型
	 * 
	 * @param sysDefine
	 *            系统定义
	 */
	public void setSysDefine(Boolean sysDefine) {
		this.sysDefine = sysDefine;
	}
	/**
	 * 获取系统与工厂差异名称
	 */
	public String getFactoryBillName() {
		return factoryBillName;
	}
	/**
	 * 设置系统与工厂差异名称
	 */
	public void setFactoryBillName(String factoryBillName) {
		this.factoryBillName = factoryBillName;
	}
	/**
	 * 获取是否在工厂单据管理显示
	 */
	public Boolean getIsShow() {
		return isShow;
	}
	/**
	 * 设置是否在工厂单据管理显示
	 */
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	
	
}
