package com.bestway.bcus.cas.authority;

import com.bestway.common.Request;

public interface CasFinanceReportAction {
    /** 原材料明细帐 */
    void check1ByBrower(Request request);

    /** 边角料明细帐 */
    void check2ByBrower(Request request);

    /** 产成品明细帐 */
    void check3ByBrower(Request request);

    /** 残次品明细帐 */
    void check4ByBrower(Request request);

    /** 销售收入统计表 */
    void check5ByBrower(Request request);

    /** 资产负债表 */
    void check6ByBrower(Request request);

    /** 存货统计表 */
    void check7ByBrower(Request request);

    /** 利润表 */
    void check8ByBrower(Request request);

    /** 盘点表 */
    void check9ByBrower(Request request);

    /** 现金流量表 */
    void check10ByBrower(Request request);

}
