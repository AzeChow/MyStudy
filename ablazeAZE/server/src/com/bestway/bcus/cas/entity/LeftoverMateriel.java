package com.bestway.bcus.cas.entity;

import com.bestway.common.BaseScmEntity;
/**
 * 边角料
 * @author Administrator
 *
 */
public class LeftoverMateriel extends BaseScmEntity {

    // /////////////////////////////////////////////////////////////////////////
    // 为了增加统计的性能所以用边角料来关联 leftovermateriel --> materiel 是一对多的关系//
    // /////////////////////////////////////////////////////////////////////////
	/**
     * 边角料料号
     */
    private String ptNo = null;
    /**
     * 报关名称
     */
    private String name = null;
    /**
     * 规格
     */
    private String spec = null;
    /**
     * 备注
     */
    private String note = null;
    /**
     * 取得边角料报关名称
     * @return name 报关名称.
     */
    public String getName() {
        return name;
    }
    /**
     * 设置边角料报关名称
     * @param name  边角料报关名称
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 取得边角料规格
     * @return spec 边角料规格.
     */
    public String getSpec() {
        return spec;
    }
    /**
     * 设置边角料规格
     * @param spec  边角料规格
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }
    /**
     * 取得边角料料号
     * @return ptNo 边角料料号.
     */
    public String getPtNo() {
        return ptNo;
    }
    /**
     * 设置边角料料号
     * @param leftoverMaterielPtNo  边角料料号
     */
    public void setPtNo(String leftoverMaterielPtNo) {
        this.ptNo = leftoverMaterielPtNo;
    }   
    /**
     * 取得备注内容
     * @return note 备注.
     */
    public String getNote() {
        return note;
    }
    /**
     * 设置备注内容
     * @param note  备注
     */
    public void setNote(String note) {
        this.note = note;
    }

}
