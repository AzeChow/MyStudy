package com.bestway.bcus.financial.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.financial.dao.FinancialDao;
import com.bestway.bcus.financial.entity.MonthlyProductionAndSales;
import com.bestway.common.materialbase.entity.CurrRate;

/**
 * 每月产量与销量 生成数据 逻辑 接口
 * @author Administrator
 *
 */
public class GenMonthlyProductionAndSalesLogic {
	
	private FinancialDao financialDao = null;
	
	public FinancialDao getFinancialDao() {
		return financialDao;
	}
	public void setFinancialDao(FinancialDao financialDao) {
		this.financialDao = financialDao;
	}
	public void init(){
		
	}
	/**
	 * 计算 统计 财务分析报表 的各表数据
	 * 当查询的数据
	 * @param conditionMap
	 */
	public void genMonthFinancialData(Map conditionMap){
		Date startDate = (Date)conditionMap.get("startDate");
		Date endDate = (Date)conditionMap.get("endDate");
		List billType = (List)conditionMap.get("billType");
		
		String thisMonthStr = getThisMonthStr(startDate);
		String lastMonthStr = getLastMonthStr(startDate);
		
		//重新查询前，把原来的数据删除
		deleteExitData(thisMonthStr);
		financialDao.getHibernateTemplate().flush();
		//查找上个月的结存，如果结存大于0的，把结存增加到本月的数据上去
		findLastDataAndInsert(thisMonthStr, lastMonthStr);
		financialDao.getHibernateTemplate().flush();
		//查找本月的新数据，并处理后，增加到本月的数据上去
		findAndGenThisMonthlyProductionAndSales(startDate, endDate, billType, thisMonthStr);
		
		financialDao.getHibernateTemplate().flush();
	}
	/**
	 * 重新查询前，把原来的数据删除 
	 * @param thisMonthStr
	 */
	private void deleteExitData(String thisMonthStr){
		String hsql_delete = "delete from MonthlyProductionAndSales a where a.accountDate = ?";
		financialDao.batchUpdateOrDelete(hsql_delete, thisMonthStr);
	}
	/**
	 * 查找上个月的结存，如果结存大于0的，把结存增加到本月的数据上去
	 * @param thisMonthStr
	 * @param lastMonthStr
	 */
	private void findLastDataAndInsert(String thisMonthStr, String lastMonthStr){
		String hsql_last_count_query = "select count(a.id) from MonthlyProductionAndSales a where a.accountDate = ? and a.lastQuantity > 0 ";
		List lastCountList = financialDao.find(hsql_last_count_query, lastMonthStr);
		
		Long lastCount = 0L;
		
		if(lastCountList != null && !lastCountList.isEmpty()){
			lastCount = (Long)lastCountList.get(0);
		}
		
		//查询上个月的结存情况，为了避免数据量过大，分次查询
		String hsql_last_list_query = "select a from MonthlyProductionAndSales a where a.accountDate = ? and a.lastQuantity > 0 ";
		List lastList = null;
		int lastPage = getUpdateDataPage(lastCount);
		
		for(int i = 0 ; i < lastPage ; i ++){
			int index = i * FinancialLogic.updateDataPageSize;
			lastList = financialDao.findPageList(hsql_last_list_query, lastMonthStr, index, FinancialLogic.updateDataPageSize);
			for(int j = 0 ; j < lastList.size() && lastList != null ; j ++){
				MonthlyProductionAndSales lastMonthlyProductionAndSales = (MonthlyProductionAndSales)lastList.get(j);
				
				MonthlyProductionAndSales thisMonthlyProductionAndSales = new MonthlyProductionAndSales();
				thisMonthlyProductionAndSales.setAccountDate(thisMonthStr);
				thisMonthlyProductionAndSales.setHsName(lastMonthlyProductionAndSales.getHsName());
				thisMonthlyProductionAndSales.setHsSpec(lastMonthlyProductionAndSales.getHsSpec());
				thisMonthlyProductionAndSales.setHsUnit(lastMonthlyProductionAndSales.getHsUnit());
				
				financialDao.saveOrUpdate(thisMonthlyProductionAndSales);
				financialDao.getHibernateTemplate().flush();
			}
		}
	}
	/**
	 * 查找本月的新数据，并处理后，增加到本月的数据上去
	 * @param startDate
	 * @param endDate
	 * @param billType
	 */
	private void findAndGenThisMonthlyProductionAndSales(Date startDate, Date endDate, List billType, String thisMonthStr){
		//单据中心查询语句
		String hsql_bill_select = "select a.hsName, a.hsSpec, a.hsUnit.id, a.billMaster.billType.code, sum(a.hsAmount), sum(a.money) from BillDetailProduct a where a.billMaster.validDate >= ? and a.billMaster.validDate <= ? ";
		//报关单查询语句
		String hsql_customs_select = "select a.commName, a.commSpec, a.unit.id, a.baseCustomsDeclaration.impExpType, a.baseCustomsDeclaration.currency.code, sum(a.commAmount), sum(a.commTotalPrice) from BcsCustomsDeclarationCommInfo a where a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? ";
		
		String condition_bill_billtype = "";
		String condition_customs_billtype = "";
		if(billType != null && !billType.isEmpty()){
			for(int i = 0 ; i < billType.size() ; i ++){
				String code = (String) billType.get(i);
				if("2101".equals(code)){//直接出口    转换为报关单类型 4  成品出口
					condition_customs_billtype += "4,";
				}else if("2102".equals(code)){//结转出口   转换为报关单类型   5 转厂出口
					condition_customs_billtype += "5,";
				}else if("2104".equals(code)){//返工复出    转换为报关单类型   7 返工复出
					condition_customs_billtype += "7,";
				}else if("2003".equals(code)){//退厂返工   转换为报关单类型 2退厂返工
					condition_customs_billtype += "2,";
				}else{//找单据中心
					condition_bill_billtype += "'" + code + "',";
				}
			}
			if(!condition_bill_billtype.equals("")){
				condition_bill_billtype = condition_bill_billtype.substring(0, condition_bill_billtype.length() - 1);
				condition_bill_billtype = " and a.billMaster.billType.code in(" + condition_bill_billtype + ")";
				hsql_bill_select += condition_bill_billtype + " group by a.hsName, a.hsSpec, a.hsUnit.id, a.billMaster.billType.code";
			}else{
				hsql_bill_select = "";
			}
			if(!condition_customs_billtype.equals("")){
				condition_customs_billtype = condition_customs_billtype.substring(0, condition_customs_billtype.length() - 1);
				condition_customs_billtype = " and a.baseCustomsDeclaration.impExpType in(" + condition_customs_billtype + ")";
				hsql_customs_select += condition_customs_billtype + " group by a.commName, a.commSpec, a.unit.id, a.baseCustomsDeclaration.impExpType, a.baseCustomsDeclaration.currency.code";
			}else{//如果没有选择报关单的，则不查找报关单的数据
				hsql_customs_select = "";
			}
		}
		
		
		
		
		List<Object> params = new ArrayList<Object>();
		params.add(startDate);
		params.add(endDate);
		if(!hsql_bill_select.equals("")){
			List list = financialDao.find(hsql_bill_select, params.toArray());
			if(list != null && !list.isEmpty()){
				for(int i = 0 ; i < list.size() ; i ++){
					Object[] obj = (Object[])list.get(i);
					String hsName = (String)obj[0];
					String hsSpec = (String)obj[1];
					String unitID = (String)obj[2];
					String billTypeCode = (String)obj[3];
					
					Unit unit = (Unit)financialDao.load(Unit.class, unitID);
					
					Double quantity = (Double) obj[4];
					Double account = (Double) obj[5];
					if(quantity == null)
						quantity = 0.00;
					if(account == null)
						account = 0.00;
					
					MonthlyProductionAndSales monthlyProductionAndSales = findMonthlyProductionAndSales(thisMonthStr, hsName, hsSpec, unit);
					if("2001".equals(billTypeCode)){
						//单据类型为：2001 成品期初单 时，把数量加到上月结存上
						monthlyProductionAndSales.setLastQuantity(FinancialCommon.add(monthlyProductionAndSales.getLastQuantity(), quantity));
					}else if("2002".equals(billTypeCode) || "2004".equals(billTypeCode)){
						//单据类型为：2002 车间入库 或 2004本月盈单 时，把数量与金额加到本月产量上
						monthlyProductionAndSales.setProductionQuantity(FinancialCommon.add(monthlyProductionAndSales.getProductionQuantity(), quantity));
						monthlyProductionAndSales.setProductionAccount(FinancialCommon.add(monthlyProductionAndSales.getProductionAccount(), account));
					}else if("2103".equals(billTypeCode) || "2105".equals(billTypeCode)){
						//单据类型为：2103 返回车间 或 2105本月盘亏单 时，把数量与金额减到本月产量上
						monthlyProductionAndSales.setProductionQuantity(FinancialCommon.sub(monthlyProductionAndSales.getProductionQuantity(), quantity));
						monthlyProductionAndSales.setProductionAccount(FinancialCommon.sub(monthlyProductionAndSales.getProductionAccount(), account));
					}else if("2106".equals(billTypeCode) || "2107".equals(billTypeCode) || "2108".equals(billTypeCode)){
						//单据类型为：2106 海关批准内销 或 2107 其他内销 或  2108 其他处理  时，把数量与金额加到本月销量上
						monthlyProductionAndSales.setSaleQuantity(FinancialCommon.add(monthlyProductionAndSales.getSaleQuantity(), quantity));
						monthlyProductionAndSales.setSaleAccount(FinancialCommon.add(monthlyProductionAndSales.getSaleAccount(), account));
					}else{
						continue;
					}
					financialDao.saveOrUpdate(monthlyProductionAndSales);
					financialDao.getHibernateTemplate().flush();
				}
			}
		}
		
		if(!hsql_customs_select.equals("")){
			//如果没有选择报关单的，则不查找报关单的数据
		
			List list = financialDao.find(hsql_customs_select, params.toArray());
			if(list != null && !list.isEmpty()){
				for(int i = 0 ; i < list.size() ; i ++){
					Object[] obj = (Object[])list.get(i);
					String hsName = (String)obj[0];
					String hsSpec = (String)obj[1];
					String unitID = (String)obj[2];
					Integer impExpType = (Integer)obj[3];
					String currencyCode = (String)obj[4];
					
					Unit unit = (Unit)financialDao.load(Unit.class, unitID);
	
					Double quantity = (Double) obj[5];
					Double account = (Double) obj[6];

					if(quantity == null)
						quantity = 0.00;
					if(account == null)
						account = 0.00;
					else
						account = getTotalAccount(currencyCode, account);

					MonthlyProductionAndSales monthlyProductionAndSales = findMonthlyProductionAndSales(thisMonthStr, hsName, hsSpec, unit);
					if(impExpType == null){
						continue;
					}else if(impExpType == 4 || impExpType == 5 || impExpType == 7){
						//单据类型或报关单类型为：2101 4  直接出口 或 2102 5 结转出口 或 2104 7 返工复出  时，把数量与金额加到本月销量上
						monthlyProductionAndSales.setSaleQuantity(FinancialCommon.add(monthlyProductionAndSales.getSaleQuantity(), quantity));
						monthlyProductionAndSales.setSaleAccount(FinancialCommon.add(monthlyProductionAndSales.getSaleAccount(), account));
					}else if(impExpType == 2){
						//单据类型或报关单类型为：2003 2 退厂返工，把数量与金额减到本月销量上
						monthlyProductionAndSales.setSaleQuantity(FinancialCommon.sub(monthlyProductionAndSales.getSaleQuantity(), quantity));
						monthlyProductionAndSales.setSaleAccount(FinancialCommon.sub(monthlyProductionAndSales.getSaleAccount(), account));
					}else{
						continue;
					}
					financialDao.saveOrUpdate(monthlyProductionAndSales);
					financialDao.getHibernateTemplate().flush();
				}
			}
		}
	}
	
	public MonthlyProductionAndSales findMonthlyProductionAndSales(String monthStr, String hsName, String hsSpec, Unit hsUnit){
		String hsql_find = "select a from MonthlyProductionAndSales a where a.accountDate = ? and a.hsName = ? and a.hsSpec = ? and a.hsUnit = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(monthStr);
		params.add(hsName);
		params.add(hsSpec);
		params.add(hsUnit);
		
		List list = financialDao.find(hsql_find, params.toArray());
		if(list != null && !list.isEmpty()){
			MonthlyProductionAndSales monthlyProductionAndSales = (MonthlyProductionAndSales)list.get(0);
			if(monthlyProductionAndSales.getLastQuantity() == null)
				monthlyProductionAndSales.setLastQuantity(0.00);
			if(monthlyProductionAndSales.getProductionQuantity() == null)
				monthlyProductionAndSales.setProductionQuantity(0.00);
			if(monthlyProductionAndSales.getProductionAccount() == null)
				monthlyProductionAndSales.setProductionAccount(0.00);
			if(monthlyProductionAndSales.getProductionPrice() == null)
				monthlyProductionAndSales.setProductionPrice(0.00);
			if(monthlyProductionAndSales.getSaleQuantity() == null)
				monthlyProductionAndSales.setSaleQuantity(0.00);
			if(monthlyProductionAndSales.getSaleAccount() == null)
				monthlyProductionAndSales.setSaleAccount(0.00);
			if(monthlyProductionAndSales.getSalePrice() == null)
				monthlyProductionAndSales.setSalePrice(0.00);
			return monthlyProductionAndSales;
		}else{
			MonthlyProductionAndSales monthlyProductionAndSales = new MonthlyProductionAndSales();
			monthlyProductionAndSales.setAccountDate(monthStr);
			monthlyProductionAndSales.setHsName(hsName);
			monthlyProductionAndSales.setHsSpec(hsSpec);
			monthlyProductionAndSales.setHsUnit(hsUnit);
			monthlyProductionAndSales.setLastQuantity(0.00);
			monthlyProductionAndSales.setProductionQuantity(0.00);
			monthlyProductionAndSales.setProductionAccount(0.00);
			monthlyProductionAndSales.setProductionPrice(0.00);
			monthlyProductionAndSales.setSaleQuantity(0.00);
			monthlyProductionAndSales.setSaleAccount(0.00);
			monthlyProductionAndSales.setSalePrice(0.00);
			return monthlyProductionAndSales;
		}
	}
	/**
	 * 获取查询的这个月的字符串
	 * @param startDate
	 * @return
	 */
	private String getThisMonthStr(Date startDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		if(month > 9)
			return year + "" + month;
		else 
			return year + "0" + month;
	}
	/**
	 * 获取查询的上个月的字符串
	 * @param startDate
	 * @return
	 */
	private String getLastMonthStr(Date startDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.MONTH,-1);//取上个月的数据
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		
		if(month > 9)
			return year + "" + month;
		else 
			return year + "0" + month;
	}
	/**
	 * 获取总的查询页数
	 * @param startDate
	 * @return
	 */
	private int getUpdateDataPage(Long count){
		return (int)((count + FinancialLogic.updateDataPageSize - 1) / FinancialLogic.updateDataPageSize);
	}
	private Double getTotalAccount(String currencyCode, Double oriAccount){
		String hsql_select_currrate = "select a from CurrRate a where a.curr.name = '人民币' and a.curr1.code = ? order by a.createDate desc";
		List list = financialDao.find(hsql_select_currrate, currencyCode);
		if(list != null && !list.isEmpty()){
			CurrRate currRate = (CurrRate)list.get(0);
			oriAccount = FinancialCommon.mul(oriAccount, currRate.getRate());
			return oriAccount;
		}else{
			return oriAccount;
		}
	}
}
