package com.bestway.bcus.financial.action;

import java.util.List;
import java.util.Map;

import com.bestway.common.Request;
import com.bestway.customs.common.action.BaseEncAction;
/**
 * 账务成本分析 远程调用 接口
 * @author Administrator
 *
 */
public interface FinancialAction extends BaseEncAction {
	/**
	 * 找单据中心
	 * @param request
	 * @param financialType
	 * @return
	 */
	List findBillType(Request request, int financialType);
	/**
	 * 原材料进出仓帐 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	List findMaterialAccountList(Request request, Map conditionMap);
	/**
	 * 原材料进出仓帐 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
//	int findMaterialAccountCount(Request request, Map conditionMap);
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	void updateMaterialAccount(Request request, List list);
	/**
	 * 导入修改的数据
	 * @param request
	 * @param list
	 */
	void importMaterialAccount(Request request, List list);
	/**
	 * 料件加权平均价 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	List findMaterialAveragePriceList(Request request, Map conditionMap);
	/**
	 * 料件加权平均价 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	int findMaterialAveragePriceCount(Request request, Map conditionMap);
	
	/**
	 * 成品单耗调整表 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	List findProductConsumptionList(Request request, Map conditionMap);
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	void updateProductConsumption(Request request, List list);
	/**
	 * 成品单耗调整表 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
//	int findProductConsumptionCount(Request request, Map conditionMap);
	
	/**
	 * 每月产量与销量 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	List findMonthlyProductionAndSalesList(Request request, Map conditionMap);
	/**
	 * 每月产量与销量导入
	 * @param list
	 */
	void importMonthlyProductionAndSales(Request request, List list);
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	void updateMonthlyProductionAndSales(Request request, List list);
//	/**
//	 * 每月产量与销量 查找
//	 * @param request
//	 * @param conditionMap
//	 * @return
//	 */
//	int findMonthlyProductionAndSalesCount(Request request, Map conditionMap);
	
	/**
	 * 成本汇总表 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	List findCostSummaryReportList(Request request, Map conditionMap);
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	void updateCostSummaryReport(Request request, List list);
	/**
	 * 成本汇总表 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
//	int findCostSummaryReportCount(Request request, Map conditionMap);
	
	/**
	 * 成品加权平均价 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	List findProductWeightedAveragePriceList(Request request, Map conditionMap);
	/**
	 * 成品加权平均价 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
//	int findProductWeightedAveragePriceCount(Request request, Map conditionMap);
	
	/**
	 * 成品成本进出仓帐 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	List findFinishedProductCostList(Request request, Map conditionMap);
	/**
	 * 成品成本进出仓帐 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
//	int findFinishedProductCostCount(Request request, Map conditionMap);
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	void updateFinishedProductCost(Request request, List list);
	/**
	 * 导入修改的数据
	 * @param request
	 * @param list
	 */
	void importFinishedProductCost(Request request, List list);
	Map findUnit(Request request);
}
