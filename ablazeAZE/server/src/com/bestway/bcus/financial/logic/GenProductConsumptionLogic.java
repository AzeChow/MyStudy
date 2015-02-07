package com.bestway.bcus.financial.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcus.financial.dao.FinancialDao;
import com.bestway.bcus.financial.entity.MonthlyProductionAndSales;
import com.bestway.bcus.financial.entity.ProductConsumption;

/**
 * 成品单耗调整表 生成数据 逻辑 接口
 * 
 * @author Administrator
 * 
 */
public class GenProductConsumptionLogic {

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
	 * 成品单耗调整表 生成数据主接口
	 * 
	 * @param conditionMap
	 */
	public void genProductConsumptionData(Map conditionMap) {
		Date startDate = (Date) conditionMap.get("startDate");
		Date endDate = (Date) conditionMap.get("endDate");

		String thisMonthStr = FinancialCommon.getThisMonthStr(startDate);

		// 重新查询前，把原来的数据删除
		deleteExitData(thisMonthStr);
		financialDao.getHibernateTemplate().flush();
		// 查找本月的新数据，并处理后，增加到本月的数据上去
		findAndGenThisMonthlyProductConsumption(startDate, endDate, thisMonthStr);
		financialDao.getHibernateTemplate().flush();
	}

	/**
	 * 重新查询前，把原来的数据删除
	 * 
	 * @param thisMonthStr
	 */
	private void deleteExitData(String thisMonthStr) {
		String hsql_delete = "delete from ProductConsumption a where a.accountDate = ?";
		financialDao.batchUpdateOrDelete(hsql_delete, thisMonthStr);
	}

	/**
	 * 查找上个月的结存，如果结存大于0的，把结存增加到本月的数据上去
	 * 
	 * @param thisMonthStr
	 * @param lastMonthStr
	 */
	private void findAndGenThisMonthlyProductConsumption(Date startDate,
			Date endDate, String thisMonthStr) {
		String hsql_last_count_query = "select a from MonthlyProductionAndSales a where a.accountDate = ?";
		List monthlyProductionAndSalesList = financialDao.find(hsql_last_count_query, thisMonthStr);
		
		if (monthlyProductionAndSalesList == null
				|| monthlyProductionAndSalesList.isEmpty()) {
			return;
		}

		for (int i = 0; i < monthlyProductionAndSalesList.size(); i++) {
			MonthlyProductionAndSales monthly = (MonthlyProductionAndSales) monthlyProductionAndSalesList.get(i);

			String hsql_select_contract = "select a.contract from ContractExg a where a.name = ? and a.spec = ? and a.unit = ? and a.contract.declareState = '3' order by a.contract.beginDate";
			List<Object> params = new ArrayList<Object>();
			params.add(monthly.getHsName());
			params.add(monthly.getHsSpec());
			params.add(monthly.getHsUnit());
			List contractList = financialDao.find(hsql_select_contract, params.toArray());

			if (contractList == null || contractList.isEmpty())
				continue;

			// 按开始时间排序，拿最早的有效合同
			Contract contract = (Contract) contractList.get(0);

			String hsql_select_contractbom = "select a from ContractBom a where a.contractExg.name = ? and a.contractExg.spec = ? and a.contractExg.unit = ? and a.contractExg.contract = ?";
			params.add(contract);

			List contractbomList = financialDao.find(hsql_select_contractbom,params.toArray());

			if (contractbomList == null || contractbomList.isEmpty()) {
				continue;
			}
			for (int j = 0; j < contractbomList.size(); j++) {
				ContractBom contractBom = (ContractBom)contractbomList.get(j);
				
				ProductConsumption productConsumption = new ProductConsumption();
				productConsumption.setAccountDate(thisMonthStr);
				productConsumption.setHsName(monthly.getHsName());
				productConsumption.setHsSpec(monthly.getHsSpec());
				productConsumption.setHsUnit(monthly.getHsUnit());
				productConsumption.setThisProduction(monthly.getProductionQuantity());
				
				productConsumption.setLjName(contractBom.getName());
				productConsumption.setLjSpec(contractBom.getSpec());
				productConsumption.setLjUnit(contractBom.getUnit());
				
				productConsumption.setAlterRate(1.00);
				productConsumption.setUnitWaste(contractBom.getUnitWaste());
				productConsumption.setWaste(contractBom.getWaste());
				
				//如果损耗率为空的，默认为 0
				double waste = productConsumption.getWaste() == null ? 0.00 : productConsumption.getWaste();
				
				//调后单项用量=净耗/(1-损耗率)*上下浮率
				double changeUnitWaste = FinancialCommon.div(productConsumption.getUnitWaste(), FinancialCommon.sub(1, waste/100));
				productConsumption.setChangeUnitWaste(changeUnitWaste);
				
				//本月需料量=调后单项用量*产量
				if(productConsumption.getThisProduction() != null){
					productConsumption.setLjNeedQuantity(FinancialCommon.mul(changeUnitWaste, productConsumption.getThisProduction()));
				}
				
				financialDao.saveOrUpdate(productConsumption);
				financialDao.getHibernateTemplate().flush();
			}
		}
	}
}
