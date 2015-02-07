/*
 * Created on 2004-7-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.constant;

import java.io.Serializable;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ImpExpType implements Serializable {
	/**
	 * 料件进口 料件资料 直接进口---料件
	 */
	public static final int DIRECT_IMPORT = 0;

	/**
	 * 料件转厂 料件资料 转厂进口---料件
	 */
	public static final int TRANSFER_FACTORY_IMPORT = 1;

	/**
	 * 退厂返工 成品资料 ---成品
	 */
	public static final int BACK_FACTORY_REWORK = 2;

	/**
	 * 一般贸易进口 --- 料件
	 */
	public static final int GENERAL_TRADE_IMPORT = 3;

	/**
	 * 成品出口 成品资料 ---成品
	 */
	public static final int DIRECT_EXPORT = 4;

	/**
	 * 转厂出口 成品资料 ---成品
	 */
	public static final int TRANSFER_FACTORY_EXPORT = 5;

	/**
	 * 退料出口 料件资料 ---料件
	 */
	public static final int BACK_MATERIEL_EXPORT = 6;

	/**
	 * 返工复出 成品资料 ---成品
	 */

	public static final int REWORK_EXPORT = 7;

	/**
	 * 边角料退港 ----料件
	 */
	public static final int REMIAN_MATERIAL_BACK_PORT = 8;

	/**
	 * 边角料内销 ---料件
	 */
	public static final int REMIAN_MATERIAL_DOMESTIC_SALES = 9;

	/**
	 * 一般贸易出口 ---成品
	 */
	public static final int GENERAL_TRADE_EXPORT = 10;

	/**
	 * 设备进口
	 */
	public static final int EQUIPMENT_IMPORT = 11;

	/**
	 * 退港维修
	 */
	public static final int BACK_PORT_REPAIR = 12;

	/**
	 * 设备退港
	 */
	public static final int EQUIPMENT_BACK_PORT = 13;

	/**
	 * 余料结转(出口报关单)
	 */
	public static final int REMAIN_FORWARD_EXPORT = 14;

	/**
	 * 出口仓储
	 */
	public static final int EXPORT_STORAGE = 15;

	/**
	 * 进口仓储
	 */
	public static final int IMPORT_STORAGE = 16;

	/**
	 * 料件内销，海关批准内销 ---料件
	 */
	public static final int MATERIAL_DOMESTIC_SALES = 17;

	/**
	 * 料件退换 ---料件
	 */
	public static final int MATERIAL_EXCHANGE = 18;

	/**
	 * 料件复出 ---料件
	 */
	public static final int MATERIAL_REOUT = 19;

	/**
	 * 所有（注意：不存在这样的类型，方便在参数设置中使用到）
	 */
	public static final int All_Type = 20;

	/**
	 * 进料成品退换 ---成品
	 */
	public static final int IMPORT_EXG_BACK = 25;

	/**
	 * 修理物品 ---成品
	 */
	public static final int IMPORT_REPAIR_MATERIAL = 26;

	/**
	 * 修理物品复出 ---成品
	 */
	public static final int EXPORT_MATERIAL_REBACK = 27;

	/**
	 * 进料成品退换复出 ---成品
	 */
	public static final int EXPORT_EXG_REBACK = 28;

	/**
	 * 余料结转(进口报关单)
	 */
	public static final int REMAIN_FORWARD_IMPORT = 21;

	public static String getImpExpTypeDesc(int impExpType) {
		String str = "";
		switch (impExpType) {
		
		case ImpExpType.DIRECT_IMPORT: // 0
			str = "料件进口";
			break;
		case ImpExpType.TRANSFER_FACTORY_IMPORT: // 1
			str = "料件转厂";
			break;
		case ImpExpType.BACK_FACTORY_REWORK: // 2
			str = "退厂返工";
			break;
		case ImpExpType.GENERAL_TRADE_IMPORT: // 3
			str = "一般贸易进口";
			break;
		case ImpExpType.DIRECT_EXPORT: // 4
			str = "成品出口";
			break;
		case ImpExpType.TRANSFER_FACTORY_EXPORT: // 5
			str = "转厂出口";
			break;
		case ImpExpType.BACK_MATERIEL_EXPORT: // 6
			str = "退料出口";
			break;
		case ImpExpType.REWORK_EXPORT: // 7
			str = "返工复出";
			break;
		case ImpExpType.REMIAN_MATERIAL_BACK_PORT: // 8
			str = "边角料退港";
			break;
		case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES: // 9
			str = "边角料内销";
			break;
		case ImpExpType.GENERAL_TRADE_EXPORT: // 10
			str = "一般贸易出口";
			break;
		case ImpExpType.EQUIPMENT_IMPORT: // 11
			str = "设备进口";
			break;
		case ImpExpType.BACK_PORT_REPAIR: // 12
			str = "退港维修";
			break;
		case ImpExpType.EQUIPMENT_BACK_PORT: // 13
			str = "设备退港";
			break;
		case ImpExpType.REMAIN_FORWARD_EXPORT: // 14
			str = "余料结转出口";
			break;
		case ImpExpType.EXPORT_STORAGE: // 15
			str = "出口仓储";
			break;
		case ImpExpType.IMPORT_STORAGE: // 16
			str = "进口仓储";
			break;
		case ImpExpType.MATERIAL_DOMESTIC_SALES: // 17
			str = "海关批准内销";
			break;
		case ImpExpType.MATERIAL_EXCHANGE: // 18
			str = "料件退换";
			break;
		case ImpExpType.MATERIAL_REOUT: // 19
			str = "料件复出";
			break;
		case ImpExpType.REMAIN_FORWARD_IMPORT: // 21
			str = "余料转入";
			break;
		case ImpExpType.IMPORT_EXG_BACK:// 25
			str = "进料成品退换";
			break;
		case ImpExpType.IMPORT_REPAIR_MATERIAL:// 26
			str = "修理物品";
			break;
		case ImpExpType.EXPORT_MATERIAL_REBACK:// 27
			str = "修理物品复出";
			break;
		case ImpExpType.EXPORT_EXG_REBACK:  //28
			str="进料成品退换复出";
			break;
		}
		return str;
	}
	
	public static boolean isImportType(int impExpType) {
		boolean isImport = false;
		switch (impExpType) {		
		case ImpExpType.DIRECT_IMPORT: // 0
//			str = "料件进口";
			isImport=true;
			break;
		case ImpExpType.TRANSFER_FACTORY_IMPORT: // 1
//			str = "料件转厂";
			isImport=true;
			break;
		case ImpExpType.BACK_FACTORY_REWORK: // 2
//			str = "退厂返工";
			isImport=true;
			break;
		case ImpExpType.GENERAL_TRADE_IMPORT: // 3
//			str = "一般贸易进口";
			isImport=true;
			break;
		case ImpExpType.DIRECT_EXPORT: // 4
//			str = "成品出口";
			isImport=false;
			break;
		case ImpExpType.TRANSFER_FACTORY_EXPORT: // 5
//			str = "转厂出口";
			isImport=false;
			break;
		case ImpExpType.BACK_MATERIEL_EXPORT: // 6
//			str = "退料出口";
			isImport=false;
			break;
		case ImpExpType.REWORK_EXPORT: // 7
//			str = "返工复出";
			isImport=false;
			break;
		case ImpExpType.REMIAN_MATERIAL_BACK_PORT: // 8
//			str = "边角料退港";
			isImport=false;
			break;
		case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES: // 9
//			str = "边角料内销";
			isImport=true;
			break;
		case ImpExpType.GENERAL_TRADE_EXPORT: // 10
//			str = "一般贸易出口";
			isImport=false;
			break;
		case ImpExpType.EQUIPMENT_IMPORT: // 11
//			str = "设备进口";
			isImport=true;
			break;
		case ImpExpType.BACK_PORT_REPAIR: // 12
//			str = "退港维修";
			isImport=false;
			break;
		case ImpExpType.EQUIPMENT_BACK_PORT: // 13
//			str = "设备退港";
			isImport=false;
			break;
		case ImpExpType.REMAIN_FORWARD_EXPORT: // 14
//			str = "余料结转出口";
			isImport=false;
			break;
		case ImpExpType.EXPORT_STORAGE: // 15
//			str = "出口仓储";
			isImport=false;
			break;
		case ImpExpType.IMPORT_STORAGE: // 16
//			str = "进口仓储";
			isImport=true;
			break;
		case ImpExpType.MATERIAL_DOMESTIC_SALES: // 17
//			str = "海关批准内销";
			isImport=true;
			break;
		case ImpExpType.MATERIAL_EXCHANGE: // 18
//			str = "料件退换";
			isImport=true;
			break;
		case ImpExpType.MATERIAL_REOUT: // 19
//			str = "料件复出";
			isImport=true;
			break;
		case ImpExpType.REMAIN_FORWARD_IMPORT: // 21
//			str = "余料转入";
			isImport=true;
			break;
		case ImpExpType.IMPORT_EXG_BACK:// 25
//			str = "进料成品退换";
			isImport=false;
			break;
		case ImpExpType.IMPORT_REPAIR_MATERIAL:// 26
//			str = "修理物品";
			isImport=true;
			break;
		case ImpExpType.EXPORT_MATERIAL_REBACK:// 27
//			str = "修理物品复出";
			isImport=false;
			break;
		}
		return isImport;
	}
}