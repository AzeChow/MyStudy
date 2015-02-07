/*
 * Created on 2004-10-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 加工贸易原材料来源与使用情况表(自用资料)基本资料
 */
public class ImgOrgUseInfoBase extends BaseScmEntity implements Serializable,
		Comparable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 *截止日期
	 */
	private String year;
	/**
	 * 是否是海关名称
	 */
	private Boolean customName = false;
	/**
	 *原材料名称
	 */
	private String ptName;
	/**
	 *原材料规格
	 */
	private String ptSpec;
	/**
	 *单位
	 */
	private String ptUnitName;
	/**
	 *期初库存
	 */
	private Double f1 = 0.0;
	/**
	 *直接报关进口
	 */
	private Double f2 = 0.0;
	/**
	 *结转报关进口
	 */
	private Double f3 = 0.0;
	/**
	 *已结转未收货
	 */
	private Double f4 = 0.0;
	/**
	 *未结转已收货
	 */
	private Double f5 = 0.0;
	/**
	 *其中：海关征税进口
	 */
	private Double f6 = 0.0;
	/**
	 *国内采购
	 */
	private Double f7 = 0.0;
	/**
	 *受托加工
	 */
	private Double f8 = 0.0;
	/**
	 *外发加工返回料件
	 */
	private Double f9 = 0.0;
	/**
	 *其它来源
	 */
	private Double f10 = 0.0;
	/**
	 *来源合计
	 */
	private Double f11 = 0.0;
	/**
	 *直接出口成品耗用
	 */
	private Double f12 = 0.0;
	/**
	 *实际结转成品耗用
	 */
	private Double f13 = 0.0;
	/**
	 *海关批准内销耗用
	 */
	private Double f14 = 0.0;
	/**
	 *退运出口
	 */
	private Double f15 = 0.0;
	/**
	 *其它内销耗用
	 */
	private Double f16 = 0.0;
	/**
	 *受托加工返回耗用
	 */
	private Double f17 = 0.0;
	/**
	 *外发加工出库
	 */
	private Double f18 = 0.0;
	/**
	 *外发返回成品耗用
	 */
	private Double f19 = 0.0;
	/**
	 *损耗
	 */
	private Double f20 = 0.0;
	/**
	 *其它使用
	 */
	private Double f21 = 0.0;
	/**
	 *使用合计
	 */
	private Double f22 = 0.0;
	/**
	 *期末实际库存
	 */
	private Double f23 = 0.0;
	/**
	 *其中：库存原材料
	 */
	private Double f24 = 0.0;
	/**
	 *库存在产品耗用
	 */
	private Double f25 = 0.0;
	/**
	 *库存产成品耗用
	 */
	private Double f26 = 0.0;
	/**
	 *备注
	 */
	private String f27 = "";
	/**
	 *金额
	 */
	private Double money = 0.0;
	
	/**
	 * 非报关单 部分的  金额
	 * wss:2010.04.30新加（为方便计算 从报关单中统计金额）
	 */
	private Double noCustomMoney = 0.0;
	/**
	 * S 料件退换数量
	 */
	private Double materialExchange = 0.0;
	/**
	 * 已收货未结转期初单
	 */
	private Double materiel1015 = 0.0;
	/**
	 * 已结转未收货期初单
	 */
	private Double materiel1016 = 0.0;

	/**
	 * 取得期初库存
	 * 
	 * @return f1 期初库存.
	 */
	public Double getF1() {
		return f1;
	}

	/**
	 * 设置期初库存
	 * 
	 * @param f1
	 *            期初库存.
	 */
	public void setF1(Double f1) {
		this.f1 = f1;
	}

	/**
	 * 取得其它来源
	 * 
	 * @return f10 其它来源.
	 */
	public Double getF10() {
		return f10;
	}

	/**
	 * 设置其它来源
	 * 
	 * @param f10
	 *            其它来源.
	 */
	public void setF10(Double f10) {
		this.f10 = f10;
	}

	/**
	 * 取得来源合计
	 * 
	 * @return f1+f2+f3+f5+f7+f8+f9+f10 期初库存+直接报关进口+结转报关进口+未结转已收货+国内采购
	 *         受托加工+外发加工返回料件+其它来源.
	 */
	public Double getF11() {
		return f1 + f2 + f3 + f5 + f7 + f8 + f9 + f10 - f4;
	}

	/**
	 * 取得直接出口成品耗用
	 * 
	 * @return f12 直接出口成品耗用.
	 */
	public Double getF12() {
		return f12;
	}

	/**
	 * 设置直接出口成品耗用
	 * 
	 * @param f12
	 *            直接出口成品耗用.
	 */
	public void setF12(Double f12) {
		this.f12 = f12;
	}

	/**
	 * 取得实际结转成品耗用
	 * 
	 * @return f13 实际结转成品耗用.
	 */
	public Double getF13() {
		return f13;
	}

	/**
	 * 设置实际结转成品耗用
	 * 
	 * @param f13
	 *            实际结转成品耗用.
	 */
	public void setF13(Double f13) {
		this.f13 = f13;
	}

	/**
	 * 取得海关批准内销耗用
	 * 
	 * @return f14 海关批准内销耗用.
	 */
	public Double getF14() {
		return f14;
	}

	/**
	 * 设置海关批准内销耗用
	 * 
	 * @param f14
	 *            海关批准内销耗用.
	 */
	public void setF14(Double f14) {
		this.f14 = f14;
	}

	/**
	 * 取得退运出口（料件复出）
	 * 
	 * @return f15 退运出口（料件复出）.
	 */
	public Double getF15() {
		return f15;
	}

	/**
	 * 设置退运出口（料件复出）
	 * 
	 * @param f15
	 *            退运出口（料件复出）.
	 */
	public void setF15(Double f15) {
		this.f15 = f15;
	}

	/**
	 * 取得其它内销耗用
	 * 
	 * @return f16 其它内销耗用.
	 */
	public Double getF16() {
		return f16;
	}

	/**
	 * 设置其它内销耗用
	 * 
	 * @param f16
	 *            其它内销耗用.
	 */
	public void setF16(Double f16) {
		this.f16 = f16;
	}

	/**
	 * 取得受托加工返回耗用
	 * 
	 * @return f17 受托加工返回耗用.
	 */
	public Double getF17() {
		return f17;
	}

	/**
	 * 设置受托加工返回耗用
	 * 
	 * @param f17
	 *            受托加工返回耗用.
	 */
	public void setF17(Double f17) {
		this.f17 = f17;
	}

	/**
	 * 取得外发加工出库
	 * 
	 * @return f18 外发加工出库.
	 */
	public Double getF18() {
		return f18;
	}

	/**
	 * 设置外发加工出库
	 * 
	 * @param f18
	 *            外发加工出库.
	 */
	public void setF18(Double f18) {
		this.f18 = f18;
	}

	/**
	 * 取得外发返回成品耗用
	 * 
	 * @return f19 外发返回成品耗用.
	 */
	public Double getF19() {
		return f19;
	}

	/**
	 * 设置外发返回成品耗用
	 * 
	 * @param f19
	 *            外发返回成品耗用.
	 */
	public void setF19(Double f19) {
		this.f19 = f19;
	}

	/**
	 * 取得直接报关进口
	 * 
	 * @return f2 直接报关进口.
	 */
	public Double getF2() {
		return f2;
	}

	/**
	 * 设置直接报关进口
	 * 
	 * @param f2
	 *            直接报关进口.
	 */
	public void setF2(Double f2) {
		this.f2 = f2;
	}

	/**
	 * 取得损耗
	 * 
	 * @return f20 损耗.
	 */
	public Double getF20() {
		return f20;
	}

	/**
	 * 设置损耗
	 * 
	 * @param f20
	 *            损耗.
	 */
	public void setF20(Double f20) {
		this.f20 = f20;
	}

	/**
	 * 取得其它使用
	 * 
	 * @return f21 其它使用.
	 */
	public Double getF21() {
		return f21;
	}

	/**
	 * 设置其它使用
	 * 
	 * @param f21
	 *            其它使用.
	 */
	public void setF21(Double f21) {
		this.f21 = f21;
	}

	/**
	 * 计算使用合计
	 * 
	 * @return f22=f12+f13+f14+f15+f16+f17+f18-f19+f20+f21 直接出口成品耗用+实际结转成品耗用+
	 *         海关批准内销耗用+退运出口+其它内销耗用. 受托加工返回耗用+外发加工出库-外发返回成品耗用+损耗+其它使用
	 */
	public Double getF22() {
		return f12 + f13 + f14 + f15 + f16 + f17 + f18 - f19 + f20 + f21;
	}

	/**
	 * 计算期末实际库存
	 * 
	 * @return f23=f24+f25+f26 期末实际库存=库存原材料+库存在产品耗用+库存产成品耗用.
	 */
	public Double getF23() {
		return f24 + f25 + f26;
	}

	/**
	 * 设置期末实际库存
	 * 
	 * @param f23
	 *            期末实际库存.
	 */
	public void setF23(Double f23) {
		this.f23 = f23;
	}

	/**
	 * 取得库存原材料
	 * 
	 * @return f24 库存原材料.
	 */
	public Double getF24() {
		return f24;
	}

	/**
	 * 设置库存原材料
	 * 
	 * @param f24
	 *            库存原材料.
	 */
	public void setF24(Double f24) {
		this.f24 = f24;
	}

	/**
	 * 取得库存在产品耗用
	 * 
	 * @return f25 库存在产品耗用.
	 */
	public Double getF25() {
		return f25;
	}

	/**
	 * 取得库存产成品耗用
	 * 
	 * @return f26 库存产成品耗用.
	 */
	public Double getF26() {
		return f26;
	}

	/**
	 * 设置库存产成品耗用
	 * 
	 * @param f26
	 *            库存产成品耗用.
	 */
	public void setF26(Double f26) {
		this.f26 = f26;
	}

	/**
	 * 取得备注内容
	 * 
	 * @return f27 备注.
	 */
	public String getF27() {
		return f27;
	}

	/**
	 * 设置备注
	 * 
	 * @param f27
	 *            备注.
	 */
	public void setF27(String f27) {
		this.f27 = f27;
	}

	/**
	 * 计算金额
	 * 
	 * @return money 金额.
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * 设置金额
	 * 
	 * @param money
	 *            金额.
	 */
	public void setMoney(Double money) {
		this.money = money;
	}
	
	/**
	 * 取得非报关单 部分的金额
	 * @return
	 */

	public Double getNoCustomMoney() {
		return noCustomMoney;
	}

	/**
	 * 设置非报关单 部分的金额
	 * @param noCustomMoney
	 */
	public void setNoCustomMoney(Double noCustomMoney) {
		this.noCustomMoney = noCustomMoney;
	}

	/**
	 * 取得结转报关进口
	 * 
	 * @return f3 结转报关进口.
	 */
	public Double getF3() {
		return f3;
	}

	/**
	 * 设置结转报关进口
	 * 
	 * @param f3
	 *            结转报关进口.
	 */
	public void setF3(Double f3) {
		this.f3 = f3;
	}

	/**
	 * 取得已结转未收货
	 * 
	 * @return f4 已结转未收货.
	 */
	public Double getF4() {
		return f4;
	}

	/**
	 * 设置已结转未收货
	 * 
	 * @param f4
	 *            已结转未收货.
	 */
	public void setF4(Double f4) {
		this.f4 = f4;
	}

	/**
	 * 取得未结转已收货
	 * 
	 * @return f5 未结转已收货.
	 */
	public Double getF5() {
		return f5;
	}

	/**
	 * 设置未结转已收货
	 * 
	 * @param f5
	 *            未结转已收货.
	 */
	public void setF5(Double f5) {
		this.f5 = f5;
	}

	/**
	 * 取得未结转已收货
	 * 
	 * @return f6 未结转已收货.
	 */
	public Double getF6() {
		return f6;
	}

	/**
	 * 设置未结转已收货
	 * 
	 * @param f6
	 *            未结转已收货.
	 */
	public void setF6(Double f6) {
		this.f6 = f6;
	}

	/**
	 * 取得国内采购
	 * 
	 * @return f7 国内采购.
	 */
	public Double getF7() {
		return f7;
	}

	/**
	 * 设置国内采购
	 * 
	 * @param f7
	 *            国内采购.
	 */
	public void setF7(Double f7) {
		this.f7 = f7;
	}

	/**
	 * 取得受托加工
	 * 
	 * @return f8 受托加工.
	 */
	public Double getF8() {
		return f8;
	}

	/**
	 * 设置受托加工
	 * 
	 * @param f8
	 *            受托加工.
	 */
	public void setF8(Double f8) {
		this.f8 = f8;
	}

	/**
	 * 取得外发加工返回料件
	 * 
	 * @return f9 外发加工返回料件.
	 */
	public Double getF9() {
		return f9;
	}

	/**
	 * 设置外发加工返回料件
	 * 
	 * @param f9
	 *            外发加工返回料件.
	 */
	public void setF9(Double f9) {
		this.f9 = f9;
	}

	/**
	 * 取得原材料名称
	 * 
	 * @return ptName 原材料名称.
	 */
	public String getPtName() {
		return ptName;
	}

	/**
	 * 设置原材料名称
	 * 
	 * @param ptName
	 *            原材料名称.
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	/**
	 * 取得原材料规格
	 * 
	 * @return ptSpec 原材料规格.
	 */
	public String getPtSpec() {
		return ptSpec;
	}

	/**
	 * 设置原材料规格
	 * 
	 * @param ptSpec
	 *            原材料规格.
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	/**
	 * 取得单位
	 * 
	 * @return ptUnitName 单位.
	 */
	public String getPtUnitName() {
		return ptUnitName;
	}

	/**
	 * 设置单位
	 * 
	 * @param ptUnitName
	 *            单位.
	 */
	public void setPtUnitName(String ptUnitName) {
		this.ptUnitName = ptUnitName;
	}

	/**
	 * 取得键值（唯一）
	 * 
	 * @return ptName+ptSpec+ptUnitName 原材料名称+原材料规格+单位.
	 */
	public String getKey() {
		String ptName = (this.ptName == null || "".equals(this.ptName)) ? ""
				: this.ptName + "/";
		String ptSpec = (this.ptSpec == null || "".equals(this.ptSpec)) ? ""
				: "/" + this.ptSpec;
		String ptUnitName = (this.ptUnitName == null || ""
				.equals(this.ptUnitName)) ? "" : "/" + this.ptUnitName;
		return ptName + this.ptSpec + ptUnitName;
	}

	/**
	 * 设置来源合计
	 * 
	 * @param f11
	 *            来源合计.
	 */
	public void setF11(Double f11) {
		this.f11 = f11;
	}

	/**
	 * 设置使用合计
	 * 
	 * @param f22
	 *            使用合计.
	 */
	public void setF22(Double f22) {
		this.f22 = f22;
	}

	/**
	 * 设置库存在产品耗用
	 * 
	 * @param f25
	 *            库存在产品耗用.
	 */
	public void setF25(Double f25) {
		this.f25 = f25;
	}

	/**
	 * 取得截止日期
	 * 
	 * @return year 截止日期.
	 */
	public String getYear() {
		return year;
	}

	/**
	 * 设置截止日期
	 * 
	 * @param year
	 *            截止日期.
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * 判断是否是海关名称
	 * 
	 * @return customName 是否是海关名称.
	 */
	public Boolean getCustomName() {
		return customName;
	}

	/**
	 * 设置是否是海关名称标志
	 * 
	 * @param customName
	 *            是否是海关名称.
	 */
	public void setCustomName(Boolean customName) {
		this.customName = customName;
	}

	/**
	 * 重新排序 按照原材料名称 规格 单位重新排序
	 */
	public int compareTo(Object arg0) {
		ImgOrgUseInfoBase info = (ImgOrgUseInfoBase) arg0;
		if (!this.getPtName().equals(info.getPtName())) {
			return this.getPtName().compareTo(info.getPtName());
		} else if (!this.getPtUnitName().equals(info.getPtSpec())) {
			return this.getPtSpec().compareTo(info.getPtSpec());
		} else {
			return this.getPtUnitName().compareTo(info.getPtUnitName());
		}

	}

	/**
	 * 取得料件退换数量
	 * 
	 * @return materialExchange 料件退换数量.
	 */
	public Double getMaterialExchange() {
		return materialExchange;
	}

	/**
	 * 设置料件退换数量
	 * 
	 * @param materialExchange
	 *            料件退换数量.
	 */
	public void setMaterialExchange(Double materialExchange) {
		this.materialExchange = materialExchange;
	}

	/**
	 * @return the materiel1015
	 */
	public Double getMateriel1015() {
		return materiel1015;
	}

	/**
	 * @param materiel1015
	 *            the materiel1015 to set
	 */
	public void setMateriel1015(Double materiel1015) {
		this.materiel1015 = materiel1015;
	}

	/**
	 * @return the materiel1016
	 */
	public Double getMateriel1016() {
		return materiel1016;
	}

	/**
	 * @param materiel1016
	 *            the materiel1016 to set
	 */
	public void setMateriel1016(Double materiel1016) {
		this.materiel1016 = materiel1016;
	}

}
