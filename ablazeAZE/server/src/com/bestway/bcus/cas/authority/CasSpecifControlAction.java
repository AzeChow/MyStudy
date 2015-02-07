package com.bestway.bcus.cas.authority;

import com.bestway.common.Request;

public interface CasSpecifControlAction {

    // 大批量删除或回卷或生效单据 1
    // 财务成批记帐 2
    // 修改单据单价 3
    // 单据对应 4
    // 手工批量对应 5
    // 报关单和单据对应查询 6
    // 单据对应--PK单对应 7
    // 生成单据的折算报关数量 8
    // 半成品委外管理 9

    /**
     * 大批量删除或回卷或生效单据 1
     * 
     */
    void check1ByBrower(Request request);

    /**
     * 财务成批记帐 2
     * 
     * @param request
     */
    void check2ByBrower(Request request);

    /**
     * 修改单据单价
     * 
     * @param request
     */
    void check3ByBrower(Request request);

    /**
     * 单据对应
     * 
     * @param request
     */
    void check4ByBrower(Request request);

    /**
     * 手工批量对应
     * 
     * @param request
     */
    void check5ByBrower(Request request);

    /**
     * 报关单和单据对应查询
     * 
     * @param request
     */
    void check6ByBrower(Request request);

    /**
     * 单据对应--PK单对应
     * 
     * @param request
     */
    void check7ByBrower(Request request);

    /**
     * 生成单据的折算报关数量
     * 
     * @param request
     */
    void check8ByBrower(Request request);

    /**
     * 半成品委外管理
     * 
     * @param request
     */
    void check9ByBrower(Request request);
}
