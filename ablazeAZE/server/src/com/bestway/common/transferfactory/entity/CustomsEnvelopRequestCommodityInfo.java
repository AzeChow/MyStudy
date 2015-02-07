package com.bestway.common.transferfactory.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 关封申请单明细
 */
public class CustomsEnvelopRequestCommodityInfo extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 物料对象
	 */
	private Materiel                  materiel                  = null; 
	/**
	 * 关封申请单
	 */
    private CustomsEnvelopRequestBill customsEnvelopRequestBill = null; 
    /**
     * 申请数量
     */
    private Double                    requestQuantity           = null; 
    /**
     * 单价
     */
    private Double                    unitPrice                 = null; 
    /**
     * 备注
     */
    private String                    memo                      = null; 
    /**
     * 是否备案
     */
    private Boolean                   isPutOnRecord             = false; 
    /**
     * 是否已转关封
     */
    private Boolean                   isTranCustomsEnvelop      = false; 

    /**
     * @return Returns the isPutOnRecord.
     */
    public Boolean getIsPutOnRecord() {
        return isPutOnRecord;
    }

    /**
     * @return Returns the isTranCustomsEnvelop.
     */
    public Boolean getIsTranCustomsEnvelop() {
        return isTranCustomsEnvelop;
    }

    /**
     * @param isPutOnRecord
     *            The isPutOnRecord to set.
     */
    public void setIsPutOnRecord(Boolean isPutOnRecord) {
        this.isPutOnRecord = isPutOnRecord;
    }

    /**
     * @param isTranCustomsEnvelop
     *            The isTranCustomsEnvelop to set.
     */
    public void setIsTranCustomsEnvelop(Boolean isTranCustomsEnvelop) {
        this.isTranCustomsEnvelop = isTranCustomsEnvelop;
    }

    /**
     * @return Returns the customsEnvelopRequestBill.
     */
    public CustomsEnvelopRequestBill getCustomsEnvelopRequestBill() {
        return customsEnvelopRequestBill;
    }

    /**
     * @param customsEnvelopRequestBill
     *            The customsEnvelopRequestBill to set.
     */
    public void setCustomsEnvelopRequestBill(
            CustomsEnvelopRequestBill customsEnvelopRequestBill) {
        this.customsEnvelopRequestBill = customsEnvelopRequestBill;
    }

    /**
     * @return Returns the materiel.
     */
    public Materiel getMateriel() {
        return materiel;
    }

    /**
     * @param materiel
     *            The materiel to set.
     */
    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
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
     * @return Returns the requestQuantity.
     */
    public Double getRequestQuantity() {
        return requestQuantity;
    }

    /**
     * @param requestQuantity
     *            The requestQuantity to set.
     */
    public void setRequestQuantity(Double requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    /**
     * @return Returns the requestWeight.
     */
    public Double getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param requestWeight
     *            The requestWeight to set.
     */
    public void setUnitPrice(Double requestWeight) {
        this.unitPrice = requestWeight;
    }
   
}