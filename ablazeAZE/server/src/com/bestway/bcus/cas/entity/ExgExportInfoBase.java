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
 * 加工贸易产品流向情况表基本资料
 */
public class ExgExportInfoBase extends BaseScmEntity implements Serializable,Comparable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
	 *截止日期 
	 */
	private String year;
	/**
	 * 是否是海关名称
	 */
	private Boolean customName=false; 
	/**
	 *成品名称
	 */
	private String ptName;
	/**
	 *成品规格
	 */
	private String ptSpec;
	/**
	 *单位
	 */
	private String ptUnitName;
	/**
	 *期末库存
	 */
	private Double f1 = 0.0; 
	/**
	 *直接报关出口
	 */
	private Double f2 = 0.0; 
	/**
	 *结转报关出口
	 */
	private Double f3 = 0.0; 
	/**
	 *已结转未交货
	 */
	private Double f4 = 0.0; 
	/**
	 *未结转已交货
	 */
	private Double f5 = 0.0; 
	/**
	 *海关批准内销
	 */
	private Double f6 = 0.0; 
	/**
	 *其它内销
	 */
	private Double f7 = 0.0; 
	/**
	 *受托加工返回
	 */
	private Double f8 = 0.0; 
	/**
	 *其它处理
	 */
	private Double f9 = 0.0; 
	/**
	 *期初库存
	 */
	private Double f10 = 0.0; 
	/**
	 *产量合计
	 */
	private Double f11 = 0.0; 
	/**
	 *备注
	 */
	private String f12 = ""; 
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
     * 已交货未结转期初单
     */
    private Double materiel2011 = 0.0;
    /**
     * 已结转未交货期初单
     */
    private Double materiel2012 = 0.0;
	
	
	/**
	 * 判断是否是海关名称
	 * @return customName 是否是海关名称.
	 */
	public Boolean getCustomName() {
		return customName;
	}
	/**
	 * 设置是否是海关名称  
	 * @param customName 是否是海关名称.
	 */
	public void setCustomName(Boolean customName) {
		this.customName = customName;
	}
	/**
	 * 取得期末库存
	 * @return f1 期末库存.
	 */
	public Double getF1() {
		return f1;
	}
	/**
	 * 设置期末库存
	 * @param f1 期末库存
	 */
	public void setF1(Double f1) {
		this.f1 = f1;
	}
	/**
	 * 取得期初库存
	 * @return f10 期初库存.
	 */
	public Double getF10() {
		return f10;
	}
	/**
	 * 设置期初库存
	 * @param f10 期初库存.
	 */
	public void setF10(Double f10) {
		this.f10 = f10;
	}
	/** 
	 * 取得产量合计
	 * @return f1+f2+f3+f4+f5+f6+f7+f8+f9-f10  期末库存+直接报关出口+结转报关出口+已结转未交货+未结转已交货+海关批准内销+其它内销
	 *                                                 +受托加工返回+其它处理-期初库存.
	 */
	public Double getF11() {
		return f1+f2+f3-f4+f5+f6+f7+f8+f9-f10;
	}
	/**
	 * 取得备注
	 * @return f12 备注.
	 */
   
	public String getF12() {
		return f12;
	}
	/**
	 * 设置备注
	 * @param f12 备注
	 */
	public void setF12(String f12) {
		this.f12 = f12;
	}
	/**
	 * 取得直接报关出口
	 * @return f2 直接报关出口.
	 */
	public Double getF2() {
		return f2;
	}
	/**
	 * 设置直接报关出口
	 * @param f2 直接报关出口
	 */
	public void setF2(Double f2) {
		this.f2 = f2;
	}
	/**
	 * 取得结转报关出口
	 * @return f3 结转报关出口.
	 */
	public Double getF3() {
		return f3;
	}
	/**
	 * 设置结转报关出口
	 * @param f3 结转报关出口.
	 */
	public void setF3(Double f3) {
		this.f3 = f3;
	}
	/**
	 * 取得已结转未交货
	 * @return f4 已结转未交货.
	 */
	public Double getF4() {
		return f4;
	}
	/**
	 * 设置已结转未交货
	 * @param f4 已结转未交货.
	 */
	public void setF4(Double f4) {
		this.f4 = f4;
	}
	/**
	 * 取得未结转已交货
	 * @return f5 未结转已交货.
	 */
	public Double getF5() {
		return f5;
	}
	/**
	 * 设置未结转已交货
	 * @param f5 未结转已交货.
	 */
	public void setF5(Double f5) {
		this.f5 = f5;
	}
	/**
	 * 取得海关批准内销
	 * @return f6 海关批准内销.
	 */
	public Double getF6() {
		return f6;
	}
	/**
	 * 设置海关批准内销
	 * @param f6 海关批准内销.
	 */
	public void setF6(Double f6) {
		this.f6 = f6;
	}
	/**
	 * 取得其它内销
	 * @return f7 其它内销.
	 */
	public Double getF7() {
		return f7;
	}
	/**
	 * 设置其它内销
	 * @param f7 其它内销.
	 */
	public void setF7(Double f7) {
		this.f7 = f7;
	}
	/**
	 * 取得受托加工返回
	 * @return f8 受托加工返回.
	 */
	public Double getF8() {
		return f8;
	}
	/**
	 * 设置受托加工返回
	 * @param f8 受托加工返回.
	 */
	public void setF8(Double f8) {
		this.f8 = f8;
	}
	/**
	 * 取得其它处理
	 * @return f9 其它处理.
	 */
	public Double getF9() {
		return f9;
	}
	/**
	 * 设置其它处理
	 * @param f9 其它处理.
	 */
	public void setF9(Double f9) {
		this.f9 = f9;
	}
	/**
	 * 取得金额
	 * @return money 金额.
	 */
	public Double getMoney() {
		return money;
	}
	/**
	 * 设置金额
	 * @param money 金额.
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
	 * 取得成品名称
	 * @return ptName 成品名称.
	 */
	public String getPtName() {
		return ptName;
	}
	/**
	 * 设置成品名称
	 * @param ptName 成品名称.
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	/**
	 * 取得成品规格
	 * @return ptSpec 成品规格.
	 */
	public String getPtSpec() {
		return ptSpec;
	}
	/**
	 * 设置成品规格
	 * @param ptSpec 成品规格.
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}
	/**
	 * 取得单位
	 * @return ptUnitName.
	 */
	public String getPtUnitName() {
		return ptUnitName;
	}
	/**
	 * 设置单位
	 * @param ptUnitName 单位.
	 */
	public void setPtUnitName(String ptUnitName) {
		this.ptUnitName = ptUnitName;
	}
	/**
	 * 取得截止日期
	 * @return year 截止日期.
	 */
	public String getYear() {
		return year;
	}
	/**
	 * 设置截止日期
	 * @param year 截止日期.
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * 取得键值（唯一）
	 * @return ptName+ptSpec+ptUnitName 成品名称+成品规格+单位.
	 */
	public String getKey(){
        String ptSpec  = (this.ptSpec == null || "".equals(this.ptSpec))?"":"/"+this.ptSpec;   
        String ptUnitName = (this.ptUnitName == null || "".equals(this.ptUnitName))?"":"/"+this.ptUnitName; 
        return this.ptName+ptSpec+ptUnitName;
	}
	/**
	 * 重新排序 按照成品名称 规格 单位重新排序
	 */
	public int compareTo(Object arg0) {		
		ExgExportInfoBase info = (ExgExportInfoBase)arg0;
		if(!this.getPtName().equals(info.getPtName())){
			return this.getPtName().compareTo(info.getPtName());
		}
		else if(!this.getPtUnitName().equals(info.getPtSpec())){
			return this.getPtSpec().compareTo(info.getPtSpec());
		}
		else {
			return this.getPtUnitName().compareTo(info.getPtUnitName());
		}

	}
	/**
	 * @return the materiel2011
	 */
	public Double getMateriel2011() {
		return materiel2011;
	}
	/**
	 * @param materiel2011 the materiel2011 to set
	 */
	public void setMateriel2011(Double materiel2011) {
		this.materiel2011 = materiel2011;
	}
	/**
	 * @return the materiel2012
	 */
	public Double getMateriel2012() {
		return materiel2012;
	}
	/**
	 * @param materiel2012 the materiel2012 to set
	 */
	public void setMateriel2012(Double materiel2012) {
		this.materiel2012 = materiel2012;
	}
}
