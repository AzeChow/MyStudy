/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.parameterset.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 制单号控制对象
 */
public class LotControl extends BaseScmEntity {
    private static final long serialVersionUID               = CommonUtils
                                                                   .getSerialVersionUID();
    /**
     * 车间入库单据录入时需要制造令
     */ 
    private Boolean           isWorkshopImportNeedLot        = false;
    /**
     * 车间返回单据录入时需要制造令
     */
    private Boolean           isWorkshopBackNeedLot          = false;
    /**
     * 直接出口单据录入时需要制造令
     */
    private Boolean           isDirectExportNeedLot          = false;
    /**
     * 结转出口单据录入时需要制造令
     */
    private Boolean           isTransferFactoryExportNeedLot = false;

    /**
     * 取得直接出口单据录入时是否需要制造令标志
     * @return  isDirectExportNeedLot  直接出口单据录入时是否需要制造令
     */
    public Boolean getIsDirectExportNeedLot() {
        return isDirectExportNeedLot;
    }

    /**
     * 取得结转出口单据录入时是否需要制造令标志
     * @return  isTransferFactoryExportNeedLot 结转出口单据录入时是否需要制造令
     */
    public Boolean getIsTransferFactoryExportNeedLot() {
        return isTransferFactoryExportNeedLot;
    }

    /**
     * 取得车间返回单据录入时是否需要制造令标志
     * @return  isWorkshopBackNeedLot  车间返回单据录入时是否需要制造令标志
     */
    public Boolean getIsWorkshopBackNeedLot() {
        return isWorkshopBackNeedLot;
    }

    /**
     * 取得车间入库单据录入时是否需要制造令
     * @return isWorkshopImportNeedLot 车间入库单据录入时是否需要制造令
     */
    public Boolean getIsWorkshopImportNeedLot() {
        return isWorkshopImportNeedLot;
    }

    /**
     * 设置直接出口单据录入时是否需要制造令
     * @param  isDirectExportNeedLot 直接出口单据录入时需要制造令
     */
    public void setIsDirectExportNeedLot(Boolean isDirectExportNeedLot) {
        this.isDirectExportNeedLot = isDirectExportNeedLot;
    }

    /**
     * 设置结转出口单据录入时是否需要制造令
     * @param  isTransferFactoryExportNeedLot  结转出口单据录入时需要制造令
     */
    public void setIsTransferFactoryExportNeedLot(
            Boolean isTransferFactoryExportNeedLot) {
        this.isTransferFactoryExportNeedLot = isTransferFactoryExportNeedLot;
    }

    /**
     * 设置车间返回单据录入时是否需要制造令
     * @param  isWorkshopBackNeedLot 车间返回单据录入时是否需要制造令
     */
    public void setIsWorkshopBackNeedLot(Boolean isWorkshopBackNeedLot) {
        this.isWorkshopBackNeedLot = isWorkshopBackNeedLot;
    }

    /**
     * 设置车间入库单据录入时是否需要制造令
     * @param  isWorkshopImportNeedLot  车间入库单据录入时是否需要制造令
     */
    public void setIsWorkshopImportNeedLot(Boolean isWorkshopImportNeedLot) {
        this.isWorkshopImportNeedLot = isWorkshopImportNeedLot;
    }
}
