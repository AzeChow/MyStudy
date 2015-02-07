/*
 * Created on 2004-7-2
 * 内部归并
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.entity;

// 李扬更改程序
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 存放企业内部归并－－内部归并资料
 * 
 * @author bsway
 */
public class InnerMergeData extends BaseScmEntity {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 类型
	 */
	private String imrType;

	/**
	 * 关联的物料基本资料
	 */
	private Materiel materiel;

	/**
	 * 归并前法定单位
	 */
	private Unit hsBeforeLegalUnit;

	/**
	 * 归并前企业单位
	 */
	private CalUnit hsBeforeEnterpriseUnit;

	/**
	 * 归并前商品名称，规格
	 */
	private String hsBeforeMaterielNameSpec;

	/**
	 * 十位备案序号
	 */
	private Integer hsAfterTenMemoNo;

	/**
	 * 归并后10位商品编码
	 */
	private Complex hsAfterComplex;

	/**
	 * 归并后商品名称
	 */
	private String hsAfterMaterielTenName;

	/**
	 * 归并后商品型号规格
	 */
	private String hsAfterMaterielTenSpec;

	/**
	 * 归并后备案单位
	 */
	private Unit hsAfterMemoUnit;

	/**
	 * 归并后第一法定单位
	 */
	private Unit hsAfterLegalUnit;

	/**
	 * 归并后第二法定单位
	 */
	private Unit hsAfterSecondLegalUnit;

	/**
	 * 归并后商品名称，规格
	 */
	private String hsAfterMaterielNameSpec;

	/**
	 * 四位编码序号
	 */
	private Integer hsFourNo;

	/**
	 * 四位商品名称
	 */
	private String hsFourMaterielName;

	/**
	 * 四位商品编码
	 */
	private String hsFourCode;

	/**
	 * 帐册序号
	 */
	private String emsSerialNo;

	/**
	 * 是否已经备案
	 */
	private Boolean isPutOnRecord = false;

	/**
	 * 是否存在电子帐册归并关系
	 */
	private Boolean isExistMerger = false;

	/**
	 * 导入次数
	 */
	private Date importTimer = null;

	/**
	 * 最后变更时间
	 */
	private Date updateDate = null;
	/**
	 * 第一法定单位比例因子
	 */
	private Double fristUnitRatio;
	/**
	 * 第二法定单位比例因子
	 */
	private Double secondUnitRatio;

	/**
	 * 重量单位比例因子
	 */
	private Double weigthUnitGene;
	/**
	 * 是否主料
	 */
	private Boolean isMainImg = false;
	
	// =================下面是禁用所使用================

	/**
	 * 禁用日期
	 */
	private Date forbidDate;
	/**
	 * 禁用人
	 */
	private String forbidUser;
	/**
	 * 恢复日期
	 */
	private Date revertDate;
	/**
	 * 恢复人
	 */
	private String revertUser;
	/**
	 * 是否禁止
	 */
	private Boolean isForbid=false;

	/**
	 * 
	 * 得到禁用日期
	 * @return
	 */
	public Date getForbidDate() {
		return forbidDate;
	}

	/**
	 * 
	 * 设置禁用日期
	 * @return
	 */
	public void setForbidDate(Date forbidDate) {
		this.forbidDate = forbidDate;
	}
	/**
	 * 
	 * 得到禁用人
	 * @return
	 */
	public String getForbidUser() {
		return forbidUser;
	}
	/**
	 * 
	 * 设置禁用人
	 * @return
	 */
	public void setForbidUser(String forbidUser) {
		this.forbidUser = forbidUser;
	}
	/**
	 * 
	 * 得到恢复日期
	 * @return
	 */
	public Date getRevertDate() {
		return revertDate;
	}
	/**
	 * 
	 * 设置恢复日期
	 * @return
	 */
	public void setRevertDate(Date revertDate) {
		this.revertDate = revertDate;
	}
	/**
	 * 
	 * 得到恢复人
	 * @return
	 */
	public String getRevertUser() {
		return revertUser;
	}
	/**
	 * 
	 * 设置恢复人
	 * @return
	 */
	public void setRevertUser(String revertUser) {
		this.revertUser = revertUser;
	}

	public Boolean getIsForbid() {
		return isForbid;
	}

	public void setIsForbid(Boolean isForbid) {
		this.isForbid = isForbid;
	}

	/**
	 * 获取帐册序号
	 * 
	 * @return emsSerialNo 帐册序号
	 */
	public String getEmsSerialNo() {
		return emsSerialNo;
	}

	/**
	 * 设置帐册序号
	 * 
	 * @param emsSerialNo
	 *            帐册序号
	 */
	public void setEmsSerialNo(String emsSerialNo) {
		this.emsSerialNo = emsSerialNo;
	}

	/**
	 * 获取是否已经备案
	 * 
	 * @return isPutOnRecord 是否已经备案
	 */
	public Boolean getIsPutOnRecord() {
		return isPutOnRecord;
	}

	/**
	 * 设置是否已经备案
	 * 
	 * @param isPutOnRecord
	 *            是否已经备案
	 */
	public void setIsPutOnRecord(Boolean isPutOnRecord) {
		this.isPutOnRecord = isPutOnRecord;
	}

	/**
	 * 获取归并前企业单位(从物料主档中的企业单位对应的海关单位得来)
	 * 
	 * @return hsBeforeEnterpriseUnit 归并前企业单位(从物料主档中的企业单位对应的海关单位得来)
	 */
	public CalUnit getHsBeforeEnterpriseUnit() {
		return hsBeforeEnterpriseUnit;
	}

	/**
	 * 设置归并前企业单位(从物料主档中的企业单位对应的海关单位得来)
	 * 
	 * @param hsBeforeEnterpriseUnit
	 *            归并前企业单位(从物料主档中的企业单位对应的海关单位得来)
	 */
	public void setHsBeforeEnterpriseUnit(CalUnit hsBeforeEnterpriseUnit) {
		this.hsBeforeEnterpriseUnit = hsBeforeEnterpriseUnit;
	}

	/**
	 * 获取归并前法定单位(从物料主档中的海关编码的第一单位中得来)
	 * 
	 * @return hsBeforeLegalUnit 归并前法定单位(从物料主档中的海关编码的第一单位中得来)
	 */
	public Unit getHsBeforeLegalUnit() {
		return hsBeforeLegalUnit;
	}

	/**
	 * 设置归并前法定单位(从物料主档中的海关编码的第一单位中得来)
	 * 
	 * @param hsBeforeLegalUnit
	 *            归并前法定单位(从物料主档中的海关编码的第一单位中得来)
	 */
	public void setHsBeforeLegalUnit(Unit hsBeforeLegalUnit) {
		this.hsBeforeLegalUnit = hsBeforeLegalUnit;
	}

	/**
	 * 获取归并后第一法定单位
	 * 
	 * @return hsAfterLegalUnit 归并后第一法定单位
	 */
	public Unit getHsAfterLegalUnit() {
		return hsAfterLegalUnit;
	}

	/**
	 * 设置归并后第一法定单位
	 * 
	 * @param hsAfterLegalUnit
	 *            归并后第一法定单位
	 */
	public void setHsAfterLegalUnit(Unit hsAfterLegalUnit) {
		this.hsAfterLegalUnit = hsAfterLegalUnit;
	}

	/**
	 * 获取归并后商品型号规格
	 * 
	 * @return hsAfterMaterielTenSpec 归并后商品型号规格
	 */
	public String getHsAfterMaterielTenSpec() {
		return hsAfterMaterielTenSpec;
	}

	/**
	 * 设置归并后商品型号规格
	 * 
	 * @param hsAfterMaterielTenSpec
	 *            归并后商品型号规格
	 */
	public void setHsAfterMaterielTenSpec(String hsAfterMaterielTenSpec) {
		this.hsAfterMaterielTenSpec = hsAfterMaterielTenSpec;
	}

	/**
	 * 获取归并后商品名称，规格
	 * 
	 * @return hsBeforeMaterielNameSpec 归并后商品名称，规格
	 */
	public String getHsAfterMaterielNameSpec() {
		hsAfterMaterielNameSpec = "";
		if (this.getHsAfterMaterielTenName() != null
				&& !this.getHsAfterMaterielTenName().equals("")) {
			hsAfterMaterielNameSpec += this.getHsAfterMaterielTenName() + "/";
		}
		if (this.getHsAfterMaterielTenSpec() != null
				&& !this.getHsAfterMaterielTenSpec().equals("")) {
			if (!hsAfterMaterielNameSpec.equals("")) {
				hsAfterMaterielNameSpec += this.getHsAfterMaterielTenSpec();
			} else {
				hsAfterMaterielNameSpec += "/"
						+ this.getHsAfterMaterielTenSpec();
			}
		}
		return hsAfterMaterielNameSpec;
	}

	/**
	 * 获取归并前商品名称，规格。
	 * 
	 * @return hsBeforeMaterielNameSpec 归并前商品名称，规格。
	 */
	public String getHsBeforeMaterielNameSpec() {
		hsBeforeMaterielNameSpec = "";
		if (materiel.getFactoryName() != null
				&& !materiel.getFactoryName().equals("")) {
			hsBeforeMaterielNameSpec += materiel.getFactoryName() + "/";
		}
		if (materiel.getFactorySpec() != null
				&& !materiel.getFactorySpec().equals("")) {
			if (!hsBeforeMaterielNameSpec.equals("")) {
				hsBeforeMaterielNameSpec += materiel.getFactorySpec();
			} else {
				hsBeforeMaterielNameSpec += "/" + materiel.getFactorySpec();
			}
		}
		return hsBeforeMaterielNameSpec;
	}

	/**
	 * 获取归并后10位商品编码
	 * 
	 * @return hsAfterComplex 归并后10位商品编码
	 */
	public Complex getHsAfterComplex() {
		return hsAfterComplex;
	}

	/**
	 * 设置归并后10位商品编码
	 * 
	 * @param hsAfterComplex
	 *            归并后10位商品编码
	 */
	public void setHsAfterComplex(Complex hsAfterComplex) {
		this.hsAfterComplex = hsAfterComplex;
	}

	/**
	 * 获取关联的物料基本资料
	 * 
	 * @return materiel 关联的物料基本资料
	 */
	public Materiel getMateriel() {
		return materiel;
	}

	/**
	 * 设置关联的物料基本资料
	 * 
	 * @param materiel
	 *            关联的物料基本资料
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	/**
	 * 获取归并后商品名称
	 * 
	 * @return hsAfterCommodityTenName 归并后商品名称
	 */
	public String getHsAfterMaterielTenName() {
		return hsAfterMaterielTenName;
	}

	/**
	 * 设置归并后商品名称
	 * 
	 * @param hsAfterCommodityTenName
	 *            归并后商品名称
	 */
	public void setHsAfterMaterielTenName(String hsAfterCommodityTenName) {
		this.hsAfterMaterielTenName = hsAfterCommodityTenName;
	}

	/**
	 * 获取归并后备案单位
	 * 
	 * @return hsAfterMemoUnit 归并后备案单位
	 */
	public Unit getHsAfterMemoUnit() {
		return hsAfterMemoUnit;
	}

	/**
	 * 设置归并后备案单位
	 * 
	 * @param hsAfterMemoUnit
	 *            归并后备案单位
	 */
	public void setHsAfterMemoUnit(Unit hsAfterMemoUnit) {
		this.hsAfterMemoUnit = hsAfterMemoUnit;
	}

	/**
	 * 获取十位备案序号
	 * 
	 * @return hsAfterTenMemoNo 十位备案序号
	 */
	public Integer getHsAfterTenMemoNo() {
		return hsAfterTenMemoNo;
	}

	/**
	 * 设置十位备案序号
	 * 
	 * @param hsAfterTenMemoNo
	 *            十位备案序号
	 */
	public void setHsAfterTenMemoNo(Integer hsAfterTenMemoNo) {
		this.hsAfterTenMemoNo = hsAfterTenMemoNo;
	}

	/**
	 * 获取四位商品编码
	 * 
	 * @return hsFourCode 四位商品编码
	 */
	public String getHsFourCode() {
		return hsFourCode;
	}

	/**
	 * 设置四位商品编码
	 * 
	 * @param hsFourCode
	 *            四位商品编码
	 */
	public void setHsFourCode(String hsFourCode) {
		this.hsFourCode = hsFourCode;
	}

	/**
	 * 获取四位商品名称
	 * 
	 * @return hsFourCommodityName 四位商品名称
	 */
	public String getHsFourMaterielName() {
		return hsFourMaterielName;
	}

	/**
	 * 设置四位商品名称
	 * 
	 * @param hsFourCommodityName
	 *            四位商品名称
	 */
	public void setHsFourMaterielName(String hsFourCommodityName) {
		this.hsFourMaterielName = hsFourCommodityName;
	}

	/**
	 * 获取四位编码序号
	 * 
	 * @return hsFourNo 四位编码序号
	 */
	public Integer getHsFourNo() {
		return hsFourNo;
	}

	/**
	 * 设置四位编码序号
	 * 
	 * @param hsFourNo
	 *            四位编码序号
	 */
	public void setHsFourNo(Integer hsFourNo) {
		this.hsFourNo = hsFourNo;
	}

	/**
	 * 获取类型
	 * 
	 * @return imrType 类型
	 */
	public String getImrType() {
		return imrType;
	}

	/**
	 * 设置类型
	 * 
	 * @param imrType
	 *            类型
	 */
	public void setImrType(String imrType) {
		this.imrType = imrType;
	}

	/**
	 * 获取归并后第二法定单位
	 * 
	 * @return hsAfterSecondLegalUnit 归并后第二法定单位
	 */
	public Unit getHsAfterSecondLegalUnit() {
		return hsAfterSecondLegalUnit;
	}

	/**
	 * 设置归并后第二法定单位
	 * 
	 * @param hsAfterSecondLegalUnit
	 *            归并后第二法定单位
	 */
	public void setHsAfterSecondLegalUnit(Unit hsAfterSecondLegalUnit) {
		this.hsAfterSecondLegalUnit = hsAfterSecondLegalUnit;
	}

	/**
	 * 设置归并后商品名称，规格
	 * 
	 * @param hsAfterMaterielNameSpec
	 *            归并后商品名称，规格
	 */
	public void setHsAfterMaterielNameSpec(String hsAfterMaterielNameSpec) {
		this.hsAfterMaterielNameSpec = hsAfterMaterielNameSpec;
	}

	/**
	 * 设置归并前商品名称，规格
	 * 
	 * @param hsBeforeMaterielNameSpec
	 *            归并前商品名称，规格
	 */
	public void setHsBeforeMaterielNameSpec(String hsBeforeMaterielNameSpec) {
		this.hsBeforeMaterielNameSpec = hsBeforeMaterielNameSpec;
	}

	/**
	 * 获取是否存在电子帐册归并关系
	 * 
	 * @return isExistMerger 是否存在电子帐册归并关系
	 */
	public Boolean getIsExistMerger() {
		return isExistMerger;
	}

	/**
	 * 设置是否存在电子帐册归并关系
	 * 
	 * @param isExistMerger
	 *            是否存在电子帐册归并关系
	 */
	public void setIsExistMerger(Boolean isExistMerger) {
		this.isExistMerger = isExistMerger;
	}

	public String getIsMerger() {
		if (isExistMerger != null && isExistMerger.equals(new Boolean(true))) {
			return "是";
		} else {
			return "否";
		}
	}

	public Date getImportTimer() {
		if (importTimer == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(importTimer));
	}

	public void setImportTimer(Date importTimer) {
		this.importTimer = importTimer;
	}

	public Date getUpdateDate() {
		if (updateDate == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(updateDate));
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Double getFristUnitRatio() {
		return fristUnitRatio;
	}

	public void setFristUnitRatio(Double fristUnitRatio) {
		this.fristUnitRatio = fristUnitRatio;
	}

	public Double getSecondUnitRatio() {
		return secondUnitRatio;
	}

	public void setSecondUnitRatio(Double secondUnitRatio) {
		this.secondUnitRatio = secondUnitRatio;
	}

	public Boolean getIsMainImg() {
		return isMainImg;
	}

	public void setIsMainImg(Boolean isMainImg) {
		this.isMainImg = isMainImg;
	}

	public Double getWeigthUnitGene() {
		return weigthUnitGene;
	}

	public void setWeigthUnitGene(Double weigthUnitGene) {
		this.weigthUnitGene = weigthUnitGene;
	}
	
}