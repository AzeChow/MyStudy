package com.bestway.bcus.financial.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.financial.dao.FinancialDao;
import com.bestway.bcus.financial.entity.MaterialAccount;
import com.bestway.bcus.financial.entity.MaterialAveragePrice;
import com.bestway.bcus.financial.entity.ProductConsumption;
import com.bestway.common.materialbase.entity.CurrRate;

/**
 * 成品单耗调整表 生成数据 逻辑 接口
 * 
 * @author Administrator
 * 
 */
public class GenMaterialAccountLogic {

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
	public void genMaterialAccountData(Map conditionMap) {
		Date startDate = (Date) conditionMap.get("startDate");
		Date endDate = (Date) conditionMap.get("endDate");
		List billType = (List)conditionMap.get("billType");

		String thisMonthStr = FinancialCommon.getThisMonthStr(startDate);
		String lastMonthStr = FinancialCommon.getLastMonthStr(startDate);

		// 重新查询前，把原来的数据删除
		deleteExitData(thisMonthStr);
		financialDao.getHibernateTemplate().flush();
		//查找上个月的结存，如果结存大于0的，把结存增加到本月的数据上去
		findLastDataAndInsert(thisMonthStr, lastMonthStr);
		financialDao.getHibernateTemplate().flush();
		// 查找本月的新数据，并处理后，增加到本月的数据上去 只处理上月结存与本月进入
		findAndGenThisMonthlyMaterialAccount(startDate, endDate, billType, thisMonthStr);
		financialDao.getHibernateTemplate().flush();
		//计算料件加权平均价，处理后并计算本月发出的金额
		findAndGenThisMaterialAveragePrice(thisMonthStr);
		financialDao.getHibernateTemplate().flush();
	}

	/**
	 * 重新查询前，把原来的数据删除
	 * 
	 * @param thisMonthStr
	 */
	private void deleteExitData(String thisMonthStr) {
		String hsql_delete = "delete from MaterialAccount a where a.accountDate = ?";
		financialDao.batchUpdateOrDelete(hsql_delete, thisMonthStr);
		
		String hsql_delete_MaterialAveragePrice = "delete from MaterialAveragePrice a where a.accountDate = ?";
		financialDao.batchUpdateOrDelete(hsql_delete_MaterialAveragePrice, thisMonthStr);
	}
	/**
	 * 查找上个月的结存，如果结存大于0的，把结存增加到本月的数据上去
	 * @param thisMonthStr
	 * @param lastMonthStr
	 */
	private void findLastDataAndInsert(String thisMonthStr, String lastMonthStr){
		String hsql_last_count_query = "select count(a.id) from MaterialAccount a where a.accountDate = ? and a.balanceCount > 0 and a.balanceAmount > 0";
		List lastCountList = financialDao.find(hsql_last_count_query, lastMonthStr);
		
		Long lastCount = 0L;
		
		if(lastCountList != null && !lastCountList.isEmpty()){
			lastCount = (Long)lastCountList.get(0);
		}
		
		//查询上个月的结存情况，为了避免数据量过大，分次查询
		String hsql_last_list_query = "select a from MaterialAccount a where a.accountDate = ? and a.balanceCount > 0 and a.balanceAmount > 0 ";
		List lastList = null;
		int lastPage = getUpdateDataPage(lastCount);
		for(int i = 0 ; i < lastPage ; i ++){
			int index = i * FinancialLogic.updateDataPageSize;
			lastList = financialDao.findPageList(hsql_last_list_query, lastMonthStr, index, FinancialLogic.updateDataPageSize);
			for(int j = 0 ; j < lastList.size() && lastList != null ; j ++){
				MaterialAccount lastMaterialAccount = (MaterialAccount)lastList.get(j);
				
				MaterialAccount thisMaterialAccount = new MaterialAccount();
				thisMaterialAccount.setAccountDate(thisMonthStr);
				thisMaterialAccount.setHsName(lastMaterialAccount.getHsName());
				thisMaterialAccount.setHsSpec(lastMaterialAccount.getHsSpec());
				thisMaterialAccount.setHsUnit(lastMaterialAccount.getHsUnit());
				
				thisMaterialAccount.setPreviousCount(lastMaterialAccount.getBalanceCount());
				thisMaterialAccount.setPreviousAmount(lastMaterialAccount.getBalanceAmount());
				
				financialDao.saveOrUpdate(thisMaterialAccount);
			}
		}
	}
	/**
	 * 查找上个月的结存，如果结存大于0的，把结存增加到本月的数据上去
	 * 
	 * @param thisMonthStr
	 * @param lastMonthStr
	 */
	private void findAndGenThisMonthlyMaterialAccount(Date startDate, Date endDate, List billType, String thisMonthStr) {
		//单据中心查询语句
		String hsql_bill_select = "select a.hsName, a.hsSpec, a.hsUnit.id, a.billMaster.billType.code, sum(a.hsAmount), sum(a.money) from BillDetailMateriel a where a.billMaster.validDate >= ? and a.billMaster.validDate <= ? ";
		//报关单查询语句
		String hsql_customs_select = "select a.commName, a.commSpec, a.unit.id, a.baseCustomsDeclaration.impExpType, a.baseCustomsDeclaration.currency.code, sum(a.commAmount), sum(a.commTotalPrice) from BcsCustomsDeclarationCommInfo a where a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ? ";
		
		String condition_bill_billtype = "";
		String condition_customs_billtype = "";
		if(billType != null && !billType.isEmpty()){
			for(int i = 0 ; i < billType.size() ; i ++){
				String code = (String) billType.get(i);

				if("1003".equals(code)){//直接进口    转换为报关单类型 0 料件进口
					condition_customs_billtype += "0,";
				}else if("1004".equals(code)){//结转进口   转换为报关单类型   1 料件转厂
					condition_customs_billtype += "1,";
				}else if("1102".equals(code)){//料件退换    转换为报关单类型   6 退料出口
					condition_customs_billtype += "6,";
				}else if("1103".equals(code)){//料件复出   转换为报关单类型 6 退料出口
					condition_customs_billtype += "6,";
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
					
					MaterialAccount materialAccount = findMaterialAccount(thisMonthStr, hsName, hsSpec, unit);
					if("1001".equals(billTypeCode) || "1015".equals(billTypeCode)){
						//单据类型为：1001 日期在本月的期初单 或 1015 日期在本月的已收货未转厂期初单 时，把数量与金额加到上期结存上
						materialAccount.setPreviousCount(FinancialCommon.add(materialAccount.getPreviousCount(), quantity));
						materialAccount.setPreviousAmount(FinancialCommon.add(materialAccount.getPreviousAmount(), account));
					}else if("1016".equals(billTypeCode)){
						//单据类型为：1016 日期在本月的已转厂未收货期初单  时，把数量与金额减到上期结存上
						materialAccount.setPreviousCount(FinancialCommon.sub(materialAccount.getPreviousCount(), quantity));
						materialAccount.setPreviousAmount(FinancialCommon.sub(materialAccount.getPreviousAmount(), account));
					}else if("1005".equals(billTypeCode) || "1006".equals(billTypeCode)
							|| "1007".equals(billTypeCode) || "1009".equals(billTypeCode)
							|| "1011".equals(billTypeCode)){
						//单据类型为：1005本月国购单据 或 1006 车间返回 或 1007本月盈单或 1009 其他来源 或 1011 海关征税进口 时，把数量与金额加到本月进入上
						materialAccount.setThisInCount(FinancialCommon.add(materialAccount.getThisInCount(), quantity));
						materialAccount.setThisInAmount(FinancialCommon.add(materialAccount.getThisInAmount(), account));
					}else if("1104".equals(billTypeCode) || "1106".equals(billTypeCode)
							|| "1107".equals(billTypeCode) || "1108".equals(billTypeCode)
							|| "1115".equals(billTypeCode)){
						//单据类型为：1104本月盘亏单 或 1106 结转料件退货单 或 1107 其他料件退货单或 1108 其他领用 或 1115海关批准内销 时，把数量与金额减到本月进入上
						materialAccount.setThisInCount(FinancialCommon.sub(materialAccount.getThisInCount(), quantity));
						materialAccount.setThisInAmount(FinancialCommon.sub(materialAccount.getThisInAmount(), account));
					}else{
						continue;
					}
					financialDao.saveOrUpdate(materialAccount);
					financialDao.getHibernateTemplate().flush();
				}
			}
		}
		
		if(!"".equals(hsql_customs_select)){
			//如果没有要查找报关单的条件，测不查报关单
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

					MaterialAccount materialAccount = findMaterialAccount(thisMonthStr, hsName, hsSpec, unit);
					if(impExpType == null){
						continue;
					}else if(impExpType == 0 || impExpType == 1){
						//单据类型或报关单类型为：1003 0  直接进口 或 1004 1 结转进口  时，把数量与金额加到本月进入上
						materialAccount.setThisInCount(FinancialCommon.add(materialAccount.getThisInCount(), quantity));
						materialAccount.setThisInAmount(FinancialCommon.add(materialAccount.getThisInAmount(), account));
					}else if(impExpType == 6){
						//单据类型或报关单类型为：2003 6 退厂返工，把数量与金额减到本月进入上
						materialAccount.setThisInCount(FinancialCommon.sub(materialAccount.getThisInCount(), quantity));
						materialAccount.setThisInAmount(FinancialCommon.sub(materialAccount.getThisInAmount(), account));
					}else{
						continue;
					}
					financialDao.saveOrUpdate(materialAccount);
					financialDao.getHibernateTemplate().flush();
				}
			}
		}
		String hsql_select_product_consumption = "select a from ProductConsumption a where a.accountDate = ? ";
		
		List list = financialDao.find(hsql_select_product_consumption, thisMonthStr);
		if(list != null && !list.isEmpty()){
			for(int i = 0 ; i < list.size() ; i ++){
				ProductConsumption productConsumption = (ProductConsumption)list.get(i);
				MaterialAccount materialAccount = findMaterialAccount(thisMonthStr, productConsumption.getLjName(), productConsumption.getLjSpec(), productConsumption.getLjUnit());
				
				materialAccount.setThisOutCount(FinancialCommon.add(materialAccount.getThisOutCount(), productConsumption.getLjNeedQuantity()));
				financialDao.getHibernateTemplate().flush();
//				materialAccount.setThisOutAmount(FinancialCommon.add(materialAccount.getThisOutAmount(), account));
			}
		}
	}
	//生成 料件加权平均价 报表，再把生成的平均价，更新到各个表上
	public void findAndGenThisMaterialAveragePrice(String thisMonthStr){
		String hsql_select_materialAccount = "select a from MaterialAccount a where a.accountDate = ?";
		List list = financialDao.find(hsql_select_materialAccount, thisMonthStr);
		if(list != null && !list.isEmpty()){
			for(int i = 0 ; i < list.size() ; i++ ){
				MaterialAccount materialAccount = (MaterialAccount) list.get(i);
				
				MaterialAveragePrice materialAveragePrice = new MaterialAveragePrice();
				
				materialAveragePrice.setAccountDate(thisMonthStr);
				materialAveragePrice.setHsName(materialAccount.getHsName());
				materialAveragePrice.setHsSpec(materialAccount.getHsSpec());
				materialAveragePrice.setHsUnit(materialAccount.getHsUnit());
				materialAveragePrice.setPreviousAmount(materialAccount.getPreviousAmount());
				materialAveragePrice.setPreviousCount(materialAccount.getPreviousCount());
				materialAveragePrice.setThisInAmount(materialAccount.getThisInAmount());
				materialAveragePrice.setThisInCount(materialAccount.getThisInCount());
				
				double totalCount = FinancialCommon.add(materialAveragePrice.getPreviousCount(), materialAveragePrice.getThisInCount());
				double totalAmount = FinancialCommon.add(materialAveragePrice.getPreviousAmount(), materialAveragePrice.getThisInAmount());
				
				double averagePrice = 0.00;
				
				if(totalCount != 0.00){
					averagePrice = FinancialCommon.div(totalAmount, totalCount);
				}
				materialAveragePrice.setAveragePrice(averagePrice);
				materialAccount.setAveragePrice(averagePrice);
				
				double outAmount = FinancialCommon.mul(averagePrice, materialAccount.getThisOutCount());
				
				materialAccount.setThisOutAmount(outAmount);
				materialAccount.setBalanceCount(FinancialCommon.sub(FinancialCommon.add(materialAccount.getPreviousCount(), materialAccount.getThisInCount()), materialAccount.getThisOutCount()));
				materialAccount.setBalanceAmount(FinancialCommon.sub(FinancialCommon.add(materialAccount.getPreviousAmount(), materialAccount.getThisInAmount()), materialAccount.getThisOutAmount()));
				
				financialDao.saveOrUpdate(materialAveragePrice);
				financialDao.saveOrUpdate(materialAccount);
				financialDao.getHibernateTemplate().flush();
			}
		}
	}
	public MaterialAccount findMaterialAccount(String monthStr, String hsName, String hsSpec, Unit hsUnit){
		String hsql_find = "select a from MaterialAccount a where a.accountDate = ? and a.hsName = ? and a.hsSpec = ? and a.hsUnit = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(monthStr);
		params.add(hsName);
		params.add(hsSpec);
		params.add(hsUnit);
		
		List list = financialDao.find(hsql_find, params.toArray());
		MaterialAccount materialAccount = null;
		if(list != null && !list.isEmpty()){
			materialAccount = (MaterialAccount)list.get(0);
			if(materialAccount.getPreviousAmount() == null)
				materialAccount.setPreviousAmount(0.00);
			if(materialAccount.getPreviousCount() == null)
				materialAccount.setPreviousCount(0.00);
			if(materialAccount.getThisInAmount() == null)
				materialAccount.setThisInAmount(0.00);
			if(materialAccount.getThisInCount() == null)
				materialAccount.setThisInCount(0.00);
			if(materialAccount.getThisOutAmount() == null)
				materialAccount.setThisOutAmount(0.00);
			if(materialAccount.getThisOutCount() == null)
				materialAccount.setThisOutCount(0.00);
			if(materialAccount.getBalanceAmount() == null)
				materialAccount.setBalanceAmount(0.00);
			if(materialAccount.getBalanceCount() == null)
				materialAccount.setBalanceCount(0.00);
		}else{
			materialAccount = new MaterialAccount();
			materialAccount.setAccountDate(monthStr);
			materialAccount.setHsName(hsName);
			materialAccount.setHsSpec(hsSpec);
			materialAccount.setHsUnit(hsUnit);
			materialAccount.setPreviousAmount(0.00);
			materialAccount.setPreviousCount(0.00);
			materialAccount.setThisInAmount(0.00);
			materialAccount.setThisInCount(0.00);
			materialAccount.setThisOutAmount(0.00);
			materialAccount.setThisOutCount(0.00);
			materialAccount.setBalanceAmount(0.00);
			materialAccount.setBalanceCount(0.00);
		}
		
		return materialAccount;
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
