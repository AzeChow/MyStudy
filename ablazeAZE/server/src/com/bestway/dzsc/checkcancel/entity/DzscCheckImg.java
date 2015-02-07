/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.checkcancel.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放电子手册中期核查料件资料
 * 
 * @author Administrator
 */
public class DzscCheckImg extends BaseScmEntity implements Cloneable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 中期核查表头
	 */
	private DzscCheckHead checkHead;

	/**
	 * 序号
	 */
	private String seqNum;

	/**
	 * 料号
	 */
	private String ptNum;

	/**
	 * 料件名称
	 */
	private String name;

	/**
	 * 商品编码
	 */
	private Complex complex;

	/**
	 * 规格型号
	 */
	private String spec;

	/**
	 * 计量单位
	 */
	private Unit unit;

	/**
	 * 转进未报数量
	 */
	private Double turnInNoReport;

	/**
	 * 原料在途数量
	 */
	private Double materByWay;

	/**
	 * 原料库存数量
	 */
	private Double materStock;

	/**
	 * 废料数量
	 */
	private Double depose;

	/**
	 * 在线数量
	 */
	private Double onlines;

	/**
	 * 边角料数量
	 */
	private Double overMater;

	/**
	 * 本期原料入库数量
	 */
	private Double thisMaterInWare;

	/**
	 * 原料领料数量
	 */
	private Double materGet;

	/**
	 * 本期原料内销数量
	 */
	private Double thisMaterCancel;

	/**
	 * 本期放弃料件数量
	 */
	private Double thisThrow;

	/**
	 * 合格成品耗用数量
	 */
	private Double passExgUse;

	/**
	 * 废品，残次品折料数量
	 */
	private Double otherConvert;

	/**
	 * 原料复出数量
	 */
	private Double materReuse;

	/**
	 * 原料退换
	 */
	private Double materExitChange;

	/**
	 * 半成品折料数量
	 */
	private Double halfExgConvert;

	/**
	 * 本期放弃残次品折料
	 */
	private Double thisThrowRemainExg;

	private Boolean isSelected = false;;

	/**
	 * 获取商品编码
	 * 
	 * @return complex 商品编码
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * 设置商品编码
	 * 
	 * @param complex
	 *            商品编码
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * 获取废料数量
	 * 
	 * @return depose 废料数量
	 */
	public Double getDepose() {
		return depose;
	}

	/**
	 * 设置废料数量
	 * 
	 * @param depose
	 *            废料数量
	 */
	public void setDepose(Double depose) {
		this.depose = depose;
	}

	/**
	 * 获取半成品折料数量
	 * 
	 * @return halfExgConvert 半成品折料数量
	 */
	public Double getHalfExgConvert() {
		return halfExgConvert;
	}

	/**
	 * 设置半成品折料数量
	 * 
	 * @param halfExgConvert
	 *            半成品折料数量
	 */
	public void setHalfExgConvert(Double halfExgConvert) {
		this.halfExgConvert = halfExgConvert;
	}

	/**
	 * 获取原料在途数量
	 * 
	 * @return materByWay 原料在途数量
	 */
	public Double getMaterByWay() {
		return materByWay;
	}

	/**
	 * 设置原料在途数量
	 * 
	 * @param materByWay
	 *            原料在途数量
	 */
	public void setMaterByWay(Double materByWay) {
		this.materByWay = materByWay;
	}

	/**
	 * 获取原料退换
	 * 
	 * @return materExitChange 原料退换
	 */
	public Double getMaterExitChange() {
		return materExitChange;
	}

	/**
	 * 设置原料退换
	 * 
	 * @param materExitChange
	 *            原料退换
	 */
	public void setMaterExitChange(Double materExitChange) {
		this.materExitChange = materExitChange;
	}

	/**
	 * 获取原料领料数量
	 * 
	 * @return materGet 原料领料数量
	 */
	public Double getMaterGet() {
		return materGet;
	}

	/**
	 * 设置原料领料数量
	 * 
	 * @param materGet
	 *            原料领料数量
	 */
	public void setMaterGet(Double materGet) {
		this.materGet = materGet;
	}

	/**
	 * 获取原料复出数量
	 * 
	 * @return materReuse 原料复出数量
	 */
	public Double getMaterReuse() {
		return materReuse;
	}

	/**
	 * 设置原料复出数量
	 * 
	 * @param materReuse
	 *            原料复出数量
	 */
	public void setMaterReuse(Double materReuse) {
		this.materReuse = materReuse;
	}

	/**
	 * 获取原料库存数量
	 * 
	 * @return materStock 原料库存数量
	 */
	public Double getMaterStock() {
		return materStock;
	}

	/**
	 * 设置原料库存数量
	 * 
	 * @param materStock
	 *            原料库存数量
	 */
	public void setMaterStock(Double materStock) {
		this.materStock = materStock;
	}

	/**
	 * 获取料件名称
	 * 
	 * @return name 料件名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置料件名称
	 * 
	 * @param name
	 *            料件名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取在线数量
	 * 
	 * @return online 在线数量
	 */
	public Double getOnlines() {
		return onlines;
	}

	/**
	 * 设置在线数量
	 * 
	 * @param onlines
	 *            在线数量
	 */
	public void setOnlines(Double onlines) {
		this.onlines = onlines;
	}

	/**
	 * 获取废品、残次品折料数量
	 * 
	 * @return otherConvert 废品、残次品折料数量
	 */
	public Double getOtherConvert() {
		return otherConvert;
	}

	/**
	 * 设置废品、残次品折料数量
	 * 
	 * @param otherConvert
	 *            废品、残次品折料数量
	 */
	public void setOtherConvert(Double otherConvert) {
		this.otherConvert = otherConvert;
	}

	/**
	 * 获取边角料数量
	 * 
	 * @return overMater 边角料数量
	 */
	public Double getOverMater() {
		return overMater;
	}

	/**
	 * 设置边角料数量
	 * 
	 * @param overMater
	 *            边角料数量
	 */
	public void setOverMater(Double overMater) {
		this.overMater = overMater;
	}

	/**
	 * 获取合格成品耗用数量
	 * 
	 * @return passExgUse 合格成品耗用数量
	 */
	public Double getPassExgUse() {
		return passExgUse;
	}

	/**
	 * 设置合格成品耗用数量
	 * 
	 * @param passExgUse
	 *            合格成品耗用数量
	 */
	public void setPassExgUse(Double passExgUse) {
		this.passExgUse = passExgUse;
	}

	/**
	 * 获取料号
	 * 
	 * @return ptNo 料号
	 */
	public String getPtNum() {
		return ptNum;
	}

	/**
	 * 设置料号
	 * 
	 * @param ptNo
	 *            料号
	 */
	public void setPtNum(String ptNo) {
		this.ptNum = ptNo;
	}

	/**
	 * 获取序号
	 * 
	 * @return seqNum 序号
	 */
	public String getSeqNum() {
		return seqNum;
	}

	/**
	 * 设置序号
	 * 
	 * @param seqNum
	 *            序号
	 */
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * 获取规格型号
	 * 
	 * @return spec 规格型号
	 */
	public String getSpec() {
		return spec;
	}

	/**
	 * 设置规格型号
	 * 
	 * @param spec
	 *            规格型号
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}

	/**
	 * 获取本期原料内销数量
	 * 
	 * @return thisMaterCancel 本期原料内销数量
	 */
	public Double getThisMaterCancel() {
		return thisMaterCancel;
	}

	/**
	 * 设置本期原料内销数量
	 * 
	 * @param thisMaterCancel
	 *            本期原料内销数量
	 */
	public void setThisMaterCancel(Double thisMaterCancel) {
		this.thisMaterCancel = thisMaterCancel;
	}

	/**
	 * 获取本期原料入库数量
	 * 
	 * @return thisMaterInWare 本期原料入库数量
	 */
	public Double getThisMaterInWare() {
		return thisMaterInWare;
	}

	/**
	 * 设置本期原料入库数量
	 * 
	 * @param thisMaterInWare
	 *            本期原料入库数量
	 */
	public void setThisMaterInWare(Double thisMaterInWare) {
		this.thisMaterInWare = thisMaterInWare;
	}

	/**
	 * 获取本期放弃料件数量
	 * 
	 * @return thisThrow 本期放弃料件数量
	 */
	public Double getThisThrow() {
		return thisThrow;
	}

	/**
	 * 设置本期放弃料件数量
	 * 
	 * @param thisThrow
	 *            本期放弃料件数量
	 */
	public void setThisThrow(Double thisThrow) {
		this.thisThrow = thisThrow;
	}

	/**
	 * 获取本期放弃残次品折料
	 * 
	 * @return thisThrowRemainExg 本期放弃残次品折料
	 */
	public Double getThisThrowRemainExg() {
		return thisThrowRemainExg;
	}

	/**
	 * 设置本期放弃残次品折料
	 * 
	 * @param thisThrowRemainExg
	 *            本期放弃残次品折料
	 */
	public void setThisThrowRemainExg(Double thisThrowRemainExg) {
		this.thisThrowRemainExg = thisThrowRemainExg;
	}

	/**
	 * 获取转进未报数量
	 * 
	 * @return turnInNoReport 转进未报数量
	 */
	public Double getTurnInNoReport() {
		return turnInNoReport;
	}

	/**
	 * 设置转进未报数量
	 * 
	 * @param turnInNoReport
	 *            转进未报数量
	 */
	public void setTurnInNoReport(Double turnInNoReport) {
		this.turnInNoReport = turnInNoReport;
	}

	/**
	 * 获取计量单位
	 * 
	 * @return unit 计量单位
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * 设置计量单位
	 * 
	 * @param unit
	 *            计量单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * 获取中期核查表头
	 * 
	 * @return checkHead 中期核查表头
	 */
	public DzscCheckHead getCheckHead() {
		return checkHead;
	}

	/**
	 * 设置中期核查表头
	 * 
	 * @param checkHead
	 *            中期核查表头
	 */
	public void setCheckHead(DzscCheckHead checkHead) {
		this.checkHead = checkHead;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
}
