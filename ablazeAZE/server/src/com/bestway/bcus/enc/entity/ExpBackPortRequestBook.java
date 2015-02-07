/*
 * Created on 2004-12-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 与指定的进出口申请单id匹配的出口类型的退港申请书
 * @author ls
 * table="expbackportrequestbook"
 */
public class ExpBackPortRequestBook extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * 进出口申请单
     */
    private ImpExpRequestBill impExpRequestBill   = null;
    /**
     * 香港商号
     */
    private String            hongKongCommodityNo = null;
    /**
     * 合同号
     */
    private String            contractNo          = null;
    /**
     * 运输工具
     */
    private String            conveyance          = null;
    /**
     * 申请日期
     */
    private Date              requestDate         = null;
    /**
     * 申请理由
     */
    private String            requestExcuse       = null;
    /**
     * 取得合同号
     * @return 合同号
     */
    public String getContractNo() {
        return contractNo;
    }
    /**
     * 取得运输工具
     * @return 运输工具
     */
    public String getConveyance() {
        return conveyance;
    }
    /**
     * 取得香港商号
     * @return 香港商号
     */
    public String getHongKongCommodityNo() {
        return hongKongCommodityNo;
    }
    /**
     * 取得进出口申请单
     * @return 进出口申请单
     */
    public ImpExpRequestBill getImpExpRequestBill() {
        return impExpRequestBill;
    }
    /**
     * 取得申请日期
     * @return 申请日期
     */
    public Date getRequestDate() {
        return requestDate;
    }
    /**
     * 取得申请理由
     * @return 申请理由
     */
    public String getRequestExcuse() {
        return requestExcuse;
    }
    /**
     * 设置合同号
     * @param contractNo 合同号
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }
    /**
     * 设置运输工具
     * @param conveyance  运输工具
     */
    public void setConveyance(String conveyance) {
        this.conveyance = conveyance;
    }
    /**
     * 设置香港商号
     * @param hongKongCommodityNo 香港商号
     */
    public void setHongKongCommodityNo(String hongKongCommodityNo) {
        this.hongKongCommodityNo = hongKongCommodityNo;
    }
    /**
     * 设计进出口申请单内容
     * @param impExpRequestBill 进出口申请单
     */
    public void setImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
        this.impExpRequestBill = impExpRequestBill;
    }
    /**
     * 设置申请日期
     * @param requestDate 申请日期
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
    /**
     * 设置申请理由
     * @param requestExcuse 申请理由
     */
    public void setRequestExcuse(String requestExcuse) {
        this.requestExcuse = requestExcuse;
    }
}