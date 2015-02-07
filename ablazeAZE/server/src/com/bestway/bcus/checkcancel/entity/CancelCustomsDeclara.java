/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 给核销中的报关单表头继承
 */
public class CancelCustomsDeclara extends BaseScmEntity implements Cloneable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 核销表头
	 */
	private CancelHead cancelHead;

	/**
	 * 报关单号
	 */
	private String customNo;

	/**
	 * 报关地海关
	 */
	private Customs custom;

	/**
	 * 进出口类型
	 * DIRECT_IMPORT = 0;	料件进口 料件资料 直接进口---料件
	 * TRANSFER_FACTORY_IMPORT = 1;	料件转厂 料件资料 转厂进口---料件
	 * BACK_FACTORY_REWORK = 2;	退厂返工 成品资料 ---成品
	 * GENERAL_TRADE_IMPORT = 3;	一般贸易进口 --- 料件
	 * DIRECT_EXPORT = 4;	成品出口 成品资料 ---成品
	 * TRANSFER_FACTORY_EXPORT = 5;	转厂出口 成品资料 ---成品
	 * BACK_MATERIEL_EXPORT = 6;	退料出口 料件资料 ---料件
	 * REWORK_EXPORT = 7;	返工复出 成品资料 ---成品
	 * REMIAN_MATERIAL_BACK_PORT = 8;	边角料退港 ----料件
	 * REMIAN_MATERIAL_DOMESTIC_SALES = 9;	边角料内销 ---料件
	 * GENERAL_TRADE_EXPORT = 10;	一般贸易出口 ---成品
	 * EQUIPMENT_IMPORT = 11;	设备进口
	 * BACK_PORT_REPAIR = 12;	退港维修
	 * EQUIPMENT_BACK_PORT = 13;	设备退港
	 * REMAIN_FORWARD_EXPORT = 14;	余料结转(出口报关单)
	 * EXPORT_STORAGE = 15;	出口仓储
	 * IMPORT_STORAGE = 16;	进口仓储
	 * MATERIAL_DOMESTIC_SALES = 17;	料件内销，海关批准内销 ---料件
	 * MATERIAL_EXCHANGE = 18;	料件退换 ---料件
	 * MATERIAL_REOUT = 19;	料件复出 ---料件
	 * All_Type = 20;	所有（注意：不存在这样的类型，方便在参数设置中使用到）
	 * IMPORT_EXG_BACK = 25;	进料成品退换 ---成品
	 * IMPORT_REPAIR_MATERIAL = 26;	修理物品 ---成品
	 * EXPORT_MATERIAL_REBACK = 27;	修理物品复出 ---成品
	 * EXPORT_EXG_REBACK = 28;	进料成品退换复出 ---成品
	 * REMAIN_FORWARD_IMPORT = 21;	余料结转(进口报关单)
	 */
	private Integer inOutportType;

	/**
	 * 进出口标志
	 * IMPORT=0;	进口标志
	 * EXPORT=1;	出口标志
	 * SPECIAL=2;	特殊报关单
	 */
	private String inOutportFlat;

	/**
	 * 申报日期
	 */
	private Date declareDate;

	/**
	 * 进出口日期
	 */
	private Date inOutportDate;

	/**
	 * 贸易方式
	 */
	private Trade tradeMode;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 设置核销表头
	 * 
	 * @return cancelHead 核销表头
	 */
	public CancelHead getCancelHead() {
		return cancelHead;
	}

	/**
	 * 获取核销表头
	 * 
	 * @param cancelHead
	 *            核销表头
	 */
	public void setCancelHead(CancelHead cancelHead) {
		this.cancelHead = cancelHead;
	}

	/**
	 * 设置报关地海关
	 * 
	 * @return custom 报关地海关
	 */
	public Customs getCustom() {
		return custom;
	}

	/**
	 * 获取报关地海关
	 * 
	 * @param custom
	 *            报关地海关
	 */
	public void setCustom(Customs custom) {
		this.custom = custom;
	}

	/**
	 * 设置报关单号
	 * 
	 * @return customNo 报关单号
	 */
	public String getCustomNo() {
		return customNo;
	}

	/**
	 * 获取报关单号
	 * 
	 * @param customNo
	 *            报关单号
	 */
	public void setCustomNo(String customNo) {
		this.customNo = customNo;
	}

	/**
	 * 设置申报日期
	 * 
	 * @return declareDate 申报日期
	 */
	public Date getDeclareDate() {
		return declareDate;
	}

	/**
	 * 获取申报日期
	 * 
	 * @param declareDate
	 *            申报日期
	 */
	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	/**
	 * 设置进出口日期
	 * 
	 * @return inOutportDate 进出口日期
	 */
	public Date getInOutportDate() {
		return inOutportDate;
	}

	/**
	 * 获取进出口日期
	 * 
	 * @param inOutportDate
	 *            进出口日期
	 */
	public void setInOutportDate(Date inOutportDate) {
		this.inOutportDate = inOutportDate;
	}

	/**
	 * 设置进出口类型
	 * 
	 * @return inOutportType 进出口类型
	 */
	public Integer getInOutportType() {
		return inOutportType;
	}

	/**
	 * 获取进出口类型
	 * 
	 * @param inOutportType
	 *            进出口类型
	 */
	public void setInOutportType(Integer inOutportType) {
		this.inOutportType = inOutportType;
	}

	/**
	 * 设置备注
	 * 
	 * @return note 备注
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 获取备注
	 * 
	 * @param note
	 *            备注
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 设置贸易方式
	 * 
	 * @return tradeMode 贸易方式
	 */
	public Trade getTradeMode() {
		return tradeMode;
	}

	/**
	 * 获取贸易方式
	 * 
	 * @param tradeMode
	 *            贸易方式
	 */
	public void setTradeMode(Trade tradeMode) {
		this.tradeMode = tradeMode;
	}

	/**
	 * 设置进出口标志：I表示进口，E表示出口
	 * 
	 * @return inOutportFlat 进出口标志：I表示进口，E表示出口
	 */
	public String getInOutportFlat() {
		return inOutportFlat;
	}

	/**
	 * 获取进出口标志：I表示进口，E表示出口
	 * 
	 * @param inOutportFlat
	 *            进出口标志：I表示进口，E表示出口
	 */
	public void setInOutportFlat(String inOutportFlat) {
		this.inOutportFlat = inOutportFlat;
	}
}
