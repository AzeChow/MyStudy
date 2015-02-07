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
 * 存放电子手册合同备案里的成品资料
 * 
 * @author yp
 */
public class DzscEmsExgWj extends BaseScmEntity {
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
	 * 第一法定计量单位
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
	 * 成品编码（外经接口使用）
	 * @return
	 */
	private String wjCode;

	// private Integer no = null; //序号
	// private Integer tenSeqNum = null;//凭证序号
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
	// private String complex = null; //海关编码
	// private String name = null; //商品名称
	// private String spec = null; //型号规格
	// private Double amount = null; //出口数量
	// private Unit unit = null; //单位
	// private Double price = null; //单价
	// private Double money = null; //金额
	// private Double legalAmount = null;//法定数量
	// private Unit legalUnit = null; //法定单位
	//    
	// private Double imgMoney = null; //原料费
	// private Country country = null; //消费国
	//    
	// private Double machinPrice = null; //加工费单价
	// private Double machinMoney = null; //加工费总价
	// private String note = null; //补充说明
	// private Double unitNetWeight = null;//单位净重
	// private Double unitGrossWeight = null;//单位毛重
	// private Integer seqNum = null; //成品总表序号
	// private LevyMode levyMode = null; //增减免税方式
	//    
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
	//	
	// /**
	// * @return Returns the imgMoney.
	// */
	// public Double getImgMoney() {
	// return imgMoney;
	// }
	// /**
	// * @param imgMoney The imgMoney to set.
	// */
	// public void setImgMoney(Double imgMoney) {
	// this.imgMoney = imgMoney;
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
	// * @return Returns the machinMoney.
	// */
	// public Double getMachinMoney() {
	// return machinMoney;
	// }
	// /**
	// * @param machinMoney The machinMoney to set.
	// */
	// public void setMachinMoney(Double machinMoney) {
	// this.machinMoney = machinMoney;
	// }
	// /**
	// * @return Returns the machinPrice.
	// */
	// public Double getMachinPrice() {
	// return machinPrice;
	// }
	// /**
	// * @param machinPrice The machinPrice to set.
	// */
	// public void setMachinPrice(Double machinPrice) {
	// this.machinPrice = machinPrice;
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
	// * @return Returns the unitGrossWeight.
	// */
	// public Double getUnitGrossWeight() {
	// return unitGrossWeight;
	// }
	// /**
	// * @param unitGrossWeight The unitGrossWeight to set.
	// */
	// public void setUnitGrossWeight(Double unitGrossWeight) {
	// this.unitGrossWeight = unitGrossWeight;
	// }
	// /**
	// * @return Returns the unitNetWeight.
	// */
	// public Double getUnitNetWeight() {
	// return unitNetWeight;
	// }
	// /**
	// * @param unitNetWeight The unitNetWeight to set.
	// */
	// public void setUnitNetWeight(Double unitNetWeight) {
	// this.unitNetWeight = unitNetWeight;
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
	// * @param seqNum The seqNum to set.
	// */
	// public void setSeqNum(Integer seqNum) {
	// this.seqNum = seqNum;
	// }
	// /**
	// * @return Returns the seqNum.
	// */
	// public Integer getSeqNum() {
	// return seqNum;
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

	/**
	 * 获取电子手册合同备案表头
	 * 
	 * @return dzscEmsPorWjHead 电子手册合同备案表头
	 */
	public DzscEmsPorWjHead getDzscEmsPorWjHead() {
		return dzscEmsPorWjHead;
	}

	/**
	 * 设置电子手册合同备案表头
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
	 * 获取码商品单位
	 * 
	 * @return fourUnit 码商品单位
	 */
	public Unit getFourUnit() {
		return fourUnit;
	}

	/**
	 * 设置码商品单位
	 * 
	 * @param fourUnit
	 *            码商品单位
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