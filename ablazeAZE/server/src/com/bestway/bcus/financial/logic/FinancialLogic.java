package com.bestway.bcus.financial.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.financial.dao.FinancialDao;
import com.bestway.bcus.financial.entity.MaterialAccount;
import com.bestway.bcus.financial.entity.MonthlyProductionAndSales;
import com.bestway.bcus.financial.entity.ProductAccount;
import com.bestway.bcus.financial.entity.TempMaterialAccount;
import com.bestway.bcus.financial.entity.TempMonthlyProductionAndSales;
import com.bestway.bcus.financial.entity.TempProductAccount;

/**
 * 账务成本分析 逻辑 接口
 * @author Administrator
 *
 */
public class FinancialLogic {
	
	private FinancialDao financialDao = null;
	
	private GenMonthlyProductionAndSalesLogic genMonthlyProductionAndSalesLogic = null;
	
	private GenProductConsumptionLogic genProductConsumptionLogic = null;
	
	private GenMaterialAccountLogic genMaterialAccountLogic = null;
	
	private GenCostSummaryReportLogic genCostSummaryReportLogic = null;
	
	private GenProductAccountLogic genProductAccountLogic = null;
	/**
	 * 处理数据时，每次的处理数量，以防止内存溢出或处理超时
	 */
	protected final static int updateDataPageSize = 500;
	
	public FinancialDao getFinancialDao() {
		return financialDao;
	}
	public void setFinancialDao(FinancialDao financialDao) {
		this.financialDao = financialDao;
	}
	public GenMonthlyProductionAndSalesLogic getGenMonthlyProductionAndSalesLogic() {
		return genMonthlyProductionAndSalesLogic;
	}
	public void setGenMonthlyProductionAndSalesLogic(
			GenMonthlyProductionAndSalesLogic genMonthlyProductionAndSalesLogic) {
		this.genMonthlyProductionAndSalesLogic = genMonthlyProductionAndSalesLogic;
	}
	public GenProductConsumptionLogic getGenProductConsumptionLogic() {
		return genProductConsumptionLogic;
	}
	public void setGenProductConsumptionLogic(
			GenProductConsumptionLogic genProductConsumptionLogic) {
		this.genProductConsumptionLogic = genProductConsumptionLogic;
	}
	public GenMaterialAccountLogic getGenMaterialAccountLogic() {
		return genMaterialAccountLogic;
	}
	public void setGenMaterialAccountLogic(
			GenMaterialAccountLogic genMaterialAccountLogic) {
		this.genMaterialAccountLogic = genMaterialAccountLogic;
	}
	public GenCostSummaryReportLogic getGenCostSummaryReportLogic() {
		return genCostSummaryReportLogic;
	}
	public void setGenCostSummaryReportLogic(
			GenCostSummaryReportLogic genCostSummaryReportLogic) {
		this.genCostSummaryReportLogic = genCostSummaryReportLogic;
	}
	
	public GenProductAccountLogic getGenProductAccountLogic() {
		return genProductAccountLogic;
	}
	public void setGenProductAccountLogic(
			GenProductAccountLogic genProductAccountLogic) {
		this.genProductAccountLogic = genProductAccountLogic;
	}
	public void init(){
		
	}
	/**
	 * 找单据中心
	 * @param request
	 * @return
	 */
	public List findBillType(int financialType){
		String hsql = "select a from BillType a where ";
		String condition = "";
		switch (financialType) {
			case FinancialCommon.FINANCIAL_TYPE_MONTHLY_PRODUCTION_AND_SALES:
				condition = "a.code in("
					+ "'2001',"//成品期初单
					+ "'2002',"//车间入库
					+ "'2003',"//退厂返工
					+ "'2004',"//本月盈单
					+ "'2011',"//已交货未结转期初单
					+ "'2012',"//已结转未交货期初单
					+ "'2101',"//直接出口
					+ "'2102',"//结转出口
					+ "'2103',"//返回车间
					+ "'2104',"//返工复出
					+ "'2105',"//本月盘亏单
					+ "'2106',"//海关批准内销
					+ "'2107',"//其他内销
					+ "'2108'" //其他处理
					+")" ; 
			break;
			case FinancialCommon.FINANCIAL_TYPE_MATERIAL_ACCOUNT:
				condition = "a.code in("
					+ "'1001',"//料件期初单
					+ "'1015',"//已收货未结转期初单
					+ "'1016',"//已结转未收货期初单
					+ "'1003',"//直接进口   —直接取报关单 
					+ "'1004',"//结转进口   —直接取报关单
					+ "'1005',"//国内购买
					+ "'1006',"//车间返回
					+ "'1007',"//料件盘盈单
					+ "'1104',"//料件盘亏单
					+ "'1009',"//其他来源
					+ "'1011',"//海关征税进口
					+ "'1102',"//料件退换
					+ "'1103',"//料件复出
					+ "'1106'" //结转料件退货单
					+ "'1107',"//其他料件退货单
					+ "'1108',"//其他领用
					+ "'1115'" //海关批准内销
					+")" ;
			break;
			case FinancialCommon.FINANCIAL_TYPE_PRODUCT_ACCOUNT:
				condition = "a.code in("
					+ "'2001',"//成品期初单
					+ "'2011',"//已交货未结转期初单
					+ "'2012',"//已结转未交货期初单
					+ "'2002',"//车间入库
					+ "'2103',"//返回车间
					+ "'2004',"//本月盈单
					+ "'2105',"//本月盘亏单
					+ "'2101',"//直接出口
					+ "'2102',"//结转出口
					+ "'2009',"//结转成品退货单
					+ "'2008',"//其他成品退货单
					+ "'2003',"//退厂返工
					+ "'2104',"//返工复出
					+ "'2106',"//海关批准内销
					+ "'2107',"//其他内销
					+ "'2108'" //其他处理
					+")" ;
			break;
			default:
				condition = " 1 = 1 ";
				break;
		}
		hsql += condition + " order by a.code";
		
		return financialDao.find(hsql);
	}
	/**
	 * 原材料进出仓帐 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	public List findMaterialAccountList(Map conditionMap){
		Date startDate = (Date)conditionMap.get("startDate");
		String thisMonthStr = FinancialCommon.getThisMonthStr(startDate);
		
		String isReLoad = (String)conditionMap.get("isReLoad");
		long count = findMaterialAccountCount(thisMonthStr);
		if(count == 0 || (isReLoad != null && "1".equals(isReLoad))){
			//如果数量为空，或者 是重新生成数据，则调用 重新生成数据的方法
			genMaterialAccountLogic.genMaterialAccountData(conditionMap);
		}
		String hsql = "select a from MaterialAccount a where a.accountDate = ? ";
		
		List<Object> params = new ArrayList<Object>();
		params.add(thisMonthStr);
		
		String hsName = (String)conditionMap.get("hsName");
		if(hsName != null && !"".equals(hsName)){
			hsql += " and a.hsName like ?";
			params.add("%" + hsName + "%");
		}
		hsql += " order by a.hsName";

		return financialDao.find(hsql, params.toArray());
	}
	/**
	 * 原材料进出仓帐 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	public Long findMaterialAccountCount(String thisMonthStr){
		String hsql = "select count(a.id) from MaterialAccount a where a.accountDate = ?";
		
		List lastCountList = financialDao.find(hsql, thisMonthStr);
		
		Long count = 0L;
		
		if(lastCountList != null && !lastCountList.isEmpty()){
			count = (Long)lastCountList.get(0);
		}
		
		return count;
	}
	public void importMaterialAccount(List <TempMaterialAccount> list){
		String thisMonthStr = "";
		for(TempMaterialAccount temp : list){
				
			MaterialAccount materialAccount = temp.getEntity();
			MaterialAccount entity = genMaterialAccountLogic.findMaterialAccount(materialAccount.getAccountDate(), materialAccount.getHsName(), materialAccount.getHsSpec(), materialAccount.getHsUnit());
			
			entity.setThisInCount(FinancialCommon.add(entity.getThisInCount(),materialAccount.getThisInCount()));
			entity.setThisInAmount(FinancialCommon.add(entity.getThisInAmount(), materialAccount.getThisInAmount()));
			
			financialDao.saveOrUpdate(entity);
			if(thisMonthStr.equals(""))
				thisMonthStr = entity.getAccountDate();
		}

		financialDao.getHibernateTemplate().flush();
		if(!thisMonthStr.equals(""))
			genMaterialAccountLogic.findAndGenThisMaterialAveragePrice(thisMonthStr);
	}
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	public void updateMaterialAccount(List list){
		financialDao.batchSaveOrUpdate(list);
		financialDao.getHibernateTemplate().flush();
		if(list != null && !list.isEmpty()){
			MaterialAccount materialAccount = (MaterialAccount)list.get(0);
			genMaterialAccountLogic.findAndGenThisMaterialAveragePrice(materialAccount.getAccountDate());
		}
	}
	/**
	 * 料件加权平均价 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	public List findMaterialAveragePriceList(Map conditionMap){
		Date startDate = (Date)conditionMap.get("startDate");
		String thisMonthStr = FinancialCommon.getThisMonthStr(startDate);
		
		String hsql = "select a from MaterialAveragePrice a where a.accountDate = ? ";
		
		List<Object> params = new ArrayList<Object>();
		params.add(thisMonthStr);
		
		String hsName = (String)conditionMap.get("hsName");
		if(hsName != null && !"".equals(hsName)){
			hsql += " and a.hsName like ?";
			params.add("%" + hsName + "%");
		}
		hsql += " order by a.hsName";

		return financialDao.find(hsql, params.toArray());
	}
	/**
	 * 料件加权平均价 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	public int findMaterialAveragePriceCount(Map conditionMap){
		return 0;
	}
	
	/**
	 * 成品单耗调整表 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	public List findProductConsumptionList(Map conditionMap){
		Date startDate = (Date)conditionMap.get("startDate");
		String thisMonthStr = FinancialCommon.getThisMonthStr(startDate);
		
		String isReLoad = (String)conditionMap.get("isReLoad");
		long count = findProductConsumptionCount(thisMonthStr);
		if(count == 0 || (isReLoad != null && "1".equals(isReLoad))){
			//如果数量为空，或者 是重新生成数据，则调用 重新生成数据的方法
			genProductConsumptionLogic.genProductConsumptionData(conditionMap);
		}
		String hsql = "select a from ProductConsumption a where a.accountDate = ? ";
		
		List<Object> params = new ArrayList<Object>();
		params.add(thisMonthStr);
		
		String hsName = (String)conditionMap.get("hsName");
		if(hsName != null && !"".equals(hsName)){
			hsql += " and a.hsName like ?";
			params.add("%" + hsName + "%");
		}
		String ljName = (String)conditionMap.get("ljName");
		if(ljName != null && !"".equals(ljName)){
			hsql += " and a.ljName like ?";
			params.add("%" + ljName + "%");
		}
		hsql += " order by a.hsName, a.ljName";
		
		return financialDao.find(hsql, params.toArray());
	}
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	public void updateProductConsumption(List list){
		financialDao.batchSaveOrUpdate(list);
	}
	/**
	 * 成品单耗调整表 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	public Long findProductConsumptionCount(String thisMonthStr){
		String hsql = "select count(a.id) from ProductConsumption a where a.accountDate = ?";
		
		List lastCountList = financialDao.find(hsql, thisMonthStr);
		
		Long count = 0L;
		
		if(lastCountList != null && !lastCountList.isEmpty()){
			count = (Long)lastCountList.get(0);
		}
		
		return count;
	}
	
	/**
	 * 每月产量与销量 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	public List findMonthlyProductionAndSalesList(Map conditionMap){

		Date startDate = (Date)conditionMap.get("startDate");
		String thisMonthStr = FinancialCommon.getThisMonthStr(startDate);
		
		String isReLoad = (String)conditionMap.get("isReLoad");
		long count = findMonthlyProductionAndSalesCount(thisMonthStr);
		if(count == 0 || (isReLoad != null && "1".equals(isReLoad))){
			//如果数量为空，或者 是重新生成数据，则调用 重新生成数据的方法
			genMonthlyProductionAndSalesLogic.genMonthFinancialData(conditionMap);
		}
		String hsql = "select a from MonthlyProductionAndSales a where a.accountDate = ?";
		
		List<Object> params = new ArrayList<Object>();
		params.add(thisMonthStr);
		
		String hsName = (String)conditionMap.get("hsName");
		if(hsName != null && !"".equals(hsName)){
			hsql += " and a.hsName like ?";
			params.add("%" + hsName + "%");
		}
		hsql += " order by a.hsName";

		return financialDao.find(hsql, params.toArray());
	}
	/**
	 * 每月产量与销量 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	public long findMonthlyProductionAndSalesCount(String thisMonthStr){
		String hsql = "select count(a.id) from MonthlyProductionAndSales a where a.accountDate = ?";
		
		List lastCountList = financialDao.find(hsql, thisMonthStr);
		
		Long count = 0L;
		
		if(lastCountList != null && !lastCountList.isEmpty()){
			count = (Long)lastCountList.get(0);
		}
		
		return count;
	}
	public void importMonthlyProductionAndSales(List <TempMonthlyProductionAndSales> list){
		for(TempMonthlyProductionAndSales temp : list){
			MonthlyProductionAndSales monthly = temp.getEntity();
			MonthlyProductionAndSales entity = genMonthlyProductionAndSalesLogic.findMonthlyProductionAndSales(monthly.getAccountDate(), monthly.getHsName(), monthly.getHsSpec(), monthly.getHsUnit());
			entity.setProductionQuantity(FinancialCommon.add(entity.getProductionQuantity(), monthly.getProductionQuantity()));
			entity.setProductionAccount(FinancialCommon.add(entity.getProductionAccount(), monthly.getProductionAccount()));
			entity.setSaleQuantity(FinancialCommon.add(entity.getSaleQuantity(), monthly.getSaleQuantity()));
			entity.setSaleAccount(FinancialCommon.add(entity.getSaleAccount(), monthly.getSaleAccount()));
			
			financialDao.saveOrUpdate(entity);		
			
		}
	}
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	public void updateMonthlyProductionAndSales(List list){
		financialDao.batchSaveOrUpdate(list);
	}
	/**
	 * 成本汇总表 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	public List findCostSummaryReportList(Map conditionMap){
		Date startDate = (Date)conditionMap.get("startDate");
		String thisMonthStr = FinancialCommon.getThisMonthStr(startDate);
		
		String isReLoad = (String)conditionMap.get("isReLoad");
		long count = findCostSummaryReportCount(thisMonthStr);
		if(count == 0 || (isReLoad != null && "1".equals(isReLoad))){
			//如果数量为空，或者 是重新生成数据，则调用 重新生成数据的方法
			genCostSummaryReportLogic.genCostSummaryReportData(conditionMap);
			
			Double totalMakeAmount = (Double)conditionMap.get("totalMakeAmount");
			Double totalLaborAmount = (Double)conditionMap.get("totalLaborAmount");
			if(totalMakeAmount != null || totalLaborAmount != null){//有其中一个数据的话，就需要调用计算人工费与制造费的方法
				genCostSummaryReportLogic.genMakeAndLaborCostForAmount(thisMonthStr, totalMakeAmount, totalLaborAmount);
			}
		}
		String hsql = "select a from CostSummaryReport a where a.accountDate = ?";
		
		List<Object> params = new ArrayList<Object>();
		params.add(thisMonthStr);
		
		String hsName = (String)conditionMap.get("hsName");
		if(hsName != null && !"".equals(hsName)){
			hsql += " and a.hsName like ?";
			params.add("%" + hsName + "%");
		}
		hsql += " order by a.hsName";

		return financialDao.find(hsql, params.toArray());
	}
	/**
	 * 成本汇总表 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	private Long findCostSummaryReportCount(String thisMonthStr){
		String hsql = "select count(a.id) from CostSummaryReport a where a.accountDate = ?";
		
		List lastCountList = financialDao.find(hsql, thisMonthStr);
		
		Long count = 0L;
		
		if(lastCountList != null && !lastCountList.isEmpty()){
			count = (Long)lastCountList.get(0);
		}
		
		return count;
	}
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	public void updateCostSummaryReport(List list){
		financialDao.batchSaveOrUpdate(list);
	}
	/**
	 * 成品加权平均价 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	public List findProductWeightedAveragePriceList(Map conditionMap){
		Date startDate = (Date)conditionMap.get("startDate");
		String thisMonthStr = FinancialCommon.getThisMonthStr(startDate);
		
		String hsql = "select a from ProductAveragePrice a where a.accountDate = ? ";
		
		List<Object> params = new ArrayList<Object>();
		params.add(thisMonthStr);
		
		String hsName = (String)conditionMap.get("hsName");
		if(hsName != null && !"".equals(hsName)){
			hsql += " and a.hsName like ?";
			params.add("%" + hsName + "%");
		}
		hsql += " order by a.hsName";

		return financialDao.find(hsql, params.toArray());
	}
	/**
	 * 成品加权平均价 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	public int findProductWeightedAveragePriceCount(Map conditionMap){
		return 0;
	}
	
	/**
	 * 成品成本进出仓帐 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	public List findFinishedProductCostList(Map conditionMap){
		Date startDate = (Date)conditionMap.get("startDate");
		String thisMonthStr = FinancialCommon.getThisMonthStr(startDate);
		
		String isReLoad = (String)conditionMap.get("isReLoad");
		long count = findProductAccountCount(thisMonthStr);
		if(count == 0 || (isReLoad != null && "1".equals(isReLoad))){
			//如果数量为空，或者 是重新生成数据，则调用 重新生成数据的方法
			genProductAccountLogic.genProductAccountData(conditionMap);
		}
		String hsql = "select a from ProductAccount a where a.accountDate = ?";
		
		List<Object> params = new ArrayList<Object>();
		params.add(thisMonthStr);
		
		String hsName = (String)conditionMap.get("hsName");
		if(hsName != null && !"".equals(hsName)){
			hsql += " and a.hsName like ?";
			params.add("%" + hsName + "%");
		}
		hsql += " order by a.hsName";

		return financialDao.find(hsql, params.toArray());
	}
	/**
	 * 成品成本进出仓帐 查找
	 * @param request
	 * @param conditionMap
	 * @return
	 */
	public Long findProductAccountCount(String thisMonthStr){
		String hsql = "select count(a.id) from ProductAccount a where a.accountDate = ?";
		
		List lastCountList = financialDao.find(hsql, thisMonthStr);
		
		Long count = 0L;
		
		if(lastCountList != null && !lastCountList.isEmpty()){
			count = (Long)lastCountList.get(0);
		}
		
		return count;
	}
	/**
	 * 查询后，如果有修改，更新
	 * @param request
	 * @param list
	 */
	public void updateFinishedProductCost(List list){
		financialDao.batchSaveOrUpdate(list);
		financialDao.getHibernateTemplate().flush();
		if(list != null && !list.isEmpty()){
			ProductAccount materialAccount = (ProductAccount)list.get(0);
			genMaterialAccountLogic.findAndGenThisMaterialAveragePrice(materialAccount.getAccountDate());
		}
	}
	public void importFinishedProductCost(List <TempProductAccount> list){
		String thisMonthStr = "";
		for(TempProductAccount temp : list){
				
			ProductAccount productAccount = temp.getEntity();
			ProductAccount entity = genProductAccountLogic.findProductAccount(productAccount.getAccountDate(), productAccount.getHsName(), productAccount.getHsSpec(), productAccount.getHsUnit());
			
			entity.setThisInCount(FinancialCommon.add(entity.getThisInCount(),productAccount.getThisInCount()));
			entity.setThisInAmount(FinancialCommon.add(entity.getThisInAmount(), productAccount.getThisInAmount()));
			
			financialDao.saveOrUpdate(entity);
			if(thisMonthStr.equals(""))
				thisMonthStr = entity.getAccountDate();
		}

		financialDao.getHibernateTemplate().flush();
		if(!thisMonthStr.equals(""))
			genProductAccountLogic.findAndGenThisProductAveragePrice(thisMonthStr);
	}
	public Map findUnit(){
		String hsql_select_unit = "select a from Unit a";
		List list = financialDao.find(hsql_select_unit);
		Map map = new HashMap();
		
		for(int i = 0 ; i < list.size() ; i ++){
			Unit unit = (Unit)list.get(i);
			map.put(unit.getName(), unit);
		}
		return map;
	}
}
