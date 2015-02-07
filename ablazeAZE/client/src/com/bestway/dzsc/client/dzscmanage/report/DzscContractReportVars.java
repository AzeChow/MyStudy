/*
 * Created on 2005-5-27
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage.report;

import java.util.List;

import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DzscContractReportVars {

    private static DzscContractReportVars c = null;

    private DzscContractReportVars() {

    }

    public static DzscContractReportVars getInstance() {
        if (c == null) {
            c = new DzscContractReportVars();
        }
        return c;
    }

    /**
     * 获得成品当前页的金额总和
     * 
     * @param groupPage
     * @param pageRecordCount
     * @return
     */

    public Double getTotalPricesByFinishedProductCurrentPage(List list,
            int groupPage, int pageRecordCount) {
        if (list == null) {
            return new Double(0);
        }
        DzscEmsExgWj contractExg = null;
        double prices = 0;

        for (int i = 0; i < list.size(); i++) {
            if (i >= ((groupPage - 1) * pageRecordCount)
                    && i < (groupPage * pageRecordCount)) {
                contractExg = (DzscEmsExgWj) list.get(i);
//                if (contractExg.getMoney() != null) {
//                    prices += contractExg.getMoney().doubleValue();
//                }
            }
        }
        return new Double(prices);
    }

    /**
     * 获得料件当前页的金额总和
     * 
     * @param groupPage
     * @param pageRecordCount
     * @return
     */

    public Double getTotalPricesByMaterielCurrentPage(List list, int groupPage,
            int pageRecordCount) {
        if (list == null) {
            return new Double(0);
        }
        DzscEmsImgWj contractImg = null;
        double prices = 0;
        for (int i = 0; i < list.size(); i++) {
            if (i >= ((groupPage - 1) * pageRecordCount)
                    && i < (groupPage * pageRecordCount)) {
                contractImg = (DzscEmsImgWj) list.get(i);
//                if (contractImg.getMoney() != null) {
//                    prices += contractImg.getMoney().doubleValue();
//                }
            }
        }
        return new Double(prices);

    }

}
