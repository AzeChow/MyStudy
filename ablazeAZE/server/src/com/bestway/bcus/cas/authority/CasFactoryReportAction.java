package com.bestway.bcus.cas.authority;

import com.bestway.common.Request;

/**
 * 工厂资料查询统计报表接口 贺巍于2009年7月7号添加注释
 * @author ？ 
 *
 */
public interface CasFactoryReportAction {
    //
    // 料件
    //
	/**
	 * 浏览料件出入库明细总表 权限
	 */
    void checkAuthorityMaterielSumReportByBrowse(Request request);

    /**
     * 浏览料件仓库库存情况总表 权限
     * @param request
     */
    void checkAuthorityMaterielStorageSumReportByBrowse(Request request);

    /**
     * 浏览料件月报表 权限
     * @param request
     */
    void checkAuthorityMaterielMonthReportByBrowse(Request request);

    /**
     * 浏览成品出入库明细总表 权限
     * @param request
     */
    void checkAuthorityProductSumReportByBrowse(Request request);

    /**
     * 浏览成品仓库库存情况总表 权限
     * @param request
     */
    void checkAuthorityProductStorageSumReportByBrowse(Request request);

    /**
     * 浏览成品月报表 权限 
     * @param request
     */
    void checkAuthorityProductMonthReportByBrowse(Request request);

    /**
     * 浏览边角料出入库明细帐总表 权限
     * @param request
     */
    void checkAuthorityLeftoverMaterialSumReportByBrowse(Request request);

    /**
     * 浏览边角料库存情况统计总表 权限
     * @param request
     */
    void checkAuthorityLeftoverMaterialStorageSumByBrowse(Request request);

    /**
     * 浏览残次品出入库明细帐总表 权限
     * @param request
     */
    void checkAuthorityBadProductSumByBrowse(Request request);

    /**
     * 浏览残次品库存情况统计总表 权限
     * @param request
     */
    void checkAuthorityBadProductStorageByBrowse(Request request);

    /**
     * 浏览半成品出入库明细帐 权限
     * @param request
     */
    void checkAuthorityHalfProductDetailByBrowse(Request request);

    /**
     * 浏览半成品库存情况统计表 权限
     * @param request
     */
    void checkAuthorityHalfProductStoreByBrowse(Request request);

    /**
     * 浏览半成品月报表 权限
     * @param request
     */
    void checkAuthorityHalfProductMonthReportByBrowse(Request request);

    /**
     * 浏览设备出入库明细帐 权限
     * @param request
     */
    void checkAuthorityFixingDetailByBrowse(Request request);

    /**
     * 浏览设备库存情况统计表 权限
     * @param request
     */
    void checkAuthorityFixingStoreByBrowse(Request request);

    /**
     * 浏览设备统计查询报表 权限
     * @param request
     */
    void checkAuthorityFixingQueryByBrowse(Request request);
}
