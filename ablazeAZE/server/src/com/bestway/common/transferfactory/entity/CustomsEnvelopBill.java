package com.bestway.common.transferfactory.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 关封单据管理
 * 
 * @author Administrator 2011-03-03 check by hcl
 */
public class CustomsEnvelopBill extends BaseScmEntity // 关封管理
{
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 关封号
	 */
	private String customsEnvelopBillNo = null;
	/**
	 * 购销合同编号
	 */
	private String purchaseAndSaleContractNo = null;
	/**
	 * 是进货还是出货
	 */
	private Boolean isImpExpGoods = false;
	/**
	 * 生效
	 */
	private Boolean isAvailability = false;
	/**
	 * 审批海关
	 */
	private Customs customs = null;
	/**
	 * 生效日期
	 */
	private Date beginAvailability = null;
	/**
	 * 有效截止日期
	 */
	private Date endAvailability = null;
	/**
	 * 客户供应商
	 */
	private ScmCoc scmCoc = null;
	/**
	 * 转厂公司
	 */
	private Company transCompany = null;
	/**
	 * 是否结案
	 */
	private Boolean isEndCase = false;
	/**
	 * 结案日期
	 */
	private Date endCaseDate = null;
	/**
	 * 结转报关单号
	 */
	private String carryForwardApplyToCustomsBillNo = null;
	/**
	 * 项目类型 BCUS = 0; 电子帐册 BCS = 1; 电子化手册 DZSC = 3; 电子手册
	 */
	private Integer projectType = null;
	/**
	 * 账册/手册号
	 */
	private String emsNo = null;
	/**
	 * 备注
	 */
	private String memo = null;
	/**
	 * 条码号
	 */
	private String codeNo = null;

	/**
	 * 获取生效日期
	 */
	public Date getBeginAvailability() {
		if (beginAvailability == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(beginAvailability));
	}

	/**
	 * 设置生效日期
	 */
	public void setBeginAvailability(Date beginAvailability) {
		this.beginAvailability = beginAvailability;
	}

	/**
	 * 获取结转报关单号
	 */
	public String getCarryForwardApplyToCustomsBillNo() {
		return carryForwardApplyToCustomsBillNo;
	}

	/**
	 * 获取是否生效标识
	 */
	public Boolean getIsAvailability() {
		return isAvailability;
	}

	/**
	 * 设置是否生效标识
	 */
	public void setIsAvailability(Boolean isAvailability) {
		this.isAvailability = isAvailability;
	}

	/**
	 * 设置结转报关单号
	 */
	public void setCarryForwardApplyToCustomsBillNo(
			String carryForwardApplyToCustomsBillNo) {
		this.carryForwardApplyToCustomsBillNo = carryForwardApplyToCustomsBillNo;
	}

	/**
	 * 获取有效截止日期
	 */
	public Date getEndAvailability() {
		if (endAvailability == null) {
			return endAvailability;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(endAvailability));
	}

	/**
	 * 设置有效截止日期
	 */
	public void setEndAvailability(Date endAvailability) {
		this.endAvailability = endAvailability;
	}

	/**
	 * 获取结案日期
	 */
	public Date getEndCaseDate() {
		if (endCaseDate == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(endCaseDate));
	}

	/**
	 * /** 设置结案日期
	 */
	public void setEndCaseDate(Date endCaseDate) {
		this.endCaseDate = endCaseDate;
	}

	/**
	 * 获取是否结案标识
	 */
	public Boolean getIsEndCase() {
		return isEndCase;
	}

	/**
	 * 设置是否结案标识
	 */
	public void setIsEndCase(Boolean isEndCase) {
		this.isEndCase = isEndCase;
	}

	/**
	 * 获取备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取客户供应商
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	/**
	 * 设置客户供应商
	 */
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	/**
	 * 获取审批海关
	 */
	public Customs getCustoms() {
		return customs;
	}

	/**
	 * 设置审批海关
	 */
	public void setCustoms(Customs customs) {
		this.customs = customs;
	}

	/**
	 * 获取关封号
	 */
	public String getCustomsEnvelopBillNo() {
		return customsEnvelopBillNo;
	}

	/**
	 * 设置关封号
	 */
	public void setCustomsEnvelopBillNo(String customsEnvelopBillNo) {
		this.customsEnvelopBillNo = customsEnvelopBillNo;
	}

	/**
	 * 获取是否进货标识
	 */
	public Boolean getIsImpExpGoods() {
		return isImpExpGoods;
	}

	/**
	 * 设置是否进货标识
	 */
	public void setIsImpExpGoods(Boolean isImpExpGoods) {
		this.isImpExpGoods = isImpExpGoods;
	}

	/**
	 * 获取购销合同编号
	 */
	public String getPurchaseAndSaleContractNo() {
		return purchaseAndSaleContractNo;
	}

	/**
	 * 设置购销合同编号
	 */
	public void setPurchaseAndSaleContractNo(String purchaseAndSaleContractNo) {
		this.purchaseAndSaleContractNo = purchaseAndSaleContractNo;
	}

	/**
	 * 获取帐册/手册号
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * 设置帐册/手册号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 获取项目类型
	 */
	public Integer getProjectType() {
		return projectType;
	}

	/**
	 * 设置项目类型
	 */
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	/**
	 * 获取转厂公司
	 */
	public Company getTransCompany() {
		return transCompany;
	}

	/**
	 * 设置转厂公司
	 */
	public void setTransCompany(Company transCompany) {
		this.transCompany = transCompany;
	}

	/**
	 * 获取条码号
	 */
	public String getCodeNo() {
		return codeNo;
	}

	/**
	 * 设置条码号
	 */
	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}
}