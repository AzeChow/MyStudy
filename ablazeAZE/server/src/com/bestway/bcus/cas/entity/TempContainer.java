/*
 * Created on 2004-9-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * @author ls 专门为明门做的(海关帐生成单据对应用的 集装箱)
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempContainer implements Serializable {
    private static final long serialVersionUID     = CommonUtils
                                                           .getSerialVersionUID();
    /**
     * 装箱单号
     */
    private String            containerNo          = null;
    /**
     * 料号
     */
    private String            ptNo                 = null;
    /**
     * 报关单号
     */
    private String            customsDeclarationNo = null;
    
    /**
     * 取得装箱单号
     * @return containerNo 装箱单号.
     */
    public String getContainerNo() {
        return containerNo;
    }
    /**
     * 设置装箱单号
     * @param containerNo  装箱单号
     */
    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }
    /**
     * 取得报关单号
     * @return customsDeclarationNo  报关单号.
     */
    public String getCustomsDeclarationNo() {
        return customsDeclarationNo;
    }
    /**
     * 设置报关单号
     * @param customsDeclarationNo  报关单号
     */
    public void setCustomsDeclarationNo(String customsDeclarationNo) {
        this.customsDeclarationNo = customsDeclarationNo;
    }
    /**
     * 取得料号
     * @return ptNo 料号.
     */
    public String getPtNo() {
        return ptNo;
    }
    /**
     * 设置料号
     * @param ptNo  料号
     */
    public void setPtNo(String ptNo) {
        this.ptNo = ptNo;
    }
    
    
}