/*
 * Created on 2004-7-29
 *
 * 
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
import com.bestway.bcus.cas.dao.CasLeftoverMaterielDao;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.LeftoverMaterielBalanceInfo;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.ProjectTypeParam;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CasLeftoverMaterielLogic {
	private static final Log		logger					= LogFactory
																	.getLog(ImgOrgUseInfoLogic.class);
	/** 海关帐Dao */
	private CasDao					casDao					= null;
	/** 边角料Dao */
	private CasLeftoverMaterielDao	casLeftoverMaterielDao	= null;
	/** 物料管理Dao */
	private MaterialManageDao		materialManageDao		= null;

	public CasLeftoverMaterielDao getCasLeftoverMaterielDao() {
		return casLeftoverMaterielDao;
	}

	public CasDao getCasDao() {
		return casDao;
	}

	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	public void setCasLeftoverMaterielDao(
			CasLeftoverMaterielDao casLeftoverMaterielDao) {
		this.casLeftoverMaterielDao = casLeftoverMaterielDao;
	}

	/** 过滤条件 */
	private String[]				filterStrs	= new String[] { "废", "边角料" };

	/** 为了每个公司只能有一个方法在同步运行,并且.. key = company id */
	private Map<String, Boolean>	runMap		= new Hashtable<String, Boolean>();

	/** 参数Map类,用于计算边角料时的  数据缓存 */
	static class ParameterMaps {

		// /////////////////////////////////////////////////////////////////////////////////////
		// 初始化边角料进出库,边角料征税内销 key = 报关名称+"/"+规格+"/"+单位名称
		// /////////////////////////////////////////////////////////////////////////////////////
		/** 2002 车间入库 */
		Map<String, Double>							billDetail2002MapWaste			= new HashMap<String, Double>();
		/** 车间返回 */
		Map<String, Double>							billDetail2103MapWaste			= new HashMap<String, Double>();
		/** key :报关名称+"/"+规格+"/"+单位名称  value:对应关系list,按料号排*/
		Map<String, List<FactoryAndFactualCustomsRalation>>	statCusNameRelationMtListMap	= new HashMap<String, List<FactoryAndFactualCustomsRalation>>();
//		/** key :真实的报关名称+"/"+规格+"/"+单位名称 */
//		Map<String, String>							statCusNameRelationHsMap		= new HashMap<String, String>();
//		/** key: ptNo 料号*/
//		Map<String, StatCusNameRelationMt>			statCusNameRelationMtValueMap	= new HashMap<String, StatCusNameRelationMt>();

		// /////////////////////////////////////////////////////////////////////////////////////
		// 初始化边角料,报关成品折边角料 key = 报关名称+"/"+规格+"/"+单位名称 =直接出口+结转出口+返工复出-退厂返工
		// /////////////////////////////////////////////////////////////////////////////////////
		/** 出口成品损耗量映射 */
		Map<String, Double>							customsProductMapWaste			= new HashMap<String, Double>();
		
//		/**6007边角料已打税未出库期初单*/
//		Map<String, Double>							billDetail6007Map		= new HashMap<String, Double>();
//		
//		/**边角料所有记帐年份 进量映射*/
//		Map<String, Double>							billDetailAllInMaps			= new HashMap<String, Double>();
//		
//		/**边角料所有记帐年份 出量映射*/
//		Map<String, Double>							billDetailAllOutMaps		= new HashMap<String, Double>();
	}

	/**
	 * 查找并计算边角料(按报关名称分时间段进行查询)
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param projectTypeParam
	 *            模块类型参数(是来自bcs还是bcus......)
	 * @return 边角料计算结果
	 */
	public List<LeftoverMaterielBalanceInfo> findCalLeftoverMateriel(
			Date beginDate, Date endDate, ProjectTypeParam projectTypeParam,
			boolean isCalcProductToWaste, String taskId) {
		String companyId = CommonUtils.getCompany().getId();
		Boolean isRun = runMap.get(companyId);
		if (isRun != null && isRun.booleanValue() == true) {
			throw new RuntimeException("计算边角料正在进行中请稍后再运行 ！！");
		}
		runMap.put(companyId, true);
		try {
			//
			// private parameter cache map instance
			//
			ParameterMaps parameterMaps = new ParameterMaps();
			//
			// get thread internal message
			//    	
			ProgressInfo progressInfo = ProcExeProgress.getInstance()
					.getProgressInfo(taskId);
			String clientTipMessage = "计算边角料 开始查询初始化大类与实际报关 对应关系... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

//			this.initStatCusNameRelationHsMap(parameterMaps);

			clientTipMessage = "计算边角料 开始查询海关商品大类对应... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			this.initStatCusNameRelationMap(parameterMaps);
			//
			// 是否计算边角料成品入库折耗用
			//
			if (isCalcProductToWaste == true) {
				clientTipMessage = "计算边角料 初始化 成品车间入存,车间返回,并计算其BOM耗用... !!";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				this.initBillDetailProductMap(beginDate, endDate,
						parameterMaps, progressInfo);
			}
			//
			// 计算实际的报关折耗用
			//
			if (projectTypeParam.getIsBcus() == true) {
				clientTipMessage = "计算边角料 初始化 BCUS 报关成品(直接出口+结转出口+返工复出-退厂返工)的合计BOM耗用... !!";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				this.initCustomsProductMapByBcus(beginDate, endDate,
						parameterMaps, progressInfo);
			}
			if (projectTypeParam.getIsBcs() == true) {
				clientTipMessage = "计算边角料 初始化 BCS 报关成品(直接出口+结转出口+返工复出-退厂返工)的合计BOM耗用... !!";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				this.initCustomsProductMapByBCS(beginDate, endDate,
						parameterMaps, progressInfo);
			}
			if (projectTypeParam.getIsDzsc() == true) {
				clientTipMessage = "计算边角料 初始化 DZSC 报关成品(直接出口+结转出口+返工复出-退厂返工)的合计BOM耗用... !!";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);
				this.initCustomsProductMapByDZSC(beginDate, endDate,
						parameterMaps, progressInfo);
			}

			clientTipMessage = "开始计算边角料 ... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			List<LeftoverMaterielBalanceInfo> reslutList = getResult(beginDate,
					endDate, isCalcProductToWaste, parameterMaps, progressInfo);

			//
			// 开始删除历史记录
			//
			clientTipMessage = "计算边角料 开始批量删除历史记录... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			this.casLeftoverMaterielDao.deleteLeftoverMaterielBalanceInfo();

			for (int i = 0, n = reslutList.size(); i < n; i++) {
				LeftoverMaterielBalanceInfo item = (LeftoverMaterielBalanceInfo) reslutList
						.get(i);
				item.setCompany(CommonUtils.getCompany());
				this.casDao.getHibernateTemplate().save(item);
			}
			return reslutList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			runMap.put(companyId, false);
		}
	}

	/**
	 * 获得边角料计算结果集
	 * 
	 * @return 边角料计算结果
	 */
	private List<LeftoverMaterielBalanceInfo> getResult(Date beginDate,
			Date endDate, boolean isCalcProductToWaste,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		List<LeftoverMaterielBalanceInfo> reslutList = new ArrayList<LeftoverMaterielBalanceInfo>();
		HashMap<String, LeftoverMaterielBalanceInfo> resultHash = new HashMap<String, LeftoverMaterielBalanceInfo>();

		List<Condition> conditions = new ArrayList<Condition>();

		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "6001", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "6003", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "6004", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "6101", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "6102", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "6104", ")"));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", beginDate, null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));

		String billDetailRemainMateriel = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.REMAIN_MATERIEL);

		String selects = "select sum(a.hsAmount),a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name,a.complex.code  ";
		String groupBy = "group by a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name,a.complex.code ";
		String leftOuter = " left join a.billMaster left join a.billMaster.billType left join a.hsUnit left join a.complex  ";

		// 边角料
		List listBillDetailMateriel = casDao.commonSearch(selects,
				billDetailRemainMateriel, conditions, groupBy, leftOuter);

		int size = listBillDetailMateriel.size();
		for (int i = 0; i < size; i++) {

			Object[] objs = (Object[]) listBillDetailMateriel.get(i);

			Double hsAmount = objs[0] == null ? 0.0 : (Double) objs[0];
			String typeCode = (String) objs[1];
			String hsName = objs[2] == null ? "" : (String) objs[2];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[4] == null ? "" : (String) objs[4];
			String complex = objs[5] == null ? "" : (String) objs[5];

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;

			LeftoverMaterielBalanceInfo item = null;

			if (resultHash.get(key) != null) {
				item = (LeftoverMaterielBalanceInfo) resultHash.get(key);
			} else {
				item = new LeftoverMaterielBalanceInfo();
				item.setName(hsName);
				item.setSpec(hsSpec);
				item.setUnitName(hsUnitName);
				item.setComplex(complex);
				resultHash.put(key, item);
			}

			if (typeCode.equals("6001") // 6001 边角料入库
					|| typeCode.equals("6004")// 6004 外发加工退回边角料
					|| typeCode.equals("6003")) {// 6003 边角料期初
				item.setF1(item.getF1() + hsAmount);
			} else if (typeCode.equals("6101") // 6101边角料出库
					|| typeCode.equals("6102") // 6102边角料退运出口
					|| typeCode.equals("6104")) {// 6104边角料征税内销
				item.setF2(item.getF2() + hsAmount);
			}
		}

		Iterator<LeftoverMaterielBalanceInfo> iterator = resultHash.values()
				.iterator();
		while (iterator.hasNext()) {
			LeftoverMaterielBalanceInfo item = iterator.next();
			//
			// ������ "��","�߽���" ������
			//            
			String hsName = this.filterLeftoverName(item.getName());
			String hsUnitName = item.getUnitName();
			String hsSpec = item.getSpec();
			//
			// key=名称+"/"+规格+"/"+单位 (这个key之前可能有..)
			//
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;

			item.setName(hsName);
			//
			// f4. 海关帐实际损耗量 = 成品入库单折损耗
			//
			if (isCalcProductToWaste == true) {
				Map<String, Double> tempMap = this
						.getProductConvertIntoMateriel(key, parameterMaps,
								progressInfo);
				item.setF4(item.getF4() + tempMap.get("F4"));
			}
			//
			// f5. 出口成品损耗量（手册）= 各合同的损耗量之和
			//
			item
					.setF5(item.getF5()
							+ (parameterMaps.customsProductMapWaste.get(key) == null ? 0.0
									: parameterMaps.customsProductMapWaste
											.get(key)));
			reslutList.add(item);
		}
		return reslutList;
	}

	/**
	 * 初始化成品hashMap
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 */
	private void initBillDetailProductMap(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		//
		// 提升性能
		//
		BillType billType2002 = this.casDao.findBillTypeByCode("2002"); // 2002
		// 车间入库
		// 2101
		// 2102结转出口Map
		BillType billType2103 = this.casDao.findBillTypeByCode("2103"); // 2103
		// 返回车间
		if (billType2002 == null) {
			logger.info("2002 这个类型是空");
		}
		if (billType2103 == null) {
			logger.info("2103 这个类型是空");
		}

		String selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id from BillDetailProduct a "
				+ ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = 0 )) "
				+ " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
				+ " and ( a.billMaster.billType = ? or a.billMaster.billType = ? ) and a.company.id = ? "
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id "; // order by 确保 bom id 只查询一次
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(beginDate);
		params.add(endDate);
		params.add(billType2002);
		params.add(billType2103);
		params.add(CommonUtils.getCompany().getId());

		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, params.toArray());
		String clientTipMessage = "CAS 开始查询所有成品 [ 2002 车间入库,2103 返回车间 ] 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		//
		// init
		//
		Map<String, Double> billDetail2002MapWaste = parameterMaps.billDetail2002MapWaste; // 2002
																							// 车间入库
		Map<String, Double> billDetail2103MapWaste = parameterMaps.billDetail2103MapWaste; // 2103
																							// 返加车间

		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
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
				clientTipMessage = "CAS 成品总记录 " + size + " 条 正在(折成料件用量)计算前 "
						+ (i + 1) + " 条记录";
				logger.info(clientTipMessage);

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
						logger.info("边角料计算 成品折料件 Bom 明细为空  !! == "
								+ tempVersionId);
						continue;
					}

					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						//
						// 料件料号为 key
						//
						String ptNoKey = (String) bomObjs[0];
						//
						// 成品单
						//
						Double unitUsed = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];
						//
						// 损耗
						//
						Double waste = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];
						//
						// 成品单耗损
						//
						Double productMaterialWaste = ptAmount * unitUsed
								* waste;

						// System.out.println("productMaterialWaste == "
						// + productMaterialWaste);

						if (typeCode.equals("2002")) {// 车间入库
							if (billDetail2002MapWaste.get(ptNoKey) == null) {
								billDetail2002MapWaste.put(ptNoKey,
										productMaterialWaste);
							} else {
								Double tempAmount = billDetail2002MapWaste
										.get(ptNoKey);
								tempAmount += productMaterialWaste;
								billDetail2002MapWaste.put(ptNoKey, tempAmount);
							}
						} else if (typeCode.equals("2103")) {// 车间返回
							if (billDetail2103MapWaste.get(ptNoKey) == null) {
								billDetail2103MapWaste.put(ptNoKey,
										productMaterialWaste);
							} else {
								Double tempAmount = billDetail2103MapWaste
										.get(ptNoKey);
								tempAmount += productMaterialWaste;
								billDetail2103MapWaste.put(ptNoKey, tempAmount);
							}
						}
					}
				}
				tempList.clear();
			}
		}
	}

	/**
	 * 成品入库单折损耗(车间入库-车间返回)
	 * 
	 * @param key
	 *            取得唯一键值
	 * @return 成品入库单折损耗
	 */
	private Map<String, Double> getProductConvertIntoMateriel(String key,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		Map<String, Double> map = new HashMap<String, Double>();
		//
		// 根据报关的名称 获得对应的工厂料件列表
		//
		List<FactoryAndFactualCustomsRalation> mtList = parameterMaps.statCusNameRelationMtListMap
				.get(key);
		if (mtList == null) {
			String clientTipMessage = "Jbcus 海关帐大类对应没有这样的报关名称 == " + key;
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);
			map.put("F4", 0.0);
			return map;
		}

		for (int i = 0, n = mtList.size(); i < n; i++) {
			FactoryAndFactualCustomsRalation mt = mtList.get(i);
			String ptNoKey = mt.getMateriel().getPtNo(); // 料件料号
			Double unitConvert = mt.getUnitConvert() == null ? 1.0 : mt
					.getUnitConvert(); // 折算系数
			//
			// 损耗 ［（车间入库－返回车间）的单据数量 ＊对应版本的单项用量＊损耗率＊料件折算］
			//
			if (!map.containsKey("F4")) {
				//
				// 获得该成品料号和版本对应的
				// 2002 车间入库
				// - 2103 返回车间
				//                
				Double ptAmount2002 = parameterMaps.billDetail2002MapWaste
						.get(ptNoKey);
				Double ptAmount2103 = parameterMaps.billDetail2103MapWaste
						.get(ptNoKey);
				Double ptAmount = (ptAmount2002 == null ? 0 : ptAmount2002)
						- (ptAmount2103 == null ? 0 : ptAmount2103);

				map.put("F4", (ptAmount * unitConvert));
			} else {
				Double ptAmount2002 = parameterMaps.billDetail2002MapWaste
						.get(ptNoKey);
				Double ptAmount2103 = parameterMaps.billDetail2103MapWaste
						.get(ptNoKey);
				Double ptAmount = (ptAmount2002 == null ? 0 : ptAmount2002)
						- (ptAmount2103 == null ? 0 : ptAmount2103);

				map.put("F4", map.get("F4") + (ptAmount * unitConvert));
			}
		}
		return map;
	}

	/**
	 * 初始化 statCusNameRelationMtListMap 和 statCusNameRelationMtValueMap 对应表
	 */
	private void initStatCusNameRelationMap(ParameterMaps parameterMaps) {
		Map<String, List<FactoryAndFactualCustomsRalation>> statCusNameRelationMtListMap = parameterMaps.statCusNameRelationMtListMap;
//		Map<String, StatCusNameRelationMt> statCusNameRelationMtValueMap = parameterMaps.statCusNameRelationMtValueMap;

		List<FactoryAndFactualCustomsRalation> listExistStatCusNameRelationMt = this.casDao
				.findStatCusNameRelationMt(MaterielType.MATERIEL);
		for (int i = 0; i < listExistStatCusNameRelationMt.size(); i++) {
			FactoryAndFactualCustomsRalation mt = listExistStatCusNameRelationMt.get(i);
			Materiel materiel = mt.getMateriel();
			StatCusNameRelationHsn sr = mt.getStatCusNameRelationHsn();
			//
			// keyList = 报关名称+"/"+规格+"/"+单位名称
			//
			String hsName = sr.getCusName() == null ? "" : sr.getCusName();
			String hsSpec = sr.getCusSpec() == null ? "" : sr.getCusSpec();
			String unitName = sr.getCusUnit() == null ? "" : sr.getCusUnit()
					.getName();
			unitName = unitName == null ? "" : unitName;
			String tenKey = hsName + "/" + hsSpec + "/" + unitName;

			// System.out.println("tem key == " + tenKey);

			if (statCusNameRelationMtListMap.get(tenKey) == null) {
				List<FactoryAndFactualCustomsRalation> tempList = new ArrayList<FactoryAndFactualCustomsRalation>();
				tempList.add(mt);
				statCusNameRelationMtListMap.put(tenKey, tempList);
			} else {
				List<FactoryAndFactualCustomsRalation> tempList = statCusNameRelationMtListMap
						.get(tenKey);
				tempList.add(mt);
			}
			//
			// key = ptno 因为工厂料号不可能为空
			//
//			String key = materiel.getPtNo();
//			if (statCusNameRelationMtValueMap.get(key) == null) {
//				statCusNameRelationMtValueMap.put(key, mt);
//			}
		}
	}

	/**
	 * 初始化大类和实际的对应的信息用于一对多的结转计算
	 * 
	 */
//	private void initStatCusNameRelationHsMap(ParameterMaps parameterMaps) {
//		Map<String, String> statCusNameRelationHsMap = parameterMaps.statCusNameRelationHsMap;
//
//		List<StatCusNameRelationHsn> listExistStatCusNameRelationHsn = this.casDao
//				.findStatCusNameRelationHsn(MaterielType.MATERIEL);
//		for (int i = 0, n = listExistStatCusNameRelationHsn.size(); i < n; i++) {
//			StatCusNameRelationHsn hsn = listExistStatCusNameRelationHsn.get(i);
//			StatCusNameRelation sr = hsn.getStatCusNameRelation();
//
//			String hsName = hsn.getCusName() == null ? "" : hsn.getCusName();
//			String hsSpec = hsn.getCusSpec() == null ? "" : hsn.getCusSpec();
//			String unitName = hsn.getCusUnit() == null ? "" : hsn.getCusUnit()
//					.getName();
//			unitName = unitName == null ? "" : unitName;
//			String key = hsName + "/" + hsSpec + "/" + unitName;
//			//
//			// value = 报关名称+"/"+规格+"/"+单位名称
//			//
//			String tenHsName = sr.getCusName() == null ? "" : sr.getCusName();
//			String tenHsSpec = sr.getCusSpec() == null ? "" : sr.getCusSpec();
//			String tenUnitName = sr.getCusUnit() == null ? "" : sr.getCusUnit()
//					.getName();
//			tenUnitName = tenUnitName == null ? "" : tenUnitName;
//			String value = tenHsName + "/" + tenHsSpec + "/" + tenUnitName;
//
//			if (statCusNameRelationHsMap.get(key) == null) {
//				statCusNameRelationHsMap.put(key, value);
//			}
//		}
//	}

	/**
	 * 过滤是 "废","边角料" 报关大类名字
	 * 
	 * @param hsName
	 *            报关名称
	 * @return 根据报关名称过滤边角料名称
	 */
	private String filterLeftoverName(String hsName) {
		for (int j = 0; j < filterStrs.length; j++) {
			String repalceStr = filterStrs[j];
			hsName = hsName.replaceFirst(repalceStr, "").trim();
		}
		return hsName;
	}

	/**
	 * 初始化报关成品来自联网监管customsProductMap
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 */
	private void initCustomsProductMapByBcus(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();

		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,v.id "
				+ " from CustomsDeclarationCommInfo a left join a.baseCustomsDeclaration "
				+ ",EmsHeadH2kVersion v left join v.emsHeadH2kExg exg where a.commSerialNo = exg.seqNum "
				+ " and a.version = v.version  "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?,?)  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and exg.emsHeadH2k.declareState=? "
				+ " and exg.emsHeadH2k.historyState=?  "
				+ " group by a.baseCustomsDeclaration.impExpType,v.id "
				+ " order by v.id "; // order by 确保 bom id 只查询一次

		parameters.add(Boolean.valueOf(true));
		parameters.add(beginDate);
		parameters.add(endDate);
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(false);

		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "边角料计算 开始查询所有 [联网监管] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		//
		// init 直接出口+结转出口+返工复出-退厂返工
		//
		Map<String, Double> customsProductMapWaste = parameterMaps.customsProductMapWaste; // 报关成品

		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
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
				clientTipMessage = "边角料计算 报关成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);

				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(versionIdMap.values());
				List emsHeadH2kBomList = this.casLeftoverMaterielDao
						.findEmsHeadH2kBomDetail(versionIdList);
				versionIdList.clear();
				versionIdMap.clear();

				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

				if (emsHeadH2kBomList.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}

				//
				// 获取成品版本号对应的料件明细
				//
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = emsHeadH2kBomList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) emsHeadH2kBomList.get(j);
					String tempVersionId = (String) bomObjs[5];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(bomObjs);
					}
				}

				//
				// 循环刚才临时保存的成品数据 --> 并拆料件
				//
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
					Integer typeCode = (Integer) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // version guid
					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);

					if (tempObjes == null) {
						logger.info("边角料计算 Bcus Bom 明细为空  !! == "
								+ tempVersionId);
						continue;
					}

					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];
						//
						// key=名称+"/"+规格+"/"+单位
						//                    
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;
						// System.out.println("key 1 == "+key);
						//
						// 转换成大类的对应(用于一对多的方法)
						//
//						String tempKey = parameterMaps.statCusNameRelationHsMap
//								.get(key);
//						if (tempKey != null) {
//							key = tempKey;
//						}

						// System.out.println("key 2 == "+key);
						//
						// 损耗
						//
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];
						//
						// 损耗在电子帐册中是以百分之多小来存储的整数
						//
						waste = waste * 0.01;
						//
						// 单耗
						//
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];
						//
						// 单项用量 = 单耗 / (1-损耗);
						//
						Double unitUsed = unitWear / (1 - waste);
						//
						// 成品单耗损
						//
						Double productMaterialWaste = tempHsAmount * unitUsed
								* waste;

						if (typeCode.intValue() == ImpExpType.DIRECT_EXPORT
								|| typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT
								|| typeCode.intValue() == ImpExpType.REWORK_EXPORT) {// 直接出口
							// ||
							// //转厂出口
							// ||
							// 返工复出
							if (customsProductMapWaste.get(key) == null) {
								customsProductMapWaste.put(key,
										productMaterialWaste);
							} else {
								customsProductMapWaste.put(key,
										customsProductMapWaste.get(key)
												+ productMaterialWaste);
							}
						} else if (typeCode.intValue() == ImpExpType.BACK_FACTORY_REWORK) {// 退厂返工
							productMaterialWaste = -productMaterialWaste;
							if (customsProductMapWaste.get(key) == null) {
								customsProductMapWaste.put(key,
										productMaterialWaste);
							} else {
								customsProductMapWaste.put(key,
										customsProductMapWaste.get(key)
												+ productMaterialWaste);
							}
						}
					}

				}
				tempList.clear();

			}
		}
	}

	/**
	 * 初始化报关成品来自纸质手册customsProductMap
	 * 
	 * @param beginDate
	 * @param endDate
	 */
	private void initCustomsProductMapByBCS(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();

		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,exg.id "
				+ " from BcsCustomsDeclarationCommInfo a left join a.baseCustomsDeclaration "
				+ ",ContractBom v left join v.contractExg exg left join exg.contract "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.contract.emsNo "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?,?)  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and exg.contract.declareState=? "
				+ " and exg.contract.isCancel=? "
				+ " group by a.baseCustomsDeclaration.impExpType,exg.id "
				+ " order by exg.id ";// order by 确保 exg id 只查询一次

		parameters.add(Boolean.valueOf(true));
		parameters.add(beginDate);
		parameters.add(endDate);
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(false);

		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "边角料计算 开始查询所有 [无纸通关] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		//
		// init 直接出口+结转出口+返工复出-退厂返工
		//
		Map<String, Double> customsProductMapWaste = parameterMaps.customsProductMapWaste; // 报关成品

		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
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
				clientTipMessage = "边角料计算 报关成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);

				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(versionIdMap.values());
				List emsHeadH2kBomList = this.casLeftoverMaterielDao
						.findContractBomDetail(versionIdList);
				versionIdList.clear();
				versionIdMap.clear();

				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

				if (emsHeadH2kBomList.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}

				//
				// 获取成品版本号对应的料件明细
				//
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = emsHeadH2kBomList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) emsHeadH2kBomList.get(j);
					String tempVersionId = (String) bomObjs[5];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(bomObjs);
					}
				}

				//
				// 循环刚才临时保存的成品数据 --> 并拆料件
				//
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
					Integer typeCode = (Integer) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // version guid
					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);

					if (tempObjes == null) {
						logger.info("边角料计算 Bcs Bom 明细为空  !! == "
								+ tempVersionId);
						continue;
					}

					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];
						//
						// key=名称+"/"+规格+"/"+单位
						//                    
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;
						// System.out.println("key 1 == "+key);
						//
						// 转换成大类的对应(用于一对多的方法)
						//					
//						String tempKey = parameterMaps.statCusNameRelationHsMap
//								.get(key);
//						if (tempKey != null) {
//							key = tempKey;
//						}
						// System.out.println("key 2 == "+key);
						//
						// 损耗
						//
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];
						waste = waste * 0.01;
						//
						// 单耗
						//
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];
						//
						// 单项用量 = 单耗 / (1-损耗);
						//
						Double unitUsed = unitWear / (1 - waste);
						//
						// 成品单耗损
						//
						Double productMaterialWaste = tempHsAmount * unitUsed
								* waste;

						if (typeCode.intValue() == ImpExpType.DIRECT_EXPORT
								|| typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT
								|| typeCode.intValue() == ImpExpType.REWORK_EXPORT) {// 直接出口
							// ||
							// //转厂出口
							// ||
							// 返工复出
							if (customsProductMapWaste.get(key) == null) {
								customsProductMapWaste.put(key,
										productMaterialWaste);
							} else {
								customsProductMapWaste.put(key,
										customsProductMapWaste.get(key)
												+ productMaterialWaste);
							}
						} else if (typeCode.intValue() == ImpExpType.BACK_FACTORY_REWORK) {// 退厂返工
							productMaterialWaste = -productMaterialWaste;
							if (customsProductMapWaste.get(key) == null) {
								customsProductMapWaste.put(key,
										productMaterialWaste);
							} else {
								customsProductMapWaste.put(key,
										customsProductMapWaste.get(key)
												+ productMaterialWaste);
							}
						}
					}
				}
				tempList.clear();
			}
		}
	}

	
	
	/**
	 * 初始化报关成品来DZSC customsProductMap
	 * 
	 * @param beginDate
	 * @param endDate
	 */
	private void initCustomsProductMapByDZSC(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();
		
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,exg.id "
			+ " from DzscCustomsDeclarationCommInfo a left join a.baseCustomsDeclaration "
			+ ",DzscEmsBomBill v left join v.dzscEmsExgBill exg left join exg.dzscEmsPorHead "
			+ " where a.commSerialNo = exg.seqNum "
			+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.dzscEmsPorHead.emsNo "
			+ " and a.baseCustomsDeclaration.effective = ? "
			+ " and a.baseCustomsDeclaration.declarationDate >= ? "
			+ " and a.baseCustomsDeclaration.declarationDate <= ? "
			+ " and a.baseCustomsDeclaration.impExpType in (?,?,?,?)  "
			+ " and a.baseCustomsDeclaration.company.id = ? "
			+ " and exg.dzscEmsPorHead.declareState=? "
//			+ " and exg.contract.isCancel=? "
			+ " group by a.baseCustomsDeclaration.impExpType,exg.id "
			+ " order by exg.id ";// order by 确保 exg id 只查询一次
		parameters.add(Boolean.valueOf(true));
		parameters.add(beginDate);
		parameters.add(endDate);
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);
//		parameters.add(false);
//		parameters.add(true);
	
		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "边角料计算 开始查询所有 [无纸通关] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		//
		// init 直接出口+结转出口+返工复出-退厂返工
		//
		Map<String, Double> customsProductMapWaste = parameterMaps.customsProductMapWaste; // 报关成品

		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
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
				clientTipMessage = "边角料计算 报关成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);

				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(versionIdMap.values());
				List emsHeadH2kBomList = this.casLeftoverMaterielDao
						.findDzscEmsBomBill(versionIdList);
				versionIdList.clear();
				versionIdMap.clear();

				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

				if (emsHeadH2kBomList.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}

				//
				// 获取成品版本号对应的料件明细
				//
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = emsHeadH2kBomList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) emsHeadH2kBomList.get(j);
					String tempVersionId = (String) bomObjs[5];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(bomObjs);
					}
				}

				//
				// 循环刚才临时保存的成品数据 --> 并拆料件
				//
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
					Integer typeCode = (Integer) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // version guid
					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);

					if (tempObjes == null) {
						logger.info("边角料计算 Bcs Bom 明细为空  !! == "
								+ tempVersionId);
						continue;
					}

					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];
						//
						// key=名称+"/"+规格+"/"+单位
						//                    
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;
						// System.out.println("key 1 == "+key);
						//
						// 转换成大类的对应(用于一对多的方法)
						//					
//						String tempKey = parameterMaps.statCusNameRelationHsMap
//								.get(key);
//						if (tempKey != null) {
//							key = tempKey;
//						}
						// System.out.println("key 2 == "+key);
						//
						// 损耗
						//
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];
						waste = waste * 0.01;
						//
						// 单耗
						//
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];
						//
						// 单项用量 = 单耗 / (1-损耗);
						//
						Double unitUsed = unitWear / (1 - waste);
						//
						// 成品单耗损
						//
						Double productMaterialWaste = tempHsAmount * unitUsed
								* waste;

						if (typeCode.intValue() == ImpExpType.DIRECT_EXPORT
								|| typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT
								|| typeCode.intValue() == ImpExpType.REWORK_EXPORT) {// 直接出口
							// ||
							// //转厂出口
							// ||
							// 返工复出
							if (customsProductMapWaste.get(key) == null) {
								customsProductMapWaste.put(key,
										productMaterialWaste);
							} else {
								customsProductMapWaste.put(key,
										customsProductMapWaste.get(key)
												+ productMaterialWaste);
							}
						} else if (typeCode.intValue() == ImpExpType.BACK_FACTORY_REWORK) {// 退厂返工
							productMaterialWaste = -productMaterialWaste;
							if (customsProductMapWaste.get(key) == null) {
								customsProductMapWaste.put(key,
										productMaterialWaste);
							} else {
								customsProductMapWaste.put(key,
										customsProductMapWaste.get(key)
												+ productMaterialWaste);
							}
						}
					}
				}
				tempList.clear();
			}
		}
	}

	
}
