package com.bestway.bcus.financial.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.financial.dao.FinancialDao;
import com.bestway.bcus.financial.entity.CostSummaryReport;
import com.bestway.bcus.financial.entity.MaterialAccount;
import com.bestway.bcus.financial.entity.MaterialAveragePrice;
import com.bestway.bcus.financial.entity.MonthlyProductionAndSales;
import com.bestway.bcus.financial.entity.ProductConsumption;
import com.bestway.jptds.custombase.entity.CurrRate;

/**
 * 成本汇总表 生成数据 逻辑 接口
 * 
 * @author Administrator
 * 
 */
public class GenCostSummaryReportLogic {

	private FinancialDao financialDao = null;

	public FinancialDao getFinancialDao() {
		return financialDao;
	}

	public void setFinancialDao(FinancialDao financialDao) {
		this.financialDao = financialDao;
	}

	public void init() {

	}

	/**
	 * 成本汇总表 生成数据主接口
	 * 
	 * @param conditionMap
	 */
	public void genCostSummaryReportData(Map conditionMap) {
		Date startDate = (Date) conditionMap.get("startDate");;

		String thisMonthStr = FinancialCommon.getThisMonthStr(startDate);

		// 重新查询前，把原来的数据删除
		deleteExitData(thisMonthStr);
		financialDao.getHibernateTemplate().flush();
		
		//查找每月产量与销量，把数据增加到到本月的数据上去
		findMonthlyProductionAndSalesDataAndInsert(thisMonthStr);
		financialDao.getHibernateTemplate().flush();
		
		//计算加上人工费与制造费之后的数据 与上面独立开来主要是为了方便录入时计算
		genMakeAndLaborCostForAmount(thisMonthStr, 0.00, 0.00);
	}

	public void genMakeAndLaborCostForAmount(String thisMonthStr, Double totalMakeAmount, Double totalLaborAmount){
		Double total = getTotal(thisMonthStr);
		Double averageMakeCost = 0.00;
		Double averageLaborCost = 0.00;
		if(totalLaborAmount != null && totalLaborAmount.doubleValue() != 0.00){
			averageLaborCost = FinancialCommon.div(totalLaborAmount, total);
		}else{
			totalLaborAmount = 0.00;
		}
		if(totalMakeAmount != null && totalMakeAmount.doubleValue() != 0.00){
			averageMakeCost = FinancialCommon.div(totalMakeAmount, total);
		}else{
			totalMakeAmount = 0.00;
		}
		String hsql_sum = "select a from CostSummaryReport a where a.accountDate = ?";
		List list = financialDao.find(hsql_sum, thisMonthStr);
		if(list != null && !list.isEmpty()){
			for(int i = 0 ; i < list.size() ; i++){
				CostSummaryReport costSummaryReport = (CostSummaryReport)list.get(i);
				//单个成品分摊人工费 ＝ 当月人工总费/本月所有成品产值*单个成品的本月产值
				if(averageMakeCost != 0.00)
					costSummaryReport.setMakeAmount(FinancialCommon.mul(averageMakeCost, costSummaryReport.getTotalAmount()));
				else
					costSummaryReport.setMakeAmount(0.00);
				//单个成品分摊制造费 ＝ 当月制造总费/本月所有成品产值*单个成品的本月产值
				if(averageLaborCost != 0.00)
					costSummaryReport.setLaborAmount(FinancialCommon.mul(averageLaborCost, costSummaryReport.getTotalAmount()));
				else
					costSummaryReport.setLaborAmount(0.00);
				
				costSummaryReport.setTotalLaborAmount(totalLaborAmount);
				costSummaryReport.setTotalMakeAmount(totalMakeAmount);
				
				//成品成本 ＝ 料件耗用费+制造费+人工费
				costSummaryReport.setCostTotalAmount(FinancialCommon.add(costSummaryReport.getLjTotalAmount(), 
						FinancialCommon.add(costSummaryReport.getMakeAmount(), costSummaryReport.getLaborAmount())));
				//成品成本单价 ＝ 成品成本/产量
				costSummaryReport.setCostPrice(FinancialCommon.div(costSummaryReport.getCostTotalAmount(), costSummaryReport.getThisInCount()));
				
				financialDao.saveOrUpdate(costSummaryReport);
				financialDao.getHibernateTemplate().flush();
			}
		}
		financialDao.getHibernateTemplate().flush();
	}
	
	/**
	 * 重新查询前，把原来的数据删除
	 * 
	 * @param thisMonthStr
	 */
	private void deleteExitData(String thisMonthStr) {
		String hsql_delete = "delete from CostSummaryReport a where a.accountDate = ?";
		financialDao.batchUpdateOrDelete(hsql_delete, thisMonthStr);
	}
	/**
	 * 查找每月产量与销量，把数据增加到到本月的数据上去
	 * @param thisMonthStr
	 * @param lastMonthStr
	 */
	@SuppressWarnings("unchecked")
	private void findMonthlyProductionAndSalesDataAndInsert(String thisMonthStr){
		
		String hsql_select_MonthlyProductionAndSales = "select a from MonthlyProductionAndSales a where a.accountDate = ? and a.productionQuantity > 0";
		List list = financialDao.find(hsql_select_MonthlyProductionAndSales, thisMonthStr);
		for(int j = 0 ; j < list.size() && list != null ; j ++){
			MonthlyProductionAndSales thisMonthlyProductionAndSales = (MonthlyProductionAndSales)list.get(j);
			
			CostSummaryReport costSummaryReport = new CostSummaryReport();
			costSummaryReport.setAccountDate(thisMonthStr);
			costSummaryReport.setHsName(thisMonthlyProductionAndSales.getHsName());
			costSummaryReport.setHsSpec(thisMonthlyProductionAndSales.getHsSpec());
			costSummaryReport.setHsUnit(thisMonthlyProductionAndSales.getHsUnit());
			
			costSummaryReport.setThisInCount(thisMonthlyProductionAndSales.getProductionQuantity());
			if(thisMonthlyProductionAndSales.getSaleQuantity() != null && thisMonthlyProductionAndSales.getSaleQuantity() != 0
					&& thisMonthlyProductionAndSales.getSaleAccount() != null && thisMonthlyProductionAndSales.getSaleAccount() != 0){
				//如果本月的出售总值与数量都不这空时，取平均价格
				costSummaryReport.setSalePrice(FinancialCommon.div(thisMonthlyProductionAndSales.getSaleAccount(), thisMonthlyProductionAndSales.getSaleQuantity()));
			}else{//如果本月没有出售总值与数量，则需查询最早的手册信息
				String hsql_select_contract = "select a from ContractExg a where a.name = ? and a.spec = ? and a.unit = ? and a.contract.declareState = '4' order by a.contract.beginDate";
				List<Object> params = new ArrayList<Object>();
				params.add(costSummaryReport.getHsName());
				params.add(costSummaryReport.getHsSpec());
				params.add(costSummaryReport.getHsUnit());
				List contractList = financialDao.find(hsql_select_contract, params.toArray());

				if (contractList != null && !contractList.isEmpty()){
					ContractExg contractExg = (ContractExg)contractList.get(0);
					costSummaryReport.setSalePrice(getTotalAccount(contractExg.getContract().getCurr().getCode(), contractExg.getUnitPrice()));
				}else{
					costSummaryReport.setSalePrice(0.00);
				}
			}
			costSummaryReport.setTotalAmount(FinancialCommon.mul(costSummaryReport.getThisInCount(), costSummaryReport.getSalePrice()));
			
			String hsql_select_ProductConsumption = "select a from ProductConsumption a where a.accountDate = ? and a.hsName = ? and a.hsSpec = ? and a.hsUnit = ?";
			List<Object> params = new ArrayList<Object>();
			params.add(thisMonthStr);
			params.add(costSummaryReport.getHsName());
			params.add(costSummaryReport.getHsSpec());
			params.add(costSummaryReport.getHsUnit());
			List productConsumptionList = financialDao.find(hsql_select_ProductConsumption, params.toArray());
			Double ljTotalAmount = 0.00;
			if(productConsumptionList != null && !productConsumptionList.isEmpty()){
				for(int i = 0 ; i < productConsumptionList.size() ; i ++){
					ProductConsumption productConsumption = (ProductConsumption)productConsumptionList.get(i);
					//料件总成本 ＝ 所有料件的调整后单项用量 * 对应料件单价 * 成品产量
					if(costSummaryReport.getThisInCount() != null
							&& productConsumption.getChangeUnitWaste() != null){
						double price = getLJPriceFromMaterialAveragePrice(thisMonthStr, costSummaryReport.getHsName(), costSummaryReport.getHsSpec(), costSummaryReport.getHsUnit());
						ljTotalAmount = FinancialCommon.add(ljTotalAmount, 
								FinancialCommon.mul(costSummaryReport.getThisInCount(),
										FinancialCommon.mul(productConsumption.getChangeUnitWaste(), price)));
					}
				}
			}
			costSummaryReport.setLjTotalAmount(ljTotalAmount);
			financialDao.saveOrUpdate(costSummaryReport);
			financialDao.getHibernateTemplate().flush();
		}
	}
	/**
	 * 汇率的转换
	 * @param currencyCode
	 * @param oriAccount
	 * @return
	 */
	private Double getTotalAccount(String currencyCode, Double oriAccount){
		String hsql_select_currrate = "select a from CurrRate a where a.curr.name = '人民币' and a.curr1.code = ?";
		List list = financialDao.find(hsql_select_currrate, currencyCode);
		if(list != null && !list.isEmpty()){
			CurrRate currRate = (CurrRate)list.get(0);
			return FinancialCommon.mul(oriAccount, currRate.getRate());
		}else{
			return oriAccount;
		}
	}
	private Double getTotal(String thisMonthStr){
		String hsql_sum = "select sum(a.totalAmount) from CostSummaryReport a where a.accountDate = ?";
		List list = financialDao.find(hsql_sum, thisMonthStr);
		if(list != null && !list.isEmpty()){
			return (Double)list.get(0);
		}else{
			return 0.00;
		}
	}
	private Double getLJPriceFromMaterialAveragePrice(String thisMonthStr, String hsName, String hsSpec, Unit hsUnit){
		String hsql_select_MaterialAveragePrice = "select a from MaterialAveragePrice a where a.accountDate = ? and a.hsName = ? and a.hsSpec = ? and a.hsUnit = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(thisMonthStr);
		params.add(hsName);
		params.add(hsSpec);
		params.add(hsUnit);
		
		List list = financialDao.find(hsql_select_MaterialAveragePrice, params.toArray());
		double price = 0.00;
		if(list != null && !list.isEmpty()){
			MaterialAveragePrice materialAveragePrice = (MaterialAveragePrice)list.get(0);
			if(materialAveragePrice.getAveragePrice() != null)
				price = materialAveragePrice.getAveragePrice();
		}
		return price;
	}
}
