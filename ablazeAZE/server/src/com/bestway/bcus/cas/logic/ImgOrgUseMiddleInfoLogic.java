package com.bestway.bcus.cas.logic;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.ImgOrgUseInfoBase;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.TempImgOrgUseInfo;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.Materiel;
/***
 * 海关帐中间表计算过程 
 * @author luosheng  checked 2008-10-18
 *
 */
@SuppressWarnings("unchecked")
public class ImgOrgUseMiddleInfoLogic {
	private static final Log	logger				= LogFactory
															.getLog(ImgOrgUseMiddleInfoLogic.class);
	/** 海关帐Dao */
	private CasDao				casDao				= null;
	/** 常用报关代码设置Dao */
	private CommonBaseCodeDao	commonBaseCodeDao	= null;
	/** 物料管理Dao */
	private MaterialManageDao	materialManageDao	= null;
	/**获得海关帐Dao*/
	public CasDao getCasDao() {
		return casDao;
	}
	/**获得物料管理Dao*/
	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}
	/**获得公共代码Dao*/
	public CommonBaseCodeDao getCommonBaseCodeDao() {
		return commonBaseCodeDao;
	}
	/**set公共代码Dao*/
	public void setCommonBaseCodeDao(CommonBaseCodeDao commonBaseCodeDao) {
		this.commonBaseCodeDao = commonBaseCodeDao;
	}
	/**set物料管理Dao*/
	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}
	/**set海关帐Dao*/
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	/** 参数Map类 */
	static class ParameterMaps {
		// ////////////////////////////////////////////////////////
		// 为了不传输太多参数在方法中而进行的类变量定义 findImgOrgUseInfos
		// 传递参数将 ParameterMaps 对象为参数传递来访问这些变量
		// ////////////////////////////////////////////////////////
		/** key :报关名称+" / "+规格+" / "+单位名称 */
		Map<String, List<FactoryAndFactualCustomsRalation>>	statCusNameRelationMtListMap	= new HashMap<String, List<FactoryAndFactualCustomsRalation>>();
		/** key :真实的 报关名称+" / "+规格+" / "+单位名称 */
//		Map<String, String>							statCusNameRelationHsMap		= new HashMap<String, String>();
		/** list */
		List<FactoryAndFactualCustomsRalation>					statCusNameRelationMtList		= new ArrayList<FactoryAndFactualCustomsRalation>();
		/** 折算系数 */
		Map<String, FactoryAndFactualCustomsRalation>			statCusNameRelationMtValueMap	= new HashMap<String, FactoryAndFactualCustomsRalation>();

		// /////////////////////////////////////////////////////////
		// 所有成品单据 用于 生成对应的料件之用 key = ptNo 对应的折算数量
		// /////////////////////////////////////////////////////////
		/** 2001成品起初单 */
		Map<String, Double>							billDetail2001Map				= new HashMap<String, Double>();
		/** 2002车间入库 */
		Map<String, Double>							billDetail2002Map				= new HashMap<String, Double>();
		/** 2003退厂返工 */
		Map<String, Double>							billDetail2003Map				= new HashMap<String, Double>();
		/** 2004成品盘盈单 */
		Map<String, Double>							billDetail2004Map				= new HashMap<String, Double>();
		/** 2005成品转仓入库 */
		Map<String, Double>							billDetail2005Map				= new HashMap<String, Double>();
		/** 2007受托加工车间入库 */
		Map<String, Double>							billDetail2007Map				= new HashMap<String, Double>();
		/** 2008其他成品退货单 */
		Map<String, Double>							billDetail2008Map				= new HashMap<String, Double>();
		/** 2009结转成品退货单 */
		Map<String, Double>							billDetail2009Map				= new HashMap<String, Double>();
		/** 2010受托加工退回成品 */
		Map<String, Double>							billDetail2010Map				= new HashMap<String, Double>();
		/** 2010已交货未结转期初单 */
		Map<String, Double>							billDetail2011Map				= new HashMap<String, Double>();
		/** 2010已交货未结转期初单 */
		Map<String, Double>							billDetail2012Map				= new HashMap<String, Double>();

		/** 2101直接出口 */
		Map<String, Double>							billDetail2101Map				= new HashMap<String, Double>();
		/** 2102结转出口 */
		Map<String, Double>							billDetail2102Map				= new HashMap<String, Double>();
		/** 2103返回车间 */
		Map<String, Double>							billDetail2103Map				= new HashMap<String, Double>();
		/** 2103返工复出 */
		Map<String, Double>							billDetail2104Map				= new HashMap<String, Double>();
		/** 2103成品盘亏单 */
		Map<String, Double>							billDetail2105Map				= new HashMap<String, Double>();
		/** 2106海关批准内销 */
		Map<String, Double>							billDetail2106Map				= new HashMap<String, Double>();
		/** 2107其他内销 */
		Map<String, Double>							billDetail2107Map				= new HashMap<String, Double>();
		/** 2108其他处理 */
		Map<String, Double>							billDetail2108Map				= new HashMap<String, Double>();
		/** 2109成品转仓出库 */
		Map<String, Double>							billDetail2109Map				= new HashMap<String, Double>();
		/** 2110受托加工返回 */
		Map<String, Double>							billDetail2110Map				= new HashMap<String, Double>();
		/** 4001半成品入库 */
		Map<String, Double>							billDetail4001Map				= new HashMap<String, Double>();
		/** 4002半成品盘盈单 */
		Map<String, Double>							billDetail4002Map				= new HashMap<String, Double>();
		/** 4003外发加工入库(半成品) */
		Map<String, Double>							billDetail4003Map				= new HashMap<String, Double>();
		/** 4009半成品期初单 */
		Map<String, Double>							billDetail4009Map				= new HashMap<String, Double>();
		/** 4101半成品出库 */
		Map<String, Double>							billDetail4101Map				= new HashMap<String, Double>();
		/** 4102半成品盘亏单 */
		Map<String, Double>							billDetail4102Map				= new HashMap<String, Double>();
		/** 4103委外加工出库 */
		Map<String, Double>							billDetail4103Map				= new HashMap<String, Double>();
		/** 4104外发加工返修成品 */
		Map<String, Double>							billDetail4104Map				= new HashMap<String, Double>();

		// ///////////////////////////
		// 计算 20 栏 损耗：
		// ///////////////////////////
		/** 2002车间入库映射耗用 */
		Map<String, Double>							billDetail2002MapWaste			= new HashMap<String, Double>();
		/** 4003外发加工入库耗用 */
		Map<String, Double>							billDetail4003MapWaste			= new HashMap<String, Double>();
		/** 2007受托加工车间入库映射耗用 */
		Map<String, Double>							billDetail2007MapWaste			= new HashMap<String, Double>();
		/** 2103返回车间映射耗用 */
		Map<String, Double>							billDetail2103MapWaste			= new HashMap<String, Double>();
		/** 2110受托加工返回映射耗用 */
		Map<String, Double>							billDetail2110MapWaste			= new HashMap<String, Double>();
		/** 4104 外发加工返修半成品 */
		Map<String, Double>							billDetail4104MapWaste			= new HashMap<String, Double>();
		/** 4105 外发加工领料 */
		Map<String, Double>							billDetail4105MapWaste			= new HashMap<String, Double>();
	}

	/**
	 * 获得海关帐的计算中间信息
	 * 
	 * @param imgOrgUseInfo
	 * @return
	 */
	public List<TempImgOrgUseInfo> findTempImgOrgUseInfo(Date beginDate,
			Date endDate, ImgOrgUseInfoBase imgOrgUseInfo, String taskId) {
		//
		// private parameter cache object instance
		//
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
		// get thread internal message
		//    	
		ProgressInfo progressInfo = ProcExeProgress.getInstance()
				.getProgressInfo(taskId);
		String clientTipMessage = "CAS 开始查询初始化工厂与实际报关 对应关系... !!";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
//		this.initStatCusNameRelationHsMap(statCusNameRelation, parameterMaps);

		clientTipMessage = "CAS 开始查询对应关系对应... !!";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		this.initStatCusNameRelationMap(imgOrgUseInfo, parameterMaps);

		clientTipMessage = "CAS 开始查询所有成品... !!";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		this.initBillDetailProductMap(beginDate, endDate, parameterMaps,
				progressInfo, imgOrgUseInfo);

		//
		// 分页查询
		//
		List<Object[]> listBillDetailMateriel = new ArrayList<Object[]>();
		clientTipMessage = "CAS 开始查询所有料件... !!";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		
		long beginTime = System.currentTimeMillis();
		listBillDetailMateriel = this.findBillDetailMaterielbyPtNo(null, beginDate, endDate, imgOrgUseInfo);
		logger.info("CAS 开始查询所有料件的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

		clientTipMessage = "CAS 开始最后的计算请等待.... ";
		progressInfo.setHintMessage(clientTipMessage);
		logger.info(clientTipMessage);
		List<FirstSumInfo> firstSumInfoList = new ArrayList<FirstSumInfo>();
		for (int i = 0; i < listBillDetailMateriel.size(); i++) {
			Object[] values = (Object[]) listBillDetailMateriel.get(i);
			FirstSumInfo firstSumInfo = new FirstSumInfo(values);
			firstSumInfoList.add(firstSumInfo);
		}

		//
		// 将List进行解析对不同的类型返回List
		//
		List<TempImgOrgUseInfo> resultList = getResultList(firstSumInfoList,
				parameterMaps);
		logger.info("resultList size = " + resultList.size());
		//
		// 加入汇总记录
		//
		TempImgOrgUseInfo nullRow = new TempImgOrgUseInfo(true);
		TempImgOrgUseInfo totalTemp = makeTotalTempImgExgUseInfo(resultList);
		resultList.add(nullRow);
		resultList.add(totalTemp);
		return resultList;
	}

	/**
	 * 分料号来统计进出口数量
	 * 
	 * @param ptNoList
	 * @return
	 */
	public List<Object[]> findBillDetailMaterielbyPtNo(List<String> ptNoList,
			Date beginDate, Date endDate, ImgOrgUseInfoBase imgOrgUseInfo) {
		String billDetailMateriel = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);
		StringBuilder hql = new StringBuilder(10000);
		hql.append(	"select ")
			.append("	sum(a.hsAmount),sum(a.money),a.billMaster.billType.code,a.ptName,a.ptSpec, a.ptUnit.name,a.ptPart ")
			.append("from " + billDetailMateriel + " a ")
			.append("	left join a.billMaster left join a.ptUnit  left join a.billMaster.billType ")
			.append("where ")
			.append("	a.billMaster.isValid=? and a.hsName=? and a.hsSpec=? and a.hsUnit.name=? and a.billMaster.validDate>=? and a.billMaster.validDate <=? ")
			;
		
		hql
			.append("group by a.billMaster.billType.code,a.ptName,a.ptSpec, a.ptUnit.name,a.ptPart ");
		
		System.out.println(hql.length());
		List listBillDetailMateriel = casDao.commonSearch(hql.toString(),
				new Object[] { true, imgOrgUseInfo.getPtName(),
						imgOrgUseInfo.getPtSpec(),
						imgOrgUseInfo.getPtUnitName(), beginDate, endDate });
		return listBillDetailMateriel;
	}

	/**
	 * 统计加工贸易原材料来源与使用情况
	 * 
	 * @param firstSumInfoList
	 *            最初统计信息
	 * @return 原材料来源与使用情况
	 */
	public List<TempImgOrgUseInfo> getResultList(
			List<FirstSumInfo> firstSumInfoList, ParameterMaps parameterMaps) {
		List<TempImgOrgUseInfo> reslutList = new ArrayList<TempImgOrgUseInfo>();
		HashMap<String, TempImgOrgUseInfo> resultHash = new HashMap<String, TempImgOrgUseInfo>();
		int size = firstSumInfoList.size();
		for (int i = 0; i < size; i++) {
			FirstSumInfo firstSumInfo = firstSumInfoList.get(i);
			TempImgOrgUseInfo item = null;
			Double hsAmount = firstSumInfo.getHsAmount() == null ? 0.0
					: firstSumInfo.getHsAmount();
			String typeCode = firstSumInfo.getTypeCode();
			String hsName = firstSumInfo.getHsName() == null ? ""
					: firstSumInfo.getHsName();
			String hsUnitName = firstSumInfo.getHsUnitName() == null ? ""
					: firstSumInfo.getHsUnitName();
			String hsSpec = firstSumInfo.getHsSpec() == null ? ""
					: firstSumInfo.getHsSpec();
//			Double money = firstSumInfo.getMoney() == null ? 0.0 : firstSumInfo
//					.getMoney();
			String key = firstSumInfo.getPtPart();
			
			
			//
			// key=料号
			//
			if (resultHash.get(key) != null) {
				item = (TempImgOrgUseInfo) resultHash.get(key);
			} else {
				item = new TempImgOrgUseInfo();
				item.setPtName(hsName);
				item.setPtSpec(hsSpec);
				item.setPtUnitName(hsUnitName);
				item.setPtPart(key);
				resultHash.put(key, item);
			}

			if (typeCode.equals("1001")) {
				item.setF1001(item.getF1001() + hsAmount);
			} else if (typeCode.equals("1002")) {
				item.setF1002(item.getF1002() + hsAmount);
			} else if (typeCode.equals("1003")) {
				item.setF1003(item.getF1003() + hsAmount);
			} else if (typeCode.equals("1004")) {
				item.setF1004(item.getF1004() + hsAmount);
			} else if (typeCode.equals("1005")) {
				item.setF1005(item.getF1005() + hsAmount);
			} else if (typeCode.equals("1006")) {
				item.setF1006(item.getF1006() + hsAmount);
			} else if (typeCode.equals("1007")) {
				item.setF1007(item.getF1007() + hsAmount);
			} else if (typeCode.equals("1008")) {
				item.setF1008(item.getF1008() + hsAmount);
			} else if (typeCode.equals("1009")) {
				item.setF1009(item.getF1009() + hsAmount);
			} else if (typeCode.equals("1010")) {
				item.setF1010(item.getF1010() + hsAmount);
			} else if (typeCode.equals("1011")) {
				item.setF1011(item.getF1011() + hsAmount);
			} else if (typeCode.equals("1012")) {
				item.setF1012(item.getF1012() + hsAmount);
			} else if (typeCode.equals("1013")) {
				item.setF1013(item.getF1013() + hsAmount);
			} else if (typeCode.equals("1014")) {
				item.setF1014(item.getF1014() + hsAmount);
			} else if (typeCode.equals("1015")) {
				item.setF1015(item.getF1015() + hsAmount);
			} else if (typeCode.equals("1016")) {
				item.setF1016(item.getF1016() + hsAmount);
			} else if (typeCode.equals("1101")) {
				item.setF1101(item.getF1101() + hsAmount);
			} else if (typeCode.equals("1102")) {
				item.setF1102(item.getF1102() + hsAmount);
			} else if (typeCode.equals("1103")) {
				item.setF1103(item.getF1103() + hsAmount);
			} else if (typeCode.equals("1104")) {
				item.setF1104(item.getF1104() + hsAmount);
			} else if (typeCode.equals("1105")) {
				item.setF1105(item.getF1105() + hsAmount);
			} else if (typeCode.equals("1106")) {
				item.setF1106(item.getF1106() + hsAmount);
			} else if (typeCode.equals("1107")) {
				item.setF1107(item.getF1107() + hsAmount);
			} else if (typeCode.equals("1108")) {
				item.setF1108(item.getF1108() + hsAmount);
			} else if (typeCode.equals("1109")) {
				item.setF1109(item.getF1109() + hsAmount);
			} else if (typeCode.equals("1110")) {
				item.setF1110(item.getF1110() + hsAmount);
			} else if (typeCode.equals("1111")) {
				item.setF1111(item.getF1111() + hsAmount);
			} else if (typeCode.equals("5002")) {
				item.setF1111(item.getF5002() + hsAmount);
			}
		}

		//
		// 加入不在当前料件中的记录,由于这些记录可能被成品使用
		//
		Iterator<FactoryAndFactualCustomsRalation> iteratorMt = parameterMaps.statCusNameRelationMtValueMap
				.values().iterator();
		while (iteratorMt.hasNext()) {
			FactoryAndFactualCustomsRalation mt = iteratorMt.next();
			
			Materiel materiel = mt.getMateriel();
			if (materiel == null) {
				continue;
			}
			//
			// key=料号
			//
			String key = materiel.getPtNo();
			
			if (resultHash.get(key) == null) {
				TempImgOrgUseInfo item = new TempImgOrgUseInfo();
				item.setPtName(materiel.getFactoryName());
				item.setPtSpec(materiel.getFactorySpec());
				item.setPtUnitName(materiel.getPtUnit() == null ? "" : materiel
						.getPtUnit().getName());
				item.setPtPart(key);
				resultHash.put(key, item);
			}
		}

		Iterator<TempImgOrgUseInfo> iterator = resultHash.values().iterator();
		while (iterator.hasNext()) {
			TempImgOrgUseInfo item = iterator.next();
			//
			// key=料号
			//
			String key = item.getPtPart();

			FactoryAndFactualCustomsRalation mt = parameterMaps.statCusNameRelationMtValueMap
					.get(key);

			double unitConvert = (mt == null || mt.getUnitConvert() == null) ? 0.0
					: mt.getUnitConvert();

			item.setF2001(item.getF2001()
					+ (parameterMaps.billDetail2001Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2001Map.get(key))
					* unitConvert);
			item.setF2002(item.getF2002()
					+ (parameterMaps.billDetail2002Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2002Map.get(key))
					* unitConvert);
			item.setF2003(item.getF2003()
					+ (parameterMaps.billDetail2003Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2003Map.get(key))
					* unitConvert);
			item.setF2004(item.getF2004()
					+ (parameterMaps.billDetail2004Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2004Map.get(key))
					* unitConvert);
			item.setF2005(item.getF2005()
					+ (parameterMaps.billDetail2005Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2005Map.get(key))
					* unitConvert);
			item.setF2007(item.getF2007()
					+ (parameterMaps.billDetail2007Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2007Map.get(key))
					* unitConvert);
			item.setF2008(item.getF2008()
					+ (parameterMaps.billDetail2008Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2008Map.get(key))
					* unitConvert);
			item.setF2009(item.getF2009()
					+ (parameterMaps.billDetail2009Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2009Map.get(key))
					* unitConvert);
			item.setF2010(item.getF2010()
					+ (parameterMaps.billDetail2010Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2010Map.get(key))
					* unitConvert);
			item.setF2011(item.getF2011()
					+ (parameterMaps.billDetail2011Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2011Map.get(key))
					* unitConvert);
			item.setF2012(item.getF2012()
					+ (parameterMaps.billDetail2012Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2012Map.get(key))
					* unitConvert);

			item.setF2101(item.getF2101()
					+ (parameterMaps.billDetail2101Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2101Map.get(key))
					* unitConvert);
			item.setF2102(item.getF2102()
					+ (parameterMaps.billDetail2102Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2102Map.get(key))
					* unitConvert);
			item.setF2103(item.getF2103()
					+ (parameterMaps.billDetail2103Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2103Map.get(key))
					* unitConvert);
			item.setF2104(item.getF2104()
					+ (parameterMaps.billDetail2104Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2104Map.get(key))
					* unitConvert);
			item.setF2105(item.getF2105()
					+ (parameterMaps.billDetail2105Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2105Map.get(key))
					* unitConvert);
			item.setF2106(item.getF2106()
					+ (parameterMaps.billDetail2106Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2106Map.get(key))
					* unitConvert);
			item.setF2107(item.getF2107()
					+ (parameterMaps.billDetail2107Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2107Map.get(key))
					* unitConvert);
			item.setF2108(item.getF2108()
					+ (parameterMaps.billDetail2108Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2108Map.get(key))
					* unitConvert);
			item.setF2109(item.getF2109()
					+ (parameterMaps.billDetail2109Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2109Map.get(key))
					* unitConvert);
			item.setF2110(item.getF2110()
					+ (parameterMaps.billDetail2110Map.get(key) == null ? 0.0
							: parameterMaps.billDetail2110Map.get(key))
					* unitConvert);

			item.setF4001(item.getF4001()
					+ (parameterMaps.billDetail4001Map.get(key) == null ? 0.0
							: parameterMaps.billDetail4001Map.get(key))
					* unitConvert);
			item.setF4002(item.getF4002()
					+ (parameterMaps.billDetail4002Map.get(key) == null ? 0.0
							: parameterMaps.billDetail4002Map.get(key))
					* unitConvert);
			item.setF4003(item.getF4003()
					+ (parameterMaps.billDetail4003Map.get(key) == null ? 0.0
							: parameterMaps.billDetail4003Map.get(key))
					* unitConvert);
			item.setF4009(item.getF4009()
					+ (parameterMaps.billDetail4009Map.get(key) == null ? 0.0
							: parameterMaps.billDetail4009Map.get(key))
					* unitConvert);
			item.setF4101(item.getF4101()
					+ (parameterMaps.billDetail4101Map.get(key) == null ? 0.0
							: parameterMaps.billDetail4101Map.get(key))
					* unitConvert);
			item.setF4102(item.getF4102()
					+ (parameterMaps.billDetail4102Map.get(key) == null ? 0.0
							: parameterMaps.billDetail4102Map.get(key))
					* unitConvert);
			item.setF4103(item.getF4103()
					+ (parameterMaps.billDetail4103Map.get(key) == null ? 0.0
							: parameterMaps.billDetail4103Map.get(key))
					* unitConvert);
			item.setF4104(item.getF4104()
					+ (parameterMaps.billDetail4104Map.get(key) == null ? 0.0
							: parameterMaps.billDetail4104Map.get(key))
					* unitConvert);
			item
					.setF2002Waste(item.getF2002Waste()
							+ (parameterMaps.billDetail2002MapWaste.get(key) == null ? 0.0
									: parameterMaps.billDetail2002MapWaste
											.get(key)) * unitConvert);
			item
					.setF4003Waste(item.getF4003Waste()
							+ (parameterMaps.billDetail4003MapWaste.get(key) == null ? 0.0
									: parameterMaps.billDetail4003MapWaste
											.get(key)) * unitConvert);
			item
					.setF2103Waste(item.getF2103Waste()
							+ (parameterMaps.billDetail2103MapWaste.get(key) == null ? 0.0
									: parameterMaps.billDetail2103MapWaste
											.get(key)) * unitConvert);
			item
					.setF2007Waste(item.getF2007Waste()
							+ (parameterMaps.billDetail2007MapWaste.get(key) == null ? 0.0
									: parameterMaps.billDetail2007MapWaste
											.get(key)) * unitConvert);
			item
					.setF2110Waste(item.getF2110Waste()
							+ (parameterMaps.billDetail2110MapWaste.get(key) == null ? 0.0
									: parameterMaps.billDetail2110MapWaste
											.get(key)) * unitConvert);
			item
					.setF4104Waste(item.getF4104Waste()
							+ (parameterMaps.billDetail4104MapWaste.get(key) == null ? 0.0
									: parameterMaps.billDetail4104MapWaste
											.get(key)) * unitConvert);
			item
					.setF4105Waste(item.getF4105Waste()
							+ (parameterMaps.billDetail4105MapWaste.get(key) == null ? 0.0
									: parameterMaps.billDetail4105MapWaste
											.get(key)) * unitConvert);
			
			reslutList.add(item);
		}
		return reslutList;
	}
	

	/**
	 * 初始化企业物料 (statCusNameRelationMtListMap) 和
	 * 实际报关商品(statCusNameRelationMtValueMap) 对应表 statCusNameRelationMtList
	 */
	private void initStatCusNameRelationMap(ImgOrgUseInfoBase imgOrgUseInfo, ParameterMaps parameterMaps) {
		
		// 实际报关资料key（名称+规格+单位） 对应的 对应关系列表
		Map<String, List<FactoryAndFactualCustomsRalation>> statCusNameRelationMtListMap = new HashMap<String, List<FactoryAndFactualCustomsRalation>>();
		// 工厂料号 对应的 对应关系
		Map<String, FactoryAndFactualCustomsRalation> statCusNameRelationMtValueByMaterielMap = new HashMap<String, FactoryAndFactualCustomsRalation>();
		
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
		
		// key为imgOrgUseInfo的： 报关名称+" / "+规格+" / "+单位名称
		parameterMaps.statCusNameRelationMtList = statCusNameRelationMtListMap
				.get(
				(imgOrgUseInfo.getPtName() == null ? "" : imgOrgUseInfo.getPtName())
				+ " / " + 
				(imgOrgUseInfo.getPtSpec() == null ? "" : imgOrgUseInfo.getPtSpec())
				+ " / " + 
				(imgOrgUseInfo.getPtUnitName() == null ? ""	: imgOrgUseInfo.getPtUnitName()));
		List<FactoryAndFactualCustomsRalation> statCusNameRelationMtList = parameterMaps.statCusNameRelationMtList;
		Map<String, FactoryAndFactualCustomsRalation> statCusNameRelationMtValueMap = parameterMaps.statCusNameRelationMtValueMap;
		
		if(statCusNameRelationMtList == null) {
			return ;
		}
		
		mt = null;// 对应关系
		sr = null; //实际报关资料
		hsName = null;// 报关名称
		hsSpec = null;// 规格
		unitName = null;// 单位名称
		for (int i = 0; i < statCusNameRelationMtList.size(); i++) {
			mt = statCusNameRelationMtList.get(i);
			sr = mt.getStatCusNameRelationHsn();
			hsName = sr.getCusName() == null ? "" : sr.getCusName();
			hsSpec = sr.getCusSpec() == null ? "" : sr.getCusSpec();
			unitName = sr.getCusUnit() == null ? "" : sr.getCusUnit()
					.getName();
			unitName = unitName == null ? "" : unitName;
			if (hsName.equals(imgOrgUseInfo.getPtName())
					&& hsSpec.equals(imgOrgUseInfo.getPtSpec())
					&& unitName.equals(imgOrgUseInfo.getPtUnitName())) {
				// 料号 为 key
				statCusNameRelationMtValueMap.put(mt.getMateriel().getPtNo(), mt);
			}
		}
	}

	/**
	 * 初始化大类和实际的对应的信息用于一对多的结转计算
	 * 
	 */
//	private void initStatCusNameRelationHsMap(
//			StatCusNameRelation statCusNameRelation, ParameterMaps parameterMaps) {
//		Map<String, String> statCusNameRelationHsMap = parameterMaps.statCusNameRelationHsMap;
//
//		List<StatCusNameRelationHsn> listExistStatCusNameRelationHsn = this.casDao
//				.findStatCusNameRelationHsn(statCusNameRelation);
//		for (int i = 0, n = listExistStatCusNameRelationHsn.size(); i < n; i++) {
//			StatCusNameRelationHsn hsn = listExistStatCusNameRelationHsn.get(i);
//			StatCusNameRelation sr = hsn.getStatCusNameRelation();
//
//			String hsName = hsn.getCusName() == null ? "" : hsn.getCusName();
//			String hsSpec = hsn.getCusSpec() == null ? "" : hsn.getCusSpec();
//			String unitName = hsn.getCusUnit() == null ? "" : hsn.getCusUnit()
//					.getName();
//			unitName = unitName == null ? "" : unitName;
//			String key = hsName + " / " + hsSpec + " / " + unitName;
//
//			//
//			// value = 报关名称+" / "+规格+" / "+单位名称
//			//
//			String tenHsName = sr.getCusName() == null ? "" : sr.getCusName();
//			String tenHsSpec = sr.getCusSpec() == null ? "" : sr.getCusSpec();
//			String tenUnitName = sr.getCusUnit() == null ? "" : sr.getCusUnit()
//					.getName();
//			tenUnitName = tenUnitName == null ? "" : tenUnitName;
//			String value = tenHsName + " / " + tenHsSpec + " / " + tenUnitName;
//
//			if (statCusNameRelationHsMap.get(key) == null) {
//				statCusNameRelationHsMap.put(key, value);
//			}
//		}
//	}
	/**
	 * 4003 委外加工入库
	 * 4009 半成品期初单
	 * 4104 外发加工返修半成品 
	 * 4105 外发加工领料 
	 * 获得其拆算数据
	 */
	private List getHalfProductList(Date beginDate, Date endDate) {
		String halfProductTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.SEMI_FINISHED_PRODUCT);

		String select = " select sum(a.ptAmount),a.billMaster.billType.code,v.id from "
				+ halfProductTableName
				+ " a "
				+ ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = 0 )) "
				+ " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ? and a.billMaster.billType.code in (?,?,?,?) "
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(beginDate);
		params.add(endDate);
		params.add(CommonUtils.getCompany().getId());
		params.add("4003"); // 4003 委外加工入库
		params.add("4009"); // 4009 半成品期初单
		params.add("4104"); // 4104 外发加工返修半成品
		params.add("4105"); // 4105 外发加工领料

		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(select, params.toArray());

		logger.info("CAS 开始查询委外半成品的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

		return list;
	}

	/**
	 * 初始化 成品单据明细 hashMap
	 * 
	 */
	private void initBillDetailProductMap(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo, ImgOrgUseInfoBase imgOrgUseInfo) {
		String productTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);

		String selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id from "
				+ productTableName
				+ " a "
				+ ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = 0 )) "
				+ " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ? ";
				

		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(beginDate);
		params.add(endDate);
		params.add(CommonUtils.getCompany().getId());
		
//		if (imgOrgUseInfo.getPtName() != null
//				&& !imgOrgUseInfo.getPtName().equals("")) {
//			selects += " and a.hsName = ?";
//			params.add(imgOrgUseInfo.getPtName());
//		}
//		if (imgOrgUseInfo.getPtSpec() != null
//				&& !imgOrgUseInfo.getPtSpec().equals("")) {
//			selects += " and a.hsSpec = ?";
//			params.add(imgOrgUseInfo.getPtSpec());
//		}
//		if (imgOrgUseInfo.getPtUnitName() != null
//				&& !imgOrgUseInfo.getPtUnitName().equals("")) {
//			selects += " and a.hsUnit.name = ?";
//			params.add(imgOrgUseInfo.getPtUnitName());
//		}
		
		selects += (" group by a.billMaster.billType.code,v.id "
		+ " order by v.id ");// order by 确保 bom id 只查询一次
		
		
		
		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, params.toArray());
		//
		// 加入半成品成品(委外)
		//
		list.addAll(this.getHalfProductList(beginDate, endDate));

		logger.info("CAS 开始查询所有成品的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

		//
		// init
		//
		Map<String, Double> billDetail2001Map = parameterMaps.billDetail2001Map; // 2001
		// 成品起初单
		Map<String, Double> billDetail2002Map = parameterMaps.billDetail2002Map; // 2002
		// 车间入库
		Map<String, Double> billDetail2003Map = parameterMaps.billDetail2003Map; // 
		Map<String, Double> billDetail2004Map = parameterMaps.billDetail2004Map; // 
		Map<String, Double> billDetail2005Map = parameterMaps.billDetail2005Map; // 			
		Map<String, Double> billDetail2007Map = parameterMaps.billDetail2007Map; // 2007
		// 受托加工车间入库
		Map<String, Double> billDetail2008Map = parameterMaps.billDetail2008Map;
		Map<String, Double> billDetail2009Map = parameterMaps.billDetail2009Map; // 2009
		// 结转成品退货单
		Map<String, Double> billDetail2010Map = parameterMaps.billDetail2010Map; // 2010
		// 受托加工退回成品
		Map<String, Double> billDetail2011Map = parameterMaps.billDetail2011Map;
		Map<String, Double> billDetail2012Map = parameterMaps.billDetail2012Map;

		Map<String, Double> billDetail2101Map = parameterMaps.billDetail2101Map; // 2101
		// 直接出口
		Map<String, Double> billDetail2102Map = parameterMaps.billDetail2102Map; // 2102
		// 结转出口
		Map<String, Double> billDetail2103Map = parameterMaps.billDetail2103Map; // 2103
		// 返回车间
		Map<String, Double> billDetail2104Map = parameterMaps.billDetail2104Map;
		Map<String, Double> billDetail2105Map = parameterMaps.billDetail2105Map;
		Map<String, Double> billDetail2106Map = parameterMaps.billDetail2106Map; // 2106
		// 海关批准内销
		Map<String, Double> billDetail2107Map = parameterMaps.billDetail2107Map; // 2107
		// 其他内销
		Map<String, Double> billDetail2108Map = parameterMaps.billDetail2108Map;
		Map<String, Double> billDetail2109Map = parameterMaps.billDetail2109Map;
		Map<String, Double> billDetail2110Map = parameterMaps.billDetail2110Map; // 2110
		// 受托加工返回

		Map<String, Double> billDetail4001Map = parameterMaps.billDetail4001Map;
		Map<String, Double> billDetail4002Map = parameterMaps.billDetail4002Map;
		Map<String, Double> billDetail4003Map = parameterMaps.billDetail4003Map;// 4003
		Map<String, Double> billDetail4009Map = parameterMaps.billDetail4009Map;// 4009
		// 外发加工入库

		Map<String, Double> billDetail4101Map = parameterMaps.billDetail4101Map;
		Map<String, Double> billDetail4102Map = parameterMaps.billDetail4102Map;
		Map<String, Double> billDetail4103Map = parameterMaps.billDetail4103Map;
		Map<String, Double> billDetail4104Map = parameterMaps.billDetail4104Map;

		Map<String, Double> billDetail2002MapWaste = parameterMaps.billDetail2002MapWaste;
		Map<String, Double> billDetail4003MapWaste = parameterMaps.billDetail4003MapWaste;
		Map<String, Double> billDetail2007MapWaste = parameterMaps.billDetail2007MapWaste;
		Map<String, Double> billDetail2103MapWaste = parameterMaps.billDetail2103MapWaste;
		Map<String, Double> billDetail2110MapWaste = parameterMaps.billDetail2110MapWaste;
		Map<String, Double> billDetail4104MapWaste = parameterMaps.billDetail4104MapWaste;
		Map<String, Double> billDetail4105MapWaste = parameterMaps.billDetail4105MapWaste;

		//
		// 存取分页查询bom时所临时存放的成品列表
		//
		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		
		int size = list.size();
		Object[] objs = null;
		for (int i = 0; i < size; i++) {
			objs = (Object[]) list.get(i);
			String versionId = (String) objs[2]; // version guid

			if (versionId == null) {
				continue;
			}
			//
			// 为了去掉重复的 versionId
			//
			versionIdMap.put(versionId, versionId);
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
				
		
		if (materialBomDetailList.size() <= 0) {
			logger.info(" 对应的BOM明细为空!! ");
			tempList.clear();
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

		objs = null;
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
				// 成品单项用量
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
				//
				// 成品单耗用量（净耗）
				//
				Double productMaterialUnitWaste = ptAmount * unitWaste;

				if (typeCode.equals("2001")) {// 2001 成品起初单
					if (billDetail2001Map.get(ptNoKey) == null) {
						billDetail2001Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2001Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2001Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2002")) {// 2002 车间入库
					if (billDetail2002Map.get(ptNoKey) == null) {
						billDetail2002Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2002Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2002Map.put(ptNoKey, tempAmout);
					}
					if (billDetail2002MapWaste.get(ptNoKey) == null) {
						billDetail2002MapWaste.put(ptNoKey,
								productMaterialWaste);
					} else {
						Double tempAmout = billDetail2002MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialWaste;
						billDetail2002MapWaste.put(ptNoKey, tempAmout);
					}

				} else if (typeCode.equals("2003")) {
					if (billDetail2003Map.get(ptNoKey) == null) {
						billDetail2003Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2003Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2003Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2004")) {
					if (billDetail2004Map.get(ptNoKey) == null) {
						billDetail2004Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2004Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2004Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2005")) {
					if (billDetail2005Map.get(ptNoKey) == null) {
						billDetail2005Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2005Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2005Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2007")) {// 2007 受托加工车间入库
					if (billDetail2007Map.get(ptNoKey) == null) {
						billDetail2007Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2007Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2007Map.put(ptNoKey, tempAmout);
					}

					if (billDetail2007MapWaste.get(ptNoKey) == null) {
						billDetail2007MapWaste.put(ptNoKey,
								productMaterialWaste);
					} else {
						Double tempAmout = billDetail2007MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialWaste;
						billDetail2007MapWaste.put(ptNoKey, tempAmout);
					}

				} else if (typeCode.equals("2008")) {
					if (billDetail2008Map.get(ptNoKey) == null) {
						billDetail2008Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2008Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2008Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2009")) {// 2009 结转成品退货单
					if (billDetail2009Map.get(ptNoKey) == null) {
						billDetail2009Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2009Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2009Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2010")) {// 2010 受托加工退回成品
					if (billDetail2010Map.get(ptNoKey) == null) {
						billDetail2010Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2010Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2010Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2011")) {
					if (billDetail2011Map.get(ptNoKey) == null) {
						billDetail2011Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2011Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2011Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2012")) {
					if (billDetail2012Map.get(ptNoKey) == null) {
						billDetail2012Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2012Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2012Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2101")) {// 2101 直接出口
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
					if (billDetail2103Map.get(ptNoKey) == null) {
						billDetail2103Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2103Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2103Map.put(ptNoKey, tempAmout);
					}

					if (billDetail2103MapWaste.get(ptNoKey) == null) {
						billDetail2103MapWaste.put(ptNoKey,
								productMaterialWaste);
					} else {
						Double tempAmout = billDetail2103MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialWaste;
						billDetail2103MapWaste.put(ptNoKey, tempAmout);
					}

				} else if (typeCode.equals("2104")) {
					if (billDetail2104Map.get(ptNoKey) == null) {
						billDetail2104Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2104Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2104Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2105")) {
					if (billDetail2105Map.get(ptNoKey) == null) {
						billDetail2105Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2105Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2105Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2106")) {// 2106 海关批准内销
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
					if (billDetail2107Map.get(ptNoKey) == null) {
						billDetail2107Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2107Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2107Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2108")) {
					if (billDetail2108Map.get(ptNoKey) == null) {
						billDetail2108Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2108Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2108Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2109")) {
					if (billDetail2109Map.get(ptNoKey) == null) {
						billDetail2109Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2109Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2109Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("2110")) {// 2110 受托加工返回
					if (billDetail2110Map.get(ptNoKey) == null) {
						billDetail2110Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail2110Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail2110Map.put(ptNoKey, tempAmout);
					}

					if (billDetail2110MapWaste.get(ptNoKey) == null) {
						billDetail2110MapWaste.put(ptNoKey,
								productMaterialWaste);
					} else {
						Double tempAmout = billDetail2110MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialWaste;
						billDetail2110MapWaste.put(ptNoKey, tempAmout);
					}

				} else if (typeCode.equals("4001")) {
					if (billDetail4001Map.get(ptNoKey) == null) {
						billDetail4001Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail4001Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail4001Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("4002")) {
					if (billDetail4002Map.get(ptNoKey) == null) {
						billDetail4002Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail4002Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail4002Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("4003")) {// 4003 外发加工入库
					if (billDetail4003Map.get(ptNoKey) == null) {
						billDetail4003Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail4003Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail4003Map.put(ptNoKey, tempAmout);
					}

					if (billDetail4003MapWaste.get(ptNoKey) == null) {
						billDetail4003MapWaste.put(ptNoKey,
								productMaterialWaste);
					} else {
						Double tempAmout = billDetail4003MapWaste
								.get(ptNoKey);
						tempAmout += productMaterialWaste;
						billDetail4003MapWaste.put(ptNoKey, tempAmout);
					}

				} else if (typeCode.equals("4009")) {// 4009 半成品初期
					if (billDetail4009Map.get(ptNoKey) == null) {
						billDetail4009Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail4009Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail4009Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("4101")) {
					if (billDetail4101Map.get(ptNoKey) == null) {
						billDetail4101Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail4101Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail4101Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("4102")) {
					if (billDetail4102Map.get(ptNoKey) == null) {
						billDetail4102Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail4102Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail4102Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("4103")) {
					if (billDetail4103Map.get(ptNoKey) == null) {
						billDetail4103Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail4103Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail4103Map.put(ptNoKey, tempAmout);
					}
				} else if (typeCode.equals("4104")) {// 4104 外发加工返修成品
					if (billDetail4104Map.get(ptNoKey) == null) {
						billDetail4104Map.put(ptNoKey,
								productMaterialUnitWaste);
					} else {
						Double tempAmout = billDetail4104Map
								.get(ptNoKey);
						tempAmout += productMaterialUnitWaste;
						billDetail4104Map.put(ptNoKey, tempAmout);
					}

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
			}
		}
	}

	/**
	 * 最初的统计信息
	 */
	public class FirstSumInfo {
		private Double	hsAmount	= Double.valueOf(0);
		private Double	money		= Double.valueOf(0);	// 金额
		private String	typeCode;
		private String	hsName;
		private String	hsSpec;
		private String	hsUnitName;
		private String	ptPart;

		public FirstSumInfo() {

		}

		public FirstSumInfo(Object[] values) {
			hsAmount = (Double) values[0];
			money = (Double) values[1];
			typeCode = (String) values[2];
			hsName = (String) values[3];
			hsSpec = (String) values[4];
			hsUnitName = (String) values[5];
			ptPart = (String) values[6];
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

		public String getPtPart() {
			return ptPart;
		}

		public void setPtPart(String ptPart) {
			this.ptPart = ptPart;
		}

	}

	/**
	 * 生成料件统计记录
	 * 
	 * @return
	 */
	private TempImgOrgUseInfo makeTotalTempImgExgUseInfo(
			List<TempImgOrgUseInfo> returnList) {
		TempImgOrgUseInfo temp = new TempImgOrgUseInfo();

		Double f1001 = 0.0; // 料件期初单
		Double f1002 = 0.0; // 在产品期初单
		Double f1003 = 0.0; // 直接进口
		Double f1004 = 0.0; // 结转进口
		Double f1005 = 0.0; // 国内购买
		Double f1006 = 0.0; // 车间返回
		Double f1007 = 0.0; // 料件盘盈单
		Double f1008 = 0.0; // 受托加工进库
		Double f1009 = 0.0; // 其他来源
		Double f1010 = 0.0; // 料件转仓入库
		Double f1011 = 0.0; // 海关征税进口
		Double f1012 = 0.0; // 外发加工退回料件
		Double f1013 = 0.0; // 外发加工返回料件
		Double f1014 = 0.0; // 委外期初单
		Double f1015 = 0.0; // 已收货未结转期初单
		Double f1016 = 0.0; // 已结转未收货期初单
		Double f1101 = 0.0; // 车间领用
		Double f1102 = 0.0; // 料件退换
		Double f1103 = 0.0; // 料件复出
		Double f1104 = 0.0; // 料件盘亏单
		Double f1105 = 0.0; // 料件转仓出库
		Double f1106 = 0.0; // 结转料件退货单
		Double f1107 = 0.0; // 其他料件退货单
		Double f1108 = 0.0; // 其他领用
		Double f1109 = 0.0; // 受托加工领用
		Double f1110 = 0.0; // 外发加工出库
		Double f1111 = 0.0; // 受托加工退回料件
		Double f2001 = 0.0; // 成品期初单
		Double f2002 = 0.0; // 车间入库
		Double f2003 = 0.0; // 退厂返工
		Double f2004 = 0.0; // 成品盘盈单
		Double f2005 = 0.0; // 成品转仓入库
		Double f2007 = 0.0; // 受托加工车间入库
		Double f2008 = 0.0; // 其他成品退货单
		Double f2009 = 0.0; // 结转成品退货单
		Double f2010 = 0.0; // 受托加工退回成品
		Double f2011 = 0.0; // 已交货未结转期初单
		Double f2012 = 0.0; // 已交货未结转期初单
		Double f2101 = 0.0; // 直接出口
		Double f2102 = 0.0; // 结转出口
		Double f2103 = 0.0; // 返回车间
		Double f2104 = 0.0; // 返工复出
		Double f2105 = 0.0; // 成品盘亏单
		Double f2106 = 0.0; // 海关批准内销
		Double f2107 = 0.0; // 其他内销
		Double f2108 = 0.0; // 其他处理
		Double f2109 = 0.0; // 成品转仓出库
		Double f2110 = 0.0; // 受托加工返回
		Double f4001 = 0.0; // 半成品入库
		Double f4002 = 0.0; // 半成品盘盈单
		Double f4003 = 0.0; // 委外加工入库
		Double f4009 = 0.0; // 半成品期初单
		Double f4101 = 0.0; // 半成品出库
		Double f4102 = 0.0; // 半成品盘亏单
		Double f4103 = 0.0; // 委外加工出库
		Double f4104 = 0.0; // 外发加工返修半成品
		Double f4105 = 0.0; // 外发加工领料
		Double f5002 = 0.0; // 残次品期初单
		Double f2002Waste = 0.0;
		Double f4003Waste = 0.0;
		Double f2007Waste = 0.0;
		Double f2103Waste = 0.0;
		Double f2110Waste = 0.0;
		Double f4104Waste = 0.0;
		Double f4105Waste = 0.0;

		for (int i = 0, n = returnList.size(); i < n; i++) {
			TempImgOrgUseInfo e = returnList.get(i);
			f1001 += e.getF1001();
			f1002 += e.getF1002();
			f1003 += e.getF1003();
			f1004 += e.getF1004();
			f1005 += e.getF1005();
			f1006 += e.getF1006();
			f1007 += e.getF1007();
			f1008 += e.getF1008();
			f1009 += e.getF1009();
			f1010 += e.getF1010();

			f1011 += e.getF1011();
			f1012 += e.getF1012();
			f1013 += e.getF1013();
			f1014 += e.getF1014();
			f1015 += e.getF1015();
			f1016 += e.getF1016();
			f1101 += e.getF1101();
			f1102 += e.getF1102();
			f1103 += e.getF1103();
			f1104 += e.getF1104();
			f1105 += e.getF1105();
			f1106 += e.getF1106();
			f1107 += e.getF1107();

			f1108 += e.getF1108();
			f1109 += e.getF1109();
			f1110 += e.getF1110();
			f1111 += e.getF1111();

			f2001 += e.getF2001(); // 成品期初单
			f2002 += e.getF2002(); // 车间入库
			f2003 += e.getF2003(); // 退厂返工
			f2004 += e.getF2004(); // 成品盘盈单
			f2005 += e.getF2005(); // 成品转仓入库
			f2007 += e.getF2007(); // 受托加工车间入库
			f2008 += e.getF2008(); // 其他成品退货单
			f2009 += e.getF2009(); // 结转成品退货单
			f2010 += e.getF2010(); // 受托加工退回成品
			f2011 += e.getF2011(); // 已交货未结转期初单
			f2012 += e.getF2012(); // 已交货未结转期初单
			f2101 += e.getF2101(); // 直接出口
			f2102 += e.getF2102(); // 结转出口
			f2103 += e.getF2103(); // 返回车间
			f2104 += e.getF2104(); // 返工复出
			f2105 += e.getF2105(); // 成品盘亏单
			f2106 += e.getF2106(); // 海关批准内销
			f2107 += e.getF2107(); // 其他内销
			f2108 += e.getF2108(); // 其他处理
			f2109 += e.getF2109(); // 成品转仓出库
			f2110 += e.getF2110(); // 受托加工返回
			f4001 += e.getF4001(); // 半成品入库
			f4002 += e.getF4002(); // 半成品盘盈单
			f4003 += e.getF4003(); // 委外加工入库
			f4009 += e.getF4009(); // 半成品期初单
			f4101 += e.getF4101(); // 半成品出库
			f4102 += e.getF4102(); // 半成品盘亏单
			f4103 += e.getF4103(); // 委外加工出库
			f4104 += e.getF4104(); // 外发加工返修半成品
			f4105 += e.getF4105(); // 外发加工领料
			f5002 += e.getF5002(); // 残次品期初单
			f2002Waste += e.getF2002Waste();
			f4003Waste += e.getF4003Waste();
			f2007Waste += e.getF2007Waste();
			f2103Waste += e.getF2103Waste();
			f2110Waste += e.getF2110Waste();
			f4104Waste += e.getF4104Waste();
			f4105Waste += e.getF4105Waste();
		}

		temp.setF1001(f1001);
		temp.setF1002(f1002);
		temp.setF1003(f1003);
		temp.setF1004(f1004);
		temp.setF1005(f1005);
		temp.setF1006(f1006);
		temp.setF1007(f1007);
		temp.setF1008(f1008);
		temp.setF1009(f1009);
		temp.setF1010(f1010);
		temp.setF1011(f1011);
		temp.setF1012(f1012);
		temp.setF1013(f1013);
		temp.setF1014(f1014);
		temp.setF1015(f1015);
		temp.setF1016(f1016);

		temp.setF1101(f1101);
		temp.setF1102(f1102);
		temp.setF1103(f1103);
		temp.setF1104(f1104);
		temp.setF1105(f1105);
		temp.setF1106(f1106);
		temp.setF1107(f1107);
		temp.setF1108(f1108);
		temp.setF1109(f1109);
		temp.setF1110(f1110);
		temp.setF1111(f1111);

		temp.setF2001(f2001);
		temp.setF2002(f2002);
		temp.setF2003(f2003);
		temp.setF2004(f2004);
		temp.setF2005(f2005);
		temp.setF2007(f2007);
		temp.setF2008(f2008);
		temp.setF2009(f2009);
		temp.setF2010(f2010);
		temp.setF2011(f2011);
		temp.setF2012(f2012);

		temp.setF2101(f2101);
		temp.setF2102(f2102);
		temp.setF2103(f2103);
		temp.setF2104(f2104);
		temp.setF2105(f2105);
		temp.setF2106(f2106);
		temp.setF2107(f2107);
		temp.setF2108(f2108);
		temp.setF2109(f2109);
		temp.setF2110(f2110);

		temp.setF4001(f4001);
		temp.setF4002(f4002);
		temp.setF4003(f4003);
		temp.setF4009(f4009);
		temp.setF4101(f4101);
		temp.setF4102(f4102);
		temp.setF4103(f4103);
		temp.setF4104(f4104);
		temp.setF4105(f4105);

		temp.setF2002Waste(f2002Waste);
		temp.setF4003Waste(f4003Waste);
		temp.setF2007Waste(f2007Waste);
		temp.setF2103Waste(f2103Waste);
		temp.setF2110Waste(f2110Waste);
		temp.setF4104Waste(f4104Waste);
		temp.setF4105Waste(f4105Waste);

		temp.setPtName("合计");
		return temp;
	}
}
