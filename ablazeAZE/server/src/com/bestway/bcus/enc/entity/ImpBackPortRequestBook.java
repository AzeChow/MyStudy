/*
 * Created on 2004-12-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 退港申请书 退厂返工
 * @author ls  
 * table="impbackportrequestbook"
 */
public class ImpBackPortRequestBook extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * 进出口申请单
     */
    private ImpExpRequestBill impExpRequestBill = null;
    /**
     * 合同标志
     */
    private String            contractFlag      = null;
    /**
     * 合同号
     */
    private String            contractNo        = null;
    /**
     * 退厂原因
     */
    private String            backFactoryExcuse = null;
    /**
     * 产品名称
     */
    private String            productName       = null;
    /**
     * 年
     */
    private String            year              = null;
    /**
     * 月
     */
    private String            month             = null;
    /**
     * 日
     */
    private String            day               = null;
    /**
     * 取得日
     * @return 日
     */
    public String getDay() {
        return day;
    }
    /**
     * 取得月
     * @return 月
     */
    public String getMonth() {
        return month;
    }
    /**
     * 取得年
     * @return 年
     */
    public String getYear() {
        return year;
    }
    /**
     * 设置日
     * @param day 日
     */
    public void setDay(String day) {
        this.day = day;
    }
    /**
     * 设置月
     * @param month 月
     */
    public void setMonth(String month) {
        this.month = month;
    }
    /**
     * 设置年
     * @param year 年
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 获得退厂原因
     * @return 退厂原因
     */
    public String getBackFactoryExcuse() {
        return backFactoryExcuse;
    }

    /**
     * 获得退厂标志
     * @return 退厂标志
     */
    public String getContractFlag() {
        return contractFlag;
    }

    /**
     * 获得合同号
     * @return 合同号
     */
    public String getContractNo() {
        return contractNo;
    }

  

    /**
     * 获得进出口申请单据
     * @return 进出口申请单据
     */
    public ImpExpRequestBill getImpExpRequestBill() {
        return impExpRequestBill;
    }

    /**
     * 填写退厂原因
     * @param backFactoryExcuse 退厂原因
     */
    public void setBackFactoryExcuse(String backFactoryExcuse) {
        this.backFactoryExcuse = backFactoryExcuse;
    }

    /**
     * 填写退厂标志
     * @param contractFlag 退厂标志
     */
    public void setContractFlag(String contractFlag) {
        this.contractFlag = contractFlag;
    }

    /**
     * 设置合同号
     * @param contractNo 合同号
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

   

    /**
     * 设置进出口申请单据
     * @param impExpRequestBill 进出口申请单据
     */
    public void setImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
        this.impExpRequestBill = impExpRequestBill;
    }

    /**
     * 获得产品名称
     * @return 产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 填写产品名称
     * @param productName 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
}