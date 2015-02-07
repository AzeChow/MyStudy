package com.bestway.bcus.financial.action;

import java.util.List;
import java.util.Map;

import com.bestway.bcus.financial.entity.MonthlyProductionAndSales;
import com.bestway.bcus.financial.logic.FinancialLogic;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.customs.common.action.BaseEncActionImpl;

@AuthorityClassAnnotation(caption = "账务成本分析", index = 15)
public class FinancialActionImpl extends BaseEncActionImpl implements FinancialAction {

	private FinancialLogic financialLogic = null;

	/**
	 * 找单据中心
	 * @param request
	 * @return
	 */
	public List findBillType(Request request, int financialType){
		return financialLogic.findBillType(financialType);
	}
	
	public FinancialLogic getFinancialLogic() {
		return financialLogic;
	}
	public void setFinancialLogic(FinancialLogic financialLogic) {
		this.financialLogic = financialLogic;
	}
	
	public FinancialActionImpl(){
		setModuleName("Authority");
	}
	/**
	 * 原材料进出仓帐 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "查找原材料进出仓帐", index = 3)
	public List findMaterialAccountList(Request request, Map conditionMap){
		return financialLogic.findMaterialAccountList(conditionMap);
	}
	/**
	 * 原材料进出仓帐 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
//	public int findMaterialAccountCount(Request request, Map conditionMap){
//		return financialLogic.findMaterialAccountCount(conditionMap);
//	}
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	@AuthorityFunctionAnnotation(caption = "保存原材料进出仓帐", index = 3)
	public void updateMaterialAccount(Request request, List list){
		financialLogic.updateMaterialAccount(list);
	}
	/**
	 * 导入修改的数据
	 * @param request
	 * @param list
	 */
	@AuthorityFunctionAnnotation(caption = "导入原材料进出仓帐", index = 3)
	public void importMaterialAccount(Request request, List list){
		financialLogic.importMaterialAccount(list);
	}
	/**
	 * 料件加权平均价 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "查找料件加权平均价", index = 4)
	public List findMaterialAveragePriceList(Request request, Map conditionMap){
		return financialLogic.findMaterialAveragePriceList(conditionMap);
	}
	/**
	 * 料件加权平均价 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "查找料件加权平均价", index = 4)
	public int findMaterialAveragePriceCount(Request request, Map conditionMap){
		return financialLogic.findMaterialAveragePriceCount(conditionMap);
	}
	
	/**
	 * 成品单耗调整表 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "查找成品单耗调整表", index = 2)
	public List findProductConsumptionList(Request request, Map conditionMap){
		return financialLogic.findProductConsumptionList(conditionMap);
	}
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	@AuthorityFunctionAnnotation(caption = "保存成品单耗调整表", index = 2.1)
	public void updateProductConsumption(Request request, List list){
		financialLogic.updateProductConsumption(list);
	}
	/**
	 * 成品单耗调整表 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
//	public int findProductConsumptionCount(Request request, Map conditionMap){
//		return financialLogic.findProductConsumptionCount(conditionMap);
//	}
	
	/**
	 * 每月产量与销量 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "查找每月产量与销量", index = 1)
	public List findMonthlyProductionAndSalesList(Request request, Map conditionMap){
		return financialLogic.findMonthlyProductionAndSalesList(conditionMap);
	}
	/**
	 * 每月产量与销量数据导入
	 * @param list
	 */
	@AuthorityFunctionAnnotation(caption = "导入每月产量与销量", index = 1.1)
	public void importMonthlyProductionAndSales(Request request, List list){
		financialLogic.importMonthlyProductionAndSales(list);
	}
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	@AuthorityFunctionAnnotation(caption = "保存每月产量与销量", index = 1.2)
	public void updateMonthlyProductionAndSales(Request request, List list){
		financialLogic.updateMonthlyProductionAndSales(list);
	}
//	/**
//	 * 每月产量与销量 查找
//	 * @param request
//	 * @param conditionMap
//	 * @return
//	 */
//	public int findMonthlyProductionAndSalesCount(Request request, Map conditionMap){
//		return financialLogic.findMonthlyProductionAndSalesCount(conditionMap);
//	}
//	
	/**
	 * 成本汇总表 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "查找成本汇总表", index = 5)
	public List findCostSummaryReportList(Request request, Map conditionMap){
		return financialLogic.findCostSummaryReportList(conditionMap);
	}
	/**
	 * 成本汇总表 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
//	public int findCostSummaryReportCount(Request request, Map conditionMap){
//		return financialLogic.findCostSummaryReportCount(conditionMap);
//	}
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	@AuthorityFunctionAnnotation(caption = "保存成本汇总表", index = 5.1)
	public void updateCostSummaryReport(Request request, List list){
		financialLogic.updateCostSummaryReport(list);
	}
	/**
	 * 成品加权平均价 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "查找成品成本进出仓帐", index = 7)
	public List findProductWeightedAveragePriceList(Request request, Map conditionMap){
		return financialLogic.findProductWeightedAveragePriceList(conditionMap);
	}
	/**
	 * 成品加权平均价 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
//	public int findProductWeightedAveragePriceCount(Request request, Map conditionMap){
//		return financialLogic.findProductWeightedAveragePriceCount(conditionMap);
//	}
	
	/**
	 * 成品成本进出仓帐 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "查找成品成本进出仓帐", index = 6)
	public List findFinishedProductCostList(Request request, Map conditionMap){
		return financialLogic.findFinishedProductCostList(conditionMap);
	}
	/**
	 * 成品成本进出仓帐 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
//	public int findFinishedProductCostCount(Request request, Map conditionMap){
//		return financialLogic.findFinishedProductCostCount(conditionMap);
//	}
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	@AuthorityFunctionAnnotation(caption = "保存成品成本进出仓帐", index = 6.1)
	public void updateFinishedProductCost(Request request, List list){
		financialLogic.updateFinishedProductCost(list);
	}
	/**
	 * 导入修改的数据
	 * @param request
	 * @param list
	 */
	@AuthorityFunctionAnnotation(caption = "导入成品成本进出仓帐修改数据", index = 6.2)
	public void importFinishedProductCost(Request request, List list){
		financialLogic.importFinishedProductCost(list);
	}
	public Map findUnit(Request request){
		return financialLogic.findUnit();
	}
}
