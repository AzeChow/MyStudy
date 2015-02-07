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
import com.bestway.common.constant.DutyRateType;
import com.bestway.common.constant.DzscCustomsModifyState;
import com.bestway.common.constant.EquipMode;

/**
 * 存放手册通关备案的成品资料
 * 
 * @author yp
 */
public class DzscEmsExgBill extends BaseScmEntity {
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
	 * 商品名称
	 */
	private String name = null;

	/**
	 * 型号规格
	 */
	private String spec = null;

	/**
	 * 出口数量
	 */
	private Double amount = null;

	/**
	 * 单位
	 */
	private Unit unit = null;

	/**
	 * 单价
	 */
	private Double price = null;

	/**
	 * 金额
	 */
	private Double money = null;

	/**
	 * 原料费
	 */
	private Double imgMoney = null;

	/**
	 * 消费国
	 */
	private Country country = null;

	/**
	 * 加工费单价
	 */
	private Double machinPrice = null;

	/**
	 * 加工费总价
	 */
	private Double machinMoney = null;

	/**
	 * 补充说明
	 */
	private String note = null;

	/**
	 * 单位净重
	 */
	private Double unitNetWeight = null;

	/**
	 * 单位毛重
	 */
	private Double unitGrossWeight = null;

	/**
	 * 增减免税方式
	 */
	private LevyMode levyMode = null;

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
	 * 修改标志 UNCHANGE = "0"; 未修改 MODIFIED = "1"; 已修改 DELETED = "2"; 已删除 ADDED =
	 * "3"; 新增
	 */
	private String modifyMark = null;

	/**
	 * 出口征税比例
	 */
	private Double dutyRatio = null;

	/**
	 * 物料号码(发送报文时需要，不做保存)
	 */
	private String materialPtNo = null;

	/**
	 * 报关助记码
	 */
	private String customsNo = null;

	/**
	 * 申报状态 1-企业不申报； 2-企业申报； 9-已核定
	 * 
	 */
	private Double dutyRate = null;

	private String dutyRateString = null;

	/**
	 * 合同序号（外经接口使用）
	 * 
	 * @return
	 */
	private Integer wjSeqNum;

	/**
	 * 是否禁用
	 * 
	 */
	private Boolean isForbid;
	
	public String getDutyRateString() {
		if (dutyRate == 1) {
			return "企业不申报";
		} else if (dutyRate == 2) {
			return "企业申报";
		} else if (dutyRate == 2) {
			return "已核定";
		} else
			return "";
	}

	/**
	 * 获取serialVersionUID
	 * 
	 * @return serialVersionUID.
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * 获取出口数量
	 * 
	 * @return amount 出口数量
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 设置出口数量
	 * 
	 * @param amount
	 *            出口数量
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * 获取消费国
	 * 
	 * @return country 消费国
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 设置消费国
	 * 
	 * @param country
	 *            消费国
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
	public void setDzscEmsPorHead(DzscEmsPorHead dzbaEmsPorHead) {
		this.dzscEmsPorHead = dzbaEmsPorHead;
	}

	/**
	 * 获取原料费
	 * 
	 * @return imgMoney 原料费
	 */
	public Double getImgMoney() {
		return imgMoney;
	}

	/**
	 * 设置原料费
	 * 
	 * @param imgMoney
	 *            原料费
	 */
	public void setImgMoney(Double imgMoney) {
		this.imgMoney = imgMoney;
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
	 * 获取加工费总价
	 * 
	 * @return machinMoney 加工费总价
	 */
	public Double getMachinMoney() {
		if (machinMoney == null || machinMoney == 0d) {
			return (machinPrice == null ? 0d : machinPrice)
					* (amount == null ? 0d : amount);
		}
		return machinMoney;
	}

	/**
	 * 设置加工费总价
	 * 
	 * @param machinMoney
	 *            加工费总价
	 */
	public void setMachinMoney(Double machinMoney) {
		this.machinMoney = machinMoney;
	}

	/**
	 * 获取加工费单价
	 * 
	 * @return machinPrice 加工费单价
	 */
	public Double getMachinPrice() {
		return machinPrice;
	}

	/**
	 * 设置加工费单价
	 * 
	 * @param machinPrice
	 *            加工费单价
	 */
	public void setMachinPrice(Double machinPrice) {
		this.machinPrice = machinPrice;
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
	 * 获取商品名称
	 * 
	 * @return name 商品名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param name
	 *            商品名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取补充说明
	 * 
	 * @return note 补充说明
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置补充说明
	 * 
	 * @param note
	 *            补充说明
	 */
	public void setNote(String note) {
		this.note = note;
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
	 * 获取单位毛重
	 * 
	 * @return unitGrossWeight 单位毛重
	 */
	public Double getUnitGrossWeight() {
		return unitGrossWeight;
	}

	/**
	 * 设置单位毛重
	 * 
	 * @param unitGrossWeight
	 *            单位毛重
	 */
	public void setUnitGrossWeight(Double unitGrossWeight) {
		this.unitGrossWeight = unitGrossWeight;
	}

	/**
	 * 获取单位净重
	 * 
	 * @return unitNetWeight 单位净重
	 */
	public Double getUnitNetWeight() {
		return unitNetWeight;
	}

	/**
	 * 设置单位净重
	 * 
	 * @param unitNetWeight
	 *            单位净重
	 */
	public void setUnitNetWeight(Double unitNetWeight) {
		this.unitNetWeight = unitNetWeight;
	}

	/**
	 * @param seqNum
	 *            The seqNum to set.
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * @return Returns the seqNum.
	 */
	public Integer getSeqNum() {
		return seqNum;
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
	 * 获取修改标记
	 * 
	 * @return modifyMark 修改标记
	 */
	public String getModifyMark() {
		return modifyMark;
	}

	/**
	 * 获取修改标记
	 * 
	 * @return modifyMark 修改标记
	 */
	public String getCustomsModifyMark() {
		return DzscCustomsModifyState.getCustomsModifyState(modifyMark);
	}

	/**
	 * 设置修改标记
	 * 
	 * @param modifyMark
	 *            修改标记
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
	 * 获取凭证序号
	 * 
	 * @return tenSeqNum 凭证序号
	 */
	public Integer getTenSeqNum() {
		return tenSeqNum;
	}

	/**
	 * 设置凭证序号
	 * 
	 * @param tenSeqNum
	 *            凭证序号
	 */
	public void setTenSeqNum(Integer tenSeqNum) {
		this.tenSeqNum = tenSeqNum;
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
		if (dutyRate == null) {
			// 如果单耗申报环节是1.备案 的话，那么成品的申报状态默认为 企业申报 2
			if (EquipMode.PUT_ON_RECORD.equals(this.dzscEmsPorHead
					.getEquipMode())) {
				return Double.valueOf(DutyRateType.DECLARE);
			}
			// 否则默认为 企业不申报 1
			return Double.valueOf(DutyRateType.NO_DECLARE);
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