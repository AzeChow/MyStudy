/*
 * Created on 2004-7-26
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ProjectDept;

/**
 * 报关清单归并后商品信息
 * 
 * @author Administrator 报关清单归并后商品信息 table="atcmergeaftercominfo" 2011-02-12
 *         check by hcl
 */
public class AtcMergeAfterComInfo extends BaseScmEntity implements Comparable {//
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 报关清单
	 */
	private ApplyToCustomsBillList billList;

	/**
	 * 帐册序号
	 */
	private Integer emsSerialNo;

	/**
	 * 商品信息
	 */
	private Complex complex;

	/**
	 * 商品名称
	 */
	private String complexName;

	/**
	 * 商品规格
	 */
	private String complexSpec;

	/**
	 * 申报数量
	 */
	private Double declaredAmount;

	/**
	 * 法定数量
	 */
	private Double legalAmount;

	/**
	 * 第二法定数量
	 */
	private Double secondLegalAmount;

	/**
	 * 毛重
	 */
	private Double grossWeight;

	/**
	 * 净重
	 */
	private Double netWeight;

	/**
	 * 件数
	 */
	private Integer piece;

	/**
	 * 申报单位
	 */
	private Unit unit;

	/**
	 * 法定单位
	 */
	private Unit legalUnit;

	/**
	 * 第二法定单位
	 */
	private Unit secondLegalUnit;

	/**
	 * 备注
	 */
	private String memos;

	/**
	 * 单价
	 */
	private Double price;

	/**
	 * 箱号
	 */
	private String boxNo;
	/**
	 * 总金额
	 */
	private Double totalPrice;

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 是否已转报关单
	 */
	private Boolean isTransferCustomsBill = false;

	/**
	 * 总重量
	 */
	private Double totalNetWeight = null;

	/**
	 * 原产国
	 */
	private Country country;

	/**
	 * 备用备注
	 */
	private String standbyNote;
	/**
	 * 用途代码
	 */
	private Uses usesCode;// 转报关单时用到
	/**
	 * 事业部
	 */
	private ProjectDept projectDept;// 转报关单时用到
	/**
	 * 征免方式
	 */
	private LevyMode levyMode;// 转报关单时用到

	/**
	 * 总体积
	 */
	private Double cbm;// 体积,临时使用

	/**
	 * 工缴费(加工费总价)
	 */
	private Double workUsd;
	
	/**
	 * 币制
	 */
	private Curr currency;

	/**
	 * 取得报关清单内容
	 * 
	 * @return 报关清单
	 */
	public ApplyToCustomsBillList getBillList() {
		return billList;
	}

	/**
	 * 设置报关清单内容
	 * 
	 * @param billList
	 *            报关清单
	 */
	public void setBillList(ApplyToCustomsBillList billList) {
		this.billList = billList;
	}

	/**
	 * 取得商品信息相关内容
	 * 
	 * @return 商品信息
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * 设置商品信息相关内容
	 * 
	 * @param complex
	 *            商品信息
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * 取得申报数量
	 * 
	 * @return 申报数量
	 */
	public Double getDeclaredAmount() {
		return declaredAmount;
	}

	/**
	 * 设置申报数量
	 * 
	 * @param declaredAmount
	 *            申报数量
	 */
	public void setDeclaredAmount(Double declaredAmount) {
		this.declaredAmount = declaredAmount;
	}

	/**
	 * 获得毛重
	 * 
	 * @return 毛重
	 */
	public Double getGrossWeight() {
		return grossWeight;
	}

	/**
	 * 设置毛重
	 * 
	 * @param grossWeight
	 *            毛重
	 */
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	/**
	 * 获得法定数量
	 * 
	 * @return 法定数量
	 */
	public Double getLegalAmount() {
		return legalAmount;
	}

	/**
	 * 设置法定数量
	 * 
	 * @param legalAmount
	 *            法定数量
	 */
	public void setLegalAmount(Double legalAmount) {
		this.legalAmount = legalAmount;
	}

	/**
	 * 获得备注内容
	 * 
	 * @return 备注
	 */
	public String getMemos() {
		return memos;
	}

	/**
	 * 填写备注内容
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemos(String memo) {
		this.memos = memo;
	}

	/**
	 * 获得净重
	 * 
	 * @return 净重
	 */
	public Double getNetWeight() {
		return netWeight;
	}

	/**
	 * 设置净重
	 * 
	 * @param netWeight
	 *            净重
	 */
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	/**
	 * @return Returns the secondLegalAmount.
	 */
	public Double getSecondLegalAmount() {
		return secondLegalAmount;
	}

	/**
	 * @param secondLegalAmount
	 *            The secondLegalAmount to set.
	 */
	public void setSecondLegalAmount(Double secondLegalAmount) {
		this.secondLegalAmount = secondLegalAmount;
	}

	/**
	 * 得到箱号
	 * 
	 * @return
	 */
	public String getBoxNo() {
		return boxNo;
	}

	/**
	 * 设置箱号
	 * 
	 * @param boxNo
	 */
	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	// /**
	// * @return Returns the complexCode.
	// */
	// public String getComplexCode() {
	// return complexCode;
	// }
	// /**
	// * @param complexCode The complexCode to set.
	// */
	// public void setComplexCode(String complexCode) {
	// this.complexCode = complexCode;
	// }
	/**
	 * 获得商品名称
	 * 
	 * @return 商品名称
	 */
	public String getComplexName() {
		return complexName;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param complexName
	 *            商品名称
	 */
	public void setComplexName(String complexName) {
		this.complexName = complexName;
	}

	/**
	 * 获得商品规格
	 * 
	 * @return 商品规格
	 */
	public String getComplexSpec() {
		return complexSpec;
	}

	/**
	 * 设置商品规格
	 * 
	 * @param complexSpec
	 *            商品规格
	 */
	public void setComplexSpec(String complexSpec) {
		this.complexSpec = complexSpec;
	}

	/**
	 * 获得法定单位
	 * 
	 * @return 法定单位
	 */
	public Unit getLegalUnit() {
		return legalUnit;
	}

	/**
	 * 设置法定单位
	 * 
	 * @param legalUnit
	 *            法定单位
	 */
	public void setLegalUnit(Unit legalUnit) {
		this.legalUnit = legalUnit;
	}

	/**
	 * 获得第二法定单位
	 * 
	 * @return 第二法定单位
	 */
	public Unit getSecondLegalUnit() {
		return secondLegalUnit;
	}

	/**
	 * 设置第二法定单位
	 * 
	 * @param secondLegalUnit
	 *            第二法定单位
	 */
	public void setSecondLegalUnit(Unit secondLegalUnit) {
		this.secondLegalUnit = secondLegalUnit;
	}

	/**
	 * 获得申报单位
	 * 
	 * @return R申报单位
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * 设置申报单位
	 * 
	 * @param unit
	 *            申报单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * 获得件数
	 * 
	 * @return 件数
	 */
	public Integer getPiece() {
		return piece;
	}

	/**
	 * 设置件数
	 * 
	 * @param 件数
	 */
	public void setPiece(Integer piece) {
		this.piece = piece;
	}

	/**
	 * 获得单价
	 * 
	 * @return 单价
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * 设置单价
	 * 
	 * @param price
	 *            T单价
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * 获得总金额
	 * 
	 * @return 总金额
	 */
	public Double getTotalPrice() {
		// if (this.totalPrice != null && totalPrice != 0.0) {
		// return this.totalPrice;
		// }
		// if (this.declaredAmount == null || this.price == null) {
		// return Double.valueOf(0);
		// }
		// Double dou = Double.valueOf(declaredAmount * price);
		if (totalPrice == null) {
			return 0.0;
		} else
			return totalPrice;
		// BigDecimal b = new BigDecimal(dou);
		// BigDecimal b = new BigDecimal(this.declaredAmount.doubleValue()
		// * this.price.doubleValue());
		// return Double.valueOf(b.setScale(2, BigDecimal.ROUND_HALF_UP)
		// .doubleValue());
	}

	/**
	 * 设置总金额
	 * 
	 * @param totalPrice
	 *            总金额
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * 取得版本号
	 * 
	 * @return 版本号
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * 设置版本号
	 * 
	 * @param version
	 *            版本号
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 判断是否已转报关单
	 * 
	 * @return 是否已转报关单
	 */
	public Boolean getIsTransferCustomsBill() {
		if(isTransferCustomsBill==null)
			return Boolean.FALSE;
		
		return isTransferCustomsBill;
	}

	/**
	 * 设置是否已转报关单标志
	 * 
	 * @param isTransferCustomsBill
	 *            是否已转报关单
	 */
	public void setIsTransferCustomsBill(Boolean isTransferCustomsBill) {
		this.isTransferCustomsBill = isTransferCustomsBill;
	}

	/**
	 * 获取总重量
	 */
	public Double getTotalNetWeight() {
		return totalNetWeight;
	}

	/**
	 * 设置总重量
	 */
	public void setTotalNetWeight(Double totalNetWeight) {
		this.totalNetWeight = totalNetWeight;
	}

	/**
	 * 获取帐册序号
	 */
	public Integer getEmsSerialNo() {
		return emsSerialNo;
	}

	/**
	 * 设置帐册序号
	 */
	public void setEmsSerialNo(Integer emsSerialNo) {
		this.emsSerialNo = emsSerialNo;
	}

	/**
	 * 获取原产国
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 设置原产国
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * 比较方法
	 */
	public int compareTo(Object obj) {
		AtcMergeAfterComInfo o = (AtcMergeAfterComInfo) obj;
		if (o.getEmsSerialNo() == null && this.getEmsSerialNo() == null) {
			return 0;
		} else if (o.getEmsSerialNo() == null) {
			return 1;
		} else if (this.getEmsSerialNo() == null) {
			return -1;
		}
		
		if (o.getEmsSerialNo() > this.getEmsSerialNo()) {
			return -1;
		} else if (o.getEmsSerialNo().equals(this.getEmsSerialNo())) {
			if ((o.getVersion() == null || "".equals(o.getVersion()))
					&& (this.getVersion() == null || "".equals(this.getVersion()))) {
				// 没有版本 则按原产国排序
				if(o.getCountry() == null && this.getCountry() == null) {
					return 0;
				} else if (o.getCountry() == null) {
					return 1;
				} else if (this.getCountry() == null) {
					return -1;
				} else if (o.getCountry().getCode().compareTo(
						this.getCountry().getCode()) > 0) {
					return -1;
				} else if (o.getCountry().getCode().compareTo(
						this.getCountry().getCode()) < 0) {
					return 1;
				} else {
					return 0;
				}
			} else if (o.getVersion() == null || "".equals(o.getVersion())) {
				return 1;
			} else if(this.getVersion() == null || "".equals(this.getVersion())) {
				return -1;
			}
			
			// 有版本按版本排序
			if (Integer.parseInt(o.getVersion()) > Integer.parseInt(this
					.getVersion())) {
				return -1;
			} else if (Integer.parseInt(o.getVersion()) == Integer
					.parseInt(this.getVersion())) {
				// 版本相同按原产国排序
				if(o.getCountry() == null && this.getCountry() == null) {
					return 0;
				} else if (o.getCountry() == null) {
					return 1;
				} else if (this.getCountry() == null) {
					return -1;
				} else if (o.getCountry().getCode().compareTo(
						this.getCountry().getCode()) > 0) {
					return -1;
				} else if (o.getCountry().getCode().compareTo(
						this.getCountry().getCode()) < 0) {
					return 1;
				} else {
					return 0;
				}
			} else {
				return 1;
			}
		} else {
			return 1;
		}

	}

	/**
	 * 获取备用备注
	 */
	public String getStandbyNote() {
		return standbyNote;
	}

	/**
	 * 设置备用备注
	 */
	public void setStandbyNote(String standbyNote) {
		this.standbyNote = standbyNote;
	}

	/**
	 * 获取用途代码
	 */
	public Uses getUsesCode() {
		return usesCode;
	}

	/**
	 * 设置用途代码
	 */
	public void setUsesCode(Uses usesCode) {
		this.usesCode = usesCode;
	}

	/**
	 * 获取事业部
	 */
	public ProjectDept getProjectDept() {
		return projectDept;
	}

	/**
	 * 设置事业部
	 */
	public void setProjectDept(ProjectDept projectDept) {
		this.projectDept = projectDept;
	}

	/**
	 * 获取征免方式
	 */
	public LevyMode getLevyMode() {
		return levyMode;
	}

	/**
	 * 设置征免方式
	 */
	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

	/**
	 * 获取总体积
	 */
	public Double getCbm() {
		return cbm;
	}

	/**
	 * 设置总体积
	 */
	public void setCbm(Double cbm) {
		this.cbm = cbm;
	}

	/**
	 * 获取加工费总价
	 */
	public Double getWorkUsd() {
		return workUsd;
	}

	/**
	 * 设置加工费总价
	 */
	public void setWorkUsd(Double workUsd) {
		this.workUsd = workUsd;
	}

	public Curr getCurrency() {
		return currency;
	}

	public void setCurrency(Curr currency) {
		this.currency = currency;
	}

}