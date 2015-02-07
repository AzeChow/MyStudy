/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.specificontrol.entity;

import java.io.Serializable;
import java.util.List;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.common.CommonUtils;

/**
 * @author Administrator 生产单据对应的中间信息 
 *         generated type comment go to Window - Preferences - Java - Code Style -
 *         Code Templates
 */
/**
 * @author wuzepei
 *
 */
public class TempResult implements Serializable {
    private static final long                           serialVersionUID          = CommonUtils
                                                                                          .getSerialVersionUID();
  
   /**
    * 临时单据明细
    */
    private List<BillDetail>                                  tempBillDetail1                = null;
   /**
    * 报关单商品信息与海关帐单据的对应
    */
    private List<CustomsDeclarationCommInfoBillCorresponding> c                         = null;

    
    /**
     * 取得报关单商品信息与海关帐单据的对应的内容
     * @return c  报关单商品信息与海关帐单据的对应.
     */
    public List<CustomsDeclarationCommInfoBillCorresponding> getC() {
        return c;
    }
    /**
     * 设置报关单商品信息与海关帐单据的对应的内容
     * @param c 报关单商品信息与海关帐单据的对应.
     */
    public void setC(List<CustomsDeclarationCommInfoBillCorresponding> c) {
        this.c = c;
    }
    /**
     * 取得临时单据明细的内容
     * @return tempBillDetail1 临时单据明细.
     */
    public List<BillDetail> getBillDetail() {
        return tempBillDetail1;
    }
    /**
     * 设置临时单据明细的内容
     * @param tempBillDetail1 临时单据明细.
     */
    public void setBillDetail(List<BillDetail> tempBillDetail1) {
        this.tempBillDetail1 = tempBillDetail1;
    }
}
