package com.bestway.bcus.cas.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 边角料所对应的料件数据
 */
public class LMateriel extends BaseScmEntity {

    
    /**
     * 边角料
     */
    private LeftoverMateriel leftoverMateriel = null;
    /**
     * 料号
     */
    private String           ptNo             = null;
    /**
     * 名称
     */
    private String           name             = null;
    /**
     * 规格
     */
    private String           spec             = null;
    /**
     * 备注
     */
    private String           note             = null;

    /**
     * 取得名称
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 取得规格
     * @return spec 规格
     */
    public String getSpec() {
        return spec;
    }

    /**
     * 设置规格
     * @param spec 规格
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * 取得料号
     * @return ptNo 料号
     */
    public String getPtNo() {
        return ptNo;
    }

    /**
     * 设置料号
     * @param leftoverMaterielPtNo 料号
     */
    public void setPtNo(String leftoverMaterielPtNo) {
        this.ptNo = leftoverMaterielPtNo;
    }

    /**
     * 取得备注
     * @return note 备注
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置备注
     * @param note 备注
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 取得边角料
     * @return leftoverMateriel 边角料
     */
    public LeftoverMateriel getLeftoverMateriel() {
        return leftoverMateriel;
    }

    /**
     * 设置边角料
     * @param leftoverMateriel 边角料
     */
    public void setLeftoverMateriel(LeftoverMateriel leftoverMateriel) {
        this.leftoverMateriel = leftoverMateriel;
    }

   

}
