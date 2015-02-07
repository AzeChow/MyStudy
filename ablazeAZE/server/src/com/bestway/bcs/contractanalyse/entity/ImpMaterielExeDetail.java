/*
 * Created on 2005-6-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractanalyse.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 存放报关分析－－料件执行情况分析－－料件执行明细资料
 */
public class ImpMaterielExeDetail implements Serializable {

	/**
	 * 报关日期
	 */
    private Date   applyToCustomDate        = null; 
	
	/**
	 * 报关单号
	 */
    private String applyToCustomBillNo      = null;
	
	/**
	 * 运输工具及装箱单号
	 */
    private String conveyance               = null;
	
	/**
	 * 料件进口数量
	 */
    private Double materielImportAmount     = null;
	
	/**
	 * 料件转厂数量
	 */
    private Double transferFactoryAmount    = null;
	
	/**
	 * 退料出口数量
	 */
    private Double backMaterielExportAmount = null;
	
	/**
	 * 客户名称
	 */
    private String customer                 = null;

    /**
     * 获取料件进口数量
     * 
     * @return materielImportAmount 料件进口数量
     */
    public Double getMaterielImportAmount() {
        return materielImportAmount;
    }

    /**
     * 获取料件转厂数量
     * 
     * @return transferFactoryAmount 料件转厂数量
     */
    public Double getTransferFactoryAmount() {
        return transferFactoryAmount;
    }

    /**
     * 设置料件进口数量
     * 
     * @param materielImportAmount 料件进口数量
     */
    public void setMaterielImportAmount(Double materielImportAmount) {
        this.materielImportAmount = materielImportAmount;
    }

    /**
     * 设置料件转厂数量
     * 
     * @param transferFactoryAmount 料件转厂数量
     */
    public void setTransferFactoryAmount(Double transferFactoryAmount) {
        this.transferFactoryAmount = transferFactoryAmount;
    }

    /**
     * 获取退料出口数量
     * 
     * @return backMaterielExportAmount 退料出口数量
     */
    public Double getBackMaterielExportAmount() {
        return backMaterielExportAmount;
    }

    /**
     * 获取报关单号
     * 
     * @return applyToCustomBillNo 报关单号
     */
    public String getApplyToCustomBillNo() {
        return applyToCustomBillNo;
    }

    /**
     * 获取客户名称
     * 
     * @return customer 客户名称
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * 设置客户名称
     * 
     * @param customer 客户名称
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     * 获取报关日期
     * 
     * @return applyToCustomDate 报关日期
     */
    public Date getApplyToCustomDate() {
        if(applyToCustomDate == null){
            return null;
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return java.sql.Date.valueOf(bartDateFormat.format(applyToCustomDate));
    }

    /**
     * 获取运输工具及装箱单号
     * 
     * @return conveyance 运输工具及装箱单号
     */
    public String getConveyance() {
        return conveyance;
    }

    /**
     * 设置退料出口数量
     * 
     * @param backMaterielExportAmount 退料出口数量
     */
    public void setBackMaterielExportAmount(Double backMaterielExportAmount) {
        this.backMaterielExportAmount = backMaterielExportAmount;
    }

    /**
     * 设置报关单号
     * 
     * @param applyToCustomBillNo 报关单号
     */
    public void setApplyToCustomBillNo(String applyToCustomBillNo) {
        this.applyToCustomBillNo = applyToCustomBillNo;
    }

    /**
     * 设置报关日期
     * 
     * @param applyToCustomDate 报关日期
     */
    public void setApplyToCustomDate(Date applyToCustomDate) {
        this.applyToCustomDate = applyToCustomDate;
    }

    /**
     * 设置运输工具及装箱单号
     * 
     * @param conveyance 运输工具及装箱单号
     */
    public void setConveyance(String conveyance) {
        this.conveyance = conveyance;
    }

}
