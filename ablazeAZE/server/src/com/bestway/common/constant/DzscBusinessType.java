package com.bestway.common.constant;

public class DzscBusinessType {
	public static final String MATERIAL = "0"; // 企业物料

	public static final String EMS_POR_WJ = "1"; // 经营范围/合同备案

	public static final String EMS_POR_BILL = "2"; // 电子帐册备案商品/通关备案

	public static final String FASCICULE = "3"; // 分册

	public static final String CANCEL_AFTER_VERIFICA = "4"; // 数据报核cancel after

	// verification

	public static final String INNER_MERGE = "5"; // 企业归并关系

	// public static final String AA="6"; // 电子帐册单损耗
	public static final String CHECK = "7"; // 中期核查

	// public static final String AA="D"; // 报关单
	public static final String MATERIAL_BOM = "a"; // 企业BOM

	public static final String CUSTOMS_BILL_LIST = "b"; // 清单

	// public static final String AA="c"; // 内销汇总
	// public static final String AA="d"; // 内销明细
	// public static final String AA="e"; // 授权信息
	// public static final String AA="f"; // 企业物料图片
	// public static final String AA="g"; // 通用报文

	public static String getBusinessTypeDesc(String type) {
		if (DzscBusinessType.MATERIAL.equals(type)) {
			return "企业物料备案";
		} else if (DzscBusinessType.MATERIAL_BOM.equals(type)) {
			return "企业BOM";
		} else if (DzscBusinessType.INNER_MERGE.equals(type)) {
			return "内部归并";
		} else if (DzscBusinessType.EMS_POR_WJ.equals(type)) {
			return "合同备案";
		} else if (DzscBusinessType.EMS_POR_BILL.equals(type)) {
			return "通关备案";
		} else if (DzscBusinessType.FASCICULE.equals(type)) {
			return "手册分册";
		} else if (DzscBusinessType.CUSTOMS_BILL_LIST.equals(type)) {
			return "报关清单";
		} else if (DzscBusinessType.CANCEL_AFTER_VERIFICA.equals(type)) {
			return "数据核销";
		} else if (DzscBusinessType.CHECK.equals(type)) {
			return "中期核查";
		}
		return "";
	}
}
