package com.bestway.bcus.cas.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 平衡信息表
 */
/**
 * @author Administrator
 * 
 */
public class BalanceInfo extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 名称
	 */
	private String name = "";
	/**
	 * 规格
	 */
	private String spec = "";
	/**
	 * 单位
	 */
	private String unitName = "";
	/**
	 * 非保税库存原料 wss:2010.07.20新加
	 */
	private Double f0 = 0.0;
	/**
	 * 库存原料
	 */
	private Double f1 = 0.0;
	/**
	 * 半成品折原料
	 */
	private Double f2 = 0.0;
	/**
	 * 成品折原料
	 */
	private Double f3 = 0.0;
	/**
	 * 外发加工/厂外存放数
	 */
	private Double f4 = 0.0;
	/**
	 * 实际库存总数 f1+f2+f3+f4
	 */
	private Double f5 = 0.0;
	/**
	 * 已转厂未收货数
	 */
	private Double f6 = 0.0;
	/**
	 * 已送货未转厂数
	 */
	private Double f7 = 0.0;
	/**
	 * 已转厂未送货数
	 */
	private Double f8 = 0.0;
	/**
	 * 已收货未转厂数
	 */
	private Double f9 = 0.0;
	/**
	 * 应有库存数 f5+f6+f7-f8-f9
	 */
	private Double f10 = 0.0;
	/**
	 * 合同进口数
	 */
	private Double f11 = 0.0;
	/**
	 * 合同出口耗料
	 */
	private Double f12 = 0.0;
	/**
	 * 合同结余数 f11-f12
	 */
	private Double f13 = 0.0;
	/**
	 * 短溢情况 f10-f13
	 */
	private Double f14 = 0.0;

	/**
	 * 后续补税数
	 */
	private Double f15 = 0.0;

	/**
	 * 获取后续补税数
	 * 
	 * @return
	 */
	public Double getF15() {
		return f15;
	}

	/**
	 * 料件内销数
	 */
	private Double f16 = 0.0;

	/**
	 * 国内购买 wss:2010.07.20新加
	 */
	private Double f18 = 0.0;

	/**
	 * 设置后续补税数
	 * 
	 * @param f15
	 */
	public void setF15(Double f15) {
		this.f15 = f15;
	}

	/**
	 * 获取料件内销数
	 * 
	 * @return
	 */
	public Double getF16() {
		return f16;
	}

	/**
	 * 设置料件内销数
	 * 
	 * @param f16
	 */
	public void setF16(Double f16) {
		this.f16 = f16;
	}

	/**
	 * 出错原因
	 */
	private String ErrorReason = null;

	/**
     * 
     */
	private int[] invalidationColNo = null;

	/**
	 * 取得唯一键值
	 * 
	 * @return name+ptSpec+ptUnitName 名称+规格+单位.
	 */
	public String getKey() {
		// String ptSpec = (this.spec == null ||
		// "".equals(this.spec))?"":"/"+this.spec;
		// String ptUnitName = (this.unitName == null ||
		// "".equals(this.unitName))?"":"/"+this.unitName;

		String ptSpec = (this.spec == null || "".equals(this.spec)) ? ""
				: this.spec;
		String ptUnitName = (this.unitName == null || "".equals(this.unitName)) ? ""
				: this.unitName;

		return this.name + "/" + ptSpec + "/" + ptUnitName;
		// return this.name+"/"+spec+"/"+unitName;
	}

	/**
	 * 取得库存原材料
	 * 
	 * @return f1 库存原材料.
	 */
	public Double getF1() {
		return f1;
	}

	/**
	 * 设置库存原材料
	 * 
	 * @param f1
	 *            库存原材料.
	 */
	public void setF1(Double f1) {
		this.f1 = f1;
	}

	/**
	 * 取得应有库存数
	 * 
	 * @return f10 应有库存数 f5+f6+f7-f8-f9.
	 */
	public Double getF10() {
		return (f5 == null ? 0.0 : f5) + (f6 == null ? 0.0 : f6)
				+ (f7 == null ? 0.0 : f7) - (f8 == null ? 0.0 : f8)
				- (f9 == null ? 0.0 : f9);
	}

	/**
	 * 取得合同进口数
	 * 
	 * @return f11 合同进口数.
	 */
	public Double getF11() {
		return f11;
	}

	/**
	 * 设置合同进口数
	 * 
	 * @param f11
	 *            合同进口数.
	 */
	public void setF11(Double f11) {
		this.f11 = f11;
	}

	/**
	 * 取得合同出口数
	 * 
	 * @return f12 合同出口数.
	 */
	public Double getF12() {
		return f12;
	}

	/**
	 * 设置合同出口数
	 * 
	 * @param f12
	 *            合同出口数.
	 */
	public void setF12(Double f12) {
		this.f12 = f12;
	}

	/**
	 * 取得合同结余数
	 * 
	 * @return f13 合同结余数 f11-f12.
	 */
	public Double getF13() {
		return f11 - f12;
	}

	/**
	 * 取得短溢情况
	 * 
	 * @return f14 短溢情况 f10-f13.
	 */
	public Double getF14() {
		return (getF10() == null ? 0.0 : getF10())
				- (getF13() == null ? 0.0 : getF13())
				- (getF15() == null ? 0.0 : getF15())
				- (getF18() == null ? 0.0 : getF18());
	}

	/**
	 * 取得半成品折原料
	 * 
	 * @return f2 半成品折原料.
	 */
	public Double getF2() {
		return f2;
	}

	/**
	 * 设置半成品折原料
	 * 
	 * @param f2
	 *            半成品折原料.
	 */
	public void setF2(Double f2) {
		this.f2 = f2;
	}

	/**
	 * 取得成品折原料
	 * 
	 * @return f3 成品折原料.
	 */
	public Double getF3() {
		return f3;
	}

	/**
	 * 设置成品折原料
	 * 
	 * @param f3
	 *            成品折原料.
	 */
	public void setF3(Double f3) {
		this.f3 = f3;
	}

	/**
	 * 取得外发加工/厂外存放数
	 * 
	 * @return f4 外发加工/厂外存放数.
	 */
	public Double getF4() {
		return f4;
	}

	/**
	 * 设置外发加工/厂外存放数
	 * 
	 * @param f4
	 *            外发加工/厂外存放数.
	 */
	public void setF4(Double f4) {
		this.f4 = f4;
	}

	/**
	 * 取得实际库存总数 f1+f2+f3+f4
	 * 
	 * @return f5 实际库存总数 f1+f2+f3+f4+f0.
	 */
	public Double getF5() {
		return f5;
	}

	/**
	 * 设置实际库存总数 f1+f2+f3+f4
	 * 
	 * @param f5
	 *            实际库存总数 f1+f2+f3+f4.
	 */
	public void setF5(Double f5) {
		this.f5 = f5;
	}

	/**
	 * 取得已转厂未收货数
	 * 
	 * @return f6 已转厂未收货.
	 */
	public Double getF6() {
		return f6;
	}

	/**
	 * 设置已转厂未收货数
	 * 
	 * @param f6
	 *            已转厂未收货.
	 */
	public void setF6(Double f6) {
		this.f6 = f6;
	}

	/**
	 * 取得已送货未转厂数
	 * 
	 * @return f7 已送货未转厂数.
	 */
	public Double getF7() {
		return f7;
	}

	/**
	 * 设置已送货未转厂数
	 * 
	 * @param f7
	 *            已送货未转厂数.
	 */
	public void setF7(Double f7) {
		this.f7 = f7;
	}

	/**
	 * 取得已转厂未送货数
	 * 
	 * @return f8 已转厂未送货数.
	 */
	public Double getF8() {
		return f8;
	}

	/**
	 * 设置已转厂未送货数
	 * 
	 * @param f8
	 *            已转厂未送货.
	 */
	public void setF8(Double f8) {
		this.f8 = f8;
	}

	/**
	 * 取得已收货未转厂数
	 * 
	 * @return f9 已收货未转厂数.
	 */
	public Double getF9() {
		return f9;
	}

	/**
	 * 设置已收货未转厂数
	 * 
	 * @param f9
	 *            已收货未转厂数.
	 */
	public void setF9(Double f9) {
		this.f9 = f9;
	}

	/**
	 * 取得商品名称
	 * 
	 * @return name 商品名称.
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param name
	 *            商品名称.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 取得商品规格
	 * 
	 * @return spec 商品规格.
	 */
	public String getSpec() {
		return spec;
	}

	/**
	 * 设置商品规格
	 * 
	 * @param spec
	 *            商品规格.
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}

	/**
	 * 取得单位名称
	 * 
	 * @return unitName 单位名称.
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * 设置单位名称
	 * 
	 * @param unitName
	 *            单位名称.
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getErrorReason() {
		return ErrorReason;
	}

	public void setErrorReason(String errorReason) {
		ErrorReason = errorReason;
	}

	public int[] getInvalidationColNo() {
		return invalidationColNo;
	}

	public void setInvalidationColNo(int[] invalidationColNo) {
		this.invalidationColNo = invalidationColNo;
	}

	public void setF10(Double f10) {
		this.f10 = f10;
	}

	public void setF13(Double f13) {
		this.f13 = f13;
	}

	public void setF14(Double f14) {
		this.f14 = f14;
	}

	public Double getF0() {
		return f0;
	}

	public void setF0(Double f0) {
		this.f0 = f0;
	}

	public Double getF18() {
		return f18;
	}

	public void setF18(Double f18) {
		this.f18 = f18;
	}

}
