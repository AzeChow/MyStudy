/*
 * Created on 2004-10-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.BillCustomBillCmpExgInfo;
import com.bestway.bcus.cas.entity.CarryForwardCmpExgInfo;
import com.bestway.bcus.cas.entity.ExgExportInfo;
import com.bestway.bcus.cas.entity.ExgExportInfoBase;
import com.bestway.bcus.cas.entity.ExgExportInfoCustom;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 海关帐 ExgExportInfoLogic 成品出口logic类 checked 2008-10-25
 * 
 * 贺巍于 2009年 12月4日补充注释
 * 
 * @author luosheng
 * 
 */
public class ExgExportInfoLogic {
	private static final Log logger = LogFactory
			.getLog(ExgExportInfoLogic.class);
	/** 海关帐casDao */
	private CasDao casDao = null;
	/** 常用报关代码设置Dao */
	private CommonBaseCodeDao commonBaseCodeDao = null;

	/** get海关帐Dao */
	public CasDao getCasDao() {
		return casDao;
	}

	/** get公共代码Dao */
	public CommonBaseCodeDao getCommonBaseCodeDao() {
		return commonBaseCodeDao;
	}

	/** set公共代码Dao */
	public void setCommonBaseCodeDao(CommonBaseCodeDao commonBaseCodeDao) {
		this.commonBaseCodeDao = commonBaseCodeDao;
	}

	/** set海关帐Dao */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	/** 为了每个公司只能有一个方法在同步运行,并且.. key = company id */
	private Map<String, Boolean> runMap = new Hashtable<String, Boolean>();

	/**
	 * 查询出口成品信息
	 * 
	 * @param conditions
	 *            conditions中包括对单据生效日期的过滤
	 * @return 生效日期过滤后的出口成品信息
	 */
	public List findExgExportInfos(Date beginDate, Date endDate, String taskId,
			Boolean isShowTranFact) {
		String companyId = CommonUtils.getCompany().getId();
		Boolean isRun = runMap.get(companyId);
		if (isRun != null && isRun.booleanValue() == true) {
			throw new RuntimeException("成品年审报表计算正在进行中,请稍后再运行 ！！");
		}
		runMap.put(companyId, true);
		try {
			//
			// key = 报关名称+"/"+规格+"/"+单位名称
			//
			Map<String, Double> f3Map = new HashMap<String, Double>();
			Map<String, Double> f4Map = new HashMap<String, Double>();
			Map<String, Double> f5Map = new HashMap<String, Double>();
			//
			// key :真实的 报关名称+"/"+规格+"/"+单位名称
			//
			// Map<String, String> statCusNameRelationHsMap = new
			// HashMap<String, String>();
			//
			// key = 报关名称+"/"+规格+"/"+单位名称 + "/" +客户代码
			//
			Map<String, Double> carryForwardByCustomerMap = new HashMap<String, Double>();

			if (beginDate == null) {
				beginDate = CommonUtils.getBeginDate(Integer
						.valueOf(CommonUtils.getYear()), 0, 1);
			}
			if (endDate == null) {
				endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
						.getYear()), 11, 31);
			}

			//
			// get thread internal message
			//    	
			ProgressInfo progressInfo = ProcExeProgress.getInstance()
					.getProgressInfo(taskId);
			String clientTipMessageByExg = "CAS 开始查询海关商品大类对应... !!";
			logger.info(clientTipMessageByExg);
			progressInfo.setHintMessage(clientTipMessageByExg);
			// this.initStatCusNameRelationHsMap(statCusNameRelationHsMap);

			clientTipMessageByExg = "CAS 开始查询结转报关进口... !!";
			logger.info(clientTipMessageByExg);
			progressInfo.setHintMessage(clientTipMessageByExg);
			this.initF3(beginDate, endDate, f3Map, carryForwardByCustomerMap);

			clientTipMessageByExg = "CAS 开始查询已结转未送货和未结转已送货... !!";
			logger.info(clientTipMessageByExg);
			progressInfo.setHintMessage(clientTipMessageByExg);
			this.initF4AndF5(beginDate, endDate, f4Map, f5Map,
					carryForwardByCustomerMap);

			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("and", null, "billMaster.validDate",
					">=", beginDate, null));
			conditions.add(new Condition("and", null, "billMaster.validDate",
					"<=", endDate, null));

			conditions.add(new Condition("and", null, "billMaster.isValid",
					"=", Boolean.valueOf(true), null));

			conditions.add(new Condition("and", null, "hsName is not null ",
					null, null, null));

			String productBillDetail = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);// 成品明细表

			String selects = "select sum(a.hsAmount),sum(a.money),a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name  ";
			String groupBy = "group by a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name ";
			String leftOuter = " left join a.hsUnit	";
			// 成品
			clientTipMessageByExg = "CAS 开始查询所有单据成品... !!";
			progressInfo.setHintMessage(clientTipMessageByExg);
			logger.info(clientTipMessageByExg);
			List listBillDetailProduct = casDao.commonSearch(selects,
					productBillDetail, conditions, groupBy, leftOuter);

			List<FirstSumInfo> firstSumInfoList = new ArrayList<FirstSumInfo>();

			for (int i = 0; i < listBillDetailProduct.size(); i++) {
				Object[] values = (Object[]) listBillDetailProduct.get(i);
				FirstSumInfo firstSumInfo = new FirstSumInfo(values);
				firstSumInfoList.add(firstSumInfo);
			}
			
			//这里进行总计算
			List<ExgExportInfo> resultList = getResultList(firstSumInfoList,
					f3Map, f4Map, f5Map, isShowTranFact);
			//
			// 删除当年度的所有海关帐
			//
			clientTipMessageByExg = "CAS 开始删除当年度的所有海关帐... !!";
			progressInfo.setHintMessage(clientTipMessageByExg);
			logger.info(clientTipMessageByExg);
			this.casDao.deleteExgExportInfo(CommonUtils.getYear());

			for (int i = 0; i < resultList.size(); i++) {
				ExgExportInfo item = (ExgExportInfo) resultList.get(i);
				item.setCompany(CommonUtils.getCompany());
				this.casDao.getHibernateTemplate().save(item);
			}
			return resultList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			runMap.put(companyId, false);
		}
	}

	// /**
	// * 初始化大类和实际的对应的信息用于一对多的结转计算
	// *
	// */
	// private void initStatCusNameRelationHsMap(
	// Map<String, String> statCusNameRelationHsMap) {
	// List<StatCusNameRelationHsn> listExistStatCusNameRelationHsn =
	// this.casDao
	// .findStatCusNameRelationHsn(MaterielType.FINISHED_PRODUCT);
	// for (int i = 0, n = listExistStatCusNameRelationHsn.size(); i < n; i++) {
	// StatCusNameRelationHsn hsn = listExistStatCusNameRelationHsn.get(i);
	// StatCusNameRelation sr = hsn.getStatCusNameRelation();
	//
	// String hsName = hsn.getCusName() == null ? "" : hsn.getCusName();
	// String hsSpec = hsn.getCusSpec() == null ? "" : hsn.getCusSpec();
	// String unitName = hsn.getCusUnit() == null ? "" : hsn.getCusUnit()
	// .getName();
	// unitName = unitName == null ? "" : unitName;
	// String key = hsName + "/" + hsSpec + "/" + unitName;
	//
	// //
	// // value = 报关名称+"/"+规格+"/"+单位名称
	// //
	// String tenHsName = sr.getCusName() == null ? "" : sr.getCusName();
	// String tenHsSpec = sr.getCusSpec() == null ? "" : sr.getCusSpec();
	// String tenUnitName = sr.getCusUnit() == null ? "" : sr.getCusUnit()
	// .getName();
	// tenUnitName = tenUnitName == null ? "" : tenUnitName;
	// String value = tenHsName + "/" + tenHsSpec + "/" + tenUnitName;
	//
	// if (statCusNameRelationHsMap.get(key) == null) {
	// statCusNameRelationHsMap.put(key, value);
	// }
	// }
	// }

	/**
	 * 3． 结转报关进口: 进口类型 为 “ 料件转厂”、贸易方式为 “来/进料深加工”且进口日期在本帐目年度的报关单数量的累加
	 * 
	 * 生成 以 报关名称+"/"+规格+"/"+单位为 key 的 carryForwardMap 生成 以 报关名称+"/"+规格+"/"+单位 +
	 * "/" +客户代码为 key 的 carryForwardByCustomerMap
	 */
	private void initF3(Date beginDate, Date endDate,
			Map<String, Double> f3Map,
			Map<String, Double> carryForwardByCustomerMap) {
		//
		// 以名称+规格+单位,客户来分组进行统计
		//
		String selects = "select sum(a.commAmount),a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.customer.code  ";
		String groupBy = "group by a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.customer.code ";
		String leftOuter = " left join a.unit left join a.baseCustomsDeclaration.customer	";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.TRANSFER_FACTORY_EXPORT, null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpDate", ">=", beginDate, null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpDate", "<=", endDate, null));

		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.effective", "=", true, null));// 生效
		conditions
				.add(new Condition("and", null,
						"baseCustomsDeclaration.customer", " is not null ",
						null, null));// 客户供应商为空的不要

		List listCustomsDeclarationCommInfo = casDao.commonSearch(selects,
				"CustomsDeclarationCommInfo", conditions, groupBy, leftOuter);
		makeMap(f3Map, carryForwardByCustomerMap,
				listCustomsDeclarationCommInfo);
		List listBcsCustomsDeclarationCommInfo = casDao
				.commonSearch(selects, "BcsCustomsDeclarationCommInfo",
						conditions, groupBy, leftOuter);
		makeMap(f3Map, carryForwardByCustomerMap,
				listBcsCustomsDeclarationCommInfo);
		List listDzscCustomsDeclarationCommInfo = casDao.commonSearch(selects,
				"DzscCustomsDeclarationCommInfo", conditions, groupBy,
				leftOuter);
		makeMap(f3Map, carryForwardByCustomerMap,
				listDzscCustomsDeclarationCommInfo);
	}

	/**
	 * 生成结转 map
	 * 
	 * @param carryForwardMap
	 *            结转单据映射
	 * @param list
	 */
	private void makeMap(Map<String, Double> carryForwardMap,
			Map<String, Double> carryForwardByCustomerMap, List list) {
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			// 以 报关名称规格+单位为key的
			Double commAmount = (Double) objs[0] == null ? 0.0
					: (Double) objs[0];
			String name = (String) objs[1] == null ? "" : (String) objs[1];
			String spec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String customerCode = (String) objs[4] == null ? ""
					: (String) objs[4];
			String key = name + "/" + spec + "/" + unitName;
			// //
			// // 转换成大类的对应(用于一对多的方法)
			// //
			// if (statCusNameRelationHsMap.get(key) != null) {
			// key = statCusNameRelationHsMap.get(key);
			// }

			if (carryForwardMap.get(key) == null) {
				carryForwardMap.put(key, commAmount);
			} else {
				Double commA = carryForwardMap.get(key);
				commA += commAmount;
				carryForwardMap.put(key, commA);
			}
			String hasCustomerKey = key + "/" + customerCode;
			if (carryForwardByCustomerMap.get(hasCustomerKey) == null) {
				carryForwardByCustomerMap.put(hasCustomerKey, commAmount);
			} else {
				Double commA = carryForwardByCustomerMap.get(hasCustomerKey);
				commA += commAmount;
				carryForwardByCustomerMap.put(hasCustomerKey, commA);
			}
		}
	}

	/**
	 * 4． 已结转未收货: 当相同客户名称的 （ “结转出口” 单据折算报关数量的累加 －“结转成品退货单” 单据折算报关数量的累加 <
	 * “结转报关出口”数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF3Map 已结转未收货
	 * 
	 * 5． 未结转已收货: 当相同客户名称的 （ “结转出口” 单据折算报关数量的累加 －“结转成品退货单” 单据折算报关数量的累加 >“结转报关出口”
	 * 数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF4Map 未结转已收货
	 */
	private void initF4AndF5(Date beginDate, Date endDate,
			Map<String, Double> f4Map, Map<String, Double> f5Map,
			Map<String, Double> carryForwardByCustomerMap) {
		//
		// 以 报关名+称规格+单位+客户 为key的 map
		// f4Map ,f5Map
		//
		//
		// 以名称+规格+单位,客户来分组进行统计
		//
		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,"
				+ "a.billMaster.scmCoc.code,a.billMaster.billType.code ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,"
				+ "a.billMaster.scmCoc.code,a.billMaster.billType.code ";
		String leftOuter = " left join a.hsUnit left join a.billMaster.scmCoc	";
		List<Condition> conditions = new ArrayList<Condition>();

		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2102", ""));// 结转出口
		conditions.add(new Condition("or", "", "billMaster.billType.code", "=",
				"2009", ")"));
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));

		List carryForwardList = casDao.commonSearch(selects,
				"BillDetailProduct", conditions, groupBy, leftOuter);

		//
		// key = 报关名称+"/"+规格+"/"+单位名称 + "/" +客户代码
		//
		Map<String, Double> carryForwardBackMap = new HashMap<String, Double>();
		for (int i = 0; i < carryForwardList.size(); i++) {
			Object[] objs = (Object[]) carryForwardList.get(i);
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String scmCocCode = (String) objs[4] == null ? ""
					: (String) objs[4];
			String typeCode = (String) objs[5];
			String key = hsName + "/" + hsSpec + "/" + unitName + "/"
					+ scmCocCode;
			if (typeCode.equalsIgnoreCase("2009")) {// 结转成品退货单
				if (carryForwardBackMap.get(key) == null) {
					carryForwardBackMap.put(key, hsAmount);
				} else {
					Double amount = carryForwardBackMap.get(key) + hsAmount;
					carryForwardBackMap.put(key, amount);
				}
			}
		}

		//
		// 初始化结转数 (报关单)
		//
		// this.initF3ByCustomer(beginDate, endDate, null,
		// carryForwardByCustomerMap);
		// this.initF3(beginDate, endDate,new HashMap(),
		// carryForwardByCustomerMap);

		for (int i = 0; i < carryForwardList.size(); i++) {
			Object[] objs = (Object[]) carryForwardList.get(i);
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String scmCocCode = (String) objs[4] == null ? ""
					: (String) objs[4];
			String typeCode = (String) objs[5];

			if (typeCode.equalsIgnoreCase("2009")) { // 结转成品退货单
				continue;
			}

			String noCustomerKey = hsName + "/" + hsSpec + "/" + unitName;
			String hasCustomerKey = noCustomerKey + "/" + scmCocCode;
			System.out.println("------------------key=" + noCustomerKey);
			//
			// 退货数量
			//
			Double backAmount = carryForwardBackMap.get(hasCustomerKey) == null ? 0.0
					: carryForwardBackMap.get(hasCustomerKey);
			System.out.println("-------------------backAmount=" + backAmount);
			hsAmount -= backAmount;
			System.out.println("-------------------hsAmount=" + hsAmount);
			//
			// 报关单报关数量
			//
			Double customAmount = carryForwardByCustomerMap.get(hasCustomerKey) == null ? 0.0
					: carryForwardByCustomerMap.get(hasCustomerKey);
			System.out.println("-------------------customAmount="
					+ customAmount);
			//
			// 报关数量-收货数量
			//
			double tempAmount = customAmount - hsAmount;

			System.out.println("-------------------tempAmount=" + tempAmount);
			if (tempAmount > 0) {
				System.out.println("customAmount=" + customAmount);
				System.out.println("hsAmount=" + hsAmount);
				System.out.println("tempAmount=" + tempAmount);
				System.out.println("结果大于零");
				// 已结转未送货
				// tempAmount = -tempAmount;
				if (f4Map.get(noCustomerKey) == null) {
					f4Map.put(noCustomerKey, tempAmount);
				} else {
					Double tempA = f4Map.get(noCustomerKey);
					tempA += tempAmount;
					f4Map.put(noCustomerKey, tempA);
				}
			} else if (tempAmount < 0) {
				System.out.println("customAmount=" + customAmount);
				System.out.println("hsAmount=" + hsAmount);
				System.out.println("tempAmount=" + tempAmount);
				System.out.println("结果小于零");
				// 未结转已送货
				tempAmount = -tempAmount;
				if (f5Map.get(noCustomerKey) == null) {
					f5Map.put(noCustomerKey, tempAmount);
				} else {
					Double tempA = f5Map.get(noCustomerKey);
					tempA += tempAmount;
					f5Map.put(noCustomerKey, tempA);
				}
			} else if (tempAmount == 0) {
				System.out.println("customAmount=" + customAmount);
				System.out.println("hsAmount=" + hsAmount);
				System.out.println("tempAmount=" + tempAmount);
				System.out.println("结果等于零");
				f4Map.put(noCustomerKey, 0.0);
			}
		}
	}

	/**
	 * 统计产成品信息 主要用于加工贸易产品流向情况表
	 * 
	 * @param firstSumInfoList
	 *            最初统计信息
	 * @return 出口成品信息
	 */
	public List<ExgExportInfo> getResultList(
			List<FirstSumInfo> firstSumInfoList, Map<String, Double> f3Map,
			Map<String, Double> f4Map, Map<String, Double> f5Map,
			Boolean isShowTranFact) {
		HashMap<String, ExgExportInfo> resultHash = new HashMap<String, ExgExportInfo>();
		for (int i = 0; i < firstSumInfoList.size(); i++) {
			FirstSumInfo firstSumInfo = firstSumInfoList.get(i);
			if (firstSumInfo == null) {
				continue;
			}
			ExgExportInfo item = null;
			Double hsAmount = firstSumInfo.getHsAmount() == null ? 0.0
					: firstSumInfo.getHsAmount();
			String typeCode = firstSumInfo.getTypeCode() == null ? ""
					: firstSumInfo.getTypeCode();
			String hsName = firstSumInfo.getHsName() == null ? ""
					: firstSumInfo.getHsName();
			String hsUnitName = firstSumInfo.getHsUnitName() == null ? ""
					: firstSumInfo.getHsUnitName();
			String hsSpec = firstSumInfo.getHsSpec() == null ? ""
					: firstSumInfo.getHsSpec();
			Double money = firstSumInfo.getMoney() == null ? 0.0 : firstSumInfo
					.getMoney();

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			if (resultHash.get(key) != null) {
				item = (ExgExportInfo) resultHash.get(key);
			} else {
				item = new ExgExportInfo();
				item.setYear(CommonUtils.getYear());
				item.setCustomName(true);
				item.setPtName(hsName);
				item.setPtSpec(hsSpec);
				item.setPtUnitName(hsUnitName);
				resultHash.put(key, item);
			}
			//
			// 报关总金额 老海帐是这样子的
			// 2101直接出口 + 2102结转出口+ 2106海关批准内销+ 2107其他内销+ 2108其他处理 + 2110 受托加工返回
			// - 2001 成品起初单
			// 
			if (typeCode.equals("2101") || typeCode.equals("2102")
					|| typeCode.equals("2106") || typeCode.equals("2107")
					|| typeCode.equals("2108") || typeCode.equals("2110")) {
				item.setMoney(item.getMoney() + money);
			} else if (typeCode.equals("2001")) {
				item.setMoney(item.getMoney() - money);
			}
			
			//wss:2010.05.04新加(为了方便计算    从报关单中统计金额
			if (typeCode.equals("2106") || typeCode.equals("2107")
					|| typeCode.equals("2108")) {
				item.setNoCustomMoney(item.getNoCustomMoney() + money);
			} else if (typeCode.equals("2001")) {
				item.setNoCustomMoney(item.getNoCustomMoney() - money);
			}
			
			
			//
			// 1．期末库存: 各类型成品进仓单据折算报关数量的累加－各类型成品出单据折算报关数量的累加
			//
			if (typeCode.equals("2001") // 2001 成品期初单
					|| typeCode.equals("2002")// 2002 车间入库
					|| typeCode.equals("2003")// 2003 退厂返工
					|| typeCode.equals("2004")// 2004 成品盘盈单
					|| typeCode.equals("2005")// 2005 成品转仓入库
					|| typeCode.equals("2007")// 2007 受托加工车间入库
					|| typeCode.equals("2008")// 2008 其他成品退货单
					|| typeCode.equals("2009")// 2009 结转成品退货单
					|| typeCode.equals("2010")// 2010 受托加工退回成品
			// || typeCode.equals("2011")//2011 已交货未结转期初单
			// || typeCode.equals("2012")//2012 已交货未结转期初单
			) { // 入库
				item.setF1(item.getF1() + hsAmount);
			}
			if (typeCode.equals("2101") // 2101 直接出口
					|| typeCode.equals("2102")// 2102 结转出口
					|| typeCode.equals("2103")// 2103 返回车间
					|| typeCode.equals("2104")// 2104 返工复出
					|| typeCode.equals("2105")// 2105 成品盘亏单
					|| typeCode.equals("2106")// 2106 海关批准内销
					|| typeCode.equals("2107")// 2107 其他内销
					|| typeCode.equals("2108")// 2108 其他处理
					|| typeCode.equals("2109")// 2109 成品转仓出库
					|| typeCode.equals("2110")// 2110 受托加工返回
			) { // 出库
				item.setF1(item.getF1() - hsAmount);
			}

			if (typeCode.equals("2101")) {// 2.直接出口
				item.setF2(item.getF2() + hsAmount);
			}
			//
			// 3. 结转报关出口: 进口类型 为 “ 转厂出口” 及贸易方式 为 “来/进料深加工”
			// 且出口日期在本帐目年度内的报关单数量的累加．
			//
			item.setF3(f3Map.get(key) == null ? 0.0 : f3Map.get(key));
			//
			// 4． 已结转未收货: 当相同客户名称的（ “结转出口” 的单据折算报关数量的累加 －“结转成品退货单” 的单据折算报关数量的累加>
			// “结转报关出口”数量）时,各客户两者差额值的和。
			//
			if (f4Map.get(key) == null && f5Map.get(key) == null) {
				item.setF4(f3Map.get(key) == null ? 0.0 : f3Map.get(key));
				// System.out
				// .println("f3Map.get(key)=" + f3Map.get(key) == null ? 0.0
				// : f3Map.get(key));
			} else {
				item.setF4(f4Map.get(key) == null ? 0.0 : f4Map.get(key));
				// System.out
				// .println("f4Map.get(key)=" + f4Map.get(key) == null ? 0.0
				// : f4Map.get(key));
			}
			// item.setF4((f4Map.get(key) == null ? 0.0 : f4Map.get(key))==0 ?
			// );

			//
			// 5. 未结转已收货: 当相同客户名称的（ “结转出口” 的单据折算报关数量的累加 －“结转成品退货单” 的单据折算报关数量的累加
			// < “结转报关出口” 数量）时, 两者的差额值各客户两者差额值的和。.
			//
			item.setF5(f5Map.get(key) == null ? 0.0 : f5Map.get(key));

			// System.out.println("f4Map.get(key)="+f4Map.get(key));

			// System.out.println("f5Map.get(key)="+f5Map.get(key));

			if (typeCode.equals("2106")) {// 6.海关批准内销
				item.setF6(item.getF6() + hsAmount);
			}
			if (typeCode.equals("2107")) {// 7.其它内销
				item.setF7(item.getF7() + hsAmount);
			}
			//
			// 8．受托加工: “2110 受托加工返回” 单据折算报关数量的累加 － “2010 受托加工退回成品” 的单据折算报关数量的累加。
			// 
			if (typeCode.equals("2110") 
					|| typeCode.equals("2010")
					|| typeCode.equals("2112")//wss:2010.04.19新加类型
					|| typeCode.equals("2016")) {//wss:2010.04.19新加类型
				if (typeCode.equals("2110") 
						|| typeCode.equals("2112")) { // 2110 受托加工返回   2112受托加工成品出库 
					item.setF8(item.getF8() + hsAmount);
				}
				if (typeCode.equals("2010") 
						|| typeCode.equals("2016")) { // 2010 受托加工退回成品    2016受托加工成品返修
					item.setF8(item.getF8() - hsAmount);
				}
			}
			if (typeCode.equals("2108")) {// 9.其它处理
				item.setF9(item.getF9() + hsAmount);
			}
			//
			// 2001 成品起初单
			//
			if (typeCode.equals("2001")) {// 10.期初库存
				item.setF10(item.getF10() + hsAmount);
			}
			// 2011,2012 已交货未结转期初单 已结转未交货期初单
			if (isShowTranFact) {
				if (typeCode.equals("2011")) {
					item.setMateriel2011(item.getMateriel2011() + hsAmount);
				} else if (typeCode.equals("2012")) {
					item.setMateriel2012(item.getMateriel2012() + hsAmount);
				}
			}
		}

		Iterator iterator = resultHash.values().iterator();
		while (iterator.hasNext()) {
			ExgExportInfo item = (ExgExportInfo) iterator.next();
			// 转厂期初
			Double tranFact = (item.getMateriel2011() == null ? 0.0 : item
					.getMateriel2011())
					- (item.getMateriel2012() == null ? 0.0 : item
							.getMateriel2012());

			if (isShowTranFact) {
				if (tranFact != 0) {
					item.setF12(item.getF12()
							+ (tranFact > 0 ? "已交货未结转" : "已结转未交货")
							+ (tranFact > 0 ? tranFact : -tranFact) + " ; ");
				}
			}
		}

		List<ExgExportInfo> lsResult = new ArrayList<ExgExportInfo>();
		lsResult.addAll(resultHash.values());
		return lsResult;
	}

	/**
	 * 最初的统计信息
	 */
	public class FirstSumInfo {
		private Double hsAmount = Double.valueOf(0);
		private Double money = Double.valueOf(0); // 金额
		private String typeCode;
		private String hsName;
		private String hsSpec;
		private String hsUnitName;

		/**
		 * 最初的统计信息
		 */
		public FirstSumInfo() {

		}

		/**
		 * 最初的统计信息
		 */
		public FirstSumInfo(Object[] values) {
			hsAmount = (Double) values[0];
			money = (Double) values[1];
			typeCode = (String) values[2];
			hsName = (String) values[3];
			hsSpec = (String) values[4];
			hsUnitName = (String) values[5];
		}

		/**
		 * 取得工厂数量
		 * 
		 * @return ptAmount 工厂数量.
		 */
		public Double getHsAmount() {
			return hsAmount;
		}

		/**
		 * 设置工厂数量
		 * 
		 * @param ptAmount
		 *            工厂数量
		 */
		public void setHsAmount(Double ptAmount) {
			this.hsAmount = ptAmount;
		}

		/**
		 * 取得金额
		 * 
		 * @return money 金额
		 */
		public Double getMoney() {
			return money;
		}

		/**
		 * 设置金额
		 * 
		 * @param money
		 *            金额
		 */
		public void setMoney(Double money) {
			this.money = money;
		}

		/**
		 * 取得工厂商品名称
		 * 
		 * @return ptName 工厂商品名称.
		 */
		public String getHsName() {
			return hsName;
		}

		/**
		 * 取得报关商品名称
		 * 
		 * @param ptName
		 *            报关商品名称
		 */
		public void setHsName(String ptName) {
			this.hsName = ptName;
		}

		/**
		 * 取得工厂商品规格
		 * 
		 * @return ptSpec 工厂商品规格.
		 */
		public String getHsSpec() {
			return hsSpec;
		}

		/**
		 * 设置工厂商品规格
		 * 
		 * @param ptSpec
		 *            工厂商品规格
		 */
		public void setHsSpec(String ptSpec) {
			this.hsSpec = ptSpec;
		}

		/**
		 * 取得报关单位
		 * 
		 * @return hsUnitName 报关单位.
		 */
		public String getHsUnitName() {
			return hsUnitName;
		}

		/**
		 * @param ptUnitName
		 *            工厂单位
		 */
		public void setHsUnitName(String ptUnitName) {
			this.hsUnitName = ptUnitName;
		}

		/**
		 * 取得单据类型代码
		 * 
		 * @return typeCode 单据类型代码.
		 */
		public String getTypeCode() {
			return typeCode;
		}

		/**
		 * 设置单据类型代码
		 * 
		 * @param typeCode
		 *            单据类型代码
		 */
		public void setTypeCode(String typeCode) {
			this.typeCode = typeCode;
		}
	}

	/**
	 * ========================================================================
	 * ============================ // 以下是报关单出口与海关帐单据中的直接出口单据相对应
	 * //===============
	 * ==========================================================
	 * ===========================
	 */

	/**
	 * 单据报关单对比
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 年度的单据报关单对比
	 */
	private Map<String, BillCustomBillCmpExgInfo> findBillExgInfos(
			Date beginDate, Date endDate) {
		HashMap<String, BillCustomBillCmpExgInfo> htResult = new HashMap<String, BillCustomBillCmpExgInfo>();
		List<Condition> conditions = new ArrayList<Condition>();

		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2101", ")"));
		// 年度条件
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));

		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec, a.hsUnit.name  ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name ";
		String leftOuter = " left join a.hsUnit ";

		List billDetails = casDao.commonSearch(selects, "BillDetailProduct",
				conditions, groupBy, leftOuter);

		System.out.println("billDetails.size()==" + billDetails.size());
		for (int i = 0; i < billDetails.size(); i++) {
			Object[] billDetail = (Object[]) billDetails.get(i);
			Double hsAmount = billDetail[0] == null ? 0.0
					: (Double) billDetail[0];
			String hsName = billDetail[1] == null ? "" : (String) billDetail[1];
			String hsSpec = billDetail[2] == null ? "" : (String) billDetail[2];
			String hsUnitName = billDetail[3] == null ? ""
					: (String) billDetail[3];

			String key = hsName + "/" + hsSpec + "/" + hsUnitName;

			BillCustomBillCmpExgInfo cmpInfo = (BillCustomBillCmpExgInfo) htResult
					.get(key);
			if (cmpInfo == null) {
				cmpInfo = new BillCustomBillCmpExgInfo();
				cmpInfo.setPtName(hsName);
				cmpInfo.setPtSpec(hsSpec);
				cmpInfo.setPtUnitName(hsUnitName);
				htResult.put(key, cmpInfo);
			}
			cmpInfo.setF2(cmpInfo.getF2() + hsAmount);
		}
		return htResult;
	}

	/**
	 * 单据报关单对比
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 海关帐中存在的有记录的单据与报关单比对
	 */
	public List<BillCustomBillCmpExgInfo> findBillCustomBillCmpExgInfos(
			Date beginDate, Date endDate) {

		if (beginDate == null) {
			beginDate = CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
					.getYear()), 0, 1);
		}
		if (endDate == null) {
			endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
					.getYear()), 11, 31);
		}
		//
		// 查找单据里面的折算报关数量
		//
		Map<String, BillCustomBillCmpExgInfo> mapResult = this
				.findBillExgInfos(beginDate, endDate);
		//
		// 海关帐单据中没有记录根本不用统计了
		//
		if (mapResult.size() <= 0) {
			return new ArrayList<BillCustomBillCmpExgInfo>();
		}

		List<Condition> conditions = new ArrayList<Condition>();
		// //////////////////////
		// 查找报关单据里面的报关数量
		// //////////////////////
		//
		// 以名称+规格+单位,来分组进行统计
		//
		String selects = "select sum(a.commAmount),a.commName,a.commSpec, a.unit.name ";
		String groupBy = "group by a.commName,a.commSpec, a.unit.name ";

		// 这里并没有用贸易方式来区分
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.DIRECT_EXPORT, null));// 直接出口单据
		// 生效
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.effective", "=", true, null));
		// 进出口日期,申报日期???
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpDate", ">=", beginDate, null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpDate", "<=", endDate, null));

		List listCustomsDeclarationCommInfo = casDao.commonSearch(selects,
				"CustomsDeclarationCommInfo", conditions, groupBy, "");
		List bcslistCustomsDeclarationCommInfo = casDao.commonSearch(selects,
				"BcsCustomsDeclarationCommInfo", conditions, groupBy, "");
		List dzsclistCustomsDeclarationCommInfo = casDao.commonSearch(selects,
				"DzscCustomsDeclarationCommInfo", conditions, groupBy, "");

		if (bcslistCustomsDeclarationCommInfo != null
				&& bcslistCustomsDeclarationCommInfo.size() != 0) {
			listCustomsDeclarationCommInfo
					.addAll(bcslistCustomsDeclarationCommInfo);
		}
		if (dzsclistCustomsDeclarationCommInfo != null
				&& dzsclistCustomsDeclarationCommInfo.size() != 0) {
			listCustomsDeclarationCommInfo
					.addAll(dzsclistCustomsDeclarationCommInfo);
		}
		List<BillCustomBillCmpExgInfo> resultList = new ArrayList<BillCustomBillCmpExgInfo>();

		for (int i = 0; i < listCustomsDeclarationCommInfo.size(); i++) {
			Object[] objs = (Object[]) listCustomsDeclarationCommInfo.get(i);
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String key = hsName + "/" + hsSpec + "/" + unitName;

			BillCustomBillCmpExgInfo temp = mapResult.get(key);
			//
			// 不统计海关单据中不存在的
			//
			if (temp == null) {
				continue;
			}
			temp.setF2CustomNum(CommonUtils.getDoubleExceptNull(temp
					.getF2CustomNum())
					+ hsAmount);
		}
		resultList.addAll(mapResult.values());
		return resultList;
	}

	/**
	 * 结转成品对比
	 * 
	 * @param conditions
	 *            查询条件
	 * @param scmCoc
	 *            客户/供应商
	 * @return 按客户分的结转成品比对
	 */
	public List<CarryForwardCmpExgInfo> findCarryForwardCmpExgInfos(
			Date beginDate, Date endDate, ScmCoc scmCoc) {
		if (beginDate == null) {
			beginDate = CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
					.getYear()), 0, 1);
		}
		if (endDate == null) {
			endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
					.getYear()), 11, 31);
		}
		//
		// 以名称+规格+单位,客户来分组进行统计
		//
		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,"
				+ " a.billMaster.scmCoc.code,a.billMaster.scmCoc.name,a.billMaster.billType.code ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.scmCoc.code,"
				+ " a.billMaster.scmCoc.name,a.billMaster.billType.code ";
		String leftOuter = " left join a.hsUnit left join a.billMaster.scmCoc ";

		List<Condition> conditions = new ArrayList<Condition>();

		if (scmCoc != null) {
			conditions.add(new Condition("and", null, "billMaster.scmCoc.id",
					"=", scmCoc.getId(), null));
		}
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		// 2009 结转成品退货单
		// 2102 结转出口
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2102", ""));// 结转出口
		conditions.add(new Condition("or", "", "billMaster.billType.code", "=",
				"2009", ")")); // 2009 结转成品退货单
		// 年度条件
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));

		List carryForwardList = casDao.commonSearch(selects,
				"BillDetailProduct", conditions, groupBy, leftOuter);
		if (carryForwardList.size() <= 0) {
			return new ArrayList<CarryForwardCmpExgInfo>();
		}
		//
		// key = 报关名称+"/"+规格+"/"+单位名称 + "/" +客户代码
		//
		Map<String, Double> carryForwardByCustomerMap = new HashMap<String, Double>();
		//
		// 初始化结转数 (报关单)按客户来分的
		//
		this.initF3ByCustomer(beginDate, endDate, scmCoc,
				carryForwardByCustomerMap);
		//
		// 生成中间对象
		//
		HashMap<String, CarryForwardCmpExgInfo> htResult = new HashMap<String, CarryForwardCmpExgInfo>();
		for (int i = 0; i < carryForwardList.size(); i++) {
			Object[] objs = (Object[]) carryForwardList.get(i);
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String scmCocCode = (String) objs[4] == null ? ""
					: (String) objs[4];
			String scmCocName = (String) objs[5] == null ? ""
					: (String) objs[5];
			String typeCode = (String) objs[6] == null ? "" : (String) objs[6];

			String key = hsName + "/" + hsSpec + "/" + unitName + "/"
					+ scmCocCode;

			CarryForwardCmpExgInfo cmpInfo = (CarryForwardCmpExgInfo) htResult
					.get(key);
			if (cmpInfo == null) {
				cmpInfo = new CarryForwardCmpExgInfo();
				cmpInfo.setPtName(hsName);
				cmpInfo.setPtSpec(hsSpec);
				cmpInfo.setPtUnitName(unitName);
				cmpInfo.setCustomerName(scmCocName);
				htResult.put(key, cmpInfo);
			}
			if (typeCode.equals("2102")) { // 2102 结转出口
				cmpInfo.setHsAmount2102(cmpInfo.getHsAmount2102() + hsAmount);
			} else {// 2009 结转成品退货单
				cmpInfo.setHsAmount2009(cmpInfo.getHsAmount2009() + hsAmount);
			}
			cmpInfo.setCustomNum(carryForwardByCustomerMap.get(key) == null ? 0
					: carryForwardByCustomerMap.get(key));
			System.out.println("-----------------------key:" + key);
			System.out.println("-----------------------customNum:"
					+ cmpInfo.getCustomNum());
		}
		List<CarryForwardCmpExgInfo> resultList = new ArrayList<CarryForwardCmpExgInfo>();
		resultList.addAll(htResult.values());
		return resultList;
	}

	/**
	 * 3． 结转报关进口: 进口类型 为 “ 料件转厂”、贸易方式为 “来/进料深加工”且进口日期在本帐目年度的报关单数量的累加
	 * 
	 * 生成 以 报关名称+"/"+规格+"/"+单位为 key 的 carryForwardMap 生成 以 报关名称+"/"+规格+"/"+单位 +
	 * "/" +客户代码为 key 的 carryForwardByCustomerMap
	 * 
	 * @param scmCoc
	 *            客户
	 */
	private void initF3ByCustomer(Date beginDate, Date endDate, ScmCoc scmCoc,
			Map<String, Double> carryForwardByCustomerMap) {
		//
		// 初始化大类对应关系
		//
		//
		// key :真实的 报关名称+"/"+规格+"/"+单位名称
		//
		Map<String, String> statCusNameRelationHsMap = new HashMap<String, String>();

		// this.initStatCusNameRelationHsMap(statCusNameRelationHsMap);
		//
		// 以 报关名称规格+单位为key的 map
		//
		Map<String, Double> f3Map = new HashMap<String, Double>();
		//
		// 以名称+规格+单位,客户来分组进行统计
		//
		String selects = "select sum(a.commAmount),a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.customer.code  ";
		String groupBy = "group by a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.customer.code ";
		String leftOuter = " left join a.unit left join a.baseCustomsDeclaration.customer ";
		List<Condition> conditions = new ArrayList<Condition>();
		if (scmCoc != null) {
			conditions.add(new Condition("and", null, "billMaster.scmCoc.id",
					"=", scmCoc.getId(), null));
		}
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.TRANSFER_FACTORY_EXPORT, null));// 转厂出口
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpDate", ">=", beginDate, null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpDate", "<=", endDate, null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.effective", "=", true, null));// 生效
		conditions
				.add(new Condition("and", null,
						"baseCustomsDeclaration.customer", " is not null ",
						null, null));// 客户供应商不为空的不要
		// 这个条件暂不叛
		// conditions.add(new Condition("and", null,
		// "baseCustomsDeclaration.tradeMode.code", "=", "1427", null));// 出料加工

		List listCustomsDeclarationCommInfo = casDao.commonSearch(selects,
				"CustomsDeclarationCommInfo", conditions, groupBy, leftOuter);
		makeMap(f3Map, carryForwardByCustomerMap,
				listCustomsDeclarationCommInfo);
		List listBcsCustomsDeclarationCommInfo = casDao
				.commonSearch(selects, "BcsCustomsDeclarationCommInfo",
						conditions, groupBy, leftOuter);
		makeMap(f3Map, carryForwardByCustomerMap,
				listBcsCustomsDeclarationCommInfo);
		List listDzscCustomsDeclarationCommInfo = casDao.commonSearch(selects,
				"DzscCustomsDeclarationCommInfo", conditions, groupBy,
				leftOuter);
		makeMap(f3Map, carryForwardByCustomerMap,
				listDzscCustomsDeclarationCommInfo);
	}

	/**
	 * 重新载入海关资料(料件来自自定资料)
	 * 
	 */
	public void copyExgExportInfoCustom() {
		//
		// 删除当年度的所有海关帐
		//
		this.casDao.deleteExgExportInfoCustom(CommonUtils.getYear());
		//
		// 分页查询并修改
		//
		int index = 0;
		int length = 1000;
		while (true) {
			List tempBillDetails = this.casDao.findExgExportInfo(index, length,
					CommonUtils.getYear());
			for (int i = 0; i < tempBillDetails.size(); i++) {
				ExgExportInfo exgExportInfo = (ExgExportInfo) tempBillDetails
						.get(i);
				ExgExportInfoCustom exgExportInfoCustom = new ExgExportInfoCustom();
				exgExportInfoCustom.setCompany(exgExportInfo.getCompany());
				exgExportInfoCustom.setYear(exgExportInfo.getYear());
				exgExportInfoCustom.setMoney(exgExportInfo.getMoney());
				exgExportInfoCustom.setPtName(exgExportInfo.getPtName());
				exgExportInfoCustom.setPtSpec(exgExportInfo.getPtSpec());
				exgExportInfoCustom
						.setPtUnitName(exgExportInfo.getPtUnitName());
				exgExportInfoCustom.setF1(exgExportInfo.getF1());
				exgExportInfoCustom.setF2(exgExportInfo.getF2());
				exgExportInfoCustom.setF3(exgExportInfo.getF3());
				exgExportInfoCustom.setF4(exgExportInfo.getF4());
				exgExportInfoCustom.setF5(exgExportInfo.getF5());
				exgExportInfoCustom.setF6(exgExportInfo.getF6());
				exgExportInfoCustom.setF7(exgExportInfo.getF7());
				exgExportInfoCustom.setF8(exgExportInfo.getF8());
				exgExportInfoCustom.setF9(exgExportInfo.getF9());
				exgExportInfoCustom.setF10(exgExportInfo.getF10());
				exgExportInfoCustom.setF12(exgExportInfo.getF12());
				this.casDao.getHibernateTemplate().save(exgExportInfoCustom);
			}
			index += length;
			if (tempBillDetails.size() <= 0 || tempBillDetails.size() < length) {
				break;
			}
		}
	}

	/**
	 * 统计来自报关单的金额(出口成品) 成品出口 + 转厂出口 + 返工复出 + 一般贸易出口 - 退厂返工
	 * 
	 * @param isCustoms
	 *            是否是报关用的
	 * @return 报关单金额(出口成品)
	 */
	public List<ExgExportInfoBase> sumMoneyFromApplyToCustomsByProduct(
			Date beginDate, Date endDate, boolean isCustoms) {
		if (beginDate == null) {
			beginDate = CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
					.getYear()), 0, 1);
		}
		if (endDate == null) {
			endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
					.getYear()), 11, 31);
		}
		//
		// 以名称+规格+单位,来分组进行统计
		//
		String selects = "select sum(a.commTotalPrice),a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.impExpType,a.baseCustomsDeclaration.currency.code,a.baseCustomsDeclaration.impExpDate ";
		String groupBy = "group by a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.impExpType,a.baseCustomsDeclaration.currency.code,a.baseCustomsDeclaration.impExpDate ";//wss:2010.04.30:增加了报关单生效日期
		String leftOuter = " left join a.unit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", "(",
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.DIRECT_EXPORT, null));//直接出口
		conditions.add(new Condition("or", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.TRANSFER_FACTORY_EXPORT, null));//转厂出口
		conditions.add(new Condition("or", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.GENERAL_TRADE_EXPORT, null));//一般贸易出口
		conditions.add(new Condition("or", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.REWORK_EXPORT, null));//返工复出
		conditions.add(new Condition("or", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.BACK_FACTORY_REWORK, ")"));//退厂返工

		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.effective", "=", true, null));// 生效

		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpDate", ">=", beginDate, null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpDate", "<=", endDate, null));

		// conditions.add(new Condition("and", "(",
		// "baseCustomsDeclaration.tradeMode.code", "=", "0255", null));// 来料深加工
		// conditions.add(new Condition("or", null,
		// "baseCustomsDeclaration.tradeMode.code", "=", "0654", ")"));// 进料深加工

		List listCustomsDeclarationCommInfo = casDao.commonSearch(selects,
				"CustomsDeclarationCommInfo", conditions, groupBy, leftOuter);
		List listCustomsDeclarationCommInfoDzsc = casDao.commonSearch(selects,
				"DzscCustomsDeclarationCommInfo", conditions, groupBy,
				leftOuter);
		if (listCustomsDeclarationCommInfoDzsc != null
				&& listCustomsDeclarationCommInfoDzsc.size() != 0) {
			listCustomsDeclarationCommInfo
					.addAll(listCustomsDeclarationCommInfoDzsc);
		}
		List listCustomsDeclarationCommInfoBcs = casDao
				.commonSearch(selects, "BcsCustomsDeclarationCommInfo",
						conditions, groupBy, leftOuter);
		if (listCustomsDeclarationCommInfoBcs != null
				&& listCustomsDeclarationCommInfoBcs.size() != 0) {
			listCustomsDeclarationCommInfo
					.addAll(listCustomsDeclarationCommInfoBcs);
		}
		//
		// 统计来自报关单的金额
		//
		Map<String, Double> mapCustomCommInfo = new HashMap<String, Double>();
		//
		// key = 报关名称+"/"+规格+"/"+单位名称
		//
		Double hsAmount = 0.0;
		for (int i = 0; i < listCustomsDeclarationCommInfo.size(); i++) {
			Object[] objs = (Object[]) listCustomsDeclarationCommInfo.get(i);
			Double hsAmount1 = (Double) objs[0] == null ? 0.0
					: (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			Integer impExpType = (Integer) objs[4] == null ? 0
					: (Integer) objs[4];
			//wss:2010.04.30增加  报关单生效日期 （为取得汇率）
			Date  validDate = (Date)objs[6];
			Double rate = casDao.findCurrRateByM((String) objs[5],validDate);
			
			String key = hsName + "/" + hsSpec + "/" + unitName;
			
			hsAmount = hsAmount1 * rate;// 得到汇率换算
			if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {// 退厂返工   减
				if (mapCustomCommInfo.get(key) == null) {
					mapCustomCommInfo.put(key, -hsAmount);
				} else {
					Double tempHsAmount = mapCustomCommInfo.get(key);
					tempHsAmount -= hsAmount;
					mapCustomCommInfo.put(key, tempHsAmount);
				}
			} else {
				if (mapCustomCommInfo.get(key) == null) {      // 其它   加
					mapCustomCommInfo.put(key, hsAmount);
				} else {
					Double tempHsAmount = mapCustomCommInfo.get(key);
					tempHsAmount += hsAmount;
					mapCustomCommInfo.put(key, tempHsAmount);
				}
			}
		}
		//
		// 获取大类的对应统计数量
		//
		// Map<String, Double> bigTenMap = new HashMap<String, Double>();
		//
		// List<StatCusNameRelationHsn> listExistStatCusNameRelationHsn =
		// this.casDao
		// .findStatCusNameRelationHsn(MaterielType.FINISHED_PRODUCT);
		// for (int i = 0; i < listExistStatCusNameRelationHsn.size(); i++) {
		// StatCusNameRelationHsn sh = listExistStatCusNameRelationHsn.get(i);
		// String hsName = sh.getCusName() == null ? "" : sh.getCusName();
		// String hsSpec = sh.getCusSpec() == null ? "" : sh.getCusSpec();
		// String unitName = sh.getCusUnit() == null ? "" : sh.getCusUnit()
		// .getName();
		// unitName = unitName == null ? "" : unitName;
		// String tenKey = hsName + "/" + hsSpec + "/" + unitName;
		//
		// StatCusNameRelation sc = sh.getStatCusNameRelation();
		// String bigHsName = sc.getCusName() == null ? "" : sc.getCusName();
		// String bigHsSpec = sc.getCusSpec() == null ? "" : sc.getCusSpec();
		// String bigUnitName = sc.getCusUnit() == null ? "" : sc.getCusUnit()
		// .getName();
		// bigUnitName = bigUnitName == null ? "" : bigUnitName;
		// String bigTenKey = bigHsName + "/" + bigHsSpec + "/" + bigUnitName;
		//
		// //
		// // 获取报关的数量
		// //
		// Double amount = mapCustomCommInfo.get(tenKey) == null ? 0.0
		// : mapCustomCommInfo.get(tenKey);
		//
		// if (bigTenMap.get(bigTenKey) == null) {
		// bigTenMap.put(bigTenKey, amount);
		// } else {
		// Double tempAmount = bigTenMap.get(bigTenKey);
		// tempAmount += amount;
		// bigTenMap.put(bigTenKey, tempAmount);
		// }
		// }
		//
		// 修改现在的统计资料
		// returnList
		//
		// 分页查询并修改
		//
		List<ExgExportInfoBase> returnList = new ArrayList<ExgExportInfoBase>();
		int index = 0;
		int length = 1000;
		while (true) {
			List tempBillDetails = null;
			if (isCustoms) {
				tempBillDetails = this.casDao.findExgExportInfoCustom(index,
						length, CommonUtils.getYear());
			} else {
				tempBillDetails = this.casDao.findExgExportInfo(index, length,
						CommonUtils.getYear());
			}
			for (int i = 0; i < tempBillDetails.size(); i++) {

				ExgExportInfoBase exgExportInfo = (ExgExportInfoBase) tempBillDetails
						.get(i);

				String hsName = exgExportInfo.getPtName() == null ? ""
						: exgExportInfo.getPtName();
				String hsSpec = exgExportInfo.getPtSpec() == null ? ""
						: exgExportInfo.getPtSpec();
				String unitName = exgExportInfo.getPtUnitName() == null ? ""
						: exgExportInfo.getPtUnitName();
				String key = hsName + "/" + hsSpec + "/" + unitName;
				Double money = mapCustomCommInfo.get(key) == null ? 0
						: mapCustomCommInfo.get(key);
				exgExportInfo.setMoney(exgExportInfo.getNoCustomMoney() + money);
				this.casDao.getHibernateTemplate().save(exgExportInfo);
				returnList.add(exgExportInfo);
			}
			index += length;
			if (tempBillDetails.size() <= 0 || tempBillDetails.size() < length) {
				break;
			}
		}
		return returnList;
	}

	/**
	 * 成品统计报表(自定义)
	 * 
	 * @param year
	 *            年度
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 统计指定的年度的成品资料(自定义)
	 */
	public List findExgExportInfos(String year, Boolean isOverMillion) {
		List<ExgExportInfoBase> returnList = new ArrayList<ExgExportInfoBase>();
		List list = this.casDao.findExgExportInfos(year);
		if (isOverMillion == true) {
			for (int i = 0; i < list.size(); i++) {
				ExgExportInfoBase exgExportInfo = (ExgExportInfoBase) list
						.get(i);
				if (exgExportInfo.getMoney() < 1000000) {
					ExgExportInfoBase temp = new ExgExportInfoBase();
					temp.setPtName(exgExportInfo.getPtName());
					temp.setPtSpec(exgExportInfo.getPtSpec());
					temp.setPtUnitName(exgExportInfo.getPtUnitName());
					temp.setMoney(exgExportInfo.getMoney());
					temp.setF12("金额小于一百万");
					returnList.add(temp);
				} else {
					returnList.add(exgExportInfo);
				}
			}
			return returnList;
		} else {
			return list;
		}
	}

	/**
	 * 成品统计报表(海关)
	 * 
	 * @param year
	 *            年度
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 统计指定的年度的成品资料(报关)
	 */
	public List findExgExportInfoCustoms(String year, Boolean isOverMillion) {
		List<ExgExportInfoBase> returnList = new ArrayList<ExgExportInfoBase>();
		List list = this.casDao.findExgExportInfoCustoms(year);
		if (isOverMillion == true) {
			for (int i = 0; i < list.size(); i++) {
				ExgExportInfoBase exgExportInfo = (ExgExportInfoBase) list
						.get(i);
				if (exgExportInfo.getMoney() < 1000000) {
					ExgExportInfoBase temp = new ExgExportInfoBase();
					temp.setPtName(exgExportInfo.getPtName());
					temp.setPtSpec(exgExportInfo.getPtSpec());
					temp.setPtUnitName(exgExportInfo.getPtUnitName());
					temp.setMoney(exgExportInfo.getMoney());
					temp.setF12("小于一百万");
					returnList.add(temp);
				} else {
					returnList.add(exgExportInfo);
				}
			}
			return returnList;
		} else {
			return list;
		}
	}
}
