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
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.dao.CasLeftoverMaterielDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.dao.MaterialManageDao;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CasLeftoverMaterielLogic2 {
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


	/** 参数Map类 */
		// /////////////////////////////////////////////////////////////////////////////////////
		// 初始化边角料,报关成品折边角料 key = 报关名称+"/"+规格+"/"+单位名称 =直接出口+结转出口+返工复出-退厂返工
		// /////////////////////////////////////////////////////////////////////////////////////
		/** 出口成品损耗量映射 */
		Map<String, Double>							customsProductMapWaste			= new HashMap<String, Double>();


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
	public Map<String, Double> findCalLeftoverMateriel(
			Date beginDate, Date endDate, String taskId) {
		String companyId = CommonUtils.getCompany().getId();		
		try {	
			customsProductMapWaste			= new HashMap<String, Double>();
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
			//
			// 计算实际的报关折耗用
			//
//			if (projectTypeParam.getIsBcus() == true) {
				clientTipMessage = "计算边角料 初始化 BCUS 报关成品(直接出口+结转出口+返工复出-退厂返工)的合计BOM耗用... !!";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				this.initCustomsProductMapByBcus(beginDate, endDate, progressInfo);
//			}
//			if (projectTypeParam.getIsBcs() == true) {
				clientTipMessage = "计算边角料 初始化 BCS 报关成品(直接出口+结转出口+返工复出-退厂返工)的合计BOM耗用... !!";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				this.initCustomsProductMapByBCS(beginDate, endDate, progressInfo);
//			}
//			if (projectTypeParam.getIsDzsc() == true) {
				clientTipMessage = "计算边角料 初始化 DZSC 报关成品(直接出口+结转出口+返工复出-退厂返工)的合计BOM耗用... !!";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);
				this.initCustomsProductMapByDZSC(beginDate,endDate,progressInfo);
//			}	
			
		} catch (Exception ex) {
			ex.printStackTrace();
//			throw new RuntimeException(ex);
		}
		return customsProductMapWaste;
	}

		
	/**
	 * 初始化报关成品来自联网监管customsProductMap
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 */
	private void initCustomsProductMapByBcus(Date beginDate, Date endDate, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();

		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,v.id "
				+ " from CustomsDeclarationCommInfo a left join a.baseCustomsDeclaration "
				+ ",EmsHeadH2kVersion v left join v.emsHeadH2kExg exg where a.commSerialNo = exg.seqNum "
				+ " and a.version = v.version  "
//				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.isEmsCav = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?,?)  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
//				+ " and exg.emsHeadH2k.declareState=? "
//				+ " and exg.emsHeadH2k.historyState=?  "
				+ " group by a.baseCustomsDeclaration.impExpType,v.id "
				+ " order by v.id "; // order by 确保 bom id 只查询一次

		parameters.add(Boolean.valueOf(true));
//		parameters.add(Boolean.valueOf(true));
		parameters.add(beginDate);
		parameters.add(endDate);
		parameters.add(ImpExpType.DIRECT_EXPORT);//成品出口 成品资料 ---成品 
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);//转厂出口 成品资料 ---成品
		parameters.add(ImpExpType.REWORK_EXPORT);//返工复出 成品资料 ---成品
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);//退厂返工 成品资料 ---成品
		parameters.add(CommonUtils.getCompany().getId());
//		parameters.add(DeclareState.PROCESS_EXE);
//		parameters.add(false);

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
						// 成品单耗损 = 成品数量 * 单项用量 * 损耗
						//
						Double productMaterialWaste = tempHsAmount * unitUsed
								* waste;

						//
						// 已核销的报关单拆边角料 = 成品单耗损 (直接出口 + 转厂出口 + 返工复出 - 退厂返工) 
						//
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
	private void initCustomsProductMapByBCS(Date beginDate, Date endDate, ProgressInfo progressInfo) {

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
//				+ " and exg.contract.isCancel=? "
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
		parameters.add(DeclareState.CHANGING_CANCEL);
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
						
						
//						System.out.println("key  == "+key + " tempHsAmount = " + tempHsAmount + " unitUsed ="
//								+ unitUsed + " waste = "+waste);

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
	private void initCustomsProductMapByDZSC(Date beginDate, Date endDate, ProgressInfo progressInfo) {

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
//				+ " and exg.contract.isCancel=? "
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
		parameters.add(DeclareState.CHANGING_CANCEL);
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
						
						
//						System.out.println("key  == "+key + " tempHsAmount = " + tempHsAmount + " unitUsed ="
//								+ unitUsed + " waste = "+waste);

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
