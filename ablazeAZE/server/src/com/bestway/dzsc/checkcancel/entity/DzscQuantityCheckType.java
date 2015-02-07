package com.bestway.dzsc.checkcancel.entity;

public class DzscQuantityCheckType {
	// 中期核查数量类型

	// 原料在途数量
	public static final String materByWay = "01";

	// 原料库存数量
	public static final String materStock = "02";

	// 废料数量
	public static final String depose = "03";

	// 在线数量
	public static final String onlines = "04";

	// 边角料数量
	public static final String overMater = "05";

	// 本期原料入库数量
	public static final String thisMaterInWare = "06";

	// 原料领料数量
	public static final String materGet = "07";

	// 本期原料内销数量
	public static final String thisMaterCancel = "08";

	// 本期放弃料件数量
	public static final String thisThrow = "09";

	// 合格成品耗用数量
	public static final String passExgUse = "10";

	// 废品,残次品折料数量
	public static final String otherConvert = "11";

	// 本期放弃残次品折料
	public static final String materReuse = "12";

	// 原料退换
	public static final String materExitChange = "13";

	// 半成品折料数量
	public static final String halfExgConvert = "14";

}
