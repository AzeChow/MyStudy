/*
 * Created on 2004-7-26
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ProjectDept;

/**
 * 报关清单归并前商品信息
 * 
 * @author Administrator 报关清单归并前商品信息 table="atcmergebeforecominfo"
 * 2011-02-12check by hcl
 */
public class AtcMergeBeforeComInfo extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 报送清单归并后商品信息
	 */
	private AtcMergeAfterComInfo afterComInfo;

	/**
	 * 商品序号
	 */
	private Integer serialNo;

	/**
	 * 物料信息
	 */
	private Materiel materiel;

	/**
	 * 企业申报单价
	 */
	private Double declaredPrice;

	/**
	 * 申报数量
	 */
	private Double declaredAmount;

	/**
	 * 第一法定数量
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
	 * 产销国
	 */
	private Country salesCountry;

	/**
	 * 用途代码
	 */
	private Uses usesCode;

	/**
	 * 币制
	 */
	private Curr currency;

	/**
	 * 征免方式
	 */
	private LevyMode levyMode;

	/**
	 * 是否自动创建
	 */
	private Boolean isAutoCreate = false;

	/**
	 * 备注
	 */
	private String memos;

	/**
	 * 事业部
	 */
	private ProjectDept projectDept;

	/**
	 * 总净重
	 */
	private Double totalnetweight = null;

	/**
	 * 海关版本号
	 */
	private Integer version = null;
	/**
	 * 企业版本号
	 */
	private String cmpVersion = null;
	/**
	 * 仓库数量
	 */
	private Double storeAmount = null;
	
	/**
	 * 单重
	 */
	private Double unitWeight = null;

	/**
	 * 报关单号
	 */
	private String customsNo = null;

	/**
	 * 扩展备注
	 */
	private String extendMemo = null;

	/**
	 * 扩展备注1
	 */
	private String extendMemo1 = null;

	/**
	 * 件数
	 */
	private Integer piece = null;

	/**
	 * 箱号
	 */
	private String boxNo;
	
	
	/**
	 * 企业版本号
	 */
	public String getCmpVersion() {
		return cmpVersion;
	}
	/**
	 * 企业版本号
	 */
	public void setCmpVersion(String cmpVersion) {
		this.cmpVersion = cmpVersion;
	}
	/**
	 * 工缴费(加工费总价)
	 */
	private Double workUsd;
	
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
	
	/**
	 * 得到箱号
	 * @return
	 */
	public String getBoxNo() {
		return boxNo;
	}

	/**
	 * 设置箱号
	 * @param boxNo
	 */
	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}
	/**
	 * 总金额
	 */
	private Double totalPrice = null;

	/**
	 * 判断是否自动创建
	 * 
	 * @return 是否自动创建
	 */
	public Boolean getIsAutoCreate() {
		return isAutoCreate;
	}

	/**
	 * 设置是否自动创建标志
	 * 
	 * @param isAutoCreate
	 *            是否自动创建
	 */
	public void setIsAutoCreate(Boolean isAutoCreate) {
		this.isAutoCreate = isAutoCreate;
	}

	/**
	 * 取得报关清单归并后商品信息
	 * 
	 * @return 报关清单归并后商品信息
	 */
	public AtcMergeAfterComInfo getAfterComInfo() {
		return afterComInfo;
	}

	/**
	 * 设计报关清单归并后商品信息
	 * 
	 * @param afterComInfo
	 *            报关清单归并后商品信息
	 */
	public void setAfterComInfo(AtcMergeAfterComInfo afterComInfo) {
		this.afterComInfo = afterComInfo;
	}

	/**
	 * 获得申报数量
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
	 * 获得企业申报单价
	 * 
	 * @return 企业申报单价
	 */
	public Double getDeclaredPrice() {
		return declaredPrice;
	}

	/**
	 * 设置企业申报单价
	 * 
	 * @param declaredPrice
	 *            企业申报单价
	 */
	public void setDeclaredPrice(Double declaredPrice) {
		this.declaredPrice = declaredPrice;
	}

	/**
	 * 取得毛重
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
	 * 获得物料信息
	 * 
	 * @return 物料信息
	 */
	public Materiel getMateriel() {
		return materiel;
	}

	/**
	 * 设置物料信息
	 * 
	 * @param materiel
	 *            物料信息
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
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
	 * 获得第二法定数量
	 * 
	 * @return 第二法定数量
	 */
	public Double getSecondLegalAmount() {
		return secondLegalAmount;
	}

	/**
	 * 设置第二法定数量
	 * 
	 * @param secondLegalAmount
	 *            第二法定数量
	 */
	public void setSecondLegalAmount(Double secondLegalAmount) {
		this.secondLegalAmount = secondLegalAmount;
	}

	// /**
	// * @return Returns the materielCode.
	// */
	// public String getMaterielCode() {
	// return materielCode;
	// }
	// /**
	// * @param materielCode The materielCode to set.
	// */
	// public void setMaterielCode(String materielCode) {
	// this.materielCode = materielCode;
	// }
	// /**
	// * @return Returns the materielName.
	// */
	// public String getMaterielName() {
	// return materielName;
	// }
	// /**
	// * @param materielName The materielName to set.
	// */
	// public void setMaterielName(String materielName) {
	// this.materielName = materielName;
	// }
	// /**
	// * @return Returns the materielSpec.
	// */
	// public String getMaterielSpec() {
	// return materielSpec;
	// }
	// /**
	// * @param materielSpec The materielSpec to set.
	// */
	// public void setMaterielSpec(String materielSpec) {
	// this.materielSpec = materielSpec;
	// }
	/**
	 * 获得币制
	 * 
	 * @return 币制
	 */
	public Curr getCurrency() {
		return currency;
	}

	/**
	 * 设置币制
	 * 
	 * @param currency
	 *            币制
	 */
	public void setCurrency(Curr currency) {
		this.currency = currency;
	}

	/**
	 * 获得征免方式
	 * 
	 * @return 征免方式
	 */
	public LevyMode getLevyMode() {
		return levyMode;
	}

	/**
	 * 设置征免方式
	 * 
	 * @param levyMode
	 *            征免方式
	 */
	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

	/**
	 * 获得产销国
	 * 
	 * @return 产销国
	 */
	public Country getSalesCountry() {
		return salesCountry;
	}

	/**
	 * 设置产销国
	 * 
	 * @param salesCountry
	 *            产销国
	 */
	public void setSalesCountry(Country salesCountry) {
		this.salesCountry = salesCountry;
	}

	/**
	 * 获得用途代码
	 * 
	 * @return 用途代码
	 */
	public Uses getUsesCode() {
		return usesCode;
	}

	/**
	 * 设置用途代码
	 * 
	 * @param usesCode
	 *            用途代码
	 */
	public void setUsesCode(Uses usesCode) {
		this.usesCode = usesCode;
	}
	/**
	 * 获取总净重
	 */
	public Double getTotalnetweight() {
		return totalnetweight;
	}
	/**
	 * 设置总净重
	 */
	public void setTotalnetweight(Double totalnetweight) {
		this.totalnetweight = totalnetweight;
	}
	/**
	 * 获取版本
	 */
	public Integer getVersion() {
		return version;
	}
	/**
	 * 设置版本
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * 获取商品序号
	 */
	public Integer getSerialNo() {
		return serialNo;
	}
	/**
	 * 设置商品序号
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * 获取报关单号
	 */
	public String getCustomsNo() {
		return customsNo;
	}
	/**
	 * 设置报关单号
	 */
	public void setCustomsNo(String customsNo) {
		this.customsNo = customsNo;
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
	 * 获取总金额
	 */
	public Double getTotalPrice() {
		if (totalPrice != null && totalPrice != 0.0) {
			return totalPrice;
		}
		Double price = (this.declaredPrice == null ? Double.valueOf(0)
				: this.declaredPrice);
		Double amount = (this.declaredAmount == null ? Double.valueOf(0)
				: this.declaredAmount);
		return price * amount;
	}
	/**
	 * 获取扩展备注
	 */
	public String getExtendMemo() {
		return extendMemo;
	}
	/**
	 * 设置扩展备注 
	 */
	public void setExtendMemo(String extendMemo) {
		this.extendMemo = extendMemo;
	}
	/**
	 * 获取件数
	 */
	public Integer getPiece() {
		return piece;
	}
	/**
	 * 设置件数
	 */
	public void setPiece(Integer piece) {
		this.piece = piece;
	}
	/**
	 * 获取扩展备注
	 */
	public String getExtendMemo1() {
		return extendMemo1;
	}
	/**
	 * 设置扩展备注
	 */
	public void setExtendMemo1(String extendMemo1) {
		this.extendMemo1 = extendMemo1;
	}
	/**
	 * 设置总金额
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * 获取单重
	 */
	public Double getUnitWeight() {
		if(unitWeight==null){
			return 0d;
		}
		return unitWeight;
	}
	/**
	 * 设置单重
	 */
	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}
	/**
	 * 获取仓库数量
	 */
	public Double getStoreAmount() {
		if(storeAmount==null){
			return 0d;
		}
		return storeAmount;
	}
	/**
	 * 设置仓库数量
	 */
	
	public void setStoreAmount(Double storeAmount) {
		this.storeAmount = storeAmount;
	}
	
	
}