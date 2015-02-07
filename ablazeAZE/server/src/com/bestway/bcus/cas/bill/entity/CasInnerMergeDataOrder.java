/*
 * Created on 2004-7-17
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.bill.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放从文本导入的内部归并资料
 * 
 * @author Administrator
 */
public class CasInnerMergeDataOrder extends BaseScmEntity {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 料号
	 */
	private Integer casF1;
	/**
	 * 工厂商品名称
	 */
	private Integer casF2;

	/**
	 * 工厂型号规格
	 */
	private Integer casF3;

	/**
	 * 净重
	 */
	private Integer casF4;

	/**
	 * 参考单价
	 */
	private Integer casF5;

	/**
	 * 单位
	 */
	private Integer casF6;

	/**
	 * 单位折算
	 */
	private Integer casF7;

	/**
	 * 报关编码
	 */
	private Integer casF8;

	/**
	 * 报关名称
	 */
	private Integer casF9;

	/**
	 * 报关规格
	 */
	private Integer casF10;

	/**
	 * 报关单位
	 */
	private Integer casF11;

	/**
	 * 手册号
	 */
	private Integer casF12;

	/**
	 * 归并序号
	 */
	private Integer casF13;

	/**
	 * 手册开始日期
	 */
	private Integer casF14;

	/**
	 * 手册结束日期
	 */
	private Integer casF15;

	/**
	 * 管理类型
	 */
	private Integer casF16;

	/**
	 * 成品版本号
	 */
	private Integer casF17;
	
	/**
	 * 客户供应商
	 */
	private Integer casF18;
	
	/**
	 * 备注
	 */
	private Integer remark;
	
	
	public Integer getCasF1() {
		return casF1;
	}

	public void setCasF1(Integer casF1) {
		this.casF1 = casF1;
	}

	public Integer getCasF10() {
		return casF10;
	}

	public void setCasF10(Integer casF10) {
		this.casF10 = casF10;
	}

	public Integer getCasF11() {
		return casF11;
	}

	public void setCasF11(Integer casF11) {
		this.casF11 = casF11;
	}

	public Integer getCasF12() {
		return casF12;
	}

	public void setCasF12(Integer casF12) {
		this.casF12 = casF12;
	}

	public Integer getCasF13() {
		return casF13;
	}

	public void setCasF13(Integer casF13) {
		this.casF13 = casF13;
	}

	public Integer getCasF14() {
		return casF14;
	}

	public void setCasF14(Integer casF14) {
		this.casF14 = casF14;
	}

	public Integer getCasF15() {
		return casF15;
	}

	public void setCasF15(Integer casF15) {
		this.casF15 = casF15;
	}

	public Integer getCasF16() {
		return casF16;
	}

	public void setCasF16(Integer casF16) {
		this.casF16 = casF16;
	}

	public Integer getCasF17() {
		return casF17;
	}

	public void setCasF17(Integer casF17) {
		this.casF17 = casF17;
	}

	public Integer getCasF2() {
		return casF2;
	}

	public void setCasF2(Integer casF2) {
		this.casF2 = casF2;
	}

	public Integer getCasF3() {
		return casF3;
	}

	public void setCasF3(Integer casF3) {
		this.casF3 = casF3;
	}

	public Integer getCasF4() {
		return casF4;
	}

	public void setCasF4(Integer casF4) {
		this.casF4 = casF4;
	}

	public Integer getCasF5() {
		return casF5;
	}

	public void setCasF5(Integer casF5) {
		this.casF5 = casF5;
	}

	public Integer getCasF6() {
		return casF6;
	}

	public void setCasF6(Integer casF6) {
		this.casF6 = casF6;
	}

	public Integer getCasF7() {
		return casF7;
	}

	public void setCasF7(Integer casF7) {
		this.casF7 = casF7;
	}

	public Integer getCasF8() {
		return casF8;
	}

	public void setCasF8(Integer casF8) {
		this.casF8 = casF8;
	}

	public Integer getCasF9() {
		return casF9;
	}

	public void setCasF9(Integer casF9) {
		this.casF9 = casF9;
	}

	public Integer getCasF18() {
		return casF18;
	}

	public void setCasF18(Integer casF18) {
		this.casF18 = casF18;
	}

	public Integer getRemark() {
		return remark;
	}

	public void setRemark(Integer remark) {
		this.remark = remark;
	}

}
