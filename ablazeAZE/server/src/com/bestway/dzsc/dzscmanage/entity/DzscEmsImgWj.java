/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.dzscmanage.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscCustomsModifyState;

/**
 * 存放电子手册合同备案里的料件资料
 * 
 * @author yp
 */
public class DzscEmsImgWj extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 电子手册合同备案表头
	 */
	private DzscEmsPorWjHead dzscEmsPorWjHead = null;

	/**
	 * 备案序号
	 */
	private Integer seqNum;

	/**
	 * 4码归并序号
	 */
	private Integer fourSeqNum;

	/**
	 * 4码商品编码
	 */
	private String fourComplex;

	/**
	 * 4码商品名称
	 */
	private String fourName;

	/**
	 * 4码商品型号规格
	 */
	private String fourSpec;

	/**
	 * 4码商品单位
	 */
	private Unit fourUnit = null;

	/**
	 * 法定计量单位
	 */
	private Unit firstUnit = null;

	/**
	 * 第二法定计量单位
	 */
	private Unit secondUnit = null;

	/**
	 * 修改标志
	 */
	private String modifyMark;

	
	/**
	 * 料件编码（外经接口使用）
	 * @return
	 */
	private String wjCode;
	
	
	// private Integer no = null; //序号
	// private Integer tenSeqNum = null;//凭证序号
	// private String complex = null; //海关编码
	// private String name = null; //料件名称
	// private String spec = null; //型号规格
	// private Double price = null; //单价
	// private Double amount = null; //数量
	// private Double wareAmount = null; //损耗数量
	// private Double money = null; //金额
	// private Unit unit = null; //单位
	// private Double legalGross = null; //法定单位总量
	// private Double legalAmount = null; //法定单位数量
	// private Unit legalUnit = null; //法定单位
	// private Double unitWeight = null; //单位重量
	// private Double weight = null; //总重量
	// private String isMainImg = null; //主料/辅料
	// private Country country = null; //原产地
	// private Boolean isImgCustom = null;//料件是否报关
	// private Integer seqNum = null; //归并序号
	// private LevyMode levyMode = null; //增减免税方式
	// private String note = null; //说明
	//
	// /**
	// * @return Returns the serialVersionUID.
	// */
	// public static long getSerialVersionUID() {
	// return serialVersionUID;
	// }
	// /**
	// * @return Returns the amount.
	// */
	// public Double getAmount() {
	// return amount;
	// }
	// /**
	// * @param amount The amount to set.
	// */
	// public void setAmount(Double amount) {
	// this.amount = amount;
	// }
	// /**
	// * @return Returns the complex.
	// */
	// public String getComplex() {
	// return complex;
	// }
	// /**
	// * @param complex The complex to set.
	// */
	// public void setComplex(String complex) {
	// this.complex = complex;
	// }
	// /**
	// * @return Returns the country.
	// */
	// public Country getCountry() {
	// return country;
	// }
	// /**
	// * @param country The country to set.
	// */
	// public void setCountry(Country country) {
	// this.country = country;
	// }
	// /**
	// * @return Returns the dzbaEmsPorWjHead.
	// */
	// public DzscEmsPorWjHead getDzscEmsPorWjHead() {
	// return dzscEmsPorWjHead;
	// }
	// /**
	// * @param dzbaEmsPorWjHead The dzbaEmsPorWjHead to set.
	// */
	// public void setDzscEmsPorWjHead(DzscEmsPorWjHead dzbaEmsPorWjHead) {
	// this.dzscEmsPorWjHead = dzbaEmsPorWjHead;
	// }
	// /**
	// * @return Returns the isImgCustom.
	// */
	// public Boolean getIsImgCustom() {
	// return isImgCustom;
	// }
	// /**
	// * @param isImgCustom The isImgCustom to set.
	// */
	// public void setIsImgCustom(Boolean isImgCustom) {
	// this.isImgCustom = isImgCustom;
	// }
	// /**
	// * @return Returns the isMainImg.
	// */
	// public String getIsMainImg() {
	// return isMainImg;
	// }
	// /**
	// * @param isMainImg The isMainImg to set.
	// */
	// public void setIsMainImg(String isMainImg) {
	// this.isMainImg = isMainImg;
	// }
	// /**
	// * @return Returns the legalAmount.
	// */
	// public Double getLegalAmount() {
	// return legalAmount;
	// }
	// /**
	// * @param legalAmount The legalAmount to set.
	// */
	// public void setLegalAmount(Double legalAmount) {
	// this.legalAmount = legalAmount;
	// }
	// /**
	// * @return Returns the legalGross.
	// */
	// public Double getLegalGross() {
	// return legalGross;
	// }
	// /**
	// * @param legalGross The legalGross to set.
	// */
	// public void setLegalGross(Double legalGross) {
	// this.legalGross = legalGross;
	// }
	// /**
	// * @return Returns the legalUnit.
	// */
	// public Unit getLegalUnit() {
	// return legalUnit;
	// }
	// /**
	// * @param legalUnit The legalUnit to set.
	// */
	// public void setLegalUnit(Unit legalUnit) {
	// this.legalUnit = legalUnit;
	// }
	// /**
	// * @return Returns the levyMode.
	// */
	// public LevyMode getLevyMode() {
	// return levyMode;
	// }
	// /**
	// * @param levyMode The levyMode to set.
	// */
	// public void setLevyMode(LevyMode levyMode) {
	// this.levyMode = levyMode;
	// }
	// /**
	// * @return Returns the money.
	// */
	// public Double getMoney() {
	// return money;
	// }
	// /**
	// * @param money The money to set.
	// */
	// public void setMoney(Double money) {
	// this.money = money;
	// }
	// /**
	// * @return Returns the name.
	// */
	// public String getName() {
	// return name;
	// }
	// /**
	// * @param name The name to set.
	// */
	// public void setName(String name) {
	// this.name = name;
	// }
	// /**
	// * @return Returns the no.
	// */
	// public Integer getNo() {
	// return no;
	// }
	// /**
	// * @param no The no to set.
	// */
	// public void setNo(Integer no) {
	// this.no = no;
	// }
	// /**
	// * @return Returns the note.
	// */
	// public String getNote() {
	// return note;
	// }
	// /**
	// * @param note The note to set.
	// */
	// public void setNote(String note) {
	// this.note = note;
	// }
	// /**
	// * @return Returns the price.
	// */
	// public Double getPrice() {
	// return price;
	// }
	// /**
	// * @param price The price to set.
	// */
	// public void setPrice(Double price) {
	// this.price = price;
	// }
	// /**
	// * @return Returns the seqNum.
	// */
	// public Integer getSeqNum() {
	// return seqNum;
	// }
	// /**
	// * @param seqNum The seqNum to set.
	// */
	// public void setSeqNum(Integer seqNum) {
	// this.seqNum = seqNum;
	// }
	// /**
	// * @return Returns the spec.
	// */
	// public String getSpec() {
	// return spec;
	// }
	// /**
	// * @param spec The spec to set.
	// */
	// public void setSpec(String spec) {
	// this.spec = spec;
	// }
	// /**
	// * @return Returns the unit.
	// */
	// public Unit getUnit() {
	// return unit;
	// }
	// /**
	// * @param unit The unit to set.
	// */
	// public void setUnit(Unit unit) {
	// this.unit = unit;
	// }
	// /**
	// * @return Returns the unitWeight.
	// */
	// public Double getUnitWeight() {
	// return unitWeight;
	// }
	// /**
	// * @param unitWeight The unitWeight to set.
	// */
	// public void setUnitWeight(Double unitWeight) {
	// this.unitWeight = unitWeight;
	// }
	// /**
	// * @return Returns the wareAmount.
	// */
	// public Double getWareAmount() {
	// return wareAmount;
	// }
	// /**
	// * @param wareAmount The wareAmount to set.
	// */
	// public void setWareAmount(Double wareAmount) {
	// this.wareAmount = wareAmount;
	// }
	// /**
	// * @return Returns the weight.
	// */
	// public Double getWeight() {
	// return weight;
	// }
	// /**
	// * @param weight The weight to set.
	// */
	// public void setWeight(Double weight) {
	// this.weight = weight;
	// }
	// /**
	// * @return Returns the tenSeqNum.
	// */
	// public Integer getTenSeqNum() {
	// return tenSeqNum;
	// }
	// /**
	// * @param tenSeqNum The tenSeqNum to set.
	// */
	// public void setTenSeqNum(Integer tenSeqNum) {
	// this.tenSeqNum = tenSeqNum;
	// }

	/**
	 * 获取电子手册合同备案表头
	 * 
	 * @return dzscEmsPorWjHead 电子手册合同备案表头
	 */
	public DzscEmsPorWjHead getDzscEmsPorWjHead() {
		return dzscEmsPorWjHead;
	}

	/**
	 * 获取电子手册合同备案表头
	 * 
	 * @param dzscEmsPorWjHead
	 *            电子手册合同备案表头
	 */
	public void setDzscEmsPorWjHead(DzscEmsPorWjHead dzscEmsPorWjHead) {
		this.dzscEmsPorWjHead = dzscEmsPorWjHead;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * 获取4码商品编码
	 * 
	 * @return fourComplex 4码商品编码
	 */
	public String getFourComplex() {
		return fourComplex;
	}

	/**
	 * 设置4码商品编码
	 * 
	 * @param fourComplex
	 *            4码商品编码
	 */
	public void setFourComplex(String fourComplex) {
		this.fourComplex = fourComplex;
	}

	/**
	 * 获取4码商品名称
	 * 
	 * @return fourName 4码商品名称
	 */
	public String getFourName() {
		return fourName;
	}

	/**
	 * 设置4码商品名称
	 * 
	 * @param fourName
	 *            4码商品名称
	 */
	public void setFourName(String fourName) {
		this.fourName = fourName;
	}

	/**
	 * 获取4码归并序号
	 * 
	 * @return fourSeqNum 4码归并序号
	 */
	public Integer getFourSeqNum() {
		return fourSeqNum;
	}

	/**
	 * 设置4码归并序号
	 * 
	 * @param fourSeqNum
	 *            4码归并序号
	 */
	public void setFourSeqNum(Integer fourSeqNum) {
		this.fourSeqNum = fourSeqNum;
	}

	/**
	 * 获取4码商品型号规格
	 * 
	 * @return fourSpec 4码商品型号规格
	 */
	public String getFourSpec() {
		return fourSpec;
	}

	/**
	 * 设置4码商品型号规格
	 * 
	 * @param fourSpec
	 *            4码商品型号规格
	 */
	public void setFourSpec(String fourSpec) {
		this.fourSpec = fourSpec;
	}

	/**
	 * 获取4码商品单位
	 * 
	 * @return fourUnit 4码商品单位
	 */
	public Unit getFourUnit() {
		return fourUnit;
	}

	/**
	 * 设置4码商品单位
	 * 
	 * @param fourUnit
	 *            4码商品单位
	 */
	public void setFourUnit(Unit fourUnit) {
		this.fourUnit = fourUnit;
	}

	public Unit getFirstUnit() {
		return firstUnit;
	}

	public void setFirstUnit(Unit firstUnit) {
		this.firstUnit = firstUnit;
	}

	public Unit getSecondUnit() {
		return secondUnit;
	}

	public void setSecondUnit(Unit secondUnit) {
		this.secondUnit = secondUnit;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	/**
	 * 获取海关修改标志
	 * 
	 * @return editMark 修改标志
	 */
	public String getCustomsModifyMark() {
		return DzscCustomsModifyState.getCustomsModifyState(modifyMark);
	}

	/**
	 * 附加编码
	 * 
	 * @return
	 */
	public String getComplexSCode() {
		if (this.getFourComplex() == null
				|| this.getFourComplex().length() <= 8) {
			return "";
		} else {
			return this.getFourComplex().substring(8,
					this.getFourComplex().length());
		}
	}

	public String getWjCode() {
		return wjCode;
	}

	public void setWjCode(String wjCode) {
		this.wjCode = wjCode;
	}
}