package com.bestway.bcus.cas.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillDetailHalfProduct;
import com.bestway.bcus.cas.bill.entity.BillDetailLeftoverMateriel;
import com.bestway.bcus.cas.bill.entity.BillDetailMateriel;
import com.bestway.bcus.cas.bill.entity.BillDetailProduct;
import com.bestway.bcus.cas.bill.entity.BillDetailRemainProduct;
import com.bestway.bcus.cas.bill.entity.BillInit;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.bill.entity.BillMasterHalfProduct;
import com.bestway.bcus.cas.bill.entity.BillMasterLeftoverMateriel;
import com.bestway.bcus.cas.bill.entity.BillMasterMateriel;
import com.bestway.bcus.cas.bill.entity.BillMasterProduct;
import com.bestway.bcus.cas.bill.entity.BillMasterRemainProduct;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.BillCustomBillCmpImgInfo;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.CarryForwardCmpImgInfo;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.ImgOrgUseInfo;
import com.bestway.bcus.cas.entity.ImgOrgUseInfoBase;
import com.bestway.bcus.cas.entity.ImgOrgUseInfoCustom;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 
 * 料件年审报表
 * 
 * @author luosheng checked 2008-11-29
 * 
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ImgOrgUseInfoLogic {
	private static final Log logger = LogFactory
			.getLog(ImgOrgUseInfoLogic.class);
	/** 海关帐Dao */
	private CasDao casDao = null;
	/** 常用报关代码设置Dao */
	private CommonBaseCodeDao commonBaseCodeDao = null;
	/** 物料管理Dao */
	private MaterialManageDao materialManageDao = null;
	/** CasLeftoverMaterielLogic2 Dao */
	private CasLeftoverMaterielLogic2 casLeftoverMaterielLogic2 = null;

	/** CasDao Dao */
	public CasDao getCasDao() {
		return casDao;
	}

	/** CasLeftoverMaterielLogic2 Dao */
	public CasLeftoverMaterielLogic2 getCasLeftoverMaterielLogic2() {
		return casLeftoverMaterielLogic2;
	}

	/** CasLeftoverMaterielLogic2 logic */
	public void setCasLeftoverMaterielLogic2(
			CasLeftoverMaterielLogic2 casLeftoverMaterielLogic2) {
		this.casLeftoverMaterielLogic2 = casLeftoverMaterielLogic2;
	}

	/** MaterialManageDao Dao */
	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	/** CommonBaseCodeDao Dao */
	public CommonBaseCodeDao getCommonBaseCodeDao() {
		return commonBaseCodeDao;
	}

	/** CommonBaseCodeDao Dao */
	public void setCommonBaseCodeDao(CommonBaseCodeDao commonBaseCodeDao) {
		this.commonBaseCodeDao = commonBaseCodeDao;
	}

	/** CommonBaseCodeDao Dao */
	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	/** CasDao Dao */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	/** 为了每个公司只能有一个方法在同步运行,并且.. key = company id */
	private Map<String, Boolean> runMap = new Hashtable<String, Boolean>();

	/** 参数Map类 */
	static class ParameterMaps {
		/***********************************************************************
		 * 为了不传输太多参数在方法中而进行的类变量定义 findImgOrgUseInfos 传递参数将 ParameterMaps
		 * 对象为参数传递来访问这些变量
		 **********************************************************************/

		/** key:工厂料号 --用于生成成品期初单, add by sls */
		Map<String, Double> f1Map = new HashMap<String, Double>();

		/** key:报关名称+" / "+规格+" / "+单位名称 */
		Map<String, Double> f3Map = new HashMap<String, Double>();

		/** key: 报关名称+" / "+规格+" / "+单位名称 */
		Map<String, Double> f4Map = new HashMap<String, Double>();

		/** key:报关名称+" / "+规格+" / "+单位名称 */
		Map<String, Double> f5Map = new HashMap<String, Double>();

		/** key:工厂料号 --用于料件期初单, add by sls */
		Map<String, Double> f24Map = new HashMap<String, Double>();

		/** key:工厂料号 --用于在产品期初单, add by sls */
		Map<String, Double> f25Map = new HashMap<String, Double>();

		/** key:报关名称+" / "+规格+" / "+单位名称+/"+客户代码 */
		Map<String, Double> carryForwardByCustomerMap = new HashMap<String, Double>();

		/** key :报关名称+" / "+规格+" / "+单位名称 */
		Map<String, List<FactoryAndFactualCustomsRalation>> statCusNameRelationMtListMap = new HashMap<String, List<FactoryAndFactualCustomsRalation>>();
		/** key :真实的 报关名称+" / "+规格+" / "+单位名称 */
		// Map<String, String> statCusNameRelationHsMap = new HashMap<String,
		// String>();
		/** key: ptNo */
		// Map<String, StatCusNameRelationMt> statCusNameRelationMtValueMap =
		// new HashMap<String, StatCusNameRelationMt>(); ;
		// /////////////////////////////////////////////////////////////////////////////////////
		// 初始化边角料退运出口,边角料征税内销 key = 报关名称+" / "+规格+" / "+单位名称 在F14 ,F20 用到
		// /////////////////////////////////////////////////////////////////////////////////////
		/** 边角料退运出口 */
		Map<String, Double> remainMaterialBackMap = new HashMap<String, Double>();
		/** 边角料征税内销 */
		Map<String, Double> remainMaterialDomesticSalesMap = new HashMap<String, Double>();

		// /////////////////////////////////////////////////////////
		// 所有成品单据 用于 生成对应的料件之用 key = ptNo 对应的折算数量
		// /////////////////////////////////////////////////////////
		/** 2001成品起初单 */
		Map<String, Double> billDetail2001And4009Map = new HashMap<String, Double>();
		/** 2002车间入库 */
		Map<String, Double> billDetail2002Map = new HashMap<String, Double>();

		/** key: ptNo 料号 */
		Map<String, FactoryAndFactualCustomsRalation> statCusNameRelationMtValueByMaterielMap = new HashMap<String, FactoryAndFactualCustomsRalation>();
		/** 2007受托加工车间入库 */
		Map<String, Double> billDetail2007Map = new HashMap<String, Double>();
		/** 2009结转成品退货单 */
		Map<String, Double> billDetail2009Map = new HashMap<String, Double>();

		// /** 2010受托加工退回成品 */
		// Map<String, Double> billDetail2010Map = new HashMap<String,
		// Double>();

		/** wss:2010.04.19修改： 2010受托加工退回成品 + 2016受托加工成品返修 */
		Map<String, Double> billDetail2111And2016Map = new HashMap<String, Double>();

		// /**
		// * 2016受托加工成品返修
		// * wss:2010.04.19新加
		// */
		// Map<String, Double> billDetail2016Map = new HashMap<String,
		// Double>();
		//		
		// /**
		// * 4008受托加工成品返修
		// * wss:2010.04.19新加
		// * 待定
		// */
		// Map<String, Double> billDetail4008Map = new HashMap<String,
		// Double>();

		/** 2101直接出口 */
		Map<String, Double> billDetail2101Map = new HashMap<String, Double>();
		/** 2102结转出口 */
		Map<String, Double> billDetail2102Map = new HashMap<String, Double>();
		/** 2103返回车间 */
		Map<String, Double> billDetail2103Map = new HashMap<String, Double>();
		/** 2106海关批准内销 */
		Map<String, Double> billDetail2106Map = new HashMap<String, Double>();
		/** 2107其他内销 */
		Map<String, Double> billDetail2107Map = new HashMap<String, Double>();

		// /** 2110受托加工返回 */
		// Map<String, Double> billDetail2110Map = new HashMap<String,
		// Double>();

		/** wss:2010.04.19修改： 2112受托加工成品出库 */
		Map<String, Double> billDetail2112Map = new HashMap<String, Double>();

		// /**
		// * 2112受托加工成品出库
		// * wss:2010.04.19新加
		// */
		// Map<String, Double> billDetail2112Map = new HashMap<String,
		// Double>();
		//		
		// /**
		// * 4108受托加工半成品出库
		// * wss:2010.04.19新加
		// * 待定
		// */
		// Map<String, Double> billDetail4108Map = new HashMap<String,
		// Double>();

		/** F26入库成品-出库成品 */
		Map<String, Double> billDetailF26Map = new HashMap<String, Double>();
		/** 存放在F26产品折料的料件与对应的报关 */
		Map<String, FirstSumInfo> billDetailF26MaterialMap = new HashMap<String, FirstSumInfo>();

		// /** 4003外发加工入库(半成品) */
		// Map<String, Double> billDetail4003Map = new HashMap<String,
		// Double>();

		/** wss:2010.04.19修改：:4003外发加工入库(半成品) + 2015外发加工成品入库 + 4006外发加工半成品入库 */
		Map<String, Double> billDetail4004And2015And4006And2017Map = new HashMap<String, Double>();

		// /**
		// * 2015外发加工成品入库
		// * wss:2010.04.19新加
		// */
		// Map<String, Double> billDetail2015Map = new HashMap<String,
		// Double>();
		//		
		// /**
		// * 4006外发加工半成品入库
		// * wss:2010.04.19新加
		// */
		// Map<String, Double> billDetail4006Map = new HashMap<String,
		// Double>();

		// /** 4104外发加工返修成品 */
		// Map<String, Double> billDetail4104Map = new HashMap<String,
		// Double>();

		/** wss:2010.04.19修改：4104外发加工返修成品 + 2113外发加工成品返修 */
		Map<String, Double> billDetail4104And2113Map = new HashMap<String, Double>();

		// /**
		// * 2113外发加工成品返修
		// * wss:2010.04.19新加
		// */
		// Map<String, Double> billDetail2113Map = new HashMap<String,
		// Double>();

		// Map<String, Double> billDetailHalfMap = new HashMap<String,
		// Double>();
		/** key :料号 ,value : 大类报关名称+" / "+规格+" / "+单位名称 */
		Map<String, String> ptNoKeyMap = new HashMap<String, String>();

		Map<String, BillTemp> hsKeyMap = new HashMap<String, BillTemp>();

		// ///////////////////////////
		// 计算 20 栏 损耗：
		// ///////////////////////////
		/** 2002车间入库映射耗用 */
		Map<String, Double> billDetail2002MapWaste = new HashMap<String, Double>();

		/** 4003外发加工入库耗用 */
		Map<String, Double> billDetail4003MapWaste = new HashMap<String, Double>();

		/** 2007受托加工车间入库映射耗用 */
		Map<String, Double> billDetail2007MapWaste = new HashMap<String, Double>();

		/** 2103返回车间映射耗用 */
		Map<String, Double> billDetail2103MapWaste = new HashMap<String, Double>();

		/** 2110受托加工返回映射耗用 */
		Map<String, Double> billDetail2110MapWaste = new HashMap<String, Double>();
		/** 2014受托加工成品入库 */
		Map<String, Double> billDetail2014MapWaste = new HashMap<String, Double>();
		/** 2015外发加工成品入库 */
		Map<String, Double> billDetail2015MapWaste = new HashMap<String, Double>();
		/** 2015外发加工成品入库 */
		Map<String, Double> billDetail2015MapByF25 = new HashMap<String, Double>();
		/** 2113外发加工成品返修 */
		Map<String, Double> billDetail2113MapWaste = new HashMap<String, Double>();
		/** 2113外发加工成品返修 */
		Map<String, Double> billDetail2113MapByF25 = new HashMap<String, Double>();
		/** 2114外发加工成品出库 */
		Map<String, Double> billDetail2114MapByF25 = new HashMap<String, Double>();
		/** 2017外发加工成品退回 */
		Map<String, Double> billDetail2017MapByF25 = new HashMap<String, Double>();

		/** 4104 外发加工返修半成品 */
		Map<String, Double> billDetail4104MapWaste = new HashMap<String, Double>();

		/** 4105 外发加工领料 */
		Map<String, Double> billDetail4105MapWaste = new HashMap<String, Double>();

		/** key :报关名称+" / "+规格+" / "+单位名称 */
		Map<String, List<FactoryAndFactualCustomsRalation>> statCusNameRelationMtListByMaterielMap = new HashMap<String, List<FactoryAndFactualCustomsRalation>>();

		/** 2002车间入库 to F25 */
		Map<String, Double> billDetail2002MapByF25 = new HashMap<String, Double>();

		/** 2007受托加工车间入库 */
		Map<String, Double> billDetail2007MapByF25 = new HashMap<String, Double>();

		/** 2010受托加工退回成品 */
		Map<String, Double> billDetail2110MapByF25 = new HashMap<String, Double>();

		/** 2103返回车间 */
		Map<String, Double> billDetail2103MapByF25 = new HashMap<String, Double>();

		// /////////////////////////////////////////////////////////////////////////////////////
		// 初始化边角料,报关成品折边角料 key = 报关名称+" / "+规格+" / "+单位名称 =直接出口+结转出口+返工复出-退厂返工
		// /////////////////////////////////////////////////////////////////////////////////////
		/** 出口成品损耗量映射 */
		Map<String, Double> customsProductMapWaste = new HashMap<String, Double>();

		/**
		 * 4001半成品入库 2010.07.01新加
		 */
		Map<String, Double> billDetail4001SemiProductMapByF25 = new HashMap<String, Double>();

		/**
		 * 4101半成品出库（不含期初单） 2010.07.01新加
		 */
		Map<String, Double> billDetail4101SemiProductMapByF25 = new HashMap<String, Double>();

		/**
		 * 5001残次品入库 成品 、半成品 部分 2010.07.01新加
		 */
		Map<String, Double> billDetail5001FinishedAndSemiBadProductMapByF25 = new HashMap<String, Double>();

		/**
		 * 半成品结余 + 4002半成品盘盈单 + 4004外发加工半成品退 + 4006外发加工半成品入库 - 4102半成品盘亏单 -
		 * 4103委外加工出库 - 4104外发加工半成品返修 - 4106委外加工出库 - 5001残次品入库（半成品部分
		 */
		Map<String, Double> mapBillDetailPtAmountHalf = new HashMap<String, Double>();
		Map<String, Double> mapNianShenDetailPtAmountHalf = new HashMap<String, Double>();
	}

	/**
	 * 加工贸易原材料来源与使用情况表
	 * 
	 * @param conditions
	 *            conditions中包括对单据生效日期的过滤
	 * @return returnAsCustom false 工厂资料 true 海关资料
	 */
	public List<ImgOrgUseInfo> findImgOrgUseInfos(Date beginDate, Date endDate,
			String taskId, Boolean isShowTranFact) {
		String companyId = CommonUtils.getCompany().getId();
		Boolean isRun = runMap.get(companyId);
		if (isRun != null && isRun.booleanValue() == true) {
			throw new RuntimeException("料件年审报表计算正在进行中,请稍后再运行 ！！");
		}
		runMap.put(companyId, true);
		try {
			// 静态内部类，里面有有用的各种map
			ParameterMaps parameterMaps = new ParameterMaps();

			// 日期
			if (beginDate == null) {
				beginDate = CommonUtils.getBeginDate(Integer
						.valueOf(CommonUtils.getYear()), 0, 1);
			}
			if (endDate == null) {
				endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
						.getYear()), 11, 31);
			}

			// get thread internal message
			ProgressInfo progressInfo = ProcExeProgress.getInstance()
					.getProgressInfo(taskId);
			String clientTipMessage = "CAS 开始查询初始化大类与实际报关 对应关系... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);
			// this.initStatCusNameRelationHsMap(parameterMaps);

			clientTipMessage = "CAS 开始查询结转进口... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			/**
			 * 第一步： 初始化结转报关进口(查询的是 转厂进口 报关物料) f3Map:(key为
			 * 海关名称+海关规格+海关单位，value为统计报关数量) carryForwardByCustomerMap:(key为
			 * 海关名称+海关规格+海关单位+客户编码，value为统计报关数量)
			 */
			this.initF3(beginDate, endDate, parameterMaps);

			clientTipMessage = "CAS 开始查询已结转未收货和未结转已收货... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			/**
			 * 第二步： 初始化F4已结转未收货 F5未结转已收货 f4Map:已结转未收货(未按客户) key为报关名称+报关规格+报关单位
			 * value为报关数量 - 单据数量 f5Map:未结转已收货(未按客户) key为报关名称+报关规格+报关单位
			 * value为单据数量 - 报关数量
			 */
			this.initF4AndF5(beginDate, endDate, parameterMaps);

			clientTipMessage = "CAS 开始查询海关商品大类对应... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			/**
			 * 第三步： 料件报关商品 与 对应关系
			 * statCusNameRelationMtListMap：key为报关名称+报关规格+报关单位
			 * value工厂和实际客户对应表的list
			 */
			this.initStatCusNameRelationMap(parameterMaps);

			clientTipMessage = "CAS 开始查询边角料退运出口和边角料征税... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			/**
			 * 第四步： remainMaterialBackMap边角料退运出口: key为 报关名称+报关规格+报关单位
			 * value为6102边角料退运出口单据报关数量 remainMaterialDomesticSalesMap边角料征税内销:
			 * key为 报关名称+报关规格+报关单位 value为6104边角料征税内销单据报关数量
			 */
			this.initRemainMaterialMap(beginDate, endDate, parameterMaps);

			/**
			 * 第五步： 出口成品损耗量映射 customsProductMapWaste
			 */
			parameterMaps.customsProductMapWaste = casLeftoverMaterielLogic2
					.findCalLeftoverMateriel(beginDate, endDate, taskId);// 出口成品
			// 损耗量（单项用量*损耗率）
			// 映射

			clientTipMessage = "CAS 开始查询所有成品... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			/**
			 * 第六步： statCusNameRelationMtListByMaterielMap
			 * statCusNameRelationMtValueByMaterielMap
			 */
			//this.initStatCusNameRelationByMaterielMap(parameterMaps);// 工厂的 与
			// 海关的
			// 对应关系(该工厂料号对应哪个海关商品)
			/**
			 * 第七步： 初始化成品 （海关帐-其它报表-车间材料库存情况表有引用此方法）
			 */
			System.out.println("CAS 第七步：开始初始化成品折料**************************");

			this.initBillDetailProductMap(beginDate, endDate, parameterMaps,
					progressInfo); // wss:注 --在这里
			/**
			 * 第八步： 初始化半成品（含残次品中成品、半成品）
			 */
			System.out.println("CAS 第八步：开始初始化半成品折料**************************");
			this.initMapBillDetailHalfProduct(beginDate, endDate, parameterMaps,
					progressInfo);

			logger.info("CAS  2001成品与半成品期初单Map               size == "
					+ parameterMaps.billDetail2001And4009Map.size());
			logger.info("CAS  2002车间入库Map                size == "
					+ parameterMaps.billDetail2002Map.size());
			logger.info("CAS  2007受托加工车间入库Map          size == "
					+ parameterMaps.billDetail2007Map.size());
			logger.info("CAS  2009结转成品退货单Map           size == "
					+ parameterMaps.billDetail2009Map.size());
			logger.info("CAS  2111受托加工退回成品and2016受托加工成品返修Map size == "
					+ parameterMaps.billDetail2111And2016Map.size());
			logger.info("CAS  2101直接出口Map                size == "
					+ parameterMaps.billDetail2101Map.size());
			logger.info("CAS  2102结转出口Map                size == "
					+ parameterMaps.billDetail2102Map.size());
			logger.info("CAS  2103返回车间Map                size == "
					+ parameterMaps.billDetail2103Map.size());
			logger.info("CAS  2106海关批准内销Map             size == "
					+ parameterMaps.billDetail2106Map.size());
			logger.info("CAS  2107其他内销Map                size == "
					+ parameterMaps.billDetail2107Map.size());
			logger.info("CAS  2110受托加工返回and 2112受托加工成品出库Map size == "
					+ parameterMaps.billDetail2112Map.size());
			logger.info("CAS  4104外发加工返修成品Map          size == "
					+ parameterMaps.billDetail4104And2113Map.size());
			logger.info("CAS  4003外发加工入库Map             size == "
					+ parameterMaps.billDetail4004And2015And4006And2017Map
							.size());
			logger.info("CAS  f26．  库存产品耗用Map          size == "
					+ parameterMaps.billDetailF26Map.size());

			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("and", null, "billMaster.isValid",
					"=", Boolean.valueOf(true), null));
			conditions.add(new Condition("and", null, "hsName is not null ",
					null, null, null));
			conditions.add(new Condition("and", null, "billMaster.validDate",
					">=", beginDate, null));
			conditions.add(new Condition("and", null, "billMaster.validDate",
					"<=", endDate, null));
			// G00-A070154 弹簧 五金配件/钢/铁 0 588 6000 0.44 2400 108 7320209000
			// 弹簧/五金配件/铁 108 0.0072
			String billDetailMateriel = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);

			String selects = "select sum(a.hsAmount),sum(a.money),a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name  ";
			String groupBy = "group by a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name  ";
			String leftOuter = " left join a.billMaster left join a.hsUnit  left join a.billMaster.billType	";
			String orderBy = "order by sum(a.money) desc ";

			clientTipMessage = "CAS 开始查询所有料件... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			long beginTime = System.currentTimeMillis();

			// 第九步：
			// 查询所有料件(单据体)
			System.out.println("wss 第九步：开始初始化料件**************************");

			List listBillDetailMateriel = casDao
					.commonSearch(selects, billDetailMateriel, conditions,
							groupBy, leftOuter, orderBy);

			System.out.println("wss 单据  料件 list.size = "
					+ listBillDetailMateriel.size());

			List<Condition> newConditions = new ArrayList<Condition>();
			newConditions.addAll(conditions);
			String[] codes = new String[] { "5001", "5002", "5101" };
			newConditions.add(new Condition("and", null,
					"billMaster.billType.code", " in (", codes, ")"));
			newConditions.add(new Condition("and", null, "note", "=", "料件",
					null)); // note == 料件

			// 第十步：
			// 查询残次品（ 料件）

			System.out
					.println("CAS 第十步：开始初始化残次品（ 料件）*************************");

			String billDetailBadProduct = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);
			List listBillDetailMateriel2 = casDao.commonSearch(selects,
					billDetailBadProduct, newConditions, groupBy + " "
							+ orderBy, leftOuter);

			System.out.println("CAS 单据     残次品5101（料件） list.size = "
					+ listBillDetailMateriel2.size());

			// 加入残次品出库 (料件)5101 5001,5002
			listBillDetailMateriel.addAll(listBillDetailMateriel2);

			logger.info("CAS 开始查询所有料件的共用时间: "
					+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

			clientTipMessage = "CAS 开始最后的计算请等待.... ";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);
			
			
			
			
			/*
			 * 统计年审报表
			 */
			List<FirstSumInfo> firstSumInfoList = new ArrayList<FirstSumInfo>();
			
			
			//
			// 把成品折料的报关商品名称加入
			//
			FirstSumInfo firstSumInfo = null; // 统计item数据
			Iterator<Map.Entry<String, FirstSumInfo>> it = parameterMaps.billDetailF26MaterialMap
					.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, FirstSumInfo> e = it.next();
				firstSumInfo = (FirstSumInfo) e.getValue();
				firstSumInfoList.add(firstSumInfo);
			}
			
			/*
			 * 2013-1-10 根据料号的【对应关系】把料件单据里的【料号】映射为【报关商品】 
			 */
			Object[] object = null;
			firstSumInfo = null; // 统计item数据
			for (int i = 0; i < listBillDetailMateriel.size(); i++) {
				object = (Object[]) listBillDetailMateriel.get(i);
				String key = (object[3] == null ? "" : object[3].toString())
						+ '/' + (object[4] == null ? "" : object[4]) + " / "
						+ (object[5] == null ? "" : object[5]);
				if (parameterMaps.billDetailF26MaterialMap.get(key) != null) {
					firstSumInfoList
							.remove(parameterMaps.billDetailF26MaterialMap
									.get(key));
				}
				firstSumInfo = new FirstSumInfo(object);
				firstSumInfoList.add(firstSumInfo);
				
			}
			

			// 第十一步：
			// 这里是总统计
			List<ImgOrgUseInfo> resultList = getResultList(firstSumInfoList,
					parameterMaps, progressInfo, isShowTranFact, beginDate,
					endDate); // 将List进行解析对不同的类型返回List
			System.out.println("resultList size = " + resultList.size());

			// 第十二步：
			// 删除当年度的所有海关帐
			clientTipMessage = "CAS 开始删除当年度的所有海关帐... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);
			this.casDao.deleteImgOrgUseInfo(CommonUtils.getYear());

			for (int i = 0; i < resultList.size(); i++) {
				ImgOrgUseInfo item = (ImgOrgUseInfo) resultList.get(i);
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

	/**
	 * 统计加工贸易原材料来源与使用情况
	 * 
	 * @param firstSumInfoList
	 *            最初统计信息
	 * @return 原材料来源与使用情况
	 */
	public List<ImgOrgUseInfo> getResultList(
			List<FirstSumInfo> firstSumInfoList, ParameterMaps parameterMaps,
			ProgressInfo progressInfo, Boolean isShowTranFact, Date beginDate,
			Date endDate) {
		List<ImgOrgUseInfo> reslutList = new ArrayList<ImgOrgUseInfo>();
		HashMap<String, ImgOrgUseInfo> resultHash = new HashMap<String, ImgOrgUseInfo>();
		int size = firstSumInfoList.size();
		
		/*
		 * 先统计料件数据，跳过要折料的成品半成品。
		 */
		String key = null;
		String hsName = null;// 报关名称
		String hsSpec = null;// 规格
		String hsUnitName = null;// 单位名称
		Double hsAmount = null; // 料件数量（没有折算之前的数量）
		Double money = null; // 金额（没有折算之前的金额）
		String typeCode = null;// 单据类型
		FirstSumInfo firstSumInfo = null; // 统计item数据
		for (int i = 0; i < size; i++) {
			firstSumInfo = firstSumInfoList.get(i);
			ImgOrgUseInfo item = null;
			hsAmount = firstSumInfo.getHsAmount() == null ? 0.0
					: firstSumInfo.getHsAmount();
			typeCode = firstSumInfo.getTypeCode();
			hsName = firstSumInfo.getHsName() == null ? ""
					: firstSumInfo.getHsName();
			hsUnitName = firstSumInfo.getHsUnitName() == null ? ""
					: firstSumInfo.getHsUnitName();
			hsSpec = firstSumInfo.getHsSpec() == null ? ""
					: firstSumInfo.getHsSpec();
			money = firstSumInfo.getMoney() == null ? 0.0 : firstSumInfo
					.getMoney();
			//
			// key=名称+" / "+规格+" / "+单位
			//
			key = hsName + " / " + hsSpec + " / " + hsUnitName;
			if (resultHash.get(key) != null) {
				item = (ImgOrgUseInfo) resultHash.get(key);
			} else {
				item = new ImgOrgUseInfo();
				item.setYear(CommonUtils.getYear());
				item.setCustomName(true);
				item.setPtName(hsName);
				item.setPtSpec(hsSpec);
				item.setPtUnitName(hsUnitName);
				resultHash.put(key, item);
			}

			// 要折料的先跳过，后面循环会计算。
			if ("折料".equals(typeCode)) {
				continue;
			}
			//
			// 报关总金额 = 1003 + 1004 + 1005 + 1009 + 1011 + 1008 + 1001 + 1002
			// 1003直接进口 + 1004结转进口+1005国内购买+1009其他来源+1011海关征税进口 + 1008
			// 受托加工进库+1001料件期初单
			// +1002在产品期初
			//
			if (typeCode != null) {
				if (typeCode.equals("1003") || typeCode.equals("1004")
						|| typeCode.equals("1005") || typeCode.equals("1009")
						|| typeCode.equals("1011") || typeCode.equals("1008")
						|| typeCode.equals("1001") || typeCode.equals("1002")) {
					item.setMoney(item.getMoney() + money);
				}
				// wss:2010.04.30新加（从方便计算 从报关单中统计金额）除去了与报关单相关的
				if (typeCode.equals("1005") || typeCode.equals("1009")
						|| typeCode.equals("1008") || typeCode.equals("1001")
						|| typeCode.equals("1002")) {
					item.setNoCustomMoney(item.getNoCustomMoney() + money);
				}
				//
				// 1. 期初库存: “料件期初单”折算报关数量的累加 ＋ “在产品期初单”折算报关数量的累加 ＋
				// “成品期初单”数量＊对应版本单耗＊料件折算 -- 后面的步骤计算
				// 5002 残次品期初单
				// 1001 料件起初单 1002在产品期初 5002 残次品起初单
				if (typeCode.equals("1001") || typeCode.equals("1002")
						|| typeCode.equals("5002")) {// 1.料件期初库存
					item.setF1(item.getF1() + hsAmount);
				}
				//
				// 2. 直接报关进口：“直接进口”单据折算报关数量的累加 ＋ “海关征税进口” 单据折算报关数量 数量的累加.
				//
				if (typeCode.equals("1003") || typeCode.equals("1011")) {// 2.料件直接报关进口
					item.setF2(item.getF2() + hsAmount);
				}
				//
				// 6．其中:海关征税进口 : 单据类型为 “海关征税进口” 单据折算报关数量的累加.
				//
				if (typeCode.equals("1011")) {
					item.setF6(item.getF6() + hsAmount);
				}
				//
				// 7．国内采购: 单据类型为 “国内采购” 的单据折算报关数量的累加。 - 其它料件退货单
				//
				if (typeCode.equals("1005") || typeCode.equals("1107")) {// 7.料件国内采购
					if (typeCode.equals("1005")) { // 料件国内采购
						item.setF7(item.getF7() + hsAmount);
					}
					if (typeCode.equals("1107")) { // 1111 受托加工退回料件
						item.setF7(item.getF7() - hsAmount);
					}

				}
				//
				// 8．受托加工: “受托加工进库” 单据折算报关数量的累加 － “受托加工退回料件”
				// +受托加工料件返修-受托加工料件出库。的单据折算报关数量的累加。
				// 
				if (typeCode.equals("1008") || typeCode.equals("1111")
						|| typeCode.equals("1019") || typeCode.equals("1114")) {
					if (typeCode.equals("1008") || typeCode.equals("1019")) { // 1008
						// 受托加工进库，1019受托加工返修
						item.setF8(item.getF8() + hsAmount);
					}
					if (typeCode.equals("1111") || typeCode.equals("1114")) { // 1111
						// 受托加工退回料件，1114受托加工出库
						item.setF8(item.getF8() - hsAmount);
					}
				}
				//
				// 9．外发加工返回原料: 单据类型为“外发加工料件入库” 的单据折算报关数量的累加。
				//
				if (typeCode.equals("1018") // 1018 外发加工料件入库
						|| typeCode.equals("1017")) {// wss:2010.04.19新加
					// 1017外发加工料件退回
					item.setF9(item.getF9() + hsAmount);
				}
				//
				// 10．其它来源： “其它来源”单据折算报关数量的累加。
				//
				if (typeCode.equals("1009")) {// 1009 其他来源
					item.setF10(item.getF10() + hsAmount);
				}
				if (typeCode.equals("1103")) {// 15.退运出口(1103料件复出单据)
					item.setF15(item.getF15() + hsAmount);
				}
				//
				// luosheng 2009/05/13
				//
				if (typeCode.equals("5101")// 14.5101残次品出库,在海关征税内销中
						|| typeCode.equals("1115")) {// 1115海关批准内销wss2010.06.17新加
					item.setF14(item.getF14() + hsAmount);
				}

				if (typeCode.equals("1112") // 18.外发加工返修出库,料件加工发外单据
						|| typeCode.equals("1113")) {// wss:2010.04.19新加
					// 1113外发加工料件出库
					item.setF18(item.getF18() + hsAmount);
				}
				//
				// 21.其它使用 1102 料件退换 + 1108 其他领用
				//
				if (typeCode.equals("1102") || typeCode.equals("1108")) {
					item.setF21(item.getF21() + hsAmount);
				}

				//
				// 24． 其中：库存原材料：各料件进仓单据折算报关数量的累加－各料件出仓单据折算报关数量的累加
				// 入库
				// （1015 已收货未结转期初单
				// 1016 已结转未收货期初单
				// 1014 委外期初单
				// 1013 外发加工返回料件 ）不放入入库
				// 
				if (typeCode.equals("1001") // 1001 料件期初单
						|| typeCode.equals("1003") // 1003 直接进口
						|| typeCode.equals("1004") // 1004 结转进口
						|| typeCode.equals("1005") // 1005 国内购买
						|| typeCode.equals("1006") // 1006 车间返回
						|| typeCode.equals("1007") // 1007 料件盘盈单
						|| typeCode.equals("1008") // 1008 受托加工进库
						|| typeCode.equals("1009") // 1009 其他来源
						|| typeCode.equals("1010") // 1010 料件转仓入库
						|| typeCode.equals("1011") // 1011海关征税进口
						|| typeCode.equals("1017") // 1017 外发加工料件退回
						|| typeCode.equals("1018") // 1018 外发加工料件入库
						|| typeCode.equals("1019") // 1019 受托加工料件返修
						|| typeCode.equals("5001"))// 5001 残次品入库
				{
					item.setF24(item.getF24() + hsAmount);
				} else if (typeCode.equals("1101") // 1101 车间领用
						|| typeCode.equals("1102") // 1102 料件退换
						|| typeCode.equals("1103") // 1103 料件复出
						|| typeCode.equals("1104") // 1104 料件盘亏单
						|| typeCode.equals("1105") // 1105 料件转仓出库
						|| typeCode.equals("1106") // 1106 结转料件退货单
						|| typeCode.equals("1107") // 1107 其他料件退货单
						|| typeCode.equals("1108") // 1108 其他领用
						|| typeCode.equals("1109") // 1109 受托加工领用
						|| typeCode.equals("1111") // 1111 受托加工退回料件
						|| typeCode.equals("1115") // 1115 海关批准内销
						|| typeCode.equals("1113") // 1113 外发加工料件出库
						|| typeCode.equals("1112") // 1112 外发加工返修出库
						|| typeCode.equals("1114") // 1114 受托加工料件出库
						|| typeCode.equals("5101"))// 5101残 次品出库
				{ // 出库
					item.setF24(item.getF24() - hsAmount);
				}

				//
				// 1101 车间领用 + 1002在产品期初 - 1006 车间返回
				if (typeCode.equals("1101") || typeCode.equals("1002")
						|| typeCode.equals("1014") // 委外期初单(料件)
						|| typeCode.equals("1112") // 外发加工返修出库(料件)
						|| typeCode.equals("1113")// 外发加工料件出库(料件)
				) {
					item.setF25(item.getF25() + hsAmount);
				} else if (typeCode.equals("1006") || typeCode.equals("1017")// 外发加工料件退回(料件)
						|| typeCode.equals("1018")// 外发加工料件入库(料件)
				) {// wss:2010.07.01添加：5101残次品出库||
					// typeCode.equals("5101")
					// 料件部分
					item.setF25(item.getF25() - hsAmount);// F25库存在产品耗用
				}
				//
				// materialExchange 料件退换数量 用于备注
				//
				if (typeCode.equals("1102")) {
					item.setMaterialExchange(item.getMaterialExchange()
							+ hsAmount);
				}
				if (isShowTranFact) {
					if (typeCode.equals("1015")) {
						item.setMateriel1015(item.getMateriel1015() + hsAmount);
					} else if (typeCode.equals("1016")) {
						item.setMateriel1016(item.getMateriel1016() + hsAmount);
					}
				}
			}
		}

		/*
		 * 统计成品、半成品，数量=工厂数量*折算系数，
		 */
		String year = String
				.valueOf(Integer.valueOf(CommonUtils.getYear()) + 1);
		this.casDao.deleteBillInit(year);
		Collection<ImgOrgUseInfo> c = resultHash.values();
		Iterator<ImgOrgUseInfo> iterator = c.iterator();
		int i = 0;
		size = c.size();
		key = null;
		hsName = null;// 报关名称
		hsSpec = null;// 规格
		hsUnitName = null;// 单位名称
		while (iterator.hasNext()) {
			i++;
			ImgOrgUseInfo item = iterator.next();
			hsName = item.getPtName();
			hsUnitName = item.getPtUnitName();
			hsSpec = item.getPtSpec();
			//
			// key=名称+" / "+规格+" / "+单位
			//
			key = hsName + " / " + hsSpec + " / " + hsUnitName;

			// 获得成品折料件当前 key 值的所有栏位
			//
			long beginTime = System.currentTimeMillis();
			String clientTipMessage = "CAS 最后计算: " + size + " 报关料件条记录前 "
					+ i + " 条记录";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			/**
			 * 成品（含有了残次品中的 成品、半成品 部分）
			 */
			Map<String, Double> tempMap = this.getProductConvertIntoMateriel(
					key, parameterMaps);
			

			/**
			 * 半成品
			 */
			Map<String, Double> halftempMap = this
					.getHalfProductConvertIntoMateriel(key, parameterMaps);

			logger.info("CAS 最后计算: " + size + " 报关料件条记录前 " + i + " 条记录"
					+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
			//
			// 1. 期初库存: “成品期初单”数量＊对应版本单耗＊料件折算
			//
			item.setF1(item.getF1() + tempMap.get("F1"));

			//
			// 3． 结转报关进口: 进口类型 为 “ 料件转厂”、贸易方式为 “来/进料深加工”且进口日期在本帐目年度的报关单数量的累加.
			//
			item.setF3(parameterMaps.f3Map.get(key) == null ? 0.0
					: parameterMaps.f3Map.get(key));
			//
			// 4． 已结转未收货: 当相同客户名称的（ “结转进口” 单据折算报关数量的累加 －“结转料件退货单” 单据折算报关数量的累加 <
			// “结转报关进口”数量的累加）时,所有客户两者差额值的和
			//
			if (parameterMaps.f4Map.get(key) == null
					&& parameterMaps.f5Map.get(key) == null) {
				item.setF4(parameterMaps.f3Map.get(key) == null ? 0.0
						: parameterMaps.f3Map.get(key));
			} else {
				item.setF4(parameterMaps.f4Map.get(key) == null ? 0.0
						: parameterMaps.f4Map.get(key));
			}
			//
			// 5. 未结转已收货: 当相同客户名称的（ “结转进口” 单据折算报关数量的累加 －“结转料件退货单” 单据折算报关数量的累加
			// >“结转报关进口” 数量的累加）时, 所有客户两者差额值的和
			//
			item.setF5(parameterMaps.f5Map.get(key) == null ? 0.0
					: parameterMaps.f5Map.get(key));
			// System.out.println("wss 统计时key:" + key+"tempMap.get(f5) = " +
			// parameterMaps.f5Map.get(key));
			//          
			// 12.直接出口成品耗用 ∑（单据类型为“直接出口”的单据数量 ＊对应版本的单耗＊料件折算）
			// 
			item.setF12(tempMap.get("F12"));
			//
			// 13.实际结转成品耗用 ∑（单据类型为“结转出口-结转成品退货单”的单据数量 ＊ 对应版本的单耗＊料件折算）
			//
			// 
			item.setF13(tempMap.get("F13"));
			// /
			// begin 14
			// 14.海关批准内销耗用 ∑（单据类型为“海关批准内销”的单据数量 ＊对应版本的单耗＊料件折算）
			// 加上边角料征税内销的单据数量（并在27栏备注）
			//
			// /////////////
			// 2008,6.5号修改
			// 加入残次品出库 5101 billDetail2107Map (成品，和料件)
			Double remainMaterialDomesticSales = parameterMaps.remainMaterialDomesticSalesMap
					.get(key) == null ? 0.0
					: parameterMaps.remainMaterialDomesticSalesMap.get(key);
			item.setF14(item.getF14() + tempMap.get("F14")
					+ remainMaterialDomesticSales);

			//
			// 16.其它内销耗用 ∑（单据类型为“其它内销”的单据数量 ＊对应版本的单耗＊料件折算）
			//
			item.setF16(tempMap.get("F16"));
			//
			// 17.受托加工返回耗用 ∑［（单据类型为“受托加工返回”的单据数量－单据类型为“受托加工退回成品”的单据数量）
			// ＊对应版本的单耗＊料件折算］
			//
			item.setF17(tempMap.get("F17"));
			//
			// 19.外发返回成品耗用
			// ∑［（单据类型为“外发加工入库”的单据数量－单据类型为“外发加工返修成品”的单据数量）］＊对应版本的单耗＊料件折算
			//
			item.setF19(tempMap.get("F19"));

			//
			// begin 20
			// 20.损耗 ［（车间入库＋受托加工车间入库＋外发加工入库－返回车间－受托加工返回－外发加工返修成品）的单据数量
			// ＊对应版本的单项用量＊损耗率＊料件折算］－ （边角料退运出口＋边角料征税内销）的单据折算报关数量
			//
			Double remainMaterialBack = parameterMaps.remainMaterialBackMap
					.get(key) == null ? 0.0
					: parameterMaps.remainMaterialBackMap.get(key);
			item.setF20(tempMap.get("F20") - remainMaterialDomesticSales
					- remainMaterialBack);
			//
			// 25.库存在产品耗用 （车间领用＋在产品期初单＋受托加工领用＋外发加工出库－外发加工退回料件－车间返回）折算报关数量的累加 －［（
			// 车间入库－返回车间＋外发加工入库－外发加工返修成品＋受托加工车间入库）数量＊对应版本单项用量＊料件折算］
			//
			// 半成品折原材料

			item.setF25(item.getF25() + halftempMap.get("F25")
					+ tempMap.get("F25"));
			//
			// 26.库存产成品耗用 相同版本的（入库成品单据数量－出库成品单据数量） ＊对应版本的单耗
			//
			item.setF26(tempMap.get("F26"));

			// 转厂期初单
			Double tranFact = (item.getMateriel1015() == null ? 0.0 : item
					.getMateriel1015())
					- (item.getMateriel1016() == null ? 0.0 : item
							.getMateriel1016());

			//
			// 最后设置 f27 备注栏
			//
			if (item.getMaterialExchange() > 0) {
				item.setF27(item.getF27() + "料件退换数量: "
						+ item.getMaterialExchange() + " ; ");
			}
			if (remainMaterialDomesticSales > 0) {
				item.setF27(item.getF27() + "边角料征税内销数量: "
						+ remainMaterialDomesticSales + " ; ");
			}
			if (remainMaterialBack > 0) {
				item.setF27(item.getF27() + "边角料退运出口数量: " + remainMaterialBack
						+ " ; ");
			}
			if (isShowTranFact) {
				if (tranFact != 0) {
					item.setF27(item.getF27()
							+ (tranFact > 0 ? "已收货未结转" : "已结转未收货")
							+ (tranFact > 0 ? tranFact : -tranFact) + " ; ");
				}
			}
			reslutList.add(item);
		}
		return reslutList;
	}

	/**
	 * 获得半成品折料
	 * 
	 * @param key
	 * @param parameterMaps
	 * @return
	 */
	private Map<String, Double> getHalfProductConvertIntoMateriel(String key,
			ParameterMaps parameterMaps) {
		Map<String, Double> map = new HashMap<String, Double>();
		//
		// 根据报关的名称 获得对应的工厂料件列表
		//
		map.put("F25", 0.0);
		map.put("F25Factory", 0.0);
		//
		// 根据报关的名称 获得对应的工厂料件列表
		//
		List<FactoryAndFactualCustomsRalation> mtList = parameterMaps.statCusNameRelationMtListMap
				.get(key);

		if (mtList == null) {
			/*
			 * 会出现找不到对应关系的情况
			 * 例如：
			 * 对应关系中： 料号 对应 实际报关资料  折算系数
			 *            L1  ->  A          1
			 *            L2  ->  B          2
			 * 料件单据中：
			 *      料号 工厂数量 实际报关资料 报关数量
			 *      L1  10      A         10
			 *      L2  10      B         20
			 * 
			 * 我们报表计算取的对应关系是:  L1  ->  A          1
			 * 
			 * 所以找不到对应关系：L2  ->  B          2
			 */
			
			
			logger.info("对应表中没有对应的报关名称/规格/单位 == " + key);
			
			return map;
		}

		FactoryAndFactualCustomsRalation mt = null;
		String ptNoKey = null; // 料件料号
		Double unitConvert = null; // 折算系数
		for (int i = 0, n = mtList.size(); i < n; i++) {
			mt = mtList.get(i);
			ptNoKey = mt.getMateriel().getPtNo(); // 料件料号
			unitConvert = mt.getUnitConvert() == null ? 0.0 : mt
					.getUnitConvert(); // 折算系数

			//
			// 当出现一个料号对应多个报关品名的时候，
			// 抓取id号最大的来显示折算
			//
			FactoryAndFactualCustomsRalation ffcr = parameterMaps.statCusNameRelationMtValueByMaterielMap.get(ptNoKey);
			if (ffcr == null) {
				continue;
			}
			
			Double halfHsAmount = parameterMaps.mapNianShenDetailPtAmountHalf
					.get(ptNoKey);
			
			Double ptAmount = (halfHsAmount == null ? 0 : halfHsAmount);
			if (!map.containsKey("F25")) {
				map.put("F25", (ptAmount * unitConvert));
				map.put("F25Factory", ptAmount);
			} else {
				map.put("F25", map.get("F25") + (ptAmount * unitConvert));
				map.put("F25Factory", map.get("F25Factory") + ptAmount);
			}
			if (ptAmount != 0.0) {
				BillInit temp = new BillInit();
				temp.setYear(String.valueOf(Integer.valueOf(CommonUtils
						.getYear()) + 1));
				temp.setTypeCode("F25");
				temp.setPtAmount(ptAmount);
				temp.setPtNo(ptNoKey);
				temp.setHsNo(mt.getStatCusNameRelationHsn().getComplex()
						.getCode());
				temp.setHsName(mt.getStatCusNameRelationHsn().getCusName());
				temp.setHsSpec(mt.getStatCusNameRelationHsn().getCusSpec());
				temp.setHsAmount(ptAmount * unitConvert);
				temp.setNote("F25半成品");
				materialManageDao.saveOrUpdate(temp);
			}
		}
		return map;
	}

	/**
	 * 获得成品折料件当前 key 值的所有栏位
	 * 
	 * @param key
	 *            = 报关名称+" / "+规格+" / "+单位名称
	 * @return map key = F1 = 期初库存: “成品期初单”数量＊对应版本单耗＊料件折算 key = F12 = 直接出口成品耗用
	 *         ∑（单据类型为“直接出口”的单据数量 ＊对应版本的单耗＊料件折算） key = F13 = 实际结转成品耗用
	 *         ∑（单据类型为“结转出口-结转成品退货单”的单据数量 ＊ 对应版本的单耗＊料件折算） key = F14 = 海关批准内销耗用
	 *         ∑（单据类型为“海关批准内销”的单据数量 ＊对应版本的单耗＊料件折算） 加上边角料征税内销的单据数量（并在27栏备注） key =
	 *         F16 = 其它内销耗用 ∑（单据类型为“其它内销”的单据数量 ＊对应版本的单耗＊料件折算） key = F17 =
	 *         受托加工返回耗用 ∑［（单据类型为“受托加工返回”的单据数量－单据类型为“受托加工退回成品”的单据数量）
	 *         ＊对应版本的单耗＊料件折算］ key = F19 = 外发返回成品耗用
	 *         ∑［（单据类型为“外发加工入库”的单据数量－单据类型为“外发加工返修成品”的单据数量）］＊对应版本的单耗＊料件折算 key =
	 *         F20 = 损耗 ［（车间入库＋受托加工车间入库＋外发加工入库－返回车间－受托加工返回－外发加工返修成品）的单据数量
	 *         ＊对应版本的单项用量＊损耗率＊料件折算］ key = F25 = （
	 *         车间入库－返回车间＋外发加工入库－外发加工返修成品＋受托加工车间入库）数量＊对应版本单项用量＊料件折算 key = F26 =
	 *         库存产成品耗用 相同版本的（入库成品单据数量－出库成品单据数量） ＊对应版本的单耗
	 */
	private Map<String, Double> getProductConvertIntoMateriel(String key,
			ParameterMaps parameterMaps) {

		// ////////////////////////////////////////////////////////////////////////////////////////////////////
		// key = F1 = 期初库存: “成品期初单”数量＊对应版本单耗＊料件折算
		// key = F12 = 直接出口成品耗用 ∑（单据类型为“直接出口”的单据数量 ＊对应版本的单耗＊料件折算）
		// key = F13 = 实际结转成品耗用 ∑（单据类型为“结转出口-结转成品退货单”的单据数量 ＊ 对应版本的单耗＊料件折算）
		// key = F14 = 海关批准内销耗用 ∑（单据类型为“海关批准内销”的单据数量 ＊对应版本的单耗＊料件折算）
		// 加上边角料征税内销的单据数量（并在27栏备注）
		// key = F16 = 其它内销耗用 ∑（单据类型为“其它内销”的单据数量 ＊对应版本的单耗＊料件折算）
		// key = F17 = 受托加工返回耗用 ∑［（单据类型为“受托加工返回”的单据数量－单据类型为“受托加工退回成品”的单据数量）
		// ＊对应版本的单耗＊料件折算］
		// key = F19 = 外发返回成品耗用
		// ∑［（单据类型为“外发加工入库”的单据数量－单据类型为“外发加工返修成品”的单据数量）］＊对应版本的单耗＊料件折算
		// key = F20 = 损耗 ［（车间入库＋受托加工车间入库＋外发加工入库－返回车间－受托加工返回－外发加工返修成品）的单据数量
		// ＊对应版本的单项用量＊损耗率＊料件折算］
		// key = F25 = （ 车间入库－返回车间＋外发加工入库－外发加工返修成品＋受托加工车间入库）数量＊对应版本单项用量＊料件折算
		// key = F26 = 库存产成品耗用 相同版本的（入库成品单据数量－出库成品单据数量） ＊对应版本的单耗
		// /////////////////////////////////////////////////////////////////////////////////////////////////////
		Map<String, Double> map = new HashMap<String, Double>();
		
		map.put("F1", 0.0);
		map.put("F12", 0.0);
		map.put("F13", 0.0);
		map.put("F14", 0.0);
		map.put("F16", 0.0);
		map.put("F17", 0.0);
		map.put("F19", 0.0);
		map.put("F20", 0.0);
		map.put("F25", 0.0);
		map.put("F26", 0.0);
		map.put("F25Factory", 0.0);
		
		//
		// 根据报关的名称 获得对应的工厂料件列表
		//
		List<FactoryAndFactualCustomsRalation> mtList = parameterMaps.statCusNameRelationMtListMap
				.get(key);

		if (mtList == null) {
			/*
			 * 会出现找不到对应关系的情况
			 * 例如：
			 * 对应关系中： 料号 对应 实际报关资料  折算系数
			 *            L1  ->  A          1
			 *            L2  ->  B          2
			 * 料件单据中：
			 *      料号 工厂数量 实际报关资料 报关数量
			 *      L1  10      A         10
			 *      L2  10      B         20
			 * 
			 * 我们报表计算取的对应关系是:  L1  ->  A          1
			 * 
			 * 所以找不到对应关系：L2  ->  B          2
			 */
			
			
			logger.info("对应表中没有对应的报关名称/规格/单位 == " + key);
			
			return map;
		}	
		
		
		System.out.println("===准备循环的HSKEY: " + key + "===" + mtList.size());
		FactoryAndFactualCustomsRalation mt = null;
		String ptNoKey = null; // 料件料号
		Double unitConvert = null; // 折算系数
		for (int i = 0, n = mtList.size(); i < n; i++) {
			mt = mtList.get(i);
			ptNoKey = mt.getMateriel().getPtNo(); // 料件料号
			unitConvert = mt.getUnitConvert() == null ? 0.0 : mt
					.getUnitConvert(); // 折算系数

			//
			// 当出现一个料号对应多个报关品名的时候，
			// 抓取id号最大的来显示折算
			//
			FactoryAndFactualCustomsRalation ffcr = parameterMaps.statCusNameRelationMtValueByMaterielMap.get(ptNoKey);
			if (ffcr == null) {
				continue;
			}
			
			//
			// 获得该成品料号和版本对应的 成品期初单的生效的记录
			//
			if (!map.containsKey("F1")) {
				//
				// “成品期初单(成品期初单+半成品期初单)”数量＊对应版本单耗＊料件折算
				//              
				Double ptAmount = parameterMaps.billDetail2001And4009Map
						.get(ptNoKey);
				ptAmount = ptAmount == null ? 0.0 : ptAmount;
				map.put("F1", ptAmount * unitConvert);
			} else {
				Double ptAmount = parameterMaps.billDetail2001And4009Map
						.get(ptNoKey);
				ptAmount = ptAmount == null ? 0.0 : ptAmount;
				map.put("F1", map.get("F1") + (ptAmount * unitConvert));
			}

			//
			// 直接出口成品耗用 ∑（单据类型为“直接出口”的单据数量 ＊对应版本的单耗＊料件折算）
			//
			if (!map.containsKey("F12")) {
				Double ptAmount = parameterMaps.billDetail2101Map.get(ptNoKey);
				ptAmount = ptAmount == null ? 0.0 : ptAmount;
				map.put("F12", ptAmount * unitConvert);
			} else {
				Double ptAmount = parameterMaps.billDetail2101Map.get(ptNoKey);
				ptAmount = ptAmount == null ? 0.0 : ptAmount;
				map.put("F12", map.get("F12") + (ptAmount * unitConvert));
			}
			//
			// 实际结转成品耗用 ∑（单据类型为“结转出口-结转成品退货单”的单据数量 ＊ 对应版本的单耗＊料件折算）
			//
			if (!map.containsKey("F13")) {
				//
				// 获得该成品料号和版本对应的 结转出口的生效的记录
				//
				Double ptAmount2102 = parameterMaps.billDetail2102Map
						.get(ptNoKey);
				Double ptAmount2009 = parameterMaps.billDetail2009Map
						.get(ptNoKey);

				Double ptAmount = (ptAmount2102 == null ? 0 : ptAmount2102)
						- (ptAmount2009 == null ? 0 : ptAmount2009);
				map.put("F13", ptAmount * unitConvert);

			} else {
				Double ptAmount2102 = parameterMaps.billDetail2102Map
						.get(ptNoKey);
				Double ptAmount2009 = parameterMaps.billDetail2009Map
						.get(ptNoKey);

				Double ptAmount = (ptAmount2102 == null ? 0 : ptAmount2102)
						- (ptAmount2009 == null ? 0 : ptAmount2009);
				map.put("F13", map.get("F13") + (ptAmount * unitConvert));
			}

			//
			// 海关批准内销耗用 ∑（单据类型为“海关批准内销”的单据数量 ＊对应版本的单耗＊料件折算）
			// 加上边角料征税内销的单据数量（并在27栏备注）
			//
			if (!map.containsKey("F14")) {
				//
				// 获得该成品料号和版本对应的 海关批准内销的生效的记录
				//
				Double ptAmount2106 = parameterMaps.billDetail2106Map
						.get(ptNoKey);
				Double ptAmount = (ptAmount2106 == null ? 0 : ptAmount2106);
				map.put("F14", (ptAmount * unitConvert));
			} else {
				Double ptAmount2106 = parameterMaps.billDetail2106Map
						.get(ptNoKey);
				Double ptAmount = (ptAmount2106 == null ? 0 : ptAmount2106);
				map.put("F14", map.get("F14") + (ptAmount * unitConvert));
			}
			//
			// 其它内销耗用 ∑（单据类型为“其它内销”的单据数量 ＊对应版本的单耗＊料件折算）
			//
			if (!map.containsKey("F16")) {
				//
				// 获得该成品料号和版本对应的 2107 其他内销 的生效的记录
				//
				Double ptAmount2107 = parameterMaps.billDetail2107Map
						.get(ptNoKey);
				Double ptAmount = (ptAmount2107 == null ? 0.0 : ptAmount2107);
				map.put("F16", (ptAmount * unitConvert));
			} else {
				Double ptAmount2107 = parameterMaps.billDetail2107Map
						.get(ptNoKey);
				Double ptAmount = (ptAmount2107 == null ? 0.0 : ptAmount2107);
				map.put("F16", map.get("F16") + (ptAmount * unitConvert));
			}

			//
			// 受托加工返回耗用 ∑［（单据类型为“受托加工返回”的单据数量－单据类型为“受托加工退回成品”的单据数量）
			// ＊对应版本的单耗＊料件折算］
			//
			if (!map.containsKey("F17")) {
				//
				// 获得该成品料号和版本对应的 2107 其他内销 的生效的记录
				//
				Double ptAmount2112 = parameterMaps.billDetail2112Map
						.get(ptNoKey);
				Double ptAmount2111And2016 = parameterMaps.billDetail2111And2016Map
						.get(ptNoKey);
				Double ptAmount = (ptAmount2112 == null ? 0 : ptAmount2112)
						- (ptAmount2111And2016 == null ? 0
								: ptAmount2111And2016);

				map.put("F17", (ptAmount * unitConvert));
			} else {
				Double ptAmount2112 = parameterMaps.billDetail2112Map
						.get(ptNoKey);
				Double ptAmount2111And2016 = parameterMaps.billDetail2111And2016Map
						.get(ptNoKey);
				Double ptAmount = (ptAmount2112 == null ? 0 : ptAmount2112)
						- (ptAmount2111And2016 == null ? 0
								: ptAmount2111And2016);
				map.put("F17", map.get("F17") + (ptAmount * unitConvert));
			}

			//
			// 外发返回成品耗用
			// 2015外发加工成品入库+2017外发加工成品退回+4004外发加工半成品退回+4006外发加工半成品入库-2113外发加工成品返修-4104外发加工半成品返修
			//
			if (!map.containsKey("F19")) {

				Double ptAmount4004And2015And4006And2017 = parameterMaps.billDetail4004And2015And4006And2017Map
						.get(ptNoKey);
				Double ptAmount4104And2113 = parameterMaps.billDetail4104And2113Map
						.get(ptNoKey);

				Double ptAmount = (ptAmount4004And2015And4006And2017 == null ? 0.0
						: ptAmount4004And2015And4006And2017)
						- (ptAmount4104And2113 == null ? 0.0
								: ptAmount4104And2113);
				map.put("F19", (ptAmount * unitConvert));
			} else {
				Double ptAmount4004And2015And4006And2017 = parameterMaps.billDetail4004And2015And4006And2017Map
						.get(ptNoKey);
				Double ptAmount4104And2113 = parameterMaps.billDetail4104And2113Map
						.get(ptNoKey);

				Double ptAmount = (ptAmount4004And2015And4006And2017 == null ? 0
						: ptAmount4004And2015And4006And2017)
						- (ptAmount4104And2113 == null ? 0
								: ptAmount4104And2113);
				map.put("F19", map.get("F19") + (ptAmount * unitConvert));
			}

			//
			// 损耗 ［（车间入库＋受托加工车间入库＋外发加工入库－返回车间－受托加工返回－外发加工返修成品）的单据数量
			// ＊对应版本的单项用量＊损耗率＊料件折算］
			//
			if (!map.containsKey("F20")) {
				//
				// 获得该成品料号和版本对应的
				// 2002 车间入库 + 4003 外发加工入库 +2007 受托加工车间入库
				// - 2103 返回车间 - 2110 受托加工返回 - 4104 外发加工返修成品 - 4105 外发车间领用
				//                
				Double ptAmount2002 = parameterMaps.billDetail2002MapWaste
						.get(ptNoKey);
				Double ptAmount2103 = parameterMaps.billDetail2103MapWaste
						.get(ptNoKey);
				Double ptAmount2014 = parameterMaps.billDetail2014MapWaste
						.get(ptNoKey);
				Double ptAmount2015 = parameterMaps.billDetail2015MapWaste
						.get(ptNoKey);
				Double ptAmount2113 = parameterMaps.billDetail2113MapWaste
						.get(ptNoKey);
				Double ptAmount = (ptAmount2002 == null ? 0 : ptAmount2002)
						+ (ptAmount2014 == null ? 0 : ptAmount2014)
						+ (ptAmount2015 == null ? 0 : ptAmount2015)
						- (ptAmount2103 == null ? 0 : ptAmount2103)
						+ (ptAmount2113 == null ? 0 : ptAmount2113);
				map.put("F20", (ptAmount * unitConvert));
			} else {
				Double ptAmount2002 = parameterMaps.billDetail2002MapWaste
						.get(ptNoKey);
				Double ptAmount2103 = parameterMaps.billDetail2103MapWaste
						.get(ptNoKey);
				Double ptAmount2014 = parameterMaps.billDetail2014MapWaste
						.get(ptNoKey);
				Double ptAmount2015 = parameterMaps.billDetail2015MapWaste
						.get(ptNoKey);
				Double ptAmount2113 = parameterMaps.billDetail2113MapWaste
						.get(ptNoKey);
				Double ptAmount = (ptAmount2002 == null ? 0 : ptAmount2002)
						+ (ptAmount2014 == null ? 0 : ptAmount2014)
						+ (ptAmount2015 == null ? 0 : ptAmount2015)
						- (ptAmount2103 == null ? 0 : ptAmount2103)
						+ (ptAmount2113 == null ? 0 : ptAmount2113);
				map.put("F20", map.get("F20") + (ptAmount * unitConvert));
			}

			// 半成品库存重新计算（wss:2010.07.02重新修改）
			/***************************************************************
			 * + 4101半成品出库*BOM - 4001半成品入库*BOM - 2002车间入库*BOM + 2103返回车间*BOM -
			 * 5001残次品成品入库*BOM - 5001残次品半成品入库
			 * 
			 * (wss:2010.07.02 修改)
			 **************************************************************/
			Double ptAmount2002 = parameterMaps.billDetail2002MapByF25
					.get(ptNoKey);
			Double ptAmount2103 = parameterMaps.billDetail2103MapByF25
					.get(ptNoKey);
			Double ptAmount2113 = parameterMaps.billDetail2113MapByF25
					.get(ptNoKey);
			Double ptAmount2114 = parameterMaps.billDetail2114MapByF25
					.get(ptNoKey);
			Double ptAmount2015 = parameterMaps.billDetail2015MapByF25
					.get(ptNoKey);
			Double ptAmount2017 = parameterMaps.billDetail2017MapByF25
					.get(ptNoKey);
			Double ptAmountF25 = (ptAmount2103 == null ? 0 : ptAmount2103)
					- (ptAmount2002 == null ? 0 : ptAmount2002)
					+ (ptAmount2113 == null ? 0 : ptAmount2113)
					+ (ptAmount2114 == null ? 0 : ptAmount2114)
					- (ptAmount2015 == null ? 0 : ptAmount2015)
					- (ptAmount2017 == null ? 0 : ptAmount2017);

			if (!map.containsKey("F25")) {
				map.put("F25", (ptAmountF25 * unitConvert));
				map.put("F25Factory", ptAmountF25);
			} else {
				map.put("F25", map.get("F25") + (ptAmountF25 * unitConvert));
				map.put("F25Factory", map.get("F25Factory") + ptAmountF25);
			}
			if (ptAmountF25 != 0.0) {
				BillInit temp = new BillInit();
				temp.setYear(String.valueOf(Integer.valueOf(CommonUtils
						.getYear()) + 1));
				temp.setTypeCode("F25");
				temp.setPtAmount(ptAmountF25);
				temp.setPtNo(ptNoKey);
				temp.setHsNo(mt.getStatCusNameRelationHsn().getComplex()
						.getCode());
				temp.setHsName(mt.getStatCusNameRelationHsn().getCusName());
				temp.setHsSpec(mt.getStatCusNameRelationHsn().getCusSpec());
				temp.setHsAmount(ptAmountF25 * unitConvert);
				temp.setNote("F25成品");
				materialManageDao.saveOrUpdate(temp);
			}
			//
			// 库存产成品耗用 相同版本的（入库成品单据数量－出库成品单据数量） ＊对应版本的单耗
			//
			if (!map.containsKey("F26")) {
				Double f26PtAmount = parameterMaps.billDetailF26Map
						.get(ptNoKey);
				Double ptAmount = (f26PtAmount == null ? 0 : f26PtAmount);
				map.put("F26", (ptAmount * unitConvert));
			} else {
				Double f26PtAmount = parameterMaps.billDetailF26Map
						.get(ptNoKey);
				Double ptAmount = (f26PtAmount == null ? 0 : f26PtAmount);
				map.put("F26", map.get("F26") + (ptAmount * unitConvert));
			}
			// System.out.println("wss 正在计算的成品折料号为: " + ptNoKey + " 其中数量为 ："
			// + map.get("F26"));

		}
		return map;
	}

	/**
	 * 3． 结转报关进口: 进口类型 为 “ 料件转厂”、贸易方式为 “来/进料深加工”且进口日期在本帐目年度的报关单数量的累加
	 * 
	 * 生成 以 报关名称+" / "+规格+" / "+单位为 key 的 carryForwardMap 生成 以 报关名称+" / "+规格+" / "+单位 +
	 * " / " +客户代码为 key 的 carryForwardByCustomerMap
	 */
	private void initF3(Date beginDate, Date endDate,
			ParameterMaps parameterMaps) {
		//
		// 以 报关名称规格+单位为key的 map
		//
		Map<String, Double> f3Map = parameterMaps.f3Map;
		//
		// 以 报关名+称规格+单位+客户 为key的 map
		//
		Map<String, Double> carryForwardByCustomerMap = parameterMaps.carryForwardByCustomerMap;
		//
		// 以名称+规格+单位,客户来分组进行统计
		//
		String selects = "select sum(a.commAmount),a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.customer.code  ";
		String groupBy = "group by a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.customer.code ";
		String leftOuter = " left join a.unit left join a.baseCustomsDeclaration.customer ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.TRANSFER_FACTORY_IMPORT, null));// 转厂进口
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

		// conditions.add(new Condition("and", "(",
		// "baseCustomsDeclaration.tradeMode.code", "=", "0255", null));// 来料深加工
		// conditions.add(new Condition("or", null,
		// "baseCustomsDeclaration.tradeMode.code", "=", "0654", ")"));// 进料深加工

		// 电子化手册
		List listCustomsDeclarationCommInfo = casDao.commonSearch(selects,
				"CustomsDeclarationCommInfo", conditions, groupBy, leftOuter);
		makeMap(f3Map, carryForwardByCustomerMap,
				listCustomsDeclarationCommInfo);

		// 电子手册
		List listBcsCustomsDeclarationCommInfo = casDao
				.commonSearch(selects, "BcsCustomsDeclarationCommInfo",
						conditions, groupBy, leftOuter);
		makeMap(f3Map, carryForwardByCustomerMap,
				listBcsCustomsDeclarationCommInfo);

		// 电子帐册
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
	 *            结转数量映射
	 * @param carryForwardByCustomerMap
	 * @param list
	 */
	private void makeMap(Map<String, Double> carryForwardMap,
			Map<String, Double> carryForwardByCustomerMap, List list) {
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);

			Double commAmount = (Double) objs[0] == null ? 0.0
					: (Double) objs[0];
			String name = (String) objs[1] == null ? "" : (String) objs[1];
			String spec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String customerCode = (String) objs[4] == null ? ""
					: (String) objs[4];

			// key为： 报关名称 + 报关规格 + 报关单位
			String key = name + " / " + spec + " / " + unitName;

			if (carryForwardMap.get(key) == null) {
				carryForwardMap.put(key, commAmount);
			} else {
				Double commA = carryForwardMap.get(key);
				commA += commAmount;
				carryForwardMap.put(key, commA);
			}

			// key为：报关名称 + 报关规格 + 报关单位 + 客户/供应商代码
			String hasCustomerKey = key + " / " + customerCode;
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
	 * 4． 已结转未收货: 当相同客户名称的 （ “结转进口” 单据折算报关数量的累加 －“结转料件退货单” 单据折算报关数量的累加 <
	 * “结转报关进口”数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF3Map 已结转未收货
	 * 
	 * 5． 未结转已收货: 当相同客户名称的 （ “结转进口” 单据折算报关数量的累加 －“结转料件退货单” 单据折算报关数量的累加 >“结转报关进口”
	 * 数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF4Map 未结转已收货 *
	 * 
	 */
	private void initF4AndF5(Date beginDate, Date endDate,
			ParameterMaps parameterMaps) {

		// 以 报关名+称规格+单位+客户 为key的 map
		Map<String, Double> f4Map = parameterMaps.f4Map;
		Map<String, Double> f5Map = parameterMaps.f5Map;

		// 此条在F3中初始化
		Map<String, Double> carryForwardByCustomerMap = parameterMaps.carryForwardByCustomerMap;

		// 以名称+规格+单位,客户来分组进行统计
		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.code,a.billMaster.billType.code  ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.scmCoc.code,a.billMaster.billType.code  ";
		String leftOuter = " left join a.hsUnit left join a.billMaster.scmCoc ";

		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "1004", null)); // 1004 结转进口
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1106", ")")); // 1106 结转料件退货单
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", beginDate, null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));

		List carryForwardList = casDao.commonSearch(selects,
				"BillDetailMateriel", conditions, groupBy, leftOuter);

		// key = 报关名称+" / "+规格+" / "+单位名称 + " / " +客户代码
		Map<String, Double> carryForwardBackMap = new HashMap<String, Double>();
		for (int i = 0; i < carryForwardList.size(); i++) {
			Object[] objs = (Object[]) carryForwardList.get(i);
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String scmCocCode = (String) objs[4] == null ? ""
					: (String) objs[4];

			// key = 报关名称+" / "+规格+" / "+单位名称 + " / " +客户代码
			String key = hsName + " / " + hsSpec + " / " + unitName + " / "
					+ scmCocCode;

			String typeCode = (String) objs[5] == null ? "" : (String) objs[5];
			if (typeCode.equalsIgnoreCase("1106")) {// 结转料件退货单
				carryForwardBackMap.put(key, hsAmount);// 退货单据体的Map，key含客户编码
			}
		}

		for (int i = 0; i < carryForwardList.size(); i++) {
			Object[] objs = (Object[]) carryForwardList.get(i);
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String scmCocCode = (String) objs[4] == null ? ""
					: (String) objs[4];
			String typeCode = (String) objs[5] == null ? "" : (String) objs[5];

			// 无含客户的编码key:报关名称 + 报关规格 + 报关单位
			String noCustomerKey = hsName + " / " + hsSpec + " / " + unitName;

			// 含客户编码的key:报关名称 + 报关规格 + 报关单位 + 客户编码
			String hasCustomerKey = noCustomerKey + " / " + scmCocCode;
			if (typeCode.equalsIgnoreCase("1106")) {// 跳过 结转料件退货单
				continue;
			}

			// 退货数量
			Double backAmount = carryForwardBackMap.get(hasCustomerKey) == null ? 0.0
					: carryForwardBackMap.get(hasCustomerKey);

			// 收货数量 = 结转进口 - 结转料件退货
			hsAmount -= backAmount;

			// 报关单报关数量
			Double customAmount = carryForwardByCustomerMap.get(hasCustomerKey) == null ? 0.0
					: carryForwardByCustomerMap.get(hasCustomerKey);

			// System.out.println("hasCustomKey = " + hasCustomerKey);
			// System.out.println("customAmount = " + customAmount);

			// 收货数量(单据) - 报关数量(报关单)
			double tempAmount = hsAmount - customAmount;
			// System.out.println("==收货数量="+hsAmount+"==报关数量="+customAmount+"=="+tempAmount);
			if (tempAmount < 0) {
				// 已结转未收货(未按客户)
				tempAmount = -tempAmount;
				if (f4Map.get(noCustomerKey) == null) {
					f4Map.put(noCustomerKey, tempAmount);
				} else {
					Double tempA = f4Map.get(noCustomerKey);
					tempA += tempAmount;
					f4Map.put(noCustomerKey, tempA);
				}
			} else {
				// 未结转已收货(未按客户)
				if (f5Map.get(noCustomerKey) == null) {
					f5Map.put(noCustomerKey, tempAmount);
				} else {
					Double tempA = f5Map.get(noCustomerKey);
					tempA += tempAmount;
					f5Map.put(noCustomerKey, tempA);
				}
			}
		}
	}

	/**
	 * 初始化企业物料 (statCusNameRelationMtListMap) 和
	 * 实际报关商品(statCusNameRelationMtValueMap) 对应表
	 */
	private void initStatCusNameRelationMap(ParameterMaps parameterMaps) {
		// 实际报关资料key（名称+规格+单位） 对应的 对应关系列表
		Map<String, List<FactoryAndFactualCustomsRalation>> statCusNameRelationMtListMap = parameterMaps.statCusNameRelationMtListMap;
		// 工厂料号 对应的 对应关系
		Map<String, FactoryAndFactualCustomsRalation> statCusNameRelationMtValueByMaterielMap = parameterMaps.statCusNameRelationMtValueByMaterielMap;
		
		// 查询工厂和实际客户对应表的所有物料对应关系
		List<FactoryAndFactualCustomsRalation> listExistStatCusNameRelationMt = this.casDao
				.findStatCusNameRelationMt(MaterielType.MATERIEL);
		
		List<FactoryAndFactualCustomsRalation> tempList = null;
		// key为： 报关名称+" / "+规格+" / "+单位名称
		String tenKey = null;
		// key = ptno 工厂料号
		String key = null;
		FactoryAndFactualCustomsRalation mt = null;// 对应关系
		Materiel materiel = null;// 物料主档
		StatCusNameRelationHsn sr = null; //实际报关资料
		String hsName = null;// 报关名称
		String hsSpec = null;// 规格
		String unitName = null;// 单位名称
		for (int i = 0; i < listExistStatCusNameRelationMt.size(); i++) {
			mt = listExistStatCusNameRelationMt
					.get(i);
			materiel = mt.getMateriel();
			sr = mt.getStatCusNameRelationHsn();
			
			if (materiel == null) {
				continue;
			}
			
			/*
			 *  组装 工厂料号 对应的 对应关系
			 */
			// key = ptno 工厂料号
			key = materiel.getPtNo();
			if (statCusNameRelationMtValueByMaterielMap.get(key) == null) { // 不存在该料号加入该料号和对应关系
				statCusNameRelationMtValueByMaterielMap.put(key, mt); 
			} else {
				continue; // 存在 跳过本次循环
			}

			/*
			 * 组装 实际报关资料key（名称+规格+单位） 对应的 对应关系列表
			 */
			hsName = sr.getCusName() == null ? "" : sr.getCusName();
			hsSpec = sr.getCusSpec() == null ? "" : sr.getCusSpec();
			unitName = sr.getCusUnit() == null ? "" : sr.getCusUnit()
					.getName();
			unitName = unitName == null ? "" : unitName;

			// key为： 报关名称+" / "+规格+" / "+单位名称
			tenKey = hsName + " / " + hsSpec + " / " + unitName;
			tempList = statCusNameRelationMtListMap.get(tenKey);
			if (tempList == null) {
				tempList = new ArrayList<FactoryAndFactualCustomsRalation>();
				tempList.add(mt);
				statCusNameRelationMtListMap.put(tenKey, tempList);
			} else {
				tempList.add(mt);// 报关名称 可能对应多个 工厂名称
			}
		}
	}

	
	/**
	 * 初始化边角料外发加工退回 (remainMaterialBackMap)边角料征税内销
	 * (remainMaterialDomesticSalesMap)
	 * 
	 */
	private void initRemainMaterialMap(Date beginDate, Date endDate,
			ParameterMaps parameterMaps) {
		Map<String, Double> remainMaterialBackMap = parameterMaps.remainMaterialBackMap;
		Map<String, Double> remainMaterialDomesticSalesMap = parameterMaps.remainMaterialDomesticSalesMap;

		List<Condition> conditions = new ArrayList<Condition>();

		// conditions.add(new Condition("and", null, "billMaster.isValid",
		// "=",Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				new Boolean(true), null));

		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));

		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "6102", ""));// 6102 边角料退运出口
		conditions.add(new Condition("or", "", "billMaster.billType.code", "=",
				"6104", ")"));// 6104 边角料征税内销

		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", beginDate, null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));

		String billDetailRemainMateriel = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.REMAIN_MATERIEL);

		String selects = "select sum(a.hsAmount),sum(a.money),a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name  ";
		String groupBy = "group by a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name ";
		String leftOuter = " left join a.hsUnit  ";

		// 边角料
		List listBillDetailMateriel = casDao.commonSearch(selects,
				billDetailRemainMateriel, conditions, groupBy, leftOuter);
		System.out.println("wss 边角料6102 边角料退运出口、6104 边角料征税内销 list.size为"
				+ listBillDetailMateriel.size());
		logger.info("边角料征税内销 = " + listBillDetailMateriel.size());
		for (int i = 0; i < listBillDetailMateriel.size(); i++) {
			// 6102 边角料退运出口
			// 6104 边角料征税内销
			Object[] objs = (Object[]) listBillDetailMateriel.get(i);

			Double hsAmount = objs[0] == null ? 0.0 : (Double) objs[0];
			String typeCode = (String) objs[2];
			String hsName = objs[3] == null ? "" : (String) objs[3];
			String hsSpec = objs[4] == null ? "" : (String) objs[4];
			String hsUnitName = objs[5] == null ? "" : (String) objs[5];

			// key=名称+" / "+规格+" / "+单位
			String key = hsName + " / " + hsSpec + " / " + hsUnitName;
			System.out.println("wss 边角料key:" + key);
			System.out.println("wss 边角料typeCode:" + typeCode);
			System.out.println("wss 边角料hsAmount:" + hsAmount);

			// wss:这里为什么没有累加？=====>>>因为查询时已经分组累加了
			if (typeCode.equals("6102")) {// 边角料退运出口
				remainMaterialBackMap.put(key, hsAmount);
			} else if (typeCode.equals("6104")) {// 边角料征税内销
				remainMaterialDomesticSalesMap.put(key, hsAmount);

				logger.info("边角料征税内销 = " + key + " 数量 =" + hsAmount);
			}
		}
	}

	/**
	 * 获得半成品成品列表
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private List getHalfProductList(Date beginDate, Date endDate) {

		// 4003 委外加工入库
		// 4104 外发加工返修半成品
		// 4105 外发加工领料
		// wss:2010.04.19新加 4006外发加工半成品入库

		String halfProductTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.SEMI_FINISHED_PRODUCT);

		String select = " select sum(a.ptAmount),a.billMaster.billType.code,v.id from "
				+ halfProductTableName
				+ " a "
				+ ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = 0 )) "
				+ " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ? and a.billMaster.billType.code in (?,?,?,?,?,?,?) "
				// + " and a.billMaster.notice != ? "
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(beginDate);
		params.add(endDate);
		params.add(CommonUtils.getCompany().getId());
		params.add("4001");// 4001半成品入库(wss:2010.07.01新加 )
		params.add("4101");// 4101半成品出库(wss:2010.07.01新加 )
		params.add("4003"); // 4003 委外加工入库
		params.add("4104"); // 4104 外发加工返修半成品
		params.add("4105"); // 4105 外发加工领料
		params.add("4006");// 4006外发加工半成品入库(wss:2010.04.19新加 )
		params.add("4009");
		// 成品
//		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(select, params.toArray());
		// System.out.println("wss 半成品部分：list.size = " + list.size());

		// logger.info("CAS 开始查询委外半成品的共用时间: "
		// + (System.currentTimeMillis() - beginTime) + " 毫秒 ");

		return list;
	}

	/**
	 * 获得残次品成品列表
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private List getBadProductList(Date beginDate, Date endDate) {

		String billDetailBadProduct = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);

		String select = " select sum(a.ptAmount),a.billMaster.billType.code,v.id from "
				+ billDetailBadProduct
				+ " a "
				+ ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = 0 )) "
				+ " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ? and a.billMaster.billType.code in(?,?) "
				+ " and ( a.note = ? or a.note = ? )"
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(beginDate);
		params.add(endDate);
		params.add(CommonUtils.getCompany().getId());
		params.add("5101"); // 残次品出库 5101
		params.add("5001"); // 残次品入库 5001
		params.add("成品");
		params.add("半成品");// wss:2010.07.01新加

//		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(select, params.toArray());

		// logger.info("CAS 开始查询 残次品出库 成品的共用时间: "
		// + (System.currentTimeMillis() - beginTime) + " 毫秒 ");

		return list;
	}

	/**
	 * 初始化 成品单据明细 hashMap
	 * 
	 */
	private void initBillDetailProductMap(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		/*
		 * 成品单据
		 */
		String productTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);

		String selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id from "
				+ productTableName
				+ " a "
				+ ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = 0 )) "
				+ " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ? "
				// +
				// "and a.billMaster.billType.code in('2002','2015','2017','2103','2113','2114')"
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次

		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(beginDate);
		params.add(endDate);
		params.add(CommonUtils.getCompany().getId());
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, params.toArray());

		System.out.println("CAS 成品list.size = " + list.size());

		/*
		 * 加入相关半成品单据
		 */

		List halfList = this.getHalfProductList(beginDate, endDate);
		list.addAll(halfList);

		System.out.println("CAS 半成品list.size = " + list.size());

		/*
		 * 获得残次品(成品、半成品)单据wss:2010.07.01加上
		 */
		List badList = this.getBadProductList(beginDate, endDate);
		list.addAll(badList);
		System.out.println("CAS 残次品（成品）list.size = " + badList.size());

		// logger.info("CAS 开始查询所有成品的共用时间: "
		// + (System.currentTimeMillis() - beginTime) + " 毫秒 ");
		//		
		/** 2001成品起初单 */
		Map<String, Double> billDetail2001And4009Map = parameterMaps.billDetail2001And4009Map;// 

		/** 2002车间入库 */
		Map<String, Double> billDetail2002Map = parameterMaps.billDetail2002Map; // 

		/** 2007受托加工车间入库 */
		Map<String, Double> billDetail2007Map = parameterMaps.billDetail2007Map; // 

		/** 2009结转成品退货单 */
		Map<String, Double> billDetail2009Map = parameterMaps.billDetail2009Map; // 

		/** 2010受托加工退回成品and2116受托加工成品返 */
		Map<String, Double> billDetail2111And2016Map // 
		= parameterMaps.billDetail2111And2016Map; // wss:2010.04.19 修改

		/** 2014受托加工成品入库 */
		Map<String, Double> billDetail2014MapWaste = parameterMaps.billDetail2014MapWaste;
		/** 2015外发加工成品入库 */
		Map<String, Double> billDetail2015MapWaste = parameterMaps.billDetail2015MapWaste;
		/** 2113外发加工成品返修 */
		Map<String, Double> billDetail2113MapWaste = parameterMaps.billDetail2113MapWaste;
		/** 2015外发加工成品入库 */
		Map<String, Double> billDetail2015MapByF25 = parameterMaps.billDetail2015MapByF25;
		/** 2113外发加工成品返修 */
		Map<String, Double> billDetail2113MapByF25 = parameterMaps.billDetail2113MapByF25;
		/** 2114外发加工成品返修 */
		Map<String, Double> billDetail2114MapByF25 = parameterMaps.billDetail2114MapByF25;
		/** 2017外发加工成品返修 */
		Map<String, Double> billDetail2017MapByF25 = parameterMaps.billDetail2017MapByF25;
		/** 2101直接出口 */
		Map<String, Double> billDetail2101Map = parameterMaps.billDetail2101Map; // 

		/** 2102结转出口 */
		Map<String, Double> billDetail2102Map = parameterMaps.billDetail2102Map; // 

		/** 2103返回车间 */
		Map<String, Double> billDetail2103Map = parameterMaps.billDetail2103Map; // 

		/** 2106海关批准内销 */
		Map<String, Double> billDetail2106Map = parameterMaps.billDetail2106Map; // 

		/** 2107其他内销 */
		Map<String, Double> billDetail2107Map = parameterMaps.billDetail2107Map; // 

		/** wss:2010.04.19: 2110受托加工返回 + 2112受托加工成品出库 */
		Map<String, Double> billDetail2110And2112Map = parameterMaps.billDetail2112Map; // 2110
		// 2112

		/** wss:2010.04.19修改：4104外发加工返修成品 + 2113外发加工成品返修 */
		Map<String, Double> billDetail4104And2113Map = parameterMaps.billDetail4104And2113Map; // 4104

		/** F26入库成品-出库成品 */
		Map<String, Double> billDetailF26Map = parameterMaps.billDetailF26Map; //

		/** 存放在F26产品折料的料件与对应的报关 */
		Map<String, FirstSumInfo> billDetailF26MaterialMap = parameterMaps.billDetailF26MaterialMap;

		/** wss:2010.04.19修改：4003外发加工入库 + 2015外发加工成品入库 + 4006外发加工半成品入库 */
		Map<String, Double> billDetail4004And2015And4006And2017Map = parameterMaps.billDetail4004And2015And4006And2017Map; // 4003
		// 2015
		// 4006

		/** 2002车间入库 */
		Map<String, Double> billDetail2002MapWaste = parameterMaps.billDetail2002MapWaste;//

		/** 4003委外加工入库 */
		Map<String, Double> billDetail4003MapWaste = parameterMaps.billDetail4003MapWaste;//

		/** 2007受托加工车间入库 */
		Map<String, Double> billDetail2007MapWaste = parameterMaps.billDetail2007MapWaste;//

		/** 2103返回车间 */
		Map<String, Double> billDetail2103MapWaste = parameterMaps.billDetail2103MapWaste;//

		/** 2110受托加工返回 */
		Map<String, Double> billDetail2110MapWaste = parameterMaps.billDetail2110MapWaste;//

		// /**wss:2010.04.19新增 受托加工成品出库*/
		// Map<String, Double> billDetail2112MapWaste =
		// parameterMaps.billDetail2112MapWaste;

		/** 4104外发加工半成品返修 */
		Map<String, Double> billDetail4104MapWaste = parameterMaps.billDetail4104MapWaste;// 4104

		/** 4105外发加工领料 */
		Map<String, Double> billDetail4105MapWaste = parameterMaps.billDetail4105MapWaste;// 4105

		/** 2002车间入库 to F25 */
		Map<String, Double> billDetail2002MapByF25 = parameterMaps.billDetail2002MapByF25;// 2002

		/** 2007受托加工车间入库to F25 */
		Map<String, Double> billDetail2007MapByF25 = parameterMaps.billDetail2007MapByF25;// 2007

		/** 2010受托加工退回成品to F25 */
		Map<String, Double> billDetail2110MapByF25 = parameterMaps.billDetail2110MapByF25;// 2110

		/** 2103返回车间to F25 */
		Map<String, Double> billDetail2103MapByF25 = parameterMaps.billDetail2103MapByF25;// 2103

		/**
		 * 5001残次品 成品、半成品部分 wss:2010.07.01新加
		 */
		Map<String, Double> billDetail5001FinishedAndSemiBadProductMapByF25 = parameterMaps.billDetail5001FinishedAndSemiBadProductMapByF25;
		/**
		 * 4001半成品入库 wss:2010.07.01新加 排除了期初单
		 */
		Map<String, Double> billDetail4001SemiProductMapByF25 = parameterMaps.billDetail4001SemiProductMapByF25;

		/**
		 * 4101半成品出库 wss:2010.07.01新加
		 */
		Map<String, Double> billDetail4101SemiProductMapByF25 = parameterMaps.billDetail4101SemiProductMapByF25;

		// 存取分页查询bom时所临时存放的成品列表
		/** ******* 遍历成品、半成品、残次品(成品半成品部分)单据 */
		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		
		int size = list.size();
		/*
		 * 获取成品版本列表
		 */
		Object[] objs = null;
		for (int i = 0; i < size; i++) {
			objs = (Object[]) list.get(i);
			String versionId = (String) objs[2]; // 报关常用工厂BOM版本id

			if (versionId == null) {
				logger.info("单据中成品对应的BOM版本为空！" );
				continue;
			}

			// 为了去掉重复的 versionId
			versionIdMap.put(versionId, versionId);// 版本号id 对应 版本号id
			tempList.add(objs);
		}
		
		beginTime = System.currentTimeMillis();
		String clientTipMessage = "CAS 成品总记录 " + size
				+ " 条 正在(折成料件用量)计算...";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		List<String> versionIdList = new ArrayList<String>();
		versionIdList.addAll(versionIdMap.values());
		List materialBomDetailList = this.materialManageDao
				.findMaterielBomDetail(versionIdList);
		logger.info("开始查询 " + versionIdList.size() + " 个版本时间 "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
		
		if (materialBomDetailList.size() <= 0) {
			logger.info(" 对应的BOM明细为空!! ");
			tempList.clear();
		}
		
		/*
		 * 获取成品版本号对应的料件明细
		 */ 
		Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
		for (int j = 0, n = materialBomDetailList.size(); j < n; j++) {
			Object[] bomObjs = (Object[]) materialBomDetailList.get(j);
			String tempVersionId = (String) bomObjs[1];// 该bom版本号
			if (bomObjectMap.get(tempVersionId) == null) {
				List<Object[]> temp = new ArrayList<Object[]>();
				temp.add(bomObjs);
				bomObjectMap.put(tempVersionId, temp);
			} else {
				List<Object[]> temp = bomObjectMap.get(tempVersionId);
				temp.add(bomObjs);// 该版本号 对应 多个bom
			}
		}
		

		
		objs = null;
		for (int j = 0, n = tempList.size(); j < n; j++) {
			objs = (Object[]) tempList.get(j);// 成品单据体 及其对应的版本等
			Double ptAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 工厂数量
			String typeCode = (String) objs[1]; // 类型代码
			String tempVersionId = (String) objs[2]; // version
			// guid版本号id

			List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);// 相应版本的

			if (tempObjes == null) {
				logger.info("Cas 计算时 Bom 明细为空  !! == " + tempVersionId);
				continue;
			}

			
			FactoryAndFactualCustomsRalation ffcr = null;
			String ptNoKey = null;
			StatCusNameRelationHsn sr = null;
			String hsName = null;
			String hsSpec = null;
			String unitName = null;
			for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
				Object[] bomObjs = tempObjes.get(k);

				// 料件料号为 key
				ptNoKey = (String) bomObjs[0];
				//
				// 得到成品料号的 对应关系
				//
				ffcr = parameterMaps.statCusNameRelationMtValueByMaterielMap
						.get(ptNoKey);
				if(ffcr == null) {
					continue;
				}
				
				sr = ffcr.getStatCusNameRelationHsn();
				if(sr == null) {
					continue;
				} else {
					hsName = sr.getCusName() == null ? ""
							: sr.getCusName();
					hsSpec = sr.getCusSpec() == null ? ""
							: sr.getCusSpec();
					unitName = sr.getCusUnit() == null ? ""
							: sr.getCusUnit()
									.getName();
					unitName = unitName == null ? "" : unitName;
				}
				
				// key=名称+规格+单位
				String key = hsName + " / " + hsSpec + " / " + unitName;
				Object[] obj = new Object[6];
				obj[0] = 0.0;
				obj[1] = 0.0;
				obj[2] = "折料";
				obj[3] = hsName;
				obj[4] = hsSpec;
				obj[5] = unitName;
				FirstSumInfo firstSumInfo = new FirstSumInfo(obj);
				billDetailF26MaterialMap.put(key, firstSumInfo);
				
				
				
				// 成品单耗
				Double unitWaste = bomObjs[2] == null ? 0.0
						: (Double) bomObjs[2];

				// 成品单项用量
				Double unitUsed = bomObjs[3] == null ? 0.0
						: (Double) bomObjs[3];

				// 损耗
				Double waste = bomObjs[4] == null ? 0.0
						: (Double) bomObjs[4];
				/**
				 * 成品 单项 总用量 耗用总量=成品数量*(单耗/(1-损耗率)
				 */
				Double productMaterialUnitUsed = ptAmount
						* (unitWaste / (1 - waste));
				/**
				 * 成品 单耗 总用量
				 */
				Double productMaterialUnitWaste = ptAmount
						* (unitWaste);

				/**
				 * 成品 损耗 总用量
				 */
				Double productMaterialWaste = ptAmount * unitUsed
						* waste;

				if (typeCode.equals("2001")) {// F1=成品期初+半成品期初
					if (billDetail2001And4009Map.get(ptNoKey) == null) {
						billDetail2001And4009Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2001And4009Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2001And4009Map
								.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("4009")) {// F1=成品期初+半成品期初
					if (billDetail2001And4009Map.get(ptNoKey) == null) {
						billDetail2001And4009Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2001And4009Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2001And4009Map
								.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2002")) {// 2002 车间入库
					// 单耗总用量
					if (billDetail2002Map.get(ptNoKey) == null) {
						billDetail2002Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2002Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2002Map.put(ptNoKey, tempAmout);
					}
					// 损耗总用量
					if (billDetail2002MapWaste.get(ptNoKey) == null) {
						billDetail2002MapWaste.put(ptNoKey,
								productMaterialWaste);
					} else {
						Double tempAmout = billDetail2002MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialWaste;
						billDetail2002MapWaste.put(ptNoKey, tempAmout);
					}
					// 单项总用量
					if (billDetail2002MapByF25.get(ptNoKey) == null) {
						billDetail2002MapByF25.put(ptNoKey,
								productMaterialUnitUsed);
					} else {
						Double tempAmout = billDetail2002MapByF25
								.get(ptNoKey);
						tempAmout += productMaterialUnitUsed;
						billDetail2002MapByF25.put(ptNoKey, tempAmout);
					}

				} else if (typeCode.equals("2007")) {// 2007 受托加工车间入库
					// 单耗
					if (billDetail2007Map.get(ptNoKey) == null) {
						billDetail2007Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2007Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2007Map.put(ptNoKey, tempAmout);
					}

					// 损耗
					if (billDetail2007MapWaste.get(ptNoKey) == null) {
						billDetail2007MapWaste.put(ptNoKey,
								productMaterialWaste);
					} else {
						Double tempAmout = billDetail2007MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialWaste;
						billDetail2007MapWaste.put(ptNoKey, tempAmout);
					}

					// 单项用量
					if (billDetail2007MapByF25.get(ptNoKey) == null) {
						billDetail2007MapByF25.put(ptNoKey,
								productMaterialUnitUsed);
					} else {
						Double tempAmout = billDetail2007MapByF25
								.get(ptNoKey);
						tempAmout += productMaterialUnitUsed;
						billDetail2007MapByF25.put(ptNoKey, tempAmout);
					}

				} else if (typeCode.equals("2009")) {// 2009 结转成品退货单
					// 单耗
					if (billDetail2009Map.get(ptNoKey) == null) {
						billDetail2009Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2009Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2009Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2014")) {// 2009 结转成品退货单
					// 单耗
					if (billDetail2014MapWaste.get(ptNoKey) == null) {
						billDetail2014MapWaste.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2014MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2014MapWaste.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2015")) {// 2015外发加工成品入库*BOM
					// 单耗
					if (billDetail2015MapWaste.get(ptNoKey) == null) {
						billDetail2015MapWaste.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2015MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2015MapWaste.put(ptNoKey, tempAmout);
					}
					// 单项用量
					if (billDetail2015MapByF25.get(ptNoKey) == null) {
						billDetail2015MapByF25.put(ptNoKey,
								productMaterialUnitUsed);
					} else {
						Double tempAmout = billDetail2015MapByF25
								.get(ptNoKey);
						tempAmout += productMaterialUnitUsed;
						billDetail2015MapByF25.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2113")) {// 2113外发加工成品返修*BOM
					// 单耗
					if (billDetail2113MapWaste.get(ptNoKey) == null) {
						billDetail2113MapWaste.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2113MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2113MapWaste.put(ptNoKey, tempAmout);
					}
					// 单项用量
					if (billDetail2113MapByF25.get(ptNoKey) == null) {
						billDetail2113MapByF25.put(ptNoKey,
								productMaterialUnitUsed);
					} else {
						Double tempAmout = billDetail2113MapByF25
								.get(ptNoKey);
						tempAmout += productMaterialUnitUsed;
						billDetail2113MapByF25.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2114")) {// 2114外发加工成品出库*BOM
					// 单项用量
					if (billDetail2114MapByF25.get(ptNoKey) == null) {
						billDetail2114MapByF25.put(ptNoKey,
								productMaterialUnitUsed);
					} else {
						Double tempAmout = billDetail2114MapByF25
								.get(ptNoKey);
						tempAmout += productMaterialUnitUsed;
						billDetail2114MapByF25.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2017")) {// 2017外发加工成品退回*BOM
					// 单项用量
					if (billDetail2017MapByF25.get(ptNoKey) == null) {
						billDetail2017MapByF25.put(ptNoKey,
								productMaterialUnitUsed);
					} else {
						Double tempAmout = billDetail2017MapByF25
								.get(ptNoKey);
						tempAmout += productMaterialUnitUsed;
						billDetail2017MapByF25.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2111")// 2111受托加工成品退回
						|| typeCode.equals("2016")) {// wss:2010.04.19新增：2016受托加工成品返修
					// 单耗
					if (billDetail2111And2016Map.get(ptNoKey) == null) {
						billDetail2111And2016Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2111And2016Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2111And2016Map
								.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2101")) {// 2101 直接出口
					// 单耗
					if (billDetail2101Map.get(ptNoKey) == null) {
						billDetail2101Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2101Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2101Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2102")) {// 2102 结转出口
					// 单耗
					if (billDetail2102Map.get(ptNoKey) == null) {
						billDetail2102Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2102Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2102Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2103")) {// 2103 返回车间

					// 单耗
					if (billDetail2103Map.get(ptNoKey) == null) {
						billDetail2103Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2103Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2103Map.put(ptNoKey, tempAmout);
					}

					// 损耗
					if (billDetail2103MapWaste.get(ptNoKey) == null) {
						billDetail2103MapWaste.put(ptNoKey,
								productMaterialWaste);
					} else {
						Double tempAmout = billDetail2103MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialWaste;
						billDetail2103MapWaste.put(ptNoKey, tempAmout);
					}

					// 单项用量
					if (billDetail2103MapByF25.get(ptNoKey) == null) {
						billDetail2103MapByF25.put(ptNoKey,
								productMaterialUnitUsed);
					} else {
						Double tempAmout = billDetail2103MapByF25
								.get(ptNoKey);
						tempAmout += productMaterialUnitUsed;
						billDetail2103MapByF25.put(ptNoKey, tempAmout);
					}

				} else if (typeCode.equals("2106")
						|| typeCode.equals("5101")) {// 2106 海关批准内销 ,
					// 残次品出库 5101
					// 单耗
					if (billDetail2106Map.get(ptNoKey) == null) {
						billDetail2106Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2106Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2106Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2107")) {// 2107 其他内销
					// 单耗
					if (billDetail2107Map.get(ptNoKey) == null) {
						billDetail2107Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2107Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2107Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2110")// 2110 受托加工返回
						|| typeCode.equals("2112")) {// wss:2010.04.19新增：2112受托加工成品出库
					// 单耗
					if (billDetail2110And2112Map.get(ptNoKey) == null) {
						billDetail2110And2112Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2110And2112Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2110And2112Map
								.put(ptNoKey, tempAmout);
					}

					// 损耗
					if (billDetail2110MapWaste.get(ptNoKey) == null) {
						billDetail2110MapWaste.put(ptNoKey,
								productMaterialWaste);
					} else {
						Double tempAmout = billDetail2110MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialWaste;
						billDetail2110MapWaste.put(ptNoKey, tempAmout);
					}

					// 单项用量
					if (billDetail2110MapByF25.get(ptNoKey) == null) {
						billDetail2110MapByF25.put(ptNoKey,
								productMaterialUnitUsed);
					} else {
						Double tempAmout = billDetail2110MapByF25
								.get(ptNoKey);
						tempAmout += productMaterialUnitUsed;
						billDetail2110MapByF25.put(ptNoKey, tempAmout);
					}

				} else if (typeCode.equals("4004")// 4004外发加工半成品退回
						|| typeCode.equals("4006")// wss:2010.04.19新增：4006外发加工半成品入库
						|| typeCode.equals("2015")// 2015外发加工成品入库
						|| typeCode.equals("2017")) {// 2017外发加工成品退回
					// 单耗
					if (billDetail4004And2015And4006And2017Map
							.get(ptNoKey) == null) {
						billDetail4004And2015And4006And2017Map.put(
								ptNoKey, productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail4004And2015And4006And2017Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail4004And2015And4006And2017Map.put(
								ptNoKey, tempAmout);
					}

					// 损耗
					if (billDetail4003MapWaste.get(ptNoKey) == null) {
						billDetail4003MapWaste.put(ptNoKey,
								productMaterialWaste);
					} else {
						Double tempAmout = billDetail4003MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialWaste;
						billDetail4003MapWaste.put(ptNoKey, tempAmout);
					}

				} else if (typeCode.equals("4104")// 4104 外发加工返修半成品
						|| typeCode.equals("2113")) {// wss:2010.04.19新增：2113外发加工成品返修
					// 单耗
					if (billDetail4104And2113Map.get(ptNoKey) == null) {
						billDetail4104And2113Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail4104And2113Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail4104And2113Map
								.put(ptNoKey, tempAmout);
					}

					// 损耗
					if (billDetail4104MapWaste.get(ptNoKey) == null) {
						billDetail4104MapWaste.put(ptNoKey,
								productMaterialWaste);
					} else {
						Double tempAmout = billDetail4104MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialWaste;
						billDetail4104MapWaste.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("4105")) {// 4105 外发加工领料
					// 单耗
					if (billDetail4105MapWaste.get(ptNoKey) == null) {
						billDetail4105MapWaste.put(ptNoKey,
								productMaterialWaste);
					} else {
						Double tempAmout = billDetail4105MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialWaste;
						billDetail4105MapWaste.put(ptNoKey, tempAmout);
					}
				}

				// wss:2010.07.01新加
				else if (typeCode.equals("4001")) {// 4001 半成品入库
					if (billDetail4001SemiProductMapByF25.get(ptNoKey) == null) {
						billDetail4001SemiProductMapByF25.put(ptNoKey,
								productMaterialUnitUsed);
					} else {
						Double tempAmout = billDetail4001SemiProductMapByF25
								.get(ptNoKey);
						tempAmout += productMaterialUnitUsed;
						billDetail4001SemiProductMapByF25.put(ptNoKey,
								tempAmout);
					}
				}
				// wss:2010.07.01新加
				else if (typeCode.equals("4101")) {// 4101半成品出库
					if (billDetail4101SemiProductMapByF25.get(ptNoKey) == null) {
						billDetail4101SemiProductMapByF25.put(ptNoKey,
								productMaterialUnitUsed);
					} else {
						Double tempAmout = billDetail4101SemiProductMapByF25
								.get(ptNoKey);
						tempAmout += productMaterialUnitUsed;
						billDetail4101SemiProductMapByF25.put(ptNoKey,
								tempAmout);
					}
				}
				// wss:2010.07.01新加
				else if (typeCode.equals("5001")) {// 5001残次品入库
					// 半成品、成品部分折料单耗
					if (billDetail5001FinishedAndSemiBadProductMapByF25
							.get(ptNoKey) == null) {
						billDetail5001FinishedAndSemiBadProductMapByF25
								.put(ptNoKey, productMaterialUnitUsed);
					} else {
						Double tempAmout = billDetail5001FinishedAndSemiBadProductMapByF25
								.get(ptNoKey);
						tempAmout += productMaterialUnitUsed;
						billDetail5001FinishedAndSemiBadProductMapByF25
								.put(ptNoKey, tempAmout);
					}
				}

				//
				// F26 入库成品-出库成品
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
					if (billDetailF26Map.get(ptNoKey) == null) {
						billDetailF26Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetailF26Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetailF26Map.put(ptNoKey, tempAmout);
					}
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

					if (billDetailF26Map.get(ptNoKey) == null) {
						billDetailF26Map.put(ptNoKey,
								-productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetailF26Map
								.get(ptNoKey);
						tempAmout -= productMaterialUnitWaste;
						billDetailF26Map.put(ptNoKey, tempAmout);
					}
				}

			}
		}
	}

	/**
	 * 初始化第一个单据map
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param parameterMaps
	 * @param progressInfo
	 */
	private void initFistBillDetailMap(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		String productTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);
		String selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id from "
				+ productTableName
				+ " a "
				+ ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = 0 )) "
				+ " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ?  and a.ptAmount != 0  "
				+ "and a.billMaster.billType.code in('2002','2015','2017','2103','2113','2114')"
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次

		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(beginDate);
		params.add(endDate);
		params.add(CommonUtils.getCompany().getId());
		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, params.toArray());

		selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id "
				+ " from BillDetailHalfProduct a,MaterialBomVersion v  "
				+ " where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = 0))"
				// + "(select min(c.version) "
				// + " from MaterialBomVersion c "
				// + " where c.master=v.master))) "
				+ " and a.billMaster.isValid = ? "
				+ " and a.billMaster.validDate >= ? "
				+ " and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ? "
				+ " and a.billMaster.billType.code  in(?,?,?,?,?,?) "
				+ " group by a.billMaster.billType.code,v.id"
				+ " order by v.id ";// order by 确保 bom id 只查询一次

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(true);
		parameters.add(beginDate);
		parameters.add(endDate);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add("4001");// 半成品入库*BOM
		parameters.add("4101"); // 半成品出库*BOM
		parameters.add("4104");// 外发加工半成品返修*BOM
		parameters.add("4106"); // 外发加工半成品出库*BOM
		parameters.add("4004"); // 外发加工半成品退回*BOM
		parameters.add("4006"); // 外发加工半成品入库*BOM
		// 半成品
		List listHalf = casDao.commonSearch(selects, parameters.toArray());
		list.addAll(listHalf);
		// 残次品（半成品部分）
		selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id "
				+ " from BillDetailRemainProduct a,MaterialBomVersion v  "
				+ " where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version =0)) "
				// + "(select min(c.version) " + " from MaterialBomVersion c "
				// + " where c.master=v.master))) "
				+ " and a.billMaster.isValid = ? "
				+ " and a.billMaster.validDate >= ? "
				+ " and a.billMaster.validDate <= ? " + " and ("
				+ " (a.billMaster.billType.code =?  and a.note = ?) "
				+ " or (a.billMaster.billType.code =? and a.note in(?,?)) "
				+ " or (a.billMaster.billType.code =?  and a.note = ?)) "
				+ " and a.billMaster.company.id = ? "
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次

		parameters = new ArrayList<Object>();
		parameters.add(true);
		parameters.add(CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
				.getYear()), 0, 1));
		parameters.add(endDate);
		parameters.add("5101");
		parameters.add("半成品");

		parameters.add("5001");
		parameters.add("成品");
		parameters.add("料件");

		parameters.add("5002");
		parameters.add("半成品");

		parameters.add(CommonUtils.getCompany().getId());
		beginTime = System.currentTimeMillis();
		// 残次品
		List list5001 = casDao.commonSearch(selects, parameters.toArray());

		list.addAll(list5001);

		logger.info("CAS 开始查询所有成品的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
		/**
		 * 半成品结余 + 4002半成品盘盈单 + 4004外发加工半成品退 + 4006外发加工半成品入库 - 4102半成品盘亏单 -
		 * 4103委外加工出库 - 4104外发加工半成品返修 - 4106委外加工出库 - 5001残次品入库（半成品部分
		 */
		Map<String, Double> mapBillDetailPtAmountHalf = parameterMaps.mapBillDetailPtAmountHalf;
		//
		// 存取分页查询bom时所临时存放的成品列表
		//
		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String versionId = (String) objs[2]; // 报关常用工厂BOM版本id

			if (versionId == null) {
				continue;
			}
			//
			// 为了去掉重复的 versionId
			//
			versionIdMap.put(versionId, versionId);
			tempList.add(objs);

			if ((i != 0 && (versionIdMap.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				String clientTipMessage = "CAS 成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(versionIdMap.values());
				List materialBomDetailList = this.materialManageDao
						.findMaterielBomDetail(versionIdList);
				versionIdList.clear();
				versionIdMap.clear();

				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

				if (materialBomDetailList.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}
				//
				// 获取成品版本号对应的料件明细
				//
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = materialBomDetailList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) materialBomDetailList.get(j);
					String tempVersionId = (String) bomObjs[1];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(bomObjs);
					}
				}

				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double ptAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 工厂数量
					String typeCode = (String) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // version guid

					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);

					if (tempObjes == null) {
						logger.info("Cas 计算时 Bom 明细为空  !! == " + tempVersionId);
						continue;
					}

					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						//
						// 料件料号为 key
						//
						String ptNoKey = (String) bomObjs[0];

						//
						// 成品单耗
						//
						Double unitWaste = bomObjs[2] == null ? 0.0
								: (Double) bomObjs[2];
						//
						// 损耗
						//
						Double waste = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];
						//
						// 成品单耗用量
						//
						Double totalUnitWaste = ptAmount
								* (unitWaste / (1 - waste));

						if (typeCode.equals("2103") || typeCode.equals("2113")// 2113外发加工成品返修
								|| typeCode.equals("2114")// +2114外发加工成品出库
								|| typeCode.equals("4101")// 4101半成品出库*BOM
								|| typeCode.equals("4104")// 4104外发加工半成品返修*BOM
								|| typeCode.equals("4106")// 4106外发加工半成品出库*BOM
								|| typeCode.equals("5002")// 5002外发加工半成品入库
						) { // 入库
							if (mapBillDetailPtAmountHalf.get(ptNoKey) == null) {
								mapBillDetailPtAmountHalf.put(ptNoKey,
										totalUnitWaste);
							} else {
								Double tempAmout = mapBillDetailPtAmountHalf
										.get(ptNoKey);
								tempAmout += totalUnitWaste;
								mapBillDetailPtAmountHalf.put(ptNoKey,
										tempAmout);
							}
						}
						if (typeCode.equals("2002") || typeCode.equals("2015")// 2015外发加工成品入库
								|| typeCode.equals("2017")// 2017外发加工成品退回
								|| typeCode.equals("4001") // 4001半成品入库*BOM
								|| typeCode.equals("4004") // 4004外发加工半成品退回*BOM
								|| typeCode.equals("4006") // 4006外发加工半成品入库*BOM
								|| typeCode.equals("5101")// 5101残次品 出库（半成品部分
								|| typeCode.equals("5001")// 5001残次品 入库（成品、料件部分
						) { // 出库
							if (mapBillDetailPtAmountHalf.get(ptNoKey) == null) {
								mapBillDetailPtAmountHalf.put(ptNoKey,
										-totalUnitWaste);
							} else {
								Double tempAmout = mapBillDetailPtAmountHalf
										.get(ptNoKey);
								tempAmout -= totalUnitWaste;
								mapBillDetailPtAmountHalf.put(ptNoKey,
										tempAmout);
							}
						}
					}
				}
				tempList.clear();
			}
		}
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

		public FirstSumInfo() {

		}

		public FirstSumInfo(Object[] values) {
			hsAmount = (Double) values[0];
			money = (Double) values[1];
			typeCode = (String) values[2];
			hsName = (String) values[3];
			hsSpec = (String) values[4];
			hsUnitName = (String) values[5];
		}

		/**
		 * 取得报关数量
		 * 
		 * @return hsAmount 报关数量.
		 */
		public Double getHsAmount() {
			return hsAmount;
		}

		/**
		 * 设置报关数量
		 * 
		 * @param ptAmount
		 *            工厂数量.
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
		 * 取得报关名称
		 * 
		 * @return hsName 报关名称 .
		 */
		public String getHsName() {
			return hsName;
		}

		/**
		 * 设置报关名称
		 * 
		 * @param ptName
		 *            工厂名称
		 */
		public void setHsName(String ptName) {
			this.hsName = ptName;
		}

		/**
		 * 取得报关规格
		 * 
		 * @return hsSpec 报关规格.
		 */
		public String getHsSpec() {
			return hsSpec;
		}

		/**
		 * 设置报关规格
		 * 
		 * @param ptSpec
		 *            工厂规格
		 */
		public void setHsSpec(String ptSpec) {
			this.hsSpec = ptSpec;
		}

		/**
		 * 取得报关单位
		 * 
		 * @return hsUnitName 报关单位
		 */
		public String getHsUnitName() {
			return hsUnitName;
		}

		/**
		 * 设置报关单位
		 * 
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

	// ====================================================================================================
	// 以上全部是料件统计计算代码
	// ====================================================================================================

	/**
	 * 单据报关单对比
	 * 
	 * @param conditions
	 *            查询条件包括年度和有效期
	 * @return 料件的单据与进口报关单对比
	 */
	private Map<String, BillCustomBillCmpImgInfo> findBillImgInfos(
			Date beginDate, Date endDate) {
		HashMap<String, BillCustomBillCmpImgInfo> htResult = new HashMap<String, BillCustomBillCmpImgInfo>();

		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "1003", "")); // 1003直接报关进口
		conditions.add(new Condition("or", "", "billMaster.billType.code", "=",
				"1103", ""));// 料件复出
		conditions.add(new Condition("or", "", "billMaster.billType.code", "=",
				"1102", ")")); // 料件退换

		// 年度条件
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));

		// 物料明细表
		String billDetailMateriel = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);

		String selects = "select sum(a.hsAmount),a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name  ";
		String groupBy = "group by a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name ";
		String leftOuter = " left join a.hsUnit ";

		List billDetails = casDao.commonSearch(selects, billDetailMateriel,
				conditions, groupBy, leftOuter);

		logger
				.info("Jbcus 单据报关单对比 : billDetails size == "
						+ billDetails.size());

		for (int i = 0; i < billDetails.size(); i++) {
			Object[] billDetail = (Object[]) billDetails.get(i);
			Double hsAmount = billDetail[0] == null ? 0.0
					: (Double) billDetail[0];
			String typeCode = (String) billDetail[1];
			String hsName = billDetail[2] == null ? "" : (String) billDetail[2];
			String hsSpec = billDetail[3] == null ? "" : (String) billDetail[3];
			String hsUnitName = billDetail[4] == null ? ""
					: (String) billDetail[4];

			String key = hsName + " / " + hsSpec + " / " + hsUnitName;

			BillCustomBillCmpImgInfo cmpInfo = (BillCustomBillCmpImgInfo) htResult
					.get(key);
			if (cmpInfo == null) {
				cmpInfo = new BillCustomBillCmpImgInfo();

				cmpInfo.setPtName(hsName);
				cmpInfo.setPtSpec(hsSpec);
				cmpInfo.setPtUnitName(hsUnitName);

				htResult.put(key, cmpInfo);
			}

			if (typeCode.equals("1003")) { // 单据直接进口
				cmpInfo.setF2(cmpInfo.getF2() + hsAmount);
			} else if (typeCode.equals("1103")) { // 料件复出单据折料件
				cmpInfo.setFimgReOut(cmpInfo.getFimgReOut() + hsAmount);
			} else if (typeCode.equals("1102")) { // 料件退换
				cmpInfo.setFimgExchange(cmpInfo.getFimgExchange() + hsAmount);
			}
		}
		return htResult;
	}

	/**
	 * 单据报关单对比
	 * 
	 * @param conditions
	 *            查询条件包括有效期
	 * @return 海关帐中存在的料件单据与进口报关单对比
	 */
	public List<BillCustomBillCmpImgInfo> findBillCustomBillCmpImgInfos(
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
		Map<String, BillCustomBillCmpImgInfo> mapResult = this
				.findBillImgInfos(beginDate, endDate);
		//
		// 海关帐单据中没有记录根本不用统计了
		//
		if (mapResult.size() <= 0) {
			logger.info("Jbcus 单据报关单对比 : mapResult is size null ");
			return new ArrayList<BillCustomBillCmpImgInfo>();
		}

		List<Condition> conditions = new ArrayList<Condition>();
		// //////////////////////
		// 查找报关单据里面的报关数量
		// //////////////////////
		//
		// 以名称+规格+单位,来分组进行统计
		//
		String selects = "select sum(a.commAmount),a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.tradeMode.code ";
		String groupBy = "group by a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.tradeMode.code ";

		// 0265 来料料件复出
		// 0664 进料料件复出 [料件复出]
		conditions.add(new Condition("and", "(",
				"baseCustomsDeclaration.tradeMode.code", "=", "0265", null));// 来料料件复出
		conditions.add(new Condition("or", null,
				"baseCustomsDeclaration.tradeMode.code", "=", "0664", null));// 进料料件复出
		// 0300 来料料件退换
		// 0700 进料料件退换 [料件退换]
		conditions.add(new Condition("or", null,
				"baseCustomsDeclaration.tradeMode.code", "=", "0300", null));// 来料料件退换
		conditions.add(new Condition("or", null,
				"baseCustomsDeclaration.tradeMode.code", "=", "0700", null));// 进料料件退换
		// 0214 来料加工
		// 0615 进料对口 [直接进口]
		conditions.add(new Condition("or", null,
				"baseCustomsDeclaration.tradeMode.code", "=", "0214", null));// 来料加工
		conditions.add(new Condition("or", null,
				"baseCustomsDeclaration.tradeMode.code", "=", "0615", ")"));// 进料对口

		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.effective", "=", true, null));// 生效

		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpDate", ">=", beginDate, null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpDate", "<=", endDate, null));

		List listCustomsDeclarationCommInfo = casDao.commonSearch(selects,
				"CustomsDeclarationCommInfo", conditions, groupBy, "");
		List bcs = casDao.commonSearch(selects,
				"BcsCustomsDeclarationCommInfo", conditions, groupBy, "");
		if (bcs != null && bcs.size() != 0) {
			listCustomsDeclarationCommInfo.addAll(bcs);
		}
		List dzsc = casDao.commonSearch(selects,
				"DzscCustomsDeclarationCommInfo", conditions, groupBy, "");
		if (dzsc != null && dzsc.size() != 0) {
			listCustomsDeclarationCommInfo.addAll(dzsc);
		}

		for (int i = 0; i < listCustomsDeclarationCommInfo.size(); i++) {
			Object[] objs = (Object[]) listCustomsDeclarationCommInfo.get(i);
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String tradeCode = (String) objs[4];
			String key = hsName + " / " + hsSpec + " / " + unitName;

			BillCustomBillCmpImgInfo temp = mapResult.get(key);
			//
			// 不统计海关单据中不存在的
			//
			if (temp == null) {
				continue;
			}
			// 0214 来料加工
			// 0615 进料对口
			if (tradeCode.equals("0214") || tradeCode.equals("0615")) {// [直接进口]
				temp.setF2CustomNum(CommonUtils.getDoubleExceptNull(temp
						.getF2CustomNum())
						+ hsAmount);
			} else if (tradeCode.equals("0265") || tradeCode.equals("0664")) { // [料件复出]
				// 0265 来料料件复出
				// 0664 进料料件复出
				temp.setFimgReOutCustomNum(CommonUtils.getDoubleExceptNull(temp
						.getFimgReOutCustomNum())
						+ +hsAmount);
			} else if (tradeCode.equals("0300") || tradeCode.equals("0700")) { // [料件退换]
				// 0300 来料料件退换
				// 0700 进料料件退换
				temp.setFimgExchangeCustomNum(CommonUtils
						.getDoubleExceptNull(temp.getFimgExchangeCustomNum())
						+ +hsAmount);
			}
		}
		List<BillCustomBillCmpImgInfo> resultList = new ArrayList<BillCustomBillCmpImgInfo>();
		resultList.addAll(mapResult.values());
		return resultList;
	}

	/**
	 * 重新载入海关资料(料件来自海关资料)
	 * 
	 */
	public void copyImgOrgUseInfoCustom() {
		//
		// 删除当年度的所有海关帐
		//
		this.casDao.deleteImgOrgUseInfoCustom(CommonUtils.getYear());
		//
		// 分页查询并修改
		//
		int index = 0;
		int length = 1000;
		while (true) {
			List tempBillDetails = this.casDao.findImgOrgUseInfo(index, length,
					CommonUtils.getYear());
			for (int i = 0; i < tempBillDetails.size(); i++) {
				ImgOrgUseInfo imgOrgUseInfo = (ImgOrgUseInfo) tempBillDetails
						.get(i);
				ImgOrgUseInfoCustom imgOrgUseInfoCustom = new ImgOrgUseInfoCustom();
				imgOrgUseInfoCustom.setCompany(imgOrgUseInfo.getCompany());
				imgOrgUseInfoCustom.setYear(imgOrgUseInfo.getYear());
				imgOrgUseInfoCustom.setMoney(imgOrgUseInfo.getMoney());
				imgOrgUseInfoCustom.setPtName(imgOrgUseInfo.getPtName());
				imgOrgUseInfoCustom.setPtSpec(imgOrgUseInfo.getPtSpec());
				imgOrgUseInfoCustom
						.setPtUnitName(imgOrgUseInfo.getPtUnitName());

				imgOrgUseInfoCustom.setF1(imgOrgUseInfo.getF1());
				imgOrgUseInfoCustom.setF2(imgOrgUseInfo.getF2());
				imgOrgUseInfoCustom.setF3(imgOrgUseInfo.getF3());
				imgOrgUseInfoCustom.setF4(imgOrgUseInfo.getF4());
				imgOrgUseInfoCustom.setF5(imgOrgUseInfo.getF5());
				imgOrgUseInfoCustom.setF6(imgOrgUseInfo.getF6());
				imgOrgUseInfoCustom.setF7(imgOrgUseInfo.getF7());
				imgOrgUseInfoCustom.setF8(imgOrgUseInfo.getF8());
				imgOrgUseInfoCustom.setF9(imgOrgUseInfo.getF9());
				imgOrgUseInfoCustom.setF10(imgOrgUseInfo.getF10());
				imgOrgUseInfoCustom.setF12(imgOrgUseInfo.getF12());
				imgOrgUseInfoCustom.setF13(imgOrgUseInfo.getF13());
				imgOrgUseInfoCustom.setF14(imgOrgUseInfo.getF14());
				imgOrgUseInfoCustom.setF15(imgOrgUseInfo.getF15());
				imgOrgUseInfoCustom.setF16(imgOrgUseInfo.getF16());
				imgOrgUseInfoCustom.setF17(imgOrgUseInfo.getF17());
				imgOrgUseInfoCustom.setF18(imgOrgUseInfo.getF18());
				imgOrgUseInfoCustom.setF19(imgOrgUseInfo.getF19());
				imgOrgUseInfoCustom.setF20(imgOrgUseInfo.getF20());
				imgOrgUseInfoCustom.setF21(imgOrgUseInfo.getF21());
				// imgOrgUseInfoCustom.setF22(imgOrgUseInfo.getF22());
				// imgOrgUseInfoCustom.setF23(imgOrgUseInfo.getF23());
				imgOrgUseInfoCustom.setF24(imgOrgUseInfo.getF24());
				imgOrgUseInfoCustom.setF25(imgOrgUseInfo.getF25());
				imgOrgUseInfoCustom.setF26(imgOrgUseInfo.getF26());
				imgOrgUseInfoCustom.setF27(imgOrgUseInfo.getF27());

				this.casDao.getHibernateTemplate().save(imgOrgUseInfoCustom);
			}
			index += length;
			if (tempBillDetails.size() <= 0 || tempBillDetails.size() < length) {
				break;
			}
		}
	}

	/**
	 * 统计来自报关单的金额(进口料件) 料件进口 + 料件转厂 + 一般贸易进口 - 退料出口
	 * 
	 * @param isCustoms
	 *            是否是报关用
	 * @return 报关单金额 (进口料件)
	 */

	public List<ImgOrgUseInfoBase> sumMoneyFromApplyToCustomsByMateriel(
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
		String selects = "select sum(a.commTotalPrice),a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.impExpType,a.baseCustomsDeclaration.currency.code,a.baseCustomsDeclaration.impExpDate";
		String groupBy = "  group by a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.impExpType,a.baseCustomsDeclaration.currency.code,a.baseCustomsDeclaration.impExpDate ";// wss:2010.04.30:增加了报关单生效日期
		String leftOuter = "  left join a.unit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", "(",
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.DIRECT_IMPORT, null));// 直接进口
		conditions.add(new Condition("or", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.TRANSFER_FACTORY_IMPORT, null));// 料件转厂
		conditions.add(new Condition("or", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.GENERAL_TRADE_IMPORT, null));// 一般贸易进口（料件）
		conditions.add(new Condition("or", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.BACK_MATERIEL_EXPORT, ")"));// 退料出口

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

		// 三种手册类型，放在一起
		List listCustomsDeclarationCommInfo = casDao.commonSearch(selects,
				"CustomsDeclarationCommInfo ", conditions, groupBy, leftOuter);
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
		// key = 报关名称+" / "+规格+" / "+单位名称
		//
		System.out.println("listCustomsDeclarationCommInfo.size() == "
				+ listCustomsDeclarationCommInfo.size());
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
			// wss:2010.04.30增加 报关单生效日期 （为取得汇率）
			Date validDate = (Date) objs[6];

			Double rate = casDao.findCurrRateByM((String) objs[5], validDate);

			System.out.println("wss rate = " + rate);
			if (rate == 0.0) {
				hsAmount = hsAmount1;
			} else
				hsAmount = hsAmount1 * rate;// 得到汇率换算
			String key = hsName + " / " + hsSpec + " / " + unitName;
			if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {// 退料出口 减
				if (mapCustomCommInfo.get(key) == null) {
					mapCustomCommInfo.put(key, -hsAmount);
				} else {
					Double tempHsAmount = mapCustomCommInfo.get(key);
					tempHsAmount -= hsAmount;
					mapCustomCommInfo.put(key, tempHsAmount);
				}
			} else { // 直接进口,料件转厂,一般贸易进口（料件） 全为加
				if (mapCustomCommInfo.get(key) == null) {
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

		// List<StatCusNameRelationHsn> listExistStatCusNameRelationHsn =
		// this.casDao
		// .findStatCusNameRelationHsn(MaterielType.MATERIEL);
		// for (int i = 0; i < listExistStatCusNameRelationHsn.size(); i++) {
		// StatCusNameRelationHsn sh = listExistStatCusNameRelationHsn.get(i);
		// String hsName = sh.getCusName() == null ? "" : sh.getCusName();
		// String hsSpec = sh.getCusSpec() == null ? "" : sh.getCusSpec();
		// String unitName = sh.getCusUnit() == null ? "" : sh.getCusUnit()
		// .getName();
		// unitName = unitName == null ? "" : unitName;
		// String tenKey = hsName + " / " + hsSpec + " / " + unitName;
		// System.out.println(tenKey);

		// StatCusNameRelation sc = sh.getStatCusNameRelation();
		// String bigHsName = sc.getCusName() == null ? "" : sc.getCusName();
		// String bigHsSpec = sc.getCusSpec() == null ? "" : sc.getCusSpec();
		// String bigUnitName = sc.getCusUnit() == null ? "" : sc.getCusUnit()
		// .getName();
		// bigUnitName = bigUnitName == null ? "" : bigUnitName;
		// String bigTenKey = bigHsName + " / " + bigHsSpec + " / " + bigUnitName;

		//
		// 获取报关的数量
		//
		// Double amount = mapCustomCommInfo.get(tenKey) == null ? 0.0
		// : mapCustomCommInfo.get(tenKey);

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
		List<ImgOrgUseInfoBase> returnList = new ArrayList<ImgOrgUseInfoBase>();
		int index = 0;
		int length = 1000;

		while (true) {
			List tempBillDetails = null;// 加工贸易原材料来源与使用情况表
			if (isCustoms) {
				tempBillDetails = this.casDao.findImgOrgUseInfoCustom(index,
						length, CommonUtils.getYear());
			} else {
				tempBillDetails = this.casDao.findImgOrgUseInfo(index, length,
						CommonUtils.getYear());
			}

			for (int i = 0; i < tempBillDetails.size(); i++) {

				ImgOrgUseInfoBase imgOrgUseInfo = (ImgOrgUseInfoBase) tempBillDetails
						.get(i);

				String hsName = imgOrgUseInfo.getPtName() == null ? ""
						: imgOrgUseInfo.getPtName();
				String hsSpec = imgOrgUseInfo.getPtSpec() == null ? ""
						: imgOrgUseInfo.getPtSpec();
				String unitName = imgOrgUseInfo.getPtUnitName() == null ? ""
						: imgOrgUseInfo.getPtUnitName();
				String key = hsName + " / " + hsSpec + " / " + unitName;
				Double money = mapCustomCommInfo.get(key) == null ? 0
						: mapCustomCommInfo.get(key);
				System.out.println("wss mapCustomCommInfo.size() = "
						+ mapCustomCommInfo.size());
				System.out.println("wss key" + key);
				System.out.println("wss money =" + money);
				// imgOrgUseInfo.setMoney(money);
				imgOrgUseInfo
						.setMoney(imgOrgUseInfo.getNoCustomMoney() + money);
				this.casDao.getHibernateTemplate().save(imgOrgUseInfo);
				returnList.add(imgOrgUseInfo);
			}
			index += length;
			if (tempBillDetails.size() <= 0 || tempBillDetails.size() < length) {
				break;
			}
		}
		return returnList;

	}

	/**
	 * 获得自定义统计资料(料件统计报表)
	 * 
	 * @param year
	 *            年度
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 指定年度内的符合条件(是否超过百万)的料件自定义统计报表
	 */
	public List findImgOrgUseInfos(String year, Boolean isOverMillion) {

		List<ImgOrgUseInfoBase> returnList = new ArrayList<ImgOrgUseInfoBase>();
		List list = this.casDao.findImgOrgUseInfos(year);
		if (isOverMillion == true) {
			for (int i = 0; i < list.size(); i++) {
				// 由于缓存的问题所有要new
				ImgOrgUseInfoBase imgOrgUseInfo = (ImgOrgUseInfoBase) list
						.get(i);
				if (imgOrgUseInfo.getMoney() < 1000000) {
					ImgOrgUseInfoBase temp = new ImgOrgUseInfoBase();
					temp.setPtName(imgOrgUseInfo.getPtName());
					temp.setPtSpec(imgOrgUseInfo.getPtSpec());
					temp.setPtUnitName(imgOrgUseInfo.getPtUnitName());
					temp.setMoney(imgOrgUseInfo.getMoney());
					temp.setF27("金额小于一百万");
					returnList.add(temp);
				} else {
					returnList.add(imgOrgUseInfo);
				}
			}
			return returnList;
		} else {
			return list;
		}
	}

	/**
	 * 获得海关资料(料件统计报表)
	 * 
	 * @param year
	 *            年度
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 指定年度内的符合条件(是否超过百万)的料件统计报表
	 */
	public List findImgOrgUseInfoCustoms(String year, Boolean isOverMillion) {
		List<ImgOrgUseInfoBase> returnList = new ArrayList<ImgOrgUseInfoBase>();
		List list = this.casDao.findImgOrgUseInfoCustoms(year);
		if (isOverMillion == true) {
			for (int i = 0; i < list.size(); i++) {
				// 由于缓存的问题所有要new
				ImgOrgUseInfoBase imgOrgUseInfo = (ImgOrgUseInfoBase) list
						.get(i);
				if (imgOrgUseInfo.getMoney() < 1000000) {
					ImgOrgUseInfoBase temp = new ImgOrgUseInfoBase();
					temp.setPtName(imgOrgUseInfo.getPtName());
					temp.setPtSpec(imgOrgUseInfo.getPtSpec());
					temp.setPtUnitName(imgOrgUseInfo.getPtUnitName());
					temp.setMoney(imgOrgUseInfo.getMoney());
					temp.setF27("金额小于一百万");
					returnList.add(temp);
				} else {
					returnList.add(imgOrgUseInfo);
				}
			}
			return returnList;
		} else {
			return list;
		}
	}

	// ====================================================================================================
	// 以下全部是结转收货对比统计计算代码 (结转收货对比)
	// ====================================================================================================

	/**
	 * 4． 已结转未收货: 当相同客户名称的 （ “结转进口” 单据折算报关数量的累加 －“结转料件退货单” 单据折算报关数量的累加 <
	 * “结转报关进口”数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF3Map 已结转未收货
	 * 
	 * 5． 未结转已收货: 当相同客户名称的 （ “结转进口” 单据折算报关数量的累加 －“结转料件退货单” 单据折算报关数量的累加 >“结转报关进口”
	 * 数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF4Map 未结转已收货 *
	 * 
	 * @param conditions
	 *            查询条件
	 * @param scmCoc
	 *            客户
	 * @return 加工贸易原材料来源与使用情况表中的结转收货对比信息
	 */
	public List<CarryForwardCmpImgInfo> findCarryForwardCmpImgInfos(
			Date beginDate, Date endDate, ScmCoc scmCoc) {

		ParameterMaps parameterMaps = new ParameterMaps();

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
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "1004", ""));// 结转进口
		conditions.add(new Condition("or", "", "billMaster.billType.code", "=",
				"1106", ")")); // 1106 结转料件退货单
		// 年度条件
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));

		List carryForwardList = casDao.commonSearch(selects,
				"BillDetailMateriel", conditions, groupBy, leftOuter);
		if (carryForwardList.size() <= 0) {
			return new ArrayList<CarryForwardCmpImgInfo>();
		}
		//
		// 初始化结转数 (报关单)按客户来分的
		//
		this.initF3ByCustomer(beginDate, endDate, scmCoc, parameterMaps);

		//
		// 以 报关名+称规格+单位+客户 为key的 map
		//
		Map<String, Double> carryForwardByCustomerMap = parameterMaps.carryForwardByCustomerMap;

		//
		// 生成中间对象
		//
		HashMap<String, CarryForwardCmpImgInfo> htResult = new HashMap<String, CarryForwardCmpImgInfo>();
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

			String key = hsName + " / " + hsSpec + " / " + unitName + " / "
					+ scmCocCode;

			CarryForwardCmpImgInfo cmpInfo = (CarryForwardCmpImgInfo) htResult
					.get(key);
			if (cmpInfo == null) {
				cmpInfo = new CarryForwardCmpImgInfo();
				cmpInfo.setPtName(hsName);
				cmpInfo.setPtSpec(hsSpec);
				cmpInfo.setPtUnitName(unitName);
				cmpInfo.setCustomerName(scmCocName);
				htResult.put(key, cmpInfo);
			}
			if (typeCode.equals("1004")) {
				cmpInfo.setHsAmount1004(cmpInfo.getHsAmount1004() + hsAmount);
			} else {
				cmpInfo.setHsAmount1106(cmpInfo.getHsAmount1106() + hsAmount);
			}
			cmpInfo.setCustomNum(carryForwardByCustomerMap.get(key) == null ? 0
					: carryForwardByCustomerMap.get(key));
		}
		List<CarryForwardCmpImgInfo> resultList = new ArrayList<CarryForwardCmpImgInfo>();
		resultList.addAll(htResult.values());
		return resultList;
	}

	/**
	 * 3． 结转报关进口: 进口类型 为 “ 料件转厂”、贸易方式为 “来/进料深加工”且进口日期在本帐目年度的报关单数量的累加
	 * 
	 * 生成 以 报关名称+" / "+规格+" / "+单位为 key 的 carryForwardMap 生成 以 报关名称+" / "+规格+" / "+单位 +
	 * " / " +客户代码为 key 的 carryForwardByCustomerMap
	 */
	private void initF3ByCustomer(Date beginDate, Date endDate, ScmCoc scmCoc,
			ParameterMaps parameterMaps) {
		//
		// 初始化大类对应关系
		//
		// this.initStatCusNameRelationHsMap(parameterMaps);
		//
		// key :真实的 报关名称+" / "+规格+" / "+单位名称
		//
		// Map<String, String> statCusNameRelationHsMap =
		// parameterMaps.statCusNameRelationHsMap;
		//
		// 以 报关名称规格+单位为key的 map
		//
		Map<String, Double> f3Map = parameterMaps.f3Map;
		//
		// 以 报关名+称规格+单位+客户 为key的 map
		//
		Map<String, Double> carryForwardByCustomerMap = parameterMaps.carryForwardByCustomerMap;
		//
		// 以名称+规格+单位,客户来分组进行统计
		//
		String selects = "select sum(a.commAmount),a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.customer.code  ";
		String groupBy = "group by a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.customer.code ";
		String leftOuter = " left join a.unit left join a.baseCustomsDeclaration.customer ";
		List<Condition> conditions = new ArrayList<Condition>();
		if (scmCoc != null) {
			conditions.add(new Condition("and", null,
					"baseCustomsDeclaration.customer.id", "=", scmCoc.getId(),
					null));
		}
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.TRANSFER_FACTORY_IMPORT, null));
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

		// conditions.add(new Condition("and", "(",
		// "baseCustomsDeclaration.tradeMode.code", "=", "0255", null));// 来料深加工
		// conditions.add(new Condition("or", null,
		// "baseCustomsDeclaration.tradeMode.code", "=", "0654", ")"));// 进料深加工

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
	 * 改变记账年度是生成料件期初单与在产品期初单
	 * 
	 * @param taskId
	 * @return
	 */
	public Map<String, Map<String, BillInit>> getMaterielF24F25ForBill(
			String taskId, Date beginDate, Date endDate) {
//		ProgressInfo progressInfo = ProcExeProgress.getInstance()
//				.getProgressInfo("taskId");
		if (beginDate == null) {
			beginDate = CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
					.getYear()), 0, 1);
		}
		if (endDate == null) {
			endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
					.getYear()), 11, 31);
		}

		// 查找料件，计算F24,25栏
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));
		conditions.add(new Condition("and", null, "ptAmount", "!=", 0.0, null));

		// 单据体为 料件单据体
		String billDetailMateriel = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);

		// 数量、单据类型、料号、报关编码、报关名称、报关规格、仓库
		String selects = "select sum(a.ptAmount),a.billMaster.billType.code,a.ptPart,a.complex.code,a.hsName,a.hsSpec,a.wareSet.code  ";

		String groupBy = "group by a.billMaster.billType.code,a.ptPart,a.complex.code,a.hsName,a.hsSpec,a.wareSet.code ";
		String leftOuter = " left join a.billMaster left join a.complex left join a.billMaster.billType	left join a.wareSet ";
		List<Object[]> listBillDetailMateriel = casDao.commonSearch(selects,
				billDetailMateriel, conditions, groupBy, leftOuter);

		HashMap<String, BillInit> f24Map = new HashMap();
		HashMap<String, BillInit> f244Map = new HashMap();
		HashMap<String, BillInit> f25Map = new HashMap();
		HashMap<String, BillInit> f255Map = new HashMap();

		for (Object[] item : listBillDetailMateriel) {
			Double ptAmount = (Double) item[0] == null ? 0.0 : (Double) item[0];
			String typeCode = (String) item[1];
			String ptNo = item[2] == null ? "" : item[2].toString();
			String hsNo = item[3] == null ? "" : item[3].toString();
			String hsName = item[4] == null ? "" : item[4].toString();
			String hsSpec = item[5] == null ? "" : item[5].toString();
			String wareSetCode = item[6] == null ? "" : item[6].toString();

			/**
			 * 1.料件期初单
			 */
			// String key = ptNo + " / " + wareSetCode;//料号 + 仓库代码
			// wss2010.08.26 Key改成 料号 + 报关编码 + 报关名称 + 报关规格 + 仓库
			String key = ptNo + " / " + hsNo + " / " + hsName + " / " + hsSpec + " / "
					+ wareSetCode;

			if (typeCode != null && ptNo != null && !ptNo.equals("")) {

				if (typeCode.equals("1001") // 1001 料件期初单
						|| typeCode.equals("1003") // 1003 直接进口
						|| typeCode.equals("1004") // 1004 结转进口
						|| typeCode.equals("1005") // 1005 国内购买
						|| typeCode.equals("1006") // 1006 车间返回
						|| typeCode.equals("1007") // 1007 料件盘盈单
						|| typeCode.equals("1008") // 1008 受托加工进库
						|| typeCode.equals("1009") // 1009 其他来源
						|| typeCode.equals("1010") // 1010 料件转仓入库
						|| typeCode.equals("1011") // 1011 海关征税进口
						|| typeCode.equals("1017") // 1017 外发加工料件退回
						|| typeCode.equals("1018") // 1018 外发加工料件入库
						|| typeCode.equals("1019") // 1019 受托加工料件返修
						|| typeCode.equals("5001"))// 5001 残次品入库
				{
					if (f24Map.get(key) == null) {
						BillInit temp = new BillInit();
						temp.setYear(CommonUtils.getYear());
						temp.setTypeCode("1001");// 1001料件期初单
						temp.setPtAmount(ptAmount);
						temp.setPtNo(ptNo);
						temp.setHsNo(hsNo);
						temp.setHsName(hsName);
						temp.setHsSpec(hsSpec);
						temp.setWareSetCode(wareSetCode);
						f24Map.put(key, temp);
					} else {
						f24Map.get(key).setPtAmount(
								f24Map.get(key).getPtAmount() + ptAmount);
					}
				} else if (typeCode.equals("1101") // 1101 车间领用
						|| typeCode.equals("1102") // 1102 料件退换
						|| typeCode.equals("1103") // 1103 料件复出
						|| typeCode.equals("1104") // 1104 料件盘亏单
						|| typeCode.equals("1105") // 1105 料件转仓出库
						|| typeCode.equals("1106") // 1106 结转料件退货单
						|| typeCode.equals("1107") // 1107 其他料件退货单
						|| typeCode.equals("1108") // 1108 其他领用
						|| typeCode.equals("1109") // 1109 受托加工领用
						|| typeCode.equals("1111") // 1111 受托加工退回料件
						|| typeCode.equals("1115") // 1115 海关批准内销
						|| typeCode.equals("1113") // 1113 外发加工料件出库
						|| typeCode.equals("1112") // 1112 外发加工返修出库
						|| typeCode.equals("1114") // 1114 受托加工料件出库
						|| typeCode.equals("5101"))// 5101残 次品出库
				{ // 出库
					if (f24Map.get(key) == null) {
						BillInit temp = new BillInit();
						temp.setYear(CommonUtils.getYear());
						temp.setTypeCode("1001");// 1001料件期初单
						temp.setPtAmount(-ptAmount);
						temp.setPtNo(ptNo);
						temp.setHsNo(hsNo);
						temp.setHsName(hsName);
						temp.setHsSpec(hsSpec);
						temp.setWareSetCode(wareSetCode);
						f24Map.put(key, temp);
					} else {
						f24Map.get(key).setPtAmount(
								f24Map.get(key).getPtAmount() - ptAmount);
					}
				}

				/**
				 * 料件期初单（料件部分） 1101 车间领用 + 1002 在产品起初单 - 1006 车间返回
				 */
				if (typeCode.equals("1101") || typeCode.equals("1002")
						|| typeCode.equals("1014") // 1014委外期初单(料件)
						|| typeCode.equals("1112") // 1112外发加工返修出库(料件)
						|| typeCode.equals("1113") // 1113外发加工料件出库(料件)
				) {
					if (f25Map.get(key) == null) {
						BillInit temp = new BillInit();
						temp.setYear(CommonUtils.getYear());
						temp.setTypeCode("1002");// 1002在产品期初单
						temp.setPtAmount(ptAmount);
						temp.setPtNo(ptNo);
						temp.setHsNo(hsNo);
						temp.setHsName(hsName);
						temp.setHsSpec(hsSpec);
						temp.setWareSetCode(wareSetCode);
						f25Map.put(key, temp);
					} else {
						f25Map.get(key).setPtAmount(
								f25Map.get(key).getPtAmount() + ptAmount);
					}
				} else if (typeCode.equals("1006") || typeCode.equals("1017")// 1017外发加工料件退回(料件)
						|| typeCode.equals("1018")// 1018外发加工料件入库(料件)
				) {
					if (f25Map.get(key) == null) {
						BillInit temp = new BillInit();
						temp.setYear(CommonUtils.getYear());
						temp.setTypeCode("1002");// 1002在产品期初单
						temp.setPtAmount(-ptAmount);
						temp.setPtNo(ptNo);
						temp.setHsNo(hsNo);
						temp.setHsName(hsName);
						temp.setHsSpec(hsSpec);
						temp.setWareSetCode(wareSetCode);
						f25Map.put(key, temp);
					} else {
						f25Map.get(key).setPtAmount(
								f25Map.get(key).getPtAmount() - ptAmount);
					}
				}
			}
		}

		// 去掉期初为0的数据,料件期初
		for (Map.Entry<String, BillInit> item : f24Map.entrySet()) {
			if (!item.getValue().getPtAmount().equals(0.0)
					&& item.getValue().getPtAmount() != 0.0) {
				f244Map.put(item.getKey(), item.getValue());
			}
		}

		// 保存在产品期初
		for (Map.Entry<String, BillInit> item : f25Map.entrySet()) {
			if (!item.getValue().getPtAmount().equals(0.0)
					&& item.getValue().getPtAmount() != 0.0) {
				f255Map.put(item.getKey(), item.getValue());
			}
		}
		Map<String, Map<String, BillInit>> returnMap = new HashMap<String, Map<String, BillInit>>();
		returnMap.put("1001", f244Map);// 料件期初单
		returnMap.put("1002", f255Map);// 在产品期初单（仍旧是以 料号+仓库 作为key统计的）
		return returnMap;
	}

	/**
	 * 改变记账年度是生成料件期初单与在产品期初单
	 * 
	 * @param taskId
	 * @return
	 */
	public Map<String, Map<String, BillInit>> getMaterielF25BOMForBill(
			String taskId, Date beginDate, Date endDate) {
		ProgressInfo progressInfo = ProcExeProgress.getInstance()
				.getProgressInfo("taskId");
		if (beginDate == null) {
			beginDate = CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
					.getYear()), 0, 1);
		}
		if (endDate == null) {
			endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
					.getYear()), 11, 31);
		}
		HashMap<String, BillInit> f25BomMap = new HashMap();
		// 查找成品，计算25栏
		ParameterMaps parameterMaps = new ParameterMaps();
		/**
		 * 成品/半成品
		 */
		this.initFistBillDetailMap(beginDate, endDate, parameterMaps,
				progressInfo);
		// 因不折料的部分也要加入
		Iterator<String> halfList = parameterMaps.mapBillDetailPtAmountHalf
				.keySet().iterator();
		FactoryAndFactualCustomsRalation f = null;
		while (halfList.hasNext()) {
			String ptNo = halfList.next();
			Double ptAmount = parameterMaps.mapBillDetailPtAmountHalf.get(ptNo);
			ptAmount = (ptAmount == null ? 0.0 : ptAmount);
			BillInit temp = new BillInit();
			temp.setYear(CommonUtils.getYear());
			temp.setTypeCode("1002");
			temp.setPtAmount(ptAmount);
			temp.setPtNo(ptNo);
			List lsit = materialManageDao
					.getMaxUnitConvertFactoryAndFactualCustomsRalationByPtNo(ptNo);
			if (lsit != null && lsit.size() > 0) {
				f = (FactoryAndFactualCustomsRalation) lsit.get(0);
				temp.setHsNo(f.getStatCusNameRelationHsn().getComplex()
						.getCode());
				temp.setHsName(f.getStatCusNameRelationHsn().getCusName());
				temp.setHsSpec(f.getStatCusNameRelationHsn().getCusSpec());
				temp.setHsAmount(ptAmount * f.getUnitConvert());
			}
			temp.setNote("半成品与成品");
			f25BomMap.put(ptNo, temp);
		}

		// 保存在产品期初
		for (Map.Entry<String, BillInit> item : f25BomMap.entrySet()) {
			if (!item.getValue().getPtAmount().equals(0.0)
					&& item.getValue().getPtAmount() != 0.0) {
				f25BomMap.put(item.getKey(), item.getValue());
			}
		}
		Map<String, Map<String, BillInit>> returnMap = new HashMap<String, Map<String, BillInit>>();
		returnMap.put("1002", f25BomMap);// 在产品期半成品与成品初单
		return returnMap;
	}

	/**
	 * 初始化 半成品单据明细折BOM （用Map缓存） wss2010.11.17整理
	 */
	private void initMapBillDetailHalfProduct(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		/** 存放在F26产品折料的料件与对应的报关 */
		Map<String, FirstSumInfo> billDetailF26MaterialMap = parameterMaps.billDetailF26MaterialMap;

		String selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id "
				+ " from BillDetailHalfProduct a,MaterialBomVersion v  "
				+ " where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = 0))"
				// + "(select min(c.version) "
				// + " from MaterialBomVersion c "
				// + " where c.master=v.master))) "
				+ " and a.billMaster.isValid = ? "
				+ " and a.billMaster.validDate >= ? "
				+ " and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ? "
				+ " and a.billMaster.billType.code not in(?,?,?) "
				+ " group by a.billMaster.billType.code,v.id"
				+ " order by v.id ";// order by 确保 bom id 只查询一次

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(true);
		parameters.add(beginDate);
		parameters.add(endDate);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add("4001");
		parameters.add("4009");
		parameters.add("4101"); // 不为半成品入库与半成品出库
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());

		// 残次品（半成品部分）
		selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id "
				+ " from BillDetailRemainProduct a,MaterialBomVersion v  "
				+ " where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version =0)) "
				// + "(select min(c.version) " + " from MaterialBomVersion c "
				// + " where c.master=v.master))) "
				+ " and a.billMaster.isValid = ? "
				+ " and a.billMaster.validDate >= ? "
				+ " and a.billMaster.validDate <= ? " + " and ("
				+ " (a.billMaster.billType.code =?  and a.note = ?) "
				+ " or (a.billMaster.billType.code =? and a.note in(?,?)) "
				+ " or (a.billMaster.billType.code =?  and a.note = ?)) "
				+ " and a.billMaster.company.id = ? "
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次

		parameters = new ArrayList<Object>();
		parameters.add(true);
		parameters.add(CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
				.getYear()), 0, 1));
		parameters.add(endDate);
		parameters.add("5101");
		parameters.add("半成品");

		parameters.add("5001");
		parameters.add("成品");
		parameters.add("料件");

		parameters.add("5002");
		parameters.add("半成品");

		parameters.add(CommonUtils.getCompany().getId());
		beginTime = System.currentTimeMillis();
		List list5001 = casDao.commonSearch(selects, parameters.toArray());

		list.addAll(list5001);

		logger.info("CAS 开始查询所有半成品单据的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

		/**
		 * 半成品结余 + 4002半成品盘盈单 + 4004外发加工半成品退 + 4006外发加工半成品入库 - 4102半成品盘亏单 -
		 * 4103委外加工出库 - 4104外发加工半成品返修 - 4106委外加工出库 - 5001残次品入库（半成品部分
		 */
		Map<String, Double> mapNianShenDetailPtAmountHalf = parameterMaps.mapNianShenDetailPtAmountHalf; //

		// 存取分页查询bom时所临时存放的成品列表
		List<Object[]> lsTemp = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		
		
		int size = list.size();
		/*
		 * 获取成品版本列表
		 */
		Object[] objs = null;
		for (int i = 0; i < size; i++) {
			objs = (Object[]) list.get(i);
			String versionId = (String) objs[2]; // 报关常用工厂BOM版本id

			if (versionId == null) {
				continue;
			}

			// 为了去掉重复的 versionId
			versionIdMap.put(versionId, versionId);// 版本号id 对应 版本号id
			lsTemp.add(objs);
		}
		
		beginTime = System.currentTimeMillis();
		String clientTipMessage = "CAS 半成品总记录 " + size
				+ " 条 正在(折成料件用量)计算...";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		List<String> versionIdList = new ArrayList<String>();
		versionIdList.addAll(versionIdMap.values());
		List materialBomDetailList = this.materialManageDao
				.findMaterielBomDetail(versionIdList);
		logger.info("开始查询 " + versionIdList.size() + " 个版本时间 "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
		
		if (materialBomDetailList.size() <= 0) {
			logger.info(" 对应的BOM明细为空!! ");
			lsTemp.clear();
		}
		
		/*
		 * 获取成品版本号对应的料件明细
		 */ 
		Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
		for (int j = 0, n = materialBomDetailList.size(); j < n; j++) {
			Object[] bomObjs = (Object[]) materialBomDetailList.get(j);
			String tempVersionId = (String) bomObjs[1];// 该bom版本号
			if (bomObjectMap.get(tempVersionId) == null) {
				List<Object[]> temp = new ArrayList<Object[]>();
				temp.add(bomObjs);
				bomObjectMap.put(tempVersionId, temp);
			} else {
				List<Object[]> temp = bomObjectMap.get(tempVersionId);
				temp.add(bomObjs);// 该版本号 对应 多个bom
			}
		}
		
		objs = null;
		for (int j = 0, n = lsTemp.size(); j < n; j++) {
			objs = (Object[]) lsTemp.get(j);
			Double ptAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 工厂数量
			String typeCode = (String) objs[1]; // 类型代码
			String tempVersionId = (String) objs[2]; // version guid
			List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);
			if (tempObjes == null) {
				logger.info("Cas 计算时 Bom 明细为空  !! == " + tempVersionId);
				continue;
			}
			FactoryAndFactualCustomsRalation ffcr = null;
			String ptNoKey = null;
			StatCusNameRelationHsn sr = null;
			String hsName = null;
			String hsSpec = null;
			String unitName = null;
			for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
				Object[] bomObjs = tempObjes.get(k);

				// 料件料号为 key
				ptNoKey = (String) bomObjs[0];
				//
				// 得到成品折料中的料件
				//
				ffcr = parameterMaps.statCusNameRelationMtValueByMaterielMap
						.get(ptNoKey);
				if(ffcr == null) {
					continue;
				}
				
				sr = ffcr.getStatCusNameRelationHsn();
				if(sr == null) {
					continue;
				} else {
					hsName = sr.getCusName() == null ? ""
							: sr.getCusName();
					hsSpec = sr.getCusSpec() == null ? ""
							: sr.getCusSpec();
					unitName = sr.getCusUnit() == null ? ""
							: sr.getCusUnit()
									.getName();
					unitName = unitName == null ? "" : unitName;
				}
				// key=名称+规格+单位
				String key = hsName + " / " + hsSpec + " / " + unitName;
				Object[] obj = new Object[6];
				obj[0] = 0.0;
				obj[1] = 0.0;
				obj[2] = "折料";
				obj[3] = hsName;
				obj[4] = hsSpec;
				obj[5] = unitName;
				FirstSumInfo firstSumInfo = new FirstSumInfo(obj);
				billDetailF26MaterialMap.put(key, firstSumInfo);

				// 单耗
				Double unitWaste = bomObjs[2] == null ? 0.0
						: (Double) bomObjs[2];

				// 损耗
				Double waste = bomObjs[4] == null ? 0.0
						: (Double) bomObjs[4];

				// 料件的半成品单耗用总量=半成品数量*(单耗/(1-损耗率)
				Double totalUnitUsed = ptAmount
						* (unitWaste / (1 - waste));

				if (typeCode.equals("4002")// 4002半成品盘盈单
						|| typeCode.equals("4009")// 4009半成品期初单
						|| typeCode.equals("5002")// 5002外发加工半成品入库
				) { // 入库
					if (mapNianShenDetailPtAmountHalf.get(ptNoKey) == null) {
						mapNianShenDetailPtAmountHalf.put(ptNoKey,
								totalUnitUsed);
					} else {
						Double tempAmout = mapNianShenDetailPtAmountHalf
								.get(ptNoKey);
						tempAmout += totalUnitUsed;
						mapNianShenDetailPtAmountHalf.put(ptNoKey,
								tempAmout);
					}
				}
				if (typeCode.equals("4102") // 4102半成品盘亏单
						|| typeCode.equals("5101")// 5101残次品 出库（半成品部分
						|| typeCode.equals("5001")// 5001残次品 入库（成品、料件部分
				) { // 出库
					if (mapNianShenDetailPtAmountHalf.get(ptNoKey) == null) {
						mapNianShenDetailPtAmountHalf.put(ptNoKey,
								-totalUnitUsed);
					} else {
						Double tempAmout = mapNianShenDetailPtAmountHalf
								.get(ptNoKey);
						tempAmout -= totalUnitUsed;
						mapNianShenDetailPtAmountHalf.put(ptNoKey,
								tempAmout);
					}
				}
			}
		}
		
	}

	/**
	 * 改变记账年度时生成成品期初单
	 * 
	 * @param taskId
	 */
	public Map<String, BillInit> getProductF1ForBill(String taskId,
			Date beginDate, Date endDate) {
		if (beginDate == null) {
			beginDate = CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
					.getYear()), 0, 1);
		}
		if (endDate == null) {
			endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
					.getYear()), 11, 31);
		}
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));

		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));

		conditions.add(new Condition("and", null, "ptAmount", "!=", 0.0, null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));

		String productBillDetail = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);// 成品明细表

		// 数量、单据类型、料号、报关编码、报关名称、报关规格、仓库、版本
		String selects = "select sum(a.ptAmount),a.billMaster.billType.code,a.ptPart,a.complex.code,a.hsName,a.hsSpec,a.wareSet.code,a.version ";
		String groupBy = "group by a.billMaster.billType.code,a.ptPart,a.complex.code,a.hsName,a.hsSpec,a.wareSet.code,a.version ";
		String leftOuter = " left join a.billMaster left join a.complex left join a.billMaster.billType	left join a.wareSet 	";

		List<Object[]> listBillDetailProduct = casDao.commonSearch(selects,
				productBillDetail, conditions, groupBy, leftOuter);

		HashMap<String, BillInit> f1Map = new HashMap();
		HashMap<String, BillInit> f11Map = new HashMap();
		for (Object[] item : listBillDetailProduct) {
			Double ptAmount = (Double) item[0] == null ? 0.0 : (Double) item[0];
			String typeCode = (String) item[1];

			String ptNo = item[2] == null ? "" : item[2].toString();

			String hsNo = item[3] == null ? "" : item[3].toString();
			String hsName = item[4] == null ? "" : item[4].toString();
			String hsSpec = item[5] == null ? "" : item[5].toString();
			// System.out.println("hsSpec" + hsSpec);

			String wareSetCode = item[6] == null ? "" : item[6].toString();
			Integer version = item[7] == null ? 0 : (Integer) item[7];

			// String key = ptNo + " / " + wareSetCode+" / "+version.toString();

			// key改为：料号、报关编码、报关名称、报关规格、仓库、版本号
			String key = ptNo + " / " + hsNo + " / " + hsName + " / " + hsSpec + " / "
					+ wareSetCode + " / " + version.toString();
			// System.out.println("wss key:" + key);

			if (ptNo != null && !ptNo.equals("")) {
				//
				// 1．期末库存: 各类型成品进仓单据折算报关数量的累加－各类型成品出单据折算报关数量的累加
				//
				if (typeCode.equals("2001") // 2001 成品期初单
						|| typeCode.equals("2002")// 2002 车间入库
						|| typeCode.equals("2003")// 2003 退厂返工
						|| typeCode.equals("2004")// 2004 成品盘盈单
						|| typeCode.equals("2005")// 2005 成品转仓入库
						|| typeCode.equals("2008")// 2008 其他成品退货单
						|| typeCode.equals("2009")// 2009 结转成品退货单
						|| typeCode.equals("2014")// 2014受托加工成品入库
						|| typeCode.equals("2015")// 2015外发加工成品入库
						|| typeCode.equals("2016")// 2016受托加工成品返修
				// || typeCode.equals("2011")//2011 已交货未结转期初单
				// || typeCode.equals("2012")//2012 已交货未结转期初单
				) { // 入库

					if (f1Map.get(key) == null) {
						BillInit temp = new BillInit();
						temp.setYear(CommonUtils.getYear());
						temp.setTypeCode("2001");
						temp.setPtAmount(ptAmount);
						temp.setPtNo(ptNo);

						temp.setHsNo(hsNo);
						temp.setHsName(hsName);
						temp.setHsSpec(hsSpec);

						temp.setWareSetCode(wareSetCode);
						temp.setVersion(version);
						f1Map.put(key, temp);
					} else {
						f1Map.get(key).setPtAmount(
								f1Map.get(key).getPtAmount() + ptAmount);

					}
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
						|| typeCode.equals("2111")// 2111受托加工成品退回
						|| typeCode.equals("2112")// 2112受托加工成品出库
						|| typeCode.equals("2113")// 2113外发加工成品返修
				) { // 出库
					if (f1Map.get(key) == null) {
						BillInit temp = new BillInit();
						temp.setYear(CommonUtils.getYear());
						temp.setTypeCode("2001");
						temp.setPtAmount(-ptAmount);
						temp.setPtNo(ptNo);
						temp.setHsNo(hsNo);
						temp.setHsName(hsName);
						temp.setHsSpec(hsSpec);
						temp.setWareSetCode(wareSetCode);
						temp.setVersion(version);
						f1Map.put(key, temp);

					} else {
						f1Map.get(key).setPtAmount(
								f1Map.get(key).getPtAmount() - ptAmount);
					}
				}
			}
		}

		// 去掉期初为0的数据
		for (Map.Entry<String, BillInit> item : f1Map.entrySet()) {
			if (!item.getValue().getPtAmount().equals(0.0)
					&& item.getValue().getPtAmount() != 0.0) {
				f11Map.put(item.getKey(), item.getValue());
			}
		}
		return f11Map;
	}

	/**
	 * 改变记账年度时生成半成品期初单
	 * 
	 * @param taskId
	 * @author wss2010.10.07
	 */
	public Map<String, BillInit> getSemiProductF1ForBill(String taskId,
			Date beginDate, Date endDate) {
		if (beginDate == null) {
			beginDate = CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
					.getYear()), 0, 1);
		}
		if (endDate == null) {
			endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
					.getYear()), 11, 31);
		}
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));

		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));

		conditions.add(new Condition("and", null, "ptAmount", "!=", 0.0, null));

		String productBillDetail = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.SEMI_FINISHED_PRODUCT);// 半成品明细表

		// 数量、单据类型、料号、仓库、版本
		String selects = "select sum(a.ptAmount),a.billMaster.billType.code,a.ptPart,a.wareSet.code,a.version ";
		String groupBy = "group by a.billMaster.billType.code,a.ptPart,a.wareSet.code,a.version ";
		String leftOuter = " left join a.billMaster left join a.complex left join a.billMaster.billType	left join a.wareSet ";

		List<Object[]> listBillDetailProduct = casDao.commonSearch(selects,
				productBillDetail, conditions, groupBy, leftOuter);

		HashMap<String, BillInit> f1Map = new HashMap();
		HashMap<String, BillInit> f11Map = new HashMap();
		for (Object[] item : listBillDetailProduct) {
			Double ptAmount = (Double) item[0] == null ? 0.0 : (Double) item[0];
			String typeCode = (String) item[1];

			String ptNo = item[2] == null ? "" : item[2].toString();

			String wareSetCode = item[3] == null ? "" : item[3].toString();
			Integer version = item[4] == null ? 0 : (Integer) item[4];

			// key改为：料号、仓库、版本号
			String key = ptNo + " / " + wareSetCode + " / " + version.toString();

			if (ptNo != null && !ptNo.equals("")) {

				// 1．期末库存: 各类型成品进仓单据折算报关数量的累加－各类型成品出单据折算报关数量的累加
				if (typeCode.equals("4009")// 4009半成品期初单
						|| typeCode.equals("4001") // 4001 半成品入库
						|| typeCode.equals("4002")// 4002 半成品盘盈单
						|| typeCode.equals("4004")// 4004 外发加工半成品退回
						|| typeCode.equals("4006")// 4006 外发加工半成品入库

				) { // 入库

					if (f1Map.get(key) == null) {
						BillInit temp = new BillInit();
						temp.setYear(CommonUtils.getYear());
						temp.setTypeCode("4009");
						temp.setPtAmount(ptAmount);
						temp.setPtNo(ptNo);

						temp.setWareSetCode(wareSetCode);
						temp.setVersion(version);
						f1Map.put(key, temp);
					} else {
						f1Map.get(key).setPtAmount(
								f1Map.get(key).getPtAmount() + ptAmount);
					}
				}
				if (typeCode.equals("4101") // 4101半成品出库
						|| typeCode.equals("4102")// 4102半成品盘亏单
						|| typeCode.equals("4104")// 4104外发加工半成品返修
						|| typeCode.equals("4106")// 4106外发加工半成品出库
				) { // 出库
					if (f1Map.get(key) == null) {
						BillInit temp = new BillInit();
						temp.setYear(CommonUtils.getYear());
						temp.setTypeCode("4009");
						temp.setPtAmount(-ptAmount);
						temp.setPtNo(ptNo);

						temp.setWareSetCode(wareSetCode);
						temp.setVersion(version);
						f1Map.put(key, temp);

					} else {
						f1Map.get(key).setPtAmount(
								f1Map.get(key).getPtAmount() - ptAmount);
					}
				}
			}
		}

		// 去掉期初为0的数据
		for (Map.Entry<String, BillInit> item : f1Map.entrySet()) {
			if (!item.getValue().getPtAmount().equals(0.0)
					&& item.getValue().getPtAmount() != 0.0) {
				f11Map.put(item.getKey(), item.getValue());
			}
		}
		return f11Map;
	}

	/**
	 * 生成料件期初,在产品期初,成品期初
	 */
	public void saveBillInit(String taskId) {
		// 1.delete all year in init
		this.casDao.deleteBillInit(CommonUtils.getYear());

		// 2.create bill init
		Map<String, Map<String, BillInit>> mapMaterielBillInit = this
				.getMaterielF24F25ForBill(taskId, null, null);
		//为什么要把计算在产品中的半成品与成品单独计算并且不与在产品料件部份相加减，是因为考虑到料号有一对多的情况。
		Map<String, Map<String, BillInit>> mapF25BOMBillInit = this
				.getMaterielF25BOMForBill(taskId, null, null);//计算在产品半成品与成品部份
		Map<String, BillInit> mapBillInit1001 = mapMaterielBillInit.get("1001");// 料件期初
		Map<String, BillInit> mapBillInit1002 = mapMaterielBillInit.get("1002");// 在产品料件部份期初
		Map<String, BillInit> mapBillInit1002F25BOM = mapF25BOMBillInit.get("1002");// 在产品半成品与成品部份期初
		
		Map<String, BillInit> mapBillInit2001 = this.getProductF1ForBill("",
				null, null);// 成品期初
		Map<String, BillInit> mapBillInit4009 = this.getSemiProductF1ForBill(
				"", null, null);// 半成品期初
		Map<String, BillInit> mapBillInit5002 = this.getMapForBillInit(
				MaterielType.BAD_PRODUCT, "", null, null);// 残次品期初单
		Map<String, BillInit> mapBillInit6003 = this.getMapForBillInit(
				MaterielType.REMAIN_MATERIEL, "", null, null);// 边角料期初单6003

		// wss2010.10.26
		Map<String, BillInit> billInit1020 = this.get1020Bill(null, null);// 国内购买期初单（只需要报关资料的统计）

		// 3.save bill init
		this.saveBillInit(mapBillInit1001);
		this.saveBillInit(mapBillInit1002);
		this.saveBillInit(mapBillInit1002F25BOM);
		this.saveBillInit(mapBillInit2001);
		this.saveBillInit(mapBillInit4009);
		this.saveBillInit(mapBillInit5002);
		this.saveBillInit(mapBillInit6003);
		this.saveBillInit(billInit1020);
	}

	/**
	 * 切换记帐年度 获取1020国内购买期初
	 * 
	 * @return
	 * @author wss2010.10.26
	 */
	public Map<String, BillInit> get1020Bill(Date beginDate, Date endDate) {

		HashMap<String, BillInit> map1020 = new HashMap();

		if (beginDate == null) {
			beginDate = CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
					.getYear()), 0, 1);
		}
		if (endDate == null) {
			endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
					.getYear()), 11, 31);
		}
		List<Condition> conditions = new ArrayList<Condition>();
		// 1.时间范围
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));
		// 2.有效
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));

		// 3.1005同内购买 、1020国内购买期初
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "1005", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1020", ")"));

		// 4.数量不为0
		conditions.add(new Condition("and", null, "hsAmount", "!=", 0.0, null));
		// 5.海关商品名称不为空
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));

		String billDetail = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);
		String selects = " select a.hsName,a.hsSpec,a.hsUnit.code,sum(a.hsAmount)";
		String groupBy = " group by a.billMaster.billType.code,a.hsName,a.hsSpec,a.hsUnit.code ";
		String leftOuter = " left outer join a.billMaster ";

		List<Object[]> listBillDetailMateriel = casDao.commonSearch(selects,
				billDetail, conditions, groupBy, leftOuter);

		// 核销发票
//		Map<String, Double> invoiceVerificationAmountMap = new HashMap<String, Double>();
		String hsql = " select a.cuName,a.cuSpec,a.unit.code,sum(a.canceledNum)"
				+ " from CasInvoiceInfo a where a.casInvoice.validDate>= ? and a.casInvoice.validDate<= ? "
				+ " and a.canceled=? "
				+ " and a.company.id=? group by a.cuName,a.cuSpec,a.unit.code ";
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getBeginDate(beginDate));
		params.add(CommonUtils.getEndDate(endDate));
		params.add(true);
		params.add(CommonUtils.getCompany().getId());

		List<Object[]> list = this.casDao.commonSearch(hsql, params.toArray());

		System.out.println("wss 核销数list.size " + list.size());
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String hsName = (objs[0] == null ? "" : objs[0].toString().trim());
			String hsSpec = (objs[1] == null ? "" : objs[1].toString().trim());
			String hsUnitCode = (objs[2] == null ? "" : objs[2].toString()
					.trim());
			Unit hsUnit = casDao.findUnitByCode(hsUnitCode);
			Double hsAmount = (objs[3] == null ? 0.0 : Double.valueOf(objs[3]
					.toString().trim()));

			// key=名称+" / "+规格+" / "+单位
			String key = hsName + " / " + hsSpec + " / " + hsUnit == null ? ""
					: hsUnit.getName();

			if (map1020.get(key) == null) {
				BillInit temp = new BillInit();
				temp.setYear(CommonUtils.getYear());
				temp.setTypeCode("1020");
				temp.setHsName(hsName);
				temp.setHsSpec(hsSpec);
				temp.setHsUnit(hsUnit);
				temp.setHsAmount(hsAmount);
				map1020.put(key, temp);
			} else {
				// 核销数量应该为减
				map1020.get(key).setPtAmount(
						CommonUtils.getDoubleExceptNull(map1020.get(key)
								.getHsAmount())
								- hsAmount);
			}

			System.out.println("wss 核销数key:" + key);
			System.out.println("wss hsAmount:" + hsAmount);
		}

		for (int j = 0; j < listBillDetailMateriel.size(); j++) {
			Object[] objs = (Object[]) listBillDetailMateriel.get(j);
			String hsName = (objs[0] == null ? "" : objs[0].toString().trim());
			String hsSpec = (objs[1] == null ? "" : objs[1].toString().trim());
			String hsUnitCode = (objs[2] == null ? "" : objs[2].toString()
					.trim());
			Unit hsUnit = casDao.findUnitByCode(hsUnitCode);
			Double hsAmount = (objs[3] == null ? 0.0 : Double.valueOf(objs[3]
					.toString().trim()));

			// key=名称+" / "+规格+" / "+单位
			String key = hsName + " / " + hsSpec + " / " + hsUnit == null ? ""
					: hsUnit.getName();

			if (map1020.get(key) == null) {
				BillInit temp = new BillInit();
				temp.setYear(CommonUtils.getYear());
				temp.setTypeCode("1020");
				temp.setHsName(hsName);
				temp.setHsSpec(hsSpec);
				temp.setHsUnit(hsUnit);
				temp.setHsAmount(hsAmount);
				map1020.put(key, temp);
			} else {
				// 国内购买期初单 与 国内购买 应该为 加
				map1020.get(key).setPtAmount(
						CommonUtils.getDoubleExceptNull(map1020.get(key)
								.getHsAmount())
								+ hsAmount);
			}
		}

		// 去掉期初为0的数据
		for (Map.Entry<String, BillInit> item : map1020.entrySet()) {
			if (0.0 == item.getValue().getHsAmount()) {
				map1020.remove(item);
			}
		}
		return map1020;
	}

	/**
	 * 改变记账年度时生残次品,边角料期初
	 * 
	 * @param taskId
	 * @author wss
	 */
	public Map<String, BillInit> getMapForBillInit(String type, String taskId,
			Date beginDate, Date endDate) {
		HashMap<String, BillInit> f1Map = new HashMap();
		HashMap<String, BillInit> f11Map = new HashMap();
		if (!(type.equals(MaterielType.BAD_PRODUCT) || type
				.equals(MaterielType.REMAIN_MATERIEL))) {
			return f1Map;
		}
		if (beginDate == null) {
			beginDate = CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
					.getYear()), 0, 1);
		}
		if (endDate == null) {
			endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
					.getYear()), 11, 31);
		}
		List<Condition> conditions = new ArrayList<Condition>();
		// 1.时间范围
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));
		// 2.有效
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		// 3.数量不为0
		conditions.add(new Condition("and", null, "ptAmount", "!=", 0.0, null));
		// 4.海关商品名称不为空
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));

		String productBillDetail = "";
		String selects = "";
		String groupBy = "";
		if (MaterielType.BAD_PRODUCT.equals(type)) {
			productBillDetail = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);// 残次品明细表

			// 残次品：数量、单据类型、料号、报关编码、报关名称、报关规格、仓库、版本号、注释
			selects = "select sum(a.ptAmount),a.billMaster.billType.code,a.ptPart,a.complex.code,a.hsName,a.hsSpec,a.wareSet.code,a.version,a.note ";
			groupBy = "group by a.billMaster.billType.code,a.ptPart,a.complex.code,a.hsName,a.hsSpec,a.wareSet.code,a.version ,a.note ";
		} else if (MaterielType.REMAIN_MATERIEL.equals(type)) {
			productBillDetail = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.REMAIN_MATERIEL);// 边角料明细表

			// 边角料：数量、单据类型、料号、报关编码、报关名称、报关规格、仓库
			selects = "select sum(a.ptAmount),a.billMaster.billType.code,a.ptPart,a.complex.code,a.hsName,a.hsSpec,a.wareSet.code ";
			groupBy = "group by a.billMaster.billType.code,a.ptPart,a.complex.code,a.hsName,a.hsSpec,a.wareSet.code ";
		}

		String leftOuter = " left join a.billMaster left join a.complex left join a.billMaster.billType	left join a.wareSet ";

		List<Object[]> listBillDetailProduct = casDao.commonSearch(selects,
				productBillDetail, conditions, groupBy, leftOuter);

		for (Object[] item : listBillDetailProduct) {
			Double ptAmount = (Double) item[0] == null ? 0.0 : (Double) item[0];
			String typeCode = (String) item[1];
			String ptNo = (String) item[2];

			String hsNo = item[3] == null ? "" : item[3].toString();
			String hsName = item[4] == null ? "" : item[4].toString();
			String hsSpec = item[5] == null ? "" : item[5].toString();

			String wareSetCode = item[6] == null ? "" : item[6].toString();

			Integer version = 0;
			String note = "";

			// //key 为 料号 + 仓库 + 版本号
			// String key = ptNo + " / " + wareSetCode + " / " + version.toString();

			// key 为 料号+报关编码+报关名称+报关规格+仓库
			String key = ptNo + " / " + hsNo + " / " + hsName + " / " + hsSpec + " / "
					+ wareSetCode;

			if (MaterielType.BAD_PRODUCT.equals(type)) {
				version = item[7] == null ? 0 : (Integer) item[7];
				note = item[8] == null ? "" : item[8].toString();

				// 如果是残次品，key 还要加上版本号
				key += " / " + version.toString();
			}

			if (ptNo != null && !ptNo.equals("")) {
				if (typeCode.equals("5001") // 残次品入库
						|| typeCode.equals("5002")// 残次品期初单
						|| typeCode.equals("5003")// 外发加工残次品入仓
						|| typeCode.equals("5004")// 受拖加工残次品入库
						|| typeCode.equals("5005")// 残次品盘盈单

						|| typeCode.equals("6001")// 边角料入库
						|| typeCode.equals("6002")// 边角料盘盈单
						|| typeCode.equals("6003")// 边角料期初单
						|| typeCode.equals("6004")// 外发加工退回边角料
						|| typeCode.equals("6005")// 外发加工边角料入库
						|| typeCode.equals("6006")// 受托加工边角料返修
				) { // 入库

					if (f1Map.get(key) == null) {
						BillInit temp = new BillInit();
						temp.setYear(CommonUtils.getYear());

						if (type.equals(MaterielType.BAD_PRODUCT)) {
							temp.setTypeCode("5002");
							temp.setVersion(version);
							temp.setNote(note);
						} else {
							temp.setTypeCode("6003");
						}

						temp.setPtAmount(ptAmount);
						temp.setPtNo(ptNo);

						temp.setHsNo(hsNo);
						temp.setHsName(hsName);
						temp.setHsSpec(hsSpec);

						temp.setWareSetCode(wareSetCode);

						f1Map.put(key, temp);
					} else {
						f1Map.get(key).setPtAmount(
								f1Map.get(key).getPtAmount() + ptAmount);
					}
				}
				if (typeCode.equals("5101") // 残次品出库
						|| typeCode.equals("5102")// 残次品盘亏单
						|| typeCode.equals("5103")// 外发加工残次品出仓
						|| typeCode.equals("5104")// 受托加工残次品出仓

						|| typeCode.equals("6101")// 边角料出库
						|| typeCode.equals("6102")// 边角料退运出口
						|| typeCode.equals("6103")// 边角料盘亏单
						|| typeCode.equals("6104")// 边角料征税内销
						|| typeCode.equals("6105")// 外发加工边角料返修
						|| typeCode.equals("6106")// 受托加工边角料出库
				) { // 出库
					if (f1Map.get(key) == null) {
						BillInit temp = new BillInit();
						temp.setYear(CommonUtils.getYear());

						if (type.equals(MaterielType.BAD_PRODUCT)) {
							temp.setTypeCode("5002");
							temp.setVersion(version);
							temp.setNote(note);
						} else {
							temp.setTypeCode("6003");
						}

						temp.setPtAmount(-ptAmount);
						temp.setPtNo(ptNo);

						temp.setHsNo(hsNo);
						temp.setHsName(hsName);
						temp.setHsSpec(hsSpec);

						temp.setWareSetCode(wareSetCode);

						f1Map.put(key, temp);
					} else {
						f1Map.get(key).setPtAmount(
								f1Map.get(key).getPtAmount() - ptAmount);
					}
				}
			}
		}

		// 去掉期初为0的数据
		for (Map.Entry<String, BillInit> item : f1Map.entrySet()) {
			if (!item.getValue().getPtAmount().equals(0.0)
					&& item.getValue().getPtAmount() != 0.0) {
				f11Map.put(item.getKey(), item.getValue());
			}
		}
		return f11Map;
	}

	/**
	 * save map to bill init
	 * 
	 * @param map
	 */
	private void saveBillInit(Map<String, BillInit> map) {
		for (Map.Entry<String, BillInit> e : map.entrySet()) {
			BillInit billInit = e.getValue();
			billInit.setCompany(CommonUtils.getCompany());
			this.casDao.saveOrUpdate(billInit);
		}
	}

	/**
	 * 生成单据头与体
	 * 
	 * @param map
	 * @param mMap
	 * @param fMap
	 * @param typeCode
	 */
	public void makeBillInit() {
		String frontYear = String.valueOf(Integer
				.valueOf(CommonUtils.getYear()).intValue() - 1);// 前 一年
		/**
		 * 1.获得前一年已生成的现年期初
		 */
		List<BillInit> list1001 = this.casDao.findBillInit(frontYear, "1001");// 料件期初
		// System.out.println("wss list1001.size = " + list1001.size());

		List<BillInit> list1002 = this.casDao.findBillInit(frontYear, "1002");// 在产品期初
		// System.out.println("wss list1002.size = " + list1002.size());

		List<BillInit> list2001 = this.casDao.findBillInit(frontYear, "2001");// 成品期初
		// System.out.println("wss list2001.size = " + list2001.size());

		List<BillInit> list4009 = this.casDao.findBillInit(frontYear, "4009");// 半成品期初
		// System.out.println("wss list4009.size = " + list4009.size());

		List<BillInit> list5002 = this.casDao.findBillInit(frontYear, "5002");// 残次品期初
		// System.out.println("wss list5002.size = " + list5002.size());

		List<BillInit> list6003 = this.casDao.findBillInit(frontYear, "6003");// 边角料期初
		// System.out.println("wss list6003.size = " + list6003.size());

		List<BillInit> list1020 = this.casDao.findBillInit(frontYear, "1020");// 国内购买期初单
		// System.out.println("wss list1020.size = " + list1020.size());

		String year = CommonUtils.getYear();// 今年
		String billNo = year + "01010001";// 目标单据号码

		/**
		 * 2.删除原有的期初单据
		 */
		System.out.println("wss 删除原有的期初单据。。。");
		this.casDao.deleteBillByInit("1001", billNo);
		this.casDao.deleteBillByInit("1002", billNo);
		this.casDao.deleteBillByInit("2001", billNo);
		this.casDao.deleteBillByInit("4009", billNo);
		this.casDao.deleteBillByInit("5002", billNo);
		this.casDao.deleteBillByInit("6003", billNo);
		this.casDao.deleteBillByInit("1020", billNo);

		System.out.println("wss 删除原有的期初单据完毕！");

		/**
		 * 3.初始化一些基本的东西
		 */
		// 对应关系
		List<FactoryAndFactualCustomsRalation> listFFC = new ArrayList<FactoryAndFactualCustomsRalation>();

		// 料号 与 对应关系 关联
		HashMap<String, FactoryAndFactualCustomsRalation> mapPtNoFFC = new HashMap();

		// 料号+报关名称+报关规格+报关单位 与 对应关系 关联
		HashMap<String, FactoryAndFactualCustomsRalation> mapHsInfoFFC = new HashMap();

		listFFC = this.casDao
				.findFactoryAndFactualCustomsRalationByMaterielType(null);

		for (FactoryAndFactualCustomsRalation f : listFFC) {
			String ptNo = f.getMateriel().getPtNo();
			String hsCode = f.getStatCusNameRelationHsn().getComplex() == null ? "" :f.getStatCusNameRelationHsn().getComplex()
					.getCode();
			String hsName = f.getStatCusNameRelationHsn().getCusName();
			String hsSpec = f.getStatCusNameRelationHsn().getCusSpec();

			// wss只要第一个？？Of course!
			if (mapPtNoFFC.get(ptNo) == null) {
				mapPtNoFFC.put(ptNo, f);
			}

			String key = ptNo + " / " + hsCode + " / " + hsName + " / " + hsSpec;
			// System.out.println("wss key = " + key);

			if (mapHsInfoFFC.get(key) == null) {
				mapHsInfoFFC.put(key, f);
			}
		}

		// 报关常用工厂物料
		List<Materiel> listMateriel = new ArrayList<Materiel>();
		Map<String, Materiel> mapPtNoM = new HashMap<String, Materiel>();
		listMateriel = this.casDao
				.findMaterialForRelation(MaterielType.SEMI_FINISHED_PRODUCT);
		listMateriel.addAll(this.casDao
				.findMaterialForRelation(MaterielType.BAD_PRODUCT));

		for (Materiel m : listMateriel) {
			// 为什么半成品会用mapPtNoM来找报关名称，是因为对应关系中无半成品的对应关系
			if (mapPtNoM.get(m.getPtNo()) == null) {
				mapPtNoM.put(m.getPtNo(), m);
			}
		}

		// 仓库
		Map<String, WareSet> wareSetMap = new HashMap<String, WareSet>();
		List<WareSet> wareSet = this.casDao.findWareSet();
		for (WareSet w : wareSet) {
			wareSetMap.put(w.getCode(), w);
		}

		// 日期
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.valueOf(CommonUtils.getYear()));
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date beginDate = calendar.getTime();

		/**
		 * 1001料件期初
		 */
		if (list1001 != null && list1001.size() > 0) {

			System.out.println("料件期初1001共  " + list1001.size());
			// a.生成单据头
			BillMaster billMaster = new BillMasterMateriel();
			// 填充单据头并保存
			billMaster = this.fillInitBillMaster(billMaster, billNo, "1001",
					beginDate);

			// b.生成单据明细
			for (BillInit item : list1001) {
				BillDetail b = new BillDetailMateriel();// 料件
				BillInit temp = item;
				// 填充单据体并保存
				fillInitBillDetail(b, billMaster, temp, wareSetMap, mapPtNoFFC,
						mapHsInfoFFC);
			}
		}

		/**
		 * 1002在产品期初
		 */
		if (list1002 != null && list1002.size() > 0) {
			System.out.println("在产品期初1002共 " + list1002.size());
			// a.生成单据头
			BillMaster billMaster = new BillMasterMateriel();
			// 填充单据头并保存
			billMaster = this.fillInitBillMaster(billMaster, billNo, "1002",
					beginDate);

			// b.生成单据明细
			for (BillInit item : list1002) {
				BillDetail b = new BillDetailMateriel();
				BillInit temp = item;
				// 填充单据体并保存
				fillInit1002BillDetail(b, billMaster, temp, wareSetMap,
						mapPtNoFFC, mapHsInfoFFC);
			}
		}

		/**
		 * 2001成品期初
		 */
		if (list2001 != null && list2001.size() > 0) {

			System.out.println("wss list2001.size = " + list2001.size());
			// a.生成单据头
			BillMaster billMaster = new BillMasterProduct();
			// 填充单据头并保存
			billMaster = this.fillInitBillMaster(billMaster, billNo, "2001",
					beginDate);

			// b.生成单据明细
			for (BillInit item : list2001) {
				BillDetail b = new BillDetailProduct();// 料件
				BillInit temp = item;
				// 填充单据体并保存
				fillInitBillDetail(b, billMaster, temp, wareSetMap, mapPtNoFFC,
						mapHsInfoFFC);
			}
		}

		/**
		 * 4009半成品期初
		 */
		if (list4009 != null && list4009.size() > 0) {

			System.out.println("wss list4009.size = " + list4009.size());
			// a.生成单据头
			BillMaster billMaster = new BillMasterHalfProduct();
			// 填充单据头并保存
			billMaster = this.fillInitBillMaster(billMaster, billNo, "4009",
					beginDate);

			// b.生成单据明细
			for (BillInit item : list4009) {
				BillDetail b = new BillDetailHalfProduct();
				BillInit temp = item;
				// 填充单据体并保存
				fillHalfProductInitBillDetail(b, billMaster, temp, wareSetMap,
						mapPtNoM);
			}
		}

		/**
		 * 5002残次品期初
		 */
		if (list5002 != null && list5002.size() > 0) {

			System.out.println("wss list5005.size = " + list5002.size());
			// a.生成单据头
			BillMaster billMaster = new BillMasterRemainProduct();
			// 填充单据头并保存
			billMaster = this.fillInitBillMaster(billMaster, billNo, "5002",
					beginDate);

			// b.生成单据明细
			for (BillInit item : list5002) {
				BillDetail b = new BillDetailRemainProduct();
				BillInit temp = item;

				// 填充单据体并保存
				if ("半成品".equals(temp.getNote())) {
					b.setNote("半成品");
					fillHalfProductInitBillDetail(b, billMaster, temp,
							wareSetMap, mapPtNoM);
				} else {
					fillInitBillDetail(b, billMaster, temp, wareSetMap,
							mapPtNoFFC, mapHsInfoFFC);
				}
			}
		}

		/**
		 * 6003边角料期初
		 */
		if (list6003 != null && list6003.size() > 0) {

			System.out.println("wss list6003.size = " + list6003.size());
			// a.生成单据头
			BillMaster billMaster = new BillMasterLeftoverMateriel();
			// 填充单据头并保存
			billMaster = this.fillInitBillMaster(billMaster, billNo, "6003",
					beginDate);

			// b.生成单据明细
			for (BillInit item : list6003) {
				BillDetail b = new BillDetailLeftoverMateriel();
				BillInit temp = item;
				// 填充单据体并保存
				fillInitBillDetail(b, billMaster, temp, wareSetMap, mapPtNoFFC,
						mapHsInfoFFC);
			}
		}

		/**
		 * 1020国内购买期初单（特殊，只需要报关资料，在发票管理中显示） wss2010.10.26
		 */
		if (list1020 != null && list1020.size() > 0) {

			System.out.println("wss list1020.size = " + list1020.size());

			// a.生成单据头
			BillMaster billMaster = new BillMasterMateriel();
			// 填充单据头并保存
			billMaster = this.fillInitBillMaster(billMaster, billNo, "1020",
					beginDate);
			System.out.println("wss 生成billMaster完毕！");

			// b.生成单据明细
			for (BillInit item : list1020) {
				BillDetail b = new BillDetailMateriel();// 料件
				BillInit temp = item;

				b.setBillMaster(billMaster);
				b.setBillNo(billMaster.getBillNo());
				b.setHsName(temp.getHsName());
				b.setHsSpec(temp.getHsSpec());
				b.setHsUnit(temp.getHsUnit());
				b.setHsAmount(temp.getHsAmount());

				b.setCompany(CommonUtils.getCompany());
				this.casDao.saveOrUpdate(b);
			}

			System.out.println("wss 生成billDetail完毕！");

		}

	}

	/**
	 * 填充期初单的 单据头
	 * 
	 * @param billMaster
	 *            要填充的单据头
	 * @param billNo
	 *            单据号
	 * @param typeCode
	 *            单据类型代码
	 * @param validDate
	 *            生效日期
	 * @author wss2010.10.07
	 */
	private BillMaster fillInitBillMaster(BillMaster billMaster, String billNo,
			String typeCode, Date validDate) {
		billMaster.setBillNo(billNo);
		billMaster.setBillType(this.casDao.findBillTypeByCode(typeCode));
		billMaster.setCompany(CommonUtils.getCompany());
		// 1002在产品期初单没有仓库不能生效
		if (!"1002".equals(typeCode)) {
			billMaster.setIsValid(true);
			billMaster.setValidDate(validDate);
		}
		billMaster.setIsValid(true);
		billMaster.setValidDate(validDate);
		billMaster.setCreateDate(new Date());
		this.casDao.saveOrUpdate(billMaster);
		return billMaster;
	}

	/**
	 * 填充期初单 单据体
	 * 
	 * @param billDetail
	 * @param billMaster
	 * @param temp
	 * @param wareSetMap
	 * @param mapPtNoFFC
	 * @param mapHsInfoFFC
	 * @return
	 * @author wss2010.10.07
	 */
	private void fillInit1002BillDetail(BillDetail billDetail,
			BillMaster billMaster, BillInit temp,
			Map<String, WareSet> wareSetMap,
			HashMap<String, FactoryAndFactualCustomsRalation> mapPtNoFFC,
			HashMap<String, FactoryAndFactualCustomsRalation> mapHsInfoFFC) {
		if (temp == null) {
			return;
		}
		billDetail.setBillMaster(billMaster);
		billDetail.setBillNo(billMaster.getBillNo());
		billDetail.setPtPart(temp.getPtNo());
		billDetail.setPtAmount((temp.getPtAmount() == null) ? 0.0 : temp
				.getPtAmount());
		billDetail.setWareSet(wareSetMap.get(temp.getWareSetCode() == null ? ""
				: temp.getWareSetCode()));
		billDetail.setVersion(temp.getVersion());
		billDetail.setNote(temp.getNote());

		String key = temp.getPtNo() + " / " + temp.getHsNo() + " / "
				+ temp.getHsName() + " / " + temp.getHsSpec();

		// 通过 料件+报关编码+报关名称+报关规格，找相应的对应关系
		FactoryAndFactualCustomsRalation f = mapHsInfoFFC.get(key);

		// 如果没有，则通过料号找对应关系(主要用于生成 在产品期初单)

		if (f == null) {
			f = mapPtNoFFC.get(billDetail.getPtPart());
			// System.out.println("====ptartf=" + billDetail.getPtPart() + " f="
			// + f);
			// 当有一个料号对应多个报关名称时，取ID号最大的一个
			List lsit = materialManageDao
					.getMaxUnitConvertFactoryAndFactualCustomsRalationByPtNo(billDetail
							.getPtPart());
			if (lsit != null && lsit.size() > 0) {
				f = (FactoryAndFactualCustomsRalation) lsit.get(0);
			}
		}

		// 如果有对应关系
		if (f != null) {

			billDetail.setPtName(f.getMateriel().getFactoryName());
			billDetail.setPtSpec(f.getMateriel().getFactorySpec());
			billDetail.setPtUnit(f.getMateriel().getCalUnit());

			billDetail
					.setNetWeight(f.getMateriel().getPtNetWeight() != null ? f
							.getMateriel().getPtNetWeight()
							* billDetail.getPtAmount() : 0.0);

			billDetail.setPtPrice(f.getMateriel().getPtPrice());
			billDetail.setMoney(billDetail.getPtPrice() == null ? 0.0
					: billDetail.getPtAmount() * billDetail.getPtPrice());

			Double ptPrice = f.getMateriel().getPtPrice() == null ? 0.0 : f
					.getMateriel().getPtPrice();

			// if (f.getUnitConvert() == null) {
			// throw new RuntimeException("单据中心--对应关系料号为：【"
			// + f.getMateriel().getPtNo() + "】的单位折算为空。");
			// } else if (f.getUnitConvert().doubleValue() == Double.valueOf(0))
			// {
			// throw new RuntimeException("单据中心--对应关系料号为：【"
			// + f.getMateriel().getPtNo() + "】的单位折算为零。");
			// }
			Double hsPrice = 0.0;
			if (f.getUnitConvert() != null
					&& f.getUnitConvert().doubleValue() != Double.valueOf(0)) {
				hsPrice = ptPrice
						/ Double.parseDouble(f.getUnitConvert().toString());
			}
			billDetail.setHsPrice(hsPrice);// 海关单价
			billDetail.setComplex(f.getStatCusNameRelationHsn().getComplex());
			billDetail.setHsName(f.getStatCusNameRelationHsn().getCusName());
			billDetail.setHsSpec(f.getStatCusNameRelationHsn().getCusSpec());
			billDetail.setHsUnit(f.getStatCusNameRelationHsn().getCusUnit());
			billDetail.setHsAmount((billDetail.getPtAmount() == null ? 0.0
					: billDetail.getPtAmount())
					* (f.getUnitConvert() != null ? f.getUnitConvert() : 0.0));
			// billDetail.setEmsNo(f.getStatCusNameRelationHsn().getEmsNo());//手册号有争议，故先屏蔽

		} else {
			billDetail.setNote("没有做对应关系");
		}
		billDetail.setCompany(CommonUtils.getCompany());
		this.casDao.saveOrUpdate(billDetail);
	}

	/**
	 * 填充期初单 单据体
	 * 
	 * @param billDetail
	 * @param billMaster
	 * @param temp
	 * @param wareSetMap
	 * @param mapPtNoFFC
	 * @param mapHsInfoFFC
	 * @return
	 * @author wss2010.10.07
	 */
	private void fillInitBillDetail(BillDetail billDetail,
			BillMaster billMaster, BillInit temp,
			Map<String, WareSet> wareSetMap,
			HashMap<String, FactoryAndFactualCustomsRalation> mapPtNoFFC,
			HashMap<String, FactoryAndFactualCustomsRalation> mapHsInfoFFC) {
		if (temp == null) {
			return;
		}
		billDetail.setBillMaster(billMaster);
		billDetail.setBillNo(billMaster.getBillNo());
		billDetail.setPtPart(temp.getPtNo());
		billDetail.setPtAmount((temp.getPtAmount() == null) ? 0.0 : temp
				.getPtAmount());
		billDetail.setWareSet(wareSetMap.get(temp.getWareSetCode() == null ? ""
				: temp.getWareSetCode()));
		billDetail.setVersion(temp.getVersion());
		billDetail.setNote(temp.getNote());

		String key = temp.getPtNo() + " / " + temp.getHsNo() + " / "
				+ temp.getHsName() + " / " + temp.getHsSpec();

		// 通过 料件+报关编码+报关名称+报关规格，找相应的对应关系
		FactoryAndFactualCustomsRalation f = mapHsInfoFFC.get(key);

		// 如果没有，则通过料号找对应关系(主要用于生成 在产品期初单)

		if (f == null) {
			f = mapPtNoFFC.get(billDetail.getPtPart());
		}

		// 如果有对应关系
		if (f != null) {

			billDetail.setPtName(f.getMateriel().getFactoryName());
			billDetail.setPtSpec(f.getMateriel().getFactorySpec());
			billDetail.setPtUnit(f.getMateriel().getCalUnit());

			billDetail
					.setNetWeight(f.getMateriel().getPtNetWeight() != null ? f
							.getMateriel().getPtNetWeight()
							* billDetail.getPtAmount() : 0.0);

			billDetail.setPtPrice(f.getMateriel().getPtPrice());
			billDetail.setMoney(billDetail.getPtPrice() == null ? 0.0
					: billDetail.getPtAmount() * billDetail.getPtPrice());

			Double ptPrice = f.getMateriel().getPtPrice() == null ? 0.0 : f
					.getMateriel().getPtPrice();

			// if (f.getUnitConvert() == null) {
			// throw new RuntimeException("单据中心--对应关系料号为：【"
			// + f.getMateriel().getPtNo() + "】的单位折算为空。");
			// } else if (f.getUnitConvert().doubleValue() == Double.valueOf(0))
			// {
			// throw new RuntimeException("单据中心--对应关系料号为：【"
			// + f.getMateriel().getPtNo() + "】的单位折算为零。");
			// }
			Double hsPrice = 0.0;
			if (f.getUnitConvert() != null
					&& f.getUnitConvert().doubleValue() != Double.valueOf(0)) {
				hsPrice = ptPrice
						/ Double.parseDouble(f.getUnitConvert().toString());
			}
			billDetail.setHsPrice(hsPrice);// 海关单价
			billDetail.setComplex(f.getStatCusNameRelationHsn().getComplex());
			billDetail.setHsName(f.getStatCusNameRelationHsn().getCusName());
			billDetail.setHsSpec(f.getStatCusNameRelationHsn().getCusSpec());
			billDetail.setHsUnit(f.getStatCusNameRelationHsn().getCusUnit());
			billDetail.setHsAmount((billDetail.getPtAmount() == null ? 0.0
					: billDetail.getPtAmount())
					* (f.getUnitConvert() != null ? f.getUnitConvert() : 0.0));
			// billDetail.setEmsNo(f.getStatCusNameRelationHsn().getEmsNo());//手册号有争议，故先屏蔽

		} else {
			billDetail.setNote("没有做对应关系");
		}
		billDetail.setCompany(CommonUtils.getCompany());
		this.casDao.saveOrUpdate(billDetail);
	}

	/**
	 * 填充期初单 半成品相关的 单据体
	 * 
	 * @param billDetail
	 * @param billMaster
	 * @param temp
	 * @param wareSetMap
	 * @param mapPtNoFFC
	 * @param mapHsInfoFFC
	 * @return
	 * @author wss2010.10.07
	 */
	private void fillHalfProductInitBillDetail(BillDetail billDetail,
			BillMaster billMaster, BillInit temp,
			Map<String, WareSet> wareSetMap, Map<String, Materiel> mapPtNoM) {
		if (temp == null) {
			return;
		}
		billDetail.setBillMaster(billMaster);
		billDetail.setBillNo(billMaster.getBillNo());

		billDetail.setPtPart(temp.getPtNo());

		billDetail.setPtAmount((temp.getPtAmount() == null) ? 0.0 : temp
				.getPtAmount());
		billDetail.setWareSet(wareSetMap.get(temp.getWareSetCode() == null ? ""
				: temp.getWareSetCode()));
		billDetail.setVersion(temp.getVersion());
		billDetail.setNote(temp.getNote());
		Materiel m = mapPtNoM.get(temp.getPtNo());

		if (m != null) {
			System.out.println("wss 半成品");
			// billDetail.setNote("半成品");
			billDetail.setPtName(m.getFactoryName());
			billDetail.setPtSpec(m.getFactorySpec());
			billDetail.setPtUnit(m.getCalUnit());
			billDetail.setNetWeight(m.getPtNetWeight() != null ? m
					.getPtNetWeight()
					* billDetail.getPtAmount() : 0.0);
			billDetail.setPtPrice(m.getPtPrice());
			billDetail.setMoney(billDetail.getPtPrice() == null ? 0.0
					: billDetail.getPtAmount() * billDetail.getPtPrice());
			billDetail.setComplex(m.getComplex());

			billDetail.setHsName(m.getPtName());
			billDetail.setHsSpec(m.getPtSpec());
			billDetail.setHsUnit(m.getPtUnit());

			billDetail.setHsAmount((billDetail.getPtAmount() == null ? 0.0
					: billDetail.getPtAmount())
					* (m.getUnitConvert() != null ? m.getUnitConvert() : 0.0));
		} else {
			billDetail.setNote("无此料号的半成品资料");
		}

		billDetail.setCompany(CommonUtils.getCompany());
		this.casDao.saveOrUpdate(billDetail);
	}

	/**
	 * 生成单据头与体
	 * 
	 * @param map
	 * @param mMap
	 * @param fMap
	 * @param typeCode
	 */
	public void updateBillByMap(HashMap<String, Object> map, String typeCode) {
		if (map.size() != 0) {
			String year = CommonUtils.getYear();
			String billNo = year + "01010001";
			String type = "";
			if (!typeCode.equals("2001")) {
				type = MaterielType.MATERIEL;
			} else {
				type = MaterielType.FINISHED_PRODUCT;
			}
			//
			// 先清空以前的
			//
			this.casDao.deleteBillByInit(typeCode, billNo);

			List<FactoryAndFactualCustomsRalation> flist = this.casDao
					.findFactoryAndFactualCustomsRalation(type);
			HashMap<String, FactoryAndFactualCustomsRalation> fMap = new HashMap();
			for (FactoryAndFactualCustomsRalation f : flist) {
				if (fMap.get(f.getMateriel().getPtNo()) == null) {
					fMap.put(f.getMateriel().getPtNo(), f);
				}
			}
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.valueOf(CommonUtils.getYear()));
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date beginDate = calendar.getTime();

			// 生成单据头
			BillMaster billMaster = null;
			if (!typeCode.equals("2001")) {
				billMaster = new BillMasterMateriel();
			} else {
				billMaster = new BillMasterProduct();
			}
			billMaster.setBillNo(billNo);
			billMaster.setBillType(this.casDao.findBillTypeByCode(typeCode));
			billMaster.setCompany(CommonUtils.getCompany());
			billMaster.setIsValid(true);
			billMaster.setValidDate(beginDate);
			billMaster.setCreateDate(new Date());
			this.casDao.saveOrUpdate(billMaster);

			Map<String, WareSet> wareSetMap = new HashMap<String, WareSet>();
			List<WareSet> wareSet = this.casDao.findWareSet();
			for (WareSet w : wareSet) {
				wareSetMap.put(w.getCode(), w);
			}

			// 生成单据明细
			for (Map.Entry<String, Object> item : map.entrySet()) {
				BillDetail b = null;
				if (!typeCode.equals("2001")) {
					b = new BillDetailMateriel();
				} else {
					b = new BillDetailProduct();
				}
				//
				//
				//				
				if (item.getValue() instanceof BillInit) {
					BillInit temp = (BillInit) item.getValue();
					b.setBillMaster(billMaster);
					b.setPtPart(temp == null ? "" : temp.getPtNo());
					b.setBillNo(billMaster.getBillNo());
					b
							.setPtAmount((temp == null || temp.getPtAmount() == null) ? 0.0
									: temp.getPtAmount());
					b.setWareSet(wareSetMap.get(temp == null ? "" : temp
							.getWareSetCode()));
					FactoryAndFactualCustomsRalation f = fMap
							.get(b.getPtPart());
					if (f != null) {
						b.setPtName(f.getMateriel().getFactoryName());
						b.setPtSpec(f.getMateriel().getFactorySpec());
						b.setPtUnit(f.getMateriel().getCalUnit());
						b
								.setNetWeight(f.getMateriel().getPtNetWeight() != null ? f
										.getMateriel().getPtNetWeight()
										* b.getPtAmount()
										: 0.0);
						b.setPtPrice(f.getMateriel().getPtPrice());
						b.setMoney(b.getPtPrice() == null ? 0.0 : b
								.getPtAmount()
								* b.getPtPrice());
						b
								.setComplex(f.getStatCusNameRelationHsn()
										.getComplex());
						b.setHsName(f.getStatCusNameRelationHsn().getCusName());
						b.setHsSpec(f.getStatCusNameRelationHsn().getCusSpec());
						b.setHsUnit(f.getStatCusNameRelationHsn().getCusUnit());
						b.setHsAmount((b.getPtAmount() == null ? 0.0 : b
								.getPtAmount())
								* (f.getUnitConvert() != null ? f
										.getUnitConvert() : 0.0));
					}
					this.casDao.saveOrUpdate(b);
				} else { // value is double
					b.setBillMaster(billMaster);
					b.setPtPart(item.getKey());
					b.setBillNo(billMaster.getBillNo());
					b.setPtAmount(item.getValue() == null ? 0.0 : (Double) item
							.getValue());
					FactoryAndFactualCustomsRalation f = fMap
							.get(b.getPtPart());
					if (f != null) {
						b.setPtName(f.getMateriel().getFactoryName());
						b.setPtSpec(f.getMateriel().getFactorySpec());
						b.setPtUnit(f.getMateriel().getCalUnit());
						b
								.setNetWeight(f.getMateriel().getPtNetWeight() != null ? f
										.getMateriel().getPtNetWeight()
										* b.getPtAmount()
										: 0.0);
						b.setPtPrice(f.getMateriel().getPtPrice());
						b.setMoney(b.getPtPrice() == null ? 0.0 : b
								.getPtAmount()
								* b.getPtPrice());
						b
								.setComplex(f.getStatCusNameRelationHsn()
										.getComplex());
						b.setHsName(f.getStatCusNameRelationHsn().getCusName());
						b.setHsSpec(f.getStatCusNameRelationHsn().getCusSpec());
						b.setHsUnit(f.getStatCusNameRelationHsn().getCusUnit());
						b.setHsAmount((b.getPtAmount() == null ? 0.0 : b
								.getPtAmount())
								* (f.getUnitConvert() != null ? f
										.getUnitConvert() : 0.0));
					}
					this.casDao.saveOrUpdate(b);
				}
			}
		}
	}

	/**
	 * 车间材料库存统计表（按料号统计）
	 * 
	 * @param taskId
	 * @param beginDate
	 * @param endDate
	 * @return (wss:2010.07.02检查)
	 */
	public List getF25ByPtNo(String taskId, Date beginDate, Date endDate,
			ParameterMaps parameterMaps) {
		ProgressInfo progressInfo = ProcExeProgress.getInstance()
				.getProgressInfo(taskId);

		/*
		 * 日期
		 */
		if (beginDate == null) {
			beginDate = CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
					.getYear()), 0, 1);
		}
		if (endDate == null) {
			endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
					.getYear()), 11, 31);
		}

		/*
		 * 查找所有料件单据,计算F24,25栏
		 */
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));
		String billDetailMateriel = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);
		String selects = "select sum(a.ptAmount),a.billMaster.billType.code,a.ptPart,"
				+ "a.ptName,a.ptSpec,a.ptUnit.name ";
		String groupBy = "group by a.billMaster.billType.code,a.ptPart,a.ptName,a.ptSpec,a.ptUnit.name ";
		String leftOuter = " left join a.billMaster left join a.billMaster.billType left join a.ptUnit ";

		List<Object[]> listBillDetailMateriel = casDao.commonSearch(selects,
				billDetailMateriel, conditions, groupBy, leftOuter);

		System.out
				.println("wss 料件list.size = " + listBillDetailMateriel.size());
		/*
		 * 加上5001残次品入库 （料件）wss:2010.07.01
		 */
		String billDetailRemainProduct = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);
		conditions.add(new Condition("and", null, "billMaster.billType.code",
				"=", "5001", null));
		conditions.add(new Condition("and", null, "note", "=", "料件", null));

		List<Object[]> listBillDetailRemainProduct = casDao.commonSearch(
				selects, billDetailRemainProduct, conditions, groupBy,
				leftOuter);

		System.out.println("wss 5001入库残次品（料件）list.size = "
				+ listBillDetailRemainProduct.size());

		/*
		 * 遍历料件单据 及 残次品(料件)单据均为不用折料的
		 */
		listBillDetailMateriel.addAll(listBillDetailRemainProduct);

		/**
		 * 料号 对应 billTemp
		 */
		HashMap<String, BillTemp> f25Map = new HashMap();
		for (Object[] item : listBillDetailMateriel) {
			BillTemp tmp = new BillTemp();

			Double ptAmount = item[0] == null ? 0.0 : (Double) item[0];//数量
			String typeCode = (String) item[1];
			String ptNo = item[2] == null ? "" : item[2].toString();//单据类型代码
			String ptName = item[3] == null ? "" : item[3].toString();//商品名称
			String ptSpec = item[4] == null ? "" : item[4].toString();//商品规格
			String ptUnitName = item[5] == null ? "" : item[5].toString();//计量单位名称


			if (ptNo != null && !ptNo.equals("")) {

				if (f25Map.get(ptNo) == null) {
					tmp.setBill1(ptNo);
					tmp.setBill2(ptName);
					tmp.setBill3(ptSpec);
					tmp.setBill4(ptUnitName);
					f25Map.put(ptNo, tmp);
				} else {
					tmp = f25Map.get(ptNo);
				}

				if (typeCode != null) {

					/***********************************************************
					 * 车间在产品数 + 1002在产品期初单 + 1101车间领用 - 1006车间返回 + 4101半成品出库*BOM
					 * - 4001半成品入库*BOM - 2002车间入库*BOM + 2103车间返回*BOM -
					 * 5001残次品入库(半成品\成品)*BOM - 5001残次品入库(料件)
					 * 
					 * (以下是不用折料的料件部分) wss:2010.07.02确认
					 **********************************************************/
					if("0A73600.".equals(ptNo)) {
						System.out.println("typeCode:" + typeCode + ",amount:" + ptAmount);
					}
					
					if (typeCode.equals("1101") || typeCode.equals("1002")) {
						Double amount1 = CommonUtils.getDoubleExceptNull(tmp
								.getBillSum1())
								+ ptAmount;

						tmp.setBillSum1(amount1);// wss这里

						f25Map.put(ptNo, tmp);
					} else if (typeCode.equals("1006")
							|| typeCode.equals("5001")) {
						Double amount1 = CommonUtils.getDoubleExceptNull(tmp
								.getBillSum1())
								- ptAmount;
						tmp.setBillSum1(amount1);// wss这里
						f25Map.put(ptNo, tmp);
					} else {
						if("0A73600.".equals(ptNo)) {
						System.out.println("穿越了!");
						}
					}
				}
			}
		}

		/*
		 * 料件-对应关系
		 */
		List<FactoryAndFactualCustomsRalation> flist = this.casDao
				.findFactoryAndFactualCustomsRalation(MaterielType.MATERIEL);

		HashMap<String, FactoryAndFactualCustomsRalation> fMap = new HashMap();
		for (FactoryAndFactualCustomsRalation f : flist) {
			if (fMap.get(f.getMateriel().getPtNo()) == null) {
				fMap.put(f.getMateriel().getPtNo(), f);
			}
		}

		/*
		 * 查找成品，计算25栏
		 */
		if (parameterMaps == null) {
			parameterMaps = new ParameterMaps();
			parameterMaps.statCusNameRelationMtValueByMaterielMap = fMap;//	在下面折算成品的方法时，需要料件-对应关系
			// 在这里折料
			this.initBillDetailProductMap(beginDate, endDate, parameterMaps,
					progressInfo);
		}

		/*
		 * 取常用报关物料，以作下续循环使用
		 */
		List materiel = this.commonBaseCodeDao.findMateriel();

		/**
		 * 报关常用工厂物料 料号集合
		 */
		List<String> ptNoList = new ArrayList();
		for (int i = 0; i < materiel.size(); i++) {
			Materiel m = (Materiel) materiel.get(i);
			ptNoList.add(m.getPtNo());
		}
		/**
		 * 料号 对应 billTemp
		 */
		HashMap<String, BillTemp> returnMap = new HashMap();
		for (String item : ptNoList) {

			// 2002车间入库*BOM
			Double ptAmount2002 = parameterMaps.billDetail2002MapByF25
					.get(item);

			// Double ptAmount2007 = parameterMaps.billDetail2007MapByF25
			// .get(item);

			// Double ptAmount2110 = parameterMaps.billDetail2110MapByF25
			// .get(item);

			// 2103车间返回*BOM
			Double ptAmount2103 = parameterMaps.billDetail2103MapByF25
					.get(item);

			// 4001半成品入库*BOM
			Double ptAmount4001 = parameterMaps.billDetail4001SemiProductMapByF25
					.get(item);

			// 4101半成品出库*BOM
			Double ptAmount4101 = parameterMaps.billDetail4101SemiProductMapByF25
					.get(item);

			// 5001残次品入库(半成品\成品)*BOM
			Double ptAmount5001 = parameterMaps.billDetail5001FinishedAndSemiBadProductMapByF25
					.get(item);

			Double ptAmount = (ptAmount2002 == null ? 0 : ptAmount2002)
					// + (ptAmount2007 == null ? 0 : ptAmount2007)
					// - (ptAmount2110 == null ? 0 : ptAmount2110)
					- (ptAmount2103 == null ? 0 : ptAmount2103)
					+ (ptAmount4001 == null ? 0 : ptAmount4001)
					- (ptAmount4101 == null ? 0 : ptAmount4101)
					+ (ptAmount5001 == null ? 0 : ptAmount5001);

			// 1.料件 与 成品折料有 同的
			if (f25Map.get(item) != null) {
				BillTemp tmp = f25Map.get(item);
				returnMap.put(item, tmp);

				/***************************************************************
				 * 车间在产品数 + 1002在产品期初单 + 1101车间领用 - 1006车间返回 + 4101半成品出库*BOM -
				 * 4001半成品入库*BOM - 2002车间入库*BOM + 2103车间返回*BOM -
				 * 5001残次品入库(半成品\成品)*BOM - 5001残次品入库(料件)
				 * 
				 * （此处是要折料的部分） wss:2010.07.02确认
				 **************************************************************/
				if("0A73600.".equals(item)) {
					System.out.println("amount:" + ptAmount);
				}
				tmp.setBillSum1(tmp.getBillSum1() == null ? -ptAmount : (tmp
						.getBillSum1() - ptAmount));

				if (fMap.get(tmp.getBill1()) != null) {
					FactoryAndFactualCustomsRalation f = fMap.get(tmp
							.getBill1());
					tmp.setBillSum2(CommonUtils.getDoubleExceptNull(tmp
							.getBillSum1())
							* CommonUtils.getDoubleExceptNull(f
									.getUnitConvert()));

					tmp.setBill5(f.getStatCusNameRelationHsn().getCusName());// 报关名称
					tmp.setBill6(f.getStatCusNameRelationHsn().getCusSpec());// 报关规格
					tmp.setBill7(f.getStatCusNameRelationHsn().getCusUnit()
							.getName());// 报关单位
				}

			}
			// 2.料件与折料 没有相同的
			else if (parameterMaps.billDetail2002MapByF25.get(item) != null
					// || parameterMaps.billDetail2007MapByF25.get(item) != null
					// || parameterMaps.billDetail2110MapByF25.get(item) != null
					|| parameterMaps.billDetail2103MapByF25.get(item) != null
					|| parameterMaps.billDetail4001SemiProductMapByF25
							.get(item) != null
					|| parameterMaps.billDetail4101SemiProductMapByF25
							.get(item) != null
					|| parameterMaps.billDetail5001FinishedAndSemiBadProductMapByF25
							.get(item) != null) {

				BillTemp tmp = null;

				// 折料与折料有相同的
				if (returnMap.get(item) != null) {
					tmp = returnMap.get(item);
				}
				// 折料与折料没有相同的
				else {
					tmp = new BillTemp();

				}
				returnMap.put(item, tmp);
				tmp.setBill1(item);

				// wss这里
				tmp.setBillSum1(-ptAmount);

				if (fMap.get(tmp.getBill1()) != null) {
					FactoryAndFactualCustomsRalation f = fMap.get(tmp
							.getBill1());
					tmp.setBillSum2(CommonUtils.getDoubleExceptNull(tmp
							.getBillSum1())
							* CommonUtils.getDoubleExceptNull(f
									.getUnitConvert()));

					tmp.setBill2(f.getMateriel().getFactoryName());// 工厂名称
					tmp.setBill3(f.getMateriel().getFactorySpec());// 工厂规格
					tmp.setBill4(f.getMateriel().getCalUnit().getName());// 工厂单位
					tmp.setBill5(f.getStatCusNameRelationHsn().getCusName());// 报关名称
					tmp.setBill6(f.getStatCusNameRelationHsn().getCusSpec());// 报关规格
					tmp.setBill7(f.getStatCusNameRelationHsn().getCusUnit()
							.getName());// 报关单位

				}
			}
		}

		List list = new ArrayList();
		Iterator it = returnMap.values().iterator();
		while (it.hasNext()) {
			BillTemp tmp = (BillTemp) it.next();
			list.add(tmp);
		}
		return list;
	}

	/**
	 * 临时方法，车间材料库存统计表（按报关名称统计）
	 * 
	 * @param taskId
	 * @param beginDate
	 * @param endDate
	 * @param parameterMaps
	 * @return
	 */
	public Map<String, BillTemp> getF25ByHsKeyForTemp(String taskId,
			Date beginDate, Date endDate, ParameterMaps parameterMaps) {
		List<BillTemp> list = this.getF25ByPtNo(taskId, beginDate, endDate,
				parameterMaps);
		Map<String, BillTemp> map = new HashMap();
		for (BillTemp item : list) {
			String key = item.getBill5() + " / " + item.getBill6() + " / "
					+ item.getBill7();
			BillTemp tmp = null;
			if (!map.containsKey(key)) {
				tmp = new BillTemp();
				tmp.setBill1(key);
				tmp.setBill2(item.getBill5());
				tmp.setBill3(item.getBill6());
				tmp.setBill4(item.getBill7());
				map.put(key, tmp);
			} else {
				tmp = map.get(key);
			}

			tmp.setBillSum1(CommonUtils.getDoubleExceptNull(tmp.getBillSum1())
					+ CommonUtils.getDoubleExceptNull(item.getBillSum1()));
			tmp.setBillSum2(CommonUtils.getDoubleExceptNull(tmp.getBillSum2())
					+ CommonUtils.getDoubleExceptNull(item.getBillSum2()));

		}
		return map;
	}

	/**
	 * 临时方法，车间材料库存统计表（按报关名称统计）
	 * 
	 * @param taskId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List getF25ByHsKey2(String taskId, Date beginDate, Date endDate) {
		Map<String, BillTemp> map = this.getF25ByHsKeyForTemp(taskId,
				beginDate, endDate, null);
		List list = new ArrayList();
		Iterator it = map.values().iterator();
		while (it.hasNext()) {
			BillTemp tmp = (BillTemp) it.next();
			list.add(tmp);
		}
		return list;
	}

	
	/**
	 * 改变记账年度是生成料件期初单与在产品期初单
	 * 
	 * @param taskId
	 * @return
	 */
	public Map<String, Map<String, Map<String, Double>>> getMaterielF24F25ForBillByCustom(
			String taskId, Date beginDate, Date endDate) {
//		ProgressInfo progressInfo = ProcExeProgress.getInstance()
//				.getProgressInfo(taskId);
		if (beginDate == null) {
			beginDate = CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
					.getYear()), 0, 1);
		}
		if (endDate == null) {
			endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
					.getYear()), 11, 31);
		}
//		String year = CommonUtils.getYear() == null ? String.valueOf(Calendar
//				.getInstance().get(Calendar.YEAR)) : CommonUtils.getYear();
		// 查找料件，计算F24,25栏
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions
				.add(new Condition("and", null, "ptAmount ", "!=", 0.0, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));
		String billDetailMateriel = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);
		String selects = "select sum(a.ptAmount),a.billMaster.billType.code,a.ptPart,a.billMaster.scmCoc.name ";
		String groupBy = "group by a.billMaster.billType.code,a.billMaster.scmCoc.name,a.ptPart ";
		String leftOuter = " left join a.billMaster left join a.billMaster.billType	";
		List<Object[]> listBillDetailMateriel = casDao.commonSearch(selects,
				billDetailMateriel, conditions, groupBy, leftOuter);
		HashMap<String, Map<String, Double>> f24Map = new HashMap();
		HashMap<String, Map<String, Double>> f25Map = new HashMap();
		HashMap<String, Map<String, Double>> f244Map = new HashMap();
		HashMap<String, Map<String, Double>> f255Map = new HashMap();
		for (Object[] item : listBillDetailMateriel) {
			Double ptAmount = (Double) item[0];
			String typeCode = (String) item[1];
			String ptNo = item[2] == null ? "" : item[2].toString();
			String scmCocName = item[3] == null ? "" : item[3].toString();
			Map<String, Double> f24SubMap = null;
			Map<String, Double> f25SubMap = null;
			// 初始化F24
			if (f24Map.get(scmCocName) != null) {
				f24SubMap = f24Map.get(scmCocName);
			} else {
				f24SubMap = new HashMap();
				f24Map.put(scmCocName, f24SubMap);
			}
			// 初始化F25
			if (f25Map.get(scmCocName) != null) {
				f25SubMap = f25Map.get(scmCocName);
			} else {
				f25SubMap = new HashMap();
				f25Map.put(scmCocName, f25SubMap);
			}
			if (typeCode != null && ptNo != null && !ptNo.equals("")) {
				//
				// 24． 其中：库存原材料：各料件进仓单据折算报关数量的累加－各料件出仓单据折算报关数量的累加
				// 入库
				// 1015 已收货未结转期初单
				// 1016 已结转未收货期初单
				// 1014 委外期初单 1013 外发加工返回料件 不放入入库
				//
				if (typeCode.equals("1001") // 1001 料件期初单
						// || typeCode.equals("1002") //1002 在产品期初单
						|| typeCode.equals("1003") // 1003 直接进口
						|| typeCode.equals("1004") // 1004 结转进口
						|| typeCode.equals("1005") // 1005 国内购买
						|| typeCode.equals("1006") // 1006 车间返回
						|| typeCode.equals("1007") // 1007 料件盘盈单
						|| typeCode.equals("1008") // 1008 受托加工进库
						|| typeCode.equals("1009") // 1009 其他来源
						|| typeCode.equals("1010") // 1010 料件转仓入库
						|| typeCode.equals("1011") // 1011 海关征税进口
				// || typeCode.equals("1012") //1012 外发加工退回料件
				) {
					if (f24SubMap.get(ptNo) == null) {
						f24SubMap.put(ptNo, ptAmount);
					} else {
						Double tmp = f24SubMap.get(ptNo) + ptAmount;
						f24SubMap.put(ptNo, tmp);
					}
				} else if (typeCode.equals("1101") // 1101 车间领用
						|| typeCode.equals("1102") // 1102 料件退换
						|| typeCode.equals("1103") // 1103 料件复出
						|| typeCode.equals("1104") // 1104 料件盘亏单
						|| typeCode.equals("1105") // 1105 料件转仓出库
						|| typeCode.equals("1106") // 1106 结转料件退货单
						|| typeCode.equals("1107") // 1107 其他料件退货单
						|| typeCode.equals("1108") // 1108 其他领用
						|| typeCode.equals("1109") // 1109 受托加工领用
						|| typeCode.equals("1110") // 1110 外发加工出库
						|| typeCode.equals("1111") // 1111 受托加工退回料件
				) { // 出库
					if (f24SubMap.get(ptNo) == null) {
						f24SubMap.put(ptNo, -ptAmount);
					} else {
						Double tmp = f24SubMap.get(ptNo) - ptAmount;
						f24SubMap.put(ptNo, tmp);
					}
				}

				//
				// 25 .这是料件统计后面还有成品统计
				// 库存在产品耗用 .这是料件统计后面还有成品统计
				// 1101 车间领用 + 1002 在产品起初单 + 1109 受托加工领用 - 1006 车间返回
				//
				if (typeCode.equals("1101") || typeCode.equals("1002")
						|| typeCode.equals("1109")) {
					if (f25SubMap.get(ptNo) == null) {
						f25SubMap.put(ptNo, ptAmount);
					} else {
						Double tmp = f25SubMap.get(ptNo) + ptAmount;
						f25SubMap.put(ptNo, tmp);
					}
				} else if (typeCode.equals("1006")) {
					if (f25SubMap.get(ptNo) == null) {
						f25SubMap.put(ptNo, -ptAmount);
					} else {
						Double tmp = f25SubMap.get(ptNo) - ptAmount;
						f25SubMap.put(ptNo, tmp);
					}
				}
			}

		}
		// 查找成品，计算25栏
		Map<String, ParameterMaps> productMap = this
				.getMaterielF25FromProductByCustom(beginDate, endDate);
		for (Map.Entry<String, ParameterMaps> item : productMap.entrySet()) {
			Map<String, Double> f25SubMap = null;
			if (f25Map.get(item.getKey()) != null) {
				f25SubMap = f25Map.get(item.getKey());
				Iterator<String> ptNoList = f25SubMap.keySet().iterator();
				while (ptNoList.hasNext()) {
					String ptNo = ptNoList.next();
					//
					// （ 车间入库－返回车间＋外发加工入库－外发加工返修成品＋受托加工车间入库）数量＊对应版本单项用量
					//
					//
					// 获得该成品料号和版本对应的
					// 2002 车间入库 +2007 受托加工车间入库
					// - 2110 受托加工返回 - 2103返回车间
					// 
					Double ptAmount2002 = item.getValue().billDetail2002Map
							.get(ptNo);
					Double ptAmount2007 = item.getValue().billDetail2007Map
							.get(ptNo);
					Double ptAmount2110And2002 = item.getValue().billDetail2112Map
							.get(ptNo);
					Double ptAmount2103 = item.getValue().billDetail2103Map
							.get(ptNo);

					Double ptAmount = (ptAmount2002 == null ? 0 : ptAmount2002)
							+ (ptAmount2007 == null ? 0 : ptAmount2007)
							- (ptAmount2110And2002 == null ? 0
									: ptAmount2110And2002)
							- (ptAmount2103 == null ? 0 : ptAmount2103);
					if (f25SubMap.get(ptNo) == null) {
						f25SubMap.put(ptNo, -ptAmount);
					} else {
						f25SubMap.put(ptNo, f25SubMap.get(ptNo) - ptAmount);
					}
				}
			}
		}

		// 去掉数量为0的数据
		for (Map.Entry<String, Map<String, Double>> item : f24Map.entrySet()) {
			Map<String, Double> f244SubMap = new HashMap();
			if (item.getValue().entrySet() != null
					&& item.getValue().entrySet().size() != 0) {
				for (Map.Entry<String, Double> tmp : item.getValue().entrySet()) {
					if (!tmp.getValue().equals(0.0) && tmp.getValue() != 0.0) {
						f244SubMap.put(tmp.getKey(), tmp.getValue());
					}
				}
			}
			f244Map.put(item.getKey(), f244SubMap);
		}
		for (Map.Entry<String, Map<String, Double>> item : f25Map.entrySet()) {
			Map<String, Double> f255SubMap = new HashMap();
			if (item.getValue().entrySet() != null
					&& item.getValue().entrySet().size() != 0) {
				for (Map.Entry<String, Double> tmp : item.getValue().entrySet()) {
					if (!tmp.getValue().equals(0.0) && tmp.getValue() != 0.0) {
						f255SubMap.put(tmp.getKey(), tmp.getValue());
					}
				}
			}
			f255Map.put(item.getKey(), f255SubMap);
		}
		Map<String, Map<String, Map<String, Double>>> returnMap = new HashMap();
		returnMap.put("f24", f24Map);
		returnMap.put("f25", f25Map);

		return returnMap;
	}

	/**
	 * 获得25来自海关成品
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private Map<String, ParameterMaps> getMaterielF25FromProductByCustom(
			Date beginDate, Date endDate) {
		Map<String, ParameterMaps> returnMaps = new HashMap();
		String productTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);

		String selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id,a.billMaster.scmCoc.name from "
				+ productTableName
				+ " a "
				+ ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = 0 )) "
				+ " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ?  and a.ptAmount !=0 and a.billMaster.billType.code in('2002','2007','2110','2103')  "//
				+ " group by a.billMaster.billType.code,a.billMaster.scmCoc.name,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次

		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(beginDate);
		params.add(endDate);
		params.add(CommonUtils.getCompany().getId());
		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, params.toArray());

		logger.info("CAS 开始查询所有成品的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

		// 存取分页查询bom时所临时存放的成品列表
		//
		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String scmCocName = (String) objs[3];
			ParameterMaps pMaps = null;
			if (scmCocName != null) {
				if (returnMaps.get(scmCocName) != null) {
					pMaps = returnMaps.get(scmCocName);
					// pMaps = tmp;
					// pMaps.billDetail2002Map = tmp.billDetail2002Map;
					// pMaps.billDetail2007Map = tmp.billDetail2002Map;
					// pMaps.billDetail2110Map = tmp.billDetail2002Map;
					// pMaps.billDetail2103Map = tmp.billDetail2002Map;
				} else {
					pMaps = new ParameterMaps();
					returnMaps.put(scmCocName, pMaps);
				}
			}

			String versionId = (String) objs[2]; // version guid

			if (versionId == null) {
				continue;
			}
			//
			// 为了去掉重复的 versionId
			//
			versionIdMap.put(versionId, versionId);
			tempList.add(objs);

			if ((i != 0 && (versionIdMap.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				// String clientTipMessage = "CAS 成品总记录 " + size
				// + " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				// logger.info(clientTipMessage);
				// progressInfo.setHintMessage(clientTipMessage);

				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(versionIdMap.values());
				List materialBomDetailList = this.materialManageDao
						.findMaterielBomDetail(versionIdList);
				versionIdList.clear();
				versionIdMap.clear();

				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

				if (materialBomDetailList.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}

				//
				// 获取成品版本号对应的料件明细
				//
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = materialBomDetailList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) materialBomDetailList.get(j);
					String tempVersionId = (String) bomObjs[1];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(bomObjs);
					}
				}

				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double ptAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 工厂数量
					String typeCode = (String) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // version guid

					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);

					if (tempObjes == null) {
						logger.info("Cas 计算时 Bom 明细为空  !! == " + tempVersionId);
						continue;
					}

					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						//
						// 料件料号为 key
						//
						String ptNoKey = (String) bomObjs[0];
						//
						// 成品单耗
						//
						Double unitWaste = bomObjs[2] == null ? 0.0
								: (Double) bomObjs[2];
						//
						// 成品单
						//
//						Double unitUsed = bomObjs[3] == null ? 0.0
//								: (Double) bomObjs[3];
						//
						// 损耗
						//
						Double waste = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];
						//
						// 成品单耗损
						//
//						Double productMaterialWaste = ptAmount * unitUsed
//								* waste;
						//
						// 成品单耗用量
						//
						// Double productMaterialUnitWaste = ptAmount *
						// unitWaste;
						Double productMaterialUnitWaste = ptAmount
								* (unitWaste / (1 - waste));

						if (typeCode.equals("2002")) {// 2002 车间入库
							if (pMaps.billDetail2002Map.get(ptNoKey) == null) {
								pMaps.billDetail2002Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = pMaps.billDetail2002Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								pMaps.billDetail2002Map.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2007")) {// 2007 受托加工车间入库
							if (pMaps.billDetail2007Map.get(ptNoKey) == null) {
								pMaps.billDetail2007Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = pMaps.billDetail2007Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								pMaps.billDetail2007Map.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2110")// 2110 受托加工返回
								|| (typeCode.equals("2112"))) {// wss:2010.04.19:新增2112受托加工成品出库
							if (pMaps.billDetail2112Map.get(ptNoKey) == null) {
								pMaps.billDetail2112Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = pMaps.billDetail2112Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								pMaps.billDetail2112Map.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2103")) {// 2103 返回车间
							if (pMaps.billDetail2103Map.get(ptNoKey) == null) {
								pMaps.billDetail2103Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = pMaps.billDetail2103Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								pMaps.billDetail2103Map.put(ptNoKey, tempAmout);
							}
						}
					}
				}
				tempList.clear();
			}
		}
		return returnMaps;
	}

	/**
	 * 改变记账年度时生成成品期初单
	 * 
	 * @param taskId
	 */
	public Map<String, Map<String, Double>> getProductF1ForBillByCustom(
			String taskId, Date beginDate, Date endDate) {
		Map<String, Map<String, Double>> f1Map = new HashMap();
		Map<String, Map<String, Double>> f11Map = new HashMap();
		if (beginDate == null) {
			beginDate = CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
					.getYear()), 0, 1);
		}
		if (endDate == null) {
			endDate = CommonUtils.getEndDate(Integer.valueOf(CommonUtils
					.getYear()), 11, 31);
		}
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));

		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));

		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions
				.add(new Condition("and", null, "ptAmount ", "!=", 0.0, null));

		String productBillDetail = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);// 成品明细表

		String selects = "select sum(a.ptAmount),a.billMaster.billType.code,a.ptPart,a.billMaster.scmCoc.name ";
		String groupBy = "group by a.billMaster.billType.code,a.billMaster.scmCoc.name,a.ptPart ";
		String leftOuter = " left join a.billMaster	";

		List<Object[]> listBillDetailProduct = casDao.commonSearch(selects,
				productBillDetail, conditions, groupBy, leftOuter);

		for (Object[] item : listBillDetailProduct) {

			Double ptAmount = (Double) item[0];
			String typeCode = (String) item[1];
			String ptNo = (String) item[2];
			String scmCocName = item[3] == null ? "" : item[3].toString();
			if (ptNo != null && !ptNo.equals("")) {
				Map<String, Double> f1SubMap = null;
				// 初始化F24
				if (f1Map.get(scmCocName) != null) {
					f1SubMap = f1Map.get(scmCocName);
				} else {
					f1SubMap = new HashMap();
					f1Map.put(scmCocName, f1SubMap);
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
					if (f1SubMap.get(ptNo) == null) {
						f1SubMap.put(ptNo, ptAmount);
					} else {
						Double tmp = f1SubMap.get(ptNo) + ptAmount;
						f1SubMap.put(ptNo, tmp);
					}
					// item.setF1(item.getF1() + hsAmount);
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
					if (f1SubMap.get(ptNo) == null) {
						f1SubMap.put(ptNo, -ptAmount);
					} else {
						Double tmp = f1SubMap.get(ptNo) - ptAmount;
						f1SubMap.put(ptNo, tmp);
					}
					// item.setF1(item.getF1() - hsAmount);
				}
			}
		}
		for (Map.Entry<String, Map<String, Double>> item : f1Map.entrySet()) {
			Map<String, Double> f11SubMap = new HashMap();
			if (item.getValue().entrySet() != null
					&& item.getValue().entrySet().size() != 0) {
				for (Map.Entry<String, Double> tmp : item.getValue().entrySet()) {
					if (!tmp.getValue().equals(0.0) && tmp.getValue() != 0.0) {
						f11SubMap.put(tmp.getKey(), tmp.getValue());
					}
				}
			}
			f11Map.put(item.getKey(), f11SubMap);
		}

		return f1Map;
	}

	/**
	 * 生成单据头与体(按客户)
	 * 
	 * @param map
	 * @param mMap
	 * @param fMap
	 * @param typeCode
	 */
	public void updateBillByMapForCustom(
			HashMap<String, Map<String, Double>> map, String typeCode) {
		if (map.size() != 0) {
			String type = "";
			if (!typeCode.equals("2001")) {
				type = MaterielType.MATERIEL;
			} else {
				type = MaterielType.FINISHED_PRODUCT;
			}
			List<FactoryAndFactualCustomsRalation> flist = this.casDao
					.findFactoryAndFactualCustomsRalation(type);
			HashMap<String, FactoryAndFactualCustomsRalation> fMap = new HashMap();
			for (FactoryAndFactualCustomsRalation f : flist) {
				if (fMap.get(f.getMateriel().getPtNo()) == null) {
					fMap.put(f.getMateriel().getPtNo(), f);
				}
			}
			List<ScmCoc> sList = this.casDao.findScmcoc();
			HashMap<String, ScmCoc> sMap = new HashMap();
			for (ScmCoc s : sList) {
				if (sMap.get(s.getName()) == null) {
					sMap.put(s.getName(), s);
				}
			}
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.valueOf(CommonUtils.getYear()));
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date beginDate = calendar.getTime();
			String year = CommonUtils.getYear();
			int i = 1;
			for (Map.Entry<String, Map<String, Double>> item : map.entrySet()) {
				if (item.getValue().entrySet() != null
						&& item.getValue().entrySet().size() != 0) {
					// 生成单据头
					BillMaster billMaster = null;
					if (!typeCode.equals("2001")) {
						billMaster = new BillMasterMateriel();
					} else {
						billMaster = new BillMasterProduct();
					}
					String joinStr = "";
					if (i < 10) {
						joinStr = "000" + i;
					} else {
						joinStr = "00" + i;
					}
					billMaster.setScmCoc(sMap.get(item.getKey()));
					billMaster.setBillNo(year + "0101" + joinStr);
					billMaster.setBillType(this.casDao
							.findBillTypeByCode(typeCode));
					billMaster.setCompany(CommonUtils.getCompany());
					billMaster.setIsValid(true);
					billMaster.setValidDate(beginDate);
					billMaster.setCreateDate(new Date());
					this.casDao.saveOrUpdate(billMaster);
					// 生成单据明细
					for (Map.Entry<String, Double> tmp : item.getValue()
							.entrySet()) {
						BillDetail b = null;
						if (!typeCode.equals("2001")) {
							b = new BillDetailMateriel();
						} else {
							b = new BillDetailProduct();
						}
						b.setBillMaster(billMaster);
						b.setPtPart(tmp.getKey());
						b.setBillNo(billMaster.getBillNo());

						FactoryAndFactualCustomsRalation f = fMap.get(b
								.getPtPart());
						if (f != null) {
							b.setPtName(f.getMateriel().getFactoryName());
							b.setPtSpec(f.getMateriel().getFactorySpec());
							b.setPtUnit(f.getMateriel().getCalUnit());
							b.setPtAmount(tmp.getValue() == null ? 0.0 : tmp
									.getValue());
							b
									.setNetWeight(f.getMateriel()
											.getPtNetWeight() != null ? f
											.getMateriel().getPtNetWeight()
											* b.getPtAmount() : 0.0);
							b.setPtPrice(f.getMateriel().getPtPrice());
							b.setMoney(b.getPtPrice() == null ? 0.0 : b
									.getPtAmount()
									* b.getPtPrice());
							b.setComplex(f.getStatCusNameRelationHsn()
									.getComplex());
							b.setHsName(f.getStatCusNameRelationHsn()
									.getCusName());
							b.setHsSpec(f.getStatCusNameRelationHsn()
									.getCusSpec());
							b.setHsUnit(f.getStatCusNameRelationHsn()
									.getCusUnit());
							b.setHsAmount((b.getPtAmount() == null ? 0.0 : b
									.getPtAmount())
									* (f.getUnitConvert() != null ? f
											.getUnitConvert() : 0.0));
						}
						this.casDao.saveOrUpdate(b);
					}
					i++;
				}
			}
		}
	}
}
