package com.bestway.bcus.cas.authority;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
//工厂资料统计报表
@AuthorityClassAnnotation(caption = "海关帐", index = 14)
public class CasFactoryReportActionImpl extends BaseActionImpl implements
        CasFactoryReportAction {

    //
    // 料件
    //
    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--料件出入库明细帐", index = 2.1)
    public void checkAuthorityMaterielSumReportByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--料件仓库库存情况表", index = 2.2)
    public void checkAuthorityMaterielStorageSumReportByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--料件月报表", index = 2.3)
    public void checkAuthorityMaterielMonthReportByBrowse(Request request) {
    }

    //
    // 成品
    //
    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--成品出入库明细帐", index = 2.4)
    public void checkAuthorityProductSumReportByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--成品仓库库存情况统计表", index = 2.5)
    public void checkAuthorityProductStorageSumReportByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--浏览成品月报表", index = 2.6)
    public void checkAuthorityProductMonthReportByBrowse(Request request) {
    }

    //
    // 边角料
    //
    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--边角料出入库明细帐总表", index = 2.7)
    public void checkAuthorityLeftoverMaterialSumReportByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--边角料库存情况统计总表", index = 2.8)
    public void checkAuthorityLeftoverMaterialStorageSumByBrowse(Request request) {
    }

    //
    // 残次品
    //
    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--残次品出入库明细帐总表", index = 2.9)
    public void checkAuthorityBadProductSumByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--残次品库存情况统计总表", index = 2.91)
    public void checkAuthorityBadProductStorageByBrowse(Request request) {
    }

    //
    // 半成品
    //
    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--半成品出入库明细帐", index = 2.92)
    public void checkAuthorityHalfProductDetailByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--半成品库存情况统计表", index = 2.93)
    public void checkAuthorityHalfProductStoreByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--半成品月报表", index = 2.94)
    public void checkAuthorityHalfProductMonthReportByBrowse(Request request) {
    }

    //
    // 设备
    //
    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--设备出入库明细帐", index = 2.95)
    public void checkAuthorityFixingDetailByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--设备库存情况统计表", index = 2.96)
    public void checkAuthorityFixingStoreByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "工厂资料统计报表--设备统计查询报表", index = 2.97)
    public void checkAuthorityFixingQueryByBrowse(Request request) {
    }

}
