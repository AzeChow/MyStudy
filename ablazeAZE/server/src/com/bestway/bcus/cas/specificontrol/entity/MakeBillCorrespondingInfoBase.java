/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.specificontrol.entity;

import java.util.Date;

import com.bestway.bcus.cas.entity.BillType;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 生产单据对应的中间信息
 */
public class MakeBillCorrespondingInfoBase extends BaseScmEntity {
	private static final long	serialVersionUID				= CommonUtils
																		.getSerialVersionUID();

	// //////////////////////////////
	// 单据信息
	// //////////////////////////////
	/**
	 * 单据Id
	 */
	private String				billDetailId					= null;
	/**
	 * 单据号
	 */
	private String				billNo							= null;
	/**
	 * 生效日期
	 */
	private Date				validDate						= null;
	/**
	 * 单据类型
	 */
	private String  			billTypeName					= null;
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
	private Integer impExpType	= null;

	/**
	 * 工厂料号
	 */
	private String				ptPart							= null;
	/**
	 * 商品名称
	 */
	private String				ptName							= null;
	/**
	 * 规格型号
	 */
	private String				ptSpec							= null;

	// ///////////////////////
	// 报关单信息
	// //////////////////////
	/**
	 * 报关单ID号
	 */
	private String				customsDeclarationId			= null;
	/**
	 * 报关单商品信息Id
	 */
	private String				customsDeclarationCommInfoId	= null;
	/**
	 * 报关单号
	 */
	private String				customsDeclarationCode			= null;
	/**
	 * 电子帐册号码
	 */
	private String				emsHeadH2k						= null;
	/**
	 * 商品名称
	 */
	private String				commName						= null;
	/**
	 * 商品名称
	 */
	private ScmCoc				scmCoc							= null;
	/**
	 * 商品规格
	 */
	private String				commSpec						= null;

	/**
	 * 对应数量
	 */
	private Double				amount							= null;

	/**
	 * 取得单据Id
	 * 
	 * @return billDetailId 单据Id.
	 */
	public String getBillDetailId() {
		return billDetailId;
	}

	/**
	 * 设置单据Id
	 * 
	 * @param billDetailId
	 *            单据Id
	 */
	public void setBillDetailId(String billDetailId) {
		this.billDetailId = billDetailId;
	}

	

	/**
	 * 取得对应数量
	 * 
	 * @return amount 对应数量.
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 设置对应数量
	 * 
	 * @param amount
	 *            对应数量.
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getCommSpec() {
		return commSpec;
	}

	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

	public String getCustomsDeclarationCommInfoId() {
		return customsDeclarationCommInfoId;
	}

	public void setCustomsDeclarationCommInfoId(
			String customsDeclarationCommInfoId) {
		this.customsDeclarationCommInfoId = customsDeclarationCommInfoId;
	}

	public String getCustomsDeclarationId() {
		return customsDeclarationId;
	}

	public void setCustomsDeclarationId(String customsDeclarationId) {
		this.customsDeclarationId = customsDeclarationId;
	}

	public String getEmsHeadH2k() {
		return emsHeadH2k;
	}

	public void setEmsHeadH2k(String emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtPart() {
		return ptPart;
	}

	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	public Integer getImpExpType() {
		return impExpType;
	}

	public void setImpExpType(Integer impExpType) {
		this.impExpType = impExpType;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	
}
