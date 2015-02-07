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
 * 存放电子手册－物料申报的变更情况
 * 
 * @author Administrator
 */
public class MaterialHistory extends BaseScmEntity {     
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 物料ID
	 */
	private String customMaterialId;
	
	/**
	 * 物料类别
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
	 * 工厂单位
	 */
	private CalUnit calUnit;     
	
	
//	private String ptName;        //报关品名
//	private String ptSpec;        //报关型号规格
	
	/**
	 * 报关单位
	 */
	private Unit ptUnit;        
	
	/**
	 * 工厂单位与报关单位折算系数
	 */
	private Double unitConvert; 
	
	/**
	 * 详细型号规格
	 */
	private String ptDeSpec;    
	
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
	 * 供应商、客户、合作伙伴,scmcoc指对象
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
	 *,对应料号
	 */
	private String equalPtno;  
	
	/**
	 * 废料重量
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
	
	/**
	 *明门使用--包装种类
	 */
	private Wrap wrap;
	
	/**
	 * 明门使用--仓库净重
	 */
	private Double cknetWeight;  
	
	/**
	 * 明门使用--仓库毛重
	 */
	private Double ckoutWeight;   
	
	/**
	 * 明门使用--企业数量
	 */
	private Double amount;     
	
	/**
	 * 状态标志
	 * ORIGINAL = "0"	原始状态
	 * APPLY = "1";	申请
	 * EXECUTE = "2";	执行
	 * CHANGE = "3";	变更
	 * CHECK_CANCEL = "4";	核销
	 * BACK_BILL = "5";	退单
	 */
	private String stateMark;
	
	/**
	 * 获取物料ID
	 * 
	 * @return customMaterialId 物料ID
	 */
	public String getCustomMaterialId() {
		return customMaterialId;
	}
	
	/**
	 * 设置物料ID
	 * 
	 * @param customMaterialId 物料ID
	 */
	public void setCustomMaterialId(String customMaterialId) {
		this.customMaterialId = customMaterialId;
	}
	
	/**
	 * 获取工厂单位
	 * 
	 * @return calUnit 工厂单位
	 */
	public CalUnit getCalUnit() {
		return calUnit;
	}
	
	/**
	 * 设置工厂单位
	 *
	 * @param calUnit 工厂单位
	 */
	public void setCalUnit(CalUnit calUnit) {
		this.calUnit = calUnit;
	}
	
	/**
	 * 获取重量单位
	 * 
	 * @return calWeightUnit 重量单位
	 */
	public CalUnit getCalWeightUnit() {
		return calWeightUnit;
	}
	
	/**
	 * 设置重量单位
	 * 
	 * @param calWeightUnit 重量单位
	 */
	public void setCalWeightUnit(CalUnit calWeightUnit) {
		this.calWeightUnit = calWeightUnit;
	}
	
	/**
	 * 获取十位商品编码
	 * 
	 * @return complex 十位商品编码
	 */
	public Complex getComplex() {
		return complex;
	}
	
	/**
	 * 设置十位商品编码
	 * 
	 * @param complex 十位商品编码
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}
	
	/**
	 * 获取是否保纳税 0：保税，1：纳税，2：保纳税
	 * 
	 * @return proBonded 是否保纳税 0：保税，1：纳税，2：保纳税
	 */
	public String getProBonded() {
		return proBonded;
	}
	
	/**
	 * 设置是否保纳税 0：保税，1：纳税，2：保纳税
	 * 
	 * @param proBonded 是否保纳税 0：保税，1：纳税，2：保纳税
	 */
	public void setProBonded(String proBonded) {
		this.proBonded = proBonded;
	}
	
	/**
	 * 获取详细型号规格 
	 * 
	 * @return ptDeSpec 详细型号规格 
	 */
	public String getPtDeSpec() {
		return ptDeSpec;
	}
	
	/**
	 * 设置详细型号规格 
	 * 
	 * @param ptDeSpec 详细型号规格 
	 */
	public void setPtDeSpec(String ptDeSpec) {
		this.ptDeSpec = ptDeSpec;
	}
	
	/**
	 * 获取英文品名
	 * 
	 * @return ptEnglishName 英文品名
	 */
	public String getPtEnglishName() {
		return ptEnglishName;
	}
	
	/**
	 * 设置英文品名
	 * 
	 * @param ptEnglishName 英文品名
	 */
	public void setPtEnglishName(String ptEnglishName) {
		this.ptEnglishName = ptEnglishName;
	}
	
	/**
	 * 获取英文详细规格
	 * 
	 * @return ptEnglishSpec 英文详细规格
	 */
	public String getPtEnglishSpec() {
		return ptEnglishSpec;
	}
	
	/**
	 * 设置英文详细规格
	 * 
	 * @param ptEnglishSpec 英文详细规格
	 */
	public void setPtEnglishSpec(String ptEnglishSpec) {
		this.ptEnglishSpec = ptEnglishSpec;
	}
//	/**
//	 * @return Returns the ptName.
//	 */
//	public String getFactoryName() {
//		return ptName;
//	}
//	/**
//	 * @param ptName The ptName to set.
//	 */
//	public void setFactoryName(String ptName) {
//		this.ptName = ptName;
//	}
	/**
	 * 获取净重
	 * 
	 * @return ptNetWeight 净重
	 */
	public Double getPtNetWeight() {
		return ptNetWeight;
	}
	
	/**
	 * 设置净重
	 * 
	 * @param ptNetWeight 净重
	 */
	public void setPtNetWeight(Double ptNetWeight) {
		this.ptNetWeight = ptNetWeight;
	}
	
	/**
	 * 获取料号
	 * 
	 * @return ptNo 料号
	 */
	public String getPtNo() {
		return ptNo;
	}
	
	/**
	 * 设置料号
	 * 
	 * @param ptNo 料号
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}
	
	/**
	 * 获取毛重
	 * 
	 * @return ptOutWeight 毛重
	 */
	public Double getPtOutWeight() {
		return ptOutWeight;
	}
	
	/**
	 * 设置毛重
	 * 
	 * @param ptOutWeight 毛重
	 */
	public void setPtOutWeight(Double ptOutWeight) {
		this.ptOutWeight = ptOutWeight;
	}
	
	/**
	 * 获取单价
	 * 
	 * @return ptPrice 单价
	 */
	public Double getPtPrice() {
		return ptPrice;
	}
	
	/**
	 * 设置单价
	 * 
	 * @param ptPrice 单价
	 */
	public void setPtPrice(Double ptPrice) {
		this.ptPrice = ptPrice;
	}
//	/**
//	 * @return Returns the ptSpec.
//	 */
//	public String getFactorySpec() {
//		return ptSpec;
//	}
//	/**
//	 * @param ptSpec The ptSpec to set.
//	 */
//	public void setFactorySpec(String ptSpec) {
//		this.ptSpec = ptSpec;
//	}
	/**
	 * 获取版本号
	 * 
	 * @return ptVersion 版本号
	 */
	public String getPtVersion() {
		return ptVersion;
	}
	
	/**
	 * 
	 * 设置版本号
	 * 
	 * @param ptVersion 版本号
	 */
	public void setPtVersion(String ptVersion) {
		this.ptVersion = ptVersion;
	}
	
	/**
	 * 获取供应商、客户、合作伙伴   scmcoc指对象
	 * 
	 * @return scmCoc 供应商、客户   scmcoc指对象
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}
	
	/**
	 * 设置供应商、客户、合作伙伴   scmcoc指对象
	 * 
	 * @param scmCoc 供应商、客户、合作伙伴   scmcoc指对象
	 */
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}
	
	/**
	 * 获取物料类别
	 * 
	 * @return scmCoi 物料类别
	 */
	public ScmCoi getScmCoi() {
		return scmCoi;
	}
	
	/**
	 * 设置
	 * 
	 * @param scmCoi 物料类别
	 */
	public void setScmCoi(ScmCoi scmCoi) {
		this.scmCoi = scmCoi;
	}
	
	/**
	 * 获取原产国
	 * 
	 * @return sysArea 原产国
	 */
	public SysArea getSysArea() {
		return sysArea;
	}
	
	/**
	 * 设置原产国
	 * 
	 * @param sysArea 原产国
	 */
	public void setSysArea(SysArea sysArea) {
		this.sysArea = sysArea;
	}
	
	/**
	 * 获取税制代码
	 * 
	 * @return taxation 税制代码
	 */
	public ShareCode getTaxation() {
		return taxation;
	}
	
	/**
	 * 设置税制代码
	 * 
	 * @param taxation 税制代码
	 */
	public void setTaxation(ShareCode taxation) {
		this.taxation = taxation;
	}
	
	/**
	 * 获取对应料号
	 * 
	 * @return equalPtno,对应料号
	 */
	public String getEqualPtno() {
		return equalPtno;
	}
	
	/**
	 *,对应料号设置
	 * 
	 * @param equalPtno 对应料号
	 */
	public void setEqualPtno(String equalPtno) {
		this.equalPtno = equalPtno;
	}
	
	/**
	 * 获取工厂品名
	 * 
	 * @return factoryName 工厂品名
	 */
	public String getFactoryName() {
		return factoryName;
	}
	
	/**
	 * 设置工厂品名
	 * 
	 * @param factoryName 工厂品名
	 */
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	
	/**
	 * 获取工厂规格
	 * 
	 * @return factorySpec 工厂规格
	 */
	public String getFactorySpec() {
		return factorySpec;
	}
	
	/**
	 * 设置工厂规格
	 * 
	 * @param factorySpec 工厂规格
	 */
	public void setFactorySpec(String factorySpec) {
		this.factorySpec = factorySpec;
	}
	
	/**
	 * 获取报关单位
	 * 
	 * @return ptUnit 报关单位
	 */
	public Unit getPtUnit() {
		return ptUnit;
	}
	
	/**
	 * 设置报关单位
	 * 
	 * @param ptUnit 报关单位
	 */
	public void setPtUnit(Unit ptUnit) {
		this.ptUnit = ptUnit;
	}
	
	/**
	 * 获取工厂单位与报关单位折算系数
	 * 
	 * @return unitConvert 工厂单位与报关单位折算系数
	 */
	public Double getUnitConvert() {
		return unitConvert;
	}
	
	/**
	 * 设置工厂单位与报关单位折算系数
	 * 
	 * @param unitConvert 工厂单位与报关单位折算系数
	 */
	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}
	
	/**
	 * 获取材料来源
	 * 
	 * @return materialSource 材料来源
	 */
	public String getMaterialSource() {
		return materialSource;
	}
	
	/**
	 * 设置材料来源
	 * 
	 * @param materialSource 材料来源
	 */
	public void setMaterialSource(String materialSource) {
		this.materialSource = materialSource;
	}
	
	/**
	 * 获取废料代码
	 * 
	 * @return scrapCode 废料代码
	 */
	public String getScrapCode() {
		return scrapCode;
	}
	
	/**
	 * 设置废料代码
	 * 
	 * @param scrapCode 废料代码
	 */
	public void setScrapCode(String scrapCode) {
		this.scrapCode = scrapCode;
	}
	
	/**
	 * 获取废料重量
	 * 
	 * @return scrapWeight 废料重量
	 */
	public Double getScrapWeight() {
		return scrapWeight;
	}
	
	/**
	 * 设置废料重量
	 * 
	 * @param scrapWeight 废料重量
	 */
	public void setScrapWeight(Double scrapWeight) {
		this.scrapWeight = scrapWeight;
	}
	
	/**
	 * 获取关键零件
	 * 
	 * @return keyHardware 关键零件
	 */
	public Boolean getKeyHardware() {
		return keyHardware;
	}
	
	/**
	 * 设置关键零件
	 * 
	 * @param keyHardware 关键零件
	 */
	public void setKeyHardware(Boolean keyHardware) {
		this.keyHardware = keyHardware;
	}
	
	/**
	 * 获取仓库净重
	 * 
	 * @return cknetWeight 仓库净重
	 */
	public Double getCknetWeight() {
		return cknetWeight;
	}
	
	/**
	 * 设置仓库净重
	 * 
	 * @param cknetWeight 仓库净重
	 */
	public void setCknetWeight(Double cknetWeight) {
		this.cknetWeight = cknetWeight;
	}
	
	/**
	 * 获取仓库毛重
	 * 
	 * @return ckoutWeight 仓库毛重
	 */
	public Double getCkoutWeight() {
		return ckoutWeight;
	}
	
	/**
	 * 设置仓库毛重
	 * 
	 * @param ckoutWeight 仓库毛重
	 */
	public void setCkoutWeight(Double ckoutWeight) {
		this.ckoutWeight = ckoutWeight;
	}
	
	/**
	 * 获取包装种类
	 * 
	 * @return wrap 包装种类
	 */
	public Wrap getWrap() {
		return wrap;
	}
	
	/**
	 * 设置包装种类
	 * 
	 * @param wrap 包装种类
	 */
	public void setWrap(Wrap wrap) {
		this.wrap = wrap;
	}
	
	/**
	 * 获取企业数量
	 * 
	 * @return amount 企业数量
	 */
	public Double getAmount() {
		return amount;
	}
	
	/**
	 * 设置企业数量
	 * 
	 * @param amount 企业数量
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	/**
	 * 获取状态标志
	 * 
	 * @return stateMark 状态标志
	 */
	public String getStateMark() {
		return stateMark;
	}
	
	/**
	 * 设置状态标志
	 * 
	 * @param stateMark 状态标志
	 */
	public void setStateMark(String stateMark) {
		this.stateMark = stateMark;
	}
}
