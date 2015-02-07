package com.bestway.bcus.cas.authority;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
//海关资料统计报表
@AuthorityClassAnnotation(caption = "海关帐", index = 14)
public class CasCustomReportActionImpl extends BaseActionImpl implements
        CasCustomReportAction {

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

           
    @AuthorityFunctionAnnotation(caption = "海关资料统计报表--成品进出仓帐", index = 3.1)
    public void checkAuthorityProductInOutByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "海关资料统计报表--原材料进出仓帐", index = 3.2)
    public void checkAuthorityMaterielInOutByBrowse(Request request) {
    }

    
    @AuthorityFunctionAnnotation(caption = "海关资料统计报表--设备进出仓帐", index = 3.3)
    public void checkAuthorityFixingInOutByBrowse(Request request) {
    }

   
    @AuthorityFunctionAnnotation(caption = "海关资料统计报表--边角料进出仓帐", index = 3.4)
    public void checkAuthorityLeftoverMaterialInOutByBrowse(Request request) {
    }

    
    @AuthorityFunctionAnnotation(caption = "海关资料统计报表--残次品进出仓帐", index = 3.5)
    public void checkAuthorityBadProductInOutByBrowse(Request request) {
    }

    
    @AuthorityFunctionAnnotation(caption = "海关资料统计报表--结转明细帐", index = 3.6)
    public void checkAuthorityTransferFactoryInOutByBrowse(Request request) {
    }

    
    @AuthorityFunctionAnnotation(caption = "海关资料统计报表--委托发外加工帐", index = 3.7)
    public void checkAuthorityOutSourceInOutByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "海关资料统计报表--加工贸易原材料来源与使用情况表", index = 3.8)
    public void checkAuthorityCustomMaterielByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "海关资料统计报表--加工贸易产品流向情况表", index = 3.9)
    public void checkAuthorityCustomProductByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "海关资料统计报表--加工贸易生产设备使用情况表", index = 3.91)
    public void checkAuthorityCustomFixingByBrowse(Request request) {
    }

    @AuthorityFunctionAnnotation(caption = "海关资料统计报表--料件成品设备库存情况统计表", index = 3.92)
    public void checkAuthorityCustomSourceByBrowse(Request request) {
    }

}
