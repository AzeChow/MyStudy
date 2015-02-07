
/*
 * Created on 2005-5-27
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fixtureonorder.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.fixtureonorder.entity.FixtureContractItems;

/**
 * @author fhz
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FxitureContractReportVars {

    private static FxitureContractReportVars c = null;

    private FxitureContractReportVars() {

    }

    public static FxitureContractReportVars getInstance() {
        if (c == null) {
            c = new FxitureContractReportVars();
        }
        return c;
    }

    
    /**
     * 获得设备当前页的金额总和
     * 
     * @param groupPage
     * @param pageRecordCount
     * @return
     */

    public Double getTotalPricesByItemsCurrentPage(List list, int groupPage,
            int pageRecordCount) {
        if (list == null) {
            return new Double(0);
        }
        String key = String.valueOf(groupPage) + pageRecordCount;

        FixtureContractItems contractItem = null;
        double prices = 0;
        for (int i = 0; i < list.size(); i++) {
            if (i >= ((groupPage - 1) * pageRecordCount)
                    && i < (groupPage * pageRecordCount)) {
                contractItem = (FixtureContractItems) list.get(i);
                if (contractItem.getTotalPrice() != null) {
                    prices += contractItem.getTotalPrice().doubleValue();
                }
            }
        }
        return new Double(prices);

    }

}
