package com.bestway.bcus.cas.authority;

import com.bestway.common.Request;

/**
 * 海关帐统计报表接口
 * @author ?
 *
 */
public interface CasCustomReportAction {
    // 原材料进出仓帐 1
    // 成品进出仓帐 2
    // 设备进出仓帐 3
    // 边角料进出仓帐 4
    // 残次品进出仓帐 5
    // 结转明细帐 6
    // 委托发外加工帐 7

    // 加工贸易原材料来源与使用情况表 8
    // 加工贸易产品流向情况表 9
    // 加工贸易生产设备使用情况表 10
    // 料件，成品，设备库存情况统计表 11
    
    
    
    /**
     * 原材料进出仓帐
     */
    void checkAuthorityMaterielInOutByBrowse(Request request);

    /**
     * 成品进出仓帐
     * @param request
     */
    void checkAuthorityProductInOutByBrowse(Request request);

    /**
     * 设备进出仓帐
     * @param request
     */
    void checkAuthorityFixingInOutByBrowse(Request request);

    /**
     * 边角料进出仓帐
     * @param request
     */
    void checkAuthorityLeftoverMaterialInOutByBrowse(Request request);

    /**
     * 残次品进出仓帐
     * @param request
     */
    void checkAuthorityBadProductInOutByBrowse(Request request);

    /**
     * 结转明细帐
     * @param request
     */
    void checkAuthorityTransferFactoryInOutByBrowse(Request request);

    
    /**
     * 委托发外加工帐
     * @param request
     */
    void checkAuthorityOutSourceInOutByBrowse(Request request);

    
    /**
     * 加工贸易原材料来源与使用情况表
     * @param request
     */
    void checkAuthorityCustomMaterielByBrowse(Request request);

    /**
     * 加工贸易产品流向情况表
     * @param request
     */
    void checkAuthorityCustomProductByBrowse(Request request);

    /**
     * 加工贸易生产设备使用情况表
     * @param request
     */
    void checkAuthorityCustomFixingByBrowse(Request request);

    
    /**
     * 料件，成品，设备库存情况统计表 
     * @param request
     */
    void checkAuthorityCustomSourceByBrowse(Request request);
}
