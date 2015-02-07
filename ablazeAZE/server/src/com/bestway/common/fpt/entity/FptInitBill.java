/*
 * Created on 2004-10-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.fpt.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 进出货转厂期初单
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FptInitBill extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 单据号
	 */
	private String  fptInitBillCode = null; 
	/**
	 * 生效日期
	 */
    private Date    effectiveDate               = null;
    /**
     * 是否生效
     */
    private Boolean isEffective                 = false;
    /**
     * 商品项数
     */
    private Integer itemCount                   = null; 
    /**
     * 客户供应商
     */
    private ScmCoc  scmCoc                      = null; 
    /**
     * 录入日期
     */
    private Date    buildDate                   = null;
    /**
     * 录入单位号码
     */
    private String  buildCompanyCode            = null; 
    /**
     * 录入单位名称
     */
    private String  buildCompanyName            = null; 
    /**
     * 备注
     */
    private String  memo                        = null; 
//    private Integer impExpFlag                  = null; //进出货标志
    /**
     * 进出货标志
     */
    private Boolean isImpExpFlag=false;
	/**
	 * 关封号
	 */
	private String envelopNo = null; 
    /**
     * 录入人员对象
     */
    private AclUser aclUser                     = null; 

    /**
     * @return Returns the aclUser.
     */
    public AclUser getAclUser() {
        return aclUser;
    }
    /**
     * @param aclUser The aclUser to set.
     */
    public void setAclUser(AclUser aclUser) {
        this.aclUser = aclUser;
    }
//    /**
//     * @return Returns the impExpFlag.
//     */
//    public Integer getImpExpFlag() {
//        return impExpFlag;
//    }
//
//    /**
//     * @param impExpFlag
//     *            The impExpFlag to set.
//     */
//    public void setImpExpFlag(Integer impExpFlag) {
//        this.impExpFlag = impExpFlag;
//    }

    //    private Set
    // transgerFactoryInitBillCommodityInfoes
    // = null; //一对多的集合

    /**
     * @return Returns the bCompanyCode.
     */
    public String getBuildCompanyCode() {
        return buildCompanyCode;
    }

    /**
     * @param companyCode
     *            The bCompanyCode to set.
     */
    public void setBuildCompanyCode(String companyCode) {
        buildCompanyCode = companyCode;
    }

    /**
     * @return Returns the bCompanyName.
     */
    public String getBuildCompanyName() {
        return buildCompanyName;
    }

    /**
     * @param companyName
     *            The bCompanyName to set.
     */
    public void setBuildCompanyName(String companyName) {
        buildCompanyName = companyName;
    }

    /**
     * @return Returns the bDate.
     */
    public Date getBuildDate() {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(buildDate!=null){
			return java.sql.Date.valueOf(dateFormat.format(buildDate));
		}
        return buildDate;
    }

    /**
     * @param date
     *            The bDate to set.
     */
    public void setBuildDate(Date date) {
        buildDate = date;
    }

    /**
     * @return Returns the commodityNum.
     */
    public Integer getItemCount() {
        return itemCount;
    }

    /**
     * @param commodityNum
     *            The commodityNum to set.
     */
    public void setItemCount(Integer commodityNum) {
        this.itemCount = commodityNum;
    }

    /**
     * @return Returns the effectiveDate.
     */
    public Date getEffectiveDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(effectiveDate!=null){
			return java.sql.Date.valueOf(dateFormat.format(effectiveDate));
		}
        return effectiveDate;
    }

    /**
     * @param effectiveDate
     *            The effectiveDate to set.
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @return Returns the isEffective.
     */
    public Boolean getIsEffective() {
        return isEffective;
    }

    /**
     * @param isEffective
     *            The isEffective to set.
     */
    public void setIsEffective(Boolean isEffective) {
        this.isEffective = isEffective;
    }

    /**
     * @return Returns the memo.
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     *            The memo to set.
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * @return Returns the scmCoc.
     */
    public ScmCoc getScmCoc() {
        return scmCoc;
    }

    /**
     * @param scmCoc
     *            The scmCoc to set.
     */
    public void setScmCoc(ScmCoc scmCoc) {
        this.scmCoc = scmCoc;
    }

    /**
     * @return Returns the transferFactoryInitBillCode.
     */
    public String getFptInitBillCode() {
        return fptInitBillCode;
    }

    /**
     * @param transferFactoryInitBillCode
     *            The transferFactoryInitBillCode to set.
     */
    public void setFptInitBillCode(
            String transferFactoryInitBillCode) {
        this.fptInitBillCode = transferFactoryInitBillCode;
    }
	public Boolean getIsImpExpFlag() {
		return isImpExpFlag;
	}
	public void setIsImpExpFlag(Boolean isImpExpFlag) {
		this.isImpExpFlag = isImpExpFlag;
	}
	public String getEnvelopNo() {
		return envelopNo;
	}
	public void setEnvelopNo(String envelopNo) {
		this.envelopNo = envelopNo;
	}

    //    /**
    //     * @return Returns the transgerFactoryInitBillCommodityInfoes.
    //     */
    //    public Set getTransgerFactoryInitBillCommodityInfoes() {
    //        return transgerFactoryInitBillCommodityInfoes;
    //    }
    //
    //    /**
    //     * @param transgerFactoryInitBillCommodityInfoes
    //     * The transgerFactoryInitBillCommodityInfoes to set.
    //     */
    //    public void setTransgerFactoryInitBillCommodityInfoes(
    //            Set transgerFactoryInitBillCommodityInfoes) {
    //        this.transgerFactoryInitBillCommodityInfoes =
    // transgerFactoryInitBillCommodityInfoes;
    //    }
}