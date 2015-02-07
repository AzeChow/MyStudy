/*
 * Created on 2004-6-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.materialapply.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.common.materialbase.entity.ShareCode;
import com.bestway.common.materialbase.entity.SysArea;

/**
 * 存放电子手册－物料申报的资料
 * 
 * @author Administrator
 */
public class MaterialChange extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 序号
	 */
	private Integer no;
	/**
	 * 类别
	 */
	private ScmCoi scmCoi; 

	/**
	 * 料号
	 */
	private String ptNo; 

	/**
	 * 十位商品编码
	 */
	private Complex complex; 

	/**
	 * 工厂品名
	 */
	private String factoryName; 

	/**
	 * 工厂规格
	 */
	private String factorySpec; 
	/**
	 * 报关名称
	 */
	private String ptName;
	
	/**
	 * 报关规格
	 */
	private String ptSpec;
	/**
	 * 工厂单位
	 */
	private CalUnit calUnit; 

	/**
	 * 详细型号规格
	 */
	private String ptDeSpec; 

	/**
	 * 报关单位
	 */
	private Unit ptUnit; 

	/**
	 * 工厂单位与报关单位折算系数
	 */
	private Double unitConvert; 

	/**
	 * 单价
	 */
	private Double ptPrice; 

	/**
	 * 净重
	 */
	private Double ptNetWeight; 

	/**
	 * 重量单位
	 */
	private CalUnit calWeightUnit; 

	/**
	 * 毛重
	 */
	private Double ptOutWeight; 

	/**
	 * 版本号
	 */
	private String ptVersion; 

	/**
	 * 英文品名
	 */
	private String ptEnglishName; 

	/**
	 * 英文详细规格
	 */
	private String ptEnglishSpec; 

	/**
	 * 供应商
	 */
	private ScmCoc scmCoc; 

	
	/**
	 * 原产国
	 */
	private SysArea sysArea; 

	/**
	 * 税制代码
	 */
	private ShareCode taxation; 

	/**
	 * 是否保纳税
	 */
	private String proBonded; 
	
	/**
	 * 对应料号
	 */
	private String equalPtno; 

	/**
	 * 废料代码
	 */
	private String scrapCode; 

	/**
	 * 废料重量
	 */
	private Double scrapWeight; 

	/**
	 * 材料来源
	 */
	private String materialSource; 

	/**
	 * 关键零件
	 */
	private Boolean keyHardware = false; 

	// ----明门使用----
	/**
	 * 包装种类
	 */
	private Wrap wrap; 

	/**
	 * 仓库净重
	 */
	private Double cknetWeight; 

	/**
	 * 仓库毛重
	 */
	private Double ckoutWeight; 

	/**
	 * 企业数量
	 */
	private Double amount; 

	/**
	 * 是否在BcsInnerMerge中使用
	 */
	private Boolean isUseInBcsInnerMerge = false; 

	/**
	 * 是否新增物料主挡(联网监管使用)
	 */
	private Boolean isNewMateriel = true; 

	/**
	 * 是否委外
	 */
	private Boolean isOutsource = false; 
	
	/**
	 * 是否被更新
	 */
	private Boolean isUpdateMateriel = false;
	/**
	 * 物料ID
	 */
	private String customMaterialId;

	/**
	 * 电子手册使用--状态标志
	 * ORIGINAL = "0";	原始状态
	 * APPLY = "1"; 	申请
	 * EXECUTE = "2";	执行
	 * CHANGE = "3";	变更
	 * CHECK_CANCEL = "4";	 核销
	 * BACK_BILL = "5";	退单
	 */
	private String stateMark;

    /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark;
	/**
	 * 是够禁止归并
	 */
	private Boolean isForbidMerge =false;
	/**
	 *  是否选中
	 */
	private Boolean isSelected=false;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public CalUnit getCalUnit() {
		return calUnit;
	}

	public void setCalUnit(CalUnit calUnit) {
		this.calUnit = calUnit;
	}

	public CalUnit getCalWeightUnit() {
		return calWeightUnit;
	}

	public void setCalWeightUnit(CalUnit calWeightUnit) {
		this.calWeightUnit = calWeightUnit;
	}

	public Double getCknetWeight() {
		return cknetWeight;
	}

	public void setCknetWeight(Double cknetWeight) {
		this.cknetWeight = cknetWeight;
	}

	public Double getCkoutWeight() {
		return ckoutWeight;
	}

	public void setCkoutWeight(Double ckoutWeight) {
		this.ckoutWeight = ckoutWeight;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public String getEqualPtno() {
		return equalPtno;
	}

	public void setEqualPtno(String equalPtno) {
		this.equalPtno = equalPtno;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getFactorySpec() {
		return factorySpec;
	}

	public void setFactorySpec(String factorySpec) {
		this.factorySpec = factorySpec;
	}

	public Boolean getIsNewMateriel() {
		return isNewMateriel;
	}

	public void setIsNewMateriel(Boolean isNewMateriel) {
		this.isNewMateriel = isNewMateriel;
	}

	public Boolean getIsOutsource() {
		return isOutsource;
	}

	public void setIsOutsource(Boolean isOutsource) {
		this.isOutsource = isOutsource;
	}

	public Boolean getIsUpdateMateriel() {
		return isUpdateMateriel;
	}

	public void setIsUpdateMateriel(Boolean isUpdateMateriel) {
		this.isUpdateMateriel = isUpdateMateriel;
	}

	public Boolean getIsUseInBcsInnerMerge() {
		return isUseInBcsInnerMerge;
	}

	public void setIsUseInBcsInnerMerge(Boolean isUseInBcsInnerMerge) {
		this.isUseInBcsInnerMerge = isUseInBcsInnerMerge;
	}

	public Boolean getKeyHardware() {
		return keyHardware;
	}

	public void setKeyHardware(Boolean keyHardware) {
		this.keyHardware = keyHardware;
	}

	public String getMaterialSource() {
		return materialSource;
	}

	public void setMaterialSource(String materialSource) {
		this.materialSource = materialSource;
	}

	public String getProBonded() {
		return proBonded;
	}

	public void setProBonded(String proBonded) {
		this.proBonded = proBonded;
	}

	public String getPtDeSpec() {
		return ptDeSpec;
	}

	public void setPtDeSpec(String ptDeSpec) {
		this.ptDeSpec = ptDeSpec;
	}

	public String getPtEnglishName() {
		return ptEnglishName;
	}

	public void setPtEnglishName(String ptEnglishName) {
		this.ptEnglishName = ptEnglishName;
	}

	public String getPtEnglishSpec() {
		return ptEnglishSpec;
	}

	public void setPtEnglishSpec(String ptEnglishSpec) {
		this.ptEnglishSpec = ptEnglishSpec;
	}

	public Double getPtNetWeight() {
		return ptNetWeight;
	}

	public void setPtNetWeight(Double ptNetWeight) {
		this.ptNetWeight = ptNetWeight;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public Double getPtOutWeight() {
		return ptOutWeight;
	}

	public void setPtOutWeight(Double ptOutWeight) {
		this.ptOutWeight = ptOutWeight;
	}

	public Double getPtPrice() {
		return ptPrice;
	}

	public void setPtPrice(Double ptPrice) {
		this.ptPrice = ptPrice;
	}

	public Unit getPtUnit() {
		return ptUnit;
	}

	public void setPtUnit(Unit ptUnit) {
		this.ptUnit = ptUnit;
	}

	public String getPtVersion() {
		return ptVersion;
	}

	public void setPtVersion(String ptVersion) {
		this.ptVersion = ptVersion;
	}

	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	public ScmCoi getScmCoi() {
		return scmCoi;
	}

	public void setScmCoi(ScmCoi scmCoi) {
		this.scmCoi = scmCoi;
	}

	public String getScrapCode() {
		return scrapCode;
	}

	public void setScrapCode(String scrapCode) {
		this.scrapCode = scrapCode;
	}

	public Double getScrapWeight() {
		return scrapWeight;
	}

	public void setScrapWeight(Double scrapWeight) {
		this.scrapWeight = scrapWeight;
	}

	public SysArea getSysArea() {
		return sysArea;
	}

	public void setSysArea(SysArea sysArea) {
		this.sysArea = sysArea;
	}

	public ShareCode getTaxation() {
		return taxation;
	}

	public void setTaxation(ShareCode taxation) {
		this.taxation = taxation;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	public Wrap getWrap() {
		return wrap;
	}

	public void setWrap(Wrap wrap) {
		this.wrap = wrap;
	}

	public String getCustomMaterialId() {
		return customMaterialId;
	}

	public void setCustomMaterialId(String customMaterialId) {
		this.customMaterialId = customMaterialId;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getStateMark() {
		return stateMark;
	}

	public void setStateMark(String stateMark) {
		this.stateMark = stateMark;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Boolean getIsForbidMerge() {
		return isForbidMerge;
	}

	public void setIsForbidMerge(Boolean isForbidMerge) {
		this.isForbidMerge = isForbidMerge;
	}

}
