package com.bestway.bcus.checkstock.logic;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcs.verification.entity.VFAnalyType;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.checkcancel.entity.CancelOwnerHead;
import com.bestway.bcus.checkstock.dao.ECSCheckStockDao;
import com.bestway.bcus.checkstock.dao.ECSContractAnalyseDao;
import com.bestway.bcus.checkstock.entity.ECSAnalyType;
import com.bestway.bcus.checkstock.entity.ECSContractAnalyse;
import com.bestway.bcus.checkstock.entity.ECSCustomsCountExg;
import com.bestway.bcus.checkstock.entity.ECSCustomsCountExgResolve;
import com.bestway.bcus.checkstock.entity.ECSCustomsCountImg;
import com.bestway.bcus.checkstock.entity.ECSDeclarationCommInfoExg;
import com.bestway.bcus.checkstock.entity.ECSDeclarationCommInfoImg;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.temp.ECSContractAnalyseTemp;
import com.bestway.bcus.checkstock.entity.temp.ECSCustomsCountExgResolveTemp;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.jptds.common.AppException;

public class ECSContractAnalyseLogic {
	private ECSContractAnalyseDao ecsContractAnalyseDao;
	private ECSCheckStockDao ecsCheckStockDao;

	/**
	 * @param csContractAnalyseDao
	 *            the csContractAnalyseDao to set
	 */
	public void setEcsContractAnalyseDao(
			ECSContractAnalyseDao ecsContractAnalyseDao) {
		this.ecsContractAnalyseDao = ecsContractAnalyseDao;
	}

	/**
	 * @param csCheckStockDao
	 *            the csCheckStockDao to set
	 */
	public void setEcsCheckStockDao(ECSCheckStockDao ecsCheckStockDao) {
		this.ecsCheckStockDao = ecsCheckStockDao;
	}

	/**
	 * 获得并保存成品情况统计表数据
	 * 
	 * @param section
	 *            盘点核查批次
	 * @param cancelTimes
	 *            核销次数
	 * @param beginDate
	 *            报关单申报开始日期
	 * @param endDate
	 *            报关单申报结束日期
	 */
	public void gainECSCustomsCountExg(ECSSection section) {
//		List list = ecsContractAnalyseDao
//				.findECSCustomsCountExgFromCancel(section);
		/**
		 * 2014-2-21添加成品明细表后，统计表的数据来源于明细表
		 */
		List<ECSDeclarationCommInfoExg> ptLs = ecsCheckStockDao.findByECSSection(section,
				ECSDeclarationCommInfoExg.class.getSimpleName(), null);
		Map countExgMap = new HashMap<String, ECSCustomsCountExg>();
		ECSDeclarationCommInfoExg pt = null;
		for (int i = 0; i < ptLs.size(); i++) {
			pt =  ptLs.get(i);
			/**
			 * 根据备案序号+版本号进行统计 出口数量 = 报关单标志==1，出口类型为【成品出口】的报关单商品数量； 转厂数量 =
			 * 报关单标志==1，出口类型为【转厂出口】的报关单商品数量； 退厂返工 =
			 * 报关单标志==0，进口类型为【退厂返工】的报关单商品数量； 返工复出 =
			 * 报关单标志==1，出口类型为【返工复出】的报关单商品数量； 总出口量 = 成品出口 + 转厂出口 -退厂返工 +返工复出
			 */
			String key = pt.getSeqNum() + "@" + pt.getVersion();
			// 帐册编号 成品备案序号 版本号 成品名称 型号规格 计量单位 商品数量 报关单标志 出口类型
			ECSCustomsCountExg tempCountExg = (ECSCustomsCountExg) countExgMap
					.get(key);
			int impExpFlag = pt.getImpExpFlag();// 报关单标志
			int impExpType = pt.getImpExpType();// 出口类型
			if (tempCountExg == null) {
				tempCountExg = new ECSCustomsCountExg();
				tempCountExg.init();
				tempCountExg.setEmsNo(pt.getEmsNo());// 帐册编号
				tempCountExg.setSeqNum(pt.getSeqNum());// 成品备案序号
				if (pt.getVersion() == null || "".equals(pt.getVersion())) {
					throw new RuntimeException("报关单存在无版本号的商品");
				} else {
					tempCountExg.setVersion(pt.getVersion());// 版本号
				}
				tempCountExg.setCommName(pt.getCommName());// 成品名称
				tempCountExg.setCommSpec(pt.getCommSpec());// 型号规格
				tempCountExg.setUnit(pt.getUnit());// 计量单位
				if (ImpExpFlag.IMPORT == impExpFlag) {// 0
					switch (impExpType) {
					case ImpExpType.BACK_FACTORY_REWORK:
						tempCountExg
								.setBackFactoryReworkAmount(pt.getCommAmount() == null ? 0 : pt.getCommAmount());
						break;
					}
				} else if (ImpExpFlag.EXPORT == impExpFlag) {// 1
					switch (impExpType) {
					case ImpExpType.DIRECT_EXPORT:
						tempCountExg
								.setDirectExportAmount(pt.getCommAmount() == null ? 0 : pt.getCommAmount());
						break;
					case ImpExpType.TRANSFER_FACTORY_EXPORT:
						tempCountExg
								.setTransferExportAmount(pt.getCommAmount() == null ? 0 : pt.getCommAmount());
						break;
					case ImpExpType.REWORK_EXPORT:
						tempCountExg
								.setReworkExportAmount(pt.getCommAmount() == null ? 0 : pt.getCommAmount());
						break;
					}
				}
				// 总出口量 = 成品出口 + 转厂出口 -退厂返工 +返工复出
				double totalExportAmount = tempCountExg.getDirectExportAmount()
						+ tempCountExg.getTransferExportAmount()
						+ tempCountExg.getReworkExportAmount()
						- tempCountExg.getBackFactoryReworkAmount();
				tempCountExg.setTotalExportAmount(totalExportAmount);
				tempCountExg.setCompany(CommonUtils.getCompany());
				tempCountExg.setSection(section);
				countExgMap.put(key, tempCountExg);
			} else {
				if (ImpExpFlag.IMPORT == impExpFlag) {// 0
					switch (impExpType) {
					case ImpExpType.BACK_FACTORY_REWORK:
						Double backFactoryReworkAmount = tempCountExg
								.getBackFactoryReworkAmount()
								+ (pt.getCommAmount() == null ? 0 : pt.getCommAmount());
						tempCountExg
								.setBackFactoryReworkAmount(backFactoryReworkAmount);
						break;
					}
				} else if (ImpExpFlag.EXPORT == impExpFlag) {// 1
					switch (impExpType) {
					case ImpExpType.DIRECT_EXPORT:
						Double directExportAmount = tempCountExg
								.getDirectExportAmount()
								+ (pt.getCommAmount() == null ? 0 : pt.getCommAmount());
						tempCountExg.setDirectExportAmount(directExportAmount);
						break;
					case ImpExpType.TRANSFER_FACTORY_EXPORT:
						Double transferExportAmount = tempCountExg
								.getTransferExportAmount()
								+ (pt.getCommAmount() == null ? 0 : pt.getCommAmount());
						tempCountExg
								.setTransferExportAmount(transferExportAmount);
						break;
					case ImpExpType.REWORK_EXPORT:
						Double reworkExportAmount = tempCountExg
								.getReworkExportAmount()
								+ (pt.getCommAmount() == null ? 0 : pt.getCommAmount());
						tempCountExg.setReworkExportAmount(reworkExportAmount);
						break;
					}
					// 总出口量 = 成品出口 + 转厂出口 -退厂返工 +返工复出
					double totalExportAmount = tempCountExg
							.getDirectExportAmount()
							+ tempCountExg.getTransferExportAmount()
							+ tempCountExg.getReworkExportAmount()
							- tempCountExg.getBackFactoryReworkAmount();
					tempCountExg.setTotalExportAmount(totalExportAmount);
				}
			}

		}
		// 批量保存成品统计
		List<ECSCustomsCountExg> countExgList = new ArrayList<ECSCustomsCountExg>();
		Iterator it = countExgMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			countExgList.add((ECSCustomsCountExg) countExgMap.get(key));
		}
		ecsContractAnalyseDao.batchSaveOrUpdate(countExgList);
	}

	/**
	 * 获得成品统计表
	 * 
	 * @param section
	 * @return
	 */
	public List findECSCustomsCountExgBySection(ECSSection section,
			Integer seqNum) {
		return ecsContractAnalyseDao.findECSCustomsCountExgBySection(section,
				seqNum);
	}

	/**
	 * 根据批次号删除数据表
	 * 
	 * @param section
	 *            批次
	 * @param analyType
	 *            核查分析类型
	 * @return
	 */
	public int delectVFBySection(ECSSection section, int analyType) {
		String table = "ECSCustomsCountImg";
		if (ECSAnalyType.ECSCUSTOMS_COUNTIMG == analyType) {
			table = "ECSCustomsCountExg";
		} else if (ECSAnalyType.ECSCUSTOMS_COUNTEXG == analyType) {
			table = "ECSCustomsCountExg";
		} else if (ECSAnalyType.ECSCUSTOMS_COUNTEXG_CONVERT == analyType) {
			table = "ECSCustomsCountExgResolve";
		} else if (ECSAnalyType.ECSCUSTOMS_ANALYSE == analyType) {
			table = "ECSContractAnalyse";
		}
		return ecsContractAnalyseDao.deleteECSBySection(section, table);
	}

	public List<Object[]> findEmsheadh2kBomCountExgBySection(ECSSection section) {
		// 根据成品备案序号+版本获得与成品统计表相关的BOM
		return null;
	}

	/**
	 * 折算成品并保存到成品折料统计表
	 * 
	 * @param countExgList
	 *            成品统计表
	 */
	public void convertCustomsCountExgToImg(ECSSection section) {
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在准备数据,请稍等...");
			info.setTotalNum(100);
		}
		long t = System.currentTimeMillis();
		//不要在同一个事务中删除再插入，这样速度会非常慢
//		// 删除已折算料件
//		delectVFBySection(section, ECSAnalyType.ECSCUSTOMS_COUNTEXG_CONVERT);
		if (info != null)
			info.setCurrentNum(30);
		long t2 = System.currentTimeMillis();
		System.out.println("---------------------" + ((t2 - t) / 1000.0d)
				+ " 删除------- 1----------------------------");
		t = t2;
		// 根据成品备案序号+版本获得与成品统计表相关的BOM
		int num = ecsContractAnalyseDao
				.countEmsheadh2kBomCountExgBySection(section);
		if (info != null)
			info.setCurrentNum(60);
		if (num > 0) {
			Iterator<Object[]> reList = ecsContractAnalyseDao
					.findEmsheadh2kBomCountExgBySection(section);
			if (info != null)
				info.setCurrentNum(100);
			if (info != null) {
				info.setMethodName("正在折算料件,请稍等...");
				info.setTotalNum(num / 100);
				info.setCurrentNum(0);
			}
			t2 = System.currentTimeMillis();
			System.out.println("---------------------" + ((t2 - t) / 1000.0d)
					+ " 查找------- 1----------------------------");
			t = t2;
			List<ECSCustomsCountExgResolve> countExgResolveList = new ArrayList<ECSCustomsCountExgResolve>();
			if (info != null)
				info.setCurrentNum(0);
			ECSCustomsCountExgResolve countExgResolve = null;
			ECSCustomsCountExg countExg = null;
			Object[] row = null;
			if(info!=null){
				info.setMethodName("正在保存数据，共"+num+"条");
				info.setTotalNum(num);
				info.setCurrentNum(0);
			}
			for (int i = 0; reList.hasNext(); i++) {
				row = reList.next();
//				if (info != null && (i - 100) % 100 == 0)
//					info.setCurrentNum(info.getCurrentNum() + 1);
				countExgResolve = new ECSCustomsCountExgResolve();
				countExgResolve.init();
				countExg = (ECSCustomsCountExg) row[1];
				// 帐册编号 成品情况统计 料件备案序号 料件名称 型号规格 单耗 损耗 耗用量 计量单位
				countExgResolve.setEmsNo((String) row[0]);// 帐册编号
				countExgResolve.setEcsCustomsCountExg(countExg);// 成品情况统计
				countExgResolve.setSeqNum((Integer) row[2]);// 料件备案序号
				countExgResolve.setCommName(CommonUtils
						.getStringExceptNull(row[3]));// 料件名称
				countExgResolve.setCommSpec(CommonUtils
						.getStringExceptNull(row[4]));// 型号规格
				countExgResolve.setUnitWaste(CommonUtils
						.getDoubleExceptNull((Double) row[5]));// 单耗
				countExgResolve.setWaste(CommonUtils
						.getDoubleExceptNull((Double) row[6]) * 0.01);// 损耗
				countExgResolve
						.setUnit(CommonUtils.getStringExceptNull(row[7]));// 计量单位

				// 总耗用 = 总出口数 * 单耗 / (1 - 损耗)
				double wasteAmount = CaleUtil.dividend(CaleUtil.multiply(
						countExg.getTotalExportAmount(), countExgResolve
								.getUnitWaste()), 1 - CommonUtils
						.getDoubleExceptNull(countExgResolve.getWaste()), 9);
				countExgResolve.setWasteAmount(wasteAmount);
				countExgResolve.setCompany(CommonUtils.getCompany());
				countExgResolve.setSection(section);
				countExgResolveList.add(countExgResolve);
				if(countExgResolveList.size()>=10000){
					// 批量保存成品统计折料
					this.ecsCheckStockDao.batchSaveOrUpdateDirect(countExgResolveList);
					countExgResolveList.clear();
					if(info != null ){
						info.setCurrentNum(i+1);
					}
				}
			}
			t2 = System.currentTimeMillis();
			System.out.println("---------------------" + ((t2 - t) / 1000.0d)
					+ " 折料------- 2----------------------------");
			t = t2;
			if (info != null) {
				info.setCurrentNum(0);
			}
			// 批量保存成品统计折料
			this.ecsCheckStockDao.batchSaveOrUpdateDirect(countExgResolveList);
			if(info != null ){
				info.setCurrentNum(info.getTotalNum());
			}
			t2 = System.currentTimeMillis();
			System.out.println("---------------------" + ((t2 - t) / 1000.0d)
					+ " 保存-------3----------------------------");
			t = t2;
//			return makeECSCustomsCountExgResolveTeam(countExgResolveList);
		}
//		return Collections.EMPTY_LIST;
	}

	/**
	 * 构造临时的成品折料临时数据
	 * 
	 * @param countExgResolveList
	 * @return
	 */
	public List makeECSCustomsCountExgResolveTeam(List countExgResolveList) {
		List countExgResolveListTemp = new ArrayList<ECSCustomsCountExgResolveTemp>();
		ECSCustomsCountExgResolve moni = (ECSCustomsCountExgResolve) countExgResolveList
				.get(0);
		for (int i = 0; i < countExgResolveList.size(); i++) {
			ECSCustomsCountExgResolveTemp countExgResolve = new ECSCustomsCountExgResolveTemp();
			countExgResolve.init();
			ECSCustomsCountExgResolve obj = (ECSCustomsCountExgResolve) countExgResolveList
					.get(i);
			// 账册编号 备案序号 版本号 成品名称 成品规格， 料件序号 料件名称 料件规格 计量单位 单耗 损耗 总耗用 总出口
			countExgResolve.setEmsNo(obj.getEmsNo());// 账册编号
			countExgResolve
					.setHsSeqNum(obj.getEcsCustomsCountExg().getSeqNum());// 备案序号
			countExgResolve
					.setVersion(obj.getEcsCustomsCountExg().getVersion());// 版本号
			countExgResolve
					.setHsName(obj.getEcsCustomsCountExg().getCommName());// 成品名称
			countExgResolve
					.setHsSpec(obj.getEcsCustomsCountExg().getCommSpec());// 成品规格
			countExgResolve.setPtSeqNum(obj.getSeqNum());// 料件序号
			countExgResolve.setPtName(obj.getCommName());// 料件名称
			countExgResolve.setPtSpec(obj.getCommSpec());// 料件规格
			countExgResolve.setUnit(obj.getUnit());// 计量单位
			countExgResolve.setUnitWaste(obj.getUnitWaste());// 单耗
			countExgResolve.setWaste(obj.getWaste());// 损耗
			countExgResolve.setWasteAmount(obj.getWasteAmount());// 总耗用
			countExgResolve.setTotalExportAmount(obj.getEcsCustomsCountExg()
					.getTotalExportAmount());// 总出口
			countExgResolveListTemp.add(countExgResolve);
		}

		countExgResolveList.clear();
		return countExgResolveListTemp;
	}

	/**
	 * 获得成品统计折料
	 * 
	 * @param section
	 * @return
	 */
	public List findECSCustomsCountExgResolveBySection(ECSSection section,
			Integer seqNum) {
//		List countExgResolveList = ecsContractAnalyseDao
//				.findECSCustomsCountExgResolveBySection(section, seqNum);
//		List countExgResolveListTemp = new ArrayList<ECSCustomsCountExgResolveTemp>();
//		for (int i = 0; i < countExgResolveList.size(); i++) {
//			ECSCustomsCountExgResolveTemp countExgResolve = new ECSCustomsCountExgResolveTemp();
//			countExgResolve.init();
//			Object[] obj = (Object[]) countExgResolveList.get(i);
//			// 账册编号 备案序号 版本号 成品名称 成品规格， 料件序号 料件名称 料件规格 计量单位 单耗 损耗 总耗用 总出口
//			countExgResolve.setEmsNo(CommonUtils.getStringExceptNull(obj[0]));// 账册编号
//			countExgResolve.setHsSeqNum(Integer.valueOf(obj[1].toString()));// 备案序号
//			countExgResolve.setVersion(Integer.valueOf(obj[2].toString()));// 版本号
//			countExgResolve.setHsName(CommonUtils.getStringExceptNull(obj[3]));// 成品名称
//			countExgResolve.setHsSpec(CommonUtils.getStringExceptNull(obj[4]));// 成品规格
//			countExgResolve.setPtSeqNum(Integer.valueOf(obj[5].toString()));// 料件序号
//			countExgResolve.setPtName(CommonUtils.getStringExceptNull(obj[6]));// 料件名称
//			countExgResolve.setPtSpec(obj[7] == null ? "" : obj[7].toString());// 料件规格
//			countExgResolve.setUnit(obj[8].toString());// 计量单位
//			countExgResolve.setUnitWaste(obj[9] == null ? Double.valueOf(0)
//					: Double.valueOf(obj[9].toString()));// 单耗
//			countExgResolve.setWaste(obj[10] == null ? Double.valueOf(0)
//					: Double.valueOf(obj[10].toString()));// 损耗
//			countExgResolve.setWasteAmount(obj[11] == null ? Double.valueOf(0)
//					: Double.valueOf(obj[11].toString()));// 总耗用
//			countExgResolve.setTotalExportAmount(obj[12] == null ? Double
//					.valueOf(0) : Double.valueOf(obj[12].toString()));// 总出口
//			countExgResolveListTemp.add(countExgResolve);
//		}
//		return countExgResolveListTemp;
		return ecsContractAnalyseDao.findECSCustomsCountExgResolveBySection(section);
	}
	/**
	 * 查询料件明细表数据
	 */
	public List<ECSDeclarationCommInfoImg> findECSDeclarationCommInfoImgBySection(
			ECSSection section, Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSDeclarationCommInfoImg.class.getSimpleName(), seqNum);
	}
	/**
	 * 查询成品明细表数据
	 */
	public List<ECSDeclarationCommInfoExg> findECSDeclarationCommInfoExgBySection(
			ECSSection section, Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSDeclarationCommInfoExg.class.getSimpleName(), seqNum);
	}
	
	/**
	 * 根据版本号和备案序号查询成品明细表数据
	 */
	public List<ECSDeclarationCommInfoExg> findECSDeclarationCommInfoExgBySectionAndVersion(
			ECSSection section, Integer seqNum,String version) {
		return ecsCheckStockDao.findByECSSectionAndVersion(section,
				ECSDeclarationCommInfoExg.class.getSimpleName(), seqNum,version);
	}
	
	/**
	 * 核销表头
	 */
	public CancelOwnerHead findCancelOwnerHead(String cancelTimes){
		return ecsCheckStockDao.findCancelOwnerHead(cancelTimes);
	}
	/**
	 * 查询料件情况统计表数据
	 * 
	 * @param section
	 *            批次信息
	 * @param seqNum
	 *            备案序号
	 * @return
	 */
	public List<ECSCustomsCountImg> findECSCustomsImgBySection(
			ECSSection section, Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSCustomsCountImg.class.getSimpleName(), seqNum);
	}

	/**
	 * 是否存在section(批次)的料件明细表数据
	 * 
	 * @param section
	 *            批次信息
	 */
	public boolean isExistECSDeclarationCommInfoImgBySection(ECSSection section) {
		return ecsCheckStockDao.isExistByECSSection(section,
				ECSDeclarationCommInfoImg.class.getSimpleName());
	}
	/**
	 * 是否存在section(批次)的成品明细表数据
	 * 
	 * @param section
	 *            批次信息
	 */
	public boolean isExistECSDeclarationCommInfoExgBySection(ECSSection section) {
		return ecsCheckStockDao.isExistByECSSection(section,
				ECSDeclarationCommInfoExg.class.getSimpleName());
	}
	/**
	 * 是否存在section(批次)的料件情况统计表数据
	 * 
	 * @param section
	 *            批次信息
	 */
	public boolean isExistECSCustomsImgBySection(ECSSection section) {
		return ecsCheckStockDao.isExistByECSSection(section,
				ECSCustomsCountImg.class.getSimpleName());
	}
	/**
	 * 删除section批次的料件明细表数据
	 * 
	 * @param section
	 */
	public void deleteECSDeclarationCommInfoImgBySection(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section, ECSDeclarationCommInfoImg.class
				.getSimpleName());
	}
	/**
	 * 删除section批次的成品明细表数据
	 * 
	 * @param section
	 */
	public void deleteECSDeclarationCommInfoExgBySection(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section, ECSDeclarationCommInfoExg.class
				.getSimpleName());
	}
	/**
	 * 删除section批次的料件情况统计表数据
	 * 
	 * @param section
	 */
	public void deleteECSCustomsImgBySection(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section, ECSCustomsCountImg.class
				.getSimpleName());
	}
	/**
	 * 获取预报核料件报关单数据
	 */
	public List<ECSDeclarationCommInfoImg> finddeclarationCommInfoImg(ECSSection section){
		
		Double rate = ecsContractAnalyseDao.findCurrRate();
		
		List list = ecsContractAnalyseDao.findDeclarationMaterialByEcsSection(section);
		List<ECSDeclarationCommInfoImg> infoimg =new  ArrayList<ECSDeclarationCommInfoImg>();
		for(int i =0;i<list.size();i++){
			Object[] objects =(Object[]) list.get(i);
			ECSDeclarationCommInfoImg ecsDeclarationCommInfoImg = new ECSDeclarationCommInfoImg();
			ecsDeclarationCommInfoImg.setEmsNo(objects[0].toString());
			ecsDeclarationCommInfoImg.setSeqNum(Integer.valueOf(objects[1].toString()));
			ecsDeclarationCommInfoImg.setCustomsDeclarationCode(objects[2].toString());
			ecsDeclarationCommInfoImg.setDeclarationDate((Date)objects[3]);
			ecsDeclarationCommInfoImg.setImpExpType(Integer.valueOf(objects[4].toString()));
			ecsDeclarationCommInfoImg.setTradeMode(String.valueOf(objects[5].toString()));
			ecsDeclarationCommInfoImg.setUnit(objects[6].toString());
			ecsDeclarationCommInfoImg.setCommName(objects[7].toString());
			ecsDeclarationCommInfoImg.setCommSpec(objects[8].toString());
			ecsDeclarationCommInfoImg.setCommAmount(Double.valueOf((String) (objects[9].toString() == null ? 0 : objects[9].toString())));
			
			ecsDeclarationCommInfoImg.setCommUnitPrice(Double.valueOf((String) (objects[10].toString() == null ? 0 : objects[10].toString())));
			Double commUnitPrice = CaleUtil.multiply(ecsDeclarationCommInfoImg.getCommUnitPrice(), rate,4);
			ecsDeclarationCommInfoImg.setCommUnitPrice(commUnitPrice);
			
			ecsDeclarationCommInfoImg.setTotalMoney(Double.valueOf((String) (objects[11].toString() == null ? 0 : objects[11].toString())));
			Double totalMoney = CaleUtil.multiply(ecsDeclarationCommInfoImg.getTotalMoney(), rate,5);
			ecsDeclarationCommInfoImg.setTotalMoney(totalMoney);
			
			ecsDeclarationCommInfoImg.setCurrency(objects[12].toString());
			ecsDeclarationCommInfoImg.setImpExpFlag(Integer.valueOf(objects[13].toString()));
			ecsDeclarationCommInfoImg.setSection(section);
			ecsDeclarationCommInfoImg.setCompany(CommonUtils.getCompany());
			infoimg.add(ecsDeclarationCommInfoImg);
		}
		ecsContractAnalyseDao.batchSaveOrUpdate(infoimg);
		return infoimg;
	}
	/**
	 * 获取预报核成品报关单数据
	 */
	public List<ECSDeclarationCommInfoExg> finddDeclarationCommInfoExg(ECSSection section){
		
		Double rate = ecsContractAnalyseDao.findCurrRate();
		
		List list = ecsContractAnalyseDao.findDeclarationProductByEcsSection(section);
		List<ECSDeclarationCommInfoExg> infoimg =new  ArrayList<ECSDeclarationCommInfoExg>();
		for(int i =0;i<list.size();i++){
			Object[] objects =(Object[]) list.get(i);
			ECSDeclarationCommInfoExg ecsdeclarationCommInfoExg = new ECSDeclarationCommInfoExg();
			ecsdeclarationCommInfoExg.setEmsNo(objects[0].toString());
			ecsdeclarationCommInfoExg.setSeqNum(Integer.valueOf(objects[1].toString()));
			ecsdeclarationCommInfoExg.setCustomsDeclarationCode(objects[2].toString());
			ecsdeclarationCommInfoExg.setDeclarationDate((Date)objects[3]);
			ecsdeclarationCommInfoExg.setImpExpType(Integer.valueOf(objects[4].toString()));
			ecsdeclarationCommInfoExg.setTradeMode(String.valueOf(objects[5].toString()));
			ecsdeclarationCommInfoExg.setUnit(objects[6].toString());
			ecsdeclarationCommInfoExg.setCommName(objects[7].toString());
			ecsdeclarationCommInfoExg.setCommSpec(objects[8].toString());
			ecsdeclarationCommInfoExg.setCommAmount(Double.valueOf((String) (objects[9].toString() == null ? 0 : objects[9].toString())));
			
			ecsdeclarationCommInfoExg.setCommUnitPrice(Double.valueOf((String) (objects[10].toString() == null ? 0 : objects[10].toString())));
			Double commUnitPrice = CaleUtil.multiply(ecsdeclarationCommInfoExg.getCommUnitPrice(), rate,4);
			ecsdeclarationCommInfoExg.setCommUnitPrice(commUnitPrice);
			
			ecsdeclarationCommInfoExg.setTotalMoney(Double.valueOf((String) (objects[11].toString() == null ? 0 : objects[11].toString())));
			Double totalMoney = CaleUtil.multiply(ecsdeclarationCommInfoExg.getTotalMoney(), rate,5);
			ecsdeclarationCommInfoExg.setTotalMoney(totalMoney);
			
			ecsdeclarationCommInfoExg.setCurrency(objects[12].toString());
			ecsdeclarationCommInfoExg.setImpExpFlag(Integer.valueOf(objects[14].toString()));
			ecsdeclarationCommInfoExg.setVersion(Integer.valueOf(objects[13].toString()));
			ecsdeclarationCommInfoExg.setSection(section);
			ecsdeclarationCommInfoExg.setCompany(CommonUtils.getCompany());
			infoimg.add(ecsdeclarationCommInfoExg);
		}
		ecsContractAnalyseDao.batchSaveOrUpdate(infoimg);
		return infoimg ;
	}
	/**
	 * 汇总统计section批次的料件情况统计数据
	 * 
	 * @param section
	 *            批次信息
	 */
	public List<ECSCustomsCountImg> computeECSCustomsImgBySection(
			ECSSection section) {
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在准备数据,请稍等...");
			info.setTotalNum(100);
		}
		long t = System.currentTimeMillis();

		deleteECSCustomsImgBySection(section);

		if (info != null)
			info.setCurrentNum(2);
		long t2 = System.currentTimeMillis();
		System.out.println("删除数据时间：" + (t2 - t) + "毫秒");
		t = t2;

		/**
		 * 第一步：查询出自用核销的期初数据
		 */
		List<Object[]> cancelResult = ecsContractAnalyseDao
				.findCancelOwnerMaterialBeginNum(section.getCancelOwnerHead());

		if (info != null)
			info.setCurrentNum(30);

		String key = null; // 备案序号+'/'+计量单位(code)
		// Map key：备案序号+'/'+计量单位(code)
		// Map<String,Double> cancelMaterialMap = new HashMap<String, Double>();
		Map<String, Object[]> cancelMaterialMap = new HashMap<String, Object[]>();
		if (cancelResult != null && !cancelResult.isEmpty()) {
			for (Object[] o : cancelResult) {
				Object[] list = new Object[8];
				key = CommonUtils.getStringExceptNull(o[1]).concat("/").concat(
						(String) o[4]);
				// cancelMaterialMap.put(key,(Double)o[2]);
				list[0] = (String) o[0];
				list[1] = (Integer) o[1];
				list[2] = (String) o[2];
				list[3] = (String) o[3];
				list[4] = (String) o[4];
				list[5] = (Double) o[5];
				cancelMaterialMap.put(key, list);
			}
		}
		if (info != null)
			info.setCurrentNum(60);
//		/**
//		 * 第二步:根据自用核销表头 + 报关单申报日期(盘点核查开始日期、盘点核查结束日期 区间过滤)，
//		 * 并至只查询出报关单类型类型为进口与出口，进出类型为： 料件进口、料件转厂、余料结转进口、余料结转出口、退料出口的报关料件数据
//		 * 
//		 */
//		List<Object[]> ptLs = ecsContractAnalyseDao
//				.findCustomsDeclarationMaterialByEcsSection(section);
		
		/**
		 * 2014-2-21添加料件明细表后，统计表的数据来源于明细表
		 */
		List<ECSDeclarationCommInfoImg> ptLs = ecsCheckStockDao.findByECSSection(section,
				ECSDeclarationCommInfoImg.class.getSimpleName(), null);
		
		
		if (info != null)
			info.setCurrentNum(100);
		t2 = System.currentTimeMillis();
		System.out.println("准备数据数据时间：" + (t2 - t) + "毫秒");
		t = t2;
		if (ptLs != null && ptLs.size() > 0) {
			// Map key：备案序号+'/'+计量单位(name)
			/**
			 * 第三步：统计料件情况数据
			 */
			if (info != null) {
				info.setMethodName("正在统计数据,请稍等...");
				info.setCurrentNum(1);
				info.setTotalNum(ptLs.size() / 100);
			}
			Map<String, ECSCustomsCountImg> rsMap = new HashMap<String, ECSCustomsCountImg>();
			ECSDeclarationCommInfoImg pt = null;
			for (int i = 0; i < ptLs.size(); i++) {
				if (info != null && i % 100 == 0) {
					info.setCurrentNum(info.getCurrentNum() + 1);
				}
				pt = ptLs.get(i);
				// 计算料件表
				computeCountImg(section, pt, cancelMaterialMap, rsMap);
			}
			if (info != null)
				info.setCurrentNum(100);
			t2 = System.currentTimeMillis();
			System.out.println("计算数据时间：" + (t2 - t) + "毫秒");
			t = t2;

			ECSCustomsCountImg[] rs = new ECSCustomsCountImg[rsMap.size()];
			rs = rsMap.values().toArray(rs);
			/*******************************************************************
			 * 第四步：使用备案号顺序排序
			 */
			Arrays.sort(rs, new Comparator<ECSCustomsCountImg>() {
				public int compare(ECSCustomsCountImg o1, ECSCustomsCountImg o2) {
					return (o1.getSeqNum() - o2.getSeqNum()) > 0 ? 1 : 0;
				}
			});
			List<ECSCustomsCountImg> rsLs = Arrays.asList(rs);
			for (int i = 0; i < rsLs.size(); i++) {
				rsLs.get(i).setSerialNumber(i + 1);
			}
			t2 = System.currentTimeMillis();
			System.out.println("排序数据时间：" + (t2 - t) + "毫秒");
			t = t2;
			/**
			 * 第五步：保存统计结果
			 */
			if (info != null) {
				info.setMethodName("正在保存统计结果,请稍等...");
				info.setCurrentNum(1);
				info.setTotalNum(rsLs.size() / 100);
			}
			ecsCheckStockDao.batchSaveOrUpdateDirect(rsLs,info);
			t2 = System.currentTimeMillis();
			System.out.println("保存数据时间：" + (t2 - t) + "毫秒");
			t = t2;
			return rsLs;

		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * 汇总料件情况表
	 * 
	 * @param section
	 *            批次
	 * @param pt
	 *            [账册编号，进出口标记，进出口类型，备案号（归并序号），计量单位(Unit.name)，商品名称，商品规格，商品数量]>
	 * @param cancelMaterialMap
	 *            期初数据
	 * @param rsMap
	 *            计算结果
	 */
	/**
	 * private void computeCountImg(ECSSection section,Object[] pt,Map<String,Double>
	 * cancelMaterialMap,Map<String,ECSCustomsCountImg> rsMap){ Integer
	 * impExpFlag = (Integer)pt[1]; //进出口标记 if(ImpExpFlag.SPECIAL == impExpFlag)
	 * //特殊报关单物料跳过 return; Integer impExpType = (Integer)pt[2]; //进出口类型
	 * ECSCustomsCountImg img = null; String key = null; //备案序号+'/'+计量单位(code)
	 * String emsNo = (String)pt[0]; //账册编号 Integer seqNum = (Integer)pt[3];
	 * //备案号（归并序号） String unit = ((String)pt[4]); //计量单位(Unit) String commName =
	 * (String)pt[5]; String commSpec = (String)pt[6]; double amount =
	 * forD((Double)pt[7]); //商品数量 key =
	 * CommonUtils.getStringExceptNull(pt[3]).concat("/").concat(unit); img =
	 * rsMap.get(key); if(img == null){ img = new ECSCustomsCountImg();
	 * img.init(); img.setSection(section); img.setEmsNo(emsNo);
	 * img.setCommName(commName); img.setCommSpec(commSpec);
	 * img.setSeqNum(seqNum); img.setUnit(unit);
	 * img.setQcAmount(forD(cancelMaterialMap.get(key)));
	 * img.setTotalImportAmount(img.getQcAmount()); rsMap.put(key, img); }
	 * if(ImpExpFlag.IMPORT == impExpFlag){ switch(impExpType){ case
	 * ImpExpType.DIRECT_IMPORT: //料件进口
	 * img.setDirectImportAmount(CaleUtil.add(forD(img.getDirectImportAmount()),
	 * amount));
	 * img.setTotalImportAmount(CaleUtil.add(forD(img.getTotalImportAmount()),
	 * amount)); break; case ImpExpType.TRANSFER_FACTORY_IMPORT: //料件转厂
	 * img.setTransferExportAmount(CaleUtil.add(forD(img.getTransferExportAmount()),
	 * amount));
	 * img.setTotalImportAmount(CaleUtil.add(forD(img.getTotalImportAmount()),
	 * amount)); break; case ImpExpType.REMAIN_FORWARD_IMPORT: //余料结转进口
	 * img.setRemainForwardImportAmount(CaleUtil.add(forD(img.getRemainForwardImportAmount()),
	 * amount));
	 * img.setTotalImportAmount(CaleUtil.add(forD(img.getTotalImportAmount()),
	 * amount)); break; } }else if(ImpExpFlag.EXPORT == impExpFlag){
	 * switch(impExpType){ case ImpExpType.REMAIN_FORWARD_EXPORT: //余料结转出口
	 * img.setRemainForwardExportAmount(CaleUtil.add(forD(img.getRemainForwardExportAmount()),
	 * amount));
	 * img.setTotalImportAmount(CaleUtil.subtract(forD(img.getTotalImportAmount()),
	 * amount)); break; case ImpExpType.BACK_MATERIEL_EXPORT: //退料出口
	 * img.setBackMaterExportAmount(CaleUtil.add(forD(img.getBackMaterExportAmount()),
	 * amount));
	 * img.setTotalImportAmount(CaleUtil.subtract(forD(img.getTotalImportAmount()),
	 * amount)); break; } } }
	 */

	/**
	 * 汇总料件情况表
	 * 
	 * @param section
	 *            批次
	 * @param pt
	 *            [账册编号，进出口标记，进出口类型，备案号（归并序号），计量单位(Unit.name)，商品名称，商品规格，商品数量]>
	 * @param cancelMaterialMap
	 *            期初数据
	 * @param rsMap
	 *            计算结果
	 */
	private void computeCountImg(ECSSection section, ECSDeclarationCommInfoImg pt,
			Map<String, Object[]> cancelMaterialMap,
			Map<String, ECSCustomsCountImg> rsMap) {
		Integer impExpFlag = (Integer) pt.getImpExpFlag(); // 进出口标记
		if (ImpExpFlag.SPECIAL == impExpFlag) // 特殊报关单物料跳过
			return;
		Integer impExpType = (Integer) pt.getImpExpType(); // 进出口类型
		String ptkey = CommonUtils.getStringExceptNull(pt.getSeqNum()).concat("/")
				.concat((String) pt.getUnit());
		Double amountPt = (Double) pt.getCommAmount();
		ECSCustomsCountImg img = null;
		Object[] obj = null;
		Set<Entry<String, Object[]>> entrySet = cancelMaterialMap.entrySet();
		Iterator<Map.Entry<String, Object[]>> it = entrySet.iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object[]> entry = (Map.Entry<String, Object[]>) it
					.next();
			img = rsMap.get(entry.getKey());
			obj = entry.getValue();
			double amount = forD((Double) obj[5]); // 商品数量
			if (img == null) {
				img = new ECSCustomsCountImg();
				img.init();
				img.setSection(section);
				img.setEmsNo((String) obj[0]);
				img.setSeqNum((Integer) obj[1]);
				img.setCommName((String) obj[2]);
				img.setCommSpec((String) obj[3]);
				img.setUnit(((String) obj[4]));
				img.setQcAmount(amount);
				img.setTotalImportAmount(img.getQcAmount());
				rsMap.put(entry.getKey(), img);
			}

			if (ptkey.equals(entry.getKey())) {
				if (ImpExpFlag.IMPORT == impExpFlag) {
					switch (impExpType) {
					case ImpExpType.DIRECT_IMPORT: // 料件进口
						img.setDirectImportAmount(CaleUtil.add(forD(img
								.getDirectImportAmount()), amountPt));
						img.setTotalImportAmount(CaleUtil.add(forD(img
								.getTotalImportAmount()), amountPt));
						break;
					case ImpExpType.TRANSFER_FACTORY_IMPORT: // 料件转厂
						img.setTransferExportAmount(CaleUtil.add(forD(img
								.getTransferExportAmount()), amountPt));
						img.setTotalImportAmount(CaleUtil.add(forD(img
								.getTotalImportAmount()), amountPt));
						break;
					case ImpExpType.REMAIN_FORWARD_IMPORT: // 余料结转进口
						img.setRemainForwardImportAmount(CaleUtil.add(forD(img
								.getRemainForwardImportAmount()), amountPt));
						img.setTotalImportAmount(CaleUtil.add(forD(img
								.getTotalImportAmount()), amountPt));
						break;
					}
				} else if (ImpExpFlag.EXPORT == impExpFlag) {
					switch (impExpType) {
					case ImpExpType.REMAIN_FORWARD_EXPORT: // 余料结转出口
						img.setRemainForwardExportAmount(CaleUtil.add(forD(img
								.getRemainForwardExportAmount()), amountPt));
						img.setTotalImportAmount(CaleUtil.subtract(forD(img
								.getTotalImportAmount()), amountPt));
						break;
					case ImpExpType.BACK_MATERIEL_EXPORT: // 退料出口
						img.setBackMaterExportAmount(CaleUtil.add(forD(img
								.getBackMaterExportAmount()), amountPt));
						img.setTotalImportAmount(CaleUtil.subtract(forD(img
								.getTotalImportAmount()), amountPt));
						break;
					}

				}
			}

		}
	}

	private double forD(Double d) {
		return CommonUtils.getDoubleExceptNull(d);
	}

	/**
	 * 判断是否存在某批次的数据表
	 * 
	 * @param section
	 *            批次
	 * @param analyType
	 *            核查分析类型
	 * @return
	 */
	public boolean isExistVFBySection(ECSSection section, int analyType) {
		String table = "ECSCustomsCountImg";
		if (ECSAnalyType.ECSCUSTOMS_COUNTIMG == analyType) {
			table = "ECSCustomsCountExg";
		} else if (ECSAnalyType.ECSCUSTOMS_COUNTEXG == analyType) {
			table = "ECSCustomsCountExg";
		} else if (ECSAnalyType.ECSCUSTOMS_COUNTEXG_CONVERT == analyType) {
			table = "ECSCustomsCountExgResolve";
		} else if (ECSAnalyType.ECSCUSTOMS_ANALYSE == analyType) {
			table = "ECSContractAnalyse";
		}
		return this.ecsContractAnalyseDao.isExistECSBySection(section, table);
	}

	/**
	 * 查询获得料件情况统计表,成品折料料件数据,并保存到账册分析表
	 * 
	 * @param section
	 *            批次
	 */
	public void gainEmsHeadH2kAnalyse(ECSSection section) {
		// 1、获得料件情况统计表数据
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在统计料件数据,请稍等...");
			info.setTotalNum(100);
		}
		List customsCountImgList = ecsCheckStockDao.findSumImportQtyByECSSection(section);
//		List contractAnalyseTempList = new ArrayList<ECSContractAnalyse>();
		List contractAnalyseList = new ArrayList<ECSContractAnalyse>();
		Map contractAnalyseMap = new HashMap<Integer, ECSContractAnalyse>();
//		Map<String,Double> impAnalyseMap=new HashMap<String,Double>();
		for (int i = 0; i < customsCountImgList.size(); i++) {
			ECSContractAnalyse contractAnalyse = new ECSContractAnalyse();
			contractAnalyse.init();
			Object[] countImg = (Object[]) customsCountImgList
					.get(i);
			// 盘点核查批次 帐册编号 备案序号 料件名称 型号规格 计量单位 进口数量 成品耗用 结余数量
			contractAnalyse.setSection(section);
			contractAnalyse.setEmsNo((String)countImg[0]);
			contractAnalyse.setSeqNum((Integer)countImg[1]);
//			contractAnalyse.setCommName(countImg.getCommName());
//			contractAnalyse.setCommSpec(countImg.getCommSpec());
//			contractAnalyse.setUnit(countImg.getUnit());
			contractAnalyse.setImportAmount((Double)countImg[2]);//countImg.getTotalImportAmount()
			contractAnalyse.setCompany(CommonUtils.getCompany());
//			contractAnalyseTempList.add(contractAnalyse);
			contractAnalyseMap.put(contractAnalyse.getSeqNum(), contractAnalyse);
		}
		customsCountImgList.clear();
		if (info != null)
			info.setCurrentNum(25);
		if (info != null) {
			info.setMethodName("正在统计成品数据,请稍等...");
			info.setTotalNum(100);
		}
		// 2、获得成品折料料件情况统计表数据
		List customsCountExgResolveList = ecsCheckStockDao.findSumWasteQtyByECSSection(section);
//				findECSCustomsCountExgResolveBySection(
//				section, null);
		for (int i = 0; i < customsCountExgResolveList.size(); i++) {
			
			Object[] countExgResolve = (Object[]) customsCountExgResolveList
					.get(i);
			Integer key=(Integer)countExgResolve[1];
			// 盘点核查批次 帐册编号 备案序号 料件名称 型号规格 计量单位 进口数量 成品耗用 结余数量
			ECSContractAnalyse contractAnalyse = (ECSContractAnalyse)contractAnalyseMap.get(key);
			if(contractAnalyse==null){
				contractAnalyse=new ECSContractAnalyse();
				contractAnalyse.init();
				contractAnalyse.setSection(section);
				contractAnalyse.setEmsNo((String)countExgResolve[0]);
				contractAnalyse.setSeqNum((Integer)countExgResolve[1]);
				contractAnalyse.setCompany(CommonUtils.getCompany());
				contractAnalyseMap.put(key, contractAnalyse);
			}

//			contractAnalyse.setCommName(countExgResolve.getPtName());
//			contractAnalyse.setCommSpec(countExgResolve.getPtName());
//			contractAnalyse.setUnit(countExgResolve.getUnit());
			contractAnalyse.setWasteAmount((Double)countExgResolve[2]);		

//			contractAnalyseTempList.add(contractAnalyse);
		}
		customsCountExgResolveList.clear();
		contractAnalyseList.addAll(contractAnalyseMap.values());
		if (info != null)
			info.setCurrentNum(50);
		//3、补充名称规格和单位
		List emsHeadH2kImgLs = ecsContractAnalyseDao.findEmsHeadH2kImg();
		Map emsHeadH2kImgMap = new HashMap<Integer,Object[]>();
		for (int i = 0; i < emsHeadH2kImgLs.size(); i++) {
			Object[] img = (Object[]) emsHeadH2kImgLs
					.get(i);
			Integer key = (Integer) img[1];
			Object[] obj = (Object[]) emsHeadH2kImgMap.get(key);
//			if(obj==null){
				emsHeadH2kImgMap.put(key,img);
//			}
		}
		for (int i = 0; i < contractAnalyseList.size(); i++) {
			ECSContractAnalyse contractAnalyse  = (ECSContractAnalyse) contractAnalyseList.get(i);
			Object[] obj = (Object[]) emsHeadH2kImgMap.get(contractAnalyse.getSeqNum());
			if(obj!=null){
				if(obj[2]!=null){
					contractAnalyse.setCommName(CommonUtils.getStringExceptNull(obj[2].toString()));
				}
				if(obj[3]!=null){
					contractAnalyse.setCommSpec(CommonUtils.getStringExceptNull(obj[3].toString()));
				}
				if(obj[4]!=null){
					contractAnalyse.setUnit(CommonUtils.getStringExceptNull(obj[4].toString()));
				}
			}
			contractAnalyse.setResultAmount(CaleUtil.subtract(
					contractAnalyse.getImportAmount(), contractAnalyse
							.getWasteAmount(), 4));
		}
		if (info != null) {
			info.setMethodName("正在保存折算数据,请稍等...");
			info.setTotalNum(75);
		}
		
		// 4、批量保存
		if (info != null) {
			info.setMethodName("正在保存折算数据,请稍等...");
			info.setTotalNum(100);
		}
//		Iterator it = contractAnalyseMap.keySet().iterator();
//		while (it.hasNext()) {
//			Integer key = (Integer) it.next();
//			ECSContractAnalyse contractAnalyse = (ECSContractAnalyse) contractAnalyseMap
//					.get(key);
//			contractAnalyseList.add(contractAnalyse);
//		}
		
		this.ecsCheckStockDao.batchSaveECSEnity(contractAnalyseList);
		if (info != null)
			info.setCurrentNum(100);
	}

	/**
	 * 获得账册分析
	 * 
	 * @param section
	 * @return
	 */
	public List findECSContractAnalyseBySection(ECSSection section,
			Integer seqNum) {
		List contractAnalyseList = ecsContractAnalyseDao
				.findECSContractAnalyseBySection(section, seqNum);
		List contractAnalyseListTempList = new ArrayList();
		for (int i = 0; i < contractAnalyseList.size(); i++) {
			ECSContractAnalyseTemp contractAnalyseTemp = new ECSContractAnalyseTemp();
			Object[] obj = (Object[]) contractAnalyseList.get(i);
			// 帐册编号 备案序号 料件名称 型号规格 计量单位 进口数量 成品耗用 结余数量
			contractAnalyseTemp.setEmsNo(CommonUtils
					.getStringExceptNull(obj[0]));
			contractAnalyseTemp.setSeqNum(Integer.valueOf(obj[1].toString()));
			contractAnalyseTemp.setCommName(CommonUtils
					.getStringExceptNull(obj[2]));
			contractAnalyseTemp.setCommSpec(CommonUtils
					.getStringExceptNull(obj[3]));
			contractAnalyseTemp
					.setUnit(CommonUtils.getStringExceptNull(obj[4]));
			contractAnalyseTemp.setImportAmount(obj[5] == null ? Double
					.valueOf(0) : Double.valueOf(obj[5].toString()));
			contractAnalyseTemp.setWasteAmount(obj[6] == null ? Double
					.valueOf(0) : Double.valueOf(obj[6].toString()));
			contractAnalyseTemp.setResultAmount(obj[7] == null ? Double
					.valueOf(0) : Double.valueOf(obj[7].toString()));
			contractAnalyseListTempList.add(contractAnalyseTemp);
		}
		return contractAnalyseListTempList;
	}

	/**
	 * 根据批次号分页查询成品折料,成品统计
	 * 
	 * @param section
	 *            批次号
	 * @param index
	 *            开始值
	 * @param length
	 *            长度
	 * @param property
	 *            对象属性
	 * @param value
	 *            对象值
	 * @param isLike
	 *            是否模糊
	 * @return
	 */
	public List<Object> findPageECSBySection(ECSSection section, int index, int length, String property,
			Object value, boolean isLike, int analyType) {
		String table = "ECSCustomsCountImg";
		if (ECSAnalyType.ECSCUSTOMS_COUNTIMG == analyType) {
			table = "ECSCustomsCountImg";
		} else if (ECSAnalyType.ECSCUSTOMS_COUNTEXG == analyType) {
			table = "ECSCustomsCountExg";
		} else if (ECSAnalyType.ECSCUSTOMS_COUNTEXG_CONVERT == analyType) {
			table = "ECSCustomsCountExgResolve";
		} else if (ECSAnalyType.ECSCUSTOMS_ANALYSE == analyType) {
			table = "ECSContractAnalyse";
		}
		return this.ecsContractAnalyseDao.findPageECSBySection(section, index,
				length, property, value, isLike, table);
	}
	
	/**
	 * t统计某批次的账册分析资料的笔数
	 * 
	 * @param section
	 *            批次
	 * @param isImgOrExg
	 *            料件true或成品false
	 * @return
	 */
	public long countByECSSection(ECSSection section,String clazz,String property, 
			Object value, boolean isLike) {
		return this.ecsCheckStockDao.countByECSSection(section,clazz,property,value,isLike);
	}
}
