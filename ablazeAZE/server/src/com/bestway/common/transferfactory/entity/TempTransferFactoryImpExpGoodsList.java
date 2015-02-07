/*
 * Created on 2004-10-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.transferfactory.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TempTransferFactoryImpExpGoodsList implements Serializable{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	private	String billDate=null; //单据生效日期
    private String billType=null;//单据类型
    private String billCode=null;//单据号码
    private String emsNo=null;//帐册序号
    private String materielCode=null;//物料号码
    private String materielName=null;//物料名称
    private Double transferQuantity=null;//数量
    private String unit=null;//单位
    private String companyName=null;//公司名称
   
    
    //新增 BY SXK
    /**
	 * 关封号
	 */
	private String envelopNo = null; 
	/**
	 * 客户供应商
	 */
	private String scmCoc = null;

	/**
	 * 报关单号
	 */
	private String customsDeclarationCode=null;
	
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}
	public String getEnvelopNo() {
		return envelopNo;
	}
	public void setEnvelopNo(String envelopNo) {
		this.envelopNo = envelopNo;
	}
	public String getScmCoc() {
		return scmCoc;
	}
	public void setScmCoc(String scmCoc) {
		this.scmCoc = scmCoc;
	}
	
    
    /**
     * @return Returns the unit.
     */
    public String getUnit() {
        return unit;
    }
    /**
     * @param unit The unit to set.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
    /**
     * @return Returns the billCode.
     */
    public String getBillCode() {
        return billCode;
    }
    /**
     * @param billCode The billCode to set.
     */
    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }
    /**
     * @return Returns the billDate.
     */
    public String getBillDate() {
        return billDate;
    }
    /**
     * @param billDate The billDate to set.
     */
    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
    /**
     * @return Returns the billType.
     */
    public String getBillType() {
        return billType;
    }
    /**
     * @param billType The billType to set.
     */
    public void setBillType(String billType) {
        this.billType = billType;
    }
    /**
     * @return Returns the companyName.
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * @param companyName The companyName to set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    /**
     * @return Returns the emsNo.
     */
    public String getEmsNo() {
        return emsNo;
    }
    /**
     * @param emsNo The emsNo to set.
     */
    public void setEmsNo(String emsNo) {
        this.emsNo = emsNo;
    }
    /**
     * @return Returns the materielCode.
     */
    public String getMaterielCode() {
        return materielCode;
    }
    /**
     * @param materielCode The materielCode to set.
     */
    public void setMaterielCode(String materielCode) {
        this.materielCode = materielCode;
    }
    /**
     * @return Returns the materielName.
     */
    public String getMaterielName() {
        return materielName;
    }
    /**
     * @param materielName The materielName to set.
     */
    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }
    /**
     * @return Returns the transferQuantity.
     */
    public Double getTransferQuantity() {
        return transferQuantity;
    }
    /**
     * @param transferQuantity The transferQuantity to set.
     */
    public void setTransferQuantity(Double transferQuantity) {
        this.transferQuantity = transferQuantity;
    }
}
