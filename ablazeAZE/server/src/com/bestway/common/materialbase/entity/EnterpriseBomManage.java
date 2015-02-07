/*
 * Created on 2004-6-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放工厂BOM管理子件资料
 * 
 * @author Administrator
 */
public class EnterpriseBomManage extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 父级料号
	 */
	private String parentNo = null;

	/**
	 * 子级料号
	 */
	private String compentNo = null;

	/**
	 * 子级名称
	 */
	private String compentName = null;

	/**
	 * 子级名称
	 */
	private String compentSpec = null;

	/**
	 * 单位用量
	 */
	private Double unitDosage = null;

	/**
	 * 单耗
	 */
	private Double unitWare = null;

	/**
	 * 损耗
	 */
	private Double ware = null;

	/**
	 * 净重
	 */
	private Double netWeight = null;

	/**
	 * 毛重
	 */
	private Double grossWeight = null;

	/**
	 * 是否主料
	 */
	private Boolean isMainImg = false;

	/**
	 * 父件生效日期
	 */
	private Date effectDate = null;

	/**
	 * 父件失效日期
	 */
	private Date endDate = null;

	/**
	 * 废品率
	 */
	private Double wasterRate = null;

	/**
	 * 父件版本号
	 */
	private String versionNo = null;

	/**
	 * 废品重量
	 */
	private Double wasterWeight = null;

	/**
	 * 损耗率
	 */
	private Double wareRate = null;

	/**
	 * 材料来源
	 */
	private String imgSource = null;

	/**
	 * 子件生效日期
	 */
	private Date childEffectDate = null;

	/**
	 * 子件失效日期
	 */
	private Date childEndDate = null;

	/**
	 * 子件版本号
	 */
	private String childVersionNo = null;
	/**
	 * 备注
	 */
	private String memo = null;

	/**
	 * 获取子级料号
	 * 
	 * @return compentNo 子级料号
	 */
	public String getCompentNo() {
		return compentNo;
	}

	/**
	 * 设置子级料号
	 * 
	 * @param compentNo
	 *            子级料号
	 */
	public void setCompentNo(String compentNo) {
		this.compentNo = compentNo;
	}

	/**
	 * 获取子级名称
	 * 
	 * @return compentName 子级名称
	 */
	public String getCompentName() {
		return compentName;
	}

	/**
	 * 设置子级名称
	 * 
	 * @param compentName
	 *            子级名称
	 */
	public void setCompentName(String compentName) {
		this.compentName = compentName;
	}

	public String getCompentSpec() {
		return compentSpec;
	}

	public void setCompentSpec(String compentSpec) {
		this.compentSpec = compentSpec;
	}

	/**
	 * 获取父级料号
	 * 
	 * @return parentNo 父级料号
	 */
	public String getParentNo() {
		return parentNo;
	}

	/**
	 * 设置父级料号
	 * 
	 * @param parentNo
	 *            父级料号
	 */
	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	/**
	 * 获取单位用量
	 * 
	 * @return unitDosage 单位用量
	 */
	public Double getUnitDosage() {
		return unitDosage;
	}

	/**
	 * 设置单位用量
	 * 
	 * @param unitDosage
	 *            单位用量
	 */
	public void setUnitDosage(Double unitDosage) {
		this.unitDosage = unitDosage;
	}

	/**
	 * 获取单耗
	 * 
	 * @return unitWare 单耗
	 */
	public Double getUnitWare() {
		return unitWare;
	}

	/**
	 * 设置单耗
	 * 
	 * @param unitWare
	 *            单耗
	 */
	public void setUnitWare(Double unitWare) {
		this.unitWare = unitWare;
	}

	/**
	 * 获取损耗
	 * 
	 * @return ware 损耗
	 */
	public Double getWare() {
		return ware;
	}

	/**
	 * 设置损耗
	 * 
	 * @param ware
	 *            损耗
	 */
	public void setWare(Double ware) {
		this.ware = ware;
	}

	/**
	 * 获取父件生效日期
	 * 
	 * @return effectDate 父件生效日期
	 */
	public Date getEffectDate() {
		return effectDate;
	}

	/**
	 * 设置父件生效日期
	 * 
	 * @param effectDate
	 *            父件生效日期
	 */
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	/**
	 * 获取毛重
	 * 
	 * @return grossWeight 毛重
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
	 * 获取是否主料
	 * 
	 * @return isMainImg 是否主料
	 */
	public Boolean getIsMainImg() {
		return isMainImg;
	}

	/**
	 * 设置是否主料
	 * 
	 * @param isMainImg
	 *            是否主料
	 */
	public void setIsMainImg(Boolean isMainImg) {
		this.isMainImg = isMainImg;
	}

	/**
	 * 获取净重
	 * 
	 * @return netWeight 净重
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
	 * 获取父件失效日期
	 * 
	 * @return endDate 父件失效日期
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置父件失效日期
	 * 
	 * @param endDate
	 *            父件失效日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取父件版本号
	 * 
	 * @return versionNo 父件版本号
	 */
	public String getVersionNo() {
		if (versionNo != null && !"".equals(versionNo.trim())) {
			return String.valueOf(Double.valueOf(versionNo.trim()).intValue());
		}
		return versionNo;
	}

	/**
	 * 设置父件版本号
	 * 
	 * @param versionNo
	 *            父件版本号
	 */
	public void setVersionNo(String versionNo) {
		if (versionNo != null && !"".equals(versionNo.trim())) {
			this.versionNo = String.valueOf(Double.valueOf(versionNo.trim())
					.intValue());
		} else {
			this.versionNo = versionNo;
		}
	}

	/**
	 * 获取废品率
	 * 
	 * @return wasterRate 废品率
	 */
	public Double getWasterRate() {
		return wasterRate;
	}

	/**
	 * 设置废品率
	 * 
	 * @param wasterRate
	 *            废品率
	 */
	public void setWasterRate(Double wasterRate) {
		this.wasterRate = wasterRate;
	}

	/**
	 * 获取损耗率
	 * 
	 * @return wareRate 损耗率
	 */
	public Double getWareRate() {
		return wareRate;
	}

	/**
	 * 设置损耗率
	 * 
	 * @param wareRate
	 *            损耗率
	 */
	public void setWareRate(Double wareRate) {
		this.wareRate = wareRate;
	}

	/**
	 * 获取废品重量
	 * 
	 * @return wasterWeight 废品重量
	 */
	public Double getWasterWeight() {
		return wasterWeight;
	}

	/**
	 * 设置废品重量
	 * 
	 * @param wasterWeight
	 *            废品重量
	 */
	public void setWasterWeight(Double wasterWeight) {
		this.wasterWeight = wasterWeight;
	}

	/**
	 * 获取材料来源
	 * 
	 * @return imgSource 材料来源
	 */
	public String getImgSource() {
		return imgSource;
	}

	/**
	 * 设置材料来源
	 * 
	 * @param imgSource
	 *            材料来源
	 */
	public void setImgSource(String imgSource) {
		this.imgSource = imgSource;
	}

	// public Date getChildEffectDate() {
	// return childEffectDate;
	// }
	// public void setChildEffectDate(Date childEffectDate) {
	// this.childEffectDate = childEffectDate;
	// }
	// public Date getChildEndDate() {
	// return childEndDate;
	// }
	// public void setChildEndDate(Date childEndDate) {
	// this.childEndDate = childEndDate;
	// }
	// public String getChildVersionNo() {
	// return childVersionNo;
	// }
	// public void setChildVersionNo(String childVersionNo) {
	// this.childVersionNo = childVersionNo;
	// }

	/**
	 * 获取子件生效日期
	 * 
	 * @return childEffectDate 子件生效日期
	 */
	public Date getChildEffectDate() {
		return childEffectDate;
	}

	/**
	 * 设置子件生效日期
	 * 
	 * @param childEffectDate
	 *            子件生效日期
	 */
	public void setChildEffectDate(Date childEffectDate) {
		this.childEffectDate = childEffectDate;
	}

	/**
	 * 获取子件失效日期
	 * 
	 * @return childEndDate 子件失效日期
	 */
	public Date getChildEndDate() {
		return childEndDate;
	}

	/**
	 * 设置子件失效日期
	 * 
	 * @param childEndDate
	 *            子件失效日期
	 */
	public void setChildEndDate(Date childEndDate) {
		this.childEndDate = childEndDate;
	}

	/**
	 * 获取子件版本号
	 * 
	 * @return childVersionNo 子件版本号
	 */
	public String getChildVersionNo() {
		return childVersionNo;
	}

	/**
	 * 设置子件版本号
	 * 
	 * @param childVersionNo
	 *            子件版本号
	 */
	public void setChildVersionNo(String childVersionNo) {
		this.childVersionNo = childVersionNo;
	}

	/**
	 * 获得备注
	 * 
	 * @return memo 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
