package com.bestway.customs.common.logic;

import java.util.HashMap;
import java.util.Map;

import com.bestway.common.constant.ImpExpType;

/**
 * 通过贸易方式取得进出口类型
 */
public class MapTrade {
	/**
	 * 进口贸易方式对应表
	 */
	private static Map<String, Integer> imptrademap = null;

	/**
	 * 出口贸易方式对应表
	 */
	private static Map<String, Integer> exptrademap = null;

	/**
	 * 其它贸易方式对应表
	 */
	private static Map<String, Integer> spetrademap = null;

	private static void loadmap() {
		if (imptrademap == null) {
			imptrademap = new HashMap();
			exptrademap = new HashMap();
			spetrademap = new HashMap();
		}
		imptrademap.clear();
		exptrademap.clear();
		spetrademap.clear();
		/***********************************************************************
		 * *******************初始化进口报关单贸易方式对应表***************
		 **********************************************************************/
		/**
		 * 料件进口 料件资料 直接进口
		 */
		imptrademap.put("来料加工", ImpExpType.DIRECT_IMPORT);
		imptrademap.put("进料对口", ImpExpType.DIRECT_IMPORT);
		imptrademap.put("进料非对口", ImpExpType.DIRECT_IMPORT);
		/**
		 * 料件进口 料件退还
		 */
		imptrademap.put("来料料件退换", ImpExpType.DIRECT_IMPORT);
		imptrademap.put("进料料件退换", ImpExpType.DIRECT_IMPORT);

		/**
		 * 料件转厂 料件资料 转厂进口
		 */
		imptrademap.put("来料深加工", ImpExpType.TRANSFER_FACTORY_IMPORT);
		imptrademap.put("进料深加工", ImpExpType.TRANSFER_FACTORY_IMPORT);
		/**
		 * 退厂返工 成品资料
		 */
		imptrademap.put("来料成品退换", ImpExpType.BACK_FACTORY_REWORK);
		imptrademap.put("进料成品退换", ImpExpType.BACK_FACTORY_REWORK);

		/**
		 * 余料结转进口
		 */
		imptrademap.put("来料余料结转", ImpExpType.REMAIN_FORWARD_IMPORT);
		imptrademap.put("进料余料结转", ImpExpType.REMAIN_FORWARD_IMPORT);
		
		/**
		 * 海关批准内销
		 */
		imptrademap.put("进料料件内销", ImpExpType.MATERIAL_DOMESTIC_SALES);
		imptrademap.put("来料料件内销", ImpExpType.MATERIAL_DOMESTIC_SALES);
		
//		/**
//		 * 一般贸易
//		 */
//		Imptrademap.put("一般贸易", ImpExpType.GENERAL_TRADE_IMPORT);
		/***********************************************************************
		 * *****************初始化出口报关单贸易方式对应表**************************
		 **********************************************************************/
		/**
		 * 成品，直接出口
		 */
		exptrademap.put("进料对口", ImpExpType.DIRECT_EXPORT);
		exptrademap.put("进料非对口", ImpExpType.DIRECT_EXPORT);
		exptrademap.put("来料加工", ImpExpType.DIRECT_EXPORT);

		/**
		 * 退料出口 料件资料
		 */
		exptrademap.put("来料料件退换", ImpExpType.BACK_MATERIEL_EXPORT);
		exptrademap.put("进料料件退换", ImpExpType.BACK_MATERIEL_EXPORT);
		exptrademap.put("来料料件复出", ImpExpType.BACK_MATERIEL_EXPORT);
		exptrademap.put("进料料件复出", ImpExpType.BACK_MATERIEL_EXPORT);

		/**
		 * 余料结转(出口报关单)
		 */
		exptrademap.put("来料余料结转", ImpExpType.REMAIN_FORWARD_EXPORT);
		exptrademap.put("进料余料结转", ImpExpType.REMAIN_FORWARD_EXPORT);

		/**
		 * 转厂出口 成品资料
		 */
		exptrademap.put("来料深加工", ImpExpType.TRANSFER_FACTORY_EXPORT);
		exptrademap.put("进料深加工", ImpExpType.TRANSFER_FACTORY_EXPORT);

		/**
		 * 成品，返工复出
		 */
		exptrademap.put("来料成品退换", ImpExpType.REWORK_EXPORT);
		exptrademap.put("进料成品退换", ImpExpType.REWORK_EXPORT);

		/***********************************************************************
		 * *************************初始化其它报关单贸易方式对应表*******************
		 **********************************************************************/
		/**
		 * 一般贸易
		 */
		imptrademap.put("一般贸易", ImpExpType.GENERAL_TRADE_IMPORT);
		/**
		 * 边角料退港
		 */
		imptrademap.put("进料边角料复出", ImpExpType.REMIAN_MATERIAL_BACK_PORT);
		imptrademap.put("来料边角料复出", ImpExpType.REMIAN_MATERIAL_BACK_PORT);
		/**
		 * 边角料内销
		 */
		imptrademap.put("进料边角料内销", ImpExpType.MATERIAL_DOMESTIC_SALES);
		imptrademap.put("来料边角料内销", ImpExpType.MATERIAL_DOMESTIC_SALES);
		
		/**
		 * 一般贸易
		 */
		
		/**
		 * 边角料退港
		 */
		exptrademap.put("进料边角料复出", ImpExpType.REMIAN_MATERIAL_BACK_PORT);
		exptrademap.put("来料边角料复出", ImpExpType.REMIAN_MATERIAL_BACK_PORT);
		/**
		 * 边角料内销
		 */
		exptrademap.put("进料边角料内销", ImpExpType.MATERIAL_DOMESTIC_SALES);
		exptrademap.put("来料边角料内销", ImpExpType.MATERIAL_DOMESTIC_SALES);
		
		/**************************特殊报关单*******************************/
		spetrademap.put("不作价设备", ImpExpType.EQUIPMENT_IMPORT);
		spetrademap.put("加工贸易设备", ImpExpType.EQUIPMENT_IMPORT);
		spetrademap.put("加工设备内销", ImpExpType.EQUIPMENT_IMPORT);
		spetrademap.put("加工设备结转", ImpExpType.EQUIPMENT_IMPORT);
		spetrademap.put("加工设备退运", ImpExpType.BACK_PORT_REPAIR);
		spetrademap.put("减免设备结转", ImpExpType.EQUIPMENT_BACK_PORT);
		spetrademap.put("合资合作设备", ImpExpType.EQUIPMENT_IMPORT);
		spetrademap.put("外资设备物品", ImpExpType.EQUIPMENT_IMPORT);
		spetrademap.put("设备进出区", ImpExpType.EQUIPMENT_IMPORT);
		spetrademap.put("境外设备进区", ImpExpType.BACK_PORT_REPAIR);
		spetrademap.put("区内设备退运", ImpExpType.BACK_PORT_REPAIR);
		
		spetrademap.put("来料边角料内销", ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);
		//下面这两个解屏蔽
		spetrademap.put("进料边角料内销", ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);
		spetrademap.put("来料边角料复出", ImpExpType.REMIAN_MATERIAL_BACK_PORT);
		
		spetrademap.put("进料边角料复出", ImpExpType.REMIAN_MATERIAL_BACK_PORT);
		spetrademap.put("一般贸易进口", ImpExpType.GENERAL_TRADE_IMPORT);
		spetrademap.put("一般贸易出口", ImpExpType.GENERAL_TRADE_EXPORT);
	}

	public static Integer getImpExpType(String trade, int impexpflap) {
		Integer strreturn = 999;
		loadmap();
		System.out.print(trade);
		try {
			switch (impexpflap) {
			case 0:
				strreturn = imptrademap.get(trade);
				break;
			case 1:
				strreturn = exptrademap.get(trade);
				break;
			case 2:
				strreturn = spetrademap.get(trade);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return strreturn;
	}
}
