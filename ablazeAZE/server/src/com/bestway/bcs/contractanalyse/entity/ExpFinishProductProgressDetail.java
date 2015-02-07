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
 * 存放报关分析－－成品执行情况分析－－出口成品执行进度明细资料
 */
public class ExpFinishProductProgressDetail implements Serializable {

	/**
	 * 报关日期
	 */
    private Date   applyToCustomDate     = null;
	
	/**
	 * 报关单号
	 */
    private String applyToCustomBillNo   = null; 
	
	/**
	 * 运输工具及装箱单号
	 */
    private String conveyance            = null;  
	
	/**
	 * 成品出口数量
	 */
    private Double directExportAmount    = null; 
	
	/**
	 * 转厂出口数量
	 */
    private Double transferFactoryExport = null; 
	
	/**
	 * 退厂返工数量
	 */
    private Double backFactoryRework     = null; 
	
	/**
	 * 返工复出数量
	 */
    private Double reworkExport          = null;
	
	/**
	 * 客户名称
	 */
    private String customer              = null;

    

    /**
     * 获取退厂返工数量
     * 
     * @return backFactoryRework 退厂返工数量
     */
    public Double getBackFactoryRework() {
        return backFactoryRework;
    }
    
    /**
     * 获取成品出口数量
     * 
     * @return directExportAmount 成品出口数量
     */
    public Double getDirectExportAmount() {
        return directExportAmount;
    }
    
    /**
     * 获取返工复出数量
     * 
     * @return reworkExport 返工复出数量
     */
    public Double getReworkExport() {
        return reworkExport;
    }
    
    /**
     * 获取转厂出口数量
     * 
     * @return transferFactoryExport 转厂出口数量
     */
    public Double getTransferFactoryExport() {
        return transferFactoryExport;
    }
    
    /**
     * 设置退厂返工数量
     * 
     * @param backFactoryRework 退厂返工数量
     */
    public void setBackFactoryRework(Double backFactoryRework) {
        this.backFactoryRework = backFactoryRework;
    }
    
    /**
     * 设置成品出口数量
     * 
     * @param directExportAmount 成品出口数量
     */
    public void setDirectExportAmount(Double directExportAmount) {
        this.directExportAmount = directExportAmount;
    }
    
    /**
     * 设置返工复出数量
     * 
     * @param reworkExport 返工复出数量
     */
    public void setReworkExport(Double reworkExport) {
        this.reworkExport = reworkExport;
    }
    
    
    /**
     * 设置转厂出口数量
     * 
     * @param transferFactoryExport 转厂出口数量
     */
    public void setTransferFactoryExport(Double transferFactoryExport) {
        this.transferFactoryExport = transferFactoryExport;
    }
    
    /**
     * 获取报关单号
     * 
     * @return  applyToCustomBillNo 报关单号
     */
    public String getApplyToCustomBillNo() {
        return applyToCustomBillNo;
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
}
