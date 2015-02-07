/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.dzscmanage.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscCustomsModifyState;

/**
 * 存放电子手册通关备案里的料件资料
 * 
 * @author yp
 */
public class DzscEmsImgBill extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 电子手册通关备案表头
	 */
	private DzscEmsPorHead dzscEmsPorHead = null;

	/**
	 * 备案序号
	 */
	private Integer seqNum = null;

	/**
	 * 归并序号
	 */
	private Integer tenSeqNum = null;

	/**
	 * 海关编码
	 */
	private Complex complex = null;

	/**
	 * 料件名称
	 */
	private String name = null;

	/**
	 * 型号规格
	 */
	private String spec = null;

	/**
	 * 单价
	 */
	private Double price = null;

	/**
	 * 数量
	 */
	private Double amount = null;

	/**
	 * 金额
	 */
	private Double money = null;

	/**
	 * 单位
	 */
	private Unit unit = null;

	/**
	 * 单位重量
	 */
	private Double unitWeight = null;

	/**
	 * 总重量
	 */
	private Double weight = null;

	/**
	 * 原产国
	 */
	private Country country = null;

	/**
	 * 增减免税方式
	 */
	private LevyMode levyMode = null;

	/**
	 * 说明
	 */
	private String note = null;

	/**
	 * 法定单位比例因子
	 */
	private Double legalUnitGene;

	/**
	 * 第二法定单位比例因子
	 */
	private Double legalUnit2Gene;

	/**
	 * 重量单位比例因子
	 */
	private Double weigthUnitGene;

    /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark;

	/**
	 * 出口征税比例
	 */
	private Double dutyRatio;

	/**
	 * 详细型号规格
	 */
	private String detailNote;

	/**
	 * 物料号码(发送报文时需要，不做保存)
	 */
	private String materialPtNo = null;

	/**
	 * 报关助记码
	 */
	private String customsNo = null;
	/**
	 * 非保税料件比例
	 * 
	 */
	private Double dutyRate = null;
	
	/**
	 * 合同序号（外经接口使用）
	 * @return
	 */
	private Integer wjSeqNum;
	
	/**
	 * 是否禁用
	 * 
	 */
	private Boolean isForbid;
	/**
	 * 获取serialVersionUID
	 * 
	 * @return serialVersionUID.
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * 获取数量
	 * 
	 * @return amount 数量
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 设置数量
	 * 
	 * @param amount
	 *            数量
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * 获取海关编码
	 * 
	 * @return complex 海关编码
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * 设置海关编码
	 * 
	 * @param complex
	 *            海关编码
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * 获取原产国
	 * 
	 * @return country 原产国
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 设置原产国
	 * 
	 * @param country
	 *            原产国
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * 获取电子手册通过备案表头
	 * 
	 * @return dzbaEmsPorHead 电子手册通过备案表头
	 */
	public DzscEmsPorHead getDzscEmsPorHead() {
		return dzscEmsPorHead;
	}

	/**
	 * 设置电子手册通过备案表头
	 * 
	 * @param dzbaEmsPorHead
	 *            电子手册通过备案表头
	 */
	public void setDzscEmsPorHead(DzscEmsPorHead dzscEmsPorHead) {
		this.dzscEmsPorHead = dzscEmsPorHead;
	}

	/**
	 * 获取增减免税方式
	 * 
	 * @return levyMode 增减免税方式
	 */
	public LevyMode getLevyMode() {
		return levyMode;
	}

	/**
	 * 设置增减免税方式
	 * 
	 * @param levyMode
	 *            增减免税方式
	 */
	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

	/**
	 * 获取金额
	 * 
	 * @return money 金额
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * 设置金额
	 * 
	 * @param money
	 *            金额
	 */
	public void setMoney(Double money) {
		this.money = money;
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
	 * 获取单价
	 * 
	 * @return price 单价
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * 设置单价
	 * 
	 * @param price
	 *            单价
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * 获取型号规格
	 * 
	 * @return spec 型号规格
	 */
	public String getSpec() {
		return spec;
	}

	/**
	 * 设置型号规格
	 * 
	 * @param spec
	 *            型号规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}

	/**
	 * 获取单位
	 * 
	 * @return unit 单位
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit
	 *            单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * 获取单位重量
	 * 
	 * @return unitWeight 单位重量
	 */
	public Double getUnitWeight() {
		return unitWeight;
	}

	/**
	 * 设置单位重量
	 * 
	 * @param unitWeight
	 *            单位重量
	 */
	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	/**
	 * 获取总重量
	 * 
	 * @return weight 总重量
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * 设置总重量
	 * 
	 * @param weight
	 *            总重量
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * 获取说明
	 * 
	 * @return note 说明
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置说明
	 * 
	 * @param note
	 *            说明
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取修改标志
	 * 
	 * @return modifyMark 修改标志
	 */
	public String getModifyMark() {
		return modifyMark;
	}

	/**
	 * 获取修改标志
	 * 
	 * @return modifyMark 修改标志
	 */
	public String getCustomsModifyMark() {
		return DzscCustomsModifyState.getCustomsModifyState(modifyMark);
	}

	/**
	 * 设置修改标志
	 * 
	 * @param modifyMark
	 *            修改标志
	 */
	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	/**
	 * 获取出口征税比例
	 * 
	 * @return dutyRatio 出口征税比例
	 */
	public Double getDutyRatio() {
		return dutyRatio;
	}

	/**
	 * 设置出口征税比例
	 * 
	 * @param dutyRatio
	 *            出口征税比例
	 */
	public void setDutyRatio(Double dutyRatio) {
		this.dutyRatio = dutyRatio;
	}

	/**
	 * 获取序号
	 * 
	 * @return tenSeqNum 序号
	 */
	public Integer getTenSeqNum() {
		return tenSeqNum;
	}

	/**
	 * 设置序号
	 * 
	 * @param tenSeqNum
	 *            序号
	 */
	public void setTenSeqNum(Integer tenSeqNum) {
		this.tenSeqNum = tenSeqNum;
	}

	public String getDetailNote() {
		return detailNote;
	}

	public void setDetailNote(String detailNote) {
		this.detailNote = detailNote;
	}
	/**
	 * 商品编号
	 * 
	 * @return
	 */
	public String getComplexTCode() {
		if (this.getComplex() == null
				|| this.getComplex().getCode().length() < 8) {
			return "";
		} else {
			return this.getComplex().getCode().substring(0,8);
		}
	}
	/**
	 * 附加编码
	 * 
	 * @return
	 */
	public String getComplexSCode() {
		if (this.getComplex() == null
				|| this.getComplex().getCode().length() <= 8) {
			return "";
		} else {
			return this.getComplex().getCode().substring(8,
					this.getComplex().getCode().length());
		}
	}

	public String getMaterialPtNo() {
		return materialPtNo;
	}

	public void setMaterialPtNo(String materialPtNo) {
		this.materialPtNo = materialPtNo;
	}

	public Double getLegalUnit2Gene() {
		return legalUnit2Gene;
	}

	public void setLegalUnit2Gene(Double legalUnit2Gene) {
		this.legalUnit2Gene = legalUnit2Gene;
	}

	public Double getLegalUnitGene() {
		return legalUnitGene;
	}

	public void setLegalUnitGene(Double legalUnitGene) {
		this.legalUnitGene = legalUnitGene;
	}

	public Double getWeigthUnitGene() {
		return weigthUnitGene;
	}

	public void setWeigthUnitGene(Double weigthUnitGene) {
		this.weigthUnitGene = weigthUnitGene;
	}

	public String getCustomsNo() {
		return customsNo;
	}

	public void setCustomsNo(String customsNo) {
		this.customsNo = customsNo;
	}

	public Double getDutyRate() {
		if(dutyRate==null){
			return 0.0;
		}
		return dutyRate;
	}

	public void setDutyRate(Double dutyRate) {
		this.dutyRate = dutyRate;
	}


	public Integer getWjSeqNum() {
		return wjSeqNum;
	}

	public void setWjSeqNum(Integer wjSeqNum) {
		this.wjSeqNum = wjSeqNum;
	}

	public Boolean getIsForbid() {
		return isForbid;
	}

	public void setIsForbid(Boolean isForbid) {
		this.isForbid = isForbid;
	}
	
}