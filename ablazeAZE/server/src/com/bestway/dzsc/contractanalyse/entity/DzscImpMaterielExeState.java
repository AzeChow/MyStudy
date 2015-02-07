/*
 * Created on 2005-6-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractanalyse.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 存放报关分析－－料件执行情况分析－－料件执行情况
 * 
 * @author ls
 */
public class DzscImpMaterielExeState implements Serializable {
	
	/**
	 * 报关日期
	 */
    private Date    applyToCustomDate   = null; 
	
	/**
	 * 报关单号
	 */
    private String  applyToCustomBillNo = null;
	
	/**
	 * 手册编号
	 */
    private String  emsNo               = null; 
	
	/**
	 * 进出口类型
	 */
    private Integer impExpType          = null; 
	
	/**
	 * 说明
	 */
    private String  explain             = null;  
	
	/**
	 * 入(进口数量)
	 */
    private Double  impAmount           = null;  
	
	/**
	 * 出(出口数量)
	 */
    private Double  expAmount           = null; 

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
     * 获取手册编号
     * 
     * @return emsNo 手册编号
     */
    public String getEmsNo() {
        return emsNo;
    }

    /**
     * 获取出(出口数量)
     * 
     * @return expAmount 出(出口数量)
     */
    public Double getExpAmount() {
        return expAmount;
    }

    /**
     * 获取说明
     * 
     * @return explain 说明
     */
    public String getExplain() {
        return explain;
    }

    /**
     * 获取入(进口数量)
     * 
     * @return impAmount 入(进口数量)
     */
    public Double getImpAmount() {
        return impAmount;
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
     * 设置手册编号
     * 
     * @param emsNo 手册编号
     */
    public void setEmsNo(String emsNo) {
        this.emsNo = emsNo;
    }

    
    /**
     * 设置出(出口数量)
     * 
     * @param expAmount 出(出口数量)
     */
    public void setExpAmount(Double expAmount) {
        this.expAmount = expAmount;
    }

    
    /**
     * 设置说明
     * 
     * @param explain 说明
     */
    public void setExplain(String explain) {
        this.explain = explain;
    }

    
    /**
     * 设置入(进口数量)
     * 
     * @param impAmount 入(进口数量)
     */
    public void setImpAmount(Double impAmount) {
        this.impAmount = impAmount;
    }

    /**
     * 获取进出口类型
     * 
     * @return impExpType 进出口类型
     */
    public Integer getImpExpType() {
        return impExpType;
    }
    
    /**
     * 设置进出口类型
     * 
     * @param impExpType 进出口类型
     */
    public void setImpExpType(Integer impExpType) {
        this.impExpType = impExpType;
    }
}
