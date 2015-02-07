package com.bestway.common.transferfactory.entity;

import java.util.Set;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 关封申请单
 */
public class CustomsEnvelopRequestBill extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 单据号
	 */
    private String    billNo                               = null; 
    /**
     * 客户供应商
     */
    private ScmCoc  scmCoc                               = null; 
    /**
     * 是否生产关封
     */
    private Boolean isMakeCustomsEnvelop                 = false; 
    /**
     * 是进货还是出货
     */
    private Boolean isImpExpGoods                        = false; 
    /**
     * 生效
     */
    private Boolean isAvailability                       = false; 
    /**
     * 采购/推销员
     */
    private String  buyerSalesman                        = null; 
    /**
     * 报关员
     */
    private String  applyToCustomserEmployee             = null; 
    /**
     * 核销员
     */
    private String  cancelAfterVerificationEmployee      = null;
    /**
     * 制表人
     */
    private String  lister                               = null; 
    /**
     * 送货人
     */
    private String  deliverGoodsAddress                  = null; 
    /**
     * 备注
     */
    private String  memo                                 = null;     
    

   
    /**
     * @return Returns the applyToCustomserEmployee.
     */
    public String getApplyToCustomserEmployee() {
        return applyToCustomserEmployee;
    }

    /**
     * @param applyToCustomserEmployee
     *            The applyToCustomserEmployee to set.
     */
    public void setApplyToCustomserEmployee(String applyToCustomserEmployee) {
        this.applyToCustomserEmployee = applyToCustomserEmployee;
    }

    /**
     * @return Returns the billNo.
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * @return Returns the isImpExpGoods.
     */
    public Boolean getIsImpExpGoods() {
        return isImpExpGoods;
    }

    /**
     * @param isImpExpGoods
     *            The isImpExpGoods to set.
     */
    public void setIsImpExpGoods(Boolean isImpExpGoods) {
        this.isImpExpGoods = isImpExpGoods;
    }

    /**
     * @return Returns the isValidata.
     */
    public Boolean getIsAvailability() {
        return isAvailability;
    }


    /**
     * @param isValidata
     *            The isValidata to set.
     */
    public void setIsAvailability(Boolean isValidata) {
        this.isAvailability = isValidata;
    }

    /**
     * @return Returns the isMakeCustomsEnvelop.
     */
    public Boolean getIsMakeCustomsEnvelop() {
        return isMakeCustomsEnvelop;
    }

    /**
     * @param isMakeCustomsEnvelop
     *            The isMakeCustomsEnvelop to set.
     */
    public void setIsMakeCustomsEnvelop(Boolean isMakeCustomsEnvelop) {
        this.isMakeCustomsEnvelop = isMakeCustomsEnvelop;
    }

   
    /**
     * @param billNo
     *            The billNo to set.
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * @return Returns the buyerSalesman.
     */
    public String getBuyerSalesman() {
        return buyerSalesman;
    }

    /**
     * @param buyerSalesman
     *            The buyerSalesman to set.
     */
    public void setBuyerSalesman(String buyerSalesman) {
        this.buyerSalesman = buyerSalesman;
    }

    /**
     * @return Returns the cancelAfterVerificationEmployee.
     */
    public String getCancelAfterVerificationEmployee() {
        return cancelAfterVerificationEmployee;
    }

    /**
     * @param cancelAfterVerificationEmployee
     *            The cancelAfterVerificationEmployee to set.
     */
    public void setCancelAfterVerificationEmployee(
            String cancelAfterVerificationEmployee) {
        this.cancelAfterVerificationEmployee = cancelAfterVerificationEmployee;
    }

    /**
     * @return Returns the deliverGoodsAddress.
     */
    public String getDeliverGoodsAddress() {
        return deliverGoodsAddress;
    }

    /**
     * @param deliverGoodsAddress
     *            The deliverGoodsAddress to set.
     */
    public void setDeliverGoodsAddress(String deliverGoodsAddress) {
        this.deliverGoodsAddress = deliverGoodsAddress;
    }

    /**
     * @return Returns the lister.
     */
    public String getLister() {
        return lister;
    }

    /**
     * @param lister
     *            The lister to set.
     */
    public void setLister(String lister) {
        this.lister = lister;
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
}