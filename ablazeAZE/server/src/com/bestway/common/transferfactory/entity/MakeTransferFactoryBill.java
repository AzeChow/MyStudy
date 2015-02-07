/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.transferfactory.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 获得转厂的单据商品信息
 * @author 
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MakeTransferFactoryBill extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 海关帐工厂单据id
	 */
	private String billMasterId = null; 
	/**
	 * 结转单据Id
	 */
    private String transFactBillId = null; 

    /**
     * 海关帐工厂商品信息id
     * 
     * @return Returns the cfcId.
     */
    public String getBillMasterId() {
        return billMasterId;
    }

    /**
     * 结转商品信息Id
     * 
     * @return Returns the tfcId.
     */
    public String getTransFactBillId() {
        return transFactBillId;
    }

    /**
     * 海关帐工厂商品信息id
     * 
     * @param cfcId
     *            The cfcId to set.
     */
    public void setBillMasterId(String cfcId) {
        this.billMasterId = cfcId;
    }

    /**
     * 结转商品信息Id
     * 
     * @param tfcId
     *            The tfcId to set.
     */
    public void setTransFactBillId(String tfcId) {
        this.transFactBillId = tfcId;
    }
}