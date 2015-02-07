/*
 * Created on 2004-10-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempImgOrgUseInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String	ptPart		= "";	// 料号
	private String	ptName		= "";	// 原材料名称
	private String	ptSpec		= "";	// 原材料规格
	private String	ptUnitName	= "";	// 单位
	private Double	f1001		= 0.0;	// 料件期初单
	private Double	f1002		= 0.0;	// 在产品期初单
	private Double	f1003		= 0.0;	// 直接进口
	private Double	f1004		= 0.0;	// 结转进口
	private Double	f1005		= 0.0;	// 国内购买
	private Double	f1006		= 0.0;	// 车间返回
	private Double	f1007		= 0.0;	// 料件盘盈单
	private Double	f1008		= 0.0;	// 受托加工进库
	private Double	f1009		= 0.0;	// 其他来源
	private Double	f1010		= 0.0;	// 料件转仓入库
	private Double	f1011		= 0.0;	// 海关征税进口
	private Double	f1012		= 0.0;	// 外发加工退回料件
	private Double	f1013		= 0.0;	// 外发加工返回料件
	private Double	f1014		= 0.0;	// 委外期初单
	private Double	f1015		= 0.0;	// 已收货未结转期初单
	private Double	f1016		= 0.0;	// 已结转未收货期初单
	private Double	f1101		= 0.0;	// 车间领用
	private Double	f1102		= 0.0;	// 料件退换
	private Double	f1103		= 0.0;	// 料件复出
	private Double	f1104		= 0.0;	// 料件盘亏单
	private Double	f1105		= 0.0;	// 料件转仓出库
	private Double	f1106		= 0.0;	// 结转料件退货单
	private Double	f1107		= 0.0;	// 其他料件退货单
	private Double	f1108		= 0.0;	// 其他领用
	private Double	f1109		= 0.0;	// 受托加工领用
	private Double	f1110		= 0.0;	// 外发加工出库
	private Double	f1111		= 0.0;	// 受托加工退回料件
	private Double	f2001		= 0.0;	// 成品期初单
	private Double	f2002		= 0.0;	// 车间入库
	private Double	f2003		= 0.0;	// 退厂返工
	private Double	f2004		= 0.0;	// 成品盘盈单
	private Double	f2005		= 0.0;	// 成品转仓入库
	private Double	f2007		= 0.0;	// 受托加工车间入库
	private Double	f2008		= 0.0;	// 其他成品退货单
	private Double	f2009		= 0.0;	// 结转成品退货单
	private Double	f2010		= 0.0;	// 受托加工退回成品
	private Double	f2011		= 0.0;	// 已交货未结转期初单
	private Double	f2012		= 0.0;	// 已交货未结转期初单
	private Double	f2101		= 0.0;	// 直接出口
	private Double	f2102		= 0.0;	// 结转出口
	private Double	f2103		= 0.0;	// 返回车间
	private Double	f2104		= 0.0;	// 返工复出
	private Double	f2105		= 0.0;	// 成品盘亏单
	private Double	f2106		= 0.0;	// 海关批准内销
	private Double	f2107		= 0.0;	// 其他内销
	private Double	f2108		= 0.0;	// 其他处理
	private Double	f2109		= 0.0;	// 成品转仓出库
	private Double	f2110		= 0.0;	// 受托加工返回
	private Double	f4001		= 0.0;	// 半成品入库
	private Double	f4002		= 0.0;	// 半成品盘盈单
	private Double	f4003		= 0.0;	// 委外加工入库
	private Double	f4009		= 0.0;	// 半成品期初
	private Double	f4101		= 0.0;	// 半成品出库
	private Double	f4102		= 0.0;	// 半成品盘亏单
	private Double	f4103		= 0.0;	// 委外加工出库
	private Double	f4104		= 0.0;	// 外发加工返修半成品
	private Double	f4105		= 0.0;	// 外发加工领料
	private Double	f5002		= 0.0;	// 残次品期初
	/**
	 * 2002车间入库映射耗用
	 */
	private Double							f2002Waste			=0.0;
	/**
	 * 4003外发加工入库耗用
	 */
	private Double							f4003Waste			=0.0;
	/**
	 * 2007受托加工车间入库映射耗用
	 */
	private Double							f2007Waste			=0.0;
	/**
	 * 2103返回车间映射耗用
	 */
	private Double							f2103Waste			=0.0;
	/**
	 * 2110受托加工返回映射耗用
	 */
	private Double							f2110Waste			=0.0;
	/**
	 * 4104 外发加工返修半成品
	 */
	private Double							f4104Waste			=0.0;
	/**
	 * 4105 外发加工领料
	 */
	private Double							f4105Waste			=0.0;
	
	
	public TempImgOrgUseInfo(){
		
	}
	
	public TempImgOrgUseInfo(boolean isNull){
		if(isNull== true){
			f1001		= null;	// 料件期初单
			f1002		= null;	// 在产品期初单
			f1003		= null;	// 直接进口
			f1004		= null;	// 结转进口
			f1005		= null;	// 国内购买
			f1006		= null;	// 车间返回
			f1007		= null;	// 料件盘盈单
			f1008		= null;	// 受托加工进库
			f1009		= null;	// 其他来源
			f1010		= null;	// 料件转仓入库
			f1011		= null;	// 海关征税进口
			f1012		= null;	// 外发加工退回料件
			f1013		= null;	// 外发加工返回料件
			f1014		= null;	// 委外期初单
			f1015		= null;	// 已收货未结转期初单
			f1016		= null;	// 已结转未收货期初单
			f1101		= null;	// 车间领用
			f1102		= null;	// 料件退换
			f1103		= null;	// 料件复出
			f1104		= null;	// 料件盘亏单
			f1105		= null;	// 料件转仓出库
			f1106		= null;	// 结转料件退货单
			f1107		= null;	// 其他料件退货单
			f1108		= null;	// 其他领用
			f1109		= null;	// 受托加工领用
			f1110		= null;	// 外发加工出库
			f1111		= null;	// 受托加工退回料件
			f2001		= null;	// 成品期初单
			f2002		= null;	// 车间入库
			f2003		= null;	// 退厂返工
			f2004		= null;	// 成品盘盈单
			f2005		= null;	// 成品转仓入库
			f2007		= null;	// 受托加工车间入库
			f2008		= null;	// 其他成品退货单
			f2009		= null;	// 结转成品退货单
			f2010		= null;	// 受托加工退回成品
			f2011		= null;	// 已交货未结转期初单
			f2012		= null;	// 已交货未结转期初单
			f2101		= null;	// 直接出口
			f2102		= null;	// 结转出口
			f2103		= null;	// 返回车间
			f2104		= null;	// 返工复出
			f2105		= null;	// 成品盘亏单
			f2106		= null;	// 海关批准内销
			f2107		= null;	// 其他内销
			f2108		= null;	// 其他处理
			f2109		= null;	// 成品转仓出库
			f2110		= null;	// 受托加工返回
			f4001		= null;	// 半成品入库
			f4002		= null;	// 半成品盘盈单
			f4003		= null;	// 委外加工入库
			f4009		= null;	// 半成品期初
			f4101		= null;	// 半成品出库
			f4102		= null;	// 半成品盘亏单
			f4103		= null;	// 委外加工出库
			f4104		= null;	// 外发加工返修半成品
			f4105		= null;	// 外发加工领料
			f5002		= null;	// 残次品期初
			/**
			 * 2002车间入库映射耗用
			 */
			f2002Waste			=null;
			/**
			 * 4003外发加工入库耗用
			 */
			f4003Waste			=null;
			/**
			 * 2007受托加工车间入库映射耗用
			 */
			f2007Waste			=null;
			/**
			 * 2103返回车间映射耗用
			 */
			f2103Waste			=null;
			/**
			 * 2110受托加工返回映射耗用
			 */
			f2110Waste			=null;
			/**
			 * 4104 外发加工返修半成品
			 */
			f4104Waste			=null;
			/**
			 * 4105 外发加工领料
			 */
			f4105Waste			=null;
		}
	}
	
	
	
	

	/**
	 * 取得键值（唯一）
	 * 
	 * @return ptName+ptSpec+ptUnitName 原材料名称+原材料规格+单位.
	 */
	public String getKey() {
		String ptSpec = (this.ptSpec == null || "".equals(this.ptSpec)) ? ""
				: "/" + this.ptSpec;
		String ptUnitName = (this.ptUnitName == null || ""
				.equals(this.ptUnitName)) ? "" : "/" + this.ptUnitName;
		return this.ptName + ptSpec + ptUnitName;
	}

	public Double getF1001() {
		return f1001;
	}

	public void setF1001(Double f1001) {
		this.f1001 = f1001;
	}

	public Double getF1002() {
		return f1002;
	}

	public void setF1002(Double f1002) {
		this.f1002 = f1002;
	}

	public Double getF1003() {
		return f1003;
	}

	public void setF1003(Double f1003) {
		this.f1003 = f1003;
	}

	public Double getF1004() {
		return f1004;
	}

	public void setF1004(Double f1004) {
		this.f1004 = f1004;
	}

	public Double getF1005() {
		return f1005;
	}

	public void setF1005(Double f1005) {
		this.f1005 = f1005;
	}

	public Double getF1006() {
		return f1006;
	}

	public void setF1006(Double f1006) {
		this.f1006 = f1006;
	}

	public Double getF1007() {
		return f1007;
	}

	public void setF1007(Double f1007) {
		this.f1007 = f1007;
	}

	public Double getF1008() {
		return f1008;
	}

	public void setF1008(Double f1008) {
		this.f1008 = f1008;
	}

	public Double getF1009() {
		return f1009;
	}

	public void setF1009(Double f1009) {
		this.f1009 = f1009;
	}

	public Double getF1010() {
		return f1010;
	}

	public void setF1010(Double f1010) {
		this.f1010 = f1010;
	}

	public Double getF1011() {
		return f1011;
	}

	public void setF1011(Double f1011) {
		this.f1011 = f1011;
	}

	public Double getF1012() {
		return f1012;
	}

	public void setF1012(Double f1012) {
		this.f1012 = f1012;
	}

	public Double getF1013() {
		return f1013;
	}

	public void setF1013(Double f1013) {
		this.f1013 = f1013;
	}

	public Double getF1014() {
		return f1014;
	}

	public void setF1014(Double f1014) {
		this.f1014 = f1014;
	}

	public Double getF1015() {
		return f1015;
	}

	public void setF1015(Double f1015) {
		this.f1015 = f1015;
	}

	public Double getF1016() {
		return f1016;
	}

	public void setF1016(Double f1016) {
		this.f1016 = f1016;
	}

	public Double getF1101() {
		return f1101;
	}

	public void setF1101(Double f1101) {
		this.f1101 = f1101;
	}

	public Double getF1102() {
		return f1102;
	}

	public void setF1102(Double f1102) {
		this.f1102 = f1102;
	}

	public Double getF1103() {
		return f1103;
	}

	public void setF1103(Double f1103) {
		this.f1103 = f1103;
	}

	public Double getF1104() {
		return f1104;
	}

	public void setF1104(Double f1104) {
		this.f1104 = f1104;
	}

	public Double getF1105() {
		return f1105;
	}

	public void setF1105(Double f1105) {
		this.f1105 = f1105;
	}

	public Double getF1106() {
		return f1106;
	}

	public void setF1106(Double f1106) {
		this.f1106 = f1106;
	}

	public Double getF1107() {
		return f1107;
	}

	public void setF1107(Double f1107) {
		this.f1107 = f1107;
	}

	public Double getF1108() {
		return f1108;
	}

	public void setF1108(Double f1108) {
		this.f1108 = f1108;
	}

	public Double getF1109() {
		return f1109;
	}

	public void setF1109(Double f1109) {
		this.f1109 = f1109;
	}

	public Double getF1110() {
		return f1110;
	}

	public void setF1110(Double f1110) {
		this.f1110 = f1110;
	}

	public Double getF1111() {
		return f1111;
	}

	public void setF1111(Double f1111) {
		this.f1111 = f1111;
	}

	public Double getF2001() {
		return f2001;
	}

	public void setF2001(Double f2001) {
		this.f2001 = f2001;
	}

	public Double getF2002() {
		return f2002;
	}

	public void setF2002(Double f2002) {
		this.f2002 = f2002;
	}

	public Double getF2003() {
		return f2003;
	}

	public void setF2003(Double f2003) {
		this.f2003 = f2003;
	}

	public Double getF2004() {
		return f2004;
	}

	public void setF2004(Double f2004) {
		this.f2004 = f2004;
	}

	public Double getF2005() {
		return f2005;
	}

	public void setF2005(Double f2005) {
		this.f2005 = f2005;
	}

	public Double getF2007() {
		return f2007;
	}

	public void setF2007(Double f2007) {
		this.f2007 = f2007;
	}

	public Double getF2008() {
		return f2008;
	}

	public void setF2008(Double f2008) {
		this.f2008 = f2008;
	}

	public Double getF2009() {
		return f2009;
	}

	public void setF2009(Double f2009) {
		this.f2009 = f2009;
	}

	public Double getF2010() {
		return f2010;
	}

	public void setF2010(Double f2010) {
		this.f2010 = f2010;
	}

	public Double getF2011() {
		return f2011;
	}

	public void setF2011(Double f2011) {
		this.f2011 = f2011;
	}

	public Double getF2012() {
		return f2012;
	}

	public void setF2012(Double f2012) {
		this.f2012 = f2012;
	}

	public Double getF2101() {
		return f2101;
	}

	public void setF2101(Double f2101) {
		this.f2101 = f2101;
	}

	public Double getF2102() {
		return f2102;
	}

	public void setF2102(Double f2102) {
		this.f2102 = f2102;
	}

	public Double getF2103() {
		return f2103;
	}

	public void setF2103(Double f2103) {
		this.f2103 = f2103;
	}

	public Double getF2104() {
		return f2104;
	}

	public void setF2104(Double f2104) {
		this.f2104 = f2104;
	}

	public Double getF2105() {
		return f2105;
	}

	public void setF2105(Double f2105) {
		this.f2105 = f2105;
	}

	public Double getF2106() {
		return f2106;
	}

	public void setF2106(Double f2106) {
		this.f2106 = f2106;
	}

	public Double getF2107() {
		return f2107;
	}

	public void setF2107(Double f2107) {
		this.f2107 = f2107;
	}

	public Double getF2108() {
		return f2108;
	}

	public void setF2108(Double f2108) {
		this.f2108 = f2108;
	}

	public Double getF2109() {
		return f2109;
	}

	public void setF2109(Double f2109) {
		this.f2109 = f2109;
	}

	public Double getF2110() {
		return f2110;
	}

	public void setF2110(Double f2110) {
		this.f2110 = f2110;
	}

	public Double getF4001() {
		return f4001;
	}

	public void setF4001(Double f4001) {
		this.f4001 = f4001;
	}

	public Double getF4002() {
		return f4002;
	}

	public void setF4002(Double f4002) {
		this.f4002 = f4002;
	}

	public Double getF4003() {
		return f4003;
	}

	public void setF4003(Double f4003) {
		this.f4003 = f4003;
	}

	public Double getF4101() {
		return f4101;
	}

	public void setF4101(Double f4101) {
		this.f4101 = f4101;
	}

	public Double getF4102() {
		return f4102;
	}

	public void setF4102(Double f4102) {
		this.f4102 = f4102;
	}

	public Double getF4103() {
		return f4103;
	}

	public void setF4103(Double f4103) {
		this.f4103 = f4103;
	}

	public Double getF4104() {
		return f4104;
	}

	public void setF4104(Double f4104) {
		this.f4104 = f4104;
	}

	

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public String getPtUnitName() {
		return ptUnitName;
	}

	public void setPtUnitName(String ptUnitName) {
		this.ptUnitName = ptUnitName;
	}

	
	

	public String getPtPart() {
		return ptPart;
	}

	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}

	public Double getF2002Waste() {
		return f2002Waste;
	}

	public void setF2002Waste(Double waste) {
		f2002Waste = waste;
	}

	public Double getF2007Waste() {
		return f2007Waste;
	}

	public void setF2007Waste(Double waste) {
		f2007Waste = waste;
	}

	public Double getF2103Waste() {
		return f2103Waste;
	}

	public void setF2103Waste(Double waste) {
		f2103Waste = waste;
	}

	public Double getF2110Waste() {
		return f2110Waste;
	}

	public void setF2110Waste(Double waste) {
		f2110Waste = waste;
	}

	public Double getF4003Waste() {
		return f4003Waste;
	}

	public void setF4003Waste(Double waste) {
		f4003Waste = waste;
	}

	public Double getF4104Waste() {
		return f4104Waste;
	}

	public void setF4104Waste(Double waste) {
		f4104Waste = waste;
	}

	public Double getF4105Waste() {
		return f4105Waste;
	}

	public void setF4105Waste(Double waste) {
		f4105Waste = waste;
	}

	public Double getF4105() {
		return f4105;
	}

	public void setF4105(Double f4105) {
		this.f4105 = f4105;
	}

	public Double getF4009() {
		return f4009;
	}

	public void setF4009(Double f4009) {
		this.f4009 = f4009;
	}

	public Double getF5002() {
		return f5002;
	}

	public void setF5002(Double f5002) {
		this.f5002 = f5002;
	}
	
	
}
